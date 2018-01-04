package br.unb.cic.bd.simuladortrafego.grafo;

import br.unb.cic.bd.simuladortrafego.Parametros;

public class Arco extends ElementoGrafo {

	private String nome;
	private String linha;
	private double velocidadeMedia;
	private double tamanho;
	private int numero;
	private ElementoGrafo proximo;

	public Arco(String linha, String nome, double velocidadeMedia, double tamanho) {
		super();
		this.nome = nome;
		this.linha = linha;
		this.velocidadeMedia = velocidadeMedia;
		this.tamanho = tamanho;
		this.numero = Integer.parseInt(nome.substring(1));
	}

	public Arco(String linha, String nome, double tamanho) {
		super();
		this.linha = linha;
		this.nome = nome;
		this.tamanho = tamanho;
		this.numero = Integer.parseInt(nome.substring(1));
		this.velocidadeMedia = Parametros.VELOCIDADE_MEDIA_KM_POR_HORA;
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

	public double getVelocidadeMedia() {
		return velocidadeMedia;
	}

	public void setVelocidadeMedia(double velocidadeMedia) {
		this.velocidadeMedia = velocidadeMedia;
	}

	public double getTamanho() {
		return tamanho;
	}

	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public void consomeTempo(DtoTempoPosicao tempoPosicao) {
		double tempo = tempoPosicao.getTempo();
		double posicao = tempoPosicao.getPosicao();
		double distanciaRemanescente = tamanho - tamanho * posicao;

		if (distanciaRemanescente <= calcularDistancia(tempo)) {
			tempoPosicao.setTempo(tempo - calcularTempo(distanciaRemanescente));
			tempoPosicao.setPosicao(1);
		} else {
			tempoPosicao.setTempo(0);
			tempoPosicao.setPosicao(posicao + calcularDistancia(tempo) / tamanho);
		}

	}

	private double calcularDistancia(double tempo) {
		return this.velocidadeMedia * Parametros.KILOMETROS_POR_HORA_PARA_METROS_POR_SEGUNDO * tempo;
	}

	private double calcularTempo(double distancia) {
		return distancia / (this.velocidadeMedia * Parametros.KILOMETROS_POR_HORA_PARA_METROS_POR_SEGUNDO);
	}

	public ElementoGrafo getProximo() {
		return proximo;
	}

	public void setProximo(ElementoGrafo proximo) {
		this.proximo = proximo;
	}

}
