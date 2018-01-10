/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   5:    */ import com.asinfo.as2.produccion.reportes.servicio.ServicioReporteFabricacion;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.ViewScoped;
/*  15:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  16:    */ import net.sf.jasperreports.engine.JRException;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @ViewScoped
/*  21:    */ public class ReporteTransformacionProductoBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @EJB
/*  26:    */   private ServicioReporteFabricacion servicioReporteFabricacion;
/*  27:    */   private MovimientoInventario transformacionProducto;
/*  28:    */   
/*  29:    */   protected JRDataSource getJRDataSource()
/*  30:    */   {
/*  31: 59 */     List listaDatosReporte = new ArrayList();
/*  32: 60 */     JRDataSource ds = null;
/*  33:    */     try
/*  34:    */     {
/*  35: 63 */       listaDatosReporte = this.servicioReporteFabricacion.getReporteTransformacionProducto(this.transformacionProducto);
/*  36:    */       
/*  37: 65 */       String[] fields = { "f_codigoProducto", "f_nombreProducto", "f_unidad", "f_bodegaOrigen", "f_bodegaDestino", "f_cantidad", "f_operacion" };
/*  38:    */       
/*  39:    */ 
/*  40: 68 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  41:    */     }
/*  42:    */     catch (Exception e)
/*  43:    */     {
/*  44: 71 */       e.printStackTrace();
/*  45:    */     }
/*  46: 73 */     return ds;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected String getCompileFileName()
/*  50:    */   {
/*  51: 83 */     return "reporteTransformacionProducto";
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected Map<String, Object> getReportParameters()
/*  55:    */   {
/*  56: 93 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  57: 94 */     reportParameters.put("ReportTitle", "Reporte Transformacion Producto");
/*  58: 95 */     reportParameters.put("p_fecha", this.transformacionProducto.getFecha());
/*  59: 96 */     reportParameters.put("p_numero", this.transformacionProducto.getNumero());
/*  60: 97 */     reportParameters.put("p_descripcion", this.transformacionProducto.getDescripcion());
/*  61: 98 */     return reportParameters;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String execute()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:108 */       super.prepareReport();
/*  69:    */     }
/*  70:    */     catch (JRException e)
/*  71:    */     {
/*  72:110 */       LOG.info("Error JRException");
/*  73:111 */       e.printStackTrace();
/*  74:112 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  75:    */     }
/*  76:    */     catch (IOException e)
/*  77:    */     {
/*  78:114 */       LOG.info("Error IOException");
/*  79:115 */       e.printStackTrace();
/*  80:116 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  81:    */     }
/*  82:118 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public MovimientoInventario getTransformacionProducto()
/*  86:    */   {
/*  87:122 */     return this.transformacionProducto;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setTransformacionProducto(MovimientoInventario transformacionProducto)
/*  91:    */   {
/*  92:126 */     this.transformacionProducto = transformacionProducto;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteTransformacionProductoBean
 * JD-Core Version:    0.7.0.1
 */