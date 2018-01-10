/*    1:     */ package com.asinfo.as2.compras.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*    4:     */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*    5:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    6:     */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioFacturaProveedorImportacion;
/*    7:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    8:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioNotaCreditoProveedor;
/*    9:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*   10:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioVerificadorCompras;
/*   11:     */ import com.asinfo.as2.dao.CajaChicaDao;
/*   12:     */ import com.asinfo.as2.dao.CuentaPorPagarDao;
/*   13:     */ import com.asinfo.as2.dao.DetalleFacturaProveedorDao;
/*   14:     */ import com.asinfo.as2.dao.DetalleFacturaProveedorImportacionDao;
/*   15:     */ import com.asinfo.as2.dao.DetalleFacturaProveedorImportacionProductoDao;
/*   16:     */ import com.asinfo.as2.dao.DetalleRecepcionProveedorDao;
/*   17:     */ import com.asinfo.as2.dao.DocumentoContabilizacionDao;
/*   18:     */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*   19:     */ import com.asinfo.as2.dao.GastoProductoFacturaProveedorDao;
/*   20:     */ import com.asinfo.as2.dao.GenericoDao;
/*   21:     */ import com.asinfo.as2.dao.ImpuestoProductoFacturaProveedorDao;
/*   22:     */ import com.asinfo.as2.dao.InventarioProductoDao;
/*   23:     */ import com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao;
/*   24:     */ import com.asinfo.as2.dao.sri.DetalleFacturaProveedorSRIDao;
/*   25:     */ import com.asinfo.as2.dao.sri.FacturaProveedorSRIDao;
/*   26:     */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   27:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   28:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   29:     */ import com.asinfo.as2.entities.Asiento;
/*   30:     */ import com.asinfo.as2.entities.CajaChica;
/*   31:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   32:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   33:     */ import com.asinfo.as2.entities.CentroCosto;
/*   34:     */ import com.asinfo.as2.entities.CompraCajaChica;
/*   35:     */ import com.asinfo.as2.entities.CondicionPago;
/*   36:     */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   37:     */ import com.asinfo.as2.entities.CuentaContable;
/*   38:     */ import com.asinfo.as2.entities.CuentaPorPagar;
/*   39:     */ import com.asinfo.as2.entities.DetalleAsiento;
/*   40:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   41:     */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacion;
/*   42:     */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionProducto;
/*   43:     */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   44:     */ import com.asinfo.as2.entities.DimensionContable;
/*   45:     */ import com.asinfo.as2.entities.Documento;
/*   46:     */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*   47:     */ import com.asinfo.as2.entities.Empresa;
/*   48:     */ import com.asinfo.as2.entities.EntidadBase;
/*   49:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   50:     */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   51:     */ import com.asinfo.as2.entities.GastoProductoFacturaProveedor;
/*   52:     */ import com.asinfo.as2.entities.Impuesto;
/*   53:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor;
/*   54:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   55:     */ import com.asinfo.as2.entities.Organizacion;
/*   56:     */ import com.asinfo.as2.entities.Pago;
/*   57:     */ import com.asinfo.as2.entities.Producto;
/*   58:     */ import com.asinfo.as2.entities.Proveedor;
/*   59:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   60:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   61:     */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   62:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   63:     */ import com.asinfo.as2.entities.Sucursal;
/*   64:     */ import com.asinfo.as2.entities.TipoAsiento;
/*   65:     */ import com.asinfo.as2.entities.sri.AnuladoSRI;
/*   66:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*   67:     */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   68:     */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*   69:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   70:     */ import com.asinfo.as2.entities.sri.ReembolsoGastos;
/*   71:     */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   72:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   73:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   74:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   75:     */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*   76:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   77:     */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*   78:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   79:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   80:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI;
/*   81:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAnuladoSRI;
/*   82:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*   83:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*   84:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*   85:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*   86:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*   87:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*   88:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*   89:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   90:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   91:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/*   92:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   93:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   94:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*   95:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*   96:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*   97:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*   98:     */ import com.asinfo.as2.util.AppUtil;
/*   99:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  100:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*  101:     */ import java.io.PrintStream;
/*  102:     */ import java.math.BigDecimal;
/*  103:     */ import java.math.RoundingMode;
/*  104:     */ import java.util.ArrayList;
/*  105:     */ import java.util.Calendar;
/*  106:     */ import java.util.Collection;
/*  107:     */ import java.util.Date;
/*  108:     */ import java.util.HashMap;
/*  109:     */ import java.util.HashSet;
/*  110:     */ import java.util.Iterator;
/*  111:     */ import java.util.List;
/*  112:     */ import java.util.Map;
/*  113:     */ import java.util.Set;
/*  114:     */ import javax.ejb.EJB;
/*  115:     */ import javax.ejb.SessionContext;
/*  116:     */ import javax.ejb.Stateless;
/*  117:     */ import javax.ejb.TransactionAttribute;
/*  118:     */ import javax.ejb.TransactionAttributeType;
/*  119:     */ import javax.ejb.TransactionManagement;
/*  120:     */ import javax.ejb.TransactionManagementType;
/*  121:     */ import org.apache.log4j.Logger;
/*  122:     */ 
/*  123:     */ @Stateless
/*  124:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  125:     */ public class ServicioFacturaProveedorImpl
/*  126:     */   extends AbstractServicioAS2Financiero
/*  127:     */   implements ServicioFacturaProveedor
/*  128:     */ {
/*  129:     */   private static final long serialVersionUID = -4230804044196235710L;
/*  130:     */   @EJB
/*  131:     */   private transient FacturaProveedorDao facturaProveedorDao;
/*  132:     */   @EJB
/*  133:     */   private transient DetalleFacturaProveedorDao detalleFacturaProveedorDao;
/*  134:     */   @EJB
/*  135:     */   private transient ServicioSecuencia servicioSecuencia;
/*  136:     */   @EJB
/*  137:     */   private transient CuentaPorPagarDao cuentaPorPagarDao;
/*  138:     */   @EJB
/*  139:     */   private transient GastoProductoFacturaProveedorDao gastoProductoFacturaProveedorDao;
/*  140:     */   @EJB
/*  141:     */   private transient ServicioCondicionPago servicioCondicionPago;
/*  142:     */   @EJB
/*  143:     */   private transient ImpuestoProductoFacturaProveedorDao impuestoProductoFacturaProveedorDao;
/*  144:     */   @EJB
/*  145:     */   private transient DetalleFacturaProveedorImportacionDao detalleFacturaProveedorImportacionDao;
/*  146:     */   @EJB
/*  147:     */   private transient DetalleFacturaProveedorImportacionProductoDao detalleFacturaProveedorImportacionProductoDao;
/*  148:     */   @EJB
/*  149:     */   private transient ServicioPeriodo servicioPeriodo;
/*  150:     */   @EJB
/*  151:     */   private transient FacturaProveedorSRIDao facturaProveedorSRIDao;
/*  152:     */   @EJB
/*  153:     */   private transient DetalleFacturaProveedorSRIDao detallefacturaProveedorSRIDao;
/*  154:     */   @EJB
/*  155:     */   private transient ServicioVerificadorInventario servicioVerificadorInventario;
/*  156:     */   @EJB
/*  157:     */   private transient ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  158:     */   @EJB
/*  159:     */   private transient ServicioProducto servicioProducto;
/*  160:     */   @EJB
/*  161:     */   private transient ServicioDocumento servicioDocumento;
/*  162:     */   @EJB
/*  163:     */   private transient ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  164:     */   @EJB
/*  165:     */   private transient ServicioImpuesto servicioImpuesto;
/*  166:     */   @EJB
/*  167:     */   private transient ServicioRecepcionProveedor servicioRecepcionProveedor;
/*  168:     */   @EJB
/*  169:     */   private transient DetalleRecepcionProveedorDao detalleRecepcionProveedorDao;
/*  170:     */   @EJB
/*  171:     */   private transient InventarioProductoDao inventarioProductoDao;
/*  172:     */   @EJB
/*  173:     */   private transient ServicioCosteo servicioCosteo;
/*  174:     */   @EJB
/*  175:     */   private transient ServicioVerificadorCompras servicioVerificadorCompras;
/*  176:     */   @EJB
/*  177:     */   private transient ServicioFacturaProveedorImportacion servicioFacturaProveedorImportacion;
/*  178:     */   @EJB
/*  179:     */   private transient ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  180:     */   @EJB
/*  181:     */   private transient ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  182:     */   @EJB
/*  183:     */   private transient ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  184:     */   @EJB
/*  185:     */   private transient GenericoDao<ReembolsoGastos> reembolsoGastosDao;
/*  186:     */   @EJB
/*  187:     */   private transient ServicioConceptoRetencionSRI servicioConceptoRetencionSRI;
/*  188:     */   @EJB
/*  189:     */   private transient ServicioRegistroPeso servicioRegistroPeso;
/*  190:     */   @EJB
/*  191:     */   private transient DetalleFacturaProveedorSRIDao detalleFacturaProveedorSRIDao;
/*  192:     */   @EJB
/*  193:     */   private transient ServicioPago servicioPago;
/*  194:     */   @EJB
/*  195:     */   private transient ServicioAnuladoSRI servicioAnuladoSRI;
/*  196:     */   @EJB
/*  197:     */   private transient ComprobanteElectronicoPendienteSRIDao comprobanteElectronicoPendienteSRIDao;
/*  198:     */   @EJB
/*  199:     */   private transient DocumentoContabilizacionDao documentoContabilizacionDao;
/*  200:     */   @EJB
/*  201:     */   private transient CajaChicaDao cajaChicaDao;
/*  202:     */   @EJB
/*  203:     */   private transient ServicioNotaCreditoProveedor servicioNotaCreditoProveedor;
/*  204:     */   
/*  205:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  206:     */   public void guardar(FacturaProveedor facturaProveedor)
/*  207:     */     throws ExcepcionAS2, ExcepcionAS2Compras, ExcepcionAS2Financiero, AS2Exception
/*  208:     */   {
/*  209: 213 */     guardar(facturaProveedor, true);
/*  210:     */   }
/*  211:     */   
/*  212:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  213:     */   public void guardar(FacturaProveedor facturaProveedor, boolean verificaPeriodo)
/*  214:     */     throws ExcepcionAS2, ExcepcionAS2Compras, ExcepcionAS2Financiero, AS2Exception
/*  215:     */   {
/*  216:     */     try
/*  217:     */     {
/*  218: 228 */       if ((facturaProveedor.getFacturaProveedorSRI() != null) && (facturaProveedor.getFacturaProveedorSRI().isIndicadorReembolso())) {
/*  219: 229 */         validaReembolsoGastos(facturaProveedor.getFacturaProveedorSRI());
/*  220:     */       }
/*  221: 233 */       this.servicioVerificadorInventario.cantidadDetalle(facturaProveedor.getListaDetalleFacturaProveedor());
/*  222: 235 */       if (!facturaProveedor.isIndicadorNoValidarPrecioMayorCero()) {
/*  223: 236 */         this.servicioVerificadorInventario.verificarTotalDetalle(facturaProveedor.getListaDetalleFacturaProveedor());
/*  224:     */       }
/*  225: 240 */       validar(facturaProveedor, verificaPeriodo);
/*  226: 241 */       validarGastoProductoFacturaProveedor(facturaProveedor);
/*  227:     */       DetalleFacturaProveedor dfp;
/*  228:     */       GastoProductoFacturaProveedor gasto;
/*  229: 243 */       if (facturaProveedor.isSoloLectura())
/*  230:     */       {
/*  231: 246 */         for (Iterator localIterator1 = facturaProveedor.getListaDetalleFacturaProveedor().iterator(); localIterator1.hasNext();)
/*  232:     */         {
/*  233: 246 */           dfp = (DetalleFacturaProveedor)localIterator1.next();
/*  234: 247 */           guardarGastoImportacion(dfp);
/*  235: 249 */           for (Iterator localIterator2 = dfp.getListaGastoProductoFacturaProveedor().iterator(); localIterator2.hasNext();)
/*  236:     */           {
/*  237: 249 */             gasto = (GastoProductoFacturaProveedor)localIterator2.next();
/*  238: 250 */             this.gastoProductoFacturaProveedorDao.guardar(gasto);
/*  239:     */           }
/*  240: 252 */           this.detalleFacturaProveedorDao.guardar(dfp);
/*  241:     */         }
/*  242: 254 */         guardarReembolso(facturaProveedor.getFacturaProveedorSRI());
/*  243: 255 */         contabilizar(facturaProveedor);
/*  244:     */       }
/*  245:     */       else
/*  246:     */       {
/*  247: 260 */         if ((facturaProveedor.getRecepcionProveedor() != null) && (facturaProveedor.getRecepcionProveedor().getIdRecepcionProveedor() == 0)) {
/*  248: 261 */           this.servicioRecepcionProveedor.guardar(facturaProveedor.getRecepcionProveedor(), true, false);
/*  249:     */         }
/*  250: 265 */         this.servicioVerificadorCompras.actualizarPrecioFechaUltimaCompra(facturaProveedor);
/*  251: 268 */         if (!facturaProveedor.isIndicadorLiquidarImportacion()) {
/*  252: 269 */           this.servicioVerificadorCompras.actualizarCantidadPorFacturar(facturaProveedor, true);
/*  253:     */         }
/*  254: 272 */         if ((facturaProveedor.getNumero() == null) || (facturaProveedor.getNumero().isEmpty())) {
/*  255: 273 */           cargarSecuencia(facturaProveedor, null);
/*  256:     */         }
/*  257: 275 */         Object mapaRecepcionProveedor = new HashMap();
/*  258: 276 */         for (dfp = facturaProveedor.getListaDetalleFacturaProveedor().iterator(); dfp.hasNext();)
/*  259:     */         {
/*  260: 276 */           dfp = (DetalleFacturaProveedor)dfp.next();
/*  261: 280 */           for (ImpuestoProductoFacturaProveedor impuestoProductoFacturaProveedor : ((DetalleFacturaProveedor)dfp).getListaImpuestoProductoFacturaProveedor()) {
/*  262: 281 */             this.impuestoProductoFacturaProveedorDao.guardar(impuestoProductoFacturaProveedor);
/*  263:     */           }
/*  264: 284 */           guardarGastoImportacion((DetalleFacturaProveedor)dfp);
/*  265: 288 */           for (GastoProductoFacturaProveedor gasto : ((DetalleFacturaProveedor)dfp).getListaGastoProductoFacturaProveedor())
/*  266:     */           {
/*  267: 289 */             if (!((DetalleFacturaProveedor)dfp).getProducto().isTraIndicadorServicio()) {
/*  268: 290 */               gasto.setEliminado(true);
/*  269:     */             }
/*  270: 293 */             this.gastoProductoFacturaProveedorDao.guardar(gasto);
/*  271:     */           }
/*  272: 296 */           this.detalleFacturaProveedorDao.guardar((EntidadBase)dfp);
/*  273: 298 */           if (!facturaProveedor.isIndicadorLiquidarImportacion()) {
/*  274: 300 */             for (DetalleRecepcionProveedor detalleRecepcionProveedor : ((DetalleFacturaProveedor)dfp).getListaDetalleRecepcionProveedor())
/*  275:     */             {
/*  276: 301 */               this.detalleRecepcionProveedorDao.guardar(detalleRecepcionProveedor);
/*  277: 302 */               ((Map)mapaRecepcionProveedor).put(Integer.valueOf(detalleRecepcionProveedor.getRecepcionProveedor().getId()), detalleRecepcionProveedor
/*  278: 303 */                 .getRecepcionProveedor());
/*  279:     */             }
/*  280:     */           }
/*  281:     */         }
/*  282: 310 */         BigDecimal bono = facturaProveedor.getBono();
/*  283: 311 */         for (Object dfp = facturaProveedor.getListaCuentaPorPagar().iterator(); ((Iterator)dfp).hasNext();)
/*  284:     */         {
/*  285: 311 */           CuentaPorPagar cuentaPorPagar = (CuentaPorPagar)((Iterator)dfp).next();
/*  286: 312 */           cuentaPorPagar.setSaldo(cuentaPorPagar.getValor());
/*  287: 315 */           if ((!cuentaPorPagar.isEliminado()) && (bono.compareTo(BigDecimal.ZERO) > 0)) {
/*  288: 316 */             if (bono.compareTo(cuentaPorPagar.getSaldo()) > 0)
/*  289:     */             {
/*  290: 317 */               bono = bono.subtract(cuentaPorPagar.getSaldo());
/*  291: 318 */               cuentaPorPagar.setSaldo(BigDecimal.ZERO);
/*  292:     */             }
/*  293:     */             else
/*  294:     */             {
/*  295: 320 */               cuentaPorPagar.setSaldo(cuentaPorPagar.getSaldo().subtract(bono));
/*  296: 321 */               bono = BigDecimal.ZERO;
/*  297:     */             }
/*  298:     */           }
/*  299: 325 */           this.cuentaPorPagarDao.guardar(cuentaPorPagar);
/*  300:     */         }
/*  301: 328 */         FacturaProveedorSRI facturaProveedorSRI = facturaProveedor.getFacturaProveedorSRI();
/*  302: 329 */         guardarReembolso(facturaProveedorSRI);
/*  303: 332 */         if (facturaProveedor.getDocumento().getDocumentoBase() == DocumentoBase.PEDIDO_IMPORTACION) {
/*  304: 334 */           this.servicioFacturaProveedorImportacion.guardar(facturaProveedor.getFacturaProveedorImportacion());
/*  305:     */         }
/*  306: 340 */         this.facturaProveedorDao.guardar(facturaProveedor);
/*  307:     */         
/*  308:     */ 
/*  309: 343 */         Set<Integer> listaIdDevoluciones = new HashSet();
/*  310: 347 */         for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor()) {
/*  311: 349 */           if (!dfp.isEliminado())
/*  312:     */           {
/*  313: 351 */             costoLinea = dfp.getBaseImponible();
/*  314: 352 */             if (!facturaProveedor.isIndicadorCreditoTributario()) {
/*  315: 353 */               costoLinea = costoLinea.add(dfp.getValorImpuestosLinea());
/*  316:     */             }
/*  317: 356 */             if ((!facturaProveedor.isIndicadorLiquidarImportacion()) && (TipoProducto.ARTICULO.equals(dfp.getProducto().getTipoProducto())))
/*  318:     */             {
/*  319: 359 */               BigDecimal cantidadTotal = BigDecimal.ZERO;
/*  320: 360 */               if (!dfp.getListaDetalleRecepcionProveedor().isEmpty())
/*  321:     */               {
/*  322: 361 */                 Set<Integer> listaIdDetallesRecepciones = new HashSet();
/*  323: 362 */                 for (DetalleRecepcionProveedor detalleRecepcionProveedor : dfp.getListaDetalleRecepcionProveedor())
/*  324:     */                 {
/*  325: 363 */                   listaIdDetallesRecepciones.add(Integer.valueOf(detalleRecepcionProveedor.getId()));
/*  326: 364 */                   cantidadTotal = cantidadTotal.add(detalleRecepcionProveedor.getInventarioProducto().getCantidad());
/*  327:     */                 }
/*  328: 367 */                 costoLinea = cantidadTotal.compareTo(BigDecimal.ZERO) > 0 ? costoLinea.divide(cantidadTotal, 18, RoundingMode.HALF_UP) : costoLinea;
/*  329: 370 */                 for (DetalleRecepcionProveedor detalleRecepcionProveedor : dfp.getListaDetalleRecepcionProveedor())
/*  330:     */                 {
/*  331: 372 */                   detalleRecepcionProveedor.getInventarioProducto().setCosto(costoLinea.multiply(detalleRecepcionProveedor.getInventarioProducto().getCantidad()));
/*  332: 373 */                   this.inventarioProductoDao.guardar(detalleRecepcionProveedor.getInventarioProducto());
/*  333:     */                 }
/*  334: 379 */                 for (Integer id : listaIdDetallesRecepciones)
/*  335:     */                 {
/*  336: 380 */                   List<DetalleFacturaProveedor> listDetallesDevoluciones = this.servicioNotaCreditoProveedor.getDetallesDevolucionesProveedor(id.intValue());
/*  337: 381 */                   for (DetalleFacturaProveedor detalleDevolucion : listDetallesDevoluciones)
/*  338:     */                   {
/*  339: 382 */                     listaIdDevoluciones.add(Integer.valueOf(detalleDevolucion.getFacturaProveedor().getId()));
/*  340: 383 */                     detalleDevolucion.getInventarioProducto()
/*  341: 384 */                       .setCosto(costoLinea.multiply(detalleDevolucion.getInventarioProducto().getCantidad()));
/*  342: 385 */                     this.inventarioProductoDao.guardar(detalleDevolucion.getInventarioProducto());
/*  343:     */                   }
/*  344:     */                 }
/*  345:     */               }
/*  346:     */             }
/*  347:     */           }
/*  348:     */         }
/*  349:     */         BigDecimal costoLinea;
/*  350: 394 */         if (!facturaProveedor.isIndicadorLiquidarImportacion()) {
/*  351: 395 */           this.servicioVerificadorCompras.actualizarCantidadPorFacturar(facturaProveedor, false);
/*  352:     */         }
/*  353: 399 */         this.servicioSecuencia.actualizarSecuencia(facturaProveedor.getDocumento().getSecuencia(), facturaProveedor.getNumero());
/*  354: 401 */         if (facturaProveedorSRI != null) {
/*  355: 403 */           if ((facturaProveedorSRI.getTipoComprobanteSRI() != null) && (facturaProveedorSRI.getSecuenciaLiquidacionCompra() != null) && 
/*  356: 404 */             (facturaProveedorSRI.isIndicadorLiquidacionCompra()))
/*  357:     */           {
/*  358: 406 */             numeroLiquidacionCompra = facturaProveedorSRI.getNumero();
/*  359:     */             
/*  360: 408 */             this.servicioSecuencia.actualizarSecuencia(facturaProveedorSRI.getSecuenciaLiquidacionCompra(), (String)numeroLiquidacionCompra);
/*  361:     */           }
/*  362:     */         }
/*  363: 413 */         this.servicioRegistroPeso.actualizarLiquidacion(facturaProveedor);
/*  364:     */         
/*  365: 415 */         contabilizar(facturaProveedor);
/*  366: 420 */         if (!facturaProveedor.getDocumento().isIndicadorDocumentoExterior())
/*  367:     */         {
/*  368: 421 */           for (numeroLiquidacionCompra = ((Map)mapaRecepcionProveedor).values().iterator(); ((Iterator)numeroLiquidacionCompra).hasNext();)
/*  369:     */           {
/*  370: 421 */             RecepcionProveedor recepcionProveedor = (RecepcionProveedor)((Iterator)numeroLiquidacionCompra).next();
/*  371: 422 */             recepcionProveedor = this.servicioRecepcionProveedor.cargarDetalleAFacturar(recepcionProveedor.getId());
/*  372: 423 */             recepcionProveedor.setFacturaProveedor(facturaProveedor);
/*  373: 424 */             recepcionProveedor.setEstado(Estado.FACTURADO);
/*  374: 425 */             if (recepcionProveedor.getFacturaProveedor().getFacturaProveedorSRI() != null)
/*  375:     */             {
/*  376: 428 */               String numeroFactura = recepcionProveedor.getFacturaProveedor().getFacturaProveedorSRI().getEstablecimiento() + "-" + recepcionProveedor.getFacturaProveedor().getFacturaProveedorSRI().getPuntoEmision() + "-" + recepcionProveedor.getFacturaProveedor().getFacturaProveedorSRI().getNumero();
/*  377: 429 */               recepcionProveedor.setNumeroFactura(numeroFactura);
/*  378:     */             }
/*  379:     */             else
/*  380:     */             {
/*  381: 431 */               recepcionProveedor.setNumeroFactura(recepcionProveedor.getFacturaProveedor().getReferencia3());
/*  382:     */             }
/*  383: 433 */             this.servicioRecepcionProveedor.contabilizar(recepcionProveedor);
/*  384: 434 */             if (ParametrosSistema.getGeneracionDeCostosAutomatica(recepcionProveedor.getIdOrganizacion()).booleanValue()) {
/*  385: 436 */               this.servicioCosteo.generarCostos(recepcionProveedor, 
/*  386: 437 */                 ParametrosSistema.isCosteoPorBodega(recepcionProveedor.getIdOrganizacion()).booleanValue());
/*  387:     */             }
/*  388:     */           }
/*  389: 440 */           for (numeroLiquidacionCompra = listaIdDevoluciones.iterator(); ((Iterator)numeroLiquidacionCompra).hasNext();)
/*  390:     */           {
/*  391: 440 */             Integer idDevolucion = (Integer)((Iterator)numeroLiquidacionCompra).next();
/*  392: 441 */             this.servicioRecepcionProveedor.contabilizar(null, this.facturaProveedorDao.cargarDetalle(idDevolucion.intValue(), false));
/*  393:     */           }
/*  394:     */         }
/*  395:     */       }
/*  396:     */     }
/*  397:     */     catch (ExcepcionAS2Compras e)
/*  398:     */     {
/*  399:     */       Object numeroLiquidacionCompra;
/*  400: 448 */       this.context.setRollbackOnly();
/*  401: 449 */       e.printStackTrace();
/*  402: 450 */       throw e;
/*  403:     */     }
/*  404:     */     catch (ExcepcionAS2Financiero e)
/*  405:     */     {
/*  406: 452 */       this.context.setRollbackOnly();
/*  407: 453 */       e.printStackTrace();
/*  408: 454 */       throw e;
/*  409:     */     }
/*  410:     */     catch (ExcepcionAS2 e)
/*  411:     */     {
/*  412: 456 */       this.context.setRollbackOnly();
/*  413: 457 */       e.printStackTrace();
/*  414: 458 */       throw e;
/*  415:     */     }
/*  416:     */     catch (AS2Exception e)
/*  417:     */     {
/*  418: 460 */       this.context.setRollbackOnly();
/*  419: 461 */       e.printStackTrace();
/*  420: 462 */       throw e;
/*  421:     */     }
/*  422:     */     catch (Exception e)
/*  423:     */     {
/*  424: 464 */       this.context.setRollbackOnly();
/*  425: 465 */       e.printStackTrace();
/*  426: 466 */       LOG.error(e);
/*  427: 467 */       throw new ExcepcionAS2(e);
/*  428:     */     }
/*  429:     */   }
/*  430:     */   
/*  431:     */   private void guardarReembolso(FacturaProveedorSRI facturaProveedorSRI)
/*  432:     */   {
/*  433: 473 */     if (facturaProveedorSRI != null)
/*  434:     */     {
/*  435: 475 */       for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI()) {
/*  436: 476 */         this.detallefacturaProveedorSRIDao.guardar(detalleFacturaProveedorSRI);
/*  437:     */       }
/*  438: 479 */       this.facturaProveedorSRIDao.guardar(facturaProveedorSRI);
/*  439: 481 */       for (ReembolsoGastos rg : facturaProveedorSRI.getListaReembolsoGastos())
/*  440:     */       {
/*  441: 482 */         if (!facturaProveedorSRI.isIndicadorReembolso()) {
/*  442: 483 */           rg.setEliminado(true);
/*  443:     */         }
/*  444: 485 */         this.reembolsoGastosDao.guardar(rg);
/*  445:     */       }
/*  446:     */     }
/*  447:     */   }
/*  448:     */   
/*  449:     */   public void validaReembolsoGastos(FacturaProveedorSRI facturaProveedorSRI)
/*  450:     */     throws AS2Exception
/*  451:     */   {
/*  452: 492 */     BigDecimal baseImponibleDiferenteCeroRG = BigDecimal.ZERO;
/*  453: 493 */     BigDecimal baseImponibleTarifaCeroRG = BigDecimal.ZERO;
/*  454: 494 */     BigDecimal descuento = BigDecimal.ZERO;
/*  455: 495 */     int i = 0;
/*  456: 496 */     if (facturaProveedorSRI != null)
/*  457:     */     {
/*  458: 497 */       for (ReembolsoGastos rg : facturaProveedorSRI.getListaReembolsoGastos()) {
/*  459: 498 */         if (!rg.isEliminado())
/*  460:     */         {
/*  461: 499 */           i++;
/*  462: 500 */           baseImponibleDiferenteCeroRG = baseImponibleDiferenteCeroRG.add(rg.getBaseImponibleDiferenteCero());
/*  463: 501 */           baseImponibleTarifaCeroRG = baseImponibleTarifaCeroRG.add(rg.getBaseImponibleTarifaCero());
/*  464: 502 */           descuento = descuento.add(rg.getDescuentoImpuesto());
/*  465:     */         }
/*  466:     */       }
/*  467: 506 */       if (i == 0) {
/*  468: 507 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.servicio.impl.ServicioFacturaProveedorImpl.MENSAJE_ERROR_DEBE_EXISTIR_UN_REMBOLSO", new String[] { "" });
/*  469:     */       }
/*  470: 510 */       if ((facturaProveedorSRI.getCompraCajaChica() != null) && 
/*  471: 511 */         (facturaProveedorSRI.getCompraCajaChica().getDescuentoImpuesto().compareTo(descuento) < 0)) {
/*  472: 512 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.servicio.impl.ServicioFacturaProveedorImpl.MENSAJE_ERROR_DETALLE_REMBOLSO_DESCUENTO", new String[] { "" });
/*  473:     */       }
/*  474: 515 */       if ((facturaProveedorSRI.getFacturaProveedor() != null) && 
/*  475: 516 */         (facturaProveedorSRI.getFacturaProveedor().getDescuentoImpuesto().compareTo(descuento) < 0)) {
/*  476: 517 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.servicio.impl.ServicioFacturaProveedorImpl.MENSAJE_ERROR_DETALLE_REMBOLSO_DESCUENTO", new String[] { "" });
/*  477:     */       }
/*  478: 520 */       if (facturaProveedorSRI.getFacturaProveedor() != null)
/*  479:     */       {
/*  480: 522 */         BigDecimal totalFactura = facturaProveedorSRI.getFacturaProveedor().getTotal().subtract(facturaProveedorSRI.getFacturaProveedor().getDescuento()).add(facturaProveedorSRI.getFacturaProveedor().getImpuesto());
/*  481: 523 */         if (baseImponibleDiferenteCeroRG.add(baseImponibleTarifaCeroRG).compareTo(totalFactura) == 1) {
/*  482: 524 */           throw new AS2Exception("com.asinfo.as2.compras.procesos.servicio.impl.ServicioFacturaProveedorImpl.ERROR_BASE_IMPONIBLE_TARIFA_CERO_DIFERENTE_CERO", new String[] { "" });
/*  483:     */         }
/*  484:     */       }
/*  485: 527 */       if (facturaProveedorSRI.getCompraCajaChica() != null)
/*  486:     */       {
/*  487: 528 */         BigDecimal totalCompraCajaChica = facturaProveedorSRI.getCompraCajaChica().getValor();
/*  488: 529 */         if (baseImponibleDiferenteCeroRG.add(baseImponibleTarifaCeroRG).compareTo(totalCompraCajaChica) == 1) {
/*  489: 530 */           throw new AS2Exception("com.asinfo.as2.compras.procesos.servicio.impl.ServicioFacturaProveedorImpl.ERROR_BASE_IMPONIBLE_TARIFA_CERO_DIFERENTE_CERO", new String[] { "" });
/*  490:     */         }
/*  491:     */       }
/*  492:     */     }
/*  493:     */   }
/*  494:     */   
/*  495:     */   private void guardarGastoImportacion(DetalleFacturaProveedor dfp)
/*  496:     */   {
/*  497: 544 */     for (Iterator localIterator1 = dfp.getListaDetalleFacturaProveedorImportacion().iterator(); localIterator1.hasNext();)
/*  498:     */     {
/*  499: 544 */       dfpi = (DetalleFacturaProveedorImportacion)localIterator1.next();
/*  500: 545 */       this.detalleFacturaProveedorImportacionDao.guardar(dfpi);
/*  501: 546 */       for (DetalleFacturaProveedorImportacionProducto dfpip : dfpi.getListaDetalleFacturaProveedorImportacionProducto())
/*  502:     */       {
/*  503: 547 */         dfpip.setEliminado(!dfpi.isIndicadorDistribucionManual());
/*  504: 548 */         this.detalleFacturaProveedorImportacionProductoDao.guardar(dfpip);
/*  505:     */       }
/*  506:     */     }
/*  507:     */     DetalleFacturaProveedorImportacion dfpi;
/*  508:     */   }
/*  509:     */   
/*  510:     */   public void cargarSecuencia(FacturaProveedor facturaProveedor, PuntoDeVenta puntoDeVenta)
/*  511:     */     throws ExcepcionAS2
/*  512:     */   {
/*  513: 557 */     if (puntoDeVenta != null) {
/*  514: 558 */       this.servicioDocumento.cargarSecuencia(facturaProveedor.getDocumento(), puntoDeVenta, facturaProveedor.getFecha());
/*  515:     */     }
/*  516: 561 */     String numero = this.servicioSecuencia.obtenerSecuencia(facturaProveedor.getDocumento().getSecuencia(), facturaProveedor.getFecha());
/*  517:     */     
/*  518: 563 */     facturaProveedor.setNumero(numero);
/*  519:     */   }
/*  520:     */   
/*  521:     */   public void eliminar(FacturaProveedor facturaProveedor)
/*  522:     */   {
/*  523: 573 */     this.facturaProveedorDao.eliminar(facturaProveedor);
/*  524:     */   }
/*  525:     */   
/*  526:     */   public FacturaProveedor buscarPorId(Integer id)
/*  527:     */   {
/*  528: 584 */     return (FacturaProveedor)this.facturaProveedorDao.buscarPorId(id);
/*  529:     */   }
/*  530:     */   
/*  531:     */   public FacturaProveedor cargarDetalle(Integer idFacturaProveedor)
/*  532:     */     throws ExcepcionAS2Compras
/*  533:     */   {
/*  534: 594 */     return this.facturaProveedorDao.cargarDetalle(idFacturaProveedor.intValue());
/*  535:     */   }
/*  536:     */   
/*  537:     */   public FacturaProveedor cargarInformacionSRI(Integer idFacturaProveedor)
/*  538:     */     throws ExcepcionAS2Compras
/*  539:     */   {
/*  540: 604 */     return this.facturaProveedorDao.cargarInformacionSRI(idFacturaProveedor.intValue());
/*  541:     */   }
/*  542:     */   
/*  543:     */   public void totalizar(FacturaProveedor facturaProveedor)
/*  544:     */     throws ExcepcionAS2Compras
/*  545:     */   {
/*  546: 615 */     BigDecimal total = BigDecimal.ZERO;
/*  547: 616 */     BigDecimal descuento = BigDecimal.ZERO;
/*  548: 618 */     for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor()) {
/*  549: 619 */       if (!dfp.isEliminado())
/*  550:     */       {
/*  551: 620 */         dfp.setDescuento(dfp.getPrecio().multiply(dfp.getPorcentajeDescuento()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/*  552: 621 */         total = total.add(dfp.getPrecioLinea());
/*  553: 622 */         descuento = descuento.add(dfp.getDescuentoLinea());
/*  554:     */       }
/*  555:     */     }
/*  556: 626 */     if (facturaProveedor.getFacturaProveedorImportacion() == null) {
/*  557: 627 */       totalizarImpuesto(facturaProveedor);
/*  558:     */     }
/*  559: 629 */     facturaProveedor.setTotal(FuncionesUtiles.redondearBigDecimal(total));
/*  560: 630 */     facturaProveedor.setDescuento(FuncionesUtiles.redondearBigDecimal(descuento));
/*  561: 633 */     if (facturaProveedor.getTraValorTotalSubida().compareTo(BigDecimal.ZERO) == 1)
/*  562:     */     {
/*  563: 634 */       BigDecimal retencionComercializadora = FuncionesUtiles.porcentaje(facturaProveedor.getTotal(), 0.2D, 2);
/*  564: 635 */       facturaProveedor.setRetencionComercializadora(retencionComercializadora);
/*  565:     */     }
/*  566:     */   }
/*  567:     */   
/*  568:     */   public void generarCuentaPorPagar(FacturaProveedor facturaProveedor)
/*  569:     */   {
/*  570: 649 */     if ((!facturaProveedor.isSoloLectura()) && (facturaProveedor.getNumeroCuotas() >= 0) && (facturaProveedor.getCondicionPago() != null) && 
/*  571: 650 */       (facturaProveedor.getCondicionPago().getId() > 0))
/*  572:     */     {
/*  573: 652 */       int numeroCuota = 0;
/*  574: 653 */       int numeroCuotas = facturaProveedor.getNumeroCuotas();
/*  575:     */       
/*  576: 655 */       BigDecimal totalFactura = facturaProveedor.getTotalFactura();
/*  577: 656 */       totalFactura = totalFactura.add(facturaProveedor.getRetencionComercializadora());
/*  578: 657 */       totalFactura = totalFactura.add(facturaProveedor.getRetencion3X1000());
/*  579: 658 */       totalFactura = totalFactura.add(facturaProveedor.getRetencionIvaPresuntivo());
/*  580: 659 */       totalFactura = totalFactura.subtract(facturaProveedor.getDescuentoImpuesto());
/*  581: 660 */       BigDecimal valorCuota = totalFactura.divide(new BigDecimal(numeroCuotas), 2, RoundingMode.HALF_UP);
/*  582:     */       
/*  583: 662 */       int hasta = Math.max(facturaProveedor.getNumeroCuotas(), facturaProveedor.getListaCuentaPorPagar().size());
/*  584: 663 */       Date fecha = facturaProveedor.getFechaRecepcion() == null ? facturaProveedor.getFecha() : facturaProveedor.getFechaRecepcion();
/*  585: 664 */       for (int i = 0; i < hasta; i++)
/*  586:     */       {
/*  587: 665 */         numeroCuota = i + 1;
/*  588: 667 */         if (numeroCuota > facturaProveedor.getNumeroCuotas())
/*  589:     */         {
/*  590: 668 */           ((CuentaPorPagar)facturaProveedor.getListaCuentaPorPagar().get(i)).setEliminado(true);
/*  591:     */         }
/*  592:     */         else
/*  593:     */         {
/*  594:     */           CuentaPorPagar cuentaPorPagar;
/*  595:     */           CuentaPorPagar cuentaPorPagar;
/*  596: 673 */           if (numeroCuota > facturaProveedor.getListaCuentaPorPagar().size()) {
/*  597: 674 */             cuentaPorPagar = new CuentaPorPagar();
/*  598:     */           } else {
/*  599: 676 */             cuentaPorPagar = (CuentaPorPagar)facturaProveedor.getListaCuentaPorPagar().get(i);
/*  600:     */           }
/*  601: 678 */           cuentaPorPagar.setEliminado(false);
/*  602: 679 */           Date fechaVencimiento = null;
/*  603:     */           
/*  604: 681 */           CondicionPago condicionPago = this.servicioCondicionPago.buscarPorId(Integer.valueOf(facturaProveedor.getCondicionPago().getIdCondicionPago()));
/*  605: 683 */           if (condicionPago.isIndicadorFechaFija())
/*  606:     */           {
/*  607: 684 */             Calendar calFechaVen = Calendar.getInstance();
/*  608: 685 */             calFechaVen.setTime(fecha);
/*  609:     */             
/*  610: 687 */             fechaVencimiento = FuncionesUtiles.getFecha(condicionPago.getDiaVencimiento(), calFechaVen.get(2), calFechaVen
/*  611: 688 */               .get(1));
/*  612:     */           }
/*  613:     */           else
/*  614:     */           {
/*  615: 690 */             fechaVencimiento = FuncionesUtiles.sumarFechaDiasMeses(fecha, condicionPago.getMesesPlazo() * (i + 1), condicionPago
/*  616: 691 */               .getDiasPlazo() * (i + 1));
/*  617:     */           }
/*  618: 694 */           cuentaPorPagar.setFechaVencimiento(fechaVencimiento
/*  619: 695 */             .compareTo(facturaProveedor.getFecha()) < 0 ? facturaProveedor.getFecha() : fechaVencimiento);
/*  620: 696 */           cuentaPorPagar.setIdOrganizacion(facturaProveedor.getIdOrganizacion());
/*  621: 697 */           cuentaPorPagar.setIdSucursal(facturaProveedor.getSucursal().getIdOrganizacion());
/*  622: 698 */           cuentaPorPagar.setNumeroCuota(numeroCuota);
/*  623: 701 */           if (numeroCuota == numeroCuotas)
/*  624:     */           {
/*  625: 702 */             valorCuota = totalFactura;
/*  626: 703 */             facturaProveedor.setFechaVencimiento(fechaVencimiento);
/*  627:     */           }
/*  628: 705 */           cuentaPorPagar.setValor(valorCuota);
/*  629:     */           
/*  630: 707 */           cuentaPorPagar.setFacturaProveedor(facturaProveedor);
/*  631: 709 */           if (numeroCuota > facturaProveedor.getListaCuentaPorPagar().size()) {
/*  632: 710 */             facturaProveedor.getListaCuentaPorPagar().add(cuentaPorPagar);
/*  633:     */           }
/*  634: 713 */           totalFactura = totalFactura.subtract(valorCuota);
/*  635:     */         }
/*  636:     */       }
/*  637:     */     }
/*  638:     */   }
/*  639:     */   
/*  640:     */   public void totalizarImpuesto(FacturaProveedor facturaProveedor)
/*  641:     */     throws ExcepcionAS2Compras
/*  642:     */   {
/*  643: 728 */     BigDecimal totalImpuestoProductoTotal = BigDecimal.ZERO;
/*  644: 729 */     BigDecimal totalDescuentoImpuestoProductoTotal = BigDecimal.ZERO;
/*  645: 730 */     BigDecimal totalImpuestoProducto = BigDecimal.ZERO;
/*  646: 731 */     DetalleFacturaProveedor detalleFacturaProveedor = null;
/*  647: 733 */     for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor())
/*  648:     */     {
/*  649: 734 */       totalizarImpuestoLinea(dfp);
/*  650: 735 */       if (!dfp.isEliminado())
/*  651:     */       {
/*  652: 736 */         totalImpuestoProducto = totalImpuestoProducto.add(dfp.getValorImpuestosLinea());
/*  653: 737 */         totalImpuestoProductoTotal = totalImpuestoProductoTotal.add(dfp.getTraValorImpuestosLineaTotal());
/*  654: 738 */         totalDescuentoImpuestoProductoTotal = totalDescuentoImpuestoProductoTotal.add(dfp.getValorDescuentoImpuestosLinea());
/*  655: 739 */         if (dfp.getValorImpuestosLinea().compareTo(BigDecimal.ZERO) == 1) {
/*  656: 740 */           detalleFacturaProveedor = dfp;
/*  657:     */         }
/*  658:     */       }
/*  659:     */     }
/*  660: 745 */     totalImpuestoProductoTotal = FuncionesUtiles.redondearBigDecimal(totalImpuestoProductoTotal, 2);
/*  661:     */     
/*  662: 747 */     BigDecimal diferencia = totalImpuestoProductoTotal.subtract(totalImpuestoProducto);
/*  663: 749 */     if (diferencia.compareTo(BigDecimal.ZERO) != 0)
/*  664:     */     {
/*  665: 751 */       detalleFacturaProveedor.setValorImpuestosLinea(detalleFacturaProveedor.getValorImpuestosLinea().add(diferencia));
/*  666: 753 */       if (!detalleFacturaProveedor.getProducto().getTipoProducto().equals(TipoProducto.ARTICULO))
/*  667:     */       {
/*  668: 754 */         GastoProductoFacturaProveedor gpfp = (GastoProductoFacturaProveedor)detalleFacturaProveedor.getListaGastoProductoFactura().get(0);
/*  669: 755 */         gpfp.setValor(gpfp.getValor().add(diferencia));
/*  670:     */       }
/*  671:     */     }
/*  672: 760 */     facturaProveedor.setImpuesto(FuncionesUtiles.redondearBigDecimal(totalImpuestoProductoTotal));
/*  673: 761 */     facturaProveedor.setDescuentoImpuesto(FuncionesUtiles.redondearBigDecimal(totalDescuentoImpuestoProductoTotal));
/*  674:     */   }
/*  675:     */   
/*  676:     */   public List<FacturaProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  677:     */   {
/*  678: 768 */     return this.facturaProveedorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  679:     */   }
/*  680:     */   
/*  681:     */   private void totalizarImpuestoLinea(DetalleFacturaProveedor detalleFacturaProveedor)
/*  682:     */   {
/*  683: 772 */     detalleFacturaProveedor.setValorImpuestosLinea(BigDecimal.ZERO);
/*  684: 773 */     detalleFacturaProveedor.setValorDescuentoImpuestosLinea(BigDecimal.ZERO);
/*  685: 774 */     detalleFacturaProveedor.setTraValorImpuestosLineaTotal(BigDecimal.ZERO);
/*  686: 776 */     for (ImpuestoProductoFacturaProveedor ipfp : detalleFacturaProveedor.getListaImpuestoProductoFacturaProveedor()) {
/*  687: 777 */       if (!ipfp.isEliminado())
/*  688:     */       {
/*  689: 779 */         detalleFacturaProveedor.setValorImpuestosLinea(detalleFacturaProveedor.getValorImpuestosLinea().add(ipfp.getImpuestoProducto()));
/*  690: 780 */         detalleFacturaProveedor.setValorDescuentoImpuestosLinea(detalleFacturaProveedor
/*  691: 781 */           .getValorDescuentoImpuestosLinea().add(ipfp.getDescuentoImpuestoProducto()));
/*  692:     */       }
/*  693:     */     }
/*  694: 785 */     detalleFacturaProveedor.setTraValorImpuestosLineaTotal(detalleFacturaProveedor.getValorImpuestosLinea());
/*  695: 786 */     detalleFacturaProveedor.setValorImpuestosLinea(FuncionesUtiles.redondearBigDecimal(detalleFacturaProveedor.getValorImpuestosLinea(), 2));
/*  696:     */   }
/*  697:     */   
/*  698:     */   public List<FacturaProveedor> obtenerListaComboConEqual(String sortField, boolean sortOrder, Map<String, String> filters)
/*  699:     */   {
/*  700: 798 */     return this.facturaProveedorDao.obtenerListaComboConEqual(sortField, sortOrder, filters);
/*  701:     */   }
/*  702:     */   
/*  703:     */   public List<FacturaProveedor> listaFacturasPorRecibir(Estado estado, int idEmpresa)
/*  704:     */   {
/*  705: 809 */     return this.facturaProveedorDao.listaFacturasPorRecibir(estado, idEmpresa);
/*  706:     */   }
/*  707:     */   
/*  708:     */   public List<FacturaProveedor> listaFacturaPorProveedor(Empresa empresa)
/*  709:     */   {
/*  710: 819 */     return this.facturaProveedorDao.listaFacturaPorProveedor(empresa);
/*  711:     */   }
/*  712:     */   
/*  713:     */   public BigDecimal getSumaImpuestoPorIdFacturaProveedor(int idDetalleFacturaProveedor)
/*  714:     */   {
/*  715: 829 */     return this.facturaProveedorDao.getSumaImpuestoPorIdFacturaProveedor(idDetalleFacturaProveedor);
/*  716:     */   }
/*  717:     */   
/*  718:     */   private void validar(FacturaProveedor facturaProveedor, boolean verificaPeriodo)
/*  719:     */     throws ExcepcionAS2Compras, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  720:     */   {
/*  721: 844 */     if ((facturaProveedor.getFacturaProveedorSRI() != null) && (facturaProveedor.getFacturaProveedorSRI().isIndicadorReembolso())) {
/*  722: 845 */       for (ReembolsoGastos rg : facturaProveedor.getFacturaProveedorSRI().getListaReembolsoGastos()) {
/*  723: 846 */         if (!rg.isEliminado()) {
/*  724: 848 */           if (FuncionesUtiles.compararFechas(facturaProveedor.getFacturaProveedorSRI().getFechaEmision(), rg.getFechaEmision())) {
/*  725: 850 */             throw new AS2Exception("msg_error_fecha_fuera_rango", new String[] {" Para el reembolso menor o igual " + FuncionesUtiles.dateToString(facturaProveedor.getFacturaProveedorSRI().getFechaEmision()) });
/*  726:     */           }
/*  727:     */         }
/*  728:     */       }
/*  729:     */     }
/*  730: 858 */     if ((facturaProveedor.getDocumento().isIndicadorDocumentoTributario()) && 
/*  731: 859 */       (facturaProveedor.getFacturaProveedorSRI().getNumero().equals("0"))) {
/*  732: 860 */       throw new ExcepcionAS2Compras("msg_error_numero_factura");
/*  733:     */     }
/*  734: 865 */     if ((facturaProveedor.getEmpresa().getProveedor().getValorMaximoDocumento().compareTo(BigDecimal.ZERO) > 0) && 
/*  735: 866 */       (facturaProveedor.getTotalFactura().compareTo(facturaProveedor.getEmpresa().getProveedor().getValorMaximoDocumento()) > 0)) {
/*  736: 868 */       throw new ExcepcionAS2("msg_error_excedio_monto_maximo_compra", " " + facturaProveedor.getTotalFactura().toString() + ">" + facturaProveedor.getEmpresa().getProveedor().getValorMaximoDocumento().toString());
/*  737:     */     }
/*  738: 873 */     if (facturaProveedor.isIndicadorGastoImportacion())
/*  739:     */     {
/*  740: 874 */       facturaProveedor.setIndicadorGastoImportacion(false);
/*  741: 875 */       for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor()) {
/*  742: 877 */         if ((!dfp.isEliminado()) && (dfp.isIndicadorGastoImportacion()))
/*  743:     */         {
/*  744: 878 */           facturaProveedor.setIndicadorGastoImportacion(true);
/*  745: 879 */           BigDecimal valor = dfp.getBaseImponible();
/*  746: 880 */           if (!dfp.getFacturaProveedor().isIndicadorCreditoTributario()) {
/*  747: 881 */             valor = valor.add(dfp.getValorImpuestosLinea());
/*  748:     */           }
/*  749: 883 */           BigDecimal distribuido = BigDecimal.ZERO;
/*  750: 884 */           for (DetalleFacturaProveedorImportacion dfpi : dfp.getListaDetalleFacturaProveedorImportacion())
/*  751:     */           {
/*  752: 885 */             if (!dfpi.isEliminado())
/*  753:     */             {
/*  754: 886 */               if (dfpi.getFacturaProveedor() == null) {
/*  755: 887 */                 throw new ExcepcionAS2Compras("msg_error_seleccionar_importacion", " " + dfp.getProducto().getNombre());
/*  756:     */               }
/*  757: 889 */               distribuido = distribuido.add(dfpi.getValor());
/*  758:     */             }
/*  759: 892 */             if (dfpi.getListaDetalleFacturaProveedorImportacionProducto().size() > 0)
/*  760:     */             {
/*  761: 894 */               BigDecimal valorDistribucionManual = distribucionManual(dfpi.getListaDetalleFacturaProveedorImportacionProducto());
/*  762: 897 */               if ((valorDistribucionManual.compareTo(BigDecimal.ZERO) != 0) && (valorDistribucionManual.compareTo(dfpi.getValor()) != 0)) {
/*  763: 899 */                 throw new ExcepcionAS2Compras("msg_error_distribucion_gasto_importacion_manual", " " + dfp.getProducto().getNombre());
/*  764:     */               }
/*  765: 902 */               if ((valorDistribucionManual.compareTo(BigDecimal.ZERO) != 0) && (valorDistribucionManual.compareTo(dfpi.getValor()) == 0)) {
/*  766: 903 */                 dfpi.setIndicadorDistribucionManual(true);
/*  767:     */               }
/*  768: 906 */               if (valorDistribucionManual.compareTo(BigDecimal.ZERO) == 0) {
/*  769: 907 */                 dfpi.setIndicadorDistribucionManual(false);
/*  770:     */               }
/*  771:     */             }
/*  772:     */           }
/*  773: 913 */           if (valor.compareTo(distribuido) != 0) {
/*  774: 914 */             throw new ExcepcionAS2Compras("msg_error_distribucion_gasto_importacion", " " + dfp.getProducto().getNombre());
/*  775:     */           }
/*  776:     */         }
/*  777:     */       }
/*  778:     */     }
/*  779: 920 */     if (verificaPeriodo)
/*  780:     */     {
/*  781: 923 */       Date fecha = (facturaProveedor.getDocumento().isIndicadorDocumentoExterior()) && (facturaProveedor.getRecepcionProveedor() != null) ? facturaProveedor.getRecepcionProveedor().getFecha() : facturaProveedor.getFecha();
/*  782: 924 */       this.servicioPeriodo.buscarPorFecha(fecha, facturaProveedor.getIdOrganizacion(), facturaProveedor.getDocumento().getDocumentoBase());
/*  783:     */     }
/*  784: 927 */     if (verificaExistenciaNumero(facturaProveedor) > 0L)
/*  785:     */     {
/*  786: 928 */       cargarSecuencia(facturaProveedor, null);
/*  787: 929 */       throw new ExcepcionAS2Compras("msg_info_numero_duplicado_numero_nuevo", " " + facturaProveedor.getNumero());
/*  788:     */     }
/*  789:     */     Date fechaEmisionFactura;
/*  790: 932 */     if (facturaProveedor.getDocumento().getDocumentoBase() == DocumentoBase.FACTURA_PROVEEDOR)
/*  791:     */     {
/*  792: 934 */       if (facturaProveedor.getFacturaProveedorSRI() != null)
/*  793:     */       {
/*  794: 935 */         if (facturaProveedor.getFacturaProveedorSRI().getAutorizacionProveedorSRI() != null)
/*  795:     */         {
/*  796: 939 */           Date fechaCaducidadAutorizacion = FuncionesUtiles.setAtributoFecha(facturaProveedor.getFacturaProveedorSRI().getAutorizacionProveedorSRI().getFechaHasta());
/*  797: 940 */           fechaEmisionFactura = FuncionesUtiles.setAtributoFecha(facturaProveedor.getFacturaProveedorSRI().getFechaEmision());
/*  798: 941 */           Date fechaEmisionRegistro = FuncionesUtiles.setAtributoFecha(facturaProveedor.getFecha());
/*  799: 943 */           if (fechaEmisionFactura.after(fechaCaducidadAutorizacion)) {
/*  800: 946 */             throw new ExcepcionAS2Compras("msgs_error_autorizacion_factura_caducada", " (" + FuncionesUtiles.dateToString(fechaEmisionFactura) + " > " + FuncionesUtiles.dateToString(fechaCaducidadAutorizacion) + ")");
/*  801:     */           }
/*  802: 949 */           if (fechaEmisionRegistro.before(fechaEmisionFactura)) {
/*  803: 950 */             throw new ExcepcionAS2Financiero("msg_error_fecha_registro_factura");
/*  804:     */           }
/*  805:     */         }
/*  806: 956 */         if ((facturaProveedor.getFacturaProveedorSRI() != null) && 
/*  807: 957 */           (this.servicioFacturaProveedorSRI.existeFactura(facturaProveedor.getFacturaProveedorSRI()))) {
/*  808: 959 */           throw new ExcepcionAS2Compras("msgs_mensaje_error_numero_factura_sri_duplicado", " (" + facturaProveedor.getFacturaProveedorSRI().getTipoComprobanteSRI().getNombre() + ")");
/*  809:     */         }
/*  810:     */       }
/*  811: 966 */       BigDecimal totalFactura = facturaProveedor.getTotalFactura();
/*  812: 967 */       totalFactura = totalFactura.add(facturaProveedor.getRetencionComercializadora());
/*  813: 968 */       totalFactura = totalFactura.add(facturaProveedor.getRetencion3X1000());
/*  814: 969 */       totalFactura = totalFactura.add(facturaProveedor.getRetencionIvaPresuntivo());
/*  815: 970 */       totalFactura = totalFactura.subtract(facturaProveedor.getDescuentoImpuesto());
/*  816: 971 */       if ((facturaProveedor.getTotalCuentaPorPagar().compareTo(totalFactura) != 0) && (facturaProveedor.getFacturaProveedorImportacion() == null)) {
/*  817: 972 */         throw new ExcepcionAS2Compras("msg_error_diferencia_forma_pago");
/*  818:     */       }
/*  819: 977 */       if (facturaProveedor.getBono().compareTo(facturaProveedor.getTotalCuentaPorPagar()) > 0) {
/*  820: 978 */         throw new ExcepcionAS2Compras("msg_error_guardar");
/*  821:     */       }
/*  822: 981 */       if ((!facturaProveedor.isIndicadorSaldoInicial()) && (!facturaProveedor.isIndicadorGastoImportacion())) {
/*  823: 982 */         for (DetalleFacturaProveedor detalleFactura : facturaProveedor.getListaDetalleFacturaProveedor()) {
/*  824: 985 */           if ((!detalleFactura.isEliminado()) && (detalleFactura.getProducto().isTraIndicadorServicio()))
/*  825:     */           {
/*  826: 987 */             BigDecimal valorLinea = detalleFactura.getBaseImponible().add(detalleFactura.getValorImpuestosLinea());
/*  827: 989 */             if ((valorLinea.compareTo(detalleFactura.getValorGastoContable()) != 0) && 
/*  828: 990 */               (detalleFactura.getListaDetalleRecepcionProveedor().isEmpty()))
/*  829:     */             {
/*  830: 992 */               String mensaje = "";
/*  831: 993 */               if (facturaProveedor.getFacturaProveedorSRI() != null) {
/*  832: 996 */                 mensaje = " F:/" + facturaProveedor.getFacturaProveedorSRI().getEstablecimiento() + "-" + facturaProveedor.getFacturaProveedorSRI().getPuntoEmision() + "-" + facturaProveedor.getFacturaProveedorSRI().getNumero() + " ";
/*  833:     */               }
/*  834: 999 */               mensaje = mensaje + detalleFactura.getProducto().getNombre().trim() + " " + detalleFactura.getProducto().getNombre() + " " + valorLinea + "<>" + detalleFactura.getValorGastoContable();
/*  835:     */               
/*  836:1001 */               throw new ExcepcionAS2Compras("msg_error_diferencia_gasto_servicio", mensaje);
/*  837:     */             }
/*  838:     */           }
/*  839:     */         }
/*  840:     */       }
/*  841:     */     }
/*  842:     */   }
/*  843:     */   
/*  844:     */   public BigDecimal distribucionManual(List<DetalleFacturaProveedorImportacionProducto> listaDFPIP)
/*  845:     */   {
/*  846:1013 */     BigDecimal distribucionManual = BigDecimal.ZERO;
/*  847:1014 */     for (DetalleFacturaProveedorImportacionProducto dfpip : listaDFPIP) {
/*  848:1016 */       if (dfpip.getValor().compareTo(BigDecimal.ZERO) != 0) {
/*  849:1017 */         distribucionManual = distribucionManual.add(dfpip.getValor());
/*  850:     */       }
/*  851:     */     }
/*  852:1020 */     return distribucionManual;
/*  853:     */   }
/*  854:     */   
/*  855:     */   public void contabilizar(FacturaProveedor facturaProveedor)
/*  856:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  857:     */   {
/*  858:1030 */     if ((!facturaProveedor.isIndicadorSaldoInicial()) && (facturaProveedor.getFacturaProveedorImportacion() == null) && 
/*  859:1031 */       (facturaProveedor.getDocumento().isIndicadorContabilizar()))
/*  860:     */     {
/*  861:1033 */       Date fechaContabilizacion = facturaProveedor.getFecha();
/*  862:1034 */       int idFacturaProveedor = facturaProveedor.getIdFacturaProveedor();
/*  863:1036 */       for (Iterator localIterator1 = facturaProveedor.getListaDetalleFacturaProveedor().iterator(); localIterator1.hasNext();)
/*  864:     */       {
/*  865:1036 */         detalleFacturaProveedor = (DetalleFacturaProveedor)localIterator1.next();
/*  866:1038 */         if (detalleFacturaProveedor.getProducto().getTipoProducto() == TipoProducto.ARTICULO) {
/*  867:1040 */           facturaProveedor.setTraImpuestoBienes(facturaProveedor.getTraImpuestoBienes().add(detalleFacturaProveedor.getValorImpuestosLinea()));
/*  868:     */         }
/*  869:     */       }
/*  870:     */       DetalleFacturaProveedor detalleFacturaProveedor;
/*  871:1044 */       facturaProveedor.setTraImpuestoBienes(facturaProveedor.getTraImpuestoBienes().setScale(2, RoundingMode.HALF_UP));
/*  872:     */       Asiento asiento;
/*  873:1046 */       if (facturaProveedor.getAsiento() != null)
/*  874:     */       {
/*  875:1047 */         Asiento asiento = facturaProveedor.getAsiento();
/*  876:1048 */         asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/*  877:1049 */         for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/*  878:1050 */           detalleAsiento.setEliminado(true);
/*  879:     */         }
/*  880:     */       }
/*  881:     */       else
/*  882:     */       {
/*  883:1053 */         asiento = new Asiento();
/*  884:1054 */         asiento.setIdOrganizacion(facturaProveedor.getIdOrganizacion());
/*  885:1055 */         asiento.setSucursal(facturaProveedor.getSucursal());
/*  886:     */         
/*  887:1057 */         TipoAsiento tipoAsiento = facturaProveedor.getDocumento().getTipoAsiento();
/*  888:1058 */         asiento.setTipoAsiento(tipoAsiento);
/*  889:     */       }
/*  890:1062 */       String concepto = "";
/*  891:     */       
/*  892:1064 */       concepto = facturaProveedor.getDocumento().getNombre().trim() + " #" + facturaProveedor.getNumero() + " " + facturaProveedor.getDescripcion();
/*  893:1065 */       asiento.setConcepto2(facturaProveedor.getDescripcion2());
/*  894:1066 */       asiento.setConcepto(concepto);
/*  895:1067 */       asiento.setFecha(fechaContabilizacion);
/*  896:     */       
/*  897:1069 */       List<DetalleInterfazContable> listaDA = new ArrayList();
/*  898:1072 */       if (facturaProveedor.getBono().compareTo(BigDecimal.ZERO) != 0) {
/*  899:1073 */         listaDA.addAll(this.facturaProveedorDao.getBonoIC(idFacturaProveedor));
/*  900:     */       }
/*  901:1077 */       if (facturaProveedor.getRetencionComercializadora().compareTo(BigDecimal.ZERO) != 0) {
/*  902:1078 */         listaDA.addAll(this.facturaProveedorDao.getRetencionComercializadora(facturaProveedor.getId()));
/*  903:     */       }
/*  904:1082 */       if (facturaProveedor.getRetencion3X1000().compareTo(BigDecimal.ZERO) != 0) {
/*  905:1083 */         listaDA.addAll(this.facturaProveedorDao.getRetencion3X1000(facturaProveedor.getId()));
/*  906:     */       }
/*  907:1087 */       if (facturaProveedor.getRetencionIvaPresuntivo().compareTo(BigDecimal.ZERO) != 0) {
/*  908:1088 */         listaDA.addAll(this.facturaProveedorDao.getRetencionIvaPresuntivo(facturaProveedor.getId()));
/*  909:     */       }
/*  910:1093 */       listaDA.addAll(this.facturaProveedorDao.getCuentaImportacionFPGIC(idFacturaProveedor));
/*  911:     */       
/*  912:1095 */       listaDA.addAll(this.facturaProveedorDao.getGastoServiciosIC(idFacturaProveedor));
/*  913:     */       
/*  914:1097 */       super.generarAsiento(asiento, listaDA, facturaProveedor.getDocumento());
/*  915:1098 */       List<Integer> list = new ArrayList();
/*  916:1099 */       list = new ArrayList();
/*  917:1100 */       list.add(Integer.valueOf(facturaProveedor.getIdFacturaProveedor()));
/*  918:1101 */       List<DetalleInterfazContableProceso> lista = new ArrayList();
/*  919:1102 */       List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/*  920:     */       
/*  921:1104 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(facturaProveedor.getIdOrganizacion(), DocumentoBase.FACTURA_PROVEEDOR);
/*  922:     */       
/*  923:     */ 
/*  924:1107 */       List<DocumentoContabilizacion> listDocs = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(facturaProveedor.getIdOrganizacion(), DocumentoBase.FACTURA_PROVEEDOR);
/*  925:1108 */       listDocs.addAll(this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(facturaProveedor.getIdOrganizacion(), DocumentoBase.RECEPCION_BODEGA, ProcesoContabilizacionEnum.PROVISION_GASTOS));
/*  926:1110 */       for (DocumentoContabilizacion documentoContabilizacion : listDocs)
/*  927:     */       {
/*  928:1111 */         if (ProcesoContabilizacionEnum.PROVISION_GASTOS.equals(documentoContabilizacion.getProcesoContabilizacion()))
/*  929:     */         {
/*  930:1112 */           this.documentoContabilizacionDao.detach(documentoContabilizacion);
/*  931:1113 */           documentoContabilizacion.setDebe(!documentoContabilizacion.isDebe());
/*  932:     */         }
/*  933:1116 */         lista = this.facturaProveedorDao.getInterfazFacturaProveedorDimensiones(list, documentoContabilizacion.getProcesoContabilizacion());
/*  934:1118 */         for (DetalleInterfazContableProceso dicp : lista) {
/*  935:1119 */           if (dicp.getImpuestosComercializadora().compareTo(BigDecimal.ZERO) > 0)
/*  936:     */           {
/*  937:1122 */             dicp.setValor(dicp.getValor().add(dicp.getImpuestosComercializadora()));
/*  938:1123 */             break;
/*  939:     */           }
/*  940:     */         }
/*  941:1127 */         listaDetalleAsiento.addAll(this.servicioInterfazContableProceso
/*  942:1128 */           .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/*  943:     */       }
/*  944:1132 */       asiento.getListaDetalleAsiento().addAll(listaDetalleAsiento);
/*  945:     */       
/*  946:     */ 
/*  947:1135 */       this.servicioAsiento.guardar(this.servicioInterfazContableProceso.ajustarDiferencias(asiento));
/*  948:     */       
/*  949:     */ 
/*  950:     */ 
/*  951:1139 */       facturaProveedor.setEstado(Estado.CONTABILIZADO);
/*  952:     */       
/*  953:1141 */       facturaProveedor.setFechaContabilizacion(fechaContabilizacion);
/*  954:     */       
/*  955:1143 */       facturaProveedor.setAsiento(asiento);
/*  956:     */     }
/*  957:     */   }
/*  958:     */   
/*  959:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  960:     */   public void anularFacturaProveedorImportacion(FacturaProveedor fp)
/*  961:     */     throws ExcepcionAS2, ExcepcionAS2Compras, ExcepcionAS2Financiero
/*  962:     */   {
/*  963:1157 */     FacturaProveedor facturaProveedor = cargarDetalle(Integer.valueOf(fp.getId()));
/*  964:1158 */     this.servicioPeriodo.buscarPorFecha(facturaProveedor.getFecha(), facturaProveedor.getIdOrganizacion(), fp.getDocumento().getDocumentoBase());
/*  965:1159 */     if (!facturaProveedor.getEstado().equals(Estado.ELABORADO)) {
/*  966:1160 */       throw new ExcepcionAS2Compras("msg_error_editar");
/*  967:     */     }
/*  968:1163 */     for (DetalleFacturaProveedorImportacion dfpi : facturaProveedor.getListaDetalleFacturaProveedorImportacion()) {
/*  969:1164 */       if (dfpi.getValor().compareTo(BigDecimal.ZERO) > 0) {
/*  970:1165 */         throw new ExcepcionAS2Compras("msg_error_registro_gasto_importacion");
/*  971:     */       }
/*  972:     */     }
/*  973:1169 */     if (facturaProveedor.getRecepcionProveedor() != null) {
/*  974:1170 */       throw new ExcepcionAS2Compras("msg_error_editar");
/*  975:     */     }
/*  976:1173 */     Object dfpi = this.facturaProveedorDao.verificarFacturaProveedor(fp.getId());
/*  977:1174 */     if (((List)dfpi).size() > 0) {
/*  978:1175 */       throw new ExcepcionAS2Compras("msg_info_atada_a_factura_cliente");
/*  979:     */     }
/*  980:1178 */     actualizarEstado(Integer.valueOf(fp.getId()), Estado.ANULADO);
/*  981:     */     
/*  982:1180 */     facturaProveedor.setEstado(Estado.ANULADO);
/*  983:1181 */     this.servicioRegistroPeso.actualizarLiquidacion(facturaProveedor);
/*  984:     */   }
/*  985:     */   
/*  986:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  987:     */   public void anular(FacturaProveedor fp)
/*  988:     */     throws ExcepcionAS2, ExcepcionAS2Compras, ExcepcionAS2Financiero
/*  989:     */   {
/*  990:     */     try
/*  991:     */     {
/*  992:1195 */       FacturaProveedor facturaProveedor = cargarDetalle(Integer.valueOf(fp.getId()));
/*  993:     */       
/*  994:1197 */       esEditable(facturaProveedor);
/*  995:1200 */       if ((facturaProveedor.getFacturaProveedorSRI() != null) && (facturaProveedor.getFacturaProveedorSRI().isIndicadorRetencionAsumida())) {
/*  996:1201 */         throw new ExcepcionAS2Compras("msgs_error_existe_retencion_factura");
/*  997:     */       }
/*  998:1205 */       for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor()) {
/*  999:1206 */         for (DetalleFacturaProveedorImportacion dfpi : dfp.getListaDetalleFacturaProveedorImportacion()) {
/* 1000:1207 */           if ((dfpi.getFacturaProveedor() != null) && (dfpi.getFacturaProveedor().getEstado().equals(Estado.CERRADO))) {
/* 1001:1208 */             throw new ExcepcionAS2("msg_error_factura_proveedor_importacion_cerrado");
/* 1002:     */           }
/* 1003:     */         }
/* 1004:     */       }
/* 1005:1213 */       if (facturaProveedor.isSoloLectura()) {
/* 1006:1214 */         throw new ExcepcionAS2("msg_error_existen_pagos_para_factura");
/* 1007:     */       }
/* 1008:1217 */       if (facturaProveedor.getAsiento() != null)
/* 1009:     */       {
/* 1010:1218 */         facturaProveedor.getAsiento().setIndicadorAutomatico(false);
/* 1011:1219 */         this.servicioAsiento.anular(facturaProveedor.getAsiento());
/* 1012:     */       }
/* 1013:1221 */       if (facturaProveedor.getFacturaProveedorSRI() != null)
/* 1014:     */       {
/* 1015:1227 */         this.comprobanteElectronicoPendienteSRIDao.eliminarComprobanteElectronicoPendienteSRI(null, facturaProveedor.getFacturaProveedorSRI(), null);
/* 1016:     */         
/* 1017:     */ 
/* 1018:1230 */         this.facturaProveedorSRIDao.eliminarFacturaProveedorSRI(Integer.valueOf(facturaProveedor.getFacturaProveedorSRI().getId()));
/* 1019:     */       }
/* 1020:1234 */       actualizarEstado(Integer.valueOf(facturaProveedor.getId()), Estado.ANULADO);
/* 1021:1235 */       this.servicioVerificadorCompras.actualizarCantidadPorFacturar(facturaProveedor, true);
/* 1022:     */       
/* 1023:1237 */       liberarRecepciones(facturaProveedor);
/* 1024:     */       
/* 1025:     */ 
/* 1026:1240 */       facturaProveedor.setEstado(Estado.ANULADO);
/* 1027:1241 */       this.servicioRegistroPeso.actualizarLiquidacion(facturaProveedor);
/* 1028:1244 */       for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor()) {
/* 1029:1245 */         this.facturaProveedorDao.eliminarDetalleFacturaProveedorImportacion(dfp);
/* 1030:     */       }
/* 1031:     */     }
/* 1032:     */     catch (ExcepcionAS2Compras e)
/* 1033:     */     {
/* 1034:1249 */       this.context.setRollbackOnly();
/* 1035:1250 */       e.printStackTrace();
/* 1036:1251 */       throw e;
/* 1037:     */     }
/* 1038:     */     catch (ExcepcionAS2Financiero e)
/* 1039:     */     {
/* 1040:1253 */       this.context.setRollbackOnly();
/* 1041:1254 */       e.printStackTrace();
/* 1042:1255 */       throw e;
/* 1043:     */     }
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public void actualizarEstado(Integer idFacturaProveedor, Estado estado)
/* 1047:     */   {
/* 1048:1266 */     this.facturaProveedorDao.actualizarEstado(idFacturaProveedor, estado);
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public void esEditable(FacturaProveedor facturaProveedor)
/* 1052:     */     throws ExcepcionAS2Compras, ExcepcionAS2Financiero
/* 1053:     */   {
/* 1054:1277 */     this.servicioPeriodo.buscarPorFecha(facturaProveedor.getFecha(), facturaProveedor.getIdOrganizacion(), facturaProveedor
/* 1055:1278 */       .getDocumento().getDocumentoBase());
/* 1056:1280 */     if ((facturaProveedor.getFacturaProveedorPadre() != null) && (facturaProveedor.getFacturaProveedorPadre().getEstado() == Estado.CERRADO)) {
/* 1057:1281 */       throw new ExcepcionAS2Compras("msg_error_factura_proveedor_importacion_cerrado");
/* 1058:     */     }
/* 1059:1284 */     if (facturaProveedor.getEstado() == Estado.ANULADO) {
/* 1060:1286 */       throw new ExcepcionAS2Compras("msg_error_editar");
/* 1061:     */     }
/* 1062:1288 */     if ((facturaProveedor.getAsiento() != null) && (facturaProveedor.getAsiento().getEstado() == Estado.REVISADO)) {
/* 1063:1290 */       throw new ExcepcionAS2Compras("msg_info_anular_estado_asiento");
/* 1064:     */     }
/* 1065:1295 */     List<CuentaPorPagar> listaCuentaPorPagar = this.facturaProveedorDao.obtenerCuentaPorPagar(facturaProveedor.getId());
/* 1066:1296 */     BigDecimal bono = facturaProveedor.getBono();
/* 1067:1297 */     BigDecimal valorCuentaPorPagar = BigDecimal.ZERO;
/* 1068:1298 */     BigDecimal saldoCuentaPorPagar = BigDecimal.ZERO;
/* 1069:1300 */     for (CuentaPorPagar cuentaPorPagar : listaCuentaPorPagar)
/* 1070:     */     {
/* 1071:1301 */       if (cuentaPorPagar.isIndicadorBloqueada())
/* 1072:     */       {
/* 1073:1302 */         facturaProveedor.setSoloLectura(true);
/* 1074:1303 */         break;
/* 1075:     */       }
/* 1076:1305 */       valorCuentaPorPagar = valorCuentaPorPagar.add(cuentaPorPagar.getValor());
/* 1077:1306 */       saldoCuentaPorPagar = saldoCuentaPorPagar.add(cuentaPorPagar.getSaldo());
/* 1078:     */     }
/* 1079:1309 */     if (saldoCuentaPorPagar.add(bono).compareTo(valorCuentaPorPagar) != 0) {
/* 1080:1310 */       facturaProveedor.setSoloLectura(true);
/* 1081:     */     }
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public List<CuentaPorPagar> obtenerFacturasPendientes(int idOrganizacion, int idEmpresa, int idFacturaProveedor, Date fechaVencimiento, TipoServicioCuentaBancariaEnum tipoServicio, CategoriaEmpresa categoriaEmpresa)
/* 1085:     */   {
/* 1086:1323 */     return this.facturaProveedorDao.obtenerFacturasPendientes(idOrganizacion, idEmpresa, idFacturaProveedor, fechaVencimiento, tipoServicio, categoriaEmpresa, 0);
/* 1087:     */   }
/* 1088:     */   
/* 1089:     */   public List<CuentaPorPagar> obtenerFacturasPendientes(int idOrganizacion, int idEmpresa, int idFacturaProveedor, Date fechaVencimiento)
/* 1090:     */   {
/* 1091:1329 */     return this.facturaProveedorDao.obtenerFacturasPendientes(idOrganizacion, idEmpresa, idFacturaProveedor, fechaVencimiento, null, null, 0);
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   public List<CuentaPorPagar> obtenerFacturasPendientes(int idOrganizacion, int idEmpresa, int idFacturaProveedor, Date fechaVencimiento, int idSucursal)
/* 1095:     */   {
/* 1096:1336 */     return this.facturaProveedorDao.obtenerFacturasPendientes(idOrganizacion, idEmpresa, idFacturaProveedor, fechaVencimiento, null, null, idSucursal);
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   public List<CuentaPorPagar> obtenerFacturasPendientes(int idOrganizacion, int idEmpresa)
/* 1100:     */   {
/* 1101:1347 */     return obtenerFacturasPendientes(idOrganizacion, idEmpresa, 0, null);
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   public List<CuentaPorPagar> obtenerFacturasPendientes(int idOrganizacion, Date fechaVencimiento, TipoServicioCuentaBancariaEnum tipoServicio, CategoriaEmpresa categoriaEmpresa)
/* 1105:     */   {
/* 1106:1358 */     return obtenerFacturasPendientes(idOrganizacion, 0, 0, fechaVencimiento, tipoServicio, categoriaEmpresa);
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public FacturaProveedor copiarFacturaProveedor(FacturaProveedor fp)
/* 1110:     */     throws ExcepcionAS2
/* 1111:     */   {
/* 1112:1363 */     return copiarFacturaProveedor(fp, null);
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   public FacturaProveedor copiarFacturaProveedor(FacturaProveedor fp, FacturaProveedor facturaProveedor)
/* 1116:     */     throws ExcepcionAS2
/* 1117:     */   {
/* 1118:1373 */     boolean addDetalleFacturaProveedorPadre = true;
/* 1119:1375 */     if (facturaProveedor == null)
/* 1120:     */     {
/* 1121:1376 */       addDetalleFacturaProveedorPadre = false;
/* 1122:1377 */       facturaProveedor = new FacturaProveedor();
/* 1123:1378 */       facturaProveedor.setFecha(fp.getFecha());
/* 1124:1379 */       facturaProveedor.setDocumento(fp.getDocumento());
/* 1125:1380 */       facturaProveedor.setIndicadorSaldoInicial(fp.isIndicadorSaldoInicial());
/* 1126:1381 */       facturaProveedor.setDescuento(fp.getDescuento());
/* 1127:1382 */       facturaProveedor.setDescripcion(fp.getDescripcion());
/* 1128:1383 */       facturaProveedor.setNumeroCuotas(fp.getNumeroCuotas());
/* 1129:1384 */       facturaProveedor.setCondicionPago(fp.getCondicionPago());
/* 1130:1385 */       facturaProveedor.setIdOrganizacion(fp.getIdOrganizacion());
/* 1131:1386 */       facturaProveedor.setSucursal(fp.getSucursal());
/* 1132:1387 */       facturaProveedor.setEmpresa(fp.getEmpresa());
/* 1133:1388 */       facturaProveedor.setEstado(Estado.ELABORADO);
/* 1134:1389 */       facturaProveedor.setIndicadorCreditoTributario(fp.isIndicadorCreditoTributario());
/* 1135:     */       
/* 1136:     */ 
/* 1137:1392 */       cargarSecuencia(facturaProveedor, null);
/* 1138:     */     }
/* 1139:1395 */     facturaProveedor.setDireccionEmpresa(fp.getDireccionEmpresa());
/* 1140:1396 */     facturaProveedor.setDireccionFactura(fp.getDireccionFactura());
/* 1141:1397 */     facturaProveedor.setImpuesto(fp.getImpuesto());
/* 1142:1398 */     facturaProveedor.setTotal(fp.getTotal());
/* 1143:1400 */     if (!fp.getListaDetalleFacturaProveedor().isEmpty()) {
/* 1144:1402 */       for (DetalleFacturaProveedor dfp : fp.getListaDetalleFacturaProveedor())
/* 1145:     */       {
/* 1146:1404 */         detalleFacturaProveedor = new DetalleFacturaProveedor();
/* 1147:     */         
/* 1148:1406 */         detalleFacturaProveedor.setIdOrganizacion(dfp.getIdOrganizacion());
/* 1149:1407 */         detalleFacturaProveedor.setIdSucursal(dfp.getIdSucursal());
/* 1150:1408 */         detalleFacturaProveedor.setFacturaProveedor(facturaProveedor);
/* 1151:1409 */         detalleFacturaProveedor.setCantidad(dfp.getCantidad());
/* 1152:1410 */         detalleFacturaProveedor.setDescuento(dfp.getDescuento());
/* 1153:1411 */         detalleFacturaProveedor.setDescripcion(dfp.getDescripcion());
/* 1154:1412 */         detalleFacturaProveedor.setPrecio(dfp.getPrecio());
/* 1155:1413 */         detalleFacturaProveedor.setUnidadCompra(dfp.getUnidadCompra());
/* 1156:1414 */         detalleFacturaProveedor.setProducto(dfp.getProducto());
/* 1157:1415 */         detalleFacturaProveedor.setCantidad(dfp.getCantidad());
/* 1158:1416 */         detalleFacturaProveedor.setValorImpuestosLinea(dfp.getValorImpuestosLinea());
/* 1159:1417 */         detalleFacturaProveedor.setIndicadorImpuestos(dfp.isIndicadorImpuestos());
/* 1160:1418 */         detalleFacturaProveedor.setPorcentajeDescuento(dfp.getPorcentajeDescuento());
/* 1161:1419 */         detalleFacturaProveedor.setDescuento(dfp.getDescuento());
/* 1162:1420 */         detalleFacturaProveedor.setValorDescuentoImpuestosLinea(dfp.getValorDescuentoImpuestosLinea());
/* 1163:1422 */         if (addDetalleFacturaProveedorPadre) {
/* 1164:1423 */           detalleFacturaProveedor.setDetalleFacturaProveedorPadre(dfp);
/* 1165:     */         }
/* 1166:1426 */         facturaProveedor.getListaDetalleFacturaProveedor().add(detalleFacturaProveedor);
/* 1167:1428 */         if (!dfp.getListaImpuestoProductoFacturaProveedor().isEmpty()) {
/* 1168:1430 */           for (ImpuestoProductoFacturaProveedor ifp : dfp.getListaImpuestoProductoFacturaProveedor())
/* 1169:     */           {
/* 1170:1432 */             ImpuestoProductoFacturaProveedor impuestoProductoFacturaProveedor = new ImpuestoProductoFacturaProveedor();
/* 1171:1433 */             impuestoProductoFacturaProveedor.setPorcentajeImpuesto(ifp.getPorcentajeImpuesto());
/* 1172:1434 */             impuestoProductoFacturaProveedor.setPorcentajeDescuentoImpuesto(ifp.getPorcentajeDescuentoImpuesto());
/* 1173:1435 */             impuestoProductoFacturaProveedor.setImpuesto(ifp.getImpuesto());
/* 1174:1436 */             impuestoProductoFacturaProveedor.setDetalleFacturaProveedor(detalleFacturaProveedor);
/* 1175:1437 */             detalleFacturaProveedor.getListaImpuestoProductoFacturaProveedor().add(impuestoProductoFacturaProveedor);
/* 1176:     */           }
/* 1177:     */         }
/* 1178:1441 */         if (!dfp.getListaGastoProductoFacturaProveedor().isEmpty()) {
/* 1179:1443 */           for (GastoProductoFacturaProveedor gpfp : dfp.getListaGastoProductoFacturaProveedor())
/* 1180:     */           {
/* 1181:1445 */             GastoProductoFacturaProveedor gastoProductoFacturaProveedor = new GastoProductoFacturaProveedor();
/* 1182:1446 */             gastoProductoFacturaProveedor.setValor(gpfp.getValor());
/* 1183:1447 */             gastoProductoFacturaProveedor.setCuentaContableGasto(gpfp.getCuentaContableGasto());
/* 1184:1448 */             gastoProductoFacturaProveedor.setDetalleFacturaProveedor(detalleFacturaProveedor);
/* 1185:1449 */             gastoProductoFacturaProveedor.setDimensionContable1(gpfp.getDimensionContable1());
/* 1186:1450 */             gastoProductoFacturaProveedor.setDimensionContable2(gpfp.getDimensionContable2());
/* 1187:1451 */             gastoProductoFacturaProveedor.setDimensionContable3(gpfp.getDimensionContable3());
/* 1188:1452 */             gastoProductoFacturaProveedor.setDimensionContable4(gpfp.getDimensionContable4());
/* 1189:1453 */             gastoProductoFacturaProveedor.setDimensionContable5(gpfp.getDimensionContable5());
/* 1190:1454 */             detalleFacturaProveedor.getListaGastoProductoFacturaProveedor().add(gastoProductoFacturaProveedor);
/* 1191:     */           }
/* 1192:     */         }
/* 1193:     */       }
/* 1194:     */     }
/* 1195:     */     DetalleFacturaProveedor detalleFacturaProveedor;
/* 1196:1463 */     if (!fp.getListaCuentaPorPagar().isEmpty()) {
/* 1197:1464 */       for (CuentaPorPagar cpp : fp.getListaCuentaPorPagar())
/* 1198:     */       {
/* 1199:1465 */         CuentaPorPagar cuentaPorPagar = new CuentaPorPagar();
/* 1200:1466 */         cuentaPorPagar.setIdOrganizacion(cpp.getIdOrganizacion());
/* 1201:1467 */         cuentaPorPagar.setIdSucursal(cpp.getIdSucursal());
/* 1202:1468 */         cuentaPorPagar.setFacturaProveedor(facturaProveedor);
/* 1203:1469 */         cuentaPorPagar.setFechaVencimiento(cpp.getFechaVencimiento());
/* 1204:1470 */         cuentaPorPagar.setNumeroCuota(cpp.getNumeroCuota());
/* 1205:1471 */         cuentaPorPagar.setSaldo(cpp.getValor());
/* 1206:1472 */         cuentaPorPagar.setValor(cpp.getValor());
/* 1207:1473 */         facturaProveedor.getListaCuentaPorPagar().add(cuentaPorPagar);
/* 1208:     */       }
/* 1209:     */     }
/* 1210:1476 */     if (fp.getDocumento().isIndicadorDocumentoTributario())
/* 1211:     */     {
/* 1212:1477 */       FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/* 1213:1478 */       facturaProveedorSRI.setIdOrganizacion(fp.getIdOrganizacion());
/* 1214:1479 */       facturaProveedorSRI.setIdSucursal(fp.getSucursal().getIdSucursal());
/* 1215:1480 */       if (fp.getFacturaProveedorSRI() != null)
/* 1216:     */       {
/* 1217:1482 */         facturaProveedorSRI.setIdOrganizacion(fp.getFacturaProveedorSRI().getIdOrganizacion());
/* 1218:1483 */         facturaProveedorSRI.setIdSucursal(fp.getFacturaProveedorSRI().getIdSucursal());
/* 1219:1484 */         facturaProveedorSRI.setTipoIdentificacion(fp.getFacturaProveedorSRI().getTipoIdentificacion());
/* 1220:1485 */         facturaProveedorSRI.setTipoComprobanteSRI(fp.getFacturaProveedorSRI().getTipoComprobanteSRI());
/* 1221:     */         
/* 1222:1487 */         facturaProveedorSRI.setCreditoTributarioSRI(null);
/* 1223:1488 */         facturaProveedorSRI.setAutorizacion(fp.getFacturaProveedorSRI().getAutorizacion());
/* 1224:1489 */         facturaProveedorSRI.setFechaEmision(fp.getFacturaProveedorSRI().getFechaEmision());
/* 1225:1490 */         facturaProveedorSRI.setEstablecimiento(fp.getFacturaProveedorSRI().getEstablecimiento());
/* 1226:1491 */         facturaProveedorSRI.setPuntoEmision(fp.getFacturaProveedorSRI().getPuntoEmision());
/* 1227:1492 */         facturaProveedorSRI.setCodigoFormaPagoSRI(fp.getFacturaProveedorSRI().getCodigoFormaPagoSRI());
/* 1228:     */       }
/* 1229:1495 */       facturaProveedorSRI.setNumero("0");
/* 1230:1496 */       facturaProveedorSRI.setEstablecimientoRetencion("000");
/* 1231:1497 */       facturaProveedorSRI.setPuntoEmisionRetencion("000");
/* 1232:1498 */       facturaProveedorSRI.setNumeroRetencion("0");
/* 1233:1499 */       facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/* 1234:1500 */       facturaProveedorSRI.setEstado(Estado.ELABORADO);
/* 1235:1501 */       facturaProveedorSRI.setFacturaProveedor(facturaProveedor);
/* 1236:1502 */       facturaProveedor.setFacturaProveedorSRI(facturaProveedorSRI);
/* 1237:     */     }
/* 1238:1504 */     return facturaProveedor;
/* 1239:     */   }
/* 1240:     */   
/* 1241:     */   public int contarPorCriterio(Map<String, String> filters)
/* 1242:     */   {
/* 1243:1514 */     return this.facturaProveedorDao.contarPorCriterio(filters);
/* 1244:     */   }
/* 1245:     */   
/* 1246:     */   public void obtenerImpuestosProductos(Producto producto, DetalleFacturaProveedor dfp)
/* 1247:     */     throws ExcepcionAS2Inventario
/* 1248:     */   {
/* 1249:1526 */     producto.setCategoriaImpuesto(this.servicioCategoriaImpuesto.cargarDetalle(producto.getCategoriaImpuesto().getId()));
/* 1250:     */     
/* 1251:1528 */     FacturaProveedorSRI fpSRI = dfp.getFacturaProveedor().getFacturaProveedorSRI();
/* 1252:1529 */     Date fechaEmision = (fpSRI != null) && (fpSRI.getFechaEmision() != null) ? fpSRI.getFechaEmision() : dfp.getFacturaProveedor().getFecha();
/* 1253:1530 */     List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, fechaEmision);
/* 1254:1532 */     for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/* 1255:     */     {
/* 1256:1534 */       ImpuestoProductoFacturaProveedor impuestoProductoFacturaProveedor = new ImpuestoProductoFacturaProveedor();
/* 1257:1535 */       impuestoProductoFacturaProveedor.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 1258:1536 */       impuestoProductoFacturaProveedor.setImpuesto(rangoImpuesto.getImpuesto());
/* 1259:1537 */       impuestoProductoFacturaProveedor.setDetalleFacturaProveedor(dfp);
/* 1260:1538 */       dfp.getListaImpuestoProductoFacturaProveedor().add(impuestoProductoFacturaProveedor);
/* 1261:     */     }
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   public long verificaExistenciaNumero(FacturaProveedor facturaProveedor)
/* 1265:     */   {
/* 1266:1550 */     return this.facturaProveedorDao.verificaExistenciaNumero(facturaProveedor);
/* 1267:     */   }
/* 1268:     */   
/* 1269:     */   public void agregarGastoProductoFacturaProveedor(DetalleFacturaProveedor dfp, CentroCosto centroCosto)
/* 1270:     */     throws ExcepcionAS2
/* 1271:     */   {
/* 1272:1554 */     agregarGastoProductoFacturaProveedor(dfp, null, null, null, null, null, null, null);
/* 1273:     */   }
/* 1274:     */   
/* 1275:     */   public void agregarGastoProductoFacturaProveedor(DetalleFacturaProveedor dfp, DimensionContable dimensionContable1, DimensionContable dimensionContable2, DimensionContable dimensionContable3, DimensionContable dimensionContable4, DimensionContable dimensionContable5, CuentaContable cuentaContableGasto, CuentaContable cuentaContableGastoImpuesto)
/* 1276:     */     throws ExcepcionAS2
/* 1277:     */   {
/* 1278:1569 */     totalizarImpuestoLinea(dfp);
/* 1279:1570 */     if (dfp.getListaGastoProductoFactura().isEmpty())
/* 1280:     */     {
/* 1281:1574 */       GastoProductoFacturaProveedor gpfpGasto = new GastoProductoFacturaProveedor();
/* 1282:1576 */       if (cuentaContableGasto == null) {
/* 1283:1579 */         cuentaContableGasto = this.servicioProducto.buscarPorCodigoYCargarCuentasContables(dfp.getProducto().getCodigo()).getSubcategoriaProducto().getCuentaContableGasto();
/* 1284:     */       }
/* 1285:1585 */       if (cuentaContableGasto != null) {
/* 1286:1586 */         gpfpGasto.setCuentaContableGasto(cuentaContableGasto);
/* 1287:     */       }
/* 1288:1589 */       gpfpGasto.setDetalleFacturaProveedor(dfp);
/* 1289:1590 */       if ((!dfp.getFacturaProveedor().isIndicadorCreditoTributario()) || 
/* 1290:1591 */         (ParametrosSistema.getFacturaProveedorImpuestoProductoServicioGasto(dfp.getFacturaProveedor().getIdOrganizacion()).booleanValue())) {
/* 1291:1593 */         gpfpGasto.setValor(dfp.getBaseImponible().add(dfp.getValorImpuestosLinea()).subtract(dfp.getValorDescuentoImpuestosLinea()));
/* 1292:     */       } else {
/* 1293:1596 */         gpfpGasto.setValor(dfp.getBaseImponible());
/* 1294:     */       }
/* 1295:1599 */       if (gpfpGasto.getCuentaContableGasto() == null) {
/* 1296:1600 */         gpfpGasto.setCuentaContableGasto(new CuentaContable());
/* 1297:     */       }
/* 1298:1608 */       if (dimensionContable1 != null) {
/* 1299:1609 */         gpfpGasto.setDimensionContable1(dimensionContable1);
/* 1300:     */       }
/* 1301:1611 */       if (dimensionContable2 != null) {
/* 1302:1612 */         gpfpGasto.setDimensionContable2(dimensionContable2);
/* 1303:     */       }
/* 1304:1614 */       if (dimensionContable3 != null) {
/* 1305:1615 */         gpfpGasto.setDimensionContable3(dimensionContable3);
/* 1306:     */       }
/* 1307:1617 */       if (dimensionContable4 != null) {
/* 1308:1618 */         gpfpGasto.setDimensionContable4(dimensionContable4);
/* 1309:     */       }
/* 1310:1620 */       if (dimensionContable5 != null) {
/* 1311:1621 */         gpfpGasto.setDimensionContable5(dimensionContable5);
/* 1312:     */       }
/* 1313:1624 */       dfp.getListaGastoProductoFacturaProveedor().add(gpfpGasto);
/* 1314:1625 */       if ((dfp.getFacturaProveedor().isIndicadorCreditoTributario()) && 
/* 1315:1626 */         (!ParametrosSistema.getFacturaProveedorImpuestoProductoServicioGasto(dfp.getFacturaProveedor().getIdOrganizacion()).booleanValue())) {
/* 1316:1629 */         for (ImpuestoProductoFacturaProveedor ipfp : dfp.getListaImpuestoProductoFacturaProveedor()) {
/* 1317:1630 */           if ((!ipfp.isEliminado()) && (ipfp.getImpuestoProducto().compareTo(BigDecimal.ZERO) > 0))
/* 1318:     */           {
/* 1319:1631 */             GastoProductoFacturaProveedor gpfpImpuesto = new GastoProductoFacturaProveedor();
/* 1320:1632 */             if (cuentaContableGastoImpuesto == null)
/* 1321:     */             {
/* 1322:1633 */               cuentaContableGastoImpuesto = new CuentaContable();
/* 1323:1634 */               Impuesto impuesto = this.servicioImpuesto.buscarPorId(ipfp.getImpuesto().getId());
/* 1324:1635 */               if (impuesto.getCuentaContableCompra() != null) {
/* 1325:1636 */                 cuentaContableGastoImpuesto = impuesto.getCuentaContableCompra();
/* 1326:     */               }
/* 1327:     */             }
/* 1328:1640 */             gpfpImpuesto.setCuentaContableGasto(cuentaContableGastoImpuesto);
/* 1329:1641 */             gpfpImpuesto.setValor(ipfp.getImpuestoProducto().setScale(2, RoundingMode.HALF_UP));
/* 1330:1642 */             gpfpImpuesto.setDetalleFacturaProveedor(dfp);
/* 1331:1644 */             if (dimensionContable1 != null) {
/* 1332:1645 */               gpfpImpuesto.setDimensionContable1(dimensionContable1);
/* 1333:     */             }
/* 1334:1647 */             if (dimensionContable2 != null) {
/* 1335:1648 */               gpfpImpuesto.setDimensionContable2(dimensionContable2);
/* 1336:     */             }
/* 1337:1650 */             if (dimensionContable3 != null) {
/* 1338:1651 */               gpfpImpuesto.setDimensionContable3(dimensionContable3);
/* 1339:     */             }
/* 1340:1653 */             if (dimensionContable4 != null) {
/* 1341:1654 */               gpfpImpuesto.setDimensionContable4(dimensionContable4);
/* 1342:     */             }
/* 1343:1656 */             if (dimensionContable5 != null) {
/* 1344:1657 */               gpfpImpuesto.setDimensionContable5(dimensionContable5);
/* 1345:     */             }
/* 1346:1660 */             dfp.getListaGastoProductoFacturaProveedor().add(gpfpImpuesto);
/* 1347:     */           }
/* 1348:     */         }
/* 1349:     */       }
/* 1350:     */     }
/* 1351:     */   }
/* 1352:     */   
/* 1353:     */   public void actualizarAtributoEntidad(FacturaProveedor facturaProveedor, HashMap<String, Object> campos)
/* 1354:     */   {
/* 1355:1676 */     this.facturaProveedorDao.actualizarAtributoEntidad(facturaProveedor, campos);
/* 1356:     */   }
/* 1357:     */   
/* 1358:     */   public FacturaProveedor buscarPorRecepcionProveedor(int idRecepcionProveedor)
/* 1359:     */   {
/* 1360:1687 */     return this.facturaProveedorDao.buscarPorRecepcionProveedor(idRecepcionProveedor);
/* 1361:     */   }
/* 1362:     */   
/* 1363:     */   public RecepcionProveedor cargarDetalleARecibir(RecepcionProveedor recepcionProveedor, Integer idFacturaProveedor, List<DetalleFacturaProveedor> listaDetalleFacturaProveedorPorRecepcionar)
/* 1364:     */   {
/* 1365:1698 */     FacturaProveedor facturaProveedor = this.facturaProveedorDao.cargarDetalleARecibir(idFacturaProveedor);
/* 1366:1699 */     recepcionProveedor.setFacturaProveedor(facturaProveedor);
/* 1367:1700 */     recepcionProveedor.setEmpresa(facturaProveedor.getEmpresa());
/* 1368:1701 */     recepcionProveedor.setDescripcion(facturaProveedor.getDescripcion());
/* 1369:1702 */     recepcionProveedor.setProyecto(facturaProveedor.getProyecto());
/* 1370:1703 */     recepcionProveedor.setPersonaResponsable(facturaProveedor.getPersonaResponsable());
/* 1371:     */     FacturaProveedorImportacion facturaProveedorImportacion;
/* 1372:1705 */     if (facturaProveedor.getDocumento().isIndicadorDocumentoExterior())
/* 1373:     */     {
/* 1374:1707 */       facturaProveedorImportacion = this.servicioFacturaProveedorImportacion.cargarDetalle(facturaProveedor.getFacturaProveedorImportacion().getId());
/* 1375:1708 */       facturaProveedor = facturaProveedorImportacion.getFacturaProveedor();
/* 1376:1709 */       recepcionProveedor.setNumeroImportacion(facturaProveedor.getNumero());
/* 1377:     */     }
/* 1378:1711 */     for (DetalleFacturaProveedor dfp : listaDetalleFacturaProveedorPorRecepcionar) {
/* 1379:1713 */       if (dfp.getProducto().getTipoProducto() == TipoProducto.ARTICULO)
/* 1380:     */       {
/* 1381:1714 */         DetalleRecepcionProveedor detalleRecepcionProveedor = new DetalleRecepcionProveedor();
/* 1382:1715 */         detalleRecepcionProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1383:1716 */         detalleRecepcionProveedor.setIdSucursal(AppUtil.getSucursal().getId());
/* 1384:1717 */         detalleRecepcionProveedor.setProducto(dfp.getProducto());
/* 1385:1718 */         detalleRecepcionProveedor.setCantidad(dfp.getCantidad());
/* 1386:1719 */         detalleRecepcionProveedor.setUnidadCompra(dfp.getUnidadCompra());
/* 1387:1720 */         detalleRecepcionProveedor.setRecepcionProveedor(recepcionProveedor);
/* 1388:1721 */         detalleRecepcionProveedor.setDetalleFacturaProveedor(dfp);
/* 1389:1722 */         recepcionProveedor.getListaDetalleRecepcionProveedor().add(detalleRecepcionProveedor);
/* 1390:     */         
/* 1391:1724 */         InventarioProducto inventarioProducto = new InventarioProducto();
/* 1392:1725 */         inventarioProducto.setProducto(detalleRecepcionProveedor.getProducto());
/* 1393:1726 */         detalleRecepcionProveedor.setInventarioProducto(inventarioProducto);
/* 1394:     */       }
/* 1395:     */     }
/* 1396:1731 */     recepcionProveedor.setFacturaProveedor(facturaProveedor);
/* 1397:     */     
/* 1398:1733 */     return recepcionProveedor;
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   public void validarBodega(FacturaProveedor facturaProveedor)
/* 1402:     */     throws ExcepcionAS2
/* 1403:     */   {
/* 1404:1743 */     for (Iterator localIterator1 = facturaProveedor.getListaDetalleFacturaProveedor().iterator(); localIterator1.hasNext();)
/* 1405:     */     {
/* 1406:1743 */       dfp = (DetalleFacturaProveedor)localIterator1.next();
/* 1407:1744 */       for (DetalleRecepcionProveedor detalleRecepcionProveedor : dfp.getListaDetalleRecepcionProveedor()) {
/* 1408:1745 */         if ((!dfp.isEliminado()) && (detalleRecepcionProveedor.getBodega() == null))
/* 1409:     */         {
/* 1410:1747 */           String strMensaje = " " + dfp.getProducto().getCodigo() + " " + dfp.getProducto().getNombre();
/* 1411:1748 */           throw new ExcepcionAS2("msg_producto_bodega_compra", strMensaje);
/* 1412:     */         }
/* 1413:     */       }
/* 1414:     */     }
/* 1415:     */     DetalleFacturaProveedor dfp;
/* 1416:     */   }
/* 1417:     */   
/* 1418:     */   public Object[] getDatosFacturaImpresionAsiento(int idAsiento)
/* 1419:     */   {
/* 1420:1761 */     return this.facturaProveedorDao.getDatosFacturaImpresionAsiento(idAsiento);
/* 1421:     */   }
/* 1422:     */   
/* 1423:     */   public void liberarRecepciones(FacturaProveedor facturaProveedor)
/* 1424:     */   {
/* 1425:1771 */     if (facturaProveedor.getRecepcionProveedor() != null)
/* 1426:     */     {
/* 1427:1773 */       HashMap<String, Object> camposFacturaProveedor = new HashMap();
/* 1428:1774 */       camposFacturaProveedor.put("recepcionProveedor", null);
/* 1429:1775 */       this.facturaProveedorDao.actualizarAtributoEntidad(facturaProveedor, camposFacturaProveedor);
/* 1430:1777 */       for (DetalleFacturaProveedor detalleFacturaProveedor : facturaProveedor.getListaDetalleFacturaProveedor()) {
/* 1431:1778 */         for (DetalleRecepcionProveedor detalleRecepcionProveedor : detalleFacturaProveedor.getListaDetalleRecepcionProveedor())
/* 1432:     */         {
/* 1433:1779 */           HashMap<String, Object> camposDetalleFacturaProveedor = new HashMap();
/* 1434:1780 */           camposDetalleFacturaProveedor.put("detalleFacturaProveedor", null);
/* 1435:1781 */           this.detalleRecepcionProveedorDao.actualizarAtributoEntidad(detalleRecepcionProveedor, camposDetalleFacturaProveedor);
/* 1436:     */         }
/* 1437:     */       }
/* 1438:     */     }
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   public List<FacturaProveedor> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 1442:     */   {
/* 1443:1795 */     return this.facturaProveedorDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1447:     */   public void guardarAux(FacturaProveedor facturaProveedor)
/* 1448:     */     throws ExcepcionAS2
/* 1449:     */   {
/* 1450:1806 */     totalizar(facturaProveedor);
/* 1451:     */     try
/* 1452:     */     {
/* 1453:1809 */       if ((!facturaProveedor.isIndicadorSaldoInicial()) && (!facturaProveedor.isIndicadorGastoImportacion())) {
/* 1454:1810 */         for (DetalleFacturaProveedor detalleFactura : facturaProveedor.getListaDetalleFacturaProveedor()) {
/* 1455:1813 */           if ((!detalleFactura.isEliminado()) && (detalleFactura.getProducto().isTraIndicadorServicio()))
/* 1456:     */           {
/* 1457:1815 */             valorLinea = detalleFactura.getBaseImponible().add(detalleFactura.getValorImpuestosLinea());
/* 1458:1817 */             if (valorLinea.compareTo(detalleFactura.getValorGastoContable()) != 0)
/* 1459:     */             {
/* 1460:1818 */               String mensaje = " " + detalleFactura.getProducto().getNombre().trim() + " " + detalleFactura.getProducto().getNombre();
/* 1461:     */               
/* 1462:1820 */               throw new ExcepcionAS2Compras("msg_error_diferencia_gasto_servicio", mensaje);
/* 1463:     */             }
/* 1464:     */           }
/* 1465:     */         }
/* 1466:     */       }
/* 1467:     */       BigDecimal valorLinea;
/* 1468:1827 */       for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor())
/* 1469:     */       {
/* 1470:1830 */         for (ImpuestoProductoFacturaProveedor impuestoProductoFacturaProveedor : dfp.getListaImpuestoProductoFacturaProveedor()) {
/* 1471:1831 */           this.impuestoProductoFacturaProveedorDao.guardar(impuestoProductoFacturaProveedor);
/* 1472:     */         }
/* 1473:1835 */         for (GastoProductoFacturaProveedor gasto : dfp.getListaGastoProductoFacturaProveedor())
/* 1474:     */         {
/* 1475:1836 */           if (!dfp.getProducto().isTraIndicadorServicio()) {
/* 1476:1837 */             gasto.setEliminado(true);
/* 1477:     */           }
/* 1478:1839 */           this.gastoProductoFacturaProveedorDao.guardar(gasto);
/* 1479:     */         }
/* 1480:1841 */         this.detalleFacturaProveedorDao.guardar(dfp);
/* 1481:     */       }
/* 1482:1843 */       this.facturaProveedorDao.guardar(facturaProveedor);
/* 1483:1845 */       if (!facturaProveedor.isIndicadorSaldoInicial()) {
/* 1484:1846 */         contabilizar(facturaProveedor);
/* 1485:     */       }
/* 1486:     */     }
/* 1487:     */     catch (ExcepcionAS2Compras e)
/* 1488:     */     {
/* 1489:1850 */       this.context.setRollbackOnly();
/* 1490:1851 */       e.printStackTrace();
/* 1491:1852 */       throw e;
/* 1492:     */     }
/* 1493:     */     catch (ExcepcionAS2Financiero e)
/* 1494:     */     {
/* 1495:1854 */       this.context.setRollbackOnly();
/* 1496:1855 */       e.printStackTrace();
/* 1497:1856 */       throw e;
/* 1498:     */     }
/* 1499:     */     catch (ExcepcionAS2 e)
/* 1500:     */     {
/* 1501:1858 */       this.context.setRollbackOnly();
/* 1502:1859 */       e.printStackTrace();
/* 1503:1860 */       throw e;
/* 1504:     */     }
/* 1505:     */     catch (Exception e)
/* 1506:     */     {
/* 1507:1862 */       this.context.setRollbackOnly();
/* 1508:1863 */       e.printStackTrace();
/* 1509:1864 */       LOG.error(e);
/* 1510:1865 */       throw new ExcepcionAS2(e);
/* 1511:     */     }
/* 1512:     */   }
/* 1513:     */   
/* 1514:     */   public List<CuentaPorPagar> obtenerFacturasPendientesLiquidacionCuentaPorPagar(Pago pago, int idFacturaProveedor, BigDecimal tolerancia, boolean filtrarPorSucursal)
/* 1515:     */   {
/* 1516:1873 */     return this.facturaProveedorDao.obtenerFacturasPendientesLiquidacionCuentaPorPagar(pago, idFacturaProveedor, tolerancia, filtrarPorSucursal);
/* 1517:     */   }
/* 1518:     */   
/* 1519:     */   public void bloquearFactura(int idFacturaProveedor, boolean bloqueo)
/* 1520:     */   {
/* 1521:1883 */     this.facturaProveedorDao.bloquearFactura(idFacturaProveedor, bloqueo);
/* 1522:     */   }
/* 1523:     */   
/* 1524:     */   public List<FacturaProveedor> obtenerListaComboAutocompletar(int idEmpresa, String cadena)
/* 1525:     */   {
/* 1526:1888 */     return this.facturaProveedorDao.obtenerListaComboAutocompletar(idEmpresa, cadena);
/* 1527:     */   }
/* 1528:     */   
/* 1529:     */   public List<FacturaProveedor> autocompletarFacturaProveedorDevolucion(int idEmpresa, Map<String, String> filters)
/* 1530:     */   {
/* 1531:1898 */     return this.facturaProveedorDao.autocompletarFacturaProveedorDevolucion(idEmpresa, filters);
/* 1532:     */   }
/* 1533:     */   
/* 1534:     */   public List<DetalleFacturaProveedor> buscarDetallesNoDespachados(Integer idFacturaProveedor)
/* 1535:     */   {
/* 1536:1903 */     return this.facturaProveedorDao.buscarDetallesNoDespachados(idFacturaProveedor);
/* 1537:     */   }
/* 1538:     */   
/* 1539:     */   private void validarGastoProductoFacturaProveedor(FacturaProveedor facturaProveedor)
/* 1540:     */     throws ExcepcionAS2Compras, ExcepcionAS2Financiero, ExcepcionAS2
/* 1541:     */   {
/* 1542:1908 */     for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor()) {
/* 1543:1909 */       if ((!dfp.isEliminado()) && (dfp.getProducto().isTraIndicadorServicio()) && (!dfp.isIndicadorGastoImportacion()) && 
/* 1544:1910 */         (dfp.getListaGastoProductoFacturaProveedor().size() > 0)) {
/* 1545:1911 */         for (GastoProductoFacturaProveedor gpfpGasto : dfp.getListaGastoProductoFacturaProveedor())
/* 1546:     */         {
/* 1547:1912 */           System.out.println("cuenta: " + gpfpGasto.getCuentaContableGasto().getNombre());
/* 1548:1913 */           if (!gpfpGasto.isEliminado())
/* 1549:     */           {
/* 1550:1914 */             if (gpfpGasto.getCuentaContableGasto().getNombre() == null) {
/* 1551:1915 */               throw new ExcepcionAS2Compras("msg_info_seleccionar_cuenta_contable");
/* 1552:     */             }
/* 1553:1917 */             if ((gpfpGasto.getCuentaContableGasto().isIndicadorValidarDimension1()) && (gpfpGasto.getDimensionContable1() == null))
/* 1554:     */             {
/* 1555:1919 */               AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { gpfpGasto.getCuentaContableGasto().getCodigo(), gpfpGasto.getCuentaContableGasto().getNombre(), "1" });
/* 1556:1920 */               throw new ExcepcionAS2(exception.getMensaje());
/* 1557:     */             }
/* 1558:1922 */             if ((gpfpGasto.getCuentaContableGasto().isIndicadorValidarDimension2()) && 
/* 1559:1923 */               (gpfpGasto.getDimensionContable2() == null))
/* 1560:     */             {
/* 1561:1925 */               AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { gpfpGasto.getCuentaContableGasto().getCodigo(), gpfpGasto.getCuentaContableGasto().getNombre(), "2" });
/* 1562:1926 */               throw new ExcepcionAS2(exception.getMensaje());
/* 1563:     */             }
/* 1564:1928 */             if ((gpfpGasto.getCuentaContableGasto().isIndicadorValidarDimension3()) && 
/* 1565:1929 */               (gpfpGasto.getDimensionContable3() == null))
/* 1566:     */             {
/* 1567:1931 */               AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { gpfpGasto.getCuentaContableGasto().getCodigo(), gpfpGasto.getCuentaContableGasto().getNombre(), "3" });
/* 1568:1932 */               throw new ExcepcionAS2(exception.getMensaje());
/* 1569:     */             }
/* 1570:1934 */             if ((gpfpGasto.getCuentaContableGasto().isIndicadorValidarDimension4()) && 
/* 1571:1935 */               (gpfpGasto.getDimensionContable4() == null))
/* 1572:     */             {
/* 1573:1937 */               AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { gpfpGasto.getCuentaContableGasto().getCodigo(), gpfpGasto.getCuentaContableGasto().getNombre(), "4" });
/* 1574:1938 */               throw new ExcepcionAS2(exception.getMensaje());
/* 1575:     */             }
/* 1576:1940 */             if ((gpfpGasto.getCuentaContableGasto().isIndicadorValidarDimension5()) && 
/* 1577:1941 */               (gpfpGasto.getDimensionContable5() == null))
/* 1578:     */             {
/* 1579:1943 */               AS2Exception exception = new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { gpfpGasto.getCuentaContableGasto().getCodigo(), gpfpGasto.getCuentaContableGasto().getNombre(), "5" });
/* 1580:1944 */               throw new ExcepcionAS2(exception.getMensaje());
/* 1581:     */             }
/* 1582:     */           }
/* 1583:     */         }
/* 1584:     */       }
/* 1585:     */     }
/* 1586:     */   }
/* 1587:     */   
/* 1588:     */   public void agregarDetalleFacturaSRI332(FacturaProveedorSRI facturaProveedorSRI)
/* 1589:     */   {
/* 1590:1957 */     String codigoSRI332 = "332";
/* 1591:1958 */     DetalleFacturaProveedorSRI dfpSRI332 = null;
/* 1592:1959 */     Map<String, String> filters = new HashMap();
/* 1593:1960 */     filters.put("idOrganizacion", Integer.toString(facturaProveedorSRI.getIdOrganizacion()));
/* 1594:1961 */     filters.put("codigo", codigoSRI332);
/* 1595:1962 */     filters.put("activo", "true");
/* 1596:1963 */     List<ConceptoRetencionSRI> listaConceptoRetencionSRI = this.servicioConceptoRetencionSRI.obtenerListaCombo("codigo", true, filters);
/* 1597:     */     
/* 1598:1965 */     BigDecimal baseImponibleTotal = facturaProveedorSRI.getBaseImponible();
/* 1599:1967 */     for (DetalleFacturaProveedorSRI dfpSRI : facturaProveedorSRI.getListaDetalleFacturaProveedorSRINoEliminados()) {
/* 1600:1968 */       if (dfpSRI.getConceptoRetencionSRI().getTipoConceptoRetencion() == TipoConceptoRetencion.FUENTE) {
/* 1601:1970 */         if (dfpSRI.getConceptoRetencionSRI().getCodigo().equals(codigoSRI332)) {
/* 1602:1971 */           dfpSRI332 = dfpSRI;
/* 1603:     */         } else {
/* 1604:1973 */           baseImponibleTotal = baseImponibleTotal.subtract(dfpSRI.getBaseImponibleRetencion());
/* 1605:     */         }
/* 1606:     */       }
/* 1607:     */     }
/* 1608:1978 */     if (baseImponibleTotal.compareTo(BigDecimal.ZERO) > 0) {
/* 1609:1979 */       if (dfpSRI332 != null) {
/* 1610:1980 */         dfpSRI332.setBaseImponibleRetencion(baseImponibleTotal);
/* 1611:     */       } else {
/* 1612:1982 */         this.servicioFacturaProveedorSRI.cargarDetalleRetencion(facturaProveedorSRI, (ConceptoRetencionSRI)listaConceptoRetencionSRI.get(0), baseImponibleTotal);
/* 1613:     */       }
/* 1614:     */     }
/* 1615:     */   }
/* 1616:     */   
/* 1617:     */   public List<CuentaPorPagar> obtenerFacturasPendientes(int idOrganizacion, Date fechaVencimiento, TipoServicioCuentaBancariaEnum tipoServicio, CategoriaEmpresa categoriaEmpresa, Documento documento)
/* 1618:     */   {
/* 1619:1990 */     Boolean indicadorPagoCash = fechaVencimiento == null ? null : Boolean.valueOf(true);
/* 1620:1991 */     return this.facturaProveedorDao.obtenerFacturasPendientes(0, 0, fechaVencimiento, tipoServicio, categoriaEmpresa, 0, documento, indicadorPagoCash, idOrganizacion, null, null);
/* 1621:     */   }
/* 1622:     */   
/* 1623:     */   public List<CuentaPorPagar> obtenerFacturasPendientes(int idEmpresa, int idFacturaProveedor, Date fechaVencimientoHasta, TipoServicioCuentaBancariaEnum tipoServicio, CategoriaEmpresa categoriaEmpresa, int idSucursal, Documento documento, Boolean indicadorPagoCash, Integer idOrganizacion, Date fechaVencimientoDesde, Boolean indicadorCXPEnOPP)
/* 1624:     */   {
/* 1625:1999 */     return this.facturaProveedorDao.obtenerFacturasPendientes(idEmpresa, idFacturaProveedor, fechaVencimientoHasta, tipoServicio, categoriaEmpresa, idSucursal, documento, indicadorPagoCash, idOrganizacion
/* 1626:2000 */       .intValue(), fechaVencimientoDesde, indicadorCXPEnOPP);
/* 1627:     */   }
/* 1628:     */   
/* 1629:     */   public List<CuentaPorPagar> obtenerFacturasPendientes(int idEmpresa, int idFacturaProveedor, Date fechaVencimientoHasta, TipoServicioCuentaBancariaEnum tipoServicio, CategoriaEmpresa categoriaEmpresa, int idSucursal, Documento documento, Boolean indicadorPagoCash, Integer idOrganizacion, Date fechaVencimientoDesde)
/* 1630:     */   {
/* 1631:2006 */     return obtenerFacturasPendientes(idEmpresa, idFacturaProveedor, fechaVencimientoHasta, tipoServicio, categoriaEmpresa, idSucursal, documento, indicadorPagoCash, idOrganizacion, fechaVencimientoDesde, null);
/* 1632:     */   }
/* 1633:     */   
/* 1634:     */   public void anularRetencion(FacturaProveedorSRI fpSRI)
/* 1635:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 1636:     */   {
/* 1637:     */     try
/* 1638:     */     {
/* 1639:     */       CajaChica cajaChica;
/* 1640:2015 */       if (fpSRI.getCompraCajaChica() != null)
/* 1641:     */       {
/* 1642:2016 */         cajaChica = fpSRI.getCompraCajaChica().getCajaChica();
/* 1643:     */         
/* 1644:2018 */         BigDecimal valorRetencion = fpSRI.isIndicadorRetencionAsumida() ? BigDecimal.ZERO : fpSRI.getTotalValorRetenido();
/* 1645:     */         
/* 1646:2020 */         cajaChica.setValor(this.servicioFacturaProveedorSRI
/* 1647:2021 */           .valorAcumuladoCajaChica(null, fpSRI.getCompraCajaChica().getCajaChica()).add(valorRetencion));
/* 1648:     */         
/* 1649:2023 */         this.cajaChicaDao.guardar(cajaChica);
/* 1650:     */       }
/* 1651:2027 */       if ((fpSRI != null) && (fpSRI.getDocumento().isIndicadorDocumentoTributario()) && (
/* 1652:2028 */         (!fpSRI.getDocumento().isIndicadorDocumentoElectronico()) || (fpSRI.getAutorizacionRetencion() != null))) {
/* 1653:2030 */         crearAnuladoSRI(fpSRI);
/* 1654:     */       }
/* 1655:2034 */       if (fpSRI.getPago() != null) {
/* 1656:2035 */         this.servicioPago.anularPago(fpSRI.getPago());
/* 1657:     */       }
/* 1658:2037 */       for (DetalleFacturaProveedorSRI detalle : fpSRI.getListaDetalleFacturaProveedorSRI())
/* 1659:     */       {
/* 1660:2038 */         detalle.setEliminado(true);
/* 1661:2039 */         this.detalleFacturaProveedorSRIDao.guardar(detalle);
/* 1662:     */       }
/* 1663:2042 */       fpSRI.setAutorizacionRetencion("0000000000");
/* 1664:2043 */       fpSRI.setEstablecimientoRetencion("000");
/* 1665:2044 */       fpSRI.setEstado(Estado.APROBADO);
/* 1666:2045 */       fpSRI.setFechaEmisionRetencion(null);
/* 1667:2046 */       fpSRI.setIndicadorRetencionEmitida(false);
/* 1668:2047 */       fpSRI.setNumeroRetencion("0");
/* 1669:2048 */       fpSRI.setPuntoEmisionRetencion("000");
/* 1670:2049 */       fpSRI.setTotalValorRetenido(null);
/* 1671:2050 */       fpSRI.setCreditoTributarioSRI(null);
/* 1672:2051 */       fpSRI.setDocumentoModificado(null);
/* 1673:2052 */       fpSRI.setPago(null);
/* 1674:2053 */       fpSRI.setAmbiente(0);
/* 1675:2054 */       fpSRI.setTipoEmision(0);
/* 1676:2055 */       fpSRI.setIndicadorDocumentoElectronico(false);
/* 1677:2056 */       fpSRI.setClaveAcceso(null);
/* 1678:2057 */       fpSRI.setDireccionMatriz(null);
/* 1679:2058 */       fpSRI.setDireccionSucursal(null);
/* 1680:2059 */       fpSRI.setEmail(null);
/* 1681:2060 */       fpSRI.setFechaAutorizacion(null);
/* 1682:2061 */       fpSRI.setCodigoUnico(null);
/* 1683:2062 */       fpSRI.setDocumento(null);
/* 1684:2063 */       fpSRI.setMensajeSRI(null);
/* 1685:2064 */       fpSRI.setIndicadorRetencionAsumida(false);
/* 1686:2065 */       this.comprobanteElectronicoPendienteSRIDao.eliminarComprobanteElectronicoPendienteSRI(null, fpSRI, null);
/* 1687:2066 */       this.facturaProveedorSRIDao.guardar(fpSRI);
/* 1688:     */     }
/* 1689:     */     catch (ExcepcionAS2Financiero e)
/* 1690:     */     {
/* 1691:2069 */       this.context.setRollbackOnly();
/* 1692:2070 */       throw new ExcepcionAS2(e.getCodigoExcepcion());
/* 1693:     */     }
/* 1694:     */     catch (Exception e)
/* 1695:     */     {
/* 1696:2072 */       e.printStackTrace();
/* 1697:2073 */       this.context.setRollbackOnly();
/* 1698:2074 */       throw new ExcepcionAS2(e.getMessage());
/* 1699:     */     }
/* 1700:     */   }
/* 1701:     */   
/* 1702:     */   private void crearAnuladoSRI(FacturaProveedorSRI fpSRI)
/* 1703:     */     throws ExcepcionAS2
/* 1704:     */   {
/* 1705:2079 */     AnuladoSRI anuladoSRI = new AnuladoSRI();
/* 1706:2080 */     anuladoSRI.setIdOrganizacion(fpSRI.getIdOrganizacion());
/* 1707:2081 */     anuladoSRI.setIdSucursal(fpSRI.getIdSucursal());
/* 1708:     */     
/* 1709:2083 */     TipoComprobanteSRI tcsri = ((Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.RETENCION_PROVEEDOR, fpSRI.getIdOrganizacion()).get(0)).getTipoComprobanteSRI();
/* 1710:2084 */     anuladoSRI.setTipoComprobanteSRI(tcsri);
/* 1711:2085 */     anuladoSRI.setAnio(
/* 1712:2086 */       FuncionesUtiles.getAnio(fpSRI.getFechaEmisionRetencion() == null ? fpSRI.getFechaEmision() : fpSRI.getFechaEmisionRetencion()));
/* 1713:2087 */     anuladoSRI.setMes(
/* 1714:2088 */       FuncionesUtiles.getMes(fpSRI.getFechaEmisionRetencion() == null ? fpSRI.getFechaEmision() : fpSRI.getFechaEmisionRetencion()));
/* 1715:2089 */     anuladoSRI.setEstablecimiento(fpSRI.getEstablecimientoRetencion());
/* 1716:2090 */     anuladoSRI.setPuntoEmision(fpSRI.getPuntoEmisionRetencion());
/* 1717:2091 */     anuladoSRI.setNumeroDesde(fpSRI.getNumeroRetencion());
/* 1718:2092 */     anuladoSRI.setNumeroHasta(fpSRI.getNumeroRetencion());
/* 1719:2093 */     anuladoSRI.setAutorizacion(fpSRI.getAutorizacionRetencion() != null ? fpSRI.getAutorizacionRetencion() : "0000000000");
/* 1720:2094 */     anuladoSRI.setDocumentoRelacionado(Integer.valueOf(fpSRI.getIdFacturaProveedorSRI()));
/* 1721:2095 */     anuladoSRI.setFechaEmisionDocumento(fpSRI.getFechaEmisionRetencion());
/* 1722:2096 */     this.servicioAnuladoSRI.guardar(anuladoSRI);
/* 1723:     */   }
/* 1724:     */   
/* 1725:     */   public void liberarRecepcionFacturaProveedor(RecepcionProveedor recepcionProveedor)
/* 1726:     */   {
/* 1727:2101 */     this.facturaProveedorDao.liberarRecepcionFacturaProveedor(recepcionProveedor);
/* 1728:     */   }
/* 1729:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.impl.ServicioFacturaProveedorImpl
 * JD-Core Version:    0.7.0.1
 */