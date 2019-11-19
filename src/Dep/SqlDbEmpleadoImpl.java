/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;

/**
 *
 * @author 
 */
public class SqlDbEmpleadoImpl implements EmpleadoDAO {

    Connection conexion;

    public SqlDbEmpleadoImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

    public boolean InsertarEmpleado(Empleado emp) {
        boolean valor = false;
        String sql = "INSERT INTO empleados VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, emp.getEapellido());
            sentencia.setInt(2, emp.getEdept());
            sentencia.setInt(3,emp.getDir());
            sentencia.setInt(4,emp.getEmp_no());
            sentencia.setDate(5, java.sql.Date.valueOf(emp.getFecha_alt()));
            sentencia.setString(6,emp.getOficio());
            sentencia.setDouble(7, emp.getSalario());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas insertadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                 System.out.printf("Empleado %d insertado%n", emp.getEmp_no());
            }
            sentencia.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public boolean EliminarEmpleado(int emp_no) {
        boolean valor = false;
        String sql = "DELETE FROM empleados WHERE emp_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, emp_no);
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas eliminadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d eliminado%n", emp_no);
            }
            sentencia.close();
        } catch (SQLException e) {
            MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public boolean ModificarEmpleado(int num, Empleado emp) {
        boolean valor = false;
        String sql = "UPDATE empleados SET apellido = ?, dept_no = ?, dir = ?, fecha_alt = ?, oficio = ?, salario = ? WHERE emp_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(7, num);
            sentencia.setString(1, emp.getEapellido());
            sentencia.setInt(2, emp.getEdept());
            sentencia.setInt(3,emp.getDir());
            sentencia.setDate(4, java.sql.Date.valueOf(emp.getFecha_alt()));
            sentencia.setString(5,emp.getOficio());
            sentencia.setDouble(6, emp.getSalario());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas modificadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d modificado%n", num);
            }
            sentencia.close();
        } catch (SQLException e) {
           MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public Empleado ConsultarEmpleado(int emp_no) {
        String sql = "SELECT emp_no, apellido, dept_no, oficio, salario, fecha_alt, dir FROM empleados WHERE emp_no =  ?";
        PreparedStatement sentencia;
        Empleado emp = new Empleado();        
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, emp_no);
            ResultSet rs = sentencia.executeQuery();          
            if (rs.next()) {
                emp.setEmp_no(rs.getInt("emp_no"));
                emp.setEapellido(rs.getString("apellido"));
                emp.setEdept(rs.getInt("dept_no"));
                emp.setFecha_alt(LocalDate.from(Instant.ofEpochMilli(rs.getDate("fecha_alt").getTime())));
                emp.setSalario(rs.getDouble("salario"));
                emp.setDir(rs.getInt("dir"));
                emp.setOficio(rs.getString("oficio"));
            }
            else
                System.out.printf("Empleado: %d No existe%n",emp_no);
            
            rs.close();// liberar recursos
            sentencia.close();
         
        } catch (SQLException e) {
            MensajeExcepcion(e);            
        }
        return emp;
    }

    private void MensajeExcepcion(SQLException e) {
       System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
       System.out.printf("Mensaje   : %s %n", e.getMessage());
       System.out.printf("SQL estado: %s %n", e.getSQLState());
       System.out.printf("Cód error : %s %n", e.getErrorCode());
    }
}
