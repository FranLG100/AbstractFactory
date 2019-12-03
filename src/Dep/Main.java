/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.time.Clock;
import java.time.LocalDate;

/**
 *
 * @author DAM
 */
public class Main {
    public static void main(String[] args){
        //DAOFactory factory=new NeodatisDAOFactory();
        DAOFactory factory=new SqlDbDAOFactory();
        DepartamentoDAO dpto=factory.getDepartamentoDAO();
        EmpleadoDAO emp=factory.getEmpleadoDAO();
        Departamento dptoAux=new Departamento(1,"Mantenimiento","Sevilla");
        Departamento dptoAuxMod=new Departamento(1,"Recursos Humanos","Sevilla");
        Departamento dpto2=new Departamento(2,"Inicial","Sevilla");
        Empleado empAux=new Empleado(4, "Jimenez", 12, 12, LocalDate.of(2019, 1, 19), "Oficinista", 900.0);
        Empleado empAuxInicial=new Empleado(1, "Lorente", 2, 0, LocalDate.of(2019, 1, 19), "Oficinista", 900.0);
        Empleado empAuxInicial2=new Empleado(2, "Girol", 2, 1, LocalDate.of(2019, 1, 19), "Contable", 900.0);
        Empleado empMod=new Empleado(1, "Lorente", 2, 0, LocalDate.of(2019, 1, 19), "Jefe de Proyecto", 1800.0);
        
        //INSERTAMOS UN DEPARTAMENTO
        dpto.InsertarDep(dpto2);
        
        //INSERTAR UN DEPARTAMENTO
        System.out.println("\nINSERTAMOS DEPARTAMENTO");
        dpto.InsertarDep(dptoAux);
        //CONSULTAR DEPARTAMENTO;
        System.out.println("\n\nCONSULTAMOS DEPARTAMENTO");
        System.out.println("Numero:"+dpto.ConsultarDep(1).getDeptno());
        System.out.println("Numero:"+dpto.ConsultarDep(1).getDnombre());
        System.out.println("Numero:"+dpto.ConsultarDep(1).getLoc()+"\n");
        
        //MODIFICAMOS DEPARTAMENTO
        dpto.ModificarDep(1, dptoAuxMod);
        //LO VOLVEMOS A CONSULTAR
        System.out.println("\nCONSULTAMOS DEPARTAMENTO DE NUEVO");
        System.out.println("Numero:"+dpto.ConsultarDep(1).getDeptno());
        System.out.println("Numero:"+dpto.ConsultarDep(1).getDnombre());
        System.out.println("Numero:"+dpto.ConsultarDep(1).getLoc()+"\n");
        
        
        //ELIMINAMOS DEPARTAMENTO
        dpto.EliminarDep(1);
        
        //INSERTAMOS EMPLEADO
        System.out.println("\nINSERTAMOS EMPLEADO");
        emp.InsertarEmpleado(empAuxInicial);
        emp.InsertarEmpleado(empAuxInicial2);
        //Empleado que dara error
        emp.InsertarEmpleado(empAux);
        
        //MODIFICAMOS EMPLEADO
        System.out.println("\nMODIFICAMOS EMPLEADO");
        emp.ModificarEmpleado(1, empMod);
        
        //CONSULTAMOS EMPLEADO
        System.out.println("\nCONSULTAMOS EMPLEADO");
        System.out.println("apellido :"+emp.ConsultarEmpleado(1).getEapellido());
        System.out.println("departamento :"+emp.ConsultarEmpleado(1).getEdept());
        System.out.println("fecha alta :"+emp.ConsultarEmpleado(1).getFecha_alt());
        System.out.println("oficio :"+emp.ConsultarEmpleado(1).getOficio());
        System.out.println("salario :"+emp.ConsultarEmpleado(1).getSalario());
        System.out.println("director :"+emp.ConsultarEmpleado(1).getDir());
        
        
        //ELIMINAMOS DEPARTAMENTO CON EMPLEADOS (DA ERROR)
        dpto.EliminarDep(2);
        
        //ELIMINAMOS EMPLEADO (DA ERROR)
        emp.EliminarEmpleado(1);
        
        //System.out.println(dpto.ConsultarDep(1).getDnombre());
        
    }
}
