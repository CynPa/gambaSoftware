/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=ComponenteEquipo.class)
/*  7:   */ public class ComponenteEquipoConverter
/*  8:   */   extends EntidadBaseConverter<ComponenteEquipo>
/*  9:   */ {
/* 10:   */   public ComponenteEquipoConverter()
/* 11:   */   {
/* 12:21 */     super("com.asinfo.as2.entities.mantenimiento.ComponenteEquipo");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.ComponenteEquipoConverter
 * JD-Core Version:    0.7.0.1
 */