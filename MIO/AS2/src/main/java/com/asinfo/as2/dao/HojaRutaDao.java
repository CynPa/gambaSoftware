/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Cliente;
/*   4:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   5:    */ import com.asinfo.as2.entities.DetalleHojaRuta;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.HojaRuta;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.Transportista;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.ejb.Stateless;
/*  16:    */ import javax.persistence.EntityManager;
/*  17:    */ import javax.persistence.Query;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.persistence.TypedQuery;
/*  20:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  21:    */ import javax.persistence.criteria.CriteriaQuery;
/*  22:    */ import javax.persistence.criteria.Expression;
/*  23:    */ import javax.persistence.criteria.Fetch;
/*  24:    */ import javax.persistence.criteria.Join;
/*  25:    */ import javax.persistence.criteria.JoinType;
/*  26:    */ import javax.persistence.criteria.Path;
/*  27:    */ import javax.persistence.criteria.Predicate;
/*  28:    */ import javax.persistence.criteria.Root;
/*  29:    */ 
/*  30:    */ @Stateless
/*  31:    */ public class HojaRutaDao
/*  32:    */   extends AbstractDaoAS2<HojaRuta>
/*  33:    */ {
/*  34:    */   public HojaRutaDao()
/*  35:    */   {
/*  36: 49 */     super(HojaRuta.class);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public HojaRuta cargarDetalle(int idHojaRuta)
/*  40:    */   {
/*  41: 54 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  42:    */     
/*  43:    */ 
/*  44: 57 */     CriteriaQuery<HojaRuta> cqCabecera = criteriaBuilder.createQuery(HojaRuta.class);
/*  45: 58 */     Root<HojaRuta> fromCabecera = cqCabecera.from(HojaRuta.class);
/*  46:    */     
/*  47: 60 */     fromCabecera.fetch("documento", JoinType.LEFT).fetch("secuencia", JoinType.LEFT);
/*  48: 61 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  49: 62 */     fromCabecera.fetch("transportista", JoinType.LEFT);
/*  50: 63 */     Path<Integer> pathId = fromCabecera.get("idHojaRuta");
/*  51: 64 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idHojaRuta)));
/*  52: 65 */     CriteriaQuery<HojaRuta> selectDespacho = cqCabecera.select(fromCabecera);
/*  53:    */     
/*  54: 67 */     HojaRuta hojaRuta = (HojaRuta)this.em.createQuery(selectDespacho).getSingleResult();
/*  55:    */     
/*  56:    */ 
/*  57: 70 */     CriteriaQuery<DetalleHojaRuta> cqDetalle = criteriaBuilder.createQuery(DetalleHojaRuta.class);
/*  58: 71 */     Root<DetalleHojaRuta> fromDetalle = cqDetalle.from(DetalleHojaRuta.class);
/*  59:    */     
/*  60: 73 */     fromDetalle.fetch("hojaRuta", JoinType.LEFT);
/*  61: 74 */     Fetch<Object, Object> despachoCliente = fromDetalle.fetch("despachoCliente", JoinType.LEFT);
/*  62: 75 */     despachoCliente.fetch("transportista", JoinType.LEFT);
/*  63: 76 */     despachoCliente.fetch("guiaRemision", JoinType.LEFT);
/*  64:    */     
/*  65: 78 */     Fetch<Object, Object> direccionEmpresa = despachoCliente.fetch("direccionEmpresa", JoinType.LEFT);
/*  66: 79 */     direccionEmpresa.fetch("ubicacion", JoinType.LEFT);
/*  67: 80 */     despachoCliente.fetch("subempresa", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  68:    */     
/*  69: 82 */     Fetch<Object, Object> empresa = despachoCliente.fetch("empresa", JoinType.LEFT);
/*  70: 83 */     empresa.fetch("cliente", JoinType.LEFT);
/*  71: 84 */     empresa.fetch("proveedor", JoinType.LEFT);
/*  72: 85 */     empresa.fetch("empleado", JoinType.LEFT);
/*  73:    */     
/*  74: 87 */     Path<Integer> pathIdDetalle = fromDetalle.join("hojaRuta").get("idHojaRuta");
/*  75: 88 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idHojaRuta)));
/*  76: 89 */     CriteriaQuery<DetalleHojaRuta> selectDetalleDespacho = cqDetalle.select(fromDetalle);
/*  77:    */     
/*  78: 91 */     List<DetalleHojaRuta> listaDetalleHojaRuta = this.em.createQuery(selectDetalleDespacho).getResultList();
/*  79: 92 */     hojaRuta.setListaDetalleHojaRuta(listaDetalleHojaRuta);
/*  80:    */     
/*  81: 94 */     return hojaRuta;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<HojaRuta> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  85:    */   {
/*  86: 99 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  87:100 */     CriteriaQuery<HojaRuta> criteriaQuery = criteriaBuilder.createQuery(HojaRuta.class);
/*  88:101 */     Root<HojaRuta> from = criteriaQuery.from(HojaRuta.class);
/*  89:    */     
/*  90:103 */     from.fetch("transportista", JoinType.LEFT);
/*  91:    */     
/*  92:105 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  93:106 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  94:    */     
/*  95:108 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  96:    */     
/*  97:110 */     CriteriaQuery<HojaRuta> select = criteriaQuery.select(from);
/*  98:    */     
/*  99:112 */     TypedQuery<HojaRuta> typedQuery = this.em.createQuery(select);
/* 100:113 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 101:    */     
/* 102:115 */     return typedQuery.getResultList();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List<DetalleHojaRuta> cargarDespachos(int idOrganizacion, Sucursal sucursal, Date fechaDesde, Date fechaHasta)
/* 106:    */   {
/* 107:120 */     StringBuilder sql = new StringBuilder();
/* 108:121 */     sql.append(" SELECT new DetalleHojaRuta(d.idOrganizacion, 0, coalesce(f.numero,''), SUM(d.cantidad), COUNT(*), e.descripcion,");
/* 109:122 */     sql.append(" dc.idDespachoCliente, dc.fecha, e.nombreComercial, e.nombreFiscal, ciu.nombre) ");
/* 110:123 */     sql.append(" FROM DetalleDespachoCliente d ");
/* 111:124 */     sql.append(" JOIN d.despachoCliente dc  ");
/* 112:125 */     sql.append(" JOIN dc.empresa e  ");
/* 113:126 */     sql.append(" JOIN e.cliente c ");
/* 114:127 */     sql.append(" LEFT JOIN d.inventarioProducto i ");
/* 115:128 */     sql.append(" LEFT JOIN d.detalleFacturaCliente dfc ");
/* 116:129 */     sql.append(" LEFT JOIN dfc.facturaCliente f ");
/* 117:130 */     sql.append(" JOIN f.direccionEmpresa dire ");
/* 118:131 */     sql.append(" JOIN dire.ciudad ciu ");
/* 119:132 */     sql.append(" WHERE dc.estado != :estadoAnulado AND ( d.idOrganizacion = :idOrganizacion AND dc.fecha between :fechaDesde AND :fechaHasta) ");
/* 120:133 */     if (sucursal != null) {
/* 121:134 */       sql.append(" AND dc.sucursal = :sucursal");
/* 122:    */     }
/* 123:136 */     sql.append(" and dc.idDespachoCliente NOT IN (SELECT dcc.idDespachoCliente FROM DetalleHojaRuta dhr JOIN dhr.despachoCliente dcc JOIN dhr.hojaRuta hr) ");
/* 124:137 */     sql.append(" GROUP BY d.idOrganizacion,coalesce(f.numero,''), e.descripcion,dc.idDespachoCliente, dc.fecha, e.nombreComercial, e.nombreFiscal, ciu.nombre ");
/* 125:138 */     sql.append(" ORDER BY ciu.nombre, dc.fecha  ");
/* 126:139 */     Query query = this.em.createQuery(sql.toString());
/* 127:140 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 128:141 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 129:142 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 130:143 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 131:144 */     if (sucursal != null) {
/* 132:145 */       query.setParameter("sucursal", sucursal);
/* 133:    */     }
/* 134:147 */     return query.getResultList();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<Object[]> reporteHojaRuta(int idOrganizacion, int idHojaRuta)
/* 138:    */   {
/* 139:153 */     StringBuilder sql = new StringBuilder();
/* 140:154 */     sql.append(" SELECT d.nombre, hr.fecha, hr.descripcion, dhr.factura, ");
/* 141:155 */     sql.append(" e.nombreFiscal, dc.fecha, dhr.cantidad, dhr.piezas, dhr.bultos,  ");
/* 142:156 */     sql.append(" dhr.descripcion, hr.numero, dhr.ciudad, hr.responsable ");
/* 143:157 */     sql.append(" FROM DetalleHojaRuta dhr ");
/* 144:158 */     sql.append(" INNER JOIN dhr.hojaRuta hr ");
/* 145:159 */     sql.append(" INNER JOIN hr.documento d ");
/* 146:160 */     sql.append(" INNER JOIN dhr.despachoCliente dc ");
/* 147:161 */     sql.append(" INNER JOIN dc.empresa e ");
/* 148:162 */     sql.append(" WHERE hr.idHojaRuta = :idHojaRuta ");
/* 149:163 */     sql.append(" AND hr.idOrganizacion = :idOrganizacion ");
/* 150:164 */     sql.append(" ORDER BY dc.fecha, dhr.factura ");
/* 151:    */     
/* 152:166 */     Query query = this.em.createQuery(sql.toString());
/* 153:167 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 154:168 */     query.setParameter("idHojaRuta", Integer.valueOf(idHojaRuta));
/* 155:    */     
/* 156:170 */     return query.getResultList();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public List<DespachoCliente> detalleHojaRutaTransportista(int idOrganizacion, Sucursal sucursal, Transportista transportista, Date fechaDesde, Empresa empresa, Date fechaHasta)
/* 160:    */   {
/* 161:177 */     StringBuilder sql = new StringBuilder();
/* 162:    */     
/* 163:179 */     sql.append(" SELECT dc FROM DespachoCliente dc");
/* 164:180 */     sql.append(" JOIN FETCH dc.empresa e");
/* 165:181 */     sql.append(" LEFT JOIN FETCH dc.transportista tra");
/* 166:182 */     sql.append(" LEFT JOIN FETCH dc.sucursal s");
/* 167:183 */     sql.append(" LEFT JOIN FETCH dc.direccionEmpresa dir");
/* 168:184 */     sql.append(" LEFT JOIN FETCH dir.ubicacion ub");
/* 169:185 */     sql.append(" LEFT JOIN FETCH e.cliente cli");
/* 170:186 */     sql.append(" LEFT JOIN FETCH dc.subempresa se");
/* 171:187 */     sql.append(" LEFT JOIN FETCH se.empresa esub");
/* 172:188 */     sql.append(" LEFT JOIN FETCH esub.cliente clisub");
/* 173:189 */     sql.append(" WHERE dc.idOrganizacion = :idOrganizacion");
/* 174:190 */     sql.append(" AND s = :sucursal ");
/* 175:191 */     sql.append(" AND dc.fecha between :fechaDesde and :fechaHasta ");
/* 176:192 */     sql.append(" AND dc.estado != :estado  ");
/* 177:193 */     sql.append(" AND dc.idDespachoCliente not in (select dcc.idDespachoCliente from DetalleHojaRuta dhr inner join dhr.despachoCliente dcc inner join dhr.hojaRuta hr)");
/* 178:194 */     if (transportista != null) {
/* 179:195 */       sql.append(" AND tra = :transportista");
/* 180:    */     }
/* 181:197 */     if (empresa != null) {
/* 182:198 */       sql.append(" and e  = :empresa ");
/* 183:    */     }
/* 184:200 */     sql.append(" order by e.nombreComercial, esub.nombreComercial ");
/* 185:    */     
/* 186:202 */     Query query = this.em.createQuery(sql.toString());
/* 187:203 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 188:204 */     query.setParameter("sucursal", sucursal);
/* 189:205 */     if (transportista != null) {
/* 190:206 */       query.setParameter("transportista", transportista);
/* 191:    */     }
/* 192:208 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 193:209 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 194:210 */     query.setParameter("estado", Estado.ANULADO);
/* 195:211 */     if (empresa != null) {
/* 196:212 */       query.setParameter("empresa", empresa);
/* 197:    */     }
/* 198:215 */     return query.getResultList();
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<Object[]> reporteHojaRutaTransportista(int idOrganizacion, int idHojaRuta, boolean detallado)
/* 202:    */   {
/* 203:263 */     StringBuilder sql = new StringBuilder();
/* 204:    */     
/* 205:265 */     sql.append(" select trans.nombre,hr.numero, hr.fecha, hr.descripcion ,dc.numero ,e.nombreComercial, coalesce( esub.nombreComercial, '' ) , ");
/* 206:266 */     sql.append(" p.nombre, uv.nombre, lddc.cantidad, concat(ubi.direccion1, ' ',ubi.direccion2, ' ',ubi.direccion3, ' ',ubi.direccion4 ) ");
/* 207:267 */     sql.append(" from  DetalleHojaRuta dhr ");
/* 208:268 */     sql.append(" inner join dhr.hojaRuta hr ");
/* 209:269 */     sql.append(" inner join dhr.despachoCliente dc");
/* 210:270 */     sql.append(" left join dc.listaDetalleDespachoCliente lddc");
/* 211:271 */     sql.append(" left join hr.transportista trans ");
/* 212:272 */     sql.append(" inner join lddc.producto p ");
/* 213:273 */     sql.append(" inner join lddc.unidadVenta uv ");
/* 214:274 */     sql.append(" inner join dc.direccionEmpresa dire");
/* 215:275 */     sql.append(" inner join dire.ubicacion ubi ");
/* 216:276 */     sql.append(" inner join dc.empresa e");
/* 217:277 */     sql.append(" left  join dc.subempresa se");
/* 218:278 */     sql.append(" left  join se.empresa esub");
/* 219:279 */     sql.append(" where hr.idHojaRuta = :idHojaRuta ");
/* 220:280 */     if (detallado) {
/* 221:281 */       sql.append(" order by e.nombreComercial, coalesce  (esub.nombreComercial, ''), p.nombre  ");
/* 222:    */     } else {
/* 223:283 */       sql.append(" order by p.nombre");
/* 224:    */     }
/* 225:286 */     Query query = this.em.createQuery(sql.toString());
/* 226:287 */     query.setParameter("idHojaRuta", Integer.valueOf(idHojaRuta));
/* 227:    */     
/* 228:289 */     return query.getResultList();
/* 229:    */   }
/* 230:    */   
/* 231:    */   public List<Cliente> getDetallesHojaRutaPorFechaYTransportista(Date fecha, int idUsuarioTransportista)
/* 232:    */   {
/* 233:296 */     StringBuilder sql = new StringBuilder();
/* 234:297 */     sql.append(" SELECT c1, c2 ");
/* 235:298 */     sql.append(" FROM  DetalleHojaRuta dhr ");
/* 236:299 */     sql.append(" INNER JOIN dhr.hojaRuta hr ");
/* 237:300 */     sql.append(" INNER JOIN hr.transportista trans ");
/* 238:301 */     sql.append(" INNER JOIN trans.usuario u ");
/* 239:302 */     sql.append(" INNER JOIN dhr.despachoCliente dc ");
/* 240:303 */     sql.append(" INNER JOIN dc.empresa e");
/* 241:304 */     sql.append(" INNER JOIN e.cliente c1");
/* 242:305 */     sql.append(" LEFT  JOIN dc.subempresa se");
/* 243:306 */     sql.append(" LEFT  JOIN se.empresa esub");
/* 244:307 */     sql.append(" LEFT  JOIN esub.cliente c2");
/* 245:308 */     sql.append(" WHERE hr.fecha = :fecha ");
/* 246:309 */     sql.append(" AND u.idUsuario = :idUsuario ");
/* 247:    */     
/* 248:311 */     Query query = this.em.createQuery(sql.toString());
/* 249:312 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 250:313 */     query.setParameter("idUsuario", Integer.valueOf(idUsuarioTransportista));
/* 251:    */     
/* 252:315 */     List<Object[]> lista = query.getResultList();
/* 253:316 */     List<Cliente> listaCliente = new ArrayList();
/* 254:    */     try
/* 255:    */     {
/* 256:318 */       for (Object[] elemento : lista)
/* 257:    */       {
/* 258:319 */         Cliente cliente1 = (Cliente)elemento[0];
/* 259:320 */         Cliente cliente2 = (Cliente)elemento[1];
/* 260:321 */         if (cliente2 != null) {
/* 261:322 */           listaCliente.add(cliente2);
/* 262:    */         } else {
/* 263:324 */           listaCliente.add(cliente1);
/* 264:    */         }
/* 265:    */       }
/* 266:    */     }
/* 267:    */     catch (Exception e)
/* 268:    */     {
/* 269:328 */       e.printStackTrace();
/* 270:    */     }
/* 271:331 */     return listaCliente;
/* 272:    */   }
/* 273:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.HojaRutaDao
 * JD-Core Version:    0.7.0.1
 */