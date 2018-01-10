/*   1:    */ package com.asinfo.as2.finaciero.activos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   5:    */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteActivoFijo;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.RequestScoped;
/*  15:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  16:    */ import net.sf.jasperreports.engine.JRException;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @RequestScoped
/*  21:    */ public class ReporteActivoFijoBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 8436856997948409225L;
/*  25:    */   @EJB
/*  26:    */   private ServicioReporteActivoFijo servicioReporteActivoFijo;
/*  27:    */   private ActivoFijo activoFijo;
/*  28: 51 */   private final String COMPILE_FILE_NAME = "reporteActivoFijo";
/*  29:    */   
/*  30:    */   protected JRDataSource getJRDataSource()
/*  31:    */   {
/*  32: 61 */     List listaDatosReporte = new ArrayList();
/*  33: 62 */     JRDataSource ds = null;
/*  34:    */     try
/*  35:    */     {
/*  36: 64 */       listaDatosReporte = this.servicioReporteActivoFijo.getReporteActivoFijo(getActivoFijo().getIdActivoFijo());
/*  37:    */       
/*  38: 66 */       String[] fields = { "f_codigoActivoFijo", "f_nombreActivoFijo", "f_codigoBarrasActivoFijo", "f_numeroParteActivoFijo", "f_numeroSerieActivoFijo", "f_fechaFacturaProveedor", "f_numeroFacturaProveedor", "f_valorActivo", "f_valorDepreciado", "f_valorCompraRelacionada", "f_valorAdicional", "f_fechaBaja", "f_descripcionActivoFijo", "f_codigoCategoriaActivoFijo", "f_nombreCategoriaActivoFijo", "f_codigoCuentaContableActivoFijo", "f_nombreCuentaContableActivoFijo", "f_codigoCuentaContableDepreciacion", "f_nombreCuentaContableDepreciacion", "f_codigoCuentaContableDepreciacionAcumulada", "f_nombreCuentaContableDepreciacionAcumulada", "f_codigoCuentaContableSuperavitPorRevalorizacion", "f_nombreCuentaContableSuperavitPorRevalorizacion", "f_codigoCuentaContableDeficitPorRevalorizacion", "f_nombreCuentaContableDeficitPorRevalorizacion", "f_codigoMotivoBaja", "f_nombreMotivoBaja", "f_codigoCuentaContableMotivoBajaActivo", "f_nombreCuentaContableMotivoBajaActivo", "f_codigoProducto", "f_codigoAlternoProducto", "f_codigoComercialProducto", "f_nombreProducto", "f_nombreComercialProducto" };
/*  39:    */       
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48: 76 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  49:    */     }
/*  50:    */     catch (Exception e)
/*  51:    */     {
/*  52: 78 */       e.printStackTrace();
/*  53:    */     }
/*  54: 80 */     return ds;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected String getCompileFileName()
/*  58:    */   {
/*  59: 92 */     return "reporteActivoFijo";
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected Map<String, Object> getReportParameters()
/*  63:    */   {
/*  64:103 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  65:104 */     reportParameters.put("ReportTitle", "Activo Fijo");
/*  66:105 */     return reportParameters;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String execute()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:114 */       super.prepareReport();
/*  74:    */     }
/*  75:    */     catch (JRException e)
/*  76:    */     {
/*  77:117 */       LOG.info("Error JRException");
/*  78:118 */       e.printStackTrace();
/*  79:119 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  80:    */     }
/*  81:    */     catch (IOException e)
/*  82:    */     {
/*  83:121 */       LOG.info("Error IOException");
/*  84:122 */       e.printStackTrace();
/*  85:123 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  86:    */     }
/*  87:126 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public ActivoFijo getActivoFijo()
/*  91:    */   {
/*  92:133 */     if (this.activoFijo == null) {
/*  93:134 */       this.activoFijo = new ActivoFijo();
/*  94:    */     }
/*  95:136 */     return this.activoFijo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setActivoFijo(ActivoFijo activoFijo)
/*  99:    */   {
/* 100:144 */     this.activoFijo = activoFijo;
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.reportes.ReporteActivoFijoBean
 * JD-Core Version:    0.7.0.1
 */