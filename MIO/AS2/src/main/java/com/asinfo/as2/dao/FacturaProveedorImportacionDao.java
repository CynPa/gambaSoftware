/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.Bodega;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   7:    */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacion;
/*   8:    */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionGasto;
/*   9:    */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*  10:    */ import com.asinfo.as2.entities.DetalleProcesoImportacion;
/*  11:    */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.Empresa;
/*  14:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  15:    */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*  16:    */ import com.asinfo.as2.entities.GastoImportacion;
/*  17:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  18:    */ import com.asinfo.as2.entities.Lote;
/*  19:    */ import com.asinfo.as2.entities.Moneda;
/*  20:    */ import com.asinfo.as2.entities.Pais;
/*  21:    */ import com.asinfo.as2.entities.PartidaArancelaria;
/*  22:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*  23:    */ import com.asinfo.as2.entities.ProcesoImportacion;
/*  24:    */ import com.asinfo.as2.entities.Producto;
/*  25:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  26:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  27:    */ import com.asinfo.as2.entities.Unidad;
/*  28:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  29:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  30:    */ import java.util.Date;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import javax.ejb.Stateless;
/*  34:    */ import javax.persistence.EntityManager;
/*  35:    */ import javax.persistence.Query;
/*  36:    */ import javax.persistence.TypedQuery;
/*  37:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  38:    */ import javax.persistence.criteria.CriteriaQuery;
/*  39:    */ import javax.persistence.criteria.Expression;
/*  40:    */ import javax.persistence.criteria.Fetch;
/*  41:    */ import javax.persistence.criteria.Join;
/*  42:    */ import javax.persistence.criteria.JoinType;
/*  43:    */ import javax.persistence.criteria.Path;
/*  44:    */ import javax.persistence.criteria.Predicate;
/*  45:    */ import javax.persistence.criteria.Root;
/*  46:    */ 
/*  47:    */ @Stateless
/*  48:    */ public class FacturaProveedorImportacionDao
/*  49:    */   extends AbstractDaoAS2<FacturaProveedorImportacion>
/*  50:    */ {
/*  51:    */   public FacturaProveedorImportacionDao()
/*  52:    */   {
/*  53: 53 */     super(FacturaProveedorImportacion.class);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List<FacturaProveedorImportacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  57:    */   {
/*  58: 63 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  59: 64 */     CriteriaQuery<FacturaProveedorImportacion> criteriaQuery = criteriaBuilder.createQuery(FacturaProveedorImportacion.class);
/*  60: 65 */     Root<FacturaProveedorImportacion> from = criteriaQuery.from(FacturaProveedorImportacion.class);
/*  61:    */     
/*  62: 67 */     Fetch<Object, Object> facturaProveedor = from.fetch("facturaProveedor", JoinType.LEFT);
/*  63: 68 */     facturaProveedor.fetch("recepcionProveedor", JoinType.LEFT);
/*  64: 69 */     facturaProveedor.fetch("empresa", JoinType.LEFT);
/*  65: 70 */     facturaProveedor.fetch("documento", JoinType.LEFT);
/*  66:    */     
/*  67: 72 */     Fetch<Object, Object> asiento = facturaProveedor.fetch("asiento", JoinType.LEFT);
/*  68: 73 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/*  69: 74 */     from.fetch("pais", JoinType.LEFT);
/*  70: 75 */     from.fetch("moneda", JoinType.LEFT);
/*  71:    */     
/*  72: 77 */     Fetch<Object, Object> asientoImportacion = from.fetch("asiento", JoinType.LEFT);
/*  73: 78 */     asientoImportacion.fetch("tipoAsiento", JoinType.LEFT);
/*  74:    */     
/*  75: 80 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  76: 81 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  77:    */     
/*  78: 83 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  79:    */     
/*  80: 85 */     CriteriaQuery<FacturaProveedorImportacion> select = criteriaQuery.select(from);
/*  81: 86 */     TypedQuery<FacturaProveedorImportacion> typedQuery = this.em.createQuery(select);
/*  82: 87 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  83:    */     
/*  84: 89 */     return typedQuery.getResultList();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List<FacturaProveedorImportacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  88:    */   {
/*  89: 98 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  90: 99 */     CriteriaQuery<FacturaProveedorImportacion> criteriaQuery = criteriaBuilder.createQuery(FacturaProveedorImportacion.class);
/*  91:100 */     Root<FacturaProveedorImportacion> from = criteriaQuery.from(FacturaProveedorImportacion.class);
/*  92:101 */     Fetch<Object, Object> facturaProveedor = from.fetch("facturaProveedor", JoinType.LEFT);
/*  93:102 */     facturaProveedor.fetch("empresa", JoinType.LEFT);
/*  94:103 */     facturaProveedor.fetch("documento", JoinType.LEFT);
/*  95:104 */     from.fetch("moneda", JoinType.LEFT);
/*  96:105 */     from.fetch("pais", JoinType.LEFT);
/*  97:    */     
/*  98:107 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  99:108 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 100:    */     
/* 101:110 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 102:    */     
/* 103:112 */     CriteriaQuery<FacturaProveedorImportacion> select = criteriaQuery.select(from);
/* 104:113 */     TypedQuery<FacturaProveedorImportacion> typedQuery = this.em.createQuery(select);
/* 105:    */     
/* 106:115 */     return typedQuery.getResultList();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public FacturaProveedorImportacion cargarDetalle(int idFacturaProveedorImportacion)
/* 110:    */   {
/* 111:126 */     FacturaProveedorImportacion facturaProveedorImportacion = (FacturaProveedorImportacion)buscarPorId(Integer.valueOf(idFacturaProveedorImportacion));
/* 112:127 */     if (facturaProveedorImportacion.getMoneda() != null) {
/* 113:128 */       facturaProveedorImportacion.getMoneda().getId();
/* 114:    */     }
/* 115:130 */     facturaProveedorImportacion.getCuentaContableImportacion().getId();
/* 116:131 */     facturaProveedorImportacion.getPais().getId();
/* 117:    */     
/* 118:133 */     facturaProveedorImportacion.getFacturaProveedor().getId();
/* 119:134 */     facturaProveedorImportacion.getFacturaProveedor().getEmpresa().getId();
/* 120:135 */     facturaProveedorImportacion.getFacturaProveedor().getDocumento().getId();
/* 121:136 */     facturaProveedorImportacion.getFacturaProveedor().getListaDetalleFacturaProveedor().size();
/* 122:138 */     if (facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor() != null)
/* 123:    */     {
/* 124:139 */       facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor().getIdRecepcionProveedor();
/* 125:141 */       for (DetalleRecepcionProveedor drp : facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor()
/* 126:142 */         .getListaDetalleRecepcionProveedor())
/* 127:    */       {
/* 128:143 */         drp.getProducto().getIdProducto();
/* 129:144 */         drp.getBodega().getIdBodega();
/* 130:145 */         drp.getInventarioProducto().getIdInventarioProducto();
/* 131:146 */         if (drp.getInventarioProducto().getLote() != null) {
/* 132:147 */           drp.getInventarioProducto().getLote().getIdLote();
/* 133:    */         }
/* 134:    */       }
/* 135:    */     }
/* 136:153 */     if (facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor() != null)
/* 137:    */     {
/* 138:154 */       facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor().getId();
/* 139:155 */       facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor().getAsiento().getId();
/* 140:156 */       facturaProveedorImportacion.getFacturaProveedor().getRecepcionProveedor().getListaDetalleRecepcionProveedor().size();
/* 141:    */     }
/* 142:159 */     for (DetalleFacturaProveedor detalleFacturaProveedor : facturaProveedorImportacion.getFacturaProveedor().getListaDetalleFacturaProveedor())
/* 143:    */     {
/* 144:160 */       detalleFacturaProveedor.getProducto().getId();
/* 145:161 */       detalleFacturaProveedor.getProducto().getUnidad().getId();
/* 146:162 */       detalleFacturaProveedor.getProducto().getUnidadCompra().getNombre();
/* 147:164 */       if (detalleFacturaProveedor.getProducto().getBodegaCompra() != null) {
/* 148:165 */         detalleFacturaProveedor.getProducto().getBodegaCompra().getId();
/* 149:    */       }
/* 150:167 */       if (detalleFacturaProveedor.getProducto().getSubcategoriaProducto() != null) {
/* 151:168 */         detalleFacturaProveedor.getProducto().getSubcategoriaProducto().getId();
/* 152:    */       }
/* 153:169 */       if (detalleFacturaProveedor.getPartidaArancelaria() != null) {
/* 154:170 */         detalleFacturaProveedor.getPartidaArancelaria().getId();
/* 155:    */       }
/* 156:173 */       if (detalleFacturaProveedor.getDetallePedidoProveedor() != null)
/* 157:    */       {
/* 158:174 */         detalleFacturaProveedor.getDetallePedidoProveedor().getId();
/* 159:175 */         detalleFacturaProveedor.getDetallePedidoProveedor().getPedidoProveedor().getId();
/* 160:    */       }
/* 161:    */     }
/* 162:179 */     facturaProveedorImportacion.getListaDetalleFacturaProveedorImportacionGasto().size();
/* 163:180 */     for (DetalleFacturaProveedorImportacionGasto dfpig : facturaProveedorImportacion.getListaDetalleFacturaProveedorImportacionGasto())
/* 164:    */     {
/* 165:181 */       dfpig.getGastoImportacion().getId();
/* 166:182 */       if (dfpig.getMoneda() != null) {
/* 167:183 */         dfpig.getMoneda().getId();
/* 168:    */       }
/* 169:    */     }
/* 170:187 */     facturaProveedorImportacion.getListaDetalleProcesoImportacion().size();
/* 171:188 */     for (DetalleProcesoImportacion dpi : facturaProveedorImportacion.getListaDetalleProcesoImportacion()) {
/* 172:189 */       dpi.getProcesoImportacion().getId();
/* 173:    */     }
/* 174:192 */     facturaProveedorImportacion.getFacturaProveedor().getListaDetalleFacturaProveedorImportacion().size();
/* 175:193 */     for (DetalleFacturaProveedorImportacion dfpi : facturaProveedorImportacion.getFacturaProveedor()
/* 176:194 */       .getListaDetalleFacturaProveedorImportacion())
/* 177:    */     {
/* 178:195 */       dfpi.getDetalleFacturaProveedor().getId();
/* 179:196 */       dfpi.getDetalleFacturaProveedor().getProducto().getId();
/* 180:197 */       dfpi.getDetalleFacturaProveedor().getUnidadCompra().getId();
/* 181:198 */       dfpi.getDetalleFacturaProveedor().getUnidadCompra().getNombre();
/* 182:199 */       dfpi.getDetalleFacturaProveedor().getFacturaProveedor().getId();
/* 183:    */     }
/* 184:201 */     if (facturaProveedorImportacion.getAsiento() != null) {
/* 185:202 */       facturaProveedorImportacion.getAsiento().getId();
/* 186:    */     }
/* 187:205 */     return facturaProveedorImportacion;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public List getReporteFacturasProveedorImportacionPorLiquidar()
/* 191:    */     throws ExcepcionAS2
/* 192:    */   {
/* 193:215 */     String sql = "SELECT fp.numero, e.nombreFiscal, fpi.puertoEmbarque, fpi.puertoLlegada, p.nombre, fpi.estado FROM FacturaProveedorImportacion fpi  INNER JOIN fpi.facturaProveedor fp  INNER JOIN fp.empresa e  INNER JOIN fpi.pais p WHERE fpi.estado IN (:elaborado,:procesado)";
/* 194:    */     
/* 195:    */ 
/* 196:218 */     Query query = this.em.createQuery(sql).setParameter("elaborado", Estado.ELABORADO).setParameter("procesado", Estado.PROCESADO);
/* 197:219 */     return query.getResultList();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public List getReporteFacturaProveedorImportacionGasto(Date fechaDesde, Date fechaHasta, Empresa proveedor, Pais paisOrigen)
/* 201:    */   {
/* 202:233 */     String sql = "SELECT fp.numero, fp.fecha, e.nombreFiscal, fpi.fechaEmbarque,fpi.puertoEmbarque,fpi.fechaLlegada,fpi.puertoLlegada,fpi.medioTransporteEnum, p.nombre, fpi.estado, gi.nombre, dfpig.tipoProrrateoEnum ,dfpig.valorPresupuesto,dfpig.valorReal FROM DetalleFacturaProveedorImportacionGasto dfpig INNER JOIN dfpig.facturaProveedorImportacion fpi INNER JOIN fpi.facturaProveedor fp INNER JOIN fp.empresa e INNER JOIN fpi.pais p INNER JOIN dfpig.gastoImportacion gi WHERE fp.fecha BETWEEN :fechaDesde AND :fechaHasta AND (:proveedor IS NULL OR e = :proveedor) AND (:paisOrigen IS NULL OR p = :paisOrigen)";
/* 203:    */     
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:239 */     Query query = this.em.createQuery(sql).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).setParameter("proveedor", proveedor).setParameter("paisOrigen", paisOrigen);
/* 209:240 */     return query.getResultList();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List getReporteFacturaProveedorImportacionGastoDetallado(Date fechaDesde, Date fechaHasta, Empresa proveedor)
/* 213:    */   {
/* 214:245 */     String sql = " SELECT fp.numero, fp.fecha, e.nombreFiscal,(SELECT fpi.puertoEmbarque FROM FacturaProveedorImportacion fpi INNER JOIN fpi.facturaProveedor fpIn WHERE fpIn = fp), pr.codigo, pr.nombre, dfp.valorPresupuestoImportacion, dfp.valorRealImportacion FROM DetalleFacturaProveedor dfp INNER JOIN dfp.facturaProveedor fp INNER JOIN fp.empresa e INNER JOIN fp.documento d INNER JOIN dfp.producto pr WHERE d.indicadorDocumentoExterior = true AND fp.fecha BETWEEN :fechaDesde AND :fechaHasta AND (:proveedor IS NULL OR e = :proveedor)";
/* 215:    */     
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:251 */     Query query = this.em.createQuery(sql).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).setParameter("proveedor", proveedor);
/* 221:    */     
/* 222:253 */     return query.getResultList();
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List<DetalleFacturaProveedorImportacion> cargarFacturasProveedor(int idFacturaProveedor)
/* 226:    */   {
/* 227:258 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 228:259 */     CriteriaQuery<DetalleFacturaProveedorImportacion> cqDetalle = criteriaBuilder.createQuery(DetalleFacturaProveedorImportacion.class);
/* 229:260 */     Root<DetalleFacturaProveedorImportacion> from = cqDetalle.from(DetalleFacturaProveedorImportacion.class);
/* 230:    */     
/* 231:262 */     Fetch<Object, Object> facturaProveedor = from.fetch("facturaProveedor", JoinType.LEFT);
/* 232:263 */     facturaProveedor.fetch("empresa", JoinType.LEFT);
/* 233:264 */     facturaProveedor.fetch("facturaProveedorImportacion", JoinType.LEFT);
/* 234:    */     
/* 235:266 */     Path<Integer> pathIdDetalle = from.join("facturaProveedor").get("idFacturaProveedor");
/* 236:267 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idFacturaProveedor)));
/* 237:268 */     CriteriaQuery<DetalleFacturaProveedorImportacion> select = cqDetalle.select(from);
/* 238:    */     
/* 239:270 */     return this.em.createQuery(select).getResultList();
/* 240:    */   }
/* 241:    */   
/* 242:    */   public boolean tieneGastoFacturaExterior(int idFacturaProveedor)
/* 243:    */   {
/* 244:274 */     StringBuilder sql = new StringBuilder();
/* 245:275 */     sql.append("SELECT gi");
/* 246:276 */     sql.append(" FROM DetalleFacturaProveedorImportacionGasto dfpig");
/* 247:277 */     sql.append(" INNER JOIN dfpig.gastoImportacion gi");
/* 248:278 */     sql.append(" INNER JOIN dfpig.facturaProveedorImportacion fpi");
/* 249:279 */     sql.append(" INNER JOIN fpi.facturaProveedor fp");
/* 250:280 */     sql.append(" WHERE fp.idFacturaProveedor = :idFacturaProveedor");
/* 251:281 */     sql.append(" AND gi.indicadorFacturaExterior = true");
/* 252:    */     
/* 253:283 */     Query query = this.em.createQuery(sql.toString()).setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 254:284 */     return query.getResultList().size() > 0;
/* 255:    */   }
/* 256:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.FacturaProveedorImportacionDao
 * JD-Core Version:    0.7.0.1
 */