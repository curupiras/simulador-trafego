package br.unb.cic.bd.simuladortrafego.main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import br.unb.cic.bd.simuladortrafego.linha.Linha;
import br.unb.cic.bd.simuladortrafego.onibus.Onibus;

public class App {
	public static void main(String[] args) {

		Linha linha = new Linha("CIRCULAR-W3-SUL-NORTE-L2-NORTE-SUL");
		Onibus o1 = new Onibus("O1", linha, linha.getArcos().get(1), 0);
		
		SimuladorDeViagem simulador = new SimuladorDeViagem(o1);
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(simulador, 0, SimuladorDeViagem.PERIODO_DEFAULT, TimeUnit.SECONDS);

	}

}

// Graph<No, Arco> g = new DefaultDirectedGraph<>(Arco.class);
//
// No n1 = new No("L1", "N1", 30);
// No n2 = new No("L1", "N2", 30);
// No n3 = new No("L1", "N3", 30);
//
//// add the vertices
// g.addVertex(n1);
// g.addVertex(n2);
// g.addVertex(n3);
//
// Arco a1 = new Arco("L1", "A1", 60, 800);
// Arco a2 = new Arco("L1", "A2", 60, 900);
// Arco a3 = new Arco("L1", "A3", 60, 400);
//
//// add edges to create linking structure
// g.addEdge(n1, n2, a1);
// g.addEdge(n2, n3, a2);
// g.addEdge(n3, n1, a3);
//
// System.out.println(g.toString());
