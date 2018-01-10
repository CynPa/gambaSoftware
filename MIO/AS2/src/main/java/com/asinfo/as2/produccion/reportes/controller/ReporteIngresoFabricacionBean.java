/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   5:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.ViewScoped;
/*  15:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  16:    */ import net.sf.jasperreports.engine.JRException;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @ViewScoped
/*  21:    */ public class ReporteIngresoFabricacionBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 770222109067650882L;
/*  25:    */   @EJB
/*  26:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  27:    */   private MovimientoInventario ajusteInventario;
/*  28:    */   private MovimientoInventario ajusteInventarioIndividual;
/*  29: 45 */   private final String COMPILE_FILE_NAME = "reporteIngresoFabricacion";
/*  30:    */   
/*  31:    */   protected JRDataSource getJRDataSource()
/*  32:    */   {
/*  33: 50 */     List listaDatosReporte = new ArrayList();
/*  34: 51 */     JRDataSource ds = null;
/*  35:    */     try
/*  36:    */     {
/*  37: 53 */       if (getAjusteInventarioIndividual() != null) {
/*  38: 54 */         listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteIngresoFabricacion(getAjusteInventarioIndividual(), true);
/*  39:    */       } else {
/*  40: 57 */         listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteIngresoFabricacion(getAjusteInventario(), false);
/*  41:    */       }
/*  42: 59 */       String[] fields = { "f_numeroOF", "f_codigoProducto", "f_nombreProducto", "f_fechaDetalle", "f_cantidadOrigen", "f_unidadConversion", "f_cantidad", "f_descripcionDetalle", "f_loteDetalle", "f_unidadInformativa", "f_cantidadInformativa", "f_numeroIF" };
/*  43:    */       
/*  44:    */ 
/*  45: 62 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  46:    */     }
/*  47:    */     catch (Exception e)
/*  48:    */     {
/*  49: 65 */       e.printStackTrace();
/*  50:    */     }
/*  51: 67 */     return ds;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected String getCompileFileName()
/*  55:    */   {
/*  56: 72 */     return "reporteIngresoFabricacion";
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Map<String, Object> getReportParameters()
/*  60:    */   {
/*  61: 78 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  62: 79 */     reportParameters.put("ReportTitle", "Ingreso Fabricacion");
/*  63: 80 */     reportParameters.put("p_numero", 
/*  64: 81 */       getAjusteInventarioIndividual() != null ? getAjusteInventarioIndividual().getNumero() : getAjusteInventario().getNumero());
/*  65: 82 */     reportParameters.put("p_fecha", 
/*  66: 83 */       getAjusteInventarioIndividual() != null ? getAjusteInventarioIndividual().getFecha() : getAjusteInventario().getFecha());
/*  67: 84 */     return reportParameters;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String execute()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74: 92 */       super.prepareReport();
/*  75: 93 */       limpiar();
/*  76:    */     }
/*  77:    */     catch (JRException e)
/*  78:    */     {
/*  79: 95 */       LOG.info("Error JRException");
/*  80: 96 */       e.printStackTrace();
/*  81: 97 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  82:    */     }
/*  83:    */     catch (IOException e)
/*  84:    */     {
/*  85: 99 */       LOG.info("Error IOException");
/*  86:100 */       e.printStackTrace();
/*  87:101 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  88:    */     }
/*  89:104 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public MovimientoInventario getAjusteInventario()
/*  93:    */   {
/*  94:108 */     if (this.ajusteInventario == null) {
/*  95:109 */       this.ajusteInventario = new MovimientoInventario();
/*  96:    */     }
/*  97:111 */     return this.ajusteInventario;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setAjusteInventario(MovimientoInventario ajusteInventario)
/* 101:    */   {
/* 102:115 */     this.ajusteInventario = ajusteInventario;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public MovimientoInventario getAjusteInventarioIndividual()
/* 106:    */   {
/* 107:119 */     return this.ajusteInventarioIndividual;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setAjusteInventarioIndividual(MovimientoInventario ajusteInventarioIndividual)
/* 111:    */   {
/* 112:123 */     this.ajusteInventarioIndividual = ajusteInventarioIndividual;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void limpiar()
/* 116:    */   {
/* 117:127 */     this.ajusteInventario = null;
/* 118:128 */     this.ajusteInventarioIndividual = null;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteIngresoFabricacionBean
 * JD-Core Version:    0.7.0.1
 */