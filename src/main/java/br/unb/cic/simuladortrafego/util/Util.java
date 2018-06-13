package br.unb.cic.simuladortrafego.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.unb.cic.simuladortrafego.onibus.Onibus;

@Component
public class Util {

	@Value("${simulador.fatorDeOscilacaoDaVelocidade}")
	private double fatorDeOscilacaoDaVelocidade;

	public void atualizarVelocidade(Onibus onibus) {
		double fatorOscilacao = fatorDeOscilacaoDaVelocidade;
		double velocidadeMediaDoArco = onibus.getElementoGrafo().getVelocidadeMedia();
		double velocidade = velocidadeMediaDoArco * ((1 - fatorOscilacao) + fatorOscilacao * Math.random() * 2);
		onibus.setVelocidade(velocidade);
	}

}
