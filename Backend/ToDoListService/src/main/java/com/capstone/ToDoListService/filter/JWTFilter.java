package com.capstone.ToDoListService.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        // Since we are working with HTTP requests therefore we need to typecast it
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // GETTING HEADER
        String tokenHeader = httpServletRequest.getHeader("authorization");

        // Printing the responses
        System.out.println( "HEADER "+ tokenHeader);
        System.out.println( "METHOD "+ httpServletRequest.getMethod());

        // Checking what kind of request method is called
        if("OPTIONS".equals(httpServletRequest.getMethod()))
        {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
        else if(tokenHeader == null || !tokenHeader.startsWith("Bearer") )
        {
            throw new ServletException("Missing or Invalid Exception");
        }

        // If HEADER is found with Bearer Token
        String token = tokenHeader.substring(7); // Actual token starts with 7th index
        System.out.println( "TOKEN "+ token);

        // Claiming the token | SigningKey = key mentioned at the time of setting algorithm in generating token.
        Claims claims= Jwts.parser().setSigningKey("securityKey").parseClaimsJws(token).getBody();
        System.out.println("CLAIMS "+ claims);

        httpServletRequest.setAttribute("claims", claims);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
