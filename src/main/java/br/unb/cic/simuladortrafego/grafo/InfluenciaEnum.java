package br.unb.cic.simuladortrafego.grafo;

public enum InfluenciaEnum {

	INFLUENCIA_FORTE("Influência Forte"),
	INFLUENCIA_MODERADA("Influência Moderada"),
	INFLUENCIA_LEVE("Influência Leve"),
	INFLUENCIA_AUSENTE("Influência Ausente");

	private String nome;

	InfluenciaEnum(String nome) {
		this.nome = nome;
	}

	public String nome() {
		return this.nome;
	}

}
