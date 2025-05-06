package com.ttg.email_app.config;

import com.azure.identity.ClientSecretCredential;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphServiceClientConfig {

    @Autowired
    private ClientSecretCredential credential;

    @Bean
    public GraphServiceClient graphServiceClient() {
        String[] scopes = new String[] { "https://graph.microsoft.com/.default" };
        return new GraphServiceClient(credential,scopes);
        
    }
}
