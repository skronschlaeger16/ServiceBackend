package com.smartsced.servicebackend;

import com.smartsced.servicebackend.DataService.ServiceDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


    @Configuration
    public class AppConfiguration {
        @Bean
        public ServiceDataService createDataService() {
            ServiceDataService d = new ServiceDataService();

            return d;
        }
}
