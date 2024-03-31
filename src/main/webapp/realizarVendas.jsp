<%@ page import="org.json.JSONObject, org.json.JSONArray"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.Clientes"%>
<%@ page import="Model.Produtos"%>
<%@ page import="DAO.ClientesDAO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%
Date dataAtual = new Date();
SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Escolha o formato desejado
String dataAtualFormatada = formatoData.format(dataAtual);
%>
<%
Clientes clientes = new Clientes();
%>
<%
Produtos produtos = new Produtos();
%>
<html lang="pt-BR">
<head>
<title>Venda</title>
<link rel="icon"
	href="img/2992655_click_computer_currency_dollar_money_icon.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
</head>
<body class="bg-light">
	<%@ include file="menu.jsp"%>
	<main class="container d-flex h-auto d-inline-block">
		<div class="row col-md-9">
			<!-- Formulário de Cliente -->
			<form id="buscarClienteForm" class="row g-3 needs-validation"
				novalidate action="selecionarClienteProdutos" method="POST">
				<div id="Cliente">
					<div class="col-md-8">
						<h2>Cliente</h2>
						<div class="row">
							<div class="col-md-6">
								<label for="cliId" class="form-label">Codigo: </label> <input
									type="text" class="form-control" id="cliId" name="cliId"
									required
									value="<%=request.getAttribute("cliId") != null ? request.getAttribute("cliId").toString() : ""%>"
									readonly>
							</div>
							<div class="col-md-6">
								<label for="dataProd" class="form-label d-flex">Data: </label> <input
									type="text" class="form-control d-flex"
									value="<%=dataAtualFormatada%>" disabled name="data">
							</div>
						</div>
						<div>
							<label for="cliNome" class="form-label">Nome: </label> <input
								type="text" class="form-control" id="cliNome" name="cliNome"
								required
								value="<%=request.getAttribute("cliNome") != null ? request.getAttribute("cliNome").toString() : ""%>"
								readonly>
						</div>
						<div class="col-md-6">
							<label for="validationCustom01" class="form-label">CPF: </label>
							<input type="text" class="form-control" id="validationCustom01"
								name="cliCpf" required
								value="<%=request.getAttribute("cliCpf") != null ? request.getAttribute("cliCpf").toString() : ""%><%clientes.getCpf();%>">
						</div>
						<div class="col-md-6">
							<label for="cliEndereco" class="form-label">Endereço: </label> <input
								type="text" class="form-control" id="cliEndereco"
								name="cliEndereco" required
								value="<%=request.getAttribute("cliEndereco") != null ? request.getAttribute("cliEndereco").toString() : ""%>"
								readonly>
						</div>
						<div class="col-md-2">
							<label for="cliNumero" class="form-label">N°: </label> <input
								type="text" class="form-control" id="cliNumero" name="cliNumero"
								required
								value="<%=request.getAttribute("cliNumero") != null ? request.getAttribute("cliNumero").toString() : ""%>"
								readonly>
						</div>
						<div></div>
					</div>
				</div>
				<div id="Produtos">
					<div class="col-md-8">
						<h2>Produto</h2>
						<div class="col-md-6">
							<label for="validationCustom02" class="form-label">Codigo:
							</label> <input type="text" class="form-control" id="idProd"
								name="idProd" required
								value="<%=request.getAttribute("idProd") != null ? request.getAttribute("idProd").toString() : ""%><%produtos.getId();%>">
						</div>
						<div>
							<label for="desProd" class="form-label">Descricão: </label> <input
								type="text" class="form-control" id="desProd" name="desProd"
								required
								value="<%=request.getAttribute("desProd") != null ? request.getAttribute("desProd").toString() : ""%>"
								readonly>
						</div>
						<div class="row">
							<div class="col-md-6">
								<label for="precoProd" class="form-label">Preço: </label> <input
									type="text" class="form-control" id="precoProd"
									name="precoProd" required
									value="<%=request.getAttribute("precoProd") != null ? request.getAttribute("precoProd").toString() : ""%>"
									readonly>
							</div>
							<div class="col-md-6">
								<label for="qtdProd" class="form-label">QTD: </label> <input
									type="number" class="form-control" id="qtdProd" name="qtdProd"
									required
									value="<%=request.getAttribute("qtdProd") != null ? request.getAttribute("qtdProd").toString() : ""%>">
							</div>
						</div>
						<div class="col-md-6">
							<label for="compraProd" class="form-label">Meu Preço:</label> <input
								type="text" class="form-control" id="compraProd"
								name="compraProd" required
								value="<%=request.getAttribute("compraProd") != null ? request.getAttribute("compraProd").toString() : ""%>"
								readonly>
						</div>
						<div class="d-flex justify-content-between">
							<input class="btn btn-primary mt-3" type="submit"
								value="Pesquisar">
							<button type="button" class="btn btn-danger mt-3" id="addItemBtn">Adicionar
								item</button>

						</div>
						<div class="d-flex col-md-6"></div>
					</div>
				</div>
				<input type="hidden" id="idProd" name="idProd"> <input
					type="hidden" id="desProd" name="desProd"> <input
					type="hidden" id="qtdProd" name="qtdProd"> <input
					type="hidden" id="precoProd" name="precoProd"> <input
					type="hidden" id="compraProd" name="compraProd">
			</form>

		</div>
		<div class="row col-md-5">

			<div class="p-3  bg-light">



               <form action="">
				<h2 class="d-flex justify-content-center">Itens Da Venda</h2>
				<div>
					<table class="table table-dark table-hover" id="carrinho">
						<thead>
							<tr>
								<th>Codigo</th>
								<th>Produto</th>
								<th>Quantidade</th>
								<th>Preço</th>
								<th>Subtotal</th>
							</tr>
						</thead>
						<tbody>
							<%
							// Obtendo a lista de itens da sessão
							HttpSession session_2 = request.getSession();
							JSONArray itensArray = (JSONArray) session.getAttribute("itens");
							if (itensArray != null) {
								for (int i = 0; i < itensArray.length(); i++) {
									JSONObject itemJson = itensArray.getJSONObject(i);
							%>
							<tr>
								<td><%=itemJson.getString("idProd")%></td>
								<td><%=itemJson.getString("desProd")%></td>
								<td><%=itemJson.getString("qtdProd")%></td>
								<td><%=itemJson.getString("precoProd")%></td>
								<td><%=itemJson.getString("subtotal")%></td>
							</tr>
							<%
							}
							}
							%>
						</tbody>
					</table>
                     
				</div>
                </form>
				<div class="d-flex justify-content-between"></div>


			</div>
			
			  <div>
			   <a href="detroyLista.jsp" type="button" class="btn btn-danger">Limpar Lista</a>    
			
			</div>
			<div class=" col-md-3">
				<label class="form-label">Total Venda:</label> <input id="total"
					type="text" class="form-control" name="total"
					value="">
					<input type="hidden" id="totalValue" name="totalValue" value="0">
					
			</div>
			<div class=" col-md-3">
				<label class="form-label">lucro:</label> <input type="text" id="lucro"
					class="form-control" name="lucro"
					value="">
			</div>
			<div class=" col-md-3">
				<label class="form-label">Desconto:</label> <input type="text"
					class="form-control" name="desconto"
					value="0.00">
			</div>
			<div>
			    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmacaoModal" >Pagamento</button>	
			    <input type="button" class="btn btn-danger" value="Desconto">	
			    <a type="button" class="btn btn-warning" href="#" data-bs-toggle="modal" data-bs-target="#CancelarVenda">Cancelar Venda</a>	
			</div>
			
		</div>
		 <form action="InseirVendaEintens" id="modalVendas">
		   <div class="modal fade" tabindex="-1" id="confirmacaoModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Pagamento</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
          <div>
         
             <select class="form-select" name=formaPagamento id="formaPagamento">
               <option value="Dinheiro">Dinheiro</option> 
               <option value="Crédito">Crédito</option>
               <option value="Débito" selected>Débito</option>
               <option value="Pix">Pix</option>    
               <option value="Anotado">Anotado</option>  
                  
             </select>
             <div class=" col-md-3">
             <label class="form-label">Valor: </label>
              <input type="text" class="form-control ml-1" id="pegardoTotal"> 
              
             </div>
             <div class="col-md-3" id="dinheiroRecebidoDiv">
                <label class="form-label">Dinheiro recebido: </label>
                <input type ="text" class="form-control ml-1" id="dinheiroRecebido">
             
             </div>
               <div class="col-md-3" id="trocoDiv">
                  <label  class="form-label"> Troco: </label>
                  <input type="text" class="form-control" id="trocoVenda"> 
                      <input type="button" class="btn btn-success" value="Confimar Troco" style="margin-top: 10px;">
               
               </div>
               
               <div class="col-md-6" id="observacaoDiv">
                  <label  class="form-label"> Observação: </label>
                  <input type="text" class="form-control" id="observacao"> 
                    
               
               </div>
          </div>
              
      </div>
      <div class="modal-footer">
             <h4 class="display-7">Deseja finalizar a venda ?</h4>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Não</button>
          <button type="submit" class="btn btn-primary" >Sim</button>
          
      </div>
     
    </div>
  </div>
</div>
 </form>
 
       <div class="modal fade" tabindex="-1" id="CancelarVenda">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Deseja Cancelar a Venda ?</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
      </div>  
      <div class="modal-footer">
         
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Não</button>
          <a type="submit" class="btn btn-primary" href="detroyLista.jsp">Sim</a>
          
      </div>
     
    </div>
  </div>
</div>
	</main>
	
	


	<!-- Bootstrap JavaScript Bundle com Popper -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
		crossorigin="anonymous"></script>
	<script>
	var totalValue = 0;
	var lucroValue = 0;
	var pegarTotal = 0 ;

	document.getElementById("addItemBtn").addEventListener("click", function() {
	    // Obter os valores dos campos
	    var idProd = document.getElementById("idProd").value;
	    var desProd = document.getElementById("desProd").value;
	    var qtdProd = document.getElementById("qtdProd").value;
	    var precoProd = document.getElementById("precoProd").value;
	    var meuPreco = document.getElementById("compraProd").value;

	    // Verificar se os campos de entrada não estão vazios
	    if (idProd && desProd && qtdProd && precoProd && meuPreco) {
	        // Calcular o subtotal
	        var subtotalValue = parseFloat(qtdProd) * parseFloat(precoProd);
	        var lucroUnitario = parseFloat(precoProd) - parseFloat(meuPreco); // Calcular o lucro unitário
	        var lucroCalculo = lucroUnitario * parseFloat(qtdProd); // Calcular o lucro total

	        // Criar um objeto com os dados do item
	        var item = {
	            idProd: idProd,
	            desProd: desProd,
	            qtdProd: qtdProd,
	            precoProd: precoProd,
	            subtotal: subtotalValue
	        };

	        // Enviar os dados para o Servlet usando AJAX
	        var xhr = new XMLHttpRequest();
	        xhr.open("POST", "inserirItens", true);
	        xhr.setRequestHeader("Content-Type", "application/json");
	        xhr.onreadystatechange = function() {
	            if (xhr.readyState == 4 && xhr.status == 200) {
	                // Adicionar a nova linha à tabela após a resposta do Servlet
	                var newRow = xhr.responseText;
	                var tableBody = document.getElementById("carrinho").getElementsByTagName("tbody")[0];
	                var newRowElement = document.createElement("tr");
	                newRowElement.innerHTML = newRow;
	                tableBody.appendChild(newRowElement);

	                // Limpar os campos após adicionar o item
	                document.getElementById("idProd").value = "";
	                document.getElementById("desProd").value = "";
	                document.getElementById("qtdProd").value = "";
	                document.getElementById("precoProd").value = "";
	                document.getElementById("compraProd").value = ""; // Limpar o campo de preço de compra

	                console.log("Item adicionado com sucesso!");

	                // Atualizar o valor total da venda e o lucro
	                updateTotal(subtotalValue, lucroCalculo, pegarTotal);
	            }
	        };
	        xhr.send(JSON.stringify(item));
	    } else {
	        alert("Por favor, preencha todos os campos.");
	    }
	});

	// Função para calcular o total da venda e o lucro
	function updateTotal(subtotalValue, lucroCalculo) {
	    totalValue += subtotalValue; // Adicionar o subtotal ao total
	    lucroValue += lucroCalculo; // Adicionar o lucro ao total de lucro
	    totalValue += pegarTotal;

	    document.getElementById("total").value = totalValue.toFixed(2);
	    document.getElementById("lucro").value = lucroValue.toFixed(2); // Exibir o lucro com duas casas decimais
	    document.getElementById("pegardoTotal").value = totalValue.toFixed(2);
	}
	// Função para mostrar ou ocultar os campos com base na opção selecionada
    function mostrarCampos() {
        var select = document.getElementById("formaPagamento");
        var dinheiroRecebidoDiv  = document.getElementById("dinheiroRecebidoDiv");
        var trocoDiv  = document.getElementById("trocoDiv");

        // Verifica se a opção selecionada é "Dinheiro"
        if (select.value === "Dinheiro") {
        	dinheiroRecebidoDiv .style.display = "block"; // Mostra o campo "Dinheiro recebido"
        	trocoDiv .style.display = "block"; // Mostra o campo "Troco"
        } else {
        	dinheiroRecebidoDiv.style.display = "none"; // Oculta o campo "Dinheiro recebido"
        	trocoDiv.style.display = "none"; // Oculta o campo "Troco"
        }
    }

    // Chama a função ao carregar a página e quando o valor do select mudar
    window.onload = mostrarCampos;
    document.getElementById("formaPagamento").addEventListener("change", mostrarCampos);
    
    function mostrarObservacao() {
        var select = document.getElementById("formaPagamento");
        var observacao  = document.getElementById("observacaoDiv");

        // Verifica se a opção selecionada é "Dinheiro"
        if (select.value === "Anotado") {
        	observacao .style.display = "block"; // Mostra o campo "observacao"
        	 // Mostra o campo "Troco"
        } else {
        	observacao .style.display = "none";// Oculta o campo "observacao"
        }
    }

    // Chama a função ao carregar a página e quando o valor do select mudar
    window.onload = mostrarObservacao;
    document.getElementById("formaPagamento").addEventListener("change", mostrarObservacao);
	</script>
	
	



</body>
</html>