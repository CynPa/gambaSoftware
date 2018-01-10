/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.produccion.reportes.servicio.ServicioReporteFabricacion;
/*   5:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Date;
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
/*  22:    */ public class ReporteParteProduccionBean
/*  23:    */   extends AbstractBaseReportBean
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @EJB
/*  27:    */   private ServicioReporteFabricacion servicioReporteFabricacion;
/*  28: 58 */   private Date fechaDesde = new Date();
/*  29: 62 */   private Date fechaHasta = new Date();
/*  30:    */   
/*  31:    */   protected JRDataSource getJRDataSource()
/*  32:    */   {
/*  33: 77 */     List listaDatosReporte = new ArrayList();
/*  34: 78 */     JRDataSource ds = null;
/*  35:    */     try
/*  36:    */     {
/*  37: 81 */       listaDatosReporte = this.servicioReporteFabricacion.getReporteParteProduccion(getFechaDesde(), getFechaHasta());
/*  38:    */       
/*  39: 83 */       String[] fields = { "f_idOrdenFabricacion", "f_numero", "f_nombreProducto", "f_fechaLanzamiento", "f_fechaCierre", "f_cantidadPlanificada", "f_cantidadProducida", "f_fechaProduccion", "f_marcaProducto", "f_presentacionProducto", "f_cantidadPresentacion", "f_fechaEmpaque", "f_tiempoHoras" };
/*  40:    */       
/*  41:    */ 
/*  42: 86 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  43:    */     }
/*  44:    */     catch (Exception e)
/*  45:    */     {
/*  46: 89 */       e.printStackTrace();
/*  47:    */     }
/*  48: 91 */     return ds;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected String getCompileFileName()
/*  52:    */   {
/*  53:102 */     return "reporteParteProduccion";
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected Map<String, Object> getReportParameters()
/*  57:    */   {
/*  58:115 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  59:116 */     reportParameters.put("ReportTitle", "Parte Produccion");
/*  60:117 */     reportParameters.put("fechaDesde", getFechaDesde());
/*  61:118 */     reportParameters.put("fechaHasta", getFechaHasta());
/*  62:119 */     return reportParameters;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String execute()
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:129 */       super.prepareReport();
/*  70:    */     }
/*  71:    */     catch (JRException e)
/*  72:    */     {
/*  73:131 */       LOG.info("Error JRException");
/*  74:132 */       e.printStackTrace();
/*  75:133 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  76:    */     }
/*  77:    */     catch (IOException e)
/*  78:    */     {
/*  79:135 */       LOG.info("Error IOException");
/*  80:136 */       e.printStackTrace();
/*  81:137 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  82:    */     }
/*  83:139 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void validaDatos()
/*  87:    */   {
/*  88:144 */     if (this.fechaDesde == null) {
/*  89:145 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/*  90:    */     }
/*  91:147 */     if (this.fechaHasta == null) {
/*  92:148 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Date getFechaDesde()
/*  97:    */   {
/*  98:158 */     return this.fechaDesde;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setFechaDesde(Date fechaDesde)
/* 102:    */   {
/* 103:168 */     this.fechaDesde = fechaDesde;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Date getFechaHasta()
/* 107:    */   {
/* 108:177 */     return this.fechaHasta;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setFechaHasta(Date fechaHasta)
/* 112:    */   {
/* 113:187 */     this.fechaHasta = fechaHasta;
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteParteProduccionBean
 * JD-Core Version:    0.7.0.1
 */