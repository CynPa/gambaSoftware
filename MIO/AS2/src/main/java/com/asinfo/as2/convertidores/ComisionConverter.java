/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Comision;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Comision.class)
/*  7:   */ public class ComisionConverter
/*  8:   */   extends EntidadBaseConverter<Comision>
/*  9:   */ {
/* 10:   */   public ComisionConverter()
/* 11:   */   {
/* 12:29 */     super("com.asinfo.as2.entities.Comision");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.ComisionConverter
 * JD-Core Version:    0.7.0.1
 */