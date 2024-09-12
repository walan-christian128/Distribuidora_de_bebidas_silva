package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import DAO.UsuarioDAO;
import Model.PasswordUtil;
import Model.Usuario;

/**
 * Servlet implementation class userLogin
 */
@WebServlet(name = "Login.jsp", urlPatterns = { "/logar" })
public class userLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public userLogin() {
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

		RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
		rd.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    // Obtendo os parâmetros da requisição
	    String usuario = request.getParameter("email");
	    String senha = request.getParameter("senha");
	    String empresa = request.getParameter("empresa");
	    
	    // Verificar se os parâmetros necessários foram fornecidos
	    if (usuario == null || usuario.isEmpty() || senha == null || senha.isEmpty() || empresa == null || empresa.isEmpty()) {
	        // Se algum parâmetro estiver faltando, redirecionar para a página de login com uma mensagem de erro
	        request.setAttribute("erro", "Todos os campos devem ser preenchidos.");
	        RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
	        rd.forward(request, response);
	        return;
	    }

	    // Criar ou obter a sessão
	    HttpSession session = request.getSession();
	    
	    // Definir o atributo da empresa na sessão
	    session.setAttribute("empresa", empresa);

	    try {
	        // Criar o DAO para o usuário com base na empresa
	        UsuarioDAO dao = new UsuarioDAO(empresa);
	        
	        // Verificar se o login é válido
	        boolean loginValido = dao.efetuarLogin(usuario, senha, empresa);

	        if (loginValido) {
	            // Redirecionar para a página principal após o login bem-sucedido
	            response.sendRedirect("Home.jsp");
	        } else {
	            // Exibir mensagem de erro se o login falhar
	            request.setAttribute("erro", "Usuário, senha ou empresa incorretos.");
	            RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
	            rd.forward(request, response);
	        }
	    } catch (Exception e) {
	        // Tratar exceções e exibir mensagem de erro genérica
	        e.printStackTrace();
	        request.setAttribute("erro", "Ocorreu um erro ao processar a solicitação.");
	        RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
	        rd.forward(request, response);
	    }
	}


}
