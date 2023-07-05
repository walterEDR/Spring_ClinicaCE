package com.ues.edu.clinicace.servicioimpl;


import com.ues.edu.clinicace.modelo.Paciente;
import com.ues.edu.clinicace.repositorio.IPacienteRepo;
import com.ues.edu.clinicace.servicio.IReportesServiceEXCEL;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class ReportesServiceEXCELImpl implements IReportesServiceEXCEL {

    @Autowired
    private IPacienteRepo pacienteRepo;

    @Override
    public  void generateExcel(HttpServletResponse response) throws IOException{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Pacientes");
        Row filaTitulo = sheet.createRow(0);
        Cell celda = filaTitulo.createCell(1);
        celda.setCellValue("LISTADO GENERAL DE PACIENTES");
        Row filaData = sheet.createRow(2);
        int dataRowIndice = 1;
        String[] columnas = {"ID", "NOMBRE", "APELLIDO", "IDENTIFICACION", "DIRECCION"};
        for (int i=0; i<columnas.length;i++){
            celda = filaData.createCell(i);
            celda.setCellValue(columnas[i]);
        }
        int dataRowIndex = 4;
        List<Paciente> pacientes = pacienteRepo.findAll();
        for (Paciente datos: pacientes){
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(datos.getIdPaciente());
            dataRow.createCell(1).setCellValue(datos.getNombrePaciente());
            dataRow.createCell(2).setCellValue(datos.getApellidoPaciente());
            dataRow.createCell(3).setCellValue(datos.getIdentPaciente());
            dataRow.createCell(4).setCellValue(datos.getDireccionPaciente());
            dataRowIndex++;
        }
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }

}
