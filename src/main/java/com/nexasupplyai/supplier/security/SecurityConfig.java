package com.nexasupplyai.supplier.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import java.util.ArrayList;
import java.util.Locale;

@Configuration @EnableWebSecurity @EnableMethodSecurity
public class SecurityConfig {
    private static final String[] DOCS = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};
    private static final String[] READ = {AppRoles.ADMIN, AppRoles.INVENTORY_MANAGER, AppRoles.PURCHASING,
            AppRoles.SALES, AppRoles.REPORT_VIEWER, AppRoles.AI_USER};
    private static final String[] WRITE = {AppRoles.ADMIN, AppRoles.PURCHASING};
    @Bean @ConditionalOnProperty(name="app.security.enabled", havingValue="true")
    SecurityFilterChain secured(HttpSecurity http) throws Exception {
        return http.csrf(c -> c.disable()).cors(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
                        .requestMatchers(DOCS).hasRole(AppRoles.ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole(READ)
                        .requestMatchers("/api/**").hasAnyRole(WRITE).anyRequest().denyAll())
                .oauth2ResourceServer(o -> o.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                        .jwt(j -> j.jwtAuthenticationConverter(jwtConverter()))).build();
    }
    @Bean @ConditionalOnProperty(name="app.security.enabled", havingValue="false", matchIfMissing=true)
    SecurityFilterChain local(HttpSecurity http) throws Exception {
        return http.csrf(c -> c.disable()).cors(Customizer.withDefaults())
                .authorizeHttpRequests(a -> a.anyRequest().permitAll()).build();
    }
    @Bean JwtAuthenticationConverter jwtConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = new ArrayList<GrantedAuthority>();
            var scopes = scopesConverter.convert(jwt);
            if (scopes != null) authorities.addAll(scopes);
            Object claim = jwt.getClaims().get("roles");
            if (claim instanceof java.util.Collection<?> roles) {
                roles.stream().map(String::valueOf)
                        .map(r -> "ROLE_" + r.toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]", "_"))
                        .map(SimpleGrantedAuthority::new).forEach(authorities::add);
            }
            return authorities;
        });
        return converter;
    }
}
