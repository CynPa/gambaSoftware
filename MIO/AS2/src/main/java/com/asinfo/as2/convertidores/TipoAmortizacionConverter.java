/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=TipoAmortizacion.class)
/*  7:   */ public class TipoAmortizacionConverter
/*  8:   */   extends EntidadBaseConverter<TipoAmortizacion>
/*  9:   */ {
/* 10:   */   public TipoAmortizacionConverter()
/* 11:   */   {
/* 12:21 */     super("com.asinfo.as2.entities.amortizacion.TipoAmortizacion");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TipoAmortizacionConverter
 * JD-Core Version:    0.7.0.1
 */