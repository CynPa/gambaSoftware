/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Atributo;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Atributo.class)
/*  7:   */ public class AtributoConverter
/*  8:   */   extends EntidadBaseConverter<Atributo>
/*  9:   */ {
/* 10:   */   public AtributoConverter()
/* 11:   */   {
/* 12:26 */     super("com.ec.asinfo.as2.entities.Atributo");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.AtributoConverter
 * JD-Core Version:    0.7.0.1
 */