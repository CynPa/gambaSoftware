/*  1:   */ package com.asinfo.as2.ws.compras.service.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*  4:   */ import com.asinfo.as2.entities.PedidoProveedor;
/*  5:   */ import com.asinfo.as2.entities.Producto;
/*  6:   */ import com.asinfo.as2.enumeraciones.Estado;
/*  7:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  8:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  9:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/* 10:   */ import com.asinfo.as2.ws.compras.service.ServicioPedidoProveedorWS;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.jws.WebService;
/* 13:   */ 
/* 14:   */ @WebService(endpointInterface="com.asinfo.as2.ws.compras.service.ServicioPedidoProveedorWS")
/* 15:   */ public class ServicioPedidoProveedorWSImpl
/* 16:   */   implements ServicioPedidoProveedorWS
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   private transient ServicioPedidoProveedor servicioPedidoProveedor;
/* 20:   */   @EJB
/* 21:   */   private transient ServicioProducto servicioProducto;
/* 22:   */   
/* 23:   */   public void aprobarPedidoPorProducto(Integer idOrganizacion, String numero, String nombreDocumento, String codigoProducto)
/* 24:   */     throws AS2Exception
/* 25:   */   {
/* 26:42 */     PedidoProveedor pedidoProveedor = this.servicioPedidoProveedor.buscarPorNumero(idOrganizacion, numero, nombreDocumento);
/* 27:44 */     if ((pedidoProveedor.getEstado() == Estado.ANULADO) || (pedidoProveedor.getEstado() == Estado.CERRADO)) {
/* 28:45 */       throw new AS2Exception("msg_proceso_erroneo numero=" + numero + " Estado=" + pedidoProveedor.getEstado().getNombre());
/* 29:   */     }
/* 30:48 */     Producto producto = null;
/* 31:   */     try
/* 32:   */     {
/* 33:50 */       producto = this.servicioProducto.buscarPorCodigo(codigoProducto, idOrganizacion.intValue(), null);
/* 34:   */     }
/* 35:   */     catch (ExcepcionAS2 E)
/* 36:   */     {
/* 37:52 */       throw new AS2Exception("msg_info_registro_no_encontrado", new String[] { "codigoProducto=" + codigoProducto });
/* 38:   */     }
/* 39:55 */     this.servicioPedidoProveedor.aprobarPorProducto(pedidoProveedor, producto);
/* 40:56 */     this.servicioPedidoProveedor.actualizarEstado(Integer.valueOf(pedidoProveedor.getId()), Estado.APROBADO);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void aprobarPedido(Integer idOrganizacion, String numero, String nombreDocumento)
/* 44:   */     throws AS2Exception
/* 45:   */   {
/* 46:61 */     PedidoProveedor pedidoProveedor = this.servicioPedidoProveedor.buscarPorNumero(idOrganizacion, numero, nombreDocumento);
/* 47:63 */     if ((pedidoProveedor.getEstado() == Estado.ANULADO) || (pedidoProveedor.getEstado() == Estado.CERRADO)) {
/* 48:64 */       throw new AS2Exception("msg_proceso_erroneo numero=" + numero + " Estado=" + pedidoProveedor.getEstado().getNombre());
/* 49:   */     }
/* 50:67 */     this.servicioPedidoProveedor.aprobarPorProducto(pedidoProveedor, null);
/* 51:68 */     this.servicioPedidoProveedor.actualizarEstado(Integer.valueOf(pedidoProveedor.getId()), Estado.APROBADO);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void cerrarPedido(Integer idOrganizacion, String numero, String nombreDocumento)
/* 55:   */     throws AS2Exception
/* 56:   */   {
/* 57:73 */     PedidoProveedor pedidoProveedor = this.servicioPedidoProveedor.buscarPorNumero(idOrganizacion, numero, nombreDocumento);
/* 58:74 */     this.servicioPedidoProveedor.actualizarEstado(Integer.valueOf(pedidoProveedor.getId()), Estado.CERRADO);
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.compras.service.impl.ServicioPedidoProveedorWSImpl
 * JD-Core Version:    0.7.0.1
 */