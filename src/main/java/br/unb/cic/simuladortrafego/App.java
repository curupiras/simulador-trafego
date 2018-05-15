package br.unb.cic.simuladortrafego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
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
