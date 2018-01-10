/*   1:    */ package com.asinfo.as2.rs.ventas.dto;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ 
/*   6:    */ public class DetalleFacturaClienteResponseDto
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private int idDetalleFacturaCliente;
/*  10:    */   private int idOrganizacion;
/*  11:    */   private int idSucursal;
/*  12:    */   private Integer idProducto;
/*  13: 17 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  14: 19 */   private BigDecimal precio = BigDecimal.ZERO;
/*  15: 21 */   private BigDecimal descuento = BigDecimal.ZERO;
/*  16: 23 */   private BigDecimal descuentoLinea = BigDecimal.ZERO;
/*  17: 25 */   private BigDecimal porcentajeDescuento = BigDecimal.ZERO;
/*  18:    */   private Integer idDispositivoSincronizacion;
/*  19:    */   private Integer idBodega;
/*  20:    */   private Integer idDetalleFacturaClientePadre;
/*  21: 33 */   private int hashCode = 0;
/*  22:    */   
/*  23:    */   public int getHashCode()
/*  24:    */   {
/*  25: 36 */     this.hashCode = hashCode();
/*  26: 37 */     return this.hashCode;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int hashCode()
/*  30:    */   {
/*  31: 43 */     int hash = 1;
/*  32: 44 */     hash += hash * 31 + (this.idDetalleFacturaCliente + "").hashCode();
/*  33: 45 */     hash += hash * 54 + (this.idOrganizacion + "").hashCode();
/*  34: 46 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/*  35: 47 */     hash += hash * 55 + (this.idProducto + "").hashCode();
/*  36: 48 */     hash += hash * 12 + (this.cantidad + "").hashCode();
/*  37: 49 */     hash += hash * 86 + (this.precio + "").hashCode();
/*  38: 50 */     hash += hash * 30 + (this.descuento + "").hashCode();
/*  39: 51 */     hash += hash * 30 + (this.descuentoLinea + "").hashCode();
/*  40: 52 */     hash += hash * 15 + (this.porcentajeDescuento + "").hashCode();
/*  41: 53 */     hash += hash * 15 + (this.idBodega + "").hashCode();
/*  42: 54 */     hash += hash * 15 + (this.idDetalleFacturaClientePadre + "").hashCode();
/*  43:    */     
/*  44: 56 */     return hash;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getIdDetalleFacturaCliente()
/*  48:    */   {
/*  49: 60 */     return this.idDetalleFacturaCliente;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setIdDetalleFacturaCliente(int idDetalleFacturaCliente)
/*  53:    */   {
/*  54: 64 */     this.idDetalleFacturaCliente = idDetalleFacturaCliente;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdOrganizacion()
/*  58:    */   {
/*  59: 68 */     return this.idOrganizacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdOrganizacion(int idOrganizacion)
/*  63:    */   {
/*  64: 72 */     this.idOrganizacion = idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdSucursal()
/*  68:    */   {
/*  69: 76 */     return this.idSucursal;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdSucursal(int idSucursal)
/*  73:    */   {
/*  74: 80 */     this.idSucursal = idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public BigDecimal getCantidad()
/*  78:    */   {
/*  79: 84 */     return this.cantidad;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setCantidad(BigDecimal cantidad)
/*  83:    */   {
/*  84: 88 */     this.cantidad = cantidad;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public BigDecimal getPrecio()
/*  88:    */   {
/*  89: 92 */     return this.precio;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setPrecio(BigDecimal precio)
/*  93:    */   {
/*  94: 96 */     this.precio = precio;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public BigDecimal getDescuento()
/*  98:    */   {
/*  99:100 */     return this.descuento;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setDescuento(BigDecimal descuento)
/* 103:    */   {
/* 104:104 */     this.descuento = descuento;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public BigDecimal getPorcentajeDescuento()
/* 108:    */   {
/* 109:108 */     return this.porcentajeDescuento;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setPorcentajeDescuento(BigDecimal porcentajeDescuento)
/* 113:    */   {
/* 114:112 */     this.porcentajeDescuento = porcentajeDescuento;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Integer getIdProducto()
/* 118:    */   {
/* 119:116 */     return this.idProducto;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setIdProducto(Integer idProducto)
/* 123:    */   {
/* 124:120 */     this.idProducto = idProducto;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public BigDecimal getDescuentoLinea()
/* 128:    */   {
/* 129:124 */     return this.descuentoLinea;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setDescuentoLinea(BigDecimal descuentoLinea)
/* 133:    */   {
/* 134:128 */     this.descuentoLinea = descuentoLinea;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Integer getIdDispositivoSincronizacion()
/* 138:    */   {
/* 139:132 */     return this.idDispositivoSincronizacion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 143:    */   {
/* 144:136 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Integer getIdBodega()
/* 148:    */   {
/* 149:140 */     return this.idBodega;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIdBodega(Integer idBodega)
/* 153:    */   {
/* 154:144 */     this.idBodega = idBodega;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Integer getIdDetalleFacturaClientePadre()
/* 158:    */   {
/* 159:148 */     return this.idDetalleFacturaClientePadre;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIdDetalleFacturaClientePadre(Integer idDetalleFacturaClientePadre)
/* 163:    */   {
/* 164:152 */     this.idDetalleFacturaClientePadre = idDetalleFacturaClientePadre;
/* 165:    */   }
/* 166:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.ventas.dto.DetalleFacturaClienteResponseDto
 * JD-Core Version:    0.7.0.1
 */