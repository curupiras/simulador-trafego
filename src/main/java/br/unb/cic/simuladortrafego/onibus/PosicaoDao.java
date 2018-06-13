package br.unb.cic.simuladortrafego.onibus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateArrays;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import com.vividsolutions.jts.linearref.LinearLocation;
import com.vividsolutions.jts.linearref.LocationIndexedLine;
import com.vividsolutions.jts.operation.distance.DistanceOp;
import com.vividsolutions.jts.operation.linemerge.LineMerger;

import br.unb.cic.simuladortrafego.grafo.Arco;
import br.unb.cic.simuladortrafego.grafo.No;

@Component
public class PosicaoDao {

	@Autowired
	private PosicaoRepository posicaoRepository;

	private Connection conn;

	private static final Log logger = LogFactory.getLog(PosicaoDao.class);

	public long inserePosicao(Onibus onibus) {
		if (onibus.getElementoGrafo() instanceof Arco) {
			long chave = inserePosicaoArco(onibus);
			inserePosicao2(onibus);
			return chave;
		} else {
			long chave = inserePosicaoNo(onibus);
//			inserePosicao2(onibus);
			return chave;
		}

	}

	private long inserePosicaoNo(Onibus onibus) {
		long chave = 0;

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gerenciador";
			this.conn = DriverManager.getConnection(url, "postgres", "curup1ras");

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data = formatter.format(onibus.getHoraAtualizacao());

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO posicao (datahora, onibus, linha, velocidade, geo_ponto_rede_pto) "
							+ "(SELECT TIMESTAMP '" + data
							+ "', ?, ?, ?, geo_ponto_rede_pto::geometry FROM no where nome = ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, onibus.getNome());
			ps.setString(2, onibus.getLinha().getNome());
			ps.setDouble(3, onibus.getVelocidade());
			ps.setString(4, onibus.getElementoGrafo().getNome());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				chave = rs.getInt(1);
			} else {
				logger.info("Posição em nó não inserida no banco: " + onibus);
			}

			ps.close();
			conn.close();

		} catch (

		Exception e) {
			logger.error("Erro ao tentar inserir prosição no banco de dados.", e);
		}

		logger.info("Posicao No 1 fid: " + chave);
		return chave;
	}

	private void inserePosicao2(Onibus onibus) {
		Posicao posicao = new Posicao();
		posicao.setDataHora(onibus.getHoraAtualizacao());
		posicao.setOnibus(onibus.getNome());
		posicao.setLinha(onibus.getLinha().getNome());
		posicao.setVelocidade(onibus.getVelocidade());

		if (onibus.getElementoGrafo() instanceof No) {
			posicao.setPonto(((No) onibus.getElementoGrafo()).getPonto());
		} 
		else if(onibus.getElementoGrafo() instanceof Arco){
			Geometry linha = ((Arco)onibus.getElementoGrafo()).getGeoLinha();
			LineMerger merger = new LineMerger();
			merger.add(linha);
			@SuppressWarnings("rawtypes")
			Collection mergedColl = merger.getMergedLineStrings();
			Geometry merged = linha.getFactory().buildGeometry(mergedColl);
			LineString lineString = (LineString)merged;
			Coordinate pt1 = lineString.getCoordinateN(0);
			Coordinate pt2 = lineString.getCoordinateN(lineString.getNumPoints() - 1);
			Coordinate coordinate = LinearLocation.pointAlongSegmentByFraction(pt1, pt2, onibus.getPosicao());
			Coordinate[] coordinates={coordinate};
			CoordinateSequence coordinateSequence = new CoordinateArraySequence(coordinates);
			posicao.setPonto(new Point(coordinateSequence, merged.getFactory()));
		}
		posicaoRepository.save(posicao);
		logger.info("Posicao 2 fid: " + posicao.getFid());
		

	}

	public long inserePosicaoArco(Onibus onibus) {

		long chave = 0;

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gerenciador";
			this.conn = DriverManager.getConnection(url, "postgres", "curup1ras");

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data = formatter.format(onibus.getHoraAtualizacao());

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO posicao (datahora, onibus, linha, velocidade, geo_ponto_rede_pto) "
							+ "(SELECT TIMESTAMP '" + data
							+ "', ?, ?, ?, ST_Line_Interpolate_Point(ST_LineMerge(geo_linhas_lin), ?)::geometry FROM arco where fid = ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, onibus.getNome());
			ps.setString(2, onibus.getLinha().getNome());
			ps.setDouble(3, onibus.getVelocidade());
			ps.setDouble(4, onibus.getPosicao());
			ps.setInt(5, onibus.getElementoGrafo().getNumero());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				chave = rs.getInt(1);
			} else {
				logger.info("Posição de em arco não inserida no banco: " + onibus);
			}

			ps.close();
			conn.close();

		} catch (

		Exception e) {
			logger.error("Erro ao tentar inserir posição no banco de dados.", e);
		}

		logger.info("Posicao Arco 1 fid: " + chave);
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
			} else {
				logger.info("Latitude e longitude não atualizadas: " + onibus);
			}

			s.close();
			conn.close();

		} catch (

		Exception e) {
			logger.error("Erro ao tentar inserir prosição no banco de dados.", e);
		}
	}

}
