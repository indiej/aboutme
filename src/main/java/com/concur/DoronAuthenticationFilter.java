package com.concur;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DoronAuthenticationFilter extends GenericFilterBean {

    private final static Logger logger = LoggerFactory.getLogger(DoronAuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("DoronAuthenticationFilter start doing stuff");
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);

        Optional<String> username = Optional.fromNullable(httpRequest.getHeader("X-Auth-Username"));
        Optional<String> password = Optional.fromNullable(httpRequest.getHeader("X-Auth-Password"));
        Optional<String> token = Optional.fromNullable(httpRequest.getHeader("X-Auth-Token"));


        try {
            logger.info("DoronAuthenticationFilter is passing request down the filter chain");
            //SecurityContextHolder.getContext().setAuthentication(new DoronAutenticationWithToken());
            SecurityContextHolder.getContext().setAuthentication(new DoronAutenticationWithToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJlbWFpbCI6InJldWJlbi5qb2huQGNvbmN1ci5jb20iLCJpc3MiOiJjb25jdXIiLCJleHAiOjE0NDQ3ODMxOTgsImlhdCI6MTQ0NDc3NTk5OH0.S4Yh1baFYAcpZyzCxkR5bHMWLdX7TPh3nCe-jgSdNTFF3Mz13gxM8PCd4JRQzWj-77XdVUd67iQjiWo_5mMPSUJchmWdK-9bbFhoBesBF84XqvnIo2IkDkIB5fYRWLuyp-kBKGZPg6zOlZIniN12cd02yOb0TsJ_nxvMELMIb0A45ZWU9oeYVnyGaSSgKKP3K2MI3i4j7MiAYxb5WKgoRh6wHjAAJ0gWSqRog7F8SkAzF8nJac6YH5N0cSuEodp4bfqzmiYxTB21q_Q7vHV_B1EQXDuq23pyo_tDBDD6vahPd1M0w9AUN1t7PYUoJiveajoAdePPavM9HmBxF7rm5G_MEXeZeurKH-54bYUG8joRSweLplGqKD79WZkJs08DNS4hAtSpEaqytFwNiP3Wgm43EFbGEHd7sCqyp9qQ7Vf3hRNEBlz40nIbvBNJb0IypsEP1RM53Sqt4CHq-5iyBFJngrwfTAMKw20d79-G0Mp50tNCFkaRGsBM8Apr54_tIqwp9mf-1pa7rhmiIzvUAMKS0KuwRejzkS4-FeI6PrjeS4eiMUGISAA6_JgyVvVCSml6V59UgWQstUx7-QEJEgXo1aMUdGIX7wF9T3VZFOoUZ94By1ax2WN7nIcPOVqhlHo_TtoLEa7Iiz0akITVWFholCC0DjaQpdbFqzr5onw"));

            logger.info("DoronAuthenticationFilter after");
            //addSessionContextToLogging();
            chain.doFilter(request, response);
            logger.info("DoronAuthenticationFilter chain");
        } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", internalAuthenticationServiceException);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        } finally {
            MDC.remove("test");
        }
    }

//    private void addSessionContextToLogging() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String tokenValue = "EMPTY";
//        if (authentication != null && !Strings.isNullOrEmpty(authentication.getDetails().toString())) {
//            MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1");
//            tokenValue = encoder.encodePassword(authentication.getDetails().toString(), "not_so_random_salt");
//        }
//        MDC.put("test", tokenValue);
//
//        String userValue = "EMPTY";
//        if (authentication != null && !Strings.isNullOrEmpty(authentication.getPrincipal().toString())) {
//            userValue = authentication.getPrincipal().toString();
//        }
//    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }
}
