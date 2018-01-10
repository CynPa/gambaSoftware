/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Proveedor;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ 
/*  6:   */ @Stateless
/*  7:   */ public class ProveedorDao
/*  8:   */   extends AbstractDaoAS2<Proveedor>
/*  9:   */ {
/* 10:   */   public ProveedorDao()
/* 11:   */   {
/* 12:26 */     super(Proveedor.class);
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ProveedorDao
 * JD-Core Version:    0.7.0.1
 */