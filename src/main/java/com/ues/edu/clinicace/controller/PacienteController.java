package com.ues.edu.clinicace.controller;

import com.ues.edu.clinicace.modelo.GenericResponse;
import com.ues.edu.clinicace.modelo.Paciente;
import com.ues.edu.clinicace.servicio.IPacienteService;
import com.ues.edu.clinicace.servicioimpl.ReportesServiceEXCELImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private final ReportesServiceEXCELImpl reportesServiceEXCEL;
    private final IPacienteService servicioPaciente;



    public PacienteController(IPacienteService servicioPaciente, ReportesServiceEXCELImpl reportesServiceEXCEL) {
        this.reportesServiceEXCEL = reportesServiceEXCEL;
        this.servicioPaciente = servicioPaciente;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> showPatients(){
        List<Paciente> patients = this.servicioPaciente.listar();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<Paciente> pacienteById(@PathVariable("idPaciente") Integer idPaciente){
        Paciente paciente= this.servicioPaciente.leerPorId(idPaciente);
        return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
    }

    @GetMapping("/{filter}")
    public ResponseEntity<List<Paciente>> searchPatient(@PathVariable("filter") String filter){
        List<Paciente> patinents = this.servicioPaciente.buscarPaciente(filter);
        return new ResponseEntity<List<Paciente>>(patinents, HttpStatus.OK);
    }

    @PostMapping
    public Paciente savePatient( @RequestBody Paciente patient){
        return this.servicioPaciente.registrar(patient);
    }

    @PutMapping
    public ResponseEntity<GenericResponse<Paciente>> editPatient(@RequestBody Paciente patient){
        Optional<Paciente> opt = Optional.ofNullable(this.servicioPaciente.leerPorId(patient.getIdPaciente()));
        GenericResponse<Paciente> response;
        Paciente patientResponse;

        if (opt.isEmpty()){
            patientResponse = savePatient(patient);
            System.out.println(patient.getNombrePaciente() + " " + patient.getApellidoPaciente());
            response =  new GenericResponse<Paciente>(1, "Paciente guardado con exito", patientResponse);
            return new ResponseEntity<GenericResponse<Paciente>>(response, HttpStatus.OK);
        } else {
            response = new GenericResponse<Paciente>(0, "Paciente no fue guardado", patient);
            return new ResponseEntity<GenericResponse<Paciente>>(response, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Paciente>> deletePatient(@PathVariable("id") Integer id){
        Optional<Paciente> opt = Optional.ofNullable(this.servicioPaciente.leerPorId(id));
        GenericResponse<Paciente> response =  new GenericResponse<Paciente>();
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;

        if (opt.isPresent()){
            if(this.servicioPaciente.eliminar(opt.get())){
                response.setCode(1);
                response.setMessage("Exito - Se elimino al paciente");
                response.setResponse(opt.get());
            } else {
                response.setCode(0);
                response.setMessage("Fallo - No se pudo eliminar al paciente");
                response.setResponse(opt.get());
            }
        } else {
            response.setCode(0);
            response.setMessage("Fallo - No hay paciente que eliminar");
        }
        return new ResponseEntity<GenericResponse<Paciente>>(response, http);
    }


    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception{

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=pacientes.xls";
        response.setHeader(headerKey, headerValue);
        reportesServiceEXCEL.generateExcel(response);
        response.flushBuffer();
    }

}
