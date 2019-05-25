package com.ray;

import java.util.ArrayList;
import java.util.HashMap;

public class DB {
  private ArrayList<HashMap<String, Object>>books = new ArrayList<HashMap<String, Object>>();
  private ArrayList<HashMap<String, String>> authors = new ArrayList<HashMap<String, String>>();
  private int size = 10;
  public ArrayList<HashMap<String, Object>> getBooks (int page) {
    int start = (page - 1) * size;
    int end = page * size;
    int length = getBookSize();
    if (end > length) {
      end = length;
    }

    ArrayList<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();
    for (int i = start; i < end; i++) {
      // 这里要判断
      results.add(books.get(i));
    }
    return results;
  }
  public HashMap<String, Object> getBook (String id) {
    HashMap<String, Object> book = new HashMap<String,Object>();
    int size = books.size();
    for (int i = 0; i < size; i++) {
      book = books.get(i);
      if (book.get("id").equals(id)) {
        return book;
      }
    }
    return book;
  }

  public int getBookSize () {
    return books.size();
  }
  public boolean addBook (String name, String authorId) {
    boolean result = true;
    int size = books.size();

    for (int i = 0; i < size; i++) {
      if (name.equals(books.get(i).get("name"))) {
        result = false;
      }
    }
    if (!result) {
      return result;
    }

    HashMap<String, Object> book = new HashMap<String, Object>();

    book.put("id", books.size() + "");
    book.put("author", getAuthor(authorId));
    book.put("name", name);
    books.add(book);

    return result;
  }

  public HashMap<String, String> getAuthor (String authorId) {
    HashMap<String, String> author = new HashMap<String, String>();
    int length = authors.size();
    for (int i = 0; i < length; i++) {

      if (authorId.equals(authors.get(i).get("id"))) {
        author = authors.get(i);
      }
    }

    return author;
  }

  public ArrayList<HashMap<String, String>> getAuthors () {
    return authors;
  }

  public DB () {
    System.out.println("DB类开始生成数据");
    try {
      for (int i = 0; i < 20; i++) {
        HashMap<String, String> author = new HashMap<String, String>();
        author.put("id", i + 1 + "");
        author.put("name", "作家" + (i + 1));
        authors.add(author);
      }
  
      for (int i = 0; i < 100; i++) {
        HashMap<String, Object> book = new HashMap<String, Object>();
        book.put("id", i + 1 + "");
        book.put("name", "书本" + (i + 1));
        book.put("author", getRandomAuthor());
        books.add(book);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("数据初始化完成!");
  }

  private HashMap<String, String> getRandomAuthor () {
    int index = (int)(1 + Math.random() * (authors.size()));
    HashMap<String, String> author = authors.get(index - 1);
    return author;
  }
}