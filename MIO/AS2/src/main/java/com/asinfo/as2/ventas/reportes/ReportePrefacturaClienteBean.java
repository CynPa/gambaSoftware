/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.PrefacturaCliente;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPrefacturaCliente;
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
/*  23:    */ public class ReportePrefacturaClienteBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @EJB
/*  28:    */   private ServicioPrefacturaCliente servicioPrefacturaCliente;
/*  29:    */   private PrefacturaCliente prefacturaCliente;
/*  30:    */   private String COMPILE_FILE_NAME;
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 47 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  35: 48 */     JRDataSource ds = null;
/*  36:    */     try
/*  37:    */     {
/*  38: 51 */       listaDatosReporte = this.servicioPrefacturaCliente.getReportePrefacturaCliente(this.prefacturaCliente.getIdPrefacturaCliente());
/*  39: 52 */       String[] fields = { "f_nombreFiscal", "f_identificacion", "f_codigo", "f_nombreComercial", "f_direccion", "f_referencia", "f_fechaPrefactura", "f_descuentoPrefactura", "f_totalPrefactura", "f_impuestoPrefactura", "f_descripcionPrefactura", "f_numeroPrefactura", "f_cantidad", "f_precio", "f_descripcion", "f_descuento", "f_codigoProducto", "f_producto", "f_codigoAlterno", "f_codigoBarras", "f_peso", "f_ciudad", "f_condicionPago", "f_agenteComercial", "f_unidadVenta", "f_zona", "f_canal", "f_telefono1", "f_telefono2", "f_provincia" };
/*  40:    */       
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45: 58 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  46:    */     }
/*  47:    */     catch (ExcepcionAS2 e)
/*  48:    */     {
/*  49: 60 */       LOG.info("Error " + e);
/*  50: 61 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  51:    */     }
/*  52: 63 */     return ds;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected String getCompileFileName()
/*  56:    */   {
/*  57: 68 */     this.COMPILE_FILE_NAME = this.prefacturaCliente.getDocumento().getReporte();
/*  58: 69 */     return this.COMPILE_FILE_NAME;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String execute()
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65: 75 */       LOG.info("Nombre del archivo + " + this.COMPILE_FILE_NAME);
/*  66: 76 */       super.prepareReport();
/*  67:    */     }
/*  68:    */     catch (JRException e)
/*  69:    */     {
/*  70: 79 */       LOG.info("Error JRException");
/*  71: 80 */       e.printStackTrace();
/*  72: 81 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  73:    */     }
/*  74:    */     catch (IOException e)
/*  75:    */     {
/*  76: 83 */       LOG.info("Error IOException");
/*  77: 84 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  78:    */     }
/*  79: 87 */     return "";
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected Map<String, Object> getReportParameters()
/*  83:    */   {
/*  84: 93 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  85: 94 */     reportParameters.put("ReportTitle", "Pre-Factura Cliente");
/*  86: 95 */     return reportParameters;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public PrefacturaCliente getPrefacturaCliente()
/*  90:    */   {
/*  91: 99 */     return this.prefacturaCliente;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setPrefacturaCliente(PrefacturaCliente prefacturaCliente)
/*  95:    */   {
/*  96:103 */     this.prefacturaCliente = prefacturaCliente;
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePrefacturaClienteBean
 * JD-Core Version:    0.7.0.1
 */