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
        int total=0;
        int exDpto=0;
        int exJefe=0;
        boolean valor = false;
        String contador="SELECT COUNT(*) FROM empleados WHERE emp_no="+emp.getEmp_no();
        String existeDpto="SELECT EXISTS(SELECT * FROM departamentos WHERE dept_no="+emp.getEdept()+")";
        String existeJefe="SELECT EXISTS(SELECT * FROM empleados WHERE emp_no="+emp.getDir()+")";
        String sql = "INSERT INTO empleados VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia;
        PreparedStatement existeDptoPS;
        PreparedStatement existeJefePS;
        PreparedStatement cuentaFilas;
        try {
            cuentaFilas=conexion.prepareStatement(contador);
            ResultSet rs = cuentaFilas.executeQuery();
            if (rs.next()) {
                total=rs.getInt(1);
            }
            existeDptoPS=conexion.prepareStatement(existeDpto);
            rs = existeDptoPS.executeQuery();
            if (rs.next()) {
                exDpto=rs.getInt(1);
            }
                existeJefePS=conexion.prepareStatement(existeJefe);
                rs = existeJefePS.executeQuery();
                if (rs.next()) {
                    exJefe=rs.getInt(1);
                }
            if(total==0 && exDpto>0 && (exJefe>0 ||emp.getDir()==0) ){
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1,emp.getEmp_no());
            sentencia.setString(2, emp.getEapellido());
            sentencia.setString(3,emp.getOficio());
            sentencia.setInt(4,emp.getDir());
            sentencia.setDate(5, java.sql.Date.valueOf(emp.getFecha_alt()));
            sentencia.setDouble(6, emp.getSalario());
            sentencia.setInt(7, emp.getEdept());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas insertadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                 System.out.printf("Empleado %d insertado%n", emp.getEmp_no());
            }
            sentencia.close();
            }else{
                if(total!=0)
                    System.out.println("Ya existe ese empleado");
                if(exDpto==0)
                    System.out.println("No existe ese departamento");
                if(exJefe==0 && emp.getDir()!=0)
                    System.out.println("No existe ese Director");
            }
        } catch (SQLException e) {
            MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public boolean EliminarEmpleado(int emp_no) {
        int total=0;
        boolean valor = false;
        String contador="SELECT COUNT(*) FROM empleados WHERE dir="+emp_no;
        String sql = "DELETE FROM empleados WHERE emp_no = ? ";
        PreparedStatement sentencia;
        PreparedStatement cuentaFilas;
        try {
            cuentaFilas=conexion.prepareStatement(contador);
            ResultSet rs = cuentaFilas.executeQuery();
            if (rs.next()) {
                total=rs.getInt(1);
            }
            if(total==0){
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, emp_no);
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas eliminadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d eliminado%n", emp_no);
            }
            sentencia.close();
            }else{
                System.out.println("Ese empleado tiene subordinados");
            }
        } catch (SQLException e) {
            MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public boolean ModificarEmpleado(int num, Empleado emp) {
        boolean valor = false;
        int exDpto=0;
        int exJefe=0;
        String existeDpto="SELECT EXISTS(SELECT * FROM departamentos WHERE dept_no="+emp.getEdept()+")";
        String existeJefe="SELECT EXISTS(SELECT * FROM empleados WHERE emp_no="+emp.getDir()+")";
        String sql = "UPDATE empleados SET apellido = ?, dept_no = ?, dir = ?, fecha_alt = ?, oficio = ?, salario = ? WHERE emp_no = ? ";
        PreparedStatement sentencia;
        PreparedStatement existeDptoPS;
        PreparedStatement existeJefePS;
         try {
            existeDptoPS=conexion.prepareStatement(existeDpto);
            ResultSet rs = existeDptoPS.executeQuery();
            if (rs.next()) {
                exDpto=rs.getInt(1);
            }
            existeJefePS=conexion.prepareStatement(existeJefe);
            rs = existeJefePS.executeQuery();
            if (rs.next()) {
                exJefe=rs.getInt(1);
            }
            if(exDpto>0 && (exJefe>0 || emp.getDir()==0)){
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
            }else{
                if(exDpto==0)
                    System.out.println("No existe ese departamento");
                if(exJefe==0)
                    System.out.println("No existe ese Director");
            }
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
                emp.setFecha_alt(LocalDate.from(rs.getDate("fecha_alt").toLocalDate()));
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
