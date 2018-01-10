/*  1:   */ package com.asinfo.as2.rs.inventario.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class PedidoSugeridoRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idEmpresa;
/* 11:   */   private Integer idEmpresaSubCliente;
/* 12:   */   
/* 13:   */   public Integer getIdEmpresa()
/* 14:   */   {
/* 15:16 */     return this.idEmpresa;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setIdEmpresa(Integer idEmpresa)
/* 19:   */   {
/* 20:20 */     this.idEmpresa = idEmpresa;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Integer getIdEmpresaSubCliente()
/* 24:   */   {
/* 25:24 */     return this.idEmpresaSubCliente;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setIdEmpresaSubCliente(Integer idEmpresaSubCliente)
/* 29:   */   {
/* 30:28 */     this.idEmpresaSubCliente = idEmpresaSubCliente;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.PedidoSugeridoRequestDto
 * JD-Core Version:    0.7.0.1
 */