package br.com.sadock.isibank.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//desabilito a tela de login automática do springboot
		http.cors(cors -> {
			cors.configurationSource(this.corsConfigurationSource());
		});
		
		
		http.csrf( (csrf) -> {
			csrf.disable();
		})
		.authorizeHttpRequests( (auth) -> {
			auth.requestMatchers(new AntPathRequestMatcher("/clientes", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/login", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/face/register", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/face/training", "GET")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/face/recognize", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				.anyRequest().authenticated();
		})
		.addFilterBefore(new IsiBankFilter(), UsernamePasswordAuthenticationFilter.class)
		.headers( (header) -> {
			header.frameOptions( (iframe) -> iframe.disable());
		});
		
		return http.build();
	}
	
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("*"));
	    configuration.setAllowedMethods(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

}
