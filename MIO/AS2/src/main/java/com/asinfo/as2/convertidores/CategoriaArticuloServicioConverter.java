/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.mantenimiento.old.CategoriaArticuloServicio;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CategoriaArticuloServicio.class)
/*  7:   */ public class CategoriaArticuloServicioConverter
/*  8:   */   extends EntidadBaseConverter<CategoriaArticuloServicio>
/*  9:   */ {
/* 10:   */   public CategoriaArticuloServicioConverter()
/* 11:   */   {
/* 12:28 */     super("com.asinfo.as2.entities.mantenimiento.CategoriaArticuloServicio");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CategoriaArticuloServicioConverter
 * JD-Core Version:    0.7.0.1
 */