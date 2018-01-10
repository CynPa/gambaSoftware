/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Especialidad;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Especialidad.class)
/*  7:   */ public class EspecialidadConverter
/*  8:   */   extends EntidadBaseConverter<Especialidad>
/*  9:   */ {
/* 10:   */   public EspecialidadConverter()
/* 11:   */   {
/* 12:27 */     super("com.asinfo.as2.entities.afiliaciones.Especialidad");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.EspecialidadConverter
 * JD-Core Version:    0.7.0.1
 */