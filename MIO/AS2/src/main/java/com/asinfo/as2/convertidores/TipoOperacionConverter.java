/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.TipoOperacion;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=TipoOperacion.class)
/*  7:   */ public class TipoOperacionConverter
/*  8:   */   extends EntidadBaseConverter<TipoOperacion>
/*  9:   */ {
/* 10:   */   public TipoOperacionConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.TipoOperacion");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TipoOperacionConverter
 * JD-Core Version:    0.7.0.1
 */