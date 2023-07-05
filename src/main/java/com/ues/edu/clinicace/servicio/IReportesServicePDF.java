package com.ues.edu.clinicace.servicio;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IReportesServicePDF<T> {
    void generaReporte(InputStream stream, HttpServletResponse response, List<T> data) throws IOException;
    void generaReporteParams(InputStream stream, HttpServletResponse response, List<T> data) throws IOException;


}
