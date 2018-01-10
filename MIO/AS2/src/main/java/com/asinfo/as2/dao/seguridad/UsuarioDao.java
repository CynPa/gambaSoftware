/*   1:    */ package com.asinfo.as2.dao.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.RutaVendedor;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.UsuarioBodega;
/*   8:    */ import com.asinfo.as2.entities.UsuarioDimensionContable;
/*   9:    */ import com.asinfo.as2.entities.UsuarioOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.UsuarioSucursal;
/*  11:    */ import com.asinfo.as2.entities.Visualizacion;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadPermiso;
/*  13:    */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*  14:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  15:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  16:    */ import com.asinfo.as2.entities.seguridad.UsuarioSuperior;
/*  17:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Collection;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.Iterator;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.Stateless;
/*  27:    */ import javax.persistence.EntityManager;
/*  28:    */ import javax.persistence.NoResultException;
/*  29:    */ import javax.persistence.Query;
/*  30:    */ import javax.persistence.TypedQuery;
/*  31:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  32:    */ import javax.persistence.criteria.CriteriaQuery;
/*  33:    */ import javax.persistence.criteria.Expression;
/*  34:    */ import javax.persistence.criteria.Fetch;
/*  35:    */ import javax.persistence.criteria.Join;
/*  36:    */ import javax.persistence.criteria.JoinType;
/*  37:    */ import javax.persistence.criteria.Order;
/*  38:    */ import javax.persistence.criteria.Path;
/*  39:    */ import javax.persistence.criteria.Predicate;
/*  40:    */ import javax.persistence.criteria.Root;
/*  41:    */ 
/*  42:    */ @Stateless
/*  43:    */ public class UsuarioDao
/*  44:    */   extends AbstractDaoAS2<EntidadUsuario>
/*  45:    */ {
/*  46:    */   public UsuarioDao()
/*  47:    */   {
/*  48: 64 */     super(EntidadUsuario.class);
/*  49:    */   }
/*  50:    */   
/*  51:    */   @Deprecated
/*  52:    */   public EntidadUsuario buscarPorNombreUsuario(String nombreUsuario)
/*  53:    */   {
/*  54: 77 */     EntidadUsuario usuario = null;
/*  55:    */     try
/*  56:    */     {
/*  57: 81 */       Query query = this.em.createQuery("SELECT u FROM EntidadUsuario u LEFT JOIN FETCH u.dispositivo WHERE u.nombreUsuario=:nombreUsuario");
/*  58: 82 */       query.setParameter("nombreUsuario", nombreUsuario);
/*  59: 83 */       usuario = (EntidadUsuario)query.getSingleResult();
/*  60:    */     }
/*  61:    */     catch (NoResultException e)
/*  62:    */     {
/*  63: 86 */       return null;
/*  64:    */     }
/*  65: 88 */     return usuario;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public EntidadUsuario buscarPorNombreUsuario(String nombreUsuario, EntidadSistema sistema)
/*  69:    */   {
/*  70:100 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  71:    */     
/*  72:102 */     CriteriaQuery<EntidadUsuario> cqUsuario = cb.createQuery(EntidadUsuario.class);
/*  73:103 */     Root<EntidadUsuario> from = cqUsuario.from(EntidadUsuario.class);
/*  74:104 */     CriteriaQuery<EntidadUsuario> selectUsuario = cqUsuario.select(from);
/*  75:105 */     cqUsuario.where(cb.equal(from.get("nombreUsuario"), nombreUsuario));
/*  76:    */     try
/*  77:    */     {
/*  78:108 */       EntidadUsuario usuario = (EntidadUsuario)this.em.createQuery(selectUsuario).getSingleResult();
/*  79:    */       
/*  80:    */ 
/*  81:111 */       usuario.getListaRol().size();
/*  82:112 */       this.em.detach(usuario);
/*  83:115 */       for (Iterator localIterator1 = usuario.getListaRol().iterator(); localIterator1.hasNext();)
/*  84:    */       {
/*  85:115 */         rol = (EntidadRol)localIterator1.next();
/*  86:116 */         this.em.detach(rol);
/*  87:    */         
/*  88:118 */         CriteriaQuery<EntidadPermiso> cqPermiso = cb.createQuery(EntidadPermiso.class);
/*  89:119 */         Root<EntidadPermiso> fromPermiso = cqPermiso.from(EntidadPermiso.class);
/*  90:120 */         CriteriaQuery<EntidadPermiso> selectPermiso = cqPermiso.select(fromPermiso);
/*  91:121 */         fromPermiso.fetch("procesoOrganizacion").fetch("entidadProceso");
/*  92:    */         
/*  93:123 */         cqPermiso.where(new Predicate[] { cb.equal(fromPermiso.join("procesoOrganizacion").join("entidadProceso").get("sistema"), sistema), cb
/*  94:124 */           .equal(fromPermiso.get("entidadRol"), rol), cb
/*  95:125 */           .equal(fromPermiso.join("procesoOrganizacion").join("entidadProceso").get("indicadorMostrarMenu"), Boolean.valueOf(true)) });
/*  96:126 */         cqPermiso.orderBy(new Order[] { cb.asc(fromPermiso.join("procesoOrganizacion").join("entidadProceso").get("orden")) });
/*  97:    */         
/*  98:128 */         List<EntidadPermiso> listaPermiso = this.em.createQuery(selectPermiso).getResultList();
/*  99:129 */         rol.setListaPermiso(listaPermiso);
/* 100:131 */         for (EntidadPermiso permiso : listaPermiso)
/* 101:    */         {
/* 102:132 */           permiso.getListaAccion().size();
/* 103:133 */           this.em.detach(permiso);
/* 104:134 */           permiso.setEntidadRol(rol);
/* 105:    */         }
/* 106:    */       }
/* 107:    */       EntidadRol rol;
/* 108:138 */       return usuario;
/* 109:    */     }
/* 110:    */     catch (NoResultException e) {}
/* 111:140 */     return null;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public List<EntidadUsuario> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters, List<Integer> listaIdOrganizacion)
/* 115:    */   {
/* 116:152 */     List<Integer> listaIdUsuario = buscarUsuariosPorOrganizaciones(listaIdOrganizacion);
/* 117:    */     
/* 118:154 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 119:155 */     CriteriaQuery<EntidadUsuario> criteriaQuery = criteriaBuilder.createQuery(EntidadUsuario.class);
/* 120:156 */     Root<EntidadUsuario> from = criteriaQuery.from(EntidadUsuario.class);
/* 121:157 */     from.fetch("tipoVendedor", JoinType.LEFT);
/* 122:158 */     from.fetch("planComision", JoinType.LEFT);
/* 123:    */     
/* 124:160 */     Expression<Integer> expIdEntidadUsuario = from.get("idUsuario");
/* 125:    */     
/* 126:162 */     Predicate predicate = expIdEntidadUsuario.in(listaIdUsuario);
/* 127:163 */     Predicate indicadorSuperAdministrador = criteriaBuilder.equal(from.get("indicadorSuperAdministrador"), Boolean.valueOf(false));
/* 128:    */     
/* 129:165 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 130:166 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 131:    */     
/* 132:168 */     Predicate[] listaPredicate = (Predicate[])empresiones.toArray(new Predicate[empresiones.size() + 2]);
/* 133:169 */     listaPredicate[empresiones.size()] = predicate;
/* 134:170 */     listaPredicate[(empresiones.size() + 1)] = indicadorSuperAdministrador;
/* 135:    */     
/* 136:172 */     criteriaQuery.where(listaPredicate);
/* 137:    */     
/* 138:174 */     CriteriaQuery<EntidadUsuario> select = criteriaQuery.select(from);
/* 139:175 */     TypedQuery<EntidadUsuario> typedQuery = this.em.createQuery(select);
/* 140:176 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 141:    */     
/* 142:178 */     return typedQuery.getResultList();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int contarPorCriterio(Map<String, String> filtros, List<Integer> listaIdOrganizacion)
/* 146:    */   {
/* 147:183 */     List<Integer> listaIdUsuario = buscarUsuariosPorOrganizaciones(listaIdOrganizacion);
/* 148:    */     
/* 149:185 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 150:186 */     CriteriaQuery<Long> cq = cb.createQuery(Long.class);
/* 151:187 */     Root<EntidadUsuario> from = cq.from(EntidadUsuario.class);
/* 152:188 */     cq.select(cb.count(from));
/* 153:    */     
/* 154:190 */     Expression<Integer> expIdEntidadUsuario = from.get("idUsuario");
/* 155:191 */     Predicate predicate = expIdEntidadUsuario.in(listaIdUsuario);
/* 156:    */     
/* 157:    */ 
/* 158:194 */     List<Expression<?>> expresiones = obtenerExpresiones(filtros, cb, from);
/* 159:    */     
/* 160:196 */     Predicate[] listaPredicate = (Predicate[])expresiones.toArray(new Predicate[expresiones.size() + 1]);
/* 161:197 */     listaPredicate[expresiones.size()] = predicate;
/* 162:199 */     if (!expresiones.isEmpty()) {
/* 163:200 */       cq.where(listaPredicate);
/* 164:    */     }
/* 165:203 */     return ((Long)this.em.createQuery(cq).getSingleResult()).intValue();
/* 166:    */   }
/* 167:    */   
/* 168:    */   private List<Integer> buscarUsuariosPorOrganizaciones(List<Integer> listaIdOrganizacion)
/* 169:    */   {
/* 170:207 */     StringBuilder sql = new StringBuilder();
/* 171:208 */     sql.append("SELECT eu.idUsuario FROM UsuarioOrganizacion uo");
/* 172:209 */     sql.append(" INNER JOIN uo.entidadUsuario eu ");
/* 173:210 */     sql.append(" WHERE uo.organizacion.idOrganizacion IN :listaIdOrganizacion ");
/* 174:    */     
/* 175:212 */     Query query = this.em.createQuery(sql.toString());
/* 176:213 */     query.setParameter("listaIdOrganizacion", listaIdOrganizacion);
/* 177:    */     
/* 178:215 */     return query.getResultList();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public EntidadUsuario cargarDetalle(int idEntidadUsuario, int idOrganizacion)
/* 182:    */   {
/* 183:227 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 184:    */     
/* 185:    */ 
/* 186:230 */     CriteriaQuery<EntidadUsuario> cqCabecera = criteriaBuilder.createQuery(EntidadUsuario.class);
/* 187:231 */     Root<EntidadUsuario> fromCabecera = cqCabecera.from(EntidadUsuario.class);
/* 188:232 */     Fetch<Object, Object> empleado = fromCabecera.fetch("empleado", JoinType.LEFT);
/* 189:233 */     empleado.fetch("empresa", JoinType.LEFT);
/* 190:234 */     fromCabecera.fetch("tipoVendedor", JoinType.LEFT);
/* 191:235 */     fromCabecera.fetch("recaudador", JoinType.LEFT);
/* 192:236 */     fromCabecera.fetch("planComision", JoinType.LEFT);
/* 193:237 */     fromCabecera.fetch("dispositivo", JoinType.LEFT);
/* 194:    */     
/* 195:239 */     Path<Integer> pathId = fromCabecera.get("idUsuario");
/* 196:240 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idEntidadUsuario)));
/* 197:241 */     CriteriaQuery<EntidadUsuario> selectUsuario = cqCabecera.select(fromCabecera);
/* 198:    */     
/* 199:243 */     EntidadUsuario entidadUsuario = (EntidadUsuario)this.em.createQuery(selectUsuario).getSingleResult();
/* 200:    */     
/* 201:    */ 
/* 202:246 */     CriteriaQuery<UsuarioBodega> cqUsuarioBodega = criteriaBuilder.createQuery(UsuarioBodega.class);
/* 203:247 */     Root<UsuarioBodega> fromUsuarioBodega = cqUsuarioBodega.from(UsuarioBodega.class);
/* 204:248 */     fromUsuarioBodega.fetch("bodega", JoinType.LEFT);
/* 205:249 */     Path<Integer> pathIdUsuarioBodega = fromUsuarioBodega.join("entidadUsuario").get("idUsuario");
/* 206:250 */     Path<Integer> pathIdOrganizacionUsuarioBodega = fromUsuarioBodega.get("idOrganizacion");
/* 207:251 */     Path<Boolean> pathPredeterminado = fromUsuarioBodega.get("predeterminado");
/* 208:252 */     Path<String> pathNombreBodega = fromUsuarioBodega.join("bodega").get("nombre");
/* 209:253 */     cqUsuarioBodega.where(criteriaBuilder.equal(pathIdUsuarioBodega, Integer.valueOf(idEntidadUsuario)));
/* 210:    */     
/* 211:    */ 
/* 212:    */ 
/* 213:257 */     List<Order> listaOrdenar = new ArrayList();
/* 214:258 */     listaOrdenar.add(criteriaBuilder.desc(pathPredeterminado));
/* 215:259 */     listaOrdenar.add(criteriaBuilder.asc(pathNombreBodega));
/* 216:260 */     CriteriaQuery<UsuarioBodega> selectUsuarioBodega = cqUsuarioBodega.select(fromUsuarioBodega).orderBy(listaOrdenar);
/* 217:261 */     List<UsuarioBodega> listaUsuarioBodega = this.em.createQuery(selectUsuarioBodega).getResultList();
/* 218:262 */     entidadUsuario.setListaUsuarioBodega(listaUsuarioBodega);
/* 219:    */     
/* 220:    */ 
/* 221:265 */     CriteriaQuery<UsuarioOrganizacion> cqUsuarioOrganizacion = criteriaBuilder.createQuery(UsuarioOrganizacion.class);
/* 222:266 */     Root<UsuarioOrganizacion> fromUsuarioOrganizacion = cqUsuarioOrganizacion.from(UsuarioOrganizacion.class);
/* 223:267 */     fromUsuarioOrganizacion.fetch("organizacion", JoinType.LEFT);
/* 224:268 */     fromUsuarioOrganizacion.fetch("visualizacion", JoinType.LEFT);
/* 225:269 */     Path<Integer> pathIdUsuarioOrganizacion = fromUsuarioOrganizacion.join("entidadUsuario").get("idUsuario");
/* 226:    */     
/* 227:271 */     cqUsuarioOrganizacion.where(criteriaBuilder.equal(pathIdUsuarioOrganizacion, Integer.valueOf(idEntidadUsuario)));
/* 228:272 */     CriteriaQuery<UsuarioOrganizacion> selectUsuarioOrganizacion = cqUsuarioOrganizacion.select(fromUsuarioOrganizacion);
/* 229:273 */     List<UsuarioOrganizacion> listaUsuarioOrganizacion = this.em.createQuery(selectUsuarioOrganizacion).getResultList();
/* 230:274 */     entidadUsuario.setListaUsuarioOrganizacion(listaUsuarioOrganizacion);
/* 231:    */     
/* 232:    */ 
/* 233:277 */     CriteriaQuery<UsuarioSucursal> cqUsuarioSucursal = criteriaBuilder.createQuery(UsuarioSucursal.class);
/* 234:278 */     Root<UsuarioSucursal> fromUsuarioSucursal = cqUsuarioSucursal.from(UsuarioSucursal.class);
/* 235:279 */     fromUsuarioSucursal.fetch("sucursal", JoinType.LEFT);
/* 236:280 */     Path<Integer> pathIdUsuarioSucursal = fromUsuarioSucursal.join("entidadUsuario").get("idUsuario");
/* 237:281 */     Path<Integer> pathIdOrganizacionUsuarioSucursal = fromUsuarioSucursal.get("idOrganizacion");
/* 238:282 */     cqUsuarioSucursal.where(criteriaBuilder.equal(pathIdUsuarioSucursal, Integer.valueOf(idEntidadUsuario)));
/* 239:    */     
/* 240:    */ 
/* 241:    */ 
/* 242:286 */     CriteriaQuery<UsuarioSucursal> selectUsuarioSucursal = cqUsuarioSucursal.select(fromUsuarioSucursal);
/* 243:287 */     List<UsuarioSucursal> listaUsuarioSucursal = this.em.createQuery(selectUsuarioSucursal).getResultList();
/* 244:288 */     entidadUsuario.setListaUsuarioSucursal(listaUsuarioSucursal);
/* 245:    */     
/* 246:    */ 
/* 247:291 */     CriteriaQuery<UsuarioSuperior> cqUsuarioSuperior = criteriaBuilder.createQuery(UsuarioSuperior.class);
/* 248:292 */     Root<UsuarioSuperior> fromUsuarioSuperior = cqUsuarioSuperior.from(UsuarioSuperior.class);
/* 249:293 */     fromUsuarioSuperior.fetch("superior", JoinType.INNER);
/* 250:294 */     fromUsuarioSuperior.fetch("entidadUsuario", JoinType.INNER);
/* 251:295 */     Path<Integer> pathIdUsuarioSuperior = fromUsuarioSuperior.join("entidadUsuario").get("idUsuario");
/* 252:    */     
/* 253:297 */     cqUsuarioSuperior.where(criteriaBuilder.equal(pathIdUsuarioSuperior, Integer.valueOf(idEntidadUsuario)));
/* 254:298 */     CriteriaQuery<UsuarioSuperior> selectUsuarioSuperior = cqUsuarioSuperior.select(fromUsuarioSuperior);
/* 255:299 */     List<UsuarioSuperior> listaUsuarioSuperior = this.em.createQuery(selectUsuarioSuperior).getResultList();
/* 256:300 */     entidadUsuario.setListaUsuarioSuperior(listaUsuarioSuperior);
/* 257:    */     
/* 258:    */ 
/* 259:303 */     CriteriaQuery<UsuarioDimensionContable> cqUsuarioDimensionContable = criteriaBuilder.createQuery(UsuarioDimensionContable.class);
/* 260:304 */     Root<UsuarioDimensionContable> fromUsuarioDimensionContable = cqUsuarioDimensionContable.from(UsuarioDimensionContable.class);
/* 261:305 */     fromUsuarioDimensionContable.fetch("dimensionContable", JoinType.LEFT);
/* 262:306 */     Path<Integer> pathIdUsuarioDimensionContable = fromUsuarioDimensionContable.join("entidadUsuario").get("idUsuario");
/* 263:    */     
/* 264:308 */     cqUsuarioDimensionContable.where(criteriaBuilder.equal(pathIdUsuarioDimensionContable, Integer.valueOf(idEntidadUsuario)));
/* 265:309 */     CriteriaQuery<UsuarioDimensionContable> selectUsuarioDimensionContable = cqUsuarioDimensionContable.select(fromUsuarioDimensionContable);
/* 266:310 */     List<UsuarioDimensionContable> listaUsuarioDimensionContable = this.em.createQuery(selectUsuarioDimensionContable).getResultList();
/* 267:311 */     entidadUsuario.setListaUsuarioDimensionContable(listaUsuarioDimensionContable);
/* 268:    */     
/* 269:    */ 
/* 270:314 */     entidadUsuario.getListaRol().size();
/* 271:    */     
/* 272:316 */     return entidadUsuario;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public EntidadUsuario cargarDetalleRutaVendedor(int idEntidadUsuario)
/* 276:    */   {
/* 277:321 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 278:    */     
/* 279:    */ 
/* 280:324 */     CriteriaQuery<EntidadUsuario> cqCabecera = criteriaBuilder.createQuery(EntidadUsuario.class);
/* 281:325 */     Root<EntidadUsuario> fromCabecera = cqCabecera.from(EntidadUsuario.class);
/* 282:326 */     Fetch<Object, Object> empleado = fromCabecera.fetch("empleado", JoinType.LEFT);
/* 283:327 */     empleado.fetch("empresa", JoinType.LEFT);
/* 284:328 */     fromCabecera.fetch("tipoVendedor", JoinType.LEFT);
/* 285:    */     
/* 286:330 */     Path<Integer> pathId = fromCabecera.get("idUsuario");
/* 287:331 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idEntidadUsuario)));
/* 288:332 */     CriteriaQuery<EntidadUsuario> selectUsuario = cqCabecera.select(fromCabecera);
/* 289:    */     
/* 290:334 */     EntidadUsuario entidadUsuario = (EntidadUsuario)this.em.createQuery(selectUsuario).getSingleResult();
/* 291:    */     
/* 292:336 */     CriteriaQuery<RutaVendedor> cqVendedorSector = criteriaBuilder.createQuery(RutaVendedor.class);
/* 293:337 */     Root<RutaVendedor> fromVendedorSector = cqVendedorSector.from(RutaVendedor.class);
/* 294:338 */     fromVendedorSector.fetch("usuario", JoinType.LEFT);
/* 295:339 */     fromVendedorSector.fetch("sector", JoinType.LEFT);
/* 296:340 */     Path<Integer> pathVendedor = fromVendedorSector.join("usuario").get("idUsuario");
/* 297:341 */     cqVendedorSector.where(criteriaBuilder.equal(pathVendedor, Integer.valueOf(idEntidadUsuario)));
/* 298:342 */     CriteriaQuery<RutaVendedor> selectVendedorSector = cqVendedorSector.select(fromVendedorSector);
/* 299:343 */     List<RutaVendedor> listaVendedorSector = this.em.createQuery(selectVendedorSector).getResultList();
/* 300:344 */     entidadUsuario.setListaRutaVendedor(listaVendedorSector);
/* 301:    */     
/* 302:346 */     entidadUsuario.setListaUsuarioSuperior(new ArrayList());
/* 303:347 */     entidadUsuario.setListaUsuarioDimensionContable(new ArrayList());
/* 304:    */     
/* 305:349 */     return entidadUsuario;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public EntidadUsuario buscaAgenteComercialPorIdEmpresa(int idEmpresa)
/* 309:    */   {
/* 310:354 */     EntidadUsuario agenteComercial = null;
/* 311:    */     
/* 312:    */ 
/* 313:357 */     String sqlNumeroRegistros = " SELECT count(ac.idUsuario) FROM Empresa em  INNER JOIN em.cliente cl  INNER JOIN cl.agenteComercial ac  WHERE ac.indicadorAgenteComercial= :indicadorAgenteComercial  AND em.idEmpresa=:idEmpresa ";
/* 314:    */     
/* 315:    */ 
/* 316:    */ 
/* 317:361 */     Query query = this.em.createQuery(sqlNumeroRegistros);
/* 318:362 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 319:363 */     query.setParameter("indicadorAgenteComercial", Boolean.valueOf(true));
/* 320:364 */     Long numeroRegistros = (Long)query.getSingleResult();
/* 321:366 */     if (numeroRegistros.longValue() > 0L)
/* 322:    */     {
/* 323:368 */       String sql = " SELECT ac FROM Empresa em  INNER JOIN em.cliente cl  INNER JOIN cl.agenteComercial ac  WHERE ac.indicadorAgenteComercial= :indicadorAgenteComercial  AND em.idEmpresa=:idEmpresa ";
/* 324:    */       
/* 325:    */ 
/* 326:371 */       query = this.em.createQuery(sql);
/* 327:372 */       query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 328:373 */       query.setParameter("indicadorAgenteComercial", Boolean.valueOf(true));
/* 329:374 */       agenteComercial = (EntidadUsuario)query.getSingleResult();
/* 330:    */     }
/* 331:378 */     return agenteComercial;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public EntidadUsuario loginPos(String nombreUsuario, String clave)
/* 335:    */     throws ExcepcionAS2
/* 336:    */   {
/* 337:    */     try
/* 338:    */     {
/* 339:384 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 340:385 */       CriteriaQuery<EntidadUsuario> criteriaQuery = criteriaBuilder.createQuery(EntidadUsuario.class);
/* 341:386 */       Root<EntidadUsuario> from = criteriaQuery.from(EntidadUsuario.class);
/* 342:    */       
/* 343:388 */       Map<String, String> filters = new HashMap();
/* 344:389 */       filters.put("nombreUsuario", "=" + nombreUsuario);
/* 345:390 */       filters.put("clave", "=" + clave);
/* 346:391 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 347:392 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 348:    */       
/* 349:394 */       CriteriaQuery<EntidadUsuario> select = criteriaQuery.select(from);
/* 350:395 */       TypedQuery<EntidadUsuario> typedQuery = this.em.createQuery(select);
/* 351:    */       
/* 352:397 */       return (EntidadUsuario)typedQuery.getSingleResult();
/* 353:    */     }
/* 354:    */     catch (NoResultException e)
/* 355:    */     {
/* 356:400 */       throw new ExcepcionAS2(e);
/* 357:    */     }
/* 358:    */   }
/* 359:    */   
/* 360:    */   public EntidadUsuario usuarioPredeterminado()
/* 361:    */     throws ExcepcionAS2
/* 362:    */   {
/* 363:    */     try
/* 364:    */     {
/* 365:406 */       StringBuilder sql = new StringBuilder();
/* 366:407 */       sql.append("SELECT eu FROM EntidadUsuario eu WHERE eu.predeterminado=:predeterminado");
/* 367:408 */       Query query = this.em.createQuery(sql.toString());
/* 368:409 */       query.setParameter("predeterminado", Boolean.valueOf(true));
/* 369:410 */       query.setMaxResults(1);
/* 370:411 */       return (EntidadUsuario)query.getSingleResult();
/* 371:    */     }
/* 372:    */     catch (NoResultException e)
/* 373:    */     {
/* 374:413 */       throw new ExcepcionAS2(e);
/* 375:    */     }
/* 376:    */   }
/* 377:    */   
/* 378:    */   public EntidadUsuario obtieneUsuarioAutorizaVentas(String nombreUsuario, String clave)
/* 379:    */     throws ExcepcionAS2
/* 380:    */   {
/* 381:    */     try
/* 382:    */     {
/* 383:427 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 384:428 */       CriteriaQuery<EntidadUsuario> criteriaQuery = criteriaBuilder.createQuery(EntidadUsuario.class);
/* 385:429 */       Root<EntidadUsuario> from = criteriaQuery.from(EntidadUsuario.class);
/* 386:    */       
/* 387:431 */       Map<String, String> filters = new HashMap();
/* 388:432 */       filters.put("nombreUsuario", "=" + nombreUsuario);
/* 389:433 */       filters.put("clave", "=" + clave);
/* 390:434 */       filters.put("indicadorAutorizaVentas", "true");
/* 391:435 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 392:436 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 393:    */       
/* 394:438 */       CriteriaQuery<EntidadUsuario> select = criteriaQuery.select(from);
/* 395:439 */       TypedQuery<EntidadUsuario> typedQuery = this.em.createQuery(select);
/* 396:    */       
/* 397:441 */       return (EntidadUsuario)typedQuery.getSingleResult();
/* 398:    */     }
/* 399:    */     catch (NoResultException e)
/* 400:    */     {
/* 401:444 */       throw new ExcepcionAS2(e);
/* 402:    */     }
/* 403:    */   }
/* 404:    */   
/* 405:    */   public List<EntidadUsuario> getEntidadUsuario(int idOrganizacion, boolean indicadorAgenteComercial, Sucursal sucursal)
/* 406:    */   {
/* 407:450 */     StringBuilder sql = new StringBuilder();
/* 408:451 */     sql.append(" SELECT distinct eu FROM UsuarioOrganizacion uo");
/* 409:452 */     sql.append(" INNER JOIN uo.entidadUsuario eu");
/* 410:453 */     sql.append(" WHERE eu.indicadorAgenteComercial=:indicadorAgenteComercial");
/* 411:454 */     sql.append(" AND uo.organizacion.idOrganizacion=:idOrganizacion");
/* 412:    */     
/* 413:456 */     Query query = this.em.createQuery(sql.toString());
/* 414:457 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 415:458 */     query.setParameter("indicadorAgenteComercial", Boolean.valueOf(indicadorAgenteComercial));
/* 416:    */     
/* 417:    */ 
/* 418:461 */     return query.getResultList();
/* 419:    */   }
/* 420:    */   
/* 421:    */   public List<EntidadUsuario> getEntidadUsuario(int idOrganizacion, boolean indicadorAgenteComercial, Sucursal sucursal, Boolean activo, Boolean indicadorConPlanComision)
/* 422:    */   {
/* 423:467 */     StringBuilder sql = new StringBuilder();
/* 424:468 */     sql.append(" SELECT distinct eu FROM UsuarioOrganizacion uo");
/* 425:469 */     sql.append(" INNER JOIN uo.entidadUsuario eu");
/* 426:470 */     sql.append(" LEFT JOIN FETCH eu.planComision pc ");
/* 427:471 */     sql.append(" WHERE eu.indicadorAgenteComercial=:indicadorAgenteComercial");
/* 428:472 */     sql.append(" AND uo.organizacion.idOrganizacion=:idOrganizacion");
/* 429:473 */     if (activo != null) {
/* 430:474 */       sql.append(" AND eu.activo = :activo ");
/* 431:    */     }
/* 432:476 */     if (indicadorConPlanComision != null) {
/* 433:477 */       if (indicadorConPlanComision.booleanValue()) {
/* 434:478 */         sql.append(" AND pc IS NOT NULL ");
/* 435:    */       } else {
/* 436:480 */         sql.append(" AND pc IS NULL ");
/* 437:    */       }
/* 438:    */     }
/* 439:484 */     Query query = this.em.createQuery(sql.toString());
/* 440:485 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 441:486 */     query.setParameter("indicadorAgenteComercial", Boolean.valueOf(indicadorAgenteComercial));
/* 442:487 */     if (activo != null) {
/* 443:488 */       query.setParameter("activo", activo);
/* 444:    */     }
/* 445:492 */     return query.getResultList();
/* 446:    */   }
/* 447:    */   
/* 448:    */   public List<Object[]> getListaPermisosUsuario(int idUsuario)
/* 449:    */   {
/* 450:502 */     StringBuilder sql = new StringBuilder();
/* 451:503 */     sql.append(" SELECT u.nombreUsuario,CONCAT(u.nombre1,' ',u.nombre2), ep.viewName, a.nombre, ep.modulo.nombre, ep.viewId ");
/* 452:504 */     sql.append(" FROM EntidadUsuario u");
/* 453:505 */     sql.append(" INNER JOIN u.listaRol r");
/* 454:506 */     sql.append(" INNER JOIN r.listaPermiso p");
/* 455:507 */     sql.append(" RIGHT JOIN p.listaAccion a");
/* 456:508 */     sql.append(" INNER JOIN p.procesoOrganizacion po");
/* 457:509 */     sql.append(" INNER JOIN po.entidadProceso ep");
/* 458:    */     
/* 459:511 */     sql.append(" WHERE u.idUsuario =:idUsuario ");
/* 460:512 */     sql.append(" ORDER BY ep.viewName");
/* 461:    */     
/* 462:514 */     Query query = this.em.createQuery(sql.toString());
/* 463:515 */     query.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 464:    */     
/* 465:517 */     List<Object[]> lista = query.getResultList();
/* 466:518 */     List<Object[]> result = new ArrayList();
/* 467:519 */     for (Object[] objects : lista)
/* 468:    */     {
/* 469:520 */       Object[] objeto = new Object[6];
/* 470:521 */       objeto[0] = objects[0];
/* 471:522 */       objeto[1] = objects[1];
/* 472:523 */       objeto[2] = objects[2];
/* 473:524 */       objeto[3] = objects[3];
/* 474:525 */       objeto[4] = objects[4];
/* 475:526 */       String cadena = (String)objects[5];
/* 476:527 */       String[] picada = cadena.split("/");
/* 477:528 */       String submodulo = "";
/* 478:529 */       for (int i = 3; i < picada.length - 1; i++) {
/* 479:530 */         if (!picada[i].equals("")) {
/* 480:531 */           submodulo = submodulo + "/" + picada[i].substring(0, 1).toUpperCase() + picada[i].substring(1);
/* 481:    */         }
/* 482:    */       }
/* 483:534 */       objeto[5] = (submodulo.equals("") ? "/" : submodulo);
/* 484:535 */       result.add(objeto);
/* 485:    */     }
/* 486:538 */     return result;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public boolean buscarAdministradores(String nombreUsuario)
/* 490:    */   {
/* 491:552 */     Query query = this.em.createQuery("SELECT u FROM EntidadUsuario u WHERE u.nombreUsuario<>:nombreUsuario AND activo = 1");
/* 492:553 */     query.setParameter("nombreUsuario", nombreUsuario);
/* 493:554 */     List<EntidadUsuario> lista = query.getResultList();
/* 494:555 */     if (lista.size() > 0) {
/* 495:556 */       return false;
/* 496:    */     }
/* 497:558 */     return true;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public List<EntidadUsuario> obtenerListaUsuarios()
/* 501:    */   {
/* 502:565 */     StringBuilder sql = new StringBuilder();
/* 503:566 */     sql.append(" SELECT eu FROM EntidadUsuario eu");
/* 504:567 */     Query query = this.em.createQuery(sql.toString());
/* 505:568 */     return query.getResultList();
/* 506:    */   }
/* 507:    */   
/* 508:    */   public List<Object[]> listaReporteRutaVenededor(EntidadUsuario entidadUsuario)
/* 509:    */   {
/* 510:573 */     StringBuilder sql = new StringBuilder();
/* 511:574 */     sql.append(" select s.nombre, s.codigo, u.nombre1, u.nombre2, rv.diaSemana ");
/* 512:575 */     sql.append(" from RutaVendedor rv ");
/* 513:576 */     sql.append(" inner join rv.usuario u ");
/* 514:577 */     sql.append(" inner join rv.sector s ");
/* 515:578 */     sql.append(" where u = :entidadUsuario ");
/* 516:579 */     Query query = this.em.createQuery(sql.toString());
/* 517:580 */     query.setParameter("entidadUsuario", entidadUsuario);
/* 518:581 */     return query.getResultList();
/* 519:    */   }
/* 520:    */   
/* 521:    */   public List<EntidadUsuario> autocompletarSuperiores(String consulta, EntidadUsuario entidadUsuario)
/* 522:    */   {
/* 523:587 */     StringBuilder sql = new StringBuilder();
/* 524:588 */     sql.append(" SELECT distinct eu FROM UsuarioOrganizacion uo");
/* 525:589 */     sql.append(" INNER JOIN uo.entidadUsuario eu ");
/* 526:590 */     sql.append(" INNER JOIN uo.organizacion org ");
/* 527:591 */     sql.append(" WHERE eu.activo = TRUE ");
/* 528:592 */     sql.append(" AND eu.indicadorAprobador = TRUE ");
/* 529:593 */     sql.append(" AND (UPPER(eu.nombreUsuario) LIKE UPPER(:consulta) ");
/* 530:594 */     sql.append(" OR UPPER(eu.nombre1) LIKE UPPER(:consulta) ");
/* 531:595 */     sql.append(" OR UPPER(eu.nombre2) LIKE UPPER(:consulta) ) ");
/* 532:596 */     sql.append(" AND eu.idUsuario != :idUsuario");
/* 533:597 */     sql.append(" AND org.idOrganizacion IN ( ");
/* 534:598 */     sql.append(" SELECT org2.idOrganizacion FROM UsuarioOrganizacion uo2 INNER JOIN uo2.entidadUsuario eu2 INNER JOIN uo2.organizacion org2 WHERE eu2.idUsuario = :idUsuario )");
/* 535:599 */     Query query = this.em.createQuery(sql.toString());
/* 536:600 */     query.setParameter("idUsuario", Integer.valueOf(entidadUsuario.getIdUsuario()));
/* 537:601 */     query.setParameter("consulta", "%" + consulta.trim() + "%");
/* 538:    */     
/* 539:603 */     return query.getResultList();
/* 540:    */   }
/* 541:    */   
/* 542:    */   public List<EntidadUsuario> buscarJerarquiaInmediata(EntidadUsuario entidadUsuario, boolean indicadorAscendente, DocumentoBase proceso)
/* 543:    */   {
/* 544:608 */     StringBuilder sql = new StringBuilder();
/* 545:609 */     if (indicadorAscendente) {
/* 546:610 */       sql.append(" SELECT distinct sup ");
/* 547:    */     } else {
/* 548:612 */       sql.append(" SELECT distinct eu ");
/* 549:    */     }
/* 550:614 */     sql.append(" FROM UsuarioSuperior usup ");
/* 551:615 */     sql.append(" INNER JOIN usup.entidadUsuario eu ");
/* 552:616 */     sql.append(" INNER JOIN usup.superior sup ");
/* 553:617 */     if (indicadorAscendente)
/* 554:    */     {
/* 555:618 */       sql.append(" WHERE eu.idUsuario = :idUsuario ");
/* 556:619 */       sql.append(" AND eu.activo = true ");
/* 557:    */     }
/* 558:    */     else
/* 559:    */     {
/* 560:621 */       sql.append(" WHERE sup.idUsuario = :idUsuario ");
/* 561:622 */       sql.append(" AND sup.activo = true ");
/* 562:    */     }
/* 563:624 */     if (proceso != null) {
/* 564:625 */       sql.append(" AND usup.documentoBase = :proceso ");
/* 565:    */     }
/* 566:627 */     Query query = this.em.createQuery(sql.toString());
/* 567:628 */     query.setParameter("idUsuario", Integer.valueOf(entidadUsuario.getIdUsuario()));
/* 568:629 */     if (proceso != null) {
/* 569:630 */       query.setParameter("proceso", proceso);
/* 570:    */     }
/* 571:633 */     return query.getResultList();
/* 572:    */   }
/* 573:    */   
/* 574:    */   public List<EntidadUsuario> buscarUsuarioPorSistemaYOrganizacion(String sistema, Integer idOrganizacion)
/* 575:    */   {
/* 576:638 */     StringBuilder sql = new StringBuilder();
/* 577:639 */     sql.append("SELECT DISTINCT(eu) ");
/* 578:640 */     sql.append(" FROM EntidadPermiso ep, EntidadUsuario eu ");
/* 579:641 */     sql.append(" INNER JOIN FETCH eu.listaRol lr  ");
/* 580:642 */     sql.append(" INNER JOIN ep.procesoOrganizacion po ");
/* 581:643 */     sql.append(" INNER JOIN po.entidadProceso epro ");
/* 582:644 */     sql.append(" INNER JOIN epro.sistema es ");
/* 583:645 */     sql.append(" INNER JOIN po.organizacion org ");
/* 584:646 */     sql.append(" INNER JOIN ep.entidadRol er ");
/* 585:647 */     sql.append(" WHERE lr.idRol = er.idRol ");
/* 586:648 */     sql.append(" AND eu.activo IS TRUE ");
/* 587:649 */     if (idOrganizacion != null) {
/* 588:650 */       sql.append(" AND org.idOrganizacion = :idOrganizacion ");
/* 589:    */     }
/* 590:652 */     if (sistema != null) {
/* 591:653 */       sql.append(" AND es.nombre = :sistema ");
/* 592:    */     }
/* 593:656 */     Query query = this.em.createQuery(sql.toString());
/* 594:657 */     if (idOrganizacion != null) {
/* 595:658 */       query.setParameter("idOrganizacion", idOrganizacion);
/* 596:    */     }
/* 597:660 */     if (sistema != null) {
/* 598:661 */       query.setParameter("sistema", sistema);
/* 599:    */     }
/* 600:664 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 601:    */     
/* 602:666 */     List<EntidadUsuario> listaUsuario = query.getResultList();
/* 603:667 */     for (EntidadUsuario entidadUsuario : listaUsuario) {
/* 604:671 */       for (localIterator2 = entidadUsuario.getListaRol().iterator(); localIterator2.hasNext();)
/* 605:    */       {
/* 606:671 */         rol = (EntidadRol)localIterator2.next();
/* 607:672 */         CriteriaQuery<EntidadPermiso> cqPermiso = cb.createQuery(EntidadPermiso.class);
/* 608:673 */         Root<EntidadPermiso> fromPermiso = cqPermiso.from(EntidadPermiso.class);
/* 609:674 */         CriteriaQuery<EntidadPermiso> selectPermiso = cqPermiso.select(fromPermiso);
/* 610:675 */         fromPermiso.fetch("procesoOrganizacion").fetch("entidadProceso");
/* 611:    */         
/* 612:677 */         cqPermiso.where(new Predicate[] { cb.equal(fromPermiso.join("procesoOrganizacion").join("entidadProceso").get("sistema").get("nombre"), sistema), cb
/* 613:678 */           .equal(fromPermiso.get("entidadRol"), rol), cb
/* 614:679 */           .equal(fromPermiso.join("procesoOrganizacion").join("entidadProceso").get("indicadorMostrarMenu"), Boolean.valueOf(true)) });
/* 615:680 */         cqPermiso.orderBy(new Order[] { cb.asc(fromPermiso.join("procesoOrganizacion").join("entidadProceso").get("orden")) });
/* 616:    */         
/* 617:682 */         List<EntidadPermiso> listaPermiso = this.em.createQuery(selectPermiso).getResultList();
/* 618:    */         
/* 619:684 */         rol.setListaPermiso(listaPermiso);
/* 620:686 */         for (EntidadPermiso permiso : listaPermiso)
/* 621:    */         {
/* 622:687 */           permiso.getListaAccion().size();
/* 623:    */           
/* 624:689 */           permiso.setEntidadRol(rol);
/* 625:    */         }
/* 626:    */       }
/* 627:    */     }
/* 628:    */     Iterator localIterator2;
/* 629:    */     EntidadRol rol;
/* 630:693 */     return listaUsuario;
/* 631:    */   }
/* 632:    */   
/* 633:    */   public List<EntidadUsuario> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* 634:    */   {
/* 635:705 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 636:706 */     CriteriaQuery<EntidadUsuario> cq = cb.createQuery(EntidadUsuario.class);
/* 637:707 */     Root<EntidadUsuario> from = cq.from(EntidadUsuario.class);
/* 638:708 */     from.fetch("empleado", JoinType.LEFT);
/* 639:    */     
/* 640:    */ 
/* 641:711 */     agregarFiltros(filtros, cb, cq, from);
/* 642:    */     
/* 643:    */ 
/* 644:714 */     agregarOrdenamiento(sortField, sortOrder, cb, cq, from);
/* 645:    */     
/* 646:    */ 
/* 647:717 */     TypedQuery<EntidadUsuario> typedQuery = this.em.createQuery(cq.select(from));
/* 648:718 */     agregarPaginacion(0, 20, typedQuery);
/* 649:    */     
/* 650:720 */     return this.em.createQuery(cq).getResultList();
/* 651:    */   }
/* 652:    */   
/* 653:    */   public Visualizacion buscarVisualizacionPorNombreUsuario(String nombreUsuario)
/* 654:    */   {
/* 655:725 */     Visualizacion visualizacionUsuario = null;
/* 656:    */     try
/* 657:    */     {
/* 658:729 */       Query query = this.em.createQuery("SELECT vi FROM UsuarioOrganizacion uo INNER JOIN uo.visualizacion vi  INNER JOIN uo.entidadUsuario us INNER JOIN uo.organizacion org WHERE us.nombreUsuario=:nombreUsuario AND org.idOrganizacion =:idOrganizacion");
/* 659:    */       
/* 660:731 */       query.setParameter("nombreUsuario", nombreUsuario);
/* 661:    */       
/* 662:733 */       query.setParameter("idOrganizacion", Integer.valueOf(AppUtil.getOrganizacion().getId()));
/* 663:734 */       visualizacionUsuario = (Visualizacion)query.getSingleResult();
/* 664:    */     }
/* 665:    */     catch (NoResultException e)
/* 666:    */     {
/* 667:736 */       return null;
/* 668:    */     }
/* 669:738 */     return visualizacionUsuario;
/* 670:    */   }
/* 671:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.seguridad.UsuarioDao
 * JD-Core Version:    0.7.0.1
 */