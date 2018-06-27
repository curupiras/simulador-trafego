package br.unb.cic.simuladortrafego.onibus;

import java.util.Date;

import br.unb.cic.simuladortrafego.grafo.ElementoGrafo;
import br.unb.cic.simuladortrafego.linha.Linha;

public class Onibus {

	private Linha linha;
	private ElementoGrafo elementoGrafo;
	private double posicaoNoElementoGrafo;
	private String nome;
	private Date horaAtualizacao;
	private String latitude;
	private String longitude;
	private double velocidade;

	// Atributos para debug
	private double tempoAcumuladoDebug = 0;
	private double tempoLocalDebug = 0;

	public Onibus(String nome, Linha linha, ElementoGrafo elementoGrafo, double posicao, Date horaAtualizacao) {
		this.nome = nome;
		this.linha = linha;
		this.elementoGrafo = elementoGrafo;
		this.posicaoNoElementoGrafo = posicao;
		this.horaAtualizacao = horaAtualizacao;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public ElementoGrafo getElementoGrafo() {
		return elementoGrafo;
	}

	public void setElementoGrafo(ElementoGrafo elementoGrafo) {
		this.elementoGrafo = elementoGrafo;
	}

	public double getPosicao() {
		return posicaoNoElementoGrafo;
	}

	public void setPosicao(double posicao) {
		this.posicaoNoElementoGrafo = posicao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getHoraAtualizacao() {
		return horaAtualizacao;
	}

	public void setHoraAtualizacao(Date horaAtualizacao) {
		this.horaAtualizacao = horaAtualizacao;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Onibus [linha=" + linha + ", elementoGrafo=" + elementoGrafo + ", posicaoNoElementoGrafo="
				+ posicaoNoElementoGrafo + ", nome=" + nome + ", horaAtualizacao=" + horaAtualizacao + ", latitude="
				+ latitude + ", longitude=" + longitude + ", velocidade=" + velocidade + ", tempoAcumuladoDebug="
				+ tempoAcumuladoDebug + ", tempoLocalDebug=" + tempoLocalDebug + "]";
	}

	public double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

	public double getTempoAcumuladoDebug() {
		return tempoAcumuladoDebug;
	}

	public void setTempoAcumuladoDebug(double tempoAcumuladoDebug) {
		this.tempoAcumuladoDebug = tempoAcumuladoDebug;
	}

	public double getTempoLocalDebug() {
		return tempoLocalDebug;
	}

	public void setTempoLocalDebug(double tempoLocalDebug) {
		this.tempoLocalDebug = tempoLocalDebug;
	}

}
