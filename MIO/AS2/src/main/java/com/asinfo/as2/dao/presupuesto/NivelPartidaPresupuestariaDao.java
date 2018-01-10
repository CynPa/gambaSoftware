/*  1:   */ package com.asinfo.as2.dao.presupuesto;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.presupuesto.NivelPartidaPresupuestaria;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.TypedQuery;
/* 10:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 11:   */ import javax.persistence.criteria.CriteriaQuery;
/* 12:   */ import javax.persistence.criteria.Expression;
/* 13:   */ import javax.persistence.criteria.Predicate;
/* 14:   */ import javax.persistence.criteria.Root;
/* 15:   */ 
/* 16:   */ @Stateless
/* 17:   */ public class NivelPartidaPresupuestariaDao
/* 18:   */   extends AbstractDaoAS2<NivelPartidaPresupuestaria>
/* 19:   */ {
/* 20:   */   public NivelPartidaPresupuestariaDao()
/* 21:   */   {
/* 22:41 */     super(NivelPartidaPresupuestaria.class);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<NivelPartidaPresupuestaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 26:   */   {
/* 27:48 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 28:49 */     CriteriaQuery<NivelPartidaPresupuestaria> criteriaQuery = criteriaBuilder.createQuery(NivelPartidaPresupuestaria.class);
/* 29:50 */     Root<NivelPartidaPresupuestaria> from = criteriaQuery.from(NivelPartidaPresupuestaria.class);
/* 30:   */     
/* 31:52 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 32:53 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 33:   */     
/* 34:55 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 35:   */     
/* 36:57 */     CriteriaQuery<NivelPartidaPresupuestaria> select = criteriaQuery.select(from);
/* 37:58 */     TypedQuery<NivelPartidaPresupuestaria> typedQuery = this.em.createQuery(select);
/* 38:59 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 39:   */     
/* 40:61 */     return typedQuery.getResultList();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public List<NivelPartidaPresupuestaria> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 44:   */   {
/* 45:68 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 46:69 */     CriteriaQuery<NivelPartidaPresupuestaria> criteriaQuery = criteriaBuilder.createQuery(NivelPartidaPresupuestaria.class);
/* 47:70 */     Root<NivelPartidaPresupuestaria> from = criteriaQuery.from(NivelPartidaPresupuestaria.class);
/* 48:   */     
/* 49:72 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 50:73 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 51:   */     
/* 52:75 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 53:   */     
/* 54:77 */     CriteriaQuery<NivelPartidaPresupuestaria> select = criteriaQuery.select(from);
/* 55:78 */     TypedQuery<NivelPartidaPresupuestaria> typedQuery = this.em.createQuery(select);
/* 56:   */     
/* 57:80 */     return typedQuery.getResultList();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public NivelPartidaPresupuestaria cargarDetalle(int idPartidaPresupuestaria)
/* 61:   */   {
/* 62:89 */     NivelPartidaPresupuestaria nivelPartidaPresupuestaria = (NivelPartidaPresupuestaria)buscarPorId(Integer.valueOf(idPartidaPresupuestaria));
/* 63:90 */     return nivelPartidaPresupuestaria;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.presupuesto.NivelPartidaPresupuestariaDao
 * JD-Core Version:    0.7.0.1
 */