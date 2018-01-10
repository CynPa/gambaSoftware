package com.gambalit.test;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class GenerarReporte1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JasperPrint jasperPrintWindow;
		try {
			jasperPrintWindow = JasperFillManager.fillReport(
					"C:\\Users\\Gambalit\\JaspersoftWorkspace\\MyReports\\Invoice_Table_Based.jasper", null,
					Conexion.conectar());
			JasperViewer jasperViewer = new JasperViewer(jasperPrintWindow);
			jasperViewer.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void generarReporte()
	{
	// se muestra en una ventana aparte para su descarga
			JasperPrint jasperPrintWindow;
			try {
				jasperPrintWindow = JasperFillManager.fillReport(
						"C:\\Users\\Ecodeup\\JaspersoftWorkspace\\Reportes Escuela\\ReporteAlumnos.jasper", null,
						Conexion.conectar());
				JasperViewer jasperViewer = new JasperViewer(jasperPrintWindow);
				jasperViewer.setVisible(true);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}
