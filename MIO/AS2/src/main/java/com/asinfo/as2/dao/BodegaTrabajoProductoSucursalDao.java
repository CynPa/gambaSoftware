/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Bodega;
/*  4:   */ import com.asinfo.as2.entities.BodegaTrabajoProductoSucursal;
/*  5:   */ import com.asinfo.as2.entities.Producto;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class BodegaTrabajoProductoSucursalDao
/* 13:   */   extends AbstractDaoAS2<BodegaTrabajoProductoSucursal>
/* 14:   */ {
/* 15:   */   public BodegaTrabajoProductoSucursalDao()
/* 16:   */   {
/* 17:31 */     super(BodegaTrabajoProductoSucursal.class);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List<Bodega> obtenerBodegaTrabajoProducto(Producto producto, Integer idSucursal)
/* 21:   */   {
/* 22:36 */     StringBuilder sqll = new StringBuilder();
/* 23:37 */     sqll.append(" SELECT bt ");
/* 24:38 */     sqll.append(" FROM BodegaTrabajoProductoSucursal btps ");
/* 25:39 */     sqll.append(" INNER JOIN btps.bodegaTrabajo bt ");
/* 26:40 */     sqll.append(" INNER JOIN btps.producto p ");
/* 27:41 */     sqll.append(" INNER JOIN btps.sucursal s ");
/* 28:42 */     sqll.append(" WHERE p.idProducto=:idProducto ");
/* 29:43 */     if (idSucursal != null) {
/* 30:44 */       sqll.append(" AND s.idSucursal =:idSucursal ");
/* 31:   */     }
/* 32:47 */     Query query = this.em.createQuery(sqll.toString());
/* 33:48 */     query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/* 34:49 */     if (idSucursal != null) {
/* 35:50 */       query.setParameter("idSucursal", idSucursal);
/* 36:   */     }
/* 37:52 */     return query.getResultList();
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.BodegaTrabajoProductoSucursalDao
 * JD-Core Version:    0.7.0.1
 */