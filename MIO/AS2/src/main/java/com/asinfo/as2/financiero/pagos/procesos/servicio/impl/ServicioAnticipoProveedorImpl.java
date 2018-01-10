/*   1:    */ package com.asinfo.as2.financiero.pagos.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.clases.PagoAnticipoCheque;
/*   6:    */ import com.asinfo.as2.dao.AnticipoProveedorDao;
/*   7:    */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   9:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*  10:    */ import com.asinfo.as2.entities.Asiento;
/*  11:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*  12:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  13:    */ import com.asinfo.as2.entities.CuentaContable;
/*  14:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  15:    */ import com.asinfo.as2.entities.Documento;
/*  16:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  17:    */ import com.asinfo.as2.entities.Empresa;
/*  18:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  19:    */ import com.asinfo.as2.entities.FormaPago;
/*  20:    */ import com.asinfo.as2.entities.Organizacion;
/*  21:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  22:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  23:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  24:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  25:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  28:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  29:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  30:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  31:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  32:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  33:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  34:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*  35:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioOrdenPagoProveedor;
/*  36:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  37:    */ import com.asinfo.as2.util.AppUtil;
/*  38:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  39:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  40:    */ import java.math.BigDecimal;
/*  41:    */ import java.util.ArrayList;
/*  42:    */ import java.util.Date;
/*  43:    */ import java.util.HashMap;
/*  44:    */ import java.util.List;
/*  45:    */ import java.util.Map;
/*  46:    */ import javax.ejb.EJB;
/*  47:    */ import javax.ejb.SessionContext;
/*  48:    */ import javax.ejb.Stateless;
/*  49:    */ import javax.ejb.TransactionAttribute;
/*  50:    */ import javax.ejb.TransactionAttributeType;
/*  51:    */ import javax.ejb.TransactionManagement;
/*  52:    */ import javax.ejb.TransactionManagementType;
/*  53:    */ 
/*  54:    */ @Stateless
/*  55:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  56:    */ public class ServicioAnticipoProveedorImpl
/*  57:    */   extends AbstractServicioAS2Financiero
/*  58:    */   implements ServicioAnticipoProveedor
/*  59:    */ {
/*  60:    */   private static final long serialVersionUID = 4541506233621824324L;
/*  61:    */   @EJB
/*  62:    */   private AnticipoProveedorDao anticipoProveedorDao;
/*  63:    */   @EJB
/*  64:    */   private FacturaProveedorDao facturaProveedorDao;
/*  65:    */   @EJB
/*  66:    */   private ServicioPeriodo servicioPeriodo;
/*  67:    */   @EJB
/*  68:    */   private ServicioSecuencia servicioSecuencia;
/*  69:    */   @EJB
/*  70:    */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  71:    */   @EJB
/*  72:    */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  73:    */   @EJB
/*  74:    */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  75:    */   @EJB
/*  76:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  77:    */   @EJB
/*  78:    */   private ServicioOrdenPagoProveedor servicioOrdenPagoProveedor;
/*  79:    */   
/*  80:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  81:    */   public void guardar(AnticipoProveedor anticipoProveedor)
/*  82:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:104 */       validar(anticipoProveedor);
/*  87:108 */       if ((!ParametrosSistema.getIndicadorAprobarPagos(anticipoProveedor.getIdOrganizacion()).booleanValue()) || (anticipoProveedor.isIndicadorSaldoInicial())) {
/*  88:109 */         anticipoProveedor.setEstado(Estado.CONTABILIZADO);
/*  89:    */       }
/*  90:113 */       cargarSecuencia(anticipoProveedor);
/*  91:    */       
/*  92:115 */       anticipoProveedor.setSaldo(anticipoProveedor.getValor());
/*  93:116 */       if (anticipoProveedor.getFacturaProveedorSRI() != null)
/*  94:    */       {
/*  95:117 */         anticipoProveedor.getFacturaProveedorSRI().setFechaRegistro(anticipoProveedor.getFecha());
/*  96:118 */         anticipoProveedor.getFacturaProveedorSRI().setFechaEmision(anticipoProveedor.getFecha());
/*  97:119 */         this.servicioFacturaProveedorSRI.guardar(anticipoProveedor.getFacturaProveedorSRI());
/*  98:    */       }
/*  99:121 */       if (anticipoProveedor.getIndicadorContabilizar() == null) {
/* 100:122 */         anticipoProveedor.setIndicadorContabilizar(Boolean.valueOf(anticipoProveedor.getDocumento().isIndicadorContabilizar()));
/* 101:    */       }
/* 102:124 */       this.anticipoProveedorDao.guardar(anticipoProveedor);
/* 103:126 */       if ((anticipoProveedor.getSecuencia() != null) && (anticipoProveedor.getEstado().equals(Estado.CONTABILIZADO))) {
/* 104:127 */         this.servicioSecuencia.actualizarSecuencia(anticipoProveedor.getSecuencia(), anticipoProveedor.getDocumentoReferencia());
/* 105:    */       }
/* 106:132 */       if (anticipoProveedor.getEstado().equals(Estado.CONTABILIZADO))
/* 107:    */       {
/* 108:133 */         if ((!anticipoProveedor.isIndicadorSaldoInicial()) && 
/* 109:134 */           (anticipoProveedor.getDocumento().getDocumentoBase() == DocumentoBase.ANTICIPO_PROVEEDOR)) {
/* 110:135 */           contabilizar(anticipoProveedor);
/* 111:    */         }
/* 112:138 */         if (anticipoProveedor.getCuentaBancariaOrganizacionDevolucion() != null)
/* 113:    */         {
/* 114:139 */           BigDecimal saldoAnticipo = anticipoProveedor.getSaldo();
/* 115:140 */           saldoAnticipo = saldoAnticipo.subtract(anticipoProveedor.getValorDevolucion());
/* 116:141 */           anticipoProveedor.setSaldo(saldoAnticipo);
/* 117:142 */           this.anticipoProveedorDao.guardar(anticipoProveedor);
/* 118:143 */           contabilizarDevolucion(anticipoProveedor);
/* 119:    */         }
/* 120:    */       }
/* 121:    */     }
/* 122:    */     catch (ExcepcionAS2Financiero e)
/* 123:    */     {
/* 124:148 */       this.context.setRollbackOnly();
/* 125:149 */       throw e;
/* 126:    */     }
/* 127:    */     catch (ExcepcionAS2 e)
/* 128:    */     {
/* 129:152 */       this.context.setRollbackOnly();
/* 130:153 */       throw e;
/* 131:    */     }
/* 132:    */     catch (Exception e)
/* 133:    */     {
/* 134:156 */       this.context.setRollbackOnly();
/* 135:157 */       throw new ExcepcionAS2(e);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 140:    */   public void guardarDevolucion(AnticipoProveedor anticipoProveedor)
/* 141:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 142:    */   {
/* 143:    */     try
/* 144:    */     {
/* 145:167 */       validaFechaDevolucionAnticipo(anticipoProveedor.getFecha(), anticipoProveedor.getFechaDevolucion());
/* 146:    */       
/* 147:169 */       validarSaldoDevolucion(anticipoProveedor);
/* 148:    */       
/* 149:171 */       validar(anticipoProveedor);
/* 150:    */       
/* 151:173 */       BigDecimal saldoAnticipo = anticipoProveedor.getSaldo();
/* 152:174 */       saldoAnticipo = saldoAnticipo.subtract(anticipoProveedor.getValorDevolucion());
/* 153:175 */       anticipoProveedor.setSaldo(saldoAnticipo);
/* 154:176 */       this.anticipoProveedorDao.guardar(anticipoProveedor);
/* 155:177 */       contabilizarDevolucion(anticipoProveedor);
/* 156:    */     }
/* 157:    */     catch (ExcepcionAS2Financiero e)
/* 158:    */     {
/* 159:180 */       this.context.setRollbackOnly();
/* 160:181 */       throw e;
/* 161:    */     }
/* 162:    */     catch (Exception e)
/* 163:    */     {
/* 164:184 */       this.context.setRollbackOnly();
/* 165:185 */       throw new ExcepcionAS2(e);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void eliminar(AnticipoProveedor anticipoProveedor)
/* 170:    */   {
/* 171:197 */     this.anticipoProveedorDao.eliminar(anticipoProveedor);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public AnticipoProveedor buscarPorId(Integer id)
/* 175:    */   {
/* 176:207 */     return (AnticipoProveedor)this.anticipoProveedorDao.buscarPorId(id);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public AnticipoProveedor cargarDetalle(int idAnticipoProveedor)
/* 180:    */   {
/* 181:217 */     return this.anticipoProveedorDao.cargarDetalle(idAnticipoProveedor);
/* 182:    */   }
/* 183:    */   
/* 184:    */   private void validar(AnticipoProveedor anticipoProveedor)
/* 185:    */     throws ExcepcionAS2
/* 186:    */   {
/* 187:    */     DocumentoBase documentoBase;
/* 188:    */     DocumentoBase documentoBase;
/* 189:232 */     if (anticipoProveedor.getDocumentoDevolucion() != null) {
/* 190:233 */       documentoBase = anticipoProveedor.getDocumentoDevolucion().getDocumentoBase();
/* 191:    */     } else {
/* 192:235 */       documentoBase = anticipoProveedor.getDocumento().getDocumentoBase();
/* 193:    */     }
/* 194:238 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$DocumentoBase[documentoBase.ordinal()])
/* 195:    */     {
/* 196:    */     case 1: 
/* 197:240 */       this.servicioPeriodo.buscarPorFecha(anticipoProveedor.getFecha(), anticipoProveedor.getIdOrganizacion(), documentoBase);
/* 198:241 */       break;
/* 199:    */     case 2: 
/* 200:244 */       this.servicioPeriodo.buscarPorFecha(anticipoProveedor.getFechaDevolucion(), anticipoProveedor.getIdOrganizacion(), documentoBase);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void cargarSecuencia(AnticipoProveedor anticipoProveedor)
/* 205:    */     throws ExcepcionAS2
/* 206:    */   {
/* 207:256 */     if (anticipoProveedor.getNumero().isEmpty())
/* 208:    */     {
/* 209:257 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(anticipoProveedor.getDocumento().getIdDocumento(), anticipoProveedor
/* 210:258 */         .getFecha());
/* 211:259 */       anticipoProveedor.setNumero(numero);
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public boolean validaAsientoAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/* 216:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 217:    */   {
/* 218:272 */     return true;
/* 219:    */   }
/* 220:    */   
/* 221:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 222:    */   public void anularAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/* 223:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 224:    */   {
/* 225:284 */     anularAnticipoProveedor(anticipoProveedor, true);
/* 226:    */   }
/* 227:    */   
/* 228:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 229:    */   public void anularAnticipoProveedor(AnticipoProveedor anticipoProveedor, boolean verificaPagoCash)
/* 230:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 231:    */   {
/* 232:    */     try
/* 233:    */     {
/* 234:294 */       esEditable(anticipoProveedor, true, verificaPagoCash);
/* 235:    */       
/* 236:296 */       anticipoProveedor = (AnticipoProveedor)this.anticipoProveedorDao.buscarPorId(Integer.valueOf(anticipoProveedor.getId()));
/* 237:297 */       anticipoProveedor.setEstado(Estado.ANULADO);
/* 238:298 */       this.anticipoProveedorDao.flush();
/* 239:302 */       if (anticipoProveedor.getAsiento() != null)
/* 240:    */       {
/* 241:303 */         anticipoProveedor.getAsiento().setIndicadorAutomatico(false);
/* 242:304 */         this.servicioAsiento.anular(anticipoProveedor.getAsiento());
/* 243:    */       }
/* 244:307 */       this.servicioOrdenPagoProveedor.reversarOrdenAlAnularAnticipo(anticipoProveedor);
/* 245:    */     }
/* 246:    */     catch (ExcepcionAS2Financiero e)
/* 247:    */     {
/* 248:309 */       this.context.setRollbackOnly();
/* 249:310 */       throw e;
/* 250:    */     }
/* 251:    */     catch (Exception e)
/* 252:    */     {
/* 253:313 */       this.context.setRollbackOnly();
/* 254:314 */       throw new ExcepcionAS2(e);
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   public List<AnticipoProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 259:    */   {
/* 260:328 */     return this.anticipoProveedorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void contabilizar(AnticipoProveedor anticipoProveedor)
/* 264:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 265:    */   {
/* 266:338 */     if (anticipoProveedor.getIndicadorContabilizar().booleanValue())
/* 267:    */     {
/* 268:339 */       Date fechaContabilizacion = anticipoProveedor.getFecha();
/* 269:340 */       int idAnticipoProveedor = anticipoProveedor.getId();
/* 270:341 */       boolean indicadorGenerarAiento = false;
/* 271:    */       Asiento asiento;
/* 272:    */       Asiento asiento;
/* 273:    */       TipoAsiento tipoAsiento;
/* 274:343 */       if (anticipoProveedor.getAsiento() != null)
/* 275:    */       {
/* 276:344 */         asiento = this.servicioAsiento.cargarDetalle(anticipoProveedor.getAsiento().getId());
/* 277:    */       }
/* 278:    */       else
/* 279:    */       {
/* 280:347 */         asiento = new Asiento();
/* 281:348 */         asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 282:349 */         asiento.setSucursal(AppUtil.getSucursal());
/* 283:    */         
/* 284:    */ 
/* 285:    */ 
/* 286:353 */         tipoAsiento = anticipoProveedor.getNotaCreditoProveedor() != null ? anticipoProveedor.getNotaCreditoProveedor().getDocumento().getTipoAsiento() : anticipoProveedor.getDocumento().getTipoAsiento();
/* 287:    */         
/* 288:355 */         asiento.setTipoAsiento(tipoAsiento);
/* 289:    */       }
/* 290:358 */       for (DetalleAsiento dt : asiento.getListaDetalleAsiento()) {
/* 291:359 */         dt.setEliminado(true);
/* 292:    */       }
/* 293:363 */       String concepto = "";
/* 294:364 */       if (anticipoProveedor.getNotaCreditoProveedor() != null) {
/* 295:367 */         concepto = anticipoProveedor.getNotaCreditoProveedor().getDocumento().getNombre().trim() + " #" + anticipoProveedor.getNotaCreditoProveedor().getNumero() + " " + anticipoProveedor.getNotaCreditoProveedor().getDescripcion();
/* 296:    */       } else {
/* 297:370 */         concepto = anticipoProveedor.getDocumento().getNombre().trim() + " #" + anticipoProveedor.getNumero() + " " + anticipoProveedor.getEmpresa().getNombreFiscal() + " " + anticipoProveedor.getDescripcion();
/* 298:    */       }
/* 299:373 */       asiento.setConcepto(concepto);
/* 300:374 */       asiento.setFecha(fechaContabilizacion);
/* 301:375 */       if (anticipoProveedor.getFechaPosfechado() != null) {
/* 302:376 */         asiento.setFechaChequePosfechado(anticipoProveedor.getFechaPosfechado());
/* 303:    */       }
/* 304:379 */       if ((anticipoProveedor.getNotaPosfechado() != null) && (!anticipoProveedor.getNotaPosfechado().isEmpty())) {
/* 305:380 */         asiento.setNotaPosfechado(anticipoProveedor.getNotaPosfechado());
/* 306:    */       }
/* 307:383 */       asiento.setEstado(Estado.ELABORADO);
/* 308:384 */       asiento.setIndicadorAutomatico(true);
/* 309:385 */       List<DetalleInterfazContable> listaDA = new ArrayList();
/* 310:386 */       if (anticipoProveedor.getNotaCreditoProveedor() == null)
/* 311:    */       {
/* 312:387 */         listaDA.addAll(this.anticipoProveedorDao.getDetalleFormaPagoAnticipoIC(idAnticipoProveedor));
/* 313:388 */         indicadorGenerarAiento = true;
/* 314:    */       }
/* 315:392 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(anticipoProveedor.getIdOrganizacion(), DocumentoBase.ANTICIPO_PROVEEDOR);
/* 316:    */       
/* 317:    */ 
/* 318:395 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(anticipoProveedor.getIdOrganizacion(), DocumentoBase.ANTICIPO_PROVEEDOR);
/* 319:    */       
/* 320:397 */       List<Integer> list = new ArrayList();
/* 321:398 */       list.add(Integer.valueOf(anticipoProveedor.getIdAnticipoProveedor()));
/* 322:399 */       List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 323:400 */       List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/* 324:401 */       for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 325:    */       {
/* 326:402 */         lista.addAll(this.anticipoProveedorDao.getInterfazAnticipoProveedorDimensiones(list));
/* 327:403 */         listaDetalleAsiento.addAll(this.servicioInterfazContableProceso
/* 328:404 */           .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 329:    */       }
/* 330:407 */       FacturaProveedor notaCreditoProveedor = anticipoProveedor.getNotaCreditoProveedor();
/* 331:    */       boolean reversaGasto;
/* 332:408 */       if (notaCreditoProveedor != null)
/* 333:    */       {
/* 334:409 */         reversaGasto = ParametrosSistema.getNotaCreditoReversaGasto(notaCreditoProveedor.getIdOrganizacion()).booleanValue();
/* 335:410 */         if (reversaGasto)
/* 336:    */         {
/* 337:411 */           listaDA.addAll(this.facturaProveedorDao.getGastoServiciosNCIC(notaCreditoProveedor.getIdFacturaProveedor()));
/* 338:412 */           indicadorGenerarAiento = true;
/* 339:    */         }
/* 340:415 */         list = new ArrayList();
/* 341:416 */         list.add(Integer.valueOf(notaCreditoProveedor.getIdFacturaProveedor()));
/* 342:417 */         DocumentoBase documentoBase = notaCreditoProveedor.getDocumento().getDocumentoBase();
/* 343:418 */         listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(notaCreditoProveedor.getIdOrganizacion(), documentoBase);
/* 344:420 */         for (DocumentoContabilizacion documentoContabilizacion : this.servicioDocumentoContabilizacion
/* 345:421 */           .obtenerListaPorDocumentoBase(notaCreditoProveedor.getIdOrganizacion(), documentoBase))
/* 346:    */         {
/* 347:422 */           lista = this.facturaProveedorDao.getInterfazNotaCreditoDimensiones(list, documentoContabilizacion.getProcesoContabilizacion(), reversaGasto);
/* 348:424 */           if (lista.size() > 0) {
/* 349:425 */             listaDetalleAsiento.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 350:    */           }
/* 351:    */         }
/* 352:    */       }
/* 353:431 */       if (indicadorGenerarAiento) {
/* 354:432 */         super.generarAsiento(asiento, listaDA, notaCreditoProveedor == null ? anticipoProveedor
/* 355:433 */           .getDocumento() : notaCreditoProveedor.getDocumento());
/* 356:    */       }
/* 357:435 */       asiento.getListaDetalleAsiento().addAll(listaDetalleAsiento);
/* 358:    */       
/* 359:    */ 
/* 360:438 */       this.servicioAsiento.guardar(this.servicioInterfazContableProceso.ajustarDiferencias(asiento));
/* 361:    */       
/* 362:    */ 
/* 363:441 */       anticipoProveedor.setEstado(Estado.CONTABILIZADO);
/* 364:    */       
/* 365:443 */       anticipoProveedor.setFechaContabilizacion(fechaContabilizacion);
/* 366:    */       
/* 367:    */ 
/* 368:446 */       anticipoProveedor.setAsiento(asiento);
/* 369:447 */       this.anticipoProveedorDao.guardar(anticipoProveedor);
/* 370:    */     }
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void contabilizarDevolucion(AnticipoProveedor anticipoProveedor)
/* 374:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 375:    */   {
/* 376:453 */     if (anticipoProveedor.getIndicadorContabilizar().booleanValue())
/* 377:    */     {
/* 378:454 */       Date fechaContabilizacion = anticipoProveedor.getFechaDevolucion();
/* 379:455 */       int idAnticipoProveedor = anticipoProveedor.getId();
/* 380:    */       Asiento asiento;
/* 381:    */       Asiento asiento;
/* 382:    */       TipoAsiento tipoAsiento;
/* 383:457 */       if (anticipoProveedor.getAsientoDevolucion() != null)
/* 384:    */       {
/* 385:458 */         asiento = this.servicioAsiento.cargarDetalle(anticipoProveedor.getAsientoDevolucion().getId());
/* 386:    */       }
/* 387:    */       else
/* 388:    */       {
/* 389:461 */         asiento = new Asiento();
/* 390:462 */         asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 391:463 */         asiento.setSucursal(AppUtil.getSucursal());
/* 392:    */         
/* 393:465 */         tipoAsiento = anticipoProveedor.getDocumentoDevolucion().getTipoAsiento();
/* 394:466 */         asiento.setTipoAsiento(tipoAsiento);
/* 395:    */       }
/* 396:469 */       for (DetalleAsiento dt : asiento.getListaDetalleAsiento()) {
/* 397:470 */         dt.setEliminado(true);
/* 398:    */       }
/* 399:474 */       String concepto = "";
/* 400:475 */       concepto = anticipoProveedor.getDocumentoDevolucion().getNombre().trim() + " #" + anticipoProveedor.getNumero();
/* 401:476 */       asiento.setConcepto(concepto);
/* 402:477 */       asiento.setFecha(fechaContabilizacion);
/* 403:478 */       List<DetalleInterfazContable> listaDA = new ArrayList();
/* 404:    */       int idFormaPago;
/* 405:    */       DetalleInterfazContable detalleInterfazContable;
/* 406:480 */       if (anticipoProveedor.getCuentaBancariaOrganizacionDevolucion().isIndicadorCruce())
/* 407:    */       {
/* 408:481 */         int idCuentaContableCruce = anticipoProveedor.getCuentaContableDevolucionCruce().getId();
/* 409:482 */         String referencia1 = anticipoProveedor.getEmpresa().getNombreFiscal();
/* 410:483 */         String referencia2 = anticipoProveedor.getDocumentoDevolucion().getNombre() + " #" + anticipoProveedor.getNumero();
/* 411:484 */         String referencia3 = anticipoProveedor.getDocumentoReferenciaDevolucion();
/* 412:485 */         idFormaPago = anticipoProveedor.getFormaPagoDevolucion().getId();
/* 413:486 */         BigDecimal valor = anticipoProveedor.getValorDevolucion();
/* 414:    */         
/* 415:488 */         detalleInterfazContable = new DetalleInterfazContable(idCuentaContableCruce, referencia1, referencia2, referencia3, "", Integer.valueOf(idFormaPago), valor);
/* 416:489 */         List<DetalleInterfazContable> lista = new ArrayList();
/* 417:490 */         lista.add(detalleInterfazContable);
/* 418:491 */         listaDA.addAll(lista);
/* 419:    */       }
/* 420:    */       else
/* 421:    */       {
/* 422:493 */         listaDA.addAll(this.anticipoProveedorDao.getDetalleFormaPagoDevolucionAnticipoIC(idAnticipoProveedor));
/* 423:    */       }
/* 424:497 */       super.generarAsiento(asiento, listaDA, anticipoProveedor.getDocumentoDevolucion());
/* 425:    */       
/* 426:    */ 
/* 427:    */ 
/* 428:501 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(anticipoProveedor.getIdOrganizacion(), DocumentoBase.ANTICIPO_PROVEEDOR);
/* 429:    */       
/* 430:    */ 
/* 431:504 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(anticipoProveedor.getIdOrganizacion(), DocumentoBase.ANTICIPO_PROVEEDOR);
/* 432:    */       
/* 433:506 */       List<Integer> list = new ArrayList();
/* 434:507 */       list.add(Integer.valueOf(anticipoProveedor.getIdAnticipoProveedor()));
/* 435:508 */       List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 436:509 */       for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 437:    */       {
/* 438:510 */         for (DetalleInterfazContableProceso detalleInterfazContableProceso : this.anticipoProveedorDao
/* 439:511 */           .getInterfazAnticipoProveedorDimensiones(list))
/* 440:    */         {
/* 441:512 */           detalleInterfazContableProceso.setValor(anticipoProveedor.getValorDevolucion());
/* 442:513 */           lista.add(detalleInterfazContableProceso);
/* 443:    */         }
/* 444:515 */         asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 445:516 */           .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true));
/* 446:    */       }
/* 447:520 */       this.servicioAsiento.guardar(asiento);
/* 448:    */       
/* 449:    */ 
/* 450:523 */       anticipoProveedor.setEstado(Estado.CONTABILIZADO);
/* 451:    */       
/* 452:525 */       anticipoProveedor.setFechaContabilizacion(fechaContabilizacion);
/* 453:    */       
/* 454:    */ 
/* 455:528 */       anticipoProveedor.setAsientoDevolucion(asiento);
/* 456:529 */       this.anticipoProveedorDao.guardar(anticipoProveedor);
/* 457:    */     }
/* 458:    */   }
/* 459:    */   
/* 460:    */   public BigDecimal obtenerSaldoAnticipo(int idProveedor, Date fechaHasta)
/* 461:    */   {
/* 462:540 */     return this.anticipoProveedorDao.obtenerSaldoAnticipo(idProveedor, fechaHasta);
/* 463:    */   }
/* 464:    */   
/* 465:    */   public void actualizarEstado(Integer idAnticipoProveedor, Estado estado)
/* 466:    */   {
/* 467:551 */     this.anticipoProveedorDao.actualizarEstado(idAnticipoProveedor, estado);
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void esEditable(AnticipoProveedor anticipoProveedor)
/* 471:    */     throws ExcepcionAS2Financiero
/* 472:    */   {
/* 473:562 */     esEditable(anticipoProveedor, false);
/* 474:    */   }
/* 475:    */   
/* 476:    */   public int contarPorCriterio(Map<String, String> filters)
/* 477:    */   {
/* 478:572 */     return this.anticipoProveedorDao.contarPorCriterio(filters);
/* 479:    */   }
/* 480:    */   
/* 481:    */   public BigDecimal obtenerSaldoProveedor(int idEmpresa)
/* 482:    */   {
/* 483:582 */     return this.anticipoProveedorDao.obtenerSaldoProveedor(idEmpresa);
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void esEditable(AnticipoProveedor anticipoProveedor, boolean anular)
/* 487:    */     throws ExcepcionAS2Financiero
/* 488:    */   {
/* 489:593 */     esEditable(anticipoProveedor, anular, true);
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void esEditable(AnticipoProveedor anticipoProveedor, boolean anular, boolean verificaPagoCash)
/* 493:    */     throws ExcepcionAS2Financiero
/* 494:    */   {
/* 495:598 */     this.servicioPeriodo.buscarPorFecha(anticipoProveedor.getFecha(), anticipoProveedor.getIdOrganizacion(), anticipoProveedor
/* 496:599 */       .getDocumento().getDocumentoBase());
/* 497:600 */     if (anticipoProveedor.getEstado() == Estado.ANULADO) {
/* 498:601 */       throw new ExcepcionAS2Financiero("msg_error_anular_anticipo_anulado");
/* 499:    */     }
/* 500:604 */     if ((verificaPagoCash) && (anticipoProveedor.getPagoCash() != null)) {
/* 501:605 */       throw new ExcepcionAS2Financiero("msg_error_anticipo_pago_cash");
/* 502:    */     }
/* 503:608 */     if (!anular) {
/* 504:610 */       if ((anticipoProveedor.getEstado().equals(Estado.APROBADO)) || (anticipoProveedor.getEstado().equals(Estado.CONTABILIZADO))) {
/* 505:611 */         throw new ExcepcionAS2Financiero("msg_error_editar");
/* 506:    */       }
/* 507:    */     }
/* 508:615 */     if (anticipoProveedor.getSaldo().compareTo(anticipoProveedor.getValor()) != 0) {
/* 509:616 */       throw new ExcepcionAS2Financiero("msg_error_anular_anticipo_proveedor_valor");
/* 510:    */     }
/* 511:619 */     if (anticipoProveedor.getNotaCreditoProveedor() != null) {
/* 512:620 */       throw new ExcepcionAS2Financiero("msg_error_existe_nota_credito_atada_a_anticipo");
/* 513:    */     }
/* 514:623 */     if ((anticipoProveedor.getAsiento() != null) && (anticipoProveedor.getAsiento().getEstado() == Estado.REVISADO)) {
/* 515:624 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 516:    */     }
/* 517:    */   }
/* 518:    */   
/* 519:    */   private void validarSaldoDevolucion(AnticipoProveedor anticipoProveedor)
/* 520:    */     throws ExcepcionAS2Financiero
/* 521:    */   {
/* 522:636 */     if (anticipoProveedor.getSaldo().compareTo(anticipoProveedor.getValorDevolucion()) != 0) {
/* 523:637 */       throw new ExcepcionAS2Financiero("msg_info_saldo_devolucion");
/* 524:    */     }
/* 525:    */   }
/* 526:    */   
/* 527:    */   public void validaFechaDevolucionAnticipo(Date fechaAnticipoProveedor, Date fechaDevolucion)
/* 528:    */     throws ExcepcionAS2Financiero
/* 529:    */   {
/* 530:649 */     Date fechaAnticipoProveedorAux = FuncionesUtiles.setAtributoFecha(fechaAnticipoProveedor);
/* 531:    */     
/* 532:    */ 
/* 533:652 */     Date fechaDevolucionAux = FuncionesUtiles.setAtributoFecha(fechaDevolucion);
/* 534:654 */     if (!FuncionesUtiles.compararFechaAnteriorOIgual(fechaAnticipoProveedorAux, fechaDevolucionAux)) {
/* 535:655 */       throw new ExcepcionAS2Financiero("msg_error_fecha_anticipo_devolucion");
/* 536:    */     }
/* 537:    */   }
/* 538:    */   
/* 539:    */   public void actualizarChequeEntregadoPago(int idAnticipoProveedor, boolean indicadorEntregaCheque, String usuarioEntregaCheque, Date fechaEntregaCheque)
/* 540:    */   {
/* 541:669 */     this.anticipoProveedorDao.actualizarChequeEntregadoPago(idAnticipoProveedor, indicadorEntregaCheque, usuarioEntregaCheque, fechaEntregaCheque);
/* 542:    */   }
/* 543:    */   
/* 544:    */   public List<PagoAnticipoCheque> getPagoConCheques()
/* 545:    */   {
/* 546:679 */     return this.anticipoProveedorDao.getPagoConCheques();
/* 547:    */   }
/* 548:    */   
/* 549:    */   public void detach(AnticipoProveedor anticipoProveedor)
/* 550:    */   {
/* 551:689 */     this.anticipoProveedorDao.detach(anticipoProveedor);
/* 552:    */   }
/* 553:    */   
/* 554:    */   public BigDecimal getSaldoAnticipoPreveedor(AnticipoProveedor anticipoProveedor)
/* 555:    */   {
/* 556:694 */     return this.anticipoProveedorDao.saldoAnticipoProveedor(anticipoProveedor);
/* 557:    */   }
/* 558:    */   
/* 559:    */   public List<AnticipoProveedor> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 560:    */   {
/* 561:699 */     return this.anticipoProveedorDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 562:    */   }
/* 563:    */   
/* 564:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 565:    */   public void anularDevolverAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/* 566:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 567:    */   {
/* 568:    */     try
/* 569:    */     {
/* 570:708 */       Asiento asientoTemporal = anticipoProveedor.getAsientoDevolucion();
/* 571:    */       
/* 572:710 */       BigDecimal saldoAnticipo = anticipoProveedor.getSaldo();
/* 573:711 */       saldoAnticipo = saldoAnticipo.add(anticipoProveedor.getValorDevolucion());
/* 574:712 */       anticipoProveedor.setSaldo(saldoAnticipo);
/* 575:    */       
/* 576:714 */       anticipoProveedor.setCuentaBancariaOrganizacionDevolucion(null);
/* 577:715 */       anticipoProveedor.setDocumentoDevolucion(null);
/* 578:716 */       anticipoProveedor.setFechaDevolucion(null);
/* 579:717 */       anticipoProveedor.setValorDevolucion(BigDecimal.ZERO);
/* 580:718 */       anticipoProveedor.setFormaPagoDevolucion(null);
/* 581:719 */       anticipoProveedor.setAsientoDevolucion(null);
/* 582:720 */       anticipoProveedor.setDocumentoReferenciaDevolucion("");
/* 583:721 */       anticipoProveedor.setDescripcionDevolucion("");
/* 584:    */       
/* 585:723 */       this.anticipoProveedorDao.guardar(anticipoProveedor);
/* 586:    */       
/* 587:725 */       asientoTemporal.setIndicadorAutomatico(false);
/* 588:726 */       this.servicioAsiento.anular(asientoTemporal);
/* 589:    */     }
/* 590:    */     catch (ExcepcionAS2Financiero e)
/* 591:    */     {
/* 592:729 */       this.context.setRollbackOnly();
/* 593:730 */       throw e;
/* 594:    */     }
/* 595:    */     catch (Exception e)
/* 596:    */     {
/* 597:733 */       this.context.setRollbackOnly();
/* 598:734 */       throw new ExcepcionAS2(e);
/* 599:    */     }
/* 600:    */   }
/* 601:    */   
/* 602:    */   public void actualizarAtributoEntidad(AnticipoProveedor anticipoProveedor, HashMap<String, Object> campos)
/* 603:    */   {
/* 604:740 */     this.anticipoProveedorDao.actualizarAtributoEntidad(anticipoProveedor, campos);
/* 605:    */   }
/* 606:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.procesos.servicio.impl.ServicioAnticipoProveedorImpl
 * JD-Core Version:    0.7.0.1
 */