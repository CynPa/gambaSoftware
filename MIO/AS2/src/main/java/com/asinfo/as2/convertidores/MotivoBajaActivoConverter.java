/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=MotivoBajaActivo.class)
/*  7:   */ public class MotivoBajaActivoConverter
/*  8:   */   extends EntidadBaseConverter<MotivoBajaActivo>
/*  9:   */ {
/* 10:   */   public MotivoBajaActivoConverter()
/* 11:   */   {
/* 12:13 */     super("com.asinfo.as2.entities.MotivoBajaActivo");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.MotivoBajaActivoConverter
 * JD-Core Version:    0.7.0.1
 */