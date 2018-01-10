/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CategoriaEquipo.class)
/*  7:   */ public class CategoriaEquipoConverter
/*  8:   */   extends EntidadBaseConverter<CategoriaEquipo>
/*  9:   */ {
/* 10:   */   public CategoriaEquipoConverter()
/* 11:   */   {
/* 12:21 */     super("com.asinfo.as2.entities.mantenimiento.CategoriaEquipo");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CategoriaEquipoConverter
 * JD-Core Version:    0.7.0.1
 */