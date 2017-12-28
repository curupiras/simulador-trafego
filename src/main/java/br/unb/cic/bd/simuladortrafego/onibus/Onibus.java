package br.unb.cic.bd.simuladortrafego.onibus;

import java.util.Date;

import br.unb.cic.bd.simuladortrafego.arco.Arco;
import br.unb.cic.bd.simuladortrafego.linha.Linha;

public class Onibus {

	private Linha linha;
	private Arco arco;
	private double posicaoNoArco;
	private String nome;
	private Date horaAtualizacao;

	public Onibus(String nome, Linha linha, Arco arco, double posicao) {
		this.nome = nome;
		this.linha = linha;
		this.arco = arco;
		this.posicaoNoArco = posicao;
		this.horaAtualizacao = new Date();
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Arco getArco() {
		return arco;
	}

	public void setArco(Arco arco) {
		this.arco = arco;
	}

	public double getPosicao() {
		return posicaoNoArco;
	}

	public void setPosicao(double posicao) {
		this.posicaoNoArco = posicao;
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

	@Override
	public String toString() {
		return nome + " Arco: " + this.arco + " Posicao: " + this.posicaoNoArco;
	}

}
