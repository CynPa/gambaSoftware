/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.TipoPrestamo;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=TipoPrestamo.class)
/*  7:   */ public class TipoPrestamoConverter
/*  8:   */   extends EntidadBaseConverter<TipoPrestamo>
/*  9:   */ {
/* 10:   */   public TipoPrestamoConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.TipoPrestamo");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TipoPrestamoConverter
 * JD-Core Version:    0.7.0.1
 */