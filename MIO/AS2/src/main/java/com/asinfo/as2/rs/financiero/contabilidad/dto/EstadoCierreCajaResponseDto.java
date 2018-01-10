/*  1:   */ package com.asinfo.as2.rs.financiero.contabilidad.dto;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.enumeraciones.Estado;
/*  4:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  5:   */ import java.io.Serializable;
/*  6:   */ 
/*  7:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  8:   */ public class EstadoCierreCajaResponseDto
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private Estado estado;
/* 12:   */   private int idCierreCaja;
/* 13:   */   private String numeroCierreCajaAs2;
/* 14:16 */   private int hashCode = 0;
/* 15:   */   
/* 16:   */   public int getHashCode()
/* 17:   */   {
/* 18:19 */     this.hashCode = hashCode();
/* 19:20 */     return this.hashCode;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int hashCode()
/* 23:   */   {
/* 24:25 */     int hash = 1;
/* 25:26 */     hash += hash * 41 + (this.idCierreCaja + "").hashCode();
/* 26:27 */     hash += hash * 25 + (this.estado + "").hashCode();
/* 27:28 */     hash += hash * 47 + (this.numeroCierreCajaAs2 + "").hashCode();
/* 28:29 */     return hash;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Estado getEstado()
/* 32:   */   {
/* 33:33 */     return this.estado;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setEstado(Estado estado)
/* 37:   */   {
/* 38:37 */     this.estado = estado;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setHashCode(int hashCode)
/* 42:   */   {
/* 43:41 */     this.hashCode = hashCode;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int getIdCierreCaja()
/* 47:   */   {
/* 48:45 */     return this.idCierreCaja;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void setIdCierreCaja(int idCierreCaja)
/* 52:   */   {
/* 53:49 */     this.idCierreCaja = idCierreCaja;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public String getNumeroCierreCajaAs2()
/* 57:   */   {
/* 58:53 */     return this.numeroCierreCajaAs2;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void setNumeroCierreCajaAs2(String numeroCierreCajaAs2)
/* 62:   */   {
/* 63:57 */     this.numeroCierreCajaAs2 = numeroCierreCajaAs2;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.contabilidad.dto.EstadoCierreCajaResponseDto
 * JD-Core Version:    0.7.0.1
 */