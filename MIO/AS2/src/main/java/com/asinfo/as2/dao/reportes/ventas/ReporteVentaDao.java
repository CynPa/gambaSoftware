/*    1:     */ package com.asinfo.as2.dao.reportes.ventas;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*    4:     */ import com.asinfo.as2.entities.Atributo;
/*    5:     */ import com.asinfo.as2.entities.Canal;
/*    6:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*    7:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*    8:     */ import com.asinfo.as2.entities.Cobro;
/*    9:     */ import com.asinfo.as2.entities.DetalleCobro;
/*   10:     */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   11:     */ import com.asinfo.as2.entities.DimensionContable;
/*   12:     */ import com.asinfo.as2.entities.Empresa;
/*   13:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   14:     */ import com.asinfo.as2.entities.Producto;
/*   15:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   16:     */ import com.asinfo.as2.entities.Recaudador;
/*   17:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   18:     */ import com.asinfo.as2.entities.Subempresa;
/*   19:     */ import com.asinfo.as2.entities.Sucursal;
/*   20:     */ import com.asinfo.as2.entities.Zona;
/*   21:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   22:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   23:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   24:     */ import com.asinfo.as2.enumeraciones.OrdenamientoEnum;
/*   25:     */ import com.asinfo.as2.enumeraciones.TipoVentaEnum;
/*   26:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   27:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   28:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   29:     */ import java.io.PrintStream;
/*   30:     */ import java.math.BigDecimal;
/*   31:     */ import java.util.ArrayList;
/*   32:     */ import java.util.Date;
/*   33:     */ import java.util.HashMap;
/*   34:     */ import java.util.List;
/*   35:     */ import java.util.Map;
/*   36:     */ import javax.ejb.Stateless;
/*   37:     */ import javax.persistence.EntityManager;
/*   38:     */ import javax.persistence.Query;
/*   39:     */ import javax.persistence.TemporalType;
/*   40:     */ 
/*   41:     */ @Stateless
/*   42:     */ public class ReporteVentaDao
/*   43:     */   extends AbstractDaoAS2<FacturaCliente>
/*   44:     */ {
/*   45:     */   public ReporteVentaDao()
/*   46:     */   {
/*   47:  65 */     super(FacturaCliente.class);
/*   48:     */   }
/*   49:     */   
/*   50:     */   public List getReportePedidoCliente(int idPedidoCliente)
/*   51:     */     throws ExcepcionAS2
/*   52:     */   {
/*   53:  76 */     String sql = "SELECT e.nombreFiscal, u.direccion1, e.identificacion, p.fecha, d.cantidad, pr.codigo, pr.nombreComercial, d.precio, p.total, p.descuento, p.impuesto, c.nombre, p.numeroCuotas, p.descripcion  FROM DetallePedidoCliente d INNER JOIN d.pedidoCliente p  INNER JOIN p.empresa e  INNER JOIN p.direccionEmpresa de  INNER JOIN de.ubicacion u INNER JOIN d.producto pr  INNER JOIN p.condicionPago c  WHERE p.idPedidoCliente = :idPedidoCliente";
/*   54:     */     
/*   55:     */ 
/*   56:     */ 
/*   57:     */ 
/*   58:     */ 
/*   59:  82 */     Query query = this.em.createQuery(sql).setParameter("idPedidoCliente", Integer.valueOf(idPedidoCliente));
/*   60:  83 */     return query.getResultList();
/*   61:     */   }
/*   62:     */   
/*   63:     */   public List getListaReporteFacturacionResumido(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente)
/*   64:     */     throws ExcepcionAS2Ventas
/*   65:     */   {
/*   66: 101 */     String sql = "SELECT f.numero, f.fecha, e.nombreFiscal, f.total, f.descuento,  f.impuesto FROM FacturaCliente f  INNER JOIN f.empresa e  WHERE f.fecha >= :fechaDesde  AND f.fecha <= :fechaHasta  AND f.estado != :estadoAnulado";
/*   67: 104 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0)) {
/*   68: 105 */       sql = sql + " AND f.numero >= :numeroDesde AND f.numero <= :numeroHasta ";
/*   69:     */     }
/*   70: 108 */     if (idCliente != 0) {
/*   71: 109 */       sql = sql + " AND f.empresa.idEmpresa = :idEmpresa";
/*   72:     */     }
/*   73: 112 */     sql = sql + " ORDER BY f.numero";
/*   74:     */     
/*   75: 114 */     Query query = this.em.createQuery(sql).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).setParameter("estadoAnulado", Estado.ANULADO);
/*   76: 117 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0)) {
/*   77: 118 */       query = query.setParameter("numeroDesde", numeroDesde).setParameter("numeroHasta", numeroHasta);
/*   78:     */     }
/*   79: 121 */     if (idCliente != 0) {
/*   80: 122 */       query = query.setParameter("idEmpresa", Integer.valueOf(idCliente));
/*   81:     */     }
/*   82: 125 */     return query.getResultList();
/*   83:     */   }
/*   84:     */   
/*   85:     */   public List getListaReporteFacturacionDetallado(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, Sucursal sucursal, PuntoDeVenta puntoVenta, TipoVentaEnum tipoVenta, boolean saldoInicial, boolean indicadorTipoReporte, int idOrganizacion, DocumentoBase documentoBase, int idMotivoNotaCreditoCliente, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Atributo atributo, String valorAtributo, DimensionContable proyecto)
/*   86:     */   {
/*   87: 151 */     System.out.println("entrooo query");
/*   88: 152 */     StringBuilder sql2 = new StringBuilder();
/*   89: 153 */     sql2.append(" SELECT s.codigo, s.nombre, c.codigo, c.nombre, z.nombre, z.nombre, ac.codigo, ac.nombre1, ");
/*   90: 154 */     sql2.append(" f.numero, f.fecha, e.nombreFiscal, e.identificacion, f.total, f.descuento, f.impuesto, ");
/*   91: 155 */     sql2.append(" p.codigo, p.codigoComercial, p.nombre, u.codigo, df.cantidad, df.precio, df.descripcion, df.descuento, fp.numero, d.codigo, d.nombre, CASE WHEN d.documentoBase=:devolucionCliente THEN -1 ELSE d.operacion END, ");
/*   92:     */     
/*   93:     */ 
/*   94: 158 */     sql2.append(" (df.precioLinea-df.descuentoLinea)*coalesce(ipfc.porcentajeImpuesto,0)/100.00, df.peso, df.precioLinea, df.descuentoLinea,uv.nombre,m.nombre, coalesce(dc.nombre, dcp.nombre), f.descripcion, fsri.autorizacion, esb.nombreFiscal, esb.nombreComercial, gr.numero, COALESCE(pc.numero, pcd.numero), fsri.claveAcceso ");
/*   95:     */     
/*   96:     */ 
/*   97: 161 */     sql2.append(" FROM ImpuestoProductoFacturaCliente ipfc ");
/*   98: 162 */     sql2.append(" RIGHT OUTER JOIN ipfc.detalleFacturaCliente df ");
/*   99: 163 */     sql2.append(" LEFT JOIN df.unidadVenta uv ");
/*  100: 164 */     sql2.append(" LEFT OUTER JOIN df.producto p ");
/*  101: 165 */     sql2.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/*  102: 166 */     sql2.append(" LEFT OUTER JOIN sp.categoriaProducto cp ");
/*  103: 167 */     sql2.append(" LEFT OUTER JOIN p.unidad u ");
/*  104: 168 */     sql2.append(" LEFT OUTER JOIN df.facturaCliente f ");
/*  105: 169 */     sql2.append(" LEFT OUTER JOIN f.subempresa sbe ");
/*  106: 170 */     sql2.append(" LEFT OUTER JOIN sbe.empresa esb ");
/*  107: 171 */     sql2.append(" LEFT OUTER JOIN f.facturaClienteSRI fsri ");
/*  108: 172 */     sql2.append(" LEFT OUTER JOIN f.proyecto dc ");
/*  109: 173 */     sql2.append(" LEFT OUTER JOIN f.sucursal s ");
/*  110: 174 */     sql2.append(" LEFT OUTER JOIN f.documento d ");
/*  111: 175 */     sql2.append(" LEFT OUTER JOIN f.motivoNotaCreditoCliente m ");
/*  112: 176 */     sql2.append(" LEFT OUTER JOIN f.agenteComercial ac ");
/*  113: 177 */     sql2.append(" LEFT OUTER JOIN f.zona z ");
/*  114: 178 */     sql2.append(" LEFT OUTER JOIN f.canal c ");
/*  115: 179 */     sql2.append(" LEFT OUTER JOIN f.empresa e ");
/*  116: 180 */     sql2.append(" LEFT OUTER JOIN e.cliente cl ");
/*  117: 181 */     sql2.append(" LEFT OUTER JOIN f.facturaClientePadre fp");
/*  118: 182 */     sql2.append(" LEFT OUTER JOIN fp.proyecto dcp");
/*  119: 183 */     sql2.append(" LEFT OUTER JOIN df.detalleDespachoCliente ddc");
/*  120: 184 */     sql2.append(" LEFT OUTER JOIN ddc.despachoCliente dcl");
/*  121: 185 */     sql2.append(" LEFT OUTER JOIN dcl.guiaRemision gr");
/*  122: 186 */     sql2.append(" LEFT OUTER JOIN df.detallePedidoCliente dpc");
/*  123: 187 */     sql2.append(" LEFT OUTER JOIN dpc.pedidoCliente pc");
/*  124: 188 */     sql2.append(" LEFT OUTER JOIN ddc.detallePedidoCliente dpcd");
/*  125: 189 */     sql2.append(" LEFT OUTER JOIN dpcd.pedidoCliente pcd");
/*  126:     */     
/*  127: 191 */     sql2.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  128: 192 */     if (sucursal != null) {
/*  129: 193 */       sql2.append(" AND s.idSucursal = :idSucursal ");
/*  130:     */     }
/*  131: 195 */     if (puntoVenta != null) {
/*  132: 196 */       sql2.append(" AND f.numero LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/*  133:     */     }
/*  134: 199 */     if (proyecto != null) {
/*  135: 200 */       sql2.append(" AND dc.idDimensionContable = :idDimensionContable OR dcp.idDimensionContable = :idDimensionContable ");
/*  136:     */     }
/*  137: 202 */     sql2.append(" AND (e.idEmpresa = :idCliente OR :idCliente=0) ");
/*  138: 203 */     sql2.append(" AND (ac.idUsuario = :idVendedor OR :idVendedor=0) ");
/*  139: 204 */     sql2.append(" AND (z.idZona = :idZona OR :idZona=0) ");
/*  140: 205 */     sql2.append(" AND (c.idCanal = :idCanal OR :idCanal=0) ");
/*  141: 206 */     sql2.append(" AND (p.idProducto = :idProducto OR :idProducto=0) ");
/*  142: 207 */     sql2.append(" AND (sp.idSubcategoriaProducto = :idSubcategoriaProducto OR :idSubcategoriaProducto=0) ");
/*  143: 208 */     sql2.append(" AND (cp.idCategoriaProducto = :idCategoriaProducto OR :idCategoriaProducto=0) ");
/*  144: 209 */     sql2.append(" AND f.indicadorSaldoInicial = :saldoInicial ");
/*  145: 210 */     sql2.append(" AND f.idOrganizacion = :idOrganizacion ");
/*  146: 211 */     sql2.append(" AND (m.idMotivoNotaCreditoCliente = :idMotivoNotaCreditoCliente OR :idMotivoNotaCreditoCliente=0 ) ");
/*  147: 214 */     if (documentoBase != null) {
/*  148: 215 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/*  149: 216 */         sql2.append(" AND d.documentoBase IN (:documentoBase,:documentoDevolucion) ");
/*  150:     */       } else {
/*  151: 218 */         sql2.append(" AND d.documentoBase = :documentoBase ");
/*  152:     */       }
/*  153:     */     }
/*  154: 222 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0)) {
/*  155: 223 */       sql2.append(" AND f.numero >= :numeroDesde AND f.numero <= :numeroHasta ");
/*  156:     */     }
/*  157: 226 */     if (anuladas) {
/*  158: 227 */       sql2.append(" AND f.estado = :estadoAnulado ");
/*  159:     */     } else {
/*  160: 229 */       sql2.append(" AND f.estado != :estadoAnulado ");
/*  161:     */     }
/*  162: 232 */     if (!tipoVenta.equals(TipoVentaEnum.TODOS)) {
/*  163: 233 */       sql2.append(" AND f.documento.indicadorDocumentoExterior = :tipoVenta ");
/*  164:     */     }
/*  165: 236 */     if (atributo != null)
/*  166:     */     {
/*  167: 237 */       sql2.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND p = pr");
/*  168: 239 */       if (!valorAtributo.isEmpty()) {
/*  169: 240 */         sql2.append(" AND pa.valor = :valorAtributo ");
/*  170:     */       }
/*  171: 242 */       sql2.append(" )");
/*  172:     */     }
/*  173: 252 */     if (indicadorTipoReporte) {
/*  174: 254 */       sql2.append(" ORDER BY d.nombre, p.nombre ");
/*  175:     */     } else {
/*  176: 256 */       sql2.append(" ORDER BY f.numero ");
/*  177:     */     }
/*  178: 259 */     Query query = this.em.createQuery(sql2.toString());
/*  179: 260 */     query.setParameter("fechaDesde", fechaDesde);
/*  180: 261 */     query.setParameter("fechaHasta", fechaHasta);
/*  181: 262 */     query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/*  182: 263 */     if (sucursal != null) {
/*  183: 264 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/*  184:     */     }
/*  185: 266 */     if (puntoVenta != null) {
/*  186: 267 */       query.setParameter("codigoPuntoVenta", puntoVenta.getCodigo().trim());
/*  187:     */     }
/*  188: 269 */     if (proyecto != null) {
/*  189: 270 */       query.setParameter("idDimensionContable", Integer.valueOf(proyecto.getId()));
/*  190:     */     }
/*  191: 272 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  192: 273 */     query.setParameter("idZona", Integer.valueOf(idZona));
/*  193: 274 */     query.setParameter("idCanal", Integer.valueOf(idCanal));
/*  194: 275 */     query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto == null ? 0 : subcategoriaProducto.getIdSubcategoriaProducto()));
/*  195: 276 */     query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto == null ? 0 : categoriaProducto.getIdCategoriaProducto()));
/*  196: 277 */     query.setParameter("idProducto", Integer.valueOf(producto == null ? 0 : producto.getIdProducto()));
/*  197: 278 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/*  198: 279 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  199: 280 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  200: 281 */     query.setParameter("devolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
/*  201: 283 */     if (atributo != null)
/*  202:     */     {
/*  203: 284 */       query.setParameter("atributo", atributo);
/*  204: 285 */       if (!valorAtributo.isEmpty()) {
/*  205: 286 */         query.setParameter("valorAtributo", valorAtributo);
/*  206:     */       }
/*  207:     */     }
/*  208: 290 */     if (documentoBase != null)
/*  209:     */     {
/*  210: 291 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/*  211: 292 */         query.setParameter("documentoDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
/*  212:     */       }
/*  213: 294 */       query.setParameter("documentoBase", documentoBase);
/*  214:     */     }
/*  215: 296 */     query.setParameter("idMotivoNotaCreditoCliente", Integer.valueOf(idMotivoNotaCreditoCliente));
/*  216: 298 */     if (!tipoVenta.equals(TipoVentaEnum.TODOS))
/*  217:     */     {
/*  218: 299 */       query.setParameter("tipoVenta", Boolean.valueOf(false));
/*  219: 300 */       if (tipoVenta.equals(TipoVentaEnum.EXTERIOR)) {
/*  220: 301 */         query.setParameter("tipoVenta", Boolean.valueOf(true));
/*  221:     */       }
/*  222:     */     }
/*  223: 304 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0))
/*  224:     */     {
/*  225: 305 */       query.setParameter("numeroDesde", numeroDesde);
/*  226: 306 */       query.setParameter("numeroHasta", numeroHasta);
/*  227:     */     }
/*  228: 309 */     return query.getResultList();
/*  229:     */   }
/*  230:     */   
/*  231:     */   public List getListaReporteFacturacionResumido(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, Sucursal sucursal, PuntoDeVenta puntoVenta, TipoVentaEnum tipoVenta, boolean saldoInicial, boolean indicadorTipoReporte, int idOrganizacion, DocumentoBase documentoBase, int idMotivoNotaCreditoCliente, DimensionContable proyecto)
/*  232:     */   {
/*  233: 335 */     StringBuilder sql2 = new StringBuilder();
/*  234: 336 */     sql2.append(" SELECT s.codigo, s.nombre, c.codigo, c.nombre, z.nombre, z.nombre, ac.codigo, ac.nombre1, ");
/*  235: 337 */     sql2.append(" f.numero, f.fecha, e.nombreFiscal, e.identificacion,df.recargo, df.valor, f.total, f.descuento, f.impuesto,  ");
/*  236: 338 */     sql2.append(" f.idFacturaCliente, e.nombreComercial, fp.numero, ");
/*  237: 339 */     sql2.append(" fs.autorizacion, f.fechaCreacion, case when f.estado = :estadoAnulado THEN 'ANULADO' else 'GENERADO' END ,  ");
/*  238: 340 */     sql2.append(" coalesce(f.usuarioModificacion,''), f.fechaModificacion, coalesce(ma.nombre,''), ");
/*  239: 341 */     sql2.append(" e.identificacion, f.usuarioCreacion,d.codigo, m.nombre, d.nombre, ");
/*  240: 342 */     sql2.append(" CASE WHEN d.documentoBase=:devolucionCliente THEN -1 ELSE d.operacion END,dfc.cantidad,dfc.peso,uv.nombre,");
/*  241: 343 */     sql2.append(" m.nombre, coalesce(dc.nombre, dcp.nombre), f.descripcion, fsri.autorizacion, coalesce(fsri.montoIva,0) ,");
/*  242: 344 */     sql2.append(" esb.nombreFiscal,esb.nombreComercial,fsri.claveAcceso ");
/*  243: 345 */     sql2.append(" FROM FacturaCliente f ");
/*  244: 346 */     sql2.append(" INNER JOIN f.sucursal s ");
/*  245: 347 */     sql2.append(" INNER JOIN f.empresa e ");
/*  246: 348 */     sql2.append(" INNER JOIN f.documento d ");
/*  247: 349 */     sql2.append(" LEFT OUTER JOIN f.facturaClienteSRI fsri ");
/*  248: 350 */     sql2.append(" LEFT JOIN f.subempresa sbe ");
/*  249: 351 */     sql2.append(" LEFT JOIN sbe.empresa esb ");
/*  250: 352 */     sql2.append(" LEFT JOIN f.proyecto dc ");
/*  251: 353 */     sql2.append(" LEFT JOIN f.motivoNotaCreditoCliente m ");
/*  252: 354 */     sql2.append(" LEFT JOIN f.zona z ");
/*  253: 355 */     sql2.append(" LEFT JOIN f.canal c ");
/*  254: 356 */     sql2.append(" LEFT JOIN f.agenteComercial ac ");
/*  255: 357 */     sql2.append(" LEFT JOIN f.facturaClientePadre fp");
/*  256: 358 */     sql2.append(" LEFT JOIN fp.proyecto dcp");
/*  257: 359 */     sql2.append(" LEFT JOIN f.facturaClienteSRI fs");
/*  258: 360 */     sql2.append(" LEFT JOIN f.motivoAnulacion ma");
/*  259: 361 */     sql2.append(" LEFT JOIN f.listaDetalleFacturaCliente dfc ");
/*  260: 362 */     sql2.append(" LEFT JOIN dfc.unidadVenta uv ");
/*  261: 363 */     sql2.append(" LEFT JOIN f.listaDetalleFacturaClienteComercializadora df ");
/*  262: 364 */     sql2.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  263: 365 */     if (sucursal != null) {
/*  264: 366 */       sql2.append(" AND s.idSucursal = :idSucursal ");
/*  265:     */     }
/*  266: 368 */     if (puntoVenta != null) {
/*  267: 369 */       sql2.append(" AND f.numero LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/*  268:     */     }
/*  269: 372 */     if (proyecto != null) {
/*  270: 373 */       sql2.append(" AND dc.idDimensionContable = :idDimensionContable OR dcp.idDimensionContable = :idDimensionContable ");
/*  271:     */     }
/*  272: 375 */     sql2.append(" AND (e.idEmpresa = :idCliente OR :idCliente=0) ");
/*  273: 376 */     sql2.append(" AND (ac.idUsuario = :idVendedor OR :idVendedor=0) ");
/*  274: 377 */     sql2.append(" AND (z.idZona = :idZona OR :idZona=0) ");
/*  275: 378 */     sql2.append(" AND (c.idCanal = :idCanal OR :idCanal=0) ");
/*  276: 379 */     sql2.append(" AND f.indicadorSaldoInicial = :saldoInicial ");
/*  277: 380 */     sql2.append(" AND f.idOrganizacion = :idOrganizacion ");
/*  278: 381 */     sql2.append(" AND (m.idMotivoNotaCreditoCliente = :idMotivoNotaCreditoCliente OR :idMotivoNotaCreditoCliente=0 ) ");
/*  279: 383 */     if (documentoBase != null) {
/*  280: 384 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/*  281: 385 */         sql2.append(" AND d.documentoBase IN (:documentoBase,:documentoDevolucion) ");
/*  282:     */       } else {
/*  283: 387 */         sql2.append(" AND d.documentoBase = :documentoBase ");
/*  284:     */       }
/*  285:     */     }
/*  286: 391 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0)) {
/*  287: 392 */       sql2.append(" AND f.numero >= :numeroDesde AND f.numero <= :numeroHasta ");
/*  288:     */     }
/*  289: 395 */     if (anuladas) {
/*  290: 396 */       sql2.append(" AND f.estado = :estadoAnulado ");
/*  291:     */     } else {
/*  292: 398 */       sql2.append(" AND f.estado != :estadoAnulado ");
/*  293:     */     }
/*  294: 401 */     if (!tipoVenta.equals(TipoVentaEnum.TODOS)) {
/*  295: 402 */       sql2.append(" AND f.documento.indicadorDocumentoExterior = :tipoVenta ");
/*  296:     */     }
/*  297: 406 */     sql2.append(" ORDER BY d.nombre, f.numero  ");
/*  298:     */     
/*  299: 408 */     Query query = this.em.createQuery(sql2.toString());
/*  300: 409 */     query.setParameter("fechaDesde", fechaDesde);
/*  301: 410 */     query.setParameter("fechaHasta", fechaHasta);
/*  302: 411 */     query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/*  303: 412 */     if (sucursal != null) {
/*  304: 413 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/*  305:     */     }
/*  306: 415 */     if (puntoVenta != null) {
/*  307: 416 */       query.setParameter("codigoPuntoVenta", puntoVenta.getCodigo().trim());
/*  308:     */     }
/*  309: 418 */     if (proyecto != null) {
/*  310: 419 */       query.setParameter("idDimensionContable", Integer.valueOf(proyecto.getId()));
/*  311:     */     }
/*  312: 421 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  313: 422 */     query.setParameter("idZona", Integer.valueOf(idZona));
/*  314: 423 */     query.setParameter("idCanal", Integer.valueOf(idCanal));
/*  315: 424 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/*  316: 425 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  317: 426 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  318: 427 */     query.setParameter("devolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
/*  319: 428 */     query.setParameter("idMotivoNotaCreditoCliente", Integer.valueOf(idMotivoNotaCreditoCliente));
/*  320: 429 */     if (documentoBase != null)
/*  321:     */     {
/*  322: 430 */       query.setParameter("documentoBase", documentoBase);
/*  323: 432 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/*  324: 433 */         query.setParameter("documentoDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
/*  325:     */       }
/*  326:     */     }
/*  327: 436 */     if (!tipoVenta.equals(TipoVentaEnum.TODOS))
/*  328:     */     {
/*  329: 437 */       query.setParameter("tipoVenta", Boolean.valueOf(false));
/*  330: 438 */       if (tipoVenta.equals(TipoVentaEnum.EXTERIOR)) {
/*  331: 439 */         query.setParameter("tipoVenta", Boolean.valueOf(true));
/*  332:     */       }
/*  333:     */     }
/*  334: 442 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0))
/*  335:     */     {
/*  336: 443 */       query.setParameter("numeroDesde", numeroDesde);
/*  337: 444 */       query.setParameter("numeroHasta", numeroHasta);
/*  338:     */     }
/*  339: 447 */     return query.getResultList();
/*  340:     */   }
/*  341:     */   
/*  342:     */   public List getListaReporteVentasCombustible(Date fechaDesde, Date fechaHasta, int idCliente, boolean anuladas, int idCanal, int idZona, int idCategoriaProducto, int idOrganizacion)
/*  343:     */   {
/*  344: 470 */     StringBuilder sql2 = new StringBuilder();
/*  345: 471 */     sql2.append(" SELECT e.codigo,e.identificacion,e.nombreFiscal, e.nombreComercial, p.nombre, f.fecha, ts.nombre, f.numero, fs.autorizacion, ");
/*  346: 472 */     sql2.append(" dfc.cantidad, dfc.precio, df.recargo, df.valor, f.idFacturaCliente, fs.baseImponibleTarifaCero, fs.baseImponibleDiferenteCero, fs.montoIva");
/*  347:     */     
/*  348: 474 */     sql2.append(" FROM DetalleFacturaClienteComercializadora df ");
/*  349: 475 */     sql2.append(" RIGHT OUTER JOIN df.facturaCliente f ");
/*  350: 476 */     sql2.append(" LEFT JOIN f.facturaClienteSRI fs ");
/*  351: 477 */     sql2.append(" LEFT JOIN fs.tipoComprobanteSRI ts ");
/*  352: 478 */     sql2.append(" LEFT OUTER JOIN f.empresa e ");
/*  353: 479 */     sql2.append(" LEFT OUTER JOIN f.documento d ");
/*  354: 480 */     sql2.append(" LEFT OUTER JOIN f.zona z ");
/*  355: 481 */     sql2.append(" LEFT OUTER JOIN f.canal c, ");
/*  356: 482 */     sql2.append(" DetalleFacturaCliente dfc JOIN dfc.producto p");
/*  357: 483 */     sql2.append(" JOIN p.subcategoriaProducto s ");
/*  358: 484 */     sql2.append(" JOIN s.categoriaProducto ca ");
/*  359: 485 */     sql2.append(" WHERE dfc.facturaCliente = f AND f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  360: 486 */     sql2.append(" AND (e.idEmpresa = :idCliente OR :idCliente=0) ");
/*  361: 487 */     sql2.append(" AND (z.idZona = :idZona OR :idZona=0) ");
/*  362: 488 */     sql2.append(" AND (ca.idCategoriaProducto = :idCategoriaProducto OR :idCategoriaProducto=0) ");
/*  363: 489 */     sql2.append(" AND (c.idCanal = :idCanal OR :idCanal=0) ");
/*  364: 490 */     sql2.append(" AND f.idOrganizacion = :idOrganizacion ");
/*  365: 491 */     sql2.append(" AND d.documentoBase = :documentoBase ");
/*  366: 494 */     if (anuladas) {
/*  367: 495 */       sql2.append(" AND f.estado = :estadoAnulado ");
/*  368:     */     } else {
/*  369: 497 */       sql2.append(" AND f.estado != :estadoAnulado ");
/*  370:     */     }
/*  371: 502 */     sql2.append(" ORDER BY f.numero ");
/*  372:     */     
/*  373: 504 */     Query query = this.em.createQuery(sql2.toString());
/*  374: 505 */     query.setParameter("fechaDesde", fechaDesde);
/*  375: 506 */     query.setParameter("fechaHasta", fechaHasta);
/*  376: 507 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  377: 508 */     query.setParameter("idZona", Integer.valueOf(idZona));
/*  378: 509 */     query.setParameter("idCategoriaProducto", Integer.valueOf(idCategoriaProducto));
/*  379: 510 */     query.setParameter("idCanal", Integer.valueOf(idCanal));
/*  380: 511 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  381: 512 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  382: 513 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_CLIENTE);
/*  383:     */     
/*  384: 515 */     Map<Integer, Object[]> mapaFacturas = new HashMap();
/*  385:     */     
/*  386:     */ 
/*  387: 518 */     List<Object[]> lista = query.getResultList();
/*  388: 520 */     for (Object[] objeto : lista)
/*  389:     */     {
/*  390: 521 */       if (!mapaFacturas.containsKey(Integer.valueOf(Integer.parseInt(objeto[13].toString())))) {
/*  391: 522 */         mapaFacturas.put(Integer.valueOf(Integer.parseInt(objeto[13].toString())), objeto);
/*  392:     */       }
/*  393: 524 */       objeto[11] = ("3" + objeto[11]);
/*  394:     */     }
/*  395: 526 */     for (Object[] obj : mapaFacturas.values())
/*  396:     */     {
/*  397: 527 */       Object[] objeto = new Object[13];
/*  398: 528 */       objeto = (Object[])obj.clone();
/*  399: 529 */       objeto[11] = "0BASE 0";
/*  400: 530 */       objeto[12] = ((BigDecimal)obj[14]);
/*  401: 531 */       lista.add(objeto);
/*  402: 532 */       objeto = (Object[])obj.clone();
/*  403: 533 */       objeto[11] = "1BASE12";
/*  404: 534 */       objeto[12] = ((BigDecimal)obj[15]);
/*  405: 535 */       lista.add(objeto);
/*  406: 536 */       objeto = (Object[])obj.clone();
/*  407: 537 */       objeto[11] = "2IMPUESTOS";
/*  408: 538 */       objeto[12] = ((BigDecimal)obj[16]);
/*  409: 539 */       lista.add(objeto);
/*  410:     */     }
/*  411: 541 */     return lista;
/*  412:     */   }
/*  413:     */   
/*  414:     */   public List getListaReporteVentaProductoDetallado(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, Sucursal sucursal, PuntoDeVenta puntoVenta, TipoVentaEnum tipoVenta, boolean saldoInicial, int idOrganizacion, DocumentoBase documentoBase, int idMotivoNotaCreditoCliente, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Atributo atributo, String valorAtributo, DimensionContable proyecto)
/*  415:     */   {
/*  416: 568 */     StringBuilder sql2 = new StringBuilder();
/*  417: 569 */     sql2.append(" SELECT s.codigo, s.nombre, c.codigo, c.nombre, z.codigo, z.nombre, ac.codigo, ac.nombre1, ");
/*  418:     */     
/*  419: 571 */     sql2.append(" f.numero, f.fecha, e.nombreFiscal, e.identificacion, df.precioLinea-df.descuentoLinea, ");
/*  420:     */     
/*  421: 573 */     sql2.append(" df.descuentoLinea , ((df.precioLinea - df.descuentoLinea)*coalesce(ipfc.porcentajeImpuesto,0)/100.00), ");
/*  422: 574 */     sql2.append(" p.codigo, p.codigoComercial, p.nombre, u.codigo, df.cantidad, df.precio, df.descripcion, ");
/*  423: 575 */     sql2.append(" df.descuento, YEAR(f.fecha), MONTH(f.fecha), fp.numero, d.nombre, CASE WHEN d.documentoBase=:devolucionCliente THEN -1 ELSE d.operacion END, ");
/*  424:     */     
/*  425: 577 */     sql2.append(" df.precioLinea, df.descuentoLinea,df.cantidad,df.peso,uv.nombre, m.nombre, coalesce(dc.nombre, dcp.nombre), f.descripcion, fsri.autorizacion");
/*  426:     */     
/*  427: 579 */     sql2.append(" FROM ImpuestoProductoFacturaCliente ipfc ");
/*  428: 580 */     sql2.append(" RIGHT OUTER JOIN ipfc.detalleFacturaCliente df ");
/*  429: 581 */     sql2.append(" LEFT JOIN df.unidadVenta uv ");
/*  430: 582 */     sql2.append(" LEFT OUTER JOIN df.facturaCliente f ");
/*  431: 583 */     sql2.append(" LEFT OUTER JOIN f.facturaClienteSRI fsri ");
/*  432: 584 */     sql2.append(" LEFT OUTER JOIN f.proyecto dc ");
/*  433: 585 */     sql2.append(" LEFT OUTER JOIN f.sucursal s ");
/*  434: 586 */     sql2.append(" LEFT OUTER JOIN f.empresa e ");
/*  435: 587 */     sql2.append(" LEFT OUTER JOIN f.documento d ");
/*  436: 588 */     sql2.append(" LEFT OUTER JOIN f.motivoNotaCreditoCliente m ");
/*  437: 589 */     sql2.append(" LEFT OUTER JOIN e.cliente cl ");
/*  438: 590 */     sql2.append(" LEFT OUTER JOIN f.zona z ");
/*  439: 591 */     sql2.append(" LEFT OUTER JOIN f.canal c ");
/*  440: 592 */     sql2.append(" LEFT OUTER JOIN f.agenteComercial ac ");
/*  441: 593 */     sql2.append(" LEFT OUTER JOIN df.producto p ");
/*  442: 594 */     sql2.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/*  443: 595 */     sql2.append(" LEFT OUTER JOIN sp.categoriaProducto cp ");
/*  444: 596 */     sql2.append(" LEFT OUTER JOIN p.unidad u ");
/*  445: 597 */     sql2.append(" LEFT OUTER JOIN f.facturaClientePadre fp");
/*  446: 598 */     sql2.append(" LEFT OUTER JOIN fp.proyecto dcp");
/*  447: 599 */     sql2.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  448: 600 */     if (sucursal != null) {
/*  449: 601 */       sql2.append(" AND s.idSucursal = :idSucursal  ");
/*  450:     */     }
/*  451: 603 */     sql2.append(" AND (e.idEmpresa = :idCliente OR :idCliente=0) ");
/*  452: 604 */     sql2.append(" AND (ac.idUsuario = :idVendedor OR :idVendedor=0) ");
/*  453: 605 */     sql2.append(" AND (z.idZona = :idZona OR :idZona=0) ");
/*  454: 606 */     sql2.append(" AND (c.idCanal = :idCanal OR :idCanal=0) ");
/*  455: 607 */     sql2.append(" AND (p.idProducto = :idProducto OR :idProducto=0) ");
/*  456: 608 */     sql2.append(" AND (sp.idSubcategoriaProducto = :idSubcategoriaProducto OR :idSubcategoriaProducto=0) ");
/*  457: 609 */     sql2.append(" AND (cp.idCategoriaProducto = :idCategoriaProducto OR :idCategoriaProducto=0) ");
/*  458: 610 */     sql2.append(" AND f.indicadorSaldoInicial = :saldoInicial ");
/*  459:     */     
/*  460: 612 */     sql2.append(" AND ((df.precioLinea - df.descuentoLinea)) != 0 ");
/*  461: 613 */     sql2.append(" AND f.idOrganizacion=:idOrganizacion ");
/*  462: 614 */     if (puntoVenta != null) {
/*  463: 615 */       sql2.append(" AND f.numero LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/*  464:     */     }
/*  465: 617 */     sql2.append(" AND (m.idMotivoNotaCreditoCliente = :idMotivoNotaCreditoCliente OR :idMotivoNotaCreditoCliente=0 ) ");
/*  466: 620 */     if (proyecto != null) {
/*  467: 621 */       sql2.append(" AND dc.idDimensionContable = :idDimensionContable OR dcp.idDimensionContable = :idDimensionContable ");
/*  468:     */     }
/*  469: 625 */     if (documentoBase != null) {
/*  470: 626 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/*  471: 627 */         sql2.append(" AND d.documentoBase IN (:documentoBase,:documentoDevolucion) ");
/*  472:     */       } else {
/*  473: 629 */         sql2.append(" AND d.documentoBase = :documentoBase ");
/*  474:     */       }
/*  475:     */     }
/*  476: 633 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0)) {
/*  477: 634 */       sql2.append(" AND f.numero >= :numeroDesde AND f.numero <= :numeroHasta ");
/*  478:     */     }
/*  479: 637 */     if (anuladas) {
/*  480: 638 */       sql2.append(" AND f.estado = :estadoAnulado ");
/*  481:     */     } else {
/*  482: 640 */       sql2.append(" AND f.estado != :estadoAnulado ");
/*  483:     */     }
/*  484: 643 */     if (!tipoVenta.equals(TipoVentaEnum.TODOS)) {
/*  485: 644 */       sql2.append(" AND f.documento.indicadorDocumentoExterior = :tipoVenta ");
/*  486:     */     }
/*  487: 647 */     if (atributo != null)
/*  488:     */     {
/*  489: 648 */       sql2.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND p = pr");
/*  490: 650 */       if (!valorAtributo.isEmpty()) {
/*  491: 651 */         sql2.append(" AND pa.valor = :valorAtributo ");
/*  492:     */       }
/*  493: 653 */       sql2.append(" )");
/*  494:     */     }
/*  495: 656 */     sql2.append(" ORDER BY p.nombre, YEAR(f.fecha) ASC , MONTH(f.fecha) ASC");
/*  496:     */     
/*  497: 658 */     Query query = this.em.createQuery(sql2.toString());
/*  498: 659 */     query.setParameter("fechaDesde", fechaDesde);
/*  499: 660 */     query.setParameter("fechaHasta", fechaHasta);
/*  500: 661 */     query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/*  501: 662 */     if (sucursal != null) {
/*  502: 663 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/*  503:     */     }
/*  504: 665 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  505: 666 */     query.setParameter("idZona", Integer.valueOf(idZona));
/*  506: 667 */     query.setParameter("idCanal", Integer.valueOf(idCanal));
/*  507: 668 */     query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto == null ? 0 : subcategoriaProducto.getIdSubcategoriaProducto()));
/*  508: 669 */     query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto == null ? 0 : categoriaProducto.getIdCategoriaProducto()));
/*  509: 670 */     query.setParameter("idProducto", Integer.valueOf(producto == null ? 0 : producto.getIdProducto()));
/*  510: 671 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/*  511: 672 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  512: 673 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  513: 674 */     query.setParameter("devolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
/*  514: 675 */     if (puntoVenta != null) {
/*  515: 676 */       query.setParameter("codigoPuntoVenta", puntoVenta.getCodigo().trim());
/*  516:     */     }
/*  517: 678 */     if (proyecto != null) {
/*  518: 679 */       query.setParameter("idDimensionContable", Integer.valueOf(proyecto.getId()));
/*  519:     */     }
/*  520: 681 */     if (documentoBase != null)
/*  521:     */     {
/*  522: 682 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/*  523: 683 */         query.setParameter("documentoDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
/*  524:     */       }
/*  525: 685 */       query.setParameter("documentoBase", documentoBase);
/*  526:     */     }
/*  527: 687 */     query.setParameter("idMotivoNotaCreditoCliente", Integer.valueOf(idMotivoNotaCreditoCliente));
/*  528: 688 */     if (atributo != null)
/*  529:     */     {
/*  530: 689 */       query.setParameter("atributo", atributo);
/*  531: 690 */       if (!valorAtributo.isEmpty()) {
/*  532: 691 */         query.setParameter("valorAtributo", valorAtributo);
/*  533:     */       }
/*  534:     */     }
/*  535: 695 */     if (!tipoVenta.equals(TipoVentaEnum.TODOS))
/*  536:     */     {
/*  537: 696 */       query.setParameter("tipoVenta", Boolean.valueOf(false));
/*  538: 697 */       if (tipoVenta.equals(TipoVentaEnum.EXTERIOR)) {
/*  539: 698 */         query.setParameter("tipoVenta", Boolean.valueOf(true));
/*  540:     */       }
/*  541:     */     }
/*  542: 701 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0))
/*  543:     */     {
/*  544: 702 */       query.setParameter("numeroDesde", numeroDesde);
/*  545: 703 */       query.setParameter("numeroHasta", numeroHasta);
/*  546:     */     }
/*  547: 706 */     return query.getResultList();
/*  548:     */   }
/*  549:     */   
/*  550:     */   public List getListaReporteEstadoCuenta(Date fechaDesde, Date fechaHasta, Empresa cliente, Recaudador recaudador, Subempresa subempresa, int idOrganizacion, OrdenamientoEnum orden, boolean saldoDiferenteDeCero, Sucursal sucursal, PuntoDeVenta puntoDeVenta)
/*  551:     */     throws ExcepcionAS2Ventas
/*  552:     */   {
/*  553: 723 */     String ordenString = "v.fechaDocumento";
/*  554: 724 */     if ((orden == OrdenamientoEnum.FACTURA) || (orden == OrdenamientoEnum.SALDO_FACTURA)) {
/*  555: 725 */       ordenString = "v.fechaFactura, v.numeroFactura, v.fechaDocumento, v.credito";
/*  556:     */     }
/*  557: 727 */     if (orden == OrdenamientoEnum.DOCUMENTO) {
/*  558: 728 */       ordenString = "v.fechaDocumento, v.numeroDocumento, v.fechaFactura";
/*  559:     */     }
/*  560: 731 */     StringBuilder sql = new StringBuilder();
/*  561: 732 */     sql.append(" SELECT v.nombreFiscal, v.codigo, v.fechaDocumento, v.numeroFactura, ");
/*  562: 733 */     sql.append(" v.fechaVencimiento, v.descripcionDocumento, v.debito, v.credito, v.nombreDocumento, ");
/*  563: 734 */     sql.append(" v.numeroDocumento,v.indicadorGeneradaProtesto, v.codigoDocumento, v.codigoDocumentoProceso, ");
/*  564: 735 */     sql.append(" v.asientoVenta, v.asientoDocumento, v.referencia1, v.referencia2, v.referencia3, v.referencia4, ");
/*  565: 736 */     sql.append(" v.referencia5, v.referencia6, v.valorReferencia1, v.valorReferencia2, v.valorReferencia3, v.identificacion,");
/*  566: 737 */     sql.append(" see.nombreFiscal, v.fechaFactura, fc.descripcion");
/*  567: 738 */     sql.append(" FROM FacturaCliente fc, VEstadoCuenta v");
/*  568: 739 */     sql.append(" LEFT JOIN fc.subempresa se ");
/*  569: 740 */     sql.append(" LEFT JOIN se.empresa see ");
/*  570: 742 */     if (orden == OrdenamientoEnum.SALDO_FACTURA) {
/*  571: 743 */       sql.append(" WHERE v.fechaFactura BETWEEN :fechaDesde AND :fechaHasta ");
/*  572:     */     } else {
/*  573: 745 */       sql.append(" WHERE v.fechaDocumento BETWEEN :fechaDesde AND :fechaHasta ");
/*  574:     */     }
/*  575: 747 */     sql.append(" AND v.idFacturaCliente = fc.idFacturaCliente");
/*  576: 748 */     sql.append(" AND v.idOrganizacion = :idOrganizacion ");
/*  577: 749 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  578: 750 */       sql.append(" AND v.idSucursal = :idSucursal");
/*  579:     */     }
/*  580: 752 */     if ((null != cliente) && (cliente.getId() != 0)) {
/*  581: 753 */       sql.append(" AND v.idEmpresa = :idCliente ");
/*  582:     */     }
/*  583: 755 */     if ((null != subempresa) && (subempresa.getId() != 0)) {
/*  584: 756 */       sql.append(" AND v.idSubempresa = :idSubempresa ");
/*  585:     */     }
/*  586: 758 */     if ((null != recaudador) && (recaudador.getId() != 0)) {
/*  587: 759 */       sql.append(" AND v.idRecaudador = :idRecaudador ");
/*  588:     */     }
/*  589: 761 */     if ((null != puntoDeVenta) && (puntoDeVenta.getId() != 0)) {
/*  590: 762 */       sql.append(" AND v.numeroFactura LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/*  591:     */     }
/*  592: 764 */     if (saldoDiferenteDeCero)
/*  593:     */     {
/*  594: 765 */       sql.append(" AND EXISTS ");
/*  595: 766 */       sql.append(" ( ");
/*  596: 767 */       sql.append(" \tSELECT ec2.idFacturaCliente  ");
/*  597: 768 */       sql.append(" \tFROM VEstadoCuenta ec2  ");
/*  598: 769 */       sql.append(" \tWHERE ec2.idFacturaCliente=v.idFacturaCliente ");
/*  599: 770 */       if (orden == OrdenamientoEnum.SALDO_FACTURA) {
/*  600: 771 */         sql.append(" AND ec2.fechaFactura<=:fechaHasta ");
/*  601:     */       } else {
/*  602: 773 */         sql.append(" AND ec2.fechaDocumento<=:fechaHasta ");
/*  603:     */       }
/*  604: 775 */       sql.append(" \tGROUP BY ec2.idFacturaCliente HAVING SUM(ec2.debito-ec2.credito) <> 0  ");
/*  605: 776 */       sql.append(" ) ");
/*  606:     */     }
/*  607: 778 */     sql.append(" ORDER BY " + ordenString);
/*  608:     */     
/*  609: 780 */     Query query = this.em.createQuery(sql.toString());
/*  610: 781 */     query.setParameter("fechaDesde", fechaDesde);
/*  611: 782 */     query.setParameter("fechaHasta", fechaHasta);
/*  612: 783 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  613: 784 */     if ((null != cliente) && (cliente.getId() != 0)) {
/*  614: 785 */       query.setParameter("idCliente", Integer.valueOf(cliente.getId()));
/*  615:     */     }
/*  616: 787 */     if ((null != subempresa) && (subempresa.getId() != 0)) {
/*  617: 788 */       query.setParameter("idSubempresa", Integer.valueOf(subempresa.getId()));
/*  618:     */     }
/*  619: 790 */     if ((null != recaudador) && (recaudador.getId() != 0)) {
/*  620: 791 */       query.setParameter("idRecaudador", Integer.valueOf(recaudador.getId()));
/*  621:     */     }
/*  622: 793 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  623: 794 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/*  624:     */     }
/*  625: 796 */     if (puntoDeVenta != null) {
/*  626: 797 */       query.setParameter("codigoPuntoVenta", puntoDeVenta.getCodigo());
/*  627:     */     }
/*  628: 800 */     return query.getResultList();
/*  629:     */   }
/*  630:     */   
/*  631:     */   public List getListaReporteCorteFecha(Date fechaHasta, Empresa cliente, Recaudador recaudador, int idOrganizacion, Subempresa subempresa, EntidadUsuario agenteComercial, Sucursal sucursal, PuntoDeVenta puntoDeVenta, Zona zona, DimensionContable proyecto, CategoriaEmpresa categoriaEmpresa)
/*  632:     */     throws ExcepcionAS2Ventas
/*  633:     */   {
/*  634: 815 */     StringBuilder sql = new StringBuilder();
/*  635:     */     
/*  636: 817 */     sql.append(" SELECT v.codigo, v.identificacion, v.nombreFiscal, v.fechaFactura, v.numeroFactura, sum(v.debito), sum(v.credito), sum(v.debito - v.credito), v.indicadorGeneradaProtesto, v.codigoDocumento, fc.referencia2, coalesce(dc.nombre, ' ') ");
/*  637:     */     
/*  638: 819 */     sql.append(" FROM FacturaCliente fc ");
/*  639: 820 */     sql.append(" LEFT JOIN fc.empresa e ");
/*  640: 821 */     sql.append(" LEFT JOIN e.categoriaEmpresa ce ");
/*  641: 822 */     sql.append(" LEFT OUTER JOIN fc.proyecto dc, VEstadoCuenta v ");
/*  642: 823 */     sql.append(" WHERE v.idFacturaCliente = fc.idFacturaCliente AND v.fechaDocumento <= :fechaHasta ");
/*  643: 824 */     sql.append(" AND v.idOrganizacion = :idOrganizacion ");
/*  644: 825 */     if ((null != cliente) && (cliente.getId() != 0)) {
/*  645: 826 */       sql.append(" AND v.idEmpresa =:idCliente ");
/*  646:     */     }
/*  647: 828 */     if ((null != recaudador) && (recaudador.getId() != 0)) {
/*  648: 829 */       sql.append(" AND v.idRecaudador = :idRecaudador ");
/*  649:     */     }
/*  650: 831 */     if ((null != subempresa) && (subempresa.getId() != 0)) {
/*  651: 832 */       sql.append(" AND v.idSubempresa =:idSubempresa ");
/*  652:     */     }
/*  653: 834 */     if ((null != agenteComercial) && (agenteComercial.getId() != 0)) {
/*  654: 835 */       sql.append(" AND v.idAgenteComercial =:idAgenteComercial ");
/*  655:     */     }
/*  656: 837 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  657: 838 */       sql.append(" AND v.idSucursal = :idSucursal ");
/*  658:     */     }
/*  659: 840 */     if (null != zona) {
/*  660: 841 */       sql.append(" AND v.idZona = :idZona ");
/*  661:     */     }
/*  662: 843 */     if (puntoDeVenta != null) {
/*  663: 844 */       sql.append(" AND v.numeroFactura LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/*  664:     */     }
/*  665: 847 */     if (proyecto != null) {
/*  666: 848 */       sql.append(" AND dc.idDimensionContable = :idDimensionContable ");
/*  667:     */     }
/*  668: 851 */     if (categoriaEmpresa != null) {
/*  669: 852 */       sql.append(" AND ce.idCategoriaEmpresa = :idCategoriaEmpresa ");
/*  670:     */     }
/*  671: 855 */     sql.append(" GROUP BY v.codigo, v.identificacion, v.nombreFiscal, v.fechaFactura, v.numeroFactura, v.indicadorGeneradaProtesto,v.codigoDocumento, fc.referencia2, v.fechaVencimiento, coalesce(dc.nombre, ' ') ");
/*  672:     */     
/*  673: 857 */     sql.append(" HAVING sum(v.debito - v.credito)<>0 ");
/*  674:     */     
/*  675: 859 */     sql.append(" ORDER BY v.nombreFiscal, v.fechaFactura, v.numeroFactura ");
/*  676:     */     
/*  677: 861 */     Query query = this.em.createQuery(sql.toString());
/*  678: 862 */     query.setParameter("fechaHasta", fechaHasta);
/*  679: 863 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  680: 864 */     if ((null != cliente) && (cliente.getId() != 0)) {
/*  681: 865 */       query.setParameter("idCliente", Integer.valueOf(cliente.getId()));
/*  682:     */     }
/*  683: 867 */     if ((null != recaudador) && (recaudador.getId() != 0)) {
/*  684: 868 */       query.setParameter("idRecaudador", Integer.valueOf(recaudador.getId()));
/*  685:     */     }
/*  686: 870 */     if ((null != subempresa) && (subempresa.getId() != 0)) {
/*  687: 871 */       query.setParameter("idSubempresa", Integer.valueOf(subempresa.getId()));
/*  688:     */     }
/*  689: 873 */     if ((null != agenteComercial) && (agenteComercial.getId() != 0)) {
/*  690: 874 */       query.setParameter("idAgenteComercial", Integer.valueOf(agenteComercial.getId()));
/*  691:     */     }
/*  692: 876 */     if ((null != sucursal) && (sucursal.getId() != 0)) {
/*  693: 877 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/*  694:     */     }
/*  695: 879 */     if (null != zona) {
/*  696: 880 */       query.setParameter("idZona", Integer.valueOf(zona.getId()));
/*  697:     */     }
/*  698: 882 */     if ((puntoDeVenta != null) && (puntoDeVenta.getId() != 0)) {
/*  699: 883 */       query.setParameter("codigoPuntoVenta", puntoDeVenta.getCodigo());
/*  700:     */     }
/*  701: 885 */     if (proyecto != null) {
/*  702: 886 */       query.setParameter("idDimensionContable", Integer.valueOf(proyecto.getId()));
/*  703:     */     }
/*  704: 888 */     if (proyecto != null) {
/*  705: 889 */       query.setParameter("idDimensionContable", Integer.valueOf(proyecto.getId()));
/*  706:     */     }
/*  707: 892 */     if (categoriaEmpresa != null) {
/*  708: 893 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getId()));
/*  709:     */     }
/*  710: 896 */     return query.getResultList();
/*  711:     */   }
/*  712:     */   
/*  713:     */   public List getListaReporteVentasMargenDescuento(Date fechaDesde, Date fechaHasta, int idCliente, BigDecimal porcentaje, int idOrganizacion)
/*  714:     */     throws ExcepcionAS2Ventas
/*  715:     */   {
/*  716: 912 */     String sql = "SELECT f.numero, f.fecha, e.nombreFiscal,  ((df.descuento * 100)/df.precio), df.cantidad, p.codigo, p.nombre, p.volumen, p.peso  FROM DetalleFacturaCliente df  INNER JOIN df.facturaCliente f  INNER JOIN df.producto p  INNER JOIN f.empresa e  WHERE df.precio <> 0    AND :porcentaje < ((df.descuento * 100)/df.precio)  AND f.idOrganizacion=:idOrganizacion";
/*  717: 917 */     if (fechaDesde != null)
/*  718:     */     {
/*  719: 918 */       sql = sql + " AND f.fecha >= :fechaDesde ";
/*  720:     */     }
/*  721:     */     else
/*  722:     */     {
/*  723: 920 */       fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/*  724: 921 */       sql = sql + " AND f.fecha >= :fechaDesde ";
/*  725:     */     }
/*  726: 924 */     if (fechaHasta != null)
/*  727:     */     {
/*  728: 925 */       sql = sql + " AND f.fecha <= :fechaHasta ";
/*  729:     */     }
/*  730:     */     else
/*  731:     */     {
/*  732: 927 */       fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/*  733: 928 */       sql = sql + " AND f.fecha <= :fechaHasta ";
/*  734:     */     }
/*  735: 931 */     if (idCliente != 0) {
/*  736: 932 */       sql = sql + " AND e.idEmpresa = :idEmpresa";
/*  737:     */     }
/*  738: 935 */     Query query = this.em.createQuery(sql);
/*  739: 936 */     query.setParameter("fechaDesde", fechaDesde);
/*  740: 937 */     query.setParameter("fechaHasta", fechaHasta);
/*  741: 938 */     query.setParameter("porcentaje", porcentaje);
/*  742: 939 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  743: 941 */     if (idCliente != 0) {
/*  744: 942 */       query = query.setParameter("idEmpresa", Integer.valueOf(idCliente));
/*  745:     */     }
/*  746: 945 */     return query.getResultList();
/*  747:     */   }
/*  748:     */   
/*  749:     */   public BigDecimal obtenerSaldoEstadoCuenta(int idCliente, Date fechaHasta, boolean indicadorFechaDocumento)
/*  750:     */   {
/*  751: 949 */     Date fechaHastaReporte = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -1);
/*  752: 950 */     StringBuilder sql = new StringBuilder();
/*  753: 951 */     sql.append(" SELECT SUM(debito-credito) ");
/*  754: 952 */     sql.append(" FROM VEstadoCuenta e ");
/*  755: 953 */     sql.append(" WHERE e.idEmpresa= :idCliente");
/*  756: 954 */     if (indicadorFechaDocumento) {
/*  757: 955 */       sql.append(" AND e.fechaFactura <= :fechaHasta ");
/*  758:     */     } else {
/*  759: 957 */       sql.append(" AND e.fechaDocumento <= :fechaHasta ");
/*  760:     */     }
/*  761: 960 */     Query query = this.em.createQuery(sql.toString());
/*  762: 961 */     query.setParameter("fechaHasta", fechaHastaReporte);
/*  763: 962 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  764: 963 */     BigDecimal resultado = (BigDecimal)query.getSingleResult();
/*  765: 964 */     if (resultado == null) {
/*  766: 965 */       resultado = BigDecimal.ZERO;
/*  767:     */     }
/*  768: 967 */     return resultado;
/*  769:     */   }
/*  770:     */   
/*  771:     */   public List getReporteNotaDebito(int idNotaDebito)
/*  772:     */   {
/*  773: 984 */     String sql = " SELECT f.numero, f.fecha, cp.nombre, f.descripcion, f.total,  em.nombreComercial, em.identificacion ,CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4)  FROM FacturaCliente f  INNER JOIN f.empresa em  INNER JOIN f.direccionEmpresa de  INNER JOIN de.ubicacion u INNER JOIN f.condicionPago cp WHERE f.idFacturaCliente = :idNotaDebito ";
/*  774:     */     
/*  775:     */ 
/*  776:     */ 
/*  777:     */ 
/*  778: 989 */     Query query = this.em.createQuery(sql).setParameter("idNotaDebito", Integer.valueOf(idNotaDebito));
/*  779: 990 */     return query.getResultList();
/*  780:     */   }
/*  781:     */   
/*  782:     */   public List getReporteFacturaCliente(int idFacturaCliente)
/*  783:     */     throws ExcepcionAS2
/*  784:     */   {
/*  785:1003 */     String sql = "SELECT e.nombreFiscal, CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4), e.identificacion, f.fecha, d.cantidad, pr.codigo, pr.nombreComercial, d.precio, f.total, f.descuento, f.impuesto,  de.telefono1, f.descripcion, YEAR(f.fecha), MONTH(f.fecha), DAY(f.fecha), d.descripcion FROM DetalleFacturaCliente d INNER JOIN d.facturaCliente f  INNER JOIN f.empresa e  INNER JOIN f.direccionEmpresa de  INNER JOIN de.ubicacion u INNER JOIN d.producto pr  INNER JOIN f.condicionPago c  WHERE f.idFacturaCliente = :idFacturaCliente";
/*  786:     */     
/*  787:     */ 
/*  788:     */ 
/*  789:     */ 
/*  790:     */ 
/*  791:1009 */     Query query = this.em.createQuery(sql).setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
/*  792:1010 */     return query.getResultList();
/*  793:     */   }
/*  794:     */   
/*  795:     */   public List getReporteVentasProducto(Date fechaDesde, Date fechaHasta, int idProducto)
/*  796:     */   {
/*  797:1031 */     String sql = " SELECT s.codigo, s.nombre, c.codigo, c.nombre, z.nombre, z.nombre, ac.codigo, ac.nombre1,  f.numero, f.fecha, e.nombreFiscal, e.identificacion, f.total, f.descuento, f.impuesto,  p.codigo, p.codigoComercial, p.nombre, u.codigo,  df.cantidad, df.precio, df.descripcion, df.descuento  FROM DetalleFacturaCliente df  LEFT OUTER JOIN df.facturaCliente f  LEFT OUTER JOIN f.sucursal s  LEFT OUTER JOIN f.empresa e  LEFT OUTER JOIN e.cliente cl  LEFT OUTER JOIN f.zona z  LEFT OUTER JOIN f.canal c  LEFT OUTER JOIN f.agenteComercial ac  LEFT OUTER JOIN df.producto p  LEFT OUTER JOIN p.unidad u  WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta  AND (p.idProducto = :idProducto OR :idProducto=0)  AND f.documento.documentoBase = :documentoBase ORDER BY p.nombre ";
/*  798:     */     
/*  799:     */ 
/*  800:     */ 
/*  801:     */ 
/*  802:     */ 
/*  803:     */ 
/*  804:     */ 
/*  805:     */ 
/*  806:1040 */     Query query = this.em.createQuery(sql);
/*  807:1041 */     query.setParameter("fechaDesde", fechaDesde);
/*  808:1042 */     query.setParameter("fechaHasta", fechaHasta);
/*  809:1043 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  810:1044 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_CLIENTE);
/*  811:     */     
/*  812:1046 */     return query.getResultList();
/*  813:     */   }
/*  814:     */   
/*  815:     */   public List getReporteVentasCaja(Date fechaDesde, Date fechaHasta, int idAgenteComercial, int idFormaPago, int idPuntoDeVenta, int idCaja)
/*  816:     */   {
/*  817:1067 */     String sql = " SELECT s.codigo, s.nombre, c.codigo, c.nombre, z.nombre, z.nombre, ac.codigo, ac.nombre1,  f.numero, f.fecha, e.nombreFiscal, e.identificacion, f.total, f.descuento, f.impuesto,  p.codigo, p.codigoComercial, p.nombre, u.codigo,  df.cantidad, df.precio, df.descripcion, df.descuento  FROM DetalleFacturaCliente df  LEFT OUTER JOIN df.facturaCliente f  LEFT OUTER JOIN f.sucursal s  LEFT OUTER JOIN f.empresa e  LEFT OUTER JOIN e.cliente cl  LEFT OUTER JOIN f.zona z  LEFT OUTER JOIN f.canal c  LEFT OUTER JOIN f.agenteComercial ac  LEFT OUTER JOIN f.formaPago fp  LEFT OUTER JOIN f.documento d  LEFT OUTER JOIN df.producto p  LEFT OUTER JOIN p.unidad u,  AutorizacionDocumentoSRI ad LEFT OUTER JOIN ad.puntoDeVenta pv, Caja ca  WHERE d = ad.documento AND p = ca.puntoDeVenta  AND f.fecha BETWEEN :fechaDesde AND :fechaHasta  AND (ac.idUsuario = :idAgenteComercial OR :idAgenteComercial=0)  AND (fp.idFormaPago = :idFormaPago OR :idFormaPago=0)  AND (pv.idPuntoDeVenta = :idPuntoDeVenta OR :idPuntoDeVenta=0)  AND (ca.idCaja = :idCaja OR :idCaja=0)  AND f.documento.documentoBase = :documentoBase ORDER BY f.numero";
/*  818:     */     
/*  819:     */ 
/*  820:     */ 
/*  821:     */ 
/*  822:     */ 
/*  823:     */ 
/*  824:     */ 
/*  825:     */ 
/*  826:     */ 
/*  827:     */ 
/*  828:     */ 
/*  829:1079 */     Query query = this.em.createQuery(sql);
/*  830:1080 */     query.setParameter("fechaDesde", fechaDesde);
/*  831:1081 */     query.setParameter("fechaHasta", fechaHasta);
/*  832:1082 */     query.setParameter("idAgenteComercial", Integer.valueOf(idAgenteComercial));
/*  833:1083 */     query.setParameter("idFormaPago", Integer.valueOf(idFormaPago));
/*  834:1084 */     query.setParameter("idPuntoDeVenta", Integer.valueOf(idPuntoDeVenta));
/*  835:1085 */     query.setParameter("idCaja", Integer.valueOf(idCaja));
/*  836:1086 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_CLIENTE);
/*  837:     */     
/*  838:1088 */     return query.getResultList();
/*  839:     */   }
/*  840:     */   
/*  841:     */   public List getReporteCorteFechaAnticipoClientes(Date fechaCorte, int idCliente, boolean indicadorSaldoDiferenciaCero)
/*  842:     */     throws ExcepcionAS2
/*  843:     */   {
/*  844:1101 */     StringBuilder sql = new StringBuilder();
/*  845:     */     
/*  846:1103 */     sql.append(" SELECT ac.numero, ac.valor, dlac.valor, e.nombreComercial, e.nombreFiscal,  lac.fecha, fc.numero, ac.saldo, e.identificacion, nc.numero, ac.fecha ");
/*  847:     */     
/*  848:1105 */     sql.append(" FROM DetalleLiquidacionAnticipoCliente dlac ");
/*  849:1106 */     sql.append(" LEFT OUTER JOIN  dlac.liquidacionAnticipoCliente lac ");
/*  850:1107 */     sql.append(" LEFT OUTER JOIN lac.anticipoCliente ac ");
/*  851:1108 */     sql.append(" LEFT OUTER JOIN ac.notaCreditoCliente nc ");
/*  852:1109 */     sql.append(" LEFT OUTER JOIN ac.empresa e ");
/*  853:1110 */     sql.append(" LEFT OUTER JOIN dlac.cuentaPorCobrar cxc ");
/*  854:1111 */     sql.append(" LEFT OUTER JOIN cxc.facturaCliente fc ");
/*  855:1112 */     sql.append(" WHERE lac.fecha <= :fechaCorte ");
/*  856:1113 */     sql.append(" AND lac.estado <> :estadoLiquidacionAnticipo ");
/*  857:1114 */     sql.append(" AND ac.estado <> :estadoAnticipo ");
/*  858:1115 */     if (idCliente != 0) {
/*  859:1116 */       sql.append(" AND e.idEmpresa =:idCliente ");
/*  860:     */     }
/*  861:1118 */     if (indicadorSaldoDiferenciaCero) {
/*  862:1119 */       sql.append(" AND ac.saldo != 0");
/*  863:     */     }
/*  864:1121 */     sql.append(" GROUP BY e.nombreComercial, e.nombreFiscal, e.identificacion, ac.numero, lac.fecha,  ");
/*  865:1122 */     sql.append(" fc.numero, ac.valor, dlac.valor, ac.saldo, nc.numero , ac.fecha");
/*  866:     */     
/*  867:     */ 
/*  868:1125 */     Query query = this.em.createQuery(sql.toString());
/*  869:1126 */     query.setParameter("fechaCorte", fechaCorte);
/*  870:1127 */     query.setParameter("estadoLiquidacionAnticipo", Estado.ANULADO);
/*  871:1128 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/*  872:1129 */     if (idCliente != 0) {
/*  873:1130 */       query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  874:     */     }
/*  875:1133 */     return query.getResultList();
/*  876:     */   }
/*  877:     */   
/*  878:     */   public List getReporteCorteFechaAnticipoClientesSinLiquidacion(Date fechaCorte, int idCliente)
/*  879:     */     throws ExcepcionAS2
/*  880:     */   {
/*  881:1146 */     StringBuilder sql = new StringBuilder();
/*  882:     */     
/*  883:1148 */     sql.append(" SELECT ac.numero, ac.valor, ac.valor * 0, e.nombreComercial, e.nombreFiscal,  ac.fecha, ' ', ac.saldo, e.identificacion, nc.numero, ac.fecha ");
/*  884:     */     
/*  885:1150 */     sql.append(" FROM AnticipoCliente ac ");
/*  886:1151 */     sql.append(" LEFT OUTER JOIN ac.empresa e ");
/*  887:1152 */     sql.append(" LEFT OUTER JOIN ac.notaCreditoCliente nc ");
/*  888:1153 */     sql.append(" WHERE ac.idAnticipoCliente ");
/*  889:1154 */     sql.append(" NOT IN (SELECT lac.anticipoCliente.idAnticipoCliente FROM LiquidacionAnticipoCliente lac WHERE lac.fecha <= :fechaCorte AND lac.estado <> :estadoAnticipo) ");
/*  890:     */     
/*  891:1156 */     sql.append(" AND ac.fecha <= :fechaCorte ");
/*  892:1157 */     sql.append(" AND ac.estado <> :estadoAnticipo ");
/*  893:1158 */     if (idCliente != 0) {
/*  894:1159 */       sql.append(" AND e.idEmpresa =:idCliente ");
/*  895:     */     }
/*  896:1161 */     sql.append(" GROUP BY e.nombreComercial, e.nombreFiscal, e.identificacion, ac.numero, ac.fecha,  ");
/*  897:1162 */     sql.append(" ac.valor, ac.saldo,nc.numero ");
/*  898:     */     
/*  899:     */ 
/*  900:1165 */     Query query = this.em.createQuery(sql.toString());
/*  901:1166 */     query.setParameter("fechaCorte", fechaCorte);
/*  902:1167 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/*  903:1168 */     if (idCliente != 0) {
/*  904:1169 */       query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  905:     */     }
/*  906:1172 */     return query.getResultList();
/*  907:     */   }
/*  908:     */   
/*  909:     */   public List geteReporteVentasPorAtributo(Date fechaDesde, Date fechaHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, int idSucursal, int idAtributo, int idOrganizacion)
/*  910:     */   {
/*  911:1193 */     StringBuilder sql = new StringBuilder();
/*  912:1194 */     sql.append(" SELECT s.codigo, s.nombre, c.codigo, c.nombre, z.nombre, z.nombre, ac.codigo, ac.nombre1, ");
/*  913:1195 */     sql.append(" f.numero, f.fecha, e.nombreFiscal, e.identificacion, f.total, f.descuento, f.impuesto, ");
/*  914:1196 */     sql.append(" p.codigo, p.codigoComercial, p.nombre, u.codigo, ");
/*  915:1197 */     sql.append(" df.cantidad, df.precio, df.descripcion, df.descuento, ");
/*  916:1198 */     sql.append(" case when donc.documentoBase = :documentoBaseDevolucion then coalesce(dnc.cantidad,0) else 0 end, ");
/*  917:1199 */     sql.append(" case when donc.documentoBase = :documentoBaseDevolucion then coalesce(dnc.precio,0) else 0 end, ");
/*  918:1200 */     sql.append(" case when donc.documentoBase = :documentoNotaCredito then coalesce(dnc.precio,0) else 0 end, ");
/*  919:1201 */     sql.append(" (SELECT pa.valor FROM ProductoAtributo pa WHERE pa.producto.idProducto=p.idProducto AND pa.atributo.idAtributo = :idAtributo ) ");
/*  920:     */     
/*  921:1203 */     sql.append(" FROM DetalleFacturaCliente dnc ");
/*  922:1204 */     sql.append(" RIGHT OUTER JOIN dnc.facturaCliente nc ");
/*  923:1205 */     sql.append(" RIGHT OUTER JOIN nc.documento donc ");
/*  924:1206 */     sql.append(" RIGHT OUTER JOIN dnc.detalleFacturaClientePadre df ");
/*  925:1207 */     sql.append(" LEFT OUTER JOIN df.facturaCliente f ");
/*  926:1208 */     sql.append(" LEFT OUTER JOIN f.sucursal s ");
/*  927:1209 */     sql.append(" LEFT OUTER JOIN f.documento d ");
/*  928:1210 */     sql.append(" LEFT OUTER JOIN f.empresa e ");
/*  929:1211 */     sql.append(" LEFT OUTER JOIN e.cliente cl ");
/*  930:1212 */     sql.append(" LEFT OUTER JOIN f.zona z ");
/*  931:1213 */     sql.append(" LEFT OUTER JOIN f.canal c ");
/*  932:1214 */     sql.append(" LEFT OUTER JOIN f.agenteComercial ac ");
/*  933:1215 */     sql.append(" LEFT OUTER JOIN df.producto p ");
/*  934:1216 */     sql.append(" LEFT OUTER JOIN p.unidadVenta u ");
/*  935:1217 */     sql.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  936:1218 */     sql.append(" AND (s.idSucursal = :idSucursal OR :idSucursal=0) ");
/*  937:1219 */     sql.append(" AND (e.idEmpresa = :idCliente OR :idCliente=0) ");
/*  938:1220 */     sql.append(" AND (ac.idUsuario = :idVendedor OR :idVendedor=0) ");
/*  939:1221 */     sql.append(" AND (z.idZona = :idZona OR :idZona=0) ");
/*  940:1222 */     sql.append(" AND (c.idCanal = :idCanal OR :idCanal=0) ");
/*  941:1223 */     sql.append(" AND d.documentoBase = :documentoBase");
/*  942:1224 */     sql.append(" AND (c.idCanal = :idCanal OR :idCanal=0) ");
/*  943:1225 */     sql.append(" AND df.idOrganizacion = :idOrganizacion");
/*  944:1226 */     sql.append(" AND (nc IS NULL OR nc.estado != :estadoAnulado) ");
/*  945:1227 */     if (anuladas) {
/*  946:1228 */       sql.append(" AND f.estado = :estadoAnulado ");
/*  947:     */     } else {
/*  948:1230 */       sql.append(" AND f.estado != :estadoAnulado ");
/*  949:     */     }
/*  950:1233 */     sql.append(" ORDER BY 24,f.numero ");
/*  951:     */     
/*  952:1235 */     Query query = this.em.createQuery(sql.toString());
/*  953:1236 */     query.setParameter("fechaDesde", fechaDesde);
/*  954:1237 */     query.setParameter("fechaHasta", fechaHasta);
/*  955:1238 */     query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/*  956:1239 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  957:1240 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  958:1241 */     query.setParameter("idZona", Integer.valueOf(idZona));
/*  959:1242 */     query.setParameter("idCanal", Integer.valueOf(idCanal));
/*  960:1243 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_CLIENTE);
/*  961:1244 */     query.setParameter("documentoBaseDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
/*  962:1245 */     query.setParameter("documentoNotaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/*  963:1246 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  964:1247 */     query.setParameter("idAtributo", Integer.valueOf(idAtributo));
/*  965:1248 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  966:     */     
/*  967:1250 */     return query.getResultList();
/*  968:     */   }
/*  969:     */   
/*  970:     */   public List<Object[]> getListaReporteEstadoCuenta(int idCliente, String numeroFactura)
/*  971:     */   {
/*  972:1264 */     StringBuffer sql = new StringBuffer();
/*  973:1265 */     sql.append("SELECT v.fechaDocumento, v.numeroDocumento, v.fechaVencimiento, v.numeroFactura, v.debito, v.credito,v.codigoDocumento, v.nombreDocumento,v.codigoDocumentoProceso, v.idCobro, v.idFacturaCliente, v.documentoBase, fc.referencia2 ");
/*  974:     */     
/*  975:     */ 
/*  976:1268 */     sql.append(" FROM FacturaCliente fc, VEstadoCuenta v WHERE v.idFacturaCliente = fc.idFacturaCliente and v.idEmpresa = :idCliente AND v.numeroFactura = :numeroFactura ORDER BY v.fechaDocumento, v.debito desc, v.credito desc");
/*  977:     */     
/*  978:     */ 
/*  979:1271 */     Query query = this.em.createQuery(sql.toString()).setParameter("idCliente", Integer.valueOf(idCliente)).setParameter("numeroFactura", numeroFactura);
/*  980:     */     
/*  981:1273 */     return query.getResultList();
/*  982:     */   }
/*  983:     */   
/*  984:     */   public List<DetalleFormaCobro> getReporteListaRetencionVentas(Date fechaDesde, Date fechaHasta, int idEmpresa, int idOrganizacion, Sucursal sucursal, boolean facturaSinRetencion, PuntoDeVenta puntoVenta)
/*  985:     */   {
/*  986:1279 */     StringBuilder sql = new StringBuilder();
/*  987:1280 */     sql.append(" select em.identificacion, em.nombreComercial, em.nombreFiscal, ");
/*  988:1281 */     sql.append(" fc.fecha, fc.numero, fc.total, fc.impuesto, fc.descuento, ");
/*  989:1282 */     sql.append(" (fc.total - fc.descuento + fc.impuesto) ");
/*  990:1284 */     if (!facturaSinRetencion)
/*  991:     */     {
/*  992:1285 */       sql.append(" ,max(co.numero), max(co.fecha), max(co.descripcion), max(co.valor), ");
/*  993:1286 */       sql.append(" sum(case when coalesce(fp.indicadorRetencionIva,false) = true then dcfc.valor else 0.00 end), ");
/*  994:1287 */       sql.append(" sum(case when coalesce(fp.indicadorRetencionFuente,false) = true then dcfc.valor else 0.00 end), ");
/*  995:1288 */       sql.append(" co.fecha, a.numero, ");
/*  996:1289 */       sql.append(" case when coalesce(fp.indicadorRetencionIva,false) = true then fp.porcentajeRetencion else 0.00 end, ");
/*  997:1290 */       sql.append(" case when coalesce(fp.indicadorRetencionFuente,false) = true then fp.porcentajeRetencion else 0.00 end, dfc.documentoReferencia, dfc.autorizacion");
/*  998:1291 */       sql.append(" from DetalleCobroFormaCobro dcfc ");
/*  999:1292 */       sql.append(" inner join dcfc.detalleCobro dc ");
/* 1000:1293 */       sql.append(" inner join dcfc.detalleFormaCobro dfc ");
/* 1001:1294 */       sql.append(" inner join dfc.formaPago fp ");
/* 1002:1295 */       sql.append(" inner join dc.cuentaPorCobrar cxc ");
/* 1003:1296 */       sql.append(" inner join cxc.facturaCliente fc ");
/* 1004:1297 */       sql.append(" inner join fc.documento doc ");
/* 1005:1298 */       sql.append(" inner join fc.empresa em ");
/* 1006:1299 */       sql.append(" inner join em.cliente cl ");
/* 1007:1300 */       sql.append(" inner join dc.cobro co ");
/* 1008:1301 */       sql.append(" inner join co.asiento a ");
/* 1009:1302 */       sql.append(" where fc.idOrganizacion = :idOrganizacion ");
/* 1010:1303 */       sql.append(" and (em.idEmpresa = :idEmpresa or :idEmpresa = 0 ) ");
/* 1011:1304 */       if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 1012:1305 */         sql.append(" and fc.sucursal.idSucursal = :idSucursal ");
/* 1013:     */       }
/* 1014:1307 */       if (null != puntoVenta) {
/* 1015:1308 */         sql.append(" and fc.numero like concat('%-', :codigoPuntoVenta, '-%') ");
/* 1016:     */       }
/* 1017:1310 */       sql.append(" and fc.estado != :estadoAnulado ");
/* 1018:1311 */       sql.append(" and co.estado != :estadoAnulado ");
/* 1019:1312 */       sql.append(" and doc.documentoBase = :documentoBase ");
/* 1020:1313 */       sql.append(" and co.fecha between :fechaDesde and :fechaHasta ");
/* 1021:1314 */       sql.append(" and (fp.indicadorRetencionIva = true or fp.indicadorRetencionFuente = true) ");
/* 1022:1315 */       sql.append(" group by em.identificacion, em.nombreComercial, em.nombreFiscal, ");
/* 1023:1316 */       sql.append(" fc.fecha, fc.numero, fc.total, fc.impuesto, fc.descuento, co.fecha, a.numero,");
/* 1024:1317 */       sql.append(" fp.porcentajeRetencion, fp.indicadorRetencionIva, fp.indicadorRetencionFuente, dfc.documentoReferencia, dfc.autorizacion");
/* 1025:     */     }
/* 1026:     */     else
/* 1027:     */     {
/* 1028:1319 */       sql.append(" from FacturaCliente as fc ");
/* 1029:1320 */       sql.append(" inner join fc.documento doc ");
/* 1030:1321 */       sql.append(" inner join fc.empresa em ");
/* 1031:1322 */       sql.append(" inner join em.cliente cl ");
/* 1032:1323 */       sql.append(" where fc.idOrganizacion = :idOrganizacion ");
/* 1033:1324 */       sql.append(" and (em.idEmpresa = :idEmpresa or :idEmpresa = 0 ) ");
/* 1034:1325 */       if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 1035:1326 */         sql.append(" and fc.sucursal.idSucursal = :idSucursal ");
/* 1036:     */       }
/* 1037:1328 */       if (null != puntoVenta) {
/* 1038:1329 */         sql.append(" and fc.numero like concat('%-', :codigoPuntoVenta, '-%') ");
/* 1039:     */       }
/* 1040:1331 */       sql.append(" and fc.estado != :estadoAnulado ");
/* 1041:1332 */       sql.append(" and doc.documentoBase = :documentoBase ");
/* 1042:1333 */       sql.append(" and (cl.indicadorEmiteRetencion = true) ");
/* 1043:1334 */       sql.append(" and fc.fecha between :fechaDesde and :fechaHasta ");
/* 1044:1335 */       sql.append(" and  ");
/* 1045:1336 */       sql.append(" \tNOT EXISTS  ");
/* 1046:1337 */       sql.append(" \t( ");
/* 1047:1338 */       sql.append(" \t\tSELECT 1  ");
/* 1048:1339 */       sql.append(" \t\tFROM CuentaPorCobrar cxc  ");
/* 1049:1340 */       sql.append(" \t\tJOIN cxc.listaDetalleCobro dc  ");
/* 1050:1341 */       sql.append(" \t\tjoin dc.cobro co join co.listaDetalleFormaCobro dfc  ");
/* 1051:1342 */       sql.append(" \t\tjoin dfc.listaDetalleCobroFormaCobro dcfc  ");
/* 1052:1343 */       sql.append(" \t\tjoin dfc.formaPago fp ");
/* 1053:1344 */       sql.append(" \t\tWHERE fc = cxc.facturaCliente and co.estado != :estadoAnulado  ");
/* 1054:1345 */       sql.append(" \t\tand (fp.indicadorRetencionIva = true or fp.indicadorRetencionFuente = true) ");
/* 1055:1346 */       sql.append(" \t) and ");
/* 1056:1347 */       sql.append(" \tNOT EXISTS  ");
/* 1057:1348 */       sql.append(" \t( ");
/* 1058:1349 */       sql.append(" \t\tSELECT 1 FROM CuentaPorCobrar cxc ");
/* 1059:1350 */       sql.append(" \t\tWHERE cxc.facturaCliente = fc ");
/* 1060:1351 */       sql.append(" \t\tGROUP BY cxc.facturaCliente ");
/* 1061:1352 */       sql.append(" \t\tHAVING SUM(saldo-valorBloqueado) <= 0 ");
/* 1062:1353 */       sql.append(" \t)");
/* 1063:1354 */       sql.append(" group by em.identificacion, em.nombreComercial, em.nombreFiscal, ");
/* 1064:1355 */       sql.append(" fc.fecha, fc.numero, fc.total, fc.impuesto, fc.descuento ");
/* 1065:     */     }
/* 1066:1357 */     sql.append(" order by em.nombreFiscal");
/* 1067:     */     
/* 1068:1359 */     Query query = this.em.createQuery(sql.toString());
/* 1069:1360 */     query.setParameter("fechaDesde", fechaDesde);
/* 1070:1361 */     query.setParameter("fechaHasta", fechaHasta);
/* 1071:1362 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 1072:1363 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1073:1364 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_CLIENTE);
/* 1074:1365 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1075:1366 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 1076:1367 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 1077:     */     }
/* 1078:1369 */     if (null != puntoVenta) {
/* 1079:1370 */       query.setParameter("codigoPuntoVenta", puntoVenta.getCodigo().trim());
/* 1080:     */     }
/* 1081:1373 */     return query.getResultList();
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public List<DetalleCobro> getReporteListaDetalleCobro(List<Cobro> listaCobro)
/* 1085:     */   {
/* 1086:1379 */     StringBuilder sql = new StringBuilder();
/* 1087:     */     
/* 1088:1381 */     sql.append(" SELECT dc FROM DetalleCobro dc ");
/* 1089:1382 */     sql.append(" LEFT JOIN FETCH dc.cobro c ");
/* 1090:1383 */     sql.append(" LEFT JOIN FETCH dc.cuentaPorCobrar cxc ");
/* 1091:1384 */     sql.append(" LEFT JOIN FETCH cxc.facturaCliente fc ");
/* 1092:1385 */     sql.append(" LEFT JOIN FETCH fc.empresa e ");
/* 1093:1386 */     sql.append(" LEFT JOIN FETCH fc.facturaClienteSRI fcs ");
/* 1094:1387 */     sql.append(" WHERE c in (:listaCobro)");
/* 1095:1388 */     Query query = this.em.createQuery(sql.toString());
/* 1096:1389 */     query.setParameter("listaCobro", listaCobro);
/* 1097:1390 */     return query.getResultList();
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public List getListaReporteFacturacionProductoResumido(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCliente, int idVendedor, boolean anuladas, int idCanal, int idZona, Sucursal sucursal, PuntoDeVenta puntoVenta, TipoVentaEnum tipoVenta, boolean saldoInicial, int idOrganizacion, DocumentoBase documentoBase, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Atributo atributo, String valorAtributo, DimensionContable proyecto)
/* 1101:     */   {
/* 1102:1399 */     StringBuilder sql2 = new StringBuilder();
/* 1103:1400 */     sql2.append(" SELECT p.codigo, p.nombre, u.codigo, u.nombre, SUM(df.cantidad),SUM(df.peso), dc.nombre, f.descripcion, fsri.autorizacion ");
/* 1104:1401 */     sql2.append(" FROM DetalleFacturaCliente df ");
/* 1105:1402 */     sql2.append(" LEFT OUTER JOIN df.facturaCliente f ");
/* 1106:1403 */     sql2.append(" LEFT OUTER JOIN f.facturaClienteSRI fsri ");
/* 1107:1404 */     sql2.append(" LEFT OUTER JOIN f.proyecto dc ");
/* 1108:1405 */     sql2.append(" LEFT OUTER JOIN f.sucursal s ");
/* 1109:1406 */     sql2.append(" LEFT OUTER JOIN f.empresa e ");
/* 1110:1407 */     sql2.append(" LEFT OUTER JOIN f.documento d ");
/* 1111:1408 */     sql2.append(" LEFT OUTER JOIN e.cliente cl ");
/* 1112:1409 */     sql2.append(" LEFT OUTER JOIN f.zona z ");
/* 1113:1410 */     sql2.append(" LEFT OUTER JOIN f.canal c ");
/* 1114:1411 */     sql2.append(" LEFT OUTER JOIN f.agenteComercial ac ");
/* 1115:1412 */     sql2.append(" LEFT OUTER JOIN df.producto p ");
/* 1116:1413 */     sql2.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/* 1117:1414 */     sql2.append(" LEFT OUTER JOIN sp.categoriaProducto cp ");
/* 1118:1415 */     sql2.append(" LEFT OUTER JOIN p.unidad u ");
/* 1119:1416 */     sql2.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1120:1417 */     if (sucursal != null) {
/* 1121:1418 */       sql2.append(" AND s.idSucursal = :idSucursal ");
/* 1122:     */     }
/* 1123:1420 */     sql2.append(" AND (e.idEmpresa = :idCliente OR :idCliente=0) ");
/* 1124:1421 */     sql2.append(" AND (ac.idUsuario = :idVendedor OR :idVendedor=0) ");
/* 1125:1422 */     sql2.append(" AND (z.idZona = :idZona OR :idZona=0) ");
/* 1126:1423 */     sql2.append(" AND (c.idCanal = :idCanal OR :idCanal=0) ");
/* 1127:1424 */     sql2.append(" AND (p.idProducto = :idProducto OR :idProducto=0) ");
/* 1128:1425 */     sql2.append(" AND (sp.idSubcategoriaProducto = :idSubcategoriaProducto OR :idSubcategoriaProducto=0) ");
/* 1129:1426 */     sql2.append(" AND (cp.idCategoriaProducto = :idCategoriaProducto OR :idCategoriaProducto=0) ");
/* 1130:1427 */     sql2.append(" AND f.indicadorSaldoInicial = :saldoInicial ");
/* 1131:1428 */     sql2.append(" AND f.idOrganizacion = :idOrganizacion ");
/* 1132:1429 */     if (puntoVenta != null) {
/* 1133:1430 */       sql2.append(" AND f.numero LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/* 1134:     */     }
/* 1135:1434 */     if (proyecto != null) {
/* 1136:1435 */       sql2.append(" AND dc.idDimensionContable = :idDimensionContable ");
/* 1137:     */     }
/* 1138:1437 */     if (documentoBase != null) {
/* 1139:1438 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/* 1140:1439 */         sql2.append(" AND d.documentoBase IN (:documentoBase,:documentoDevolucion) ");
/* 1141:     */       } else {
/* 1142:1441 */         sql2.append(" AND d.documentoBase = :documentoBase ");
/* 1143:     */       }
/* 1144:     */     }
/* 1145:1446 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0)) {
/* 1146:1447 */       sql2.append(" AND f.numero >= :numeroDesde AND f.numero <= :numeroHasta ");
/* 1147:     */     }
/* 1148:1450 */     if (anuladas) {
/* 1149:1451 */       sql2.append(" AND f.estado = :estadoAnulado ");
/* 1150:     */     } else {
/* 1151:1453 */       sql2.append(" AND f.estado != :estadoAnulado ");
/* 1152:     */     }
/* 1153:1456 */     if (!tipoVenta.equals(TipoVentaEnum.TODOS)) {
/* 1154:1457 */       sql2.append(" AND f.documento.indicadorDocumentoExterior = :tipoVenta ");
/* 1155:     */     }
/* 1156:1459 */     if (atributo != null)
/* 1157:     */     {
/* 1158:1460 */       sql2.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND p = pr");
/* 1159:1462 */       if (!valorAtributo.isEmpty()) {
/* 1160:1463 */         sql2.append(" AND pa.valor = :valorAtributo ");
/* 1161:     */       }
/* 1162:1465 */       sql2.append(" )");
/* 1163:     */     }
/* 1164:1470 */     sql2.append(" GROUP BY p.codigo, p.nombre, u.codigo, u.nombre, dc.nombre, f.descripcion, fsri.autorizacion");
/* 1165:1471 */     sql2.append(" ORDER BY p.nombre ");
/* 1166:     */     
/* 1167:1473 */     Query query = this.em.createQuery(sql2.toString());
/* 1168:1474 */     query.setParameter("fechaDesde", fechaDesde);
/* 1169:1475 */     query.setParameter("fechaHasta", fechaHasta);
/* 1170:1476 */     query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/* 1171:1477 */     if (sucursal != null) {
/* 1172:1478 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/* 1173:     */     }
/* 1174:1480 */     if (puntoVenta != null) {
/* 1175:1481 */       query.setParameter("codigoPuntoVenta", puntoVenta.getCodigo().trim());
/* 1176:     */     }
/* 1177:1483 */     if (proyecto != null) {
/* 1178:1484 */       query.setParameter("idDimensionContable", Integer.valueOf(proyecto.getId()));
/* 1179:     */     }
/* 1180:1486 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/* 1181:1487 */     query.setParameter("idZona", Integer.valueOf(idZona));
/* 1182:1488 */     query.setParameter("idCanal", Integer.valueOf(idCanal));
/* 1183:1489 */     query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto == null ? 0 : subcategoriaProducto.getIdSubcategoriaProducto()));
/* 1184:1490 */     query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto == null ? 0 : categoriaProducto.getIdCategoriaProducto()));
/* 1185:1491 */     query.setParameter("idProducto", Integer.valueOf(producto == null ? 0 : producto.getIdProducto()));
/* 1186:1492 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/* 1187:1493 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1188:1494 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1189:1495 */     if (atributo != null)
/* 1190:     */     {
/* 1191:1496 */       query.setParameter("atributo", atributo);
/* 1192:1497 */       if (!valorAtributo.isEmpty()) {
/* 1193:1498 */         query.setParameter("valorAtributo", valorAtributo);
/* 1194:     */       }
/* 1195:     */     }
/* 1196:1502 */     if (documentoBase != null)
/* 1197:     */     {
/* 1198:1503 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/* 1199:1504 */         query.setParameter("documentoDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
/* 1200:     */       }
/* 1201:1506 */       query.setParameter("documentoBase", documentoBase);
/* 1202:     */     }
/* 1203:1509 */     if (!tipoVenta.equals(TipoVentaEnum.TODOS))
/* 1204:     */     {
/* 1205:1510 */       query.setParameter("tipoVenta", Boolean.valueOf(false));
/* 1206:1511 */       if (tipoVenta.equals(TipoVentaEnum.EXTERIOR)) {
/* 1207:1512 */         query.setParameter("tipoVenta", Boolean.valueOf(true));
/* 1208:     */       }
/* 1209:     */     }
/* 1210:1515 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0))
/* 1211:     */     {
/* 1212:1516 */       query.setParameter("numeroDesde", numeroDesde);
/* 1213:1517 */       query.setParameter("numeroHasta", numeroHasta);
/* 1214:     */     }
/* 1215:1519 */     return query.getResultList();
/* 1216:     */   }
/* 1217:     */   
/* 1218:     */   public List<Object[]> getReporteAnalisisVentasCliente(Date fechaDesde, Date fechaHasta, boolean saldoInicial, int idOrganizacion, int idSubcategoriaProducto, boolean indicadorCantidad)
/* 1219:     */   {
/* 1220:     */     String sumatoria;
/* 1221:     */     String sumatoria;
/* 1222:1527 */     if (indicadorCantidad) {
/* 1223:1528 */       sumatoria = "SUM(df.cantidad)";
/* 1224:     */     } else {
/* 1225:1531 */       sumatoria = "ROUND(SUM(df.precioLinea-df.descuentoLinea),2)";
/* 1226:     */     }
/* 1227:1534 */     List<Object[]> lista = new ArrayList();
/* 1228:1535 */     StringBuilder sql = new StringBuilder();
/* 1229:1536 */     sql.append(" SELECT e.nombreComercial, e.identificacion, p.codigo, p.codigoComercial, p.nombre, u.nombre," + sumatoria);
/* 1230:1537 */     sql.append(" FROM DetalleFacturaCliente df ");
/* 1231:1538 */     sql.append(" LEFT OUTER JOIN df.facturaCliente f ");
/* 1232:1539 */     sql.append(" LEFT OUTER JOIN f.empresa e ");
/* 1233:1540 */     sql.append(" LEFT OUTER JOIN df.producto p ");
/* 1234:1541 */     sql.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/* 1235:1542 */     sql.append(" LEFT OUTER JOIN p.unidad u ");
/* 1236:1543 */     sql.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1237:1544 */     sql.append(" AND f.indicadorSaldoInicial = :saldoInicial ");
/* 1238:1545 */     sql.append(" AND f.idOrganizacion = :idOrganizacion AND f.estado != :estadoAnulado");
/* 1239:1546 */     sql.append(" AND (sp.idSubcategoriaProducto = :idSubcategoriaProducto OR :idSubcategoriaProducto=0) ");
/* 1240:1547 */     sql.append(" GROUP BY e.nombreComercial, e.identificacion, p.codigo, p.codigoComercial, p.nombre, u.codigo, u.nombre");
/* 1241:1548 */     sql.append(" ORDER BY e.nombreComercial, p.nombre ");
/* 1242:     */     
/* 1243:1550 */     Query query = this.em.createQuery(sql.toString());
/* 1244:1551 */     query.setParameter("fechaDesde", fechaDesde);
/* 1245:1552 */     query.setParameter("fechaHasta", fechaHasta);
/* 1246:1553 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/* 1247:1554 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1248:1555 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1249:1556 */     query.setParameter("idSubcategoriaProducto", Integer.valueOf(idSubcategoriaProducto));
/* 1250:1557 */     lista.addAll(query.getResultList());
/* 1251:1559 */     if (indicadorCantidad) {
/* 1252:1560 */       sumatoria = "SUM(df.valor*0)";
/* 1253:     */     } else {
/* 1254:1562 */       sumatoria = "SUM(df.valor)";
/* 1255:     */     }
/* 1256:1564 */     sql = new StringBuilder();
/* 1257:1565 */     sql.append(" SELECT e.nombreComercial, e.identificacion, df.recargo, df.recargo, df.recargo, 'S/U'," + sumatoria);
/* 1258:1566 */     sql.append(" FROM DetalleFacturaClienteComercializadora df ");
/* 1259:1567 */     sql.append(" LEFT OUTER JOIN df.facturaCliente f ");
/* 1260:1568 */     sql.append(" LEFT OUTER JOIN f.empresa e ");
/* 1261:1569 */     sql.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1262:1570 */     sql.append(" AND f.indicadorSaldoInicial = :saldoInicial ");
/* 1263:1571 */     sql.append(" AND f.idOrganizacion = :idOrganizacion AND f.estado != :estadoAnulado");
/* 1264:1572 */     sql.append(" GROUP BY e.nombreComercial, e.identificacion, df.recargo");
/* 1265:1573 */     sql.append(" ORDER BY e.nombreComercial, df.recargo ");
/* 1266:     */     
/* 1267:1575 */     query = this.em.createQuery(sql.toString());
/* 1268:1576 */     query.setParameter("fechaDesde", fechaDesde);
/* 1269:1577 */     query.setParameter("fechaHasta", fechaHasta);
/* 1270:1578 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/* 1271:1579 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1272:1580 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1273:1581 */     lista.addAll(query.getResultList());
/* 1274:     */     
/* 1275:1583 */     return lista;
/* 1276:     */   }
/* 1277:     */   
/* 1278:     */   public List getReporteAnalisisVentasProducto(Date fechaDesde, Date fechaHasta, boolean saldoInicial, int idOrganizacion, int idSubcategoriaProducto, boolean indicadorCantidad)
/* 1279:     */   {
/* 1280:     */     String sumatoria;
/* 1281:     */     String sumatoria;
/* 1282:1591 */     if (indicadorCantidad) {
/* 1283:1592 */       sumatoria = "SUM(df.cantidad)";
/* 1284:     */     } else {
/* 1285:1595 */       sumatoria = "ROUND(SUM(df.precioLinea-df.descuentoLinea),2)";
/* 1286:     */     }
/* 1287:1598 */     StringBuilder sql = new StringBuilder();
/* 1288:1599 */     sql.append(" SELECT e.nombreComercial, p.codigo, p.codigoComercial, p.nombre, u.nombre," + sumatoria + ", YEAR(f.fecha), MONTH(f.fecha)");
/* 1289:1600 */     sql.append(" FROM DetalleFacturaCliente df ");
/* 1290:1601 */     sql.append(" LEFT OUTER JOIN df.facturaCliente f ");
/* 1291:1602 */     sql.append(" LEFT OUTER JOIN f.empresa e ");
/* 1292:1603 */     sql.append(" LEFT OUTER JOIN df.producto p ");
/* 1293:1604 */     sql.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/* 1294:1605 */     sql.append(" LEFT OUTER JOIN p.unidad u ");
/* 1295:1606 */     sql.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 1296:1607 */     sql.append(" AND f.indicadorSaldoInicial = :saldoInicial ");
/* 1297:1608 */     sql.append(" AND f.idOrganizacion = :idOrganizacion AND f.estado != :estadoAnulado");
/* 1298:1609 */     sql.append(" AND (sp.idSubcategoriaProducto = :idSubcategoriaProducto OR :idSubcategoriaProducto=0) ");
/* 1299:1610 */     sql.append(" GROUP BY e.nombreComercial, p.codigo, p.codigoComercial, p.nombre, u.codigo, u.nombre, YEAR(f.fecha), MONTH(f.fecha)");
/* 1300:1611 */     sql.append(" ORDER BY e.nombreComercial, p.nombre, YEAR(f.fecha), MONTH(f.fecha)");
/* 1301:     */     
/* 1302:1613 */     Query query = this.em.createQuery(sql.toString());
/* 1303:1614 */     query.setParameter("fechaDesde", fechaDesde);
/* 1304:1615 */     query.setParameter("fechaHasta", fechaHasta);
/* 1305:1616 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/* 1306:1617 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1307:1618 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1308:1619 */     query.setParameter("idSubcategoriaProducto", Integer.valueOf(idSubcategoriaProducto));
/* 1309:     */     
/* 1310:1621 */     return query.getResultList();
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   public List getSaldosFactura(Date fechaHasta, int idEmpresaFinal)
/* 1314:     */   {
/* 1315:1628 */     StringBuilder sql = new StringBuilder();
/* 1316:1629 */     sql.append(" SELECT fc.fecha, fc.numero, SUM(cxc.saldo), SUM(cxc.valorBloqueado), fc.empresa.idEmpresa, se.empresa.idEmpresa, cxc.idCuentaPorCobrar, fc.indicadorEmisionRetencion, cp.diasPlazo, cp.mesesPlazo ");
/* 1317:     */     
/* 1318:1631 */     sql.append(" FROM CuentaPorCobrar cxc ");
/* 1319:1632 */     sql.append(" INNER JOIN cxc.facturaCliente fc ");
/* 1320:1633 */     sql.append(" INNER JOIN fc.condicionPago cp ");
/* 1321:1634 */     sql.append(" LEFT JOIN fc.subempresa se ");
/* 1322:1635 */     sql.append(" WHERE ((se.empresa.idEmpresa = :idEmpresaFinal) ");
/* 1323:1636 */     sql.append(" OR (fc.empresa.idEmpresa = :idEmpresaFinal)) ");
/* 1324:1637 */     sql.append(" AND fc.fecha <= :fechaHasta ");
/* 1325:1638 */     sql.append(" AND fc.estado != :estadoAnulado ");
/* 1326:1639 */     sql.append(" AND cxc.indicadorAnulada = false ");
/* 1327:1640 */     sql.append(" GROUP BY fc.fecha, cxc.idCuentaPorCobrar, fc.numero, fc.empresa.idEmpresa, se.empresa.idEmpresa, fc.indicadorEmisionRetencion, cp.diasPlazo, cp.mesesPlazo ");
/* 1328:1641 */     sql.append(" HAVING SUM(cxc.saldo) > 0 ");
/* 1329:1642 */     sql.append(" ORDER BY fc.fecha, fc.numero, fc.empresa.idEmpresa ");
/* 1330:1643 */     Query query = this.em.createQuery(sql.toString());
/* 1331:1644 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1332:1645 */     query.setParameter("fechaHasta", fechaHasta);
/* 1333:1646 */     query.setParameter("idEmpresaFinal", Integer.valueOf(idEmpresaFinal));
/* 1334:     */     
/* 1335:1648 */     return query.getResultList();
/* 1336:     */   }
/* 1337:     */   
/* 1338:     */   public List<Object[]> getVentaProductoVendido(Date fechaDesde, Date fechaHasta, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo, String valorAtributo, String tipoReporte, Sucursal sucursal, boolean saldoInicial, CategoriaEmpresa categoriaEmpresa, Empresa empresa)
/* 1339:     */   {
/* 1340:1661 */     StringBuilder sql = new StringBuilder();
/* 1341:1662 */     String criterioAgrupacion = criterioAgrupacion(tipoReporte);
/* 1342:1663 */     String criterioColumnas = criterioColumnas(tipoReporte);
/* 1343:1664 */     sql.append(" SELECT " + criterioColumnas + ",sum(dfc.cantidad), sum(dfc.precio-dfc.descuento),sum(dfc.precioLinea-dfc.descuentoLinea), " + " sum(dfc.precioLinea*0), sum(dfc.precioLinea*0), sum(dfc.precioLinea*0)," + " sum(dfc.precioLinea*0), sum(dfc.precioLinea*0), sum(dfc.precioLinea*0)," + " sum(dfc.precioLinea*0), sum(dfc.precioLinea*0), sum(dfc.precioLinea*0)," + " sum(dfc.precioLinea*0), sum(dfc.precioLinea*0), sum(dfc.precioLinea*0)," + " sum(dfc.precioLinea*0), sum(dfc.precioLinea*0), sum(dfc.precioLinea*0) ");
/* 1344:     */     
/* 1345:     */ 
/* 1346:     */ 
/* 1347:     */ 
/* 1348:     */ 
/* 1349:1670 */     sql.append(" FROM DetalleFacturaCliente dfc ");
/* 1350:1671 */     sql.append(" JOIN dfc.facturaCliente fc ");
/* 1351:1672 */     sql.append(" JOIN fc.sucursal s ");
/* 1352:1673 */     sql.append(" JOIN fc.empresa e ");
/* 1353:1674 */     sql.append(" JOIN e.categoriaEmpresa cate ");
/* 1354:1675 */     sql.append(" JOIN fc.documento d ");
/* 1355:1676 */     sql.append(" JOIN dfc.producto p ");
/* 1356:1677 */     sql.append(" JOIN p.unidadVenta uni ");
/* 1357:1678 */     sql.append(" JOIN p.subcategoriaProducto subcat ");
/* 1358:1679 */     sql.append(" JOIN subcat.categoriaProducto cat  ");
/* 1359:1680 */     sql.append(" LEFT JOIN dfc.detalleDespachoCliente ddc  ");
/* 1360:1681 */     sql.append(" LEFT JOIN ddc.despachoCliente dc  ");
/* 1361:1682 */     sql.append(" LEFT JOIN ddc.bodega bod  ");
/* 1362:1683 */     sql.append(" LEFT JOIN fc.agenteComercial ac ");
/* 1363:1684 */     sql.append(" LEFT OUTER JOIN fc.canal c ");
/* 1364:     */     
/* 1365:1686 */     sql.append(" WHERE fc.idOrganizacion = :idOrganizacion ");
/* 1366:1688 */     if (empresa != null) {
/* 1367:1689 */       sql.append(" AND e = :empresa ");
/* 1368:     */     }
/* 1369:1692 */     if (categoriaProducto != null) {
/* 1370:1693 */       sql.append(" AND cat = :categoriaProducto ");
/* 1371:     */     }
/* 1372:1695 */     if (subcategoriaProducto != null) {
/* 1373:1696 */       sql.append(" AND subcat = :subcategoriaProducto ");
/* 1374:     */     }
/* 1375:1698 */     if (categoriaEmpresa != null) {
/* 1376:1699 */       sql.append(" AND cate = :categoriaEmpresa ");
/* 1377:     */     }
/* 1378:1701 */     if (atributo != null)
/* 1379:     */     {
/* 1380:1702 */       sql.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND p = pr");
/* 1381:1704 */       if (!valorAtributo.isEmpty()) {
/* 1382:1705 */         sql.append(" AND pa.valor = :valorAtributo ");
/* 1383:     */       }
/* 1384:1707 */       sql.append(" )");
/* 1385:     */     }
/* 1386:1709 */     if (sucursal != null) {
/* 1387:1710 */       sql.append(" AND s = :sucursal ");
/* 1388:     */     }
/* 1389:1712 */     sql.append(" AND fc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1390:1713 */     sql.append(" AND fc.estado != :estadoAnulado ");
/* 1391:1714 */     sql.append(" AND fc.indicadorSaldoInicial = :saldoInicial ");
/* 1392:1715 */     sql.append(" AND CASE WHEN dc IS NOT NULL THEN dc.estado  ELSE fc.estado END != :estadoAnulado");
/* 1393:1716 */     sql.append(" AND d.documentoBase = :documentoBaseFacturaCliente ");
/* 1394:1717 */     sql.append(" GROUP BY " + criterioAgrupacion + "");
/* 1395:     */     
/* 1396:1719 */     Query query = this.em.createQuery(sql.toString());
/* 1397:1720 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1398:1721 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 1399:1722 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 1400:1723 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1401:1724 */     query.setParameter("documentoBaseFacturaCliente", DocumentoBase.FACTURA_CLIENTE);
/* 1402:1725 */     if (empresa != null) {
/* 1403:1726 */       query.setParameter("empresa", empresa);
/* 1404:     */     }
/* 1405:1728 */     if (categoriaProducto != null) {
/* 1406:1729 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 1407:     */     }
/* 1408:1731 */     if (subcategoriaProducto != null) {
/* 1409:1732 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 1410:     */     }
/* 1411:1734 */     if (categoriaEmpresa != null) {
/* 1412:1735 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 1413:     */     }
/* 1414:1737 */     if (atributo != null)
/* 1415:     */     {
/* 1416:1738 */       query.setParameter("atributo", atributo);
/* 1417:1739 */       if (!valorAtributo.isEmpty()) {
/* 1418:1740 */         query.setParameter("valorAtributo", valorAtributo);
/* 1419:     */       }
/* 1420:     */     }
/* 1421:1743 */     if (sucursal != null) {
/* 1422:1744 */       query.setParameter("sucursal", sucursal);
/* 1423:     */     }
/* 1424:1747 */     query.setParameter("saldoInicial", Boolean.valueOf(saldoInicial));
/* 1425:     */     
/* 1426:1749 */     return query.getResultList();
/* 1427:     */   }
/* 1428:     */   
/* 1429:     */   public List<Object[]> getDespachoProductoVendido(Date fechaDesde, Date fechaHasta, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo, String valorAtributo, String tipoReporte, Sucursal sucursal, CategoriaEmpresa categoriaEmpresa, Empresa empresa)
/* 1430:     */   {
/* 1431:1758 */     StringBuilder sql = new StringBuilder();
/* 1432:1759 */     String criterioAgrupacion = criterioAgrupacion(tipoReporte);
/* 1433:1760 */     String criterioColumnas = criterioColumnas(tipoReporte);
/* 1434:     */     
/* 1435:1762 */     sql.append(" SELECT " + criterioColumnas + ",sum(ip.cantidad), sum(ddc.cantidad*0), sum(ip.costo)  ");
/* 1436:1763 */     sql.append(" FROM DetalleDespachoCliente ddc ");
/* 1437:1764 */     sql.append(" INNER JOIN ddc.bodega bod  ");
/* 1438:1765 */     sql.append(" INNER JOIN ddc.despachoCliente dc ");
/* 1439:1766 */     sql.append(" INNER JOIN ddc.inventarioProducto ip ");
/* 1440:1767 */     sql.append(" INNER JOIN ddc.producto p ");
/* 1441:1768 */     sql.append(" INNER JOIN p.subcategoriaProducto subcat ");
/* 1442:1769 */     sql.append(" INNER JOIN subcat.categoriaProducto cat  ");
/* 1443:1770 */     sql.append(" LEFT JOIN ddc.detalleFacturaCliente dfc ");
/* 1444:1771 */     sql.append(" LEFT JOIN dfc.facturaCliente fc ");
/* 1445:1772 */     sql.append(" LEFT JOIN fc.sucursal s ");
/* 1446:1773 */     sql.append(" LEFT JOIN fc.empresa e ");
/* 1447:1774 */     sql.append(" LEFT JOIN e.categoriaEmpresa cate ");
/* 1448:1775 */     sql.append(" LEFT JOIN dfc.producto p ");
/* 1449:1776 */     sql.append(" LEFT JOIN p.unidadVenta uni ");
/* 1450:1777 */     sql.append(" LEFT JOIN fc.agenteComercial ac ");
/* 1451:1778 */     sql.append(" LEFT OUTER JOIN fc.canal c ");
/* 1452:1779 */     sql.append(" WHERE fc.idOrganizacion = :idOrganizacion ");
/* 1453:1780 */     if (empresa != null) {
/* 1454:1781 */       sql.append(" AND e = :empresa ");
/* 1455:     */     }
/* 1456:1783 */     if (categoriaProducto != null) {
/* 1457:1784 */       sql.append(" AND cat = :categoriaProducto ");
/* 1458:     */     }
/* 1459:1786 */     if (subcategoriaProducto != null) {
/* 1460:1787 */       sql.append(" AND subcat = :subcategoriaProducto ");
/* 1461:     */     }
/* 1462:1789 */     if (categoriaEmpresa != null) {
/* 1463:1790 */       sql.append(" AND cate = :categoriaEmpresa ");
/* 1464:     */     }
/* 1465:1792 */     if (atributo != null)
/* 1466:     */     {
/* 1467:1793 */       sql.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND p = pr");
/* 1468:1795 */       if (!valorAtributo.isEmpty()) {
/* 1469:1796 */         sql.append(" AND pa.valor = :valorAtributo ");
/* 1470:     */       }
/* 1471:1798 */       sql.append(" )");
/* 1472:     */     }
/* 1473:1800 */     if (sucursal != null) {
/* 1474:1801 */       sql.append(" AND s = :sucursal ");
/* 1475:     */     }
/* 1476:1803 */     sql.append(" AND dc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1477:1804 */     sql.append(" AND dc.estado != :estadoAnulado ");
/* 1478:1805 */     sql.append(" GROUP BY " + criterioAgrupacion);
/* 1479:     */     
/* 1480:1807 */     Query query = this.em.createQuery(sql.toString());
/* 1481:1808 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1482:1809 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 1483:1810 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 1484:1811 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1485:1812 */     if (empresa != null) {
/* 1486:1813 */       query.setParameter("empresa", empresa);
/* 1487:     */     }
/* 1488:1815 */     if (categoriaProducto != null) {
/* 1489:1816 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 1490:     */     }
/* 1491:1818 */     if (subcategoriaProducto != null) {
/* 1492:1819 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 1493:     */     }
/* 1494:1821 */     if (categoriaEmpresa != null) {
/* 1495:1822 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 1496:     */     }
/* 1497:1824 */     if (atributo != null)
/* 1498:     */     {
/* 1499:1825 */       query.setParameter("atributo", atributo);
/* 1500:1826 */       if (!valorAtributo.isEmpty()) {
/* 1501:1827 */         query.setParameter("valorAtributo", valorAtributo);
/* 1502:     */       }
/* 1503:     */     }
/* 1504:1830 */     if (sucursal != null) {
/* 1505:1831 */       query.setParameter("sucursal", sucursal);
/* 1506:     */     }
/* 1507:1834 */     return query.getResultList();
/* 1508:     */   }
/* 1509:     */   
/* 1510:     */   public List<Object[]> getNotasCreditoProductoVendido(Date fechaDesde, Date fechaHasta, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo, String valorAtributo, String tipoReporte, Sucursal sucursal, CategoriaEmpresa categoriaEmpresa, Empresa empresa)
/* 1511:     */   {
/* 1512:1843 */     StringBuilder sql = new StringBuilder();
/* 1513:1844 */     String criterioAgrupacion = criterioAgrupacion(tipoReporte);
/* 1514:1845 */     String criterioColumnas = criterioColumnas(tipoReporte);
/* 1515:     */     
/* 1516:1847 */     sql.append(" SELECT " + criterioColumnas + " ,sum(dnc.cantidad), sum(dnc.precio) ,sum(dnc.precioLinea) ");
/* 1517:1848 */     sql.append(" FROM DetalleFacturaCliente dnc ");
/* 1518:1849 */     sql.append(" JOIN dnc.facturaCliente nc ");
/* 1519:1850 */     sql.append(" JOIN nc.sucursal s ");
/* 1520:1851 */     sql.append(" JOIN nc.documento d ");
/* 1521:1852 */     sql.append(" JOIN nc.facturaClientePadre fc ");
/* 1522:1853 */     sql.append(" JOIN fc.empresa e ");
/* 1523:1854 */     sql.append(" JOIN e.categoriaEmpresa cate ");
/* 1524:1855 */     sql.append(" JOIN dnc.producto p ");
/* 1525:1856 */     sql.append(" JOIN p.unidadVenta uni ");
/* 1526:1857 */     sql.append(" JOIN p.subcategoriaProducto subcat ");
/* 1527:1858 */     sql.append(" JOIN subcat.categoriaProducto cat  ");
/* 1528:1859 */     sql.append(" LEFT JOIN dnc.detalleFacturaClientePadre dfcp  ");
/* 1529:1860 */     sql.append(" LEFT JOIN dfcp.detalleDespachoCliente ddc  ");
/* 1530:1861 */     sql.append(" LEFT JOIN ddc.despachoCliente dc  ");
/* 1531:1862 */     sql.append(" LEFT JOIN ddc.bodega bod  ");
/* 1532:1863 */     sql.append(" LEFT JOIN nc.agenteComercial ac ");
/* 1533:1864 */     sql.append(" LEFT OUTER JOIN nc.canal c ");
/* 1534:     */     
/* 1535:1866 */     sql.append(" WHERE fc.idOrganizacion = :idOrganizacion ");
/* 1536:1867 */     if (empresa != null) {
/* 1537:1868 */       sql.append(" AND e = :empresa ");
/* 1538:     */     }
/* 1539:1870 */     if (categoriaProducto != null) {
/* 1540:1871 */       sql.append(" AND cat = :categoriaProducto ");
/* 1541:     */     }
/* 1542:1873 */     if (subcategoriaProducto != null) {
/* 1543:1874 */       sql.append(" AND subcat = :subcategoriaProducto ");
/* 1544:     */     }
/* 1545:1876 */     if (categoriaEmpresa != null) {
/* 1546:1877 */       sql.append(" AND cate = :categoriaEmpresa ");
/* 1547:     */     }
/* 1548:1879 */     if (atributo != null)
/* 1549:     */     {
/* 1550:1880 */       sql.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND p = pr");
/* 1551:1882 */       if (!valorAtributo.isEmpty()) {
/* 1552:1883 */         sql.append(" AND pa.valor = :valorAtributo ");
/* 1553:     */       }
/* 1554:1885 */       sql.append(" )");
/* 1555:     */     }
/* 1556:1887 */     if (sucursal != null) {
/* 1557:1888 */       sql.append(" AND s = :sucursal ");
/* 1558:     */     }
/* 1559:1890 */     sql.append(" AND nc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1560:1891 */     sql.append(" AND nc.estado != :estadoAnulado ");
/* 1561:1892 */     sql.append(" AND d.documentoBase = :documentoBaseNotaCredito ");
/* 1562:1893 */     sql.append(" GROUP BY " + criterioAgrupacion);
/* 1563:     */     
/* 1564:1895 */     Query query = this.em.createQuery(sql.toString());
/* 1565:1896 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1566:1897 */     query.setParameter("documentoBaseNotaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 1567:1898 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 1568:1899 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 1569:1900 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1570:1901 */     if (empresa != null) {
/* 1571:1902 */       query.setParameter("empresa", empresa);
/* 1572:     */     }
/* 1573:1904 */     if (categoriaProducto != null) {
/* 1574:1905 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 1575:     */     }
/* 1576:1907 */     if (subcategoriaProducto != null) {
/* 1577:1908 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 1578:     */     }
/* 1579:1910 */     if (categoriaEmpresa != null) {
/* 1580:1911 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 1581:     */     }
/* 1582:1913 */     if (atributo != null)
/* 1583:     */     {
/* 1584:1914 */       query.setParameter("atributo", atributo);
/* 1585:1915 */       if (!valorAtributo.isEmpty()) {
/* 1586:1916 */         query.setParameter("valorAtributo", valorAtributo);
/* 1587:     */       }
/* 1588:     */     }
/* 1589:1919 */     if (sucursal != null) {
/* 1590:1920 */       query.setParameter("sucursal", sucursal);
/* 1591:     */     }
/* 1592:1923 */     return query.getResultList();
/* 1593:     */   }
/* 1594:     */   
/* 1595:     */   public List<Object[]> getDevolucionesProductoVendido(Date fechaDesde, Date fechaHasta, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo, String valorAtributo, String tipoReporte, boolean indicadorParaCostos, Sucursal sucursal, CategoriaEmpresa categoriaEmpresa, Empresa empresa)
/* 1596:     */   {
/* 1597:1932 */     StringBuilder sql = new StringBuilder();
/* 1598:1933 */     String criterioAgrupacion = criterioAgrupacion(tipoReporte);
/* 1599:1934 */     String criterioColumnas = criterioColumnas(tipoReporte);
/* 1600:1936 */     if (indicadorParaCostos) {
/* 1601:1937 */       sql.append(" SELECT" + criterioColumnas + " ,sum(ip.cantidad), sum(0*ddvc.cantidad), sum(ip.costo) ");
/* 1602:     */     } else {
/* 1603:1939 */       sql.append(" SELECT" + criterioColumnas + " ,sum(ddvc.cantidad), sum(ddvc.precio-ddvc.descuento), sum(ddvc.precioLinea-ddvc.descuentoLinea) ");
/* 1604:     */     }
/* 1605:1942 */     sql.append(" FROM DetalleFacturaCliente ddvc ");
/* 1606:1943 */     sql.append(" INNER JOIN ddvc.facturaCliente dvc ");
/* 1607:1944 */     sql.append(" LEFT JOIN dvc.sucursal s ");
/* 1608:1945 */     sql.append(" LEFT JOIN dvc.documento d ");
/* 1609:1946 */     sql.append(" LEFT JOIN dvc.facturaClientePadre fc ");
/* 1610:1947 */     sql.append(" LEFT JOIN fc.empresa e ");
/* 1611:1948 */     sql.append(" LEFT JOIN e.categoriaEmpresa cate ");
/* 1612:1949 */     sql.append(" LEFT JOIN ddvc.inventarioProducto ip ");
/* 1613:1950 */     sql.append(" LEFT JOIN  ip.bodega bod ");
/* 1614:1951 */     sql.append(" LEFT JOIN ddvc.producto p ");
/* 1615:1952 */     sql.append(" LEFT JOIN p.unidadVenta uni ");
/* 1616:1953 */     sql.append(" LEFT JOIN p.subcategoriaProducto subcat ");
/* 1617:1954 */     sql.append(" LEFT JOIN subcat.categoriaProducto cat  ");
/* 1618:1955 */     sql.append(" LEFT JOIN ddvc.detalleDespachoCliente ddc  ");
/* 1619:1956 */     sql.append(" LEFT JOIN ddc.despachoCliente dc  ");
/* 1620:1957 */     sql.append(" LEFT JOIN fc.agenteComercial ac ");
/* 1621:1958 */     sql.append(" LEFT OUTER JOIN fc.canal c ");
/* 1622:1959 */     sql.append(" WHERE fc.idOrganizacion = :idOrganizacion ");
/* 1623:1960 */     if (empresa != null) {
/* 1624:1961 */       sql.append(" AND e = :empresa ");
/* 1625:     */     }
/* 1626:1963 */     if (categoriaProducto != null) {
/* 1627:1964 */       sql.append(" AND cat = :categoriaProducto ");
/* 1628:     */     }
/* 1629:1966 */     if (subcategoriaProducto != null) {
/* 1630:1967 */       sql.append(" AND subcat = :subcategoriaProducto ");
/* 1631:     */     }
/* 1632:1969 */     if (categoriaEmpresa != null) {
/* 1633:1970 */       sql.append(" AND cate = :categoriaEmpresa ");
/* 1634:     */     }
/* 1635:1972 */     if (atributo != null)
/* 1636:     */     {
/* 1637:1973 */       sql.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND p = pr");
/* 1638:1975 */       if (!valorAtributo.isEmpty()) {
/* 1639:1976 */         sql.append(" AND pa.valor = :valorAtributo ");
/* 1640:     */       }
/* 1641:1978 */       sql.append(" )");
/* 1642:     */     }
/* 1643:1980 */     if (sucursal != null) {
/* 1644:1981 */       sql.append(" AND s = :sucursal ");
/* 1645:     */     }
/* 1646:1983 */     sql.append(" AND dvc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1647:1984 */     sql.append(" AND dvc.estado != :estadoAnulado ");
/* 1648:1985 */     sql.append(" AND d.documentoBase = :documentoBaseDevolucionCliente  ");
/* 1649:1986 */     sql.append(" GROUP BY " + criterioAgrupacion);
/* 1650:     */     
/* 1651:1988 */     Query query = this.em.createQuery(sql.toString());
/* 1652:1989 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1653:1990 */     query.setParameter("documentoBaseDevolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
/* 1654:1991 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 1655:1992 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 1656:1993 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1657:1994 */     if (empresa != null) {
/* 1658:1995 */       query.setParameter("empresa", empresa);
/* 1659:     */     }
/* 1660:1997 */     if (categoriaProducto != null) {
/* 1661:1998 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 1662:     */     }
/* 1663:2000 */     if (subcategoriaProducto != null) {
/* 1664:2001 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 1665:     */     }
/* 1666:2003 */     if (categoriaEmpresa != null) {
/* 1667:2004 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 1668:     */     }
/* 1669:2006 */     if (atributo != null)
/* 1670:     */     {
/* 1671:2007 */       query.setParameter("atributo", atributo);
/* 1672:2008 */       if (!valorAtributo.isEmpty()) {
/* 1673:2009 */         query.setParameter("valorAtributo", valorAtributo);
/* 1674:     */       }
/* 1675:     */     }
/* 1676:2012 */     if (sucursal != null) {
/* 1677:2013 */       query.setParameter("sucursal", sucursal);
/* 1678:     */     }
/* 1679:2016 */     return query.getResultList();
/* 1680:     */   }
/* 1681:     */   
/* 1682:     */   public List<Object[]> getNotasDebitoProductoVendido(Date fechaDesde, Date fechaHasta, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo, String valorAtributo, String tipoReporte, Sucursal sucursal, CategoriaEmpresa categoriaEmpresa, Empresa empresa)
/* 1683:     */   {
/* 1684:2025 */     StringBuilder sql = new StringBuilder();
/* 1685:2026 */     String criterioAgrupacion = criterioAgrupacion(tipoReporte);
/* 1686:2027 */     String criterioColumnas = criterioColumnas(tipoReporte);
/* 1687:     */     
/* 1688:2029 */     sql.append(" SELECT " + criterioColumnas + " ,sum(dnd.cantidad), sum(dnd.precio), sum(dnd.precioLinea) ");
/* 1689:2030 */     sql.append(" FROM DetalleFacturaCliente dnd ");
/* 1690:2031 */     sql.append(" JOIN dnd.facturaCliente nd ");
/* 1691:2032 */     sql.append(" JOIN nd.sucursal s ");
/* 1692:2033 */     sql.append(" JOIN nd.documento d ");
/* 1693:2034 */     sql.append(" JOIN nd.facturaClientePadre fc ");
/* 1694:2035 */     sql.append(" JOIN fc.empresa e ");
/* 1695:2036 */     sql.append(" JOIN e.categoriaEmpresa cate ");
/* 1696:2037 */     sql.append(" JOIN dnd.producto p ");
/* 1697:2038 */     sql.append(" JOIN p.unidadVenta uni ");
/* 1698:2039 */     sql.append(" JOIN p.subcategoriaProducto subcat ");
/* 1699:2040 */     sql.append(" JOIN subcat.categoriaProducto cat  ");
/* 1700:2041 */     sql.append(" LEFT JOIN dnd.detalleFacturaClientePadre dfcp  ");
/* 1701:2042 */     sql.append(" LEFT JOIN dfcp.detalleDespachoCliente ddc  ");
/* 1702:2043 */     sql.append(" LEFT JOIN ddc.despachoCliente dc  ");
/* 1703:2044 */     sql.append(" LEFT JOIN fc.agenteComercial ac ");
/* 1704:2045 */     sql.append(" LEFT JOIN ddc.bodega bod  ");
/* 1705:2046 */     sql.append(" LEFT OUTER JOIN fc.canal c ");
/* 1706:     */     
/* 1707:2048 */     sql.append(" WHERE fc.idOrganizacion = :idOrganizacion ");
/* 1708:2049 */     if (empresa != null) {
/* 1709:2050 */       sql.append(" AND e = :empresa ");
/* 1710:     */     }
/* 1711:2052 */     if (categoriaProducto != null) {
/* 1712:2053 */       sql.append(" AND cat = :categoriaProducto ");
/* 1713:     */     }
/* 1714:2055 */     if (subcategoriaProducto != null) {
/* 1715:2056 */       sql.append(" AND subcat = :subcategoriaProducto ");
/* 1716:     */     }
/* 1717:2058 */     if (categoriaEmpresa != null) {
/* 1718:2059 */       sql.append(" AND cate = :categoriaEmpresa ");
/* 1719:     */     }
/* 1720:2061 */     if (atributo != null)
/* 1721:     */     {
/* 1722:2062 */       sql.append(" AND EXISTS (SELECT pr FROM ProductoAtributo pa JOIN pa.producto pr WHERE pa.atributo = :atributo AND p = pr");
/* 1723:2064 */       if (!valorAtributo.isEmpty()) {
/* 1724:2065 */         sql.append(" AND pa.valor = :valorAtributo ");
/* 1725:     */       }
/* 1726:2067 */       sql.append(" )");
/* 1727:     */     }
/* 1728:2069 */     if (sucursal != null) {
/* 1729:2070 */       sql.append(" AND s = :sucursal ");
/* 1730:     */     }
/* 1731:2072 */     sql.append(" AND nd.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1732:2073 */     sql.append(" AND nd.estado != :estadoAnulado ");
/* 1733:2074 */     sql.append(" AND d.documentoBase = :documentoBaseNotaDebito ");
/* 1734:2075 */     sql.append(" GROUP BY" + criterioAgrupacion);
/* 1735:     */     
/* 1736:2077 */     Query query = this.em.createQuery(sql.toString());
/* 1737:2078 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1738:2079 */     query.setParameter("documentoBaseNotaDebito", DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 1739:2080 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 1740:2081 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 1741:2082 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1742:2083 */     if (empresa != null) {
/* 1743:2084 */       query.setParameter("empresa", empresa);
/* 1744:     */     }
/* 1745:2086 */     if (categoriaProducto != null) {
/* 1746:2087 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 1747:     */     }
/* 1748:2089 */     if (subcategoriaProducto != null) {
/* 1749:2090 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 1750:     */     }
/* 1751:2092 */     if (categoriaEmpresa != null) {
/* 1752:2093 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 1753:     */     }
/* 1754:2095 */     if (atributo != null)
/* 1755:     */     {
/* 1756:2096 */       query.setParameter("atributo", atributo);
/* 1757:2097 */       if (!valorAtributo.isEmpty()) {
/* 1758:2098 */         query.setParameter("valorAtributo", valorAtributo);
/* 1759:     */       }
/* 1760:     */     }
/* 1761:2101 */     if (sucursal != null) {
/* 1762:2102 */       query.setParameter("sucursal", sucursal);
/* 1763:     */     }
/* 1764:2105 */     return query.getResultList();
/* 1765:     */   }
/* 1766:     */   
/* 1767:     */   public String criterioAgrupacion(String tipoReporte)
/* 1768:     */   {
/* 1769:2110 */     StringBuilder criterioAgrupacion = new StringBuilder();
/* 1770:2111 */     if (tipoReporte.equals("cliente")) {
/* 1771:2112 */       criterioAgrupacion.append(" e.identificacion, e.nombreFiscal, cate.codigo, cate.nombre, s.codigo, s.nombre ");
/* 1772:     */     }
/* 1773:2114 */     if (tipoReporte.equals("producto"))
/* 1774:     */     {
/* 1775:2115 */       criterioAgrupacion.append(" p.codigo, p.nombre, COALESCE(bod.codigo, 'SBOD'), COALESCE(bod.nombre,'SIN BODEGA'), s.codigo, s.nombre, ");
/* 1776:2116 */       criterioAgrupacion.append(" p.codigoAlterno, p.volumen, subcat.nombre, subcat.codigo, cat.nombre, cat.codigo, uni.nombre, uni.codigo ");
/* 1777:     */     }
/* 1778:2118 */     if (tipoReporte.equals("factura")) {
/* 1779:2119 */       criterioAgrupacion.append(" e.identificacion, e.nombreFiscal, fc.numero, fc.fecha, COALESCE(dc.numero,''), dc.fecha,  s.codigo, s.nombre, CONCAT(ac.nombre2, ac.nombre1), c.nombre");
/* 1780:     */     }
/* 1781:2122 */     if (tipoReporte.equals("facturaProducto"))
/* 1782:     */     {
/* 1783:2123 */       criterioAgrupacion.append(" fc.numero, fc.fecha,p.codigo, p.nombre, e.identificacion, e.nombreFiscal,s.codigo, s.nombre, COALESCE(dc.numero,''), dc.fecha, ");
/* 1784:     */       
/* 1785:2125 */       criterioAgrupacion.append(" subcat.nombre, subcat.codigo, cat.nombre, cat.codigo, uni.nombre, uni.codigo,p.volumen, CONCAT(ac.nombre2, ac.nombre1), c.nombre ");
/* 1786:     */     }
/* 1787:2128 */     return criterioAgrupacion.toString();
/* 1788:     */   }
/* 1789:     */   
/* 1790:     */   public String criterioColumnas(String tipoReporte)
/* 1791:     */   {
/* 1792:2132 */     StringBuilder criterioColumnas = new StringBuilder();
/* 1793:2133 */     if (tipoReporte.equals("cliente"))
/* 1794:     */     {
/* 1795:2134 */       criterioColumnas.append(" e.identificacion, e.nombreFiscal, cate.codigo, cate.nombre, s.codigo, s.nombre, ");
/* 1796:2135 */       criterioColumnas.append(" '', '', '', '',  ");
/* 1797:2136 */       criterioColumnas.append(" '', '', '', '', ");
/* 1798:2137 */       criterioColumnas.append(" '', '', '', '', '', '', '', '', '','' ");
/* 1799:     */     }
/* 1800:2139 */     if (tipoReporte.equals("producto"))
/* 1801:     */     {
/* 1802:2140 */       criterioColumnas.append(" '', '', '', '', s.codigo, s.nombre, ");
/* 1803:2141 */       criterioColumnas.append(" p.codigo, p.nombre, COALESCE(bod.codigo, 'SBOD'), COALESCE(bod.nombre,'SIN BODEGA'),  ");
/* 1804:2142 */       criterioColumnas.append(" '', '', '', '', ");
/* 1805:2143 */       criterioColumnas
/* 1806:2144 */         .append(" p.codigoAlterno, p.volumen, subcat.nombre, subcat.codigo, cat.nombre, cat.codigo, uni.nombre, uni.codigo,'','' ");
/* 1807:     */     }
/* 1808:2146 */     if (tipoReporte.equals("factura"))
/* 1809:     */     {
/* 1810:2147 */       criterioColumnas.append(" e.identificacion, e.nombreFiscal,'','',  s.codigo, s.nombre, ");
/* 1811:2148 */       criterioColumnas.append(" '', '', '', '',  ");
/* 1812:2149 */       criterioColumnas.append(" fc.numero, fc.fecha, COALESCE(dc.numero,''), dc.fecha, ");
/* 1813:2150 */       criterioColumnas.append(" '', '', '', '', '', '', '', '',CONCAT(ac.nombre2, ac.nombre1), c.nombre");
/* 1814:     */     }
/* 1815:2152 */     if (tipoReporte.equals("facturaProducto"))
/* 1816:     */     {
/* 1817:2153 */       criterioColumnas.append(" e.identificacion, e.nombreFiscal, '', '', s.codigo, s.nombre, ");
/* 1818:2154 */       criterioColumnas.append(" p.codigo, p.nombre, '', '', ");
/* 1819:2155 */       criterioColumnas.append(" fc.numero, fc.fecha, COALESCE(dc.numero,''), dc.fecha, ");
/* 1820:2156 */       criterioColumnas.append(" '', p.volumen, subcat.nombre, subcat.codigo, cat.nombre, cat.codigo, uni.nombre, uni.codigo, CONCAT(ac.nombre2, ac.nombre1),c.nombre ");
/* 1821:     */     }
/* 1822:2159 */     return criterioColumnas.toString();
/* 1823:     */   }
/* 1824:     */   
/* 1825:     */   public List<Object[]> getReporteComportamientoCobroCliente(int idOrganizacion, Date fechaDesde, Date fechaHasta, CategoriaEmpresa categoriaEmpresa, Empresa empresa)
/* 1826:     */   {
/* 1827:2165 */     StringBuilder sql = new StringBuilder();
/* 1828:2166 */     sql.append(" SELECT emp.nombreFiscal, emp.identificacion, fc.fecha, fc.numero, ");
/* 1829:2167 */     sql.append(" cxc.fechaVencimiento, cob.fecha, dcfc.valor, re.nombre, ");
/* 1830:2168 */     sql.append(" concat(ac.nombre1,' ',ac.nombre2), fp.nombre, dfc.indicadorChequePosfechado, ");
/* 1831:2169 */     sql.append(" cob.numero, cob.estado, gc.fechaCobro ");
/* 1832:2170 */     sql.append(" FROM DetalleCobroFormaCobro dcfc ");
/* 1833:2171 */     sql.append(" INNER JOIN dcfc.detalleFormaCobro dfc ");
/* 1834:2172 */     sql.append(" INNER JOIN dfc.formaPago fp ");
/* 1835:2173 */     sql.append(" INNER JOIN dcfc.detalleCobro dc ");
/* 1836:2174 */     sql.append(" INNER JOIN dc.cuentaPorCobrar cxc ");
/* 1837:2175 */     sql.append(" INNER JOIN cxc.facturaCliente fc ");
/* 1838:2176 */     sql.append(" INNER JOIN fc.empresa emp ");
/* 1839:2177 */     sql.append(" INNER JOIN emp.categoriaEmpresa ce ");
/* 1840:2178 */     sql.append(" INNER JOIN dc.cobro cob ");
/* 1841:2179 */     sql.append(" LEFT JOIN cob.recaudador re ");
/* 1842:2180 */     sql.append(" LEFT JOIN fc.agenteComercial ac ");
/* 1843:2181 */     sql.append(" LEFT JOIN dfc.garantiaCliente gc ");
/* 1844:2182 */     sql.append(" WHERE cob.estado <> :estadoAnulado and cob.idOrganizacion = :idOrganizacion ");
/* 1845:2183 */     sql.append(" AND cob.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1846:2184 */     if (categoriaEmpresa != null) {
/* 1847:2185 */       sql.append(" AND ce.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 1848:     */     }
/* 1849:2187 */     if (empresa != null) {
/* 1850:2188 */       sql.append(" AND emp.idEmpresa = :idEmpresa ");
/* 1851:     */     }
/* 1852:2190 */     sql.append(" ORDER BY emp.identificacion ");
/* 1853:2191 */     Query query = this.em.createQuery(sql.toString());
/* 1854:2192 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 1855:2193 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1856:2194 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 1857:2195 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 1858:2196 */     if (categoriaEmpresa != null) {
/* 1859:2197 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getId()));
/* 1860:     */     }
/* 1861:2199 */     if (empresa != null) {
/* 1862:2200 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/* 1863:     */     }
/* 1864:2202 */     return query.getResultList();
/* 1865:     */   }
/* 1866:     */   
/* 1867:     */   public List<Object[]> getReporteVencimientoMensual(int idOrganizacion, Empresa empresa, Subempresa subempresa, Date fechaHasta, boolean fechaEmisionFactura)
/* 1868:     */   {
/* 1869:2209 */     String fecha = "YEAR(v.fechaVencimiento), MONTH(v.fechaVencimiento)";
/* 1870:2210 */     if (fechaEmisionFactura) {
/* 1871:2211 */       fecha = "YEAR(v.fechaFactura), MONTH(v.fechaFactura)";
/* 1872:     */     }
/* 1873:2213 */     StringBuilder sql = new StringBuilder();
/* 1874:2214 */     sql.append(" SELECT v.codigo, v.identificacion, v.nombreFiscal, ce.nombre, ");
/* 1875:2215 */     sql.append(" v.identificacionSubempresa, v.nombreFiscalSubempresa ,  ");
/* 1876:2216 */     sql.append(" v.numeroFactura," + fecha + ", SUM(v.debito - v.credito), fc.referencia2 ");
/* 1877:2217 */     sql.append(" FROM  FacturaCliente fc ");
/* 1878:2218 */     sql.append(" INNER JOIN fc.empresa em ");
/* 1879:2219 */     sql.append(" INNER JOIN em.categoriaEmpresa ce, VEstadoCuenta v ");
/* 1880:2220 */     sql.append(" WHERE  v.fechaDocumento <= :fechaHasta ");
/* 1881:2221 */     sql.append(" AND fc.idFacturaCliente = v.idFacturaCliente ");
/* 1882:2222 */     sql.append(" AND v.idOrganizacion = :idOrganizacion ");
/* 1883:2223 */     if (empresa != null) {
/* 1884:2224 */       sql.append(" AND em.idEmpresa = :idEmpresa ");
/* 1885:     */     }
/* 1886:2226 */     if ((null != subempresa) && (subempresa.getId() != 0)) {
/* 1887:2227 */       sql.append(" AND v.idSubempresa = :idSubempresa ");
/* 1888:     */     }
/* 1889:2229 */     sql.append(" \tGROUP BY v.codigo, v.identificacion, v.nombreFiscal, ce.nombre,v.identificacionSubempresa, v.nombreFiscalSubempresa ,v.numeroFactura, fc.referencia2, " + fecha + " ");
/* 1890:     */     
/* 1891:     */ 
/* 1892:2232 */     sql.append("    HAVING  SUM(v.debito - v.credito) <> 0  ");
/* 1893:2233 */     Query query = this.em.createQuery(sql.toString());
/* 1894:2234 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1895:2235 */     query.setParameter("fechaHasta", fechaHasta);
/* 1896:2236 */     if (empresa != null) {
/* 1897:2237 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getIdEmpresa()));
/* 1898:     */     }
/* 1899:2239 */     if ((null != subempresa) && (subempresa.getId() != 0)) {
/* 1900:2240 */       query.setParameter("idSubempresa", Integer.valueOf(subempresa.getId()));
/* 1901:     */     }
/* 1902:2242 */     return query.getResultList();
/* 1903:     */   }
/* 1904:     */   
/* 1905:     */   public List<Object[]> getReporteImpuestoVenta(Date fechaDesde, Date fechaHasta, Empresa cliente, CategoriaEmpresa categoriaEmpresa, EntidadUsuario agenteComercial, boolean anuladas, Canal canal, Zona zona, int idOrganizacion, DocumentoBase documentoBase, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, boolean indicadorResumen, boolean agrupadoPorCliente, Sucursal sucursal)
/* 1906:     */   {
/* 1907:2251 */     List<Object[]> result = new ArrayList();
/* 1908:2252 */     result.addAll(getValoresFacturaVentas(fechaDesde, fechaHasta, cliente, categoriaEmpresa, agenteComercial, anuladas, canal, zona, idOrganizacion, documentoBase, categoriaProducto, subcategoriaProducto, producto, indicadorResumen, agrupadoPorCliente, 0, sucursal));
/* 1909:     */     
/* 1910:2254 */     result.addAll(getValoresFacturaVentas(fechaDesde, fechaHasta, cliente, categoriaEmpresa, agenteComercial, anuladas, canal, zona, idOrganizacion, documentoBase, categoriaProducto, subcategoriaProducto, producto, indicadorResumen, agrupadoPorCliente, 1, sucursal));
/* 1911:     */     
/* 1912:2256 */     result.addAll(getValoresFacturaVentas(fechaDesde, fechaHasta, cliente, categoriaEmpresa, agenteComercial, anuladas, canal, zona, idOrganizacion, documentoBase, categoriaProducto, subcategoriaProducto, producto, indicadorResumen, agrupadoPorCliente, 2, sucursal));
/* 1913:     */     
/* 1914:2258 */     result.addAll(getValoresFacturaVentas(fechaDesde, fechaHasta, cliente, categoriaEmpresa, agenteComercial, anuladas, canal, zona, idOrganizacion, documentoBase, categoriaProducto, subcategoriaProducto, producto, indicadorResumen, agrupadoPorCliente, 3, sucursal));
/* 1915:     */     
/* 1916:2260 */     result.addAll(getValoresFacturaVentas(fechaDesde, fechaHasta, cliente, categoriaEmpresa, agenteComercial, anuladas, canal, zona, idOrganizacion, documentoBase, categoriaProducto, subcategoriaProducto, producto, indicadorResumen, agrupadoPorCliente, 4, sucursal));
/* 1917:     */     
/* 1918:     */ 
/* 1919:2263 */     String selectProduct = "";
/* 1920:2264 */     String selectFactura = "";
/* 1921:2265 */     if (agrupadoPorCliente)
/* 1922:     */     {
/* 1923:2266 */       selectProduct = "'', '', ''";
/* 1924:2267 */       if (indicadorResumen) {
/* 1925:2268 */         selectFactura = " '', '', ''";
/* 1926:     */       } else {
/* 1927:2270 */         selectFactura = " f.fecha, f.numero, ''";
/* 1928:     */       }
/* 1929:     */     }
/* 1930:     */     else
/* 1931:     */     {
/* 1932:2273 */       selectProduct = "cp.nombre, sp.nombre, p.nombre";
/* 1933:2274 */       if (indicadorResumen) {
/* 1934:2275 */         selectFactura = " '', '', '', mncc.nombre ";
/* 1935:     */       } else {
/* 1936:2277 */         selectFactura = " f.fecha, f.numero, df.idDetalleFacturaCliente, mncc.nombre ";
/* 1937:     */       }
/* 1938:     */     }
/* 1939:2280 */     StringBuilder sql2 = new StringBuilder();
/* 1940:2281 */     sql2.append("SELECT e.identificacion, e.nombreFiscal, ce.nombre, " + selectProduct + ", z.codigo,  z.nombre, c.codigo, c.nombre, CONCAT(ac.nombre2, ac.nombre1), fcSRI.autorizacion, fcSRI.claveAcceso,");
/* 1941:     */     
/* 1942:2283 */     sql2.append(" (CASE WHEN d.documentoBase = :documentoNotaCredito OR d.documentoBase = :documentoDevolucion THEN -1 ELSE 1 END)*");
/* 1943:2284 */     sql2.append(" (CASE WHEN i.tipoImpuesto = 0 THEN SUM((df.precioLinea-df.descuentoLinea+df.iceLinea)*(COALESCE(ipfc.porcentajeImpuesto,0)/100))");
/* 1944:     */     
/* 1945:2286 */     sql2.append(" ELSE SUM((df.cantidad)*(COALESCE(presp.cantidadUnidades,1))*(COALESCE(ipfc.porcentajeImpuesto,0))) END), ");
/* 1946:2287 */     sql2.append(" CONCAT('6', COALESCE(i.nombre, 'Sin Impuesto'))," + selectFactura);
/* 1947:2288 */     sql2.append(" FROM ImpuestoProductoFacturaCliente ipfc ");
/* 1948:2289 */     sql2.append(" RIGHT JOIN ipfc.impuesto i");
/* 1949:2290 */     sql2.append(" RIGHT JOIN ipfc.detalleFacturaCliente df ");
/* 1950:2291 */     sql2.append(" LEFT JOIN df.unidadVenta uv ");
/* 1951:2292 */     sql2.append(" LEFT OUTER JOIN df.facturaCliente f ");
/* 1952:2293 */     sql2.append(" LEFT OUTER JOIN f.motivoNotaCreditoCliente mncc ");
/* 1953:2294 */     sql2.append(" LEFT OUTER JOIN f.empresa e ");
/* 1954:2295 */     sql2.append(" LEFT OUTER JOIN e.categoriaEmpresa ce");
/* 1955:2296 */     sql2.append(" LEFT OUTER JOIN f.documento d ");
/* 1956:2297 */     sql2.append(" LEFT OUTER JOIN e.cliente cl ");
/* 1957:2298 */     sql2.append(" LEFT OUTER JOIN f.zona z ");
/* 1958:2299 */     sql2.append(" LEFT OUTER JOIN f.canal c ");
/* 1959:2300 */     sql2.append(" LEFT OUTER JOIN f.agenteComercial ac ");
/* 1960:2301 */     sql2.append(" LEFT OUTER JOIN df.producto p ");
/* 1961:2302 */     sql2.append(" LEFT JOIN p.presentacionProducto presp ");
/* 1962:2303 */     sql2.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/* 1963:2304 */     sql2.append(" LEFT OUTER JOIN sp.categoriaProducto cp ");
/* 1964:2305 */     sql2.append(" LEFT OUTER JOIN f.facturaClienteSRI fcSRI ");
/* 1965:2306 */     sql2.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1966:2307 */     if ((cliente != null) && (agrupadoPorCliente)) {
/* 1967:2308 */       sql2.append(" AND e = :cliente");
/* 1968:     */     }
/* 1969:2310 */     if ((categoriaEmpresa != null) && (agrupadoPorCliente)) {
/* 1970:2311 */       sql2.append(" AND ce = :categoriaEmpresa");
/* 1971:     */     }
/* 1972:2313 */     if (agenteComercial != null) {
/* 1973:2314 */       sql2.append(" AND ac = :agenteComercial");
/* 1974:     */     }
/* 1975:2316 */     if (zona != null) {
/* 1976:2317 */       sql2.append(" AND z = :zona");
/* 1977:     */     }
/* 1978:2319 */     if (canal != null) {
/* 1979:2320 */       sql2.append(" AND c = :canal");
/* 1980:     */     }
/* 1981:2322 */     if ((producto != null) && (!agrupadoPorCliente)) {
/* 1982:2323 */       sql2.append(" AND p = :producto");
/* 1983:     */     }
/* 1984:2325 */     if ((subcategoriaProducto != null) && (!agrupadoPorCliente)) {
/* 1985:2326 */       sql2.append(" AND sp = :subcategoriaProducto");
/* 1986:     */     }
/* 1987:2328 */     if ((categoriaProducto != null) && (!agrupadoPorCliente)) {
/* 1988:2329 */       sql2.append(" AND cp = :categoriaProducto");
/* 1989:     */     }
/* 1990:2331 */     if (sucursal != null) {
/* 1991:2332 */       sql2.append(" AND f.sucursal = :sucursal");
/* 1992:     */     }
/* 1993:2334 */     sql2.append(" AND ((df.precioLinea - df.descuentoLinea)) != 0 ");
/* 1994:2335 */     sql2.append(" AND f.idOrganizacion=:idOrganizacion ");
/* 1995:2338 */     if (documentoBase != null) {
/* 1996:2339 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/* 1997:2340 */         sql2.append(" AND d.documentoBase IN (:documentoBase,:documentoDevolucion) ");
/* 1998:     */       } else {
/* 1999:2342 */         sql2.append(" AND d.documentoBase = :documentoBase ");
/* 2000:     */       }
/* 2001:     */     }
/* 2002:2346 */     if (anuladas) {
/* 2003:2347 */       sql2.append(" AND f.estado = :estadoAnulado ");
/* 2004:     */     } else {
/* 2005:2349 */       sql2.append(" AND f.estado != :estadoAnulado ");
/* 2006:     */     }
/* 2007:2351 */     if (agrupadoPorCliente)
/* 2008:     */     {
/* 2009:2352 */       if (indicadorResumen)
/* 2010:     */       {
/* 2011:2353 */         sql2.append(" GROUP BY e.identificacion, e.nombreFiscal, ce.nombre, z.codigo,  z.nombre, c.codigo, c.nombre, CONCAT(ac.nombre2, ac.nombre1),");
/* 2012:     */         
/* 2013:2355 */         sql2.append(" i.nombre, i.tipoImpuesto, d.documentoBase, fcSRI.autorizacion, fcSRI.claveAcceso");
/* 2014:     */       }
/* 2015:     */       else
/* 2016:     */       {
/* 2017:2357 */         sql2.append(" GROUP BY e.identificacion, e.nombreFiscal, ce.nombre, z.codigo,  z.nombre, c.codigo, c.nombre, CONCAT(ac.nombre2, ac.nombre1),");
/* 2018:     */         
/* 2019:2359 */         sql2.append(" i.nombre, i.tipoImpuesto, f.fecha, f.numero, d.documentoBase, fcSRI.autorizacion, fcSRI.claveAcceso");
/* 2020:     */       }
/* 2021:     */     }
/* 2022:2362 */     else if (indicadorResumen)
/* 2023:     */     {
/* 2024:2363 */       sql2.append(" GROUP BY p.nombre, e.identificacion, e.nombreFiscal, ce.nombre, cp.nombre, sp.nombre, z.codigo,  z.nombre, c.codigo, c.nombre,");
/* 2025:     */       
/* 2026:2365 */       sql2.append(" CONCAT(ac.nombre2, ac.nombre1), i.nombre, i.tipoImpuesto, d.documentoBase, mncc.nombre, fcSRI.autorizacion, fcSRI.claveAcceso");
/* 2027:     */     }
/* 2028:     */     else
/* 2029:     */     {
/* 2030:2368 */       sql2.append(" GROUP BY p.nombre, e.identificacion, e.nombreFiscal, ce.nombre, cp.nombre, sp.nombre, z.codigo,  z.nombre, c.codigo, c.nombre,");
/* 2031:     */       
/* 2032:2370 */       sql2.append(" CONCAT(ac.nombre2, ac.nombre1), i.nombre, i.tipoImpuesto, f.fecha, f.numero, df.idDetalleFacturaCliente, d.documentoBase, mncc.nombre, fcSRI.autorizacion, fcSRI.claveAcceso");
/* 2033:     */     }
/* 2034:2374 */     if (agrupadoPorCliente)
/* 2035:     */     {
/* 2036:2375 */       if (indicadorResumen) {
/* 2037:2376 */         sql2.append(" ORDER BY e.nombreFiscal");
/* 2038:     */       } else {
/* 2039:2378 */         sql2.append(" ORDER BY f.fecha, f.numero, e.nombreFiscal");
/* 2040:     */       }
/* 2041:     */     }
/* 2042:2381 */     else if (indicadorResumen) {
/* 2043:2382 */       sql2.append(" ORDER BY p.nombre, e.nombreFiscal");
/* 2044:     */     } else {
/* 2045:2384 */       sql2.append(" ORDER BY f.fecha, p.nombre, e.nombreFiscal");
/* 2046:     */     }
/* 2047:2388 */     Query query = this.em.createQuery(sql2.toString());
/* 2048:2389 */     query.setParameter("fechaDesde", fechaDesde);
/* 2049:2390 */     query.setParameter("fechaHasta", fechaHasta);
/* 2050:2391 */     query.setParameter("documentoNotaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 2051:2392 */     query.setParameter("documentoDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
/* 2052:2393 */     if (cliente != null) {
/* 2053:2394 */       query.setParameter("cliente", cliente);
/* 2054:     */     }
/* 2055:2396 */     if (categoriaEmpresa != null) {
/* 2056:2397 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 2057:     */     }
/* 2058:2399 */     if (agenteComercial != null) {
/* 2059:2400 */       query.setParameter("agenteComercial", agenteComercial);
/* 2060:     */     }
/* 2061:2402 */     if (zona != null) {
/* 2062:2403 */       query.setParameter("zona", zona);
/* 2063:     */     }
/* 2064:2405 */     if (canal != null) {
/* 2065:2406 */       query.setParameter("canal", canal);
/* 2066:     */     }
/* 2067:2408 */     if (producto != null) {
/* 2068:2409 */       query.setParameter("producto", producto);
/* 2069:     */     }
/* 2070:2411 */     if (subcategoriaProducto != null) {
/* 2071:2412 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 2072:     */     }
/* 2073:2414 */     if (categoriaProducto != null) {
/* 2074:2415 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 2075:     */     }
/* 2076:2417 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 2077:2418 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 2078:2419 */     if (documentoBase != null)
/* 2079:     */     {
/* 2080:2420 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {}
/* 2081:2423 */       query.setParameter("documentoBase", documentoBase);
/* 2082:     */     }
/* 2083:2425 */     if (sucursal != null) {
/* 2084:2426 */       query.setParameter("sucursal", sucursal);
/* 2085:     */     }
/* 2086:2428 */     result.addAll(query.getResultList());
/* 2087:2429 */     return result;
/* 2088:     */   }
/* 2089:     */   
/* 2090:     */   public List<Object[]> getValoresFacturaVentas(Date fechaDesde, Date fechaHasta, Empresa cliente, CategoriaEmpresa categoriaEmpresa, EntidadUsuario agenteComercial, boolean anuladas, Canal canal, Zona zona, int idOrganizacion, DocumentoBase documentoBase, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, boolean indicadorResumen, boolean agrupadoPorCliente, int valor, Sucursal sucursal)
/* 2091:     */   {
/* 2092:2437 */     String selectProduct = "";
/* 2093:2438 */     if (agrupadoPorCliente) {
/* 2094:2439 */       selectProduct = "'', '', ''";
/* 2095:     */     } else {
/* 2096:2441 */       selectProduct = "cp.nombre, sp.nombre, p.nombre";
/* 2097:     */     }
/* 2098:2443 */     StringBuilder sql = new StringBuilder();
/* 2099:2444 */     sql.append("SELECT e.identificacion, e.nombreFiscal, ce.nombre, " + selectProduct);
/* 2100:2445 */     sql.append(", z.codigo,  z.nombre, c.codigo, c.nombre, CONCAT(ac.nombre2, ac.nombre1), fcSRI.autorizacion, fcSRI.claveAcceso, ");
/* 2101:2446 */     sql.append(" (CASE WHEN d.documentoBase = :documentoNotaCredito OR d.documentoBase = :documentoDevolucion THEN -1 ELSE 1 END)*");
/* 2102:2447 */     if (valor == 0) {
/* 2103:2448 */       sql.append(" (SUM(df.precioLinea)), '1Subtotal',");
/* 2104:     */     }
/* 2105:2450 */     if (valor == 1) {
/* 2106:2451 */       sql.append(" (-SUM(df.descuentoLinea)), '2Descuento',");
/* 2107:     */     }
/* 2108:2453 */     if (valor == 2) {
/* 2109:2454 */       sql.append(" (SUM(df.iceLinea)), '3ICE',");
/* 2110:     */     }
/* 2111:2456 */     if (valor == 3) {
/* 2112:2457 */       if (agrupadoPorCliente) {
/* 2113:2458 */         sql.append(" COALESCE(fcSRI.baseImponibleDiferenteCero,0), '4BaseImponibleDiferente0',");
/* 2114:     */       } else {
/* 2115:2460 */         sql.append(" COALESCE(fcSRI.baseImponibleDiferenteCero,0)*0, '4BaseImponibleDiferente0',");
/* 2116:     */       }
/* 2117:     */     }
/* 2118:2463 */     if (valor == 4) {
/* 2119:2464 */       if (agrupadoPorCliente) {
/* 2120:2465 */         sql.append(" COALESCE(fcSRI.baseImponibleTarifaCero,f.total) , '5BaseImponibleTarifa0',");
/* 2121:     */       } else {
/* 2122:2467 */         sql.append(" COALESCE(fcSRI.baseImponibleTarifaCero,0)*0, '5BaseImponibleTarifa0',");
/* 2123:     */       }
/* 2124:     */     }
/* 2125:2471 */     if (agrupadoPorCliente)
/* 2126:     */     {
/* 2127:2472 */       if (indicadorResumen) {
/* 2128:2473 */         sql.append(" '', '', ''");
/* 2129:     */       } else {
/* 2130:2475 */         sql.append(" f.fecha, f.numero, ''");
/* 2131:     */       }
/* 2132:     */     }
/* 2133:2478 */     else if (indicadorResumen) {
/* 2134:2479 */       sql.append(" '', '', '' , mncc.nombre");
/* 2135:     */     } else {
/* 2136:2481 */       sql.append(" f.fecha, f.numero, df.idDetalleFacturaCliente, mncc.nombre ");
/* 2137:     */     }
/* 2138:2484 */     sql.append(" FROM DetalleFacturaCliente df ");
/* 2139:2485 */     sql.append(" LEFT JOIN df.unidadVenta uv ");
/* 2140:2486 */     sql.append(" LEFT OUTER JOIN df.facturaCliente f ");
/* 2141:2487 */     sql.append(" LEFT OUTER JOIN f.motivoNotaCreditoCliente mncc ");
/* 2142:2488 */     sql.append(" LEFT OUTER JOIN f.empresa e ");
/* 2143:2489 */     sql.append(" LEFT OUTER JOIN e.categoriaEmpresa ce");
/* 2144:2490 */     sql.append(" LEFT OUTER JOIN f.documento d ");
/* 2145:2491 */     sql.append(" LEFT OUTER JOIN e.cliente cl ");
/* 2146:2492 */     sql.append(" LEFT OUTER JOIN f.zona z ");
/* 2147:2493 */     sql.append(" LEFT OUTER JOIN f.canal c ");
/* 2148:2494 */     sql.append(" LEFT OUTER JOIN f.agenteComercial ac ");
/* 2149:2495 */     sql.append(" LEFT OUTER JOIN f.facturaClienteSRI fcSRI ");
/* 2150:2496 */     sql.append(" LEFT OUTER JOIN df.producto p ");
/* 2151:2497 */     sql.append(" LEFT JOIN p.presentacionProducto presp ");
/* 2152:2498 */     sql.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/* 2153:2499 */     sql.append(" LEFT OUTER JOIN sp.categoriaProducto cp ");
/* 2154:2500 */     sql.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 2155:2501 */     if ((cliente != null) && (agrupadoPorCliente)) {
/* 2156:2502 */       sql.append(" AND e = :cliente");
/* 2157:     */     }
/* 2158:2504 */     if ((categoriaEmpresa != null) && (agrupadoPorCliente)) {
/* 2159:2505 */       sql.append(" AND ce = :categoriaEmpresa");
/* 2160:     */     }
/* 2161:2507 */     if (agenteComercial != null) {
/* 2162:2508 */       sql.append(" AND ac = :agenteComercial");
/* 2163:     */     }
/* 2164:2510 */     if (zona != null) {
/* 2165:2511 */       sql.append(" AND z = :zona");
/* 2166:     */     }
/* 2167:2513 */     if (canal != null) {
/* 2168:2514 */       sql.append(" AND c = :canal");
/* 2169:     */     }
/* 2170:2516 */     if ((producto != null) && (!agrupadoPorCliente)) {
/* 2171:2517 */       sql.append(" AND p = :producto");
/* 2172:     */     }
/* 2173:2519 */     if ((subcategoriaProducto != null) && (!agrupadoPorCliente)) {
/* 2174:2520 */       sql.append(" AND sp = :subcategoriaProducto");
/* 2175:     */     }
/* 2176:2522 */     if ((categoriaProducto != null) && (!agrupadoPorCliente)) {
/* 2177:2523 */       sql.append(" AND cp = :categoriaProducto");
/* 2178:     */     }
/* 2179:2525 */     if (sucursal != null) {
/* 2180:2526 */       sql.append(" AND f.sucursal = :sucursal");
/* 2181:     */     }
/* 2182:2528 */     sql.append(" AND ((df.precioLinea - df.descuentoLinea)) != 0 ");
/* 2183:2529 */     sql.append(" AND f.idOrganizacion=:idOrganizacion ");
/* 2184:2532 */     if (documentoBase != null) {
/* 2185:2533 */       if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/* 2186:2534 */         sql.append(" AND d.documentoBase IN (:documentoBase,:documentoDevolucion) ");
/* 2187:     */       } else {
/* 2188:2536 */         sql.append(" AND d.documentoBase = :documentoBase ");
/* 2189:     */       }
/* 2190:     */     }
/* 2191:2540 */     if (anuladas) {
/* 2192:2541 */       sql.append(" AND f.estado = :estadoAnulado ");
/* 2193:     */     } else {
/* 2194:2543 */       sql.append(" AND f.estado != :estadoAnulado ");
/* 2195:     */     }
/* 2196:2545 */     if (agrupadoPorCliente)
/* 2197:     */     {
/* 2198:2546 */       sql.append(" GROUP BY e.identificacion, e.nombreFiscal, ce.nombre, z.codigo,  z.nombre, c.codigo, c.nombre, CONCAT(ac.nombre2, ac.nombre1), d.documentoBase, fcSRI.baseImponibleDiferenteCero, fcSRI.baseImponibleTarifaCero, f.total, fcSRI.autorizacion, fcSRI.claveAcceso");
/* 2199:2548 */       if (!indicadorResumen) {
/* 2200:2549 */         sql.append(", f.fecha, f.numero, fcSRI.baseImponibleDiferenteCero, fcSRI.baseImponibleTarifaCero, f.total, fcSRI.autorizacion, fcSRI.claveAcceso");
/* 2201:     */       }
/* 2202:     */     }
/* 2203:     */     else
/* 2204:     */     {
/* 2205:2553 */       sql.append(" GROUP BY p.nombre, e.identificacion, e.nombreFiscal, ce.nombre, cp.nombre, sp.nombre, z.codigo,  z.nombre, c.codigo, c.nombre,");
/* 2206:     */       
/* 2207:2555 */       sql.append(" CONCAT(ac.nombre2, ac.nombre1), d.documentoBase , mncc.nombre, fcSRI.baseImponibleDiferenteCero, fcSRI.baseImponibleTarifaCero, f.total, fcSRI.autorizacion, fcSRI.claveAcceso");
/* 2208:2557 */       if (!indicadorResumen) {
/* 2209:2558 */         sql.append(", f.fecha, f.numero, df.idDetalleFacturaCliente, fcSRI.baseImponibleDiferenteCero, fcSRI.baseImponibleTarifaCero, f.total, fcSRI.autorizacion, fcSRI.claveAcceso");
/* 2210:     */       }
/* 2211:     */     }
/* 2212:2562 */     if (agrupadoPorCliente)
/* 2213:     */     {
/* 2214:2563 */       if (indicadorResumen) {
/* 2215:2564 */         sql.append(" ORDER BY e.nombreFiscal");
/* 2216:     */       } else {
/* 2217:2566 */         sql.append(" ORDER BY f.fecha, f.numero, e.nombreFiscal");
/* 2218:     */       }
/* 2219:     */     }
/* 2220:2569 */     else if (indicadorResumen) {
/* 2221:2570 */       sql.append(" ORDER BY p.nombre, e.nombreFiscal");
/* 2222:     */     } else {
/* 2223:2572 */       sql.append(" ORDER BY f.fecha, f.numero, p.nombre, e.nombreFiscal, mncc.nombre");
/* 2224:     */     }
/* 2225:2576 */     Query query1 = this.em.createQuery(sql.toString());
/* 2226:2577 */     query1.setParameter("fechaDesde", fechaDesde);
/* 2227:2578 */     query1.setParameter("fechaHasta", fechaHasta);
/* 2228:2579 */     query1.setParameter("documentoNotaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 2229:2580 */     query1.setParameter("documentoDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
/* 2230:2581 */     if (cliente != null) {
/* 2231:2582 */       query1.setParameter("cliente", cliente);
/* 2232:     */     }
/* 2233:2584 */     if (categoriaEmpresa != null) {
/* 2234:2585 */       query1.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 2235:     */     }
/* 2236:2587 */     if (agenteComercial != null) {
/* 2237:2588 */       query1.setParameter("agenteComercial", agenteComercial);
/* 2238:     */     }
/* 2239:2590 */     if (zona != null) {
/* 2240:2591 */       query1.setParameter("zona", zona);
/* 2241:     */     }
/* 2242:2593 */     if (canal != null) {
/* 2243:2594 */       query1.setParameter("canal", canal);
/* 2244:     */     }
/* 2245:2596 */     if (producto != null) {
/* 2246:2597 */       query1.setParameter("producto", producto);
/* 2247:     */     }
/* 2248:2599 */     if (subcategoriaProducto != null) {
/* 2249:2600 */       query1.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 2250:     */     }
/* 2251:2602 */     if (categoriaProducto != null) {
/* 2252:2603 */       query1.setParameter("categoriaProducto", categoriaProducto);
/* 2253:     */     }
/* 2254:2605 */     if (sucursal != null) {
/* 2255:2606 */       query1.setParameter("sucursal", sucursal);
/* 2256:     */     }
/* 2257:2608 */     query1.setParameter("estadoAnulado", Estado.ANULADO);
/* 2258:2609 */     query1.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 2259:2610 */     if (documentoBase != null) {
/* 2260:2611 */       query1.setParameter("documentoBase", documentoBase);
/* 2261:     */     }
/* 2262:2613 */     return query1.getResultList();
/* 2263:     */   }
/* 2264:     */   
/* 2265:     */   public Object[] getResumenVentaCliente(Date fechaDesde, Date fechaHasta, int idEmpresa)
/* 2266:     */   {
/* 2267:2618 */     StringBuilder sql2 = new StringBuilder();
/* 2268:2619 */     sql2.append(" SELECT SUM(CASE WHEN d.documentoBase=:devolucionCliente OR d.documentoBase=:notaCredito THEN (-1*(f.total - f.descuento)) ELSE (f.total - f.descuento) END), COUNT(f.idFacturaCliente) ");
/* 2269:     */     
/* 2270:2621 */     sql2.append(" FROM FacturaCliente f ");
/* 2271:2622 */     sql2.append(" INNER JOIN f.empresa e ");
/* 2272:2623 */     sql2.append(" INNER JOIN f.documento d ");
/* 2273:2624 */     sql2.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 2274:2625 */     sql2.append(" AND (e.idEmpresa = :idEmpresa OR :idEmpresa=0) ");
/* 2275:2626 */     sql2.append(" AND f.estado != :estadoAnulado ");
/* 2276:     */     
/* 2277:2628 */     Query query = this.em.createQuery(sql2.toString());
/* 2278:2629 */     query.setParameter("fechaDesde", fechaDesde);
/* 2279:2630 */     query.setParameter("fechaHasta", fechaHasta);
/* 2280:2631 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 2281:2632 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 2282:2633 */     query.setParameter("devolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
/* 2283:2634 */     query.setParameter("notaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 2284:     */     
/* 2285:2636 */     List<Object[]> lista = query.getResultList();
/* 2286:2637 */     Object[] resultado = null;
/* 2287:2638 */     if (!lista.isEmpty()) {
/* 2288:2639 */       resultado = (Object[])lista.get(0);
/* 2289:     */     }
/* 2290:2641 */     return resultado;
/* 2291:     */   }
/* 2292:     */   
/* 2293:     */   public List<Object[]> getResumenProductosMasVendidosCliente(Date fechaDesde, Date fechaHasta, int idEmpresa)
/* 2294:     */   {
/* 2295:2647 */     StringBuilder sql2 = new StringBuilder();
/* 2296:2648 */     sql2.append(" SELECT p.idProducto, p.codigo, p.nombre, SUM(CASE WHEN d.documentoBase=:devolucionCliente THEN (-1*(dfc.cantidad)) ELSE CASE WHEN d.documentoBase=:notaCredito THEN 0 ELSE (dfc.cantidad) END END), SUM(CASE WHEN d.documentoBase=:devolucionCliente OR d.documentoBase=:notaCredito THEN (-1*(dfc.precioLinea - dfc.descuentoLinea)) ELSE (dfc.precioLinea - dfc.descuentoLinea) END) ");
/* 2297:     */     
/* 2298:2650 */     sql2.append(" FROM DetalleFacturaCliente dfc ");
/* 2299:2651 */     sql2.append(" INNER JOIN dfc.producto p ");
/* 2300:2652 */     sql2.append(" INNER JOIN dfc.facturaCliente f ");
/* 2301:2653 */     sql2.append(" INNER JOIN f.empresa e ");
/* 2302:2654 */     sql2.append(" INNER JOIN f.documento d ");
/* 2303:2655 */     sql2.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 2304:2656 */     sql2.append(" AND (e.idEmpresa = :idEmpresa OR :idEmpresa=0) ");
/* 2305:2657 */     sql2.append(" AND f.estado != :estadoAnulado ");
/* 2306:2658 */     sql2.append(" GROUP BY p.idProducto, p.codigo, p.nombre ");
/* 2307:2659 */     sql2.append(" ORDER BY SUM(CASE WHEN d.documentoBase=:devolucionCliente THEN (-1*(dfc.cantidad)) ELSE CASE WHEN d.documentoBase=:notaCredito THEN 0 ELSE (dfc.cantidad) END END) DESC ");
/* 2308:     */     
/* 2309:     */ 
/* 2310:2662 */     Query query = this.em.createQuery(sql2.toString());
/* 2311:2663 */     query.setParameter("fechaDesde", fechaDesde);
/* 2312:2664 */     query.setParameter("fechaHasta", fechaHasta);
/* 2313:2665 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 2314:2666 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 2315:2667 */     query.setParameter("devolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
/* 2316:2668 */     query.setParameter("notaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 2317:2669 */     query.setMaxResults(50);
/* 2318:     */     
/* 2319:2671 */     return query.getResultList();
/* 2320:     */   }
/* 2321:     */   
/* 2322:     */   public List<Object[]> getDetalleVentasProductoCliente(Date fechaDesde, Date fechaHasta, int idEmpresa, int idProducto)
/* 2323:     */   {
/* 2324:2677 */     StringBuilder sql2 = new StringBuilder();
/* 2325:2678 */     sql2.append(" SELECT f.fecha, SUM(CASE WHEN d.documentoBase=:devolucionCliente THEN (-1*(dfc.cantidad)) ELSE CASE WHEN d.documentoBase=:notaCredito THEN 0 ELSE (dfc.cantidad) END END), SUM(CASE WHEN d.documentoBase=:devolucionCliente OR d.documentoBase=:notaCredito THEN (-1*(dfc.precioLinea - dfc.descuentoLinea)) ELSE (dfc.precioLinea - dfc.descuentoLinea) END) ");
/* 2326:     */     
/* 2327:2680 */     sql2.append(" FROM DetalleFacturaCliente dfc ");
/* 2328:2681 */     sql2.append(" INNER JOIN dfc.producto p ");
/* 2329:2682 */     sql2.append(" INNER JOIN dfc.facturaCliente f ");
/* 2330:2683 */     sql2.append(" INNER JOIN f.empresa e ");
/* 2331:2684 */     sql2.append(" INNER JOIN f.documento d ");
/* 2332:2685 */     sql2.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 2333:2686 */     sql2.append(" AND (e.idEmpresa = :idEmpresa OR :idEmpresa=0) ");
/* 2334:2687 */     sql2.append(" AND f.estado != :estadoAnulado ");
/* 2335:2688 */     sql2.append(" AND p.idProducto =:idProducto  ");
/* 2336:2689 */     sql2.append(" GROUP BY f.fecha ");
/* 2337:2690 */     sql2.append(" ORDER BY f.fecha ASC ");
/* 2338:     */     
/* 2339:2692 */     Query query = this.em.createQuery(sql2.toString());
/* 2340:2693 */     query.setParameter("fechaDesde", fechaDesde);
/* 2341:2694 */     query.setParameter("fechaHasta", fechaHasta);
/* 2342:2695 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 2343:2696 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/* 2344:2697 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 2345:2698 */     query.setParameter("devolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
/* 2346:2699 */     query.setParameter("notaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 2347:     */     
/* 2348:2701 */     return query.getResultList();
/* 2349:     */   }
/* 2350:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.ventas.ReporteVentaDao
 * JD-Core Version:    0.7.0.1
 */