package com.example.application.data.repository;

import com.example.application.data.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    @Query("select e from Empresa e "+
    "where lower(e.nomeFantasia) like lower(concat('%', :searchTerm, '%'))")
    List<Empresa> search(@Param("searchTerm") String searchTerm);
}
