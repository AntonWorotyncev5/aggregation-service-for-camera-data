package ru.vorotyncev.netris.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ResponseCameraModel {
  private long id;
  private String type;
  private String url;
  private String value;
  private long ttl;
}
