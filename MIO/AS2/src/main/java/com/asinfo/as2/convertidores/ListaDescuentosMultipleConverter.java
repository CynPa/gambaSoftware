/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ListaDescuentos;
/*  4:   */ import javax.faces.convert.FacesConverter;
/*  5:   */ 
/*  6:   */ @FacesConverter(forClass=ListaDescuentos.class, value="listaDescuentoConverter")
/*  7:   */ public class ListaDescuentosMultipleConverter
/*  8:   */   extends EntidadBaseConverter<ListaDescuentos>
/*  9:   */ {
/* 10:   */   public ListaDescuentosMultipleConverter()
/* 11:   */   {
/* 12:30 */     super("com.asinfo.as2.entities.ListaDescuentos");
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.ListaDescuentosMultipleConverter
 * JD-Core Version:    0.7.0.1
 */