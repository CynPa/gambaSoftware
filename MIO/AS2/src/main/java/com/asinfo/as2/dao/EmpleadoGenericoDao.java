/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EmpleadoGenerico;
/*  4:   */ import com.asinfo.as2.entities.Empresa;
/*  5:   */ import com.asinfo.as2.entities.Especialidad;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import java.util.Set;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ import javax.persistence.EntityManager;
/* 12:   */ import javax.persistence.TypedQuery;
/* 13:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 14:   */ import javax.persistence.criteria.CriteriaQuery;
/* 15:   */ import javax.persistence.criteria.Expression;
/* 16:   */ import javax.persistence.criteria.Path;
/* 17:   */ import javax.persistence.criteria.Predicate;
/* 18:   */ import javax.persistence.criteria.Root;
/* 19:   */ 
/* 20:   */ @Stateless
/* 21:   */ public class EmpleadoGenericoDao
/* 22:   */   extends AbstractDaoAS2<EmpleadoGenerico>
/* 23:   */ {
/* 24:   */   public EmpleadoGenericoDao()
/* 25:   */   {
/* 26:40 */     super(EmpleadoGenerico.class);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public EmpleadoGenerico cargarDetalle(int idEmpleadoGenerico)
/* 30:   */   {
/* 31:50 */     EmpleadoGenerico empleadoGenerico = (EmpleadoGenerico)buscarPorId(Integer.valueOf(idEmpleadoGenerico));
/* 32:51 */     empleadoGenerico.getEspecialidad().getId();
/* 33:52 */     if (empleadoGenerico.getEspecialidad() != null) {
/* 34:53 */       empleadoGenerico.getEspecialidad().getId();
/* 35:   */     }
/* 36:55 */     if (empleadoGenerico.getEmpresa() != null) {
/* 37:56 */       empleadoGenerico.getEmpresa().getId();
/* 38:   */     }
/* 39:58 */     return empleadoGenerico;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public List<EmpleadoGenerico> autocompletarEmpleadoGenerico(String sortField, boolean sortOrder, Map<String, String> filters)
/* 43:   */   {
/* 44:63 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 45:64 */     CriteriaQuery<EmpleadoGenerico> criteriaQuery = criteriaBuilder.createQuery(EmpleadoGenerico.class);
/* 46:65 */     Root<EmpleadoGenerico> from = criteriaQuery.from(EmpleadoGenerico.class);
/* 47:   */     
/* 48:67 */     Predicate disjunction = criteriaBuilder.disjunction();
/* 49:69 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/* 50:   */     {
/* 51:70 */       String filterProperty = (String)it.next();
/* 52:72 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/* 53:   */       {
/* 54:73 */         String filterValue = (String)filters.get(filterProperty);
/* 55:   */         
/* 56:75 */         disjunction.getExpressions().add(criteriaBuilder
/* 57:76 */           .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue + "%"));
/* 58:   */       }
/* 59:   */     }
/* 60:81 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 61:   */     
/* 62:83 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 63:84 */     empresiones.clear();
/* 64:85 */     empresiones.add(disjunction);
/* 65:86 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 66:   */     
/* 67:88 */     CriteriaQuery<EmpleadoGenerico> select = criteriaQuery.select(from);
/* 68:   */     
/* 69:90 */     TypedQuery<EmpleadoGenerico> typedQuery = this.em.createQuery(select);
/* 70:   */     
/* 71:92 */     return typedQuery.getResultList();
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.EmpleadoGenericoDao
 * JD-Core Version:    0.7.0.1
 */