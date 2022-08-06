package ru.vorotyncev.netris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vorotyncev.netris.model.ResponseCameraModel;
import ru.vorotyncev.netris.service.CamService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/")
public class CamController {

  private final CamService camService;

  @Autowired
  public CamController(CamService camService) {
    this.camService = camService;
  }

  @GetMapping("/cam")
  public ResponseEntity getAllCams() {

    List<ResponseCameraModel> list;
    try {
      long startTime = System.currentTimeMillis();

      list = camService.dataProccess();

      long endTime = System.currentTimeMillis();
      System.out.printf("procceced in %f seconds%n", (double)(endTime - startTime)/1000);

    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Query failed: " + e.getMessage());
    }

    return ResponseEntity.ok(list);
  }

}
