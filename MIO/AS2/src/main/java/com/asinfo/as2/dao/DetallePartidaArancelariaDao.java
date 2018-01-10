/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetallePartidaArancelaria;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.TypedQuery;
/*  9:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 10:   */ import javax.persistence.criteria.CriteriaQuery;
/* 11:   */ import javax.persistence.criteria.Expression;
/* 12:   */ import javax.persistence.criteria.Predicate;
/* 13:   */ import javax.persistence.criteria.Root;
/* 14:   */ 
/* 15:   */ @Stateless
/* 16:   */ public class DetallePartidaArancelariaDao
/* 17:   */   extends AbstractDaoAS2<DetallePartidaArancelaria>
/* 18:   */ {
/* 19:   */   public DetallePartidaArancelariaDao()
/* 20:   */   {
/* 21:40 */     super(DetallePartidaArancelaria.class);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public List<DetallePartidaArancelaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 25:   */   {
/* 26:51 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 27:52 */     CriteriaQuery<DetallePartidaArancelaria> criteriaQuery = criteriaBuilder.createQuery(DetallePartidaArancelaria.class);
/* 28:53 */     Root<DetallePartidaArancelaria> from = criteriaQuery.from(DetallePartidaArancelaria.class);
/* 29:   */     
/* 30:55 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 31:56 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 32:   */     
/* 33:58 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 34:   */     
/* 35:60 */     CriteriaQuery<DetallePartidaArancelaria> select = criteriaQuery.select(from);
/* 36:61 */     TypedQuery<DetallePartidaArancelaria> typedQuery = this.em.createQuery(select);
/* 37:62 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 38:   */     
/* 39:64 */     return typedQuery.getResultList();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public List<DetallePartidaArancelaria> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 43:   */   {
/* 44:75 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 45:76 */     CriteriaQuery<DetallePartidaArancelaria> criteriaQuery = criteriaBuilder.createQuery(DetallePartidaArancelaria.class);
/* 46:77 */     Root<DetallePartidaArancelaria> from = criteriaQuery.from(DetallePartidaArancelaria.class);
/* 47:   */     
/* 48:79 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 49:80 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 50:   */     
/* 51:82 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 52:   */     
/* 53:84 */     CriteriaQuery<DetallePartidaArancelaria> select = criteriaQuery.select(from);
/* 54:85 */     TypedQuery<DetallePartidaArancelaria> typedQuery = this.em.createQuery(select);
/* 55:   */     
/* 56:87 */     return typedQuery.getResultList();
/* 57:   */   }
/* 58:   */   
/* 59:   */   public DetallePartidaArancelaria cargarDetalle(int idDetallePartidaArancelaria)
/* 60:   */   {
/* 61:97 */     return null;
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetallePartidaArancelariaDao
 * JD-Core Version:    0.7.0.1
 */