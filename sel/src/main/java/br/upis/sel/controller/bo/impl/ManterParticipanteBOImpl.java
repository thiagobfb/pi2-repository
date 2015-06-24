package br.upis.sel.controller.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.upis.sel.controller.bo.ManterParticipanteBO;
import br.upis.sel.enums.ParticipanteStatus;
import br.upis.sel.enums.PerfilDescricao;
import br.upis.sel.model.dao.ParticipanteDAO;
import br.upis.sel.model.entity.Participante;
import br.upis.sel.model.entity.Perfil;

import com.google.common.collect.Lists;

@Service
@Transactional
public class ManterParticipanteBOImpl extends AbstractBOImpl implements ManterParticipanteBO {

	private static final long serialVersionUID = -8764722197921168168L;
	
	@Autowired
	private ParticipanteDAO participanteDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.upis.sel.controller.ManterParticipanteBO#buscarPorPerfil(br.upis.sel
	 * .model.entity.Perfil)
	 */
	@Override
	public List<Participante> buscarPorPerfil(Perfil... perfil) {
		List<Perfil> perfilParam = new ArrayList<Perfil>();

		for (int i = 0; i < perfil.length; i++) {
			perfilParam.add(perfil[i]);
		}
		return this.participanteDAO.findByPerfis(perfilParam);
	}

	@Override
	public List<Participante> buscarTodosParticipantes() {
		return Lists.newArrayList(this.participanteDAO.findAll());
	}

	@Override
	public Participante buscarPorId(Long id) {
		return this.participanteDAO.findOne(id);
	}

	@Override
	public void incluirParticipante(Participante p) {
		if (p.getPerfis() == null || p.getPerfis().isEmpty()) {
			throw new RuntimeException("O participante não possui perfis adicionados");
		}

		if (p.getStatus() == null) {
			p.setStatus(ParticipanteStatus.ATIVO);
		}
		
		String cpfFormatado = this.retirarMascaraCPF(p.getUsername());
		p.setUsername(cpfFormatado);
		
		for (Perfil perfil : p.getPerfis()) {
			if (perfil.getDescricao().equals(PerfilDescricao.ROLE_ADMINISTRADOR)) {
				if (p.getPassword() != null || !p.getPassword().equals("")) {
					String senha = this.criptografarSenha(p.getPassword());
					p.setPassword(senha);
					break;
				} else {
					throw new RuntimeException("Participante com perfil de administrador precisa de senha");
				}
			}
		}

		this.participanteDAO.save(p);
	}

	private String criptografarSenha(String psw) {
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		return encoder.encodePassword(psw, null);
	}

	@Override
	public void alterarParticipante(Participante p, ParticipanteStatus ps) {
		
		Participante participante = this.participanteDAO.findOne(p.getIdParticipante());
		participante.setNome(p.getNome());
		participante.setPassword(p.getPassword());
		participante.setUsername(p.getUsername());
		participante.setStatus(ps);
		
		participante.getPerfis().clear();
		participante.getPerfis().addAll(p.getPerfis());
		
		this.incluirParticipante(participante);
	}
	
	private String retirarMascaraCPF(String cpf) {
		String somenteNumeros = cpf.replace(".", "").replace("-", "");
		return somenteNumeros;
	}
	
	@Override
	public List<Participante> buscarParticipantesPorFiltro(String nome, String cpf) {
		boolean nomeNaoNuloENaoVazio = nome != null && !nome.equals("");
		boolean cpfNaoNuloENaoVazio = cpf != null && !cpf.equals("");
		
		if (nomeNaoNuloENaoVazio && cpfNaoNuloENaoVazio) {
			cpf = this.retirarMascaraCPF(cpf);
			nome = formatarCampoPesquisa(nome);
			return this.participanteDAO.findByNomeLikeAndUsername(nome, cpf);
		} else if (nomeNaoNuloENaoVazio && !cpfNaoNuloENaoVazio) {
			nome = formatarCampoPesquisa(nome);
			return this.participanteDAO.findByNomeLike(nome);
		} else if (!nomeNaoNuloENaoVazio && cpfNaoNuloENaoVazio) {
			cpf = this.retirarMascaraCPF(cpf);
			return this.participanteDAO.findByUsernameLike(cpf);
		} else {
			return null;
		}
	}

	@Override
	public List<Participante> buscarParticipantesPorNome(String query) {
		return this.participanteDAO.findByNomeStartingWith(query);
	}

	@Override
	public List<Participante> buscarArrematantesPorNome(String query, Perfil... perfil) {
		List<Perfil> perfilParam = new ArrayList<Perfil>();

		for (int i = 0; i < perfil.length; i++) {
			perfilParam.add(perfil[i]);
		}
		
		return this.participanteDAO.findByNomeStartingWithAndPerfis(query, perfilParam);
	}
	
}
