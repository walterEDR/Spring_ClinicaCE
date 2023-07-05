package com.ues.edu.clinicace.servicio;

import com.ues.edu.clinicace.modelo.Medico;

import java.util.List;

public interface IMedicoService extends ICRUD<Medico> {
    List<Medico> buscarMedico(String filtro);

}
