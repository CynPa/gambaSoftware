/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ContratoVentaDao;
/*   4:    */ import com.asinfo.as2.dao.ContratoVentaFacturaContratoVentaDao;
/*   5:    */ import com.asinfo.as2.dao.DetalleContratoVentaDao;
/*   6:    */ import com.asinfo.as2.dao.DetalleFacturaClienteDao;
/*   7:    */ import com.asinfo.as2.dao.DetalleValoresContratoVentaDao;
/*   8:    */ import com.asinfo.as2.dao.DetallesFacturaContratoVentaDao;
/*   9:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  12:    */ import com.asinfo.as2.entities.ContratoVenta;
/*  13:    */ import com.asinfo.as2.entities.ContratoVentaFacturaContratoVenta;
/*  14:    */ import com.asinfo.as2.entities.DetalleContratoVenta;
/*  15:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  16:    */ import com.asinfo.as2.entities.DetalleValoresContratoVenta;
/*  17:    */ import com.asinfo.as2.entities.DetallesFacturaContratoVenta;
/*  18:    */ import com.asinfo.as2.entities.Documento;
/*  19:    */ import com.asinfo.as2.entities.Empresa;
/*  20:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  21:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  22:    */ import com.asinfo.as2.entities.Organizacion;
/*  23:    */ import com.asinfo.as2.entities.Sucursal;
/*  24:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  25:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  26:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  27:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  28:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  29:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  30:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  31:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  32:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioContratoVenta;
/*  33:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDetalleFacturaContratoVenta;
/*  34:    */ import java.io.PrintStream;
/*  35:    */ import java.math.BigDecimal;
/*  36:    */ import java.math.RoundingMode;
/*  37:    */ import java.util.ArrayList;
/*  38:    */ import java.util.Calendar;
/*  39:    */ import java.util.Date;
/*  40:    */ import java.util.HashMap;
/*  41:    */ import java.util.Iterator;
/*  42:    */ import java.util.List;
/*  43:    */ import java.util.Map;
/*  44:    */ import java.util.TreeMap;
/*  45:    */ import javax.ejb.EJB;
/*  46:    */ import javax.ejb.Stateless;
/*  47:    */ import javax.ejb.TransactionManagement;
/*  48:    */ import javax.ejb.TransactionManagementType;
/*  49:    */ 
/*  50:    */ @Stateless
/*  51:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  52:    */ public class ServicioContratoVentaImpl
/*  53:    */   extends AbstractServicioAS2
/*  54:    */   implements ServicioContratoVenta
/*  55:    */ {
/*  56:    */   private static final long serialVersionUID = -9214403611253077141L;
/*  57:    */   @EJB
/*  58:    */   private ContratoVentaDao contratoVentaDao;
/*  59:    */   @EJB
/*  60:    */   private DetalleContratoVentaDao detalleContratoVentaDao;
/*  61:    */   @EJB
/*  62:    */   private DetallesFacturaContratoVentaDao detallesFacturaContratoVentaDao;
/*  63:    */   @EJB
/*  64:    */   private DetalleValoresContratoVentaDao detalleValoresContratoVentaDao;
/*  65:    */   @EJB
/*  66:    */   private ContratoVentaFacturaContratoVentaDao contratoVentaFacturaContratoVentaDao;
/*  67:    */   @EJB
/*  68:    */   private ServicioDocumento servicioDocumento;
/*  69:    */   @EJB
/*  70:    */   private ServicioSecuencia servicioSecuencia;
/*  71:    */   @EJB
/*  72:    */   private ServicioDetalleFacturaContratoVenta servicioDetalleFacturaContratoVenta;
/*  73:    */   @EJB
/*  74:    */   private FacturaClienteDao facturaClienteDao;
/*  75:    */   @EJB
/*  76:    */   private DetalleFacturaClienteDao detalleFacturaClienteDao;
/*  77:    */   
/*  78:    */   public void guardar(ContratoVenta contratoVenta)
/*  79:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  80:    */   {
/*  81: 91 */     validar(contratoVenta);
/*  82: 92 */     if ((contratoVenta.getNumero() == null) || (contratoVenta.getNumero().isEmpty())) {
/*  83: 93 */       cargarSecuencia(contratoVenta);
/*  84:    */     }
/*  85: 96 */     this.contratoVentaDao.guardar(contratoVenta);
/*  86: 98 */     for (DetalleContratoVenta dcv : contratoVenta.getListaDetalleContratoVenta()) {
/*  87: 99 */       if (!dcv.isEliminado()) {
/*  88:100 */         this.detalleContratoVentaDao.guardar(dcv);
/*  89:    */       }
/*  90:    */     }
/*  91:104 */     for (DetalleValoresContratoVenta dvcv : contratoVenta.getListaDetalleValoresContratoVenta()) {
/*  92:105 */       this.detalleValoresContratoVentaDao.guardar(dvcv);
/*  93:    */     }
/*  94:109 */     for (DetallesFacturaContratoVenta dfcv : contratoVenta.getListaDetallesFacturaContratoVenta()) {
/*  95:110 */       if (!dfcv.isIndicadorFacturado())
/*  96:    */       {
/*  97:113 */         dfcv.setIndicadorFacturado(contratoVenta.isIndicadorSaldoInicial());
/*  98:114 */         if (dfcv.isEliminado())
/*  99:    */         {
/* 100:115 */           for (ContratoVentaFacturaContratoVenta cvfcv : dfcv.getListaContratoVentaFacturaContratoVenta()) {
/* 101:116 */             this.contratoVentaFacturaContratoVentaDao.guardar(cvfcv);
/* 102:    */           }
/* 103:118 */           this.detallesFacturaContratoVentaDao.guardar(dfcv);
/* 104:    */         }
/* 105:    */         else
/* 106:    */         {
/* 107:121 */           this.detallesFacturaContratoVentaDao.guardar(dfcv);
/* 108:123 */           for (ContratoVentaFacturaContratoVenta cvfcv : dfcv.getListaContratoVentaFacturaContratoVenta()) {
/* 109:124 */             this.contratoVentaFacturaContratoVentaDao.guardar(cvfcv);
/* 110:    */           }
/* 111:    */         }
/* 112:    */       }
/* 113:    */     }
/* 114:129 */     for (DetalleContratoVenta dcv : contratoVenta.getListaDetalleContratoVenta()) {
/* 115:130 */       if (dcv.isEliminado()) {
/* 116:131 */         this.detalleContratoVentaDao.guardar(dcv);
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void eliminar(ContratoVenta contratoVenta)
/* 122:    */     throws ExcepcionAS2
/* 123:    */   {}
/* 124:    */   
/* 125:    */   public ContratoVenta buscarPorId(Integer id)
/* 126:    */     throws ExcepcionAS2
/* 127:    */   {
/* 128:146 */     return null;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<ContratoVenta> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 132:    */   {
/* 133:151 */     return this.contratoVentaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int contarPorCriterio(Map<String, String> filters)
/* 137:    */   {
/* 138:156 */     return this.contratoVentaDao.contarPorCriterio(filters);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public ContratoVenta totalizar(ContratoVenta contratoVenta)
/* 142:    */   {
/* 143:161 */     BigDecimal totalFacturado = BigDecimal.ZERO;
/* 144:162 */     Date fechaInicioCuotas = contratoVenta.getFecha();
/* 145:163 */     for (DetalleValoresContratoVenta dvcv : contratoVenta.getListaDetalleValoresContratoVenta()) {
/* 146:164 */       if (dvcv.getValorDevengar().compareTo(BigDecimal.ZERO) > 0)
/* 147:    */       {
/* 148:165 */         totalFacturado = totalFacturado.add(dvcv.getValorDevengar());
/* 149:166 */         dvcv.setValor(BigDecimal.ZERO);
/* 150:167 */         fechaInicioCuotas = dvcv.getFecha();
/* 151:    */       }
/* 152:    */       else
/* 153:    */       {
/* 154:170 */         dvcv.setEliminado(true);
/* 155:    */       }
/* 156:    */     }
/* 157:172 */     Calendar calfic = Calendar.getInstance();
/* 158:173 */     calfic.setTime(fechaInicioCuotas);
/* 159:174 */     calfic.add(5, 1);
/* 160:175 */     fechaInicioCuotas = calfic.getTime();
/* 161:    */     
/* 162:177 */     Map<Date, DetalleValoresContratoVenta> hmDetalleValoresContratoVenta = new TreeMap();
/* 163:178 */     for (DetalleContratoVenta dcv : contratoVenta.getListaDetalleContratoVenta()) {
/* 164:179 */       if (!dcv.isEliminado())
/* 165:    */       {
/* 166:180 */         if (fechaInicioCuotas.before(dcv.getFechaDesde())) {
/* 167:181 */           fechaInicioCuotas = dcv.getFechaDesde();
/* 168:    */         }
/* 169:183 */         BigDecimal valor = BigDecimal.ZERO;
/* 170:184 */         if (dcv.getCantidad().compareTo(BigDecimal.ZERO) != 0)
/* 171:    */         {
/* 172:185 */           valor = dcv.getPrecioLinea().subtract(totalFacturado);
/* 173:186 */           totalFacturado = BigDecimal.ZERO;
/* 174:187 */           dcv.setPrecio(dcv.getPrecioLinea().divide(dcv.getCantidad(), 6, RoundingMode.HALF_UP));
/* 175:    */         }
/* 176:    */         else
/* 177:    */         {
/* 178:189 */           dcv.setPrecio(BigDecimal.ZERO);
/* 179:    */         }
/* 180:192 */         int cantidadMeses = FuncionesUtiles.cantidadMeses(fechaInicioCuotas, dcv.getFechaHasta());
/* 181:    */         
/* 182:194 */         BigDecimal valorEsteMes = BigDecimal.ZERO;
/* 183:195 */         Date fechaFinEsteMes = null;
/* 184:197 */         if (cantidadMeses == 1)
/* 185:    */         {
/* 186:198 */           DetalleValoresContratoVenta detalleValoresContratoVenta = creaDetalleValoresContratoVenta(contratoVenta);
/* 187:199 */           valorEsteMes = valor;
/* 188:200 */           fechaFinEsteMes = FuncionesUtiles.getFechaFinMes(fechaInicioCuotas);
/* 189:    */           
/* 190:202 */           detalleValoresContratoVenta.setFecha(fechaFinEsteMes);
/* 191:203 */           detalleValoresContratoVenta.setValor(valorEsteMes);
/* 192:206 */           if (hmDetalleValoresContratoVenta.containsKey(fechaFinEsteMes))
/* 193:    */           {
/* 194:207 */             ((DetalleValoresContratoVenta)hmDetalleValoresContratoVenta.get(fechaFinEsteMes)).setValor(valorEsteMes.add(((DetalleValoresContratoVenta)hmDetalleValoresContratoVenta.get(fechaFinEsteMes)).getValor()));
/* 195:    */           }
/* 196:    */           else
/* 197:    */           {
/* 198:210 */             detalleValoresContratoVenta.setFecha(fechaFinEsteMes);
/* 199:211 */             detalleValoresContratoVenta.setValor(valorEsteMes);
/* 200:212 */             hmDetalleValoresContratoVenta.put(fechaFinEsteMes, detalleValoresContratoVenta);
/* 201:    */           }
/* 202:    */         }
/* 203:    */         else
/* 204:    */         {
/* 205:216 */           BigDecimal proporcionalPrimero = FuncionesUtiles.getProporcianalDiasPosterioresVSMes(fechaInicioCuotas);
/* 206:    */           
/* 207:218 */           BigDecimal totalMeses = FuncionesUtiles.cantidadMesesDecimales(fechaInicioCuotas, dcv.getFechaHasta());
/* 208:    */           
/* 209:    */ 
/* 210:221 */           BigDecimal valorMensual = valor.divide(totalMeses, 2, RoundingMode.HALF_UP);
/* 211:    */           
/* 212:223 */           BigDecimal valorAsignadoAcumulado = BigDecimal.ZERO;
/* 213:224 */           for (int i = 0; i < cantidadMeses; i++)
/* 214:    */           {
/* 215:225 */             DetalleValoresContratoVenta detalleValoresContratoVenta = creaDetalleValoresContratoVenta(contratoVenta);
/* 216:226 */             valorEsteMes = BigDecimal.ZERO;
/* 217:227 */             fechaFinEsteMes = FuncionesUtiles.getFechaFinMes(FuncionesUtiles.sumarFechaMeses(fechaInicioCuotas, i));
/* 218:229 */             if (i == 0) {
/* 219:230 */               valorEsteMes = valorMensual.multiply(proporcionalPrimero).setScale(2, RoundingMode.HALF_UP);
/* 220:231 */             } else if (i == cantidadMeses - 1) {
/* 221:232 */               valorEsteMes = valor.subtract(valorAsignadoAcumulado).setScale(2, RoundingMode.HALF_UP);
/* 222:    */             } else {
/* 223:234 */               valorEsteMes = valorMensual;
/* 224:    */             }
/* 225:236 */             valorAsignadoAcumulado = valorAsignadoAcumulado.add(valorEsteMes);
/* 226:239 */             if (hmDetalleValoresContratoVenta.containsKey(fechaFinEsteMes))
/* 227:    */             {
/* 228:240 */               ((DetalleValoresContratoVenta)hmDetalleValoresContratoVenta.get(fechaFinEsteMes)).setValor(valorEsteMes.add(((DetalleValoresContratoVenta)hmDetalleValoresContratoVenta.get(fechaFinEsteMes)).getValor()));
/* 229:    */             }
/* 230:    */             else
/* 231:    */             {
/* 232:243 */               detalleValoresContratoVenta.setFecha(fechaFinEsteMes);
/* 233:244 */               detalleValoresContratoVenta.setValor(valorEsteMes);
/* 234:245 */               hmDetalleValoresContratoVenta.put(fechaFinEsteMes, detalleValoresContratoVenta);
/* 235:    */             }
/* 236:    */           }
/* 237:    */         }
/* 238:    */       }
/* 239:    */     }
/* 240:252 */     contratoVenta.getListaDetalleValoresContratoVenta().addAll(hmDetalleValoresContratoVenta.values());
/* 241:253 */     return contratoVenta;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public ContratoVenta generarDetallesFacturaContratoVenta(ContratoVenta contratoVenta)
/* 245:    */     throws ExcepcionAS2
/* 246:    */   {
/* 247:262 */     int numeroCuotas = contratoVenta.getNumeroCuotas();
/* 248:263 */     int cuotasFacturadas = 0;
/* 249:264 */     Date fechaInicioCuotas = contratoVenta.getFecha();
/* 250:265 */     for (DetallesFacturaContratoVenta dfcv : contratoVenta.getListaDetallesFacturaContratoVenta()) {
/* 251:266 */       if (dfcv.isIndicadorFacturado())
/* 252:    */       {
/* 253:267 */         fechaInicioCuotas = dfcv.getFecha();
/* 254:268 */         cuotasFacturadas++;
/* 255:    */       }
/* 256:    */     }
/* 257:271 */     numeroCuotas -= cuotasFacturadas;
/* 258:272 */     if (numeroCuotas <= 0) {
/* 259:273 */       throw new ExcepcionAS2("msg_error_nuevo_numero_cuotas");
/* 260:    */     }
/* 261:276 */     for (DetallesFacturaContratoVenta dfcv : contratoVenta.getListaDetallesFacturaContratoVenta()) {
/* 262:277 */       if (!dfcv.isIndicadorFacturado())
/* 263:    */       {
/* 264:278 */         dfcv.setEliminado(true);
/* 265:279 */         for (ContratoVentaFacturaContratoVenta cvfcv : dfcv.getListaContratoVentaFacturaContratoVenta()) {
/* 266:280 */           cvfcv.setEliminado(true);
/* 267:    */         }
/* 268:    */       }
/* 269:    */     }
/* 270:285 */     int diferenciaFecha = FuncionesUtiles.diferenciasDeFechas(fechaInicioCuotas, contratoVenta.getFechaVencimiento());
/* 271:286 */     int fechaBefore = 0;
/* 272:287 */     for (int i = 0; i < numeroCuotas; i++)
/* 273:    */     {
/* 274:288 */       fechaBefore += diferenciaFecha / numeroCuotas;
/* 275:    */       
/* 276:    */ 
/* 277:291 */       DetallesFacturaContratoVenta detallesFacturaContratoVenta = new DetallesFacturaContratoVenta();
/* 278:292 */       detallesFacturaContratoVenta.setIdOrganizacion(contratoVenta.getIdOrganizacion());
/* 279:293 */       detallesFacturaContratoVenta.setIdSucursal(contratoVenta.getSucursal().getIdSucursal());
/* 280:294 */       detallesFacturaContratoVenta.setContratoVenta(contratoVenta);
/* 281:295 */       detallesFacturaContratoVenta.setFecha(FuncionesUtiles.sumarFechaDiasMeses(fechaInicioCuotas, fechaBefore));
/* 282:296 */       detallesFacturaContratoVenta.setValor(BigDecimal.ZERO);
/* 283:297 */       detallesFacturaContratoVenta.setIndicadorFacturado(false);
/* 284:298 */       detallesFacturaContratoVenta.setNumeroCuota(Integer.valueOf(i + 1));
/* 285:299 */       detallesFacturaContratoVenta.setListaContratoVentaFacturaContratoVenta(new ArrayList());
/* 286:302 */       for (DetalleContratoVenta dcv : contratoVenta.getListaDetalleContratoVenta()) {
/* 287:303 */         if (!dcv.isEliminado())
/* 288:    */         {
/* 289:304 */           ContratoVentaFacturaContratoVenta contratoVentaFacturaContratoVenta = new ContratoVentaFacturaContratoVenta();
/* 290:305 */           contratoVentaFacturaContratoVenta.setIdOrganizacion(dcv.getIdOrganizacion());
/* 291:306 */           contratoVentaFacturaContratoVenta.setIdSucursal(dcv.getIdSucursal());
/* 292:307 */           contratoVentaFacturaContratoVenta.setValor(BigDecimal.ZERO);
/* 293:308 */           contratoVentaFacturaContratoVenta.setValorTotal(dcv.getPrecioLinea());
/* 294:309 */           contratoVentaFacturaContratoVenta.setDetalleContratoVenta(dcv);
/* 295:310 */           contratoVentaFacturaContratoVenta.setDetallesFacturaContratoVenta(detallesFacturaContratoVenta);
/* 296:    */           
/* 297:312 */           detallesFacturaContratoVenta.getListaContratoVentaFacturaContratoVenta().add(contratoVentaFacturaContratoVenta);
/* 298:    */         }
/* 299:    */       }
/* 300:315 */       contratoVenta.getListaDetallesFacturaContratoVenta().add(detallesFacturaContratoVenta);
/* 301:    */     }
/* 302:318 */     return contratoVenta;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public DetalleValoresContratoVenta creaDetalleValoresContratoVenta(ContratoVenta contratoVenta)
/* 306:    */   {
/* 307:325 */     DetalleValoresContratoVenta detalleValoresContratoVenta = new DetalleValoresContratoVenta();
/* 308:326 */     detalleValoresContratoVenta.setIdOrganizacion(contratoVenta.getIdOrganizacion());
/* 309:327 */     detalleValoresContratoVenta.setIdSucursal(contratoVenta.getSucursal().getIdSucursal());
/* 310:328 */     detalleValoresContratoVenta.setContratoVenta(contratoVenta);
/* 311:329 */     detalleValoresContratoVenta.setIndicadorDevengado(false);
/* 312:330 */     detalleValoresContratoVenta.setIndicadorFacturado(Boolean.valueOf(false));
/* 313:331 */     detalleValoresContratoVenta.setIndicadorContabilizado(Boolean.valueOf(false));
/* 314:    */     
/* 315:333 */     return detalleValoresContratoVenta;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public ContratoVenta cargarSecuencia(ContratoVenta contratoVenta)
/* 319:    */     throws ExcepcionAS2
/* 320:    */   {
/* 321:345 */     String numero = this.servicioSecuencia.obtenerSecuencia(contratoVenta.getDocumento().getSecuencia(), contratoVenta.getFecha());
/* 322:346 */     this.servicioSecuencia.actualizarSecuencia(contratoVenta.getDocumento().getSecuencia(), numero);
/* 323:347 */     contratoVenta.setNumero(numero);
/* 324:    */     
/* 325:349 */     return contratoVenta;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public ContratoVenta cargarDetalle(ContratoVenta contratoVenta)
/* 329:    */   {
/* 330:359 */     return this.contratoVentaDao.cargarDetalle(contratoVenta);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public List getContratoVenta(ContratoVenta contratoVenta, Organizacion organizacion, Sucursal sucursal)
/* 334:    */     throws ExcepcionAS2
/* 335:    */   {
/* 336:365 */     return this.contratoVentaDao.getContratoVenta(contratoVenta, organizacion, sucursal);
/* 337:    */   }
/* 338:    */   
/* 339:    */   public List getContratoVentaDevengado(InterfazContableProceso interfazContableProceso, Organizacion organizacion, Sucursal sucursal)
/* 340:    */     throws ExcepcionAS2
/* 341:    */   {
/* 342:371 */     return this.contratoVentaDao.getContratoVentaDevengado(interfazContableProceso, organizacion, sucursal);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void esEditable(ContratoVenta contratoVenta)
/* 346:    */     throws ExcepcionAS2
/* 347:    */   {
/* 348:376 */     if ((contratoVenta.getEstado().equals(Estado.ANULADO)) || (contratoVenta.getEstado().equals(Estado.CERRADO))) {
/* 349:377 */       throw new ExcepcionAS2("msg_accion_no_permitida");
/* 350:    */     }
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void esAnulable(ContratoVenta contratoVenta)
/* 354:    */     throws ExcepcionAS2
/* 355:    */   {
/* 356:383 */     esEditable(contratoVenta);
/* 357:384 */     ContratoVenta cv = this.contratoVentaDao.cargarDetalle(contratoVenta);
/* 358:385 */     for (DetallesFacturaContratoVenta dfcv : cv.getListaDetallesFacturaContratoVenta()) {
/* 359:386 */       if (dfcv.isIndicadorFacturado() == true) {
/* 360:387 */         throw new ExcepcionAS2("msg_accion_no_permitida");
/* 361:    */       }
/* 362:    */     }
/* 363:    */   }
/* 364:    */   
/* 365:    */   public List<Object[]> getValoresDevengados(Date fecha, Date fechaHasta, Empresa empresa, Organizacion organizacion, Sucursal sucursal, boolean fechaFactura)
/* 366:    */     throws ExcepcionAS2
/* 367:    */   {
/* 368:395 */     return this.contratoVentaDao.getValoresDevengados(fecha, fechaHasta, empresa, organizacion, sucursal, fechaFactura);
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void validar(ContratoVenta contratoVenta)
/* 372:    */     throws ExcepcionAS2
/* 373:    */   {
/* 374:403 */     BigDecimal totalDetalle = BigDecimal.ZERO;
/* 375:406 */     for (DetallesFacturaContratoVenta dfcv : contratoVenta.getListaDetallesFacturaContratoVenta()) {
/* 376:407 */       if (contratoVenta.isIndicadorSaldoInicial()) {
/* 377:408 */         totalDetalle = contratoVenta.getTotalContratoVenta();
/* 378:410 */       } else if (!dfcv.isEliminado()) {
/* 379:411 */         for (ContratoVentaFacturaContratoVenta cvfcv : dfcv.getListaContratoVentaFacturaContratoVenta()) {
/* 380:412 */           totalDetalle = totalDetalle.add(cvfcv.getValor());
/* 381:    */         }
/* 382:    */       }
/* 383:    */     }
/* 384:418 */     if ((!contratoVenta.isIndicadorSaldoInicial()) && (contratoVenta.getTotalContratoVenta().compareTo(totalDetalle) != 0)) {
/* 385:419 */       throw new ExcepcionAS2("msg_error_diferencia_contrato_venta");
/* 386:    */     }
/* 387:422 */     totalDetalle = BigDecimal.ZERO;
/* 388:423 */     for (DetalleValoresContratoVenta dvcv : contratoVenta.getListaDetalleValoresContratoVenta()) {
/* 389:424 */       if (!dvcv.isEliminado()) {
/* 390:425 */         totalDetalle = totalDetalle.add(dvcv.getValor()).add(dvcv.getValorDevengar());
/* 391:    */       }
/* 392:    */     }
/* 393:429 */     System.out.println(contratoVenta.getTotalContratoVenta() + " " + totalDetalle);
/* 394:430 */     if (contratoVenta.getTotalContratoVenta().compareTo(totalDetalle) != 0) {
/* 395:432 */       throw new ExcepcionAS2("msg_error_diferencia_contrato_venta");
/* 396:    */     }
/* 397:    */   }
/* 398:    */   
/* 399:    */   public List contratoVentaFacturadoVSNoFacturado(ContratoVenta contratoVenta, Organizacion organizacion, Sucursal sucursal)
/* 400:    */     throws ExcepcionAS2
/* 401:    */   {
/* 402:449 */     return this.contratoVentaDao.contratoVentaFacturadoVSNoFacturado(contratoVenta, organizacion, sucursal);
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void procesarSaldoInicial(ContratoVenta contratoVenta)
/* 406:    */   {
/* 407:454 */     contratoVenta = cargarDetalle(contratoVenta);
/* 408:455 */     contratoVenta.setEstado(Estado.EMITIDO_CONTRATO);
/* 409:456 */     for (DetalleValoresContratoVenta dvcv : contratoVenta.getListaDetalleValoresContratoVenta()) {
/* 410:457 */       if (!dvcv.isIndicadorDevengado())
/* 411:    */       {
/* 412:458 */         dvcv.setValorDevengar(dvcv.getValor());
/* 413:459 */         dvcv.setValor(BigDecimal.ZERO);
/* 414:460 */         dvcv.setIndicadorDevengado(true);
/* 415:461 */         dvcv.setIndicadorFacturado(Boolean.valueOf(true));
/* 416:462 */         this.detalleValoresContratoVentaDao.guardar(dvcv);
/* 417:    */       }
/* 418:    */     }
/* 419:465 */     this.contratoVentaDao.guardar(contratoVenta);
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void liberarValoresDevengados(FacturaCliente facturaCliente)
/* 423:    */     throws ExcepcionAS2Ventas
/* 424:    */   {
/* 425:470 */     for (Iterator localIterator = facturaCliente.getListaDetalleFacturaCliente().iterator(); localIterator.hasNext();)
/* 426:    */     {
/* 427:470 */       dfc = (DetalleFacturaCliente)localIterator.next();
/* 428:471 */       if ((dfc.getContratoVentaFacturaContratoVenta() != null) && (dfc.getContratoVentaFacturaContratoVenta().getDetallesFacturaContratoVenta() != null))
/* 429:    */       {
/* 430:472 */         dfc.getContratoVentaFacturaContratoVenta().getDetallesFacturaContratoVenta().setIndicadorFacturado(false);
/* 431:    */         
/* 432:    */ 
/* 433:475 */         this.detallesFacturaContratoVentaDao.guardar(dfc.getContratoVentaFacturaContratoVenta().getDetallesFacturaContratoVenta());
/* 434:476 */         dfc.setContratoVentaFacturaContratoVenta(null);
/* 435:    */         
/* 436:    */ 
/* 437:479 */         this.detalleFacturaClienteDao.guardar(dfc);
/* 438:    */       }
/* 439:    */     }
/* 440:    */     DetalleFacturaCliente dfc;
/* 441:483 */     Object listaDetalleValoresContratoVenta = obtenerListaDetalleValoresContratoVentaPorFactura(facturaCliente);
/* 442:484 */     for (DetalleValoresContratoVenta detalleValoresContratoVenta : (List)listaDetalleValoresContratoVenta) {
/* 443:485 */       if (!detalleValoresContratoVenta.getIndicadorContabilizado().booleanValue())
/* 444:    */       {
/* 445:486 */         if (facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.FACTURA_CLIENTE))
/* 446:    */         {
/* 447:487 */           detalleValoresContratoVenta.setValor(detalleValoresContratoVenta.getValorDevengar());
/* 448:488 */           detalleValoresContratoVenta.setValorDevengar(BigDecimal.ZERO);
/* 449:489 */           detalleValoresContratoVenta.setIndicadorFacturado(Boolean.valueOf(false));
/* 450:490 */           detalleValoresContratoVenta.setIndicadorDevengado(false);
/* 451:491 */           detalleValoresContratoVenta.setFacturaCliente(null);
/* 452:    */         }
/* 453:493 */         if ((facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.NOTA_CREDITO_CLIENTE)) || (facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.DEVOLUCION_CLIENTE))) {
/* 454:494 */           detalleValoresContratoVenta.setEliminado(true);
/* 455:    */         }
/* 456:496 */         this.detalleValoresContratoVentaDao.guardar(detalleValoresContratoVenta);
/* 457:    */       }
/* 458:    */       else
/* 459:    */       {
/* 460:498 */         throw new ExcepcionAS2Ventas("msg_accion_no_permitida", " DEVENGADO " + Estado.CONTABILIZADO);
/* 461:    */       }
/* 462:    */     }
/* 463:    */   }
/* 464:    */   
/* 465:    */   public List<DetalleValoresContratoVenta> obtenerListaDetalleValoresContratoVentaPorFactura(FacturaCliente facturaCliente)
/* 466:    */   {
/* 467:506 */     return this.detalleValoresContratoVentaDao.obtenerListaDetalleValoresContratoVentaPorFactura(facturaCliente);
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void generarDevengadosNotaCredito(FacturaCliente notaCreditoCliente)
/* 471:    */   {
/* 472:511 */     Map<Integer, DetallesFacturaContratoVenta> mapaDetallesFacturaContratoVenta = new HashMap();
/* 473:512 */     Map<Integer, BigDecimal> mapaValoresDetallesFacturaContratoVenta = new HashMap();
/* 474:513 */     for (DetalleFacturaCliente detalleNotaCredito : notaCreditoCliente.getListaDetalleFacturaCliente()) {
/* 475:514 */       if ((detalleNotaCredito.getDetalleFacturaClientePadre() != null) && (detalleNotaCredito.getDetalleFacturaClientePadre().getContratoVentaFacturaContratoVenta() != null))
/* 476:    */       {
/* 477:515 */         DetalleFacturaCliente detallefacturaCliente = detalleNotaCredito.getDetalleFacturaClientePadre();
/* 478:516 */         ContratoVentaFacturaContratoVenta cvfcv = detallefacturaCliente.getContratoVentaFacturaContratoVenta();
/* 479:517 */         detalleNotaCredito.setContratoVentaFacturaContratoVenta(cvfcv);
/* 480:    */         
/* 481:519 */         mapaDetallesFacturaContratoVenta.put(Integer.valueOf(cvfcv.getDetallesFacturaContratoVenta().getId()), cvfcv.getDetallesFacturaContratoVenta());
/* 482:    */         
/* 483:521 */         BigDecimal valor = (BigDecimal)mapaValoresDetallesFacturaContratoVenta.get(Integer.valueOf(cvfcv.getDetallesFacturaContratoVenta().getId()));
/* 484:522 */         if (valor == null) {
/* 485:523 */           valor = BigDecimal.ZERO;
/* 486:    */         }
/* 487:525 */         valor = valor.add(detalleNotaCredito.getPrecioLinea());
/* 488:526 */         mapaValoresDetallesFacturaContratoVenta.put(Integer.valueOf(cvfcv.getDetallesFacturaContratoVenta().getId()), valor);
/* 489:    */         
/* 490:528 */         System.out.println("valor:" + valor);
/* 491:    */       }
/* 492:    */     }
/* 493:531 */     for (DetallesFacturaContratoVenta dfcv : mapaDetallesFacturaContratoVenta.values()) {
/* 494:532 */       this.servicioDetalleFacturaContratoVenta.generarValoresDevengadosNotaCredito(notaCreditoCliente, dfcv, ((BigDecimal)mapaValoresDetallesFacturaContratoVenta.get(Integer.valueOf(dfcv.getId()))).setScale(2, RoundingMode.HALF_UP));
/* 495:    */     }
/* 496:    */   }
/* 497:    */   
/* 498:    */   public ContratoVenta copiarContratoVenta(ContratoVenta contratoVenta)
/* 499:    */     throws ExcepcionAS2
/* 500:    */   {
/* 501:539 */     contratoVenta.setIdContratoVenta(0);
/* 502:540 */     contratoVenta.setNumero("");
/* 503:541 */     contratoVenta.setEstado(Estado.ELABORADO);
/* 504:542 */     contratoVenta.setCuotasFacturadas(false);
/* 505:544 */     for (DetalleContratoVenta dcv : contratoVenta.getListaDetalleContratoVenta())
/* 506:    */     {
/* 507:545 */       dcv.setIdDetalleContratoVenta(0);
/* 508:546 */       dcv.setContratoVenta(contratoVenta);
/* 509:    */     }
/* 510:548 */     for (DetalleValoresContratoVenta dvcv : contratoVenta.getListaDetalleValoresContratoVenta())
/* 511:    */     {
/* 512:549 */       dvcv.setIdDetalleValoresContratoVenta(0);
/* 513:550 */       dvcv.setContratoVenta(contratoVenta);
/* 514:551 */       dvcv.setIndicadorFacturado(Boolean.valueOf(false));
/* 515:552 */       dvcv.setComprobante(null);
/* 516:553 */       dvcv.setFacturaCliente(null);
/* 517:554 */       dvcv.setValorDevengar(BigDecimal.ZERO);
/* 518:    */     }
/* 519:556 */     for (??? = contratoVenta.getListaDetallesFacturaContratoVenta().iterator(); ???.hasNext();)
/* 520:    */     {
/* 521:556 */       dfcv = (DetallesFacturaContratoVenta)???.next();
/* 522:557 */       dfcv.setIdDetallesFacturaContratoVenta(0);
/* 523:558 */       dfcv.setContratoVenta(contratoVenta);
/* 524:559 */       dfcv.setIndicadorFacturado(false);
/* 525:561 */       for (ContratoVentaFacturaContratoVenta cvfcv : dfcv.getListaContratoVentaFacturaContratoVenta())
/* 526:    */       {
/* 527:562 */         cvfcv.setIdContratoVentaFacturaContratoVenta(0);
/* 528:563 */         cvfcv.setDetallesFacturaContratoVenta(dfcv);
/* 529:    */       }
/* 530:    */     }
/* 531:    */     DetallesFacturaContratoVenta dfcv;
/* 532:567 */     contratoVenta = totalizar(contratoVenta);
/* 533:    */     
/* 534:    */ 
/* 535:    */ 
/* 536:571 */     return contratoVenta;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public HashMap<String, String> getContratosEnFactura(int idOrganizacion, int idFacturaCliente)
/* 540:    */   {
/* 541:576 */     return this.contratoVentaDao.getContratosEnFactura(idOrganizacion, idFacturaCliente);
/* 542:    */   }
/* 543:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioContratoVentaImpl
 * JD-Core Version:    0.7.0.1
 */