/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   4:    */ import com.asinfo.as2.entities.ListaPrecios;
/*   5:    */ import com.asinfo.as2.entities.Moneda;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.VersionListaPrecios;
/*   8:    */ import com.asinfo.as2.entities.Zona;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import java.math.BigDecimal;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.ejb.Stateless;
/*  17:    */ import javax.persistence.EntityManager;
/*  18:    */ import javax.persistence.NoResultException;
/*  19:    */ import javax.persistence.Query;
/*  20:    */ import javax.persistence.TemporalType;
/*  21:    */ import javax.persistence.TypedQuery;
/*  22:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  23:    */ import javax.persistence.criteria.CriteriaQuery;
/*  24:    */ import javax.persistence.criteria.Join;
/*  25:    */ import javax.persistence.criteria.JoinType;
/*  26:    */ import javax.persistence.criteria.Order;
/*  27:    */ import javax.persistence.criteria.Path;
/*  28:    */ import javax.persistence.criteria.Predicate;
/*  29:    */ import javax.persistence.criteria.Root;
/*  30:    */ 
/*  31:    */ @Stateless
/*  32:    */ public class ListaPreciosDao
/*  33:    */   extends AbstractDaoAS2<ListaPrecios>
/*  34:    */ {
/*  35:    */   @EJB
/*  36:    */   private ProductoDao productoDao;
/*  37:    */   @EJB
/*  38:    */   private DetalleVersionListaPreciosDao detalleVersionListaPreciosDao;
/*  39:    */   @EJB
/*  40:    */   private VersionListaPreciosDao versionListaPreciosDao;
/*  41:    */   
/*  42:    */   public ListaPreciosDao()
/*  43:    */   {
/*  44: 53 */     super(ListaPrecios.class);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public DetalleVersionListaPrecios getDatosVersionListaPrecios(int idListaPrecios, int idProducto, Date fecha, Integer idZona, String numeroFactura, boolean verificarZona)
/*  48:    */     throws ExcepcionAS2
/*  49:    */   {
/*  50: 67 */     String sql = "";
/*  51:    */     
/*  52: 69 */     Query query = null;
/*  53:    */     try
/*  54:    */     {
/*  55: 71 */       sql = " SELECT dvlp FROM DetalleVersionListaPrecios dvlp  JOIN FETCH dvlp.versionListaPrecios vlp  JOIN FETCH vlp.listaPrecios lp  LEFT JOIN vlp.zona z  WHERE dvlp.producto.idProducto = :idProducto  AND :fecha between vlp.validoDesde and CASE WHEN vlp.validoHasta IS NULL THEN :fecha ELSE vlp.validoHasta END  AND lp.idListaPrecios = :idListaPrecios AND vlp.activo = true AND lp.activo =  true";
/*  56: 77 */       if (verificarZona) {
/*  57: 78 */         if (idZona == null) {
/*  58: 79 */           sql = sql + " AND z IS NULL";
/*  59:    */         } else {
/*  60: 81 */           sql = sql + " AND z.idZona = :idZona";
/*  61:    */         }
/*  62:    */       }
/*  63: 86 */       query = this.em.createQuery(sql);
/*  64:    */       
/*  65: 88 */       query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  66: 89 */       query.setParameter("fecha", fecha);
/*  67: 90 */       query.setParameter("idListaPrecios", Integer.valueOf(idListaPrecios));
/*  68: 92 */       if (idZona != null) {
/*  69: 93 */         query.setParameter("idZona", idZona);
/*  70:    */       }
/*  71: 96 */       return (DetalleVersionListaPrecios)query.getSingleResult();
/*  72:    */     }
/*  73:    */     catch (NoResultException e)
/*  74:    */     {
/*  75: 99 */       Producto producto = (Producto)this.productoDao.buscarPorId(Integer.valueOf(idProducto));
/*  76:100 */       String strMensaje = "";
/*  77:101 */       if (producto != null)
/*  78:    */       {
/*  79:102 */         String numero = " (Factura: " + numeroFactura + " )";
/*  80:103 */         strMensaje = " " + producto.getCodigo() + " " + producto.getNombre() + " (" + fecha + ")" + numero;
/*  81:    */       }
/*  82:105 */       throw new ExcepcionAS2("msg_error_producto_no_lista_precios", strMensaje);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public ListaPrecios cargarDetalle(int idListaPrecios)
/*  87:    */   {
/*  88:117 */     StringBuilder sql = new StringBuilder();
/*  89:118 */     sql.append(" SELECT dvlp FROM DetalleVersionListaPrecios dvlp ");
/*  90:119 */     sql.append(" JOIN FETCH dvlp.producto p ");
/*  91:120 */     sql.append(" JOIN FETCH p.unidad u ");
/*  92:121 */     sql.append(" JOIN FETCH p.subcategoriaProducto scp ");
/*  93:122 */     sql.append(" JOIN FETCH scp.categoriaProducto cp");
/*  94:123 */     sql.append(" JOIN FETCH dvlp.versionListaPrecios v ");
/*  95:124 */     sql.append(" JOIN FETCH v.listaPrecios l ");
/*  96:125 */     sql.append(" JOIN FETCH l.moneda m ");
/*  97:126 */     sql.append(" LEFT JOIN FETCH v.zona z ");
/*  98:127 */     sql.append(" WHERE l.idListaPrecios = :idListaPrecios ");
/*  99:    */     
/* 100:129 */     Query query = this.em.createQuery(sql.toString());
/* 101:130 */     query.setParameter("idListaPrecios", Integer.valueOf(idListaPrecios));
/* 102:    */     
/* 103:132 */     List<DetalleVersionListaPrecios> lista = query.getResultList();
/* 104:    */     
/* 105:134 */     HashMap<Integer, VersionListaPrecios> hmVersionListaPrecios = new HashMap();
/* 106:135 */     ListaPrecios listaPrecios = null;
/* 107:136 */     for (DetalleVersionListaPrecios dvlp : lista)
/* 108:    */     {
/* 109:137 */       this.detalleVersionListaPreciosDao.detach(dvlp);
/* 110:138 */       VersionListaPrecios v = dvlp.getVersionListaPrecios();
/* 111:139 */       if (!hmVersionListaPrecios.containsKey(Integer.valueOf(v.getIdVersionListaPrecios())))
/* 112:    */       {
/* 113:140 */         if (listaPrecios == null)
/* 114:    */         {
/* 115:141 */           listaPrecios = v.getListaPrecios();
/* 116:142 */           detach(listaPrecios);
/* 117:143 */           listaPrecios.setVersionesListaPrecios(new ArrayList());
/* 118:    */         }
/* 119:145 */         this.versionListaPreciosDao.detach(v);
/* 120:146 */         v.setDetalleVersionesListaPrecios(new ArrayList());
/* 121:147 */         hmVersionListaPrecios.put(Integer.valueOf(dvlp.getVersionListaPrecios().getIdVersionListaPrecios()), dvlp.getVersionListaPrecios());
/* 122:148 */         listaPrecios.getVersionesListaPrecios().add(v);
/* 123:    */       }
/* 124:150 */       v = (VersionListaPrecios)hmVersionListaPrecios.get(Integer.valueOf(v.getIdVersionListaPrecios()));
/* 125:151 */       dvlp.setVersionListaPrecios(v);
/* 126:152 */       v.getDetalleVersionesListaPrecios().add(dvlp);
/* 127:    */     }
/* 128:155 */     if (listaPrecios == null)
/* 129:    */     {
/* 130:156 */       listaPrecios = (ListaPrecios)buscarPorId(Integer.valueOf(idListaPrecios));
/* 131:157 */       if (listaPrecios.getMoneda() != null) {
/* 132:158 */         listaPrecios.getMoneda().getId();
/* 133:    */       }
/* 134:160 */       listaPrecios.getVersionesListaPrecios().size();
/* 135:161 */       for (VersionListaPrecios versionListaPrecios : listaPrecios.getVersionesListaPrecios())
/* 136:    */       {
/* 137:162 */         versionListaPrecios.getDetalleVersionesListaPrecios().size();
/* 138:163 */         if (versionListaPrecios.getZona() != null) {
/* 139:164 */           versionListaPrecios.getZona().getId();
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:169 */     return listaPrecios;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public ListaPrecios obtenerListaPreciosVigente(int idListaPrecios)
/* 147:    */   {
/* 148:173 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 149:    */     
/* 150:    */ 
/* 151:176 */     CriteriaQuery<ListaPrecios> cqCabecera = criteriaBuilder.createQuery(ListaPrecios.class);
/* 152:177 */     Root<ListaPrecios> fromCabecera = cqCabecera.from(ListaPrecios.class);
/* 153:    */     
/* 154:179 */     Path<Integer> pathId = fromCabecera.get("idListaPrecios");
/* 155:180 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idListaPrecios)));
/* 156:181 */     CriteriaQuery<ListaPrecios> selectListaPrecios = cqCabecera.select(fromCabecera);
/* 157:    */     
/* 158:183 */     ListaPrecios listaPrecios = (ListaPrecios)this.em.createQuery(selectListaPrecios).getSingleResult();
/* 159:    */     
/* 160:    */ 
/* 161:186 */     CriteriaQuery<VersionListaPrecios> cqDetalle = criteriaBuilder.createQuery(VersionListaPrecios.class);
/* 162:187 */     Root<VersionListaPrecios> fromDetalle = cqDetalle.from(VersionListaPrecios.class);
/* 163:    */     
/* 164:189 */     fromDetalle.fetch("zona", JoinType.LEFT);
/* 165:    */     
/* 166:191 */     Path<Integer> pathIdDetalle = fromDetalle.join("listaPrecios").get("idListaPrecios");
/* 167:192 */     Path<Boolean> pathActivo = fromDetalle.get("activo");
/* 168:193 */     cqDetalle.where(new Predicate[] { criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idListaPrecios)), criteriaBuilder.equal(pathActivo, Boolean.valueOf(true)) });
/* 169:194 */     cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromDetalle.get("idVersionListaPrecios")) });
/* 170:    */     
/* 171:196 */     CriteriaQuery<VersionListaPrecios> selectVersionListaPrecios = cqDetalle.select(fromDetalle);
/* 172:    */     
/* 173:198 */     List<VersionListaPrecios> listaVersionListaPrecios = this.em.createQuery(selectVersionListaPrecios).getResultList();
/* 174:199 */     listaPrecios.setVersionesListaPrecios(listaVersionListaPrecios);
/* 175:202 */     for (VersionListaPrecios versionListaPrecios : listaVersionListaPrecios)
/* 176:    */     {
/* 177:204 */       int idVersionListaPrecios = versionListaPrecios.getId();
/* 178:    */       
/* 179:206 */       CriteriaQuery<DetalleVersionListaPrecios> cqVersionListaPrecios = criteriaBuilder.createQuery(DetalleVersionListaPrecios.class);
/* 180:207 */       Root<DetalleVersionListaPrecios> fromVersionListaPrecios = cqVersionListaPrecios.from(DetalleVersionListaPrecios.class);
/* 181:    */       
/* 182:209 */       fromVersionListaPrecios.fetch("producto", JoinType.LEFT);
/* 183:    */       
/* 184:211 */       Path<Integer> pathIdVersionListaPrecios = fromVersionListaPrecios.join("versionListaPrecios").get("idVersionListaPrecios");
/* 185:212 */       cqVersionListaPrecios.where(criteriaBuilder.equal(pathIdVersionListaPrecios, Integer.valueOf(idVersionListaPrecios)));
/* 186:213 */       CriteriaQuery<DetalleVersionListaPrecios> selectDetalleVersionListaPrecios = cqVersionListaPrecios.select(fromVersionListaPrecios);
/* 187:    */       
/* 188:215 */       List<DetalleVersionListaPrecios> listaDetalleVersionListaPrecios = this.em.createQuery(selectDetalleVersionListaPrecios).getResultList();
/* 189:216 */       versionListaPrecios.setDetalleVersionesListaPrecios(listaDetalleVersionListaPrecios);
/* 190:    */     }
/* 191:219 */     return listaPrecios;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<VersionListaPrecios> getZonaListaPreciosProductoNuevo(int idOrganizacion, int idListaPrecios, int idProducto)
/* 195:    */   {
/* 196:224 */     StringBuilder sql = new StringBuilder();
/* 197:225 */     sql.append(" SELECT v FROM VersionListaPrecios v ");
/* 198:226 */     sql.append(" JOIN FETCH v.zona z JOIN FETCH v.listaPrecios l");
/* 199:227 */     sql.append(" WHERE z.idOrganizacion = :idOrganizacion AND l.idListaPrecios = :idListaPrecios AND v.activo = true AND NOT EXISTS  ");
/* 200:228 */     sql.append(" \t( ");
/* 201:229 */     sql.append(" \t\tSELECT zc  ");
/* 202:230 */     sql.append(" \t\tFROM DetalleVersionListaPrecios d  ");
/* 203:231 */     sql.append(" \t\tinner join d.producto p  ");
/* 204:232 */     sql.append(" \t\tinner join d.versionListaPrecios vc  ");
/* 205:233 */     sql.append(" \t\tinner join vc.zona zc  ");
/* 206:234 */     sql.append(" \t\tinner join vc.listaPrecios lc ");
/* 207:235 */     sql.append(" \t\tWHERE zc = z AND p.idProducto = :idProducto ");
/* 208:236 */     sql.append(" \t\tAND lc.idListaPrecios = :idListaPrecios");
/* 209:237 */     sql.append(" \t)");
/* 210:    */     
/* 211:239 */     Query query = this.em.createQuery(sql.toString());
/* 212:240 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 213:241 */     query.setParameter("idListaPrecios", Integer.valueOf(idListaPrecios));
/* 214:242 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/* 215:243 */     return query.getResultList();
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List getReporteListaPreciosUltimaVersion(int idVersionListaPrecios)
/* 219:    */   {
/* 220:247 */     String sql = "";
/* 221:248 */     Query query = null;
/* 222:249 */     sql = " SELECT p.codigo,p.nombre,p.precioReferencialCompra,dvlp.precioUnitario,dvlp.precioUnitarioClienteFinal FROM DetalleVersionListaPrecios dvlp  INNER JOIN dvlp.versionListaPrecios vlp  LEFT JOIN dvlp.producto p  WHERE vlp.idVersionListaPrecios = :idVersionListaPrecios ";
/* 223:    */     
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:254 */     query = this.em.createQuery(sql);
/* 228:    */     
/* 229:256 */     query.setParameter("idVersionListaPrecios", Integer.valueOf(idVersionListaPrecios));
/* 230:    */     
/* 231:258 */     return query.getResultList();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public VersionListaPrecios getUltimaVersionListaPrecios(int idListaPrecios)
/* 235:    */   {
/* 236:264 */     Query query = null;
/* 237:265 */     String sql = " SELECT vlp FROM VersionListaPrecios vlp  INNER JOIN vlp.listaPrecios lp  WHERE lp.idListaPrecios = :idListaPrecios ORDER BY vlp.activo desc,coalesce(vlp.validoHasta,'30/12/9999') desc";
/* 238:    */     
/* 239:    */ 
/* 240:268 */     query = this.em.createQuery(sql);
/* 241:269 */     query.setParameter("idListaPrecios", Integer.valueOf(idListaPrecios));
/* 242:    */     
/* 243:271 */     List<VersionListaPrecios> listaPrecios = query.getResultList();
/* 244:273 */     if (listaPrecios.size() > 0) {
/* 245:274 */       return (VersionListaPrecios)listaPrecios.get(0);
/* 246:    */     }
/* 247:276 */     return null;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public List<VersionListaPrecios> getListaVersionListaPrecios(ListaPrecios listaPrecios)
/* 251:    */   {
/* 252:285 */     String sql = " SELECT vlp FROM VersionListaPrecios vlp INNER JOIN vlp.listaPrecios WHERE vlp.listaPrecios = :listaPrecios ";
/* 253:    */     
/* 254:287 */     Query query = this.em.createQuery(sql);
/* 255:288 */     query.setParameter("listaPrecios", listaPrecios);
/* 256:    */     
/* 257:290 */     List<VersionListaPrecios> lista = query.getResultList();
/* 258:    */     
/* 259:292 */     return lista;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public BigDecimal precioProducto(VersionListaPrecios versionListaPrecios, String nombreProducto)
/* 263:    */   {
/* 264:300 */     BigDecimal precio = BigDecimal.ZERO;
/* 265:301 */     StringBuilder sql = new StringBuilder();
/* 266:    */     
/* 267:303 */     sql.append(" SELECT dvlp FROM DetalleVersionListaPrecios dvlp ");
/* 268:304 */     sql.append(" LEFT JOIN dvlp.producto p ");
/* 269:305 */     sql.append(" WHERE dvlp.versionListaPrecios = :versionListaPrecios ");
/* 270:306 */     sql.append(" AND p.nombre = :nombreProducto ");
/* 271:    */     
/* 272:308 */     Query query = this.em.createQuery(sql.toString());
/* 273:309 */     query.setParameter("versionListaPrecios", versionListaPrecios);
/* 274:310 */     query.setParameter("nombreProducto", nombreProducto);
/* 275:    */     
/* 276:312 */     List<DetalleVersionListaPrecios> lista = query.getResultList();
/* 277:314 */     if (lista.size() > 0) {
/* 278:315 */       precio = ((DetalleVersionListaPrecios)lista.get(0)).getPrecioUnitario();
/* 279:    */     }
/* 280:318 */     return precio;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public List<DetalleVersionListaPrecios> obtenerListaDetalleVersionListaPrecios(Date fecha, List<Integer> listaIdListaPrecio)
/* 284:    */   {
/* 285:325 */     StringBuilder sql = new StringBuilder();
/* 286:    */     
/* 287:327 */     sql.append(" SELECT dvlp ");
/* 288:328 */     sql.append(" FROM DetalleVersionListaPrecios dvlp ");
/* 289:329 */     sql.append(" INNER JOIN FETCH dvlp.producto prod ");
/* 290:330 */     sql.append(" JOIN FETCH dvlp.versionListaPrecios vlp ");
/* 291:331 */     sql.append(" JOIN FETCH vlp.listaPrecios lp ");
/* 292:332 */     sql.append(" LEFT JOIN vlp.zona z ");
/* 293:    */     
/* 294:334 */     sql.append(" LEFT JOIN FETCH prod.subcategoriaProducto scp ");
/* 295:335 */     sql.append(" LEFT JOIN FETCH scp.categoriaProducto cp ");
/* 296:336 */     sql.append(" LEFT JOIN FETCH prod.unidad uni ");
/* 297:337 */     sql.append(" LEFT JOIN FETCH prod.unidadVenta univ ");
/* 298:338 */     sql.append(" LEFT JOIN FETCH prod.presentacionProducto pp ");
/* 299:339 */     sql.append(" LEFT JOIN FETCH prod.marcaProducto mp ");
/* 300:340 */     sql.append(" LEFT JOIN FETCH prod.categoriaImpuesto ci ");
/* 301:    */     
/* 302:342 */     sql.append(" WHERE :fecha between vlp.validoDesde and CASE WHEN vlp.validoHasta IS NULL THEN :fecha ELSE vlp.validoHasta END ");
/* 303:343 */     sql.append(" AND lp.idListaPrecios IN :listaIdListaPrecio ");
/* 304:344 */     sql.append(" AND vlp.activo = true ");
/* 305:345 */     sql.append(" AND lp.activo =  true ");
/* 306:346 */     sql.append(" AND prod.activo =  true ");
/* 307:    */     
/* 308:348 */     Query query = this.em.createQuery(sql.toString());
/* 309:    */     
/* 310:350 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 311:351 */     query.setParameter("listaIdListaPrecio", listaIdListaPrecio);
/* 312:    */     
/* 313:353 */     return query.getResultList();
/* 314:    */   }
/* 315:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ListaPreciosDao
 * JD-Core Version:    0.7.0.1
 */