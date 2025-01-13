package ru.fast.bills.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.fast.bills.data.models.MediatorEntity;
import ru.fast.bills.data.repository.MediatorRepository;

@Component
@RequiredArgsConstructor
public class MediatorUserDetailsServiceImpl implements UserDetailsService {
    private final MediatorRepository mediatorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MediatorEntity mediatorEntity = this.mediatorRepository.findByServiceName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Service %s not found".formatted(username)));

        return new User(mediatorEntity.getServiceName(),
                mediatorEntity.getPassword(),
                mediatorEntity.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getAuthority().name())).toList());
    }
}
