/*  1:   */ package com.asinfo.as2.rs.financiero.contabilidad.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class ConsultarCierreCajaRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private int idOrganizacion;
/* 11:   */   private Integer idCierreCaja;
/* 12:   */   
/* 13:   */   public int getIdOrganizacion()
/* 14:   */   {
/* 15:16 */     return this.idOrganizacion;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setIdOrganizacion(int idOrganizacion)
/* 19:   */   {
/* 20:20 */     this.idOrganizacion = idOrganizacion;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Integer getIdCierreCaja()
/* 24:   */   {
/* 25:24 */     return this.idCierreCaja;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setIdCierreCaja(Integer idCierreCaja)
/* 29:   */   {
/* 30:28 */     this.idCierreCaja = idCierreCaja;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.contabilidad.dto.ConsultarCierreCajaRequestDto
 * JD-Core Version:    0.7.0.1
 */