package ru.vorotyncev.netris.model;

import lombok.Value;

@Value
public class TokenDataModel {
  String value;
  long ttl;
}
