/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ComponenteCosto;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=ComponenteCosto.class)
/*  7:   */ public class ComponenteCostoConverter
/*  8:   */   extends EntidadBaseConverter<ComponenteCosto>
/*  9:   */ {
/* 10:   */   public ComponenteCostoConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.mantenimiento.ComponenteCosto");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.ComponenteCostoConverter
 * JD-Core Version:    0.7.0.1
 */