package br.unb.cic.simuladortrafego.onibus;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import com.vividsolutions.jts.linearref.LengthLocationMap;
import com.vividsolutions.jts.linearref.LinearLocation;
import com.vividsolutions.jts.linearref.LocationIndexedLine;
import com.vividsolutions.jts.operation.linemerge.LineMerger;

import br.unb.cic.simuladortrafego.grafo.Arco;
import br.unb.cic.simuladortrafego.grafo.No;

@Component
public class PosicaoDao {

	@Autowired
	private PosicaoRepository posicaoRepository;

	public void inserePosicao(Onibus onibus) {
		Posicao posicao = new Posicao();
		posicao.setDataHora(onibus.getHoraAtualizacao());
		posicao.setOnibus(onibus.getNome());
		posicao.setLinha(onibus.getLinha().getNome());
		posicao.setVelocidade(onibus.getVelocidade());
		posicao.setProcessado(false);

		if (onibus.getElementoGrafo() instanceof No) {
			posicao.setPonto(((No) onibus.getElementoGrafo()).getPonto());
		} else if (onibus.getElementoGrafo() instanceof Arco) {
			Geometry linha = ((Arco) onibus.getElementoGrafo()).getGeoLinha();
			LineMerger merger = new LineMerger();
			merger.add(linha);
			@SuppressWarnings("rawtypes")
			Collection mergedColl = merger.getMergedLineStrings();
			Geometry merged = linha.getFactory().buildGeometry(mergedColl);

			LengthLocationMap lengthLocationMap = new LengthLocationMap(merged);
			LocationIndexedLine locationIndexedLine = new LocationIndexedLine(merged);
			LinearLocation linearLocation = lengthLocationMap.getLocation(merged.getLength() * onibus.getPosicao());
			Coordinate result = locationIndexedLine.extractPoint(linearLocation);
			Coordinate[] coordinates = { result };
			CoordinateSequence coordinateSequence = new CoordinateArraySequence(coordinates);
			posicao.setPonto(new Point(coordinateSequence, merged.getFactory()));
		}
		posicaoRepository.save(posicao);
		Point point = (Point) posicao.getPonto();
		onibus.setLatitude(String.valueOf(point.getY()));
		onibus.setLongitude(String.valueOf(point.getX()));
	}

}
