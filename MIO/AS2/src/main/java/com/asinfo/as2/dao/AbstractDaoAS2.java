/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.db.AS2DBBase;
/*   4:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoProveedor;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.entities.Idioma;
/*   7:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   8:    */ import com.asinfo.as2.entities.OrdenFabricacionMaquina;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*  11:    */ import com.asinfo.as2.entities.Tema;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadAccion;
/*  13:    */ import com.asinfo.as2.entities.seguridad.EntidadPermiso;
/*  14:    */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*  15:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  16:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  17:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  18:    */ import com.asinfo.as2.enumeraciones.ClaseBodegaEnum;
/*  19:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  20:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  21:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*  22:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  23:    */ import com.asinfo.as2.enumeraciones.EstadoRegistroPeso;
/*  24:    */ import com.asinfo.as2.enumeraciones.EstadoSolicitudCompraEnum;
/*  25:    */ import com.asinfo.as2.enumeraciones.FrecuenciaFechaEnum;
/*  26:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  27:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*  28:    */ import com.asinfo.as2.enumeraciones.PrioridadEnum;
/*  29:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  30:    */ import com.asinfo.as2.enumeraciones.TipoAccesoContable;
/*  31:    */ import com.asinfo.as2.enumeraciones.TipoArticuloServicioEnum;
/*  32:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*  33:    */ import com.asinfo.as2.enumeraciones.TipoComponenteCostoEnum;
/*  34:    */ import com.asinfo.as2.enumeraciones.TipoCosto;
/*  35:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*  36:    */ import com.asinfo.as2.enumeraciones.TipoDepartamento;
/*  37:    */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*  38:    */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*  39:    */ import com.asinfo.as2.enumeraciones.TipoFrecuenciaEnum;
/*  40:    */ import com.asinfo.as2.enumeraciones.TipoListaPreciosEnum;
/*  41:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  42:    */ import com.asinfo.as2.enumeraciones.TipoRegistroPeso;
/*  43:    */ import com.asinfo.as2.enumeraciones.TipoRubro;
/*  44:    */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*  45:    */ import com.asinfo.as2.util.AppUtil;
/*  46:    */ import java.math.BigDecimal;
/*  47:    */ import java.util.ArrayList;
/*  48:    */ import java.util.Date;
/*  49:    */ import java.util.HashMap;
/*  50:    */ import java.util.Iterator;
/*  51:    */ import java.util.List;
/*  52:    */ import java.util.Map;
/*  53:    */ import java.util.Map.Entry;
/*  54:    */ import java.util.Set;
/*  55:    */ import javax.persistence.EntityManager;
/*  56:    */ import javax.persistence.EntityManagerFactory;
/*  57:    */ import javax.persistence.Query;
/*  58:    */ import javax.persistence.TypedQuery;
/*  59:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  60:    */ import javax.persistence.criteria.CriteriaQuery;
/*  61:    */ import javax.persistence.criteria.Expression;
/*  62:    */ import javax.persistence.criteria.From;
/*  63:    */ import javax.persistence.criteria.Join;
/*  64:    */ import javax.persistence.criteria.JoinType;
/*  65:    */ import javax.persistence.criteria.Order;
/*  66:    */ import javax.persistence.criteria.Path;
/*  67:    */ import javax.persistence.criteria.Predicate;
/*  68:    */ import javax.persistence.criteria.Root;
/*  69:    */ import javax.persistence.metamodel.Attribute;
/*  70:    */ import javax.persistence.metamodel.EntityType;
/*  71:    */ import javax.persistence.metamodel.Metamodel;
/*  72:    */ 
/*  73:    */ public abstract class AbstractDaoAS2<T extends EntidadBase>
/*  74:    */   extends AS2DBBase
/*  75:    */ {
/*  76:    */   protected Class<T> claseEntidad;
/*  77:    */   
/*  78:    */   public AbstractDaoAS2(Class<T> claseEntidad)
/*  79:    */   {
/*  80: 86 */     this.claseEntidad = claseEntidad;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void insertar(T entidad)
/*  84:    */   {
/*  85: 95 */     this.em.persist(entidad);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void actualizar(T entidad)
/*  89:    */   {
/*  90:104 */     this.em.merge(entidad);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void eliminarAnular(T entidad)
/*  94:    */   {
/*  95:113 */     this.em.remove(this.em.merge(entidad));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void eliminar(T entidad)
/*  99:    */   {
/* 100:122 */     this.em.remove(this.em.merge(entidad));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public T buscarPorId(Object id)
/* 104:    */   {
/* 105:132 */     return (EntidadBase)this.em.find(this.claseEntidad, id);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void refrescar(T entidad)
/* 109:    */   {
/* 110:141 */     this.em.refresh(entidad);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void detach(T entidad)
/* 114:    */   {
/* 115:150 */     this.em.detach(entidad);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void flush()
/* 119:    */   {
/* 120:158 */     this.em.flush();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void guardar(T entidad)
/* 124:    */   {
/* 125:167 */     if (entidad.isEliminado())
/* 126:    */     {
/* 127:168 */       if (entidad.getId() != 0) {
/* 128:169 */         eliminar(entidad);
/* 129:    */       }
/* 130:    */     }
/* 131:173 */     else if (entidad.getId() == 0) {
/* 132:174 */       insertar(entidad);
/* 133:    */     } else {
/* 134:176 */       actualizar(entidad);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int contarPorCriterio(Map<String, String> filtros)
/* 139:    */   {
/* 140:189 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 141:190 */     CriteriaQuery<Long> cq = cb.createQuery(Long.class);
/* 142:191 */     Root<T> from = cq.from(this.claseEntidad);
/* 143:192 */     cq.select(cb.count(from));
/* 144:    */     
/* 145:    */ 
/* 146:195 */     List<Expression<?>> expresiones = obtenerExpresiones(filtros, cb, from);
/* 147:197 */     if (!expresiones.isEmpty()) {
/* 148:198 */       cq.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 149:    */     }
/* 150:201 */     return ((Long)this.em.createQuery(cq).getSingleResult()).intValue();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<T> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* 154:    */   {
/* 155:213 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 156:214 */     CriteriaQuery<T> cq = cb.createQuery(this.claseEntidad);
/* 157:215 */     Root<T> from = cq.from(this.claseEntidad);
/* 158:    */     
/* 159:    */ 
/* 160:218 */     agregarFiltros(filtros, cb, cq, from);
/* 161:    */     
/* 162:    */ 
/* 163:221 */     agregarOrdenamiento(sortField, sortOrder, cb, cq, from);
/* 164:    */     
/* 165:    */ 
/* 166:224 */     TypedQuery<T> typedQuery = this.em.createQuery(cq.select(from));
/* 167:225 */     agregarPaginacion(0, 20, typedQuery);
/* 168:    */     
/* 169:227 */     return this.em.createQuery(cq).getResultList();
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List<T> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filtros)
/* 173:    */   {
/* 174:241 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 175:242 */     CriteriaQuery<T> cq = cb.createQuery(this.claseEntidad);
/* 176:243 */     Root<T> from = cq.from(this.claseEntidad);
/* 177:    */     
/* 178:    */ 
/* 179:246 */     agregarOrdenamiento(sortField, sortOrder, cb, cq, from);
/* 180:    */     
/* 181:    */ 
/* 182:249 */     agregarFiltros(filtros, cb, cq, from);
/* 183:    */     
/* 184:    */ 
/* 185:252 */     TypedQuery<T> typedQuery = this.em.createQuery(cq.select(from));
/* 186:253 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 187:    */     
/* 188:255 */     return typedQuery.getResultList();
/* 189:    */   }
/* 190:    */   
/* 191:    */   protected void agregarOrdenamiento(String sortField, boolean sortOrder, CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> from)
/* 192:    */   {
/* 193:269 */     if ((sortField != null) && (sortField.length() > 0))
/* 194:    */     {
/* 195:271 */       Path<String> path = null;
/* 196:272 */       String[] properties = sortField.split("\\.");
/* 197:274 */       if (properties.length == 1)
/* 198:    */       {
/* 199:275 */         path = from.get(sortField);
/* 200:    */       }
/* 201:    */       else
/* 202:    */       {
/* 203:278 */         sortField = properties[0];
/* 204:279 */         Join<Object, Object> joinTable = from.join(sortField, JoinType.LEFT);
/* 205:281 */         for (int i = 1; i < properties.length; i++)
/* 206:    */         {
/* 207:282 */           sortField = properties[i];
/* 208:283 */           if (i < properties.length - 1) {
/* 209:284 */             joinTable = joinTable.join(sortField, JoinType.LEFT);
/* 210:    */           } else {
/* 211:286 */             path = joinTable.get(sortField);
/* 212:    */           }
/* 213:    */         }
/* 214:    */       }
/* 215:291 */       if ((path == null) || (sortOrder)) {
/* 216:292 */         cq.select(from).orderBy(new Order[] { cb.asc(path) });
/* 217:    */       } else {
/* 218:294 */         cq.select(from).orderBy(new Order[] { cb.desc(path) });
/* 219:    */       }
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void agregarPaginacion(int startIndex, int pageSize, TypedQuery<T> typedQuery)
/* 224:    */   {
/* 225:307 */     typedQuery.setFirstResult(startIndex);
/* 226:308 */     typedQuery.setMaxResults(pageSize);
/* 227:    */   }
/* 228:    */   
/* 229:    */   protected List<Expression<?>> obtenerExpresiones(Map<String, String> filtros, CriteriaBuilder criteriaBuilder, Root<T> fromRoot)
/* 230:    */   {
/* 231:322 */     filtros = agregarFiltrosOrganizacion(filtros);
/* 232:    */     
/* 233:324 */     Predicate conjunction = criteriaBuilder.conjunction();
/* 234:325 */     Map<String, Predicate> mapaDisjunction = new HashMap();
/* 235:326 */     List<Expression<?>> expresiones = new ArrayList();
/* 236:329 */     for (String propiedadFiltro : filtros.keySet())
/* 237:    */     {
/* 238:330 */       String valorFiltro = (String)filtros.get(propiedadFiltro);
/* 239:331 */       boolean indicadorConjunction = true;
/* 240:332 */       String[] segmentosPropiedad = propiedadFiltro.split("~");
/* 241:    */       
/* 242:334 */       Predicate disjunction = criteriaBuilder.disjunction();
/* 243:340 */       if (segmentosPropiedad.length > 1) {
/* 244:341 */         if (segmentosPropiedad[0].equals("OR"))
/* 245:    */         {
/* 246:342 */           String grupo_OR = "";
/* 247:343 */           if (segmentosPropiedad.length > 2) {
/* 248:344 */             grupo_OR = segmentosPropiedad[1];
/* 249:    */           }
/* 250:346 */           indicadorConjunction = false;
/* 251:347 */           propiedadFiltro = segmentosPropiedad[(segmentosPropiedad.length - 1)];
/* 252:348 */           if (mapaDisjunction.containsKey(grupo_OR)) {
/* 253:349 */             disjunction = (Predicate)mapaDisjunction.get(grupo_OR);
/* 254:    */           } else {
/* 255:351 */             mapaDisjunction.put(grupo_OR, disjunction);
/* 256:    */           }
/* 257:    */         }
/* 258:353 */         else if (segmentosPropiedad[0].equals("AND"))
/* 259:    */         {
/* 260:356 */           propiedadFiltro = segmentosPropiedad[(segmentosPropiedad.length - 1)];
/* 261:    */         }
/* 262:    */       }
/* 263:361 */       Class<?> tipoDato = getTipoDato(this.claseEntidad, propiedadFiltro);
/* 264:363 */       if ((propiedadFiltro != null) && (!propiedadFiltro.isEmpty()))
/* 265:    */       {
/* 266:365 */         From<?, ?> from = fromRoot;
/* 267:366 */         String[] propiedades = propiedadFiltro.split("\\.");
/* 268:367 */         String propiedad = propiedades[(propiedades.length - 1)];
/* 269:369 */         if (propiedades.length > 1) {
/* 270:370 */           for (int i = 0; i < propiedades.length - 1; i++) {
/* 271:371 */             from = from.join(propiedades[i]);
/* 272:    */           }
/* 273:    */         }
/* 274:376 */         Expression<Boolean> expresion = obtenerExpresion(tipoDato, from, criteriaBuilder, propiedad, valorFiltro);
/* 275:378 */         if (expresion != null) {
/* 276:379 */           if (indicadorConjunction) {
/* 277:380 */             conjunction.getExpressions().add(expresion);
/* 278:    */           } else {
/* 279:382 */             disjunction.getExpressions().add(expresion);
/* 280:    */           }
/* 281:    */         }
/* 282:    */       }
/* 283:    */     }
/* 284:387 */     for (Predicate disjunction : mapaDisjunction.values()) {
/* 285:388 */       if (disjunction.getExpressions().size() > 0) {
/* 286:390 */         conjunction.getExpressions().add(disjunction);
/* 287:    */       }
/* 288:    */     }
/* 289:394 */     expresiones.addAll(conjunction.getExpressions());
/* 290:    */     
/* 291:396 */     return expresiones;
/* 292:    */   }
/* 293:    */   
/* 294:    */   protected void agregarFiltros(Map<String, String> filtros, CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery, Root<T> fromRoot)
/* 295:    */   {
/* 296:409 */     List<Expression<?>> expresiones = obtenerExpresiones(filtros, criteriaBuilder, fromRoot);
/* 297:411 */     if (!expresiones.isEmpty()) {
/* 298:412 */       criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   private Expression<?> obtenerExpresion(Class<?> tipoDato, From<?, ?> from, CriteriaBuilder criteriaBuilder, String propiedadFiltro, String valorFiltro)
/* 303:    */   {
/* 304:429 */     if (tipoDato == String.class)
/* 305:    */     {
/* 306:430 */       DatoFiltro<String> datoFiltro = new DatoFiltro(String.class, from, propiedadFiltro, valorFiltro);
/* 307:431 */       return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 308:    */     }
/* 309:433 */     if ((tipoDato == Integer.class) || (tipoDato.toString().equals("int")) || (tipoDato.toString().equals("short")))
/* 310:    */     {
/* 311:434 */       DatoFiltro<Integer> datoFiltro = new DatoFiltro(Integer.class, from, propiedadFiltro, valorFiltro);
/* 312:435 */       return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 313:    */     }
/* 314:437 */     if ((tipoDato == Long.class) || (tipoDato.toString().equals("long")))
/* 315:    */     {
/* 316:438 */       DatoFiltro<Long> datoFiltro = new DatoFiltro(Long.class, from, propiedadFiltro, valorFiltro);
/* 317:439 */       return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 318:    */     }
/* 319:441 */     if (tipoDato == BigDecimal.class)
/* 320:    */     {
/* 321:442 */       DatoFiltro<BigDecimal> datoFiltro = new DatoFiltro(BigDecimal.class, from, propiedadFiltro, valorFiltro);
/* 322:443 */       return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 323:    */     }
/* 324:445 */     if ((tipoDato == Boolean.class) || (tipoDato.toString().equals("boolean")))
/* 325:    */     {
/* 326:446 */       DatoFiltro<Boolean> datoFiltro = new DatoFiltro(Boolean.class, from, propiedadFiltro, valorFiltro);
/* 327:447 */       return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 328:    */     }
/* 329:449 */     if (tipoDato == Date.class)
/* 330:    */     {
/* 331:450 */       DatoFiltro<Date> datoFiltro = new DatoFiltro(Date.class, from, propiedadFiltro, valorFiltro);
/* 332:451 */       return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 333:    */     }
/* 334:453 */     if (tipoDato == MovimientoInventario.class)
/* 335:    */     {
/* 336:454 */       DatoFiltro<MovimientoInventario> datoFiltro = new DatoFiltro(MovimientoInventario.class, from, propiedadFiltro, valorFiltro);
/* 337:    */       
/* 338:456 */       return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 339:    */     }
/* 340:458 */     if (tipoDato == EntidadUsuario.class)
/* 341:    */     {
/* 342:459 */       DatoFiltro<EntidadUsuario> datoFiltro = new DatoFiltro(EntidadUsuario.class, from, propiedadFiltro, valorFiltro);
/* 343:460 */       return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 344:    */     }
/* 345:462 */     if (tipoDato.isEnum())
/* 346:    */     {
/* 347:463 */       if (tipoDato == Estado.class)
/* 348:    */       {
/* 349:464 */         DatoFiltro<Estado> datoFiltro = new DatoFiltro(Estado.class, from, propiedadFiltro, valorFiltro);
/* 350:465 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 351:    */       }
/* 352:467 */       if (tipoDato == ClaseBodegaEnum.class)
/* 353:    */       {
/* 354:468 */         DatoFiltro<ClaseBodegaEnum> datoFiltro = new DatoFiltro(ClaseBodegaEnum.class, from, propiedadFiltro, valorFiltro);
/* 355:469 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 356:    */       }
/* 357:471 */       if (tipoDato == DocumentoBase.class)
/* 358:    */       {
/* 359:472 */         DatoFiltro<DocumentoBase> datoFiltro = new DatoFiltro(DocumentoBase.class, from, propiedadFiltro, valorFiltro);
/* 360:473 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 361:    */       }
/* 362:475 */       if (tipoDato == TipoAccesoContable.class)
/* 363:    */       {
/* 364:476 */         DatoFiltro<TipoAccesoContable> datoFiltro = new DatoFiltro(TipoAccesoContable.class, from, propiedadFiltro, valorFiltro);
/* 365:    */         
/* 366:478 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 367:    */       }
/* 368:480 */       if (tipoDato == TipoArticuloServicioEnum.class)
/* 369:    */       {
/* 370:481 */         DatoFiltro<TipoArticuloServicioEnum> datoFiltro = new DatoFiltro(TipoArticuloServicioEnum.class, from, propiedadFiltro, valorFiltro);
/* 371:    */         
/* 372:483 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 373:    */       }
/* 374:485 */       if (tipoDato == TipoEmpresaEnum.class)
/* 375:    */       {
/* 376:486 */         DatoFiltro<TipoEmpresaEnum> datoFiltro = new DatoFiltro(TipoEmpresaEnum.class, from, propiedadFiltro, valorFiltro);
/* 377:487 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 378:    */       }
/* 379:489 */       if (tipoDato == TipoCosto.class)
/* 380:    */       {
/* 381:490 */         DatoFiltro<TipoCosto> datoFiltro = new DatoFiltro(TipoCosto.class, from, propiedadFiltro, valorFiltro);
/* 382:491 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 383:    */       }
/* 384:493 */       if (tipoDato == TipoListaPreciosEnum.class)
/* 385:    */       {
/* 386:494 */         DatoFiltro<TipoListaPreciosEnum> datoFiltro = new DatoFiltro(TipoListaPreciosEnum.class, from, propiedadFiltro, valorFiltro);
/* 387:    */         
/* 388:496 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 389:    */       }
/* 390:498 */       if (tipoDato == TipoProducto.class)
/* 391:    */       {
/* 392:499 */         DatoFiltro<TipoProducto> datoFiltro = new DatoFiltro(TipoProducto.class, from, propiedadFiltro, valorFiltro);
/* 393:500 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 394:    */       }
/* 395:502 */       if (tipoDato == TipoRubro.class)
/* 396:    */       {
/* 397:503 */         DatoFiltro<TipoRubro> datoFiltro = new DatoFiltro(TipoRubro.class, from, propiedadFiltro, valorFiltro);
/* 398:504 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 399:    */       }
/* 400:506 */       if (tipoDato == TipoRubroEnum.class)
/* 401:    */       {
/* 402:507 */         DatoFiltro<TipoRubroEnum> datoFiltro = new DatoFiltro(TipoRubroEnum.class, from, propiedadFiltro, valorFiltro);
/* 403:508 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 404:    */       }
/* 405:510 */       if (tipoDato == ProcesoContabilizacionEnum.class)
/* 406:    */       {
/* 407:511 */         DatoFiltro<ProcesoContabilizacionEnum> datoFiltro = new DatoFiltro(ProcesoContabilizacionEnum.class, from, propiedadFiltro, valorFiltro);
/* 408:    */         
/* 409:513 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 410:    */       }
/* 411:515 */       if (tipoDato == TipoCuentaBancariaOrganizacion.class)
/* 412:    */       {
/* 413:516 */         DatoFiltro<TipoCuentaBancariaOrganizacion> datoFiltro = new DatoFiltro(TipoCuentaBancariaOrganizacion.class, from, propiedadFiltro, valorFiltro);
/* 414:    */         
/* 415:518 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 416:    */       }
/* 417:520 */       if (tipoDato == EstadoProduccionEnum.class)
/* 418:    */       {
/* 419:521 */         DatoFiltro<EstadoProduccionEnum> datoFiltro = new DatoFiltro(EstadoProduccionEnum.class, from, propiedadFiltro, valorFiltro);
/* 420:    */         
/* 421:523 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 422:    */       }
/* 423:525 */       if (tipoDato == TipoComponenteCostoEnum.class)
/* 424:    */       {
/* 425:526 */         DatoFiltro<TipoComponenteCostoEnum> datoFiltro = new DatoFiltro(TipoComponenteCostoEnum.class, from, propiedadFiltro, valorFiltro);
/* 426:    */         
/* 427:528 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 428:    */       }
/* 429:530 */       if (tipoDato == Mes.class)
/* 430:    */       {
/* 431:531 */         DatoFiltro<Mes> datoFiltro = new DatoFiltro(Mes.class, from, propiedadFiltro, valorFiltro);
/* 432:532 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 433:    */       }
/* 434:534 */       if (tipoDato == TipoRegistroPeso.class)
/* 435:    */       {
/* 436:535 */         DatoFiltro<TipoRegistroPeso> datoFiltro = new DatoFiltro(TipoRegistroPeso.class, from, propiedadFiltro, valorFiltro);
/* 437:    */         
/* 438:537 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 439:    */       }
/* 440:539 */       if (tipoDato == EstadoRegistroPeso.class)
/* 441:    */       {
/* 442:540 */         DatoFiltro<EstadoRegistroPeso> datoFiltro = new DatoFiltro(EstadoRegistroPeso.class, from, propiedadFiltro, valorFiltro);
/* 443:    */         
/* 444:542 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 445:    */       }
/* 446:544 */       if (tipoDato == EstadoControlCalidad.class)
/* 447:    */       {
/* 448:545 */         DatoFiltro<EstadoControlCalidad> datoFiltro = new DatoFiltro(EstadoControlCalidad.class, from, propiedadFiltro, valorFiltro);
/* 449:    */         
/* 450:547 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 451:    */       }
/* 452:549 */       if (tipoDato == TipoFrecuenciaEnum.class)
/* 453:    */       {
/* 454:550 */         DatoFiltro<TipoFrecuenciaEnum> datoFiltro = new DatoFiltro(TipoFrecuenciaEnum.class, from, propiedadFiltro, valorFiltro);
/* 455:    */         
/* 456:552 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 457:    */       }
/* 458:554 */       if (tipoDato == FrecuenciaFechaEnum.class)
/* 459:    */       {
/* 460:555 */         DatoFiltro<FrecuenciaFechaEnum> datoFiltro = new DatoFiltro(FrecuenciaFechaEnum.class, from, propiedadFiltro, valorFiltro);
/* 461:    */         
/* 462:557 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 463:    */       }
/* 464:559 */       if (tipoDato == PrioridadEnum.class)
/* 465:    */       {
/* 466:560 */         DatoFiltro<PrioridadEnum> datoFiltro = new DatoFiltro(PrioridadEnum.class, from, propiedadFiltro, valorFiltro);
/* 467:561 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 468:    */       }
/* 469:563 */       if (tipoDato == TipoCicloProduccionEnum.class)
/* 470:    */       {
/* 471:564 */         DatoFiltro<TipoCicloProduccionEnum> datoFiltro = new DatoFiltro(TipoCicloProduccionEnum.class, from, propiedadFiltro, valorFiltro);
/* 472:    */         
/* 473:566 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 474:    */       }
/* 475:568 */       if (tipoDato == TipoEmpresa.class)
/* 476:    */       {
/* 477:569 */         DatoFiltro<TipoEmpresa> datoFiltro = new DatoFiltro(TipoEmpresa.class, from, propiedadFiltro, valorFiltro);
/* 478:570 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 479:    */       }
/* 480:572 */       if (tipoDato == EstadoSolicitudCompraEnum.class)
/* 481:    */       {
/* 482:573 */         DatoFiltro<EstadoSolicitudCompraEnum> datoFiltro = new DatoFiltro(EstadoSolicitudCompraEnum.class, from, propiedadFiltro, valorFiltro);
/* 483:    */         
/* 484:575 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 485:    */       }
/* 486:577 */       if (tipoDato == TipoDepartamento.class)
/* 487:    */       {
/* 488:578 */         DatoFiltro<TipoDepartamento> datoFiltro = new DatoFiltro(TipoDepartamento.class, from, propiedadFiltro, valorFiltro);
/* 489:579 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 490:    */       }
/* 491:581 */       if (tipoDato == Parametro.class)
/* 492:    */       {
/* 493:582 */         DatoFiltro<Parametro> datoFiltro = new DatoFiltro(Parametro.class, from, propiedadFiltro, valorFiltro);
/* 494:583 */         return datoFiltro.getExpresion(criteriaBuilder, datoFiltro);
/* 495:    */       }
/* 496:585 */       return null;
/* 497:    */     }
/* 498:588 */     return null;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public Class<?> getTipoDato(Class<?> claseRoot, String propiedadRoot)
/* 502:    */   {
/* 503:601 */     String[] datos = propiedadRoot.split("\\.");
/* 504:    */     
/* 505:603 */     String propiedad = datos[0];
/* 506:    */     
/* 507:605 */     Class<?> clase = this.em.getEntityManagerFactory().getMetamodel().entity(claseRoot).getAttribute(propiedad).getJavaType();
/* 508:607 */     if (datos.length > 1)
/* 509:    */     {
/* 510:608 */       propiedad = propiedadRoot.replace(propiedad + ".", "");
/* 511:609 */       return getTipoDato(clase, propiedad);
/* 512:    */     }
/* 513:611 */     return clase;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public T cargarDetalle(int id)
/* 517:    */   {
/* 518:625 */     return buscarPorId(Integer.valueOf(id));
/* 519:    */   }
/* 520:    */   
/* 521:    */   public Map<String, String> agregarFiltrosOrganizacion(Map<String, String> filters)
/* 522:    */   {
/* 523:630 */     if (filters == null) {
/* 524:631 */       filters = new HashMap();
/* 525:    */     }
/* 526:634 */     if ((!this.claseEntidad.getName().equals(Organizacion.class.getName())) && (!this.claseEntidad.getName().equals(Idioma.class.getName())) && 
/* 527:635 */       (!this.claseEntidad.getName().equals(Tema.class.getName())) && (!this.claseEntidad.getName().equals(EntidadUsuario.class.getName())) && 
/* 528:636 */       (!this.claseEntidad.getName().equals(CreditoTributarioSRI.class.getName())) && 
/* 529:637 */       (!this.claseEntidad.getName().equals(EntidadSistema.class.getName())) && (!this.claseEntidad.getName().equals(EntidadRol.class.getName())) && 
/* 530:638 */       (!this.claseEntidad.getName().equals(EntidadAccion.class.getName())) && (!this.claseEntidad.getName().equals(EntidadPermiso.class.getName())) && 
/* 531:639 */       (!this.claseEntidad.getName().equals(ProcesoOrganizacion.class.getName())) && (!this.claseEntidad.getName().equals(OrdenFabricacionMaquina.class.getName())) && 
/* 532:640 */       (!this.claseEntidad.getName().equals(DetalleLiquidacionAnticipoProveedor.class.getName()))) {
/* 533:642 */       if ((!filters.containsKey("idOrganizacion")) && 
/* 534:643 */         (AppUtil.getOrganizacion() != null)) {
/* 535:644 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 536:    */       }
/* 537:    */     }
/* 538:649 */     return filters;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public void actualizarAtributoEntidad(T entidad, HashMap<String, Object> campos)
/* 542:    */   {
/* 543:663 */     int indice = 1;
/* 544:664 */     String clase = entidad.getClass().getSimpleName().split("_")[0];
/* 545:665 */     String idEntidad = "id" + clase;
/* 546:666 */     String sql = "";
/* 547:667 */     if ((campos != null) && (!campos.isEmpty()))
/* 548:    */     {
/* 549:669 */       sql = " UPDATE " + clase + " t SET ";
/* 550:670 */       Iterator it1 = campos.entrySet().iterator();
/* 551:671 */       while (it1.hasNext())
/* 552:    */       {
/* 553:672 */         Map.Entry e = (Map.Entry)it1.next();
/* 554:673 */         sql = sql + " t." + e.getKey() + "=:valor" + indice + ",";
/* 555:674 */         indice++;
/* 556:    */       }
/* 557:676 */       sql = sql.substring(0, sql.length() - 1);
/* 558:677 */       sql = sql + " WHERE t." + idEntidad + " =:id ";
/* 559:    */       
/* 560:679 */       Query query = this.em.createQuery(sql);
/* 561:    */       
/* 562:681 */       indice = 1;
/* 563:682 */       Iterator it2 = campos.entrySet().iterator();
/* 564:683 */       while (it2.hasNext())
/* 565:    */       {
/* 566:684 */         Map.Entry e = (Map.Entry)it2.next();
/* 567:685 */         query.setParameter("valor" + indice, e.getValue());
/* 568:686 */         indice++;
/* 569:    */       }
/* 570:688 */       query.setParameter("id", Integer.valueOf(entidad.getId()));
/* 571:689 */       query.executeUpdate();
/* 572:    */     }
/* 573:    */   }
/* 574:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.AbstractDaoAS2
 * JD-Core Version:    0.7.0.1
 */