package br.unb.cic.simuladortrafego;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.unb.cic.parametros.Parametros;
import br.unb.cic.simuladortrafego.onibus.Onibus;
import br.unb.cic.simuladortrafego.onibus.PosicaoDao;

@Component
@Scope("prototype")
public class SimuladorDeViagem{

	private static final Logger logger = Logger.getLogger(SimuladorDeViagem.class.getName());
	private static final long PERIODO_DE_ATUALIZACAO_DE_VIAGEM_EM_MS = 10000;
	private static final long ATRASO_DE_ATUALIZACAO_DE_VIAGEM_EM_MS = 0;

	private PosicaoDao posicaoDao;
	private Onibus onibus;

	public SimuladorDeViagem(Onibus onibus) {
		this.onibus = onibus;
		posicaoDao = new PosicaoDao();
	}

	@Scheduled(initialDelay = ATRASO_DE_ATUALIZACAO_DE_VIAGEM_EM_MS, fixedRate = PERIODO_DE_ATUALIZACAO_DE_VIAGEM_EM_MS)
	public synchronized void scheduledTask() {
		logger.debug("Início da simulação de Viagem.");
		long chave = 0;
		synchronized (onibus) {
			atualizarPosicao();
			atualizarVelocidade();
			chave = posicaoDao.inserePosicao(onibus);
			
			if(chave == 0){
				logger.info("Problema ao inserrir posição.");
			}
			
			posicaoDao.atualizaLatitudeLongitude(chave, onibus);
		}
		logger.debug("Fim da simulação de Viagem.");
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
