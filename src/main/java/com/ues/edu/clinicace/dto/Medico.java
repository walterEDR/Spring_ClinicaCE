package com.ues.edu.clinicace.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Medico implements Serializable {
    private Integer idMedico;
    private String nombreMedico;
    private String apellidoMedico;
    private String jvpm;

}
