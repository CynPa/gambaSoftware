/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.TipoContacto;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=TipoContacto.class)
/*  7:   */ public class TipoContactoConverter
/*  8:   */   extends EntidadBaseConverter<TipoContacto>
/*  9:   */ {
/* 10:   */   public TipoContactoConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.TipoContacto");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TipoContactoConverter
 * JD-Core Version:    0.7.0.1
 */