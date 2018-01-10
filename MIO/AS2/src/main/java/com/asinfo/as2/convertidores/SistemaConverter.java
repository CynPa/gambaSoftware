/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=EntidadSistema.class)
/*  7:   */ public class SistemaConverter
/*  8:   */   extends EntidadBaseConverter<EntidadSistema>
/*  9:   */ {
/* 10:   */   public SistemaConverter()
/* 11:   */   {
/* 12:27 */     super("com.asinfo.as2.entities.seguridad.EntidadSistema");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.SistemaConverter
 * JD-Core Version:    0.7.0.1
 */