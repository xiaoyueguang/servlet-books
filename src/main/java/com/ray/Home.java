package com.ray;

public class Home extends Controller {
  public void Index () {
    setData("name", "world");
    setTemplate("index.pug");
  }

}