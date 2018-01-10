/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.calidad.MotivoCastigoCalidad;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=MotivoCastigoCalidad.class)
/*  7:   */ public class MotivoCastigoCalidadConverter
/*  8:   */   extends EntidadBaseConverter<MotivoCastigoCalidad>
/*  9:   */ {
/* 10:   */   public MotivoCastigoCalidadConverter()
/* 11:   */   {
/* 12:21 */     super("com.asinfo.as2.entities.inventario.calidad.MotivoCastigoCalidad");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.MotivoCastigoCalidadConverter
 * JD-Core Version:    0.7.0.1
 */