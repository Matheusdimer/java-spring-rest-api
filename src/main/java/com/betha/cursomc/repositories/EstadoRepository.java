package com.betha.cursomc.repositories;

import com.betha.cursomc.domain.Cidade;
import com.betha.cursomc.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
