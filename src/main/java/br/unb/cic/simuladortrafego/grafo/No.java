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

@Entity
@Table(name = "no_preditor")
public class No extends ElementoGrafo {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "fid")
	private int id;

	@Column(name = "parada")
	private int parada;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "situacao")
	private String situacao;

	@Column(name = "estrutura_de_paragem")
	private String estrutura;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "geo_ponto_rede_pto")
	private Geometry ponto;

	@Column(name = "nome")
	private String nome;

	@Column(name = "linha")
	private String linha;

	@Transient
	private double atraso;

	@Transient
	private int numero;

	@Transient
	private ElementoGrafo proximo;

	@Transient
	private ElementoGrafo anterior;

	public No(String linha, String nome, double atraso) {
		super();
		this.linha = linha;
		this.nome = nome;
		this.atraso = atraso;
		this.numero = Integer.parseInt(nome.substring(1));
	}
	
	public No() {
		super();
	}
	
	@PostLoad
	public void setup() {
		this.numero = this.id;
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

	public double getAtraso() {
		return atraso;
	}

	public void setAtraso(double atraso) {
		this.atraso = atraso;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	@Override
	public double consomeTempo(DtoTempoPosicao tempoPosicao) {
		double tempo = tempoPosicao.getTempo();
		double posicao = tempoPosicao.getPosicao();
		double atrasoRemanescente = atraso - atraso * posicao;

		if (atrasoRemanescente <= tempo) {
			tempoPosicao.setTempo(tempo - atrasoRemanescente);
			tempoPosicao.setPosicao(1);
			return atrasoRemanescente;
		} else {
			tempoPosicao.setTempo(0);
			tempoPosicao.setPosicao(posicao + tempo / atraso);
			return tempo;
		}
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParada() {
		return parada;
	}

	public void setParada(int parada) {
		this.parada = parada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getEstrutura() {
		return estrutura;
	}

	public void setEstrutura(String estrutura) {
		this.estrutura = estrutura;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Geometry getPonto() {
		return ponto;
	}

	public void setPonto(Geometry ponto) {
		this.ponto = ponto;
	}

	@Override
	public double getVelocidadeMedia() {
		return 0;
	}

}
