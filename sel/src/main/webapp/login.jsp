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

	<script type="text/javascript" src='<c:url value="resources/js/jquery-1.11.3.js"></c:url>'></script>
	<script type="text/javascript" src='<c:url value="resources/js/bootstrap.js"></c:url>'></script>
	<script type="text/javascript" src='<c:url value="resources/js/bootstrap.min.js"></c:url>'></script>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class=".col-md-6 .col-md-offset-3">
				<img src="resources/img/layout_upis_02.gif" alt="UPIS"	class="img-rounded" />
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6 col-md-4">
				<fieldset>
					<legend>Login</legend>
					<form action='<c:url value="/login.jsp"></c:url>' method="post">
						<input type="hidden" id="autentication" name="username" />
						<div class="form-group has-success has-error has-feedback">
							<label for="cpf">CPF: </label>
							<input type="text" id="cpf"	class="form-control" placeholder="Digite o Cpf"	required="required"	aria-describedby="inputSuccess2Status inputError2Status" name="cpf" onblur="atualiza()" />
							<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
							<span id="inputSuccess2Status" class="sr-only">(success)</span>
							<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
							<span id="inputError2Status" class="sr-only">(error)</span>
						</div>

						<div class="form-group">
							<label for="senha">Senha: </label>
							<input type="password" id="senha" class="form-control" placeholder="Digite a senha"	required="required" name="password" onblur="atualiza()" />
						</div>
						<button type="submit" class="btn btn-default btn-lg">Login</button>
					</form>
				</fieldset>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		function atualiza(){
			autentication.value=cpf.value;
// 			alert(autentication.value);
		}
	</script>
</body>
</html>
