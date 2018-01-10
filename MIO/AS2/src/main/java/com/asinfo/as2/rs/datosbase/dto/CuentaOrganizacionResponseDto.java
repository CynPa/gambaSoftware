/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   9:    */ public class CuentaOrganizacionResponseDto
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private Integer idCuentaOrganizacion;
/*  13:    */   private Integer idOrganizacion;
/*  14:    */   private String numeroCuenta;
/*  15:    */   private Integer tipoCuenta;
/*  16:    */   private boolean activo;
/*  17:    */   private String nombreBanco;
/*  18:    */   private String numeroCuentaSimple;
/*  19: 27 */   private int hashCode = 0;
/*  20: 29 */   List<FormaPagoResponseDto> listaFormaPago = new ArrayList();
/*  21:    */   
/*  22:    */   public int getHashCode()
/*  23:    */   {
/*  24: 32 */     this.hashCode = hashCode();
/*  25: 33 */     return this.hashCode;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int hashCode()
/*  29:    */   {
/*  30: 38 */     int hash = 1;
/*  31: 39 */     hash += hash * 17 + (this.idCuentaOrganizacion + "").hashCode();
/*  32: 40 */     hash += hash * 22 + (this.idOrganizacion + "").hashCode();
/*  33: 41 */     hash += hash * 41 + (this.numeroCuenta + "").hashCode();
/*  34: 42 */     hash += hash * 36 + (this.tipoCuenta + "").hashCode();
/*  35: 43 */     hash += hash * 11 + (this.activo + "").hashCode();
/*  36: 44 */     hash += hash * 12 + (this.nombreBanco + "").hashCode();
/*  37: 45 */     hash += hash * 13 + (this.numeroCuentaSimple + "").hashCode();
/*  38: 47 */     for (FormaPagoResponseDto formaPagoResponseDto : this.listaFormaPago) {
/*  39: 48 */       hash += formaPagoResponseDto.getHashCode();
/*  40:    */     }
/*  41: 51 */     return hash;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Integer getIdCuentaOrganizacion()
/*  45:    */   {
/*  46: 55 */     return this.idCuentaOrganizacion;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setIdCuentaOrganizacion(Integer idCuentaOrganizacion)
/*  50:    */   {
/*  51: 59 */     this.idCuentaOrganizacion = idCuentaOrganizacion;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Integer getIdOrganizacion()
/*  55:    */   {
/*  56: 63 */     return this.idOrganizacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  60:    */   {
/*  61: 67 */     this.idOrganizacion = idOrganizacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getNumeroCuenta()
/*  65:    */   {
/*  66: 71 */     return this.numeroCuenta;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setNumeroCuenta(String numeroCuenta)
/*  70:    */   {
/*  71: 75 */     this.numeroCuenta = numeroCuenta;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Integer getTipoCuenta()
/*  75:    */   {
/*  76: 79 */     return this.tipoCuenta;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setTipoCuenta(Integer tipoCuenta)
/*  80:    */   {
/*  81: 83 */     this.tipoCuenta = tipoCuenta;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setHashCode(int hashCode)
/*  85:    */   {
/*  86: 87 */     this.hashCode = hashCode;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isActivo()
/*  90:    */   {
/*  91: 91 */     return this.activo;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setActivo(boolean activo)
/*  95:    */   {
/*  96: 95 */     this.activo = activo;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public List<FormaPagoResponseDto> getListaFormaPago()
/* 100:    */   {
/* 101: 99 */     return this.listaFormaPago;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setListaFormaPago(List<FormaPagoResponseDto> listaFormaPago)
/* 105:    */   {
/* 106:103 */     this.listaFormaPago = listaFormaPago;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getNombreBanco()
/* 110:    */   {
/* 111:107 */     return this.nombreBanco;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setNombreBanco(String nombreBanco)
/* 115:    */   {
/* 116:111 */     this.nombreBanco = nombreBanco;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getNumeroCuentaSimple()
/* 120:    */   {
/* 121:115 */     return this.numeroCuentaSimple;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setNumeroCuentaSimple(String numeroCuentaSimple)
/* 125:    */   {
/* 126:119 */     this.numeroCuentaSimple = numeroCuentaSimple;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.CuentaOrganizacionResponseDto
 * JD-Core Version:    0.7.0.1
 */