package com.pichincha.prueba.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;

@Configuration
@EnableTransactionManagement
public class SpecificationResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SpecificationArgumentResolver());
    }

}
