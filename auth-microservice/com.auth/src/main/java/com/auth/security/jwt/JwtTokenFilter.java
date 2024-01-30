package com.auth.security.jwt;

import com.auth.security.service.UserDetailsServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(req);

    //        logger.error("tokenr "+token+"<<<");
            
            
            if (token!=null && jwtProvider.validateToken(token)) {
                String nombreUsuario=jwtProvider.getNombreUsuarioFromToken(token);
                
                //logger.error("doFilterInternal User "+nombreUsuario+"<<<");
                
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);                        

                //logger.error("doFilterInternal token válido");
            } else {
                logger.error("doFilterInternal error en tokens");
            }
            
        } catch (Exception e) {
            logger.error("fail en el método doFilterInternal");
        }
        filterChain.doFilter(req, res);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        //logger.error("doFilterInternal header "+header);

        if(header!=null && header.startsWith("Bearer"))
            return header.replace("Bearer ","");
        return null;        
    }
}
