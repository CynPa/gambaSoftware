/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=EntidadProceso.class)
/*  7:   */ public class EntidadProcesoConverter
/*  8:   */   extends EntidadBaseConverter<EntidadProceso>
/*  9:   */ {
/* 10:   */   public EntidadProcesoConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.seguridad.EntidadProceso");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.EntidadProcesoConverter
 * JD-Core Version:    0.7.0.1
 */