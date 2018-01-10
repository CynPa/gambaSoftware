/*   1:    */ package com.asinfo.as2.ws.ventas.model;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.Calendar;
/*   7:    */ 
/*   8:    */ public class FacturaClienteWSEntity
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 1L;
/*  12:    */   private String tipoDocumento;
/*  13:    */   private String establecimiento;
/*  14:    */   private String puntoDeVenta;
/*  15:    */   private String numero;
/*  16:    */   private Calendar fecha;
/*  17:    */   private Calendar fechaVencimiento;
/*  18:    */   private String direccionFactura;
/*  19: 44 */   private Integer condicionPago = Integer.valueOf(1);
/*  20: 46 */   private Integer numeroCuotas = Integer.valueOf(1);
/*  21:    */   private DetalleFacturaClienteWSEntity[] listaDetalleFacturaCliente;
/*  22: 50 */   private BigDecimal totalImpuesto = BigDecimal.ZERO.setScale(2);
/*  23: 52 */   private BigDecimal totalImporte = BigDecimal.ZERO.setScale(2);
/*  24:    */   private String email;
/*  25:    */   private String descripcion;
/*  26:    */   private String identificacionCliente;
/*  27:    */   private String establecimientoModificado;
/*  28:    */   private String puntoDeVentaModificado;
/*  29:    */   private String numeroModificado;
/*  30:    */   private String usuario;
/*  31:    */   private String documento;
/*  32:    */   
/*  33:    */   public FacturaClienteWSEntity() {}
/*  34:    */   
/*  35:    */   public FacturaClienteWSEntity(FacturaCliente facturaCliente)
/*  36:    */   {
/*  37: 74 */     this.direccionFactura = facturaCliente.getDireccionFactura();
/*  38: 75 */     this.descripcion = facturaCliente.getDescripcion();
/*  39: 76 */     this.establecimiento = facturaCliente.getNumero().substring(3);
/*  40: 77 */     this.puntoDeVenta = facturaCliente.getNumero().substring(4, 7);
/*  41: 78 */     this.numero = facturaCliente.getNumero().substring(8, 17);
/*  42: 79 */     this.email = facturaCliente.getEmail();
/*  43: 80 */     this.totalImporte = facturaCliente.getTotal();
/*  44: 81 */     this.totalImpuesto = facturaCliente.getImpuesto();
/*  45: 82 */     this.numeroCuotas = Integer.valueOf(facturaCliente.getNumeroCuotas());
/*  46: 83 */     if (facturaCliente.getFecha() != null)
/*  47:    */     {
/*  48: 84 */       this.fecha = Calendar.getInstance();
/*  49: 85 */       this.fecha.setTime(facturaCliente.getFecha());
/*  50:    */     }
/*  51: 87 */     if (facturaCliente.getFechaVencimiento() != null)
/*  52:    */     {
/*  53: 88 */       this.fechaVencimiento = Calendar.getInstance();
/*  54: 89 */       this.fechaVencimiento.setTime(facturaCliente.getFechaVencimiento());
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getTipoDocumento()
/*  59:    */   {
/*  60: 94 */     return this.tipoDocumento;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setTipoDocumento(String tipoDocumento)
/*  64:    */   {
/*  65: 98 */     this.tipoDocumento = tipoDocumento;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getDescripcion()
/*  69:    */   {
/*  70:102 */     return this.descripcion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setDescripcion(String descripcion)
/*  74:    */   {
/*  75:106 */     this.descripcion = descripcion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getIdentificacionCliente()
/*  79:    */   {
/*  80:110 */     return this.identificacionCliente;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdentificacionCliente(String identificacionCliente)
/*  84:    */   {
/*  85:114 */     this.identificacionCliente = identificacionCliente;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getEstablecimientoModificado()
/*  89:    */   {
/*  90:118 */     return this.establecimientoModificado;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setEstablecimientoModificado(String establecimientoModificado)
/*  94:    */   {
/*  95:122 */     this.establecimientoModificado = establecimientoModificado;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getNumeroModificado()
/*  99:    */   {
/* 100:126 */     return this.numeroModificado;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setNumeroModificado(String numeroModificado)
/* 104:    */   {
/* 105:130 */     this.numeroModificado = numeroModificado;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getEstablecimiento()
/* 109:    */   {
/* 110:134 */     return this.establecimiento;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setEstablecimiento(String establecimiento)
/* 114:    */   {
/* 115:138 */     this.establecimiento = establecimiento;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getPuntoDeVenta()
/* 119:    */   {
/* 120:142 */     return this.puntoDeVenta;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setPuntoDeVenta(String puntoDeVenta)
/* 124:    */   {
/* 125:146 */     this.puntoDeVenta = puntoDeVenta;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getNumero()
/* 129:    */   {
/* 130:150 */     return this.numero;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setNumero(String numero)
/* 134:    */   {
/* 135:154 */     this.numero = numero;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Calendar getFecha()
/* 139:    */   {
/* 140:158 */     return this.fecha;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setFecha(Calendar fecha)
/* 144:    */   {
/* 145:162 */     this.fecha = fecha;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Calendar getFechaVencimiento()
/* 149:    */   {
/* 150:166 */     return this.fechaVencimiento;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setFechaVencimiento(Calendar fechaVencimiento)
/* 154:    */   {
/* 155:170 */     this.fechaVencimiento = fechaVencimiento;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getDireccionFactura()
/* 159:    */   {
/* 160:174 */     return this.direccionFactura;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setDireccionFactura(String direccionFactura)
/* 164:    */   {
/* 165:178 */     this.direccionFactura = direccionFactura;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Integer getNumeroCuotas()
/* 169:    */   {
/* 170:182 */     return this.numeroCuotas;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setNumeroCuotas(Integer numeroCuotas)
/* 174:    */   {
/* 175:186 */     this.numeroCuotas = numeroCuotas;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public DetalleFacturaClienteWSEntity[] getListaDetalleFacturaCliente()
/* 179:    */   {
/* 180:190 */     return this.listaDetalleFacturaCliente;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setListaDetalleFacturaCliente(DetalleFacturaClienteWSEntity[] listaDetalleFacturaCliente)
/* 184:    */   {
/* 185:195 */     this.listaDetalleFacturaCliente = listaDetalleFacturaCliente;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public BigDecimal getTotalImpuesto()
/* 189:    */   {
/* 190:199 */     return this.totalImpuesto;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setTotalImpuesto(BigDecimal totalImpuesto)
/* 194:    */   {
/* 195:203 */     this.totalImpuesto = totalImpuesto;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public BigDecimal getTotalImporte()
/* 199:    */   {
/* 200:207 */     return this.totalImporte;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setTotalImporte(BigDecimal totalImporte)
/* 204:    */   {
/* 205:211 */     this.totalImporte = totalImporte;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String getEmail()
/* 209:    */   {
/* 210:215 */     return this.email;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setEmail(String email)
/* 214:    */   {
/* 215:219 */     this.email = email;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String getPuntoDeVentaModificado()
/* 219:    */   {
/* 220:223 */     return this.puntoDeVentaModificado;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setPuntoDeVentaModificado(String puntoDeVentaModificado)
/* 224:    */   {
/* 225:227 */     this.puntoDeVentaModificado = puntoDeVentaModificado;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Integer getCondicionPago()
/* 229:    */   {
/* 230:231 */     return this.condicionPago;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setCondicionPago(Integer condicionPago)
/* 234:    */   {
/* 235:235 */     this.condicionPago = condicionPago;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String getUsuario()
/* 239:    */   {
/* 240:239 */     return this.usuario;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setUsuario(String usuario)
/* 244:    */   {
/* 245:243 */     this.usuario = usuario;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getDocumento()
/* 249:    */   {
/* 250:247 */     return this.documento;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setDocumento(String documento)
/* 254:    */   {
/* 255:251 */     this.documento = documento;
/* 256:    */   }
/* 257:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.FacturaClienteWSEntity
 * JD-Core Version:    0.7.0.1
 */