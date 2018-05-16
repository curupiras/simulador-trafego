package br.unb.cic.simuladortrafego;

import java.util.Date;

import br.unb.cic.parametros.Parametros;
import br.unb.cic.simuladortrafego.onibus.Onibus;
import br.unb.cic.simuladortrafego.onibus.PosicaoDao;

public class SimuladorDeViagem implements Runnable {
	
	private PosicaoDao posicaoDao;
	private Onibus onibus;

	public SimuladorDeViagem(Onibus onibus) {
		this.onibus = onibus;
		posicaoDao = new PosicaoDao();
	}

	public synchronized void run() {
			atualizarPosicao();
			atualizarVelocidade();
			long chave = posicaoDao.inserePosicao(onibus);
			posicaoDao.atualizaLatitudeLongitude(chave, onibus);
	}

	private void atualizarPosicao() {
		onibus.setHoraAtualizacao(new Date());
		onibus.deslocar(Parametros.PERIODO_DE_ATUALIZACAO_DO_DESLOCAMENTO_EM_SEGUNDOS);
	}
	
	private void atualizarVelocidade() {
		double fatorOscilacao = Parametros.FATOR_DE_OSCILACAO_DA_VELOCIDADE;
		double velocidadeMediaDoArco = onibus.getElementoGrafo().getVelocidadeMedia();
		double velocidade = velocidadeMediaDoArco * ((1 - fatorOscilacao) + fatorOscilacao * Math.random() * 2);
		onibus.setVelocidade(velocidade); 
	}

}
