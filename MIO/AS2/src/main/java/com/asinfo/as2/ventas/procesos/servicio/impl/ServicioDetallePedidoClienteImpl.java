/*  1:   */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.DetallePedidoClienteDao;
/*  4:   */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*  5:   */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDetallePedidoCliente;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.EJB;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class ServicioDetallePedidoClienteImpl
/* 12:   */   implements ServicioDetallePedidoCliente
/* 13:   */ {
/* 14:   */   @EJB
/* 15:   */   DetallePedidoClienteDao detallePedidoClienteDao;
/* 16:   */   
/* 17:   */   public List<DetallePedidoCliente> buscarDetallePedidoClientePorProducto(int idProducto, int idOrganizacion)
/* 18:   */   {
/* 19:22 */     return this.detallePedidoClienteDao.buscarDetallePedidoClientePorProducto(idProducto, idOrganizacion);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void guardar(DetallePedidoCliente detallePedidoCliente)
/* 23:   */   {
/* 24:28 */     this.detallePedidoClienteDao.guardar(detallePedidoCliente);
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioDetallePedidoClienteImpl
 * JD-Core Version:    0.7.0.1
 */