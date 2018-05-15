package br.unb.cic.simuladortrafego.rs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unb.cic.simuladortrafego.localizador.Localizador;
import br.unb.cic.simuladortrafego.onibus.DtoFrota;

@RestController
public class Localizacao {

	@Autowired
	Localizador localizador;

	@RequestMapping("/localizacao")
	public DtoFrota localizacao() {
		return localizador.recuperarLocalizaçãoDaFrota();
	}

}
