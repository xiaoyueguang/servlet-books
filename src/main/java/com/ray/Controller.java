package com.ray;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class Controller {
  private int statusCode = 404;
  protected DB db;
  public void setStatusCode (int code) {
    if (code == 500) {
      template = "500.html";
    }
    statusCode = code;
  }
  public int getStatus () {
    return statusCode;
  }

  public void setDb(DB db) {
    this.db = db;
  }

  private String template = "404.html";
  private HashMap<String, Object>data = new HashMap<String, Object>();
  protected HttpServletRequest req;

  public void setTemplate (String template) {
    this.template = template;
    statusCode = 200;
  }
  public String getTemplate () {
    return template;
  }
  public void setData (String key, Object val) {
    data.put(key, val);
  }
  public HashMap<String, Object> getData () {
    return data;
  }
  public void setReq (HttpServletRequest req) {
    this.req = req;
  }
}