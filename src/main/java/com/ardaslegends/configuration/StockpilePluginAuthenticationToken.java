package com.ardaslegends.configuration;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class StockpilePluginAuthenticationToken extends AbstractAuthenticationToken {

    public StockpilePluginAuthenticationToken(Object details) {
        super(AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        setAuthenticated(true);
        setDetails(details);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}