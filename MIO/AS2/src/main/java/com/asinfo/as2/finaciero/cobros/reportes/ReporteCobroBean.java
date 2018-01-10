/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Cobro;
/*   5:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
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
/*  21:    */ public class ReporteCobroBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -3178818486208673560L;
/*  25:    */   @EJB
/*  26:    */   private ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  27:    */   private Cobro cobro;
/*  28: 51 */   private final String COMPILE_FILE_NAME = "reporteCobro";
/*  29:    */   
/*  30:    */   protected JRDataSource getJRDataSource()
/*  31:    */   {
/*  32: 61 */     List listaDatosReporte = new ArrayList();
/*  33: 62 */     JRDataSource ds = null;
/*  34:    */     try
/*  35:    */     {
/*  36: 64 */       listaDatosReporte = this.servicioReporteCobroCliente.getReporteCobro(getCobro().getIdCobro());
/*  37:    */       
/*  38: 66 */       String[] fields = { "f_nombreComercial", "f_identificacion", "f_numeroCobro", "f_fechaCobro", "f_valorCobro", "f_descripcionCobro", "f_numeroFactura", "f_fechaEmision", "f_fechaVencimiento", "f_cuota", "f_saldo", "f_valorLiquidar", "f_descripcion2" };
/*  39:    */       
/*  40:    */ 
/*  41: 69 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  42:    */     }
/*  43:    */     catch (Exception e)
/*  44:    */     {
/*  45: 71 */       e.printStackTrace();
/*  46:    */     }
/*  47: 73 */     return ds;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected String getCompileFileName()
/*  51:    */   {
/*  52: 83 */     return "reporteCobro";
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected Map<String, Object> getReportParameters()
/*  56:    */   {
/*  57: 94 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  58: 95 */     reportParameters.put("ReportTitle", "Cobro");
/*  59: 96 */     return reportParameters;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String execute()
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66:105 */       super.prepareReport();
/*  67:    */     }
/*  68:    */     catch (JRException e)
/*  69:    */     {
/*  70:108 */       LOG.info("Error JRException");
/*  71:109 */       e.printStackTrace();
/*  72:110 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  73:    */     }
/*  74:    */     catch (IOException e)
/*  75:    */     {
/*  76:112 */       LOG.info("Error IOException");
/*  77:113 */       e.printStackTrace();
/*  78:114 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  79:    */     }
/*  80:117 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Cobro getCobro()
/*  84:    */   {
/*  85:121 */     if (this.cobro == null) {
/*  86:122 */       this.cobro = new Cobro();
/*  87:    */     }
/*  88:124 */     return this.cobro;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCobro(Cobro cobro)
/*  92:    */   {
/*  93:128 */     this.cobro = cobro;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteCobroBean
 * JD-Core Version:    0.7.0.1
 */