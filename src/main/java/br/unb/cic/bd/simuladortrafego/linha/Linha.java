package br.unb.cic.bd.simuladortrafego.linha;

import java.util.List;

import br.unb.cic.bd.simuladortrafego.grafo.Arco;
import br.unb.cic.bd.simuladortrafego.grafo.ArcoDao;
import br.unb.cic.bd.simuladortrafego.grafo.No;
import br.unb.cic.bd.simuladortrafego.grafo.NoDao;

public class Linha {

	private String nome;
	private List<Arco> arcos;
	private List<No> nos;
	private ArcoDao arcoDao;
	private NoDao noDao;

	public Linha(String nome) {
		this.nome = nome;

		arcoDao = new ArcoDao();
		this.arcos = arcoDao.getArcosFromLinha(nome);

		noDao = new NoDao();
		this.nos = noDao.getNosFromLinha(nome);

		for (int i = 0; i < nos.size(); i++) {
			nos.get(i).setProximo(arcos.get(i));
		}
		
		for(int i = 0; i < arcos.size(); i++){
			arcos.get(i).setAnterior(nos.get(i));
		}
		
		for(int i = 1; i < nos.size(); i++){
			nos.get(i).setAnterior(arcos.get(i-1));
		}

		for (int i = 0; i < arcos.size() - 1; i++) {
			arcos.get(i).setProximo(nos.get(i + 1));
		}
		
		arcos.get(arcos.size() - 1).setProximo(nos.get(0));
		nos.get(0).setAnterior(arcos.get(arcos.size()-1));

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
