/*    1:     */ package com.asinfo.as2.configuracionbase.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*    4:     */ import com.asinfo.as2.controller.LanguageController;
/*    5:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*    9:     */ import com.asinfo.as2.entities.CentroCosto;
/*   10:     */ import com.asinfo.as2.entities.CondicionPago;
/*   11:     */ import com.asinfo.as2.entities.Configuracion;
/*   12:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   13:     */ import com.asinfo.as2.entities.CuentaContable;
/*   14:     */ import com.asinfo.as2.entities.Empresa;
/*   15:     */ import com.asinfo.as2.entities.FormaPago;
/*   16:     */ import com.asinfo.as2.entities.Organizacion;
/*   17:     */ import com.asinfo.as2.entities.Producto;
/*   18:     */ import com.asinfo.as2.entities.Rubro;
/*   19:     */ import com.asinfo.as2.entities.TipoAsiento;
/*   20:     */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   21:     */ import com.asinfo.as2.enumeraciones.ModuloEnum;
/*   22:     */ import com.asinfo.as2.enumeraciones.Parametro;
/*   23:     */ import com.asinfo.as2.enumeraciones.TipoAnexoSRI;
/*   24:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI;
/*   25:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*   26:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   27:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*   28:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   29:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   30:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*   31:     */ import com.asinfo.as2.util.AppUtil;
/*   32:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   33:     */ import java.math.BigDecimal;
/*   34:     */ import java.util.ArrayList;
/*   35:     */ import java.util.Date;
/*   36:     */ import java.util.List;
/*   37:     */ import javax.ejb.EJB;
/*   38:     */ import javax.faces.bean.ManagedBean;
/*   39:     */ import javax.faces.bean.ViewScoped;
/*   40:     */ import javax.faces.model.SelectItem;
/*   41:     */ import org.apache.log4j.Logger;
/*   42:     */ import org.primefaces.component.datatable.DataTable;
/*   43:     */ import org.primefaces.event.SelectEvent;
/*   44:     */ import org.primefaces.model.LazyDataModel;
/*   45:     */ 
/*   46:     */ @ManagedBean
/*   47:     */ @ViewScoped
/*   48:     */ public class ConfiguracionBean
/*   49:     */   extends PageControllerAS2
/*   50:     */ {
/*   51:     */   private static final long serialVersionUID = 6370559635460208623L;
/*   52:     */   @EJB
/*   53:     */   private transient ServicioConfiguracion servicioConfiguracion;
/*   54:     */   @EJB
/*   55:     */   private transient ServicioCuentaContable servicioCuentaContable;
/*   56:     */   @EJB
/*   57:     */   private transient ServicioCentroCosto servicioCentroCosto;
/*   58:     */   @EJB
/*   59:     */   private transient ServicioTipoAsiento servicioTipoAsiento;
/*   60:     */   @EJB
/*   61:     */   private transient ServicioRubro servicioRubro;
/*   62:     */   @EJB
/*   63:     */   private transient ServicioCondicionPago servicioCondicionPago;
/*   64:     */   @EJB
/*   65:     */   private transient ServicioFormaPago servicioFormaPago;
/*   66:     */   @EJB
/*   67:     */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*   68:     */   @EJB
/*   69:     */   private transient ServicioEmpresa servicioEmpresa;
/*   70:     */   @EJB
/*   71:     */   private transient ServicioProducto servicioProducto;
/*   72:     */   @EJB
/*   73:     */   private transient ServicioConceptoRetencionSRI servicioConceptoRetencionSRI;
/*   74:     */   private Configuracion configuracion;
/*   75:     */   private LazyDataModel<Configuracion> listaConfiguracion;
/*   76:     */   private DataTable dtConfiguracion;
/*   77:     */   private boolean indicadorCuentaContable;
/*   78:     */   private boolean indicadorCentroCosto;
/*   79:     */   private boolean indicadorInteger;
/*   80:     */   private boolean indicadorString;
/*   81:     */   private boolean indicadorBoolean;
/*   82:     */   private boolean indicadorParametroEncriptarClave;
/*   83:     */   private boolean indicadorBigDecimal;
/*   84:     */   private boolean indicadorTipoAsientoInterfazVentas;
/*   85:     */   private boolean indicadorDate;
/*   86:     */   private boolean indicadorRubro;
/*   87:     */   private boolean indicadorTipoAnexoSRI;
/*   88:     */   private boolean indicadorCondicionPagoBonoAfiliacion;
/*   89:     */   private boolean indicadorFormaPagoAfiliacion;
/*   90:     */   private boolean indicadorCuentaBancariaOrganizacionAfiliacion;
/*   91:     */   private boolean indicadorClienteGenerico;
/*   92:     */   private boolean indicadorProductoFlete;
/*   93:     */   private boolean indicadorConceptoRetencionSRI;
/*   94:     */   private BigDecimal valorBigDecimal;
/*   95:     */   private boolean valorBoolean;
/*   96:     */   private int valorInteger;
/*   97:     */   private String valorCuentaContable;
/*   98:     */   private String valorCentroCosto;
/*   99:     */   private String valorString;
/*  100:     */   private String valorTipoAsientoInterfazVentas;
/*  101:     */   private Date valorDate;
/*  102:     */   private String valorRubro;
/*  103:     */   private String valorTipoAnexoSRI;
/*  104:     */   private String valorCondicionPago;
/*  105:     */   private String valorFormaPago;
/*  106:     */   private String valorCuentaBancariaOrganizacion;
/*  107:     */   private Empresa valorClienteGenerico;
/*  108:     */   private Producto valorProductoFlete;
/*  109:     */   private String valorConceptoRetencionSRI;
/*  110:     */   private List<Configuracion> listaDatosBase;
/*  111:     */   private List<Configuracion> listaGestionFinanciera;
/*  112:     */   private List<Configuracion> listaInventario;
/*  113:     */   private List<Configuracion> listaNomina;
/*  114:     */   private List<Configuracion> listaProduccion;
/*  115:     */   private List<Configuracion> listaVentas;
/*  116:     */   private List<Configuracion> listaCompras;
/*  117:     */   
/*  118:     */   public String guardar()
/*  119:     */   {
/*  120:     */     try
/*  121:     */     {
/*  122: 150 */       if (isIndicadorCuentaContable())
/*  123:     */       {
/*  124: 151 */         CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorId(Integer.valueOf(Integer.parseInt(this.valorCuentaContable)));
/*  125: 152 */         this.configuracion.setValor(this.valorCuentaContable);
/*  126: 153 */         this.configuracion.setValorMostrar(cuentaContable.getTraNombreParaMostrar());
/*  127:     */       }
/*  128: 154 */       else if (isIndicadorTipoAsientoInterfazVentas())
/*  129:     */       {
/*  130: 155 */         TipoAsiento tipoAsiento = this.servicioTipoAsiento.buscarPorId(Integer.valueOf(Integer.parseInt(this.valorTipoAsientoInterfazVentas)));
/*  131: 156 */         this.configuracion.setValor(this.valorTipoAsientoInterfazVentas);
/*  132: 157 */         this.configuracion.setValorMostrar(tipoAsiento.getNombre());
/*  133:     */       }
/*  134: 158 */       else if (isIndicadorRubro())
/*  135:     */       {
/*  136: 159 */         Rubro rubro = this.servicioRubro.buscarPorId(Integer.parseInt(this.valorRubro));
/*  137: 160 */         this.configuracion.setValor(this.valorRubro);
/*  138: 161 */         this.configuracion.setValorMostrar(rubro.getNombre());
/*  139:     */       }
/*  140: 162 */       else if (isIndicadorTipoAnexoSRI())
/*  141:     */       {
/*  142: 163 */         String valorMostrar = TipoAnexoSRI.obtenerNombreAnexoPorNombreAbreviado(this.valorTipoAnexoSRI);
/*  143: 164 */         this.configuracion.setValor(this.valorTipoAnexoSRI);
/*  144: 165 */         this.configuracion.setValorMostrar(valorMostrar);
/*  145:     */       }
/*  146: 166 */       else if (isIndicadorCondicionPagoBonoAfiliacion())
/*  147:     */       {
/*  148: 167 */         CondicionPago condicionPago = this.servicioCondicionPago.buscarPorId(Integer.valueOf(Integer.parseInt(this.valorCondicionPago)));
/*  149: 168 */         this.configuracion.setValor(this.valorCondicionPago);
/*  150: 169 */         this.configuracion.setValorMostrar(condicionPago.getNombre());
/*  151:     */       }
/*  152: 170 */       else if (isIndicadorFormaPagoAfiliacion())
/*  153:     */       {
/*  154: 171 */         FormaPago formaPago = this.servicioFormaPago.buscarPorId(Integer.valueOf(Integer.parseInt(this.valorFormaPago)));
/*  155: 172 */         this.configuracion.setValor(this.valorFormaPago);
/*  156: 173 */         this.configuracion.setValorMostrar(formaPago.getNombre());
/*  157:     */       }
/*  158: 174 */       else if (isIndicadorCuentaBancariaOrganizacionAfiliacion())
/*  159:     */       {
/*  160: 175 */         CuentaBancariaOrganizacion cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(
/*  161: 176 */           Integer.parseInt(this.valorCuentaBancariaOrganizacion));
/*  162: 177 */         this.configuracion.setValor(this.valorCuentaBancariaOrganizacion);
/*  163: 178 */         this.configuracion.setValorMostrar(cuentaBancariaOrganizacion.getNombreCompleto());
/*  164:     */       }
/*  165: 180 */       else if (isIndicadorClienteGenerico())
/*  166:     */       {
/*  167: 181 */         Empresa empresa = this.servicioEmpresa.buscarPorId(Integer.valueOf(this.valorClienteGenerico.getIdEmpresa()));
/*  168: 182 */         this.configuracion.setValor(String.valueOf(this.valorClienteGenerico.getIdEmpresa()));
/*  169: 183 */         this.configuracion.setValorMostrar(empresa.getIdentificacion() + "-" + empresa.getNombreComercial() + "-" + empresa.getNombreFiscal());
/*  170:     */       }
/*  171: 185 */       else if (isIndicadorProductoFlete())
/*  172:     */       {
/*  173: 186 */         Producto producto = this.servicioProducto.buscarPorId(this.valorProductoFlete.getId());
/*  174: 187 */         this.configuracion.setValor(String.valueOf(producto.getId()));
/*  175: 188 */         this.configuracion.setValorMostrar(producto.getCodigo() + "-" + producto.getNombre());
/*  176:     */       }
/*  177: 190 */       else if (isIndicadorConceptoRetencionSRI())
/*  178:     */       {
/*  179: 191 */         ConceptoRetencionSRI conceptoRetencionSRI = this.servicioConceptoRetencionSRI.buscarPorId(Integer.valueOf(Integer.parseInt(this.valorConceptoRetencionSRI)));
/*  180: 192 */         this.configuracion.setValor(this.valorConceptoRetencionSRI);
/*  181: 193 */         this.configuracion.setValorMostrar(conceptoRetencionSRI.getCodigo() + "-" + conceptoRetencionSRI.getNombre());
/*  182:     */       }
/*  183: 195 */       else if (isIndicadorCentroCosto())
/*  184:     */       {
/*  185: 196 */         CentroCosto centroCosto = this.servicioCentroCosto.buscarPorId(Integer.parseInt(this.valorCentroCosto));
/*  186: 197 */         this.configuracion.setValor(this.valorCentroCosto);
/*  187: 198 */         this.configuracion.setValorMostrar(centroCosto.getTraNombreParaMostrar());
/*  188:     */       }
/*  189:     */       else
/*  190:     */       {
/*  191: 200 */         if (isIndicadorBigDecimal())
/*  192:     */         {
/*  193: 201 */           this.configuracion.setValor(this.valorBigDecimal.toString());
/*  194:     */         }
/*  195: 202 */         else if (isIndicadorBoolean())
/*  196:     */         {
/*  197: 203 */           if (this.valorBoolean) {
/*  198: 204 */             this.configuracion.setValor("true");
/*  199:     */           } else {
/*  200: 206 */             this.configuracion.setValor("false");
/*  201:     */           }
/*  202:     */         }
/*  203: 208 */         else if (isIndicadorInteger())
/*  204:     */         {
/*  205: 209 */           this.configuracion.setValor(this.valorInteger + "");
/*  206:     */         }
/*  207: 210 */         else if (isIndicadorString())
/*  208:     */         {
/*  209: 211 */           this.configuracion.setValor(this.valorString);
/*  210:     */         }
/*  211: 212 */         else if (isIndicadorDate())
/*  212:     */         {
/*  213: 213 */           String fechaFormateada = FuncionesUtiles.dateToString(this.valorDate);
/*  214: 214 */           this.configuracion.setValor(fechaFormateada);
/*  215:     */         }
/*  216: 216 */         this.configuracion.setValorMostrar(this.configuracion.getValor());
/*  217:     */       }
/*  218: 218 */       this.servicioConfiguracion.guardar(this.configuracion);
/*  219:     */       
/*  220: 220 */       limpiar();
/*  221:     */       
/*  222: 222 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  223:     */     }
/*  224:     */     catch (Exception e)
/*  225:     */     {
/*  226: 224 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  227: 225 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  228: 226 */       e.printStackTrace();
/*  229:     */     }
/*  230: 228 */     return "";
/*  231:     */   }
/*  232:     */   
/*  233:     */   public String cargarDatos()
/*  234:     */   {
/*  235: 238 */     return "";
/*  236:     */   }
/*  237:     */   
/*  238:     */   public List<Configuracion> getListaCompras()
/*  239:     */   {
/*  240: 245 */     this.listaCompras = this.servicioConfiguracion.listaConfiguracionPorModulo(ModuloEnum.COMPRAS.getNombre(), AppUtil.getOrganizacion()
/*  241: 246 */       .getIdOrganizacion());
/*  242: 247 */     return this.listaCompras;
/*  243:     */   }
/*  244:     */   
/*  245:     */   public void setListaCompras(List<Configuracion> listaCompras)
/*  246:     */   {
/*  247: 251 */     this.listaCompras = listaCompras;
/*  248:     */   }
/*  249:     */   
/*  250:     */   public List<Configuracion> getListaDatosBase()
/*  251:     */   {
/*  252: 258 */     this.listaDatosBase = this.servicioConfiguracion.listaConfiguracionPorModulo(ModuloEnum.DATOS_BASE.getNombre(), AppUtil.getOrganizacion()
/*  253: 259 */       .getIdOrganizacion());
/*  254: 260 */     return this.listaDatosBase;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public void setListaDatosBase(List<Configuracion> listaDatosBase)
/*  258:     */   {
/*  259: 264 */     this.listaDatosBase = listaDatosBase;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public List<Configuracion> getListaGestionFinanciera()
/*  263:     */   {
/*  264: 272 */     this.listaGestionFinanciera = this.servicioConfiguracion.listaConfiguracionPorModulo(ModuloEnum.GESTION_FINANCIERA.getNombre(), 
/*  265: 273 */       AppUtil.getOrganizacion().getIdOrganizacion());
/*  266: 274 */     return this.listaGestionFinanciera;
/*  267:     */   }
/*  268:     */   
/*  269:     */   public void setListaGestionFinanciera(List<Configuracion> listaGestionFinanciera)
/*  270:     */   {
/*  271: 278 */     this.listaGestionFinanciera = listaGestionFinanciera;
/*  272:     */   }
/*  273:     */   
/*  274:     */   public List<Configuracion> getListaInventario()
/*  275:     */   {
/*  276: 285 */     this.listaInventario = this.servicioConfiguracion.listaConfiguracionPorModulo(ModuloEnum.INVENTARIO.getNombre(), AppUtil.getOrganizacion()
/*  277: 286 */       .getIdOrganizacion());
/*  278: 287 */     return this.listaInventario;
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void setListaInventario(List<Configuracion> listaInventario)
/*  282:     */   {
/*  283: 291 */     this.listaInventario = listaInventario;
/*  284:     */   }
/*  285:     */   
/*  286:     */   public List<Configuracion> getListaNomina()
/*  287:     */   {
/*  288: 299 */     this.listaNomina = this.servicioConfiguracion.listaConfiguracionPorModulo(ModuloEnum.NOMINA.getNombre(), AppUtil.getOrganizacion().getIdOrganizacion());
/*  289: 300 */     return this.listaNomina;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public void setListaNomina(List<Configuracion> listaNomina)
/*  293:     */   {
/*  294: 304 */     this.listaNomina = listaNomina;
/*  295:     */   }
/*  296:     */   
/*  297:     */   public List<Configuracion> getListaProduccion()
/*  298:     */   {
/*  299: 311 */     this.listaProduccion = this.servicioConfiguracion.listaConfiguracionPorModulo(ModuloEnum.PRODUCCION.getNombre(), AppUtil.getOrganizacion()
/*  300: 312 */       .getIdOrganizacion());
/*  301: 313 */     return this.listaProduccion;
/*  302:     */   }
/*  303:     */   
/*  304:     */   public void setListaProduccion(List<Configuracion> listaProduccion)
/*  305:     */   {
/*  306: 317 */     this.listaProduccion = listaProduccion;
/*  307:     */   }
/*  308:     */   
/*  309:     */   public List<Configuracion> getListaVentas()
/*  310:     */   {
/*  311: 324 */     this.listaVentas = this.servicioConfiguracion.listaConfiguracionPorModulo(ModuloEnum.VENTAS.getNombre(), AppUtil.getOrganizacion().getIdOrganizacion());
/*  312: 325 */     return this.listaVentas;
/*  313:     */   }
/*  314:     */   
/*  315:     */   public void setListaVentas(List<Configuracion> listaVentas)
/*  316:     */   {
/*  317: 329 */     this.listaVentas = listaVentas;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public void nuevo()
/*  321:     */   {
/*  322: 336 */     setConfiguracion(new Configuracion());
/*  323:     */   }
/*  324:     */   
/*  325:     */   public ServicioConfiguracion getServicioConfiguracion()
/*  326:     */   {
/*  327: 345 */     return this.servicioConfiguracion;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public void setServicioConfiguracion(ServicioConfiguracion servicioConfiguracion)
/*  331:     */   {
/*  332: 355 */     this.servicioConfiguracion = servicioConfiguracion;
/*  333:     */   }
/*  334:     */   
/*  335:     */   public Configuracion getConfiguracion()
/*  336:     */   {
/*  337: 364 */     if (this.configuracion == null) {
/*  338: 365 */       this.configuracion = new Configuracion();
/*  339:     */     }
/*  340: 368 */     return this.configuracion;
/*  341:     */   }
/*  342:     */   
/*  343:     */   public void setConfiguracion(Configuracion configuracion)
/*  344:     */   {
/*  345: 378 */     this.configuracion = configuracion;
/*  346:     */   }
/*  347:     */   
/*  348:     */   public LazyDataModel<Configuracion> getListaConfiguracion()
/*  349:     */   {
/*  350: 387 */     return this.listaConfiguracion;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public void setListaConfiguracion(LazyDataModel<Configuracion> listaConfiguracion)
/*  354:     */   {
/*  355: 397 */     this.listaConfiguracion = listaConfiguracion;
/*  356:     */   }
/*  357:     */   
/*  358:     */   public DataTable getDtConfiguracion()
/*  359:     */   {
/*  360: 406 */     return this.dtConfiguracion;
/*  361:     */   }
/*  362:     */   
/*  363:     */   public List<Empresa> autocompletarClientes(String consulta)
/*  364:     */   {
/*  365: 416 */     List<Empresa> lista = this.servicioEmpresa.autocompletarClientes(consulta);
/*  366: 417 */     return lista;
/*  367:     */   }
/*  368:     */   
/*  369:     */   public List<Producto> autocompletarProducto(String consulta)
/*  370:     */   {
/*  371: 421 */     List<Producto> lista = this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta);
/*  372: 422 */     return lista;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public void setDtConfiguracion(DataTable dtConfiguracion)
/*  376:     */   {
/*  377: 432 */     this.dtConfiguracion = dtConfiguracion;
/*  378:     */   }
/*  379:     */   
/*  380:     */   public boolean isIndicadorCuentaContable()
/*  381:     */   {
/*  382: 436 */     return this.indicadorCuentaContable;
/*  383:     */   }
/*  384:     */   
/*  385:     */   public void setIndicadorCuentaContable(boolean indicadorCuentaContable)
/*  386:     */   {
/*  387: 440 */     this.indicadorCuentaContable = indicadorCuentaContable;
/*  388:     */   }
/*  389:     */   
/*  390:     */   public boolean isIndicadorInteger()
/*  391:     */   {
/*  392: 444 */     return this.indicadorInteger;
/*  393:     */   }
/*  394:     */   
/*  395:     */   public void setIndicadorInteger(boolean indicadorInteger)
/*  396:     */   {
/*  397: 448 */     this.indicadorInteger = indicadorInteger;
/*  398:     */   }
/*  399:     */   
/*  400:     */   public boolean isIndicadorString()
/*  401:     */   {
/*  402: 452 */     return this.indicadorString;
/*  403:     */   }
/*  404:     */   
/*  405:     */   public void setIndicadorString(boolean indicadorString)
/*  406:     */   {
/*  407: 456 */     this.indicadorString = indicadorString;
/*  408:     */   }
/*  409:     */   
/*  410:     */   public boolean isIndicadorBoolean()
/*  411:     */   {
/*  412: 460 */     return this.indicadorBoolean;
/*  413:     */   }
/*  414:     */   
/*  415:     */   public void setIndicadorBoolean(boolean indicadorBoolean)
/*  416:     */   {
/*  417: 464 */     this.indicadorBoolean = indicadorBoolean;
/*  418:     */   }
/*  419:     */   
/*  420:     */   public boolean isIndicadorBigDecimal()
/*  421:     */   {
/*  422: 468 */     return this.indicadorBigDecimal;
/*  423:     */   }
/*  424:     */   
/*  425:     */   public void setIndicadorBigDecimal(boolean indicadorBigDecimal)
/*  426:     */   {
/*  427: 472 */     this.indicadorBigDecimal = indicadorBigDecimal;
/*  428:     */   }
/*  429:     */   
/*  430:     */   public BigDecimal getValorBigDecimal()
/*  431:     */   {
/*  432: 476 */     return this.valorBigDecimal;
/*  433:     */   }
/*  434:     */   
/*  435:     */   public void setValorBigDecimal(BigDecimal valorBigDecimal)
/*  436:     */   {
/*  437: 480 */     this.valorBigDecimal = valorBigDecimal;
/*  438:     */   }
/*  439:     */   
/*  440:     */   public boolean isValorBoolean()
/*  441:     */   {
/*  442: 484 */     return this.valorBoolean;
/*  443:     */   }
/*  444:     */   
/*  445:     */   public void setValorBoolean(boolean valorBoolean)
/*  446:     */   {
/*  447: 488 */     this.valorBoolean = valorBoolean;
/*  448:     */   }
/*  449:     */   
/*  450:     */   public int getValorInteger()
/*  451:     */   {
/*  452: 492 */     return this.valorInteger;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public void setValorInteger(int valorInteger)
/*  456:     */   {
/*  457: 496 */     this.valorInteger = valorInteger;
/*  458:     */   }
/*  459:     */   
/*  460:     */   public String getValorCuentaContable()
/*  461:     */   {
/*  462: 500 */     return this.valorCuentaContable;
/*  463:     */   }
/*  464:     */   
/*  465:     */   public void setValorCuentaContable(String valorCuentaContable)
/*  466:     */   {
/*  467: 504 */     this.valorCuentaContable = valorCuentaContable;
/*  468:     */   }
/*  469:     */   
/*  470:     */   public String getValorString()
/*  471:     */   {
/*  472: 508 */     return this.valorString;
/*  473:     */   }
/*  474:     */   
/*  475:     */   public void setValorString(String valorString)
/*  476:     */   {
/*  477: 512 */     this.valorString = valorString;
/*  478:     */   }
/*  479:     */   
/*  480:     */   public String editar()
/*  481:     */   {
/*  482: 522 */     if (getConfiguracion().getId() > 0)
/*  483:     */     {
/*  484: 523 */       limpiarIndicadores();
/*  485: 524 */       if (this.configuracion.getParametro().getTipoDato().equals("CuentaContable"))
/*  486:     */       {
/*  487: 525 */         setIndicadorCuentaContable(true);
/*  488: 526 */         setValorCuentaContable(this.configuracion.getValor());
/*  489:     */       }
/*  490: 527 */       else if (this.configuracion.getParametro().getTipoDato().equals("Rubro"))
/*  491:     */       {
/*  492: 528 */         setIndicadorRubro(true);
/*  493: 529 */         setValorRubro(this.configuracion.getValor());
/*  494:     */       }
/*  495: 530 */       else if (this.configuracion.getParametro().getTipoDato().equals("Integer"))
/*  496:     */       {
/*  497: 531 */         setIndicadorInteger(true);
/*  498: 532 */         setValorInteger(Integer.parseInt(this.configuracion.getValor()));
/*  499:     */       }
/*  500: 533 */       else if (this.configuracion.getParametro().getTipoDato().equals("String"))
/*  501:     */       {
/*  502: 534 */         setIndicadorString(true);
/*  503: 535 */         setValorString(this.configuracion.getValor());
/*  504:     */       }
/*  505: 536 */       else if (this.configuracion.getParametro().getTipoDato().equals("Boolean"))
/*  506:     */       {
/*  507: 537 */         setIndicadorBoolean(true);
/*  508: 538 */         setValorBoolean(this.configuracion.getValor().trim().equals("true"));
/*  509:     */       }
/*  510: 539 */       else if (this.configuracion.getParametro().getTipoDato().equals("BigDecimal"))
/*  511:     */       {
/*  512: 540 */         setIndicadorBigDecimal(true);
/*  513: 541 */         double valor = Double.parseDouble(this.configuracion.getValor());
/*  514: 542 */         setValorBigDecimal(BigDecimal.valueOf(valor));
/*  515:     */       }
/*  516: 543 */       else if (this.configuracion.getParametro().getTipoDato().equals("TipoAsiento"))
/*  517:     */       {
/*  518: 544 */         setIndicadorTipoAsientoInterfazVentas(true);
/*  519: 545 */         setValorTipoAsientoInterfazVentas(this.configuracion.getValor());
/*  520:     */       }
/*  521: 546 */       else if (this.configuracion.getParametro().getTipoDato().equals("Date"))
/*  522:     */       {
/*  523: 547 */         setIndicadorDate(true);
/*  524:     */         
/*  525: 549 */         Date fecha = FuncionesUtiles.stringToDate(this.configuracion.getValor());
/*  526: 550 */         setValorDate(fecha);
/*  527:     */       }
/*  528: 551 */       else if (this.configuracion.getParametro().getTipoDato().equals("TipoAnexoSRI"))
/*  529:     */       {
/*  530: 552 */         setIndicadorTipoAnexoSRI(true);
/*  531: 553 */         setValorTipoAnexoSRI(this.configuracion.getValor());
/*  532:     */       }
/*  533: 554 */       else if (this.configuracion.getParametro().getTipoDato().equals("CondicionPago"))
/*  534:     */       {
/*  535: 555 */         setIndicadorCondicionPagoBonoAfiliacion(true);
/*  536: 556 */         setValorCondicionPago(this.configuracion.getValor());
/*  537:     */       }
/*  538: 557 */       else if (this.configuracion.getParametro().getTipoDato().equals("FormaPago"))
/*  539:     */       {
/*  540: 558 */         setIndicadorFormaPagoAfiliacion(true);
/*  541: 559 */         setValorFormaPago(this.configuracion.getValor());
/*  542:     */       }
/*  543: 560 */       else if (this.configuracion.getParametro().getTipoDato().equals("CuentaBancariaOrganizacion"))
/*  544:     */       {
/*  545: 561 */         setIndicadorCuentaBancariaOrganizacionAfiliacion(true);
/*  546: 562 */         setValorCuentaBancariaOrganizacion(this.configuracion.getValor());
/*  547:     */       }
/*  548: 564 */       else if (this.configuracion.getParametro().getTipoDato().equals("ConceptoRetencionSRI"))
/*  549:     */       {
/*  550: 565 */         setIndicadorConceptoRetencionSRI(true);
/*  551: 566 */         setValorConceptoRetencionSRI(this.configuracion.getValor());
/*  552:     */       }
/*  553: 567 */       else if (this.configuracion.getParametro().getTipoDato().equals("CentroCosto"))
/*  554:     */       {
/*  555: 568 */         setIndicadorCentroCosto(true);
/*  556: 569 */         setValorCentroCosto(this.configuracion.getValor());
/*  557:     */       }
/*  558: 570 */       else if (this.configuracion.getParametro().getTipoDato().equals("Empresa"))
/*  559:     */       {
/*  560: 572 */         setIndicadorClienteGenerico(true);
/*  561:     */         try
/*  562:     */         {
/*  563: 575 */           setValorClienteGenerico(this.servicioEmpresa.buscarPorId(Integer.valueOf(Integer.parseInt(this.configuracion.getValor()))));
/*  564:     */         }
/*  565:     */         catch (NumberFormatException e)
/*  566:     */         {
/*  567: 577 */           setValorClienteGenerico(null);
/*  568:     */         }
/*  569:     */       }
/*  570: 580 */       else if (this.configuracion.getParametro().getTipoDato().equals("Producto"))
/*  571:     */       {
/*  572: 582 */         setIndicadorProductoFlete(true);
/*  573:     */         try
/*  574:     */         {
/*  575: 585 */           setValorProductoFlete(this.servicioProducto.buscarPorId(Integer.parseInt(this.configuracion.getValor())));
/*  576:     */         }
/*  577:     */         catch (NumberFormatException e)
/*  578:     */         {
/*  579: 587 */           setValorProductoFlete(null);
/*  580:     */         }
/*  581:     */       }
/*  582: 591 */       if (this.configuracion.getParametro().equals(Parametro.INDICADOR_ENCRIPTAR_PASSWORD)) {
/*  583: 592 */         this.indicadorParametroEncriptarClave = true;
/*  584:     */       } else {
/*  585: 594 */         this.indicadorParametroEncriptarClave = false;
/*  586:     */       }
/*  587: 597 */       setEditado(true);
/*  588:     */     }
/*  589:     */     else
/*  590:     */     {
/*  591: 599 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  592:     */     }
/*  593: 601 */     return "";
/*  594:     */   }
/*  595:     */   
/*  596:     */   private void limpiarIndicadores()
/*  597:     */   {
/*  598: 605 */     setIndicadorBoolean(false);
/*  599: 606 */     setIndicadorCuentaContable(false);
/*  600: 607 */     setIndicadorCentroCosto(false);
/*  601: 608 */     setIndicadorTipoAsientoInterfazVentas(false);
/*  602: 609 */     setIndicadorInteger(false);
/*  603: 610 */     setIndicadorString(false);
/*  604: 611 */     setIndicadorBigDecimal(false);
/*  605: 612 */     setIndicadorRubro(false);
/*  606: 613 */     setIndicadorCondicionPagoBonoAfiliacion(false);
/*  607: 614 */     setIndicadorFormaPagoAfiliacion(false);
/*  608: 615 */     setIndicadorCuentaBancariaOrganizacionAfiliacion(false);
/*  609: 616 */     setIndicadorClienteGenerico(false);
/*  610: 617 */     setIndicadorProductoFlete(false);
/*  611: 618 */     setValorBigDecimal(BigDecimal.ZERO);
/*  612: 619 */     setValorBoolean(false);
/*  613: 620 */     setValorCuentaContable("");
/*  614: 621 */     setValorCentroCosto("");
/*  615: 622 */     setValorTipoAsientoInterfazVentas("");
/*  616: 623 */     setValorInteger(0);
/*  617: 624 */     setValorString("");
/*  618: 625 */     setValorDate(null);
/*  619: 626 */     setValorRubro("");
/*  620: 627 */     setValorClienteGenerico(null);
/*  621: 628 */     setValorProductoFlete(null);
/*  622: 629 */     setIndicadorConceptoRetencionSRI(false);
/*  623: 630 */     setIndicadorDate(false);
/*  624: 631 */     setIndicadorTipoAnexoSRI(false);
/*  625:     */   }
/*  626:     */   
/*  627:     */   public String limpiar()
/*  628:     */   {
/*  629: 641 */     setEditado(false);
/*  630: 642 */     this.configuracion = new Configuracion();
/*  631:     */     
/*  632: 644 */     limpiarIndicadores();
/*  633: 645 */     this.servicioConfiguracion.cargarConfiguracion(Integer.valueOf(AppUtil.getOrganizacion().getId()));
/*  634:     */     
/*  635: 647 */     return "";
/*  636:     */   }
/*  637:     */   
/*  638:     */   public String eliminar()
/*  639:     */   {
/*  640: 657 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  641: 658 */     return "";
/*  642:     */   }
/*  643:     */   
/*  644:     */   public String crear()
/*  645:     */   {
/*  646: 668 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  647: 669 */     return "";
/*  648:     */   }
/*  649:     */   
/*  650:     */   public ServicioCuentaContable getServicioCuentaContable()
/*  651:     */   {
/*  652: 673 */     return this.servicioCuentaContable;
/*  653:     */   }
/*  654:     */   
/*  655:     */   public void setServicioCuentaContable(ServicioCuentaContable servicioCuentaContable)
/*  656:     */   {
/*  657: 677 */     this.servicioCuentaContable = servicioCuentaContable;
/*  658:     */   }
/*  659:     */   
/*  660:     */   public boolean isIndicadorTipoAsientoInterfazVentas()
/*  661:     */   {
/*  662: 681 */     return this.indicadorTipoAsientoInterfazVentas;
/*  663:     */   }
/*  664:     */   
/*  665:     */   public void setIndicadorTipoAsientoInterfazVentas(boolean indicadorTipoAsientoInterfazVentas)
/*  666:     */   {
/*  667: 685 */     this.indicadorTipoAsientoInterfazVentas = indicadorTipoAsientoInterfazVentas;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public String getValorTipoAsientoInterfazVentas()
/*  671:     */   {
/*  672: 689 */     return this.valorTipoAsientoInterfazVentas;
/*  673:     */   }
/*  674:     */   
/*  675:     */   public void setValorTipoAsientoInterfazVentas(String valorTipoAsientoInterfazVentas)
/*  676:     */   {
/*  677: 693 */     this.valorTipoAsientoInterfazVentas = valorTipoAsientoInterfazVentas;
/*  678:     */   }
/*  679:     */   
/*  680:     */   public boolean isIndicadorDate()
/*  681:     */   {
/*  682: 702 */     return this.indicadorDate;
/*  683:     */   }
/*  684:     */   
/*  685:     */   public void setIndicadorDate(boolean indicadorDate)
/*  686:     */   {
/*  687: 712 */     this.indicadorDate = indicadorDate;
/*  688:     */   }
/*  689:     */   
/*  690:     */   public Date getValorDate()
/*  691:     */   {
/*  692: 721 */     return this.valorDate;
/*  693:     */   }
/*  694:     */   
/*  695:     */   public void setValorDate(Date valorDate)
/*  696:     */   {
/*  697: 731 */     this.valorDate = valorDate;
/*  698:     */   }
/*  699:     */   
/*  700:     */   public boolean isIndicadorRubro()
/*  701:     */   {
/*  702: 740 */     return this.indicadorRubro;
/*  703:     */   }
/*  704:     */   
/*  705:     */   public void setIndicadorRubro(boolean indicadorRubro)
/*  706:     */   {
/*  707: 750 */     this.indicadorRubro = indicadorRubro;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public String getValorRubro()
/*  711:     */   {
/*  712: 759 */     return this.valorRubro;
/*  713:     */   }
/*  714:     */   
/*  715:     */   public void setValorRubro(String valorRubro)
/*  716:     */   {
/*  717: 769 */     this.valorRubro = valorRubro;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public boolean isIndicadorTipoAnexoSRI()
/*  721:     */   {
/*  722: 778 */     return this.indicadorTipoAnexoSRI;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void setIndicadorTipoAnexoSRI(boolean indicadorTipoAnexoSRI)
/*  726:     */   {
/*  727: 788 */     this.indicadorTipoAnexoSRI = indicadorTipoAnexoSRI;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public String getValorTipoAnexoSRI()
/*  731:     */   {
/*  732: 797 */     return this.valorTipoAnexoSRI;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public void setValorTipoAnexoSRI(String valorTipoAnexoSRI)
/*  736:     */   {
/*  737: 807 */     this.valorTipoAnexoSRI = valorTipoAnexoSRI;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public List<SelectItem> getListaTipoAnexos()
/*  741:     */   {
/*  742: 816 */     List<SelectItem> lista = new ArrayList();
/*  743: 817 */     for (TipoAnexoSRI tipoAnexoSRI : TipoAnexoSRI.values()) {
/*  744: 818 */       lista.add(new SelectItem(tipoAnexoSRI.getNombreAbreviado(), tipoAnexoSRI.getNombreAnexo()));
/*  745:     */     }
/*  746: 821 */     return lista;
/*  747:     */   }
/*  748:     */   
/*  749:     */   public boolean isIndicadorCondicionPagoBonoAfiliacion()
/*  750:     */   {
/*  751: 830 */     return this.indicadorCondicionPagoBonoAfiliacion;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public void setIndicadorCondicionPagoBonoAfiliacion(boolean indicadorCondicionPagoBonoAfiliacion)
/*  755:     */   {
/*  756: 840 */     this.indicadorCondicionPagoBonoAfiliacion = indicadorCondicionPagoBonoAfiliacion;
/*  757:     */   }
/*  758:     */   
/*  759:     */   public String getValorCondicionPago()
/*  760:     */   {
/*  761: 849 */     return this.valorCondicionPago;
/*  762:     */   }
/*  763:     */   
/*  764:     */   public void setValorCondicionPago(String valorCondicionPago)
/*  765:     */   {
/*  766: 859 */     this.valorCondicionPago = valorCondicionPago;
/*  767:     */   }
/*  768:     */   
/*  769:     */   public boolean isIndicadorFormaPagoAfiliacion()
/*  770:     */   {
/*  771: 868 */     return this.indicadorFormaPagoAfiliacion;
/*  772:     */   }
/*  773:     */   
/*  774:     */   public void setIndicadorFormaPagoAfiliacion(boolean indicadorFormaPagoAfiliacion)
/*  775:     */   {
/*  776: 878 */     this.indicadorFormaPagoAfiliacion = indicadorFormaPagoAfiliacion;
/*  777:     */   }
/*  778:     */   
/*  779:     */   public String getValorFormaPago()
/*  780:     */   {
/*  781: 887 */     return this.valorFormaPago;
/*  782:     */   }
/*  783:     */   
/*  784:     */   public void setValorFormaPago(String valorFormaPago)
/*  785:     */   {
/*  786: 897 */     this.valorFormaPago = valorFormaPago;
/*  787:     */   }
/*  788:     */   
/*  789:     */   public boolean isIndicadorCuentaBancariaOrganizacionAfiliacion()
/*  790:     */   {
/*  791: 906 */     return this.indicadorCuentaBancariaOrganizacionAfiliacion;
/*  792:     */   }
/*  793:     */   
/*  794:     */   public void setIndicadorCuentaBancariaOrganizacionAfiliacion(boolean indicadorCuentaBancariaOrganizacionAfiliacion)
/*  795:     */   {
/*  796: 916 */     this.indicadorCuentaBancariaOrganizacionAfiliacion = indicadorCuentaBancariaOrganizacionAfiliacion;
/*  797:     */   }
/*  798:     */   
/*  799:     */   public String getValorCuentaBancariaOrganizacion()
/*  800:     */   {
/*  801: 925 */     return this.valorCuentaBancariaOrganizacion;
/*  802:     */   }
/*  803:     */   
/*  804:     */   public void setValorCuentaBancariaOrganizacion(String valorCuentaBancariaOrganizacion)
/*  805:     */   {
/*  806: 935 */     this.valorCuentaBancariaOrganizacion = valorCuentaBancariaOrganizacion;
/*  807:     */   }
/*  808:     */   
/*  809:     */   public boolean isIndicadorClienteGenerico()
/*  810:     */   {
/*  811: 942 */     return this.indicadorClienteGenerico;
/*  812:     */   }
/*  813:     */   
/*  814:     */   public void setIndicadorClienteGenerico(boolean indicadorClienteGenerico)
/*  815:     */   {
/*  816: 950 */     this.indicadorClienteGenerico = indicadorClienteGenerico;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public Empresa getValorClienteGenerico()
/*  820:     */   {
/*  821: 957 */     return this.valorClienteGenerico;
/*  822:     */   }
/*  823:     */   
/*  824:     */   public void setValorClienteGenerico(Empresa valorClienteGenerico)
/*  825:     */   {
/*  826: 965 */     this.valorClienteGenerico = valorClienteGenerico;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public Producto getValorProductoFlete()
/*  830:     */   {
/*  831: 969 */     return this.valorProductoFlete;
/*  832:     */   }
/*  833:     */   
/*  834:     */   public void setValorProductoFlete(Producto valorProductoFlete)
/*  835:     */   {
/*  836: 973 */     this.valorProductoFlete = valorProductoFlete;
/*  837:     */   }
/*  838:     */   
/*  839:     */   public boolean isIndicadorProductoFlete()
/*  840:     */   {
/*  841: 977 */     return this.indicadorProductoFlete;
/*  842:     */   }
/*  843:     */   
/*  844:     */   public void setIndicadorProductoFlete(boolean indicadorProductoFlete)
/*  845:     */   {
/*  846: 981 */     this.indicadorProductoFlete = indicadorProductoFlete;
/*  847:     */   }
/*  848:     */   
/*  849:     */   public boolean isIndicadorConceptoRetencionSRI()
/*  850:     */   {
/*  851: 990 */     return this.indicadorConceptoRetencionSRI;
/*  852:     */   }
/*  853:     */   
/*  854:     */   public void setIndicadorConceptoRetencionSRI(boolean indicadorConceptoRetencionSRI)
/*  855:     */   {
/*  856:1000 */     this.indicadorConceptoRetencionSRI = indicadorConceptoRetencionSRI;
/*  857:     */   }
/*  858:     */   
/*  859:     */   public String getValorConceptoRetencionSRI()
/*  860:     */   {
/*  861:1009 */     return this.valorConceptoRetencionSRI;
/*  862:     */   }
/*  863:     */   
/*  864:     */   public void setValorConceptoRetencionSRI(String valorConceptoRetencionSRI)
/*  865:     */   {
/*  866:1019 */     this.valorConceptoRetencionSRI = valorConceptoRetencionSRI;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public boolean isIndicadorCentroCosto()
/*  870:     */   {
/*  871:1028 */     return this.indicadorCentroCosto;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void setIndicadorCentroCosto(boolean indicadorCentroCosto)
/*  875:     */   {
/*  876:1038 */     this.indicadorCentroCosto = indicadorCentroCosto;
/*  877:     */   }
/*  878:     */   
/*  879:     */   public String getValorCentroCosto()
/*  880:     */   {
/*  881:1047 */     return this.valorCentroCosto;
/*  882:     */   }
/*  883:     */   
/*  884:     */   public void setValorCentroCosto(String valorCentroCosto)
/*  885:     */   {
/*  886:1057 */     this.valorCentroCosto = valorCentroCosto;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public boolean isIndicadorParametroEncriptarClave()
/*  890:     */   {
/*  891:1061 */     return this.indicadorParametroEncriptarClave;
/*  892:     */   }
/*  893:     */   
/*  894:     */   public void setIndicadorParametroEncriptarClave(boolean indicadorParametroEncriptarClave)
/*  895:     */   {
/*  896:1065 */     this.indicadorParametroEncriptarClave = indicadorParametroEncriptarClave;
/*  897:     */   }
/*  898:     */   
/*  899:     */   public void onRowSelect(SelectEvent event)
/*  900:     */   {
/*  901:1069 */     this.configuracion = ((Configuracion)event.getObject());
/*  902:     */   }
/*  903:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.ConfiguracionBean
 * JD-Core Version:    0.7.0.1
 */