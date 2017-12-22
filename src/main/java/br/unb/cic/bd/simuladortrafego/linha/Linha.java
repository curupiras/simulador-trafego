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
	private int primeiroArco = Integer.MAX_VALUE;
	private int ultimoArco = Integer.MIN_VALUE;

	public Linha(String nome) {
		this.nome = nome;

		ArcoDao arcoDao = ArcoDao.getInstance();
		this.arcos = arcoDao.getArcosFromLinha(nome);
		setPrimeiroUltimoArco(this.arcos);

		NoDao noDao = NoDao.getInstance();
		this.nos = noDao.getNosFromLinha(nome);
	}

	private void setPrimeiroUltimoArco(Map<Integer, Arco> arcos) {
		for (Integer key : arcos.keySet()) {
			if (key < primeiroArco) {
				primeiroArco = key;
			}

			if (key > ultimoArco) {
				ultimoArco = key;
			}
		}

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
	public String toString() {
		return nos.toString() + "\n" + arcos.toString();
	}

	public Arco getProximoArco(Arco arco) {
		int numeroArco = arco.getNumero();

		if (numeroArco == ultimoArco) {
			return arcos.get(primeiroArco);
		}

		return arcos.get(numeroArco + 1);
	}

}
