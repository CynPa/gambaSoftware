/*   1:    */ package com.asinfo.as2.calidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.calidad.procesos.servicio.ServicioControlCalidad;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   7:    */ import com.asinfo.as2.dao.GenericoDao;
/*   8:    */ import com.asinfo.as2.dao.RegistroPesoDao;
/*   9:    */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.SecuenciaAS2Service;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  12:    */ import com.asinfo.as2.entities.Bodega;
/*  13:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  14:    */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*  15:    */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*  16:    */ import com.asinfo.as2.entities.Documento;
/*  17:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  18:    */ import com.asinfo.as2.entities.Lote;
/*  19:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  20:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*  21:    */ import com.asinfo.as2.entities.Producto;
/*  22:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  23:    */ import com.asinfo.as2.entities.RegistroPeso;
/*  24:    */ import com.asinfo.as2.entities.Sucursal;
/*  25:    */ import com.asinfo.as2.entities.Unidad;
/*  26:    */ import com.asinfo.as2.entities.calidad.VariableCalidad;
/*  27:    */ import com.asinfo.as2.entities.calidad.VariableCalidadOrdenFabricacion;
/*  28:    */ import com.asinfo.as2.entities.calidad.VariableCalidadProducto;
/*  29:    */ import com.asinfo.as2.entities.calidad.VariableCalidadRegistroPeso;
/*  30:    */ import com.asinfo.as2.entities.produccion.HistoricoCalidadOrdenFabricacion;
/*  31:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  32:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  33:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  34:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*  35:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  36:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  37:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  38:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  39:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  40:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  41:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*  42:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  43:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  44:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*  45:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*  46:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioHistoricoCalidadOrdenFabricacion;
/*  47:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  48:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  49:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  50:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  51:    */ import java.io.IOException;
/*  52:    */ import java.io.InputStream;
/*  53:    */ import java.math.BigDecimal;
/*  54:    */ import java.math.RoundingMode;
/*  55:    */ import java.util.Date;
/*  56:    */ import java.util.HashMap;
/*  57:    */ import java.util.Iterator;
/*  58:    */ import java.util.List;
/*  59:    */ import java.util.Map;
/*  60:    */ import javax.ejb.EJB;
/*  61:    */ import javax.ejb.Lock;
/*  62:    */ import javax.ejb.LockType;
/*  63:    */ import javax.ejb.SessionContext;
/*  64:    */ import javax.ejb.Stateless;
/*  65:    */ import javax.ejb.TransactionAttribute;
/*  66:    */ import javax.ejb.TransactionAttributeType;
/*  67:    */ import javax.ejb.TransactionManagement;
/*  68:    */ import javax.ejb.TransactionManagementType;
/*  69:    */ import org.apache.log4j.Logger;
/*  70:    */ import org.apache.poi.xssf.usermodel.XSSFCell;
/*  71:    */ import org.apache.poi.xssf.usermodel.XSSFRow;
/*  72:    */ 
/*  73:    */ @Stateless
/*  74:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  75:    */ public class ServicioControlCalidadImpl
/*  76:    */   extends AbstractServicioAS2
/*  77:    */   implements ServicioControlCalidad
/*  78:    */ {
/*  79:    */   private static final long serialVersionUID = 1L;
/*  80:    */   @EJB
/*  81:    */   private RegistroPesoDao registroPesoDao;
/*  82:    */   @EJB
/*  83:    */   private ServicioInventarioProducto servicioInventarioProducto;
/*  84:    */   @EJB
/*  85:    */   private ServicioPedidoProveedor servicioPedidoProveedor;
/*  86:    */   @EJB
/*  87:    */   private transient ServicioGenerico<VariableCalidadRegistroPeso> servicioVariableCalidadRegistroPeso;
/*  88:    */   @EJB
/*  89:    */   private transient ServicioSucursal servicioSucursal;
/*  90:    */   @EJB
/*  91:    */   private transient ServicioDocumento servicioDocumento;
/*  92:    */   @EJB
/*  93:    */   private transient ServicioRecepcionProveedor servicioRecepcionProveedor;
/*  94:    */   @EJB
/*  95:    */   private transient SecuenciaAS2Service secuenciaAS2Service;
/*  96:    */   @EJB
/*  97:    */   private transient ServicioLote servicioLote;
/*  98:    */   @EJB
/*  99:    */   private transient OrdenFabricacionDao ordenFabricacionDao;
/* 100:    */   @EJB
/* 101:    */   private transient ServicioHistoricoCalidadOrdenFabricacion servicioHistoricoCalidadOrdenFabricacion;
/* 102:    */   @EJB
/* 103:    */   private transient GenericoDao<VariableCalidadOrdenFabricacion> variableCalidadOrdenFabricacionDao;
/* 104:    */   @EJB
/* 105:    */   private transient ServicioMovimientoInventario servicioMovimientoInventario;
/* 106:    */   @EJB
/* 107:    */   private transient ServicioCosteo servicioCosteo;
/* 108:    */   @EJB
/* 109:    */   private transient ServicioRegistroPeso servicioRegistroPeso;
/* 110:    */   @EJB
/* 111:    */   protected transient ServicioControlCalidad servicioControlCalidad;
/* 112:    */   @EJB
/* 113:    */   protected transient GenericoDao<DetalleMovimientoInventario> detalleMovimientoInventarioDao;
/* 114:    */   @EJB
/* 115:    */   protected transient GenericoDao<HistoricoCalidadOrdenFabricacion> historicoCalidadOrdenFabricacionDao;
/* 116:    */   @EJB
/* 117:    */   private ServicioProducto servicioProducto;
/* 118:    */   @EJB
/* 119:    */   protected transient ServicioVerificadorInventario servicioVerificadorInventario;
/* 120:    */   @EJB
/* 121:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/* 122:    */   
/* 123:    */   public List<VariableCalidadRegistroPeso> getListaVariableCalidadRegistroPeso(RegistroPeso registroPeso)
/* 124:    */   {
/* 125:134 */     return this.registroPesoDao.getListaVariableCalidadRegistroPeso(registroPeso);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public List<VariableCalidadOrdenFabricacion> getListaVariableCalidadOrdenFabricacion(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion)
/* 129:    */   {
/* 130:140 */     return this.ordenFabricacionDao.getListaVariableCalidadOrdenFabricacion(historicoCalidadOrdenFabricacion);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public List<VariableCalidadProducto> getListaVariableCalidadProducto(Producto producto)
/* 134:    */   {
/* 135:145 */     return this.registroPesoDao.getListaVariableCalidadProducto(producto);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public RegistroPeso cargarExcel(InputStream inputStream, RegistroPeso registroPeso)
/* 139:    */     throws ExcepcionAS2
/* 140:    */   {
/* 141:    */     try
/* 142:    */     {
/* 143:166 */       Iterator filas = FuncionesUtiles.leerExcelXlsx(inputStream, 0);
/* 144:    */       
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:173 */       HashMap<String, Integer> mapCabeceras = new HashMap();
/* 151:174 */       int cont = 0;
/* 152:175 */       while (filas.hasNext())
/* 153:    */       {
/* 154:176 */         XSSFRow fila = (XSSFRow)filas.next();
/* 155:    */         Iterator cells;
/* 156:178 */         if (cont == 0)
/* 157:    */         {
/* 158:179 */           cells = fila.cellIterator();
/* 159:180 */           while (cells.hasNext())
/* 160:    */           {
/* 161:181 */             XSSFCell celda = (XSSFCell)cells.next();
/* 162:182 */             if ((celda != null) && (!celda.getStringCellValue().trim().isEmpty())) {
/* 163:183 */               mapCabeceras.put(celda.getStringCellValue().trim().toLowerCase(), Integer.valueOf(celda.getColumnIndex()));
/* 164:    */             }
/* 165:    */           }
/* 166:    */         }
/* 167:189 */         if (cont == 1) {
/* 168:190 */           for (VariableCalidadRegistroPeso vcrp : registroPeso.getListaVariableCalidadRegistroPeso())
/* 169:    */           {
/* 170:191 */             Integer indice = (Integer)mapCabeceras.get(vcrp.getVariableCalidadProducto().getVariableCalidad().getCodigo().trim().toLowerCase());
/* 171:192 */             if (indice != null)
/* 172:    */             {
/* 173:193 */               XSSFCell celda = fila.getCell(indice.intValue());
/* 174:194 */               if (celda != null) {
/* 175:195 */                 if ((celda.getCellType() == 0) && (!vcrp.getVariableCalidadProducto().getVariableCalidad().isIndicadorMedicionUnica()))
/* 176:    */                 {
/* 177:196 */                   vcrp.setValorNir(BigDecimal.valueOf(celda.getNumericCellValue()));
/* 178:    */                 }
/* 179:197 */                 else if (vcrp.getVariableCalidadProducto().getVariableCalidad().isIndicadorMedicionUnica())
/* 180:    */                 {
/* 181:198 */                   String valorUnico = celda.getCellType() == 0 ? String.valueOf(celda.getNumericCellValue()) : celda.getStringCellValue();
/* 182:199 */                   vcrp.setValorUnico(valorUnico);
/* 183:    */                 }
/* 184:    */               }
/* 185:    */             }
/* 186:    */           }
/* 187:    */         }
/* 188:205 */         cont++;
/* 189:    */       }
/* 190:    */     }
/* 191:    */     catch (IOException e)
/* 192:    */     {
/* 193:208 */       throw new ExcepcionAS2(e);
/* 194:    */     }
/* 195:211 */     return registroPeso;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public HistoricoCalidadOrdenFabricacion cargarExcel(InputStream inputStream, HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion)
/* 199:    */     throws ExcepcionAS2
/* 200:    */   {
/* 201:    */     try
/* 202:    */     {
/* 203:221 */       Iterator filas = FuncionesUtiles.leerExcelXlsx(inputStream, 0);
/* 204:    */       
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:228 */       HashMap<String, Integer> mapCabeceras = new HashMap();
/* 211:229 */       int cont = 0;
/* 212:230 */       while (filas.hasNext())
/* 213:    */       {
/* 214:231 */         XSSFRow fila = (XSSFRow)filas.next();
/* 215:    */         Iterator cells;
/* 216:233 */         if (cont == 0)
/* 217:    */         {
/* 218:234 */           cells = fila.cellIterator();
/* 219:235 */           while (cells.hasNext())
/* 220:    */           {
/* 221:236 */             XSSFCell celda = (XSSFCell)cells.next();
/* 222:237 */             if ((celda != null) && (!celda.getStringCellValue().trim().isEmpty())) {
/* 223:238 */               mapCabeceras.put(celda.getStringCellValue().trim().toLowerCase(), Integer.valueOf(celda.getColumnIndex()));
/* 224:    */             }
/* 225:    */           }
/* 226:    */         }
/* 227:244 */         if (cont == 1) {
/* 228:245 */           for (VariableCalidadOrdenFabricacion vcof : historicoCalidadOrdenFabricacion.getListaVariableCalidadOrdenFabricacion())
/* 229:    */           {
/* 230:246 */             Integer indice = (Integer)mapCabeceras.get(vcof.getVariableCalidadProducto().getVariableCalidad().getCodigo().trim().toLowerCase());
/* 231:247 */             if (indice != null)
/* 232:    */             {
/* 233:248 */               XSSFCell celda = fila.getCell(indice.intValue());
/* 234:249 */               if (celda != null) {
/* 235:250 */                 if ((celda.getCellType() == 0) && (!vcof.getVariableCalidadProducto().getVariableCalidad().isIndicadorMedicionUnica()))
/* 236:    */                 {
/* 237:251 */                   vcof.setValorNir(BigDecimal.valueOf(celda.getNumericCellValue()));
/* 238:    */                 }
/* 239:252 */                 else if (vcof.getVariableCalidadProducto().getVariableCalidad().isIndicadorMedicionUnica())
/* 240:    */                 {
/* 241:253 */                   String valorUnico = celda.getCellType() == 0 ? String.valueOf(celda.getNumericCellValue()) : celda.getStringCellValue();
/* 242:254 */                   vcof.setValorUnico(valorUnico);
/* 243:    */                 }
/* 244:    */               }
/* 245:    */             }
/* 246:    */           }
/* 247:    */         }
/* 248:260 */         cont++;
/* 249:    */       }
/* 250:    */     }
/* 251:    */     catch (IOException e)
/* 252:    */     {
/* 253:263 */       throw new ExcepcionAS2(e);
/* 254:    */     }
/* 255:266 */     return historicoCalidadOrdenFabricacion;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void guardarRegistroPeso(RegistroPeso registroPeso, EstadoControlCalidad ecc)
/* 259:    */     throws AS2Exception, ExcepcionAS2
/* 260:    */   {
/* 261:    */     try
/* 262:    */     {
/* 263:272 */       registroPeso.setEstadoControlCalidad(ecc);
/* 264:273 */       this.registroPesoDao.guardar(registroPeso);
/* 265:276 */       if (registroPeso.getListaVariableCalidadRegistroPeso() != null) {
/* 266:277 */         for (VariableCalidadRegistroPeso vcrp : registroPeso.getListaVariableCalidadRegistroPeso()) {
/* 267:278 */           this.servicioVariableCalidadRegistroPeso.guardar(vcrp);
/* 268:    */         }
/* 269:    */       }
/* 270:    */     }
/* 271:    */     catch (AS2Exception e)
/* 272:    */     {
/* 273:282 */       this.context.setRollbackOnly();
/* 274:283 */       e.printStackTrace();
/* 275:284 */       throw e;
/* 276:    */     }
/* 277:    */     catch (Exception e)
/* 278:    */     {
/* 279:286 */       this.context.setRollbackOnly();
/* 280:287 */       LOG.error(e);
/* 281:288 */       e.printStackTrace();
/* 282:289 */       throw new ExcepcionAS2(e);
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void guardarHistoricoCalidadOrdenFabricacion(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion)
/* 287:    */     throws AS2Exception, ExcepcionAS2
/* 288:    */   {
/* 289:    */     try
/* 290:    */     {
/* 291:299 */       OrdenFabricacion ordenFabricacion = historicoCalidadOrdenFabricacion.getOrdenFabricacion();
/* 292:    */       
/* 293:301 */       historicoCalidadOrdenFabricacion.setFechaControlCalidad(new Date());
/* 294:303 */       if (ordenFabricacion.getProducto().isIndicadorLote()) {
/* 295:304 */         this.servicioLote.guardar(historicoCalidadOrdenFabricacion.getLote());
/* 296:    */       } else {
/* 297:306 */         historicoCalidadOrdenFabricacion.setLote(null);
/* 298:    */       }
/* 299:310 */       if ((historicoCalidadOrdenFabricacion.getEstado().equals(EstadoControlCalidad.CUARENTENA)) && 
/* 300:311 */         (!ordenFabricacion.isIndicadorValidaStockSuborden()) && (ordenFabricacion.isIndicadorSuborden())) {
/* 301:312 */         historicoCalidadOrdenFabricacion.setCantidad(BigDecimal.ZERO);
/* 302:    */       }
/* 303:316 */       HistoricoCalidadOrdenFabricacion historicoAnterior = historicoCalidadOrdenFabricacion.getId() != 0 ? this.servicioHistoricoCalidadOrdenFabricacion.buscarPorId(historicoCalidadOrdenFabricacion.getId()) : null;
/* 304:317 */       EstadoControlCalidad estadoAnterior = historicoAnterior == null ? EstadoControlCalidad.CUARENTENA : historicoAnterior.getEstado();
/* 305:318 */       String descripcionAnteriorMotivoCambio = historicoAnterior == null ? "" : historicoAnterior.getMotivoCambioEstado();
/* 306:    */       
/* 307:    */ 
/* 308:321 */       this.servicioHistoricoCalidadOrdenFabricacion.guardar(historicoCalidadOrdenFabricacion);
/* 309:324 */       if (historicoCalidadOrdenFabricacion.getListaVariableCalidadOrdenFabricacion() != null) {
/* 310:325 */         for (VariableCalidadOrdenFabricacion vcof : historicoCalidadOrdenFabricacion.getListaVariableCalidadOrdenFabricacion()) {
/* 311:326 */           this.variableCalidadOrdenFabricacionDao.guardar(vcof);
/* 312:    */         }
/* 313:    */       }
/* 314:331 */       liberarHistoricoCalidadOrdenFabricacion(historicoCalidadOrdenFabricacion, estadoAnterior, descripcionAnteriorMotivoCambio);
/* 315:    */       
/* 316:    */ 
/* 317:334 */       actualizarCantidadControlCalidad(historicoCalidadOrdenFabricacion);
/* 318:    */       
/* 319:    */ 
/* 320:337 */       Object campos = new HashMap();
/* 321:338 */       ((HashMap)campos).put("indicadorRegistradoCalidad", Boolean.valueOf(true));
/* 322:339 */       this.ordenFabricacionDao.actualizarAtributoEntidad(historicoCalidadOrdenFabricacion.getOrdenFabricacion(), (HashMap)campos);
/* 323:    */     }
/* 324:    */     catch (AS2Exception e)
/* 325:    */     {
/* 326:342 */       this.context.setRollbackOnly();
/* 327:343 */       e.printStackTrace();
/* 328:344 */       throw e;
/* 329:    */     }
/* 330:    */     catch (Exception e)
/* 331:    */     {
/* 332:346 */       this.context.setRollbackOnly();
/* 333:347 */       e.printStackTrace();
/* 334:348 */       throw new ExcepcionAS2(e);
/* 335:    */     }
/* 336:    */   }
/* 337:    */   
/* 338:    */   private void liberarHistoricoCalidadOrdenFabricacion(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion, EstadoControlCalidad estadoAnterior, String descripcionAnteriorMotivoCambio)
/* 339:    */     throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2
/* 340:    */   {
/* 341:355 */     if ((historicoCalidadOrdenFabricacion.getOrdenFabricacion().isIndicadorValidaStockSuborden()) || 
/* 342:356 */       (!historicoCalidadOrdenFabricacion.getOrdenFabricacion().isIndicadorSuborden()))
/* 343:    */     {
/* 344:359 */       if (historicoCalidadOrdenFabricacion.getCantidadControlCalidad().compareTo(historicoCalidadOrdenFabricacion.getCantidad()) < 0) {
/* 345:360 */         crearIngresFabricacionParcialCalidad(historicoCalidadOrdenFabricacion, estadoAnterior, descripcionAnteriorMotivoCambio);
/* 346:    */       }
/* 347:363 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$EstadoControlCalidad[historicoCalidadOrdenFabricacion.getEstado().ordinal()])
/* 348:    */       {
/* 349:    */       case 1: 
/* 350:365 */         if (historicoCalidadOrdenFabricacion.getProductoNuevo() != null)
/* 351:    */         {
/* 352:367 */           this.servicioMovimientoInventario.recepcionarDetalleIngresoFabricacion(historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion());
/* 353:368 */           generarTransformacion(historicoCalidadOrdenFabricacion);
/* 354:    */         }
/* 355:    */         else
/* 356:    */         {
/* 357:371 */           HashMap<String, Object> campos = new HashMap();
/* 358:372 */           campos.put("indicadorCumpleCalidad", Boolean.valueOf(true));
/* 359:373 */           campos.put("descripcion2", historicoCalidadOrdenFabricacion.getMotivoCambioEstado());
/* 360:374 */           campos.put("cantidad", historicoCalidadOrdenFabricacion.getCantidad());
/* 361:375 */           campos.put("cantidadOrigen", historicoCalidadOrdenFabricacion.getCantidad());
/* 362:376 */           campos.put("fechaCreacion", new Date());
/* 363:377 */           campos.put("usuarioCreacion", historicoCalidadOrdenFabricacion.getUsuarioCreacion());
/* 364:378 */           this.detalleMovimientoInventarioDao.actualizarAtributoEntidad(historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion(), campos);
/* 365:    */           
/* 366:    */ 
/* 367:381 */           campos = new HashMap();
/* 368:382 */           campos.put("cantidad", historicoCalidadOrdenFabricacion.getCantidad());
/* 369:383 */           this.historicoCalidadOrdenFabricacionDao.actualizarAtributoEntidad(historicoCalidadOrdenFabricacion, campos);
/* 370:    */         }
/* 371:386 */         break;
/* 372:    */       case 2: 
/* 373:390 */         HashMap<String, Object> campos = new HashMap();
/* 374:391 */         campos.put("cantidad", historicoCalidadOrdenFabricacion.getCantidad());
/* 375:392 */         this.historicoCalidadOrdenFabricacionDao.actualizarAtributoEntidad(historicoCalidadOrdenFabricacion, campos);
/* 376:393 */         break;
/* 377:    */       case 3: 
/* 378:397 */         this.servicioMovimientoInventario.recepcionarDetalleIngresoFabricacion(historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion());
/* 379:398 */         generarTransformacion(historicoCalidadOrdenFabricacion);
/* 380:399 */         break;
/* 381:    */       }
/* 382:    */     }
/* 383:    */   }
/* 384:    */   
/* 385:    */   private void crearIngresFabricacionParcialCalidad(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion, EstadoControlCalidad estadoAnterior, String descripcionAnteriorMotivoCambio)
/* 386:    */     throws AS2Exception, ExcepcionAS2
/* 387:    */   {
/* 388:409 */     BigDecimal cantidad = historicoCalidadOrdenFabricacion.getCantidad();
/* 389:410 */     BigDecimal cantidadControlCalidad = historicoCalidadOrdenFabricacion.getCantidadControlCalidad();
/* 390:411 */     BigDecimal diferencia = cantidad.subtract(cantidadControlCalidad);
/* 391:    */     
/* 392:    */ 
/* 393:414 */     DetalleMovimientoInventario detalleMovimientoInventario = historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion();
/* 394:415 */     DetalleMovimientoInventario detalleMovimientoInventarioNuevo = new DetalleMovimientoInventario();
/* 395:416 */     detalleMovimientoInventarioNuevo.setFechaCreacion(detalleMovimientoInventario.getFechaCreacion());
/* 396:417 */     detalleMovimientoInventarioNuevo.setUsuarioCreacion(historicoCalidadOrdenFabricacion.getUsuarioModificacion() == null ? historicoCalidadOrdenFabricacion.getUsuarioCreacion() : historicoCalidadOrdenFabricacion.getUsuarioModificacion());
/* 397:418 */     detalleMovimientoInventarioNuevo.setCantidad(diferencia);
/* 398:419 */     detalleMovimientoInventarioNuevo.setCantidadOrigen(diferencia);
/* 399:420 */     detalleMovimientoInventarioNuevo.setCosto(detalleMovimientoInventario.getCosto());
/* 400:421 */     detalleMovimientoInventarioNuevo.setIdOrganizacion(detalleMovimientoInventario.getIdOrganizacion());
/* 401:422 */     detalleMovimientoInventarioNuevo.setIdSucursal(detalleMovimientoInventario.getIdSucursal());
/* 402:423 */     detalleMovimientoInventarioNuevo.setBodegaDestino(detalleMovimientoInventario.getBodegaOrigen());
/* 403:424 */     detalleMovimientoInventarioNuevo.setBodegaOrigen(detalleMovimientoInventario.getBodegaOrigen());
/* 404:425 */     detalleMovimientoInventarioNuevo.setLote(detalleMovimientoInventario.getLote());
/* 405:426 */     detalleMovimientoInventarioNuevo.setMovimientoInventario(detalleMovimientoInventario.getMovimientoInventario());
/* 406:427 */     detalleMovimientoInventarioNuevo.setProducto(detalleMovimientoInventario.getProducto());
/* 407:428 */     detalleMovimientoInventarioNuevo.setUnidadConversion(detalleMovimientoInventario.getUnidadConversion());
/* 408:429 */     detalleMovimientoInventarioNuevo.setIndicadorRecibido(false);
/* 409:430 */     detalleMovimientoInventarioNuevo.setIndicadorCumpleCalidad(false);
/* 410:    */     
/* 411:432 */     this.detalleMovimientoInventarioDao.guardar(detalleMovimientoInventarioNuevo);
/* 412:435 */     if ((estadoAnterior == null) || (estadoAnterior.equals(EstadoControlCalidad.CUARENTENA)) || (estadoAnterior.equals(EstadoControlCalidad.RECHAZADO)))
/* 413:    */     {
/* 414:436 */       HistoricoCalidadOrdenFabricacion hcof = new HistoricoCalidadOrdenFabricacion();
/* 415:437 */       hcof.setIdOrganizacion(historicoCalidadOrdenFabricacion.getIdOrganizacion());
/* 416:438 */       hcof.setIdSucursal(historicoCalidadOrdenFabricacion.getIdSucursal());
/* 417:439 */       hcof.setEstado(estadoAnterior);
/* 418:440 */       hcof.setCantidad(diferencia);
/* 419:441 */       hcof.setDetalleIngresoFabricacion(detalleMovimientoInventarioNuevo);
/* 420:442 */       hcof.setFechaControlCalidad(new Date());
/* 421:443 */       hcof.setOrdenFabricacion(historicoCalidadOrdenFabricacion.getOrdenFabricacion());
/* 422:444 */       hcof.setLote(historicoCalidadOrdenFabricacion.getLote());
/* 423:445 */       hcof.setMotivoCambioEstado(descripcionAnteriorMotivoCambio);
/* 424:446 */       this.historicoCalidadOrdenFabricacionDao.guardar(hcof);
/* 425:    */     }
/* 426:450 */     historicoCalidadOrdenFabricacion.setCantidad(cantidadControlCalidad);
/* 427:451 */     detalleMovimientoInventario.setUsuarioCreacion(historicoCalidadOrdenFabricacion.getUsuarioCreacion());
/* 428:452 */     detalleMovimientoInventario.setFechaCreacion(new Date());
/* 429:453 */     detalleMovimientoInventario.setCantidad(cantidadControlCalidad);
/* 430:454 */     detalleMovimientoInventario.setCantidadOrigen(cantidadControlCalidad);
/* 431:    */   }
/* 432:    */   
/* 433:    */   private void generarTransformacion(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion)
/* 434:    */     throws AS2Exception, ExcepcionAS2
/* 435:    */   {
/* 436:461 */     OrdenFabricacion ordenFabricacion = historicoCalidadOrdenFabricacion.getOrdenFabricacion();
/* 437:462 */     DetalleMovimientoInventario detalleMovimientoInventario = historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion();
/* 438:    */     
/* 439:    */ 
/* 440:465 */     Map<String, String> filtros = new HashMap();
/* 441:466 */     filtros.put("idOrganizacion", String.valueOf(historicoCalidadOrdenFabricacion.getIdOrganizacion()));
/* 442:467 */     filtros.put("operacion", "=-1");
/* 443:468 */     filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 444:469 */     List<Documento> listaDocumentoProductoOrigen = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros);
/* 445:    */     
/* 446:    */ 
/* 447:472 */     filtros = new HashMap();
/* 448:473 */     filtros.put("idOrganizacion", String.valueOf(historicoCalidadOrdenFabricacion.getIdOrganizacion()));
/* 449:474 */     filtros.put("operacion", "=1");
/* 450:475 */     filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 451:476 */     List<Documento> listaDocumentoProductoDestino = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros);
/* 452:478 */     if (listaDocumentoProductoOrigen.isEmpty()) {
/* 453:479 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_EGRESO", new String[] { "" });
/* 454:    */     }
/* 455:481 */     if (listaDocumentoProductoDestino.isEmpty()) {
/* 456:482 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_INGRESO", new String[] { "" });
/* 457:    */     }
/* 458:486 */     Producto productoOrigen = detalleMovimientoInventario.getProducto();
/* 459:487 */     Producto productoDestino = historicoCalidadOrdenFabricacion.getProductoNuevo();
/* 460:488 */     Integer idOrganizacion = Integer.valueOf(ordenFabricacion.getIdOrganizacion());
/* 461:489 */     Integer idSucursal = Integer.valueOf(ordenFabricacion.getSucursal().getIdSucursal());
/* 462:490 */     Bodega bodegaOrigen = detalleMovimientoInventario.getBodegaOrigen();
/* 463:491 */     Bodega bodegaDestino = historicoCalidadOrdenFabricacion.getBodegaDestino();
/* 464:492 */     BigDecimal cantidad = detalleMovimientoInventario.getCantidad();
/* 465:493 */     BigDecimal cantidadOrigen = detalleMovimientoInventario.getCantidadOrigen();
/* 466:494 */     Unidad unidadConversion = detalleMovimientoInventario.getUnidadConversion();
/* 467:495 */     Date fecha = detalleMovimientoInventario.getMovimientoInventario().getFecha();
/* 468:496 */     Lote loteDestino = null;
/* 469:497 */     Lote loteOrigen = null;
/* 470:498 */     String prefijoLote = historicoCalidadOrdenFabricacion.getEstado().equals(EstadoControlCalidad.REPROCESO) ? "RPC-" : "CPC-";
/* 471:501 */     if (productoOrigen.isIndicadorLote())
/* 472:    */     {
/* 473:502 */       String codigoLote = "";
/* 474:503 */       if (detalleMovimientoInventario.getLote() != null) {
/* 475:504 */         codigoLote = detalleMovimientoInventario.getLote().getCodigo();
/* 476:505 */       } else if (detalleMovimientoInventario.getMovimientoInventario().getOrdenFabricacion() != null) {
/* 477:506 */         codigoLote = detalleMovimientoInventario.getMovimientoInventario().getOrdenFabricacion().getNumero();
/* 478:    */       }
/* 479:    */       try
/* 480:    */       {
/* 481:509 */         loteOrigen = this.servicioLote.buscarPorCodigo(codigoLote, productoOrigen);
/* 482:    */       }
/* 483:    */       catch (ExcepcionAS2 e)
/* 484:    */       {
/* 485:511 */         loteOrigen = this.servicioLote.crearLoteInterno(idOrganizacion.intValue(), productoOrigen, codigoLote, true);
/* 486:    */       }
/* 487:    */     }
/* 488:516 */     if (productoDestino.isIndicadorLote())
/* 489:    */     {
/* 490:517 */       productoDestino.setPrefijoLote(prefijoLote);
/* 491:518 */       loteDestino = this.servicioLote.crearLoteInterno(idOrganizacion.intValue(), productoDestino, null, true);
/* 492:    */     }
/* 493:521 */     MovimientoInventario transformacionMateriales = new MovimientoInventario();
/* 494:522 */     transformacionMateriales.setIdOrganizacion(idOrganizacion.intValue());
/* 495:523 */     transformacionMateriales.setSucursal(ordenFabricacion.getSucursal());
/* 496:524 */     transformacionMateriales.setFecha(fecha);
/* 497:525 */     transformacionMateriales.setProductoTerminadoTransformacion(productoDestino);
/* 498:526 */     transformacionMateriales.setBodegaOrigen(bodegaOrigen);
/* 499:527 */     transformacionMateriales.setBodegaDestino(bodegaDestino);
/* 500:528 */     transformacionMateriales.setLoteTransformacion(loteDestino);
/* 501:529 */     transformacionMateriales.setDocumento((Documento)listaDocumentoProductoOrigen.get(0));
/* 502:530 */     transformacionMateriales.setDocumentoDestino((Documento)listaDocumentoProductoDestino.get(0));
/* 503:    */     
/* 504:532 */     BigDecimal cantidadPT = cantidad;
/* 505:533 */     if (!productoDestino.getUnidad().equals(productoOrigen.getUnidad()))
/* 506:    */     {
/* 507:536 */       BigDecimal valorConversion = this.servicioProducto.convierteUnidad(productoOrigen.getUnidad(), productoDestino.getUnidad(), productoDestino.getIdProducto(), cantidadPT).setScale(productoDestino.getUnidad().getNumeroDecimales().intValue(), RoundingMode.HALF_UP);
/* 508:537 */       cantidadPT = valorConversion;
/* 509:    */     }
/* 510:539 */     transformacionMateriales.setCantidadTransformacion(cantidadPT);
/* 511:    */     
/* 512:541 */     DetalleMovimientoInventario detalle = new DetalleMovimientoInventario();
/* 513:542 */     detalle.setIdOrganizacion(idOrganizacion.intValue());
/* 514:543 */     detalle.setIdSucursal(idSucursal.intValue());
/* 515:544 */     detalle.setMovimientoInventario(transformacionMateriales);
/* 516:545 */     detalle.setBodegaOrigen(bodegaOrigen);
/* 517:546 */     detalle.setCantidad(cantidad);
/* 518:547 */     detalle.setCantidadOrigen(cantidadOrigen);
/* 519:548 */     detalle.setLote(loteOrigen);
/* 520:549 */     detalle.setProducto(productoOrigen);
/* 521:550 */     detalle.setUnidadConversion(unidadConversion);
/* 522:551 */     transformacionMateriales.getDetalleMovimientosInventario().add(detalle);
/* 523:    */     
/* 524:553 */     this.servicioMovimientoInventario.guardaTransformacionProducto(transformacionMateriales, false, null, null, null);
/* 525:    */     
/* 526:    */ 
/* 527:556 */     detalleMovimientoInventario.setTransformacionAutomatica(transformacionMateriales);
/* 528:557 */     this.detalleMovimientoInventarioDao.guardar(detalleMovimientoInventario);
/* 529:    */     
/* 530:    */ 
/* 531:560 */     historicoCalidadOrdenFabricacion.setTransformacion(transformacionMateriales);
/* 532:561 */     this.servicioHistoricoCalidadOrdenFabricacion.guardar(historicoCalidadOrdenFabricacion);
/* 533:    */   }
/* 534:    */   
/* 535:    */   @Lock(LockType.WRITE)
/* 536:    */   public void actualizarCantidadControlCalidad(HistoricoCalidadOrdenFabricacion historico)
/* 537:    */     throws AS2Exception
/* 538:    */   {
/* 539:570 */     BigDecimal cantidadLiberada = this.servicioHistoricoCalidadOrdenFabricacion.obtenerSumaCantidadPorOrdenFabricacionEstado(historico.getOrdenFabricacion(), EstadoControlCalidad.LIBERADO);
/* 540:571 */     BigDecimal cantidadRechazada = this.servicioHistoricoCalidadOrdenFabricacion.obtenerSumaCantidadPorOrdenFabricacionEstado(historico.getOrdenFabricacion(), EstadoControlCalidad.RECHAZADO);
/* 541:572 */     BigDecimal cantidadCuarentena = this.servicioHistoricoCalidadOrdenFabricacion.obtenerSumaCantidadPorOrdenFabricacionEstado(historico.getOrdenFabricacion(), EstadoControlCalidad.CUARENTENA);
/* 542:573 */     BigDecimal cantidadReproceso = this.servicioHistoricoCalidadOrdenFabricacion.obtenerSumaCantidadPorOrdenFabricacionEstado(historico.getOrdenFabricacion(), EstadoControlCalidad.REPROCESO);
/* 543:    */     
/* 544:575 */     HashMap<String, Object> campos = new HashMap();
/* 545:576 */     campos.put("cantidadLiberadaCalidad", cantidadLiberada);
/* 546:577 */     campos.put("cantidadRechazadaCalidad", cantidadRechazada);
/* 547:578 */     campos.put("cantidadCuarentenaCalidad", cantidadCuarentena);
/* 548:579 */     campos.put("cantidadReprocesoCalidad", cantidadReproceso);
/* 549:580 */     this.ordenFabricacionDao.actualizarAtributoEntidad(historico.getOrdenFabricacion(), campos);
/* 550:    */   }
/* 551:    */   
/* 552:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 553:    */   public void liberar(RegistroPeso registroPeso, Bodega bodegaCalidad)
/* 554:    */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 555:    */   {
/* 556:669 */     if (registroPeso.getDetallePedidoProveedor().getPedidoProveedor().getEstado().ordinal() < 5) {
/* 557:670 */       throw new ExcepcionAS2("msg_error_pedido_no_aprobado");
/* 558:    */     }
/* 559:672 */     if ((registroPeso.getProducto().getIndicadorControlCalidad().booleanValue()) && (
/* 560:673 */       (registroPeso.getLiberadoCastigo() == null) || (!registroPeso.getLiberadoCastigo().booleanValue()))) {
/* 561:674 */       for (VariableCalidadRegistroPeso vcrp : registroPeso.getListaVariableCalidadRegistroPeso()) {
/* 562:675 */         if (!vcrp.isAceptable()) {
/* 563:676 */           throw new ExcepcionAS2("msg_error_variables_fuera_rango");
/* 564:    */         }
/* 565:    */       }
/* 566:    */     }
/* 567:680 */     registroPeso.setControlCalidadFinalizado(new Boolean(true));
/* 568:    */     try
/* 569:    */     {
/* 570:682 */       guardarRegistroPeso(registroPeso, EstadoControlCalidad.LIBERADO);
/* 571:683 */       if (registroPeso.getProducto().getIndicadorControlCalidad().booleanValue()) {
/* 572:685 */         generarRecepcionProveedor(registroPeso, bodegaCalidad);
/* 573:    */       }
/* 574:    */     }
/* 575:    */     catch (ExcepcionAS2Inventario e)
/* 576:    */     {
/* 577:688 */       this.context.setRollbackOnly();
/* 578:689 */       e.printStackTrace();
/* 579:690 */       throw e;
/* 580:    */     }
/* 581:    */     catch (ExcepcionAS2 e)
/* 582:    */     {
/* 583:692 */       this.context.setRollbackOnly();
/* 584:693 */       e.printStackTrace();
/* 585:694 */       throw e;
/* 586:    */     }
/* 587:    */     catch (AS2Exception e)
/* 588:    */     {
/* 589:696 */       this.context.setRollbackOnly();
/* 590:697 */       e.printStackTrace();
/* 591:698 */       throw e;
/* 592:    */     }
/* 593:    */     catch (Exception e)
/* 594:    */     {
/* 595:700 */       this.context.setRollbackOnly();
/* 596:701 */       e.printStackTrace();
/* 597:702 */       throw new ExcepcionAS2(e);
/* 598:    */     }
/* 599:    */   }
/* 600:    */   
/* 601:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 602:    */   public void rechazar(RegistroPeso registroPeso)
/* 603:    */     throws ExcepcionAS2
/* 604:    */   {
/* 605:    */     try
/* 606:    */     {
/* 607:710 */       registroPeso.setEstadoControlCalidad(EstadoControlCalidad.RECHAZADO);
/* 608:711 */       registroPeso.setControlCalidadFinalizado(new Boolean(true));
/* 609:712 */       this.registroPesoDao.guardar(registroPeso);
/* 610:    */       
/* 611:714 */       PedidoProveedor pedidoProveedor = registroPeso.getDetallePedidoProveedor().getPedidoProveedor();
/* 612:715 */       this.servicioPedidoProveedor.actualizarEstado(Integer.valueOf(pedidoProveedor.getId()), Estado.CERRADO);
/* 613:    */     }
/* 614:    */     catch (Exception e)
/* 615:    */     {
/* 616:717 */       this.context.setRollbackOnly();
/* 617:718 */       e.printStackTrace();
/* 618:719 */       throw new ExcepcionAS2(e);
/* 619:    */     }
/* 620:    */   }
/* 621:    */   
/* 622:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 623:    */   public void castigar(RegistroPeso registroPeso, PedidoProveedor pedidoProveedor, DetallePedidoProveedor detalleNuevo)
/* 624:    */     throws ExcepcionAS2, AS2Exception
/* 625:    */   {
/* 626:    */     try
/* 627:    */     {
/* 628:728 */       DetallePedidoProveedor detalleOriginal = null;
/* 629:729 */       if (registroPeso.getDetallePedidoProveedor().getId() != detalleNuevo.getId()) {
/* 630:731 */         for (DetallePedidoProveedor detalle : pedidoProveedor.getListaDetallePedidoProveedor()) {
/* 631:732 */           if (detalle.getId() == registroPeso.getDetallePedidoProveedor().getId())
/* 632:    */           {
/* 633:733 */             detalleOriginal = detalle;
/* 634:734 */             BigDecimal cantidadRecibida = detalleNuevo.getCantidad();
/* 635:735 */             detalle.setCantidad(detalle.getCantidad().subtract(cantidadRecibida));
/* 636:736 */             detalle.setCantidadPorRecibir(detalle.getCantidadPorRecibir().subtract(cantidadRecibida));
/* 637:    */           }
/* 638:    */         }
/* 639:    */       } else {
/* 640:740 */         detalleOriginal = detalleNuevo;
/* 641:    */       }
/* 642:744 */       this.servicioPedidoProveedor.totalizar(pedidoProveedor);
/* 643:745 */       this.servicioPedidoProveedor.guardar(pedidoProveedor);
/* 644:    */       
/* 645:    */ 
/* 646:748 */       registroPeso.setDetallePedidoProveedorOriginal(detalleOriginal);
/* 647:749 */       registroPeso.setDetallePedidoProveedor(detalleNuevo);
/* 648:750 */       registroPeso.setProducto(detalleNuevo.getProducto());
/* 649:751 */       registroPeso.setLiberadoCastigo(new Boolean(true));
/* 650:    */       
/* 651:753 */       guardarRegistroPeso(registroPeso, EstadoControlCalidad.LIBERADO);
/* 652:    */       
/* 653:755 */       generarRecepcionProveedor(registroPeso, registroPeso.getBodegaLiberar());
/* 654:    */     }
/* 655:    */     catch (AS2Exception e)
/* 656:    */     {
/* 657:757 */       this.context.setRollbackOnly();
/* 658:758 */       e.printStackTrace();
/* 659:759 */       throw e;
/* 660:    */     }
/* 661:    */     catch (ExcepcionAS2 e)
/* 662:    */     {
/* 663:761 */       this.context.setRollbackOnly();
/* 664:762 */       e.printStackTrace();
/* 665:763 */       throw e;
/* 666:    */     }
/* 667:    */     catch (Exception e)
/* 668:    */     {
/* 669:765 */       this.context.setRollbackOnly();
/* 670:766 */       e.printStackTrace();
/* 671:767 */       throw new ExcepcionAS2(e);
/* 672:    */     }
/* 673:    */   }
/* 674:    */   
/* 675:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 676:    */   public void generarRecepcionProveedor(RegistroPeso registroPeso, Bodega bodegaCalidad)
/* 677:    */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/* 678:    */   {
/* 679:    */     try
/* 680:    */     {
/* 681:775 */       RecepcionProveedor recepcionProveedor = new RecepcionProveedor();
/* 682:776 */       recepcionProveedor.setNumero("");
/* 683:777 */       recepcionProveedor.setIdOrganizacion(registroPeso.getIdOrganizacion());
/* 684:778 */       recepcionProveedor.setSucursal(this.servicioSucursal.buscarPorId(Integer.valueOf(registroPeso.getIdSucursal())));
/* 685:779 */       recepcionProveedor.setFecha(new Date());
/* 686:780 */       recepcionProveedor.setEstado(Estado.PROCESADO);
/* 687:781 */       recepcionProveedor.setRegistroPeso(registroPeso);
/* 688:782 */       recepcionProveedor.setDescripcion(registroPeso.getDescripcion());
/* 689:    */       
/* 690:784 */       Documento documento = null;
/* 691:785 */       List<Documento> listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.RECEPCION_BODEGA, registroPeso
/* 692:786 */         .getIdOrganizacion());
/* 693:787 */       if ((listaDocumento != null) && (!listaDocumento.isEmpty()))
/* 694:    */       {
/* 695:788 */         documento = (Documento)listaDocumento.get(0);
/* 696:789 */         recepcionProveedor.setDocumento(documento);
/* 697:    */       }
/* 698:    */       else
/* 699:    */       {
/* 700:791 */         throw new AS2Exception("ERROR_FALTA_PARAMETRIZAR_DOCUMENTO", new String[] { DocumentoBase.RECEPCION_BODEGA.toString() });
/* 701:    */       }
/* 702:793 */       recepcionProveedor.setEmpresa(registroPeso.getEmpresa());
/* 703:794 */       recepcionProveedor.setPedidoProveedor(registroPeso.getDetallePedidoProveedor().getPedidoProveedor());
/* 704:    */       
/* 705:796 */       DetalleRecepcionProveedor detalle = new DetalleRecepcionProveedor();
/* 706:797 */       detalle.setIdOrganizacion(registroPeso.getIdOrganizacion());
/* 707:798 */       detalle.setIdSucursal(registroPeso.getIdSucursal());
/* 708:799 */       detalle.setBodega(bodegaCalidad);
/* 709:800 */       detalle.setCantidad(this.servicioRegistroPeso.getCantidadRegistroRecepcionProveedor(registroPeso));
/* 710:801 */       detalle.setDetallePedidoProveedor(registroPeso.getDetallePedidoProveedor());
/* 711:802 */       detalle.setUnidadCompra(registroPeso.getDetallePedidoProveedor().getUnidadCompra());
/* 712:803 */       detalle.setProducto(registroPeso.getDetallePedidoProveedor().getProducto());
/* 713:    */       
/* 714:805 */       InventarioProducto inventarioProducto = new InventarioProducto();
/* 715:806 */       inventarioProducto.setOperacion(recepcionProveedor.getDocumento().getOperacion());
/* 716:807 */       inventarioProducto.setProducto(registroPeso.getDetallePedidoProveedor().getProducto());
/* 717:808 */       detalle.setInventarioProducto(inventarioProducto);
/* 718:809 */       detalle.setRecepcionProveedor(recepcionProveedor);
/* 719:    */       
/* 720:811 */       crearLote(detalle, registroPeso);
/* 721:812 */       recepcionProveedor.getListaDetalleRecepcionProveedor().add(detalle);
/* 722:813 */       if (detalle.getProducto().isIndicadorLote())
/* 723:    */       {
/* 724:814 */         registroPeso.setLote(detalle.getInventarioProducto().getLote());
/* 725:815 */         this.registroPesoDao.guardar(registroPeso);
/* 726:    */       }
/* 727:817 */       this.servicioRecepcionProveedor.guardar(recepcionProveedor);
/* 728:    */     }
/* 729:    */     catch (ExcepcionAS2Financiero e)
/* 730:    */     {
/* 731:820 */       this.context.setRollbackOnly();
/* 732:821 */       e.printStackTrace();
/* 733:822 */       throw e;
/* 734:    */     }
/* 735:    */     catch (ExcepcionAS2 e)
/* 736:    */     {
/* 737:824 */       this.context.setRollbackOnly();
/* 738:825 */       e.printStackTrace();
/* 739:826 */       throw e;
/* 740:    */     }
/* 741:    */     catch (AS2Exception e)
/* 742:    */     {
/* 743:828 */       this.context.setRollbackOnly();
/* 744:829 */       e.printStackTrace();
/* 745:830 */       throw e;
/* 746:    */     }
/* 747:    */     catch (Exception e)
/* 748:    */     {
/* 749:832 */       this.context.setRollbackOnly();
/* 750:833 */       LOG.error(e);
/* 751:834 */       e.printStackTrace();
/* 752:835 */       throw new ExcepcionAS2(e);
/* 753:    */     }
/* 754:    */   }
/* 755:    */   
/* 756:    */   private Lote crearLote(DetalleRecepcionProveedor detalleRecepcion, RegistroPeso registroPeso)
/* 757:    */     throws AS2Exception, ExcepcionAS2
/* 758:    */   {
/* 759:840 */     if (detalleRecepcion.getProducto().isIndicadorLote())
/* 760:    */     {
/* 761:842 */       Lote lote = null;
/* 762:    */       try
/* 763:    */       {
/* 764:844 */         lote = this.servicioLote.buscarPorCodigo(registroPeso.getNumero(), detalleRecepcion.getProducto());
/* 765:    */       }
/* 766:    */       catch (ExcepcionAS2 localExcepcionAS2) {}
/* 767:849 */       if (lote == null)
/* 768:    */       {
/* 769:850 */         Date fechaFabricacion = registroPeso.getFechaElaboracion() != null ? registroPeso.getFechaElaboracion() : registroPeso.getFecha();
/* 770:851 */         Date fechaCaducidad = registroPeso.getFechaCaducidad() != null ? registroPeso.getFechaCaducidad() : null;
/* 771:852 */         if (fechaCaducidad == null)
/* 772:    */         {
/* 773:853 */           fechaCaducidad = registroPeso.getFecha();
/* 774:854 */           if (detalleRecepcion.getProducto().getDiasCaducidad() != null) {
/* 775:855 */             fechaCaducidad = FuncionesUtiles.sumarFechaDiasMeses(fechaCaducidad, detalleRecepcion.getProducto().getDiasCaducidad().intValue());
/* 776:    */           }
/* 777:    */         }
/* 778:859 */         lote = new Lote();
/* 779:860 */         lote.setActivo(true);
/* 780:861 */         lote.setIdOrganizacion(detalleRecepcion.getIdOrganizacion());
/* 781:862 */         lote.setIdSucursal(detalleRecepcion.getIdSucursal());
/* 782:863 */         lote.setProducto(detalleRecepcion.getProducto());
/* 783:864 */         lote.setIndicadorMovimientoInterno(false);
/* 784:865 */         lote.setFechaCaducidad(fechaCaducidad);
/* 785:866 */         lote.setFechaFabricacion(fechaFabricacion);
/* 786:867 */         lote.setEmpresa(detalleRecepcion.getRecepcionProveedor().getEmpresa());
/* 787:    */         
/* 788:869 */         lote.setCodigo(registroPeso.getNumero());
/* 789:870 */         this.servicioLote.guardar(lote);
/* 790:    */       }
/* 791:872 */       detalleRecepcion.getInventarioProducto().setLote(lote);
/* 792:873 */       return lote;
/* 793:    */     }
/* 794:875 */     return null;
/* 795:    */   }
/* 796:    */   
/* 797:    */   public void cargaListaVariableCalidadOrdenFabricacion(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion)
/* 798:    */   {
/* 799:883 */     List<VariableCalidadOrdenFabricacion> lvcof = this.servicioControlCalidad.getListaVariableCalidadOrdenFabricacion(historicoCalidadOrdenFabricacion);
/* 800:885 */     if ((lvcof.isEmpty()) && (historicoCalidadOrdenFabricacion.getOrdenFabricacion() != null)) {
/* 801:886 */       for (VariableCalidadProducto vcp : this.servicioControlCalidad.getListaVariableCalidadProducto(historicoCalidadOrdenFabricacion.getOrdenFabricacion().getProducto())) {
/* 802:887 */         if (vcp.getVariableCalidad().isIndicadorProductoTerminado())
/* 803:    */         {
/* 804:888 */           VariableCalidadOrdenFabricacion vcof = new VariableCalidadOrdenFabricacion();
/* 805:889 */           vcof.setIdOrganizacion(historicoCalidadOrdenFabricacion.getIdOrganizacion());
/* 806:890 */           vcof.setIdSucursal(historicoCalidadOrdenFabricacion.getIdSucursal());
/* 807:891 */           vcof.setVariableCalidadProducto(vcp);
/* 808:892 */           vcof.setHistoricoCalidadOrdenFabricacion(historicoCalidadOrdenFabricacion);
/* 809:893 */           lvcof.add(vcof);
/* 810:    */         }
/* 811:    */       }
/* 812:    */     }
/* 813:    */     HashMap<Integer, BigDecimal> hmPromedio;
/* 814:900 */     if ((historicoCalidadOrdenFabricacion.getId() == 0) && (historicoCalidadOrdenFabricacion.getDetalleIngresoFabricacion() != null))
/* 815:    */     {
/* 816:903 */       Object filters = new HashMap();
/* 817:904 */       ((HashMap)filters).put("ordenFabricacionPrincipal.idOrdenFabricacion", "" + historicoCalidadOrdenFabricacion.getOrdenFabricacion().getIdOrdenFabricacion());
/* 818:905 */       List<OrdenFabricacion> subordenes = this.servicioOrdenFabricacion.obtenerListaCombo("numero", true, (Map)filters);
/* 819:907 */       if (!subordenes.isEmpty())
/* 820:    */       {
/* 821:910 */         hmPromedio = new HashMap();
/* 822:911 */         for (VariableCalidad variableCalidad : this.servicioHistoricoCalidadOrdenFabricacion.getListaVariableCalidadPromedioSubordenes(subordenes)) {
/* 823:912 */           hmPromedio.put(Integer.valueOf(variableCalidad.getIdVariableCalidad()), variableCalidad.getValorNirPromedio());
/* 824:    */         }
/* 825:916 */         for (VariableCalidadOrdenFabricacion variableCalidadOrdenFabricacion : lvcof)
/* 826:    */         {
/* 827:917 */           BigDecimal promedio = (BigDecimal)hmPromedio.get(Integer.valueOf(variableCalidadOrdenFabricacion.getVariableCalidadProducto().getVariableCalidad().getId()));
/* 828:918 */           variableCalidadOrdenFabricacion.setValorNir(promedio == null ? BigDecimal.ZERO : promedio.setScale(2, RoundingMode.HALF_UP));
/* 829:    */         }
/* 830:    */       }
/* 831:    */     }
/* 832:923 */     historicoCalidadOrdenFabricacion.setListaVariableCalidadOrdenFabricacion(lvcof);
/* 833:    */   }
/* 834:    */   
/* 835:    */   public void actualizaValorNIRAceptable(VariableCalidadOrdenFabricacion variableCalidadOrdenFabricacion)
/* 836:    */   {
/* 837:929 */     if (variableCalidadOrdenFabricacion.getValorNir().compareTo(BigDecimal.ZERO) != 0)
/* 838:    */     {
/* 839:930 */       if (variableCalidadOrdenFabricacion.getValorNir().compareTo(variableCalidadOrdenFabricacion.getVariableCalidadProducto().getValorMinimo()) >= 0) {
/* 840:932 */         if (variableCalidadOrdenFabricacion.getValorNir().compareTo(variableCalidadOrdenFabricacion.getVariableCalidadProducto().getValorMaximo()) > 0) {}
/* 841:    */       }
/* 842:    */     }
/* 843:    */     else
/* 844:    */     {
/* 845:933 */       variableCalidadOrdenFabricacion.setAceptable(true); return;
/* 846:    */     }
/* 847:935 */     variableCalidadOrdenFabricacion.setAceptable(false);
/* 848:    */   }
/* 849:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.procesos.servicio.impl.ServicioControlCalidadImpl
 * JD-Core Version:    0.7.0.1
 */