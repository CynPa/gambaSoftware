/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.mantenimiento.old.TarifaSalarial;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=TarifaSalarial.class)
/*  7:   */ public class TarifaSalarialConverter
/*  8:   */   extends EntidadBaseConverter<TarifaSalarial>
/*  9:   */ {
/* 10:   */   public TarifaSalarialConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.mantenimiento.TarifaSalarial");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TarifaSalarialConverter
 * JD-Core Version:    0.7.0.1
 */