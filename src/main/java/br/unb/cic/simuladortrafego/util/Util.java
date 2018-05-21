package br.unb.cic.simuladortrafego.util;

import br.unb.cic.parametros.Parametros;
import br.unb.cic.simuladortrafego.onibus.Onibus;

public class Util {
	
	public static void atualizarVelocidade(Onibus onibus) {
		double fatorOscilacao = Parametros.FATOR_DE_OSCILACAO_DA_VELOCIDADE;
		double velocidadeMediaDoArco = onibus.getElementoGrafo().getVelocidadeMedia();
		double velocidade = velocidadeMediaDoArco * ((1 - fatorOscilacao) + fatorOscilacao * Math.random() * 2);
		onibus.setVelocidade(velocidade);
	}
	
}
