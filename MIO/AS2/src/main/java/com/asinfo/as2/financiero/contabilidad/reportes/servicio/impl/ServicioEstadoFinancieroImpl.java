/*   1:    */ package com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetalleEstadoFinancieroDao;
/*   4:    */ import com.asinfo.as2.dao.EstadoFinancieroDao;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleEstadoFinanciero;
/*   7:    */ import com.asinfo.as2.entities.EstadoFinanciero;
/*   8:    */ import com.asinfo.as2.entities.NivelCuenta;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  12:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  14:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoEstadoFinanciero;
/*  16:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioPorcentajeImpuestoRentaAnual;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  20:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioEstadoFinanciero;
/*  21:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  24:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  25:    */ import java.io.PrintStream;
/*  26:    */ import java.math.BigDecimal;
/*  27:    */ import java.math.RoundingMode;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.Collection;
/*  30:    */ import java.util.Date;
/*  31:    */ import java.util.Iterator;
/*  32:    */ import java.util.List;
/*  33:    */ import java.util.Map;
/*  34:    */ import java.util.TreeMap;
/*  35:    */ import javax.ejb.EJB;
/*  36:    */ import javax.ejb.Stateless;
/*  37:    */ import javax.ejb.TransactionAttribute;
/*  38:    */ import javax.ejb.TransactionAttributeType;
/*  39:    */ 
/*  40:    */ @Stateless
/*  41:    */ public class ServicioEstadoFinancieroImpl
/*  42:    */   implements ServicioEstadoFinanciero
/*  43:    */ {
/*  44:    */   @EJB
/*  45:    */   private EstadoFinancieroDao estadoFinancieroDao;
/*  46:    */   @EJB
/*  47:    */   private DetalleEstadoFinancieroDao detalleEstadoFinancieroDao;
/*  48:    */   @EJB
/*  49:    */   private ServicioCuentaContable servicioCuentaContable;
/*  50:    */   @EJB
/*  51:    */   private ServicioPorcentajeImpuestoRentaAnual servicioPorcentajeImpuestoRentaAnual;
/*  52:    */   
/*  53:    */   public void guardar(EstadoFinanciero estadoFinanciero)
/*  54:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  55:    */   {
/*  56: 77 */     for (DetalleEstadoFinanciero d : estadoFinanciero.getListaDetalleEstadoFinanciero()) {
/*  57: 78 */       if (d.getNota() != "") {
/*  58: 79 */         this.detalleEstadoFinancieroDao.guardar(d);
/*  59:    */       }
/*  60:    */     }
/*  61: 82 */     this.estadoFinancieroDao.guardar(estadoFinanciero);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void eliminar(EstadoFinanciero estadoFinanciero)
/*  65:    */   {
/*  66: 94 */     this.estadoFinancieroDao.eliminar(estadoFinanciero);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public EstadoFinanciero buscarPorId(Integer id)
/*  70:    */   {
/*  71:106 */     return (EstadoFinanciero)this.estadoFinancieroDao.buscarPorId(id);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public EstadoFinanciero cargarDetalle(int idEstadoFinanciero)
/*  75:    */     throws ExcepcionAS2Financiero
/*  76:    */   {
/*  77:117 */     return this.estadoFinancieroDao.cargarDetalle(idEstadoFinanciero);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public EstadoFinanciero cargarDatos(EstadoFinanciero estadoFinanciero, String dimension, String valorDimension, int idSucursal, int nivel, boolean acumulado)
/*  81:    */     throws ExcepcionAS2Financiero
/*  82:    */   {
/*  83:136 */     int idOrganizacion = estadoFinanciero.getIdOrganizacion();
/*  84:    */     
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:141 */     Map<String, DetalleEstadoFinanciero> listaDetalleEstadoFinanciero = new TreeMap();
/*  89:    */     Date fechaDesde;
/*  90:147 */     if (estadoFinanciero.getTipoEstadoFinanciero() != TipoEstadoFinanciero.BALANCE_COMPROBACION)
/*  91:    */     {
/*  92:148 */       boolean indicadorNIIF = estadoFinanciero.isIndicadorNIIF();
/*  93:149 */       boolean indicadorCuentaMovimiento = estadoFinanciero.isIndicadorCuentaMovimiento();
/*  94:    */       
/*  95:151 */       fechaDesde = estadoFinanciero.getFechaDesde();
/*  96:152 */       Date fechaHasta = estadoFinanciero.getFechaHasta();
/*  97:    */       
/*  98:154 */       estadoFinanciero = this.estadoFinancieroDao.buscarPorFechaHasta(estadoFinanciero.getIdOrganizacion(), estadoFinanciero.getFechaHasta(), estadoFinanciero
/*  99:155 */         .getTipoEstadoFinanciero());
/* 100:    */       
/* 101:157 */       estadoFinanciero.setIndicadorNIIF(indicadorNIIF);
/* 102:158 */       estadoFinanciero.setIndicadorCuentaMovimiento(indicadorCuentaMovimiento);
/* 103:159 */       estadoFinanciero.setFechaDesde(fechaDesde);
/* 104:160 */       estadoFinanciero.setFechaHasta(fechaHasta);
/* 105:    */     }
/* 106:162 */     int nivelMaximo = 0;
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:167 */     List<CuentaContable> cuentasContables = this.servicioCuentaContable.obtenerListaCombo("codigo", true, null);
/* 112:168 */     for (CuentaContable cuentaContable : cuentasContables) {
/* 113:169 */       if (!listaDetalleEstadoFinanciero.containsKey(cuentaContable.getCodigo()))
/* 114:    */       {
/* 115:171 */         DetalleEstadoFinanciero detalle = new DetalleEstadoFinanciero();
/* 116:172 */         detalle.setCuentaContable(cuentaContable);
/* 117:173 */         detalle.setNombreCuentaContable(cuentaContable.getNombre());
/* 118:174 */         detalle.setCodigoCuenta(cuentaContable.getCodigo());
/* 119:    */         
/* 120:176 */         detalle.setEstadoFinanciero(estadoFinanciero);
/* 121:177 */         detalle.setDebe(BigDecimal.ZERO);
/* 122:178 */         detalle.setHaber(BigDecimal.ZERO);
/* 123:179 */         detalle.setNivel(cuentaContable.getNivelCuenta().getCodigo());
/* 124:180 */         detalle.setIdNivel(cuentaContable.getNivelCuenta().getIdNivelCuenta());
/* 125:182 */         if (detalle.getNivel() > nivelMaximo) {
/* 126:183 */           nivelMaximo = detalle.getNivel();
/* 127:    */         }
/* 128:186 */         listaDetalleEstadoFinanciero.put(cuentaContable.getCodigo(), detalle);
/* 129:    */       }
/* 130:    */     }
/* 131:194 */     for (DetalleEstadoFinanciero detalleEstadoFinanciero : estadoFinanciero.getListaDetalleEstadoFinanciero())
/* 132:    */     {
/* 133:195 */       DetalleEstadoFinanciero detalleEF = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get(detalleEstadoFinanciero.getCuentaContable().getCodigo());
/* 134:196 */       detalleEF.setIdDetalleEstadoFinanciero(detalleEstadoFinanciero.getIdDetalleEstadoFinanciero());
/* 135:197 */       detalleEF.setNota(detalleEstadoFinanciero.getNota());
/* 136:    */     }
/* 137:200 */     if (estadoFinanciero.getTipoEstadoFinanciero() == null) {
/* 138:201 */       estadoFinanciero.setTipoEstadoFinanciero(TipoEstadoFinanciero.BALANCE_GENERAL);
/* 139:    */     }
/* 140:207 */     if (estadoFinanciero.getTipoEstadoFinanciero().equals(TipoEstadoFinanciero.BALANCE_COMPROBACION)) {
/* 141:208 */       for (fechaDesde = this.servicioCuentaContable.calcularSaldos(FuncionesUtiles.obtenerFechaInicial(), 
/* 142:209 */             FuncionesUtiles.sumarFechaDiasMeses(estadoFinanciero.getFechaDesde(), -1), estadoFinanciero.getTipoEstadoFinanciero(), dimension, valorDimension, estadoFinanciero
/* 143:210 */             .isIndicadorNIIF(), idSucursal).iterator(); fechaDesde.hasNext();)
/* 144:    */       {
/* 145:208 */         detalleAsiento = (Object[])fechaDesde.next();
/* 146:    */         
/* 147:    */ 
/* 148:211 */         DetalleEstadoFinanciero dEF = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get(detalleAsiento[0]);
/* 149:212 */         dEF.setSaldoInicial(new BigDecimal(detalleAsiento[1].toString()).subtract(new BigDecimal(detalleAsiento[2].toString())));
/* 150:    */       }
/* 151:    */     }
/* 152:    */     Object[] detalleAsiento;
/* 153:219 */     Date fechaDesde = estadoFinanciero.getFechaDesde();
/* 154:220 */     if (((estadoFinanciero.getTipoEstadoFinanciero().equals(TipoEstadoFinanciero.BALANCE_RESULTADOS)) && (acumulado)) || 
/* 155:221 */       (estadoFinanciero.getTipoEstadoFinanciero().equals(TipoEstadoFinanciero.BALANCE_GENERAL))) {
/* 156:222 */       fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 157:    */     }
/* 158:224 */     for (Object[] detalleAsiento : this.servicioCuentaContable.calcularSaldos(fechaDesde, estadoFinanciero.getFechaHasta(), estadoFinanciero
/* 159:225 */       .getTipoEstadoFinanciero(), dimension, valorDimension, estadoFinanciero.isIndicadorNIIF(), idSucursal))
/* 160:    */     {
/* 161:226 */       DetalleEstadoFinanciero dEF = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get(detalleAsiento[0]);
/* 162:227 */       dEF.setDebe(new BigDecimal(detalleAsiento[1].toString()));
/* 163:228 */       dEF.setHaber(new BigDecimal(detalleAsiento[2].toString()));
/* 164:    */     }
/* 165:230 */     if (estadoFinanciero.getTipoEstadoFinanciero() != TipoEstadoFinanciero.BALANCE_COMPROBACION) {
/* 166:231 */       estadoFinanciero.setResultadoEjercicio(this.servicioCuentaContable.obteneResultadoEjercicio(estadoFinanciero.getFechaDesde(), estadoFinanciero
/* 167:232 */         .getFechaHasta(), dimension, valorDimension, estadoFinanciero.isIndicadorNIIF(), idSucursal, idOrganizacion));
/* 168:    */     }
/* 169:239 */     estadoFinanciero.setIdOrganizacion(idOrganizacion);
/* 170:    */     BigDecimal debeResultado;
/* 171:241 */     if (estadoFinanciero.getTipoEstadoFinanciero() == TipoEstadoFinanciero.BALANCE_GENERAL)
/* 172:    */     {
/* 173:250 */       BigDecimal resultado = estadoFinanciero.getResultadoEjercicio();
/* 174:    */       BigDecimal haberResultado;
/* 175:    */       CuentaContable cuentaContable;
/* 176:    */       BigDecimal haberResultado;
/* 177:256 */       if (resultado.compareTo(BigDecimal.ZERO) > 0)
/* 178:    */       {
/* 179:    */         try
/* 180:    */         {
/* 181:258 */           int idCuentaContable = ParametrosSistema.getCuentaPerdidaPeriodoActual(estadoFinanciero.getIdOrganizacion()).intValue();
/* 182:259 */           CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorId(Integer.valueOf(idCuentaContable));
/* 183:260 */           if (cuentaContable == null) {
/* 184:261 */             throw new Exception();
/* 185:    */           }
/* 186:    */         }
/* 187:    */         catch (Exception e)
/* 188:    */         {
/* 189:265 */           throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "CuentaPerdidaPeriodoActual");
/* 190:    */         }
/* 191:    */         CuentaContable cuentaContable;
/* 192:    */         int idCuentaContable;
/* 193:268 */         BigDecimal debeResultado = resultado;
/* 194:269 */         haberResultado = BigDecimal.ZERO;
/* 195:    */       }
/* 196:    */       else
/* 197:    */       {
/* 198:    */         try
/* 199:    */         {
/* 200:272 */           int idCuentaContable = ParametrosSistema.getCuentaUtilidadPeriodoActual(estadoFinanciero.getIdOrganizacion()).intValue();
/* 201:273 */           CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorId(Integer.valueOf(idCuentaContable));
/* 202:274 */           if (cuentaContable == null) {
/* 203:275 */             throw new Exception();
/* 204:    */           }
/* 205:    */         }
/* 206:    */         catch (Exception e)
/* 207:    */         {
/* 208:279 */           throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "CuentaUtilidadPeriodoActual");
/* 209:    */         }
/* 210:    */         int idCuentaContable;
/* 211:282 */         debeResultado = BigDecimal.ZERO;
/* 212:283 */         haberResultado = resultado.negate();
/* 213:    */       }
/* 214:289 */       DetalleEstadoFinanciero defResultado = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get(cuentaContable.getCodigo());
/* 215:    */       
/* 216:291 */       defResultado.setDebe(defResultado.getDebe().add(debeResultado));
/* 217:292 */       defResultado.setHaber(defResultado.getHaber().add(haberResultado));
/* 218:    */     }
/* 219:297 */     for (int i = nivelMaximo; i > 1; i--) {
/* 220:298 */       for (DetalleEstadoFinanciero detalleEstadoFinanciero : listaDetalleEstadoFinanciero.values()) {
/* 221:299 */         if ((detalleEstadoFinanciero.getNivel() == i) && 
/* 222:300 */           (detalleEstadoFinanciero.getCuentaContable().getCuentaPadre() != null))
/* 223:    */         {
/* 224:301 */           DetalleEstadoFinanciero d = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get(detalleEstadoFinanciero.getCuentaContable().getCuentaPadre()
/* 225:302 */             .getCodigo());
/* 226:303 */           BigDecimal debe = d.getDebe().add(detalleEstadoFinanciero.getDebe());
/* 227:304 */           BigDecimal haber = d.getHaber().add(detalleEstadoFinanciero.getHaber());
/* 228:305 */           BigDecimal saldoInicial = d.getSaldoInicial().add(detalleEstadoFinanciero.getSaldoInicial());
/* 229:306 */           d.setDebe(debe);
/* 230:307 */           d.setHaber(haber);
/* 231:308 */           d.setSaldoInicial(saldoInicial);
/* 232:    */         }
/* 233:    */       }
/* 234:    */     }
/* 235:313 */     estadoFinanciero.setListaDetalleEstadoFinanciero(new ArrayList());
/* 236:317 */     if (estadoFinanciero.getTipoEstadoFinanciero() == TipoEstadoFinanciero.BALANCE_GENERAL)
/* 237:    */     {
/* 238:321 */       DetalleEstadoFinanciero d = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get("1.");
/* 239:    */       BigDecimal saldo;
/* 240:    */       BigDecimal saldo;
/* 241:322 */       if (d != null) {
/* 242:323 */         saldo = d.getSaldo();
/* 243:    */       } else {
/* 244:325 */         saldo = BigDecimal.ZERO;
/* 245:    */       }
/* 246:327 */       estadoFinanciero.setSaldoActivo(saldo);
/* 247:328 */       d = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get("2.");
/* 248:329 */       if (d != null) {
/* 249:330 */         saldo = d.getSaldo();
/* 250:    */       } else {
/* 251:332 */         saldo = BigDecimal.ZERO;
/* 252:    */       }
/* 253:334 */       estadoFinanciero.setSaldoPasivo(saldo.negate());
/* 254:335 */       d = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get("3.");
/* 255:336 */       if (d != null) {
/* 256:337 */         saldo = d.getSaldo();
/* 257:    */       } else {
/* 258:339 */         saldo = BigDecimal.ZERO;
/* 259:    */       }
/* 260:341 */       estadoFinanciero.setSaldoPatrimonio(saldo.negate());
/* 261:    */     }
/* 262:346 */     if (estadoFinanciero.getTipoEstadoFinanciero() == TipoEstadoFinanciero.BALANCE_RESULTADOS)
/* 263:    */     {
/* 264:349 */       d = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get("4.");
/* 265:    */       BigDecimal saldo;
/* 266:    */       BigDecimal saldo;
/* 267:350 */       if (d != null) {
/* 268:351 */         saldo = d.getSaldo();
/* 269:    */       } else {
/* 270:353 */         saldo = BigDecimal.ZERO;
/* 271:    */       }
/* 272:355 */       estadoFinanciero.setSaldoIngreso(saldo);
/* 273:356 */       d = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get("5.");
/* 274:357 */       if (d != null) {
/* 275:358 */         saldo = d.getSaldo();
/* 276:    */       } else {
/* 277:360 */         saldo = BigDecimal.ZERO;
/* 278:    */       }
/* 279:362 */       estadoFinanciero.setSaldoCosto(saldo.negate());
/* 280:363 */       d = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get("6.");
/* 281:364 */       if (d != null) {
/* 282:365 */         saldo = d.getSaldo();
/* 283:    */       } else {
/* 284:367 */         saldo = BigDecimal.ZERO;
/* 285:    */       }
/* 286:369 */       estadoFinanciero.setSaldoGasto(saldo.negate());
/* 287:    */     }
/* 288:375 */     for (DetalleEstadoFinanciero d = listaDetalleEstadoFinanciero.values().iterator(); d.hasNext();)
/* 289:    */     {
/* 290:375 */       d = (DetalleEstadoFinanciero)d.next();
/* 291:376 */       if (((d.getDebe().compareTo(BigDecimal.ZERO) != 0) || (d.getHaber().compareTo(BigDecimal.ZERO) != 0) || 
/* 292:377 */         (d.getSaldoInicial().compareTo(BigDecimal.ZERO) != 0)) && (
/* 293:378 */         (estadoFinanciero.getTipoEstadoFinanciero() == TipoEstadoFinanciero.BALANCE_COMPROBACION) || 
/* 294:379 */         (d.getSaldo().compareTo(BigDecimal.ZERO) != 0))) {
/* 295:380 */         estadoFinanciero.getListaDetalleEstadoFinanciero().add(d);
/* 296:    */       }
/* 297:    */     }
/* 298:    */     DetalleEstadoFinanciero d;
/* 299:386 */     List<DetalleEstadoFinanciero> lista = estadoFinanciero.getListaDetalleEstadoFinanciero();
/* 300:387 */     estadoFinanciero.setListaDetalleEstadoFinanciero(new ArrayList());
/* 301:388 */     for (DetalleEstadoFinanciero detalleEstadoFinanciero : lista) {
/* 302:390 */       if (estadoFinanciero.isIndicadorCuentaMovimiento())
/* 303:    */       {
/* 304:391 */         if (detalleEstadoFinanciero.getCuentaContable().isIndicadorMovimiento()) {
/* 305:392 */           estadoFinanciero.getListaDetalleEstadoFinanciero().add(detalleEstadoFinanciero);
/* 306:    */         }
/* 307:    */       }
/* 308:395 */       else if (detalleEstadoFinanciero.getCuentaContable().getNivelCuenta().getCodigo() <= nivel) {
/* 309:396 */         estadoFinanciero.getListaDetalleEstadoFinanciero().add(detalleEstadoFinanciero);
/* 310:    */       }
/* 311:    */     }
/* 312:400 */     return estadoFinanciero;
/* 313:    */   }
/* 314:    */   
/* 315:    */   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
/* 316:    */   public EstadoFinanciero cargarDatos(int anio1, int mes1, int anio2, int mes2, TipoEstadoFinanciero tipoEstadoFinanciero, String dimension, String valorDimension, boolean indicadorNIIF, int idSucursal, int nivel, boolean acumulado, int idOrganizacion)
/* 317:    */     throws ExcepcionAS2Financiero
/* 318:    */   {
/* 319:415 */     EstadoFinanciero ef1 = crearEstadoFinanciero(anio1, mes1, indicadorNIIF);
/* 320:416 */     ef1.setIdOrganizacion(idOrganizacion);
/* 321:417 */     ef1.setTipoEstadoFinanciero(tipoEstadoFinanciero);
/* 322:418 */     ef1 = cargarDatos(ef1, dimension, valorDimension, idSucursal, nivel, acumulado);
/* 323:419 */     ef1.setIdOrganizacion(idOrganizacion);
/* 324:    */     
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:424 */     EstadoFinanciero ef2 = crearEstadoFinanciero(anio2, mes2, indicadorNIIF);
/* 329:425 */     ef2.setIdOrganizacion(idOrganizacion);
/* 330:426 */     ef2.setTipoEstadoFinanciero(tipoEstadoFinanciero);
/* 331:427 */     ef2 = cargarDatos(ef2, dimension, valorDimension, idSucursal, nivel, acumulado);
/* 332:    */     
/* 333:429 */     TreeMap<String, DetalleEstadoFinanciero> listaDetalleEstadoFinanciero = new TreeMap();
/* 334:434 */     for (DetalleEstadoFinanciero detalleEstadoFinanciero : ef1.getListaDetalleEstadoFinanciero())
/* 335:    */     {
/* 336:435 */       String clave = detalleEstadoFinanciero.getCuentaContable().getCodigo();
/* 337:436 */       if (listaDetalleEstadoFinanciero.containsKey(clave))
/* 338:    */       {
/* 339:437 */         DetalleEstadoFinanciero def = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get(clave);
/* 340:438 */         def.setDebe(detalleEstadoFinanciero.getDebe());
/* 341:439 */         def.setHaber(detalleEstadoFinanciero.getHaber());
/* 342:440 */         def.setNota(detalleEstadoFinanciero.getNota());
/* 343:    */       }
/* 344:    */       else
/* 345:    */       {
/* 346:442 */         DetalleEstadoFinanciero def = new DetalleEstadoFinanciero();
/* 347:443 */         def.setNota(detalleEstadoFinanciero.getNota());
/* 348:444 */         def.setDebe(detalleEstadoFinanciero.getDebe());
/* 349:445 */         def.setHaber(detalleEstadoFinanciero.getHaber());
/* 350:446 */         def.setCuentaContable(detalleEstadoFinanciero.getCuentaContable());
/* 351:447 */         listaDetalleEstadoFinanciero.put(clave, def);
/* 352:    */       }
/* 353:    */     }
/* 354:453 */     for (??? = ef2.getListaDetalleEstadoFinanciero().iterator(); ???.hasNext();)
/* 355:    */     {
/* 356:453 */       detalleEstadoFinanciero = (DetalleEstadoFinanciero)???.next();
/* 357:454 */       String clave = detalleEstadoFinanciero.getCuentaContable().getCodigo();
/* 358:455 */       if (listaDetalleEstadoFinanciero.containsKey(clave))
/* 359:    */       {
/* 360:456 */         DetalleEstadoFinanciero def = (DetalleEstadoFinanciero)listaDetalleEstadoFinanciero.get(clave);
/* 361:457 */         def.setDebe2(detalleEstadoFinanciero.getDebe());
/* 362:458 */         def.setHaber2(detalleEstadoFinanciero.getHaber());
/* 363:459 */         def.setNota2(detalleEstadoFinanciero.getNota());
/* 364:    */       }
/* 365:    */       else
/* 366:    */       {
/* 367:461 */         DetalleEstadoFinanciero def = new DetalleEstadoFinanciero();
/* 368:462 */         def.setDebe2(detalleEstadoFinanciero.getDebe());
/* 369:463 */         def.setHaber2(detalleEstadoFinanciero.getHaber());
/* 370:464 */         def.setNota2(detalleEstadoFinanciero.getNota2());
/* 371:465 */         def.setCuentaContable(detalleEstadoFinanciero.getCuentaContable());
/* 372:466 */         listaDetalleEstadoFinanciero.put(clave, def);
/* 373:    */       }
/* 374:    */     }
/* 375:    */     DetalleEstadoFinanciero detalleEstadoFinanciero;
/* 376:469 */     EstadoFinanciero estadoFinanciero = new EstadoFinanciero();
/* 377:474 */     for (DetalleEstadoFinanciero d : listaDetalleEstadoFinanciero.values()) {
/* 378:475 */       estadoFinanciero.getListaDetalleEstadoFinanciero().add(d);
/* 379:    */     }
/* 380:477 */     return estadoFinanciero;
/* 381:    */   }
/* 382:    */   
/* 383:    */   private EstadoFinanciero crearEstadoFinanciero(int anio1, int mes1, boolean indicadorNIIF)
/* 384:    */   {
/* 385:484 */     Date fechaDesde = FuncionesUtiles.getFecha(1, mes1, anio1);
/* 386:485 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(anio1, mes1);
/* 387:    */     
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:490 */     EstadoFinanciero ef1 = new EstadoFinanciero();
/* 392:491 */     ef1.setFechaDesde(fechaDesde);
/* 393:492 */     ef1.setFechaHasta(fechaHasta);
/* 394:493 */     ef1.setIndicadorNIIF(indicadorNIIF);
/* 395:494 */     return ef1;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public List obtenerReporteEstadoFinancieroComparativoMensual(int anioDesde, int mesDesde, int anioHasta, int mesHasta, TipoEstadoFinanciero tipoEstadoFinanciero, String dimension, String valorDimension, boolean indicadorNIIF, Sucursal sucursal, int nivel, boolean flujoCajaEconomico, int idOrganizacion)
/* 399:    */     throws ExcepcionAS2Financiero
/* 400:    */   {
/* 401:503 */     List<EstadoFinanciero> listaEstadoFinanciero = new ArrayList();
/* 402:504 */     int anio = anioDesde;
/* 403:505 */     int mes = mesDesde;
/* 404:507 */     if (anioDesde > anioHasta) {
/* 405:508 */       System.out.println("anio desde no puede ser mayor al anio hasta");
/* 406:509 */     } else if ((anioDesde == anioHasta) && (mesDesde > mesHasta)) {
/* 407:510 */       System.out.println("mes desde no puede ser mayor al mes hasta en el mismo anio");
/* 408:    */     } else {
/* 409:    */       do
/* 410:    */       {
/* 411:513 */         EstadoFinanciero ef = crearEstadoFinanciero(anio, mes, indicadorNIIF);
/* 412:514 */         ef.setTipoEstadoFinanciero(tipoEstadoFinanciero);
/* 413:515 */         ef.setIdOrganizacion(idOrganizacion);
/* 414:    */         
/* 415:517 */         ef = cargarDatos(ef, dimension, valorDimension, sucursal == null ? -1 : sucursal.getIdSucursal(), nivel, tipoEstadoFinanciero
/* 416:518 */           .equals(TipoEstadoFinanciero.BALANCE_GENERAL));
/* 417:519 */         listaEstadoFinanciero.add(ef);
/* 418:520 */         mes++;
/* 419:521 */         if (mes == 13)
/* 420:    */         {
/* 421:522 */           anio++;
/* 422:523 */           mes = 1;
/* 423:    */         }
/* 424:526 */       } while ((anio < anioHasta) || ((anio == anioHasta) && (mes <= mesHasta)));
/* 425:    */     }
/* 426:529 */     List<Object[]> lista = new ArrayList();
/* 427:531 */     for (EstadoFinanciero estadoFinanciero : listaEstadoFinanciero)
/* 428:    */     {
/* 429:532 */       for (DetalleEstadoFinanciero detalleEstadoFinanciero : estadoFinanciero.getListaDetalleEstadoFinanciero())
/* 430:    */       {
/* 431:533 */         Object[] objeto = new Object[4];
/* 432:534 */         objeto[0] = detalleEstadoFinanciero.getCuentaContable().getCodigo();
/* 433:535 */         objeto[1] = detalleEstadoFinanciero.getCuentaContable().getNombre();
/* 434:536 */         objeto[2] = detalleEstadoFinanciero.getSaldo();
/* 435:537 */         objeto[3] = estadoFinanciero.getFechaHasta();
/* 436:538 */         lista.add(objeto);
/* 437:    */       }
/* 438:540 */       Object[] objeto = new Object[4];
/* 439:541 */       if (tipoEstadoFinanciero.equals(TipoEstadoFinanciero.BALANCE_GENERAL))
/* 440:    */       {
/* 441:542 */         objeto = new Object[4];
/* 442:543 */         objeto[0] = "7.";
/* 443:544 */         objeto[1] = "TOTAL ACTIVO";
/* 444:545 */         objeto[2] = estadoFinanciero.getSaldoActivo();
/* 445:546 */         objeto[3] = estadoFinanciero.getFechaHasta();
/* 446:547 */         lista.add(objeto);
/* 447:    */         
/* 448:549 */         objeto = new Object[4];
/* 449:550 */         objeto[0] = "8.";
/* 450:551 */         objeto[1] = "TOTAL PASIVO";
/* 451:552 */         objeto[2] = estadoFinanciero.getSaldoPasivo();
/* 452:553 */         objeto[3] = estadoFinanciero.getFechaHasta();
/* 453:554 */         lista.add(objeto);
/* 454:    */         
/* 455:556 */         objeto = new Object[4];
/* 456:557 */         objeto[0] = "9.";
/* 457:558 */         objeto[1] = "TOTAL PATRIMONIO";
/* 458:559 */         objeto[2] = estadoFinanciero.getSaldoPatrimonio();
/* 459:560 */         objeto[3] = estadoFinanciero.getFechaHasta();
/* 460:561 */         lista.add(objeto);
/* 461:    */       }
/* 462:563 */       if (tipoEstadoFinanciero.equals(TipoEstadoFinanciero.BALANCE_RESULTADOS))
/* 463:    */       {
/* 464:564 */         objeto = new Object[4];
/* 465:565 */         objeto[0] = "7.";
/* 466:566 */         objeto[1] = "RESULTADO DEL EJERCICIO";
/* 467:567 */         objeto[2] = estadoFinanciero.getResultadoEjercicio();
/* 468:568 */         objeto[3] = estadoFinanciero.getFechaHasta();
/* 469:569 */         lista.add(objeto);
/* 470:571 */         if (flujoCajaEconomico) {
/* 471:572 */           calculos(lista, estadoFinanciero, estadoFinanciero.getResultadoEjercicio());
/* 472:    */         }
/* 473:    */       }
/* 474:    */     }
/* 475:577 */     return lista;
/* 476:    */   }
/* 477:    */   
/* 478:    */   private void calculos(List<Object[]> lista, EstadoFinanciero estado, BigDecimal resultadoEjercicio)
/* 479:    */     throws ExcepcionAS2Financiero
/* 480:    */   {
/* 481:582 */     BigDecimal porcentajeUtilidadTrabajador = (BigDecimal)ParametrosSistema.getParametro(AppUtil.getOrganizacion().getId(), Parametro.PORCENTAJE_UTILIDAD_TRABAJADOR);
/* 482:    */     
/* 483:584 */     BigDecimal porcentajeUtilidadRentaAnual = this.servicioPorcentajeImpuestoRentaAnual.obtenerPorcentajePorAnio(FuncionesUtiles.getAnio(estado
/* 484:585 */       .getFechaHasta()));
/* 485:    */     
/* 486:587 */     Object[] objeto = new Object[4];
/* 487:588 */     objeto[0] = "7.1";
/* 488:589 */     objeto[1] = "UTILIDAD TRABAJADOR";
/* 489:590 */     BigDecimal utilidaTrabajador = resultadoEjercicio.multiply(porcentajeUtilidadTrabajador.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP)
/* 490:591 */       .negate());
/* 491:592 */     objeto[2] = utilidaTrabajador;
/* 492:593 */     objeto[3] = estado.getFechaHasta();
/* 493:594 */     lista.add(objeto);
/* 494:    */     
/* 495:596 */     objeto = new Object[4];
/* 496:597 */     objeto[0] = "7.2";
/* 497:598 */     objeto[1] = "IMPUESTO RENTA ANUAL ";
/* 498:599 */     BigDecimal utilidadImpuestoRentaAnual = resultadoEjercicio.multiply(porcentajeUtilidadRentaAnual.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP)
/* 499:600 */       .negate());
/* 500:601 */     objeto[2] = utilidadImpuestoRentaAnual;
/* 501:602 */     objeto[3] = estado.getFechaHasta();
/* 502:603 */     lista.add(objeto);
/* 503:    */     
/* 504:605 */     BigDecimal depreciacion = BigDecimal.ZERO;
/* 505:607 */     for (DetalleEstadoFinanciero detalleEstadoFinanciero : estado.getListaDetalleEstadoFinanciero()) {
/* 506:608 */       if ((detalleEstadoFinanciero.getCuentaContable().isIndicadorMovimiento()) && 
/* 507:609 */         (detalleEstadoFinanciero.getCuentaContable().getTipoCuentaContable().equals(TipoCuentaContable.DEPRECIACION))) {
/* 508:610 */         depreciacion = depreciacion.add(this.servicioCuentaContable.obtenerSaldo(estado.getFechaDesde(), estado.getFechaHasta(), detalleEstadoFinanciero
/* 509:611 */           .getCuentaContable().getIdCuentaContable(), ValoresCalculo.DEBE_HABER, TipoCalculo.MOVIMIENTOS_MES, false, detalleEstadoFinanciero
/* 510:612 */           .getCuentaContable().getIdSucursal()));
/* 511:    */       }
/* 512:    */     }
/* 513:616 */     objeto = new Object[4];
/* 514:617 */     objeto[0] = "7.3";
/* 515:618 */     objeto[1] = "DEPRECIACIONES";
/* 516:619 */     objeto[2] = depreciacion;
/* 517:620 */     objeto[3] = estado.getFechaHasta();
/* 518:621 */     lista.add(objeto);
/* 519:    */     
/* 520:623 */     objeto = new Object[4];
/* 521:624 */     objeto[0] = "7.4";
/* 522:625 */     objeto[1] = "FLUJO CAJA ECONOMICO ";
/* 523:626 */     objeto[2] = resultadoEjercicio.add(utilidaTrabajador.add(utilidadImpuestoRentaAnual)).add(depreciacion);
/* 524:627 */     objeto[3] = estado.getFechaHasta();
/* 525:628 */     lista.add(objeto);
/* 526:    */   }
/* 527:    */   
/* 528:    */   public static List<Estado> getEstadosComprobantes(int idOrganizacion)
/* 529:    */   {
/* 530:633 */     List<Estado> estados = new ArrayList();
/* 531:634 */     estados.add(Estado.REVISADO);
/* 532:635 */     if (!ParametrosSistema.getMayorizarComprobantes(idOrganizacion).booleanValue()) {
/* 533:636 */       estados.add(Estado.ELABORADO);
/* 534:    */     }
/* 535:639 */     return estados;
/* 536:    */   }
/* 537:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioEstadoFinancieroImpl
 * JD-Core Version:    0.7.0.1
 */