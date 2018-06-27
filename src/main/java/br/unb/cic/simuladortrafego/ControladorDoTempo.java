package br.unb.cic.simuladortrafego;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ControladorDoTempo {
	
	private static final Logger logger = Logger.getLogger(ControladorDoTempo.class.getName());
	
	private Calendar calendar;
	private SimpleDateFormat sdf;
	
	@Value("${simulador.periodoDeAtualizacaoDoDeslocamentoEmMilisegundos}")
	private int periodoDeAtualizacaoDoDeslocamentoEmMilisegundos;
	
	@PostConstruct
	private void init(){
		calendar = Calendar.getInstance();
		sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
	}
	
	@Scheduled(fixedRateString  = "#{${simulador.periodoDeAtualizacaoDoDeslocamentoEmMilisegundos} / ${simulador.multiplicadorDoTempo}}")
	public synchronized void scheduledTask() {
		calendar.add(Calendar.MILLISECOND, periodoDeAtualizacaoDoDeslocamentoEmMilisegundos);
		logger.debug(sdf.format(calendar.getTime()));
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public Date getDate() {
		return calendar.getTime();
	}
	
	public String getHoraFormatada(){
		return sdf.format(calendar.getTime());
	}
	
	
}
