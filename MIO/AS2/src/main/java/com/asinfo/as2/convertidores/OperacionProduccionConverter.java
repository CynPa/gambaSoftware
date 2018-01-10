/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.produccion.OperacionProduccion;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=OperacionProduccion.class)
/*  7:   */ public class OperacionProduccionConverter
/*  8:   */   extends EntidadBaseConverter<OperacionProduccion>
/*  9:   */ {
/* 10:   */   public OperacionProduccionConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.produccion.OperacionProduccion");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.OperacionProduccionConverter
 * JD-Core Version:    0.7.0.1
 */