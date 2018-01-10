/*    1:     */ package com.asinfo.as2.nomina.configuracion;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.controller.TipoCuentaBancariaBean;
/*    4:     */ import com.asinfo.as2.configuracionbase.controller.TipoIdentificacionBean;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*    6:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*    7:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioParroquia;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioProvincia;
/*    9:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   10:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoCuentaBancaria;
/*   11:     */ import com.asinfo.as2.controller.LanguageController;
/*   12:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*   13:     */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   14:     */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   15:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   16:     */ import com.asinfo.as2.datosbase.servicio.ServicioTipoContacto;
/*   17:     */ import com.asinfo.as2.entities.Banco;
/*   18:     */ import com.asinfo.as2.entities.CargoEmpleado;
/*   19:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   20:     */ import com.asinfo.as2.entities.CategoriaRubro;
/*   21:     */ import com.asinfo.as2.entities.Ciudad;
/*   22:     */ import com.asinfo.as2.entities.Contacto;
/*   23:     */ import com.asinfo.as2.entities.CuentaBancaria;
/*   24:     */ import com.asinfo.as2.entities.CuentaBancariaEmpresa;
/*   25:     */ import com.asinfo.as2.entities.Departamento;
/*   26:     */ import com.asinfo.as2.entities.DetalleDocumentoDigitalizado;
/*   27:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   28:     */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*   29:     */ import com.asinfo.as2.entities.DocumentoDigitalizadoDepartamento;
/*   30:     */ import com.asinfo.as2.entities.DotacionEmpleado;
/*   31:     */ import com.asinfo.as2.entities.Empleado;
/*   32:     */ import com.asinfo.as2.entities.Empresa;
/*   33:     */ import com.asinfo.as2.entities.EstadoCivil;
/*   34:     */ import com.asinfo.as2.entities.FormacionAcademica;
/*   35:     */ import com.asinfo.as2.entities.InstitucionEducativa;
/*   36:     */ import com.asinfo.as2.entities.LlamadoAtencion;
/*   37:     */ import com.asinfo.as2.entities.NivelInstruccion;
/*   38:     */ import com.asinfo.as2.entities.Organizacion;
/*   39:     */ import com.asinfo.as2.entities.Pais;
/*   40:     */ import com.asinfo.as2.entities.Parroquia;
/*   41:     */ import com.asinfo.as2.entities.Producto;
/*   42:     */ import com.asinfo.as2.entities.Provincia;
/*   43:     */ import com.asinfo.as2.entities.Sucursal;
/*   44:     */ import com.asinfo.as2.entities.TipoContacto;
/*   45:     */ import com.asinfo.as2.entities.TipoContrato;
/*   46:     */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*   47:     */ import com.asinfo.as2.entities.TipoDiscapacidad;
/*   48:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   49:     */ import com.asinfo.as2.entities.Titulo;
/*   50:     */ import com.asinfo.as2.entities.Ubicacion;
/*   51:     */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*   52:     */ import com.asinfo.as2.enumeraciones.CondicionRespectoDiscapacidad;
/*   53:     */ import com.asinfo.as2.enumeraciones.ConvenioDobleImposicion;
/*   54:     */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*   55:     */ import com.asinfo.as2.enumeraciones.Genero;
/*   56:     */ import com.asinfo.as2.enumeraciones.Mes;
/*   57:     */ import com.asinfo.as2.enumeraciones.ResidenciaTrabajador;
/*   58:     */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*   59:     */ import com.asinfo.as2.enumeraciones.TipoIdentificacionSRI;
/*   60:     */ import com.asinfo.as2.enumeraciones.TipoSangre;
/*   61:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   62:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   63:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   64:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCargoEmpleado;
/*   65:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDetalleDocumentoDigitalizado;
/*   66:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEstadoCivil;
/*   67:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioInstitucionEducativa;
/*   68:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioMotivoLlamadoAtencion;
/*   69:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioNivelInstruccion;
/*   70:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoContrato;
/*   71:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoDiscapacidad;
/*   72:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTitulo;
/*   73:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   74:     */ import com.asinfo.as2.util.AppUtil;
/*   75:     */ import com.asinfo.as2.util.RutaArchivo;
/*   76:     */ import com.asinfo.as2.utils.DatosSRI;
/*   77:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   78:     */ import com.asinfo.as2.utils.JsfUtil;
/*   79:     */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*   80:     */ import java.io.BufferedInputStream;
/*   81:     */ import java.io.File;
/*   82:     */ import java.io.FileOutputStream;
/*   83:     */ import java.io.InputStream;
/*   84:     */ import java.io.PrintStream;
/*   85:     */ import java.util.ArrayList;
/*   86:     */ import java.util.Calendar;
/*   87:     */ import java.util.Date;
/*   88:     */ import java.util.HashMap;
/*   89:     */ import java.util.List;
/*   90:     */ import java.util.Map;
/*   91:     */ import javax.annotation.PostConstruct;
/*   92:     */ import javax.ejb.EJB;
/*   93:     */ import javax.faces.bean.ManagedBean;
/*   94:     */ import javax.faces.bean.ManagedProperty;
/*   95:     */ import javax.faces.bean.ViewScoped;
/*   96:     */ import javax.faces.model.SelectItem;
/*   97:     */ import org.apache.log4j.Logger;
/*   98:     */ import org.primefaces.component.datatable.DataTable;
/*   99:     */ import org.primefaces.component.tabview.TabView;
/*  100:     */ import org.primefaces.context.RequestContext;
/*  101:     */ import org.primefaces.event.FileUploadEvent;
/*  102:     */ import org.primefaces.model.LazyDataModel;
/*  103:     */ import org.primefaces.model.SortOrder;
/*  104:     */ import org.primefaces.model.StreamedContent;
/*  105:     */ import org.primefaces.model.TreeNode;
/*  106:     */ import org.primefaces.model.UploadedFile;
/*  107:     */ 
/*  108:     */ @ManagedBean
/*  109:     */ @ViewScoped
/*  110:     */ public class EmpleadoBean
/*  111:     */   extends PageControllerAS2
/*  112:     */ {
/*  113:     */   private static final long serialVersionUID = 5928599170604257612L;
/*  114:     */   @EJB
/*  115:     */   protected ServicioEmpresa servicioEmpresa;
/*  116:     */   @EJB
/*  117:     */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  118:     */   @EJB
/*  119:     */   private ServicioCargoEmpleado servicioCargoEmpleado;
/*  120:     */   @EJB
/*  121:     */   protected ServicioDepartamento servicioDepartamento;
/*  122:     */   @EJB
/*  123:     */   private ServicioEstadoCivil servicioEstadoCivil;
/*  124:     */   @EJB
/*  125:     */   private ServicioTitulo servicioTitulo;
/*  126:     */   @EJB
/*  127:     */   private ServicioTipoDiscapacidad servicioTipoDiscapacidad;
/*  128:     */   @EJB
/*  129:     */   private ServicioPais servicioPais;
/*  130:     */   @EJB
/*  131:     */   private ServicioGenerico<Banco> servicioBanco;
/*  132:     */   @EJB
/*  133:     */   private ServicioProvincia servicioProvincia;
/*  134:     */   @EJB
/*  135:     */   private ServicioCiudad servicioCiudad;
/*  136:     */   @EJB
/*  137:     */   private ServicioTipoContrato servicioTipoContrato;
/*  138:     */   @EJB
/*  139:     */   private ServicioTipoCuentaBancaria servicioTipoCuentaBancaria;
/*  140:     */   @EJB
/*  141:     */   private ServicioNivelInstruccion servicioNivelInstruccion;
/*  142:     */   @EJB
/*  143:     */   private ServicioInstitucionEducativa servicioInstitucionEducativa;
/*  144:     */   @EJB
/*  145:     */   private ServicioSucursal servicioSucursal;
/*  146:     */   @EJB
/*  147:     */   private ServicioTipoContacto servicioTipoContacto;
/*  148:     */   @EJB
/*  149:     */   private ServicioDetalleDocumentoDigitalizado servicioDetalleDocumentoDigitalizado;
/*  150:     */   @EJB
/*  151:     */   private ServicioMotivoLlamadoAtencion servicioMotivoLlamadoAtencion;
/*  152:     */   @EJB
/*  153:     */   private transient ServicioParroquia servicioParroquia;
/*  154:     */   @EJB
/*  155:     */   protected ServicioGenerico<HorarioEmpleado> servicioHorarioEmpleado;
/*  156:     */   @EJB
/*  157:     */   protected ServicioProducto servicioProducto;
/*  158:     */   @EJB
/*  159:     */   private transient ServicioGenerico<CategoriaRubro> servicioCategoriaRubro;
/*  160:     */   private Empresa empresa;
/*  161:     */   private FormacionAcademica formacionAcademica;
/*  162:     */   private String cargoEmpleadoSelecionado;
/*  163:     */   private String departamentoSelecionado;
/*  164:     */   private String estadoCivilSeleccionado;
/*  165:     */   private String tituloSelecionado;
/*  166:     */   private String tipoDiscapacidadSelecionadad;
/*  167:     */   private String anio;
/*  168:     */   private String mes;
/*  169:     */   private StreamedContent file;
/*  170:     */   private StreamedContent fileLlamadoAtencion;
/*  171:     */   private String categoriaRubroSeleccionado;
/*  172:     */   private TreeNode selectedNode;
/*  173:     */   protected LazyDataModel<Empresa> listaEmpresa;
/*  174:     */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  175:     */   private List<CargoEmpleado> listaCargoEmpleado;
/*  176:     */   private List<Departamento> listaDepartamento;
/*  177:     */   private List<EstadoCivil> listaEstadoCivil;
/*  178:     */   private List<Titulo> listaTitulo;
/*  179:     */   private List<TipoDiscapacidad> listaTipoDiscapacidad;
/*  180:     */   private List<Pais> listaPais;
/*  181:     */   private List<Banco> listaBanco;
/*  182:     */   private List<Provincia> listaProvincia;
/*  183:     */   private List<Ciudad> listaCiudad;
/*  184:     */   private List<TipoContrato> listaTipoContrato;
/*  185:     */   private List<TipoCuentaBancaria> listaTipoCuentaBancaria;
/*  186:     */   private List<NivelInstruccion> listaNivelInstruccion;
/*  187:     */   private List<InstitucionEducativa> listaInstitucionEducativa;
/*  188:     */   private List<Sucursal> listaSucursal;
/*  189:     */   private List<TipoContacto> listaTipoContacto;
/*  190:     */   private List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizadoEmpleado;
/*  191:     */   private List<Producto> listaProductos;
/*  192:     */   private List<CategoriaRubro> listaCategoriaRubros;
/*  193:     */   private List<SelectItem> listaTipoEmpresa;
/*  194:     */   private List<SelectItem> listaGenero;
/*  195:     */   private List<SelectItem> listaGentilicioPais;
/*  196:     */   private List<SelectItem> listaFormaPagoEmpleado;
/*  197:     */   private List<SelectItem> listaAnio;
/*  198:     */   private List<SelectItem> listaMes;
/*  199:     */   private List<SelectItem> listaTipoSangre;
/*  200:     */   private List<SelectItem> listaResidenciaEmpleado;
/*  201:     */   private List<SelectItem> listaConvenioDobleImposicion;
/*  202:     */   private List<SelectItem> listaCondicionRespectoDiscapacidad;
/*  203:     */   private List<SelectItem> listaTipoIdentificacionSRI;
/*  204:     */   private boolean empleadoExiste;
/*  205:     */   private DetalleDocumentoDigitalizado selectedDetalleDocumento;
/*  206:     */   private LlamadoAtencion selectedLlamadoAtencion;
/*  207:     */   protected DataTable dtEmpresa;
/*  208:     */   private DataTable dtDirecciones;
/*  209:     */   private DataTable dtCuentaBancarioEmpresa;
/*  210:     */   private DataTable dtFormacionAcademica;
/*  211:     */   private DataTable dtListaDetalleDocumento;
/*  212:     */   private String allowTypes;
/*  213:     */   private String sizeLimit;
/*  214:     */   private String tipoFileUpload;
/*  215:     */   @ManagedProperty("#{tipoIdentificacionBean}")
/*  216:     */   private TipoIdentificacionBean tipoIdentificacionBean;
/*  217:     */   @ManagedProperty("#{tipoCuentaBancariaBean}")
/*  218:     */   private TipoCuentaBancariaBean tipoCuentaBancariaBean;
/*  219:     */   private TabView tvDetallesEmpleado;
/*  220:     */   private boolean activadorTipoServicio;
/*  221:     */   private int numeroHuella;
/*  222:     */   private List<LlamadoAtencion> listaLlamadosAtencion;
/*  223:     */   
/*  224:     */   @PostConstruct
/*  225:     */   public void init()
/*  226:     */   {
/*  227: 272 */     this.listaEmpresa = new LazyDataModel()
/*  228:     */     {
/*  229:     */       private static final long serialVersionUID = 1L;
/*  230:     */       
/*  231:     */       public List<Empresa> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  232:     */       {
/*  233: 279 */         List<Empresa> lista = new ArrayList();
/*  234:     */         
/*  235: 281 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  236: 282 */         filters.put("indicadorEmpleado", "true");
/*  237: 283 */         lista = EmpleadoBean.this.servicioEmpresa.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  238: 284 */         EmpleadoBean.this.listaEmpresa.setRowCount(EmpleadoBean.this.servicioEmpresa.contarPorCriterio(filters));
/*  239:     */         
/*  240: 286 */         return lista;
/*  241:     */       }
/*  242:     */     };
/*  243:     */   }
/*  244:     */   
/*  245:     */   private void crearEntidad()
/*  246:     */   {
/*  247: 301 */     this.empresa = new Empresa();
/*  248: 302 */     this.empresa.setCategoriaEmpresa(new CategoriaEmpresa());
/*  249: 303 */     this.empresa.setTipoIdentificacion(new TipoIdentificacion());
/*  250: 304 */     this.empresa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  251: 305 */     this.empresa.setIdSucursal(AppUtil.getSucursal().getId());
/*  252: 306 */     this.empresa.setIndicadorCliente(false);
/*  253: 307 */     this.empresa.setIndicadorEmpleado(true);
/*  254: 308 */     this.empresa.setIndicadorProveedor(false);
/*  255: 309 */     this.empresa.setTipoEmpresa(TipoEmpresa.NATURAL);
/*  256:     */     
/*  257: 311 */     Empleado empleado = new Empleado();
/*  258: 312 */     empleado.setEmpresa(this.empresa);
/*  259: 313 */     empleado.setCargoEmpleado(new CargoEmpleado());
/*  260: 314 */     empleado.setTipoContrato(null);
/*  261: 315 */     empleado.setPais(new Pais());
/*  262: 316 */     empleado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  263: 317 */     empleado.setSucursal(AppUtil.getSucursal());
/*  264: 318 */     empleado.setCategoriaRubro(new CategoriaRubro());
/*  265: 319 */     empleado.setResidenciaTrabajador(ResidenciaTrabajador.LOCAL.getCodigo());
/*  266: 320 */     empleado.setCondicionRespectoDiscapacidad(CondicionRespectoDiscapacidad.NO_APLICA.getCodigo());
/*  267:     */     
/*  268: 322 */     this.empresa.setEmpleado(empleado);
/*  269: 323 */     agregarDireccion();
/*  270:     */   }
/*  271:     */   
/*  272:     */   public String editar()
/*  273:     */   {
/*  274: 334 */     if ((getEmpresa() != null) && (getEmpresa().getIdEmpresa() != 0))
/*  275:     */     {
/*  276: 335 */       this.empresa = this.servicioEmpresa.cargarDetalle(getEmpresa());
/*  277: 336 */       for (DireccionEmpresa de : this.empresa.getDirecciones()) {
/*  278: 337 */         de.getCiudad().setListaParroquia(listaParroquiasPorCiudad(de.getCiudad()));
/*  279:     */       }
/*  280: 339 */       this.empresa.setListaEmpresaAtributo(new ArrayList());
/*  281: 340 */       if (this.empresa.getDirecciones().isEmpty()) {
/*  282: 341 */         agregarDireccion();
/*  283:     */       }
/*  284: 346 */       getListaDetalleDocumentoDigitalizadoEmpleado();
/*  285: 347 */       setListaLlamadosAtencion(null);
/*  286: 348 */       setEditado(true);
/*  287:     */     }
/*  288:     */     else
/*  289:     */     {
/*  290: 366 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  291:     */     }
/*  292: 368 */     return "";
/*  293:     */   }
/*  294:     */   
/*  295:     */   public String guardar()
/*  296:     */   {
/*  297:     */     try
/*  298:     */     {
/*  299: 378 */       boolean banderaFechaCorrecta = true;
/*  300: 379 */       for (DetalleDocumentoDigitalizado detalleDocumento : this.listaDetalleDocumentoDigitalizadoEmpleado) {
/*  301: 380 */         if ((detalleDocumento.getFichero() != null) && 
/*  302: 381 */           (detalleDocumento.getFechaDesde() != null) && (detalleDocumento.getFechaHasta() != null) && 
/*  303: 382 */           (detalleDocumento.getFechaDesde().compareTo(detalleDocumento.getFechaHasta()) == 1))
/*  304:     */         {
/*  305: 383 */           banderaFechaCorrecta = false;
/*  306: 384 */           addErrorMessage(getLanguageController().getMensaje("msg_error_fecha_hasta"));
/*  307:     */         }
/*  308:     */       }
/*  309: 388 */       if (banderaFechaCorrecta)
/*  310:     */       {
/*  311: 389 */         this.servicioEmpresa.guardar(getEmpresa());
/*  312: 390 */         for (DetalleDocumentoDigitalizado detalleDocumento : this.listaDetalleDocumentoDigitalizadoEmpleado) {
/*  313: 391 */           if (detalleDocumento.getFichero() != null)
/*  314:     */           {
/*  315: 392 */             detalleDocumento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  316: 393 */             detalleDocumento.setIdSucursal(AppUtil.getSucursal().getId());
/*  317: 394 */             this.servicioDetalleDocumentoDigitalizado.guardar(detalleDocumento);
/*  318:     */           }
/*  319:     */         }
/*  320: 397 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  321: 398 */         setEditado(false);
/*  322: 399 */         setEmpleadoExiste(false);
/*  323: 400 */         limpiar();
/*  324:     */       }
/*  325:     */     }
/*  326:     */     catch (AS2Exception e)
/*  327:     */     {
/*  328: 403 */       JsfUtil.addErrorMessage(e, "");
/*  329: 404 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  330:     */     }
/*  331:     */     catch (ExcepcionAS2Identification e)
/*  332:     */     {
/*  333: 406 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  334: 407 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  335: 408 */       e.printStackTrace();
/*  336:     */     }
/*  337:     */     catch (ExcepcionAS2 e)
/*  338:     */     {
/*  339: 410 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  340: 411 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  341: 412 */       e.printStackTrace();
/*  342:     */     }
/*  343:     */     catch (Exception e)
/*  344:     */     {
/*  345: 414 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  346: 415 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  347: 416 */       e.printStackTrace();
/*  348:     */     }
/*  349: 418 */     return "";
/*  350:     */   }
/*  351:     */   
/*  352:     */   public String eliminar()
/*  353:     */   {
/*  354:     */     try
/*  355:     */     {
/*  356: 428 */       this.servicioEmpresa.eliminar(getEmpresa());
/*  357: 429 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  358:     */     }
/*  359:     */     catch (Exception e)
/*  360:     */     {
/*  361: 431 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  362: 432 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  363:     */     }
/*  364: 434 */     return "";
/*  365:     */   }
/*  366:     */   
/*  367:     */   public String cargarDatos()
/*  368:     */   {
/*  369: 443 */     return "";
/*  370:     */   }
/*  371:     */   
/*  372:     */   public String limpiar()
/*  373:     */   {
/*  374: 452 */     crearEntidad();
/*  375: 453 */     this.listaDetalleDocumentoDigitalizadoEmpleado = null;
/*  376: 454 */     getListaDetalleDocumentoDigitalizadoEmpleado();
/*  377: 455 */     return "";
/*  378:     */   }
/*  379:     */   
/*  380:     */   public String agregarDireccion()
/*  381:     */   {
/*  382: 462 */     DireccionEmpresa direccion = new DireccionEmpresa();
/*  383: 463 */     Ubicacion ubicacion = new Ubicacion();
/*  384: 464 */     direccion.setIndicadorDireccionEnvio(false);
/*  385: 465 */     direccion.setIndicadorDireccionFactura(false);
/*  386: 466 */     direccion.setIndicadorDireccionPrincipal(true);
/*  387: 467 */     direccion.setUbicacion(ubicacion);
/*  388: 468 */     direccion.setEmpresa(getEmpresa());
/*  389: 469 */     getEmpresa().getDirecciones().add(direccion);
/*  390:     */     
/*  391: 471 */     return "";
/*  392:     */   }
/*  393:     */   
/*  394:     */   public void agregarCuentaBancariaEmpresa()
/*  395:     */   {
/*  396: 476 */     CuentaBancaria cuentaBancaria = new CuentaBancaria();
/*  397: 477 */     cuentaBancaria.setPais(new Pais());
/*  398: 478 */     cuentaBancaria.setBanco(new Banco());
/*  399: 479 */     cuentaBancaria.setContacto("N/A");
/*  400: 480 */     cuentaBancaria.setTelefonoContacto("999999");
/*  401: 481 */     cuentaBancaria.setDescripcion("N/A");
/*  402: 482 */     cuentaBancaria.setTipoCuentaBancaria(new TipoCuentaBancaria());
/*  403: 483 */     cuentaBancaria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  404: 484 */     cuentaBancaria.setIdSucursal(AppUtil.getSucursal().getId());
/*  405:     */     
/*  406: 486 */     CuentaBancariaEmpresa cuentaBancariaEmpresa = new CuentaBancariaEmpresa();
/*  407: 487 */     cuentaBancariaEmpresa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  408: 488 */     cuentaBancariaEmpresa.setIdSucursal(Integer.valueOf(AppUtil.getSucursal().getId()));
/*  409: 489 */     cuentaBancariaEmpresa.setDescripcion("N/A");
/*  410:     */     
/*  411: 491 */     cuentaBancariaEmpresa.setCuentaBancaria(cuentaBancaria);
/*  412: 492 */     cuentaBancariaEmpresa.setEmpresa(this.empresa);
/*  413:     */     
/*  414: 494 */     getEmpresa().getListaCuentaBancariaEmpresa().add(cuentaBancariaEmpresa);
/*  415:     */   }
/*  416:     */   
/*  417:     */   public String eliminarDireccion()
/*  418:     */   {
/*  419: 499 */     DireccionEmpresa direccionEmpresa = (DireccionEmpresa)this.dtDirecciones.getRowData();
/*  420: 500 */     direccionEmpresa.setEliminado(true);
/*  421: 501 */     direccionEmpresa.getUbicacion().setEliminado(true);
/*  422:     */     
/*  423: 503 */     return "";
/*  424:     */   }
/*  425:     */   
/*  426:     */   public String eliminarCuentaBancariaEmpresa()
/*  427:     */   {
/*  428: 507 */     CuentaBancariaEmpresa cuentaBancariaEmpresa = (CuentaBancariaEmpresa)this.dtCuentaBancarioEmpresa.getRowData();
/*  429: 508 */     cuentaBancariaEmpresa.setEliminado(true);
/*  430: 509 */     cuentaBancariaEmpresa.getCuentaBancaria().setEliminado(true);
/*  431:     */     
/*  432: 511 */     return "";
/*  433:     */   }
/*  434:     */   
/*  435:     */   public String eliminarFormacionAcademica()
/*  436:     */   {
/*  437: 515 */     FormacionAcademica formacionAcademica = (FormacionAcademica)this.dtFormacionAcademica.getRowData();
/*  438: 516 */     formacionAcademica.setEliminado(true);
/*  439: 517 */     return "";
/*  440:     */   }
/*  441:     */   
/*  442:     */   public void agregarContactoListener()
/*  443:     */   {
/*  444: 521 */     Contacto contacto = new Contacto();
/*  445: 522 */     contacto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  446: 523 */     contacto.setEmpresa(getEmpresa());
/*  447: 524 */     contacto.setCargo("EMPLEADO");
/*  448: 525 */     getEmpresa().getListaContacto().add(contacto);
/*  449:     */   }
/*  450:     */   
/*  451:     */   public String eliminarContacto(Contacto contacto)
/*  452:     */   {
/*  453: 535 */     contacto.setEliminado(true);
/*  454: 536 */     return "";
/*  455:     */   }
/*  456:     */   
/*  457:     */   public List<CuentaBancariaEmpresa> getListaCuentaBancariaEmpresa()
/*  458:     */   {
/*  459: 541 */     List<CuentaBancariaEmpresa> cuentaBancariaEmpresa = new ArrayList();
/*  460: 542 */     for (CuentaBancariaEmpresa cbe : this.empresa.getListaCuentaBancariaEmpresa()) {
/*  461: 543 */       if (!cbe.isEliminado()) {
/*  462: 544 */         cuentaBancariaEmpresa.add(cbe);
/*  463:     */       }
/*  464:     */     }
/*  465: 547 */     return cuentaBancariaEmpresa;
/*  466:     */   }
/*  467:     */   
/*  468:     */   public void agregarFormacionAcademica()
/*  469:     */   {
/*  470: 551 */     FormacionAcademica formacionAcademica = new FormacionAcademica();
/*  471: 552 */     formacionAcademica.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  472: 553 */     formacionAcademica.setIdSucursal(AppUtil.getSucursal().getId());
/*  473: 554 */     formacionAcademica.setEmpleado(this.empresa.getEmpleado());
/*  474: 555 */     formacionAcademica.setInstitucionEducativa(new InstitucionEducativa());
/*  475: 556 */     formacionAcademica.setNivelInstruccion(new NivelInstruccion());
/*  476: 557 */     this.empresa.getEmpleado().getListaFormacionAcademica().add(formacionAcademica);
/*  477:     */   }
/*  478:     */   
/*  479:     */   public int getSizeListaCuentaBancaria()
/*  480:     */   {
/*  481: 566 */     return getListaCuentaBancariaEmpresa().size();
/*  482:     */   }
/*  483:     */   
/*  484:     */   public void processUploadFichero(FileUploadEvent eventImagen)
/*  485:     */   {
/*  486: 570 */     if (this.tipoFileUpload.equals("fotoEmpleado")) {
/*  487: 571 */       processUploadFoto(eventImagen);
/*  488: 572 */     } else if (this.tipoFileUpload.equals("documentoDigitalizado")) {
/*  489: 573 */       processUploadDocumento(eventImagen);
/*  490: 576 */     } else if (this.tipoFileUpload.equals("huellaDigital")) {
/*  491: 577 */       processUploadFotoHuella(eventImagen);
/*  492:     */     }
/*  493:     */   }
/*  494:     */   
/*  495:     */   public void processUploadFoto(FileUploadEvent eventImagen)
/*  496:     */   {
/*  497:     */     try
/*  498:     */     {
/*  499: 591 */       String fileName = "emp_" + AppUtil.getOrganizacion().getId() + "_" + getEmpresa().getCodigo() + recuperaExtencion(eventImagen.getFile().getFileName());
/*  500:     */       
/*  501: 593 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "empleado");
/*  502:     */       
/*  503: 595 */       File directorio = new File(uploadDir);
/*  504:     */       
/*  505: 597 */       File file = new File(uploadDir + fileName);
/*  506: 599 */       if (!directorio.exists()) {
/*  507: 600 */         directorio.mkdirs();
/*  508:     */       }
/*  509: 603 */       InputStream input = new BufferedInputStream(eventImagen.getFile().getInputstream());
/*  510:     */       
/*  511: 605 */       this.empresa.getEmpleado().setImagen(fileName);
/*  512:     */       
/*  513: 607 */       FileOutputStream output = new FileOutputStream(file);
/*  514: 609 */       while (input.available() != 0) {
/*  515: 610 */         output.write(input.read());
/*  516:     */       }
/*  517: 612 */       input.close();
/*  518: 613 */       output.close();
/*  519:     */       
/*  520: 615 */       addInfoMessage(getLanguageController().getMensaje("msg_info_subir_imagen"));
/*  521:     */     }
/*  522:     */     catch (Exception e)
/*  523:     */     {
/*  524: 618 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_imagen"));
/*  525: 619 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  526:     */     }
/*  527:     */   }
/*  528:     */   
/*  529:     */   public void processUploadFotoHuella(FileUploadEvent eventImagen)
/*  530:     */   {
/*  531:     */     try
/*  532:     */     {
/*  533: 634 */       String fileName = "hue_" + getNumeroHuella() + "_emp_" + AppUtil.getOrganizacion().getId() + "_" + getEmpresa().getCodigo() + recuperaExtencion(eventImagen.getFile().getFileName());
/*  534:     */       
/*  535: 636 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "huellas_digitales");
/*  536:     */       
/*  537: 638 */       File directorio = new File(uploadDir);
/*  538:     */       
/*  539: 640 */       File file = new File(uploadDir + fileName);
/*  540: 642 */       if (!directorio.exists()) {
/*  541: 643 */         directorio.mkdirs();
/*  542:     */       }
/*  543: 646 */       InputStream input = new BufferedInputStream(eventImagen.getFile().getInputstream());
/*  544: 648 */       switch (getNumeroHuella())
/*  545:     */       {
/*  546:     */       case 1: 
/*  547: 650 */         this.empresa.getEmpleado().setImagenHuella1(fileName);
/*  548: 651 */         break;
/*  549:     */       case 2: 
/*  550: 653 */         this.empresa.getEmpleado().setImagenHuella2(fileName);
/*  551: 654 */         break;
/*  552:     */       case 3: 
/*  553: 656 */         this.empresa.getEmpleado().setImagenHuella3(fileName);
/*  554: 657 */         break;
/*  555:     */       case 4: 
/*  556: 659 */         this.empresa.getEmpleado().setImagenHuella4(fileName);
/*  557: 660 */         break;
/*  558:     */       case 5: 
/*  559: 662 */         this.empresa.getEmpleado().setImagenHuella5(fileName);
/*  560: 663 */         break;
/*  561:     */       case 6: 
/*  562: 665 */         this.empresa.getEmpleado().setImagenHuella6(fileName);
/*  563: 666 */         break;
/*  564:     */       case 7: 
/*  565: 668 */         this.empresa.getEmpleado().setImagenHuella7(fileName);
/*  566: 669 */         break;
/*  567:     */       case 8: 
/*  568: 671 */         this.empresa.getEmpleado().setImagenHuella8(fileName);
/*  569: 672 */         break;
/*  570:     */       case 9: 
/*  571: 674 */         this.empresa.getEmpleado().setImagenHuella9(fileName);
/*  572: 675 */         break;
/*  573:     */       case 10: 
/*  574: 677 */         this.empresa.getEmpleado().setImagenHuella10(fileName);
/*  575: 678 */         break;
/*  576:     */       }
/*  577: 683 */       FileOutputStream output = new FileOutputStream(file);
/*  578: 685 */       while (input.available() != 0) {
/*  579: 686 */         output.write(input.read());
/*  580:     */       }
/*  581: 688 */       input.close();
/*  582: 689 */       output.close();
/*  583:     */       
/*  584: 691 */       addInfoMessage(getLanguageController().getMensaje("msg_info_subir_imagen"));
/*  585:     */     }
/*  586:     */     catch (Exception e)
/*  587:     */     {
/*  588: 694 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_imagen"));
/*  589: 695 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  590:     */     }
/*  591:     */   }
/*  592:     */   
/*  593:     */   public void processUploadDocumento(FileUploadEvent eventDocumento)
/*  594:     */   {
/*  595:     */     try
/*  596:     */     {
/*  597: 705 */       String fileName = "emp_" + AppUtil.getOrganizacion().getId() + "_" + getEmpresa().getCodigo() + "_" + this.selectedDetalleDocumento.getDocumentoDigitalizadoDepartamento().getDocumentoDigitalizado().getNombre().replaceAll("[^a-zA-Z0-9 ]", "") + recuperaExtencion(eventDocumento.getFile().getFileName());
/*  598:     */       
/*  599: 707 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "documentos_empleado");
/*  600:     */       
/*  601: 709 */       File directorio = new File(uploadDir);
/*  602:     */       
/*  603: 711 */       File file = new File(uploadDir + fileName);
/*  604: 713 */       if (!directorio.exists()) {
/*  605: 714 */         directorio.mkdirs();
/*  606:     */       }
/*  607: 717 */       InputStream input = new BufferedInputStream(eventDocumento.getFile().getInputstream());
/*  608: 718 */       System.out.println("fileName");
/*  609: 719 */       this.selectedDetalleDocumento.setFichero(fileName);
/*  610:     */       
/*  611: 721 */       FileOutputStream output = new FileOutputStream(file);
/*  612: 723 */       while (input.available() != 0) {
/*  613: 724 */         output.write(input.read());
/*  614:     */       }
/*  615: 727 */       input.close();
/*  616: 728 */       output.close();
/*  617:     */       
/*  618: 730 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  619:     */     }
/*  620:     */     catch (Exception e)
/*  621:     */     {
/*  622: 733 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_imagen"));
/*  623: 734 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  624:     */     }
/*  625:     */   }
/*  626:     */   
/*  627:     */   public String recuperaExtencion(String nombreArchivo)
/*  628:     */   {
/*  629: 748 */     int mid = nombreArchivo.lastIndexOf(".");
/*  630: 749 */     return "." + nombreArchivo.substring(mid + 1, nombreArchivo.length());
/*  631:     */   }
/*  632:     */   
/*  633:     */   public void actualizarCiudad() {}
/*  634:     */   
/*  635:     */   public void cargarCiudad()
/*  636:     */   {
/*  637:     */     try
/*  638:     */     {
/*  639: 758 */       DireccionEmpresa direccionEmpresa = (DireccionEmpresa)this.dtDirecciones.getRowData();
/*  640: 759 */       Ciudad ciudad = (Ciudad)this.selectedNode.getData();
/*  641: 760 */       direccionEmpresa.setCiudad(ciudad);
/*  642:     */       
/*  643:     */ 
/*  644: 763 */       direccionEmpresa.getCiudad().setListaParroquia(listaParroquiasPorCiudad(direccionEmpresa.getCiudad()));
/*  645:     */     }
/*  646:     */     catch (Exception e)
/*  647:     */     {
/*  648: 766 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  649:     */     }
/*  650:     */   }
/*  651:     */   
/*  652:     */   public List<Empresa> autocompletarEmpleados(String consulta)
/*  653:     */   {
/*  654: 772 */     List<Empresa> lista = this.servicioEmpresa.autocompletarEmpleados(consulta);
/*  655:     */     
/*  656: 774 */     return lista;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void buscarEmpresaEvent()
/*  660:     */   {
/*  661:     */     try
/*  662:     */     {
/*  663: 779 */       this.empresa = this.servicioEmpresa.buscarEmpresaPorIdentificacion(getEmpresa().getIdentificacion());
/*  664: 780 */       this.empresa = this.servicioEmpresa.cargarDetalleTodo(this.empresa);
/*  665: 781 */       if (this.empresa.getEmpleado() == null)
/*  666:     */       {
/*  667: 782 */         Empleado empleado = new Empleado();
/*  668: 783 */         empleado.setEmpresa(getEmpresa());
/*  669: 784 */         empleado.setCargoEmpleado(new CargoEmpleado());
/*  670: 785 */         empleado.setTipoContrato(null);
/*  671: 786 */         empleado.setPais(new Pais());
/*  672: 787 */         empleado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  673: 788 */         empleado.setSucursal(AppUtil.getSucursal());
/*  674: 789 */         this.empresa.setIndicadorEmpleado(true);
/*  675: 790 */         this.empresa.setEmpleado(empleado);
/*  676:     */       }
/*  677: 792 */       setEmpleadoExiste(true);
/*  678: 793 */       setEmpresa(this.empresa);
/*  679: 794 */       this.empresa.setListaEmpresaAtributo(new ArrayList());
/*  680: 795 */       RequestContext.getCurrentInstance().update("panelNuevo");
/*  681:     */     }
/*  682:     */     catch (ExcepcionAS2 e)
/*  683:     */     {
/*  684: 797 */       setEmpleadoExiste(false);
/*  685: 798 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  686:     */     }
/*  687:     */   }
/*  688:     */   
/*  689:     */   public String cancelar()
/*  690:     */   {
/*  691: 805 */     setEditado(false);
/*  692: 806 */     setEmpleadoExiste(false);
/*  693: 807 */     limpiar();
/*  694: 808 */     return "";
/*  695:     */   }
/*  696:     */   
/*  697:     */   public void cargarTipoServicio()
/*  698:     */   {
/*  699: 812 */     CuentaBancariaEmpresa cbe = (CuentaBancariaEmpresa)this.dtCuentaBancarioEmpresa.getRowData();
/*  700: 813 */     if ((cbe.getCuentaBancaria().getBanco().getCodigo().equals("30")) || (cbe.getCuentaBancaria().getBanco().equals("17"))) {
/*  701: 814 */       this.activadorTipoServicio = true;
/*  702:     */     } else {
/*  703: 816 */       this.activadorTipoServicio = false;
/*  704:     */     }
/*  705:     */   }
/*  706:     */   
/*  707:     */   public Empresa getEmpresa()
/*  708:     */   {
/*  709: 832 */     if (this.empresa == null) {
/*  710: 833 */       this.empresa = new Empresa();
/*  711:     */     }
/*  712: 835 */     return this.empresa;
/*  713:     */   }
/*  714:     */   
/*  715:     */   public void setEmpresa(Empresa empresa)
/*  716:     */   {
/*  717: 845 */     this.empresa = empresa;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public LazyDataModel<Empresa> getListaEmpresa()
/*  721:     */   {
/*  722: 854 */     return this.listaEmpresa;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void setListaEmpresa(LazyDataModel<Empresa> listaEmpresa)
/*  726:     */   {
/*  727: 864 */     this.listaEmpresa = listaEmpresa;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public DataTable getDtEmpresa()
/*  731:     */   {
/*  732: 873 */     return this.dtEmpresa;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public void setDtEmpresa(DataTable dtEmpresa)
/*  736:     */   {
/*  737: 883 */     this.dtEmpresa = dtEmpresa;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public TipoIdentificacionBean getTipoIdentificacionBean()
/*  741:     */   {
/*  742: 892 */     return this.tipoIdentificacionBean;
/*  743:     */   }
/*  744:     */   
/*  745:     */   public void setTipoIdentificacionBean(TipoIdentificacionBean tipoIdentificacionBean)
/*  746:     */   {
/*  747: 902 */     this.tipoIdentificacionBean = tipoIdentificacionBean;
/*  748:     */   }
/*  749:     */   
/*  750:     */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/*  751:     */   {
/*  752: 911 */     if (this.listaCategoriaEmpresa == null) {
/*  753: 912 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, null);
/*  754:     */     }
/*  755: 914 */     return this.listaCategoriaEmpresa;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/*  759:     */   {
/*  760: 924 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/*  761:     */   }
/*  762:     */   
/*  763:     */   public List<SelectItem> getListaTipoEmpresa()
/*  764:     */   {
/*  765: 934 */     if (this.listaTipoEmpresa == null)
/*  766:     */     {
/*  767: 935 */       this.listaTipoEmpresa = new ArrayList();
/*  768: 936 */       for (TipoEmpresa t : TipoEmpresa.values())
/*  769:     */       {
/*  770: 937 */         SelectItem item = new SelectItem(t, t.getNombre());
/*  771: 938 */         this.listaTipoEmpresa.add(item);
/*  772:     */       }
/*  773:     */     }
/*  774: 942 */     return this.listaTipoEmpresa;
/*  775:     */   }
/*  776:     */   
/*  777:     */   public List<DireccionEmpresa> getDirecciones()
/*  778:     */   {
/*  779: 952 */     List<DireccionEmpresa> direcciones = new ArrayList();
/*  780: 953 */     for (DireccionEmpresa de : getEmpresa().getDirecciones()) {
/*  781: 955 */       if (!de.isEliminado()) {
/*  782: 956 */         direcciones.add(de);
/*  783:     */       }
/*  784:     */     }
/*  785: 960 */     return direcciones;
/*  786:     */   }
/*  787:     */   
/*  788:     */   public void setDirecciones(List<DireccionEmpresa> direcciones)
/*  789:     */   {
/*  790: 970 */     getEmpresa().setDirecciones(direcciones);
/*  791:     */   }
/*  792:     */   
/*  793:     */   public String getCargoEmpleadoSelecionado()
/*  794:     */   {
/*  795: 979 */     this.cargoEmpleadoSelecionado = "";
/*  796: 980 */     if (getEmpresa().getEmpleado().getCargoEmpleado() != null) {
/*  797: 981 */       this.cargoEmpleadoSelecionado = ("" + getEmpresa().getEmpleado().getCargoEmpleado().getId());
/*  798:     */     }
/*  799: 983 */     return this.cargoEmpleadoSelecionado;
/*  800:     */   }
/*  801:     */   
/*  802:     */   public void setCargoEmpleadoSelecionado(String cargoEmpleadoSelecionado)
/*  803:     */   {
/*  804: 994 */     if (this.cargoEmpleadoSelecionado != cargoEmpleadoSelecionado)
/*  805:     */     {
/*  806: 995 */       this.cargoEmpleadoSelecionado = cargoEmpleadoSelecionado;
/*  807: 996 */       CargoEmpleado auxCargoEmpleadoSelecionado = null;
/*  808: 997 */       if (this.cargoEmpleadoSelecionado != "")
/*  809:     */       {
/*  810: 998 */         int idCargoEmpleadoSelecionado = Integer.parseInt(this.cargoEmpleadoSelecionado);
/*  811: 999 */         auxCargoEmpleadoSelecionado = this.servicioCargoEmpleado.buscarPorId(idCargoEmpleadoSelecionado);
/*  812:     */       }
/*  813:1001 */       getEmpresa().getEmpleado().setCargoEmpleado(auxCargoEmpleadoSelecionado);
/*  814:     */     }
/*  815:     */   }
/*  816:     */   
/*  817:     */   public List<CargoEmpleado> getListaCargoEmpleado()
/*  818:     */   {
/*  819:1012 */     if (this.listaCargoEmpleado == null) {
/*  820:1013 */       this.listaCargoEmpleado = this.servicioCargoEmpleado.obtenerListaCombo("nombre", true, null);
/*  821:     */     }
/*  822:1015 */     return this.listaCargoEmpleado;
/*  823:     */   }
/*  824:     */   
/*  825:     */   public List<Departamento> getListaDepartamento()
/*  826:     */   {
/*  827:1024 */     if (this.listaDepartamento == null) {
/*  828:1025 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/*  829:     */     }
/*  830:1027 */     return this.listaDepartamento;
/*  831:     */   }
/*  832:     */   
/*  833:     */   public String getDepartamentoSelecionado()
/*  834:     */   {
/*  835:1036 */     this.departamentoSelecionado = null;
/*  836:1037 */     if (getEmpresa().getEmpleado().getDepartamento() != null) {
/*  837:1038 */       this.departamentoSelecionado = ("" + getEmpresa().getEmpleado().getDepartamento().getId());
/*  838:     */     }
/*  839:1040 */     return this.departamentoSelecionado;
/*  840:     */   }
/*  841:     */   
/*  842:     */   public void setDepartamentoSelecionado(String departamentoSelecionado)
/*  843:     */   {
/*  844:1051 */     if (this.departamentoSelecionado != departamentoSelecionado)
/*  845:     */     {
/*  846:1052 */       this.departamentoSelecionado = departamentoSelecionado;
/*  847:1053 */       Departamento auxDepartamentoSelecionado = null;
/*  848:1054 */       if (this.departamentoSelecionado != "")
/*  849:     */       {
/*  850:1055 */         int idDepartamentoSelecionado = Integer.parseInt(this.departamentoSelecionado);
/*  851:1056 */         auxDepartamentoSelecionado = this.servicioDepartamento.buscarPorId(idDepartamentoSelecionado);
/*  852:     */       }
/*  853:1058 */       getEmpresa().getEmpleado().setDepartamento(auxDepartamentoSelecionado);
/*  854:     */     }
/*  855:     */   }
/*  856:     */   
/*  857:     */   public List<EstadoCivil> getListaEstadoCivil()
/*  858:     */   {
/*  859:1068 */     if (this.listaEstadoCivil == null) {
/*  860:1069 */       this.listaEstadoCivil = this.servicioEstadoCivil.obtenerListaCombo("nombre", true, null);
/*  861:     */     }
/*  862:1071 */     return this.listaEstadoCivil;
/*  863:     */   }
/*  864:     */   
/*  865:     */   public String getEstadoCivilSeleccionado()
/*  866:     */   {
/*  867:1080 */     this.estadoCivilSeleccionado = null;
/*  868:1081 */     if (getEmpresa().getEmpleado().getEstadoCivil() != null) {
/*  869:1082 */       this.estadoCivilSeleccionado = ("" + getEmpresa().getEmpleado().getEstadoCivil().getId());
/*  870:     */     }
/*  871:1084 */     return this.estadoCivilSeleccionado;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void setEstadoCivilSeleccionado(String estadoCivilSeleccionado)
/*  875:     */   {
/*  876:1095 */     if (this.estadoCivilSeleccionado != estadoCivilSeleccionado)
/*  877:     */     {
/*  878:1096 */       this.estadoCivilSeleccionado = estadoCivilSeleccionado;
/*  879:1097 */       EstadoCivil auxEstadoCivil = null;
/*  880:1098 */       if (this.departamentoSelecionado != "")
/*  881:     */       {
/*  882:1099 */         int idEstadoCivil = Integer.parseInt(this.estadoCivilSeleccionado);
/*  883:1100 */         auxEstadoCivil = this.servicioEstadoCivil.buscarPorId(idEstadoCivil);
/*  884:     */       }
/*  885:1102 */       getEmpresa().getEmpleado().setEstadoCivil(auxEstadoCivil);
/*  886:     */     }
/*  887:     */   }
/*  888:     */   
/*  889:     */   public List<Titulo> getListaTitulo()
/*  890:     */   {
/*  891:1112 */     if (this.listaTitulo == null) {
/*  892:1113 */       this.listaTitulo = this.servicioTitulo.obtenerListaCombo("nombre", true, null);
/*  893:     */     }
/*  894:1115 */     return this.listaTitulo;
/*  895:     */   }
/*  896:     */   
/*  897:     */   public String getTituloSelecionado()
/*  898:     */   {
/*  899:1124 */     this.tituloSelecionado = null;
/*  900:1125 */     if (getEmpresa().getEmpleado().getTitulo() != null) {
/*  901:1126 */       this.tituloSelecionado = ("" + getEmpresa().getEmpleado().getTitulo().getId());
/*  902:     */     }
/*  903:1128 */     return this.tituloSelecionado;
/*  904:     */   }
/*  905:     */   
/*  906:     */   public void setTituloSelecionado(String tituloSelecionado)
/*  907:     */   {
/*  908:1139 */     if (this.tituloSelecionado != tituloSelecionado)
/*  909:     */     {
/*  910:1140 */       this.tituloSelecionado = tituloSelecionado;
/*  911:1141 */       Titulo auxTitulo = null;
/*  912:1142 */       if (this.tituloSelecionado != "")
/*  913:     */       {
/*  914:1143 */         int idTitulo = Integer.parseInt(this.tituloSelecionado);
/*  915:1144 */         auxTitulo = this.servicioTitulo.buscarPorId(idTitulo);
/*  916:     */       }
/*  917:1146 */       getEmpresa().getEmpleado().setTitulo(auxTitulo);
/*  918:     */     }
/*  919:     */   }
/*  920:     */   
/*  921:     */   public List<SelectItem> getListaGenero()
/*  922:     */   {
/*  923:1156 */     if (this.listaGenero == null)
/*  924:     */     {
/*  925:1157 */       this.listaGenero = new ArrayList();
/*  926:1158 */       for (Genero genero : Genero.values())
/*  927:     */       {
/*  928:1159 */         SelectItem item = new SelectItem(genero, genero.getNombre());
/*  929:1160 */         this.listaGenero.add(item);
/*  930:     */       }
/*  931:     */     }
/*  932:1163 */     return this.listaGenero;
/*  933:     */   }
/*  934:     */   
/*  935:     */   public DataTable getDtDirecciones()
/*  936:     */   {
/*  937:1172 */     return this.dtDirecciones;
/*  938:     */   }
/*  939:     */   
/*  940:     */   public void setDtDirecciones(DataTable dtDirecciones)
/*  941:     */   {
/*  942:1182 */     this.dtDirecciones = dtDirecciones;
/*  943:     */   }
/*  944:     */   
/*  945:     */   public String getTipoDiscapacidadSelecionadad()
/*  946:     */   {
/*  947:1191 */     this.tipoDiscapacidadSelecionadad = null;
/*  948:1192 */     if (getEmpresa().getEmpleado().getTipoDiscapacidad() != null) {
/*  949:1193 */       this.tipoDiscapacidadSelecionadad = ("" + getEmpresa().getEmpleado().getTipoDiscapacidad().getId());
/*  950:     */     }
/*  951:1195 */     return this.tipoDiscapacidadSelecionadad;
/*  952:     */   }
/*  953:     */   
/*  954:     */   public void setTipoDiscapacidadSelecionadad(String tipoDiscapacidadSelecionadad)
/*  955:     */   {
/*  956:1206 */     if (this.tipoDiscapacidadSelecionadad != tipoDiscapacidadSelecionadad)
/*  957:     */     {
/*  958:1208 */       this.tipoDiscapacidadSelecionadad = tipoDiscapacidadSelecionadad;
/*  959:     */       
/*  960:1210 */       TipoDiscapacidad auxTipoDiscapacidad = null;
/*  961:1211 */       if (this.tipoDiscapacidadSelecionadad != "")
/*  962:     */       {
/*  963:1212 */         int idTipoDiscapacidad = Integer.parseInt(this.tipoDiscapacidadSelecionadad);
/*  964:1213 */         auxTipoDiscapacidad = this.servicioTipoDiscapacidad.buscarPorId(idTipoDiscapacidad);
/*  965:     */       }
/*  966:1215 */       getEmpresa().getEmpleado().setTipoDiscapacidad(auxTipoDiscapacidad);
/*  967:     */     }
/*  968:     */   }
/*  969:     */   
/*  970:     */   public List<TipoDiscapacidad> getListaTipoDiscapacidad()
/*  971:     */   {
/*  972:1225 */     if (this.listaTipoDiscapacidad == null) {
/*  973:1226 */       this.listaTipoDiscapacidad = this.servicioTipoDiscapacidad.obtenerListaCombo("nombre", true, null);
/*  974:     */     }
/*  975:1228 */     return this.listaTipoDiscapacidad;
/*  976:     */   }
/*  977:     */   
/*  978:     */   public DataTable getDtCuentaBancarioEmpresa()
/*  979:     */   {
/*  980:1237 */     return this.dtCuentaBancarioEmpresa;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public void setDtCuentaBancarioEmpresa(DataTable dtCuentaBancarioEmpresa)
/*  984:     */   {
/*  985:1247 */     this.dtCuentaBancarioEmpresa = dtCuentaBancarioEmpresa;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public TipoCuentaBancariaBean getTipoCuentaBancariaBean()
/*  989:     */   {
/*  990:1256 */     return this.tipoCuentaBancariaBean;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public void setTipoCuentaBancariaBean(TipoCuentaBancariaBean tipoCuentaBancariaBean)
/*  994:     */   {
/*  995:1266 */     this.tipoCuentaBancariaBean = tipoCuentaBancariaBean;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public List<Pais> getListaPais()
/*  999:     */   {
/* 1000:1270 */     if (this.listaPais == null) {
/* 1001:1271 */       this.listaPais = this.servicioPais.obtenerListaCombo("nombre", false, null);
/* 1002:     */     }
/* 1003:1273 */     return this.listaPais;
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public List<Banco> getListaBanco()
/* 1007:     */   {
/* 1008:1277 */     if (this.listaBanco == null) {
/* 1009:1278 */       this.listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, null);
/* 1010:     */     }
/* 1011:1280 */     return this.listaBanco;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public List<Provincia> getListaProvincia()
/* 1015:     */   {
/* 1016:1289 */     if (this.listaProvincia == null) {
/* 1017:1290 */       this.listaProvincia = this.servicioProvincia.obtenerListaCombo("nombre", true, null);
/* 1018:     */     }
/* 1019:1293 */     return this.listaProvincia;
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   public List<Ciudad> getListaCiudad()
/* 1023:     */   {
/* 1024:1302 */     if (this.listaCiudad == null) {
/* 1025:1303 */       this.listaCiudad = this.servicioCiudad.obtenerListaCombo("nombre", true, null);
/* 1026:     */     }
/* 1027:1305 */     return this.listaCiudad;
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   public TreeNode getSelectedNode()
/* 1031:     */   {
/* 1032:1314 */     return this.selectedNode;
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public void setSelectedNode(TreeNode selectedNode)
/* 1036:     */   {
/* 1037:1324 */     this.selectedNode = selectedNode;
/* 1038:     */   }
/* 1039:     */   
/* 1040:     */   public List<TipoContrato> getListaTipoContrato()
/* 1041:     */   {
/* 1042:1333 */     if (this.listaTipoContrato == null) {
/* 1043:1334 */       this.listaTipoContrato = this.servicioTipoContrato.obtenerListaCombo("nombre", true, null);
/* 1044:     */     }
/* 1045:1336 */     return this.listaTipoContrato;
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public List<SelectItem> getListaGentilicioPais()
/* 1049:     */   {
/* 1050:1346 */     if (this.listaGentilicioPais == null)
/* 1051:     */     {
/* 1052:1347 */       this.listaGentilicioPais = new ArrayList();
/* 1053:1348 */       for (Pais pais : this.servicioPais.obtenerListaCombo("", false, null))
/* 1054:     */       {
/* 1055:1349 */         SelectItem item = new SelectItem(pais, pais.getNombre() + "\t|\t" + pais.getGentilicio());
/* 1056:1350 */         this.listaGentilicioPais.add(item);
/* 1057:     */       }
/* 1058:     */     }
/* 1059:1353 */     return this.listaGentilicioPais;
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   public List<SelectItem> getListaAnio()
/* 1063:     */   {
/* 1064:1362 */     Calendar c = Calendar.getInstance();
/* 1065:1363 */     int anio = c.get(1);
/* 1066:1364 */     if (this.listaAnio == null)
/* 1067:     */     {
/* 1068:1365 */       this.listaAnio = new ArrayList();
/* 1069:1366 */       for (int i = anio; i >= anio - 100; i--)
/* 1070:     */       {
/* 1071:1367 */         SelectItem item = new SelectItem(Integer.valueOf(i), i + "");
/* 1072:1368 */         this.listaAnio.add(item);
/* 1073:     */       }
/* 1074:     */     }
/* 1075:1371 */     return this.listaAnio;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public void setListaAnio(List<SelectItem> listaAnio)
/* 1079:     */   {
/* 1080:1381 */     this.listaAnio = listaAnio;
/* 1081:     */   }
/* 1082:     */   
/* 1083:     */   public List<SelectItem> getListaMes()
/* 1084:     */   {
/* 1085:1390 */     if (this.listaMes == null)
/* 1086:     */     {
/* 1087:1391 */       this.listaMes = new ArrayList();
/* 1088:1392 */       for (Mes mes : Mes.values())
/* 1089:     */       {
/* 1090:1393 */         SelectItem item = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.toString());
/* 1091:1394 */         this.listaMes.add(item);
/* 1092:     */       }
/* 1093:     */     }
/* 1094:1397 */     return this.listaMes;
/* 1095:     */   }
/* 1096:     */   
/* 1097:     */   public void setListaMes(List<SelectItem> listaMes)
/* 1098:     */   {
/* 1099:1401 */     this.listaMes = listaMes;
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   public List<SelectItem> getListaFormaPagoEmpleado()
/* 1103:     */   {
/* 1104:1410 */     if (this.listaFormaPagoEmpleado == null)
/* 1105:     */     {
/* 1106:1411 */       this.listaFormaPagoEmpleado = new ArrayList();
/* 1107:1412 */       for (FormaPagoEmpleado formaPagoEmpleado : FormaPagoEmpleado.values())
/* 1108:     */       {
/* 1109:1413 */         SelectItem item = new SelectItem(formaPagoEmpleado, formaPagoEmpleado.getNombre());
/* 1110:1414 */         this.listaFormaPagoEmpleado.add(item);
/* 1111:     */       }
/* 1112:     */     }
/* 1113:1417 */     return this.listaFormaPagoEmpleado;
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public boolean isEmpleadoExiste()
/* 1117:     */   {
/* 1118:1426 */     return this.empleadoExiste;
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public void setEmpleadoExiste(boolean empleadoExiste)
/* 1122:     */   {
/* 1123:1436 */     this.empleadoExiste = empleadoExiste;
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public List<TipoCuentaBancaria> getListaTipoCuentaBancaria()
/* 1127:     */   {
/* 1128:1440 */     if (this.listaTipoCuentaBancaria == null) {
/* 1129:1441 */       this.listaTipoCuentaBancaria = this.servicioTipoCuentaBancaria.obtenerListaCombo(null, false, null);
/* 1130:     */     }
/* 1131:1443 */     return this.listaTipoCuentaBancaria;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public List<FormacionAcademica> getListaFormacionAcademica()
/* 1135:     */   {
/* 1136:1453 */     List<FormacionAcademica> lista = new ArrayList();
/* 1137:1454 */     for (FormacionAcademica formacionAcademica : this.empresa.getEmpleado().getListaFormacionAcademica())
/* 1138:     */     {
/* 1139:1455 */       if (formacionAcademica.getNivelInstruccion() == null) {
/* 1140:1456 */         formacionAcademica.setNivelInstruccion(new NivelInstruccion());
/* 1141:     */       }
/* 1142:1458 */       if (formacionAcademica.getInstitucionEducativa() == null) {
/* 1143:1459 */         formacionAcademica.setInstitucionEducativa(new InstitucionEducativa());
/* 1144:     */       }
/* 1145:1462 */       if (!formacionAcademica.isEliminado()) {
/* 1146:1463 */         lista.add(formacionAcademica);
/* 1147:     */       }
/* 1148:     */     }
/* 1149:1466 */     return lista;
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   public List<NivelInstruccion> getListaNivelInstruccion()
/* 1153:     */   {
/* 1154:1475 */     if (this.listaNivelInstruccion == null) {
/* 1155:1476 */       this.listaNivelInstruccion = this.servicioNivelInstruccion.obtenerListaCombo(null, false, null);
/* 1156:     */     }
/* 1157:1478 */     return this.listaNivelInstruccion;
/* 1158:     */   }
/* 1159:     */   
/* 1160:     */   public List<InstitucionEducativa> getListaInstitucionEducativa()
/* 1161:     */   {
/* 1162:1487 */     if (this.listaInstitucionEducativa == null) {
/* 1163:1488 */       this.listaInstitucionEducativa = this.servicioInstitucionEducativa.obtenerListaCombo(null, false, null);
/* 1164:     */     }
/* 1165:1490 */     return this.listaInstitucionEducativa;
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   public FormacionAcademica getFormacionAcademica()
/* 1169:     */   {
/* 1170:1499 */     return this.formacionAcademica;
/* 1171:     */   }
/* 1172:     */   
/* 1173:     */   public void setInstitucionEducativaEmpleado(FormacionAcademica formacionAcademica)
/* 1174:     */   {
/* 1175:1509 */     this.formacionAcademica = formacionAcademica;
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   public DataTable getDtFormacionAcademica()
/* 1179:     */   {
/* 1180:1518 */     return this.dtFormacionAcademica;
/* 1181:     */   }
/* 1182:     */   
/* 1183:     */   public void setDtFormacionAcademica(DataTable dtFormacionAcademica)
/* 1184:     */   {
/* 1185:1528 */     this.dtFormacionAcademica = dtFormacionAcademica;
/* 1186:     */   }
/* 1187:     */   
/* 1188:     */   public String getAnio()
/* 1189:     */   {
/* 1190:1537 */     return this.anio;
/* 1191:     */   }
/* 1192:     */   
/* 1193:     */   public void setAnio(String anio)
/* 1194:     */   {
/* 1195:1547 */     this.anio = anio;
/* 1196:     */   }
/* 1197:     */   
/* 1198:     */   public String getMes()
/* 1199:     */   {
/* 1200:1556 */     return this.mes;
/* 1201:     */   }
/* 1202:     */   
/* 1203:     */   public void setMes(String mes)
/* 1204:     */   {
/* 1205:1566 */     this.mes = mes;
/* 1206:     */   }
/* 1207:     */   
/* 1208:     */   public List<Sucursal> getListaSucursal()
/* 1209:     */   {
/* 1210:1575 */     if (this.listaSucursal == null) {
/* 1211:1576 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 1212:     */     }
/* 1213:1579 */     return this.listaSucursal;
/* 1214:     */   }
/* 1215:     */   
/* 1216:     */   public List<SelectItem> getListaResidenciaEmpleado()
/* 1217:     */   {
/* 1218:1588 */     if (this.listaResidenciaEmpleado == null)
/* 1219:     */     {
/* 1220:1589 */       this.listaResidenciaEmpleado = new ArrayList();
/* 1221:1590 */       for (ResidenciaTrabajador residencia : ResidenciaTrabajador.values())
/* 1222:     */       {
/* 1223:1591 */         SelectItem item = new SelectItem(residencia.getCodigo(), residencia.getNombre());
/* 1224:1592 */         this.listaResidenciaEmpleado.add(item);
/* 1225:     */       }
/* 1226:     */     }
/* 1227:1595 */     return this.listaResidenciaEmpleado;
/* 1228:     */   }
/* 1229:     */   
/* 1230:     */   public void setListaResidenciaEmpleado(List<SelectItem> listaResidenciaEmpleado)
/* 1231:     */   {
/* 1232:1605 */     this.listaResidenciaEmpleado = listaResidenciaEmpleado;
/* 1233:     */   }
/* 1234:     */   
/* 1235:     */   public List<SelectItem> getListaPaisesRDEP()
/* 1236:     */   {
/* 1237:1609 */     return DatosSRI.getListaPaises();
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   public List<SelectItem> getListaConvenioDobleImposicion()
/* 1241:     */   {
/* 1242:1618 */     if (this.listaConvenioDobleImposicion == null)
/* 1243:     */     {
/* 1244:1619 */       this.listaConvenioDobleImposicion = new ArrayList();
/* 1245:1620 */       for (ConvenioDobleImposicion convenio : ConvenioDobleImposicion.values())
/* 1246:     */       {
/* 1247:1621 */         SelectItem item = new SelectItem(convenio.getCodigo(), convenio.getNombre());
/* 1248:1622 */         this.listaConvenioDobleImposicion.add(item);
/* 1249:     */       }
/* 1250:     */     }
/* 1251:1625 */     return this.listaConvenioDobleImposicion;
/* 1252:     */   }
/* 1253:     */   
/* 1254:     */   public void setListaConvenioDobleImposicion(List<SelectItem> listaConvenioDobleImposicion)
/* 1255:     */   {
/* 1256:1635 */     this.listaConvenioDobleImposicion = listaConvenioDobleImposicion;
/* 1257:     */   }
/* 1258:     */   
/* 1259:     */   public List<SelectItem> getListaCondicionRespectoDiscapacidad()
/* 1260:     */   {
/* 1261:1644 */     if (this.listaCondicionRespectoDiscapacidad == null)
/* 1262:     */     {
/* 1263:1645 */       this.listaCondicionRespectoDiscapacidad = new ArrayList();
/* 1264:1646 */       for (CondicionRespectoDiscapacidad discapacidad : CondicionRespectoDiscapacidad.values())
/* 1265:     */       {
/* 1266:1647 */         SelectItem item = new SelectItem(discapacidad.getCodigo(), discapacidad.getNombre());
/* 1267:1648 */         this.listaCondicionRespectoDiscapacidad.add(item);
/* 1268:     */       }
/* 1269:     */     }
/* 1270:1651 */     return this.listaCondicionRespectoDiscapacidad;
/* 1271:     */   }
/* 1272:     */   
/* 1273:     */   public void setListaCondicionRespectoDiscapacidad(List<SelectItem> listaCondicionRespectoDiscapacidad)
/* 1274:     */   {
/* 1275:1661 */     this.listaCondicionRespectoDiscapacidad = listaCondicionRespectoDiscapacidad;
/* 1276:     */   }
/* 1277:     */   
/* 1278:     */   public List<SelectItem> getListaTipoIdentificacionSRI()
/* 1279:     */   {
/* 1280:1670 */     if (this.listaTipoIdentificacionSRI == null)
/* 1281:     */     {
/* 1282:1671 */       this.listaTipoIdentificacionSRI = new ArrayList();
/* 1283:1672 */       for (TipoIdentificacionSRI identificacion : TipoIdentificacionSRI.values())
/* 1284:     */       {
/* 1285:1673 */         SelectItem item = new SelectItem(identificacion.getCodigo(), identificacion.getNombre());
/* 1286:1674 */         this.listaTipoIdentificacionSRI.add(item);
/* 1287:     */       }
/* 1288:     */     }
/* 1289:1677 */     return this.listaTipoIdentificacionSRI;
/* 1290:     */   }
/* 1291:     */   
/* 1292:     */   public void setListaTipoIdentificacionSRI(List<SelectItem> listaTipoIdentificacionSRI)
/* 1293:     */   {
/* 1294:1687 */     this.listaTipoIdentificacionSRI = listaTipoIdentificacionSRI;
/* 1295:     */   }
/* 1296:     */   
/* 1297:     */   public List<SelectItem> getListaTipoSangre()
/* 1298:     */   {
/* 1299:1696 */     if (this.listaTipoSangre == null)
/* 1300:     */     {
/* 1301:1697 */       this.listaTipoSangre = new ArrayList();
/* 1302:1698 */       for (TipoSangre tipo : TipoSangre.values()) {
/* 1303:1699 */         this.listaTipoSangre.add(new SelectItem(tipo, tipo.getNombre()));
/* 1304:     */       }
/* 1305:     */     }
/* 1306:1702 */     return this.listaTipoSangre;
/* 1307:     */   }
/* 1308:     */   
/* 1309:     */   public void setListaTipoSangre(List<SelectItem> listaTipoSangre)
/* 1310:     */   {
/* 1311:1712 */     this.listaTipoSangre = listaTipoSangre;
/* 1312:     */   }
/* 1313:     */   
/* 1314:     */   public List<Contacto> getListaContacto()
/* 1315:     */   {
/* 1316:1716 */     List<Contacto> lista = new ArrayList();
/* 1317:1717 */     for (Contacto contacto : getEmpresa().getListaContacto()) {
/* 1318:1718 */       if (!contacto.isEliminado()) {
/* 1319:1719 */         lista.add(contacto);
/* 1320:     */       }
/* 1321:     */     }
/* 1322:1723 */     return lista;
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   public List<TipoContacto> getListaTipoContacto()
/* 1326:     */   {
/* 1327:1727 */     if (this.listaTipoContacto == null) {
/* 1328:1728 */       this.listaTipoContacto = this.servicioTipoContacto.obtenerListaCombo("nombre", true, null);
/* 1329:     */     }
/* 1330:1730 */     return this.listaTipoContacto;
/* 1331:     */   }
/* 1332:     */   
/* 1333:     */   public void setListaTipoContacto(List<TipoContacto> listaTipoContacto)
/* 1334:     */   {
/* 1335:1734 */     this.listaTipoContacto = listaTipoContacto;
/* 1336:     */   }
/* 1337:     */   
/* 1338:     */   public DataTable getDtListaDetalleDocumento()
/* 1339:     */   {
/* 1340:1738 */     return this.dtListaDetalleDocumento;
/* 1341:     */   }
/* 1342:     */   
/* 1343:     */   public void setDtListaDetalleDocumento(DataTable dtListaDetalleDocumento)
/* 1344:     */   {
/* 1345:1742 */     this.dtListaDetalleDocumento = dtListaDetalleDocumento;
/* 1346:     */   }
/* 1347:     */   
/* 1348:     */   public DetalleDocumentoDigitalizado getSelectedDetalleDocumento()
/* 1349:     */   {
/* 1350:1746 */     return this.selectedDetalleDocumento;
/* 1351:     */   }
/* 1352:     */   
/* 1353:     */   public void setSelectedDetalleDocumento(DetalleDocumentoDigitalizado selectedDetalleDocumento)
/* 1354:     */   {
/* 1355:1750 */     this.selectedDetalleDocumento = selectedDetalleDocumento;
/* 1356:     */   }
/* 1357:     */   
/* 1358:     */   public String seleccionar(DetalleDocumentoDigitalizado detalle)
/* 1359:     */   {
/* 1360:1754 */     this.selectedDetalleDocumento = detalle;
/* 1361:1755 */     System.out.println("--------->>" + this.selectedDetalleDocumento.getDocumentoDigitalizadoDepartamento().getDocumentoDigitalizado().getNombre());
/* 1362:     */     
/* 1363:1757 */     return "";
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   public List<DetalleDocumentoDigitalizado> getListaDetalleDocumentoDigitalizadoEmpleado()
/* 1367:     */   {
/* 1368:1761 */     if ((this.empresa == null) || (this.empresa.getEmpleado() == null) || (this.empresa.getEmpleado().getDepartamento() == null))
/* 1369:     */     {
/* 1370:1762 */       this.listaDetalleDocumentoDigitalizadoEmpleado = new ArrayList();
/* 1371:1763 */       return this.listaDetalleDocumentoDigitalizadoEmpleado;
/* 1372:     */     }
/* 1373:1765 */     if ((this.listaDetalleDocumentoDigitalizadoEmpleado == null) || (this.listaDetalleDocumentoDigitalizadoEmpleado.size() == 0))
/* 1374:     */     {
/* 1375:1766 */       int idOrganizacion = AppUtil.getOrganizacion().getIdOrganizacion();
/* 1376:1767 */       int idEmpleado = this.empresa.getEmpleado().getIdEmpleado();
/* 1377:1768 */       int idDepartamento = this.empresa.getEmpleado().getDepartamento().getIdDepartamento();
/* 1378:1769 */       this.listaDetalleDocumentoDigitalizadoEmpleado = this.servicioDetalleDocumentoDigitalizado.cargarListaDetalleDocumentoDigitalizadoEmpleado(idOrganizacion, idEmpleado, idDepartamento);
/* 1379:     */     }
/* 1380:1773 */     return this.listaDetalleDocumentoDigitalizadoEmpleado;
/* 1381:     */   }
/* 1382:     */   
/* 1383:     */   public void setListaDetalleDocumentoDigitalizadoEmpleado(List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizadoEmpleado)
/* 1384:     */   {
/* 1385:1777 */     this.listaDetalleDocumentoDigitalizadoEmpleado = listaDetalleDocumentoDigitalizadoEmpleado;
/* 1386:     */   }
/* 1387:     */   
/* 1388:     */   public StreamedContent getFile()
/* 1389:     */   {
/* 1390:1781 */     return this.file;
/* 1391:     */   }
/* 1392:     */   
/* 1393:     */   public void setFile(StreamedContent file)
/* 1394:     */   {
/* 1395:1785 */     this.file = file;
/* 1396:     */   }
/* 1397:     */   
/* 1398:     */   public void asignarFile(DetalleDocumentoDigitalizado detalleDoc)
/* 1399:     */   {
/* 1400:     */     try
/* 1401:     */     {
/* 1402:1790 */       String fileName = detalleDoc.getFichero();
/* 1403:     */       
/* 1404:1792 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "documentos_empleado");
/* 1405:1793 */       this.file = FuncionesUtiles.descargarArchivo(uploadDir + fileName, "", fileName);
/* 1406:     */     }
/* 1407:     */     catch (Exception e)
/* 1408:     */     {
/* 1409:1795 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 1410:1796 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 1411:     */     }
/* 1412:     */   }
/* 1413:     */   
/* 1414:     */   public void asignarFileLlamadoAtencion(LlamadoAtencion llamado)
/* 1415:     */   {
/* 1416:     */     try
/* 1417:     */     {
/* 1418:1802 */       String fileName = llamado.getFichero();
/* 1419:     */       
/* 1420:1804 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "llamados_atencion");
/* 1421:1805 */       this.fileLlamadoAtencion = FuncionesUtiles.descargarArchivo(uploadDir + fileName, "", fileName);
/* 1422:     */     }
/* 1423:     */     catch (Exception e)
/* 1424:     */     {
/* 1425:1807 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 1426:1808 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 1427:     */     }
/* 1428:     */   }
/* 1429:     */   
/* 1430:     */   public void agregarLlamadoAtencion()
/* 1431:     */   {
/* 1432:1813 */     LlamadoAtencion obj = new LlamadoAtencion();
/* 1433:1814 */     obj.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1434:1815 */     obj.setIdSucursal(AppUtil.getSucursal().getId());
/* 1435:1816 */     obj.setEmpleado(this.empresa.getEmpleado());
/* 1436:     */     
/* 1437:1818 */     this.empresa.getEmpleado().getListaLlamadoAtencion().add(obj);
/* 1438:     */   }
/* 1439:     */   
/* 1440:     */   public List<LlamadoAtencion> getListaLlamadosAtencion()
/* 1441:     */   {
/* 1442:1822 */     if (this.listaLlamadosAtencion == null)
/* 1443:     */     {
/* 1444:1823 */       this.listaLlamadosAtencion = new ArrayList();
/* 1445:1824 */       for (LlamadoAtencion llamado : this.servicioEmpresa.cargarListaLlamadosAtencion(getEmpresa().getEmpleado().getId())) {
/* 1446:1825 */         if (!llamado.isEliminado()) {
/* 1447:1826 */           this.listaLlamadosAtencion.add(llamado);
/* 1448:     */         }
/* 1449:     */       }
/* 1450:     */     }
/* 1451:1830 */     return this.listaLlamadosAtencion;
/* 1452:     */   }
/* 1453:     */   
/* 1454:     */   public void setListaLlamadosAtencion(List<LlamadoAtencion> listaLlamadosAtencion)
/* 1455:     */   {
/* 1456:1834 */     this.listaLlamadosAtencion = listaLlamadosAtencion;
/* 1457:     */   }
/* 1458:     */   
/* 1459:     */   public LlamadoAtencion getSelectedLlamadoAtencion()
/* 1460:     */   {
/* 1461:1838 */     return this.selectedLlamadoAtencion;
/* 1462:     */   }
/* 1463:     */   
/* 1464:     */   public void setSelectedLlamadoAtencion(LlamadoAtencion selectedLlamadoAtencion)
/* 1465:     */   {
/* 1466:1842 */     this.selectedLlamadoAtencion = selectedLlamadoAtencion;
/* 1467:     */   }
/* 1468:     */   
/* 1469:     */   public StreamedContent getFileLlamadoAtencion()
/* 1470:     */   {
/* 1471:1846 */     return this.fileLlamadoAtencion;
/* 1472:     */   }
/* 1473:     */   
/* 1474:     */   public void setFileLlamadoAtencion(StreamedContent fileLlamadoAtencion)
/* 1475:     */   {
/* 1476:1850 */     this.fileLlamadoAtencion = fileLlamadoAtencion;
/* 1477:     */   }
/* 1478:     */   
/* 1479:     */   public String getTipoFileUpload()
/* 1480:     */   {
/* 1481:1854 */     return this.tipoFileUpload;
/* 1482:     */   }
/* 1483:     */   
/* 1484:     */   public void setTipoFileUpload(String tipoFileUpload)
/* 1485:     */   {
/* 1486:1858 */     this.tipoFileUpload = tipoFileUpload;
/* 1487:     */   }
/* 1488:     */   
/* 1489:     */   public String getSizeLimit()
/* 1490:     */   {
/* 1491:1862 */     if (this.tipoFileUpload != null) {
/* 1492:1864 */       this.sizeLimit = (this.tipoFileUpload.equals("llamadoAtencion") ? "5000000" : this.tipoFileUpload.equals("documentoDigitalizado") ? "5000000" : this.tipoFileUpload.equals("fotoEmpleado") ? "1000000" : "1000000");
/* 1493:     */     } else {
/* 1494:1866 */       this.sizeLimit = "1000000";
/* 1495:     */     }
/* 1496:1868 */     return this.sizeLimit;
/* 1497:     */   }
/* 1498:     */   
/* 1499:     */   public void setSizeLimit(String sizeLimit)
/* 1500:     */   {
/* 1501:1872 */     this.sizeLimit = sizeLimit;
/* 1502:     */   }
/* 1503:     */   
/* 1504:     */   public String getAllowTypes()
/* 1505:     */   {
/* 1506:1876 */     if (this.tipoFileUpload != null) {
/* 1507:1879 */       this.allowTypes = (this.tipoFileUpload.equals("llamadoAtencion") ? "/(\\.|\\/)(gif|jpe?g|png|pdf|tif|zip|rar)$/" : this.tipoFileUpload.equals("documentoDigitalizado") ? "/(\\.|\\/)(gif|jpe?g|png|pdf|tif|zip|rar)$/" : this.tipoFileUpload.equals("fotoEmpleado") ? "/(\\.|\\/)(gif|jpe?g|png)$/" : "/(\\.|\\/)(gif|jpe?g|png)$/");
/* 1508:     */     } else {
/* 1509:1881 */       this.allowTypes = "/(\\.|\\/)(gif|jpe?g|png)$/";
/* 1510:     */     }
/* 1511:1883 */     return this.allowTypes;
/* 1512:     */   }
/* 1513:     */   
/* 1514:     */   public void setAllowTypes(String allowTypes)
/* 1515:     */   {
/* 1516:1887 */     this.allowTypes = allowTypes;
/* 1517:     */   }
/* 1518:     */   
/* 1519:     */   public List<Parroquia> listaParroquiasPorCiudad(Ciudad ciudad)
/* 1520:     */   {
/* 1521:1891 */     List<Parroquia> listaParroquiasPorCiudad = new ArrayList();
/* 1522:1892 */     if (ciudad != null) {
/* 1523:1893 */       listaParroquiasPorCiudad = this.servicioParroquia.obtenerListaCombo("", true, null, ciudad.getIdCiudad());
/* 1524:     */     }
/* 1525:1895 */     return listaParroquiasPorCiudad;
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   public List<HorarioEmpleado> getListaHorarioEmpleado()
/* 1529:     */   {
/* 1530:1899 */     Map<String, String> filters = new HashMap();
/* 1531:1900 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 1532:1901 */     return this.servicioHorarioEmpleado.obtenerListaCombo(HorarioEmpleado.class, "nombre", true, filters);
/* 1533:     */   }
/* 1534:     */   
/* 1535:     */   public void agregarDotacion()
/* 1536:     */   {
/* 1537:1905 */     DotacionEmpleado ojbDotacion = new DotacionEmpleado();
/* 1538:1906 */     ojbDotacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1539:1907 */     ojbDotacion.setIdSucursal(AppUtil.getSucursal().getId());
/* 1540:1908 */     ojbDotacion.setEmpleado(this.empresa.getEmpleado());
/* 1541:     */     
/* 1542:1910 */     this.empresa.getEmpleado().getListaDotacion().add(ojbDotacion);
/* 1543:     */   }
/* 1544:     */   
/* 1545:     */   public List<DotacionEmpleado> getListaDotacion()
/* 1546:     */   {
/* 1547:1915 */     List<DotacionEmpleado> lista = new ArrayList();
/* 1548:1916 */     for (DotacionEmpleado llamado : getEmpresa().getEmpleado().getListaDotacion()) {
/* 1549:1917 */       if (!llamado.isEliminado()) {
/* 1550:1918 */         lista.add(llamado);
/* 1551:     */       }
/* 1552:     */     }
/* 1553:1921 */     return lista;
/* 1554:     */   }
/* 1555:     */   
/* 1556:     */   public List<Producto> getListaProductos()
/* 1557:     */   {
/* 1558:1926 */     HashMap<String, String> filters = new HashMap();
/* 1559:1927 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 1560:1928 */     if (this.listaProductos == null) {
/* 1561:1929 */       this.listaProductos = this.servicioProducto.obtenerListaCombo("nombre", true, filters);
/* 1562:     */     }
/* 1563:1932 */     return this.listaProductos;
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   public String eliminarDotacion(DotacionEmpleado obj)
/* 1567:     */   {
/* 1568:1936 */     getListaDotacion();
/* 1569:1937 */     obj.setEliminado(true);
/* 1570:1938 */     return "";
/* 1571:     */   }
/* 1572:     */   
/* 1573:     */   public List<Producto> autocompletarProductos(String consulta)
/* 1574:     */   {
/* 1575:1942 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta);
/* 1576:     */   }
/* 1577:     */   
/* 1578:     */   public void setListaProductos(List<Producto> listaProductos)
/* 1579:     */   {
/* 1580:1946 */     this.listaProductos = listaProductos;
/* 1581:     */   }
/* 1582:     */   
/* 1583:     */   public List<CategoriaRubro> getListaCategoriaRubros()
/* 1584:     */   {
/* 1585:1951 */     if (this.listaCategoriaRubros == null)
/* 1586:     */     {
/* 1587:1952 */       Map<String, String> filtros = new HashMap();
/* 1588:1953 */       filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 1589:1954 */       filtros.put("activo", "true");
/* 1590:1955 */       this.listaCategoriaRubros = this.servicioCategoriaRubro.obtenerListaCombo(CategoriaRubro.class, "nombre", true, filtros);
/* 1591:     */     }
/* 1592:1958 */     return this.listaCategoriaRubros;
/* 1593:     */   }
/* 1594:     */   
/* 1595:     */   public void setListaCategoriaRubros(List<CategoriaRubro> listaCategoriaRubros)
/* 1596:     */   {
/* 1597:1962 */     this.listaCategoriaRubros = listaCategoriaRubros;
/* 1598:     */   }
/* 1599:     */   
/* 1600:     */   public String getCategoriaRubroSeleccionado()
/* 1601:     */   {
/* 1602:1966 */     this.categoriaRubroSeleccionado = null;
/* 1603:1967 */     if (getEmpresa().getEmpleado().getCategoriaRubro() != null) {
/* 1604:1968 */       this.categoriaRubroSeleccionado = ("" + getEmpresa().getEmpleado().getCategoriaRubro().getId());
/* 1605:     */     }
/* 1606:1971 */     return this.categoriaRubroSeleccionado;
/* 1607:     */   }
/* 1608:     */   
/* 1609:     */   public void setCategoriaRubroSeleccionado(String categoriaRubroSeleccionado)
/* 1610:     */   {
/* 1611:1976 */     if (this.categoriaRubroSeleccionado != categoriaRubroSeleccionado)
/* 1612:     */     {
/* 1613:1977 */       this.categoriaRubroSeleccionado = categoriaRubroSeleccionado;
/* 1614:1978 */       CategoriaRubro auxCategoriaRubroSeleccionado = null;
/* 1615:1979 */       if (this.categoriaRubroSeleccionado != "")
/* 1616:     */       {
/* 1617:1980 */         int idCategoriaRubroSeleccionado = Integer.parseInt(this.categoriaRubroSeleccionado);
/* 1618:1981 */         auxCategoriaRubroSeleccionado = (CategoriaRubro)this.servicioCategoriaRubro.buscarPorId(CategoriaRubro.class, Integer.valueOf(idCategoriaRubroSeleccionado));
/* 1619:     */       }
/* 1620:1983 */       getEmpresa().getEmpleado().setCategoriaRubro(auxCategoriaRubroSeleccionado);
/* 1621:     */     }
/* 1622:1986 */     this.categoriaRubroSeleccionado = categoriaRubroSeleccionado;
/* 1623:     */   }
/* 1624:     */   
/* 1625:     */   public TabView getTvDetallesEmpleado()
/* 1626:     */   {
/* 1627:1990 */     return this.tvDetallesEmpleado;
/* 1628:     */   }
/* 1629:     */   
/* 1630:     */   public void setTvDetallesEmpleado(TabView tvDetallesEmpleado)
/* 1631:     */   {
/* 1632:1994 */     this.tvDetallesEmpleado = tvDetallesEmpleado;
/* 1633:     */   }
/* 1634:     */   
/* 1635:     */   public boolean isActivadorTipoServicio()
/* 1636:     */   {
/* 1637:2000 */     return this.activadorTipoServicio;
/* 1638:     */   }
/* 1639:     */   
/* 1640:     */   public void setActivadorTipoServicio(boolean activadorTipoServicio)
/* 1641:     */   {
/* 1642:2004 */     this.activadorTipoServicio = activadorTipoServicio;
/* 1643:     */   }
/* 1644:     */   
/* 1645:     */   public List<Ciudad> autocompletarCiudad(String consulta)
/* 1646:     */   {
/* 1647:2008 */     return this.servicioCiudad.autocompletarCiudad(consulta);
/* 1648:     */   }
/* 1649:     */   
/* 1650:     */   public int getNumeroHuella()
/* 1651:     */   {
/* 1652:2012 */     return this.numeroHuella;
/* 1653:     */   }
/* 1654:     */   
/* 1655:     */   public void setNumeroHuella(int numeroHuella)
/* 1656:     */   {
/* 1657:2016 */     this.numeroHuella = numeroHuella;
/* 1658:     */   }
/* 1659:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.EmpleadoBean
 * JD-Core Version:    0.7.0.1
 */