package br.unb.cic.bd.simuladortrafego;

import java.util.Date;

import br.unb.cic.bd.simuladortrafego.arco.Arco;
import br.unb.cic.bd.simuladortrafego.arco.DtoArcoPosicao;
import br.unb.cic.bd.simuladortrafego.linha.Linha;
import br.unb.cic.bd.simuladortrafego.onibus.Onibus;
import br.unb.cic.bd.simuladortrafego.onibus.PosicaoDao;

public class SimuladorDeViagem implements Runnable {
	
	public static final long PERIODO_DEFAULT_SEGUNDOS = 10;
	public static final double KILOMETROS_POR_HORA_PARA_METROS_POR_SEGUNDO = 0.277778;
	
	private Onibus onibus;
	
	public SimuladorDeViagem(Onibus onibus){
		this.onibus = onibus;
	}
	
	public void run() {
        System.out.println(onibus);
        atualizarPosicao(onibus);
        
        PosicaoDao posicaoDao = PosicaoDao.getInstance();
        long chave = posicaoDao.inserePosicao(onibus);
    }

	private void atualizarPosicao(Onibus onibus) {
		onibus.setHoraAtualizacao(new Date());
		double distancia = calcularDistanciaPercorrida(onibus);
		DtoArcoPosicao arcoPosicaoNovo = getNovaPosicao(distancia, onibus);
		onibus.setArco(arcoPosicaoNovo.getArco());
		onibus.setPosicao(arcoPosicaoNovo.getPosicao());
	}

	private double calcularDistanciaPercorrida(Onibus onibus) {
		double velocidadeMedia = onibus.getArco().getVelocidadeMedia();
		double tempo = PERIODO_DEFAULT_SEGUNDOS;
		return velocidadeMedia*KILOMETROS_POR_HORA_PARA_METROS_POR_SEGUNDO*tempo;
	}

	private DtoArcoPosicao getNovaPosicao(double distancia, Onibus onibus) {
		Arco arcoAtual = onibus.getArco();
		double posicaoAtual = onibus.getPosicao();
		Linha linha = onibus.getLinha();
		
		Arco arcoNovo = arcoAtual;
		double restanteArcoAtual = arcoAtual.getTamanho() * (1-posicaoAtual);
		double distanciaRemanescente = distancia;
		double posicaoNova;
		
		if(distanciaRemanescente > restanteArcoAtual){
			
			distanciaRemanescente = distanciaRemanescente - restanteArcoAtual;
			arcoNovo = linha.getProximoArco(arcoNovo);
			
			while(distanciaRemanescente > arcoNovo.getTamanho()){
				distanciaRemanescente = distanciaRemanescente - arcoNovo.getTamanho();
				arcoNovo = linha.getProximoArco(arcoNovo);
			}
			
			posicaoNova = distanciaRemanescente/arcoNovo.getTamanho();
		}else{
			posicaoNova = posicaoAtual + distanciaRemanescente/arcoNovo.getTamanho();
		}
		
		return new DtoArcoPosicao(arcoNovo, posicaoNova);
	}
}
