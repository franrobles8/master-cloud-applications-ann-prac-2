package es.codeurjc.books;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@SpringBootApplication
public class Application {

    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper(Arrays.asList("dozer_mapping.xml"));
    }

    public static void main(String[] args) {
        RetryTemplate template = new RetryTemplate();
		AlwaysRetryPolicy policy = new AlwaysRetryPolicy();
		
		template.setRetryPolicy(policy);
		template.execute(context ->
			{
				SpringApplication.run(Application.class, args);
				return true;
			});
    }

    @Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
