package com.jao.recetas.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jao.recetas.services.security.JwtUtilsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.security.sasl.AuthenticationException;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor @Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtilsService jwtUtilsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if ((authorizationHeader != null) && (authorizationHeader.startsWith("Bearer "))) {
            try {
                String tokenStr = authorizationHeader.substring(7);//capturamos el token sin Bearer
                DecodedJWT decodedJWT = this.jwtUtilsService.validateToken(tokenStr);
                String username = this.jwtUtilsService.extractUsername(decodedJWT);
                String authoritiesStr = this.jwtUtilsService.getSpecificClaim(decodedJWT, "authorities").asString();
                log.info("TOKEN DE JAO - Authorities leídas del token: {}", authoritiesStr);
                Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);
                log.info("TOKEN DE JAO - GrantedAuthorities convertidas: {}", authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            } catch (JWTVerificationException e) {
                SecurityContextHolder.clearContext();
                throw new AuthenticationException("Acceso denegado por token inválido");
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                throw new AuthenticationException("Acceso denegado por error inesperado");
            }
        }

        filterChain.doFilter(request, response);
    }
}
