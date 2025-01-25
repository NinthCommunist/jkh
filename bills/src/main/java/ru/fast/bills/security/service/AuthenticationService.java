package ru.fast.bills.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fast.bills.data.models.MediatorEntity;
import ru.fast.bills.data.repository.MediatorRepository;
import ru.fast.bills.security.data.models.MediatorRoleEntity;
import ru.fast.bills.security.data.repository.MediatorRolesRepository;
import ru.fast.bills.security.exceptions.AuthException;
import ru.fast.bills.security.web.dto.LoginRequest;
import ru.fast.bills.security.web.dto.RefreshTokenRequest;
import ru.fast.bills.security.web.dto.RegistrationRequest;
import ru.fast.bills.security.web.dto.TokenResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final MediatorRepository mediatorRepository;
    private final MediatorRolesRepository rolesRepository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void registration(RegistrationRequest registr) {
        String serviceName = registr.username();
        if (this.mediatorRepository.existsByServiceName(serviceName)) {
            throw AuthException.mediatorAlreadyRegistered(serviceName);
        }
        MediatorRoleEntity role = this.rolesRepository
                .findAllByAuthority(registr.role()).orElseThrow(AuthException::roleNotFound);

        MediatorEntity mediatorEntity = new MediatorEntity();
        mediatorEntity.setServiceName(serviceName);
        mediatorEntity.setPassword(this.passwordEncoder.encode(registr.password()));
        MediatorEntity savedMediator = this.mediatorRepository.save(mediatorEntity);

        role.getMediators().add(savedMediator);
    }

    public TokenResponse login(LoginRequest login) {
        Authentication authenticate = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login.login(), login.password()));

        TokenResponse tokens = this.jwtProvider.tokensFor(authenticate);

        log.debug("Login success for {}", authenticate.getName());
        return tokens;
    }

    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        Authentication authentication = this.jwtProvider.parseAuthentication(refreshTokenRequest.refresh());
        JwtProvider.TokenWithExpiration accessToken = this.jwtProvider.accessTokenFor(authentication);
        return new TokenResponse(accessToken.token(), null, accessToken.expiration(), null);
    }
}
