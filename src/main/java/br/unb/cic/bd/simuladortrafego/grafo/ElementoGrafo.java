package br.unb.cic.bd.simuladortrafego.grafo;

public abstract class ElementoGrafo {

	public abstract void consomeTempo(DtoTempoPosicao tempoPosicao);
	
	public abstract int getNumero();
	
	public abstract ElementoGrafo getProximo();
	
	public abstract double getVelocidadeMedia();
	
	public abstract String getNome();

}
