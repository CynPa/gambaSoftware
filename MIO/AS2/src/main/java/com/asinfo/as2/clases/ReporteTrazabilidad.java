/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.Temporal;
/*  10:    */ import javax.persistence.TemporalType;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="tmp_detalle_interfaz_contable")
/*  14:    */ public class ReporteTrazabilidad
/*  15:    */ {
/*  16:    */   @Id
/*  17:    */   @Column(name="detalle_interfaz_contable")
/*  18:    */   private Integer idDetalleInterfazContable;
/*  19:    */   @Column(name="numero_factura")
/*  20:    */   private String numeroFactura;
/*  21:    */   @Temporal(TemporalType.DATE)
/*  22:    */   @Column(name="fecha_factura")
/*  23:    */   private Date fechaFactura;
/*  24:    */   @Column(name="numero_despacho")
/*  25:    */   private String numeroDespacho;
/*  26:    */   @Temporal(TemporalType.DATE)
/*  27:    */   @Column(name="fecha_despacho")
/*  28:    */   private Date fechaDespacho;
/*  29:    */   @Column(name="cantidad1")
/*  30:    */   private BigDecimal cantidad1;
/*  31:    */   @Column(name="cantidad2")
/*  32:    */   private BigDecimal cantidad2;
/*  33:    */   @Column(name="cantidad3")
/*  34:    */   private BigDecimal cantidadDetallePedidoCliente;
/*  35:    */   @Column(name="numero_pedido")
/*  36:    */   private String numeroPedido;
/*  37:    */   @Temporal(TemporalType.DATE)
/*  38:    */   @Column(name="fecha_pedido")
/*  39:    */   private Date fechaPedido;
/*  40:    */   @Column(name="codigo_producto")
/*  41:    */   private String codigoProducto;
/*  42:    */   @Column(name="nombre_producto")
/*  43:    */   private String nombreProducto;
/*  44:    */   @Column(name="nombre_fiscal")
/*  45:    */   private String nombreFiscal;
/*  46:    */   @Column(name="id1")
/*  47:    */   private int id1;
/*  48:    */   @Column(name="id2")
/*  49:    */   private int id2;
/*  50:    */   @Column(name="id_pedido_cliente")
/*  51:    */   private int idPedidoCliente;
/*  52:    */   @Column(name="id3")
/*  53:    */   private int id3;
/*  54:    */   
/*  55:    */   public ReporteTrazabilidad(String numeroFactura, Date fechaFactura, String numeroDespacho, Date fechaDespacho, BigDecimal cantidad1, BigDecimal cantidad2, BigDecimal cantidadDetallePedidoCliente, String numeroPedido, Date fechaPedido, String codigoProducto, String nombreProducto, String nombreFiscal, int id1, int id2, int idPedidoCliente, int id3)
/*  56:    */   {
/*  57:115 */     this.numeroFactura = numeroFactura;
/*  58:116 */     this.fechaFactura = fechaFactura;
/*  59:117 */     this.numeroDespacho = numeroDespacho;
/*  60:118 */     this.fechaDespacho = fechaDespacho;
/*  61:119 */     this.cantidad1 = cantidad1;
/*  62:120 */     this.cantidad2 = cantidad2;
/*  63:121 */     this.cantidadDetallePedidoCliente = cantidadDetallePedidoCliente;
/*  64:122 */     this.numeroPedido = numeroPedido;
/*  65:123 */     this.fechaPedido = fechaPedido;
/*  66:124 */     this.codigoProducto = codigoProducto;
/*  67:125 */     this.nombreProducto = nombreProducto;
/*  68:126 */     this.nombreFiscal = nombreFiscal;
/*  69:127 */     this.id1 = id1;
/*  70:128 */     this.id2 = id2;
/*  71:129 */     this.idPedidoCliente = idPedidoCliente;
/*  72:130 */     this.id3 = id3;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Integer getIdDetalleInterfazContable()
/*  76:    */   {
/*  77:141 */     return this.idDetalleInterfazContable;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdDetalleInterfazContable(Integer idDetalleInterfazContable)
/*  81:    */   {
/*  82:151 */     this.idDetalleInterfazContable = idDetalleInterfazContable;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getNumeroFactura()
/*  86:    */   {
/*  87:160 */     return this.numeroFactura;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setNumeroFactura(String numeroFactura)
/*  91:    */   {
/*  92:170 */     this.numeroFactura = numeroFactura;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Date getFechaFactura()
/*  96:    */   {
/*  97:179 */     return this.fechaFactura;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setFechaFactura(Date fechaFactura)
/* 101:    */   {
/* 102:189 */     this.fechaFactura = fechaFactura;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getNumeroDespacho()
/* 106:    */   {
/* 107:198 */     return this.numeroDespacho;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setNumeroDespacho(String numeroDespacho)
/* 111:    */   {
/* 112:208 */     this.numeroDespacho = numeroDespacho;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Date getFechaDespacho()
/* 116:    */   {
/* 117:217 */     return this.fechaDespacho;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFechaDespacho(Date fechaDespacho)
/* 121:    */   {
/* 122:227 */     this.fechaDespacho = fechaDespacho;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public BigDecimal getCantidad1()
/* 126:    */   {
/* 127:236 */     return this.cantidad1;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setCantidad1(BigDecimal cantidad1)
/* 131:    */   {
/* 132:246 */     this.cantidad1 = cantidad1;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public BigDecimal getCantidad2()
/* 136:    */   {
/* 137:255 */     return this.cantidad2;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setCantidad2(BigDecimal cantidad2)
/* 141:    */   {
/* 142:265 */     this.cantidad2 = cantidad2;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public BigDecimal getCantidadDetallePedidoCliente()
/* 146:    */   {
/* 147:274 */     return this.cantidadDetallePedidoCliente;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setCantidadDetallePedidoCliente(BigDecimal cantidadDetallePedidoCliente)
/* 151:    */   {
/* 152:284 */     this.cantidadDetallePedidoCliente = cantidadDetallePedidoCliente;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getNumeroPedido()
/* 156:    */   {
/* 157:293 */     return this.numeroPedido;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setNumeroPedido(String numeroPedido)
/* 161:    */   {
/* 162:303 */     this.numeroPedido = numeroPedido;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Date getFechaPedido()
/* 166:    */   {
/* 167:312 */     return this.fechaPedido;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setFechaPedido(Date fechaPedido)
/* 171:    */   {
/* 172:322 */     this.fechaPedido = fechaPedido;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getCodigoProducto()
/* 176:    */   {
/* 177:331 */     return this.codigoProducto;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setCodigoProducto(String codigoProducto)
/* 181:    */   {
/* 182:341 */     this.codigoProducto = codigoProducto;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getNombreProducto()
/* 186:    */   {
/* 187:350 */     return this.nombreProducto;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setNombreProducto(String nombreProducto)
/* 191:    */   {
/* 192:360 */     this.nombreProducto = nombreProducto;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String getNombreFiscal()
/* 196:    */   {
/* 197:369 */     return this.nombreFiscal;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setNombreFiscal(String nombreFiscal)
/* 201:    */   {
/* 202:379 */     this.nombreFiscal = nombreFiscal;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public int getId1()
/* 206:    */   {
/* 207:388 */     return this.id1;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setId1(int id1)
/* 211:    */   {
/* 212:398 */     this.id1 = id1;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public int getId2()
/* 216:    */   {
/* 217:407 */     return this.id2;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setId2(int id2)
/* 221:    */   {
/* 222:417 */     this.id2 = id2;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public int getIdPedidoCliente()
/* 226:    */   {
/* 227:426 */     return this.idPedidoCliente;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setIdPedidoCliente(int idPedidoCliente)
/* 231:    */   {
/* 232:436 */     this.idPedidoCliente = idPedidoCliente;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public int getId3()
/* 236:    */   {
/* 237:445 */     return this.id3;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setId3(int id3)
/* 241:    */   {
/* 242:455 */     this.id3 = id3;
/* 243:    */   }
/* 244:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteTrazabilidad
 * JD-Core Version:    0.7.0.1
 */