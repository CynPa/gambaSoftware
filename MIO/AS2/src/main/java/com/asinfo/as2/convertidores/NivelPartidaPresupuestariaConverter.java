/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CuentaContable;
/*  4:   */ import com.asinfo.as2.entities.presupuesto.NivelPartidaPresupuestaria;
/*  5:   */ import javax.faces.convert.FacesConverter;
/*  6:   */ 
/*  7:   */ @FacesConverter(forClass=NivelPartidaPresupuestaria.class)
/*  8:   */ public class NivelPartidaPresupuestariaConverter
/*  9:   */   extends EntidadBaseConverter<CuentaContable>
/* 10:   */ {
/* 11:   */   public NivelPartidaPresupuestariaConverter()
/* 12:   */   {
/* 13:28 */     super("com.asinfo.as2.entities.NivelPartidaPresupuestaria");
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.NivelPartidaPresupuestariaConverter
 * JD-Core Version:    0.7.0.1
 */