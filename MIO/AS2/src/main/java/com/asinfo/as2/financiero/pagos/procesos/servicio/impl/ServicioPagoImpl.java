/*    1:     */ package com.asinfo.as2.financiero.pagos.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*    4:     */ import com.asinfo.as2.clases.PagoAnticipoCheque;
/*    5:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    6:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioVerificadorCompras;
/*    7:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    8:     */ import com.asinfo.as2.dao.CuentaContableDao;
/*    9:     */ import com.asinfo.as2.dao.CuentaPorPagarDao;
/*   10:     */ import com.asinfo.as2.dao.DetalleFormaPagoDao;
/*   11:     */ import com.asinfo.as2.dao.DetallePagoDao;
/*   12:     */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*   13:     */ import com.asinfo.as2.dao.MovimientoBancarioDao;
/*   14:     */ import com.asinfo.as2.dao.OrdenPagoProveedorDao;
/*   15:     */ import com.asinfo.as2.dao.PagoDao;
/*   16:     */ import com.asinfo.as2.dao.sri.FacturaProveedorSRIDao;
/*   17:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   18:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   19:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   20:     */ import com.asinfo.as2.entities.Asiento;
/*   21:     */ import com.asinfo.as2.entities.Contacto;
/*   22:     */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   23:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   24:     */ import com.asinfo.as2.entities.CuentaPorPagar;
/*   25:     */ import com.asinfo.as2.entities.DetalleAsiento;
/*   26:     */ import com.asinfo.as2.entities.DetalleFormaPago;
/*   27:     */ import com.asinfo.as2.entities.DetalleOrdenPagoProveedor;
/*   28:     */ import com.asinfo.as2.entities.DetallePago;
/*   29:     */ import com.asinfo.as2.entities.Documento;
/*   30:     */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*   31:     */ import com.asinfo.as2.entities.Empresa;
/*   32:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   33:     */ import com.asinfo.as2.entities.FormaPago;
/*   34:     */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*   35:     */ import com.asinfo.as2.entities.OrdenPagoProveedor;
/*   36:     */ import com.asinfo.as2.entities.Organizacion;
/*   37:     */ import com.asinfo.as2.entities.Pago;
/*   38:     */ import com.asinfo.as2.entities.Sucursal;
/*   39:     */ import com.asinfo.as2.entities.TipoContacto;
/*   40:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   41:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   42:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   43:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   44:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   45:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   46:     */ import com.asinfo.as2.finaciero.pagos.reportes.ReportePagoBean;
/*   47:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*   48:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*   49:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*   50:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   51:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*   52:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   53:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   54:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioOrdenPagoProveedor;
/*   55:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/*   56:     */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReportePagoProveedor;
/*   57:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*   58:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*   59:     */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*   60:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   61:     */ import com.asinfo.as2.util.AppUtil;
/*   62:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   63:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   64:     */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   65:     */ import java.io.IOException;
/*   66:     */ import java.io.InputStream;
/*   67:     */ import java.io.PrintStream;
/*   68:     */ import java.math.BigDecimal;
/*   69:     */ import java.math.RoundingMode;
/*   70:     */ import java.text.SimpleDateFormat;
/*   71:     */ import java.util.ArrayList;
/*   72:     */ import java.util.Collection;
/*   73:     */ import java.util.Collections;
/*   74:     */ import java.util.Comparator;
/*   75:     */ import java.util.Date;
/*   76:     */ import java.util.HashMap;
/*   77:     */ import java.util.HashSet;
/*   78:     */ import java.util.Iterator;
/*   79:     */ import java.util.List;
/*   80:     */ import java.util.Map;
/*   81:     */ import java.util.Set;
/*   82:     */ import javax.ejb.EJB;
/*   83:     */ import javax.ejb.SessionContext;
/*   84:     */ import javax.ejb.Stateless;
/*   85:     */ import javax.ejb.TransactionAttribute;
/*   86:     */ import javax.ejb.TransactionAttributeType;
/*   87:     */ import javax.ejb.TransactionManagement;
/*   88:     */ import javax.ejb.TransactionManagementType;
/*   89:     */ import net.sf.jasperreports.engine.JRDataSource;
/*   90:     */ import org.apache.log4j.Logger;
/*   91:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*   92:     */ 
/*   93:     */ @Stateless
/*   94:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*   95:     */ public class ServicioPagoImpl
/*   96:     */   extends AbstractServicioAS2Financiero
/*   97:     */   implements ServicioPago
/*   98:     */ {
/*   99:     */   private static final long serialVersionUID = 1L;
/*  100:     */   @EJB
/*  101:     */   private transient PagoDao pagoDao;
/*  102:     */   @EJB
/*  103:     */   private transient CuentaContableDao cuentaContableDao;
/*  104:     */   @EJB
/*  105:     */   private transient FacturaProveedorDao facturaProveedorDao;
/*  106:     */   @EJB
/*  107:     */   private transient DetallePagoDao detallePagoDao;
/*  108:     */   @EJB
/*  109:     */   private transient ServicioPeriodo servicioPeriodo;
/*  110:     */   @EJB
/*  111:     */   private transient ServicioSecuencia servicioSecuencia;
/*  112:     */   @EJB
/*  113:     */   private transient DetalleFormaPagoDao detalleFormaPagoDao;
/*  114:     */   @EJB
/*  115:     */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  116:     */   @EJB
/*  117:     */   private transient ServicioVerificadorCompras servicioVerificadorCompras;
/*  118:     */   @EJB
/*  119:     */   private ServicioVerificadorInventario servicioVerificadorInventario;
/*  120:     */   @EJB
/*  121:     */   private transient ServicioDocumento servicioDocumento;
/*  122:     */   @EJB
/*  123:     */   private transient ServicioEmpresa servicioEmpresa;
/*  124:     */   @EJB
/*  125:     */   private transient FacturaProveedorSRIDao facturaProveedorSRIDao;
/*  126:     */   @EJB
/*  127:     */   private transient ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  128:     */   @EJB
/*  129:     */   private transient ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  130:     */   @EJB
/*  131:     */   private transient ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  132:     */   @EJB
/*  133:     */   private transient ServicioOrganizacion servicioOrganizacion;
/*  134:     */   @EJB
/*  135:     */   private transient ServicioEnvioEmail servicioEnvioEmail;
/*  136:     */   @EJB
/*  137:     */   private transient ServicioReportePagoProveedor servicioReportePagoProveedor;
/*  138:     */   @EJB
/*  139:     */   private transient OrdenPagoProveedorDao ordenPagoProveedorDao;
/*  140:     */   @EJB
/*  141:     */   private transient ServicioOrdenPagoProveedor servicioOrdenPagoProveedor;
/*  142:     */   @EJB
/*  143:     */   private transient ServicioGenerico<DetalleOrdenPagoProveedor> detalleOrdenPagoProveedorDao;
/*  144:     */   @EJB
/*  145:     */   private transient MovimientoBancarioDao movimientoBancario;
/*  146:     */   @EJB
/*  147:     */   private transient CuentaPorPagarDao cuentaPorPagarDao;
/*  148:     */   
/*  149:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  150:     */   public void guardar(Pago pago)
/*  151:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  152:     */   {
/*  153: 182 */     guardar(pago, null);
/*  154:     */   }
/*  155:     */   
/*  156:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  157:     */   public void guardar(Pago pago, String nombreUsuario)
/*  158:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  159:     */   {
/*  160:     */     try
/*  161:     */     {
/*  162: 190 */       if (!pago.isIndicadorRetencion())
/*  163:     */       {
/*  164: 192 */         this.servicioVerificadorInventario.cantidadDetalle(pago.getListaDetallePago());
/*  165:     */         
/*  166: 194 */         this.servicioVerificadorInventario.verificarTotalDetalle(pago.getListaDetallePago());
/*  167:     */       }
/*  168: 196 */       validar(pago);
/*  169: 197 */       validarValoresOrdenPagoProveedor(pago);
/*  170: 200 */       for (DetalleFormaPago detalleFormaPago : pago.getListaDetalleFormaPago()) {
/*  171: 201 */         if ((!detalleFormaPago.isEliminado()) && (detalleFormaPago.getFormaPago().isIndicadorCheque()))
/*  172:     */         {
/*  173: 202 */           pago.setIndicadorTieneCheques(true);
/*  174: 203 */           break;
/*  175:     */         }
/*  176:     */       }
/*  177: 209 */       if (!ParametrosSistema.getIndicadorAprobarPagos(pago.getIdOrganizacion()).booleanValue()) {
/*  178: 210 */         pago.setEstado(Estado.CONTABILIZADO);
/*  179:     */       }
/*  180: 213 */       if (pago.getPagoCash() != null) {
/*  181: 214 */         pago.setEstado(Estado.CONTABILIZADO);
/*  182:     */       }
/*  183: 217 */       if (pago.getEstado().equals(Estado.CONTABILIZADO))
/*  184:     */       {
/*  185: 218 */         if (pago.getAsiento() != null) {
/*  186: 220 */           this.servicioVerificadorCompras.actualizarCuentaPorPagar(pago, true);
/*  187:     */         }
/*  188: 223 */         this.servicioVerificadorCompras.actualizarCuentaPorPagar(pago, false);
/*  189:     */       }
/*  190: 226 */       cargarSecuencia(pago);
/*  191:     */       
/*  192:     */ 
/*  193: 229 */       Object listaIdOrdenPagoProveedor = new HashSet();
/*  194: 230 */       Map<Integer, CuentaPorPagar> hashCuentaPorPagar = new HashMap();
/*  195: 231 */       for (DetallePago detallePago : pago.getListaDetallePago())
/*  196:     */       {
/*  197: 232 */         if (detallePago.getValor().compareTo(BigDecimal.ZERO) == 0)
/*  198:     */         {
/*  199: 233 */           if (detallePago.getPago().getIdPago() > 0) {
/*  200: 234 */             if (!pago.getEstado().equals(Estado.CONTABILIZADO)) {
/*  201: 235 */               this.pagoDao.actualizarIndicadorBloqueadoPagoPorFactura(detallePago.getCuentaPorPagar().getIdCuentaPorPagar(), true);
/*  202:     */             } else {
/*  203: 237 */               this.pagoDao.actualizarIndicadorBloqueadoPagoPorFactura(detallePago.getCuentaPorPagar().getIdCuentaPorPagar(), false);
/*  204:     */             }
/*  205:     */           }
/*  206: 240 */           detallePago.setEliminado(true);
/*  207:     */         }
/*  208: 243 */         else if ((!detallePago.isEliminado()) && (detallePago.getDetalleOrdenPagoProveedor() != null))
/*  209:     */         {
/*  210: 244 */           detallePago.getDetalleOrdenPagoProveedor().setIndicadorPagado(true);
/*  211: 245 */           detallePago.getDetalleOrdenPagoProveedor().setValorPagado(detallePago.getValor());
/*  212: 246 */           ((Set)listaIdOrdenPagoProveedor).add(Integer.valueOf(detallePago.getDetalleOrdenPagoProveedor().getOrdenPagoProveedor().getId()));
/*  213: 247 */           if (detallePago.getDetalleOrdenPagoProveedor().getCuentaPorPagar() != null)
/*  214:     */           {
/*  215: 248 */             CuentaPorPagar cuentaPorPagarDOPP = detallePago.getDetalleOrdenPagoProveedor().getCuentaPorPagar();
/*  216: 249 */             hashCuentaPorPagar.put(Integer.valueOf(cuentaPorPagarDOPP.getId()), cuentaPorPagarDOPP);
/*  217:     */           }
/*  218: 251 */           this.detalleOrdenPagoProveedorDao.guardar(detallePago.getDetalleOrdenPagoProveedor());
/*  219:     */         }
/*  220: 255 */         this.detallePagoDao.guardar(detallePago);
/*  221:     */       }
/*  222: 259 */       for (DetalleFormaPago detalleFormaPago : pago.getListaDetalleFormaPago())
/*  223:     */       {
/*  224: 260 */         if ((detalleFormaPago.getCuentaBancariaOrganizacion() != null) && (detalleFormaPago.getCuentaBancariaOrganizacion().getId() == 0)) {
/*  225: 261 */           detalleFormaPago.setCuentaBancariaOrganizacion(null);
/*  226:     */         }
/*  227: 264 */         if (detalleFormaPago.isEliminado()) {
/*  228: 265 */           detalleFormaPago.setSecuencia(null);
/*  229:     */         }
/*  230: 268 */         this.detalleFormaPagoDao.guardar(detalleFormaPago);
/*  231:     */       }
/*  232: 272 */       if (pago.getId() == 0) {
/*  233: 274 */         actualizarDescripcionPago(pago);
/*  234:     */       }
/*  235: 278 */       this.pagoDao.guardar(pago);
/*  236: 280 */       for (DetalleFormaPago detalleFormaPago : pago.getListaDetalleFormaPago()) {
/*  237: 281 */         if ((!detalleFormaPago.isEliminado()) && (detalleFormaPago.getSecuencia() != null) && (pago.getEstado().equals(Estado.CONTABILIZADO))) {
/*  238: 283 */           this.servicioSecuencia.actualizarSecuencia(detalleFormaPago.getSecuencia(), detalleFormaPago.getDocumentoReferencia());
/*  239:     */         }
/*  240:     */       }
/*  241: 287 */       if (pago.getEstado().equals(Estado.ELABORADO)) {
/*  242: 288 */         actualizaIndicadorBloqueadoPago(pago.getIdPago(), true, pago.isIndicadorEdicionBloqueo());
/*  243:     */       }
/*  244: 291 */       if (pago.getEstado().equals(Estado.CONTABILIZADO)) {
/*  245: 293 */         contabilizar(pago);
/*  246:     */       }
/*  247: 295 */       for (Integer idOPP : (Set)listaIdOrdenPagoProveedor) {
/*  248: 296 */         this.ordenPagoProveedorDao.cerrarOrdenPagoProveedor(idOPP, nombreUsuario);
/*  249:     */       }
/*  250: 298 */       this.servicioOrdenPagoProveedor.liberarCuentasPorPagar(hashCuentaPorPagar);
/*  251:     */     }
/*  252:     */     catch (ExcepcionAS2Financiero e)
/*  253:     */     {
/*  254: 300 */       this.context.setRollbackOnly();
/*  255: 301 */       e.printStackTrace();
/*  256: 302 */       throw e;
/*  257:     */     }
/*  258:     */     catch (ExcepcionAS2 e)
/*  259:     */     {
/*  260: 304 */       this.context.setRollbackOnly();
/*  261: 305 */       e.printStackTrace();
/*  262: 306 */       throw e;
/*  263:     */     }
/*  264:     */     catch (Exception e)
/*  265:     */     {
/*  266: 308 */       this.context.setRollbackOnly();
/*  267: 309 */       e.printStackTrace();
/*  268: 310 */       throw new ExcepcionAS2(e);
/*  269:     */     }
/*  270:     */   }
/*  271:     */   
/*  272:     */   private void validarValoresOrdenPagoProveedor(Pago pago)
/*  273:     */     throws AS2Exception
/*  274:     */   {
/*  275: 315 */     for (DetallePago detallePago : pago.getListaDetallePago())
/*  276:     */     {
/*  277: 316 */       DetalleOrdenPagoProveedor detalleOPP = detallePago.getDetalleOrdenPagoProveedor();
/*  278: 318 */       if ((!detallePago.isEliminado()) && (detalleOPP != null) && (detallePago.getValor().compareTo(detalleOPP.getValorAprobado()) == 1)) {
/*  279: 320 */         throw new AS2Exception("msg_error_valor_a_liquidar_mayor_al_aprobado", new String[] {"\n" + detallePago.getValor() + " > " + detalleOPP.getValorAprobado().toString() });
/*  280:     */       }
/*  281:     */     }
/*  282:     */   }
/*  283:     */   
/*  284:     */   public void eliminar(Pago pago)
/*  285:     */   {
/*  286: 333 */     this.pagoDao.eliminar(pago);
/*  287:     */   }
/*  288:     */   
/*  289:     */   public Pago buscarPorId(Integer id)
/*  290:     */   {
/*  291: 344 */     return (Pago)this.pagoDao.buscarPorId(id);
/*  292:     */   }
/*  293:     */   
/*  294:     */   public Pago cargarDetalle(int idPago)
/*  295:     */   {
/*  296: 354 */     return this.pagoDao.cargarDetalle(idPago);
/*  297:     */   }
/*  298:     */   
/*  299:     */   public void validar(Pago pago)
/*  300:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  301:     */   {
/*  302: 358 */     BigDecimal valorFormaPago = BigDecimal.ZERO;
/*  303: 359 */     BigDecimal valorFacturas = BigDecimal.ZERO;
/*  304: 360 */     BigDecimal valorPago = pago.getValor();
/*  305:     */     
/*  306:     */ 
/*  307: 363 */     this.servicioPeriodo.buscarPorFecha(pago.getFecha(), pago.getIdOrganizacion(), pago.getDocumento().getDocumentoBase());
/*  308: 366 */     for (DetalleFormaPago detalleFormaPago : pago.getListaDetalleFormaPago())
/*  309:     */     {
/*  310: 367 */       if ((detalleFormaPago.getFechaPosfechado() != null) && 
/*  311: 368 */         (pago.getFecha().compareTo(detalleFormaPago.getFechaPosfechado()) > 0)) {
/*  312: 369 */         throw new ExcepcionAS2Financiero("msg_error_fecha_pago_fecha_posfechado");
/*  313:     */       }
/*  314: 372 */       if (!detalleFormaPago.isEliminado()) {
/*  315: 373 */         valorFormaPago = valorFormaPago.add(detalleFormaPago.getValor());
/*  316:     */       }
/*  317:     */     }
/*  318: 377 */     if (valorFormaPago.compareTo(valorPago) != 0) {
/*  319: 378 */       throw new ExcepcionAS2Financiero("msg_info_pago_0004");
/*  320:     */     }
/*  321: 382 */     if (!pago.getIndicadorPagoLiquidacion()) {
/*  322: 383 */       for (DetallePago detallePago : pago.getListaDetallePago()) {
/*  323: 384 */         if ((detallePago.getValor().compareTo(BigDecimal.ZERO) == 1) && 
/*  324: 385 */           (detallePago.getCuentaPorPagar() != null) && (detallePago.getValor().compareTo(detallePago.getCuentaPorPagar().getSaldo()
/*  325: 386 */           .add(ParametrosSistema.getValorToleranciaCompraVenta(detallePago.getIdOrganizacion()))) == 1)) {
/*  326: 391 */           throw new ExcepcionAS2Financiero("msg_info_pago_0002", " " + (detallePago.getCuentaPorPagar().getFacturaProveedor() != null ? " " : detallePago.getCuentaPorPagar().getFacturaProveedor().getFacturaProveedorSRI() != null ? detallePago.getCuentaPorPagar().getFacturaProveedor().getFacturaProveedorSRI().getNumero() : " ") + " | " + detallePago.getValor() + " <> " + detallePago.getCuentaPorPagar().getSaldo());
/*  327:     */         }
/*  328:     */       }
/*  329:     */     }
/*  330: 402 */     if (pago.getIndicadorPagoLiquidacion()) {
/*  331: 403 */       for (DetallePago detallePago : pago.getListaDetallePago())
/*  332:     */       {
/*  333: 404 */         if (detallePago.getValor().compareTo(BigDecimal.ZERO) == 1)
/*  334:     */         {
/*  335: 405 */           if (FuncionesUtiles.compararFechas(pago.getFecha(), detallePago.getCuentaPorPagar().getFacturaProveedor().getFecha())) {
/*  336: 408 */             throw new ExcepcionAS2Financiero("msg_error_fecha_pago_factura", detallePago.getCuentaPorPagar().getFacturaProveedor().getNumero() + " | " + detallePago.getCuentaPorPagar().getFacturaProveedor().getFacturaProveedorSRI().getNumero());
/*  337:     */           }
/*  338: 410 */           if ((detallePago.getCuentaPorPagar() != null) && 
/*  339: 411 */             (detallePago.getValor().compareTo(detallePago.getCuentaPorPagar().getSaldo()) == 1)) {
/*  340: 412 */             throw new ExcepcionAS2Financiero("msg_info_pago_0002");
/*  341:     */           }
/*  342:     */         }
/*  343: 416 */         if (!detallePago.isEliminado()) {
/*  344: 417 */           valorFacturas = valorFacturas.add(detallePago.getValor());
/*  345:     */         }
/*  346:     */       }
/*  347:     */     } else {
/*  348: 422 */       for (DetallePago detallePago : pago.getListaDetallePago())
/*  349:     */       {
/*  350: 423 */         if ((detallePago.getValor().compareTo(BigDecimal.ZERO) != 0) && 
/*  351: 424 */           (FuncionesUtiles.compararFechas(pago.getFecha(), detallePago.getCuentaPorPagar().getFacturaProveedor().getFecha()))) {
/*  352: 430 */           throw new ExcepcionAS2Financiero("msg_error_fecha_pago_factura", detallePago.getCuentaPorPagar().getFacturaProveedor().getNumero() + " | " + (detallePago.getCuentaPorPagar().getFacturaProveedor().getFacturaProveedorSRI() != null ? detallePago.getCuentaPorPagar().getFacturaProveedor().getFacturaProveedorSRI().getNumero() : detallePago.getCuentaPorPagar().getFacturaProveedor().getNumero()));
/*  353:     */         }
/*  354: 434 */         if (!detallePago.isEliminado()) {
/*  355: 435 */           valorFacturas = valorFacturas.add(detallePago.getValor());
/*  356:     */         }
/*  357:     */       }
/*  358:     */     }
/*  359:     */   }
/*  360:     */   
/*  361:     */   private void cargarSecuencia(Pago pago)
/*  362:     */     throws ExcepcionAS2
/*  363:     */   {
/*  364: 453 */     if (pago.getNumero().equals(""))
/*  365:     */     {
/*  366: 454 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(pago.getDocumento().getIdDocumento(), pago.getFecha());
/*  367: 455 */       pago.setNumero(numero);
/*  368:     */     }
/*  369:     */   }
/*  370:     */   
/*  371:     */   public boolean validaAsientoPago(Pago pago)
/*  372:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  373:     */   {
/*  374: 466 */     return (pago.getAsiento().getEstado() != Estado.APROBADO) || (pago.getAsiento().getEstado() != Estado.ANULADO);
/*  375:     */   }
/*  376:     */   
/*  377:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  378:     */   public void anularPago(Pago pago)
/*  379:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  380:     */   {
/*  381: 477 */     anularPago(pago, true);
/*  382:     */   }
/*  383:     */   
/*  384:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  385:     */   public void anularPago(Pago pago, boolean verificaPagoCash)
/*  386:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  387:     */   {
/*  388:     */     try
/*  389:     */     {
/*  390: 485 */       pago = cargarDetalle(pago.getIdPago());
/*  391:     */       
/*  392: 487 */       esEditable(pago, true, verificaPagoCash);
/*  393: 488 */       if (pago.getEstado() == Estado.CONTABILIZADO) {
/*  394: 489 */         this.servicioVerificadorCompras.actualizarCuentaPorPagar(pago, true);
/*  395:     */       }
/*  396: 491 */       actualizarEstado(pago.getId(), Estado.ANULADO);
/*  397: 493 */       if (pago.getAsiento() != null)
/*  398:     */       {
/*  399: 494 */         pago.getAsiento().setIndicadorAutomatico(false);
/*  400: 495 */         this.servicioAsiento.anular(pago.getAsiento());
/*  401:     */       }
/*  402: 498 */       actualizaIndicadorBloqueadoPago(pago.getIdPago(), false, false);
/*  403:     */       
/*  404:     */ 
/*  405: 501 */       this.servicioOrdenPagoProveedor.reversarOrdenAlAnularPago(pago);
/*  406:     */     }
/*  407:     */     catch (ExcepcionAS2Financiero e)
/*  408:     */     {
/*  409: 503 */       this.context.setRollbackOnly();
/*  410: 504 */       throw e;
/*  411:     */     }
/*  412:     */     catch (Exception e)
/*  413:     */     {
/*  414: 506 */       this.context.setRollbackOnly();
/*  415: 507 */       throw new ExcepcionAS2(e);
/*  416:     */     }
/*  417:     */   }
/*  418:     */   
/*  419:     */   public List<Pago> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  420:     */   {
/*  421: 519 */     return this.pagoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  422:     */   }
/*  423:     */   
/*  424:     */   public int contarPorCriterio(Map<String, String> filters)
/*  425:     */   {
/*  426: 529 */     return this.pagoDao.contarPorCriterio(filters);
/*  427:     */   }
/*  428:     */   
/*  429:     */   public void contabilizar(Pago pago)
/*  430:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  431:     */   {
/*  432: 539 */     if (pago.getDocumento().isIndicadorContabilizar())
/*  433:     */     {
/*  434: 540 */       Date fechaContabilizacion = pago.getFecha();
/*  435: 541 */       int idPago = pago.getIdPago();
/*  436:     */       Asiento asiento;
/*  437:     */       Asiento asiento;
/*  438: 544 */       if (pago.getAsiento() != null)
/*  439:     */       {
/*  440: 545 */         asiento = this.servicioAsiento.cargarDetalle(pago.getAsiento().getId());
/*  441:     */       }
/*  442:     */       else
/*  443:     */       {
/*  444: 548 */         asiento = new Asiento();
/*  445: 549 */         asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  446: 550 */         asiento.setSucursal(AppUtil.getSucursal());
/*  447: 551 */         asiento.setTipoAsiento(pago.getDocumento().getTipoAsiento());
/*  448: 552 */         if ((null != pago.getNumeroEgreso()) && (!pago.getNumeroEgreso().isEmpty())) {
/*  449: 553 */           asiento.setNumero(pago.getNumeroEgreso());
/*  450:     */         }
/*  451:     */       }
/*  452: 557 */       String concepto = "";
/*  453: 558 */       concepto = pago.getDocumento().getNombre().trim() + " #" + pago.getNumero() + " " + pago.getDescripcion();
/*  454:     */       
/*  455: 560 */       asiento.setConcepto(concepto);
/*  456: 561 */       asiento.setFecha(fechaContabilizacion);
/*  457: 563 */       for (DetalleFormaPago dfp : pago.getListaDetalleFormaPago())
/*  458:     */       {
/*  459: 564 */         if (dfp.getFechaPosfechado() != null) {
/*  460: 565 */           asiento.setFechaChequePosfechado(dfp.getFechaPosfechado());
/*  461:     */         }
/*  462: 567 */         if ((dfp.getDescripcion() != null) && (!dfp.getDescripcion().isEmpty())) {
/*  463: 568 */           asiento.setNotaPosfechado(dfp.getDescripcion());
/*  464:     */         }
/*  465:     */       }
/*  466: 572 */       Object listaDA = new ArrayList();
/*  467: 573 */       ((List)listaDA).addAll(this.pagoDao.getFormaPagoBancosIC(idPago));
/*  468:     */       
/*  469:     */ 
/*  470: 576 */       super.generarAsiento(asiento, (List)listaDA, pago.getDocumento());
/*  471:     */       
/*  472: 578 */       List<Integer> list = new ArrayList();
/*  473: 579 */       List<DetalleInterfazContableProceso> lista = new ArrayList();
/*  474: 580 */       DocumentoContabilizacion documentoContabilizacion = new DocumentoContabilizacion();
/*  475:     */       List<CriterioDistribucion> listaCriterioDistribucion;
/*  476:     */       List<CriterioDistribucion> listaCriterioDistribucion;
/*  477: 582 */       if (pago.isIndicadorRetencionAsumida())
/*  478:     */       {
/*  479: 584 */         List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(pago.getIdOrganizacion(), DocumentoBase.RETENCION_PROVEEDOR);
/*  480: 585 */         documentoContabilizacion = (DocumentoContabilizacion)listaDocumentoContabilizacion.get(0);
/*  481:     */         
/*  482: 587 */         listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(pago.getIdOrganizacion(), DocumentoBase.RETENCION_PROVEEDOR);
/*  483:     */       }
/*  484:     */       else
/*  485:     */       {
/*  486: 591 */         List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(pago.getIdOrganizacion(), DocumentoBase.FACTURA_PROVEEDOR);
/*  487:     */         
/*  488: 593 */         listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(pago.getIdOrganizacion(), DocumentoBase.FACTURA_PROVEEDOR);
/*  489: 596 */         for (DocumentoContabilizacion dc : listaDocumentoContabilizacion) {
/*  490: 597 */           if (dc.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.CXP_PROVEEDOR)) {
/*  491: 598 */             documentoContabilizacion = dc;
/*  492:     */           }
/*  493:     */         }
/*  494:     */       }
/*  495: 603 */       BigDecimal valorFactura = BigDecimal.ZERO;
/*  496: 604 */       Object listaTmp = new ArrayList();
/*  497: 605 */       Map<Integer, DetallePago> mapaCobroFactura = new HashMap();
/*  498: 606 */       for (DetallePago dp : pago.getListaDetallePago())
/*  499:     */       {
/*  500: 608 */         clave = Integer.valueOf(dp.getCuentaPorPagar().getFacturaProveedor().getId());
/*  501: 609 */         DetallePago d = (DetallePago)mapaCobroFactura.get(clave);
/*  502: 610 */         if (d == null)
/*  503:     */         {
/*  504: 611 */           DetallePago dpnew = new DetallePago();
/*  505: 612 */           dpnew.setValor(dp.getValor());
/*  506: 613 */           dpnew.setCuentaPorPagar(dp.getCuentaPorPagar());
/*  507: 614 */           mapaCobroFactura.put(clave, dpnew);
/*  508:     */         }
/*  509:     */         else
/*  510:     */         {
/*  511: 616 */           d.setValor(d.getValor().add(dp.getValor()));
/*  512:     */         }
/*  513:     */       }
/*  514:     */       Integer clave;
/*  515: 621 */       for (??? = mapaCobroFactura.values().iterator(); ???.hasNext();)
/*  516:     */       {
/*  517: 621 */         dc = (DetallePago)???.next();
/*  518: 622 */         if (dc.getValor().compareTo(BigDecimal.ZERO) != 0)
/*  519:     */         {
/*  520: 623 */           list = new ArrayList();
/*  521: 624 */           list.add(Integer.valueOf(dc.getCuentaPorPagar().getFacturaProveedor().getIdFacturaProveedor()));
/*  522: 625 */           listaTmp = this.facturaProveedorDao.getInterfazFacturaProveedorDimensiones(list, ProcesoContabilizacionEnum.CXP_PROVEEDOR);
/*  523: 626 */           valorFactura = BigDecimal.ZERO;
/*  524: 627 */           for (DetalleInterfazContableProceso detalleInterfazContableProceso : (List)listaTmp) {
/*  525: 628 */             valorFactura = valorFactura.add(detalleInterfazContableProceso.getValor());
/*  526:     */           }
/*  527: 630 */           for (DetalleInterfazContableProceso detalleInterfazContableProceso : (List)listaTmp) {
/*  528: 631 */             detalleInterfazContableProceso.setValor(dc
/*  529: 632 */               .getValor().multiply(detalleInterfazContableProceso.getValor()).divide(valorFactura, 24, RoundingMode.HALF_UP));
/*  530:     */           }
/*  531: 634 */           lista.addAll((Collection)listaTmp);
/*  532:     */         }
/*  533:     */       }
/*  534:     */       DetallePago dc;
/*  535: 638 */       Object listaDetalleAsiento = this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, 
/*  536: 639 */         !pago.isIndicadorRetencionAsumida());
/*  537: 656 */       for (DetalleAsiento detalleAsiento : (List)listaDetalleAsiento)
/*  538:     */       {
/*  539: 657 */         String descripcion = detalleAsiento.getDescripcion();
/*  540: 658 */         detalleAsiento.setDescripcion(descripcion.length() > 200 ? descripcion.substring(0, 200) : descripcion);
/*  541:     */       }
/*  542: 661 */       asiento.getListaDetalleAsiento().addAll((Collection)listaDetalleAsiento);
/*  543:     */       
/*  544:     */ 
/*  545: 664 */       this.servicioAsiento.guardar(asiento);
/*  546:     */       
/*  547:     */ 
/*  548:     */ 
/*  549:     */ 
/*  550: 669 */       pago.setFechaContabilizacion(fechaContabilizacion);
/*  551: 676 */       if (pago.getAsiento() == null)
/*  552:     */       {
/*  553: 677 */         pago.setAsiento(asiento);
/*  554: 678 */         this.pagoDao.guardar(pago);
/*  555:     */       }
/*  556:     */       try
/*  557:     */       {
/*  558: 681 */         enviarEmail(pago);
/*  559:     */       }
/*  560:     */       catch (Exception e)
/*  561:     */       {
/*  562: 683 */         System.out.println("Error al enviar mail");
/*  563: 684 */         e.printStackTrace();
/*  564:     */       }
/*  565:     */     }
/*  566:     */   }
/*  567:     */   
/*  568:     */   public void cargarFacturasPendientes(Pago pago, boolean filtrarPorSucursal, boolean indicadorOrdenPagoProveedor)
/*  569:     */   {
/*  570: 696 */     cargarFacturasPendientes(pago, 0, filtrarPorSucursal, indicadorOrdenPagoProveedor);
/*  571:     */   }
/*  572:     */   
/*  573:     */   public void cargarFacturasPendientes(Pago pago, int idFacturaProveedor, boolean filtrarPorSucursal, boolean indicadorOrdenPagoProveedor)
/*  574:     */   {
/*  575: 706 */     Map<Integer, DetalleOrdenPagoProveedor> mapaValorAprobado = new HashMap();
/*  576: 707 */     for (DetallePago detallePago : pago.getListaDetallePago())
/*  577:     */     {
/*  578: 708 */       detallePago.setValor(BigDecimal.ZERO);
/*  579: 709 */       detallePago.setEliminado(true);
/*  580:     */     }
/*  581: 711 */     Object listaFacturasPendientes = null;
/*  582: 712 */     if (pago.getIndicadorPagoLiquidacion())
/*  583:     */     {
/*  584: 714 */       BigDecimal tolerancia = ParametrosSistema.getValorToleranciaCompraVenta(AppUtil.getOrganizacion().getIdOrganizacion());
/*  585: 715 */       listaFacturasPendientes = this.servicioFacturaProveedor.obtenerFacturasPendientesLiquidacionCuentaPorPagar(pago, idFacturaProveedor, tolerancia, filtrarPorSucursal);
/*  586:     */     }
/*  587:     */     else
/*  588:     */     {
/*  589: 718 */       int idSucursal = filtrarPorSucursal ? pago.getSucursal().getId() : 0;
/*  590: 719 */       if (!indicadorOrdenPagoProveedor)
/*  591:     */       {
/*  592: 720 */         listaFacturasPendientes = this.servicioFacturaProveedor.obtenerFacturasPendientes(pago.getIdOrganizacion(), pago.getEmpresa().getId(), idFacturaProveedor, null, idSucursal);
/*  593:     */       }
/*  594:     */       else
/*  595:     */       {
/*  596: 724 */         listaFacturasPendientes = new ArrayList();
/*  597:     */         
/*  598: 726 */         List<DetalleOrdenPagoProveedor> listaDetalleOrdenPago = this.ordenPagoProveedorDao.buscarDetallesPendientesPago(pago.getIdOrganizacion(), null, pago
/*  599: 727 */           .getFecha(), Integer.valueOf(idSucursal), Integer.valueOf(pago.getEmpresa().getId()), null, null, null);
/*  600: 728 */         for (DetalleOrdenPagoProveedor detalleOrdenPagoProveedor : listaDetalleOrdenPago) {
/*  601: 729 */           if (detalleOrdenPagoProveedor.getCuentaPorPagar() != null)
/*  602:     */           {
/*  603: 730 */             ((List)listaFacturasPendientes).add(detalleOrdenPagoProveedor.getCuentaPorPagar());
/*  604: 731 */             mapaValorAprobado.put(Integer.valueOf(detalleOrdenPagoProveedor.getCuentaPorPagar().getId()), detalleOrdenPagoProveedor);
/*  605:     */           }
/*  606:     */         }
/*  607:     */       }
/*  608:     */     }
/*  609: 737 */     BigDecimal valorPago = pago.getValor();
/*  610: 738 */     int i = 0;
/*  611: 739 */     for (CuentaPorPagar cxp : (List)listaFacturasPendientes)
/*  612:     */     {
/*  613:     */       DetallePago detallePago;
/*  614:     */       DetallePago detallePago;
/*  615: 742 */       if (i < pago.getListaDetallePago().size())
/*  616:     */       {
/*  617: 743 */         detallePago = (DetallePago)pago.getListaDetallePago().get(i);
/*  618:     */       }
/*  619:     */       else
/*  620:     */       {
/*  621: 745 */         detallePago = new DetallePago();
/*  622: 746 */         pago.getListaDetallePago().add(detallePago);
/*  623:     */       }
/*  624: 749 */       detallePago.setPago(pago);
/*  625: 750 */       detallePago.setEliminado(false);
/*  626: 751 */       detallePago.setCuentaPorPagar(cxp);
/*  627: 752 */       BigDecimal saldoCuentaPorCobrar = cxp.getSaldo();
/*  628: 753 */       BigDecimal valor = BigDecimal.ZERO;
/*  629: 755 */       if (pago.getIndicadorPagoLiquidacion())
/*  630:     */       {
/*  631: 756 */         valor = saldoCuentaPorCobrar;
/*  632:     */       }
/*  633: 758 */       else if (indicadorOrdenPagoProveedor)
/*  634:     */       {
/*  635: 759 */         BigDecimal valorAprobado = null;
/*  636: 760 */         DetalleOrdenPagoProveedor detalleOrdenPagoProveedor = (DetalleOrdenPagoProveedor)mapaValorAprobado.get(Integer.valueOf(cxp.getId()));
/*  637: 761 */         detallePago.setDetalleOrdenPagoProveedor(detalleOrdenPagoProveedor);
/*  638: 762 */         pago.setOrdenPagoProveedor(detalleOrdenPagoProveedor.getOrdenPagoProveedor());
/*  639: 763 */         if (detalleOrdenPagoProveedor != null) {
/*  640: 764 */           valorAprobado = detalleOrdenPagoProveedor.getValorAprobado();
/*  641:     */         }
/*  642: 766 */         if ((valorAprobado != null) && (valorAprobado.compareTo(saldoCuentaPorCobrar) <= 0)) {
/*  643: 767 */           valor = valorAprobado;
/*  644:     */         } else {
/*  645: 769 */           valor = saldoCuentaPorCobrar;
/*  646:     */         }
/*  647:     */       }
/*  648: 773 */       else if (valorPago.compareTo(saldoCuentaPorCobrar) > 0)
/*  649:     */       {
/*  650: 774 */         valor = saldoCuentaPorCobrar;
/*  651:     */       }
/*  652:     */       else
/*  653:     */       {
/*  654: 776 */         valor = valorPago;
/*  655:     */       }
/*  656: 779 */       detallePago.setValor(valor);
/*  657: 780 */       valorPago = valorPago.subtract(valor);
/*  658:     */       
/*  659: 782 */       i++;
/*  660:     */     }
/*  661: 786 */     if (pago.getIndicadorPagoLiquidacion()) {
/*  662: 787 */       pago.setValor(valorPago.multiply(new BigDecimal(-1)));
/*  663:     */     }
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void actualizarEstado(int idPago, Estado estado)
/*  667:     */   {
/*  668: 799 */     this.pagoDao.actualizaEstado(idPago, estado);
/*  669:     */   }
/*  670:     */   
/*  671:     */   public void esEditable(Pago pago, boolean anular)
/*  672:     */     throws ExcepcionAS2Financiero
/*  673:     */   {
/*  674: 809 */     esEditable(pago, anular, true);
/*  675:     */   }
/*  676:     */   
/*  677:     */   public void esEditable(Pago pago, boolean anular, boolean verificaPagoCash)
/*  678:     */     throws ExcepcionAS2Financiero
/*  679:     */   {
/*  680: 815 */     this.servicioPeriodo.buscarPorFecha(pago.getFecha(), pago.getIdOrganizacion(), pago.getDocumento().getDocumentoBase());
/*  681: 817 */     if (pago.getEstado() == Estado.ANULADO) {
/*  682: 818 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/*  683:     */     }
/*  684: 821 */     if (!anular) {
/*  685: 823 */       if ((pago.getEstado() == Estado.CONTABILIZADO) || (pago.getEstado() == Estado.APROBADO)) {
/*  686: 824 */         throw new ExcepcionAS2Financiero("msg_error_asiento_aprobado");
/*  687:     */       }
/*  688:     */     }
/*  689: 828 */     if ((verificaPagoCash) && (pago.getPagoCash() != null)) {
/*  690: 830 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/*  691:     */     }
/*  692: 832 */     if ((pago.getAsiento() != null) && (pago.getAsiento().getEstado() == Estado.REVISADO)) {
/*  693: 835 */       throw new ExcepcionAS2Financiero("msg_info_anular_estado_asiento");
/*  694:     */     }
/*  695:     */   }
/*  696:     */   
/*  697:     */   public void esEditable(Pago pago)
/*  698:     */     throws ExcepcionAS2Financiero
/*  699:     */   {
/*  700: 847 */     esEditable(pago, false);
/*  701:     */   }
/*  702:     */   
/*  703:     */   public void detach(Pago pago)
/*  704:     */   {
/*  705: 857 */     this.pagoDao.detach(pago);
/*  706:     */   }
/*  707:     */   
/*  708:     */   public void actualizaIndicadorBloqueadoPago(int idPago, boolean bloqueo, boolean edicion)
/*  709:     */     throws ExcepcionAS2Financiero
/*  710:     */   {
/*  711: 868 */     this.pagoDao.actualizaIndicadorBloqueadoPago(idPago, bloqueo, edicion);
/*  712:     */   }
/*  713:     */   
/*  714:     */   public void actualizarDescripcionPago(Pago pago)
/*  715:     */   {
/*  716: 879 */     String listadoFacturas = "";
/*  717: 880 */     String factura = "";
/*  718: 882 */     for (DetallePago detallePago : pago.getListaDetallePago()) {
/*  719: 884 */       if (!detallePago.isEliminado())
/*  720:     */       {
/*  721: 886 */         if (detallePago.getCuentaPorPagar().getFacturaProveedor().getFacturaProveedorSRI() != null)
/*  722:     */         {
/*  723: 888 */           String punto = detallePago.getCuentaPorPagar().getFacturaProveedor().getFacturaProveedorSRI().getPuntoEmision();
/*  724: 889 */           String establecimiento = detallePago.getCuentaPorPagar().getFacturaProveedor().getFacturaProveedorSRI().getEstablecimiento();
/*  725: 890 */           String numero = detallePago.getCuentaPorPagar().getFacturaProveedor().getFacturaProveedorSRI().getNumero();
/*  726: 891 */           factura = establecimiento + "-" + punto + "-" + numero;
/*  727:     */         }
/*  728:     */         else
/*  729:     */         {
/*  730: 894 */           factura = detallePago.getCuentaPorPagar().getFacturaProveedor().getNumero();
/*  731:     */         }
/*  732: 898 */         if (!listadoFacturas.contains(factura)) {
/*  733: 899 */           listadoFacturas = listadoFacturas + (listadoFacturas.length() > 0 ? ", " : "") + factura;
/*  734:     */         }
/*  735:     */       }
/*  736:     */     }
/*  737: 905 */     String descripcion = "F:/ " + listadoFacturas + " " + (pago.getDescripcion() != null ? pago.getDescripcion() : "");
/*  738:     */     
/*  739: 907 */     descripcion = descripcion.length() > 200 ? descripcion.substring(0, 200) : descripcion;
/*  740:     */     
/*  741: 909 */     pago.setDescripcion(descripcion);
/*  742:     */   }
/*  743:     */   
/*  744:     */   public void actualizarChequeEntregadoPago(int idPago, boolean indicadorEntregaCheque, String usuarioEntregaCheque, Date fechaEntregaCheque)
/*  745:     */   {
/*  746: 920 */     this.pagoDao.actualizarChequeEntregadoPago(idPago, indicadorEntregaCheque, usuarioEntregaCheque, fechaEntregaCheque);
/*  747:     */   }
/*  748:     */   
/*  749:     */   public List<PagoAnticipoCheque> getPagoConCheques()
/*  750:     */   {
/*  751: 930 */     return this.pagoDao.getPagoConCheques();
/*  752:     */   }
/*  753:     */   
/*  754:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  755:     */   public void cargarPagos(String fileName, InputStream imInputStream, int finaInicial, int idOrganizacion)
/*  756:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  757:     */   {
/*  758: 945 */     Map<String, Pago> hmPagoDeposito = new HashMap();
/*  759: 946 */     Map<String, List<CuentaPorPagar>> hmFacturaProveedor = new HashMap();
/*  760: 947 */     Map<String, CuentaBancariaOrganizacion> hmCuentaBancariaOrganizacion = new HashMap();
/*  761: 948 */     Map<String, Empresa> hmEmpresa = new HashMap();
/*  762: 949 */     Map<String, Documento> hmDocumento = new HashMap();
/*  763:     */     
/*  764:     */ 
/*  765: 952 */     BigDecimal tolerancia = ParametrosSistema.getValorToleranciaCompraVenta(AppUtil.getOrganizacion().getIdOrganizacion());
/*  766:     */     try
/*  767:     */     {
/*  768: 957 */       datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getIdOrganizacion(), fileName, imInputStream, finaInicial, 0);
/*  769:     */     }
/*  770:     */     catch (IOException e1)
/*  771:     */     {
/*  772:     */       HSSFCell[][] datos;
/*  773: 959 */       throw new ExcepcionAS2("msg_error_archivo_generado");
/*  774:     */     }
/*  775:     */     HSSFCell[][] datos;
/*  776: 976 */     FormaPago formaPago = null;
/*  777:     */     
/*  778:     */ 
/*  779: 979 */     int filaActual = finaInicial + 1;
/*  780: 980 */     int columnaActual = 0;
/*  781: 981 */     HSSFCell[] filaErronea = new HSSFCell[0];
/*  782: 983 */     for (HSSFCell[] fila : datos)
/*  783:     */     {
/*  784:     */       try
/*  785:     */       {
/*  786: 990 */         filaErronea = fila;
/*  787:     */         
/*  788: 992 */         columnaActual = 0;
/*  789: 993 */         String identificacion = fila[0].getStringCellValue().trim();
/*  790:     */         
/*  791: 995 */         columnaActual = 1;
/*  792: 996 */         String factura = fila[1].getStringCellValue().trim();
/*  793:     */         
/*  794: 998 */         columnaActual = 2;
/*  795: 999 */         Date fechaPago = fila[2].getDateCellValue();
/*  796:     */         
/*  797:1001 */         columnaActual = 3;
/*  798:1002 */         String beneficiario = fila[3].getStringCellValue();
/*  799:     */         
/*  800:1004 */         columnaActual = 4;
/*  801:1005 */         String codigoFormaPago = fila[4].getStringCellValue().trim();
/*  802:     */         
/*  803:1007 */         columnaActual = 5;
/*  804:1008 */         String cuentaContable = fila[5].getStringCellValue().trim();
/*  805:     */         
/*  806:1010 */         columnaActual = 6;
/*  807:1011 */         BigDecimal valor = BigDecimal.valueOf(fila[6].getNumericCellValue());
/*  808:1013 */         if (fila[7] == null) {
/*  809:1014 */           throw new ExcepcionAS2("msg_info_numero_deposito", " <> " + factura);
/*  810:     */         }
/*  811:1016 */         columnaActual = 7;
/*  812:1017 */         String numeroDeposito = fila[7].getStringCellValue().trim();
/*  813:     */         
/*  814:1019 */         columnaActual = 8;
/*  815:1020 */         String descripcion = fila[8].getStringCellValue().trim();
/*  816:1022 */         if (fila[9] == null) {
/*  817:1023 */           throw new ExcepcionAS2("msg_info_numero_control", " <> " + factura);
/*  818:     */         }
/*  819:1025 */         columnaActual = 9;
/*  820:1026 */         int numeroControl = (int)fila[9].getNumericCellValue();
/*  821:1028 */         if (fila[10] == null) {
/*  822:1029 */           throw new ExcepcionAS2("msg_info_validar_valor", " <> " + factura);
/*  823:     */         }
/*  824:1031 */         columnaActual = 10;
/*  825:1032 */         boolean indicadorValidarValor = fila[10].getStringCellValue().trim().equalsIgnoreCase("SI");
/*  826:     */         
/*  827:1034 */         columnaActual = 11;
/*  828:1035 */         String nombreDocumento = fila[11].getStringCellValue().trim();
/*  829:     */         
/*  830:1037 */         columnaActual = 12;
/*  831:1038 */         fila[12].setCellType(1);
/*  832:1039 */         String numeroEgreso = fila[12].getStringCellValue().trim();
/*  833:     */         
/*  834:     */ 
/*  835:     */ 
/*  836:     */ 
/*  837:1044 */         Documento documento = (Documento)hmDocumento.get(nombreDocumento);
/*  838:1045 */         if (documento == null)
/*  839:     */         {
/*  840:1046 */           Map<String, String> filters = new HashMap();
/*  841:1047 */           filters.put("documentoBase", "" + DocumentoBase.PAGO_PROVEEDOR);
/*  842:1048 */           filters.put("nombre", nombreDocumento);
/*  843:1049 */           List<Documento> listaDocumento = this.servicioDocumento.obtenerListaCombo("", false, filters);
/*  844:1050 */           if (listaDocumento.isEmpty()) {
/*  845:1051 */             throw new ExcepcionAS2("msg_configuracion_documento");
/*  846:     */           }
/*  847:1053 */           documento = (Documento)listaDocumento.get(0);
/*  848:1054 */           hmDocumento.put("nombre", documento);
/*  849:     */         }
/*  850:1058 */         Asiento asiento = new Asiento();
/*  851:1059 */         asiento.setIdOrganizacion(idOrganizacion);
/*  852:1060 */         asiento.setTipoAsiento(documento.getTipoAsiento());
/*  853:1061 */         asiento.setNumero(numeroEgreso);
/*  854:1063 */         if (this.servicioAsiento.verificarExistenciaNumero(asiento).longValue() > 0L) {
/*  855:1064 */           throw new ExcepcionAS2("msg_numero_asiento_ya_existe");
/*  856:     */         }
/*  857:     */       }
/*  858:     */       catch (ExcepcionAS2 e)
/*  859:     */       {
/*  860:1068 */         this.context.setRollbackOnly();
/*  861:1069 */         throw e;
/*  862:     */       }
/*  863:     */       catch (IllegalArgumentException e)
/*  864:     */       {
/*  865:1071 */         this.context.setRollbackOnly();
/*  866:     */         
/*  867:1073 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1) + " Dato: " + filaErronea[columnaActual].toString());
/*  868:     */       }
/*  869:     */       catch (Exception e)
/*  870:     */       {
/*  871:1075 */         this.context.setRollbackOnly();
/*  872:1076 */         e.printStackTrace();
/*  873:     */         
/*  874:1078 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1) + " Dato: " + filaErronea[columnaActual].toString());
/*  875:     */       }
/*  876:     */       try
/*  877:     */       {
/*  878:     */         Documento documento;
/*  879:     */         String numeroEgreso;
/*  880:     */         String nombreDocumento;
/*  881:     */         boolean indicadorValidarValor;
/*  882:     */         int numeroControl;
/*  883:     */         String descripcion;
/*  884:     */         String numeroDeposito;
/*  885:     */         BigDecimal valor;
/*  886:     */         String cuentaContable;
/*  887:     */         String codigoFormaPago;
/*  888:     */         String beneficiario;
/*  889:     */         Date fechaPago;
/*  890:     */         String factura;
/*  891:     */         String identificacion;
/*  892:1088 */         CuentaBancariaOrganizacion cuentaBancariaOrganizacion = (CuentaBancariaOrganizacion)hmCuentaBancariaOrganizacion.get(cuentaContable);
/*  893:     */         Map<String, String> filters;
/*  894:1089 */         if (cuentaBancariaOrganizacion == null)
/*  895:     */         {
/*  896:1090 */           filters = new HashMap();
/*  897:1091 */           filters.put("cuentaContableBanco.codigo", cuentaContable);
/*  898:     */           
/*  899:1093 */           List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("idCuentaBancariaOrganizacion", true, filters);
/*  900:1094 */           if (!listaCuentaBancariaOrganizacion.isEmpty()) {
/*  901:1095 */             cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(((CuentaBancariaOrganizacion)listaCuentaBancariaOrganizacion.get(0)).getId());
/*  902:     */           } else {
/*  903:1097 */             throw new ExcepcionAS2("msg_error_cuenta_bancaria_erronea", " " + cuentaContable);
/*  904:     */           }
/*  905:1099 */           hmCuentaBancariaOrganizacion.put(cuentaContable, cuentaBancariaOrganizacion);
/*  906:     */         }
/*  907:1105 */         formaPago = null;
/*  908:1106 */         if (cuentaBancariaOrganizacion.getListaFormaPago().isEmpty()) {
/*  909:1107 */           throw new ExcepcionAS2("msg_error_no_existe_forma_para_cuenta_bancaria", " " + cuentaContable);
/*  910:     */         }
/*  911:1109 */         for (FormaPagoCuentaBancariaOrganizacion fp : cuentaBancariaOrganizacion.getListaFormaPago()) {
/*  912:1110 */           if (fp.getFormaPago().getCodigo().equalsIgnoreCase(codigoFormaPago))
/*  913:     */           {
/*  914:1111 */             formaPago = fp.getFormaPago();
/*  915:1112 */             break;
/*  916:     */           }
/*  917:     */         }
/*  918:1117 */         if (formaPago == null) {
/*  919:1118 */           throw new ExcepcionAS2("msg_error_no_existe_forma_para_cuenta_bancaria", " " + cuentaContable);
/*  920:     */         }
/*  921:1124 */         Empresa empresa = (Empresa)hmEmpresa.get(identificacion);
/*  922:1125 */         if (empresa == null)
/*  923:     */         {
/*  924:1126 */           Map<String, String> filters = new HashMap();
/*  925:1127 */           filters.put("identificacion", identificacion);
/*  926:1128 */           filters.put("indicadorProveedor", "true");
/*  927:1129 */           empresa = this.servicioEmpresa.bucarEmpresaPorIdentificacion(filters);
/*  928:1130 */           empresa = this.servicioEmpresa.cargarDetalle(empresa);
/*  929:1131 */           hmEmpresa.put(identificacion, empresa);
/*  930:     */           
/*  931:1133 */           List<CuentaPorPagar> listaCxP = this.servicioFacturaProveedor.obtenerFacturasPendientes(idOrganizacion, empresa.getId(), 0, null);
/*  932:1134 */           hmFacturaProveedor.put(identificacion, listaCxP);
/*  933:     */         }
/*  934:1142 */         String establecimiento = factura.substring(0, 3);
/*  935:1143 */         String punto = factura.substring(4, 7);
/*  936:1144 */         String numero = factura.substring(8).replaceFirst("^0*", "");
/*  937:1145 */         String numeroFacturaBusqueda = establecimiento + "-" + punto + "-" + numero;
/*  938:     */         
/*  939:1147 */         FacturaProveedor facturaProveedor = null;
/*  940:1148 */         CuentaPorPagar cuentaPorPagar = null;
/*  941:1149 */         List<CuentaPorPagar> listaCxP = (List)hmFacturaProveedor.get(identificacion);
/*  942:1150 */         if (listaCxP != null) {
/*  943:1152 */           for (CuentaPorPagar cxp : listaCxP) {
/*  944:1153 */             if (cxp.getFacturaProveedor().getFacturaProveedorSRI() != null)
/*  945:     */             {
/*  946:1155 */               FacturaProveedorSRI facturaSRI = cxp.getFacturaProveedor().getFacturaProveedorSRI();
/*  947:     */               
/*  948:1157 */               String numeroFactura = facturaSRI.getEstablecimiento() + "-" + facturaSRI.getPuntoEmision() + "-" + facturaSRI.getNumero().replaceFirst("^0*", "");
/*  949:1159 */               if (numeroFactura.equals(numeroFacturaBusqueda))
/*  950:     */               {
/*  951:1160 */                 facturaProveedor = cxp.getFacturaProveedor();
/*  952:1161 */                 cuentaPorPagar = cxp;
/*  953:1162 */                 break;
/*  954:     */               }
/*  955:     */             }
/*  956:     */           }
/*  957:     */         }
/*  958:1168 */         if (cuentaPorPagar == null) {
/*  959:1169 */           throw new ExcepcionAS2("msg_error_existe_cuenta_por_pagar", " <> " + factura);
/*  960:     */         }
/*  961:1175 */         Pago pago = (Pago)hmPagoDeposito.get(numeroDeposito);
/*  962:1176 */         if (pago == null)
/*  963:     */         {
/*  964:1179 */           pago = new Pago();
/*  965:1180 */           pago.setDocumento(documento);
/*  966:1181 */           pago.setEmpresa(facturaProveedor.getEmpresa());
/*  967:1182 */           pago.setFecha(fechaPago);
/*  968:1183 */           pago.setIdOrganizacion(facturaProveedor.getIdOrganizacion());
/*  969:1184 */           pago.setSucursal(facturaProveedor.getSucursal());
/*  970:1185 */           pago.setBeneficiario(beneficiario);
/*  971:1186 */           pago.setValor(valor);
/*  972:1187 */           pago.setDescripcion(descripcion);
/*  973:1188 */           pago.setEstado(Estado.CONTABILIZADO);
/*  974:1189 */           pago.setNumero("");
/*  975:1190 */           pago.setTolerancia(tolerancia);
/*  976:1191 */           pago.setNumeroEgreso(numeroEgreso);
/*  977:     */           
/*  978:     */ 
/*  979:1194 */           DetalleFormaPago detalleFormaPago = new DetalleFormaPago();
/*  980:1195 */           detalleFormaPago.setCuentaBancariaOrganizacion(cuentaBancariaOrganizacion);
/*  981:1196 */           detalleFormaPago.setFormaPago(formaPago);
/*  982:1197 */           detalleFormaPago.setPago(pago);
/*  983:1198 */           detalleFormaPago.setCuentaContableFormaPago(cuentaBancariaOrganizacion.getCuentaContableBanco());
/*  984:1199 */           detalleFormaPago.setDocumentoReferencia(numeroDeposito);
/*  985:1200 */           pago.getListaDetalleFormaPago().add(detalleFormaPago);
/*  986:     */           
/*  987:1202 */           hmPagoDeposito.put(numeroDeposito, pago);
/*  988:     */         }
/*  989:     */         else
/*  990:     */         {
/*  991:1205 */           pago.setValor(pago.getValor().add(valor));
/*  992:     */         }
/*  993:1208 */         ((DetalleFormaPago)pago.getListaDetalleFormaPago().get(0)).setValor(pago.getValor());
/*  994:1210 */         if ((indicadorValidarValor) && 
/*  995:1211 */           (valor.subtract(cuentaPorPagar.getSaldo()).abs().compareTo(tolerancia) > 0)) {
/*  996:1212 */           throw new ExcepcionAS2("msg_error_valor_cobro", " " + factura);
/*  997:     */         }
/*  998:1216 */         DetallePago detallePago = new DetallePago();
/*  999:1217 */         detallePago.setCuentaPorPagar(cuentaPorPagar);
/* 1000:1218 */         detallePago.setPago(pago);
/* 1001:1219 */         detallePago.setIdOrganizacion(pago.getIdOrganizacion());
/* 1002:1220 */         detallePago.setIdSucursal(pago.getSucursal().getId());
/* 1003:1221 */         detallePago.setValor(valor);
/* 1004:1222 */         pago.getListaDetallePago().add(detallePago);
/* 1005:1223 */         filaActual++;
/* 1006:     */       }
/* 1007:     */       catch (ExcepcionAS2Financiero e)
/* 1008:     */       {
/* 1009:1226 */         LOG.error("Error al migrar factura proveedor", e);
/* 1010:1227 */         this.context.setRollbackOnly();
/* 1011:1228 */         throw e;
/* 1012:     */       }
/* 1013:     */       catch (ExcepcionAS2 e)
/* 1014:     */       {
/* 1015:1230 */         LOG.error("Error al migrar factura proveedor", e);
/* 1016:1231 */         this.context.setRollbackOnly();
/* 1017:1232 */         throw e;
/* 1018:     */       }
/* 1019:     */       catch (Exception e)
/* 1020:     */       {
/* 1021:1234 */         LOG.error("Error al migrar factura proveedor", e);
/* 1022:1235 */         this.context.setRollbackOnly();
/* 1023:1236 */         throw new ExcepcionAS2(e);
/* 1024:     */       }
/* 1025:     */     }
/* 1026:     */     try
/* 1027:     */     {
/* 1028:1243 */       Object listaPago = new ArrayList(hmPagoDeposito.values());
/* 1029:     */       
/* 1030:     */ 
/* 1031:     */ 
/* 1032:     */ 
/* 1033:     */ 
/* 1034:     */ 
/* 1035:     */ 
/* 1036:     */ 
/* 1037:1252 */       Collections.sort((List)listaPago, new Comparator()
/* 1038:     */       {
/* 1039:     */         public int compare(Pago arg0, Pago arg1)
/* 1040:     */         {
/* 1041:1248 */           return arg0.getFecha().compareTo(arg1.getFecha());
/* 1042:     */         }
/* 1043:     */       });
/* 1044:1254 */       for (Pago pago : (List)listaPago) {
/* 1045:1255 */         guardar(pago);
/* 1046:     */       }
/* 1047:     */     }
/* 1048:     */     catch (ExcepcionAS2Financiero e)
/* 1049:     */     {
/* 1050:1259 */       LOG.error("Error al migrar factura proveedor", e);
/* 1051:1260 */       this.context.setRollbackOnly();
/* 1052:1261 */       throw e;
/* 1053:     */     }
/* 1054:     */     catch (ExcepcionAS2 e)
/* 1055:     */     {
/* 1056:1263 */       LOG.error("Error al migrar factura proveedor", e);
/* 1057:1264 */       this.context.setRollbackOnly();
/* 1058:1265 */       throw e;
/* 1059:     */     }
/* 1060:     */     catch (Exception e)
/* 1061:     */     {
/* 1062:1267 */       LOG.error("Error al migrar factura proveedor", e);
/* 1063:1268 */       this.context.setRollbackOnly();
/* 1064:1269 */       throw new ExcepcionAS2(e);
/* 1065:     */     }
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public void actualizarIndicadorBloqueadoPagoPorFactura(int idFacturaProveedor, boolean bloqueo)
/* 1069:     */   {
/* 1070:1281 */     this.pagoDao.actualizarIndicadorBloqueadoPagoPorFactura(idFacturaProveedor, bloqueo);
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public void enviarEmail(Pago pago)
/* 1074:     */   {
/* 1075:1286 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 1076:1287 */     List<Contacto> listaContactos = this.pagoDao.getContactosPagoProveedor(pago);
/* 1077:1289 */     if (listaContactos.size() > 0)
/* 1078:     */     {
/* 1079:1290 */       Contacto contacto = (Contacto)listaContactos.get(0);
/* 1080:1291 */       String bodyText = contacto.getTipoContacto().getTextoCuerpoCorreoPagoProveedor();
/* 1081:1292 */       bodyText = bodyText.replaceAll(":numeroComprobante:", pago.getNumero());
/* 1082:1293 */       bodyText = bodyText.replaceAll(":fechaComprobante:", sdf.format(pago.getFecha()));
/* 1083:1294 */       bodyText = bodyText.replaceAll(":nombreProveedor:", pago.getEmpresa().getNombreFiscal());
/* 1084:1295 */       bodyText = bodyText.replaceAll(":identificacionProveedor:", pago.getEmpresa().getIdentificacion());
/* 1085:     */       
/* 1086:1297 */       Organizacion organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(pago.getIdOrganizacion()));
/* 1087:     */       
/* 1088:1299 */       String asunto = "Pago No. " + pago.getNumero() + " - " + organizacion.getRazonSocial() + " => " + pago.getEmpresa().getNombreFiscal();
/* 1089:1300 */       String emails = this.servicioEmpresa.cargarMails(pago.getEmpresa(), DocumentoBase.PAGO_PROVEEDOR);
/* 1090:     */       
/* 1091:1302 */       List<Object[]> listaDatosReporte = new ArrayList();
/* 1092:1303 */       listaDatosReporte = this.servicioReportePagoProveedor.getReportePago(pago.getId());
/* 1093:1304 */       JRDataSource ds = new QueryResultDataSource(listaDatosReporte, ReportePagoBean.fields);
/* 1094:     */       
/* 1095:1306 */       this.servicioEnvioEmail.enviarEmailComprobanteNoElectronicos(organizacion, pago.getSucursal().getId(), emails, asunto, bodyText, pago
/* 1096:1307 */         .getDocumento().getDocumentoBase(), pago.getNumero(), ds, "reportePago", pago.getUsuarioCreacion());
/* 1097:     */     }
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public List<Pago> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 1101:     */   {
/* 1102:1313 */     return this.pagoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   public void actualizarAtributoEntidad(Pago pago, HashMap<String, Object> campos)
/* 1106:     */   {
/* 1107:1318 */     this.pagoDao.actualizarAtributoEntidad(pago, campos);
/* 1108:     */   }
/* 1109:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.procesos.servicio.impl.ServicioPagoImpl
 * JD-Core Version:    0.7.0.1
 */