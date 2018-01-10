/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Calificacion;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Calificacion.class)
/*  7:   */ public class CalificacionConverter
/*  8:   */   extends EntidadBaseConverter<Calificacion>
/*  9:   */ {
/* 10:   */   public CalificacionConverter()
/* 11:   */   {
/* 12:17 */     super("com.asinfo.as2.entities.Calificacion");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CalificacionConverter
 * JD-Core Version:    0.7.0.1
 */