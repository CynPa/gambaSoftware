/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Producto;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Producto.class)
/*  7:   */ public class ProductoConverter
/*  8:   */   extends EntidadBaseConverter<Producto>
/*  9:   */ {
/* 10:   */   public ProductoConverter()
/* 11:   */   {
/* 12:29 */     super("com.ec.asinfo.as2.entities.Producto");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.ProductoConverter
 * JD-Core Version:    0.7.0.1
 */