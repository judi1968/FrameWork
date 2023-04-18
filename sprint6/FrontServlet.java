/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package etu1968.framework.servlet;

import annotation.Url;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> MappingURLS;
    String exeption = "Tout les exception : Pas d'exeption";
    String exeption_scaner = "Exeption scanner : Pas d'exeption";
    String classe_scanner = " le path a scanner est bon";
    String name_project = "Test-Framework";

    // getters
    public HashMap<String, Mapping> getMappingURLS() {
        return MappingURLS;
    }

    // setters
    public void setMappingURLS(HashMap<String, Mapping> MappingURLS) {
        this.MappingURLS = MappingURLS;
    }

    public String getUrlScanner() {
        String url = "url";
        try {
            File inputFile = new File(name_project + "/WEB-INF/config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("sprint");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    url = eElement.getElementsByTagName("url-classes").item(0).getTextContent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            exeption_scaner = e.getMessage();
        } finally {
            return url;
        }
    }

    public void init() {
        try {
            MappingURLS = new HashMap<>();
            String path = FrontServlet.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = java.net.URLDecoder.decode(path, "UTF-8");
            String currentDirectory = getUrlScanner();
            File dir = new File(currentDirectory);
            File[] files = dir.listFiles();
            for (File file : files) {
                String[] filename = file.getName().split("\\.");
                Class className = Class.forName("etu1968.framework.servlet." + filename[0]);
                if (className != null) {
                    Method[] methods = className.getDeclaredMethods();
                    for (Method met : methods) {
                        if (met.isAnnotationPresent(Url.class)) {
                            Url annotation = met.getAnnotation(Url.class);
                            String urlValue = annotation.value();
                            MappingURLS.put(urlValue, new Mapping(className.getSimpleName(), met.getName()));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            exeption = ex.getMessage();
            classe_scanner = getUrlScanner();
        }
    }

    public String getUrlNavigateur(HttpServletRequest req) throws ServletException, IOException {
        String requestUrl = req.getRequestURL().toString();
        String host = req.getHeader("Host");
        requestUrl = requestUrl.split("//")[1].replace(host, "");
        return requestUrl;
    }

    public String[] verifyUrlNavigateur(String urlnav) {
        String method = "";
        String classe = "";
        String[] val = new String[2];
        String[] notVal = new String[3];
        boolean exist = false;
        for (Map.Entry<String, Mapping> entry : MappingURLS.entrySet()) {
            if (entry.getKey().compareToIgnoreCase(urlnav) == 0) {
                classe = entry.getValue().getClassName();
                method = entry.getValue().getMethod();
                val[0] = classe;
                val[1] = method;
                exist = true;
                break;
            }
        }
        if (exist)
            return val;
        else
            return notVal;
    }

    public ModelView getResponse(String className, String methodName) throws Exception {
        String currentDirectory = getUrlScanner();
        File dir = new File(currentDirectory);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().compareTo(className.concat(".class")) == 0) {
                Class<?> employerClass = null;
                employerClass = Class.forName("etu1968.framework.servlet." + className);
                // Vérifier si la classe Employeur a été chargée avec succès
                if (employerClass != null) {
                    // Vérifier si la méthode find_all existe dans la classe Employeur
                    Method findMethod = null;
                    try {
                        findMethod = employerClass.getMethod(methodName);
                    } catch (NoSuchMethodException e) {
                        throw e;
                    }

                    // Vérifier si la méthode find_all existe
                    if (findMethod != null) {
                        // Exécuter la méthode find_all et récupérer sa valeur de retour
                        try {
                            Object result = findMethod.invoke(employerClass.newInstance());
                            // Faire quelque chose avec la valeur de retour
                            return (ModelView) result;
                        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            throw e;
                        }
                    } else {
                        Exception exc = new Exception("La méthode find_all n'existe pas dans la classe " + className);
                        throw exc;
                    }
                } else {
                    Exception exc = new Exception("La classe " + className + " n'a pas pu être chargée.");
                    throw exc;
                }

            }
        }
        return null;
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        String urlNav = "huhu" + getUrlNavigateur(req);
        String name_project = req.getContextPath();

        String[] urltable = urlNav.split(name_project);
        if (urltable.length != 1) {
            String urlnavig = urltable[1]; // url : ex : localhost:8080/Name-project/urlnavig
            urlnavig = urlnavig.substring(1);
            String[] resultVerifyUrl = verifyUrlNavigateur(urlnavig);
            if (resultVerifyUrl.length == 2) {
                int a = 0;
                // String pathToClass = getUrlScanner()+"/";
                String className = resultVerifyUrl[0];
                String methodName = resultVerifyUrl[1];
                try {
                    ModelView modelv = null;
                    modelv = getResponse(className, methodName);
                    String reponse = "no model";
                    HashMap<String,Object> donne = modelv.getData();
                    reponse = modelv.getUrl();
                    reponse = "Pages/" + reponse;
                    RequestDispatcher dispatcher = req.getRequestDispatcher(reponse);
                    if(donne == null){
                        dispatcher.forward(req, res);
                    }else{
                        int hu = 0;
                        for (Map.Entry<String, Object> entry : donne.entrySet()) {
                            req.setAttribute(entry.getKey(),entry.getValue());
                            hu++;
                        }

                        req.setAttribute("indecs",hu+"");
                        dispatcher.forward(req, res);
                    }
                    return;
                } catch (Exception e) {
                    out.println(e.getMessage());
                }
            } else
                out.println("<h1 color='red'>Url : " + urlnavig + " introuvable</h1>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}