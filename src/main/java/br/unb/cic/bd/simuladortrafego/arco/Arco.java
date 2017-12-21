package br.unb.cic.bd.simuladortrafego.arco;

public class Arco {
	
	public static final float VELOCIDADE_MEDIA_DEFAULT = 60;
	
	private String nome;
	private String linha;
	private float velocidadeMedia;
	private float tamanho;
	private int numero;

	public Arco(String linha, String nome, float velocidadeMedia, float tamanho) {
		super();
		this.nome = nome;
		this.linha = linha;
		this.velocidadeMedia = velocidadeMedia;
		this.tamanho = tamanho;
		this.numero = Integer.parseInt(nome.substring(1));
	}

	public Arco(String linha, String nome, float tamanho) {
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

	public float getVelocidadeMedia() {
		return velocidadeMedia;
	}

	public void setVelocidadeMedia(float velocidadeMedia) {
		this.velocidadeMedia = velocidadeMedia;
	}

	public float getTamanho() {
		return tamanho;
	}

	public void setTamanho(float tamanho) {
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
