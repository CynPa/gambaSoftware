/*  1:   */ package com.asinfo.as2.utils.comparator;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Producto;
/*  4:   */ import java.util.Comparator;
/*  5:   */ 
/*  6:   */ public class ProductoComparator
/*  7:   */   implements Comparator<Producto>
/*  8:   */ {
/*  9:   */   public int compare(Producto o1, Producto o2)
/* 10:   */   {
/* 11:32 */     return o1.getCodigo().compareToIgnoreCase(o2.getCodigo());
/* 12:   */   }
/* 13:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.comparator.ProductoComparator
 * JD-Core Version:    0.7.0.1
 */