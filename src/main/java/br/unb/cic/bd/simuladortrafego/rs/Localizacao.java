package br.unb.cic.bd.simuladortrafego.rs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unb.cic.bd.simuladortrafego.onibus.Onibus;

@RestController
public class Localizacao {

	@RequestMapping("/localizacao")
	public Onibus localizacao() {
		return new Onibus("O1", null, null, 0);
	}

}
