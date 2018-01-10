/*   1:    */ package com.asinfo.as2.rs.financiero.contabilidad.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  10:    */ public class CierreCajaRequestDto
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = 1L;
/*  14:    */   private Integer idOrganizacion;
/*  15:    */   private Integer idSucursal;
/*  16:    */   private String fechaHasta;
/*  17:    */   private Integer idUsuario;
/*  18: 23 */   private BigDecimal valor = BigDecimal.ZERO;
/*  19: 24 */   private BigDecimal totalUsuario = BigDecimal.ZERO;
/*  20:    */   private Integer idDispositivoSincronizacion;
/*  21:    */   private Integer idCierreCaja;
/*  22:    */   private String numeroCierreCajaAs2;
/*  23: 31 */   private int hashCode = 0;
/*  24: 33 */   private List<DetalleCierreCajaRequestDto> listaDetalleCierreCaja = new ArrayList();
/*  25: 34 */   private List<DetalleDenominacionFormaCobroRequestDto> listaDetalleDenominacionFormaCobro = new ArrayList();
/*  26:    */   
/*  27:    */   public Integer getIdOrganizacion()
/*  28:    */   {
/*  29: 37 */     return this.idOrganizacion;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  33:    */   {
/*  34: 41 */     this.idOrganizacion = idOrganizacion;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Integer getIdSucursal()
/*  38:    */   {
/*  39: 45 */     return this.idSucursal;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setIdSucursal(Integer idSucursal)
/*  43:    */   {
/*  44: 49 */     this.idSucursal = idSucursal;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public BigDecimal getValor()
/*  48:    */   {
/*  49: 53 */     return this.valor;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Integer getIdUsuario()
/*  53:    */   {
/*  54: 57 */     return this.idUsuario;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdUsuario(Integer idUsuario)
/*  58:    */   {
/*  59: 61 */     this.idUsuario = idUsuario;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setValor(BigDecimal valor)
/*  63:    */   {
/*  64: 65 */     this.valor = valor;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getFechaHasta()
/*  68:    */   {
/*  69: 69 */     return this.fechaHasta;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setFechaHasta(String fechaHasta)
/*  73:    */   {
/*  74: 73 */     this.fechaHasta = fechaHasta;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<DetalleCierreCajaRequestDto> getListaDetalleCierreCaja()
/*  78:    */   {
/*  79: 77 */     return this.listaDetalleCierreCaja;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setListaDetalleCierreCaja(List<DetalleCierreCajaRequestDto> listaDetalleCierreCaja)
/*  83:    */   {
/*  84: 81 */     this.listaDetalleCierreCaja = listaDetalleCierreCaja;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Integer getIdDispositivoSincronizacion()
/*  88:    */   {
/*  89: 85 */     return this.idDispositivoSincronizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/*  93:    */   {
/*  94: 89 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Integer getIdCierreCaja()
/*  98:    */   {
/*  99: 93 */     return this.idCierreCaja;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setIdCierreCaja(Integer idCierreCaja)
/* 103:    */   {
/* 104: 97 */     this.idCierreCaja = idCierreCaja;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getHashCode()
/* 108:    */   {
/* 109:101 */     return this.hashCode;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setHashCode(int hashCode)
/* 113:    */   {
/* 114:105 */     this.hashCode = hashCode;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List<DetalleDenominacionFormaCobroRequestDto> getListaDetalleDenominacionFormaCobro()
/* 118:    */   {
/* 119:109 */     return this.listaDetalleDenominacionFormaCobro;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setListaDetalleDenominacionFormaCobro(List<DetalleDenominacionFormaCobroRequestDto> listaDetalleDenominacionFormaCobro)
/* 123:    */   {
/* 124:113 */     this.listaDetalleDenominacionFormaCobro = listaDetalleDenominacionFormaCobro;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public BigDecimal getTotalUsuario()
/* 128:    */   {
/* 129:117 */     return this.totalUsuario;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setTotalUsuario(BigDecimal totalUsuario)
/* 133:    */   {
/* 134:121 */     this.totalUsuario = totalUsuario;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getNumeroCierreCajaAs2()
/* 138:    */   {
/* 139:125 */     return this.numeroCierreCajaAs2;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setNumeroCierreCajaAs2(String numeroCierreCajaAs2)
/* 143:    */   {
/* 144:129 */     this.numeroCierreCajaAs2 = numeroCierreCajaAs2;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.contabilidad.dto.CierreCajaRequestDto
 * JD-Core Version:    0.7.0.1
 */