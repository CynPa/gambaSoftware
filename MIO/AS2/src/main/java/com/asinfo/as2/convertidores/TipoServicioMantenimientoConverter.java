/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.mantenimiento.old.TipoServicioMantenimiento;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=TipoServicioMantenimiento.class)
/*  7:   */ public class TipoServicioMantenimientoConverter
/*  8:   */   extends EntidadBaseConverter<TipoServicioMantenimiento>
/*  9:   */ {
/* 10:   */   public TipoServicioMantenimientoConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.mantenimiento.TipoServicioMantenimiento");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.TipoServicioMantenimientoConverter
 * JD-Core Version:    0.7.0.1
 */