/**
 * 
 */

//máscara para cpf
var mascaraCPF = function() {
	$("#cpf").mask("999.999.999-99");
}

//insere cpf no hidden
var atualiza = function() {
	$("#cpf").blur(function() {
		var cpfComMascara = cpf.value;
//		var cpfSemPonto = cpfComMascara.replace(/./g, "");
		var somenteNumeros = cpfComMascara.replace(/[\.-]/g, "");
		
		autentication.value = somenteNumeros;
//		alert(autentication.value);
	});
}
//chamando jQuery
//$(document).ready(function(){});
var mascaraValorItem = function() {
	$(".valorMoeda").maskMoney({
		symbol: "R$ ", showSymbol: true, thousands: ".", decimal: ",", symbolStay: false
	});
}

var validarCampoCpf = function() {
	$("#cpf").blur(function() {
		var valorCpf = $(this).val();
		
		if (valorCpf.length > 0) {
			$("#campo-cpf").addClass("has-success").addClass("has-feedback").removeClass("has-error");
			$("#cpf").attr("aria-describedby", "inputSuccess2Status");
			$("#campo-cpf").children(".glyphicon-ok").removeClass("esconder_icone");
			$("#campo-cpf").children(".glyphicon-remove").addClass("esconder_icone");
			$("#inputSuccess2Status").removeClass(".esconder_icone");
		} else {
			$("#campo-cpf").addClass("has-error").addClass("has-feedback").removeClass("has-success");
			$("#cpf").attr("aria-describedby", "inputError2Status");
			$("#campo-cpf").children(".glyphicon-remove").removeClass("esconder_icone");
			$("#campo-cpf").children(".glyphicon-ok").addClass("esconder_icone");
			$("#inputError2Status").removeClass(".esconder_icone");
		}
	});
}

var validarCampoSenha = function() {
	$("#senha").blur(function() {
		var valorSenha = $(this).val();
		
		if (valorSenha.length > 0) {
			$("#campo-senha").addClass("has-success").addClass("has-feedback").removeClass("has-error");
			$("#senha").attr("aria-describedby", "inputSuccess2Status");
			$("#campo-senha").children(".glyphicon-ok").removeClass("esconder_icone");
			$("#campo-senha").children(".glyphicon-remove").addClass("esconder_icone");
			$("#inputSuccess2Status").removeClass(".esconder_icone");
		} else {
			$("#campo-senha").addClass("has-error").addClass("has-feedback").removeClass("has-success");
			$("#senha").attr("aria-describedby", "inputError2Status");
			$("#campo-senha").children(".glyphicon-remove").removeClass("esconder_icone");
			$("#campo-senha").children(".glyphicon-ok").addClass("esconder_icone");
			$("#inputError2Status").removeClass(".esconder_icone");
		}
	});
}

