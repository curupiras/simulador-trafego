package br.unb.cic.simuladortrafego.util;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.unb.cic.simuladortrafego.onibus.Onibus;

@Component
public class Util {

	@Value("${simulador.fatorDeOscilacaoDaVelocidade}")
	private double fatorDeOscilacaoDaVelocidade;
	@Value("${simulador.fatorDeOscilacaoDaVelocidadeDesvioPadrao}")
	private double fatorDeOscilacaoDaVelocidadeDesvioPadrao;

	private static final Logger logger = Logger.getLogger(Util.class.getName());
	
	@PostConstruct
	public void informaParametros() {
		logger.info("fatorDeOscilacaoDaVelocidadeDesvioPadrao: " + fatorDeOscilacaoDaVelocidadeDesvioPadrao);
	}

	public void atualizarVelocidade(Onibus onibus) {
		double fatorOscilacao = getFatorOscilacaoVelocidade();
		double velocidadeMediaDoArco = onibus.getElementoGrafo().getVelocidadeMedia();
		double velocidade = velocidadeMediaDoArco * fatorOscilacao;
		onibus.setVelocidade(velocidade);
	}

	public double getFatorOscilacaoVelocidade() {
		Random random = new Random();
		return random.nextGaussian() * fatorDeOscilacaoDaVelocidadeDesvioPadrao + fatorDeOscilacaoDaVelocidade;
	}

}
