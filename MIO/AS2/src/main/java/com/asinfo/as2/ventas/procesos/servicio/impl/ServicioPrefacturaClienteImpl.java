/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProcesoPrefacturaCliente;
/*   5:    */ import com.asinfo.as2.dao.AjustePrefacturaClienteDao;
/*   6:    */ import com.asinfo.as2.dao.DespachoClienteDao;
/*   7:    */ import com.asinfo.as2.dao.DetalleAjustePrefacturaClienteDao;
/*   8:    */ import com.asinfo.as2.dao.ImpuestoProductoPrefacturaClienteDao;
/*   9:    */ import com.asinfo.as2.dao.PrefacturaClienteDao;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  12:    */ import com.asinfo.as2.entities.AjustePrefacturaCliente;
/*  13:    */ import com.asinfo.as2.entities.Asiento;
/*  14:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*  15:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  16:    */ import com.asinfo.as2.entities.DetalleAjustePrefacturaCliente;
/*  17:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  18:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  19:    */ import com.asinfo.as2.entities.Documento;
/*  20:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  21:    */ import com.asinfo.as2.entities.Empresa;
/*  22:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  23:    */ import com.asinfo.as2.entities.ImpuestoProductoPrefacturaCliente;
/*  24:    */ import com.asinfo.as2.entities.Organizacion;
/*  25:    */ import com.asinfo.as2.entities.PrefacturaCliente;
/*  26:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  27:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  28:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  29:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  30:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  31:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  32:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  33:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  34:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  35:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  36:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  37:    */ import com.asinfo.as2.util.AppUtil;
/*  38:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  39:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  40:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  41:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPrefacturaCliente;
/*  42:    */ import java.math.BigDecimal;
/*  43:    */ import java.math.RoundingMode;
/*  44:    */ import java.util.ArrayList;
/*  45:    */ import java.util.Date;
/*  46:    */ import java.util.HashMap;
/*  47:    */ import java.util.Iterator;
/*  48:    */ import java.util.List;
/*  49:    */ import java.util.Map;
/*  50:    */ import java.util.Set;
/*  51:    */ import javax.ejb.EJB;
/*  52:    */ import javax.ejb.SessionContext;
/*  53:    */ import javax.ejb.Stateless;
/*  54:    */ import javax.ejb.TransactionAttribute;
/*  55:    */ import javax.ejb.TransactionAttributeType;
/*  56:    */ import javax.ejb.TransactionManagement;
/*  57:    */ import javax.ejb.TransactionManagementType;
/*  58:    */ 
/*  59:    */ @Stateless
/*  60:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  61:    */ public class ServicioPrefacturaClienteImpl
/*  62:    */   extends AbstractServicioAS2
/*  63:    */   implements ServicioPrefacturaCliente
/*  64:    */ {
/*  65:    */   private static final long serialVersionUID = -3306457507788019674L;
/*  66:    */   @EJB
/*  67:    */   private transient PrefacturaClienteDao prefacturaClienteDao;
/*  68:    */   @EJB
/*  69:    */   private transient AjustePrefacturaClienteDao ajustePrefacturaClienteDao;
/*  70:    */   @EJB
/*  71:    */   private transient DetalleAjustePrefacturaClienteDao detalleAjustePrefacturaClienteDao;
/*  72:    */   @EJB
/*  73:    */   private transient ImpuestoProductoPrefacturaClienteDao impuestoProductoPrefacturaClienteDao;
/*  74:    */   @EJB
/*  75:    */   private transient DespachoClienteDao despachoClienteDao;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioPeriodo servicioPeriodo;
/*  78:    */   @EJB
/*  79:    */   private transient ServicioSecuencia servicioSecuencia;
/*  80:    */   @EJB
/*  81:    */   private transient ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  82:    */   @EJB
/*  83:    */   private transient ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  84:    */   @EJB
/*  85:    */   private transient ServicioAsiento servicioAsiento;
/*  86:    */   @EJB
/*  87:    */   private transient ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  88:    */   @EJB
/*  89:    */   private transient ServicioDocumento servicioDocumento;
/*  90:    */   @EJB
/*  91:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  92:    */   
/*  93:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  94:    */   public void guardar(PrefacturaCliente prefacturaCliente)
/*  95:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:114 */       this.servicioPeriodo.buscarPorFecha(prefacturaCliente.getFecha(), prefacturaCliente.getIdOrganizacion(), prefacturaCliente.getDocumento().getDocumentoBase());
/* 100:    */       
/* 101:116 */       cargarSecuencia(prefacturaCliente);
/* 102:    */       
/* 103:    */ 
/* 104:119 */       prefacturaCliente.setFiltroBusqueda(prefacturaCliente.getEmpresa().getNombreFiscal() + " " + prefacturaCliente
/* 105:120 */         .getEmpresa().getNombreFiscal() + " " + prefacturaCliente.getNumero());
/* 106:    */       
/* 107:    */ 
/* 108:    */ 
/* 109:124 */       AjustePrefacturaCliente ajustePrefacturaCliente = (AjustePrefacturaCliente)prefacturaCliente.getListaAjustePrefacturaCliente().get(0);
/* 110:125 */       if (prefacturaCliente.getId() > 0)
/* 111:    */       {
/* 112:127 */         AjustePrefacturaCliente ajustePrefacturaPadre = this.prefacturaClienteDao.getAjusteActivo(prefacturaCliente.getId());
/* 113:128 */         ajustePrefacturaCliente.setAjustePrefacturaClientePadre(ajustePrefacturaPadre);
/* 114:    */       }
/* 115:131 */       this.prefacturaClienteDao.guardar(prefacturaCliente);
/* 116:    */       
/* 117:133 */       Map<Integer, DespachoCliente> mapaDespachos = new HashMap();
/* 118:134 */       Map<Integer, DespachoCliente> eliminadosDespachos = new HashMap();
/* 119:135 */       this.ajustePrefacturaClienteDao.guardar(ajustePrefacturaCliente);
/* 120:137 */       for (DetalleAjustePrefacturaCliente detalleAjustePrefacturaCliente : ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente())
/* 121:    */       {
/* 122:138 */         if (!detalleAjustePrefacturaCliente.isEliminado())
/* 123:    */         {
/* 124:139 */           if ((detalleAjustePrefacturaCliente.getDetalleDespachoCliente() != null) && 
/* 125:140 */             (!mapaDespachos.containsKey(Integer.valueOf(detalleAjustePrefacturaCliente.getDetalleDespachoCliente().getDespachoCliente().getId())))) {
/* 126:141 */             mapaDespachos.put(Integer.valueOf(detalleAjustePrefacturaCliente.getDetalleDespachoCliente().getDespachoCliente().getId()), detalleAjustePrefacturaCliente
/* 127:142 */               .getDetalleDespachoCliente().getDespachoCliente());
/* 128:    */           }
/* 129:    */         }
/* 130:146 */         else if (detalleAjustePrefacturaCliente.getDetalleDespachoCliente() != null) {
/* 131:147 */           eliminadosDespachos.put(Integer.valueOf(detalleAjustePrefacturaCliente.getDetalleDespachoCliente().getDespachoCliente().getId()), detalleAjustePrefacturaCliente
/* 132:148 */             .getDetalleDespachoCliente().getDespachoCliente());
/* 133:    */         }
/* 134:152 */         this.detalleAjustePrefacturaClienteDao.guardar(detalleAjustePrefacturaCliente);
/* 135:153 */         for (ImpuestoProductoPrefacturaCliente impuestoProductoPrefacturaCliente : detalleAjustePrefacturaCliente
/* 136:154 */           .getListaImpuestoProductoPrefacturaCliente()) {
/* 137:155 */           this.impuestoProductoPrefacturaClienteDao.guardar(impuestoProductoPrefacturaCliente);
/* 138:    */         }
/* 139:    */       }
/* 140:158 */       prefacturaCliente.setIndicadorGeneradoDespacho(Boolean.valueOf(mapaDespachos.size() > 0));
/* 141:159 */       actualizarAjusteActivo(prefacturaCliente.getId(), ajustePrefacturaCliente.getId());
/* 142:161 */       for (DespachoCliente despacho : mapaDespachos.values())
/* 143:    */       {
/* 144:162 */         despacho.setIndicadorGeneradoPrefactura(true);
/* 145:163 */         despacho.setPrefacturaCliente(prefacturaCliente);
/* 146:164 */         this.despachoClienteDao.guardar(despacho);
/* 147:    */       }
/* 148:167 */       for (DespachoCliente eDespachos : eliminadosDespachos.values())
/* 149:    */       {
/* 150:168 */         eDespachos.setIndicadorGeneradoPrefactura(false);
/* 151:169 */         eDespachos.setPrefacturaCliente(null);
/* 152:170 */         this.despachoClienteDao.guardar(eDespachos);
/* 153:    */       }
/* 154:175 */       contabilizar(ajustePrefacturaCliente);
/* 155:    */     }
/* 156:    */     catch (ExcepcionAS2Financiero e)
/* 157:    */     {
/* 158:178 */       this.context.setRollbackOnly();
/* 159:179 */       throw e;
/* 160:    */     }
/* 161:    */     catch (ExcepcionAS2 e)
/* 162:    */     {
/* 163:181 */       this.context.setRollbackOnly();
/* 164:182 */       throw e;
/* 165:    */     }
/* 166:    */     catch (Exception e)
/* 167:    */     {
/* 168:184 */       this.context.setRollbackOnly();
/* 169:185 */       throw new ExcepcionAS2Ventas(e);
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   private void cargarSecuencia(PrefacturaCliente prefacturaCliente)
/* 174:    */     throws ExcepcionAS2
/* 175:    */   {
/* 176:197 */     if (prefacturaCliente.getNumero().isEmpty())
/* 177:    */     {
/* 178:198 */       String numero = "";
/* 179:199 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(prefacturaCliente.getDocumento().getId(), prefacturaCliente.getFecha());
/* 180:    */       
/* 181:201 */       prefacturaCliente.setNumero(numero);
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void eliminar(PrefacturaCliente prefacturaCliente)
/* 186:    */     throws ExcepcionAS2
/* 187:    */   {
/* 188:212 */     this.prefacturaClienteDao.eliminar(prefacturaCliente);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public PrefacturaCliente buscarPorId(Integer id)
/* 192:    */     throws ExcepcionAS2
/* 193:    */   {
/* 194:222 */     return (PrefacturaCliente)this.prefacturaClienteDao.buscarPorId(id);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void totalizar(AjustePrefacturaCliente ajustePrefacturaCliente)
/* 198:    */     throws ExcepcionAS2Ventas
/* 199:    */   {
/* 200:232 */     BigDecimal total = BigDecimal.ZERO;
/* 201:233 */     BigDecimal descuento = BigDecimal.ZERO;
/* 202:235 */     for (DetalleAjustePrefacturaCliente dapc : ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente()) {
/* 203:236 */       if (!dapc.isEliminado())
/* 204:    */       {
/* 205:237 */         dapc.setDescuento(dapc.getPrecio().multiply(dapc.getPorcentajeDescuento()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/* 206:    */         
/* 207:239 */         total = total.add(dapc.getPrecioLinea());
/* 208:240 */         descuento = descuento.add(dapc.getDescuentoLinea());
/* 209:    */       }
/* 210:    */     }
/* 211:244 */     totalizarImpuesto(ajustePrefacturaCliente);
/* 212:245 */     ajustePrefacturaCliente.setTotal(FuncionesUtiles.redondearBigDecimal(total));
/* 213:246 */     ajustePrefacturaCliente.setDescuento(FuncionesUtiles.redondearBigDecimal(descuento));
/* 214:    */     
/* 215:    */ 
/* 216:249 */     ajustePrefacturaCliente.getPrefacturaCliente().setTotal(ajustePrefacturaCliente.getTotal());
/* 217:250 */     ajustePrefacturaCliente.getPrefacturaCliente().setDescuento(ajustePrefacturaCliente.getDescuento());
/* 218:251 */     ajustePrefacturaCliente.getPrefacturaCliente().setImpuesto(ajustePrefacturaCliente.getImpuesto());
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void totalizarImpuesto(AjustePrefacturaCliente ajustePrefacturaCliente)
/* 222:    */     throws ExcepcionAS2Ventas
/* 223:    */   {
/* 224:261 */     BigDecimal totalBaseImponible = BigDecimal.ZERO;
/* 225:262 */     BigDecimal totalImpuestoProducto = BigDecimal.ZERO;
/* 226:264 */     for (DetalleAjustePrefacturaCliente dapc : ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente())
/* 227:    */     {
/* 228:265 */       if (!dapc.isEliminado())
/* 229:    */       {
/* 230:266 */         totalBaseImponible = totalBaseImponible.add(dapc.getBaseImponible());
/* 231:267 */         totalImpuestoProducto = totalImpuestoProducto.add(dapc.getValorImpuestosLinea());
/* 232:    */       }
/* 233:269 */       ajustePrefacturaCliente.setImpuesto(FuncionesUtiles.redondearBigDecimal(totalImpuestoProducto));
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public List<PrefacturaCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 238:    */     throws ExcepcionAS2
/* 239:    */   {
/* 240:283 */     return this.prefacturaClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public int contarPorCriterio(Map<String, String> filters)
/* 244:    */   {
/* 245:293 */     return this.prefacturaClienteDao.contarPorCriterio(filters);
/* 246:    */   }
/* 247:    */   
/* 248:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 249:    */   public void anular(PrefacturaCliente prefacturaCliente)
/* 250:    */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/* 251:    */   {
/* 252:305 */     esEditable(prefacturaCliente);
/* 253:306 */     prefacturaCliente = this.prefacturaClienteDao.cargarDetalle(prefacturaCliente.getId());
/* 254:    */     
/* 255:308 */     List<Asiento> codigos = listaCodigosAsiento(prefacturaCliente.getIdPrefacturaCliente());
/* 256:310 */     for (AjustePrefacturaCliente ajustePrefacturaCliente : prefacturaCliente.getListaAjustePrefacturaCliente()) {
/* 257:312 */       for (DetalleAjustePrefacturaCliente detalleAjustePrefacturaCliente : ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente()) {
/* 258:314 */         if (detalleAjustePrefacturaCliente.getDetalleDespachoCliente() != null)
/* 259:    */         {
/* 260:317 */           HashMap<String, Object> campos = new HashMap();
/* 261:318 */           campos.put("indicadorGeneradoPrefactura", Boolean.valueOf(false));
/* 262:319 */           this.despachoClienteDao.actualizarAtributoEntidad(detalleAjustePrefacturaCliente.getDetalleDespachoCliente().getDespachoCliente(), campos);
/* 263:    */           
/* 264:    */ 
/* 265:    */ 
/* 266:323 */           campos = new HashMap();
/* 267:324 */           campos.put("detalleDespachoCliente", null);
/* 268:325 */           this.detalleAjustePrefacturaClienteDao.actualizarAtributoEntidad(detalleAjustePrefacturaCliente, campos);
/* 269:    */         }
/* 270:    */       }
/* 271:    */     }
/* 272:331 */     for (Asiento asi : codigos)
/* 273:    */     {
/* 274:332 */       asi = this.servicioAsiento.cargarDetalle(asi.getIdAsiento());
/* 275:333 */       asi.setIndicadorAutomatico(false);
/* 276:    */       try
/* 277:    */       {
/* 278:335 */         this.servicioAsiento.anular(asi);
/* 279:    */       }
/* 280:    */       catch (ExcepcionAS2Financiero e)
/* 281:    */       {
/* 282:337 */         this.context.setRollbackOnly();
/* 283:338 */         throw e;
/* 284:    */       }
/* 285:    */     }
/* 286:343 */     Object campos = new HashMap();
/* 287:344 */     ((HashMap)campos).put("estado", Estado.ANULADO);
/* 288:345 */     this.prefacturaClienteDao.actualizarAtributoEntidad(prefacturaCliente, (HashMap)campos);
/* 289:    */   }
/* 290:    */   
/* 291:    */   public List<PrefacturaCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 292:    */   {
/* 293:356 */     return this.prefacturaClienteDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 294:    */   }
/* 295:    */   
/* 296:    */   public List<PrefacturaCliente> autocompletarPrefactura(String consulta)
/* 297:    */   {
/* 298:366 */     List<PrefacturaCliente> lista = new ArrayList();
/* 299:367 */     String ordenar = "numero";
/* 300:368 */     Map<String, String> filters = new HashMap();
/* 301:369 */     filters.put("filtroBusqueda", "%" + consulta.trim() + "%");
/* 302:370 */     filters.put("estado", Estado.ELABORADO.toString());
/* 303:    */     
/* 304:372 */     lista = obtenerListaCombo(ordenar, true, filters);
/* 305:    */     
/* 306:374 */     return lista;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public PrefacturaCliente cargarDetalle(int idPrefacturaCliente)
/* 310:    */   {
/* 311:384 */     return this.prefacturaClienteDao.cargarDetalle(idPrefacturaCliente);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public List<AjustePrefacturaCliente> getListaAjustePrefacturaCliente(int idPrefacturaCliente)
/* 315:    */   {
/* 316:394 */     return this.prefacturaClienteDao.getListaAjustePrefacturaCliente(idPrefacturaCliente);
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void actualizarAjusteActivo(int idPrefacturaCliente, int idAjustePrefacturaCliente)
/* 320:    */   {
/* 321:404 */     this.prefacturaClienteDao.actualizarAjusteActivo(idPrefacturaCliente, idAjustePrefacturaCliente);
/* 322:    */   }
/* 323:    */   
/* 324:    */   public AjustePrefacturaCliente obtenerAjustePrefacturaActivo(int idPrefactura)
/* 325:    */   {
/* 326:414 */     return this.prefacturaClienteDao.obtenerAjustePrefacturaActivo(idPrefactura);
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void contabilizar(AjustePrefacturaCliente ajuste)
/* 330:    */     throws ExcepcionAS2, AS2Exception
/* 331:    */   {
/* 332:420 */     Date fechaContabilizacion = ajuste.getFecha();
/* 333:    */     Asiento asiento;
/* 334:423 */     if (ajuste.getAsiento() != null)
/* 335:    */     {
/* 336:424 */       Asiento asiento = ajuste.getAsiento();
/* 337:425 */       asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/* 338:    */     }
/* 339:    */     else
/* 340:    */     {
/* 341:427 */       asiento = new Asiento();
/* 342:428 */       asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 343:429 */       asiento.setSucursal(AppUtil.getSucursal());
/* 344:430 */       asiento.setEstado(Estado.ELABORADO);
/* 345:431 */       asiento.setIndicadorAutomatico(true);
/* 346:    */     }
/* 347:441 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(ajuste.getPrefacturaCliente().getDocumento().getId()));
/* 348:442 */     asiento.setTipoAsiento(documento.getTipoAsiento());
/* 349:    */     
/* 350:    */ 
/* 351:445 */     String concepto = "";
/* 352:    */     
/* 353:447 */     concepto = ajuste.getPrefacturaCliente().getDocumento().getNombre().trim() + " #" + ajuste.getPrefacturaCliente().getNumero() + " " + ajuste.getPrefacturaCliente().getDescripcion();
/* 354:448 */     asiento.setConcepto(concepto);
/* 355:449 */     asiento.setFecha(fechaContabilizacion);
/* 356:    */     
/* 357:    */ 
/* 358:    */ 
/* 359:453 */     List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(ajuste
/* 360:454 */       .getIdOrganizacion(), DocumentoBase.PREFACTURA_CLIENTE);
/* 361:    */     
/* 362:456 */     List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(ajuste.getIdOrganizacion(), DocumentoBase.PREFACTURA_CLIENTE);
/* 363:461 */     for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 364:    */     {
/* 365:463 */       List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 366:464 */       List<DetalleInterfazContableProceso> listaActual = new ArrayList();
/* 367:    */       
/* 368:466 */       listaActual.addAll(this.prefacturaClienteDao.getInterfazVentasDimensiones(ajuste, documentoContabilizacion.getProcesoContabilizacion()));
/* 369:    */       
/* 370:468 */       List<DetalleInterfazContableProceso> listaAnterior = new ArrayList();
/* 371:469 */       if (ajuste.getAjustePrefacturaClientePadre() != null) {
/* 372:470 */         listaAnterior.addAll(this.prefacturaClienteDao.getInterfazVentasDimensiones(ajuste.getAjustePrefacturaClientePadre(), documentoContabilizacion
/* 373:471 */           .getProcesoContabilizacion()));
/* 374:    */       }
/* 375:474 */       Map<String, DetalleInterfazContableProceso> hmAjusteAnterior = new HashMap();
/* 376:475 */       for (DetalleInterfazContableProceso dicp : listaAnterior) {
/* 377:476 */         hmAjusteAnterior.put(((DetalleInterfazContableProcesoPrefacturaCliente)dicp).getClave(), dicp);
/* 378:    */       }
/* 379:479 */       for (DetalleInterfazContableProceso dicp : listaActual)
/* 380:    */       {
/* 381:480 */         String clave = ((DetalleInterfazContableProcesoPrefacturaCliente)dicp).getClave();
/* 382:481 */         DetalleInterfazContableProceso dicpAnterior = (DetalleInterfazContableProceso)hmAjusteAnterior.get(clave);
/* 383:483 */         if (dicpAnterior != null)
/* 384:    */         {
/* 385:484 */           dicp.setValor(dicp.getValor().subtract(dicpAnterior.getValor()));
/* 386:486 */           if (dicp.getValor().compareTo(BigDecimal.ZERO) != 0) {
/* 387:487 */             lista.add(dicp);
/* 388:    */           }
/* 389:491 */           hmAjusteAnterior.remove(clave);
/* 390:    */         }
/* 391:    */         else
/* 392:    */         {
/* 393:493 */           lista.add(dicp);
/* 394:    */         }
/* 395:    */       }
/* 396:497 */       Iterator it = hmAjusteAnterior.keySet().iterator();
/* 397:498 */       while (it.hasNext())
/* 398:    */       {
/* 399:499 */         String key = (String)it.next();
/* 400:500 */         ((DetalleInterfazContableProceso)hmAjusteAnterior.get(key)).setValor(((DetalleInterfazContableProceso)hmAjusteAnterior.get(key)).getValor().multiply(new BigDecimal(-1)));
/* 401:501 */         lista.add(hmAjusteAnterior.get(key));
/* 402:    */       }
/* 403:506 */       if (lista.size() > 0) {
/* 404:507 */         asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 405:508 */           .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 406:    */       }
/* 407:    */     }
/* 408:513 */     this.servicioAsiento.guardar(asiento);
/* 409:    */     
/* 410:515 */     ajuste.setFechaContabilizacion(fechaContabilizacion);
/* 411:    */     
/* 412:517 */     ajuste.setAsiento(asiento);
/* 413:518 */     this.ajustePrefacturaClienteDao.guardar(ajuste);
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void liberarPrefacturaCliente(FacturaCliente facturaCliente)
/* 417:    */     throws ExcepcionAS2
/* 418:    */   {
/* 419:529 */     facturaCliente = this.servicioFacturaCliente.buscarPorId(Integer.valueOf(facturaCliente.getId()));
/* 420:531 */     if (facturaCliente.getEstado() == Estado.ANULADO)
/* 421:    */     {
/* 422:533 */       this.prefacturaClienteDao.liberarPrefacturaCliente(facturaCliente);
/* 423:    */     }
/* 424:    */     else
/* 425:    */     {
/* 426:536 */       List<FacturaCliente> listaNC = this.servicioFacturaCliente.getListaNotaCreditoCliente(facturaCliente, null);
/* 427:    */       
/* 428:    */ 
/* 429:539 */       BigDecimal totalFactura = facturaCliente.getTotal().subtract(facturaCliente.getDescuento()).add(facturaCliente.getImpuesto());
/* 430:540 */       BigDecimal totalNC = BigDecimal.ZERO;
/* 431:541 */       for (FacturaCliente fc : listaNC) {
/* 432:542 */         totalNC = totalNC.add(fc.getTotal().subtract(fc.getDescuento()).add(fc.getImpuesto()));
/* 433:    */       }
/* 434:545 */       if (totalFactura.compareTo(totalNC) != 0) {
/* 435:546 */         throw new ExcepcionAS2("msg_error_liberar_prefactura");
/* 436:    */       }
/* 437:548 */       this.prefacturaClienteDao.liberarPrefacturaCliente(facturaCliente);
/* 438:    */     }
/* 439:    */   }
/* 440:    */   
/* 441:    */   public List<PrefacturaCliente> getListaPrefacturaCliente(FacturaCliente facturaCliente)
/* 442:    */   {
/* 443:560 */     return this.prefacturaClienteDao.getListaPrefacturaCliente(facturaCliente);
/* 444:    */   }
/* 445:    */   
/* 446:    */   public List<Object[]> getReportePrefacturaCliente(int idPrefacturaCliente)
/* 447:    */     throws ExcepcionAS2
/* 448:    */   {
/* 449:565 */     return this.prefacturaClienteDao.getReportePrefacturaCliente(idPrefacturaCliente);
/* 450:    */   }
/* 451:    */   
/* 452:    */   private void esEditable(PrefacturaCliente prefacturaCliente)
/* 453:    */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/* 454:    */   {
/* 455:577 */     this.servicioPeriodo.buscarPorFecha(prefacturaCliente.getFecha(), prefacturaCliente.getIdOrganizacion(), prefacturaCliente.getDocumento().getDocumentoBase());
/* 456:579 */     if (!prefacturaCliente.getEstado().equals(Estado.ELABORADO)) {
/* 457:580 */       throw new ExcepcionAS2Ventas("msg_error_anular");
/* 458:    */     }
/* 459:    */   }
/* 460:    */   
/* 461:    */   public List<Asiento> listaCodigosAsiento(int idAjustePrefacturaCliente)
/* 462:    */   {
/* 463:587 */     return this.prefacturaClienteDao.listaCodigosAsiento(idAjustePrefacturaCliente);
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void contabilizarAjustesPrefacturaCliente(Date fechaDesde, Date fechaHasta)
/* 467:    */     throws ExcepcionAS2, AS2Exception
/* 468:    */   {
/* 469:597 */     for (PrefacturaCliente p : this.prefacturaClienteDao.getListaPrefacturaCliente(fechaDesde, fechaHasta))
/* 470:    */     {
/* 471:598 */       PrefacturaCliente prefacturaCliente = cargarDetalle(p.getId());
/* 472:599 */       this.prefacturaClienteDao.detach(prefacturaCliente);
/* 473:601 */       for (AjustePrefacturaCliente ajustePrefacturaCliente : prefacturaCliente.getListaAjustePrefacturaCliente()) {
/* 474:603 */         if (ajustePrefacturaCliente.getAsiento() != null)
/* 475:    */         {
/* 476:604 */           for (DetalleAsiento detalleAsiento : ajustePrefacturaCliente.getAsiento().getListaDetalleAsiento()) {
/* 477:605 */             detalleAsiento.setEliminado(true);
/* 478:    */           }
/* 479:607 */           contabilizar(ajustePrefacturaCliente);
/* 480:    */         }
/* 481:    */       }
/* 482:    */     }
/* 483:    */   }
/* 484:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioPrefacturaClienteImpl
 * JD-Core Version:    0.7.0.1
 */