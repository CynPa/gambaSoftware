/*    1:     */ package com.asinfo.as2.dao.reportes.compras;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.ReporteAnticipoProveedor;
/*    4:     */ import com.asinfo.as2.clases.ReporteAsiento;
/*    5:     */ import com.asinfo.as2.clases.ReporteFacturaProveedor;
/*    6:     */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*    7:     */ import com.asinfo.as2.entities.Atributo;
/*    8:     */ import com.asinfo.as2.entities.Bodega;
/*    9:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   10:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   11:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   12:     */ import com.asinfo.as2.entities.Organizacion;
/*   13:     */ import com.asinfo.as2.entities.Producto;
/*   14:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   15:     */ import com.asinfo.as2.entities.Sucursal;
/*   16:     */ import com.asinfo.as2.entities.ValorAtributo;
/*   17:     */ import com.asinfo.as2.enumeraciones.CategoriaEmpresaEnum;
/*   18:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   19:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   20:     */ import com.asinfo.as2.enumeraciones.OrdenamientoEnum;
/*   21:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   22:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   23:     */ import com.asinfo.as2.util.AppUtil;
/*   24:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   25:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   26:     */ import java.math.BigDecimal;
/*   27:     */ import java.util.ArrayList;
/*   28:     */ import java.util.Date;
/*   29:     */ import java.util.List;
/*   30:     */ import javax.ejb.Stateless;
/*   31:     */ import javax.persistence.EntityManager;
/*   32:     */ import javax.persistence.Query;
/*   33:     */ import javax.persistence.TemporalType;
/*   34:     */ 
/*   35:     */ @Stateless
/*   36:     */ public class ReporteCompraDao
/*   37:     */   extends AbstractDaoAS2<FacturaProveedor>
/*   38:     */ {
/*   39:     */   public ReporteCompraDao()
/*   40:     */   {
/*   41:  55 */     super(FacturaProveedor.class);
/*   42:     */   }
/*   43:     */   
/*   44:     */   public List getListaReporteFacturacionResumidoCompra(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idDocumento, boolean saldoInicial, int idOrganizacion, int tipoCreditoTributario, boolean indicadorResumido, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, List<Producto> listaProducto, Sucursal sucursal, DocumentoBase documentoBase)
/*   45:     */     throws ExcepcionAS2Ventas
/*   46:     */   {
/*   47:  79 */     boolean creditoTributario = tipoCreditoTributario == 1;
/*   48:     */     StringBuilder sql;
/*   49:  82 */     if (indicadorResumido)
/*   50:     */     {
/*   51:  83 */       StringBuilder sql = new StringBuilder();
/*   52:  84 */       sql.append(" SELECT CASE WHEN fSRI IS NULL THEN f.numero ELSE CONCAT(fSRI.establecimiento, '-', fSRI.puntoEmision, '-', fSRI.numero) END, ");
/*   53:  85 */       sql.append(" f.fecha, e.identificacion, e.nombreFiscal, f.total, f.descuento,f.impuesto,f.descripcion,f.retencionComercializadora,f.numero,e.codigo,ce.nombre,d.operacion, ");
/*   54:  86 */       sql.append(" fSRI.fechaEmision, fSRI.autorizacion , '', fSRI.baseImponibleTarifaCero, fSRI.baseImponibleDiferenteCero,fSRI.baseImponibleNoObjetoIva,");
/*   55:  87 */       sql.append(" fSRI.autorizacionRetencion, fSRI.claveAcceso, f.descuentoImpuesto, f.descripcion ");
/*   56:  88 */       sql.append(" FROM FacturaProveedor f ");
/*   57:  89 */       sql.append(" LEFT JOIN f.facturaProveedorSRI fSRI ");
/*   58:  90 */       sql.append(" INNER JOIN f.empresa e ");
/*   59:  91 */       sql.append(" INNER JOIN f.documento d ");
/*   60:  92 */       sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/*   61:  93 */       sql.append(" WHERE f.fecha >= :fechaDesde ");
/*   62:  94 */       sql.append(" AND f.fecha <= :fechaHasta ");
/*   63:  95 */       sql.append(" AND f.estado != :estadoAnulado AND d.indicadorDocumentoExterior=false ");
/*   64:  96 */       sql.append(" AND (e.idEmpresa = :idCliente OR :idCliente=0) ");
/*   65:  97 */       sql.append(" AND (d.idDocumento = :idDocumento OR :idDocumento=0) ");
/*   66:  98 */       sql.append(" AND f.indicadorSaldoInicial=:saldoInicial ");
/*   67:  99 */       sql.append(" AND f.idOrganizacion=:idOrganizacion ");
/*   68:     */     }
/*   69:     */     else
/*   70:     */     {
/*   71: 101 */       sql = new StringBuilder();
/*   72: 102 */       sql.append(" SELECT f.numero, CASE WHEN fSRI IS NULL THEN f.numero ELSE CONCAT(fSRI.establecimiento, '-', fSRI.puntoEmision, '-', fSRI.numero) END,");
/*   73: 103 */       sql.append(" f.fecha, e.codigo, e.identificacion, e.nombreFiscal, e.nombreComercial, ");
/*   74: 104 */       sql.append(" p.codigo, p.codigoBarras, p.nombre, p.nombreComercial, df.cantidad, df.precio, ");
/*   75: 105 */       sql.append(" df.descuento, df.descripcion, d.operacion, f.descripcion, p.precioReferencialCompra ");
/*   76: 106 */       sql.append(" FROM  DetalleFacturaProveedor df ");
/*   77: 107 */       sql.append(" INNER JOIN df.producto p ");
/*   78: 108 */       sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/*   79: 109 */       sql.append(" INNER JOIN sp.categoriaProducto cp ");
/*   80: 110 */       sql.append(" INNER JOIN df.facturaProveedor f ");
/*   81: 111 */       sql.append(" LEFT JOIN f.facturaProveedorSRI fSRI ");
/*   82: 112 */       sql.append(" INNER JOIN f.empresa e ");
/*   83: 113 */       sql.append(" INNER JOIN f.documento d ");
/*   84: 114 */       sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/*   85: 115 */       sql.append(" WHERE f.fecha >= :fechaDesde ");
/*   86: 116 */       sql.append(" AND f.fecha <= :fechaHasta ");
/*   87: 117 */       sql.append(" AND f.estado != :estadoAnulado AND d.indicadorDocumentoExterior=false ");
/*   88: 118 */       sql.append(" AND (e.idEmpresa = :idCliente OR :idCliente=0) ");
/*   89: 119 */       sql.append(" AND (d.idDocumento = :idDocumento OR :idDocumento=0) ");
/*   90: 120 */       sql.append(" AND f.indicadorSaldoInicial=:saldoInicial ");
/*   91: 121 */       sql.append(" AND f.idOrganizacion=:idOrganizacion ");
/*   92: 122 */       if (categoriaProducto != null) {
/*   93: 123 */         sql.append(" AND cp.idCategoriaProducto = :idCategoriaProducto ");
/*   94:     */       }
/*   95: 125 */       if (subcategoriaProducto != null) {
/*   96: 126 */         sql.append(" AND sp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/*   97:     */       }
/*   98: 128 */       if ((listaProducto != null) && (!listaProducto.isEmpty())) {
/*   99: 129 */         sql.append(" AND p IN (:listaProductos) ");
/*  100:     */       }
/*  101:     */     }
/*  102: 132 */     if (tipoCreditoTributario > 0) {
/*  103: 133 */       sql.append(" AND f.indicadorCreditoTributario = :creditoTributario ");
/*  104:     */     }
/*  105: 135 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  106: 136 */       sql.append(" AND f.sucursal.idSucursal = :idSucursal");
/*  107:     */     }
/*  108: 139 */     if (documentoBase != null) {
/*  109: 140 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_PROVEEDOR) {
/*  110: 141 */         sql.append(" AND d.documentoBase IN (:documentoBase, :documentoDevolucion) ");
/*  111:     */       } else {
/*  112: 143 */         sql.append(" AND d.documentoBase = :documentoBase ");
/*  113:     */       }
/*  114:     */     }
/*  115: 146 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0)) {
/*  116: 147 */       sql.append(" AND f.numero >= :numeroDesde AND f.numero <= :numeroHasta ");
/*  117:     */     }
/*  118: 149 */     if (indicadorResumido) {
/*  119: 150 */       sql.append(" ORDER BY e.nombreFiscal, f.fecha ");
/*  120:     */     } else {
/*  121: 152 */       sql.append(" ORDER BY p.nombre, f.fecha ");
/*  122:     */     }
/*  123: 154 */     Query query = this.em.createQuery(sql.toString());
/*  124: 155 */     query.setParameter("fechaDesde", fechaDesde);
/*  125: 156 */     query.setParameter("fechaHasta", fechaHasta);
/*  126: 157 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  127: 158 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  128: 159 */     query.setParameter("idDocumento", Integer.valueOf(idDocumento));
/*  129: 160 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/*  130: 161 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  131: 162 */     if (!indicadorResumido)
/*  132:     */     {
/*  133: 163 */       if (categoriaProducto != null) {
/*  134: 164 */         query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/*  135:     */       }
/*  136: 166 */       if (subcategoriaProducto != null) {
/*  137: 167 */         query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/*  138:     */       }
/*  139: 169 */       if ((listaProducto != null) && (!listaProducto.isEmpty())) {
/*  140: 170 */         query.setParameter("listaProductos", listaProducto);
/*  141:     */       }
/*  142:     */     }
/*  143: 173 */     if (tipoCreditoTributario > 0) {
/*  144: 174 */       query.setParameter("creditoTributario", Boolean.valueOf(creditoTributario));
/*  145:     */     }
/*  146: 176 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  147: 177 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/*  148:     */     }
/*  149: 179 */     if (documentoBase != null)
/*  150:     */     {
/*  151: 180 */       query.setParameter("documentoBase", documentoBase);
/*  152: 181 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_PROVEEDOR) {
/*  153: 182 */         query.setParameter("documentoDevolucion", DocumentoBase.DEVOLUCION_PROVEEDOR);
/*  154:     */       }
/*  155:     */     }
/*  156: 185 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0))
/*  157:     */     {
/*  158: 186 */       query.setParameter("numeroDesde", numeroDesde);
/*  159: 187 */       query.setParameter("numeroHasta", numeroHasta);
/*  160:     */     }
/*  161: 190 */     return query.getResultList();
/*  162:     */   }
/*  163:     */   
/*  164:     */   public List getListaReporteCorteFechaResumido(Date fechaHasta, int idProveedor, int idOrganizacion)
/*  165:     */     throws ExcepcionAS2Ventas
/*  166:     */   {
/*  167: 201 */     String sql = "SELECT v.nombreFiscal, v.codigo, v.identificacion, SUM(v.debito - v.credito)  FROM VEstadoCuentaProveedor v WHERE 1=1 AND v.fechaDocumento <= :fechaHasta  AND v.idOrganizacion = :idOrganizacion ";
/*  168: 204 */     if (idProveedor != 0) {
/*  169: 205 */       sql = sql + " AND v.idEmpresa <= :idProveedor ";
/*  170:     */     }
/*  171: 208 */     sql = sql + " GROUP BY v.codigo, v.identificacion, v.nombreFiscal";
/*  172:     */     
/*  173: 210 */     Query query = this.em.createQuery(sql);
/*  174: 211 */     query.setParameter("fechaHasta", fechaHasta);
/*  175: 212 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  176: 214 */     if (idProveedor != 0) {
/*  177: 215 */       query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/*  178:     */     }
/*  179: 217 */     return query.getResultList();
/*  180:     */   }
/*  181:     */   
/*  182:     */   public List getListaReporteCorteFecha(Date fechaHasta, int idProveedor, int idOrganizacion, int idTipoOperacion, Sucursal sucursal, CategoriaEmpresa categoriaEmpresa)
/*  183:     */     throws ExcepcionAS2Ventas
/*  184:     */   {
/*  185: 232 */     boolean agrupadoCategoriaEmpresa = (categoriaEmpresa != null) && (categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()));
/*  186:     */     
/*  187: 234 */     StringBuilder sql = new StringBuilder();
/*  188: 235 */     sql.append(" SELECT v.codigo, v.identificacion, v.nombreFiscal, v.fechaFactura, v.numeroFactura,sum(v.debito) ,sum(v.credito), sum(v.debito - v.credito), v.idSucursal, v.nombreSucursal, ");
/*  189: 236 */     sql.append(" v.idCategoriaEmpresa, v.nombreCategoriaEmpresa ");
/*  190: 237 */     sql.append(" FROM VEstadoCuentaProveedor v ");
/*  191: 238 */     sql.append(" WHERE v.fechaDocumento <= :fechaHasta ");
/*  192: 239 */     sql.append(" AND v.idOrganizacion = :idOrganizacion ");
/*  193: 240 */     sql.append(" AND (v.idTipoOperacion = :idTipoOperacion OR 0 = :idTipoOperacion) ");
/*  194: 241 */     if (idProveedor != 0) {
/*  195: 242 */       sql.append(" AND v.idEmpresa = :idProveedor ");
/*  196:     */     }
/*  197: 244 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  198: 245 */       sql.append(" AND v.idSucursal = :idSucursal");
/*  199:     */     }
/*  200: 247 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/*  201: 248 */       sql.append(" AND v.idCategoriaEmpresa = :idCategoriaEmpresa");
/*  202:     */     }
/*  203: 250 */     sql.append(" GROUP BY v.codigo, v.identificacion, v.nombreFiscal, v.fechaFactura, v.numeroFactura, idSucursal, v.nombreSucursal, v.idCategoriaEmpresa, v.nombreCategoriaEmpresa, v.fechaVencimiento ");
/*  204: 251 */     sql.append(" HAVING sum(v.debito - v.credito)<>0 ");
/*  205: 252 */     if ((categoriaEmpresa != null) && (agrupadoCategoriaEmpresa)) {
/*  206: 253 */       sql.append(" ORDER BY v.nombreCategoriaEmpresa, v.nombreSucursal, v.nombreFiscal, v.fechaVencimiento, v.numeroFactura ");
/*  207:     */     } else {
/*  208: 255 */       sql.append(" ORDER BY v.nombreSucursal, v.nombreFiscal, v.fechaVencimiento, v.numeroFactura ");
/*  209:     */     }
/*  210: 258 */     Query query = this.em.createQuery(sql.toString());
/*  211: 259 */     query.setParameter("fechaHasta", fechaHasta);
/*  212: 260 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  213: 261 */     query.setParameter("idTipoOperacion", Integer.valueOf(idTipoOperacion));
/*  214: 262 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/*  215: 263 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getIdCategoriaEmpresa()));
/*  216:     */     }
/*  217: 265 */     if (idProveedor != 0) {
/*  218: 266 */       query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/*  219:     */     }
/*  220: 268 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  221: 269 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/*  222:     */     }
/*  223: 272 */     return query.getResultList();
/*  224:     */   }
/*  225:     */   
/*  226:     */   public BigDecimal obtenerSaldoEstadoCuenta(int idProveedor, Date fechaHasta, int idTipoOperacion)
/*  227:     */   {
/*  228: 277 */     Date fechaHastaReporte = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -1);
/*  229:     */     
/*  230: 279 */     StringBuilder sql = new StringBuilder();
/*  231: 280 */     sql.append(" SELECT SUM(debito-credito) FROM VEstadoCuentaProveedor e ");
/*  232: 281 */     sql.append(" WHERE e.fechaDocumento <= :fechaHasta ");
/*  233: 282 */     sql.append(" AND e.idEmpresa= :idProveedor ");
/*  234: 283 */     sql.append(" AND (e.idTipoOperacion = :idTipoOperacion OR 0=:idTipoOperacion) ");
/*  235:     */     
/*  236: 285 */     Query query = this.em.createQuery(sql.toString());
/*  237: 286 */     query.setParameter("fechaHasta", fechaHastaReporte);
/*  238: 287 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/*  239: 288 */     query.setParameter("idTipoOperacion", Integer.valueOf(idTipoOperacion));
/*  240:     */     
/*  241: 290 */     BigDecimal resultado = (BigDecimal)query.getSingleResult();
/*  242: 292 */     if (resultado == null) {
/*  243: 293 */       resultado = BigDecimal.ZERO;
/*  244:     */     }
/*  245: 295 */     return resultado;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public List getListaReporteEstadoCuenta(Date fechaDesde, Date fechaHasta, int idProveedor, int idTipoOperacion, OrdenamientoEnum orden, boolean saldoDiferenteDeCero, int idOrganizacion, Sucursal sucursal, CategoriaEmpresa categoriaEmpresa)
/*  249:     */     throws ExcepcionAS2Ventas
/*  250:     */   {
/*  251: 311 */     String ordenString = ",v.fechaDocumento, v.credito";
/*  252:     */     
/*  253: 313 */     boolean agrupadoCategoriaEmpresa = (categoriaEmpresa != null) && (categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()));
/*  254: 314 */     if (orden == OrdenamientoEnum.FACTURA) {
/*  255: 315 */       ordenString = ",v.fechaFactura, v.numeroFactura, v.fechaDocumento, v.credito";
/*  256:     */     }
/*  257: 317 */     if (orden == OrdenamientoEnum.DOCUMENTO) {
/*  258: 318 */       ordenString = ",v.fechaDocumento, v.numeroDocumento, v.fechaFactura";
/*  259:     */     }
/*  260: 320 */     if (orden == OrdenamientoEnum.SALDO_FACTURA) {
/*  261: 321 */       ordenString = ",v.fechaFactura, v.fechaDocumento, v.credito";
/*  262:     */     }
/*  263: 324 */     StringBuilder sql = new StringBuilder();
/*  264: 325 */     sql.append(" SELECT v.nombreFiscal, v.codigo, v.fechaDocumento, v.numeroFactura, ");
/*  265: 326 */     sql.append(" v.fechaVencimiento, v.descripcionDocumento, v.debito, v.credito, v.nombreDocumento, ");
/*  266: 327 */     sql.append(" v.numeroDocumento, v.tipoOperacion, v.codigoDocumento, v.codigoDocumentoProceso,  ");
/*  267: 328 */     sql.append(" v.asientoCompra, v.asientoDocumento, v.referencia1, v.referencia2, v.valorReferencia1, v.valorReferencia2, v.valorReferencia3, v.identificacion, ");
/*  268: 329 */     sql.append(" v.idCategoriaEmpresa, v.nombreCategoriaEmpresa, v.descripcionFactura, v.idPago ");
/*  269: 330 */     sql.append(" FROM VEstadoCuentaProveedor v ");
/*  270: 331 */     if (orden == OrdenamientoEnum.SALDO_FACTURA) {
/*  271: 332 */       sql.append(" WHERE v.fechaFactura BETWEEN :fechaDesde AND :fechaHasta ");
/*  272:     */     } else {
/*  273: 334 */       sql.append(" WHERE v.fechaDocumento BETWEEN :fechaDesde AND :fechaHasta ");
/*  274:     */     }
/*  275: 336 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/*  276: 337 */       sql.append(" AND v.idCategoriaEmpresa =:idCategoriaEmpresa ");
/*  277:     */     }
/*  278: 339 */     sql.append(" AND (v.idTipoOperacion = :idTipoOperacion OR 0 = :idTipoOperacion) ");
/*  279: 340 */     sql.append(" AND (v.idOrganizacion = :idOrganizacion)");
/*  280: 341 */     sql.append(" AND (v.idEmpresa = :idProveedor OR 0 = :idProveedor)");
/*  281: 342 */     if (saldoDiferenteDeCero) {
/*  282: 343 */       sql.append(" AND EXISTS (SELECT ec2.idFacturaProveedor FROM VEstadoCuentaProveedor ec2 WHERE ec2.idFacturaProveedor=v.idFacturaProveedor AND ec2.fechaDocumento<=:fechaHasta GROUP BY ec2.idFacturaProveedor HAVING SUM(ec2.debito-ec2.credito) <> 0 ) ");
/*  283:     */     }
/*  284: 345 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  285: 346 */       sql.append(" AND v.idSucursal = :idSucursal");
/*  286:     */     }
/*  287: 348 */     if ((categoriaEmpresa != null) && (agrupadoCategoriaEmpresa)) {
/*  288: 349 */       sql.append(" ORDER BY v.nombreCategoriaEmpresa, v.nombreFiscal " + ordenString);
/*  289:     */     } else {
/*  290: 351 */       sql.append(" ORDER BY v.nombreFiscal " + ordenString);
/*  291:     */     }
/*  292: 354 */     Query query = this.em.createQuery(sql.toString());
/*  293: 355 */     query.setParameter("fechaDesde", fechaDesde);
/*  294: 356 */     query.setParameter("fechaHasta", fechaHasta);
/*  295: 357 */     query.setParameter("idTipoOperacion", Integer.valueOf(idTipoOperacion));
/*  296: 358 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/*  297: 359 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  298: 360 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  299: 361 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/*  300:     */     }
/*  301: 363 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/*  302: 364 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getIdCategoriaEmpresa()));
/*  303:     */     }
/*  304: 367 */     return query.getResultList();
/*  305:     */   }
/*  306:     */   
/*  307:     */   public List<Object[]> getListaReporteEstadoCuenta(int idProveedor, FacturaProveedor facturaProveedor)
/*  308:     */   {
/*  309: 379 */     StringBuilder sql = new StringBuilder();
/*  310: 380 */     sql.append(" SELECT v.fechaDocumento, v.numeroDocumento, v.fechaVencimiento, v.numeroFactura, v.debito, v.credito, v.descripcionDocumento, v.codigoDocumentoProceso, ");
/*  311: 381 */     sql.append(" v.nombreFiscal, v.nombreDocumento, v.idFacturaProveedor, v.documentoBase, v.idPago ");
/*  312: 382 */     sql.append(" FROM FacturaProveedor fp, VEstadoCuentaProveedor v ");
/*  313: 383 */     sql.append(" INNER JOIN fp.documento d ");
/*  314: 384 */     sql.append(" WHERE fp.idFacturaProveedor = v.idFacturaProveedor and v.idEmpresa = :idProveedor  ");
/*  315: 385 */     sql.append(" AND v.idFacturaProveedor = :idFactura ");
/*  316: 386 */     sql.append(" ORDER BY v.fechaDocumento ");
/*  317:     */     
/*  318: 388 */     Query query = this.em.createQuery(sql.toString()).setParameter("idProveedor", Integer.valueOf(idProveedor)).setParameter("idFactura", Integer.valueOf(facturaProveedor.getId()));
/*  319:     */     
/*  320: 390 */     return query.getResultList();
/*  321:     */   }
/*  322:     */   
/*  323:     */   public List getReporteFacturaProveedor(int idFacturaProveedor)
/*  324:     */   {
/*  325: 395 */     StringBuilder sql = new StringBuilder();
/*  326: 396 */     sql.append(" SELECT e.nombreFiscal, CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4), ");
/*  327: 397 */     sql.append(" e.identificacion, f.fecha, d.cantidad, pr.codigo, pr.nombreComercial, d.precio, f.total, f.descuento, f.impuesto, ");
/*  328: 398 */     sql.append(" de.telefono1, f.descripcion, YEAR(f.fecha), MONTH(f.fecha), DAY(f.fecha),f.numero , fps.establecimiento, fps.puntoEmision, ");
/*  329: 399 */     sql.append(" fps.numero, re.numero, a.numero, fps.establecimientoRetencion, fps.puntoEmisionRetencion, fps.numeroRetencion, p.nombre, ");
/*  330: 400 */     sql.append(" COALESCE(fps.baseImponibleTarifaCero,0), COALESCE(fps.baseImponibleDiferenteCero,0), ciu.nombre, fps.autorizacionRetencion, ");
/*  331: 401 */     sql.append(" paa.numero, f.usuarioCreacion, fpi.puertoEmbarque, fpi.puertoLlegada, fpi.informacionTransporte, fpi.fechaEmbarque, ");
/*  332: 402 */     sql.append(" fpi.fechaCierre, fpi.fechaLlegada, fpi.numeroDUI, fpi.medioTransporteEnum, CONCAT(prs.nombres,' ',prs.apellidos) ");
/*  333: 403 */     sql.append(" FROM DetalleFacturaProveedor d ");
/*  334: 404 */     sql.append(" INNER JOIN d.producto pr ");
/*  335: 405 */     sql.append(" INNER JOIN d.facturaProveedor f ");
/*  336: 406 */     sql.append(" INNER JOIN f.empresa e ");
/*  337: 407 */     sql.append(" LEFT JOIN f.condicionPago c ");
/*  338: 408 */     sql.append(" INNER JOIN f.direccionEmpresa de ");
/*  339: 409 */     sql.append(" LEFT OUTER JOIN de.ciudad ciu ");
/*  340: 410 */     sql.append(" LEFT OUTER JOIN f.facturaProveedorSRI fps ");
/*  341: 411 */     sql.append(" LEFT JOIN fps.pago pa ");
/*  342: 412 */     sql.append(" LEFT JOIN pa.asiento paa ");
/*  343: 413 */     sql.append(" INNER JOIN de.ubicacion u ");
/*  344: 414 */     sql.append(" LEFT JOIN f.recepcionProveedor re ");
/*  345: 415 */     sql.append(" LEFT JOIN f.asiento a ");
/*  346: 416 */     sql.append(" LEFT JOIN f.proyecto p ");
/*  347: 417 */     sql.append(" LEFT JOIN f.personaResponsable prs ");
/*  348: 418 */     sql.append(" LEFT JOIN f.facturaProveedorImportacion fpi ");
/*  349: 419 */     sql.append(" WHERE f.idFacturaProveedor = :idFacturaProveedor ");
/*  350:     */     
/*  351: 421 */     Query query = this.em.createQuery(sql.toString()).setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  352: 422 */     return query.getResultList();
/*  353:     */   }
/*  354:     */   
/*  355:     */   public List<ReporteAnticipoProveedor> getReporteSaldoAnticipoProveedor(Date fechaDesde, Date fechaHasta, int idProveedor, int idOrganizacion, boolean indicadorResumen)
/*  356:     */     throws ExcepcionAS2
/*  357:     */   {
/*  358: 429 */     StringBuilder sql = new StringBuilder();
/*  359:     */     
/*  360: 431 */     sql.append(" SELECT new ReporteAnticipoProveedor(e.idEmpresa,e.nombreComercial, e.nombreFiscal, e.identificacion,  ");
/*  361: 432 */     if (!indicadorResumen) {
/*  362: 433 */       sql.append(" ap.numero,");
/*  363:     */     }
/*  364: 435 */     sql.append(" COALESCE(SUM(ap.valor), 0)-");
/*  365: 436 */     sql.append(" \tCOALESCE(");
/*  366: 437 */     sql.append(" \t\t(SELECT SUM(dlap.valor)");
/*  367: 438 */     sql.append(" \t\tFROM DetalleLiquidacionAnticipoProveedor dlap ");
/*  368: 439 */     sql.append(" \t\tJOIN dlap.liquidacionAnticipoProveedor lap ");
/*  369: 440 */     sql.append(" \t\tJOIN lap.anticipoProveedor apv ");
/*  370: 441 */     sql.append(" \t\tJOIN apv.empresa ev ");
/*  371: 442 */     sql.append(" \t\tJOIN dlap.cuentaPorPagar cxp ");
/*  372: 443 */     sql.append(" \t\tJOIN cxp.facturaProveedor fp ");
/*  373: 444 */     sql.append(" \t\tWHERE lap.estado = :estado ");
/*  374: 445 */     sql.append(" \t\tAND apv.estado = :estado ");
/*  375: 446 */     sql.append(" \t\tAND apv.idOrganizacion = :idOrganizacion ");
/*  376: 447 */     sql.append(" \t\tAND ( :idProveedor=0 OR ev.idEmpresa = :idProveedor) ");
/*  377: 448 */     sql.append(" \t\tAND lap.fecha < :fechaDesde ");
/*  378: 449 */     if (indicadorResumen)
/*  379:     */     {
/*  380: 450 */       sql.append(" \t\tAND e.idEmpresa=ev.idEmpresa");
/*  381: 451 */       sql.append(" \t\tGROUP BY ev.idEmpresa)");
/*  382:     */     }
/*  383:     */     else
/*  384:     */     {
/*  385: 453 */       sql.append(" \t\tAND ap.idAnticipoProveedor=apv.idAnticipoProveedor");
/*  386: 454 */       sql.append(" \t\tGROUP BY apv.idAnticipoProveedor )");
/*  387:     */     }
/*  388: 456 */     sql.append(" \t, 0)-");
/*  389: 457 */     sql.append(" \tCOALESCE(");
/*  390: 458 */     sql.append(" \t\t(SELECT SUM(apr.valorDevolucion)");
/*  391: 459 */     sql.append(" \t\tFROM AnticipoProveedor apr ");
/*  392: 460 */     sql.append(" \t\tJOIN apr.empresa er ");
/*  393: 461 */     sql.append(" \t\tWHERE apr.estado = :estado ");
/*  394: 462 */     sql.append(" \t\tAND apr.idOrganizacion = :idOrganizacion ");
/*  395: 463 */     sql.append(" \t\tAND ( :idProveedor=0 OR er.idEmpresa = :idProveedor) ");
/*  396: 464 */     sql.append(" \t\tAND apr.fechaDevolucion < :fechaDesde ");
/*  397: 465 */     if (indicadorResumen)
/*  398:     */     {
/*  399: 466 */       sql.append(" \t\tAND e.idEmpresa=er.idEmpresa");
/*  400: 467 */       sql.append(" \t\tGROUP BY er.idEmpresa)");
/*  401:     */     }
/*  402:     */     else
/*  403:     */     {
/*  404: 469 */       sql.append(" \t\tAND ap.idAnticipoProveedor=apr.idAnticipoProveedor");
/*  405: 470 */       sql.append(" \t\tGROUP BY apr.idAnticipoProveedor )");
/*  406:     */     }
/*  407: 472 */     sql.append(" \t, 0),");
/*  408: 473 */     sql.append(" 'SALDO',ap.estado )");
/*  409: 474 */     sql.append(" FROM AnticipoProveedor ap ");
/*  410: 475 */     sql.append(" JOIN ap.empresa e ");
/*  411: 476 */     sql.append(" WHERE ap.fecha <:fechaDesde ");
/*  412: 477 */     sql.append(" AND ap.estado = :estado ");
/*  413: 478 */     sql.append(" AND ap.idOrganizacion = :idOrganizacion ");
/*  414: 479 */     sql.append(" AND ( :idProveedor=0 OR e.idEmpresa = :idProveedor) ");
/*  415: 480 */     if (indicadorResumen) {
/*  416: 481 */       sql.append(" GROUP BY e.idEmpresa,e.nombreComercial, e.nombreFiscal, e.identificacion,ap.estado");
/*  417:     */     } else {
/*  418: 483 */       sql.append(" GROUP BY ap.idAnticipoProveedor,ap.numero,e.idEmpresa,e.nombreComercial, e.nombreFiscal, e.identificacion,ap.estado");
/*  419:     */     }
/*  420: 486 */     Query query = this.em.createQuery(sql.toString());
/*  421: 487 */     query.setParameter("fechaDesde", fechaDesde);
/*  422: 488 */     query.setParameter("estado", Estado.CONTABILIZADO);
/*  423: 489 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  424: 490 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/*  425:     */     
/*  426: 492 */     return query.getResultList();
/*  427:     */   }
/*  428:     */   
/*  429:     */   public List<ReporteAnticipoProveedor> getReporteAnticipoProveedor(Date fechaDesde, Date fechaHasta, int idProveedor, int idOrganizacion, boolean indicadorResumido)
/*  430:     */     throws ExcepcionAS2
/*  431:     */   {
/*  432: 507 */     StringBuilder sql = new StringBuilder();
/*  433:     */     
/*  434: 509 */     sql.append(" SELECT new ReporteAnticipoProveedor(e.idEmpresa,e.nombreComercial, e.nombreFiscal, e.identificacion, ");
/*  435: 510 */     sql.append("  ap.fecha,  ap.valor*0, ap.numero, ap.valor, '',0*ap.valor ,'ANTICIPO',ap.estado)");
/*  436: 511 */     sql.append(" FROM AnticipoProveedor ap ");
/*  437: 512 */     sql.append(" JOIN ap.empresa e ");
/*  438: 513 */     sql.append(" WHERE ap.estado = :estado ");
/*  439: 514 */     sql.append(" AND ap.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  440:     */     
/*  441: 516 */     sql.append(" AND ap.idOrganizacion = :idOrganizacion ");
/*  442: 517 */     sql.append(" AND ( :idProveedor=0 OR e.idEmpresa = :idProveedor) ");
/*  443: 518 */     sql.append(" ORDER BY e.nombreComercial,ap.fecha");
/*  444:     */     
/*  445: 520 */     Query query = this.em.createQuery(sql.toString());
/*  446: 521 */     query.setParameter("fechaDesde", fechaDesde);
/*  447: 522 */     query.setParameter("fechaHasta", fechaHasta);
/*  448: 523 */     query.setParameter("estado", Estado.CONTABILIZADO);
/*  449: 524 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  450: 525 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/*  451:     */     
/*  452: 527 */     return query.getResultList();
/*  453:     */   }
/*  454:     */   
/*  455:     */   public List<ReporteAnticipoProveedor> getReporteLiquidacionAnticipoProveedor(Date fechaDesde, Date fechaHasta, List<String> listaNumeroAnticipo, int idProveedor, int idOrganizacion, boolean indicadorResumido)
/*  456:     */     throws ExcepcionAS2
/*  457:     */   {
/*  458: 534 */     List<ReporteAnticipoProveedor> lista = new ArrayList();
/*  459:     */     
/*  460: 536 */     StringBuilder sql = new StringBuilder();
/*  461:     */     
/*  462: 538 */     sql.append(" SELECT new ReporteAnticipoProveedor(e.idEmpresa,e.nombreComercial, e.nombreFiscal, e.identificacion, ");
/*  463: 539 */     sql.append(" lap.fecha, lap.valor*0, ap.numero,0*dlap.valor ,lap.numero, dlap.valor  ,'LIQUIDACION', COALESCE(CONCAT(fpsri.establecimiento,'-',fpsri.puntoEmision,'-',fpsri.numero),fp.numero))");
/*  464: 540 */     sql.append(" FROM DetalleLiquidacionAnticipoProveedor dlap ");
/*  465: 541 */     sql.append(" JOIN dlap.liquidacionAnticipoProveedor lap ");
/*  466: 542 */     sql.append(" JOIN lap.anticipoProveedor ap ");
/*  467: 543 */     sql.append(" JOIN ap.empresa e ");
/*  468: 544 */     sql.append(" JOIN dlap.cuentaPorPagar cxp ");
/*  469: 545 */     sql.append(" JOIN cxp.facturaProveedor fp ");
/*  470: 546 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fpsri ");
/*  471: 547 */     sql.append(" WHERE lap.estado = :estado ");
/*  472: 548 */     sql.append(" AND ap.estado = :estado ");
/*  473: 549 */     sql.append(" AND ap.idOrganizacion = :idOrganizacion ");
/*  474: 550 */     sql.append(" AND ( :idProveedor=0 OR e.idEmpresa = :idProveedor) ");
/*  475: 551 */     sql.append(" AND lap.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  476:     */     
/*  477: 553 */     Query query = this.em.createQuery(sql.toString());
/*  478: 554 */     query.setParameter("estado", Estado.CONTABILIZADO);
/*  479: 555 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  480: 556 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/*  481: 557 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  482: 558 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  483: 559 */     lista.addAll(query.getResultList());
/*  484:     */     
/*  485:     */ 
/*  486:     */ 
/*  487:     */ 
/*  488: 564 */     sql = new StringBuilder();
/*  489:     */     
/*  490: 566 */     sql.append(" SELECT new ReporteAnticipoProveedor(e.idEmpresa,e.nombreComercial, e.nombreFiscal, e.identificacion, ");
/*  491: 567 */     sql.append("  ap.fecha,  ap.valor*0, ap.numero, 0*ap.valor, ap.numero, ap.valorDevolucion ,'DEVOLUCION','')");
/*  492: 568 */     sql.append(" FROM AnticipoProveedor ap ");
/*  493: 569 */     sql.append(" JOIN ap.empresa e ");
/*  494: 570 */     sql.append(" WHERE ap.estado = :estado ");
/*  495: 571 */     sql.append(" AND ap.fechaDevolucion BETWEEN :fechaDesde AND :fechaHasta ");
/*  496: 572 */     sql.append(" AND ap.idOrganizacion = :idOrganizacion ");
/*  497: 573 */     sql.append(" AND ( :idProveedor=0 OR e.idEmpresa = :idProveedor) ");
/*  498:     */     
/*  499: 575 */     query = this.em.createQuery(sql.toString());
/*  500: 576 */     query.setParameter("fechaDesde", fechaDesde);
/*  501: 577 */     query.setParameter("fechaHasta", fechaHasta);
/*  502: 578 */     query.setParameter("estado", Estado.CONTABILIZADO);
/*  503: 579 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  504: 580 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/*  505: 581 */     lista.addAll(query.getResultList());
/*  506:     */     
/*  507: 583 */     return lista;
/*  508:     */   }
/*  509:     */   
/*  510:     */   public List getReporteComparativoPrecioCompra(int idSubcategoriaProducto, TipoProducto tipoProducto)
/*  511:     */   {
/*  512: 597 */     StringBuilder sql = new StringBuilder();
/*  513:     */     
/*  514: 599 */     sql.append(" SELECT e.codigo, e.identificacion, e.nombreComercial, e.nombreFiscal, cp.codigo, cp.nombre, sp.codigo, sp.nombre, p.codigo, p.nombre, puc.precio, puc.fecha ");
/*  515: 600 */     sql.append(" FROM ProductoUltimaCompra puc ");
/*  516: 601 */     sql.append(" LEFT OUTER JOIN puc.empresa e ");
/*  517: 602 */     sql.append(" LEFT OUTER JOIN puc.producto p ");
/*  518: 603 */     sql.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/*  519: 604 */     sql.append(" LEFT OUTER JOIN sp.categoriaProducto cp ");
/*  520: 605 */     sql.append(" WHERE (sp.idSubcategoriaProducto = :idSubcategoriaProducto OR :idSubcategoriaProducto=0) ");
/*  521: 606 */     sql.append(" AND puc.idOrganizacion = :idOrganizacion ");
/*  522: 607 */     if (tipoProducto != null) {
/*  523: 608 */       sql.append(" AND p.tipoProducto = :tipoProducto ");
/*  524:     */     }
/*  525: 610 */     sql.append(" ORDER BY e.nombreComercial,p.nombre ");
/*  526:     */     
/*  527: 612 */     Query query = this.em.createQuery(sql.toString());
/*  528: 613 */     query.setParameter("idSubcategoriaProducto", Integer.valueOf(idSubcategoriaProducto));
/*  529: 614 */     query.setParameter("idOrganizacion", Integer.valueOf(AppUtil.getOrganizacion().getId()));
/*  530: 615 */     if (tipoProducto != null) {
/*  531: 616 */       query.setParameter("tipoProducto", tipoProducto);
/*  532:     */     }
/*  533: 619 */     return query.getResultList();
/*  534:     */   }
/*  535:     */   
/*  536:     */   public List getReporteAnalisisComprasProveedor(Date fechaDesde, Date fechaHasta, boolean saldoInicial, int idOrganizacion, int idSubcategoriaProducto, boolean indicadorCantidad)
/*  537:     */   {
/*  538:     */     String sumatoria;
/*  539:     */     String sumatoria;
/*  540: 627 */     if (indicadorCantidad) {
/*  541: 628 */       sumatoria = "SUM(df.cantidad)";
/*  542:     */     } else {
/*  543: 630 */       sumatoria = "ROUND(SUM(df.cantidad*(df.precio-df.descuento)),2)";
/*  544:     */     }
/*  545: 633 */     StringBuilder sql = new StringBuilder();
/*  546: 634 */     sql.append(" SELECT e.nombreComercial, e.identificacion, p.codigo, p.codigoComercial, p.nombre, u.nombre," + sumatoria);
/*  547: 635 */     sql.append(" FROM DetalleFacturaProveedor df ");
/*  548: 636 */     sql.append(" LEFT OUTER JOIN df.facturaProveedor f ");
/*  549: 637 */     sql.append(" LEFT OUTER JOIN f.empresa e ");
/*  550: 638 */     sql.append(" LEFT OUTER JOIN f.documento d ");
/*  551: 639 */     sql.append(" LEFT OUTER JOIN df.producto p ");
/*  552: 640 */     sql.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/*  553: 641 */     sql.append(" LEFT OUTER JOIN p.unidad u ");
/*  554: 642 */     sql.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  555: 643 */     sql.append(" AND f.indicadorSaldoInicial = :saldoInicial ");
/*  556: 644 */     sql.append(" AND f.idOrganizacion = :idOrganizacion AND f.estado != :estadoAnulado");
/*  557: 645 */     sql.append(" AND (sp.idSubcategoriaProducto = :idSubcategoriaProducto OR :idSubcategoriaProducto=0) ");
/*  558: 646 */     sql.append(" AND d.documentoBase = :documentoBase AND d.indicadorDocumentoExterior = false");
/*  559: 647 */     sql.append(" GROUP BY e.nombreComercial, e.identificacion, p.codigo, p.codigoComercial, p.nombre, u.codigo, u.nombre");
/*  560: 648 */     sql.append(" ORDER BY e.nombreComercial, p.nombre ");
/*  561:     */     
/*  562: 650 */     Query query = this.em.createQuery(sql.toString());
/*  563: 651 */     query.setParameter("fechaDesde", fechaDesde);
/*  564: 652 */     query.setParameter("fechaHasta", fechaHasta);
/*  565: 653 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/*  566: 654 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  567: 655 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  568: 656 */     query.setParameter("idSubcategoriaProducto", Integer.valueOf(idSubcategoriaProducto));
/*  569: 657 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_PROVEEDOR);
/*  570:     */     
/*  571: 659 */     return query.getResultList();
/*  572:     */   }
/*  573:     */   
/*  574:     */   public List getReporteAnalisisComprasProducto(Date fechaDesde, Date fechaHasta, boolean saldoInicial, int idOrganizacion, int idSubcategoriaProducto, boolean indicadorCantidad)
/*  575:     */   {
/*  576:     */     String sumatoria;
/*  577:     */     String sumatoria;
/*  578: 667 */     if (indicadorCantidad) {
/*  579: 668 */       sumatoria = "SUM(df.cantidad)";
/*  580:     */     } else {
/*  581: 670 */       sumatoria = "ROUND(SUM(df.cantidad*(df.precio-df.descuento)),2)";
/*  582:     */     }
/*  583: 673 */     StringBuilder sql = new StringBuilder();
/*  584: 674 */     sql.append(" SELECT e.nombreComercial, p.codigo, p.codigoComercial, p.nombre, u.nombre," + sumatoria + ", MONTH(f.fecha)");
/*  585: 675 */     sql.append(" FROM DetalleFacturaProveedor df ");
/*  586: 676 */     sql.append(" LEFT OUTER JOIN df.facturaProveedor f ");
/*  587: 677 */     sql.append(" LEFT OUTER JOIN f.empresa e ");
/*  588: 678 */     sql.append(" LEFT OUTER JOIN f.documento d ");
/*  589: 679 */     sql.append(" LEFT OUTER JOIN df.producto p ");
/*  590: 680 */     sql.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/*  591: 681 */     sql.append(" LEFT OUTER JOIN p.unidad u ");
/*  592: 682 */     sql.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  593: 683 */     sql.append(" AND f.indicadorSaldoInicial = :saldoInicial ");
/*  594: 684 */     sql.append(" AND f.idOrganizacion = :idOrganizacion AND f.estado != :estadoAnulado");
/*  595: 685 */     sql.append(" AND d.documentoBase = :documentoBase AND d.indicadorDocumentoExterior = false");
/*  596: 686 */     sql.append(" AND (sp.idSubcategoriaProducto = :idSubcategoriaProducto OR :idSubcategoriaProducto=0) ");
/*  597: 687 */     sql.append(" GROUP BY e.nombreComercial, p.codigo, p.codigoComercial, p.nombre, u.codigo, u.nombre, MONTH(f.fecha)");
/*  598: 688 */     sql.append(" ORDER BY e.nombreComercial, p.nombre, MONTH(f.fecha)");
/*  599:     */     
/*  600: 690 */     Query query = this.em.createQuery(sql.toString());
/*  601: 691 */     query.setParameter("fechaDesde", fechaDesde);
/*  602: 692 */     query.setParameter("fechaHasta", fechaHasta);
/*  603: 693 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/*  604: 694 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  605: 695 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  606: 696 */     query.setParameter("idSubcategoriaProducto", Integer.valueOf(idSubcategoriaProducto));
/*  607: 697 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_PROVEEDOR);
/*  608:     */     
/*  609: 699 */     return query.getResultList();
/*  610:     */   }
/*  611:     */   
/*  612:     */   public List<Object[]> getStockMinimoProducto(int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/*  613:     */   {
/*  614: 705 */     StringBuilder sql = new StringBuilder();
/*  615: 706 */     sql.append("SELECT p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, AVG(pb.saldoMinimo)");
/*  616: 707 */     sql.append(" FROM ProductoBodega pb");
/*  617: 708 */     sql.append(" INNER JOIN pb.producto p");
/*  618: 709 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/*  619: 710 */     sql.append(" WHERE p.idOrganizacion=:idOrganizacion");
/*  620: 711 */     if (subcategoriaProducto != null) {
/*  621: 712 */       sql.append(" AND sp = :subcategoriaProducto");
/*  622:     */     }
/*  623: 714 */     if (categoriaProducto != null) {
/*  624: 715 */       sql.append(" AND sp.categoriaProducto = :categoriaProducto");
/*  625:     */     }
/*  626: 717 */     if (atributo != null) {
/*  627: 718 */       agregarFiltroAtributo(sql, categoriaProducto, subcategoriaProducto, valorAtributoSeleccionado, textoValorAtributo);
/*  628:     */     }
/*  629: 720 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre");
/*  630: 721 */     sql.append(" ORDER BY sp.codigo, sp.nombre, p.idProducto, p.codigo, p.nombre");
/*  631:     */     
/*  632: 723 */     Query query = this.em.createQuery(sql.toString());
/*  633: 724 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  634: 725 */     if (subcategoriaProducto != null) {
/*  635: 726 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/*  636:     */     }
/*  637: 728 */     if (categoriaProducto != null) {
/*  638: 729 */       query.setParameter("categoriaProducto", categoriaProducto);
/*  639:     */     }
/*  640: 731 */     if (atributo != null) {
/*  641: 732 */       agregarParametrosAtributo(query, atributo, valorAtributoSeleccionado, textoValorAtributo);
/*  642:     */     }
/*  643: 734 */     return query.getResultList();
/*  644:     */   }
/*  645:     */   
/*  646:     */   public List<Object[]> getCosumoMensualPromedio(int idOrganizacion, Date fechaConsumoValido, Date fechaDesde, Date fechaCorte, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Bodega bodega, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo, String detalle, String cabecera)
/*  647:     */   {
/*  648: 741 */     StringBuilder sql = new StringBuilder();
/*  649: 742 */     sql.append("SELECT p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, SUM(det.cantidad)");
/*  650: 743 */     sql.append(" FROM " + detalle + " det");
/*  651: 744 */     if (bodega != null) {
/*  652: 745 */       if ("DetalleMovimientoInventario".equals(detalle)) {
/*  653: 746 */         sql.append(" INNER JOIN det.bodegaOrigen b");
/*  654:     */       } else {
/*  655: 748 */         sql.append(" INNER JOIN det.bodega b");
/*  656:     */       }
/*  657:     */     }
/*  658: 751 */     sql.append(" INNER JOIN det.producto p");
/*  659: 752 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/*  660: 753 */     sql.append(" INNER JOIN det." + cabecera + " c");
/*  661: 754 */     if ("DetalleMovimientoInventario".equals(detalle)) {
/*  662: 755 */       sql.append(" INNER JOIN c.documento d");
/*  663:     */     }
/*  664: 757 */     sql.append(" WHERE c.idOrganizacion=:idOrganizacion");
/*  665: 758 */     if ("DetalleMovimientoInventario".equals(detalle)) {
/*  666: 759 */       sql.append(" AND d.documentoBase = :documentoConsumoBodega");
/*  667:     */     }
/*  668: 761 */     sql.append(" AND c.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  669: 762 */     sql.append(" AND  EXISTS (");
/*  670: 763 */     sql.append("\t\tSELECT 1");
/*  671: 764 */     sql.append("\t\tFROM InventarioProducto ip");
/*  672: 765 */     sql.append(" \t\tINNER JOIN ip.documento vd");
/*  673: 766 */     if (bodega != null) {
/*  674: 767 */       sql.append(" \tINNER JOIN ip.bodega vb");
/*  675:     */     }
/*  676: 769 */     sql.append(" \t\tINNER JOIN ip.producto vp");
/*  677: 770 */     sql.append(" \t\tINNER JOIN vp.subcategoriaProducto vsp ");
/*  678: 771 */     sql.append(" \t\tWHERE ip.idOrganizacion=:idOrganizacion");
/*  679: 772 */     sql.append(" \t\tAND vd.documentoBase = :documentoInventario");
/*  680: 773 */     sql.append(" \t\tAND ip.fecha >= :fechaConsumoValido");
/*  681: 774 */     if (subcategoriaProducto != null) {
/*  682: 775 */       sql.append(" \tAND vsp = :subcategoriaProducto");
/*  683:     */     }
/*  684: 777 */     if (categoriaProducto != null) {
/*  685: 778 */       sql.append(" \tAND vsp.categoriaProducto = :categoriaProducto");
/*  686:     */     }
/*  687: 780 */     if (bodega != null) {
/*  688: 781 */       sql.append(" \tAND vb = :bodega");
/*  689:     */     }
/*  690: 783 */     if (producto != null) {
/*  691: 784 */       sql.append(" \tAND vp = :producto");
/*  692:     */     }
/*  693: 786 */     if (atributo != null)
/*  694:     */     {
/*  695: 787 */       sql.append(" \t\tAND vp IN (");
/*  696: 788 */       sql.append("\t\t\t\tSELECT prod");
/*  697: 789 */       sql.append("\t\t\t\tFROM ProductoAtributo pratr");
/*  698: 790 */       sql.append("\t\t\t\tINNER JOIN prat.atributo atr");
/*  699: 791 */       sql.append("\t\t\t\tINNER JOIN prat.producto prod");
/*  700: 792 */       if ((subcategoriaProducto != null) || (categoriaProducto != null)) {
/*  701: 793 */         sql.append("\t\t\tINNER JOIN prod.subcategoriaProducto subpro");
/*  702:     */       }
/*  703: 795 */       sql.append("\t\t\t\tLEFT JOIN pratr.valorAtributo vatr");
/*  704: 796 */       sql.append("\t\t\t\tWHERE atr = :atributo");
/*  705: 797 */       if (valorAtributoSeleccionado != null) {
/*  706: 798 */         sql.append("\t\t\tAND vatr = :valorAtributoSeleccionado");
/*  707: 799 */       } else if ((textoValorAtributo != null) && (!textoValorAtributo.isEmpty())) {
/*  708: 800 */         sql.append("\t\t\tAND vatr.valor = :textoValorAtributo");
/*  709:     */       }
/*  710: 802 */       if (subcategoriaProducto != null) {
/*  711: 803 */         sql.append("\t\t\tAND subpro = :subcategoriaProducto");
/*  712:     */       }
/*  713: 805 */       if (categoriaProducto != null) {
/*  714: 806 */         sql.append("\t\t\tAND subpro.categoriaProducto = :categoriaProducto");
/*  715:     */       }
/*  716: 808 */       sql.append("\t\t\t\t)");
/*  717:     */     }
/*  718: 810 */     sql.append(")");
/*  719: 811 */     if (subcategoriaProducto != null) {
/*  720: 812 */       sql.append(" AND sp = :subcategoriaProducto");
/*  721:     */     }
/*  722: 814 */     if (categoriaProducto != null) {
/*  723: 815 */       sql.append(" AND sp.categoriaProducto = :categoriaProducto");
/*  724:     */     }
/*  725: 817 */     if (producto != null) {
/*  726: 818 */       sql.append(" AND p = :producto");
/*  727:     */     }
/*  728: 820 */     if (bodega != null) {
/*  729: 821 */       sql.append(" AND b = :bodega");
/*  730:     */     }
/*  731: 823 */     if (atributo != null) {
/*  732: 824 */       agregarFiltroAtributo(sql, categoriaProducto, subcategoriaProducto, valorAtributoSeleccionado, textoValorAtributo);
/*  733:     */     }
/*  734: 826 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre");
/*  735: 827 */     sql.append(" ORDER BY sp.codigo, sp.nombre, p.idProducto, p.codigo, p.nombre");
/*  736:     */     
/*  737: 829 */     Query query = this.em.createQuery(sql.toString());
/*  738: 830 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  739: 831 */     if ("DetalleMovimientoInventario".equals(detalle)) {
/*  740: 832 */       query.setParameter("documentoConsumoBodega", DocumentoBase.CONSUMO_BODEGA);
/*  741:     */     }
/*  742: 834 */     if ("DetalleMovimientoInventario".equals(detalle)) {
/*  743: 835 */       query.setParameter("documentoInventario", DocumentoBase.CONSUMO_BODEGA);
/*  744:     */     } else {
/*  745: 837 */       query.setParameter("documentoInventario", DocumentoBase.DESPACHO_BODEGA);
/*  746:     */     }
/*  747: 839 */     query.setParameter("fechaConsumoValido", fechaConsumoValido);
/*  748: 840 */     query.setParameter("fechaDesde", fechaDesde);
/*  749: 841 */     query.setParameter("fechaHasta", fechaCorte);
/*  750: 843 */     if (subcategoriaProducto != null) {
/*  751: 844 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/*  752:     */     }
/*  753: 846 */     if (categoriaProducto != null) {
/*  754: 847 */       query.setParameter("categoriaProducto", categoriaProducto);
/*  755:     */     }
/*  756: 849 */     if (atributo != null) {
/*  757: 850 */       agregarParametrosAtributo(query, atributo, valorAtributoSeleccionado, textoValorAtributo);
/*  758:     */     }
/*  759: 852 */     if (producto != null) {
/*  760: 853 */       query.setParameter("producto", producto);
/*  761:     */     }
/*  762: 855 */     if (bodega != null) {
/*  763: 856 */       query.setParameter("bodega", bodega);
/*  764:     */     }
/*  765: 858 */     return query.getResultList();
/*  766:     */   }
/*  767:     */   
/*  768:     */   public List<Object[]> getStockEnTransitoLocal(int idOrganizacion, Date fechaConsumoValido, Date fechaDesde, Date fechaCorte, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Bodega bodega, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/*  769:     */   {
/*  770: 865 */     StringBuilder sql = new StringBuilder();
/*  771: 866 */     sql.append("SELECT p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, SUM(dpp.cantidadPorRecibir)");
/*  772: 867 */     sql.append(" FROM DetallePedidoProveedor dpp");
/*  773: 868 */     sql.append(" INNER JOIN dpp.producto p");
/*  774: 869 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/*  775: 870 */     sql.append(" INNER JOIN dpp.pedidoProveedor pp");
/*  776: 871 */     if (bodega != null) {
/*  777: 872 */       sql.append(" INNER JOIN pp.bodega b");
/*  778:     */     }
/*  779: 874 */     sql.append(" WHERE pp.idOrganizacion=:idOrganizacion");
/*  780: 875 */     sql.append(" AND pp.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  781: 876 */     sql.append(" AND pp.estado != :estadoCerrado");
/*  782: 877 */     sql.append(" AND pp.estado != :estadoAnulado");
/*  783: 878 */     if (subcategoriaProducto != null) {
/*  784: 879 */       sql.append(" AND sp = :subcategoriaProducto");
/*  785:     */     }
/*  786: 881 */     if (categoriaProducto != null) {
/*  787: 882 */       sql.append(" AND sp.categoriaProducto = :categoriaProducto");
/*  788:     */     }
/*  789: 884 */     if (atributo != null) {
/*  790: 885 */       agregarFiltroAtributo(sql, categoriaProducto, subcategoriaProducto, valorAtributoSeleccionado, textoValorAtributo);
/*  791:     */     }
/*  792: 887 */     if (producto != null) {
/*  793: 888 */       sql.append(" AND p = :producto");
/*  794:     */     }
/*  795: 890 */     if (bodega != null) {
/*  796: 891 */       sql.append(" AND b = :bodega");
/*  797:     */     }
/*  798: 893 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre");
/*  799: 894 */     sql.append(" ORDER BY sp.codigo, sp.nombre, p.idProducto, p.codigo, p.nombre");
/*  800:     */     
/*  801: 896 */     Query query = this.em.createQuery(sql.toString());
/*  802: 897 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  803: 898 */     query.setParameter("fechaDesde", fechaDesde);
/*  804: 899 */     query.setParameter("fechaHasta", fechaCorte);
/*  805: 900 */     query.setParameter("estadoCerrado", Estado.CERRADO);
/*  806: 901 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  807: 903 */     if (subcategoriaProducto != null) {
/*  808: 904 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/*  809:     */     }
/*  810: 906 */     if (categoriaProducto != null) {
/*  811: 907 */       query.setParameter("categoriaProducto", categoriaProducto);
/*  812:     */     }
/*  813: 909 */     if (atributo != null) {
/*  814: 910 */       agregarParametrosAtributo(query, atributo, valorAtributoSeleccionado, textoValorAtributo);
/*  815:     */     }
/*  816: 912 */     if (producto != null) {
/*  817: 913 */       query.setParameter("producto", producto);
/*  818:     */     }
/*  819: 915 */     if (bodega != null) {
/*  820: 916 */       query.setParameter("bodega", bodega);
/*  821:     */     }
/*  822: 918 */     return query.getResultList();
/*  823:     */   }
/*  824:     */   
/*  825:     */   public List<Object[]> getStockEnTransitoImportacion(int idOrganizacion, Date fechaConsumoValido, Date fechaDesde, Date fechaCorte, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Bodega bodega, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/*  826:     */   {
/*  827: 925 */     StringBuilder sql = new StringBuilder();
/*  828: 926 */     sql.append("SELECT p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, SUM(dfp.cantidad)");
/*  829: 927 */     sql.append(" FROM DetalleFacturaProveedor dfp");
/*  830: 928 */     if (bodega != null) {
/*  831: 929 */       sql.append(" INNER JOIN dfp.bodega b");
/*  832:     */     }
/*  833: 931 */     sql.append(" INNER JOIN dfp.producto p");
/*  834: 932 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/*  835: 933 */     sql.append(" INNER JOIN dfp.facturaProveedor fp");
/*  836: 934 */     sql.append(" INNER JOIN fp.documento d");
/*  837: 935 */     sql.append(" WHERE fp.idOrganizacion=:idOrganizacion");
/*  838: 936 */     sql.append(" AND fp.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  839: 937 */     sql.append(" AND fp.estado != :estadoAnulado");
/*  840: 938 */     sql.append(" AND fp.recepcionProveedor IS NULL");
/*  841: 939 */     sql.append(" AND d.documentoBase = :documentoImportacion");
/*  842: 940 */     if (subcategoriaProducto != null) {
/*  843: 941 */       sql.append(" AND sp = :subcategoriaProducto");
/*  844:     */     }
/*  845: 943 */     if (categoriaProducto != null) {
/*  846: 944 */       sql.append(" AND sp.categoriaProducto = :categoriaProducto");
/*  847:     */     }
/*  848: 946 */     if (producto != null) {
/*  849: 947 */       sql.append(" AND p = :producto");
/*  850:     */     }
/*  851: 949 */     if (bodega != null) {
/*  852: 950 */       sql.append(" AND b = :bodega");
/*  853:     */     }
/*  854: 952 */     if (atributo != null) {
/*  855: 953 */       agregarFiltroAtributo(sql, categoriaProducto, subcategoriaProducto, valorAtributoSeleccionado, textoValorAtributo);
/*  856:     */     }
/*  857: 955 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre");
/*  858: 956 */     sql.append(" ORDER BY sp.codigo, sp.nombre, p.idProducto, p.codigo, p.nombre");
/*  859:     */     
/*  860: 958 */     Query query = this.em.createQuery(sql.toString());
/*  861: 959 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  862: 960 */     query.setParameter("fechaDesde", fechaDesde);
/*  863: 961 */     query.setParameter("fechaHasta", fechaCorte);
/*  864: 962 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  865: 963 */     query.setParameter("documentoImportacion", DocumentoBase.PEDIDO_IMPORTACION);
/*  866: 965 */     if (subcategoriaProducto != null) {
/*  867: 966 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/*  868:     */     }
/*  869: 968 */     if (categoriaProducto != null) {
/*  870: 969 */       query.setParameter("categoriaProducto", categoriaProducto);
/*  871:     */     }
/*  872: 971 */     if (atributo != null) {
/*  873: 972 */       agregarParametrosAtributo(query, atributo, valorAtributoSeleccionado, textoValorAtributo);
/*  874:     */     }
/*  875: 974 */     if (producto != null) {
/*  876: 975 */       query.setParameter("producto", producto);
/*  877:     */     }
/*  878: 977 */     if (bodega != null) {
/*  879: 978 */       query.setParameter("bodega", bodega);
/*  880:     */     }
/*  881: 980 */     return query.getResultList();
/*  882:     */   }
/*  883:     */   
/*  884:     */   public List<Object[]> getRecepcionProveedor(int idOrganizacion, Date fechaDesde, Date fechaCorte, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Bodega bodega, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/*  885:     */   {
/*  886: 987 */     StringBuilder sql = new StringBuilder();
/*  887: 988 */     sql.append("SELECT p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, SUM(drp.cantidad)");
/*  888: 989 */     sql.append(" FROM DetalleRecepcionProveedor drp");
/*  889: 990 */     if (bodega != null) {
/*  890: 991 */       sql.append(" INNER JOIN drp.bodega b");
/*  891:     */     }
/*  892: 993 */     sql.append(" INNER JOIN drp.producto p");
/*  893: 994 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/*  894: 995 */     sql.append(" INNER JOIN drp.recepcionProveedor rp");
/*  895: 996 */     sql.append(" WHERE rp.idOrganizacion=:idOrganizacion");
/*  896: 997 */     sql.append(" AND rp.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  897: 998 */     sql.append(" AND rp.estado != :estadoAnulado");
/*  898: 999 */     if (subcategoriaProducto != null) {
/*  899:1000 */       sql.append(" AND sp = :subcategoriaProducto");
/*  900:     */     }
/*  901:1002 */     if (categoriaProducto != null) {
/*  902:1003 */       sql.append(" AND sp.categoriaProducto = :categoriaProducto");
/*  903:     */     }
/*  904:1005 */     if (bodega != null) {
/*  905:1006 */       sql.append(" AND b = :bodega");
/*  906:     */     }
/*  907:1008 */     if (producto != null) {
/*  908:1009 */       sql.append(" AND p = :producto");
/*  909:     */     }
/*  910:1011 */     if (atributo != null) {
/*  911:1012 */       agregarFiltroAtributo(sql, categoriaProducto, subcategoriaProducto, valorAtributoSeleccionado, textoValorAtributo);
/*  912:     */     }
/*  913:1014 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre");
/*  914:1015 */     sql.append(" ORDER BY sp.codigo, sp.nombre, p.idProducto, p.codigo, p.nombre");
/*  915:     */     
/*  916:1017 */     Query query = this.em.createQuery(sql.toString());
/*  917:1018 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  918:1019 */     query.setParameter("fechaDesde", fechaDesde);
/*  919:1020 */     query.setParameter("fechaHasta", fechaCorte);
/*  920:1021 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  921:1023 */     if (subcategoriaProducto != null) {
/*  922:1024 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/*  923:     */     }
/*  924:1026 */     if (categoriaProducto != null) {
/*  925:1027 */       query.setParameter("categoriaProducto", categoriaProducto);
/*  926:     */     }
/*  927:1029 */     if (atributo != null) {
/*  928:1030 */       agregarParametrosAtributo(query, atributo, valorAtributoSeleccionado, textoValorAtributo);
/*  929:     */     }
/*  930:1032 */     if (producto != null) {
/*  931:1033 */       query.setParameter("producto", producto);
/*  932:     */     }
/*  933:1035 */     if (bodega != null) {
/*  934:1036 */       query.setParameter("bodega", bodega);
/*  935:     */     }
/*  936:1038 */     return query.getResultList();
/*  937:     */   }
/*  938:     */   
/*  939:     */   public List<Object[]> getDevolucionCliente(int idOrganizacion, Date fechaDesde, Date fechaCorte, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Bodega bodega, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/*  940:     */   {
/*  941:1045 */     StringBuilder sql = new StringBuilder();
/*  942:1046 */     sql.append("SELECT p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, SUM(dfc.cantidad)");
/*  943:1047 */     sql.append(" FROM DetalleFacturaCliente dfc");
/*  944:1048 */     if (bodega != null) {
/*  945:1049 */       sql.append(" INNER JOIN dfc.bodega b");
/*  946:     */     }
/*  947:1051 */     sql.append(" INNER JOIN dfc.producto p");
/*  948:1052 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/*  949:1053 */     sql.append(" INNER JOIN dfc.facturaCliente fc");
/*  950:1054 */     sql.append(" INNER JOIN fc.documento d");
/*  951:1055 */     sql.append(" WHERE fc.idOrganizacion=:idOrganizacion");
/*  952:1056 */     sql.append(" AND fc.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  953:1057 */     sql.append(" AND fc.estado != :estadoAnulado");
/*  954:1058 */     sql.append(" AND d.documentoBase = :documentoDevolucion");
/*  955:1059 */     if (subcategoriaProducto != null) {
/*  956:1060 */       sql.append(" AND sp = :subcategoriaProducto");
/*  957:     */     }
/*  958:1062 */     if (categoriaProducto != null) {
/*  959:1063 */       sql.append(" AND sp.categoriaProducto = :categoriaProducto");
/*  960:     */     }
/*  961:1065 */     if (bodega != null) {
/*  962:1066 */       sql.append(" AND b = :bodega");
/*  963:     */     }
/*  964:1068 */     if (producto != null) {
/*  965:1069 */       sql.append(" AND p = :producto");
/*  966:     */     }
/*  967:1071 */     if (atributo != null) {
/*  968:1072 */       agregarFiltroAtributo(sql, categoriaProducto, subcategoriaProducto, valorAtributoSeleccionado, textoValorAtributo);
/*  969:     */     }
/*  970:1074 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre");
/*  971:1075 */     sql.append(" ORDER BY sp.codigo, sp.nombre, p.idProducto, p.codigo, p.nombre");
/*  972:     */     
/*  973:1077 */     Query query = this.em.createQuery(sql.toString());
/*  974:1078 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  975:1079 */     query.setParameter("fechaDesde", fechaDesde);
/*  976:1080 */     query.setParameter("fechaHasta", fechaCorte);
/*  977:1081 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  978:1082 */     query.setParameter("documentoDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
/*  979:1084 */     if (subcategoriaProducto != null) {
/*  980:1085 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/*  981:     */     }
/*  982:1087 */     if (categoriaProducto != null) {
/*  983:1088 */       query.setParameter("categoriaProducto", categoriaProducto);
/*  984:     */     }
/*  985:1090 */     if (atributo != null) {
/*  986:1091 */       agregarParametrosAtributo(query, atributo, valorAtributoSeleccionado, textoValorAtributo);
/*  987:     */     }
/*  988:1093 */     if (producto != null) {
/*  989:1094 */       query.setParameter("producto", producto);
/*  990:     */     }
/*  991:1096 */     if (bodega != null) {
/*  992:1097 */       query.setParameter("bodega", bodega);
/*  993:     */     }
/*  994:1099 */     return query.getResultList();
/*  995:     */   }
/*  996:     */   
/*  997:     */   public List<Object[]> getConsumosBodega(int idOrganizacion, Date fechaDesde, Date fechaCorte, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Bodega bodega, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/*  998:     */   {
/*  999:1106 */     StringBuilder sql = new StringBuilder();
/* 1000:1107 */     sql.append("SELECT p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, SUM(dmi.cantidad)");
/* 1001:1108 */     sql.append(" FROM DetalleMovimientoInventario dmi");
/* 1002:1109 */     if (bodega != null) {
/* 1003:1110 */       sql.append(" INNER JOIN dmi.bodegaOrigen b");
/* 1004:     */     }
/* 1005:1112 */     sql.append(" INNER JOIN dmi.producto p");
/* 1006:1113 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 1007:1114 */     sql.append(" INNER JOIN dmi.movimientoInventario mi");
/* 1008:1115 */     sql.append(" INNER JOIN mi.documento d");
/* 1009:1116 */     sql.append(" WHERE mi.idOrganizacion=:idOrganizacion");
/* 1010:1117 */     sql.append(" AND (d.documentoBase = :documentoConsumoBodega OR d.documentoBase = :documentoSalidaMaterial)");
/* 1011:1118 */     sql.append(" AND mi.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 1012:1119 */     if (subcategoriaProducto != null) {
/* 1013:1120 */       sql.append(" AND sp = :subcategoriaProducto");
/* 1014:     */     }
/* 1015:1122 */     if (categoriaProducto != null) {
/* 1016:1123 */       sql.append(" AND sp.categoriaProducto = :categoriaProducto");
/* 1017:     */     }
/* 1018:1125 */     if (bodega != null) {
/* 1019:1126 */       sql.append(" AND b = :bodega");
/* 1020:     */     }
/* 1021:1128 */     if (producto != null) {
/* 1022:1129 */       sql.append(" AND p = :producto");
/* 1023:     */     }
/* 1024:1131 */     if (atributo != null) {
/* 1025:1132 */       agregarFiltroAtributo(sql, categoriaProducto, subcategoriaProducto, valorAtributoSeleccionado, textoValorAtributo);
/* 1026:     */     }
/* 1027:1134 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre");
/* 1028:1135 */     sql.append(" ORDER BY sp.codigo, sp.nombre, p.idProducto, p.codigo, p.nombre");
/* 1029:     */     
/* 1030:1137 */     Query query = this.em.createQuery(sql.toString());
/* 1031:1138 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1032:1139 */     query.setParameter("documentoConsumoBodega", DocumentoBase.CONSUMO_BODEGA);
/* 1033:1140 */     query.setParameter("documentoSalidaMaterial", DocumentoBase.SALIDA_MATERIAL);
/* 1034:1141 */     query.setParameter("fechaDesde", fechaDesde);
/* 1035:1142 */     query.setParameter("fechaHasta", fechaCorte);
/* 1036:1144 */     if (subcategoriaProducto != null) {
/* 1037:1145 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 1038:     */     }
/* 1039:1147 */     if (categoriaProducto != null) {
/* 1040:1148 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 1041:     */     }
/* 1042:1150 */     if (atributo != null) {
/* 1043:1151 */       agregarParametrosAtributo(query, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 1044:     */     }
/* 1045:1153 */     if (producto != null) {
/* 1046:1154 */       query.setParameter("producto", producto);
/* 1047:     */     }
/* 1048:1156 */     if (bodega != null) {
/* 1049:1157 */       query.setParameter("bodega", bodega);
/* 1050:     */     }
/* 1051:1159 */     return query.getResultList();
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public List<Object[]> getDespachoCliente(int idOrganizacion, Date fechaDesde, Date fechaCorte, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Bodega bodega, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/* 1055:     */   {
/* 1056:1166 */     StringBuilder sql = new StringBuilder();
/* 1057:1167 */     sql.append("SELECT p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, SUM(ddc.cantidad)");
/* 1058:1168 */     sql.append(" FROM DetalleDespachoCliente ddc");
/* 1059:1169 */     if (bodega != null) {
/* 1060:1170 */       sql.append(" INNER JOIN ddc.bodega b");
/* 1061:     */     }
/* 1062:1172 */     sql.append(" INNER JOIN ddc.producto p");
/* 1063:1173 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 1064:1174 */     sql.append(" INNER JOIN ddc.despachoCliente dc");
/* 1065:1175 */     sql.append(" WHERE dc.idOrganizacion=:idOrganizacion");
/* 1066:1176 */     sql.append(" AND dc.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 1067:1177 */     sql.append(" AND dc.estado != :estadoAnulado");
/* 1068:1178 */     if (subcategoriaProducto != null) {
/* 1069:1179 */       sql.append(" AND sp = :subcategoriaProducto");
/* 1070:     */     }
/* 1071:1181 */     if (categoriaProducto != null) {
/* 1072:1182 */       sql.append(" AND sp.categoriaProducto = :categoriaProducto");
/* 1073:     */     }
/* 1074:1184 */     if (bodega != null) {
/* 1075:1185 */       sql.append(" AND b = :bodega");
/* 1076:     */     }
/* 1077:1187 */     if (producto != null) {
/* 1078:1188 */       sql.append(" AND p = :producto");
/* 1079:     */     }
/* 1080:1190 */     if (atributo != null) {
/* 1081:1191 */       agregarFiltroAtributo(sql, categoriaProducto, subcategoriaProducto, valorAtributoSeleccionado, textoValorAtributo);
/* 1082:     */     }
/* 1083:1193 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre");
/* 1084:1194 */     sql.append(" ORDER BY sp.codigo, sp.nombre, p.idProducto, p.codigo, p.nombre");
/* 1085:     */     
/* 1086:1196 */     Query query = this.em.createQuery(sql.toString());
/* 1087:1197 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1088:1198 */     query.setParameter("fechaDesde", fechaDesde);
/* 1089:1199 */     query.setParameter("fechaHasta", fechaCorte);
/* 1090:1200 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1091:1202 */     if (subcategoriaProducto != null) {
/* 1092:1203 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 1093:     */     }
/* 1094:1205 */     if (categoriaProducto != null) {
/* 1095:1206 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 1096:     */     }
/* 1097:1208 */     if (atributo != null) {
/* 1098:1209 */       agregarParametrosAtributo(query, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 1099:     */     }
/* 1100:1211 */     if (producto != null) {
/* 1101:1212 */       query.setParameter("producto", producto);
/* 1102:     */     }
/* 1103:1214 */     if (bodega != null) {
/* 1104:1215 */       query.setParameter("bodega", bodega);
/* 1105:     */     }
/* 1106:1217 */     return query.getResultList();
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public List<Object[]> getDevolucionProveedor(int idOrganizacion, Date fechaDesde, Date fechaCorte, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Bodega bodega, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/* 1110:     */   {
/* 1111:1224 */     StringBuilder sql = new StringBuilder();
/* 1112:1225 */     sql.append("SELECT p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre, SUM(dfp.cantidad)");
/* 1113:1226 */     sql.append(" FROM DetalleFacturaProveedor dfp");
/* 1114:1227 */     if (bodega != null) {
/* 1115:1228 */       sql.append(" INNER JOIN dfp.bodega b");
/* 1116:     */     }
/* 1117:1230 */     sql.append(" INNER JOIN dfp.producto p");
/* 1118:1231 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 1119:1232 */     sql.append(" INNER JOIN dfp.facturaProveedor fp");
/* 1120:1233 */     sql.append(" INNER JOIN fp.documento d");
/* 1121:1234 */     sql.append(" WHERE fp.idOrganizacion=:idOrganizacion");
/* 1122:1235 */     sql.append(" AND fp.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 1123:1236 */     sql.append(" AND fp.estado != :estadoAnulado");
/* 1124:1237 */     sql.append(" AND d.documentoBase = :documentoDevolucion");
/* 1125:1238 */     if (subcategoriaProducto != null) {
/* 1126:1239 */       sql.append(" AND sp = :subcategoriaProducto");
/* 1127:     */     }
/* 1128:1241 */     if (categoriaProducto != null) {
/* 1129:1242 */       sql.append(" AND sp.categoriaProducto = :categoriaProducto");
/* 1130:     */     }
/* 1131:1244 */     if (bodega != null) {
/* 1132:1245 */       sql.append(" AND b = :bodega");
/* 1133:     */     }
/* 1134:1247 */     if (producto != null) {
/* 1135:1248 */       sql.append(" AND p = :producto");
/* 1136:     */     }
/* 1137:1250 */     if (atributo != null) {
/* 1138:1251 */       agregarFiltroAtributo(sql, categoriaProducto, subcategoriaProducto, valorAtributoSeleccionado, textoValorAtributo);
/* 1139:     */     }
/* 1140:1253 */     sql.append(" GROUP BY p.idProducto, p.codigo, p.nombre, sp.codigo, sp.nombre");
/* 1141:1254 */     sql.append(" ORDER BY sp.codigo, sp.nombre, p.idProducto, p.codigo, p.nombre");
/* 1142:     */     
/* 1143:1256 */     Query query = this.em.createQuery(sql.toString());
/* 1144:1257 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1145:1258 */     query.setParameter("fechaDesde", fechaDesde);
/* 1146:1259 */     query.setParameter("fechaHasta", fechaCorte);
/* 1147:1260 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1148:1261 */     query.setParameter("documentoDevolucion", DocumentoBase.DEVOLUCION_PROVEEDOR);
/* 1149:1263 */     if (subcategoriaProducto != null) {
/* 1150:1264 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 1151:     */     }
/* 1152:1266 */     if (categoriaProducto != null) {
/* 1153:1267 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 1154:     */     }
/* 1155:1269 */     if (atributo != null) {
/* 1156:1270 */       agregarParametrosAtributo(query, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 1157:     */     }
/* 1158:1272 */     if (producto != null) {
/* 1159:1273 */       query.setParameter("producto", producto);
/* 1160:     */     }
/* 1161:1275 */     if (bodega != null) {
/* 1162:1276 */       query.setParameter("bodega", bodega);
/* 1163:     */     }
/* 1164:1278 */     return query.getResultList();
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   public List<Object[]> getFechaMinimaMovimientos(int idOrganizacion, Date fechaDesde, Date fechaCorte, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Bodega bodega, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/* 1168:     */   {
/* 1169:1285 */     StringBuilder sql = new StringBuilder();
/* 1170:1286 */     sql.append("SELECT p.idProducto, MIN(ip.fecha)");
/* 1171:1287 */     sql.append(" FROM InventarioProducto ip");
/* 1172:1288 */     if (bodega != null) {
/* 1173:1289 */       sql.append(" INNER JOIN ip.bodega b");
/* 1174:     */     }
/* 1175:1291 */     sql.append(" INNER JOIN ip.producto p");
/* 1176:1292 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 1177:1293 */     sql.append(" WHERE ip.idOrganizacion=:idOrganizacion");
/* 1178:1294 */     sql.append(" AND ip.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 1179:1295 */     if (subcategoriaProducto != null) {
/* 1180:1296 */       sql.append(" AND sp = :subcategoriaProducto");
/* 1181:     */     }
/* 1182:1298 */     if (categoriaProducto != null) {
/* 1183:1299 */       sql.append(" AND sp.categoriaProducto = :categoriaProducto");
/* 1184:     */     }
/* 1185:1301 */     if (bodega != null) {
/* 1186:1302 */       sql.append(" AND b = :bodega");
/* 1187:     */     }
/* 1188:1304 */     if (producto != null) {
/* 1189:1305 */       sql.append(" AND p = :producto");
/* 1190:     */     }
/* 1191:1307 */     if (atributo != null) {
/* 1192:1308 */       agregarFiltroAtributo(sql, categoriaProducto, subcategoriaProducto, valorAtributoSeleccionado, textoValorAtributo);
/* 1193:     */     }
/* 1194:1310 */     sql.append(" GROUP BY p.idProducto");
/* 1195:1311 */     sql.append(" ORDER BY p.idProducto");
/* 1196:     */     
/* 1197:1313 */     Query query = this.em.createQuery(sql.toString());
/* 1198:1314 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1199:1315 */     query.setParameter("fechaDesde", fechaDesde);
/* 1200:1316 */     query.setParameter("fechaHasta", fechaCorte);
/* 1201:1317 */     if (subcategoriaProducto != null) {
/* 1202:1318 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 1203:     */     }
/* 1204:1320 */     if (categoriaProducto != null) {
/* 1205:1321 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 1206:     */     }
/* 1207:1323 */     if (atributo != null) {
/* 1208:1324 */       agregarParametrosAtributo(query, atributo, valorAtributoSeleccionado, textoValorAtributo);
/* 1209:     */     }
/* 1210:1326 */     if (producto != null) {
/* 1211:1327 */       query.setParameter("producto", producto);
/* 1212:     */     }
/* 1213:1329 */     if (bodega != null) {
/* 1214:1330 */       query.setParameter("bodega", bodega);
/* 1215:     */     }
/* 1216:1332 */     return query.getResultList();
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   private void agregarFiltroAtributo(StringBuilder sql, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/* 1220:     */   {
/* 1221:1337 */     sql.append(" AND p IN (");
/* 1222:1338 */     sql.append("\tSELECT pr");
/* 1223:1339 */     sql.append("\tFROM ProductoAtributo pa");
/* 1224:1340 */     sql.append("\tINNER JOIN pa.atributo a");
/* 1225:1341 */     sql.append("\tINNER JOIN pa.producto pr");
/* 1226:1342 */     if ((subcategoriaProducto != null) || (categoriaProducto != null)) {
/* 1227:1343 */       sql.append("\tINNER JOIN pr.subcategoriaProducto sub");
/* 1228:     */     }
/* 1229:1345 */     sql.append("\tLEFT JOIN pa.valorAtributo v");
/* 1230:1346 */     sql.append("\tWHERE a = :atributo");
/* 1231:1347 */     if (valorAtributoSeleccionado != null) {
/* 1232:1348 */       sql.append("\tAND v = :valorAtributoSeleccionado");
/* 1233:1349 */     } else if ((textoValorAtributo != null) && (!textoValorAtributo.isEmpty())) {
/* 1234:1350 */       sql.append("\tAND pa.valor = :textoValorAtributo");
/* 1235:     */     }
/* 1236:1352 */     if (subcategoriaProducto != null) {
/* 1237:1353 */       sql.append("\tAND sub = :subcategoriaProducto");
/* 1238:     */     }
/* 1239:1355 */     if (categoriaProducto != null) {
/* 1240:1356 */       sql.append("\tAND sub.categoriaProducto = :categoriaProducto");
/* 1241:     */     }
/* 1242:1358 */     sql.append(")");
/* 1243:     */   }
/* 1244:     */   
/* 1245:     */   private void agregarParametrosAtributo(Query query, Atributo atributo, ValorAtributo valorAtributoSeleccionado, String textoValorAtributo)
/* 1246:     */   {
/* 1247:1362 */     query.setParameter("atributo", atributo);
/* 1248:1363 */     if (valorAtributoSeleccionado != null) {
/* 1249:1364 */       query.setParameter("valorAtributoSeleccionado", valorAtributoSeleccionado);
/* 1250:1365 */     } else if ((textoValorAtributo != null) && (!textoValorAtributo.isEmpty())) {
/* 1251:1366 */       query.setParameter("textoValorAtributo", textoValorAtributo);
/* 1252:     */     }
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   public ReporteFacturaProveedor getReporteFacturaProveedorAsiento(int idFacturaProveedor)
/* 1256:     */   {
/* 1257:1373 */     StringBuilder sql = new StringBuilder();
/* 1258:1374 */     sql.append(" SELECT new ReporteFacturaProveedor(af.idAsiento, ar.idAsiento, e.codigo, e.nombreFiscal, e.identificacion, ");
/* 1259:1375 */     sql.append(" CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4), ciu.nombre, de.telefono1, ");
/* 1260:1376 */     sql.append(" CONCAT(fps.establecimiento,'-', fps.puntoEmision, '-', fps.numero), ");
/* 1261:1377 */     sql.append(" CONCAT(fps.establecimientoRetencion,'-', fps.puntoEmisionRetencion, '-', fps.numeroRetencion), ");
/* 1262:1378 */     sql.append(" '', re.numero, af.numero, ar.numero, f.fecha, fps.fechaEmision, f.fechaVencimiento, re.fecha, f.numeroCuotas, ");
/* 1263:1379 */     sql.append(" tc.codigo, tc.nombre, ct.codigo, ct.nombre, '', f.usuarioCreacion, f.descripcion, '', c.diasPlazo, f.total, f.estado, f.indicadorCreditoTributario, are.idAsiento) ");
/* 1264:1380 */     sql.append(" FROM FacturaProveedor f ");
/* 1265:1381 */     sql.append(" INNER JOIN f.empresa e ");
/* 1266:1382 */     sql.append(" LEFT  JOIN f.condicionPago c ");
/* 1267:1383 */     sql.append(" LEFT  JOIN f.asiento af ");
/* 1268:1384 */     sql.append(" LEFT  JOIN f.direccionEmpresa de ");
/* 1269:1385 */     sql.append(" LEFT  JOIN f.recepcionProveedor re ");
/* 1270:1386 */     sql.append(" LEFT  JOIN re.asiento are ");
/* 1271:1387 */     sql.append(" LEFT  JOIN de.ubicacion u ");
/* 1272:1388 */     sql.append(" LEFT  JOIN de.ciudad ciu ");
/* 1273:1389 */     sql.append(" LEFT  JOIN f.facturaProveedorSRI fps ");
/* 1274:1390 */     sql.append(" LEFT  JOIN fps.tipoComprobanteSRI tc ");
/* 1275:1391 */     sql.append(" LEFT  JOIN fps.creditoTributarioSRI ct ");
/* 1276:1392 */     sql.append(" LEFT  JOIN fps.pago pr ");
/* 1277:1393 */     sql.append(" LEFT  JOIN pr.asiento ar ");
/* 1278:1394 */     sql.append(" WHERE f.idFacturaProveedor = :idFacturaProveedor ");
/* 1279:     */     
/* 1280:1396 */     Query query = this.em.createQuery(sql.toString());
/* 1281:1397 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1282:     */     
/* 1283:1399 */     ReporteFacturaProveedor reporteFacturaProveedor = (ReporteFacturaProveedor)query.getSingleResult();
/* 1284:     */     
/* 1285:     */ 
/* 1286:1402 */     sql = new StringBuilder();
/* 1287:1403 */     sql.append(" SELECT MAX(p.numero) FROM DetalleFacturaProveedor df ");
/* 1288:1404 */     sql.append(" INNER JOIN df.facturaProveedor f ");
/* 1289:1405 */     sql.append(" INNER JOIN df.detallePedidoProveedor dp ");
/* 1290:1406 */     sql.append(" INNER JOIN dp.pedidoProveedor p ");
/* 1291:1407 */     sql.append(" WHERE f.idFacturaProveedor = :idFacturaProveedor ");
/* 1292:1408 */     query = this.em.createQuery(sql.toString());
/* 1293:1409 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1294:1410 */     String numeroPedido = (String)query.getSingleResult();
/* 1295:1411 */     reporteFacturaProveedor.setNumeroPedido(numeroPedido);
/* 1296:     */     
/* 1297:     */ 
/* 1298:1414 */     sql = new StringBuilder();
/* 1299:1415 */     sql.append(" SELECT SUM(dop.valor), SUM(dop.valorAprobado), SUM(dop.valorPagado) FROM DetalleOrdenPagoProveedor dop ");
/* 1300:1416 */     sql.append(" INNER JOIN dop.ordenPagoProveedor op ");
/* 1301:1417 */     sql.append(" INNER JOIN dop.cuentaPorPagar cc ");
/* 1302:1418 */     sql.append(" INNER JOIN cc.facturaProveedor f ");
/* 1303:1419 */     sql.append(" WHERE f.idFacturaProveedor = :idFacturaProveedor ");
/* 1304:1420 */     sql.append(" AND op.estado = :estado ");
/* 1305:1421 */     query = this.em.createQuery(sql.toString());
/* 1306:1422 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1307:1423 */     query.setParameter("estado", Estado.APROBADO);
/* 1308:1424 */     List<Object[]> lista = query.getResultList();
/* 1309:1426 */     for (Object[] objects : lista)
/* 1310:     */     {
/* 1311:1427 */       reporteFacturaProveedor.setValorOrdenPago((BigDecimal)objects[0]);
/* 1312:1428 */       reporteFacturaProveedor.setValorAprobadoOrdenPago((BigDecimal)objects[1]);
/* 1313:1429 */       reporteFacturaProveedor.setValorPagadoOrdenPago((BigDecimal)objects[2]);
/* 1314:     */     }
/* 1315:1433 */     sql = new StringBuilder();
/* 1316:1434 */     sql.append(" SELECT SUM(cc.saldo) FROM CuentaPorPagar cc ");
/* 1317:1435 */     sql.append(" INNER JOIN cc.facturaProveedor f ");
/* 1318:1436 */     sql.append(" WHERE f.idFacturaProveedor = :idFacturaProveedor ");
/* 1319:1437 */     query = this.em.createQuery(sql.toString());
/* 1320:1438 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 1321:1439 */     reporteFacturaProveedor.setSaldo((BigDecimal)query.getSingleResult());
/* 1322:     */     
/* 1323:1441 */     return reporteFacturaProveedor;
/* 1324:     */   }
/* 1325:     */   
/* 1326:     */   public List<ReporteAsiento> getReporteAsiento(int idAsiento)
/* 1327:     */   {
/* 1328:1447 */     StringBuilder sql = new StringBuilder();
/* 1329:1448 */     sql.append(" SELECT new ReporteAsiento(a.numero, ta.nombre, a.concepto, a.fecha, cc.codigo, cc.codigoAlterno, cc.nombre, da.debe, da.haber, da.descripcion, ");
/* 1330:1449 */     sql.append(" d1.codigo, d1.nombre, d2.codigo, d2.nombre, d3.codigo, d3.nombre, d4.codigo, d4.nombre, d5.codigo, d5.nombre ) ");
/* 1331:1450 */     sql.append(" FROM DetalleAsiento da ");
/* 1332:1451 */     sql.append(" INNER JOIN da.asiento a ");
/* 1333:1452 */     sql.append(" INNER JOIN a.tipoAsiento ta ");
/* 1334:1453 */     sql.append(" INNER JOIN da.cuentaContable cc");
/* 1335:1454 */     sql.append(" LEFT JOIN  da.dimensionContable1 d1 ");
/* 1336:1455 */     sql.append(" LEFT JOIN  da.dimensionContable2 d2 ");
/* 1337:1456 */     sql.append(" LEFT JOIN  da.dimensionContable3 d3 ");
/* 1338:1457 */     sql.append(" LEFT JOIN  da.dimensionContable4 d4 ");
/* 1339:1458 */     sql.append(" LEFT JOIN  da.dimensionContable5 d5 ");
/* 1340:1459 */     sql.append(" WHERE a.idAsiento = :idAsiento");
/* 1341:1460 */     sql.append(" ORDER BY da.idDetalleAsiento ");
/* 1342:     */     
/* 1343:1462 */     Query qAsiento = this.em.createQuery(sql.toString());
/* 1344:1463 */     qAsiento.setParameter("idAsiento", Integer.valueOf(idAsiento));
/* 1345:     */     
/* 1346:1465 */     return qAsiento.getResultList();
/* 1347:     */   }
/* 1348:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.compras.ReporteCompraDao
 * JD-Core Version:    0.7.0.1
 */