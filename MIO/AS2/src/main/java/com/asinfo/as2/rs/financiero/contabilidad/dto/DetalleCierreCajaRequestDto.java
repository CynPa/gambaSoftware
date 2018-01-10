/*  1:   */ package com.asinfo.as2.rs.financiero.contabilidad.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.math.BigDecimal;
/*  6:   */ 
/*  7:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  8:   */ public class DetalleCierreCajaRequestDto
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 1L;
/* 12:   */   private int idOrganizacion;
/* 13:   */   private int idSucursal;
/* 14:18 */   private BigDecimal valor = BigDecimal.ZERO;
/* 15:   */   private Integer idDispositivoSincronizacion;
/* 16:   */   private int idDetalleCierreCaja;
/* 17:22 */   private int hashCode = 0;
/* 18:   */   private int idDetalleFormaCobro;
/* 19:   */   
/* 20:   */   public BigDecimal getValor()
/* 21:   */   {
/* 22:27 */     return this.valor;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setValor(BigDecimal valor)
/* 26:   */   {
/* 27:31 */     this.valor = valor;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Integer getIdDispositivoSincronizacion()
/* 31:   */   {
/* 32:35 */     return this.idDispositivoSincronizacion;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 36:   */   {
/* 37:39 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int getIdOrganizacion()
/* 41:   */   {
/* 42:43 */     return this.idOrganizacion;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setIdOrganizacion(int idOrganizacion)
/* 46:   */   {
/* 47:47 */     this.idOrganizacion = idOrganizacion;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int getIdSucursal()
/* 51:   */   {
/* 52:51 */     return this.idSucursal;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setIdSucursal(int idSucursal)
/* 56:   */   {
/* 57:55 */     this.idSucursal = idSucursal;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int getIdDetalleCierreCaja()
/* 61:   */   {
/* 62:59 */     return this.idDetalleCierreCaja;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void setIdDetalleCierreCaja(int idDetalleCierreCaja)
/* 66:   */   {
/* 67:63 */     this.idDetalleCierreCaja = idDetalleCierreCaja;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public int getHashCode()
/* 71:   */   {
/* 72:67 */     return this.hashCode;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void setHashCode(int hashCode)
/* 76:   */   {
/* 77:71 */     this.hashCode = hashCode;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public int getIdDetalleFormaCobro()
/* 81:   */   {
/* 82:75 */     return this.idDetalleFormaCobro;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void setIdDetalleFormaCobro(int idDetalleFormaCobro)
/* 86:   */   {
/* 87:79 */     this.idDetalleFormaCobro = idDetalleFormaCobro;
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.contabilidad.dto.DetalleCierreCajaRequestDto
 * JD-Core Version:    0.7.0.1
 */