/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Sucursal;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=Sucursal.class)
/*  7:   */ public class SucursalConverter
/*  8:   */   extends EntidadBaseConverter<Sucursal>
/*  9:   */ {
/* 10:   */   public SucursalConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.Sucursal");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.SucursalConverter
 * JD-Core Version:    0.7.0.1
 */