package com.ray;

import java.util.ArrayList;
import java.util.HashMap;

public class Book extends Controller {
  public void Index () {
    String paramsPage = req.getParameter("page");
    if (paramsPage == null || paramsPage.length() == 0) {
      paramsPage = "1";
    }
    int total = db.getBookSize();
    int page = Integer.parseInt(paramsPage);
    double totalPage = Math.ceil(total / 10f);
    setData("prev", page - 1);
    setData("next", page + 1);
    setData("end", new Double(totalPage + "").intValue());
    setData("page", page);
    setData("total", db.getBookSize());
    setData("books", (ArrayList<HashMap<String, Object>>)db.getBooks(page));
    setTemplate("lists.html");
  }

  public void Detail () {
    String id = req.getParameter("id");

    if (id == null || id.length() == 0) {
      // setTemplate(template);
    } else {
      setData("book", db.getBook(id));
      setTemplate("detail.html");
    }
  }

  public void Add () {
    ArrayList<HashMap<String, String>> authors = db.getAuthors();
    setData("authors", authors);
    setTemplate("add.html");
  }

  public void AddBook () {
    String name = req.getParameter("name");
    String authorId = req.getParameter("authorId");
    String message = "";
    String template;

    if (name == null || name.length() == 0) {
      message = "书本名未填写";
      template = "add-book-fail.html";
    } else {
      boolean isSuccess = db.addBook(name, authorId);
      if (isSuccess) {
        template = "add-book-success.html";
      } else {
        message = "书本有重复";
        template = "add-book-fail.html";
      }
    }

    setData("message", message);
    setData("name", name);
    setTemplate(template);
  }

  public void Edit () {}
}