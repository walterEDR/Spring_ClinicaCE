package com.ues.edu.clinicace.repositorio;

import com.ues.edu.clinicace.modelo.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPacienteRepo extends JpaRepository<Paciente, Integer> {
    //Para el filtro de busqueda
    @Query("from Paciente p where (LOWER(p.nombrePaciente) like %:filtro% or LOWER(p.apellidoPaciente) like %:filtro%)")
    List<Paciente> buscarPaciente(@Param("filtro") String filtro);
}
