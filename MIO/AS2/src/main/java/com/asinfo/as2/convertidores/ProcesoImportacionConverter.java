/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ProcesoImportacion;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=ProcesoImportacion.class)
/*  7:   */ public class ProcesoImportacionConverter
/*  8:   */   extends EntidadBaseConverter<ProcesoImportacion>
/*  9:   */ {
/* 10:   */   public ProcesoImportacionConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.ProcesoImportacion");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.ProcesoImportacionConverter
 * JD-Core Version:    0.7.0.1
 */