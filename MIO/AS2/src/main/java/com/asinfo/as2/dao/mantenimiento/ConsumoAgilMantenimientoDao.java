/*  1:   */ package com.asinfo.as2.dao.mantenimiento;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.ConsumoAgilMantenimiento;
/*  5:   */ import com.asinfo.as2.entities.mantenimiento.DetalleComponenteEquipo;
/*  6:   */ import com.asinfo.as2.entities.mantenimiento.DetalleConsumoAgilMantenimiento;
/*  7:   */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  8:   */ import java.util.List;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ import javax.persistence.EntityManager;
/* 11:   */ import javax.persistence.TypedQuery;
/* 12:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 13:   */ import javax.persistence.criteria.CriteriaQuery;
/* 14:   */ import javax.persistence.criteria.Fetch;
/* 15:   */ import javax.persistence.criteria.JoinType;
/* 16:   */ import javax.persistence.criteria.Root;
/* 17:   */ 
/* 18:   */ @Stateless
/* 19:   */ public class ConsumoAgilMantenimientoDao
/* 20:   */   extends AbstractDaoAS2<ConsumoAgilMantenimiento>
/* 21:   */ {
/* 22:   */   public ConsumoAgilMantenimientoDao()
/* 23:   */   {
/* 24:20 */     super(ConsumoAgilMantenimiento.class);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public ConsumoAgilMantenimiento cargarDetalle()
/* 28:   */   {
/* 29:24 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 30:25 */     CriteriaQuery<ConsumoAgilMantenimiento> criteriaQuery = cb.createQuery(ConsumoAgilMantenimiento.class);
/* 31:26 */     Root<ConsumoAgilMantenimiento> from = criteriaQuery.from(ConsumoAgilMantenimiento.class);
/* 32:27 */     Fetch<Object, Object> documento = from.fetch("documento", JoinType.INNER);
/* 33:28 */     Fetch<Object, Object> documentoOrden = from.fetch("documentoOrden", JoinType.LEFT);
/* 34:29 */     documento.fetch("secuencia", JoinType.LEFT);
/* 35:30 */     documentoOrden.fetch("secuencia", JoinType.LEFT);
/* 36:31 */     from.fetch("responsableSalidaMercaderia", JoinType.LEFT);
/* 37:32 */     criteriaQuery.where(cb.isNotNull(from.get("idConsumoAgilMantenimiento")));
/* 38:33 */     CriteriaQuery<ConsumoAgilMantenimiento> select = criteriaQuery.select(from);
/* 39:34 */     List<ConsumoAgilMantenimiento> listCAM = this.em.createQuery(select).getResultList();
/* 40:35 */     ConsumoAgilMantenimiento tmpCAM = null;
/* 41:36 */     if (!listCAM.isEmpty())
/* 42:   */     {
/* 43:37 */       tmpCAM = (ConsumoAgilMantenimiento)listCAM.get(0);
/* 44:   */       
/* 45:39 */       CriteriaQuery<DetalleConsumoAgilMantenimiento> cqDCAM = cb.createQuery(DetalleConsumoAgilMantenimiento.class);
/* 46:40 */       Root<DetalleConsumoAgilMantenimiento> fromDCAM = cqDCAM.from(DetalleConsumoAgilMantenimiento.class);
/* 47:41 */       fromDCAM.fetch("equipo", JoinType.LEFT);
/* 48:42 */       fromDCAM.fetch("componenteEquipo", JoinType.LEFT);
/* 49:43 */       fromDCAM.fetch("actividadMantenimiento", JoinType.LEFT);
/* 50:44 */       fromDCAM.fetch("material", JoinType.LEFT);
/* 51:45 */       fromDCAM.fetch("bodegaOrigen", JoinType.LEFT);
/* 52:46 */       fromDCAM.fetch("lote", JoinType.LEFT);
/* 53:47 */       fromDCAM.fetch("destinoCosto", JoinType.LEFT);
/* 54:48 */       cqDCAM.where(cb.equal(fromDCAM.join("consumoAgilMantenimiento"), tmpCAM));
/* 55:49 */       CriteriaQuery<DetalleConsumoAgilMantenimiento> selectDCAM = cqDCAM.select(fromDCAM);
/* 56:50 */       List<DetalleConsumoAgilMantenimiento> listaDCAM = this.em.createQuery(selectDCAM).getResultList();
/* 57:51 */       for (DetalleConsumoAgilMantenimiento detalleOrdenTrabajoMantenimiento : listaDCAM)
/* 58:   */       {
/* 59:53 */         CriteriaQuery<DetalleComponenteEquipo> cqDCE = cb.createQuery(DetalleComponenteEquipo.class);
/* 60:54 */         Root<DetalleComponenteEquipo> fromDCE = cqDCE.from(DetalleComponenteEquipo.class);
/* 61:55 */         fromDCE.fetch("componenteEquipo", JoinType.LEFT);
/* 62:56 */         cqDCE.where(cb.equal(fromDCE.join("equipo"), detalleOrdenTrabajoMantenimiento.getEquipo()));
/* 63:57 */         CriteriaQuery<DetalleComponenteEquipo> selectDCE = cqDCE.select(fromDCE);
/* 64:58 */         List<DetalleComponenteEquipo> listaDCE = this.em.createQuery(selectDCE).getResultList();
/* 65:59 */         detalleOrdenTrabajoMantenimiento.getEquipo().setListaComponenteEquipo(listaDCE);
/* 66:   */       }
/* 67:61 */       tmpCAM.setListaDetalleConsumoAgilMantenimiento(listaDCAM);
/* 68:62 */       this.em.detach(tmpCAM);
/* 69:   */     }
/* 70:64 */     return tmpCAM;
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.ConsumoAgilMantenimientoDao
 * JD-Core Version:    0.7.0.1
 */