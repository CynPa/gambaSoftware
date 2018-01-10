/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.TipoBodega;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ import javax.persistence.EntityManager;
/*  6:   */ import javax.persistence.Query;
/*  7:   */ 
/*  8:   */ @Stateless
/*  9:   */ public class TipoBodegaDao
/* 10:   */   extends AbstractDaoAS2<TipoBodega>
/* 11:   */ {
/* 12:   */   public TipoBodegaDao()
/* 13:   */   {
/* 14:31 */     super(TipoBodega.class);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public TipoBodega obtenerBodepaPorNombre(String nombre)
/* 18:   */   {
/* 19:41 */     StringBuffer sql = new StringBuffer();
/* 20:42 */     sql.append("SELECT tb FROM TipoBodega tb WHERE tb.nombre = :nombre ");
/* 21:   */     
/* 22:44 */     Query query = this.em.createQuery(sql.toString()).setParameter("nombre", nombre);
/* 23:   */     
/* 24:46 */     return (TipoBodega)query.getSingleResult();
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TipoBodegaDao
 * JD-Core Version:    0.7.0.1
 */