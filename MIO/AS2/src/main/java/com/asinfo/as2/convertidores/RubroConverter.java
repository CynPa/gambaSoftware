/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Rubro;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Rubro.class)
/*  7:   */ public class RubroConverter
/*  8:   */   extends EntidadBaseConverter<Rubro>
/*  9:   */ {
/* 10:   */   public RubroConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.Rubro");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.RubroConverter
 * JD-Core Version:    0.7.0.1
 */