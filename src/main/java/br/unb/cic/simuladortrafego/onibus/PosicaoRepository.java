package br.unb.cic.simuladortrafego.onibus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicaoRepository extends JpaRepository<Posicao, Long> {
	Posicao findById(long id);
}
