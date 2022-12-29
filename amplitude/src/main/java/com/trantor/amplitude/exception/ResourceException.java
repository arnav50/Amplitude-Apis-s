package com.trantor.amplitude.exception;

public class ResourceException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ResourceException(String msg, Throwable e) {
    super(msg, e);
  }
}
