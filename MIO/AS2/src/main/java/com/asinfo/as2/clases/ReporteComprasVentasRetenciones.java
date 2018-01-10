/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.JoinColumn;
/*   9:    */ import javax.persistence.ManyToOne;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="tmp_compras_ventas_retenciones")
/*  14:    */ public class ReporteComprasVentasRetenciones
/*  15:    */ {
/*  16:    */   @Id
/*  17:    */   @Column(name="id_compras_ventas_retenciones")
/*  18:    */   private int idComprasVentasRetenciones;
/*  19:    */   @Column(name="codigo_tipo_comprobante")
/*  20:    */   private String codigoTipoComprobante;
/*  21:    */   @Column(name="nombre_tipo_comprobante")
/*  22:    */   private String nombreTipoComprobante;
/*  23:    */   @Column(name="numero_registros")
/*  24:    */   private Long numeroRegistros;
/*  25:    */   @Column(name="base_imponible_tarifa0")
/*  26:    */   private BigDecimal baseImponibleTarifa0;
/*  27:    */   @Column(name="base_imponible_tarifa12")
/*  28:    */   private BigDecimal baseImponibleTarifa12;
/*  29:    */   @Column(name="base_no_objeto_iva")
/*  30:    */   private BigDecimal baseNoObjetoIva;
/*  31:    */   @Column(name="valor_iva")
/*  32:    */   private BigDecimal valorIva;
/*  33:    */   @Column(name="descuento_impuesto")
/*  34:    */   private BigDecimal descuentoImpuesto;
/*  35:    */   @Column(name="valor_fob_comprobante")
/*  36:    */   private BigDecimal valorFobComprobante;
/*  37:    */   @Column(name="tipo_reporte")
/*  38:    */   private String tipoReporte;
/*  39:    */   @Column(name="codigo_credito_tributario")
/*  40:    */   private String codigoCreditoTributario;
/*  41:    */   @Column(name="nombre_credito_tributario")
/*  42:    */   private String nombreCreditoTributario;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_reporte_general_retencion_venta", nullable=true)
/*  45:    */   private ReporteGeneralRetencion reporteGeneralRetencionVenta;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_reporte_general_retencion_cliente", nullable=true)
/*  48:    */   private ReporteGeneralRetencion reporteRetencionCliente;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_reporte_general_retencion_compra", nullable=true)
/*  51:    */   private ReporteGeneralRetencion reporteGeneralRetencionCompra;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_reporte_general_retencion_exportacion", nullable=true)
/*  54:    */   private ReporteGeneralRetencion reporteGeneralRetencionExportacion;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_reporte_general_retencion_anulado", nullable=true)
/*  57:    */   private ReporteGeneralRetencion reporteGeneralRetencionAnulado;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_reporte_general_retencion_resumido", nullable=true)
/*  60:    */   private ReporteGeneralRetencion reporteGeneralRetencionResumido;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_reporte_general_retencion_venta_fisicas", nullable=true)
/*  63:    */   private ReporteGeneralRetencion reporteGeneralRetencionVentaFisicas;
/*  64:    */   @Column(name="codigo_grupo")
/*  65:    */   private String codigoGrupo;
/*  66:    */   
/*  67:    */   public ReporteComprasVentasRetenciones(String codigoTipoComprobante, String nombreTipoComprobante, Long numeroRegistros, BigDecimal baseImponibleTarifa0, BigDecimal baseImponibleTarifa12, BigDecimal baseNoObjetoIva, BigDecimal valorIva, BigDecimal valorFobComprobante, String tipoReporte, String codigoCreditoTributario, String nombreCreditoTributario)
/*  68:    */   {
/*  69:121 */     this.codigoTipoComprobante = codigoTipoComprobante;
/*  70:122 */     this.nombreTipoComprobante = nombreTipoComprobante;
/*  71:123 */     this.numeroRegistros = numeroRegistros;
/*  72:124 */     this.baseImponibleTarifa0 = baseImponibleTarifa0;
/*  73:125 */     this.baseImponibleTarifa12 = baseImponibleTarifa12;
/*  74:126 */     this.baseNoObjetoIva = baseNoObjetoIva;
/*  75:127 */     this.valorIva = valorIva;
/*  76:128 */     this.valorFobComprobante = valorFobComprobante;
/*  77:129 */     this.tipoReporte = tipoReporte;
/*  78:130 */     this.codigoCreditoTributario = codigoCreditoTributario;
/*  79:131 */     this.nombreCreditoTributario = nombreCreditoTributario;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public ReporteComprasVentasRetenciones(String codigoTipoComprobante, String nombreTipoComprobante, Long numeroRegistros, BigDecimal baseImponibleTarifa0, BigDecimal baseImponibleTarifa12, BigDecimal baseNoObjetoIva, BigDecimal valorIva, String tipoReporte)
/*  83:    */   {
/*  84:147 */     this(codigoTipoComprobante, nombreTipoComprobante, numeroRegistros, baseImponibleTarifa0, baseImponibleTarifa12, baseNoObjetoIva, valorIva, BigDecimal.ZERO, tipoReporte, null, null);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ReporteComprasVentasRetenciones(String codigoTipoComprobante, String nombreTipoComprobante, Long numeroRegistros, BigDecimal baseImponibleTarifa0, BigDecimal baseImponibleTarifa12, BigDecimal baseNoObjetoIva, BigDecimal valorIva, String tipoReporte, String codigoCreditoTributario, String nombreCreditoTributario)
/*  88:    */   {
/*  89:154 */     this(codigoTipoComprobante, nombreTipoComprobante, numeroRegistros, baseImponibleTarifa0, baseImponibleTarifa12, baseNoObjetoIva, valorIva, BigDecimal.ZERO, tipoReporte, codigoCreditoTributario, nombreCreditoTributario);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public ReporteComprasVentasRetenciones(String codigoTipoComprobante, String nombreTipoComprobante, Long numeroRegistros, BigDecimal valorFobComprobante, String tipoReporte)
/*  93:    */   {
/*  94:170 */     this(codigoTipoComprobante, nombreTipoComprobante, numeroRegistros, valorFobComprobante, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, valorFobComprobante, tipoReporte, null, null);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public ReporteComprasVentasRetenciones(String codigoTipoComprobante, String nombreTipoComprobante, Long numeroRegistros, String tipoReporte)
/*  98:    */   {
/*  99:175 */     this(codigoTipoComprobante, nombreTipoComprobante, numeroRegistros, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, tipoReporte, null, null);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public ReporteComprasVentasRetenciones(String codigoTipoComprobante, String nombreTipoComprobante, Long numeroRegistros, BigDecimal baseImponibleTarifa0, BigDecimal baseImponibleTarifa12, BigDecimal baseNoObjetoIva, BigDecimal valorIva, String tipoReporte, String codigoGrupo)
/* 103:    */   {
/* 104:182 */     this(codigoTipoComprobante, nombreTipoComprobante, numeroRegistros, baseImponibleTarifa0, baseImponibleTarifa12, baseNoObjetoIva, valorIva, tipoReporte);
/* 105:    */     
/* 106:184 */     this.codigoGrupo = codigoGrupo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public ReporteComprasVentasRetenciones(String codigoTipoComprobante, String nombreTipoComprobante, Long numeroRegistros, BigDecimal baseImponibleTarifa0, BigDecimal baseImponibleTarifa12, BigDecimal baseNoObjetoIva, BigDecimal valorIva, String tipoReporte, String codigoGrupo, BigDecimal descuentoImpuesto)
/* 110:    */   {
/* 111:190 */     this(codigoTipoComprobante, nombreTipoComprobante, numeroRegistros, baseImponibleTarifa0, baseImponibleTarifa12, baseNoObjetoIva, valorIva, tipoReporte, codigoGrupo);
/* 112:    */     
/* 113:192 */     this.descuentoImpuesto = descuentoImpuesto;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public ReporteComprasVentasRetenciones(String codigoTipoComprobante, String nombreTipoComprobante, Long numeroRegistros, BigDecimal baseImponibleTarifa12, String tipoReporte, String codigoGrupo)
/* 117:    */   {
/* 118:197 */     this.codigoTipoComprobante = codigoTipoComprobante;
/* 119:198 */     this.nombreTipoComprobante = nombreTipoComprobante;
/* 120:199 */     this.numeroRegistros = numeroRegistros;
/* 121:200 */     this.baseImponibleTarifa12 = baseImponibleTarifa12;
/* 122:201 */     this.tipoReporte = tipoReporte;
/* 123:202 */     this.codigoGrupo = codigoGrupo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getIdComprasVentasRetenciones()
/* 127:    */   {
/* 128:213 */     return this.idComprasVentasRetenciones;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setIdComprasVentasRetenciones(int idComprasVentasRetenciones)
/* 132:    */   {
/* 133:223 */     this.idComprasVentasRetenciones = idComprasVentasRetenciones;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getCodigoTipoComprobante()
/* 137:    */   {
/* 138:232 */     return this.codigoTipoComprobante;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setCodigoTipoComprobante(String codigoTipoComprobante)
/* 142:    */   {
/* 143:242 */     this.codigoTipoComprobante = codigoTipoComprobante;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getNombreTipoComprobante()
/* 147:    */   {
/* 148:251 */     return this.nombreTipoComprobante;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setNombreTipoComprobante(String nombreTipoComprobante)
/* 152:    */   {
/* 153:261 */     this.nombreTipoComprobante = nombreTipoComprobante;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Long getNumeroRegistros()
/* 157:    */   {
/* 158:270 */     return this.numeroRegistros;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setNumeroRegistros(Long numeroRegistros)
/* 162:    */   {
/* 163:280 */     this.numeroRegistros = numeroRegistros;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public BigDecimal getBaseImponibleTarifa0()
/* 167:    */   {
/* 168:289 */     return this.baseImponibleTarifa0;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setBaseImponibleTarifa0(BigDecimal baseImponibleTarifa0)
/* 172:    */   {
/* 173:299 */     this.baseImponibleTarifa0 = baseImponibleTarifa0;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public BigDecimal getBaseImponibleTarifa12()
/* 177:    */   {
/* 178:308 */     return this.baseImponibleTarifa12;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setBaseImponibleTarifa12(BigDecimal baseImponibleTarifa12)
/* 182:    */   {
/* 183:318 */     this.baseImponibleTarifa12 = baseImponibleTarifa12;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public BigDecimal getBaseNoObjetoIva()
/* 187:    */   {
/* 188:327 */     return this.baseNoObjetoIva;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setBaseNoObjetoIva(BigDecimal baseNoObjetoIva)
/* 192:    */   {
/* 193:337 */     this.baseNoObjetoIva = baseNoObjetoIva;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public BigDecimal getValorIva()
/* 197:    */   {
/* 198:346 */     return this.valorIva;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setValorIva(BigDecimal valorIva)
/* 202:    */   {
/* 203:356 */     this.valorIva = valorIva;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public BigDecimal getValorFobComprobante()
/* 207:    */   {
/* 208:365 */     return this.valorFobComprobante;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setValorFobComprobante(BigDecimal valorFobComprobante)
/* 212:    */   {
/* 213:375 */     this.valorFobComprobante = valorFobComprobante;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String getTipoReporte()
/* 217:    */   {
/* 218:384 */     return this.tipoReporte;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setTipoReporte(String tipoReporte)
/* 222:    */   {
/* 223:394 */     this.tipoReporte = tipoReporte;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public ReporteGeneralRetencion getReporteGeneralRetencionVenta()
/* 227:    */   {
/* 228:403 */     return this.reporteGeneralRetencionVenta;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setReporteGeneralRetencionVenta(ReporteGeneralRetencion reporteGeneralRetencionVenta)
/* 232:    */   {
/* 233:413 */     this.reporteGeneralRetencionVenta = reporteGeneralRetencionVenta;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public ReporteGeneralRetencion getReporteGeneralRetencionCompra()
/* 237:    */   {
/* 238:422 */     return this.reporteGeneralRetencionCompra;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setReporteGeneralRetencionCompra(ReporteGeneralRetencion reporteGeneralRetencionCompra)
/* 242:    */   {
/* 243:432 */     this.reporteGeneralRetencionCompra = reporteGeneralRetencionCompra;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public ReporteGeneralRetencion getReporteGeneralRetencionExportacion()
/* 247:    */   {
/* 248:441 */     return this.reporteGeneralRetencionExportacion;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setReporteGeneralRetencionExportacion(ReporteGeneralRetencion reporteGeneralRetencionExportacion)
/* 252:    */   {
/* 253:452 */     this.reporteGeneralRetencionExportacion = reporteGeneralRetencionExportacion;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public ReporteGeneralRetencion getReporteGeneralRetencionAnulado()
/* 257:    */   {
/* 258:461 */     return this.reporteGeneralRetencionAnulado;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setReporteGeneralRetencionAnulado(ReporteGeneralRetencion reporteGeneralRetencionAnulado)
/* 262:    */   {
/* 263:471 */     this.reporteGeneralRetencionAnulado = reporteGeneralRetencionAnulado;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public ReporteGeneralRetencion getReporteGeneralRetencionResumido()
/* 267:    */   {
/* 268:480 */     return this.reporteGeneralRetencionResumido;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setReporteGeneralRetencionResumido(ReporteGeneralRetencion reporteGeneralRetencionResumido)
/* 272:    */   {
/* 273:490 */     this.reporteGeneralRetencionResumido = reporteGeneralRetencionResumido;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public String getCodigoCreditoTributario()
/* 277:    */   {
/* 278:494 */     return this.codigoCreditoTributario;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setCodigoCreditoTributario(String codigoCreditoTributario)
/* 282:    */   {
/* 283:498 */     this.codigoCreditoTributario = codigoCreditoTributario;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public String getNombreCreditoTributario()
/* 287:    */   {
/* 288:502 */     return this.nombreCreditoTributario;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setNombreCreditoTributario(String nombreCreditoTributario)
/* 292:    */   {
/* 293:506 */     this.nombreCreditoTributario = nombreCreditoTributario;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public String getCodigoGrupo()
/* 297:    */   {
/* 298:510 */     return this.codigoGrupo;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setCodigoGrupo(String codigoGrupo)
/* 302:    */   {
/* 303:514 */     this.codigoGrupo = codigoGrupo;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public ReporteGeneralRetencion getReporteRetencionCliente()
/* 307:    */   {
/* 308:518 */     return this.reporteRetencionCliente;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setReporteRetencionCliente(ReporteGeneralRetencion reporteRetencionCliente)
/* 312:    */   {
/* 313:522 */     this.reporteRetencionCliente = reporteRetencionCliente;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public ReporteGeneralRetencion getReporteGeneralRetencionVentaFisicas()
/* 317:    */   {
/* 318:529 */     return this.reporteGeneralRetencionVentaFisicas;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setReporteGeneralRetencionVentaFisicas(ReporteGeneralRetencion reporteGeneralRetencionVentaFisicas)
/* 322:    */   {
/* 323:536 */     this.reporteGeneralRetencionVentaFisicas = reporteGeneralRetencionVentaFisicas;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public BigDecimal getDescuentoImpuesto()
/* 327:    */   {
/* 328:540 */     return this.descuentoImpuesto;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setDescuentoImpuesto(BigDecimal descuentoImpuesto)
/* 332:    */   {
/* 333:544 */     this.descuentoImpuesto = descuentoImpuesto;
/* 334:    */   }
/* 335:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteComprasVentasRetenciones
 * JD-Core Version:    0.7.0.1
 */