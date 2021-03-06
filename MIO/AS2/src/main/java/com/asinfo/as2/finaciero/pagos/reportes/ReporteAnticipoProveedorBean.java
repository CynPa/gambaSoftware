/*   1:    */ package com.asinfo.as2.finaciero.pagos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*   5:    */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReporteAnticipoProveedor;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  15:    */ import net.sf.jasperreports.engine.JRException;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ public class ReporteAnticipoProveedorBean
/*  20:    */   extends AbstractBaseReportBean
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 2657481634108348119L;
/*  23:    */   @EJB
/*  24:    */   private ServicioReporteAnticipoProveedor servicioReporteAnticipoProveedor;
/*  25:    */   private AnticipoProveedor anticipoProveedor;
/*  26: 42 */   private final String COMPILE_FILE_NAME = "reporteAnticipoProveedor";
/*  27:    */   
/*  28:    */   protected JRDataSource getJRDataSource()
/*  29:    */   {
/*  30: 47 */     List listaDatosReporte = new ArrayList();
/*  31: 48 */     JRDataSource ds = null;
/*  32:    */     try
/*  33:    */     {
/*  34: 50 */       listaDatosReporte = this.servicioReporteAnticipoProveedor.getReporteAnticipo(getAnticipoProveedor().getId());
/*  35:    */       
/*  36: 52 */       String[] fields = { "f_nombreComercial", "f_identificacion", "f_fecha", "f_numero", "f_valor", "f_numeroDocumento", "f_beneficiario", "f_descripcion", "f_formaPago", "f_cuentaBancaria", "f_banco" };
/*  37:    */       
/*  38:    */ 
/*  39: 55 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  40:    */     }
/*  41:    */     catch (Exception e)
/*  42:    */     {
/*  43: 57 */       e.printStackTrace();
/*  44:    */     }
/*  45: 59 */     return ds;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected String getCompileFileName()
/*  49:    */   {
/*  50: 64 */     return "reporteAnticipoProveedor";
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected Map<String, Object> getReportParameters()
/*  54:    */   {
/*  55: 70 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  56: 71 */     reportParameters.put("ReportTitle", "Anticipo Proveedor");
/*  57: 72 */     return reportParameters;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String execute()
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64: 81 */       super.prepareReport();
/*  65:    */     }
/*  66:    */     catch (JRException e)
/*  67:    */     {
/*  68: 84 */       LOG.info("Error JRException");
/*  69: 85 */       e.printStackTrace();
/*  70: 86 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  71:    */     }
/*  72:    */     catch (IOException e)
/*  73:    */     {
/*  74: 88 */       LOG.info("Error IOException");
/*  75: 89 */       e.printStackTrace();
/*  76: 90 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  77:    */     }
/*  78: 93 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public AnticipoProveedor getAnticipoProveedor()
/*  82:    */   {
/*  83: 97 */     if (this.anticipoProveedor == null) {
/*  84: 98 */       this.anticipoProveedor = new AnticipoProveedor();
/*  85:    */     }
/*  86:100 */     return this.anticipoProveedor;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/*  90:    */   {
/*  91:104 */     this.anticipoProveedor = anticipoProveedor;
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReporteAnticipoProveedorBean
 * JD-Core Version:    0.7.0.1
 */