/*   1:    */ package com.asinfo.as2.dao.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.produccion.TareaProduccion;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.TypedQuery;
/*  12:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  13:    */ import javax.persistence.criteria.CriteriaQuery;
/*  14:    */ import javax.persistence.criteria.Expression;
/*  15:    */ import javax.persistence.criteria.JoinType;
/*  16:    */ import javax.persistence.criteria.Path;
/*  17:    */ import javax.persistence.criteria.Predicate;
/*  18:    */ import javax.persistence.criteria.Root;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class TareaProduccionDao
/*  22:    */   extends AbstractDaoAS2<TareaProduccion>
/*  23:    */ {
/*  24:    */   public TareaProduccionDao()
/*  25:    */   {
/*  26: 42 */     super(TareaProduccion.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List<TareaProduccion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  30:    */   {
/*  31: 48 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  32: 49 */     CriteriaQuery<TareaProduccion> criteriaQuery = criteriaBuilder.createQuery(TareaProduccion.class);
/*  33: 50 */     Root<TareaProduccion> from = criteriaQuery.from(TareaProduccion.class);
/*  34: 51 */     from.fetch("maquina", JoinType.LEFT);
/*  35: 52 */     from.fetch("tarifaOperacion", JoinType.LEFT);
/*  36:    */     
/*  37: 54 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  38:    */     
/*  39: 56 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  40: 57 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  41:    */     
/*  42: 59 */     CriteriaQuery<TareaProduccion> select = criteriaQuery.select(from);
/*  43: 60 */     TypedQuery<TareaProduccion> typedQuery = this.em.createQuery(select);
/*  44:    */     
/*  45: 62 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  46:    */     
/*  47: 64 */     return typedQuery.getResultList();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List<TareaProduccion> autocompletarTareaProduccion(String sortField, boolean sortOrder, Map<String, String> filters)
/*  51:    */   {
/*  52: 76 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  53: 77 */     CriteriaQuery<TareaProduccion> criteriaQuery = criteriaBuilder.createQuery(TareaProduccion.class);
/*  54: 78 */     Root<TareaProduccion> from = criteriaQuery.from(TareaProduccion.class);
/*  55: 79 */     from.fetch("maquina", JoinType.LEFT);
/*  56: 80 */     from.fetch("centroTrabajo", JoinType.LEFT);
/*  57: 81 */     from.fetch("tarifaOperacion", JoinType.LEFT);
/*  58: 82 */     Predicate disjunction = criteriaBuilder.disjunction();
/*  59: 84 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  60:    */     {
/*  61: 85 */       String filterProperty = (String)it.next();
/*  62: 87 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  63:    */       {
/*  64: 88 */         String filterValue = (String)filters.get(filterProperty);
/*  65:    */         
/*  66: 90 */         disjunction.getExpressions().add(criteriaBuilder
/*  67: 91 */           .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue + "%"));
/*  68:    */       }
/*  69:    */     }
/*  70: 96 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  71:    */     
/*  72: 98 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  73: 99 */     empresiones.clear();
/*  74:100 */     empresiones.add(disjunction);
/*  75:101 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  76:    */     
/*  77:103 */     CriteriaQuery<TareaProduccion> select = criteriaQuery.select(from);
/*  78:    */     
/*  79:105 */     TypedQuery<TareaProduccion> typedQuery = this.em.createQuery(select);
/*  80:    */     
/*  81:107 */     return typedQuery.getResultList();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<TareaProduccion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  85:    */   {
/*  86:118 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  87:119 */     CriteriaQuery<TareaProduccion> criteriaQuery = criteriaBuilder.createQuery(TareaProduccion.class);
/*  88:120 */     Root<TareaProduccion> from = criteriaQuery.from(TareaProduccion.class);
/*  89:    */     
/*  90:122 */     from.fetch("maquina", JoinType.LEFT);
/*  91:123 */     from.fetch("centroTrabajo", JoinType.LEFT);
/*  92:    */     
/*  93:125 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  94:    */     
/*  95:127 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  96:128 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  97:    */     
/*  98:130 */     CriteriaQuery<TareaProduccion> select = criteriaQuery.select(from);
/*  99:    */     
/* 100:132 */     TypedQuery<TareaProduccion> typedQuery = this.em.createQuery(select);
/* 101:    */     
/* 102:134 */     return typedQuery.getResultList();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public TareaProduccion cargarDetalle(int idTareaProduccion)
/* 106:    */   {
/* 107:145 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 108:    */     
/* 109:147 */     CriteriaQuery<TareaProduccion> cqCabecera = criteriaBuilder.createQuery(TareaProduccion.class);
/* 110:148 */     Root<TareaProduccion> fromCabecera = cqCabecera.from(TareaProduccion.class);
/* 111:149 */     fromCabecera.fetch("centroTrabajo", JoinType.LEFT);
/* 112:150 */     fromCabecera.fetch("tarifaOperacion", JoinType.LEFT);
/* 113:151 */     fromCabecera.fetch("maquina", JoinType.LEFT);
/* 114:    */     
/* 115:153 */     cqCabecera.where(criteriaBuilder.equal(fromCabecera.get("idTareaProduccion"), Integer.valueOf(idTareaProduccion)));
/* 116:154 */     CriteriaQuery<TareaProduccion> select = cqCabecera.select(fromCabecera);
/* 117:    */     
/* 118:156 */     TareaProduccion tareaProduccion = (TareaProduccion)this.em.createQuery(select).getSingleResult();
/* 119:157 */     this.em.detach(tareaProduccion);
/* 120:    */     
/* 121:159 */     return tareaProduccion;
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.produccion.TareaProduccionDao
 * JD-Core Version:    0.7.0.1
 */