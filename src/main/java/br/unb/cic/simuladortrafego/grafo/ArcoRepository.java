package br.unb.cic.simuladortrafego.grafo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArcoRepository extends JpaRepository<Arco, Long> {

	List<Arco> findByLinhaOrderByFid(String linha);

}
