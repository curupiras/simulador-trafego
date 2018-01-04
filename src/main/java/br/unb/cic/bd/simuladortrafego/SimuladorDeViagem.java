package br.unb.cic.bd.simuladortrafego;

import java.util.Date;

import br.unb.cic.bd.simuladortrafego.onibus.Onibus;
import br.unb.cic.bd.simuladortrafego.onibus.PosicaoDao;

public class SimuladorDeViagem implements Runnable {
	
	private PosicaoDao posicaoDao;
	private Onibus onibus;

	public SimuladorDeViagem(Onibus onibus) {
		this.onibus = onibus;
		posicaoDao = new PosicaoDao();
	}

	public synchronized void run() {
			atualizarPosicao();
			long chave = posicaoDao.inserePosicao(onibus);
			posicaoDao.atualizaLatitudeLongitude(chave, onibus);
	}

	private void atualizarPosicao() {
		onibus.setHoraAtualizacao(new Date());
		onibus.deslocar(Parametros.PERIODO_DE_ATUALIZACAO_DO_DESLOCAMENTO_EM_SEGUNDOS);
	}

}
