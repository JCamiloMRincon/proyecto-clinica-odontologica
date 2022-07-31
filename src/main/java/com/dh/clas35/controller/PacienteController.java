package com.dh.clas35.controller;

import com.dh.clas35.entities.Paciente;
import com.dh.clas35.exceptions.BadRequestException;
import com.dh.clas35.exceptions.ResourceNotFoundException;
import com.dh.clas35.services.PacienteServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger logger = Logger.getLogger(PacienteController.class);
    @Autowired
    private PacienteServiceImp pacienteService;

    @Operation(summary = "Lista de todos los pacientes registrados")
    @GetMapping
    public ResponseEntity<List<Paciente>> traerPacientes() {
        List<Paciente> listaDePacientes = pacienteService.listarPacientes();
        if (listaDePacientes.size() == 0) {
            logger.warn("Operación exitosa. Sin embargo, no hay pacientes registrados");
        } else {
            logger.info("Operación exitosa. Número de pacientes registrados: " + listaDePacientes.size());
        }
        return ResponseEntity.ok(listaDePacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> traerPacientePorId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(id);
        if (pacienteBuscado.isPresent()) {
            logger.info("Operación exitosa. Se consultó el paciente con id: " + id);
            return ResponseEntity.ok(pacienteBuscado.get());
        } else {
            logger.error("Operación fallida. El paciente no existe");
            throw new ResourceNotFoundException("El paciente con id: " + id + " no existe. " +
                    "¿Ingresó un id correcto?");
        }
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarNuevoPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        if (paciente.getDni() != 0) {
            logger.info("Operación exitosa. Se registró el paciente " +
                    paciente.getApellido() + " " + paciente.getNombre() +
                    " con DNI: " + paciente.getDni());
            return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
        } else {
            logger.error("Operación fallida. El paciente no puede tener un dni igual a 0");
            throw new BadRequestException("El DNI no puede ser 0. Inténtelo de nuevo");
        }
    }

    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(paciente.getId());
        if (pacienteBuscado.isPresent()) {
            logger.info("Operación exitosa. Se actualizó el paciente con id: " + pacienteBuscado.get().getId());
            return ResponseEntity.ok(pacienteService.actualizarPaciente(paciente));
        } else {
            logger.error("Operación fallida. El paciente no existe");
            throw new ResourceNotFoundException("El paciente con id: " + paciente.getId() + " no existe. " +
                    "¿Ingresó un id correcto?");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(id);
        if (pacienteBuscado.isPresent()) {
            pacienteService.eliminarPaciente(id);
            logger.info("Operación exitosa. Se eliminó el paciente con id: " + id);
            return ResponseEntity.ok("El paciente  con id: " + id + " fue eliminado correctamente");
        } else {
            logger.error("Operación fallida. El paciente no existe");
            throw new ResourceNotFoundException("El paciente con id: " + id + " no existe. " +
                    "¿Ingresó un id correcto?");
        }
    }

}
