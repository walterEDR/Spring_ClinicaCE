package com.ues.edu.clinicace.repositorio;

import com.ues.edu.clinicace.modelo.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface IEspecialidadRepo extends JpaRepository<Especialidad, Integer> {
}
