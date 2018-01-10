/*   1:    */ package com.asinfo.as2.financiero.cobros.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.clases.ReporteAnticipoCliente;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   7:    */ import com.asinfo.as2.dao.AnticipoClienteDao;
/*   8:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  10:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*  11:    */ import com.asinfo.as2.entities.Asiento;
/*  12:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  13:    */ import com.asinfo.as2.entities.Cobro;
/*  14:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*  15:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  16:    */ import com.asinfo.as2.entities.CuentaContable;
/*  17:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  18:    */ import com.asinfo.as2.entities.Documento;
/*  19:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  20:    */ import com.asinfo.as2.entities.Empresa;
/*  21:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  22:    */ import com.asinfo.as2.entities.FormaPago;
/*  23:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  24:    */ import com.asinfo.as2.entities.Organizacion;
/*  25:    */ import com.asinfo.as2.entities.Sucursal;
/*  26:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  27:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  28:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  29:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  30:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  31:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  32:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*  33:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  34:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  35:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  36:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  37:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  38:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  39:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  40:    */ import com.asinfo.as2.util.AppUtil;
/*  41:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  42:    */ import java.math.BigDecimal;
/*  43:    */ import java.util.ArrayList;
/*  44:    */ import java.util.Date;
/*  45:    */ import java.util.HashMap;
/*  46:    */ import java.util.List;
/*  47:    */ import java.util.Map;
/*  48:    */ import javax.ejb.EJB;
/*  49:    */ import javax.ejb.SessionContext;
/*  50:    */ import javax.ejb.Stateless;
/*  51:    */ import javax.ejb.TransactionAttribute;
/*  52:    */ import javax.ejb.TransactionAttributeType;
/*  53:    */ import javax.ejb.TransactionManagement;
/*  54:    */ import javax.ejb.TransactionManagementType;
/*  55:    */ 
/*  56:    */ @Stateless
/*  57:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  58:    */ public class ServicioAnticipoClienteImpl
/*  59:    */   extends AbstractServicioAS2Financiero
/*  60:    */   implements ServicioAnticipoCliente
/*  61:    */ {
/*  62:    */   private static final long serialVersionUID = -8066073045427883894L;
/*  63:    */   @EJB
/*  64:    */   private AnticipoClienteDao anticipoClienteDao;
/*  65:    */   @EJB
/*  66:    */   private FacturaClienteDao facturaClienteDao;
/*  67:    */   @EJB
/*  68:    */   private ServicioSecuencia servicioSecuencia;
/*  69:    */   @EJB
/*  70:    */   private ServicioPeriodo servicioPeriodo;
/*  71:    */   @EJB
/*  72:    */   private ServicioAsiento servicioAsiento;
/*  73:    */   @EJB
/*  74:    */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  75:    */   @EJB
/*  76:    */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  77:    */   @EJB
/*  78:    */   private ServicioSucursal servicioSucursal;
/*  79:    */   @EJB
/*  80:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  81:    */   
/*  82:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  83:    */   public void guardar(AnticipoCliente anticipoCliente)
/*  84:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:108 */       validar(anticipoCliente);
/*  89:    */       
/*  90:110 */       cargarSecuencia(anticipoCliente);
/*  91:    */       
/*  92:    */ 
/*  93:113 */       anticipoCliente.setSaldo(anticipoCliente.getValor());
/*  94:114 */       if (anticipoCliente.getIndicadorContabilizar() == null) {
/*  95:115 */         anticipoCliente.setIndicadorContabilizar(Boolean.valueOf(anticipoCliente.getDocumento().isIndicadorContabilizar()));
/*  96:    */       }
/*  97:117 */       this.anticipoClienteDao.guardar(anticipoCliente);
/*  98:120 */       if ((!anticipoCliente.isIndicadorSaldoInicial()) && (anticipoCliente.getDocumento().getDocumentoBase() == DocumentoBase.ANTICIPO_CLIENTE)) {
/*  99:121 */         contabilizar(anticipoCliente);
/* 100:    */       }
/* 101:    */     }
/* 102:    */     catch (ExcepcionAS2Financiero e)
/* 103:    */     {
/* 104:125 */       this.context.setRollbackOnly();
/* 105:126 */       throw e;
/* 106:    */     }
/* 107:    */     catch (ExcepcionAS2 e)
/* 108:    */     {
/* 109:129 */       this.context.setRollbackOnly();
/* 110:130 */       throw e;
/* 111:    */     }
/* 112:    */     catch (Exception e)
/* 113:    */     {
/* 114:139 */       this.context.setRollbackOnly();
/* 115:140 */       throw new ExcepcionAS2(e);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 120:    */   public void guardarDevolucion(AnticipoCliente anticipoCliente)
/* 121:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:155 */       validaFechaDevolucionAnticipo(anticipoCliente.getFecha(), anticipoCliente.getFechaDevolucion());
/* 126:    */       
/* 127:157 */       validar(anticipoCliente);
/* 128:    */       
/* 129:159 */       BigDecimal saldoAnticipo = anticipoCliente.getSaldo();
/* 130:160 */       saldoAnticipo = saldoAnticipo.subtract(anticipoCliente.getValorDevolucion());
/* 131:161 */       anticipoCliente.setSaldo(saldoAnticipo);
/* 132:163 */       if (anticipoCliente.getSecuencia() != null) {
/* 133:164 */         this.servicioSecuencia.actualizarSecuencia(anticipoCliente.getSecuencia(), anticipoCliente.getDocumentoReferenciaDevolucion());
/* 134:    */       }
/* 135:166 */       this.anticipoClienteDao.guardar(anticipoCliente);
/* 136:    */       
/* 137:168 */       contabilizarDevolucion(anticipoCliente);
/* 138:    */     }
/* 139:    */     catch (ExcepcionAS2Financiero e)
/* 140:    */     {
/* 141:171 */       this.context.setRollbackOnly();
/* 142:172 */       throw e;
/* 143:    */     }
/* 144:    */     catch (ExcepcionAS2 e)
/* 145:    */     {
/* 146:175 */       this.context.setRollbackOnly();
/* 147:176 */       throw e;
/* 148:    */     }
/* 149:    */     catch (Exception e)
/* 150:    */     {
/* 151:179 */       this.context.setRollbackOnly();
/* 152:180 */       throw new ExcepcionAS2(e);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 157:    */   public void anularDevolverAnticipoCliente(AnticipoCliente anticipoCliente)
/* 158:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 159:    */   {
/* 160:    */     try
/* 161:    */     {
/* 162:191 */       Asiento asientoTemporal = anticipoCliente.getAsientoDevolucion();
/* 163:    */       
/* 164:193 */       BigDecimal saldoAnticipo = anticipoCliente.getSaldo();
/* 165:194 */       saldoAnticipo = saldoAnticipo.add(anticipoCliente.getValorDevolucion());
/* 166:195 */       anticipoCliente.setSaldo(saldoAnticipo);
/* 167:    */       
/* 168:197 */       anticipoCliente.setCuentaBancariaOrganizacionDevolucion(null);
/* 169:198 */       anticipoCliente.setDocumentoDevolucion(null);
/* 170:199 */       anticipoCliente.setFechaDevolucion(null);
/* 171:200 */       anticipoCliente.setValorDevolucion(BigDecimal.ZERO);
/* 172:201 */       anticipoCliente.setFormaPagoDevolucion(null);
/* 173:202 */       anticipoCliente.setAsientoDevolucion(null);
/* 174:203 */       anticipoCliente.setDocumentoReferenciaDevolucion("");
/* 175:204 */       anticipoCliente.setBeneficiarioDevolucion("");
/* 176:205 */       anticipoCliente.setDescripcionDevolucion("");
/* 177:    */       
/* 178:207 */       this.anticipoClienteDao.guardar(anticipoCliente);
/* 179:    */       
/* 180:209 */       asientoTemporal.setIndicadorAutomatico(false);
/* 181:210 */       this.servicioAsiento.anular(asientoTemporal);
/* 182:    */     }
/* 183:    */     catch (ExcepcionAS2Financiero e)
/* 184:    */     {
/* 185:213 */       this.context.setRollbackOnly();
/* 186:214 */       throw e;
/* 187:    */     }
/* 188:    */     catch (Exception e)
/* 189:    */     {
/* 190:217 */       this.context.setRollbackOnly();
/* 191:218 */       throw new ExcepcionAS2(e);
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void cargarSecuencia(AnticipoCliente anticipoCliente)
/* 196:    */     throws ExcepcionAS2
/* 197:    */   {
/* 198:230 */     if (anticipoCliente.getNumero().isEmpty())
/* 199:    */     {
/* 200:231 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(anticipoCliente.getDocumento().getIdDocumento(), anticipoCliente.getFecha());
/* 201:232 */       anticipoCliente.setNumero(numero);
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   private void validar(AnticipoCliente anticipoCliente)
/* 206:    */     throws ExcepcionAS2Financiero
/* 207:    */   {
/* 208:    */     DocumentoBase documentoBase;
/* 209:    */     DocumentoBase documentoBase;
/* 210:246 */     if (anticipoCliente.getDocumentoDevolucion() != null) {
/* 211:248 */       documentoBase = anticipoCliente.getDocumentoDevolucion().getDocumentoBase();
/* 212:    */     } else {
/* 213:251 */       documentoBase = anticipoCliente.getDocumento().getDocumentoBase();
/* 214:    */     }
/* 215:254 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$DocumentoBase[documentoBase.ordinal()])
/* 216:    */     {
/* 217:    */     case 1: 
/* 218:256 */       this.servicioPeriodo.buscarPorFecha(anticipoCliente.getFecha(), anticipoCliente.getIdOrganizacion(), documentoBase);
/* 219:257 */       break;
/* 220:    */     case 2: 
/* 221:260 */       this.servicioPeriodo.buscarPorFecha(anticipoCliente.getFechaDevolucion(), anticipoCliente.getIdOrganizacion(), documentoBase);
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void eliminar(AnticipoCliente anticipoCliente)
/* 226:    */   {
/* 227:272 */     this.anticipoClienteDao.eliminar(anticipoCliente);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public AnticipoCliente buscarPorId(int idAnticipoCliente)
/* 231:    */   {
/* 232:282 */     return (AnticipoCliente)this.anticipoClienteDao.buscarPorId(Integer.valueOf(idAnticipoCliente));
/* 233:    */   }
/* 234:    */   
/* 235:    */   public List<AnticipoCliente> obtenerAnticiposPendientes(int idEmpresa)
/* 236:    */   {
/* 237:292 */     return this.anticipoClienteDao.obtenerAnticiposPendientes(idEmpresa);
/* 238:    */   }
/* 239:    */   
/* 240:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 241:    */   public void anularAnticipoCliente(AnticipoCliente anticipoCliente, boolean verificaNotaCredito, boolean indicadorAnularDesdeCobro)
/* 242:    */     throws ExcepcionAS2
/* 243:    */   {
/* 244:    */     try
/* 245:    */     {
/* 246:301 */       esEditable(anticipoCliente, verificaNotaCredito, indicadorAnularDesdeCobro);
/* 247:    */       
/* 248:303 */       actualizarEstado(Integer.valueOf(anticipoCliente.getId()), Estado.ANULADO);
/* 249:305 */       if (anticipoCliente.getAsiento() != null)
/* 250:    */       {
/* 251:306 */         anticipoCliente.getAsiento().setIndicadorAutomatico(false);
/* 252:307 */         this.servicioAsiento.anular(anticipoCliente.getAsiento());
/* 253:    */       }
/* 254:    */     }
/* 255:    */     catch (ExcepcionAS2Financiero e)
/* 256:    */     {
/* 257:311 */       this.context.setRollbackOnly();
/* 258:312 */       throw e;
/* 259:    */     }
/* 260:    */     catch (Exception e)
/* 261:    */     {
/* 262:315 */       this.context.setRollbackOnly();
/* 263:316 */       throw new ExcepcionAS2(e);
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void anularAnticipoCliente(AnticipoCliente anticipoCliente)
/* 268:    */     throws ExcepcionAS2
/* 269:    */   {
/* 270:328 */     anularAnticipoCliente(anticipoCliente, true, false);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<AnticipoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 274:    */   {
/* 275:339 */     return this.anticipoClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void contabilizar(AnticipoCliente anticipoCliente)
/* 279:    */     throws ExcepcionAS2, AS2Exception
/* 280:    */   {
/* 281:349 */     if (anticipoCliente.getIndicadorContabilizar().booleanValue())
/* 282:    */     {
/* 283:350 */       Date fechaContabilizacion = anticipoCliente.getFecha();
/* 284:351 */       if (anticipoCliente.getDocumento().isIndicadorContabilizar())
/* 285:    */       {
/* 286:    */         Asiento asiento;
/* 287:    */         Asiento asiento;
/* 288:    */         int idOrganizacion;
/* 289:353 */         if (anticipoCliente.getAsiento() != null)
/* 290:    */         {
/* 291:354 */           asiento = this.servicioAsiento.cargarDetalle(anticipoCliente.getAsiento().getId());
/* 292:    */         }
/* 293:    */         else
/* 294:    */         {
/* 295:357 */           asiento = new Asiento();
/* 296:    */           
/* 297:359 */           idOrganizacion = anticipoCliente.getIdOrganizacion();
/* 298:360 */           asiento.setIdOrganizacion(idOrganizacion);
/* 299:361 */           asiento.setSucursal(anticipoCliente.getSucursal());
/* 300:363 */           if (anticipoCliente.getNotaCreditoCliente() != null) {
/* 301:364 */             asiento.setTipoAsiento(anticipoCliente.getNotaCreditoCliente().getDocumento().getTipoAsiento());
/* 302:    */           } else {
/* 303:366 */             asiento.setTipoAsiento(anticipoCliente.getDocumento().getTipoAsiento());
/* 304:    */           }
/* 305:    */         }
/* 306:369 */         for (DetalleAsiento dt : asiento.getListaDetalleAsiento()) {
/* 307:370 */           dt.setEliminado(true);
/* 308:    */         }
/* 309:373 */         String concepto = "";
/* 310:375 */         if (anticipoCliente.getNotaCreditoCliente() != null) {
/* 311:377 */           concepto = anticipoCliente.getNotaCreditoCliente().getDocumento().getNombre().trim() + " #" + anticipoCliente.getNotaCreditoCliente().getNumero() + " " + anticipoCliente.getNotaCreditoCliente().getDescripcion();
/* 312:    */         } else {
/* 313:380 */           concepto = anticipoCliente.getDocumento().getNombre().trim() + " #" + anticipoCliente.getNumero() + " " + anticipoCliente.getDescripcion();
/* 314:    */         }
/* 315:382 */         asiento.setConcepto(concepto);
/* 316:383 */         asiento.setFecha(fechaContabilizacion);
/* 317:384 */         asiento.setEstado(Estado.ELABORADO);
/* 318:385 */         asiento.setIndicadorAutomatico(true);
/* 319:387 */         if (anticipoCliente.getNotaCreditoCliente() == null)
/* 320:    */         {
/* 321:388 */           List<DetalleInterfazContable> listaDA = this.anticipoClienteDao.getDetalleFormaCobroAnticipoIC(anticipoCliente);
/* 322:    */           
/* 323:390 */           super.generarAsiento(asiento, listaDA, anticipoCliente.getDocumento());
/* 324:    */         }
/* 325:393 */         List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(anticipoCliente
/* 326:394 */           .getIdOrganizacion(), DocumentoBase.ANTICIPO_CLIENTE);
/* 327:    */         
/* 328:396 */         List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(anticipoCliente
/* 329:397 */           .getIdOrganizacion(), DocumentoBase.ANTICIPO_CLIENTE);
/* 330:    */         
/* 331:399 */         List<Integer> list = new ArrayList();
/* 332:400 */         List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 333:401 */         List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/* 334:    */         
/* 335:403 */         FacturaCliente notaCreditoCliente = anticipoCliente.getNotaCreditoCliente();
/* 336:    */         boolean notaCreditoPorDecuento;
/* 337:    */         List<DocumentoContabilizacion> listaDocConDIV;
/* 338:404 */         if (notaCreditoCliente != null)
/* 339:    */         {
/* 340:406 */           notaCreditoPorDecuento = notaCreditoCliente.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_CREDITO_CLIENTE;
/* 341:    */           
/* 342:408 */           list = new ArrayList();
/* 343:409 */           list.add(Integer.valueOf(notaCreditoCliente.getIdFacturaCliente()));
/* 344:410 */           DocumentoBase documentoBase = notaCreditoCliente.getDocumento().getDocumentoBase();
/* 345:411 */           listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(notaCreditoCliente.getIdOrganizacion(), documentoBase);
/* 346:    */           
/* 347:    */ 
/* 348:414 */           List<DocumentoContabilizacion> listaDocContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(notaCreditoCliente
/* 349:415 */             .getIdOrganizacion(), documentoBase);
/* 350:    */           
/* 351:    */ 
/* 352:    */ 
/* 353:419 */           List<DocumentoContabilizacion> listaDocContaFac = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(notaCreditoCliente
/* 354:420 */             .getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE, ProcesoContabilizacionEnum.IMPUESTO_VENTAS);
/* 355:421 */           if (notaCreditoCliente.getMontoIce().compareTo(BigDecimal.ZERO) > 0) {
/* 356:422 */             listaDocContaFac.addAll(this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(notaCreditoCliente.getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE, ProcesoContabilizacionEnum.ICE));
/* 357:    */           }
/* 358:425 */           if (!notaCreditoPorDecuento) {
/* 359:428 */             listaDocContaFac.addAll(this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(notaCreditoCliente.getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE, ProcesoContabilizacionEnum.DESCUENTO_VENTAS));
/* 360:    */           }
/* 361:432 */           if (notaCreditoCliente.getDescuentoImpuesto().compareTo(BigDecimal.ZERO) > 0)
/* 362:    */           {
/* 363:433 */             listaDocConDIV = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(notaCreditoCliente
/* 364:434 */               .getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE, ProcesoContabilizacionEnum.DESCUENTO_IMPUESTO_VENTAS);
/* 365:436 */             if (listaDocConDIV.size() == 0) {
/* 366:437 */               throw new ExcepcionAS2("no_existe_documento_contabilizacion_descuento_ventas");
/* 367:    */             }
/* 368:439 */             listaDocContaFac.addAll(listaDocConDIV);
/* 369:    */           }
/* 370:442 */           for (DocumentoContabilizacion documneto : listaDocContaFac) {
/* 371:443 */             documneto.setDebe(!documneto.isDebe());
/* 372:    */           }
/* 373:446 */           listaDocContabilizacion.addAll(listaDocContaFac);
/* 374:447 */           for (DocumentoContabilizacion documentoContabilizacion : listaDocContabilizacion)
/* 375:    */           {
/* 376:448 */             lista = this.facturaClienteDao.getInterfazNotaCreditoDimensiones(list, documentoContabilizacion.getProcesoContabilizacion(), notaCreditoPorDecuento);
/* 377:    */             
/* 378:    */ 
/* 379:451 */             listaDetalleAsiento.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 380:    */           }
/* 381:    */         }
/* 382:457 */         list = new ArrayList();
/* 383:458 */         lista = new ArrayList();
/* 384:459 */         list.add(Integer.valueOf(anticipoCliente.getIdAnticipoCliente()));
/* 385:460 */         for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 386:    */         {
/* 387:461 */           lista.addAll(this.anticipoClienteDao.getInterfazAnticipoClienteDimensiones(list));
/* 388:462 */           listaDetalleAsiento.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 389:    */         }
/* 390:466 */         asiento.getListaDetalleAsiento().addAll(listaDetalleAsiento);
/* 391:    */         
/* 392:468 */         ajustarDiferencias(asiento);
/* 393:    */         
/* 394:    */ 
/* 395:471 */         this.servicioAsiento.guardar(asiento);
/* 396:    */         
/* 397:    */ 
/* 398:474 */         anticipoCliente.setEstado(Estado.CONTABILIZADO);
/* 399:    */         
/* 400:476 */         anticipoCliente.setFechaContabilizacion(fechaContabilizacion);
/* 401:    */         
/* 402:    */ 
/* 403:479 */         anticipoCliente.setAsiento(asiento);
/* 404:480 */         this.anticipoClienteDao.guardar(anticipoCliente);
/* 405:    */       }
/* 406:    */     }
/* 407:    */   }
/* 408:    */   
/* 409:    */   private Asiento ajustarDiferencias(Asiento asiento)
/* 410:    */   {
/* 411:493 */     List<DetalleAsiento> lista = new ArrayList();
/* 412:494 */     for (DetalleAsiento detalle : asiento.getListaDetalleAsiento()) {
/* 413:495 */       if (!detalle.isEliminado()) {
/* 414:496 */         lista.add(detalle);
/* 415:    */       }
/* 416:    */     }
/* 417:500 */     BigDecimal totalDebe = BigDecimal.ZERO;
/* 418:501 */     BigDecimal totalHaber = BigDecimal.ZERO;
/* 419:503 */     for (DetalleAsiento detalle : lista) {
/* 420:504 */       if (!detalle.isEliminado())
/* 421:    */       {
/* 422:505 */         totalDebe = totalDebe.add(detalle.getDebe());
/* 423:506 */         totalHaber = totalHaber.add(detalle.getHaber());
/* 424:    */       }
/* 425:    */     }
/* 426:510 */     BigDecimal diferencia = totalDebe.subtract(totalHaber);
/* 427:512 */     if (diferencia.compareTo(BigDecimal.ZERO) != 0)
/* 428:    */     {
/* 429:514 */       DetalleAsiento detalleAsientoDebe = null;
/* 430:516 */       for (DetalleAsiento detalle : lista) {
/* 431:517 */         if ((!detalle.isEliminado()) && 
/* 432:518 */           (detalle.getDebe().compareTo(BigDecimal.ZERO) > 0) && (detalle.getDebe().compareTo(diferencia.abs()) >= 0))
/* 433:    */         {
/* 434:519 */           detalleAsientoDebe = detalle;
/* 435:520 */           break;
/* 436:    */         }
/* 437:    */       }
/* 438:525 */       if (diferencia.abs().compareTo(new BigDecimal(0.2D)) <= 0) {
/* 439:526 */         if (diferencia.compareTo(BigDecimal.ZERO) > 0) {
/* 440:527 */           detalleAsientoDebe.setDebe(detalleAsientoDebe.getDebe().subtract(diferencia.abs()));
/* 441:    */         } else {
/* 442:529 */           detalleAsientoDebe.setDebe(detalleAsientoDebe.getDebe().add(diferencia.abs()));
/* 443:    */         }
/* 444:    */       }
/* 445:    */     }
/* 446:537 */     return asiento;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void contabilizarDevolucion(AnticipoCliente anticipoCliente)
/* 450:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 451:    */   {
/* 452:543 */     if (anticipoCliente.getIndicadorContabilizar().booleanValue())
/* 453:    */     {
/* 454:544 */       Date fechaContabilizacion = anticipoCliente.getFechaDevolucion();
/* 455:545 */       int idAnticipoCliente = anticipoCliente.getId();
/* 456:    */       Asiento asiento;
/* 457:    */       Asiento asiento;
/* 458:    */       int idOrganizacion;
/* 459:547 */       if (anticipoCliente.getAsientoDevolucion() != null)
/* 460:    */       {
/* 461:548 */         asiento = this.servicioAsiento.cargarDetalle(anticipoCliente.getAsientoDevolucion().getId());
/* 462:    */       }
/* 463:    */       else
/* 464:    */       {
/* 465:551 */         asiento = new Asiento();
/* 466:    */         
/* 467:553 */         idOrganizacion = AppUtil.getOrganizacion() != null ? AppUtil.getOrganizacion().getId() : anticipoCliente.getIdOrganizacion();
/* 468:554 */         Sucursal sucursal = AppUtil.getSucursal() != null ? AppUtil.getSucursal() : this.servicioSucursal.buscarPorId(Integer.valueOf(anticipoCliente.getId()));
/* 469:    */         
/* 470:556 */         asiento.setIdOrganizacion(idOrganizacion);
/* 471:557 */         asiento.setSucursal(sucursal);
/* 472:    */         
/* 473:559 */         TipoAsiento tipoAsiento = anticipoCliente.getDocumentoDevolucion().getTipoAsiento();
/* 474:560 */         asiento.setTipoAsiento(tipoAsiento);
/* 475:    */       }
/* 476:562 */       for (DetalleAsiento dt : asiento.getListaDetalleAsiento()) {
/* 477:563 */         dt.setEliminado(true);
/* 478:    */       }
/* 479:566 */       String concepto = "";
/* 480:567 */       concepto = anticipoCliente.getDocumentoDevolucion().getNombre().trim() + " #" + anticipoCliente.getNumero();
/* 481:568 */       asiento.setConcepto(concepto);
/* 482:569 */       asiento.setFecha(fechaContabilizacion);
/* 483:570 */       List<DetalleInterfazContable> listaDA = new ArrayList();
/* 484:    */       String referencia4;
/* 485:    */       BigDecimal valor;
/* 486:572 */       if (anticipoCliente.getCuentaBancariaOrganizacionDevolucion().isIndicadorCruce())
/* 487:    */       {
/* 488:573 */         int idCuentaContableCruce = anticipoCliente.getCuentaContableDevolucionCruce().getId();
/* 489:574 */         String referencia1 = anticipoCliente.getEmpresa().getNombreFiscal();
/* 490:575 */         String referencia2 = anticipoCliente.getDocumentoDevolucion().getNombre() + " #" + anticipoCliente.getNumero();
/* 491:576 */         String referencia3 = anticipoCliente.getDocumentoReferenciaDevolucion();
/* 492:577 */         referencia4 = anticipoCliente.getBeneficiarioDevolucion();
/* 493:578 */         int idFormaPago = anticipoCliente.getFormaPagoDevolucion().getId();
/* 494:579 */         valor = anticipoCliente.getValorDevolucion().negate();
/* 495:    */         
/* 496:581 */         DetalleInterfazContable detalleInterfazContable = new DetalleInterfazContable(idCuentaContableCruce, referencia1, referencia2, referencia3, referencia4, Integer.valueOf(idFormaPago), valor);
/* 497:582 */         List<DetalleInterfazContable> lista = new ArrayList();
/* 498:583 */         lista.add(detalleInterfazContable);
/* 499:584 */         listaDA.addAll(lista);
/* 500:    */       }
/* 501:    */       else
/* 502:    */       {
/* 503:586 */         listaDA.addAll(this.anticipoClienteDao.getDetalleFormaCobroDevolucionAnticipoIC(idAnticipoCliente));
/* 504:    */       }
/* 505:590 */       super.generarAsiento(asiento, listaDA, anticipoCliente.getDocumentoDevolucion());
/* 506:    */       
/* 507:    */ 
/* 508:593 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(anticipoCliente
/* 509:594 */         .getIdOrganizacion(), DocumentoBase.ANTICIPO_CLIENTE);
/* 510:    */       
/* 511:596 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(anticipoCliente
/* 512:597 */         .getIdOrganizacion(), DocumentoBase.ANTICIPO_CLIENTE);
/* 513:    */       
/* 514:599 */       List<Integer> list = new ArrayList();
/* 515:600 */       list.add(Integer.valueOf(anticipoCliente.getIdAnticipoCliente()));
/* 516:601 */       List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 517:602 */       for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 518:    */       {
/* 519:603 */         for (DetalleInterfazContableProceso detalleInterfazContableProceso : this.anticipoClienteDao.getInterfazAnticipoClienteDimensiones(list))
/* 520:    */         {
/* 521:604 */           detalleInterfazContableProceso.setValor(anticipoCliente.getValorDevolucion());
/* 522:605 */           lista.add(detalleInterfazContableProceso);
/* 523:    */         }
/* 524:607 */         asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 525:608 */           .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true));
/* 526:    */       }
/* 527:612 */       this.servicioAsiento.guardar(asiento);
/* 528:    */       
/* 529:    */ 
/* 530:615 */       anticipoCliente.setAsientoDevolucion(asiento);
/* 531:616 */       this.anticipoClienteDao.guardar(anticipoCliente);
/* 532:    */     }
/* 533:    */   }
/* 534:    */   
/* 535:    */   public AnticipoCliente generaAnticipoDevolucionCliente(Empresa empresa, MovimientoInventario movimientoInventario, FacturaCliente facturaCliente, AnticipoCliente anticipoCliente)
/* 536:    */   {
/* 537:631 */     return anticipoCliente;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public AnticipoCliente cargarDetalle(int idAnticipoCliente)
/* 541:    */   {
/* 542:643 */     return this.anticipoClienteDao.cargarDetalle(idAnticipoCliente);
/* 543:    */   }
/* 544:    */   
/* 545:    */   public BigDecimal obtenerSaldoAnticipo(int idCliente, Date fechaHasta)
/* 546:    */   {
/* 547:655 */     return this.anticipoClienteDao.obtenerSaldoAnticipo(idCliente, fechaHasta);
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void esEditable(AnticipoCliente anticipoCliente, boolean verificaNotaCredito, boolean indicadorAnularDesdeCobro)
/* 551:    */     throws ExcepcionAS2Financiero
/* 552:    */   {
/* 553:666 */     if ((!indicadorAnularDesdeCobro) && (anticipoCliente.getCobro() != null)) {
/* 554:667 */       throw new ExcepcionAS2Financiero("msg_error_anular");
/* 555:    */     }
/* 556:671 */     this.servicioPeriodo.buscarPorFecha(anticipoCliente.getFecha(), anticipoCliente.getIdOrganizacion(), anticipoCliente.getDocumento().getDocumentoBase());
/* 557:672 */     if (anticipoCliente.getSaldo().compareTo(anticipoCliente.getValor()) != 0) {
/* 558:673 */       throw new ExcepcionAS2Financiero("msg_error_anular_anticipo_proveedor_valor");
/* 559:    */     }
/* 560:675 */     if ((verificaNotaCredito) && (anticipoCliente.getNotaCreditoCliente() != null)) {
/* 561:676 */       throw new ExcepcionAS2Financiero("msg_error_existe_nota_credito_atada_a_anticipo");
/* 562:    */     }
/* 563:678 */     if ((anticipoCliente.getAsiento() != null) && (anticipoCliente.getAsiento().getEstado() == Estado.REVISADO)) {
/* 564:679 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 565:    */     }
/* 566:681 */     if (anticipoCliente.getEstado() == Estado.ANULADO) {
/* 567:682 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 568:    */     }
/* 569:684 */     if (this.anticipoClienteDao.existeCierreCaja(anticipoCliente).longValue() > 0L) {
/* 570:685 */       throw new ExcepcionAS2Financiero("msg_error_anular_anticipo_cliente_cierre_caja");
/* 571:    */     }
/* 572:    */   }
/* 573:    */   
/* 574:    */   public void actualizarEstado(Integer idAnticipoCliente, Estado estado)
/* 575:    */   {
/* 576:697 */     this.anticipoClienteDao.actualizarEstado(idAnticipoCliente, estado);
/* 577:    */   }
/* 578:    */   
/* 579:    */   public int contarPorCriterio(Map<String, String> filters)
/* 580:    */   {
/* 581:707 */     return this.anticipoClienteDao.contarPorCriterio(filters);
/* 582:    */   }
/* 583:    */   
/* 584:    */   public void validaFechaDevolucionAnticipo(Date fechaAnticipoCliente, Date fechaDevolucion)
/* 585:    */     throws ExcepcionAS2Financiero
/* 586:    */   {
/* 587:718 */     Date fechaAnticipoClienteAux = FuncionesUtiles.setAtributoFecha(fechaAnticipoCliente);
/* 588:    */     
/* 589:    */ 
/* 590:721 */     Date fechaDevolucionAux = FuncionesUtiles.setAtributoFecha(fechaDevolucion);
/* 591:723 */     if (!FuncionesUtiles.compararFechaAnteriorOIgual(fechaAnticipoClienteAux, fechaDevolucionAux)) {
/* 592:724 */       throw new ExcepcionAS2Financiero("msg_error_fecha_anticipo_devolucion");
/* 593:    */     }
/* 594:    */   }
/* 595:    */   
/* 596:    */   public BigDecimal getSaldoAnticipo(AnticipoCliente anticipoCliente)
/* 597:    */   {
/* 598:735 */     return this.anticipoClienteDao.getSaldoAnticipo(anticipoCliente);
/* 599:    */   }
/* 600:    */   
/* 601:    */   public AnticipoCliente buscarPorCobro(Cobro cobro)
/* 602:    */   {
/* 603:740 */     return this.anticipoClienteDao.buscarPorCobro(cobro);
/* 604:    */   }
/* 605:    */   
/* 606:    */   public List<AnticipoCliente> listaAnticiposClientesMasivos(BigDecimal valor, int idOrganizacion, Empresa empresa)
/* 607:    */   {
/* 608:745 */     return this.anticipoClienteDao.listaAnticiposClientesMasivos(valor, idOrganizacion, empresa);
/* 609:    */   }
/* 610:    */   
/* 611:    */   public void actualizarAtributoEntidad(AnticipoCliente anticipoCliente, HashMap<String, Object> campos)
/* 612:    */   {
/* 613:750 */     this.anticipoClienteDao.actualizarAtributoEntidad(anticipoCliente, campos);
/* 614:    */   }
/* 615:    */   
/* 616:    */   public List<ReporteAnticipoCliente> obtenerAnticiposClientes(CategoriaEmpresa categoriaEmpresa, Integer idCliente, Date fechaHasta, Integer idOrganizacion)
/* 617:    */   {
/* 618:756 */     return this.anticipoClienteDao.obtenerAnticiposClientes(categoriaEmpresa, idCliente, fechaHasta, idOrganizacion);
/* 619:    */   }
/* 620:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioAnticipoClienteImpl
 * JD-Core Version:    0.7.0.1
 */