/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ import org.hibernate.annotations.ColumnDefault;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="tipo_contacto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class TipoContacto
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="tipo_contacto", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_contacto")
/*  23:    */   @Column(name="id_tipo_contacto")
/*  24:    */   private int idTipoContacto;
/*  25:    */   @Column(name="id_organizacion", nullable=false)
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Column(name="id_sucursal", nullable=false)
/*  28:    */   private int idSucursal;
/*  29:    */   @Column(name="codigo", length=10, nullable=false)
/*  30:    */   @NotNull
/*  31:    */   @Size(min=2, max=10)
/*  32:    */   private String codigo;
/*  33:    */   @Column(name="nombre", length=50, nullable=false)
/*  34:    */   @NotNull
/*  35:    */   @Size(min=2, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   @Column(name="indicador_notificar_factura", nullable=false)
/*  45:    */   private boolean indicadorNotificarFactura;
/*  46:    */   @Column(name="indicador_notificar_retencion", nullable=false)
/*  47:    */   private boolean indicadorNotificarRetencion;
/*  48:    */   @Column(name="indicador_notificar_nota_credito", nullable=false)
/*  49:    */   private boolean indicadorNotificarNotaCredito;
/*  50:    */   @Column(name="indicador_notificar_nota_debito", nullable=false)
/*  51:    */   private boolean indicadorNotificarNotaDebito;
/*  52:    */   @Column(name="indicador_notificar_guia_remision", nullable=false)
/*  53:    */   private boolean indicadorNotificarGuiaRemision;
/*  54:    */   @Column(name="indicador_notificar_pedido_cliente", nullable=false)
/*  55:    */   private boolean indicadorNotificarPedidoCliente;
/*  56:    */   @Column(name="indicador_notificar_pedido_proveedor", nullable=false)
/*  57:    */   private boolean indicadorNotificarPedidoProveedor;
/*  58:    */   @Column(name="indicador_notificar_pago_proveedor", nullable=false)
/*  59:    */   private boolean indicadorNotificarPagoProveedor;
/*  60:    */   @Column(name="indicador_notificar_vencimiento_factura_cliente", nullable=false)
/*  61:    */   @NotNull
/*  62:    */   @ColumnDefault("'0'")
/*  63:    */   private boolean indicadorNotificarVencimientoFacturaCliente;
/*  64:    */   @Column(name="indicador_notificar_solicitud_compra", nullable=false)
/*  65:    */   @NotNull
/*  66:    */   @ColumnDefault("'0'")
/*  67:    */   private boolean indicadorNotificarSolicitudCompra;
/*  68:    */   @Column(name="texto_cuerpo_correo_factura", nullable=true, columnDefinition="text")
/*  69:    */   private String textoCuerpoCorreoFactura;
/*  70:    */   @Column(name="texto_cuerpo_correo_nota_credito_cliente", nullable=true, columnDefinition="text")
/*  71:    */   private String textoCuerpoCorreoNotaCreditoCliente;
/*  72:    */   @Column(name="texto_cuerpo_correo_nota_debito_cliente", nullable=true, columnDefinition="text")
/*  73:    */   private String textoCuerpoCorreoNotaDebitoCliente;
/*  74:    */   @Column(name="texto_cuerpo_correo_retencion_proveedor", nullable=true, columnDefinition="text")
/*  75:    */   private String textoCuerpoCorreoRetencionProveedor;
/*  76:    */   @Column(name="texto_cuerpo_correo_guia_remision", nullable=true, columnDefinition="text")
/*  77:    */   private String textoCuerpoCorreoGuiaRemision;
/*  78:    */   @Column(name="texto_cuerpo_correo_pedido_cliente", nullable=true, columnDefinition="text")
/*  79:    */   private String textoCuerpoCorreoPedidoCliente;
/*  80:    */   @Column(name="texto_cuerpo_correo_pedido_proveedor", nullable=true, columnDefinition="text")
/*  81:    */   private String textoCuerpoCorreoPedidoProveedor;
/*  82:    */   @Column(name="texto_cuerpo_correo_pago_proveedor", nullable=true, columnDefinition="text")
/*  83:    */   private String textoCuerpoCorreoPagoProveedor;
/*  84:    */   @Column(name="texto_cuerpo_correo_vencimiento_factura_cliente", nullable=true, columnDefinition="text")
/*  85:    */   private String textoCuerpoCorreoVencimientoFacturaCliente;
/*  86:    */   @Column(name="texto_cuerpo_correo_solicitud_compra", nullable=true, columnDefinition="text")
/*  87:    */   private String textoCuerpoCorreoSolicitudCompra;
/*  88:    */   
/*  89:    */   public int getId()
/*  90:    */   {
/*  91:153 */     return this.idTipoContacto;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public TipoContacto() {}
/*  95:    */   
/*  96:    */   public TipoContacto(int idTipoContacto)
/*  97:    */   {
/*  98:164 */     this.idTipoContacto = idTipoContacto;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public TipoContacto(int idTipoContacto, String codigo, String nombre)
/* 102:    */   {
/* 103:176 */     this.idTipoContacto = idTipoContacto;
/* 104:177 */     this.codigo = codigo;
/* 105:178 */     this.nombre = nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIdTipoContacto()
/* 109:    */   {
/* 110:187 */     return this.idTipoContacto;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdTipoContacto(int idTipoContacto)
/* 114:    */   {
/* 115:197 */     this.idTipoContacto = idTipoContacto;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getCodigo()
/* 119:    */   {
/* 120:206 */     return this.codigo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setCodigo(String codigo)
/* 124:    */   {
/* 125:216 */     this.codigo = codigo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getNombre()
/* 129:    */   {
/* 130:225 */     return this.nombre;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setNombre(String nombre)
/* 134:    */   {
/* 135:235 */     this.nombre = nombre;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getDescripcion()
/* 139:    */   {
/* 140:244 */     return this.descripcion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setDescripcion(String descripcion)
/* 144:    */   {
/* 145:254 */     this.descripcion = descripcion;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isActivo()
/* 149:    */   {
/* 150:263 */     return this.activo;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setActivo(boolean activo)
/* 154:    */   {
/* 155:273 */     this.activo = activo;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isPredeterminado()
/* 159:    */   {
/* 160:282 */     return this.predeterminado;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setPredeterminado(boolean predeterminado)
/* 164:    */   {
/* 165:292 */     this.predeterminado = predeterminado;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public int getIdOrganizacion()
/* 169:    */   {
/* 170:301 */     return this.idOrganizacion;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIdOrganizacion(int idOrganizacion)
/* 174:    */   {
/* 175:311 */     this.idOrganizacion = idOrganizacion;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int getIdSucursal()
/* 179:    */   {
/* 180:320 */     return this.idSucursal;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setIdSucursal(int idSucursal)
/* 184:    */   {
/* 185:330 */     this.idSucursal = idSucursal;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean isIndicadorNotificarFactura()
/* 189:    */   {
/* 190:334 */     return this.indicadorNotificarFactura;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setIndicadorNotificarFactura(boolean indicadorNotificarFactura)
/* 194:    */   {
/* 195:338 */     this.indicadorNotificarFactura = indicadorNotificarFactura;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public boolean isIndicadorNotificarRetencion()
/* 199:    */   {
/* 200:342 */     return this.indicadorNotificarRetencion;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setIndicadorNotificarRetencion(boolean indicadorNotificarRetencion)
/* 204:    */   {
/* 205:346 */     this.indicadorNotificarRetencion = indicadorNotificarRetencion;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isIndicadorNotificarNotaCredito()
/* 209:    */   {
/* 210:350 */     return this.indicadorNotificarNotaCredito;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setIndicadorNotificarNotaCredito(boolean indicadorNotificarNotaCredito)
/* 214:    */   {
/* 215:354 */     this.indicadorNotificarNotaCredito = indicadorNotificarNotaCredito;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean isIndicadorNotificarNotaDebito()
/* 219:    */   {
/* 220:358 */     return this.indicadorNotificarNotaDebito;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setIndicadorNotificarNotaDebito(boolean indicadorNotificarNotaDebito)
/* 224:    */   {
/* 225:362 */     this.indicadorNotificarNotaDebito = indicadorNotificarNotaDebito;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean isIndicadorNotificarGuiaRemision()
/* 229:    */   {
/* 230:366 */     return this.indicadorNotificarGuiaRemision;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setIndicadorNotificarGuiaRemision(boolean indicadorNotificarGuiaRemision)
/* 234:    */   {
/* 235:370 */     this.indicadorNotificarGuiaRemision = indicadorNotificarGuiaRemision;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public boolean isIndicadorNotificarPedidoCliente()
/* 239:    */   {
/* 240:374 */     return this.indicadorNotificarPedidoCliente;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setIndicadorNotificarPedidoCliente(boolean indicadorNotificarPedidoCliente)
/* 244:    */   {
/* 245:378 */     this.indicadorNotificarPedidoCliente = indicadorNotificarPedidoCliente;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public boolean isIndicadorNotificarPedidoProveedor()
/* 249:    */   {
/* 250:382 */     return this.indicadorNotificarPedidoProveedor;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setIndicadorNotificarPedidoProveedor(boolean indicadorNotificarPedidoProveedor)
/* 254:    */   {
/* 255:386 */     this.indicadorNotificarPedidoProveedor = indicadorNotificarPedidoProveedor;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public boolean isIndicadorNotificarPagoProveedor()
/* 259:    */   {
/* 260:390 */     return this.indicadorNotificarPagoProveedor;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setIndicadorNotificarPagoProveedor(boolean indicadorNotificarPagoProveedor)
/* 264:    */   {
/* 265:394 */     this.indicadorNotificarPagoProveedor = indicadorNotificarPagoProveedor;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public boolean isIndicadorNotificarVencimientoFacturaCliente()
/* 269:    */   {
/* 270:398 */     return this.indicadorNotificarVencimientoFacturaCliente;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setIndicadorNotificarVencimientoFacturaCliente(boolean indicadorNotificarVencimientoFacturaCliente)
/* 274:    */   {
/* 275:402 */     this.indicadorNotificarVencimientoFacturaCliente = indicadorNotificarVencimientoFacturaCliente;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public boolean isIndicadorNotificarSolicitudCompra()
/* 279:    */   {
/* 280:406 */     return this.indicadorNotificarSolicitudCompra;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setIndicadorNotificarSolicitudCompra(boolean indicadorNotificarSolicitudCompra)
/* 284:    */   {
/* 285:410 */     this.indicadorNotificarSolicitudCompra = indicadorNotificarSolicitudCompra;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public String getTextoCuerpoCorreoFactura()
/* 289:    */   {
/* 290:414 */     return this.textoCuerpoCorreoFactura;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setTextoCuerpoCorreoFactura(String textoCuerpoCorreoFactura)
/* 294:    */   {
/* 295:418 */     this.textoCuerpoCorreoFactura = textoCuerpoCorreoFactura;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public String getTextoCuerpoCorreoNotaCreditoCliente()
/* 299:    */   {
/* 300:422 */     return this.textoCuerpoCorreoNotaCreditoCliente;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setTextoCuerpoCorreoNotaCreditoCliente(String textoCuerpoCorreoNotaCreditoCliente)
/* 304:    */   {
/* 305:426 */     this.textoCuerpoCorreoNotaCreditoCliente = textoCuerpoCorreoNotaCreditoCliente;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public String getTextoCuerpoCorreoNotaDebitoCliente()
/* 309:    */   {
/* 310:430 */     return this.textoCuerpoCorreoNotaDebitoCliente;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setTextoCuerpoCorreoNotaDebitoCliente(String textoCuerpoCorreoNotaDebitoCliente)
/* 314:    */   {
/* 315:434 */     this.textoCuerpoCorreoNotaDebitoCliente = textoCuerpoCorreoNotaDebitoCliente;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public String getTextoCuerpoCorreoRetencionProveedor()
/* 319:    */   {
/* 320:438 */     return this.textoCuerpoCorreoRetencionProveedor;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setTextoCuerpoCorreoRetencionProveedor(String textoCuerpoCorreoRetencionProveedor)
/* 324:    */   {
/* 325:442 */     this.textoCuerpoCorreoRetencionProveedor = textoCuerpoCorreoRetencionProveedor;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public String getTextoCuerpoCorreoGuiaRemision()
/* 329:    */   {
/* 330:446 */     return this.textoCuerpoCorreoGuiaRemision;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setTextoCuerpoCorreoGuiaRemision(String textoCuerpoCorreoGuiaRemision)
/* 334:    */   {
/* 335:450 */     this.textoCuerpoCorreoGuiaRemision = textoCuerpoCorreoGuiaRemision;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public String getTextoCuerpoCorreoPedidoCliente()
/* 339:    */   {
/* 340:454 */     return this.textoCuerpoCorreoPedidoCliente;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void setTextoCuerpoCorreoPedidoCliente(String textoCuerpoCorreoPedidoCliente)
/* 344:    */   {
/* 345:458 */     this.textoCuerpoCorreoPedidoCliente = textoCuerpoCorreoPedidoCliente;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public String getTextoCuerpoCorreoPedidoProveedor()
/* 349:    */   {
/* 350:462 */     return this.textoCuerpoCorreoPedidoProveedor;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void setTextoCuerpoCorreoPedidoProveedor(String textoCuerpoCorreoPedidoProveedor)
/* 354:    */   {
/* 355:466 */     this.textoCuerpoCorreoPedidoProveedor = textoCuerpoCorreoPedidoProveedor;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public String getTextoCuerpoCorreoPagoProveedor()
/* 359:    */   {
/* 360:470 */     return this.textoCuerpoCorreoPagoProveedor;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void setTextoCuerpoCorreoPagoProveedor(String textoCuerpoCorreoPagoProveedor)
/* 364:    */   {
/* 365:474 */     this.textoCuerpoCorreoPagoProveedor = textoCuerpoCorreoPagoProveedor;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public String getTextoCuerpoCorreoVencimientoFacturaCliente()
/* 369:    */   {
/* 370:478 */     return this.textoCuerpoCorreoVencimientoFacturaCliente;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void setTextoCuerpoCorreoVencimientoFacturaCliente(String textoCuerpoCorreoVencimientoFacturaCliente)
/* 374:    */   {
/* 375:482 */     this.textoCuerpoCorreoVencimientoFacturaCliente = textoCuerpoCorreoVencimientoFacturaCliente;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public String getTextoCuerpoCorreoSolicitudCompra()
/* 379:    */   {
/* 380:486 */     return this.textoCuerpoCorreoSolicitudCompra;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setTextoCuerpoCorreoSolicitudCompra(String textoCuerpoCorreoSolicitudCompra)
/* 384:    */   {
/* 385:490 */     this.textoCuerpoCorreoSolicitudCompra = textoCuerpoCorreoSolicitudCompra;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public String getAbreviaturas()
/* 389:    */   {
/* 390:494 */     String abreviaturas = "( ";
/* 391:495 */     if (this.indicadorNotificarFactura) {
/* 392:496 */       abreviaturas = abreviaturas + "FC ";
/* 393:    */     }
/* 394:498 */     if (this.indicadorNotificarRetencion) {
/* 395:499 */       abreviaturas = abreviaturas + "RP ";
/* 396:    */     }
/* 397:501 */     if (this.indicadorNotificarNotaCredito) {
/* 398:502 */       abreviaturas = abreviaturas + "NC ";
/* 399:    */     }
/* 400:504 */     if (this.indicadorNotificarNotaDebito) {
/* 401:505 */       abreviaturas = abreviaturas + "ND ";
/* 402:    */     }
/* 403:507 */     if (this.indicadorNotificarGuiaRemision) {
/* 404:508 */       abreviaturas = abreviaturas + "GR ";
/* 405:    */     }
/* 406:510 */     if (this.indicadorNotificarPagoProveedor) {
/* 407:511 */       abreviaturas = abreviaturas + "PaP ";
/* 408:    */     }
/* 409:513 */     if (this.indicadorNotificarPedidoCliente) {
/* 410:514 */       abreviaturas = abreviaturas + "PC ";
/* 411:    */     }
/* 412:516 */     if (this.indicadorNotificarPedidoProveedor) {
/* 413:517 */       abreviaturas = abreviaturas + "PP ";
/* 414:    */     }
/* 415:519 */     if (this.indicadorNotificarVencimientoFacturaCliente) {
/* 416:520 */       abreviaturas = abreviaturas + "VFC ";
/* 417:    */     }
/* 418:522 */     if (this.indicadorNotificarSolicitudCompra) {
/* 419:523 */       abreviaturas = abreviaturas + "SC ";
/* 420:    */     }
/* 421:526 */     abreviaturas = abreviaturas + ")";
/* 422:    */     
/* 423:528 */     return abreviaturas;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public String toString()
/* 427:    */   {
/* 428:538 */     return this.nombre;
/* 429:    */   }
/* 430:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoContacto
 * JD-Core Version:    0.7.0.1
 */