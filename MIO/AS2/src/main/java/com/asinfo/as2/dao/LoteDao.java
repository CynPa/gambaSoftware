/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Atributo;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.entities.Lote;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.NoResultException;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ import javax.persistence.TypedQuery;
/*  17:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  18:    */ import javax.persistence.criteria.CriteriaQuery;
/*  19:    */ import javax.persistence.criteria.Expression;
/*  20:    */ import javax.persistence.criteria.JoinType;
/*  21:    */ import javax.persistence.criteria.Path;
/*  22:    */ import javax.persistence.criteria.Predicate;
/*  23:    */ import javax.persistence.criteria.Root;
/*  24:    */ 
/*  25:    */ @Stateless
/*  26:    */ public class LoteDao
/*  27:    */   extends AbstractDaoAS2<Lote>
/*  28:    */ {
/*  29:    */   @EJB
/*  30:    */   private AtributoDao atributoDao;
/*  31:    */   
/*  32:    */   public LoteDao()
/*  33:    */   {
/*  34: 52 */     super(Lote.class);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List<Lote> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  38:    */   {
/*  39: 62 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  40: 63 */     CriteriaQuery<Lote> criteriaQuery = criteriaBuilder.createQuery(Lote.class);
/*  41: 64 */     Root<Lote> from = criteriaQuery.from(Lote.class);
/*  42:    */     
/*  43: 66 */     from.fetch("producto", JoinType.LEFT);
/*  44:    */     
/*  45: 68 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  46: 69 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  47:    */     
/*  48: 71 */     CriteriaQuery<Lote> select = criteriaQuery.select(from);
/*  49:    */     
/*  50: 73 */     TypedQuery<Lote> typedQuery = this.em.createQuery(select);
/*  51: 74 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  52:    */     
/*  53: 76 */     return typedQuery.getResultList();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Lote cargarDetalle(int idLote)
/*  57:    */   {
/*  58: 86 */     Lote lote = (Lote)buscarPorId(Integer.valueOf(idLote));
/*  59: 87 */     if (lote.getProducto() != null) {
/*  60: 88 */       lote.getProducto().getId();
/*  61:    */     }
/*  62: 91 */     if (lote.getEmpresa() != null) {
/*  63: 92 */       lote.getEmpresa().getId();
/*  64:    */     }
/*  65: 95 */     return lote;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Lote buscarPorCodigo(String codigo)
/*  69:    */     throws ExcepcionAS2
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:108 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  74:109 */       CriteriaQuery<Lote> criteriaQuery = criteriaBuilder.createQuery(Lote.class);
/*  75:110 */       Root<Lote> from = criteriaQuery.from(Lote.class);
/*  76:    */       
/*  77:112 */       from.fetch("producto", JoinType.LEFT);
/*  78:    */       
/*  79:114 */       Map<String, String> filters = new HashMap();
/*  80:115 */       filters.put("codigo", "=" + codigo);
/*  81:116 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  82:117 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  83:    */       
/*  84:119 */       CriteriaQuery<Lote> select = criteriaQuery.select(from);
/*  85:120 */       TypedQuery<Lote> typedQuery = this.em.createQuery(select);
/*  86:121 */       return (Lote)typedQuery.getSingleResult();
/*  87:    */     }
/*  88:    */     catch (NoResultException e)
/*  89:    */     {
/*  90:124 */       throw new ExcepcionAS2("msg_lote_no_encontrado", " " + codigo);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Lote buscarPorCodigo(String codigo, Producto producto)
/*  95:    */     throws ExcepcionAS2
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:139 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 100:140 */       CriteriaQuery<Lote> criteriaQuery = criteriaBuilder.createQuery(Lote.class);
/* 101:141 */       Root<Lote> from = criteriaQuery.from(Lote.class);
/* 102:    */       
/* 103:143 */       from.fetch("producto", JoinType.LEFT);
/* 104:    */       
/* 105:145 */       Map<String, String> filters = new HashMap();
/* 106:146 */       filters.put("codigo", "=" + codigo);
/* 107:147 */       filters.put("producto.idProducto", "=" + producto.getIdProducto());
/* 108:148 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 109:149 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 110:    */       
/* 111:151 */       CriteriaQuery<Lote> select = criteriaQuery.select(from);
/* 112:152 */       TypedQuery<Lote> typedQuery = this.em.createQuery(select);
/* 113:    */       
/* 114:154 */       List<Lote> lista = typedQuery.getResultList();
/* 115:155 */       Lote resultado = null;
/* 116:156 */       for (Lote lote : lista) {
/* 117:157 */         if (resultado == null)
/* 118:    */         {
/* 119:158 */           resultado = lote;
/* 120:    */         }
/* 121:160 */         else if (lote.isIndicadorMovimientoInterno())
/* 122:    */         {
/* 123:161 */           resultado = lote;
/* 124:162 */           break;
/* 125:    */         }
/* 126:    */       }
/* 127:166 */       if (resultado == null) {
/* 128:167 */         throw new ExcepcionAS2("msg_lote_no_encontrado", " " + codigo);
/* 129:    */       }
/* 130:169 */       return resultado;
/* 131:    */     }
/* 132:    */     catch (NoResultException e)
/* 133:    */     {
/* 134:173 */       throw new ExcepcionAS2("msg_lote_no_encontrado", " " + codigo);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Lote buscarPorCodigoProductoProveedor(String codigo, Producto producto, Empresa empresa)
/* 139:    */     throws ExcepcionAS2
/* 140:    */   {
/* 141:    */     try
/* 142:    */     {
/* 143:188 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 144:189 */       CriteriaQuery<Lote> criteriaQuery = criteriaBuilder.createQuery(Lote.class);
/* 145:190 */       Root<Lote> from = criteriaQuery.from(Lote.class);
/* 146:    */       
/* 147:192 */       from.fetch("producto", JoinType.LEFT);
/* 148:    */       
/* 149:194 */       Map<String, String> filters = new HashMap();
/* 150:195 */       filters.put("codigo", "=" + codigo);
/* 151:196 */       if (empresa == null) {
/* 152:197 */         filters.put("indicadorMovimientoInterno", "true");
/* 153:    */       } else {
/* 154:199 */         filters.put("empresa.idEmpresa", "=" + empresa.getIdEmpresa());
/* 155:    */       }
/* 156:201 */       filters.put("producto.idProducto", "=" + producto.getIdProducto());
/* 157:202 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 158:203 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 159:    */       
/* 160:205 */       CriteriaQuery<Lote> select = criteriaQuery.select(from);
/* 161:206 */       TypedQuery<Lote> typedQuery = this.em.createQuery(select);
/* 162:207 */       return (Lote)typedQuery.getSingleResult();
/* 163:    */     }
/* 164:    */     catch (NoResultException e)
/* 165:    */     {
/* 166:210 */       throw new ExcepcionAS2("msg_lote_no_encontrado", " " + codigo);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<Lote> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 171:    */   {
/* 172:221 */     boolean LIKE = false;
/* 173:222 */     boolean MAX_RESULT = false;
/* 174:223 */     if (filters != null)
/* 175:    */     {
/* 176:224 */       LIKE = filters.containsKey("~LIKE");
/* 177:225 */       filters.remove("~LIKE");
/* 178:226 */       MAX_RESULT = filters.containsKey("~MAX_RESULT");
/* 179:227 */       filters.remove("~MAX_RESULT");
/* 180:    */     }
/* 181:230 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 182:231 */     CriteriaQuery<Lote> criteriaQuery = criteriaBuilder.createQuery(Lote.class);
/* 183:232 */     Root<Lote> from = criteriaQuery.from(Lote.class);
/* 184:233 */     from.fetch("producto", JoinType.LEFT);
/* 185:    */     
/* 186:235 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 187:237 */     if (filters != null)
/* 188:    */     {
/* 189:239 */       String codigo = (String)filters.get("codigo");
/* 190:240 */       filters.remove("codigo");
/* 191:242 */       if (codigo != null) {
/* 192:243 */         if (LIKE) {
/* 193:244 */           empresiones.add(criteriaBuilder.like(criteriaBuilder.lower(from.get("codigo").as(String.class)), "%" + codigo + "%"));
/* 194:    */         } else {
/* 195:246 */           empresiones.add(criteriaBuilder.equal(from.get("codigo"), codigo));
/* 196:    */         }
/* 197:    */       }
/* 198:    */     }
/* 199:250 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 200:251 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 201:    */     
/* 202:253 */     CriteriaQuery<Lote> select = criteriaQuery.select(from);
/* 203:254 */     TypedQuery<Lote> typedQuery = this.em.createQuery(select);
/* 204:255 */     if (MAX_RESULT) {
/* 205:256 */       typedQuery.setMaxResults(20);
/* 206:    */     }
/* 207:259 */     return typedQuery.getResultList();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Lote buscarPorCodigoIndicadorMovimiento(String codigo, boolean indicadorMovimiento)
/* 211:    */     throws ExcepcionAS2
/* 212:    */   {
/* 213:    */     try
/* 214:    */     {
/* 215:272 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 216:273 */       CriteriaQuery<Lote> criteriaQuery = criteriaBuilder.createQuery(Lote.class);
/* 217:274 */       Root<Lote> from = criteriaQuery.from(Lote.class);
/* 218:    */       
/* 219:276 */       from.fetch("producto", JoinType.LEFT);
/* 220:    */       
/* 221:278 */       Map<String, String> filters = new HashMap();
/* 222:279 */       filters.put("codigo", "=" + codigo);
/* 223:280 */       filters.put("indicadorMovimientoInterno", new Boolean(indicadorMovimiento).toString());
/* 224:281 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 225:282 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 226:    */       
/* 227:284 */       CriteriaQuery<Lote> select = criteriaQuery.select(from);
/* 228:285 */       TypedQuery<Lote> typedQuery = this.em.createQuery(select);
/* 229:286 */       return (Lote)typedQuery.getSingleResult();
/* 230:    */     }
/* 231:    */     catch (NoResultException e)
/* 232:    */     {
/* 233:289 */       throw new ExcepcionAS2("msg_lote_no_encontrado", " " + codigo);
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Lote verificarLote(int idOrganizacion, String codigo, Producto producto, int idLote)
/* 238:    */   {
/* 239:295 */     StringBuilder sql = new StringBuilder();
/* 240:296 */     sql.append(" SELECT l FROM Lote l ");
/* 241:297 */     sql.append(" LEFT JOIN l.producto p ");
/* 242:298 */     sql.append(" WHERE l.idOrganizacion =:idOrganizacion ");
/* 243:299 */     sql.append(" AND l.codigo = :codigo ");
/* 244:300 */     sql.append(" AND p = :producto ");
/* 245:301 */     sql.append(" AND l.indicadorMovimientoInterno = true ");
/* 246:302 */     sql.append(" AND l.idLote != :idLote ");
/* 247:    */     
/* 248:304 */     Query query = this.em.createQuery(sql.toString());
/* 249:305 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 250:306 */     query.setParameter("codigo", codigo);
/* 251:307 */     query.setParameter("producto", producto);
/* 252:308 */     query.setParameter("idLote", Integer.valueOf(idLote));
/* 253:    */     
/* 254:310 */     List<Lote> lista = query.getResultList();
/* 255:312 */     if (lista.isEmpty()) {
/* 256:313 */       return null;
/* 257:    */     }
/* 258:316 */     return (Lote)lista.get(0);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public Lote getAtributosLote(int idLote, int numeroAtributos)
/* 262:    */   {
/* 263:327 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 264:    */     
/* 265:    */ 
/* 266:330 */     CriteriaQuery<Lote> cqCabecera = criteriaBuilder.createQuery(Lote.class);
/* 267:331 */     Root<Lote> fromCabecera = cqCabecera.from(Lote.class);
/* 268:332 */     fromCabecera.fetch("producto", JoinType.LEFT);
/* 269:333 */     fromCabecera.fetch("empresa", JoinType.LEFT);
/* 270:335 */     for (int i = 1; i <= numeroAtributos; i++)
/* 271:    */     {
/* 272:336 */       fromCabecera.fetch("atributo" + i, JoinType.LEFT);
/* 273:337 */       fromCabecera.fetch("valorAtributo" + i, JoinType.LEFT);
/* 274:    */     }
/* 275:339 */     Path<Integer> pathId = fromCabecera.get("idLote");
/* 276:    */     
/* 277:341 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idLote)));
/* 278:342 */     CriteriaQuery<Lote> cqLote = cqCabecera.select(fromCabecera);
/* 279:    */     
/* 280:344 */     Lote lote = (Lote)this.em.createQuery(cqLote).getSingleResult();
/* 281:    */     
/* 282:346 */     Atributo atributo = lote.getAtributo1();
/* 283:347 */     if (atributo != null)
/* 284:    */     {
/* 285:348 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 286:349 */       lote.setAtributo1(atributo);
/* 287:    */     }
/* 288:352 */     atributo = lote.getAtributo2();
/* 289:353 */     if (atributo != null)
/* 290:    */     {
/* 291:354 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 292:355 */       lote.setAtributo2(atributo);
/* 293:    */     }
/* 294:358 */     atributo = lote.getAtributo3();
/* 295:359 */     if (atributo != null)
/* 296:    */     {
/* 297:360 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 298:361 */       lote.setAtributo3(atributo);
/* 299:    */     }
/* 300:364 */     atributo = lote.getAtributo4();
/* 301:365 */     if (atributo != null)
/* 302:    */     {
/* 303:366 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 304:367 */       lote.setAtributo4(atributo);
/* 305:    */     }
/* 306:370 */     atributo = lote.getAtributo5();
/* 307:371 */     if (atributo != null)
/* 308:    */     {
/* 309:372 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 310:373 */       lote.setAtributo5(atributo);
/* 311:    */     }
/* 312:376 */     atributo = lote.getAtributo6();
/* 313:377 */     if (atributo != null)
/* 314:    */     {
/* 315:378 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 316:379 */       lote.setAtributo6(atributo);
/* 317:    */     }
/* 318:382 */     atributo = lote.getAtributo7();
/* 319:383 */     if (atributo != null)
/* 320:    */     {
/* 321:384 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 322:385 */       lote.setAtributo7(atributo);
/* 323:    */     }
/* 324:388 */     atributo = lote.getAtributo8();
/* 325:389 */     if (atributo != null)
/* 326:    */     {
/* 327:390 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 328:391 */       lote.setAtributo8(atributo);
/* 329:    */     }
/* 330:394 */     atributo = lote.getAtributo9();
/* 331:395 */     if (atributo != null)
/* 332:    */     {
/* 333:396 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 334:397 */       lote.setAtributo9(atributo);
/* 335:    */     }
/* 336:400 */     atributo = lote.getAtributo10();
/* 337:401 */     if (atributo != null)
/* 338:    */     {
/* 339:402 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 340:403 */       lote.setAtributo10(atributo);
/* 341:    */     }
/* 342:406 */     return lote;
/* 343:    */   }
/* 344:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.LoteDao
 * JD-Core Version:    0.7.0.1
 */