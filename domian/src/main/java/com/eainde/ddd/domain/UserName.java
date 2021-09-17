package com.eainde.ddd.domain;

import org.immutables.value.Value;

import com.eainde.ddd.Wrapped;
import com.google.common.base.Preconditions;

import com.eainde.ddd.Wrapped;
import com.google.common.base.Preconditions;

@Value.Immutable
@Wrapped
public abstract class UserName extends Domain<String> {
  private static final int USER_NAME_MAX_LENGTH = 100;
  private static final String USER_NAME_MAX_LENGTH_VALIDATION_FAILED_MESSAGE =
      "%s must be less than or equals %s";

  @Value.Check
  public void check() {
    final String value = value();
    Preconditions.checkState(
        value.length() <= USER_NAME_MAX_LENGTH,
        USER_NAME_MAX_LENGTH_VALIDATION_FAILED_MESSAGE,
        "User Name",
        USER_NAME_MAX_LENGTH,
        value);
  }
}