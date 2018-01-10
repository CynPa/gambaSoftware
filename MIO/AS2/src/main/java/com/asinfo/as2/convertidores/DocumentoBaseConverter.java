/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  4:   */ import javax.faces.convert.EnumConverter;
/*  5:   */ import javax.faces.convert.FacesConverter;
/*  6:   */ 
/*  7:   */ @FacesConverter(forClass=DocumentoBase.class)
/*  8:   */ public class DocumentoBaseConverter
/*  9:   */   extends EnumConverter
/* 10:   */ {
/* 11:   */   public DocumentoBaseConverter()
/* 12:   */   {
/* 13:29 */     super(DocumentoBase.class);
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.DocumentoBaseConverter
 * JD-Core Version:    0.7.0.1
 */