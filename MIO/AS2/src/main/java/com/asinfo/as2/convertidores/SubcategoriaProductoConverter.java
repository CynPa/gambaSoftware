/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=SubcategoriaProducto.class)
/*  7:   */ public class SubcategoriaProductoConverter
/*  8:   */   extends EntidadBaseConverter<SubcategoriaProducto>
/*  9:   */ {
/* 10:   */   public SubcategoriaProductoConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.SubcategoriaProducto");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.SubcategoriaProductoConverter
 * JD-Core Version:    0.7.0.1
 */