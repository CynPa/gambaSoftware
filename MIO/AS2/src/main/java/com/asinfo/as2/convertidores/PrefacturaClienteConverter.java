/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.PrefacturaCliente;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=PrefacturaCliente.class)
/*  7:   */ public class PrefacturaClienteConverter
/*  8:   */   extends EntidadBaseConverter<PrefacturaCliente>
/*  9:   */ {
/* 10:   */   public PrefacturaClienteConverter()
/* 11:   */   {
/* 12:26 */     super("com.asinfo.as2.entities.PrefacturaCliente");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.PrefacturaClienteConverter
 * JD-Core Version:    0.7.0.1
 */