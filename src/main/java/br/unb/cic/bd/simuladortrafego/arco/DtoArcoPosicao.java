package br.unb.cic.bd.simuladortrafego.arco;

public class DtoArcoPosicao {
	private Arco arco;
	private double posicao;

	public DtoArcoPosicao(Arco arco, double posicao) {
		super();
		this.arco = arco;
		this.posicao = posicao;
	}

	public Arco getArco() {
		return arco;
	}

	public void setArco(Arco arco) {
		this.arco = arco;
	}

	public double getPosicao() {
		return posicao;
	}

	public void setPosicao(double posicao) {
		this.posicao = posicao;
	}

}
