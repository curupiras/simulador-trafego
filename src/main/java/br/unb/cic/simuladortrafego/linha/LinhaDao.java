package br.unb.cic.simuladortrafego.linha;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.unb.cic.simuladortrafego.grafo.Arco;
import br.unb.cic.simuladortrafego.grafo.ArcoRepository;
import br.unb.cic.simuladortrafego.grafo.No;
import br.unb.cic.simuladortrafego.grafo.NoRepository;

@Component
public class LinhaDao {

	@Autowired
	private ArcoRepository arcoRepository;

	@Autowired
	private NoRepository noRepository;

	@Value("${simulador.velocidadeMaxima}")
	private double velocidadeMaxima;

	@Value("${simulador.atrasoNaParada}")
	private double atrasoNaParada;

	public void inicializaLinha(Linha linha) {

		List<Arco> arcos = arcoRepository.findByLinhaOrderByFid(linha.getNome());
		for (Arco arco : arcos) {
			arco.setVelocidadeMaxima(velocidadeMaxima);
		}

		List<No> nos = noRepository.findByLinhaOrderById(linha.getNome());
		for (No no : nos) {
			no.setAtraso(atrasoNaParada);
		}

		linha.setArcos(arcos);
		linha.setNos(nos);

		for (int i = 0; i < nos.size(); i++) {
			nos.get(i).setProximo(arcos.get(i));
		}

		for (int i = 0; i < arcos.size(); i++) {
			arcos.get(i).setAnterior(nos.get(i));
		}

		for (int i = 1; i < nos.size(); i++) {
			nos.get(i).setAnterior(arcos.get(i - 1));
		}

		for (int i = 0; i < arcos.size() - 1; i++) {
			arcos.get(i).setProximo(nos.get(i + 1));
		}

		arcos.get(arcos.size() - 1).setProximo(nos.get(0));
		nos.get(0).setAnterior(arcos.get(arcos.size() - 1));
	}

}
