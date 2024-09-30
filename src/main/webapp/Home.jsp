<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Model.Vendas"%>
<%@ page import="Model.ItensVenda"%>
<%@ page import="DAO.VendasDAO"%>
<%@ page import="Model.Produtos"%>
<%@ page import="DAO.ProdutosDAO"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.format.DateTimeFormatter"%>





<%


String empresa = (String) session.getAttribute("empresa");
if (empresa == null || empresa.isEmpty()) {
    throw new RuntimeException("O nome da empresa não está definido na sessão.");
}
List<Vendas> lista;
VendasDAO Vdao = new VendasDAO(empresa);
lista = Vdao.listarVendasdoDia();
%>
<%

List<Produtos> prodp; // Declara a lista
ProdutosDAO daop = new ProdutosDAO(empresa);
prodp = daop.listarProdutos(); // Atribui o resultado da busca à lista exibida na tabela
%>
<%
double totalVendasDia = 0;

// Formatar data para yyyy-MM-dd
SimpleDateFormat dataEUA = new SimpleDateFormat("yyyy-MM-dd");
String datamysql = dataEUA.format(new Date());

// Converter a string formatada para LocalDate
DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
LocalDate data_venda = LocalDate.parse(datamysql, formato);

// Instanciar o DAO e obter o total de vendas
VendasDAO dao = new VendasDAO(empresa);
totalVendasDia = dao.retornaTotalVendaPorDia(data_venda);

// Definir o total de vendas como atributo da requisição
request.setAttribute("totalVendido", totalVendasDia);
%>


<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Home</title>
<link rel="icon"
	href="img/2992664_cart_dollar_mobile_shopping_smartphone_icon.png">
<link rel="stylesheet" href="style.css">



</head>
<body>
	<%@ include file="menu.jsp"%>
	<div class="table-container">
		<h2>Vendas do Dia</h2>
		<table id="VendaDiaria">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Data</th>
					<th>Total</th>
					<th>Observações</th>
					<th>Desconto</th>
					<th>Lucro</th>
					<th>Forma De Pagamento</th>

				</tr>
			</thead>
			<tbody>
				<%
				if (lista != null && !lista.isEmpty()) {
					for (int i = 0; i < lista.size(); i++) {
				%>
				<tr id="<%=lista.get(i).getId()%>" class="linha-editar"
					data-id="<%=lista.get(i).getId()%>">
					<td><a href="selecionarVenda?id=<%=lista.get(i).getId()%>"><%=lista.get(i).getCliente() != null && lista.get(i).getCliente().getNome() != null ? lista.get(i).getCliente().getNome() : "" %></a></td>
					<td><a href="selecionarVenda?id=<%=lista.get(i).getId()%>"><%=lista.get(i).getData_venda()%></a></td>
					<td><a href="selecionarVenda?id=<%=lista.get(i).getId()%>"><%=lista.get(i).getTotal_venda()%></a></td>
					<td><a href="selecionarVenda?id=<%=lista.get(i).getId()%>"><%=lista.get(i).getObs()%></a></td>
					<td><a href="selecionarVenda?id=<%=lista.get(i).getId()%>"><%=lista.get(i).getDesconto()%></a></td>
					<td><a href="selecionarVenda?id=<%=lista.get(i).getId()%>"><%=lista.get(i).getLucro()%></a></td>
					<td><a href="selecionarVenda?id=<%=lista.get(i).getId()%>"><%=lista.get(i).getformaPagamento()%></a></td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="6">Não há vendas disponíveis na data de hoje.</td>
				</tr>
				<%
				}
				%>
			</tbody>

		</table>


		<div class="col-md-3">
			<label class="form-label">Total Diario Vendido: </label> <input
				type="text" class="form-control" name="totalVendido"
				value="<%=request.getAttribute("totalVendido") != null ? request.getAttribute("totalVendido").toString() : ""%>">

		</div>
	


	</div>
	<div class="table-containerproduto">
		<h2>Produtos em Estoque</h2>
		<table id="VendaDiaria">
			<thead>
				<tr>
					<th>Codigo</th>
					<th>Descrição</th>
					<th>Quantidade</th>


				</tr>
			</thead>
			<tbody>
				<%
				if (prodp != null && !prodp.isEmpty()) {
					for (int i = 0; i < prodp.size(); i++) {
				%>
				<tr id="<%=prodp.get(i).getId()%>" class="linha-editar"
					data-id="<%=prodp.get(i).getId()%>">
					<td><a href="select?id=<%=prodp.get(i).getId()%>"><%=prodp.get(i).getId()%></a></td>
					<td><a href="select?id=<%=prodp.get(i).getId()%>"><%=prodp.get(i).getDescricao()%></a></td>
					<td><a href="select?id=<%=prodp.get(i).getId()%>"><%=prodp.get(i).getQtd_estoque()%></a></td>

				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="6">Não há vendas disponíveis na data de hoje.</td>
				</tr>
				<%
				}
				%>
			</tbody>

		</table>


	</div>

	


</body>
</html>