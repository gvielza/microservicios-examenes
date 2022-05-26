package com.microservicios.examenes.services;

import java.util.List;

import com.formacionbdi.microservicios.commons.service.CommonService;
import com.microservicios.commons.examenes.models.entity.Asignatura;
import com.microservicios.commons.examenes.models.entity.Examen;

public interface ExamenService extends CommonService<Examen>{
	public List<Examen> findByNombre(String term);
	public Iterable<Asignatura> findAllAsignaturas();

}
