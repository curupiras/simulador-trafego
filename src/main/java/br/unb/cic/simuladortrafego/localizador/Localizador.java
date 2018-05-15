package br.unb.cic.simuladortrafego.localizador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unb.cic.simuladortrafego.onibus.DtoFrota;
import br.unb.cic.simuladortrafego.onibus.DtoOnibus;
import br.unb.cic.simuladortrafego.onibus.Frota;
import br.unb.cic.simuladortrafego.onibus.Onibus;

@Component
public class Localizador {

	@Autowired
	private Frota frota;

	public DtoFrota recuperarLocalizaçãoDaFrota(){
		List<Onibus> lista = frota.getLista();
		List<DtoOnibus> listaDto = new ArrayList<>();
		DtoFrota dtoFrota = new DtoFrota();
		
		for (Onibus onibus : lista) {
			listaDto.add(getDtoOnibus(onibus));
		}
		
		dtoFrota.setFrota(listaDto);
		return dtoFrota;
	}

	private DtoOnibus getDtoOnibus(Onibus onibus) {
		DtoOnibus dtoOnibus = new DtoOnibus();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		dtoOnibus.setNome(onibus.getNome());
		dtoOnibus.setLinha(onibus.getLinha().getNome());
		dtoOnibus.setVelocidade(onibus.getVelocidade());
		dtoOnibus.setDataHora(simpleDateFormat.format(onibus.getHoraAtualizacao()));
		dtoOnibus.setLatitude(onibus.getLatitude());
		dtoOnibus.setLongitude(onibus.getLongitude());
		
		return dtoOnibus;
	}

}
