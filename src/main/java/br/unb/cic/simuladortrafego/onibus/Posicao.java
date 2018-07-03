package br.unb.cic.simuladortrafego.onibus;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(name = "posicao")
public class Posicao {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id")
	private long id;

	@Column(name = "datahora")
	private Date dataHora;

	@Column(name = "ordem")
	private String onibus;

	@Column(name = "linha")
	private String linha;

	@Column(name = "geo_pto")
	private Geometry ponto;

	@Column(name = "velocidade")
	private double velocidade;

	@Column(name = "processado")
	private boolean processado;

	public long getFid() {
		return id;
	}

	public void setFid(long fid) {
		this.id = fid;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getOnibus() {
		return onibus;
	}

	public void setOnibus(String onibus) {
		this.onibus = onibus;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public Geometry getPonto() {
		return ponto;
	}

	public void setPonto(Geometry ponto) {
		this.ponto = ponto;
	}

	public double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

	public boolean isProcessado() {
		return processado;
	}

	public void setProcessado(boolean processado) {
		this.processado = processado;
	}

}
