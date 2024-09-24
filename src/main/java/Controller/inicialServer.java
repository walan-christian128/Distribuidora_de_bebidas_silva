package Controller;

import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
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
import java.util.Properties;
import java.util.Random;

import jakarta.mail.Session;
import jakarta.mail.Message;

import Conexao.ConnectionFactory;
import DAO.createData;
import DAO.dataBsesDAO;
import DAO.UsuarioDAO;
import DAO.VendasDAO;
import DAO.itensVendaDAO;
import Model.ItensVenda;
import Model.Usuario;
import Model.Vendas;

@WebServlet(urlPatterns = { "/selecionarVenda", "/totalVendas", "/CadastroUserEmpresa", "/RecuperaSenhaServlet",
		"/AtualizaçãoSenha" })
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

		} else if (action.equals("/RecuperaSenhaServlet")) {
			try {
				enviarEmail(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (action.equals("/AtualizaçãoSenha")) {
			try {
				atualizaSenha(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void atualizaSenha(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, ClassNotFoundException {
		String senha = request.getParameter("senha");
		String senha2 = request.getParameter("senha2");
		String email = request.getParameter("email");
		String empresa = request.getParameter("empresa");

		if (senha != null && !senha.trim().isEmpty()) {
			Usuario uso = new Usuario();

			uso.setSenha(senha);
			uso.setEmail(email);
			
			if (senha .equals(senha2) ) {
				UsuarioDAO dao = new UsuarioDAO(empresa);
				dao.recuperaSenha(senha, email, empresa);
				request.setAttribute("ok", "senha alterada com sucesso");
				RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);

			} else if (senha != senha2) {
				
				request.setAttribute("erro","Campo confirmação de senha diferente do campo nova senha verifique o valor e digite novamente");
				RequestDispatcher rd = request.getRequestDispatcher("RedefinirSenha.jsp");
				rd.forward(request, response);
			}
		}

	}

	private void enviarEmail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, ClassNotFoundException {
		String email = request.getParameter("email");
		String empresa = request.getParameter("empresa");

		try {
			// Verifica se o email existe no banco de dados
			UsuarioDAO usuarioDAO = new UsuarioDAO(empresa);
			boolean emailExiste = usuarioDAO.enviaEmail(email, empresa);

			if (emailExiste) {
				// Enviar OTP por e-mail
				String to = email;
				String resetLink = "http://localhost:8080/Distribuidora_de_bebidas_silva/RedefinirSenha.jsp"; // Gera o
																												// OTP
																												// aqui

				// Configurações do servidor de e-mail
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");

				// Autenticação para envio de e-mail
				Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("walancristiano@gmail.com", "kjtd hzzx syze ysvo"); // Use o
																												// seu
																												// e-mail
																												// e
																												// senha
					}
				});

				try {
					// Criando a mensagem de e-mail
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("walancristiano@gmail.com")); // E-mail do remetente
					message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to)); // E-mail do
																									// destinatário
					message.setSubject("Recuperação de Senha");
					message.setText("Click no link para redefinição de senha: " + resetLink);

					// Enviando o e-mail
					Transport.send(message);
					System.out.println("E-mail enviado com sucesso");

					// Define a mensagem de sucesso na requisição
					request.setAttribute("ok", "Email enviado com sucesso.");

					// Encaminha a requisição para o JSP
					RequestDispatcher rd = request.getRequestDispatcher("RecuperarSenha.jsp");
					rd.forward(request, response);

				} catch (MessagingException e) {
					e.printStackTrace();
					request.setAttribute("erro", "Falha ao enviar o e-mail.");
					RequestDispatcher rd = request.getRequestDispatcher("RecuperarSenha.jsp");
					rd.forward(request, response);
				}

			} else {
				// Caso o e-mail ou a empresa estejam incorretos
				request.setAttribute("erro", "Email ou empresa incorretos.");
				RequestDispatcher rd = request.getRequestDispatcher("RecuperarSenha.jsp");
				rd.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erro", "Ocorreu um erro ao processar a solicitação.");
			RequestDispatcher rd = request.getRequestDispatcher("RecuperarSenha.jsp");
			rd.forward(request, response);
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
		HttpSession session = request.getSession();
		String empresa = (String) session.getAttribute("empresa");
		String idVenda = request.getParameter("id");
		int vendaID = Integer.parseInt(idVenda);
		if (idVenda != null) {

			try {
				itensVendaDAO itdao = new itensVendaDAO(empresa);
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
