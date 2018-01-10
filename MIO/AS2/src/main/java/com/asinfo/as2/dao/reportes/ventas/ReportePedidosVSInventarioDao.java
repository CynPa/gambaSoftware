/*  1:   */ package com.asinfo.as2.dao.reportes.ventas;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.PedidoCliente;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.Query;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ReportePedidosVSInventarioDao
/* 14:   */   extends AbstractDaoAS2<PedidoCliente>
/* 15:   */ {
/* 16:   */   public ReportePedidosVSInventarioDao()
/* 17:   */   {
/* 18:37 */     super(PedidoCliente.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public List getPedidosVSInventario(Date fecha)
/* 22:   */   {
/* 23:43 */     List lista = new ArrayList();
/* 24:44 */     String sql = "SELECT dpc.producto.nombre,SUM(dpc.cantidad),dpc.cantidad*0,dpc.producto.idProducto FROM DetallePedidoCliente dpc\tINNER JOIN dpc.pedidoCliente pc\tWHERE pc.fecha<=:fecha\tGROUP BY dpc.producto.idProducto,dpc.producto.nombre,dpc.cantidad ORDER BY dpc.producto.idProducto";
/* 25:   */     
/* 26:   */ 
/* 27:   */ 
/* 28:48 */     Query query = this.em.createQuery(sql);
/* 29:49 */     query.setParameter("fecha", fecha);
/* 30:50 */     lista.addAll(query.getResultList());
/* 31:   */     
/* 32:52 */     sql = "SELECT sp.producto.nombre,sp.saldo*0,SUM(sp.saldo),sp.producto.idProducto FROM SaldoProducto  sp WHERE sp.fecha <=:fecha\tGROUP BY sp.producto.idProducto,sp.producto.nombre,sp.saldo ORDER BY sp.producto.idProducto";
/* 33:   */     
/* 34:54 */     query = this.em.createQuery(sql);
/* 35:55 */     query.setParameter("fecha", fecha);
/* 36:56 */     lista.addAll(query.getResultList());
/* 37:57 */     return lista;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.ventas.ReportePedidosVSInventarioDao
 * JD-Core Version:    0.7.0.1
 */