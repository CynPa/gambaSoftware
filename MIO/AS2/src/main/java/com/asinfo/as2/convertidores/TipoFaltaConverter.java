/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.nomina.asistencia.TipoFalta;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=TipoFalta.class)
/*  7:   */ public class TipoFaltaConverter
/*  8:   */   extends EntidadBaseConverter<TipoFalta>
/*  9:   */ {
/* 10:   */   public TipoFaltaConverter()
/* 11:   */   {
/* 12:25 */     super("com.asinfo.as2.entities.nomina.asistencia.TipoFalta");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TipoFaltaConverter
 * JD-Core Version:    0.7.0.1
 */