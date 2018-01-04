package br.unb.cic.bd.simuladortrafego.grafo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NoDao {

	private static final NoDao INSTANCE = new NoDao();
	private Connection conn;

	private NoDao() {

	}

	public static NoDao getInstance() {
		return INSTANCE;
	}

	public List<No> getNosFromLinha(String linha) {

		List<No> lista = new ArrayList<No>();
		Map<Integer, No> map = new HashMap<>();

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gerenciador";
			this.conn = DriverManager.getConnection(url, "postgres", "curup1ras");

			PreparedStatement s = conn.prepareStatement("select nome from no where linha = ? order by fid");
			s.setString(1, linha);
			ResultSet r = s.executeQuery();

			while (r.next()) {
				No no = new No(linha, r.getString(1));
				map.put(no.getNumero(),no);
			}
			
			for (int i = 0 ; i<map.size();i++) {
				lista.add(map.get(i+1));
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
