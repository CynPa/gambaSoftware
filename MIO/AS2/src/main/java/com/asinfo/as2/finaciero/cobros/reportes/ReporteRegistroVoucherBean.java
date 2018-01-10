/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Cobro;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*   7:    */ import com.asinfo.as2.util.AppUtil;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.RequestScoped;
/*  17:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  18:    */ import net.sf.jasperreports.engine.JRException;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @RequestScoped
/*  23:    */ public class ReporteRegistroVoucherBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -3178818486208673560L;
/*  27:    */   @EJB
/*  28:    */   private ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  29:    */   private Cobro cobro;
/*  30: 32 */   private final String COMPILE_FILE_NAME = "reporteRegistroVoucher";
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 42 */     List listaDatosReporte = new ArrayList();
/*  35: 43 */     JRDataSource ds = null;
/*  36:    */     try
/*  37:    */     {
/*  38: 45 */       listaDatosReporte = this.servicioReporteCobroCliente.getReporteRegistroVoucher(AppUtil.getOrganizacion().getId(), getCobro().getIdCobro());
/*  39: 46 */       String[] fields = { "f_fechaRegistro", "f_documento", "f_nota", "f_estacion", "f_tarjetaCredito", "f_planTarjetaCredito", "f_numeroTarjeta", "f_baseImponibleDiferenteCero", "f_baseImponibleTarifaCero", "f_montoIva", "f_cobroTarjeta", "f_valor", "f_documentoReferencia", "f_lote", "f_fechaVoucher", "f_descripcion" };
/*  40:    */       
/*  41:    */ 
/*  42:    */ 
/*  43: 50 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  44:    */     }
/*  45:    */     catch (Exception e)
/*  46:    */     {
/*  47: 52 */       e.printStackTrace();
/*  48:    */     }
/*  49: 54 */     return ds;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected String getCompileFileName()
/*  53:    */   {
/*  54: 64 */     return "reporteRegistroVoucher";
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected Map<String, Object> getReportParameters()
/*  58:    */   {
/*  59: 75 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  60: 76 */     reportParameters.put("ReportTitle", "Voucher registrado");
/*  61: 77 */     return reportParameters;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String execute()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68: 86 */       super.prepareReport();
/*  69:    */     }
/*  70:    */     catch (JRException e)
/*  71:    */     {
/*  72: 89 */       LOG.info("Error JRException");
/*  73: 90 */       e.printStackTrace();
/*  74: 91 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  75:    */     }
/*  76:    */     catch (IOException e)
/*  77:    */     {
/*  78: 93 */       LOG.info("Error IOException");
/*  79: 94 */       e.printStackTrace();
/*  80: 95 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  81:    */     }
/*  82: 98 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Cobro getCobro()
/*  86:    */   {
/*  87:102 */     if (this.cobro == null) {
/*  88:103 */       this.cobro = new Cobro();
/*  89:    */     }
/*  90:105 */     return this.cobro;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setCobro(Cobro cobro)
/*  94:    */   {
/*  95:109 */     this.cobro = cobro;
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteRegistroVoucherBean
 * JD-Core Version:    0.7.0.1
 */