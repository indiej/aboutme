package com.concur;

import java.net.URI;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestTemplate;



public class DoronAutenticationWithToken implements Authentication {

    private final static Logger logger = LoggerFactory.getLogger(DoronAuthenticationFilter.class);
    
    @Autowired
    Environment env; //FIXME - why it's not getting injected? Does this class needs to be a bean?

    boolean authenticated;
    public DoronAutenticationWithToken(String token){
        logger.info("DoronAutenticationWithToken make network call");
        RestTemplate restTemplate = new RestTemplate();
        URI uri = null;
        
        //String jwtUrl = env.getProperty("com.concur.auth.jwt");
        String jwtUrl = "https://jwt-verifier.concur.com";
        
        try {
            uri = new URI(jwtUrl + "/?jwt="+token);
        }catch (Exception e){
            //oops
            this.setAuthenticated(false);
        }

        //String result = restTemplate.postForObject(API_URL + "account/authenticate/?email="+ AUTH_USER +"&token="+ AUTH_PASS, null, String.class);
        String result =  restTemplate.getForObject(uri, String.class);

        if (result.contains("email")) {
            this.setAuthenticated(true);
        } else {
            this.setAuthenticated(false);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        logger.info("DoronAutenticationWithToken isAuthenticated");
        //make authentication call
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
