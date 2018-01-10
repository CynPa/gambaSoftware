/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Provincia;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Provincia.class)
/*  7:   */ public class ProvinciaConverter
/*  8:   */   extends EntidadBaseConverter<Provincia>
/*  9:   */ {
/* 10:   */   public ProvinciaConverter()
/* 11:   */   {
/* 12:27 */     super("com.asinfo.as2.entities.Provincia");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.ProvinciaConverter
 * JD-Core Version:    0.7.0.1
 */