/*    1:     */ package com.asinfo.as2.financiero.pagos.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*    4:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    6:     */ import com.asinfo.as2.dao.CuentaPorPagarDao;
/*    7:     */ import com.asinfo.as2.dao.DetallePagoCashDao;
/*    8:     */ import com.asinfo.as2.dao.EmpleadoDao;
/*    9:     */ import com.asinfo.as2.dao.OrdenPagoProveedorDao;
/*   10:     */ import com.asinfo.as2.dao.PagoCashDao;
/*   11:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   12:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   13:     */ import com.asinfo.as2.entities.AnticipoProveedor;
/*   14:     */ import com.asinfo.as2.entities.Asiento;
/*   15:     */ import com.asinfo.as2.entities.Banco;
/*   16:     */ import com.asinfo.as2.entities.CriterioContabilizacion;
/*   17:     */ import com.asinfo.as2.entities.CuentaBancaria;
/*   18:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   19:     */ import com.asinfo.as2.entities.CuentaContable;
/*   20:     */ import com.asinfo.as2.entities.CuentaPorPagar;
/*   21:     */ import com.asinfo.as2.entities.DetalleAsiento;
/*   22:     */ import com.asinfo.as2.entities.DetalleFormaPago;
/*   23:     */ import com.asinfo.as2.entities.DetalleOrdenPagoProveedor;
/*   24:     */ import com.asinfo.as2.entities.DetallePago;
/*   25:     */ import com.asinfo.as2.entities.DetallePagoCash;
/*   26:     */ import com.asinfo.as2.entities.Documento;
/*   27:     */ import com.asinfo.as2.entities.Empleado;
/*   28:     */ import com.asinfo.as2.entities.Empresa;
/*   29:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   30:     */ import com.asinfo.as2.entities.FormaPago;
/*   31:     */ import com.asinfo.as2.entities.MovimientoBancario;
/*   32:     */ import com.asinfo.as2.entities.OrdenPagoProveedor;
/*   33:     */ import com.asinfo.as2.entities.Organizacion;
/*   34:     */ import com.asinfo.as2.entities.Pago;
/*   35:     */ import com.asinfo.as2.entities.PagoCash;
/*   36:     */ import com.asinfo.as2.entities.PagoRol;
/*   37:     */ import com.asinfo.as2.entities.PersonaResponsable;
/*   38:     */ import com.asinfo.as2.entities.Proveedor;
/*   39:     */ import com.asinfo.as2.entities.Quincena;
/*   40:     */ import com.asinfo.as2.entities.Secuencia;
/*   41:     */ import com.asinfo.as2.entities.Sucursal;
/*   42:     */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*   43:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   44:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   45:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   46:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   47:     */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*   48:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   49:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   50:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*   51:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*   52:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   53:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   54:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   55:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*   56:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioOrdenPagoProveedor;
/*   57:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/*   58:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPagoCash;
/*   59:     */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReportePagoProveedor;
/*   60:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*   61:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*   62:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   63:     */ import com.asinfo.as2.util.AppUtil;
/*   64:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   65:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   66:     */ import java.math.BigDecimal;
/*   67:     */ import java.math.RoundingMode;
/*   68:     */ import java.util.ArrayList;
/*   69:     */ import java.util.Collection;
/*   70:     */ import java.util.Date;
/*   71:     */ import java.util.HashMap;
/*   72:     */ import java.util.HashSet;
/*   73:     */ import java.util.Iterator;
/*   74:     */ import java.util.List;
/*   75:     */ import java.util.Map;
/*   76:     */ import java.util.Set;
/*   77:     */ import javax.ejb.EJB;
/*   78:     */ import javax.ejb.SessionContext;
/*   79:     */ import javax.ejb.Stateless;
/*   80:     */ import javax.ejb.TransactionAttribute;
/*   81:     */ import javax.ejb.TransactionAttributeType;
/*   82:     */ import javax.ejb.TransactionManagement;
/*   83:     */ import javax.ejb.TransactionManagementType;
/*   84:     */ import org.apache.log4j.Logger;
/*   85:     */ 
/*   86:     */ @Stateless
/*   87:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*   88:     */ public class ServicioPagoCashImpl
/*   89:     */   extends AbstractServicioAS2Financiero
/*   90:     */   implements ServicioPagoCash
/*   91:     */ {
/*   92:     */   private static final long serialVersionUID = 72417655803928018L;
/*   93: 101 */   private static final Logger LOG = Logger.getLogger(ServicioPagoCashImpl.class);
/*   94:     */   @EJB
/*   95:     */   private transient PagoCashDao pagoCashDao;
/*   96:     */   @EJB
/*   97:     */   private transient DetallePagoCashDao detallePagoCashDao;
/*   98:     */   @EJB
/*   99:     */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  100:     */   @EJB
/*  101:     */   private transient ServicioPago servicioPago;
/*  102:     */   @EJB
/*  103:     */   private transient ServicioDocumento servicioDocumento;
/*  104:     */   @EJB
/*  105:     */   private transient ServicioAnticipoProveedor servicioAnticipoProveedor;
/*  106:     */   @EJB
/*  107:     */   private transient CuentaPorPagarDao cuentaPorPagarDao;
/*  108:     */   @EJB
/*  109:     */   private transient ServicioPeriodo servicioPeriodo;
/*  110:     */   @EJB
/*  111:     */   private transient ServicioAsiento servicioAsiento;
/*  112:     */   @EJB
/*  113:     */   private transient ServicioSecuencia servicioSecuencia;
/*  114:     */   @EJB
/*  115:     */   private transient ServicioPagoRol servicioPagoRol;
/*  116:     */   @EJB
/*  117:     */   private transient ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  118:     */   @EJB
/*  119:     */   private transient ServicioPagoCash servicioPagoCash;
/*  120:     */   @EJB
/*  121:     */   private transient EmpleadoDao empleadoDao;
/*  122:     */   @EJB
/*  123:     */   private transient OrdenPagoProveedorDao ordenPagoProveedorDao;
/*  124:     */   @EJB
/*  125:     */   private transient ServicioGenerico<DetalleOrdenPagoProveedor> detalleOrdenPagoProveedorDao;
/*  126:     */   @EJB
/*  127:     */   private transient ServicioOrdenPagoProveedor servicioOrdenPagoProveedor;
/*  128:     */   @EJB
/*  129:     */   private transient ServicioSucursal servicioSucursal;
/*  130:     */   @EJB
/*  131:     */   private transient ServicioReportePagoProveedor servicioReportePagoProveedor;
/*  132:     */   
/*  133:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  134:     */   public void guardar(PagoCash pagoCash)
/*  135:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  136:     */   {
/*  137:     */     try
/*  138:     */     {
/*  139: 152 */       cargarSecuencia(pagoCash);
/*  140: 153 */       validarValoresOrdenPagoProveedor(pagoCash);
/*  141: 154 */       Map<Integer, CuentaPorPagar> hashCuentaPorPagarPagoCash = new HashMap();
/*  142: 155 */       pagoCash.setValorPago(BigDecimal.ZERO);
/*  143: 156 */       for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash())
/*  144:     */       {
/*  145: 158 */         if ((detallePagoCash.getValor().compareTo(BigDecimal.ZERO) == 0) || (detallePagoCash.isEliminado()) || (
/*  146: 159 */           (detallePagoCash.getCuentaPorPagar() != null) && (!detallePagoCash.isIndicadorAprobado())))
/*  147:     */         {
/*  148: 160 */           detallePagoCash.setEliminado(true);
/*  149: 161 */           if (detallePagoCash.getCuentaPorPagar() != null) {
/*  150: 162 */             hashCuentaPorPagarPagoCash.put(Integer.valueOf(detallePagoCash.getCuentaPorPagar().getId()), detallePagoCash.getCuentaPorPagar());
/*  151:     */           }
/*  152:     */         }
/*  153: 165 */         else if (pagoCash.getDocumento().getDocumentoBase().equals(DocumentoBase.PAGO_CASH_EMPLEADO))
/*  154:     */         {
/*  155: 166 */           pagoCash.setValorPago(pagoCash.getValorPago().add(detallePagoCash.getValor().divide(new BigDecimal(100))));
/*  156:     */         }
/*  157:     */         else
/*  158:     */         {
/*  159: 169 */           pagoCash.setValorPago(pagoCash.getValorPago().add(detallePagoCash.getValor()));
/*  160:     */         }
/*  161: 173 */         this.detallePagoCashDao.guardar(detallePagoCash);
/*  162:     */       }
/*  163: 175 */       this.pagoCashDao.guardar(pagoCash);
/*  164: 177 */       if (pagoCash.getDocumento().getDocumentoBase() == DocumentoBase.PAGO_CASH_EMPLEADO) {
/*  165: 179 */         this.servicioPagoRol.actualizarPagoCash(pagoCash.getPagoRol().getIdPagoRol(), pagoCash, false, true);
/*  166:     */       } else {
/*  167: 181 */         actualizaIndicadorBloqueado(pagoCash.getIdPagoCash(), true);
/*  168:     */       }
/*  169: 184 */       for (CuentaPorPagar cpp : hashCuentaPorPagarPagoCash.values()) {
/*  170: 185 */         this.cuentaPorPagarDao.desbloquearCuentaPorPagar(cpp);
/*  171:     */       }
/*  172:     */     }
/*  173:     */     catch (ExcepcionAS2Financiero e)
/*  174:     */     {
/*  175: 188 */       this.context.setRollbackOnly();
/*  176: 189 */       e.printStackTrace();
/*  177: 190 */       throw e;
/*  178:     */     }
/*  179:     */     catch (ExcepcionAS2 e)
/*  180:     */     {
/*  181: 192 */       this.context.setRollbackOnly();
/*  182: 193 */       e.printStackTrace();
/*  183: 194 */       throw e;
/*  184:     */     }
/*  185:     */     catch (AS2Exception e)
/*  186:     */     {
/*  187: 196 */       this.context.setRollbackOnly();
/*  188: 197 */       e.printStackTrace();
/*  189: 198 */       throw e;
/*  190:     */     }
/*  191:     */     catch (Exception e)
/*  192:     */     {
/*  193: 200 */       this.context.setRollbackOnly();
/*  194: 201 */       e.printStackTrace();
/*  195: 202 */       throw new ExcepcionAS2(e);
/*  196:     */     }
/*  197:     */   }
/*  198:     */   
/*  199:     */   private void validarValoresOrdenPagoProveedor(PagoCash pagoCash)
/*  200:     */     throws AS2Exception
/*  201:     */   {
/*  202: 208 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash())
/*  203:     */     {
/*  204: 209 */       DetalleOrdenPagoProveedor detalleOPP = detallePagoCash.getDetalleOrdenPagoProveedor();
/*  205: 211 */       if ((!detallePagoCash.isEliminado()) && (detalleOPP != null) && (detallePagoCash.getValor().compareTo(detalleOPP.getValorAprobado()) == 1)) {
/*  206: 213 */         throw new AS2Exception("msg_error_valor_a_liquidar_mayor_al_aprobado", new String[] {"\n" + detallePagoCash.getValor() + " > " + detalleOPP.getValorAprobado().toString() });
/*  207:     */       }
/*  208:     */     }
/*  209:     */   }
/*  210:     */   
/*  211:     */   private void cargarSecuencia(PagoCash pagoCash)
/*  212:     */     throws ExcepcionAS2
/*  213:     */   {
/*  214: 226 */     if ("".equals(pagoCash.getNumero()))
/*  215:     */     {
/*  216: 227 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(pagoCash.getDocumento().getIdDocumento(), pagoCash.getFechaPago());
/*  217: 228 */       pagoCash.setNumero(numero);
/*  218:     */     }
/*  219:     */   }
/*  220:     */   
/*  221:     */   public void eliminar(PagoCash pagoCash)
/*  222:     */   {
/*  223: 239 */     this.pagoCashDao.eliminar(pagoCash);
/*  224:     */   }
/*  225:     */   
/*  226:     */   public PagoCash buscarPorId(int idPagoCash)
/*  227:     */   {
/*  228: 249 */     return (PagoCash)this.pagoCashDao.buscarPorId(Integer.valueOf(idPagoCash));
/*  229:     */   }
/*  230:     */   
/*  231:     */   public List<PagoCash> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  232:     */   {
/*  233: 261 */     return this.pagoCashDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  234:     */   }
/*  235:     */   
/*  236:     */   public List<PagoCash> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  237:     */   {
/*  238: 271 */     return this.pagoCashDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  239:     */   }
/*  240:     */   
/*  241:     */   public int contarPorCriterio(Map<String, String> filters)
/*  242:     */   {
/*  243: 281 */     return this.pagoCashDao.contarPorCriterio(filters);
/*  244:     */   }
/*  245:     */   
/*  246:     */   public PagoCash cargarDetalle(int idPagoCash)
/*  247:     */   {
/*  248: 291 */     return this.pagoCashDao.cargarDetalle(idPagoCash);
/*  249:     */   }
/*  250:     */   
/*  251:     */   public PagoCash cargarDetalleAnulacion(int idPagoCash)
/*  252:     */   {
/*  253: 296 */     return this.pagoCashDao.cargarDetalleAnulacion(idPagoCash);
/*  254:     */   }
/*  255:     */   
/*  256:     */   public void cargarFacturasPendientes(PagoCash pagoCash)
/*  257:     */   {
/*  258: 306 */     cargarFacturasPendientes(pagoCash, null, false);
/*  259:     */   }
/*  260:     */   
/*  261:     */   public void cargarFacturasPendientes(PagoCash pagoCash, Documento documento, boolean indicadorOrdenPagoProveedor)
/*  262:     */   {
/*  263: 316 */     Map<Integer, DetalleOrdenPagoProveedor> mapaValorAprobado = new HashMap();
/*  264: 317 */     BigDecimal valorPago = pagoCash.getValorPago();
/*  265: 318 */     List<CuentaPorPagar> listaFacturasPendientes = new ArrayList();
/*  266:     */     List<DetalleOrdenPagoProveedor> listaDetalleOrdenPago;
/*  267: 319 */     if (!indicadorOrdenPagoProveedor)
/*  268:     */     {
/*  269: 320 */       listaFacturasPendientes = this.servicioFacturaProveedor.obtenerFacturasPendientes(pagoCash.getIdOrganizacion(), pagoCash.getFechaVencimiento(), pagoCash
/*  270: 321 */         .getTipoServicioCuentaBancaria(), pagoCash.getCategoriaEmpresa(), documento);
/*  271:     */     }
/*  272:     */     else
/*  273:     */     {
/*  274: 324 */       listaDetalleOrdenPago = this.ordenPagoProveedorDao.buscarDetallesPendientesPago(pagoCash.getIdOrganizacion(), 
/*  275: 325 */         Boolean.valueOf(true), pagoCash.getFechaVencimiento(), null, null, pagoCash.getTipoServicioCuentaBancaria(), pagoCash.getCategoriaEmpresa(), documento);
/*  276: 327 */       for (DetalleOrdenPagoProveedor detalleOrdenPagoProveedor : listaDetalleOrdenPago)
/*  277:     */       {
/*  278: 328 */         CuentaPorPagar cxp = detalleOrdenPagoProveedor.getCuentaPorPagar();
/*  279: 329 */         if (cxp != null)
/*  280:     */         {
/*  281: 330 */           cxp.setOrdenPagoProveedor(detalleOrdenPagoProveedor.getOrdenPagoProveedor());
/*  282: 331 */           listaFacturasPendientes.add(detalleOrdenPagoProveedor.getCuentaPorPagar());
/*  283: 332 */           mapaValorAprobado.put(Integer.valueOf(detalleOrdenPagoProveedor.getCuentaPorPagar().getId()), detalleOrdenPagoProveedor);
/*  284:     */         }
/*  285:     */         else
/*  286:     */         {
/*  287: 334 */           agregarAnticipo(pagoCash, detalleOrdenPagoProveedor, null, null, null, detalleOrdenPagoProveedor.getPersonaResponsable());
/*  288:     */         }
/*  289:     */       }
/*  290:     */     }
/*  291: 339 */     for (CuentaPorPagar cxp : listaFacturasPendientes)
/*  292:     */     {
/*  293: 341 */       DetallePagoCash detallePagoCash = new DetallePagoCash();
/*  294: 342 */       detallePagoCash.setIdOrganizacion(pagoCash.getIdOrganizacion());
/*  295: 343 */       detallePagoCash.setPagoCash(pagoCash);
/*  296: 344 */       detallePagoCash.setEliminado(false);
/*  297: 345 */       detallePagoCash.setCuentaPorPagar(cxp);
/*  298: 346 */       detallePagoCash.setProveedor(cxp.getFacturaProveedor().getEmpresa().getProveedor());
/*  299: 347 */       detallePagoCash.setValor(BigDecimal.ZERO);
/*  300: 348 */       detallePagoCash.setDiasVencidos((int)FuncionesUtiles.DiasEntreFechas(cxp.getFechaVencimiento(), pagoCash.getFechaPago()));
/*  301: 349 */       detallePagoCash.setIndicadorAprobado(true);
/*  302: 350 */       pagoCash.getListaDetallePagoCash().add(detallePagoCash);
/*  303: 352 */       if (indicadorOrdenPagoProveedor)
/*  304:     */       {
/*  305: 353 */         BigDecimal valor = BigDecimal.ZERO;
/*  306: 354 */         BigDecimal saldoCuentaPorCobrar = cxp.getSaldo();
/*  307: 355 */         BigDecimal valorAprobado = null;
/*  308: 356 */         DetalleOrdenPagoProveedor detalleOrdenPagoProveedor = (DetalleOrdenPagoProveedor)mapaValorAprobado.get(Integer.valueOf(cxp.getId()));
/*  309: 357 */         detallePagoCash.setDetalleOrdenPagoProveedor(detalleOrdenPagoProveedor);
/*  310: 358 */         pagoCash.setOrdenPagoProveedor(detalleOrdenPagoProveedor.getOrdenPagoProveedor());
/*  311: 359 */         if (detalleOrdenPagoProveedor != null) {
/*  312: 360 */           valorAprobado = detalleOrdenPagoProveedor.getValorAprobado();
/*  313:     */         }
/*  314: 362 */         if ((valorAprobado != null) && (valorAprobado.compareTo(saldoCuentaPorCobrar) <= 0)) {
/*  315: 363 */           valor = valorAprobado;
/*  316:     */         } else {
/*  317: 365 */           valor = saldoCuentaPorCobrar;
/*  318:     */         }
/*  319: 367 */         detallePagoCash.setValor(valor);
/*  320:     */       }
/*  321:     */     }
/*  322:     */   }
/*  323:     */   
/*  324:     */   public void agregarAnticipo(PagoCash pagoCash, DetalleOrdenPagoProveedor detalleOrdenPagoProveedor, Empresa empresa, BigDecimal valorAnticipo, String descripcionAnticipo, PersonaResponsable personaResponsable)
/*  325:     */   {
/*  326: 376 */     DetallePagoCash detallePagoCash = new DetallePagoCash();
/*  327: 377 */     if (detalleOrdenPagoProveedor != null)
/*  328:     */     {
/*  329: 378 */       detallePagoCash.setProveedor(detalleOrdenPagoProveedor.getProveedor());
/*  330: 379 */       detallePagoCash.setValor(detalleOrdenPagoProveedor.getValorAprobado());
/*  331: 380 */       detallePagoCash.setDescripcion(detalleOrdenPagoProveedor.getDescripcion());
/*  332:     */     }
/*  333:     */     else
/*  334:     */     {
/*  335: 382 */       detallePagoCash.setProveedor(empresa.getProveedor());
/*  336: 383 */       detallePagoCash.setValor(valorAnticipo);
/*  337: 384 */       detallePagoCash.setDescripcion(descripcionAnticipo);
/*  338:     */     }
/*  339: 386 */     detallePagoCash.setDetalleOrdenPagoProveedor(detalleOrdenPagoProveedor);
/*  340: 387 */     detallePagoCash.setIdOrganizacion(pagoCash.getIdOrganizacion());
/*  341: 388 */     detallePagoCash.setIdSucursal(pagoCash.getIdSucursal());
/*  342: 389 */     detallePagoCash.setCuentaPorPagar(null);
/*  343: 390 */     detallePagoCash.setIndicadorAprobado(true);
/*  344: 391 */     detallePagoCash.setIndicadorProcesado(false);
/*  345: 392 */     detallePagoCash.setIndicadorDeshabilitarIngreso(true);
/*  346: 393 */     detallePagoCash.setPagoCash(pagoCash);
/*  347: 394 */     detallePagoCash.setPersonaResponsable(personaResponsable);
/*  348:     */     
/*  349:     */ 
/*  350: 397 */     datosBanco(detallePagoCash);
/*  351: 398 */     pagoCash.getListaDetallePagoCash().add(detallePagoCash);
/*  352:     */   }
/*  353:     */   
/*  354:     */   public void datosBanco(DetallePagoCash dpc)
/*  355:     */   {
/*  356: 404 */     Object[] datosCuentaBancaria = this.servicioReportePagoProveedor.getCuentaBancariaProveedor(Integer.valueOf(dpc.getProveedor().getEmpresa().getIdEmpresa()));
/*  357: 405 */     if (datosCuentaBancaria != null)
/*  358:     */     {
/*  359: 406 */       dpc.setCuentaBancariaEmpresa((String)datosCuentaBancaria[5]);
/*  360: 407 */       dpc.setTipoCuentaBancaria((String)datosCuentaBancaria[6]);
/*  361: 408 */       dpc.setNombreBanco((String)datosCuentaBancaria[7]);
/*  362: 409 */       dpc.setCodigoBanco((String)datosCuentaBancaria[3]);
/*  363:     */     }
/*  364:     */   }
/*  365:     */   
/*  366:     */   public void actualizarEstadoPagoCash(int idPagoCash, Estado estado)
/*  367:     */   {
/*  368: 420 */     this.pagoCashDao.actualizarEstadoPagoCash(idPagoCash, estado);
/*  369:     */   }
/*  370:     */   
/*  371:     */   public void contabilizarPagoCash(PagoCash pagoCash, String documentoReferencia)
/*  372:     */     throws ExcepcionAS2
/*  373:     */   {
/*  374: 431 */     contabilizarPagoCash(pagoCash, documentoReferencia, null);
/*  375:     */   }
/*  376:     */   
/*  377:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  378:     */   public void contabilizarPagoCash(PagoCash pagoCash, String documentoReferencia, String nombreUsuario)
/*  379:     */     throws ExcepcionAS2
/*  380:     */   {
/*  381: 437 */     Set<Integer> listaIdOrdenPagoProveedor = new HashSet();
/*  382: 438 */     Map<Integer, CuentaPorPagar> hashCuentaPorPagar = new HashMap();
/*  383:     */     
/*  384:     */ 
/*  385:     */ 
/*  386: 442 */     Documento documentoAnticipo = pagoCash.getDocumentoAnticipo() == null ? (Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.ANTICIPO_PROVEEDOR, pagoCash.getIdOrganizacion()).get(0) : pagoCash.getDocumentoAnticipo();
/*  387:     */     
/*  388:     */ 
/*  389:     */ 
/*  390: 446 */     Documento documentoPago = pagoCash.getDocumentoPago() == null ? (Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.PAGO_PROVEEDOR, pagoCash.getIdOrganizacion()).get(0) : pagoCash.getDocumentoPago();
/*  391:     */     
/*  392:     */ 
/*  393: 449 */     this.servicioDocumento.detach(documentoAnticipo);
/*  394: 450 */     documentoAnticipo.setIndicadorContabilizar(pagoCash.getDocumento().isIndicadorContabilizar());
/*  395: 451 */     this.servicioDocumento.detach(documentoPago);
/*  396: 452 */     documentoPago.setIndicadorContabilizar(pagoCash.getDocumento().isIndicadorContabilizar());
/*  397:     */     
/*  398: 454 */     Map<Integer, Map<Integer, DetallePagoCash>> mapaPagoCash = new HashMap();
/*  399:     */     
/*  400:     */ 
/*  401: 457 */     Asiento asiento = new Asiento();
/*  402: 458 */     asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  403: 459 */     asiento.setSucursal(AppUtil.getSucursal());
/*  404: 460 */     asiento.setTipoAsiento(pagoCash.getDocumento().getTipoAsiento());
/*  405: 461 */     asiento.setFecha(pagoCash.getFechaContabilizacion());
/*  406: 462 */     asiento.setTipoAsiento(this.servicioDocumento.cargarDetalle(pagoCash.getDocumento().getId()).getTipoAsiento());
/*  407: 463 */     asiento.setNumero("");
/*  408: 464 */     asiento.setEstado(Estado.ELABORADO);
/*  409: 465 */     asiento.setIndicadorAutomatico(true);
/*  410: 466 */     asiento.setConcepto(pagoCash.getDocumento().getNombre() + " - " + pagoCash.getNumero() + " - " + 
/*  411: 467 */       FuncionesUtiles.convertidorFechaALetras(pagoCash.getFechaPago()));
/*  412: 468 */     asiento.setDocumentoOrigen(pagoCash.getDocumento());
/*  413: 469 */     BigDecimal valorPagoCash = BigDecimal.ZERO;
/*  414: 470 */     BigDecimal valorAnticiposCash = BigDecimal.ZERO;
/*  415:     */     
/*  416: 472 */     CuentaBancariaOrganizacion cbo = this.servicioCuentaBancariaOrganizacion.cargarDetalle(pagoCash.getCuentaBancariaOrganizacion().getIdCuentaBancariaOrganizacion());
/*  417:     */     try
/*  418:     */     {
/*  419: 475 */       for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash()) {
/*  420: 477 */         if (detallePagoCash.isIndicadorProcesado()) {
/*  421: 478 */           if (detallePagoCash.getCuentaPorPagar() == null)
/*  422:     */           {
/*  423: 479 */             AnticipoProveedor anticipoProveedor = new AnticipoProveedor();
/*  424: 480 */             anticipoProveedor.setBeneficiario(detallePagoCash.getProveedor().getEmpresa().getNombreFiscal().length() > 49 ? detallePagoCash
/*  425: 481 */               .getProveedor().getEmpresa().getNombreFiscal().substring(0, 49) : detallePagoCash
/*  426: 482 */               .getProveedor().getEmpresa().getNombreFiscal());
/*  427: 483 */             anticipoProveedor.setCuentaBancariaOrganizacion(detallePagoCash.getPagoCash().getCuentaBancariaOrganizacion());
/*  428: 484 */             anticipoProveedor.setDescripcion(detallePagoCash.getDescripcion());
/*  429: 485 */             anticipoProveedor.setPersonaResponsable(detallePagoCash.getPersonaResponsable());
/*  430:     */             
/*  431: 487 */             anticipoProveedor.setDocumento(documentoAnticipo);
/*  432: 488 */             anticipoProveedor.setDocumentoReferencia(documentoReferencia);
/*  433: 489 */             anticipoProveedor.setEmpresa(detallePagoCash.getProveedor().getEmpresa());
/*  434: 490 */             anticipoProveedor.setFecha(detallePagoCash.getPagoCash().getFechaPago());
/*  435: 491 */             anticipoProveedor.setFormaPago(detallePagoCash.getPagoCash().getFormaPago());
/*  436: 492 */             anticipoProveedor.setValor(detallePagoCash.getValor());
/*  437: 493 */             anticipoProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  438: 494 */             anticipoProveedor.setSucursal(AppUtil.getSucursal());
/*  439: 495 */             anticipoProveedor.setNumero("");
/*  440: 496 */             anticipoProveedor.setPagoCash(pagoCash);
/*  441: 497 */             anticipoProveedor.setEstado(Estado.CONTABILIZADO);
/*  442: 498 */             anticipoProveedor.setCuentaContableBancoPagoCash(cbo.getCuentaContableBancoPagoCash());
/*  443: 499 */             anticipoProveedor.setIndicadorContabilizar(Boolean.valueOf(pagoCash.getDocumento().isIndicadorContabilizar()));
/*  444: 501 */             if (pagoCash.getDocumento().isIndicadorContabilizar())
/*  445:     */             {
/*  446: 505 */               DetalleAsiento detalleAsiento = new DetalleAsiento();
/*  447: 506 */               detalleAsiento.setAsiento(asiento);
/*  448: 507 */               detalleAsiento.setIdOrganizacion(pagoCash.getIdOrganizacion());
/*  449: 508 */               detalleAsiento.setIdSucursal(pagoCash.getIdSucursal());
/*  450: 509 */               detalleAsiento.setCuentaContable(cbo.getCuentaContableBancoPagoCash());
/*  451: 510 */               detalleAsiento.setDescripcion("Anticipo proveedor: " + anticipoProveedor.getEmpresa().getNombreFiscal());
/*  452: 511 */               detalleAsiento.setDebe(anticipoProveedor.getValor());
/*  453: 512 */               asiento.getListaDetalleAsiento().add(detalleAsiento);
/*  454:     */             }
/*  455: 515 */             valorAnticiposCash = valorAnticiposCash.add(anticipoProveedor.getValor());
/*  456:     */             
/*  457: 517 */             this.servicioAnticipoProveedor.guardar(anticipoProveedor);
/*  458: 519 */             if (detallePagoCash.getDetalleOrdenPagoProveedor() != null)
/*  459:     */             {
/*  460: 520 */               detallePagoCash.setIndicadorAprobado(true);
/*  461: 521 */               detallePagoCash.getDetalleOrdenPagoProveedor().setIndicadorPagado(true);
/*  462: 522 */               detallePagoCash.getDetalleOrdenPagoProveedor().setValorPagado(detallePagoCash.getValor());
/*  463: 523 */               listaIdOrdenPagoProveedor.add(Integer.valueOf(detallePagoCash.getDetalleOrdenPagoProveedor().getOrdenPagoProveedor().getId()));
/*  464: 524 */               this.detalleOrdenPagoProveedorDao.guardar(detallePagoCash.getDetalleOrdenPagoProveedor());
/*  465:     */             }
/*  466:     */           }
/*  467:     */           else
/*  468:     */           {
/*  469: 528 */             int idEmpresa = detallePagoCash.getCuentaPorPagar().getFacturaProveedor().getEmpresa().getIdEmpresa();
/*  470:     */             Map<Integer, DetallePagoCash> mapaPago;
/*  471:     */             Map<Integer, DetallePagoCash> mapaPago;
/*  472: 529 */             if (mapaPagoCash.containsKey(Integer.valueOf(idEmpresa)))
/*  473:     */             {
/*  474: 530 */               mapaPago = (Map)mapaPagoCash.get(Integer.valueOf(idEmpresa));
/*  475:     */             }
/*  476:     */             else
/*  477:     */             {
/*  478: 532 */               mapaPago = new HashMap();
/*  479: 533 */               mapaPagoCash.put(Integer.valueOf(idEmpresa), mapaPago);
/*  480:     */             }
/*  481: 535 */             mapaPago.put(Integer.valueOf(detallePagoCash.getId()), detallePagoCash);
/*  482:     */           }
/*  483:     */         }
/*  484:     */       }
/*  485: 541 */       for (Map<Integer, DetallePagoCash> mapaPago : mapaPagoCash.values())
/*  486:     */       {
/*  487: 542 */         Pago pago = new Pago();
/*  488:     */         
/*  489: 544 */         pago.setDocumento(documentoPago);
/*  490: 545 */         pago.setFecha(pagoCash.getFechaContabilizacion());
/*  491: 546 */         pago.setSucursal(AppUtil.getSucursal());
/*  492: 547 */         pago.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  493: 548 */         pago.setEstado(Estado.APROBADO);
/*  494: 549 */         pago.setNumero("");
/*  495:     */         
/*  496: 551 */         valorPago = BigDecimal.ZERO;
/*  497: 552 */         String nombreProveedor = "";
/*  498: 553 */         for (DetallePagoCash detallePagoCash : mapaPago.values())
/*  499:     */         {
/*  500: 554 */           pago.setBeneficiario(detallePagoCash.getCuentaPorPagar().getFacturaProveedor().getEmpresa().getNombreFiscal());
/*  501: 555 */           nombreProveedor = detallePagoCash.getCuentaPorPagar().getFacturaProveedor().getEmpresa().getNombreFiscal();
/*  502: 556 */           pago.setEmpresa(detallePagoCash.getCuentaPorPagar().getFacturaProveedor().getEmpresa());
/*  503: 557 */           pago.setDescripcion(detallePagoCash.getDescripcion());
/*  504: 558 */           valorPago = valorPago.add(detallePagoCash.getValor());
/*  505: 559 */           DetallePago detallePago = new DetallePago();
/*  506: 560 */           detallePago.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  507: 561 */           detallePago.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  508: 562 */           detallePago.setPago(pago);
/*  509: 563 */           detallePago.setCuentaPorPagar(detallePagoCash.getCuentaPorPagar());
/*  510: 564 */           detallePago.setValor(detallePagoCash.getValor());
/*  511: 565 */           detallePago.setPago(pago);
/*  512: 566 */           pago.getListaDetallePago().add(detallePago);
/*  513: 568 */           if (detallePagoCash.getDetalleOrdenPagoProveedor() != null)
/*  514:     */           {
/*  515: 569 */             detallePagoCash.setIndicadorAprobado(true);
/*  516: 570 */             detallePagoCash.getDetalleOrdenPagoProveedor().setIndicadorPagado(true);
/*  517: 571 */             detallePagoCash.getDetalleOrdenPagoProveedor().setValorPagado(detallePagoCash.getValor());
/*  518: 572 */             listaIdOrdenPagoProveedor.add(Integer.valueOf(detallePagoCash.getDetalleOrdenPagoProveedor().getOrdenPagoProveedor().getId()));
/*  519: 573 */             if (detallePagoCash.getDetalleOrdenPagoProveedor().getCuentaPorPagar() != null)
/*  520:     */             {
/*  521: 574 */               CuentaPorPagar cuentaPorPagarDOPP = detallePagoCash.getDetalleOrdenPagoProveedor().getCuentaPorPagar();
/*  522: 575 */               hashCuentaPorPagar.put(Integer.valueOf(cuentaPorPagarDOPP.getId()), cuentaPorPagarDOPP);
/*  523:     */             }
/*  524: 577 */             this.detalleOrdenPagoProveedorDao.guardar(detallePagoCash.getDetalleOrdenPagoProveedor());
/*  525:     */           }
/*  526:     */         }
/*  527: 580 */         pago.setValor(valorPago);
/*  528:     */         
/*  529: 582 */         DetalleFormaPago detalleFormaPago = new DetalleFormaPago();
/*  530: 583 */         detalleFormaPago.setPago(pago);
/*  531: 584 */         detalleFormaPago.setValor(valorPago);
/*  532: 585 */         detalleFormaPago.setCuentaBancariaOrganizacion(pagoCash.getCuentaBancariaOrganizacion());
/*  533: 586 */         detalleFormaPago.setFormaPago(pagoCash.getFormaPago());
/*  534: 587 */         detalleFormaPago.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  535: 588 */         detalleFormaPago.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  536: 589 */         detalleFormaPago.setDocumentoReferencia(documentoReferencia);
/*  537: 590 */         detalleFormaPago.setCuentaContableFormaPago(cbo.getCuentaContableBancoPagoCash());
/*  538: 591 */         pago.getListaDetalleFormaPago().add(detalleFormaPago);
/*  539: 592 */         pago.setPagoCash(pagoCash);
/*  540:     */         
/*  541: 594 */         this.servicioPago.guardar(pago);
/*  542: 596 */         if (pagoCash.getDocumento().isIndicadorContabilizar())
/*  543:     */         {
/*  544: 599 */           DetalleAsiento detalleAsiento = new DetalleAsiento();
/*  545: 600 */           detalleAsiento.setAsiento(asiento);
/*  546: 601 */           detalleAsiento.setIdOrganizacion(pagoCash.getIdOrganizacion());
/*  547: 602 */           detalleAsiento.setIdSucursal(pagoCash.getIdSucursal());
/*  548: 603 */           detalleAsiento.setCuentaContable(cbo.getCuentaContableBancoPagoCash());
/*  549: 604 */           detalleAsiento.setDescripcion("Pago proveedor: " + nombreProveedor);
/*  550: 605 */           detalleAsiento.setDebe(valorPago);
/*  551: 606 */           asiento.getListaDetalleAsiento().add(detalleAsiento);
/*  552:     */         }
/*  553: 609 */         valorPagoCash = valorPagoCash.add(valorPago);
/*  554:     */       }
/*  555:     */       BigDecimal valorPago;
/*  556: 613 */       actualizaIndicadorBloqueado(pagoCash.getIdPagoCash(), false);
/*  557: 615 */       if (pagoCash.getDocumento().isIndicadorContabilizar())
/*  558:     */       {
/*  559: 618 */         detalleAsiento = new DetalleAsiento();
/*  560: 619 */         ((DetalleAsiento)detalleAsiento).setAsiento(asiento);
/*  561: 620 */         ((DetalleAsiento)detalleAsiento).setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  562: 621 */         ((DetalleAsiento)detalleAsiento).setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  563: 622 */         ((DetalleAsiento)detalleAsiento).setCuentaContable(cbo.getCuentaContableBanco());
/*  564: 623 */         ((DetalleAsiento)detalleAsiento).setHaber(valorPagoCash.add(valorAnticiposCash));
/*  565: 624 */         ((DetalleAsiento)detalleAsiento).setDescripcion("Pago proveedores: " + pagoCash.getFechaPago());
/*  566: 625 */         asiento.getListaDetalleAsiento().add(detalleAsiento);
/*  567:     */         
/*  568: 627 */         pagoCash.setAsiento(asiento);
/*  569: 628 */         pagoCash.setEstado(Estado.CONTABILIZADO);
/*  570: 629 */         pagoCash.setDocumentoReferencia(documentoReferencia);
/*  571:     */         
/*  572:     */ 
/*  573:     */ 
/*  574: 633 */         MovimientoBancario movimientoBancario = new MovimientoBancario();
/*  575:     */         
/*  576: 635 */         CuentaBancariaOrganizacion cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.buscarPorCuentaContable(((DetalleAsiento)detalleAsiento).getCuentaContable().getId());
/*  577: 636 */         if (cuentaBancariaOrganizacion == null)
/*  578:     */         {
/*  579: 637 */           ((DetalleAsiento)detalleAsiento).setEliminado(true);
/*  580: 638 */           throw new ExcepcionAS2Financiero("msg_error_parametrizacion_cuenta_pago", ((DetalleAsiento)detalleAsiento).getCuentaContable().getCodigo());
/*  581:     */         }
/*  582: 640 */         movimientoBancario.setEstado(Estado.CONTABILIZADO);
/*  583: 641 */         movimientoBancario.setDetalleAsiento((DetalleAsiento)detalleAsiento);
/*  584: 642 */         movimientoBancario.setIdOrganizacion(asiento.getIdOrganizacion());
/*  585: 643 */         movimientoBancario.setIdSucursal(asiento.getSucursal().getId());
/*  586: 644 */         movimientoBancario.setBeneficiario("Pago proveedores: " + pagoCash.getFechaPago());
/*  587: 645 */         movimientoBancario.setValor(((DetalleAsiento)detalleAsiento).getHaber().negate());
/*  588: 646 */         movimientoBancario.setCuentaBancariaOrganizacion(cuentaBancariaOrganizacion);
/*  589: 647 */         movimientoBancario.setDocumento(pagoCash.getDocumento());
/*  590: 648 */         movimientoBancario.setDescripcion(asiento.getConcepto());
/*  591: 649 */         movimientoBancario.setDocumentoReferencia(documentoReferencia);
/*  592: 650 */         movimientoBancario.setFecha(asiento.getFecha());
/*  593: 651 */         movimientoBancario.setFormaPago(pagoCash.getFormaPago());
/*  594:     */         
/*  595: 653 */         ((DetalleAsiento)detalleAsiento).setMovimientoBancario(movimientoBancario);
/*  596:     */         
/*  597: 655 */         this.servicioAsiento.guardar(asiento);
/*  598: 656 */         for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash()) {
/*  599: 657 */           this.detallePagoCashDao.guardar(detallePagoCash);
/*  600:     */         }
/*  601:     */       }
/*  602:     */       else
/*  603:     */       {
/*  604: 660 */         pagoCash.setEstado(Estado.CONTABILIZADO);
/*  605:     */       }
/*  606: 662 */       this.pagoCashDao.guardar(pagoCash);
/*  607: 663 */       for (Object detalleAsiento = listaIdOrdenPagoProveedor.iterator(); ((Iterator)detalleAsiento).hasNext();)
/*  608:     */       {
/*  609: 663 */         Integer idOPP = (Integer)((Iterator)detalleAsiento).next();
/*  610: 664 */         this.ordenPagoProveedorDao.cerrarOrdenPagoProveedor(idOPP, nombreUsuario);
/*  611:     */       }
/*  612: 666 */       this.servicioOrdenPagoProveedor.liberarCuentasPorPagar(hashCuentaPorPagar);
/*  613:     */     }
/*  614:     */     catch (ExcepcionAS2Financiero e)
/*  615:     */     {
/*  616: 668 */       this.context.setRollbackOnly();
/*  617: 669 */       throw e;
/*  618:     */     }
/*  619:     */     catch (ExcepcionAS2 e)
/*  620:     */     {
/*  621: 671 */       this.context.setRollbackOnly();
/*  622: 672 */       throw e;
/*  623:     */     }
/*  624:     */     catch (Exception e)
/*  625:     */     {
/*  626: 674 */       this.context.setRollbackOnly();
/*  627: 675 */       throw new ExcepcionAS2(e);
/*  628:     */     }
/*  629:     */   }
/*  630:     */   
/*  631:     */   public void contabilizarPagoCashEmpleado(PagoCash pagoCash, String documentoReferencia)
/*  632:     */     throws ExcepcionAS2
/*  633:     */   {
/*  634:     */     Asiento asiento;
/*  635:     */     Asiento asiento;
/*  636: 688 */     if (pagoCash.getAsiento() != null)
/*  637:     */     {
/*  638: 689 */       asiento = this.servicioAsiento.cargarDetalle(pagoCash.getAsiento().getIdAsiento());
/*  639:     */     }
/*  640:     */     else
/*  641:     */     {
/*  642: 691 */       asiento = new Asiento();
/*  643: 692 */       asiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  644: 693 */       asiento.setSucursal(AppUtil.getSucursal());
/*  645: 694 */       asiento.setTipoAsiento(pagoCash.getDocumento().getTipoAsiento());
/*  646: 695 */       asiento.setFecha(pagoCash.getFechaPago());
/*  647:     */       
/*  648: 697 */       pagoCash.setAsiento(asiento);
/*  649:     */     }
/*  650: 699 */     String concepto = "";
/*  651:     */     
/*  652: 701 */     concepto = pagoCash.getDocumento().getNombre().trim() + " #" + pagoCash.getNumero();
/*  653: 702 */     asiento.setConcepto(concepto);
/*  654:     */     
/*  655:     */ 
/*  656:     */ 
/*  657:     */ 
/*  658: 707 */     List<DetalleInterfazContable> listaDA = new ArrayList();
/*  659: 708 */     Map<Integer, DetalleInterfazContable> mapa = new HashMap();
/*  660: 709 */     BigDecimal valorAcumuladoBanco = BigDecimal.ZERO;
/*  661: 710 */     CuentaContable cuentaSueldosPorPagar = null;
/*  662:     */     
/*  663:     */ 
/*  664:     */ 
/*  665: 714 */     List<CriterioContabilizacion> listaCriterio = this.servicioDocumentoContabilizacion.getListaCriterioContabilizacion(pagoCash.getIdOrganizacion(), DocumentoBase.PAGO_ROL, ProcesoContabilizacionEnum.SUELDOS_POR_PAGAR_EMPLEADO);
/*  666: 717 */     if (listaCriterio.isEmpty()) {
/*  667: 718 */       throw new ExcepcionAS2("msg_error_criterio_contabilizacion_pago_cash_sueldos_por_pagar");
/*  668:     */     }
/*  669: 719 */     if (listaCriterio.size() > 1) {
/*  670: 720 */       throw new ExcepcionAS2("msg_error_criterio_contabilizacion_pago_cash_sueldos_por_pagar", " Existe mas de un criterio");
/*  671:     */     }
/*  672: 722 */     cuentaSueldosPorPagar = ((CriterioContabilizacion)listaCriterio.get(0)).getCuentaContable();
/*  673: 725 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash())
/*  674:     */     {
/*  675: 726 */       DetalleInterfazContable detalleIC = new DetalleInterfazContable();
/*  676:     */       
/*  677:     */ 
/*  678:     */ 
/*  679: 730 */       BigDecimal valorPago = detallePagoCash.getValor().divide(new BigDecimal(100));
/*  680: 731 */       valorAcumuladoBanco = valorAcumuladoBanco.add(valorPago);
/*  681: 732 */       detalleIC.setIdCuentaContable(cuentaSueldosPorPagar.getIdCuentaContable());
/*  682: 733 */       detalleIC.setValor(valorPago);
/*  683: 734 */       detalleIC.setReferencia1("");
/*  684: 735 */       detalleIC.setReferencia2("");
/*  685: 736 */       detalleIC.setReferencia3("");
/*  686: 737 */       detalleIC.setReferencia4("");
/*  687: 738 */       listaDA.add(detalleIC);
/*  688:     */     }
/*  689: 741 */     for (DetalleInterfazContable detalleIC : listaDA)
/*  690:     */     {
/*  691: 742 */       BigDecimal valor = detalleIC.getValor();
/*  692: 743 */       if (mapa.containsKey(Integer.valueOf(detalleIC.getIdCuentaContable())))
/*  693:     */       {
/*  694: 744 */         DetalleInterfazContable detalleAux = (DetalleInterfazContable)mapa.get(Integer.valueOf(detalleIC.getIdCuentaContable()));
/*  695: 745 */         detalleAux.setValor(valor.add(detalleAux.getValor()));
/*  696:     */       }
/*  697:     */       else
/*  698:     */       {
/*  699: 747 */         mapa.put(Integer.valueOf(detalleIC.getIdCuentaContable()), detalleIC);
/*  700:     */       }
/*  701:     */     }
/*  702: 751 */     Object listaEmpleadosPorCuenta = new ArrayList(mapa.values());
/*  703:     */     
/*  704:     */ 
/*  705: 754 */     List<DetalleInterfazContable> listaDetalleBanco = new ArrayList();
/*  706: 755 */     DetalleInterfazContable detalleICBanco = new DetalleInterfazContable();
/*  707: 756 */     detalleICBanco.setIdCuentaContable(pagoCash.getCuentaBancariaOrganizacion().getCuentaContableBanco().getIdCuentaContable());
/*  708: 757 */     detalleICBanco.setValor(valorAcumuladoBanco.negate());
/*  709: 758 */     detalleICBanco.setIdFormaPago(Integer.valueOf(pagoCash.getFormaPago().getIdFormaPago()));
/*  710: 759 */     detalleICBanco.setReferencia1("");
/*  711: 760 */     detalleICBanco.setReferencia2(FuncionesUtiles.nombreMes(pagoCash.getPagoRol().getMes() - 1) + " - " + 
/*  712: 761 */       Integer.toString(pagoCash.getPagoRol().getAnio()) + "\t (" + pagoCash.getPagoRol().getFecha() + ")");
/*  713: 762 */     detalleICBanco.setReferencia3(documentoReferencia);
/*  714: 763 */     detalleICBanco.setReferencia4("");
/*  715: 764 */     listaDetalleBanco.add(detalleICBanco);
/*  716:     */     
/*  717: 766 */     List<DetalleInterfazContable> listaDetalleInterfazContable = new ArrayList();
/*  718: 767 */     listaDetalleInterfazContable.addAll((Collection)listaEmpleadosPorCuenta);
/*  719: 768 */     listaDetalleInterfazContable.addAll(listaDetalleBanco);
/*  720:     */     
/*  721: 770 */     super.generarAsiento(asiento, listaDetalleInterfazContable, pagoCash.getDocumento());
/*  722:     */     
/*  723: 772 */     this.servicioAsiento.guardar(asiento);
/*  724:     */     
/*  725: 774 */     pagoCash.setEstado(Estado.CONTABILIZADO);
/*  726: 775 */     pagoCash.setDocumentoReferencia(documentoReferencia);
/*  727:     */     
/*  728: 777 */     this.pagoCashDao.guardar(pagoCash);
/*  729:     */   }
/*  730:     */   
/*  731:     */   public void actualizaIndicadorBloqueado(int idPagoCash, boolean bloqueo)
/*  732:     */     throws ExcepcionAS2Financiero
/*  733:     */   {
/*  734: 788 */     this.pagoCashDao.actualizaIndicadorBloqueado(idPagoCash, bloqueo);
/*  735:     */   }
/*  736:     */   
/*  737:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  738:     */   public void anularPagoCash(PagoCash pagoCash)
/*  739:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  740:     */   {
/*  741:     */     try
/*  742:     */     {
/*  743: 800 */       pagoCash = cargarDetalle(pagoCash.getId());
/*  744:     */       
/*  745: 802 */       esEditable(pagoCash, false);
/*  746:     */       
/*  747:     */ 
/*  748: 805 */       actualizaIndicadorBloqueado(pagoCash.getIdPagoCash(), false);
/*  749:     */       
/*  750:     */ 
/*  751: 808 */       actualizarEstadoPagoCash(pagoCash.getIdPagoCash(), Estado.ANULADO);
/*  752: 809 */       this.servicioOrdenPagoProveedor.reversarOrdenAlAnularPagoCash(pagoCash);
/*  753: 810 */       Map<String, String> filters = new HashMap();
/*  754: 811 */       filters.put("pagoCash.idPagoCash", "" + pagoCash.getId());
/*  755: 812 */       List<Pago> listaPagos = this.servicioPago.obtenerListaCombo("numero", true, filters);
/*  756: 813 */       for (Iterator localIterator = listaPagos.iterator(); localIterator.hasNext();)
/*  757:     */       {
/*  758: 813 */         pago = (Pago)localIterator.next();
/*  759: 814 */         this.servicioPago.anularPago(pago, false);
/*  760:     */       }
/*  761:     */       Pago pago;
/*  762: 816 */       Object listaAnticipoProveedor = this.servicioAnticipoProveedor.obtenerListaCombo("numero", true, filters);
/*  763: 817 */       for (AnticipoProveedor anticipoProveedor : (List)listaAnticipoProveedor) {
/*  764: 818 */         this.servicioAnticipoProveedor.anularAnticipoProveedor(anticipoProveedor, false);
/*  765:     */       }
/*  766: 821 */       Asiento asientoPagoCash = null;
/*  767: 822 */       asientoPagoCash = pagoCash.getAsiento();
/*  768: 823 */       if (asientoPagoCash != null)
/*  769:     */       {
/*  770: 824 */         asientoPagoCash.setIndicadorAutomatico(false);
/*  771: 825 */         this.servicioAsiento.anular(asientoPagoCash);
/*  772:     */       }
/*  773:     */     }
/*  774:     */     catch (ExcepcionAS2Financiero e)
/*  775:     */     {
/*  776: 829 */       this.context.setRollbackOnly();
/*  777: 830 */       throw e;
/*  778:     */     }
/*  779:     */     catch (Exception e)
/*  780:     */     {
/*  781: 832 */       this.context.setRollbackOnly();
/*  782: 833 */       throw new ExcepcionAS2(e);
/*  783:     */     }
/*  784:     */   }
/*  785:     */   
/*  786:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  787:     */   public void anularPagoCashEmpleado(PagoCash pagoCash)
/*  788:     */     throws ExcepcionAS2
/*  789:     */   {
/*  790:     */     try
/*  791:     */     {
/*  792: 847 */       esEditable(pagoCash);
/*  793:     */       
/*  794: 849 */       this.servicioPagoRol.actualizarPagoCash(pagoCash.getPagoRol().getIdPagoRol(), pagoCash, true, false);
/*  795:     */       
/*  796:     */ 
/*  797: 852 */       actualizarEstadoPagoCash(pagoCash.getIdPagoCash(), Estado.ANULADO);
/*  798:     */     }
/*  799:     */     catch (ExcepcionAS2Financiero e)
/*  800:     */     {
/*  801: 855 */       throw e;
/*  802:     */     }
/*  803:     */     catch (Exception e)
/*  804:     */     {
/*  805: 857 */       this.context.setRollbackOnly();
/*  806: 858 */       throw new ExcepcionAS2(e);
/*  807:     */     }
/*  808:     */   }
/*  809:     */   
/*  810:     */   public void esEditable(PagoCash pagoCash)
/*  811:     */     throws ExcepcionAS2Financiero
/*  812:     */   {
/*  813: 869 */     esEditable(pagoCash, true);
/*  814:     */   }
/*  815:     */   
/*  816:     */   public void esEditable(PagoCash pagoCash, boolean verificaContabilizado)
/*  817:     */     throws ExcepcionAS2Financiero
/*  818:     */   {
/*  819: 874 */     PagoCash pagoCashAnulado = cargarDetalleAnulacion(pagoCash.getId());
/*  820:     */     
/*  821:     */ 
/*  822: 877 */     this.servicioPeriodo.buscarPorFecha(pagoCashAnulado.getFechaPago(), pagoCashAnulado.getIdOrganizacion(), pagoCash
/*  823: 878 */       .getDocumento().getDocumentoBase());
/*  824: 881 */     if ((pagoCashAnulado.getEstado().equals(Estado.ANULADO)) || ((pagoCashAnulado.getEstado().equals(Estado.CONTABILIZADO)) && (verificaContabilizado)) || (
/*  825: 882 */       (pagoCashAnulado.getEstado().equals(Estado.APROBADO)) && (verificaContabilizado)))
/*  826:     */     {
/*  827: 883 */       LOG.info("Estado erroneo");
/*  828: 884 */       throw new ExcepcionAS2Financiero("msg_error_editar_pago_cash_contabilizado_anulado");
/*  829:     */     }
/*  830:     */   }
/*  831:     */   
/*  832:     */   public PagoCash cargarDetalleEmpleado(int idPagoCash)
/*  833:     */     throws ExcepcionAS2Financiero
/*  834:     */   {
/*  835: 895 */     return this.pagoCashDao.cargarDetalleEmpleado(idPagoCash);
/*  836:     */   }
/*  837:     */   
/*  838:     */   public List<Object[]> getDatosArchivoPagoCashEmpleado(PagoCash pagoCash, int idOrganizacion)
/*  839:     */   {
/*  840: 905 */     List<Object[]> lista = new ArrayList();
/*  841: 907 */     if (pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("10")) {
/*  842: 908 */       lista = getDatosArchivoPagoCashEmpleadoBancoPichincha(pagoCash);
/*  843: 909 */     } else if (pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("32")) {
/*  844: 910 */       lista = getDatosArchivoPagoCashEmpleadoBancoInternacional(pagoCash);
/*  845: 911 */     } else if (pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("205")) {
/*  846: 912 */       lista = getDatosArchivoPagoCashEmpleadoCoop23Julio(pagoCash);
/*  847: 913 */     } else if (pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("36")) {
/*  848: 914 */       lista = getDatosArchivoPagoCashEmpleadoProdubanco(pagoCash);
/*  849: 915 */     } else if (pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("30")) {
/*  850: 916 */       lista = getDatosArchivoPagoCashEmpleadoBancoDelPacifico(pagoCash);
/*  851: 917 */     } else if (pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("17")) {
/*  852: 918 */       lista = getDatosArchivoPagoCashEmpleadoBancoDeGuayaquil(pagoCash, idOrganizacion);
/*  853: 919 */     } else if ((pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("34")) || 
/*  854: 920 */       (pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("37"))) {
/*  855: 921 */       lista = getDatosArchivoPagoCashEmpleadoBancoBolivariano(pagoCash, idOrganizacion);
/*  856:     */     }
/*  857: 923 */     return lista;
/*  858:     */   }
/*  859:     */   
/*  860:     */   private List<Object[]> getDatosArchivoPagoCashEmpleadoBancoPichincha(PagoCash pagoCash)
/*  861:     */   {
/*  862: 927 */     List<Object[]> lista = new ArrayList();
/*  863:     */     
/*  864: 929 */     int registro = 1;
/*  865: 930 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash())
/*  866:     */     {
/*  867:     */       Object[] datos;
/*  868: 933 */       if (ParametrosSistema.isPagoCashProveedorShort(pagoCash.getIdOrganizacion()).booleanValue())
/*  869:     */       {
/*  870: 934 */         Object[] datos = new Object[12];
/*  871: 935 */         datos[0] = "PA";
/*  872: 936 */         datos[1] = String.valueOf(registro);
/*  873: 937 */         datos[2] = "USD";
/*  874: 938 */         datos[3] = detallePagoCash.getValor().toString().substring(0, detallePagoCash.getValor().toString().lastIndexOf("."));
/*  875:     */         
/*  876:     */ 
/*  877: 941 */         datos[4] = "CTA";
/*  878: 942 */         datos[5] = detallePagoCash.getTipoCuentaBancaria();
/*  879: 943 */         datos[6] = detallePagoCash.getCuentaBancariaEmpleado();
/*  880:     */         
/*  881:     */ 
/*  882: 946 */         datos[7] = detallePagoCash.getReferenciaPagoEmpleado();
/*  883: 947 */         datos[8] = detallePagoCash.getEmpleado().getEmpresa().getTipoIdentificacion().getCodigo();
/*  884: 948 */         datos[9] = detallePagoCash.getIdentificacionEmpleado();
/*  885: 949 */         datos[10] = (detallePagoCash.getEmpleado().getApellidos() + " " + detallePagoCash.getEmpleado().getNombres());
/*  886:     */         
/*  887: 951 */         datos[11] = detallePagoCash.getCodigoBancoCuentaBancariaEmpleado();
/*  888:     */         
/*  889:     */ 
/*  890: 954 */         registro++;
/*  891:     */       }
/*  892:     */       else
/*  893:     */       {
/*  894: 957 */         datos = new Object[19];
/*  895: 958 */         datos[0] = "PA";
/*  896: 959 */         datos[1] = detallePagoCash.getCuentaBancariaEmpresa();
/*  897: 960 */         datos[2] = String.valueOf(registro);
/*  898: 961 */         datos[3] = "10A";
/*  899: 962 */         datos[4] = detallePagoCash.getEmpleado().getEmpresa().getIdentificacion();
/*  900: 963 */         datos[5] = "USD";
/*  901:     */         
/*  902: 965 */         datos[6] = detallePagoCash.getValor().toString().substring(0, detallePagoCash.getValor().toString().lastIndexOf("."));
/*  903: 966 */         datos[7] = "CTA";
/*  904: 967 */         datos[8] = detallePagoCash.getCodigoBancoCuentaBancariaEmpleado();
/*  905: 968 */         datos[9] = detallePagoCash.getTipoCuentaBancaria();
/*  906:     */         
/*  907: 970 */         datos[10] = detallePagoCash.getCuentaBancariaEmpleado();
/*  908: 971 */         datos[11] = detallePagoCash.getEmpleado().getEmpresa().getTipoIdentificacion().getCodigo();
/*  909: 972 */         datos[12] = detallePagoCash.getEmpleado().getEmpresa().getIdentificacion();
/*  910: 973 */         datos[13] = (detallePagoCash.getEmpleado().getApellidos() + " " + detallePagoCash.getEmpleado().getNombres());
/*  911: 974 */         datos[14] = detallePagoCash.getDireccionEmpleado();
/*  912: 975 */         datos[15] = detallePagoCash.getCiudadEmpleado();
/*  913: 976 */         datos[16] = detallePagoCash.getTelefonoEmpleado();
/*  914: 977 */         datos[17] = detallePagoCash.getReferenciaPagoEmpleado();
/*  915:     */         
/*  916: 979 */         datos[18] = ("|" + detallePagoCash.getEmpleado().getEmpresa().getEmail1() + "|");
/*  917:     */         
/*  918: 981 */         registro++;
/*  919:     */       }
/*  920: 984 */       lista.add(datos);
/*  921:     */     }
/*  922: 988 */     return lista;
/*  923:     */   }
/*  924:     */   
/*  925:     */   private List<Object[]> getDatosArchivoPagoCashEmpleadoProdubanco(PagoCash pagoCash)
/*  926:     */   {
/*  927: 994 */     List<Object[]> lista = new ArrayList();
/*  928: 995 */     BigDecimal total = BigDecimal.ZERO;
/*  929: 996 */     Object[] cabecera = new Object[1];
/*  930:     */     
/*  931: 998 */     String anno = Integer.toString(pagoCash.getPagoRol().getFecha().getYear()).substring(1, 3);
/*  932: 999 */     String mes = Integer.toString(pagoCash.getPagoRol().getFecha().getMonth() + 1);
/*  933:1000 */     String dia = Integer.toString(pagoCash.getPagoRol().getFecha().getDate());
/*  934:1002 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash())
/*  935:     */     {
/*  936:1003 */       total = total.add(detallePagoCash.getValor());
/*  937:     */       
/*  938:1005 */       String a = detallePagoCash.getEmpleado().getApellidos() + " " + detallePagoCash.getEmpleado().getNombres();
/*  939:1006 */       if (a.length() > 40) {
/*  940:1007 */         a = a.substring(0, 40);
/*  941:     */       } else {
/*  942:1009 */         a = FuncionesUtiles.completarALaDerecha(' ', 40, a);
/*  943:     */       }
/*  944:1013 */       String b = detallePagoCash.getCuentaBancariaEmpleado().length() < 11 ? FuncionesUtiles.completarALaIzquierda('0', 11, detallePagoCash.getCuentaBancariaEmpleado()) : detallePagoCash.getCuentaBancariaEmpleado();
/*  945:     */       
/*  946:1015 */       Object[] detalles = new Object[1];
/*  947:1016 */       detalles[0] = 
/*  948:     */       
/*  949:     */ 
/*  950:1019 */         ("C" + a + b + FuncionesUtiles.completarALaIzquierda('0', 14, detallePagoCash.getValor().toString().substring(0, detallePagoCash.getValor().toString().lastIndexOf("."))) + FuncionesUtiles.completarALaIzquierda('0', 2, dia) + FuncionesUtiles.completarALaIzquierda('0', 2, mes) + anno + "N".trim());
/*  951:     */       
/*  952:1021 */       lista.add(detalles);
/*  953:     */     }
/*  954:1024 */     String b = AppUtil.getOrganizacion().getRazonSocial();
/*  955:1025 */     if (b.length() > 40) {
/*  956:1026 */       b = b.substring(0, 40);
/*  957:     */     } else {
/*  958:1028 */       b = FuncionesUtiles.completarALaDerecha(' ', 40, b);
/*  959:     */     }
/*  960:1033 */     cabecera[0] = ("D" + b + ((DetallePagoCash)pagoCash.getListaDetallePagoCash().get(0)).getCuentaBancariaEmpresa() + FuncionesUtiles.completarALaIzquierda('0', 14, total.toString().substring(0, total.toString().lastIndexOf("."))) + FuncionesUtiles.completarALaIzquierda('0', 2, dia) + FuncionesUtiles.completarALaIzquierda('0', 2, mes) + anno + "N".trim());
/*  961:1034 */     lista.add(0, cabecera);
/*  962:     */     
/*  963:1036 */     return lista;
/*  964:     */   }
/*  965:     */   
/*  966:     */   private List<Object[]> getDatosArchivoPagoCashEmpleadoBancoInternacional(PagoCash pagoCash)
/*  967:     */   {
/*  968:1041 */     List<Object[]> lista = new ArrayList();
/*  969:1042 */     List<Object[]> listaCuentaBancariaEmpleado = this.empleadoDao.getCuentaBancariaEmpleado();
/*  970:1043 */     Map<Integer, Banco> hashMapCodigoBancoEmpleado = new HashMap();
/*  971:1044 */     int idEmpleado = 0;
/*  972:1045 */     for (Object[] object : listaCuentaBancariaEmpleado)
/*  973:     */     {
/*  974:1046 */       idEmpleado = Integer.parseInt(object[0].toString());
/*  975:1047 */       hashMapCodigoBancoEmpleado.put(Integer.valueOf(idEmpleado), (Banco)object[3]);
/*  976:     */     }
/*  977:1050 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash())
/*  978:     */     {
/*  979:1052 */       Object[] datos = new Object[12];
/*  980:1053 */       datos[0] = "PA";
/*  981:1054 */       datos[1] = detallePagoCash.getCuentaBancariaEmpleado();
/*  982:1055 */       datos[2] = "USD";
/*  983:1056 */       datos[3] = detallePagoCash.getValor().toString().substring(0, detallePagoCash.getValor().toString().lastIndexOf("."));
/*  984:1057 */       datos[4] = "CTA";
/*  985:1058 */       datos[5] = detallePagoCash.getTipoCuentaBancaria();
/*  986:1059 */       datos[6] = detallePagoCash.getCuentaBancariaEmpleado();
/*  987:1060 */       datos[7] = "Sueldo Pago Rol";
/*  988:1061 */       datos[8] = detallePagoCash.getEmpleado().getEmpresa().getTipoIdentificacion().getCodigo();
/*  989:1062 */       datos[9] = detallePagoCash.getIdentificacionEmpleado();
/*  990:     */       
/*  991:1064 */       String b = detallePagoCash.getEmpleado().getApellidos() + " " + detallePagoCash.getEmpleado().getNombres();
/*  992:1065 */       b = remove1(b.toString().trim().toUpperCase());
/*  993:1066 */       datos[10] = (b.length() > 41 ? b.substring(0, 41) : b);
/*  994:1067 */       datos[11] = ((Banco)hashMapCodigoBancoEmpleado.get(Integer.valueOf(detallePagoCash.getEmpleado().getEmpresa().getIdEmpresa()))).getCodigo();
/*  995:     */       
/*  996:1069 */       Object[] datos2 = new Object[1];
/*  997:1070 */       datos2[0] = (datos[0] + "\t" + datos[1] + "\t" + datos[2] + "\t" + datos[3] + "\t" + datos[4] + "\t" + datos[5] + "\t" + datos[6] + "\t" + datos[7] + "\t" + datos[8] + "\t" + datos[9] + "\t" + datos[10] + "\t" + datos[11]);
/*  998:     */       
/*  999:1072 */       lista.add(datos2);
/* 1000:     */     }
/* 1001:1076 */     return lista;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   private List<Object[]> getDatosArchivoPagoCashEmpleadoCoop23Julio(PagoCash pagoCash)
/* 1005:     */   {
/* 1006:1080 */     List<Object[]> lista = new ArrayList();
/* 1007:1082 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash())
/* 1008:     */     {
/* 1009:1084 */       String a = detallePagoCash.getValor().divide(new BigDecimal(100), RoundingMode.HALF_UP).toString().replace(".", ",");
/* 1010:1085 */       Object[] datos = new Object[2];
/* 1011:1086 */       datos[0] = detallePagoCash.getCuentaBancariaEmpleado();
/* 1012:1087 */       datos[1] = a;
/* 1013:1088 */       lista.add(datos);
/* 1014:     */     }
/* 1015:1092 */     return lista;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   private List<Object[]> getDatosArchivoPagoCashEmpleadoBancoDelPacifico(PagoCash pagoCash)
/* 1019:     */   {
/* 1020:1098 */     List<Object[]> lista = new ArrayList();
/* 1021:1099 */     List<Object[]> listaCuentaBancariaEmpleado = this.empleadoDao.getCuentaBancariaEmpleado();
/* 1022:1100 */     Map<Integer, Banco> hashMapCodigoBancoEmpleado = new HashMap();
/* 1023:     */     
/* 1024:1102 */     int idEmpleado = 0;
/* 1025:1103 */     for (Object[] object : listaCuentaBancariaEmpleado)
/* 1026:     */     {
/* 1027:1104 */       idEmpleado = Integer.parseInt(object[0].toString());
/* 1028:1105 */       hashMapCodigoBancoEmpleado.put(Integer.valueOf(idEmpleado), (Banco)object[3]);
/* 1029:     */     }
/* 1030:1108 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash()) {
/* 1031:1109 */       if (pagoCash.getTipoServicioCuentaBancaria().equals(TipoServicioCuentaBancariaEnum.RU))
/* 1032:     */       {
/* 1033:1110 */         Object[] datos = new Object[17];
/* 1034:1111 */         datos[0] = pagoCash.getCuentaBancariaOrganizacion().getLocalidad();
/* 1035:1112 */         datos[1] = "OCP";
/* 1036:1113 */         datos[2] = detallePagoCash.getTipoServicioCuentaBancaria();
/* 1037:1114 */         datos[3] = (detallePagoCash.getTipoCuentaBancaria().equals("AHO") ? "10" : "00");
/* 1038:1115 */         datos[4] = "00000000";
/* 1039:1116 */         datos[5] = FuncionesUtiles.completarALaIzquierda('0', 15, detallePagoCash
/* 1040:1117 */           .getValor().toString().substring(0, detallePagoCash.getValor().toString().lastIndexOf(".")));
/* 1041:1118 */         datos[6] = FuncionesUtiles.completarALaDerecha(' ', 15, detallePagoCash.getIdentificacionEmpleado());
/* 1042:1119 */         datos[7] = "PAGO PACIFICO       ";
/* 1043:1120 */         datos[8] = "CU";
/* 1044:1121 */         datos[9] = "USD";
/* 1045:1122 */         String b = remove1(detallePagoCash.getEmpleado().getEmpresa().getNombreFiscal().toUpperCase());
/* 1046:1123 */         datos[10] = (b.length() > 30 ? b.substring(0, 30) : FuncionesUtiles.completarALaDerecha(' ', 30, b));
/* 1047:1124 */         datos[11] = "    ";
/* 1048:1125 */         datos[12] = detallePagoCash.getEmpleado().getEmpresa().getTipoIdentificacion().getCodigo();
/* 1049:1126 */         datos[13] = FuncionesUtiles.completarALaDerecha(' ', 15, detallePagoCash.getIdentificacionEmpleado());
/* 1050:1127 */         datos[14] = "                                                            ";
/* 1051:1128 */         datos[15] = ((Banco)hashMapCodigoBancoEmpleado.get(Integer.valueOf(detallePagoCash.getEmpleado().getEmpresa().getIdEmpresa()))).getCodigo();
/* 1052:1129 */         datos[16] = FuncionesUtiles.completarALaDerecha(' ', 20, detallePagoCash.getCuentaBancariaEmpleado().trim());
/* 1053:1130 */         Object[] datos2 = new Object[1];
/* 1054:1131 */         datos2[0] = (datos[0] + "" + datos[1] + "" + datos[2] + "" + datos[3] + "" + datos[4] + "" + datos[5] + "" + datos[6] + "" + datos[7] + "" + datos[8] + "" + datos[9] + "" + datos[10] + "" + datos[11] + "" + datos[12] + "" + datos[13] + "" + datos[14] + "" + datos[15] + "" + datos[16]);
/* 1055:     */         
/* 1056:     */ 
/* 1057:1134 */         lista.add(datos2);
/* 1058:     */       }
/* 1059:     */       else
/* 1060:     */       {
/* 1061:1136 */         Object[] datos = new Object[15];
/* 1062:1137 */         datos[0] = pagoCash.getCuentaBancariaOrganizacion().getLocalidad();
/* 1063:1138 */         datos[1] = "OCP";
/* 1064:1139 */         datos[2] = detallePagoCash.getTipoServicioCuentaBancaria();
/* 1065:1140 */         datos[3] = (detallePagoCash.getTipoCuentaBancaria().equals("AHO") ? "10" : "00");
/* 1066:1141 */         datos[4] = (detallePagoCash.getCuentaBancariaEmpleado().length() > 8 ? detallePagoCash.getCuentaBancariaEmpleado().substring(0, 8) : 
/* 1067:1142 */           FuncionesUtiles.completarALaIzquierda('0', 8, detallePagoCash.getCuentaBancariaEmpleado()));
/* 1068:1143 */         datos[5] = FuncionesUtiles.completarALaIzquierda('0', 15, detallePagoCash
/* 1069:1144 */           .getValor().toString().substring(0, detallePagoCash.getValor().toString().lastIndexOf(".")));
/* 1070:1145 */         datos[6] = FuncionesUtiles.completarALaDerecha(' ', 15, detallePagoCash.getIdentificacionEmpleado());
/* 1071:     */         
/* 1072:1147 */         PagoRol pagoRol = this.servicioPagoRol.cargarDetalle(pagoCash.getPagoRol().getIdPagoRol());
/* 1073:     */         
/* 1074:     */ 
/* 1075:1150 */         String referen = "PACIFICO " + pagoRol.getQuincena().getCodigo() + FuncionesUtiles.getMes(pagoRol.getFecha()) + FuncionesUtiles.getAnio(pagoRol.getFecha());
/* 1076:1151 */         datos[7] = (referen.length() > 20 ? referen.substring(0, 20) : FuncionesUtiles.completarALaDerecha(' ', 20, referen));
/* 1077:     */         
/* 1078:1153 */         String a = "CU";
/* 1079:1154 */         if ((detallePagoCash.getCuentaBancariaEmpleado().trim().equals("CH")) || 
/* 1080:1155 */           (detallePagoCash.getCuentaBancariaEmpleado().trim().equals("EF")))
/* 1081:     */         {
/* 1082:1156 */           a = detallePagoCash.getCuentaBancariaEmpleado().trim();
/* 1083:1157 */           datos[3] = "  ";
/* 1084:1158 */           datos[4] = "        ";
/* 1085:     */         }
/* 1086:1161 */         datos[8] = a;
/* 1087:     */         
/* 1088:1163 */         datos[9] = "USD";
/* 1089:     */         
/* 1090:1165 */         String b = remove1(detallePagoCash.getEmpleado().getEmpresa().getNombreFiscal().toUpperCase());
/* 1091:1166 */         datos[10] = (b.length() > 30 ? b.substring(0, 30) : FuncionesUtiles.completarALaDerecha(' ', 30, b));
/* 1092:1167 */         datos[11] = "    ";
/* 1093:1168 */         datos[12] = detallePagoCash.getEmpleado().getEmpresa().getTipoIdentificacion().getCodigo();
/* 1094:1169 */         datos[13] = FuncionesUtiles.completarALaDerecha(' ', 14, detallePagoCash.getIdentificacionEmpleado());
/* 1095:1170 */         datos[14] = "          ";
/* 1096:     */         
/* 1097:1172 */         Object[] datos2 = new Object[1];
/* 1098:1173 */         datos2[0] = (datos[0] + "" + datos[1] + "" + datos[2] + "" + datos[3] + "" + datos[4] + "" + datos[5] + "" + datos[6] + "" + datos[7] + "" + datos[8] + "" + datos[9] + "" + datos[10] + "" + datos[11] + "" + datos[12] + "" + datos[13] + "" + datos[14]);
/* 1099:     */         
/* 1100:1175 */         lista.add(datos2);
/* 1101:     */       }
/* 1102:     */     }
/* 1103:1181 */     return lista;
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   private List<Object[]> getDatosArchivoPagoCashEmpleadoBancoDeGuayaquil(PagoCash pagoCash, int idOrganizacion)
/* 1107:     */   {
/* 1108:1185 */     List<Object[]> lista = new ArrayList();
/* 1109:     */     
/* 1110:1187 */     List<Object[]> listaCuentaBancariaEmpleado = this.empleadoDao.getCuentaBancariaEmpleado();
/* 1111:1188 */     List<Object[]> listaDireccionTelefonoEmpleado = this.empleadoDao.getDireccionTelefonoEmpleado(idOrganizacion);
/* 1112:1189 */     Map<Integer, Banco> hashMapCodigoBancoEmpleado = new HashMap();
/* 1113:1190 */     Map<Integer, Object> hashMapCelularEmpleado = new HashMap();
/* 1114:     */     
/* 1115:1192 */     int idEmpleado = 0;
/* 1116:1193 */     for (Object[] object : listaCuentaBancariaEmpleado)
/* 1117:     */     {
/* 1118:1194 */       idEmpleado = Integer.parseInt(object[0].toString());
/* 1119:1195 */       hashMapCodigoBancoEmpleado.put(Integer.valueOf(idEmpleado), (Banco)object[3]);
/* 1120:     */     }
/* 1121:1198 */     for (Object[] object : listaDireccionTelefonoEmpleado)
/* 1122:     */     {
/* 1123:1199 */       idEmpleado = Integer.parseInt(object[0].toString());
/* 1124:1200 */       hashMapCelularEmpleado.put(Integer.valueOf(idEmpleado), object[1].toString().length() != 10 ? "9999999999" : object[1].toString());
/* 1125:     */     }
/* 1126:1203 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash()) {
/* 1127:1205 */       if (pagoCash.getTipoServicioCuentaBancaria().equals(TipoServicioCuentaBancariaEnum.RP))
/* 1128:     */       {
/* 1129:1206 */         Object[] datos = new Object[9];
/* 1130:1207 */         datos[0] = (detallePagoCash.getTipoCuentaBancaria().equals("AHO") ? "A" : "C");
/* 1131:     */         
/* 1132:1209 */         datos[1] = (detallePagoCash.getCuentaBancariaEmpleado().length() < 10 ? 
/* 1133:1210 */           FuncionesUtiles.completarALaIzquierda('0', 10, detallePagoCash.getCuentaBancariaEmpleado()) : detallePagoCash
/* 1134:1211 */           .getCuentaBancariaEmpleado());
/* 1135:     */         
/* 1136:1213 */         datos[2] = FuncionesUtiles.completarALaIzquierda('0', 15, detallePagoCash
/* 1137:1214 */           .getValor().toString().substring(0, detallePagoCash.getValor().toString().lastIndexOf(".")));
/* 1138:1215 */         datos[3] = "XX";
/* 1139:1216 */         datos[4] = "Y";
/* 1140:1217 */         datos[5] = pagoCash.getCuentaBancariaOrganizacion().getLocalidad();
/* 1141:1218 */         datos[6] = "                    ";
/* 1142:1219 */         String nombreEmpleado = remove1(detallePagoCash.getEmpleado().getNombreCompleto());
/* 1143:1220 */         datos[7] = (nombreEmpleado.toUpperCase().length() > 18 ? nombreEmpleado.substring(0, 18).toUpperCase() : 
/* 1144:1221 */           FuncionesUtiles.completarALaIzquierda('0', 18, nombreEmpleado.toUpperCase()));
/* 1145:1222 */         datos[8] = pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getMotivo();
/* 1146:     */         
/* 1147:1224 */         Object[] datos2 = new Object[1];
/* 1148:1225 */         datos2[0] = (datos[0] + "" + datos[1] + "" + datos[2] + "" + datos[3] + "" + datos[4] + "" + datos[5] + "" + datos[6] + "" + datos[7] + "" + datos[8]);
/* 1149:     */         
/* 1150:1227 */         lista.add(datos2);
/* 1151:     */       }
/* 1152:     */       else
/* 1153:     */       {
/* 1154:1230 */         Object[] datos = new Object[12];
/* 1155:1231 */         datos[0] = (detallePagoCash.getTipoCuentaBancaria().equals("AHO") ? "A" : "C");
/* 1156:1232 */         datos[1] = FuncionesUtiles.completarALaIzquierda('0', 25, detallePagoCash
/* 1157:1233 */           .getValor().toString().substring(0, detallePagoCash.getValor().toString().lastIndexOf(".")));
/* 1158:1234 */         datos[2] = "XX";
/* 1159:1235 */         datos[3] = "Y";
/* 1160:1236 */         datos[4] = pagoCash.getCuentaBancariaOrganizacion().getLocalidad();
/* 1161:1237 */         String codigoBanco = ((Banco)hashMapCodigoBancoEmpleado.get(Integer.valueOf(detallePagoCash.getEmpleado().getEmpresa().getIdEmpresa()))).getCodigo();
/* 1162:1238 */         if (codigoBanco.length() > 2)
/* 1163:     */         {
/* 1164:1239 */           datos[5] = "XX";
/* 1165:1240 */           datos[9] = codigoBanco;
/* 1166:     */         }
/* 1167:     */         else
/* 1168:     */         {
/* 1169:1242 */           datos[5] = codigoBanco;
/* 1170:1243 */           datos[9] = "   ";
/* 1171:     */         }
/* 1172:1246 */         datos[6] = FuncionesUtiles.completarALaIzquierda('0', 18, detallePagoCash.getCuentaBancariaEmpleado());
/* 1173:1247 */         String nombreEmpleado = remove1(detallePagoCash.getEmpleado().getNombreCompleto());
/* 1174:1248 */         datos[7] = (nombreEmpleado.toUpperCase().length() > 18 ? nombreEmpleado.substring(0, 18).toUpperCase() : 
/* 1175:1249 */           FuncionesUtiles.completarALaDerecha(' ', 18, nombreEmpleado.toUpperCase()));
/* 1176:1250 */         datos[8] = pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getMotivo();
/* 1177:1251 */         datos[10] = detallePagoCash.getEmpleado().getEmpresa().getTipoIdentificacion().getCodigo();
/* 1178:1252 */         datos[11] = FuncionesUtiles.completarALaDerecha(' ', 13, detallePagoCash.getIdentificacionEmpleado());
/* 1179:     */         
/* 1180:1254 */         Object[] datos2 = new Object[1];
/* 1181:1255 */         datos2[0] = (datos[0] + "" + datos[1] + "" + datos[2] + "" + datos[3] + "" + datos[4] + "" + datos[5] + "" + datos[6] + "" + datos[7] + "" + datos[8] + "" + datos[9] + "" + datos[10] + "" + datos[11]);
/* 1182:     */         
/* 1183:1257 */         lista.add(datos2);
/* 1184:     */       }
/* 1185:     */     }
/* 1186:1262 */     return lista;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   private List<Object[]> getDatosArchivoPagoCashEmpleadoBancoBolivariano(PagoCash pagoCash, int idOrganizacion)
/* 1190:     */   {
/* 1191:1267 */     int secuencial = 1;
/* 1192:1268 */     List<Object[]> lista = new ArrayList();
/* 1193:1269 */     List<Object[]> listaCuentaBancariaEmpleado = this.empleadoDao.getCuentaBancariaEmpleado();
/* 1194:1270 */     List<Object[]> listaDireccionTelefonoEmpleado = this.empleadoDao.getDireccionTelefonoEmpleado(idOrganizacion);
/* 1195:1271 */     Map<Integer, Banco> hashMapCodigoBancoEmpleado = new HashMap();
/* 1196:1272 */     Map<Integer, Object> hashMapCelularEmpleado = new HashMap();
/* 1197:1273 */     Map<Integer, TipoCuentaBancaria> hashMapTipoCuentaBancariaEmpleado = new HashMap();
/* 1198:1274 */     Map<Integer, String> hashMapCuentaBancariaEmpleado = new HashMap();
/* 1199:1275 */     Map<Integer, TipoServicioCuentaBancariaEnum> hashMapTipoServicioCuentaBancaria = new HashMap();
/* 1200:     */     
/* 1201:1277 */     int idEmpleado = 0;
/* 1202:1278 */     for (Object[] object : listaCuentaBancariaEmpleado)
/* 1203:     */     {
/* 1204:1279 */       idEmpleado = Integer.parseInt(object[0].toString());
/* 1205:1280 */       hashMapCodigoBancoEmpleado.put(Integer.valueOf(idEmpleado), (Banco)object[3]);
/* 1206:1281 */       hashMapCuentaBancariaEmpleado.put(Integer.valueOf(idEmpleado), object[1].toString());
/* 1207:1282 */       hashMapTipoCuentaBancariaEmpleado.put(Integer.valueOf(idEmpleado), (TipoCuentaBancaria)object[2]);
/* 1208:1283 */       hashMapTipoServicioCuentaBancaria.put(Integer.valueOf(idEmpleado), (TipoServicioCuentaBancariaEnum)object[5]);
/* 1209:     */     }
/* 1210:1286 */     for (Object[] object : listaDireccionTelefonoEmpleado)
/* 1211:     */     {
/* 1212:1287 */       idEmpleado = Integer.parseInt(object[0].toString());
/* 1213:1288 */       hashMapCelularEmpleado.put(Integer.valueOf(idEmpleado), object[1].toString().length() != 10 ? "9999999999" : object[1].toString());
/* 1214:     */     }
/* 1215:1291 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash())
/* 1216:     */     {
/* 1217:1293 */       Object[] datos = new Object[30];
/* 1218:1294 */       datos[0] = "BZDET";
/* 1219:1295 */       datos[1] = FuncionesUtiles.completarALaIzquierda('0', 6, Integer.toString(secuencial++));
/* 1220:1296 */       datos[2] = (detallePagoCash.getCuentaBancariaEmpleado().trim().length() > 18 ? detallePagoCash
/* 1221:1297 */         .getCuentaBancariaEmpleado().trim().substring(0, 18) : 
/* 1222:1298 */         FuncionesUtiles.completarALaDerecha(' ', 18, detallePagoCash.getCuentaBancariaEmpleado().trim()));
/* 1223:1299 */       datos[3] = detallePagoCash.getEmpleado().getEmpresa().getTipoIdentificacion().getCodigo().trim();
/* 1224:1300 */       datos[4] = FuncionesUtiles.completarALaDerecha(' ', 14, detallePagoCash.getIdentificacionEmpleado());
/* 1225:1301 */       String nombreEmpleado = remove1(detallePagoCash.getEmpleado().getNombreCompleto());
/* 1226:1302 */       datos[5] = (nombreEmpleado.length() > 60 ? nombreEmpleado.substring(0, 60) : 
/* 1227:1303 */         FuncionesUtiles.completarALaDerecha(' ', 60, nombreEmpleado));
/* 1228:1304 */       TipoServicioCuentaBancariaEnum tipoServicioCuentaBancaria = (TipoServicioCuentaBancariaEnum)hashMapTipoServicioCuentaBancaria.get(Integer.valueOf(detallePagoCash.getEmpleado().getEmpresa().getIdEmpresa()));
/* 1229:     */       
/* 1230:1306 */       String formaPago = tipoServicioCuentaBancaria.equals(TipoServicioCuentaBancariaEnum.COB) ? "COB" : tipoServicioCuentaBancaria == null ? "CUE" : "CUE";
/* 1231:1307 */       datos[6] = formaPago;
/* 1232:1308 */       datos[7] = "001";
/* 1233:1309 */       datos[8] = 
/* 1234:1310 */         (formaPago.equals("COB") ? 
/* 1235:1311 */         ((Banco)hashMapCodigoBancoEmpleado.get(Integer.valueOf(detallePagoCash.getEmpleado().getEmpresa().getIdEmpresa()))).getCodigo().toString().trim() : formaPago.equals("CUE") ? "34" : 
/* 1236:1312 */         FuncionesUtiles.completarALaDerecha(' ', 2, ""));
/* 1237:1313 */       TipoCuentaBancaria tcb = (TipoCuentaBancaria)hashMapTipoCuentaBancariaEmpleado.get(Integer.valueOf(detallePagoCash.getEmpleado().getEmpresa().getIdEmpresa()));
/* 1238:     */       
/* 1239:1315 */       String codigoTipoCuentaBancaria = tcb.getCodigo().trim().toUpperCase().equals("CTE") ? "03" : tcb.getCodigo().trim().toUpperCase().equals("AHO") ? "04" : FuncionesUtiles.completarALaDerecha(' ', 2, "");
/* 1240:1316 */       datos[9] = ((formaPago.equals("CUE")) || (formaPago.equals("COB")) ? codigoTipoCuentaBancaria : FuncionesUtiles.completarALaDerecha(' ', 2, ""));
/* 1241:     */       
/* 1242:1318 */       datos[10] = ((formaPago.equals("CUE")) || (formaPago.equals("COB")) ? 
/* 1243:1319 */         FuncionesUtiles.completarALaDerecha(' ', 20, 
/* 1244:1320 */         ((String)hashMapCuentaBancariaEmpleado.get(Integer.valueOf(detallePagoCash.getEmpleado().getEmpresa().getIdEmpresa()))).toString().trim()) : 
/* 1245:1321 */         FuncionesUtiles.completarALaDerecha(' ', 20, ""));
/* 1246:1322 */       datos[11] = "1";
/* 1247:1323 */       datos[12] = FuncionesUtiles.completarALaIzquierda('0', 15, detallePagoCash
/* 1248:1324 */         .getValor().toString().substring(0, detallePagoCash.getValor().toString().lastIndexOf(".")));
/* 1249:1325 */       datos[13] = FuncionesUtiles.completarALaDerecha(' ', 60, "SUELDO PAGO ROL");
/* 1250:     */       
/* 1251:1327 */       Secuencia secuencia = pagoCash.getDocumento().getSecuencia();
/* 1252:1328 */       String numeroString = pagoCash.getNumero();
/* 1253:1329 */       if ((secuencia.getPrefijo() != null) && (!secuencia.getPrefijo().isEmpty()))
/* 1254:     */       {
/* 1255:1330 */         String[] cadena = numeroString.split(secuencia.getPrefijo());
/* 1256:1331 */         numeroString = cadena[1];
/* 1257:     */       }
/* 1258:1333 */       if ((secuencia.getSufijo() != null) && (!secuencia.getSufijo().isEmpty()))
/* 1259:     */       {
/* 1260:1334 */         String[] cadena = numeroString.split(secuencia.getSufijo());
/* 1261:1335 */         numeroString = cadena[0];
/* 1262:     */       }
/* 1263:1337 */       Integer numero = Integer.valueOf(numeroString);
/* 1264:1338 */       datos[14] = FuncionesUtiles.completarALaIzquierda('0', 15, Integer.toString(numero.intValue()));
/* 1265:1339 */       datos[15] = "000000000000000";
/* 1266:1340 */       datos[16] = "000000000000000";
/* 1267:1341 */       datos[17] = "00000000000000000000";
/* 1268:1342 */       datos[18] = "          ";
/* 1269:1343 */       datos[19] = "                                                  ";
/* 1270:1344 */       datos[20] = "                                                  ";
/* 1271:1345 */       datos[21] = "                    ";
/* 1272:1346 */       datos[22] = "RPA";
/* 1273:1347 */       datos[23] = "          ";
/* 1274:1348 */       datos[24] = "          ";
/* 1275:1349 */       datos[25] = "          ";
/* 1276:1350 */       datos[26] = " ";
/* 1277:1351 */       datos[27] = FuncionesUtiles.completarALaIzquierda('0', 5, pagoCash.getCuentaBancariaOrganizacion().getDescripcion() == null ? "" : pagoCash
/* 1278:1352 */         .getCuentaBancariaOrganizacion().getDescripcion());
/* 1279:1353 */       datos[28] = "      ";
/* 1280:1354 */       datos[29] = "RPA";
/* 1281:     */       
/* 1282:1356 */       Object[] datos2 = new Object[1];
/* 1283:1357 */       datos2[0] = (datos[0] + "" + datos[1] + "" + datos[2] + "" + datos[3] + "" + datos[4] + "" + datos[5] + "" + datos[6] + "" + datos[7] + "" + datos[8] + "" + datos[9] + "" + datos[10] + "" + datos[11] + "" + datos[12] + "" + datos[13] + "" + datos[14] + "" + datos[15] + "" + datos[16] + "" + datos[17] + "" + datos[18] + "" + datos[19] + "" + datos[20] + "" + datos[21] + "" + datos[22] + "" + datos[23] + "" + datos[24] + "" + datos[25] + "" + datos[26] + "" + datos[27] + "" + datos[28] + "" + datos[29]);
/* 1284:     */       
/* 1285:     */ 
/* 1286:     */ 
/* 1287:1361 */       lista.add(datos2);
/* 1288:     */     }
/* 1289:1365 */     return lista;
/* 1290:     */   }
/* 1291:     */   
/* 1292:     */   public static String remove1(String input)
/* 1293:     */   {
/* 1294:1369 */     String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
/* 1295:1370 */     String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
/* 1296:1371 */     String output = input;
/* 1297:1372 */     for (int i = 0; i < original.length(); i++) {
/* 1298:1373 */       output = output.replace(original.charAt(i), ascii.charAt(i));
/* 1299:     */     }
/* 1300:1375 */     return output;
/* 1301:     */   }
/* 1302:     */   
/* 1303:     */   public List<Object[]> datosPagoCash(int idOrganizacion, PagoCash pagoCash)
/* 1304:     */   {
/* 1305:1380 */     return this.pagoCashDao.datosPagoCash(idOrganizacion, pagoCash);
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   public List<Object[]> datosPagoCashEmpleado(PagoCash pagoCash)
/* 1309:     */   {
/* 1310:1385 */     return this.pagoCashDao.datosPagoCashEmpleado(pagoCash);
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   public void actualizarAtributoEntidad(PagoCash pagoCash, HashMap<String, Object> campos)
/* 1314:     */   {
/* 1315:1390 */     this.pagoCashDao.actualizarAtributoEntidad(pagoCash, campos);
/* 1316:     */   }
/* 1317:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.procesos.servicio.impl.ServicioPagoCashImpl
 * JD-Core Version:    0.7.0.1
 */