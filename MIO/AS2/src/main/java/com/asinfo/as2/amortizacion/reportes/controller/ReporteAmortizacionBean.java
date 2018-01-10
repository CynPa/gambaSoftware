/*   1:    */ package com.asinfo.as2.amortizacion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.amortizacion.procesos.servicio.ServicioAmortizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.amortizacion.Amortizacion;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.ArrayList;
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
/*  22:    */ public class ReporteAmortizacionBean
/*  23:    */   extends AbstractBaseReportBean
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1599721133141713459L;
/*  26:    */   @EJB
/*  27:    */   private ServicioAmortizacion servicioAmortizacion;
/*  28: 31 */   private final String COMPILE_FILE_NAME = "reporteAmortizacion";
/*  29:    */   private Amortizacion amortizacion;
/*  30:    */   
/*  31:    */   protected JRDataSource getJRDataSource()
/*  32:    */   {
/*  33: 37 */     List listaDatosReporte = new ArrayList();
/*  34: 38 */     JRDataSource ds = null;
/*  35:    */     try
/*  36:    */     {
/*  37: 40 */       listaDatosReporte = this.servicioAmortizacion.getReporteAmortizacion(getAmortizacion());
/*  38: 41 */       String[] fields = { "f_numero", "f_fecha_inicio_amortizacion", "f_meses_amortizados", "f_meses_por_amortizar", "f_valor", "f_valor_amortizado", "f_valor_amortizado_total", "f_numero_compra", "f_factura_proveedor", "f_fecha_compra", "f_descripcion", "f_tipo_amortizacion", "f_det_fecha_vencimiento", "f_det_valor", "f_det_estado", "f_det_asiento" };
/*  39:    */       
/*  40:    */ 
/*  41: 44 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  42:    */     }
/*  43:    */     catch (ExcepcionAS2 e)
/*  44:    */     {
/*  45: 46 */       LOG.info("Error " + e);
/*  46: 47 */       e.printStackTrace();
/*  47:    */     }
/*  48: 50 */     return ds;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected Map<String, Object> getReportParameters()
/*  52:    */   {
/*  53: 58 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  54: 59 */     reportParameters.put("ReportTitle", "Amortizacion");
/*  55:    */     
/*  56: 61 */     return reportParameters;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected String getCompileFileName()
/*  60:    */   {
/*  61: 69 */     return "reporteAmortizacion";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String execute()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68: 78 */       super.prepareReport();
/*  69:    */     }
/*  70:    */     catch (JRException e)
/*  71:    */     {
/*  72: 80 */       LOG.info("Error JRException");
/*  73: 81 */       e.printStackTrace();
/*  74: 82 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  75:    */     }
/*  76:    */     catch (IOException e)
/*  77:    */     {
/*  78: 84 */       LOG.info("Error IOException");
/*  79: 85 */       e.printStackTrace();
/*  80: 86 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  81:    */     }
/*  82: 89 */     return null;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Amortizacion getAmortizacion()
/*  86:    */   {
/*  87: 96 */     if (this.amortizacion == null) {
/*  88: 97 */       this.amortizacion = new Amortizacion();
/*  89:    */     }
/*  90:100 */     return this.amortizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setAmortizacion(Amortizacion amortizacion)
/*  94:    */   {
/*  95:108 */     this.amortizacion = amortizacion;
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.amortizacion.reportes.controller.ReporteAmortizacionBean
 * JD-Core Version:    0.7.0.1
 */