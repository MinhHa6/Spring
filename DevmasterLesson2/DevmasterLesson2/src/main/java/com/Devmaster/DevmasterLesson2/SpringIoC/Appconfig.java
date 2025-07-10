package com.Devmaster.DevmasterLesson2.SpringIoC;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Appconfig {
    @Bean
    public String appNAme()
    {
        return "<h1>Vien Cong nghe</h1><h1>Spring BootApplication";
    }
}
