package com.gustavofernandez.exchangerate.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JwtAction {

  String value();

}
