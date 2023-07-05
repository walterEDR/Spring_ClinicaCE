package com.ues.edu.clinicace.repositorio;


import com.ues.edu.clinicace.modelo.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicoRep extends JpaRepository<Medico, Integer> {


    // para el filtro de busqueda

    @Query("from Medico m where (LOWER(m.nombreMedico) like %:filtro% or LOWER(m.apellidoMedico) like %:filtro%)")
    List<Medico> buscarMedico(@Param("filtro") String filtro);

}
