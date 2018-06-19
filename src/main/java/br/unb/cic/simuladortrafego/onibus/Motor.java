package br.unb.cic.simuladortrafego.onibus;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unb.cic.simuladortrafego.dominio.tempoviagem.TempoViagem;
import br.unb.cic.simuladortrafego.dominio.tempoviagem.TempoViagemRepository;
import br.unb.cic.simuladortrafego.grafo.DtoTempoPosicao;
import br.unb.cic.simuladortrafego.util.Util;

@Component
public class Motor {

	@Autowired
	private TempoViagemRepository tempoViagemRepository;

	@Autowired
	private Util util;

	private static final Logger logger = Logger.getLogger(Motor.class.getName());

	public void deslocar(Onibus onibus, long tempo) {
		DtoTempoPosicao tempoPosicao = new DtoTempoPosicao(tempo, onibus.getPosicao(), onibus.getVelocidade());

		while (tempoPosicao.getTempo() > 0) {
			double tempoGasto = onibus.getElementoGrafo().consomeTempo(tempoPosicao);

			onibus.setTempoAcumuladoDebug(onibus.getTempoAcumuladoDebug() + tempoGasto);
			onibus.setTempoLocalDebug(onibus.getTempoLocalDebug() + tempoGasto);

			if (tempoPosicao.getPosicao() == 1) {
				logger.debug(
						"Tempo de viagem em " + onibus.getElementoGrafo() + ": " + onibus.getTempoLocalDebug() + " s");
				TempoViagem tempoViagem = new TempoViagem(new Date(), onibus.getElementoGrafo().getNome(),
						onibus.getTempoLocalDebug(), onibus.getNome());
				tempoViagemRepository.save(tempoViagem);
				onibus.setTempoLocalDebug(0);

				onibus.setElementoGrafo(onibus.getElementoGrafo().getProximo());
				tempoPosicao.setPosicao(0);
				util.atualizarVelocidade(onibus);
				tempoPosicao.setVelocidade(onibus.getVelocidade());
			}
		}

		onibus.setPosicao(tempoPosicao.getPosicao());

	}

}
