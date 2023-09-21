package com.example.application.data.service;

import com.example.application.data.entity.Empresa;
import com.example.application.data.entity.RamoAtividade;
import com.example.application.data.repository.EmpresaRepository;
import com.example.application.data.repository.RamoAtividadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final EmpresaRepository empresaRepository;
    private final RamoAtividadeRepository ramoAtividadeRepository;

    public CrmService(EmpresaRepository empresaRepository,
                      RamoAtividadeRepository ramoAtividadeRepository){

        this.empresaRepository = empresaRepository;
        this.ramoAtividadeRepository = ramoAtividadeRepository;
    }

    public List<Empresa> findAllEmpresas(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return empresaRepository.findAll();
        } else {
            return empresaRepository.search(filterText);
        }
    }

    public long countEmpresas(){
        return empresaRepository.count();
    }

    public void deleteEmpresa(Empresa empresa){
        empresaRepository.delete(empresa);
    }

    public void saveEmpresa(Empresa empresa){
        if(empresa == null){
            System.err.println("Empresa is null");
            return;
        }

        empresaRepository.save(empresa);
    }

    public List<RamoAtividade> findAllRamoAtividades(){
        return ramoAtividadeRepository.findAll();
    }
}
