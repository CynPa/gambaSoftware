/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Lote;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Lote.class)
/*  7:   */ public class LoteConverter
/*  8:   */   extends EntidadBaseConverter<Lote>
/*  9:   */ {
/* 10:   */   public LoteConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.Lote");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.LoteConverter
 * JD-Core Version:    0.7.0.1
 */