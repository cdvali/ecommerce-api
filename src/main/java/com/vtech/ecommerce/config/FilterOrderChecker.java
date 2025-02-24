package com.vtech.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class FilterOrderChecker {

    @Autowired
    private FilterChainProxy filterChainProxy;

    @PostConstruct
    public void printFilterOrder() {
        System.out.println("======= Security Filter Order =======");
        for (SecurityFilterChain chain : filterChainProxy.getFilterChains()) {
            chain.getFilters().forEach(filter -> 
                System.out.println(filter.getClass().getSimpleName())
            );
        }
        System.out.println("=====================================");
    }
}
