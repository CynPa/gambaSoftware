/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.produccion.TarifaOperacion;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=TarifaOperacion.class)
/*  7:   */ public class TarifaOperacionConverter
/*  8:   */   extends EntidadBaseConverter<TarifaOperacion>
/*  9:   */ {
/* 10:   */   public TarifaOperacionConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.produccion.TarifaOperacion");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TarifaOperacionConverter
 * JD-Core Version:    0.7.0.1
 */