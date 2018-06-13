package br.unb.cic.simuladortrafego.linha;

import java.util.List;

import br.unb.cic.simuladortrafego.grafo.Arco;
import br.unb.cic.simuladortrafego.grafo.No;

public class Linha {

	private String nome;
	private List<Arco> arcos;
	private List<No> nos;

	public Linha(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Arco> getArcos() {
		return arcos;
	}

	public void setArcos(List<Arco> arcos) {
		this.arcos = arcos;
	}

	public List<No> getNos() {
		return nos;
	}

	public void setNos(List<No> nos) {
		this.nos = nos;
	}

	@Override
	public String toString() {
		return nos.toString() + "\n" + arcos.toString();
	}

}
