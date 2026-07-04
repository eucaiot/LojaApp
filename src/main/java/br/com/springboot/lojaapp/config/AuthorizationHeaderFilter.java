package br.com.springboot.lojaapp.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthorizationHeaderFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationHeaderFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            String authorizationHeader = httpRequest.getHeader(AUTHORIZATION_HEADER);
            if (authorizationHeader != null && !authorizationHeader.isBlank()) {
                log.debug("Authorization header recebido: {}", authorizationHeader);
                httpRequest.setAttribute(AUTHORIZATION_HEADER, authorizationHeader);
            }
        }
        chain.doFilter(request, response);
    }
}
