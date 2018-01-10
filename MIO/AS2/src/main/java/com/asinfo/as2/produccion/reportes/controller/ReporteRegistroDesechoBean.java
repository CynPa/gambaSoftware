/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   5:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioDesechoMaterial;
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
/*  21:    */ public class ReporteRegistroDesechoBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 770222109067650882L;
/*  25:    */   @EJB
/*  26:    */   private ServicioDesechoMaterial servicioDesechoMaterial;
/*  27:    */   private OrdenFabricacion ordenFabricacion;
/*  28: 44 */   private final String COMPILE_FILE_NAME = "reporteRegistroDesecho";
/*  29:    */   
/*  30:    */   protected JRDataSource getJRDataSource()
/*  31:    */   {
/*  32: 49 */     List listaDatosReporte = new ArrayList();
/*  33: 50 */     JRDataSource ds = null;
/*  34:    */     try
/*  35:    */     {
/*  36: 52 */       listaDatosReporte = this.servicioDesechoMaterial.getReporteRegistroDesecho(this.ordenFabricacion);
/*  37:    */       
/*  38: 54 */       String[] fields = { "f_numeroOrdenFabricacion", "f_codigoProducto", "f_nombreProducto", "f_descripcionOrdenFabricacion", "f_cantidadOrdenFabricacion", "f_cantidadBatchOrdenFabricacion", "f_cantidadFabricadaOrdenFabricacion", "f_cantidadBatchFabricadosOrdenFabricacion", "f_cantidadBOM", "f_numeroOrdenSalidaMaterial", "f_codigoMaterial", "f_nombreMaterial", "f_loteMaterial", "f_bodegaMaterial", "f_unidadMaterial", "f_cantidadMaterial", "f_cantidadDesechoMaterial", "f_unidadInformativaMaterial", "f_cantidadInformativaDesecho" };
/*  39:    */       
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44: 60 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  45:    */     }
/*  46:    */     catch (Exception e)
/*  47:    */     {
/*  48: 62 */       e.printStackTrace();
/*  49:    */     }
/*  50: 64 */     return ds;
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected String getCompileFileName()
/*  54:    */   {
/*  55: 69 */     return "reporteRegistroDesecho";
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected Map<String, Object> getReportParameters()
/*  59:    */   {
/*  60: 75 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  61: 76 */     reportParameters.put("ReportTitle", "Registro Desecho");
/*  62: 77 */     return reportParameters;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String execute()
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69: 85 */       super.prepareReport();
/*  70:    */     }
/*  71:    */     catch (JRException e)
/*  72:    */     {
/*  73: 87 */       LOG.info("Error JRException");
/*  74: 88 */       e.printStackTrace();
/*  75: 89 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  76:    */     }
/*  77:    */     catch (IOException e)
/*  78:    */     {
/*  79: 91 */       LOG.info("Error IOException");
/*  80: 92 */       e.printStackTrace();
/*  81: 93 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  82:    */     }
/*  83: 96 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public OrdenFabricacion getOrdenFabricacion()
/*  87:    */   {
/*  88:100 */     return this.ordenFabricacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/*  92:    */   {
/*  93:104 */     this.ordenFabricacion = ordenFabricacion;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteRegistroDesechoBean
 * JD-Core Version:    0.7.0.1
 */