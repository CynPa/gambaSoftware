/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   4:    */ import com.asinfo.as2.entities.DetalleDocumentoDigitalizado;
/*   5:    */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*   6:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoCategoriaEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.Comparator;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.Stateless;
/*  17:    */ import javax.persistence.EntityManager;
/*  18:    */ import javax.persistence.Query;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.persistence.TypedQuery;
/*  21:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  22:    */ import javax.persistence.criteria.CriteriaQuery;
/*  23:    */ import javax.persistence.criteria.Expression;
/*  24:    */ import javax.persistence.criteria.Fetch;
/*  25:    */ import javax.persistence.criteria.JoinType;
/*  26:    */ import javax.persistence.criteria.Predicate;
/*  27:    */ import javax.persistence.criteria.Root;
/*  28:    */ 
/*  29:    */ @Stateless
/*  30:    */ public class DetalleDocumentoDigitalizadoDao
/*  31:    */   extends AbstractDaoAS2<DetalleDocumentoDigitalizado>
/*  32:    */ {
/*  33:    */   public DetalleDocumentoDigitalizadoDao()
/*  34:    */   {
/*  35: 34 */     super(DetalleDocumentoDigitalizado.class);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<DetalleDocumentoDigitalizado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 46 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  41: 47 */     CriteriaQuery<DetalleDocumentoDigitalizado> criteriaQuery = criteriaBuilder.createQuery(DetalleDocumentoDigitalizado.class);
/*  42: 48 */     Root<DetalleDocumentoDigitalizado> from = criteriaQuery.from(DetalleDocumentoDigitalizado.class);
/*  43:    */     
/*  44: 50 */     from.fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  45: 51 */     Fetch<Object, Object> documentoDigitalizadoDepartamento = from.fetch("documentoDigitalizadoDepartamento", JoinType.LEFT);
/*  46: 52 */     documentoDigitalizadoDepartamento.fetch("documentoDigitalizado", JoinType.LEFT).fetch("categoriaDocumentoDigitalizado", JoinType.LEFT);
/*  47: 53 */     documentoDigitalizadoDepartamento.fetch("departamento", JoinType.LEFT);
/*  48:    */     
/*  49: 55 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  50: 56 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  51:    */     
/*  52: 58 */     CriteriaQuery<DetalleDocumentoDigitalizado> select = criteriaQuery.select(from);
/*  53:    */     
/*  54: 60 */     TypedQuery<DetalleDocumentoDigitalizado> typedQuery = this.em.createQuery(select);
/*  55: 61 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  56:    */     
/*  57: 63 */     return typedQuery.getResultList();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public List<DetalleDocumentoDigitalizado> cargarListaDetalleDocumentoDigitalizadoEmpleado(int idEmpleado)
/*  61:    */   {
/*  62: 68 */     StringBuilder sql = new StringBuilder();
/*  63: 69 */     sql.append(" SELECT dtdd FROM DetalleDocumentoDigitalizado dtdd ");
/*  64: 70 */     sql.append(" WHERE dtdd.empleado.idEmpleado = :idEmpleado ");
/*  65: 71 */     sql.append(" AND dtdd.documentoDigitalizadoDepartamento.documentoDigitalizado.categoriaDocumentoDigitalizado.activo IS TRUE ");
/*  66: 72 */     sql.append(" AND dtdd.documentoDigitalizadoDepartamento.documentoDigitalizado.activo IS TRUE ");
/*  67: 73 */     sql.append(" ORDER BY dtdd.documentoDigitalizadoDepartamento.documentoDigitalizado.categoriaDocumentoDigitalizado.nombre");
/*  68:    */     
/*  69: 75 */     Query query = this.em.createQuery(sql.toString());
/*  70: 76 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  71: 77 */     return query.getResultList();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<DetalleDocumentoDigitalizado> cargarListaDetalleDocumentoDigitalizadoEmpresa(int idEmpresa, int idOrganizacion, int idDocumentoDigitalizado)
/*  75:    */   {
/*  76: 83 */     StringBuilder sql = new StringBuilder();
/*  77: 84 */     sql.append("SELECT dtdd FROM DetalleDocumentoDigitalizado dtdd");
/*  78: 85 */     sql.append(" LEFT JOIN dtdd.empresa e");
/*  79: 86 */     sql.append(" LEFT JOIN dtdd.documentoDigitalizado dd");
/*  80: 87 */     sql.append(" LEFT JOIN dd.categoriaDocumentoDigitalizado cdd");
/*  81: 88 */     sql.append(" WHERE dd.idOrganizacion = :idOrganizacion");
/*  82: 89 */     sql.append(" AND e.idEmpresa = :idEmpresa");
/*  83: 90 */     sql.append(" AND cdd.activo IS TRUE");
/*  84: 91 */     sql.append(" AND dd.activo IS TRUE");
/*  85: 92 */     if (idDocumentoDigitalizado > 0) {
/*  86: 93 */       sql.append(" AND dd.idDocumentoDigitalizado = :idDocumentoDigitalizado");
/*  87:    */     }
/*  88: 95 */     sql.append(" ORDER BY cdd.nombre");
/*  89:    */     
/*  90: 97 */     Query query = this.em.createQuery(sql.toString());
/*  91: 98 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  92: 99 */     if (idDocumentoDigitalizado > 0) {
/*  93:100 */       query.setParameter("idDocumentoDigitalizado", Integer.valueOf(idDocumentoDigitalizado));
/*  94:    */     }
/*  95:102 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  96:103 */     return query.getResultList();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public List<DocumentoDigitalizadoCategoriaEmpresa> cargarListaDetalleDocumentoDigitalizadoClienteProveedor(int idCategoriaEmpresa, boolean indicadorCliente, boolean indicadorProveedor, int idDocumentoDigitalizado)
/* 100:    */   {
/* 101:109 */     StringBuilder sql = new StringBuilder();
/* 102:110 */     sql.append(" SELECT ddce ");
/* 103:111 */     sql.append(" FROM CategoriaEmpresa ce ");
/* 104:112 */     sql.append(" INNER JOIN ce.listaDocumentoDigitalizadoCategoriaEmpresa ddce ");
/* 105:113 */     sql.append(" INNER JOIN FETCH ddce.documentoDigitalizado dd ");
/* 106:114 */     sql.append(" LEFT JOIN FETCH dd.categoriaDocumentoDigitalizado cdd ");
/* 107:115 */     sql.append(" WHERE ce.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 108:116 */     sql.append(" AND cdd.activo = true ");
/* 109:117 */     sql.append(" AND dd.activo = true ");
/* 110:118 */     if ((indicadorCliente) && (indicadorProveedor)) {
/* 111:119 */       sql.append(" AND (dd.indicadorCliente IS TRUE OR dd.indicadorProveedor IS TRUE ) ");
/* 112:120 */     } else if (indicadorCliente) {
/* 113:121 */       sql.append(" AND (dd.indicadorCliente IS TRUE) ");
/* 114:122 */     } else if (indicadorProveedor) {
/* 115:123 */       sql.append(" AND (dd.indicadorProveedor IS TRUE) ");
/* 116:    */     } else {
/* 117:125 */       sql.append(" AND 2 = 1 ");
/* 118:    */     }
/* 119:127 */     if (idDocumentoDigitalizado != 0) {
/* 120:128 */       sql.append(" AND dd.idDocumentoDigitalizado = :idDocumentoDigitalizado");
/* 121:    */     }
/* 122:130 */     sql.append(" ORDER BY dd.indicadorProveedor");
/* 123:    */     
/* 124:132 */     Query query = this.em.createQuery(sql.toString());
/* 125:133 */     query.setParameter("idCategoriaEmpresa", Integer.valueOf(idCategoriaEmpresa));
/* 126:134 */     if (idDocumentoDigitalizado != 0) {
/* 127:135 */       query.setParameter("idDocumentoDigitalizado", Integer.valueOf(idDocumentoDigitalizado));
/* 128:    */     }
/* 129:137 */     return query.getResultList();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List reporteDocumentosDigitalizadosPorEmpleado(int idEmpleado)
/* 133:    */   {
/* 134:142 */     StringBuilder sql = new StringBuilder();
/* 135:143 */     sql.append(" SELECT dtdd.documentoDigitalizadoDepartamento.documentoDigitalizado.categoriaDocumentoDigitalizado.nombre,dtdd.documentoDigitalizadoDepartamento.documentoDigitalizado.nombre,dtdd.fechaDesde,dtdd.fechaHasta,dtdd.documentoDigitalizadoDepartamento.requerido FROM DetalleDocumentoDigitalizado dtdd ");
/* 136:144 */     sql.append(" WHERE dtdd.empleado.idEmpleado = :idEmpleado ");
/* 137:145 */     sql.append(" ORDER BY dtdd.documentoDigitalizadoDepartamento.documentoDigitalizado.categoriaDocumentoDigitalizado.nombre");
/* 138:    */     
/* 139:147 */     Query query = this.em.createQuery(sql.toString());
/* 140:148 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 141:149 */     return query.getResultList();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<DetalleDocumentoDigitalizado> reporteDocumentosCargarDetalle(int idEmpleado, int idDocumentoDigitalizadoDepartamento, Date fechaVencer)
/* 145:    */   {
/* 146:154 */     StringBuilder sql = new StringBuilder();
/* 147:155 */     sql.append(" SELECT ddt");
/* 148:156 */     sql.append(" FROM DetalleDocumentoDigitalizado ddt ");
/* 149:157 */     sql.append(" WHERE ddt.empleado.idEmpleado = :idEmpleado ");
/* 150:158 */     sql.append(" AND ddt.documentoDigitalizadoDepartamento.idDocumentoDigitalizadoDepartamento = :idDocumentoDigitalizadoDepartamento ");
/* 151:159 */     if (fechaVencer != null)
/* 152:    */     {
/* 153:160 */       sql.append(" AND ddt.fechaHasta != null");
/* 154:161 */       sql.append(" AND ddt.fechaHasta < :fechaVencer");
/* 155:    */     }
/* 156:164 */     Query query = this.em.createQuery(sql.toString());
/* 157:165 */     query.setParameter("idDocumentoDigitalizadoDepartamento", Integer.valueOf(idDocumentoDigitalizadoDepartamento));
/* 158:166 */     if (fechaVencer != null) {
/* 159:167 */       query.setParameter("fechaVencer", fechaVencer);
/* 160:    */     }
/* 161:169 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 162:170 */     return query.getResultList();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List cargarListaDetalleDocumentoDigitalizado(int idOrganizacion, int idEmpleado, int idDepartamento, int idDocumentoDigitalizado, boolean cargados, boolean noCargados)
/* 166:    */   {
/* 167:177 */     StringBuilder sql2 = new StringBuilder();
/* 168:178 */     sql2.append(" SELECT dd.categoriaDocumentoDigitalizado.nombre, dd.nombre,ddd.requerido,em.apellidos + ' ' + em.nombres,em.empresa.identificacion,dto.nombre,em.idEmpleado,ddd.idDocumentoDigitalizadoDepartamento");
/* 169:179 */     sql2.append(" FROM Empleado em ");
/* 170:180 */     sql2.append(" INNER JOIN em.departamento dto ");
/* 171:181 */     sql2.append(" INNER JOIN dto.listaDocumentoDigitalizadoDepartamento ddd ");
/* 172:182 */     sql2.append(" INNER JOIN ddd.documentoDigitalizado dd ");
/* 173:183 */     sql2.append(" WHERE dd.idOrganizacion = :idOrganizacion ");
/* 174:184 */     sql2.append(" AND dd.categoriaDocumentoDigitalizado.activo IS TRUE ");
/* 175:185 */     sql2.append(" AND dd.activo IS TRUE ");
/* 176:187 */     if (idEmpleado != 0) {
/* 177:188 */       sql2.append(" AND em.idEmpleado = :idEmpleado ");
/* 178:    */     }
/* 179:190 */     if (idDepartamento != 0) {
/* 180:191 */       sql2.append(" AND dto.idDepartamento = :idDepartamento ");
/* 181:    */     }
/* 182:193 */     if (idDocumentoDigitalizado != 0) {
/* 183:194 */       sql2.append(" AND dd.idDocumentoDigitalizado = :idDocumentoDigitalizado ");
/* 184:    */     }
/* 185:196 */     sql2.append(" ORDER BY dd.categoriaDocumentoDigitalizado.nombre, dto.nombre");
/* 186:    */     
/* 187:198 */     Query query2 = this.em.createQuery(sql2.toString());
/* 188:199 */     query2.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 189:200 */     if (idEmpleado != 0) {
/* 190:201 */       query2.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 191:    */     }
/* 192:203 */     if (idDepartamento != 0) {
/* 193:204 */       query2.setParameter("idDepartamento", Integer.valueOf(idDepartamento));
/* 194:    */     }
/* 195:206 */     if (idDocumentoDigitalizado != 0) {
/* 196:207 */       query2.setParameter("idDocumentoDigitalizado", Integer.valueOf(idDocumentoDigitalizado));
/* 197:    */     }
/* 198:209 */     List lista2 = query2.getResultList();
/* 199:210 */     return lista2;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<Object[]> getReporteDocumentoDigitalizadoEmpresa(int idOrganizacion, CategoriaEmpresa categoriaEmpresa, Empresa empresa, DocumentoDigitalizado documento, Date fechaTopeVencimiento, boolean indicadorCliente, boolean indicadorProveedor, boolean cargados, boolean noCargados)
/* 203:    */   {
/* 204:218 */     Map<String, Object[]> mapaResultado = new HashMap();
/* 205:220 */     if (cargados)
/* 206:    */     {
/* 207:221 */       StringBuilder sql = new StringBuilder();
/* 208:222 */       sql.append(" SELECT emp.idEmpresa, dd.idDocumentoDigitalizado, cdd.nombre, dd.nombre, emp.nombreFiscal, emp.identificacion, ce.nombre, ddd.fechaDesde, ddd.fechaHasta, ddd.fichero, ddce.requerido ");
/* 209:223 */       sql.append(" FROM Empresa emp ");
/* 210:224 */       sql.append(" INNER JOIN emp.categoriaEmpresa ce ");
/* 211:225 */       sql.append(" INNER JOIN emp.listaDetalleDocumentoDigitalizadoEmpleado ddd ");
/* 212:226 */       sql.append(" INNER JOIN ddd.documentoDigitalizadoCategoriaEmpresa ddce ");
/* 213:227 */       sql.append(" INNER JOIN ddd.documentoDigitalizado dd ");
/* 214:228 */       sql.append(" INNER JOIN dd.categoriaDocumentoDigitalizado cdd ");
/* 215:229 */       sql.append(" LEFT JOIN emp.cliente cli ");
/* 216:230 */       sql.append(" WHERE emp.idOrganizacion = :idOrganizacion ");
/* 217:231 */       if (empresa != null) {
/* 218:232 */         sql.append(" AND emp.idEmpresa = :idEmpresa ");
/* 219:    */       }
/* 220:234 */       if (categoriaEmpresa != null) {
/* 221:235 */         sql.append(" AND ce.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 222:    */       }
/* 223:237 */       if (indicadorCliente)
/* 224:    */       {
/* 225:238 */         sql.append(" AND emp.indicadorCliente IS TRUE ");
/* 226:239 */         sql.append(" AND cli.tipoCliente != :subcliente ");
/* 227:    */       }
/* 228:241 */       if (indicadorProveedor) {
/* 229:242 */         sql.append(" AND emp.indicadorProveedor IS TRUE ");
/* 230:    */       }
/* 231:244 */       if (fechaTopeVencimiento != null) {
/* 232:245 */         sql.append(" AND dd.caduca IS TRUE AND ddd.fechaHasta <= :fechaTopeVencimiento ");
/* 233:    */       }
/* 234:247 */       if (cargados) {
/* 235:248 */         sql.append(" AND ddd.fichero IS NOT NULL ");
/* 236:    */       }
/* 237:250 */       if (documento != null) {
/* 238:251 */         sql.append(" AND dd.idDocumentoDigitalizado = :idDocumentoDigitalizado ");
/* 239:    */       }
/* 240:253 */       sql.append(" AND cdd.activo IS TRUE ");
/* 241:254 */       sql.append(" AND dd.activo IS TRUE ");
/* 242:    */       
/* 243:256 */       Query query = this.em.createQuery(sql.toString());
/* 244:257 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 245:258 */       if (indicadorCliente) {
/* 246:259 */         query.setParameter("subcliente", TipoEmpresaEnum.SUBCLIENTE);
/* 247:    */       }
/* 248:261 */       if (empresa != null) {
/* 249:262 */         query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/* 250:    */       }
/* 251:264 */       if (categoriaEmpresa != null) {
/* 252:265 */         query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getId()));
/* 253:    */       }
/* 254:267 */       if (fechaTopeVencimiento != null) {
/* 255:268 */         query.setParameter("fechaTopeVencimiento", fechaTopeVencimiento, TemporalType.DATE);
/* 256:    */       }
/* 257:270 */       if (documento != null) {
/* 258:271 */         query.setParameter("idDocumentoDigitalizado", Integer.valueOf(documento.getId()));
/* 259:    */       }
/* 260:273 */       List<Object[]> listaAsignados = query.getResultList();
/* 261:275 */       for (Object[] objects : listaAsignados)
/* 262:    */       {
/* 263:276 */         Integer idEmpresa = (Integer)objects[0];
/* 264:277 */         Integer idDocumento = (Integer)objects[1];
/* 265:278 */         String key = idEmpresa + "~" + idDocumento;
/* 266:    */         
/* 267:280 */         Object[] nuevoRow = new Object[9];
/* 268:281 */         nuevoRow[0] = objects[2];
/* 269:282 */         nuevoRow[1] = objects[3];
/* 270:283 */         nuevoRow[2] = ((Boolean)objects[10]);
/* 271:284 */         nuevoRow[3] = objects[4];
/* 272:285 */         nuevoRow[4] = objects[5];
/* 273:286 */         nuevoRow[5] = objects[6];
/* 274:287 */         nuevoRow[6] = objects[7];
/* 275:288 */         nuevoRow[7] = objects[8];
/* 276:289 */         nuevoRow[8] = Boolean.valueOf((String)objects[9] == null ? 0 : true);
/* 277:290 */         mapaResultado.put(key, nuevoRow);
/* 278:    */       }
/* 279:    */     }
/* 280:295 */     if (noCargados)
/* 281:    */     {
/* 282:296 */       StringBuilder sql2 = new StringBuilder();
/* 283:297 */       sql2.append(" SELECT emp.idEmpresa, dd.idDocumentoDigitalizado, cdd.nombre, dd.nombre, emp.nombreFiscal, emp.identificacion, ce.nombre, ddce.requerido ");
/* 284:298 */       sql2.append(" FROM Empresa emp ");
/* 285:299 */       sql2.append(" INNER JOIN emp.categoriaEmpresa ce ");
/* 286:300 */       sql2.append(" INNER JOIN ce.listaDocumentoDigitalizadoCategoriaEmpresa ddce ");
/* 287:301 */       sql2.append(" INNER JOIN ddce.documentoDigitalizado dd ");
/* 288:302 */       sql2.append(" INNER JOIN dd.categoriaDocumentoDigitalizado cdd ");
/* 289:303 */       sql2.append(" LEFT JOIN emp.cliente cli ");
/* 290:304 */       sql2.append(" WHERE emp.idOrganizacion = :idOrganizacion ");
/* 291:305 */       if (empresa != null) {
/* 292:306 */         sql2.append(" AND emp.idEmpresa = :idEmpresa ");
/* 293:    */       }
/* 294:308 */       if (categoriaEmpresa != null) {
/* 295:309 */         sql2.append(" AND ce.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 296:    */       }
/* 297:311 */       if (indicadorCliente)
/* 298:    */       {
/* 299:312 */         sql2.append(" AND emp.indicadorCliente IS TRUE ");
/* 300:313 */         sql2.append(" AND cli.tipoCliente != :subcliente ");
/* 301:    */       }
/* 302:315 */       if (indicadorProveedor) {
/* 303:316 */         sql2.append(" AND emp.indicadorProveedor IS TRUE ");
/* 304:    */       }
/* 305:318 */       if (documento != null) {
/* 306:319 */         sql2.append(" AND dd.idDocumentoDigitalizado = :idDocumentoDigitalizado ");
/* 307:    */       }
/* 308:321 */       sql2.append(" AND cdd.activo IS TRUE ");
/* 309:322 */       sql2.append(" AND dd.activo IS TRUE ");
/* 310:323 */       sql2.append(" AND dd.idDocumentoDigitalizado NOT IN ( ");
/* 311:324 */       sql2.append(" SELECT dd2.idDocumentoDigitalizado FROM DetalleDocumentoDigitalizado ddd INNER JOIN ddd.empresa emp2 INNER JOIN ddd.documentoDigitalizado dd2 ");
/* 312:325 */       sql2.append(" WHERE emp2.idEmpresa = emp.idEmpresa AND ddd.fichero IS NOT NULL) ");
/* 313:    */       
/* 314:327 */       Query query2 = this.em.createQuery(sql2.toString());
/* 315:328 */       query2.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 316:329 */       if (indicadorCliente) {
/* 317:330 */         query2.setParameter("subcliente", TipoEmpresaEnum.SUBCLIENTE);
/* 318:    */       }
/* 319:332 */       if (empresa != null) {
/* 320:333 */         query2.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/* 321:    */       }
/* 322:335 */       if (categoriaEmpresa != null) {
/* 323:336 */         query2.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getId()));
/* 324:    */       }
/* 325:338 */       if (documento != null) {
/* 326:339 */         query2.setParameter("idDocumentoDigitalizado", Integer.valueOf(documento.getId()));
/* 327:    */       }
/* 328:341 */       List<Object[]> listaPorCategoriaEmpresa = query2.getResultList();
/* 329:343 */       for (Object[] objects : listaPorCategoriaEmpresa)
/* 330:    */       {
/* 331:344 */         Integer idEmpresa = (Integer)objects[0];
/* 332:345 */         Integer idDocumento = (Integer)objects[1];
/* 333:346 */         String key = idEmpresa + "~" + idDocumento;
/* 334:    */         
/* 335:348 */         Object[] nuevoRow = new Object[9];
/* 336:350 */         if (!mapaResultado.containsKey(nuevoRow))
/* 337:    */         {
/* 338:351 */           nuevoRow[0] = objects[2];
/* 339:352 */           nuevoRow[1] = objects[3];
/* 340:353 */           nuevoRow[2] = ((Boolean)objects[7]);
/* 341:354 */           nuevoRow[3] = objects[4];
/* 342:355 */           nuevoRow[4] = objects[5];
/* 343:356 */           nuevoRow[5] = objects[6];
/* 344:357 */           nuevoRow[6] = null;
/* 345:358 */           nuevoRow[7] = null;
/* 346:359 */           nuevoRow[8] = Boolean.valueOf(false);
/* 347:360 */           mapaResultado.put(key, nuevoRow);
/* 348:    */         }
/* 349:    */       }
/* 350:    */     }
/* 351:365 */     List<Object[]> resultado = new ArrayList(mapaResultado.values());
/* 352:    */     
/* 353:    */ 
/* 354:368 */     Collections.sort(resultado, new Comparator()
/* 355:    */     {
/* 356:    */       public int compare(Object[] o1, Object[] o2)
/* 357:    */       {
/* 358:371 */         return ((String)o1[0] + "_" + (String)o1[1] + "_" + (String)o1[5] + "_" + (String)o1[3]).compareTo((String)o2[0] + "_" + (String)o2[1] + "_" + (String)o2[5] + "_" + (String)o2[3]);
/* 359:    */       }
/* 360:375 */     });
/* 361:376 */     return resultado;
/* 362:    */   }
/* 363:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleDocumentoDigitalizadoDao
 * JD-Core Version:    0.7.0.1
 */