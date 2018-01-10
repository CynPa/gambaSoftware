/*  1:   */ package com.asinfo.as2.dao.reportes.inventario;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.Bodega;
/*  5:   */ import com.asinfo.as2.entities.InventarioProducto;
/*  6:   */ import com.asinfo.as2.entities.Producto;
/*  7:   */ import com.asinfo.as2.entities.ProductoMaterial;
/*  8:   */ import java.util.Date;
/*  9:   */ import java.util.List;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ import javax.persistence.EntityManager;
/* 12:   */ import javax.persistence.Query;
/* 13:   */ 
/* 14:   */ @Stateless
/* 15:   */ public class ReporteProyeccionKidsDao
/* 16:   */   extends AbstractDaoAS2<InventarioProducto>
/* 17:   */ {
/* 18:   */   public ReporteProyeccionKidsDao()
/* 19:   */   {
/* 20:38 */     super(InventarioProducto.class);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public List<ProductoMaterial> getListaProductoMaterial(int idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta)
/* 24:   */   {
/* 25:58 */     StringBuilder sql = new StringBuilder();
/* 26:59 */     sql.append(" SELECT pm FROM ProductoMaterial pm");
/* 27:60 */     sql.append(" JOIN FETCH pm.producto");
/* 28:61 */     sql.append(" JOIN FETCH pm.material");
/* 29:62 */     sql.append(" ORDER BY pm.producto.nombre ASC");
/* 30:63 */     Query query = this.em.createQuery(sql.toString());
/* 31:64 */     return query.getResultList();
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.inventario.ReporteProyeccionKidsDao
 * JD-Core Version:    0.7.0.1
 */