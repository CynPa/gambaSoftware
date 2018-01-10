/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CausaSalidaEmpleado;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=CausaSalidaEmpleado.class)
/*  7:   */ public class CausaSalidaEmpleadoConverter
/*  8:   */   extends EntidadBaseConverter<CausaSalidaEmpleado>
/*  9:   */ {
/* 10:   */   public CausaSalidaEmpleadoConverter()
/* 11:   */   {
/* 12:21 */     super("com.asinfo.as2.entities.CausaSalidaEmpleado");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.CausaSalidaEmpleadoConverter
 * JD-Core Version:    0.7.0.1
 */