package br.unb.cic.bd.simuladortrafego.main;

import br.unb.cic.bd.simuladortrafego.onibus.Onibus;

public class SimuladorDeViagem implements Runnable {
	
	public static final long PERIODO_DEFAULT = 10;
	
	private Onibus onibus;
	
	public SimuladorDeViagem(Onibus onibus){
		this.onibus = onibus;
	}
	
	public void run() {
        System.out.println(onibus);
        atualizarPosicao(onibus);
    }

	private void atualizarPosicao(Onibus onibus2) {
		// TODO Auto-generated method stub		
	}

}
