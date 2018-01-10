/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.TypedQuery;
/*  11:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  12:    */ import javax.persistence.criteria.CriteriaQuery;
/*  13:    */ import javax.persistence.criteria.Expression;
/*  14:    */ import javax.persistence.criteria.Fetch;
/*  15:    */ import javax.persistence.criteria.JoinType;
/*  16:    */ import javax.persistence.criteria.Path;
/*  17:    */ import javax.persistence.criteria.Predicate;
/*  18:    */ import javax.persistence.criteria.Root;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class PersonaResponsableDao
/*  22:    */   extends AbstractDaoAS2<PersonaResponsable>
/*  23:    */ {
/*  24:    */   public PersonaResponsableDao()
/*  25:    */   {
/*  26: 40 */     super(PersonaResponsable.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List<PersonaResponsable> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  30:    */   {
/*  31: 52 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  32: 53 */     CriteriaQuery<PersonaResponsable> criteriaQuery = criteriaBuilder.createQuery(PersonaResponsable.class);
/*  33: 54 */     Root<PersonaResponsable> from = criteriaQuery.from(PersonaResponsable.class);
/*  34:    */     
/*  35: 56 */     from.fetch("tipoIdentificacion", JoinType.INNER);
/*  36: 57 */     from.fetch("especialidad", JoinType.LEFT);
/*  37: 58 */     from.fetch("proveedor", JoinType.LEFT);
/*  38: 59 */     from.fetch("empleado", JoinType.LEFT);
/*  39:    */     
/*  40: 61 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  41: 62 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  42:    */     
/*  43: 64 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  44:    */     
/*  45: 66 */     CriteriaQuery<PersonaResponsable> select = criteriaQuery.select(from);
/*  46:    */     
/*  47: 68 */     TypedQuery<PersonaResponsable> typedQuery = this.em.createQuery(select);
/*  48: 69 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  49:    */     
/*  50: 71 */     return typedQuery.getResultList();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public PersonaResponsable cargarDetalle(int idPersonaResponsable)
/*  54:    */   {
/*  55: 76 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  56:    */     
/*  57: 78 */     CriteriaQuery<PersonaResponsable> cqCabecera = criteriaBuilder.createQuery(PersonaResponsable.class);
/*  58: 79 */     Root<PersonaResponsable> fromCabecera = cqCabecera.from(PersonaResponsable.class);
/*  59:    */     
/*  60: 81 */     fromCabecera.fetch("especialidad", JoinType.LEFT);
/*  61: 82 */     fromCabecera.fetch("tipoIdentificacion", JoinType.INNER);
/*  62: 83 */     Fetch<Object, Object> proveedor = fromCabecera.fetch("proveedor", JoinType.LEFT);
/*  63: 84 */     proveedor.fetch("proveedor", JoinType.LEFT);
/*  64: 85 */     fromCabecera.fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  65: 86 */     Path<Integer> pathId = fromCabecera.get("idPersonaResponsable");
/*  66: 87 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idPersonaResponsable)));
/*  67: 88 */     CriteriaQuery<PersonaResponsable> selectPedido = cqCabecera.select(fromCabecera);
/*  68:    */     
/*  69: 90 */     PersonaResponsable responsableSalidaMercaderia = (PersonaResponsable)this.em.createQuery(selectPedido).getSingleResult();
/*  70:    */     
/*  71: 92 */     return responsableSalidaMercaderia;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<PersonaResponsable> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  75:    */   {
/*  76: 97 */     filters = agregarFiltrosOrganizacion(filters);
/*  77:    */     
/*  78: 99 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  79:100 */     CriteriaQuery<PersonaResponsable> criteriaQuery = criteriaBuilder.createQuery(PersonaResponsable.class);
/*  80:101 */     Root<PersonaResponsable> from = criteriaQuery.from(PersonaResponsable.class);
/*  81:102 */     from.fetch("especialidad", JoinType.LEFT);
/*  82:103 */     from.fetch("proveedor", JoinType.LEFT);
/*  83:104 */     from.fetch("empleado", JoinType.LEFT);
/*  84:    */     
/*  85:106 */     Predicate conjunction = criteriaBuilder.conjunction();
/*  86:107 */     Predicate disjunction = criteriaBuilder.disjunction();
/*  87:109 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  88:    */     {
/*  89:110 */       String filterProperty = (String)it.next();
/*  90:112 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  91:    */       {
/*  92:113 */         String filterValue = (String)filters.get(filterProperty);
/*  93:115 */         if (filterProperty.startsWith("idOrganizacion")) {
/*  94:116 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/*  95:117 */         } else if (filterProperty.startsWith("activo")) {
/*  96:118 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/*  97:119 */         } else if (filterProperty.startsWith("indicador")) {
/*  98:120 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/*  99:    */         } else {
/* 100:122 */           disjunction.getExpressions().add(criteriaBuilder
/* 101:123 */             .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue + "%"));
/* 102:    */         }
/* 103:    */       }
/* 104:    */     }
/* 105:128 */     if (disjunction.getExpressions().size() > 0) {
/* 106:129 */       conjunction.getExpressions().add(disjunction);
/* 107:    */     }
/* 108:132 */     if (conjunction.getExpressions().size() > 0) {
/* 109:133 */       criteriaQuery.where(conjunction);
/* 110:    */     }
/* 111:136 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 112:    */     
/* 113:138 */     CriteriaQuery<PersonaResponsable> select = criteriaQuery.select(from);
/* 114:139 */     TypedQuery<PersonaResponsable> typedQuery = this.em.createQuery(select);
/* 115:    */     
/* 116:141 */     return typedQuery.getResultList();
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PersonaResponsableDao
 * JD-Core Version:    0.7.0.1
 */