package br.com.koldex.auth.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class JwtPayloadResponse {

    private String sub;
    private String email;
    private String context;
    private List<String> roles;
    private List<String> actions;
}