/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.VersionListaPrecios;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=VersionListaPrecios.class)
/*  7:   */ public class VersionListaPreciosConverter
/*  8:   */   extends EntidadBaseConverter<VersionListaPrecios>
/*  9:   */ {
/* 10:   */   public VersionListaPreciosConverter()
/* 11:   */   {
/* 12:15 */     super("com.asinfo.as2.entities.VersionListaPrecios");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.VersionListaPreciosConverter
 * JD-Core Version:    0.7.0.1
 */