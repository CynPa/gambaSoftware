/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReportePedidoProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Documento;
/*   6:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
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
/*  23:    */ public class ReportePedidoProveedorBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @EJB
/*  28:    */   private ServicioReportePedidoProveedor servicioReportePedidoProveedor;
/*  29:    */   private PedidoProveedor pedidoProveedor;
/*  30: 51 */   private static String COMPILE_FILE_NAME = "reportePedidoProveedor";
/*  31: 53 */   public static final String[] fields = { "f_nombreComercial", "f_direccionEmpresa", "f_identificacion", "f_fechaPedido", "f_fechaEntrega", "f_cantidadProducto", "f_codigoProducto", "f_nombreProducto", "f_precioProducto", "f_subTotalPedido", "f_descuentoPedido", "f_impuestoTotal", "f_telefonoEmpresa", "f_descripcionPedido", "f_numero", "f_unidad", "f_dimension1", "f_dimension2", "f_dimension3", "f_descripcion", "f_usuarioCambioEstado", "f_fechaCambioEstado", "f_dimension4", "f_dimension5", "f_codigoAlternoProducto", "f_codigoProveedor", "f_codigoAlternoProveedor", "f_estado", "f_numeroPedidoProveedor", "f_incoterm", "f_payment", "f_project", "f_agent", "f_delivery", "f_condicionPago", "f_productoNombreAlterno", "f_bodega", "f_proyecto", "f_estado", "f_referencia", "f_email", "f_telefonoRespuesta", "f_emailRespuesta", "f_responsable" };
/*  32:    */   
/*  33:    */   protected JRDataSource getJRDataSource()
/*  34:    */   {
/*  35: 69 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  36: 70 */     JRDataSource ds = null;
/*  37:    */     try
/*  38:    */     {
/*  39: 72 */       listaDatosReporte = this.servicioReportePedidoProveedor.getReportePedidoProveedor(getPedidoProveedor().getIdPedidoProveedor());
/*  40: 73 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  41:    */     }
/*  42:    */     catch (Exception e)
/*  43:    */     {
/*  44: 75 */       LOG.info("Error " + e);
/*  45: 76 */       e.printStackTrace();
/*  46:    */     }
/*  47: 78 */     return ds;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected String getCompileFileName()
/*  51:    */   {
/*  52: 89 */     COMPILE_FILE_NAME = this.pedidoProveedor.getDocumento().getReporte();
/*  53: 90 */     return COMPILE_FILE_NAME;
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected Map<String, Object> getReportParameters()
/*  57:    */   {
/*  58:102 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  59:103 */     reportParameters.put("ReportTitle", "Pedido de Proveedor");
/*  60:104 */     reportParameters.put("FechaPedido", "Fecha pedido:");
/*  61:105 */     reportParameters.put("CondicionPago", "Condicion de Pago:");
/*  62:106 */     reportParameters.put("NumeroCuotas", "Numero de Cuotas:");
/*  63:107 */     reportParameters.put("Subtotal", "Subtotal");
/*  64:108 */     reportParameters.put("Descuento", "Descuento");
/*  65:109 */     reportParameters.put("SubtotalDescuento", "Subtotal - Descuento");
/*  66:110 */     reportParameters.put("Impuesto", "Impuesto");
/*  67:111 */     reportParameters.put("Total", "Total");
/*  68:112 */     reportParameters.put("ClienteProveedor", "Proveedor:");
/*  69:113 */     reportParameters.put("nombreUsuario", getPedidoProveedor().getUsuarioCreacion().trim());
/*  70:114 */     reportParameters.put("p_sucursal", Integer.valueOf(getPedidoProveedor().getSucursal().getIdSucursal()));
/*  71:115 */     return reportParameters;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String execute()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:125 */       super.prepareReport();
/*  79:    */     }
/*  80:    */     catch (JRException e)
/*  81:    */     {
/*  82:127 */       LOG.info("Error JRException");
/*  83:128 */       e.printStackTrace();
/*  84:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  85:    */     }
/*  86:    */     catch (IOException e)
/*  87:    */     {
/*  88:131 */       LOG.info("Error IOException");
/*  89:132 */       e.printStackTrace();
/*  90:133 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  91:    */     }
/*  92:135 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public PedidoProveedor getPedidoProveedor()
/*  96:    */   {
/*  97:146 */     if (this.pedidoProveedor == null) {
/*  98:147 */       this.pedidoProveedor = new PedidoProveedor();
/*  99:    */     }
/* 100:149 */     return this.pedidoProveedor;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 104:    */   {
/* 105:159 */     this.pedidoProveedor = pedidoProveedor;
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReportePedidoProveedorBean
 * JD-Core Version:    0.7.0.1
 */