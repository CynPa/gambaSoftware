/*   1:    */ package com.asinfo.as2.compras.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteAnticipoProveedor;
/*   4:    */ import com.asinfo.as2.clases.ReporteFacturaProveedor;
/*   5:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   6:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   7:    */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*   8:    */ import com.asinfo.as2.dao.GenericoDao;
/*   9:    */ import com.asinfo.as2.dao.reportes.compras.AnalisisVencimientosProveedorDao;
/*  10:    */ import com.asinfo.as2.dao.reportes.compras.ReporteCompraDao;
/*  11:    */ import com.asinfo.as2.dao.reportes.compras.ReportePedidoSaldoPorRecibirDao;
/*  12:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*  13:    */ import com.asinfo.as2.entities.Asiento;
/*  14:    */ import com.asinfo.as2.entities.Atributo;
/*  15:    */ import com.asinfo.as2.entities.Bodega;
/*  16:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  17:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  18:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoProveedor;
/*  19:    */ import com.asinfo.as2.entities.DetallePago;
/*  20:    */ import com.asinfo.as2.entities.Empresa;
/*  21:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  22:    */ import com.asinfo.as2.entities.LiquidacionAnticipoProveedor;
/*  23:    */ import com.asinfo.as2.entities.Pago;
/*  24:    */ import com.asinfo.as2.entities.Producto;
/*  25:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  26:    */ import com.asinfo.as2.entities.Sucursal;
/*  27:    */ import com.asinfo.as2.entities.TipoOperacion;
/*  28:    */ import com.asinfo.as2.entities.ValorAtributo;
/*  29:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  30:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  31:    */ import com.asinfo.as2.enumeraciones.OrdenamientoEnum;
/*  32:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  33:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  34:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  35:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  36:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  37:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  38:    */ import java.math.BigDecimal;
/*  39:    */ import java.math.RoundingMode;
/*  40:    */ import java.util.ArrayList;
/*  41:    */ import java.util.Collection;
/*  42:    */ import java.util.Collections;
/*  43:    */ import java.util.Comparator;
/*  44:    */ import java.util.Date;
/*  45:    */ import java.util.HashMap;
/*  46:    */ import java.util.HashSet;
/*  47:    */ import java.util.Iterator;
/*  48:    */ import java.util.List;
/*  49:    */ import java.util.Map;
/*  50:    */ import java.util.Set;
/*  51:    */ import javax.ejb.EJB;
/*  52:    */ import javax.ejb.Stateless;
/*  53:    */ 
/*  54:    */ @Stateless
/*  55:    */ public class ServicioReporteCompraImpl
/*  56:    */   implements ServicioReporteCompra
/*  57:    */ {
/*  58:    */   @EJB
/*  59:    */   private AnalisisVencimientosProveedorDao analisisVencimientosProveedorDao;
/*  60:    */   @EJB
/*  61:    */   private ReportePedidoSaldoPorRecibirDao reportePedidoSaldoPorRecibirDao;
/*  62:    */   @EJB
/*  63:    */   private ReporteCompraDao reporteCompraDao;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioProducto servicioProducto;
/*  66:    */   @EJB
/*  67:    */   private FacturaProveedorDao facturaProveedorDao;
/*  68:    */   @EJB
/*  69:    */   private GenericoDao<DetallePago> detallePagoDao;
/*  70:    */   @EJB
/*  71:    */   private GenericoDao<DetalleLiquidacionAnticipoProveedor> detalleLiquidacionAnticipoProveedorDao;
/*  72:    */   
/*  73:    */   public List getAnalisisVencimientoProveedor(Date fechaDesde, Date fechaHasta, int idEmpresa, int idOrganizacion, int idTipoOperacion, TipoOrganizacion tipoOrganizacion, Sucursal sucursal, CategoriaEmpresa categoriaEmpresa, boolean indicadorSoloVencido)
/*  74:    */     throws ExcepcionAS2Compras
/*  75:    */   {
/*  76:107 */     List<Object[]> datos = this.analisisVencimientosProveedorDao.getAnalisisVencimientoProveedor(fechaDesde, fechaHasta, idEmpresa, idOrganizacion, idTipoOperacion, sucursal, categoriaEmpresa, indicadorSoloVencido);
/*  77:    */     Map<String, Map<Integer, Object[]>> hmFacturas;
/*  78:    */     Object[] fila;
/*  79:    */     int i;
/*  80:110 */     if (tipoOrganizacion == TipoOrganizacion.TIPO_ORGANIZACION_ADRIALPETRO)
/*  81:    */     {
/*  82:111 */       List<Object[]> datosPedidoProveedor = this.analisisVencimientosProveedorDao.getDatosPedido(idOrganizacion, idEmpresa, fechaHasta);
/*  83:112 */       List<Object[]> datosDimensiones = this.analisisVencimientosProveedorDao.getDatosDimension(idOrganizacion, idEmpresa, fechaHasta);
/*  84:    */       
/*  85:114 */       hmFacturas = new HashMap();
/*  86:115 */       for (Iterator localIterator1 = datos.iterator(); localIterator1.hasNext();)
/*  87:    */       {
/*  88:115 */         fila = (Object[])localIterator1.next();
/*  89:116 */         String clave = fila[0] + "~" + fila[3];
/*  90:    */         
/*  91:118 */         Map<Integer, Object[]> hmFactura = (Map)hmFacturas.get(clave);
/*  92:120 */         if (hmFactura == null)
/*  93:    */         {
/*  94:121 */           hmFactura = new HashMap();
/*  95:122 */           hmFacturas.put(clave, hmFactura);
/*  96:    */         }
/*  97:124 */         hmFactura.put(Integer.valueOf(fila.hashCode()), fila);
/*  98:    */       }
/*  99:128 */       i = datos.isEmpty() ? 0 : ((Object[])datos.get(0)).length;
/* 100:129 */       for (fila = datosPedidoProveedor.iterator(); fila.hasNext();)
/* 101:    */       {
/* 102:129 */         pedido = (Object[])fila.next();
/* 103:130 */         String clave = pedido[0] + "~" + pedido[1];
/* 104:    */         
/* 105:    */ 
/* 106:133 */         Map<Integer, Object[]> hmFactura = (Map)hmFacturas.get(clave);
/* 107:134 */         if (hmFactura != null) {
/* 108:135 */           for (Object[] facturas : hmFactura.values()) {
/* 109:136 */             facturas[(i - 2)] = (("".equals(facturas[(i - 2)]) ? "" : new StringBuilder().append(facturas[(i - 2)].toString()).append(", ").toString()) + pedido[2]);
/* 110:    */           }
/* 111:    */         }
/* 112:    */       }
/* 113:    */       Object[] pedido;
/* 114:142 */       for (fila = datosDimensiones.iterator(); fila.hasNext();)
/* 115:    */       {
/* 116:142 */         dimension = (Object[])fila.next();
/* 117:143 */         String clave = dimension[0] + "~" + dimension[1];
/* 118:    */         
/* 119:    */ 
/* 120:    */ 
/* 121:147 */         Map<Integer, Object[]> hmFactura = (Map)hmFacturas.get(clave);
/* 122:148 */         if (hmFactura != null) {
/* 123:149 */           for (Object[] facturas : hmFactura.values()) {
/* 124:150 */             facturas[(i - 1)] = (("".equals(facturas[(i - 1)]) ? "" : new StringBuilder().append(facturas[(i - 1)].toString()).append(", ").toString()) + dimension[2]);
/* 125:    */           }
/* 126:    */         }
/* 127:    */       }
/* 128:    */     }
/* 129:    */     Object[] dimension;
/* 130:157 */     return datos;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public List<Object[]> getReportePedidoSaldoPorRecibir(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 134:    */   {
/* 135:169 */     return this.reportePedidoSaldoPorRecibirDao.getReportePedidoSaldoPorRecibir(fechaDesde, fechaHasta, idEmpresa, soloPendientes, categoriaProducto, subcategoriaProducto, idOrganizacion);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public List getListaReporteFacturacionResumidoCompra(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idProveedor, int idDocumento, boolean saldoInicial, int idOrganizacion, int tipoCreditoTributario, boolean indicadorResumido, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, List<Producto> listaProducto, Sucursal sucursal, DocumentoBase documentoBase)
/* 139:    */     throws ExcepcionAS2Ventas
/* 140:    */   {
/* 141:184 */     return this.reporteCompraDao.getListaReporteFacturacionResumidoCompra(fechaDesde, fechaHasta, numeroDesde, numeroHasta, idProveedor, idDocumento, saldoInicial, idOrganizacion, tipoCreditoTributario, indicadorResumido, categoriaProducto, subcategoriaProducto, listaProducto, sucursal, documentoBase);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List getListaReporteCorteFechaResumido(Date fechaHasta, int idProveedor, int idOrganizacion)
/* 145:    */     throws ExcepcionAS2Ventas
/* 146:    */   {
/* 147:197 */     return this.reporteCompraDao.getListaReporteCorteFechaResumido(fechaHasta, idProveedor, idOrganizacion);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List getListaReporteCorteFecha(Date fechaHasta, int idProveedor, int idOrganizacion, int idTipoOperacion, Sucursal sucursal, CategoriaEmpresa categoriaEmpresa)
/* 151:    */     throws ExcepcionAS2
/* 152:    */   {
/* 153:211 */     List lista = this.reporteCompraDao.getListaReporteCorteFecha(fechaHasta, idProveedor, idOrganizacion, idTipoOperacion, sucursal, categoriaEmpresa);
/* 154:213 */     if (lista.size() == 0) {
/* 155:214 */       throw new ExcepcionAS2("msg_no_hay_datos");
/* 156:    */     }
/* 157:216 */     return lista;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public BigDecimal obtenerSaldoEstadoCuenta(int idProveedor, Date fechaHasta, int idTipoOperacion)
/* 161:    */   {
/* 162:231 */     return this.reporteCompraDao.obtenerSaldoEstadoCuenta(idProveedor, fechaHasta, idTipoOperacion);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List getListaReporteEstadoCuenta(Date fechaDesde, Date fechaHasta, int idProveedor, int idTipoOperacion, OrdenamientoEnum orden, boolean saldoDiferenteDeCero, int idOrganizacion, Sucursal sucursal, CategoriaEmpresa categoriaEmpresa)
/* 166:    */     throws ExcepcionAS2Ventas
/* 167:    */   {
/* 168:245 */     return this.reporteCompraDao.getListaReporteEstadoCuenta(fechaDesde, fechaHasta, idProveedor, idTipoOperacion, orden, saldoDiferenteDeCero, idOrganizacion, sucursal, categoriaEmpresa);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List getReporteFacturaProveedor(int idFacturaProveedor)
/* 172:    */   {
/* 173:258 */     return this.reporteCompraDao.getReporteFacturaProveedor(idFacturaProveedor);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List getVencimientos(Empresa empresa, Date fecha, int idOrganizacion, TipoOperacion tipoOperacion, CategoriaEmpresa categoriaEmpresa, boolean indicadorSoloVencido)
/* 177:    */     throws ExcepcionAS2
/* 178:    */   {
/* 179:271 */     List lista = this.analisisVencimientosProveedorDao.getVencimientos(empresa, fecha, idOrganizacion, tipoOperacion, categoriaEmpresa, indicadorSoloVencido);
/* 180:273 */     if (lista.size() == 0) {
/* 181:274 */       throw new ExcepcionAS2("msg_no_hay_datos");
/* 182:    */     }
/* 183:276 */     return lista;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<Object[]> getListaReporteEstadoCuenta(int idProveedor, FacturaProveedor facturaProveedor)
/* 187:    */   {
/* 188:288 */     return this.reporteCompraDao.getListaReporteEstadoCuenta(idProveedor, facturaProveedor);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List getReporteComparativoPrecioCompra(int idSubcategoriaProducto, TipoProducto tipoProducto)
/* 192:    */   {
/* 193:301 */     return this.reporteCompraDao.getReporteComparativoPrecioCompra(idSubcategoriaProducto, tipoProducto);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public List getReporteAnalisisComprasProveedor(Date fechaDesde, Date fechaHasta, boolean saldoInicial, int idOrganizacion, int idSubcategoriaProducto, boolean indicadorCantidad)
/* 197:    */   {
/* 198:308 */     return this.reporteCompraDao.getReporteAnalisisComprasProveedor(fechaDesde, fechaHasta, saldoInicial, idOrganizacion, idSubcategoriaProducto, indicadorCantidad);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List getReporteAnalisisComprasProducto(Date fechaDesde, Date fechaHasta, boolean saldoInicial, int idOrganizacion, int idSubcategoriaProducto, boolean indicadorCantidad)
/* 202:    */   {
/* 203:316 */     return this.reporteCompraDao.getReporteAnalisisComprasProducto(fechaDesde, fechaHasta, saldoInicial, idOrganizacion, idSubcategoriaProducto, indicadorCantidad);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<ReporteAnticipoProveedor> getReporteAnticipoProveedores(Date fechaDesde, Date fechaHasta, int idProveedor, int idOrganizacion, boolean indicadorResumido)
/* 207:    */     throws ExcepcionAS2
/* 208:    */   {
/* 209:329 */     return this.reporteCompraDao.getReporteAnticipoProveedor(fechaDesde, fechaHasta, idProveedor, idOrganizacion, indicadorResumido);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<ReporteAnticipoProveedor> getReporteLiquidacionAnticipoProveedor(Date fechaDesde, Date fechaHasta, List<String> listaNumeroAnticipo, int idProveedor, int idOrganizacion, boolean indicadorResumido)
/* 213:    */     throws ExcepcionAS2
/* 214:    */   {
/* 215:343 */     return this.reporteCompraDao.getReporteLiquidacionAnticipoProveedor(fechaDesde, fechaHasta, listaNumeroAnticipo, idProveedor, idOrganizacion, indicadorResumido);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List<ReporteAnticipoProveedor> getReporteAnticipoProveedoresResumido(Date fechaDesde, Date fechaHasta, int idProveedor, int idOrganizacion, boolean indicadorResumido)
/* 219:    */     throws ExcepcionAS2
/* 220:    */   {
/* 221:357 */     HashMap<String, ReporteAnticipoProveedor> hmReporteAnticipoProveedor = new HashMap();
/* 222:    */     
/* 223:    */ 
/* 224:360 */     List<ReporteAnticipoProveedor> listaSaldoAnticipoProveedor = this.reporteCompraDao.getReporteSaldoAnticipoProveedor(fechaDesde, fechaHasta, idProveedor, idOrganizacion, true);
/* 225:362 */     for (ReporteAnticipoProveedor reporteAnticipoProveedor : listaSaldoAnticipoProveedor)
/* 226:    */     {
/* 227:363 */       clave = reporteAnticipoProveedor.getIdentificacion();
/* 228:364 */       if (hmReporteAnticipoProveedor.containsKey(clave))
/* 229:    */       {
/* 230:365 */         ReporteAnticipoProveedor rap = (ReporteAnticipoProveedor)hmReporteAnticipoProveedor.get(clave);
/* 231:366 */         rap.setValor(rap.getValor().add(reporteAnticipoProveedor.getValor()));
/* 232:    */       }
/* 233:    */       else
/* 234:    */       {
/* 235:368 */         reporteAnticipoProveedor.setValor(reporteAnticipoProveedor.getValor());
/* 236:369 */         hmReporteAnticipoProveedor.put(clave, reporteAnticipoProveedor);
/* 237:    */       }
/* 238:    */     }
/* 239:    */     String clave;
/* 240:375 */     Object listaAnticipoProveedor = this.reporteCompraDao.getReporteAnticipoProveedor(fechaDesde, fechaHasta, idProveedor, idOrganizacion, indicadorResumido);
/* 241:    */     
/* 242:377 */     List<String> listaNumeroAnticipo = new ArrayList();
/* 243:378 */     for (ReporteAnticipoProveedor reporteAnticipoProveedor : (List)listaAnticipoProveedor)
/* 244:    */     {
/* 245:379 */       clave = reporteAnticipoProveedor.getIdentificacion();
/* 246:380 */       if (hmReporteAnticipoProveedor.containsKey(clave))
/* 247:    */       {
/* 248:381 */         ReporteAnticipoProveedor rap = (ReporteAnticipoProveedor)hmReporteAnticipoProveedor.get(clave);
/* 249:382 */         rap.setValorAnticipo(rap.getValorAnticipo().add(reporteAnticipoProveedor.getValorAnticipo()));
/* 250:    */       }
/* 251:    */       else
/* 252:    */       {
/* 253:384 */         reporteAnticipoProveedor.setValorAnticipo(reporteAnticipoProveedor.getValorAnticipo());
/* 254:385 */         hmReporteAnticipoProveedor.put(clave, reporteAnticipoProveedor);
/* 255:    */       }
/* 256:    */     }
/* 257:389 */     List<ReporteAnticipoProveedor> listaLiquidacionAnticipoProveedor = this.reporteCompraDao.getReporteLiquidacionAnticipoProveedor(fechaDesde, fechaHasta, listaNumeroAnticipo, idProveedor, idOrganizacion, indicadorResumido);
/* 258:    */     
/* 259:    */ 
/* 260:392 */     HashMap<String, ReporteAnticipoProveedor> hmReporteLiquidacionAnticipoProveedor = new HashMap();
/* 261:393 */     for (ReporteAnticipoProveedor reporteAnticipoProveedor : listaLiquidacionAnticipoProveedor)
/* 262:    */     {
/* 263:395 */       String clave = reporteAnticipoProveedor.getIdentificacion();
/* 264:396 */       ReporteAnticipoProveedor rap = (ReporteAnticipoProveedor)hmReporteAnticipoProveedor.get(clave);
/* 265:397 */       if (rap != null)
/* 266:    */       {
/* 267:398 */         rap.setValorLiquidacionAnticipo(rap.getValorLiquidacionAnticipo().add(reporteAnticipoProveedor.getValorLiquidacionAnticipo()));
/* 268:    */       }
/* 269:    */       else
/* 270:    */       {
/* 271:400 */         reporteAnticipoProveedor.setValorLiquidacionAnticipo(reporteAnticipoProveedor.getValorLiquidacionAnticipo());
/* 272:401 */         hmReporteLiquidacionAnticipoProveedor.put(clave, reporteAnticipoProveedor);
/* 273:    */       }
/* 274:    */     }
/* 275:405 */     for (String clave = hmReporteLiquidacionAnticipoProveedor.values().iterator(); clave.hasNext();)
/* 276:    */     {
/* 277:405 */       reporteAnticipoProveedor = (ReporteAnticipoProveedor)clave.next();
/* 278:406 */       String clave = reporteAnticipoProveedor.getIdentificacion();
/* 279:407 */       if (hmReporteAnticipoProveedor.containsKey(clave))
/* 280:    */       {
/* 281:408 */         ReporteAnticipoProveedor rap = (ReporteAnticipoProveedor)hmReporteAnticipoProveedor.get(clave);
/* 282:409 */         rap.setValorLiquidacionAnticipo(reporteAnticipoProveedor.getValorLiquidacionAnticipo());
/* 283:    */       }
/* 284:    */     }
/* 285:    */     ReporteAnticipoProveedor reporteAnticipoProveedor;
/* 286:413 */     List<ReporteAnticipoProveedor> lista = new ArrayList();
/* 287:414 */     for (ReporteAnticipoProveedor reporteAnticipoProveedor : hmReporteAnticipoProveedor.values()) {
/* 288:415 */       if ((reporteAnticipoProveedor.getValor().compareTo(BigDecimal.ZERO) != 0) || 
/* 289:416 */         (reporteAnticipoProveedor.getValorAnticipo().compareTo(BigDecimal.ZERO) != 0) || 
/* 290:417 */         (reporteAnticipoProveedor.getValorLiquidacionAnticipo().compareTo(BigDecimal.ZERO) != 0)) {
/* 291:418 */         lista.add(reporteAnticipoProveedor);
/* 292:    */       }
/* 293:    */     }
/* 294:421 */     return lista;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<ReporteAnticipoProveedor> getReporteSaldoAnticipoProveedor(Date fechaDesde, Date fechaHasta, int idProveedor, int idOrganizacion)
/* 298:    */     throws ExcepcionAS2
/* 299:    */   {
/* 300:434 */     return this.reporteCompraDao.getReporteSaldoAnticipoProveedor(fechaDesde, fechaHasta, idProveedor, idOrganizacion, false);
/* 301:    */   }
/* 302:    */   
/* 303:    */   public List getReporteFacturaRecepcion(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 304:    */   {
/* 305:440 */     return this.reportePedidoSaldoPorRecibirDao.getReporteFacturaRecepcion(fechaDesde, fechaHasta, idEmpresa, soloPendientes, categoriaProducto, subcategoriaProducto, idOrganizacion);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public List<Object[]> getReporteRecepcionFactura(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 309:    */   {
/* 310:446 */     return this.reportePedidoSaldoPorRecibirDao.getReporteRecepcionFactura(fechaDesde, fechaHasta, idEmpresa, soloPendientes, categoriaProducto, subcategoriaProducto, idOrganizacion);
/* 311:    */   }
/* 312:    */   
/* 313:    */   private Object[] crearDetalleReporte(Object[] obj, String periodoEstadistico)
/* 314:    */   {
/* 315:450 */     Object[] object = new Object[17];
/* 316:451 */     object[0] = obj[0];
/* 317:452 */     object[1] = obj[1];
/* 318:453 */     object[2] = obj[2];
/* 319:454 */     object[3] = obj[3];
/* 320:455 */     object[4] = obj[4];
/* 321:456 */     object[5] = BigDecimal.ZERO;
/* 322:457 */     object[6] = BigDecimal.ZERO;
/* 323:458 */     object[7] = BigDecimal.ZERO;
/* 324:459 */     object[8] = BigDecimal.ZERO;
/* 325:460 */     object[9] = BigDecimal.ZERO;
/* 326:461 */     object[10] = BigDecimal.ZERO;
/* 327:462 */     object[11] = BigDecimal.ZERO;
/* 328:463 */     object[12] = BigDecimal.ZERO;
/* 329:464 */     object[13] = BigDecimal.ZERO;
/* 330:465 */     object[14] = BigDecimal.ZERO;
/* 331:466 */     object[15] = Integer.valueOf(12);
/* 332:467 */     object[16] = periodoEstadistico;
/* 333:468 */     return object;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public List<Object[]> getReportePlanificacionCompras(int idOrganizacion, Date fechaCorte, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Bodega bodega, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo, Integer mesesSinConsumo)
/* 337:    */   {
/* 338:475 */     List<Object[]> listResult = new ArrayList();
/* 339:476 */     Date fechaDesdePeriodoEstadistico = FuncionesUtiles.sumarFechaAnios(fechaCorte, -1);
/* 340:477 */     Date fechaConsumoValido = FuncionesUtiles.sumarFechaMeses(fechaCorte, -mesesSinConsumo.intValue());
/* 341:478 */     String periodoEstadistico = FuncionesUtiles.dateToString(fechaDesdePeriodoEstadistico) + " - " + FuncionesUtiles.dateToString(fechaCorte);
/* 342:479 */     Map<Integer, Object[]> mapProductos = new HashMap();
/* 343:    */     
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:489 */     List<Object[]> listaStockMinimo = this.reporteCompraDao.getStockMinimoProducto(idOrganizacion, categoriaProducto, subcategoriaProducto, producto, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 353:491 */     for (Iterator localIterator = listaStockMinimo.iterator(); localIterator.hasNext();)
/* 354:    */     {
/* 355:491 */       objects = (Object[])localIterator.next();
/* 356:492 */       Object[] obj = crearDetalleReporte(objects, periodoEstadistico);
/* 357:493 */       obj[5] = objects[5];
/* 358:494 */       mapProductos.put((Integer)objects[0], obj);
/* 359:    */     }
/* 360:499 */     Object listaConsumoBodega = this.reporteCompraDao.getCosumoMensualPromedio(idOrganizacion, fechaConsumoValido, fechaDesdePeriodoEstadistico, fechaCorte, categoriaProducto, subcategoriaProducto, producto, bodega, atributo, valorAtributoSeleccionado, textoValorAtributo, "DetalleMovimientoInventario", "movimientoInventario");
/* 361:502 */     for (Object[] objects = ((List)listaConsumoBodega).iterator(); objects.hasNext();)
/* 362:    */     {
/* 363:502 */       objects = (Object[])objects.next();
/* 364:503 */       Object[] obj = (Object[])mapProductos.get(objects[0]);
/* 365:504 */       if (obj != null)
/* 366:    */       {
/* 367:505 */         obj[6] = objects[5];
/* 368:506 */         mapProductos.put((Integer)objects[0], obj);
/* 369:    */       }
/* 370:    */       else
/* 371:    */       {
/* 372:508 */         obj = crearDetalleReporte(objects, periodoEstadistico);
/* 373:509 */         obj[6] = objects[5];
/* 374:510 */         mapProductos.put((Integer)objects[0], obj);
/* 375:    */       }
/* 376:    */     }
/* 377:516 */     List<Object[]> listaPromedioDespachoCliente = this.reporteCompraDao.getCosumoMensualPromedio(idOrganizacion, fechaConsumoValido, fechaDesdePeriodoEstadistico, fechaCorte, categoriaProducto, subcategoriaProducto, producto, bodega, atributo, valorAtributoSeleccionado, textoValorAtributo, "DetalleDespachoCliente", "despachoCliente");
/* 378:519 */     for (Object[] objects = listaPromedioDespachoCliente.iterator(); objects.hasNext();)
/* 379:    */     {
/* 380:519 */       objects = (Object[])objects.next();
/* 381:520 */       Object[] obj = (Object[])mapProductos.get(objects[0]);
/* 382:521 */       if (obj != null)
/* 383:    */       {
/* 384:522 */         obj[6] = new BigDecimal(obj[6].toString()).add(new BigDecimal(objects[5].toString()));
/* 385:523 */         mapProductos.put((Integer)objects[0], obj);
/* 386:    */       }
/* 387:    */       else
/* 388:    */       {
/* 389:525 */         obj = crearDetalleReporte(objects, periodoEstadistico);
/* 390:526 */         obj[6] = objects[5];
/* 391:527 */         mapProductos.put((Integer)objects[0], obj);
/* 392:    */       }
/* 393:    */     }
/* 394:533 */     List<Object[]> listaStockEnTransitoLocal = this.reporteCompraDao.getStockEnTransitoLocal(idOrganizacion, fechaConsumoValido, fechaDesdePeriodoEstadistico, fechaCorte, categoriaProducto, subcategoriaProducto, producto, bodega, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 395:536 */     for (Object[] objects = listaStockEnTransitoLocal.iterator(); objects.hasNext();)
/* 396:    */     {
/* 397:536 */       objects = (Object[])objects.next();
/* 398:537 */       Object[] obj = (Object[])mapProductos.get(objects[0]);
/* 399:538 */       if (obj != null)
/* 400:    */       {
/* 401:539 */         obj[8] = objects[5];
/* 402:540 */         mapProductos.put((Integer)objects[0], obj);
/* 403:    */       }
/* 404:    */       else
/* 405:    */       {
/* 406:542 */         obj = crearDetalleReporte(objects, periodoEstadistico);
/* 407:543 */         obj[8] = objects[5];
/* 408:544 */         mapProductos.put((Integer)objects[0], obj);
/* 409:    */       }
/* 410:    */     }
/* 411:550 */     List<Object[]> listaStockEnTransitoImportacion = this.reporteCompraDao.getStockEnTransitoImportacion(idOrganizacion, fechaConsumoValido, fechaDesdePeriodoEstadistico, fechaCorte, categoriaProducto, subcategoriaProducto, producto, bodega, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 412:553 */     for (Object[] objects = listaStockEnTransitoImportacion.iterator(); objects.hasNext();)
/* 413:    */     {
/* 414:553 */       objects = (Object[])objects.next();
/* 415:554 */       Object[] obj = (Object[])mapProductos.get(objects[0]);
/* 416:555 */       if (obj != null)
/* 417:    */       {
/* 418:556 */         obj[9] = objects[5];
/* 419:557 */         mapProductos.put((Integer)objects[0], obj);
/* 420:    */       }
/* 421:    */       else
/* 422:    */       {
/* 423:559 */         obj = crearDetalleReporte(objects, periodoEstadistico);
/* 424:560 */         obj[9] = objects[5];
/* 425:561 */         mapProductos.put((Integer)objects[0], obj);
/* 426:    */       }
/* 427:    */     }
/* 428:567 */     List<Object[]> listaRecepcionProveedor = this.reporteCompraDao.getRecepcionProveedor(idOrganizacion, fechaDesdePeriodoEstadistico, fechaCorte, categoriaProducto, subcategoriaProducto, producto, bodega, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 429:569 */     for (Object[] objects = listaRecepcionProveedor.iterator(); objects.hasNext();)
/* 430:    */     {
/* 431:569 */       objects = (Object[])objects.next();
/* 432:570 */       Object[] obj = (Object[])mapProductos.get(objects[0]);
/* 433:571 */       if (obj != null)
/* 434:    */       {
/* 435:572 */         obj[13] = objects[5];
/* 436:573 */         mapProductos.put((Integer)objects[0], obj);
/* 437:    */       }
/* 438:    */       else
/* 439:    */       {
/* 440:575 */         obj = crearDetalleReporte(objects, periodoEstadistico);
/* 441:576 */         obj[13] = objects[5];
/* 442:577 */         mapProductos.put((Integer)objects[0], obj);
/* 443:    */       }
/* 444:    */     }
/* 445:583 */     List<Object[]> listaDevolucionCliente = this.reporteCompraDao.getDevolucionCliente(idOrganizacion, fechaDesdePeriodoEstadistico, fechaCorte, categoriaProducto, subcategoriaProducto, producto, bodega, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 446:585 */     for (Object[] objects = listaDevolucionCliente.iterator(); objects.hasNext();)
/* 447:    */     {
/* 448:585 */       objects = (Object[])objects.next();
/* 449:586 */       Object[] obj = (Object[])mapProductos.get(objects[0]);
/* 450:587 */       if (obj != null)
/* 451:    */       {
/* 452:588 */         obj[13] = ((BigDecimal)obj[13]).add((BigDecimal)objects[5]);
/* 453:589 */         mapProductos.put((Integer)objects[0], obj);
/* 454:    */       }
/* 455:    */       else
/* 456:    */       {
/* 457:591 */         obj = crearDetalleReporte(objects, periodoEstadistico);
/* 458:592 */         obj[13] = objects[5];
/* 459:593 */         mapProductos.put((Integer)objects[0], obj);
/* 460:    */       }
/* 461:    */     }
/* 462:599 */     List<Object[]> listaConsumosBodega = this.reporteCompraDao.getConsumosBodega(idOrganizacion, fechaDesdePeriodoEstadistico, fechaCorte, categoriaProducto, subcategoriaProducto, producto, bodega, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 463:601 */     for (Object[] objects = listaConsumosBodega.iterator(); objects.hasNext();)
/* 464:    */     {
/* 465:601 */       objects = (Object[])objects.next();
/* 466:602 */       Object[] obj = (Object[])mapProductos.get(objects[0]);
/* 467:603 */       if (obj != null)
/* 468:    */       {
/* 469:604 */         obj[14] = objects[5];
/* 470:605 */         mapProductos.put((Integer)objects[0], obj);
/* 471:    */       }
/* 472:    */       else
/* 473:    */       {
/* 474:607 */         obj = crearDetalleReporte(objects, periodoEstadistico);
/* 475:608 */         obj[14] = objects[5];
/* 476:609 */         mapProductos.put((Integer)objects[0], obj);
/* 477:    */       }
/* 478:    */     }
/* 479:615 */     List<Object[]> listaDespachoCliente = this.reporteCompraDao.getDespachoCliente(idOrganizacion, fechaDesdePeriodoEstadistico, fechaCorte, categoriaProducto, subcategoriaProducto, producto, bodega, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 480:617 */     for (Object[] objects = listaDespachoCliente.iterator(); objects.hasNext();)
/* 481:    */     {
/* 482:617 */       objects = (Object[])objects.next();
/* 483:618 */       Object[] obj = (Object[])mapProductos.get(objects[0]);
/* 484:619 */       if (obj != null)
/* 485:    */       {
/* 486:620 */         obj[14] = ((BigDecimal)obj[14]).add((BigDecimal)objects[5]);
/* 487:621 */         mapProductos.put((Integer)objects[0], obj);
/* 488:    */       }
/* 489:    */       else
/* 490:    */       {
/* 491:623 */         obj = crearDetalleReporte(objects, periodoEstadistico);
/* 492:624 */         obj[14] = objects[5];
/* 493:625 */         mapProductos.put((Integer)objects[0], obj);
/* 494:    */       }
/* 495:    */     }
/* 496:    */     Object[] objects;
/* 497:631 */     List<Object[]> listaDevolucionProveedor = this.reporteCompraDao.getDevolucionProveedor(idOrganizacion, fechaDesdePeriodoEstadistico, fechaCorte, categoriaProducto, subcategoriaProducto, producto, bodega, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 498:633 */     for (Object[] objects : listaDevolucionProveedor)
/* 499:    */     {
/* 500:634 */       obj = (Object[])mapProductos.get(objects[0]);
/* 501:635 */       if (obj != null)
/* 502:    */       {
/* 503:636 */         obj[14] = ((BigDecimal)obj[14]).add((BigDecimal)objects[5]);
/* 504:637 */         mapProductos.put((Integer)objects[0], obj);
/* 505:    */       }
/* 506:    */       else
/* 507:    */       {
/* 508:639 */         obj = crearDetalleReporte(objects, periodoEstadistico);
/* 509:640 */         obj[14] = objects[5];
/* 510:641 */         mapProductos.put((Integer)objects[0], obj);
/* 511:    */       }
/* 512:    */     }
/* 513:647 */     List<Object[]> listaFechaMinimaMovimientos = this.reporteCompraDao.getFechaMinimaMovimientos(idOrganizacion, fechaDesdePeriodoEstadistico, fechaCorte, categoriaProducto, subcategoriaProducto, producto, bodega, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 514:650 */     for (Object[] obj = listaFechaMinimaMovimientos.iterator(); obj.hasNext();)
/* 515:    */     {
/* 516:650 */       objects = (Object[])obj.next();
/* 517:651 */       Object[] obj = (Object[])mapProductos.get(objects[0]);
/* 518:652 */       if (obj != null)
/* 519:    */       {
/* 520:653 */         int meses = FuncionesUtiles.cantidadMeses((Date)objects[1], fechaCorte);
/* 521:654 */         if (meses > 12) {
/* 522:655 */           meses = 12;
/* 523:    */         }
/* 524:657 */         obj[15] = Integer.valueOf(meses);
/* 525:658 */         if (FuncionesUtiles.getMes((Date)objects[1]) != FuncionesUtiles.getMes(fechaDesdePeriodoEstadistico))
/* 526:    */         {
/* 527:659 */           periodoEstadistico = FuncionesUtiles.dateToString((Date)objects[1]) + " - " + FuncionesUtiles.dateToString(fechaCorte);
/* 528:660 */           obj[16] = periodoEstadistico;
/* 529:    */         }
/* 530:662 */         mapProductos.put((Integer)objects[0], obj);
/* 531:    */       }
/* 532:    */     }
/* 533:    */     Object[] objects;
/* 534:667 */     for (Object[] objects : mapProductos.values())
/* 535:    */     {
/* 536:668 */       BigDecimal mes = new BigDecimal(objects[15].toString());
/* 537:669 */       if (BigDecimal.ZERO.compareTo(mes) != 0) {
/* 538:670 */         objects[6] = new BigDecimal(objects[6].toString()).divide(mes, 2, RoundingMode.HALF_UP);
/* 539:    */       }
/* 540:673 */       objects[7] = this.servicioProducto.getSaldo(((Integer)objects[0]).intValue(), fechaCorte);
/* 541:    */       
/* 542:    */ 
/* 543:    */ 
/* 544:677 */       BigDecimal cantidadAComprar = new BigDecimal(objects[6].toString()).add(new BigDecimal(objects[5].toString())).subtract(new BigDecimal(objects[7].toString())).subtract(new BigDecimal(objects[8].toString())).subtract(new BigDecimal(objects[9].toString()));
/* 545:678 */       objects[10] = (cantidadAComprar.compareTo(BigDecimal.ZERO) > 0 ? cantidadAComprar : BigDecimal.ZERO);
/* 546:    */       
/* 547:680 */       BigDecimal costoTotal = this.servicioProducto.getCosto(((Integer)objects[0]).intValue(), fechaCorte, null);
/* 548:681 */       if (BigDecimal.ZERO.compareTo((BigDecimal)objects[7]) != 0) {
/* 549:682 */         objects[11] = costoTotal.divide((BigDecimal)objects[7], 2, RoundingMode.HALF_UP);
/* 550:    */       }
/* 551:684 */       objects[12] = costoTotal.multiply((BigDecimal)objects[10]);
/* 552:    */       
/* 553:686 */       objects[5] = new BigDecimal(objects[5].toString());
/* 554:687 */       objects[6] = new BigDecimal(objects[6].toString());
/* 555:688 */       objects[7] = new BigDecimal(objects[7].toString());
/* 556:689 */       objects[8] = new BigDecimal(objects[8].toString());
/* 557:690 */       objects[9] = new BigDecimal(objects[9].toString());
/* 558:691 */       objects[10] = new BigDecimal(objects[10].toString());
/* 559:692 */       objects[11] = new BigDecimal(objects[11].toString());
/* 560:693 */       objects[12] = new BigDecimal(objects[12].toString());
/* 561:694 */       objects[13] = new BigDecimal(objects[13].toString());
/* 562:695 */       objects[14] = new BigDecimal(objects[14].toString());
/* 563:    */       
/* 564:697 */       listResult.add(objects);
/* 565:    */     }
/* 566:699 */     Collections.sort(listResult, new Comparator()
/* 567:    */     {
/* 568:    */       public int compare(Object[] o1, Object[] o2)
/* 569:    */       {
/* 570:703 */         return ((String)o1[3] + "_" + (String)o1[4] + "_" + (String)o1[1] + "_" + (String)o1[2]).compareTo((String)o2[3] + "_" + (String)o2[4] + "_" + (String)o2[1] + "_" + (String)o2[2]);
/* 571:    */       }
/* 572:706 */     });
/* 573:707 */     return listResult;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public ReporteFacturaProveedor getReporteFacturaProveedorAsientos(int idFacturaProveedor)
/* 577:    */   {
/* 578:714 */     ReporteFacturaProveedor reporteFacturaProveedor = this.reporteCompraDao.getReporteFacturaProveedorAsiento(idFacturaProveedor);
/* 579:715 */     reporteFacturaProveedor.setListaReporteAsientoNotaCredito(new ArrayList());
/* 580:716 */     reporteFacturaProveedor.setListaReporteAsientoPago(new ArrayList());
/* 581:717 */     reporteFacturaProveedor.setListaReporteAsientoLiquidacionAnticipo(new ArrayList());
/* 582:718 */     reporteFacturaProveedor.setListaReporteAsientoAnticipo(new ArrayList());
/* 583:721 */     if (reporteFacturaProveedor.getIdAsientoFacturaProveedor() != null) {
/* 584:722 */       reporteFacturaProveedor.setListaReporteAsientoFacturaProveedor(this.reporteCompraDao.getReporteAsiento(reporteFacturaProveedor.getIdAsientoFacturaProveedor().intValue()));
/* 585:    */     }
/* 586:724 */     if (reporteFacturaProveedor.getIdAsientoRetencion() != null) {
/* 587:725 */       reporteFacturaProveedor.setListaReporteAsientoRetencion(this.reporteCompraDao.getReporteAsiento(reporteFacturaProveedor.getIdAsientoRetencion().intValue()));
/* 588:    */     }
/* 589:727 */     if (reporteFacturaProveedor.getIdAsientoRecepcion() != null) {
/* 590:728 */       reporteFacturaProveedor.setListaReporteAsientoRecepcion(this.reporteCompraDao.getReporteAsiento(reporteFacturaProveedor.getIdAsientoRecepcion().intValue()));
/* 591:    */     }
/* 592:732 */     HashMap<String, String> filters = new HashMap();
/* 593:733 */     filters.put("facturaProveedorPadre.idFacturaProveedor", "" + idFacturaProveedor);
/* 594:734 */     filters.put("documento.documentoBase", "" + DocumentoBase.NOTA_CREDITO_PROVEEDOR);
/* 595:735 */     filters.put("estado", "!=" + Estado.ANULADO);
/* 596:736 */     List<FacturaProveedor> lista = this.facturaProveedorDao.obtenerListaCombo("numero", true, filters);
/* 597:737 */     for (FacturaProveedor facturaProveedor : lista) {
/* 598:738 */       if (facturaProveedor.getAsiento() != null) {
/* 599:739 */         reporteFacturaProveedor.getListaReporteAsientoNotaCredito().addAll(this.reporteCompraDao.getReporteAsiento(facturaProveedor.getAsiento().getId()));
/* 600:    */       }
/* 601:    */     }
/* 602:744 */     Object listaCampos = new ArrayList();
/* 603:745 */     ((List)listaCampos).add("pago");
/* 604:746 */     ((List)listaCampos).add("pago.asiento");
/* 605:    */     
/* 606:748 */     filters = new HashMap();
/* 607:749 */     filters.put("cuentaPorPagar.facturaProveedor.idFacturaProveedor", "" + idFacturaProveedor);
/* 608:750 */     filters.put("pago.estado", "!=" + Estado.ANULADO);
/* 609:751 */     List<DetallePago> listaDetallePagos = this.detallePagoDao.obtenerListaPorPagina(DetallePago.class, 0, 100, "pago.numero", true, filters, (List)listaCampos);
/* 610:    */     
/* 611:753 */     Set<Integer> keys = new HashSet();
/* 612:754 */     for (Iterator localIterator2 = listaDetallePagos.iterator(); localIterator2.hasNext();)
/* 613:    */     {
/* 614:754 */       detallePago = (DetallePago)localIterator2.next();
/* 615:755 */       if ((!keys.contains(Integer.valueOf(detallePago.getPago().getId()))) && (detallePago.getPago().getAsiento() != null))
/* 616:    */       {
/* 617:756 */         reporteFacturaProveedor.getListaReporteAsientoPago().addAll(this.reporteCompraDao.getReporteAsiento(detallePago.getPago().getAsiento().getId()));
/* 618:757 */         keys.add(Integer.valueOf(detallePago.getPago().getId()));
/* 619:    */       }
/* 620:    */     }
/* 621:    */     DetallePago detallePago;
/* 622:762 */     listaCampos = new ArrayList();
/* 623:763 */     ((List)listaCampos).add("liquidacionAnticipoProveedor");
/* 624:764 */     ((List)listaCampos).add("liquidacionAnticipoProveedor.asiento");
/* 625:765 */     ((List)listaCampos).add("liquidacionAnticipoProveedor.anticipoProveedor");
/* 626:766 */     ((List)listaCampos).add("liquidacionAnticipoProveedor.anticipoProveedor.asiento");
/* 627:    */     
/* 628:768 */     filters = new HashMap();
/* 629:769 */     filters.put("cuentaPorPagar.facturaProveedor.idFacturaProveedor", "" + idFacturaProveedor);
/* 630:770 */     filters.put("liquidacionAnticipoProveedor.estado", "!=" + Estado.ANULADO);
/* 631:    */     
/* 632:772 */     Object listaDetalleLiquidacionAnticipoProveedor = this.detalleLiquidacionAnticipoProveedorDao.obtenerListaPorPagina(DetalleLiquidacionAnticipoProveedor.class, 0, 100, "liquidacionAnticipoProveedor.numero", true, filters, (List)listaCampos);
/* 633:    */     
/* 634:774 */     keys = new HashSet();
/* 635:775 */     for (DetalleLiquidacionAnticipoProveedor detalleLiquidacionAnticipoProveedor : (List)listaDetalleLiquidacionAnticipoProveedor) {
/* 636:776 */       if ((!keys.contains(Integer.valueOf(detalleLiquidacionAnticipoProveedor.getLiquidacionAnticipoProveedor().getId()))) && (detalleLiquidacionAnticipoProveedor.getLiquidacionAnticipoProveedor().getAsiento() != null))
/* 637:    */       {
/* 638:777 */         reporteFacturaProveedor.getListaReporteAsientoLiquidacionAnticipo().addAll(this.reporteCompraDao.getReporteAsiento(detalleLiquidacionAnticipoProveedor.getLiquidacionAnticipoProveedor().getAsiento().getId()));
/* 639:778 */         keys.add(Integer.valueOf(detalleLiquidacionAnticipoProveedor.getLiquidacionAnticipoProveedor().getId()));
/* 640:779 */         AnticipoProveedor anticipoProveedor = detalleLiquidacionAnticipoProveedor.getLiquidacionAnticipoProveedor().getAnticipoProveedor();
/* 641:781 */         if ((anticipoProveedor.getNotaCreditoProveedor() == null) && (anticipoProveedor.getAsiento() != null)) {
/* 642:783 */           reporteFacturaProveedor.getListaReporteAsientoAnticipo().addAll(this.reporteCompraDao.getReporteAsiento(anticipoProveedor.getAsiento().getId()));
/* 643:    */         }
/* 644:    */       }
/* 645:    */     }
/* 646:788 */     return reporteFacturaProveedor;
/* 647:    */   }
/* 648:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.servicio.impl.ServicioReporteCompraImpl
 * JD-Core Version:    0.7.0.1
 */