/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.mantenimiento.old.Procedimiento;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Procedimiento.class)
/*  7:   */ public class ProcedimientoConverter
/*  8:   */   extends EntidadBaseConverter<Procedimiento>
/*  9:   */ {
/* 10:   */   public ProcedimientoConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.mantenimiento.Procedimiento");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.ProcedimientoConverter
 * JD-Core Version:    0.7.0.1
 */