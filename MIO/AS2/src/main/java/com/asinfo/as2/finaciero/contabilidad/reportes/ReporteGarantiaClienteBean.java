/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.GarantiaCliente;
/*   5:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteGarantiaCliente;
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
/*  21:    */ public class ReporteGarantiaClienteBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -867217422309192454L;
/*  25:    */   @EJB
/*  26:    */   private ServicioReporteGarantiaCliente servicioReporteGarantiaCliente;
/*  27:    */   private GarantiaCliente garantiaCliente;
/*  28: 32 */   private final String COMPILE_FILE_NAME = "reporteGarantiaCliente";
/*  29:    */   
/*  30:    */   protected JRDataSource getJRDataSource()
/*  31:    */   {
/*  32: 37 */     List listaDatosReporte = new ArrayList();
/*  33: 38 */     JRDataSource ds = null;
/*  34:    */     try
/*  35:    */     {
/*  36: 40 */       listaDatosReporte = this.servicioReporteGarantiaCliente.getReporteGarantiaCliente(getGarantiaCliente().getId());
/*  37:    */       
/*  38: 42 */       String[] fields = { "f_cliente", "f_identificacionCliente", "f_banco", "f_numeroCheque", "f_numeroCuenta", "f_fechaIngreso", "f_fechaCobro", "f_girador", "f_recibidoPor", "f_estadoGarantiaCliente", "f_valor", "f_concepto", "f_numeroCuentaBancariaOrganizacion", "f_valorProtestado", "f_diasCredito", "f_facturaCliente", "f_valorDetalleCobro" };
/*  39:    */       
/*  40:    */ 
/*  41:    */ 
/*  42: 46 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  43:    */     }
/*  44:    */     catch (Exception e)
/*  45:    */     {
/*  46: 48 */       LOG.info("Error " + e);
/*  47: 49 */       e.printStackTrace();
/*  48:    */     }
/*  49: 51 */     return ds;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected String getCompileFileName()
/*  53:    */   {
/*  54: 61 */     return "reporteGarantiaCliente";
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected Map<String, Object> getReportParameters()
/*  58:    */   {
/*  59: 72 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  60: 73 */     reportParameters.put("ReportTitle", "Cheque Posfechado");
/*  61: 74 */     reportParameters.put("p_fechaEmision:", "Fecha de Emision:");
/*  62: 75 */     reportParameters.put("p_numeroCheque", "No. Cheque:");
/*  63: 76 */     reportParameters.put("p_valor", "Valor:");
/*  64: 77 */     reportParameters.put("p_cliente", "Cliente:");
/*  65: 78 */     reportParameters.put("p_identificacionCliente", "Identificacion:");
/*  66: 79 */     reportParameters.put("p_banco", "Banco:");
/*  67: 80 */     reportParameters.put("p_fechaIngreso", "Fecha de Ingreso:");
/*  68: 81 */     reportParameters.put("p_fechaCobro", "Fecha a Cobrar:");
/*  69: 82 */     reportParameters.put("p_concepto", "Concepto:");
/*  70: 83 */     reportParameters.put("p_diasCredito", "Dias de Credito Otorgado:");
/*  71: 84 */     reportParameters.put("p_fechaEmision", "Fecha de Emision:");
/*  72: 85 */     reportParameters.put("p_elaborado", "Elaborado Por");
/*  73: 86 */     reportParameters.put("p_revisado", "Revisado Por");
/*  74: 87 */     reportParameters.put("p_aprobado", "Aprobado Por");
/*  75:    */     
/*  76: 89 */     return reportParameters;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String execute()
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83: 94 */       super.prepareReport();
/*  84:    */     }
/*  85:    */     catch (JRException e)
/*  86:    */     {
/*  87: 96 */       LOG.info("Error JRException");
/*  88: 97 */       e.printStackTrace();
/*  89: 98 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  90:    */     }
/*  91:    */     catch (IOException e)
/*  92:    */     {
/*  93:100 */       LOG.info("Error IOException");
/*  94:101 */       e.printStackTrace();
/*  95:102 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  96:    */     }
/*  97:105 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public GarantiaCliente getGarantiaCliente()
/* 101:    */   {
/* 102:109 */     if (this.garantiaCliente == null) {
/* 103:110 */       this.garantiaCliente = new GarantiaCliente();
/* 104:    */     }
/* 105:112 */     return this.garantiaCliente;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setGarantiaCliente(GarantiaCliente garantiaCliente)
/* 109:    */   {
/* 110:116 */     this.garantiaCliente = garantiaCliente;
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.ReporteGarantiaClienteBean
 * JD-Core Version:    0.7.0.1
 */