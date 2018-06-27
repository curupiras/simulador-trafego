package br.unb.cic.simuladortrafego.onibus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.unb.cic.simuladortrafego.ControladorDoTempo;
import br.unb.cic.simuladortrafego.SimuladorDeLinha;
import br.unb.cic.simuladortrafego.SimuladorDeViagem;
import br.unb.cic.simuladortrafego.linha.Linha;
import br.unb.cic.simuladortrafego.linha.LinhaDao;
import br.unb.cic.simuladortrafego.util.Util;

@Component
public class Frota {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private LinhaDao linhaDao;

	@Autowired
	private Util util;
	
	@Autowired
	ControladorDoTempo controladorDoTempo;

	@Value("${simulador.tamanhoDaFrota}")
	private int tamanhoDaFrota;

	private static final Log logger = LogFactory.getLog(Frota.class);

	List<Onibus> listaOnibus;
	Map<String, Onibus> mapaOnibus;
	List<Linha> listaLinhas;
	Map<String, Linha> mapaLinhas;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("Inicio do Simulador");

		listaOnibus = new ArrayList<>();
		mapaOnibus = new HashMap<>();
		listaLinhas = new ArrayList<>();
		mapaLinhas = new HashMap<>();

		Linha l1 = new Linha("CIRCULAR-W3-SUL-NORTE-L2-NORTE-SUL");
		linhaDao.inicializaLinha(l1);
		inserirLinha(l1);

		for (int i = 0; i < tamanhoDaFrota; i++) {
			Onibus onibus = new Onibus("O" + (i + 1), l1, l1.getArcos().get(i), 0, controladorDoTempo.getDate());
			util.atualizarVelocidade(onibus);
			inserirOnibus(onibus);
		}

		iniciarSimuladoresDeViagens();
		iniciarSimuladoresDeLinhas();
	}

	private void iniciarSimuladoresDeViagens() {
		for (Onibus onibus : listaOnibus) {
			appContext.getBean(SimuladorDeViagem.class, onibus);
		}

	}

	private void iniciarSimuladoresDeLinhas() {
		for (Linha linha : listaLinhas) {
			appContext.getBean(SimuladorDeLinha.class, linha);
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
