package ru.vorotyncev.netris.service;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vorotyncev.netris.model.ResponseCameraModel;
import ru.vorotyncev.netris.model.UrlModel;
import ru.vorotyncev.netris.utils.AsyncHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CamService {

  @Value("${ru.korshun.target}")
  private String url;

  private final AsyncHandler asyncHandler;

  @Autowired
  public CamService(AsyncHandler asyncHandler) {
    this.asyncHandler = asyncHandler;
  }

  public List<ResponseCameraModel> dataProccess() throws IOException {

    List<CompletableFuture<ResponseCameraModel>> futures = new ArrayList<>();

    String inputMas = asyncHandler.getStringFromUrl(url).orElse("[]");
    JSONArray json = new JSONArray(inputMas);

    IntStream
            .range(0, json.length())
            .mapToObj(i -> new Gson().fromJson(json.get(i).toString(), UrlModel.class))
            .forEach(i -> {
              try {
                futures.add(asyncHandler.getResult(i.getId(), i.getSourceDataUrl(), i.getTokenDataUrl()));
              } catch (IOException e) {
                e.printStackTrace();
              }
            });

    return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
  }
}
