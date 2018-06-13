package br.unb.cic.simuladortrafego.dominio.tempoviagem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TempoViagemRepository extends JpaRepository<TempoViagem, Long> {

	TempoViagem findById(long id);

}
