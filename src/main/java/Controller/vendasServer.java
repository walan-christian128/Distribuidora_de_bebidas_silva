package Controller;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import DAO.ClientesDAO;
import DAO.ProdutosDAO;
import DAO.VendasDAO;
import DAO.itensVendaDAO;
import Model.Clientes;
import Model.ItensVenda;
import Model.Produtos;
import Model.Vendas;

/**
 * Servlet implementation class vendasServer
 */

@WebServlet(urlPatterns = { "/selecionarClienteProdutos", "/inserirItens", "/InseirVendaEintens", "/PeriodoVenda",
		"/dia","/maisVendidos" })

public class vendasServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	double total, subtotal, lucro, preco, meuPreco;

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
        // Obtendo a sessão
        HttpSession session = request.getSession();
        String empresa = (String) session.getAttribute("empresa"); // Exemplo de atributo de sessão

        // Agora, você pode usar o valor da "empresa" em qualquer parte do seu código
        if (empresa != null) {
            System.out.println("Empresa selecionada: " + empresa);
        } else {
            System.out.println("Nenhuma empresa selecionada.");
        }

        String action = request.getServletPath();
        switch (action) {
            case "/selecionarClienteProdutos":
                try {
                    selecionarClienteProd(request, response);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "/inserirItens":
                inserirItens(request, response);
                break;
            case "/InseirVendaEintens":
                inserirVendas(request, response);
                break;
            case "/PeriodoVenda":
                vendaPorPeriodo(request, response);
                break;
            case "/dia":
                vendaPorDia(request, response);
                break;
            case "/maisVendidos":
                maisVendidos(request, response);
                break;
            default:
                response.getWriter().append("Ação não reconhecida.");
                break;
        }
    }

	private void maisVendidos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dataVendainicio = request.getParameter("dataVendainicio");
		String dataVendafim = request.getParameter("dataVendafim");
		
		HttpSession session = request.getSession();
        String empresa = (String) session.getAttribute("empresa");
		
	        if(dataVendainicio !=null && dataVendafim !=null) {
	        	String fomatoData = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(fomatoData);
				
	        	try {
	        		Date datainicalFormata = sdf.parse(dataVendainicio);
					Date datafinalFormata = sdf.parse(dataVendafim);
					VendasDAO dao = new VendasDAO(empresa);
					
					ArrayList<ItensVenda> lista_2 = (ArrayList<ItensVenda>) dao.maisVendidos(datainicalFormata,
							datafinalFormata);
				
					 request.setAttribute("maisVendidos", lista_2);
					 
					 RequestDispatcher dispatcher = request.getRequestDispatcher("menu.jsp");
			         dispatcher.forward(request, response);
					
				
					
					
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
	        	
	        	
	        }
		
	}

	private void vendaPorDia(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String data = request.getParameter("data");
		
		HttpSession session = request.getSession();
        String empresa = (String) session.getAttribute("empresa");
        
        try {
            SimpleDateFormat dataVenda = new SimpleDateFormat("dd/MM/yyyy");
            Date dataVendaInf = dataVenda.parse(data);

            VendasDAO dao = new VendasDAO(empresa);
            double totalVenda = dao.retornaTotalVendaPorData(dataVendaInf);

            request.setAttribute("totalVenda", totalVenda);
            request.setAttribute("data", data);
            RequestDispatcher dispatcher = request.getRequestDispatcher("menu.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    
	}

	private void vendaPorPeriodo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");
		
		HttpSession session = request.getSession();
        String empresa = (String) session.getAttribute("empresa");

		if (dataInicial != null && dataFinal != null) {
			String fomatoData = "dd/MM/yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(fomatoData);

			try {
				Date datainicalFormata = sdf.parse(dataInicial);
				Date datafinalFormata = sdf.parse(dataFinal);
				VendasDAO dao = new VendasDAO(empresa);
				ArrayList<Vendas> lista_2 = (ArrayList<Vendas>) dao.totalPorPeriodo(datainicalFormata,
						datafinalFormata);
				request.setAttribute("periodo", lista_2);
				RequestDispatcher dispatcher = request.getRequestDispatcher("menu.jsp");
				dispatcher.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
	}

	private void inserirVendas(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		  String empresa = (String) session.getAttribute("empresa");
		  
		String idCli = request.getParameter("cliId");
		int cliId = Integer.parseInt(idCli);

		Vendas obj = new Vendas();

		if (idCli != null && !idCli.trim().isEmpty()) {
			try {
				Clientes objCli = new Clientes();

				objCli.setId(cliId);
				obj.setCliente(objCli);

				obj.setData_venda(request.getParameter("data"));
				obj.setTotal_venda(Double.parseDouble(request.getParameter("totalVenda")));
				obj.setObs(request.getParameter("observacao"));
				obj.setLucro(Double.parseDouble(request.getParameter("lucro")));
				obj.setDesconto(Double.parseDouble(request.getParameter("desconto")));
				obj.setFormaPagamento(request.getParameter("formaPagamento"));

				VendasDAO dao = new VendasDAO(empresa);
				dao.cadastrarVenda(obj);

				/* System.out.println("Cliente" + objCli.getId()); */
				//// tabela vendas ok///itens sendo inseridos///não mexer

				obj.setId(dao.retornaUltimaVenda());
				/* System.out.println("ID da última venda: " + dao.retornaUltimaVenda()); */

				JSONArray itensArray = (JSONArray) session.getAttribute("itens");
				if (itensArray != null) {
					for (int i = 0; i < itensArray.length(); i++) {
						JSONObject linha = itensArray.getJSONObject(i);

						total = 0.0;
						lucro = 0.0;

						String idProdVenda = linha.getString("idProd");
						String qtdProd = linha.getString("qtdProd");
						String subItens = linha.getString("subtotal");

						ProdutosDAO dao_produto = new ProdutosDAO();
						itensVendaDAO daoitem = new itensVendaDAO();
						Produtos objp = new Produtos();
						ItensVenda itens = new ItensVenda();

						itens.setVenda(obj);
						objp.setId(Integer.parseInt(idProdVenda));
						itens.setProduto(objp);
						itens.setQtd(Integer.parseInt(qtdProd));
						itens.setSubtotal(Double.parseDouble(subItens));

						int qtd_estoque, qtd_comprada, qtd_atualizada;
						// Baixa no estoque
						qtd_estoque = dao_produto.retornaEstoqueAtual(objp.getId());
						qtd_comprada = Integer.parseInt(qtdProd);
						qtd_atualizada = qtd_estoque - qtd_comprada;

						dao_produto.baixarEstoque(objp.getId(), qtd_atualizada);

						// Cadastrar o item de venda
						daoitem.cadastraItem(itens);

						/*
						 * System.out.println("Codigo Produto " + idProdVenda);
						 * System.out.println("Quantidade" + qtdProd);
						 * System.out.println("Codigo Produto " + subItens);
						 */

						/* System.out.println("Total: " + total); */

						// Faça o que precisar com os atributos

					}

					total = 0.0;
					lucro = 0.0;
					session.removeAttribute("totalVenda");

					session.removeAttribute("itens");
					session.removeAttribute("lucro");

					response.sendRedirect("realizarVendas.jsp");

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			HttpSession newSession = request.getSession(true);
			newSession.removeAttribute("totalVenda");
		}

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
			String precoMeu = itemJson.getString("compraProd");

			// Verificando se a quantidade não é nula
			if (qtdProd != null) {
				int qtdPrdo = Integer.parseInt(qtdProd);
				preco = Double.parseDouble(precoProd);
				meuPreco = Double.parseDouble(precoMeu);

				// Calculando o subtotal
				subtotal = qtdPrdo * preco;
				total += subtotal;
				lucro += preco - meuPreco;

				request.setAttribute("idProd", idProd);
				request.setAttribute("desProd", desProd);
				request.setAttribute("qtdProd", qtdProd);
				request.setAttribute("subtotal", subtotal);
				request.setAttribute("totalVenda", total);
				request.setAttribute("lucro", lucro);

				/* total = (Double) session.getAttribute("totalVenda"); */

				// Calculando o lucro para este item

				// Construindo a nova linha da tabela HTML
				String newRow = "<tr>" + "<td>" + idProd + "</td>" + "<td>" + desProd + "</td>" + "<td>" + qtdProd
						+ "</td>" + "<td>" + precoProd + "</td>" + "<td>" + subtotal + "</td>" + "</tr>";

				JSONObject newItem = new JSONObject();

				newItem.put("idProd", idProd);
				newItem.put("desProd", desProd);
				newItem.put("qtdProd", qtdProd);
				newItem.put("precoProd", precoProd);
				newItem.put("subtotal", String.valueOf(subtotal));
				newItem.put("totalVenda", String.valueOf(total));
				newItem.put("compraProd", String.valueOf(lucro));

				JSONArray itens = (JSONArray) session.getAttribute("itens");

				if (itens == null) {
					itens = new JSONArray();
				}

				itens.put(newItem);

				// Atualizar a lista de itens na sessão
				session.setAttribute("itens", itens);
				session.setAttribute("totalVenda", total);
				session.setAttribute("lucro", lucro);

				// Escrevendo a nova linha na resposta

				PrintWriter out = response.getWriter();
				out.println(newRow);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void selecionarClienteProd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException {
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
		HttpSession session = request.getSession();
		session.isNew();

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
