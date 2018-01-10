/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CentroTrabajo;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CentroTrabajo.class)
/*  7:   */ public class CentroTrabajoConverter
/*  8:   */   extends EntidadBaseConverter<CentroTrabajo>
/*  9:   */ {
/* 10:   */   public CentroTrabajoConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.mantenimiento.CentroTrabajo");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CentroTrabajoConverter
 * JD-Core Version:    0.7.0.1
 */