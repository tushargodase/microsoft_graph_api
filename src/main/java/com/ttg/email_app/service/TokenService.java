package com.ttg.email_app.service;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private ClientSecretCredential credential;
    
    public String getAccessToken() {
        try {

            String[] scopes = new String[] { "https://graph.microsoft.com/.default" };

            final TokenRequestContext context = new TokenRequestContext();
            context.addScopes(scopes);
            // Get token for Microsoft Graph
            AccessToken token = credential.getToken(context).block();
            assert token != null;
            return token.getToken();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get access token", e);
        }
    }
    
    public boolean isTokenValid(String token) {
        try {
            // Get new token and compare
            String newToken = getAccessToken();
            return token.equals(newToken);
        } catch (Exception e) {
            return false;
        }
    }
}
