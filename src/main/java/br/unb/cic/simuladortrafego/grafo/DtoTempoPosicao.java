package br.unb.cic.simuladortrafego.grafo;

public class DtoTempoPosicao {
	private double tempo;
	private double posicao;

	public DtoTempoPosicao(double tempo, double posicao) {
		super();
		this.tempo = tempo;
		this.posicao = posicao;
	}

	public double getTempo() {
		return tempo;
	}

	public void setTempo(double tempo) {
		this.tempo = tempo;
	}

	public double getPosicao() {
		return posicao;
	}

	public void setPosicao(double posicao) {
		this.posicao = posicao;
	}

}
