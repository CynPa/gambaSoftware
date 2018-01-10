/*  1:   */ package com.asinfo.as2.inventario.procesos.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.entities.MovimientoInventario;
/*  5:   */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  6:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  7:   */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  8:   */ import java.io.IOException;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.Map;
/* 12:   */ import javax.ejb.EJB;
/* 13:   */ import javax.faces.bean.ManagedBean;
/* 14:   */ import javax.faces.bean.ViewScoped;
/* 15:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 16:   */ import net.sf.jasperreports.engine.JRException;
/* 17:   */ import org.apache.log4j.Logger;
/* 18:   */ 
/* 19:   */ @ManagedBean
/* 20:   */ @ViewScoped
/* 21:   */ public class ReporteAprobarAjusteInventarioBean
/* 22:   */   extends AbstractBaseReportBean
/* 23:   */ {
/* 24:   */   private static final long serialVersionUID = 770222109067650882L;
/* 25:   */   @EJB
/* 26:   */   private ServicioMovimientoInventario servicioMovimientoInventario;
/* 27:36 */   private final String COMPILE_FILE_NAME = "reporteAprobarAjusteInventario";
/* 28:   */   private MovimientoInventario ajusteInventario;
/* 29:   */   
/* 30:   */   protected JRDataSource getJRDataSource()
/* 31:   */   {
/* 32:43 */     List listaDatosReporte = new ArrayList();
/* 33:44 */     JRDataSource ds = null;
/* 34:   */     try
/* 35:   */     {
/* 36:46 */       listaDatosReporte = this.servicioMovimientoInventario.getReporteAprobarAjusteInventario(this.ajusteInventario.getId());
/* 37:   */       
/* 38:48 */       String[] fields = { "f_documento", "f_numero", "f_fecha", "f_motivo", "f_nota", "f_saldoInicial", "f_codigoProducto", "f_nombreProducto", "f_unidadDetalle", "f_bodegaDetalle", "f_notaDetalle", "f_cantidadDetalle", "f_costoDetalle" };
/* 39:   */       
/* 40:50 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 41:   */     }
/* 42:   */     catch (Exception e)
/* 43:   */     {
/* 44:52 */       e.printStackTrace();
/* 45:   */     }
/* 46:54 */     return ds;
/* 47:   */   }
/* 48:   */   
/* 49:   */   protected String getCompileFileName()
/* 50:   */   {
/* 51:59 */     return "reporteAprobarAjusteInventario";
/* 52:   */   }
/* 53:   */   
/* 54:   */   protected Map<String, Object> getReportParameters()
/* 55:   */   {
/* 56:64 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 57:65 */     reportParameters.put("ReportTitle", "Ajuste Inventario");
/* 58:66 */     return reportParameters;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String execute()
/* 62:   */   {
/* 63:   */     try
/* 64:   */     {
/* 65:75 */       super.prepareReport();
/* 66:   */     }
/* 67:   */     catch (JRException e)
/* 68:   */     {
/* 69:78 */       LOG.info("Error JRException");
/* 70:79 */       e.printStackTrace();
/* 71:80 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 72:   */     }
/* 73:   */     catch (IOException e)
/* 74:   */     {
/* 75:82 */       LOG.info("Error IOException");
/* 76:83 */       e.printStackTrace();
/* 77:84 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 78:   */     }
/* 79:87 */     return "";
/* 80:   */   }
/* 81:   */   
/* 82:   */   public MovimientoInventario getAjusteInventario()
/* 83:   */   {
/* 84:91 */     return this.ajusteInventario;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public void setAjusteInventario(MovimientoInventario ajusteInventario)
/* 88:   */   {
/* 89:95 */     this.ajusteInventario = ajusteInventario;
/* 90:   */   }
/* 91:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.ReporteAprobarAjusteInventarioBean
 * JD-Core Version:    0.7.0.1
 */