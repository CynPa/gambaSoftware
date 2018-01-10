/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ 
/*   7:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   8:    */ public class DenominacionFormaCobroResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer idDenominacionFormaCobro;
/*  12:    */   private Integer idOrganizacion;
/*  13:    */   private Integer idSucursal;
/*  14:    */   private String codigo;
/*  15:    */   private String nombre;
/*  16:    */   private Boolean activo;
/*  17:    */   private String descripcion;
/*  18:    */   private BigDecimal valor;
/*  19:    */   private Integer idFormaPago;
/*  20: 22 */   private int hashCode = 0;
/*  21:    */   
/*  22:    */   public int getHashCode()
/*  23:    */   {
/*  24: 25 */     this.hashCode = hashCode();
/*  25: 26 */     return this.hashCode;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int hashCode()
/*  29:    */   {
/*  30: 31 */     int hash = 1;
/*  31: 32 */     hash += hash * 17 + (this.idDenominacionFormaCobro + "").hashCode();
/*  32: 33 */     hash += hash * 22 + (this.idOrganizacion + "").hashCode();
/*  33: 34 */     hash += hash * 23 + (this.idSucursal + "").hashCode();
/*  34: 35 */     hash += hash * 41 + (this.codigo == null ? 0 : this.codigo.hashCode());
/*  35: 36 */     hash += hash * 42 + (this.nombre + "").hashCode();
/*  36: 37 */     hash += hash * 37 + (this.descripcion + "").hashCode();
/*  37: 38 */     hash += hash * 11 + (this.activo + "").hashCode();
/*  38: 39 */     hash += hash * 44 + (this.valor + "").hashCode();
/*  39: 40 */     hash += hash * 33 + (this.idFormaPago + "").hashCode();
/*  40:    */     
/*  41: 42 */     return hash;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Integer getIdDenominacionFormaCobro()
/*  45:    */   {
/*  46: 46 */     return this.idDenominacionFormaCobro;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setIdDenominacionFormaCobro(Integer idDenominacionFormaCobro)
/*  50:    */   {
/*  51: 50 */     this.idDenominacionFormaCobro = idDenominacionFormaCobro;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Integer getIdSucursal()
/*  55:    */   {
/*  56: 54 */     return this.idSucursal;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdSucursal(Integer idSucursal)
/*  60:    */   {
/*  61: 58 */     this.idSucursal = idSucursal;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Boolean getActivo()
/*  65:    */   {
/*  66: 62 */     return this.activo;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setActivo(Boolean activo)
/*  70:    */   {
/*  71: 66 */     this.activo = activo;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getDescripcion()
/*  75:    */   {
/*  76: 70 */     return this.descripcion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setDescripcion(String descripcion)
/*  80:    */   {
/*  81: 74 */     this.descripcion = descripcion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public BigDecimal getValor()
/*  85:    */   {
/*  86: 78 */     return this.valor;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setValor(BigDecimal valor)
/*  90:    */   {
/*  91: 82 */     this.valor = valor;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Integer getIdFormaPago()
/*  95:    */   {
/*  96: 86 */     return this.idFormaPago;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdFormaPago(Integer idFormaPago)
/* 100:    */   {
/* 101: 90 */     this.idFormaPago = idFormaPago;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getCodigo()
/* 105:    */   {
/* 106: 94 */     return this.codigo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setCodigo(String codigo)
/* 110:    */   {
/* 111: 98 */     this.codigo = codigo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getNombre()
/* 115:    */   {
/* 116:102 */     return this.nombre;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setNombre(String nombre)
/* 120:    */   {
/* 121:106 */     this.nombre = nombre;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isActivo()
/* 125:    */   {
/* 126:110 */     return this.activo.booleanValue();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setActivo(boolean activo)
/* 130:    */   {
/* 131:114 */     this.activo = Boolean.valueOf(activo);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Integer getIdOrganizacion()
/* 135:    */   {
/* 136:118 */     return this.idOrganizacion;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setIdOrganizacion(Integer idOrganizacion)
/* 140:    */   {
/* 141:122 */     this.idOrganizacion = idOrganizacion;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setHashCode(int hashCode)
/* 145:    */   {
/* 146:126 */     this.hashCode = hashCode;
/* 147:    */   }
/* 148:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.DenominacionFormaCobroResponseDto
 * JD-Core Version:    0.7.0.1
 */