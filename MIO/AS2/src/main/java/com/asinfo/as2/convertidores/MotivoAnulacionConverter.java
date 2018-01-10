/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.MotivoAnulacion;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=MotivoAnulacion.class)
/*  7:   */ public class MotivoAnulacionConverter
/*  8:   */   extends EntidadBaseConverter<MotivoAnulacion>
/*  9:   */ {
/* 10:   */   public MotivoAnulacionConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.MotivoAnulacion");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.MotivoAnulacionConverter
 * JD-Core Version:    0.7.0.1
 */