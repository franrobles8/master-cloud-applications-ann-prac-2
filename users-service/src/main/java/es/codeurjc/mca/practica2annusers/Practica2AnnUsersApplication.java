package es.codeurjc.mca.practica2annusers;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Practica2AnnUsersApplication {

	public static void main(String[] args) {
		RetryTemplate template = new RetryTemplate();
		AlwaysRetryPolicy policy = new AlwaysRetryPolicy();
		
		template.setRetryPolicy(policy);
		template.execute(context ->
			{
				SpringApplication.run(Practica2AnnUsersApplication.class, args);
				return true;
			});
	}

	@Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
