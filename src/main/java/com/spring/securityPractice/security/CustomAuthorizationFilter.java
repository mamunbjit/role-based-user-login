package com.spring.securityPractice.security;


import com.spring.securityPractice.constants.AppConstants;
import com.spring.securityPractice.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;


import java.io.IOException;
import java.util.ArrayList;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AppConstants.HEADER_STRING);
        if(header==null||!header.startsWith(AppConstants.TOKEN_PREFIX)){
            filterChain.doFilter(request,response);
        }else {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(header);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        }
    }


private UsernamePasswordAuthenticationToken getAuthenticationToken(String header) {
    if (header != null) {
        String token = header.replace(AppConstants.TOKEN_PREFIX, "");
        String user = JWTUtils.hasTokenExpired(token) ? null : JWTUtils.extractUser(token);

        if (user != null) {
            // Fetch user's roles/authorities from your authentication logic
            List<GrantedAuthority> authorities = getAuthoritiesForUser(user);

            // Create and return the authentication token with roles/authorities
            return new UsernamePasswordAuthenticationToken(user, null, authorities);
        }
    }
    return null;
}

    // Implement this method to fetch the user's authorities/roles from your authentication logic
    private List<GrantedAuthority> getAuthoritiesForUser(String user) {
        // You should implement this method to retrieve the user's roles/authorities from your database or any other source.
        // For example, you can use Spring Security's UserDetails to fetch authorities.
        // Here's a simplified example where all users have the "ROLE_USER" authority:
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // Add more authorities as needed for your application.
        return authorities;
    }

}

