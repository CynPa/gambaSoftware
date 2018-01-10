/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ContratoVentaFacturaContratoVenta;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ import javax.persistence.EntityManager;
/*  6:   */ import javax.persistence.TypedQuery;
/*  7:   */ import javax.persistence.criteria.CriteriaBuilder;
/*  8:   */ import javax.persistence.criteria.CriteriaQuery;
/*  9:   */ import javax.persistence.criteria.JoinType;
/* 10:   */ import javax.persistence.criteria.Path;
/* 11:   */ import javax.persistence.criteria.Root;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class ContratoVentaFacturaContratoVentaDao
/* 15:   */   extends AbstractDaoAS2<ContratoVentaFacturaContratoVenta>
/* 16:   */ {
/* 17:   */   public ContratoVentaFacturaContratoVentaDao()
/* 18:   */   {
/* 19:36 */     super(ContratoVentaFacturaContratoVenta.class);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public ContratoVentaFacturaContratoVenta cargarDetalle(int idContratoVentaFacturaContratoVenta)
/* 23:   */   {
/* 24:40 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 25:   */     
/* 26:   */ 
/* 27:43 */     CriteriaQuery<ContratoVentaFacturaContratoVenta> cqCabecera = criteriaBuilder.createQuery(ContratoVentaFacturaContratoVenta.class);
/* 28:44 */     Root<ContratoVentaFacturaContratoVenta> fromCabecera = cqCabecera.from(ContratoVentaFacturaContratoVenta.class);
/* 29:   */     
/* 30:46 */     fromCabecera.fetch("detallesFacturaContratoVenta", JoinType.LEFT);
/* 31:   */     
/* 32:48 */     Path<Integer> pathId = fromCabecera.get("idContratoVentaFacturaContratoVenta");
/* 33:49 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idContratoVentaFacturaContratoVenta)));
/* 34:50 */     CriteriaQuery<ContratoVentaFacturaContratoVenta> select = cqCabecera.select(fromCabecera);
/* 35:   */     
/* 36:52 */     ContratoVentaFacturaContratoVenta contratoVentaFacturaContratoVenta = (ContratoVentaFacturaContratoVenta)this.em.createQuery(select).getSingleResult();
/* 37:   */     
/* 38:54 */     return contratoVentaFacturaContratoVenta;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ContratoVentaFacturaContratoVentaDao
 * JD-Core Version:    0.7.0.1
 */