package com.ues.edu.clinicace.servicio;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IReportesServiceEXCEL {
    void generateExcel(HttpServletResponse response) throws IOException;
}
