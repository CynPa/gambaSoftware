/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   4:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   5:    */ import com.asinfo.as2.entities.DetallePagoComision;
/*   6:    */ import com.asinfo.as2.entities.DetalleVersionPlanComision;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.PagoComision;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  11:    */ import com.asinfo.as2.entities.VersionPlanComision;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  13:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  16:    */ import com.asinfo.as2.enumeraciones.TipoComisionEnum;
/*  17:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  18:    */ import java.io.PrintStream;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.ejb.Stateless;
/*  23:    */ import javax.persistence.EntityManager;
/*  24:    */ import javax.persistence.NoResultException;
/*  25:    */ import javax.persistence.Query;
/*  26:    */ import javax.persistence.TemporalType;
/*  27:    */ import javax.persistence.TypedQuery;
/*  28:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  29:    */ import javax.persistence.criteria.CriteriaQuery;
/*  30:    */ import javax.persistence.criteria.Expression;
/*  31:    */ import javax.persistence.criteria.Fetch;
/*  32:    */ import javax.persistence.criteria.Join;
/*  33:    */ import javax.persistence.criteria.JoinType;
/*  34:    */ import javax.persistence.criteria.Path;
/*  35:    */ import javax.persistence.criteria.Predicate;
/*  36:    */ import javax.persistence.criteria.Root;
/*  37:    */ 
/*  38:    */ @Stateless
/*  39:    */ public class PagoComisionDao
/*  40:    */   extends AbstractDaoAS2<PagoComision>
/*  41:    */ {
/*  42:    */   public PagoComisionDao()
/*  43:    */   {
/*  44: 39 */     super(PagoComision.class);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<PagoComision> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  48:    */   {
/*  49: 43 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  50: 44 */     CriteriaQuery<PagoComision> criteriaQuery = criteriaBuilder.createQuery(PagoComision.class);
/*  51: 45 */     Root<PagoComision> from = criteriaQuery.from(PagoComision.class);
/*  52:    */     
/*  53: 47 */     from.fetch("documento", JoinType.INNER);
/*  54:    */     
/*  55: 49 */     List<Expression<?>> expreciones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  56: 50 */     criteriaQuery.where((Predicate[])expreciones.toArray(new Predicate[expreciones.size()]));
/*  57:    */     
/*  58: 52 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  59:    */     
/*  60: 54 */     CriteriaQuery<PagoComision> select = criteriaQuery.select(from);
/*  61: 55 */     TypedQuery<PagoComision> typedQuery = this.em.createQuery(select);
/*  62: 56 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  63:    */     
/*  64: 58 */     return typedQuery.getResultList();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public PagoComision cargarDetalle(int idPagoComision)
/*  68:    */   {
/*  69: 62 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  70:    */     
/*  71: 64 */     CriteriaQuery<PagoComision> cqCabecera = criteriaBuilder.createQuery(PagoComision.class);
/*  72: 65 */     Root<PagoComision> fromCabecera = cqCabecera.from(PagoComision.class);
/*  73:    */     
/*  74: 67 */     fromCabecera.fetch("documento", JoinType.INNER).fetch("secuencia", JoinType.LEFT);
/*  75:    */     
/*  76: 69 */     Path<Integer> pathId = fromCabecera.get("idPagoComision");
/*  77: 70 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idPagoComision)));
/*  78: 71 */     CriteriaQuery<PagoComision> selectPagoComision = cqCabecera.select(fromCabecera);
/*  79:    */     
/*  80: 73 */     PagoComision pagoComision = (PagoComision)this.em.createQuery(selectPagoComision).getSingleResult();
/*  81:    */     
/*  82: 75 */     CriteriaQuery<DetallePagoComision> cqDetalle = criteriaBuilder.createQuery(DetallePagoComision.class);
/*  83: 76 */     Root<DetallePagoComision> fromDetalle = cqDetalle.from(DetallePagoComision.class);
/*  84:    */     
/*  85: 78 */     fromDetalle.fetch("agenteComercial", JoinType.INNER);
/*  86: 79 */     fromDetalle.fetch("categoriaProducto", JoinType.LEFT);
/*  87: 80 */     fromDetalle.fetch("subcategoriaProducto", JoinType.LEFT);
/*  88: 81 */     fromDetalle.fetch("producto", JoinType.LEFT);
/*  89: 82 */     fromDetalle.fetch("rangoDiasComision", JoinType.INNER);
/*  90: 83 */     fromDetalle.fetch("facturaCliente", JoinType.INNER);
/*  91: 84 */     fromDetalle.fetch("detalleCobro", JoinType.INNER).fetch("cobro", JoinType.INNER);
/*  92:    */     
/*  93: 86 */     Path<Integer> pathIdDetalle = fromDetalle.join("pagoComision").get("idPagoComision");
/*  94: 87 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idPagoComision)));
/*  95: 88 */     CriteriaQuery<DetallePagoComision> selectDetalle = cqDetalle.select(fromDetalle);
/*  96:    */     
/*  97: 90 */     List<DetallePagoComision> listaDetallePagoComision = this.em.createQuery(selectDetalle).getResultList();
/*  98: 91 */     pagoComision.setListaDetallePagoComision(listaDetallePagoComision);
/*  99: 92 */     return pagoComision;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<Object[]> getFacturasAPagarComision(EntidadUsuario agenteComercial, DetalleVersionPlanComision detalle, VersionPlanComision version, Date fechaInicalCobro, Date fechaFinalCobro, Date fechaInicalFactura, Date fechaFinalFactura)
/* 103:    */   {
/* 104: 98 */     StringBuilder sql = new StringBuilder();
/* 105: 99 */     sql.append(" SELECT fc.idFacturaCliente, fc.numero, fc.fecha, co.fecha, fc.total-fc.descuento+fc.impuesto+fcSRI.montoIce, fc.total-fc.descuento, SUM(dfc.precioLinea-dfc.descuentoLinea), SUM(dfc.cantidad), dc.idDetalleCobro, dc.valor, co.numero, dcfc.idDetalleCobroFormaCobro, co.idCobro ");
/* 106:100 */     sql.append(" FROM DetalleFacturaCliente dfc, DetalleCobroFormaCobro dcfc ");
/* 107:101 */     if (version.getTipoComisionEnum().equals(TipoComisionEnum.SUPERVISOR))
/* 108:    */     {
/* 109:102 */       sql.append(" , DetalleVersionPlanComisionSupervisor dvpcs ");
/* 110:103 */       sql.append(" INNER JOIN dvpcs.agenteComercial acSup ");
/* 111:104 */       sql.append(" INNER JOIN dvpcs.empresa empSup ");
/* 112:    */     }
/* 113:106 */     sql.append(" INNER JOIN dcfc.detalleCobro dc ");
/* 114:107 */     sql.append(" INNER JOIN dcfc.detalleFormaCobro dfCob ");
/* 115:108 */     sql.append(" INNER JOIN dfCob.formaPago fCob ");
/* 116:109 */     sql.append(" INNER JOIN dc.cobro co ");
/* 117:110 */     sql.append(" INNER JOIN dc.cuentaPorCobrar cxc ");
/* 118:111 */     sql.append(" INNER JOIN cxc.facturaCliente fcc ");
/* 119:112 */     sql.append(" INNER JOIN dfc.producto prod ");
/* 120:113 */     sql.append(" INNER JOIN prod.subcategoriaProducto scp ");
/* 121:114 */     sql.append(" INNER JOIN scp.categoriaProducto cp ");
/* 122:115 */     sql.append(" INNER JOIN dfc.facturaCliente fc ");
/* 123:116 */     sql.append(" LEFT JOIN fc.facturaClienteSRI fcSRI ");
/* 124:117 */     sql.append(" INNER JOIN fc.empresa emp ");
/* 125:118 */     sql.append(" LEFT JOIN fc.subempresa subEmp ");
/* 126:119 */     sql.append(" LEFT JOIN subEmp.empresa emp2 ");
/* 127:120 */     sql.append(" INNER JOIN fc.documento doc ");
/* 128:121 */     sql.append(" LEFT JOIN fc.agenteComercial ac ");
/* 129:122 */     sql.append(" WHERE fcc.idFacturaCliente = fc.idFacturaCliente ");
/* 130:123 */     sql.append(" AND fCob.indicadorRetencionFuente = false AND fCob.indicadorRetencionIva = false ");
/* 131:124 */     sql.append(" AND dfCob.indicadorChequeProtestado = false ");
/* 132:125 */     sql.append(" AND fc.fecha <= :fechaFinalFactura AND fc.fecha >= :fechaInicalFactura ");
/* 133:126 */     sql.append(" AND co.fecha <= :fechaFinalCobro AND co.fecha >= :fechaInicalCobro ");
/* 134:127 */     sql.append(" AND (doc.documentoBase = :documentoBaseFC OR doc.documentoBase = :documentoBaseNDC) ");
/* 135:128 */     sql.append(" AND fc.estado != :estadoAnulado ");
/* 136:129 */     sql.append(" AND fc.estado != :estadoRechazadoSRI ");
/* 137:130 */     sql.append(" AND (co.estado = :estadoProcesado OR co.estado = :estadoContabilizado) ");
/* 138:132 */     if (version.getTipoComisionEnum().equals(TipoComisionEnum.AGENTE_COMERICAL))
/* 139:    */     {
/* 140:133 */       sql.append(" AND ac.idUsuario = :idUsuario ");
/* 141:    */     }
/* 142:134 */     else if (version.getTipoComisionEnum().equals(TipoComisionEnum.SUPERVISOR))
/* 143:    */     {
/* 144:135 */       sql.append(" AND ac.idUsuario = acSup.idUsuario ");
/* 145:136 */       sql.append(" AND (emp.idEmpresa = empSup.idEmpresa OR emp2.idEmpresa = empSup.idEmpresa) ");
/* 146:    */     }
/* 147:139 */     if (detalle.getCategoriaProducto() != null) {
/* 148:140 */       sql.append(" AND cp.idCategoriaProducto = :idCategoriaProducto ");
/* 149:    */     }
/* 150:142 */     if (detalle.getSubcategoriaProducto() != null) {
/* 151:143 */       sql.append(" AND scp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/* 152:    */     }
/* 153:145 */     if (detalle.getProducto() != null) {
/* 154:146 */       sql.append(" AND prod.idProducto = :idProducto ");
/* 155:    */     }
/* 156:149 */     sql.append(" GROUP BY fc.idFacturaCliente,fc.numero,fc.fecha, co.fecha,fc.total,fc.descuento,fc.impuesto,fcSRI.montoIce, dcfc.idDetalleCobroFormaCobro, dc.idDetalleCobro, dc.valor, co.numero, co.idCobro ");
/* 157:    */     
/* 158:151 */     Query query = this.em.createQuery(sql.toString());
/* 159:152 */     query.setParameter("fechaInicalFactura", fechaInicalFactura, TemporalType.DATE);
/* 160:153 */     query.setParameter("fechaFinalFactura", fechaFinalFactura, TemporalType.DATE);
/* 161:154 */     query.setParameter("fechaInicalCobro", fechaInicalCobro, TemporalType.DATE);
/* 162:155 */     query.setParameter("fechaFinalCobro", fechaFinalCobro, TemporalType.DATE);
/* 163:156 */     if (version.getTipoComisionEnum().equals(TipoComisionEnum.AGENTE_COMERICAL)) {
/* 164:157 */       query.setParameter("idUsuario", Integer.valueOf(agenteComercial.getIdUsuario()));
/* 165:    */     }
/* 166:159 */     query.setParameter("documentoBaseFC", DocumentoBase.FACTURA_CLIENTE);
/* 167:160 */     query.setParameter("documentoBaseNDC", DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 168:161 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 169:162 */     query.setParameter("estadoRechazadoSRI", Estado.RECHAZADO_SRI);
/* 170:163 */     query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 171:164 */     query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/* 172:165 */     if (detalle.getCategoriaProducto() != null) {
/* 173:166 */       query.setParameter("idCategoriaProducto", Integer.valueOf(detalle.getCategoriaProducto().getId()));
/* 174:    */     }
/* 175:168 */     if (detalle.getSubcategoriaProducto() != null) {
/* 176:169 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(detalle.getSubcategoriaProducto().getId()));
/* 177:    */     }
/* 178:171 */     if (detalle.getProducto() != null) {
/* 179:172 */       query.setParameter("idProducto", Integer.valueOf(detalle.getProducto().getId()));
/* 180:    */     }
/* 181:175 */     return query.getResultList();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<Object[]> getReportePagoComision(PagoComision pagoComision)
/* 185:    */   {
/* 186:180 */     StringBuilder sql = new StringBuilder();
/* 187:181 */     sql.append(" SELECT doc.nombre, pc.numero, pc.fecha, pc.mesInicial, pc.mesFinal, pc.anioInicial, pc.anioFinal, pc.estado, pc.descripcion ");
/* 188:182 */     sql.append(" , CONCAT(ac.nombre1, ' ', ac.nombre2), fc.numero, cp.nombre, scp.nombre, prod.codigo, prod.nombre, rdc.nombre, co.numero ");
/* 189:183 */     sql.append(" , dpc.baseComision, dpc.formaPagoComisionEnum, dpc.porcentajeComision, dpc.valorComision ");
/* 190:184 */     sql.append(" FROM DetallePagoComision dpc ");
/* 191:185 */     sql.append(" INNER JOIN dpc.pagoComision pc ");
/* 192:186 */     sql.append(" INNER JOIN pc.documento doc ");
/* 193:187 */     sql.append(" INNER JOIN dpc.agenteComercial ac ");
/* 194:188 */     sql.append(" LEFT JOIN dpc.categoriaProducto cp ");
/* 195:189 */     sql.append(" LEFT JOIN dpc.subcategoriaProducto scp ");
/* 196:190 */     sql.append(" LEFT JOIN dpc.producto prod ");
/* 197:191 */     sql.append(" INNER JOIN dpc.rangoDiasComision rdc ");
/* 198:192 */     sql.append(" INNER JOIN dpc.facturaCliente fc ");
/* 199:193 */     sql.append(" INNER JOIN dpc.detalleCobro dcc ");
/* 200:194 */     sql.append(" INNER JOIN dcc.cobro co ");
/* 201:195 */     sql.append(" WHERE pc.idPagoComision = :idPagoComision ");
/* 202:196 */     sql.append(" ORDER BY CONCAT(ac.nombre1, ' ', ac.nombre2) ");
/* 203:    */     
/* 204:198 */     Query query = this.em.createQuery(sql.toString());
/* 205:199 */     query.setParameter("idPagoComision", Integer.valueOf(pagoComision.getId()));
/* 206:200 */     return query.getResultList();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public PagoComision obtenerUltimoPagoComision()
/* 210:    */   {
/* 211:204 */     StringBuilder sql = new StringBuilder();
/* 212:205 */     sql.append(" SELECT pc ");
/* 213:206 */     sql.append(" FROM PagoComision pc ");
/* 214:207 */     sql.append(" ORDER BY pc.anioFinal, pc.mesFinal DESC ");
/* 215:    */     
/* 216:209 */     Query query = this.em.createQuery(sql.toString());
/* 217:210 */     query.setMaxResults(1);
/* 218:    */     try
/* 219:    */     {
/* 220:212 */       return (PagoComision)query.getSingleResult();
/* 221:    */     }
/* 222:    */     catch (NoResultException e) {}
/* 223:214 */     return null;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public List<Object[]> getReportePagoComision(int idOrganizacion, Date fechaDesde, Date fechaHasta, EntidadUsuario agenteComercial, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, CategoriaEmpresa categoriaEmpresa, Empresa empresa)
/* 227:    */   {
/* 228:222 */     StringBuilder sql = new StringBuilder();
/* 229:223 */     sql.append(" SELECT doc.nombre, pc.numero, pc.fecha, pc.mesInicial, pc.mesFinal, pc.anioInicial, pc.anioFinal, pc.estado, pc.descripcion ");
/* 230:224 */     sql.append(" , CONCAT(ac.nombre1, ' ', ac.nombre2), fc.numero, cp.nombre, scp.nombre, prod.codigo, prod.nombre, rdc.nombre, co.numero ");
/* 231:225 */     sql.append(" , dpc.baseComision, dpc.formaPagoComisionEnum, dpc.porcentajeComision, dpc.valorComision, emp.nombreFiscal ");
/* 232:226 */     sql.append(" FROM DetallePagoComision dpc ");
/* 233:227 */     sql.append(" INNER JOIN dpc.pagoComision pc ");
/* 234:228 */     sql.append(" INNER JOIN pc.documento doc ");
/* 235:229 */     sql.append(" INNER JOIN dpc.agenteComercial ac ");
/* 236:230 */     sql.append(" LEFT JOIN dpc.categoriaProducto cp ");
/* 237:231 */     sql.append(" LEFT JOIN dpc.subcategoriaProducto scp ");
/* 238:232 */     sql.append(" LEFT JOIN dpc.producto prod ");
/* 239:233 */     sql.append(" LEFT JOIN prod.subcategoriaProducto scp2 ");
/* 240:234 */     sql.append(" LEFT JOIN scp2.categoriaProducto cp2 ");
/* 241:235 */     sql.append(" INNER JOIN dpc.rangoDiasComision rdc ");
/* 242:236 */     sql.append(" INNER JOIN dpc.facturaCliente fc ");
/* 243:237 */     sql.append(" INNER JOIN fc.empresa emp ");
/* 244:238 */     sql.append(" INNER JOIN emp.categoriaEmpresa cEmp ");
/* 245:239 */     sql.append(" INNER JOIN dpc.detalleCobro dcc ");
/* 246:240 */     sql.append(" INNER JOIN dcc.cobro co ");
/* 247:241 */     sql.append(" WHERE pc.idOrganizacion = :idOrganizacion ");
/* 248:242 */     sql.append(" AND co.fecha <= :fechaHasta AND co.fecha >= :fechaDesde ");
/* 249:243 */     if (agenteComercial != null) {
/* 250:244 */       sql.append(" AND ac.idUsuario = :idUsuario ");
/* 251:    */     }
/* 252:246 */     if (categoriaProducto != null) {
/* 253:247 */       sql.append(" AND (cp.idCategoriaProducto = :idCategoriaProducto OR cp2.idCategoriaProducto = :idCategoriaProducto) ");
/* 254:    */     }
/* 255:249 */     if (subcategoriaProducto != null) {
/* 256:250 */       sql.append(" AND (scp.idSubcategoriaProducto = :idSubcategoriaProducto OR scp2.idSubcategoriaProducto = :idSubcategoriaProducto) ");
/* 257:    */     }
/* 258:252 */     if (producto != null) {
/* 259:253 */       sql.append(" AND prod.idProducto = :idProducto ");
/* 260:    */     }
/* 261:255 */     if (categoriaEmpresa != null) {
/* 262:256 */       sql.append(" AND cEmp.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 263:    */     }
/* 264:258 */     if (empresa != null) {
/* 265:259 */       sql.append(" AND emp.idEmpresa = :idEmpresa ");
/* 266:    */     }
/* 267:262 */     sql.append(" ORDER BY CONCAT(ac.nombre1, ' ', ac.nombre2) ");
/* 268:    */     
/* 269:264 */     Query query = this.em.createQuery(sql.toString());
/* 270:265 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 271:266 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 272:267 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 273:268 */     if (agenteComercial != null) {
/* 274:269 */       query.setParameter("idUsuario", Integer.valueOf(agenteComercial.getId()));
/* 275:    */     }
/* 276:271 */     if (categoriaProducto != null) {
/* 277:272 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/* 278:    */     }
/* 279:274 */     if (subcategoriaProducto != null) {
/* 280:275 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/* 281:    */     }
/* 282:277 */     if (producto != null) {
/* 283:278 */       query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/* 284:    */     }
/* 285:280 */     if (categoriaEmpresa != null) {
/* 286:281 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getId()));
/* 287:    */     }
/* 288:283 */     if (empresa != null) {
/* 289:284 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/* 290:    */     }
/* 291:286 */     return query.getResultList();
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void validarFechasPagoComision(PagoComision pagoComision)
/* 295:    */     throws AS2Exception
/* 296:    */   {
/* 297:290 */     if (pagoComision.getFechaDesde().compareTo(pagoComision.getFechaHasta()) > 0) {
/* 298:292 */       throw new AS2Exception("msg_error_intervalo_fechas", new String[] {pagoComision.getMesInicial().getNombre() + "-" + pagoComision.getAnioInicial(), pagoComision.getMesFinal().getNombre() + "-" + pagoComision.getAnioFinal() });
/* 299:    */     }
/* 300:295 */     StringBuilder sql = new StringBuilder();
/* 301:296 */     sql.append(" SELECT pc.fechaHasta ");
/* 302:297 */     sql.append(" FROM PagoComision pc ");
/* 303:298 */     sql.append(" WHERE pc.fechaHasta >= :fechaDesde ");
/* 304:299 */     sql.append(" AND pc.idPagoComision != :idPagoComision ");
/* 305:300 */     sql.append(" AND pc.idOrganizacion = :idOrganizacion ");
/* 306:    */     
/* 307:302 */     Query query = this.em.createQuery(sql.toString());
/* 308:303 */     query.setParameter("idOrganizacion", Integer.valueOf(pagoComision.getIdOrganizacion()));
/* 309:304 */     query.setParameter("idPagoComision", Integer.valueOf(pagoComision.getId()));
/* 310:305 */     query.setParameter("fechaDesde", pagoComision.getFechaDesde(), TemporalType.DATE);
/* 311:    */     try
/* 312:    */     {
/* 313:308 */       Date resultado = (Date)query.getSingleResult();
/* 314:309 */       System.out.println("resultado: " + resultado);
/* 315:311 */       if (resultado != null) {
/* 316:312 */         throw new AS2Exception("msg_error_pago_comision_fecha_fuera_rango", new String[] { "" });
/* 317:    */       }
/* 318:    */     }
/* 319:    */     catch (NoResultException localNoResultException) {}
/* 320:    */   }
/* 321:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PagoComisionDao
 * JD-Core Version:    0.7.0.1
 */