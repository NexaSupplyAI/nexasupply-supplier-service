package com.nexasupplyai.supplier.config;
import org.springframework.http.HttpHeaders;
public interface InterServiceAuthenticationProvider { void apply(HttpHeaders headers); }
