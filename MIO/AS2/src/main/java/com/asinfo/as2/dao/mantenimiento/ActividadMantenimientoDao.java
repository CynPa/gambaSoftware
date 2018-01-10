/*  1:   */ package com.asinfo.as2.dao.mantenimiento;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*  5:   */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoEspecialidad;
/*  6:   */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoHerramienta;
/*  7:   */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoMaterial;
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
/* 21:   */ public class ActividadMantenimientoDao
/* 22:   */   extends AbstractDaoAS2<ActividadMantenimiento>
/* 23:   */ {
/* 24:   */   public ActividadMantenimientoDao()
/* 25:   */   {
/* 26:32 */     super(ActividadMantenimiento.class);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ActividadMantenimiento cargarDetalle(ActividadMantenimiento actividadMantenimiento)
/* 30:   */   {
/* 31:36 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 32:37 */     CriteriaQuery<ActividadMantenimiento> criteriaQuery = cb.createQuery(ActividadMantenimiento.class);
/* 33:38 */     Root<ActividadMantenimiento> from = criteriaQuery.from(ActividadMantenimiento.class);
/* 34:39 */     from.fetch("tipoActividad", JoinType.INNER);
/* 35:40 */     criteriaQuery.where(cb.equal(from.get("idActividadMantenimiento"), Integer.valueOf(actividadMantenimiento.getId())));
/* 36:41 */     CriteriaQuery<ActividadMantenimiento> select = criteriaQuery.select(from);
/* 37:42 */     ActividadMantenimiento actividad = (ActividadMantenimiento)this.em.createQuery(select).getSingleResult();
/* 38:   */     
/* 39:44 */     CriteriaQuery<ActividadMantenimientoHerramienta> cqHerramienta = cb.createQuery(ActividadMantenimientoHerramienta.class);
/* 40:45 */     Root<ActividadMantenimientoHerramienta> fromHerramienta = cqHerramienta.from(ActividadMantenimientoHerramienta.class);
/* 41:46 */     fromHerramienta.fetch("herramienta", JoinType.INNER);
/* 42:47 */     cqHerramienta.where(cb.equal(fromHerramienta.join("actividadMantenimiento"), actividad));
/* 43:48 */     CriteriaQuery<ActividadMantenimientoHerramienta> selectHerramienta = cqHerramienta.select(fromHerramienta);
/* 44:49 */     List<ActividadMantenimientoHerramienta> listaHerramienta = this.em.createQuery(selectHerramienta).getResultList();
/* 45:   */     
/* 46:51 */     CriteriaQuery<ActividadMantenimientoMaterial> cqMaterial = cb.createQuery(ActividadMantenimientoMaterial.class);
/* 47:52 */     Root<ActividadMantenimientoMaterial> fromMaterial = cqMaterial.from(ActividadMantenimientoMaterial.class);
/* 48:53 */     fromMaterial.fetch("material", JoinType.INNER);
/* 49:54 */     cqMaterial.where(cb.equal(fromMaterial.join("actividadMantenimiento"), actividad));
/* 50:55 */     CriteriaQuery<ActividadMantenimientoMaterial> selectMaterial = cqMaterial.select(fromMaterial);
/* 51:56 */     List<ActividadMantenimientoMaterial> listaMaterial = this.em.createQuery(selectMaterial).getResultList();
/* 52:   */     
/* 53:58 */     CriteriaQuery<ActividadMantenimientoEspecialidad> cqEspecialidad = cb.createQuery(ActividadMantenimientoEspecialidad.class);
/* 54:59 */     Root<ActividadMantenimientoEspecialidad> fromEspecialidad = cqEspecialidad.from(ActividadMantenimientoEspecialidad.class);
/* 55:60 */     fromEspecialidad.fetch("especialidad", JoinType.INNER);
/* 56:61 */     cqEspecialidad.where(cb.equal(fromEspecialidad.join("actividadMantenimiento"), actividad));
/* 57:62 */     CriteriaQuery<ActividadMantenimientoEspecialidad> selectEspecialidad = cqEspecialidad.select(fromEspecialidad);
/* 58:63 */     List<ActividadMantenimientoEspecialidad> listaEspecialidad = this.em.createQuery(selectEspecialidad).getResultList();
/* 59:   */     
/* 60:65 */     actividad.setListaEspecialidad(listaEspecialidad);
/* 61:66 */     actividad.setListaHerramienta(listaHerramienta);
/* 62:67 */     actividad.setListaMaterial(listaMaterial);
/* 63:68 */     return actividad;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public List<ActividadMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 67:   */   {
/* 68:73 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 69:74 */     CriteriaQuery<ActividadMantenimiento> criteriaQuery = criteriaBuilder.createQuery(ActividadMantenimiento.class);
/* 70:75 */     Root<ActividadMantenimiento> from = criteriaQuery.from(ActividadMantenimiento.class);
/* 71:76 */     from.fetch("tipoActividad", JoinType.INNER);
/* 72:   */     
/* 73:78 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 74:79 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 75:   */     
/* 76:81 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 77:   */     
/* 78:83 */     CriteriaQuery<ActividadMantenimiento> select = criteriaQuery.select(from);
/* 79:   */     
/* 80:85 */     TypedQuery<ActividadMantenimiento> typedQuery = this.em.createQuery(select);
/* 81:86 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 82:   */     
/* 83:88 */     return typedQuery.getResultList();
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.ActividadMantenimientoDao
 * JD-Core Version:    0.7.0.1
 */