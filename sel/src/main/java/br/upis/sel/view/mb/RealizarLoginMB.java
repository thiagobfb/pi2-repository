package br.upis.sel.view.mb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.upis.sel.controller.facade.SELFacade;
import br.upis.sel.model.entity.Participante;

@Controller
public class RealizarLoginMB extends AbstractMB implements AuthenticationProvider {
	
	private static final long serialVersionUID = -1980011185324165060L;

	@Autowired
	private SELFacade facade;
	
//	@Autowired
//	private ParticipanteSession session;
	
	private String cpf;
	private String senha;
	
	public String efetuarLogin() {
//		try {
//			Participante participante = this.facade.realizarLogin(this.cpf, this.senha);
//			
//			if (participante != null) {
//				this.loginSpringSecurity(participante);
//				this.session.setParticipante(participante);
//				return "index";
//			} else {
//				throw new Exception();
//			}
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return "";
	}

	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}*/
	
	@SuppressWarnings("unused")
	private void loginSpringSecurity(Participante participante) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(participante, null, participante.getPerfis());
		token.setDetails(participante);
		SecurityContextHolder.createEmptyContext();
		SecurityContextHolder.getContext().setAuthentication(token);
		
	}
	
	//Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
		 
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
	 
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("/login");
		 
		return model;
	}

	public String logout() {
		SecurityContextHolder.clearContext();
//		this.session.setParticipante(null);
		return "login";
	}

	@Override
	public Authentication authenticate(Authentication arg0)
			throws AuthenticationException {
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public void recuperarObjeto() {
		//sem corpo aqui
	}

	@Override
	public void prepararAlteracao() {
		//sem corpo aqui
	}
	
}
