package br.com.koldex.auth.api.domain.service;

import br.com.koldex.auth.api.domain.dto.RegisterRequest;
import br.com.koldex.auth.api.domain.entity.UserAccount;
import br.com.koldex.auth.api.domain.repository.UserAccountRepository;
import br.com.koldex.auth.api.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {

    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new BusinessException("E-mail já cadastrado");
                });

        UserAccount user = UserAccount.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .indAtivo("S")
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}