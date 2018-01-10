/*  1:   */ package com.asinfo.as2.dao.mantenimiento.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.Actividad;
/*  5:   */ import com.asinfo.as2.entities.mantenimiento.old.Criticidad;
/*  6:   */ import com.asinfo.as2.entities.mantenimiento.old.ListaVerificacion;
/*  7:   */ import com.asinfo.as2.entities.mantenimiento.old.Tarea;
/*  8:   */ import com.asinfo.as2.entities.mantenimiento.old.TarifaSalarial;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Map;
/* 11:   */ import javax.ejb.Stateless;
/* 12:   */ import javax.persistence.EntityManager;
/* 13:   */ import javax.persistence.TypedQuery;
/* 14:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 15:   */ import javax.persistence.criteria.CriteriaQuery;
/* 16:   */ import javax.persistence.criteria.Expression;
/* 17:   */ import javax.persistence.criteria.JoinType;
/* 18:   */ import javax.persistence.criteria.Predicate;
/* 19:   */ import javax.persistence.criteria.Root;
/* 20:   */ 
/* 21:   */ @Stateless
/* 22:   */ public class ActividadDao
/* 23:   */   extends AbstractDaoAS2<Actividad>
/* 24:   */ {
/* 25:   */   public ActividadDao()
/* 26:   */   {
/* 27:41 */     super(Actividad.class);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Actividad cargarDetalle(int idActividad)
/* 31:   */   {
/* 32:51 */     Actividad actividad = (Actividad)buscarPorId(Integer.valueOf(idActividad));
/* 33:52 */     if (actividad.getCriticidad() != null) {
/* 34:53 */       actividad.getCriticidad().getId();
/* 35:   */     }
/* 36:55 */     if (actividad.getListaVerificacion() != null) {
/* 37:56 */       actividad.getListaVerificacion().getId();
/* 38:   */     }
/* 39:58 */     if (actividad.getListaTarea().size() > 0) {
/* 40:59 */       for (Tarea tarea : actividad.getListaTarea())
/* 41:   */       {
/* 42:60 */         tarea.getIdTarea();
/* 43:61 */         tarea.getTarifaSalarial().getId();
/* 44:   */       }
/* 45:   */     }
/* 46:64 */     return actividad;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public List<Actividad> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 50:   */   {
/* 51:70 */     filters = agregarFiltrosOrganizacion(filters);
/* 52:   */     
/* 53:72 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 54:73 */     CriteriaQuery<Actividad> criteriaQuery = criteriaBuilder.createQuery(Actividad.class);
/* 55:74 */     Root<Actividad> from = criteriaQuery.from(Actividad.class);
/* 56:75 */     from.fetch("listaVerificacion", JoinType.LEFT);
/* 57:   */     
/* 58:77 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 59:   */     
/* 60:79 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 61:80 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 62:   */     
/* 63:82 */     CriteriaQuery<Actividad> select = criteriaQuery.select(from);
/* 64:83 */     TypedQuery<Actividad> typedQuery = this.em.createQuery(select);
/* 65:   */     
/* 66:   */ 
/* 67:86 */     typedQuery.setMaxResults(20);
/* 68:   */     
/* 69:88 */     return typedQuery.getResultList();
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.old.ActividadDao
 * JD-Core Version:    0.7.0.1
 */