package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.xdevapi.Result;

import Conexao.ConnectionFactory;
import Model.Vendas;

public class createData {
	private static Connection con;

	public createData() {
		createData.con = new ConnectionFactory().getConnection();

	}

	public static void createDatabase(String databaseName) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Statement statement = con.createStatement();

			// Criar banco de dados
			String createDatabaseSQL = "CREATE DATABASE " + databaseName;
			statement.executeUpdate(createDatabaseSQL);
			System.out.println("Banco de dados criado com sucesso!");

			// Conectar ao novo banco de dados

			statement = con.createStatement();

			// Criar tabelas
			String createTableSQL = "CREATE TABLE tb_clientes (" + "    id int NOT NULL AUTO_INCREMENT,"
					+ "	   nome varchar(100) DEFAULT NULL," + "    rg varchar(30) DEFAULT NULL,"
					+ "    cpf varchar(20) DEFAULT NULL," + "    email varchar(200) DEFAULT NULL,"
					+ "    telefone varchar(30) DEFAULT NULL," + "    celular varchar(30) DEFAULT NULL,"
					+ "    cep varchar(100) DEFAULT NULL," + "    endereco varchar(255) DEFAULT NULL,"
					+ "    numero int DEFAULT NULL," + "    complemento varchar(200) DEFAULT NULL,"
					+ "    cidade varchar(100) DEFAULT NULL," + "    estado varchar(2) DEFAULT NULL,"
					+ "    PRIMARY KEY (id) " + ")";
			statement.executeUpdate(createTableSQL);

			String createTable_2 = "CREATE TABLE `tb_fornecedores` (" + "  `id` int NOT NULL AUTO_INCREMENT,"
					+ "  `nome` varchar(100) DEFAULT NULL," + "  `cnpj` varchar(100) DEFAULT NULL,"
					+ "  `email` varchar(200) DEFAULT NULL," + "  `telefone` varchar(30) DEFAULT NULL,"
					+ "  `celular` varchar(30) DEFAULT NULL," + "  `cep` varchar(100) DEFAULT NULL,"
					+ "  `endereco` varchar(255) DEFAULT NULL," + "  `numero` int DEFAULT NULL,"
					+ "  `complemento` varchar(200) DEFAULT NULL," + "  `bairro` varchar(100) DEFAULT NULL,"
					+ "  `cidade` varchar(100) DEFAULT NULL," + "  `estado` varchar(2) DEFAULT NULL,"
					+ "  PRIMARY KEY (`id`)"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
			statement.executeUpdate(createTable_2);

			String createTable_3 = "CREATE TABLE `tb_funcionarios` (" + "  `id` int NOT NULL AUTO_INCREMENT,"
					+ "  `nome` varchar(100) DEFAULT NULL," + "  `rg` varchar(30) DEFAULT NULL,"
					+ "  `cpf` varchar(20) DEFAULT NULL," + "  `email` varchar(200) DEFAULT NULL,"
					+ "  `senha` varchar(10) DEFAULT NULL," + "  `cargo` varchar(100) DEFAULT NULL,"
					+ "  `nivel_acesso` varchar(50) DEFAULT NULL," + "  `telefone` varchar(30) DEFAULT NULL,"
					+ "  `celular` varchar(30) DEFAULT NULL," + "  `cep` varchar(100) DEFAULT NULL,"
					+ "  `endereco` varchar(255) DEFAULT NULL," + "  `numero` int DEFAULT NULL,"
					+ "  `complemento` varchar(200) DEFAULT NULL," + "  `bairro` varchar(100) DEFAULT NULL,"
					+ "  `cidade` varchar(100) DEFAULT NULL," + "  `estado` varchar(2) DEFAULT NULL,"
					+ "  PRIMARY KEY (`id`)" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
			statement.executeUpdate(createTable_3);

			String createTable_4 = "CREATE TABLE `tb_produtos` (" + "  `id` int NOT NULL AUTO_INCREMENT,"
					+ "  `descricao` varchar(100) DEFAULT NULL," + "  `preco_de_venda` decimal(10,2) DEFAULT NULL,"
					+ "  `preco_de_compra` decimal(10,2) DEFAULT NULL," + "  `qtd_estoque` int DEFAULT NULL,"
					+ "  `for_id` int DEFAULT NULL," + "  PRIMARY KEY (`id`)," + "  KEY `for_id` (`for_id`),"
					+ "  CONSTRAINT `tb_produtos_ibfk_1` FOREIGN KEY (`for_id`) REFERENCES `tb_fornecedores` (`id`)"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
			statement.executeUpdate(createTable_4);

			String createTable_5 = "CREATE TABLE `tb_vendas` (" + "  `id` int NOT NULL AUTO_INCREMENT,"
					+ "  `cliente_id` int DEFAULT NULL," + "  `data_venda` datetime DEFAULT NULL,"
					+ "  `total_venda` decimal(10,2) DEFAULT NULL," + "  `observacoes` text," + "  PRIMARY KEY (`id`),"
					+ "  KEY `cliente_id` (`cliente_id`),"
					+ "  CONSTRAINT `tb_vendas_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `tb_clientes` (`id`)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
			statement.executeUpdate(createTable_5);

			String createTable_6 = "CREATE TABLE `tb_itensvendas` (" + "  `id` int NOT NULL AUTO_INCREMENT,"
					+ "  `venda_id` int DEFAULT NULL," + "  `produto_id` int DEFAULT NULL,"
					+ "  `qtd` int DEFAULT NULL," + "  `subtotal` decimal(10,2) DEFAULT NULL," + "  PRIMARY KEY (`id`),"
					+ "  KEY `venda_id` (`venda_id`)," + "  KEY `produto_id` (`produto_id`),"
					+ "  CONSTRAINT `tb_itensvendas_ibfk_1` FOREIGN KEY (`venda_id`) REFERENCES `tb_vendas` (`id`),"
					+ "  CONSTRAINT `tb_itensvendas_ibfk_2` FOREIGN KEY (`produto_id`) REFERENCES `tb_produtos` (`id`)"
					+ ") ";
			statement.executeUpdate(createTable_6);

			// System.out.println("Tabelas Criadas com sucesso!");

			// Fechar conexão
			statement.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> ListarBases() {
		List<String> lista = new ArrayList<>();
		try {
			String sql = "SELECT schema_name FROM information_schema.schemata where schema_name not in ('mysql','information_schema','performance_schema')";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String schemaName = rs.getString("schema_name");
		
				lista.add(schemaName);
			}

			 // Verifique o tamanho da lista
		} catch (Exception e) {
			e.printStackTrace(); // Imprima a pilha de erros para depuração
		}
		return lista;
	}

}
