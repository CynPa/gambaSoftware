/*  1:   */ package com.asinfo.as2.rs.seguridad.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class ConsultarSistemaRequestDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private Integer idOrganizacion;
/*  9:   */   private String sistema;
/* 10:   */   
/* 11:   */   public String getSistema()
/* 12:   */   {
/* 13:13 */     return this.sistema;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setSistema(String sistema)
/* 17:   */   {
/* 18:17 */     this.sistema = sistema;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Integer getIdOrganizacion()
/* 22:   */   {
/* 23:21 */     return this.idOrganizacion;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 27:   */   {
/* 28:25 */     this.idOrganizacion = idOrganizacion;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.seguridad.dto.ConsultarSistemaRequestDto
 * JD-Core Version:    0.7.0.1
 */