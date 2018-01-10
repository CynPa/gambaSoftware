/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.RecepcionDevolucionTransportista;
/*   5:    */ import com.asinfo.as2.entities.Transportista;
/*   6:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRecepcionDevolucionTransportista;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  17:    */ import net.sf.jasperreports.engine.JRException;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ 
/*  20:    */ @ManagedBean
/*  21:    */ @ViewScoped
/*  22:    */ public class ReporteRecepcionDevolucionTransportistaBean
/*  23:    */   extends AbstractBaseReportBean
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 4992625350815288045L;
/*  26:    */   @EJB
/*  27:    */   private transient ServicioRecepcionDevolucionTransportista servicioRecepcionDevolucionTransportista;
/*  28:    */   private RecepcionDevolucionTransportista recepcionDevolucionTransportista;
/*  29:    */   
/*  30:    */   protected JRDataSource getJRDataSource()
/*  31:    */   {
/*  32: 49 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  33: 50 */     JRDataSource ds = null;
/*  34:    */     try
/*  35:    */     {
/*  36: 53 */       listaDatosReporte = this.servicioRecepcionDevolucionTransportista.getReporteRecepcionDevolucionTransportista(this.recepcionDevolucionTransportista
/*  37: 54 */         .getFecha(), this.recepcionDevolucionTransportista.getTransportista().getId(), false);
/*  38: 55 */       String[] fields = { "f_fecha", "f_nombreTransportista", "f_codigoProducto", "f_nombreProducto", "f_cantidad", "f_cantidadRecibida", "f_cliente", "f_tipoRecepcion" };
/*  39:    */       
/*  40: 57 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  41:    */     }
/*  42:    */     catch (Exception e)
/*  43:    */     {
/*  44: 59 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  45:    */     }
/*  46: 62 */     return ds;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected String getCompileFileName()
/*  50:    */   {
/*  51: 67 */     return "reporteRecepcionDevolucionTransportista";
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String execute()
/*  55:    */   {
/*  56:    */     try
/*  57:    */     {
/*  58: 73 */       super.prepareReport();
/*  59:    */     }
/*  60:    */     catch (JRException e)
/*  61:    */     {
/*  62: 75 */       LOG.info("Error JRException");
/*  63: 76 */       e.printStackTrace();
/*  64: 77 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  65:    */     }
/*  66:    */     catch (IOException e)
/*  67:    */     {
/*  68: 79 */       LOG.info("Error IOException");
/*  69: 80 */       e.printStackTrace();
/*  70: 81 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  71:    */     }
/*  72: 84 */     return null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected Map<String, Object> getReportParameters()
/*  76:    */   {
/*  77: 89 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  78: 90 */     reportParameters.put("ReportTitle", "RECEPCION DE DEVOLUCIONES");
/*  79: 91 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/*  80: 92 */     String[] fields = { "f_fecha", "f_nombreTransportista", "f_codigoProducto", "f_nombreProducto", "f_cantidad", "f_cantidadRecibida", "f_cliente", "f_tipoRecepcion" };
/*  81:    */     
/*  82:    */ 
/*  83: 95 */     List<Object[]> listaDatosReporte = this.servicioRecepcionDevolucionTransportista.getReporteRecepcionDevolucionTransportista(this.recepcionDevolucionTransportista
/*  84: 96 */       .getFecha(), this.recepcionDevolucionTransportista.getTransportista().getId(), false);
/*  85: 97 */     List<Object[]> listaDatosReporteTransportista = this.servicioRecepcionDevolucionTransportista.getReporteRecepcionDevolucionTransportista(this.recepcionDevolucionTransportista
/*  86: 98 */       .getFecha(), this.recepcionDevolucionTransportista.getTransportista().getId(), true);
/*  87: 99 */     JRDataSource ds1 = new QueryResultDataSource(listaDatosReporte, fields);
/*  88:    */     
/*  89:101 */     reportParameters.put("listaDevolucion", ds1);
/*  90:102 */     if (listaDatosReporteTransportista.size() > 0)
/*  91:    */     {
/*  92:104 */       JRDataSource ds2 = new QueryResultDataSource(listaDatosReporteTransportista, fields);
/*  93:    */       
/*  94:106 */       reportParameters.put("listaDevolucionTransportista", ds2);
/*  95:    */     }
/*  96:108 */     return reportParameters;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public RecepcionDevolucionTransportista getRecepcionDevolucionTransportista()
/* 100:    */   {
/* 101:112 */     return this.recepcionDevolucionTransportista;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setRecepcionDevolucionTransportista(RecepcionDevolucionTransportista recepcionDevolucionTransportista)
/* 105:    */   {
/* 106:116 */     this.recepcionDevolucionTransportista = recepcionDevolucionTransportista;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteRecepcionDevolucionTransportistaBean
 * JD-Core Version:    0.7.0.1
 */