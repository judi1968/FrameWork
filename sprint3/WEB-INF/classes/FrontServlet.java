/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package etu1968.framework.servlet;

import annotation.Url;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.*;

/**
 *
 * @author judi
 */
public class FrontServlet extends HttpServlet {
   HashMap<String,Mapping> MappingURLS;
   String exeption = "Pas d'exeption";

    //getters
      public HashMap<String, Mapping> getMappingURLS() {
          return MappingURLS;
      }
    //setters
    public void setMappingURLS(HashMap<String, Mapping> MappingURLS) {
        this.MappingURLS = MappingURLS;
    }
    public String getUrlScanner(){
        String url = "url";
        try {
            File inputFile = new File("config.xml");
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
            exeption = e.getMessage();          
        } finally {
            return url;
        }
    }
    
   @Override
   public void init() throws ServletException{
       try{
        MappingURLS = new HashMap<>();        
        String path = FrontServlet.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = java.net.URLDecoder.decode(path, "UTF-8");
        String currentDirectory = getUrlScanner();
        File dir = new File(currentDirectory);
                File[] files = dir.listFiles();              
            for (File file : files) {
            String[] filename = file.getName().split("\\.");
            Class className = Class.forName("etu1968.framework.servlet."+filename[0]);
            if(className!=null){
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
       }
       catch(UnsupportedEncodingException | ClassNotFoundException | SecurityException ex){
          
        }
    }
       
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FrontServlet</title>");  
            out.println("<style>body{font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;}</style>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FrontServlet at " + request.getContextPath() + "</h1>");
            out.println("<h1>Liste des mappings :</h1>");
            int a = 0;
            for (Map.Entry<String, Mapping> entry : MappingURLS.entrySet()) {
               out.println("<p>URL: " + entry.getKey() + "</p>");
               out.println("<p>Class: " + entry.getValue().getClassName() + "</p>");
               out.println("<p>Method: " + entry.getValue().getMethod() + "</p><br>");
               a++;
            }
            if (a==0) {
                exeption = "Le url-classes dans config.xml est faux ou il n'y a pas de classe annoter Url dans le path que vous avez donner";
            }
            out.println("<h1>"+exeption+"</h1>");
            out.println("</body></html>");
        }
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            processRequest(request, response);
    }
}
