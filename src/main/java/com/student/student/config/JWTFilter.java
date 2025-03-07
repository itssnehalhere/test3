package com.student.student.config;

import com.student.student.entity.StudentEntity;
import com.student.student.repository.StudentEntityRepository;
import com.student.student.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private StudentEntityRepository repository;

    public JWTFilter(JWTService jwtService, StudentEntityRepository repository) {
        this.jwtService = jwtService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        System.out.println(tokenHeader);
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);
            System.out.println(token);
            String user = jwtService.getUser(token);

            Optional<StudentEntity> opUsername=repository.findByUsername(user);
            if(opUsername.isPresent()){
                StudentEntity username = opUsername.get();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username.getName(),
                        null,null);
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);


            }

        }
        filterChain.doFilter(request,response);
    }
}
