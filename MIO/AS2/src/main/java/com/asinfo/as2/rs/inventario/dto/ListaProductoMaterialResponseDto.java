/*   1:    */ package com.asinfo.as2.rs.inventario.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ 
/*   7:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   8:    */ public class ListaProductoMaterialResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer idProductoMaterial;
/*  12:    */   private int idOrganizacion;
/*  13:    */   private int idSucursal;
/*  14: 18 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  15: 20 */   private BigDecimal cantidadSustituto = BigDecimal.ZERO;
/*  16: 22 */   private BigDecimal proporcion = new BigDecimal(100);
/*  17:    */   private Boolean activo;
/*  18:    */   private Boolean indicadorPrincipal;
/*  19:    */   private ProductoResponseDto producto;
/*  20:    */   private ProductoResponseDto sustituto;
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
/*  32: 42 */     hash += hash * 17 + (this.idProductoMaterial + "").hashCode();
/*  33: 43 */     hash += hash * 36 + (this.idOrganizacion + "").hashCode();
/*  34: 44 */     hash += hash * 40 + (this.idSucursal + "").hashCode();
/*  35: 45 */     hash += hash * 3 + (this.cantidad + "").hashCode();
/*  36: 46 */     hash += hash * 6 + (this.proporcion + "").hashCode();
/*  37: 47 */     hash += hash * 22 + (this.activo + "").hashCode();
/*  38: 48 */     hash += hash * 10 + (this.indicadorPrincipal + "").hashCode();
/*  39: 49 */     hash += hash * 11 + (this.producto + "").hashCode();
/*  40: 50 */     hash += hash * 14 + (this.sustituto + "").hashCode();
/*  41: 51 */     hash += hash * 12 + (this.cantidadSustituto + "").hashCode();
/*  42:    */     
/*  43: 53 */     return hash;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Integer getIdProductoMaterial()
/*  47:    */   {
/*  48: 57 */     return this.idProductoMaterial;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdProductoMaterial(Integer idProductoMaterial)
/*  52:    */   {
/*  53: 61 */     this.idProductoMaterial = idProductoMaterial;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdOrganizacion()
/*  57:    */   {
/*  58: 65 */     return this.idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdOrganizacion(int idOrganizacion)
/*  62:    */   {
/*  63: 69 */     this.idOrganizacion = idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdSucursal()
/*  67:    */   {
/*  68: 73 */     return this.idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdSucursal(int idSucursal)
/*  72:    */   {
/*  73: 77 */     this.idSucursal = idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public BigDecimal getCantidad()
/*  77:    */   {
/*  78: 81 */     return this.cantidad;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setCantidad(BigDecimal cantidad)
/*  82:    */   {
/*  83: 85 */     this.cantidad = cantidad;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public BigDecimal getProporcion()
/*  87:    */   {
/*  88: 89 */     return this.proporcion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setProporcion(BigDecimal proporcion)
/*  92:    */   {
/*  93: 93 */     this.proporcion = proporcion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Boolean getActivo()
/*  97:    */   {
/*  98: 97 */     return this.activo;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setActivo(Boolean activo)
/* 102:    */   {
/* 103:101 */     this.activo = activo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Boolean getIndicadorPrincipal()
/* 107:    */   {
/* 108:105 */     return this.indicadorPrincipal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIndicadorPrincipal(Boolean indicadorPrincipal)
/* 112:    */   {
/* 113:109 */     this.indicadorPrincipal = indicadorPrincipal;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public ProductoResponseDto getProducto()
/* 117:    */   {
/* 118:113 */     return this.producto;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setProducto(ProductoResponseDto producto)
/* 122:    */   {
/* 123:117 */     this.producto = producto;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public ProductoResponseDto getSustituto()
/* 127:    */   {
/* 128:121 */     return this.sustituto;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setSustituto(ProductoResponseDto sustituto)
/* 132:    */   {
/* 133:125 */     this.sustituto = sustituto;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setHashCode(int hashCode)
/* 137:    */   {
/* 138:129 */     this.hashCode = hashCode;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public BigDecimal getCantidadSustituto()
/* 142:    */   {
/* 143:133 */     return this.cantidadSustituto;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setCantidadSustituto(BigDecimal cantidadSustituto)
/* 147:    */   {
/* 148:137 */     this.cantidadSustituto = cantidadSustituto;
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.ListaProductoMaterialResponseDto
 * JD-Core Version:    0.7.0.1
 */