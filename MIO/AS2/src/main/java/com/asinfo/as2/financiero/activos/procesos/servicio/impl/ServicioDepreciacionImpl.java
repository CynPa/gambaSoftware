/*   1:    */ package com.asinfo.as2.financiero.activos.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.dao.DepreciacionDao;
/*   5:    */ import com.asinfo.as2.dao.DetalleDepreciacionDao;
/*   6:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   9:    */ import com.asinfo.as2.entities.CuentaContable;
/*  10:    */ import com.asinfo.as2.entities.Depreciacion;
/*  11:    */ import com.asinfo.as2.entities.DetalleDepreciacion;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  16:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  17:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo;
/*  20:    */ import com.asinfo.as2.financiero.activos.procesos.servicio.ServicioDepreciacion;
/*  21:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  23:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  24:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  25:    */ import com.asinfo.as2.util.AppUtil;
/*  26:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  27:    */ import java.math.BigDecimal;
/*  28:    */ import java.math.RoundingMode;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.Calendar;
/*  31:    */ import java.util.Date;
/*  32:    */ import java.util.HashMap;
/*  33:    */ import java.util.Iterator;
/*  34:    */ import java.util.List;
/*  35:    */ import java.util.Map;
/*  36:    */ import javax.ejb.EJB;
/*  37:    */ import javax.ejb.Stateless;
/*  38:    */ 
/*  39:    */ @Stateless
/*  40:    */ public class ServicioDepreciacionImpl
/*  41:    */   extends AbstractServicioAS2Financiero
/*  42:    */   implements ServicioDepreciacion
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = -982013272427309364L;
/*  45:    */   @EJB
/*  46:    */   private transient DepreciacionDao depreciacionDao;
/*  47:    */   @EJB
/*  48:    */   private transient DetalleDepreciacionDao detalleDepreciacionDao;
/*  49:    */   @EJB
/*  50:    */   private transient ServicioPeriodo servicioPeriodo;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioActivoFijo servicioActivoFijo;
/*  53:    */   
/*  54:    */   public void guardar(Depreciacion depreciacion)
/*  55:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  56:    */   {
/*  57: 74 */     if (!depreciacion.isEliminado())
/*  58:    */     {
/*  59: 75 */       validar(depreciacion);
/*  60: 76 */       if (depreciacion.getDepreciacionPadre() != null) {
/*  61: 77 */         actualizaDepreciacionAnterior(depreciacion);
/*  62:    */       }
/*  63: 79 */       for (DetalleDepreciacion detalleDepreciacion : depreciacion.getListaDetalleDepreciacion()) {
/*  64: 80 */         this.detalleDepreciacionDao.guardar(detalleDepreciacion);
/*  65:    */       }
/*  66:    */     }
/*  67: 83 */     this.depreciacionDao.guardar(depreciacion);
/*  68: 85 */     if (!depreciacion.isIndicadorDepreciacionFiscal())
/*  69:    */     {
/*  70: 86 */       Object listaDetalleDepreciacion = buscarPorActivo(depreciacion.getActivoFijo().getIdActivoFijo(), true);
/*  71: 87 */       HashMap<String, BigDecimal> mapaDetalleDepreciacion = new HashMap();
/*  72: 89 */       for (Iterator localIterator2 = ((List)listaDetalleDepreciacion).iterator(); localIterator2.hasNext();)
/*  73:    */       {
/*  74: 89 */         detalleDepreciacion = (DetalleDepreciacion)localIterator2.next();
/*  75: 90 */         String clave = detalleDepreciacion.getAnio() + "-" + detalleDepreciacion.getMes();
/*  76: 91 */         mapaDetalleDepreciacion.put(clave, detalleDepreciacion.getValor());
/*  77:    */       }
/*  78:    */       DetalleDepreciacion detalleDepreciacion;
/*  79: 93 */       BigDecimal valorActual = BigDecimal.ZERO;
/*  80: 94 */       for (DetalleDepreciacion detalleDepreciacion : depreciacion.getListaDetalleDepreciacion())
/*  81:    */       {
/*  82: 95 */         String clave = detalleDepreciacion.getAnio() + "-" + detalleDepreciacion.getMes();
/*  83: 96 */         valor = BigDecimal.ZERO;
/*  84: 97 */         if (mapaDetalleDepreciacion.containsKey(clave)) {
/*  85: 98 */           valor = (BigDecimal)mapaDetalleDepreciacion.get(clave);
/*  86:    */         }
/*  87:100 */         detalleDepreciacion.setDiferenciaTemporal(valor.subtract(detalleDepreciacion.getValor()));
/*  88:101 */         valorActual = valorActual.add(detalleDepreciacion.getDiferenciaTemporal());
/*  89:102 */         this.detalleDepreciacionDao.guardar(detalleDepreciacion);
/*  90:    */       }
/*  91:    */       BigDecimal valor;
/*  92:105 */       if (depreciacion.getDepreciacionPadre() != null)
/*  93:    */       {
/*  94:106 */         for (DetalleDepreciacion detalleDepreciacion : depreciacion.getDepreciacionPadre().getListaDetalleDepreciacion()) {
/*  95:107 */           this.detalleDepreciacionDao.detach(detalleDepreciacion);
/*  96:    */         }
/*  97:109 */         this.depreciacionDao.detach(depreciacion.getDepreciacionPadre());
/*  98:110 */         Depreciacion depreciacionPadre = cargarDetalle(depreciacion.getDepreciacionPadre().getIdDepreciacion());
/*  99:    */         
/* 100:112 */         BigDecimal valorDepreciacionAnterior = BigDecimal.ZERO;
/* 101:114 */         for (valor = depreciacionPadre.getListaDetalleDepreciacion().iterator(); valor.hasNext();)
/* 102:    */         {
/* 103:114 */           detalleDepreciacionTmp = (DetalleDepreciacion)valor.next();
/* 104:115 */           if (detalleDepreciacionTmp.isActivo()) {
/* 105:116 */             valorDepreciacionAnterior = valorDepreciacionAnterior.add(detalleDepreciacionTmp.getDiferenciaTemporal());
/* 106:    */           }
/* 107:    */         }
/* 108:    */         DetalleDepreciacion detalleDepreciacionTmp;
/* 109:119 */         BigDecimal valorProporcional = FuncionesUtiles.redondearBigDecimal(valorDepreciacionAnterior
/* 110:120 */           .add(valorActual).divide(BigDecimal.valueOf(depreciacion.getVidaUtil()), RoundingMode.HALF_UP), 2);
/* 111:121 */         for (DetalleDepreciacion detalleDepreciacion : depreciacion.getListaDetalleDepreciacion())
/* 112:    */         {
/* 113:122 */           detalleDepreciacion.setDiferenciaTemporalRevalorizacion(detalleDepreciacion.getDiferenciaTemporal().subtract(valorProporcional));
/* 114:123 */           this.detalleDepreciacionDao.guardar(detalleDepreciacion);
/* 115:    */         }
/* 116:125 */         contabilizar(depreciacion);
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void eliminar(Depreciacion depreciacion)
/* 122:    */   {
/* 123:137 */     this.depreciacionDao.eliminar(depreciacion);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Depreciacion buscarPorId(int idDepreciacion)
/* 127:    */   {
/* 128:148 */     return (Depreciacion)this.depreciacionDao.buscarPorId(Integer.valueOf(idDepreciacion));
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<Depreciacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 132:    */   {
/* 133:159 */     return this.depreciacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int contarPorCriterio(Map<String, String> filters)
/* 137:    */   {
/* 138:169 */     return this.depreciacionDao.contarPorCriterio(filters);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Depreciacion cargarDetalle(int idDepreciacion)
/* 142:    */   {
/* 143:179 */     return this.depreciacionDao.cargarDetalle(idDepreciacion);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void generarListaDepreciacion(Depreciacion depreciacion)
/* 147:    */   {
/* 148:189 */     generarListaDepreciacion(depreciacion, depreciacion.getVidaUtil());
/* 149:    */   }
/* 150:    */   
/* 151:    */   public ActivoFijo generarDepreciacion(ActivoFijo activoFijo)
/* 152:    */   {
/* 153:194 */     Depreciacion depreciacionFiscal = (Depreciacion)activoFijo.getListaDepreciacion().get(0);
/* 154:195 */     Depreciacion depreciacionNIIF = (Depreciacion)activoFijo.getListaDepreciacion().get(1);
/* 155:196 */     if (activoFijo.isIndicadorDepreciar())
/* 156:    */     {
/* 157:200 */       depreciacionFiscal.setEliminado(false);
/* 158:201 */       depreciacionFiscal.setEstado(Estado.APROBADO);
/* 159:202 */       depreciacionFiscal.setValorActivo(activoFijo.getValorActivo());
/* 160:203 */       depreciacionFiscal.setFechaInicioDepreciacion(activoFijo.getTraFechaInicioDepreciacion());
/* 161:    */       
/* 162:205 */       BigDecimal valorDepreciar = FuncionesUtiles.redondearBigDecimal(depreciacionFiscal
/* 163:206 */         .getValorActivo().subtract(depreciacionFiscal.getValorDepreciado())
/* 164:207 */         .subtract(depreciacionFiscal.getValorResidual()), 2);
/* 165:208 */       depreciacionFiscal.setValorADepreciar(valorDepreciar);
/* 166:    */       
/* 167:    */ 
/* 168:211 */       depreciacionNIIF.setEliminado(false);
/* 169:212 */       depreciacionNIIF.setEstado(Estado.APROBADO);
/* 170:213 */       depreciacionNIIF.setValorActivo(activoFijo.getValorActivo());
/* 171:214 */       depreciacionNIIF.setFechaInicioDepreciacion(activoFijo.getTraFechaInicioDepreciacion());
/* 172:    */       
/* 173:    */ 
/* 174:217 */       valorDepreciar = FuncionesUtiles.redondearBigDecimal(depreciacionNIIF
/* 175:218 */         .getValorActivo().subtract(depreciacionNIIF.getValorDepreciado())
/* 176:219 */         .subtract(depreciacionNIIF.getValorResidual()).add(activoFijo.getValorCompraRelacionada())
/* 177:220 */         .add(activoFijo.getValorAdicional()), 2);
/* 178:221 */       depreciacionNIIF.setValorADepreciar(valorDepreciar);
/* 179:    */       
/* 180:223 */       int numeroMesesDepreciacionMaximo = depreciacionFiscal.getVidaUtil();
/* 181:224 */       if (numeroMesesDepreciacionMaximo < depreciacionNIIF.getVidaUtil()) {
/* 182:225 */         numeroMesesDepreciacionMaximo = depreciacionNIIF.getVidaUtil();
/* 183:    */       }
/* 184:229 */       generarListaDepreciacion(depreciacionFiscal, numeroMesesDepreciacionMaximo);
/* 185:    */       
/* 186:    */ 
/* 187:232 */       generarListaDepreciacion(depreciacionNIIF, numeroMesesDepreciacionMaximo);
/* 188:    */     }
/* 189:    */     else
/* 190:    */     {
/* 191:234 */       depreciacionFiscal.setEliminado(true);
/* 192:235 */       depreciacionNIIF.setEliminado(true);
/* 193:    */     }
/* 194:238 */     return activoFijo;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void generarListaDepreciacion(Depreciacion depreciacion, int numeroMesesDepreciacionMaximo)
/* 198:    */   {
/* 199:250 */     for (DetalleDepreciacion detalleDepreciacion : depreciacion.getListaDetalleDepreciacion()) {
/* 200:251 */       detalleDepreciacion.setEliminado(true);
/* 201:    */     }
/* 202:254 */     if (depreciacion.getVidaUtil() > 0)
/* 203:    */     {
/* 204:256 */       int vidaUtil = depreciacion.getVidaUtil();
/* 205:257 */       int numeroMesesDepreciacion = numeroMesesDepreciacionMaximo;
/* 206:258 */       BigDecimal total = depreciacion.getValorADepreciar();
/* 207:259 */       BigDecimal valor = total.divide(new BigDecimal(depreciacion.getVidaUtil()), 2, RoundingMode.HALF_UP);
/* 208:260 */       BigDecimal valorSuma = BigDecimal.ZERO;
/* 209:261 */       if (depreciacion.isIndicadorDepreciacionFiscal()) {
/* 210:262 */         numeroMesesDepreciacion = vidaUtil;
/* 211:    */       }
/* 212:264 */       for (int i = 0; i < numeroMesesDepreciacion; i++)
/* 213:    */       {
/* 214:266 */         Date fechaDepreciacion = null;
/* 215:    */         DetalleDepreciacion detalleDepreciacion;
/* 216:269 */         if (i <= depreciacion.getListaDetalleDepreciacion().size() - 1)
/* 217:    */         {
/* 218:270 */           DetalleDepreciacion detalleDepreciacion = (DetalleDepreciacion)depreciacion.getListaDetalleDepreciacion().get(i);
/* 219:271 */           detalleDepreciacion.setEliminado(false);
/* 220:    */         }
/* 221:    */         else
/* 222:    */         {
/* 223:273 */           detalleDepreciacion = new DetalleDepreciacion();
/* 224:274 */           detalleDepreciacion.setDepreciacion(depreciacion);
/* 225:275 */           depreciacion.getListaDetalleDepreciacion().add(detalleDepreciacion);
/* 226:    */         }
/* 227:278 */         fechaDepreciacion = FuncionesUtiles.sumarFechaMeses(depreciacion.getFechaInicioDepreciacion(), i);
/* 228:279 */         detalleDepreciacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 229:280 */         detalleDepreciacion.setIdSucursal(AppUtil.getSucursal().getId());
/* 230:281 */         detalleDepreciacion.setAnio(FuncionesUtiles.getAnio(fechaDepreciacion));
/* 231:282 */         detalleDepreciacion.setMes(FuncionesUtiles.getMes(fechaDepreciacion));
/* 232:283 */         detalleDepreciacion.setDiferenciaTemporal(BigDecimal.ZERO);
/* 233:284 */         detalleDepreciacion.setDiferenciaTemporalRevalorizacion(BigDecimal.ZERO);
/* 234:285 */         detalleDepreciacion.setActivo(true);
/* 235:    */         
/* 236:287 */         valorSuma = valorSuma.add(valor);
/* 237:    */         
/* 238:289 */         BigDecimal diferencia = total.subtract(valorSuma);
/* 239:290 */         if (diferencia.compareTo(BigDecimal.ZERO) < 0)
/* 240:    */         {
/* 241:291 */           valor = valor.add(total.subtract(valorSuma));
/* 242:292 */           i = vidaUtil - 1;
/* 243:    */         }
/* 244:295 */         detalleDepreciacion.setValor(valor);
/* 245:    */       }
/* 246:    */     }
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Depreciacion generarListaRevalorizacion(Depreciacion depreciacion)
/* 250:    */     throws ExcepcionAS2Financiero
/* 251:    */   {
/* 252:311 */     Depreciacion depreciacionAnterior = null;
/* 253:    */     Date fechaMesSiguienteDepreciacion;
/* 254:    */     Depreciacion d;
/* 255:    */     Date fechaDepreciacion;
/* 256:    */     Date fechaMesSiguienteDepreciacion;
/* 257:313 */     if (depreciacion.getId() > 0)
/* 258:    */     {
/* 259:315 */       int mes = 0;
/* 260:316 */       int anio = 0;
/* 261:317 */       Calendar fecha = Calendar.getInstance();
/* 262:318 */       fecha.setTime(depreciacion.getFechaInicioDepreciacion());
/* 263:    */       
/* 264:320 */       depreciacionAnterior = depreciacion.getDepreciacionPadre();
/* 265:321 */       for (DetalleDepreciacion detalleDepreciacion : depreciacionAnterior.getListaDetalleDepreciacion()) {
/* 266:322 */         if ((detalleDepreciacion.getAnio() > anio) && (detalleDepreciacion.isActivo()))
/* 267:    */         {
/* 268:323 */           anio = detalleDepreciacion.getAnio();
/* 269:324 */           mes = detalleDepreciacion.getMes();
/* 270:    */         }
/* 271:325 */         else if ((detalleDepreciacion.getMes() > mes) && (detalleDepreciacion.isActivo()))
/* 272:    */         {
/* 273:326 */           mes = detalleDepreciacion.getMes();
/* 274:    */         }
/* 275:    */       }
/* 276:331 */       Date fechaAnterior = FuncionesUtiles.getFecha(1, mes, anio);
/* 277:    */       
/* 278:    */ 
/* 279:334 */       Date fechaDepreciacion = FuncionesUtiles.getFecha(1, fecha.get(2) + 1, fecha.get(1));
/* 280:    */       
/* 281:    */ 
/* 282:337 */       fechaMesSiguienteDepreciacion = FuncionesUtiles.sumarFechaMeses(fechaAnterior, 1);
/* 283:    */     }
/* 284:    */     else
/* 285:    */     {
/* 286:340 */       d = this.depreciacionDao.obtenerDepreciacionAnterior(depreciacion.getActivoFijo().getId());
/* 287:341 */       DetalleDepreciacion detalleDepreciacionUltimoDepreciado = null;
/* 288:342 */       if (d == null) {
/* 289:343 */         throw new ExcepcionAS2Financiero("msg_info_no_existe_depreciacion_previa");
/* 290:    */       }
/* 291:345 */       detalleDepreciacionUltimoDepreciado = this.depreciacionDao.obtenerUltimoDetalleDepreciacionDepreciado(d.getId());
/* 292:346 */       if (detalleDepreciacionUltimoDepreciado == null) {
/* 293:347 */         throw new ExcepcionAS2Financiero("msg_info_revalorizacion_previa");
/* 294:    */       }
/* 295:350 */       depreciacionAnterior = d;
/* 296:351 */       depreciacion.setDepreciacionPadre(depreciacionAnterior);
/* 297:    */       
/* 298:    */ 
/* 299:354 */       Calendar fecha = Calendar.getInstance();
/* 300:355 */       fecha.setTime(depreciacion.getFechaInicioDepreciacion());
/* 301:    */       
/* 302:    */ 
/* 303:358 */       fechaDepreciacion = FuncionesUtiles.getFecha(1, fecha.get(2) + 1, fecha.get(1));
/* 304:    */       
/* 305:    */ 
/* 306:361 */       Date fechaDetalleDepreciacionUltimoDepreciado = FuncionesUtiles.getFecha(1, detalleDepreciacionUltimoDepreciado.getMes(), detalleDepreciacionUltimoDepreciado
/* 307:362 */         .getAnio());
/* 308:    */       
/* 309:    */ 
/* 310:365 */       fechaMesSiguienteDepreciacion = FuncionesUtiles.sumarFechaMeses(fechaDetalleDepreciacionUltimoDepreciado, 1);
/* 311:    */     }
/* 312:373 */     if (fechaDepreciacion.equals(fechaMesSiguienteDepreciacion))
/* 313:    */     {
/* 314:374 */       generarListaDepreciacion(depreciacion);
/* 315:    */     }
/* 316:    */     else
/* 317:    */     {
/* 318:376 */       for (DetalleDepreciacion detalleDepreciacion : depreciacion.getListaDetalleDepreciacion()) {
/* 319:377 */         detalleDepreciacion.setEliminado(true);
/* 320:    */       }
/* 321:379 */       throw new ExcepcionAS2Financiero("msg_error_fecha_depreciacion");
/* 322:    */     }
/* 323:382 */     return depreciacionAnterior;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public Depreciacion obtenerDepreciacionActivo(int idActivoFijo, boolean indicadorDepreciacionFiscal)
/* 327:    */   {
/* 328:392 */     return this.depreciacionDao.obtenerDepreciacionActivo(idActivoFijo, indicadorDepreciacionFiscal);
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void esEditable(Depreciacion depreciacion)
/* 332:    */     throws ExcepcionAS2Financiero
/* 333:    */   {
/* 334:403 */     if (depreciacion.isIndicadorDepreciacionFiscal()) {
/* 335:404 */       throw new ExcepcionAS2Financiero("msg_accion_no_permitida");
/* 336:    */     }
/* 337:406 */     if (depreciacion.getDepreciacionPadre() == null) {
/* 338:407 */       throw new ExcepcionAS2Financiero("msg_accion_no_permitida");
/* 339:    */     }
/* 340:409 */     if (!this.depreciacionDao.buscarDetalleDepreciacionDepreciados(depreciacion.getId()).isEmpty()) {
/* 341:410 */       throw new ExcepcionAS2Financiero("msg_existen_activos_depreciados");
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   private void actualizaDepreciacionAnterior(Depreciacion depreciacion)
/* 346:    */     throws ExcepcionAS2Financiero
/* 347:    */   {
/* 348:423 */     Depreciacion depreciacionAnterior = generarListaRevalorizacion(depreciacion);
/* 349:424 */     int mes = FuncionesUtiles.getMes(depreciacion.getFechaInicioDepreciacion());
/* 350:425 */     int anio = FuncionesUtiles.getAnio(depreciacion.getFechaInicioDepreciacion());
/* 351:    */     
/* 352:427 */     this.depreciacionDao.actualizaDetalleDepreciacionNoDepreciados(depreciacionAnterior.getId(), anio, mes);
/* 353:428 */     depreciacionAnterior.setFechaFinDepreciacion(depreciacion.getFechaInicioDepreciacion());
/* 354:429 */     depreciacionAnterior.setActivo(false);
/* 355:431 */     if (depreciacion.getId() > 0) {}
/* 356:435 */     this.depreciacionDao.guardar(depreciacionAnterior);
/* 357:    */     
/* 358:437 */     depreciacion.setDepreciacionPadre(depreciacionAnterior);
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void contabilizar(Depreciacion depreciacion)
/* 362:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 363:    */   {
/* 364:443 */     Date fechaContabilizacion = FuncionesUtiles.getFechaFinMes(depreciacion.getFechaInicioDepreciacion());
/* 365:444 */     Date fecha = FuncionesUtiles.getFechaFinMes(FuncionesUtiles.sumarFechaMeses(fechaContabilizacion, -1));
/* 366:445 */     BigDecimal valorActivoCorte = this.depreciacionDao.getValorActivoCorteFecha(depreciacion.getDepreciacionPadre().getIdDepreciacion(), fecha);
/* 367:446 */     BigDecimal valorDepreciacionAcumulada = depreciacion.getActivoFijo().getValorActivo().subtract(valorActivoCorte);
/* 368:447 */     BigDecimal valorNetoRevalorizacion = valorActivoCorte.subtract(depreciacion.getValorActivo());
/* 369:448 */     if (valorNetoRevalorizacion.compareTo(BigDecimal.ZERO) != 0)
/* 370:    */     {
/* 371:450 */       ActivoFijo activoFijo = this.servicioActivoFijo.cargarDetalle(depreciacion.getActivoFijo().getIdActivoFijo());
/* 372:    */       int idCuentaContableRevalorizacion;
/* 373:    */       int idCuentaContableRevalorizacion;
/* 374:452 */       if (valorNetoRevalorizacion.compareTo(BigDecimal.ZERO) < 0)
/* 375:    */       {
/* 376:453 */         if (activoFijo.getCategoriaActivo().getCuentaContableSuperavitPorRevalorizacion() == null) {
/* 377:454 */           throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "CuentaContableSuperavitPorRevalorizacion");
/* 378:    */         }
/* 379:456 */         idCuentaContableRevalorizacion = activoFijo.getCategoriaActivo().getCuentaContableSuperavitPorRevalorizacion().getIdCuentaContable();
/* 380:    */       }
/* 381:    */       else
/* 382:    */       {
/* 383:458 */         if (activoFijo.getCategoriaActivo().getCuentaContableDeDeficitPorRevalorizacion() == null) {
/* 384:459 */           throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "CuentaContableDeDeficitPorRevalorizacion");
/* 385:    */         }
/* 386:461 */         idCuentaContableRevalorizacion = activoFijo.getCategoriaActivo().getCuentaContableDeDeficitPorRevalorizacion().getIdCuentaContable();
/* 387:    */       }
/* 388:    */       Asiento asiento;
/* 389:463 */       if (depreciacion.getAsientoRevalorizacion() != null)
/* 390:    */       {
/* 391:464 */         Asiento asiento = depreciacion.getAsientoRevalorizacion();
/* 392:465 */         asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/* 393:    */       }
/* 394:    */       else
/* 395:    */       {
/* 396:467 */         asiento = new Asiento();
/* 397:468 */         asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 398:469 */         asiento.setSucursal(AppUtil.getSucursal());
/* 399:470 */         TipoAsiento tipoAsiento = depreciacion.getDocumentoRevalorizacion().getTipoAsiento();
/* 400:471 */         asiento.setTipoAsiento(tipoAsiento);
/* 401:    */       }
/* 402:474 */       String concepto = "";
/* 403:475 */       concepto = depreciacion.getDocumentoRevalorizacion().getNombre().trim() + " / " + depreciacion.getFechaInicioDepreciacion();
/* 404:476 */       asiento.setConcepto(concepto);
/* 405:477 */       asiento.setFecha(fechaContabilizacion);
/* 406:478 */       String nombreActivoFijo = depreciacion.getActivoFijo().getNombre();
/* 407:479 */       List<DetalleInterfazContable> listaDA = new ArrayList();
/* 408:480 */       DetalleInterfazContable detalle = new DetalleInterfazContable(Integer.valueOf(idCuentaContableRevalorizacion), "Revalorización del activo " + nombreActivoFijo, "", "", valorNetoRevalorizacion);
/* 409:    */       
/* 410:    */ 
/* 411:483 */       depreciacion.setActivoFijo(this.servicioActivoFijo.cargarDetalle(depreciacion.getActivoFijo().getIdActivoFijo()));
/* 412:484 */       listaDA.add(detalle);
/* 413:485 */       detalle = new DetalleInterfazContable(Integer.valueOf(depreciacion.getActivoFijo().getCategoriaActivo().getCuentaContableDepreciacionAcumulada()
/* 414:486 */         .getIdCuentaContable()), "Revalorización del activo " + nombreActivoFijo, "", "", valorDepreciacionAcumulada);
/* 415:487 */       listaDA.add(detalle);
/* 416:488 */       BigDecimal valorActivo = depreciacion.getValorActivo().subtract(depreciacion.getDepreciacionPadre().getValorActivo());
/* 417:489 */       detalle = new DetalleInterfazContable(Integer.valueOf(depreciacion.getActivoFijo().getCategoriaActivo().getCuentaContableActivoFijo()
/* 418:490 */         .getIdCuentaContable()), "Revalorización del activo " + nombreActivoFijo, "", "", valorActivo);
/* 419:491 */       listaDA.add(detalle);
/* 420:    */       
/* 421:493 */       super.generarAsiento(asiento, listaDA, depreciacion.getDocumentoRevalorizacion());
/* 422:    */       
/* 423:495 */       this.servicioAsiento.guardar(asiento);
/* 424:    */       
/* 425:497 */       depreciacion.setEstado(Estado.CONTABILIZADO);
/* 426:    */       
/* 427:499 */       depreciacion.setFechaContabilizacion(fechaContabilizacion);
/* 428:500 */       depreciacion.setAsientoRevalorizacion(asiento);
/* 429:    */     }
/* 430:    */   }
/* 431:    */   
/* 432:    */   private void validar(Depreciacion depreciacion)
/* 433:    */     throws ExcepcionAS2Financiero
/* 434:    */   {
/* 435:512 */     this.servicioPeriodo.buscarPorFecha(depreciacion.getFechaInicioDepreciacion(), depreciacion.getIdOrganizacion(), DocumentoBase.DEPRECIACION);
/* 436:    */   }
/* 437:    */   
/* 438:    */   public List<DetalleDepreciacion> buscarPorActivo(int idActivoFijo, boolean indicadorDepreciacionFiscal)
/* 439:    */   {
/* 440:516 */     return this.depreciacionDao.buscarPorActivo(idActivoFijo, indicadorDepreciacionFiscal);
/* 441:    */   }
/* 442:    */   
/* 443:    */   public DetalleDepreciacion obtenerUltimoDetalleDepreciacionDepreciado(int idDepreciacion)
/* 444:    */   {
/* 445:526 */     return this.depreciacionDao.obtenerUltimoDetalleDepreciacionDepreciado(idDepreciacion);
/* 446:    */   }
/* 447:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.procesos.servicio.impl.ServicioDepreciacionImpl
 * JD-Core Version:    0.7.0.1
 */