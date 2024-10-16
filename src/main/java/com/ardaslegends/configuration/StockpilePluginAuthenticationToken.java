package com.ardaslegends.configuration;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Authentication token for the Stockpile Plugin.
 * This token is used to authenticate requests coming from the Stockpile Plugin.
 * It sets the authenticated user with the role of "ROLE_ADMIN".
 */
public class StockpilePluginAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * Constructs a new StockpilePluginAuthenticationToken.
     *
     * @param details the details of the authentication request
     */
    public StockpilePluginAuthenticationToken(Object details) {
        super(AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        setAuthenticated(true);
        setDetails(details);
    }

    /**
     * Returns the credentials for this authentication token.
     * This implementation always returns null as credentials are not used.
     *
     * @return null
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Returns the principal for this authentication token.
     * This implementation always returns null as the principal is not used.
     *
     * @return null
     */
    @Override
    public Object getPrincipal() {
        return null;
    }
}