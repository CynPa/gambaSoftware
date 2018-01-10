/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class ListaMotivoNotaCreditoClienteRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idOrganizacion;
/* 11:12 */   private List<MotivoNotaCreditoClienteRequestDto> listaMotivoNotaCreditoClienteRequest = new ArrayList();
/* 12:   */   
/* 13:   */   public Integer getIdOrganizacion()
/* 14:   */   {
/* 15:15 */     return this.idOrganizacion;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 19:   */   {
/* 20:19 */     this.idOrganizacion = idOrganizacion;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public List<MotivoNotaCreditoClienteRequestDto> getListaMotivoNotaCreditoClienteRequest()
/* 24:   */   {
/* 25:23 */     return this.listaMotivoNotaCreditoClienteRequest;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setListaMotivoNotaCreditoClienteRequest(List<MotivoNotaCreditoClienteRequestDto> listaMotivoNotaCreditoClienteRequest)
/* 29:   */   {
/* 30:27 */     this.listaMotivoNotaCreditoClienteRequest = listaMotivoNotaCreditoClienteRequest;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.ListaMotivoNotaCreditoClienteRequestDto
 * JD-Core Version:    0.7.0.1
 */