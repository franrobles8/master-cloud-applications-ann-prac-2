package es.codeurjc.mca.practica2annusers;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Practica2AnnUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(Practica2AnnUsersApplication.class, args);
	}

	@Bean
    public Mapper mapper() {
        return new DozerBeanMapper(Arrays.asList("dozer_mapping.xml"));
    }

}
