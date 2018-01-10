/*  1:   */ package com.asinfo.as2.inventario.reportes.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.procesos.servicio.ServicioNotaCreditoProveedor;
/*  4:   */ import com.asinfo.as2.controller.LanguageController;
/*  5:   */ import com.asinfo.as2.entities.FacturaProveedor;
/*  6:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  7:   */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  8:   */ import java.io.IOException;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.Map;
/* 12:   */ import javax.ejb.EJB;
/* 13:   */ import javax.faces.bean.ManagedBean;
/* 14:   */ import javax.faces.bean.ViewScoped;
/* 15:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 16:   */ import net.sf.jasperreports.engine.JRException;
/* 17:   */ import org.apache.log4j.Logger;
/* 18:   */ 
/* 19:   */ @ManagedBean
/* 20:   */ @ViewScoped
/* 21:   */ public class ReporteDevolucionProveedorBean
/* 22:   */   extends AbstractBaseReportBean
/* 23:   */ {
/* 24:   */   private static final long serialVersionUID = 1L;
/* 25:   */   @EJB
/* 26:   */   private ServicioNotaCreditoProveedor servicioNotaCreditoProveedor;
/* 27:   */   private FacturaProveedor devolucionProveedor;
/* 28:   */   
/* 29:   */   protected JRDataSource getJRDataSource()
/* 30:   */   {
/* 31:37 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 32:38 */     JRDataSource ds = null;
/* 33:   */     try
/* 34:   */     {
/* 35:41 */       listaDatosReporte = this.servicioNotaCreditoProveedor.getReporteNotaCreditoProveedor(this.devolucionProveedor.getIdFacturaProveedor());
/* 36:   */       
/* 37:43 */       String[] fields = { "nombreFiscal", "direccionEmpresa", "identificacion", "fechaFactura", "cantidadProducto", "codigoProducto", "nombreProducto", "precioProducto", "subTotalFactura", "descuentoFactura", "impuestoTotal", "telefonoEmpresa", "descripcionFactura", "anioFecha", "mesFecha", "diaFecha", "descripcionProducto", "codigoCliente", "ciudadCliente", "unidad", "peso", "codigoAlterno", "codigoComercial", "codigoBarras", "numeroFactura", "nombreComercial", "numeroFacturaPadre", "motivoNotaCredito", "bodega", "numeroFacturaSRI" };
/* 38:   */       
/* 39:   */ 
/* 40:   */ 
/* 41:   */ 
/* 42:   */ 
/* 43:49 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 44:   */     }
/* 45:   */     catch (Exception e)
/* 46:   */     {
/* 47:51 */       LOG.info("Error ", e);
/* 48:52 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 49:   */     }
/* 50:54 */     return ds;
/* 51:   */   }
/* 52:   */   
/* 53:   */   protected String getCompileFileName()
/* 54:   */   {
/* 55:59 */     return "reporteDevolucionProveedor";
/* 56:   */   }
/* 57:   */   
/* 58:   */   protected Map<String, Object> getReportParameters()
/* 59:   */   {
/* 60:65 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 61:66 */     reportParameters.put("ReportTitle", "Devolucion Proveedor");
/* 62:67 */     return reportParameters;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public String execute()
/* 66:   */   {
/* 67:   */     try
/* 68:   */     {
/* 69:73 */       super.prepareReport();
/* 70:   */     }
/* 71:   */     catch (JRException e)
/* 72:   */     {
/* 73:75 */       LOG.info("Error JRException");
/* 74:76 */       e.printStackTrace();
/* 75:77 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 76:   */     }
/* 77:   */     catch (IOException e)
/* 78:   */     {
/* 79:79 */       LOG.info("Error IOException");
/* 80:80 */       e.printStackTrace();
/* 81:81 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 82:   */     }
/* 83:83 */     return "";
/* 84:   */   }
/* 85:   */   
/* 86:   */   public FacturaProveedor getDevolucionProveedor()
/* 87:   */   {
/* 88:90 */     return this.devolucionProveedor;
/* 89:   */   }
/* 90:   */   
/* 91:   */   public void setDevolucionProveedor(FacturaProveedor devolucionProveedor)
/* 92:   */   {
/* 93:98 */     this.devolucionProveedor = devolucionProveedor;
/* 94:   */   }
/* 95:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteDevolucionProveedorBean
 * JD-Core Version:    0.7.0.1
 */