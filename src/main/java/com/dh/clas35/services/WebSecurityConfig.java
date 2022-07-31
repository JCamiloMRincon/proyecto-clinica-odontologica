package com.dh.clas35.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UsuarioServiceImp usuarioService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurityConfig(UsuarioServiceImp usuarioService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioService = usuarioService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http.csrf().disable()
                .authorizeHttpRequests()
                //.antMatchers("/turnos").hasRole("USER")
                .antMatchers("/post_turno.html").hasRole("USER")
                //.antMatchers("/pacientes").hasRole("ADMIN")
                //.antMatchers("/odontologos").hasRole("ADMIN")
                .antMatchers("/get_all_odontologos.html").hasRole("ADMIN")
                .antMatchers("/get_all_pacientes.html").hasRole("ADMIN")
                .antMatchers("/post_odontologo.html").hasRole("ADMIN")
                .antMatchers("/post_paciente.html").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout();
    }

}
