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
        Departamento dptoAux=new Departamento(1,"Contabilidad","Sevilla");
        Empleado empAux=new Empleado(4, "Rodriguez", 2, 1, LocalDate.of(2019, 1, 19), "Oficinista", 900.0);
        
        emp.InsertarEmpleado(empAux);
        //dpto.InsertarDep(dptoAux);
        
        System.out.println(dpto.ConsultarDep(2).getDnombre());
        System.out.println(emp.ConsultarEmpleado(4).getEapellido());
    }
}
