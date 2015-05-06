package br.upis.sel.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.google.common.collect.Lists;

import br.upis.sel.enums.ParticipanteStatus;
import br.upis.sel.enums.PerfilDescricao;
import br.upis.sel.model.dao.ParticipanteDAO;
import br.upis.sel.model.dao.PerfilDAO;
import br.upis.sel.model.entity.Participante;
import br.upis.sel.model.entity.Perfil;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("br.upis.sel.config")
@EnableScheduling
public class GeraTabelas implements CommandLineRunner {
	
	@Autowired
	ParticipanteDAO participanteDAO;
	
	@Autowired
	PerfilDAO perfilDAO;

	public static void main(String[] args) {
		SpringApplication.run(GeraTabelas.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//salva os perfis no banco
		System.out.println("Gerando os perfis");
		perfilDAO.save(new Perfil(PerfilDescricao.ROLE_ADMINISTRADOR));
		perfilDAO.save(new Perfil(PerfilDescricao.ROLE_LEILOEIRO));
		perfilDAO.save(new Perfil(PerfilDescricao.ROLE_COMITENTE));
		perfilDAO.save(new Perfil(PerfilDescricao.ROLE_ARREMATANTE));
		
		List<Perfil> perfis = Lists.newArrayList(perfilDAO.findAll());
		
		String senha = "admin";
		
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		encoder.encodePassword(senha, null);
		System.out.println(encoder.toString());
		
		participanteDAO.save(new Participante("Administrador Teste", encoder.toString(), "54753133184", ParticipanteStatus.ATIVO, perfis));
	}

}
