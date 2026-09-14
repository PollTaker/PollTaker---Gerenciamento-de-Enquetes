package com.lucaschalita.polltaker.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2ServerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer() throws SQLException {
        // Inicia o console do H2 em uma porta completamente separada (8082)
        // Isso ignora qualquer bloqueio de rota, iframes ou conflitos com o Spring Security.
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }
}