/*  1:   */ package com.asinfo.as2.compronteselectronicos.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.sri.SecuenciaDocumentoElectronicoDao;
/*  4:   */ import com.asinfo.as2.entities.sri.SecuenciaDocumentoElectronico;
/*  5:   */ import javax.ejb.EJB;
/*  6:   */ import javax.ejb.LocalBean;
/*  7:   */ import javax.ejb.Lock;
/*  8:   */ import javax.ejb.LockType;
/*  9:   */ import javax.ejb.Singleton;
/* 10:   */ import javax.persistence.EntityManager;
/* 11:   */ import javax.persistence.NoResultException;
/* 12:   */ import javax.persistence.PersistenceContext;
/* 13:   */ import javax.persistence.Query;
/* 14:   */ 
/* 15:   */ @LocalBean
/* 16:   */ @Singleton
/* 17:   */ public class ServicioSecuenciaDocumentoElectronico
/* 18:   */ {
/* 19:   */   @EJB
/* 20:   */   private SecuenciaDocumentoElectronicoDao secuenciaDocumentoElectronicoDao;
/* 21:   */   @PersistenceContext(name="AS2PU")
/* 22:   */   protected EntityManager em;
/* 23:   */   
/* 24:   */   @Lock(LockType.WRITE)
/* 25:   */   public String generarSecuenciaDocumento(int longitud)
/* 26:   */   {
/* 27:45 */     Query query = this.em.createQuery("SELECT s.siguienteSecuencia FROM SecuenciaDocumentoElectronico s");
/* 28:   */     Integer secuencia;
/* 29:   */     try
/* 30:   */     {
/* 31:48 */       Integer secuencia = (Integer)query.getSingleResult();
/* 32:   */       
/* 33:   */ 
/* 34:51 */       query = this.em.createQuery("UPDATE SecuenciaDocumentoElectronico s SET s.siguienteSecuencia = (:secuencia + 1)");
/* 35:52 */       query.setParameter("secuencia", secuencia);
/* 36:53 */       query.executeUpdate();
/* 37:   */     }
/* 38:   */     catch (NoResultException e)
/* 39:   */     {
/* 40:56 */       SecuenciaDocumentoElectronico objSecuencia = new SecuenciaDocumentoElectronico();
/* 41:57 */       objSecuencia.setSiguienteSecuencia(2);
/* 42:58 */       this.secuenciaDocumentoElectronicoDao.guardar(objSecuencia);
/* 43:59 */       secuencia = Integer.valueOf(1);
/* 44:   */     }
/* 45:62 */     String strSecuencia = secuencia.toString();
/* 46:63 */     int k = longitud - strSecuencia.trim().length();
/* 47:64 */     for (int i = 1; i <= k; i++) {
/* 48:65 */       strSecuencia = "0" + strSecuencia;
/* 49:   */     }
/* 50:68 */     return strSecuencia;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.servicio.impl.ServicioSecuenciaDocumentoElectronico
 * JD-Core Version:    0.7.0.1
 */