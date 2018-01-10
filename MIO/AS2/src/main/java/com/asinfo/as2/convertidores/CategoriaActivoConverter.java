/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CategoriaActivo;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CategoriaActivo.class)
/*  7:   */ public class CategoriaActivoConverter
/*  8:   */   extends EntidadBaseConverter<CategoriaActivo>
/*  9:   */ {
/* 10:   */   public CategoriaActivoConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.CategoriaActivo");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CategoriaActivoConverter
 * JD-Core Version:    0.7.0.1
 */