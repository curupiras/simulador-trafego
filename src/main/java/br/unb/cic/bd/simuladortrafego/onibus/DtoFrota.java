package br.unb.cic.bd.simuladortrafego.onibus;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoFrota {
	
	List<DtoOnibus> frota;
	
	@JsonProperty("FROTA")
	public List<DtoOnibus> getFrota(){
		return this.frota;
	}

	public void setFrota(List<DtoOnibus> frota) {
		this.frota = frota;
	}

}
