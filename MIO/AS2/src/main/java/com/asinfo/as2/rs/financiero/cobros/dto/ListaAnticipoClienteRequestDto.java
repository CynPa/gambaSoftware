/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class ListaAnticipoClienteRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idOrganizacion;
/* 11:   */   private Integer idSucursal;
/* 12:   */   private Integer idCliente;
/* 13:16 */   private List<AnticipoClienteRequestDto> listaAnticipoCliente = new ArrayList();
/* 14:   */   
/* 15:   */   public Integer getIdOrganizacion()
/* 16:   */   {
/* 17:19 */     return this.idOrganizacion;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 21:   */   {
/* 22:23 */     this.idOrganizacion = idOrganizacion;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Integer getIdSucursal()
/* 26:   */   {
/* 27:27 */     return this.idSucursal;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setIdSucursal(Integer idSucursal)
/* 31:   */   {
/* 32:31 */     this.idSucursal = idSucursal;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Integer getIdCliente()
/* 36:   */   {
/* 37:35 */     return this.idCliente;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setIdCliente(Integer idCliente)
/* 41:   */   {
/* 42:39 */     this.idCliente = idCliente;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public List<AnticipoClienteRequestDto> getListaAnticipoCliente()
/* 46:   */   {
/* 47:43 */     return this.listaAnticipoCliente;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setListaAnticipoCliente(List<AnticipoClienteRequestDto> listaAnticipoCliente)
/* 51:   */   {
/* 52:47 */     this.listaAnticipoCliente = listaAnticipoCliente;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.ListaAnticipoClienteRequestDto
 * JD-Core Version:    0.7.0.1
 */