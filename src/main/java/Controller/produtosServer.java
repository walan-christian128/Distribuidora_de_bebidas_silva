package Controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import DAO.ProdutosDAO;
import Model.Fornecedores;
import Model.Produtos;

/**
 * Servlet implementation class produtosServer
 */
@WebServlet(urlPatterns = {"/main", "/insert", "/select", "/update", "/delete" })
public class produtosServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProdutosDAO dao = new ProdutosDAO();
	Produtos prod = new Produtos();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public produtosServer() {
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

		if (action.equals("/insert")) {
			CadastrandoProdutos(request, response);
		} else if (action.equals("/select")) {
			listandoProduto(request, response);
		} else if (action.equals("/delete")) {
			ApagarProdutos(request, response);
		} else {
			response.sendRedirect("Produtos.jsp");

		}
	}

	private void listandoProduto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idProduto = request.getParameter("id");
		try {
			prod.setId(Integer.parseInt(idProduto));

			dao.consultarProduto(prod);
			request.setAttribute("id", prod.getId());
			request.setAttribute("descricao", prod.getDescricao());
			request.setAttribute("qtd_estoque", prod.getQtd_estoque());
			request.setAttribute("preco_de_compra", prod.getPreco_de_compra());
			request.setAttribute("preco_de_venda", prod.getPreco_de_venda());
			request.setAttribute("for_id", prod.getFornecedor().getNome());
		} catch (Exception e) {
			// TODO: handle exception
		}

		RequestDispatcher rd = request.getRequestDispatcher("EditarProduto.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Restante do código para setar os valores em "prod"
			prod.setId(Integer.parseInt(request.getParameter("id")));
			prod.setDescricao(request.getParameter("descricao"));

			String qtdEstoqueStr = request.getParameter("qtd_estoque");
			if (qtdEstoqueStr != null && !qtdEstoqueStr.isEmpty()) {
				prod.setQtd_estoque(Integer.parseInt(qtdEstoqueStr));
			}

			String precoCompraStr = request.getParameter("preco_de_compra");
			if (precoCompraStr != null && !precoCompraStr.isEmpty()) {
				prod.setPreco_de_compra(Double.parseDouble(precoCompraStr));
			}

			String precoVendaStr = request.getParameter("preco_de_venda");
			if (precoVendaStr != null && !precoVendaStr.isEmpty()) {
				prod.setPreco_de_venda(Double.parseDouble(precoVendaStr));
			}

			String idFornecedorStr = request.getParameter("for_id");
			if (idFornecedorStr != null && !idFornecedorStr.isEmpty()) {
				Fornecedores f = new Fornecedores();
				f.setId(Integer.parseInt(idFornecedorStr));
				prod.setFornecedor(f);
			} else {
				prod.setFornecedor(null);
			}

			dao.alterarProdutos(prod);
			response.sendRedirect("Produtos.jsp");
		} catch (NumberFormatException e) {
			// Lide com o caso em que há um problema de formato numérico

			e.printStackTrace();
		} catch (Exception e) {
			// Lide com outras exceções não previstas

			e.printStackTrace();
		}
	}

	protected void CadastrandoProdutos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		     String prodDescricao = request.getParameter("descricao");
	     if(prodDescricao !=null &&!prodDescricao.trim().isEmpty()) {
		try {
			
			prod.setDescricao(prodDescricao);
			prod.setPreco_de_compra(Double.parseDouble(request.getParameter("preco_de_compra")));
			prod.setPreco_de_venda(Double.parseDouble(request.getParameter("preco_de_venda")));
			prod.setQtd_estoque(Integer.parseInt(request.getParameter("qtd_estoque")));
			Fornecedores fornecedores = new Fornecedores();
			fornecedores.setId(Integer.parseInt(request.getParameter("for_id")));
			prod.setFornecedor(fornecedores);
			dao.cadastrar(prod);
			response.sendRedirect("Produtos.jsp");

			
		} catch (Exception e) {
		
		}
	     }
	      
	}

	protected void ApagarProdutos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id != null) {
			try {

				prod.setId(Integer.parseInt(id));
				dao.excluir(prod);
				response.sendRedirect("Produtos.jsp");
			} catch (Exception e) {

			}

		}
	}

}
