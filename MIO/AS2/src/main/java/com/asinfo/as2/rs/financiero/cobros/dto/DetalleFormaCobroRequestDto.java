/*   1:    */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ 
/*   6:    */ public class DetalleFormaCobroRequestDto
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 1L;
/*  10:    */   private Integer idCuentaOrganizacion;
/*  11:    */   private Integer idDetalleFormaCobro;
/*  12:    */   private Integer idFormaPago;
/*  13:    */   private String documentoReferencia;
/*  14:    */   private Integer idBancoOrigen;
/*  15: 23 */   private BigDecimal valor = BigDecimal.ZERO;
/*  16:    */   private Integer idDispositivoSincronizacion;
/*  17:    */   private String numeroTarjeta;
/*  18:    */   private String lote;
/*  19:    */   private BigDecimal interes;
/*  20:    */   private Integer idTarjetaCredito;
/*  21:    */   private Integer idPlanTarjetaCredito;
/*  22:    */   
/*  23:    */   public Integer getIdCuentaOrganizacion()
/*  24:    */   {
/*  25: 38 */     return this.idCuentaOrganizacion;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setIdCuentaOrganizacion(Integer idCuentaOrganizacion)
/*  29:    */   {
/*  30: 42 */     this.idCuentaOrganizacion = idCuentaOrganizacion;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Integer getIdFormaPago()
/*  34:    */   {
/*  35: 46 */     return this.idFormaPago;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setIdFormaPago(Integer idFormaPago)
/*  39:    */   {
/*  40: 50 */     this.idFormaPago = idFormaPago;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getDocumentoReferencia()
/*  44:    */   {
/*  45: 54 */     return this.documentoReferencia;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setDocumentoReferencia(String documentoReferencia)
/*  49:    */   {
/*  50: 58 */     this.documentoReferencia = documentoReferencia;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Integer getIdBancoOrigen()
/*  54:    */   {
/*  55: 62 */     return this.idBancoOrigen;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdBancoOrigen(Integer idBancoOrigen)
/*  59:    */   {
/*  60: 66 */     this.idBancoOrigen = idBancoOrigen;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public BigDecimal getValor()
/*  64:    */   {
/*  65: 70 */     return this.valor;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setValor(BigDecimal valor)
/*  69:    */   {
/*  70: 74 */     this.valor = valor;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Integer getIdDispositivoSincronizacion()
/*  74:    */   {
/*  75: 78 */     return this.idDispositivoSincronizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/*  79:    */   {
/*  80: 82 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Integer getIdDetalleFormaCobro()
/*  84:    */   {
/*  85: 86 */     return this.idDetalleFormaCobro;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdDetalleFormaCobro(Integer idDetalleFormaCobro)
/*  89:    */   {
/*  90: 90 */     this.idDetalleFormaCobro = idDetalleFormaCobro;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getNumeroTarjeta()
/*  94:    */   {
/*  95: 94 */     return this.numeroTarjeta;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setNumeroTarjeta(String numeroTarjeta)
/*  99:    */   {
/* 100: 98 */     this.numeroTarjeta = numeroTarjeta;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getLote()
/* 104:    */   {
/* 105:102 */     return this.lote;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setLote(String lote)
/* 109:    */   {
/* 110:106 */     this.lote = lote;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public BigDecimal getInteres()
/* 114:    */   {
/* 115:110 */     return this.interes;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setInteres(BigDecimal interes)
/* 119:    */   {
/* 120:114 */     this.interes = interes;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Integer getIdTarjetaCredito()
/* 124:    */   {
/* 125:118 */     return this.idTarjetaCredito;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setIdTarjetaCredito(Integer idTarjetaCredito)
/* 129:    */   {
/* 130:122 */     this.idTarjetaCredito = idTarjetaCredito;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Integer getIdPlanTarjetaCredito()
/* 134:    */   {
/* 135:126 */     return this.idPlanTarjetaCredito;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setIdPlanTarjetaCredito(Integer idPlanTarjetaCredito)
/* 139:    */   {
/* 140:130 */     this.idPlanTarjetaCredito = idPlanTarjetaCredito;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.DetalleFormaCobroRequestDto
 * JD-Core Version:    0.7.0.1
 */