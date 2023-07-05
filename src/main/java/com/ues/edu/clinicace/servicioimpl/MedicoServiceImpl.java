package com.ues.edu.clinicace.servicioimpl;


import com.ues.edu.clinicace.modelo.Medico;
import com.ues.edu.clinicace.repositorio.IMedicoRep;
import com.ues.edu.clinicace.servicio.IMedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class MedicoServiceImpl implements IMedicoService {

    private final IMedicoRep servicioMedico;

   /*
    @Autowired
    public MedicoServiceImpl(IMedicoRep servicioMedico) {
        // TODO Auto-generated constructor stub
        this.servicioMedico = servicioMedico;
    }
*/
    @Override
    public Medico registrar(Medico obj) {
        // TODO Auto-generated method stub
        return this.servicioMedico.save(obj);
    }

    @Override
    public Medico modificar(Medico obj) {
        // TODO Auto-generated method stub
        //return null;
        return this.servicioMedico.save(obj);
    }

    @Override
    public List<Medico> listar() {
        List<Medico> listMedicos= this.servicioMedico.findAll();
        return listMedicos;
    }

    @Override
    public Medico leerPorId(Integer id) {
        // TODO Auto-generated method stub
        return this.servicioMedico.findById(id).get();
    }

    @Override
    public boolean eliminar(Medico obj) {
        // TODO Auto-generated method stub
        try {
            this.servicioMedico.delete(obj);
            return true;
        } catch (Exception e) {
            // TODO: handle exception

            return false;
        }

    }


    @Override
    public List<Medico> buscarMedico(String filtro) {
        return null;
    }
}
