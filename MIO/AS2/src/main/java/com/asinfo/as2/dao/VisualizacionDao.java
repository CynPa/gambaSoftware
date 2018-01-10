/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleVisualizacion;
/*  4:   */ import com.asinfo.as2.entities.Visualizacion;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ import javax.persistence.TypedQuery;
/* 10:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 11:   */ import javax.persistence.criteria.CriteriaQuery;
/* 12:   */ import javax.persistence.criteria.JoinType;
/* 13:   */ import javax.persistence.criteria.Root;
/* 14:   */ 
/* 15:   */ @Stateless
/* 16:   */ public class VisualizacionDao
/* 17:   */   extends AbstractDaoAS2<Visualizacion>
/* 18:   */ {
/* 19:   */   public VisualizacionDao()
/* 20:   */   {
/* 21:34 */     super(Visualizacion.class);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Visualizacion cargarDetalle(int id)
/* 25:   */   {
/* 26:41 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 27:42 */     CriteriaQuery<Visualizacion> cq = cb.createQuery(Visualizacion.class);
/* 28:   */     
/* 29:44 */     Root<Visualizacion> from = cq.from(Visualizacion.class);
/* 30:45 */     cq.where(cb.equal(from.get("idVisualizacion"), Integer.valueOf(id)));
/* 31:   */     
/* 32:47 */     Visualizacion visualizacion = (Visualizacion)this.em.createQuery(cq.select(from)).getSingleResult();
/* 33:   */     
/* 34:   */ 
/* 35:50 */     CriteriaQuery<DetalleVisualizacion> cqDetalle = cb.createQuery(DetalleVisualizacion.class);
/* 36:51 */     Root<DetalleVisualizacion> fromDetalle = cqDetalle.from(DetalleVisualizacion.class);
/* 37:52 */     fromDetalle.fetch("categoriaProducto", JoinType.LEFT);
/* 38:53 */     fromDetalle.fetch("subcategoriaProducto", JoinType.LEFT);
/* 39:   */     
/* 40:55 */     cqDetalle.where(cb.equal(fromDetalle.join("visualizacion"), visualizacion));
/* 41:56 */     CriteriaQuery<DetalleVisualizacion> selectVisualizacion = cqDetalle.select(fromDetalle);
/* 42:   */     
/* 43:58 */     List<DetalleVisualizacion> listaDetalleVisualizacion = this.em.createQuery(selectVisualizacion).getResultList();
/* 44:59 */     visualizacion.setListaDetalleVisualizacion(listaDetalleVisualizacion);
/* 45:   */     
/* 46:61 */     return visualizacion;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public List<DetalleVisualizacion> getListaDetalleVisualizacion(int idVisualizacion)
/* 50:   */   {
/* 51:67 */     StringBuilder sbSQL = new StringBuilder();
/* 52:68 */     sbSQL.append(" SELECT dvu FROM DetalleVisualizacion dvu ");
/* 53:69 */     sbSQL.append(" INNER JOIN dvu.visualizacion vu ");
/* 54:70 */     sbSQL.append(" INNER JOIN FETCH dvu.categoriaProducto cp ");
/* 55:71 */     sbSQL.append(" LEFT JOIN FETCH dvu.subcategoriaProducto scp ");
/* 56:72 */     sbSQL.append(" WHERE vu.idVisualizacion = :idVisualizacion ");
/* 57:73 */     sbSQL.append(" AND vu.activo = true ");
/* 58:   */     
/* 59:75 */     Query query = this.em.createQuery(sbSQL.toString());
/* 60:76 */     query.setParameter("idVisualizacion", Integer.valueOf(idVisualizacion));
/* 61:77 */     return query.getResultList();
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.VisualizacionDao
 * JD-Core Version:    0.7.0.1
 */