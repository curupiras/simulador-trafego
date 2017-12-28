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

import br.unb.cic.bd.simuladortrafego.SimuladorDeViagem;
import br.unb.cic.bd.simuladortrafego.linha.Linha;

@Component
public class Frota {
	List<Onibus> lista;
	Map<String, Onibus> mapa;

	private static final Log logger = LogFactory.getLog(Frota.class);

	public Frota() {
		lista = new ArrayList<>();
		mapa = new HashMap<>();
		
		Linha linha = new Linha("CIRCULAR-W3-SUL-NORTE-L2-NORTE-SUL");
		Onibus o1 = new Onibus("O1", linha, linha.getArcos().get(1), 0);
		inserirOnibus(o1);
		
		iniciarSimuladoresDeViagem(lista);
	}

	private void iniciarSimuladoresDeViagem(List<Onibus> lista) {
		
		for (Onibus onibus : lista) {
			SimuladorDeViagem simulador = new SimuladorDeViagem(onibus);
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(simulador, 0, SimuladorDeViagem.PERIODO_DEFAULT_SEGUNDOS, TimeUnit.SECONDS);
		}
		
	}

	public void inserirOnibus(Onibus onibus) {
		if (mapa.containsKey(onibus.getNome())) {
			logger.info("O ônibus " + onibus.getNome() + " já existe.");
			return;
		}
		lista.add(onibus);
		mapa.put(onibus.getNome(), onibus);
	}

	public List<Onibus> getLista() {
		return lista;
	}

	public void setLista(List<Onibus> lista) {
		this.lista = lista;
	}

	public Map<String, Onibus> getMapa() {
		return mapa;
	}

	public void setMapa(Map<String, Onibus> mapa) {
		this.mapa = mapa;
	}

}
