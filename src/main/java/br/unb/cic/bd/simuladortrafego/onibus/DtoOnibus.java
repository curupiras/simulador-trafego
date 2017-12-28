package br.unb.cic.bd.simuladortrafego.onibus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoOnibus {

	private String dataHora;
	private String Nome;
	private String Linha;
	private String Latitude;
	private String Longitude;
	private double Velocidade;

	@JsonProperty("DATAHORA")
	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	@JsonProperty("ORDEM")
	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	@JsonProperty("LINHA")
	public String getLinha() {
		return Linha;
	}

	public void setLinha(String linha) {
		Linha = linha;
	}

	@JsonProperty("LATITUDE")
	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	@JsonProperty("LONGITUDE")
	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	@JsonProperty("VELOCIDADE")
	public double getVelocidade() {
		return Velocidade;
	}

	public void setVelocidade(double velocidade) {
		Velocidade = velocidade;
	}

}
