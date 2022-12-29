package com.trantor.amplitude.enums;

public enum SchemaObjectEnum {
  Active_Users_Counts("active_users_counts"), Annotations("annotations"), Average_Session_Length(
      "average_session_length"), Cohorts("cohorts"), Events("events");

  private String streamObjectName;

  SchemaObjectEnum(String streamObjName) {
    this.streamObjectName = streamObjName;
  }

  public String getStreamObjectName() {
    return streamObjectName;
  }
}
