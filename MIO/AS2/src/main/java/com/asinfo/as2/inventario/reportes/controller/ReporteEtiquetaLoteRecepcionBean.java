/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   7:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  10:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ViewScoped;
/*  18:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  19:    */ import net.sf.jasperreports.engine.JRException;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class ReporteEtiquetaLoteRecepcionBean
/*  25:    */   extends AbstractBaseReportBean
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioRecepcionProveedor servicioRecepcionProveedor;
/*  30:    */   private RecepcionProveedor recepcionProveedor;
/*  31: 45 */   private final String COMPILE_FILE_NAME = "reporteLoteEtiqueta";
/*  32:    */   
/*  33:    */   protected JRDataSource getJRDataSource()
/*  34:    */   {
/*  35: 50 */     JRDataSource ds = null;
/*  36:    */     try
/*  37:    */     {
/*  38: 53 */       String[] fields = { "f_numero", "f_fecha", "f_codigoLote", "f_nombreComercialProducto", "f_cantidad", "f_nombreUnidad", "f_descripcionProducto", "f_proveedor", "f_nombreProducto", "f_codigoUnidad", "f_codigoProducto", "f_numeroDecimales", "f_precio", "f_fechaCreacion" };
/*  39:    */       
/*  40:    */ 
/*  41:    */ 
/*  42: 57 */       List<Object[]> listaDatosReporte = new ArrayList();
/*  43: 59 */       if (getRecepcionProveedor() != null) {
/*  44: 61 */         listaDatosReporte = this.servicioRecepcionProveedor.getDatosImpresionEtiquetaLote(AppUtil.getOrganizacion().getId(), 
/*  45: 62 */           getRecepcionProveedor().getDocumento(), getRecepcionProveedor().getNumero(), null, null, false, AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/*  46:    */       }
/*  47: 65 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  48:    */     }
/*  49:    */     catch (Exception e)
/*  50:    */     {
/*  51: 67 */       e.printStackTrace();
/*  52:    */     }
/*  53: 69 */     return ds;
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected String getCompileFileName()
/*  57:    */   {
/*  58: 74 */     return "reporteLoteEtiqueta";
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected Map<String, Object> getReportParameters()
/*  62:    */   {
/*  63: 80 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  64: 81 */     reportParameters.put("ReportTitle", "Lote Etiqueta");
/*  65: 82 */     return reportParameters;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String execute()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 91 */       super.prepareReport();
/*  73:    */     }
/*  74:    */     catch (JRException e)
/*  75:    */     {
/*  76: 94 */       LOG.info("Error JRException");
/*  77: 95 */       e.printStackTrace();
/*  78: 96 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  79:    */     }
/*  80:    */     catch (IOException e)
/*  81:    */     {
/*  82: 98 */       LOG.info("Error IOException");
/*  83: 99 */       e.printStackTrace();
/*  84:100 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  85:    */     }
/*  86:103 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public RecepcionProveedor getRecepcionProveedor()
/*  90:    */   {
/*  91:107 */     return this.recepcionProveedor;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setRecepcionProveedor(RecepcionProveedor recepcionProveedor)
/*  95:    */   {
/*  96:111 */     this.recepcionProveedor = recepcionProveedor;
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteEtiquetaLoteRecepcionBean
 * JD-Core Version:    0.7.0.1
 */