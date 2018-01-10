/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.enumeraciones.MetodoFacturacionEnum;
/*  4:   */ import javax.faces.convert.EnumConverter;
/*  5:   */ import javax.faces.convert.FacesConverter;
/*  6:   */ 
/*  7:   */ @FacesConverter(forClass=MetodoFacturacionEnum.class)
/*  8:   */ public class MetodoFacturacionEnumConverter
/*  9:   */   extends EnumConverter
/* 10:   */ {
/* 11:   */   public MetodoFacturacionEnumConverter()
/* 12:   */   {
/* 13:29 */     super(MetodoFacturacionEnum.class);
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.MetodoFacturacionEnumConverter
 * JD-Core Version:    0.7.0.1
 */