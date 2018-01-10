/*   1:    */ package com.asinfo.as2.produccion.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CuentaContableDao;
/*   4:    */ import com.asinfo.as2.dao.GenericoDao;
/*   5:    */ import com.asinfo.as2.dao.InventarioProductoDao;
/*   6:    */ import com.asinfo.as2.dao.produccion.CostosDeFabricacionDao;
/*   7:    */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioComponenteCosto;
/*   9:    */ import com.asinfo.as2.entities.Asiento;
/*  10:    */ import com.asinfo.as2.entities.ComponenteCosto;
/*  11:    */ import com.asinfo.as2.entities.CriterioContabilizacion;
/*  12:    */ import com.asinfo.as2.entities.CuentaContable;
/*  13:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  14:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  15:    */ import com.asinfo.as2.entities.DimensionContable;
/*  16:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  17:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  18:    */ import com.asinfo.as2.entities.Organizacion;
/*  19:    */ import com.asinfo.as2.entities.Producto;
/*  20:    */ import com.asinfo.as2.entities.SubProducto;
/*  21:    */ import com.asinfo.as2.entities.Sucursal;
/*  22:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  23:    */ import com.asinfo.as2.entities.produccion.CostosDeFabricacion;
/*  24:    */ import com.asinfo.as2.entities.produccion.DetalleCostoFabricacion;
/*  25:    */ import com.asinfo.as2.entities.produccion.OperacionOrdenFabricacion;
/*  26:    */ import com.asinfo.as2.entities.produccion.OperacionProduccion;
/*  27:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  28:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*  29:    */ import com.asinfo.as2.entities.produccion.TareaProduccion;
/*  30:    */ import com.asinfo.as2.entities.produccion.TarifaOperacion;
/*  31:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  32:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  33:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  34:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*  35:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  36:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*  37:    */ import com.asinfo.as2.enumeraciones.TipoComponenteCostoEnum;
/*  38:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  39:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  40:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  41:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  42:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  43:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  44:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  45:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  46:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*  47:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  48:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioCostosDeFabricacion;
/*  49:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  50:    */ import com.asinfo.as2.util.AppUtil;
/*  51:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  52:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  53:    */ import java.math.BigDecimal;
/*  54:    */ import java.math.RoundingMode;
/*  55:    */ import java.util.ArrayList;
/*  56:    */ import java.util.Collection;
/*  57:    */ import java.util.Date;
/*  58:    */ import java.util.HashMap;
/*  59:    */ import java.util.Iterator;
/*  60:    */ import java.util.List;
/*  61:    */ import java.util.Map;
/*  62:    */ import java.util.Set;
/*  63:    */ import javax.annotation.Resource;
/*  64:    */ import javax.ejb.EJB;
/*  65:    */ import javax.ejb.SessionContext;
/*  66:    */ import javax.ejb.Stateless;
/*  67:    */ import javax.ejb.TransactionAttribute;
/*  68:    */ import javax.ejb.TransactionAttributeType;
/*  69:    */ import javax.ejb.TransactionManagement;
/*  70:    */ import javax.ejb.TransactionManagementType;
/*  71:    */ 
/*  72:    */ @Stateless
/*  73:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  74:    */ public class ServicioCostosDeFabricacionImpl
/*  75:    */   implements ServicioCostosDeFabricacion
/*  76:    */ {
/*  77:    */   @EJB
/*  78:    */   private ServicioProducto servicioProducto;
/*  79:    */   @EJB
/*  80:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  81:    */   @EJB
/*  82:    */   private InventarioProductoDao inventarioProductoDao;
/*  83:    */   @EJB
/*  84:    */   private CostosDeFabricacionDao costosDeFabricacionDao;
/*  85:    */   @EJB
/*  86:    */   private OrdenFabricacionDao ordenFabricacionDao;
/*  87:    */   @EJB
/*  88:    */   private ServicioInventarioProducto servicioInventarioProducto;
/*  89:    */   @EJB
/*  90:    */   private ServicioAsiento servicioAsiento;
/*  91:    */   @EJB
/*  92:    */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  93:    */   @EJB
/*  94:    */   private CuentaContableDao cuentaContableDao;
/*  95:    */   @EJB
/*  96:    */   private ServicioComponenteCosto servicioComponenteCosto;
/*  97:    */   @EJB
/*  98:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  99:    */   @EJB
/* 100:    */   private GenericoDao<DetalleCostoFabricacion> detalleCostoFabricacionDao;
/* 101:    */   @EJB
/* 102:    */   private ServicioCosteo servicioCosteo;
/* 103:    */   @EJB
/* 104:    */   private ServicioPeriodo servicioPeriodo;
/* 105:    */   @Resource
/* 106:    */   protected SessionContext context;
/* 107:    */   
/* 108:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 109:    */   public void guardar(CostosDeFabricacion entidad)
/* 110:    */     throws AS2Exception
/* 111:    */   {
/* 112:    */     try
/* 113:    */     {
/* 114:122 */       validar(entidad);
/* 115:123 */       this.costosDeFabricacionDao.guardar(entidad);
/* 116:124 */       for (DetalleCostoFabricacion detalle : entidad.getListaDetalleCostoFabricacion())
/* 117:    */       {
/* 118:125 */         this.detalleCostoFabricacionDao.guardar(detalle);
/* 119:126 */         this.servicioInventarioProducto.actualizarComponentesCostoFabricacion(detalle);
/* 120:    */       }
/* 121:    */     }
/* 122:    */     catch (AS2Exception e)
/* 123:    */     {
/* 124:129 */       this.context.setRollbackOnly();
/* 125:130 */       throw e;
/* 126:    */     }
/* 127:    */     catch (Exception e)
/* 128:    */     {
/* 129:132 */       e.printStackTrace();
/* 130:133 */       this.context.setRollbackOnly();
/* 131:134 */       throw new AS2Exception(e.getMessage());
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   private void validar(CostosDeFabricacion costosDefabricacion)
/* 136:    */     throws AS2Exception
/* 137:    */   {
/* 138:140 */     Map<String, String> filtro = new HashMap();
/* 139:141 */     filtro.put("idOrganizacion", costosDefabricacion.getIdOrganizacion() + "");
/* 140:142 */     filtro.put("anio", "=" + costosDefabricacion.getAnio());
/* 141:143 */     filtro.put("mes", "=" + costosDefabricacion.getMes());
/* 142:144 */     List<CostosDeFabricacion> lista = this.costosDeFabricacionDao.obtenerListaCombo("anio", true, filtro);
/* 143:145 */     for (CostosDeFabricacion base : lista) {
/* 144:146 */       if (base.getId() != costosDefabricacion.getId()) {
/* 145:147 */         throw new AS2Exception("msg_error_codigo_repetido", new String[] { costosDefabricacion.getAnio() + "-" + costosDefabricacion.getMes() });
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void eliminar(CostosDeFabricacion entidad)
/* 151:    */   {
/* 152:154 */     this.costosDeFabricacionDao.eliminar(entidad);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<CostosDeFabricacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 156:    */   {
/* 157:160 */     return this.costosDeFabricacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int contarPorCriterio(Map<String, String> filters)
/* 161:    */   {
/* 162:165 */     return this.costosDeFabricacionDao.contarPorCriterio(filters);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public CostosDeFabricacion buscarPorId(Integer id)
/* 166:    */   {
/* 167:170 */     return (CostosDeFabricacion)this.costosDeFabricacionDao.buscarPorId(id);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public CostosDeFabricacion cargarDetalle(Integer id)
/* 171:    */   {
/* 172:175 */     return this.costosDeFabricacionDao.cargarDetalle(id.intValue());
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void generarCostosDeFabricacion(OrdenFabricacion ordenFabricacion, CostosDeFabricacion costosDeFabricacion)
/* 176:    */   {
/* 177:187 */     Producto productoPrincipal = ordenFabricacion.getProducto();
/* 178:    */     
/* 179:    */ 
/* 180:190 */     List<DetalleMovimientoInventario> listaFabricacion = this.costosDeFabricacionDao.getFabricacion(ordenFabricacion);
/* 181:191 */     Map<Producto, CostoFabricacion> hmProductosFabricados = new HashMap();
/* 182:194 */     for (DetalleMovimientoInventario dMI : listaFabricacion)
/* 183:    */     {
/* 184:196 */       costoFabricacion = (CostoFabricacion)hmProductosFabricados.get(dMI.getProducto());
/* 185:198 */       if (costoFabricacion != null)
/* 186:    */       {
/* 187:199 */         costoFabricacion.setTotalCantidad(costoFabricacion.getTotalCantidad().add(dMI.getCantidad()));
/* 188:    */       }
/* 189:    */       else
/* 190:    */       {
/* 191:201 */         costoFabricacion = new CostoFabricacion(null);
/* 192:202 */         costoFabricacion.setProducto(dMI.getProducto());
/* 193:203 */         costoFabricacion.setTotalCantidad(dMI.getCantidad());
/* 194:204 */         hmProductosFabricados.put(costoFabricacion.getProducto(), costoFabricacion);
/* 195:    */       }
/* 196:    */     }
/* 197:209 */     BigDecimal porcentajeCostoSubproductos = BigDecimal.ZERO;
/* 198:210 */     List<SubProducto> listaSubProducto = this.servicioProducto.getListaSubproducto(productoPrincipal);
/* 199:211 */     for (CostoFabricacion costoFabricacion = listaSubProducto.iterator(); costoFabricacion.hasNext();)
/* 200:    */     {
/* 201:211 */       subProducto = (SubProducto)costoFabricacion.next();
/* 202:212 */       CostoFabricacion costoFabricacion = (CostoFabricacion)hmProductosFabricados.get(subProducto.getProducto());
/* 203:214 */       if (costoFabricacion != null)
/* 204:    */       {
/* 205:215 */         costoFabricacion.setIndicadorSubproducto(true);
/* 206:216 */         costoFabricacion.setPorcentajeCostos(subProducto.getPorcentajeCosto());
/* 207:217 */         porcentajeCostoSubproductos = porcentajeCostoSubproductos.add(subProducto.getPorcentajeCosto());
/* 208:    */       }
/* 209:    */     }
/* 210:222 */     BigDecimal porcentajeCostoProducto = new BigDecimal(100).subtract(porcentajeCostoSubproductos);
/* 211:223 */     for (SubProducto subProducto = hmProductosFabricados.values().iterator(); subProducto.hasNext();)
/* 212:    */     {
/* 213:223 */       costoFabricacion = (CostoFabricacion)subProducto.next();
/* 214:224 */       if (!costoFabricacion.isIndicadorSubproducto()) {
/* 215:225 */         costoFabricacion.setPorcentajeCostos(porcentajeCostoProducto);
/* 216:    */       }
/* 217:    */     }
/* 218:    */     CostoFabricacion costoFabricacion;
/* 219:230 */     BigDecimal costoMateriales = getCostosMateriales(ordenFabricacion);
/* 220:231 */     costoMateriales = costoMateriales == null ? BigDecimal.ZERO : costoMateriales;
/* 221:232 */     for (DetalleMovimientoInventario dMI : listaFabricacion)
/* 222:    */     {
/* 223:233 */       CostoFabricacion costoFabricacion = (CostoFabricacion)hmProductosFabricados.get(dMI.getProducto());
/* 224:    */       
/* 225:235 */       BigDecimal costoMaterial = costosDeFabricacion.getCostoMateriales().multiply(dMI
/* 226:236 */         .getCantidad().multiply(costoFabricacion.getPorcentajeCostos().divide(new BigDecimal(100.0D), 2, RoundingMode.HALF_UP))
/* 227:237 */         .divide(costoFabricacion.getTotalCantidad(), 4, RoundingMode.HALF_UP));
/* 228:    */       
/* 229:239 */       BigDecimal costoDepreciacion = costosDeFabricacion.getCostoDepreciaciones().multiply(dMI
/* 230:240 */         .getCantidad().multiply(costoFabricacion.getPorcentajeCostos().divide(new BigDecimal(100.0D), 2, RoundingMode.HALF_UP))
/* 231:241 */         .divide(costoFabricacion.getTotalCantidad(), 4, RoundingMode.HALF_UP));
/* 232:    */       
/* 233:243 */       BigDecimal costoIndirecto = costosDeFabricacion.getCostoIndirectos().multiply(dMI
/* 234:244 */         .getCantidad().multiply(costoFabricacion.getPorcentajeCostos().divide(new BigDecimal(100.0D), 2, RoundingMode.HALF_UP))
/* 235:245 */         .divide(costoFabricacion.getTotalCantidad(), 4, RoundingMode.HALF_UP));
/* 236:    */       
/* 237:247 */       BigDecimal costoManoDeObra = costosDeFabricacion.getCostoManoDeObra().multiply(dMI
/* 238:248 */         .getCantidad().multiply(costoFabricacion.getPorcentajeCostos().divide(new BigDecimal(100.0D), 2, RoundingMode.HALF_UP))
/* 239:249 */         .divide(costoFabricacion.getTotalCantidad(), 4, RoundingMode.HALF_UP));
/* 240:    */       
/* 241:251 */       BigDecimal costoTotal = costoMaterial.add(costoDepreciacion).add(costoIndirecto).add(costoManoDeObra);
/* 242:    */       
/* 243:253 */       dMI.getInventarioProducto().setCostoMateriales(costoMaterial);
/* 244:254 */       dMI.getInventarioProducto().setCostoDepreciaciones(costoDepreciacion);
/* 245:255 */       dMI.getInventarioProducto().setCostoIndirectos(costoIndirecto);
/* 246:256 */       dMI.getInventarioProducto().setCostoManoDeObra(costoManoDeObra);
/* 247:257 */       dMI.getInventarioProducto().setCostoTotal(costoTotal);
/* 248:258 */       dMI.getInventarioProducto().setCosto(costoTotal);
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void generarCostosEstandar(Date fechaDesde, Date fechaHasta) {}
/* 253:    */   
/* 254:    */   private class CostoFabricacion
/* 255:    */   {
/* 256:    */     private Producto producto;
/* 257:    */     private BigDecimal totalCantidad;
/* 258:    */     private BigDecimal porcentajeCostos;
/* 259:295 */     private boolean indicadorSubproducto = false;
/* 260:    */     
/* 261:    */     private CostoFabricacion() {}
/* 262:    */     
/* 263:    */     public Producto getProducto()
/* 264:    */     {
/* 265:303 */       return this.producto;
/* 266:    */     }
/* 267:    */     
/* 268:    */     public void setProducto(Producto producto)
/* 269:    */     {
/* 270:313 */       this.producto = producto;
/* 271:    */     }
/* 272:    */     
/* 273:    */     public BigDecimal getTotalCantidad()
/* 274:    */     {
/* 275:322 */       return this.totalCantidad;
/* 276:    */     }
/* 277:    */     
/* 278:    */     public void setTotalCantidad(BigDecimal totalCantidad)
/* 279:    */     {
/* 280:332 */       this.totalCantidad = totalCantidad;
/* 281:    */     }
/* 282:    */     
/* 283:    */     public BigDecimal getPorcentajeCostos()
/* 284:    */     {
/* 285:341 */       return this.porcentajeCostos;
/* 286:    */     }
/* 287:    */     
/* 288:    */     public void setPorcentajeCostos(BigDecimal porcentajeCostos)
/* 289:    */     {
/* 290:351 */       this.porcentajeCostos = porcentajeCostos;
/* 291:    */     }
/* 292:    */     
/* 293:    */     public boolean isIndicadorSubproducto()
/* 294:    */     {
/* 295:360 */       return this.indicadorSubproducto;
/* 296:    */     }
/* 297:    */     
/* 298:    */     public void setIndicadorSubproducto(boolean indicadorSubproducto)
/* 299:    */     {
/* 300:370 */       this.indicadorSubproducto = indicadorSubproducto;
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   public BigDecimal getCostosMateriales(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 305:    */   {
/* 306:382 */     return this.costosDeFabricacionDao.getCostosMateriales(Integer.valueOf(idOrganizacion), fechaDesde, fechaHasta, null);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public BigDecimal getCostosMateriales(OrdenFabricacion ordenFabricacion)
/* 310:    */   {
/* 311:394 */     return this.costosDeFabricacionDao.getCostosMateriales(null, null, null, ordenFabricacion);
/* 312:    */   }
/* 313:    */   
/* 314:    */   private BigDecimal obtenerPesoCostoOrdenFabricacion(OrdenFabricacion ordenFabricacion, ComponenteCosto componenteCosto)
/* 315:    */   {
/* 316:399 */     BigDecimal pesoOrden = BigDecimal.ZERO;
/* 317:404 */     if (componenteCosto.getIndicadorCoeficienteProduccion().booleanValue()) {
/* 318:405 */       pesoOrden = ordenFabricacion.getCantidadFabricada().multiply(ordenFabricacion.getProducto().getCoeficienteProduccion()).setScale(4, RoundingMode.HALF_UP);
/* 319:    */     } else {
/* 320:408 */       for (OperacionOrdenFabricacion operacionOrdenFabricacion : ordenFabricacion.getListaOperacionProduccion())
/* 321:    */       {
/* 322:409 */         BigDecimal tarifa = operacionOrdenFabricacion.getOperacionProduccion().getTareaProduccion().getTarifaOperacion().getCosto();
/* 323:414 */         if (operacionOrdenFabricacion.getOperacionProduccion().isIndicadorFijo())
/* 324:    */         {
/* 325:415 */           operacionOrdenFabricacion.setHorasHombre(new BigDecimal(operacionOrdenFabricacion.getOperacionProduccion().getNumeroPersonas()));
/* 326:416 */           operacionOrdenFabricacion.setHorasMaquina(new BigDecimal(operacionOrdenFabricacion.getOperacionProduccion().getNumeroMaquinas()));
/* 327:    */         }
/* 328:419 */         if (componenteCosto.getIndicadorProrratearHorasHombre().booleanValue()) {
/* 329:420 */           pesoOrden = pesoOrden.add(operacionOrdenFabricacion.getHorasHombre());
/* 330:    */         }
/* 331:422 */         if (componenteCosto.getIndicadorProrratearHorasHombreXValor().booleanValue()) {
/* 332:423 */           pesoOrden = pesoOrden.add(operacionOrdenFabricacion.getHorasHombre().multiply(tarifa).setScale(4, RoundingMode.HALF_UP));
/* 333:    */         }
/* 334:425 */         if (componenteCosto.getIndicadorProrratearHorasMaquina().booleanValue()) {
/* 335:426 */           pesoOrden = pesoOrden.add(operacionOrdenFabricacion.getHorasMaquina());
/* 336:    */         }
/* 337:428 */         if (componenteCosto.getIndicadorProrratearHorasMaquinaXValor().booleanValue()) {
/* 338:429 */           pesoOrden = pesoOrden.add(operacionOrdenFabricacion.getHorasMaquina().multiply(tarifa).setScale(4, RoundingMode.HALF_UP));
/* 339:    */         }
/* 340:431 */         if (componenteCosto.getIndicadorCoeficienteProduccion().booleanValue()) {
/* 341:433 */           pesoOrden = pesoOrden.add(ordenFabricacion.getCantidadFabricada().multiply(ordenFabricacion.getProducto().getCoeficienteProduccion()));
/* 342:    */         }
/* 343:    */       }
/* 344:    */     }
/* 345:438 */     return pesoOrden;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void prorratearComponentesCosto(List<OrdenFabricacion> listaOrdenFabricacion, ComponenteCosto componenteCosto, BigDecimal valorProrratear)
/* 349:    */   {
/* 350:444 */     TipoComponenteCostoEnum tipoComponente = componenteCosto.getTipoComponenteCostoEnum();
/* 351:445 */     BigDecimal totalPesoOrden = BigDecimal.ZERO;
/* 352:446 */     Map<Integer, BigDecimal> mapaPesoOrden = new HashMap();
/* 353:449 */     for (Iterator localIterator = listaOrdenFabricacion.iterator(); localIterator.hasNext();)
/* 354:    */     {
/* 355:449 */       ordenFabricacion = (OrdenFabricacion)localIterator.next();
/* 356:450 */       BigDecimal pesoOrden = obtenerPesoCostoOrdenFabricacion(ordenFabricacion, componenteCosto);
/* 357:    */       
/* 358:452 */       mapaPesoOrden.put(Integer.valueOf(ordenFabricacion.getId()), pesoOrden);
/* 359:453 */       totalPesoOrden = totalPesoOrden.add(pesoOrden);
/* 360:    */     }
/* 361:    */     OrdenFabricacion ordenFabricacion;
/* 362:459 */     BigDecimal totalValorProrratear = BigDecimal.ZERO;
/* 363:460 */     for (OrdenFabricacion ordenFabricacion : listaOrdenFabricacion)
/* 364:    */     {
/* 365:461 */       BigDecimal pesoOrden = (BigDecimal)mapaPesoOrden.get(Integer.valueOf(ordenFabricacion.getId()));
/* 366:462 */       BigDecimal proporcionOrden = BigDecimal.ZERO;
/* 367:463 */       if (totalPesoOrden.compareTo(BigDecimal.ZERO) > 0) {
/* 368:464 */         proporcionOrden = pesoOrden.divide(totalPesoOrden, 6, RoundingMode.HALF_UP);
/* 369:    */       }
/* 370:466 */       BigDecimal valorAsignar = valorProrratear.multiply(proporcionOrden).setScale(4, RoundingMode.HALF_UP);
/* 371:467 */       totalValorProrratear = totalValorProrratear.add(valorAsignar);
/* 372:468 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$TipoComponenteCostoEnum[tipoComponente.ordinal()])
/* 373:    */       {
/* 374:    */       case 1: 
/* 375:470 */         BigDecimal valorAnterior = ordenFabricacion.getCostoManoObra();
/* 376:471 */         valorAsignar = valorAsignar.add(valorAnterior);
/* 377:472 */         ordenFabricacion.setCostoManoObra(valorAsignar);
/* 378:    */         
/* 379:    */ 
/* 380:    */ 
/* 381:476 */         break;
/* 382:    */       case 2: 
/* 383:479 */         BigDecimal valorAnterior = ordenFabricacion.getCostoDepreciacion();
/* 384:480 */         valorAsignar = valorAsignar.add(valorAnterior);
/* 385:481 */         ordenFabricacion.setCostoDepreciacion(valorAsignar);
/* 386:    */         
/* 387:    */ 
/* 388:    */ 
/* 389:485 */         break;
/* 390:    */       case 3: 
/* 391:488 */         BigDecimal valorAnterior = ordenFabricacion.getCostoIndirecto();
/* 392:489 */         valorAsignar = valorAsignar.add(valorAnterior);
/* 393:490 */         ordenFabricacion.setCostoIndirecto(valorAsignar);
/* 394:    */         
/* 395:    */ 
/* 396:    */ 
/* 397:494 */         break;
/* 398:    */       case 4: 
/* 399:497 */         BigDecimal valorAnterior = ordenFabricacion.getCostoMateriales();
/* 400:498 */         valorAsignar = valorAsignar.add(valorAnterior);
/* 401:499 */         ordenFabricacion.setCostoMateriales(valorAsignar);
/* 402:    */       }
/* 403:    */     }
/* 404:508 */     BigDecimal diferencia = valorProrratear.subtract(totalValorProrratear);
/* 405:509 */     if ((diferencia.compareTo(BigDecimal.ZERO) > 0) && (listaOrdenFabricacion.size() > 0)) {
/* 406:511 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$TipoComponenteCostoEnum[tipoComponente.ordinal()])
/* 407:    */       {
/* 408:    */       case 1: 
/* 409:513 */         BigDecimal costo = ((OrdenFabricacion)listaOrdenFabricacion.get(0)).getCostoManoObra();
/* 410:514 */         ((OrdenFabricacion)listaOrdenFabricacion.get(0)).setCostoManoObra(costo.add(diferencia));
/* 411:515 */         break;
/* 412:    */       case 2: 
/* 413:518 */         BigDecimal costo = ((OrdenFabricacion)listaOrdenFabricacion.get(0)).getCostoDepreciacion();
/* 414:519 */         ((OrdenFabricacion)listaOrdenFabricacion.get(0)).setCostoDepreciacion(costo.add(diferencia));
/* 415:520 */         break;
/* 416:    */       case 3: 
/* 417:523 */         BigDecimal costo = ((OrdenFabricacion)listaOrdenFabricacion.get(0)).getCostoIndirecto();
/* 418:524 */         ((OrdenFabricacion)listaOrdenFabricacion.get(0)).setCostoIndirecto(costo.add(diferencia));
/* 419:525 */         break;
/* 420:    */       case 4: 
/* 421:528 */         BigDecimal costo = ((OrdenFabricacion)listaOrdenFabricacion.get(0)).getCostoMateriales();
/* 422:529 */         ((OrdenFabricacion)listaOrdenFabricacion.get(0)).setCostoMateriales(costo.add(diferencia));
/* 423:530 */         break;
/* 424:    */       }
/* 425:    */     }
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void contabilizar(CostosDeFabricacion costosDeFabricacion)
/* 429:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 430:    */   {
/* 431:542 */     List<Object[]> detalles = this.cuentaContableDao.obtenerCierreCostosPorComponenteCosto(costosDeFabricacion.getIdOrganizacion(), costosDeFabricacion
/* 432:543 */       .getAnio().intValue(), costosDeFabricacion.getMes().intValue());
/* 433:544 */     List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/* 434:545 */     BigDecimal valorAcumulado = BigDecimal.ZERO;
/* 435:546 */     Asiento asiento = costosDeFabricacion.getAsiento();
/* 436:547 */     String concepto = "CIERRE COSTOS " + costosDeFabricacion.getAnio() + "-" + FuncionesUtiles.nombreMes(costosDeFabricacion.getMes().intValue());
/* 437:    */     Sucursal sucursal;
/* 438:549 */     if (asiento == null)
/* 439:    */     {
/* 440:550 */       asiento = new Asiento();
/* 441:551 */       asiento.setIdOrganizacion(costosDeFabricacion.getIdOrganizacion());
/* 442:552 */       sucursal = new Sucursal();
/* 443:553 */       sucursal.setIdSucursal(costosDeFabricacion.getIdSucursal().intValue());
/* 444:554 */       asiento.setSucursal(sucursal);
/* 445:555 */       asiento.setIndicadorAutomatico(true);
/* 446:556 */       asiento.setFecha(FuncionesUtiles.getFechaFinMes(costosDeFabricacion.getAnio().intValue(), costosDeFabricacion.getMes().intValue()));
/* 447:557 */       asiento.setEstado(Estado.ELABORADO);
/* 448:558 */       asiento.setConcepto(concepto);
/* 449:559 */       asiento.setListaDetalleAsiento(new ArrayList());
/* 450:560 */       TipoAsiento tipoAsiento = null;
/* 451:    */       try
/* 452:    */       {
/* 453:563 */         tipoAsiento = this.servicioTipoAsiento.buscarPorId(ParametrosSistema.getTipoAsientoInterfazVentas(costosDeFabricacion.getIdOrganizacion()));
/* 454:    */       }
/* 455:    */       catch (Exception e)
/* 456:    */       {
/* 457:565 */         throw new ExcepcionAS2("msg_info_configuracion", Parametro.TIPO_ASIENTO_INTERFAZ_VENTAS.getNombre());
/* 458:    */       }
/* 459:567 */       asiento.setTipoAsiento(tipoAsiento);
/* 460:    */     }
/* 461:    */     else
/* 462:    */     {
/* 463:569 */       asiento = this.servicioAsiento.cargarDetalle(asiento.getIdAsiento());
/* 464:570 */       for (DetalleAsiento detalleAsiento : listaDetalleAsiento) {
/* 465:571 */         detalleAsiento.setEliminado(true);
/* 466:    */       }
/* 467:    */     }
/* 468:575 */     for (Object[] objects : detalles)
/* 469:    */     {
/* 470:577 */       CuentaContable cuentaContable = new CuentaContable(Integer.parseInt(objects[0].toString()), objects[1].toString(), objects[2].toString(), Boolean.parseBoolean(objects[3].toString()));
/* 471:    */       
/* 472:579 */       BigDecimal valor = new BigDecimal(objects[19].toString());
/* 473:580 */       valorAcumulado = valorAcumulado.add(valor);
/* 474:581 */       DetalleAsiento detalleAsiento = new DetalleAsiento(cuentaContable, valor);
/* 475:582 */       if (objects[4] != null)
/* 476:    */       {
/* 477:584 */         DimensionContable d1 = new DimensionContable(Integer.parseInt(objects[4].toString()), "1", objects[5].toString(), objects[6].toString());
/* 478:585 */         detalleAsiento.setDimensionContable1(d1);
/* 479:    */       }
/* 480:587 */       if (objects[7] != null)
/* 481:    */       {
/* 482:589 */         DimensionContable d2 = new DimensionContable(Integer.parseInt(objects[7].toString()), "2", objects[8].toString(), objects[9].toString());
/* 483:590 */         detalleAsiento.setDimensionContable2(d2);
/* 484:    */       }
/* 485:592 */       if (objects[10] != null)
/* 486:    */       {
/* 487:594 */         DimensionContable d3 = new DimensionContable(Integer.parseInt(objects[10].toString()), "3", objects[11].toString(), objects[12].toString());
/* 488:595 */         detalleAsiento.setDimensionContable3(d3);
/* 489:    */       }
/* 490:597 */       if (objects[13] != null)
/* 491:    */       {
/* 492:599 */         DimensionContable d4 = new DimensionContable(Integer.parseInt(objects[13].toString()), "4", objects[14].toString(), objects[15].toString());
/* 493:600 */         detalleAsiento.setDimensionContable4(d4);
/* 494:    */       }
/* 495:602 */       if (objects[16] != null)
/* 496:    */       {
/* 497:604 */         DimensionContable d5 = new DimensionContable(Integer.parseInt(objects[16].toString()), "5", objects[17].toString(), objects[18].toString());
/* 498:605 */         detalleAsiento.setDimensionContable5(d5);
/* 499:    */       }
/* 500:607 */       detalleAsiento.setDescripcion(concepto);
/* 501:608 */       detalleAsiento.setAsiento(asiento);
/* 502:609 */       listaDetalleAsiento.add(detalleAsiento);
/* 503:    */     }
/* 504:615 */     DocumentoContabilizacion documentoContabilizacion = (DocumentoContabilizacion)this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(costosDeFabricacion.getIdOrganizacion(), DocumentoBase.INGRESO_PRODUCCION, ProcesoContabilizacionEnum.PRODUCCION_EN_PROCESO).get(0);
/* 505:    */     
/* 506:    */ 
/* 507:    */ 
/* 508:619 */     DetalleAsiento detalleAsiento = new DetalleAsiento(((CriterioContabilizacion)documentoContabilizacion.getListaCriterioContabilizacion().get(0)).getCuentaContable(), valorAcumulado.compareTo(BigDecimal.ZERO) < 0 ? valorAcumulado.negate() : BigDecimal.ZERO, valorAcumulado.compareTo(BigDecimal.ZERO) > 0 ? valorAcumulado : BigDecimal.ZERO);
/* 509:620 */     detalleAsiento.setAsiento(asiento);
/* 510:621 */     listaDetalleAsiento.add(detalleAsiento);
/* 511:622 */     asiento.setListaDetalleAsiento(listaDetalleAsiento);
/* 512:623 */     this.servicioAsiento.guardar(asiento);
/* 513:624 */     costosDeFabricacion.setAsiento(asiento);
/* 514:625 */     costosDeFabricacion.setEstado(Estado.CONTABILIZADO);
/* 515:626 */     this.costosDeFabricacionDao.guardar(costosDeFabricacion);
/* 516:    */   }
/* 517:    */   
/* 518:    */   public CostosDeFabricacion generarCostosDeFabricacion(CostosDeFabricacion costosDeFabricacion, boolean costeoSubOrdenes)
/* 519:    */     throws AS2Exception, ExcepcionAS2Financiero
/* 520:    */   {
/* 521:635 */     this.servicioPeriodo.buscarPorFecha(costosDeFabricacion.getFechaDesde(), costosDeFabricacion.getIdOrganizacion(), DocumentoBase.ORDEN_FABRICACION);
/* 522:    */     
/* 523:    */ 
/* 524:638 */     int mes = FuncionesUtiles.getMes(costosDeFabricacion.getFechaDesde());
/* 525:639 */     int anio = FuncionesUtiles.getAnio(costosDeFabricacion.getFechaDesde());
/* 526:640 */     List<OperacionOrdenFabricacion> listaOperacionOrdenFabricacionMensual = this.servicioOrdenFabricacion.getOperacionOrdenFabricacionPorAnioMes(AppUtil.getOrganizacion().getId(), null, anio, mes, costeoSubOrdenes);
/* 527:641 */     Map<Integer, List<OperacionOrdenFabricacion>> mapaOperacionOrdenFabricacionMensual = new HashMap();
/* 528:642 */     for (OperacionOrdenFabricacion operacionOrdenFabricacion : listaOperacionOrdenFabricacionMensual)
/* 529:    */     {
/* 530:643 */       List<OperacionOrdenFabricacion> lista = (List)mapaOperacionOrdenFabricacionMensual.get(Integer.valueOf(operacionOrdenFabricacion.getOrdenFabricacion().getId()));
/* 531:644 */       if (lista == null) {
/* 532:645 */         lista = new ArrayList();
/* 533:    */       }
/* 534:647 */       lista.add(operacionOrdenFabricacion);
/* 535:648 */       mapaOperacionOrdenFabricacionMensual.put(Integer.valueOf(operacionOrdenFabricacion.getOrdenFabricacion().getId()), lista);
/* 536:    */     }
/* 537:652 */     Object listaOrdenFabricacion = this.servicioOrdenFabricacion.buscarOrdenesPorRangoFechaCosto(AppUtil.getOrganizacion().getId(), costosDeFabricacion.getFechaDesde(), costosDeFabricacion.getFechaHasta(), costeoSubOrdenes);
/* 538:    */     
/* 539:654 */     StringBuilder ordenesFabricacion = new StringBuilder("");
/* 540:655 */     Map<Integer, List<OrdenFabricacion>> mapaOrdenesFabricacionPorRuta = new HashMap();
/* 541:656 */     for (OrdenFabricacion ordenFabricacion : (List)listaOrdenFabricacion)
/* 542:    */     {
/* 543:657 */       List<OperacionOrdenFabricacion> listaOperacionOrdenFabricacion = (List)mapaOperacionOrdenFabricacionMensual.get(Integer.valueOf(ordenFabricacion.getId()));
/* 544:658 */       if ((listaOperacionOrdenFabricacion == null) || (listaOperacionOrdenFabricacion.size() == 0)) {
/* 545:659 */         ordenesFabricacion.append(ordenFabricacion.getNumero() + ",");
/* 546:    */       }
/* 547:661 */       ordenFabricacion.setListaOperacionProduccion(listaOperacionOrdenFabricacion);
/* 548:662 */       List<OrdenFabricacion> lista = (List)mapaOrdenesFabricacionPorRuta.get(Integer.valueOf(ordenFabricacion.getRutaFabricacion().getId()));
/* 549:663 */       if (lista == null) {
/* 550:664 */         lista = new ArrayList();
/* 551:    */       }
/* 552:666 */       lista.add(ordenFabricacion);
/* 553:667 */       mapaOrdenesFabricacionPorRuta.put(Integer.valueOf(ordenFabricacion.getRutaFabricacion().getId()), lista);
/* 554:    */     }
/* 555:671 */     if (!ordenesFabricacion.toString().equals("")) {
/* 556:672 */       throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioCostosDeFabricacionImpl.ERROR_NO_EXISTE_OPERACIONES_ORDEN_FABRICACION", new String[] { ordenesFabricacion.toString() });
/* 557:    */     }
/* 558:676 */     BigDecimal costoMateriales = BigDecimal.ZERO;
/* 559:677 */     BigDecimal costoManoObra = BigDecimal.ZERO;
/* 560:678 */     BigDecimal costoDepreciaciones = BigDecimal.ZERO;
/* 561:679 */     BigDecimal costoIndirectos = BigDecimal.ZERO;
/* 562:682 */     for (OrdenFabricacion orden : (List)listaOrdenFabricacion)
/* 563:    */     {
/* 564:683 */       BigDecimal costoMaterialesOrden = BigDecimal.ZERO;
/* 565:684 */       if (orden.getTipoCicloProduccionEnum().equals(TipoCicloProduccionEnum.CICLO_CORTO)) {
/* 566:685 */         costoMaterialesOrden = this.servicioOrdenFabricacion.getCostosMateriales(orden);
/* 567:    */       } else {
/* 568:687 */         costoMaterialesOrden = this.servicioCosteo.obtenerCostoOrdenFabricacion(true, orden, null, costosDeFabricacion.getFechaDesde(), costosDeFabricacion.getFechaHasta(), null, null);
/* 569:    */       }
/* 570:689 */       orden.setCostoMateriales(costoMaterialesOrden);
/* 571:690 */       costoMateriales = costoMateriales.add(orden.getCostoMateriales());
/* 572:    */     }
/* 573:696 */     Object listaComponenteManoObra = this.servicioComponenteCosto.buscarComponentePorTipo(AppUtil.getOrganizacion().getId(), TipoComponenteCostoEnum.MANO_OBRA);
/* 574:697 */     List<ComponenteCosto> listaComponenteDepreciaciones = this.servicioComponenteCosto.buscarComponentePorTipo(AppUtil.getOrganizacion().getId(), TipoComponenteCostoEnum.DEPRECIACION);
/* 575:698 */     List<ComponenteCosto> listaComponenteIndirectos = this.servicioComponenteCosto.buscarComponentePorTipo(AppUtil.getOrganizacion().getId(), TipoComponenteCostoEnum.INDIRECTO);
/* 576:701 */     for (ComponenteCosto componenteCosto : (List)listaComponenteManoObra)
/* 577:    */     {
/* 578:702 */       Map<Integer, BigDecimal> mapaValorPorRutaFabricacion = this.servicioComponenteCosto.obtenerSaldoCuentaDistribuidoPorRuta(componenteCosto, costosDeFabricacion.getFechaDesde(), costosDeFabricacion.getFechaHasta());
/* 579:703 */       Iterator<Integer> it = mapaValorPorRutaFabricacion.keySet().iterator();
/* 580:704 */       while (it.hasNext())
/* 581:    */       {
/* 582:705 */         Integer idRuta = (Integer)it.next();
/* 583:706 */         BigDecimal valorCostoPorRuta = (BigDecimal)mapaValorPorRutaFabricacion.get(idRuta);
/* 584:707 */         List<OrdenFabricacion> listaOrdenFabricacionPorRuta = (List)mapaOrdenesFabricacionPorRuta.get(idRuta);
/* 585:709 */         if (listaOrdenFabricacionPorRuta != null)
/* 586:    */         {
/* 587:710 */           costoManoObra = costoManoObra.add(valorCostoPorRuta);
/* 588:712 */           if (valorCostoPorRuta.compareTo(BigDecimal.ZERO) != 0) {
/* 589:713 */             prorratearComponentesCosto(listaOrdenFabricacionPorRuta, componenteCosto, valorCostoPorRuta);
/* 590:    */           }
/* 591:    */         }
/* 592:    */       }
/* 593:    */     }
/* 594:722 */     for (ComponenteCosto componenteCosto : listaComponenteDepreciaciones)
/* 595:    */     {
/* 596:723 */       Map<Integer, BigDecimal> mapaValorPorRutaFabricacion = this.servicioComponenteCosto.obtenerSaldoCuentaDistribuidoPorRuta(componenteCosto, costosDeFabricacion.getFechaDesde(), costosDeFabricacion.getFechaHasta());
/* 597:    */       
/* 598:725 */       Iterator<Integer> it = mapaValorPorRutaFabricacion.keySet().iterator();
/* 599:726 */       while (it.hasNext())
/* 600:    */       {
/* 601:727 */         Integer idRuta = (Integer)it.next();
/* 602:728 */         BigDecimal valorCostoPorRuta = (BigDecimal)mapaValorPorRutaFabricacion.get(idRuta);
/* 603:729 */         List<OrdenFabricacion> listaOrdenFabricacionPorRuta = (List)mapaOrdenesFabricacionPorRuta.get(idRuta);
/* 604:730 */         costoDepreciaciones = costoDepreciaciones.add(valorCostoPorRuta);
/* 605:731 */         if (listaOrdenFabricacionPorRuta != null) {
/* 606:733 */           if (valorCostoPorRuta.compareTo(BigDecimal.ZERO) != 0) {
/* 607:734 */             prorratearComponentesCosto(listaOrdenFabricacionPorRuta, componenteCosto, valorCostoPorRuta);
/* 608:    */           }
/* 609:    */         }
/* 610:    */       }
/* 611:    */     }
/* 612:743 */     for (??? = listaComponenteIndirectos.iterator(); ???.hasNext();)
/* 613:    */     {
/* 614:743 */       componenteCosto = (ComponenteCosto)???.next();
/* 615:744 */       Map<Integer, BigDecimal> mapaValorPorRutaFabricacion = this.servicioComponenteCosto.obtenerSaldoCuentaDistribuidoPorRuta(componenteCosto, costosDeFabricacion.getFechaDesde(), costosDeFabricacion.getFechaHasta());
/* 616:    */       
/* 617:746 */       Iterator<Integer> it = mapaValorPorRutaFabricacion.keySet().iterator();
/* 618:747 */       while (it.hasNext())
/* 619:    */       {
/* 620:748 */         Integer idRuta = (Integer)it.next();
/* 621:749 */         BigDecimal valorCostoPorRuta = (BigDecimal)mapaValorPorRutaFabricacion.get(idRuta);
/* 622:750 */         List<OrdenFabricacion> listaOrdenFabricacionPorRuta = (List)mapaOrdenesFabricacionPorRuta.get(idRuta);
/* 623:751 */         costoIndirectos = costoIndirectos.add(valorCostoPorRuta);
/* 624:752 */         if (listaOrdenFabricacionPorRuta != null) {
/* 625:754 */           if (valorCostoPorRuta.compareTo(BigDecimal.ZERO) != 0) {
/* 626:755 */             prorratearComponentesCosto(listaOrdenFabricacionPorRuta, componenteCosto, valorCostoPorRuta);
/* 627:    */           }
/* 628:    */         }
/* 629:    */       }
/* 630:    */     }
/* 631:763 */     Object listaDetalleCostoFabricacion = new ArrayList();
/* 632:764 */     for (ComponenteCosto componenteCosto = ((List)listaOrdenFabricacion).iterator(); componenteCosto.hasNext();)
/* 633:    */     {
/* 634:764 */       ordenFabricacion = (OrdenFabricacion)componenteCosto.next();
/* 635:765 */       DetalleCostoFabricacion detalleCosto = actualizarCostoMensualOrdenFabricacion(costosDeFabricacion, ordenFabricacion, ordenFabricacion.getCostoMateriales(), ordenFabricacion.getCostoManoObra(), ordenFabricacion.getCostoDepreciacion(), ordenFabricacion.getCostoIndirecto());
/* 636:766 */       ((List)listaDetalleCostoFabricacion).add(detalleCosto);
/* 637:    */     }
/* 638:769 */     costosDeFabricacion.setCostoMateriales(costoMateriales);
/* 639:770 */     costosDeFabricacion.setCostoManoDeObra(costoManoObra);
/* 640:771 */     costosDeFabricacion.setCostoDepreciaciones(costoDepreciaciones);
/* 641:772 */     costosDeFabricacion.setCostoIndirectos(costoIndirectos);
/* 642:    */     
/* 643:774 */     Map<Integer, DetalleCostoFabricacion> mapaDetallesViejos = new HashMap();
/* 644:775 */     for (DetalleCostoFabricacion detalleCostoFabricacion : costosDeFabricacion.getListaDetalleCostoFabricacion())
/* 645:    */     {
/* 646:776 */       mapaDetallesViejos.put(Integer.valueOf(detalleCostoFabricacion.getOrdenFabricacion().getId()), detalleCostoFabricacion);
/* 647:777 */       if (!detalleCostoFabricacion.getOrdenFabricacion().getEstado().equals(EstadoProduccionEnum.FINALIZADA)) {
/* 648:778 */         detalleCostoFabricacion.setEliminado(true);
/* 649:    */       }
/* 650:    */     }
/* 651:782 */     for (OrdenFabricacion ordenFabricacion = ((List)listaDetalleCostoFabricacion).iterator(); ordenFabricacion.hasNext();)
/* 652:    */     {
/* 653:782 */       detalleCostoFabricacion = (DetalleCostoFabricacion)ordenFabricacion.next();
/* 654:783 */       detalleCostoFabricacion.setEliminado(false);
/* 655:784 */       mapaDetallesViejos.put(Integer.valueOf(detalleCostoFabricacion.getOrdenFabricacion().getId()), detalleCostoFabricacion);
/* 656:    */     }
/* 657:    */     DetalleCostoFabricacion detalleCostoFabricacion;
/* 658:787 */     List<DetalleCostoFabricacion> listaActualizada = new ArrayList();
/* 659:788 */     for (DetalleCostoFabricacion detalleCostoFabricacion : mapaDetallesViejos.values()) {
/* 660:789 */       listaActualizada.add(detalleCostoFabricacion);
/* 661:    */     }
/* 662:792 */     costosDeFabricacion.setListaDetalleCostoFabricacion(listaActualizada);
/* 663:    */     
/* 664:794 */     return costosDeFabricacion;
/* 665:    */   }
/* 666:    */   
/* 667:    */   public DetalleCostoFabricacion actualizarCostoMensualOrdenFabricacion(CostosDeFabricacion costosDeFabricacion, OrdenFabricacion ordenFabricacion, BigDecimal costoMateriales, BigDecimal costoManoObra, BigDecimal costoDepreciacion, BigDecimal costoIndirecto)
/* 668:    */     throws AS2Exception
/* 669:    */   {
/* 670:799 */     boolean indicadorCicloLargo = ordenFabricacion.getTipoCicloProduccionEnum().equals(TipoCicloProduccionEnum.CICLO_LARGO);
/* 671:    */     
/* 672:    */ 
/* 673:802 */     boolean indicadorCierraOFEsteMes = (ordenFabricacion.getFechaCierre() != null) && (ordenFabricacion.getFechaCierre().compareTo(costosDeFabricacion.getFechaDesde()) >= 0) && (ordenFabricacion.getFechaCierre().compareTo(costosDeFabricacion.getFechaHasta()) <= 0);
/* 674:    */     
/* 675:    */ 
/* 676:805 */     Date fechaLanzamiento = ordenFabricacion.getFechaLanzamiento() == null ? null : FuncionesUtiles.setAtributoFecha(ordenFabricacion.getFechaLanzamiento());
/* 677:806 */     Date fechaCierre = ordenFabricacion.getFechaCierre() == null ? null : FuncionesUtiles.setAtributoFecha(ordenFabricacion.getFechaCierre());
/* 678:808 */     if ((fechaLanzamiento.after(costosDeFabricacion.getFechaHasta())) || ((fechaCierre != null) && 
/* 679:809 */       (fechaCierre.before(costosDeFabricacion.getFechaDesde())))) {
/* 680:810 */       throw new AS2Exception("msg_error_fechas_fuera_rango_costos_orden_fabricacion", new String[] { ordenFabricacion.getNumero(), FuncionesUtiles.nombreMes(costosDeFabricacion.getMes().intValue()) + " " + costosDeFabricacion.getAnio() });
/* 681:    */     }
/* 682:814 */     DetalleCostoFabricacion detalleCosto = null;
/* 683:815 */     Map<String, String> filtros = new HashMap();
/* 684:816 */     filtros.put("ordenFabricacion.idOrdenFabricacion", ordenFabricacion.getId() + "");
/* 685:817 */     filtros.put("costosDeFabricacion.idCostosDeFabricacion", costosDeFabricacion.getId() + "");
/* 686:818 */     List<String> listaCampos = new ArrayList();
/* 687:819 */     listaCampos.add("ordenFabricacion");
/* 688:820 */     listaCampos.add("costosDeFabricacion");
/* 689:821 */     List<DetalleCostoFabricacion> listaDetalle = this.detalleCostoFabricacionDao.obtenerListaPorPagina(DetalleCostoFabricacion.class, 0, 1, null, true, filtros, listaCampos);
/* 690:822 */     if (listaDetalle.size() > 0) {
/* 691:823 */       detalleCosto = (DetalleCostoFabricacion)listaDetalle.get(0);
/* 692:    */     } else {
/* 693:825 */       detalleCosto = crearDetalleCostoFabricacion(costosDeFabricacion, ordenFabricacion);
/* 694:    */     }
/* 695:827 */     detalleCosto.setIndicadorCierraOFEsteMes(indicadorCierraOFEsteMes);
/* 696:828 */     detalleCosto.setIndicadorCicloLargo(indicadorCicloLargo);
/* 697:    */     
/* 698:    */ 
/* 699:831 */     Date fechaDesde = indicadorCicloLargo ? costosDeFabricacion.getFechaDesde() : null;
/* 700:832 */     Date fechaHasta = indicadorCicloLargo ? costosDeFabricacion.getFechaHasta() : null;
/* 701:833 */     BigDecimal cantidadFabricada = this.ordenFabricacionDao.obtenerCantidadFabricada(ordenFabricacion, fechaDesde, fechaHasta);
/* 702:834 */     detalleCosto.setCantidadFabricadaMes(cantidadFabricada);
/* 703:835 */     BigDecimal cantidadFabricadaAntes = null;
/* 704:836 */     if (indicadorCicloLargo)
/* 705:    */     {
/* 706:837 */       cantidadFabricadaAntes = this.ordenFabricacionDao.obtenerCantidadFabricada(ordenFabricacion, null, FuncionesUtiles.sumarFechaDiasMeses(fechaDesde, -1));
/* 707:838 */       detalleCosto.setCantidadFabricadaAntes(cantidadFabricadaAntes);
/* 708:    */     }
/* 709:842 */     DetalleCostoFabricacion detalleAnterior = null;
/* 710:843 */     if ((fechaLanzamiento.before(costosDeFabricacion.getFechaDesde())) && (indicadorCicloLargo))
/* 711:    */     {
/* 712:844 */       detalleAnterior = this.costosDeFabricacionDao.buscarDetalleCostoFabricacionMesAnterior(costosDeFabricacion, ordenFabricacion);
/* 713:    */       
/* 714:    */ 
/* 715:847 */       detalleCosto.setCostoMaterialesInicial(detalleAnterior.getCostoMaterialesPendiente());
/* 716:848 */       detalleCosto.setCostoManoObraInicial(detalleAnterior.getCostoManoObraPendiente());
/* 717:849 */       detalleCosto.setCostoDepreciacionInicial(detalleAnterior.getCostoDepreciacionPendiente());
/* 718:850 */       detalleCosto.setCostoIndirectosInicial(detalleAnterior.getCostoIndirectosPendiente());
/* 719:    */     }
/* 720:    */     else
/* 721:    */     {
/* 722:853 */       detalleCosto.setCostoMaterialesInicial(BigDecimal.ZERO);
/* 723:854 */       detalleCosto.setCostoManoObraInicial(BigDecimal.ZERO);
/* 724:855 */       detalleCosto.setCostoDepreciacionInicial(BigDecimal.ZERO);
/* 725:856 */       detalleCosto.setCostoIndirectosInicial(BigDecimal.ZERO);
/* 726:    */     }
/* 727:860 */     detalleCosto.setCostoMaterialesMes(costoMateriales.setScale(4, RoundingMode.HALF_UP));
/* 728:861 */     detalleCosto.setCostoManoObraMes(costoManoObra.setScale(4, RoundingMode.HALF_UP));
/* 729:862 */     detalleCosto.setCostoDepreciacionMes(costoDepreciacion.setScale(4, RoundingMode.HALF_UP));
/* 730:863 */     detalleCosto.setCostoIndirectosMes(costoIndirecto.setScale(4, RoundingMode.HALF_UP));
/* 731:    */     
/* 732:    */ 
/* 733:866 */     actualizarCantidadAsignadaMesCostofabricacion(detalleCosto);
/* 734:869 */     if ((indicadorCierraOFEsteMes) && 
/* 735:870 */       (detalleCosto.getCostoMaterialesPendiente().add(detalleCosto.getCostoManoObraPendiente()).add(detalleCosto.getCostoDepreciacionPendiente()).add(detalleCosto.getCostoIndirectosPendiente()).compareTo(BigDecimal.ZERO) != 0)) {
/* 736:871 */       throw new AS2Exception("msg_error_orden_fabricacion_finalizada_con_costos_pendientes", new String[] { ordenFabricacion.getNumero() });
/* 737:    */     }
/* 738:874 */     return detalleCosto;
/* 739:    */   }
/* 740:    */   
/* 741:    */   private DetalleCostoFabricacion crearDetalleCostoFabricacion(CostosDeFabricacion costosDeFabricacion, OrdenFabricacion ordenFabricacion)
/* 742:    */   {
/* 743:878 */     DetalleCostoFabricacion detalle = new DetalleCostoFabricacion();
/* 744:879 */     detalle.setIdOrganizacion(ordenFabricacion.getIdOrganizacion());
/* 745:880 */     detalle.setIdSucursal(ordenFabricacion.getSucursal().getId());
/* 746:881 */     detalle.setOrdenFabricacion(ordenFabricacion);
/* 747:882 */     detalle.setCostosDeFabricacion(costosDeFabricacion);
/* 748:    */     
/* 749:884 */     return detalle;
/* 750:    */   }
/* 751:    */   
/* 752:    */   public void actualizarCantidadAsignadaMesCostofabricacion(DetalleCostoFabricacion detalleCosto)
/* 753:    */   {
/* 754:889 */     BigDecimal cantidadFabricada = detalleCosto.getCantidadFabricadaMes();
/* 755:890 */     BigDecimal cantidadFabricadaAntes = detalleCosto.getCantidadFabricadaAntes();
/* 756:892 */     if (cantidadFabricada.compareTo(BigDecimal.ZERO) != 0)
/* 757:    */     {
/* 758:893 */       BigDecimal costoMaterialesAsignarMes = BigDecimal.ZERO;
/* 759:894 */       BigDecimal costoManoObraAsignarMes = BigDecimal.ZERO;
/* 760:895 */       BigDecimal costoDepreciacionesAsignarMes = BigDecimal.ZERO;
/* 761:896 */       BigDecimal costoIndirectoAsignarMes = BigDecimal.ZERO;
/* 762:    */       
/* 763:898 */       BigDecimal proporcionProduccionMes = BigDecimal.ONE;
/* 764:899 */       if (cantidadFabricada.add(cantidadFabricadaAntes).compareTo(detalleCosto.getOrdenFabricacion().getCantidad()) < 0) {
/* 765:900 */         proporcionProduccionMes = cantidadFabricada.divide(detalleCosto.getOrdenFabricacion().getCantidad().subtract(cantidadFabricadaAntes), 10, RoundingMode.HALF_UP);
/* 766:    */       }
/* 767:904 */       if ((detalleCosto.isIndicadorCierraOFEsteMes()) || (!detalleCosto.isIndicadorCicloLargo()))
/* 768:    */       {
/* 769:905 */         costoMaterialesAsignarMes = detalleCosto.getCostoMaterialesInicial().add(detalleCosto.getCostoMaterialesMes());
/* 770:906 */         costoManoObraAsignarMes = detalleCosto.getCostoManoObraInicial().add(detalleCosto.getCostoManoObraMes());
/* 771:907 */         costoDepreciacionesAsignarMes = detalleCosto.getCostoDepreciacionInicial().add(detalleCosto.getCostoDepreciacionMes());
/* 772:908 */         costoIndirectoAsignarMes = detalleCosto.getCostoIndirectosInicial().add(detalleCosto.getCostoIndirectosMes());
/* 773:    */       }
/* 774:    */       else
/* 775:    */       {
/* 776:911 */         costoMaterialesAsignarMes = detalleCosto.getCostoMaterialesInicial().add(detalleCosto.getCostoMaterialesMes()).multiply(proporcionProduccionMes).setScale(4, RoundingMode.HALF_UP);
/* 777:912 */         costoManoObraAsignarMes = detalleCosto.getCostoManoObraInicial().add(detalleCosto.getCostoManoObraMes()).multiply(proporcionProduccionMes).setScale(4, RoundingMode.HALF_UP);
/* 778:913 */         costoDepreciacionesAsignarMes = detalleCosto.getCostoDepreciacionInicial().add(detalleCosto.getCostoDepreciacionMes()).multiply(proporcionProduccionMes).setScale(4, RoundingMode.HALF_UP);
/* 779:914 */         costoIndirectoAsignarMes = detalleCosto.getCostoIndirectosInicial().add(detalleCosto.getCostoIndirectosMes()).multiply(proporcionProduccionMes).setScale(4, RoundingMode.HALF_UP);
/* 780:    */       }
/* 781:939 */       detalleCosto.setCostoMaterialesAsignadoMes(costoMaterialesAsignarMes);
/* 782:940 */       detalleCosto.setCostoManoObraAsignadoMes(costoManoObraAsignarMes);
/* 783:941 */       detalleCosto.setCostoDepreciacionAsignadoMes(costoDepreciacionesAsignarMes);
/* 784:942 */       detalleCosto.setCostoIndirectosAsignadoMes(costoIndirectoAsignarMes);
/* 785:    */     }
/* 786:946 */     detalleCosto.setCostoMaterialesPendiente(detalleCosto.getCostoMaterialesInicial().add(detalleCosto.getCostoMaterialesMes()).subtract(detalleCosto.getCostoMaterialesAsignadoMes()).setScale(4, RoundingMode.HALF_UP));
/* 787:947 */     detalleCosto.setCostoManoObraPendiente(detalleCosto.getCostoManoObraInicial().add(detalleCosto.getCostoManoObraMes()).subtract(detalleCosto.getCostoManoObraAsignadoMes()).setScale(4, RoundingMode.HALF_UP));
/* 788:948 */     detalleCosto.setCostoDepreciacionPendiente(detalleCosto.getCostoDepreciacionInicial().add(detalleCosto.getCostoDepreciacionMes()).subtract(detalleCosto.getCostoDepreciacionAsignadoMes()).setScale(4, RoundingMode.HALF_UP));
/* 789:949 */     detalleCosto.setCostoIndirectosPendiente(detalleCosto.getCostoIndirectosInicial().add(detalleCosto.getCostoIndirectosMes()).subtract(detalleCosto.getCostoIndirectosAsignadoMes()).setScale(4, RoundingMode.HALF_UP));
/* 790:    */   }
/* 791:    */   
/* 792:    */   public List<Object[]> getReporteCostosFabricacion(int idOrganizacion, Producto producto, int anioDesde, int mesDesde, int anioHasta, int mesHasta)
/* 793:    */   {
/* 794:955 */     return this.costosDeFabricacionDao.getReporteCostosFabricacion(idOrganizacion, producto, anioDesde, mesDesde, anioHasta, mesHasta);
/* 795:    */   }
/* 796:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.impl.ServicioCostosDeFabricacionImpl
 * JD-Core Version:    0.7.0.1
 */