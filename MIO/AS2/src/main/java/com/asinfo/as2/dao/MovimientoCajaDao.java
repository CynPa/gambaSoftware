/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.MovimientoCaja;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.TypedQuery;
/*  9:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 10:   */ import javax.persistence.criteria.CriteriaQuery;
/* 11:   */ import javax.persistence.criteria.Expression;
/* 12:   */ import javax.persistence.criteria.JoinType;
/* 13:   */ import javax.persistence.criteria.Predicate;
/* 14:   */ import javax.persistence.criteria.Root;
/* 15:   */ 
/* 16:   */ @Stateless
/* 17:   */ public class MovimientoCajaDao
/* 18:   */   extends AbstractDaoAS2<MovimientoCaja>
/* 19:   */ {
/* 20:   */   public MovimientoCajaDao()
/* 21:   */   {
/* 22:42 */     super(MovimientoCaja.class);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<MovimientoCaja> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 26:   */   {
/* 27:47 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 28:48 */     CriteriaQuery<MovimientoCaja> criteriaQuery = criteriaBuilder.createQuery(MovimientoCaja.class);
/* 29:49 */     Root<MovimientoCaja> from = criteriaQuery.from(MovimientoCaja.class);
/* 30:   */     
/* 31:51 */     from.fetch("usuario", JoinType.LEFT);
/* 32:   */     
/* 33:53 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 34:54 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 35:55 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 36:   */     
/* 37:57 */     CriteriaQuery<MovimientoCaja> select = criteriaQuery.select(from);
/* 38:   */     
/* 39:59 */     TypedQuery<MovimientoCaja> typedQuery = this.em.createQuery(select);
/* 40:60 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 41:   */     
/* 42:62 */     return typedQuery.getResultList();
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.MovimientoCajaDao
 * JD-Core Version:    0.7.0.1
 */