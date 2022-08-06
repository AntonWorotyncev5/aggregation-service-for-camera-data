package ru.vorotyncev.netris.model;

import lombok.Value;

@Value
public class UrlModel {
  int id;
  String sourceDataUrl;
  String tokenDataUrl;
}
