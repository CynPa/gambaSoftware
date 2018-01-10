/*  1:   */ package com.asinfo.as2.datosbase.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*  5:   */ import com.asinfo.as2.entities.ListaDescuentos;
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
/* 21:   */ public class ReporteListaDescuentosBean
/* 22:   */   extends AbstractBaseReportBean
/* 23:   */ {
/* 24:   */   private static final long serialVersionUID = 1L;
/* 25:   */   @EJB
/* 26:   */   private ServicioListaDescuentos servicioListaDescuentos;
/* 27:33 */   private final String COMPILE_FILE_NAME = "reporteListaDescuentos";
/* 28:   */   private ListaDescuentos listaDescuentos;
/* 29:   */   
/* 30:   */   protected JRDataSource getJRDataSource()
/* 31:   */   {
/* 32:39 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 33:40 */     JRDataSource ds = null;
/* 34:   */     try
/* 35:   */     {
/* 36:42 */       listaDatosReporte = this.servicioListaDescuentos.getReportelistaDescuentos(this.listaDescuentos.getIdListaDescuentos());
/* 37:   */       
/* 38:44 */       String[] fields = { "f_porcentajeDescuentoMaximo", "f_producto", "f_codigoListaDescuento", "f_nombreListaDescuento", "f_descripcionListaDescuento", "f_codigoProducto" };
/* 39:   */       
/* 40:   */ 
/* 41:47 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 42:   */     }
/* 43:   */     catch (Exception e)
/* 44:   */     {
/* 45:49 */       e.printStackTrace();
/* 46:   */     }
/* 47:51 */     return ds;
/* 48:   */   }
/* 49:   */   
/* 50:   */   protected Map<String, Object> getReportParameters()
/* 51:   */   {
/* 52:56 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 53:57 */     reportParameters.put("ReportTitle", "Lista de Descuentos");
/* 54:58 */     return reportParameters;
/* 55:   */   }
/* 56:   */   
/* 57:   */   protected String getCompileFileName()
/* 58:   */   {
/* 59:63 */     return "reporteListaDescuentos";
/* 60:   */   }
/* 61:   */   
/* 62:   */   public String execute()
/* 63:   */   {
/* 64:   */     try
/* 65:   */     {
/* 66:70 */       super.prepareReport();
/* 67:   */     }
/* 68:   */     catch (JRException e)
/* 69:   */     {
/* 70:73 */       LOG.info("Error JRException");
/* 71:74 */       e.printStackTrace();
/* 72:75 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 73:   */     }
/* 74:   */     catch (IOException e)
/* 75:   */     {
/* 76:77 */       LOG.info("Error IOException");
/* 77:78 */       e.printStackTrace();
/* 78:79 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 79:   */     }
/* 80:82 */     return "";
/* 81:   */   }
/* 82:   */   
/* 83:   */   public ListaDescuentos getListaDescuentos()
/* 84:   */   {
/* 85:86 */     return this.listaDescuentos;
/* 86:   */   }
/* 87:   */   
/* 88:   */   public void setListaDescuentos(ListaDescuentos listaDescuentos)
/* 89:   */   {
/* 90:90 */     this.listaDescuentos = listaDescuentos;
/* 91:   */   }
/* 92:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.ReporteListaDescuentosBean
 * JD-Core Version:    0.7.0.1
 */