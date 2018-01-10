/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   5:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.faces.bean.ManagedBean;
/*  13:    */ import javax.faces.bean.ViewScoped;
/*  14:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  15:    */ import net.sf.jasperreports.engine.JRException;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @ViewScoped
/*  20:    */ public class ReporteOFABean
/*  21:    */   extends AbstractBaseReportBean
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   private OrdenFabricacion ordenFabricacion;
/*  25:    */   @EJB
/*  26:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  27: 40 */   private final String COMPILE_FILE_NAME = "reporteOrdenFabricacion";
/*  28:    */   
/*  29:    */   protected JRDataSource getJRDataSource()
/*  30:    */   {
/*  31: 45 */     JRDataSource ds = null;
/*  32:    */     try
/*  33:    */     {
/*  34: 48 */       String[] fields = { "numeroOF", "fechaOF", "fechaLanzamientoOF", "fechaCierreOF", "tipoCicloOF", "descripcionOF", "codigoProductoOF", "nombreProductoOF", "codigoUnidadOF", "sucursalOF", "rutaFabricacionOF", "bodegaOF", "nombresPersonaResponsableOF", "apellidosPersonaResponsableOF", "atributoOF", "valorAtributoOF", "cantidadOF", "cantidadBatchOF", "cantidadFabricadaOF", "cantidadBatchFabricadosOF", "cantidadProduccionOF", "numeroOFH", "rutaFabricacionOFH", "codigoProductoOFH", "nombreProductoOFH", "codigoUnidadOFH", "cantidadOFH", "cantidadBatchOFH", "cantidadFabricadaOFH", "cantidadBatchFabricadosOFH", "cantidadProduccionOFH", "numeroOSMP", "numeroOSMH" };
/*  35:    */       
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41: 55 */       List<Object[]> listaDatosReporte = this.servicioOrdenFabricacion.getReporteOrdenFabricacion(this.ordenFabricacion);
/*  42:    */       
/*  43: 57 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  44:    */     }
/*  45:    */     catch (Exception e)
/*  46:    */     {
/*  47: 59 */       e.printStackTrace();
/*  48:    */     }
/*  49: 61 */     return ds;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected String getCompileFileName()
/*  53:    */   {
/*  54: 66 */     return "reporteOrdenFabricacion";
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected Map<String, Object> getReportParameters()
/*  58:    */   {
/*  59: 71 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  60: 72 */     reportParameters.put("ReportTitle", "Reporte Orden Fabricacion");
/*  61: 73 */     return reportParameters;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String execute()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68: 82 */       super.prepareReport();
/*  69:    */     }
/*  70:    */     catch (JRException e)
/*  71:    */     {
/*  72: 85 */       LOG.info("Error JRException");
/*  73: 86 */       e.printStackTrace();
/*  74: 87 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  75:    */     }
/*  76:    */     catch (IOException e)
/*  77:    */     {
/*  78: 89 */       LOG.info("Error IOException");
/*  79: 90 */       e.printStackTrace();
/*  80: 91 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  81:    */     }
/*  82: 94 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public OrdenFabricacion getOrdenFabricacion()
/*  86:    */   {
/*  87: 98 */     return this.ordenFabricacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/*  91:    */   {
/*  92:102 */     this.ordenFabricacion = ordenFabricacion;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteOFABean
 * JD-Core Version:    0.7.0.1
 */