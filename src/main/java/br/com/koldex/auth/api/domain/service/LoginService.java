package br.com.koldex.auth.api.domain.service;

import br.com.koldex.auth.api.domain.dto.LoginRequest;
import br.com.koldex.auth.api.domain.dto.LoginResponse;
import br.com.koldex.auth.api.domain.entity.Context;
import br.com.koldex.auth.api.domain.entity.UserAccount;
import br.com.koldex.auth.api.domain.entity.UserRole;
import br.com.koldex.auth.api.domain.repository.ContextRepository;
import br.com.koldex.auth.api.domain.repository.UserAccountRepository;
import br.com.koldex.auth.api.domain.repository.UserRoleRepository;
import br.com.koldex.auth.api.exception.BusinessException;
import br.com.koldex.auth.api.exception.ForbiddenException;
import br.com.koldex.auth.api.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final UserAccountRepository userRepository;
    private final ContextRepository contextRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        // 1. busca usuário
        UserAccount user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UnauthorizedException("Usuário ou senha inválidos")
                );

        // 2. valida senha (BCrypt)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Usuário ou senha inválidos");
        }

        // 3. busca contexto
        Context context = contextRepository.findByName(request.getContext())
                .orElseThrow(() ->
                        new BusinessException("Contexto não encontrado")
                );

        // 4. busca roles do usuário no contexto
        List<UserRole> userRoles =
                userRoleRepository.findRolesByUserAndContext(
                        user.getId(),
                        context.getName()
                );

        // 5. valida acesso ao contexto
        if (userRoles.isEmpty()) {
            throw new ForbiddenException("Usuário não possui acesso ao contexto");
        }

        // 6. monta roles
        List<String> roles = userRoles.stream()
                .map(ur -> ur.getRole().getName())
                .distinct()
                .toList();

        // 7. monta actions
        List<String> actions = userRoles.stream()
                .flatMap(ur -> ur.getRole()
                        .getRoleActions()
                        .stream())
                .map(ra -> ra.getAction().getName())
                .distinct()
                .toList();

        // 8. gera JWT
        String token = jwtService.generateAccessToken(
                user,
                context.getName(),
                roles,
                actions
        );

        return new LoginResponse(token);
    }
}
