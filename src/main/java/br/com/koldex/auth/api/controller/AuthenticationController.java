package br.com.koldex.auth.api.controller;

import br.com.koldex.auth.api.domain.dto.LoginRequest;
import br.com.koldex.auth.api.domain.dto.LoginResponse;
import br.com.koldex.auth.api.domain.dto.RegisterRequest;
import br.com.koldex.auth.api.domain.service.LoginService;
import br.com.koldex.auth.api.domain.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final LoginService loginService;
    private final RegisterService registerService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        return ResponseEntity.ok(loginService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        registerService.register(request);
        return ResponseEntity.ok().build();
    }

}