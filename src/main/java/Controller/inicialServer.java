package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexao.ConnectionFactory;
import DAO.createData;
import DAO.dataBsesDAO;
import DAO.UsuarioDAO;
import DAO.VendasDAO;
import DAO.itensVendaDAO;
import Model.ItensVenda;
import Model.Usuario;
import Model.Vendas;

@WebServlet(urlPatterns = { "/selecionarVenda", "/totalVendas", "/CadastroUserEmpresa"})
public class inicialServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;

	public inicialServer() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		String action = request.getServletPath();
		System.out.println("Ação recebida: " + action);
		if (action.equals("/selecionarVenda")) {
			itensPorvenda(request, response);
		} else if (action.equals("/CadastroUserEmpresa")) {
			try {
				createBase(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} 
	}

	

	@SuppressWarnings("static-access")
	private void createBase(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String empresa = request.getParameter("base");

		if (empresa != null && !empresa.trim().isEmpty()) {
			try {
				// Criação da instância de createData que irá criar o banco e as tabelas
				createData data = new createData(empresa);

				// Coletar dados do usuário
				String nomeUsuario = request.getParameter("nome");
				String usuarioTelefone = request.getParameter("telefone");
				String usuarioEmail = request.getParameter("email");
				String usuarioSenha = request.getParameter("senha");

				if (nomeUsuario != null && !nomeUsuario.trim().isEmpty()) {
					// Inserir o usuário na base de dados
					Usuario uso = new Usuario();
					uso.setNome(nomeUsuario);
					uso.setTelefone(usuarioTelefone);
					uso.setEmail(usuarioEmail);
					uso.setSenha(usuarioSenha);

					// Método `inserirUsuarioEmpresa` já está definido para trabalhar com a conexão
					// do banco
					data.inserirUsuarioEmpresa(uso);
				}

				// Redireciona para a página de login
				RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
				// Enviar uma mensagem de erro para o usuário ou redirecionar para uma página de
				// erro
				request.setAttribute("errorMessage",
						"Ocorreu um erro ao criar o banco de dados e/ou inserir o usuário.");
				RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
				rd.forward(request, response);
			}
		} else {
			// Tratamento para quando o nome da base está vazio ou nulo
			request.setAttribute("errorMessage", "O nome da base de dados não pode ser vazio.");
			RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void Vendas(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    String empresa = (String) session.getAttribute("empresa");
	    
	    if (empresa == null || empresa.isEmpty()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome da empresa não fornecido.");
	        return;
	    }

	    try {
	        // Passe o nome da empresa para o DAO
	        VendasDAO dao = new VendasDAO(empresa);
	        ArrayList<Vendas> lista = (ArrayList<Vendas>) dao.listarVendasdoDia();
	        request.setAttribute("Vendas", lista);
	        RequestDispatcher rd = request.getRequestDispatcher("Home.jsp");
	        rd.forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace(); // Imprimir a pilha de erros para depuração
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar a requisição.");
	    }
	}

	
	

	private void itensPorvenda(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idVenda = request.getParameter("id");
		int vendaID = Integer.parseInt(idVenda);
		if (idVenda != null) {
			
			try {
				itensVendaDAO itdao = new itensVendaDAO();
				ArrayList<ItensVenda> lista_2 = (ArrayList<ItensVenda>) itdao.listarItensPorVendao(vendaID);

				request.setAttribute("tableRows", lista_2);
				RequestDispatcher rd = request.getRequestDispatcher("DetalheVenda.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	

}
