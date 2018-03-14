package br.unb.cic.bd.simuladortrafego.grafo;

import br.unb.cic.bd.simuladortrafego.Parametros;

public enum InfluenciaEnum {

	INFLUENCIA_FORTE("Influência Forte", Parametros.FATOR_DE_INFLUENCIA_FORTE),
	INFLUENCIA_MODERADA("Influência Moderada", Parametros.FATOR_DE_INFLUENCIA_MODERADO),
	INFLUENCIA_LEVE("Influência Leve", Parametros.FATOR_DE_INFLUENCIA_LEVE),
	INFLUENCIA_AUSENTE("Influência Ausente", Parametros.FATOR_DE_INFLUENCIA_AUSENTE);
	

	private String nome;
	private double fatorDeCorrecao;

	InfluenciaEnum(String nome, double fator) {
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
