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
        DAOFactory factory=new NeodatisDAOFactory();
        //DAOFactory factory=new SqlDbDAOFactory();
        DepartamentoDAO dpto=factory.getDepartamentoDAO();
        EmpleadoDAO emp=factory.getEmpleadoDAO();
        Departamento dptoAux=new Departamento(3,"Mantenimiento","Sevilla");
        Empleado empAux=new Empleado(11, "Jimenez", 1, 12, LocalDate.of(2019, 1, 19), "Oficinista", 900.0);
        
        emp.InsertarEmpleado(empAux);
        //dpto.InsertarDep(dptoAux);
        //dpto.EliminarDep(3);
        //emp.EliminarEmpleado(1);
        
        //System.out.println(dpto.ConsultarDep(3).getDnombre());
        System.out.println(emp.ConsultarEmpleado(11).getDir());
    }
}
