/*   1:    */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.Date;
/*   7:    */ 
/*   8:    */ public class AnticipoClienteResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer idOrganizacion;
/*  12:    */   private Integer idSucursal;
/*  13:    */   private Integer idAnticipoCliente;
/*  14:    */   private String numero;
/*  15:    */   private Integer idCliente;
/*  16:    */   private BigDecimal valor;
/*  17:    */   private BigDecimal saldo;
/*  18:    */   private Date fecha;
/*  19:    */   private String descripcion;
/*  20:    */   private Estado estado;
/*  21: 32 */   private int hashCode = 0;
/*  22: 34 */   private boolean activo = true;
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
/*  33: 44 */     hash += hash * 44 + (this.idOrganizacion + "").hashCode();
/*  34: 45 */     hash += hash * 23 + (this.idSucursal + "").hashCode();
/*  35: 46 */     hash += hash * 30 + (this.idAnticipoCliente + "").hashCode();
/*  36: 47 */     hash += hash * 50 + (this.numero + "").hashCode();
/*  37: 48 */     hash += hash * 65 + (this.idCliente + "").hashCode();
/*  38: 49 */     hash += hash * 80 + (this.valor + "").hashCode();
/*  39: 50 */     hash += hash * 95 + (this.saldo + "").hashCode();
/*  40: 51 */     hash += hash * 4 + (this.fecha + "").hashCode();
/*  41: 52 */     hash += hash * 15 + (this.descripcion + "").hashCode();
/*  42: 53 */     hash += hash * 28 + (this.estado + "").hashCode();
/*  43:    */     
/*  44: 55 */     return hash;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean isActivo()
/*  48:    */   {
/*  49: 59 */     return this.activo;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setActivo(boolean activo)
/*  53:    */   {
/*  54: 63 */     this.activo = activo;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Integer getIdOrganizacion()
/*  58:    */   {
/*  59: 67 */     return this.idOrganizacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  63:    */   {
/*  64: 71 */     this.idOrganizacion = idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Integer getIdSucursal()
/*  68:    */   {
/*  69: 75 */     return this.idSucursal;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdSucursal(Integer idSucursal)
/*  73:    */   {
/*  74: 79 */     this.idSucursal = idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setHashCode(int hashCode)
/*  78:    */   {
/*  79: 83 */     this.hashCode = hashCode;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Integer getIdAnticipoCliente()
/*  83:    */   {
/*  84: 87 */     return this.idAnticipoCliente;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdAnticipoCliente(Integer idAnticipoCliente)
/*  88:    */   {
/*  89: 91 */     this.idAnticipoCliente = idAnticipoCliente;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getNumero()
/*  93:    */   {
/*  94: 95 */     return this.numero;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setNumero(String numero)
/*  98:    */   {
/*  99: 99 */     this.numero = numero;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Integer getIdCliente()
/* 103:    */   {
/* 104:103 */     return this.idCliente;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setIdCliente(Integer idCliente)
/* 108:    */   {
/* 109:107 */     this.idCliente = idCliente;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public BigDecimal getValor()
/* 113:    */   {
/* 114:111 */     return this.valor;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setValor(BigDecimal valor)
/* 118:    */   {
/* 119:115 */     this.valor = valor;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public BigDecimal getSaldo()
/* 123:    */   {
/* 124:119 */     return this.saldo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setSaldo(BigDecimal saldo)
/* 128:    */   {
/* 129:123 */     this.saldo = saldo;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Date getFecha()
/* 133:    */   {
/* 134:127 */     return this.fecha;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setFecha(Date fecha)
/* 138:    */   {
/* 139:131 */     this.fecha = fecha;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getDescripcion()
/* 143:    */   {
/* 144:135 */     return this.descripcion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDescripcion(String descripcion)
/* 148:    */   {
/* 149:139 */     this.descripcion = descripcion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Estado getEstado()
/* 153:    */   {
/* 154:143 */     return this.estado;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setEstado(Estado estado)
/* 158:    */   {
/* 159:147 */     this.estado = estado;
/* 160:    */   }
/* 161:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.AnticipoClienteResponseDto
 * JD-Core Version:    0.7.0.1
 */