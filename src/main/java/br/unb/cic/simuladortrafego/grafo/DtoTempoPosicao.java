package br.unb.cic.simuladortrafego.grafo;

public class DtoTempoPosicao {
	private double tempo;
	private double posicao;
	private double velocidade;

	public DtoTempoPosicao(double tempo, double posicao, double velocidade) {
		super();
		this.tempo = tempo;
		this.posicao = posicao;
		this.velocidade = velocidade;
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

	public double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

}
