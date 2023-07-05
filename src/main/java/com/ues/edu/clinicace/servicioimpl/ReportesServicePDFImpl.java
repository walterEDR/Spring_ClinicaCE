package com.ues.edu.clinicace.servicioimpl;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.ues.edu.clinicace.servicio.IReportesServicePDF;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;

@Service
public class ReportesServicePDFImpl<T> implements IReportesServicePDF<T> {


    @Override
    public void generaReporte(InputStream stream, HttpServletResponse response, List<T> data) throws IOException {
        try {
            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(data);
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Admin");



            // FIN de nuevo parametro
            final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, source);
            response.setContentType("application/x-pdf");
            response.setHeader("Content-disposition", "inline; filename=App_report_en.pdf");
            final OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (JRException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void generaReporteParams(InputStream stream, HttpServletResponse response, List<T> data) throws IOException {
        try {
            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(data);
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Admin");

            //Nuevos con parametros de idEspecialidad y fechaConsulta
            //final File imgLogo = ResourceUtils.getFile("classpath:images/logobufmpues.jpj");
            parameters.put("idEspecialidadParam","idEspecialidadParam");
            parameters.put("fechaConsultaParam","fechaConsultaParam");
            //parameters.put("imgLogo",new FileInputStream(imgLogo));

            // FIN de nuevo parametro
            final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, source);
            response.setContentType("application/x-pdf");
            response.setHeader("Content-disposition", "inline; filename=App_report_en.pdf");
            final OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (JRException e) {
            e.printStackTrace();
        }


    }
}