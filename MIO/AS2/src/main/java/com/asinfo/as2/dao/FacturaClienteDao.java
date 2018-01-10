/*    1:     */ package com.asinfo.as2.dao;

/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*    4:     */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*    5:     */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*    6:     */ import com.asinfo.as2.entities.Asiento;
/*    7:     */ import com.asinfo.as2.entities.Bodega;
/*    8:     */ import com.asinfo.as2.entities.Canal;
/*    9:     */ import com.asinfo.as2.entities.ContratoVenta;
/*   10:     */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   11:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   12:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   13:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   14:     */ import com.asinfo.as2.entities.DetalleValoresContratoVenta;
/*   15:     */ import com.asinfo.as2.entities.Documento;
/*   16:     */ import com.asinfo.as2.entities.Empresa;
/*   17:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   18:     */ import com.asinfo.as2.entities.HojaRuta;
/*   19:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   20:     */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   21:     */ import com.asinfo.as2.entities.MotivoAnulacion;
/*   22:     */ import com.asinfo.as2.entities.Producto;
/*   23:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   24:     */ import com.asinfo.as2.entities.Secuencia;
/*   25:     */ import com.asinfo.as2.entities.Subempresa;
/*   26:     */ import com.asinfo.as2.entities.Sucursal;
/*   27:     */ import com.asinfo.as2.entities.Transportista;
/*   28:     */ import com.asinfo.as2.entities.Zona;
/*   29:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   30:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   31:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   32:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   33:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   34:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   35:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*   36:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   37:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   38:     */ import com.asinfo.as2.util.AppUtil;
/*   39:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   40:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   41:     */ import java.io.PrintStream;
/*   42:     */ import java.math.BigDecimal;
/*   43:     */ import java.math.RoundingMode;
/*   44:     */ import java.util.ArrayList;
/*   45:     */ import java.util.Collection;
/*   46:     */ import java.util.Date;
/*   47:     */ import java.util.HashMap;
/*   48:     */ import java.util.HashSet;
/*   49:     */ import java.util.Iterator;
/*   50:     */ import java.util.List;
/*   51:     */ import java.util.Map;
/*   52:     */ import java.util.Set;
/*   53:     */ import javax.ejb.EJB;
/*   54:     */ import javax.ejb.Stateless;
/*   55:     */ import javax.persistence.EntityManager;
/*   56:     */ import javax.persistence.NoResultException;
/*   57:     */ import javax.persistence.Query;
/*   58:     */ import javax.persistence.TemporalType;
/*   59:     */ import javax.persistence.TypedQuery;
/*   60:     */ import javax.persistence.criteria.CriteriaBuilder;
/*   61:     */ import javax.persistence.criteria.CriteriaQuery;
/*   62:     */ import javax.persistence.criteria.Expression;
/*   63:     */ import javax.persistence.criteria.Fetch;
/*   64:     */ import javax.persistence.criteria.Join;
/*   65:     */ import javax.persistence.criteria.JoinType;
/*   66:     */ import javax.persistence.criteria.Path;
/*   67:     */ import javax.persistence.criteria.Predicate;
/*   68:     */ import javax.persistence.criteria.Root;
/*   69:     */ import javax.persistence.criteria.Selection;

/*   70:     */
/*   71:     */ @Stateless
/* 72: */ public class FacturaClienteDao/* 73: */ extends AbstractDaoAS2<FacturaCliente>
/* 74: */ {
	/* 75: */ @EJB
	/* 76: */ private transient DetalleFacturaClienteDao detalleFacturaClienteDao;
	/* 77: */ @EJB
	/* 78: */ private ServicioGenerico<FacturaClienteSRI> servicioFacturaClienteSRI;
	/* 79: */ @EJB
	/* 80: */ protected transient ServicioFacturaCliente servicioFacturaCliente;
	/* 81: */ @EJB
	/* 82: */ private transient ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
	/* 83: */ @EJB
	/* 84: */ private transient ProductoDao productoDao;
	/* 85: */ @EJB
	/* 86: */ private transient AsientoDao asientoDao;

	/* 87: */
	/* 88: */ public FacturaClienteDao()
	/* 89: */ {
		/* 90: 102 */ super(FacturaCliente.class);
		/* 91: */ }

	/* 92: */
	/* 93: */ public FacturaCliente cargarDetalle(int idFacturaCliente)
	/* 94: */ {
		/* 95: 106 */ return cargarDetalle(idFacturaCliente, true);
		/* 96: */ }

	/* 97: */
	/* 98: */ public FacturaCliente cargarDetalle(int idFacturaCliente, boolean cargarDetalles)
	/* 99: */ {
		/* 100: 111 */ CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		/* 101: */
		/* 102: */
		/* 103: 114 */ CriteriaQuery<FacturaCliente> cqCabecera = criteriaBuilder.createQuery(FacturaCliente.class);
		/* 104: 115 */ Root<FacturaCliente> fromCabecera = cqCabecera.from(FacturaCliente.class);
		/* 105: */
		/* 106: 117 */ fromCabecera.fetch("sucursal", JoinType.LEFT);
		/* 107: 118 */ fromCabecera.fetch("formaPago", JoinType.LEFT);
		/* 108: 119 */ fromCabecera.fetch("condicionPago", JoinType.LEFT);
		/* 109: 120 */ fromCabecera.fetch("zona", JoinType.LEFT);
		/* 110: 121 */ fromCabecera.fetch("canal", JoinType.LEFT);
		/* 111: 122 */ fromCabecera.fetch("agenteComercial", JoinType.LEFT);
		/* 112: 123 */ fromCabecera.fetch("motivoNotaCreditoCliente", JoinType.LEFT);
		/* 113: 124 */ fromCabecera.fetch("pedidoCliente", JoinType.LEFT);
		/* 114: 125 */ fromCabecera.fetch("proyecto", JoinType.LEFT);
		/* 115: */
		/* 116: 127 */ fromCabecera.fetch("lugarIncoterm", JoinType.LEFT);
		/* 117: 128 */ fromCabecera.fetch("paisOrigen", JoinType.LEFT);
		/* 118: 129 */ fromCabecera.fetch("paisAdquisicion", JoinType.LEFT);
		/* 119: 130 */ fromCabecera.fetch("puertoEmbarque", JoinType.LEFT);
		/* 120: 131 */ fromCabecera.fetch("paisDestino", JoinType.LEFT);
		/* 121: 132 */ fromCabecera.fetch("puertoDestino", JoinType.LEFT);
		/* 122: 133 */ fromCabecera.fetch("asiento", JoinType.LEFT);
		/* 123: */
		/* 124: 135 */ Fetch<Object, Object> facturaClienteSRI = fromCabecera.fetch("facturaClienteSRI", JoinType.LEFT);
		/* 125: 136 */ facturaClienteSRI.fetch("tipoComprobanteSRI", JoinType.LEFT);
		/* 126: */
		/* 127: 138 */ Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
		/* 128: 139 */ documento.fetch("secuencia", JoinType.LEFT);
		/* 129: 140 */ documento.fetch("tipoAsiento", JoinType.LEFT);
		/* 130: */
		/* 131: 142 */ Fetch<Object, Object> facturaClientePadre = fromCabecera.fetch("facturaClientePadre",
				JoinType.LEFT);
		/* 132: 143 */ facturaClientePadre.fetch("empresa", JoinType.LEFT);
		/* 133: 144 */ facturaClientePadre.fetch("documento", JoinType.LEFT);
		/* 134: */
		/* 135: 146 */ Fetch<Object, Object> subempresa = fromCabecera.fetch("subempresa", JoinType.LEFT);
		/* 136: 147 */ subempresa.fetch("empresa", JoinType.LEFT);
		/* 137: */
		/* 138: 149 */ Fetch<Object, Object> despachoCliente = fromCabecera.fetch("despachoCliente", JoinType.LEFT);
		/* 139: 150 */ despachoCliente.fetch("tipoOrdenDespacho", JoinType.LEFT);
		/* 140: 151 */ despachoCliente.fetch("transportista", JoinType.LEFT);
		/* 141: */
		/* 142: 153 */ Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.LEFT);
		/* 143: 154 */ Fetch<Object, Object> cliente = empresa.fetch("cliente", JoinType.LEFT);
		/* 144: 155 */ cliente.fetch("tipoOrdenDespacho", JoinType.LEFT);
		/* 145: 156 */ cliente.fetch("listaPrecios", JoinType.LEFT);
		/* 146: 157 */ cliente.fetch("listaDescuentos", JoinType.LEFT);
		/* 147: */
		/* 148: 159 */ empresa.fetch("tipoIdentificacion", JoinType.LEFT);
		/* 149: 160 */ empresa.fetch("categoriaEmpresa", JoinType.LEFT);
		/* 150: */
		/* 151: 162 */ Fetch<Object, Object> direccion = fromCabecera.fetch("direccionEmpresa", JoinType.LEFT);
		/* 152: 163 */ direccion.fetch("ubicacion", JoinType.LEFT);
		/* 153: 164 */ direccion.fetch("ciudad", JoinType.LEFT);
		/* 154: */
		/* 155: 166 */ Path<Integer> pathId = fromCabecera.get("idFacturaCliente");
		/* 156: 167 */ cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idFacturaCliente)));
		/* 157: 168 */ CriteriaQuery<FacturaCliente> selectPedido = cqCabecera.select(fromCabecera);
		/* 158: */
		/* 159: 170 */ Fetch<Object, Object> asiento = fromCabecera.fetch("asiento", JoinType.LEFT);
		/* 160: 171 */ asiento.fetch("listaDetalleAsiento", JoinType.LEFT);
		/* 161: 172 */ asiento.fetch("tipoAsiento", JoinType.LEFT);
		/* 162: */
		/* 163: 174 */ FacturaCliente facturaCliente = (FacturaCliente) this.em.createQuery(selectPedido)
				.getSingleResult();
		/* 164: 176 */ if (cargarDetalles)
		/* 165: */ {
			/* 166: 178 */ facturaCliente.getListaPrefacturaCliente().size();
			/* 167: 179 */ this.em.detach(facturaCliente);
			/* 168: */
			/* 169: */
			/* 170: 182 */ CriteriaQuery<CuentaPorCobrar> cqCXC = criteriaBuilder.createQuery(CuentaPorCobrar.class);
			/* 171: 183 */ Root<CuentaPorCobrar> fromCXC = cqCXC.from(CuentaPorCobrar.class);
			/* 172: */
			/* 173: 185 */ cqCXC.where(criteriaBuilder.equal(fromCXC.join("facturaCliente"), facturaCliente));
			/* 174: 186 */ CriteriaQuery<CuentaPorCobrar> selectCXC = cqCXC.select(fromCXC);
			/* 175: */
			/* 176: 188 */ List<CuentaPorCobrar> listaCXC = this.em.createQuery(selectCXC).getResultList();
			/* 177: 189 */ for (CuentaPorCobrar cxc : listaCXC)
			/* 178: */ {
				/* 179: 190 */ this.em.detach(cxc);
				/* 180: 191 */ cxc.setTraValorCobrado(cxc.getValor().subtract(cxc.getSaldo()));
				/* 181: 192 */ cxc.setFacturaCliente(facturaCliente);
				/* 182: */ }
			/* 183: 194 */ facturaCliente.setListaCuentaPorCobrar(listaCXC);
			/* 184: */
			/* 185: */
			/* 186: 197 */ Object cqDetalle = criteriaBuilder.createQuery(DetalleFacturaCliente.class);
			/* 187: 198 */ Root<DetalleFacturaCliente> fromDetalle = ((CriteriaQuery) cqDetalle)
					.from(DetalleFacturaCliente.class);
			/* 188: 199 */ fromDetalle.fetch("unidadVenta", JoinType.LEFT);
			/* 189: 200 */ fromDetalle.fetch("bodega", JoinType.LEFT);
			/* 190: 201 */ Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
			/* 191: 202 */ producto.fetch("subcategoriaProducto", JoinType.LEFT);
			/* 192: 203 */ producto.fetch("categoriaImpuesto", JoinType.LEFT);
			/* 193: 204 */ producto.fetch("presentacionProducto", JoinType.LEFT);
			/* 194: 205 */ fromDetalle.fetch("detalleFacturaClientePadre", JoinType.LEFT)
					.fetch("detalleDespachoCliente", JoinType.LEFT);
			/* 195: 206 */ Fetch<Object, Object> contratoVentaFacturaContratoVenta = fromDetalle
					.fetch("contratoVentaFacturaContratoVenta", JoinType.LEFT);
			/* 196: 207 */ Fetch<Object, Object> detallesFacturaContratoVenta = contratoVentaFacturaContratoVenta
					.fetch("detallesFacturaContratoVenta", JoinType.LEFT);
			/* 197: */
			/* 198: 209 */ detallesFacturaContratoVenta.fetch("contratoVenta", JoinType.LEFT);
			/* 199: 210 */ Fetch<Object, Object> detalleDespachoCliente = fromDetalle.fetch("detalleDespachoCliente",
					JoinType.LEFT);
			/* 200: 211 */ detalleDespachoCliente.fetch("producto", JoinType.LEFT);
			/* 201: 212 */ detalleDespachoCliente.fetch("despachoCliente", JoinType.LEFT).fetch("tipoOrdenDespacho",
					JoinType.LEFT);
			/* 202: */
			/* 203: 214 */ ((CriteriaQuery) cqDetalle)
					.where(criteriaBuilder.equal(fromDetalle.join("facturaCliente"), facturaCliente));
			/* 204: 215 */ CriteriaQuery<DetalleFacturaCliente> selectFactura = ((CriteriaQuery) cqDetalle)
					.select(fromDetalle);
			/* 205: */
			/* 206: 217 */ List<DetalleFacturaCliente> listaDetalleFacturaCliente = this.em.createQuery(selectFactura)
					.getResultList();
			/* 207: 218 */ facturaCliente.setListaDetalleFacturaCliente(listaDetalleFacturaCliente);
			/* 208: 221 */ for (DetalleFacturaCliente detalleFacturaCliente : listaDetalleFacturaCliente)
			/* 209: */ {
				/* 210: 222 */ this.em.detach(detalleFacturaCliente);
				/* 211: 223 */ detalleFacturaCliente.setFacturaCliente(facturaCliente);
				/* 212: 224 */ Integer idDetalleFacturaCliente = Integer.valueOf(detalleFacturaCliente.getId());
				/* 213: */
				/* 214: 226 */ CriteriaQuery<ImpuestoProductoFacturaCliente> cqImpuesto = criteriaBuilder
						.createQuery(ImpuestoProductoFacturaCliente.class);
				/* 215: 227 */ Root<ImpuestoProductoFacturaCliente> fromImpuesto = cqImpuesto
						.from(ImpuestoProductoFacturaCliente.class);
				/* 216: */
				/* 217: 229 */ fromImpuesto.fetch("impuesto", JoinType.LEFT);
				/* 218: */
				/* 219: 231 */ Path<Integer> pathIdImpuesto = fromImpuesto.join("detalleFacturaCliente")
						.get("idDetalleFacturaCliente");
				/* 220: 232 */ cqImpuesto.where(criteriaBuilder.equal(pathIdImpuesto, idDetalleFacturaCliente));
				/* 221: */
				/* 222: 234 */ CriteriaQuery<ImpuestoProductoFacturaCliente> selectImpuesto = cqImpuesto
						.select(fromImpuesto);
				/* 223: */
				/* 224: 236 */ List<ImpuestoProductoFacturaCliente> listaImpuestoProductoFacturaCliente = this.em
						.createQuery(selectImpuesto).getResultList();
				/* 225: */
				/* 226: 238 */ detalleFacturaCliente
						.setListaImpuestoProductoFacturaCliente(listaImpuestoProductoFacturaCliente);
				/* 227: */ }
			/* 228: 242 */ facturaCliente.setListaDetalleFacturaClienteComercializadora(new ArrayList());
			/* 229: 243 */ if ((facturaCliente.getAsiento() != null)
					&& (facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.FACTURA_CLIENTE))) {
				/* 230: 244 */ facturaCliente
						.setAsiento(this.asientoDao.cargarDetalle(facturaCliente.getAsiento().getId()));
				/* 231: */ }
			/* 232: */ }
		/* 233: 247 */ return facturaCliente;
		/* 234: */ }

	/* 235: */
	/* 236: */ public List<DetalleFacturaCliente> cargarDetalleFactura(int idFacturaCliente)
	/* 237: */ {
		/* 238: 261 */ CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		/* 239: 262 */ CriteriaQuery<DetalleFacturaCliente> cqDetalle = criteriaBuilder
				.createQuery(DetalleFacturaCliente.class);
		/* 240: 263 */ Root<DetalleFacturaCliente> from = cqDetalle.from(DetalleFacturaCliente.class);
		/* 241: */
		/* 242: 265 */ from.fetch("detallePedidoCliente", JoinType.LEFT);
		/* 243: 266 */ from.fetch("detalleDespachoCliente", JoinType.LEFT);
		/* 244: 267 */ Fetch<Object, Object> contratoVentaFacturaContratoVenta = from
				.fetch("contratoVentaFacturaContratoVenta", JoinType.LEFT);
		/* 245: 268 */ contratoVentaFacturaContratoVenta.fetch("detallesFacturaContratoVenta", JoinType.LEFT);
		/* 246: */
		/* 247: 270 */ Path<Integer> pathIdDetalle = from.join("facturaCliente").get("idFacturaCliente");
		/* 248: 271 */ cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idFacturaCliente)));
		/* 249: 272 */ CriteriaQuery<DetalleFacturaCliente> select = cqDetalle.select(from);
		/* 250: */
		/* 251: 274 */ return this.em.createQuery(select).getResultList();
		/* 252: */ }

	/* 253: */
	/* 254: */ public List<DetalleFacturaCliente> cargarDetalleNotaCreditoDevolucion(int idFacturaCliente)
	/* 255: */ {
		/* 256: 287 */ CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		/* 257: 288 */ CriteriaQuery<DetalleFacturaCliente> cqDetalle = criteriaBuilder
				.createQuery(DetalleFacturaCliente.class);
		/* 258: 289 */ Root<DetalleFacturaCliente> from = cqDetalle.from(DetalleFacturaCliente.class);
		/* 259: */
		/* 260: 291 */ from.fetch("detalleFacturaClientePadre", JoinType.LEFT).fetch("detalleDespachoCliente",
				JoinType.LEFT);
		/* 261: 292 */ Fetch<Object, Object> contratoVentaFacturaContratoVenta = from
				.fetch("contratoVentaFacturaContratoVenta", JoinType.LEFT);
		/* 262: 293 */ contratoVentaFacturaContratoVenta.fetch("detallesFacturaContratoVenta", JoinType.LEFT);
		/* 263: */
		/* 264: 295 */ Path<Integer> pathIdDetalle = from.join("facturaCliente").get("idFacturaCliente");
		/* 265: 296 */ cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idFacturaCliente)));
		/* 266: 297 */ CriteriaQuery<DetalleFacturaCliente> select = cqDetalle.select(from);
		/* 267: */
		/* 268: 299 */ return this.em.createQuery(select).getResultList();
		/* 269: */ }

	/* 270: */
	/* 271: */ public List<CuentaPorCobrar> obtenerFacturasPendientes(int idEmpresa, int idFacturaCliente,
			String numeroFactura)
	/* 272: */ {
		/* 273: 310 */ StringBuilder sql = new StringBuilder();
		/* 274: 311 */ sql.append("SELECT cxc FROM CuentaPorCobrar cxc LEFT JOIN FETCH cxc.facturaCliente fc");
		/* 275: 312 */ sql.append(
				" WHERE (fc.empresa.idEmpresa=:idEmpresa OR :idEmpresa = 0)  AND cxc.saldo-cxc.valorBloqueado > 0 ");
		/* 276: 313 */ sql.append(" AND fc.estado<>:estado ");
		/* 277: 314 */ if ((numeroFactura != null) && (!numeroFactura.isEmpty())) {
			/* 278: 315 */ sql.append(" AND fc.numero like :numeroFactura");
			/* 279: */ }
		/* 280: 317 */ sql.append(
				" AND (fc.idFacturaCliente=:idFacturaCliente OR :idFacturaCliente=0) AND cxc.indicadorAnulada = false");
		/* 281: 318 */ sql.append(" ORDER BY fc.fecha, cxc.numeroCuota ASC, cxc.saldo DESC ");
		/* 282: 319 */ Query query = this.em.createQuery(sql.toString());
		/* 283: 320 */ query.setParameter("estado", Estado.ANULADO);
		/* 284: 321 */ query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
		/* 285: 322 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 286: 323 */ if ((numeroFactura != null) && (!numeroFactura.isEmpty())) {
			/* 287: 324 */ query.setParameter("numeroFactura", "%" + numeroFactura + "%");
			/* 288: */ }
		/* 289: 326 */ return query.getResultList();
		/* 290: */ }

	/* 291: */
	/* 292: */ public List<DetalleFacturaCliente> getListaDetalleFacturaPorDespachar(int idFacturaCliente)
	/* 293: */ {
		/* 294: 340 */ CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		/* 295: 341 */ CriteriaQuery<DetalleFacturaCliente> criteriaQuery = criteriaBuilder
				.createQuery(DetalleFacturaCliente.class);
		/* 296: 342 */ Root<DetalleFacturaCliente> from = criteriaQuery.from(DetalleFacturaCliente.class);
		/* 297: */
		/* 298: 344 */ from.fetch("unidadVenta", JoinType.LEFT);
		/* 299: 345 */ Fetch<Object, Object> producto = from.fetch("producto", JoinType.LEFT);
		/* 300: 346 */ producto.fetch("categoriaImpuesto", JoinType.LEFT);
		/* 301: */
		/* 302: 348 */ CriteriaQuery<DetalleFacturaCliente> select = criteriaQuery.select(from);
		/* 303: */
		/* 304: 350 */ List<Expression> predicates = new ArrayList();
		/* 305: 351 */ predicates.add(criteriaBuilder.equal(from.join("facturaCliente").get("idFacturaCliente"),
				Integer.valueOf(idFacturaCliente)));
		/* 306: 352 */ predicates.add(
				criteriaBuilder.greaterThan(from.get("cantidadPorDespachar").as(BigDecimal.class), BigDecimal.ZERO));
		/* 307: */
		/* 308: 354 */ criteriaQuery.where((Predicate[]) predicates.toArray(new Predicate[predicates.size()]));
		/* 309: */
		/* 310: 356 */ return this.em.createQuery(select).getResultList();
		/* 311: */ }

	/* 312: */
	/* 313: */ public List<FacturaCliente> listaFacturasPorDespachar(Estado estadoAprobado, int idEmpresa)
	/* 314: */ {
		/* 315: 370 */ Query query = this.em.createQuery(
				"SELECT new FacturaCliente(f.idFacturaCliente,f.numero,f.fecha)  FROM FacturaCliente f   WHERE f.estado>=:estadoAprobado  AND f.estado!=:estado  AND f.empresa.idEmpresa=:idEmpresa AND f.documento.documentoBase=:documentoBase AND EXISTS (SELECT 1 FROM DetalleFacturaCliente dfc WHERE dfc.facturaCliente.idFacturaCliente = f.idFacturaCliente AND dfc.cantidadPorDespachar>0) ORDER BY f.numero DESC");
		/* 316: */
		/* 317: */
		/* 318: */
		/* 319: */
		/* 320: 375 */ query.setParameter("estadoAprobado", estadoAprobado);
		/* 321: 376 */ query.setParameter("estado", Estado.ANULADO);
		/* 322: 377 */ query.setParameter("documentoBase", DocumentoBase.FACTURA_CLIENTE);
		/* 323: 378 */ query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
		/* 324: 379 */ return query.getResultList();
		/* 325: */ }

	/* 326: */
	/* 327: */ public List<FacturaCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField,
			boolean sortOrder, Map<String, String> filters)
	/* 328: */ {
		/* 329: 390 */ List<FacturaCliente> listaFacturaCliente = new ArrayList();
		/* 330: */
		/* 331: 392 */ CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		/* 332: 393 */ CriteriaQuery<FacturaCliente> criteriaQuery = criteriaBuilder.createQuery(FacturaCliente.class);
		/* 333: 394 */ Root<FacturaCliente> from = criteriaQuery.from(FacturaCliente.class);
		/* 334: 395 */ Join<Object, Object> despachoCliente = from.join("despachoCliente", JoinType.LEFT);
		/* 335: 396 */ Join<Object, Object> pedidoCliente = from.join("pedidoCliente", JoinType.LEFT);
		/* 336: 397 */ Join<Object, Object> facturaClienteSRI = from.join("facturaClienteSRI", JoinType.LEFT);
		/* 337: 398 */ Join<Object, Object> documento = from.join("documento", JoinType.LEFT);
		/* 338: 399 */ Join<Object, Object> direccionEmpresa = from.join("direccionEmpresa", JoinType.LEFT);
		/* 339: 400 */ Join<Object, Object> anticipoCliente = from.join("anticipoCliente", JoinType.LEFT);
		/* 340: 401 */ Join<Object, Object> facturaClientePadre = from.join("facturaClientePadre", JoinType.LEFT);
		/* 341: 402 */ Join<Object, Object> proyecto = from.join("proyecto", JoinType.LEFT);
		/* 342: 403 */ Join<Object, Object> sucursal = from.join("sucursal", JoinType.LEFT);
		/* 343: 404 */ Join<Object, Object> empresa = from.join("empresa", JoinType.LEFT);
		/* 344: 405 */ Join<Object, Object> cliente = empresa.join("cliente", JoinType.LEFT);
		/* 345: 406 */ Join<Object, Object> asiento = from.join("asiento", JoinType.LEFT);
		/* 346: 407 */ Join<Object, Object> tipoAsiento = asiento.join("tipoAsiento", JoinType.LEFT);
		/* 347: */
		/* 348: 409 */ Path<Integer> pathidOrganizacion = from.get("idOrganizacion");
		/* 349: 410 */ Path<Integer> pathUsuarioCreacion = from.get("usuarioCreacion");
		/* 350: 411 */ Path<Integer> pathUsuarioModificacion = from.get("usuarioModificacion");
		/* 351: 412 */ Path<Integer> pathFechaCreacion = from.get("fechaCreacion");
		/* 352: 413 */ Path<Integer> pathFechaModificacion = from.get("fechaModificacion");
		/* 353: 414 */ Path<Integer> pathIdFacturaCliente = from.get("idFacturaCliente");
		/* 354: 415 */ Path<String> pathNumero = from.get("numero");
		/* 355: 416 */ Path<String> pathArchivo = from.get("archivo");
		/* 356: 417 */ Path<String> pathEmail = from.get("email");
		/* 357: 418 */ Path<Integer> pathIdEmpresa = empresa.get("idEmpresa");
		/* 358: 419 */ Path<String> pathNombreFiscal = empresa.get("nombreFiscal");
		/* 359: 420 */ Path<Integer> pathIdPedidoCliente = pedidoCliente.get("idPedidoCliente");
		/* 360: 421 */ Path<String> pathNumeroPedidoCliente = pedidoCliente.get("numero");
		/* 361: 422 */ Path<Date> pathFecha = from.get("fecha");
		/* 362: 423 */ Path<BigDecimal> pathTotal = from.get("total");
		/* 363: 424 */ Path<BigDecimal> pathDescuento = from.get("descuento");
		/* 364: 425 */ Path<BigDecimal> pathDescuentoImpuesto = from.get("descuentoImpuesto");
		/* 365: 426 */ Path<BigDecimal> pathImpuesto = from.get("impuesto");
		/* 366: 427 */ Path<Integer> pathIdDespachoCliente = despachoCliente.get("idDespachoCliente");
		/* 367: 428 */ Path<String> pathNumeroDespachoCliente = despachoCliente.get("numero");
		/* 368: 429 */ Path<Estado> pathEstado = from.get("estado");
		/* 369: 430 */ Path<Boolean> pathIndicadorEmisionRetencion = from.get("indicadorEmisionRetencion");
		/* 370: 431 */ Path<Boolean> pathIndicadorGeneroTransformacion = from.get("indicadorGeneroTransformacion");
		/* 371: 432 */ Path<Boolean> pathIndicadorGeneradoPrefactura = from.get("indicadorGeneradoPrefactura");
		/* 372: 433 */ Path<Integer> pathIdFacturaclienteSRI = facturaClienteSRI.get("idFacturaclienteSRI");
		/* 373: 434 */ Path<String> pathMensajeSRIFacturaclienteSRI = facturaClienteSRI.get("mensajeSRI");
		/* 374: 435 */ Path<String> pathMontoIce = facturaClienteSRI.get("montoIce");
		/* 375: 436 */ Path<String> pathClaveAccesoFacturaclienteSRI = facturaClienteSRI.get("claveAcceso");
		/* 376: 437 */ Path<String> pathDescripcion = from.get("descripcion");
		/* 377: 438 */ Path<Integer> pathIdDocumento = documento.get("idDocumento");
		/* 378: 439 */ Path<String> pathReporteDocumento = documento.get("reporte");
		/* 379: 440 */ Path<Boolean> pathIndicadorDocumentoExterior = documento.get("indicadorDocumentoExterior");
		/* 380: 441 */ Path<DocumentoBase> pathDocumentoBase = documento.get("documentoBase");
		/* 381: 442 */ Path<Boolean> pathIndicadorDocumentoElectronico = documento.get("indicadorDocumentoElectronico");
		/* 382: 443 */ Path<Integer> pathIdDireccionEmpresa = direccionEmpresa.get("idDireccionEmpresa");
		/* 383: 444 */ Path<BigDecimal> pathValorOtros = from.get("valorOtros");
		/* 384: 445 */ Path<String> pathCodigoMovil = from.get("codigoMovil");
		/* 385: */
		/* 386: */
		/* 387: 448 */ Path<Integer> pathIdFacturaClientePadre = facturaClientePadre.get("idFacturaCliente");
		/* 388: 449 */ Path<String> pathNumeroFacturaClientePadre = facturaClientePadre.get("numero");
		/* 389: */
		/* 390: */
		/* 391: 452 */ Path<Integer> pathIdAnticipoCliente = anticipoCliente.get("idAnticipoCliente");
		/* 392: 453 */ Path<Integer> pathNumeroAnticipoCliente = anticipoCliente.get("numero");
		/* 393: 454 */ Path<BigDecimal> pathSaldoAnticipoCliente = anticipoCliente.get("saldo");
		/* 394: */
		/* 395: */
		/* 396: 457 */ Path<Integer> pathIdAsiento = asiento.get("idAsiento");
		/* 397: 458 */ Path<Integer> pathNumeroAsiento = asiento.get("numero");
		/* 398: */
		/* 399: */
		/* 400: 461 */ Path<Integer> pathIdTipoAsiento = tipoAsiento.get("idTipoAsiento");
		/* 401: 462 */ Path<String> pathNombreTipoAsiento = tipoAsiento.get("nombre");
		/* 402: */
		/* 403: */
		/* 404: 465 */ Path<Integer> pathIdCliente = cliente.get("idCliente");
		/* 405: 466 */ Path<BigDecimal> pathCreditoMaximoCliente = cliente.get("creditoMaximo");
		/* 406: 467 */ Path<BigDecimal> pathCreditoUtilizadoCliente = cliente.get("creditoUtilizado");
		/* 407: */
		/* 408: */
		/* 409: 470 */ Path<Integer> pathIdProyecto = proyecto.get("idDimensionContable");
		/* 410: 471 */ Path<BigDecimal> pathCodigoProyecto = proyecto.get("codigo");
		/* 411: 472 */ Path<BigDecimal> pathNombreProyecto = proyecto.get("nombre");
		/* 412: */
		/* 413: */
		/* 414: 475 */ Path<Integer> pathIdSucursal = sucursal.get("idSucursal");
		/* 415: 476 */ Path<String> pathNombreSucursal = sucursal.get("nombre");
		/* 416: */
		/* 417: 478 */ List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
		/* 418: 479 */ criteriaQuery.where((Predicate[]) expresiones.toArray(new Predicate[expresiones.size()]));
		/* 419: */
		/* 420: 481 */ agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
		/* 421: */
		/* 422: 483 */ CriteriaQuery<FacturaCliente> select = criteriaQuery.multiselect(new Selection[] {
				pathidOrganizacion, pathIdSucursal, pathIdFacturaCliente, pathNumero, pathIdEmpresa, pathNombreFiscal,
				pathIdPedidoCliente, pathNumeroPedidoCliente, pathFecha, pathTotal, pathDescuento, pathImpuesto,
				pathIdDespachoCliente, pathNumeroDespachoCliente, pathEstado, pathIndicadorGeneradoPrefactura,
				pathIndicadorEmisionRetencion, pathIdFacturaclienteSRI, pathDescripcion, pathIdDocumento,
				pathReporteDocumento, pathIndicadorDocumentoExterior, pathIdDireccionEmpresa, pathIdAnticipoCliente,
				pathNumeroAnticipoCliente, pathSaldoAnticipoCliente, pathIdAsiento, pathNumeroAsiento,
				pathIdTipoAsiento, pathNombreTipoAsiento, pathIdCliente, pathCreditoMaximoCliente,
				pathCreditoUtilizadoCliente, pathDocumentoBase, pathIdFacturaClientePadre,
				pathNumeroFacturaClientePadre, pathIndicadorDocumentoElectronico, pathIdProyecto, pathCodigoProyecto,
				pathNombreProyecto, pathEmail, pathUsuarioCreacion, pathUsuarioModificacion, pathFechaCreacion,
				pathFechaModificacion, pathMensajeSRIFacturaclienteSRI, pathNombreSucursal, pathValorOtros,
				pathCodigoMovil, pathArchivo, pathClaveAccesoFacturaclienteSRI, pathMontoIce,
				pathIndicadorGeneroTransformacion, pathDescuentoImpuesto });
		/* 423: */
		/* 424: */
		/* 425: */
		/* 426: */
		/* 427: */
		/* 428: */
		/* 429: */
		/* 430: */
		/* 431: */
		/* 432: */
		/* 433: 494 */ TypedQuery<FacturaCliente> typedQuery = this.em.createQuery(select);
		/* 434: 495 */ agregarPaginacion(startIndex, pageSize, typedQuery);
		/* 435: */
		/* 436: 497 */ listaFacturaCliente = typedQuery.getResultList();
		/* 437: */ Map<Integer, List<DetalleValoresContratoVenta>> hmContrato;
		/* 438: 499 */ if (listaFacturaCliente.size() > 0)
		/* 439: */ {
			/* 440: 502 */ CriteriaQuery<DetalleValoresContratoVenta> cqDetalleValoresContratoVenta = criteriaBuilder
					.createQuery(DetalleValoresContratoVenta.class);
			/* 441: 503 */ Root<DetalleValoresContratoVenta> fromDetalleValoresContratoVenta = cqDetalleValoresContratoVenta
					.from(DetalleValoresContratoVenta.class);
			/* 442: 504 */ fromDetalleValoresContratoVenta.fetch("contratoVenta", JoinType.LEFT);
			/* 443: */
			/* 444: 506 */ cqDetalleValoresContratoVenta
					.where(fromDetalleValoresContratoVenta.join("facturaCliente").in(listaFacturaCliente));
			/* 445: */
			/* 446: */
			/* 447: 509 */ CriteriaQuery<DetalleValoresContratoVenta> selectDetalleValoresContratoVenta = cqDetalleValoresContratoVenta
					.select(fromDetalleValoresContratoVenta);
			/* 448: 510 */ TypedQuery<DetalleValoresContratoVenta> typedQuery2 = this.em
					.createQuery(selectDetalleValoresContratoVenta);
			/* 449: 511 */ List<DetalleValoresContratoVenta> listaDetalleValoresContratoVenta = typedQuery2
					.getResultList();
			/* 450: */
			/* 451: 513 */ hmContrato = new HashMap();
			/* 452: 514 */ Map<String, String> hmContratoKey = new HashMap();
			/* 453: 515 */ for (DetalleValoresContratoVenta dvcv : listaDetalleValoresContratoVenta)
			/* 454: */ {
				/* 455: 516 */ List<DetalleValoresContratoVenta> facturaCliente = (List) hmContrato
						.get(Integer.valueOf(dvcv.getFacturaCliente().getId()));
				/* 456: 517 */ if (!hmContratoKey
						.containsKey(dvcv.getFacturaCliente().getId() + "~" + dvcv.getContratoVenta().getNumero()))
				/* 457: */ {
					/* 458: 518 */ if (facturaCliente == null)
					/* 459: */ {
						/* 460: 519 */ facturaCliente = new ArrayList();
						/* 461: 520 */ hmContrato.put(Integer.valueOf(dvcv.getFacturaCliente().getId()),
								facturaCliente);
						/* 462: */ }
					/* 463: 522 */ facturaCliente.add(dvcv);
					/* 464: 523 */ hmContratoKey.put(
							dvcv.getFacturaCliente().getId() + "~" + dvcv.getContratoVenta().getNumero(),
							dvcv/* 465: 524 */ .getContratoVenta().getNumero());
					/* 466: */ }
				/* 467: */ }
			/* 468: 529 */ for (FacturaCliente facturaCliente : listaFacturaCliente)
			/* 469: */ {
				/* 470: 530 */ List<DetalleValoresContratoVenta> contrato = (List) hmContrato
						.get(Integer.valueOf(facturaCliente.getId()));
				/* 471: 531 */ facturaCliente.setListaDetalleValoresContratoVenta(contrato);
				/* 472: */ }
			/* 473: */ }
		/* 474: 536 */ return listaFacturaCliente;
		/* 475: */ }

	/* 476: */
	/* 477: */ public List<FacturaCliente> obtenerListaComboConEqual(String sortField, boolean sortOrder,
			Map<String, String> filters)
	/* 478: */ {
		/* 479: 549 */ CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		/* 480: 550 */ CriteriaQuery<FacturaCliente> criteriaQuery = criteriaBuilder.createQuery(FacturaCliente.class);
		/* 481: 551 */ Root<FacturaCliente> from = criteriaQuery.from(FacturaCliente.class);
		/* 482: 552 */ from.fetch("empresa", JoinType.LEFT);
		/* 483: 553 */ from.fetch("documento", JoinType.LEFT);
		/* 484: */
		/* 485: 555 */ String numero = (String) filters.get("numero");
		/* 486: 556 */ filters.remove("numero");
		/* 487: */
		/* 488: 558 */ agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
		/* 489: */
		/* 490: 560 */ List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
		/* 491: 561 */ if (numero != null) {
			/* 492: 562 */ expresiones.add(criteriaBuilder.equal(from.get("numero"), numero));
			/* 493: */ }
		/* 494: 565 */ criteriaQuery.where((Predicate[]) expresiones.toArray(new Predicate[expresiones.size()]));
		/* 495: */
		/* 496: 567 */ CriteriaQuery<FacturaCliente> select = criteriaQuery.select(from);
		/* 497: 568 */ TypedQuery<FacturaCliente> typedQuery = this.em.createQuery(select);
		/* 498: */
		/* 499: 570 */ return typedQuery.getResultList();
		/* 500: */ }

	/* 501: */
	/* 502: */ public List<FacturaCliente> obtenerListaCombo(String sortField, boolean sortOrder,
			Map<String, String> filters)
	/* 503: */ {
		/* 504: 582 */ CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		/* 505: 583 */ CriteriaQuery<FacturaCliente> criteriaQuery = criteriaBuilder.createQuery(FacturaCliente.class);
		/* 506: 584 */ Root<FacturaCliente> from = criteriaQuery.from(FacturaCliente.class);
		/* 507: 585 */ from.fetch("proyecto", JoinType.LEFT);
		/* 508: 586 */ from.fetch("documento", JoinType.LEFT);
		/* 509: 587 */ from.fetch("sucursal", JoinType.LEFT);
		/* 510: */
		/* 511: 589 */ Fetch<Object, Object> empresa = from.fetch("empresa", JoinType.LEFT);
		/* 512: 590 */ empresa.fetch("cliente", JoinType.LEFT);
		/* 513: 591 */ empresa.fetch("proveedor", JoinType.LEFT);
		/* 514: 592 */ empresa.fetch("empleado", JoinType.LEFT);
		/* 515: */
		/* 516: */
		/* 517: 595 */ Fetch<Object, Object> subempresa = from.fetch("subempresa", JoinType.LEFT);
		/* 518: 596 */ Fetch<Object, Object> empresaSubempresa = subempresa.fetch("empresa", JoinType.LEFT);
		/* 519: 597 */ empresaSubempresa.fetch("cliente", JoinType.LEFT);
		/* 520: 598 */ empresaSubempresa.fetch("proveedor", JoinType.LEFT);
		/* 521: 599 */ empresaSubempresa.fetch("empleado", JoinType.LEFT);
		/* 522: */
		/* 523: 601 */ agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
		/* 524: */
		/* 525: 603 */ List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
		/* 526: */
		/* 527: 605 */ criteriaQuery.where((Predicate[]) expresiones.toArray(new Predicate[expresiones.size()]));
		/* 528: */
		/* 529: 607 */ CriteriaQuery<FacturaCliente> select = criteriaQuery.select(from);
		/* 530: 608 */ TypedQuery<FacturaCliente> typedQuery = this.em.createQuery(select);
		/* 531: 609 */ typedQuery.setMaxResults(20);
		/* 532: */
		/* 533: 611 */ return typedQuery.getResultList();
		/* 534: */ }

	/* 535: */
	/* 536: */ public BigDecimal getSumaImpuestoPorIdFacturaCliente(int idDetalleFacturaCliente)
	/* 537: */ {
		/* 538: 621 */ String query = "SELECT SUM(i.porcentajeImpuesto) FROM ImpuestoProductoFacturaCliente i INNER JOIN i.detalleFacturaCliente d WHERE d.idDetalleFacturaCliente = :idDetalleFacturaCliente ";
		/* 539: */
		/* 540: */
		/* 541: 624 */ BigDecimal suma = (BigDecimal) this.em.createQuery(query)
				.setParameter("idDetalleFacturaCliente", Integer.valueOf(idDetalleFacturaCliente)).getSingleResult();
		/* 542: 625 */ return suma;
		/* 543: */ }

	/* 544: */
	/* 545: */ public List<Object[]> getReporteFacturaCliente(int idFacturaCliente)/* 546: */ throws ExcepcionAS2
	/* 547: */ {
		/* 548: 629 */ return getReporteFacturaCliente(idFacturaCliente, false);
		/* 549: */ }

	/* 550: */
	/* 551: */ public List<Object[]> getReporteFacturaCliente(int idFacturaCliente, boolean ordenado)
			/* 552: */ throws ExcepcionAS2
	/* 553: */ {
		/* 554: 642 */ StringBuilder sql = new StringBuilder();
		/* 555: 643 */ sql.append(
				" SELECT e.nombreFiscal, CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4), ");
		/* 556: 644 */ sql.append(
				" e.identificacion, f.fecha,CASE WHEN d.indicadorManejoPeso = true then d.peso else d.cantidad end, pr.codigo, pr.nombreComercial, d.precio, f.total, f.descuento, f.impuesto, ");
		/* 557: */
		/* 558: 646 */ sql
				.append(" de.telefono1, f.descripcion, YEAR(f.fecha), MONTH(f.fecha), DAY(f.fecha), d.descripcion, ");
		/* 559: 647 */ sql.append(
				" e.codigo, ci.nombre, c.nombre, CONCAT(ag.nombre1, ' ', ag.nombre2), un.nombre, pr.peso, gr.numero, f.referencia1, f.referencia2, ");
		/* 560: */
		/* 561: 649 */ sql.append(
				" pr.codigoAlterno, pr.codigoComercial, pr.codigoBarras, f.numero, z.nombre, ca.nombre, f.fechaVencimiento, u.direccion5, e.nombreComercial, ");
		/* 562: */
		/* 563: 651 */ sql.append(" fcSRI.baseImponibleTarifaCero, fcSRI.baseImponibleDiferenteCero, d.descuento,");
		/* 564: 652 */ sql.append(
				" fcSRI.autorizacion, fcSRI.fechaAutorizacion, fcSRI.fechaCaducidad,'', f.numero, f.idFacturaCliente, f.usuarioCreacion, f.fechaCreacion, coalesce(l.codigo,''), ");
		/* 565: */
		/* 566: 654 */ sql
				.append(" p.nombre, u.direccion5, dc.numero, 1, de.telefono2, pr.descripcion, pa.nombre, un.codigo, ");
		/* 567: */
		/* 568: */
		/* 569: 657 */ sql.append(
				" o.razonSocial, o.identificacion, fcSRI.fechaAutorizacion, fcSRI.claveAcceso, oc.numeroResolucionContribuyente,");
		/* 570: 658 */ sql.append(
				" oc.indicadorObligadoContabilidad, fcSRI.direccionMatriz, fcSRI.direccionSucursal, fcSRI.ambiente, fcSRI.tipoEmision,fcp.numero,fcp.fecha,moti.nombre,fcSRI.email,");
		/* 571: */
		/* 572: */
		/* 573: */
		/* 574: 662 */ sql.append(" d.peso, d.precioLinea, d.descuentoLinea, ld.codigo,");
		/* 575: */
		/* 576: 664 */ sql.append(" d.nandina, f.referencia3, f.referencia4, f.referencia5, f.referencia6, ");
		/* 577: 665 */ sql.append(
				" pe.numero, l.codigo, des.numero, d.porcentajeDescuento, CASE WHEN d.indicadorManejoPeso = false then d.peso else d.cantidad end, pr.descripcion, f.descripcion2, fcSRI.montoIRBPNR, fcSRI.montoIva, pr.nombre, f.valorReferencia1, f.valorReferencia2, se.empresaFinal, f.referencia7, ");
		/* 578: */
		/* 579: 667 */ sql.append(
				" f.referencia8, f.referencia9, e.codigoAlterno, cl.referencia, cl.referencia2, f.referencia10,");
		/* 580: */
		/* 581: */
		/* 582: 670 */ sql.append(
				" gr.autorizacion, gr.claveAcceso, gr.conductor, gr.licencia, gr.placa, co.nombre, gr.fecha, cd.nombre, gr.fechaVigencia, gr.fechaAutorizacion");
		/* 583: */
		/* 584: */
		/* 585: */
		/* 586: 674 */ sql.append(
				", se.codigo, d.subsidio, fcSRI.totalSubsidio, fcSRI.codigoFormaPagoSRI, c.diasPlazo, tr.identificacion, tr.nombre, fcSRI.montoIce, c.mesesPlazo, f.descuentoImpuesto, f.idFacturaCliente ");
		/* 587: */
		/* 588: */
		/* 589: 677 */ sql.append(" FROM DetalleFacturaCliente d ");
		/* 590: 678 */ sql.append(" LEFT JOIN d.detalleDespachoCliente ddc ");
		/* 591: 679 */ sql.append(" LEFT JOIN ddc.inventarioProducto ip ");
		/* 592: 680 */ sql.append(" LEFT JOIN ip.lote l ");
		/* 593: 681 */ sql.append(" LEFT JOIN d.facturaCliente f ");
		/* 594: 682 */ sql.append(" LEFT JOIN f.empresa e ");
		/* 595: 683 */ sql.append(" LEFT JOIN f.direccionEmpresa de ");
		/* 596: 684 */ sql.append(" LEFT JOIN e.cliente cl ");
		/* 597: 685 */ sql.append(" LEFT JOIN cl.listaDescuentos ld ");
		/* 598: 686 */ sql.append(" LEFT JOIN ddc.despachoCliente dc ");
		/* 599: 687 */ sql.append(" LEFT JOIN dc.guiaRemision gr ");
		/* 600: 688 */ sql.append(" LEFT JOIN de.ubicacion u ");
		/* 601: 689 */ sql.append(" LEFT JOIN de.ciudad ci ");
		/* 602: 690 */ sql.append(" LEFT JOIN ci.provincia p ");
		/* 603: 691 */ sql.append(" LEFT JOIN p.pais pa ");
		/* 604: 692 */ sql.append(" LEFT JOIN d.producto pr ");
		/* 605: 693 */ sql.append(" LEFT JOIN d.unidadVenta un ");
		/* 606: 694 */ sql.append(" LEFT JOIN f.condicionPago c ");
		/* 607: 695 */ sql.append(" LEFT JOIN f.agenteComercial ag ");
		/* 608: 696 */ sql.append(" LEFT JOIN f.zona z ");
		/* 609: 697 */ sql.append(" LEFT JOIN f.canal ca ");
		/* 610: 698 */ sql.append(" LEFT JOIN f.agenteComercial ag ");
		/* 611: 699 */ sql.append(" LEFT JOIN f.facturaClienteSRI fcSRI, Organizacion o ");
		/* 612: 700 */ sql.append(" LEFT JOIN f.facturaClientePadre fcp ");
		/* 613: 701 */ sql.append(" LEFT JOIN f.motivoNotaCreditoCliente moti ");
		/* 614: 702 */ sql.append(" LEFT JOIN f.pedidoCliente pe ");
		/* 615: */
		/* 616: 704 */ sql.append(" LEFT JOIN f.despachoCliente des ");
		/* 617: */
		/* 618: 706 */ sql.append(" LEFT JOIN f.subempresa se");
		/* 619: 707 */ sql.append(" LEFT JOIN gr.transportista tr ");
		/* 620: 708 */ sql.append(" LEFT JOIN gr.ciudadOrigen co ");
		/* 621: 709 */ sql.append(" LEFT JOIN gr.ciudadDestino cd ");
		/* 622: 710 */ sql.append(" JOIN o.organizacionConfiguracion oc");
		/* 623: 711 */ sql
				.append(" WHERE o.idOrganizacion = f.idOrganizacion AND f.idFacturaCliente = :idFacturaCliente ");
		/* 624: 712 */ if (ordenado) {
			/* 625: 713 */ sql.append(" ORDER BY pr.codigo ");
			/* 626: */ } else {
			/* 627: 715 */ sql.append(" ORDER BY d.idDetalleFacturaCliente ");
			/* 628: */ }
		/* 629: 718 */ Query query = this.em.createQuery(sql.toString()).setParameter("idFacturaCliente",
				Integer.valueOf(idFacturaCliente));
		/* 630: 719 */ return query.getResultList();
		/* 631: */ }

	/* 632: */
	/* 633: */ public List getReporteFacturaClientePorLotes(String facturaDesde, String facturaHasta)
			/* 634: */ throws ExcepcionAS2
	/* 635: */ {
		/* 636: 732 */ String sql = "SELECT e.nombreFiscal, CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4), e.identificacion, f.fecha, f.numero, d.cantidad, pr.codigo, pr.nombreComercial, d.precio, f.total, f.descuento, f.impuesto,  de.telefono1, f.descripcion, YEAR(f.fecha), MONTH(f.fecha), DAY(f.fecha), d.descripcion,  e.codigo, ci.nombre, c.nombre, CONCAT(ag.nombre1, ' ', ag.nombre2), un.nombre, pr.peso, gr.numero, f.referencia1, f.referencia2,  pr.codigoAlterno, pr.codigoComercial, pr.codigoBarras, f.numero  FROM DetalleFacturaCliente d  LEFT JOIN d.facturaCliente f  LEFT JOIN f.empresa e  LEFT JOIN f.direccionEmpresa de  LEFT JOIN f.despachoCliente dc  LEFT JOIN dc.guiaRemision gr LEFT JOIN de.ubicacion u  LEFT JOIN de.ciudad ci  LEFT JOIN d.producto pr  LEFT JOIN pr.unidad un  LEFT JOIN f.condicionPago c  LEFT JOIN f.agenteComercial ag  WHERE f.numero BETWEEN :facturaDesde AND :facturaHasta";
		/* 637: */
		/* 638: */
		/* 639: */
		/* 640: */
		/* 641: */
		/* 642: */
		/* 643: */
		/* 644: */
		/* 645: */
		/* 646: 742 */ Query query = this.em.createQuery(sql).setParameter("facturaDesde", facturaDesde)
				.setParameter("facturaHasta", facturaHasta);
		/* 647: 743 */ return query.getResultList();
		/* 648: */ }

	/* 649: */
	/* 650: */ public List<FacturaCliente> obtenerFacturaClientePorSucursalPuntoVenta(String sucursal,
			String puntoDeVenta)
	/* 651: */ {
		/* 652: 755 */ String sql = " SELECT fc  FROM FacturaCliente fc  WHERE SUBSTRING (fc.numero, 1,3) = :sucursal AND SUBSTRING (fc.numero, 5,3) = :puntoDeVenta";
		/* 653: */
		/* 654: 757 */ Query query = this.em.createQuery(sql);
		/* 655: 758 */ query.setParameter("sucursal", sucursal).setParameter("puntoDeVenta", puntoDeVenta);
		/* 656: */
		/* 657: 760 */ return query.getResultList();
		/* 658: */ }

	/* 659: */
	/* 660: */ public List<DetalleInterfazContable> getInterfazVentasCXC(Date fechaDesde, Date fechaHasta,
			DocumentoBase documentoBase, int idDocumento, int idOrganizacion, boolean agrupado)
			/* 661: */ throws ExcepcionAS2Financiero
	/* 662: */ {
		/* 663: */ try
		/* 664: */ {
			/* 665: 776 */ StringBuilder sql = new StringBuilder();
			/* 666: 777 */ if (agrupado) {
				/* 667: 778 */ sql.append(
						"SELECT new DetalleInterfazContable(ce.cuentaContableCliente.idCuentaContable,'','','', ");
				/* 668: */ } else {
				/* 669: 780 */ sql.append(
						"SELECT new DetalleInterfazContable(ce.cuentaContableCliente.idCuentaContable,'',CONCAT('FC/',fc.numero),'', ");
				/* 670: */ }
			/* 671: 782 */ sql.append(" fc.total+fc.impuesto+fc.valorOtros-fc.descuento,fc.idFacturaCliente) ");
			/* 672: 783 */ sql.append(" FROM FacturaCliente fc ");
			/* 673: 784 */ sql.append(" INNER JOIN fc.documento d ");
			/* 674: 785 */ sql.append(" INNER JOIN fc.empresa e ");
			/* 675: 786 */ sql.append(" INNER JOIN e.categoriaEmpresa ce ");
			/* 676: 787 */ sql.append(
					" WHERE fc.fecha BETWEEN :fechaDesde AND :fechaHasta AND fc.indicadorSaldoInicial=false AND d.documentoBase=:documentoBase ");
			/* 677: 788 */ sql.append(" AND fc.estado<>:estadoAnulado AND fc.estado<>:estadoContabilizado ");
			/* 678: 789 */ sql.append(
					" AND (d.idDocumento = :idDocumento OR :idDocumento=0) AND fc.idOrganizacion = :idOrganizacion ");
			/* 679: */
			/* 680: 791 */ Query query = this.em.createQuery(sql.toString());
			/* 681: 792 */ query.setParameter("fechaDesde", fechaDesde);
			/* 682: 793 */ query.setParameter("fechaHasta", fechaHasta);
			/* 683: 794 */ query.setParameter("documentoBase", documentoBase);
			/* 684: 795 */ query.setParameter("estadoAnulado", Estado.ANULADO);
			/* 685: 796 */ query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
			/* 686: 797 */ query.setParameter("idDocumento", Integer.valueOf(idDocumento));
			/* 687: 798 */ query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
			/* 688: 799 */ return query.getResultList();
			/* 689: */ }
		/* 690: */ catch (IllegalArgumentException e)
		/* 691: */ {
			/* 692: 802 */ throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable",
					"cuentaContableCliente");
			/* 693: */ }
		/* 694: */ }

	/* 695: */
	/* 696: */ public List<DetalleInterfazContable> getInterfazVentasInventarioCosto(Date fechaDesde, Date fechaHasta)
	/* 697: */ {
		/* 698: 817 */ List<DetalleInterfazContable> lista = new ArrayList();
		/* 699: 818 */ Query query = this.em.createQuery(
				"SELECT new DetalleInterfazContable(sc.cuentaContableMercaderiaPorDespachar.idCuentaContable,'','','',ROUND(-SUM(ip.costo),2) )\tFROM DetalleFacturaCliente dfc\tINNER JOIN dfc.facturaCliente fc\tINNER JOIN dfc.producto pr \tINNER JOIN dfc.inventarioProducto ip\tINNER JOIN pr.subcategoriaProducto sc WHERE pr.tipoProducto=:tipoProducto AND fc.fecha BETWEEN :fechaDesde and :fechaHasta\tGROUP BY sc.cuentaContableMercaderiaPorDespachar.idCuentaContable HAVING SUM(ip.costo)!=0");
		/* 700: */
		/* 701: */
		/* 702: */
		/* 703: */
		/* 704: */
		/* 705: */
		/* 706: 825 */ query.setParameter("fechaDesde", fechaDesde);
		/* 707: 826 */ query.setParameter("fechaHasta", fechaHasta);
		/* 708: 827 */ query.setParameter("tipoProducto", TipoProducto.ARTICULO);
		/* 709: 828 */ lista = query.getResultList();
		/* 710: */
		/* 711: 830 */ query = this.em.createQuery(
				"SELECT new DetalleInterfazContable(sc.cuentaContableCosto.idCuentaContable,'','','',ROUND(SUM(ip.costo),2) )\tFROM DetalleFacturaCliente dfc\tINNER JOIN dfc.facturaCliente fc\tINNER JOIN dfc.producto pr \tINNER JOIN dfc.inventarioProducto ip\tINNER JOIN pr.subcategoriaProducto sc WHERE pr.tipoProducto=:tipoProducto AND fc.fecha BETWEEN :fechaDesde and :fechaHasta\tGROUP BY sc.cuentaContableCosto.idCuentaContable HAVING SUM(ip.costo)!=0");
		/* 712: */
		/* 713: */
		/* 714: */
		/* 715: */
		/* 716: */
		/* 717: 836 */ query.setParameter("fechaDesde", fechaDesde);
		/* 718: 837 */ query.setParameter("fechaHasta", fechaHasta);
		/* 719: 838 */ query.setParameter("tipoProducto", TipoProducto.ARTICULO);
		/* 720: */
		/* 721: 840 */ lista.addAll(query.getResultList());
		/* 722: */
		/* 723: 842 */ return lista;
		/* 724: */ }

	/* 725: */
	/* 726: */ public List<DetalleInterfazContable> getInterfazVentasIngreso(Date fechaDesde, Date fechaHasta,
			DocumentoBase documentoBase, int idDocumento, int idOrganizacion, boolean agrupado)
			/* 727: */ throws ExcepcionAS2Financiero
	/* 728: */ {
		/* 729: 857 */ List<DetalleInterfazContable> lista = new ArrayList();
		/* 730: */ try
		/* 731: */ {
			/* 732: 861 */ StringBuilder sql = new StringBuilder();
			/* 733: 862 */ if (agrupado) {
				/* 734: 863 */ sql.append(
						"SELECT new DetalleInterfazContable(scp.cuentaContableIngreso.idCuentaContable,'','','', ");
				/* 735: */ } else {
				/* 736: 865 */ sql.append(
						"SELECT new DetalleInterfazContable(scp.cuentaContableIngreso.idCuentaContable,'',CONCAT('FC/',fc.numero),'', ");
				/* 737: */ }
			/* 738: 868 */ sql.append(" -ROUND(SUM(dfc.precioLinea),2), scp.cuentaContableIngreso.idCuentaContable) ");
			/* 739: 869 */ sql.append(" FROM DetalleFacturaCliente dfc ");
			/* 740: 870 */ sql.append(" INNER JOIN dfc.facturaCliente fc ");
			/* 741: 871 */ sql.append(" INNER JOIN fc.documento d ");
			/* 742: 872 */ sql.append(" INNER JOIN dfc.producto p ");
			/* 743: 873 */ sql.append(" INNER JOIN p.subcategoriaProducto scp ");
			/* 744: 874 */ sql.append(
					" WHERE fc.fecha BETWEEN :fechaDesde AND :fechaHasta AND fc.indicadorSaldoInicial=false AND d.documentoBase=:documentoBase ");
			/* 745: 875 */ sql.append(
					" AND fc.estado<>:estadoAnulado AND fc.estado<>:estadoContabilizado AND fc.idOrganizacion = :idOrganizacion ");
			/* 746: 876 */ sql.append(" AND (d.idDocumento = :idDocumento OR :idDocumento=0) ");
			/* 747: 877 */ if (agrupado) {
				/* 748: 878 */ sql.append(" GROUP BY fc.idFacturaCliente,scp.cuentaContableIngreso.idCuentaContable ");
				/* 749: */ } else {
				/* 750: 880 */ sql
						.append(" GROUP BY fc.idFacturaCliente,fc.numero, scp.cuentaContableIngreso.idCuentaContable ");
				/* 751: */ }
			/* 752: 883 */ sql.append(" HAVING ROUND(SUM(dfc.precioLinea),2)!=0 ");
			/* 753: */
			/* 754: 885 */ Query query = this.em.createQuery(sql.toString());
			/* 755: 886 */ query.setParameter("fechaDesde", fechaDesde);
			/* 756: 887 */ query.setParameter("fechaHasta", fechaHasta);
			/* 757: 888 */ query.setParameter("documentoBase", documentoBase);
			/* 758: 889 */ query.setParameter("estadoAnulado", Estado.ANULADO);
			/* 759: 890 */ query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
			/* 760: 891 */ query.setParameter("idDocumento", Integer.valueOf(idDocumento));
			/* 761: 892 */ query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
			/* 762: */
			/* 763: 894 */ lista.addAll(query.getResultList());
			/* 764: */ }
		/* 765: */ catch (IllegalArgumentException e)
		/* 766: */ {
			/* 767: 897 */ throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable",
					"cuentaContableIngreso");
			/* 768: */ }
		/* 769: */ try
		/* 770: */ {
			/* 771: 900 */ StringBuilder sql2 = new StringBuilder();
			/* 772: 901 */ sql2.append(
					"SELECT new DetalleInterfazContable(scp.cuentaContableDescuentoVenta.idCuentaContable,'','','',");
			/* 773: */
			/* 774: 903 */ sql2
					.append(" ROUND(SUM(dfc.descuentoLinea),2),scp.cuentaContableDescuentoVenta.idCuentaContable) ");
			/* 775: 904 */ sql2.append(" FROM DetalleFacturaCliente dfc ");
			/* 776: 905 */ sql2.append(" INNER JOIN dfc.facturaCliente fc ");
			/* 777: 906 */ sql2.append(" INNER JOIN fc.documento d ");
			/* 778: 907 */ sql2.append(" INNER JOIN dfc.producto p ");
			/* 779: 908 */ sql2.append(" INNER JOIN p.subcategoriaProducto scp ");
			/* 780: 909 */ sql2.append(
					" WHERE fc.fecha BETWEEN :fechaDesde AND :fechaHasta AND fc.indicadorSaldoInicial=false AND d.documentoBase=:documentoBase ");
			/* 781: 910 */ sql2.append(
					" AND fc.estado<>:estadoAnulado AND fc.estado<>:estadoContabilizado  AND fc.idOrganizacion = :idOrganizacion ");
			/* 782: 911 */ sql2.append(" AND (d.idDocumento = :idDocumento OR :idDocumento=0)");
			/* 783: 912 */ sql2
					.append(" GROUP BY fc.idFacturaCliente,scp.cuentaContableDescuentoVenta.idCuentaContable ");
			/* 784: */
			/* 785: 914 */ sql2.append(" HAVING SUM(dfc.descuentoLinea)!=0");
			/* 786: */
			/* 787: 916 */ Query query = this.em.createQuery(sql2.toString());
			/* 788: 917 */ query.setParameter("fechaDesde", fechaDesde);
			/* 789: 918 */ query.setParameter("fechaHasta", fechaHasta);
			/* 790: 919 */ query.setParameter("documentoBase", documentoBase);
			/* 791: 920 */ query.setParameter("estadoAnulado", Estado.ANULADO);
			/* 792: 921 */ query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
			/* 793: 922 */ query.setParameter("idDocumento", Integer.valueOf(idDocumento));
			/* 794: 923 */ query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
			/* 795: 924 */ lista.addAll(query.getResultList());
			/* 796: */
			/* 797: 926 */ return lista;
			/* 798: */ }
		/* 799: */ catch (IllegalArgumentException e)
		/* 800: */ {
			/* 801: 929 */ throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable",
					"cuentaContableDescuentoVenta");
			/* 802: */ }
		/* 803: */ }

	/* 804: */
	/* 805: */ public List<DetalleInterfazContable> getInterfazVentasEstructuraPrecioCombustible(
			List<Integer> listaFacturaCliente)/* 806: */ throws ExcepcionAS2Financiero
	/* 807: */ {
		/* 808: 944 */ List<DetalleInterfazContable> lista = new ArrayList();
		/* 809: */
		/* 810: 946 */ StringBuilder sql = new StringBuilder();
		/* 811: 947 */ sql.append(
				" SELECT new DetalleInterfazContable(cct.idCuentaContable,'',CONCAT('FC/',fc.numero),dfc.recargo, -SUM(ROUND(dfc.valor,2)), cct.idCuentaContable) ");
		/* 812: */
		/* 813: 949 */ sql.append(" FROM DetalleFacturaClienteComercializadora dfc ");
		/* 814: 950 */ sql.append(" INNER JOIN dfc.facturaCliente fc ");
		/* 815: 951 */ sql.append(" INNER JOIN fc.documento d ");
		/* 816: 952 */ sql.append(" INNER JOIN dfc.cuentaContable cct ");
		/* 817: 953 */ sql
				.append(" WHERE fc.idFacturaCliente in(:listaFacturaCliente) AND fc.indicadorSaldoInicial=false ");
		/* 818: 954 */ sql.append(" GROUP BY dfc.recargo, cct.idCuentaContable, fc.numero ");
		/* 819: 955 */ sql.append(" HAVING SUM(dfc.valor)!=0 ");
		/* 820: */
		/* 821: 957 */ Query query = this.em.createQuery(sql.toString());
		/* 822: 958 */ query.setParameter("listaFacturaCliente", listaFacturaCliente);
		/* 823: */
		/* 824: 960 */ lista.addAll(query.getResultList());
		/* 825: */
		/* 826: 962 */ return lista;
		/* 827: */ }

	/* 828: */
	/* 829: */ public void actualizarEstadoFacturasInterfazContable(InterfazContableProceso interfazContableProceso)
	/* 830: */ {
		/* 831: 974 */ String sql = "UPDATE FacturaCliente fc  SET fc.estado=:estado, fc.interfazContableProceso = NULL\tWHERE fc.interfazContableProceso = :interfazContableProceso";
		/* 832: */
		/* 833: */
		/* 834: 977 */ Query query = this.em.createQuery(sql);
		/* 835: 978 */ query.setParameter("estado", Estado.PROCESADO);
		/* 836: 979 */ query.setParameter("interfazContableProceso", interfazContableProceso);
		/* 837: 980 */ query.executeUpdate();
		/* 838: */ }

	/* 839: */
	/* 840: */ public void liberarDespachoCliente(FacturaCliente facturaCliente)
	/* 841: */ {
		/* 842: 992 */ String sql = "UPDATE DespachoCliente dc  SET dc.indicadorGeneradoFactura = false \tWHERE dc IN ( SELECT dc2 from DetalleFacturaCliente dfc LEFT JOIN dfc.detalleDespachoCliente ddc LEFT JOIN ddc.despachoCliente dc2 WHERE dfc.facturaCliente = :facturaCliente )";
		/* 843: */
		/* 844: */
		/* 845: 995 */ Query query = this.em.createQuery(sql);
		/* 846: 996 */ query.setParameter("facturaCliente", facturaCliente);
		/* 847: 997 */ query.executeUpdate();
		/* 848: */
		/* 849: */
		/* 850:1000 */ sql = "UPDATE FacturaCliente fc  SET fc.despachoCliente=null\tWHERE fc = :facturaCliente";
		/* 851: */
		/* 852:1002 */ query = this.em.createQuery(sql);
		/* 853:1003 */ query.setParameter("facturaCliente", facturaCliente);
		/* 854: */
		/* 855:1005 */ query.executeUpdate();
		/* 856:1006 */ sql = "UPDATE DetalleFacturaCliente dfc  SET dfc.detalleDespachoCliente=null\tWHERE dfc.facturaCliente = :facturaCliente";
		/* 857: */
		/* 858:1008 */ query = this.em.createQuery(sql);
		/* 859:1009 */ query.setParameter("facturaCliente", facturaCliente);
		/* 860:1010 */ query.executeUpdate();
		/* 861: */ }

	/* 862: */
	/* 863: */ public List<DetalleInterfazContable> getFacturaClienteNDIC(Integer idFacturaCliente)
	/* 864: */ {
		/* 865:1022 */ Query query = this.em.createQuery(
				"SELECT new DetalleInterfazContable(cccl.idCuentaContable,\tem.nombreFiscal,CONCAT(do.nombre, ' #', fc.numero),'',fc.total)\tFROM FacturaCliente fc \tINNER JOIN fc.empresa em \tINNER JOIN em.categoriaEmpresa cem \tINNER JOIN cem.cuentaContableCliente cccl \tINNER JOIN fc.documento do  WHERE fc.idFacturaCliente=:idFacturaCliente ");
		/* 866: */
		/* 867: */
		/* 868: */
		/* 869: */
		/* 870: */
		/* 871:1028 */ query.setParameter("idFacturaCliente", idFacturaCliente);
		/* 872:1029 */ return query.getResultList();
		/* 873: */ }

	/* 874: */
	/* 875: */ public List<DetalleInterfazContable> getFacturaClienteCPIC(Integer idFacturaCliente)
	/* 876: */ {
		/* 877:1053 */ Query query = this.em.createQuery(
				"SELECT new DetalleInterfazContable(cccl.idCuentaContable, em.nombreFiscal,CONCAT(do.nombre, ' #', fc.numero),'',-fc.total)\tFROM FacturaCliente fc \tINNER JOIN fc.empresa em \tINNER JOIN em.categoriaEmpresa cem \tINNER JOIN cem.cuentaContableCliente cccl \tINNER JOIN fc.documento do  WHERE fc.idFacturaCliente=:idFacturaCliente ");
		/* 878: */
		/* 879: */
		/* 880: */
		/* 881: */
		/* 882: */
		/* 883:1059 */ query.setParameter("idFacturaCliente", idFacturaCliente);
		/* 884:1060 */ return query.getResultList();
		/* 885: */ }

	/* 886: */
	/* 887: */ public void actualizarEstado(int idFacturaCliente, Estado estado, MotivoAnulacion motivoAnulacion)
	/* 888: */ {
		/* 889:1071 */ StringBuilder sql = new StringBuilder();
		/* 890:1072 */ sql.append(" UPDATE FacturaCliente f SET f.estado = :estado ");
		/* 891:1073 */ if (motivoAnulacion != null) {
			/* 892:1074 */ sql.append(", motivoAnulacion = :motivoAnulacion ");
			/* 893: */ }
		/* 894:1076 */ sql.append(" WHERE f.idFacturaCliente = :idFacturaCliente ");
		/* 895:1077 */ Query query = this.em.createQuery(sql.toString());
		/* 896:1078 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente)).setParameter("estado",
				estado);
		/* 897:1079 */ if (motivoAnulacion != null) {
			/* 898:1080 */ query.setParameter("motivoAnulacion", motivoAnulacion);
			/* 899: */ }
		/* 900:1082 */ query.executeUpdate();
		/* 901: */ }

	/* 902: */
	/* 903: */ public void actualizarValorDevuelto(int idFacturaCliente, BigDecimal valorDevuelto)
	/* 904: */ {
		/* 905:1086 */ StringBuilder sql = new StringBuilder();
		/* 906:1087 */ sql.append(" UPDATE FacturaCliente f SET f.valorDevuelto = :valorDevuelto ");
		/* 907:1088 */ sql.append(" WHERE f.idFacturaCliente = :idFacturaCliente ");
		/* 908:1089 */ Query query = this.em.createQuery(sql.toString());
		/* 909:1090 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 910:1091 */ query.setParameter("valorDevuelto", valorDevuelto);
		/* 911:1092 */ query.executeUpdate();
		/* 912: */ }

	/* 913: */
	/* 914: */ public List<CuentaPorCobrar> obtenerCuotasCobradas(int idFacturaCliente)
	/* 915: */ {
		/* 916:1104 */ Query query = this.em.createQuery(
				"SELECT cxc FROM CuentaPorCobrar cxc WHERE (cxc.saldo<>cxc.valor or cxc.valorBloqueado > 0) AND cxc.facturaCliente.idFacturaCliente=:idFacturaCliente");
		/* 917: */
		/* 918:1106 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 919: */
		/* 920:1108 */ return query.getResultList();
		/* 921: */ }

	/* 922: */
	/* 923: */ public void anularInterfazContableVenta(InterfazContableProceso interfazContableProceso)
	/* 924: */ {
		/* 925:1120 */ String sql = "UPDATE FacturaCliente fc  SET fc.estado=:estadoAprobado, fc.interfazContableProceso=NULL WHERE fc.interfazContableProceso=:interfazContableProceso";
		/* 926: */
		/* 927: */
		/* 928:1123 */ Query query = this.em.createQuery(sql);
		/* 929:1124 */ query.setParameter("estadoAprobado", Estado.APROBADO);
		/* 930:1125 */ query.setParameter("interfazContableProceso", interfazContableProceso);
		/* 931:1126 */ query.executeUpdate();
		/* 932: */ }

	/* 933: */
	/* 934: */ public List<DetalleInterfazContable> getInterfazNotaCreditoCuentasImpuesto(int idFacturaCliente)
			/* 935: */ throws ExcepcionAS2Financiero
	/* 936: */ {
		/* 937: */ try
		/* 938: */ {
			/* 939:1140 */ List<DetalleInterfazContable> lista = new ArrayList();
			/* 940: */
			/* 941: */
			/* 942: */
			/* 943: */
			/* 944:1145 */ String sql = "SELECT new DetalleInterfazContable(ccv.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero),'', ROUND(SUM((dfc.precioLinea-dfc.descuentoLinea)*(ipfc.porcentajeImpuesto/100)),2) ) \tFROM ImpuestoProductoFacturaCliente ipfc   INNER JOIN  ipfc.impuesto i\tINNER JOIN  ipfc.detalleFacturaCliente dfc\tINNER JOIN  dfc.facturaCliente fc\tINNER JOIN  fc.empresa em\tINNER JOIN  fc.documento do LEFT JOIN i.cuentaContableVenta ccv\tWHERE fc.idFacturaCliente=:idFacturaCliente GROUP BY ccv.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero) HAVING SUM((dfc.precioLinea-dfc.descuentoLinea)*(ipfc.porcentajeImpuesto/100)) !=0";
			/* 945: */
			/* 946: */
			/* 947: */
			/* 948: */
			/* 949: */
			/* 950: */
			/* 951: */
			/* 952: */
			/* 953:1154 */ Query query = this.em.createQuery(sql);
			/* 954:1155 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
			/* 955:1156 */ return query.getResultList();
			/* 956: */ }
		/* 957: */ catch (IllegalArgumentException e)
		/* 958: */ {
			/* 959:1161 */ e.printStackTrace();
			/* 960:1162 */ throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable",
					"cuentaContableImpuesto");
			/* 961: */ }
		/* 962: */ }

	/* 963: */
	/* 964: */ public List<DetalleInterfazContable> getInterfazNotaCreditoCuentasAnticipo(int idFacturaCliente)
			/* 965: */ throws ExcepcionAS2Financiero
	/* 966: */ {
		/* 967: */ try
		/* 968: */ {
			/* 969:1176 */ List<DetalleInterfazContable> lista = new ArrayList();
			/* 970: */
			/* 971:1178 */ Query query = this.em.createQuery(
					"SELECT new DetalleInterfazContable(ce.cuentaContableAnticipoClienteNotaCredito.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero),'',-fc.total-fc.impuesto+fc.descuento ) FROM FacturaCliente fc  INNER JOIN fc.documento do  INNER JOIN fc.empresa em  INNER JOIN em.categoriaEmpresa ce  WHERE fc.idFacturaCliente=:idFacturaCliente");
			/* 972: */
			/* 973: */
			/* 974: */
			/* 975: */
			/* 976:1183 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
			/* 977:1184 */ return query.getResultList();
			/* 978: */ }
		/* 979: */ catch (IllegalArgumentException e)
		/* 980: */ {
			/* 981:1189 */ throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable",
					"cuentaContableAnticipoClienteNotaCredito");
			/* 982: */ }
		/* 983: */ }

	/* 984: */
	/* 985: */ public List<DetalleInterfazContable> getInterfazNotaCreditoCuentasDescuento(int idFacturaCliente)
			/* 986: */ throws ExcepcionAS2Financiero
	/* 987: */ {
		/* 988: */ try
		/* 989: */ {
			/* 990:1204 */ List<DetalleInterfazContable> lista = new ArrayList();
			/* 991: */
			/* 992: */
			/* 993: */
			/* 994: */
			/* 995:1209 */ String sql = "SELECT new DetalleInterfazContable(ccdv.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero),'',ROUND(SUM(dfc.precioLinea-dfc.descuentoLinea),2) )\tFROM DetalleFacturaCliente dfc\tINNER JOIN dfc.facturaCliente fc INNER JOIN fc.empresa em \tINNER JOIN fc.documento do\tINNER JOIN dfc.producto pr \tINNER JOIN pr.subcategoriaProducto scp LEFT JOIN scp.cuentaContableDescuentoVenta ccdv\tWHERE fc.idFacturaCliente=:idFacturaCliente GROUP BY ccdv.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero) HAVING SUM(dfc.precioLinea-dfc.descuentoLinea)!=0";
			/* 996: */
			/* 997: */
			/* 998: */
			/* 999: */
			/* 1000: */
			/* 1001: */
			/* 1002: */
			/* 1003: */
			/* 1004:1218 */ Query query = this.em.createQuery(sql);
			/* 1005:1219 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
			/* 1006: */
			/* 1007:1221 */ return query.getResultList();
			/* 1008: */ }
		/* 1009: */ catch (IllegalArgumentException e)
		/* 1010: */ {
			/* 1011:1226 */ e.printStackTrace();
			/* 1012:1227 */ throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable",
					"cuentaContableDescuento");
			/* 1013: */ }
		/* 1014: */ }

	/* 1015: */
	/* 1016: */ public List<DetalleInterfazContable> getInterfazDevolucionCuentasDevolucion(int idFacturaCliente)
			/* 1017: */ throws ExcepcionAS2Financiero
	/* 1018: */ {
		/* 1019: */ try
		/* 1020: */ {
			/* 1021:1242 */ List<DetalleInterfazContable> lista = new ArrayList();
			/* 1022: */
			/* 1023: */
			/* 1024: */
			/* 1025: */
			/* 1026:1247 */ String sql = "SELECT new DetalleInterfazContable(scp.cuentaContableDevolucionVenta.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero),'',ROUND(SUM(dfc.precioLinea-dfc.descuentoLinea),2) )\tFROM DetalleFacturaCliente dfc\tINNER JOIN dfc.facturaCliente fc INNER JOIN fc.empresa em \tINNER JOIN fc.documento do\tINNER JOIN dfc.producto pr \tINNER JOIN pr.subcategoriaProducto scp\tWHERE fc.idFacturaCliente=:idFacturaCliente GROUP BY scp.cuentaContableDevolucionVenta.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', fc.numero) HAVING SUM(dfc.precioLinea)!=0";
			/* 1027: */
			/* 1028: */
			/* 1029: */
			/* 1030: */
			/* 1031: */
			/* 1032: */
			/* 1033: */
			/* 1034:1255 */ Query query = this.em.createQuery(sql);
			/* 1035:1256 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
			/* 1036: */
			/* 1037:1258 */ return query.getResultList();
			/* 1038: */ }
		/* 1039: */ catch (IllegalArgumentException e)
		/* 1040: */ {
			/* 1041:1263 */ throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable",
					"cuentaContableDevolucionVentas");
			/* 1042: */ }
		/* 1043: */ }

	/* 1044: */
	/* 1045: */ public List<DetalleFacturaCliente> obtenerDetalleDevolucionCliente(int idFacturaCliente)
	/* 1046: */ {
		/* 1047:1275 */ StringBuilder sql = new StringBuilder();
		/* 1048:1276 */ sql.append(" SELECT dfc FROM DetalleFacturaCliente dfc ");
		/* 1049:1277 */ sql.append(" INNER JOIN FETCH dfc.detalleDespachoCliente ddc ");
		/* 1050:1278 */ sql.append(" INNER JOIN FETCH dfc.facturaCliente fc ");
		/* 1051:1279 */ sql.append(" LEFT JOIN FETCH fc.direccionEmpresa de ");
		/* 1052:1280 */ sql.append(" JOIN FETCH dfc.producto pr ");
		/* 1053:1281 */ sql.append(" JOIN FETCH pr.unidad u ");
		/* 1054:1282 */ sql.append(" JOIN FETCH dfc.unidadVenta uv ");
		/* 1055:1283 */ sql.append(" LEFT JOIN FETCH ddc.bodega b ");
		/* 1056:1284 */ sql.append(" LEFT JOIN FETCH ddc.inventarioProducto ip ");
		/* 1057:1285 */ sql.append(" INNER JOIN FETCH ddc.despachoCliente dc ");
		/* 1058:1286 */ sql.append(" LEFT JOIN FETCH dc.ordenDespachoCliente odc ");
		/* 1059:1287 */ sql.append(" LEFT JOIN FETCH odc.sucursal su ");
		/* 1060:1288 */ sql.append(" LEFT JOIN FETCH ip.lote l ");
		/* 1061:1289 */ sql.append(" LEFT JOIN FETCH pr.presentacionProducto pres ");
		/* 1062:1290 */ sql.append(" WHERE fc.idFacturaCliente = :idFacturaCliente ");
		/* 1063:1291 */ sql.append(" AND pr.tipoProducto = :tipoProducto ");
		/* 1064:1292 */ sql
				.append(" AND CASE WHEN ddc != null then (ddc.cantidad - ddc.cantidadDevuelta) else 1 end > 0 ");
		/* 1065: */
		/* 1066:1294 */ Query query = this.em.createQuery(sql.toString());
		/* 1067:1295 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 1068:1296 */ query.setParameter("tipoProducto", TipoProducto.ARTICULO);
		/* 1069:1297 */ return query.getResultList();
		/* 1070: */ }

	/* 1071: */
	/* 1072: */ public long verificaExistenciaNumero(FacturaCliente facturaCliente)
	/* 1073: */ {
		/* 1074:1301 */ Documento documento = facturaCliente.getDocumento();
		/* 1075: */
		/* 1076:1303 */ StringBuilder sql = new StringBuilder();
		/* 1077:1304 */ sql.append(" SELECT COUNT(*) FROM FacturaCliente f ");
		/* 1078:1305 */ sql.append(" WHERE f.idFacturaCliente != :idFacturaCliente ");
		/* 1079:1306 */ sql.append(" AND f.numero =:numero ");
		/* 1080:1307 */ sql.append(" AND f.idOrganizacion = :idOrganizacion ");
		/* 1081:1308 */ if ((documento.getDocumentoBase() == DocumentoBase.NOTA_CREDITO_CLIENTE)
				|| (documento.getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE)) {
			/* 1082:1309 */ sql.append(
					" AND (f.documento.documentoBase=:documentoBaseNotaCredito OR f.documento.documentoBase=:documentoBaseDevolucion) ");
			/* 1083: */ } else {
			/* 1084:1311 */ sql.append(" AND f.documento.documentoBase=:documentoBase");
			/* 1085: */ }
		/* 1086:1314 */ Query query = this.em.createQuery(sql.toString());
		/* 1087:1315 */ query.setParameter("numero", facturaCliente.getNumero());
		/* 1088:1316 */ query.setParameter("idFacturaCliente", Integer.valueOf(facturaCliente.getIdFacturaCliente()));
		/* 1089:1317 */ query.setParameter("idOrganizacion", Integer.valueOf(facturaCliente.getIdOrganizacion()));
		/* 1090:1318 */ if ((documento.getDocumentoBase() == DocumentoBase.NOTA_CREDITO_CLIENTE)
				|| (documento.getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE))
		/* 1091: */ {
			/* 1092:1319 */ query.setParameter("documentoBaseNotaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
			/* 1093:1320 */ query.setParameter("documentoBaseDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
			/* 1094: */ }
		/* 1095: */ else
		/* 1096: */ {
			/* 1097:1322 */ query.setParameter("documentoBase", documento.getDocumentoBase());
			/* 1098: */ }
		/* 1099:1325 */ return ((Long) query.getSingleResult()).longValue();
		/* 1100: */ }

	/* 1101: */
	/* 1102: */ public void verificaNumeroFacturaRangoSecuencia(FacturaCliente facturaCliente)
			/* 1103: */ throws ExcepcionAS2Financiero
	/* 1104: */ {
		/* 1105:1336 */ StringBuilder sql = new StringBuilder();
		/* 1106:1337 */ sql.append("SELECT new Secuencia(s.desde, s.hasta) FROM AutorizacionDocumentoSRI a ");
		/* 1107:1338 */ sql.append(" INNER JOIN a.documento d ");
		/* 1108:1339 */ sql.append(" INNER JOIN a.secuencia s ");
		/* 1109:1340 */ sql.append(" WHERE d.idOrganizacion = :idOrganizacion  ");
		/* 1110:1341 */ sql.append(" AND d.idDocumento = :documento");
		/* 1111:1342 */ sql.append(" AND a.puntoDeVenta = :puntoDeVenta ");
		/* 1112: */
		/* 1113:1344 */ Query query = this.em.createQuery(sql.toString());
		/* 1114:1345 */ query.setParameter("idOrganizacion", Integer.valueOf(facturaCliente.getIdOrganizacion()));
		/* 1115:1346 */ query.setParameter("documento",
				Integer.valueOf(facturaCliente.getDocumento().getIdDocumento()));
		/* 1116:1347 */ query.setParameter("puntoDeVenta", AppUtil.getPuntoDeVenta());
		/* 1117: */
		/* 1118:1349 */ List<Secuencia> listaSecuencia = query.getResultList();
		/* 1119:1350 */ int desde = 0;
		/* 1120:1351 */ int hasta = 0;
		/* 1121:1352 */ for (Secuencia secuencia : listaSecuencia)
		/* 1122: */ {
			/* 1123:1353 */ desde = secuencia.getDesde();
			/* 1124:1354 */ hasta = secuencia.getHasta();
			/* 1125: */ }
		/* 1126:1357 */ String[] numeroFactura = facturaCliente.getNumero().split("-");
		/* 1127:1358 */ int numero = Integer.valueOf(numeroFactura[2]).intValue();
		/* 1128:1360 */ if ((numero < desde) || (numero > hasta)) {
			/* 1129:1362 */ throw new ExcepcionAS2Financiero("msg_error_numero_factura_fuera_rango",
					" " + desde + " - " + hasta + " ( " + facturaCliente.getNumero() + " ) ");
			/* 1130: */ }
		/* 1131: */ }

	/* 1132: */
	/* 1133: */ public FacturaCliente buscarPorDespachoCliente(Integer idDespachoCliente)
	/* 1134: */ {
		/* 1135:1375 */ Query query = this.em.createQuery(
				"SELECT f FROM FacturaCliente f JOIN f.documento d WHERE f.despachoCliente.idDespachoCliente = :idDespachoCliente AND f.estado != :estadoAnulado AND d.documentoBase =:documento");
		/* 1136: */
		/* 1137:1377 */ query.setParameter("idDespachoCliente", idDespachoCliente);
		/* 1138:1378 */ query.setParameter("estadoAnulado", Estado.ANULADO);
		/* 1139:1379 */ query.setParameter("documento", DocumentoBase.FACTURA_CLIENTE);
		/* 1140:1380 */ query.setMaxResults(1);
		/* 1141: */ try
		/* 1142: */ {
			/* 1143:1383 */ return (FacturaCliente) query.getSingleResult();
			/* 1144: */ }
		/* 1145: */ catch (NoResultException e) {
		}
		/* 1146:1385 */ return null;
		/* 1147: */ }

	/* 1148: */
	/* 1149: */ public FacturaCliente cargarDetalleADespachar(Integer idFacturaCliente)
	/* 1150: */ {
		/* 1151:1397 */ CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		/* 1152:1398 */ CriteriaQuery<FacturaCliente> cqDetalle = criteriaBuilder.createQuery(FacturaCliente.class);
		/* 1153:1399 */ Root<FacturaCliente> from = cqDetalle.from(FacturaCliente.class);
		/* 1154: */
		/* 1155:1401 */ from.fetch("subempresa", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
		/* 1156:1402 */ from.fetch("pedidoCliente", JoinType.LEFT).fetch("transportista", JoinType.LEFT);
		/* 1157:1403 */ from.fetch("proyecto", JoinType.LEFT);
		/* 1158:1404 */ from.fetch("documento", JoinType.LEFT);
		/* 1159: */
		/* 1160:1406 */ Fetch<Object, Object> empresa = from.fetch("empresa", JoinType.LEFT);
		/* 1161:1407 */ empresa.fetch("cliente", JoinType.LEFT).fetch("tipoOrdenDespacho", JoinType.LEFT);
		/* 1162: */
		/* 1163:1409 */ Fetch<Object, Object> direccion = from.fetch("direccionEmpresa", JoinType.LEFT);
		/* 1164:1410 */ direccion.fetch("ubicacion", JoinType.LEFT);
		/* 1165: */
		/* 1166:1412 */ Path<Integer> pathIdDetalle = from.get("idFacturaCliente");
		/* 1167:1413 */ cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, idFacturaCliente));
		/* 1168:1414 */ CriteriaQuery<FacturaCliente> select = cqDetalle.select(from);
		/* 1169: */
		/* 1170:1416 */ return (FacturaCliente) this.em.createQuery(select).getSingleResult();
		/* 1171: */ }

	/* 1172: */
	/* 1173: */ public FacturaCliente buscarFacturaCliente(Map<String, String> filters)/* 1174: */ throws ExcepcionAS2
	/* 1175: */ {
		/* 1176: */ try
		/* 1177: */ {
			/* 1178:1423 */ FacturaCliente facturaCliente = null;
			/* 1179: */
			/* 1180:1425 */ CriteriaBuilder cb = this.em.getCriteriaBuilder();
			/* 1181:1426 */ CriteriaQuery<FacturaCliente> cq = cb.createQuery(FacturaCliente.class);
			/* 1182:1427 */ Root<FacturaCliente> from = cq.from(FacturaCliente.class);
			/* 1183: */
			/* 1184:1429 */ List<Expression<?>> empresiones = obtenerExpresiones(filters, cb, from);
			/* 1185:1430 */ cq.where((Predicate[]) empresiones.toArray(new Predicate[empresiones.size()]));
			/* 1186: */
			/* 1187:1432 */ CriteriaQuery<FacturaCliente> select = cq.select(from);
			/* 1188:1433 */ return cargarDetalle(
					((FacturaCliente) this.em.createQuery(select).getSingleResult()).getId());
			/* 1189: */ }
		/* 1190: */ catch (NoResultException e)
		/* 1191: */ {
			/* 1192:1438 */ throw new ExcepcionAS2("msg_info_empresa_no_encontrada",
					" " + (String) filters.get("nombre"));
			/* 1193: */ }
		/* 1194: */ }

	/* 1195: */
	/* 1196: */ public void validarCambioNumeroFactura(String numero)/* 1197: */ throws ExcepcionAS2Ventas
	/* 1198: */ {
		/* 1199:1444 */ String sql = "select f.idFacturaCliente from FacturaCliente f where numero = :numero";
		/* 1200:1445 */ Query query = this.em.createQuery(sql);
		/* 1201:1446 */ query.setParameter("numero", numero);
		/* 1202: */
		/* 1203: */
		/* 1204:1449 */ List<FacturaCliente> lista = query.getResultList();
		/* 1205:1450 */ if (lista.size() != 0) {
			/* 1206:1451 */ throw new ExcepcionAS2Ventas("msg_error_numero_duplicado");
			/* 1207: */ }
		/* 1208: */ }

	/* 1209: */
	/* 1210: */ public List<FacturaCliente> getListaFacturaCliente(Date fechaDesde, Date fechaHasta,
			DocumentoBase documentoBase, int idDocumento, int idOrganizacion)
	/* 1211: */ {
		/* 1212:1465 */ StringBuilder filtro = new StringBuilder();
		/* 1213:1467 */ if (!documentoBase.equals(DocumentoBase.DEVOLUCION_CLIENTE))
		/* 1214: */ {
			/* 1215:1468 */ filtro.append(" AND fc.estado<>:estadoContabilizado ");
			/* 1216: */
			/* 1217:1470 */ filtro.append(" AND a IS NULL ");
			/* 1218: */ }
		/* 1219:1472 */ filtro.append(" AND fc.estado<>:estadoEsperaContingencia ");
		/* 1220:1473 */ filtro.append(" AND fc.estado<>:estadoEspera ");
		/* 1221:1474 */ filtro.append(" AND fc.estado<>:estadoRechazado ");
		/* 1222: */
		/* 1223: */
		/* 1224: */
		/* 1225:1478 */ filtro.append(" AND ( d.indicadorDocumentoTributario = false OR fc.estado<>:estadoElaborado )");
		/* 1226:1479 */ if (!documentoBase.equals(DocumentoBase.FACTURA_CLIENTE)) {
			/* 1227:1480 */ filtro.append(" AND fc.interfazContableProceso IS NULL ");
			/* 1228: */ }
		/* 1229:1483 */ StringBuilder sql = new StringBuilder();
		/* 1230:1484 */ sql.append(" SELECT new FacturaCliente(fc.idFacturaCliente, fc.numero, fc.fecha) ");
		/* 1231:1485 */ sql.append(" from FacturaCliente fc ");
		/* 1232:1486 */ sql.append(" INNER JOIN fc.documento d ");
		/* 1233:1487 */ sql.append(" LEFT JOIN fc.asiento a ");
		/* 1234:1488 */ sql.append(" WHERE fc.fecha BETWEEN :fechaDesde AND :fechaHasta");
		/* 1235:1489 */ sql.append(" AND fc.indicadorSaldoInicial=false ");
		/* 1236:1490 */ sql.append(" AND d.documentoBase=:documentoBase ");
		/* 1237:1491 */ sql.append(" AND fc.estado<>:estadoAnulado  " + filtro.toString());
		/* 1238:1492 */ sql.append(" AND (d.idDocumento = :idDocumento OR :idDocumento=0) ");
		/* 1239:1493 */ sql.append(" AND fc.idOrganizacion = :idOrganizacion ");
		/* 1240: */
		/* 1241:1495 */ Query query = this.em.createQuery(sql.toString());
		/* 1242:1496 */ query.setParameter("fechaDesde", fechaDesde);
		/* 1243:1497 */ query.setParameter("fechaHasta", fechaHasta);
		/* 1244:1498 */ query.setParameter("documentoBase", documentoBase);
		/* 1245:1499 */ query.setParameter("estadoAnulado", Estado.ANULADO);
		/* 1246:1500 */ query.setParameter("estadoElaborado", Estado.ELABORADO);
		/* 1247:1501 */ query.setParameter("estadoEsperaContingencia", Estado.EN_ESPERA_CONTINGENCIA);
		/* 1248:1502 */ query.setParameter("estadoEspera", Estado.EN_ESPERA);
		/* 1249:1503 */ query.setParameter("estadoRechazado", Estado.RECHAZADO_SRI);
		/* 1250:1504 */ if (!documentoBase.equals(DocumentoBase.DEVOLUCION_CLIENTE)) {
			/* 1251:1505 */ query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
			/* 1252: */ }
		/* 1253:1507 */ query.setParameter("idDocumento", Integer.valueOf(idDocumento));
		/* 1254:1508 */ query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
		/* 1255: */
		/* 1256:1510 */ return query.getResultList();
		/* 1257: */ }

	/* 1258: */
	/* 1259: */ public List<DetalleInterfazContableProceso> getInterfazDevolucionCostoVentasDimensiones(
			List<Integer> listaDevolucionCliente, ProcesoContabilizacionEnum procesoContabilizacionEnum)
			/* 1260: */ throws ExcepcionAS2Financiero
	/* 1261: */ {
		/* 1262:1517 */ String valores = "";
		/* 1263:1518 */ valores = "ROUND(SUM(ip.costo),2)";
		/* 1264:1519 */ String descripcion = "";
		/* 1265:1520 */ String grupoDescripcion = "";
		/* 1266:1521 */ if (listaDevolucionCliente.size() == 1)
		/* 1267: */ {
			/* 1268:1522 */ descripcion = "''";
			/* 1269: */ }
		/* 1270: */ else
		/* 1271: */ {
			/* 1272:1524 */ descripcion = "CONCAT(fc.numero)";
			/* 1273:1525 */ grupoDescripcion = "," + descripcion;
			/* 1274: */ }
		/* 1275:1528 */ StringBuilder sql = new StringBuilder();
		/* 1276: */
		/* 1277: */
		/* 1278:1531 */ sql.append(
				"SELECT new DetalleInterfazContableProcesoDespachos(d.idDocumento, d.nombre, s.idSucursal, s.nombre,");
		/* 1279:1532 */ sql.append(
				" ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa, e.nombreFiscal, cp.idCategoriaProducto, cp.nombre, ");
		/* 1280:1533 */ sql
				.append(" sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, b.idBodega, b.nombre, ");
		/* 1281:1534 */ sql.append(" se.idSubempresa, se.codigo, mncc.idMotivoNotaCreditoCliente, mncc.nombre,"
				+ descripcion + "," + valores + " )");
		/* 1282:1535 */ sql.append(" FROM DetalleFacturaCliente dfc ");
		/* 1283:1536 */ sql.append(" LEFT OUTER JOIN dfc.facturaCliente fc ");
		/* 1284:1537 */ sql.append(" INNER JOIN dfc.inventarioProducto ip");
		/* 1285:1538 */ sql.append(" INNER JOIN ip.bodega b");
		/* 1286:1539 */ sql.append(" INNER JOIN ip.producto p ");
		/* 1287:1540 */ sql.append(" INNER JOIN p.subcategoriaProducto sp ");
		/* 1288:1541 */ sql.append(" INNER JOIN sp.categoriaProducto cp ");
		/* 1289:1542 */ sql.append(" INNER JOIN fc.documento d ");
		/* 1290:1543 */ sql.append(" LEFT JOIN fc.motivoNotaCreditoCliente mncc ");
		/* 1291:1544 */ sql.append(" INNER JOIN fc.empresa e ");
		/* 1292:1545 */ sql.append(" INNER JOIN fc.sucursal s ");
		/* 1293:1546 */ sql.append(" INNER JOIN e.categoriaEmpresa ce ");
		/* 1294:1547 */ sql.append(" LEFT JOIN fc.subempresa se");
		/* 1295:1548 */ sql.append(" WHERE fc.idFacturaCliente in (:listaDevolucionCliente) ");
		/* 1296:1549 */ sql.append(" GROUP BY d.idDocumento, d.nombre, s.idSucursal, s.nombre,");
		/* 1297:1550 */ sql.append(
				" ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa, e.nombreFiscal, cp.idCategoriaProducto, cp.nombre, ");
		/* 1298:1551 */ sql
				.append(" sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, b.idBodega, b.nombre, ");
		/* 1299:1552 */ sql
				.append(" se.idSubempresa, se.codigo, mncc.idMotivoNotaCreditoCliente, mncc.nombre" + grupoDescripcion);
		/* 1300:1553 */ sql.append(" HAVING " + valores + " <> 0");
		/* 1301: */
		/* 1302:1555 */ Query query = this.em.createQuery(sql.toString());
		/* 1303:1556 */ query.setParameter("listaDevolucionCliente", listaDevolucionCliente);
		/* 1304:1557 */ return query.getResultList();
		/* 1305: */ }

	/* 1306: */
	/* 1307: */ @Deprecated
	/* 1308: */ public List getReporteNotaCreditoCliente(int idFacturaCliente)
	/* 1309: */ {
		/* 1310:1572 */ StringBuilder sql = new StringBuilder();
		/* 1311:1573 */ sql.append(
				" SELECT e.nombreFiscal, CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4,' ',u.direccion5), ");
		/* 1312:1574 */ sql.append(
				" e.identificacion, f.fecha, d.cantidad, pr.codigo, pr.nombreComercial, d.precio, f.total, f.descuento, f.impuesto, ");
		/* 1313:1575 */ sql
				.append(" de.telefono1, f.descripcion, YEAR(f.fecha), MONTH(f.fecha), DAY(f.fecha), pr.nombre, ");
		/* 1314:1576 */ sql.append(" e.codigo, ci.nombre, un.nombre, pr.peso, ");
		/* 1315:1577 */ sql.append(
				" pr.codigoAlterno, pr.codigoComercial, pr.codigoBarras, f.numero, e.nombreComercial,fp.numero,mnc.nombre, coalesce(l.codigo,''), dc.numero, ");
		/* 1316: */
		/* 1317:1579 */ sql.append(
				" fcSRI.autorizacion, fcSRI.fechaAutorizacion, fcSRI.fechaCaducidad,'', f.idFacturaCliente, f.usuarioCreacion, f.fechaCreacion, 1, ");
		/* 1318: */
		/* 1319: */
		/* 1320: */
		/* 1321:1583 */ sql.append(
				" o.razonSocial, o.identificacion, fcSRI.fechaAutorizacion, fcSRI.claveAcceso, oc.numeroResolucionContribuyente,");
		/* 1322:1584 */ sql.append(
				" oc.indicadorObligadoContabilidad, fcSRI.direccionMatriz, fcSRI.direccionSucursal, fcSRI.ambiente, fcSRI.tipoEmision,fcp.numero,fcp.fecha,moti.nombre,fcSRI.email,fcSRI.baseImponibleTarifaCero, fcSRI.baseImponibleDiferenteCero, d.descuento,");
		/* 1323: */
		/* 1324: */
		/* 1325: */
		/* 1326:1588 */ sql.append(
				" d.peso, d.precioLinea, d.descuentoLinea, fcSRI.montoIRBPNR, fcSRI.montoIva, cli.referencia, fp.referencia8, cli.referencia2, fp.referencia9, fcSRI.montoIce ,");
		/* 1327: */
		/* 1328: */
		/* 1329:1591 */ sql.append(
				" fp.referencia2, f.descuentoImpuesto, fcSRI.codigoFormaPagoSRI, f.idOrganizacion, CONCAT(ag.nombre1, ' ', ag.nombre2), f.referencia8");
		/* 1330: */
		/* 1331:1593 */ sql.append(" FROM DetalleFacturaCliente d ");
		/* 1332:1594 */ sql.append(" LEFT JOIN d.inventarioProducto ip ");
		/* 1333:1595 */ sql.append(" LEFT JOIN ip.lote l ");
		/* 1334:1596 */ sql.append(" LEFT JOIN d.facturaCliente f ");
		/* 1335:1597 */ sql.append(" LEFT JOIN f.facturaClientePadre fp ");
		/* 1336:1598 */ sql.append(" LEFT JOIN fp.despachoCliente dc ");
		/* 1337:1599 */ sql.append(" LEFT JOIN f.motivoNotaCreditoCliente mnc ");
		/* 1338:1600 */ sql.append(" LEFT JOIN f.empresa e ");
		/* 1339:1601 */ sql.append(" LEFT JOIN e.cliente cli ");
		/* 1340:1602 */ sql.append(" LEFT JOIN f.direccionEmpresa de  ");
		/* 1341:1603 */ sql.append(" LEFT JOIN de.ubicacion u ");
		/* 1342:1604 */ sql.append(" LEFT JOIN de.ciudad ci ");
		/* 1343:1605 */ sql.append(" LEFT JOIN d.producto pr  ");
		/* 1344:1606 */ sql.append(" LEFT JOIN d.unidadVenta un ");
		/* 1345:1607 */ sql.append(" LEFT JOIN f.facturaClienteSRI fcSRI, Organizacion o ");
		/* 1346:1608 */ sql.append(" LEFT JOIN f.facturaClientePadre fcp ");
		/* 1347:1609 */ sql.append(" LEFT JOIN f.motivoNotaCreditoCliente moti ");
		/* 1348:1610 */ sql.append(" LEFT JOIN f.agenteComercial ag ");
		/* 1349:1611 */ sql.append(" JOIN o.organizacionConfiguracion oc");
		/* 1350: */
		/* 1351:1613 */ sql
				.append(" WHERE o.idOrganizacion = f.idOrganizacion AND f.idFacturaCliente = :idFacturaCliente ");
		/* 1352:1614 */ sql.append(" ORDER BY pr.nombreComercial ");
		/* 1353: */
		/* 1354:1616 */ Query query = this.em.createQuery(sql.toString());
		/* 1355:1617 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 1356:1618 */ return query.getResultList();
		/* 1357: */ }

	/* 1358: */
	/* 1359: */ public void actualizarEstadoContabilizadoFacturaClienteInterfazContable(
			InterfazContableProceso interfazContableProceso, List<Integer> listaFacturaClienteInterfazContable)
	/* 1360: */ {
		/* 1361:1631 */ String sql = "UPDATE FacturaCliente fc  SET fc.estado=:estado, fc.interfazContableProceso = :interfazContableProceso, fechaContabilizacion = :fechaContabilizacion\tWHERE fc.idFacturaCliente in (:listaFacturaClienteInterfazContable)";
		/* 1362: */
		/* 1363: */
		/* 1364: */
		/* 1365:1635 */ Query query = this.em.createQuery(sql);
		/* 1366:1636 */ query.setParameter("estado", Estado.CONTABILIZADO);
		/* 1367:1637 */ query.setParameter("interfazContableProceso", interfazContableProceso);
		/* 1368:1638 */ query.setParameter("fechaContabilizacion", interfazContableProceso.getFechaHasta());
		/* 1369:1639 */ query.setParameter("listaFacturaClienteInterfazContable", listaFacturaClienteInterfazContable);
		/* 1370:1640 */ query.executeUpdate();
		/* 1371: */ }

	/* 1372: */
	/* 1373: */ public List<CuentaPorCobrar> obtenerFacturasPendientesLiquidacionCuentaPorCobrar(int idEmpresa,
			int idFacturaCliente, Date fechaCobroLiquidacion, BigDecimal tolerancia)
	/* 1374: */ {
		/* 1375:1652 */ StringBuilder sql = new StringBuilder();
		/* 1376:1653 */ sql.append("SELECT cxc FROM CuentaPorCobrar cxc LEFT JOIN FETCH cxc.facturaCliente fc");
		/* 1377:1654 */ sql.append(" WHERE (fc.empresa.idEmpresa=:idEmpresa OR :idEmpresa = 0) ");
		/* 1378:1655 */ sql.append(" AND fc.estado<>:estado ");
		/* 1379:1656 */ sql.append(" AND (fc.idFacturaCliente=:idFacturaCliente OR :idFacturaCliente=0) ");
		/* 1380:1657 */ sql.append(" AND (cxc.saldo-cxc.valorBloqueado <> 0 AND cxc.saldo <=:tolerancia )");
		/* 1381:1658 */ sql.append(" AND  EXISTS ");
		/* 1382:1659 */ sql.append(" ( ");
		/* 1383:1660 */ sql.append(" \tSELECT 1 FROM DetalleCobro d ");
		/* 1384:1661 */ sql.append(" \tJOIN d.cobro c JOIN d.cuentaPorCobrar cxcc");
		/* 1385:1662 */ sql.append(" \tJOIN cxcc.facturaCliente fcc");
		/* 1386:1663 */ sql.append(" \tWHERE c.fecha <= :fechaCobroLiquidacion AND fcc = fc");
		/* 1387:1664 */ sql.append(" ) ");
		/* 1388:1665 */ sql.append(" ORDER BY fc.fecha ASC ");
		/* 1389:1666 */ Query query = this.em.createQuery(sql.toString());
		/* 1390:1667 */ query.setParameter("estado", Estado.ANULADO);
		/* 1391:1668 */ query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
		/* 1392:1669 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 1393:1670 */ query.setParameter("fechaCobroLiquidacion", fechaCobroLiquidacion);
		/* 1394:1671 */ query.setParameter("tolerancia", tolerancia);
		/* 1395: */
		/* 1396:1673 */ return query.getResultList();
		/* 1397: */ }

	/* 1398: */
	/* 1399: */ public List<DetalleInterfazContableProceso> getInterfazVentasDimensiones(List<Integer> listaFacturaCliente, ProcesoContabilizacionEnum procesoContabilizacionEnum)
/* 1400:     */     throws ExcepcionAS2Financiero
/* 1401:     */   {
/* 1402:1691 */     List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 1403:     */     
/* 1404:1693 */     String valores = "";
/* 1405:1694 */     String valoresHaving = "";
/* 1406:1695 */     String selectImpuesto = "";
/* 1407:1696 */     String selectVentas = " 0,'', 0, '',";
/* 1408:1697 */     String grupoImpuesto = "";
/* 1409:1698 */     String grupoVentas = "";
/* 1410:1699 */     String from = "";
/* 1411:1700 */     switch (
		1.$SwitchMap$com$asinfo$as2$enumeraciones$ProcesoContabilizacionEnum[procesoContabilizacionEnum.ordinal()])
/* 1412:     */     {
/* 1413:     */     case 1: 
/* 1414:1704 */       List<DetalleInterfazContableProceso> listaTmp = getInterfazPrecioLineaCXC_Cliente(listaFacturaCliente);
/* 1415:1705 */       Map<Integer, List<DetalleInterfazContableProceso>> hashDetalleInterfazContableProceso = new HashMap();
/* 1416:1706 */       for (Iterator localIterator = listaTmp.iterator(); localIterator.hasNext();)
/* 1417:     */       {
/* 1418:1706 */         dicp = (DetalleInterfazContableProceso)localIterator.next();
/* 1419:1707 */         if (hashDetalleInterfazContableProceso.containsKey(dicp.getIdFacturaCliente()))
/* 1420:     */         {
/* 1421:1708 */           ((List)hashDetalleInterfazContableProceso.get(dicp.getIdFacturaCliente())).add(dicp);
/* 1422:     */         }
/* 1423:     */         else
/* 1424:     */         {
/* 1425:1710 */           List<DetalleInterfazContableProceso> aux = new ArrayList();
/* 1426:1711 */           aux.add(dicp);
/* 1427:1712 */           hashDetalleInterfazContableProceso.put(dicp.getIdFacturaCliente(), aux);
/* 1428:     */         }
/* 1429:     */       }
/* 1430:     */       DetalleInterfazContableProceso dicp;
/* 1431:1718 */       Object idsRegistrados = new HashSet();
/* 1432:1719 */       for (DetalleInterfazContableProceso dicp : getInterfazDescuentoImpuestoCXC_Cliente(listaFacturaCliente)) {
/* 1433:1720 */         if (!((Set)idsRegistrados).contains(dicp.getIdFacturaCliente()))
/* 1434:     */         {
/* 1435:1721 */           ((DetalleInterfazContableProceso)((List)hashDetalleInterfazContableProceso.get(dicp.getIdFacturaCliente())).get(0)).setValor(
/* 1436:1722 */             ((DetalleInterfazContableProceso)((List)hashDetalleInterfazContableProceso.get(dicp.getIdFacturaCliente())).get(0)).getValor().subtract(dicp.getDescuentoImpuesto()));
/* 1437:1723 */           ((Set)idsRegistrados).add(dicp.getIdFacturaCliente());
/* 1438:     */         }
/* 1439:     */       }
/* 1440:1727 */       for (Integer integer : hashDetalleInterfazContableProceso.keySet()) {
/* 1441:1728 */         lista.addAll((Collection)hashDetalleInterfazContableProceso.get(integer));
/* 1442:     */       }
/* 1443:1732 */       valores = "CASE WHEN i.tipoImpuesto = 0 then sum((dfc.precioLinea-dfc.descuentoLinea+dfc.iceLinea)*((coalesce(ipfc.porcentajeImpuesto,0)/100))) ";
/* 1444:1733 */       valores = valores + " else sum((dfc.cantidad)*(coalesce(presp.cantidadUnidades,1))*(coalesce(ipfc.porcentajeImpuesto,0))) end";
/* 1445:     */       
/* 1446:1735 */       valoresHaving = valores;
/* 1447:1736 */       selectImpuesto = " i.idImpuesto,i.nombre, ";
/* 1448:1737 */       grupoImpuesto = " , i.tipoImpuesto, i.idImpuesto,i.nombre";
/* 1449:1738 */       from = " FROM ImpuestoProductoFacturaCliente ipfc  LEFT OUTER JOIN ipfc.impuesto i  INNER JOIN ipfc.detalleFacturaCliente dfc ";
/* 1450:1739 */       break;
/* 1451:     */     case 2: 
/* 1452:1743 */       valores = "sum(dfc.precioLinea)";
/* 1453:1744 */       valoresHaving = valores;
/* 1454:1745 */       selectImpuesto = " 0,'', ";
/* 1455:1746 */       selectVentas = " proy.idDimensionContable,proy.nombre, ci.idCategoriaImpuesto, ci.nombre,";
/* 1456:1747 */       grupoVentas = ", proy.idDimensionContable,proy.nombre, ci.idCategoriaImpuesto, ci.nombre ";
/* 1457:1748 */       grupoImpuesto = "";
/* 1458:1749 */       from = " FROM DetalleFacturaCliente dfc ";
/* 1459:1750 */       break;
/* 1460:     */     case 3: 
/* 1461:1754 */       valores = "sum(dfc.descuentoLinea)";
/* 1462:1755 */       valoresHaving = valores;
/* 1463:1756 */       selectImpuesto = " 0,'', ";
/* 1464:1757 */       selectVentas = " 0 ,'' , ci.idCategoriaImpuesto, ci.nombre,";
/* 1465:1758 */       grupoVentas = ", ci.idCategoriaImpuesto, ci.nombre ";
/* 1466:1759 */       grupoImpuesto = "";
/* 1467:1760 */       from = " FROM DetalleFacturaCliente dfc ";
/* 1468:1761 */       break;
/* 1469:     */     case 4: 
/* 1470:1766 */       valores = "CASE WHEN i.tipoImpuesto = 0 then sum((dfc.precioLinea-dfc.descuentoLinea+dfc.iceLinea)*(coalesce(ipfc.porcentajeImpuesto,0)/100))  else sum((dfc.cantidad)*(coalesce(presp.cantidadUnidades,1))*(coalesce(ipfc.porcentajeImpuesto,0))) end";
/* 1471:     */       
/* 1472:1768 */       valoresHaving = valores;
/* 1473:1769 */       selectImpuesto = " i.idImpuesto,i.nombre, ";
/* 1474:1770 */       grupoImpuesto = " , i.tipoImpuesto, i.idImpuesto,i.nombre";
/* 1475:1771 */       from = " FROM ImpuestoProductoFacturaCliente ipfc  LEFT OUTER JOIN ipfc.impuesto i  RIGHT JOIN ipfc.detalleFacturaCliente dfc ";
/* 1476:1772 */       break;
/* 1477:     */     case 5: 
/* 1478:1775 */       valores = "sum(dfc.iceLinea)";
/* 1479:1776 */       valoresHaving = valores;
/* 1480:1777 */       selectImpuesto = " 0,'', ";
/* 1481:1778 */       grupoImpuesto = "";
/* 1482:1779 */       from = " FROM DetalleFacturaCliente dfc ";
/* 1483:1780 */       break;
/* 1484:     */     case 6: 
/* 1485:1782 */       valores = "sum(fc.descuentoImpuesto)";
/* 1486:1783 */       valoresHaving = valores;
/* 1487:1784 */       selectImpuesto = " 0,'', ";
/* 1488:1785 */       grupoImpuesto = "";
/* 1489:1786 */       from = " FROM FacturaCliente fc ";
/* 1490:1787 */       break;
/* 1491:     */     case 7: 
/* 1492:1789 */       valores = "sum(fc.fleteInternacional)";
/* 1493:1790 */       valoresHaving = valores;
/* 1494:1791 */       selectImpuesto = " 0,'', ";
/* 1495:1792 */       grupoImpuesto = "";
/* 1496:1793 */       from = " FROM FacturaCliente fc ";
/* 1497:     */     }
/* 1498:1797 */     StringBuilder sql = new StringBuilder();
/* 1499:     */     
/* 1500:1799 */     String descripcion = "CONCAT(d.nombre,' #', fc.numero)";
/* 1501:1800 */     String grupoDescripcion = "," + descripcion;
/* 1502:     */     try
/* 1503:     */     {
/* 1504:1803 */       sql.append("SELECT new DetalleInterfazContableProcesoVentas(d.idDocumento, d.nombre, s.idSucursal, s.nombre, ce.idCategoriaEmpresa,");
/* 1505:1804 */       sql.append(" ce.nombre,e.idEmpresa, e.nombreFiscal,");
/* 1506:1805 */       if ((ProcesoContabilizacionEnum.DESCUENTO_IMPUESTO_VENTAS.equals(procesoContabilizacionEnum)) || (ProcesoContabilizacionEnum.COSTO_FLETE.equals(procesoContabilizacionEnum))) {
/* 1507:1806 */         sql.append(" 0, '', 0, '', 0, '',");
/* 1508:     */       } else {
/* 1509:1808 */         sql.append(" cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre,");
/* 1510:     */       }
/* 1511:1810 */       sql.append(" c.idCanal, c.nombre, se.idSubempresa, se.codigo, z.idZona, z.nombre, ");
/* 1512:1811 */       sql.append(selectImpuesto + selectVentas + descripcion + ", " + descripcion + ", " + valores + " )");
/* 1513:1812 */       sql.append(from);
/* 1514:1816 */       if ((!ProcesoContabilizacionEnum.DESCUENTO_IMPUESTO_VENTAS.equals(procesoContabilizacionEnum)) && (!ProcesoContabilizacionEnum.COSTO_FLETE.equals(procesoContabilizacionEnum)))
/* 1515:     */       {
/* 1516:1817 */         sql.append(" INNER JOIN dfc.producto p ");
/* 1517:1818 */         sql.append(" LEFT JOIN p.presentacionProducto presp ");
/* 1518:1819 */         sql.append(" LEFT JOIN p.categoriaImpuesto ci ");
/* 1519:1820 */         sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 1520:1821 */         sql.append(" INNER JOIN sp.categoriaProducto cp ");
/* 1521:1822 */         sql.append(" INNER JOIN dfc.facturaCliente fc ");
/* 1522:     */       }
/* 1523:1825 */       sql.append(" INNER JOIN fc.documento d ");
/* 1524:1826 */       sql.append(" INNER JOIN fc.sucursal s ");
/* 1525:1827 */       sql.append(" LEFT JOIN fc.canal c ");
/* 1526:1828 */       sql.append(" LEFT JOIN fc.zona z ");
/* 1527:1829 */       sql.append(" LEFT JOIN fc.proyecto proy ");
/* 1528:1830 */       sql.append(" LEFT JOIN fc.subempresa se");
/* 1529:1831 */       sql.append(" LEFT JOIN fc.empresa e ");
/* 1530:1832 */       sql.append(" LEFT JOIN e.categoriaEmpresa ce ");
/* 1531:1833 */       sql.append(" WHERE fc.idFacturaCliente in (:listaFacturaCliente)");
/* 1532:1834 */       sql.append(" GROUP BY d.idDocumento, d.nombre, s.idSucursal, s.nombre, ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa, e.nombreFiscal,");
/* 1533:1835 */       if ((!ProcesoContabilizacionEnum.DESCUENTO_IMPUESTO_VENTAS.equals(procesoContabilizacionEnum)) && (!ProcesoContabilizacionEnum.COSTO_FLETE.equals(procesoContabilizacionEnum))) {
/* 1534:1836 */         sql.append(" cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre,");
/* 1535:     */       }
/* 1536:1838 */       sql.append(" c.idCanal, c.nombre, se.idSubempresa,se.codigo, z.idZona, z.nombre ");
/* 1537:1839 */       sql.append(grupoImpuesto + grupoVentas + grupoDescripcion);
/* 1538:1840 */       sql.append(" HAVING " + valoresHaving + " <> 0");
/* 1539:     */       
/* 1540:1842 */       Query query = this.em.createQuery(sql.toString());
/* 1541:1843 */       query.setParameter("listaFacturaCliente", listaFacturaCliente);
/* 1542:1844 */       lista.addAll(query.getResultList());
/* 1543:1845 */       return lista;
/* 1544:     */     }
/* 1545:     */     catch (IllegalArgumentException e)
/* 1546:     */     {
/* 1547:1848 */       e.printStackTrace();
/* 1548:1849 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableCliente");
/* 1549:     */     }
/* 1550:     */   }

	/* 1551: */
	/* 1552: */ private List<DetalleInterfazContableProceso> getInterfazDescuentoImpuestoCXC_Cliente(
			List<Integer> listaFacturaCliente)/* 1553: */ throws ExcepcionAS2Financiero
	/* 1554: */ {
		/* 1555:1856 */ String valores = "";
		/* 1556:1857 */ String valoresHaving = "";
		/* 1557:1858 */ String selectImpuesto = "";
		/* 1558:1859 */ String grupoImpuesto = "";
		/* 1559:1860 */ String selectProyecto = " 0,'', 0,'',";
		/* 1560:1861 */ String from = "";
		/* 1561:1862 */ valores = " SUM(fc.descuentoImpuesto) ";
		/* 1562:1863 */ valoresHaving = valores;
		/* 1563:1864 */ selectImpuesto = " 0,'', ";
		/* 1564:1865 */ grupoImpuesto = "";
		/* 1565:1866 */ from = " FROM FacturaCliente fc ";
		/* 1566: */
		/* 1567:1868 */ StringBuilder sql = new StringBuilder();
		/* 1568: */
		/* 1569:1870 */ String descripcion = "CONCAT(d.nombre,' #', fc.numero)";
		/* 1570:1871 */ String grupoDescripcion = "," + descripcion;
		/* 1571: */ try
		/* 1572: */ {
			/* 1573:1874 */ sql.append(
					"SELECT new DetalleInterfazContableProcesoVentas(d.idDocumento, d.nombre, s.idSucursal, s.nombre, ce.idCategoriaEmpresa,");
			/* 1574:1875 */ sql.append(" ce.nombre, e.idEmpresa, e.nombreFiscal,");
			/* 1575:1876 */ sql.append(
					" 0, '', 0, '', 0, '', c.idCanal, c.nombre, se.idSubempresa, se.codigo, z.idZona, z.nombre, ");
			/* 1576:1877 */ sql.append(selectImpuesto + selectProyecto + descripcion + ", " + descripcion
					+ ", SUM(fc.descuentoImpuesto)*0, " + valores + ", fc.idFacturaCliente )");
			/* 1577: */
			/* 1578:1879 */ sql.append(from);
			/* 1579:1880 */ sql.append(" INNER JOIN fc.documento d ");
			/* 1580:1881 */ sql.append(" INNER JOIN fc.sucursal s ");
			/* 1581:1882 */ sql.append(" LEFT JOIN fc.canal c ");
			/* 1582:1883 */ sql.append(" LEFT JOIN fc.zona z ");
			/* 1583:1884 */ sql.append(" LEFT JOIN fc.subempresa se");
			/* 1584:1885 */ sql.append(" LEFT JOIN fc.empresa e ");
			/* 1585:1886 */ sql.append(" LEFT JOIN e.categoriaEmpresa ce ");
			/* 1586:1887 */ sql.append(" WHERE fc.idFacturaCliente in (:listaFacturaCliente)");
			/* 1587:1888 */ sql.append(
					" GROUP BY d.idDocumento, d.nombre, s.idSucursal, s.nombre, ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa, e.nombreFiscal,");
			/* 1588:1889 */ sql.append(
					" c.idCanal, c.nombre, se.idSubempresa,se.codigo, z.idZona, z.nombre, fc.idFacturaCliente ");
			/* 1589:1890 */ sql.append(grupoImpuesto + grupoDescripcion);
			/* 1590:1891 */ sql.append(" HAVING " + valoresHaving + " <> 0");
			/* 1591: */
			/* 1592:1893 */ Query query = this.em.createQuery(sql.toString());
			/* 1593:1894 */ query.setParameter("listaFacturaCliente", listaFacturaCliente);
			/* 1594:1895 */ return query.getResultList();
			/* 1595: */ }
		/* 1596: */ catch (IllegalArgumentException e)
		/* 1597: */ {
			/* 1598:1898 */ e.printStackTrace();
			/* 1599:1899 */ throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable",
					"cuentaContableCliente");
			/* 1600: */ }
		/* 1601: */ }

	/* 1602: */
	/* 1603: */ private List<DetalleInterfazContableProceso> getInterfazPrecioLineaCXC_Cliente(
			List<Integer> listaFacturaCliente)/* 1604: */ throws ExcepcionAS2Financiero
	/* 1605: */ {
		/* 1606:1905 */ String valores = "";
		/* 1607:1906 */ String valoresHaving = "";
		/* 1608:1907 */ String selectImpuesto = "";
		/* 1609:1908 */ String grupoImpuesto = "";
		/* 1610:1909 */ String selectProyecto = " 0,'', 0,'',";
		/* 1611:1910 */ String from = "";
		/* 1612: */
		/* 1613: */
		/* 1614:1913 */ valores = " (sum(dfc.precioLinea-dfc.descuentoLinea+dfc.iceLinea) + sum(fc.valorOtros + fc.fleteInternacional)/count(*)) ";
		/* 1615:1914 */ valoresHaving = valores;
		/* 1616:1915 */ selectImpuesto = " 0,'', ";
		/* 1617:1916 */ grupoImpuesto = "";
		/* 1618:1917 */ from = " FROM DetalleFacturaCliente dfc ";
		/* 1619: */
		/* 1620:1919 */ StringBuilder sql = new StringBuilder();
		/* 1621: */
		/* 1622:1921 */ String descripcion = "CONCAT(d.nombre,' #', fc.numero)";
		/* 1623:1922 */ String grupoDescripcion = "," + descripcion;
		/* 1624: */ try
		/* 1625: */ {
			/* 1626:1925 */ sql.append(
					"SELECT new DetalleInterfazContableProcesoVentas(d.idDocumento, d.nombre, s.idSucursal, s.nombre,");
			/* 1627:1926 */ sql.append(" ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa, e.nombreFiscal,");
			/* 1628:1927 */ sql.append(
					" cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre,");
			/* 1629:1928 */ sql.append(" c.idCanal, c.nombre, se.idSubempresa, se.codigo, z.idZona, z.nombre,");
			/* 1630:1929 */ sql.append(selectImpuesto + selectProyecto + descripcion + ", " + descripcion + ", "
					+ valores
					+ ", sum((dfc.precioLinea-dfc.descuentoLinea+dfc.iceLinea) + fc.valorOtros)*0, fc.idFacturaCliente )");
			/* 1631: */
			/* 1632:1931 */ sql.append(from);
			/* 1633:1932 */ sql.append(" INNER JOIN dfc.producto p ");
			/* 1634:1933 */ sql.append(" LEFT JOIN p.presentacionProducto presp ");
			/* 1635:1934 */ sql.append(" INNER JOIN p.subcategoriaProducto sp ");
			/* 1636:1935 */ sql.append(" INNER JOIN sp.categoriaProducto cp ");
			/* 1637:1936 */ sql.append(" INNER JOIN dfc.facturaCliente fc ");
			/* 1638:1937 */ sql.append(" INNER JOIN fc.documento d ");
			/* 1639:1938 */ sql.append(" INNER JOIN fc.sucursal s ");
			/* 1640:1939 */ sql.append(" LEFT JOIN fc.canal c ");
			/* 1641:1940 */ sql.append(" LEFT JOIN fc.zona z ");
			/* 1642:1941 */ sql.append(" LEFT JOIN fc.subempresa se");
			/* 1643:1942 */ sql.append(" LEFT JOIN fc.empresa e ");
			/* 1644:1943 */ sql.append(" LEFT JOIN e.categoriaEmpresa ce ");
			/* 1645:1944 */ sql.append(" WHERE fc.idFacturaCliente in (:listaFacturaCliente)");
			/* 1646:1945 */ sql.append(
					" GROUP BY d.idDocumento, d.nombre, s.idSucursal, s.nombre, ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa, e.nombreFiscal,");
			/* 1647:1946 */ sql.append(
					" cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre,");
			/* 1648:1947 */ sql
					.append("c.idCanal, c.nombre, se.idSubempresa,se.codigo, z.idZona, z.nombre, fc.idFacturaCliente");
			/* 1649:1948 */ sql.append(grupoImpuesto + grupoDescripcion);
			/* 1650:1949 */ sql.append(" HAVING " + valoresHaving + " <> 0");
			/* 1651: */
			/* 1652:1951 */ Query query = this.em.createQuery(sql.toString());
			/* 1653:1952 */ query.setParameter("listaFacturaCliente", listaFacturaCliente);
			/* 1654:1953 */ return query.getResultList();
			/* 1655: */ }
		/* 1656: */ catch (IllegalArgumentException e)
		/* 1657: */ {
			/* 1658:1956 */ e.printStackTrace();
			/* 1659:1957 */ throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable",
					"cuentaContableCliente");
			/* 1660: */ }
		/* 1661: */ }

	/* 1662: */
	/* 1663: */ public List<DetalleInterfazContableProceso> getInterfazNotaCreditoDimensiones(List<Integer> listaNotaCreditoCliente, ProcesoContabilizacionEnum procesoContabilizacionEnum, boolean notaCreditoPorDecuento)
/* 1664:     */   {
/* 1665:1973 */     String valores = "";
/* 1666:1974 */     String valoresHaving = "";
/* 1667:1975 */     String selectImpuesto = "";
/* 1668:1976 */     String selectVentas = " 0,'', 0, '',";
/* 1669:1977 */     String grupoImpuesto = "";
/* 1670:1978 */     String grupoVentas = "";
/* 1671:     */     
/* 1672:1980 */     String from = "";
/* 1673:1981 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$ProcesoContabilizacionEnum[procesoContabilizacionEnum.ordinal()])
/* 1674:     */     {
/* 1675:     */     case 3: 
/* 1676:1984 */       valores = notaCreditoPorDecuento ? "sum(dfc.precioLinea)" : "sum(dfc.descuentoLinea)";
/* 1677:1985 */       valoresHaving = valores;
/* 1678:1986 */       selectImpuesto = " 0,'', ";
/* 1679:1987 */       selectVentas = " 0 ,'' , ci.idCategoriaImpuesto, ci.nombre,";
/* 1680:1988 */       grupoVentas = ", ci.idCategoriaImpuesto, ci.nombre ";
/* 1681:1989 */       grupoImpuesto = "";
/* 1682:1990 */       from = " FROM DetalleFacturaCliente dfc ";
/* 1683:1991 */       break;
/* 1684:     */     case 8: 
/* 1685:1994 */       valores = "sum(dfc.precioLinea)";
/* 1686:1995 */       valoresHaving = valores;
/* 1687:1996 */       selectImpuesto = " 0,'', ";
/* 1688:1997 */       grupoImpuesto = "";
/* 1689:1998 */       selectVentas = " 0 ,'' , ci.idCategoriaImpuesto, ci.nombre,";
/* 1690:1999 */       grupoVentas = ", ci.idCategoriaImpuesto, ci.nombre ";
/* 1691:2000 */       from = " FROM DetalleFacturaCliente dfc ";
/* 1692:2001 */       break;
/* 1693:     */     case 4: 
/* 1694:2005 */       valores = "CASE WHEN i.tipoImpuesto = 0 then sum((dfc.precioLinea-dfc.descuentoLinea+dfc.iceLinea)*(coalesce(ipfc.porcentajeImpuesto,0)/100))  else sum((dfc.cantidad)*(coalesce(presp.cantidadUnidades,1))*(coalesce(ipfc.porcentajeImpuesto,0))) end";
/* 1695:     */       
/* 1696:2007 */       valoresHaving = " sum((dfc.precioLinea-dfc.descuentoLinea)*(coalesce(ipfc.porcentajeImpuesto,0))) ";
/* 1697:2008 */       selectImpuesto = " i.idImpuesto,i.nombre, ";
/* 1698:2009 */       grupoImpuesto = " , i.tipoImpuesto, i.idImpuesto,i.nombre";
/* 1699:2010 */       from = " FROM ImpuestoProductoFacturaCliente ipfc  LEFT OUTER JOIN ipfc.impuesto i  RIGHT JOIN ipfc.detalleFacturaCliente dfc ";
/* 1700:2011 */       break;
/* 1701:     */     case 5: 
/* 1702:2014 */       valores = "sum(dfc.iceLinea)";
/* 1703:2015 */       valoresHaving = valores;
/* 1704:2016 */       selectImpuesto = " 0,'', ";
/* 1705:2017 */       grupoImpuesto = "";
/* 1706:2018 */       from = " FROM DetalleFacturaCliente dfc ";
/* 1707:2019 */       break;
/* 1708:     */     case 6: 
/* 1709:2022 */       valores = "sum(fc.descuentoImpuesto)";
/* 1710:2023 */       valoresHaving = valores;
/* 1711:2024 */       selectImpuesto = " 0,'', ";
/* 1712:2025 */       grupoImpuesto = "";
/* 1713:2026 */       from = " FROM FacturaCliente fc ";
/* 1714:     */     }
/* 1715:2030 */     String descripcion = "";
/* 1716:2031 */     String grupoDescripcion = "";
/* 1717:2032 */     if (listaNotaCreditoCliente.size() == 1)
/* 1718:     */     {
/* 1719:2033 */       descripcion = "CONCAT(d.nombre,' #', fc.numero)";
/* 1720:2034 */       grupoDescripcion = "," + descripcion;
/* 1721:     */     }
/* 1722:     */     else
/* 1723:     */     {
/* 1724:2036 */       descripcion = "''";
/* 1725:     */     }
/* 1726:2038 */     StringBuilder sql = new StringBuilder();
/* 1727:     */     
/* 1728:2040 */     sql.append("SELECT new DetalleInterfazContableProcesoNotaCreditoCliente(d.idDocumento,d.nombre, s.idSucursal,s.nombre,");
/* 1729:2041 */     sql.append(" ce.idCategoriaEmpresa,ce.nombre, e.idEmpresa,e.nombreFiscal,");
/* 1730:2042 */     if (ProcesoContabilizacionEnum.DESCUENTO_IMPUESTO_VENTAS.equals(procesoContabilizacionEnum)) {
/* 1731:2043 */       sql.append(" 0, '', 0, '', 0, '',");
/* 1732:     */     } else {
/* 1733:2045 */       sql.append(" cp.idCategoriaProducto,cp.nombre, sp.idSubcategoriaProducto,sp.nombre, p.idProducto,p.nombre,");
/* 1734:     */     }
/* 1735:2047 */     sql.append(" m.idMotivoNotaCreditoCliente,m.nombre, se.idSubempresa,se.empresaFinal,");
/* 1736:2048 */     sql.append(selectImpuesto + selectVentas + descripcion + ", " + valores + " )");
/* 1737:2049 */     sql.append(from);
/* 1738:2050 */     if (!ProcesoContabilizacionEnum.DESCUENTO_IMPUESTO_VENTAS.equals(procesoContabilizacionEnum))
/* 1739:     */     {
/* 1740:2051 */       sql.append(" INNER JOIN dfc.facturaCliente fc ");
/* 1741:2052 */       sql.append(" INNER JOIN dfc.producto p ");
/* 1742:2053 */       sql.append(" LEFT JOIN p.presentacionProducto presp ");
/* 1743:2054 */       sql.append(" LEFT JOIN p.categoriaImpuesto ci ");
/* 1744:2055 */       sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 1745:2056 */       sql.append(" INNER JOIN sp.categoriaProducto cp ");
/* 1746:     */     }
/* 1747:2058 */     sql.append(" INNER JOIN fc.documento d ");
/* 1748:2059 */     sql.append(" INNER JOIN fc.empresa e ");
/* 1749:2060 */     sql.append(" INNER JOIN fc.sucursal s ");
/* 1750:2061 */     sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/* 1751:2062 */     sql.append(" LEFT JOIN fc.proyecto proy ");
/* 1752:2063 */     sql.append(" LEFT JOIN fc.motivoNotaCreditoCliente m ");
/* 1753:2064 */     sql.append(" LEFT JOIN fc.subempresa se");
/* 1754:2065 */     sql.append(" WHERE fc.idFacturaCliente in (:listaFacturaCliente)");
/* 1755:2066 */     sql.append(" GROUP BY d.idDocumento,d.nombre, s.idSucursal,s.nombre, ce.idCategoriaEmpresa,ce.nombre, e.idEmpresa,e.nombreFiscal,");
/* 1756:2067 */     if (!ProcesoContabilizacionEnum.DESCUENTO_IMPUESTO_VENTAS.equals(procesoContabilizacionEnum)) {
/* 1757:2068 */       sql.append(" cp.idCategoriaProducto,cp.nombre, sp.idSubcategoriaProducto,sp.nombre, p.idProducto,p.nombre,");
/* 1758:     */     }
/* 1759:2070 */     sql.append(" m.idMotivoNotaCreditoCliente,m.nombre, se.idSubempresa,se.empresaFinal ");
/* 1760:2071 */     sql.append(grupoImpuesto + grupoVentas + grupoDescripcion);
/* 1761:2072 */     sql.append(" HAVING " + valoresHaving + " <> 0");
/* 1762:     */     
/* 1763:2074 */     Query query = this.em.createQuery(sql.toString());
/* 1764:2075 */     query.setParameter("listaFacturaCliente", listaNotaCreditoCliente);
/* 1765:2076 */     return query.getResultList();
/* 1766:     */   }

	/* 1767: */
	/* 1768: */ public List<Object[]> getListaDevolucionCliente(int idFacturaCliente, int idOrganizacion)
	/* 1769: */ {
		/* 1770:2086 */ StringBuilder sql = new StringBuilder();
		/* 1771: */
		/* 1772:2088 */ sql
				.append(" SELECT p.nombreComercial, p.peso, p.volumen, b.nombre, dfc.cantidad, ua.codigo, l.codigo ");
		/* 1773:2089 */ sql.append(" FROM DetalleFacturaCliente dfc  ");
		/* 1774:2090 */ sql.append(" INNER JOIN dfc.producto p ");
		/* 1775:2091 */ sql.append(" INNER JOIN dfc.bodega b ");
		/* 1776:2092 */ sql.append(" INNER JOIN p.unidadAlmacenamiento ua ");
		/* 1777:2093 */ sql.append(" INNER JOIN dfc.inventarioProducto ip ");
		/* 1778:2094 */ sql.append(" INNER JOIN ip.lote l ");
		/* 1779:2095 */ sql.append(" INNER JOIN dfc.facturaCliente fc ");
		/* 1780:2096 */ sql.append(" WHERE fc.idFacturaCliente = :idFacturaCliente ");
		/* 1781:2097 */ sql.append(" AND fc.idOrganizacion = :idOrganizacion ");
		/* 1782: */
		/* 1783:2099 */ Query query = this.em.createQuery(sql.toString());
		/* 1784:2100 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 1785:2101 */ query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
		/* 1786: */
		/* 1787:2103 */ return query.getResultList();
		/* 1788: */ }

	/* 1789: */
	/* 1790: */ public FacturaCliente cargarDetalleDevolucion(Integer idDevolucionCliente)
	/* 1791: */ {
		/* 1792:2113 */ CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		/* 1793: */
		/* 1794: */
		/* 1795:2116 */ CriteriaQuery<FacturaCliente> cqCabecera = criteriaBuilder.createQuery(FacturaCliente.class);
		/* 1796:2117 */ Root<FacturaCliente> fromCabecera = cqCabecera.from(FacturaCliente.class);
		/* 1797: */
		/* 1798:2119 */ Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
		/* 1799:2120 */ documento.fetch("secuencia", JoinType.LEFT);
		/* 1800:2121 */ documento.fetch("tipoAsiento", JoinType.LEFT);
		/* 1801: */
		/* 1802:2123 */ fromCabecera.fetch("sucursal", JoinType.LEFT);
		/* 1803:2124 */ fromCabecera.fetch("empresa", JoinType.LEFT);
		/* 1804:2125 */ fromCabecera.fetch("asiento", JoinType.LEFT);
		/* 1805: */
		/* 1806:2127 */ Path<Integer> pathId = fromCabecera.get("idFacturaCliente");
		/* 1807:2128 */ cqCabecera.where(criteriaBuilder.equal(pathId, idDevolucionCliente));
		/* 1808:2129 */ CriteriaQuery<FacturaCliente> selectRecepcion = cqCabecera.select(fromCabecera);
		/* 1809: */
		/* 1810:2131 */ FacturaCliente devolucionCliente = (FacturaCliente) this.em.createQuery(selectRecepcion)
				.getSingleResult();
		/* 1811: */
		/* 1812: */
		/* 1813:2134 */ CriteriaQuery<DetalleFacturaCliente> cqDetalle = criteriaBuilder
				.createQuery(DetalleFacturaCliente.class);
		/* 1814:2135 */ Root<DetalleFacturaCliente> fromDetalle = cqDetalle.from(DetalleFacturaCliente.class);
		/* 1815: */
		/* 1816:2137 */ fromDetalle.fetch("unidadVenta", JoinType.LEFT);
		/* 1817: */
		/* 1818:2139 */ Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
		/* 1819:2140 */ producto.fetch("unidad", JoinType.LEFT);
		/* 1820:2141 */ fromDetalle.fetch("bodega", JoinType.LEFT);
		/* 1821:2142 */ Fetch<Object, Object> detallePedidoProveedor = fromDetalle.fetch("detallePedidoCliente",
				JoinType.LEFT);
		/* 1822:2143 */ detallePedidoProveedor.fetch("pedidoCliente", JoinType.LEFT);
		/* 1823:2144 */ Fetch<Object, Object> inventarioProducto = fromDetalle.fetch("inventarioProducto",
				JoinType.LEFT);
		/* 1824:2145 */ inventarioProducto.fetch("lote", JoinType.LEFT);
		/* 1825: */
		/* 1826:2147 */ Path<Integer> pathIdDetalle = fromDetalle.join("facturaCliente").get("idFacturaCliente");
		/* 1827:2148 */ cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, idDevolucionCliente));
		/* 1828:2149 */ CriteriaQuery<DetalleFacturaCliente> selectDetalleDevolucion = cqDetalle.select(fromDetalle);
		/* 1829: */
		/* 1830:2151 */ List<DetalleFacturaCliente> listaDetalleFacturaCliente = this.em
				.createQuery(selectDetalleDevolucion).getResultList();
		/* 1831:2152 */ devolucionCliente.setListaDetalleFacturaCliente(listaDetalleFacturaCliente);
		/* 1832: */
		/* 1833:2154 */ return devolucionCliente;
		/* 1834: */ }

	/* 1835: */
	/* 1836: */ public List<FacturaCliente> obtenerFacturasMes(Date fechaDesde, Date fechaHasta, Sucursal sucursal,
			PuntoDeVenta puntoDeVenta, int idOrganizacion, DocumentoBase documentoBase,
			boolean indicadorIncluirAnulados)
	/* 1837: */ {
		/* 1838:2160 */ String condicion = "";
		/* 1839:2161 */ if (indicadorIncluirAnulados) {
			/* 1840:2162 */ condicion = " 0=0 ";
			/* 1841: */ } else {
			/* 1842:2164 */ condicion = " fc.estado <> :estadoAnulado ";
			/* 1843: */ }
		/* 1844:2166 */ StringBuilder sql = new StringBuilder();
		/* 1845:2167 */ sql.append(" SELECT dfc from DetalleFacturaCliente dfc");
		/* 1846:2168 */ sql.append(" JOIN FETCH dfc.facturaCliente fc ");
		/* 1847:2169 */ sql.append(" JOIN FETCH fc.sucursal s ");
		/* 1848:2170 */ sql.append(" JOIN FETCH fc.documento d ");
		/* 1849:2171 */ sql.append(" JOIN FETCH d.tipoComprobanteSRI tcs ");
		/* 1850:2172 */ sql.append(" JOIN FETCH dfc.producto p ");
		/* 1851:2173 */ sql.append(" JOIN FETCH fc.facturaClienteSRI fs ");
		/* 1852:2174 */ sql.append(" JOIN FETCH fc.empresa e ");
		/* 1853:2175 */ sql.append(" JOIN FETCH e.tipoIdentificacion ti ");
		/* 1854:2176 */ sql.append(" LEFT JOIN FETCH fc.despachoCliente dc ");
		/* 1855:2177 */ sql.append(" LEFT JOIN FETCH dc.guiaRemision gr ");
		/* 1856:2178 */ sql.append(" LEFT JOIN FETCH fc.motivoNotaCreditoCliente mnc ");
		/* 1857:2179 */ sql.append(" LEFT JOIN FETCH fc.facturaClientePadre fcp ");
		/* 1858:2180 */ sql.append(" LEFT JOIN FETCH fcp.documento dp ");
		/* 1859:2181 */ sql.append(" LEFT JOIN FETCH dp.tipoComprobanteSRI tcp ");
		/* 1860:2182 */ sql.append(" LEFT JOIN FETCH fcp.facturaClienteSRI fsp ");
		/* 1861:2183 */ sql.append(" WHERE " + condicion);
		/* 1862:2184 */ sql.append(" AND fc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
		/* 1863:2185 */ sql.append(" AND d.documentoBase = :documentoBase");
		/* 1864:2186 */ if (sucursal != null) {
			/* 1865:2187 */ sql.append(" AND s.idSucursal = :idSucursal");
			/* 1866: */ }
		/* 1867:2189 */ if (puntoDeVenta != null) {
			/* 1868:2190 */ sql.append(" AND fs.puntoEmision = :puntoEmision");
			/* 1869: */ }
		/* 1870:2192 */ Query query = this.em.createQuery(sql.toString());
		/* 1871:2193 */ if (!indicadorIncluirAnulados) {
			/* 1872:2194 */ query.setParameter("estadoAnulado", Estado.ANULADO);
			/* 1873: */ }
		/* 1874:2196 */ query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
		/* 1875:2197 */ query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
		/* 1876:2198 */ query.setParameter("documentoBase", documentoBase);
		/* 1877:2199 */ if (sucursal != null) {
			/* 1878:2200 */ query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
			/* 1879: */ }
		/* 1880:2202 */ if (puntoDeVenta != null) {
			/* 1881:2203 */ query.setParameter("puntoEmision", puntoDeVenta.getCodigo());
			/* 1882: */ }
		/* 1883:2205 */ List<DetalleFacturaCliente> lista = query.getResultList();
		/* 1884: */
		/* 1885:2207 */ Map<Integer, FacturaCliente> hmFacturas = new HashMap();
		/* 1886: */
		/* 1887:2209 */ List<FacturaCliente> listaF = new ArrayList();
		/* 1888:2210 */ for (DetalleFacturaCliente detalleFacturaCliente : lista)
		/* 1889: */ {
			/* 1890:2211 */ this.detalleFacturaClienteDao.detach(detalleFacturaCliente);
			/* 1891:2212 */ FacturaCliente f = (FacturaCliente) hmFacturas
					.get(Integer.valueOf(detalleFacturaCliente.getFacturaCliente().getIdFacturaCliente()));
			/* 1892:2213 */ if (f == null)
			/* 1893: */ {
				/* 1894:2214 */ hmFacturas.put(
						Integer.valueOf(detalleFacturaCliente.getFacturaCliente().getIdFacturaCliente()),
						detalleFacturaCliente.getFacturaCliente());
				/* 1895:2215 */ listaF.add(detalleFacturaCliente.getFacturaCliente());
				/* 1896:2216 */ detalleFacturaCliente.getFacturaCliente()
						.setListaDetalleFacturaCliente(new ArrayList());
				/* 1897: */ }
			/* 1898:2218 */ f = (FacturaCliente) hmFacturas
					.get(Integer.valueOf(detalleFacturaCliente.getFacturaCliente().getIdFacturaCliente()));
			/* 1899:2219 */ f.getListaDetalleFacturaCliente().add(detalleFacturaCliente);
			/* 1900:2220 */ detalleFacturaCliente.setFacturaCliente(f);
			/* 1901: */ }
		/* 1902:2223 */ return listaF;
		/* 1903: */ }

	/* 1904: */
	/* 1905: */ public List<FacturaCliente> getListaNotaCreditoCliente(FacturaCliente facturaCliente,
			FacturaCliente notaCreditoFinancieraCliente)
	/* 1906: */ {
		/* 1907:2236 */ List<DocumentoBase> listaDoc = new ArrayList();
		/* 1908:2237 */ listaDoc.add(DocumentoBase.NOTA_CREDITO_CLIENTE);
		/* 1909:2238 */ listaDoc.add(DocumentoBase.DEVOLUCION_CLIENTE);
		/* 1910: */
		/* 1911:2240 */ StringBuilder sql = new StringBuilder();
		/* 1912: */
		/* 1913:2242 */ sql.append(" SELECT nc ");
		/* 1914:2243 */ sql.append(" FROM FacturaCliente nc ");
		/* 1915:2244 */ sql.append(" JOIN nc.facturaClientePadre fc ");
		/* 1916:2245 */ sql.append(" JOIN nc.documento d ");
		/* 1917:2246 */ sql.append(" WHERE fc.idFacturaCliente = :idFacturaCliente ");
		/* 1918:2247 */ sql.append(" AND nc.estado != :estadoAnulado");
		/* 1919:2248 */ sql.append(" AND nc.documento.documentoBase IN (:listaDoc)");
		/* 1920:2249 */ if (notaCreditoFinancieraCliente != null) {
			/* 1921:2250 */ sql.append(" AND nc.idFacturaCliente != :idNotaCreditoFinancieraCliente");
			/* 1922: */ }
		/* 1923:2253 */ Query query = this.em.createQuery(sql.toString());
		/* 1924:2254 */ query.setParameter("idFacturaCliente", Integer.valueOf(facturaCliente.getId()));
		/* 1925:2255 */ query.setParameter("estadoAnulado", Estado.ANULADO);
		/* 1926:2256 */ query.setParameter("listaDoc", listaDoc);
		/* 1927:2257 */ if (notaCreditoFinancieraCliente != null) {
			/* 1928:2258 */ query.setParameter("idNotaCreditoFinancieraCliente",
					Integer.valueOf(notaCreditoFinancieraCliente.getId()));
			/* 1929: */ }
		/* 1930:2261 */ return query.getResultList();
		/* 1931: */ }

	/* 1932: */
	/* 1933: */ public List<CuentaPorCobrar> obtenerProtestos(int idFacturaCliente)
	/* 1934: */ {
		/* 1935:2274 */ StringBuilder sql = new StringBuilder();
		/* 1936:2275 */ sql.append(" SELECT cxc FROM CuentaPorCobrar cxc ");
		/* 1937:2276 */ sql.append(" WHERE  cxc.indicadorGeneradaProtesto = true ");
		/* 1938:2277 */ sql.append(" AND \tcxc.facturaCliente.idFacturaCliente=:idFacturaCliente ");
		/* 1939: */
		/* 1940:2279 */ Query query = this.em.createQuery(sql.toString());
		/* 1941:2280 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 1942: */
		/* 1943:2282 */ return query.getResultList();
		/* 1944: */ }

	/* 1945: */
	/* 1946: */ public BigDecimal totalFacturaCliente(int idFacturaCliente)
	/* 1947: */ {
		/* 1948:2287 */ StringBuilder sql = new StringBuilder();
		/* 1949:2288 */ sql.append(" SELECT fc.total+fc.impuesto-fc.descuento ");
		/* 1950:2289 */ sql.append(" FROM FacturaCliente fc ");
		/* 1951:2290 */ sql.append(" WHERE fc.idFacturaCliente =:idFacturaCliente ");
		/* 1952: */
		/* 1953:2292 */ Query query = this.em.createQuery(sql.toString());
		/* 1954:2293 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 1955: */
		/* 1956:2295 */ return (BigDecimal) query.getSingleResult();
		/* 1957: */ }

	/* 1958: */
	/* 1959: */ public FacturaCliente buscarFacturaClientePorNumero(int idOrganizacion, String numero,
			DocumentoBase documentoBase)
	/* 1960: */ {
		/* 1961:2300 */ StringBuilder sql = new StringBuilder();
		/* 1962:2301 */ sql.append(" SELECT fc ");
		/* 1963:2302 */ sql.append(" FROM FacturaCliente fc ");
		/* 1964:2303 */ sql.append(" WHERE fc.numero =:numero ");
		/* 1965:2304 */ sql.append(" AND fc.documento.documentoBase =:documentoBase ");
		/* 1966:2305 */ sql.append(" AND fc.idOrganizacion =:idOrganizacion ");
		/* 1967: */
		/* 1968:2307 */ Query query = this.em.createQuery(sql.toString());
		/* 1969:2308 */ query.setParameter("numero", numero);
		/* 1970:2309 */ query.setParameter("documentoBase", documentoBase);
		/* 1971:2310 */ query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
		/* 1972: */
		/* 1973:2312 */ List lista = query.getResultList();
		/* 1974:2313 */ if ((lista != null) && (lista.size() > 0)) {
			/* 1975:2314 */ return (FacturaCliente) lista.get(0);
			/* 1976: */ }
		/* 1977:2317 */ return null;
		/* 1978: */ }

	/* 1979: */
	/* 1980: */ public List<DetalleInterfazContableProceso> getInterfazVentasDimensionesDevengado(List<Integer> listaDetalleValorContratoVenta, ProcesoContabilizacionEnum procesoContabilizacionEnum, boolean agrupar, boolean saldoInicial)
/* 1981:     */     throws ExcepcionAS2Financiero
/* 1982:     */   {
/* 1983:2324 */     String valores = "";
/* 1984:2325 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$ProcesoContabilizacionEnum[procesoContabilizacionEnum.ordinal()])
/* 1985:     */     {
/* 1986:     */     case 2: 
/* 1987:2327 */       if (agrupar) {
/* 1988:2328 */         valores = valores + "sum(";
/* 1989:     */       }
/* 1990:2330 */       valores = valores + " CASE WHEN comprob IS NULL THEN dvcv.valorDevengar ELSE  ((lDfc.precioLinea / comprob.total) * dvcv.valorDevengar) END ";
/* 1991:2331 */       if (agrupar) {
/* 1992:2332 */         valores = valores + ")";
/* 1993:     */       }
/* 1994:     */       break;
/* 1995:     */     case 9: 
/* 1996:2337 */       if (agrupar) {
/* 1997:2338 */         valores = valores + "sum(";
/* 1998:     */       }
/* 1999:2340 */       valores = valores + " CASE WHEN comprob IS NULL THEN dvcv.valorDevengar ELSE  ((lDfc.precioLinea / comprob.total) * dvcv.valorDevengar) END ";
/* 2000:2341 */       if (agrupar) {
/* 2001:2342 */         valores = valores + ")";
/* 2002:     */       }
/* 2003:     */       break;
/* 2004:     */     }
/* 2005:2346 */     String descripcion = "CONCAT(d.nombre,' #', comprob.numero)";
/* 2006:2347 */     if (agrupar) {
/* 2007:2348 */       descripcion = "d.nombre";
/* 2008:     */     }
/* 2009:2351 */     String selectImpuesto = " 0,'', ";
/* 2010:2352 */     String selectProyecto = " 0,'', ";
/* 2011:     */     
/* 2012:2354 */     StringBuilder sql = new StringBuilder();
/* 2013:     */     
/* 2014:2356 */     sql.append("SELECT new DetalleInterfazContableProcesoVentas(d.idDocumento, d.nombre, coalesce(s.idSucursal,ss.idSucursal), coalesce(s.nombre, ss.nombre), ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa, e.nombreFiscal,");
/* 2015:     */     
/* 2016:2358 */     sql.append(" cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, c.idCanal, c.nombre, se.idSubempresa, se.codigo, z.idZona, z.nombre, ");
/* 2017:     */     
/* 2018:2360 */     sql.append(selectImpuesto + selectProyecto + " 0,'', " + descripcion + ", " + descripcion + ", " + valores + " )");
/* 2019:2361 */     sql.append(" FROM DetalleValoresContratoVenta dvcv ");
/* 2020:2362 */     sql.append(" INNER JOIN dvcv.contratoVenta cv ");
/* 2021:2363 */     sql.append(" INNER JOIN cv.sucursal ss ");
/* 2022:2364 */     sql.append(" LEFT JOIN dvcv.comprobante comprob ");
/* 2023:2365 */     sql.append(" LEFT JOIN comprob.documento d ");
/* 2024:2366 */     sql.append(" LEFT JOIN comprob.sucursal s ");
/* 2025:2367 */     sql.append(" LEFT JOIN comprob.canal c ");
/* 2026:2368 */     sql.append(" LEFT JOIN comprob.zona z ");
/* 2027:2369 */     sql.append(" LEFT JOIN comprob.subempresa se");
/* 2028:2370 */     sql.append(" LEFT JOIN comprob.empresa e ");
/* 2029:2371 */     sql.append(" LEFT JOIN e.categoriaEmpresa ce ");
/* 2030:2372 */     sql.append(" LEFT JOIN comprob.listaDetalleFacturaCliente lDfc ");
/* 2031:2373 */     if (!saldoInicial)
/* 2032:     */     {
/* 2033:2374 */       sql.append(" LEFT JOIN lDfc.producto p ");
/* 2034:     */     }
/* 2035:     */     else
/* 2036:     */     {
/* 2037:2376 */       sql.append(" INNER JOIN cv.listaDetalleContratoVenta dcv ");
/* 2038:2377 */       sql.append(" LEFT JOIN dcv.producto p ");
/* 2039:     */     }
/* 2040:2379 */     sql.append(" LEFT JOIN p.presentacionProducto presp ");
/* 2041:2380 */     sql.append(" LEFT JOIN p.subcategoriaProducto sp ");
/* 2042:2381 */     sql.append(" LEFT JOIN sp.categoriaProducto cp ");
/* 2043:2382 */     sql.append(" WHERE dvcv.idDetalleValoresContratoVenta in (:listaDetalleValorContratoVenta)");
/* 2044:2383 */     if (!agrupar) {
/* 2045:2384 */       sql.append(" AND " + valores + " <> 0 ");
/* 2046:     */     }
/* 2047:2386 */     if (agrupar)
/* 2048:     */     {
/* 2049:2387 */       sql.append(" GROUP BY d.idDocumento, d.nombre, coalesce(s.idSucursal,ss.idSucursal), coalesce(s.nombre, ss.nombre), ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa, e.nombreFiscal,");
/* 2050:     */       
/* 2051:2389 */       sql.append(" cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, c.idCanal, c.nombre, se.idSubempresa,se.codigo, z.idZona, z.nombre ");
/* 2052:     */       
/* 2053:2391 */       sql.append(" HAVING " + valores + " <> 0");
/* 2054:     */     }
/* 2055:2394 */     Query query = this.em.createQuery(sql.toString());
/* 2056:2395 */     query.setParameter("listaDetalleValorContratoVenta", listaDetalleValorContratoVenta);
/* 2057:2396 */     return query.getResultList();
/* 2058:     */   }

	/* 2059: */
	/* 2060: */ public List<DetalleFacturaCliente> buscarDetallesNoDespachados(Integer idFacturaCliente)
	/* 2061: */ {
		/* 2062:2407 */ StringBuilder sql = new StringBuilder();
		/* 2063: */
		/* 2064:2409 */ sql.append("SELECT dfc ");
		/* 2065:2410 */ sql.append(" FROM DetalleFacturaCliente dfc ");
		/* 2066:2411 */ sql.append(" INNER JOIN dfc.facturaCliente fc ");
		/* 2067:2412 */ sql.append(" LEFT JOIN FETCH dfc.producto p ");
		/* 2068:2413 */ sql.append(" LEFT JOIN FETCH p.bodegaVenta bb ");
		/* 2069:2414 */ sql.append(" LEFT JOIN FETCH p.unidadVenta uv ");
		/* 2070:2415 */ sql.append(" LEFT JOIN dfc.detalleDespachoCliente ddc ");
		/* 2071:2416 */ sql.append(" LEFT JOIN FETCH dfc.detallePedidoCliente dpc ");
		/* 2072:2417 */ sql.append(" LEFT JOIN FETCH dpc.pedidoCliente pc");
		/* 2073:2418 */ sql.append(" LEFT JOIN ddc.despachoCliente dc ");
		/* 2074:2419 */ sql.append(" WHERE fc.idFacturaCliente = :idFacturaCliente ");
		/* 2075:2420 */ sql.append(" AND ((ddc IS NULL) OR (dc.estado = :estadoAnulado)) ");
		/* 2076: */
		/* 2077:2422 */ Query query = this.em.createQuery(sql.toString());
		/* 2078:2423 */ query.setParameter("idFacturaCliente", idFacturaCliente);
		/* 2079:2424 */ query.setParameter("estadoAnulado", Estado.ANULADO);
		/* 2080: */
		/* 2081:2426 */ return query.getResultList();
		/* 2082: */ }

	/* 2083: */
	/* 2084: */ public BigDecimal valorNotaCreditoFactura(FacturaCliente notaCreditoCliente)
	/* 2085: */ {
		/* 2086:2436 */ StringBuilder sql = new StringBuilder();
		/* 2087: */
		/* 2088:2438 */ sql.append("SELECT SUM(nc.total) - SUM(nc.descuento) ");
		/* 2089:2439 */ sql.append(" FROM FacturaCliente nc ");
		/* 2090:2440 */ sql.append(" JOIN nc.facturaClientePadre fc ");
		/* 2091:2441 */ sql.append(" JOIN nc.documento d ");
		/* 2092:2442 */ sql.append(" WHERE fc.idFacturaCliente = :idFacturaCliente ");
		/* 2093:2443 */ sql.append(" AND nc.idFacturaCliente != :idNotaCreditoCliente ");
		/* 2094:2444 */ sql.append(" AND fc.estado != :estadoAnulado ");
		/* 2095:2445 */ sql.append(" AND nc.estado != :estadoAnulado ");
		/* 2096:2446 */ sql.append(
				" AND (d.documentoBase = :documentoNotaCredito OR d.documentoBase = :documentoDevolucionCliente )");
		/* 2097: */
		/* 2098:2448 */ Query query = this.em.createQuery(sql.toString());
		/* 2099:2449 */ query.setParameter("idFacturaCliente",
				Integer.valueOf(notaCreditoCliente.getFacturaClientePadre().getIdFacturaCliente()));
		/* 2100:2450 */ query.setParameter("idNotaCreditoCliente",
				Integer.valueOf(notaCreditoCliente.getIdFacturaCliente()));
		/* 2101:2451 */ query.setParameter("estadoAnulado", Estado.ANULADO);
		/* 2102:2452 */ query.setParameter("documentoNotaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
		/* 2103:2453 */ query.setParameter("documentoDevolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
		/* 2104: */
		/* 2105:2455 */ return (BigDecimal) query.getSingleResult();
		/* 2106: */ }

	/* 2107: */
	/* 2108: */ public List<String> listaCategoriaProducto(int idFacturaCliente)
	/* 2109: */ {
		/* 2110:2461 */ StringBuilder sql = new StringBuilder();
		/* 2111: */
		/* 2112:2463 */ sql.append(" SELECT scp.nombre ");
		/* 2113:2464 */ sql.append(" FROM  DetalleFacturaCliente dfc ");
		/* 2114:2465 */ sql.append(" INNER JOIN dfc.facturaCliente fc ");
		/* 2115:2466 */ sql.append(" INNER JOIN dfc.producto p ");
		/* 2116:2467 */ sql.append(" INNER JOIN p.subcategoriaProducto scp ");
		/* 2117:2468 */ sql.append(" WHERE fc.idFacturaCliente = :idFacturaCliente ");
		/* 2118: */
		/* 2119:2470 */ Query query = this.em.createQuery(sql.toString());
		/* 2120:2471 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 2121: */
		/* 2122:2473 */ return query.getResultList();
		/* 2123: */ }

	/* 2124: */
	/* 2125: */ public FacturaCliente obtenerUltimaFacturaAutorizadaPorCliente(int idOrganizacion, Empresa empresa,
			boolean devolucion, BigDecimal total)
	/* 2126: */ {
		/* 2127:2478 */ if (empresa != null)
		/* 2128: */ {
			/* 2129:2480 */ List<Estado> listaEstado = new ArrayList();
			/* 2130:2481 */ listaEstado.add(Estado.APROBADO);
			/* 2131:2482 */ listaEstado.add(Estado.CONTABILIZADO);
			/* 2132:2483 */ listaEstado.add(Estado.FACTURADO);
			/* 2133:2484 */ listaEstado.add(Estado.PROCESADO);
			/* 2134:2485 */ listaEstado.add(Estado.REVISADO);
			/* 2135: */
			/* 2136:2487 */ StringBuilder sql = new StringBuilder();
			/* 2137: */
			/* 2138:2489 */ sql.append(" SELECT fc ");
			/* 2139:2490 */ sql.append(" FROM  FacturaCliente fc ");
			/* 2140:2491 */ sql.append(" INNER JOIN FETCH fc.sucursal s");
			/* 2141:2492 */ sql.append(" INNER JOIN FETCH fc.empresa e ");
			/* 2142:2493 */ sql.append(" INNER JOIN FETCH fc.facturaClienteSRI fcSRI ");
			/* 2143:2494 */ sql.append(" INNER JOIN FETCH fc.documento doc ");
			/* 2144:2495 */ sql.append(" LEFT JOIN FETCH fc.subempresa se ");
			/* 2145:2496 */ sql.append(" LEFT JOIN FETCH se.empresa emp ");
			/* 2146:2497 */ sql.append(" WHERE fc.idOrganizacion = :idOrganizacion ");
			/* 2147:2498 */ sql.append(" AND e.idEmpresa = :idEmpresa ");
			/* 2148:2499 */ sql.append(" AND doc.documentoBase = :documentoFactura ");
			/* 2149:2500 */ sql.append(" AND fcSRI.autorizacion != '0000000000' ");
			/* 2150:2501 */ sql.append(" AND fc.estado IN :listaEstado ");
			/* 2151:2502 */ if (devolucion) {
				/* 2152:2503 */ sql.append(" AND fc.despachoCliente IS NOT NULL ");
				/* 2153: */ }
			/* 2154:2505 */ if (null != total) {
				/* 2155:2506 */ sql.append(
						" AND (fc.total - COALESCE(fc.descuento, 0.00) - COALESCE(fc.valorDevuelto, 0.00)) >= :total");
				/* 2156: */ }
			/* 2157:2508 */ sql.append(" ORDER BY fc.fecha DESC, fc.numero DESC ");
			/* 2158: */
			/* 2159:2510 */ Query query = this.em.createQuery(sql.toString());
			/* 2160:2511 */ query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
			/* 2161:2512 */ query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
			/* 2162:2513 */ query.setParameter("documentoFactura", DocumentoBase.FACTURA_CLIENTE);
			/* 2163:2514 */ query.setParameter("listaEstado", listaEstado);
			/* 2164:2515 */ if (null != total) {
				/* 2165:2516 */ query.setParameter("total", total);
				/* 2166: */ }
			/* 2167:2518 */ query.setMaxResults(1);
			/* 2168:2519 */ List<FacturaCliente> lista = query.getResultList();
			/* 2169:2520 */ if (lista.size() > 0) {
				/* 2170:2521 */ return (FacturaCliente) lista.get(0);
				/* 2171: */ }
			/* 2172:2523 */ return null;
			/* 2173: */ }
		/* 2174:2526 */ return null;
		/* 2175: */ }

	/* 2176: */
	/* 2177: */ public void liberarFacturaAutomatica(Integer idFacturaCliente)
	/* 2178: */ {
		/* 2179:2531 */ StringBuilder sql = new StringBuilder();
		/* 2180:2532 */ sql.append(" UPDATE FacturaCliente fc ");
		/* 2181:2533 */ sql.append(" SET fc.indicadorAutomatico = false  ");
		/* 2182:2534 */ sql.append(" WHERE fc.idFacturaCliente = :idFacturaCliente ");
		/* 2183: */
		/* 2184:2536 */ Query query = this.em.createQuery(sql.toString());
		/* 2185:2537 */ query.setParameter("idFacturaCliente", idFacturaCliente);
		/* 2186: */
		/* 2187:2539 */ query.executeUpdate();
		/* 2188: */ }

	/* 2189: */
	/* 2190: */ public List<FacturaCliente> listaFacturas(int idOrganizacion, Date fechaDesde, Date fechaHasta,
			Transportista transportista, Zona zona, Canal canal, HojaRuta hojaRuta)
	/* 2191: */ {
		/* 2192:2546 */ StringBuilder sql = new StringBuilder();
		/* 2193:2547 */ sql.append(" SELECT fc ");
		/* 2194:2548 */ sql.append(" FROM FacturaCliente fc ");
		/* 2195:2549 */ sql.append(" INNER JOIN fc.documento d ");
		/* 2196:2550 */ if (zona != null) {
			/* 2197:2551 */ sql.append(" LEFT JOIN fc.zona z ");
			/* 2198: */ }
		/* 2199:2553 */ if (canal != null) {
			/* 2200:2554 */ sql.append(" LEFT JOIN fc.canal c ");
			/* 2201: */ }
		/* 2202:2556 */ if ((hojaRuta != null) || (transportista != null)) {
			/* 2203:2557 */ sql.append(" LEFT JOIN fc.despachoCliente dc ");
			/* 2204: */ }
		/* 2205:2559 */ if (transportista != null) {
			/* 2206:2560 */ sql.append(" LEFT JOIN dc.transportista tra ");
			/* 2207: */ }
		/* 2208:2562 */ sql.append(" WHERE fc.idOrganizacion = :idOrganizacion ");
		/* 2209:2563 */ sql.append(" AND fc.fecha BETWEEN :fechaDesde and :fechaHasta ");
		/* 2210:2564 */ sql.append(" AND d.documentoBase = :documentoBase ");
		/* 2211:2565 */ sql.append(" AND fc.estado != :estado ");
		/* 2212:2566 */ if (hojaRuta != null) {
			/* 2213:2567 */ sql.append(
					" AND EXISTS(SELECT 1 FROM DetalleHojaRuta dhr JOIN dhr.despachoCliente dc2 JOIN dhr.hojaRuta hr WHERE dc=dc2 AND hr=:hojaRuta)");
			/* 2214: */ }
		/* 2215:2570 */ if (zona != null) {
			/* 2216:2571 */ sql.append(" AND z = :zona ");
			/* 2217: */ }
		/* 2218:2573 */ if (canal != null) {
			/* 2219:2574 */ sql.append(" AND c = :canal ");
			/* 2220: */ }
		/* 2221:2576 */ if (transportista != null) {
			/* 2222:2577 */ sql.append(" AND tra = :transportista ");
			/* 2223: */ }
		/* 2224:2580 */ Query query = this.em.createQuery(sql.toString());
		/* 2225:2581 */ query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
		/* 2226:2582 */ query.setParameter("fechaDesde", hojaRuta == null ? fechaDesde : hojaRuta.getFecha(),
				TemporalType.DATE);
		/* 2227:2583 */ query.setParameter("fechaHasta", hojaRuta == null ? fechaHasta : hojaRuta.getFecha(),
				TemporalType.DATE);
		/* 2228:2584 */ query.setParameter("documentoBase", DocumentoBase.FACTURA_CLIENTE);
		/* 2229:2585 */ query.setParameter("estado", Estado.ANULADO);
		/* 2230:2586 */ if (hojaRuta != null) {
			/* 2231:2587 */ query.setParameter("hojaRuta", hojaRuta);
			/* 2232: */ }
		/* 2233:2589 */ if (zona != null) {
			/* 2234:2590 */ query.setParameter("zona", zona);
			/* 2235: */ }
		/* 2236:2592 */ if (canal != null) {
			/* 2237:2593 */ query.setParameter("canal", canal);
			/* 2238: */ }
		/* 2239:2595 */ if (transportista != null) {
			/* 2240:2596 */ query.setParameter("transportista", transportista);
			/* 2241: */ }
		/* 2242:2599 */ return query.getResultList();
		/* 2243: */ }

	/* 2244: */
	/* 2245: */ public HashMap<Integer, BigDecimal> obtenerTotalDevolucionesPorProductoPorFecha(Producto producto,
			Date fechaInicio, Date fechaFin, List<Bodega> listaBodegaTrabajo)
	/* 2246: */ {
		/* 2247:2604 */ HashMap<Integer, BigDecimal> hmapDevoluciones = new HashMap();
		/* 2248:2605 */ StringBuilder sql = new StringBuilder();
		/* 2249:2606 */ sql.append(" SELECT p.idProducto, COALESCE(SUM(dfc.cantidad),0) ");
		/* 2250:2607 */ sql.append(" FROM DetalleFacturaCliente dfc ");
		/* 2251:2608 */ sql.append(" INNER JOIN dfc.producto p ");
		/* 2252:2609 */ sql.append(" INNER JOIN dfc.bodega b ");
		/* 2253:2610 */ sql.append(" INNER JOIN dfc.facturaCliente fc ");
		/* 2254:2611 */ sql.append(" INNER JOIN fc.documento d ");
		/* 2255:2612 */ sql.append(" INNER JOIN fc.motivoNotaCreditoCliente mnc ");
		/* 2256:2613 */ sql.append(
				" WHERE (fc.estado = :estadoProcesado OR fc.estado = :estadoContabilizado OR fc.estado = :estadoElaborado) ");
		/* 2257:2614 */ sql.append(" AND fc.fecha >= :fechaInicio ");
		/* 2258:2615 */ sql.append(" AND fc.fecha <= :fechaFin ");
		/* 2259:2616 */ sql.append(" AND d.documentoBase = :documentoDevolucion ");
		/* 2260:2617 */ sql.append(" AND mnc.indicadorAfectaDevolucion IS TRUE ");
		/* 2261:2619 */ if (producto != null) {
			/* 2262:2620 */ sql.append(" AND p.idProducto = :idProducto ");
			/* 2263: */ }
		/* 2264:2622 */ if ((listaBodegaTrabajo != null) && (listaBodegaTrabajo.size() > 0)) {
			/* 2265:2623 */ sql.append(" AND b IN (:listaBodegaTrabajo)");
			/* 2266: */ }
		/* 2267:2625 */ sql.append(" GROUP BY p.idProducto");
		/* 2268:2626 */ Query query = this.em.createQuery(sql.toString());
		/* 2269:2627 */ if (producto != null) {
			/* 2270:2628 */ query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
			/* 2271: */ }
		/* 2272:2630 */ query.setParameter("fechaInicio", fechaInicio);
		/* 2273:2631 */ query.setParameter("fechaFin", fechaFin);
		/* 2274:2632 */ query.setParameter("estadoProcesado", Estado.PROCESADO);
		/* 2275:2633 */ query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
		/* 2276:2634 */ query.setParameter("estadoElaborado", Estado.ELABORADO);
		/* 2277:2635 */ query.setParameter("documentoDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
		/* 2278:2636 */ if ((listaBodegaTrabajo != null) && (listaBodegaTrabajo.size() > 0)) {
			/* 2279:2637 */ query.setParameter("listaBodegaTrabajo", listaBodegaTrabajo);
			/* 2280: */ }
		/* 2281:2640 */ List<Object[]> listaDevoluciones = query.getResultList();
		/* 2282:2642 */ for (Object[] objects : listaDevoluciones) {
			/* 2283:2643 */ hmapDevoluciones.put((Integer) objects[0], (BigDecimal) objects[1]);
			/* 2284: */ }
		/* 2285:2645 */ return hmapDevoluciones;
		/* 2286: */ }

	/* 2287: */
	/* 2288: */ public List<DespachoCliente> detalleDespachoCliente(int idOrganizacion, Sucursal sucursal,
			HojaRuta hojaRuta, Transportista transportista, Date fechaDesde, Date fechaHasta)
	/* 2289: */ {
		/* 2290:2652 */ StringBuilder sql = new StringBuilder();
		/* 2291: */
		/* 2292:2654 */ sql.append(" SELECT dc ");
		/* 2293:2655 */ sql.append(" FROM  DespachoCliente dc");
		/* 2294:2656 */ sql.append(" LEFT JOIN FETCH dc.transportista transp");
		/* 2295:2657 */ sql.append(" LEFT JOIN FETCH dc.sucursal s");
		/* 2296:2658 */ sql.append(" LEFT JOIN FETCH dc.pedidoCliente pc");
		/* 2297:2659 */ sql.append(" LEFT JOIN FETCH dc.empresa e");
		/* 2298:2660 */ sql.append(" LEFT JOIN FETCH dc.direccionEmpresa demp ");
		/* 2299:2661 */ sql.append(" LEFT JOIN FETCH demp.ubicacion ub ");
		/* 2300:2662 */ sql.append(" LEFT JOIN FETCH demp.ciudad ciud ");
		/* 2301:2663 */ sql.append(" LEFT JOIN FETCH demp.parroquia parr ");
		/* 2302:2664 */ sql.append(" LEFT JOIN FETCH e.cliente cli");
		/* 2303:2665 */ sql.append(" LEFT JOIN FETCH e.proveedor prov");
		/* 2304:2666 */ sql.append(" LEFT JOIN FETCH e.empleado epl");
		/* 2305:2667 */ sql.append(" LEFT JOIN FETCH cli.zona ");
		/* 2306:2668 */ sql.append(" LEFT JOIN FETCH cli.transportista tra");
		/* 2307:2669 */ sql.append(" LEFT JOIN FETCH dc.subempresa se");
		/* 2308:2670 */ sql.append(" LEFT JOIN FETCH se.empresa esub");
		/* 2309:2671 */ sql.append(" LEFT JOIN FETCH esub.cliente clisub");
		/* 2310:2672 */ sql.append(" LEFT JOIN FETCH esub.proveedor provSub");
		/* 2311:2673 */ sql.append(" LEFT JOIN FETCH esub.empleado eplSub");
		/* 2312:2674 */ sql.append(" LEFT JOIN FETCH clisub.transportista trasub");
		/* 2313:2675 */ sql.append(" WHERE dc.idOrganizacion = :idOrganizacion");
		/* 2314:2676 */ sql.append(
				" AND not exists (SELECT 1 FROM DetalleFacturaCliente dfc INNER JOIN dfc.facturaCliente fc INNER JOIN fc.despachoCliente dcc WHERE dc.idDespachoCliente = dcc.idDespachoCliente )");
		/* 2315: */
		/* 2316:2678 */ sql.append(" AND dc.fecha between :fechaDesde and :fechaHasta ");
		/* 2317:2679 */ sql.append(" AND dc.estado != :estado ");
		/* 2318:2681 */ if (sucursal != null) {
			/* 2319:2682 */ sql.append(" and s = :sucursal ");
			/* 2320: */ }
		/* 2321:2685 */ if (hojaRuta != null) {
			/* 2322:2686 */ sql.append(
					" and exists (SELECT dhr FROM DetalleHojaRuta dhr INNER JOIN dhr.hojaRuta hr  WHERE hr = :hojaRuta AND dhr.despachoCliente = dc ) ");
			/* 2323: */ }
		/* 2324:2690 */ if (transportista != null) {
			/* 2325:2691 */ sql.append(" and coalesce (transp,coalesce (trasub, tra)) = :transportista ");
			/* 2326: */ }
		/* 2327:2694 */ Query query = this.em.createQuery(sql.toString());
		/* 2328: */
		/* 2329:2696 */ query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
		/* 2330:2698 */ if (sucursal != null) {
			/* 2331:2699 */ query.setParameter("sucursal", sucursal);
			/* 2332: */ }
		/* 2333:2702 */ if (hojaRuta != null) {
			/* 2334:2703 */ query.setParameter("hojaRuta", hojaRuta);
			/* 2335: */ }
		/* 2336:2705 */ if (transportista != null) {
			/* 2337:2706 */ query.setParameter("transportista", transportista);
			/* 2338: */ }
		/* 2339:2708 */ query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
		/* 2340:2709 */ query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
		/* 2341:2710 */ query.setParameter("estado", Estado.ANULADO);
		/* 2342: */
		/* 2343:2712 */ return query.getResultList();
		/* 2344: */ }

	/* 2345: */
	/* 2346: */ public long validarContabilizacionVentas(List<Integer> listaFacturaCliente)
	/* 2347: */ {
		/* 2348:2717 */ StringBuilder sql = new StringBuilder();
		/* 2349:2718 */ sql.append(" SELECT count(*)");
		/* 2350:2719 */ sql.append(" FROM FacturaCliente fc ");
		/* 2351:2720 */ sql.append(" WHERE fc.idFacturaCliente in (:listaFacturaCliente)");
		/* 2352:2721 */ sql.append(" AND fc.interfazContableProceso IS NOT NULL ");
		/* 2353: */
		/* 2354:2723 */ Query query = this.em.createQuery(sql.toString());
		/* 2355:2724 */ query.setParameter("listaFacturaCliente", listaFacturaCliente);
		/* 2356: */
		/* 2357:2726 */ return ((Long) query.getSingleResult()).longValue();
		/* 2358: */ }

	/* 2359: */
	/* 2360: */ public List<DetalleDespachoCliente> obtenerDetalleDespachoCliente(int idDespachoCliente)
	/* 2361: */ {
		/* 2362:2733 */ StringBuilder sql = new StringBuilder();
		/* 2363:2734 */ sql.append("SELECT ddc FROM DetalleDespachoCliente ddc");
		/* 2364:2735 */ sql.append(" INNER JOIN FETCH ddc.despachoCliente dc");
		/* 2365:2736 */ sql.append(" JOIN FETCH ddc.producto pr");
		/* 2366:2737 */ sql.append(" JOIN FETCH pr.unidad u");
		/* 2367:2738 */ sql.append(" JOIN FETCH ddc.unidadVenta uv");
		/* 2368:2739 */ sql.append(" LEFT JOIN FETCH ddc.bodega b");
		/* 2369:2740 */ sql.append(" LEFT JOIN FETCH ddc.inventarioProducto ip");
		/* 2370:2741 */ sql.append(" LEFT JOIN FETCH dc.ordenDespachoCliente odc");
		/* 2371:2742 */ sql.append(" LEFT JOIN FETCH odc.sucursal su");
		/* 2372:2743 */ sql.append(" LEFT JOIN FETCH ip.lote l");
		/* 2373:2744 */ sql.append(" LEFT JOIN FETCH pr.presentacionProducto pres");
		/* 2374:2745 */ sql.append(" WHERE dc.idDespachoCliente = :idDespachoCliente");
		/* 2375:2746 */ sql.append(" AND pr.tipoProducto = :tipoProducto");
		/* 2376:2747 */ sql.append(" AND (ddc.cantidad - ddc.cantidadDevuelta) > 0 ");
		/* 2377: */
		/* 2378:2749 */ Query query = this.em.createQuery(sql.toString());
		/* 2379:2750 */ query.setParameter("idDespachoCliente", Integer.valueOf(idDespachoCliente));
		/* 2380:2751 */ query.setParameter("tipoProducto", TipoProducto.ARTICULO);
		/* 2381:2752 */ return query.getResultList();
		/* 2382: */ }

	/* 2383: */
	/* 2384: */ public FacturaCliente buscarPorId(Object id)
	/* 2385: */ {
		/* 2386:2757 */ StringBuilder sql = new StringBuilder();
		/* 2387:2758 */ sql.append("SELECT p FROM FacturaCliente p ");
		/* 2388:2759 */ sql.append(" LEFT JOIN FETCH p.documento d ");
		/* 2389:2760 */ sql.append(" LEFT JOIN FETCH p.sucursal s ");
		/* 2390:2761 */ sql.append(" WHERE p.idFacturaCliente = :idFacturaCliente");
		/* 2391:2762 */ Query query = this.em.createQuery(sql.toString());
		/* 2392:2763 */ query.setParameter("idFacturaCliente", (Integer) id);
		/* 2393:2764 */ return (FacturaCliente) query.getSingleResult();
		/* 2394: */ }

	/* 2395: */
	/* 2396: */ public void actualizaFacturaClienteSRI(int idFacturaCliente, Estado estadoFactura,
			EstadoDocumentoElectronico estadoSRI, Date fechaAutorizacion, String numeroAutorizacion, String mensajeSRI)
	/* 2397: */ {
		/* 2398:2770 */ if (estadoFactura != null)
		/* 2399: */ {
			/* 2400:2771 */ StringBuilder sql = new StringBuilder();
			/* 2401:2772 */ sql.append("UPDATE FacturaCliente fc ");
			/* 2402:2773 */ sql.append(" SET fc.estado = :estado ");
			/* 2403:2774 */ sql.append(" WHERE fc.idFacturaCliente = :idFacturaCliente ");
			/* 2404:2775 */ Query query = this.em.createQuery(sql.toString());
			/* 2405:2776 */ query.setParameter("estado", estadoFactura);
			/* 2406:2777 */ query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
			/* 2407:2778 */ query.executeUpdate();
			/* 2408: */ }
		/* 2409:2781 */ String set = "SET ";
		/* 2410:2782 */ StringBuilder sql1 = new StringBuilder();
		/* 2411:2783 */ sql1.append("UPDATE FacturaClienteSRI fc ");
		/* 2412:2784 */ if (estadoFactura != null)
		/* 2413: */ {
			/* 2414:2785 */ sql1.append(set + " fc.estado = :estado ");
			/* 2415:2786 */ set = ", ";
			/* 2416: */ }
		/* 2417:2788 */ if (fechaAutorizacion != null)
		/* 2418: */ {
			/* 2419:2789 */ sql1.append(set + " fc.fechaAutorizacion = :fechaAutorizacion ");
			/* 2420:2790 */ set = ", ";
			/* 2421: */ }
		/* 2422:2792 */ if (numeroAutorizacion != null)
		/* 2423: */ {
			/* 2424:2793 */ sql1.append(set + " fc.autorizacion = :numeroAutorizacion ");
			/* 2425:2794 */ set = ", ";
			/* 2426: */ }
		/* 2427:2796 */ if (estadoSRI != null)
		/* 2428: */ {
			/* 2429:2797 */ sql1.append(set + " fc.estadoDocumentoElectronico = :estadoSRI ");
			/* 2430:2798 */ set = ", ";
			/* 2431: */ }
		/* 2432:2800 */ if (mensajeSRI != null) {
			/* 2433:2801 */ sql1.append(set + " fc.mensajeSRI = :mensajeSRI ");
			/* 2434: */ }
		/* 2435:2803 */ sql1.append(" WHERE fc.facturaCliente.idFacturaCliente = :idFacturaCliente ");
		/* 2436: */
		/* 2437:2805 */ Query query1 = this.em.createQuery(sql1.toString());
		/* 2438:2806 */ query1.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
		/* 2439:2807 */ if (estadoFactura != null) {
			/* 2440:2808 */ query1.setParameter("estado", estadoFactura);
			/* 2441: */ }
		/* 2442:2810 */ if (fechaAutorizacion != null) {
			/* 2443:2811 */ query1.setParameter("fechaAutorizacion", fechaAutorizacion);
			/* 2444: */ }
		/* 2445:2813 */ if (numeroAutorizacion != null) {
			/* 2446:2814 */ query1.setParameter("numeroAutorizacion", numeroAutorizacion);
			/* 2447: */ }
		/* 2448:2816 */ if (estadoSRI != null) {
			/* 2449:2817 */ query1.setParameter("estadoSRI", estadoSRI);
			/* 2450: */ }
		/* 2451:2819 */ if (mensajeSRI != null) {
			/* 2452:2820 */ query1.setParameter("mensajeSRI", mensajeSRI);
			/* 2453: */ }
		/* 2454:2822 */ query1.executeUpdate();
		/* 2455: */ }

	/* 2456: */
	/* 2457: */ public List<Object[]> getReporteProductoPedidoVsFactura(Date fechaDesde, Date fechaHasta,
			int idCategoriaEmpresa, int idEmpresa, int idZona, int idCanal, int idAgenteComercial, int idSucursal,
			int idOrganizacion, List<Integer> listaIdDetallePedidoCliente)
	/* 2458: */ {
		/* 2459:2835 */ StringBuilder sqlp = new StringBuilder();
		/* 2460:2836 */ sqlp.append(
				" SELECT dp.idDetallePedidoCliente, pc.fecha, pc.numero, e.identificacion, e.nombreFiscal, CONCAT(a.nombre2,' ',a.nombre1) ,c.nombre, z.nombre, s.nombre , p.codigo, p.nombre, scp.codigo, scp.nombre, dpc.cantidad, dpc.precio, dpc.descuento, (dpc.cantidad * dpc.precio), (dpc.cantidad*0), (dpc.cantidad*0), (dpc.cantidad*0), (dpc.cantidad*0), pc.fecha, '', u.codigo ");
		/* 2461: */
		/* 2462:2838 */ sqlp.append(" FROM DetalleFacturaCliente dpc");
		/* 2463:2839 */ sqlp.append(" INNER JOIN dpc.facturaCliente pc");
		/* 2464:2840 */ sqlp.append(" INNER JOIN pc.empresa e");
		/* 2465:2841 */ sqlp.append(" INNER JOIN e.categoriaEmpresa ce");
		/* 2466:2842 */ sqlp.append(" LEFT  JOIN dpc.detallePedidoCliente dp");
		/* 2467:2843 */ sqlp.append(" LEFT  JOIN pc.agenteComercial a");
		/* 2468:2844 */ sqlp.append(" LEFT  JOIN pc.canal c");
		/* 2469:2845 */ sqlp.append(" LEFT  JOIN pc.zona  z");
		/* 2470:2846 */ sqlp.append(" INNER JOIN pc.sucursal s");
		/* 2471:2847 */ sqlp.append(" INNER JOIN dpc.producto p");
		/* 2472:2848 */ sqlp.append(" INNER JOIN p.unidadVenta u ");
		/* 2473:2849 */ sqlp.append(" LEFT JOIN p.subcategoriaProducto scp");
		/* 2474:2851 */ if ((listaIdDetallePedidoCliente != null) && (!listaIdDetallePedidoCliente.isEmpty()))
		/* 2475: */ {
			/* 2476:2852 */ sqlp.append(" WHERE dp.idDetallePedidoCliente IN (:listaIdDetallePedidoCliente) ");
			/* 2477: */ }
		/* 2478: */ else
		/* 2479: */ {
			/* 2480:2855 */ sqlp.append(" WHERE pc.fecha BETWEEN :fechaDesde AND :fechaHasta");
			/* 2481:2856 */ sqlp.append(" AND pc.idOrganizacion = :idOrganizacion");
			/* 2482:2857 */ sqlp.append(" AND pc.estado != :estadoAnulado ");
			/* 2483:2859 */ if (idCategoriaEmpresa != 0) {
				/* 2484:2860 */ sqlp.append("  AND ce.idCategoriaEmpresa = :idCategoriaEmpresa ");
				/* 2485: */ }
			/* 2486:2862 */ if (idEmpresa != 0) {
				/* 2487:2863 */ sqlp.append("  AND e.idEmpresa = :idEmpresa ");
				/* 2488: */ }
			/* 2489:2865 */ if (idZona != 0) {
				/* 2490:2866 */ sqlp.append("  AND z.idZona = :idZona ");
				/* 2491: */ }
			/* 2492:2868 */ if (idCanal != 0) {
				/* 2493:2869 */ sqlp.append("  AND c.idCanal = :idCanal ");
				/* 2494: */ }
			/* 2495:2871 */ if (idAgenteComercial != 0) {
				/* 2496:2872 */ sqlp.append("  AND a.idUsuario = :idAgenteComercial ");
				/* 2497: */ }
			/* 2498:2874 */ if (idSucursal != 0) {
				/* 2499:2875 */ sqlp.append(" AND s.idSucursal = :idSucursal ");
				/* 2500: */ }
			/* 2501:2877 */ sqlp.append("  ORDER BY pc.numero, e.nombreFiscal, p.nombre ");
			/* 2502: */ }
		/* 2503:2880 */ Query query = this.em.createQuery(sqlp.toString());
		/* 2504:2882 */ if ((listaIdDetallePedidoCliente != null) && (!listaIdDetallePedidoCliente.isEmpty()))
		/* 2505: */ {
			/* 2506:2883 */ query.setParameter("listaIdDetallePedidoCliente", listaIdDetallePedidoCliente);
			/* 2507: */ }
		/* 2508: */ else
		/* 2509: */ {
			/* 2510:2886 */ query.setParameter("fechaDesde", fechaDesde);
			/* 2511:2887 */ query.setParameter("fechaHasta", fechaHasta);
			/* 2512:2888 */ query.setParameter("estadoAnulado", Estado.ANULADO);
			/* 2513:2889 */ query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
			/* 2514:2891 */ if (idCategoriaEmpresa != 0) {
				/* 2515:2892 */ query.setParameter("idCategoriaEmpresa", Integer.valueOf(idCategoriaEmpresa));
				/* 2516: */ }
			/* 2517:2894 */ if (idEmpresa != 0) {
				/* 2518:2895 */ query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
				/* 2519: */ }
			/* 2520:2897 */ if (idZona != 0) {
				/* 2521:2898 */ query.setParameter("idZona", Integer.valueOf(idZona));
				/* 2522: */ }
			/* 2523:2900 */ if (idCanal != 0) {
				/* 2524:2901 */ query.setParameter("idCanal", Integer.valueOf(idCanal));
				/* 2525: */ }
			/* 2526:2903 */ if (idAgenteComercial != 0) {
				/* 2527:2904 */ query.setParameter("idAgenteComercial", Integer.valueOf(idAgenteComercial));
				/* 2528: */ }
			/* 2529:2906 */ if (idSucursal != 0) {
				/* 2530:2907 */ query.setParameter("idSucursal", Integer.valueOf(idSucursal));
				/* 2531: */ }
			/* 2532: */ }
		/* 2533:2911 */ return query.getResultList();
		/* 2534: */ }

	/* 2535: */
	/* 2536: */ public List<FacturaCliente> obtenerFacturasNotasCredito(int idOrganizacion, Date fechaDesde,
			Date fechaHasta, DocumentoBase documentoBase, int idEmpresa)
	/* 2537: */ {
		/* 2538:2918 */ StringBuilder sql = new StringBuilder();
		/* 2539:2919 */ sql.append(" SELECT fc from FacturaCliente fc");
		/* 2540:2920 */ sql.append(" JOIN FETCH fc.documento d ");
		/* 2541:2921 */ sql.append(" JOIN FETCH fc.empresa e");
		/* 2542:2922 */ sql.append(" WHERE fc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
		/* 2543:2923 */ sql.append(" AND d.documentoBase = :documentoBase");
		/* 2544:2924 */ sql.append(
				" AND fc.estado <> :estadoAnulado AND fc.estado <> :estadoEspera AND fc.estado <> :estadoContingencia");
		/* 2545:2925 */ sql.append(" AND d.indicadorDocumentoElectronico = TRUE");
		/* 2546:2926 */ sql.append(" AND (e.idEmpresa = :idEmpresa OR :idEmpresa=0) ");
		/* 2547: */
		/* 2548:2928 */ Query query = this.em.createQuery(sql.toString());
		/* 2549: */
		/* 2550:2930 */ query.setParameter("estadoAnulado", Estado.ANULADO);
		/* 2551:2931 */ query.setParameter("estadoEspera", Estado.EN_ESPERA);
		/* 2552:2932 */ query.setParameter("estadoContingencia", Estado.EN_ESPERA_CONTINGENCIA);
		/* 2553: */
		/* 2554:2934 */ query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
		/* 2555:2935 */ query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
		/* 2556:2936 */ query.setParameter("documentoBase", documentoBase);
		/* 2557: */
		/* 2558:2938 */ query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
		/* 2559:2939 */ return query.getResultList();
		/* 2560: */ }

	/* 2561: */
	/* 2562: */ public List<Producto> obtenerUltimosProductosVendidos(int idOrganizacion, Empresa empresa,
			Subempresa subempresa)
	/* 2563: */ {
		/* 2564:2946 */ StringBuilder sql1 = new StringBuilder();
		/* 2565:2947 */ sql1.append(" SELECT fc.idFacturaCliente ");
		/* 2566:2948 */ sql1.append(" FROM FacturaCliente fc ");
		/* 2567:2949 */ sql1.append(" INNER JOIN fc.documento doc ");
		/* 2568:2950 */ sql1.append(" INNER JOIN fc.empresa emp ");
		/* 2569:2951 */ if (subempresa != null) {
			/* 2570:2952 */ sql1.append(" INNER JOIN fc.subempresa sube ");
			/* 2571: */ }
		/* 2572:2954 */ sql1.append(" WHERE fc.idOrganizacion = :idOrganizacion ");
		/* 2573:2955 */ sql1.append(" AND doc.documentoBase = :documentoBaseFacturaCliente ");
		/* 2574:2956 */ sql1.append(" AND emp.idEmpresa = :idEmpresa ");
		/* 2575:2957 */ sql1.append(" AND fc.estado != :estadoAnulado ");
		/* 2576:2958 */ if (subempresa != null) {
			/* 2577:2959 */ sql1.append(" AND sube.idSubempresa = :idSubempresa ");
			/* 2578: */ } else {
			/* 2579:2961 */ sql1.append(" AND fc.subempresa IS NULL ");
			/* 2580: */ }
		/* 2581:2963 */ sql1.append(" ORDER BY fc.fecha DESC ");
		/* 2582:2964 */ Query query1 = this.em.createQuery(sql1.toString());
		/* 2583:2965 */ query1.setMaxResults(2);
		/* 2584: */
		/* 2585:2967 */ query1.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
		/* 2586:2968 */ query1.setParameter("idEmpresa", Integer.valueOf(empresa.getIdEmpresa()));
		/* 2587:2969 */ query1.setParameter("estadoAnulado", Estado.ANULADO);
		/* 2588:2970 */ query1.setParameter("documentoBaseFacturaCliente", DocumentoBase.FACTURA_CLIENTE);
		/* 2589:2971 */ if (subempresa != null) {
			/* 2590:2972 */ query1.setParameter("idSubempresa", Integer.valueOf(subempresa.getIdSubempresa()));
			/* 2591: */ }
		/* 2592:2975 */ List<Integer> listaIdUltimasFacturaCliente = query1.getResultList();
		/* 2593:2976 */ for (Integer integer : listaIdUltimasFacturaCliente) {
			/* 2594:2977 */ System.out.println("idFactura: " + integer);
			/* 2595: */ }
		/* 2596:2979 */ if ((listaIdUltimasFacturaCliente == null) || (listaIdUltimasFacturaCliente.isEmpty())) {
			/* 2597:2980 */ return new ArrayList();
			/* 2598: */ }
		/* 2599:2984 */ StringBuilder sql2 = new StringBuilder();
		/* 2600:2985 */ sql2.append(" SELECT fc.idFacturaCliente, p.idProducto, SUM(dfc.cantidad) ");
		/* 2601:2986 */ sql2.append(" FROM DetalleFacturaCliente dfc ");
		/* 2602:2987 */ sql2.append(" INNER JOIN dfc.producto p ");
		/* 2603:2988 */ sql2.append(" INNER JOIN dfc.facturaCliente fc ");
		/* 2604: */
		/* 2605:2990 */ sql2.append(" WHERE fc.idFacturaCliente IN :listaIdFacturaCliente ");
		/* 2606:2991 */ sql2.append(" GROUP BY fc.idFacturaCliente, p.idProducto ");
		/* 2607: */
		/* 2608:2993 */ Query query2 = this.em.createQuery(sql2.toString());
		/* 2609: */
		/* 2610:2995 */ query2.setParameter("listaIdFacturaCliente", listaIdUltimasFacturaCliente);
		/* 2611: */
		/* 2612:2997 */ List<Object[]> listaProductosPorFactura = query2.getResultList();
		/* 2613: */
		/* 2614: */
		/* 2615:3000 */ Map<Integer, Producto> mapaProductos = new HashMap();
		/* 2616:3001 */ for (Object[] row : listaProductosPorFactura)
		/* 2617: */ {
			/* 2618:3002 */ Integer idFactura = (Integer) row[0];
			/* 2619:3003 */ Integer idProducto = (Integer) row[1];
			/* 2620:3004 */ BigDecimal cantidadFactura = (BigDecimal) row[2];
			/* 2621:3005 */ BigDecimal cantidadDevuelta = obtenerCantidadDevuelta(idFactura, idProducto,
					Boolean.valueOf(true));
			/* 2622:3006 */ BigDecimal cantidadVenta = cantidadFactura.subtract(cantidadDevuelta);
			/* 2623:3007 */ Producto producto = (Producto) mapaProductos.get(idProducto);
			/* 2624:3008 */ if (producto == null)
			/* 2625: */ {
				/* 2626:3009 */ producto = this.productoDao.cargarDetalle(idProducto.intValue());
				/* 2627:3010 */ producto.setTraCantidad(cantidadVenta);
				/* 2628: */ }
			/* 2629: */ else
			/* 2630: */ {
				/* 2631:3012 */ BigDecimal cantidadPromedio = producto.getTraCantidad().add(cantidadVenta)
						.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP);
				/* 2632:3013 */ producto.setTraCantidad(cantidadPromedio);
				/* 2633: */ }
			/* 2634:3015 */ mapaProductos.put(idProducto, producto);
			/* 2635: */ }
		/* 2636:3018 */ Object listaProducto = new ArrayList();
		/* 2637:3019 */ ((List) listaProducto).addAll(mapaProductos.values());
		/* 2638:3020 */ return listaProducto;
		/* 2639: */ }

	/* 2640: */
	/* 2641: */ private BigDecimal obtenerCantidadDevuelta(Integer idFacturaCliente, Integer idProducto,
			Boolean indicadorAfectaDevolucion)
	/* 2642: */ {
		/* 2643:3025 */ StringBuilder sql = new StringBuilder();
		/* 2644:3026 */ sql.append(" SELECT SUM(dncc.cantidad) ");
		/* 2645:3027 */ sql.append(" FROM DetalleFacturaCliente dncc ");
		/* 2646:3028 */ sql.append(" INNER JOIN dncc.producto p ");
		/* 2647:3029 */ sql.append(" INNER JOIN dncc.facturaCliente ncc ");
		/* 2648:3030 */ sql.append(" INNER JOIN ncc.motivoNotaCreditoCliente mnc ");
		/* 2649:3031 */ sql.append(" INNER JOIN ncc.documento doc ");
		/* 2650:3032 */ sql.append(" INNER JOIN ncc.facturaClientePadre fc ");
		/* 2651:3033 */ sql.append(" WHERE fc.idFacturaCliente = :idFacturaCliente ");
		/* 2652:3034 */ sql.append(" AND doc.documentoBase = :documentoBaseDevolucionCliente ");
		/* 2653:3035 */ sql.append(" AND p.idProducto = :idProducto ");
		/* 2654:3036 */ if (indicadorAfectaDevolucion != null) {
			/* 2655:3037 */ sql.append(" AND mnc.indicadorAfectaDevolucion = :indicadorAfectaDevolucion ");
			/* 2656: */ }
		/* 2657:3039 */ sql.append(" AND ncc.estado != :estadoAnulado ");
		/* 2658:3040 */ Query query = this.em.createQuery(sql.toString());
		/* 2659: */
		/* 2660:3042 */ query.setParameter("idFacturaCliente", idFacturaCliente);
		/* 2661:3043 */ query.setParameter("idProducto", idProducto);
		/* 2662:3044 */ query.setParameter("documentoBaseDevolucionCliente", DocumentoBase.DEVOLUCION_CLIENTE);
		/* 2663:3045 */ query.setParameter("estadoAnulado", Estado.ANULADO);
		/* 2664:3046 */ if (indicadorAfectaDevolucion != null) {
			/* 2665:3047 */ query.setParameter("indicadorAfectaDevolucion", indicadorAfectaDevolucion);
			/* 2666: */ }
		/* 2667:3050 */ BigDecimal cantidadDevuelta = BigDecimal.ZERO;
		/* 2668: */ try
		/* 2669: */ {
			/* 2670:3052 */ cantidadDevuelta = (BigDecimal) query.getSingleResult();
			/* 2671: */ }
		/* 2672: */ catch (NoResultException e)
		/* 2673: */ {
			/* 2674:3054 */ cantidadDevuelta = BigDecimal.ZERO;
			/* 2675: */ }
		/* 2676:3056 */ if (cantidadDevuelta == null) {
			/* 2677:3057 */ cantidadDevuelta = BigDecimal.ZERO;
			/* 2678: */ }
		/* 2679:3059 */ return cantidadDevuelta;
		/* 2680: */ }
	/* 2681: */ }

/*
 * Location: C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * 
 * Qualified Name: com.asinfo.as2.dao.FacturaClienteDao
 * 
 * JD-Core Version: 0.7.0.1
 * 
 */