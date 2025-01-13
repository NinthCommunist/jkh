package ru.fast.bills.security.service;

import lombok.RequiredArgsConstructor;
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
import ru.fast.bills.security.web.dto.RegistrationRequest;
import ru.fast.bills.security.web.dto.TokenResponse;

import java.util.Collection;

@Service
@RequiredArgsConstructor
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
        Collection<MediatorRoleEntity> roles = this.rolesRepository.findAllByAuthorityIn(registr.roles());

        if (roles.isEmpty()) {
            throw AuthException.roleListIsEmpty();
        }

        MediatorEntity mediatorEntity = new MediatorEntity();
        mediatorEntity.setServiceName(serviceName);
        mediatorEntity.setPassword(this.passwordEncoder.encode(registr.password()));
        mediatorEntity.setRoles(roles);

        this.mediatorRepository.save(mediatorEntity);
    }

    public TokenResponse login(LoginRequest login) {
        Authentication authenticate = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login.login(), login.password()));

        String accessToken = this.jwtProvider.accessTokenFor(authenticate);
        String refreshToken = this.jwtProvider.refreshTokenFor(authenticate);

        return new TokenResponse(accessToken, refreshToken);
    }
}
