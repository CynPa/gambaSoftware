/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Documento;
/*   6:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.RequestScoped;
/*  17:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  18:    */ import net.sf.jasperreports.engine.JRException;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @RequestScoped
/*  23:    */ public class ReporteFacturaProveedorBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -6450022697432262754L;
/*  27:    */   @EJB
/*  28:    */   private ServicioReporteCompra servicioReporteCompra;
/*  29:    */   private FacturaProveedor facturaProveedor;
/*  30: 47 */   private String COMPILE_FILE_NAME = "reporteFacturaProveedor";
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 57 */     List listaDatosReporte = new ArrayList();
/*  35: 58 */     JRDataSource ds = null;
/*  36:    */     try
/*  37:    */     {
/*  38: 60 */       listaDatosReporte = this.servicioReporteCompra.getReporteFacturaProveedor(this.facturaProveedor.getIdFacturaProveedor());
/*  39:    */       
/*  40: 62 */       String[] fields = { "f_nombreComercial", "f_direccionEmpresa", "f_identificacion", "f_fechaFactura", "f_cantidadProducto", "f_codigoProducto", "f_nombreProducto", "f_precioProducto", "f_subTotalFactura", "f_descuentoFactura", "f_impuestoTotal", "f_telefonoEmpresa", "f_descripcionFactura", "f_anioFecha", "f_mesFecha", "f_diaFecha", "f_numero", "f_establecimientoSRI", "f_puntoSRI", "f_numeroSRI", "f_numeroRecepcion", "f_numeroAsiento", "f_establecimientoRetencion", "f_puntoEmisionRetencion", "f_numeroRetencion", "f_proyecto", "f_baseImponibleTarifaCero", "f_baseImponibleDiferenteCero", "f_ciudad", "f_autorizacionRetencion", "f_numeroAsientoRetencion", "f_usuarioCreacion", "f_puertoEmbarque", "f_puertoLlegada", "f_informacionTransporte", "f_fechaEmbarque", "f_fechaCierre", "f_fechaLlegada", "f_numeroDUI", "f_MedioTransporteEnum", "f_responsable" };
/*  41:    */       
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48: 70 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  49:    */     }
/*  50:    */     catch (Exception e)
/*  51:    */     {
/*  52: 72 */       e.printStackTrace();
/*  53:    */     }
/*  54: 74 */     return ds;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected String getCompileFileName()
/*  58:    */   {
/*  59: 85 */     if (!this.facturaProveedor.getDocumento().getReporte().isEmpty()) {
/*  60: 86 */       this.COMPILE_FILE_NAME = this.facturaProveedor.getDocumento().getReporte();
/*  61:    */     }
/*  62: 89 */     return this.COMPILE_FILE_NAME;
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected Map<String, Object> getReportParameters()
/*  66:    */   {
/*  67:100 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  68:101 */     reportParameters.put("ReportTitle", "Titulo del reporte");
/*  69:102 */     reportParameters.put("p_estado", getFacturaProveedor().getEstado().getNombre());
/*  70:103 */     return reportParameters;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String execute()
/*  74:    */   {
/*  75:110 */     if (getFacturaProveedor().getId() == 0) {
/*  76:111 */       addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  77:    */     } else {
/*  78:    */       try
/*  79:    */       {
/*  80:115 */         super.prepareReport();
/*  81:    */       }
/*  82:    */       catch (JRException e)
/*  83:    */       {
/*  84:117 */         LOG.info("Error JRException");
/*  85:118 */         e.printStackTrace();
/*  86:119 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  87:    */       }
/*  88:    */       catch (IOException e)
/*  89:    */       {
/*  90:121 */         LOG.info("Error IOException");
/*  91:122 */         e.printStackTrace();
/*  92:123 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  93:    */       }
/*  94:    */     }
/*  95:127 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public FacturaProveedor getFacturaProveedor()
/*  99:    */   {
/* 100:131 */     if (this.facturaProveedor == null) {
/* 101:132 */       this.facturaProveedor = new FacturaProveedor();
/* 102:    */     }
/* 103:134 */     return this.facturaProveedor;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/* 107:    */   {
/* 108:138 */     this.facturaProveedor = facturaProveedor;
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReporteFacturaProveedorBean
 * JD-Core Version:    0.7.0.1
 */