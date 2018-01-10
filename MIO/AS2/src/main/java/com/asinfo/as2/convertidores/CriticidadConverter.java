/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.mantenimiento.old.Criticidad;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Criticidad.class)
/*  7:   */ public class CriticidadConverter
/*  8:   */   extends EntidadBaseConverter<Criticidad>
/*  9:   */ {
/* 10:   */   public CriticidadConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.mantenimiento.Criticidad");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CriticidadConverter
 * JD-Core Version:    0.7.0.1
 */