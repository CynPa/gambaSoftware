/*   1:    */ package com.asinfo.as2.ws.ventas.model;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Calendar;
/*   6:    */ 
/*   7:    */ public class FacturaClienteBaseWSEntity
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 1L;
/*  11:    */   private String establecimiento;
/*  12:    */   private String puntoDeVenta;
/*  13:    */   private String numero;
/*  14:    */   private Calendar fechaFactura;
/*  15:    */   private Calendar fechaVencimiento;
/*  16:    */   private String direccionFactura;
/*  17: 39 */   private int numeroCuotas = 1;
/*  18:    */   private DetalleFacturaClienteWSEntity[] listaDetalleFacturaCliente;
/*  19: 43 */   private BigDecimal totalImpuesto = BigDecimal.ZERO.setScale(2);
/*  20: 45 */   private BigDecimal totalImporte = BigDecimal.ZERO.setScale(2);
/*  21: 47 */   private BigDecimal fleteInternacional = BigDecimal.ZERO.setScale(2);
/*  22:    */   private Long idPuertoDestino;
/*  23:    */   private Long idPaisDestino;
/*  24: 53 */   private BigDecimal descuento = BigDecimal.ZERO.setScale(2);
/*  25:    */   private String email;
/*  26:    */   
/*  27:    */   public String getDireccionFactura()
/*  28:    */   {
/*  29: 66 */     return this.direccionFactura;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setDireccionFactura(String direccionFactura)
/*  33:    */   {
/*  34: 75 */     this.direccionFactura = direccionFactura;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getEstablecimiento()
/*  38:    */   {
/*  39: 84 */     return this.establecimiento;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setEstablecimiento(String establecimiento)
/*  43:    */   {
/*  44: 93 */     this.establecimiento = establecimiento;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Calendar getFechaFactura()
/*  48:    */   {
/*  49:102 */     return this.fechaFactura;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setFechaFactura(Calendar fechaFactura)
/*  53:    */   {
/*  54:111 */     this.fechaFactura = fechaFactura;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Calendar getFechaVencimiento()
/*  58:    */   {
/*  59:120 */     return this.fechaVencimiento;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setFechaVencimiento(Calendar fechaVencimiento)
/*  63:    */   {
/*  64:129 */     this.fechaVencimiento = fechaVencimiento;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getNumero()
/*  68:    */   {
/*  69:139 */     return this.numero;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setNumero(String numero)
/*  73:    */   {
/*  74:148 */     this.numero = numero;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getPuntoDeVenta()
/*  78:    */   {
/*  79:157 */     return this.puntoDeVenta;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setPuntoDeVenta(String puntoDeVenta)
/*  83:    */   {
/*  84:166 */     this.puntoDeVenta = puntoDeVenta;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public DetalleFacturaClienteWSEntity[] getListaDetalleFacturaCliente()
/*  88:    */   {
/*  89:175 */     return this.listaDetalleFacturaCliente;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setListaDetalleFacturaCliente(DetalleFacturaClienteWSEntity[] listaDetalleFacturaCliente)
/*  93:    */   {
/*  94:185 */     this.listaDetalleFacturaCliente = listaDetalleFacturaCliente;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getNumeroCuotas()
/*  98:    */   {
/*  99:194 */     return this.numeroCuotas;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setNumeroCuotas(int numeroCuotas)
/* 103:    */   {
/* 104:204 */     this.numeroCuotas = numeroCuotas;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public BigDecimal getTotalImpuesto()
/* 108:    */   {
/* 109:212 */     return this.totalImpuesto;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setTotalImpuesto(BigDecimal totalImpuesto)
/* 113:    */   {
/* 114:220 */     this.totalImpuesto = totalImpuesto;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public BigDecimal getTotalImporte()
/* 118:    */   {
/* 119:227 */     return this.totalImporte;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setTotalImporte(BigDecimal totalImporte)
/* 123:    */   {
/* 124:235 */     this.totalImporte = totalImporte;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getEmail()
/* 128:    */   {
/* 129:239 */     return this.email;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setEmail(String email)
/* 133:    */   {
/* 134:243 */     this.email = email;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public BigDecimal getFleteInternacional()
/* 138:    */   {
/* 139:247 */     return this.fleteInternacional;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setFleteInternacional(BigDecimal fleteInternacional)
/* 143:    */   {
/* 144:251 */     this.fleteInternacional = fleteInternacional;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Long getIdPuertoDestino()
/* 148:    */   {
/* 149:255 */     return this.idPuertoDestino;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIdPuertoDestino(Long idPuertoDestino)
/* 153:    */   {
/* 154:259 */     this.idPuertoDestino = idPuertoDestino;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Long getIdPaisDestino()
/* 158:    */   {
/* 159:263 */     return this.idPaisDestino;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIdPaisDestino(Long idPaisDestino)
/* 163:    */   {
/* 164:267 */     this.idPaisDestino = idPaisDestino;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public BigDecimal getDescuento()
/* 168:    */   {
/* 169:271 */     return this.descuento;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setDescuento(BigDecimal descuento)
/* 173:    */   {
/* 174:275 */     this.descuento = descuento;
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.FacturaClienteBaseWSEntity
 * JD-Core Version:    0.7.0.1
 */