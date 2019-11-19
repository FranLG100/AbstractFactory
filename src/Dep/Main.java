/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.time.Clock;

/**
 *
 * @author DAM
 */
public class Main {
    public static void main(String[] args){
        NeodatisDAOFactory.crearConexion();
        SqlDbDAOFactory.crearConexion();
        //DAOFactory factory=new NeodatisDAOFactory();
        DAOFactory factory=new SqlDbDAOFactory();
        DepartamentoDAO dpto=factory.getDepartamentoDAO();
        Departamento dptoAux=new Departamento(1,"Finanzas","Sevilla");
        
        
        
        //dpto.InsertarDep(dptoAux);
        
        System.out.println(dpto.ConsultarDep(1).getDnombre());
    }
}
