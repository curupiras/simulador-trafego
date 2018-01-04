package br.unb.cic.bd.simuladortrafego.grafo;

import br.unb.cic.bd.simuladortrafego.Parametros;

public class Arco extends ElementoGrafo {

	private String nome;
	private String linha;
	private double velocidadeMaxima;
	private double velocidade;
	private double tamanho;
	private int numero;
	private ElementoGrafo proximo;
	private StatusEnum status;

	public Arco(String linha, String nome, double velocidadeMaxima, double tamanho) {
		super();
		this.nome = nome;
		this.linha = linha;
		this.velocidadeMaxima = velocidadeMaxima;
		this.velocidade = velocidadeMaxima;
		this.tamanho = tamanho;
		this.numero = Integer.parseInt(nome.substring(1));
		this.status = StatusEnum.NORMAL;
	}

	public Arco(String linha, String nome, double tamanho) {
		super();
		this.linha = linha;
		this.nome = nome;
		this.tamanho = tamanho;
		this.numero = Integer.parseInt(nome.substring(1));
		this.velocidadeMaxima = Parametros.VELOCIDADE_MAXIMA_60_KM_POR_HORA;
		this.velocidade = Parametros.VELOCIDADE_MAXIMA_60_KM_POR_HORA;
		this.status = StatusEnum.NORMAL;
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

	public double getVelocidadeMaxima() {
		return velocidadeMaxima;
	}

	public void setVelocidadeMaxima(double velocidadeMaxima) {
		this.velocidadeMaxima = velocidadeMaxima;
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
		return this.velocidadeMaxima * Parametros.KILOMETROS_POR_HORA_PARA_METROS_POR_SEGUNDO * tempo;
	}

	private double calcularTempo(double distancia) {
		return distancia / (this.velocidadeMaxima * Parametros.KILOMETROS_POR_HORA_PARA_METROS_POR_SEGUNDO);
	}

	public ElementoGrafo getProximo() {
		return proximo;
	}

	public void setProximo(ElementoGrafo proximo) {
		this.proximo = proximo;
	}

	public double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

}
