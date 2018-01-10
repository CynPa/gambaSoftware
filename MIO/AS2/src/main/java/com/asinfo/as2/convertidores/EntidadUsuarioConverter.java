/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=EntidadUsuario.class)
/*  7:   */ public class EntidadUsuarioConverter
/*  8:   */   extends EntidadBaseConverter<EntidadUsuario>
/*  9:   */ {
/* 10:   */   public EntidadUsuarioConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.seguridad.EntidadUsuario");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.EntidadUsuarioConverter
 * JD-Core Version:    0.7.0.1
 */