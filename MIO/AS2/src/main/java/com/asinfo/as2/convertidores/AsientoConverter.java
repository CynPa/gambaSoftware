/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Asiento;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Asiento.class)
/*  7:   */ public class AsientoConverter
/*  8:   */   extends EntidadBaseConverter<Asiento>
/*  9:   */ {
/* 10:   */   public AsientoConverter()
/* 11:   */   {
/* 12:25 */     super("com.asinfo.as2.entities.Asiento");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.AsientoConverter
 * JD-Core Version:    0.7.0.1
 */