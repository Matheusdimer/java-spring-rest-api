package com.betha.cursomc.config;

import com.betha.cursomc.domain.PagamentoComBoleto;
import com.betha.cursomc.domain.PagamentoComCartao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;

@Configuration
public class JacksonConfig {
// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PagamentoComCartao.class);
                objectMapper.registerSubtypes(PagamentoComBoleto.class);
                SimpleDateFormat format = new SimpleDateFormat("aaaa-MM-dd'T'hh:mm:ss.SSS");
                objectMapper.setDateFormat(format);
                super.configure(objectMapper);
            }
        };
    }
}
