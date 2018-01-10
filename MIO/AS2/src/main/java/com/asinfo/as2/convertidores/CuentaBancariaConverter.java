/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CuentaBancaria;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CuentaBancaria.class)
/*  7:   */ public class CuentaBancariaConverter
/*  8:   */   extends EntidadBaseConverter<CuentaBancaria>
/*  9:   */ {
/* 10:   */   public CuentaBancariaConverter()
/* 11:   */   {
/* 12:26 */     super("com.asinfo.as2.entities.CuentaBancaria");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CuentaBancariaConverter
 * JD-Core Version:    0.7.0.1
 */