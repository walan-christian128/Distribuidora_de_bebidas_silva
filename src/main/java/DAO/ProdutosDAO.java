package DAO;

import Conexao.ConnectionFactory;
import Model.Fornecedores;
import Model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walan
 */
public class ProdutosDAO {

	private Connection con;

	public ProdutosDAO() {
		this.con = new ConnectionFactory().getConnection();

	}

	// metodo cadastra produtos//
	public void cadastrar(Produtos obj) {
		try {

			String sql = "insert into tb_produtos(descricao,qtd_estoque,for_id,preco_de_compra,preco_de_venda)values(?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, obj.getDescricao());
			stmt.setInt(2, obj.getQtd_estoque());
			stmt.setInt(3, obj.getFornecedor().getId());
			stmt.setDouble(4, obj.getPreco_de_compra());
			stmt.setDouble(5, obj.getPreco_de_venda());

			stmt.execute();

			stmt.close();

		} catch (Exception erro) {

		}

	}

	public List<Produtos> listarProdutos() {
		try {

			// 1 passo criar lista de produtos//
			List<Produtos> lista = new ArrayList<>();

			String sql = "select p.id,p.descricao,p.qtd_estoque,p.preco_de_compra,p.preco_de_venda,f.nome,f.id from tb_produtos as p "
					+ "inner join tb_fornecedores as f on (p.for_id=f.id)";

			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Produtos obj = new Produtos();
				Fornecedores f = new Fornecedores();

				obj.setId(rs.getInt("p.id"));
				obj.setDescricao(rs.getString("p.descricao"));
				obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
				obj.setPreco_de_compra(rs.getDouble("p.preco_de_compra"));
				obj.setPreco_de_venda(rs.getDouble("p.preco_de_venda"));
				f.setId(rs.getInt("f.id"));
				f.setNome(rs.getString(("f.nome")));

				obj.setFornecedor(f);

				lista.add(obj);

			}

			return lista;

		} catch (SQLException e) {

			return null;

		}

	}

	public void alterarProdutos(Produtos obj) {
        try {
            String sql = "UPDATE tb_produtos set descricao=?,qtd_estoque=?,for_id=?,preco_de_compra=?,preco_de_venda=? where id=?";

            //Organização SQL e Conexão // 
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj.getDescricao());
            stmt.setInt(2, obj.getQtd_estoque());
            stmt.setInt(3, obj.getFornecedor().getId());
            stmt.setDouble(4, obj.getPreco_de_compra());
            stmt.setDouble(5, obj.getPreco_de_venda());

            stmt.setInt(6, obj.getId());

            //Execução//
            stmt.executeUpdate();
            stmt.close();
	    } catch (SQLException erro) {
	        erro.printStackTrace(); // Isso ajudará a identificar o erro específico.
	    }
	}


	public void excluir(Produtos obj) {
		try {
			String sql = "delete from tb_produtos where id=?";
			// Organização SQL//

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, obj.getId());

			stmt.execute();
			stmt.close();

		} catch (Exception e) {

		}

	}

	public Produtos consultarPorNome(String nome) {
		try {

			// 1 passo criar lista de produtos//
			String sql = "select p.id,p.descricao,p.qtd_estoque,p.preco_de_compra,p.preco_de_venda,f.nome,f.id from tb_produtos as p "
					+ "inner join tb_fornecedores as f on (p.for_id=f.id) where p.descricao=?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();
			Produtos obj = new Produtos();
			Fornecedores f = new Fornecedores();

			if (rs.next()) {

				obj.setId(rs.getInt("p.id"));
				obj.setDescricao(rs.getString("p.descricao"));
				obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
				obj.setPreco_de_compra(rs.getDouble("p.preco_de_compra"));
				obj.setPreco_de_venda(rs.getDouble("p.preco_de_venda"));

				f.setNome(rs.getString(("f.nome")));

				obj.setFornecedor(f);

			}

			return obj;

		} catch (SQLException e) {

			return null;

		}

	}

	public Produtos consultarPorCodigo(int id) {
		try {

			// 1 passo criar lista de produtos//
			String sql = "select*from tb_produtos where id=?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			Produtos obj = new Produtos();
			Fornecedores f = new Fornecedores();

			if (rs.next()) {

				obj.setId(rs.getInt("id"));
				obj.setDescricao(rs.getString("descricao"));
				obj.setPreco_de_compra(rs.getDouble("preco_de_compra"));
				obj.setPreco_de_venda(rs.getDouble("preco_de_venda"));
				

				obj.setFornecedor(f);

			}

			return obj;

		} catch (SQLException e) {

			return null;

		}

	}
	public void consultarProduto(Produtos obj) {
		try {

			// 1 passo criar lista de produtos//
			String sql = "select p.id,p.descricao,p.qtd_estoque,p.preco_de_compra,p.preco_de_venda,f.nome,f.id from tb_produtos as p "
					+ "inner join tb_fornecedores as f on (p.for_id=f.id) where p.id=?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, obj.getId());
			ResultSet rs = stmt.executeQuery();
			
			Fornecedores f = new Fornecedores();

			if (rs.next()) {

				obj.setId(rs.getInt("p.id"));
				obj.setDescricao(rs.getString("p.descricao"));
				obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
				obj.setPreco_de_compra(rs.getDouble("p.preco_de_compra"));
				obj.setPreco_de_venda(rs.getDouble("p.preco_de_venda"));
                
				f.setNome(rs.getString(("f.nome")));
				f.setId(rs.getInt("f.id"));

				obj.setFornecedor(f);

			}

	

		} catch (SQLException e) {



		}
	}


	public List<Produtos> listarProdutosPorNome(String nome) {
		try {

			// 1 passo criar lista de produtos//
			List<Produtos> lista = new ArrayList<>();

			String sql = "select p.id,p.descricao,p.qtd_estoque,p.preco_de_compra,p.preco_de_venda,f.nome from tb_produtos as p "
					+ "inner join tb_fornecedores as f on (p.for_id=f.id)where p.descricao like ?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nome);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Produtos obj = new Produtos();
				Fornecedores f = new Fornecedores();

				obj.setId(rs.getInt("p.id"));
				obj.setDescricao(rs.getString("p.descricao"));
				obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
				obj.setPreco_de_compra(rs.getDouble("p.preco_de_compra"));
				obj.setPreco_de_venda(rs.getDouble("p.preco_de_venda"));

				f.setNome(rs.getString(("f.nome")));

				obj.setFornecedor(f);

				lista.add(obj);

			}

			return lista;

		} catch (SQLException e) {

			return null;

		}

	}

	// METODO DE DAR BAIXO NO ESTOQUE //
	public void baixarEstoque(int id, int qtd_nova) {

		try {
			String sql = "update tb_produtos set qtd_estoque=? where id=?";
			// 2 Passo - conectar o banco de dados e organizar o comando sql//

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setInt(1, qtd_nova);
			stmt.setInt(2, id);
			stmt.execute();
			stmt.close();

		} catch (Exception e) {
 
		}

	}

	// Metodo Retorna Estoque Atual de um produto//
	public int retornaEstoqueAtual(int id) {
		try {
			int qtd_estoque = 0;
			String sql = "select qtd_estoque from tb_produtos where id = ?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				qtd_estoque = (rs.getInt("qtd_estoque"));

			}

			return qtd_estoque;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void adicionarEstoque(int id, int qtd_nova) {

		try {
			String sql = "update tb_produtos set qtd_estoque=? where id=?";
			// 2 Passo - conectar o banco de dados e organizar o comando sql//

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setInt(1, qtd_nova);
			stmt.setInt(2, id);
			stmt.execute();
			stmt.close();

		} catch (Exception e) {

		}

	}

	public List<Fornecedores> nomeFonecedorProduto(int produtoId) {

		try {
			List<Fornecedores> lista = new ArrayList<>();
			String sql = "SELECT f.nome " + "FROM tb_fornecedores f "
		               + "INNER JOIN tb_produtos p ON p.for_id = f.id "
					   + "WHERE p.id = ?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, produtoId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Fornecedores f = new Fornecedores();
				 rs.getString("f.nome");
				 
				 lista.add(f);
			}
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}




}