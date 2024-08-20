package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.createData;
import DAO.VendasDAO;
import DAO.itensVendaDAO;
import Model.ItensVenda;
import Model.Usuario;
import Model.Vendas;

@WebServlet(urlPatterns = { "/selecionarVenda", "/totalVendas","/carregarBase","/CadastroUserEmpresa"})
public class inicialServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public inicialServer() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println("Ação recebida: " + action);
		if (action.equals("/selecionarVenda")) {
			itensPorvenda(request, response);
		}else if (action.equals("/carregarBase")) {
			try {
				listaDataBase(request, response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(action.equals("/CadastroUserEmpresa")) {
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
	private void createBase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	                // Método `inserirUsuarioEmpresa` já está definido para trabalhar com a conexão do banco
	                data.inserirUsuarioEmpresa(uso);
	            }

	            // Redireciona para a página de login
	            RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
	            rd.forward(request, response);

	        } catch (Exception e) {
	            e.printStackTrace();
	            // Enviar uma mensagem de erro para o usuário ou redirecionar para uma página de erro
	            request.setAttribute("errorMessage", "Ocorreu um erro ao criar o banco de dados e/ou inserir o usuário.");
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
		VendasDAO dao = new VendasDAO();
		ArrayList<Vendas> lista = (ArrayList<Vendas>) dao.listarVendasdoDia();
		request.setAttribute("Vendas", lista);
		RequestDispatcher rd = request.getRequestDispatcher("Home.jsp");
		rd.forward(request, response);
	}

	private void itensPorvenda(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idVenda = request.getParameter("id");
		int vendaID = Integer.parseInt(idVenda);
		if (idVenda != null) {
			itensVendaDAO itdao = new itensVendaDAO();
			try {
				ArrayList<ItensVenda> lista_2 = (ArrayList<ItensVenda>) itdao.listarItensPorVendao(vendaID);
				
				request.setAttribute("tableRows", lista_2);
				RequestDispatcher rd = request.getRequestDispatcher("DetalheVenda.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void listaDataBase(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException, ClassNotFoundException {
	    createData data = new createData();
	    try {
	        ArrayList<String> lista = (ArrayList<String>) data.ListarBases();
	       // Verifique o tamanho da lista
	        request.setAttribute("base", lista);
	    } catch (Exception e) {
	        e.printStackTrace(); // Imprima a pilha de erros para depuração
	    }
	    RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
	    rd.forward(request, response);
	}

	
}
