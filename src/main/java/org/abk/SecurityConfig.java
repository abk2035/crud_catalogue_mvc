package org.abk;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired// injection de dependance
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.inMemoryAuthentication()
		       .withUser("admin").password("{noop}1234").roles("USER","ADMIN");
		   auth.inMemoryAuthentication()  
		       .withUser("user").password("{noop}1234").roles("USER");
		   //****************presente une erreur************************************************************
		auth.jdbcAuthentication()
		    .dataSource(dataSource)
		    .usersByUsernameQuery("select login as principal, pass as credentials ,active from users where login=?")
		    .authoritiesByUsernameQuery("SELECT login AS principal,role AS role from users_roles")
		    //.passwordEncoder(new MD5Password())
		    .rolePrefix("ROLE_");
		
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login");
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
	}
}
