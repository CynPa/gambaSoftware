/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Periodo;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Periodo.class)
/*  7:   */ public class PeriodoConverter
/*  8:   */   extends EntidadBaseConverter<Periodo>
/*  9:   */ {
/* 10:   */   public PeriodoConverter()
/* 11:   */   {
/* 12:27 */     super("com.asinfo.as2.entities.Periodo");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.PeriodoConverter
 * JD-Core Version:    0.7.0.1
 */