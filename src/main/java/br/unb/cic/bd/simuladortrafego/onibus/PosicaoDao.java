package br.unb.cic.bd.simuladortrafego.onibus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PosicaoDao {

	private static final PosicaoDao INSTANCE = new PosicaoDao();
	private Connection conn;

	private PosicaoDao() {

	}

	public static PosicaoDao getInstance() {
		return INSTANCE;
	}

	public void inserePosicao(Onibus onibus) {

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gerenciador";
			this.conn = DriverManager.getConnection(url, "postgres", "curup1ras");

			PreparedStatement s = conn
					.prepareStatement("INSERT INTO posicao (datahora, onibus, linha, velocidade, geo_ponto_rede_pto) "
							+ "(SELECT now(), ?, ?, ?, ST_Line_Interpolate_Point(geo_linhas_lin, ?)::geometry FROM arco where fid = ?)");

			s.setString(1, onibus.getNome());
			s.setString(2, onibus.getLinha().getNome());
			s.setDouble(3, onibus.getArco().getVelocidadeMedia());
			s.setDouble(4, onibus.getPosicao());
			s.setInt(5, onibus.getArco().getNumero());

			s.execute();

			s.close();
			conn.close();

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

}
