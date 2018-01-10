/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CentroTrabajo;
/*  4:   */ import com.asinfo.as2.entities.Departamento;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.TypedQuery;
/* 10:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 11:   */ import javax.persistence.criteria.CriteriaQuery;
/* 12:   */ import javax.persistence.criteria.Expression;
/* 13:   */ import javax.persistence.criteria.Path;
/* 14:   */ import javax.persistence.criteria.Predicate;
/* 15:   */ import javax.persistence.criteria.Root;
/* 16:   */ import javax.persistence.criteria.Selection;
/* 17:   */ 
/* 18:   */ @Stateless
/* 19:   */ public class CentroTrabajoDao
/* 20:   */   extends AbstractDaoAS2<CentroTrabajo>
/* 21:   */ {
/* 22:   */   public CentroTrabajoDao()
/* 23:   */   {
/* 24:39 */     super(CentroTrabajo.class);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public List<CentroTrabajo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 28:   */   {
/* 29:49 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 30:50 */     CriteriaQuery<CentroTrabajo> criteriaQuery = criteriaBuilder.createQuery(CentroTrabajo.class);
/* 31:51 */     Root<CentroTrabajo> from = criteriaQuery.from(CentroTrabajo.class);
/* 32:   */     
/* 33:53 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 34:   */     
/* 35:55 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 36:56 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 37:   */     
/* 38:58 */     CriteriaQuery<CentroTrabajo> select = criteriaQuery.select(from);
/* 39:59 */     TypedQuery<CentroTrabajo> typedQuery = this.em.createQuery(select);
/* 40:60 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 41:   */     
/* 42:62 */     return typedQuery.getResultList();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public List<CentroTrabajo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 46:   */   {
/* 47:71 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 48:72 */     CriteriaQuery<CentroTrabajo> criteriaQuery = criteriaBuilder.createQuery(CentroTrabajo.class);
/* 49:73 */     Root<CentroTrabajo> from = criteriaQuery.from(CentroTrabajo.class);
/* 50:   */     
/* 51:75 */     Path<Integer> pathIdCentroTrabajo = from.get("idCentroTrabajo");
/* 52:76 */     Path<String> pathNombre = from.get("nombre");
/* 53:   */     
/* 54:78 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 55:79 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 56:   */     
/* 57:81 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 58:   */     
/* 59:83 */     CriteriaQuery<CentroTrabajo> select = criteriaQuery.multiselect(new Selection[] { pathIdCentroTrabajo, pathNombre });
/* 60:84 */     TypedQuery<CentroTrabajo> typedQuery = this.em.createQuery(select);
/* 61:   */     
/* 62:86 */     return typedQuery.getResultList();
/* 63:   */   }
/* 64:   */   
/* 65:   */   public CentroTrabajo cargarDetalle(int idCentroTrabajo)
/* 66:   */   {
/* 67:96 */     CentroTrabajo centroTrabajo = (CentroTrabajo)buscarPorId(Integer.valueOf(idCentroTrabajo));
/* 68:97 */     centroTrabajo.getDepartamento().getIdDepartamento();
/* 69:98 */     return centroTrabajo;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CentroTrabajoDao
 * JD-Core Version:    0.7.0.1
 */