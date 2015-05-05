package br.upis.sel.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

@SuppressWarnings("deprecation")
public class RelatorioUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void gerarRelatorio(FacesContext context, String relatorio,
			List lista, Map parametros) throws IOException, JRException {

		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();
		// InputStream caminho =
		// context.getExternalContext().getResourceAsStream(
		// relatorio);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition",
				"inline;filename=relatorio.pdf");
		// response.addHeader("Content-disposition",
		// "attachment; filename=relatorio.pdf");
		try {
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(
					lista);

			// JasperPrint jasperPrint = JasperFillManager.fillReport(relatorio,
			// parametros, datasource);
			JasperRunManager.runReportToPdfStream(new FileInputStream(new File(
					relatorio)), servletOutputStream, parametros, datasource);
			// JasperExportManager.exportReportToPdfStream(jasperPrint,
			// servletOutputStream);

			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void gerarRelatorio(FacesContext context, String relatorio,
			Connection con, Map parametros) throws IOException, JRException {

		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();
		// InputStream caminho =
		// context.getExternalContext().getResourceAsStream(
		// relatorio);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition",
				"inline;filename=relatorio.pdf");
		// response.addHeader("Content-disposition",
		// "attachment; filename=relatorio.pdf");
		try {
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			// JasperRunManager.runReportToPdfStream(caminho,
			// servletOutputStream,
			// parametros, con);
			JasperPrint jasperPrint = JasperFillManager.fillReport(relatorio,
					parametros, con);
			JasperExportManager.exportReportToPdfStream(jasperPrint,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public static void gerarMultiplosRelatorios(FacesContext context,
			List<JasperPrint> jasperPrints) {
		try {
			JRPdfExporter exporter = new JRPdfExporter();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			ByteArrayOutputStream retorno = new ByteArrayOutputStream();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
					jasperPrints);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, retorno);
			exporter.setParameter(
					JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
					Boolean.TRUE);
			exporter.exportReport();

			byte[] retornoArray = retorno.toByteArray();

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"inline;filename=relatorio.pdf");
//			response.addHeader("Content-disposition",
//					 "attachment; filename=relatorio.pdf");
			response.setContentLength(retornoArray.length);

			OutputStream output = response.getOutputStream();
			output.write(retornoArray);
			output.flush();
			output.close();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.responseComplete();

	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(
					"jdbc:jtds:sqlserver://192.168.30.3:1433/vendas",
					"mauricio.garcia", "@#4548541fjalkdi");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// public void init() throws JRException {
	// JRBeanCollectionDataSource beanCollectionDataSource = new
	// JRBeanCollectionDataSource(
	// listOfUser);
	// String reportPath = FacesContext.getCurrentInstance()
	// .getExternalContext().getRealPath("/reports/report.jasper");
	// jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),
	// beanCollectionDataSource);
	// }
}
