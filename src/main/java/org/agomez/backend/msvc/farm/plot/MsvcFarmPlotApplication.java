package org.agomez.backend.msvc.farm.plot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@EnableFeignClients
@SpringBootApplication
public class MsvcFarmPlotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcFarmPlotApplication.class, args);
	}

}
