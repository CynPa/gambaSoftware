/*  1:   */ package com.asinfo.as2.inventario.reportes.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.entities.TomaFisica;
/*  5:   */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*  6:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  7:   */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  8:   */ import java.io.IOException;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.Map;
/* 12:   */ import javax.ejb.EJB;
/* 13:   */ import javax.faces.bean.ManagedBean;
/* 14:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 15:   */ import net.sf.jasperreports.engine.JRException;
/* 16:   */ import org.apache.log4j.Logger;
/* 17:   */ 
/* 18:   */ @ManagedBean
/* 19:   */ public class ReporteTomaFisicaBean
/* 20:   */   extends AbstractBaseReportBean
/* 21:   */ {
/* 22:   */   private static final long serialVersionUID = 770222109067650882L;
/* 23:   */   @EJB
/* 24:   */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/* 25:   */   private TomaFisica tomaFisica;
/* 26:41 */   private final String COMPILE_FILE_NAME = "reporteTomaFisica";
/* 27:   */   
/* 28:   */   protected JRDataSource getJRDataSource()
/* 29:   */   {
/* 30:46 */     List listaDatosReporte = new ArrayList();
/* 31:47 */     JRDataSource ds = null;
/* 32:   */     try
/* 33:   */     {
/* 34:49 */       listaDatosReporte = this.servicioReporteMovimientoInventario.reporteTomaFisica(this.tomaFisica);
/* 35:   */       
/* 36:51 */       String[] fields = { "f_codigoProducto", "f_producto", "f_bodega", "f_cantidadSistema", "f_cantidadTomaFisica" };
/* 37:   */       
/* 38:53 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 39:   */     }
/* 40:   */     catch (Exception e)
/* 41:   */     {
/* 42:55 */       e.printStackTrace();
/* 43:   */     }
/* 44:57 */     return ds;
/* 45:   */   }
/* 46:   */   
/* 47:   */   protected String getCompileFileName()
/* 48:   */   {
/* 49:62 */     return "reporteTomaFisica";
/* 50:   */   }
/* 51:   */   
/* 52:   */   protected Map<String, Object> getReportParameters()
/* 53:   */   {
/* 54:68 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 55:69 */     reportParameters.put("ReportTitle", "Toma Fisica");
/* 56:70 */     return reportParameters;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public String execute()
/* 60:   */   {
/* 61:   */     try
/* 62:   */     {
/* 63:79 */       super.prepareReport();
/* 64:   */     }
/* 65:   */     catch (JRException e)
/* 66:   */     {
/* 67:82 */       LOG.info("Error JRException");
/* 68:83 */       e.printStackTrace();
/* 69:84 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 70:   */     }
/* 71:   */     catch (IOException e)
/* 72:   */     {
/* 73:86 */       LOG.info("Error IOException");
/* 74:87 */       e.printStackTrace();
/* 75:88 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 76:   */     }
/* 77:91 */     return "";
/* 78:   */   }
/* 79:   */   
/* 80:   */   public TomaFisica getTomaFisica()
/* 81:   */   {
/* 82:95 */     return this.tomaFisica;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void setTomaFisica(TomaFisica tomaFisica)
/* 86:   */   {
/* 87:99 */     this.tomaFisica = tomaFisica;
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteTomaFisicaBean
 * JD-Core Version:    0.7.0.1
 */