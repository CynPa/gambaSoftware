/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.RegistroPeso;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=RegistroPeso.class)
/*  7:   */ public class RegistroPesoConverter
/*  8:   */   extends EntidadBaseConverter<RegistroPeso>
/*  9:   */ {
/* 10:   */   public RegistroPesoConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.RegistroPeso");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.RegistroPesoConverter
 * JD-Core Version:    0.7.0.1
 */