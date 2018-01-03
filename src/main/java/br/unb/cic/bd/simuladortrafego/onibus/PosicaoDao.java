package br.unb.cic.bd.simuladortrafego.onibus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PosicaoDao {

	private static final PosicaoDao INSTANCE = new PosicaoDao();
	private Connection conn;

	private static final Log logger = LogFactory.getLog(PosicaoDao.class);

	private PosicaoDao() {

	}

	public static PosicaoDao getInstance() {
		return INSTANCE;
	}

	public long inserePosicao(Onibus onibus) {

		long chave = 0;

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gerenciador";
			this.conn = DriverManager.getConnection(url, "postgres", "curup1ras");

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO posicao (datahora, onibus, linha, velocidade, geo_ponto_rede_pto) "
							+ "(SELECT now(), ?, ?, ?, ST_Line_Interpolate_Point(geo_linhas_lin, ?)::geometry FROM arco where fid = ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, onibus.getNome());
			ps.setString(2, onibus.getLinha().getNome());
			ps.setDouble(3, onibus.getArco().getVelocidadeMedia());
			ps.setDouble(4, onibus.getPosicao());
			ps.setInt(5, onibus.getArco().getNumero());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				chave = rs.getInt(1);
			}

			ps.close();
			conn.close();

		} catch (

		Exception e) {
			logger.error("Erro ao tentar inserir prosição no banco de dados.", e);
		}

		return chave;
	}

	public void atualizaLatitudeLongitude(long chave, Onibus onibus) {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gerenciador";
			this.conn = DriverManager.getConnection(url, "postgres", "curup1ras");

			PreparedStatement s = conn
					.prepareStatement("select ST_X(geo_ponto_rede_pto) AS LONG, ST_Y(geo_ponto_rede_pto) AS LAT"
							+ " FROM posicao where fid = ?");

			s.setLong(1, chave);

			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				String latitude = rs.getString("LAT");
				String longitude = rs.getString("LONG");

				onibus.setLatitude(latitude);
				onibus.setLongitude(longitude);
			}

			s.close();
			conn.close();

		} catch (

		Exception e) {
			logger.error("Erro ao tentar inserir prosição no banco de dados.", e);
		}
	}

}
