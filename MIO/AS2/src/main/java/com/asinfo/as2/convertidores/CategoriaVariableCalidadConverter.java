/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.calidad.CategoriaVariableCalidad;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CategoriaVariableCalidad.class)
/*  7:   */ public class CategoriaVariableCalidadConverter
/*  8:   */   extends EntidadBaseConverter<CategoriaVariableCalidad>
/*  9:   */ {
/* 10:   */   public CategoriaVariableCalidadConverter()
/* 11:   */   {
/* 12:21 */     super("com.asinfo.as2.entities.inventario.calidad.CategoriaVariableCalidad");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CategoriaVariableCalidadConverter
 * JD-Core Version:    0.7.0.1
 */