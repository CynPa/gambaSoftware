/*  1:   */ package com.asinfo.as2.dao.produccion;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.CentroTrabajo;
/*  5:   */ import com.asinfo.as2.entities.produccion.Maquina;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.TypedQuery;
/* 11:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 12:   */ import javax.persistence.criteria.CriteriaQuery;
/* 13:   */ import javax.persistence.criteria.Expression;
/* 14:   */ import javax.persistence.criteria.JoinType;
/* 15:   */ import javax.persistence.criteria.Predicate;
/* 16:   */ import javax.persistence.criteria.Root;
/* 17:   */ 
/* 18:   */ @Stateless
/* 19:   */ public class MaquinaDao
/* 20:   */   extends AbstractDaoAS2<Maquina>
/* 21:   */ {
/* 22:   */   public MaquinaDao()
/* 23:   */   {
/* 24:40 */     super(Maquina.class);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public List<Maquina> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 28:   */   {
/* 29:50 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 30:51 */     CriteriaQuery<Maquina> criteriaQuery = criteriaBuilder.createQuery(Maquina.class);
/* 31:52 */     Root<Maquina> from = criteriaQuery.from(Maquina.class);
/* 32:53 */     from.fetch("centroTrabajo", JoinType.LEFT);
/* 33:54 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 34:   */     
/* 35:56 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 36:57 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 37:   */     
/* 38:59 */     CriteriaQuery<Maquina> select = criteriaQuery.select(from);
/* 39:60 */     TypedQuery<Maquina> typedQuery = this.em.createQuery(select);
/* 40:61 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 41:   */     
/* 42:63 */     return typedQuery.getResultList();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Maquina cargarDetalle(int idMaquina)
/* 46:   */   {
/* 47:73 */     Maquina maquina = (Maquina)buscarPorId(Integer.valueOf(idMaquina));
/* 48:74 */     maquina.getCentroTrabajo().getIdCentroTrabajo();
/* 49:75 */     return maquina;
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.produccion.MaquinaDao
 * JD-Core Version:    0.7.0.1
 */