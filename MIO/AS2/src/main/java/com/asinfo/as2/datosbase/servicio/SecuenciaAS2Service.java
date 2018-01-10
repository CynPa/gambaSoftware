/*  1:   */ package com.asinfo.as2.datosbase.servicio;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.db.AS2DBBase;
/*  4:   */ import com.asinfo.as2.entities.SecuenciaAS2;
/*  5:   */ import javax.ejb.LocalBean;
/*  6:   */ import javax.ejb.Lock;
/*  7:   */ import javax.ejb.LockType;
/*  8:   */ import javax.ejb.Singleton;
/*  9:   */ import javax.ejb.Startup;
/* 10:   */ import javax.persistence.EntityManager;
/* 11:   */ import javax.persistence.NoResultException;
/* 12:   */ import javax.persistence.Query;
/* 13:   */ 
/* 14:   */ @Singleton
/* 15:   */ @LocalBean
/* 16:   */ @Startup
/* 17:   */ public class SecuenciaAS2Service
/* 18:   */   extends AS2DBBase
/* 19:   */ {
/* 20:32 */   private static String querySecuencia = "SELECT s.siguienteSecuencia FROM SecuenciaAS2 s WHERE s.nombreSecuencia= :nombreSecuencia AND s.idOrganizacion = :idOrganizacion";
/* 21:   */   
/* 22:   */   public String getSecuencia(int idOrganizacion, String nombreSecuencia)
/* 23:   */   {
/* 24:35 */     return getSecuencia(idOrganizacion, nombreSecuencia, null);
/* 25:   */   }
/* 26:   */   
/* 27:   */   @Lock(LockType.WRITE)
/* 28:   */   public String getSecuencia(int idOrganizacion, String nombreSecuencia, Integer longitudSecuencia)
/* 29:   */   {
/* 30:48 */     if (longitudSecuencia == null) {
/* 31:49 */       longitudSecuencia = Integer.valueOf(10);
/* 32:   */     }
/* 33:51 */     Long siguienteSecuencia = Long.valueOf(1L);
/* 34:   */     try
/* 35:   */     {
/* 36:54 */       Query query2 = this.em.createQuery(querySecuencia);
/* 37:55 */       query2.setParameter("nombreSecuencia", nombreSecuencia);
/* 38:56 */       query2.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 39:57 */       siguienteSecuencia = (Long)query2.getSingleResult();
/* 40:   */     }
/* 41:   */     catch (NoResultException e)
/* 42:   */     {
/* 43:61 */       SecuenciaAS2 secuencia = new SecuenciaAS2();
/* 44:62 */       secuencia.setNombreSecuencia(nombreSecuencia);
/* 45:63 */       secuencia.setSiguienteSecuencia(siguienteSecuencia);
/* 46:64 */       secuencia.setIdOrganizacion(idOrganizacion);
/* 47:65 */       this.em.persist(secuencia);
/* 48:   */     }
/* 49:69 */     Query query = this.em.createQuery("UPDATE SecuenciaAS2 s SET s.siguienteSecuencia = :secuencia WHERE                    s.nombreSecuencia= :nombreSecuencia AND s.idOrganizacion = :idOrganizacion ");
/* 50:   */     
/* 51:71 */     query.setParameter("nombreSecuencia", nombreSecuencia);
/* 52:72 */     query.setParameter("secuencia", Long.valueOf(siguienteSecuencia.longValue() + 1L));
/* 53:73 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 54:74 */     query.executeUpdate();
/* 55:75 */     this.em.flush();
/* 56:   */     
/* 57:77 */     String strSecuencia = siguienteSecuencia.toString();
/* 58:78 */     int k = longitudSecuencia.intValue() - strSecuencia.trim().length();
/* 59:79 */     for (int i = 1; i <= k; i++) {
/* 60:80 */       strSecuencia = "0" + strSecuencia;
/* 61:   */     }
/* 62:83 */     return strSecuencia;
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.SecuenciaAS2Service
 * JD-Core Version:    0.7.0.1
 */