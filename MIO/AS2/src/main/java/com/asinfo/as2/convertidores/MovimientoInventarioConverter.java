/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.MovimientoInventario;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=MovimientoInventario.class)
/*  7:   */ public class MovimientoInventarioConverter
/*  8:   */   extends EntidadBaseConverter<MovimientoInventario>
/*  9:   */ {
/* 10:   */   public MovimientoInventarioConverter()
/* 11:   */   {
/* 12:22 */     super("com.asinfo.as2.entities.MovimientoInventario");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.MovimientoInventarioConverter
 * JD-Core Version:    0.7.0.1
 */