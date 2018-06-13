package br.unb.cic.simuladortrafego.grafo;

public enum StatusEnum {

	NORMAL("Normal"),
	EVENTO_GRAVE("Evento Grave"),
	EVENTO_MODERADO("Evento Moderado"),
	EVENTO_LEVE("Evento Leve");

	private String nome;

	StatusEnum(String nome) {
		this.nome = nome;
	}

	public String nome() {
		return this.nome;
	}

}
