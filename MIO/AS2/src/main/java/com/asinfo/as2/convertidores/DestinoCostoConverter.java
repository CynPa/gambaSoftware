/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DestinoCosto;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=DestinoCosto.class)
/*  7:   */ public class DestinoCostoConverter
/*  8:   */   extends EntidadBaseConverter<DestinoCosto>
/*  9:   */ {
/* 10:   */   public DestinoCostoConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.mantenimiento.DestinoCosto");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.DestinoCostoConverter
 * JD-Core Version:    0.7.0.1
 */