/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ProductoUltimaCompra;
/*  4:   */ import java.util.List;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ import javax.persistence.EntityManager;
/*  7:   */ import javax.persistence.Query;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class ProductoUltimaCompraDao
/* 11:   */   extends AbstractDaoAS2<ProductoUltimaCompra>
/* 12:   */ {
/* 13:   */   public ProductoUltimaCompraDao()
/* 14:   */   {
/* 15:30 */     super(ProductoUltimaCompra.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ProductoUltimaCompra obtenerPrecioUltimaCompra(int idEmpresa, int idProducto)
/* 19:   */   {
/* 20:35 */     StringBuilder sql = new StringBuilder();
/* 21:36 */     sql.append(" SELECT puc FROM ProductoUltimaCompra puc ");
/* 22:37 */     sql.append(" INNER JOIN puc.producto p ");
/* 23:38 */     sql.append(" INNER JOIN puc.empresa e ");
/* 24:39 */     sql.append(" WHERE p.idProducto=:idProducto ");
/* 25:40 */     sql.append(" AND e.idEmpresa=:idEmpresa");
/* 26:   */     
/* 27:42 */     Query query = this.em.createQuery(sql.toString());
/* 28:43 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 29:44 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/* 30:   */     
/* 31:46 */     ProductoUltimaCompra productoUltimaCompra = query.getResultList().isEmpty() ? null : (ProductoUltimaCompra)query.getResultList().get(0);
/* 32:   */     
/* 33:48 */     return productoUltimaCompra;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public List<ProductoUltimaCompra> obtenerPrecioUltimaCompraPorProveedor(int idEmpresa)
/* 37:   */   {
/* 38:53 */     StringBuilder sql = new StringBuilder();
/* 39:54 */     sql.append(" SELECT puc FROM ProductoUltimaCompra puc ");
/* 40:55 */     sql.append(" INNER JOIN FETCH puc.empresa e ");
/* 41:56 */     sql.append(" INNER JOIN FETCH puc.producto p ");
/* 42:57 */     sql.append(" INNER JOIN FETCH p.subcategoriaProducto s");
/* 43:58 */     sql.append(" INNER JOIN FETCH s.categoriaProducto c");
/* 44:59 */     sql.append(" WHERE e.idEmpresa=:idEmpresa");
/* 45:   */     
/* 46:61 */     Query query = this.em.createQuery(sql.toString());
/* 47:62 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 48:   */     
/* 49:64 */     return query.getResultList();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public List<ProductoUltimaCompra> obtenerProveedoresProductoEspecifico(int idProducto)
/* 53:   */   {
/* 54:70 */     StringBuilder sql = new StringBuilder();
/* 55:71 */     sql.append("SELECT puc");
/* 56:72 */     sql.append(" FROM ProductoUltimaCompra puc");
/* 57:73 */     sql.append(" INNER JOIN FETCH puc.producto pr");
/* 58:74 */     sql.append(" INNER JOIN FETCH puc.empresa e");
/* 59:75 */     sql.append(" INNER JOIN FETCH e.proveedor p");
/* 60:76 */     sql.append(" INNER JOIN FETCH p.condicionPago cp");
/* 61:77 */     sql.append(" WHERE pr.idProducto = :idProducto");
/* 62:   */     
/* 63:79 */     Query query = this.em.createQuery(sql.toString());
/* 64:80 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/* 65:81 */     return query.getResultList();
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ProductoUltimaCompraDao
 * JD-Core Version:    0.7.0.1
 */