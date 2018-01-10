/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.IndicadorFinanciero;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ import javax.persistence.EntityManager;
/*  6:   */ 
/*  7:   */ @Stateless
/*  8:   */ public class IndicadorFinancieroDao
/*  9:   */   extends AbstractDaoAS2<IndicadorFinanciero>
/* 10:   */ {
/* 11:   */   public IndicadorFinancieroDao()
/* 12:   */   {
/* 13:50 */     super(IndicadorFinanciero.class);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void eliminar(IndicadorFinanciero entidad)
/* 17:   */   {
/* 18:56 */     this.em.merge(entidad);
/* 19:57 */     if (entidad.getCodigo() != null) {
/* 20:58 */       entidad.setCodigo(null);
/* 21:   */     }
/* 22:60 */     super.eliminar(entidad);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public IndicadorFinanciero cargarDetalle(int idIndicadorFinanciero)
/* 26:   */   {
/* 27:66 */     IndicadorFinanciero indicadorFinanciero = (IndicadorFinanciero)buscarPorId(Integer.valueOf(idIndicadorFinanciero));
/* 28:67 */     return indicadorFinanciero;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.IndicadorFinancieroDao
 * JD-Core Version:    0.7.0.1
 */