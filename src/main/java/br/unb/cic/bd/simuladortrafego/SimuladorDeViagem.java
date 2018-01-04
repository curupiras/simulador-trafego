package br.unb.cic.bd.simuladortrafego;

import java.util.Date;

import br.unb.cic.bd.simuladortrafego.onibus.Onibus;
import br.unb.cic.bd.simuladortrafego.onibus.PosicaoDao;

public class SimuladorDeViagem implements Runnable {

	private Onibus onibus;

	public SimuladorDeViagem(Onibus onibus) {
		this.onibus = onibus;
	}

	public void run() {
		System.out.println(onibus);
		atualizarPosicao(onibus);

		PosicaoDao posicaoDao = PosicaoDao.getInstance();
		long chave = posicaoDao.inserePosicao(onibus);
		posicaoDao.atualizaLatitudeLongitude(chave, onibus);
	}

	private void atualizarPosicao(Onibus onibus) {
		onibus.setHoraAtualizacao(new Date());
		onibus.deslocar(Parametros.PERIODO_DE_DESLOCAMENTO_EM_SEGUNDOS);
	}

}
