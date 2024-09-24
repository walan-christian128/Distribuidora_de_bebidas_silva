<%@ page import="org.json.JSONObject, org.json.JSONArray"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
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
Date agora = new Date();
SimpleDateFormat dataEUA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String datamysql = dataEUA.format(agora);
%>
<%
Clientes clientes = new Clientes();
%>
<%
Produtos produtos = new Produtos();
%>
<%
HttpSession session_2 = request.getSession();
JSONArray itensArray = (JSONArray) session.getAttribute("itens");
%>
<html lang="pt-BR">
<head>
<meta charset="utf-8">
<title>Venda</title>
<link rel="icon"
	href="img/2992664_cart_dollar_mobile_shopping_smartphone_icon.png">
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
									type="text" class="form-control " id="cliId" name="cliId"
									required
									value="<%=request.getAttribute("cliId") != null ? request.getAttribute("cliId").toString() : ""%>"
									readonly>
									<div class="invalid-feedback">
									     Código é obrigatório.
									</div>
									
									
							</div>
							<div class="col-md-6">
								<label for="dataProd" class="form-label d-flex">Data: </label> <input
									type="text" class="form-control d-flex "
									value="<%=dataAtualFormatada%>" disabled name="data">
							</div>
						</div>
						<div>
							<label for="cliNome" class="form-label">Nome: </label> <input
								type="text" class="form-control " id="cliNome" name="cliNome"
								required
								value="<%=request.getAttribute("cliNome") != null ? request.getAttribute("cliNome").toString() : ""%>"
								readonly>
						</div>
						<div class="col-md-6">
							<label for="validationCustom01" class="form-label">CPF: </label>
							<input type="text" class="form-control" id="validationCustom01"
								name="cliCpf" required
								value="<%=request.getAttribute("cliCpf") != null ? request.getAttribute("cliCpf").toString() : ""%><%clientes.getCpf();%>" >
								<div class="invalid-feedback">
								    CPF Obrigatorio
								
								</div>
						</div>
						<div class="col-md-6">
							<label for="cliEndereco" class="form-label">Endereço: </label> <input
								type="text" class="form-control " id="cliEndereco"
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
							</label> <input type="text" class="form-control " id="idProd"
								name="idProd" required
								value="<%=request.getAttribute("idProd") != null ? request.getAttribute("idProd").toString() : ""%><%produtos.getId();%>">
								<div class="invalid-feedback">
									     Codigo do Produto é obrigatório.
									</div>
						</div>
						<div>
							<label for="desProd" class="form-label">Descricão: </label> <input
								type="text" class="form-control " id="desProd" name="desProd"
								required
								value="<%=request.getAttribute("desProd") != null ? request.getAttribute("desProd").toString() : ""%>"
								readonly>
						</div>
						<div class="row">
							<div class="col-md-6">
								<label for="precoProd" class="form-label">Preço: </label> <input
									type="text" class="form-control " id="precoProd"
									name="precoProd" required
									value="<%=request.getAttribute("precoProd") != null ? request.getAttribute("precoProd").toString() : ""%>"
									readonly>
							</div>
							<div class="col-md-6">
								<label for="qtdProd" class="form-label">QTD: </label> <input
									type="number" class="form-control " id="qtdProd" name="qtdProd"
									required
									value="<%=request.getAttribute("qtdProd") != null ? request.getAttribute("qtdProd").toString() : "0"%>">
									
							</div>
						</div>
						<div class="col-md-6">


							<label for="compraProd" class="form-label">Meu Preço:</label> <input
								type="text" class="form-control " id="compraProd"
								name="compraProd" required
								value="<%=request.getAttribute("compraProd") != null ? request.getAttribute("compraProd").toString() : ""%>"
								readonly>

						</div>
						<div class="d-flex justify-content-between">
							<input class="btn btn-primary mt-3" type="submit"
								value="Pesquisar">
							<button type="submit" class="btn btn-danger mt-3" id="addItemBtn"
								value="<%=session.getAttribute("itens")%>">Adicionar
								item</button>

						</div>
						<div class="d-flex col-md-6"></div>
					</div>
				</div>

			</form>

		</div>

		<div class="row col-md-5 p-3 mb-2 bg-dark text-white ">

			<div class="p-3 bg-dark">



				<form action="inserirItens" method="post" name="carrinho">
					<h2 class="d-flex justify-content-center">Itens Da Venda</h2>
					<div>
						<table class="table table-danger" id="carrinho">
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
				<a href="detroyLista.jsp" type="button" class="btn btn-danger">Limpar
					Lista</a>


			</div>
			<div class=" col-md-3">
				<label class="form-label">Total Venda:</label> <input
					id="totalVenda" type="text" class="form-control" name="totalVenda"
					value="<%=session.getAttribute("totalVenda") != null ? session.getAttribute("totalVenda").toString() : "0.00"%>">


			</div>


			<div class=" col-md-3">
				<label class="form-label">lucro:</label> <input type="text"
					id="lucro" class="form-control" name="lucro"
					value="<%=session.getAttribute("lucro") != null ? session.getAttribute("lucro").toString() : "0.00"%>">
			</div>
			<div class=" col-md-3">
				<label class="form-label">Desconto:</label> <input type="text"
					class="form-control" name="desconto"
					value="<%=request.getAttribute("desconto") != null ? request.getAttribute("desconto").toString() : "0.00"%>"
					id="desconto">
			</div>
			<div>
				<button type="button" class="btn btn-primary" data-bs-toggle="modal"
					data-bs-target="#confirmacaoModal">Pagamento</button>

				<input type="button" class="btn btn-danger" value="Desconto"
					id="descontoBtn"> <a type="button" class="btn btn-warning"
					href="#" data-bs-toggle="modal" data-bs-target="#CancelarVenda">Cancelar
					Venda</a>
			</div>

		</div>
		<form action="InseirVendaEintens" id="modalVendas" method="get">
			<div class="modal fade" tabindex="-1" id="confirmacaoModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">Pagamento</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>
						<div class="modal-body">
							<div>

								<select class="form-select" name=formaPagamento
									id="formaPagamento">
									<option value="">Selecione a Opçao de pagamento</option>
									<option value="Dinheiro">Dinheiro</option>
									<option value="Crédito">Crédito</option>
									<option value="Débito">Débito</option>
									<option value="Pix">Pix</option>
									<option value="Anotado">Anotado</option>

								</select>
								<div class=" col-md-3">
									<label class="form-label">Valor: </label> <input type="text"
										class="form-control ml-1" id="pegardoTotal"
										value="<%=session.getAttribute("totalVenda")%>"
										name="totalVenda">

								</div>
								<div class="col-md-3" id="dinheiroRecebidoDiv">
									<label class="form-label">Dinheiro recebido: </label> <input
										type="text" class="form-control ml-1" id="dinheiroRecebido">

								</div>
								<div class="col-md-3" id="trocoDiv">
									<label class="form-label"> Troco: </label> <input type="text"
										class="form-control" id="trocoVenda" value="0.00"> <input
										type="button" class="btn btn-success" value="Confimar Troco"
										style="margin-top: 10px;" id="btn-troco">

								</div>

								<div class="col-md-6" id="observacaoDiv">
									<label class="form-label"> Observação: </label> <input
										type="text" class="form-control" id="observacao"
										name="observacao">


								</div>
							</div>

						</div>
						<div class="modal-footer">
							<h4 class="display-7">Deseja finalizar a venda ?</h4>
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Não</button>
							<button type="submit" class="btn btn-primary">Sim</button>

						</div>

					</div>
				</div>
			</div>
			<input type="hidden" name="cliId"
				value="<%=request.getAttribute("cliId")%>"> <input
				type="hidden" name="data" value="<%=datamysql%>"> <input
				type="hidden" name="lucro"
				value="<%=session.getAttribute("lucro")%>"> <input
				type="hidden" class="form-control" name="desconto"
				value="<%=request.getAttribute("desconto") != null ? request.getAttribute("desconto").toString() : "0.00"%>"
				id="desconto">
			<%
			if (itensArray != null) {
				for (int i = 0; i < itensArray.length(); i++) {
					JSONObject itemJson_3 = itensArray.getJSONObject(i);
			%>
			<input type="hidden" name="idProd" id="idProd"
				value="<%=itemJson_3.getString("idProd")%>"> <input
				type="hidden" name="qtdProd" id="qtdProd"
				value="<%=itemJson_3.getString("qtdProd")%>"> <input
				type="hidden" name="subtotal" id="subtotal"
				value="<%=itemJson_3.getString("subtotal")%>">
			<%
			}
			}
			%>

		</form>

		<div class="modal fade" tabindex="-1" id="CancelarVenda">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Deseja Cancelar a Venda ?</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer">

						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Não</button>
						<a type="submit" class="btn btn-primary" href="detroyLista.jsp">Sim</a>

					</div>

				</div>
			</div>
		</div>
	</main>




	<!-- Bootstrap JavaScript Bundle com Popper -->
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.6/jquery.inputmask.min.js"></script>
<script>
		var totalValue = 0;
		var lucroValue = 0;
		var pegarTotal = 0;

		document
				.getElementById("addItemBtn")
				.addEventListener(
						"click",
						function() {
							// Obter os valores dos campos
							var idProd = document.getElementById("idProd").value;
							var desProd = document.getElementById("desProd").value;
							var qtdProd = document.getElementById("qtdProd").value;
							var precoProd = document
									.getElementById("precoProd").value;
							var meuPreco = document
									.getElementById("compraProd").value;
							var desconto = document
							.getElementById("desconto").value;
							

							// Verificar se os campos de entrada não estão vazios
							if (idProd && desProd && qtdProd && precoProd
									&& meuPreco) {
								// Converter os valores para números
								var quantidade = parseInt(qtdProd);
								var preco = parseFloat(precoProd);
								var precoCompra = parseFloat(meuPreco);

								// Verificar se os valores são números válidos
								if (!isNaN(quantidade) && !isNaN(preco)
										&& !isNaN(precoCompra) && !isNaN(desconto)) {
									// Calcular o subtotal
									var subtotalValue = quantidade * preco;
									var lucroUnitario = preco - precoCompra; // Calcular o lucro unitário
									var lucroCalculo = lucroUnitario
											* quantidade; // Calcular o lucro total

									// Criar um objeto com os dados do item
									var item = {
										idProd : idProd,
										desProd : desProd,
										qtdProd : qtdProd,
										precoProd : precoProd,
										compraProd: meuPreco,
										subtotal : subtotalValue,
										desconto : desconto
									};

									// Enviar os dados para o Servlet usando AJAX
									var xhr = new XMLHttpRequest();
									xhr.open("POST", "inserirItens", true);
									xhr.setRequestHeader("Content-Type",
											"application/json");
									xhr.onreadystatechange = function() {
										if (xhr.readyState == 4
												&& xhr.status == 200) {
											// Adicionar a nova linha à tabela após a resposta do Servlet
											var newRow = xhr.responseText;
											var tableBody = document
													.getElementById("carrinho")
													.getElementsByTagName(
															"tbody")[0];
											tableBody.insertAdjacentHTML(
													"beforeend", newRow);

											// Limpar os campos após adicionar o item
											document.getElementById("idProd").value = "";
											document.getElementById("desProd").value = "";
											document.getElementById("qtdProd").value = "";
											document
													.getElementById("precoProd").value = "";
											document
													.getElementById("compraProd").value = ""; // Limpar o campo de preço de compra

											// Atualizar o valor total da venda e o lucro
											var totalVendaInput = document
													.getElementById("totalVenda");
											totalVendaInput.value = parseFloat(totalVendaInput.value)
													+ subtotalValue;

											var lucroInput = document
													.getElementById("lucro");
											var lucroAtual = parseFloat(lucroInput.value);
											if (!isNaN(lucroAtual)) {
												lucroInput.value = lucroAtual
														+ lucroCalculo;
											}
										}
									};
									xhr.send(JSON.stringify(item));
								} else {
									alert("Por favor, insira valores numéricos válidos para quantidade, preço e preço de compra.");
								}
							} else {
								alert("Por favor, preencha todos os campos.");
							}
						});

		// Função para calcular o total da venda e o lucro
	
		// Função para calcular o total da venda e o lucro
		

		// Função para calcular o total da venda e o lucro
		function updateTotal(subtotalValue, lucroCalculo) {
			var totalValue = parseFloat(document.getElementById("totalVenda").value);
			var lucroValue = parseFloat(document.getElementById("lucro").value);

			totalValue += subtotalValue; // Adicionar o subtotal ao total
			lucroValue += lucroCalculo; // Adicionar o lucro ao total de lucro

			document.getElementById("totalVenda").value = totalValue.toFixed(2);
			document.getElementById("lucro").value = lucroValue.toFixed(2); // Exibir o lucro com duas casas decimais
		}

		// Função para calcular o total da venda e o lucro
		function updateTotal(subtotalValue, lucroCalculo) {
			totalValue += subtotalValue; // Adicionar o subtotal ao total
			lucroValue += lucroCalculo; // Adicionar o lucro ao total de lucro
			totalValue += pegarTotal;

			document.getElementById("total").value = totalValue.toFixed(2);
			document.getElementById("lucro").value = lucroValue.toFixed(2); // Exibir o lucro com duas casas decimais
			document.getElementById("pegardoTotal").value = totalValue
					.toFixed(2);
		}
		// Função para mostrar ou ocultar os campos com base na opção selecionada
		function mostrarCampos() {
			var select = document.getElementById("formaPagamento");
			var dinheiroRecebidoDiv = document
					.getElementById("dinheiroRecebidoDiv");
			var trocoDiv = document.getElementById("trocoDiv");

			// Verifica se a opção selecionada é "Dinheiro"
			if (select.value === "Dinheiro") {
				dinheiroRecebidoDiv.style.display = "block"; // Mostra o campo "Dinheiro recebido"
				trocoDiv.style.display = "block"; // Mostra o campo "Troco"
			} else {
				dinheiroRecebidoDiv.style.display = "none"; // Oculta o campo "Dinheiro recebido"
				trocoDiv.style.display = "none"; // Oculta o campo "Troco"
			}
		}

		// Chama a função ao carregar a página e quando o valor do select mudar
		window.onload = mostrarCampos;
		document.getElementById("formaPagamento").addEventListener("change",
				mostrarCampos);

		function mostrarObservacao() {
			var select = document.getElementById("formaPagamento");
			var observacao = document.getElementById("observacaoDiv");

			// Verifica se a opção selecionada é "Dinheiro"
			if (select.value === "Anotado") {
				observacao.style.display = "block"; // Mostra o campo "observacao"
				// Mostra o campo "Troco"
			} else {
				observacao.style.display = "none";// Oculta o campo "observacao"
			}
		}

		// Chama a função ao carregar a página e quando o valor do select mudar
		window.onload = mostrarObservacao;
		document.getElementById("formaPagamento").addEventListener("change",
				mostrarObservacao);
	</script>

	<script>
		document.getElementById("addItemBtn").addEventListener("click",
				function() {
					// Fazer uma requisição AJAX para o servlet
					var xhr = new XMLHttpRequest();
					xhr.open("POST", "inserirItens", true);
					xhr.setRequestHeader("Content-Type", "application/json");
					xhr.onreadystatechange = function() {
						if (xhr.readyState === 4 && xhr.status === 200) {
							// A requisição foi bem-sucedida, você pode executar ações adicionais aqui se necessário
							console.log("Sessão atualizada com sucesso!");

						}
					};
					xhr.send();
				});
	</script>
	<script>
	document.getElementById("descontoBtn").addEventListener("click", function() {
	    var desconto = parseFloat(document.getElementById("desconto").value);
	    var totalValue = parseFloat(document.getElementById("totalVenda").value);
	    
	    if (!isNaN(desconto)) {
	        totalValue -= desconto;
	        document.getElementById("totalVenda").value = totalValue.toFixed(2);
	    } else {
	        alert("Por favor, insira um valor válido para o desconto.");
	    }
	});
	
	
	</script>
	<script>
     document.getElementById("desconto").addEventListener("input", function() {
     document.getElementById("vdesconto").value = this.value;
});
     document.getElementById("totalVenda").addEventListener("input", function() {
         document.getElementById("pegardoTotal").value = this.value;
    });
</script>
	<script>
document.getElementById("carrinho").addEventListener("click", function(event) {
    var row = event.target.parentNode.parentNode;
    var idProd = row.cells[0].innerText;
 
    var qtdProd = row.cells[2].innerText;
    
    var subtotal = row.cells[4].innerText;

    document.getElementById("idProd").value = idProd;
   
    document.getElementById("qtdProd").value = qtdProd;
   
    document.getElementById("subtotal").value = subtotal;

    // Adicione este trecho para enviar os valores qtdProd e subtotal para o servidor
    document.getElementById("qtdProdHidden").value = qtdProd;
    document.getElementById("subtotalHidden").value = subtotal;
});

});

</script>
	<script>
    document.getElementById("btn-troco").addEventListener("click", function() {
        var pegarTotal = parseFloat(document.getElementById("pegardoTotal").value);
        var dinheiroRecebido = parseFloat(document.getElementById("dinheiroRecebido").value);
        
        if (!isNaN(pegarTotal) && !isNaN(dinheiroRecebido)) {
            var troco = dinheiroRecebido - pegarTotal;
            document.getElementById("trocoVenda").value = troco.toFixed(2);
        } else {
            alert("Por favor, insira valores válidos.");
        }
    });
</script>
<script>
    // Verificar a validade do formulário ao enviar
    (function () {
        'use strict'
        var forms = document.querySelectorAll('.needs-validation')
        Array.prototype.slice.call(forms).forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
    })();

    // Verificar validade dos campos no botão "Adicionar item"
    document.getElementById("addItemBtn").addEventListener("click", function (event) {
        var form = document.getElementById('buscarClienteForm');
        if (!form.checkValidity()) {
            event.preventDefault()
            event.stopPropagation()
        }
        form.classList.add('was-validated');
    });
</script>




</body>
</html>