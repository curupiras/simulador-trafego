package br.unb.cic.simuladortrafego.grafo;

import br.unb.cic.parametros.Parametros;

public enum StatusEnum {

	NORMAL("Normal", Parametros.FATOR_DE_CORRECAO_NORMAL),
	EVENTO_GRAVE("Evento Grave", Parametros.FATOR_DE_CORRECAO_GRAVE),
	EVENTO_MODERADO("Evento Moderado", Parametros.FATOR_DE_CORRECAO_MODERADO),
	EVENTO_LEVE("Evento Leve", Parametros.FATOR_DE_CORRECAO_LEVE);

	private String nome;
	private double fatorDeCorrecao;

	StatusEnum(String nome, double fator) {
		this.nome = nome;
		this.fatorDeCorrecao = fator;
	}

	public String nome() {
		return this.nome;
	}

	public double fatorDeCorrecao() {
		return this.fatorDeCorrecao;
	}

}