package com.example.proyectoapiv1.security;

import com.example.proyectoapiv1.service.Impl.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


//nos permite configurar la autenticacion y darle acceso o no a las URLs que creamos necesarias
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    //se configura la autenticacion de usuarios
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //utilizamos nuestro propio servicio de usuarios y un encriptador de spring
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    //se configura la seguridad(recursos, filtros, etc)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/home",
                        "/login",
                        "/signup",
                        "/productos/buscar/**",
                        "/productos/listar/**",
                        "/productos/ciudad/**",
                        "/productos/categoria/**",
                        "/productos/fechas/**",
                        "/productos/resultados/**",
                        "/ciudades/**",
                        "/categorias/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/puntuaciones/**").permitAll()
                .antMatchers("/roles/**", "/productos/admin/**").hasRole("ADMIN")
                .antMatchers("/reservas/**", "/favoritos/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //se configura el encriptador de claves

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //se configura el metodo de encriptacion
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsServiceImpl);
        return provider;
    }


}
