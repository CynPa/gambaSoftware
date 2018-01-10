/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.PagoComision;
/*   5:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   6:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   7:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPagoComision;
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
/*  21:    */ public class ReportePagoComisionBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -192264607856793455L;
/*  25:    */   @EJB
/*  26:    */   private ServicioPagoComision servicioPagoComision;
/*  27:    */   private PagoComision pagoComision;
/*  28: 47 */   private final String COMPILE_FILE_NAME = "reportePagoComision";
/*  29:    */   
/*  30:    */   protected JRDataSource getJRDataSource()
/*  31:    */   {
/*  32: 51 */     JRDataSource ds = null;
/*  33: 52 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  34:    */     try
/*  35:    */     {
/*  36: 54 */       listaDatosReporte = this.servicioPagoComision.getReportePagoComision(this.pagoComision);
/*  37:    */       
/*  38: 56 */       String[] fields = { "f_documento", "f_numeroPagoComision", "f_fechaPagoComision", "f_mesInicialPagoComision", "f_mesFinalPagoComision", "f_anioInicialPagoComision", "f_anioFinalPagoComision", "f_estadoPagoComision", "f_descripcionPagoComision", "f_agenteComercial", "f_numeroFacturaCliente", "f_nombreCategoriaProducto", "f_nombreSubcategoriaProducto", "f_codigoProducto", "f_nombreProducto", "f_rangoDiasComision", "f_numeroCobro", "f_baseComision", "f_formaPagoComisionEnum", "f_porcentajeComision", "f_valorComision" };
/*  39:    */       
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44: 62 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  45:    */     }
/*  46:    */     catch (Exception e)
/*  47:    */     {
/*  48: 64 */       LOG.info("Error " + e);
/*  49: 65 */       e.printStackTrace();
/*  50:    */     }
/*  51: 67 */     return ds;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected String getCompileFileName()
/*  55:    */   {
/*  56: 77 */     return "reportePagoComision";
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Map<String, Object> getReportParameters()
/*  60:    */   {
/*  61: 88 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  62: 89 */     reportParameters.put("ReportTitle", "Pago Comisiones");
/*  63: 90 */     if (getPagoComision() != null) {
/*  64: 91 */       reportParameters.put("nombreUsuario", getPagoComision().getUsuarioCreacion().trim());
/*  65:    */     }
/*  66: 93 */     return reportParameters;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String execute()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:102 */       super.prepareReport();
/*  74:    */     }
/*  75:    */     catch (JRException e)
/*  76:    */     {
/*  77:104 */       LOG.info("Error JRException");
/*  78:105 */       e.printStackTrace();
/*  79:106 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  80:    */     }
/*  81:    */     catch (IOException e)
/*  82:    */     {
/*  83:108 */       LOG.info("Error IOException");
/*  84:109 */       e.printStackTrace();
/*  85:110 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  86:    */     }
/*  87:112 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public PagoComision getPagoComision()
/*  91:    */   {
/*  92:116 */     if (this.pagoComision == null) {
/*  93:117 */       this.pagoComision = new PagoComision();
/*  94:    */     }
/*  95:119 */     return this.pagoComision;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setPagoComision(PagoComision pagoComision)
/*  99:    */   {
/* 100:123 */     this.pagoComision = pagoComision;
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePagoComisionBean
 * JD-Core Version:    0.7.0.1
 */