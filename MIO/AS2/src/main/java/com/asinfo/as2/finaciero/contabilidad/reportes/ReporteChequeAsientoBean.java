/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Asiento;
/*   5:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   6:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteChequeAsiento;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.RequestScoped;
/*  16:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  17:    */ import net.sf.jasperreports.engine.JRException;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ 
/*  20:    */ @ManagedBean
/*  21:    */ @RequestScoped
/*  22:    */ public class ReporteChequeAsientoBean
/*  23:    */   extends AbstractBaseReportBean
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @EJB
/*  27:    */   private ServicioReporteChequeAsiento servicioReporteChequeAsiento;
/*  28:    */   @EJB
/*  29:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  30:    */   private Asiento asiento;
/*  31: 37 */   private String nombreCiudadCheque = "";
/*  32: 38 */   private String COMPILE_FILE_NAME = "reporteChequeAsiento";
/*  33:    */   
/*  34:    */   protected JRDataSource getJRDataSource()
/*  35:    */   {
/*  36: 42 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  37: 43 */     JRDataSource ds = null;
/*  38:    */     try
/*  39:    */     {
/*  40: 45 */       listaDatosReporte = this.servicioReporteChequeAsiento.getReporteChequeAsiento(getAsiento().getId());
/*  41: 46 */       if (!listaDatosReporte.isEmpty()) {
/*  42: 47 */         this.nombreCiudadCheque = ((Object[])listaDatosReporte.get(0))[5].toString();
/*  43:    */       }
/*  44: 51 */       String[] fields = { "f_fecha", "f_beneficiario", "f_valor", "f_numeroCheque", "f_reporte", "f_ciudad" };
/*  45:    */       
/*  46: 53 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  47:    */     }
/*  48:    */     catch (Exception e)
/*  49:    */     {
/*  50: 56 */       LOG.info("Error " + e);
/*  51: 57 */       e.printStackTrace();
/*  52:    */     }
/*  53: 59 */     return ds;
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected Map<String, Object> getReportParameters()
/*  57:    */   {
/*  58: 70 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  59: 71 */     reportParameters.put("p_ciudad", this.nombreCiudadCheque + ",");
/*  60: 72 */     return reportParameters;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String execute()
/*  64:    */   {
/*  65:    */     try
/*  66:    */     {
/*  67: 82 */       super.prepareReport();
/*  68:    */     }
/*  69:    */     catch (JRException e)
/*  70:    */     {
/*  71: 84 */       LOG.info("Error JRException");
/*  72: 85 */       e.printStackTrace();
/*  73: 86 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  74:    */     }
/*  75:    */     catch (IOException e)
/*  76:    */     {
/*  77: 88 */       LOG.info("Error IOException");
/*  78: 89 */       e.printStackTrace();
/*  79: 90 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  80:    */     }
/*  81: 93 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected String getCompileFileName()
/*  85:    */   {
/*  86:104 */     String reporte = this.servicioCuentaBancariaOrganizacion.getReporteCuentaBancaria(getAsiento().getId());
/*  87:105 */     if (!reporte.isEmpty()) {
/*  88:106 */       this.COMPILE_FILE_NAME = reporte;
/*  89:    */     }
/*  90:109 */     return this.COMPILE_FILE_NAME;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Asiento getAsiento()
/*  94:    */   {
/*  95:113 */     if (this.asiento == null) {
/*  96:114 */       this.asiento = new Asiento();
/*  97:    */     }
/*  98:117 */     return this.asiento;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setAsiento(Asiento asiento)
/* 102:    */   {
/* 103:121 */     this.asiento = asiento;
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.ReporteChequeAsientoBean
 * JD-Core Version:    0.7.0.1
 */