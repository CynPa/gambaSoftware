/*  1:   */ package com.asinfo.as2.utils;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collection;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class Utilidades
/*  8:   */ {
/*  9:   */   public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c)
/* 10:   */   {
/* 11:10 */     List<T> r = new ArrayList(c.size());
/* 12:11 */     for (Object o : c) {
/* 13:12 */       r.add(clazz.cast(o));
/* 14:   */     }
/* 15:15 */     return r;
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.Utilidades
 * JD-Core Version:    0.7.0.1
 */