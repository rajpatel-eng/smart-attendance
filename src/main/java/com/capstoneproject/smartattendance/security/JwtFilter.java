package com.capstoneproject.smartattendance.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capstoneproject.smartattendance.entity.User;
import com.capstoneproject.smartattendance.exception.CustomeException;
import com.capstoneproject.smartattendance.exception.ErrorCode;
import com.capstoneproject.smartattendance.repository.UserRepo;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserRepo userRepo;


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
        throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String token = null;
        String userId = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token == null && request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie c : request.getCookies()) {
                if ("JWT".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }


        if (token != null) {
            try {
                userId = jwtService.extractUsername(token);
            } catch (Exception e) {
            }
        }


        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            try{
                userDetails = userDetailsService.loadUserByUsername(userId);
            }catch(Exception e){
                Cookie cookie = new Cookie("JWT", "");
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(0);
                cookie.setSecure(false);
                response.addCookie(cookie);
            }
            
            if (userDetails!=null && jwtService.isTokenValid(token, userDetails.getUsername())) {
                User user = userRepo.findById(userId).orElseThrow(()-> new CustomeException(ErrorCode.USER_NOT_FOUND));
                String tokenHash = HashUtil.sha256(token);
                if(tokenHash.equals(user.getJwt())){
                    UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } else {
           
            }
        }

        filterChain.doFilter(request, response);
    }

}
