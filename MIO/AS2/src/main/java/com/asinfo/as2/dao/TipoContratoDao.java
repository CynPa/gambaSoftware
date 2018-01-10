/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Secuencia;
/*  4:   */ import com.asinfo.as2.entities.TipoContrato;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.TypedQuery;
/* 11:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 12:   */ import javax.persistence.criteria.CriteriaQuery;
/* 13:   */ import javax.persistence.criteria.Expression;
/* 14:   */ import javax.persistence.criteria.JoinType;
/* 15:   */ import javax.persistence.criteria.Path;
/* 16:   */ import javax.persistence.criteria.Predicate;
/* 17:   */ import javax.persistence.criteria.Root;
/* 18:   */ 
/* 19:   */ @Stateless
/* 20:   */ public class TipoContratoDao
/* 21:   */   extends AbstractDaoAS2<TipoContrato>
/* 22:   */ {
/* 23:   */   public TipoContratoDao()
/* 24:   */   {
/* 25:42 */     super(TipoContrato.class);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TipoContrato buscarPorNombre(String nombre)
/* 29:   */   {
/* 30:46 */     List<TipoContrato> tipoContrato = new ArrayList();
/* 31:47 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 32:48 */     CriteriaQuery<TipoContrato> criteriaQuery = criteriaBuilder.createQuery(TipoContrato.class);
/* 33:49 */     Root<TipoContrato> from = criteriaQuery.from(TipoContrato.class);
/* 34:   */     
/* 35:51 */     Path<String> pathNombre = from.get("nombre");
/* 36:52 */     criteriaQuery.where(criteriaBuilder.equal(pathNombre, nombre));
/* 37:53 */     CriteriaQuery<TipoContrato> select = criteriaQuery.select(from);
/* 38:54 */     TypedQuery<TipoContrato> typedQuery = this.em.createQuery(select);
/* 39:   */     
/* 40:56 */     tipoContrato = typedQuery.getResultList();
/* 41:57 */     if (tipoContrato.size() > 0) {
/* 42:58 */       return (TipoContrato)tipoContrato.get(0);
/* 43:   */     }
/* 44:60 */     return null;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public List<TipoContrato> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 48:   */   {
/* 49:67 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 50:68 */     CriteriaQuery<TipoContrato> criteriaQuery = criteriaBuilder.createQuery(TipoContrato.class);
/* 51:   */     
/* 52:70 */     Root<TipoContrato> from = criteriaQuery.from(TipoContrato.class);
/* 53:71 */     from.fetch("secuencia", JoinType.LEFT);
/* 54:   */     
/* 55:73 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 56:74 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 57:75 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 58:   */     
/* 59:77 */     CriteriaQuery<TipoContrato> select = criteriaQuery.select(from);
/* 60:   */     
/* 61:79 */     TypedQuery<TipoContrato> typedQuery = this.em.createQuery(select);
/* 62:   */     
/* 63:81 */     return typedQuery.getResultList();
/* 64:   */   }
/* 65:   */   
/* 66:   */   public TipoContrato cargarDetalle(int idTipoContrato)
/* 67:   */   {
/* 68:85 */     TipoContrato tipoContrato = (TipoContrato)buscarPorId(Integer.valueOf(idTipoContrato));
/* 69:86 */     tipoContrato.getSecuencia().getIdSecuencia();
/* 70:87 */     return tipoContrato;
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TipoContratoDao
 * JD-Core Version:    0.7.0.1
 */