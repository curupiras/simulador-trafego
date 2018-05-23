package br.unb.cic.simuladortrafego;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import br.unb.cic.parametros.Parametros;
import br.unb.cic.simuladortrafego.grafo.Arco;
import br.unb.cic.simuladortrafego.grafo.ArcoDao;
import br.unb.cic.simuladortrafego.grafo.InfluenciaEnum;
import br.unb.cic.simuladortrafego.grafo.No;
import br.unb.cic.simuladortrafego.grafo.StatusEnum;
import br.unb.cic.simuladortrafego.linha.Linha;

public class SimuladorDeLinha implements Runnable {
	
	private static final Logger logger = Logger.getLogger( SimuladorDeLinha.class.getName() );

	private Linha linha;
	private ArcoDao arcoDao;

	public SimuladorDeLinha(Linha linha) {
		this.linha = linha;
		this.arcoDao = new ArcoDao();
	}

	public synchronized void run() {
		logger.debug("Início da simulação de linha.");
		atualizaStatusDosArcos();
		atualizaInfluenciaDeVisinhos();
		atualizaVelocidadesDosArcos();
		atualizaAtrasosDosNos();
		atualizaVelocidadesNoBanco();
		logger.debug("Fim da simulação de linha.");
	}

	private void atualizaVelocidadesNoBanco() {
		arcoDao.atualizaVelocidades(linha.getArcos());
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
		if (evento < Parametros.PROBABILIDADE_DE_OCORRENCIA_DE_EVENTO_GRAVE) {
			return StatusEnum.EVENTO_GRAVE;
		} else if (evento < Parametros.PROBABILIDADE_DE_OCORRENCIA_DE_EVENTO_MODERADO) {
			return StatusEnum.EVENTO_MODERADO;
		} else if (evento < Parametros.PROBABILIDADE_DE_OCORRENCIA_DE_EVENTO_LEVE) {
			return StatusEnum.EVENTO_LEVE;
		}

		return StatusEnum.NORMAL;
	}

	private StatusEnum regredirStatus(StatusEnum status, double evento) {

		if (status == StatusEnum.NORMAL) {
			return status;
		}

		if (status == StatusEnum.EVENTO_GRAVE) {
			if (evento < Parametros.PROBABILIDADE_DE_ENCERRAMENTO_DE_EVENTO_GRAVE) {
				return StatusEnum.EVENTO_MODERADO;
			}
		} else if (status == StatusEnum.EVENTO_MODERADO) {
			if (evento < Parametros.PROBABILIDADE_DE_ENCERRAMENTO_DE_EVENTO_MODERADO) {
				return StatusEnum.EVENTO_LEVE;
			}
		} else if (status == StatusEnum.EVENTO_LEVE) {
			if (evento < Parametros.PROBABILIDADE_DE_ENCERRAMENTO_DE_EVENTO_LEVE) {
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

			velocidade = velocidadeMaxima * status.fatorDeCorrecao();

			if (isHorarioDePico()) {
				velocidade = velocidade * Parametros.FATOR_DE_CORRECAO_HORARIO_DE_PICO;
			}

			velocidade = velocidade * influencia.fatorDeCorrecao();

			arco.setVelocidadeMedia(velocidade);
		}
	}

	private void atualizaAtrasosDosNos() {
		List<No> nos = linha.getNos();
		for (No no : nos) {
			double atraso = Parametros.ATRASO_NA_PARADA_EM_SEGUNDOS;
			double fatorOscilacao = Parametros.FATOR_DE_OSCILACAO_DO_ATRASO;
			atraso = atraso * ((1 - fatorOscilacao) + fatorOscilacao * Math.random() * 2);
			no.setAtraso(atraso);
		}
	}

	private boolean isHorarioDePico() {
		Calendar inferiorMatutino = Calendar.getInstance();
		inferiorMatutino.set(Calendar.HOUR_OF_DAY, Parametros.LIMITE_INFERIOR_HORA_DE_PICO_MATUTINO);
		inferiorMatutino.set(Calendar.MINUTE, Parametros.LIMITE_INFERIOR_MINUTO_DE_PICO_MATUTINO);
		inferiorMatutino.set(Calendar.SECOND, 0);

		Calendar superiorMatutino = Calendar.getInstance();
		superiorMatutino.set(Calendar.HOUR_OF_DAY, Parametros.LIMITE_SUPERIOR_HORA_DE_PICO_MATUTINO);
		superiorMatutino.set(Calendar.MINUTE, Parametros.LIMITE_SUPERIOR_MINUTO_DE_PICO_MATUTINO);
		superiorMatutino.set(Calendar.SECOND, 0);

		Calendar inferiorVespertino = Calendar.getInstance();
		inferiorVespertino.set(Calendar.HOUR_OF_DAY, Parametros.LIMITE_INFERIOR_HORA_DE_PICO_VESPERTINO);
		inferiorVespertino.set(Calendar.MINUTE, Parametros.LIMITE_INFERIOR_MINUTO_DE_PICO_VESPERTINO);
		inferiorVespertino.set(Calendar.SECOND, 0);

		Calendar superiorVespertino = Calendar.getInstance();
		superiorVespertino.set(Calendar.HOUR_OF_DAY, Parametros.LIMITE_SUPERIOR_HORA_DE_PICO_VESPERTINO);
		superiorVespertino.set(Calendar.MINUTE, Parametros.LIMITE_SUPERIOR_MINUTO_DE_PICO_VESPERTINO);
		superiorVespertino.set(Calendar.SECOND, 0);

		Calendar agora = Calendar.getInstance();

		if (agora.compareTo(inferiorMatutino) > 0 && agora.compareTo(superiorMatutino) < 0) {
			return true;
		}

		if (agora.compareTo(inferiorVespertino) > 0 && agora.compareTo(superiorVespertino) < 0) {
			return true;
		}

		return false;

	}

}
