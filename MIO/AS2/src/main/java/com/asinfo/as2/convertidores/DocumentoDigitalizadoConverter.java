/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=DocumentoDigitalizado.class)
/*  7:   */ public class DocumentoDigitalizadoConverter
/*  8:   */   extends EntidadBaseConverter<DocumentoDigitalizado>
/*  9:   */ {
/* 10:   */   public DocumentoDigitalizadoConverter()
/* 11:   */   {
/* 12:13 */     super("com.asinfo.as2.entities.DocumentoDigitalizado");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.DocumentoDigitalizadoConverter
 * JD-Core Version:    0.7.0.1
 */