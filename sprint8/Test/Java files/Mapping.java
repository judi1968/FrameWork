/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1968.framework.servlet;


/**
 *
 * @author judi
 */
public class Mapping {
    String className;
    String method;

    public Mapping(String className, String method) {
        this.className = className;
        this.method = method;
    }

    //Getters
    public String getClassName() {
        return className;
    }
     public String getMethod() {
        return method;
    }

     //Setters
    public void setMethod(String method) {
        this.method = method;
    }
    public void setClassName(String name) {
        this.className = name;
    }   
}
