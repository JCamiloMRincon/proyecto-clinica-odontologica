package com.dh.clas35.services;

import com.dh.clas35.entities.*;
import com.dh.clas35.repository.IOdontologoRepository;
import com.dh.clas35.repository.IPacienteRepository;
import com.dh.clas35.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements ApplicationRunner {

    private IUsuarioRepository usuarioRepository;
    private IPacienteRepository pacienteRepository;
    private IOdontologoRepository odontologoRepository;

    @Autowired
    public DataLoader(IUsuarioRepository usuarioRepository, IPacienteRepository pacienteRepository, IOdontologoRepository odontologoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String hashUser = passwordEncoder.encode("camilo123");
        String hashAdmin = passwordEncoder.encode("admin456");

        Usuario usuario =
                new Usuario("Camilo","jcmortigor","camilo@dh.com", hashUser, UsuarioRol.ROLE_USER);
        Usuario admin =
                new Usuario("Admin","admin","admin@dh.com", hashAdmin, UsuarioRol.ROLE_ADMIN);

        Paciente pacientePrueba =
                new Paciente("Prueba", "Paciente", "paciente@prueba.com", 123456789, LocalDate.now(),
                        new Domicilio("Avenida Siempre Viva", 742, "Springfield", "Springfield"));
        Odontologo odontologoPrueba =
                new Odontologo("Prueba", "Odontologo", 123456789);

        usuarioRepository.save(usuario);
        usuarioRepository.save(admin);
        pacienteRepository.save(pacientePrueba);
        odontologoRepository.save(odontologoPrueba);
    }

}
