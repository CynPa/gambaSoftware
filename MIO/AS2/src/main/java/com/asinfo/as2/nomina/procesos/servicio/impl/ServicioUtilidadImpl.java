/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetalleHistoricoEmpleadoDao;
/*   4:    */ import com.asinfo.as2.dao.DetalleUtilidadDao;
/*   5:    */ import com.asinfo.as2.dao.UtilidadDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.entities.DetalleHistoricoEmpleado;
/*   8:    */ import com.asinfo.as2.entities.DetalleUtilidad;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Empleado;
/*  11:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  12:    */ import com.asinfo.as2.entities.PagoRol;
/*  13:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  14:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  15:    */ import com.asinfo.as2.entities.Rubro;
/*  16:    */ import com.asinfo.as2.entities.Utilidad;
/*  17:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  18:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  19:    */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*  20:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  21:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  22:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCargaEmpleado;
/*  23:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena;
/*  24:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  25:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  26:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  27:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*  28:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  29:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioUtilidad;
/*  30:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  31:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.math.RoundingMode;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.HashMap;
/*  36:    */ import java.util.Iterator;
/*  37:    */ import java.util.List;
/*  38:    */ import java.util.Map;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.ejb.Stateless;
/*  41:    */ import javax.ejb.TransactionAttribute;
/*  42:    */ import javax.ejb.TransactionAttributeType;
/*  43:    */ 
/*  44:    */ @Stateless
/*  45:    */ public class ServicioUtilidadImpl
/*  46:    */   implements ServicioUtilidad
/*  47:    */ {
/*  48:    */   @EJB
/*  49:    */   private UtilidadDao utilidadDao;
/*  50:    */   @EJB
/*  51:    */   private DetalleUtilidadDao detalleUtilidadDao;
/*  52:    */   @EJB
/*  53:    */   private DetalleHistoricoEmpleadoDao detalleHistoricoEmpleadoDao;
/*  54:    */   @EJB
/*  55:    */   private ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  56:    */   @EJB
/*  57:    */   private ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*  58:    */   @EJB
/*  59:    */   private ServicioDocumento servicioDocumento;
/*  60:    */   @EJB
/*  61:    */   private ServicioQuincena servicioQuincena;
/*  62:    */   @EJB
/*  63:    */   private ServicioRubro servicioRubro;
/*  64:    */   @EJB
/*  65:    */   private ServicioPagoRol servicioPagoRol;
/*  66:    */   @EJB
/*  67:    */   private ServicioRubroEmpleado servicioRubroEmpleado;
/*  68:    */   @EJB
/*  69:    */   private ServicioCargaEmpleado servicioCargaEmpleado;
/*  70:    */   
/*  71:    */   public void guardar(Utilidad utilidad)
/*  72:    */     throws AS2Exception
/*  73:    */   {
/*  74: 95 */     validar(utilidad);
/*  75: 97 */     for (DetalleUtilidad du : utilidad.getListaDetalleUtilidad()) {
/*  76: 98 */       this.detalleUtilidadDao.guardar(du);
/*  77:    */     }
/*  78:100 */     utilidad.setEstado(Estado.ELABORADO);
/*  79:101 */     this.utilidadDao.guardar(utilidad);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void validar(Utilidad utilidad)
/*  83:    */     throws AS2Exception
/*  84:    */   {
/*  85:105 */     for (DetalleUtilidad du : utilidad.getListaDetalleUtilidad()) {
/*  86:106 */       if ((du.getRetencionJudicial().compareTo(BigDecimal.ZERO) == 1) && (du.getEmpleado() != null))
/*  87:    */       {
/*  88:107 */         Rubro r = this.servicioRubroEmpleado.rubroEmpleadoRetencionJudicial(du.getEmpleado(), TipoRubroEnum.RETENCION_JUDICIAL);
/*  89:108 */         if (r == null) {
/*  90:110 */           throw new AS2Exception("com.asinfo.as2.nomina.procesos.servicio.impl.ServicioUtilidadImpl.CONFIGURAR_RUBRO_RETENCION_JUDICIAL", new String[] {du.getEmpleado().getNombres() + " " + du.getEmpleado().getApellidos() });
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void eliminar(Utilidad utilidad)
/*  97:    */   {
/*  98:123 */     this.utilidadDao.eliminar(utilidad);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Utilidad buscarPorId(int idUtilidad)
/* 102:    */   {
/* 103:133 */     return (Utilidad)this.utilidadDao.buscarPorId(Integer.valueOf(idUtilidad));
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<Utilidad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 107:    */   {
/* 108:143 */     return this.utilidadDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<Utilidad> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 112:    */   {
/* 113:154 */     return this.utilidadDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int contarPorCriterio(Map<String, String> filters)
/* 117:    */   {
/* 118:165 */     return this.utilidadDao.contarPorCriterio(filters);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Utilidad cargarDetalle(int idUtilidad)
/* 122:    */   {
/* 123:174 */     return this.utilidadDao.cargarDetalle(idUtilidad);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Utilidad procesarUtilidad(Utilidad utilidad)
/* 127:    */     throws ExcepcionAS2
/* 128:    */   {
/* 129:186 */     Date fecha = FuncionesUtiles.getFecha(31, 12, utilidad.getAnio());
/* 130:187 */     this.servicioCargaEmpleado.actualizaCargasActivas(utilidad.getIdOrganizacion(), fecha);
/* 131:    */     
/* 132:189 */     HashMap<Integer, DetalleUtilidad> mapaDetalleUtilidad = new HashMap();
/* 133:190 */     BigDecimal numeroDiasTrabajados = BigDecimal.ZERO;
/* 134:191 */     BigDecimal numeroDiasTrabajadosEmpleadosCargas = BigDecimal.ZERO;
/* 135:193 */     for (DetalleUtilidad du : utilidad.getListaDetalleUtilidad()) {
/* 136:194 */       if (du.getEmpleado() != null) {
/* 137:195 */         du.setEliminado(true);
/* 138:    */       }
/* 139:    */     }
/* 140:199 */     if (utilidad.getIdUtilidad() != 0) {}
/* 141:210 */     for (DetalleUtilidad du : utilidad.getListaDetalleUtilidad()) {
/* 142:211 */       if (!du.isEliminado())
/* 143:    */       {
/* 144:213 */         numeroDiasTrabajados = numeroDiasTrabajados.add(du.getDiasRealesTrabajados());
/* 145:214 */         du.setDiasTrabajados(du.getDiasRealesTrabajados());
/* 146:216 */         if (du.getCargasFamiliares() > 0) {
/* 147:217 */           numeroDiasTrabajadosEmpleadosCargas = numeroDiasTrabajadosEmpleadosCargas.add(du.getDiasRealesTrabajados().multiply(
/* 148:218 */             BigDecimal.valueOf(du.getCargasFamiliares())));
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:224 */     Date fechaDesde = FuncionesUtiles.setAtributoFecha(FuncionesUtiles.getFecha(1, 1, utilidad.getAnio()));
/* 153:225 */     Date fechaHasta = FuncionesUtiles.setAtributoFecha(FuncionesUtiles.getFecha(31, 12, utilidad.getAnio()));
/* 154:    */     
/* 155:227 */     List<HistoricoEmpleado> listaHistoricoEmpleados = this.servicioHistoricoEmpleado.historicosParaUtilidad(utilidad.getIdOrganizacion(), fechaDesde, fechaHasta);
/* 156:    */     
/* 157:    */ 
/* 158:230 */     BigDecimal diasTrabajadosTotal = BigDecimal.ZERO;
/* 159:231 */     BigDecimal diasTrabajadosTotalReal = BigDecimal.ZERO;
/* 160:232 */     if (!listaHistoricoEmpleados.isEmpty()) {
/* 161:234 */       for (HistoricoEmpleado historicoEmpleado : listaHistoricoEmpleados)
/* 162:    */       {
/* 163:236 */         List<DetalleHistoricoEmpleado> lista = this.detalleHistoricoEmpleadoDao.getListaDetalleHistoricoEmpleado(historicoEmpleado, fechaDesde, fechaHasta);
/* 164:    */         
/* 165:    */ 
/* 166:239 */         int i = 0;
/* 167:240 */         for (DetalleHistoricoEmpleado detalleHistoricoEmpleado : lista)
/* 168:    */         {
/* 169:242 */           Date fechaDesdeAux = fechaDesde;
/* 170:243 */           fechaHastaAux = fechaHasta;
/* 171:245 */           if (fechaDesde.before(detalleHistoricoEmpleado.getFechaInicio())) {
/* 172:246 */             fechaDesdeAux = FuncionesUtiles.setAtributoFecha(detalleHistoricoEmpleado.getFechaInicio());
/* 173:    */           }
/* 174:249 */           if (detalleHistoricoEmpleado.getFechaFin() != null)
/* 175:    */           {
/* 176:250 */             if (detalleHistoricoEmpleado.getFechaFin().before(fechaHasta)) {
/* 177:251 */               fechaHastaAux = FuncionesUtiles.setAtributoFecha(detalleHistoricoEmpleado.getFechaFin());
/* 178:    */             }
/* 179:    */           }
/* 180:253 */           else if ((historicoEmpleado.getFechaSalida() != null) && 
/* 181:254 */             (historicoEmpleado.getFechaSalida().before(fechaHasta))) {
/* 182:255 */             fechaHastaAux = historicoEmpleado.getFechaSalida();
/* 183:    */           }
/* 184:259 */           Date fechaHastaAux2 = fechaHastaAux;
/* 185:260 */           i++;
/* 186:260 */           if (i == lista.size()) {
/* 187:262 */             fechaHastaAux2 = FuncionesUtiles.getFechaFinMes(fechaHastaAux);
/* 188:    */           }
/* 189:265 */           int diasFalta = this.utilidadDao.getDiasFalta(fechaDesdeAux, fechaHastaAux2, historicoEmpleado.getEmpleado().getId());
/* 190:    */           
/* 191:    */ 
/* 192:268 */           BigDecimal diasTrabajados = new BigDecimal(FuncionesUtiles.diferenciasDeFechas30Dias(fechaDesdeAux, fechaHastaAux) - diasFalta);
/* 193:    */           
/* 194:270 */           diasTrabajadosTotalReal = diasTrabajadosTotalReal.add(FuncionesUtiles.porcentaje(diasTrabajados, detalleHistoricoEmpleado
/* 195:271 */             .getPorcentajeCapacidadSemanal(), 5));
/* 196:272 */           diasTrabajadosTotal = diasTrabajadosTotal.add(diasTrabajados);
/* 197:    */         }
/* 198:276 */         diasTrabajadosTotalReal = diasTrabajadosTotalReal.setScale(0, RoundingMode.HALF_DOWN);
/* 199:278 */         if (mapaDetalleUtilidad.containsKey(Integer.valueOf(historicoEmpleado.getEmpleado().getIdEmpleado())))
/* 200:    */         {
/* 201:279 */           DetalleUtilidad detalleUtilidad = (DetalleUtilidad)mapaDetalleUtilidad.get(Integer.valueOf(historicoEmpleado.getEmpleado().getIdEmpleado()));
/* 202:280 */           detalleUtilidad.setDiasTrabajados(detalleUtilidad.getDiasTrabajados().add(diasTrabajadosTotal));
/* 203:281 */           detalleUtilidad.setDiasRealesTrabajados(detalleUtilidad.getDiasRealesTrabajados().add(diasTrabajadosTotalReal));
/* 204:    */         }
/* 205:    */         else
/* 206:    */         {
/* 207:284 */           DetalleUtilidad detalleUtilidad = new DetalleUtilidad();
/* 208:285 */           detalleUtilidad.setEmpleado(historicoEmpleado.getEmpleado());
/* 209:286 */           detalleUtilidad.setUtilidad(utilidad);
/* 210:287 */           detalleUtilidad.setDiasTrabajados(diasTrabajadosTotal);
/* 211:288 */           detalleUtilidad.setDiasRealesTrabajados(diasTrabajadosTotalReal);
/* 212:289 */           detalleUtilidad.setCargasFamiliares(historicoEmpleado.getEmpleado().getNumeroCargasActivas());
/* 213:290 */           utilidad.getListaDetalleUtilidad().add(detalleUtilidad);
/* 214:    */           
/* 215:292 */           mapaDetalleUtilidad.put(Integer.valueOf(historicoEmpleado.getEmpleado().getIdEmpleado()), detalleUtilidad);
/* 216:    */         }
/* 217:294 */         numeroDiasTrabajados = numeroDiasTrabajados.add(diasTrabajadosTotalReal);
/* 218:295 */         numeroDiasTrabajadosEmpleadosCargas = numeroDiasTrabajadosEmpleadosCargas.add(diasTrabajadosTotalReal.multiply(
/* 219:296 */           BigDecimal.valueOf(historicoEmpleado.getEmpleado().getNumeroCargasActivas())));
/* 220:    */         
/* 221:298 */         diasTrabajadosTotal = BigDecimal.ZERO;
/* 222:299 */         diasTrabajadosTotalReal = BigDecimal.ZERO;
/* 223:    */       }
/* 224:    */     }
/* 225:    */     Date fechaHastaAux;
/* 226:304 */     BigDecimal valorUtilidad = utilidad.getValor();
/* 227:305 */     if (numeroDiasTrabajados.compareTo(BigDecimal.ZERO) == 1)
/* 228:    */     {
/* 229:307 */       BigDecimal valor10 = FuncionesUtiles.porcentaje(valorUtilidad, 10.0D);
/* 230:308 */       BigDecimal valor5 = FuncionesUtiles.porcentaje(valorUtilidad, 5.0D);
/* 231:    */       
/* 232:310 */       BigDecimal valorARepartir = valor10.add(valor5);
/* 233:311 */       BigDecimal valorRepartido = BigDecimal.ZERO;
/* 234:    */       
/* 235:313 */       BigDecimal valorDia10 = valor10.divide(numeroDiasTrabajados, 20, RoundingMode.HALF_UP);
/* 236:314 */       BigDecimal valorDia5 = BigDecimal.ZERO;
/* 237:315 */       if (numeroDiasTrabajadosEmpleadosCargas.compareTo(BigDecimal.ZERO) > 0) {
/* 238:316 */         valorDia5 = valor5.divide(numeroDiasTrabajadosEmpleadosCargas, 20, RoundingMode.HALF_UP);
/* 239:    */       }
/* 240:320 */       for (DetalleUtilidad detalleUtilidad : utilidad.getListaDetalleUtilidad()) {
/* 241:321 */         if (!detalleUtilidad.isEliminado())
/* 242:    */         {
/* 243:322 */           if (detalleUtilidad.getCargasFamiliares() != 0) {
/* 244:323 */             detalleUtilidad.setValor5(valorDia5.multiply(detalleUtilidad
/* 245:324 */               .getDiasRealesTrabajados().multiply(BigDecimal.valueOf(detalleUtilidad.getCargasFamiliares())))
/* 246:325 */               .setScale(2, RoundingMode.HALF_UP));
/* 247:    */           } else {
/* 248:327 */             detalleUtilidad.setValor5(BigDecimal.ZERO);
/* 249:    */           }
/* 250:330 */           detalleUtilidad.setValor10(valorDia10.multiply(detalleUtilidad.getDiasRealesTrabajados()).setScale(2, RoundingMode.HALF_UP));
/* 251:    */           
/* 252:332 */           valorRepartido = valorRepartido.add(detalleUtilidad.getValor10().add(detalleUtilidad.getValor5()));
/* 253:    */         }
/* 254:    */       }
/* 255:337 */       BigDecimal diferencia = valorARepartir.subtract(valorRepartido).setScale(2, RoundingMode.HALF_UP);
/* 256:    */       
/* 257:339 */       BigDecimal centavo = new BigDecimal(0.01D);
/* 258:340 */       if (diferencia.compareTo(BigDecimal.ZERO) < 0) {
/* 259:341 */         centavo = centavo.negate();
/* 260:    */       }
/* 261:343 */       centavo = centavo.setScale(2, RoundingMode.HALF_UP);
/* 262:    */       
/* 263:345 */       int i = utilidad.getListaDetalleUtilidad().size() - 1;
/* 264:346 */       while ((i >= 0) && (diferencia.compareTo(BigDecimal.ZERO) != 0))
/* 265:    */       {
/* 266:347 */         DetalleUtilidad detalleUtilidad = (DetalleUtilidad)utilidad.getListaDetalleUtilidad().get(i);
/* 267:348 */         if (!detalleUtilidad.isEliminado())
/* 268:    */         {
/* 269:349 */           diferencia = diferencia.subtract(centavo);
/* 270:350 */           detalleUtilidad.setValor10(detalleUtilidad.getValor10().add(centavo));
/* 271:    */         }
/* 272:352 */         i--;
/* 273:    */       }
/* 274:    */     }
/* 275:356 */     return utilidad;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public Utilidad obtenerUtilidadPorAnio(int idOrganizacion, int anio)
/* 279:    */   {
/* 280:366 */     return this.utilidadDao.obtenerUtilidadPorAnio(idOrganizacion, anio);
/* 281:    */   }
/* 282:    */   
/* 283:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 284:    */   public void cerrar(Utilidad utilidad)
/* 285:    */     throws ExcepcionAS2, AS2Exception
/* 286:    */   {
/* 287:373 */     utilidad = this.utilidadDao.cargarDetalle(utilidad.getIdUtilidad());
/* 288:374 */     utilidad.setEstado(Estado.CERRADO);
/* 289:    */     
/* 290:376 */     Map<Integer, PagoRolEmpleado> mapaPRE = new HashMap();
/* 291:377 */     Map<String, PagoRolEmpleadoRubro> mapaPRER = new HashMap();
/* 292:    */     
/* 293:379 */     PagoRol pagoRol = utilidad.getPagoRol();
/* 294:    */     Iterator localIterator1;
/* 295:380 */     if (pagoRol == null)
/* 296:    */     {
/* 297:381 */       pagoRol = new PagoRol();
/* 298:    */     }
/* 299:    */     else
/* 300:    */     {
/* 301:383 */       if (pagoRol.getEstado() != Estado.ELABORADO) {
/* 302:384 */         throw new ExcepcionAS2("Ya se ha aprobado o Contabilizado el Pago Rol");
/* 303:    */       }
/* 304:386 */       pagoRol = this.servicioPagoRol.cargarDetalle(pagoRol.getIdPagoRol());
/* 305:387 */       for (localIterator1 = pagoRol.getListaPagoRolEmpleado().iterator(); localIterator1.hasNext();)
/* 306:    */       {
/* 307:387 */         pagoRolEmpleado = (PagoRolEmpleado)localIterator1.next();
/* 308:388 */         pagoRolEmpleado.setEliminado(true);
/* 309:389 */         mapaPRE.put(Integer.valueOf(pagoRolEmpleado.getEmpleado().getIdEmpleado()), pagoRolEmpleado);
/* 310:390 */         for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : pagoRolEmpleado.getListaPagoRolEmpleadoRubro())
/* 311:    */         {
/* 312:391 */           String clave = pagoRolEmpleado.getEmpleado().getIdEmpleado() + "~" + pagoRolEmpleadoRubro.getRubro().getIdRubro();
/* 313:392 */           mapaPRER.put(clave, pagoRolEmpleadoRubro);
/* 314:393 */           pagoRolEmpleadoRubro.setEliminado(true);
/* 315:    */         }
/* 316:    */       }
/* 317:    */     }
/* 318:    */     PagoRolEmpleado pagoRolEmpleado;
/* 319:398 */     pagoRol.setAnio(utilidad.getAnio() + 1);
/* 320:399 */     pagoRol.setIdOrganizacion(utilidad.getIdOrganizacion());
/* 321:400 */     pagoRol.setIdSucursal(utilidad.getIdSucursal());
/* 322:401 */     pagoRol.setDocumento((Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.PAGO_ROL, utilidad.getIdOrganizacion()).get(0));
/* 323:402 */     pagoRol.setMes(FuncionesUtiles.getMes(utilidad.getFechaPagoUtilidad()));
/* 324:403 */     pagoRol.setFecha(FuncionesUtiles.getFecha(15, 4, utilidad.getAnio() + 1));
/* 325:404 */     pagoRol.setEstado(Estado.ELABORADO);
/* 326:405 */     pagoRol.setIndicadorGeneradoPorUtilidad(Boolean.valueOf(true));
/* 327:    */     
/* 328:407 */     Rubro rubroUtilidad = retornoRubroUtilidad(utilidad, "°");
/* 329:    */     
/* 330:409 */     pagoRol.setQuincena(rubroUtilidad.getQuincena());
/* 331:411 */     for (DetalleUtilidad du : utilidad.getListaDetalleUtilidad()) {
/* 332:412 */       if (du.getEmpleado() != null)
/* 333:    */       {
/* 334:414 */         PagoRolEmpleado pre = (PagoRolEmpleado)mapaPRE.get(Integer.valueOf(du.getEmpleado().getIdEmpleado()));
/* 335:415 */         if (pre == null)
/* 336:    */         {
/* 337:416 */           pre = new PagoRolEmpleado();
/* 338:417 */           pagoRol.getListaPagoRolEmpleado().add(pre);
/* 339:    */         }
/* 340:    */         else
/* 341:    */         {
/* 342:419 */           pre.setEliminado(false);
/* 343:    */         }
/* 344:421 */         pre.setValorAPagar(BigDecimal.ZERO);
/* 345:422 */         pre.setIdOrganizacion(du.getIdOrganizacion());
/* 346:423 */         pre.setIdSucursal(du.getIdSucursal());
/* 347:424 */         pre.setDocumentoReferencia(pagoRol.getDocumento().getNombre());
/* 348:425 */         pre.setDepartamento(du.getEmpleado().getDepartamento());
/* 349:426 */         pre.setCentroCosto(du.getEmpleado().getCentroCosto());
/* 350:427 */         pre.setEmpleado(du.getEmpleado());
/* 351:428 */         pre.setValorAPagar(BigDecimal.ZERO);
/* 352:429 */         pre.setPagoRol(pagoRol);
/* 353:    */         
/* 354:431 */         pre.setDiasProceso(du.getDiasRealesTrabajados().intValue());
/* 355:432 */         pre.setDiasTrabajados(du.getDiasRealesTrabajados().intValue());
/* 356:    */         
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:437 */         String clave = du.getEmpleado().getIdEmpleado() + "~" + rubroUtilidad.getIdRubro();
/* 361:438 */         PagoRolEmpleadoRubro prer = (PagoRolEmpleadoRubro)mapaPRER.get(clave);
/* 362:439 */         if (prer == null)
/* 363:    */         {
/* 364:440 */           prer = new PagoRolEmpleadoRubro();
/* 365:441 */           pre.getListaPagoRolEmpleadoRubro().add(prer);
/* 366:    */         }
/* 367:    */         else
/* 368:    */         {
/* 369:443 */           prer.setEliminado(false);
/* 370:    */         }
/* 371:445 */         prer.setIdOrganizacion(du.getIdOrganizacion());
/* 372:446 */         prer.setIdSucursal(du.getIdSucursal());
/* 373:447 */         prer.setPagoRolEmpleado(pre);
/* 374:448 */         prer.setRubro(rubroUtilidad);
/* 375:449 */         prer.setValor(du.getValor10().add(du.getValor5()));
/* 376:450 */         prer.setIndicadorImpresionSobre(rubroUtilidad.isIndicadorImpresionSobre());
/* 377:451 */         prer.setIndicadorTiempo(rubroUtilidad.isIndicadorTiempo());
/* 378:452 */         prer.setIndicadorProvision(rubroUtilidad.isIndicadorProvision());
/* 379:453 */         prer.setIndicadorProvision(rubroUtilidad.isIndicadorProvision());
/* 380:454 */         pre.setValorAPagar(pre.getValorAPagar().add(prer.getValor()));
/* 381:455 */         if (du.getRetencionJudicial().compareTo(BigDecimal.ZERO) > 0)
/* 382:    */         {
/* 383:457 */           Rubro rubroRetencion = this.servicioRubroEmpleado.rubroEmpleadoRetencionJudicial(du.getEmpleado(), TipoRubroEnum.RETENCION_JUDICIAL);
/* 384:458 */           clave = du.getEmpleado().getIdEmpleado() + "~" + rubroRetencion.getIdRubro();
/* 385:459 */           prer = (PagoRolEmpleadoRubro)mapaPRER.get(clave);
/* 386:460 */           if (prer == null)
/* 387:    */           {
/* 388:461 */             prer = new PagoRolEmpleadoRubro();
/* 389:462 */             pre.getListaPagoRolEmpleadoRubro().add(prer);
/* 390:    */           }
/* 391:    */           else
/* 392:    */           {
/* 393:464 */             prer.setEliminado(false);
/* 394:    */           }
/* 395:466 */           prer.setIdOrganizacion(du.getIdOrganizacion());
/* 396:467 */           prer.setIdSucursal(du.getIdSucursal());
/* 397:468 */           prer.setPagoRolEmpleado(pre);
/* 398:469 */           prer.setRubro(rubroRetencion);
/* 399:470 */           prer.setValor(du.getRetencionJudicial());
/* 400:471 */           prer.setIndicadorImpresionSobre(rubroRetencion.isIndicadorImpresionSobre());
/* 401:472 */           prer.setIndicadorTiempo(rubroRetencion.isIndicadorTiempo());
/* 402:473 */           prer.setIndicadorProvision(rubroRetencion.isIndicadorProvision());
/* 403:474 */           prer.setIndicadorProvision(rubroRetencion.isIndicadorProvision());
/* 404:475 */           pre.setValorAPagar(pre.getValorAPagar().subtract(prer.getValor()));
/* 405:    */         }
/* 406:    */       }
/* 407:    */     }
/* 408:482 */     utilidad.setPagoRol(pagoRol);
/* 409:483 */     this.servicioPagoRol.guardar(pagoRol);
/* 410:484 */     this.utilidadDao.guardar(utilidad);
/* 411:    */   }
/* 412:    */   
/* 413:    */   public Rubro retornoRubroUtilidad(Utilidad utilidad, String mensaje)
/* 414:    */     throws AS2Exception
/* 415:    */   {
/* 416:490 */     int idRubroUtilidad = ParametrosSistema.getRubroUtilidad(utilidad.getIdOrganizacion()).intValue();
/* 417:491 */     Rubro rubroUtilidad = this.servicioRubro.buscarPorId(idRubroUtilidad);
/* 418:492 */     if (rubroUtilidad == null) {
/* 419:493 */       throw new AS2Exception("com.asinfo.as2.nomina.procesos.servicio.impl.ServicioUtilidadImpl.CONFIGURAR_RUBRO_UTILIDAD", new String[] { mensaje });
/* 420:    */     }
/* 421:496 */     return rubroUtilidad;
/* 422:    */   }
/* 423:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioUtilidadImpl
 * JD-Core Version:    0.7.0.1
 */