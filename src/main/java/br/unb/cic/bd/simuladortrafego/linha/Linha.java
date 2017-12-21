package br.unb.cic.bd.simuladortrafego.linha;

import java.util.Map;

import br.unb.cic.bd.simuladortrafego.arco.Arco;
import br.unb.cic.bd.simuladortrafego.arco.ArcoDao;
import br.unb.cic.bd.simuladortrafego.no.No;
import br.unb.cic.bd.simuladortrafego.no.NoDao;

public class Linha {
	
	private String nome;
	private Map<Integer, Arco> arcos;
	private Map<Integer, No> nos;
	
	public Linha(String nome){
		this.nome = nome;
		
		ArcoDao arcoDao = ArcoDao.getInstance();
		this.arcos = arcoDao.getArcosFromLinha(nome);
		
		NoDao noDao = NoDao.getInstance();
		this.nos = noDao.getNosFromLinha(nome);
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Map<Integer, Arco> getArcos() {
		return arcos;
	}
	public void setArcos(Map<Integer, Arco> arcos) {
		this.arcos = arcos;
	}
	public Map<Integer, No> getNos() {
		return nos;
	}
	public void setNos(Map<Integer, No> nos) {
		this.nos = nos;
	}
	
	@Override
	public String toString(){
		return nos.toString() + "\n" + arcos.toString();
	}

}
