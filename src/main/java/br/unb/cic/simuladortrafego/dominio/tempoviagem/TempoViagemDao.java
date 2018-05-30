package br.unb.cic.simuladortrafego.dominio.tempoviagem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class TempoViagemDao {

	private Connection conn;

	private static final Log logger = LogFactory.getLog(TempoViagemDao.class);

	public long insereTempoViagem(TempoViagem tempoViagem) {

		long chave = 0;

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gerenciador";
			this.conn = DriverManager.getConnection(url, "postgres", "curup1ras");

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO tempo_viagem_simulador (datahora, nome, tempo) (SELECT now(), ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, tempoViagem.getNome());
			ps.setDouble(2, tempoViagem.getTempo());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				chave = rs.getInt(1);
			}

			ps.close();
			conn.close();

		} catch (

		Exception e) {
			logger.error("Erro ao tentar inserir tempo de viagem no banco de dados.", e);
		}

		return chave;
	}
}
