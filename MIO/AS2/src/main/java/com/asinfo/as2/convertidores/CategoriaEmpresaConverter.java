/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CategoriaEmpresa.class)
/*  7:   */ public class CategoriaEmpresaConverter
/*  8:   */   extends EntidadBaseConverter<CategoriaEmpresa>
/*  9:   */ {
/* 10:   */   public CategoriaEmpresaConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.CategoriaEmpresa");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CategoriaEmpresaConverter
 * JD-Core Version:    0.7.0.1
 */