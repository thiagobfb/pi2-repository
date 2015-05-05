package br.upis.sel.view.mb;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.upis.sel.model.entity.Participante;

@Component
@Scope("session")
public class ParticipanteSession {
	
	private Participante participante;

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public boolean isLogado() {
		return this.participante != null;
	}
}
