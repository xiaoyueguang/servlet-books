package com.ray;

import java.lang.reflect.Method;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.PrintWriter;
import java.util.HashMap;
import java.lang.reflect.Constructor;

public class Main extends HttpServlet {
  private static final long serialVersionUID = 1L;
  /**
   * 路由列表 path: METHOD.CONTROLLER.ACTION
   */
  public HashMap<String, String> routes = new HashMap<String, String>();
  public Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
  private DB db;


  // 初始化. 只有创建时执行一次. 之后请求都将不再执行
  public void init() throws ServletException {
    System.out.println("初始化");
    db = new DB();
    // 设置模板目录
    cfg.setClassForTemplateLoading(cfg.getClass(), "/");
    cfg.setDefaultEncoding("UTF-8");
    routes.put("/", "GET.Home.Index");
    routes.put("/books", "GET.Book.Index");
    routes.put("/book", "GET.Book.Detail");
    routes.put("/add", "GET.Book.Add");
    routes.put("/add-book", "POST.Book.AddBook");
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    doResponse(req, res, "GET");
  }

  protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    doResponse(req, res, "POST");
  }

  protected void doResponse (HttpServletRequest req, HttpServletResponse res, String method) throws ServletException, IOException {
    String path = req.getServletPath();
    // 设置响应内容类型
    res.setContentType("text/html");
    res.setCharacterEncoding("UTF-8");
    req.setCharacterEncoding("UTF-8");

    PrintWriter out = res.getWriter();
    try {
      Controller controller;
      Template temp;
      controller = actionRun(path, method, db, req);
      res.setStatus(controller.getStatus());

      temp = cfg.getTemplate(controller.getTemplate());
      temp.setSetting("classic_compatible", "true");
      temp.process(controller.getData(), out);

    } catch (IOException e) {
      System.out.println("报错了!");
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("报错了啊!");
      e.printStackTrace();
    }
  }

  protected Controller actionRun (String path, String METHOD, DB db, HttpServletRequest req) {
    try {
      String[] action = routes.get(path).split("\\.");
      if (METHOD.equals(action[0])) {
        Class<?> controllerClass = Class.forName("com.ray." + action[1]);
        Method method = controllerClass.getMethod(action[2]);
        Constructor<?> constructor = controllerClass.getConstructor();
        Controller controller = (Controller)constructor.newInstance();
        controller.setDb(db);
        controller.setReq(req);
        method.invoke(controller);
        return controller;
      } else {
        return new Controller();
      }
    } catch (Exception e) {
      e.printStackTrace();
      return errorHandler();
    }
  }

  protected Controller errorHandler () {
    Controller controller = new Controller();
    controller.setStatusCode(500);
    return controller;
  }
}