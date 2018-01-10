/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=RutaFabricacion.class)
/*  7:   */ public class RutaFabricacionConverter
/*  8:   */   extends EntidadBaseConverter<RutaFabricacion>
/*  9:   */ {
/* 10:   */   public RutaFabricacionConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.mantenimiento.RutaFabricacion");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.RutaFabricacionConverter
 * JD-Core Version:    0.7.0.1
 */