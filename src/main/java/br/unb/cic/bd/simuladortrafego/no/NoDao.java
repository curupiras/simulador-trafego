package br.unb.cic.bd.simuladortrafego.no;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public final class NoDao {

	private static final NoDao INSTANCE = new NoDao();
	private Connection conn;

	private NoDao() {

	}

	public static NoDao getInstance() {
		return INSTANCE;
	}

	public Map<Integer, No> getNosFromLinha(String linha) {

		Map<Integer, No> map = new HashMap<Integer, No>();

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gerenciador";
			this.conn = DriverManager.getConnection(url, "postgres", "curup1ras");

			PreparedStatement s = conn.prepareStatement("select nome from no where linha = ?");
			s.setString(1, linha);
			ResultSet r = s.executeQuery();

			while (r.next()) {
				No no = new No(linha, r.getString(1));
				map.put(no.getNumero(), no);
			}

			s.close();
			conn.close();

		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return map;
	}
}
