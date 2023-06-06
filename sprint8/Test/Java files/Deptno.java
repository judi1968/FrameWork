/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1968.framework.servlet;

import annotation.Url;

/**
 *
 * @author Johan
 */
public class Deptno {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Url("Dept_name.do")
    public String print_name() {
        return "Deptno name = "+name;
    }
    
}
