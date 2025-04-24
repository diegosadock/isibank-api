package br.com.sadock.isibank.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//desabilito a tela de login automática do springboot
		http.csrf( (csrf) -> {
			csrf.disable();
		})
		.authorizeHttpRequests( (auth) -> {
			auth.requestMatchers(new AntPathRequestMatcher("/clientes", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/login", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				.anyRequest().authenticated();
		})
		.addFilterBefore(new IsiBankFilter(), UsernamePasswordAuthenticationFilter.class)
		.headers( (header) -> {
			header.frameOptions( (iframe) -> iframe.disable());
		});
		
		return http.build();
	}

}
