/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Atributo;
/*   5:    */ import com.asinfo.as2.entities.Documento;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.PedidoCliente;
/*   8:    */ import com.asinfo.as2.entities.ProductoAtributo;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProductoAtributo;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.util.RutaArchivo;
/*  13:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  14:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  15:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.RequestScoped;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import net.sf.jasperreports.engine.JRException;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @RequestScoped
/*  29:    */ public class ReportePedidoClienteBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -2223162039042164260L;
/*  33:    */   @EJB
/*  34:    */   private ServicioPedidoCliente servicioPedidoCliente;
/*  35:    */   @EJB
/*  36:    */   private ServicioProductoAtributo servicioProductoAtributo;
/*  37:    */   private PedidoCliente pedidoCliente;
/*  38: 58 */   public static String COMPILE_FILE_NAME = "reportePedidoCliente";
/*  39: 59 */   public static final String[] fields = { "nombreFiscal", "direccionCliente", "identificacionCliente", "fechaPedido", "cantidadProducto", "codigoProducto", "nombreProducto", "precioProducto", "subTotalPedido", "descuentoPedido", "impuestoTotal", "condicionPago", "numeroCuotas", "nota", "numero", "codigoZona", "nombreZona", "nombreComercial", "agenteComercial", "f_imagenProducto", "f_emailCliente", "f_emailVendedor", "f_contactoCliente", "f_telefonoCliente", "f_atributos", "f_transportista", "f_telefonoVendedor", "f_usuariocreacion", "descripcionProducto", "f_nombreProyecto", "f_estado", "f_contacto", "f_telefonoContacto", "f_emailContacto", "f_cantidadEmbalaje", "f_cantidadUnidadDespacho", "f_indicadorFijo", "f_nombreProductoNoComercial", "f_fechaDespacho", "f_referencia8", "f_empresaFinal", "monto_ice", "f_correoAgenteComercial", "f_identificacionCliente", "f_descuentoImpuesto", "f_unidad" };
/*  40:    */   
/*  41:    */   protected JRDataSource getJRDataSource()
/*  42:    */   {
/*  43: 75 */     List listaDatosReporte = new ArrayList();
/*  44: 76 */     JRDataSource ds = null;
/*  45:    */     try
/*  46:    */     {
/*  47: 78 */       listaDatosReporte = this.servicioPedidoCliente.getReportePedidoCliente(getPedidoCliente().getIdPedidoCliente());
/*  48: 80 */       for (Object object : listaDatosReporte)
/*  49:    */       {
/*  50: 81 */         Object[] objeto = (Object[])object;
/*  51: 82 */         int idProducto = Integer.parseInt(objeto[24].toString());
/*  52: 83 */         String atributos = " ";
/*  53: 84 */         for (ProductoAtributo pa : this.servicioProductoAtributo.obtenerPorProducto(idProducto)) {
/*  54: 85 */           if (pa.getAtributo().isIndicadorImpresion()) {
/*  55: 86 */             atributos = atributos.trim() + pa.getAtributo().getNombre() + ": " + pa.getValor() + "<br/>";
/*  56:    */           }
/*  57:    */         }
/*  58: 89 */         String cadenaTruncada = "";
/*  59: 90 */         if (atributos.length() > 5) {
/*  60: 91 */           cadenaTruncada = atributos.substring(0, atributos.length() - 5);
/*  61:    */         }
/*  62: 93 */         objeto[24] = cadenaTruncada;
/*  63:    */       }
/*  64: 96 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  65:    */     }
/*  66:    */     catch (ExcepcionAS2 e)
/*  67:    */     {
/*  68: 98 */       LOG.info("Error " + e);
/*  69: 99 */       e.printStackTrace();
/*  70:    */     }
/*  71:101 */     return ds;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected String getCompileFileName()
/*  75:    */   {
/*  76:111 */     if (!this.pedidoCliente.getDocumento().getReporte().isEmpty()) {
/*  77:112 */       COMPILE_FILE_NAME = this.pedidoCliente.getDocumento().getReporte();
/*  78:    */     }
/*  79:114 */     return COMPILE_FILE_NAME;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected Map<String, Object> getReportParameters()
/*  83:    */   {
/*  84:124 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  85:125 */     reportParameters.put("ReportTitle", "Pedido de Cliente");
/*  86:126 */     reportParameters.put("FechaPedido", "Fecha pedido:");
/*  87:127 */     reportParameters.put("CondicionPago", "Condicion de Pago:");
/*  88:128 */     reportParameters.put("NumeroCuotas", "Numero de Cuotas:");
/*  89:129 */     reportParameters.put("Subtotal", "Subtotal");
/*  90:130 */     reportParameters.put("Descuento", "Descuento");
/*  91:131 */     reportParameters.put("SubtotalDescuento", "Subtotal - Descuento");
/*  92:132 */     reportParameters.put("Impuesto", "Impuesto");
/*  93:133 */     reportParameters.put("Total", "Total");
/*  94:134 */     reportParameters.put("ClienteProveedor", "Cliente:");
/*  95:135 */     reportParameters.put("DescuentoImpuesto", "Descuento Impuesto");
/*  96:136 */     reportParameters.put("directorioImagenesProducto", RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "producto"));
/*  97:    */     
/*  98:138 */     return reportParameters;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String execute()
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:148 */       super.prepareReport();
/* 106:    */     }
/* 107:    */     catch (JRException e)
/* 108:    */     {
/* 109:150 */       LOG.info("Error JRException");
/* 110:151 */       e.printStackTrace();
/* 111:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 112:    */     }
/* 113:    */     catch (IOException e)
/* 114:    */     {
/* 115:154 */       LOG.info("Error IOException");
/* 116:155 */       e.printStackTrace();
/* 117:156 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 118:    */     }
/* 119:158 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public PedidoCliente getPedidoCliente()
/* 123:    */   {
/* 124:169 */     if (this.pedidoCliente == null) {
/* 125:170 */       this.pedidoCliente = new PedidoCliente();
/* 126:    */     }
/* 127:172 */     return this.pedidoCliente;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setPedidoCliente(PedidoCliente pedidoCliente)
/* 131:    */   {
/* 132:182 */     this.pedidoCliente = pedidoCliente;
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePedidoClienteBean
 * JD-Core Version:    0.7.0.1
 */