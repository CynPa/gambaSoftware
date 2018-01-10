/*  1:   */ package com.asinfo.as2.dao.reportes.inventario;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.InventarioProducto;
/*  5:   */ import java.util.Date;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ReporteInventarioProductoDao
/* 13:   */   extends AbstractDaoAS2<InventarioProducto>
/* 14:   */ {
/* 15:   */   public ReporteInventarioProductoDao()
/* 16:   */   {
/* 17:35 */     super(InventarioProducto.class);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List obtenerMovimientos(int idProducto, Date fechaDesde, Date fechaHasta)
/* 21:   */   {
/* 22:40 */     Query query = this.em.createQuery("SELECT d.documento.nombre,d.producto.nombreComercial FROM InventarioProducto d LEFT JOIN FETCH d.producto WHERE d.fecha BETWEEN :fechaDesde AND :fechaHasta AND d.producto.idProducto=:idProducto ORDER BY d.fecha ASC, d.operacion DESC, d.orden, d.numeroDocumento ASC");
/* 23:   */     
/* 24:   */ 
/* 25:   */ 
/* 26:44 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/* 27:45 */     query.setParameter("fechaDesde", fechaDesde);
/* 28:46 */     query.setParameter("fechaHasta", fechaHasta);
/* 29:   */     
/* 30:48 */     return query.getResultList();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List getReporteListaMaterial(int idProducto)
/* 34:   */   {
/* 35:53 */     Query query = this.em.createQuery("SELECT m.codigo,m.nombre,u.nombre,pm.cantidad,COALESCE(pm.descripcion,'') FROM ProductoMaterial pm INNER JOIN pm.producto p INNER JOIN pm.material m LEFT JOIN m.unidad u WHERE p.idProducto=:idProducto");
/* 36:   */     
/* 37:55 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/* 38:   */     
/* 39:57 */     return query.getResultList();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public List getReporteListaMaterialSubproductos(int idProducto)
/* 43:   */   {
/* 44:62 */     Query query = this.em.createQuery("SELECT m.codigo,m.nombre,sp.porcentajeCosto,sp.porcentaje,sp.cantidad,sp.descripcion FROM SubProducto sp INNER JOIN sp.productoPadre p INNER JOIN sp.producto m WHERE p.idProducto=:idProducto");
/* 45:   */     
/* 46:64 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/* 47:   */     
/* 48:66 */     return query.getResultList();
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.inventario.ReporteInventarioProductoDao
 * JD-Core Version:    0.7.0.1
 */