package com.eainde.ddd.domain;

import org.immutables.value.Value;

import com.eainde.ddd.Wrapped;
import com.eainde.ddd.constants.DomainConstants;
import com.google.common.base.Preconditions;

import com.eainde.ddd.Wrapped;
import com.eainde.ddd.constants.DomainConstants;
import com.google.common.base.Preconditions;

@Value.Immutable
@Wrapped
public abstract class UserCountry extends Domain<String> {

  @Value.Check
  public void check() {
    final String value = value();
    Preconditions.checkState(
        value.length() <= DomainConstants.USER_COUNTRY_MAX_LENGTH,
        DomainConstants.MAX_LENGTH_VALIDATION_FAILED,
        "UserName",
        DomainConstants.USER_COUNTRY_MAX_LENGTH,
        value);
  }
}
