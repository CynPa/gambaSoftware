/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=DetalleMovimientoInventario.class)
/*  7:   */ public class DetalleMovimientoInventarioConverter
/*  8:   */   extends EntidadBaseConverter<DetalleMovimientoInventario>
/*  9:   */ {
/* 10:   */   public DetalleMovimientoInventarioConverter()
/* 11:   */   {
/* 12:22 */     super("com.asinfo.as2.entities.DetalleMovimientoInventario");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.DetalleMovimientoInventarioConverter
 * JD-Core Version:    0.7.0.1
 */