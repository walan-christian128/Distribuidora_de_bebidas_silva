package Controller;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperExportManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import DAO.RelNotaVenda;
import DAO.VendasDAO;

@SuppressWarnings("serial")
@WebServlet("/exibirRelatorio")
public class relatorioServer extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(relatorioServer.class.getName());

    @SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
		String empresa = (String) session.getAttribute("empresa");
		VendasDAO dao = new VendasDAO(empresa);
		Integer  vendaID = dao.retornaUltimaVenda();
        if (vendaID == null) {
            response.getWriter().write("ID da venda não fornecido.");
            LOGGER.log(Level.WARNING, "ID da venda não fornecido.");
            return;
        }

        
        try {
        	
        	
            vendaID = dao.retornaUltimaVenda();
        } catch (NumberFormatException e) {
            response.getWriter().write("ID da venda inválido.");
            LOGGER.log(Level.WARNING, "ID da venda inválido: " + vendaID, e);
            return;
            
        }

        try {
            // Gerar o relatório
            RelNotaVenda relatorio = new RelNotaVenda(empresa); // Substitua "sua_empresa" conforme necessário
            JasperPrint jasperPrint = relatorio.gerarRelatorio("RelatoriosJasper/teree.jrxml", vendaID);
            LOGGER.log(Level.INFO, "Relatório gerado para vendaID: " + vendaID);

            // Exportar para PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=relatorio_venda_" + vendaID + ".pdf");

            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

            outStream.flush();
            outStream.close();
            LOGGER.log(Level.INFO, "Relatório exportado com sucesso para vendaID: " + vendaID);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Erro ao gerar relatório para vendaID: " + vendaID, e);
            response.getWriter().write("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
