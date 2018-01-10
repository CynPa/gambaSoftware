/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   4:    */ import com.asinfo.as2.entities.DetalleListaDescuentos;
/*   5:    */ import com.asinfo.as2.entities.ListaDescuentos;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.VersionListaDescuentos;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.NoResultException;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.persistence.TypedQuery;
/*  18:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  19:    */ import javax.persistence.criteria.CriteriaQuery;
/*  20:    */ import javax.persistence.criteria.Fetch;
/*  21:    */ import javax.persistence.criteria.Join;
/*  22:    */ import javax.persistence.criteria.JoinType;
/*  23:    */ import javax.persistence.criteria.Order;
/*  24:    */ import javax.persistence.criteria.Path;
/*  25:    */ import javax.persistence.criteria.Predicate;
/*  26:    */ import javax.persistence.criteria.Root;
/*  27:    */ 
/*  28:    */ @Stateless
/*  29:    */ public class ListaDescuentosDao
/*  30:    */   extends AbstractDaoAS2<ListaDescuentos>
/*  31:    */ {
/*  32:    */   public ListaDescuentosDao()
/*  33:    */   {
/*  34: 46 */     super(ListaDescuentos.class);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public DetalleListaDescuentos getDatosListaDescuentosPorProducto(ListaDescuentos listaDescuentos, Producto producto)
/*  38:    */   {
/*  39:    */     try
/*  40:    */     {
/*  41: 61 */       StringBuilder sql = new StringBuilder();
/*  42:    */       
/*  43: 63 */       sql.append(" SELECT dld FROM DetalleListaDescuentos dld ");
/*  44: 64 */       sql.append(" JOIN FETCH dld.versionListaDescuentos vld");
/*  45: 65 */       sql.append(" JOIN FETCH vld.listaDescuentos ld ");
/*  46: 66 */       sql.append(" JOIN FETCH dld.producto p ");
/*  47: 67 */       sql.append(" WHERE p.idProducto = :idProducto ");
/*  48: 68 */       sql.append(" AND ld.idListaDescuentos = :idListaDescuentos");
/*  49: 69 */       sql.append(" AND ld.activo = true");
/*  50: 70 */       sql.append(" AND vld.activo = true");
/*  51:    */       
/*  52: 72 */       Query query = this.em.createQuery(sql.toString());
/*  53:    */       
/*  54: 74 */       query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/*  55: 75 */       query.setParameter("idListaDescuentos", Integer.valueOf(listaDescuentos.getIdListaDescuentos()));
/*  56:    */       
/*  57: 77 */       return (DetalleListaDescuentos)query.getSingleResult();
/*  58:    */     }
/*  59:    */     catch (NoResultException e) {}
/*  60: 80 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public ListaDescuentos cargarDetalle(int idListaDescuentos)
/*  64:    */   {
/*  65: 92 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  66: 93 */     CriteriaQuery<ListaDescuentos> cq = cb.createQuery(ListaDescuentos.class);
/*  67:    */     
/*  68: 95 */     Root<ListaDescuentos> from = cq.from(ListaDescuentos.class);
/*  69:    */     
/*  70: 97 */     cq.where(cb.equal(from.get("idListaDescuentos"), Integer.valueOf(idListaDescuentos)));
/*  71: 98 */     ListaDescuentos listaDescuentos = (ListaDescuentos)this.em.createQuery(cq.select(from)).getSingleResult();
/*  72: 99 */     this.em.detach(listaDescuentos);
/*  73:    */     
/*  74:101 */     CriteriaQuery<VersionListaDescuentos> cqDetalle = cb.createQuery(VersionListaDescuentos.class);
/*  75:102 */     Root<VersionListaDescuentos> detalle = cqDetalle.from(VersionListaDescuentos.class);
/*  76:    */     
/*  77:104 */     cqDetalle.where(cb.equal(detalle.get("listaDescuentos"), listaDescuentos));
/*  78:105 */     cqDetalle.orderBy(new Order[] { cb.asc(detalle.get("idVersionListaDescuentos")) });
/*  79:    */     
/*  80:107 */     List<VersionListaDescuentos> listaDetalle = this.em.createQuery(cqDetalle).getResultList();
/*  81:108 */     listaDescuentos.setListaVersionesListaDescuentos(listaDetalle);
/*  82:111 */     for (VersionListaDescuentos vld : listaDetalle)
/*  83:    */     {
/*  84:113 */       int idVersionListaDescuentos = vld.getId();
/*  85:    */       
/*  86:115 */       CriteriaQuery<DetalleListaDescuentos> cqDetalleVersion = cb.createQuery(DetalleListaDescuentos.class);
/*  87:116 */       Root<DetalleListaDescuentos> fromDetalleVersion = cqDetalleVersion.from(DetalleListaDescuentos.class);
/*  88:    */       
/*  89:118 */       Fetch<Object, Object> producto = fromDetalleVersion.fetch("producto", JoinType.LEFT);
/*  90:119 */       producto.fetch("subcategoriaProducto", JoinType.LEFT).fetch("categoriaProducto", JoinType.LEFT);
/*  91:    */       
/*  92:121 */       Path<Integer> pathIdCentro = fromDetalleVersion.join("versionListaDescuentos").get("idVersionListaDescuentos");
/*  93:122 */       cqDetalleVersion.where(cb.equal(pathIdCentro, Integer.valueOf(idVersionListaDescuentos)));
/*  94:123 */       CriteriaQuery<DetalleListaDescuentos> selectCentro = cqDetalleVersion.select(fromDetalleVersion);
/*  95:    */       
/*  96:125 */       List<DetalleListaDescuentos> listaDetalleVersionListaDescuentos = this.em.createQuery(selectCentro).getResultList();
/*  97:126 */       vld.setListaDetalleListaDescuentos(listaDetalleVersionListaDescuentos);
/*  98:    */     }
/*  99:129 */     return listaDescuentos;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<Object[]> getReportelistaDescuentos(int idListaDescuentos)
/* 103:    */   {
/* 104:135 */     StringBuilder sql = new StringBuilder();
/* 105:    */     
/* 106:137 */     sql.append(" SELECT dld.porcentajeDescuentoMaximo, p.nombre, ld.codigo, ld.nombre, ld.descripcion, p.codigo  ");
/* 107:138 */     sql.append(" FROM DetalleListaDescuentos dld ");
/* 108:139 */     sql.append(" INNER JOIN dld.versionListaDescuentos vld");
/* 109:140 */     sql.append(" INNER JOIN vld.listaDescuentos ld ");
/* 110:141 */     sql.append(" INNER JOIN dld.producto p ");
/* 111:142 */     sql.append(" WHERE ld.idListaDescuentos =:idListaDescuentos ");
/* 112:    */     
/* 113:144 */     Query query = this.em.createQuery(sql.toString());
/* 114:145 */     query.setParameter("idListaDescuentos", Integer.valueOf(idListaDescuentos));
/* 115:146 */     return query.getResultList();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public List<DetalleListaDescuentos> listaCambioMasivoDescuento(List<ListaDescuentos> listaListaDescuentos, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto)
/* 119:    */   {
/* 120:153 */     StringBuilder sql = new StringBuilder();
/* 121:    */     
/* 122:155 */     sql.append(" SELECT dld ");
/* 123:156 */     sql.append(" FROM   DetalleListaDescuentos dld  ");
/* 124:157 */     sql.append(" INNER JOIN dld.versionListaDescuentos vld");
/* 125:158 */     sql.append(" INNER  JOIN  vld.listaDescuentos ld  ");
/* 126:159 */     sql.append(" INNER  JOIN  dld.producto p  ");
/* 127:160 */     sql.append(" INNER  JOIN  p.subcategoriaProducto scp  ");
/* 128:161 */     sql.append(" INNER  JOIN  scp.categoriaProducto cp  ");
/* 129:162 */     sql.append(" WHERE  ld in (:listaListaDescuentos) ");
/* 130:164 */     if (categoriaProducto != null) {
/* 131:165 */       sql.append(" AND cp =:categoriaProducto ");
/* 132:    */     }
/* 133:167 */     if (subcategoriaProducto != null) {
/* 134:168 */       sql.append(" AND scp =:subcategoriaProducto ");
/* 135:    */     }
/* 136:170 */     if (producto != null) {
/* 137:171 */       sql.append(" AND p =:producto ");
/* 138:    */     }
/* 139:174 */     Query query = this.em.createQuery(sql.toString());
/* 140:175 */     query.setParameter("listaListaDescuentos", listaListaDescuentos);
/* 141:176 */     if (categoriaProducto != null) {
/* 142:177 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 143:    */     }
/* 144:179 */     if (subcategoriaProducto != null) {
/* 145:180 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 146:    */     }
/* 147:182 */     if (producto != null) {
/* 148:183 */       query.setParameter("producto", producto);
/* 149:    */     }
/* 150:186 */     return query.getResultList();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void cambioMasivoDescuento(List<DetalleListaDescuentos> listaDetalleVersionListaDescuentos, BigDecimal porcentajeDescuento)
/* 154:    */   {
/* 155:192 */     for (DetalleListaDescuentos dldes : listaDetalleVersionListaDescuentos)
/* 156:    */     {
/* 157:193 */       StringBuilder sql1 = new StringBuilder();
/* 158:194 */       sql1.append(" UPDATE DetalleListaDescuentos dld SET dld.porcentajeDescuentoMaximo =:porcentajeDescuento ");
/* 159:195 */       sql1.append(" WHERE dld =:dldes) ");
/* 160:    */       
/* 161:197 */       Query query1 = this.em.createQuery(sql1.toString());
/* 162:198 */       query1.setParameter("dldes", dldes);
/* 163:199 */       query1.setParameter("porcentajeDescuento", porcentajeDescuento);
/* 164:200 */       query1.executeUpdate();
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public List<Producto> obtenerProductoNoAsignados(ListaDescuentos listaDescuentos, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto)
/* 169:    */   {
/* 170:210 */     StringBuilder sql = new StringBuilder();
/* 171:    */     
/* 172:212 */     sql.append(" SELECT p FROM Producto p  ");
/* 173:213 */     sql.append(" INNER JOIN p.subcategoriaProducto scp  ");
/* 174:214 */     sql.append(" INNER JOIN scp.categoriaProducto cp  ");
/* 175:215 */     sql.append(" WHERE p.indicadorVenta IS TRUE AND NOT EXISTS (SELECT 1 FROM DetalleListaDescuentos dld JOIN dld.versionListaDescuentos vld JOIN vld.listaDescuentos ld WHERE ld = :listaDescuentos AND dld.producto=p) ");
/* 176:218 */     if (categoriaProducto != null) {
/* 177:219 */       sql.append(" AND cp =:categoriaProducto ");
/* 178:    */     }
/* 179:221 */     if (subcategoriaProducto != null) {
/* 180:222 */       sql.append(" AND scp =:subcategoriaProducto ");
/* 181:    */     }
/* 182:224 */     if (producto != null) {
/* 183:225 */       sql.append(" AND p =:producto ");
/* 184:    */     }
/* 185:228 */     Query query = this.em.createQuery(sql.toString());
/* 186:229 */     query.setParameter("listaDescuentos", listaDescuentos);
/* 187:231 */     if (categoriaProducto != null) {
/* 188:232 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 189:    */     }
/* 190:234 */     if (subcategoriaProducto != null) {
/* 191:235 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 192:    */     }
/* 193:237 */     if (producto != null) {
/* 194:238 */       query.setParameter("producto", producto);
/* 195:    */     }
/* 196:241 */     return query.getResultList();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public ListaDescuentos obtenerListaDescuentosVigente(int idListaDescuentos)
/* 200:    */   {
/* 201:246 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 202:    */     
/* 203:    */ 
/* 204:249 */     CriteriaQuery<ListaDescuentos> cqCabecera = criteriaBuilder.createQuery(ListaDescuentos.class);
/* 205:250 */     Root<ListaDescuentos> fromCabecera = cqCabecera.from(ListaDescuentos.class);
/* 206:    */     
/* 207:252 */     Path<Integer> pathId = fromCabecera.get("idListaDescuentos");
/* 208:253 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idListaDescuentos)));
/* 209:254 */     CriteriaQuery<ListaDescuentos> selectListaPrecios = cqCabecera.select(fromCabecera);
/* 210:    */     
/* 211:256 */     ListaDescuentos listaDescuentos = (ListaDescuentos)this.em.createQuery(selectListaPrecios).getSingleResult();
/* 212:    */     
/* 213:    */ 
/* 214:259 */     CriteriaQuery<VersionListaDescuentos> cqDetalle = criteriaBuilder.createQuery(VersionListaDescuentos.class);
/* 215:260 */     Root<VersionListaDescuentos> fromDetalle = cqDetalle.from(VersionListaDescuentos.class);
/* 216:    */     
/* 217:262 */     Path<Integer> pathIdDetalle = fromDetalle.join("listaDescuentos").get("idListaDescuentos");
/* 218:263 */     Path<Boolean> pathActivo = fromDetalle.get("activo");
/* 219:264 */     cqDetalle.where(new Predicate[] { criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idListaDescuentos)), criteriaBuilder.equal(pathActivo, Boolean.valueOf(true)) });
/* 220:265 */     cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromDetalle.get("idVersionListaDescuentos")) });
/* 221:    */     
/* 222:267 */     CriteriaQuery<VersionListaDescuentos> selectVersionListaDescuentos = cqDetalle.select(fromDetalle);
/* 223:    */     
/* 224:269 */     List<VersionListaDescuentos> listaVersionesListaDescuentos = this.em.createQuery(selectVersionListaDescuentos).getResultList();
/* 225:270 */     listaDescuentos.setListaVersionesListaDescuentos(listaVersionesListaDescuentos);
/* 226:273 */     for (VersionListaDescuentos versionListaDescuentos : listaVersionesListaDescuentos)
/* 227:    */     {
/* 228:275 */       int idVersionListaPrecios = versionListaDescuentos.getId();
/* 229:    */       
/* 230:277 */       CriteriaQuery<DetalleListaDescuentos> cqVersionListaPrecios = criteriaBuilder.createQuery(DetalleListaDescuentos.class);
/* 231:278 */       Root<DetalleListaDescuentos> fromVersionListaPrecios = cqVersionListaPrecios.from(DetalleListaDescuentos.class);
/* 232:    */       
/* 233:280 */       fromVersionListaPrecios.fetch("producto", JoinType.LEFT);
/* 234:    */       
/* 235:282 */       Path<Integer> pathIdVersionListaPrecios = fromVersionListaPrecios.join("versionListaDescuentos").get("idVersionListaDescuentos");
/* 236:283 */       cqVersionListaPrecios.where(criteriaBuilder.equal(pathIdVersionListaPrecios, Integer.valueOf(idVersionListaPrecios)));
/* 237:284 */       CriteriaQuery<DetalleListaDescuentos> selectDetalleVersionListaDescuentos = cqVersionListaPrecios.select(fromVersionListaPrecios);
/* 238:    */       
/* 239:286 */       List<DetalleListaDescuentos> listaDetalleVersionesListaDescuentos = this.em.createQuery(selectDetalleVersionListaDescuentos).getResultList();
/* 240:287 */       versionListaDescuentos.setListaDetalleListaDescuentos(listaDetalleVersionesListaDescuentos);
/* 241:    */     }
/* 242:290 */     return listaDescuentos;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public BigDecimal getPorcentajeDescuentoMaximoVigente(ListaDescuentos listaDescuentos, Date fecha)
/* 246:    */   {
/* 247:295 */     BigDecimal saldo = BigDecimal.ZERO;
/* 248:    */     try
/* 249:    */     {
/* 250:299 */       StringBuilder sql = new StringBuilder();
/* 251:300 */       sql.append(" SELECT vld.porcentajeDescuentoMaximo ");
/* 252:301 */       sql.append(" FROM VersionListaDescuentos vld ");
/* 253:302 */       sql.append(" INNER JOIN vld.listaDescuentos ld ");
/* 254:303 */       sql.append(" WHERE ld = :listaDescuentos ");
/* 255:304 */       sql.append(" AND :fecha >= vld.validoDesde AND (:fecha <= vld.validoHasta OR vld.validoHasta IS NULL) ");
/* 256:    */       
/* 257:306 */       Query query = this.em.createQuery(sql.toString());
/* 258:307 */       query.setParameter("listaDescuentos", listaDescuentos);
/* 259:308 */       query.setParameter("fecha", fecha, TemporalType.DATE);
/* 260:    */       
/* 261:310 */       saldo = (BigDecimal)query.getSingleResult();
/* 262:    */     }
/* 263:    */     catch (NoResultException e)
/* 264:    */     {
/* 265:313 */       saldo = BigDecimal.ZERO;
/* 266:    */     }
/* 267:316 */     return saldo;
/* 268:    */   }
/* 269:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ListaDescuentosDao
 * JD-Core Version:    0.7.0.1
 */