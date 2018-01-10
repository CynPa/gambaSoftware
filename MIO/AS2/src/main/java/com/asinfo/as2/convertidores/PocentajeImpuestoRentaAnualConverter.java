/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.sri.PorcentajeImpuestoRentaAnual;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=PorcentajeImpuestoRentaAnual.class, value="porcentajeImpuestoRentaAnualConverter")
/*  7:   */ public class PocentajeImpuestoRentaAnualConverter
/*  8:   */   extends EntidadBaseConverter<PorcentajeImpuestoRentaAnual>
/*  9:   */ {
/* 10:   */   public PocentajeImpuestoRentaAnualConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.sri.PorcentajeImpuestoRentaAnual");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.PocentajeImpuestoRentaAnualConverter
 * JD-Core Version:    0.7.0.1
 */