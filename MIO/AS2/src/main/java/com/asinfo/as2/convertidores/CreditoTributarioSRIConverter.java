/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CreditoTributarioSRI.class, value="creditoTributarioSRIConverter")
/*  7:   */ public class CreditoTributarioSRIConverter
/*  8:   */   extends EntidadBaseConverter<CreditoTributarioSRI>
/*  9:   */ {
/* 10:   */   public CreditoTributarioSRIConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.sri.CreditoTributarioSRI");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CreditoTributarioSRIConverter
 * JD-Core Version:    0.7.0.1
 */