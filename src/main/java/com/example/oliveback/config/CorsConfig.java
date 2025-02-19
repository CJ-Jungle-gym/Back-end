package com.example.oliveback.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
//
//    @Value("${cors.allowedOrigins}")
//    private String frontendUrl;
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                // 모든 API에 대해 CORS 설정
//                registry.addMapping("/**") // 모든 API 경로에 대해
//                        .allowedOrigins(frontendUrl) // React 앱이 실행되는 URL
//                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드
//                        .allowedHeaders("*") // 모든 헤더 허용
//                        .allowCredentials(true); // 자격 증명 허용
//            }
//        };
//    }
}

