/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.IndicadorFinanciero;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=IndicadorFinanciero.class, value="indicadorFinancieroConverter")
/*  7:   */ public class IndicadorFinancieroConverter
/*  8:   */   extends EntidadBaseConverter<IndicadorFinanciero>
/*  9:   */ {
/* 10:   */   public IndicadorFinancieroConverter()
/* 11:   */   {
/* 12:29 */     super("IndicadorFinanciero");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.IndicadorFinancieroConverter
 * JD-Core Version:    0.7.0.1
 */