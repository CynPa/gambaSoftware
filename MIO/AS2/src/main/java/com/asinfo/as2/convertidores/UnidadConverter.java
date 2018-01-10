/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Unidad;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Unidad.class)
/*  7:   */ public class UnidadConverter
/*  8:   */   extends EntidadBaseConverter<Unidad>
/*  9:   */ {
/* 10:   */   public UnidadConverter()
/* 11:   */   {
/* 12:27 */     super("com.asinfo.as2.entities.Unidad");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.UnidadConverter
 * JD-Core Version:    0.7.0.1
 */