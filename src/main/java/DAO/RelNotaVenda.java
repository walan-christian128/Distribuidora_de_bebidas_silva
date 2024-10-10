package DAO;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import Conexao.ConnectionFactory;

public class RelNotaVenda {
    private static final Logger LOGGER = Logger.getLogger(RelNotaVenda.class.getName());
    private Connection con;

    public RelNotaVenda(String empresa) throws SQLException, ClassNotFoundException {
        // Inicialize sua conexão aqui
        this.con = new ConnectionFactory().getConnection(empresa);
    }

    public JasperPrint gerarRelatorio(String layoutPath, int vendaID) throws JRException, SQLException, ClassNotFoundException, IOException {
        // Carrega o design do relatório a partir do ClassLoader
        InputStream inputStream = getClass().getResourceAsStream("/" + layoutPath);

        if (inputStream == null) {
            LOGGER.log(Level.SEVERE, "Arquivo JRXML não encontrado no classpath: " + layoutPath);
            throw new FileNotFoundException("Arquivo JRXML não encontrado no classpath: " + layoutPath);
        } else {
            LOGGER.log(Level.INFO, "Arquivo JRXML encontrado no classpath: " + layoutPath);
        }

        JasperDesign desenho = JRXmlLoader.load(inputStream);

        // Compila o relatório
        JasperReport relatorio = JasperCompileManager.compileReport(desenho);
        LOGGER.log(Level.INFO, "Relatório compilado com sucesso.");

        // Parâmetros do relatório
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID_VENDA", vendaID);

        // Preenche o relatório
        JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, con);
        LOGGER.log(Level.INFO, "Relatório preenchido com sucesso para vendaID: " + vendaID);

        // Fecha recursos
        inputStream.close();
        con.close();

        return impressao;
    }
}
