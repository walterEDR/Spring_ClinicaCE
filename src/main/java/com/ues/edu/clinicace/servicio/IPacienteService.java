package com.ues.edu.clinicace.servicio;

import com.ues.edu.clinicace.modelo.Paciente;

import java.util.List;

public interface IPacienteService extends ICRUD<Paciente> {
    List<Paciente> buscarPaciente(String filtro);
}
