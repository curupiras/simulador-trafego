package br.unb.cic.bd.simuladortrafego.no;

public class No {
	
	public static final float ATRASO_DEFAULT = 10;

	private String linha;
	private String nome;
	private float atraso;
	private int numero;

	public No(String linha, String nome, float atraso) {
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
		this.atraso = ATRASO_DEFAULT;
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

	public float getAtraso() {
		return atraso;
	}

	public void setAtraso(float atraso) {
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

}
