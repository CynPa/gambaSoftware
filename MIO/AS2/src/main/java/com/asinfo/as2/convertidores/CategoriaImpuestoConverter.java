/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CategoriaImpuesto.class)
/*  7:   */ public class CategoriaImpuestoConverter
/*  8:   */   extends EntidadBaseConverter<CategoriaImpuesto>
/*  9:   */ {
/* 10:   */   public CategoriaImpuestoConverter()
/* 11:   */   {
/* 12:33 */     super("com.asinfo.as2.entities.CategoriaImpuesto");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CategoriaImpuestoConverter
 * JD-Core Version:    0.7.0.1
 */