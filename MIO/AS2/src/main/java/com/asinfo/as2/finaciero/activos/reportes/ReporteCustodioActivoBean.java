/*   1:    */ package com.asinfo.as2.finaciero.activos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CustodioActivoFijo;
/*   5:    */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteCustodioActivoFijo;
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
/*  21:    */ public class ReporteCustodioActivoBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 3330248466399129901L;
/*  25:    */   @EJB
/*  26:    */   private ServicioReporteCustodioActivoFijo servicioReporteCustodioActivoFijo;
/*  27:    */   private CustodioActivoFijo custodioActivoFijo;
/*  28:    */   
/*  29:    */   protected JRDataSource getJRDataSource()
/*  30:    */   {
/*  31: 58 */     List listaDatosReporte = new ArrayList();
/*  32: 59 */     JRDataSource ds = null;
/*  33:    */     try
/*  34:    */     {
/*  35: 62 */       listaDatosReporte = this.servicioReporteCustodioActivoFijo.getReporteCustodioActivo(this.custodioActivoFijo.getIdCustodioActivoFijo());
/*  36:    */       
/*  37: 64 */       String[] fields = { "f_fechaInicioCAF", "f_fechaFinCAF", "f_descripcionCustodioCAF", "f_codigoActivoFijoCAF", "f_nombreActivoFijoCAF", "f_nombreEmpleadoCAF", "f_identificacionEmpleadoCAF", "f_codigoUbicacionCAF", "f_nombreUbicacionCAF", "f_descripcionUbicacionCAF", "f_codigoDepartamentoCAF", "f_nombreDepartamentoCAF", "f_descripcionDepartamentoCAF", "f_fechaInicioCAFA", "f_fechaFinCAFA", "f_descripcionCustodioCAFA", "f_codigoActivoFijoCAFA", "f_nombreActivoFijoCAFA", "f_nombreEmpleadoCAFA", "f_identificacionEmpleadoCAFA", "f_codigoUbicacionCAFA", "f_nombreUbicacionCAFA", "f_descripcionUbicacionCAFA", "f_codigoDepartamentoCAFA", "f_nombreDepartamentoCAFA", "f_descripcionDepartamentoCAFA" };
/*  38:    */       
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44: 71 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  45:    */     }
/*  46:    */     catch (Exception e)
/*  47:    */     {
/*  48: 73 */       LOG.info("Error " + e);
/*  49: 74 */       e.printStackTrace();
/*  50:    */     }
/*  51: 76 */     return ds;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected String getCompileFileName()
/*  55:    */   {
/*  56: 86 */     return "reporteCustodioActivo";
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Map<String, Object> getReportParameters()
/*  60:    */   {
/*  61: 97 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  62: 98 */     reportParameters.put("ReportTitle", "Custodio Activo Fijo");
/*  63: 99 */     return reportParameters;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String execute()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70:104 */       super.prepareReport();
/*  71:    */     }
/*  72:    */     catch (JRException e)
/*  73:    */     {
/*  74:107 */       LOG.info("Error JRException");
/*  75:108 */       e.printStackTrace();
/*  76:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  77:    */     }
/*  78:    */     catch (IOException e)
/*  79:    */     {
/*  80:111 */       LOG.info("Error IOException");
/*  81:112 */       e.printStackTrace();
/*  82:113 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  83:    */     }
/*  84:116 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public CustodioActivoFijo getCustodioActivoFijo()
/*  88:    */   {
/*  89:125 */     return this.custodioActivoFijo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setCustodioActivoFijo(CustodioActivoFijo custodioActivoFijo)
/*  93:    */   {
/*  94:135 */     this.custodioActivoFijo = custodioActivoFijo;
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.reportes.ReporteCustodioActivoBean
 * JD-Core Version:    0.7.0.1
 */