/*  1:   */ package com.asinfo.as2.dao.sri;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.sri.PorcentajeImpuestoRentaAnual;
/*  5:   */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  6:   */ import java.math.BigDecimal;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class PorcentajeImpuestoRentaAnualDao
/* 13:   */   extends AbstractDaoAS2<PorcentajeImpuestoRentaAnual>
/* 14:   */ {
/* 15:   */   public PorcentajeImpuestoRentaAnualDao()
/* 16:   */   {
/* 17:36 */     super(PorcentajeImpuestoRentaAnual.class);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public BigDecimal obtenerPorcentajePorAnio(int anio)
/* 21:   */     throws ExcepcionAS2Financiero
/* 22:   */   {
/* 23:48 */     Query query = this.em.createQuery(" SELECT COALESCE(pira.porcentaje,0)  FROM PorcentajeImpuestoRentaAnual pira WHERE pira.anio = :anio");
/* 24:   */     
/* 25:   */ 
/* 26:51 */     query.setParameter("anio", Integer.valueOf(anio));
/* 27:   */     try
/* 28:   */     {
/* 29:53 */       return (BigDecimal)query.getSingleResult();
/* 30:   */     }
/* 31:   */     catch (Exception e)
/* 32:   */     {
/* 33:56 */       throw new ExcepcionAS2Financiero("msg_error_porcentaje_impuesto_renta");
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.PorcentajeImpuestoRentaAnualDao
 * JD-Core Version:    0.7.0.1
 */