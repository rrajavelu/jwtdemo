package com.eshanit.jwtdemo.Filter;

import com.eshanit.jwtdemo.service.CustomUserDetailService;
import com.eshanit.jwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// call this filter only once per request
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // This method will execute before any controller
        // Get JWT token from request Header
        // Validate the JWT token

        String bearerToken = request.getHeader("Authorization");
        String userName = null;
        String token= null;
        // check the token exist or has Bearer

        if (bearerToken !=null && bearerToken.startsWith("Bearer")){
            // extract JWT token from bearer Token
            token = bearerToken.substring(7);
            try{
                // Extract username from token
                userName = jwtUtil.extractUsername(token);

                // Get userDetails for this user
                UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);

                // Security Checks
                if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(upat);
                } else{
                    System.out.println("Invalid Bearer Token");
                }

            }catch (Exception ex){
                ex.printStackTrace();

            }
        }else{
            System.out.println("Invalid Bearer Token");
        }

        // If all is well forward the filter request to the request Endpoint
        filterChain.doFilter(request,response);
    }
}
