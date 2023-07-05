package com.ues.edu.clinicace.servicio;

import com.ues.edu.clinicace.modelo.Consulta;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IConsultaService extends ICRUD<Consulta> {
    void generarReportePorConsultaParams(HttpServletResponse response,
                                   int idEspecialidadParam,
                                   String fechaConsultaParam) throws IOException;
    void generarReportePorConsulta(HttpServletResponse response) throws IOException;
   //void generarReportePorConsultaGraficoBarras(HttpServletResponse response) throws IOException;


}
