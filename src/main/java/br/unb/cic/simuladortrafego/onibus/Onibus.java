package br.unb.cic.simuladortrafego.onibus;

import java.util.Date;

import org.apache.log4j.Logger;

import br.unb.cic.simuladortrafego.dominio.tempoviagem.TempoViagem;
import br.unb.cic.simuladortrafego.dominio.tempoviagem.TempoViagemDao;
import br.unb.cic.simuladortrafego.grafo.DtoTempoPosicao;
import br.unb.cic.simuladortrafego.grafo.ElementoGrafo;
import br.unb.cic.simuladortrafego.linha.Linha;
import br.unb.cic.simuladortrafego.util.Util;

public class Onibus {

	private static final Logger logger = Logger.getLogger(Onibus.class.getName());

	private Linha linha;
	private ElementoGrafo elementoGrafo;
	private double posicaoNoElementoGrafo;
	private String nome;
	private Date horaAtualizacao;
	private String latitude;
	private String longitude;
	private double velocidade;

	private TempoViagemDao tempoViagemDao;

	// Atributos para debug
	private double tempoAcumuladoDebug = 0;
	private double tempoLocalDebug = 0;

	public Onibus(String nome, Linha linha, ElementoGrafo elementoGrafo, double posicao) {
		this.nome = nome;
		this.linha = linha;
		this.elementoGrafo = elementoGrafo;
		this.posicaoNoElementoGrafo = posicao;
		this.horaAtualizacao = new Date();
		Util.atualizarVelocidade(this);

		tempoViagemDao = new TempoViagemDao();
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public ElementoGrafo getElementoGrafo() {
		return elementoGrafo;
	}

	public void setElementoGrafo(ElementoGrafo elementoGrafo) {
		this.elementoGrafo = elementoGrafo;
	}

	public double getPosicao() {
		return posicaoNoElementoGrafo;
	}

	public void setPosicao(double posicao) {
		this.posicaoNoElementoGrafo = posicao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getHoraAtualizacao() {
		return horaAtualizacao;
	}

	public void setHoraAtualizacao(Date horaAtualizacao) {
		this.horaAtualizacao = horaAtualizacao;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return nome + " Elemento: " + this.elementoGrafo + " Posicao: " + this.posicaoNoElementoGrafo;
	}

	public void deslocar(long tempo) {
		DtoTempoPosicao tempoPosicao = new DtoTempoPosicao(tempo, this.posicaoNoElementoGrafo, this.velocidade);

		while (tempoPosicao.getTempo() > 0) {
			double tempoGasto = elementoGrafo.consomeTempo(tempoPosicao);

			tempoAcumuladoDebug = tempoAcumuladoDebug + tempoGasto;
			tempoLocalDebug = tempoLocalDebug + tempoGasto;
			// logger.info(elementoGrafo + ": " + tempoAcumuladoDebug + " s");

			if (tempoPosicao.getPosicao() == 1) {
				logger.info("Tempo de viagem em " + elementoGrafo + ": " + tempoLocalDebug + " s");
				TempoViagem tempoViagem = new TempoViagem(new Date(), elementoGrafo.getNome(), tempoLocalDebug);
				tempoViagemDao.insereTempoViagem(tempoViagem);
				tempoLocalDebug = 0;

				elementoGrafo = elementoGrafo.getProximo();
				tempoPosicao.setPosicao(0);
				Util.atualizarVelocidade(this);
				tempoPosicao.setVelocidade(this.velocidade);
			}
		}

		posicaoNoElementoGrafo = tempoPosicao.getPosicao();

	}

	public double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

}
