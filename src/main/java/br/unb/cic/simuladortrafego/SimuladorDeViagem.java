package br.unb.cic.simuladortrafego;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.unb.cic.simuladortrafego.onibus.Motor;
import br.unb.cic.simuladortrafego.onibus.Onibus;
import br.unb.cic.simuladortrafego.onibus.PosicaoDao;

@Component
@Scope("prototype")
public class SimuladorDeViagem {

	@Value("${simulador.periodoDeAtualizacaoDoDeslocamentoEmSegundos}")
	private long periodoDeAtualizacaoDoDeslocamentoEmSegundos;

	@Value("${simulador.fatorDeOscilacaoDaVelocidade}")
	private double fatorDeOscilacaoDaVelocidade;
	
	@Autowired
	private Motor motor;
	
	@Autowired
	private PosicaoDao posicaoDao;

	private static final Logger logger = Logger.getLogger(SimuladorDeViagem.class.getName());
	private static final long PERIODO_DE_ATUALIZACAO_DE_VIAGEM_EM_MS = 10000;
	private static final long ATRASO_DE_ATUALIZACAO_DE_VIAGEM_EM_MS = 0;

	private Onibus onibus;

	public SimuladorDeViagem(Onibus onibus) {
		this.onibus = onibus;
	}

	@Scheduled(initialDelay = ATRASO_DE_ATUALIZACAO_DE_VIAGEM_EM_MS, fixedRate = PERIODO_DE_ATUALIZACAO_DE_VIAGEM_EM_MS)
	public synchronized void scheduledTask() {
		logger.debug("Início da simulação de Viagem.");
		synchronized (onibus) {
			atualizarPosicao();
			atualizarVelocidade();
			posicaoDao.inserePosicao(onibus);
		}
		logger.debug("Fim da simulação de Viagem.");
	}

	private void atualizarPosicao() {
		onibus.setHoraAtualizacao(new Date());
		motor.deslocar(onibus, periodoDeAtualizacaoDoDeslocamentoEmSegundos);
	}

	private void atualizarVelocidade() {
		double fatorOscilacao = fatorDeOscilacaoDaVelocidade;
		double velocidadeMediaDoArco = onibus.getElementoGrafo().getVelocidadeMedia();
		double velocidade = velocidadeMediaDoArco * ((1 - fatorOscilacao) + fatorOscilacao * Math.random() * 2);
		onibus.setVelocidade(velocidade);
	}

}
