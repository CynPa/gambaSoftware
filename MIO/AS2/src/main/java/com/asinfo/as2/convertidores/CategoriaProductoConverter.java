/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CategoriaProducto;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CategoriaProducto.class)
/*  7:   */ public class CategoriaProductoConverter
/*  8:   */   extends EntidadBaseConverter<CategoriaProducto>
/*  9:   */ {
/* 10:   */   public CategoriaProductoConverter()
/* 11:   */   {
/* 12:27 */     super("com.ec.asinfo.as2.entities.CategoriaProducto");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CategoriaProductoConverter
 * JD-Core Version:    0.7.0.1
 */