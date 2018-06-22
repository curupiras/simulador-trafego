package br.unb.cic.simuladortrafego;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.unb.cic.simuladortrafego.grafo.Arco;
import br.unb.cic.simuladortrafego.grafo.ArcoRepository;
import br.unb.cic.simuladortrafego.grafo.InfluenciaEnum;
import br.unb.cic.simuladortrafego.grafo.No;
import br.unb.cic.simuladortrafego.grafo.StatusEnum;
import br.unb.cic.simuladortrafego.linha.Linha;

@Component
@Scope("prototype")
public class SimuladorDeLinha {

	@Value("${simulador.probabilidadeDeOcorrenciaDeEventoGrave}")
	private double probabilidadeDeOcorrenciaDeEventoGrave;
	@Value("${simulador.probabilidadeDeOcorrenciaDeEventoLeve}")
	private double probabilidadeDeOcorrenciaDeEventoLeve;
	@Value("${simulador.probabilidadeDeOcorrenciaDeEventoModerado}")
	private double probabilidadeDeOcorrenciaDeEventoModerado;

	@Value("${simulador.probabilidadeDeEncerramentoDeEventoGrave}")
	private double probabilidadeDeEncerramentoDeEventoGrave;
	@Value("${simulador.probabilidadeDeEncerramentoDeEventoModerado}")
	private double probabilidadeDeEncerramentoDeEventoModerado;
	@Value("${simulador.probabilidadeDeEncerramentoDeEventoLeve}")
	private double probabilidadeDeEncerramentoDeEventoLeve;

	@Value("${simulador.limiteInferiorHoraDePicoMatutino}")
	private int limiteInferiorHoraDePicoMatutino;
	@Value("${simulador.limiteInferiorMinutoDePicoMatutino}")
	private int limiteInferiorMinutoDePicoMatutino;
	@Value("${simulador.limiteSuperiorHoraDePicoMatutino}")
	private int limiteSuperiorHoraDePicoMatutino;
	@Value("${simulador.limiteSuperiorMinutoDePicoMatutino}")
	private int limiteSuperiorMinutoDePicoMatutino;

	@Value("${simulador.limiteInferiorHoraDePicoVespertino}")
	private int limiteInferiorHoraDePicoVespertino;
	@Value("${simulador.limiteInferiorMinutoDePicoVespertino}")
	private int limiteInferiorMinutoDePicoVespertino;
	@Value("${simulador.limiteSuperiorHoraDePicoVespertino}")
	private int limiteSuperiorHoraDePicoVespertino;
	@Value("${simulador.limiteSuperiorMinutoDePicoVespertino}")
	private int limiteSuperiorMinutoDePicoVespertino;

	@Value("${simulador.atrasoNaParada}")
	private double atrasoNaParada;

	@Value("${simulador.fatorDeOscilacaoDoAtraso}")
	private double fatorDeOscilacaoDoAtraso;
	@Value("${simulador.fatorDeOscilacaoDoAtrasoDesvioPadrao}")
	private double fatorDeOscilacaoDoAtrasoDesvioPadrao;

	@Value("${simulador.fatorDeCorrecaoHorarioDePico}")
	private double fatorDeCorrecaoHorarioDePico;
	@Value("${simulador.fatorDeCorrecaoHorarioDePicoDesvioPadrao}")
	private double fatorDeCorrecaoHorarioDePicoDesvioPadrao;

	@Value("${simulador.fatorDeCorrecaoNormal}")
	private double fatorDeCorrecaoNormal;
	@Value("${simulador.fatorDeCorrecaoLeve}")
	private double fatorDeCorrecaoLeve;
	@Value("${simulador.fatorDeCorrecaoModerado}")
	private double fatorDeCorrecaoModerado;
	@Value("${simulador.fatorDeCorrecaoGrave}")
	private double fatorDeCorrecaoGrave;
	@Value("${simulador.fatorDeCorrecaoDesvioPadrao}")
	private double fatorDeCorrecaoDesvioPadrao;

	@Value("${simulador.fatorDeInfluenciaAusente}")
	private double fatorDeInfluenciaAusente;
	@Value("${simulador.fatorDeInfluenciaLeve}")
	private double fatorDeInfluenciaLeve;
	@Value("${simulador.fatorDeInfluenciaModerado}")
	private double fatorDeInfluenciaModerado;
	@Value("${simulador.fatorDeInfluenciaForte}")
	private double fatorDeInfluenciaForte;
	@Value("${simulador.fatorDeInfluenciaDesvioPadrao}")
	private double fatorDeInfluenciaDesvioPadrao;

	private static final Logger logger = Logger.getLogger(SimuladorDeLinha.class.getName());
	private static final long PERIODO_DE_ATUALIZACAO_DE_LINHA_EM_MS = 30000;
	private static final long ATRASO_DE_ATUALIZACAO_DE_LINHA_EM_MS = 5000;

	private Linha linha;

	@Autowired
	private ArcoRepository arcoRepository;

	public SimuladorDeLinha(Linha linha) {
		this.linha = linha;
	}

	@Scheduled(initialDelay = ATRASO_DE_ATUALIZACAO_DE_LINHA_EM_MS, fixedRate = PERIODO_DE_ATUALIZACAO_DE_LINHA_EM_MS)
	public synchronized void scheduledTask() {
		logger.debug("Início da simulação de linha.");
		atualizaStatusDosArcos();
		atualizaInfluenciaDeVisinhos();
		atualizaVelocidadesDosArcos();
		atualizaAtrasosDosNos();
		atualizaVelocidadesNoBanco();
		logger.debug("Fim da simulação de linha.");
	}

	private void atualizaVelocidadesNoBanco() {
		arcoRepository.save(linha.getArcos());
	}

	private void atualizaStatusDosArcos() {
		List<Arco> arcos = linha.getArcos();
		for (Arco arco : arcos) {
			double evento = Math.random();
			StatusEnum status = arco.getStatus();

			if (status == StatusEnum.NORMAL) {
				status = atualizarStatus(status, evento);
			} else {
				status = regredirStatus(status, evento);
			}

			arco.setStatus(status);
		}
	}

	private void atualizaInfluenciaDeVisinhos() {
		List<Arco> arcos = linha.getArcos();
		for (Arco arco : arcos) {
			if (arco.getStatus() == StatusEnum.EVENTO_GRAVE) {
				continue;
			}
			Arco proximoArco = arco.getProximoArco();
			Arco aposProximoArco = proximoArco.getProximoArco();
			Arco arcoAnterior = arco.getArcoAnterior();

			if (proximoArco.getStatus() == StatusEnum.EVENTO_GRAVE) {
				arco.setInfluencia(InfluenciaEnum.INFLUENCIA_FORTE);
			} else if (aposProximoArco.getStatus() == StatusEnum.EVENTO_GRAVE) {
				arco.setInfluencia(InfluenciaEnum.INFLUENCIA_MODERADA);
			} else if (arcoAnterior.getStatus() == StatusEnum.EVENTO_GRAVE) {
				arco.setInfluencia(InfluenciaEnum.INFLUENCIA_LEVE);
			} else {
				arco.setInfluencia(InfluenciaEnum.INFLUENCIA_AUSENTE);
			}
		}
	}

	private StatusEnum atualizarStatus(StatusEnum status, double evento) {
		if (evento < probabilidadeDeOcorrenciaDeEventoGrave) {
			return StatusEnum.EVENTO_GRAVE;
		} else if (evento < probabilidadeDeOcorrenciaDeEventoModerado) {
			return StatusEnum.EVENTO_MODERADO;
		} else if (evento < probabilidadeDeOcorrenciaDeEventoLeve) {
			return StatusEnum.EVENTO_LEVE;
		}

		return StatusEnum.NORMAL;
	}

	private StatusEnum regredirStatus(StatusEnum status, double evento) {

		if (status == StatusEnum.NORMAL) {
			return status;
		}

		if (status == StatusEnum.EVENTO_GRAVE) {
			if (evento < probabilidadeDeEncerramentoDeEventoGrave) {
				return StatusEnum.EVENTO_MODERADO;
			}
		} else if (status == StatusEnum.EVENTO_MODERADO) {
			if (evento < probabilidadeDeEncerramentoDeEventoModerado) {
				return StatusEnum.EVENTO_LEVE;
			}
		} else if (status == StatusEnum.EVENTO_LEVE) {
			if (evento < probabilidadeDeEncerramentoDeEventoLeve) {
				return StatusEnum.NORMAL;
			}
		}

		return status;
	}

	private void atualizaVelocidadesDosArcos() {
		List<Arco> arcos = linha.getArcos();
		for (Arco arco : arcos) {
			double velocidadeMaxima = arco.getVelocidadeMaxima();
			StatusEnum status = arco.getStatus();
			InfluenciaEnum influencia = arco.getInfluencia();
			double velocidade;

			velocidade = velocidadeMaxima * getFatorCorrecaoStatus(status);

			if (isHorarioDePico()) {
				velocidade = velocidade * getFatorCorrecaoHorarioDePico();
			}

			velocidade = velocidade * getFatorInfluencia(influencia);

			arco.setVelocidadeMedia(velocidade);
		}
	}

	private double getFatorCorrecaoHorarioDePico() {

		double corretorLinearDaMedia;

		Calendar agora = Calendar.getInstance();

		Calendar inferiorMatutino = Calendar.getInstance();
		inferiorMatutino.set(Calendar.HOUR_OF_DAY, limiteInferiorHoraDePicoMatutino);
		inferiorMatutino.set(Calendar.MINUTE, limiteInferiorMinutoDePicoMatutino);
		inferiorMatutino.set(Calendar.SECOND, 0);

		Calendar superiorMatutino = Calendar.getInstance();
		superiorMatutino.set(Calendar.HOUR_OF_DAY, limiteSuperiorHoraDePicoMatutino);
		superiorMatutino.set(Calendar.MINUTE, limiteSuperiorMinutoDePicoMatutino);
		superiorMatutino.set(Calendar.SECOND, 0);

		Calendar inferiorVespertino = Calendar.getInstance();
		inferiorVespertino.set(Calendar.HOUR_OF_DAY, limiteInferiorHoraDePicoVespertino);
		inferiorVespertino.set(Calendar.MINUTE, limiteInferiorMinutoDePicoVespertino);
		inferiorVespertino.set(Calendar.SECOND, 0);

		Calendar superiorVespertino = Calendar.getInstance();
		superiorVespertino.set(Calendar.HOUR_OF_DAY, limiteSuperiorHoraDePicoVespertino);
		superiorVespertino.set(Calendar.MINUTE, limiteSuperiorMinutoDePicoVespertino);
		superiorVespertino.set(Calendar.SECOND, 0);

		double horaAtual = agora.get(Calendar.HOUR_OF_DAY);
		double minutoAtual = agora.get(Calendar.MINUTE);
		double minutosAgora = horaAtual * 60 + minutoAtual;
		double a = 0;
		double b = 0;

		if (agora.compareTo(inferiorMatutino) > 0 && agora.compareTo(superiorMatutino) < 0) {
			double minutosInferiorMatutino = limiteInferiorHoraDePicoMatutino * 60 + limiteInferiorMinutoDePicoMatutino;
			double minutosSuperiorMatutino = limiteSuperiorHoraDePicoMatutino * 60 + limiteSuperiorMinutoDePicoMatutino;
			double minutosCentralMatutino = (minutosSuperiorMatutino + minutosInferiorMatutino) / 2;

			if (minutosAgora < minutosCentralMatutino) {
				b = (fatorDeCorrecaoHorarioDePico * minutosInferiorMatutino - minutosCentralMatutino)
						/ (minutosInferiorMatutino - minutosCentralMatutino);
				a = (1 - b) / minutosInferiorMatutino;
			} else {
				b = (fatorDeCorrecaoHorarioDePico * minutosSuperiorMatutino - minutosCentralMatutino)
						/ (minutosSuperiorMatutino - minutosCentralMatutino);
				a = (1 - b) / minutosSuperiorMatutino;
			}
		} else if (agora.compareTo(inferiorVespertino) > 0 && agora.compareTo(superiorVespertino) < 0) {
			double minutosInferiorVespertino = limiteInferiorHoraDePicoVespertino * 60
					+ limiteInferiorMinutoDePicoVespertino;
			double minutosSuperiorVespertino = limiteSuperiorHoraDePicoVespertino * 60
					+ limiteSuperiorMinutoDePicoVespertino;
			double minutosCentralVespertino = (minutosSuperiorVespertino + minutosInferiorVespertino) / 2;

			if (minutosAgora < minutosCentralVespertino) {
				b = (fatorDeCorrecaoHorarioDePico * minutosInferiorVespertino - minutosCentralVespertino)
						/ (minutosInferiorVespertino - minutosCentralVespertino);
				a = (1 - b) / minutosInferiorVespertino;
			} else {
				b = (fatorDeCorrecaoHorarioDePico * minutosSuperiorVespertino - minutosCentralVespertino)
						/ (minutosSuperiorVespertino - minutosCentralVespertino);
				a = (1 - b) / minutosSuperiorVespertino;
			}
		}

		corretorLinearDaMedia = a * minutosAgora + b;
		Random random = new Random();
		return random.nextGaussian() * fatorDeCorrecaoHorarioDePicoDesvioPadrao + corretorLinearDaMedia;
	}

	private void atualizaAtrasosDosNos() {
		List<No> nos = linha.getNos();
		for (No no : nos) {
			double atraso = atrasoNaParada;
			atraso = atraso * getFatorOscilacaoAtraso();
			no.setAtraso(atraso);
		}
	}

	private boolean isHorarioDePico() {

		Calendar agora = Calendar.getInstance();

		if (isFimDeSemana(agora)) {
			return false;
		}

		Calendar inferiorMatutino = Calendar.getInstance();
		inferiorMatutino.set(Calendar.HOUR_OF_DAY, limiteInferiorHoraDePicoMatutino);
		inferiorMatutino.set(Calendar.MINUTE, limiteInferiorMinutoDePicoMatutino);
		inferiorMatutino.set(Calendar.SECOND, 0);

		Calendar superiorMatutino = Calendar.getInstance();
		superiorMatutino.set(Calendar.HOUR_OF_DAY, limiteSuperiorHoraDePicoMatutino);
		superiorMatutino.set(Calendar.MINUTE, limiteSuperiorMinutoDePicoMatutino);
		superiorMatutino.set(Calendar.SECOND, 0);

		Calendar inferiorVespertino = Calendar.getInstance();
		inferiorVespertino.set(Calendar.HOUR_OF_DAY, limiteInferiorHoraDePicoVespertino);
		inferiorVespertino.set(Calendar.MINUTE, limiteInferiorMinutoDePicoVespertino);
		inferiorVespertino.set(Calendar.SECOND, 0);

		Calendar superiorVespertino = Calendar.getInstance();
		superiorVespertino.set(Calendar.HOUR_OF_DAY, limiteSuperiorHoraDePicoVespertino);
		superiorVespertino.set(Calendar.MINUTE, limiteSuperiorMinutoDePicoVespertino);
		superiorVespertino.set(Calendar.SECOND, 0);

		if (agora.compareTo(inferiorMatutino) > 0 && agora.compareTo(superiorMatutino) < 0) {
			return true;
		}

		if (agora.compareTo(inferiorVespertino) > 0 && agora.compareTo(superiorVespertino) < 0) {
			return true;
		}

		return false;

	}

	private double getFatorCorrecaoStatus(StatusEnum status) {
		double fator = fatorDeCorrecaoNormal;

		if (status == StatusEnum.NORMAL) {
			fator = fatorDeCorrecaoNormal;
		} else if (status == StatusEnum.EVENTO_LEVE) {
			fator = fatorDeCorrecaoLeve;
		} else if (status == StatusEnum.EVENTO_MODERADO) {
			fator = fatorDeCorrecaoModerado;
		} else if (status == StatusEnum.EVENTO_GRAVE) {
			fator = fatorDeCorrecaoGrave;
		}

		Random random = new Random();
		return random.nextGaussian() * fatorDeCorrecaoDesvioPadrao + fator;
	}

	private double getFatorInfluencia(InfluenciaEnum influencia) {
		double fator = fatorDeInfluenciaAusente;

		if (influencia == InfluenciaEnum.INFLUENCIA_AUSENTE) {
			fator = fatorDeInfluenciaAusente;
		} else if (influencia == InfluenciaEnum.INFLUENCIA_LEVE) {
			fator = fatorDeInfluenciaLeve;
		} else if (influencia == InfluenciaEnum.INFLUENCIA_MODERADA) {
			fator = fatorDeInfluenciaModerado;
		} else if (influencia == InfluenciaEnum.INFLUENCIA_FORTE) {
			fator = fatorDeInfluenciaForte;
		}

		Random random = new Random();
		return random.nextGaussian() * fatorDeInfluenciaDesvioPadrao + fator;
	}

	private double getFatorOscilacaoAtraso() {
		Random random = new Random();
		return random.nextGaussian() * fatorDeOscilacaoDoAtrasoDesvioPadrao + fatorDeOscilacaoDoAtraso;
	}

	private boolean isFimDeSemana(Calendar calendar) {
		int diaDaSemana = calendar.get(Calendar.DAY_OF_WEEK);
		if (diaDaSemana == Calendar.SATURDAY || diaDaSemana == Calendar.SUNDAY) {
			return true;
		}
		return false;

	}

}
