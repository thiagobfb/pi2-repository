package br.upis.sel.view.mb;

//import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.upis.sel.model.entity.Participante;

@Component
@Scope("session")
//@ManagedBean
public class ParticipanteSession {
	
	private Participante participante;

	public Participante getParticipante() {
		this.participante = (Participante) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public boolean isLogado() {
		return this.participante != null;
	}
}
