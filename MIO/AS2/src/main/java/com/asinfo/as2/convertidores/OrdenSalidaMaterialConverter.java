/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=OrdenSalidaMaterial.class)
/*  7:   */ public class OrdenSalidaMaterialConverter
/*  8:   */   extends EntidadBaseConverter<OrdenSalidaMaterial>
/*  9:   */ {
/* 10:   */   public OrdenSalidaMaterialConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.OrdenSalidaMaterial");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.OrdenSalidaMaterialConverter
 * JD-Core Version:    0.7.0.1
 */