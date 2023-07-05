package com.ues.edu.clinicace.controller;


import com.ues.edu.clinicace.controller.MedicoController;
import com.ues.edu.clinicace.dto.ConsultaDTO;
import com.ues.edu.clinicace.dto.ConsultaDtoIN;
import com.ues.edu.clinicace.modelo.*;
import com.ues.edu.clinicace.servicio.IConsultaService;
import com.ues.edu.clinicace.servicio.IEspecialidadService;
import com.ues.edu.clinicace.servicio.IMedicoService;
import com.ues.edu.clinicace.servicio.IPacienteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/consulta")

public class ConsultaController {

   private final IConsultaService consultaService;
    private final IEspecialidadService servicioEspecialidad;
    private final IMedicoService servicioMedico;
    private final IPacienteService servicePaciente;
    private Medico medico;
    private Especialidad especialidad;
    private Paciente paciente;
    private Consulta consulta;


    @Autowired
    public ConsultaController( IConsultaService consultaService, IEspecialidadService servicioEspecialidad, IMedicoService servicioMedico, IPacienteService servicePaciente) {
      this.consultaService = consultaService;
        this.servicioEspecialidad = servicioEspecialidad;
        this.servicioMedico = servicioMedico;
        this.servicePaciente = servicePaciente;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> mostrarConsulta(){
        List<Consulta> consultas = this.consultaService.listar();
        return  new ResponseEntity<List<Consulta>>(consultas,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> consultaById(@PathVariable("id") Integer id){
        Consulta consulta = this.consultaService.leerPorId(id);
        ConsultaDTO consultaDTO = null;
        if (consulta!=null){
            consultaDTO = new ConsultaDTO(
                    consulta.getIdConsulta(),
                    consulta.getPaciente().getIdPaciente(),
                    consulta.getMedico().getIdMedico(),
                    consulta.getEspecialidad().getIdEspecialidad(),
                    consulta.getNumConsultorio(),
                    consulta.getFechaConsulta(),
                    consulta.getHoraConsulta()
            );
        }
        return new ResponseEntity<ConsultaDTO>(consultaDTO, HttpStatus.OK);
    }

    @GetMapping("/hateos")
    public ResponseEntity<List<ConsultaDTO>> getAllHATEOConsulta(){
        List<Consulta> listConsulta = this.consultaService.listar();
        List<ConsultaDTO> listConsultaDTO = new ArrayList<>();
        if (listConsulta.size()>0){
            listConsulta.stream().forEach(c -> {
                ConsultaDTO consultaDTO = new ConsultaDTO(
                        c.getIdConsulta(),
                        c.getPaciente().getIdPaciente(),
                        c.getMedico().getIdMedico(),
                        c.getEspecialidad().getIdEspecialidad(),
                        c.getNumConsultorio(),
                        c.getFechaConsulta(),
                        c.getHoraConsulta()
                );
                //Link de medico
                Link medicoLink = linkTo(methodOn(MedicoController.class)
                        .medicoById(c.getMedico().getIdMedico())).withRel("medico");
                consultaDTO.add(medicoLink);
                listConsultaDTO.add(consultaDTO);

                //Link de Paciente
                Link pacienteLink = linkTo(methodOn(PacienteController.class)
                        .pacienteById(c.getPaciente().getIdPaciente())).withSelfRel();
                consultaDTO.add(pacienteLink);
                listConsultaDTO.add(consultaDTO);

                //Link de Paciente
                Link especialidadLink = linkTo(methodOn(EspecialidadController.class)
                        .especialidadById(c.getEspecialidad().getIdEspecialidad())).withSelfRel();
                consultaDTO.add(especialidadLink);
                listConsultaDTO.add(consultaDTO);

            });
        }
        return new ResponseEntity<List<ConsultaDTO>>(listConsultaDTO, HttpStatus.OK);
    }

    //con consultaDtoIN
    @PostMapping("/insertar")
    public ResponseEntity<GenericResponse<ConsultaDtoIN>> guardarConsulta(@RequestBody ConsultaDtoIN consultaDtoIN){
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        GenericResponse<ConsultaDtoIN> resp = new GenericResponse<ConsultaDtoIN>(0,
                "ERROR DE ALMACENAMIENTO DE LA CONSULTA", consultaDtoIN);
        Optional<ConsultaDtoIN> opt = Optional.ofNullable(consultaDtoIN);

        if (opt.isPresent()) {
            this.medico = new Medico();
            this.especialidad = new Especialidad();
            this.paciente = new Paciente();
            this.consulta = new Consulta();
            this.consulta.setMedico(this.servicioMedico.leerPorId(consultaDtoIN.getIdMedico()));
            this.consulta.setEspecialidad(this.servicioEspecialidad.leerPorId(consultaDtoIN.getIdEspecialidad()));
            this.consulta.setPaciente(this.servicePaciente.leerPorId(consultaDtoIN.getIdPaciente()));
            this.consulta.setFechaConsulta(consultaDtoIN.getFechaConsulta());
            this.consulta.setHoraConsulta(consultaDtoIN.getHoraConsulta());
            this.consulta.setNumConsultorio(consultaDtoIN.getNumConsultorio());
            if (consultaDtoIN.getDetalleConsulta().size() > 0){
                consultaDtoIN.getDetalleConsulta().stream().peek(d -> d.setConsulta(consulta))
                        .collect(Collectors.toList());
                try{
                    this.consultaService.registrar(consulta);
                    resp.setCode(1);
                    resp.setMessage("Exito - Consulta almacenada EXITOSAMENTE");
                    http = HttpStatus.OK;
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        } else {
            resp.setCode(0);
            resp.setMessage("Fallo - No se encontro consulta");
        }
        return new ResponseEntity<GenericResponse<ConsultaDtoIN>>(resp, http);
    }

    //Insercion de datos sin consultaDTOIn
    @PostMapping
    public ResponseEntity<GenericResponse<Consulta>> saveConsult(@RequestBody Consulta consulta){
        GenericResponse<Consulta> response = new GenericResponse<>(0, "Fallo - No pudo alnacenarse la conculta", consulta);
        Optional<Consulta> opt = Optional.ofNullable(consulta);
        Consulta conSelect =  new Consulta();
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        System.out.println(consulta);

        if (opt.isPresent()){
            if (consulta.getDetalleConsulta().size() > 0){
                consulta.getDetalleConsulta()
                        .stream()
                        .peek(dt -> dt.setConsulta(consulta))
                        .collect(Collectors.toList());
                try{
                    conSelect = this.consultaService.registrar(consulta);
                    response.setCode(1);
                    response.setMessage("Exito - Se almaceno la consulta");
                    response.setResponse(conSelect);
                } catch (Exception e) {
                    response.setMessage("fallo - error" + e.getMessage());
                }
            }
        }
        System.out.println(response);
        return new ResponseEntity<GenericResponse<Consulta>>(response, HttpStatus.OK);
    }
    // Metodo para generar archivo pdf
    @GetMapping(value = "/pdf")
    public void listConsultaMedicasPorEspecialidadPdf(ModelAndView model, HttpServletResponse response
                                                     ) throws IOException{
        this.consultaService.generarReportePorConsulta(response);
    }

    /* metodo de la melva
    @GetMapping(value = "/pdf")
    public void listConsultaMedicasPorEspecialidadPdf(ModelAndView model, HttpServletResponse response,
                                                      @RequestParam int idEspecialidadParam,
                                                      @RequestParam String fechaConsultaParam) throws IOException{
        this.consultaService.generarReportePorConsulta(response,idEspecialidadParam, fechaConsultaParam);
    }
*/
    /*
    @GetMapping(value = "/report/pdf")
    public void cantidadConsultaMedicasPorEspecialidadPdf(ModelAndView model, HttpServletResponse response) throws IOException{
        this.consultaService.genrarReportePorConsultaGraficoBarras(response);
    }
*/


}