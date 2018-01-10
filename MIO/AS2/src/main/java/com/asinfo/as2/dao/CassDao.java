/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.aerolineas.Cass;
/*  4:   */ import com.asinfo.as2.enumeraciones.Estado;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ import javax.persistence.EntityManager;
/*  7:   */ import javax.persistence.NoResultException;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class CassDao
/* 12:   */   extends AbstractDaoAS2<Cass>
/* 13:   */ {
/* 14:   */   public CassDao()
/* 15:   */   {
/* 16:26 */     super(Cass.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Cass obtenerCassUnico(int idOrganizacion, String recordId, String cassAreaCode, Integer airlinePrefix, Integer datePeriodStart, Integer datePeriodEnd, Integer dateOfBilling, Integer fileNumber, String currencyCode)
/* 20:   */   {
/* 21:32 */     StringBuilder sql = new StringBuilder();
/* 22:33 */     sql.append(" SELECT c FROM Cass c ");
/* 23:34 */     sql.append(" WHERE c.idOrganizacion = :idOrganizacion ");
/* 24:35 */     sql.append(" AND c.recordId = :recordId ");
/* 25:36 */     sql.append(" AND c.cassAreaCode = :cassAreaCode ");
/* 26:37 */     sql.append(" AND c.airlinePrefix = :airlinePrefix ");
/* 27:38 */     sql.append(" AND c.datePeriodStart = :datePeriodStart ");
/* 28:39 */     sql.append(" AND c.datePeriodEnd = :datePeriodEnd ");
/* 29:40 */     sql.append(" AND c.dateOfBilling = :dateOfBilling ");
/* 30:41 */     sql.append(" AND c.fileNumber = :fileNumber ");
/* 31:42 */     sql.append(" AND c.currencyCode = :currencyCode ");
/* 32:43 */     sql.append(" AND c.estado != :estado ");
/* 33:   */     
/* 34:45 */     Query query = this.em.createQuery(sql.toString());
/* 35:46 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 36:47 */     query.setParameter("recordId", recordId);
/* 37:48 */     query.setParameter("cassAreaCode", cassAreaCode);
/* 38:49 */     query.setParameter("airlinePrefix", airlinePrefix);
/* 39:50 */     query.setParameter("datePeriodStart", datePeriodStart);
/* 40:51 */     query.setParameter("datePeriodEnd", datePeriodEnd);
/* 41:52 */     query.setParameter("dateOfBilling", dateOfBilling);
/* 42:53 */     query.setParameter("fileNumber", fileNumber);
/* 43:54 */     query.setParameter("currencyCode", currencyCode);
/* 44:55 */     query.setParameter("estado", Estado.ANULADO);
/* 45:   */     try
/* 46:   */     {
/* 47:58 */       return (Cass)query.getSingleResult();
/* 48:   */     }
/* 49:   */     catch (NoResultException e) {}
/* 50:60 */     return null;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CassDao
 * JD-Core Version:    0.7.0.1
 */