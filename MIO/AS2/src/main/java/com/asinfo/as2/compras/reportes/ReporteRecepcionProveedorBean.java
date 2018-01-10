/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteRecepcionProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.ProductoAtributo;
/*  10:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProductoAtributo;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import net.sf.jasperreports.engine.JRException;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ public class ReporteRecepcionProveedorBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @EJB
/*  30:    */   private ServicioReporteRecepcionProveedor reporteRecepcionProveedor;
/*  31:    */   @EJB
/*  32:    */   private ServicioEmpresa servicioEmpresa;
/*  33:    */   private RecepcionProveedor recepcionProveedor;
/*  34:    */   @EJB
/*  35:    */   private ServicioProductoAtributo servicioProductoAtributo;
/*  36: 47 */   private final String COMPILE_FILE_NAME = "reporteRecepcionProveedor";
/*  37:    */   
/*  38:    */   protected JRDataSource getJRDataSource()
/*  39:    */   {
/*  40: 52 */     List listaDatosReporte = new ArrayList();
/*  41: 53 */     JRDataSource ds = null;
/*  42:    */     try
/*  43:    */     {
/*  44: 56 */       listaDatosReporte = this.reporteRecepcionProveedor.getReporteRecepcionProveedor(getRecepcionProveedor().getId());
/*  45:    */       
/*  46: 58 */       String[] fields = { "f_numero", "f_numero_pedido", "f_fecha", "f_descripcionTransferencia", "f_nombreComercial", "f_nombreFiscal", "f_identificacionEmpresa", "f_direccionEmpresa", "f_codigoProducto", "f_nombreProducto", "f_unidad", "f_cantidadProducto", "f_bodega", "f_descripcion", "f_lote", "f_numeroFactura", "f_costoLinea", "f_usuarioCreacion", "f_categoriaEmpresa", "f_usuarioPedido", "f_codigoAlterno", "f_unidadInformativa", "f_cantidadInformativa", "f_valor", "f_responsable" };
/*  47: 63 */       for (Object object : listaDatosReporte)
/*  48:    */       {
/*  49: 64 */         Object[] objeto = (Object[])object;
/*  50: 65 */         int idProducto = Integer.parseInt(objeto[23].toString());
/*  51: 66 */         String atributos = " ";
/*  52: 67 */         for (ProductoAtributo pa : this.servicioProductoAtributo.obtenerPorProducto(idProducto)) {
/*  53: 68 */           atributos = atributos.trim() + "\n -" + pa.getAtributo().getNombre() + ": " + pa.getValor() + " ";
/*  54:    */         }
/*  55: 70 */         objeto[23] = atributos;
/*  56:    */       }
/*  57: 73 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61: 75 */       e.printStackTrace();
/*  62:    */     }
/*  63: 77 */     return ds;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected String getCompileFileName()
/*  67:    */   {
/*  68: 82 */     return "reporteRecepcionProveedor";
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected Map<String, Object> getReportParameters()
/*  72:    */   {
/*  73: 88 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  74: 89 */     reportParameters.put("ReportTitle", "Recepción Proveedor");
/*  75:    */     
/*  76: 91 */     Empresa em = this.servicioEmpresa.cargarDetalle(getRecepcionProveedor().getEmpresa());
/*  77:    */     
/*  78: 93 */     String exterior = "EXTERIOR";
/*  79: 94 */     int i = em.getCategoriaEmpresa().getNombre().indexOf(exterior.toUpperCase());
/*  80: 95 */     reportParameters.put("p_local", Boolean.valueOf(i != -1));
/*  81:    */     
/*  82: 97 */     return reportParameters;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String execute()
/*  86:    */   {
/*  87:104 */     if (getRecepcionProveedor().getId() == 0) {
/*  88:105 */       addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  89:    */     } else {
/*  90:    */       try
/*  91:    */       {
/*  92:109 */         super.prepareReport();
/*  93:    */       }
/*  94:    */       catch (JRException e)
/*  95:    */       {
/*  96:112 */         LOG.info("Error JRException");
/*  97:113 */         e.printStackTrace();
/*  98:114 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  99:    */       }
/* 100:    */       catch (IOException e)
/* 101:    */       {
/* 102:116 */         LOG.info("Error IOException");
/* 103:117 */         e.printStackTrace();
/* 104:118 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 105:    */       }
/* 106:    */     }
/* 107:121 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public RecepcionProveedor getRecepcionProveedor()
/* 111:    */   {
/* 112:126 */     if (this.recepcionProveedor == null) {
/* 113:127 */       this.recepcionProveedor = new RecepcionProveedor();
/* 114:    */     }
/* 115:129 */     return this.recepcionProveedor;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setRecepcionProveedor(RecepcionProveedor recepcionProveedor)
/* 119:    */   {
/* 120:133 */     this.recepcionProveedor = recepcionProveedor;
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReporteRecepcionProveedorBean
 * JD-Core Version:    0.7.0.1
 */