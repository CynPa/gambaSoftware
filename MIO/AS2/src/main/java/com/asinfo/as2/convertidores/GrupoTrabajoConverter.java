/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.nomina.asistencia.GrupoTrabajo;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=GrupoTrabajo.class)
/*  7:   */ public class GrupoTrabajoConverter
/*  8:   */   extends EntidadBaseConverter<GrupoTrabajo>
/*  9:   */ {
/* 10:   */   public GrupoTrabajoConverter()
/* 11:   */   {
/* 12:29 */     super("com.asinfo.as2.entities.nomina.asistencia.GrupoTrabajo");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.GrupoTrabajoConverter
 * JD-Core Version:    0.7.0.1
 */