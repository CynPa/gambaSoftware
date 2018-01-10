/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Caja;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Caja.class)
/*  7:   */ public class CajaConverter
/*  8:   */   extends EntidadBaseConverter<Caja>
/*  9:   */ {
/* 10:   */   public CajaConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.Caja");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CajaConverter
 * JD-Core Version:    0.7.0.1
 */