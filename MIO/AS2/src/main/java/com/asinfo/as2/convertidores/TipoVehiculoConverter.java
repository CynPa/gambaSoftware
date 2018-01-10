/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ActivoFijo;
/*  4:   */ import com.asinfo.as2.entities.TipoVehiculo;
/*  5:   */ import javax.faces.convert.FacesConverter;
/*  6:   */ 
/*  7:   */ @FacesConverter(forClass=TipoVehiculo.class)
/*  8:   */ public class TipoVehiculoConverter
/*  9:   */   extends EntidadBaseConverter<ActivoFijo>
/* 10:   */ {
/* 11:   */   public TipoVehiculoConverter()
/* 12:   */   {
/* 13:24 */     super("com.asinfo.as2.entities.TipoVehiculo");
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TipoVehiculoConverter
 * JD-Core Version:    0.7.0.1
 */