package edu.cnm.deepdive.codebreaker.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class HelloController {

  @FXML
  private Text greeting;

  @FXML
  private void initialize() throws InterruptedException {

    greeting.setText("Hello, brave new world!");


  }
}
