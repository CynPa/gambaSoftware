/*  1:   */ package com.asinfo.as2.utils.comparator;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import javax.faces.model.SelectItem;
/*  5:   */ 
/*  6:   */ public class SelectItemComparator
/*  7:   */   implements Comparator<SelectItem>
/*  8:   */ {
/*  9:   */   public int compare(SelectItem o1, SelectItem o2)
/* 10:   */   {
/* 11:30 */     return o1.getLabel().compareTo(o2.getLabel());
/* 12:   */   }
/* 13:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.comparator.SelectItemComparator
 * JD-Core Version:    0.7.0.1
 */