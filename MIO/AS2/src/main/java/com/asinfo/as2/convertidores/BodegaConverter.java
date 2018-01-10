/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Bodega;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Bodega.class)
/*  7:   */ public class BodegaConverter
/*  8:   */   extends EntidadBaseConverter<Bodega>
/*  9:   */ {
/* 10:   */   public BodegaConverter()
/* 11:   */   {
/* 12:27 */     super("com.asinfo.as2.entities.Bodega");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.BodegaConverter
 * JD-Core Version:    0.7.0.1
 */