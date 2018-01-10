/*   1:    */ package com.asinfo.as2.financiero.cobros.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteCarteraCobrada;
/*   4:    */ import com.asinfo.as2.clases.ReporteCobros;
/*   5:    */ import com.asinfo.as2.dao.CobroDao;
/*   6:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   7:    */ import com.asinfo.as2.dao.GenericoDao;
/*   8:    */ import com.asinfo.as2.dao.reportes.financiero.cobros.ReporteCobroClienteDao;
/*   9:    */ import com.asinfo.as2.entities.Calificacion;
/*  10:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  11:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  12:    */ import com.asinfo.as2.entities.Empresa;
/*  13:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  14:    */ import com.asinfo.as2.entities.Recaudador;
/*  15:    */ import com.asinfo.as2.entities.Sucursal;
/*  16:    */ import com.asinfo.as2.entities.Zona;
/*  17:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Collections;
/*  24:    */ import java.util.Comparator;
/*  25:    */ import java.util.Date;
/*  26:    */ import java.util.HashMap;
/*  27:    */ import java.util.HashSet;
/*  28:    */ import java.util.Iterator;
/*  29:    */ import java.util.List;
/*  30:    */ import java.util.Set;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.ejb.Stateless;
/*  33:    */ 
/*  34:    */ @Stateless
/*  35:    */ public class ServicioReporteCobroClienteImpl
/*  36:    */   implements ServicioReporteCobroCliente
/*  37:    */ {
/*  38:    */   @EJB
/*  39:    */   private ReporteCobroClienteDao reporteCobroClienteDao;
/*  40:    */   @EJB
/*  41:    */   private FacturaClienteDao facturaClienteDao;
/*  42:    */   @EJB
/*  43:    */   private CobroDao cobroDao;
/*  44:    */   @EJB
/*  45:    */   private GenericoDao<Calificacion> calificacionDao;
/*  46:    */   
/*  47:    */   public List getReporteCobroCliente(Date fechaDesde, Date fechaHasta, int idCliente, Recaudador recaudador, int idOrganizacion, boolean indicadorPorVendedor, int idDocumento, boolean posfechados, Sucursal sucursal, PuntoDeVenta puntoDeVenta, EntidadUsuario usuario, Zona zona)
/*  48:    */     throws ExcepcionAS2
/*  49:    */   {
/*  50: 73 */     return this.reporteCobroClienteDao.getReportePagoCliente(fechaDesde, fechaHasta, idCliente, recaudador, idOrganizacion, indicadorPorVendedor, idDocumento, posfechados, sucursal, puntoDeVenta, usuario, zona);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List getReporteCobro(int idCobro)
/*  54:    */   {
/*  55: 85 */     return this.reporteCobroClienteDao.getReporteCobro(idCobro);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<Object[]> getReporteRegistroVoucher(int idOrganizacion, int idCobro)
/*  59:    */   {
/*  60: 90 */     return this.reporteCobroClienteDao.getRegistroVoucher(idOrganizacion, idCobro);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<Object[]> getReportePagoVoucher(int idOrganizacion, int idCobro)
/*  64:    */   {
/*  65: 95 */     return this.reporteCobroClienteDao.getPagoVoucher(idOrganizacion, idCobro);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<Object[]> getReporteConsultaVoucher(int idOrganizacion, PuntoDeVenta puntoDeVenta, Date fechaDesde, Date fechaHasta, String fechaReporte)
/*  69:    */   {
/*  70:101 */     return this.reporteCobroClienteDao.getConsultaVoucher(idOrganizacion, puntoDeVenta, fechaDesde, fechaHasta, fechaReporte);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Long countTickets(int idOrganizacion, Date fecha, String operacion, String estacion)
/*  74:    */   {
/*  75:106 */     return this.reporteCobroClienteDao.countTickets(idOrganizacion, fecha, operacion, estacion);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public List<Object[]> listaReporteTicket(int idOrganizacion, Date fechaDesde, Date fechaHasta, String numeroTicket, String operacion, String agencia, String tipoReporte)
/*  79:    */   {
/*  80:112 */     return this.reporteCobroClienteDao.getlistaReporteTicket(idOrganizacion, fechaDesde, fechaHasta, numeroTicket, operacion, agencia, tipoReporte);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public List getReporteCobroClientePorFormaPago(Date fechaDesde, Date fechaHasta, int idCliente, Recaudador recaudador, int idOrganizacion, boolean indicadorResumen, int idDocumento, boolean posfechados, Sucursal sucursal, Zona zona, PuntoDeVenta puntoDeVenta, EntidadUsuario usuario)
/*  84:    */   {
/*  85:126 */     return this.reporteCobroClienteDao.getReporteCobroClientePorFormaPago(fechaDesde, fechaHasta, idCliente, recaudador, idOrganizacion, indicadorResumen, idDocumento, posfechados, sucursal, zona, puntoDeVenta, usuario);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public List getReporteCobroProtestos(int idEmpresa, Date fechaDesde, Date fechaHasta, int idCuentaBancariaOrganizacion, int idFormaCobro, int idOrganizacion)
/*  89:    */   {
/*  90:141 */     return this.reporteCobroClienteDao.getReporteCobroProtestos(idEmpresa, fechaDesde, fechaHasta, idCuentaBancariaOrganizacion, idFormaCobro, idOrganizacion);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public List getReporteDepositosCobro(int idEmpresa, Date fechaDesde, Date fechaHasta, int idCuentaBancariaOrganizacion, boolean pendienteDeposito, int idOrganizacion, int idFormaCobro)
/*  94:    */   {
/*  95:155 */     return this.reporteCobroClienteDao.getReporteDepositosCobro(idEmpresa, fechaDesde, fechaHasta, idCuentaBancariaOrganizacion, pendienteDeposito, idOrganizacion, idFormaCobro);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public List getReporteChequesProtestados(Date fechaDesde, Date fechaHasta, Empresa empresa, CuentaBancariaOrganizacion cuentaBancariaOrganizacion, boolean indicadorSaldoDiferenciaCero)
/*  99:    */   {
/* 100:168 */     return this.reporteCobroClienteDao.getReporteChequesProtestados(fechaDesde, fechaHasta, empresa, cuentaBancariaOrganizacion, indicadorSaldoDiferenciaCero);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List<Object[]> facturasCanceladas(Date fechaDesde, Date fechaHasta, int idOrganizacion, Sucursal sucursal, Zona zona)
/* 104:    */   {
/* 105:174 */     List<Object[]> lista = this.reporteCobroClienteDao.facturasCanceladas(fechaDesde, fechaHasta, idOrganizacion, sucursal, zona);
/* 106:175 */     HashMap<Integer, Object[]> hmFactura = new HashMap();
/* 107:176 */     List<Integer> listaIdsFacturaCliente = new ArrayList();
/* 108:177 */     List<Object[]> listaNueva = new ArrayList();
/* 109:    */     
/* 110:179 */     List<List<Object[]>> listaFinal = new ArrayList();
/* 111:180 */     List<Object[]> listaAux = new ArrayList();
/* 112:181 */     int contador = 0;
/* 113:183 */     if (lista.size() > 2000)
/* 114:    */     {
/* 115:184 */       for (Object[] prer : lista)
/* 116:    */       {
/* 117:185 */         if (contador == 2000)
/* 118:    */         {
/* 119:186 */           listaAux = new ArrayList();
/* 120:187 */           listaFinal.add(listaAux);
/* 121:188 */           contador = 0;
/* 122:    */         }
/* 123:191 */         contador++;
/* 124:192 */         listaAux.add(prer);
/* 125:194 */         for (List<Object[]> listaNotasCreditos : listaFinal)
/* 126:    */         {
/* 127:195 */           for (Iterator localIterator3 = listaNotasCreditos.iterator(); localIterator3.hasNext();)
/* 128:    */           {
/* 129:195 */             a = (Object[])localIterator3.next();
/* 130:196 */             hmFactura.put((Integer)a[5], a);
/* 131:197 */             listaIdsFacturaCliente.add((Integer)a[5]);
/* 132:    */           }
/* 133:199 */           if (listaIdsFacturaCliente.size() > 0)
/* 134:    */           {
/* 135:200 */             Object listaNotasCredito = this.reporteCobroClienteDao.listaNotasDeCredito(listaIdsFacturaCliente);
/* 136:201 */             for (Object[] notaCredito : (List)listaNotasCredito)
/* 137:    */             {
/* 138:202 */               Object[] facturaCliente = (Object[])hmFactura.get(notaCredito[0]);
/* 139:203 */               if (facturaCliente != null) {
/* 140:204 */                 crearEstadoFactura(facturaCliente, listaNueva, notaCredito, hmFactura);
/* 141:    */               }
/* 142:    */             }
/* 143:207 */             for (Object[] facturaCliente : hmFactura.values()) {
/* 144:208 */               crearEstadoFactura(facturaCliente, listaNueva, new Object[3], hmFactura);
/* 145:    */             }
/* 146:    */           }
/* 147:    */         }
/* 148:    */       }
/* 149:    */       Object[] a;
/* 150:213 */       sortListaObjetos(listaNueva, 0);
/* 151:214 */       return listaNueva;
/* 152:    */     }
/* 153:216 */     for (??? = lista.iterator(); ???.hasNext();)
/* 154:    */     {
/* 155:216 */       a = (Object[])???.next();
/* 156:217 */       hmFactura.put((Integer)a[5], a);
/* 157:218 */       listaIdsFacturaCliente.add((Integer)a[5]);
/* 158:    */     }
/* 159:    */     Object[] a;
/* 160:220 */     if (listaIdsFacturaCliente.size() > 0)
/* 161:    */     {
/* 162:221 */       Object listaNotasCredito = this.reporteCobroClienteDao.listaNotasDeCredito(listaIdsFacturaCliente);
/* 163:222 */       for (Object[] notaCredito : (List)listaNotasCredito)
/* 164:    */       {
/* 165:223 */         Object[] facturaCliente = (Object[])hmFactura.get(notaCredito[0]);
/* 166:224 */         if (facturaCliente != null) {
/* 167:225 */           crearEstadoFactura(facturaCliente, listaNueva, notaCredito, hmFactura);
/* 168:    */         }
/* 169:    */       }
/* 170:228 */       for (Object[] facturaCliente : hmFactura.values()) {
/* 171:229 */         crearEstadoFactura(facturaCliente, listaNueva, new Object[3], hmFactura);
/* 172:    */       }
/* 173:    */     }
/* 174:232 */     sortListaObjetos(listaNueva, 0);
/* 175:233 */     return listaNueva;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void sortListaObjetos(List<Object[]> lista, final int pos)
/* 179:    */   {
/* 180:239 */     if (lista.size() > 0) {
/* 181:240 */       Collections.sort(lista, new Comparator()
/* 182:    */       {
/* 183:    */         public int compare(Object[] o1, Object[] o2)
/* 184:    */         {
/* 185:243 */           return ((String)o1[pos]).compareTo((String)o2[pos]);
/* 186:    */         }
/* 187:    */       });
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void crearEstadoFactura(Object[] facturaCliente, List<Object[]> listaNueva, Object[] notaCredito, HashMap<Integer, Object[]> hmFactura)
/* 192:    */   {
/* 193:251 */     Object[] estadoCuenta = new Object[12];
/* 194:252 */     listaNueva.add(estadoCuenta);
/* 195:253 */     estadoCuenta[0] = facturaCliente[0];
/* 196:254 */     estadoCuenta[1] = facturaCliente[1];
/* 197:255 */     estadoCuenta[2] = facturaCliente[2];
/* 198:256 */     estadoCuenta[3] = facturaCliente[3];
/* 199:257 */     estadoCuenta[4] = facturaCliente[4];
/* 200:258 */     estadoCuenta[5] = facturaCliente[5];
/* 201:    */     
/* 202:260 */     String categoriaProducto = "";
/* 203:261 */     for (String s : this.facturaClienteDao.listaCategoriaProducto(((Integer)facturaCliente[5]).intValue())) {
/* 204:262 */       if (!categoriaProducto.contains(s)) {
/* 205:263 */         categoriaProducto = categoriaProducto.concat(s + ", ");
/* 206:    */       }
/* 207:    */     }
/* 208:266 */     estadoCuenta[6] = categoriaProducto;
/* 209:    */     
/* 210:268 */     BigDecimal totalNotaCredito = BigDecimal.ZERO;
/* 211:269 */     BigDecimal totalImpuestoCredito = BigDecimal.ZERO;
/* 212:270 */     BigDecimal totalFactura = (BigDecimal)facturaCliente[4];
/* 213:271 */     if ((notaCredito[1] != null) && (notaCredito[2] != null))
/* 214:    */     {
/* 215:272 */       totalNotaCredito = (BigDecimal)notaCredito[1];
/* 216:273 */       totalImpuestoCredito = (BigDecimal)notaCredito[2];
/* 217:274 */       hmFactura.remove(notaCredito[0]);
/* 218:    */     }
/* 219:276 */     estadoCuenta[7] = totalNotaCredito;
/* 220:277 */     estadoCuenta[8] = facturaCliente[7];
/* 221:278 */     estadoCuenta[9] = totalImpuestoCredito;
/* 222:279 */     estadoCuenta[10] = totalFactura.subtract(totalNotaCredito);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public ArrayList<ReporteCobros> getListaCobros(Integer idCliente, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 226:    */   {
/* 227:285 */     return this.cobroDao.getListaCobros(idCliente, fechaDesde, fechaHasta, idOrganizacion);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<ReporteCarteraCobrada> getReporteCarteraCobrada(CategoriaEmpresa categoriaEmpresa, EntidadUsuario vendedor, Recaudador recaudador, Empresa empresa, Date fechaDesde, Date fechaHasta, boolean indicadorResumen, boolean indicadorVendedor, int idOrganizacion)
/* 231:    */   {
/* 232:293 */     List<ReporteCarteraCobrada> lista = this.reporteCobroClienteDao.getReporteCarteraCobrada(categoriaEmpresa, vendedor, recaudador, empresa, fechaDesde, fechaHasta, idOrganizacion);
/* 233:    */     
/* 234:    */ 
/* 235:296 */     List<ReporteCarteraCobrada> listaNueva = new ArrayList();
/* 236:    */     ReporteCarteraCobrada rcc;
/* 237:298 */     if (indicadorVendedor)
/* 238:    */     {
/* 239:299 */       if (indicadorResumen)
/* 240:    */       {
/* 241:302 */         HashMap<String, ReporteCarteraCobrada> hmReporteCarteraCobrada = new HashMap();
/* 242:303 */         for (ReporteCarteraCobrada rcc : lista)
/* 243:    */         {
/* 244:306 */           if (rcc.getIdAgenteComercial() == null)
/* 245:    */           {
/* 246:307 */             rcc.setIdAgenteComercial(Integer.valueOf(0));
/* 247:308 */             rcc.setNombreAgenteComercial("No definido");
/* 248:    */           }
/* 249:312 */           ReporteCarteraCobrada reporteCarteraCobrada = (ReporteCarteraCobrada)hmReporteCarteraCobrada.get(rcc.getIdEmpresa() + "~" + rcc.getIdAgenteComercial());
/* 250:314 */           if (reporteCarteraCobrada == null)
/* 251:    */           {
/* 252:316 */             reporteCarteraCobrada = rcc;
/* 253:317 */             hmReporteCarteraCobrada.put(rcc.getIdEmpresa() + "~" + rcc.getIdAgenteComercial(), rcc);
/* 254:    */           }
/* 255:321 */           int dias = FuncionesUtiles.diferenciasDeFechas(rcc.getFechaFactura(), rcc.getFechaCobro()) - 1;
/* 256:323 */           if (dias == 0) {
/* 257:324 */             reporteCarteraCobrada.setCobrado0(reporteCarteraCobrada.getCobrado0().add(rcc.getValorCobrado()));
/* 258:325 */           } else if ((0 < dias) && (dias <= 30)) {
/* 259:326 */             reporteCarteraCobrada.setCobrado30(reporteCarteraCobrada.getCobrado30().add(rcc.getValorCobrado()));
/* 260:327 */           } else if ((30 < dias) && (dias <= 60)) {
/* 261:328 */             reporteCarteraCobrada.setCobrado60(reporteCarteraCobrada.getCobrado60().add(rcc.getValorCobrado()));
/* 262:329 */           } else if ((60 < dias) && (dias <= 90)) {
/* 263:330 */             reporteCarteraCobrada.setCobrado90(reporteCarteraCobrada.getCobrado90().add(rcc.getValorCobrado()));
/* 264:331 */           } else if (90 < dias) {
/* 265:332 */             reporteCarteraCobrada.setCobrado120(reporteCarteraCobrada.getCobrado120().add(rcc.getValorCobrado()));
/* 266:    */           }
/* 267:336 */           reporteCarteraCobrada.setTotalCobrado(reporteCarteraCobrada.getTotalCobrado().add(rcc.getValorCobrado()));
/* 268:    */         }
/* 269:340 */         listaNueva = new ArrayList(hmReporteCarteraCobrada.values());
/* 270:    */       }
/* 271:    */       else
/* 272:    */       {
/* 273:345 */         HashMap<String, ReporteCarteraCobrada> hmReporteCarteraCobrada = new HashMap();
/* 274:346 */         for (??? = lista.iterator(); ???.hasNext();)
/* 275:    */         {
/* 276:346 */           rcc = (ReporteCarteraCobrada)???.next();
/* 277:349 */           if (rcc.getIdAgenteComercial() == null)
/* 278:    */           {
/* 279:350 */             rcc.setIdAgenteComercial(Integer.valueOf(0));
/* 280:351 */             rcc.setNombreAgenteComercial("No definido");
/* 281:    */           }
/* 282:356 */           ReporteCarteraCobrada reporteCarteraCobrada = (ReporteCarteraCobrada)hmReporteCarteraCobrada.get(rcc.getIdEmpresa() + "~" + rcc.getIdAgenteComercial() + "~" + rcc.getIdFacturaCliente());
/* 283:358 */           if (reporteCarteraCobrada == null)
/* 284:    */           {
/* 285:360 */             reporteCarteraCobrada = rcc;
/* 286:361 */             hmReporteCarteraCobrada.put(rcc.getIdEmpresa() + "~" + rcc.getIdAgenteComercial() + "~" + rcc.getIdFacturaCliente(), rcc);
/* 287:    */           }
/* 288:365 */           int dias = FuncionesUtiles.diferenciasDeFechas(rcc.getFechaFactura(), rcc.getFechaCobro()) - 1;
/* 289:367 */           if (dias == 0) {
/* 290:368 */             reporteCarteraCobrada.setCobrado0(reporteCarteraCobrada.getCobrado0().add(rcc.getValorCobrado()));
/* 291:369 */           } else if ((0 < dias) && (dias <= 30)) {
/* 292:370 */             reporteCarteraCobrada.setCobrado30(reporteCarteraCobrada.getCobrado30().add(rcc.getValorCobrado()));
/* 293:371 */           } else if ((30 < dias) && (dias <= 60)) {
/* 294:372 */             reporteCarteraCobrada.setCobrado60(reporteCarteraCobrada.getCobrado60().add(rcc.getValorCobrado()));
/* 295:373 */           } else if ((60 < dias) && (dias <= 90)) {
/* 296:374 */             reporteCarteraCobrada.setCobrado90(reporteCarteraCobrada.getCobrado90().add(rcc.getValorCobrado()));
/* 297:375 */           } else if (90 < dias) {
/* 298:376 */             reporteCarteraCobrada.setCobrado120(reporteCarteraCobrada.getCobrado120().add(rcc.getValorCobrado()));
/* 299:    */           }
/* 300:380 */           reporteCarteraCobrada.setTotalCobrado(reporteCarteraCobrada.getTotalCobrado().add(rcc.getValorCobrado()));
/* 301:    */         }
/* 302:384 */         listaNueva = new ArrayList(hmReporteCarteraCobrada.values());
/* 303:    */       }
/* 304:389 */       Collections.sort(listaNueva, new Comparator()
/* 305:    */       {
/* 306:    */         public int compare(ReporteCarteraCobrada o1, ReporteCarteraCobrada o2)
/* 307:    */         {
/* 308:392 */           int value1 = o1.getNombreAgenteComercial().compareTo(o2.getNombreAgenteComercial());
/* 309:393 */           if (value1 == 0) {
/* 310:394 */             return o1.getNombreEmpresa().compareTo(o2.getNombreEmpresa());
/* 311:    */           }
/* 312:396 */           return value1;
/* 313:    */         }
/* 314:    */       });
/* 315:    */     }
/* 316:    */     else
/* 317:    */     {
/* 318:    */       Object idsFacturaCliente;
/* 319:403 */       if (indicadorResumen)
/* 320:    */       {
/* 321:406 */         HashMap<Integer, ReporteCarteraCobrada> hmReporteCarteraCobrada = new HashMap();
/* 322:407 */         idsFacturaCliente = new HashSet();
/* 323:408 */         for (ReporteCarteraCobrada rcc : lista)
/* 324:    */         {
/* 325:411 */           ReporteCarteraCobrada reporteCarteraCobrada = (ReporteCarteraCobrada)hmReporteCarteraCobrada.get(Integer.valueOf(rcc.getIdEmpresa()));
/* 326:412 */           if (reporteCarteraCobrada == null)
/* 327:    */           {
/* 328:414 */             reporteCarteraCobrada = rcc;
/* 329:    */             
/* 330:416 */             hmReporteCarteraCobrada.put(Integer.valueOf(rcc.getIdEmpresa()), rcc);
/* 331:    */           }
/* 332:420 */           if (!((Set)idsFacturaCliente).contains(rcc.getIdFacturaCliente()))
/* 333:    */           {
/* 334:421 */             reporteCarteraCobrada.setTotal(reporteCarteraCobrada.getTotal().add(rcc.getTotalFactura()));
/* 335:422 */             ((Set)idsFacturaCliente).add(rcc.getIdFacturaCliente());
/* 336:    */           }
/* 337:426 */           int dias = FuncionesUtiles.diferenciasDeFechas(rcc.getFechaFactura(), rcc.getFechaCobro()) - 1;
/* 338:427 */           if (dias == 0) {
/* 339:428 */             reporteCarteraCobrada.setCobrado0(reporteCarteraCobrada.getCobrado0().add(rcc.getValorCobrado()));
/* 340:429 */           } else if ((0 < dias) && (dias <= 30)) {
/* 341:430 */             reporteCarteraCobrada.setCobrado30(reporteCarteraCobrada.getCobrado30().add(rcc.getValorCobrado()));
/* 342:431 */           } else if ((30 < dias) && (dias <= 60)) {
/* 343:432 */             reporteCarteraCobrada.setCobrado60(reporteCarteraCobrada.getCobrado60().add(rcc.getValorCobrado()));
/* 344:433 */           } else if ((60 < dias) && (dias <= 90)) {
/* 345:434 */             reporteCarteraCobrada.setCobrado90(reporteCarteraCobrada.getCobrado90().add(rcc.getValorCobrado()));
/* 346:435 */           } else if (90 < dias) {
/* 347:436 */             reporteCarteraCobrada.setCobrado120(reporteCarteraCobrada.getCobrado120().add(rcc.getValorCobrado()));
/* 348:    */           }
/* 349:440 */           reporteCarteraCobrada.setTotalCobrado(reporteCarteraCobrada.getTotalCobrado().add(rcc.getValorCobrado()));
/* 350:    */         }
/* 351:444 */         listaNueva = new ArrayList(hmReporteCarteraCobrada.values());
/* 352:    */       }
/* 353:    */       else
/* 354:    */       {
/* 355:449 */         HashMap<String, ReporteCarteraCobrada> hmReporteCarteraCobrada = new HashMap();
/* 356:450 */         for (idsFacturaCliente = lista.iterator(); ((Iterator)idsFacturaCliente).hasNext();)
/* 357:    */         {
/* 358:450 */           ReporteCarteraCobrada rcc = (ReporteCarteraCobrada)((Iterator)idsFacturaCliente).next();
/* 359:    */           
/* 360:    */ 
/* 361:453 */           ReporteCarteraCobrada reporteCarteraCobrada = (ReporteCarteraCobrada)hmReporteCarteraCobrada.get(rcc.getIdEmpresa() + "~" + rcc.getIdFacturaCliente());
/* 362:455 */           if (reporteCarteraCobrada == null)
/* 363:    */           {
/* 364:457 */             reporteCarteraCobrada = rcc;
/* 365:458 */             hmReporteCarteraCobrada.put(rcc.getIdEmpresa() + "~" + rcc.getIdFacturaCliente(), rcc);
/* 366:    */           }
/* 367:462 */           int dias = FuncionesUtiles.diferenciasDeFechas(rcc.getFechaFactura(), rcc.getFechaCobro()) - 1;
/* 368:464 */           if (dias == 0) {
/* 369:465 */             reporteCarteraCobrada.setCobrado0(reporteCarteraCobrada.getCobrado0().add(rcc.getValorCobrado()));
/* 370:466 */           } else if ((0 < dias) && (dias <= 30)) {
/* 371:467 */             reporteCarteraCobrada.setCobrado30(reporteCarteraCobrada.getCobrado30().add(rcc.getValorCobrado()));
/* 372:468 */           } else if ((30 < dias) && (dias <= 60)) {
/* 373:469 */             reporteCarteraCobrada.setCobrado60(reporteCarteraCobrada.getCobrado60().add(rcc.getValorCobrado()));
/* 374:470 */           } else if ((60 < dias) && (dias <= 90)) {
/* 375:471 */             reporteCarteraCobrada.setCobrado90(reporteCarteraCobrada.getCobrado90().add(rcc.getValorCobrado()));
/* 376:472 */           } else if (90 < dias) {
/* 377:473 */             reporteCarteraCobrada.setCobrado120(reporteCarteraCobrada.getCobrado120().add(rcc.getValorCobrado()));
/* 378:    */           }
/* 379:477 */           reporteCarteraCobrada.setTotalCobrado(reporteCarteraCobrada.getTotalCobrado().add(rcc.getValorCobrado()));
/* 380:    */         }
/* 381:481 */         listaNueva = new ArrayList(hmReporteCarteraCobrada.values());
/* 382:    */       }
/* 383:485 */       FuncionesUtiles.ordenaLista(listaNueva, "nombreEmpresa");
/* 384:    */     }
/* 385:489 */     return listaNueva;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public List<ReporteCarteraCobrada> getReporteCalificacionCliente(CategoriaEmpresa categoriaEmpresa, EntidadUsuario vendedor, Recaudador recaudador, Empresa empresa, Calificacion calificacion, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 389:    */   {
/* 390:499 */     List<Calificacion> listaCalificacion = this.calificacionDao.obtenerListaCombo(Calificacion.class, "", true, null);
/* 391:    */     
/* 392:    */ 
/* 393:502 */     List<ReporteCarteraCobrada> listaNueva = new ArrayList();
/* 394:504 */     if (!listaCalificacion.isEmpty())
/* 395:    */     {
/* 396:507 */       HashMap<Integer, BigDecimal> hmSaldosCliente = new HashMap();
/* 397:508 */       List<Object[]> listaSaldos = this.reporteCobroClienteDao.getSaldoClientes(categoriaEmpresa, vendedor, recaudador, empresa, idOrganizacion);
/* 398:509 */       for (Iterator localIterator1 = listaSaldos.iterator(); localIterator1.hasNext();)
/* 399:    */       {
/* 400:509 */         object = (Object[])localIterator1.next();
/* 401:510 */         hmSaldosCliente.put((Integer)object[0], (BigDecimal)object[1]);
/* 402:    */       }
/* 403:    */       Object[] object;
/* 404:514 */       Object lista = this.reporteCobroClienteDao.getReporteCalificacionCliente(categoriaEmpresa, vendedor, recaudador, empresa, fechaDesde, fechaHasta, idOrganizacion);
/* 405:    */       ReporteCarteraCobrada reporteCarteraCobrada;
/* 406:517 */       if (calificacion == null) {
/* 407:518 */         for (object = ((List)lista).iterator(); object.hasNext();)
/* 408:    */         {
/* 409:518 */           reporteCarteraCobrada = (ReporteCarteraCobrada)object.next();
/* 410:521 */           if (reporteCarteraCobrada.getIdAgenteComercial() == null)
/* 411:    */           {
/* 412:522 */             reporteCarteraCobrada.setIdAgenteComercial(Integer.valueOf(0));
/* 413:523 */             reporteCarteraCobrada.setNombreAgenteComercial("NO DEFINIDO");
/* 414:    */           }
/* 415:527 */           reporteCarteraCobrada.setSaldo((BigDecimal)hmSaldosCliente.get(Integer.valueOf(reporteCarteraCobrada.getIdEmpresa())));
/* 416:530 */           for (Calificacion c : listaCalificacion) {
/* 417:531 */             if ((c.getRangoDesde() <= reporteCarteraCobrada.getDiferenciaDias().intValue()) && 
/* 418:532 */               (reporteCarteraCobrada.getDiferenciaDias().intValue() <= c.getRangoHasta()))
/* 419:    */             {
/* 420:533 */               reporteCarteraCobrada.setCalificacion(c.getCodigo());
/* 421:534 */               reporteCarteraCobrada.setNombreCalificacion(c.getNombre());
/* 422:535 */               listaNueva.add(reporteCarteraCobrada);
/* 423:536 */               break;
/* 424:    */             }
/* 425:    */           }
/* 426:    */         }
/* 427:    */       } else {
/* 428:541 */         for (ReporteCarteraCobrada reporteCarteraCobrada : (List)lista)
/* 429:    */         {
/* 430:544 */           if (reporteCarteraCobrada.getIdAgenteComercial() == null)
/* 431:    */           {
/* 432:545 */             reporteCarteraCobrada.setIdAgenteComercial(Integer.valueOf(0));
/* 433:546 */             reporteCarteraCobrada.setNombreAgenteComercial("NO DEFINIDO");
/* 434:    */           }
/* 435:550 */           reporteCarteraCobrada.setSaldo((BigDecimal)hmSaldosCliente.get(Integer.valueOf(reporteCarteraCobrada.getIdEmpresa())));
/* 436:553 */           if ((calificacion.getRangoDesde() <= reporteCarteraCobrada.getDiferenciaDias().intValue()) && 
/* 437:554 */             (reporteCarteraCobrada.getDiferenciaDias().intValue() <= calificacion.getRangoHasta()))
/* 438:    */           {
/* 439:555 */             reporteCarteraCobrada.setCalificacion(calificacion.getCodigo());
/* 440:556 */             reporteCarteraCobrada.setNombreCalificacion(calificacion.getNombre());
/* 441:557 */             listaNueva.add(reporteCarteraCobrada);
/* 442:    */           }
/* 443:    */         }
/* 444:    */       }
/* 445:563 */       Collections.sort(listaNueva, new Comparator()
/* 446:    */       {
/* 447:    */         public int compare(ReporteCarteraCobrada o1, ReporteCarteraCobrada o2)
/* 448:    */         {
/* 449:566 */           int value1 = o1.getCalificacion().compareTo(o2.getCalificacion());
/* 450:567 */           if (value1 == 0)
/* 451:    */           {
/* 452:568 */             int value2 = o1.getNombreAgenteComercial().compareTo(o2.getNombreAgenteComercial());
/* 453:569 */             if (value2 == 0) {
/* 454:570 */               return o1.getNombreEmpresa().compareTo(o2.getNombreEmpresa());
/* 455:    */             }
/* 456:572 */             return value2;
/* 457:    */           }
/* 458:575 */           return value1;
/* 459:    */         }
/* 460:    */       });
/* 461:    */     }
/* 462:583 */     return listaNueva;
/* 463:    */   }
/* 464:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.reportes.servicio.impl.ServicioReporteCobroClienteImpl
 * JD-Core Version:    0.7.0.1
 */