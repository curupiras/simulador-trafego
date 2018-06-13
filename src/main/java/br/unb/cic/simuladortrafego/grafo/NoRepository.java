package br.unb.cic.simuladortrafego.grafo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoRepository extends JpaRepository<No, Long> {

	List<No> findByLinhaOrderById(String linha);

}
