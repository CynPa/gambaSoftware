/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleContratoVenta;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=DetalleContratoVenta.class)
/*  7:   */ public class DetalleContratoVentaConverter
/*  8:   */   extends EntidadBaseConverter<DetalleContratoVenta>
/*  9:   */ {
/* 10:   */   public DetalleContratoVentaConverter()
/* 11:   */   {
/* 12:21 */     super("com.asinfo.as2.entities.DetalleContratoVenta");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.DetalleContratoVentaConverter
 * JD-Core Version:    0.7.0.1
 */