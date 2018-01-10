/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.nomina.asistencia.Turno;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Turno.class)
/*  7:   */ public class TurnoConverter
/*  8:   */   extends EntidadBaseConverter<Turno>
/*  9:   */ {
/* 10:   */   public TurnoConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.nomina.asistencia.Turno");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TurnoConverter
 * JD-Core Version:    0.7.0.1
 */