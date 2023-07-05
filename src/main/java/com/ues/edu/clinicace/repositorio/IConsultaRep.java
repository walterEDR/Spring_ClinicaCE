package com.ues.edu.clinicace.repositorio;

import com.ues.edu.clinicace.dto.IConsMedicaDtoRep;
import com.ues.edu.clinicace.dto.IConsultasMedicasDTOReporte;
import com.ues.edu.clinicace.dto.ITotaLConsultasMedicasPorEspecReporteDTO;
import com.ues.edu.clinicace.modelo.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConsultaRep extends JpaRepository<Consulta, Integer>{


    @Query(value=" SELECT consulta.fecha_consulta as fechaConsulta, consulta.hora_consulta as horaConsulta, consulta.num_consultorio as numConsultorio, especialidad.nombre_especiadad as nombreEspeciadad, concat_ws(' ', medico.nombre_medico, medico.apellido_medico) as nombreCompletoMedico, concat_ws(' ', paciente.nombre_paciente, paciente.apellido_paciente) as nombreCompletoPaciente FROM consulta INNER JOIN especialidad ON consulta.id_especialidad = especialidad.id_especialidad INNER JOIN medico ON consulta.id_medico = medico.id_medico INNER JOIN paciente ON consulta.id_paciente = paciente.id_paciente ORDER BY especialidad.nombre_especiadad",nativeQuery=true)
    List<IConsMedicaDtoRep> totalConsultasPacientes();


    @Query(value = "SELECT especialidad.id_especialidad as codigoEspecialidad, consulta.fecha_consulta as fechaConsulta, consulta.hora_consulta as horaConsulta, consulta.num_consultorio as numConsultorio, especialidad.nombre_especiadad as nombreEspecialidad, concat_ws(' ', medico.nombre_medico, medico.apellido_medico) as nombreCompletoMedico, concat_ws(' ', paciente.nombre_paciente, paciente.apellido_paciente) as nombreCompletoPaciente FROM consulta INNER JOIN especialidad ON consulta.id_especialidad = especialidad.id_especialidad INNER JOIN medico ON consulta.id_medico = medico.id_medico INNER JOIN paciente ON consulta.id_paciente = paciente.id_paciente HAVING especialidad.id_especialidad=:codigoEspecialidadParam AND consulta.fecha_consulta=:fechaConsultaParam",nativeQuery=true)
    List<IConsultasMedicasDTOReporte> totalConsultaPacientes(int codigoEspecialidadParam,String fechaConsultaParam);

    @Query(value=
            "SELECT consulta.fecha_consulta as fechaConsulta,\n" +
                    "consulta.hora_consulta as horaConsulta, consulta.num_consultorio as\n" +
                    "numConsultorio, especialidad.nombre_especiadad as nombreEspecialidad,\n" +
                    "concat_ws(' ', medico.nombre_medico, medico.apellido_medico) as\n" +
                    "nombreCompletoMedico, concat_ws(' ', paciente.nombre_paciente,\n" +
                    "paciente.apellido_paciente) as nombreCompletoPaciente FROM consulta\n" +
                    "INNER JOIN especialidad ON consulta.id_especialidad =\n" +
                    "especialidad.id_especialidad INNER JOIN medico ON consulta.id_medico =\n" +
                    "medico.id_medico INNER JOIN paciente ON consulta.id_paciente =\n" +
                    "paciente.id_paciente ORDER BY\n" +
                    "especialidad.nombre_especiadad", nativeQuery=true
    )
    List<IConsultasMedicasDTOReporte> totalConsultasPacientes2();


    @Query(value = "SELECT e.nombre_especiadad as nombreEspecialidad,\n" +
            "COUNT(id_consulta) as cantidadConsulta\n" +
            "From especialidad e\n" +
            "INNER JOIN consulta c\n" +
            "ON e.id_especialidad = c.id_especialidad\n" +
            "GROUP BY e.id_especialidad\n" +
            "ORDER BY e.id_especialidad DESC\n",nativeQuery=true)
    List<ITotaLConsultasMedicasPorEspecReporteDTO> cantidadConsultaPorEspecialidadGrafBarras();



}
