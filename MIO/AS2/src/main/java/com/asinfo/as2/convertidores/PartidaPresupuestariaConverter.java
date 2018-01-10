/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CuentaContable;
/*  4:   */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*  5:   */ import javax.faces.convert.FacesConverter;
/*  6:   */ 
/*  7:   */ @FacesConverter(forClass=PartidaPresupuestaria.class)
/*  8:   */ public class PartidaPresupuestariaConverter
/*  9:   */   extends EntidadBaseConverter<CuentaContable>
/* 10:   */ {
/* 11:   */   public PartidaPresupuestariaConverter()
/* 12:   */   {
/* 13:28 */     super("com.asinfo.as2.entities.PartidaPresupuestaria");
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.PartidaPresupuestariaConverter
 * JD-Core Version:    0.7.0.1
 */