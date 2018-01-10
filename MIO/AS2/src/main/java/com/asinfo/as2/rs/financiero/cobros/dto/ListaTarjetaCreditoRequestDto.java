/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class ListaTarjetaCreditoRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idOrganizacion;
/* 11:14 */   private List<TarjetaCreditoRequestDto> listaTarjetaCredito = new ArrayList();
/* 12:   */   
/* 13:   */   public Integer getIdOrganizacion()
/* 14:   */   {
/* 15:17 */     return this.idOrganizacion;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 19:   */   {
/* 20:21 */     this.idOrganizacion = idOrganizacion;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public List<TarjetaCreditoRequestDto> getListaTarjetaCredito()
/* 24:   */   {
/* 25:25 */     return this.listaTarjetaCredito;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setListaTareaCredito(List<TarjetaCreditoRequestDto> listaTarjetaCredito)
/* 29:   */   {
/* 30:29 */     this.listaTarjetaCredito = listaTarjetaCredito;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.ListaTarjetaCreditoRequestDto
 * JD-Core Version:    0.7.0.1
 */