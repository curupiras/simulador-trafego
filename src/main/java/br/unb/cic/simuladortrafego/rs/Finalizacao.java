package br.unb.cic.simuladortrafego.rs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Finalizacao {

	@RequestMapping("/finalizacao")
	public void finalizacao() {
		System.exit(0);
	}

}
