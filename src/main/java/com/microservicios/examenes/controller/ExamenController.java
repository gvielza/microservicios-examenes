package com.microservicios.examenes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.commons.controller.CommonController;
import com.microservicios.commons.examenes.models.entity.Examen;
import com.microservicios.commons.examenes.models.entity.Pregunta;
import com.microservicios.examenes.services.ExamenService;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Examen> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Examen examenDB = o.get();
		examenDB.setNombre(examen.getNombre());
		List<Pregunta> eliminadas = new ArrayList<Pregunta>();
		examenDB.getPreguntas().forEach(pdb -> {
			if (!examen.getPreguntas().contains(pdb)) {
				eliminadas.add(pdb);
			}
		});
		// de igual forma eliminadas.forEach(examenDb::removePregunta);
		eliminadas.forEach(p -> {
			examenDB.removePregunta(p);
		});
		examenDB.setPreguntas(examen.getPreguntas());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDB));
	}
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok(service.findByNombre(term));
	}
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
	
}
