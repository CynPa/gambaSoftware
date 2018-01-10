/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Moneda;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ import javax.persistence.EntityManager;
/*  6:   */ import javax.persistence.Query;
/*  7:   */ 
/*  8:   */ @Stateless
/*  9:   */ public class MonedaDao
/* 10:   */   extends AbstractDaoAS2<Moneda>
/* 11:   */ {
/* 12:   */   public MonedaDao()
/* 13:   */   {
/* 14:27 */     super(Moneda.class);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Moneda obtenerPorCodigo(String codigo)
/* 18:   */   {
/* 19:37 */     StringBuffer sql = new StringBuffer();
/* 20:38 */     sql.append("SELECT m FROM Moneda m WHERE m.codigo = :codigo");
/* 21:   */     
/* 22:40 */     Query query = this.em.createQuery(sql.toString());
/* 23:41 */     query.setParameter("codigo", codigo);
/* 24:   */     
/* 25:43 */     return (Moneda)query.getSingleResult();
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.MonedaDao
 * JD-Core Version:    0.7.0.1
 */