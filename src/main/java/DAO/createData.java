package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Conexao.ConnectionFactory;
import Model.Usuario;

public class createData {
    private Connection con;

    private static boolean isInitialized = false;

    public createData(String databaseName) {
        if (isInitialized) {
            return; // Não faz nada se já foi inicializado
        }

        try {
            // Conectar ao MySQL sem especificar um banco de dados para criar o banco
            Connection tempCon = new ConnectionFactory().getConnection("");
            if (tempCon == null) {
                System.err.println("Erro: Conexão temporária não foi estabelecida.");
                return;
            }

            // Verifica se o banco de dados já existe e cria se necessário
            createDatabase(tempCon, databaseName);

            // Fechar conexão temporária
            tempCon.close();

            // Conectar ao banco de dados recém-criado
            this.con = new ConnectionFactory().getConnection(databaseName);
            if (this.con == null) {
                System.err.println("Erro: Conexão com o novo banco de dados não foi estabelecida.");
                return;
            }

            // Criar tabelas
            createTables();

            isInitialized = true; // Marca como inicializado

        } catch (Exception e) {
            System.err.println("Erro ao tentar estabelecer conexão com o banco de dados.");
            e.printStackTrace();
        }
    }

    private void createDatabase(Connection tempCon, String databaseName) throws SQLException {
        Statement statement = tempCon.createStatement();
        try {
            // Verifica se o banco de dados já existe
            String checkDatabaseSQL = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + databaseName + "'";
            ResultSet rs = statement.executeQuery(checkDatabaseSQL);

            if (rs.next()) {
                System.out.println("Banco de dados " + databaseName + " já existe.");
            } else {
                // Criar banco de dados
                String createDatabaseSQL = "CREATE DATABASE " + databaseName;
                statement.executeUpdate(createDatabaseSQL);
                System.out.println("Banco de dados criado com sucesso!");
            }

            rs.close();
        } finally {
            statement.close();
        }
    }

    private void createTables() throws SQLException {
        Statement statement = con.createStatement();
        try {
            // Criação das tabelas
            String createTable_1 = "CREATE TABLE tb_clientes (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "nome varchar(100) DEFAULT NULL," +
                    "rg varchar(30) DEFAULT NULL," +
                    "cpf varchar(20) DEFAULT NULL," +
                    "email varchar(200) DEFAULT NULL," +
                    "telefone varchar(30) DEFAULT NULL," +
                    "celular varchar(30) DEFAULT NULL," +
                    "cep varchar(100) DEFAULT NULL," +
                    "endereco varchar(255) DEFAULT NULL," +
                    "numero int DEFAULT NULL," +
                    "complemento varchar(200) DEFAULT NULL," +
                    "cidade varchar(100) DEFAULT NULL," +
                    "estado varchar(2) DEFAULT NULL," +
                    "PRIMARY KEY (id)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createTable_1);

            String createTable_2 = "CREATE TABLE tb_fornecedores (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "nome varchar(100) DEFAULT NULL," +
                    "cnpj varchar(100) DEFAULT NULL," +
                    "email varchar(200) DEFAULT NULL," +
                    "telefone varchar(30) DEFAULT NULL," +
                    "celular varchar(30) DEFAULT NULL," +
                    "cep varchar(100) DEFAULT NULL," +
                    "endereco varchar(255) DEFAULT NULL," +
                    "numero int DEFAULT NULL," +
                    "complemento varchar(200) DEFAULT NULL," +
                    "bairro varchar(100) DEFAULT NULL," +
                    "cidade varchar(100) DEFAULT NULL," +
                    "estado varchar(2) DEFAULT NULL," +
                    "PRIMARY KEY (id)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createTable_2);

            String createTable_3 = "CREATE TABLE tb_funcionarios (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "nome varchar(100) DEFAULT NULL," +
                    "rg varchar(30) DEFAULT NULL," +
                    "cpf varchar(20) DEFAULT NULL," +
                    "email varchar(200) DEFAULT NULL," +
                    "senha varchar(10) DEFAULT NULL," +
                    "cargo varchar(100) DEFAULT NULL," +
                    "nivel_acesso varchar(50) DEFAULT NULL," +
                    "telefone varchar(30) DEFAULT NULL," +
                    "celular varchar(30) DEFAULT NULL," +
                    "cep varchar(100) DEFAULT NULL," +
                    "endereco varchar(255) DEFAULT NULL," +
                    "numero int DEFAULT NULL," +
                    "complemento varchar(200) DEFAULT NULL," +
                    "bairro varchar(100) DEFAULT NULL," +
                    "cidade varchar(100) DEFAULT NULL," +
                    "estado varchar(2) DEFAULT NULL," +
                    "PRIMARY KEY (id)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createTable_3);

            String createTable_4 = "CREATE TABLE tb_produtos (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "descricao varchar(100) DEFAULT NULL," +
                    "preco_de_venda decimal(10,2) DEFAULT NULL," +
                    "preco_de_compra decimal(10,2) DEFAULT NULL," +
                    "qtd_estoque int DEFAULT NULL," +
                    "for_id int DEFAULT NULL," +
                    "PRIMARY KEY (id)," +
                    "KEY for_id (for_id)," +
                    "CONSTRAINT tb_produtos_ibfk_1 FOREIGN KEY (for_id) REFERENCES tb_fornecedores (id)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createTable_4);

            String createTable_5 = "CREATE TABLE tb_vendas (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "cliente_id int DEFAULT NULL," +
                    "data_venda datetime DEFAULT NULL," +
                    "total_venda decimal(10,2) DEFAULT NULL," +
                    "observacoes text," +
                    "PRIMARY KEY (id)," +
                    "KEY cliente_id (cliente_id)," +
                    "CONSTRAINT tb_vendas_ibfk_1 FOREIGN KEY (cliente_id) REFERENCES tb_clientes (id)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createTable_5);

            String createTable_6 = "CREATE TABLE tb_itensvendas (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "venda_id int DEFAULT NULL," +
                    "produto_id int DEFAULT NULL," +
                    "qtd int DEFAULT NULL," +
                    "subtotal decimal(10,2) DEFAULT NULL," +
                    "PRIMARY KEY (id)," +
                    "KEY venda_id (venda_id)," +
                    "KEY produto_id (produto_id)," +
                    "CONSTRAINT tb_itensvendas_ibfk_1 FOREIGN KEY (venda_id) REFERENCES tb_vendas (id)," +
                    "CONSTRAINT tb_itensvendas_ibfk_2 FOREIGN KEY (produto_id) REFERENCES tb_produtos (id)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createTable_6);

            String createTable_7 = "CREATE TABLE tb_usuario (" +
                    "ID INT auto_increment PRIMARY KEY," +
                    "NOME VARCHAR(150)," +
                    "TELEFONE VARCHAR(150)," +
                    "EMAIL VARCHAR(150)," +
                    "SENHA VARCHAR(300)" +
                    ");";
            statement.executeUpdate(createTable_7);

            System.out.println("Tabelas criadas com sucesso!");
        } finally {
            statement.close();
        }
    }

    public void inserirUsuarioEmpresa(Usuario uso) throws SQLException {
        if (this.con == null) {
            System.err.println("Erro: Conexão com o banco de dados não foi estabelecida.");
            return;
        }

        String sql = "INSERT INTO tb_usuario (NOME, TELEFONE, EMAIL, SENHA) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, uso.getNome());
            stmt.setString(2, uso.getTelefone());
            stmt.setString(3, uso.getEmail());
            stmt.setString(4, uso.getSenha());

            stmt.execute();
            System.out.println("Usuário inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
