/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Producto;
/*  4:   */ import com.asinfo.as2.entities.SerieProducto;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.NoResultException;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class SerieProductoDao
/* 13:   */   extends AbstractDaoAS2<SerieProducto>
/* 14:   */ {
/* 15:   */   public SerieProductoDao()
/* 16:   */   {
/* 17:32 */     super(SerieProducto.class);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List<SerieProducto> getListaSerieProducto(int idOrganizacion, Producto producto, List<String> codigos)
/* 21:   */   {
/* 22:45 */     StringBuilder sql = new StringBuilder();
/* 23:46 */     sql.append(" SELECT sp FROM SerieProducto sp");
/* 24:47 */     sql.append(" WHERE sp.idOrganizacion = :idOrganizacion");
/* 25:48 */     sql.append(" AND sp.producto = :producto");
/* 26:49 */     sql.append(" AND sp.codigo in(:codigos)");
/* 27:   */     
/* 28:51 */     Query query = this.em.createQuery(sql.toString());
/* 29:52 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 30:53 */     query.setParameter("producto", producto);
/* 31:54 */     query.setParameter("codigos", codigos);
/* 32:   */     
/* 33:56 */     return query.getResultList();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public SerieProducto buscarPorId(Object idSerieProducto)
/* 37:   */   {
/* 38:60 */     Query query = this.em.createNamedQuery("SerieProducto.buscarPorId", SerieProducto.class);
/* 39:61 */     query.setParameter("idSerieProducto", idSerieProducto);
/* 40:   */     
/* 41:63 */     SerieProducto serie = (SerieProducto)query.getSingleResult();
/* 42:64 */     this.em.detach(serie);
/* 43:65 */     return serie;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public SerieProducto buscarPorCodigo(int idOrganizacion, Producto producto, String codigo)
/* 47:   */   {
/* 48:76 */     Query query = this.em.createNamedQuery("SerieProducto.buscarPorCodigo", SerieProducto.class);
/* 49:77 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 50:78 */     query.setParameter("producto", producto);
/* 51:79 */     query.setParameter("codigo", codigo);
/* 52:   */     try
/* 53:   */     {
/* 54:82 */       SerieProducto serie = (SerieProducto)query.getSingleResult();
/* 55:83 */       this.em.detach(serie);
/* 56:84 */       return serie;
/* 57:   */     }
/* 58:   */     catch (NoResultException e) {}
/* 59:86 */     return null;
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.SerieProductoDao
 * JD-Core Version:    0.7.0.1
 */