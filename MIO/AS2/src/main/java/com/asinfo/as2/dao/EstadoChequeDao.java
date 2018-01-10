/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EstadoCheque;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ import javax.persistence.EntityManager;
/*  6:   */ import javax.persistence.Query;
/*  7:   */ 
/*  8:   */ @Stateless
/*  9:   */ public class EstadoChequeDao
/* 10:   */   extends AbstractDaoAS2<EstadoCheque>
/* 11:   */ {
/* 12:   */   public EstadoChequeDao()
/* 13:   */   {
/* 14:22 */     super(EstadoCheque.class);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean existeEstadoInicialFinal(EstadoCheque ec)
/* 18:   */   {
/* 19:26 */     StringBuilder sb = new StringBuilder();
/* 20:27 */     sb.append(" SELECT COUNT(1) FROM EstadoCheque ec ");
/* 21:28 */     sb.append(" WHERE 1 = 1 ");
/* 22:29 */     if (ec.getIdEstadoCheque() != 0) {
/* 23:30 */       sb.append(" AND ec != :ec ");
/* 24:   */     }
/* 25:32 */     if (ec.isEstadoInicial()) {
/* 26:33 */       sb.append(" AND ec.estadoInicial = true ");
/* 27:   */     }
/* 28:35 */     if (ec.isEstadoFinal()) {
/* 29:36 */       sb.append(" AND ec.estadoFinal = true ");
/* 30:   */     }
/* 31:38 */     Query qry = this.em.createQuery(sb.toString());
/* 32:39 */     if (ec.getIdEstadoCheque() != 0) {
/* 33:40 */       qry.setParameter("ec", ec);
/* 34:   */     }
/* 35:43 */     return ((Long)qry.getSingleResult()).intValue() > 0;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public EstadoCheque getEstadoInicial(int idOrganizacion)
/* 39:   */   {
/* 40:47 */     StringBuilder sb = new StringBuilder();
/* 41:48 */     sb.append(" SELECT ec FROM EstadoCheque ec ");
/* 42:49 */     sb.append(" WHERE ec.estadoInicial = true ");
/* 43:50 */     sb.append(" AND ec.idOrganizacion = :idOrganizacion");
/* 44:   */     
/* 45:52 */     Query qry = this.em.createQuery(sb.toString());
/* 46:53 */     qry.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 47:   */     
/* 48:55 */     return (EstadoCheque)qry.getSingleResult();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public EstadoCheque getEstadoFinal(int idOrganizacion)
/* 52:   */   {
/* 53:59 */     StringBuilder sb = new StringBuilder();
/* 54:60 */     sb.append(" SELECT ec FROM EstadoCheque ec ");
/* 55:61 */     sb.append(" WHERE ec.estadoFinal = true ");
/* 56:62 */     sb.append(" AND ec.idOrganizacion = :idOrganizacion");
/* 57:   */     
/* 58:64 */     Query qry = this.em.createQuery(sb.toString());
/* 59:65 */     qry.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 60:   */     
/* 61:67 */     return (EstadoCheque)qry.getSingleResult();
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.EstadoChequeDao
 * JD-Core Version:    0.7.0.1
 */