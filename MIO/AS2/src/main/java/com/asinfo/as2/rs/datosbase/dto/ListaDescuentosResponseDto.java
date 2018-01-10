/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ 
/*   7:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   8:    */ public class ListaDescuentosResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private int idOrganizacion;
/*  12:    */   private int idSucursal;
/*  13:    */   private String nombre;
/*  14:    */   private Integer idListaDescuentos;
/*  15:    */   private Boolean activo;
/*  16:    */   private Integer idDetalleListaDescuentos;
/*  17: 24 */   private BigDecimal porcentajeDescuentoMaximo = BigDecimal.ZERO;
/*  18:    */   private Integer idProducto;
/*  19:    */   private boolean indicadorDescuentoPorProducto;
/*  20:    */   private boolean indicadorCargaAutomatica;
/*  21: 32 */   private int hashCode = 0;
/*  22:    */   
/*  23:    */   public int getHashCode()
/*  24:    */   {
/*  25: 35 */     this.hashCode = hashCode();
/*  26: 36 */     return this.hashCode;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int hashCode()
/*  30:    */   {
/*  31: 41 */     int hash = 1;
/*  32: 42 */     hash += hash * 17 + (this.idOrganizacion + "").hashCode();
/*  33: 43 */     hash += hash * 22 + (this.idListaDescuentos + "").hashCode();
/*  34: 44 */     hash += hash * 23 + (this.activo + "").hashCode();
/*  35: 45 */     hash += hash * 32 + (this.nombre + "").hashCode();
/*  36: 46 */     hash += hash * 44 + (this.idSucursal + "").hashCode();
/*  37: 47 */     hash += hash * 22 + (this.idDetalleListaDescuentos + "").hashCode();
/*  38: 48 */     hash += hash * 13 + (this.porcentajeDescuentoMaximo + "").hashCode();
/*  39: 49 */     hash += hash * 53 + (this.idProducto + "").hashCode();
/*  40: 50 */     hash += hash * 63 + (this.indicadorDescuentoPorProducto + "").hashCode();
/*  41: 51 */     hash += hash * 47 + (this.indicadorCargaAutomatica + "").hashCode();
/*  42:    */     
/*  43: 53 */     return hash;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdOrganizacion()
/*  47:    */   {
/*  48: 57 */     return this.idOrganizacion;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdOrganizacion(int idOrganizacion)
/*  52:    */   {
/*  53: 61 */     this.idOrganizacion = idOrganizacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Integer getIdListaDescuentos()
/*  57:    */   {
/*  58: 65 */     return this.idListaDescuentos;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdListaDescuentos(Integer idListaDescuentos)
/*  62:    */   {
/*  63: 69 */     this.idListaDescuentos = idListaDescuentos;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Boolean getActivo()
/*  67:    */   {
/*  68: 73 */     return this.activo;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setActivo(Boolean activo)
/*  72:    */   {
/*  73: 77 */     this.activo = activo;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setHashCode(int hashCode)
/*  77:    */   {
/*  78: 81 */     this.hashCode = hashCode;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdSucursal()
/*  82:    */   {
/*  83: 85 */     return this.idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdSucursal(int idSucursal)
/*  87:    */   {
/*  88: 89 */     this.idSucursal = idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getNombre()
/*  92:    */   {
/*  93: 93 */     return this.nombre;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setNombre(String nombre)
/*  97:    */   {
/*  98: 97 */     this.nombre = nombre;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Integer getIdDetalleListaDescuentos()
/* 102:    */   {
/* 103:101 */     return this.idDetalleListaDescuentos;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdDetalleListaDescuentos(Integer idDetalleListaDescuentos)
/* 107:    */   {
/* 108:105 */     this.idDetalleListaDescuentos = idDetalleListaDescuentos;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public BigDecimal getPorcentajeDescuentoMaximo()
/* 112:    */   {
/* 113:109 */     return this.porcentajeDescuentoMaximo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setPorcentajeDescuentoMaximo(BigDecimal porcentajeDescuentoMaximo)
/* 117:    */   {
/* 118:113 */     this.porcentajeDescuentoMaximo = porcentajeDescuentoMaximo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Integer getIdProducto()
/* 122:    */   {
/* 123:117 */     return this.idProducto;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setIdProducto(Integer idProducto)
/* 127:    */   {
/* 128:121 */     this.idProducto = idProducto;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isIndicadorDescuentoPorProducto()
/* 132:    */   {
/* 133:125 */     return this.indicadorDescuentoPorProducto;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setIndicadorDescuentoPorProducto(boolean indicadorDescuentoPorProducto)
/* 137:    */   {
/* 138:129 */     this.indicadorDescuentoPorProducto = indicadorDescuentoPorProducto;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean isIndicadorCargaAutomatica()
/* 142:    */   {
/* 143:133 */     return this.indicadorCargaAutomatica;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setIndicadorCargaAutomatica(boolean indicadorCargaAutomatica)
/* 147:    */   {
/* 148:137 */     this.indicadorCargaAutomatica = indicadorCargaAutomatica;
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ListaDescuentosResponseDto
 * JD-Core Version:    0.7.0.1
 */