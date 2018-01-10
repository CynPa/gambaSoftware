/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.math.BigDecimal;
/*  5:   */ 
/*  6:   */ public class DetalleLiquidacionAnticipoResponseDto
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private int idOrganizacion;
/* 10:   */   private int idSucursal;
/* 11:   */   private Integer idLiquidacionAnticipo;
/* 12:   */   private Integer idCxC;
/* 13:   */   private BigDecimal valor;
/* 14:   */   private int idDetalleAs2;
/* 15:   */   private Integer idDispositivoSincronizacion;
/* 16:   */   
/* 17:   */   public int getIdOrganizacion()
/* 18:   */   {
/* 19:24 */     return this.idOrganizacion;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setIdOrganizacion(int idOrganizacion)
/* 23:   */   {
/* 24:28 */     this.idOrganizacion = idOrganizacion;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getIdSucursal()
/* 28:   */   {
/* 29:32 */     return this.idSucursal;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setIdSucursal(int idSucursal)
/* 33:   */   {
/* 34:36 */     this.idSucursal = idSucursal;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Integer getIdLiquidacionAnticipo()
/* 38:   */   {
/* 39:40 */     return this.idLiquidacionAnticipo;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setIdLiquidacionAnticipo(Integer idLiquidacionAnticipo)
/* 43:   */   {
/* 44:44 */     this.idLiquidacionAnticipo = idLiquidacionAnticipo;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Integer getIdCxC()
/* 48:   */   {
/* 49:48 */     return this.idCxC;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setIdCxC(Integer idCxC)
/* 53:   */   {
/* 54:52 */     this.idCxC = idCxC;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public BigDecimal getValor()
/* 58:   */   {
/* 59:56 */     return this.valor;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setValor(BigDecimal valor)
/* 63:   */   {
/* 64:60 */     this.valor = valor;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public int getIdDetalleAs2()
/* 68:   */   {
/* 69:64 */     return this.idDetalleAs2;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void setIdDetalleAs2(int idDetalleAs2)
/* 73:   */   {
/* 74:68 */     this.idDetalleAs2 = idDetalleAs2;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public Integer getIdDispositivoSincronizacion()
/* 78:   */   {
/* 79:72 */     return this.idDispositivoSincronizacion;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 83:   */   {
/* 84:76 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 85:   */   }
/* 86:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.DetalleLiquidacionAnticipoResponseDto
 * JD-Core Version:    0.7.0.1
 */