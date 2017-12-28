package br.unb.cic.bd.simuladortrafego.rs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unb.cic.bd.simuladortrafego.localizador.Localizador;
import br.unb.cic.bd.simuladortrafego.onibus.DtoFrota;

@RestController
public class Localizacao {

	@Autowired
	Localizador localizador;

	@RequestMapping("/localizacao")
	public DtoFrota localizacao() {
		return localizador.recuperarLocalizaçãoDaFrota();
	}

}
