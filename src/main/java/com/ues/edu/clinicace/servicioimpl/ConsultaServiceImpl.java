package com.ues.edu.clinicace.servicioimpl;

import com.ues.edu.clinicace.modelo.Consulta;
import com.ues.edu.clinicace.repositorio.IConsultaRep;
import com.ues.edu.clinicace.servicio.IConsultaService;
import com.ues.edu.clinicace.servicio.IReportesServicePDF;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor //ESTO SUSTITUYE A @Autowired
@Service
public class ConsultaServiceImpl implements IConsultaService {

    private final IConsultaRep consultaRepo;

    private final IReportesServicePDF  servicioReportes;

    @Override
    public void generarReportePorConsultaParams(HttpServletResponse response,
                                          int idEspecialidadParam,
                                          String fechaConsultaParam) throws IOException{
        final InputStream stream = this.getClass().getResourceAsStream("/reporte/ConsultaEspecialidad2.jrxml");
        this.servicioReportes.generaReporte(stream, response, consultaRepo.totalConsultaPacientes(idEspecialidadParam,fechaConsultaParam));
    }

    @Override
    public void generarReportePorConsulta(HttpServletResponse response) throws IOException{
        final InputStream stream = this.getClass().getResourceAsStream("/reporte/ConsultaEspecialidad2.jrxml");
        this.servicioReportes.generaReporte(stream, response, consultaRepo.totalConsultasPacientes2());
    }

    // @RequiredArgsConstructor ESTO SUSTITUYE A @Autowired
	/*
	@Autowired
	public ConsultaServiceImpl(IConsultaRepo consultaRepo) {
		// TODO Auto-generated constructor stub
		this.consultaRepo = consultaRepo;
	}
*/


    @Override
    public Consulta registrar(Consulta obj) {
        return this.consultaRepo.save(obj);
    }

    @Override
    public Consulta modificar(Consulta obj) {
        return null;
    }

    @Override
    public List<Consulta> listar() {
        List<Consulta> listConsultas = this.consultaRepo.findAll();
        return listConsultas;
    }

    @Override
    public Consulta leerPorId(Integer id) {
        return this.consultaRepo.findById(id).get();
    }

    @Override
    public boolean eliminar(Consulta obj) {
        try {
            this.consultaRepo.delete(obj);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }


/*
    @Override
    public void generarReporteConsultaPorMedico(HttpServletResponse response) throws IOException {

    }
  /*



@Override
public void generarReportePorConsultaGraficoBarras(HttpServletResponse response,
                                      int idEspecialidadParam,
                                      String fechaConsultaParam) throws IOException{
    final InputStream stream = this.getClass().getResourceAsStream("/reporte/CantidadConsultasAtendidas.jrxml");
    this.servicioReportes.generaReporte(stream, response, consultaRepo.cantidadConsultaPorEspecialidadGrafBarras());
}

     */

}
