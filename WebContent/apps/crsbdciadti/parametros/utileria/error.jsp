<%	if (request.getParameter("error")!=null && request.getParameter("error").equals("SES-001")) { %>
		<div class="negativo">Ha ocurrido un error con su sessión por favor identifiquese nuevamente.</div>
		<form action="../../../../../index.jsp" method="post" target="_top">
		<input type="submit" class="outsession" value="Continuar">
		</form>
<% } %>