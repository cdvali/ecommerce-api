package com.vtech.ecommerce.config;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Get the resource_access claim
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            return Collections.emptyList();
        }
        
        // Get the client (ecommerce-api) roles
        Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get("ecommerce-api");
        if (clientRoles == null || clientRoles.get("roles") == null) {
            return Collections.emptyList();
        }
        
        Collection<String> roles = (Collection<String>) clientRoles.get("roles");
        // Map roles to GrantedAuthority with ROLE_ prefix (and optionally uppercase them)
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }
}
