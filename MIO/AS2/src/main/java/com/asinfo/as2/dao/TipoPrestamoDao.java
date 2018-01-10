/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CuentaContable;
/*  4:   */ import com.asinfo.as2.entities.Documento;
/*  5:   */ import com.asinfo.as2.entities.Rubro;
/*  6:   */ import com.asinfo.as2.entities.Secuencia;
/*  7:   */ import com.asinfo.as2.entities.TipoPrestamo;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ import javax.persistence.EntityManager;
/* 12:   */ import javax.persistence.TypedQuery;
/* 13:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 14:   */ import javax.persistence.criteria.CriteriaQuery;
/* 15:   */ import javax.persistence.criteria.Expression;
/* 16:   */ import javax.persistence.criteria.JoinType;
/* 17:   */ import javax.persistence.criteria.Predicate;
/* 18:   */ import javax.persistence.criteria.Root;
/* 19:   */ 
/* 20:   */ @Stateless
/* 21:   */ public class TipoPrestamoDao
/* 22:   */   extends AbstractDaoAS2<TipoPrestamo>
/* 23:   */ {
/* 24:   */   public TipoPrestamoDao()
/* 25:   */   {
/* 26:40 */     super(TipoPrestamo.class);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public List<TipoPrestamo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 30:   */   {
/* 31:49 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 32:50 */     CriteriaQuery<TipoPrestamo> criteriaQuery = criteriaBuilder.createQuery(TipoPrestamo.class);
/* 33:51 */     Root<TipoPrestamo> from = criteriaQuery.from(TipoPrestamo.class);
/* 34:52 */     from.fetch("rubro", JoinType.LEFT);
/* 35:   */     
/* 36:54 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 37:55 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 38:56 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 39:   */     
/* 40:58 */     CriteriaQuery<TipoPrestamo> select = criteriaQuery.select(from);
/* 41:59 */     TypedQuery<TipoPrestamo> typedQuery = this.em.createQuery(select);
/* 42:60 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 43:   */     
/* 44:62 */     return typedQuery.getResultList();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public TipoPrestamo cargarDetalle(int idTipoPrestamo)
/* 48:   */   {
/* 49:72 */     TipoPrestamo tipoPrestamo = (TipoPrestamo)buscarPorId(Integer.valueOf(idTipoPrestamo));
/* 50:73 */     tipoPrestamo.getId();
/* 51:74 */     if (tipoPrestamo.getCuentaContable() != null) {
/* 52:75 */       tipoPrestamo.getCuentaContable().getId();
/* 53:   */     }
/* 54:77 */     tipoPrestamo.getDocumento().getId();
/* 55:78 */     tipoPrestamo.getDocumento().getSecuencia().getId();
/* 56:79 */     tipoPrestamo.getRubro().getId();
/* 57:80 */     return tipoPrestamo;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TipoPrestamoDao
 * JD-Core Version:    0.7.0.1
 */