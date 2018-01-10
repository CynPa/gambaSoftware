/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.TipoBodega;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=TipoBodega.class)
/*  7:   */ public class TipoBodegaConverter
/*  8:   */   extends EntidadBaseConverter<TipoBodega>
/*  9:   */ {
/* 10:   */   public TipoBodegaConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.TipoBodega");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TipoBodegaConverter
 * JD-Core Version:    0.7.0.1
 */