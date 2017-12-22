package br.unb.cic.bd.simuladortrafego.arco;

public class Arco {
	
	public static final double VELOCIDADE_MEDIA_DEFAULT = 60;
	
	private String nome;
	private String linha;
	private double velocidadeMedia;
	private double tamanho;
	private int numero;

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
		this.velocidadeMedia = VELOCIDADE_MEDIA_DEFAULT;
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

}
