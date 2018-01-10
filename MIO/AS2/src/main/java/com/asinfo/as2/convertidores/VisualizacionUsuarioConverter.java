/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Visualizacion;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Visualizacion.class)
/*  7:   */ public class VisualizacionUsuarioConverter
/*  8:   */   extends EntidadBaseConverter<Visualizacion>
/*  9:   */ {
/* 10:   */   public VisualizacionUsuarioConverter()
/* 11:   */   {
/* 12:29 */     super("com.asinfo.as2.entities.VisualizacionUsuario");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.VisualizacionUsuarioConverter
 * JD-Core Version:    0.7.0.1
 */