/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1968.framework.servlet;

import annotation.Url;

/**
 *
 * @author judi
 */
public class Employe {
    String name;
    String prenom;

    public String getName() {
        return name;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    @Url("find_all")
    public String findAll() {
        return "AKORY";
    }

    @Url("Emp_name")
    public ModelView print_name() {
        ModelView vue = new ModelView();
        vue.setUrl("Employer.jsp");
        String a = "hoho";
        vue.addItem("Nombre",a);
        vue.addItem("Nombres","ff");
        return vue;
    }
    
     @Url("Emp_surname")
    public String print_surname() {
        return "Employé name = "+prenom;
    }
}
