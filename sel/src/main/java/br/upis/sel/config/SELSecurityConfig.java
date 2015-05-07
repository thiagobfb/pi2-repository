package br.upis.sel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.upis.sel.controller.bo.RealizarLoginBO;

@Configuration
@EnableWebSecurity
public class SELSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RealizarLoginBO realizarLoginBO;

//	@Bean(name = "concurrencyFilter")
//	public ConcurrentSessionFilter getConcurrentSessionFilter() {
//		return new ConcurrentSessionFilter(this.getSessionRegistry());
//	}
//
//	@Bean(name = "sas")
//	public ConcurrentSessionControlAuthenticationStrategy getSas() {
//		ConcurrentSessionControlAuthenticationStrategy cscas = new ConcurrentSessionControlAuthenticationStrategy(
//				getSessionRegistry());
//		cscas.setMaximumSessions(1);
//
//		return cscas;
//	}
//
//	@Bean(name = "sessionResgistry")
//	public SessionRegistry getSessionRegistry() {
//		return new SessionRegistryImpl();
//	}
//
//	@Bean(name = "authProvider")
//	public AuthenticationProvider getAuthProvider() {
//		return new RealizarLoginMB();
//	}
//
//	@Bean(name = "authenticationManager")
//	public AuthenticationManager getAuthenticationManager() {
//		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
//		providers.add(this.getAuthProvider());
//		ProviderManager manager = new ProviderManager(providers);
//
//		return manager;
//	}
//
//	
//	@Bean(name = "participanteSession")
//	public ParticipanteSession getParticipanteSession() {
//		return new ParticipanteSession();
//	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {	
		http.authorizeRequests()
				.antMatchers("/","/pages/public/**", "/resources/**").permitAll()
				.anyRequest().authenticated().and().csrf().disable()
				.formLogin().loginPage("/login.jsp").permitAll()
				.defaultSuccessUrl("/pages/private/index.jsf").failureUrl("/login.jsp?err=1").and()
				.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login.jsp")
                .permitAll()
                .and()
             .httpBasic();
//		 http.exceptionHandling().accessDeniedPage("/pages/error/errorPage.jsf");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setPasswordEncoder(new ShaPasswordEncoder(256));
		dao.setUserDetailsService(this.realizarLoginBO);
		auth.authenticationProvider(dao);
		// auth.userDetailsService(userDetailsService)
		//
		//
		// inMemoryAuthentication().withUser("user").password("password")
		// .roles("USER");
	}
}
