package br.upis.sel.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {

	private static final String ARQUIVO_LOG = "C:/logs/reembolso_logs.log";

	private static final String PREFIXO = " [SEL] - ";
	
	private static LogUtil util;
	
	private LogUtil() {
	}
	
	public static LogUtil getCurrentInstance() {
		if (util == null) {
			util = new LogUtil();
		}
		return util;
	}

	private String getPrefixo() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())
				+ PREFIXO;
	}
	
	public static String mensagemUsuarioLogin(String nomeUsuario){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "fez login no sistema de Reembolso em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemUsuarioLogoff(String nomeUsuario){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "fez logoff no sistema de Reembolso em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemSolicitacaoReembolso(String nomeUsuario, String numeroPedido){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "registrou uma solicita��o de reembolso do pedido ";
		      texto += "de n� " + numeroPedido + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemAutorizacaoReembolso(String nomeUsuario, String numeroPedido){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "autorizou a solicita��o de reembolso ";
		      texto += "do pedido de n� " + numeroPedido + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemNegacaoReembolso(String nomeUsuario, String numeroPedido){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "N�O autorizou a solicita��o de reembolso ";
		      texto += "do pedido de n� " + numeroPedido + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemPendenciaReembolso(String nomeUsuario, String numeroPedido){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "incluiu na pend�ncia a solicita��o de reembolso ";
		      texto += "do pedido de n� " + numeroPedido + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemFinalizaReembolso(String nomeUsuario, String numeroPedido){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "finalizou a solicita��o de reembolso ";
		      texto += "do pedido de n� " + numeroPedido + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemReabrirReembolso(String nomeUsuario, String numeroPedido){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "reabriu a solicita��o de reembolso ";
		      texto += "do pedido de n� " + numeroPedido + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemNovoMotivoReembolso(String nomeUsuario, String nomeMotivo){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "criou um novo motivo de reembolso: ";
		      texto += nomeMotivo + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemAlterarMotivoReembolso(String nomeUsuario, String nomeMotivo){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "alterou o motivo de reembolso ";
		      texto += nomeMotivo + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemNovoBanco(String nomeUsuario, String nomeBanco){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "cadastrou um novo banco: ";
		      texto += nomeBanco + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemDesativarBanco(String nomeUsuario, String nomeBanco){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "desativou o banco: ";
		      texto += nomeBanco + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemNovoUsuario(String nomeUsuario, String novoUsuario){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "cadastrou um novo usu�rio: ";
		      texto += novoUsuario + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemAlterarUsuario(String nomeUsuario, String outroUsuario){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "alterou o usu�rio: ";
		      texto += outroUsuario + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemDesativarUsuario(String nomeUsuario, String outroUsuario){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "desativou o usu�rio: ";
		      texto += outroUsuario + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public static String mensagemReativarUsuario(String nomeUsuario, String outroUsuario){		
		String texto = "O usu�rio " + nomeUsuario + " ";
		      texto += "reativou o usu�rio: ";
		      texto += outroUsuario + " em ";
		      texto += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		return texto;
	}
	
	public void log(String mensagem) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(ARQUIVO_LOG, true)));
		writer.write(getPrefixo() + mensagem + System.getProperty("line.separator"));
		writer.close();
	}
}
