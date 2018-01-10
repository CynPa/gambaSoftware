/*   1:    */ package com.asinfo.as2.ventas.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.EmpresaDao;
/*   4:    */ import com.asinfo.as2.dao.TipoContactoDao;
/*   5:    */ import com.asinfo.as2.dao.reportes.financiero.cobros.ReporteAnticipoClienteDao;
/*   6:    */ import com.asinfo.as2.dao.reportes.ventas.AnalisisVencimientosClienteDao;
/*   7:    */ import com.asinfo.as2.dao.reportes.ventas.ReportePedidoOrdenFabricacionDao;
/*   8:    */ import com.asinfo.as2.dao.reportes.ventas.ReportePedidoSaldoPorDespacharDao;
/*   9:    */ import com.asinfo.as2.dao.reportes.ventas.ReporteVentaDao;
/*  10:    */ import com.asinfo.as2.entities.Atributo;
/*  11:    */ import com.asinfo.as2.entities.Canal;
/*  12:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  13:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  14:    */ import com.asinfo.as2.entities.Cobro;
/*  15:    */ import com.asinfo.as2.entities.DetalleCobro;
/*  16:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  17:    */ import com.asinfo.as2.entities.DimensionContable;
/*  18:    */ import com.asinfo.as2.entities.Empresa;
/*  19:    */ import com.asinfo.as2.entities.Organizacion;
/*  20:    */ import com.asinfo.as2.entities.Producto;
/*  21:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  22:    */ import com.asinfo.as2.entities.Recaudador;
/*  23:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  24:    */ import com.asinfo.as2.entities.Subempresa;
/*  25:    */ import com.asinfo.as2.entities.Sucursal;
/*  26:    */ import com.asinfo.as2.entities.TipoContacto;
/*  27:    */ import com.asinfo.as2.entities.Zona;
/*  28:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  29:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  30:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  31:    */ import com.asinfo.as2.enumeraciones.OrdenamientoEnum;
/*  32:    */ import com.asinfo.as2.enumeraciones.TipoVentaEnum;
/*  33:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  34:    */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*  35:    */ import com.asinfo.as2.util.AppUtil;
/*  36:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  37:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  38:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  39:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVentaRemote;
/*  40:    */ import java.io.IOException;
/*  41:    */ import java.math.BigDecimal;
/*  42:    */ import java.text.SimpleDateFormat;
/*  43:    */ import java.util.ArrayList;
/*  44:    */ import java.util.Collections;
/*  45:    */ import java.util.Comparator;
/*  46:    */ import java.util.Date;
/*  47:    */ import java.util.HashMap;
/*  48:    */ import java.util.LinkedHashMap;
/*  49:    */ import java.util.List;
/*  50:    */ import javax.ejb.EJB;
/*  51:    */ import javax.ejb.Stateless;
/*  52:    */ import javax.mail.MessagingException;
/*  53:    */ 
/*  54:    */ @Stateless
/*  55:    */ public class ServicioReporteVentaImpl
/*  56:    */   implements ServicioReporteVenta, ServicioReporteVentaRemote
/*  57:    */ {
/*  58:    */   @EJB
/*  59:    */   private AnalisisVencimientosClienteDao analisisVencimientosClienteDao;
/*  60:    */   @EJB
/*  61:    */   private ReporteVentaDao reporteVentaDao;
/*  62:    */   @EJB
/*  63:    */   private ReportePedidoSaldoPorDespacharDao reportePedidoSaldoPorDespacharDao;
/*  64:    */   @EJB
/*  65:    */   private ReporteAnticipoClienteDao reporteAnticipoClienteDao;
/*  66:    */   @EJB
/*  67:    */   private ReportePedidoOrdenFabricacionDao reportePedidoOrdenFabricacionDao;
/*  68:    */   @EJB
/*  69:    */   private EmpresaDao empresaDao;
/*  70:    */   @EJB
/*  71:    */   private TipoContactoDao tipoContactoDao;
/*  72:    */   @EJB
/*  73:    */   private ServicioEnvioEmail servicioEnvioEmail;
/*  74:    */   
/*  75:    */   public List getListaReporteEstadoCuenta(Date fechaDesde, Date fechaHasta, Empresa cliente, Recaudador recaudador, Subempresa subempresa, int idOrganizacion, OrdenamientoEnum orden, boolean saldoDiferenteDeCero, Sucursal sucursal, PuntoDeVenta puntoDeVenta)
/*  76:    */     throws ExcepcionAS2Ventas
/*  77:    */   {
/*  78:102 */     return this.reporteVentaDao.getListaReporteEstadoCuenta(fechaDesde, fechaHasta, cliente, recaudador, subempresa, idOrganizacion, orden, saldoDiferenteDeCero, sucursal, puntoDeVenta);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public List getListaReporteEstadoCuenta(Date fechaDesde, Date fechaHasta, Empresa cliente, Recaudador recaudador, Subempresa subempresa, int idOrganizacion)
/*  82:    */     throws ExcepcionAS2Ventas
/*  83:    */   {
/*  84:116 */     return this.reporteVentaDao.getListaReporteEstadoCuenta(fechaDesde, fechaHasta, cliente, recaudador, subempresa, idOrganizacion, OrdenamientoEnum.FECHA, false, null, null);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List getListaReporteCorteFecha(Date fechaHasta, Empresa cliente, Recaudador recaudador, int idOrganizacion, Subempresa subempresa, EntidadUsuario agenteComercial, Sucursal sucursal, PuntoDeVenta puntoDeVenta, Zona zona, DimensionContable proyecto, CategoriaEmpresa categoriaEmpresa)
/*  88:    */     throws ExcepcionAS2
/*  89:    */   {
/*  90:140 */     List lista = this.reporteVentaDao.getListaReporteCorteFecha(fechaHasta, cliente, recaudador, idOrganizacion, subempresa, agenteComercial, sucursal, puntoDeVenta, zona, proyecto, categoriaEmpresa);
/*  91:143 */     if (lista.size() == 0) {
/*  92:144 */       throw new ExcepcionAS2("msg_no_hay_datos");
/*  93:    */     }
/*  94:146 */     return lista;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List getListaReporteCorteFecha(Date fechaHasta, Empresa cliente, Recaudador recaudador, int idOrganizacion, Subempresa subempresa)
/*  98:    */     throws ExcepcionAS2
/*  99:    */   {
/* 100:159 */     return getListaReporteCorteFecha(fechaHasta, cliente, recaudador, idOrganizacion, subempresa, null, null, null, null, null, null);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List getAnalisisVencimientoCliente(Date fechaHasta, Empresa empresa, Recaudador recaudador, int idOrganizacion, Subempresa subempresa, EntidadUsuario agenteComercial, Sucursal sucursal, PuntoDeVenta puntoDeVenta, Zona zona, int numeroDia)
/* 104:    */     throws ExcepcionAS2Ventas
/* 105:    */   {
/* 106:171 */     return this.analisisVencimientosClienteDao.getAnalisisVencimientoCliente(fechaHasta, empresa, recaudador, idOrganizacion, subempresa, agenteComercial, sucursal, puntoDeVenta, zona, numeroDia);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List getAnalisisDinarpad(Date fechaHasta, Date fechaDesde, int idEmpresa, int idRecaudador, int idOrganizacion, int idSubempresa, int idAgenteComercial, Sucursal sucursal, int diasMinimo, BigDecimal montoMinimo)
/* 110:    */     throws ExcepcionAS2Ventas
/* 111:    */   {
/* 112:179 */     return this.analisisVencimientosClienteDao.getAnalisisDinarpad(fechaHasta, fechaDesde, idEmpresa, idRecaudador, idOrganizacion, idSubempresa, idAgenteComercial, sucursal, diasMinimo, montoMinimo);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public List getAnalisisVencimientoCliente(Empresa empresa, Recaudador recaudador, int idOrganizacion, Subempresa subempresa)
/* 116:    */     throws ExcepcionAS2Ventas
/* 117:    */   {
/* 118:192 */     return getAnalisisVencimientoCliente(new Date(), empresa, recaudador, idOrganizacion, subempresa, null, null, null, null, 0);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List getListaReporteFacturacionResumido(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente)
/* 122:    */     throws ExcepcionAS2Ventas
/* 123:    */   {
/* 124:205 */     return this.reporteVentaDao.getListaReporteFacturacionResumido(fechaDesde, fechaHasta, numeroDesde, numeroHasta, idCliente);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List getListaReporteFacturacionResumido(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, Sucursal sucursal, PuntoDeVenta puntoVenta, TipoVentaEnum tipoVenta, boolean saldoInicial, boolean indicadorTipoReporte, int idOrganizacion, DocumentoBase documentoBase, int idMotivoNotaCreditoCliente, DimensionContable proyecto)
/* 128:    */   {
/* 129:221 */     return this.reporteVentaDao.getListaReporteFacturacionResumido(fechaDesde, fechaHasta, numeroDesde, numeroHasta, idCliente, idVendedor, anuladas, idCanal, idZona, sucursal, puntoVenta, tipoVenta, saldoInicial, indicadorTipoReporte, idOrganizacion, documentoBase, idMotivoNotaCreditoCliente, proyecto);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List getListaReporteFacturacionDetallado(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, Sucursal sucursal, PuntoDeVenta puntoVenta, TipoVentaEnum tipoVenta, boolean saldoInicial, boolean indicadorTipoReporte, int idOrganizacion, DocumentoBase documentoBase, int idMotivoNotaCreditoCliente, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Atributo atributo, String valorAtributo, DimensionContable proyecto)
/* 133:    */   {
/* 134:233 */     return this.reporteVentaDao.getListaReporteFacturacionDetallado(fechaDesde, fechaHasta, numeroDesde, numeroHasta, idCliente, idVendedor, anuladas, idCanal, idZona, sucursal, puntoVenta, tipoVenta, saldoInicial, indicadorTipoReporte, idOrganizacion, documentoBase, idMotivoNotaCreditoCliente, categoriaProducto, subcategoriaProducto, producto, atributo, valorAtributo, proyecto);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List getListaReporteVentaProductoDetallado(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, Sucursal sucursal, PuntoDeVenta puntoVenta, TipoVentaEnum tipoVenta, boolean saldoInicial, int idOrganizacion, DocumentoBase documentoBase, int idMotivoNotaCreditoCliente, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Atributo atributo, String valorAtributo, DimensionContable proyecto)
/* 138:    */   {
/* 139:251 */     return this.reporteVentaDao.getListaReporteVentaProductoDetallado(fechaDesde, fechaHasta, numeroDesde, numeroHasta, idCliente, idVendedor, anuladas, idCanal, idZona, sucursal, puntoVenta, tipoVenta, saldoInicial, idOrganizacion, documentoBase, idMotivoNotaCreditoCliente, categoriaProducto, subcategoriaProducto, producto, atributo, valorAtributo, proyecto);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public List getReportePedidoSaldoPorDespachar(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 143:    */     throws ExcepcionAS2
/* 144:    */   {
/* 145:266 */     List<Object[]> listaResult = new ArrayList();
/* 146:267 */     List<Object[]> listaGeneral = new ArrayList();
/* 147:    */     
/* 148:    */ 
/* 149:270 */     List<Object[]> listaDatosReporte = this.reportePedidoSaldoPorDespacharDao.getReportePedidoSaldoPorDespachar(fechaDesde, fechaHasta, idEmpresa, soloPendientes, categoriaProducto, subcategoriaProducto, idOrganizacion);
/* 150:    */     
/* 151:    */ 
/* 152:273 */     LinkedHashMap<String, Object[]> hmDatosDespachos = this.reportePedidoSaldoPorDespacharDao.getReporteDespachos(fechaDesde, fechaHasta, idEmpresa, soloPendientes, categoriaProducto, subcategoriaProducto, idOrganizacion);
/* 153:    */     
/* 154:    */ 
/* 155:276 */     LinkedHashMap<String, Object[]> hmDatosFacturas = this.reportePedidoSaldoPorDespacharDao.getReporteFacturas(fechaDesde, fechaHasta, idEmpresa, soloPendientes, categoriaProducto, subcategoriaProducto, idOrganizacion);
/* 156:    */     
/* 157:    */ 
/* 158:279 */     LinkedHashMap<String, Object[]> hmDatosFacturaDespacho = this.reportePedidoSaldoPorDespacharDao.getReporteFacturaDespachos(fechaDesde, fechaHasta, idEmpresa, soloPendientes, categoriaProducto, subcategoriaProducto, idOrganizacion);
/* 159:282 */     for (Object[] objects : listaDatosReporte)
/* 160:    */     {
/* 161:284 */       Object[] despachos = (Object[])hmDatosDespachos.get(objects[0] + "~" + objects[2]);
/* 162:285 */       Object[] facturas = (Object[])hmDatosFacturas.get(objects[0] + "~" + objects[2]);
/* 163:286 */       Object[] facturaDespacho = (Object[])hmDatosFacturaDespacho.get(objects[0] + "~" + objects[2]);
/* 164:288 */       if (despachos != null) {
/* 165:289 */         objects[8] = despachos[4];
/* 166:    */       }
/* 167:291 */       if (facturaDespacho != null) {
/* 168:292 */         objects[8] = facturaDespacho[4];
/* 169:    */       }
/* 170:294 */       if (facturas != null) {
/* 171:295 */         objects[7] = facturas[4];
/* 172:    */       }
/* 173:297 */       listaResult.add(objects);
/* 174:    */     }
/* 175:300 */     for (Object[] object : listaResult) {
/* 176:301 */       if (soloPendientes)
/* 177:    */       {
/* 178:302 */         Estado estado = (Estado)object[10];
/* 179:303 */         BigDecimal valorPedido = (BigDecimal)object[6];
/* 180:304 */         BigDecimal valorFacturado = (BigDecimal)object[7];
/* 181:305 */         BigDecimal valorDespachado = (BigDecimal)object[8];
/* 182:306 */         if ((!estado.equals(Estado.CERRADO)) && ((valorPedido.compareTo(valorFacturado) != 0) || (valorPedido.compareTo(valorDespachado) != 0))) {
/* 183:307 */           listaGeneral.add(object);
/* 184:    */         }
/* 185:    */       }
/* 186:    */       else
/* 187:    */       {
/* 188:310 */         listaGeneral = listaResult;
/* 189:    */       }
/* 190:    */     }
/* 191:313 */     return listaGeneral;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List getListaReporteVentasMargenDescuento(Date fechaDesde, Date fechaHasta, int idCliente, BigDecimal porcentaje, int idOrganizacion)
/* 195:    */     throws ExcepcionAS2
/* 196:    */   {
/* 197:327 */     return this.reporteVentaDao.getListaReporteVentasMargenDescuento(fechaDesde, fechaHasta, idCliente, porcentaje, idOrganizacion);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public List getReportePedidoSaldoPorDescacharGenerico(Date fechaDesde, Date fechaHasta, int idEmpresa)
/* 201:    */   {
/* 202:339 */     return this.reportePedidoSaldoPorDespacharDao.getReportePedidoSaldoPorDescacharGenerico(fechaDesde, fechaHasta, idEmpresa);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public BigDecimal obtenerSaldoEstadoCuenta(int idCliente, Date fechaHasta, boolean indicadorFechaDocumento)
/* 206:    */   {
/* 207:351 */     return this.reporteVentaDao.obtenerSaldoEstadoCuenta(idCliente, fechaHasta, indicadorFechaDocumento);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List getReporteNotaDebito(int idNotaDebito)
/* 211:    */   {
/* 212:357 */     return this.reporteVentaDao.getReporteNotaDebito(idNotaDebito);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List getVencimientos(int idEmpresa, Date fecha, int idRecaudador, int idOrganizacion, int idSubempresa, int idAgenteComercial, Sucursal sucursal, PuntoDeVenta puntoDeVenta, Zona zona, CategoriaEmpresa categoriaEmpresa, boolean indicadorMostrarPosfechados)
/* 216:    */     throws ExcepcionAS2
/* 217:    */   {
/* 218:366 */     List lista = this.analisisVencimientosClienteDao.getVencimientos(idEmpresa, fecha, idRecaudador, idOrganizacion, idSubempresa, idAgenteComercial, sucursal, puntoDeVenta, zona, categoriaEmpresa, indicadorMostrarPosfechados);
/* 219:369 */     if (lista.size() == 0) {
/* 220:370 */       throw new ExcepcionAS2("msg_no_hay_datos");
/* 221:    */     }
/* 222:372 */     return lista;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List getVencimientos(int idEmpresa, Date fecha, int idRecaudador, int idOrganizacion, int idSubempresa)
/* 226:    */     throws ExcepcionAS2
/* 227:    */   {
/* 228:384 */     return getVencimientos(idEmpresa, fecha, idRecaudador, idOrganizacion, idSubempresa, 0, null, null, null, null, true);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public List getReporteFacturaCliente(int idFacturaCliente)
/* 232:    */     throws ExcepcionAS2
/* 233:    */   {
/* 234:395 */     return this.reporteVentaDao.getReporteFacturaCliente(idFacturaCliente);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public List getReporteVentasProducto(Date fechaDesde, Date fechaHasta, int idProducto)
/* 238:    */   {
/* 239:406 */     return this.reporteVentaDao.getReporteVentasProducto(fechaDesde, fechaHasta, idProducto);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public List getReporteVentasCaja(Date fechaDesde, Date fechaHasta, int idAgenteComercial, int idFormaPago, int idPuntoVenta, int idCaja)
/* 243:    */   {
/* 244:417 */     return this.reporteVentaDao.getReporteVentasCaja(fechaDesde, fechaHasta, idAgenteComercial, idFormaPago, idPuntoVenta, idCaja);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List getReporteCorteFechaAnticipoClientes(Date fechaDesde, Date fechaHasta, int idCliente, boolean indicadorSaldoDiferenciaCero, int idOrganizacion, Sucursal sucursal, boolean indicadorIncluirNotasCreditos)
/* 248:    */     throws ExcepcionAS2
/* 249:    */   {
/* 250:431 */     return this.reporteAnticipoClienteDao.getAnticipoCliente(fechaDesde, fechaHasta, idCliente, indicadorSaldoDiferenciaCero, idOrganizacion, sucursal, indicadorIncluirNotasCreditos);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List getReporteCorteFechaAnticipoClientes(Date fechaCorte, int idCliente)
/* 254:    */     throws ExcepcionAS2
/* 255:    */   {
/* 256:443 */     return getReporteCorteFechaAnticipoClientes(FuncionesUtiles.obtenerFechaInicial(), fechaCorte, idCliente, false, AppUtil.getOrganizacion()
/* 257:444 */       .getId(), null, true);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public List getReporteCorteFechaAnticipoClientesSinLiquidacion(Date fechaCorte, int idCliente)
/* 261:    */     throws ExcepcionAS2
/* 262:    */   {
/* 263:455 */     return this.reporteVentaDao.getReporteCorteFechaAnticipoClientesSinLiquidacion(fechaCorte, idCliente);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public List geteReporteVentasPorAtributo(Date fechaDesde, Date fechaHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, int idSucursal, int idAtributo, int idOrganizacion)
/* 267:    */   {
/* 268:468 */     return this.reporteVentaDao.geteReporteVentasPorAtributo(fechaDesde, fechaHasta, idCliente, idVendedor, anuladas, idCanal, idZona, idSucursal, idAtributo, idOrganizacion);
/* 269:    */   }
/* 270:    */   
/* 271:    */   public List<Object[]> getListaReporteEstadoCuenta(int idCliente, String numeroFactura)
/* 272:    */   {
/* 273:479 */     return this.reporteVentaDao.getListaReporteEstadoCuenta(idCliente, numeroFactura);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public List<DetalleFormaCobro> getReporteListaRetencionVentas(Date fechaDesde, Date fechaHasta, int idEmpresa, int idOrganizacion, Sucursal sucursal, boolean emiteRetencion, PuntoDeVenta puntoVenta)
/* 277:    */   {
/* 278:486 */     return this.reporteVentaDao.getReporteListaRetencionVentas(fechaDesde, fechaHasta, idEmpresa, idOrganizacion, sucursal, emiteRetencion, puntoVenta);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public List<DetalleCobro> getReporteListaDetalleCobro(List<Cobro> listaCobro)
/* 282:    */   {
/* 283:491 */     return this.reporteVentaDao.getReporteListaDetalleCobro(listaCobro);
/* 284:    */   }
/* 285:    */   
/* 286:    */   public List getListaReporteFacturacionProductoResumido(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, Sucursal sucursal, PuntoDeVenta puntoVenta, TipoVentaEnum tipoVenta, boolean saldoInicial, int idOrganizacion, DocumentoBase documentoBase, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Atributo atributo, String valorAtributo, DimensionContable proyecto)
/* 287:    */   {
/* 288:507 */     return this.reporteVentaDao.getListaReporteFacturacionProductoResumido(fechaDesde, fechaHasta, numeroDesde, numeroHasta, idCliente, idVendedor, anuladas, idCanal, idZona, sucursal, puntoVenta, tipoVenta, saldoInicial, idOrganizacion, documentoBase, categoriaProducto, subcategoriaProducto, producto, atributo, valorAtributo, proyecto);
/* 289:    */   }
/* 290:    */   
/* 291:    */   public List getListaReporteVentasCombustible(Date fechaDesde, Date fechaHasta, int idCliente, boolean anuladas, int idCanal, int idZona, int idSubcategoriaProducto, int idOrganizacion)
/* 292:    */   {
/* 293:524 */     return this.reporteVentaDao.getListaReporteVentasCombustible(fechaDesde, fechaHasta, idCliente, anuladas, idCanal, idZona, idSubcategoriaProducto, idOrganizacion);
/* 294:    */   }
/* 295:    */   
/* 296:    */   public List<Object[]> getReporteAnalisisVentasCliente(Date fechaDesde, Date fechaHasta, boolean saldoInicial, int idOrganizacion, int idSubcategoriaProducto, boolean indicadorCantidad)
/* 297:    */   {
/* 298:537 */     return this.reporteVentaDao.getReporteAnalisisVentasCliente(fechaDesde, fechaHasta, saldoInicial, idOrganizacion, idSubcategoriaProducto, indicadorCantidad);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List getReporteAnalisisVentasProducto(Date fechaDesde, Date fechaHasta, boolean saldoInicial, int idOrganizacion, int idSubcategoriaProducto, boolean indicadorCantidad)
/* 302:    */   {
/* 303:550 */     return this.reporteVentaDao.getReporteAnalisisVentasProducto(fechaDesde, fechaHasta, saldoInicial, idOrganizacion, idSubcategoriaProducto, indicadorCantidad);
/* 304:    */   }
/* 305:    */   
/* 306:    */   public List getReportePedidoOrdenFabricacion(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean indicadorSaldosPendientes, int idOrganizacion)
/* 307:    */     throws ExcepcionAS2
/* 308:    */   {
/* 309:558 */     return this.reportePedidoOrdenFabricacionDao.getReportePedidoOrdenFabricacion(fechaDesde, fechaHasta, idEmpresa, indicadorSaldosPendientes, idOrganizacion);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public List getReporteFacturaDespacho(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 313:    */   {
/* 314:566 */     return this.reportePedidoSaldoPorDespacharDao.getReporteFacturaDespacho(fechaDesde, fechaHasta, idEmpresa, soloPendientes, categoriaProducto, subcategoriaProducto, idOrganizacion);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public List getReporteDespachoFactura(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean soloPendientes, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, int idOrganizacion)
/* 318:    */   {
/* 319:574 */     return this.reportePedidoSaldoPorDespacharDao.getReporteDespachoFactura(fechaDesde, fechaHasta, idEmpresa, soloPendientes, categoriaProducto, subcategoriaProducto, idOrganizacion);
/* 320:    */   }
/* 321:    */   
/* 322:    */   public List getReportePedidoSaldoPorDespachar(Date fechaDesde, Date fechaHasta, int idEmpresa, boolean indicadorSaldosPendientes, int idOrganizacion)
/* 323:    */     throws ExcepcionAS2
/* 324:    */   {
/* 325:581 */     return getReportePedidoSaldoPorDespachar(fechaDesde, fechaHasta, idEmpresa, indicadorSaldosPendientes, null, null, idOrganizacion);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public List getSaldosFactura(Date fechaHasta, int idEmpresaFinal)
/* 329:    */     throws ExcepcionAS2
/* 330:    */   {
/* 331:587 */     return this.reporteVentaDao.getSaldosFactura(fechaHasta, idEmpresaFinal);
/* 332:    */   }
/* 333:    */   
/* 334:    */   public List<Object[]> getReporteResultadoNetoProductosVendidos(Date fechaDesde, Date fechaHasta, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo, String valorAtributo, String tipoReporte, Sucursal sucursal, boolean saldoInicial, CategoriaEmpresa categoriaEmpresa, Empresa empresa)
/* 335:    */   {
/* 336:594 */     List<Object[]> listaTotal = new ArrayList();
/* 337:595 */     HashMap<String, Object[]> hmDatosReporte = new HashMap();
/* 338:    */     
/* 339:    */ 
/* 340:598 */     List<Object[]> listaVentas = this.reporteVentaDao.getVentaProductoVendido(fechaDesde, fechaHasta, idOrganizacion, categoriaProducto, subcategoriaProducto, atributo, valorAtributo, tipoReporte, sucursal, saldoInicial, categoriaEmpresa, empresa);
/* 341:601 */     for (Object[] datoVenta : listaVentas)
/* 342:    */     {
/* 343:602 */       String clave = getClave(tipoReporte, datoVenta);
/* 344:    */       
/* 345:604 */       Object[] datoVentaBus = (Object[])hmDatosReporte.get(clave);
/* 346:606 */       if (datoVentaBus != null)
/* 347:    */       {
/* 348:608 */         datoVenta[24] = ((BigDecimal)datoVentaBus[24]).add((BigDecimal)datoVenta[24]);
/* 349:609 */         datoVenta[25] = ((BigDecimal)datoVentaBus[25]).add((BigDecimal)datoVenta[25]);
/* 350:610 */         datoVenta[26] = ((BigDecimal)datoVentaBus[26]).add((BigDecimal)datoVenta[26]);
/* 351:    */       }
/* 352:613 */       hmDatosReporte.put(clave, datoVenta);
/* 353:    */     }
/* 354:616 */     Object listaDespachos = this.reporteVentaDao.getDespachoProductoVendido(fechaDesde, fechaHasta, idOrganizacion, categoriaProducto, subcategoriaProducto, atributo, valorAtributo, tipoReporte, sucursal, categoriaEmpresa, empresa);
/* 355:    */     
/* 356:618 */     asignarValores((List)listaDespachos, hmDatosReporte, 27, 28, 29, tipoReporte);
/* 357:    */     
/* 358:620 */     List<Object[]> listaNotasCredito = this.reporteVentaDao.getNotasCreditoProductoVendido(fechaDesde, fechaHasta, idOrganizacion, categoriaProducto, subcategoriaProducto, atributo, valorAtributo, tipoReporte, sucursal, categoriaEmpresa, empresa);
/* 359:    */     
/* 360:622 */     asignarValores(listaNotasCredito, hmDatosReporte, 30, 31, 32, tipoReporte);
/* 361:    */     
/* 362:624 */     List<Object[]> listaDevoluciones = this.reporteVentaDao.getDevolucionesProductoVendido(fechaDesde, fechaHasta, idOrganizacion, categoriaProducto, subcategoriaProducto, atributo, valorAtributo, tipoReporte, false, sucursal, categoriaEmpresa, empresa);
/* 363:    */     
/* 364:626 */     asignarValores(listaDevoluciones, hmDatosReporte, 33, 34, 35, tipoReporte);
/* 365:    */     
/* 366:628 */     List<Object[]> listaDevolucionesCostos = this.reporteVentaDao.getDevolucionesProductoVendido(fechaDesde, fechaHasta, idOrganizacion, categoriaProducto, subcategoriaProducto, atributo, valorAtributo, tipoReporte, true, sucursal, categoriaEmpresa, empresa);
/* 367:    */     
/* 368:630 */     asignarValores(listaDevolucionesCostos, hmDatosReporte, 36, 37, 38, tipoReporte);
/* 369:    */     
/* 370:    */ 
/* 371:633 */     List<Object[]> listaNotasDebito = this.reporteVentaDao.getNotasDebitoProductoVendido(fechaDesde, fechaHasta, idOrganizacion, categoriaProducto, subcategoriaProducto, atributo, valorAtributo, tipoReporte, sucursal, categoriaEmpresa, empresa);
/* 372:    */     
/* 373:635 */     asignarValores(listaNotasDebito, hmDatosReporte, 39, 40, 41, tipoReporte);
/* 374:    */     
/* 375:637 */     listaTotal.addAll(hmDatosReporte.values());
/* 376:    */     
/* 377:639 */     ordenarLista(tipoReporte, listaTotal);
/* 378:    */     
/* 379:641 */     return listaTotal;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void asignarValores(List<Object[]> lista, HashMap<String, Object[]> hmDatosReporte, int posicionCantidad, int posicionValorUnitario, int posicionValorTotal, String tipoReporte)
/* 383:    */   {
/* 384:647 */     for (Object[] dato : lista)
/* 385:    */     {
/* 386:648 */       String clave = getClave(tipoReporte, dato);
/* 387:649 */       Object[] datoRecuperado = (Object[])hmDatosReporte.get(clave);
/* 388:651 */       if (datoRecuperado == null)
/* 389:    */       {
/* 390:652 */         datoRecuperado = new Object[42];
/* 391:654 */         for (int i = 0; i <= 23; i++) {
/* 392:655 */           datoRecuperado[i] = dato[i];
/* 393:    */         }
/* 394:658 */         for (int i = 24; i < 41; i++) {
/* 395:659 */           datoRecuperado[i] = BigDecimal.ZERO;
/* 396:    */         }
/* 397:662 */         hmDatosReporte.put(clave, datoRecuperado);
/* 398:    */       }
/* 399:665 */       datoRecuperado[posicionCantidad] = dato[24];
/* 400:666 */       datoRecuperado[posicionValorUnitario] = dato[25];
/* 401:667 */       datoRecuperado[posicionValorTotal] = dato[26];
/* 402:    */     }
/* 403:    */   }
/* 404:    */   
/* 405:    */   public String getClave(String tipoReporte, Object[] datoVenta)
/* 406:    */   {
/* 407:672 */     if (tipoReporte.equals("cliente"))
/* 408:    */     {
/* 409:673 */       String identificacionEmpresa = (String)datoVenta[0];
/* 410:674 */       String codigoCategoriaEmpresa = (String)datoVenta[2];
/* 411:675 */       String codigoSucursal = (String)datoVenta[4];
/* 412:676 */       return identificacionEmpresa + "~" + codigoCategoriaEmpresa + "~" + codigoSucursal;
/* 413:    */     }
/* 414:678 */     if (tipoReporte.equals("producto"))
/* 415:    */     {
/* 416:679 */       String codigoProducto = (String)datoVenta[6];
/* 417:680 */       String codigoBodega = (String)datoVenta[8];
/* 418:681 */       String codigoSucursal = (String)datoVenta[4];
/* 419:682 */       return codigoProducto + "~" + codigoBodega + "~" + codigoSucursal;
/* 420:    */     }
/* 421:685 */     if (tipoReporte.equals("factura"))
/* 422:    */     {
/* 423:686 */       String numeroFactura = (String)datoVenta[10];
/* 424:687 */       String codigoSucursal = (String)datoVenta[4];
/* 425:688 */       return numeroFactura + "~" + codigoSucursal;
/* 426:    */     }
/* 427:691 */     if (tipoReporte.equals("facturaProducto"))
/* 428:    */     {
/* 429:692 */       String numeroFactura = (String)datoVenta[10];
/* 430:693 */       String codigoProducto = (String)datoVenta[6];
/* 431:694 */       String codigoSucursal = (String)datoVenta[4];
/* 432:695 */       return numeroFactura + "~" + codigoProducto + "~" + codigoSucursal;
/* 433:    */     }
/* 434:699 */     return "";
/* 435:    */   }
/* 436:    */   
/* 437:    */   public Object[] recuperarDato(String tipoReporte, Object[] dato, HashMap<String, Object[]> hmDatosReporte)
/* 438:    */   {
/* 439:704 */     Object[] datoRecuperado = null;
/* 440:705 */     if (tipoReporte.equals("cliente"))
/* 441:    */     {
/* 442:706 */       String codigoIdentificacion = (String)dato[0];
/* 443:707 */       String codigoCategoria = (String)dato[2];
/* 444:708 */       String codigoSucursal = (String)dato[4];
/* 445:709 */       datoRecuperado = (Object[])hmDatosReporte.get(codigoIdentificacion + "~" + codigoCategoria + "~" + codigoSucursal);
/* 446:    */     }
/* 447:712 */     if (tipoReporte.equals("producto"))
/* 448:    */     {
/* 449:713 */       String codigoProducto = (String)dato[6];
/* 450:714 */       String codigoBodegaProducto = (String)dato[8];
/* 451:715 */       String codigoSucursal = (String)dato[4];
/* 452:716 */       datoRecuperado = (Object[])hmDatosReporte.get(codigoProducto + "~" + codigoBodegaProducto + "~" + codigoSucursal);
/* 453:    */     }
/* 454:719 */     if (tipoReporte.equals("factura"))
/* 455:    */     {
/* 456:720 */       String numeroFactura = (String)dato[10];
/* 457:721 */       String codigoSucursal = (String)dato[4];
/* 458:722 */       datoRecuperado = (Object[])hmDatosReporte.get(numeroFactura + "~" + codigoSucursal);
/* 459:    */     }
/* 460:724 */     if (tipoReporte.equals("facturaProducto"))
/* 461:    */     {
/* 462:725 */       String numeroFactura = (String)dato[10];
/* 463:726 */       String codigoProducto = (String)dato[6];
/* 464:727 */       String codigoSucursal = (String)dato[4];
/* 465:728 */       datoRecuperado = (Object[])hmDatosReporte.get(numeroFactura + "~" + codigoProducto + "~" + codigoSucursal);
/* 466:    */     }
/* 467:730 */     return datoRecuperado;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void ordenarLista(String tipoReporte, List<Object[]> listaTotal)
/* 471:    */   {
/* 472:734 */     if (tipoReporte.equals("cliente")) {
/* 473:735 */       Collections.sort(listaTotal, new Comparator()
/* 474:    */       {
/* 475:    */         public int compare(Object[] o1, Object[] o2)
/* 476:    */         {
/* 477:738 */           return ((String)o1[4] + (String)o1[2]).compareTo((String)o2[4] + (String)o2[2]);
/* 478:    */         }
/* 479:    */       });
/* 480:    */     }
/* 481:743 */     if (tipoReporte.equals("producto")) {
/* 482:744 */       Collections.sort(listaTotal, new Comparator()
/* 483:    */       {
/* 484:    */         public int compare(Object[] o1, Object[] o2)
/* 485:    */         {
/* 486:748 */           return ((String)o1[4] + (String)o1[8]).compareTo((String)o2[4] + (String)o2[8]);
/* 487:    */         }
/* 488:    */       });
/* 489:    */     }
/* 490:753 */     if (tipoReporte.equals("factura")) {
/* 491:754 */       Collections.sort(listaTotal, new Comparator()
/* 492:    */       {
/* 493:    */         public int compare(Object[] o1, Object[] o2)
/* 494:    */         {
/* 495:757 */           return ((String)o1[4] + (String)o1[10]).compareTo((String)o2[4] + (String)o2[10]);
/* 496:    */         }
/* 497:    */       });
/* 498:    */     }
/* 499:762 */     if (tipoReporte.equals("facturaProducto")) {
/* 500:765 */       Collections.sort(listaTotal, new Comparator()
/* 501:    */       {
/* 502:    */         public int compare(Object[] o1, Object[] o2)
/* 503:    */         {
/* 504:768 */           return ((String)o1[4] + (String)o1[10] + (String)o1[6]).compareTo((String)o2[4] + (String)o2[10] + (String)o2[6]);
/* 505:    */         }
/* 506:    */       });
/* 507:    */     }
/* 508:    */   }
/* 509:    */   
/* 510:    */   public List<Empresa> getEmpresasEnvioEmailVencimiento(int idEmpresa, Date fecha, int idRecaudador, int idOrganizacion, int idSubempresa, int idAgenteComercial, Sucursal sucursal, PuntoDeVenta puntoDeVenta, Zona zona, CategoriaEmpresa categoriaEmpresa)
/* 511:    */   {
/* 512:778 */     return this.analisisVencimientosClienteDao.getEmpresasEnvioEmailVencimiento(idEmpresa, fecha, idRecaudador, idOrganizacion, idSubempresa, idAgenteComercial, sucursal, puntoDeVenta, zona, categoriaEmpresa);
/* 513:    */   }
/* 514:    */   
/* 515:    */   public void enviarEmail(Date fechaCorte, String emails, Empresa empresa, byte[] pdf)
/* 516:    */     throws IOException, MessagingException
/* 517:    */   {
/* 518:784 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 519:    */     
/* 520:786 */     List<TipoContacto> listaTipoContactos = this.empresaDao.getTipoContactosPorTipoNotificacion(empresa, "VencimientoFacturaCliente");
/* 521:787 */     TipoContacto tipoContacto = null;
/* 522:788 */     if (listaTipoContactos.size() > 0) {
/* 523:789 */       tipoContacto = (TipoContacto)listaTipoContactos.get(0);
/* 524:    */     } else {
/* 525:791 */       tipoContacto = this.tipoContactoDao.getTipoContactoPredeterminadoPorTipoNotificacion("VencimientoFacturaCliente", empresa.getIdOrganizacion());
/* 526:    */     }
/* 527:794 */     String bodyText = "";
/* 528:795 */     if (tipoContacto != null)
/* 529:    */     {
/* 530:796 */       bodyText = tipoContacto.getTextoCuerpoCorreoVencimientoFacturaCliente();
/* 531:797 */       bodyText = bodyText.replaceAll(":fechaCorte:", sdf.format(fechaCorte));
/* 532:798 */       bodyText = bodyText.replaceAll(":nombreCliente:", empresa.getNombreFiscal());
/* 533:799 */       bodyText = bodyText.replaceAll(":identificacionCliente:", empresa.getIdentificacion());
/* 534:    */     }
/* 535:802 */     this.servicioEnvioEmail.enviarEmail(empresa.getIdOrganizacion(), emails, "Vencimiento Factura: " + empresa.getNombreFiscal(), bodyText, new ArrayList(), new HashMap(), pdf, "Vencimiento: " + empresa
/* 536:803 */       .getNombreFiscal() + ".pdf", "application/pdf");
/* 537:    */   }
/* 538:    */   
/* 539:    */   public List<Object[]> getReporteComportamientoCobroCliente(int idOrganizacion, Date fechaDesde, Date fechaHasta, CategoriaEmpresa categoriaEmpresa, Empresa empresa)
/* 540:    */   {
/* 541:809 */     return this.reporteVentaDao.getReporteComportamientoCobroCliente(idOrganizacion, fechaDesde, fechaHasta, categoriaEmpresa, empresa);
/* 542:    */   }
/* 543:    */   
/* 544:    */   public List<Object[]> getReporteVencimientoMensual(int idOrganizacion, Empresa empresa, Subempresa subempresa, Date fechaHasta, boolean fechaEmisionFactura)
/* 545:    */   {
/* 546:815 */     return this.reporteVentaDao.getReporteVencimientoMensual(idOrganizacion, empresa, subempresa, fechaHasta, fechaEmisionFactura);
/* 547:    */   }
/* 548:    */   
/* 549:    */   public List<Object[]> getReporteImpuestoVenta(Date fechaDesde, Date fechaHasta, Empresa cliente, CategoriaEmpresa categoriaEmpresa, EntidadUsuario agenteComercial, boolean anuladas, Canal canal, Zona zona, int idOrganizacion, DocumentoBase documentoBase, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, boolean indicadorResumen, boolean agrupadoPorCliente, Sucursal sucursal)
/* 550:    */   {
/* 551:823 */     return this.reporteVentaDao.getReporteImpuestoVenta(fechaDesde, fechaHasta, cliente, categoriaEmpresa, agenteComercial, anuladas, canal, zona, idOrganizacion, documentoBase, categoriaProducto, subcategoriaProducto, producto, indicadorResumen, agrupadoPorCliente, sucursal);
/* 552:    */   }
/* 553:    */   
/* 554:    */   public Object[] getResumenVentaCliente(Date fechaDesde, Date fechaHasta, int idEmpresa)
/* 555:    */   {
/* 556:829 */     return this.reporteVentaDao.getResumenVentaCliente(fechaDesde, fechaHasta, idEmpresa);
/* 557:    */   }
/* 558:    */   
/* 559:    */   public List<Object[]> getResumenProductosMasVendidosCliente(Date fechaDesde, Date fechaHasta, int idEmpresa)
/* 560:    */   {
/* 561:834 */     return this.reporteVentaDao.getResumenProductosMasVendidosCliente(fechaDesde, fechaHasta, idEmpresa);
/* 562:    */   }
/* 563:    */   
/* 564:    */   public List<Object[]> getDetalleVentasProductoCliente(Date fechaDesde, Date fechaHasta, int idEmpresa, int idProducto)
/* 565:    */   {
/* 566:839 */     return this.reporteVentaDao.getDetalleVentasProductoCliente(fechaDesde, fechaHasta, idEmpresa, idProducto);
/* 567:    */   }
/* 568:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.impl.ServicioReporteVentaImpl
 * JD-Core Version:    0.7.0.1
 */