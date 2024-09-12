package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexao.ConectionDataBases;

public class dataBsesDAO {
    private Connection con;

    public dataBsesDAO() throws ClassNotFoundException {
        this.con = new ConectionDataBases().getConectionDataBases();
       
    }

    public List<String> listDatabases() {
        List<String> databases = new ArrayList<>();
        String sql = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME NOT IN ('mysql', 'information_schema', 'performance_schema')";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                databases.add(rs.getString("SCHEMA_NAME"));
            }
         

        } catch (SQLException e) {
            e.printStackTrace(); // Melhor tratamento de exceção
        }
        return databases;
    }
}