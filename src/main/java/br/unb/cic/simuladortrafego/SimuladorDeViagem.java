package br.unb.cic.simuladortrafego;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.unb.cic.simuladortrafego.onibus.Motor;
import br.unb.cic.simuladortrafego.onibus.Onibus;
import br.unb.cic.simuladortrafego.onibus.PosicaoDao;
import br.unb.cic.simuladortrafego.util.Util;

@Component
@Scope("prototype")
public class SimuladorDeViagem {

	@Value("#{${simulador.periodoDeAtualizacaoDoDeslocamentoEmMilisegundos} / 1000}")
	private long periodoDeAtualizacaoDoDeslocamentoEmSegundos;

	@Autowired
	private Motor motor;

	@Autowired
	private PosicaoDao posicaoDao;

	@Autowired
	private Util util;

	@Autowired
	ControladorDoTempo controladorDoTempo;

	private static final Logger logger = Logger.getLogger(SimuladorDeViagem.class.getName());

	private Onibus onibus;

	public SimuladorDeViagem(Onibus onibus) {
		this.onibus = onibus;
	}

	@Scheduled(fixedRateString = "#{${simulador.periodoDeAtualizacaoDoDeslocamentoEmMilisegundos} / ${simulador.multiplicadorDoTempo}}")
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
		onibus.setHoraAtualizacao(controladorDoTempo.getDate());
		motor.deslocar(onibus, periodoDeAtualizacaoDoDeslocamentoEmSegundos);
	}

	private void atualizarVelocidade() {
		util.atualizarVelocidade(onibus);
	}

}
