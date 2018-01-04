package br.unb.cic.bd.simuladortrafego.grafo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class ArcoDao {

	private static final ArcoDao INSTANCE = new ArcoDao();
	private Connection conn;

	private ArcoDao() {

	}

	public static ArcoDao getInstance() {
		return INSTANCE;
	}

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
			e.printStackTrace();
		}

		return lista;
	}
}
