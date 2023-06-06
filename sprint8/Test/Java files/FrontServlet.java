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
import java.util.Enumeration;
import java.lang.reflect.Field;
import java.sql.Date;
import java.lang.reflect.Parameter;



/**
 *
 */
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> MappingURLS;
    String exeption = "Tout les exception : Pas d'exeption";
    String exeption_scaner = "Exeption scanner : Pas d'exeption";
    String classe_scanner = " le path a scanner est bon";
    String name_project = "Test-Framework";
    String url = "tsisy aloha";
    String para1 = "rien para1";
    String para2 = "rien para2";

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
    /* avoir le parametre du fonction a invoker */
    /* le nom du parametre */
    public String[] getParameterName(Method method) throws Exception {
        Parameter[] parametres = method.getParameters();
        String[] paramNames = new String[parametres.length];
        for(int i = 0 ; i < parametres.length ; i++){
            paramNames[i] = parametres[i].getName();
        }
        return paramNames;

    }
        public static Class<?>[] getParameterTypes(Object[] params) {
        Class<?>[] paramTypes = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            paramTypes[i] = params[i].getClass();
        }
        return paramTypes;
    }
    public Method getDeclaredMethodInClass(Class clazzs, String methodName) throws Exception{
        for(Method methods : clazzs.getDeclaredMethods()){
            if (methods.getName().compareToIgnoreCase(methodName)==0) {
                return methods;
            }
        }
        return null;
    }
    public Class<?>[] getClassParameters(Parameter[] parameters) throws Exception{
        Class<?>[] paramClass = new Class<?>[parameters.length]; 
        int i = 0;
        for(Parameter param : parameters){
            paramClass[i] = param.getType();
            i++;
        }
        return paramClass;
    }
    Object[] getValeurToMethod(Parameter[] parameters,HttpServletRequest req)throws Exception{
        Object[] values = new Object[parameters.length];
        Map<String, String[]> params = req.getParameterMap();
        String paramName = "";
        String fieldName = "";
        int i = 0;
        int indexValues = 0;
         for(Parameter param : parameters){
            if(params.containsKey(param.getName())){
                values[indexValues]= param.getType().cast(req.getParameter(param.getName()));
            }
            else values[indexValues]= null;
            indexValues++;
        }
        return values;
    }
    public ModelView getResponse(String className, String methodName,HttpServletRequest req) throws Exception {
        String currentDirectory = getUrlScanner();
        File dir = new File(currentDirectory);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().compareTo(className.concat(".class")) == 0) {
                Class<?> clazzs = null;
                clazzs = Class.forName("etu1968.framework.servlet." + className);
                Object instance = clazzs.newInstance();
                // Vérifier si la classe Employeur a été chargée avec succès
                if (clazzs != null) {
                    // Vérifier si la méthode exsiste dans le class
                    Field[] fields = clazzs.getDeclaredFields();

                    Map<String, String[]> params = req.getParameterMap();
                    String paramName = "";
                    String fieldName = "";
                    String setterMethodName = "";
                    Method setterMethod = null;
                    Class<?> fieldType = null;
                    for (Map.Entry<String, String[]> entry : params.entrySet()) {
                        paramName = entry.getKey(); // Nom du champ du formulaire
                        
                        // Verifier par rapport au Attribut de class
                        for (Field field : fields) {
                            fieldName = field.getName();
                            fieldType = field.getType();
                            if(paramName.compareToIgnoreCase(fieldName)==0){
                                setterMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                                setterMethod = clazzs.getMethod(setterMethodName,fieldType);
                                
                                if (fieldType == int.class || fieldType == Integer.class) {
                                    setterMethod.invoke(instance,Integer.parseInt(req.getParameter(paramName)));
                                } else if (fieldType == double.class || fieldType == Double.class) {
                                    setterMethod.invoke(instance,Double.parseDouble(req.getParameter(paramName)));
                                } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                                    setterMethod.invoke(instance,Boolean.parseBoolean(req.getParameter(paramName)));
                                } else if(fieldType == Date.class){
                                    String dateString = (String)req.getParameter(paramName);
                                    String[] dateSplit = dateString.split("-");
                                    int year = Integer.valueOf(dateSplit[0]);
                                    year = year - 1900;
                                    int mounth = Integer.valueOf(dateSplit[1]);
                                    mounth = mounth - 1;
                                    int day = Integer.valueOf(dateSplit[2]);
                                    Date d = new Date(year,mounth,day);
                                    setterMethod.invoke(instance,d);
                                }   else {
                                    setterMethod.invoke(instance,fieldType.cast(req.getParameter(paramName)));
                                }
                                req.removeAttribute(paramName);
                            }
                        }
        
                    }

                    Method findMethod = null;
                    try {
                        findMethod = getDeclaredMethodInClass(clazzs,methodName);
                    } catch (NoSuchMethodException e) {
                        Exception eex = new Exception(e.getMessage()+" : exeption : "+findMethod.getName());
                        throw eex;
                    }
                    
                    // Vérifier si la méthod existe
                    if (findMethod != null) {
                        try {
                            Parameter[] parameters = findMethod.getParameters();
                            Class<?>[] classParameters = getClassParameters(parameters);
                            findMethod = clazzs.getDeclaredMethod(methodName,classParameters);
                            Object[] valeurs = getValeurToMethod(parameters,req);
                            Object result = findMethod.invoke(instance,valeurs);
                            return (ModelView) result;
                        } catch (Exception e) {
                            Exception eex = new Exception(e.getMessage()+" : exeption : "+findMethod.getName());
                            throw eex;                        }
                    } else {
                        Exception exc = new Exception("La méthode annoter n'existe pas n'existe pas dans la classe " + className);
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
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<style> body {font-family : 'segoe ui'} </style>");

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
                    modelv = getResponse(className, methodName, req);
                    String reponse = "no model";
                    HashMap<String,Object> donne = modelv.getData();
                    reponse = modelv.getUrl();
                    reponse = "Pages/" + reponse;
                    RequestDispatcher dispatcher = req.getRequestDispatcher(reponse);
                    req.setAttribute("Parametre",para1);
                    req.setAttribute("Parametre1",para2);
                    if(donne == null){
                        dispatcher.forward(req, res);
                    }else{
                        int hu = 0;
                        for (Map.Entry<String, Object> entry : donne.entrySet()) {
                            req.setAttribute(entry.getKey(),entry.getValue());
                            hu++;
                        }
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