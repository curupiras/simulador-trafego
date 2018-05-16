package br.unb.cic.simuladortrafego.grafo;

import br.unb.cic.parametros.Parametros;

public class No extends ElementoGrafo {

	private String linha;
	private String nome;
	private double atraso;
	private int numero;
	private ElementoGrafo proximo;
	private ElementoGrafo anterior;

	public No(String linha, String nome, double atraso) {
		super();
		this.linha = linha;
		this.nome = nome;
		this.atraso = atraso;
		this.numero = Integer.parseInt(nome.substring(1));
	}

	public No(String linha, String nome) {
		super();
		this.linha = linha;
		this.nome = nome;
		this.numero = Integer.parseInt(nome.substring(1));
		this.atraso = Parametros.ATRASO_NA_PARADA_EM_SEGUNDOS;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getAtraso() {
		return atraso;
	}

	public void setAtraso(double atraso) {
		this.atraso = atraso;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	@Override
	public void consomeTempo(DtoTempoPosicao tempoPosicao) {
		double tempo = tempoPosicao.getTempo();
		double posicao = tempoPosicao.getPosicao();
		double atrasoRemanescente = atraso - atraso * posicao;

		if (atrasoRemanescente <= tempo) {
			tempoPosicao.setTempo(tempo - atrasoRemanescente);
			tempoPosicao.setPosicao(1);
		} else {
			tempoPosicao.setTempo(0);
			tempoPosicao.setPosicao(posicao + tempo / atraso);
		}
	}

	public ElementoGrafo getProximo() {
		return proximo;
	}

	public void setProximo(ElementoGrafo proximo) {
		this.proximo = proximo;
	}

	public ElementoGrafo getAnterior() {
		return anterior;
	}

	public void setAnterior(ElementoGrafo anterior) {
		this.anterior = anterior;
	}

	@Override
	public double getVelocidadeMedia() {
		return 0;
	}

}
