/*    1:     */ package com.asinfo.as2.dao;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*    4:     */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*    5:     */ import com.asinfo.as2.entities.Bodega;
/*    6:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*    7:     */ import com.asinfo.as2.entities.CuentaPorPagar;
/*    8:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*    9:     */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacion;
/*   10:     */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionGasto;
/*   11:     */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionProducto;
/*   12:     */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   13:     */ import com.asinfo.as2.entities.DetalleProcesoImportacion;
/*   14:     */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   15:     */ import com.asinfo.as2.entities.DimensionContable;
/*   16:     */ import com.asinfo.as2.entities.Documento;
/*   17:     */ import com.asinfo.as2.entities.Empresa;
/*   18:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   19:     */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   20:     */ import com.asinfo.as2.entities.GastoProductoFacturaProveedor;
/*   21:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor;
/*   22:     */ import com.asinfo.as2.entities.Pago;
/*   23:     */ import com.asinfo.as2.entities.PedidoProveedor;
/*   24:     */ import com.asinfo.as2.entities.PersonaResponsable;
/*   25:     */ import com.asinfo.as2.entities.Producto;
/*   26:     */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   27:     */ import com.asinfo.as2.entities.RegistroPeso;
/*   28:     */ import com.asinfo.as2.entities.Sucursal;
/*   29:     */ import com.asinfo.as2.entities.Unidad;
/*   30:     */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*   31:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   32:     */ import com.asinfo.as2.entities.sri.ReembolsoGastos;
/*   33:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   34:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   35:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   36:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   37:     */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*   38:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   39:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   40:     */ import java.math.BigDecimal;
/*   41:     */ import java.util.ArrayList;
/*   42:     */ import java.util.Date;
/*   43:     */ import java.util.HashMap;
/*   44:     */ import java.util.List;
/*   45:     */ import java.util.Map;
/*   46:     */ import javax.ejb.Stateless;
/*   47:     */ import javax.persistence.EntityManager;
/*   48:     */ import javax.persistence.NoResultException;
/*   49:     */ import javax.persistence.Query;
/*   50:     */ import javax.persistence.TemporalType;
/*   51:     */ import javax.persistence.TypedQuery;
/*   52:     */ import javax.persistence.criteria.CriteriaBuilder;
/*   53:     */ import javax.persistence.criteria.CriteriaQuery;
/*   54:     */ import javax.persistence.criteria.Expression;
/*   55:     */ import javax.persistence.criteria.Fetch;
/*   56:     */ import javax.persistence.criteria.Join;
/*   57:     */ import javax.persistence.criteria.JoinType;
/*   58:     */ import javax.persistence.criteria.Path;
/*   59:     */ import javax.persistence.criteria.Predicate;
/*   60:     */ import javax.persistence.criteria.Root;
/*   61:     */ import javax.persistence.criteria.Selection;
/*   62:     */ 
/*   63:     */ @Stateless
/*   64:     */ public class FacturaProveedorDao
/*   65:     */   extends AbstractDaoAS2<FacturaProveedor>
/*   66:     */ {
/*   67:     */   public FacturaProveedorDao()
/*   68:     */   {
/*   69:  62 */     super(FacturaProveedor.class);
/*   70:     */   }
/*   71:     */   
/*   72:     */   public List<FacturaProveedor> obtenerListaComboConEqual(String sortField, boolean sortOrder, Map<String, String> filters)
/*   73:     */   {
/*   74:  73 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   75:  74 */     CriteriaQuery<FacturaProveedor> criteriaQuery = criteriaBuilder.createQuery(FacturaProveedor.class);
/*   76:  75 */     Root<FacturaProveedor> from = criteriaQuery.from(FacturaProveedor.class);
/*   77:  76 */     from.fetch("documento", JoinType.LEFT);
/*   78:     */     
/*   79:  78 */     String numero = (String)filters.get("numero");
/*   80:  79 */     filters.remove("numero");
/*   81:  80 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*   82:  82 */     if (numero != null) {
/*   83:  83 */       expresiones.add(criteriaBuilder.equal(from.get("numero"), numero));
/*   84:     */     }
/*   85:  86 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*   86:     */     
/*   87:  88 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*   88:     */     
/*   89:  90 */     CriteriaQuery<FacturaProveedor> select = criteriaQuery.select(from);
/*   90:  91 */     TypedQuery<FacturaProveedor> typedQuery = this.em.createQuery(select);
/*   91:     */     
/*   92:  93 */     return typedQuery.getResultList();
/*   93:     */   }
/*   94:     */   
/*   95:     */   public List<FacturaProveedor> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*   96:     */   {
/*   97: 106 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   98: 107 */     CriteriaQuery<FacturaProveedor> criteriaQuery = criteriaBuilder.createQuery(FacturaProveedor.class);
/*   99: 108 */     Root<FacturaProveedor> from = criteriaQuery.from(FacturaProveedor.class);
/*  100: 109 */     from.fetch("asiento", JoinType.LEFT);
/*  101: 110 */     from.fetch("documento", JoinType.LEFT);
/*  102: 111 */     from.fetch("empresa", JoinType.LEFT);
/*  103: 112 */     from.fetch("facturaProveedorSRI", JoinType.LEFT);
/*  104: 113 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  105: 114 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  106: 115 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  107: 116 */     CriteriaQuery<FacturaProveedor> select = criteriaQuery.select(from);
/*  108: 117 */     TypedQuery<FacturaProveedor> typedQuery = this.em.createQuery(select);
/*  109: 118 */     agregarPaginacion(0, 20, typedQuery);
/*  110: 119 */     return typedQuery.getResultList();
/*  111:     */   }
/*  112:     */   
/*  113:     */   public List<FacturaProveedor> obtenerListaComboAutocompletar(int idEmpresa, String cadena)
/*  114:     */   {
/*  115: 125 */     StringBuilder sql = new StringBuilder();
/*  116: 126 */     sql.append(" SELECT fp FROM FacturaProveedor fp ");
/*  117: 127 */     sql.append(" INNER JOIN fp.empresa em ");
/*  118: 128 */     sql.append(" LEFT  JOIN fp.facturaProveedorSRI fps ");
/*  119: 129 */     sql.append(" WHERE em.idEmpresa = :idEmpresa");
/*  120: 130 */     sql.append(" AND fp.documento.documentoBase = :documentoBase");
/*  121: 131 */     sql.append(" AND fp.estado != :estado");
/*  122: 132 */     sql.append(" AND ( fps.numero LIKE :cadena OR fp.numero LIKE :cadena OR fp.referencia3 LIKE :cadena)");
/*  123: 133 */     sql.append(" ORDER BY fp.fecha DESC");
/*  124:     */     
/*  125: 135 */     Query query = this.em.createQuery(sql.toString());
/*  126: 136 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  127: 137 */     query.setParameter("estado", Estado.ANULADO);
/*  128: 138 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_PROVEEDOR);
/*  129: 139 */     cadena = cadena == null ? "" : cadena;
/*  130: 140 */     query.setParameter("cadena", "%" + cadena + "%");
/*  131:     */     
/*  132: 142 */     return query.getResultList();
/*  133:     */   }
/*  134:     */   
/*  135:     */   public FacturaProveedor cargarDetalle(int idFacturaProveedor)
/*  136:     */   {
/*  137: 146 */     return cargarDetalle(idFacturaProveedor, true);
/*  138:     */   }
/*  139:     */   
/*  140:     */   public FacturaProveedor cargarDetalle(int idFacturaProveedor, boolean cargarDetalles)
/*  141:     */   {
/*  142: 151 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  143:     */     
/*  144: 153 */     CriteriaQuery<FacturaProveedor> cqCabecera = criteriaBuilder.createQuery(FacturaProveedor.class);
/*  145: 154 */     Root<FacturaProveedor> fromCabecera = cqCabecera.from(FacturaProveedor.class);
/*  146:     */     
/*  147: 156 */     fromCabecera.fetch("sucursal", JoinType.LEFT).fetch("ubicacion", JoinType.LEFT);
/*  148: 157 */     fromCabecera.fetch("condicionPago", JoinType.LEFT);
/*  149:     */     
/*  150: 159 */     fromCabecera.fetch("tipoOperacion", JoinType.LEFT);
/*  151: 160 */     fromCabecera.fetch("proyecto", JoinType.LEFT);
/*  152: 161 */     fromCabecera.fetch("asiento", JoinType.LEFT);
/*  153: 162 */     fromCabecera.fetch("recepcionProveedor", JoinType.LEFT);
/*  154: 163 */     fromCabecera.fetch("anticipoProveedor", JoinType.LEFT);
/*  155:     */     
/*  156: 165 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*  157: 166 */     documento.fetch("secuencia", JoinType.LEFT);
/*  158: 167 */     documento.fetch("tipoAsiento", JoinType.LEFT);
/*  159: 168 */     documento.fetch("tipoComprobanteSRI", JoinType.LEFT);
/*  160:     */     
/*  161: 170 */     Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.LEFT);
/*  162: 171 */     empresa.fetch("proveedor", JoinType.LEFT);
/*  163: 172 */     empresa.fetch("empleado", JoinType.LEFT);
/*  164: 173 */     empresa.fetch("cliente", JoinType.LEFT);
/*  165:     */     
/*  166: 175 */     Fetch<Object, Object> facturaProveedorSRI = fromCabecera.fetch("facturaProveedorSRI", JoinType.LEFT);
/*  167: 176 */     facturaProveedorSRI.fetch("facturaClienteSRI", JoinType.LEFT);
/*  168: 177 */     facturaProveedorSRI.fetch("tipoComprobanteSRI", JoinType.LEFT);
/*  169: 178 */     facturaProveedorSRI.fetch("tipoIdentificacion", JoinType.LEFT);
/*  170: 179 */     facturaProveedorSRI.fetch("creditoTributarioSRI", JoinType.LEFT);
/*  171: 180 */     facturaProveedorSRI.fetch("documento", JoinType.LEFT);
/*  172: 181 */     facturaProveedorSRI.fetch("pago", JoinType.LEFT);
/*  173:     */     
/*  174: 183 */     Fetch<Object, Object> facturaProveedorPadre = fromCabecera.fetch("facturaProveedorPadre", JoinType.LEFT);
/*  175: 184 */     facturaProveedorPadre.fetch("empresa", JoinType.LEFT);
/*  176: 185 */     facturaProveedorPadre.fetch("documento", JoinType.LEFT);
/*  177:     */     
/*  178: 187 */     Fetch<Object, Object> direccion = fromCabecera.fetch("direccionEmpresa", JoinType.LEFT);
/*  179: 188 */     direccion.fetch("ubicacion", JoinType.LEFT);
/*  180:     */     
/*  181: 190 */     Fetch<Object, Object> facturaProveedorImportacion = fromCabecera.fetch("facturaProveedorImportacion", JoinType.LEFT);
/*  182: 191 */     facturaProveedorImportacion.fetch("pais", JoinType.LEFT);
/*  183: 192 */     facturaProveedorImportacion.fetch("moneda", JoinType.LEFT);
/*  184: 193 */     facturaProveedorImportacion.fetch("cuentaContableImportacion", JoinType.LEFT);
/*  185: 194 */     facturaProveedorImportacion.fetch("cuentaContableImportacionDiferenciaEnMasOEnMenos", JoinType.LEFT);
/*  186:     */     
/*  187: 196 */     Path<Integer> pathId = fromCabecera.get("idFacturaProveedor");
/*  188: 197 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idFacturaProveedor)));
/*  189: 198 */     CriteriaQuery<FacturaProveedor> selectFactura = cqCabecera.select(fromCabecera);
/*  190: 199 */     FacturaProveedor facturaProveedor = (FacturaProveedor)this.em.createQuery(selectFactura).getSingleResult();
/*  191:     */     CriteriaQuery<DetalleFacturaProveedorImportacionGasto> cqDetalleFacturaProveedorImportacionGasto;
/*  192:     */     List<DetalleProcesoImportacion> listaDetalleProcesoImportacion;
/*  193: 201 */     if (cargarDetalles)
/*  194:     */     {
/*  195: 203 */       this.em.detach(facturaProveedor);
/*  196:     */       
/*  197:     */ 
/*  198: 206 */       CriteriaQuery<CuentaPorPagar> cqCXP = criteriaBuilder.createQuery(CuentaPorPagar.class);
/*  199: 207 */       Root<CuentaPorPagar> fromCXP = cqCXP.from(CuentaPorPagar.class);
/*  200:     */       
/*  201: 209 */       cqCXP.where(criteriaBuilder.equal(fromCXP.join("facturaProveedor"), facturaProveedor));
/*  202: 210 */       CriteriaQuery<CuentaPorPagar> selectCXP = cqCXP.select(fromCXP);
/*  203:     */       
/*  204: 212 */       List<CuentaPorPagar> listaCXP = this.em.createQuery(selectCXP).getResultList();
/*  205: 213 */       for (CuentaPorPagar cxp : listaCXP)
/*  206:     */       {
/*  207: 214 */         this.em.detach(cxp);
/*  208: 215 */         cxp.setFacturaProveedor(facturaProveedor);
/*  209:     */       }
/*  210: 217 */       facturaProveedor.setListaCuentaPorPagar(listaCXP);
/*  211:     */       
/*  212:     */ 
/*  213:     */ 
/*  214: 221 */       Object cqDetalle = criteriaBuilder.createQuery(DetalleFacturaProveedor.class);
/*  215: 222 */       Root<DetalleFacturaProveedor> fromDetalle = ((CriteriaQuery)cqDetalle).from(DetalleFacturaProveedor.class);
/*  216: 223 */       fromDetalle.fetch("unidadCompra", JoinType.LEFT);
/*  217: 224 */       fromDetalle.fetch("inventarioProducto", JoinType.LEFT);
/*  218: 225 */       fromDetalle.fetch("bodega", JoinType.LEFT);
/*  219: 226 */       fromDetalle.fetch("partidaArancelaria", JoinType.LEFT);
/*  220: 227 */       fromDetalle.fetch("gastoImportacion", JoinType.LEFT);
/*  221:     */       
/*  222: 229 */       Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  223: 230 */       producto.fetch("unidad", JoinType.LEFT);
/*  224: 231 */       producto.fetch("categoriaImpuesto", JoinType.LEFT);
/*  225: 232 */       producto.fetch("subcategoriaProducto", JoinType.LEFT);
/*  226:     */       
/*  227:     */ 
/*  228: 235 */       fromDetalle.fetch("detallePedidoProveedor", JoinType.LEFT).fetch("pedidoProveedor", JoinType.LEFT);
/*  229:     */       
/*  230: 237 */       ((CriteriaQuery)cqDetalle).where(criteriaBuilder.equal(fromDetalle.join("facturaProveedor"), facturaProveedor));
/*  231: 238 */       CriteriaQuery<DetalleFacturaProveedor> selectDetalleFactura = ((CriteriaQuery)cqDetalle).select(fromDetalle);
/*  232:     */       
/*  233: 240 */       List<DetalleFacturaProveedor> listaDetalleFacturaProveedor = this.em.createQuery(selectDetalleFactura).getResultList();
/*  234: 241 */       facturaProveedor.setListaDetalleFacturaProveedor(listaDetalleFacturaProveedor);
/*  235: 243 */       if (facturaProveedor.getFacturaProveedorSRI() != null)
/*  236:     */       {
/*  237: 244 */         this.em.detach(facturaProveedor.getFacturaProveedorSRI());
/*  238:     */         
/*  239:     */ 
/*  240:     */ 
/*  241: 248 */         CriteriaQuery<DetalleFacturaProveedorSRI> cqDetalleFacturaProveedorSRI = criteriaBuilder.createQuery(DetalleFacturaProveedorSRI.class);
/*  242: 249 */         Root<DetalleFacturaProveedorSRI> fromDetalleFacturaProveedorSRI = cqDetalleFacturaProveedorSRI.from(DetalleFacturaProveedorSRI.class);
/*  243:     */         
/*  244: 251 */         fromDetalleFacturaProveedorSRI.fetch("conceptoRetencionSRI", JoinType.LEFT);
/*  245:     */         
/*  246: 253 */         Path<Integer> pathIdFacturaProveedorSRI = fromDetalleFacturaProveedorSRI.join("facturaProveedorSRI").get("idFacturaProveedorSRI");
/*  247: 254 */         cqDetalleFacturaProveedorSRI.where(criteriaBuilder
/*  248: 255 */           .equal(pathIdFacturaProveedorSRI, Integer.valueOf(facturaProveedor.getFacturaProveedorSRI().getIdFacturaProveedorSRI())));
/*  249:     */         
/*  250:     */ 
/*  251: 258 */         CriteriaQuery<DetalleFacturaProveedorSRI> selectDetalleFacturaProveedorSRI = cqDetalleFacturaProveedorSRI.select(fromDetalleFacturaProveedorSRI);
/*  252:     */         
/*  253: 260 */         List<DetalleFacturaProveedorSRI> listaDetalleFacturaProveedorSRI = this.em.createQuery(selectDetalleFacturaProveedorSRI).getResultList();
/*  254:     */         
/*  255: 262 */         facturaProveedor.getFacturaProveedorSRI().setListaDetalleFacturaProveedorSRI(listaDetalleFacturaProveedorSRI);
/*  256:     */         
/*  257:     */ 
/*  258: 265 */         CriteriaQuery<ReembolsoGastos> cqReembolsoGastos = criteriaBuilder.createQuery(ReembolsoGastos.class);
/*  259: 266 */         Root<ReembolsoGastos> fromReembolsoGastos = cqReembolsoGastos.from(ReembolsoGastos.class);
/*  260:     */         
/*  261: 268 */         fromReembolsoGastos.fetch("tipoIdentificacion", JoinType.LEFT);
/*  262: 269 */         fromReembolsoGastos.fetch("tipoComprobanteSRI", JoinType.LEFT);
/*  263:     */         
/*  264: 271 */         pathIdFacturaProveedorSRI = fromReembolsoGastos.join("facturaProveedorSRI").get("idFacturaProveedorSRI");
/*  265: 272 */         cqReembolsoGastos.where(criteriaBuilder
/*  266: 273 */           .equal(pathIdFacturaProveedorSRI, Integer.valueOf(facturaProveedor.getFacturaProveedorSRI().getIdFacturaProveedorSRI())));
/*  267:     */         
/*  268: 275 */         CriteriaQuery<ReembolsoGastos> selectReembolsoGastos = cqReembolsoGastos.select(fromReembolsoGastos);
/*  269:     */         
/*  270: 277 */         List<ReembolsoGastos> listaReembolsoGastos = this.em.createQuery(selectReembolsoGastos).getResultList();
/*  271:     */         
/*  272: 279 */         facturaProveedor.getFacturaProveedorSRI().setListaReembolsoGastos(listaReembolsoGastos);
/*  273:     */       }
/*  274: 282 */       if (facturaProveedor.getFacturaProveedorImportacion() != null)
/*  275:     */       {
/*  276: 284 */         this.em.detach(facturaProveedor.getFacturaProveedorImportacion());
/*  277:     */         
/*  278:     */ 
/*  279:     */ 
/*  280: 288 */         cqDetalleFacturaProveedorImportacionGasto = criteriaBuilder.createQuery(DetalleFacturaProveedorImportacionGasto.class);
/*  281:     */         
/*  282: 290 */         Root<DetalleFacturaProveedorImportacionGasto> fromDetalleFacturaProveedorImportacionGasto = cqDetalleFacturaProveedorImportacionGasto.from(DetalleFacturaProveedorImportacionGasto.class);
/*  283:     */         
/*  284: 292 */         fromDetalleFacturaProveedorImportacionGasto.fetch("gastoImportacion", JoinType.LEFT);
/*  285: 293 */         fromDetalleFacturaProveedorImportacionGasto.fetch("moneda", JoinType.LEFT);
/*  286:     */         
/*  287:     */ 
/*  288: 296 */         Path<Integer> pathIdFacturaProveedorImportacion = fromDetalleFacturaProveedorImportacionGasto.join("facturaProveedorImportacion").get("idFacturaProveedorImportacion");
/*  289: 297 */         cqDetalleFacturaProveedorImportacionGasto.where(criteriaBuilder.equal(pathIdFacturaProveedorImportacion, 
/*  290: 298 */           Integer.valueOf(facturaProveedor.getFacturaProveedorImportacion().getIdFacturaProveedorImportacion())));
/*  291:     */         
/*  292:     */ 
/*  293: 301 */         CriteriaQuery<DetalleFacturaProveedorImportacionGasto> selectDetalleFacturaProveedorImportacionGasto = cqDetalleFacturaProveedorImportacionGasto.select(fromDetalleFacturaProveedorImportacionGasto);
/*  294:     */         
/*  295:     */ 
/*  296: 304 */         List<DetalleFacturaProveedorImportacionGasto> listaDetalleFacturaProveedorImportacionGasto = this.em.createQuery(selectDetalleFacturaProveedorImportacionGasto).getResultList();
/*  297:     */         
/*  298: 306 */         facturaProveedor.getFacturaProveedorImportacion()
/*  299: 307 */           .setListaDetalleFacturaProveedorImportacionGasto(listaDetalleFacturaProveedorImportacionGasto);
/*  300:     */         
/*  301:     */ 
/*  302: 310 */         CriteriaQuery<DetalleProcesoImportacion> cqDetalleProcesoImportacion = criteriaBuilder.createQuery(DetalleProcesoImportacion.class);
/*  303: 311 */         Root<DetalleProcesoImportacion> fromDetalleProcesoImportacion = cqDetalleProcesoImportacion.from(DetalleProcesoImportacion.class);
/*  304:     */         
/*  305: 313 */         fromDetalleProcesoImportacion.fetch("procesoImportacion", JoinType.LEFT);
/*  306:     */         
/*  307: 315 */         pathIdFacturaProveedorImportacion = fromDetalleProcesoImportacion.join("facturaProveedorImportacion").get("idFacturaProveedorImportacion");
/*  308: 316 */         cqDetalleProcesoImportacion.where(criteriaBuilder.equal(pathIdFacturaProveedorImportacion, 
/*  309: 317 */           Integer.valueOf(facturaProveedor.getFacturaProveedorImportacion().getIdFacturaProveedorImportacion())));
/*  310:     */         
/*  311:     */ 
/*  312: 320 */         CriteriaQuery<DetalleProcesoImportacion> selectDetalleProcesoImportacion = cqDetalleProcesoImportacion.select(fromDetalleProcesoImportacion);
/*  313:     */         
/*  314: 322 */         listaDetalleProcesoImportacion = this.em.createQuery(selectDetalleProcesoImportacion).getResultList();
/*  315:     */         
/*  316: 324 */         facturaProveedor.getFacturaProveedorImportacion().setListaDetalleProcesoImportacion(listaDetalleProcesoImportacion);
/*  317:     */       }
/*  318: 328 */       for (DetalleFacturaProveedor detalleFacturaProveedor : listaDetalleFacturaProveedor)
/*  319:     */       {
/*  320: 330 */         this.em.detach(detalleFacturaProveedor);
/*  321: 331 */         detalleFacturaProveedor.setFacturaProveedor(facturaProveedor);
/*  322: 332 */         Integer idDetalleFacturaProveedor = Integer.valueOf(detalleFacturaProveedor.getId());
/*  323:     */         
/*  324: 334 */         CriteriaQuery<ImpuestoProductoFacturaProveedor> cqImpuesto = criteriaBuilder.createQuery(ImpuestoProductoFacturaProveedor.class);
/*  325: 335 */         Root<ImpuestoProductoFacturaProveedor> fromImpuesto = cqImpuesto.from(ImpuestoProductoFacturaProveedor.class);
/*  326:     */         
/*  327: 337 */         fromImpuesto.fetch("impuesto", JoinType.LEFT);
/*  328:     */         
/*  329: 339 */         Path<Integer> pathIdImpuesto = fromImpuesto.join("detalleFacturaProveedor").get("idDetalleFacturaProveedor");
/*  330: 340 */         cqImpuesto.where(criteriaBuilder.equal(pathIdImpuesto, idDetalleFacturaProveedor));
/*  331:     */         
/*  332: 342 */         CriteriaQuery<ImpuestoProductoFacturaProveedor> selectImpuesto = cqImpuesto.select(fromImpuesto);
/*  333:     */         
/*  334: 344 */         List<ImpuestoProductoFacturaProveedor> listaImpuestoProductoFacturaProveedor = this.em.createQuery(selectImpuesto).getResultList();
/*  335:     */         
/*  336: 346 */         detalleFacturaProveedor.setListaImpuestoProductoFacturaProveedor(listaImpuestoProductoFacturaProveedor);
/*  337: 347 */         for (ImpuestoProductoFacturaProveedor impuestoProductoFacturaProveedor : listaImpuestoProductoFacturaProveedor)
/*  338:     */         {
/*  339: 348 */           this.em.detach(impuestoProductoFacturaProveedor);
/*  340: 349 */           impuestoProductoFacturaProveedor.setDetalleFacturaProveedor(detalleFacturaProveedor);
/*  341:     */         }
/*  342: 352 */         CriteriaQuery<DetalleRecepcionProveedor> cqDetalleRecepcionProveedor = criteriaBuilder.createQuery(DetalleRecepcionProveedor.class);
/*  343: 353 */         Root<DetalleRecepcionProveedor> fromDetalleRecepcionProveedor = cqDetalleRecepcionProveedor.from(DetalleRecepcionProveedor.class);
/*  344:     */         
/*  345: 355 */         fromDetalleRecepcionProveedor.fetch("recepcionProveedor", JoinType.LEFT);
/*  346: 356 */         fromDetalleRecepcionProveedor.fetch("inventarioProducto", JoinType.LEFT);
/*  347:     */         
/*  348:     */ 
/*  349: 359 */         Path<Integer> pathIdDetalleRecepcionProveedor = fromDetalleRecepcionProveedor.join("detalleFacturaProveedor").get("idDetalleFacturaProveedor");
/*  350: 360 */         cqDetalleRecepcionProveedor.where(criteriaBuilder.equal(pathIdDetalleRecepcionProveedor, idDetalleFacturaProveedor));
/*  351:     */         
/*  352:     */ 
/*  353: 363 */         CriteriaQuery<DetalleRecepcionProveedor> selectDetalleRecepcionProveedor = cqDetalleRecepcionProveedor.select(fromDetalleRecepcionProveedor);
/*  354:     */         
/*  355: 365 */         List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor = this.em.createQuery(selectDetalleRecepcionProveedor).getResultList();
/*  356:     */         
/*  357: 367 */         detalleFacturaProveedor.setListaDetalleRecepcionProveedor(listaDetalleRecepcionProveedor);
/*  358:     */         
/*  359:     */ 
/*  360:     */ 
/*  361: 371 */         CriteriaQuery<GastoProductoFacturaProveedor> cqGastoProductoFacturaProveedor = criteriaBuilder.createQuery(GastoProductoFacturaProveedor.class);
/*  362:     */         
/*  363: 373 */         Root<GastoProductoFacturaProveedor> fromGastoProductoFacturaProveedor = cqGastoProductoFacturaProveedor.from(GastoProductoFacturaProveedor.class);
/*  364:     */         
/*  365: 375 */         fromGastoProductoFacturaProveedor.fetch("dimensionContable1", JoinType.LEFT);
/*  366: 376 */         fromGastoProductoFacturaProveedor.fetch("dimensionContable2", JoinType.LEFT);
/*  367: 377 */         fromGastoProductoFacturaProveedor.fetch("dimensionContable3", JoinType.LEFT);
/*  368: 378 */         fromGastoProductoFacturaProveedor.fetch("dimensionContable4", JoinType.LEFT);
/*  369: 379 */         fromGastoProductoFacturaProveedor.fetch("dimensionContable5", JoinType.LEFT);
/*  370:     */         
/*  371: 381 */         fromGastoProductoFacturaProveedor.fetch("cuentaContableGasto", JoinType.LEFT);
/*  372:     */         
/*  373:     */ 
/*  374: 384 */         Path<Integer> pathIdGastoProductoFacturaProveedor = fromGastoProductoFacturaProveedor.join("detalleFacturaProveedor").get("idDetalleFacturaProveedor");
/*  375: 385 */         cqGastoProductoFacturaProveedor.where(criteriaBuilder.equal(pathIdGastoProductoFacturaProveedor, idDetalleFacturaProveedor));
/*  376:     */         
/*  377:     */ 
/*  378: 388 */         CriteriaQuery<GastoProductoFacturaProveedor> selectGastoProductoFacturaProveedor = cqGastoProductoFacturaProveedor.select(fromGastoProductoFacturaProveedor);
/*  379:     */         
/*  380:     */ 
/*  381: 391 */         List<GastoProductoFacturaProveedor> listaGastoProductoFacturaProveedor = this.em.createQuery(selectGastoProductoFacturaProveedor).getResultList();
/*  382:     */         
/*  383: 393 */         detalleFacturaProveedor.setListaGastoProductoFacturaProveedor(listaGastoProductoFacturaProveedor);
/*  384: 395 */         for (GastoProductoFacturaProveedor gastoProductoFacturaProveedor : listaGastoProductoFacturaProveedor)
/*  385:     */         {
/*  386: 396 */           this.em.detach(gastoProductoFacturaProveedor);
/*  387: 397 */           gastoProductoFacturaProveedor.setDetalleFacturaProveedor(detalleFacturaProveedor);
/*  388:     */         }
/*  389: 402 */         Object cqDetalleFacturaProveedorImportacion = criteriaBuilder.createQuery(DetalleFacturaProveedorImportacion.class);
/*  390:     */         
/*  391: 404 */         Root<DetalleFacturaProveedorImportacion> fromDetalleFacturaProveedorImportacion = ((CriteriaQuery)cqDetalleFacturaProveedorImportacion).from(DetalleFacturaProveedorImportacion.class);
/*  392:     */         
/*  393: 406 */         fromDetalleFacturaProveedorImportacion.fetch("facturaProveedor", JoinType.LEFT);
/*  394:     */         
/*  395:     */ 
/*  396: 409 */         Path<Integer> pathIdDetalleFacturaProveedor = fromDetalleFacturaProveedorImportacion.join("detalleFacturaProveedor").get("idDetalleFacturaProveedor");
/*  397: 410 */         ((CriteriaQuery)cqDetalleFacturaProveedorImportacion).where(criteriaBuilder.equal(pathIdDetalleFacturaProveedor, idDetalleFacturaProveedor));
/*  398:     */         
/*  399:     */ 
/*  400: 413 */         CriteriaQuery<DetalleFacturaProveedorImportacion> selectDetalleFacturaProveedorImportacion = ((CriteriaQuery)cqDetalleFacturaProveedorImportacion).select(fromDetalleFacturaProveedorImportacion);
/*  401:     */         
/*  402:     */ 
/*  403: 416 */         List<DetalleFacturaProveedorImportacion> listaDetalleFacturaProveedorImportacion = this.em.createQuery(selectDetalleFacturaProveedorImportacion).getResultList();
/*  404:     */         
/*  405: 418 */         detalleFacturaProveedor.setListaDetalleFacturaProveedorImportacion(listaDetalleFacturaProveedorImportacion);
/*  406: 420 */         for (DetalleFacturaProveedorImportacion detalleFacturaProveedorImportacion : listaDetalleFacturaProveedorImportacion)
/*  407:     */         {
/*  408: 422 */           this.em.detach(detalleFacturaProveedorImportacion);
/*  409: 423 */           Integer idDetalleFacturaProveedorImportacion = Integer.valueOf(detalleFacturaProveedorImportacion.getId());
/*  410:     */           
/*  411:     */ 
/*  412: 426 */           CriteriaQuery<DetalleFacturaProveedorImportacionProducto> cqDetalleFacturaProveedorImportacionProducto = criteriaBuilder.createQuery(DetalleFacturaProveedorImportacionProducto.class);
/*  413:     */           
/*  414: 428 */           Root<DetalleFacturaProveedorImportacionProducto> fromDetalleFacturaProveedorImportacionProducto = cqDetalleFacturaProveedorImportacionProducto.from(DetalleFacturaProveedorImportacionProducto.class);
/*  415:     */           
/*  416: 430 */           fromDetalleFacturaProveedorImportacionProducto.fetch("detalleFacturaProveedor", JoinType.LEFT).fetch("producto", JoinType.LEFT);
/*  417:     */           
/*  418:     */ 
/*  419: 433 */           Path<Integer> pathIdDetalleFacturaProveedorImportacion = fromDetalleFacturaProveedorImportacionProducto.join("detalleFacturaProveedorImportacion").get("idDetalleFacturaProveedorImportacion");
/*  420: 434 */           cqDetalleFacturaProveedorImportacionProducto
/*  421: 435 */             .where(criteriaBuilder.equal(pathIdDetalleFacturaProveedorImportacion, idDetalleFacturaProveedorImportacion));
/*  422:     */           
/*  423:     */ 
/*  424: 438 */           CriteriaQuery<DetalleFacturaProveedorImportacionProducto> selectDetalleFacturaProveedorImportacionProducto = cqDetalleFacturaProveedorImportacionProducto.select(fromDetalleFacturaProveedorImportacionProducto);
/*  425:     */           
/*  426:     */ 
/*  427: 441 */           List<DetalleFacturaProveedorImportacionProducto> listaDetalleFacturaProveedorImportacionProducto = this.em.createQuery(selectDetalleFacturaProveedorImportacionProducto).getResultList();
/*  428:     */           
/*  429: 443 */           detalleFacturaProveedorImportacion
/*  430: 444 */             .setListaDetalleFacturaProveedorImportacionProducto(listaDetalleFacturaProveedorImportacionProducto);
/*  431:     */         }
/*  432: 446 */         facturaProveedor.setListaDetalleFacturaProveedorImportacion(listaDetalleFacturaProveedorImportacion);
/*  433:     */         
/*  434:     */ 
/*  435: 449 */         Object cqRegistroPeso = criteriaBuilder.createQuery(RegistroPeso.class);
/*  436: 450 */         Root<RegistroPeso> fromRegistroPeso = ((CriteriaQuery)cqRegistroPeso).from(RegistroPeso.class);
/*  437:     */         
/*  438: 452 */         fromRegistroPeso.fetch("transportista", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  439:     */         
/*  440: 454 */         Path<Integer> pathIdRegistroPeso = fromRegistroPeso.join("detalleFacturaProveedor").get("idDetalleFacturaProveedor");
/*  441: 455 */         ((CriteriaQuery)cqRegistroPeso).where(criteriaBuilder.equal(pathIdRegistroPeso, idDetalleFacturaProveedor));
/*  442:     */         
/*  443: 457 */         CriteriaQuery<RegistroPeso> selectRegistroPeso = ((CriteriaQuery)cqRegistroPeso).select(fromRegistroPeso);
/*  444:     */         
/*  445: 459 */         List<RegistroPeso> listaRegistroPeso = this.em.createQuery(selectRegistroPeso).getResultList();
/*  446:     */         
/*  447: 461 */         detalleFacturaProveedor.setListaRegistroPesoLiquidados(listaRegistroPeso);
/*  448:     */       }
/*  449:     */     }
/*  450: 467 */     return facturaProveedor;
/*  451:     */   }
/*  452:     */   
/*  453:     */   public List<DetalleFacturaProveedor> cargarDetalleFactura(int idFacturaProveedor)
/*  454:     */   {
/*  455: 479 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  456: 480 */     CriteriaQuery<DetalleFacturaProveedor> cq = criteriaBuilder.createQuery(DetalleFacturaProveedor.class);
/*  457: 481 */     Root<DetalleFacturaProveedor> from = cq.from(DetalleFacturaProveedor.class);
/*  458:     */     
/*  459: 483 */     from.fetch("detalleRecepcionProveedorDevolucion", JoinType.LEFT);
/*  460: 484 */     from.fetch("detallePedidoProveedor", JoinType.LEFT);
/*  461:     */     
/*  462: 486 */     Path<Integer> pathId = from.join("facturaProveedor").get("idFacturaProveedor");
/*  463: 487 */     cq.where(criteriaBuilder.equal(pathId, Integer.valueOf(idFacturaProveedor)));
/*  464: 488 */     CriteriaQuery<DetalleFacturaProveedor> select = cq.select(from);
/*  465:     */     
/*  466: 490 */     List<DetalleFacturaProveedor> lista = this.em.createQuery(select).getResultList();
/*  467: 491 */     for (DetalleFacturaProveedor dfp : lista) {
/*  468: 492 */       for (DetalleRecepcionProveedor drp : dfp.getListaDetalleRecepcionProveedor()) {
/*  469: 493 */         drp.getProducto().getIdProducto();
/*  470:     */       }
/*  471:     */     }
/*  472: 496 */     return lista;
/*  473:     */   }
/*  474:     */   
/*  475:     */   public FacturaProveedor cargarInformacionSRI(int idFacturaProveedor)
/*  476:     */   {
/*  477: 506 */     FacturaProveedor facturaProveedor = cargarDetalle(idFacturaProveedor, false);
/*  478: 507 */     facturaProveedor.getFacturaProveedorSRI().getListaDetalleFacturaProveedorSRI().size();
/*  479: 508 */     return facturaProveedor;
/*  480:     */   }
/*  481:     */   
/*  482:     */   public List<CuentaPorPagar> obtenerFacturasPendientes(int idOrganizacion, int idEmpresa, int idFacturaProveedor, Date fechaVencimiento, TipoServicioCuentaBancariaEnum tipoServicio, CategoriaEmpresa categoriaEmpresa, int idSucursal)
/*  483:     */   {
/*  484: 519 */     Boolean indicadorPagoCash = fechaVencimiento == null ? null : Boolean.valueOf(true);
/*  485: 520 */     return obtenerFacturasPendientes(idEmpresa, idFacturaProveedor, fechaVencimiento, tipoServicio, categoriaEmpresa, idSucursal, null, indicadorPagoCash, idOrganizacion, null, null);
/*  486:     */   }
/*  487:     */   
/*  488:     */   public List<CuentaPorPagar> obtenerFacturasPendientes(int idEmpresa, int idFacturaProveedor, Date fechaVencimientoHasta, TipoServicioCuentaBancariaEnum tipoServicio, CategoriaEmpresa categoriaEmpresa, int idSucursal, Documento documento, Boolean indicadorPagoCash, int idOrganizacion, Date fechaVencimientoDesde, Boolean indicadorCXPEnOPP)
/*  489:     */   {
/*  490: 535 */     StringBuilder sql = new StringBuilder();
/*  491: 536 */     sql.append(" SELECT cxp FROM CuentaPorPagar cxp ");
/*  492: 537 */     sql.append(" LEFT   JOIN FETCH cxp.facturaProveedor fp ");
/*  493: 538 */     sql.append(" LEFT   JOIN FETCH  fp.empresa em1 ");
/*  494: 539 */     sql.append(" LEFT   JOIN FETCH  em1.proveedor pro ");
/*  495: 540 */     sql.append(" LEFT   JOIN FETCH  pro.formaPago fpa ");
/*  496: 541 */     sql.append(" LEFT   JOIN FETCH  pro.empresa em2 ");
/*  497: 542 */     sql.append(" LEFT   JOIN FETCH em1.categoriaEmpresa cem ");
/*  498: 543 */     sql.append(" LEFT   JOIN FETCH em2.categoriaEmpresa cem2 ");
/*  499: 544 */     sql.append(" LEFT   JOIN FETCH fp.facturaProveedorSRI fps ");
/*  500: 545 */     sql.append(" LEFT   JOIN FETCH  fp.documento doc ");
/*  501: 546 */     sql.append(" WHERE  (fp.empresa.idEmpresa=:idEmpresa OR :idEmpresa = 0) ");
/*  502: 547 */     sql.append(" AND    cxp.saldo > 0 ");
/*  503: 548 */     sql.append(" AND    fp.estado<>:estado ");
/*  504: 549 */     sql.append(" AND    (fp.sucursal.idSucursal = :idSucursal OR :idSucursal = 0)");
/*  505: 550 */     sql.append(" AND    (fp.idFacturaProveedor=:idFacturaProveedor OR :idFacturaProveedor=0)  ");
/*  506: 551 */     sql.append(" AND    cxp.indicadorBloqueada = false ");
/*  507: 553 */     if (documento != null) {
/*  508: 554 */       sql.append(" AND doc = :documento ");
/*  509:     */     }
/*  510: 557 */     if (fechaVencimientoHasta != null) {
/*  511: 558 */       sql.append(" AND cxp.fechaVencimiento <= :fechaVencimientoHasta  ");
/*  512:     */     }
/*  513: 560 */     if (indicadorPagoCash != null) {
/*  514: 561 */       sql.append(" AND fp.empresa.proveedor.indicadorPagoCash =:indicadorPagoCash ");
/*  515:     */     }
/*  516: 563 */     if (tipoServicio != null)
/*  517:     */     {
/*  518: 564 */       sql.append(" AND EXISTS (");
/*  519: 565 */       sql.append("               SELECT 1 FROM  CuentaBancariaEmpresa cbem ");
/*  520: 566 */       sql.append("               JOIN cbem.cuentaBancaria cb ");
/*  521: 567 */       sql.append("               JOIN cbem.empresa em2 ");
/*  522: 568 */       sql.append("               WHERE cb.tipoServicioCuentaBancariaProveedor =:tipoServicio  ");
/*  523: 569 */       sql.append("               AND   em1 = em2 ");
/*  524: 570 */       sql.append("             ) ");
/*  525:     */     }
/*  526: 572 */     if (categoriaEmpresa != null) {
/*  527: 573 */       sql.append(" AND cem = :categoriaEmpresa  ");
/*  528:     */     }
/*  529: 576 */     sql.append(" AND cxp.idOrganizacion = :idOrganizacion ");
/*  530: 577 */     if (fechaVencimientoDesde != null) {
/*  531: 578 */       sql.append(" AND cxp.fechaVencimiento >= :fechaVencimientoDesde  ");
/*  532:     */     }
/*  533: 581 */     if (indicadorCXPEnOPP != null) {
/*  534: 582 */       sql.append(" AND cxp.indicadorEnOrdenPagoProveedor =:indicadorEnOrdenPagoProveedor");
/*  535:     */     }
/*  536: 584 */     sql.append(" ORDER BY cxp.fechaVencimiento, fp.numero ");
/*  537:     */     
/*  538: 586 */     Query query = this.em.createQuery(sql.toString());
/*  539: 587 */     query.setParameter("estado", Estado.ANULADO);
/*  540: 588 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  541: 589 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  542: 590 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  543: 591 */     if (documento != null) {
/*  544: 592 */       query.setParameter("documento", documento);
/*  545:     */     }
/*  546: 595 */     if (fechaVencimientoHasta != null) {
/*  547: 596 */       query.setParameter("fechaVencimientoHasta", fechaVencimientoHasta, TemporalType.DATE);
/*  548:     */     }
/*  549: 598 */     if (indicadorPagoCash != null) {
/*  550: 599 */       query.setParameter("indicadorPagoCash", indicadorPagoCash);
/*  551:     */     }
/*  552: 601 */     if (tipoServicio != null) {
/*  553: 602 */       query.setParameter("tipoServicio", tipoServicio);
/*  554:     */     }
/*  555: 604 */     if (categoriaEmpresa != null) {
/*  556: 605 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/*  557:     */     }
/*  558: 607 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  559: 608 */     if (fechaVencimientoDesde != null) {
/*  560: 609 */       query.setParameter("fechaVencimientoDesde", fechaVencimientoDesde, TemporalType.DATE);
/*  561:     */     }
/*  562: 612 */     if (indicadorCXPEnOPP != null) {
/*  563: 613 */       query.setParameter("indicadorEnOrdenPagoProveedor", indicadorCXPEnOPP);
/*  564:     */     }
/*  565: 616 */     return query.getResultList();
/*  566:     */   }
/*  567:     */   
/*  568:     */   public List<CuentaPorPagar> obtenerFacturasPendientesLiquidacionCuentaPorPagar(Pago pago, int idFacturaProveedor, BigDecimal tolerancia, boolean filtrarPorSucursal)
/*  569:     */   {
/*  570: 623 */     StringBuilder sql = new StringBuilder();
/*  571: 624 */     sql.append(" SELECT cxp FROM CuentaPorPagar cxp ");
/*  572: 625 */     sql.append(" LEFT JOIN FETCH cxp.facturaProveedor fp ");
/*  573: 626 */     sql.append(" LEFT JOIN FETCH fp.facturaProveedorSRI fps ");
/*  574: 627 */     sql.append(" WHERE (fp.empresa.idEmpresa=:idEmpresa OR :idEmpresa = 0) ");
/*  575: 628 */     sql.append(" AND ( cxp.saldo <> 0 AND cxp.saldo <= :tolerancia  ) ");
/*  576: 629 */     sql.append(" AND fp.estado<>:estado ");
/*  577: 630 */     if (filtrarPorSucursal) {
/*  578: 631 */       sql.append(" AND fp.sucursal.idSucursal = :idSucursal ");
/*  579:     */     }
/*  580: 633 */     sql.append(" AND (fp.idFacturaProveedor=:idFacturaProveedor OR :idFacturaProveedor=0)  ");
/*  581: 634 */     sql.append(" AND cxp.indicadorBloqueada = false ");
/*  582: 635 */     sql.append(" AND  EXISTS ");
/*  583: 636 */     sql.append(" ( ");
/*  584: 637 */     sql.append(" \tSELECT 1 FROM DetallePago d ");
/*  585: 638 */     sql.append(" \tJOIN d.pago p JOIN d.cuentaPorPagar cxpp");
/*  586: 639 */     sql.append(" \tJOIN cxpp.facturaProveedor fpp");
/*  587: 640 */     sql.append(" \tWHERE p.fecha <= :fechaPagoLiquidacion AND fpp = fp");
/*  588: 641 */     sql.append(" ) ");
/*  589: 642 */     sql.append(" ORDER BY fp.fecha ASC ");
/*  590:     */     
/*  591: 644 */     Query query = this.em.createQuery(sql.toString());
/*  592: 645 */     query.setParameter("estado", Estado.ANULADO);
/*  593: 646 */     if (filtrarPorSucursal) {
/*  594: 647 */       query.setParameter("idSucursal", Integer.valueOf(pago.getSucursal().getId()));
/*  595:     */     }
/*  596: 649 */     query.setParameter("idEmpresa", Integer.valueOf(pago.getEmpresa().getId()));
/*  597: 650 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  598: 651 */     query.setParameter("fechaPagoLiquidacion", pago.getFecha());
/*  599: 652 */     query.setParameter("tolerancia", tolerancia);
/*  600:     */     
/*  601: 654 */     return query.getResultList();
/*  602:     */   }
/*  603:     */   
/*  604:     */   public List<CuentaPorPagar> obtenerCuentaPorPagar(int idFacturaProveedor)
/*  605:     */   {
/*  606: 674 */     Query query = this.em.createQuery("SELECT cxp FROM CuentaPorPagar cxp WHERE cxp.facturaProveedor.idFacturaProveedor=:idFacturaProveedor");
/*  607: 675 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  608:     */     
/*  609: 677 */     return query.getResultList();
/*  610:     */   }
/*  611:     */   
/*  612:     */   public List<FacturaProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  613:     */   {
/*  614: 689 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  615: 690 */     CriteriaQuery<FacturaProveedor> criteriaQuery = criteriaBuilder.createQuery(FacturaProveedor.class);
/*  616: 691 */     Root<FacturaProveedor> from = criteriaQuery.from(FacturaProveedor.class);
/*  617:     */     
/*  618: 693 */     Join<Object, Object> documento = from.join("documento", JoinType.LEFT);
/*  619: 694 */     Join<Object, Object> recepcionProveeedor = from.join("recepcionProveedor", JoinType.LEFT);
/*  620: 695 */     Join<Object, Object> anticipoProveedor = from.join("anticipoProveedor", JoinType.LEFT);
/*  621: 696 */     Join<Object, Object> facturaProveedorPadre = from.join("facturaProveedorPadre", JoinType.LEFT);
/*  622: 697 */     Join<Object, Object> facturaPorveedorPadreSRI = facturaProveedorPadre.join("facturaProveedorSRI", JoinType.LEFT);
/*  623: 698 */     Join<Object, Object> proyecto = from.join("proyecto", JoinType.LEFT);
/*  624: 699 */     Join<Object, Object> sucursal = from.join("sucursal", JoinType.LEFT);
/*  625: 700 */     Join<Object, Object> facturaProveedorSRI = from.join("facturaProveedorSRI", JoinType.LEFT);
/*  626: 701 */     Join<Object, Object> tipoComprobanteSRI = facturaProveedorSRI.join("tipoComprobanteSRI", JoinType.LEFT);
/*  627: 702 */     Join<Object, Object> asiento = from.join("asiento", JoinType.LEFT);
/*  628: 703 */     Join<Object, Object> facturaProveedorImportacion = from.join("facturaProveedorImportacion", JoinType.LEFT);
/*  629: 704 */     Join<Object, Object> empresa = from.join("empresa", JoinType.LEFT);
/*  630: 705 */     Join<Object, Object> tipoAsiento = asiento.join("tipoAsiento", JoinType.LEFT);
/*  631: 706 */     Join<Object, Object> pais = facturaProveedorImportacion.join("pais", JoinType.LEFT);
/*  632: 707 */     Join<Object, Object> moneda = facturaProveedorImportacion.join("moneda", JoinType.LEFT);
/*  633: 708 */     Join<Object, Object> asientoImportacion = facturaProveedorImportacion.join("asiento", JoinType.LEFT);
/*  634: 709 */     Join<Object, Object> tipoAsientoImportacion = asientoImportacion.join("tipoAsiento", JoinType.LEFT);
/*  635: 710 */     Join<Object, Object> proveedor = empresa.join("proveedor", JoinType.LEFT);
/*  636:     */     
/*  637:     */ 
/*  638: 713 */     Path<Integer> pathidOrganizacion_FP = from.get("idOrganizacion");
/*  639: 714 */     Path<String> pathUsuarioCreacion_FP = from.get("usuarioCreacion");
/*  640: 715 */     Path<String> pathUsuarioModificacion_FP = from.get("usuarioModificacion");
/*  641: 716 */     Path<Integer> pathFechaCreacion_FP = from.get("fechaCreacion");
/*  642: 717 */     Path<Integer> pathFechaModificacion_FP = from.get("fechaModificacion");
/*  643: 718 */     Path<Integer> pathIdFacturaProveeedor_FP = from.get("idFacturaProveedor");
/*  644: 719 */     Path<String> pathNumero_FP = from.get("numero");
/*  645: 720 */     Path<BigDecimal> pathDescuentoImpuesto_FP = from.get("descuentoImpuesto");
/*  646: 721 */     Path<Date> pathFechaContabilizacion_FP = from.get("fechaContabilizacion");
/*  647: 722 */     Path<Date> pathFechaRecepcion_FP = from.get("fechaRecepcion");
/*  648: 723 */     Path<String> pathDescripcion_FP = from.get("descripcion");
/*  649: 724 */     Path<Date> pathFecha_FP = from.get("fecha");
/*  650: 725 */     Path<BigDecimal> pathTotal_FP = from.get("total");
/*  651: 726 */     Path<BigDecimal> pathDescuento_FP = from.get("descuento");
/*  652: 727 */     Path<BigDecimal> pathImpuesto_FP = from.get("impuesto");
/*  653: 728 */     Path<Estado> pathEstado_FP = from.get("estado");
/*  654: 729 */     Path<String> pathPDF_FP = from.get("pdf");
/*  655:     */     
/*  656:     */ 
/*  657: 732 */     Path<Integer> pathIdEmpresa_E = empresa.get("idEmpresa");
/*  658: 733 */     Path<String> pathNombreFiscal_E = empresa.get("nombreFiscal");
/*  659: 734 */     Path<String> pathNombreComercial_E = empresa.get("nombreComercial");
/*  660:     */     
/*  661:     */ 
/*  662: 737 */     Path<Integer> pathIdRecepcionProveedor_RP = recepcionProveeedor.get("idRecepcionProveedor");
/*  663: 738 */     Path<String> pathNumeroRecepcionProveedor_RP = recepcionProveeedor.get("numero");
/*  664:     */     
/*  665:     */ 
/*  666:     */ 
/*  667:     */ 
/*  668:     */ 
/*  669:     */ 
/*  670:     */ 
/*  671: 746 */     Path<Integer> pathIdDocumento_DOC = documento.get("idDocumento");
/*  672: 747 */     Path<String> pathReporteDocumento_DOC = documento.get("reporte");
/*  673: 748 */     Path<Boolean> pathIndicadorDocumentoExterior_DOC = documento.get("indicadorDocumentoExterior");
/*  674: 749 */     Path<DocumentoBase> pathDocumentoBase_DOC = documento.get("documentoBase");
/*  675: 750 */     Path<Boolean> pathIndicadorDocumentoElectronico_DOC = documento.get("indicadorDocumentoElectronico");
/*  676: 751 */     Path<String> pathIndicadorDocuemntoTributario_DOC = documento.get("indicadorDocumentoTributario");
/*  677:     */     
/*  678:     */ 
/*  679: 754 */     Path<Integer> pathIdFacturaProveeedorSRI_FP_SRI = facturaProveedorSRI.get("idFacturaProveedorSRI");
/*  680: 755 */     Path<String> pathEstablecimiento_FP_SRI = facturaProveedorSRI.get("establecimiento");
/*  681: 756 */     Path<String> pathPuntoEmision_FP_SRI = facturaProveedorSRI.get("puntoEmision");
/*  682: 757 */     Path<String> pathNumeroSRI_FP_SRI = facturaProveedorSRI.get("numero");
/*  683: 758 */     Path<String> pathNumeroRetencion_FP_SRI = facturaProveedorSRI.get("numeroRetencion");
/*  684: 759 */     Path<Boolean> pathIndicadorRetencionEmitida_FP_SRI = facturaProveedorSRI.get("indicadorRetencionEmitida");
/*  685: 760 */     Path<String> pathPuntoEmisionRetencion_FP_SRI = facturaProveedorSRI.get("puntoEmisionRetencion");
/*  686: 761 */     Path<String> pathEstablecimientoRetencion_FP_SRI = facturaProveedorSRI.get("establecimientoRetencion");
/*  687: 762 */     Path<String> pathMensajeFacturaProveedorSRI_FP_SRI = facturaProveedorSRI.get("mensajeSRI");
/*  688: 763 */     Path<Estado> pathEstadoFacturaProveedorSRI_FP_SRI = facturaProveedorSRI.get("estado");
/*  689: 764 */     Path<Boolean> pathIndicadorDocumentoElectronicoFacturaProveedorSRI_FP_SRI = facturaProveedorSRI.get("indicadorDocumentoElectronico");
/*  690: 765 */     Path<String> pathClaveAccesoFacturaProveedorSRI_FP_SRI = facturaProveedorSRI.get("claveAcceso");
/*  691:     */     
/*  692:     */ 
/*  693: 768 */     Path<Integer> pathIdTipoComprobanteSRI_FP_SRI = tipoComprobanteSRI.get("idTipoComprobanteSRI");
/*  694:     */     
/*  695:     */ 
/*  696: 771 */     Path<Integer> pathIdFacturaProveedorPadre_FP_Padre = facturaProveedorPadre.get("idFacturaProveedor");
/*  697: 772 */     Path<String> pathNumeroFacturaProveedorPadre_FP_Padre = facturaProveedorPadre.get("numero");
/*  698: 773 */     Path<Estado> pathEstadoFacturaProveedorPadre_FP_Padre = facturaProveedorPadre.get("estado");
/*  699:     */     
/*  700:     */ 
/*  701: 776 */     Path<Integer> pathIdFacturaProveeedorSRI_FP_Padre = facturaPorveedorPadreSRI.get("idFacturaProveedorSRI");
/*  702: 777 */     Path<String> pathEstablecimiento_FP_Padre = facturaPorveedorPadreSRI.get("establecimiento");
/*  703: 778 */     Path<String> pathPuntoEmision_FP_Padre = facturaPorveedorPadreSRI.get("puntoEmision");
/*  704: 779 */     Path<String> pathNumeroSRI_FP_Padre = facturaPorveedorPadreSRI.get("numero");
/*  705: 780 */     Path<String> pathEstablecimientoRetencion_FP_SRI_Padre = facturaPorveedorPadreSRI.get("establecimientoRetencion");
/*  706: 781 */     Path<String> pathNumeroRetencion_FP_SRI_Padre = facturaPorveedorPadreSRI.get("numeroRetencion");
/*  707: 782 */     Path<Boolean> pathIndicadorRetencionEmitida_FP_SRI_Padre = facturaPorveedorPadreSRI.get("indicadorRetencionEmitida");
/*  708: 783 */     Path<String> pathPuntoEmisionRetencion_FP_SRI_Padre = facturaPorveedorPadreSRI.get("puntoEmisionRetencion");
/*  709: 784 */     Path<String> pathClaveAccesoFacturaProveedorSRI_FP_SRI_Padre = facturaPorveedorPadreSRI.get("claveAcceso");
/*  710:     */     
/*  711:     */ 
/*  712: 787 */     Path<Integer> pathIdAnticipoProveeedor_A = anticipoProveedor.get("idAnticipoProveedor");
/*  713: 788 */     Path<Integer> pathNumeroAnticipoProveeedor_A = anticipoProveedor.get("numero");
/*  714: 789 */     Path<BigDecimal> pathSaldoAnticipoProveeedor_A = anticipoProveedor.get("saldo");
/*  715:     */     
/*  716:     */ 
/*  717: 792 */     Path<Integer> pathIdAsiento_AS = asiento.get("idAsiento");
/*  718: 793 */     Path<Integer> pathNumeroAsiento_AS = asiento.get("numero");
/*  719: 794 */     Path<Estado> pathEstado_AS = asiento.get("estado");
/*  720:     */     
/*  721:     */ 
/*  722: 797 */     Path<Integer> pathIdTipoAsiento_T_AS = tipoAsiento.get("idTipoAsiento");
/*  723: 798 */     Path<String> pathNombreTipoAsiento_T_AS = tipoAsiento.get("nombre");
/*  724:     */     
/*  725:     */ 
/*  726: 801 */     Path<Integer> pathIdProveedor_P = proveedor.get("idProveedor");
/*  727:     */     
/*  728:     */ 
/*  729: 804 */     Path<Integer> pathIdProyecto_PY = proyecto.get("idDimensionContable");
/*  730: 805 */     Path<String> pathCodigoProyecto_PY = proyecto.get("codigo");
/*  731: 806 */     Path<String> pathNombreProyecto_PY = proyecto.get("nombre");
/*  732:     */     
/*  733:     */ 
/*  734: 809 */     Path<Integer> pathIdSucursal_SU = sucursal.get("idSucursal");
/*  735: 810 */     Path<String> pathNombreSucursal_SU = sucursal.get("nombre");
/*  736:     */     
/*  737:     */ 
/*  738: 813 */     Path<Integer> pathIdFacturaProveedorImportacion_F_P_IM = facturaProveedorImportacion.get("idFacturaProveedorImportacion");
/*  739: 814 */     Path<BigDecimal> pathTotalValorGastoReal = facturaProveedorImportacion.get("totalValorGastoReal");
/*  740:     */     
/*  741:     */ 
/*  742: 817 */     Path<Integer> pathIdMoneda_MON = moneda.get("idMoneda");
/*  743: 818 */     Path<String> pathNombreMoneda_MON = moneda.get("nombre");
/*  744:     */     
/*  745:     */ 
/*  746: 821 */     Path<Integer> pathIdPais_PAIS = pais.get("idPais");
/*  747: 822 */     Path<String> pathNombre_PAIS = pais.get("nombre");
/*  748:     */     
/*  749:     */ 
/*  750: 825 */     Path<Integer> pathIdAsiento_AS_Importacion = asientoImportacion.get("idAsiento");
/*  751: 826 */     Path<Integer> pathNumeroAsiento_AS_Importacion = asientoImportacion.get("numero");
/*  752:     */     
/*  753:     */ 
/*  754: 829 */     Path<Integer> pathIdTipoAsiento_T_AS_Importacion = tipoAsientoImportacion.get("idTipoAsiento");
/*  755: 830 */     Path<String> pathNombreTipoAsiento_T_AS_Importacion = tipoAsientoImportacion.get("nombre");
/*  756:     */     
/*  757: 832 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  758:     */     
/*  759: 834 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  760: 835 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  761:     */     
/*  762: 837 */     CriteriaQuery<FacturaProveedor> select = criteriaQuery.multiselect(new Selection[] { pathIdFacturaProveeedor_FP, pathidOrganizacion_FP, pathUsuarioCreacion_FP, pathUsuarioModificacion_FP, pathFechaCreacion_FP, pathFechaModificacion_FP, pathNumero_FP, pathDescuentoImpuesto_FP, pathFechaContabilizacion_FP, pathFechaRecepcion_FP, pathDescripcion_FP, pathFecha_FP, pathTotal_FP, pathDescuento_FP, pathImpuesto_FP, pathEstado_FP, pathPDF_FP, pathIdEmpresa_E, pathNombreFiscal_E, pathNombreComercial_E, pathIdRecepcionProveedor_RP, pathNumeroRecepcionProveedor_RP, pathIdDocumento_DOC, pathReporteDocumento_DOC, pathIndicadorDocumentoExterior_DOC, pathDocumentoBase_DOC, pathIndicadorDocumentoElectronico_DOC, pathIndicadorDocuemntoTributario_DOC, pathIdFacturaProveeedorSRI_FP_SRI, pathEstablecimiento_FP_SRI, pathPuntoEmision_FP_SRI, pathNumeroSRI_FP_SRI, pathNumeroRetencion_FP_SRI, pathIndicadorRetencionEmitida_FP_SRI, pathPuntoEmisionRetencion_FP_SRI, pathEstablecimientoRetencion_FP_SRI, pathMensajeFacturaProveedorSRI_FP_SRI, pathEstadoFacturaProveedorSRI_FP_SRI, pathIndicadorDocumentoElectronicoFacturaProveedorSRI_FP_SRI, pathClaveAccesoFacturaProveedorSRI_FP_SRI, pathIdFacturaProveedorPadre_FP_Padre, pathNumeroFacturaProveedorPadre_FP_Padre, pathEstadoFacturaProveedorPadre_FP_Padre, pathIdFacturaProveeedorSRI_FP_Padre, pathEstablecimiento_FP_Padre, pathPuntoEmision_FP_Padre, pathNumeroSRI_FP_Padre, pathEstablecimientoRetencion_FP_SRI_Padre, pathNumeroRetencion_FP_SRI_Padre, pathIndicadorRetencionEmitida_FP_SRI_Padre, pathPuntoEmisionRetencion_FP_SRI_Padre, pathClaveAccesoFacturaProveedorSRI_FP_SRI_Padre, pathIdAnticipoProveeedor_A, pathNumeroAnticipoProveeedor_A, pathSaldoAnticipoProveeedor_A, pathIdAsiento_AS, pathNumeroAsiento_AS, pathEstado_AS, pathIdTipoAsiento_T_AS, pathNombreTipoAsiento_T_AS, pathIdProveedor_P, pathIdProyecto_PY, pathCodigoProyecto_PY, pathNombreProyecto_PY, pathIdSucursal_SU, pathNombreSucursal_SU, pathIdFacturaProveedorImportacion_F_P_IM, pathIdMoneda_MON, pathNombreMoneda_MON, pathIdPais_PAIS, pathNombre_PAIS, pathIdTipoComprobanteSRI_FP_SRI, pathTotalValorGastoReal, pathIdAsiento_AS_Importacion, pathNumeroAsiento_AS_Importacion, pathIdTipoAsiento_T_AS_Importacion, pathNombreTipoAsiento_T_AS_Importacion });
/*  763:     */     
/*  764:     */ 
/*  765:     */ 
/*  766:     */ 
/*  767:     */ 
/*  768:     */ 
/*  769:     */ 
/*  770:     */ 
/*  771:     */ 
/*  772:     */ 
/*  773:     */ 
/*  774:     */ 
/*  775:     */ 
/*  776:     */ 
/*  777:     */ 
/*  778:     */ 
/*  779:     */ 
/*  780:     */ 
/*  781:     */ 
/*  782: 857 */     TypedQuery<FacturaProveedor> typedQuery = this.em.createQuery(select);
/*  783: 858 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  784: 859 */     return typedQuery.getResultList();
/*  785:     */   }
/*  786:     */   
/*  787:     */   public List<FacturaProveedor> listaFacturasPorRecibir(Estado estadoAprobado, int idEmpresa)
/*  788:     */   {
/*  789: 871 */     Query query = this.em.createQuery("SELECT new FacturaProveedor(f.idFacturaProveedor,f.numero,f.fecha)  FROM FacturaProveedor f   WHERE f.estado>=:estadoAprobado  AND f.estado!=:estado  AND f.empresa.idEmpresa=:idEmpresa AND f.documento.documentoBase=:documentoBase AND EXISTS (SELECT 1 FROM DetalleFacturaProveedor dfc WHERE dfc.facturaProveedor.idFacturaProveedor = f.idFacturaProveedor AND dfc.cantidadPorRecibir>0) ORDER BY f.numero DESC");
/*  790:     */     
/*  791:     */ 
/*  792:     */ 
/*  793:     */ 
/*  794: 876 */     query.setParameter("estadoAprobado", estadoAprobado);
/*  795: 877 */     query.setParameter("estado", Estado.ANULADO);
/*  796: 878 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_PROVEEDOR);
/*  797: 879 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  798: 880 */     return query.getResultList();
/*  799:     */   }
/*  800:     */   
/*  801:     */   public List<DetalleFacturaProveedor> getListaDetalleFacturaPorRecibir(int idFacturaProveedor)
/*  802:     */   {
/*  803: 893 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  804: 894 */     CriteriaQuery<DetalleFacturaProveedor> criteriaQuery = criteriaBuilder.createQuery(DetalleFacturaProveedor.class);
/*  805: 895 */     Root<DetalleFacturaProveedor> from = criteriaQuery.from(DetalleFacturaProveedor.class);
/*  806:     */     
/*  807: 897 */     Fetch<Object, Object> producto = from.fetch("producto", JoinType.LEFT);
/*  808: 898 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/*  809:     */     
/*  810: 900 */     CriteriaQuery<DetalleFacturaProveedor> select = criteriaQuery.select(from);
/*  811:     */     
/*  812: 902 */     List<Expression> predicates = new ArrayList();
/*  813: 903 */     predicates.add(criteriaBuilder.equal(from.join("facturaProveedor").get("idFacturaProveedor"), Integer.valueOf(idFacturaProveedor)));
/*  814: 904 */     predicates.add(criteriaBuilder.greaterThan(from.get("cantidadPorRecibir").as(BigDecimal.class), BigDecimal.ZERO));
/*  815:     */     
/*  816: 906 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  817:     */     
/*  818: 908 */     return this.em.createQuery(select).getResultList();
/*  819:     */   }
/*  820:     */   
/*  821:     */   public List<FacturaProveedor> listaFacturaPorProveedor(Empresa empresa)
/*  822:     */   {
/*  823: 922 */     Query query = this.em.createQuery("SELECT new FacturaProveedor(f.idFacturaProveedor, f.numero, f.fecha) FROM FacturaProveedor f WHERE f.empresa.idEmpresa =:idEmpresa  ORDER BY f.fecha").setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/*  824: 923 */     return query.getResultList();
/*  825:     */   }
/*  826:     */   
/*  827:     */   public BigDecimal getSumaImpuestoPorIdFacturaProveedor(int idDetalleFacturaProveedor)
/*  828:     */   {
/*  829: 933 */     String query = "SELECT SUM(i.porcentajeImpuesto) FROM ImpuestoProductoFacturaProveedor i  INNER JOIN i.detalleFacturaProveedor d  WHERE d.idDetalleFacturaProveedor = :idDetalleFacturaProveedor";
/*  830:     */     
/*  831:     */ 
/*  832: 936 */     BigDecimal suma = (BigDecimal)this.em.createQuery(query).setParameter("idDetalleFacturaProveedor", Integer.valueOf(idDetalleFacturaProveedor)).getSingleResult();
/*  833:     */     
/*  834: 938 */     return suma;
/*  835:     */   }
/*  836:     */   
/*  837:     */   public List<DetalleInterfazContable> getCuentaPorPagarIC(int idFacturaProveedor)
/*  838:     */     throws ExcepcionAS2Financiero
/*  839:     */   {
/*  840:     */     try
/*  841:     */     {
/*  842: 953 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #',fp.numero,' ',fp.descripcion), '', (-fp.total+fp.descuento-fp.impuesto-fp.retencionComercializadora-fp.retencion3X1000 -fp.retencionIvaPresuntivo) )  FROM FacturaProveedor fp  INNER JOIN fp.documento do  INNER JOIN fp.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ce.cuentaContableProveedor cc  WHERE fp.idFacturaProveedor=:idFacturaProveedor");
/*  843:     */       
/*  844:     */ 
/*  845:     */ 
/*  846:     */ 
/*  847:     */ 
/*  848: 959 */       query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  849:     */       
/*  850: 961 */       return query.getResultList();
/*  851:     */     }
/*  852:     */     catch (IllegalArgumentException e)
/*  853:     */     {
/*  854: 964 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableProveedor");
/*  855:     */     }
/*  856:     */   }
/*  857:     */   
/*  858:     */   public List<DetalleInterfazContable> getCuentaImportacionFPGIC(int idFacturaProveedor)
/*  859:     */     throws ExcepcionAS2Financiero
/*  860:     */   {
/*  861:     */     try
/*  862:     */     {
/*  863: 981 */       StringBuilder sql = new StringBuilder();
/*  864:     */       
/*  865: 983 */       sql.append(" SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal, ");
/*  866: 984 */       sql.append(" CONCAT(do.nombre,COALESCE(CONCAT(' F:/ ',fps.establecimiento,'-',fps.puntoEmision,'-',fps.numero), ");
/*  867: 985 */       sql.append(" CONCAT(' #',fp.numero)),' Imp./ ',fpp.numero,' ',fp.descripcion), '', (dfpi.valor) ) ");
/*  868: 986 */       sql.append(" FROM DetalleFacturaProveedorImportacion dfpi ");
/*  869: 987 */       sql.append(" INNER JOIN dfpi.detalleFacturaProveedor dfp ");
/*  870: 988 */       sql.append(" INNER JOIN dfp.facturaProveedor fp");
/*  871: 989 */       sql.append(" LEFT  JOIN fp.facturaProveedorSRI fps ");
/*  872: 990 */       sql.append(" INNER JOIN fp.documento do ");
/*  873: 991 */       sql.append(" INNER JOIN fp.empresa em ");
/*  874: 992 */       sql.append(" INNER JOIN dfpi.facturaProveedor fpp ");
/*  875: 993 */       sql.append(" INNER JOIN fpp.facturaProveedorImportacion im ");
/*  876: 994 */       sql.append(" LEFT JOIN im.cuentaContableImportacion cc ");
/*  877: 995 */       sql.append(" WHERE fp.idFacturaProveedor=:idFacturaProveedor");
/*  878:     */       
/*  879: 997 */       Query query = this.em.createQuery(sql.toString());
/*  880:     */       
/*  881: 999 */       query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  882:     */       
/*  883:1001 */       return query.getResultList();
/*  884:     */     }
/*  885:     */     catch (IllegalArgumentException e)
/*  886:     */     {
/*  887:1004 */       e.printStackTrace();
/*  888:1005 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableImportacion");
/*  889:     */     }
/*  890:     */   }
/*  891:     */   
/*  892:     */   public List<DetalleInterfazContable> getCuentaImpuestoFPGIC(int idFacturaProveedor)
/*  893:     */     throws ExcepcionAS2Financiero
/*  894:     */   {
/*  895:     */     try
/*  896:     */     {
/*  897:1021 */       StringBuilder sql = new StringBuilder();
/*  898:1022 */       sql.append(" SELECT new DetalleInterfazContable(ccc.idCuentaContable,em.nombreFiscal,CONCAT(im.nombre, ' ', do.nombre, COALESCE(CONCAT(' F:/ ',fps.establecimiento,'-',fps.puntoEmision,'-',fps.numero), CONCAT(' #',fp.numero)),' ',fp.descripcion),'',ROUND(SUM(df.cantidad*(df.precio-df.descuento)*(ipfp.porcentajeImpuesto/100)),2) )");
/*  899:     */       
/*  900:1024 */       sql.append(" FROM ImpuestoProductoFacturaProveedor ipfp");
/*  901:1025 */       sql.append(" INNER JOIN ipfp.impuesto im");
/*  902:1026 */       sql.append(" INNER JOIN ipfp.detalleFacturaProveedor df");
/*  903:1027 */       sql.append(" INNER JOIN df.facturaProveedor fp");
/*  904:1028 */       sql.append(" LEFT  JOIN fp.facturaProveedorSRI fps ");
/*  905:1029 */       sql.append(" INNER JOIN fp.documento do");
/*  906:1030 */       sql.append(" INNER JOIN fp.empresa em");
/*  907:1031 */       sql.append(" INNER JOIN df.producto pr ");
/*  908:1032 */       sql.append(" LEFT JOIN im.cuentaContableCompra ccc ");
/*  909:1033 */       sql.append(" WHERE fp.indicadorCreditoTributario=true");
/*  910:1034 */       sql.append(" AND (pr.tipoProducto=:tipoProducto1 OR pr.tipoProducto=:tipoProducto2) AND df.indicadorGastoImportacion = true");
/*  911:1035 */       sql.append(" AND fp.idFacturaProveedor=:idFacturaProveedor");
/*  912:1036 */       sql.append(" GROUP BY ccc.idCuentaContable,");
/*  913:1037 */       sql.append(" em.nombreFiscal,CONCAT(im.nombre, ' ', do.nombre, COALESCE(CONCAT(' F:/ ',fps.establecimiento,'-',fps.puntoEmision,'-',fps.numero), CONCAT(' #',fp.numero)),' ',fp.descripcion)");
/*  914:     */       
/*  915:     */ 
/*  916:1040 */       Query query = this.em.createQuery(sql.toString());
/*  917:1041 */       query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  918:1042 */       query.setParameter("tipoProducto1", TipoProducto.SERVICIO);
/*  919:1043 */       query.setParameter("tipoProducto2", TipoProducto.ARTICULO_NO_INVENTARIABLE);
/*  920:     */       
/*  921:1045 */       return query.getResultList();
/*  922:     */     }
/*  923:     */     catch (IllegalArgumentException e)
/*  924:     */     {
/*  925:1048 */       e.printStackTrace();
/*  926:1049 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableCompra");
/*  927:     */     }
/*  928:     */   }
/*  929:     */   
/*  930:     */   public List<DetalleInterfazContable> getCuentaImportacionFPIIC(int idFacturaProveedor)
/*  931:     */     throws ExcepcionAS2Financiero
/*  932:     */   {
/*  933:     */     try
/*  934:     */     {
/*  935:1065 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #',fp.numero), '', (fp.total-fp.descuento+fp.impuesto) )  FROM FacturaProveedor fp  INNER JOIN fp.documento do  INNER JOIN fp.empresa em  INNER JOIN fp.facturaProveedorImportacion im  LEFT JOIN im.cuentaContableImportacion cc  WHERE fp.idFacturaProveedor=:idFacturaProveedor");
/*  936:     */       
/*  937:     */ 
/*  938:     */ 
/*  939:     */ 
/*  940:     */ 
/*  941:1071 */       query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  942:     */       
/*  943:1073 */       return query.getResultList();
/*  944:     */     }
/*  945:     */     catch (IllegalArgumentException e)
/*  946:     */     {
/*  947:1076 */       e.printStackTrace();
/*  948:1077 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableImportacion");
/*  949:     */     }
/*  950:     */   }
/*  951:     */   
/*  952:     */   public List<DetalleInterfazContable> getImpuestosBienesIC(int idFacturaProveedor)
/*  953:     */     throws ExcepcionAS2Financiero
/*  954:     */   {
/*  955:     */     try
/*  956:     */     {
/*  957:1093 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(ccc.idCuentaContable,em.nombreFiscal,CONCAT(im.nombre, ' ', do.nombre, ' #', fp.numero,' ',fp.descripcion),'',ROUND(SUM(df.cantidad*(df.precio-df.descuento)*(ipfp.porcentajeImpuesto/100)),2) )\tFROM ImpuestoProductoFacturaProveedor ipfp INNER JOIN  ipfp.impuesto im\tINNER JOIN  ipfp.detalleFacturaProveedor df\tINNER JOIN  df.facturaProveedor fp\tINNER JOIN  fp.documento do\tINNER JOIN  fp.empresa em\tINNER JOIN  df.producto pr  LEFT JOIN im.cuentaContableCompra ccc  WHERE pr.tipoProducto=:tipoProducto AND fp.indicadorCreditoTributario=true AND fp.idFacturaProveedor=:idFacturaProveedor\tGROUP BY ccc.idCuentaContable, em.nombreFiscal,CONCAT(im.nombre, ' ', do.nombre, ' #', fp.numero,' ',fp.descripcion)");
/*  958:     */       
/*  959:     */ 
/*  960:     */ 
/*  961:     */ 
/*  962:     */ 
/*  963:     */ 
/*  964:     */ 
/*  965:     */ 
/*  966:     */ 
/*  967:1103 */       query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  968:1104 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/*  969:     */       
/*  970:1106 */       return query.getResultList();
/*  971:     */     }
/*  972:     */     catch (IllegalArgumentException e)
/*  973:     */     {
/*  974:1109 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableImpuestosBienes");
/*  975:     */     }
/*  976:     */   }
/*  977:     */   
/*  978:     */   public List<DetalleInterfazContable> getMercaderiaPorRecibirIC(int idFacturaProveedor)
/*  979:     */     throws ExcepcionAS2Financiero
/*  980:     */   {
/*  981:     */     try
/*  982:     */     {
/*  983:1126 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(ccmpr.idCuentaContable,em.nombreFiscal, CONCAT(do.nombre, ' #', fp.numero,' ',fp.descripcion),'', SUM(ROUND(((CASE WHEN fp.indicadorCreditoTributario=true THEN 0 ELSE df.valorImpuestosLinea END) + (df.cantidad*(df.precio-df.descuento))),2)))\tFROM DetalleFacturaProveedor df\tINNER JOIN  df.facturaProveedor fp\tINNER JOIN  fp.documento do\tINNER JOIN  fp.empresa em\tINNER JOIN  df.producto pr \tINNER JOIN  pr.subcategoriaProducto sc LEFT JOIN sc.cuentaContableMercaderiaPorRecibir ccmpr WHERE pr.tipoProducto=:tipoProducto AND fp.idFacturaProveedor=:idFacturaProveedor\tGROUP BY ccmpr.idCuentaContable, em.nombreFiscal,CONCAT(do.nombre, ' #', fp.numero,' ',fp.descripcion)");
/*  984:     */       
/*  985:     */ 
/*  986:     */ 
/*  987:     */ 
/*  988:     */ 
/*  989:     */ 
/*  990:     */ 
/*  991:     */ 
/*  992:1135 */       query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  993:1136 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/*  994:     */       
/*  995:1138 */       return query.getResultList();
/*  996:     */     }
/*  997:     */     catch (IllegalArgumentException e)
/*  998:     */     {
/*  999:1141 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableMercaderiaPorRecibir");
/* 1000:     */     }
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public List<DetalleInterfazContable> getGastoServiciosNCIC(int idFacturaProveedor)
/* 1004:     */   {
/* 1005:1156 */     StringBuilder sql = new StringBuilder();
/* 1006:1157 */     sql.append(" SELECT new DetalleInterfazContable(ccg.idCuentaContable,d1.idDimensionContable,d2.idDimensionContable, ");
/* 1007:1158 */     sql.append(" d3.idDimensionContable, d4.idDimensionContable, d5.idDimensionContable,");
/* 1008:1159 */     sql.append(" em.nombreFiscal,CONCAT(do.nombre, ' #', fp.numero,' ',fp.descripcion),'', ");
/* 1009:1160 */     sql.append(" - ga.valor)");
/* 1010:1161 */     sql.append(" FROM GastoProductoFacturaProveedor ga");
/* 1011:1162 */     sql.append(" INNER JOIN  ga.detalleFacturaProveedor df");
/* 1012:1163 */     sql.append(" INNER JOIN  df.producto pr ");
/* 1013:1164 */     sql.append(" INNER JOIN  df.facturaProveedor fp");
/* 1014:1165 */     sql.append(" INNER JOIN  fp.documento do");
/* 1015:1166 */     sql.append(" INNER JOIN  fp.empresa em");
/* 1016:1167 */     sql.append(" INNER JOIN ga.cuentaContableGasto ccg");
/* 1017:1168 */     sql.append(" LEFT JOIN ga.dimensionContable1 d1");
/* 1018:1169 */     sql.append(" LEFT JOIN ga.dimensionContable2 d2");
/* 1019:1170 */     sql.append(" LEFT JOIN ga.dimensionContable3 d3");
/* 1020:1171 */     sql.append(" LEFT JOIN ga.dimensionContable4 d4");
/* 1021:1172 */     sql.append(" LEFT JOIN ga.dimensionContable5 d5");
/* 1022:1173 */     sql.append(" WHERE (pr.tipoProducto=:tipoProducto1 OR pr.tipoProducto=:tipoProducto2)");
/* 1023:1174 */     sql.append(" AND fp.idFacturaProveedor=:idFacturaProveedor");
/* 1024:1175 */     Query query = this.em.createQuery(sql.toString());
/* 1025:     */     
/* 1026:1177 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1027:1178 */     query.setParameter("tipoProducto1", TipoProducto.SERVICIO);
/* 1028:1179 */     query.setParameter("tipoProducto2", TipoProducto.ARTICULO_NO_INVENTARIABLE);
/* 1029:1180 */     return query.getResultList();
/* 1030:     */   }
/* 1031:     */   
/* 1032:     */   public List<DetalleInterfazContable> getCXPNCIC(int idFacturaProveedor)
/* 1033:     */     throws ExcepcionAS2Financiero
/* 1034:     */   {
/* 1035:     */     try
/* 1036:     */     {
/* 1037:1196 */       StringBuilder sql = new StringBuilder();
/* 1038:1197 */       sql.append("SELECT new DetalleInterfazContable(ccap.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre, ' #', fp.numero,' ',fp.descripcion),'', sum(ga.valor) )");
/* 1039:     */       
/* 1040:1199 */       sql.append(" FROM GastoProductoFacturaProveedor ga");
/* 1041:1200 */       sql.append(" INNER JOIN  ga.detalleFacturaProveedor df");
/* 1042:1201 */       sql.append(" INNER JOIN  df.producto pr ");
/* 1043:1202 */       sql.append(" INNER JOIN  df.facturaProveedor fp");
/* 1044:1203 */       sql.append(" INNER JOIN  fp.documento do");
/* 1045:1204 */       sql.append(" INNER JOIN  fp.empresa em ");
/* 1046:1205 */       sql.append(" INNER JOIN  em.categoriaEmpresa cec ");
/* 1047:1206 */       sql.append(" LEFT JOIN cec.cuentaContableAnticipoProveedorNotaCredito ccap");
/* 1048:1207 */       sql.append(" WHERE (pr.tipoProducto=:tipoProducto1 OR pr.tipoProducto=:tipoProducto2)");
/* 1049:1208 */       sql.append(" AND fp.idFacturaProveedor=:idFacturaProveedor");
/* 1050:1209 */       sql.append(" GROUP BY ccap.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre, ' #', fp.numero,' ',fp.descripcion)");
/* 1051:     */       
/* 1052:1211 */       Query query = this.em.createQuery(sql.toString());
/* 1053:1212 */       query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1054:1213 */       query.setParameter("tipoProducto1", TipoProducto.SERVICIO);
/* 1055:1214 */       query.setParameter("tipoProducto2", TipoProducto.ARTICULO_NO_INVENTARIABLE);
/* 1056:1215 */       return query.getResultList();
/* 1057:     */     }
/* 1058:     */     catch (IllegalArgumentException e)
/* 1059:     */     {
/* 1060:1218 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableAnticipoProveedor");
/* 1061:     */     }
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public List<DetalleInterfazContable> getGastoServiciosIC(int idFacturaProveedor)
/* 1065:     */   {
/* 1066:1231 */     StringBuilder sql = new StringBuilder();
/* 1067:1232 */     sql.append("SELECT new DetalleInterfazContable(ccg.idCuentaContable,");
/* 1068:1233 */     sql.append(" d1.idDimensionContable,d2.idDimensionContable, d3.idDimensionContable,");
/* 1069:1234 */     sql.append(" d4.idDimensionContable, d5.idDimensionContable, ");
/* 1070:1235 */     sql.append(" em.nombreFiscal,CONCAT(do.nombre, ' #', fp.numero,' ',fp.descripcion),'', ga.valor)");
/* 1071:1236 */     sql.append(" FROM GastoProductoFacturaProveedor ga");
/* 1072:1237 */     sql.append(" LEFT JOIN ga.dimensionContable1 d1");
/* 1073:1238 */     sql.append(" LEFT JOIN ga.dimensionContable2 d2");
/* 1074:1239 */     sql.append(" LEFT JOIN ga.dimensionContable3 d3");
/* 1075:1240 */     sql.append(" LEFT JOIN ga.dimensionContable4 d4");
/* 1076:1241 */     sql.append(" LEFT JOIN ga.dimensionContable5 d5");
/* 1077:1242 */     sql.append(" INNER JOIN ga.detalleFacturaProveedor df");
/* 1078:1243 */     sql.append(" INNER JOIN df.producto pr ");
/* 1079:1244 */     sql.append(" INNER JOIN df.facturaProveedor fp");
/* 1080:1245 */     sql.append(" INNER JOIN fp.documento do");
/* 1081:1246 */     sql.append(" INNER JOIN fp.empresa em");
/* 1082:1247 */     sql.append(" LEFT JOIN ga.cuentaContableGasto ccg");
/* 1083:1248 */     sql.append(" WHERE (pr.tipoProducto=:tipoProducto1 OR pr.tipoProducto=:tipoProducto2)");
/* 1084:1249 */     sql.append(" AND fp.idFacturaProveedor=:idFacturaProveedor");
/* 1085:     */     
/* 1086:1251 */     Query query = this.em.createQuery(sql.toString());
/* 1087:1252 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1088:1253 */     query.setParameter("tipoProducto1", TipoProducto.SERVICIO);
/* 1089:1254 */     query.setParameter("tipoProducto2", TipoProducto.ARTICULO_NO_INVENTARIABLE);
/* 1090:     */     
/* 1091:1256 */     return query.getResultList();
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   public List<DetalleInterfazContable> getBonoIC(int idFacturaProveedor)
/* 1095:     */     throws ExcepcionAS2Financiero
/* 1096:     */   {
/* 1097:1270 */     List<DetalleInterfazContable> lista = new ArrayList();
/* 1098:     */     try
/* 1099:     */     {
/* 1100:1272 */       Query queryCXP = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #',fp.numero,'-BONO'), '', fp.bono)  FROM FacturaProveedor fp  INNER JOIN fp.documento do  INNER JOIN fp.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ce.cuentaContableProveedor cc  WHERE fp.idFacturaProveedor=:idFacturaProveedor");
/* 1101:     */       
/* 1102:     */ 
/* 1103:     */ 
/* 1104:     */ 
/* 1105:     */ 
/* 1106:1278 */       queryCXP.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1107:1279 */       lista.addAll(queryCXP.getResultList());
/* 1108:     */     }
/* 1109:     */     catch (IllegalArgumentException e)
/* 1110:     */     {
/* 1111:1282 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableProveedor");
/* 1112:     */     }
/* 1113:1285 */     Query queryBono = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #',fp.numero,'-BONO'), '', -fp.bono)  FROM FacturaProveedor fp  INNER JOIN fp.documento do  INNER JOIN fp.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ce.cuentaContableProveedor cc  WHERE fp.idFacturaProveedor=:idFacturaProveedor");
/* 1114:     */     
/* 1115:     */ 
/* 1116:     */ 
/* 1117:     */ 
/* 1118:     */ 
/* 1119:1291 */     queryBono.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1120:     */     
/* 1121:1293 */     DetalleInterfazContable detalleInterfazContable = (DetalleInterfazContable)queryBono.getSingleResult();
/* 1122:     */     try
/* 1123:     */     {
/* 1124:1296 */       Integer idCuentaBonoCXP = ParametrosSistema.getCuentaBonoCXP();
/* 1125:1297 */       detalleInterfazContable.setIdCuentaContable(idCuentaBonoCXP.intValue());
/* 1126:1298 */       lista.add(detalleInterfazContable);
/* 1127:     */     }
/* 1128:     */     catch (Exception e)
/* 1129:     */     {
/* 1130:1301 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableBono (Configuraciï¿½n)");
/* 1131:     */     }
/* 1132:1304 */     return lista;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public List<DetalleInterfazContable> getRetencionComercializadora(int idFacturaProveedor)
/* 1136:     */     throws ExcepcionAS2Financiero
/* 1137:     */   {
/* 1138:1316 */     StringBuilder sbSQL = new StringBuilder();
/* 1139:1317 */     sbSQL.append(" SELECT new DetalleInterfazContable(ce.cuentaContable2X1000.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #',fp.numero,' ',fp.descripcion,'-RET 2X1000'), '', fp.retencionComercializadora)");
/* 1140:     */     
/* 1141:1319 */     sbSQL.append(" FROM FacturaProveedor fp");
/* 1142:1320 */     sbSQL.append(" INNER JOIN fp.documento do ");
/* 1143:1321 */     sbSQL.append(" INNER JOIN fp.empresa em ");
/* 1144:1322 */     sbSQL.append(" INNER JOIN em.categoriaEmpresa ce ");
/* 1145:1323 */     sbSQL.append(" WHERE fp.idFacturaProveedor=:idFacturaProveedor");
/* 1146:     */     
/* 1147:1325 */     Query query = this.em.createQuery(sbSQL.toString());
/* 1148:     */     
/* 1149:1327 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1150:     */     try
/* 1151:     */     {
/* 1152:1330 */       return query.getResultList();
/* 1153:     */     }
/* 1154:     */     catch (Exception e)
/* 1155:     */     {
/* 1156:1333 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContable2X1000 (CategoriaEmpresa)");
/* 1157:     */     }
/* 1158:     */   }
/* 1159:     */   
/* 1160:     */   public List<DetalleInterfazContable> getRetencion3X1000(int idFacturaProveedor)
/* 1161:     */     throws ExcepcionAS2Financiero
/* 1162:     */   {
/* 1163:1346 */     StringBuilder sbSQL = new StringBuilder();
/* 1164:1347 */     sbSQL.append(" SELECT new DetalleInterfazContable(ce.cuentaContable3X1000.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #',fp.numero,' ',fp.descripcion,'-RET 3X1000'), '', fp.retencion3X1000)");
/* 1165:     */     
/* 1166:1349 */     sbSQL.append(" FROM FacturaProveedor fp");
/* 1167:1350 */     sbSQL.append(" INNER JOIN fp.documento do ");
/* 1168:1351 */     sbSQL.append(" INNER JOIN fp.empresa em ");
/* 1169:1352 */     sbSQL.append(" INNER JOIN em.categoriaEmpresa ce ");
/* 1170:1353 */     sbSQL.append(" WHERE fp.idFacturaProveedor=:idFacturaProveedor");
/* 1171:     */     
/* 1172:1355 */     Query query = this.em.createQuery(sbSQL.toString());
/* 1173:     */     
/* 1174:1357 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1175:     */     try
/* 1176:     */     {
/* 1177:1360 */       return query.getResultList();
/* 1178:     */     }
/* 1179:     */     catch (Exception e)
/* 1180:     */     {
/* 1181:1363 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContable3X1000 (CategoriaEmpresa)");
/* 1182:     */     }
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public List<DetalleInterfazContable> getRetencionIvaPresuntivo(int idFacturaProveedor)
/* 1186:     */     throws ExcepcionAS2Financiero
/* 1187:     */   {
/* 1188:1376 */     StringBuilder sbSQL = new StringBuilder();
/* 1189:1377 */     sbSQL.append(" SELECT new DetalleInterfazContable(ce.cuentaContableIvaPresuntivo.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #',fp.numero,' ',fp.descripcion,'-RET Iva Presuntivo'), '', fp.retencionIvaPresuntivo)");
/* 1190:     */     
/* 1191:1379 */     sbSQL.append(" FROM FacturaProveedor fp");
/* 1192:1380 */     sbSQL.append(" INNER JOIN fp.documento do ");
/* 1193:1381 */     sbSQL.append(" INNER JOIN fp.empresa em ");
/* 1194:1382 */     sbSQL.append(" INNER JOIN em.categoriaEmpresa ce ");
/* 1195:1383 */     sbSQL.append(" WHERE fp.idFacturaProveedor=:idFacturaProveedor");
/* 1196:     */     
/* 1197:1385 */     Query query = this.em.createQuery(sbSQL.toString());
/* 1198:     */     
/* 1199:1387 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1200:     */     try
/* 1201:     */     {
/* 1202:1390 */       return query.getResultList();
/* 1203:     */     }
/* 1204:     */     catch (Exception e)
/* 1205:     */     {
/* 1206:1393 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContable3X1000 (CategoriaEmpresa)");
/* 1207:     */     }
/* 1208:     */   }
/* 1209:     */   
/* 1210:     */   public Estado getEstado(Integer idFacturaProveedor)
/* 1211:     */   {
/* 1212:1407 */     Query query = this.em.createQuery("SELECT f.estado FROM FacturaProveedor f WHERE f.idFacturaProveedor=:idFacturaProveedor");
/* 1213:1408 */     query.setParameter("idFacturaProveedor", idFacturaProveedor);
/* 1214:1409 */     Estado estado = (Estado)query.getSingleResult();
/* 1215:     */     
/* 1216:1411 */     return estado;
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   public void actualizarEstado(Integer idFacturaProveedor, Estado estado)
/* 1220:     */   {
/* 1221:1421 */     Query query = this.em.createQuery("UPDATE FacturaProveedor f SET f.estado=:estado WHERE f.idFacturaProveedor=:idFacturaProveedor");
/* 1222:1422 */     query.setParameter("idFacturaProveedor", idFacturaProveedor);
/* 1223:1423 */     query.setParameter("estado", estado);
/* 1224:1424 */     query.executeUpdate();
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   public List<DetalleInterfazContable> getInterfazNotaCreditoCuentasImpuesto(int idFacturaProveedor)
/* 1228:     */     throws ExcepcionAS2Financiero
/* 1229:     */   {
/* 1230:     */     try
/* 1231:     */     {
/* 1232:1438 */       List<DetalleInterfazContable> lista = new ArrayList();
/* 1233:     */       
/* 1234:1440 */       String sql = "SELECT new DetalleInterfazContable(ccc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero),'', ROUND(-SUM((dfc.cantidad*(dfc.precio-dfc.descuento))*(ipfc.porcentajeImpuesto/100)),2) ) \tFROM ImpuestoProductoFacturaProveedor ipfc   INNER JOIN  ipfc.impuesto i\tINNER JOIN  ipfc.detalleFacturaProveedor dfc\tINNER JOIN  dfc.facturaProveedor fc\tINNER JOIN  fc.empresa em\tINNER JOIN  fc.documento do LEFT JOIN i.cuentaContableCompra ccc\tWHERE fc.idFacturaProveedor=:idFacturaProveedor GROUP BY ccc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero) HAVING SUM((dfc.cantidad*(dfc.precio-dfc.descuento))*(ipfc.porcentajeImpuesto/100)) !=0";
/* 1235:     */       
/* 1236:     */ 
/* 1237:     */ 
/* 1238:     */ 
/* 1239:     */ 
/* 1240:     */ 
/* 1241:1447 */       Query query = this.em.createQuery(sql);
/* 1242:1448 */       query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1243:1449 */       return query.getResultList();
/* 1244:     */     }
/* 1245:     */     catch (IllegalArgumentException e)
/* 1246:     */     {
/* 1247:1454 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableImpuesto");
/* 1248:     */     }
/* 1249:     */   }
/* 1250:     */   
/* 1251:     */   public List<DetalleInterfazContable> getInterfazNotaCreditoCuentasAnticipo(int idFacturaProveedor)
/* 1252:     */     throws ExcepcionAS2Financiero
/* 1253:     */   {
/* 1254:     */     try
/* 1255:     */     {
/* 1256:1468 */       List<DetalleInterfazContable> lista = new ArrayList();
/* 1257:     */       
/* 1258:1470 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(ccapnc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero),'',(fc.total-fc.descuento+fc.impuesto)) FROM FacturaProveedor fc  INNER JOIN fc.documento do  INNER JOIN fc.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ce.cuentaContableAnticipoProveedorNotaCredito ccapnc  WHERE fc.idFacturaProveedor=:idFacturaProveedor");
/* 1259:     */       
/* 1260:     */ 
/* 1261:     */ 
/* 1262:     */ 
/* 1263:     */ 
/* 1264:1476 */       query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1265:1477 */       return query.getResultList();
/* 1266:     */     }
/* 1267:     */     catch (IllegalArgumentException e)
/* 1268:     */     {
/* 1269:1482 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableAnticipoProveedorNotaCredito");
/* 1270:     */     }
/* 1271:     */   }
/* 1272:     */   
/* 1273:     */   public List<DetalleInterfazContable> getInterfazDevolucionCuentasInventarioCosto(int idFacturaProveedor)
/* 1274:     */     throws ExcepcionAS2Financiero
/* 1275:     */   {
/* 1276:     */     try
/* 1277:     */     {
/* 1278:1490 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(sc.cuentaContableInventario.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero),'',ROUND(-SUM(dfc.cantidad*(dfc.precio-dfc.descuento)),2) )\tFROM DetalleFacturaProveedor dfc\tINNER JOIN dfc.facturaProveedor fc INNER JOIN fc.empresa em \tINNER JOIN fc.documento do\tINNER JOIN dfc.producto pr \tINNER JOIN pr.subcategoriaProducto sc WHERE fc.idFacturaProveedor=:idFacturaProveedor\tGROUP BY sc.cuentaContableInventario.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero) HAVING SUM(dfc.cantidad*(dfc.precio-dfc.descuento))!=0");
/* 1279:     */       
/* 1280:     */ 
/* 1281:     */ 
/* 1282:     */ 
/* 1283:     */ 
/* 1284:     */ 
/* 1285:     */ 
/* 1286:1498 */       query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1287:     */       
/* 1288:1500 */       return query.getResultList();
/* 1289:     */     }
/* 1290:     */     catch (IllegalArgumentException e)
/* 1291:     */     {
/* 1292:1503 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableInventario");
/* 1293:     */     }
/* 1294:     */   }
/* 1295:     */   
/* 1296:     */   public List<DetalleRecepcionProveedor> obtenerDetalleDevolucionProveedor(int id, boolean factura)
/* 1297:     */   {
/* 1298:1516 */     StringBuilder sql = new StringBuilder();
/* 1299:1517 */     sql.append(" SELECT drp FROM DetalleRecepcionProveedor drp ");
/* 1300:1518 */     if (factura)
/* 1301:     */     {
/* 1302:1519 */       sql.append(" JOIN FETCH drp.detalleFacturaProveedor dfp ");
/* 1303:1520 */       sql.append(" JOIN FETCH dfp.producto pr ");
/* 1304:     */     }
/* 1305:     */     else
/* 1306:     */     {
/* 1307:1522 */       sql.append(" JOIN FETCH drp.producto pr ");
/* 1308:1523 */       sql.append(" JOIN FETCH pr.unidadCompra uc ");
/* 1309:     */     }
/* 1310:1525 */     sql.append(" JOIN FETCH pr.unidad u ");
/* 1311:1526 */     sql.append(" JOIN FETCH drp.bodega b ");
/* 1312:1527 */     sql.append(" JOIN FETCH drp.inventarioProducto ip ");
/* 1313:1528 */     sql.append(" LEFT JOIN FETCH ip.lote l ");
/* 1314:1529 */     if (factura) {
/* 1315:1530 */       sql.append(" JOIN FETCH dfp.unidadCompra uc ");
/* 1316:     */     }
/* 1317:1532 */     sql.append(" INNER JOIN drp.recepcionProveedor rp ");
/* 1318:1533 */     if (factura) {
/* 1319:1534 */       sql.append(" INNER JOIN dfp.facturaProveedor fp ");
/* 1320:     */     } else {
/* 1321:1536 */       sql.append(" LEFT JOIN FETCH drp.detallePedidoProveedor dpp");
/* 1322:     */     }
/* 1323:1538 */     sql.append(" WHERE pr.tipoProducto = :tipoProducto");
/* 1324:1539 */     if (factura) {
/* 1325:1540 */       sql.append(" AND fp.idFacturaProveedor = :id");
/* 1326:     */     } else {
/* 1327:1542 */       sql.append(" AND rp.idRecepcionProveedor = :id");
/* 1328:     */     }
/* 1329:1544 */     sql.append(" AND (drp.cantidad - drp.cantidadDevuelta) > 0 ");
/* 1330:1545 */     sql.append(" AND rp.estado != :estado ");
/* 1331:     */     
/* 1332:1547 */     Query query = this.em.createQuery(sql.toString());
/* 1333:1548 */     query.setParameter("id", Integer.valueOf(id));
/* 1334:1549 */     query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 1335:1550 */     query.setParameter("estado", Estado.ANULADO);
/* 1336:     */     
/* 1337:1552 */     return query.getResultList();
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   public List<DetalleFacturaProveedor> obtenerDetalleDevoluciones(FacturaProveedor devolucion)
/* 1341:     */   {
/* 1342:1564 */     int idFacturaProveedor = devolucion.getFacturaProveedorPadre().getId();
/* 1343:1565 */     int idDevolucionProveedor = devolucion.getId();
/* 1344:     */     
/* 1345:1567 */     Query query = this.em.createQuery("SELECT dfc FROM DetalleFacturaProveedor dfc join fetch dfc.producto join fetch dfc.detalleFacturaProveedorPadre WHERE dfc.detalleFacturaProveedorPadre.facturaProveedor.idFacturaProveedor=:idFacturaProveedor AND dfc.facturaProveedor.idFacturaProveedor!=:idDevolucionProveedor AND dfc.facturaProveedor.estado!=:estadoAnulado");
/* 1346:     */     
/* 1347:     */ 
/* 1348:     */ 
/* 1349:1571 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1350:1572 */     query.setParameter("idDevolucionProveedor", Integer.valueOf(idDevolucionProveedor));
/* 1351:1573 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1352:     */     
/* 1353:1575 */     return query.getResultList();
/* 1354:     */   }
/* 1355:     */   
/* 1356:     */   public long verificaExistenciaNumero(FacturaProveedor facturaProveedor)
/* 1357:     */   {
/* 1358:1579 */     StringBuilder sql = new StringBuilder();
/* 1359:1580 */     sql.append("SELECT COUNT(*) FROM FacturaProveedor f LEFT JOIN f.facturaProveedorSRI fpSRI  WHERE f.idFacturaProveedor != :idFacturaProveedor AND f.numero =:numero AND f.idOrganizacion = :idOrganizacion AND f.documento.idDocumento = :idDocumento ");
/* 1360:     */     
/* 1361:     */ 
/* 1362:     */ 
/* 1363:1584 */     Query query = this.em.createQuery(sql.toString());
/* 1364:1585 */     query.setParameter("numero", facturaProveedor.getNumero());
/* 1365:1586 */     query.setParameter("idFacturaProveedor", Integer.valueOf(facturaProveedor.getIdFacturaProveedor()));
/* 1366:1587 */     query.setParameter("idOrganizacion", Integer.valueOf(facturaProveedor.getIdOrganizacion()));
/* 1367:1588 */     query.setParameter("idDocumento", Integer.valueOf(facturaProveedor.getDocumento().getIdDocumento()));
/* 1368:     */     
/* 1369:1590 */     return ((Long)query.getSingleResult()).longValue();
/* 1370:     */   }
/* 1371:     */   
/* 1372:     */   public FacturaProveedor buscarPorRecepcionProveedor(int idRecepcionProveedor)
/* 1373:     */   {
/* 1374:1600 */     StringBuilder sql = new StringBuilder();
/* 1375:1601 */     sql.append("SELECT f FROM FacturaProveedor f");
/* 1376:1602 */     sql.append(" INNER JOIN f.documento d");
/* 1377:1603 */     sql.append(" WHERE f.recepcionProveedor.idRecepcionProveedor = :idRecepcionProveedor");
/* 1378:1604 */     sql.append(" AND f.estado!=:estadoAnulado");
/* 1379:1605 */     sql.append(" AND (d.documentoBase=:documentoFacturaProveedor or d.documentoBase=:documentoFacturaProveedorImportacion)");
/* 1380:1606 */     Query query = this.em.createQuery(sql.toString());
/* 1381:1607 */     query.setParameter("idRecepcionProveedor", Integer.valueOf(idRecepcionProveedor));
/* 1382:1608 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1383:1609 */     query.setParameter("documentoFacturaProveedor", DocumentoBase.FACTURA_PROVEEDOR);
/* 1384:1610 */     query.setParameter("documentoFacturaProveedorImportacion", DocumentoBase.PEDIDO_IMPORTACION);
/* 1385:     */     
/* 1386:1612 */     query.setMaxResults(1);
/* 1387:     */     try
/* 1388:     */     {
/* 1389:1615 */       return (FacturaProveedor)query.getSingleResult();
/* 1390:     */     }
/* 1391:     */     catch (NoResultException e) {}
/* 1392:1617 */     return null;
/* 1393:     */   }
/* 1394:     */   
/* 1395:     */   public FacturaProveedor cargarDetalleARecibir(Integer idFacturaProveedor)
/* 1396:     */   {
/* 1397:1630 */     FacturaProveedor facturaProveedor = (FacturaProveedor)buscarPorId(idFacturaProveedor);
/* 1398:1631 */     facturaProveedor.getDocumento().getId();
/* 1399:1633 */     if (facturaProveedor.getDocumento().isIndicadorDocumentoExterior()) {
/* 1400:1635 */       if (facturaProveedor.getFacturaProveedorImportacion() != null) {
/* 1401:1636 */         facturaProveedor.getFacturaProveedorImportacion().getId();
/* 1402:     */       }
/* 1403:     */     }
/* 1404:1640 */     if (facturaProveedor.getProyecto() != null) {
/* 1405:1641 */       facturaProveedor.getProyecto().getId();
/* 1406:     */     }
/* 1407:1644 */     if (facturaProveedor.getPersonaResponsable() != null) {
/* 1408:1645 */       facturaProveedor.getPersonaResponsable().getId();
/* 1409:     */     }
/* 1410:1648 */     facturaProveedor.getEmpresa().getId();
/* 1411:     */     
/* 1412:     */ 
/* 1413:1651 */     facturaProveedor.getListaDetalleFacturaProveedor().size();
/* 1414:1653 */     for (DetalleFacturaProveedor detalleFacturaProveedor : facturaProveedor.getListaDetalleFacturaProveedor())
/* 1415:     */     {
/* 1416:1654 */       detalleFacturaProveedor.getProducto().getId();
/* 1417:1655 */       if (detalleFacturaProveedor.getProducto().getBodegaCompra() != null) {
/* 1418:1656 */         detalleFacturaProveedor.getProducto().getBodegaCompra().getId();
/* 1419:     */       }
/* 1420:1658 */       detalleFacturaProveedor.getUnidadCompra().getId();
/* 1421:1660 */       if (detalleFacturaProveedor.getDetallePedidoProveedor() != null)
/* 1422:     */       {
/* 1423:1661 */         detalleFacturaProveedor.getDetallePedidoProveedor().getId();
/* 1424:1662 */         detalleFacturaProveedor.getDetallePedidoProveedor().getPedidoProveedor().getId();
/* 1425:     */       }
/* 1426:     */     }
/* 1427:1665 */     return facturaProveedor;
/* 1428:     */   }
/* 1429:     */   
/* 1430:     */   public List<DetalleInterfazContable> getFacturaProveedorNDIC(int idFacturaProveedor)
/* 1431:     */   {
/* 1432:1676 */     StringBuilder sql = new StringBuilder();
/* 1433:1677 */     sql.append("SELECT new DetalleInterfazContable(ccpr.idCuentaContable, ");
/* 1434:1678 */     sql.append(" em.nombreFiscal,CONCAT(do.nombre, ' #', fp.numero),'',fp.total) ");
/* 1435:1679 */     sql.append(" FROM FacturaProveedor fp ");
/* 1436:1680 */     sql.append(" INNER JOIN fp.empresa em ");
/* 1437:1681 */     sql.append(" INNER JOIN em.categoriaEmpresa cem ");
/* 1438:1682 */     sql.append(" INNER JOIN cem.cuentaContableProveedor ccpr ");
/* 1439:1683 */     sql.append(" INNER JOIN fp.documento do ");
/* 1440:1684 */     sql.append(" WHERE fp.idFacturaProveedor=:idFacturaProveedor");
/* 1441:     */     
/* 1442:1686 */     Query query = this.em.createQuery(sql.toString());
/* 1443:1687 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1444:     */     
/* 1445:1689 */     return query.getResultList();
/* 1446:     */   }
/* 1447:     */   
/* 1448:     */   public List<DetalleInterfazContable> getFacturaProveedorCPIC(int idFacturaProveedor)
/* 1449:     */   {
/* 1450:1700 */     StringBuilder sql = new StringBuilder();
/* 1451:1701 */     sql.append("SELECT new DetalleInterfazContable(ccpr.idCuentaContable, ");
/* 1452:1702 */     sql.append(" em.nombreFiscal,CONCAT(do.nombre, ' #', fp.numero),'',-fp.total) ");
/* 1453:1703 */     sql.append(" FROM FacturaProveedor fp ");
/* 1454:1704 */     sql.append(" INNER JOIN fp.empresa em ");
/* 1455:1705 */     sql.append(" INNER JOIN em.categoriaEmpresa cem ");
/* 1456:1706 */     sql.append(" INNER JOIN cem.cuentaContableProveedor ccpr ");
/* 1457:1707 */     sql.append(" INNER JOIN fp.documento do ");
/* 1458:1708 */     sql.append(" WHERE fp.idFacturaProveedor=:idFacturaProveedor");
/* 1459:     */     
/* 1460:1710 */     Query query = this.em.createQuery(sql.toString());
/* 1461:1711 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1462:     */     
/* 1463:1713 */     return query.getResultList();
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public Object[] getDatosFacturaImpresionAsiento(int idAsiento)
/* 1467:     */   {
/* 1468:1724 */     StringBuilder sbSQL = new StringBuilder();
/* 1469:1725 */     sbSQL.append(" SELECT CONCAT(fpSRI.establecimiento,'-',fpSRI.puntoEmision,'-',fpSRI.numero), em.identificacion,em.nombreFiscal, ");
/* 1470:1726 */     sbSQL.append(" CONCAT(ub.direccion1,' ',ub.direccion2,' ',ub.direccion3,' ',ub.direccion4), ");
/* 1471:1727 */     sbSQL.append(" CONCAT(fpSRI.establecimientoRetencion,'-',fpSRI.puntoEmisionRetencion,'-',fpSRI.numeroRetencion) ");
/* 1472:1728 */     sbSQL.append(" FROM FacturaProveedor fp ");
/* 1473:1729 */     sbSQL.append(" LEFT JOIN fp.empresa em ");
/* 1474:1730 */     sbSQL.append(" LEFT JOIN fp.direccionEmpresa dir ");
/* 1475:1731 */     sbSQL.append(" LEFT JOIN dir.ubicacion ub ");
/* 1476:1732 */     sbSQL.append(" LEFT JOIN fp.facturaProveedorSRI fpSRI");
/* 1477:1733 */     sbSQL.append(" WHERE fp.asiento.idAsiento=:idAsiento");
/* 1478:     */     try
/* 1479:     */     {
/* 1480:1736 */       Query query = this.em.createQuery(sbSQL.toString());
/* 1481:1737 */       query.setParameter("idAsiento", Integer.valueOf(idAsiento));
/* 1482:     */       
/* 1483:1739 */       return (Object[])query.getSingleResult();
/* 1484:     */     }
/* 1485:     */     catch (NoResultException e) {}
/* 1486:1741 */     return null;
/* 1487:     */   }
/* 1488:     */   
/* 1489:     */   public List<Object[]> getReporteNotaCreditoProveedor(int idFacturaProveedor)
/* 1490:     */   {
/* 1491:1747 */     StringBuilder sql = new StringBuilder();
/* 1492:1748 */     sql.append(" SELECT e.nombreFiscal, CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4,' ',u.direccion5), ");
/* 1493:1749 */     sql.append(" e.identificacion, f.fecha, d.cantidad, pr.codigo, pr.nombreComercial, d.precio, f.total, f.descuento, f.impuesto, ");
/* 1494:1750 */     sql.append(" de.telefono1, f.descripcion, YEAR(f.fecha), MONTH(f.fecha), DAY(f.fecha), d.descripcion, ");
/* 1495:1751 */     sql.append(" e.codigo, ci.nombre, un.nombre, pr.peso, ");
/* 1496:1752 */     sql.append(" pr.codigoAlterno, pr.codigoComercial, pr.codigoBarras, case when fpsri is null then fp.numero else concat(fpsri.establecimiento, '-', fpsri.puntoEmision,'-',fpsri.numero) end, e.nombreComercial,fp.numero,mnc.nombre,b.nombre,fpsri.numero  ");
/* 1497:     */     
/* 1498:1754 */     sql.append(" FROM DetalleFacturaProveedor d ");
/* 1499:1755 */     sql.append(" LEFT JOIN d.facturaProveedor f ");
/* 1500:1756 */     sql.append(" LEFT JOIN f.facturaProveedorPadre fp ");
/* 1501:1757 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fpsri ");
/* 1502:1758 */     sql.append(" LEFT JOIN f.motivoNotaCreditoProveedor mnc ");
/* 1503:1759 */     sql.append(" LEFT JOIN f.empresa e ");
/* 1504:1760 */     sql.append(" LEFT JOIN f.direccionEmpresa de  ");
/* 1505:1761 */     sql.append(" LEFT JOIN de.ubicacion u ");
/* 1506:1762 */     sql.append(" LEFT JOIN de.ciudad ci ");
/* 1507:1763 */     sql.append(" LEFT JOIN d.producto pr  ");
/* 1508:1764 */     sql.append(" LEFT JOIN d.unidadCompra un ");
/* 1509:1765 */     sql.append(" LEFT JOIN d.bodega b ");
/* 1510:1766 */     sql.append(" WHERE f.idFacturaProveedor = :idFacturaProveedor ");
/* 1511:     */     
/* 1512:1768 */     Query query = this.em.createQuery(sql.toString());
/* 1513:1769 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1514:1770 */     return query.getResultList();
/* 1515:     */   }
/* 1516:     */   
/* 1517:     */   public void bloquearFactura(int idFacturaProveedor, boolean bloqueo)
/* 1518:     */   {
/* 1519:1775 */     StringBuilder sql = new StringBuilder();
/* 1520:1776 */     sql.append("UPDATE CuentaPorPagar cxp SET cxp.indicadorBloqueada = :bloqueo ");
/* 1521:1777 */     sql.append(" WHERE cxp.facturaProveedor.idFacturaProveedor=:idFacturaProveedor");
/* 1522:1778 */     sql.append(" AND cxp.indicadorBloqueada!=:bloqueo");
/* 1523:1779 */     Query query = this.em.createQuery(sql.toString());
/* 1524:1780 */     query.setParameter("bloqueo", Boolean.valueOf(bloqueo));
/* 1525:1781 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1526:1782 */     query.executeUpdate();
/* 1527:     */   }
/* 1528:     */   
/* 1529:     */   public List<DetalleInterfazContableProceso> getInterfazFacturaProveedorDimensiones(List<Integer> listaFacturaProveedor, ProcesoContabilizacionEnum procesoContabilizacionEnum)
/* 1530:     */   {
/* 1531:1801 */     List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 1532:     */     
/* 1533:1803 */     String valores = "";
/* 1534:1804 */     String filtro = "";
/* 1535:1805 */     String impuestoComercializadora = "";
/* 1536:1806 */     String filTipProducto = " (p.tipoProducto <> :pTipoServicio1 AND p.tipoProducto <> :pTipoServicio2) ";
/* 1537:     */     
/* 1538:1808 */     String descripcion = "";
/* 1539:1809 */     String grupoDescripcion = "";
/* 1540:1810 */     descripcion = "CONCAT(d.nombre,' #',fp.numero, CASE WHEN fps IS NOT NULL THEN CONCAT(' F:/',fps.establecimiento,'-',fps.puntoEmision,'-',fps.numero) ELSE '' END,' ',fp.descripcion,' - ',e.nombreFiscal)";
/* 1541:1811 */     grupoDescripcion = "," + descripcion;
/* 1542:1812 */     String impuesto = " i.idImpuesto, i.nombre,";
/* 1543:1813 */     String grupoImpuesto = impuesto;
/* 1544:1814 */     StringBuilder from = new StringBuilder();
/* 1545:1815 */     from.append(" FROM ImpuestoProductoFacturaProveedor ipfp ");
/* 1546:1816 */     from.append(" RIGHT OUTER JOIN ipfp.impuesto i ");
/* 1547:1817 */     from.append(" RIGHT JOIN ipfp.detalleFacturaProveedor dfp ");
/* 1548:1818 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$ProcesoContabilizacionEnum[procesoContabilizacionEnum.ordinal()])
/* 1549:     */     {
/* 1550:     */     case 1: 
/* 1551:1824 */       valores = "sum((dfp.cantidad*(dfp.precio-dfp.descuento))+dfp.valorImpuestosLinea-dfp.valorDescuentoImpuestosLinea)";
/* 1552:1825 */       impuestoComercializadora = ",(fp.retencionComercializadora + fp.retencion3X1000 + fp.retencionIvaPresuntivo) ";
/* 1553:1826 */       impuesto = " 0, '',";
/* 1554:1827 */       grupoImpuesto = "";
/* 1555:1828 */       from = new StringBuilder();
/* 1556:1829 */       from.append(" FROM DetalleFacturaProveedor dfp ");
/* 1557:1830 */       break;
/* 1558:     */     case 2: 
/* 1559:1833 */       valores = "sum(CASE WHEN fp.indicadorCreditoTributario = false THEN (dfp.cantidad*((dfp.precio-dfp.descuento)*(1+(coalesce(ipfp.porcentajeImpuesto-ipfp.porcentajeDescuentoImpuesto,0)/100)))) ELSE (dfp.cantidad*dfp.precio)END) ";
/* 1560:1834 */       filtro = " AND " + filTipProducto + " and dfp.indicadorGastoImportacion = false ";
/* 1561:1835 */       break;
/* 1562:     */     case 3: 
/* 1563:1838 */       valores = "SUM(CASE WHEN fp.indicadorCreditoTributario = false THEN (dfp.cantidad*((dfp.precio-dfp.descuento)*(1+(COALESCE(ipfp.porcentajeImpuesto-ipfp.porcentajeDescuentoImpuesto,0)/100)))) ELSE (dfp.cantidad*dfp.precio)END) ";
/* 1564:1839 */       filtro = " AND (p.tipoProducto =:pTipoServicio1 OR p.tipoProducto =:pTipoServicio2) AND dfp.indicadorGastoImportacion = false AND EXISTS (SELECT 1 FROM DetalleRecepcionProveedor drp WHERE drp.detalleFacturaProveedor = dfp) ";
/* 1565:1840 */       break;
/* 1566:     */     case 4: 
/* 1567:1843 */       valores = "sum(dfp.cantidad*((dfp.precio-dfp.descuento)*(coalesce(ipfp.porcentajeImpuesto,0)/100)))";
/* 1568:     */       
/* 1569:1845 */       filtro = " AND fp.indicadorCreditoTributario = true AND (" + filTipProducto + " OR dfp.indicadorGastoImportacion = true OR EXISTS (SELECT 1 FROM DetalleRecepcionProveedor drp WHERE drp.detalleFacturaProveedor = dfp)) ";
/* 1570:     */       
/* 1571:1847 */       break;
/* 1572:     */     case 5: 
/* 1573:1850 */       valores = "sum(dfp.cantidad*((dfp.precio-dfp.descuento)*(coalesce(ipfp.porcentajeDescuentoImpuesto,0)/100)))";
/* 1574:1851 */       filtro = " AND ipfp.porcentajeDescuentoImpuesto != 0 ";
/* 1575:1852 */       break;
/* 1576:     */     case 6: 
/* 1577:1855 */       valores = "sum(dfp.cantidad*dfp.descuento)";
/* 1578:1856 */       filtro = " AND " + filTipProducto;
/* 1579:1857 */       break;
/* 1580:     */     default: 
/* 1581:1859 */       return lista;
/* 1582:     */     }
/* 1583:1863 */     StringBuilder sql = new StringBuilder();
/* 1584:     */     
/* 1585:1865 */     sql.append("SELECT new DetalleInterfazContableProcesoFacturaProveedor(d.idDocumento, d.nombre, s.idSucursal, s.nombre, ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa,");
/* 1586:     */     
/* 1587:1867 */     sql.append(" e.nombreFiscal, cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, " + impuesto + descripcion + ", " + descripcion + "," + valores + impuestoComercializadora + " )");
/* 1588:     */     
/* 1589:1869 */     sql.append(from.toString());
/* 1590:1870 */     sql.append(" INNER JOIN dfp.producto p ");
/* 1591:1871 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 1592:1872 */     sql.append(" INNER JOIN sp.categoriaProducto cp ");
/* 1593:1873 */     sql.append(" INNER JOIN dfp.facturaProveedor fp ");
/* 1594:1874 */     sql.append(" INNER JOIN fp.documento d ");
/* 1595:1875 */     sql.append(" INNER JOIN fp.sucursal s ");
/* 1596:1876 */     sql.append(" INNER JOIN fp.empresa e ");
/* 1597:1877 */     sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/* 1598:1878 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fps ");
/* 1599:1879 */     sql.append(" LEFT JOIN fp.recepcionProveedor rp");
/* 1600:1880 */     sql.append(" WHERE fp.idFacturaProveedor in (:listaFacturaProveedor) " + filtro);
/* 1601:1881 */     sql.append(" GROUP BY d.idDocumento, d.nombre, s.idSucursal, s.nombre, ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa,");
/* 1602:1882 */     sql.append(" e.nombreFiscal, cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, " + grupoImpuesto);
/* 1603:     */     
/* 1604:1884 */     sql.append(" fp.retencionComercializadora, fp.retencion3X1000, fp.retencionIvaPresuntivo " + grupoDescripcion);
/* 1605:1885 */     sql.append(" HAVING " + valores + " <> 0");
/* 1606:     */     
/* 1607:1887 */     Query query = this.em.createQuery(sql.toString());
/* 1608:1888 */     query.setParameter("listaFacturaProveedor", listaFacturaProveedor);
/* 1609:1889 */     if ((!procesoContabilizacionEnum.equals(ProcesoContabilizacionEnum.CXP_PROVEEDOR)) && (procesoContabilizacionEnum != ProcesoContabilizacionEnum.DESCUENTO_IMPUESTO_COMPRAS))
/* 1610:     */     {
/* 1611:1891 */       query.setParameter("pTipoServicio1", TipoProducto.SERVICIO);
/* 1612:1892 */       query.setParameter("pTipoServicio2", TipoProducto.ARTICULO_NO_INVENTARIABLE);
/* 1613:     */     }
/* 1614:1894 */     lista.addAll(query.getResultList());
/* 1615:1895 */     return lista;
/* 1616:     */   }
/* 1617:     */   
/* 1618:     */   public List<DetalleInterfazContableProceso> getInterfazNotaCreditoDimensiones(List<Integer> listaNotaCreditoProveedor, ProcesoContabilizacionEnum procesoContabilizacionEnum, boolean reversaGasto)
/* 1619:     */     throws ExcepcionAS2Financiero
/* 1620:     */   {
/* 1621:1911 */     String valores = "";
/* 1622:1912 */     String condicionImpuesto = "";
/* 1623:1913 */     if (reversaGasto) {
/* 1624:1914 */       condicionImpuesto = " AND p.tipoProducto = :tipoArticulo";
/* 1625:     */     }
/* 1626:1916 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$ProcesoContabilizacionEnum[procesoContabilizacionEnum.ordinal()])
/* 1627:     */     {
/* 1628:     */     case 7: 
/* 1629:1918 */       valores = "sum(dfp.cantidad*(dfp.precio-dfp.descuento) + (case when fpp.indicadorCreditoTributario = false then (dfp.cantidad*((dfp.precio-dfp.descuento)*(coalesce(ipfp.porcentajeImpuesto,0)/100))) else (dfp.cantidad*0) end)) ";
/* 1630:1919 */       break;
/* 1631:     */     case 6: 
/* 1632:1922 */       valores = "sum(dfp.cantidad*(dfp.precio-dfp.descuento) + (case when fpp.indicadorCreditoTributario = false then (dfp.cantidad*((dfp.precio-dfp.descuento)*(coalesce(ipfp.porcentajeImpuesto,0)/100))) else (dfp.cantidad*0) end)) ";
/* 1633:1923 */       break;
/* 1634:     */     case 4: 
/* 1635:1926 */       valores = " sum(case when fpp.indicadorCreditoTributario = false then (dfp.cantidad*0) else (dfp.cantidad*((dfp.precio-dfp.descuento)*(coalesce(ipfp.porcentajeImpuesto,0)/100))) end) ";
/* 1636:1927 */       condicionImpuesto = " AND p.tipoProducto = :tipoArticulo";
/* 1637:1928 */       break;
/* 1638:     */     }
/* 1639:1934 */     StringBuilder sql = new StringBuilder();
/* 1640:     */     
/* 1641:1936 */     String descripcion = "";
/* 1642:1937 */     String grupoDescripcion = "";
/* 1643:1938 */     if (listaNotaCreditoProveedor.size() == 1)
/* 1644:     */     {
/* 1645:1939 */       descripcion = "CONCAT(d.nombre,' #', fp.numero)";
/* 1646:1940 */       grupoDescripcion = "," + descripcion;
/* 1647:     */     }
/* 1648:     */     else
/* 1649:     */     {
/* 1650:1942 */       descripcion = "''";
/* 1651:     */     }
/* 1652:1944 */     sql.append("SELECT new DetalleInterfazContableProcesoNotaCreditoProveedor(d.idDocumento, s.idSucursal, ce.idCategoriaEmpresa, e.idEmpresa,");
/* 1653:1945 */     sql.append(" cp.idCategoriaProducto, sp.idSubcategoriaProducto, p.idProducto, m.idMotivoNotaCreditoProveedor,");
/* 1654:1946 */     sql.append(" i.idImpuesto, " + descripcion + ", " + valores + " )");
/* 1655:1947 */     sql.append(" FROM ImpuestoProductoFacturaProveedor ipfp ");
/* 1656:1948 */     sql.append(" LEFT OUTER JOIN ipfp.impuesto i");
/* 1657:1949 */     sql.append(" RIGHT JOIN ipfp.detalleFacturaProveedor dfp ");
/* 1658:1950 */     sql.append(" INNER JOIN dfp.facturaProveedor fp ");
/* 1659:1951 */     sql.append(" LEFT JOIN fp.facturaProveedorPadre fpp ");
/* 1660:1952 */     sql.append(" INNER JOIN dfp.producto p ");
/* 1661:1953 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 1662:1954 */     sql.append(" INNER JOIN sp.categoriaProducto cp ");
/* 1663:1955 */     sql.append(" INNER JOIN fp.documento d ");
/* 1664:1956 */     sql.append(" INNER JOIN fp.empresa e ");
/* 1665:1957 */     sql.append(" INNER JOIN fp.sucursal s ");
/* 1666:1958 */     sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/* 1667:1959 */     sql.append(" LEFT JOIN fp.motivoNotaCreditoProveedor m ");
/* 1668:1960 */     sql.append(" WHERE fp.idFacturaProveedor in (:listaFacturaProveedor)" + condicionImpuesto);
/* 1669:1961 */     sql.append(" GROUP BY d.idDocumento, s.idSucursal, ce.idCategoriaEmpresa, e.idEmpresa,");
/* 1670:1962 */     sql.append(" cp.idCategoriaProducto, sp.idSubcategoriaProducto, p.idProducto, m.idMotivoNotaCreditoProveedor, ");
/* 1671:1963 */     sql.append(" i.idImpuesto" + grupoDescripcion);
/* 1672:1964 */     sql.append(" HAVING " + valores + " <> 0");
/* 1673:     */     
/* 1674:1966 */     Query query = this.em.createQuery(sql.toString());
/* 1675:1967 */     query.setParameter("listaFacturaProveedor", listaNotaCreditoProveedor);
/* 1676:1968 */     if ((reversaGasto) || (procesoContabilizacionEnum == ProcesoContabilizacionEnum.IMPUESTO_COMPRAS)) {
/* 1677:1969 */       query.setParameter("tipoArticulo", TipoProducto.ARTICULO);
/* 1678:     */     }
/* 1679:1971 */     return query.getResultList();
/* 1680:     */   }
/* 1681:     */   
/* 1682:     */   public List<FacturaProveedor> autocompletarFacturaProveedorDevolucion(int idEmpresa, Map<String, String> filter)
/* 1683:     */   {
/* 1684:1978 */     Map<String, String> hmParametros = new HashMap();
/* 1685:1979 */     String filterValue = null;
/* 1686:     */     
/* 1687:1981 */     StringBuilder sql = new StringBuilder();
/* 1688:1982 */     sql.append(" SELECT fp FROM FacturaProveedor fp ");
/* 1689:1983 */     sql.append(" INNER JOIN fp.empresa em ");
/* 1690:1984 */     sql.append(" INNER JOIN fp.facturaProveedorSRI fps ");
/* 1691:1985 */     sql.append(" LEFT  JOIN fp.recepcionProveedor rp ");
/* 1692:1986 */     sql.append(" WHERE em.idEmpresa = :idEmpresa ");
/* 1693:1987 */     sql.append(" AND fp.documento.documentoBase = :documentoBase ");
/* 1694:1988 */     sql.append(" AND fp.estado != :estado ");
/* 1695:1989 */     sql.append(" AND rp != null ");
/* 1696:     */     String parametro;
/* 1697:1991 */     if (filter != null)
/* 1698:     */     {
/* 1699:1992 */       parametro = "parametro";
/* 1700:1993 */       for (String filterProperty : filter.keySet()) {
/* 1701:1994 */         if (filterProperty.equals("numero"))
/* 1702:     */         {
/* 1703:1995 */           hmParametros.put(parametro, filter.get(filterProperty));
/* 1704:1996 */           sql.append(" AND (fp.numero LIKE :" + parametro + " OR fps.numero LIKE:" + parametro + ") ");
/* 1705:     */         }
/* 1706:     */       }
/* 1707:     */     }
/* 1708:2001 */     sql.append(" ORDER BY fp.fecha DESC ");
/* 1709:     */     
/* 1710:2003 */     Query query = this.em.createQuery(sql.toString());
/* 1711:2004 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 1712:2005 */     query.setParameter("estado", Estado.ANULADO);
/* 1713:2006 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_PROVEEDOR);
/* 1714:2008 */     for (String parametro : hmParametros.keySet())
/* 1715:     */     {
/* 1716:2009 */       filterValue = (String)hmParametros.get(parametro);
/* 1717:2010 */       query.setParameter(parametro, "%" + filterValue + "%");
/* 1718:     */     }
/* 1719:2013 */     return query.getResultList();
/* 1720:     */   }
/* 1721:     */   
/* 1722:     */   public List<DetalleFacturaProveedor> buscarDetallesNoDespachados(Integer idFacturaProveedor)
/* 1723:     */   {
/* 1724:2023 */     StringBuilder sql = new StringBuilder();
/* 1725:     */     
/* 1726:2025 */     sql.append("SELECT DISTINCT(dfp) ");
/* 1727:2026 */     sql.append(" FROM DetalleFacturaProveedor dfp ");
/* 1728:2027 */     sql.append(" INNER JOIN FETCH dfp.facturaProveedor fp ");
/* 1729:2028 */     sql.append(" LEFT JOIN FETCH fp.documento doc ");
/* 1730:2029 */     sql.append(" LEFT JOIN FETCH dfp.detallePedidoProveedor dpp ");
/* 1731:2030 */     sql.append(" LEFT JOIN FETCH dfp.unidadCompra uc ");
/* 1732:2031 */     sql.append(" LEFT JOIN FETCH dpp.pedidoProveedor pp ");
/* 1733:2032 */     sql.append(" LEFT JOIN FETCH dfp.producto p ");
/* 1734:2033 */     sql.append(" LEFT JOIN FETCH p.unidadCompra uv ");
/* 1735:2034 */     sql.append(" LEFT JOIN FETCH p.bodegaCompra bc ");
/* 1736:2035 */     sql.append(" LEFT JOIN FETCH dfp.listaDetalleRecepcionProveedor drp ");
/* 1737:2036 */     sql.append(" LEFT JOIN FETCH drp.recepcionProveedor rp ");
/* 1738:2037 */     sql.append(" WHERE fp.idFacturaProveedor = :idFacturaProveedor ");
/* 1739:2038 */     sql.append(" AND (dfp.listaDetalleRecepcionProveedor IS EMPTY OR rp.estado = :estadoAnulado) ");
/* 1740:2039 */     sql.append(" AND p.tipoProducto = :tipoArticulo ");
/* 1741:     */     
/* 1742:2041 */     Query query = this.em.createQuery(sql.toString());
/* 1743:2042 */     query.setParameter("idFacturaProveedor", idFacturaProveedor);
/* 1744:2043 */     query.setParameter("tipoArticulo", TipoProducto.ARTICULO);
/* 1745:2044 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1746:     */     
/* 1747:2046 */     return query.getResultList();
/* 1748:     */   }
/* 1749:     */   
/* 1750:     */   public void eliminarDetalleFacturaProveedorImportacion(DetalleFacturaProveedor detalleFacturaProveedor)
/* 1751:     */   {
/* 1752:2050 */     StringBuilder sql = new StringBuilder();
/* 1753:2051 */     sql.append(" DELETE DetalleFacturaProveedorImportacionProducto dfpip ");
/* 1754:2052 */     sql.append(" WHERE dfpip.detalleFacturaProveedorImportacion.idDetalleFacturaProveedorImportacion in  ");
/* 1755:2053 */     sql.append(" ( ");
/* 1756:2054 */     sql.append(" \tSELECT dfpi.idDetalleFacturaProveedorImportacion FROM DetalleFacturaProveedorImportacion dfpi  ");
/* 1757:2055 */     sql.append(" \tWHERE dfpi.detalleFacturaProveedor = :detalleFacturaProveedor ");
/* 1758:2056 */     sql.append(" )");
/* 1759:2057 */     Query query = this.em.createQuery(sql.toString());
/* 1760:2058 */     query.setParameter("detalleFacturaProveedor", detalleFacturaProveedor);
/* 1761:2059 */     query.executeUpdate();
/* 1762:     */     
/* 1763:2061 */     sql = new StringBuilder();
/* 1764:2062 */     sql.append(" DELETE DetalleFacturaProveedorImportacion dfpi ");
/* 1765:2063 */     sql.append(" WHERE dfpi.detalleFacturaProveedor = :detalleFacturaProveedor");
/* 1766:     */     
/* 1767:2065 */     query = this.em.createQuery(sql.toString());
/* 1768:2066 */     query.setParameter("detalleFacturaProveedor", detalleFacturaProveedor);
/* 1769:2067 */     query.executeUpdate();
/* 1770:     */   }
/* 1771:     */   
/* 1772:     */   public void liberarRecepcionFacturaProveedor(RecepcionProveedor recepcionProveedor)
/* 1773:     */   {
/* 1774:2071 */     Query query = this.em.createQuery("UPDATE DetalleRecepcionProveedor drp SET drp.detalleFacturaProveedor = null WHERE drp.recepcionProveedor = :recepcionProveedor");
/* 1775:     */     
/* 1776:2073 */     query.setParameter("recepcionProveedor", recepcionProveedor);
/* 1777:2074 */     query.executeUpdate();
/* 1778:     */   }
/* 1779:     */   
/* 1780:     */   public List<DetalleFacturaProveedorImportacion> verificarFacturaProveedor(int idFacturaProveedor)
/* 1781:     */   {
/* 1782:2079 */     StringBuilder sql = new StringBuilder();
/* 1783:2080 */     sql.append("SELECT dfpi FROM DetalleFacturaProveedorImportacion dfpi INNER JOIN dfpi.facturaProveedor fp WHERE fp.idFacturaProveedor = :idFacturaProveedor");
/* 1784:     */     
/* 1785:2082 */     Query query = this.em.createQuery(sql.toString());
/* 1786:2083 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1787:     */     
/* 1788:2085 */     return query.getResultList();
/* 1789:     */   }
/* 1790:     */   
/* 1791:     */   public List<DetalleFacturaProveedor> getDetallesDevolucionesProveedor(int idDetalleRecepcionProveedor)
/* 1792:     */   {
/* 1793:2091 */     StringBuilder sql = new StringBuilder();
/* 1794:2092 */     sql.append("SELECT dfp");
/* 1795:2093 */     sql.append(" FROM DetalleFacturaProveedor dfp");
/* 1796:2094 */     sql.append(" INNER JOIN FETCH dfp.inventarioProducto ip");
/* 1797:2095 */     sql.append(" INNER JOIN dfp.facturaProveedor fp");
/* 1798:2096 */     sql.append(" INNER JOIN dfp.detalleRecepcionProveedorDevolucion drpd");
/* 1799:2097 */     sql.append(" INNER JOIN fp.documento d");
/* 1800:2098 */     sql.append(" WHERE drpd.idDetalleRecepcionProveedor = :idDetalleRecepcionProveedor");
/* 1801:2099 */     sql.append(" AND d.documentoBase = :documetoDevolucionProveedor");
/* 1802:2100 */     sql.append(" AND fp.estado != :estadoAnulado");
/* 1803:2101 */     Query query = this.em.createQuery(sql.toString());
/* 1804:2102 */     query.setParameter("idDetalleRecepcionProveedor", Integer.valueOf(idDetalleRecepcionProveedor));
/* 1805:2103 */     query.setParameter("documetoDevolucionProveedor", DocumentoBase.DEVOLUCION_PROVEEDOR);
/* 1806:2104 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1807:2105 */     return query.getResultList();
/* 1808:     */   }
/* 1809:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.FacturaProveedorDao
 * JD-Core Version:    0.7.0.1
 */