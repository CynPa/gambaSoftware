/*   1:    */ package com.asinfo.as2.compras.importaciones.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioDocumentoGastoImportacion;
/*   4:    */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioDetalleFacturaProveedorImportacionGasto;
/*   5:    */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioFacturaProveedorImportacion;
/*   6:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   7:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*   8:    */ import com.asinfo.as2.dao.DetalleProcesoImportacionDao;
/*   9:    */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*  10:    */ import com.asinfo.as2.dao.FacturaProveedorImportacionDao;
/*  11:    */ import com.asinfo.as2.dao.reportes.compras.importaciones.ReporteImportacionDao;
/*  12:    */ import com.asinfo.as2.datosbase.servicio.ServicioMoneda;
/*  13:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  14:    */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacion;
/*  15:    */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionGasto;
/*  16:    */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionProducto;
/*  17:    */ import com.asinfo.as2.entities.DetalleProcesoImportacion;
/*  18:    */ import com.asinfo.as2.entities.Documento;
/*  19:    */ import com.asinfo.as2.entities.DocumentoGastoImportacion;
/*  20:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  21:    */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*  22:    */ import com.asinfo.as2.entities.GastoImportacion;
/*  23:    */ import com.asinfo.as2.entities.Moneda;
/*  24:    */ import com.asinfo.as2.entities.Organizacion;
/*  25:    */ import com.asinfo.as2.entities.Pais;
/*  26:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  27:    */ import com.asinfo.as2.entities.Sucursal;
/*  28:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  29:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  30:    */ import com.asinfo.as2.enumeraciones.TipoProrrateoEnum;
/*  31:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  32:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  33:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  34:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  35:    */ import com.asinfo.as2.util.AppUtil;
/*  36:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  37:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  38:    */ import java.math.BigDecimal;
/*  39:    */ import java.math.RoundingMode;
/*  40:    */ import java.util.ArrayList;
/*  41:    */ import java.util.Date;
/*  42:    */ import java.util.HashMap;
/*  43:    */ import java.util.Iterator;
/*  44:    */ import java.util.List;
/*  45:    */ import java.util.Map;
/*  46:    */ import java.util.Map.Entry;
/*  47:    */ import java.util.Set;
/*  48:    */ import javax.annotation.Resource;
/*  49:    */ import javax.ejb.EJB;
/*  50:    */ import javax.ejb.SessionContext;
/*  51:    */ import javax.ejb.Stateless;
/*  52:    */ import javax.ejb.TransactionAttribute;
/*  53:    */ import javax.ejb.TransactionAttributeType;
/*  54:    */ import javax.ejb.TransactionManagement;
/*  55:    */ import javax.ejb.TransactionManagementType;
/*  56:    */ import javax.persistence.NoResultException;
/*  57:    */ import javax.persistence.NonUniqueResultException;
/*  58:    */ 
/*  59:    */ @Stateless
/*  60:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  61:    */ public class ServicioFacturaProveedorImportacionImpl
/*  62:    */   implements ServicioFacturaProveedorImportacion
/*  63:    */ {
/*  64:    */   @EJB
/*  65:    */   private transient FacturaProveedorImportacionDao facturaProveedorImportacionDao;
/*  66:    */   @EJB
/*  67:    */   private transient FacturaProveedorDao facturaProveedorDao;
/*  68:    */   @EJB
/*  69:    */   private transient ReporteImportacionDao reporteImportacionDao;
/*  70:    */   @EJB
/*  71:    */   private transient ServicioDetalleFacturaProveedorImportacionGasto servicioDetalleFacturaProveedorImportacionGasto;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioMoneda servicioMoneda;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioRecepcionProveedor servicioRecepcionProveedor;
/*  78:    */   @EJB
/*  79:    */   private transient DetalleProcesoImportacionDao detalleProcesoImportacionDao;
/*  80:    */   @EJB
/*  81:    */   private transient ServicioPeriodo servicioPeriodo;
/*  82:    */   @EJB
/*  83:    */   private transient ServicioDocumentoGastoImportacion servicioDocumentoGastoImportacion;
/*  84:    */   @Resource
/*  85:    */   protected SessionContext context;
/*  86:    */   
/*  87:    */   public void guardar(FacturaProveedorImportacion facturaProveedorImportacion)
/*  88:    */   {
/*  89:110 */     for (DetalleFacturaProveedorImportacionGasto dfpig : facturaProveedorImportacion.getListaDetalleFacturaProveedorImportacionGasto()) {
/*  90:111 */       this.servicioDetalleFacturaProveedorImportacionGasto.guardar(dfpig);
/*  91:    */     }
/*  92:114 */     for (DetalleProcesoImportacion dpi : facturaProveedorImportacion.getListaDetalleProcesoImportacion()) {
/*  93:115 */       this.detalleProcesoImportacionDao.guardar(dpi);
/*  94:    */     }
/*  95:118 */     this.facturaProveedorImportacionDao.guardar(facturaProveedorImportacion);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void eliminar(FacturaProveedorImportacion facturaProveedorImportacion)
/*  99:    */   {
/* 100:130 */     this.facturaProveedorImportacionDao.eliminar(facturaProveedorImportacion);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public FacturaProveedorImportacion buscarPorId(int idFacturaProveedorImportacion)
/* 104:    */   {
/* 105:140 */     return (FacturaProveedorImportacion)this.facturaProveedorImportacionDao.buscarPorId(Integer.valueOf(idFacturaProveedorImportacion));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public List<FacturaProveedorImportacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 109:    */   {
/* 110:152 */     return this.facturaProveedorImportacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<FacturaProveedorImportacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 114:    */   {
/* 115:163 */     return this.facturaProveedorImportacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int contarPorCriterio(Map<String, String> filters)
/* 119:    */   {
/* 120:173 */     return this.facturaProveedorImportacionDao.contarPorCriterio(filters);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public FacturaProveedorImportacion cargarDetalle(int idEntidad)
/* 124:    */   {
/* 125:183 */     return this.facturaProveedorImportacionDao.cargarDetalle(idEntidad);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void liquidarImportacion(FacturaProveedor facturaProveedor, Date fechaContabilizacionImportacion)
/* 129:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 130:    */   {
/* 131:204 */     Date fechaVericadora = fechaContabilizacionImportacion != null ? fechaContabilizacionImportacion : new Date();
/* 132:205 */     fechaVericadora = FuncionesUtiles.setAtributoFecha(fechaVericadora);
/* 133:206 */     if ((facturaProveedor.getRecepcionProveedor() != null) && (fechaVericadora.compareTo(facturaProveedor.getRecepcionProveedor().getFecha()) < 0)) {
/* 134:207 */       throw new AS2Exception("msg_error_fecha_liquidacion_menor_fecha_recepcion", new String[] { "" });
/* 135:    */     }
/* 136:209 */     prorratear(facturaProveedor.getFacturaProveedorImportacion().getId(), fechaContabilizacionImportacion);
/* 137:    */     
/* 138:211 */     facturaProveedor = this.servicioFacturaProveedor.buscarPorId(Integer.valueOf(facturaProveedor.getIdFacturaProveedor()));
/* 139:212 */     facturaProveedor.setEstado(Estado.CERRADO);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public FacturaProveedorImportacion prorratear(int idFacturaProveedorImportacion)
/* 143:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 144:    */   {
/* 145:218 */     return prorratear(idFacturaProveedorImportacion, null);
/* 146:    */   }
/* 147:    */   
/* 148:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 149:    */   private FacturaProveedorImportacion prorratear(int idFacturaProveedorImportacion, Date fechaContabilizacionImportacion)
/* 150:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 151:    */   {
/* 152:    */     try
/* 153:    */     {
/* 154:233 */       int numeroDecimalesProrrateo = 10;
/* 155:    */       
/* 156:    */ 
/* 157:236 */       FacturaProveedorImportacion facturaProveedorImportacion = cargarDetalle(idFacturaProveedorImportacion);
/* 158:239 */       if (facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor() != null)
/* 159:    */       {
/* 160:240 */         Date fechaVerificadora = facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor().getFecha();
/* 161:241 */         int idOrganizacion = facturaProveedorImportacion.getIdOrganizacion();
/* 162:242 */         if ((ParametrosSistema.isContabilizaImportacionBasadaPresupuesto(idOrganizacion).booleanValue()) && (fechaContabilizacionImportacion != null)) {
/* 163:244 */           fechaVerificadora = fechaContabilizacionImportacion;
/* 164:    */         }
/* 165:246 */         this.servicioPeriodo.buscarPorFecha(fechaVerificadora, idOrganizacion, DocumentoBase.FACTURA_PROVEEDOR);
/* 166:    */       }
/* 167:249 */       facturaProveedorImportacion.getFacturaProveedor().getDocumento().setIndicadorDocumentoTributario(false);
/* 168:250 */       facturaProveedorImportacion.getFacturaProveedor().setIndicadorLiquidarImportacion(true);
/* 169:    */       
/* 170:    */ 
/* 171:253 */       HashMap<Integer, GastoImportacion> hashMapGastoImportacionReal = new HashMap();
/* 172:254 */       HashMap<Integer, GastoImportacion> hashMapGastoImportacionPresupuesto = new HashMap();
/* 173:257 */       for (DetalleFacturaProveedorImportacion dfpi : facturaProveedorImportacion.getFacturaProveedor()
/* 174:258 */         .getListaDetalleFacturaProveedorImportacion())
/* 175:    */       {
/* 176:259 */         DetalleFacturaProveedor dfp = dfpi.getDetalleFacturaProveedor();
/* 177:261 */         if (dfp.getFacturaProveedor().getEstado() != Estado.ANULADO)
/* 178:    */         {
/* 179:    */           BigDecimal valorManual;
/* 180:    */           BigDecimal valor;
/* 181:    */           BigDecimal valorManual;
/* 182:264 */           if (!dfpi.isIndicadorDistribucionManual())
/* 183:    */           {
/* 184:265 */             BigDecimal valor = dfpi.getValor();
/* 185:266 */             valorManual = BigDecimal.ZERO;
/* 186:    */           }
/* 187:    */           else
/* 188:    */           {
/* 189:268 */             valor = BigDecimal.ZERO;
/* 190:269 */             valorManual = dfpi.getValor();
/* 191:    */           }
/* 192:272 */           FacturaProveedor fp = dfp.getFacturaProveedor();
/* 193:275 */           if (hashMapGastoImportacionReal.containsKey(Integer.valueOf(dfp.getGastoImportacion().getId())))
/* 194:    */           {
/* 195:277 */             dfp.getGastoImportacion().setTraFecha(fp.getFecha());
/* 196:278 */             GastoImportacion gi = (GastoImportacion)hashMapGastoImportacionReal.get(Integer.valueOf(dfp.getGastoImportacion().getId()));
/* 197:279 */             gi.setTraValor(gi.getTraValor().add(valor));
/* 198:280 */             gi.setTraValorManual(gi.getTraValorManual().add(valorManual));
/* 199:    */           }
/* 200:    */           else
/* 201:    */           {
/* 202:284 */             dfp.getGastoImportacion().setTraValor(valor);
/* 203:285 */             dfp.getGastoImportacion().setTraValorManual(valorManual);
/* 204:286 */             dfp.getGastoImportacion().setTraFecha(fp.getFecha());
/* 205:287 */             hashMapGastoImportacionReal.put(Integer.valueOf(dfp.getGastoImportacion().getId()), dfp.getGastoImportacion());
/* 206:    */           }
/* 207:    */         }
/* 208:    */       }
/* 209:295 */       for (DetalleFacturaProveedorImportacionGasto dfpig : facturaProveedorImportacion.getListaDetalleFacturaProveedorImportacionGasto())
/* 210:    */       {
/* 211:296 */         if (hashMapGastoImportacionReal.containsKey(Integer.valueOf(dfpig.getGastoImportacion().getId())))
/* 212:    */         {
/* 213:298 */           dfpig.setValorReal(((GastoImportacion)hashMapGastoImportacionReal.get(Integer.valueOf(dfpig.getGastoImportacion().getId()))).getTraValor());
/* 214:299 */           dfpig.setTraValorManual(((GastoImportacion)hashMapGastoImportacionReal.get(Integer.valueOf(dfpig.getGastoImportacion().getId()))).getTraValorManual());
/* 215:    */         }
/* 216:    */         else
/* 217:    */         {
/* 218:302 */           dfpig.setValorReal(BigDecimal.ZERO);
/* 219:    */         }
/* 220:304 */         hashMapGastoImportacionPresupuesto.put(Integer.valueOf(dfpig.getGastoImportacion().getId()), dfpig.getGastoImportacion());
/* 221:    */       }
/* 222:307 */       Iterator it = hashMapGastoImportacionReal.entrySet().iterator();
/* 223:308 */       while (it.hasNext())
/* 224:    */       {
/* 225:310 */         Map.Entry e = (Map.Entry)it.next();
/* 226:312 */         if (!hashMapGastoImportacionPresupuesto.containsKey(e.getKey()))
/* 227:    */         {
/* 228:314 */           GastoImportacion gastoImportacion = (GastoImportacion)e.getValue();
/* 229:    */           
/* 230:316 */           DetalleFacturaProveedorImportacionGasto ndfpig = new DetalleFacturaProveedorImportacionGasto();
/* 231:317 */           ndfpig.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 232:318 */           ndfpig.setIdSucursal(AppUtil.getSucursal().getId());
/* 233:319 */           ndfpig.setValorPresupuesto(BigDecimal.ZERO);
/* 234:320 */           ndfpig.setValorReal(gastoImportacion.getTraValor());
/* 235:321 */           ndfpig.setTraValorManual(gastoImportacion.getTraValorManual());
/* 236:322 */           ndfpig.setFechaGasto(gastoImportacion.getTraFecha());
/* 237:323 */           ndfpig.setGastoImportacion(gastoImportacion);
/* 238:324 */           ndfpig.setTipoProrrateoEnum(gastoImportacion.getTipoProrrateo());
/* 239:325 */           ndfpig.setMoneda(this.servicioMoneda.buscarPorId(1));
/* 240:326 */           ndfpig.setFacturaProveedorImportacion(facturaProveedorImportacion);
/* 241:327 */           facturaProveedorImportacion.getListaDetalleFacturaProveedorImportacionGasto().add(ndfpig);
/* 242:    */         }
/* 243:    */       }
/* 244:333 */       facturaProveedorImportacion.setFechaProrrateo(new Date());
/* 245:334 */       guardar(facturaProveedorImportacion);
/* 246:    */       
/* 247:    */ 
/* 248:    */ 
/* 249:338 */       BigDecimal totalPrecioFacturaImportacion = BigDecimal.ZERO;
/* 250:339 */       BigDecimal totalPesoFacturaImportacion = BigDecimal.ZERO;
/* 251:    */       
/* 252:341 */       BigDecimal totalGastoFOB = BigDecimal.ZERO;
/* 253:342 */       BigDecimal totalGastoPeso = BigDecimal.ZERO;
/* 254:343 */       BigDecimal coeficienteFOB = BigDecimal.ZERO;
/* 255:344 */       BigDecimal coeficientePeso = BigDecimal.ZERO;
/* 256:    */       
/* 257:346 */       BigDecimal totalGastoPresupestadoFOB = BigDecimal.ZERO;
/* 258:347 */       BigDecimal totalGastoPresupestadoPeso = BigDecimal.ZERO;
/* 259:348 */       BigDecimal coeficientePresupuestoFOB = BigDecimal.ZERO;
/* 260:349 */       BigDecimal coeficientePresupuestoPeso = BigDecimal.ZERO;
/* 261:351 */       for (DetalleFacturaProveedor dfp : facturaProveedorImportacion.getFacturaProveedor().getListaDetalleFacturaProveedor())
/* 262:    */       {
/* 263:353 */         if (dfp.getPrecio().compareTo(BigDecimal.ZERO) == 0) {
/* 264:354 */           dfp.setPrecio(new BigDecimal(1));
/* 265:    */         }
/* 266:356 */         BigDecimal precioLinea = dfp.getPrecioLinea();
/* 267:    */         
/* 268:358 */         totalPrecioFacturaImportacion = totalPrecioFacturaImportacion.add(precioLinea);
/* 269:360 */         if (dfp.getPesoNeto().compareTo(BigDecimal.ZERO) == 0) {
/* 270:361 */           dfp.setPesoNeto(new BigDecimal(1));
/* 271:    */         }
/* 272:363 */         totalPesoFacturaImportacion = totalPesoFacturaImportacion.add(dfp.getPesoNeto());
/* 273:    */       }
/* 274:368 */       for (DetalleFacturaProveedorImportacionGasto dfpig : facturaProveedorImportacion.getListaDetalleFacturaProveedorImportacionGasto())
/* 275:    */       {
/* 276:370 */         if (dfpig.getTipoProrrateoEnum().equals(TipoProrrateoEnum.FOB))
/* 277:    */         {
/* 278:371 */           totalGastoFOB = totalGastoFOB.add(dfpig.getValorReal());
/* 279:372 */           totalGastoPresupestadoFOB = totalGastoPresupestadoFOB.add(dfpig.getValorPresupuesto());
/* 280:    */         }
/* 281:373 */         else if (dfpig.getTipoProrrateoEnum().equals(TipoProrrateoEnum.PESO))
/* 282:    */         {
/* 283:374 */           totalGastoPeso = totalGastoPeso.add(dfpig.getValorReal());
/* 284:375 */           totalGastoPresupestadoPeso = totalGastoPresupestadoPeso.add(dfpig.getValorPresupuesto());
/* 285:    */         }
/* 286:379 */         dfpig.setValorReal(dfpig.getValorReal().add(dfpig.getTraValorManual()));
/* 287:    */       }
/* 288:382 */       coeficienteFOB = totalGastoFOB.divide(totalPrecioFacturaImportacion, numeroDecimalesProrrateo, RoundingMode.HALF_UP);
/* 289:383 */       coeficientePresupuestoFOB = totalGastoPresupestadoFOB.divide(totalPrecioFacturaImportacion, numeroDecimalesProrrateo, RoundingMode.HALF_UP);
/* 290:    */       
/* 291:    */ 
/* 292:386 */       coeficientePeso = totalGastoPeso.divide(totalPesoFacturaImportacion, numeroDecimalesProrrateo, RoundingMode.HALF_UP);
/* 293:387 */       coeficientePresupuestoPeso = totalGastoPresupestadoPeso.divide(totalPesoFacturaImportacion, numeroDecimalesProrrateo, RoundingMode.HALF_UP);
/* 294:    */       
/* 295:389 */       facturaProveedorImportacion.setTotalValorGastoReal(BigDecimal.ZERO);
/* 296:390 */       for (DetalleFacturaProveedor dfp : facturaProveedorImportacion.getFacturaProveedor().getListaDetalleFacturaProveedor())
/* 297:    */       {
/* 298:392 */         BigDecimal precioLinea = dfp.getPrecioLinea();
/* 299:    */         
/* 300:394 */         BigDecimal gastoFOB = precioLinea.multiply(coeficienteFOB);
/* 301:395 */         BigDecimal gastoPeso = dfp.getPesoNeto().multiply(coeficientePeso);
/* 302:    */         
/* 303:397 */         BigDecimal gastoPresupuestoFOB = precioLinea.multiply(coeficientePresupuestoFOB);
/* 304:398 */         BigDecimal gastoPresupuestoPeso = dfp.getPesoNeto().multiply(coeficientePresupuestoPeso);
/* 305:    */         
/* 306:400 */         dfp.setGastoReal(FuncionesUtiles.redondearBigDecimal(gastoFOB.add(gastoPeso), 
/* 307:401 */           ParametrosSistema.getNumeroDecimalesPrecioCompra(facturaProveedorImportacion.getIdOrganizacion()).intValue()));
/* 308:403 */         for (DetalleFacturaProveedorImportacionProducto dfpip : dfp.getListaDetalleFacturaProveedorImportacionProducto()) {
/* 309:405 */           if (!dfpip.getDetalleFacturaProveedorImportacion().getDetalleFacturaProveedor().getFacturaProveedor().getEstado().equals(Estado.ANULADO)) {
/* 310:406 */             dfp.setGastoReal(dfp.getGastoReal().add(dfpip.getValor()));
/* 311:    */           }
/* 312:    */         }
/* 313:410 */         dfp.setGastoPresupuesto(FuncionesUtiles.redondearBigDecimal(gastoPresupuestoFOB.add(gastoPresupuestoPeso), 
/* 314:411 */           ParametrosSistema.getNumeroDecimalesPrecioCompra(facturaProveedorImportacion.getIdOrganizacion()).intValue()));
/* 315:412 */         facturaProveedorImportacion.setTotalValorGastoReal(facturaProveedorImportacion.getTotalValorGastoReal().add(dfp.getGastoReal()));
/* 316:    */       }
/* 317:415 */       facturaProveedorImportacion.setTotalValorGastoReal(FuncionesUtiles.redondearBigDecimal(facturaProveedorImportacion.getTotalValorGastoReal()));
/* 318:    */       
/* 319:417 */       this.servicioFacturaProveedor.guardar(facturaProveedorImportacion.getFacturaProveedor(), false);
/* 320:418 */       if (facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor() != null)
/* 321:    */       {
/* 322:420 */         RecepcionProveedor recepcionProveedorRecuperado = this.servicioRecepcionProveedor.cargarDetalle(facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor());
/* 323:421 */         facturaProveedorImportacion.getFacturaProveedor().setRecepcionProveedor(recepcionProveedorRecuperado);
/* 324:422 */         recepcionProveedorRecuperado.setFacturaProveedor(facturaProveedorImportacion.getFacturaProveedor());
/* 325:423 */         recepcionProveedorRecuperado.setFechaContabilizacionImportacion(fechaContabilizacionImportacion);
/* 326:424 */         this.servicioRecepcionProveedor.guardar(recepcionProveedorRecuperado, true, false);
/* 327:    */       }
/* 328:426 */       return facturaProveedorImportacion.getFacturaProveedor().getFacturaProveedorImportacion();
/* 329:    */     }
/* 330:    */     catch (NoResultException e)
/* 331:    */     {
/* 332:429 */       this.context.setRollbackOnly();
/* 333:430 */       throw e;
/* 334:    */     }
/* 335:    */     catch (NonUniqueResultException e)
/* 336:    */     {
/* 337:432 */       this.context.setRollbackOnly();
/* 338:433 */       throw e;
/* 339:    */     }
/* 340:    */     catch (ExcepcionAS2Financiero e)
/* 341:    */     {
/* 342:435 */       this.context.setRollbackOnly();
/* 343:436 */       throw e;
/* 344:    */     }
/* 345:    */     catch (ExcepcionAS2 e)
/* 346:    */     {
/* 347:438 */       this.context.setRollbackOnly();
/* 348:439 */       throw e;
/* 349:    */     }
/* 350:    */     catch (Exception e)
/* 351:    */     {
/* 352:441 */       this.context.setRollbackOnly();
/* 353:442 */       throw new ExcepcionAS2(e);
/* 354:    */     }
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void validarFacturaProveedorImportacion(FacturaProveedorImportacion facturaProveedorImportacion)
/* 358:    */     throws ExcepcionAS2, AS2Exception
/* 359:    */   {
/* 360:455 */     if (facturaProveedorImportacion != null)
/* 361:    */     {
/* 362:456 */       if (facturaProveedorImportacion.getCuentaContableImportacion() == null) {
/* 363:457 */         throw new ExcepcionAS2Financiero("msg_info_seleccionar_cuenta_contable");
/* 364:    */       }
/* 365:459 */       if (facturaProveedorImportacion.getPais().getId() == 0) {
/* 366:460 */         throw new ExcepcionAS2("msg_info_seleccionar_pais");
/* 367:    */       }
/* 368:    */     }
/* 369:463 */     if (ParametrosSistema.isContabilizaImportacionBasadaPresupuesto(facturaProveedorImportacion.getIdOrganizacion()).booleanValue())
/* 370:    */     {
/* 371:464 */       int numeroGastosFacturaExterior = 0;
/* 372:465 */       for (DetalleFacturaProveedorImportacionGasto det : facturaProveedorImportacion.getListaDetalleFacturaProveedorImportacionGasto()) {
/* 373:466 */         if (det.getGastoImportacion().isIndicadorFacturaExterior()) {
/* 374:467 */           numeroGastosFacturaExterior++;
/* 375:    */         }
/* 376:    */       }
/* 377:470 */       if (numeroGastosFacturaExterior == 0) {
/* 378:471 */         throw new AS2Exception("msg_error_ingresar_gasto_factura_exterior", new String[] { "" });
/* 379:    */       }
/* 380:472 */       if (numeroGastosFacturaExterior > 1) {
/* 381:473 */         throw new AS2Exception("msg_error_cantidad_gasto_factura_exterior_excedido", new String[] { "" });
/* 382:    */       }
/* 383:    */     }
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void guardarDatosImportacion(FacturaProveedorImportacion facturaProveedorImportacion)
/* 387:    */   {
/* 388:486 */     this.facturaProveedorImportacionDao.guardar(facturaProveedorImportacion);
/* 389:    */   }
/* 390:    */   
/* 391:    */   public List<DetalleFacturaProveedorImportacion> cargarFacturasProveedor(int facturaProveedorImportacion)
/* 392:    */     throws ExcepcionAS2
/* 393:    */   {
/* 394:492 */     return this.facturaProveedorImportacionDao.cargarFacturasProveedor(facturaProveedorImportacion);
/* 395:    */   }
/* 396:    */   
/* 397:    */   public List<Object[]> getFacturasProveedorImportacion(int idEmpresa, int idFacturaProveedor)
/* 398:    */     throws ExcepcionAS2
/* 399:    */   {
/* 400:498 */     List<Object[]> listaDatosFacturasProveedor = this.reporteImportacionDao.getReporteLiquidacionFacturaImportacion(null, null, idEmpresa, 0, idFacturaProveedor);
/* 401:    */     
/* 402:500 */     return listaDatosFacturasProveedor;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void agregarPresupuestoImportacion(FacturaProveedor facturaProveedor, boolean guarda)
/* 406:    */     throws AS2Exception
/* 407:    */   {
/* 408:507 */     int numeroGastosFacturaExterior = 0;
/* 409:    */     
/* 410:    */ 
/* 411:    */ 
/* 412:    */ 
/* 413:512 */     List<DocumentoGastoImportacion> lista = new ArrayList();
/* 414:513 */     lista = this.servicioDocumentoGastoImportacion.obtenerListaDocumentoGastoImportacion(facturaProveedor.getDocumento(), true);
/* 415:    */     
/* 416:    */ 
/* 417:    */ 
/* 418:    */ 
/* 419:518 */     Map<Integer, DetalleFacturaProveedorImportacionGasto> hashGastosImportacion = new HashMap();
/* 420:519 */     for (Iterator localIterator = facturaProveedor.getFacturaProveedorImportacion()
/* 421:520 */           .getListaDetalleFacturaProveedorImportacionGasto().iterator(); localIterator.hasNext();)
/* 422:    */     {
/* 423:519 */       det = (DetalleFacturaProveedorImportacionGasto)localIterator.next();
/* 424:    */       
/* 425:521 */       hashGastosImportacion.put(Integer.valueOf(det.getGastoImportacion().getId()), det);
/* 426:    */     }
/* 427:    */     DetalleFacturaProveedorImportacionGasto det;
/* 428:523 */     Object listaDetallesGastos = new ArrayList();
/* 429:524 */     for (DocumentoGastoImportacion documentoGastoImportacion : lista)
/* 430:    */     {
/* 431:526 */       if (documentoGastoImportacion.getGastoImportacion().isIndicadorFacturaExterior()) {
/* 432:527 */         numeroGastosFacturaExterior++;
/* 433:    */       }
/* 434:529 */       if (numeroGastosFacturaExterior > 1) {
/* 435:530 */         throw new AS2Exception("msg_error_cantidad_gasto_factura_exterior_excedido", new String[] { "" });
/* 436:    */       }
/* 437:532 */       DetalleFacturaProveedorImportacionGasto detalleFacturaProveedorImportacionGasto = (DetalleFacturaProveedorImportacionGasto)hashGastosImportacion.get(Integer.valueOf(documentoGastoImportacion.getGastoImportacion().getId()));
/* 438:533 */       if (detalleFacturaProveedorImportacionGasto == null)
/* 439:    */       {
/* 440:534 */         detalleFacturaProveedorImportacionGasto = new DetalleFacturaProveedorImportacionGasto();
/* 441:535 */         detalleFacturaProveedorImportacionGasto.setFechaGasto(facturaProveedor.getFecha());
/* 442:536 */         detalleFacturaProveedorImportacionGasto.setFacturaProveedorImportacion(facturaProveedor.getFacturaProveedorImportacion());
/* 443:537 */         detalleFacturaProveedorImportacionGasto.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 444:538 */         detalleFacturaProveedorImportacionGasto.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 445:    */       }
/* 446:    */       else
/* 447:    */       {
/* 448:540 */         hashGastosImportacion.remove(Integer.valueOf(documentoGastoImportacion.getGastoImportacion().getId()));
/* 449:    */       }
/* 450:542 */       calcularValorPresupuestado(detalleFacturaProveedorImportacionGasto, documentoGastoImportacion.getGastoImportacion(), facturaProveedor
/* 451:543 */         .getTotalFactura(), facturaProveedor.getFacturaProveedorImportacion().getMoneda());
/* 452:544 */       ((List)listaDetallesGastos).add(detalleFacturaProveedorImportacionGasto);
/* 453:    */     }
/* 454:546 */     facturaProveedor.getFacturaProveedorImportacion().setListaDetalleFacturaProveedorImportacionGasto((List)listaDetallesGastos);
/* 455:547 */     facturaProveedor.getFacturaProveedorImportacion().getListaDetalleFacturaProveedorImportacionGasto().addAll(hashGastosImportacion.values());
/* 456:548 */     if (guarda) {
/* 457:549 */       for (DetalleFacturaProveedorImportacionGasto det : facturaProveedor.getFacturaProveedorImportacion()
/* 458:550 */         .getListaDetalleFacturaProveedorImportacionGasto()) {
/* 459:551 */         this.servicioDetalleFacturaProveedorImportacionGasto.guardar(det);
/* 460:    */       }
/* 461:    */     }
/* 462:    */   }
/* 463:    */   
/* 464:    */   private void calcularValorPresupuestado(DetalleFacturaProveedorImportacionGasto detalleFacturaProveedorImportacionGasto, GastoImportacion gastoImportacion, BigDecimal totalFactura, Moneda moneda)
/* 465:    */   {
/* 466:560 */     BigDecimal valorPresupuesto = BigDecimal.ZERO;
/* 467:561 */     if (gastoImportacion.isIndicadorCalculoAutomatico())
/* 468:    */     {
/* 469:562 */       BigDecimal valorGasto = FuncionesUtiles.porcentaje(totalFactura, gastoImportacion.getPorcentaje(), 6);
/* 470:563 */       valorPresupuesto = valorGasto.add(gastoImportacion.getValorManual());
/* 471:    */     }
/* 472:566 */     if (gastoImportacion.isIndicadorFacturaExterior()) {
/* 473:567 */       valorPresupuesto = totalFactura;
/* 474:    */     }
/* 475:569 */     detalleFacturaProveedorImportacionGasto.setTipoProrrateoEnum(gastoImportacion.getTipoProrrateo());
/* 476:570 */     if (BigDecimal.ZERO.compareTo(valorPresupuesto) != 0) {
/* 477:571 */       detalleFacturaProveedorImportacionGasto.setValorPresupuesto(FuncionesUtiles.redondearBigDecimal(valorPresupuesto));
/* 478:    */     }
/* 479:572 */     detalleFacturaProveedorImportacionGasto.setGastoImportacion(gastoImportacion);
/* 480:573 */     detalleFacturaProveedorImportacionGasto.setMoneda(moneda);
/* 481:    */   }
/* 482:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.procesos.servicio.impl.ServicioFacturaProveedorImportacionImpl
 * JD-Core Version:    0.7.0.1
 */