package br.com.nogueira.springsecurity.controller.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
