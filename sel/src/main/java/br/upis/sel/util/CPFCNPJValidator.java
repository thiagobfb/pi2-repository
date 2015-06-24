package br.upis.sel.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class CPFCNPJValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		long valor = 0;
		String cpfCnpj = value.toString();

		try {
			valor = Long.parseLong(cpfCnpj);

			if (valor == 0) {
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary(ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("erro.validacao.valor"));
				throw new ValidatorException(message);
			} else {
				if (cpfCnpj.length() == 11) {
					if (!validaCPF(String.valueOf(cpfCnpj))) {
						FacesMessage message = new FacesMessage();
						message.setSeverity(FacesMessage.SEVERITY_ERROR);
						message.setSummary(ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("erro.validacao.cpf"));
						throw new ValidatorException(message);
					}
				} else if (cpfCnpj.length() == 14) {
					if (!validaCNPJ(String.valueOf(cpfCnpj))) {
						FacesMessage message = new FacesMessage();
						message.setSeverity(FacesMessage.SEVERITY_ERROR);
						message.setSummary(ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("erro.validacao.cnpj"));
						throw new ValidatorException(message);
					}
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("erro.validacao.valor"));
			throw new ValidatorException(message);
		}
	}

	/**
	 * Valida CNPJ do usu�rio.
	 * 
	 * @param cnpj
	 *            String valor com 14 d�gitos
	 */
	public static boolean validaCNPJ(String cnpj) {
		if (cnpj == null || cnpj.length() != 14)
			return false;

		try {
			Long.parseLong(cnpj);
		} catch (NumberFormatException e) { // CNPJ n�o possui somente n�meros
			return false;
		}

		int soma = 0;
		String cnpj_calc = cnpj.substring(0, 12);

		char chr_cnpj[] = cnpj.toCharArray();
		for (int i = 0; i < 4; i++)
			if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
				soma += (chr_cnpj[i] - 48) * (6 - (i + 1));

		for (int i = 0; i < 8; i++)
			if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
				soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));

		int dig = 11 - soma % 11;
		cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
		soma = 0;
		for (int i = 0; i < 5; i++)
			if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
				soma += (chr_cnpj[i] - 48) * (7 - (i + 1));

		for (int i = 0; i < 8; i++)
			if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
				soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));

		dig = 11 - soma % 11;
		cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();

		return cnpj.equals(cnpj_calc);
	}

	/**
	 * Valida CPF do usu�rio. N�o aceita CPF's padr�es como 11111111111 ou
	 * 22222222222
	 * 
	 * @param cpf
	 *            String valor com 11 d�gitos
	 */
	private static boolean validaCPF(String cpf) {
		if (cpf == null || cpf.length() != 11 || isCPFPadrao(cpf))
			return false;

		try {
			Long.parseLong(cpf);
		} catch (NumberFormatException e) { // CPF n�o possui somente n�meros
			return false;
		}

		return calcDigVerif(cpf.substring(0, 9)).equals(cpf.substring(9, 11));
	}

	/**
	 * 
	 * @param cpf
	 *            String valor a ser testado
	 * @return boolean indicando se o usu�rio entrou com um CPF padr�o
	 */
	private static boolean isCPFPadrao(String cpf) {
		if (cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") || cpf.equals("66666666666")
				|| cpf.equals("77777777777") || cpf.equals("88888888888") || cpf.equals("99999999999")) {

			return true;
		}

		return false;
	}

	private static String calcDigVerif(String num) {
		Integer primDig, segDig;
		int soma = 0, peso = 10;
		for (int i = 0; i < num.length(); i++)
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

		if (soma % 11 == 0 | soma % 11 == 1)
			primDig = new Integer(0);
		else
			primDig = new Integer(11 - (soma % 11));

		soma = 0;
		peso = 11;
		for (int i = 0; i < num.length(); i++)
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

		soma += primDig.intValue() * 2;
		if (soma % 11 == 0 | soma % 11 == 1)
			segDig = new Integer(0);
		else
			segDig = new Integer(11 - (soma % 11));

		return primDig.toString() + segDig.toString();
	}

}
