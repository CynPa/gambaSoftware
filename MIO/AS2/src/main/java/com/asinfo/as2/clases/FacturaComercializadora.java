/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empresa;
/*   4:    */ import com.asinfo.as2.entities.Producto;
/*   5:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.Date;
/*   8:    */ 
/*   9:    */ public class FacturaComercializadora
/*  10:    */ {
/*  11:    */   private String establecimiento;
/*  12:    */   private String puntoEmision;
/*  13:    */   private String autorizacion;
/*  14:    */   private String numeroFactura;
/*  15:    */   private Empresa empresa;
/*  16:    */   private Date fechaFactura;
/*  17:    */   private Date fechaVencimiento;
/*  18:    */   private Producto producto;
/*  19:    */   private BigDecimal cantidad;
/*  20:    */   private Estado estado;
/*  21:    */   private BigDecimal valorTotal;
/*  22: 39 */   private BigDecimal valorTotalCalculado = BigDecimal.ZERO;
/*  23:    */   
/*  24:    */   public FacturaComercializadora() {}
/*  25:    */   
/*  26:    */   public FacturaComercializadora(String establecimiento, String puntoEmision, String autorizacion, String numeroFactura, Empresa empresa, Date fechaFactura, Date fechaVencimiento, Producto producto, BigDecimal cantidad, Estado estado, BigDecimal valorTotal)
/*  27:    */   {
/*  28: 62 */     this.establecimiento = establecimiento;
/*  29: 63 */     this.puntoEmision = puntoEmision;
/*  30: 64 */     this.autorizacion = ((estado == Estado.ANULADO) && (autorizacion.isEmpty()) ? "0000000000" : autorizacion);
/*  31: 65 */     this.numeroFactura = numeroFactura;
/*  32: 66 */     this.empresa = empresa;
/*  33: 67 */     this.fechaFactura = fechaFactura;
/*  34: 68 */     this.fechaVencimiento = fechaVencimiento;
/*  35: 69 */     this.producto = producto;
/*  36: 70 */     this.cantidad = cantidad;
/*  37: 71 */     this.estado = estado;
/*  38: 72 */     this.valorTotal = valorTotal;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getEstablecimiento()
/*  42:    */   {
/*  43: 81 */     return this.establecimiento;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setEstablecimiento(String establecimiento)
/*  47:    */   {
/*  48: 91 */     this.establecimiento = establecimiento;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getPuntoEmision()
/*  52:    */   {
/*  53:100 */     return this.puntoEmision;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setPuntoEmision(String puntoEmision)
/*  57:    */   {
/*  58:110 */     this.puntoEmision = puntoEmision;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getAutorizacion()
/*  62:    */   {
/*  63:119 */     return this.autorizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setAutorizacion(String autorizacion)
/*  67:    */   {
/*  68:129 */     this.autorizacion = autorizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getNumeroFactura()
/*  72:    */   {
/*  73:138 */     return this.numeroFactura;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setNumeroFactura(String numeroFactura)
/*  77:    */   {
/*  78:148 */     this.numeroFactura = numeroFactura;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Empresa getEmpresa()
/*  82:    */   {
/*  83:157 */     return this.empresa;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setEmpresa(Empresa empresa)
/*  87:    */   {
/*  88:167 */     this.empresa = empresa;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Producto getProducto()
/*  92:    */   {
/*  93:176 */     return this.producto;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setProducto(Producto producto)
/*  97:    */   {
/*  98:186 */     this.producto = producto;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Date getFechaFactura()
/* 102:    */   {
/* 103:195 */     return this.fechaFactura;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setFechaFactura(Date fechaFactura)
/* 107:    */   {
/* 108:205 */     this.fechaFactura = fechaFactura;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Date getFechaVencimiento()
/* 112:    */   {
/* 113:214 */     return this.fechaVencimiento;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setFechaVencimiento(Date fechaVencimiento)
/* 117:    */   {
/* 118:224 */     this.fechaVencimiento = fechaVencimiento;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public BigDecimal getCantidad()
/* 122:    */   {
/* 123:233 */     return this.cantidad;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setCantidad(BigDecimal cantidad)
/* 127:    */   {
/* 128:243 */     this.cantidad = cantidad;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Estado getEstado()
/* 132:    */   {
/* 133:252 */     return this.estado;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setEstado(Estado estado)
/* 137:    */   {
/* 138:262 */     this.estado = estado;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public BigDecimal getValorTotal()
/* 142:    */   {
/* 143:271 */     return this.valorTotal;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setValorTotal(BigDecimal valorTotal)
/* 147:    */   {
/* 148:281 */     this.valorTotal = valorTotal;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public BigDecimal getValorTotalCalculado()
/* 152:    */   {
/* 153:288 */     return this.valorTotalCalculado;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setValorTotalCalculado(BigDecimal valorTotalCalculado)
/* 157:    */   {
/* 158:296 */     this.valorTotalCalculado = valorTotalCalculado;
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.FacturaComercializadora
 * JD-Core Version:    0.7.0.1
 */