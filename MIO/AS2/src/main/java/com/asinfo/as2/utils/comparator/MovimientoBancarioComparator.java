/*  1:   */ package com.asinfo.as2.utils.comparator;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.MovimientoBancario;
/*  4:   */ import java.util.Comparator;
/*  5:   */ 
/*  6:   */ public class MovimientoBancarioComparator
/*  7:   */   implements Comparator<MovimientoBancario>
/*  8:   */ {
/*  9:   */   public int compare(MovimientoBancario o1, MovimientoBancario o2)
/* 10:   */   {
/* 11:11 */     String documentoReferencia1 = o1.getDocumentoReferencia().replaceFirst("^0*", "");
/* 12:12 */     String documentoReferencia2 = o2.getDocumentoReferencia().replaceFirst("^0*", "");
/* 13:   */     
/* 14:14 */     return documentoReferencia1.compareToIgnoreCase(documentoReferencia2);
/* 15:   */   }
/* 16:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.comparator.MovimientoBancarioComparator
 * JD-Core Version:    0.7.0.1
 */