/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.produccion.Maquina;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Maquina.class)
/*  7:   */ public class MaquinaConverter
/*  8:   */   extends EntidadBaseConverter<Maquina>
/*  9:   */ {
/* 10:   */   public MaquinaConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.Maquina");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.MaquinaConverter
 * JD-Core Version:    0.7.0.1
 */