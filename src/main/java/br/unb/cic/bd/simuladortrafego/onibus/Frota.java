package br.unb.cic.bd.simuladortrafego.onibus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import br.unb.cic.bd.simuladortrafego.Parametros;
import br.unb.cic.bd.simuladortrafego.SimuladorDeLinha;
import br.unb.cic.bd.simuladortrafego.SimuladorDeViagem;
import br.unb.cic.bd.simuladortrafego.linha.Linha;

@Component
public class Frota {
	List<Onibus> listaOnibus;
	Map<String, Onibus> mapaOnibus;
	List<Linha> listaLinhas;
	Map<String, Linha> mapaLinhas;

	private static final Log logger = LogFactory.getLog(Frota.class);

	public Frota() {
		listaOnibus = new ArrayList<>();
		mapaOnibus = new HashMap<>();
		listaLinhas = new ArrayList<>();
		mapaLinhas = new HashMap<>();

		Linha l1 = new Linha("CIRCULAR-W3-SUL-NORTE-L2-NORTE-SUL");
		inserirLinha(l1);

		Onibus o1 = new Onibus("O1", l1, l1.getArcos().get(1), 0);
		Onibus o2 = new Onibus("O2", l1, l1.getArcos().get(1), 0.3);
		inserirOnibus(o1);
		inserirOnibus(o2);

		iniciarSimuladoresDeViagens();
		iniciarSimuladoresDeLinhas();
	}

	private void iniciarSimuladoresDeViagens() {

		for (Onibus onibus : listaOnibus) {
			SimuladorDeViagem simulador = new SimuladorDeViagem(onibus);
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(simulador, 0, Parametros.PERIODO_DE_ATUALIZACAO_DO_DESLOCAMENTO_EM_SEGUNDOS, TimeUnit.SECONDS);
		}

	}
	
	private void iniciarSimuladoresDeLinhas() {
		for (Linha linha : listaLinhas) {
			SimuladorDeLinha simulador = new SimuladorDeLinha(linha);
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(simulador, 0, Parametros.PERIODO_DE_ATUALIZACAO_DE_LINHA_EM_SEGUNDOS, TimeUnit.SECONDS);
		}
		
	}

	public void inserirOnibus(Onibus onibus) {
		if (mapaOnibus.containsKey(onibus.getNome())) {
			logger.info("O ônibus " + onibus.getNome() + " já existe.");
			return;
		}
		listaOnibus.add(onibus);
		mapaOnibus.put(onibus.getNome(), onibus);
	}
	
	public void inserirLinha(Linha linha) {
		if (mapaLinhas.containsKey(linha.getNome())) {
			logger.info("A linha " + linha.getNome() + " já existe.");
			return;
		}
		listaLinhas.add(linha);
		mapaLinhas.put(linha.getNome(), linha);
	}

	public List<Onibus> getLista() {
		return listaOnibus;
	}

	public void setLista(List<Onibus> lista) {
		this.listaOnibus = lista;
	}

	public Map<String, Onibus> getMapa() {
		return mapaOnibus;
	}

	public void setMapa(Map<String, Onibus> mapa) {
		this.mapaOnibus = mapa;
	}

}
