/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=PuntoDeVenta.class)
/*  7:   */ public class PuntoDeVentaConverter
/*  8:   */   extends EntidadBaseConverter<PuntoDeVenta>
/*  9:   */ {
/* 10:   */   public PuntoDeVentaConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.PuntoDeVenta");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.PuntoDeVentaConverter
 * JD-Core Version:    0.7.0.1
 */