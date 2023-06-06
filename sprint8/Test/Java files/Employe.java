/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1968.framework.servlet;

import annotation.Url;
import java.sql.Date;


/**
 *
 * @author judi
 */
public class Employe {
    String name;
    Date prenom;
    int numberParametre;
    public String getName() {
        return name;
    }

    public Date getPrenom() {
        return prenom;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrenom(Date prenom) {
        this.prenom = prenom;
    }
    public void setNumberParametre(int n){
        numberParametre = n;
    }
    public String getNumberParametre() {
        return numberParametre+"";
    }
    
    @Url("find_all.do")
    public String findAll() {
        return "AKORY";
    }

    @Url("Emp_name.do")
    public ModelView print_name() {
        ModelView vue = new ModelView();
        vue.setUrl("Employer.jsp");
        String a = "hoho";
        vue.addItem("Nombre",a);
        vue.addItem("Nombres","ff");
        return vue;
    }
    
     @Url("Emp_surname.do")
    public ModelView print_surname(String hihi,String huhu) {
        ModelView vue = new ModelView();
        vue.setUrl("Emp_sprint8.jsp");
        String a = "hihi : "+hihi+" , huhu :"+huhu;
       // String a = "hu";
        vue.addItem("Reps",a);
        return vue;
    }

    @Url("Emp_save.do")
    public ModelView save() {
        ModelView vue = new ModelView();
        vue.setUrl("Employer.jsp");
        String a = "hoho";
        if(getName()!=null && getPrenom()!=null && getNumberParametre()!=null) {
            vue.addItem("Reponse",getName()+","+getPrenom()+","+getNumberParametre());
        }
        else vue.addItem("Reponse","Le nom d'attribut qui prend le valeur du donne envoyer est incorrect ( Verifier le nom de l'attribut dans le class et le nom de variable qui envoye le valeur , ils doivent etre pareil)");
        vue.addItem("Nombre",a);
        
        return vue;
    }
}
