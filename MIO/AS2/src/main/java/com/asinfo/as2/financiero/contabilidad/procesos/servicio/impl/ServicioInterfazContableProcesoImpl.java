/*    1:     */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.amortizacion.procesos.servicio.ServicioAmortizacion;
/*    4:     */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*    5:     */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*    6:     */ import com.asinfo.as2.dao.CajaDao;
/*    7:     */ import com.asinfo.as2.dao.CargaBSPDao;
/*    8:     */ import com.asinfo.as2.dao.CassDao;
/*    9:     */ import com.asinfo.as2.dao.DespachoClienteDao;
/*   10:     */ import com.asinfo.as2.dao.DetalleCierreCajaDao;
/*   11:     */ import com.asinfo.as2.dao.DetalleValoresContratoVentaDao;
/*   12:     */ import com.asinfo.as2.dao.ExtractoBancarioDao;
/*   13:     */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   14:     */ import com.asinfo.as2.dao.GenericoDao;
/*   15:     */ import com.asinfo.as2.dao.InterfazContableProcesoDao;
/*   16:     */ import com.asinfo.as2.dao.MovimientoInventarioDao;
/*   17:     */ import com.asinfo.as2.dao.SubempresaDao;
/*   18:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   19:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   20:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   21:     */ import com.asinfo.as2.entities.ActivoFijo;
/*   22:     */ import com.asinfo.as2.entities.Asiento;
/*   23:     */ import com.asinfo.as2.entities.Bodega;
/*   24:     */ import com.asinfo.as2.entities.Canal;
/*   25:     */ import com.asinfo.as2.entities.CategoriaActivo;
/*   26:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   27:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   28:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   29:     */ import com.asinfo.as2.entities.CategoriaRetencion;
/*   30:     */ import com.asinfo.as2.entities.ConceptoContable;
/*   31:     */ import com.asinfo.as2.entities.CriterioContabilizacion;
/*   32:     */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   33:     */ import com.asinfo.as2.entities.CuentaContable;
/*   34:     */ import com.asinfo.as2.entities.Departamento;
/*   35:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   36:     */ import com.asinfo.as2.entities.DestinoCosto;
/*   37:     */ import com.asinfo.as2.entities.DetalleAsiento;
/*   38:     */ import com.asinfo.as2.entities.DetalleCategoriaRetencion;
/*   39:     */ import com.asinfo.as2.entities.DetalleCierreCaja;
/*   40:     */ import com.asinfo.as2.entities.DimensionContable;
/*   41:     */ import com.asinfo.as2.entities.Documento;
/*   42:     */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*   43:     */ import com.asinfo.as2.entities.Empleado;
/*   44:     */ import com.asinfo.as2.entities.Empresa;
/*   45:     */ import com.asinfo.as2.entities.ExtractoBancario;
/*   46:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   47:     */ import com.asinfo.as2.entities.GuiaAerea;
/*   48:     */ import com.asinfo.as2.entities.Impuesto;
/*   49:     */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   50:     */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*   51:     */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*   52:     */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*   53:     */ import com.asinfo.as2.entities.MotivoNotaCreditoProveedor;
/*   54:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   55:     */ import com.asinfo.as2.entities.Organizacion;
/*   56:     */ import com.asinfo.as2.entities.Producto;
/*   57:     */ import com.asinfo.as2.entities.Proveedor;
/*   58:     */ import com.asinfo.as2.entities.Rubro;
/*   59:     */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*   60:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   61:     */ import com.asinfo.as2.entities.Subempresa;
/*   62:     */ import com.asinfo.as2.entities.Sucursal;
/*   63:     */ import com.asinfo.as2.entities.TipoAsiento;
/*   64:     */ import com.asinfo.as2.entities.Zona;
/*   65:     */ import com.asinfo.as2.entities.aerolineas.Cass;
/*   66:     */ import com.asinfo.as2.entities.aerolineas.ConfiguracionCass;
/*   67:     */ import com.asinfo.as2.entities.aerolineas.Ticket;
/*   68:     */ import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
/*   69:     */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   70:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   71:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   72:     */ import com.asinfo.as2.enumeraciones.Parametro;
/*   73:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   74:     */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*   75:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   76:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   77:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCategoriaRetencion;
/*   78:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*   79:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   80:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*   81:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*   82:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*   83:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*   84:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*   85:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   86:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   87:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*   88:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   89:     */ import com.asinfo.as2.util.AppUtil;
/*   90:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   91:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   92:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   93:     */ import java.io.BufferedInputStream;
/*   94:     */ import java.io.BufferedReader;
/*   95:     */ import java.io.File;
/*   96:     */ import java.io.FileOutputStream;
/*   97:     */ import java.io.FileReader;
/*   98:     */ import java.io.IOException;
/*   99:     */ import java.io.InputStream;
/*  100:     */ import java.io.PrintStream;
/*  101:     */ import java.math.BigDecimal;
/*  102:     */ import java.math.RoundingMode;
/*  103:     */ import java.util.ArrayList;
/*  104:     */ import java.util.Calendar;
/*  105:     */ import java.util.Date;
/*  106:     */ import java.util.HashMap;
/*  107:     */ import java.util.Iterator;
/*  108:     */ import java.util.LinkedHashMap;
/*  109:     */ import java.util.List;
/*  110:     */ import java.util.Map;
/*  111:     */ import java.util.TreeMap;
/*  112:     */ import javax.ejb.EJB;
/*  113:     */ import javax.ejb.SessionContext;
/*  114:     */ import javax.ejb.Stateless;
/*  115:     */ import javax.ejb.TransactionAttribute;
/*  116:     */ import javax.ejb.TransactionAttributeType;
/*  117:     */ import org.primefaces.event.FileUploadEvent;
/*  118:     */ import org.primefaces.model.UploadedFile;
/*  119:     */ 
/*  120:     */ @Stateless
/*  121:     */ public class ServicioInterfazContableProcesoImpl
/*  122:     */   extends AbstractServicioAS2Financiero
/*  123:     */   implements ServicioInterfazContableProceso
/*  124:     */ {
/*  125:     */   private static final long serialVersionUID = 921448177836123443L;
/*  126:     */   @EJB
/*  127:     */   private transient ServicioPeriodo servicioPeriodo;
/*  128:     */   @EJB
/*  129:     */   private transient ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  130:     */   @EJB
/*  131:     */   private transient ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  132:     */   @EJB
/*  133:     */   private transient DetalleCierreCajaDao detalleCierreCajaDao;
/*  134:     */   @EJB
/*  135:     */   private transient InterfazContableProcesoDao interfazContableProcesoDao;
/*  136:     */   @EJB
/*  137:     */   private transient MovimientoInventarioDao movimientoInventarioDao;
/*  138:     */   @EJB
/*  139:     */   private transient FacturaClienteDao facturaClienteDao;
/*  140:     */   @EJB
/*  141:     */   private transient DespachoClienteDao despachoClienteDao;
/*  142:     */   @EJB
/*  143:     */   private transient ServicioTipoAsiento servicioTipoAsiento;
/*  144:     */   @EJB
/*  145:     */   private transient CajaDao cajaDao;
/*  146:     */   @EJB
/*  147:     */   private transient ServicioSecuencia servicioSecuencia;
/*  148:     */   @EJB
/*  149:     */   private transient DetalleValoresContratoVentaDao detalleValoresContratoVentaDao;
/*  150:     */   @EJB
/*  151:     */   private transient SubempresaDao subempresaDao;
/*  152:     */   @EJB
/*  153:     */   private ServicioGenerico<ConfiguracionCass> servicioConfiguracionCass;
/*  154:     */   @EJB
/*  155:     */   private ServicioDocumento servicioDocumento;
/*  156:     */   @EJB
/*  157:     */   private ServicioEmpresa servicioEmpresa;
/*  158:     */   @EJB
/*  159:     */   private ServicioCategoriaRetencion servicioCategoriaRetencion;
/*  160:     */   @EJB
/*  161:     */   private CassDao cassDao;
/*  162:     */   @EJB
/*  163:     */   private CargaBSPDao cargaBSPDao;
/*  164:     */   @EJB
/*  165:     */   private GenericoDao<Ticket> ticketDao;
/*  166:     */   @EJB
/*  167:     */   private ExtractoBancarioDao extractoBancarioDao;
/*  168:     */   @EJB
/*  169:     */   private ServicioAmortizacion servicioAmortizacion;
/*  170:     */   @EJB
/*  171:     */   private ServicioImpuesto servicioImpuesto;
/*  172: 160 */   private HashMap<String, GuiaAerea> hmAgentCode = new LinkedHashMap();
/*  173:     */   
/*  174:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  175:     */   public void guardar(InterfazContableProceso interfazContableProceso)
/*  176:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  177:     */   {
/*  178:     */     try
/*  179:     */     {
/*  180: 174 */       if ((interfazContableProceso.getDocumentoBase() == DocumentoBase.CONTRATO_VENTA) || 
/*  181: 175 */         (interfazContableProceso.getDocumentoBase().equals(DocumentoBase.CARGA_ARCHIVO)))
/*  182:     */       {
/*  183: 176 */         interfazContableProceso.setAsiento(null);
/*  184:     */         
/*  185: 178 */         this.interfazContableProcesoDao.guardar(interfazContableProceso);
/*  186:     */       }
/*  187:     */       else
/*  188:     */       {
/*  189: 181 */         if (interfazContableProceso.getDocumentoBase() == DocumentoBase.DEPOSITO_CAJA) {
/*  190: 182 */           cargarSecuencia(interfazContableProceso);
/*  191:     */         }
/*  192: 184 */         Asiento asiento = interfazContableProceso.getAsiento();
/*  193: 185 */         interfazContableProceso.setAsiento(null);
/*  194: 186 */         Date fechaContabilizacion = interfazContableProceso.getFechaHasta();
/*  195: 187 */         if ((interfazContableProceso.getId() > 0) && (interfazContableProceso.getDocumentoBase() == DocumentoBase.DEPOSITO_CAJA)) {
/*  196: 188 */           fechaContabilizacion = interfazContableProceso.getFechaContabilizacion();
/*  197:     */         }
/*  198: 191 */         interfazContableProceso.setFechaContabilizacion(fechaContabilizacion);
/*  199: 192 */         this.interfazContableProcesoDao.guardar(interfazContableProceso);
/*  200:     */         
/*  201: 194 */         List<DetalleCierreCaja> listaDetalleCierreCaja = interfazContableProceso.getListaDetalleCierreCaja();
/*  202: 195 */         List<MovimientoInventario> listaMovimientoInventario = interfazContableProceso.getListaMovimientoInventario();
/*  203: 197 */         for (DetalleCierreCaja d : listaDetalleCierreCaja)
/*  204:     */         {
/*  205: 198 */           if (d.isSeleccionado()) {
/*  206: 199 */             d.setInterfazContableProceso(interfazContableProceso);
/*  207:     */           } else {
/*  208: 201 */             d.setInterfazContableProceso(null);
/*  209:     */           }
/*  210: 204 */           this.detalleCierreCajaDao.guardar(d);
/*  211:     */         }
/*  212: 211 */         if ((interfazContableProceso.getDocumentoBase().equals(DocumentoBase.INTERFAZ_VENTAS)) || 
/*  213: 212 */           (interfazContableProceso.getDocumentoBase().equals(DocumentoBase.INTERFAZ_DEVOLUCION_COSTO_VENTAS))) {
/*  214: 213 */           for (List<Integer> list : interfazContableProceso.getListaProcesosContabilizados()) {
/*  215: 214 */             if (list.size() > 0)
/*  216:     */             {
/*  217: 215 */               if (this.facturaClienteDao.validarContabilizacionVentas(list) > 0L) {
/*  218: 216 */                 throw new ExcepcionAS2Financiero("msg_error_asiento_generado_proceso");
/*  219:     */               }
/*  220: 218 */               this.facturaClienteDao.actualizarEstadoContabilizadoFacturaClienteInterfazContable(interfazContableProceso, list);
/*  221:     */             }
/*  222:     */           }
/*  223:     */         }
/*  224: 225 */         if (interfazContableProceso.getDocumentoBase().equals(DocumentoBase.INTERFAZ_DESPACHOS)) {
/*  225: 226 */           for (List<Integer> list : interfazContableProceso.getListaProcesosContabilizados()) {
/*  226: 227 */             if (list.size() > 0) {
/*  227: 228 */               this.despachoClienteDao.actualizarEstadoContabilizadoDespachosInterfazContable(interfazContableProceso, list);
/*  228:     */             }
/*  229:     */           }
/*  230:     */         }
/*  231: 235 */         Object listaTotal = new ArrayList();
/*  232: 236 */         ((List)listaTotal).add(new ArrayList());
/*  233: 237 */         int contador = 0;
/*  234: 238 */         int numeroListas = 0;
/*  235: 239 */         for (MovimientoInventario m : listaMovimientoInventario)
/*  236:     */         {
/*  237: 240 */           if (contador == 2000)
/*  238:     */           {
/*  239: 241 */             ((List)listaTotal).add(new ArrayList());
/*  240: 242 */             numeroListas++;
/*  241: 243 */             contador = 0;
/*  242:     */           }
/*  243: 245 */           ((List)((List)listaTotal).get(numeroListas)).add(Integer.valueOf(m.getIdMovimientoInventario()));
/*  244: 246 */           contador++;
/*  245:     */         }
/*  246: 248 */         for (List<Integer> list : (List)listaTotal) {
/*  247: 249 */           if (list.size() > 0) {
/*  248: 250 */             this.movimientoInventarioDao.actualizarEstadoContabilizadoMovimientoInventarioInterfazContable(interfazContableProceso, list);
/*  249:     */           }
/*  250:     */         }
/*  251: 254 */         if (interfazContableProceso.getDocumentoBase() != DocumentoBase.DEPOSITO_CAJA) {
/*  252: 255 */           contabilizar(interfazContableProceso, asiento);
/*  253:     */         }
/*  254:     */       }
/*  255:     */     }
/*  256:     */     catch (ExcepcionAS2Financiero e)
/*  257:     */     {
/*  258: 260 */       this.context.setRollbackOnly();
/*  259: 261 */       e.printStackTrace();
/*  260: 262 */       throw e;
/*  261:     */     }
/*  262:     */     catch (ExcepcionAS2 e)
/*  263:     */     {
/*  264: 264 */       this.context.setRollbackOnly();
/*  265: 265 */       e.printStackTrace();
/*  266: 266 */       throw e;
/*  267:     */     }
/*  268:     */   }
/*  269:     */   
/*  270:     */   public void contabilizar(InterfazContableProceso interfazContableProceso, Asiento asiento)
/*  271:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  272:     */   {
/*  273:     */     try
/*  274:     */     {
/*  275: 276 */       this.servicioAsiento.guardar(asiento);
/*  276: 277 */       interfazContableProceso.setFechaContabilizacion(asiento.getFecha());
/*  277: 278 */       interfazContableProceso.setObservacion(asiento.getConcepto());
/*  278: 279 */       interfazContableProceso.setEstado(Estado.CONTABILIZADO);
/*  279:     */       
/*  280: 281 */       interfazContableProceso.setAsiento(asiento);
/*  281:     */       
/*  282: 283 */       this.interfazContableProcesoDao.guardar(interfazContableProceso);
/*  283:     */     }
/*  284:     */     catch (ExcepcionAS2Financiero e)
/*  285:     */     {
/*  286: 286 */       this.context.setRollbackOnly();
/*  287: 287 */       throw e;
/*  288:     */     }
/*  289:     */     catch (ExcepcionAS2 e)
/*  290:     */     {
/*  291: 289 */       this.context.setRollbackOnly();
/*  292: 290 */       throw e;
/*  293:     */     }
/*  294:     */     catch (Exception e)
/*  295:     */     {
/*  296: 292 */       this.context.setRollbackOnly();
/*  297: 293 */       e.printStackTrace();
/*  298: 294 */       throw new ExcepcionAS2(e);
/*  299:     */     }
/*  300:     */   }
/*  301:     */   
/*  302:     */   public void eliminar(InterfazContableProceso InterfazContableProceso) {}
/*  303:     */   
/*  304:     */   public InterfazContableProceso buscarPorId(int idInterfazContableProceso)
/*  305:     */   {
/*  306: 318 */     return (InterfazContableProceso)this.interfazContableProcesoDao.buscarPorId(Integer.valueOf(idInterfazContableProceso));
/*  307:     */   }
/*  308:     */   
/*  309:     */   public List<InterfazContableProceso> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  310:     */   {
/*  311: 330 */     return this.interfazContableProcesoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  312:     */   }
/*  313:     */   
/*  314:     */   public List<InterfazContableProceso> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  315:     */   {
/*  316: 342 */     return null;
/*  317:     */   }
/*  318:     */   
/*  319:     */   public int contarPorCriterio(Map<String, String> filters)
/*  320:     */   {
/*  321: 352 */     return this.interfazContableProcesoDao.contarPorCriterio(filters);
/*  322:     */   }
/*  323:     */   
/*  324:     */   public InterfazContableProceso cargarDetalle(int idInterfazContableProceso)
/*  325:     */   {
/*  326: 362 */     return this.interfazContableProcesoDao.cargarDetalle(idInterfazContableProceso);
/*  327:     */   }
/*  328:     */   
/*  329:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  330:     */   public Asiento contabilizarDepositoCaja(InterfazContableProceso interfazContableProceso)
/*  331:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  332:     */   {
/*  333:     */     try
/*  334:     */     {
/*  335: 377 */       if (buscarPorId(interfazContableProceso.getId()).getEstado().equals(Estado.CONTABILIZADO)) {
/*  336: 378 */         throw new ExcepcionAS2("msg_error_asiento_generado_proceso", " " + interfazContableProceso.getNumero());
/*  337:     */       }
/*  338: 380 */       generarAsiento(interfazContableProceso);
/*  339: 381 */       contabilizar(interfazContableProceso, interfazContableProceso.getAsiento());
/*  340: 382 */       return interfazContableProceso.getAsiento();
/*  341:     */     }
/*  342:     */     catch (ExcepcionAS2Financiero e)
/*  343:     */     {
/*  344: 384 */       this.context.setRollbackOnly();
/*  345: 385 */       throw e;
/*  346:     */     }
/*  347:     */     catch (ExcepcionAS2 e)
/*  348:     */     {
/*  349: 387 */       this.context.setRollbackOnly();
/*  350: 388 */       throw e;
/*  351:     */     }
/*  352:     */   }
/*  353:     */   
/*  354:     */   public Asiento generarAsiento(InterfazContableProceso interfazContableProceso)
/*  355:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  356:     */   {
/*  357:     */     try
/*  358:     */     {
/*  359: 403 */       Date fechaDesde = interfazContableProceso.getFechaDesde();
/*  360: 404 */       Date fechaHasta = interfazContableProceso.getFechaHasta();
/*  361: 405 */       Date fechaContabilizacion = interfazContableProceso.getFechaHasta();
/*  362:     */       
/*  363:     */ 
/*  364:     */ 
/*  365: 409 */       DocumentoBase documentoBaseFiltro = null;
/*  366: 410 */       int idDocumento = 0;
/*  367:     */       
/*  368:     */ 
/*  369: 413 */       DocumentoBase documentoBase = interfazContableProceso.getDocumentoBase();
/*  370: 414 */       if (documentoBase == DocumentoBase.INTERFAZ_VENTAS)
/*  371:     */       {
/*  372: 415 */         documentoBaseFiltro = interfazContableProceso.getFiltroDocumentoBase();
/*  373: 416 */         idDocumento = interfazContableProceso.getFiltroDocumento().getId();
/*  374:     */       }
/*  375: 417 */       else if (documentoBase == DocumentoBase.INTERFAZ_DEVOLUCION_COSTO_VENTAS)
/*  376:     */       {
/*  377: 418 */         documentoBaseFiltro = interfazContableProceso.getFiltroDocumentoBase();
/*  378:     */       }
/*  379: 419 */       else if (documentoBase == DocumentoBase.DEPOSITO_CAJA)
/*  380:     */       {
/*  381: 421 */         fechaContabilizacion = interfazContableProceso.getFechaContabilizacion();
/*  382:     */       }
/*  383:     */       Asiento asiento;
/*  384: 425 */       if (interfazContableProceso.getAsiento() == null)
/*  385:     */       {
/*  386: 426 */         Asiento asiento = new Asiento();
/*  387: 427 */         interfazContableProceso.setAsiento(asiento);
/*  388:     */       }
/*  389:     */       else
/*  390:     */       {
/*  391: 430 */         asiento = interfazContableProceso.getAsiento();
/*  392: 431 */         for (DetalleAsiento detalle : asiento.getListaDetalleAsiento()) {
/*  393: 432 */           detalle.setEliminado(true);
/*  394:     */         }
/*  395:     */       }
/*  396: 435 */       asiento.setIdOrganizacion(interfazContableProceso.getIdOrganizacion());
/*  397: 436 */       asiento.setSucursal(interfazContableProceso.getSucursal());
/*  398:     */       
/*  399: 438 */       TipoAsiento tipoAsiento = null;
/*  400: 439 */       String concepto = "";
/*  401:     */       List<CriterioDistribucion> listaCriterioDistribucion;
/*  402:     */       DocumentoContabilizacion documentoContabilizacion;
/*  403:     */       List<DetalleInterfazContableProceso> lista;
/*  404:     */       Iterator localIterator2;
/*  405:     */       List<Integer> list;
/*  406:     */       List<CriterioDistribucion> listaCriterioDistribucion;
/*  407:     */       FacturaCliente f;
/*  408:     */       List<CriterioDistribucion> listaCriterioDistribucion;
/*  409:     */       List<DetalleInterfazContable> listaDAIC;
/*  410:     */       List<CriterioDistribucion> listaCriterioDistribucion;
/*  411: 446 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$DocumentoBase[documentoBase.ordinal()])
/*  412:     */       {
/*  413:     */       case 1: 
/*  414:     */         try
/*  415:     */         {
/*  416: 452 */           tipoAsiento = this.servicioTipoAsiento.buscarPorId(ParametrosSistema.getTipoAsientoInterfazConsumoBodega(interfazContableProceso.getIdOrganizacion()));
/*  417:     */         }
/*  418:     */         catch (Exception e)
/*  419:     */         {
/*  420: 454 */           throw new ExcepcionAS2("msg_info_configuracion", Parametro.TIPO_ASIENTO_CONSUMO_BODEGA.getNombre());
/*  421:     */         }
/*  422: 457 */         if ((fechaDesde != null) && (fechaHasta != null)) {
/*  423: 460 */           concepto = DocumentoBase.INTERFAZ_CONSUMOS_BODEGA.getNombre() + FuncionesUtiles.dateToString(fechaDesde) + " hasta " + FuncionesUtiles.dateToString(fechaHasta);
/*  424:     */         } else {
/*  425: 465 */           concepto = ((MovimientoInventario)interfazContableProceso.getListaMovimientoInventario().get(0)).getDocumento().getNombre().trim() + " #" + ((MovimientoInventario)interfazContableProceso.getListaMovimientoInventario().get(0)).getNumero() + " " + ((MovimientoInventario)interfazContableProceso.getListaMovimientoInventario().get(0)).getDescripcion();
/*  426:     */         }
/*  427: 469 */         asiento.setDocumentoOrigen(interfazContableProceso.getDocumento());
/*  428:     */         
/*  429:     */ 
/*  430: 472 */         List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(interfazContableProceso.getIdOrganizacion(), DocumentoBase.CONSUMO_BODEGA);
/*  431:     */         
/*  432: 474 */         listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(interfazContableProceso.getIdOrganizacion(), DocumentoBase.CONSUMO_BODEGA);
/*  433: 477 */         if (!interfazContableProceso.isContabilizacionAutomatica())
/*  434:     */         {
/*  435: 478 */           interfazContableProceso.setListaMovimientoInventario(this.movimientoInventarioDao.getListaConsumoBodega(fechaDesde, fechaHasta, DocumentoBase.CONSUMO_BODEGA, interfazContableProceso
/*  436: 479 */             .getIdOrganizacion()));
/*  437:     */           
/*  438: 481 */           interfazContableProceso.getListaMovimientoInventario().addAll(this.movimientoInventarioDao.getListaConsumoBodega(fechaDesde, fechaHasta, DocumentoBase.DEVOLUCION_CONSUMO_BODEGA, interfazContableProceso
/*  439: 482 */             .getIdOrganizacion()));
/*  440:     */           
/*  441: 484 */           asiento.setListaDetalleAsiento(new ArrayList());
/*  442:     */         }
/*  443: 487 */         interfazContableProceso.setListaProcesosContabilizados(new ArrayList());
/*  444: 488 */         List<List<Integer>> listaTotal = interfazContableProceso.getListaProcesosContabilizados();
/*  445: 489 */         listaTotal.add(new ArrayList());
/*  446: 490 */         int contador = 0;
/*  447: 491 */         int numeroListas = 0;
/*  448: 492 */         for (MovimientoInventario m : interfazContableProceso.getListaMovimientoInventario())
/*  449:     */         {
/*  450: 493 */           if (contador == 2000)
/*  451:     */           {
/*  452: 494 */             listaTotal.add(new ArrayList());
/*  453: 495 */             numeroListas++;
/*  454: 496 */             contador = 0;
/*  455:     */           }
/*  456: 498 */           ((List)listaTotal.get(numeroListas)).add(Integer.valueOf(m.getIdMovimientoInventario()));
/*  457: 499 */           contador++;
/*  458:     */         }
/*  459: 503 */         for (e = listaDocumentoContabilizacion.iterator(); e.hasNext();)
/*  460:     */         {
/*  461: 503 */           documentoContabilizacion = (DocumentoContabilizacion)e.next();
/*  462: 504 */           lista = new ArrayList();
/*  463: 506 */           for (localIterator2 = interfazContableProceso.getListaProcesosContabilizados().iterator(); localIterator2.hasNext();)
/*  464:     */           {
/*  465: 506 */             list = (List)localIterator2.next();
/*  466: 507 */             if (list.size() > 0) {
/*  467: 508 */               lista.addAll(this.movimientoInventarioDao
/*  468: 509 */                 .getInterfazMovimientoInventarioDimensiones(list, DocumentoBase.CONSUMO_BODEGA, false));
/*  469:     */             }
/*  470: 511 */             if (lista.size() > 0) {
/*  471: 513 */               asiento.getListaDetalleAsiento().addAll(generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false, true));
/*  472:     */             }
/*  473:     */           }
/*  474:     */         }
/*  475: 518 */         break;
/*  476:     */       case 2: 
/*  477:     */         try
/*  478:     */         {
/*  479: 525 */           tipoAsiento = this.servicioTipoAsiento.buscarPorId(ParametrosSistema.getTipoAsientoInterfazVentas(interfazContableProceso.getIdOrganizacion()));
/*  480:     */         }
/*  481:     */         catch (Exception e)
/*  482:     */         {
/*  483: 527 */           throw new ExcepcionAS2("msg_info_configuracion", Parametro.TIPO_ASIENTO_INTERFAZ_VENTAS.getNombre());
/*  484:     */         }
/*  485: 530 */         if ((fechaHasta != null) && (fechaDesde != null)) {
/*  486: 533 */           concepto = interfazContableProceso.getFiltroDocumentoBase().getNombre() + " " + FuncionesUtiles.dateToString(fechaDesde) + " hasta " + FuncionesUtiles.dateToString(fechaHasta);
/*  487:     */         } else {
/*  488: 539 */           concepto = ((FacturaCliente)interfazContableProceso.getListaFacturaCliente().get(0)).getDocumento().getNombre().trim() + " #" + ((FacturaCliente)interfazContableProceso.getListaFacturaCliente().get(0)).getNumero() + " " + (((FacturaCliente)interfazContableProceso.getListaFacturaCliente().get(0)).getDescripcion() != null ? ((FacturaCliente)interfazContableProceso.getListaFacturaCliente().get(0)).getDescripcion() : "");
/*  489:     */         }
/*  490: 542 */         asiento.setDocumentoOrigen(interfazContableProceso.getDocumento());
/*  491:     */         
/*  492:     */ 
/*  493: 545 */         List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(interfazContableProceso.getIdOrganizacion(), interfazContableProceso.getFiltroDocumentoBase());
/*  494:     */         
/*  495: 547 */         listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(interfazContableProceso.getIdOrganizacion(), interfazContableProceso
/*  496: 548 */           .getFiltroDocumentoBase());
/*  497: 550 */         for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/*  498: 551 */           detalleAsiento.setEliminado(true);
/*  499:     */         }
/*  500: 556 */         if (!interfazContableProceso.isContabilizacionAutomatica()) {
/*  501: 557 */           interfazContableProceso.setListaFacturaCliente(this.facturaClienteDao.getListaFacturaCliente(fechaDesde, fechaHasta, documentoBaseFiltro, idDocumento, interfazContableProceso
/*  502: 558 */             .getIdOrganizacion()));
/*  503:     */         } else {
/*  504: 560 */           interfazContableProceso.setListaFacturaCliente(interfazContableProceso.getListaFacturaCliente());
/*  505:     */         }
/*  506: 564 */         if ((interfazContableProceso.isContabilizacionAutomatica()) && 
/*  507: 565 */           (interfazContableProceso.getDescuentoImpuesto().compareTo(BigDecimal.ZERO) > 0))
/*  508:     */         {
/*  509: 566 */           listaDocConDIV = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(interfazContableProceso
/*  510: 567 */             .getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE, ProcesoContabilizacionEnum.DESCUENTO_IMPUESTO_VENTAS);
/*  511: 569 */           if (listaDocConDIV.size() == 0) {
/*  512: 570 */             throw new ExcepcionAS2("no_existe_documento_contabilizacion_descuento_ventas");
/*  513:     */           }
/*  514:     */         }
/*  515: 575 */         interfazContableProceso.setListaProcesosContabilizados(new ArrayList());
/*  516: 576 */         List<List<Integer>> listaTotal = interfazContableProceso.getListaProcesosContabilizados();
/*  517: 577 */         listaTotal.add(new ArrayList());
/*  518: 578 */         int contador = 0;
/*  519: 579 */         int numeroListas = 0;
/*  520: 580 */         for (List<DocumentoContabilizacion> listaDocConDIV = interfazContableProceso.getListaFacturaCliente().iterator(); listaDocConDIV.hasNext();)
/*  521:     */         {
/*  522: 580 */           f = (FacturaCliente)listaDocConDIV.next();
/*  523: 581 */           if (contador == 2000)
/*  524:     */           {
/*  525: 582 */             listaTotal.add(new ArrayList());
/*  526: 583 */             numeroListas++;
/*  527: 584 */             contador = 0;
/*  528:     */           }
/*  529: 586 */           ((List)listaTotal.get(numeroListas)).add(Integer.valueOf(f.getIdFacturaCliente()));
/*  530: 587 */           contador++;
/*  531:     */         }
/*  532: 591 */         List<DetalleInterfazContable> listaDA = new ArrayList();
/*  533: 592 */         for (List<Integer> listadoFacturas : interfazContableProceso.getListaProcesosContabilizados()) {
/*  534: 593 */           if (listadoFacturas.size() > 0) {
/*  535: 594 */             listaDA.addAll(this.facturaClienteDao.getInterfazVentasEstructuraPrecioCombustible(listadoFacturas));
/*  536:     */           }
/*  537:     */         }
/*  538: 597 */         super.generarAsiento(asiento, listaDA, interfazContableProceso.getDocumento());
/*  539: 602 */         for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion) {
/*  540: 604 */           if (documentoContabilizacion.getProcesoContabilizacion() != ProcesoContabilizacionEnum.INGRESOS_CONTRATO_VENTA)
/*  541:     */           {
/*  542: 605 */             Object lista = new ArrayList();
/*  543: 607 */             for (List<Integer> list : interfazContableProceso.getListaProcesosContabilizados()) {
/*  544: 608 */               if (list.size() > 0) {
/*  545: 609 */                 ((List)lista).addAll(this.facturaClienteDao
/*  546: 610 */                   .getInterfazVentasDimensiones(list, documentoContabilizacion.getProcesoContabilizacion()));
/*  547:     */               }
/*  548:     */             }
/*  549: 614 */             if (((List)lista).size() > 0) {
/*  550: 616 */               asiento.getListaDetalleAsiento().addAll(generarAsiento(asiento, (List)lista, documentoContabilizacion, listaCriterioDistribucion, false, false));
/*  551:     */             }
/*  552:     */           }
/*  553:     */         }
/*  554: 621 */         break;
/*  555:     */       case 3: 
/*  556:     */         try
/*  557:     */         {
/*  558: 627 */           tipoAsiento = this.servicioTipoAsiento.buscarPorId(ParametrosSistema.getTipoAsientoInterfazDespacho(interfazContableProceso.getIdOrganizacion()));
/*  559:     */         }
/*  560:     */         catch (Exception e)
/*  561:     */         {
/*  562: 629 */           throw new ExcepcionAS2("msg_info_configuracion", Parametro.TIPO_ASIENTO_DESPACHO.getNombre());
/*  563:     */         }
/*  564: 632 */         if ((fechaDesde != null) && (fechaHasta != null)) {
/*  565: 635 */           concepto = DocumentoBase.INTERFAZ_DESPACHOS.getNombre() + FuncionesUtiles.dateToString(fechaDesde) + " hasta " + FuncionesUtiles.dateToString(fechaHasta);
/*  566:     */         } else {
/*  567: 640 */           concepto = ((DespachoCliente)interfazContableProceso.getListaDespachoCliente().get(0)).getDocumento().getNombre().trim() + " #" + ((DespachoCliente)interfazContableProceso.getListaDespachoCliente().get(0)).getNumero() + " " + ((DespachoCliente)interfazContableProceso.getListaDespachoCliente().get(0)).getDescripcion();
/*  568:     */         }
/*  569: 643 */         asiento.setDocumentoOrigen(interfazContableProceso.getDocumento());
/*  570:     */         
/*  571:     */ 
/*  572: 646 */         List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(interfazContableProceso.getIdOrganizacion(), DocumentoBase.DESPACHO_BODEGA);
/*  573:     */         
/*  574: 648 */         listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(interfazContableProceso.getIdOrganizacion(), DocumentoBase.DESPACHO_BODEGA);
/*  575: 652 */         if (!interfazContableProceso.isContabilizacionAutomatica())
/*  576:     */         {
/*  577: 653 */           interfazContableProceso.setListaDespachoCliente(this.despachoClienteDao
/*  578: 654 */             .getListaDespachoCliente(fechaDesde, fechaHasta, interfazContableProceso.getIdOrganizacion()));
/*  579: 655 */           asiento.setListaDetalleAsiento(new ArrayList());
/*  580:     */         }
/*  581: 658 */         interfazContableProceso.setListaProcesosContabilizados(new ArrayList());
/*  582:     */         
/*  583: 660 */         List<List<Integer>> listaTotal = interfazContableProceso.getListaProcesosContabilizados();
/*  584: 661 */         listaTotal.add(new ArrayList());
/*  585: 662 */         int contador = 0;
/*  586: 663 */         int numeroListas = 0;
/*  587: 664 */         for (DespachoCliente d : interfazContableProceso.getListaDespachoCliente())
/*  588:     */         {
/*  589: 665 */           if (contador == 2000)
/*  590:     */           {
/*  591: 666 */             listaTotal.add(new ArrayList());
/*  592: 667 */             numeroListas++;
/*  593: 668 */             contador = 0;
/*  594:     */           }
/*  595: 670 */           ((List)listaTotal.get(numeroListas)).add(Integer.valueOf(d.getIdDespachoCliente()));
/*  596: 671 */           contador++;
/*  597:     */         }
/*  598: 676 */         for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/*  599:     */         {
/*  600: 677 */           Object lista = new ArrayList();
/*  601: 679 */           for (List<Integer> list : interfazContableProceso.getListaProcesosContabilizados()) {
/*  602: 680 */             if (list.size() > 0) {
/*  603: 681 */               ((List)lista).addAll(this.despachoClienteDao
/*  604: 682 */                 .getInterfazDespachosDimensiones(list, documentoContabilizacion.getProcesoContabilizacion()));
/*  605:     */             }
/*  606:     */           }
/*  607: 685 */           if (((List)lista).size() > 0) {
/*  608: 687 */             asiento.getListaDetalleAsiento().addAll(generarAsiento(asiento, (List)lista, documentoContabilizacion, listaCriterioDistribucion, false, false));
/*  609:     */           }
/*  610:     */         }
/*  611: 691 */         break;
/*  612:     */       case 4: 
/*  613: 695 */         guardar(interfazContableProceso);
/*  614:     */         
/*  615: 697 */         tipoAsiento = interfazContableProceso.getDocumento().getTipoAsiento();
/*  616: 698 */         asiento = new Asiento();
/*  617: 699 */         asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  618: 700 */         asiento.setSucursal(AppUtil.getSucursal());
/*  619: 701 */         asiento.setFecha(interfazContableProceso.getFechaContabilizacion());
/*  620: 702 */         asiento.setTipoAsiento(tipoAsiento);
/*  621:     */         
/*  622:     */ 
/*  623: 705 */         concepto = interfazContableProceso.getDocumento().getNombre() + " " + FuncionesUtiles.dateToString(interfazContableProceso.getFechaHasta()) + " " + interfazContableProceso.getObservacion();
/*  624:     */         
/*  625: 707 */         asiento.setConcepto(concepto);
/*  626: 708 */         listaDAIC = this.cajaDao.interfazContableDeposito(interfazContableProceso);
/*  627: 709 */         super.generarAsiento(asiento, listaDAIC, interfazContableProceso.getDocumento());
/*  628:     */         
/*  629: 711 */         interfazContableProceso.setAsiento(asiento);
/*  630:     */         
/*  631:     */ 
/*  632:     */ 
/*  633: 715 */         interfazContableProceso.setIndicadorAgrupadoPorCuenta(false);
/*  634:     */         
/*  635: 717 */         break;
/*  636:     */       case 5: 
/*  637: 721 */         guardar(interfazContableProceso);
/*  638:     */         
/*  639: 723 */         tipoAsiento = interfazContableProceso.getDocumento().getTipoAsiento();
/*  640: 724 */         asiento = new Asiento();
/*  641: 725 */         asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  642: 726 */         asiento.setSucursal(AppUtil.getSucursal());
/*  643: 727 */         asiento.setFecha(interfazContableProceso.getFechaContabilizacion());
/*  644: 728 */         asiento.setTipoAsiento(tipoAsiento);
/*  645:     */         
/*  646:     */ 
/*  647: 731 */         concepto = interfazContableProceso.getDocumento().getNombre() + " " + FuncionesUtiles.dateToString(interfazContableProceso.getFechaHasta());
/*  648: 732 */         asiento.setConcepto(concepto);
/*  649: 733 */         List<DetalleInterfazContable> listaDAICD = this.cajaDao.interfazContableDeposito(interfazContableProceso);
/*  650: 734 */         super.generarAsiento(asiento, listaDAICD, interfazContableProceso.getDocumento());
/*  651:     */         
/*  652: 736 */         interfazContableProceso.setAsiento(asiento);
/*  653:     */         
/*  654: 738 */         break;
/*  655:     */       case 6: 
/*  656:     */         try
/*  657:     */         {
/*  658: 744 */           tipoAsiento = this.servicioTipoAsiento.buscarPorId(ParametrosSistema.getTipoAsientoCierreContableCuenta(interfazContableProceso.getIdOrganizacion()));
/*  659:     */         }
/*  660:     */         catch (Exception e)
/*  661:     */         {
/*  662: 746 */           throw new ExcepcionAS2("msg_info_configuracion", Parametro.TIPO_ASIENTO_CIERRE_CONTABLE_CUENTA.getNombre());
/*  663:     */         }
/*  664: 750 */         concepto = "Cierre de Periodo desde: " + FuncionesUtiles.dateToString(fechaDesde) + " hasta: " + FuncionesUtiles.dateToString(fechaHasta);
/*  665: 751 */         interfazContableProceso.setFechaContabilizacion(fechaContabilizacion);
/*  666:     */         
/*  667: 753 */         asiento.setListaDetalleAsiento(this.servicioCuentaContable.obtenerCierreCuentas(interfazContableProceso.getListaCuentaContable(), fechaDesde, fechaHasta, 
/*  668: 754 */           AppUtil.getOrganizacion().getId()));
/*  669:     */         
/*  670: 756 */         interfazContableProceso.setAsiento(asiento);
/*  671:     */         
/*  672: 758 */         break;
/*  673:     */       case 7: 
/*  674: 762 */         tipoAsiento = null;
/*  675:     */         try
/*  676:     */         {
/*  677: 765 */           tipoAsiento = this.servicioTipoAsiento.buscarPorId(ParametrosSistema.getTipoAsientoInterfazDevolucionCostoVentas(interfazContableProceso.getIdOrganizacion()));
/*  678:     */         }
/*  679:     */         catch (Exception e)
/*  680:     */         {
/*  681: 767 */           throw new ExcepcionAS2("msg_info_configuracion", Parametro.TIPO_ASIENTO_INTERFAZ_DEVOLUCION_COSTO_VENTAS.getNombre());
/*  682:     */         }
/*  683: 770 */         asiento.setTipoAsiento(tipoAsiento);
/*  684:     */         
/*  685: 772 */         concepto = DocumentoBase.INTERFAZ_DEVOLUCION_COSTO_VENTAS.getNombre() + " " + FuncionesUtiles.dateToString(fechaDesde) + " hasta " + FuncionesUtiles.dateToString(fechaHasta);
/*  686: 773 */         asiento.setConcepto(concepto);
/*  687:     */         
/*  688: 775 */         asiento.setDocumentoOrigen(interfazContableProceso.getDocumento());
/*  689:     */         
/*  690:     */ 
/*  691: 778 */         List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(interfazContableProceso.getIdOrganizacion(), DocumentoBase.DESPACHO_BODEGA);
/*  692:     */         
/*  693: 780 */         listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(interfazContableProceso.getIdOrganizacion(), DocumentoBase.DESPACHO_BODEGA);
/*  694:     */         
/*  695:     */ 
/*  696: 783 */         asiento.setListaDetalleAsiento(new ArrayList());
/*  697:     */         
/*  698: 785 */         interfazContableProceso.setListaFacturaCliente(this.facturaClienteDao.getListaFacturaCliente(fechaDesde, fechaHasta, documentoBaseFiltro, 0, interfazContableProceso
/*  699: 786 */           .getIdOrganizacion()));
/*  700:     */         
/*  701: 788 */         interfazContableProceso.setListaProcesosContabilizados(new ArrayList());
/*  702:     */         
/*  703: 790 */         List<List<Integer>> listaTotal = interfazContableProceso.getListaProcesosContabilizados();
/*  704: 791 */         listaTotal.add(new ArrayList());
/*  705: 792 */         int contador = 0;
/*  706: 793 */         int numeroListas = 0;
/*  707: 794 */         for (e = interfazContableProceso.getListaFacturaCliente().iterator(); ((Iterator)e).hasNext();)
/*  708:     */         {
/*  709: 794 */           FacturaCliente fc = (FacturaCliente)((Iterator)e).next();
/*  710: 795 */           if (contador == 2000)
/*  711:     */           {
/*  712: 796 */             listaTotal.add(new ArrayList());
/*  713: 797 */             numeroListas++;
/*  714: 798 */             contador = 0;
/*  715:     */           }
/*  716: 800 */           ((List)listaTotal.get(numeroListas)).add(Integer.valueOf(fc.getIdFacturaCliente()));
/*  717: 801 */           contador++;
/*  718:     */         }
/*  719: 806 */         for (e = listaDocumentoContabilizacion.iterator(); ((Iterator)e).hasNext();)
/*  720:     */         {
/*  721: 806 */           DocumentoContabilizacion documentoContabilizacion = (DocumentoContabilizacion)((Iterator)e).next();
/*  722: 807 */           List<DetalleInterfazContableProceso> lista = new ArrayList();
/*  723: 809 */           for (List<Integer> list : interfazContableProceso.getListaProcesosContabilizados()) {
/*  724: 810 */             if (list.size() > 0) {
/*  725: 811 */               lista.addAll(this.facturaClienteDao.getInterfazDevolucionCostoVentasDimensiones(list, documentoContabilizacion
/*  726: 812 */                 .getProcesoContabilizacion()));
/*  727:     */             }
/*  728:     */           }
/*  729: 816 */           if (lista.size() > 0) {
/*  730: 818 */             asiento.getListaDetalleAsiento().addAll(generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true, false));
/*  731:     */           }
/*  732:     */         }
/*  733: 823 */         break;
/*  734:     */       }
/*  735: 829 */       if (interfazContableProceso.isIndicadorAgrupadoPorCuenta())
/*  736:     */       {
/*  737: 831 */         TreeMap<String, DetalleAsiento> tmDetalleAsiento = new TreeMap();
/*  738: 833 */         for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/*  739: 835 */           if ((detalleAsiento.getHaber().compareTo(BigDecimal.ZERO) != 0) || (detalleAsiento.getDebe().compareTo(BigDecimal.ZERO) != 0))
/*  740:     */           {
/*  741: 837 */             String codigo = detalleAsiento.getCuentaContable().getCodigo();
/*  742:     */             
/*  743: 839 */             String dimension1 = "~" + detalleAsiento.getDimensionContable1().getIdDimensionContable();
/*  744:     */             
/*  745: 841 */             String dimension2 = "~" + detalleAsiento.getDimensionContable2().getIdDimensionContable();
/*  746:     */             
/*  747: 843 */             String dimension3 = "~" + detalleAsiento.getDimensionContable3().getIdDimensionContable();
/*  748:     */             
/*  749: 845 */             String dimension4 = "~" + detalleAsiento.getDimensionContable4().getIdDimensionContable();
/*  750:     */             
/*  751: 847 */             String dimension5 = "~" + detalleAsiento.getDimensionContable5().getIdDimensionContable();
/*  752:     */             
/*  753: 849 */             String clave = "";
/*  754: 850 */             if (detalleAsiento.getHaber().compareTo(BigDecimal.ZERO) == 1) {
/*  755: 851 */               clave = "H~" + codigo + dimension1 + dimension2 + dimension3 + dimension4 + dimension5;
/*  756:     */             } else {
/*  757: 853 */               clave = "D~" + codigo + dimension1 + dimension2 + dimension3 + dimension4 + dimension5;
/*  758:     */             }
/*  759: 856 */             DetalleAsiento da = (DetalleAsiento)tmDetalleAsiento.get(clave);
/*  760: 857 */             if (da == null)
/*  761:     */             {
/*  762: 858 */               detalleAsiento.setDescripcion(concepto);
/*  763: 859 */               tmDetalleAsiento.put(clave, detalleAsiento);
/*  764:     */             }
/*  765:     */             else
/*  766:     */             {
/*  767: 861 */               if (clave.contains("H~")) {
/*  768: 862 */                 da.setHaber(da.getHaber().add(detalleAsiento.getHaber()));
/*  769:     */               }
/*  770: 864 */               if (clave.contains("D~")) {
/*  771: 865 */                 da.setDebe(da.getDebe().add(detalleAsiento.getDebe()));
/*  772:     */               }
/*  773:     */             }
/*  774:     */           }
/*  775:     */         }
/*  776: 872 */         asiento.setListaDetalleAsiento(new ArrayList());
/*  777: 873 */         asiento.getListaDetalleAsiento().addAll(tmDetalleAsiento.values());
/*  778:     */       }
/*  779: 876 */       asiento.setIdOrganizacion(interfazContableProceso.getIdOrganizacion());
/*  780: 877 */       asiento.setSucursal(interfazContableProceso.getSucursal() == null ? AppUtil.getSucursal() : interfazContableProceso.getSucursal());
/*  781: 878 */       asiento.setFecha(fechaContabilizacion);
/*  782: 879 */       asiento.setTipoAsiento(tipoAsiento);
/*  783: 880 */       asiento.setEstado(Estado.ELABORADO);
/*  784: 881 */       asiento.setConcepto(concepto);
/*  785: 882 */       asiento.setIndicadorAutomatico(true);
/*  786:     */       
/*  787:     */ 
/*  788: 885 */       redondearAsiento(asiento.getListaDetalleAsiento());
/*  789:     */       
/*  790:     */ 
/*  791: 888 */       ajustarDiferencias(asiento);
/*  792:     */       
/*  793: 890 */       return interfazContableProceso.getAsiento();
/*  794:     */     }
/*  795:     */     catch (ExcepcionAS2Financiero e)
/*  796:     */     {
/*  797: 893 */       this.context.setRollbackOnly();
/*  798: 894 */       throw e;
/*  799:     */     }
/*  800:     */     catch (ExcepcionAS2 e)
/*  801:     */     {
/*  802: 896 */       this.context.setRollbackOnly();
/*  803: 897 */       throw e;
/*  804:     */     }
/*  805:     */     catch (AS2Exception e)
/*  806:     */     {
/*  807: 899 */       this.context.setRollbackOnly();
/*  808: 900 */       throw e;
/*  809:     */     }
/*  810:     */     catch (Exception e)
/*  811:     */     {
/*  812: 902 */       this.context.setRollbackOnly();
/*  813: 903 */       throw new ExcepcionAS2(e);
/*  814:     */     }
/*  815:     */   }
/*  816:     */   
/*  817:     */   public void redondearAsiento(List<DetalleAsiento> listaDetalleAsiento)
/*  818:     */   {
/*  819: 910 */     for (DetalleAsiento d : listaDetalleAsiento)
/*  820:     */     {
/*  821: 911 */       d.setDebe(d.getDebe().setScale(2, RoundingMode.HALF_UP));
/*  822: 912 */       d.setHaber(d.getHaber().setScale(2, RoundingMode.HALF_UP));
/*  823:     */     }
/*  824:     */   }
/*  825:     */   
/*  826:     */   private void corregirDecimalesAsiento(Asiento asiento)
/*  827:     */   {
/*  828: 918 */     this.servicioAsiento.calcularTotales(asiento);
/*  829:     */     
/*  830: 920 */     BigDecimal diferencia = asiento.getTotalDebe().subtract(asiento.getTotalHaber());
/*  831: 922 */     if (diferencia.compareTo(BigDecimal.ZERO) != 0) {
/*  832: 923 */       for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/*  833: 924 */         if (detalleAsiento.getHaber().compareTo(BigDecimal.ZERO) > 0)
/*  834:     */         {
/*  835: 925 */           detalleAsiento.setHaber(detalleAsiento.getHaber().add(diferencia));
/*  836: 926 */           break;
/*  837:     */         }
/*  838:     */       }
/*  839:     */     }
/*  840: 932 */     this.servicioAsiento.calcularTotales(asiento);
/*  841:     */   }
/*  842:     */   
/*  843:     */   public void esEditable(InterfazContableProceso interfazContableProceso)
/*  844:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/*  845:     */   {
/*  846: 937 */     this.servicioPeriodo.buscarPorFecha(interfazContableProceso.getFechaHasta(), interfazContableProceso.getIdOrganizacion(), interfazContableProceso
/*  847: 938 */       .getDocumento() != null ? interfazContableProceso.getDocumento().getDocumentoBase() : null);
/*  848: 939 */     if (interfazContableProceso.getEstado() == Estado.ANULADO) {
/*  849: 941 */       throw new ExcepcionAS2Financiero("msg_error_anular");
/*  850:     */     }
/*  851: 943 */     if ((interfazContableProceso.getAsiento() != null) && (interfazContableProceso.getAsiento().getEstado() == Estado.REVISADO)) {
/*  852: 945 */       throw new ExcepcionAS2Financiero("msg_error_anular");
/*  853:     */     }
/*  854:     */   }
/*  855:     */   
/*  856:     */   public List<DetalleCierreCaja> obtenerListaDetalleCierreCaja(InterfazContableProceso interfazContableProceso, int idSucursal)
/*  857:     */   {
/*  858: 951 */     List<DetalleCierreCaja> listaCierreCaja = this.interfazContableProcesoDao.obtenerListaDetalleCierreCaja(interfazContableProceso, idSucursal);
/*  859:     */     
/*  860: 953 */     return listaCierreCaja;
/*  861:     */   }
/*  862:     */   
/*  863:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  864:     */   public void anular(InterfazContableProceso interfazContableProceso)
/*  865:     */     throws ExcepcionAS2
/*  866:     */   {
/*  867:     */     try
/*  868:     */     {
/*  869: 962 */       esEditable(interfazContableProceso);
/*  870:     */       
/*  871: 964 */       interfazContableProceso = this.interfazContableProcesoDao.cargarDetalle(interfazContableProceso.getId(), true);
/*  872: 966 */       if (interfazContableProceso.getDocumentoBase() == DocumentoBase.INTERFAZ_CONSUMOS_BODEGA) {
/*  873: 968 */         this.movimientoInventarioDao.actualizarEstadoConsumosInterfazContable(interfazContableProceso);
/*  874: 969 */       } else if (interfazContableProceso.getDocumentoBase() == DocumentoBase.INTERFAZ_DESPACHOS) {
/*  875: 971 */         this.despachoClienteDao.actualizarEstadoDespachosInterfazContable(interfazContableProceso);
/*  876: 972 */       } else if ((interfazContableProceso.getDocumentoBase() == DocumentoBase.INTERFAZ_VENTAS) || 
/*  877: 973 */         (interfazContableProceso.getDocumentoBase() == DocumentoBase.INTERFAZ_DEVOLUCION_COSTO_VENTAS)) {
/*  878: 975 */         this.facturaClienteDao.actualizarEstadoFacturasInterfazContable(interfazContableProceso);
/*  879: 976 */       } else if (interfazContableProceso.getDocumentoBase() == DocumentoBase.DEPOSITO_CAJA) {
/*  880: 977 */         for (DetalleCierreCaja detalleCierreCaja : interfazContableProceso.getListaDetalleCierreCaja())
/*  881:     */         {
/*  882: 978 */           detalleCierreCaja.setInterfazContableProceso(null);
/*  883: 979 */           this.detalleCierreCajaDao.guardar(detalleCierreCaja);
/*  884:     */         }
/*  885: 981 */       } else if (interfazContableProceso.getDocumentoBase() == DocumentoBase.CONTRATO_VENTA) {
/*  886: 983 */         this.detalleValoresContratoVentaDao.liberarDetalleValoresContratoVentaInterfazContable(interfazContableProceso);
/*  887: 984 */       } else if (interfazContableProceso.getDocumentoBase() == DocumentoBase.AMORTIZACION) {
/*  888: 985 */         this.servicioAmortizacion.anularInterfazContable(interfazContableProceso);
/*  889: 986 */       } else if (interfazContableProceso.getDocumentoBase() == DocumentoBase.EXTRACTO_BANCARIO) {
/*  890: 988 */         if (interfazContableProceso.getEstado().equals(Estado.CONTABILIZADO)) {
/*  891: 989 */           for (ExtractoBancario eb : interfazContableProceso.getListaExtractoBancario())
/*  892:     */           {
/*  893: 990 */             this.servicioAsiento.anular(eb.getAsiento());
/*  894: 991 */             eb.setAsiento(null);
/*  895: 992 */             this.extractoBancarioDao.guardar(eb);
/*  896:     */           }
/*  897: 994 */         } else if (interfazContableProceso.getEstado().equals(Estado.ELABORADO)) {
/*  898: 995 */           for (ExtractoBancario eb : interfazContableProceso.getListaExtractoBancario()) {
/*  899: 996 */             if (eb.getAsiento() != null) {
/*  900: 997 */               this.servicioAsiento.anular(eb.getAsiento());
/*  901:     */             }
/*  902:     */           }
/*  903:     */         }
/*  904:     */       }
/*  905:1006 */       if (interfazContableProceso.getAsiento() != null)
/*  906:     */       {
/*  907:1007 */         interfazContableProceso.getAsiento().setIndicadorAutomatico(false);
/*  908:1008 */         this.servicioAsiento.anular(interfazContableProceso.getAsiento());
/*  909:     */       }
/*  910:1011 */       if ((interfazContableProceso.getDocumentoBase() == DocumentoBase.EXTRACTO_BANCARIO) && 
/*  911:1012 */         (interfazContableProceso.getEstado().equals(Estado.CONTABILIZADO))) {
/*  912:1013 */         interfazContableProceso.setEstado(Estado.ELABORADO);
/*  913:     */       } else {
/*  914:1015 */         interfazContableProceso.setEstado(Estado.ANULADO);
/*  915:     */       }
/*  916:1018 */       this.interfazContableProcesoDao.guardar(interfazContableProceso);
/*  917:     */     }
/*  918:     */     catch (ExcepcionAS2Financiero e)
/*  919:     */     {
/*  920:1021 */       e.printStackTrace();
/*  921:1022 */       this.context.setRollbackOnly();
/*  922:1023 */       throw e;
/*  923:     */     }
/*  924:     */     catch (ExcepcionAS2 e)
/*  925:     */     {
/*  926:1026 */       e.printStackTrace();
/*  927:1027 */       this.context.setRollbackOnly();
/*  928:1028 */       throw e;
/*  929:     */     }
/*  930:     */   }
/*  931:     */   
/*  932:     */   public void actualizarEstadoFacturasInterfazContable(InterfazContableProceso interfazContableProceso)
/*  933:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  934:     */   {
/*  935:1043 */     this.facturaClienteDao.actualizarEstadoFacturasInterfazContable(interfazContableProceso);
/*  936:     */   }
/*  937:     */   
/*  938:     */   private void cargarSecuencia(InterfazContableProceso interfazContableProceso)
/*  939:     */     throws ExcepcionAS2
/*  940:     */   {
/*  941:1048 */     if ((interfazContableProceso.getNumero() == null) || (interfazContableProceso.getNumero().isEmpty()))
/*  942:     */     {
/*  943:1049 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(interfazContableProceso.getDocumento().getId(), interfazContableProceso
/*  944:1050 */         .getFechaHasta());
/*  945:1051 */       interfazContableProceso.setNumero(numero);
/*  946:     */     }
/*  947:     */   }
/*  948:     */   
/*  949:     */   public void calcularValorTotal(InterfazContableProceso interfazContableProceso)
/*  950:     */   {
/*  951:1064 */     interfazContableProceso.setValor(BigDecimal.ZERO);
/*  952:1065 */     for (DetalleCierreCaja detalleCierreCaja : interfazContableProceso.getListaDetalleCierreCaja()) {
/*  953:1066 */       if (detalleCierreCaja.isSeleccionado()) {
/*  954:1067 */         interfazContableProceso.setValor(interfazContableProceso.getValor().add(detalleCierreCaja.getValor()));
/*  955:     */       }
/*  956:     */     }
/*  957:     */   }
/*  958:     */   
/*  959:     */   public List<DetalleAsiento> generarAsiento(Asiento asiento, List<DetalleInterfazContableProceso> listaDetalleInterfazContableProceso, DocumentoContabilizacion documentoContabilizacion, List<CriterioDistribucion> listaCriterioDistribucion, boolean reverso)
/*  960:     */     throws ExcepcionAS2Financiero, AS2Exception
/*  961:     */   {
/*  962:1076 */     return generarAsiento(asiento, listaDetalleInterfazContableProceso, documentoContabilizacion, listaCriterioDistribucion, reverso, true);
/*  963:     */   }
/*  964:     */   
/*  965:     */   public List<DetalleAsiento> generarAsiento(Asiento asiento, List<DetalleInterfazContableProceso> listaDetalleInterfazContableProceso, DocumentoContabilizacion documentoContabilizacion, List<CriterioDistribucion> listaCriterioDistribucion, boolean reverso, boolean rendodear)
/*  966:     */     throws ExcepcionAS2Financiero, AS2Exception
/*  967:     */   {
/*  968:1083 */     List<CriterioContabilizacion> listaCriterioContabilizacion = documentoContabilizacion.getListaCriterioContabilizacion();
/*  969:1084 */     if (listaCriterioContabilizacion.size() == 0) {
/*  970:1087 */       throw new ExcepcionAS2Financiero("msg_error_criterio_contabilizacion", ": " + documentoContabilizacion.getDocumentoBase().getNombre() + " - " + documentoContabilizacion.getProcesoContabilizacion().getNombre());
/*  971:     */     }
/*  972:1092 */     for (Iterator localIterator1 = listaDetalleInterfazContableProceso.iterator(); localIterator1.hasNext();)
/*  973:     */     {
/*  974:1092 */       dicp = (DetalleInterfazContableProceso)localIterator1.next();
/*  975:1094 */       for (CriterioContabilizacion cc : listaCriterioContabilizacion)
/*  976:     */       {
/*  977:1096 */         documento = dicp.getDocumento() == null ? -1 : dicp.getDocumento().intValue();
/*  978:1097 */         int sucursal = dicp.getSucursal() == null ? -1 : dicp.getSucursal().intValue();
/*  979:1098 */         int categoriaEmpresa = dicp.getCategoriaEmpresa() == null ? -1 : dicp.getCategoriaEmpresa().intValue();
/*  980:1099 */         int cliente = dicp.getCliente() == null ? -1 : dicp.getCliente().intValue();
/*  981:1100 */         int proveedor = dicp.getProveedor() == null ? -1 : dicp.getProveedor().intValue();
/*  982:1101 */         int categoriaProducto = dicp.getCategoriaProducto() == null ? -1 : dicp.getCategoriaProducto().intValue();
/*  983:1102 */         int subcategoriaProducto = dicp.getSubcategoriaProducto() == null ? -1 : dicp.getSubcategoriaProducto().intValue();
/*  984:1103 */         int producto = dicp.getProducto() == null ? -1 : dicp.getProducto().intValue();
/*  985:1104 */         int bodega = dicp.getBodega() == null ? -1 : dicp.getBodega().intValue();
/*  986:1105 */         int canal = dicp.getCanal() == null ? -1 : dicp.getCanal().intValue();
/*  987:1106 */         int subcliente = dicp.getSubcliente() == null ? -1 : dicp.getSubcliente().intValue();
/*  988:1107 */         int subproveedor = dicp.getSubproveedor() == null ? -1 : dicp.getSubproveedor().intValue();
/*  989:1108 */         int zona = dicp.getZona() == null ? -1 : dicp.getZona().intValue();
/*  990:1109 */         int categoriaImpuesto = dicp.getCategoriaImpuesto() == null ? -1 : dicp.getCategoriaImpuesto().intValue();
/*  991:1110 */         int impuesto = dicp.getImpuesto() == null ? -1 : dicp.getImpuesto().intValue();
/*  992:1111 */         int motivoNotaCreditoCliente = dicp.getMotivoNotaCreditoCliente() == null ? -1 : dicp.getMotivoNotaCreditoCliente().intValue();
/*  993:1112 */         int motivoNotaCreditoProveedor = dicp.getMotivoNotaCreditoProveedor() == null ? -1 : dicp.getMotivoNotaCreditoProveedor().intValue();
/*  994:1113 */         int motivoAjusteInventario = dicp.getMotivoAjusteInventario() == null ? -1 : dicp.getMotivoAjusteInventario().intValue();
/*  995:1114 */         int motivoBajaActivo = dicp.getMotivoBajaActivo() == null ? -1 : dicp.getMotivoBajaActivo().intValue();
/*  996:1115 */         int categoriaActivo = dicp.getCategoriaActivo() == null ? -1 : dicp.getCategoriaActivo().intValue();
/*  997:1116 */         int subcategoriaActivo = dicp.getSubcategoriaActivo() == null ? -1 : dicp.getSubcategoriaActivo().intValue();
/*  998:1117 */         int activoFijo = dicp.getActivoFijo() == null ? -1 : dicp.getActivoFijo().intValue();
/*  999:1118 */         int empleado = dicp.getEmpleado() == null ? -1 : dicp.getEmpleado().intValue();
/* 1000:1119 */         int rubro = dicp.getRubro() == null ? -1 : dicp.getRubro().intValue();
/* 1001:1120 */         int conceptoContable = dicp.getConceptoContable() == null ? -1 : dicp.getConceptoContable().intValue();
/* 1002:1121 */         int departamento = dicp.getDepartamento() == null ? -1 : dicp.getDepartamento().intValue();
/* 1003:1122 */         int destinoCosto = dicp.getDestinoCosto() == null ? -1 : dicp.getDestinoCosto().intValue();
/* 1004:1123 */         int tipoAmortizacion = dicp.getTipoAmortizacion() == null ? -1 : dicp.getTipoAmortizacion().intValue();
/* 1005:1124 */         if (existeCriterioContabilizacion(cc, documento, sucursal, categoriaEmpresa, cliente, proveedor, categoriaProducto, subcategoriaProducto, producto, bodega, canal, subcliente, subproveedor, zona, categoriaImpuesto, impuesto, motivoNotaCreditoCliente, motivoNotaCreditoProveedor, motivoAjusteInventario, motivoBajaActivo, categoriaActivo, subcategoriaActivo, activoFijo, empleado, rubro, conceptoContable, departamento, destinoCosto, tipoAmortizacion))
/* 1006:     */         {
/* 1007:1131 */           dicp.setSeleccionadoCriterioContabilizacion(true);
/* 1008:1132 */           dicp.setCuentaContable(cc.getCuentaContable());
/* 1009:1133 */           BigDecimal valor = dicp.getValor();
/* 1010:1134 */           if (((documentoContabilizacion.isHaber()) && (!reverso)) || ((documentoContabilizacion.isDebe()) && (reverso))) {
/* 1011:1135 */             dicp.setValor(valor.negate());
/* 1012:     */           }
/* 1013:1139 */           dicp.setDimensionContable1(!dicp.getCuentaContable().isIndicadorValidarDimension1() ? null : dicp.getDimensionContable1());
/* 1014:1140 */           dicp.setDimensionContable2(!dicp.getCuentaContable().isIndicadorValidarDimension2() ? null : dicp.getDimensionContable2());
/* 1015:1141 */           dicp.setDimensionContable3(!dicp.getCuentaContable().isIndicadorValidarDimension3() ? null : dicp.getDimensionContable3());
/* 1016:1142 */           dicp.setDimensionContable4(!dicp.getCuentaContable().isIndicadorValidarDimension4() ? null : dicp.getDimensionContable4());
/* 1017:1143 */           dicp.setDimensionContable5(!dicp.getCuentaContable().isIndicadorValidarDimension5() ? null : dicp.getDimensionContable5());
/* 1018:1145 */           if ((!dicp.getCuentaContable().isIndicadorValidarDimension1()) && (!dicp.getCuentaContable().isIndicadorValidarDimension2()) && 
/* 1019:1146 */             (!dicp.getCuentaContable().isIndicadorValidarDimension3()) && (!dicp.getCuentaContable().isIndicadorValidarDimension4()) && 
/* 1020:1147 */             (!dicp.getCuentaContable().isIndicadorValidarDimension5())) {
/* 1021:     */             break;
/* 1022:     */           }
/* 1023:1148 */           for (CriterioDistribucion cd : listaCriterioDistribucion) {
/* 1024:1149 */             if (existeCriterioDistribucion(cd, documento, sucursal, categoriaEmpresa, cliente, proveedor, categoriaProducto, subcategoriaProducto, producto, bodega, canal, subcliente, subproveedor, zona, categoriaImpuesto, impuesto, motivoNotaCreditoCliente, motivoNotaCreditoProveedor, motivoAjusteInventario, motivoBajaActivo, categoriaActivo, subcategoriaActivo, activoFijo, empleado, rubro, conceptoContable, departamento, destinoCosto, tipoAmortizacion))
/* 1025:     */             {
/* 1026:1154 */               dicp.setSeleccionadoCriterioDistribucion(true);
/* 1027:1155 */               dicp.setDimensionContable1(dicp
/* 1028:1156 */                 .getDimensionContable1() != null ? dicp.getDimensionContable1() : !dicp.getCuentaContable().isIndicadorValidarDimension1() ? null : cd.getDimensionContable1());
/* 1029:1157 */               dicp.setDimensionContable2(dicp
/* 1030:1158 */                 .getDimensionContable2() != null ? dicp.getDimensionContable2() : !dicp.getCuentaContable().isIndicadorValidarDimension2() ? null : cd.getDimensionContable2());
/* 1031:1159 */               dicp.setDimensionContable3(dicp
/* 1032:1160 */                 .getDimensionContable3() != null ? dicp.getDimensionContable3() : !dicp.getCuentaContable().isIndicadorValidarDimension3() ? null : cd.getDimensionContable3());
/* 1033:1161 */               dicp.setDimensionContable4(dicp
/* 1034:1162 */                 .getDimensionContable4() != null ? dicp.getDimensionContable4() : !dicp.getCuentaContable().isIndicadorValidarDimension4() ? null : cd.getDimensionContable4());
/* 1035:1163 */               dicp.setDimensionContable5(dicp
/* 1036:1164 */                 .getDimensionContable5() != null ? dicp.getDimensionContable5() : !dicp.getCuentaContable().isIndicadorValidarDimension5() ? null : cd.getDimensionContable5());
/* 1037:1165 */               break;
/* 1038:     */             }
/* 1039:     */           }
/* 1040:1168 */           break;
/* 1041:     */         }
/* 1042:     */       }
/* 1043:     */     }
/* 1044:     */     int documento;
/* 1045:1174 */     AS2Exception e = null;
/* 1046:1175 */     for (DetalleInterfazContableProceso dicp = listaDetalleInterfazContableProceso.iterator(); dicp.hasNext();)
/* 1047:     */     {
/* 1048:1175 */       dicp = (DetalleInterfazContableProceso)dicp.next();
/* 1049:1176 */       if (!((DetalleInterfazContableProceso)dicp).isSeleccionadoCriterioContabilizacion()) {
/* 1050:1177 */         e = erorCriterioContabilizacionDistribucion(documentoContabilizacion, e, (DetalleInterfazContableProceso)dicp, "");
/* 1051:     */       }
/* 1052:     */     }
/* 1053:1180 */     if (e != null) {
/* 1054:1181 */       throw e;
/* 1055:     */     }
/* 1056:1183 */     String tipoError = "Criterio Distribución: ";
/* 1057:1184 */     for (Object dicp = listaDetalleInterfazContableProceso.iterator(); ((Iterator)dicp).hasNext();)
/* 1058:     */     {
/* 1059:1184 */       DetalleInterfazContableProceso dicp = (DetalleInterfazContableProceso)((Iterator)dicp).next();
/* 1060:1185 */       if ((dicp.getCuentaContable().isIndicadorValidarDimension1()) && (dicp.getDimensionContable1() == null)) {
/* 1061:1186 */         e = erorCriterioContabilizacionDistribucion(documentoContabilizacion, e, dicp, tipoError + " Dimensión 1 ");
/* 1062:     */       }
/* 1063:1188 */       if ((dicp.getCuentaContable().isIndicadorValidarDimension2()) && (dicp.getDimensionContable2() == null)) {
/* 1064:1189 */         e = erorCriterioContabilizacionDistribucion(documentoContabilizacion, e, dicp, tipoError + " Dimensión 2 ");
/* 1065:     */       }
/* 1066:1191 */       if ((dicp.getCuentaContable().isIndicadorValidarDimension3()) && (dicp.getDimensionContable3() == null)) {
/* 1067:1192 */         e = erorCriterioContabilizacionDistribucion(documentoContabilizacion, e, dicp, tipoError + " Dimensión 3 ");
/* 1068:     */       }
/* 1069:1194 */       if ((dicp.getCuentaContable().isIndicadorValidarDimension4()) && (dicp.getDimensionContable4() == null)) {
/* 1070:1195 */         e = erorCriterioContabilizacionDistribucion(documentoContabilizacion, e, dicp, tipoError + " Dimensión 4 ");
/* 1071:     */       }
/* 1072:1197 */       if ((dicp.getCuentaContable().isIndicadorValidarDimension5()) && (dicp.getDimensionContable5() == null)) {
/* 1073:1198 */         e = erorCriterioContabilizacionDistribucion(documentoContabilizacion, e, dicp, tipoError + " Dimensión 5 ");
/* 1074:     */       }
/* 1075:     */     }
/* 1076:1202 */     if (e != null) {
/* 1077:1203 */       throw e;
/* 1078:     */     }
/* 1079:1206 */     Object listaDetalleAsientoFinal = new ArrayList();
/* 1080:1207 */     Map<String, DetalleAsiento> hmDetalleAsientoFinal = new HashMap();
/* 1081:1208 */     for (DetalleInterfazContableProceso dicp : listaDetalleInterfazContableProceso) {
/* 1082:1209 */       if ((dicp.getCuentaContable() != null) && (dicp.getValor().compareTo(BigDecimal.ZERO) != 0))
/* 1083:     */       {
/* 1084:1210 */         String clave = (dicp.getValor().compareTo(BigDecimal.ZERO) > 0 ? "D~" : "H~") + getClaveDimension(dicp);
/* 1085:     */         
/* 1086:1212 */         DetalleAsiento dac = (DetalleAsiento)hmDetalleAsientoFinal.get(clave);
/* 1087:     */         
/* 1088:1214 */         BigDecimal debe = dicp.getValor().compareTo(BigDecimal.ZERO) > 0 ? dicp.getValor() : BigDecimal.ZERO;
/* 1089:1215 */         BigDecimal haber = dicp.getValor().compareTo(BigDecimal.ZERO) < 0 ? dicp.getValor().negate() : BigDecimal.ZERO;
/* 1090:1216 */         if (dac == null)
/* 1091:     */         {
/* 1092:1217 */           dac = new DetalleAsiento();
/* 1093:     */           
/* 1094:1219 */           dac.setCuentaContable(dicp.getCuentaContable());
/* 1095:     */           
/* 1096:1221 */           dac.setDimensionContable1(dicp.getDimensionContable1());
/* 1097:1222 */           dac.setDimensionContable2(dicp.getDimensionContable2());
/* 1098:1223 */           dac.setDimensionContable3(dicp.getDimensionContable3());
/* 1099:1224 */           dac.setDimensionContable4(dicp.getDimensionContable4());
/* 1100:1225 */           dac.setDimensionContable5(dicp.getDimensionContable5());
/* 1101:     */           
/* 1102:1227 */           dac.setAsiento(asiento);
/* 1103:1228 */           hmDetalleAsientoFinal.put(clave, dac);
/* 1104:1229 */           ((List)listaDetalleAsientoFinal).add(dac);
/* 1105:     */         }
/* 1106:1232 */         dac.setDebe(dac.getDebe().add(debe));
/* 1107:1233 */         dac.setHaber(dac.getHaber().add(haber));
/* 1108:     */         
/* 1109:1235 */         String descripcion = dac.getDescripcion() + " ";
/* 1110:1236 */         String descripcion2 = dicp.getDescripcion() + " ";
/* 1111:1238 */         if (!descripcion.contains(descripcion2)) {
/* 1112:1239 */           dac.setDescripcion(descripcion + descripcion2);
/* 1113:     */         } else {
/* 1114:1241 */           dac.setDescripcion(descripcion);
/* 1115:     */         }
/* 1116:1244 */         if (dac.getDescripcion().length() > 200) {
/* 1117:1245 */           dac.setDescripcion(dac.getDescripcion().substring(0, 200));
/* 1118:     */         }
/* 1119:     */       }
/* 1120:     */     }
/* 1121:1250 */     if (rendodear) {
/* 1122:1251 */       redondearAsiento((List)listaDetalleAsientoFinal);
/* 1123:     */     }
/* 1124:1253 */     return listaDetalleAsientoFinal;
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   private boolean existeCriterioDistribucion(CriterioDistribucion cd, int documento, int sucursal, int categoriaEmpresa, int cliente, int proveedor, int categoriaProducto, int subcategoriaProducto, int producto, int bodega, int canal, int subcliente, int subproveedor, int zona, int categoriaImpuesto, int impuesto, int motivoNotaCreditoCliente, int motivoNotaCreditoProveedor, int motivoAjusteInventario, int motivoBajaActivo, int categoriaActivo, int subcategoriaActivo, int activoFijo, int empleado, int rubro, int conceptoContable, int departamento, int destinoCosto, int tipoAmortizacion)
/* 1128:     */   {
/* 1129:1286 */     return ((cd.getDocumento() == null) || (cd.getDocumento().getId() == documento)) && ((cd.getSucursal() == null) || (cd.getSucursal().getId() == sucursal)) && ((cd.getCategoriaEmpresa() == null) || (cd.getCategoriaEmpresa().getId() == categoriaEmpresa)) && ((cd.getCliente() == null) || (cd.getCliente().getId() == cliente)) && ((cd.getProveedor() == null) || (cd.getProveedor().getId() == proveedor)) && ((cd.getCategoriaProducto() == null) || (cd.getCategoriaProducto().getId() == categoriaProducto)) && ((cd.getSubcategoriaProducto() == null) || (cd.getSubcategoriaProducto().getId() == subcategoriaProducto)) && ((cd.getProducto() == null) || (cd.getProducto().getId() == producto)) && ((cd.getBodega() == null) || (cd.getBodega().getId() == bodega)) && ((cd.getCanal() == null) || (cd.getCanal().getId() == canal)) && ((cd.getSubcliente() == null) || (cd.getSubcliente().getId() == subcliente)) && ((cd.getSubproveedor() == null) || (cd.getSubproveedor().getId() == subproveedor)) && ((cd.getZona() == null) || (cd.getZona().getId() == zona)) && ((cd.getCategoriaImpuesto() == null) || (cd.getCategoriaImpuesto().getId() == categoriaImpuesto)) && ((cd.getImpuesto() == null) || (cd.getImpuesto().getId() == impuesto)) && ((cd.getMotivoNotaCreditoCliente() == null) || (cd.getMotivoNotaCreditoCliente().getId() == motivoNotaCreditoCliente)) && ((cd.getMotivoNotaCreditoProveedor() == null) || (cd.getMotivoNotaCreditoProveedor().getId() == motivoNotaCreditoProveedor)) && ((cd.getMotivoAjusteInventario() == null) || (cd.getMotivoAjusteInventario().getId() == motivoAjusteInventario)) && ((cd.getMotivoBajaActivo() == null) || (cd.getMotivoBajaActivo().getId() == motivoBajaActivo)) && ((cd.getCategoriaActivo() == null) || (cd.getCategoriaActivo().getId() == categoriaActivo)) && ((cd.getSubcategoriaActivo() == null) || (cd.getSubcategoriaActivo().getId() == subcategoriaActivo)) && ((cd.getActivoFijo() == null) || (cd.getActivoFijo().getId() == activoFijo)) && ((cd.getEmpleado() == null) || (cd.getEmpleado().getId() == empleado)) && ((cd.getRubro() == null) || (cd.getRubro().getId() == rubro)) && ((cd.getConceptoContable() == null) || (cd.getConceptoContable().getId() == conceptoContable)) && ((cd.getDepartamento() == null) || (cd.getDepartamento().getId() == departamento)) && ((cd.getDestinoCosto() == null) || (cd.getDestinoCosto().getId() == destinoCosto)) && ((cd.getTipoAmortizacion() == null) || (cd.getTipoAmortizacion().getId() == tipoAmortizacion));
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   private boolean existeCriterioContabilizacion(CriterioContabilizacion cc, int documento, int sucursal, int categoriaEmpresa, int cliente, int proveedor, int categoriaProducto, int subcategoriaProducto, int producto, int bodega, int canal, int subcliente, int subproveedor, int zona, int categoriaImpuesto, int impuesto, int motivoNotaCreditoCliente, int motivoNotaCreditoProveedor, int motivoAjusteInventario, int motivoBajaActivo, int categoriaActivo, int subcategoriaActivo, int activoFijo, int empleado, int rubro, int conceptoContable, int departamento, int destinoCosto, int tipoAmortizacion)
/* 1133:     */   {
/* 1134:1319 */     return ((cc.getDocumento() == null) || (cc.getDocumento().getId() == documento)) && ((cc.getSucursal() == null) || (cc.getSucursal().getId() == sucursal)) && ((cc.getCategoriaEmpresa() == null) || (cc.getCategoriaEmpresa().getId() == categoriaEmpresa)) && ((cc.getCliente() == null) || (cc.getCliente().getId() == cliente)) && ((cc.getProveedor() == null) || (cc.getProveedor().getId() == proveedor)) && ((cc.getCategoriaProducto() == null) || (cc.getCategoriaProducto().getId() == categoriaProducto)) && ((cc.getSubcategoriaProducto() == null) || (cc.getSubcategoriaProducto().getId() == subcategoriaProducto)) && ((cc.getProducto() == null) || (cc.getProducto().getId() == producto)) && ((cc.getBodega() == null) || (cc.getBodega().getId() == bodega)) && ((cc.getCanal() == null) || (cc.getCanal().getId() == canal)) && ((cc.getSubcliente() == null) || (cc.getSubcliente().getId() == subcliente)) && ((cc.getSubproveedor() == null) || (cc.getSubproveedor().getId() == subproveedor)) && ((cc.getZona() == null) || (cc.getZona().getId() == zona)) && ((cc.getCategoriaImpuesto() == null) || (cc.getCategoriaImpuesto().getId() == categoriaImpuesto)) && ((cc.getImpuesto() == null) || (cc.getImpuesto().getId() == impuesto)) && ((cc.getMotivoNotaCreditoCliente() == null) || (cc.getMotivoNotaCreditoCliente().getId() == motivoNotaCreditoCliente)) && ((cc.getMotivoNotaCreditoProveedor() == null) || (cc.getMotivoNotaCreditoProveedor().getId() == motivoNotaCreditoProveedor)) && ((cc.getMotivoAjusteInventario() == null) || (cc.getMotivoAjusteInventario().getId() == motivoAjusteInventario)) && ((cc.getMotivoBajaActivo() == null) || (cc.getMotivoBajaActivo().getId() == motivoBajaActivo)) && ((cc.getCategoriaActivo() == null) || (cc.getCategoriaActivo().getId() == categoriaActivo)) && ((cc.getSubcategoriaActivo() == null) || (cc.getSubcategoriaActivo().getId() == subcategoriaActivo)) && ((cc.getActivoFijo() == null) || (cc.getActivoFijo().getId() == activoFijo)) && ((cc.getEmpleado() == null) || (cc.getEmpleado().getId() == empleado)) && ((cc.getRubro() == null) || (cc.getRubro().getId() == rubro)) && ((cc.getConceptoContable() == null) || (cc.getConceptoContable().getId() == conceptoContable)) && ((cc.getDepartamento() == null) || (cc.getDepartamento().getId() == departamento)) && ((cc.getDestinoCosto() == null) || (cc.getDestinoCosto().getId() == destinoCosto)) && ((cc.getTipoAmortizacion() == null) || (cc.getTipoAmortizacion().getId() == tipoAmortizacion));
/* 1135:     */   }
/* 1136:     */   
/* 1137:     */   private AS2Exception erorCriterioContabilizacionDistribucion(DocumentoContabilizacion documentoContabilizacion, AS2Exception e, DetalleInterfazContableProceso dicp, String tipoError)
/* 1138:     */   {
/* 1139:1333 */     String mensaje = tipoError + " " + documentoContabilizacion.getDocumentoBase().getNombre() + "-" + documentoContabilizacion.getProcesoContabilizacion().getNombre();
/* 1140:1336 */     if (dicp.getSucursal() != null) {
/* 1141:1337 */       mensaje = mensaje + " / Sucursal: " + dicp.getSucursal() + "-" + dicp.getNombreSucursal();
/* 1142:     */     }
/* 1143:1340 */     if (dicp.getDocumento() != null) {
/* 1144:1341 */       mensaje = mensaje + " / Documento: " + dicp.getDocumento() + "-" + dicp.getNombreDocumento();
/* 1145:     */     }
/* 1146:1344 */     if (dicp.getImpuesto() != null) {
/* 1147:1345 */       mensaje = mensaje + " / Impuesto: " + dicp.getImpuesto() + "-" + dicp.getNombreImpuesto();
/* 1148:     */     }
/* 1149:1348 */     if (dicp.getCategoriaImpuesto() != null) {
/* 1150:1349 */       mensaje = mensaje + " / Categoria Impuesto: " + dicp.getCategoriaImpuesto() + "-" + dicp.getNombreCategoriaImpuesto();
/* 1151:     */     }
/* 1152:1352 */     if (dicp.getCategoriaEmpresa() != null) {
/* 1153:1353 */       mensaje = mensaje + " / Categoria Empresa: " + dicp.getCategoriaEmpresa() + "-" + dicp.getNombreCategoriaEmpresa();
/* 1154:     */     }
/* 1155:1356 */     if (dicp.getCliente() != null) {
/* 1156:1357 */       mensaje = mensaje + " / Cliente: " + dicp.getCliente() + "-" + dicp.getNombreCliente();
/* 1157:     */     }
/* 1158:1360 */     if (dicp.getProveedor() != null) {
/* 1159:1361 */       mensaje = mensaje + " / Proveedor: " + dicp.getProveedor() + "-" + dicp.getNombreProveedor();
/* 1160:     */     }
/* 1161:1363 */     if (dicp.getCategoriaProducto() != null) {
/* 1162:1364 */       mensaje = mensaje + " / Categoria Producto: " + dicp.getCategoriaProducto() + "-" + dicp.getNombreCategoriaProducto();
/* 1163:     */     }
/* 1164:1367 */     if (dicp.getSubcategoriaProducto() != null) {
/* 1165:1368 */       mensaje = mensaje + " / Subcategoria Producto: " + dicp.getSubcategoriaProducto() + "-" + dicp.getNombreSubcategoriaProducto();
/* 1166:     */     }
/* 1167:1371 */     if (dicp.getProducto() != null) {
/* 1168:1372 */       mensaje = mensaje + " / Producto: " + dicp.getProducto() + "-" + dicp.getNombreProducto();
/* 1169:     */     }
/* 1170:1375 */     if (dicp.getZona() != null) {
/* 1171:1376 */       mensaje = mensaje + " / Zona: " + dicp.getZona() + "-" + dicp.getNombreZona();
/* 1172:     */     }
/* 1173:1379 */     if (dicp.getCanal() != null) {
/* 1174:1380 */       mensaje = mensaje + " / Canal: " + dicp.getCanal() + "-" + dicp.getNombreCanal();
/* 1175:     */     }
/* 1176:1383 */     if (dicp.getMotivoNotaCreditoCliente() != null) {
/* 1177:1384 */       mensaje = mensaje + " / MotivoNotaCreditoCliente: " + dicp.getMotivoNotaCreditoCliente() + "-" + dicp.getNombreMotivoNotaCreditoCliente();
/* 1178:     */     }
/* 1179:1387 */     if (dicp.getMotivoNotaCreditoProveedor() != null) {
/* 1180:1389 */       mensaje = mensaje + " / MotivoNotaCreditoProveedor: " + dicp.getMotivoNotaCreditoProveedor() + "-" + dicp.getNombreMotivoNotaCreditoProveedor();
/* 1181:     */     }
/* 1182:1392 */     if (dicp.getBodega() != null) {
/* 1183:1393 */       mensaje = mensaje + " / Bodega: " + dicp.getBodega() + "-" + dicp.getNombreBodega();
/* 1184:     */     }
/* 1185:1396 */     if (dicp.getMotivoAjusteInventario() != null) {
/* 1186:1397 */       mensaje = mensaje + " / MotivoAjusteInventario: " + dicp.getMotivoAjusteInventario() + "-" + dicp.getNombreMotivoAjusteInventario();
/* 1187:     */     }
/* 1188:1400 */     if (dicp.getDestinoCosto() != null) {
/* 1189:1401 */       mensaje = mensaje + " / DestinoCosto: " + dicp.getDestinoCosto() + "-" + dicp.getNombreDestinoCosto();
/* 1190:     */     }
/* 1191:1404 */     if (dicp.getDepartamento() != null) {
/* 1192:1405 */       mensaje = mensaje + " / Departamento: " + dicp.getDepartamento() + "-" + dicp.getNombreDepartamento();
/* 1193:     */     }
/* 1194:1408 */     if (dicp.getRubro() != null) {
/* 1195:1409 */       mensaje = mensaje + " / Rubro: " + dicp.getRubro() + "-" + dicp.getNombreRubro();
/* 1196:     */     }
/* 1197:1412 */     if (dicp.getEmpleado() != null) {
/* 1198:1413 */       mensaje = mensaje + " / Empleado: " + dicp.getEmpleado() + "-" + dicp.getNombreEmpleado();
/* 1199:     */     }
/* 1200:1416 */     if (dicp.getCategoriaActivo() != null) {
/* 1201:1417 */       mensaje = mensaje + " / CategoriaActivo: " + dicp.getCategoriaActivo() + "-" + dicp.getNombreCategoriaActivo();
/* 1202:     */     }
/* 1203:1420 */     if (dicp.getSubcategoriaActivo() != null) {
/* 1204:1421 */       mensaje = mensaje + " / SubcategoriaActivo: " + dicp.getSubcategoriaActivo() + "-" + dicp.getNombreSubcategoriaActivo();
/* 1205:     */     }
/* 1206:1424 */     if (dicp.getMotivoBajaActivo() != null) {
/* 1207:1425 */       mensaje = mensaje + " / MotivoBajaActivo: " + dicp.getMotivoBajaActivo() + "-" + dicp.getNombreMotivoBajaActivo();
/* 1208:     */     }
/* 1209:1428 */     if (dicp.getActivoFijo() != null) {
/* 1210:1429 */       mensaje = mensaje + " / ActivoFijo: " + dicp.getActivoFijo() + "-" + dicp.getNombreActivoFijo();
/* 1211:     */     }
/* 1212:1432 */     if (dicp.getActivoFijo() != null) {
/* 1213:1433 */       mensaje = mensaje + " / Subcliente: " + dicp.getSubcliente() + "-" + dicp.getNombreSubcliente();
/* 1214:     */     }
/* 1215:1436 */     e = AS2Exception.agregarMensaje(e, "com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioInterfazContableProcesoImpl.MENSAJE_ERROR_CONFIGURACION_CRITERIO_CONTABILIZACION", new String[] { mensaje });
/* 1216:     */     
/* 1217:1438 */     return e;
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   public Asiento ajustarDiferencias(Asiento asiento)
/* 1221:     */   {
/* 1222:1450 */     List<DetalleAsiento> lista = new ArrayList();
/* 1223:1451 */     for (DetalleAsiento detalle : asiento.getListaDetalleAsiento()) {
/* 1224:1452 */       if (!detalle.isEliminado()) {
/* 1225:1453 */         lista.add(detalle);
/* 1226:     */       }
/* 1227:     */     }
/* 1228:1457 */     BigDecimal totalDebe = BigDecimal.ZERO;
/* 1229:1458 */     BigDecimal totalHaber = BigDecimal.ZERO;
/* 1230:1460 */     for (DetalleAsiento detalle : lista) {
/* 1231:1461 */       if (!detalle.isEliminado())
/* 1232:     */       {
/* 1233:1462 */         totalDebe = totalDebe.add(detalle.getDebe());
/* 1234:1463 */         totalHaber = totalHaber.add(detalle.getHaber());
/* 1235:     */       }
/* 1236:     */     }
/* 1237:1467 */     BigDecimal diferencia = totalDebe.subtract(totalHaber);
/* 1238:1469 */     if (diferencia.compareTo(BigDecimal.ZERO) != 0)
/* 1239:     */     {
/* 1240:1471 */       DetalleAsiento detalleAsientoDebe = null;
/* 1241:1472 */       DetalleAsiento detalleAsientoHaber = null;
/* 1242:1474 */       for (DetalleAsiento detalle : lista) {
/* 1243:1475 */         if (!detalle.isEliminado())
/* 1244:     */         {
/* 1245:1476 */           if ((detalle.getDebe().compareTo(BigDecimal.ZERO) > 0) && (detalle.getDebe().compareTo(diferencia.abs()) >= 0)) {
/* 1246:1477 */             detalleAsientoDebe = detalle;
/* 1247:     */           }
/* 1248:1479 */           if ((detalle.getHaber().compareTo(BigDecimal.ZERO) > 0) && (detalle.getHaber().compareTo(diferencia.abs()) >= 0)) {
/* 1249:1480 */             detalleAsientoHaber = detalle;
/* 1250:     */           }
/* 1251:     */         }
/* 1252:     */       }
/* 1253:1485 */       if (diferencia.abs().compareTo(new BigDecimal(0.2D)) <= 0) {
/* 1254:1486 */         if (diferencia.compareTo(BigDecimal.ZERO) > 0) {
/* 1255:1487 */           detalleAsientoHaber.setHaber(detalleAsientoHaber.getHaber().add(diferencia.abs()));
/* 1256:     */         } else {
/* 1257:1489 */           detalleAsientoDebe.setDebe(detalleAsientoDebe.getDebe().add(diferencia.abs()));
/* 1258:     */         }
/* 1259:     */       }
/* 1260:     */     }
/* 1261:1497 */     return asiento;
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   public String getClaveDimension(DetalleInterfazContableProceso detalleInterfazContableProceso)
/* 1265:     */   {
/* 1266:1501 */     String clave = String.valueOf(detalleInterfazContableProceso.getCuentaContable().getId());
/* 1267:1502 */     clave = clave + "~" + detalleInterfazContableProceso.getAgrupamiento();
/* 1268:1503 */     if ((detalleInterfazContableProceso.getDimensionContable1() != null) && 
/* 1269:1504 */       (detalleInterfazContableProceso.getCuentaContable().isIndicadorValidarDimension1())) {
/* 1270:1505 */       clave = clave + "~" + detalleInterfazContableProceso.getDimensionContable1().getId();
/* 1271:     */     }
/* 1272:1507 */     if ((detalleInterfazContableProceso.getDimensionContable2() != null) && 
/* 1273:1508 */       (detalleInterfazContableProceso.getCuentaContable().isIndicadorValidarDimension2())) {
/* 1274:1509 */       clave = clave + "~" + detalleInterfazContableProceso.getDimensionContable2().getId();
/* 1275:     */     }
/* 1276:1511 */     if ((detalleInterfazContableProceso.getDimensionContable3() != null) && 
/* 1277:1512 */       (detalleInterfazContableProceso.getCuentaContable().isIndicadorValidarDimension3())) {
/* 1278:1513 */       clave = clave + "~" + detalleInterfazContableProceso.getDimensionContable3().getId();
/* 1279:     */     }
/* 1280:1515 */     if ((detalleInterfazContableProceso.getDimensionContable4() != null) && 
/* 1281:1516 */       (detalleInterfazContableProceso.getCuentaContable().isIndicadorValidarDimension4())) {
/* 1282:1517 */       clave = clave + "~" + detalleInterfazContableProceso.getDimensionContable4().getId();
/* 1283:     */     }
/* 1284:1519 */     if ((detalleInterfazContableProceso.getDimensionContable5() != null) && 
/* 1285:1520 */       (detalleInterfazContableProceso.getCuentaContable().isIndicadorValidarDimension5())) {
/* 1286:1521 */       clave = clave + "~" + detalleInterfazContableProceso.getDimensionContable5().getId();
/* 1287:     */     }
/* 1288:1524 */     return clave;
/* 1289:     */   }
/* 1290:     */   
/* 1291:     */   public Subempresa validarAgenCode(int idOrganizacion, String codeAgent)
/* 1292:     */     throws AS2Exception
/* 1293:     */   {
/* 1294:1529 */     Subempresa subempresa = this.subempresaDao.buscarSubempresaPorCodigo(idOrganizacion, codeAgent);
/* 1295:1531 */     if (subempresa == null) {
/* 1296:1532 */       throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioInterfazContableProcesoImpl.NO_EXISTE_CODE_AGENT", new String[] { codeAgent });
/* 1297:     */     }
/* 1298:1534 */     return subempresa;
/* 1299:     */   }
/* 1300:     */   
/* 1301:     */   public void validarArchivoCass(int idOrganizacion, String recordId, String cassAreaCode, Integer airlinePrefix, Integer datePeriodStart, Integer datePeriodEnd, Integer dateOfBilling, Integer fileNumber, String currencyCode)
/* 1302:     */     throws AS2Exception
/* 1303:     */   {
/* 1304:1541 */     Cass cass = this.cassDao.obtenerCassUnico(idOrganizacion, recordId, cassAreaCode, airlinePrefix, datePeriodStart, datePeriodEnd, dateOfBilling, fileNumber, currencyCode);
/* 1305:1543 */     if (cass != null) {
/* 1306:1544 */       throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioInterfazContableProcesoImpl.YA_EXISTE_EL_ARCHIVO_CASS_EN_EL_SISTEMA", new String[] { "" });
/* 1307:     */     }
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   public void validarCategoriaEmpresa(Empresa em, List<GuiaAerea> listaCassAWM, List<GuiaAerea> listaCcrDcr)
/* 1311:     */     throws AS2Exception
/* 1312:     */   {
/* 1313:1551 */     if (em.getProveedor().getCategoriaRetencion() == null)
/* 1314:     */     {
/* 1315:1552 */       listaCassAWM.clear();
/* 1316:1553 */       listaCcrDcr.clear();
/* 1317:1554 */       throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioInterfazContableProcesoImpl.CONFIGURAR_CATEGORIA_RETENCION", new String[] { em.getIdentificacion() });
/* 1318:     */     }
/* 1319:     */   }
/* 1320:     */   
/* 1321:     */   public Date formarFechaCass(Integer datePeriodEnd)
/* 1322:     */   {
/* 1323:1561 */     String periodo = datePeriodEnd.toString();
/* 1324:1562 */     int anio = 2000 + Integer.parseInt(periodo.substring(0, 2));
/* 1325:1563 */     int mes = Integer.parseInt(periodo.substring(2, 4));
/* 1326:1564 */     int dia = Integer.parseInt(periodo.substring(4, 6));
/* 1327:1565 */     Calendar c = Calendar.getInstance();
/* 1328:1566 */     c.set(anio, mes - 1, dia);
/* 1329:1567 */     return c.getTime();
/* 1330:     */   }
/* 1331:     */   
/* 1332:     */   public String migrarCass(FileUploadEvent event, Cass cass, Asiento asiento, BigDecimal totalCredito, BigDecimal totalDebito, List<GuiaAerea> listaCassAWM, List<GuiaAerea> listaCcrDcr, List<GuiaAerea> listaGuiaAereaAgrupada, int idOrganizacion)
/* 1333:     */     throws IOException, AS2Exception, ExcepcionAS2
/* 1334:     */   {
/* 1335:1575 */     HashMap<String, String> filters = new HashMap();
/* 1336:1576 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 1337:     */     
/* 1338:     */ 
/* 1339:     */ 
/* 1340:1580 */     String uploadDir = ParametrosSistema.getAS2_HOME(1) + File.separator + "documentos" + File.separator + "";
/* 1341:     */     
/* 1342:1582 */     File directorio = new File(uploadDir);
/* 1343:1583 */     String fileName = event.getFile().getFileName();
/* 1344:     */     
/* 1345:1585 */     File file = new File(uploadDir + fileName);
/* 1346:1587 */     if (!directorio.exists()) {
/* 1347:1588 */       directorio.mkdirs();
/* 1348:     */     }
/* 1349:1590 */     InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 1350:     */     
/* 1351:1592 */     FileOutputStream output = new FileOutputStream(file);
/* 1352:1593 */     while (input.available() != 0) {
/* 1353:1594 */       output.write(input.read());
/* 1354:     */     }
/* 1355:1597 */     FileReader f = new FileReader(file.getPath());
/* 1356:1598 */     BufferedReader b = new BufferedReader(f);
/* 1357:     */     
/* 1358:1600 */     BigDecimal totalWeightChargePPAWMPrepaid = BigDecimal.ZERO;
/* 1359:1601 */     BigDecimal totalChargesDueCarrierPPPAWMPrepaid = BigDecimal.ZERO;
/* 1360:     */     
/* 1361:     */ 
/* 1362:     */ 
/* 1363:1605 */     BigDecimal totalGuiasCollectDeX = BigDecimal.ZERO;
/* 1364:     */     String cadena;
/* 1365:1607 */     while ((cadena = b.readLine()) != null)
/* 1366:     */     {
/* 1367:1609 */       if (cadena.substring(0, 3).equals("AAA"))
/* 1368:     */       {
/* 1369:1610 */         cass.setRecordId(cadena.substring(0, 3));
/* 1370:1611 */         cass.setCassAreaCode(cadena.substring(3, 5));
/* 1371:1612 */         cass.setAirlinePrefix(Integer.valueOf(cadena.substring(5, 8)));
/* 1372:1613 */         cass.setDatePeriodStart(Integer.valueOf(cadena.substring(8, 14)));
/* 1373:1614 */         cass.setDatePeriodEnd(Integer.valueOf(cadena.substring(14, 20)));
/* 1374:1615 */         cass.setDateOfBilling(Integer.valueOf(cadena.substring(20, 26)));
/* 1375:1616 */         cass.setFileNumber(Integer.valueOf(cadena.substring(26, 28)));
/* 1376:1617 */         cass.setCurrencyCode(cadena.substring(28, 31));
/* 1377:1618 */         cass.setBranchOfficeIndicator("");
/* 1378:1619 */         cass.setFiller("");
/* 1379:1620 */         cass.setReservedSpace("");
/* 1380:     */       }
/* 1381:1627 */       if (cadena.substring(0, 3).equals("AWM"))
/* 1382:     */       {
/* 1383:1628 */         GuiaAerea awm = new GuiaAerea();
/* 1384:1629 */         awm.setRecordId(cadena.substring(0, 3));
/* 1385:1630 */         awm.setBranchOfficeIndicator(cadena.substring(3, 4));
/* 1386:1631 */         awm.setVatIndicator(cadena.substring(4, 5));
/* 1387:1632 */         awm.setAirlinePrefix(Integer.valueOf(cadena.substring(5, 8)));
/* 1388:1633 */         awm.setAwbSerialNumber(cadena.substring(8, 16));
/* 1389:1634 */         awm.setAwbModularNumberCheck(cadena.substring(16, 17));
/* 1390:1635 */         awm.setFiller(cadena.substring(17, 18));
/* 1391:1636 */         awm.setOrigin(cadena.substring(18, 21));
/* 1392:1637 */         awm.setAgentCode(cadena.substring(21, 32));
/* 1393:     */         
/* 1394:1639 */         validarAgenCode(AppUtil.getOrganizacion().getIdOrganizacion(), awm.getAgentCode());
/* 1395:     */         
/* 1396:1641 */         awm.setAwbUseIndicator(cadena.substring(32, 33));
/* 1397:1642 */         awm.setLateIndicator(Integer.valueOf(cadena.substring(33, 34).trim().equals("") ? "0" : cadena.substring(33, 34)));
/* 1398:1643 */         awm.setFiller2(cadena.substring(34, 36));
/* 1399:1644 */         awm.setDestination(cadena.substring(36, 39));
/* 1400:1645 */         awm.setDateAwbExecution(Integer.valueOf(cadena.substring(39, 45)));
/* 1401:1646 */         awm.setWeightGross(Integer.valueOf(cadena.substring(45, 52)));
/* 1402:1647 */         awm.setWeightIndicator(cadena.substring(52, 53));
/* 1403:1648 */         awm.setCurrencyCode(cadena.substring(53, 56));
/* 1404:1649 */         awm.setWeightChargePp(new BigDecimal(cadena.substring(56, 68)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1405:1650 */         awm.setValuationChargePp(Integer.valueOf(cadena.substring(68, 80)));
/* 1406:1651 */         awm.setChargesDueCarrierPp(new BigDecimal(cadena.substring(80, 92)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1407:1652 */         awm.setChargesDueAgentPp(Integer.valueOf(cadena.substring(92, 104)));
/* 1408:1653 */         awm.setWeightChargeCc(new BigDecimal(cadena.substring(104, 116)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1409:1654 */         awm.setValuationChargeCc(Integer.valueOf(cadena.substring(116, 128)));
/* 1410:1655 */         awm.setOtherChargesDueCarrierCc(new BigDecimal(cadena.substring(128, 140)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1411:1656 */         awm.setOtherChargesDueAgentCc(new BigDecimal(cadena.substring(140, 152)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1412:1657 */         awm.setCommissionPercentage(Integer.valueOf(cadena.substring(152, 156)));
/* 1413:1658 */         awm.setCommission(new BigDecimal(cadena.substring(156, 168)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1414:1659 */         awm.setFiller3(cadena.substring(168, 169));
/* 1415:1660 */         awm.setTaxDueAirlineIndicator(cadena.substring(168, 169));
/* 1416:1661 */         awm.setAgentsReferenceData(cadena.substring(169, 183));
/* 1417:1662 */         awm.setFiller4(cadena.substring(183, 193));
/* 1418:1663 */         awm.setDateAwbAcceptance(Integer.valueOf(cadena.substring(193, 199)));
/* 1419:1664 */         awm.setRateOfExchange(new BigDecimal(cadena.substring(199, 210)).setScale(6));
/* 1420:1665 */         awm.setDiscount(new BigDecimal(cadena.substring(210, 222)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1421:1666 */         awm.setTaxDueAirline(Integer.valueOf(cadena.substring(222, 230)));
/* 1422:1667 */         awm.setTaxDueAgent(Integer.valueOf(cadena.substring(230, 238)));
/* 1423:1668 */         awm.setReservedSpace("");
/* 1424:1669 */         awm.setDay(Integer.valueOf("0"));
/* 1425:1670 */         awm.setSequenceNumber(Integer.valueOf("0"));
/* 1426:1671 */         awm.setReportingIndicator("");
/* 1427:1672 */         awm.setNoraSalesPeriod(Integer.valueOf("0"));
/* 1428:1673 */         awm.setDiscountIndicato("");
/* 1429:     */         
/* 1430:1675 */         totalWeightChargePPAWMPrepaid = totalWeightChargePPAWMPrepaid.add(awm.getWeightChargePp().add(awm.getWeightChargeCc()));
/* 1431:     */         
/* 1432:1677 */         totalChargesDueCarrierPPPAWMPrepaid = totalChargesDueCarrierPPPAWMPrepaid.add(awm.getChargesDueCarrierPp().add(awm.getOtherChargesDueCarrierCc()));
/* 1433:     */         
/* 1434:     */ 
/* 1435:1680 */         totalGuiasCollectDeX = totalGuiasCollectDeX.add(awm.getWeightChargeCc().add(awm.getOtherChargesDueCarrierCc()).add(awm.getOtherChargesDueAgentCc()));
/* 1436:     */         
/* 1437:1682 */         GuiaAerea auxACDCD = (GuiaAerea)getHmAgentCode().get(awm.getAgentCode());
/* 1438:1684 */         if (auxACDCD != null)
/* 1439:     */         {
/* 1440:1685 */           awm.setTotalWgtCharge(auxACDCD.getTotalWgtCharge().add(awm.getWeightChargePp()));
/* 1441:1686 */           awm.setTotalDueCarrier(auxACDCD.getTotalDueCarrier().add(awm.getChargesDueCarrierPp()));
/* 1442:1687 */           awm.setTotalWgtChargeCollect(auxACDCD.getTotalWgtChargeCollect().add(awm.getWeightChargeCc()));
/* 1443:1688 */           awm.setTotalOtherChargesDueAgentCc(auxACDCD.getTotalOtherChargesDueAgentCc().add(awm.getOtherChargesDueAgentCc()));
/* 1444:1689 */           awm.setTotalDiscount(auxACDCD.getTotalDiscount().add(awm.getDiscount()));
/* 1445:1690 */           awm.setTotalCommission(auxACDCD.getTotalCommission().add(awm.getCommission()));
/* 1446:1691 */           getHmAgentCode().put(awm.getAgentCode(), awm);
/* 1447:     */         }
/* 1448:     */         else
/* 1449:     */         {
/* 1450:1694 */           awm.setTotalWgtCharge(awm.getWeightChargePp());
/* 1451:     */           
/* 1452:1696 */           awm.setTotalDueCarrier(awm.getChargesDueCarrierPp());
/* 1453:1697 */           awm.setTotalWgtChargeCollect(awm.getWeightChargeCc());
/* 1454:1698 */           awm.setTotalOtherChargesDueAgentCc(awm.getOtherChargesDueAgentCc());
/* 1455:1699 */           awm.setTotalDiscount(awm.getDiscount());
/* 1456:1700 */           awm.setTotalCommission(awm.getCommission());
/* 1457:1701 */           getHmAgentCode().put(awm.getAgentCode(), awm);
/* 1458:     */         }
/* 1459:1704 */         awm.setIdOrganizacion(cass.getIdOrganizacion());
/* 1460:1705 */         awm.setIdSucursal(cass.getIdSucursal());
/* 1461:1706 */         awm.setCass(cass);
/* 1462:1707 */         getHmAgentCode().put(awm.getAgentCode(), awm);
/* 1463:1708 */         listaCassAWM.add(awm);
/* 1464:1709 */         cass.getListaGuiasAereas().add(awm);
/* 1465:     */       }
/* 1466:1712 */       if ((cadena.substring(0, 3).equals("DCR")) || (cadena.substring(0, 3).equals("DCO")))
/* 1467:     */       {
/* 1468:1713 */         GuiaAerea ccrDcr = new GuiaAerea();
/* 1469:1714 */         ccrDcr.setRecordId(cadena.substring(0, 3));
/* 1470:1715 */         ccrDcr.setBranchOfficeIndicator(cadena.substring(3, 4));
/* 1471:1716 */         ccrDcr.setVatIndicator(cadena.substring(4, 5));
/* 1472:1717 */         ccrDcr.setAirlinePrefix(Integer.valueOf(cadena.substring(5, 8)));
/* 1473:1718 */         ccrDcr.setAwbSerialNumber(cadena.substring(8, 16));
/* 1474:1719 */         ccrDcr.setAwbNumberModularCheck(cadena.substring(16, 17));
/* 1475:1720 */         ccrDcr.setFiller(cadena.substring(17, 18));
/* 1476:1721 */         ccrDcr.setOrigin(cadena.substring(18, 21));
/* 1477:1722 */         ccrDcr.setAgentCode(cadena.substring(21, 32));
/* 1478:1723 */         ccrDcr.setCcaDcmNumber(cadena.substring(32, 38));
/* 1479:1724 */         ccrDcr.setCurrencyCode(cadena.substring(38, 41));
/* 1480:1725 */         ccrDcr.setRateOfExchange(new BigDecimal(cadena.substring(41, 52)).setScale(6));
/* 1481:1726 */         ccrDcr.setDateAwbExecution(Integer.valueOf(cadena.substring(52, 58)));
/* 1482:1727 */         ccrDcr.setPpCcIndicator1(cadena.substring(58, 59));
/* 1483:1728 */         if (cadena.substring(0, 3).equals("DCO"))
/* 1484:     */         {
/* 1485:1729 */           ccrDcr.setWeightCharge(new BigDecimal(cadena.substring(59, 71)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP).negate());
/* 1486:1730 */           ccrDcr.setChargesDueAgent(new BigDecimal(cadena
/* 1487:1731 */             .substring(98, 110)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP).negate());
/* 1488:1732 */           ccrDcr.setChargesDueCarrier(new BigDecimal(cadena
/* 1489:1733 */             .substring(111, 123)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP).negate());
/* 1490:1734 */           ccrDcr.setDiscount(new BigDecimal(cadena.substring(159, 171)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP).negate());
/* 1491:1735 */           ccrDcr.setCommission(new BigDecimal(cadena.substring(135, 147)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP).negate());
/* 1492:     */         }
/* 1493:     */         else
/* 1494:     */         {
/* 1495:1737 */           ccrDcr.setWeightCharge(new BigDecimal(cadena.substring(59, 71)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1496:1738 */           ccrDcr.setChargesDueAgent(new BigDecimal(cadena.substring(98, 110)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1497:1739 */           ccrDcr.setChargesDueCarrier(new BigDecimal(cadena.substring(111, 123)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1498:1740 */           ccrDcr.setDiscount(new BigDecimal(cadena.substring(159, 171)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1499:1741 */           ccrDcr.setCommission(new BigDecimal(cadena.substring(135, 147)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1500:     */         }
/* 1501:1744 */         ccrDcr.setPpCcIndicator2(cadena.substring(71, 72));
/* 1502:1745 */         ccrDcr.setValuationCharge(Integer.valueOf(cadena.substring(72, 84)));
/* 1503:1746 */         ccrDcr.setPpCcIndicator3(cadena.substring(84, 85));
/* 1504:1747 */         ccrDcr.setTaxes(Integer.valueOf(cadena.substring(85, 97)));
/* 1505:1748 */         ccrDcr.setPpCcIndicator4(cadena.substring(97, 98));
/* 1506:1749 */         ccrDcr.setPpCcIndicator5(cadena.substring(110, 111));
/* 1507:     */         
/* 1508:1751 */         ccrDcr.setVatOnAwbCharges(Integer.valueOf(cadena.substring(123, 135)));
/* 1509:     */         
/* 1510:1753 */         ccrDcr.setVatOnCommission(Integer.valueOf(cadena.substring(147, 159)));
/* 1511:     */         
/* 1512:1755 */         ccrDcr.setDiscountIndicator(Integer.valueOf(cadena.substring(171, 172).trim().equals("") ? "0" : cadena.substring(171, 172)));
/* 1513:1756 */         ccrDcr.setWeightIndicator(cadena.substring(172, 173));
/* 1514:1757 */         ccrDcr.setWeight(Integer.valueOf(cadena.substring(173, 180)));
/* 1515:1758 */         ccrDcr.setDestination(cadena.substring(180, 183));
/* 1516:1759 */         ccrDcr.setReservedSpace("");
/* 1517:1760 */         ccrDcr.setReasonForAdjustment("");
/* 1518:1761 */         if (ccrDcr.getPpCcIndicator1().equals("P"))
/* 1519:     */         {
/* 1520:1762 */           totalWeightChargePPAWMPrepaid = totalWeightChargePPAWMPrepaid.add(ccrDcr.getWeightCharge());
/* 1521:1763 */           totalChargesDueCarrierPPPAWMPrepaid = totalChargesDueCarrierPPPAWMPrepaid.add(ccrDcr.getChargesDueCarrier());
/* 1522:     */         }
/* 1523:1765 */         if (ccrDcr.getPpCcIndicator1().equals("C"))
/* 1524:     */         {
/* 1525:1766 */           totalWeightChargePPAWMPrepaid = totalWeightChargePPAWMPrepaid.add(ccrDcr.getWeightCharge());
/* 1526:1767 */           totalChargesDueCarrierPPPAWMPrepaid = totalChargesDueCarrierPPPAWMPrepaid.add(ccrDcr.getChargesDueCarrier());
/* 1527:     */           
/* 1528:1769 */           totalGuiasCollectDeX = totalGuiasCollectDeX.add(ccrDcr.getWeightCharge().add(ccrDcr.getChargesDueCarrier()).add(ccrDcr.getChargesDueAgent()));
/* 1529:     */         }
/* 1530:1772 */         GuiaAerea auxACDCD = (GuiaAerea)getHmAgentCode().get(ccrDcr.getAgentCode());
/* 1531:1773 */         if (auxACDCD != null)
/* 1532:     */         {
/* 1533:1775 */           if (ccrDcr.getPpCcIndicator1().equals("P"))
/* 1534:     */           {
/* 1535:1777 */             if (auxACDCD != null)
/* 1536:     */             {
/* 1537:1778 */               ccrDcr.setTotalWgtChargeCollect(auxACDCD.getTotalWgtChargeCollect());
/* 1538:1779 */               ccrDcr.setTotalOtherChargesDueAgentCc(auxACDCD.getTotalOtherChargesDueAgentCc());
/* 1539:     */             }
/* 1540:1781 */             ccrDcr.setTotalWgtCharge(auxACDCD.getTotalWgtCharge().add(ccrDcr.getWeightCharge()));
/* 1541:     */             
/* 1542:1783 */             ccrDcr.setTotalDueCarrier(auxACDCD.getTotalDueCarrier().add(ccrDcr.getChargesDueCarrier()));
/* 1543:1784 */             getHmAgentCode().put(ccrDcr.getAgentCode(), ccrDcr);
/* 1544:     */           }
/* 1545:1786 */           if (ccrDcr.getPpCcIndicator1().equals("C"))
/* 1546:     */           {
/* 1547:1788 */             auxACDCD = (GuiaAerea)getHmAgentCode().get(ccrDcr.getAgentCode());
/* 1548:1789 */             if (auxACDCD != null)
/* 1549:     */             {
/* 1550:1790 */               ccrDcr.setTotalWgtCharge(auxACDCD.getTotalWgtCharge());
/* 1551:1791 */               ccrDcr.setTotalDueCarrier(auxACDCD.getTotalDueCarrier());
/* 1552:     */             }
/* 1553:1793 */             ccrDcr.setTotalWgtChargeCollect(auxACDCD.getTotalWgtChargeCollect().add(ccrDcr.getWeightCharge()));
/* 1554:1794 */             ccrDcr.setTotalOtherChargesDueAgentCc(auxACDCD.getTotalOtherChargesDueAgentCc().add(ccrDcr.getChargesDueAgent()));
/* 1555:1795 */             getHmAgentCode().put(ccrDcr.getAgentCode(), ccrDcr);
/* 1556:     */           }
/* 1557:1799 */           ccrDcr.setTotalDiscount(auxACDCD.getTotalDiscount().add(ccrDcr.getDiscount()));
/* 1558:1800 */           ccrDcr.setTotalCommission(auxACDCD.getTotalCommission().add(ccrDcr.getCommission()));
/* 1559:     */           
/* 1560:1802 */           getHmAgentCode().put(ccrDcr.getAgentCode(), ccrDcr);
/* 1561:     */         }
/* 1562:     */         else
/* 1563:     */         {
/* 1564:1805 */           if (ccrDcr.getPpCcIndicator1().equals("P"))
/* 1565:     */           {
/* 1566:1806 */             ccrDcr.setTotalWgtCharge(ccrDcr.getWeightCharge());
/* 1567:1807 */             ccrDcr.setTotalDueCarrier(ccrDcr.getChargesDueCarrier());
/* 1568:1808 */             ccrDcr.setTotalDiscount(ccrDcr.getDiscount());
/* 1569:1809 */             ccrDcr.setTotalCommission(ccrDcr.getCommission());
/* 1570:1810 */             getHmAgentCode().put(ccrDcr.getAgentCode(), ccrDcr);
/* 1571:     */           }
/* 1572:1812 */           if (ccrDcr.getPpCcIndicator1().equals("C"))
/* 1573:     */           {
/* 1574:1814 */             ccrDcr.setTotalWgtChargeCollect(ccrDcr.getWeightCharge());
/* 1575:1815 */             ccrDcr.setTotalOtherChargesDueAgentCc(ccrDcr.getChargesDueAgent());
/* 1576:1816 */             ccrDcr.setTotalDiscount(ccrDcr.getDiscount());
/* 1577:1817 */             ccrDcr.setTotalCommission(ccrDcr.getCommission());
/* 1578:1818 */             getHmAgentCode().put(ccrDcr.getAgentCode(), ccrDcr);
/* 1579:     */           }
/* 1580:     */         }
/* 1581:1823 */         ccrDcr.setIdOrganizacion(cass.getIdOrganizacion());
/* 1582:1824 */         ccrDcr.setIdSucursal(cass.getIdSucursal());
/* 1583:1825 */         ccrDcr.setCass(cass);
/* 1584:1826 */         getHmAgentCode().put(ccrDcr.getAgentCode(), ccrDcr);
/* 1585:1827 */         listaCcrDcr.add(ccrDcr);
/* 1586:1828 */         cass.getListaGuiasAereas().add(ccrDcr);
/* 1587:     */       }
/* 1588:     */     }
/* 1589:1833 */     BigDecimal amount = BigDecimal.ZERO;
/* 1590:1834 */     BigDecimal discount = BigDecimal.ZERO;
/* 1591:1835 */     BigDecimal commission = BigDecimal.ZERO;
/* 1592:1836 */     BigDecimal ivaRetencion70 = BigDecimal.ZERO;
/* 1593:1837 */     BigDecimal ivaRetencionCe = BigDecimal.ZERO;
/* 1594:1838 */     CuentaContable cuentaContableIVA70 = null;
/* 1595:1839 */     CuentaContable cuentaContableIVA20 = null;
/* 1596:     */     
/* 1597:     */ 
/* 1598:1842 */     BigDecimal porcentaje = this.servicioImpuesto.getPorcentajeIVA(cass.getIdOrganizacion(), formarFechaCass(cass.getDatePeriodEnd())).divide(new BigDecimal(100));
/* 1599:1843 */     System.out.println("ORGANZIACION: " + cass.getIdOrganizacion());
/* 1600:1844 */     System.out.println("FECHA: " + formarFechaCass(cass.getDatePeriodEnd()));
/* 1601:1845 */     System.out.println("PORCENTAJE: " + porcentaje);
/* 1602:1847 */     for (GuiaAerea a : this.hmAgentCode.values())
/* 1603:     */     {
/* 1604:1849 */       BigDecimal ivaRet7020 = BigDecimal.ZERO;
/* 1605:1850 */       BigDecimal ivaAgt3080 = BigDecimal.ZERO;
/* 1606:     */       
/* 1607:1852 */       Subempresa sub = validarAgenCode(idOrganizacion, a.getAgentCode());
/* 1608:1853 */       Empresa em = this.servicioEmpresa.obtenerDatosProveedor(sub.getEmpresaPadre().getIdEmpresa());
/* 1609:1854 */       validarCategoriaEmpresa(em, listaCassAWM, listaCcrDcr);
/* 1610:1855 */       CategoriaRetencion cr = this.servicioCategoriaRetencion.cargarDetalle(em.getProveedor().getCategoriaRetencion().getIdCategoriaRetencion());
/* 1611:1856 */       for (DetalleCategoriaRetencion dcr : cr.getListaDetalleCategoriaRetencion()) {
/* 1612:1857 */         if (dcr.getConceptoRetencionSRI().getTipoConceptoRetencion().equals(TipoConceptoRetencion.IVA))
/* 1613:     */         {
/* 1614:1859 */           if (dcr.getConceptoRetencionSRI().getPorcentaje().compareTo(new BigDecimal(70.0D)) == 0) {
/* 1615:1860 */             cuentaContableIVA70 = dcr.getConceptoRetencionSRI().getCuentaContable();
/* 1616:     */           } else {
/* 1617:1862 */             cuentaContableIVA20 = dcr.getConceptoRetencionSRI().getCuentaContable();
/* 1618:     */           }
/* 1619:1865 */           ivaRet7020 = dcr.getConceptoRetencionSRI().getPorcentaje().divide(new BigDecimal(100), 20, RoundingMode.HALF_UP);
/* 1620:     */           
/* 1621:1867 */           ivaAgt3080 = BigDecimal.ONE.subtract(dcr.getConceptoRetencionSRI().getPorcentaje().divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/* 1622:     */         }
/* 1623:     */       }
/* 1624:1872 */       a.setTotalTaxCom(a.getTotalCommission().add(a.getTotalDiscount()).multiply(porcentaje).setScale(6, RoundingMode.HALF_UP));
/* 1625:1873 */       a.setTotalIvaAgt3080(a.getTotalCommission().add(a.getTotalDiscount()).multiply(porcentaje).multiply(ivaAgt3080).setScale(6, RoundingMode.HALF_UP));
/* 1626:1876 */       if (ivaRet7020.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal(70.0D)) == 0) {
/* 1627:1877 */         ivaRetencion70 = ivaRetencion70.add(a.getTotalCommission().add(a.getTotalDiscount()).multiply(porcentaje).multiply(ivaRet7020)
/* 1628:1878 */           .setScale(6, RoundingMode.HALF_UP));
/* 1629:     */       } else {
/* 1630:1880 */         ivaRetencionCe = ivaRetencionCe.add(a.getTotalCommission().add(a.getTotalDiscount()).multiply(porcentaje).multiply(ivaRet7020)
/* 1631:1881 */           .setScale(6, RoundingMode.HALF_UP));
/* 1632:     */       }
/* 1633:1884 */       a.setTotalIvaRet7020(a.getTotalCommission().add(a.getTotalDiscount()).multiply(porcentaje).multiply(ivaRet7020).setScale(6, RoundingMode.HALF_UP));
/* 1634:     */       
/* 1635:1886 */       a.setTotalIsrl(a.getTotalCommission().add(a.getTotalDiscount()).multiply(new BigDecimal(0.02D)).setScale(6, RoundingMode.HALF_UP));
/* 1636:     */       
/* 1637:1888 */       a.setTotalAmount(a.getTotalWgtCharge().add(a.getTotalDueCarrier()).add(a.getTotalIsrl())
/* 1638:1889 */         .subtract(a.getTotalOtherChargesDueAgentCc().add(a.getTotalCommission()).add(a.getTotalIvaAgt3080()).add(a.getTotalDiscount())));
/* 1639:     */       
/* 1640:     */ 
/* 1641:1892 */       listaGuiaAereaAgrupada.add(a);
/* 1642:     */     }
/* 1643:1896 */     for (GuiaAerea a : this.hmAgentCode.values())
/* 1644:     */     {
/* 1645:1897 */       amount = amount.add(a.getTotalAmount());
/* 1646:1898 */       discount = discount.add(a.getTotalDiscount());
/* 1647:1899 */       commission = commission.add(a.getTotalCommission());
/* 1648:     */     }
/* 1649:1903 */     ConfiguracionCass config = (ConfiguracionCass)this.servicioConfiguracionCass.obtenerListaCombo(ConfiguracionCass.class, "nombre", true, filters).get(0);
/* 1650:1904 */     List<String> listaCampos = new ArrayList();
/* 1651:1905 */     listaCampos.add("ventasCass");
/* 1652:1906 */     listaCampos.add("manejoCargaTarifaCero");
/* 1653:1907 */     listaCampos.add("cuentaPorCobrarCass");
/* 1654:1908 */     listaCampos.add("discount");
/* 1655:1909 */     listaCampos.add("commissionAgency");
/* 1656:1910 */     listaCampos.add("guiaCollectX");
/* 1657:1911 */     listaCampos.add("creditoTributarioCommision");
/* 1658:1912 */     listaCampos.add("ivaRetenido");
/* 1659:1913 */     listaCampos.add("retencionFleteAgenciaViajeCarga");
/* 1660:1914 */     config = (ConfiguracionCass)this.servicioConfiguracionCass.cargarDetalle(ConfiguracionCass.class, config.getId(), listaCampos);
/* 1661:     */     
/* 1662:1916 */     asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1663:1917 */     asiento.setSucursal(AppUtil.getSucursal());
/* 1664:1918 */     asiento.setFecha(formarFechaCass(cass.getDatePeriodEnd()));
/* 1665:1919 */     asiento.setTipoAsiento(
/* 1666:     */     
/* 1667:1921 */       ((Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.CARGA_ARCHIVO, AppUtil.getOrganizacion().getIdOrganizacion()).get(0)).getTipoAsiento());
/* 1668:1922 */     asiento.setDocumentoOrigen(
/* 1669:1923 */       (Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.CARGA_ARCHIVO, AppUtil.getOrganizacion().getIdOrganizacion()).get(0));
/* 1670:1924 */     asiento.setNumero("");
/* 1671:1925 */     asiento.setEstado(Estado.ELABORADO);
/* 1672:1926 */     asiento.setIndicadorAutomatico(true);
/* 1673:1927 */     asiento.setConcepto("cass");
/* 1674:1929 */     for (int i = 1; i <= 10; i++)
/* 1675:     */     {
/* 1676:1930 */       if (i == 1) {
/* 1677:1931 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(config.getVentasCass().getIdCuentaContable()), "Ventas Cass", totalWeightChargePPAWMPrepaid
/* 1678:1932 */           .setScale(2, RoundingMode.HALF_UP), false);
/* 1679:     */       }
/* 1680:1934 */       if (i == 2) {
/* 1681:1935 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(config.getManejoCargaTarifaCero().getIdCuentaContable()), "Manejo Carga Tarifa Cero", totalChargesDueCarrierPPPAWMPrepaid
/* 1682:1936 */           .setScale(2, RoundingMode.HALF_UP), false);
/* 1683:     */       }
/* 1684:1938 */       if (i == 3) {
/* 1685:1939 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(config.getCuentaPorCobrarCass().getIdCuentaContable()), "Cuenta Por Cobrar Cass", amount
/* 1686:1940 */           .setScale(2, RoundingMode.HALF_UP), true);
/* 1687:     */       }
/* 1688:1942 */       if (i == 4)
/* 1689:     */       {
/* 1690:1943 */         BigDecimal valor = BigDecimal.ZERO;
/* 1691:1944 */         if (commission.compareTo(BigDecimal.ZERO) < 0)
/* 1692:     */         {
/* 1693:1945 */           valor = discount.add(commission);
/* 1694:1946 */           crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(config.getDiscount().getIdCuentaContable()), "Discount & Comisión Agencia", valor
/* 1695:1947 */             .setScale(2, RoundingMode.HALF_UP), true);
/* 1696:     */         }
/* 1697:     */         else
/* 1698:     */         {
/* 1699:1949 */           crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(config.getDiscount().getIdCuentaContable()), "Discount & Comisión Agencia", discount
/* 1700:1950 */             .setScale(2, RoundingMode.HALF_UP), true);
/* 1701:     */         }
/* 1702:     */       }
/* 1703:1954 */       if (i == 5) {
/* 1704:1956 */         if (commission.compareTo(BigDecimal.ZERO) > 0) {
/* 1705:1957 */           crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(config.getCommissionAgency().getIdCuentaContable()), "CTO Comisiones Agencias de carga", commission
/* 1706:1958 */             .setScale(2, RoundingMode.HALF_UP), true);
/* 1707:     */         }
/* 1708:     */       }
/* 1709:1962 */       if (i == 6) {
/* 1710:1963 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(config.getGuiaCollectX().getIdCuentaContable()), "Guias Collect de Exportación", totalGuiasCollectDeX
/* 1711:1964 */           .setScale(2, RoundingMode.HALF_UP), true);
/* 1712:     */       }
/* 1713:1966 */       if (i == 7) {
/* 1714:1967 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(config.getCreditoTributarioCommision().getIdCuentaContable()), "Credito Tributario Comisión", commission
/* 1715:1968 */           .add(discount).multiply(porcentaje).setScale(2, RoundingMode.HALF_UP), true);
/* 1716:     */       }
/* 1717:1970 */       if (i == 8) {
/* 1718:1971 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableIVA70.getIdCuentaContable()), "Retención IVA ", ivaRetencion70
/* 1719:1972 */           .setScale(2, RoundingMode.HALF_UP), false);
/* 1720:     */       }
/* 1721:1974 */       if (i == 9) {
/* 1722:1975 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(cuentaContableIVA20.getIdCuentaContable()), "Retención IVA CE", ivaRetencionCe
/* 1723:1976 */           .setScale(2, RoundingMode.HALF_UP), false);
/* 1724:     */       }
/* 1725:1978 */       if (i == 10) {
/* 1726:1979 */         crearDetalleAsiento(asiento, this.servicioCuentaContable.cargarDetalle(config.getRetencionFleteAgenciaViajeCarga().getIdCuentaContable()), "Retención en la Fuente", commission
/* 1727:1980 */           .add(discount).multiply(new BigDecimal(0.02D)).setScale(2, RoundingMode.HALF_UP), false);
/* 1728:     */       }
/* 1729:     */     }
/* 1730:1985 */     cass.setAsiento(asiento);
/* 1731:1986 */     diferenciaAsiento(asiento, this.servicioCuentaContable.cargarDetalle(config.getIvaRetenido().getIdCuentaContable()));
/* 1732:     */     
/* 1733:1988 */     b.close();
/* 1734:     */     
/* 1735:1990 */     return null;
/* 1736:     */   }
/* 1737:     */   
/* 1738:     */   public void diferenciaAsiento(Asiento asiento, CuentaContable diferencia)
/* 1739:     */   {
/* 1740:1995 */     BigDecimal credito = BigDecimal.ZERO;
/* 1741:1996 */     BigDecimal debito = BigDecimal.ZERO;
/* 1742:1998 */     for (DetalleAsiento da : asiento.getListaDetalleAsiento())
/* 1743:     */     {
/* 1744:1999 */       if ((da.getHaber().compareTo(BigDecimal.ZERO) == 0) && (da.getDebe().compareTo(BigDecimal.ZERO) == 0)) {
/* 1745:2000 */         da.setEliminado(true);
/* 1746:     */       }
/* 1747:2002 */       credito = credito.add(da.getHaber());
/* 1748:2003 */       debito = debito.add(da.getDebe());
/* 1749:     */     }
/* 1750:2006 */     if (credito.compareTo(debito) > 0) {
/* 1751:2007 */       crearDetalleAsiento(asiento, diferencia, "Diferencia", credito.subtract(debito), true);
/* 1752:     */     }
/* 1753:2009 */     if (credito.compareTo(debito) < 0) {
/* 1754:2010 */       crearDetalleAsiento(asiento, diferencia, "Diferencia", debito.subtract(credito), false);
/* 1755:     */     }
/* 1756:     */   }
/* 1757:     */   
/* 1758:     */   public void crearDetalleAsiento(Asiento asiento, CuentaContable cuentaContable, String descripcion, BigDecimal valor, boolean debe)
/* 1759:     */   {
/* 1760:2016 */     DetalleAsiento detalleAsiento = new DetalleAsiento();
/* 1761:2017 */     detalleAsiento.setAsiento(asiento);
/* 1762:2018 */     detalleAsiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1763:2019 */     detalleAsiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 1764:2020 */     detalleAsiento.setCuentaContable(cuentaContable);
/* 1765:2021 */     detalleAsiento.setDescripcion(descripcion);
/* 1766:2023 */     if (valor.compareTo(BigDecimal.ZERO) < 0)
/* 1767:     */     {
/* 1768:2024 */       debe = !debe;
/* 1769:2025 */       valor = valor.negate();
/* 1770:     */     }
/* 1771:2028 */     if (debe)
/* 1772:     */     {
/* 1773:2029 */       detalleAsiento.setDebe(valor);
/* 1774:2030 */       detalleAsiento.setHaber(BigDecimal.ZERO);
/* 1775:     */     }
/* 1776:     */     else
/* 1777:     */     {
/* 1778:2032 */       detalleAsiento.setHaber(valor);
/* 1779:2033 */       detalleAsiento.setDebe(BigDecimal.ZERO);
/* 1780:     */     }
/* 1781:2035 */     asiento.getListaDetalleAsiento().add(detalleAsiento);
/* 1782:     */   }
/* 1783:     */   
/* 1784:     */   public HashMap<String, GuiaAerea> getHmAgentCode()
/* 1785:     */   {
/* 1786:2040 */     return this.hmAgentCode;
/* 1787:     */   }
/* 1788:     */   
/* 1789:     */   public void setHmAgentCode(HashMap<String, GuiaAerea> hmAgentCode)
/* 1790:     */   {
/* 1791:2044 */     this.hmAgentCode = hmAgentCode;
/* 1792:     */   }
/* 1793:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioInterfazContableProcesoImpl
 * JD-Core Version:    0.7.0.1
 */