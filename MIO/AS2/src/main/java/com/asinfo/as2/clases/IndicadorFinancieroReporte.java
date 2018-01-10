/*  1:   */ package com.asinfo.as2.clases;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ 
/*  5:   */ public class IndicadorFinancieroReporte
/*  6:   */ {
/*  7:   */   private String nombre;
/*  8:   */   List<DetalleIndicadorFinancieroReporte> listaDetalleIndicadorFinanciero;
/*  9:   */   
/* 10:   */   public List<DetalleIndicadorFinancieroReporte> getListaDetalleIndicadorFinanciero()
/* 11:   */   {
/* 12:17 */     return this.listaDetalleIndicadorFinanciero;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void setListaDetalleIndicadorFinanciero(List<DetalleIndicadorFinancieroReporte> listaDetalleIndicadorFinanciero)
/* 16:   */   {
/* 17:21 */     this.listaDetalleIndicadorFinanciero = listaDetalleIndicadorFinanciero;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getNombre()
/* 21:   */   {
/* 22:24 */     return this.nombre;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setNombre(String nombre)
/* 26:   */   {
/* 27:27 */     this.nombre = nombre;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.IndicadorFinancieroReporte
 * JD-Core Version:    0.7.0.1
 */