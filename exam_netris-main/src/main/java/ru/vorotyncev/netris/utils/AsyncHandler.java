package ru.vorotyncev.netris.utils;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import ru.vorotyncev.netris.model.ResponseCameraModel;
import ru.vorotyncev.netris.model.SourceDataModel;
import ru.vorotyncev.netris.model.TokenDataModel;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Component
public class AsyncHandler {

  @Async
  public <T> CompletableFuture<T> getDataFromUrl(String url, Class<T> type) throws IOException {

    String inputJson = getStringFromUrl(url).orElse("");
    T data = new Gson().fromJson(inputJson, type);

    return new AsyncResult<>(data).completable();
  }

  @Async
  public CompletableFuture<ResponseCameraModel> getResult(int id, String sourceDataUrl, String tokenDataUrl) throws IOException {

    CompletableFuture<SourceDataModel> sourceData =
            getDataFromUrl(sourceDataUrl, SourceDataModel.class);
    CompletableFuture<TokenDataModel> tokenData =
            getDataFromUrl(tokenDataUrl, TokenDataModel.class);

    ResponseCameraModel responseModel = new ResponseCameraModel();
    responseModel.setId(id);

    Stream.of(sourceData, tokenData).map(CompletableFuture::join).forEach(i -> {
      if (i instanceof SourceDataModel) {
        responseModel.setType(((SourceDataModel) i).urlType());
        responseModel.setUrl(((SourceDataModel) i).videoUrl());
      } else if (i instanceof TokenDataModel) {
        responseModel.setValue(((TokenDataModel) i).getValue());
        responseModel.setTtl(((TokenDataModel) i).getTtl());
      }
    });

    return new AsyncResult<>(responseModel).completable();
  }

  public Optional<String> getStringFromUrl(String url) throws IOException {
    return Optional.of(IOUtils.toString(new URL(url), StandardCharsets.UTF_8));
  }

}
