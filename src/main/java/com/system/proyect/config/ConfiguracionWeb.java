package com.system.proyect.config;

import com.system.proyect.Interceptor.RolInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionWeb implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Interceptor para admin
        registry.addInterceptor(new RolInterceptor("ADMINISTRADOR"))
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/webjars/**", "/error");

        // Interceptor para estudiante
        registry.addInterceptor(new RolInterceptor("ESTUDIANTE"))
                .addPathPatterns("/estudiante/**")
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/webjars/**", "/error");

        // Interceptor para perfil (cualquier usuario logueado)
        registry.addInterceptor(new RolInterceptor("ESTUDIANTE", "ADMINISTRADOR"))
                .addPathPatterns("/profile/**")
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/webjars/**", "/error");
    }
}