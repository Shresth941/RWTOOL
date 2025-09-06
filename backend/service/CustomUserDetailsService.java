package RwTool.rwtool.service;

import RwTool.rwtool.entity.User;
import RwTool.rwtool.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository repo) {
        this.userRepository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // We use email as username
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Map role name to authority: ROLE_<NAME_UPPER_UNDERSCORE>
        String roleName = user.getRole() != null ? user.getRole().getName() : "READ_ONLY";
        String normalized = roleName.trim().toUpperCase().replace(' ', '_');
        String authority = "ROLE_" + normalized;

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(authority));

        boolean enabled = user.isActive();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities
        );
    }
}
