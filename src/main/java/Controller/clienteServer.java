package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;



import DAO.ClientesDAO;
import Model.Clientes;

/**
 * Servlet implementation class fornecedorServer
 */
@WebServlet(urlPatterns = { "/insertClientes", "/atualizarClientes", "/selectClientes","/apagarClientes"})
public class clienteServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Clientes obj = new Clientes();
	ClientesDAO dao = new ClientesDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public clienteServer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		

		String action = request.getServletPath();
		System.out.println(action);
		 if (action.equals("/insertClientes")) {
		        CadastrarClientes(request, response);
		    } else if (action.equals("/atualizarClientes")) {
		        atualizarClientes(request, response);
		    } else if (action.equals("/selectClientes")) {
		        modalSelect(request, response);
		    }
		    else if (action.equals("/apagarClientes")) {
		        apagarFornecedor(request, response);
		    }

	}
	
	

	private void apagarFornecedor(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		String id = request.getParameter("id");
		if(id != null) {
			obj.setId(Integer.parseInt(id));
			dao.excluirCliente(obj);
			response.sendRedirect("Clientes.jsp");
			
		}
		
	}

	private void modalSelect(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	  String idFornecedor = request.getParameter("id");

		try {
			obj.setId(Integer.parseInt(idFornecedor));
			dao.modalClientes(obj);
			request.setAttribute("id", obj.getId());
			request.setAttribute("nome", obj.getNome());
			request.setAttribute("cpf", obj.getCpf());
			request.setAttribute("email", obj.getEmail());
			request.setAttribute("celular", obj.getCelular());
			request.setAttribute("telefone", obj.getTelefone());
			request.setAttribute("cep", obj.getCep());
			request.setAttribute("endereco", obj.getEndereco());
			request.setAttribute("numero", obj.getNumero());
			request.setAttribute("bairro", obj.getBairro());
			request.setAttribute("cidade", obj.getCidade());
			request.setAttribute("complemento", obj.getComplemento());
			request.setAttribute("estado", obj.getUf());
		
			RequestDispatcher rd = request.getRequestDispatcher("EditarClientes.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			
		
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void atualizarClientes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String idCli = request.getParameter("id");
			if (idCli != null && !idCli.trim().isEmpty()) {
				obj.setId(Integer.parseInt(request.getParameter(idCli)));
			}
			String nomeCli = request.getParameter("nome");
			if (nomeCli != null && !nomeCli.trim().isEmpty()) {
				obj.setNome(nomeCli);
			}
			String cpfCli= request.getParameter("cnpj");
			if (cpfCli != null && !cpfCli.trim().isEmpty()) {
				obj.setCpf(cpfCli);
			}
			String emailCli = request.getParameter("email");
			if (emailCli != null && !emailCli.trim().isEmpty()) {
				obj.setEmail(emailCli);
			}
			String telefoneCli = request.getParameter("telefone");
			if (telefoneCli != null && !telefoneCli.trim().isEmpty()) {
				obj.setTelefone(telefoneCli);
			}
			String celularCli = request.getParameter("celular");
			if (celularCli != null && !celularCli.trim().isEmpty()) {
				obj.setCelular(celularCli);
			}
			String cepCli = request.getParameter("cep");
			if (cepCli != null && !cepCli.trim().isEmpty()) {
				obj.setCep(cepCli);
			}

			String enderecoCli = request.getParameter("endereco");
			if (enderecoCli != null && !enderecoCli.trim().isEmpty()) {
				obj.setEndereco(enderecoCli);
			}
			String numeroCli = request.getParameter("numero");
			if (numeroCli != null && !numeroCli.trim().isEmpty()) {
				obj.setNumero(Integer.parseInt(numeroCli));
			}
			String complementoCli = request.getParameter("complemento");
			if (complementoCli != null && !complementoCli.trim().isEmpty()) {
				obj.setComplemento(complementoCli);
			}
			String bairroCli = request.getParameter("bairro");
			if (bairroCli != null && !bairroCli.trim().isEmpty()) {
				obj.setBairro(bairroCli);
			}

			String cidadeCli = request.getParameter("cidade");
			if (cidadeCli != null && !cidadeCli.trim().isEmpty()) {
				obj.setCidade(cidadeCli);
			}
			String estadoCli = request.getParameter("estado");
			if (estadoCli != null && !estadoCli.trim().isEmpty()) {
				obj.setUf(estadoCli);
			}
			dao.alterarCliente(obj);
			response.sendRedirect("Clientes.jsp");
		} catch (Exception e) {

		}

	}

	private void CadastrarClientes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cliNome = request.getParameter("nome");
		if (cliNome != null && !cliNome.trim().isEmpty()) {
			try {

				obj.setNome(cliNome);
				obj.setCpf(request.getParameter("cpf"));
				obj.setEmail(request.getParameter("email"));
				obj.setCelular(request.getParameter("celular"));
				obj.setTelefone(request.getParameter("telefone"));
				obj.setCep(request.getParameter("cep"));
				obj.setEndereco(request.getParameter("endereco"));
				obj.setNumero(Integer.parseInt(request.getParameter("numero")));
				obj.setBairro(request.getParameter("bairro"));
				obj.setCidade(request.getParameter("cidade"));
				obj.setComplemento(request.getParameter("complemento"));
				obj.setUf(request.getParameter("estado"));
				dao.cadastrarCliente(obj);

				response.sendRedirect("Clientes.jsp");

			} catch (Exception e) {

			}
		}

	}

}
