/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleVariable;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ 
/*  6:   */ @Stateless
/*  7:   */ public class DetalleVariableDao
/*  8:   */   extends AbstractDaoAS2<DetalleVariable>
/*  9:   */ {
/* 10:   */   public DetalleVariableDao()
/* 11:   */   {
/* 12:49 */     super(DetalleVariable.class);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public DetalleVariable cargarDetalle(int idDetalleVariable)
/* 16:   */   {
/* 17:55 */     DetalleVariable detalleVariable = (DetalleVariable)buscarPorId(Integer.valueOf(idDetalleVariable));
/* 18:56 */     return detalleVariable;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleVariableDao
 * JD-Core Version:    0.7.0.1
 */