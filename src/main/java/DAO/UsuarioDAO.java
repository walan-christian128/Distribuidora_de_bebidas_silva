package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Conexao.ConectionDataBases;
import Model.PasswordUtil;
import Model.Usuario;

public class UsuarioDAO {
	
	 private Connection con;
	 private ConectionDataBases connectionFactory;
	
	 public UsuarioDAO(String databaseName) {
		 this.connectionFactory = new ConectionDataBases(databaseName);
	        try {
	            this.con = connectionFactory.getConectionDataBases();
	        } catch (SQLException e) {
	            e.printStackTrace(); // Trate a exceção conforme necessário
	        }
	    }
	@SuppressWarnings("static-access")
	public boolean efetuarLogin(String usuario, String senha, String empresa) throws ClassNotFoundException {
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    boolean loginValido = false;

	    try {
	    	 Class.forName("com.mysql.cj.jdbc.Driver");
	        // Conectar ao MySQL sem especificar um banco de dados
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "walan", "359483wa@");

	        // Usar o banco de dados dinamicamente com base no nome da empresa
	        String useDatabase = "USE " + empresa;
	        Statement stmtUse = con.createStatement();
	        stmtUse.execute(useDatabase);
	        System.out.println("Banco de dados selecionado: " + empresa);  // Confirmação do banco de dados

	        // Preparar a query para verificar o login
	        String sql = "SELECT SENHA FROM tb_usuario WHERE EMAIL = ?";
	        System.out.println("Query: " + sql);  // Exibe a query para debug
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, usuario);

	        // Executar a query
	        rs = stmt.executeQuery();

	        // Verificar se o usuário foi encontrado e comparar a senha
	        if (rs.next()) {
	            String senhaHash = rs.getString("SENHA");

	            // Criar instância de PasswordUtil para comparar o hash
	            PasswordUtil passUtil = new PasswordUtil();
	            String senhaHashFornecida = passUtil.hashPassword(senha);

	            // Comparar o hash armazenado com o hash da senha fornecida
	            if (senhaHash.equals(senhaHashFornecida)) {
	                System.out.println("Usuário encontrado e senha correta!");  // Verifica se o login é válido
	                loginValido = true;
	            } else {
	                System.out.println("Senha incorreta!");  // Senha não coincide com o hash
	            }
	        } else {
	            System.out.println("Usuário não encontrado!");  // Usuário não encontrado no banco de dados
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Fechar os recursos
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return loginValido;
	}



}
