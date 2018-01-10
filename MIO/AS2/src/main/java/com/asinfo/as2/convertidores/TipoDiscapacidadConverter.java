/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.TipoDiscapacidad;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=TipoDiscapacidad.class)
/*  7:   */ public class TipoDiscapacidadConverter
/*  8:   */   extends EntidadBaseConverter<TipoDiscapacidad>
/*  9:   */ {
/* 10:   */   public TipoDiscapacidadConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.TipoDiscapacidad");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TipoDiscapacidadConverter
 * JD-Core Version:    0.7.0.1
 */