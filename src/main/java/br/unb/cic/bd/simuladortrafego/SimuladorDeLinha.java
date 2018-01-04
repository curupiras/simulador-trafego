package br.unb.cic.bd.simuladortrafego;

import java.util.Calendar;
import java.util.List;

import br.unb.cic.bd.simuladortrafego.grafo.Arco;
import br.unb.cic.bd.simuladortrafego.grafo.No;
import br.unb.cic.bd.simuladortrafego.grafo.StatusEnum;
import br.unb.cic.bd.simuladortrafego.linha.Linha;

public class SimuladorDeLinha implements Runnable {

	private Linha linha;

	public SimuladorDeLinha(Linha linha) {
		this.linha = linha;
	}

	public synchronized void run() {
		atualizaStatusDosArcos();
		atualizaVelocidadesDosArcos();
		atualizaAtrasosDosNos();
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

		return StatusEnum.NORMAL;
	}

	private void atualizaVelocidadesDosArcos() {
		List<Arco> arcos = linha.getArcos();
		for (Arco arco : arcos) {
			double velocidadeMaxima = arco.getVelocidadeMaxima();
			StatusEnum status = arco.getStatus();
			double fatorOscilacao = Parametros.FATOR_DE_OSCILACAO_DA_VELOCIDADE;
			double velocidade;
			velocidade = velocidadeMaxima * status.fatorDeCorrecao();
			if (isHorarioDePico()) {
				velocidade = velocidade * Parametros.FATOR_DE_CORRECAO_HORARIO_DE_PICO;
			}
			velocidade = velocidade * ((1 - fatorOscilacao) + fatorOscilacao * Math.random() * 2);
			arco.setVelocidade(velocidade);
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

	private boolean isHorarioDePico(){
		Calendar inferiorMatutino =Calendar.getInstance();
		inferiorMatutino.set(Calendar.HOUR, Parametros.LIMITE_INFERIOR_HORA_DE_PICO_MATUTINO);
		inferiorMatutino.set(Calendar.MINUTE, Parametros.LIMITE_INFERIOR_MINUTO_DE_PICO_MATUTINO);
		inferiorMatutino.set(Calendar.SECOND, 0);
		
		Calendar superiorMatutino =Calendar.getInstance();
		superiorMatutino.set(Calendar.HOUR, Parametros.LIMITE_SUPERIOR_HORA_DE_PICO_MATUTINO);
		superiorMatutino.set(Calendar.MINUTE, Parametros.LIMITE_SUPERIOR_MINUTO_DE_PICO_MATUTINO);
		superiorMatutino.set(Calendar.SECOND, 0);
		
		Calendar inferiorVespertino =Calendar.getInstance();
		inferiorVespertino.set(Calendar.HOUR, Parametros.LIMITE_INFERIOR_HORA_DE_PICO_VESPERTINO);
		inferiorVespertino.set(Calendar.MINUTE, Parametros.LIMITE_INFERIOR_MINUTO_DE_PICO_VESPERTINO);
		inferiorVespertino.set(Calendar.SECOND, 0);
		
		Calendar superiorVespertino =Calendar.getInstance();
		superiorVespertino.set(Calendar.HOUR, Parametros.LIMITE_SUPERIOR_HORA_DE_PICO_VESPERTINO);
		superiorVespertino.set(Calendar.MINUTE, Parametros.LIMITE_SUPERIOR_MINUTO_DE_PICO_VESPERTINO);
		superiorVespertino.set(Calendar.SECOND, 0);
		
		Calendar agora = Calendar.getInstance();
		
		if(agora.compareTo(inferiorMatutino) < 0 && agora.compareTo(superiorMatutino) > 0 ){
			return true;
		}
		
		if(agora.compareTo(inferiorVespertino) < 0 && agora.compareTo(superiorVespertino) > 0 ){
			return true;
		}
		
		return false;
		
	}

}
