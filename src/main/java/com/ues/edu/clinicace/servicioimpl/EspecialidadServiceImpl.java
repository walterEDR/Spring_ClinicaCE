package com.ues.edu.clinicace.servicioimpl;

import com.ues.edu.clinicace.modelo.Especialidad;
import com.ues.edu.clinicace.repositorio.IEspecialidadRepo;
import com.ues.edu.clinicace.servicio.IEspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadServiceImpl implements IEspecialidadService {

    @Autowired
    private final IEspecialidadRepo servicioEspecialidad;

    @Autowired
    public EspecialidadServiceImpl(IEspecialidadRepo servicioEspecialidad) {
        this.servicioEspecialidad = servicioEspecialidad;
    }

    @Override
    public Especialidad registrar(Especialidad obj) {
        return this.servicioEspecialidad.save(obj);
    }

    @Override
    public Especialidad modificar(Especialidad obj) {
        return this.servicioEspecialidad.save(obj);
    }

    @Override
    public List<Especialidad> listar() {
        List<Especialidad> listEspecialidad = this.servicioEspecialidad.findAll();
        return listEspecialidad;
    }

    @Override
    public Especialidad leerPorId(Integer id) {
        return this.servicioEspecialidad.findById(id).orElse(null);
    }

    @Override
    public boolean eliminar(Especialidad obj) {
        try {
            this.servicioEspecialidad.delete(obj);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

   
}
