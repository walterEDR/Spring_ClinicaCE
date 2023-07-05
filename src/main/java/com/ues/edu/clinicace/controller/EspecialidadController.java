package com.ues.edu.clinicace.controller;

import com.ues.edu.clinicace.modelo.Especialidad;
import com.ues.edu.clinicace.modelo.GenericResponse;
import com.ues.edu.clinicace.servicio.IEspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/especialidad")
public class EspecialidadController {
    private final IEspecialidadService servicioEspecialidad;

    @Autowired
    public EspecialidadController(IEspecialidadService servicioEspecialidad) {
        this.servicioEspecialidad = servicioEspecialidad;
    }

    @GetMapping
    public ResponseEntity<List<Especialidad>> getSpeciality(){
        List<Especialidad> specialitys = this.servicioEspecialidad.listar();
        return new ResponseEntity<>(specialitys, HttpStatus.OK);
    }

    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<Especialidad> especialidadById(@PathVariable("idEspecialidad") Integer idEspecialidad){
        Especialidad especialidad = this.servicioEspecialidad.leerPorId(idEspecialidad);
        return new ResponseEntity<Especialidad>(especialidad,HttpStatus.OK);
    }

    @PostMapping
    public Especialidad saveSpeciality(@RequestBody Especialidad speciality){
        return this.servicioEspecialidad.registrar(speciality);
    }

    @PutMapping
    public ResponseEntity<GenericResponse<Especialidad>> editSepeciality(@RequestBody Especialidad speciality){
        Optional<Especialidad> opt = Optional.ofNullable(this.servicioEspecialidad.leerPorId(speciality.getIdEspecialidad()));
        GenericResponse<Especialidad> response;
        Especialidad specialityResponse;
        System.out.println("prev "+ speciality.getIdEspecialidad() + " " + speciality.getNombreEspeciadad());
        if (opt.isPresent()){
            specialityResponse = saveSpeciality(speciality);
            System.out.println(speciality.getNombreEspeciadad());
            response = new GenericResponse<Especialidad>(1,"Especialidad guardada con exito", specialityResponse);
            return new ResponseEntity<GenericResponse<Especialidad>>( response, HttpStatus.OK);
        } else {
            response = new GenericResponse<Especialidad>(0, "La especialidad no fue guardada", speciality);
            return new ResponseEntity<GenericResponse<Especialidad>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Especialidad>> deleteSpeciality(@PathVariable("id") Integer id){
        Optional<Especialidad> opt = Optional.ofNullable(this.servicioEspecialidad.leerPorId(id));
        GenericResponse<Especialidad> response = new GenericResponse<>();
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;

        if (opt.isPresent()){
            if (this.servicioEspecialidad.eliminar(opt.get())){
                response.setCode(1);
                response.setMessage("Exito - Se elimino la especialidad");
                response.setResponse(opt.get());
            } else {
                response.setCode(0);
                response.setMessage("Fallo - No pudo elminarse la especialidad");
                response.setResponse(opt.get());
            }
        } else {
            response.setCode(0);
            response.setMessage("Fallo - no hay especialidad que eliminar");
        }
        return new ResponseEntity<GenericResponse<Especialidad>>(response, http);
    }
}
