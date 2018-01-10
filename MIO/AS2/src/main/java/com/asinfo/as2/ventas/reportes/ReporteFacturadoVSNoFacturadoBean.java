/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.ContratoVenta;
/*   5:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   6:    */ import com.asinfo.as2.util.AppUtil;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioContratoVenta;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  18:    */ import net.sf.jasperreports.engine.JRException;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class ReporteFacturadoVSNoFacturadoBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1599721133141713459L;
/*  27:    */   @EJB
/*  28:    */   private ServicioContratoVenta servicioContratoVenta;
/*  29: 39 */   private final String COMPILE_FILE_NAME = "reporteFacturadoVSNoFacturado";
/*  30:    */   private ContratoVenta contratoVenta;
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 46 */     List listaDatosReporte = new ArrayList();
/*  35: 47 */     JRDataSource ds = null;
/*  36:    */     try
/*  37:    */     {
/*  38: 49 */       listaDatosReporte = this.servicioContratoVenta.contratoVentaFacturadoVSNoFacturado(getContratoVenta(), AppUtil.getOrganizacion(), AppUtil.getSucursal());
/*  39:    */       
/*  40: 51 */       String[] fields = { "f_numero", "f_nombreFiscal", "f_fecha", "f_fechaVencimiento", "f_descripcion", "valor", "fecha", "cuota", "indicadorFacturado" };
/*  41: 52 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  42:    */     }
/*  43:    */     catch (ExcepcionAS2 e)
/*  44:    */     {
/*  45: 54 */       LOG.info("Error " + e);
/*  46: 55 */       e.printStackTrace();
/*  47:    */     }
/*  48: 57 */     return ds;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected Map<String, Object> getReportParameters()
/*  52:    */   {
/*  53: 65 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  54: 66 */     reportParameters.put("ReportTitle", "Contrato Venta Facturado VS Contrato Venta no Facturado");
/*  55:    */     
/*  56:    */ 
/*  57: 69 */     return reportParameters;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected String getCompileFileName()
/*  61:    */   {
/*  62: 77 */     return "reporteFacturadoVSNoFacturado";
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String execute()
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69: 86 */       super.prepareReport();
/*  70:    */     }
/*  71:    */     catch (JRException e)
/*  72:    */     {
/*  73: 88 */       LOG.info("Error JRException");
/*  74: 89 */       e.printStackTrace();
/*  75: 90 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  76:    */     }
/*  77:    */     catch (IOException e)
/*  78:    */     {
/*  79: 92 */       LOG.info("Error IOException");
/*  80: 93 */       e.printStackTrace();
/*  81: 94 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  82:    */     }
/*  83: 97 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public ContratoVenta getContratoVenta()
/*  87:    */   {
/*  88:105 */     if (this.contratoVenta == null) {
/*  89:106 */       this.contratoVenta = new ContratoVenta();
/*  90:    */     }
/*  91:108 */     return this.contratoVenta;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setContratoVenta(ContratoVenta contratoVenta)
/*  95:    */   {
/*  96:116 */     this.contratoVenta = contratoVenta;
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteFacturadoVSNoFacturadoBean
 * JD-Core Version:    0.7.0.1
 */