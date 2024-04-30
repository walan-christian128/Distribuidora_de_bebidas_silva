<%@page import="com.mysql.cj.x.protobuf.MysqlxDatatypes.Array"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Model.Vendas"%>
<%@ page import="DAO.VendasDAO"%>
<%@ page import="Model.Produtos"%>
<%@ page import="DAO.ProdutosDAO"%>

<%
List<Vendas> lista;
VendasDAO Vdao = new VendasDAO();
lista = Vdao.listarVendasdoDia();
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pagina Inicial</title>
<link rel="icon"
	href="img/2992664_cart_dollar_mobile_shopping_smartphone_icon.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
	
	<style>
	td a {
	text-decoration: none; /* Remove o sublinhado */
	color: inherit; /* Mantém a cor do texto padrão */
	cursor: pointer; /* Altera o cursor para indicar que é clicável */
}
</style>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<div class="container-fluid">
		<div class="row col-md-5 mb-2 bgdanger text-dark">
			<div class="col-12">
				<h1 class="d-flex justify-content-center">Vendas do dia</h1>
				<div id="table-container" class="container-sm overflow-auto" style="overflow-y: auto; max-height: 400px;">
					<table id="VendaDiaria"
						class="table table-danger table-bordered table-hover overflow-visible">
						<thead>
						<tr>
							<th>Nome</th>
							<th>Data</th>
							<th>Total</th>
							<th>Observações</th>
							<th>Desconto</th>
							<th>Forma De Pagamento</th>
                           </tr>
                       </thead>
                       <tbody>
                            <% if (lista != null && !lista.isEmpty()) { 
                                for (int i = 0; i < lista.size(); i++) { %>
                                    <tr id="<%= lista.get(i).getId() %>" class="linha-editar" data-id="<%= lista.get(i).getId() %>">
                                        <td><a href="selecionarVenda?id=<%= lista.get(i).getId() %>"><%= lista.get(i).getCliente().getNome() %></a></td>
                                        <td><a href ="selecionarVenda?id=<%= lista.get(i).getId() %>"><%= lista.get(i).getData_venda() %></a></td>
                                        <td><a href ="selecionarVenda?id=<%= lista.get(i).getId() %>"><%= lista.get(i).getTotal_venda() %></a></td>
                                        <td><a href ="selecionarVenda?id=<%= lista.get(i).getId() %>"><%= lista.get(i).getObs() %></a></td>
                                        <td><a href ="selecionarVenda?id=<%= lista.get(i).getId() %>"><%= lista.get(i).getDesconto() %></a></td>
                                        <td><a href ="selecionarVenda?id=<%= lista.get(i).getId() %>"><%= lista.get(i).getformaPagamento() %></a></td>
                                    </tr>
                            <%   }
                                } else { %>
                                    <tr>
                                        <td colspan="6">Não há vendas disponíveis.</td>
                                    </tr>
                            <% } %>
                        </tbody>
                      
					</table>


				</div>



			</div>


		</div>


	</div>

</body>
</html>