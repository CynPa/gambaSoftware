/*  1:   */ package com.asinfo.as2.utils.comparator;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*  4:   */ import java.util.Comparator;
/*  5:   */ 
/*  6:   */ public class ConceptoRetencionSRIComparator
/*  7:   */   implements Comparator<ConceptoRetencionSRI>
/*  8:   */ {
/*  9:   */   public int compare(ConceptoRetencionSRI o1, ConceptoRetencionSRI o2)
/* 10:   */   {
/* 11:11 */     return o1.getClave().compareToIgnoreCase(o2.getClave());
/* 12:   */   }
/* 13:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.comparator.ConceptoRetencionSRIComparator
 * JD-Core Version:    0.7.0.1
 */