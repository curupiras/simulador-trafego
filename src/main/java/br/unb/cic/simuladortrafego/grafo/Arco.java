package br.unb.cic.simuladortrafego.grafo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.vividsolutions.jts.geom.Geometry;

import br.unb.cic.parametros.Parametros;

@Entity
@Table(name = "arco")
public class Arco extends ElementoGrafo {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "fid")
	private long fid;

	@Column(name = "nome")
	private String nome;

	@Column(name = "tamanho")
	private double tamanho;

	@Column(name = "geo_linhas_lin")
	private Geometry geoLinha;

	@Column(name = "linha")
	private String linha;

	@Column(name = "velocidade")
	private double velocidadeMedia;

	@Transient
	private double velocidadeMaxima;

	@Transient
	private int numero;

	@Transient
	private ElementoGrafo proximo;

	@Transient
	private ElementoGrafo anterior;

	@Transient
	private StatusEnum status = StatusEnum.NORMAL;

	@Transient
	private InfluenciaEnum influencia = InfluenciaEnum.INFLUENCIA_AUSENTE;

	public Arco() {
		super();
	}

	@PostLoad
	public void setup() {
		this.numero = Integer.parseInt(this.nome.substring(1));
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getVelocidadeMaxima() {
		return velocidadeMaxima;
	}

	public void setVelocidadeMaxima(double velocidadeMaxima) {
		this.velocidadeMaxima = velocidadeMaxima;
	}

	public double getTamanho() {
		return tamanho;
	}

	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public InfluenciaEnum getInfluencia() {
		return influencia;
	}

	public void setInfluencia(InfluenciaEnum influencia) {
		this.influencia = influencia;
	}

	@Override
	public double consomeTempo(DtoTempoPosicao tempoPosicao) {
		double tempo = tempoPosicao.getTempo();
		double posicao = tempoPosicao.getPosicao();
		double velocidade = tempoPosicao.getVelocidade();
		double distanciaRemanescente = tamanho - tamanho * posicao;

		if (distanciaRemanescente <= calcularDistancia(tempo, velocidade)) {
			double tempoGasto = calcularTempo(distanciaRemanescente, velocidade);
			tempoPosicao.setTempo(tempo - tempoGasto);
			tempoPosicao.setPosicao(1);
			return tempoGasto;
		} else {
			tempoPosicao.setTempo(0);
			tempoPosicao.setPosicao(posicao + calcularDistancia(tempo, velocidade) / tamanho);
			return tempo;
		}

	}

	private double calcularDistancia(double tempo, double velocidade) {
		return velocidade * Parametros.KILOMETROS_POR_HORA_PARA_METROS_POR_SEGUNDO * tempo;
	}

	private double calcularTempo(double distancia, double velocidade) {
		return distancia / (velocidade * Parametros.KILOMETROS_POR_HORA_PARA_METROS_POR_SEGUNDO);
	}

	public ElementoGrafo getProximo() {
		return proximo;
	}

	public void setProximo(ElementoGrafo proximo) {
		this.proximo = proximo;
	}

	public ElementoGrafo getAnterior() {
		return anterior;
	}

	public void setAnterior(ElementoGrafo anterior) {
		this.anterior = anterior;
	}

	public double getVelocidadeMedia() {
		return velocidadeMedia;
	}

	public void setVelocidadeMedia(double velocidade) {
		this.velocidadeMedia = velocidade;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Arco getProximoArco() {
		return (Arco) this.getProximo().getProximo();
	}

	public Arco getArcoAnterior() {
		return (Arco) this.getAnterior().getAnterior();
	}

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public Geometry getGeoLinha() {
		return geoLinha;
	}

	public void setGeoLinha(Geometry geoLinha) {
		this.geoLinha = geoLinha;
	}

}
