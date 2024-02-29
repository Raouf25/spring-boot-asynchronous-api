package com.mak.springbootasynchronousapi;

import org.apache.coyote.ProtocolHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})

@SpringBootApplication
public class SpringBootAsynchronousApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAsynchronousApiApplication.class, args);
    }

//    @Bean
//    TomcatProtocolHandlerCustomizer<ProtocolHandler> protocolHandlerVirtualThreadExecutorCustomizer() {
//        return protocolHandler ->
//            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
//    }
}
