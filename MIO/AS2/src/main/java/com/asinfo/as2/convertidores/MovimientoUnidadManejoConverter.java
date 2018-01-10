/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.MovimientoUnidadManejo;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=MovimientoUnidadManejo.class)
/*  7:   */ public class MovimientoUnidadManejoConverter
/*  8:   */   extends EntidadBaseConverter<MovimientoUnidadManejo>
/*  9:   */ {
/* 10:   */   public MovimientoUnidadManejoConverter()
/* 11:   */   {
/* 12:22 */     super("com.asinfo.as2.entities.MovimientoUnidadManejo");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.MovimientoUnidadManejoConverter
 * JD-Core Version:    0.7.0.1
 */