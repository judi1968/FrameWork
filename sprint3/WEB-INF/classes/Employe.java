/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1968.framework.servlet;

import annotation.Url;

/**
 *
 * @author IrinaKaren
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
    
    @Url("Emp_name")
    public String print_name() {
        return "Employé name = "+name;
    }
    
     @Url("Emp_surname")
    public String print_surname() {
        return "Employé name = "+prenom;
    }
}
