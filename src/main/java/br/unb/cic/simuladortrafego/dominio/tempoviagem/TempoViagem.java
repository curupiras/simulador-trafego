package br.unb.cic.simuladortrafego.dominio.tempoviagem;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tempo_viagem_preditor")
public class TempoViagem {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private long id;

	public TempoViagem(Date dataHora, String nome, double tempo) {
		super();
		this.dataHora = dataHora;
		this.nome = nome;
		this.tempo = tempo;
	}

	@Column(name = "datahora")
	private Date dataHora;

	@Column(name = "nome")
	private String nome;

	@Column(name = "tempo")
	private double tempo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getTempo() {
		return tempo;
	}

	public void setTempo(double tempo) {
		this.tempo = tempo;
	}

}
