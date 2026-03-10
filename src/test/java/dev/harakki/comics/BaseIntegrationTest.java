package dev.harakki.comics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    protected static final UUID ADMIN_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    protected static final UUID REGULAR_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
    protected static final UUID OTHER_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000003");

    protected SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor adminJwt() {
        return jwt()
                .jwt(builder -> builder
                        .subject(ADMIN_USER_ID.toString())
                        .claim("realm_access", Map.of("roles", List.of("ADMIN", "USER"))))
                .authorities(
                        new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USER")
                );
    }

    protected SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor userJwt() {
        return jwt()
                .jwt(builder -> builder
                        .subject(REGULAR_USER_ID.toString())
                        .claim("realm_access", Map.of("roles", List.of("USER"))))
                .authorities(new SimpleGrantedAuthority("ROLE_USER"));
    }

    protected SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor userJwt(UUID userId) {
        return jwt()
                .jwt(builder -> builder
                        .subject(userId.toString())
                        .claim("realm_access", Map.of("roles", List.of("USER"))))
                .authorities(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
