/*   1:    */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ 
/*   6:    */ public class SaldosFacturaResponseDto
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private String fecha;
/*  10:    */   private Integer idFacturaCliente;
/*  11:    */   private Integer idFactura;
/*  12:    */   private String numero;
/*  13:    */   private BigDecimal saldo;
/*  14:    */   private BigDecimal saldoBloqueado;
/*  15:    */   private Integer empresa;
/*  16:    */   private Integer subempresa;
/*  17: 25 */   private Boolean indicadorEmitidaRetencion = Boolean.valueOf(false);
/*  18:    */   private Integer diasPlazo;
/*  19:    */   private Integer mesesPlazo;
/*  20:    */   
/*  21:    */   public String getFecha()
/*  22:    */   {
/*  23: 34 */     return this.fecha;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Integer getIdFacturaCliente()
/*  27:    */   {
/*  28: 38 */     return this.idFacturaCliente;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setIdFacturaCliente(Integer idFacturaCliente)
/*  32:    */   {
/*  33: 42 */     this.idFacturaCliente = idFacturaCliente;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setFecha(String fecha)
/*  37:    */   {
/*  38: 46 */     this.fecha = fecha;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Integer getIdFactura()
/*  42:    */   {
/*  43: 50 */     return this.idFactura;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setIdFactura(Integer idFactura)
/*  47:    */   {
/*  48: 54 */     this.idFactura = idFactura;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getNumero()
/*  52:    */   {
/*  53: 58 */     return this.numero;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setNumero(String numero)
/*  57:    */   {
/*  58: 62 */     this.numero = numero;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public BigDecimal getSaldo()
/*  62:    */   {
/*  63: 66 */     return this.saldo;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setSaldo(BigDecimal saldo)
/*  67:    */   {
/*  68: 70 */     this.saldo = saldo;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public BigDecimal getSaldoBloqueado()
/*  72:    */   {
/*  73: 74 */     return this.saldoBloqueado;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setSaldoBloqueado(BigDecimal saldoBloqueado)
/*  77:    */   {
/*  78: 78 */     this.saldoBloqueado = saldoBloqueado;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Integer getEmpresa()
/*  82:    */   {
/*  83: 82 */     return this.empresa;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setEmpresa(Integer empresa)
/*  87:    */   {
/*  88: 86 */     this.empresa = empresa;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Integer getSubempresa()
/*  92:    */   {
/*  93: 90 */     return this.subempresa;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setSubempresa(Integer subempresa)
/*  97:    */   {
/*  98: 94 */     this.subempresa = subempresa;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Boolean getIndicadorEmitidaRetencion()
/* 102:    */   {
/* 103: 98 */     return this.indicadorEmitidaRetencion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIndicadorEmitidaRetencion(Boolean indicadorEmitidaRetencion)
/* 107:    */   {
/* 108:102 */     this.indicadorEmitidaRetencion = indicadorEmitidaRetencion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Integer getDiasPlazo()
/* 112:    */   {
/* 113:106 */     return this.diasPlazo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setDiasPlazo(Integer diasPlazo)
/* 117:    */   {
/* 118:110 */     this.diasPlazo = diasPlazo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Integer getMesesPlazo()
/* 122:    */   {
/* 123:114 */     return this.mesesPlazo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setMesesPlazo(Integer mesesPlazo)
/* 127:    */   {
/* 128:118 */     this.mesesPlazo = mesesPlazo;
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.SaldosFacturaResponseDto
 * JD-Core Version:    0.7.0.1
 */