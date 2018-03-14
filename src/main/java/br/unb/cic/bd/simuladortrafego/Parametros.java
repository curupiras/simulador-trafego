package br.unb.cic.bd.simuladortrafego;

public final class Parametros {
	
	public static final double FATOR_DE_CORRECAO_NORMAL = 1;
	public static final double FATOR_DE_CORRECAO_GRAVE = 0.25;
	public static final double FATOR_DE_CORRECAO_MODERADO = 0.50;
	public static final double FATOR_DE_CORRECAO_LEVE = 0.75;
	public static final double FATOR_DE_CORRECAO_HORARIO_DE_PICO = 0.60;
	public static final double FATOR_DE_INFLUENCIA_LEVE = 0.80;
	public static final double FATOR_DE_INFLUENCIA_MODERADO = 0.6;
	public static final double FATOR_DE_INFLUENCIA_FORTE = 0.4;
	public static final double FATOR_DE_INFLUENCIA_AUSENTE = 1;
	public static final double FATOR_DE_OSCILACAO_DA_VELOCIDADE = 0.1;
	public static final double FATOR_DE_OSCILACAO_DO_ATRASO = 0.1;
	
	public static final double PROBABILIDADE_DE_OCORRENCIA_DE_EVENTO_GRAVE = 0.01;
	public static final double PROBABILIDADE_DE_OCORRENCIA_DE_EVENTO_MODERADO = 0.02;
	public static final double PROBABILIDADE_DE_OCORRENCIA_DE_EVENTO_LEVE = 0.1;
	
	public static final double PROBABILIDADE_DE_ENCERRAMENTO_DE_EVENTO_GRAVE = 0.1;
	public static final double PROBABILIDADE_DE_ENCERRAMENTO_DE_EVENTO_MODERADO = 0.3;
	public static final double PROBABILIDADE_DE_ENCERRAMENTO_DE_EVENTO_LEVE = 0.6;
	
	public static final int LIMITE_INFERIOR_HORA_DE_PICO_MATUTINO = 7;
	public static final int LIMITE_INFERIOR_MINUTO_DE_PICO_MATUTINO = 30;
	public static final int LIMITE_SUPERIOR_HORA_DE_PICO_MATUTINO = 9;
	public static final int LIMITE_SUPERIOR_MINUTO_DE_PICO_MATUTINO = 30;
	
	public static final int LIMITE_INFERIOR_HORA_DE_PICO_VESPERTINO = 17;
	public static final int LIMITE_INFERIOR_MINUTO_DE_PICO_VESPERTINO = 30;
	public static final int LIMITE_SUPERIOR_HORA_DE_PICO_VESPERTINO = 19;
	public static final int LIMITE_SUPERIOR_MINUTO_DE_PICO_VESPERTINO = 30;
	
	
	public static final double VELOCIDADE_MAXIMA_60_KM_POR_HORA = 60;
	public static final double ATRASO_NA_PARADA_EM_SEGUNDOS = 10;
	
	public static final long PERIODO_DE_ATUALIZACAO_DO_DESLOCAMENTO_EM_SEGUNDOS = 10;
	public static final long PERIODO_DE_ATUALIZACAO_DE_LINHA_EM_SEGUNDOS = 30;
	
	public static final double KILOMETROS_POR_HORA_PARA_METROS_POR_SEGUNDO = 0.277778;
}
