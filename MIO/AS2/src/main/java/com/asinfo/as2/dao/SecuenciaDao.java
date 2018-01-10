/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Secuencia;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ import javax.persistence.EntityManager;
/*  6:   */ import javax.persistence.Query;
/*  7:   */ 
/*  8:   */ @Stateless
/*  9:   */ public class SecuenciaDao
/* 10:   */   extends AbstractDaoAS2<Secuencia>
/* 11:   */ {
/* 12:   */   public SecuenciaDao()
/* 13:   */   {
/* 14:32 */     super(Secuencia.class);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void actualizarSecuencia(Secuencia secuencia, int numero)
/* 18:   */   {
/* 19:43 */     Query query = this.em.createQuery("UPDATE Secuencia s SET numero=:numero WHERE s.idSecuencia=:idSecuencia AND :numero>s.numero");
/* 20:   */     
/* 21:   */ 
/* 22:46 */     query.setParameter("numero", Integer.valueOf(numero));
/* 23:47 */     query.setParameter("idSecuencia", Integer.valueOf(secuencia.getId()));
/* 24:   */     
/* 25:49 */     query.executeUpdate();
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.SecuenciaDao
 * JD-Core Version:    0.7.0.1
 */