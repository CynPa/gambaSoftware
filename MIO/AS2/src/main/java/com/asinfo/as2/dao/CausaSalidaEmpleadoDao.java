/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CausaSalidaEmpleado;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ 
/*  6:   */ @Stateless
/*  7:   */ public class CausaSalidaEmpleadoDao
/*  8:   */   extends AbstractDaoAS2<CausaSalidaEmpleado>
/*  9:   */ {
/* 10:   */   public CausaSalidaEmpleadoDao()
/* 11:   */   {
/* 12:30 */     super(CausaSalidaEmpleado.class);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public CausaSalidaEmpleado cargarDetalle(int idCausaSalidaEmpleado)
/* 16:   */   {
/* 17:40 */     return null;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CausaSalidaEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */