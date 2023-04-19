/**
 * ModelView
 */
package etu1968.framework.servlet;

import java.util.HashMap;

/**
 *
 * @author judi
 */
public class ModelView {
    String url;
    HashMap<String,Object> data = null;
    public HashMap<String,Object> getData() {
        return data;
    }
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
    public void addItem(String key,Object value){
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }
    public void addItem(String key,int value){
        Integer val = new Integer(value);
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, val);
    }
    public void addItem(String key,double value){
        Double val = new Double(value);
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, val);
    }
    public void addItem(String key,float value){
        Float f = new Float(value);
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, f);
    }
    public void addItem(String key,char value){
        Character ch = new Character(value);
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, ch);
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
}