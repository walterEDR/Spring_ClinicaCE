package com.ues.edu.clinicace.servicioimpl;

import com.ues.edu.clinicace.modelo.Paciente;
import com.ues.edu.clinicace.repositorio.IPacienteRepo;
import com.ues.edu.clinicace.servicio.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements IPacienteService {

    @Autowired
    private final IPacienteRepo servicioPaciente;

    @Autowired
    public PacienteServiceImpl(IPacienteRepo servicioPaciente) {
        this.servicioPaciente = servicioPaciente;
    }

    @Override
    public Paciente registrar(Paciente obj) {
        return this.servicioPaciente.save(obj);
    }

    @Override
    public Paciente modificar(Paciente obj) {
        return this.servicioPaciente.save(obj);
    }

    @Override
    public List<Paciente> listar() {
        List<Paciente> listPacientes = this.servicioPaciente.findAll();
        return listPacientes;
    }

    @Override
    public Paciente leerPorId(Integer id) {
        return this.servicioPaciente.findById(id).get();
    }

    @Override
    public boolean eliminar(Paciente obj) {
        try{
            this.servicioPaciente.delete(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Paciente> buscarPaciente(String filtro) {
        return this.servicioPaciente.buscarPaciente(filtro);
    }
}
