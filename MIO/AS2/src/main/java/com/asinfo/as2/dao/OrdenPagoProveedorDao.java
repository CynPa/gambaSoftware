/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   4:    */ import com.asinfo.as2.entities.DetalleOrdenPagoProveedor;
/*   5:    */ import com.asinfo.as2.entities.Documento;
/*   6:    */ import com.asinfo.as2.entities.OrdenPagoProveedor;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ import javax.persistence.TemporalType;
/*  16:    */ import javax.persistence.TypedQuery;
/*  17:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  18:    */ import javax.persistence.criteria.CriteriaQuery;
/*  19:    */ import javax.persistence.criteria.Expression;
/*  20:    */ import javax.persistence.criteria.Fetch;
/*  21:    */ import javax.persistence.criteria.Join;
/*  22:    */ import javax.persistence.criteria.JoinType;
/*  23:    */ import javax.persistence.criteria.Path;
/*  24:    */ import javax.persistence.criteria.Predicate;
/*  25:    */ import javax.persistence.criteria.Root;
/*  26:    */ 
/*  27:    */ @Stateless
/*  28:    */ public class OrdenPagoProveedorDao
/*  29:    */   extends AbstractDaoAS2<OrdenPagoProveedor>
/*  30:    */ {
/*  31:    */   public OrdenPagoProveedorDao()
/*  32:    */   {
/*  33: 52 */     super(OrdenPagoProveedor.class);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public OrdenPagoProveedor cargarDetalle(int idOrdenPagoProveedor)
/*  37:    */   {
/*  38: 61 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  39:    */     
/*  40:    */ 
/*  41: 64 */     CriteriaQuery<OrdenPagoProveedor> cqCabecera = criteriaBuilder.createQuery(OrdenPagoProveedor.class);
/*  42: 65 */     Root<OrdenPagoProveedor> fromCabecera = cqCabecera.from(OrdenPagoProveedor.class);
/*  43:    */     
/*  44: 67 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*  45: 68 */     documento.fetch("secuencia", JoinType.LEFT);
/*  46: 69 */     fromCabecera.fetch("documentoAnticipo", JoinType.LEFT);
/*  47:    */     
/*  48: 71 */     Path<Integer> pathId = fromCabecera.get("idOrdenPagoProveedor");
/*  49: 72 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idOrdenPagoProveedor)));
/*  50: 73 */     CriteriaQuery<OrdenPagoProveedor> selectOrdenPagoProveedor = cqCabecera.select(fromCabecera);
/*  51:    */     
/*  52: 75 */     OrdenPagoProveedor ordenPagoProveedor = (OrdenPagoProveedor)this.em.createQuery(selectOrdenPagoProveedor).getSingleResult();
/*  53:    */     
/*  54:    */ 
/*  55: 78 */     CriteriaQuery<DetalleOrdenPagoProveedor> cqDetalle = criteriaBuilder.createQuery(DetalleOrdenPagoProveedor.class);
/*  56: 79 */     Root<DetalleOrdenPagoProveedor> fromDetalle = cqDetalle.from(DetalleOrdenPagoProveedor.class);
/*  57:    */     
/*  58: 81 */     fromDetalle.fetch("ordenPagoProveedor", JoinType.INNER);
/*  59: 82 */     fromDetalle.fetch("cuentaPorPagar", JoinType.LEFT).fetch("facturaProveedor", JoinType.LEFT);
/*  60: 83 */     fromDetalle.fetch("personaResponsable", JoinType.LEFT);
/*  61:    */     
/*  62: 85 */     Fetch<Object, Object> proveedor = fromDetalle.fetch("proveedor", JoinType.INNER);
/*  63: 86 */     Fetch<Object, Object> empresa = proveedor.fetch("empresa", JoinType.INNER);
/*  64: 87 */     empresa.fetch("categoriaEmpresa", JoinType.INNER);
/*  65: 88 */     proveedor.fetch("formaPago", JoinType.INNER);
/*  66: 89 */     Path<Integer> pathIdDetalle = fromDetalle.join("ordenPagoProveedor").get("idOrdenPagoProveedor");
/*  67: 90 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idOrdenPagoProveedor)));
/*  68: 91 */     CriteriaQuery<DetalleOrdenPagoProveedor> selectDetalleOrdenPago = cqDetalle.select(fromDetalle);
/*  69:    */     
/*  70: 93 */     List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedor = this.em.createQuery(selectDetalleOrdenPago).getResultList();
/*  71: 94 */     ordenPagoProveedor.setListaDetalleOrdenPagoProveedor(listaDetalleOrdenPagoProveedor);
/*  72:    */     
/*  73: 96 */     return ordenPagoProveedor;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public List<OrdenPagoProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  77:    */   {
/*  78:108 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  79:109 */     CriteriaQuery<OrdenPagoProveedor> criteriaQuery = criteriaBuilder.createQuery(OrdenPagoProveedor.class);
/*  80:110 */     Root<OrdenPagoProveedor> from = criteriaQuery.from(OrdenPagoProveedor.class);
/*  81:    */     
/*  82:112 */     from.fetch("documento", JoinType.LEFT);
/*  83:    */     
/*  84:114 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  85:115 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  86:    */     
/*  87:117 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  88:    */     
/*  89:119 */     CriteriaQuery<OrdenPagoProveedor> select = criteriaQuery.select(from);
/*  90:    */     
/*  91:121 */     TypedQuery<OrdenPagoProveedor> typedQuery = this.em.createQuery(select);
/*  92:122 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  93:    */     
/*  94:124 */     return typedQuery.getResultList();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List<Object[]> getReporteOrdenPagoProveedor(OrdenPagoProveedor ordenPagoProveedor)
/*  98:    */   {
/*  99:129 */     StringBuilder sql = new StringBuilder();
/* 100:130 */     sql.append(" SELECT cat.nombre, emp.nombreFiscal, fp.numero, CONCAT(fpSRI.establecimiento, '-', fpSRI.puntoEmision, '-', fpSRI.numero), COALESCE(fp.descripcion, dopp.descripcion), fp.fecha, cxp.fechaVencimiento, dopp.valor, dopp.valorAprobado, dopp.diasVencidos, CONCAT(pr.apellidos,' ',pr.nombres), CONCAT(prf.apellidos,' ',prf.nombres), cp.diasPlazo ");
/* 101:131 */     sql.append(" FROM DetalleOrdenPagoProveedor dopp  ");
/* 102:132 */     sql.append(" LEFT  JOIN dopp.personaResponsable pr  ");
/* 103:133 */     sql.append(" INNER JOIN dopp.ordenPagoProveedor opp  ");
/* 104:134 */     sql.append(" INNER JOIN dopp.proveedor prov  ");
/* 105:135 */     sql.append(" INNER JOIN prov.empresa emp  ");
/* 106:136 */     sql.append(" INNER JOIN emp.categoriaEmpresa cat  ");
/* 107:137 */     sql.append(" LEFT JOIN dopp.cuentaPorPagar cxp  ");
/* 108:138 */     sql.append(" LEFT JOIN cxp.facturaProveedor fp  ");
/* 109:139 */     sql.append(" LEFT JOIN fp.personaResponsable prf  ");
/* 110:140 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fpSRI  ");
/* 111:141 */     sql.append(" LEFT JOIN fp.condicionPago cp  ");
/* 112:142 */     sql.append(" WHERE opp.idOrdenPagoProveedor = :idOrdenPagoProveedor ");
/* 113:143 */     sql.append(" AND (dopp.valor > 0 OR dopp.valorAprobado > 0) ");
/* 114:144 */     sql.append(" ORDER BY cat.nombre, emp.nombreFiscal ");
/* 115:    */     
/* 116:146 */     Query query = this.em.createQuery(sql.toString());
/* 117:147 */     query.setParameter("idOrdenPagoProveedor", Integer.valueOf(ordenPagoProveedor.getId()));
/* 118:148 */     return query.getResultList();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<DetalleOrdenPagoProveedor> buscarDetallesPendientesPago(int idOrganizacion, Boolean indicadorPagoCash, Date fechaPago, Integer idSucursal, Integer idEmpresa, TipoServicioCuentaBancariaEnum tipoServicio, CategoriaEmpresa categoriaEmpresa, Documento documentoFiltro)
/* 122:    */   {
/* 123:155 */     StringBuilder sql = new StringBuilder();
/* 124:156 */     sql.append(" SELECT dopp ");
/* 125:157 */     sql.append(" FROM DetalleOrdenPagoProveedor dopp  ");
/* 126:158 */     sql.append(" INNER JOIN FETCH dopp.ordenPagoProveedor opp  ");
/* 127:    */     
/* 128:160 */     sql.append(" INNER JOIN FETCH dopp.proveedor prov  ");
/* 129:161 */     sql.append(" INNER JOIN FETCH prov.formaPago  ");
/* 130:162 */     sql.append(" INNER JOIN FETCH prov.empresa emp  ");
/* 131:163 */     sql.append(" INNER JOIN FETCH emp.categoriaEmpresa cat  ");
/* 132:164 */     sql.append(" LEFT JOIN FETCH dopp.personaResponsable pr  ");
/* 133:165 */     sql.append(" LEFT JOIN FETCH dopp.cuentaPorPagar cxp  ");
/* 134:166 */     sql.append(" LEFT JOIN FETCH cxp.facturaProveedor fp  ");
/* 135:167 */     sql.append(" LEFT JOIN FETCH fp.facturaProveedorSRI fpSRI  ");
/* 136:168 */     sql.append(" LEFT JOIN FETCH fp.empresa empFact ");
/* 137:169 */     sql.append(" LEFT JOIN FETCH fp.documento docFac  ");
/* 138:170 */     sql.append(" LEFT JOIN FETCH opp.documentoAnticipo docAn  ");
/* 139:171 */     sql.append(" LEFT JOIN FETCH docAn.tipoAsiento ");
/* 140:172 */     sql.append(" WHERE opp.idOrganizacion = :idOrganizacion ");
/* 141:173 */     sql.append(" AND dopp.valorAprobado > 0 ");
/* 142:174 */     sql.append(" AND CASE WHEN cxp IS NOT NULL THEN cxp.saldo ElSE 1 END > 0");
/* 143:175 */     sql.append(" AND dopp.indicadorPagado = false ");
/* 144:176 */     sql.append(" AND opp.estado =:estadoAprobado");
/* 145:177 */     if (indicadorPagoCash != null) {
/* 146:178 */       sql.append(" AND prov.indicadorPagoCash = :indicadorPagoCash ");
/* 147:    */     }
/* 148:180 */     if ((idSucursal != null) && (!idSucursal.equals(Integer.valueOf(0)))) {
/* 149:181 */       sql.append(" AND fp.sucursal.idSucursal = :idSucursal ");
/* 150:    */     }
/* 151:184 */     if (idEmpresa != null) {
/* 152:185 */       sql.append(" AND empFact.idEmpresa = :idEmpresa ");
/* 153:    */     }
/* 154:187 */     if (categoriaEmpresa != null) {
/* 155:188 */       sql.append(" AND cat.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 156:    */     }
/* 157:190 */     if (fechaPago != null) {
/* 158:191 */       sql.append(" AND opp.fechaCorte <= :fechaPago ");
/* 159:    */     }
/* 160:193 */     if (documentoFiltro != null) {
/* 161:194 */       sql.append(" AND docFac = :documentoFiltro ");
/* 162:    */     }
/* 163:196 */     if (tipoServicio != null)
/* 164:    */     {
/* 165:197 */       sql.append(" AND EXISTS (");
/* 166:198 */       sql.append("               SELECT 1 FROM  CuentaBancariaEmpresa cbem ");
/* 167:199 */       sql.append("               JOIN cbem.cuentaBancaria cb ");
/* 168:200 */       sql.append("               JOIN cbem.empresa em2 ");
/* 169:201 */       sql.append("               WHERE cb.tipoServicioCuentaBancariaProveedor =:tipoServicio  ");
/* 170:202 */       sql.append("               AND   emp = em2 ");
/* 171:203 */       sql.append("             ) ");
/* 172:    */     }
/* 173:205 */     sql.append(" ORDER BY emp.nombreFiscal ");
/* 174:    */     
/* 175:207 */     Query query = this.em.createQuery(sql.toString());
/* 176:208 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 177:209 */     query.setParameter("estadoAprobado", Estado.APROBADO);
/* 178:210 */     if (indicadorPagoCash != null) {
/* 179:211 */       query.setParameter("indicadorPagoCash", indicadorPagoCash);
/* 180:    */     }
/* 181:213 */     if ((idSucursal != null) && (!idSucursal.equals(Integer.valueOf(0)))) {
/* 182:214 */       query.setParameter("idSucursal", idSucursal);
/* 183:    */     }
/* 184:216 */     if (idEmpresa != null) {
/* 185:217 */       query.setParameter("idEmpresa", idEmpresa);
/* 186:    */     }
/* 187:219 */     if (fechaPago != null) {
/* 188:220 */       query.setParameter("fechaPago", fechaPago, TemporalType.DATE);
/* 189:    */     }
/* 190:222 */     if (documentoFiltro != null) {
/* 191:223 */       query.setParameter("documentoFiltro", documentoFiltro);
/* 192:    */     }
/* 193:225 */     if (categoriaEmpresa != null) {
/* 194:226 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getId()));
/* 195:    */     }
/* 196:228 */     if (tipoServicio != null) {
/* 197:229 */       query.setParameter("tipoServicio", tipoServicio);
/* 198:    */     }
/* 199:232 */     return query.getResultList();
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<Integer> buscarOrdenPagoProveedorPorNumeroDeFactura(int idOrganizacion, String numeroFactura)
/* 203:    */   {
/* 204:238 */     StringBuilder sql = new StringBuilder();
/* 205:239 */     sql.append("SELECT opp.idOrdenPagoProveedor");
/* 206:240 */     sql.append(" FROM DetalleOrdenPagoProveedor dopp");
/* 207:241 */     sql.append(" INNER JOIN dopp.ordenPagoProveedor opp");
/* 208:242 */     sql.append(" INNER JOIN dopp.cuentaPorPagar cxp");
/* 209:243 */     sql.append(" INNER JOIN cxp.facturaProveedor fp");
/* 210:244 */     sql.append(" INNER JOIN fp.facturaProveedorSRI fpSRI");
/* 211:245 */     sql.append(" WHERE opp.idOrganizacion = :idOrganizacion");
/* 212:246 */     sql.append(" AND COALESCE(fpSRI.numero, fp.referencia3) LIKE :numeroFactura");
/* 213:    */     
/* 214:248 */     Query query = this.em.createQuery(sql.toString());
/* 215:249 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 216:250 */     query.setParameter("numeroFactura", "%" + numeroFactura + "%");
/* 217:251 */     return query.getResultList();
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void cerrarOrdenPagoProveedor(Integer idOrdenPagoProveedor, String nombreUsuario)
/* 221:    */   {
/* 222:255 */     StringBuilder sqlUpdate = new StringBuilder();
/* 223:256 */     sqlUpdate.append("UPDATE OrdenPagoProveedor opp");
/* 224:257 */     sqlUpdate.append(" SET opp.estado = :estado,");
/* 225:258 */     sqlUpdate.append(" opp.usuarioCierre = :nombreUsuario,");
/* 226:259 */     sqlUpdate.append(" opp.fechaCierre = :fechaCierre");
/* 227:260 */     sqlUpdate.append(" WHERE opp.idOrdenPagoProveedor = :idOrdenPagoProveedor");
/* 228:261 */     sqlUpdate.append(" AND NOT EXISTS (");
/* 229:262 */     sqlUpdate.append("\t\tSELECT 1");
/* 230:263 */     sqlUpdate.append(" \t\tFROM DetalleOrdenPagoProveedor d");
/* 231:264 */     sqlUpdate.append(" \t\tINNER JOIN d.ordenPagoProveedor o");
/* 232:265 */     sqlUpdate.append(" \t\tWHERE o.idOrdenPagoProveedor = :idOrdenPagoProveedor");
/* 233:266 */     sqlUpdate.append(" \t\tAND d.indicadorPagado = false");
/* 234:267 */     sqlUpdate.append(")");
/* 235:268 */     Query queryUpdate = this.em.createQuery(sqlUpdate.toString());
/* 236:269 */     queryUpdate.setParameter("idOrdenPagoProveedor", idOrdenPagoProveedor);
/* 237:270 */     queryUpdate.setParameter("estado", Estado.CERRADO);
/* 238:271 */     queryUpdate.setParameter("nombreUsuario", nombreUsuario);
/* 239:272 */     queryUpdate.setParameter("fechaCierre", new Date(), TemporalType.TIMESTAMP);
/* 240:273 */     queryUpdate.executeUpdate();
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void desbloquearCuentaPorPagarSegunOrden(int idDetalleOrdenPagoProveedor)
/* 244:    */   {
/* 245:283 */     StringBuilder sql = new StringBuilder();
/* 246:284 */     sql.append("UPDATE CuentaPorPagar c");
/* 247:285 */     sql.append(" SET c.indicadorEnOrdenPagoProveedor=true");
/* 248:286 */     sql.append(" WHERE EXISTS(");
/* 249:287 */     sql.append("\t\tSELECT 1 FROM DetalleOrdenPagoProveedor d");
/* 250:288 */     sql.append("\t\tINNER JOIN d.ordenPagoProveedor opp");
/* 251:289 */     sql.append("\t\tINNER JOIN d.cuentaPorPagar cpp");
/* 252:290 */     sql.append("\t\tWHERE cpp.idCuentaPorPagar=c.idCuentaPorPagar");
/* 253:291 */     sql.append(" \t\tAND d.idDetalleOrdenPagoProveedor=:idDetalleOrdenPagoProveedor");
/* 254:292 */     sql.append(")");
/* 255:293 */     Query query = this.em.createQuery(sql.toString());
/* 256:294 */     query.setParameter("idDetalleOrdenPagoProveedor", Integer.valueOf(idDetalleOrdenPagoProveedor));
/* 257:295 */     query.executeUpdate();
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void abrirOrden(int idOrdenPagoProveedor)
/* 261:    */   {
/* 262:304 */     StringBuilder sql = new StringBuilder();
/* 263:305 */     sql.append("UPDATE OrdenPagoProveedor opp");
/* 264:306 */     sql.append(" SET opp.estado = :estadoAprobado");
/* 265:307 */     sql.append(" WHERE opp.idOrdenPagoProveedor = :idOrdenPagoProveedor");
/* 266:308 */     Query query = this.em.createQuery(sql.toString());
/* 267:309 */     query.setParameter("idOrdenPagoProveedor", Integer.valueOf(idOrdenPagoProveedor));
/* 268:310 */     query.setParameter("estadoAprobado", Estado.APROBADO);
/* 269:311 */     query.executeUpdate();
/* 270:    */   }
/* 271:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.OrdenPagoProveedorDao
 * JD-Core Version:    0.7.0.1
 */