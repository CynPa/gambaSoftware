/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EstadoCheque;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=EstadoCheque.class)
/*  7:   */ public class EstadoChequeConverter
/*  8:   */   extends EntidadBaseConverter<EstadoCheque>
/*  9:   */ {
/* 10:   */   public EstadoChequeConverter()
/* 11:   */   {
/* 12:21 */     super("com.asinfo.as2.entities.EstadoCheque");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.EstadoChequeConverter
 * JD-Core Version:    0.7.0.1
 */