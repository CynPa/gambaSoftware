/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.enumeraciones.TipoSangre;
/*  4:   */ import javax.faces.convert.EnumConverter;
/*  5:   */ import javax.faces.convert.FacesConverter;
/*  6:   */ 
/*  7:   */ @FacesConverter(forClass=TipoSangre.class)
/*  8:   */ public class TipoSangreConverter
/*  9:   */   extends EnumConverter
/* 10:   */ {
/* 11:   */   public TipoSangreConverter()
/* 12:   */   {
/* 13:27 */     super(TipoSangre.class);
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TipoSangreConverter
 * JD-Core Version:    0.7.0.1
 */