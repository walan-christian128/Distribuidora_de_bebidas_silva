package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import DAO.ClientesDAO;
import DAO.ProdutosDAO;
import DAO.VendasDAO;
import Model.Clientes;
import Model.Produtos;
import Model.Vendas;

/**
 * Servlet implementation class vendasServer
 */
@WebServlet(urlPatterns = { "/selecionarClienteProdutos", "/inserirItens" })

public class vendasServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Vendas obj = new Vendas();
	VendasDAO dao = new VendasDAO();
	double total;
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public vendasServer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/selecionarClienteProdutos")) {
			selecionarClienteProd(request, response);

		} else if (action.equals("/inserirItens")) {
			inserirItens(request, response);

		}

		else {
			
		}
		

	}
	
	@SuppressWarnings("unused")
	private double calcularTotal(JSONArray itens) throws NumberFormatException, JSONException {
	    double total = 0;
	    if (itens != null) {
	        for (int i = 0; i < itens.length(); i++) {
	            JSONObject item = itens.getJSONObject(i);
	            double subtotal = Double.parseDouble(item.getString("subtotal"));
	            total += subtotal;
	        }
	    }
	    return total;
	}

	private void inserirItens(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		
		try {
			// Lendo os dados enviados pela requisição AJAX
			BufferedReader reader = request.getReader();
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			JSONObject itemJson = new JSONObject(sb.toString());

			String idProd = itemJson.getString("idProd");
			String desProd = itemJson.getString("desProd");
			String qtdProd = itemJson.getString("qtdProd");
			String precoProd = itemJson.getString("precoProd");
			String totalVenda = itemJson.getString("precoProd");
			

			// Verificando se a quantidade não é nula
			if (qtdProd != null) {
				int qtdPrdo = Integer.parseInt(qtdProd);
				double preco = Double.parseDouble(precoProd);
				

				// Calculando o subtotal
				double subtotalValue = qtdPrdo * preco;
			
			
				request.setAttribute("idProd", idProd);
				request.setAttribute("desProd", desProd);
				request.setAttribute("qtdProd", qtdProd);
			
				
				

				// Calculando o lucro para este item

				// Construindo a nova linha da tabela HTML
				String newRow = "<tr>" + "<td>" + idProd + "</td>" + "<td>" + desProd + "</td>" + "<td>" + qtdProd
						+ "</td>" + "<td>" + precoProd + "</td>" + "<td>" + subtotalValue + "</td>" + "</tr>";

				JSONObject newItem = new JSONObject();
				 
				newItem.put("idProd", idProd);
				newItem.put("desProd", desProd);
				newItem.put("qtdProd", qtdProd);
				newItem.put("precoProd", precoProd);
				newItem.put("subtotal", String.valueOf(subtotalValue));
				
				

				JSONArray itens = (JSONArray) session.getAttribute("itens");

				if (itens == null) {
					itens = new JSONArray();
				}

				itens.put(newItem);

				// Atualizar a lista de itens na sessão
				session.setAttribute("itens", itens);

				// Escrevendo a nova linha na resposta
				PrintWriter out = response.getWriter();
				out.println(newRow);
				
				out.close();

				System.out.println("ID do Produto: " + idProd);
				System.out.println("Descrição do Produto: " + desProd);
				System.out.println("Quantidade: " + qtdProd);
				System.out.println("Preço do Produto: " + precoProd);
				System.out.println("subtotal: " + subtotalValue);

				   

			}
			
			
			

			

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	private void selecionarClienteProd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cpfCli = request.getParameter("cliCpf");
		String idProdStr = request.getParameter("idProd");
		int idProd = Integer.parseInt(idProdStr);

		Produtos prod = new Produtos();
		ProdutosDAO prodDAO = new ProdutosDAO();
		Clientes cli = new Clientes();
		ClientesDAO cliDAO = new ClientesDAO();

		try {
			cli = cliDAO.consultarClientesPorcpf(cpfCli);
			request.setAttribute("cliId", cli.getId());
			request.setAttribute("cliNome", cli.getNome());
			request.setAttribute("cliCpf", cli.getCpf());
			request.setAttribute("cliEndereco", cli.getEndereco());
			request.setAttribute("cliNumero", cli.getNumero());
			prod = prodDAO.consultarPorCodigo(idProd);
			request.setAttribute("idProd", prod.getId());
			request.setAttribute("desProd", prod.getDescricao());
			request.setAttribute("compraProd", prod.getPreco_de_compra());
			request.setAttribute("precoProd", prod.getPreco_de_venda());

			RequestDispatcher rd = request.getRequestDispatcher("realizarVendas.jsp");
			rd.forward(request, response);

		} catch (Exception e) {

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}
