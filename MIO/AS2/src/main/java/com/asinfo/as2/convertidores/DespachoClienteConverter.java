/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DespachoCliente;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=DespachoCliente.class)
/*  7:   */ public class DespachoClienteConverter
/*  8:   */   extends EntidadBaseConverter<DespachoCliente>
/*  9:   */ {
/* 10:   */   public DespachoClienteConverter()
/* 11:   */   {
/* 12:31 */     super("com.asinfo.as2.entities.DespachoCliente");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.DespachoClienteConverter
 * JD-Core Version:    0.7.0.1
 */