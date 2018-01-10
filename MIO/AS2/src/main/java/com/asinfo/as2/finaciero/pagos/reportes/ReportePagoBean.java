/*   1:    */ package com.asinfo.as2.finaciero.pagos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.DetalleFormaPago;
/*   5:    */ import com.asinfo.as2.entities.FormaPago;
/*   6:    */ import com.asinfo.as2.entities.Pago;
/*   7:    */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReportePagoProveedor;
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
/*  23:    */ public class ReportePagoBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -4283259981721466901L;
/*  27:    */   @EJB
/*  28:    */   private ServicioReportePagoProveedor servicioReportePagoProveedor;
/*  29:    */   private Pago pago;
/*  30:    */   public static final String COMPILE_FILE_NAME = "reportePago";
/*  31: 54 */   public static final String[] fields = { "f_nombreComercial", "f_identificacion", "f_numeroPago", "f_fechaPago", "f_valorPago", "f_descripcionPago", "f_numeroFactura", "f_fechaEmision", "f_fechaVencimiento", "f_cuota", "f_saldo", "f_valorLiquidar", "f_facturaSri" };
/*  32:    */   
/*  33:    */   protected JRDataSource getJRDataSource()
/*  34:    */   {
/*  35: 66 */     List listaDatosReporte = new ArrayList();
/*  36: 67 */     JRDataSource ds = null;
/*  37:    */     try
/*  38:    */     {
/*  39: 69 */       listaDatosReporte = this.servicioReportePagoProveedor.getReportePago(getPago().getId());
/*  40:    */       
/*  41: 71 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  42:    */     }
/*  43:    */     catch (Exception e)
/*  44:    */     {
/*  45: 73 */       e.printStackTrace();
/*  46:    */     }
/*  47: 75 */     return ds;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected String getCompileFileName()
/*  51:    */   {
/*  52: 85 */     return "reportePago";
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected Map<String, Object> getReportParameters()
/*  56:    */   {
/*  57: 96 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  58: 97 */     reportParameters.put("ReportTitle", "Pago");
/*  59:    */     
/*  60: 99 */     String formaPago = "";
/*  61:100 */     for (DetalleFormaPago dfp : this.servicioReportePagoProveedor.getDetalleFormaCobro(getPago().getId())) {
/*  62:101 */       formaPago = formaPago.concat(dfp.getFormaPago().getNombre()).concat(":") + dfp.getDocumentoReferencia().concat(" \n");
/*  63:    */     }
/*  64:104 */     reportParameters.put("p_formaPago", formaPago);
/*  65:105 */     return reportParameters;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String execute()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72:114 */       super.prepareReport();
/*  73:    */     }
/*  74:    */     catch (JRException e)
/*  75:    */     {
/*  76:117 */       LOG.info("Error JRException");
/*  77:118 */       e.printStackTrace();
/*  78:119 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  79:    */     }
/*  80:    */     catch (IOException e)
/*  81:    */     {
/*  82:121 */       LOG.info("Error IOException");
/*  83:122 */       e.printStackTrace();
/*  84:123 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  85:    */     }
/*  86:126 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Pago getPago()
/*  90:    */   {
/*  91:130 */     if (this.pago == null) {
/*  92:131 */       this.pago = new Pago();
/*  93:    */     }
/*  94:133 */     return this.pago;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setPago(Pago pago)
/*  98:    */   {
/*  99:137 */     this.pago = pago;
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReportePagoBean
 * JD-Core Version:    0.7.0.1
 */