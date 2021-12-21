package com.example.skpr2.skprojekat2mainservice.security.service;

import io.jsonwebtoken.Claims;

public interface TokenService {

    Claims parseToken(String jwt);
}
