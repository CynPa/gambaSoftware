/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=DireccionEmpresa.class)
/*  7:   */ public class DireccionEmpresaConverter
/*  8:   */   extends EntidadBaseConverter<DireccionEmpresa>
/*  9:   */ {
/* 10:   */   public DireccionEmpresaConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.DireccionEmpresa");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.DireccionEmpresaConverter
 * JD-Core Version:    0.7.0.1
 */