/*  1:   */ package com.asinfo.as2.rs.financiero.contabilidad.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.math.BigDecimal;
/*  6:   */ 
/*  7:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  8:   */ public class DetalleDenominacionFormaCobroRequestDto
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 1L;
/* 12:   */   private int idOrganizacion;
/* 13:   */   private int idSucursal;
/* 14:18 */   private BigDecimal total = BigDecimal.ZERO;
/* 15:   */   private int cantidad;
/* 16:   */   private Integer idDispositivoSincronizacion;
/* 17:   */   private int idDetalleDenominacionFormaCobro;
/* 18:23 */   private int hashCode = 0;
/* 19:   */   private int idDenominacionFormaCobro;
/* 20:   */   
/* 21:   */   public int getIdOrganizacion()
/* 22:   */   {
/* 23:28 */     return this.idOrganizacion;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setIdOrganizacion(int idOrganizacion)
/* 27:   */   {
/* 28:32 */     this.idOrganizacion = idOrganizacion;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getIdSucursal()
/* 32:   */   {
/* 33:36 */     return this.idSucursal;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setIdSucursal(int idSucursal)
/* 37:   */   {
/* 38:40 */     this.idSucursal = idSucursal;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public BigDecimal getTotal()
/* 42:   */   {
/* 43:44 */     return this.total;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setTotal(BigDecimal total)
/* 47:   */   {
/* 48:48 */     this.total = total;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int getCantidad()
/* 52:   */   {
/* 53:52 */     return this.cantidad;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setCantidad(int cantidad)
/* 57:   */   {
/* 58:56 */     this.cantidad = cantidad;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public Integer getIdDispositivoSincronizacion()
/* 62:   */   {
/* 63:60 */     return this.idDispositivoSincronizacion;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 67:   */   {
/* 68:64 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public int getIdDetalleDenominacionFormaCobro()
/* 72:   */   {
/* 73:68 */     return this.idDetalleDenominacionFormaCobro;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public void setIdDetalleDenominacionFormaCobro(int idDetalleDenominacionFormaCobro)
/* 77:   */   {
/* 78:72 */     this.idDetalleDenominacionFormaCobro = idDetalleDenominacionFormaCobro;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public int getHashCode()
/* 82:   */   {
/* 83:76 */     return this.hashCode;
/* 84:   */   }
/* 85:   */   
/* 86:   */   public void setHashCode(int hashCode)
/* 87:   */   {
/* 88:80 */     this.hashCode = hashCode;
/* 89:   */   }
/* 90:   */   
/* 91:   */   public int getIdDenominacionFormaCobro()
/* 92:   */   {
/* 93:84 */     return this.idDenominacionFormaCobro;
/* 94:   */   }
/* 95:   */   
/* 96:   */   public void setIdDenominacionFormaCobro(int idDenominacionFormaCobro)
/* 97:   */   {
/* 98:88 */     this.idDenominacionFormaCobro = idDenominacionFormaCobro;
/* 99:   */   }
/* :0:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.contabilidad.dto.DetalleDenominacionFormaCobroRequestDto
 * JD-Core Version:    0.7.0.1
 */