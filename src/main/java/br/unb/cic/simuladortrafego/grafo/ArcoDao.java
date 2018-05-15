package br.unb.cic.simuladortrafego.grafo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class ArcoDao {

	private static final Log logger = LogFactory.getLog(ArcoDao.class);

	private Connection conn;

	public List<Arco> getArcosFromLinha(String linha) {

		List<Arco> lista = new ArrayList<Arco>();

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gerenciador";
			this.conn = DriverManager.getConnection(url, "postgres", "curup1ras");

			PreparedStatement s = conn.prepareStatement("select nome,tamanho from arco where linha = ? order by fid");
			s.setString(1, linha);
			ResultSet r = s.executeQuery();

			while (r.next()) {
				Arco arco = new Arco(linha, r.getString(1), r.getDouble(2));
				lista.add(arco);
			}

			s.close();
			conn.close();

		} catch (

		Exception e) {
			logger.error("Erro ao tentar obter arcos.",e);
		}

		return lista;
	}

	public void atualizaVelocidades(List<Arco> lista) {

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gerenciador";
			this.conn = DriverManager.getConnection(url, "postgres", "curup1ras");
			conn.setAutoCommit(false);
			PreparedStatement s = conn.prepareStatement("update arco set velocidade = ? where fid = ?");

			for (Arco arco : lista) {
				s.setDouble(1, arco.getVelocidadeMedia());
				s.setDouble(2, arco.getNumero());
				s.executeUpdate();
			}

			conn.commit();
			s.close();
			conn.close();

		} catch (Exception e) {
			logger.error("Erro ao atualizar velocidades dos arcos.",e);
		}

	}

}
