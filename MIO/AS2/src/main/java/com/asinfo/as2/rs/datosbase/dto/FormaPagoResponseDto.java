/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ 
/*   7:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   8:    */ public class FormaPagoResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer idFormaPago;
/*  12:    */   private Integer idOrganizacion;
/*  13:    */   private String codigo;
/*  14:    */   private String nombre;
/*  15:    */   private boolean indicadorRetencionIVA;
/*  16:    */   private boolean indicadorRetencionFuente;
/*  17:    */   private boolean indicadorCheque;
/*  18:    */   private boolean indicadorDepositoAutomatico;
/*  19:    */   private boolean indicadorTarjetaCredito;
/*  20:    */   private boolean activo;
/*  21:    */   private BigDecimal porcentajeRetencion;
/*  22: 34 */   private int hashCode = 0;
/*  23:    */   
/*  24:    */   public int getHashCode()
/*  25:    */   {
/*  26: 37 */     this.hashCode = hashCode();
/*  27: 38 */     return this.hashCode;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int hashCode()
/*  31:    */   {
/*  32: 43 */     int hash = 1;
/*  33: 44 */     hash += hash * 17 + (this.idFormaPago + "").hashCode();
/*  34: 45 */     hash += hash * 22 + (this.idOrganizacion + "").hashCode();
/*  35: 46 */     hash += hash * 41 + (this.codigo + "").hashCode();
/*  36: 47 */     hash += hash * 36 + (this.nombre + "").hashCode();
/*  37: 48 */     hash += hash * 11 + (this.indicadorRetencionIVA + "").hashCode();
/*  38: 49 */     hash += hash * 12 + (this.indicadorRetencionFuente + "").hashCode();
/*  39: 50 */     hash += hash * 7 + (this.indicadorCheque + "").hashCode();
/*  40: 51 */     hash += hash * 14 + (this.indicadorDepositoAutomatico + "").hashCode();
/*  41: 52 */     hash += hash * 2 + (this.indicadorTarjetaCredito + "").hashCode();
/*  42: 53 */     hash += hash * 4 + (this.activo + "").hashCode();
/*  43: 54 */     hash += hash * 55 + (this.porcentajeRetencion + "").hashCode();
/*  44:    */     
/*  45: 56 */     return hash;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Integer getIdFormaPago()
/*  49:    */   {
/*  50: 60 */     return this.idFormaPago;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setIdFormaPago(Integer idFormaPago)
/*  54:    */   {
/*  55: 64 */     this.idFormaPago = idFormaPago;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Integer getIdOrganizacion()
/*  59:    */   {
/*  60: 68 */     return this.idOrganizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  64:    */   {
/*  65: 72 */     this.idOrganizacion = idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getCodigo()
/*  69:    */   {
/*  70: 76 */     return this.codigo;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setCodigo(String codigo)
/*  74:    */   {
/*  75: 80 */     this.codigo = codigo;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getNombre()
/*  79:    */   {
/*  80: 84 */     return this.nombre;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setNombre(String nombre)
/*  84:    */   {
/*  85: 88 */     this.nombre = nombre;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isIndicadorRetencionIVA()
/*  89:    */   {
/*  90: 92 */     return this.indicadorRetencionIVA;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIndicadorRetencionIVA(boolean indicadorRetencionIVA)
/*  94:    */   {
/*  95: 96 */     this.indicadorRetencionIVA = indicadorRetencionIVA;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isIndicadorRetencionFuente()
/*  99:    */   {
/* 100:100 */     return this.indicadorRetencionFuente;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIndicadorRetencionFuente(boolean indicadorRetencionFuente)
/* 104:    */   {
/* 105:104 */     this.indicadorRetencionFuente = indicadorRetencionFuente;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isIndicadorCheque()
/* 109:    */   {
/* 110:108 */     return this.indicadorCheque;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIndicadorCheque(boolean indicadorCheque)
/* 114:    */   {
/* 115:112 */     this.indicadorCheque = indicadorCheque;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isIndicadorDepositoAutomatico()
/* 119:    */   {
/* 120:116 */     return this.indicadorDepositoAutomatico;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setIndicadorDepositoAutomatico(boolean indicadorDepositoAutomatico)
/* 124:    */   {
/* 125:120 */     this.indicadorDepositoAutomatico = indicadorDepositoAutomatico;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isIndicadorTarjetaCredito()
/* 129:    */   {
/* 130:124 */     return this.indicadorTarjetaCredito;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setIndicadorTarjetaCredito(boolean indicadorTarjetaCredito)
/* 134:    */   {
/* 135:128 */     this.indicadorTarjetaCredito = indicadorTarjetaCredito;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isActivo()
/* 139:    */   {
/* 140:132 */     return this.activo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setActivo(boolean activo)
/* 144:    */   {
/* 145:136 */     this.activo = activo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public BigDecimal getPorcentajeRetencion()
/* 149:    */   {
/* 150:140 */     return this.porcentajeRetencion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setPorcentajeRetencion(BigDecimal porcentajeRetencion)
/* 154:    */   {
/* 155:144 */     this.porcentajeRetencion = porcentajeRetencion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setHashCode(int hashCode)
/* 159:    */   {
/* 160:148 */     this.hashCode = hashCode;
/* 161:    */   }
/* 162:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.FormaPagoResponseDto
 * JD-Core Version:    0.7.0.1
 */