/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   5:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
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
/*  19:    */ public class ReporteTransferenciaBodegaBean
/*  20:    */   extends AbstractBaseReportBean
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 3748793990194304607L;
/*  23:    */   @EJB
/*  24:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  25:    */   private MovimientoInventario transferencia;
/*  26: 42 */   private final String COMPILE_FILE_NAME = "reporteTransferenciaBodega";
/*  27:    */   
/*  28:    */   protected JRDataSource getJRDataSource()
/*  29:    */   {
/*  30: 47 */     List listaDatosReporte = new ArrayList();
/*  31: 48 */     JRDataSource ds = null;
/*  32:    */     try
/*  33:    */     {
/*  34: 50 */       listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteTransferenciaBodega(getTransferencia().getId());
/*  35:    */       
/*  36: 52 */       String[] fields = { "f_numero", "f_fecha", "f_descripcionTransferencia", "f_codigoProducto", "f_nombreProducto", "f_unidad", "f_cantidadProducto", "f_bodegaOrigen", "f_bodegaDestino", "f_descripcion", "f_serie", "f_proyecto", "f_estado", "f_costo", "f_cantidadRecibida", "f_numero_asiento", "f_usuario_creacion", "f_unidadInformativa", "f_cantidadInformativa", "f_cantidadInformativaRecibida", "f_codigoAlternoProducto" };
/*  37:    */       
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41: 57 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  42:    */     }
/*  43:    */     catch (Exception e)
/*  44:    */     {
/*  45: 59 */       e.printStackTrace();
/*  46:    */     }
/*  47: 61 */     return ds;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected String getCompileFileName()
/*  51:    */   {
/*  52: 66 */     return "reporteTransferenciaBodega";
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected Map<String, Object> getReportParameters()
/*  56:    */   {
/*  57: 72 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  58: 73 */     reportParameters.put("ReportTitle", "Transferencia Bodega");
/*  59: 74 */     return reportParameters;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String execute()
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66: 83 */       super.prepareReport();
/*  67:    */     }
/*  68:    */     catch (JRException e)
/*  69:    */     {
/*  70: 86 */       LOG.info("Error JRException");
/*  71: 87 */       e.printStackTrace();
/*  72: 88 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  73:    */     }
/*  74:    */     catch (IOException e)
/*  75:    */     {
/*  76: 90 */       LOG.info("Error IOException");
/*  77: 91 */       e.printStackTrace();
/*  78: 92 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  79:    */     }
/*  80: 95 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public MovimientoInventario getTransferencia()
/*  84:    */   {
/*  85: 99 */     if (this.transferencia == null) {
/*  86:100 */       this.transferencia = new MovimientoInventario();
/*  87:    */     }
/*  88:102 */     return this.transferencia;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setTransferencia(MovimientoInventario transferencia)
/*  92:    */   {
/*  93:106 */     this.transferencia = transferencia;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteTransferenciaBodegaBean
 * JD-Core Version:    0.7.0.1
 */