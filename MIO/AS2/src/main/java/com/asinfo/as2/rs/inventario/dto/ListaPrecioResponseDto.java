/*   1:    */ package com.asinfo.as2.rs.inventario.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ 
/*   7:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   8:    */ public class ListaPrecioResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer idOrganizacion;
/*  12:    */   private Integer idSucursal;
/*  13:    */   private Integer idDetalleListaPrecio;
/*  14:    */   private Integer idListaPrecioCabecera;
/*  15:    */   private String nombre;
/*  16: 22 */   private BigDecimal precioUnitario = BigDecimal.ZERO;
/*  17:    */   private ProductoResponseDto producto;
/*  18: 26 */   private int hashCode = 0;
/*  19: 28 */   private boolean activo = true;
/*  20:    */   
/*  21:    */   public int getHashCode()
/*  22:    */   {
/*  23: 31 */     this.hashCode = hashCode();
/*  24: 32 */     return this.hashCode;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int hashCode()
/*  28:    */   {
/*  29: 37 */     int hash = 1;
/*  30: 38 */     hash += hash * 17 + (this.idDetalleListaPrecio + "").hashCode();
/*  31: 39 */     hash += hash * 22 + (this.idListaPrecioCabecera + "").hashCode();
/*  32: 40 */     hash += hash * 41 + (this.precioUnitario + "").hashCode();
/*  33: 41 */     hash += hash * 44 + (this.idOrganizacion + "").hashCode();
/*  34: 42 */     hash += hash * 23 + (this.idSucursal + "").hashCode();
/*  35: 43 */     hash += hash * 11 + (this.nombre + "").hashCode();
/*  36: 44 */     hash += hash * 36 + (this.producto == null ? 0 : this.producto.hashCode());
/*  37:    */     
/*  38: 46 */     return hash;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Integer getIdDetalleListaPrecio()
/*  42:    */   {
/*  43: 50 */     return this.idDetalleListaPrecio;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setIdDetalleListaPrecio(Integer idDetalleListaPrecio)
/*  47:    */   {
/*  48: 54 */     this.idDetalleListaPrecio = idDetalleListaPrecio;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Integer getIdListaPrecioCabecera()
/*  52:    */   {
/*  53: 58 */     return this.idListaPrecioCabecera;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdListaPrecioCabecera(Integer idListaPrecioCabecera)
/*  57:    */   {
/*  58: 62 */     this.idListaPrecioCabecera = idListaPrecioCabecera;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public BigDecimal getPrecioUnitario()
/*  62:    */   {
/*  63: 66 */     return this.precioUnitario;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setPrecioUnitario(BigDecimal precioUnitario)
/*  67:    */   {
/*  68: 70 */     this.precioUnitario = precioUnitario;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public ProductoResponseDto getProducto()
/*  72:    */   {
/*  73: 74 */     return this.producto;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setProducto(ProductoResponseDto producto)
/*  77:    */   {
/*  78: 78 */     this.producto = producto;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isActivo()
/*  82:    */   {
/*  83: 82 */     return this.activo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setActivo(boolean activo)
/*  87:    */   {
/*  88: 86 */     this.activo = activo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Integer getIdOrganizacion()
/*  92:    */   {
/*  93: 90 */     return this.idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  97:    */   {
/*  98: 94 */     this.idOrganizacion = idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Integer getIdSucursal()
/* 102:    */   {
/* 103: 98 */     return this.idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdSucursal(Integer idSucursal)
/* 107:    */   {
/* 108:102 */     this.idSucursal = idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getNombre()
/* 112:    */   {
/* 113:106 */     return this.nombre;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setNombre(String nombre)
/* 117:    */   {
/* 118:110 */     this.nombre = nombre;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.ListaPrecioResponseDto
 * JD-Core Version:    0.7.0.1
 */