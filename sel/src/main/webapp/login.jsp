<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page language="java" import="javax.servlet.jsp.PageContext" %>
<!DOCTYPE html>
<html lang="br" xmlns:th="http://www.thymeleaf.org"
	xmlns:tiles="http://www.thymeleaf.org">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta charset="UTF-8" />
	<title>SEL - Login</title>

	<link href="resources/css/bootstrap.css" rel="stylesheet" />
	<link href="resources/css/bootstrap-responsive.css" rel="stylesheet" />
	<link href="resources/css/bootstrap.min.css" rel="stylesheet" />
	<link href="resources/css/bootstrap-theme.css" rel="stylesheet" />
	<link href="resources/css/bootstrap-theme.min.css" rel="stylesheet" />
	<link href="resources/css/style.css" rel="stylesheet" />

	<script type="text/javascript" src='<c:url value="resources/js/jquery-1.11.3.js"></c:url>'></script>
	<script type="text/javascript" src='<c:url value="resources/js/bootstrap.js"></c:url>'></script>
	<script type="text/javascript" src='<c:url value="resources/js/bootstrap.min.js"></c:url>'></script>
	<script type="text/javascript" src='<c:url value="resources/js/jquery.maskedinput.js"></c:url>'></script>
	<script type="text/javascript" src='<c:url value="resources/js/funcoes.js"></c:url>'></script>
</head>
<body>
	<div class="container">
		<div id="header-login">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<img src="resources/img/layout_upis_02.gif" alt="UPIS"	class="img-rounded" />
				</div>
			</div>
			<div class="row">
				<% if (request.getParameter("err") != null) { %>
					<div class="erro"><p>Erro ao efetuar login!!</p></div>
				<% } %>
				<div class="col-xs-6 col-md-4">
					<fieldset>
						<legend>Login</legend>
						<form action='<c:url value="/login.jsp"></c:url>' method="post">
							<input type="hidden" id="autentication" name="username" />
							<div id="campo-cpf" class="form-group">
								<label for="cpf">CPF: </label>
								<input type="text" id="cpf"	class="form-control" placeholder="Digite o Cpf"	required="required" aria-describedby="" name="cpf" />
								<span class="glyphicon glyphicon-ok form-control-feedback esconder_icone" aria-hidden="true"></span>
								<span id="inputSuccess2Status" class="sr-only esconder_icone">(success)</span>
								<span class="glyphicon glyphicon-remove form-control-feedback esconder_icone" aria-hidden="true"></span>
								<span id="inputError2Status" class="sr-only esconder_icone">(error)</span>
							</div>
			
							<div id="campo-senha" class="form-group">
								<label for="senha">Senha: </label>
								<input type="password" id="senha" class="form-control" placeholder="Digite a senha"	required="required" aria-describedby="" name="password" maxlength="8"/>
								<span class="glyphicon glyphicon-ok form-control-feedback esconder_icone" aria-hidden="true"></span>
								<span id="inputSuccess2Status" class="sr-only esconder_icone">(success)</span>
								<span class="glyphicon glyphicon-remove form-control-feedback esconder_icone" aria-hidden="true"></span>
								<span id="inputError2Status" class="sr-only esconder_icone">(error)</span>
							</div>
							<button type="submit" class="btn btn-default btn-lg">Login</button>
						</form>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
// 		function atualiza(){
// 			autentication.value=cpf.value;
// 			alert(autentication.value);
// 		}
		$(document).ready(mascaraCPF);
		$(document).ready(validarCampoCpf);
		$(document).ready(validarCampoSenha);
		$(document).ready(atualiza);
	</script>
</body>
</html>
