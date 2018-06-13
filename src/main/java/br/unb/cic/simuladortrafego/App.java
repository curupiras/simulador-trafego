package br.unb.cic.simuladortrafego;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import br.unb.cic.simuladortrafego.linha.Linha;
import br.unb.cic.simuladortrafego.onibus.Onibus;

@EnableScheduling
@SpringBootApplication
@Configuration
public class App {
	
	@Value("${simulador.tamanhoDaFrota}")
	private int tamanhoDaFrota;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(tamanhoDaFrota+1);
		return threadPoolTaskScheduler;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@Scope("prototype")
	public SimuladorDeLinha simuladorDeLinha(Linha linha) {
		return new SimuladorDeLinha(linha);
	}

	@Bean
	@Scope("prototype")
	public SimuladorDeViagem simuladorDeViagem(Onibus onibus) {
		return new SimuladorDeViagem(onibus);
	}
	
}