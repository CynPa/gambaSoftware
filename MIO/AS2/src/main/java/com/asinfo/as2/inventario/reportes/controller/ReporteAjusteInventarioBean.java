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
/*  19:    */ public class ReporteAjusteInventarioBean
/*  20:    */   extends AbstractBaseReportBean
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 770222109067650882L;
/*  23:    */   @EJB
/*  24:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  25:    */   private MovimientoInventario ajusteInventario;
/*  26: 42 */   private final String COMPILE_FILE_NAME = "reporteAjusteInventario";
/*  27:    */   
/*  28:    */   protected JRDataSource getJRDataSource()
/*  29:    */   {
/*  30: 47 */     List listaDatosReporte = new ArrayList();
/*  31: 48 */     JRDataSource ds = null;
/*  32:    */     try
/*  33:    */     {
/*  34: 50 */       listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteAjusteInventario(getAjusteInventario().getId());
/*  35:    */       
/*  36: 52 */       String[] fields = { "f_idDetalleMovimientoInventario", "f_numero", "f_fecha", "f_descripcionAjusteInventario", "f_bodegaOrigen", "f_codigoProducto", "f_nombreProducto", "f_unidad", "f_cantidadProducto", "f_unidadDespacho", "f_cantidadOrigen", "f_descripcion", "f_codigoLote", "f_cantidad", "f_costo", "f_motivo", "f_documento", "f_usuarioCreacion" };
/*  37:    */       
/*  38:    */ 
/*  39:    */ 
/*  40: 56 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  41:    */     }
/*  42:    */     catch (Exception e)
/*  43:    */     {
/*  44: 58 */       e.printStackTrace();
/*  45:    */     }
/*  46: 60 */     return ds;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected String getCompileFileName()
/*  50:    */   {
/*  51: 65 */     return "reporteAjusteInventario";
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected Map<String, Object> getReportParameters()
/*  55:    */   {
/*  56: 71 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  57: 72 */     reportParameters.put("ReportTitle", "Ajuste Inventario");
/*  58: 73 */     return reportParameters;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String execute()
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65: 82 */       super.prepareReport();
/*  66:    */     }
/*  67:    */     catch (JRException e)
/*  68:    */     {
/*  69: 85 */       LOG.info("Error JRException");
/*  70: 86 */       e.printStackTrace();
/*  71: 87 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  72:    */     }
/*  73:    */     catch (IOException e)
/*  74:    */     {
/*  75: 89 */       LOG.info("Error IOException");
/*  76: 90 */       e.printStackTrace();
/*  77: 91 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  78:    */     }
/*  79: 94 */     return "";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public MovimientoInventario getAjusteInventario()
/*  83:    */   {
/*  84: 98 */     if (this.ajusteInventario == null) {
/*  85: 99 */       this.ajusteInventario = new MovimientoInventario();
/*  86:    */     }
/*  87:101 */     return this.ajusteInventario;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setAjusteInventario(MovimientoInventario ajusteInventario)
/*  91:    */   {
/*  92:105 */     this.ajusteInventario = ajusteInventario;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteAjusteInventarioBean
 * JD-Core Version:    0.7.0.1
 */