/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Ciudad;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Ciudad.class)
/*  7:   */ public class CiudadConverter
/*  8:   */   extends EntidadBaseConverter<Ciudad>
/*  9:   */ {
/* 10:   */   public CiudadConverter()
/* 11:   */   {
/* 12:27 */     super("com.asinfo.as2.entities.Ciudad");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CiudadConverter
 * JD-Core Version:    0.7.0.1
 */