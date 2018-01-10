/*    1:     */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.controller.TipoCuentaBancariaBean;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoCuentaBancaria;
/*    6:     */ import com.asinfo.as2.controller.LanguageController;
/*    7:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   10:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   11:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   12:     */ import com.asinfo.as2.entities.Banco;
/*   13:     */ import com.asinfo.as2.entities.BancoAcreedor;
/*   14:     */ import com.asinfo.as2.entities.ConceptoContable;
/*   15:     */ import com.asinfo.as2.entities.ConciliacionBancaria;
/*   16:     */ import com.asinfo.as2.entities.ConfiguracionConciliacionBancaria;
/*   17:     */ import com.asinfo.as2.entities.CuentaBancaria;
/*   18:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   19:     */ import com.asinfo.as2.entities.CuentaContable;
/*   20:     */ import com.asinfo.as2.entities.CuentaContableCruceCuentaBancariaOrganizacion;
/*   21:     */ import com.asinfo.as2.entities.Documento;
/*   22:     */ import com.asinfo.as2.entities.Empresa;
/*   23:     */ import com.asinfo.as2.entities.FormaPago;
/*   24:     */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*   25:     */ import com.asinfo.as2.entities.MovimientoBancario;
/*   26:     */ import com.asinfo.as2.entities.Organizacion;
/*   27:     */ import com.asinfo.as2.entities.Pais;
/*   28:     */ import com.asinfo.as2.entities.Producto;
/*   29:     */ import com.asinfo.as2.entities.Secuencia;
/*   30:     */ import com.asinfo.as2.entities.Sucursal;
/*   31:     */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*   32:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   33:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   34:     */ import com.asinfo.as2.enumeraciones.TipoAccesoContable;
/*   35:     */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*   36:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   37:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   38:     */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ConceptoContableBean;
/*   39:     */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*   40:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioConceptoContable;
/*   41:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   42:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioConciliacionBancaria;
/*   43:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   44:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioMovimientoBancario;
/*   45:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   46:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   47:     */ import com.asinfo.as2.util.AppUtil;
/*   48:     */ import java.util.ArrayList;
/*   49:     */ import java.util.Calendar;
/*   50:     */ import java.util.Collection;
/*   51:     */ import java.util.HashMap;
/*   52:     */ import java.util.List;
/*   53:     */ import java.util.Map;
/*   54:     */ import javax.annotation.PostConstruct;
/*   55:     */ import javax.ejb.EJB;
/*   56:     */ import javax.faces.bean.ManagedBean;
/*   57:     */ import javax.faces.bean.ManagedProperty;
/*   58:     */ import javax.faces.bean.ViewScoped;
/*   59:     */ import javax.faces.component.html.HtmlSelectOneMenu;
/*   60:     */ import javax.faces.context.FacesContext;
/*   61:     */ import javax.faces.context.PartialViewContext;
/*   62:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   63:     */ import javax.faces.model.SelectItem;
/*   64:     */ import org.apache.log4j.Logger;
/*   65:     */ import org.primefaces.component.datatable.DataTable;
/*   66:     */ import org.primefaces.context.RequestContext;
/*   67:     */ import org.primefaces.event.SelectEvent;
/*   68:     */ import org.primefaces.model.LazyDataModel;
/*   69:     */ import org.primefaces.model.SortOrder;
/*   70:     */ 
/*   71:     */ @ManagedBean
/*   72:     */ @ViewScoped
/*   73:     */ public class CuentaBancariaOrganizacionBean
/*   74:     */   extends PageControllerAS2
/*   75:     */ {
/*   76:     */   private static final long serialVersionUID = 1L;
/*   77:     */   @EJB
/*   78:     */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*   79:     */   @EJB
/*   80:     */   private ServicioSecuencia servicioSecuencia;
/*   81:     */   @EJB
/*   82:     */   private ServicioDocumento servicioDocumento;
/*   83:     */   @EJB
/*   84:     */   private ServicioMovimientoBancario servicioMovimientoBancario;
/*   85:     */   @EJB
/*   86:     */   private ServicioConciliacionBancaria servicioConciliacionBancaria;
/*   87:     */   @EJB
/*   88:     */   private ServicioConceptoContable servicioConceptoContable;
/*   89:     */   @EJB
/*   90:     */   private ServicioPais servicioPais;
/*   91:     */   @EJB
/*   92:     */   private ServicioFormaPago servicioFormaPago;
/*   93:     */   @EJB
/*   94:     */   private ServicioGenerico<Banco> servicioBanco;
/*   95:     */   @EJB
/*   96:     */   private ServicioTipoCuentaBancaria servicioTipoCuentaBancaria;
/*   97:     */   @EJB
/*   98:     */   private ServicioEmpresa servicioEmpresa;
/*   99:     */   @EJB
/*  100:     */   private ServicioCuentaContable servicioCuentaContable;
/*  101:     */   @EJB
/*  102:     */   private ServicioGenerico<ConfiguracionConciliacionBancaria> servicioConfiguracionConciliacionBancaria;
/*  103:     */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  104:     */   private LazyDataModel<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  105:     */   private List<ConceptoContable> listaConceptoContable;
/*  106:     */   private List<Documento> listaDocumentos;
/*  107:     */   private List<FormaPago> listaFormaPago;
/*  108:     */   private MovimientoBancario movimientoBancario;
/*  109:     */   private ConciliacionBancaria conciliacionBancaria;
/*  110:     */   private LazyDataModel<MovimientoBancario> listaMovimientoBancario;
/*  111:     */   private LazyDataModel<ConciliacionBancaria> listaConciliacionBancaria;
/*  112:     */   private List<Banco> listaBancoCombo;
/*  113:     */   private List<TipoCuentaBancaria> listaTipoCuentaBancaria;
/*  114:     */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacionCombo;
/*  115:     */   private List<Secuencia> listaSecuencia;
/*  116:     */   private List<Pais> listaPais;
/*  117:     */   private List<SelectItem> listaTipoCuentaBancariaOrganizacion;
/*  118: 148 */   private Producto[] productosSeleccionados = new Producto[1];
/*  119:     */   private CuentaContableCruceCuentaBancariaOrganizacion cuentaContableCruceCuentaBancariaOrganizacion;
/*  120:     */   private boolean indicador;
/*  121:     */   private DocumentoBase documentoBase;
/*  122: 152 */   private TipoAccesoContable tipoAccesoCuentaContable = TipoAccesoContable.BLOQUEADA;
/*  123: 153 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  124:     */   private enumCuentaContableEditada cuentaContableEditada;
/*  125:     */   private DataTable dtCuentaContable;
/*  126:     */   private CuentaContable cuentaContable;
/*  127:     */   private DataTable dtCuentaBancariaOrganizacion;
/*  128:     */   private DataTable dtMovimientoBancario;
/*  129:     */   private DataTable dtConciliacionBancaria;
/*  130:     */   private DataTable dtDetalleConciliacionBancaria;
/*  131:     */   private DataTable dtFormaPagoCuenta;
/*  132:     */   private DataTable dfCuentaContableCruceCuentaBancariaOrganizacion;
/*  133:     */   @ManagedProperty("#{conceptoContableBean}")
/*  134:     */   private ConceptoContableBean conceptoContableBean;
/*  135:     */   @ManagedProperty("#{tipoCuentaBancariaBean}")
/*  136:     */   private TipoCuentaBancariaBean tipoCuentaBancariaBean;
/*  137:     */   @ManagedProperty("#{listaCuentaContableBean}")
/*  138:     */   private ListaCuentaContableBean listaCuentaContableBean;
/*  139:     */   private TipoLocalidad tipoLocalidad;
/*  140:     */   private List<SelectItem> listaTipooLocalidad;
/*  141:     */   private DataTable dtBancoAcreedor;
/*  142:     */   
/*  143:     */   private static enum enumCuentaContableEditada
/*  144:     */   {
/*  145: 157 */     CUENTA_CONTABLE_BANCO,  CUENTA_CONTABLE_GASTO_BANCARIO,  CUENTA_CONTABLE_GASTO_PROTESTO,  CUENTA_CONTABLE_DIFERENCIAS,  CUENTA_CONTABLE_TRANSITORIA,  CUENTA_CONTABLE_RETENCION,  CUENTA_CONTABLE_BANCO_PAGO_CASH;
/*  146:     */     
/*  147:     */     private enumCuentaContableEditada() {}
/*  148:     */   }
/*  149:     */   
/*  150:     */   private static enum TipoLocalidad
/*  151:     */   {
/*  152: 182 */     QUITO,  GUAYAQUIL,  MATRIZ,  SUCURSAL_MAYOR;
/*  153:     */     
/*  154:     */     private TipoLocalidad() {}
/*  155:     */   }
/*  156:     */   
/*  157:     */   @PostConstruct
/*  158:     */   public void init()
/*  159:     */   {
/*  160: 194 */     this.listaCuentaBancariaOrganizacion = new LazyDataModel()
/*  161:     */     {
/*  162:     */       private static final long serialVersionUID = 1L;
/*  163:     */       
/*  164:     */       public List<CuentaBancariaOrganizacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  165:     */       {
/*  166: 202 */         List<CuentaBancariaOrganizacion> lista = new ArrayList();
/*  167:     */         
/*  168: 204 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  169:     */         
/*  170: 206 */         lista = CuentaBancariaOrganizacionBean.this.servicioCuentaBancariaOrganizacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  171:     */         
/*  172: 208 */         CuentaBancariaOrganizacionBean.this.listaCuentaBancariaOrganizacion.setRowCount(CuentaBancariaOrganizacionBean.this.servicioCuentaBancariaOrganizacion.contarPorCriterio(filters));
/*  173:     */         
/*  174: 210 */         return lista;
/*  175:     */       }
/*  176: 213 */     };
/*  177: 214 */     this.listaMovimientoBancario = new LazyDataModel()
/*  178:     */     {
/*  179:     */       private static final long serialVersionUID = 1L;
/*  180:     */       
/*  181:     */       public List<MovimientoBancario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  182:     */       {
/*  183: 221 */         List<MovimientoBancario> lista = new ArrayList();
/*  184: 223 */         if ((CuentaBancariaOrganizacionBean.this.getCuentaBancariaOrganizacion() != null) && (CuentaBancariaOrganizacionBean.this.getCuentaBancariaOrganizacion().getId() > 0))
/*  185:     */         {
/*  186: 224 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  187: 226 */           if (sortField == null)
/*  188:     */           {
/*  189: 227 */             sortField = "fecha";
/*  190: 228 */             ordenar = false;
/*  191:     */           }
/*  192: 231 */           if (filters == null) {
/*  193: 232 */             filters = new HashMap();
/*  194:     */           }
/*  195: 234 */           filters.put("idCuentaBancariaOrganizacion", "" + CuentaBancariaOrganizacionBean.this.getCuentaBancariaOrganizacion().getId());
/*  196: 235 */           lista = CuentaBancariaOrganizacionBean.this.servicioMovimientoBancario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  197:     */           
/*  198: 237 */           CuentaBancariaOrganizacionBean.this.listaMovimientoBancario.setRowCount(CuentaBancariaOrganizacionBean.this.servicioMovimientoBancario.contarPorCriterio(filters));
/*  199:     */         }
/*  200: 241 */         return lista;
/*  201:     */       }
/*  202:     */     };
/*  203:     */   }
/*  204:     */   
/*  205:     */   public String editar()
/*  206:     */   {
/*  207: 254 */     if (getCuentaBancariaOrganizacion().getId() > 0)
/*  208:     */     {
/*  209: 255 */       this.cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(getCuentaBancariaOrganizacion().getId());
/*  210:     */       
/*  211: 257 */       cargarConfiguracionConciliacion();
/*  212:     */       
/*  213: 259 */       setEditado(true);
/*  214:     */     }
/*  215:     */     else
/*  216:     */     {
/*  217: 261 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  218:     */     }
/*  219: 264 */     return "";
/*  220:     */   }
/*  221:     */   
/*  222:     */   private void cargarConfiguracionConciliacion()
/*  223:     */   {
/*  224: 268 */     Map<String, String> filters = new HashMap();
/*  225: 269 */     filters.put("cuentaBancariaOrganizacion.idCuentaBancariaOrganizacion", String.valueOf(getCuentaBancariaOrganizacion().getId()));
/*  226: 270 */     List<ConfiguracionConciliacionBancaria> listaCCB = this.servicioConfiguracionConciliacionBancaria.obtenerListaCombo(ConfiguracionConciliacionBancaria.class, "idOrganizacion", true, filters);
/*  227: 272 */     if ((listaCCB != null) && (!listaCCB.isEmpty())) {
/*  228: 273 */       this.cuentaBancariaOrganizacion.setConfiguracionConciliacionBancaria((ConfiguracionConciliacionBancaria)listaCCB.get(0));
/*  229:     */     } else {
/*  230: 275 */       crearConfiguracionConciliacion();
/*  231:     */     }
/*  232:     */   }
/*  233:     */   
/*  234:     */   public String guardar()
/*  235:     */   {
/*  236:     */     try
/*  237:     */     {
/*  238: 287 */       this.servicioCuentaBancariaOrganizacion.guardar(getCuentaBancariaOrganizacion());
/*  239: 288 */       setEditado(false);
/*  240: 289 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  241: 290 */       cargarDatos();
/*  242:     */     }
/*  243:     */     catch (ExcepcionAS2 e)
/*  244:     */     {
/*  245: 293 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  246: 294 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  247:     */     }
/*  248:     */     catch (Exception e)
/*  249:     */     {
/*  250: 296 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  251: 297 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  252:     */     }
/*  253: 299 */     return "";
/*  254:     */   }
/*  255:     */   
/*  256:     */   public String eliminar()
/*  257:     */   {
/*  258:     */     try
/*  259:     */     {
/*  260: 310 */       this.servicioCuentaBancariaOrganizacion.eliminar(getCuentaBancariaOrganizacion());
/*  261: 311 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  262: 312 */       cargarDatos();
/*  263:     */     }
/*  264:     */     catch (Exception e)
/*  265:     */     {
/*  266: 314 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  267: 315 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  268:     */     }
/*  269: 317 */     return "";
/*  270:     */   }
/*  271:     */   
/*  272:     */   public String limpiar()
/*  273:     */   {
/*  274: 327 */     crearCuentaBancariaOrganizacion();
/*  275: 328 */     crearConfiguracionConciliacion();
/*  276: 329 */     return "";
/*  277:     */   }
/*  278:     */   
/*  279:     */   public String cargarDatos()
/*  280:     */   {
/*  281: 339 */     return "";
/*  282:     */   }
/*  283:     */   
/*  284:     */   public void crearCuentaBancariaOrganizacion()
/*  285:     */   {
/*  286: 344 */     this.cuentaBancariaOrganizacion = new CuentaBancariaOrganizacion();
/*  287: 345 */     CuentaBancaria cuentaBancaria = new CuentaBancaria();
/*  288: 346 */     cuentaBancaria.setPais(new Pais());
/*  289: 347 */     cuentaBancaria.setBanco(new Banco());
/*  290: 348 */     cuentaBancaria.setTipoCuentaBancaria(new TipoCuentaBancaria());
/*  291: 349 */     cuentaBancaria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  292:     */     
/*  293:     */ 
/*  294:     */ 
/*  295: 353 */     this.cuentaBancariaOrganizacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  296:     */     
/*  297: 355 */     this.cuentaBancariaOrganizacion.setCuentaBancaria(cuentaBancaria);
/*  298:     */   }
/*  299:     */   
/*  300:     */   private void crearConfiguracionConciliacion()
/*  301:     */   {
/*  302: 360 */     ConfiguracionConciliacionBancaria configuracionConciliacion = new ConfiguracionConciliacionBancaria();
/*  303:     */     
/*  304: 362 */     configuracionConciliacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  305: 363 */     configuracionConciliacion.setIdSucursal(AppUtil.getSucursal().getId());
/*  306: 364 */     configuracionConciliacion.setActivo(true);
/*  307: 365 */     this.cuentaBancariaOrganizacion.setConfiguracionConciliacionBancaria(configuracionConciliacion);
/*  308:     */   }
/*  309:     */   
/*  310:     */   public String crearMovimientoBancario()
/*  311:     */   {
/*  312: 374 */     this.movimientoBancario = new MovimientoBancario();
/*  313: 375 */     this.movimientoBancario.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  314: 376 */     this.movimientoBancario.setIdSucursal(AppUtil.getSucursal().getId());
/*  315: 377 */     this.movimientoBancario.setDocumento(new Documento());
/*  316: 378 */     this.movimientoBancario.setEstado(Estado.ELABORADO);
/*  317: 379 */     this.movimientoBancario.setFormaPago(new FormaPago());
/*  318: 380 */     this.movimientoBancario.setFecha(Calendar.getInstance().getTime());
/*  319: 381 */     this.movimientoBancario.setCuentaBancariaOrganizacion(getCuentaBancariaOrganizacion());
/*  320: 382 */     this.movimientoBancario.setBeneficiario("");
/*  321: 383 */     return "";
/*  322:     */   }
/*  323:     */   
/*  324:     */   public String crearConciliacionBancaria()
/*  325:     */   {
/*  326: 387 */     this.conciliacionBancaria = new ConciliacionBancaria();
/*  327: 388 */     this.conciliacionBancaria.setCuentaBancariaOrganizacion(this.cuentaBancariaOrganizacion);
/*  328: 389 */     return "";
/*  329:     */   }
/*  330:     */   
/*  331:     */   public String guardarMovimientoBancario()
/*  332:     */   {
/*  333:     */     try
/*  334:     */     {
/*  335: 395 */       this.servicioMovimientoBancario.guardar(this.movimientoBancario);
/*  336: 396 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  337:     */     }
/*  338:     */     catch (ExcepcionAS2Financiero e)
/*  339:     */     {
/*  340: 400 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  341: 401 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  342:     */     }
/*  343:     */     catch (ExcepcionAS2 e)
/*  344:     */     {
/*  345: 404 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  346: 405 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  347:     */     }
/*  348:     */     catch (AS2Exception e)
/*  349:     */     {
/*  350: 407 */       this.exContabilizacion = e;
/*  351: 408 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  352: 409 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  353:     */     }
/*  354:     */     catch (Exception e)
/*  355:     */     {
/*  356: 412 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  357: 413 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  358:     */     }
/*  359: 416 */     return "";
/*  360:     */   }
/*  361:     */   
/*  362:     */   public String guardarConciliacionBancaria()
/*  363:     */   {
/*  364:     */     try
/*  365:     */     {
/*  366: 426 */       if (this.conciliacionBancaria.getId() == 0) {
/*  367: 427 */         this.conciliacionBancaria.setEstado(Estado.ELABORADO);
/*  368:     */       }
/*  369: 430 */       this.servicioConciliacionBancaria.guardar(this.conciliacionBancaria);
/*  370:     */       
/*  371: 432 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  372: 433 */       cargarDatos();
/*  373:     */     }
/*  374:     */     catch (Exception e)
/*  375:     */     {
/*  376: 435 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  377: 436 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  378:     */     }
/*  379: 438 */     return "";
/*  380:     */   }
/*  381:     */   
/*  382:     */   public String editarMovimientoBancario()
/*  383:     */   {
/*  384: 442 */     recuperarMovimientoSeleccionado();
/*  385: 443 */     return "";
/*  386:     */   }
/*  387:     */   
/*  388:     */   public void recuperarMovimientoSeleccionado()
/*  389:     */   {
/*  390: 452 */     int idDetalleMovimientoBanacario = ((MovimientoBancario)this.dtMovimientoBancario.getRowData()).getId();
/*  391:     */     try
/*  392:     */     {
/*  393: 454 */       this.movimientoBancario = this.servicioMovimientoBancario.recuperaDetalleBancario(idDetalleMovimientoBanacario);
/*  394:     */     }
/*  395:     */     catch (ExcepcionAS2Financiero e)
/*  396:     */     {
/*  397: 456 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  398: 457 */       LOG.error(e);
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   public String anularMovimientoBancario()
/*  403:     */   {
/*  404:     */     try
/*  405:     */     {
/*  406: 463 */       this.servicioMovimientoBancario.anular(this.movimientoBancario);
/*  407:     */     }
/*  408:     */     catch (ExcepcionAS2Financiero e)
/*  409:     */     {
/*  410: 465 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  411: 466 */       LOG.error(e);
/*  412:     */     }
/*  413:     */     catch (ExcepcionAS2 e)
/*  414:     */     {
/*  415: 468 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  416: 469 */       LOG.error(e);
/*  417:     */     }
/*  418: 471 */     return "";
/*  419:     */   }
/*  420:     */   
/*  421:     */   public String agregarFormaPagoCuenta()
/*  422:     */   {
/*  423: 482 */     FormaPagoCuentaBancariaOrganizacion formaPagoCuenta = new FormaPagoCuentaBancariaOrganizacion();
/*  424: 483 */     formaPagoCuenta.setFormaPago(new FormaPago());
/*  425: 484 */     formaPagoCuenta.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  426: 485 */     formaPagoCuenta.setCuentaBancariaOrganizacion(this.cuentaBancariaOrganizacion);
/*  427: 486 */     formaPagoCuenta.setIndicadorDebitoBancario(false);
/*  428: 487 */     this.cuentaBancariaOrganizacion.getListaFormaPago().add(formaPagoCuenta);
/*  429:     */     
/*  430: 489 */     return "";
/*  431:     */   }
/*  432:     */   
/*  433:     */   public String eliminarFormaPagoCuenta()
/*  434:     */   {
/*  435: 500 */     FormaPagoCuentaBancariaOrganizacion formaPagoCuenta = (FormaPagoCuentaBancariaOrganizacion)this.dtFormaPagoCuenta.getRowData();
/*  436:     */     
/*  437: 502 */     formaPagoCuenta.setEliminado(true);
/*  438:     */     
/*  439: 504 */     return "";
/*  440:     */   }
/*  441:     */   
/*  442:     */   public String eliminarCuentaContableCruce()
/*  443:     */   {
/*  444: 510 */     CuentaContableCruceCuentaBancariaOrganizacion cuentaContableCruceCuentaBancariaOrganizacion = (CuentaContableCruceCuentaBancariaOrganizacion)this.dfCuentaContableCruceCuentaBancariaOrganizacion.getRowData();
/*  445: 511 */     cuentaContableCruceCuentaBancariaOrganizacion.setEliminado(true);
/*  446:     */     
/*  447: 513 */     return "";
/*  448:     */   }
/*  449:     */   
/*  450:     */   public void cargarCuentaContable(SelectEvent event)
/*  451:     */   {
/*  452: 517 */     this.cuentaContable = ((CuentaContable)event.getObject());
/*  453: 518 */     switch (3.$SwitchMap$com$asinfo$as2$finaciero$contabilidad$procesos$controller$CuentaBancariaOrganizacionBean$enumCuentaContableEditada[this.cuentaContableEditada.ordinal()])
/*  454:     */     {
/*  455:     */     case 1: 
/*  456: 521 */       this.cuentaBancariaOrganizacion.setCuentaContableBanco(this.cuentaContable);
/*  457: 522 */       break;
/*  458:     */     case 2: 
/*  459: 524 */       this.cuentaBancariaOrganizacion.setCuentaContableBancoPagoCash(this.cuentaContable);
/*  460: 525 */       break;
/*  461:     */     case 3: 
/*  462: 528 */       this.cuentaBancariaOrganizacion.setCuentaContableGastosBancarios(this.cuentaContable);
/*  463: 529 */       break;
/*  464:     */     case 4: 
/*  465: 532 */       this.cuentaBancariaOrganizacion.setCuentaContableGastosProtesto(this.cuentaContable);
/*  466: 533 */       break;
/*  467:     */     case 5: 
/*  468: 536 */       this.cuentaBancariaOrganizacion.setCuentaContableDiferencias(this.cuentaContable);
/*  469: 537 */       break;
/*  470:     */     case 6: 
/*  471: 540 */       this.cuentaBancariaOrganizacion.setCuentaContableTransitoria(this.cuentaContable);
/*  472: 541 */       break;
/*  473:     */     case 7: 
/*  474: 544 */       this.cuentaBancariaOrganizacion.setCuentaContableRetencion(this.cuentaContable);
/*  475: 545 */       break;
/*  476:     */     }
/*  477:     */   }
/*  478:     */   
/*  479:     */   public void actualizarCuentaContableBanco()
/*  480:     */   {
/*  481: 553 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_BANCO;
/*  482: 554 */     this.cuentaContable = this.cuentaBancariaOrganizacion.getCuentaContableBanco();
/*  483:     */   }
/*  484:     */   
/*  485:     */   public void actualizarCuentaContableGastoBancario()
/*  486:     */   {
/*  487: 558 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_GASTO_BANCARIO;
/*  488: 559 */     this.cuentaContable = this.cuentaBancariaOrganizacion.getCuentaContableGastosBancarios();
/*  489:     */   }
/*  490:     */   
/*  491:     */   public void actualizarCuentaContableGastoProtesto()
/*  492:     */   {
/*  493: 563 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_GASTO_PROTESTO;
/*  494: 564 */     this.cuentaContable = this.cuentaBancariaOrganizacion.getCuentaContableGastosProtesto();
/*  495:     */   }
/*  496:     */   
/*  497:     */   public void actualizarCuentaContableCajaChicaDebe()
/*  498:     */   {
/*  499: 568 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_DIFERENCIAS;
/*  500: 569 */     this.cuentaContable = this.cuentaBancariaOrganizacion.getCuentaContableDiferencias();
/*  501:     */   }
/*  502:     */   
/*  503:     */   public void actualizarCuentaContableRetencion()
/*  504:     */   {
/*  505: 573 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_RETENCION;
/*  506: 574 */     this.cuentaContable = this.cuentaBancariaOrganizacion.getCuentaContableRetencion();
/*  507:     */   }
/*  508:     */   
/*  509:     */   public List<Empresa> autocompletarProveedores(String consulta)
/*  510:     */   {
/*  511: 578 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/*  512:     */   }
/*  513:     */   
/*  514:     */   public void cargarProducto(Producto producto)
/*  515:     */   {
/*  516: 582 */     this.productosSeleccionados[0] = producto;
/*  517: 583 */     this.cuentaBancariaOrganizacion.setProducto(producto);
/*  518:     */   }
/*  519:     */   
/*  520:     */   public String agregarCuentaContableCruceCuentaBancariaOrganizacion()
/*  521:     */   {
/*  522: 587 */     CuentaContableCruceCuentaBancariaOrganizacion cccco = new CuentaContableCruceCuentaBancariaOrganizacion();
/*  523: 588 */     cccco.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  524: 589 */     cccco.setCuentaContable(new CuentaContable());
/*  525: 590 */     cccco.setCuentaBancariaOrganizacion(this.cuentaBancariaOrganizacion);
/*  526: 591 */     this.cuentaBancariaOrganizacion.getListaCuentaContableCruceCuentaBancariaOrganizacion().add(cccco);
/*  527: 592 */     return "";
/*  528:     */   }
/*  529:     */   
/*  530:     */   public void indicadorCruce()
/*  531:     */   {
/*  532:     */     CuentaContableCruceCuentaBancariaOrganizacion cccco;
/*  533: 597 */     if (this.cuentaBancariaOrganizacion.isIndicadorCruce())
/*  534:     */     {
/*  535: 598 */       cccco = new CuentaContableCruceCuentaBancariaOrganizacion();
/*  536: 599 */       cccco.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  537: 600 */       cccco.setCuentaContable(new CuentaContable());
/*  538: 601 */       cccco.setCuentaBancariaOrganizacion(this.cuentaBancariaOrganizacion);
/*  539: 602 */       this.cuentaBancariaOrganizacion.getListaCuentaContableCruceCuentaBancariaOrganizacion().add(cccco);
/*  540:     */     }
/*  541:     */     else
/*  542:     */     {
/*  543: 604 */       for (CuentaContableCruceCuentaBancariaOrganizacion cuentaContableCruceCuentaBancariaOrganizacion : this.cuentaBancariaOrganizacion
/*  544: 605 */         .getListaCuentaContableCruceCuentaBancariaOrganizacion()) {
/*  545: 606 */         cuentaContableCruceCuentaBancariaOrganizacion.setEliminado(true);
/*  546:     */       }
/*  547:     */     }
/*  548:     */   }
/*  549:     */   
/*  550:     */   public void cargarLocalidad()
/*  551:     */   {
/*  552: 613 */     if (getTipoLocalidad().equals(TipoLocalidad.GUAYAQUIL)) {
/*  553: 614 */       this.cuentaBancariaOrganizacion.setLocalidad("1");
/*  554: 615 */     } else if (getTipoLocalidad().equals(TipoLocalidad.QUITO)) {
/*  555: 616 */       this.cuentaBancariaOrganizacion.setLocalidad("5");
/*  556: 617 */     } else if (getTipoLocalidad().equals(TipoLocalidad.MATRIZ)) {
/*  557: 618 */       this.cuentaBancariaOrganizacion.setLocalidad("01");
/*  558: 619 */     } else if (getTipoLocalidad().equals(TipoLocalidad.SUCURSAL_MAYOR)) {
/*  559: 620 */       this.cuentaBancariaOrganizacion.setLocalidad("06");
/*  560:     */     }
/*  561:     */   }
/*  562:     */   
/*  563:     */   public void agregarBancoAcreedor()
/*  564:     */   {
/*  565: 627 */     BancoAcreedor ba = new BancoAcreedor();
/*  566: 628 */     ba.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  567: 629 */     ba.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  568: 630 */     ba.setActivo(true);
/*  569: 631 */     ba.setCuentaBancariaOrganizacion(this.cuentaBancariaOrganizacion);
/*  570:     */     
/*  571: 633 */     this.cuentaBancariaOrganizacion.getListaBancoAcreedor().add(ba);
/*  572:     */   }
/*  573:     */   
/*  574:     */   public List<BancoAcreedor> getListaBancoAcreedor()
/*  575:     */   {
/*  576: 638 */     List<BancoAcreedor> lista = new ArrayList();
/*  577: 640 */     for (BancoAcreedor ba : this.cuentaBancariaOrganizacion.getListaBancoAcreedor()) {
/*  578: 641 */       if (!ba.isEliminado()) {
/*  579: 642 */         lista.add(ba);
/*  580:     */       }
/*  581:     */     }
/*  582: 646 */     return lista;
/*  583:     */   }
/*  584:     */   
/*  585:     */   public String eliminarBancoAcreedor()
/*  586:     */   {
/*  587: 650 */     BancoAcreedor ba = (BancoAcreedor)this.dtBancoAcreedor.getRowData();
/*  588: 651 */     ba.setEliminado(true);
/*  589: 652 */     return "";
/*  590:     */   }
/*  591:     */   
/*  592:     */   public List<MovimientoBancario> getDetalleConciliacionBancaria()
/*  593:     */   {
/*  594: 666 */     List<MovimientoBancario> detalle = new ArrayList();
/*  595: 667 */     for (MovimientoBancario d : getConciliacionBancaria().getListaMovimientoBancario()) {
/*  596: 670 */       detalle.add(d);
/*  597:     */     }
/*  598: 674 */     return detalle;
/*  599:     */   }
/*  600:     */   
/*  601:     */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/*  602:     */   {
/*  603: 683 */     if (this.cuentaBancariaOrganizacion == null) {
/*  604: 684 */       crearCuentaBancariaOrganizacion();
/*  605:     */     }
/*  606: 687 */     return this.cuentaBancariaOrganizacion;
/*  607:     */   }
/*  608:     */   
/*  609:     */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/*  610:     */   {
/*  611: 697 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/*  612:     */   }
/*  613:     */   
/*  614:     */   public LazyDataModel<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/*  615:     */   {
/*  616: 701 */     return this.listaCuentaBancariaOrganizacion;
/*  617:     */   }
/*  618:     */   
/*  619:     */   public void setListaCuentaBancariaOrganizacion(LazyDataModel<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/*  620:     */   {
/*  621: 705 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/*  622:     */   }
/*  623:     */   
/*  624:     */   public List<SelectItem> getListaTipoCuentaBancariaOrganizacion()
/*  625:     */   {
/*  626: 715 */     if (this.listaTipoCuentaBancariaOrganizacion == null)
/*  627:     */     {
/*  628: 716 */       this.listaTipoCuentaBancariaOrganizacion = new ArrayList();
/*  629: 717 */       for (TipoCuentaBancariaOrganizacion t : TipoCuentaBancariaOrganizacion.values())
/*  630:     */       {
/*  631: 718 */         SelectItem item = new SelectItem(t, t.getNombre());
/*  632: 719 */         this.listaTipoCuentaBancariaOrganizacion.add(item);
/*  633:     */       }
/*  634:     */     }
/*  635: 723 */     return this.listaTipoCuentaBancariaOrganizacion;
/*  636:     */   }
/*  637:     */   
/*  638:     */   public void setListaTipoCuentaBancariaOrganizacion(List<SelectItem> listaTipoCuentaBancariaOrganizacion)
/*  639:     */   {
/*  640: 733 */     this.listaTipoCuentaBancariaOrganizacion = listaTipoCuentaBancariaOrganizacion;
/*  641:     */   }
/*  642:     */   
/*  643:     */   public void actualizarConseptoContable(AjaxBehaviorEvent event)
/*  644:     */   {
/*  645: 737 */     this.indicador = true;
/*  646:     */     try
/*  647:     */     {
/*  648: 739 */       int idTipoDocumento = ((Integer)((HtmlSelectOneMenu)event.getSource()).getValue()).intValue();
/*  649:     */       
/*  650: 741 */       Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(idTipoDocumento));
/*  651: 743 */       if (documento.getDocumentoBase() == DocumentoBase.CONCEPTOS_CONTABLES) {
/*  652: 744 */         this.indicador = false;
/*  653:     */       }
/*  654:     */     }
/*  655:     */     catch (Exception e)
/*  656:     */     {
/*  657: 749 */       e.printStackTrace();
/*  658:     */     }
/*  659:     */   }
/*  660:     */   
/*  661:     */   public List<Secuencia> getListaSecuencia()
/*  662:     */   {
/*  663: 754 */     if (this.listaSecuencia == null) {
/*  664: 755 */       this.listaSecuencia = this.servicioSecuencia.obtenerListaCombo();
/*  665:     */     }
/*  666: 757 */     return this.listaSecuencia;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void setListaSecuencia(List<Secuencia> listaSecuencua)
/*  670:     */   {
/*  671: 761 */     this.listaSecuencia = listaSecuencua;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public List<Documento> getListaDocumentos()
/*  675:     */   {
/*  676:     */     try
/*  677:     */     {
/*  678: 773 */       if (this.listaDocumentos == null)
/*  679:     */       {
/*  680: 774 */         this.listaDocumentos = new ArrayList();
/*  681: 775 */         this.listaDocumentos.addAll(this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.GASTOS_BANCARIOS, AppUtil.getOrganizacion()
/*  682: 776 */           .getId()));
/*  683: 777 */         this.listaDocumentos.addAll(this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.CONCEPTOS_CONTABLES, 
/*  684: 778 */           AppUtil.getOrganizacion().getId()));
/*  685:     */       }
/*  686:     */     }
/*  687:     */     catch (ExcepcionAS2 e)
/*  688:     */     {
/*  689: 782 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  690:     */     }
/*  691: 784 */     return this.listaDocumentos;
/*  692:     */   }
/*  693:     */   
/*  694:     */   public void setListaDocumentos(List<Documento> listaDocumentos)
/*  695:     */   {
/*  696: 788 */     this.listaDocumentos = listaDocumentos;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public DataTable getDtCuentaBancariaOrganizacion()
/*  700:     */   {
/*  701: 797 */     return this.dtCuentaBancariaOrganizacion;
/*  702:     */   }
/*  703:     */   
/*  704:     */   public void setDtCuentaBancariaOrganizacion(DataTable dtCuentaBancariaOrganizacion)
/*  705:     */   {
/*  706: 807 */     this.dtCuentaBancariaOrganizacion = dtCuentaBancariaOrganizacion;
/*  707:     */   }
/*  708:     */   
/*  709:     */   public MovimientoBancario getMovimientoBancario()
/*  710:     */   {
/*  711: 811 */     if (this.movimientoBancario == null) {
/*  712: 812 */       crearMovimientoBancario();
/*  713:     */     }
/*  714: 814 */     return this.movimientoBancario;
/*  715:     */   }
/*  716:     */   
/*  717:     */   public void setMovimientoBancario(MovimientoBancario movimientoBancario)
/*  718:     */   {
/*  719: 818 */     this.movimientoBancario = movimientoBancario;
/*  720:     */   }
/*  721:     */   
/*  722:     */   public DataTable getDtMovimientoBancario()
/*  723:     */   {
/*  724: 822 */     return this.dtMovimientoBancario;
/*  725:     */   }
/*  726:     */   
/*  727:     */   public void setDtMovimientoBancario(DataTable dtMovimientoBancario)
/*  728:     */   {
/*  729: 826 */     this.dtMovimientoBancario = dtMovimientoBancario;
/*  730:     */   }
/*  731:     */   
/*  732:     */   public TipoCuentaBancariaBean getTipoCuentaBancariaBean()
/*  733:     */   {
/*  734: 835 */     return this.tipoCuentaBancariaBean;
/*  735:     */   }
/*  736:     */   
/*  737:     */   public void setTipoCuentaBancariaBean(TipoCuentaBancariaBean tipoCuentaBancariaBean)
/*  738:     */   {
/*  739: 845 */     this.tipoCuentaBancariaBean = tipoCuentaBancariaBean;
/*  740:     */   }
/*  741:     */   
/*  742:     */   public boolean isIndicador()
/*  743:     */   {
/*  744: 849 */     return this.indicador;
/*  745:     */   }
/*  746:     */   
/*  747:     */   public void setIndicador(boolean indicador)
/*  748:     */   {
/*  749: 853 */     this.indicador = indicador;
/*  750:     */   }
/*  751:     */   
/*  752:     */   public List<ConceptoContable> getListaConceptoContable()
/*  753:     */   {
/*  754: 857 */     if (this.listaConceptoContable == null) {
/*  755: 858 */       this.listaConceptoContable = this.servicioConceptoContable.obtenerListaCombo("codigo", true, null);
/*  756:     */     }
/*  757: 860 */     return this.listaConceptoContable;
/*  758:     */   }
/*  759:     */   
/*  760:     */   public void setListaConceptoContable(List<ConceptoContable> listaConceptoContable)
/*  761:     */   {
/*  762: 864 */     this.listaConceptoContable = listaConceptoContable;
/*  763:     */   }
/*  764:     */   
/*  765:     */   public ConceptoContableBean getConceptoContableBean()
/*  766:     */   {
/*  767: 873 */     return this.conceptoContableBean;
/*  768:     */   }
/*  769:     */   
/*  770:     */   public void setConceptoContableBean(ConceptoContableBean conceptoContableBean)
/*  771:     */   {
/*  772: 883 */     this.conceptoContableBean = conceptoContableBean;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public LazyDataModel<MovimientoBancario> getListaMovimientoBancario()
/*  776:     */   {
/*  777: 887 */     return this.listaMovimientoBancario;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public void setListaMovimientoBancario(LazyDataModel<MovimientoBancario> listaMovimientoBancario)
/*  781:     */   {
/*  782: 891 */     this.listaMovimientoBancario = listaMovimientoBancario;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public LazyDataModel<ConciliacionBancaria> getListaConciliacionBancaria()
/*  786:     */   {
/*  787: 895 */     return this.listaConciliacionBancaria;
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void setListaConciliacionBancaria(LazyDataModel<ConciliacionBancaria> listaConciliacionBancaria)
/*  791:     */   {
/*  792: 899 */     this.listaConciliacionBancaria = listaConciliacionBancaria;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public ConciliacionBancaria getConciliacionBancaria()
/*  796:     */   {
/*  797: 903 */     if (this.conciliacionBancaria == null)
/*  798:     */     {
/*  799: 904 */       this.conciliacionBancaria = new ConciliacionBancaria();
/*  800: 905 */       this.conciliacionBancaria.setCuentaBancariaOrganizacion(this.cuentaBancariaOrganizacion);
/*  801:     */     }
/*  802: 907 */     return this.conciliacionBancaria;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public void setConciliacionBancaria(ConciliacionBancaria conciliacionBancaria)
/*  806:     */   {
/*  807: 911 */     this.conciliacionBancaria = conciliacionBancaria;
/*  808:     */   }
/*  809:     */   
/*  810:     */   public DataTable getDtConciliacionBancaria()
/*  811:     */   {
/*  812: 915 */     return this.dtConciliacionBancaria;
/*  813:     */   }
/*  814:     */   
/*  815:     */   public void setDtConciliacionBancaria(DataTable dtConciliacionBancaria)
/*  816:     */   {
/*  817: 919 */     this.dtConciliacionBancaria = dtConciliacionBancaria;
/*  818:     */   }
/*  819:     */   
/*  820:     */   public DataTable getDtDetalleConciliacionBancaria()
/*  821:     */   {
/*  822: 923 */     return this.dtDetalleConciliacionBancaria;
/*  823:     */   }
/*  824:     */   
/*  825:     */   public void setDtDetalleConciliacionBancaria(DataTable dtDetalleConciliacionBancaria)
/*  826:     */   {
/*  827: 927 */     this.dtDetalleConciliacionBancaria = dtDetalleConciliacionBancaria;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public List<Pais> getListaPais()
/*  831:     */   {
/*  832: 931 */     if (this.listaPais == null) {
/*  833: 932 */       this.listaPais = this.servicioPais.obtenerListaCombo(null, false, null);
/*  834:     */     }
/*  835: 934 */     return this.listaPais;
/*  836:     */   }
/*  837:     */   
/*  838:     */   public DocumentoBase getDocumentoBase()
/*  839:     */   {
/*  840: 938 */     return this.documentoBase;
/*  841:     */   }
/*  842:     */   
/*  843:     */   public void setDocumentoBase(DocumentoBase documentoBase)
/*  844:     */   {
/*  845: 942 */     this.documentoBase = documentoBase;
/*  846:     */   }
/*  847:     */   
/*  848:     */   public List<FormaPagoCuentaBancariaOrganizacion> getListaFormaPagoCuenta()
/*  849:     */   {
/*  850: 952 */     List<FormaPagoCuentaBancariaOrganizacion> lista = new ArrayList();
/*  851: 953 */     for (FormaPagoCuentaBancariaOrganizacion fp : this.cuentaBancariaOrganizacion.getListaFormaPago()) {
/*  852: 954 */       if (!fp.isEliminado()) {
/*  853: 955 */         lista.add(fp);
/*  854:     */       }
/*  855:     */     }
/*  856: 958 */     return lista;
/*  857:     */   }
/*  858:     */   
/*  859:     */   public DataTable getDtFormaPagoCuenta()
/*  860:     */   {
/*  861: 967 */     return this.dtFormaPagoCuenta;
/*  862:     */   }
/*  863:     */   
/*  864:     */   public void setDtFormaPagoCuenta(DataTable dtFormaPagoCuenta)
/*  865:     */   {
/*  866: 977 */     this.dtFormaPagoCuenta = dtFormaPagoCuenta;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public List<FormaPago> getListaFormaPago()
/*  870:     */   {
/*  871: 986 */     if (this.listaFormaPago == null) {
/*  872: 987 */       this.listaFormaPago = this.servicioFormaPago.obtenerListaCombo("nombre", true, null);
/*  873:     */     }
/*  874: 989 */     return this.listaFormaPago;
/*  875:     */   }
/*  876:     */   
/*  877:     */   public void setListaFormaPago(List<FormaPago> listaFormaPago)
/*  878:     */   {
/*  879: 999 */     this.listaFormaPago = listaFormaPago;
/*  880:     */   }
/*  881:     */   
/*  882:     */   public List<Banco> getListaBancoCombo()
/*  883:     */   {
/*  884:1008 */     if (this.listaBancoCombo == null) {
/*  885:1009 */       this.listaBancoCombo = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, null);
/*  886:     */     }
/*  887:1011 */     return this.listaBancoCombo;
/*  888:     */   }
/*  889:     */   
/*  890:     */   public void setListaBancoCombo(List<Banco> listaBancoCombo)
/*  891:     */   {
/*  892:1021 */     this.listaBancoCombo = listaBancoCombo;
/*  893:     */   }
/*  894:     */   
/*  895:     */   public List<TipoCuentaBancaria> getListaTipoCuentaBancaria()
/*  896:     */   {
/*  897:1025 */     if (this.listaTipoCuentaBancaria == null) {
/*  898:1026 */       this.listaTipoCuentaBancaria = this.servicioTipoCuentaBancaria.obtenerListaCombo(null, false, null);
/*  899:     */     }
/*  900:1028 */     return this.listaTipoCuentaBancaria;
/*  901:     */   }
/*  902:     */   
/*  903:     */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacionCombo()
/*  904:     */   {
/*  905:1037 */     if (this.listaCuentaBancariaOrganizacionCombo == null) {
/*  906:1038 */       this.listaCuentaBancariaOrganizacionCombo = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/*  907:     */     }
/*  908:1040 */     return this.listaCuentaBancariaOrganizacionCombo;
/*  909:     */   }
/*  910:     */   
/*  911:     */   public void setListaCuentaBancariaOrganizacionCombo(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacionCombo)
/*  912:     */   {
/*  913:1050 */     this.listaCuentaBancariaOrganizacionCombo = listaCuentaBancariaOrganizacionCombo;
/*  914:     */   }
/*  915:     */   
/*  916:     */   public enumCuentaContableEditada getCuentaContableEditada()
/*  917:     */   {
/*  918:1061 */     return this.cuentaContableEditada;
/*  919:     */   }
/*  920:     */   
/*  921:     */   public void setCuentaContableEditada(enumCuentaContableEditada cuentaContableEditada)
/*  922:     */   {
/*  923:1071 */     this.cuentaContableEditada = cuentaContableEditada;
/*  924:     */   }
/*  925:     */   
/*  926:     */   public DataTable getDtCuentaContable()
/*  927:     */   {
/*  928:1080 */     return this.dtCuentaContable;
/*  929:     */   }
/*  930:     */   
/*  931:     */   public void setDtCuentaContable(DataTable dtCuentaContable)
/*  932:     */   {
/*  933:1090 */     this.dtCuentaContable = dtCuentaContable;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public CuentaContable getCuentaContable()
/*  937:     */   {
/*  938:1099 */     return this.cuentaContable;
/*  939:     */   }
/*  940:     */   
/*  941:     */   public void setCuentaContable(CuentaContable cuentaContable)
/*  942:     */   {
/*  943:1109 */     this.cuentaContable = cuentaContable;
/*  944:     */   }
/*  945:     */   
/*  946:     */   public Producto[] getProductosSeleccionados()
/*  947:     */   {
/*  948:1118 */     return this.productosSeleccionados;
/*  949:     */   }
/*  950:     */   
/*  951:     */   public void setProductosSeleccionados(Producto[] productosSeleccionados)
/*  952:     */   {
/*  953:1128 */     this.productosSeleccionados = productosSeleccionados;
/*  954:     */   }
/*  955:     */   
/*  956:     */   public Producto getProducto()
/*  957:     */   {
/*  958:1137 */     return this.productosSeleccionados[0];
/*  959:     */   }
/*  960:     */   
/*  961:     */   public DataTable getDfCuentaContableCruceCuentaBancariaOrganizacion()
/*  962:     */   {
/*  963:1141 */     return this.dfCuentaContableCruceCuentaBancariaOrganizacion;
/*  964:     */   }
/*  965:     */   
/*  966:     */   public void setDfCuentaContableCruceCuentaBancariaOrganizacion(DataTable dfCuentaContableCruceCuentaBancariaOrganizacion)
/*  967:     */   {
/*  968:1145 */     this.dfCuentaContableCruceCuentaBancariaOrganizacion = dfCuentaContableCruceCuentaBancariaOrganizacion;
/*  969:     */   }
/*  970:     */   
/*  971:     */   public List<CuentaContableCruceCuentaBancariaOrganizacion> getListaCuentaContableCruceCuentaBancariaOrganizacion()
/*  972:     */   {
/*  973:1149 */     List<CuentaContableCruceCuentaBancariaOrganizacion> lista = new ArrayList();
/*  974:1150 */     for (CuentaContableCruceCuentaBancariaOrganizacion cuentaContableCruceCuentaBancariaOrganizacion : this.cuentaBancariaOrganizacion
/*  975:1151 */       .getListaCuentaContableCruceCuentaBancariaOrganizacion()) {
/*  976:1152 */       if (!cuentaContableCruceCuentaBancariaOrganizacion.isEliminado()) {
/*  977:1153 */         lista.add(cuentaContableCruceCuentaBancariaOrganizacion);
/*  978:     */       }
/*  979:     */     }
/*  980:1156 */     return lista;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public CuentaContableCruceCuentaBancariaOrganizacion getCuentaContableCruceCuentaBancariaOrganizacion()
/*  984:     */   {
/*  985:1160 */     return this.cuentaContableCruceCuentaBancariaOrganizacion;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public void setCuentaContableCruceCuentaBancariaOrganizacion(CuentaContableCruceCuentaBancariaOrganizacion cuentaContableCruceCuentaBancariaOrganizacion)
/*  989:     */   {
/*  990:1165 */     this.cuentaContableCruceCuentaBancariaOrganizacion = cuentaContableCruceCuentaBancariaOrganizacion;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public TipoAccesoContable getTipoAccesoCuentaContable()
/*  994:     */   {
/*  995:1174 */     return this.tipoAccesoCuentaContable;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public void setTipoAccesoCuentaContable(TipoAccesoContable tipoAccesoCuentaContable)
/*  999:     */   {
/* 1000:1184 */     this.tipoAccesoCuentaContable = tipoAccesoCuentaContable;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public void buscarCuentaContable()
/* 1004:     */   {
/* 1005:     */     try
/* 1006:     */     {
/* 1007:1193 */       this.cuentaContableCruceCuentaBancariaOrganizacion = ((CuentaContableCruceCuentaBancariaOrganizacion)this.dfCuentaContableCruceCuentaBancariaOrganizacion.getRowData());
/* 1008:1194 */       String codigoCuentaContable = this.cuentaContableCruceCuentaBancariaOrganizacion.getCuentaContable().getCodigo();
/* 1009:1195 */       if (codigoCuentaContable != "")
/* 1010:     */       {
/* 1011:1196 */         this.cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion().getIdOrganizacion());
/* 1012:1197 */         this.cuentaContableCruceCuentaBancariaOrganizacion.setCuentaContable(this.cuentaContable);
/* 1013:     */       }
/* 1014:     */     }
/* 1015:     */     catch (ExcepcionAS2Financiero e)
/* 1016:     */     {
/* 1017:1202 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion()) + this.cuentaContableCruceCuentaBancariaOrganizacion.getCuentaContable().getCodigo();
/* 1018:1203 */       addInfoMessage(strMensaje);
/* 1019:1204 */       this.cuentaContableCruceCuentaBancariaOrganizacion.getCuentaContable().setCodigo("");
/* 1020:     */     }
/* 1021:     */     catch (Exception e)
/* 1022:     */     {
/* 1023:1207 */       LOG.error("ERROR AL BUSCAR CUENTA CONTABLE");
/* 1024:     */     }
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public AS2Exception getExContabilizacion()
/* 1028:     */   {
/* 1029:1217 */     return this.exContabilizacion;
/* 1030:     */   }
/* 1031:     */   
/* 1032:     */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 1033:     */   {
/* 1034:1227 */     this.exContabilizacion = exContabilizacion;
/* 1035:     */   }
/* 1036:     */   
/* 1037:     */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 1038:     */   {
/* 1039:1231 */     return this.listaCuentaContableBean;
/* 1040:     */   }
/* 1041:     */   
/* 1042:     */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 1043:     */   {
/* 1044:1235 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 1045:     */   }
/* 1046:     */   
/* 1047:     */   public List<SelectItem> getListaTipooLocalidad()
/* 1048:     */   {
/* 1049:1239 */     if (this.listaTipooLocalidad == null)
/* 1050:     */     {
/* 1051:1240 */       this.listaTipooLocalidad = new ArrayList();
/* 1052:1241 */       for (TipoLocalidad to : TipoLocalidad.values()) {
/* 1053:1242 */         this.listaTipooLocalidad.add(new SelectItem(to, to.name()));
/* 1054:     */       }
/* 1055:     */     }
/* 1056:1245 */     return this.listaTipooLocalidad;
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public void setListaTipooLocalidad(List<SelectItem> listaTipooLocalidad)
/* 1060:     */   {
/* 1061:1249 */     this.listaTipooLocalidad = listaTipooLocalidad;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public TipoLocalidad getTipoLocalidad()
/* 1065:     */   {
/* 1066:1256 */     return this.tipoLocalidad;
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public void setTipoLocalidad(TipoLocalidad tipoLocalidad)
/* 1070:     */   {
/* 1071:1264 */     this.tipoLocalidad = tipoLocalidad;
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   public DataTable getDtBancoAcreedor()
/* 1075:     */   {
/* 1076:1268 */     return this.dtBancoAcreedor;
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public void setDtBancoAcreedor(DataTable dtBancoAcreedor)
/* 1080:     */   {
/* 1081:1272 */     this.dtBancoAcreedor = dtBancoAcreedor;
/* 1082:     */   }
/* 1083:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.CuentaBancariaOrganizacionBean
 * JD-Core Version:    0.7.0.1
 */