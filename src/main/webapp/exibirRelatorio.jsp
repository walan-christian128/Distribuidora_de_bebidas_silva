<%@ page import="net.sf.jasperreports.engine.export.HtmlExporter, net.sf.jasperreports.engine.JasperPrint, java.io.StringWriter,net.sf.jasperreports.export.SimpleHtmlExporterOutput,net.sf.jasperreports.export.SimpleExporterInput" %>
<%
    // Obtém o relatório da sessão
    JasperPrint relatorio = (JasperPrint) session.getAttribute("relatorioVenda");
    
    if (relatorio != null) {
        // Exportar o relatório para HTML
        HtmlExporter exporter = new HtmlExporter();
        StringWriter writer = new StringWriter();
        exporter.setExporterInput(new SimpleExporterInput(relatorio));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(writer));
        exporter.exportReport();

        // Exibe o relatório gerado
        out.println(writer.toString());
    } else {
        out.println("Relatório não encontrado.");
    }
%>