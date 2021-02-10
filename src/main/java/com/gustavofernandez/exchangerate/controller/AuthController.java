package com.gustavofernandez.exchangerate.controller;

import com.gustavofernandez.exchangerate.controller.dto.AuthResponse;
import com.gustavofernandez.exchangerate.security.JwtService;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final JwtService jwtService;

  @GetMapping("get-jwt")
  public Single<AuthResponse> getJwt(@RequestParam String roles) {
    return Single.fromCallable(() -> new AuthResponse(jwtService.generateJwt(roles)));
  }

}
