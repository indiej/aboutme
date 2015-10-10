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
            SecurityContextHolder.getContext().setAuthentication(new DoronAutenticationWithToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJlbWFpbCI6InRvbUBjb25jdXIuY29tIiwiaXNzIjoiY29uY3VyIiwiZXhwIjoxNDQ0NDIyNDUwLCJpYXQiOjE0NDQ0MTUyNTB9.B3sLXQmyqrJCHe7j9kjY0Ar8Z15d4BIIt16ZNpclnVAhvWT1J4IpqGzQHaZOo6etFIq7AQcmbyMcBm9PPswJUZTPIzn3Zst8obpsil_uIHOo08tGbof9F6Z0oijyZUy9s5kxqfqQMtBTQPXwGKzjXNX6nxe2Gc5K0OAISYeXxNRc4TOtlC7XGKAQ3I8f89QFsL2TQQSkJ2Rn39SY7nPgdUeeuB_bbypDBSDofOnnQ7ru3eQ-r8dRdcCjq4AVdoCfwlwl8AP9RBPdhMspXm43nI2NjSGZ-ivrI7v9vOpJsYF8cmcTM4fjURh6Fb7Vf-6FCxwVDjOg1BPBAI63EfNbMv9eiH4B0i2wRR8l0FvxX_8IoYeAp-4B28Uk8xgc_0UNeg2tJCroDFJxt_-NYvvCy6TUJeVDGDCjjVWZfleO8euC-kEgykkNRWXbbbZSZtJuuV1r2cx0ohXUwEZYrFkaRbBxfsxMNIBRC2w3Bow-ofP3mM2vjDUL916tbnvpukdJ9bYC3C5R_nuDnxvlsUsKqmpcFgEZR5oFWZRUoqsXSy_ZAVYjBF97clDzP-QbslAA60HgIW-Djsi2DzGinjodrIRzJdnUo-V6FKt1wkXeZOfnlZQrVlXg6RFJt5gZ3Yjs-_KqABrafkjt9UT8ogGW4Ulllr1iVsjjNR1OeCys-Gs"));

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
