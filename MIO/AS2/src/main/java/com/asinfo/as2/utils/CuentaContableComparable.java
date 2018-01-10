/*  1:   */ package com.asinfo.as2.utils;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CuentaContable;
/*  4:   */ import com.asinfo.as2.entities.DetalleAsiento;
/*  5:   */ import java.util.Comparator;
/*  6:   */ 
/*  7:   */ public class CuentaContableComparable
/*  8:   */   implements Comparator<DetalleAsiento>
/*  9:   */ {
/* 10:   */   public int compare(DetalleAsiento o1, DetalleAsiento o2)
/* 11:   */   {
/* 12:30 */     return o1.getCuentaContable().getCodigo().compareTo(o2.getCuentaContable().getCodigo()) * -1;
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.CuentaContableComparable
 * JD-Core Version:    0.7.0.1
 */