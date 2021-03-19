package com.prvn.mobile.ws.config;

import com.prvn.mobile.ws.constants.SecurityConst;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader(SecurityConst.TOKEN_AUTH_HEADER);
        if(authHeader==null || authHeader.isEmpty() || !authHeader.startsWith(SecurityConst.TOKEN_BEARER)){
              chain.doFilter(request,response);
        }
        UsernamePasswordAuthenticationToken authorization = getAuthorization(authHeader);
        SecurityContextHolder.getContext().setAuthentication(authorization);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthorization(String authHeader) {
        authHeader = authHeader.replace(SecurityConst.TOKEN_BEARER,"");
        String user = Jwts.parser()
                .setSigningKey(SecurityConst.SECRET_CODE)
                .parseClaimsJws(authHeader)
                .getBody()
                .getSubject();
        if(user!= null){
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }
        return null;
    }
}
