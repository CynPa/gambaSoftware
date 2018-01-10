/*    1:     */ package com.asinfo.as2.datosbase.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioParroquia;
/*    6:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*    7:     */ import com.asinfo.as2.dao.ClienteDao;
/*    8:     */ import com.asinfo.as2.dao.ContactoDao;
/*    9:     */ import com.asinfo.as2.dao.CuentaBancariaDao;
/*   10:     */ import com.asinfo.as2.dao.CuentaBancariaEmpresaDao;
/*   11:     */ import com.asinfo.as2.dao.DireccionEmpresaDao;
/*   12:     */ import com.asinfo.as2.dao.EmpleadoDao;
/*   13:     */ import com.asinfo.as2.dao.EmpresaDao;
/*   14:     */ import com.asinfo.as2.dao.FormaPagoSRIDao;
/*   15:     */ import com.asinfo.as2.dao.GenericoDao;
/*   16:     */ import com.asinfo.as2.dao.ProductoUltimaCompraDao;
/*   17:     */ import com.asinfo.as2.dao.ProveedorDao;
/*   18:     */ import com.asinfo.as2.dao.RecargoListaPreciosClienteDao;
/*   19:     */ import com.asinfo.as2.dao.SubempresaDao;
/*   20:     */ import com.asinfo.as2.dao.UbicacionDao;
/*   21:     */ import com.asinfo.as2.dao.sri.AutorizacionProveedorSRIDao;
/*   22:     */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   23:     */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   24:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   25:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresaRemote;
/*   26:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   27:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*   28:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   29:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   30:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   31:     */ import com.asinfo.as2.entities.Ciudad;
/*   32:     */ import com.asinfo.as2.entities.Cliente;
/*   33:     */ import com.asinfo.as2.entities.CondicionPago;
/*   34:     */ import com.asinfo.as2.entities.Contacto;
/*   35:     */ import com.asinfo.as2.entities.CuentaBancariaEmpresa;
/*   36:     */ import com.asinfo.as2.entities.Departamento;
/*   37:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   38:     */ import com.asinfo.as2.entities.DotacionEmpleado;
/*   39:     */ import com.asinfo.as2.entities.Empleado;
/*   40:     */ import com.asinfo.as2.entities.Empresa;
/*   41:     */ import com.asinfo.as2.entities.EmpresaAtributo;
/*   42:     */ import com.asinfo.as2.entities.FormaPago;
/*   43:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*   44:     */ import com.asinfo.as2.entities.FormacionAcademica;
/*   45:     */ import com.asinfo.as2.entities.ListaDescuentos;
/*   46:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   47:     */ import com.asinfo.as2.entities.LlamadoAtencion;
/*   48:     */ import com.asinfo.as2.entities.Organizacion;
/*   49:     */ import com.asinfo.as2.entities.ProductoUltimaCompra;
/*   50:     */ import com.asinfo.as2.entities.Proveedor;
/*   51:     */ import com.asinfo.as2.entities.Provincia;
/*   52:     */ import com.asinfo.as2.entities.RecargoListaPreciosCliente;
/*   53:     */ import com.asinfo.as2.entities.Subempresa;
/*   54:     */ import com.asinfo.as2.entities.Sucursal;
/*   55:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   56:     */ import com.asinfo.as2.entities.Ubicacion;
/*   57:     */ import com.asinfo.as2.entities.ValorAtributo;
/*   58:     */ import com.asinfo.as2.entities.Zona;
/*   59:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   60:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*   61:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   62:     */ import com.asinfo.as2.enumeraciones.Genero;
/*   63:     */ import com.asinfo.as2.enumeraciones.MetodoFacturacionEnum;
/*   64:     */ import com.asinfo.as2.enumeraciones.ReporteEnvioMailsEnum;
/*   65:     */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*   66:     */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*   67:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   68:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   69:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*   70:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioFormacionAcademica;
/*   71:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioLlamadoAtencion;
/*   72:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   73:     */ import com.asinfo.as2.util.AppUtil;
/*   74:     */ import com.asinfo.as2.utils.DatosSRI;
/*   75:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   76:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*   77:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*   78:     */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*   79:     */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*   80:     */ import java.io.PrintStream;
/*   81:     */ import java.math.BigDecimal;
/*   82:     */ import java.text.SimpleDateFormat;
/*   83:     */ import java.util.ArrayList;
/*   84:     */ import java.util.Date;
/*   85:     */ import java.util.HashMap;
/*   86:     */ import java.util.Iterator;
/*   87:     */ import java.util.List;
/*   88:     */ import java.util.Map;
/*   89:     */ import javax.annotation.Resource;
/*   90:     */ import javax.ejb.EJB;
/*   91:     */ import javax.ejb.SessionContext;
/*   92:     */ import javax.ejb.Stateless;
/*   93:     */ import javax.ejb.TransactionAttribute;
/*   94:     */ import javax.ejb.TransactionAttributeType;
/*   95:     */ import javax.ejb.TransactionManagement;
/*   96:     */ import javax.ejb.TransactionManagementType;
/*   97:     */ import javax.faces.model.SelectItem;
/*   98:     */ import javax.persistence.NoResultException;
/*   99:     */ import org.hibernate.Hibernate;
/*  100:     */ 
/*  101:     */ @Stateless
/*  102:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  103:     */ public class ServicioEmpresaImpl
/*  104:     */   implements ServicioEmpresa, ServicioEmpresaRemote
/*  105:     */ {
/*  106:     */   @EJB
/*  107:     */   private transient EmpresaDao empresaDao;
/*  108:     */   @EJB
/*  109:     */   private transient ClienteDao clienteDao;
/*  110:     */   @EJB
/*  111:     */   private transient ProveedorDao proveedorDao;
/*  112:     */   @EJB
/*  113:     */   private transient EmpleadoDao empleadoDao;
/*  114:     */   @EJB
/*  115:     */   private transient DireccionEmpresaDao direccionEmpresaDao;
/*  116:     */   @EJB
/*  117:     */   private transient UbicacionDao ubicacionDao;
/*  118:     */   @EJB
/*  119:     */   private transient AutorizacionProveedorSRIDao autorizacionProveedorSRIDao;
/*  120:     */   @EJB
/*  121:     */   private transient CuentaBancariaEmpresaDao cuentaBancariaEmpresaDao;
/*  122:     */   @EJB
/*  123:     */   private transient CuentaBancariaDao cuentaBancariaDao;
/*  124:     */   @EJB
/*  125:     */   private transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  126:     */   @EJB
/*  127:     */   private transient ServicioFormacionAcademica servicioFormacionAcademica;
/*  128:     */   @EJB
/*  129:     */   private transient ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  130:     */   @EJB
/*  131:     */   private transient ServicioFormaPago servicioFormaPago;
/*  132:     */   @EJB
/*  133:     */   private transient ServicioCondicionPago servicioCondicionPago;
/*  134:     */   @EJB
/*  135:     */   private transient ServicioCiudad servicioCiudad;
/*  136:     */   @EJB
/*  137:     */   private transient ServicioParroquia servicioParroquia;
/*  138:     */   @EJB
/*  139:     */   private transient ServicioListaPrecios servicioListaPrecios;
/*  140:     */   @EJB
/*  141:     */   private transient ServicioListaDescuentos servicioListaDescuentos;
/*  142:     */   @EJB
/*  143:     */   private transient ServicioZona servicioZona;
/*  144:     */   @EJB
/*  145:     */   private transient RecargoListaPreciosClienteDao recargoListaPreciosClienteDao;
/*  146:     */   @EJB
/*  147:     */   private transient SubempresaDao subempresaDao;
/*  148:     */   @EJB
/*  149:     */   private transient FormaPagoSRIDao formaPagoSRIDao;
/*  150:     */   @EJB
/*  151:     */   private transient ContactoDao contactoDao;
/*  152:     */   @EJB
/*  153:     */   private transient ServicioLlamadoAtencion servicioLlamadoAtencion;
/*  154:     */   @EJB
/*  155:     */   private transient GenericoDao<DotacionEmpleado> dotacionDao;
/*  156:     */   @EJB
/*  157:     */   private transient ServicioTransportista servicioTransportista;
/*  158:     */   @EJB
/*  159:     */   private transient ServicioGenerico<EmpresaAtributo> servicioEmpresaAtributo;
/*  160:     */   @EJB
/*  161:     */   private transient ServicioVerificadorVentas servicioVerificadorVentas;
/*  162:     */   @Resource
/*  163:     */   protected SessionContext context;
/*  164:     */   @EJB
/*  165:     */   private transient ProductoUltimaCompraDao productoUltimaCompraDao;
/*  166:     */   @EJB
/*  167:     */   private transient ServicioFormaPagoSRI servicioFormaPagoSRI;
/*  168:     */   
/*  169:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  170:     */   public void guardar(Empresa empresa)
/*  171:     */     throws AS2Exception, ExcepcionAS2, ExcepcionAS2Identification, Exception
/*  172:     */   {
/*  173: 193 */     if (!empresa.isEliminado()) {
/*  174: 194 */       validar(empresa);
/*  175:     */     }
/*  176: 196 */     int idEmpresa = empresa.getId();
/*  177:     */     try
/*  178:     */     {
/*  179: 201 */       if (Hibernate.isInitialized(empresa.getListaEmpresaAtributo())) {
/*  180: 202 */         for (EmpresaAtributo empresaAtributo : empresa.getListaEmpresaAtributo())
/*  181:     */         {
/*  182: 204 */           if (empresaAtributo.getValorAtributo() != null)
/*  183:     */           {
/*  184: 205 */             empresaAtributo.setValor(empresaAtributo.getValorAtributo().getNombre());
/*  185: 206 */             empresaAtributo.setEliminado(false);
/*  186:     */           }
/*  187: 208 */           if ((empresaAtributo.getValor().isEmpty()) && (empresaAtributo.getValorAtributo() == null)) {
/*  188: 209 */             empresaAtributo.setEliminado(true);
/*  189:     */           }
/*  190: 211 */           this.servicioEmpresaAtributo.guardar(empresaAtributo);
/*  191:     */         }
/*  192:     */       }
/*  193: 215 */       if (empresa.getCliente() != null)
/*  194:     */       {
/*  195: 216 */         if (empresa.getCliente().getVersion() == null) {
/*  196: 217 */           empresa.getCliente().setVersion("1.1.0");
/*  197:     */         }
/*  198: 219 */         if ((empresa.getCliente().getCreditoMaximo() == null) || (empresa.getCliente().getCreditoMaximo().equals(BigDecimal.ZERO))) {
/*  199: 220 */           actualizarCreditoMaximo(empresa);
/*  200:     */         }
/*  201: 223 */         this.clienteDao.guardar(empresa.getCliente());
/*  202:     */       }
/*  203: 226 */       if ((idEmpresa != 0) && (empresa.getProveedor() != null))
/*  204:     */       {
/*  205: 227 */         if ((empresa.getProveedor().isIndicadorPagoCash()) && (empresa.getListaCuentaBancariaEmpresa().isEmpty()) && 
/*  206: 228 */           (empresa.getDirecciones().isEmpty())) {
/*  207: 229 */           throw new ExcepcionAS2("msg_error_cuenta_bancaria_direccion_pago_cash");
/*  208:     */         }
/*  209: 231 */         this.proveedorDao.guardar(empresa.getProveedor());
/*  210:     */       }
/*  211: 234 */       if (empresa.getEmpleado() != null)
/*  212:     */       {
/*  213: 235 */         setearYValidarValoresSRI(empresa.getEmpleado());
/*  214: 236 */         if ((empresa.getEmpleado().isIndicadorPagoCash()) && 
/*  215: 237 */           (empresa.getListaCuentaBancariaEmpresa().isEmpty()) && (empresa.getDirecciones().isEmpty())) {
/*  216: 238 */           throw new ExcepcionAS2("msg_error_cuenta_bancaria_direccion_pago_cash");
/*  217:     */         }
/*  218: 240 */         for (FormacionAcademica formacionAcademica : empresa.getEmpleado().getListaFormacionAcademica()) {
/*  219: 241 */           if (formacionAcademica.getAnioDesde() >= formacionAcademica.getAnioHasta())
/*  220:     */           {
/*  221: 242 */             if (formacionAcademica.getMesDesde() > formacionAcademica.getMesHasta()) {
/*  222: 243 */               throw new ExcepcionAS2("msg_error_fecha_hasta");
/*  223:     */             }
/*  224: 245 */             this.servicioFormacionAcademica.guardar(formacionAcademica);
/*  225:     */           }
/*  226:     */           else
/*  227:     */           {
/*  228: 248 */             this.servicioFormacionAcademica.guardar(formacionAcademica);
/*  229:     */           }
/*  230:     */         }
/*  231: 254 */         empresa.setEmail1(empresa.getEmail1() != null ? empresa.getEmail1().trim() : null);
/*  232: 255 */         empresa.setEmail2(empresa.getEmail2() != null ? empresa.getEmail2().trim() : null);
/*  233: 256 */         empresa.setEmail3(empresa.getEmail3() != null ? empresa.getEmail3().trim() : null);
/*  234:     */         
/*  235: 258 */         this.empleadoDao.guardar(empresa.getEmpleado());
/*  236: 260 */         for (DotacionEmpleado elemento : empresa.getEmpleado().getListaDotacion()) {
/*  237: 261 */           this.dotacionDao.guardar(elemento);
/*  238:     */         }
/*  239:     */       }
/*  240: 266 */       configurarDireccionComoPrincipal(empresa);
/*  241: 269 */       for (DireccionEmpresa direccionEmpresa : empresa.getDirecciones())
/*  242:     */       {
/*  243: 270 */         this.direccionEmpresaDao.guardar(direccionEmpresa);
/*  244: 271 */         this.ubicacionDao.guardar(direccionEmpresa.getUbicacion());
/*  245:     */       }
/*  246: 275 */       if (empresa.isIndicadorCliente())
/*  247:     */       {
/*  248: 276 */         for (RecargoListaPreciosCliente recago : empresa.getListaRecargoListaPreciosCliente()) {
/*  249: 277 */           this.recargoListaPreciosClienteDao.guardar(recago);
/*  250:     */         }
/*  251: 279 */         for (Subempresa subempresa : empresa.getListaSubempresa()) {
/*  252: 280 */           this.subempresaDao.guardar(subempresa);
/*  253:     */         }
/*  254:     */       }
/*  255: 284 */       if (empresa.isIndicadorProveedor()) {
/*  256: 285 */         for (Subempresa subempresa : empresa.getListaSubempresa()) {
/*  257: 286 */           this.subempresaDao.guardar(subempresa);
/*  258:     */         }
/*  259:     */       }
/*  260: 291 */       for (AutorizacionProveedorSRI autorizacionProveedorSRI : empresa.getListaAutorizacionProveedorSRI()) {
/*  261: 292 */         this.autorizacionProveedorSRIDao.guardar(autorizacionProveedorSRI);
/*  262:     */       }
/*  263: 296 */       for (CuentaBancariaEmpresa cuentaBancariaEmpresa : empresa.getListaCuentaBancariaEmpresa())
/*  264:     */       {
/*  265: 297 */         this.cuentaBancariaEmpresaDao.guardar(cuentaBancariaEmpresa);
/*  266: 298 */         this.cuentaBancariaDao.guardar(cuentaBancariaEmpresa.getCuentaBancaria());
/*  267:     */       }
/*  268: 301 */       for (FormaPagoSRI formaPagoSRI : empresa.getListaFormaPagoSRI()) {
/*  269: 302 */         this.formaPagoSRIDao.guardar(formaPagoSRI);
/*  270:     */       }
/*  271: 306 */       for (Contacto contacto : empresa.getListaContacto()) {
/*  272: 307 */         this.contactoDao.guardar(contacto);
/*  273:     */       }
/*  274: 311 */       this.empresaDao.guardar(empresa);
/*  275: 312 */       if ((idEmpresa == 0) && (empresa.getProveedor() != null))
/*  276:     */       {
/*  277: 313 */         if ((empresa.getProveedor().isIndicadorPagoCash()) && (empresa.getListaCuentaBancariaEmpresa().isEmpty()) && 
/*  278: 314 */           (empresa.getDirecciones().isEmpty())) {
/*  279: 315 */           throw new ExcepcionAS2("msg_error_cuenta_bancaria_direccion_pago_cash");
/*  280:     */         }
/*  281: 317 */         this.proveedorDao.guardar(empresa.getProveedor());
/*  282:     */       }
/*  283: 321 */       if (empresa.isIndicadorProveedor()) {
/*  284: 322 */         this.servicioTransportista.actualizarEmpresaTransportista(empresa);
/*  285:     */       }
/*  286:     */     }
/*  287:     */     catch (ExcepcionAS2 e)
/*  288:     */     {
/*  289: 326 */       this.context.setRollbackOnly();
/*  290: 327 */       throw e;
/*  291:     */     }
/*  292:     */     catch (Exception e)
/*  293:     */     {
/*  294: 329 */       this.context.setRollbackOnly();
/*  295: 330 */       throw e;
/*  296:     */     }
/*  297:     */   }
/*  298:     */   
/*  299:     */   public void actualizarCreditoMaximo(Empresa empresa)
/*  300:     */   {
/*  301: 336 */     if ((empresa != null) && (empresa.getCliente() != null) && (empresa.getCliente().getId() == 0))
/*  302:     */     {
/*  303: 337 */       empresa.getCliente().setCreditoMaximo(empresa.getCategoriaEmpresa().getCreditoMaximo());
/*  304: 338 */       empresa.getCliente().setCreditoDisponible(empresa.getCliente().getCreditoMaximo());
/*  305:     */     }
/*  306:     */   }
/*  307:     */   
/*  308:     */   public void eliminar(Empresa entidad)
/*  309:     */     throws ExcepcionAS2, ExcepcionAS2Identification, Exception
/*  310:     */   {
/*  311:     */     try
/*  312:     */     {
/*  313: 351 */       Empresa empresa = this.empresaDao.cargarDetalle(entidad);
/*  314: 352 */       empresa = this.empresaDao.cargarDetalleAtributos(empresa);
/*  315: 353 */       empresa.setEliminado(true);
/*  316:     */       
/*  317: 355 */       guardar(empresa);
/*  318:     */     }
/*  319:     */     catch (NoResultException e)
/*  320:     */     {
/*  321: 358 */       throw new ExcepcionAS2("msg_error_eliminar");
/*  322:     */     }
/*  323:     */   }
/*  324:     */   
/*  325:     */   public Empresa buscarPorId(Integer id)
/*  326:     */   {
/*  327: 370 */     return this.empresaDao.buscarPorId(id);
/*  328:     */   }
/*  329:     */   
/*  330:     */   public Empresa cargarDetalle(Empresa empresa)
/*  331:     */   {
/*  332: 380 */     return this.empresaDao.cargarDetalle(empresa);
/*  333:     */   }
/*  334:     */   
/*  335:     */   public Empresa cargarDetalle(Empresa empresa, boolean cargarDetalles)
/*  336:     */   {
/*  337: 385 */     return this.empresaDao.cargarDetalle(empresa, cargarDetalles);
/*  338:     */   }
/*  339:     */   
/*  340:     */   public Empresa cargarDetalleTodo(Empresa empresa)
/*  341:     */   {
/*  342: 396 */     empresa = this.empresaDao.cargarDetalle(empresa);
/*  343: 397 */     for (DireccionEmpresa de : empresa.getDirecciones())
/*  344:     */     {
/*  345: 398 */       Map<String, String> filters = new HashMap();
/*  346: 399 */       de.getCiudad().setListaParroquia(this.servicioParroquia.obtenerListaCombo("nombre", true, filters, de.getCiudad().getId()));
/*  347:     */     }
/*  348: 402 */     return empresa;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public Empresa cargarDetalleAtributos(Empresa empresa)
/*  352:     */   {
/*  353: 407 */     return this.empresaDao.cargarDetalleAtributos(empresa);
/*  354:     */   }
/*  355:     */   
/*  356:     */   public List<Empresa> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  357:     */   {
/*  358: 417 */     return this.empresaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  359:     */   }
/*  360:     */   
/*  361:     */   public List<Empresa> obtenerClientes(String sortField, boolean sortOrder, Map<String, String> filters)
/*  362:     */   {
/*  363: 428 */     if (filters == null) {
/*  364: 429 */       filters = new HashMap();
/*  365:     */     }
/*  366: 431 */     if (!filters.containsKey("indicadorCliente")) {
/*  367: 432 */       filters.put("indicadorCliente", "true");
/*  368:     */     }
/*  369: 434 */     return this.empresaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  370:     */   }
/*  371:     */   
/*  372:     */   public List<Empresa> obtenerClientes()
/*  373:     */   {
/*  374: 444 */     return obtenerClientes(null, false, null);
/*  375:     */   }
/*  376:     */   
/*  377:     */   public List<Empresa> obtenerProveedores(String sortField, boolean sortOrder, Map<String, String> filters)
/*  378:     */   {
/*  379: 454 */     if (filters == null) {
/*  380: 455 */       filters = new HashMap();
/*  381:     */     }
/*  382: 457 */     if (!filters.containsKey("indicadorProveedor")) {
/*  383: 458 */       filters.put("indicadorProveedor", "true");
/*  384:     */     }
/*  385: 461 */     return this.empresaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  386:     */   }
/*  387:     */   
/*  388:     */   public List<Empresa> obtenerProveedores()
/*  389:     */   {
/*  390: 471 */     return obtenerProveedores(null, false, null);
/*  391:     */   }
/*  392:     */   
/*  393:     */   public List<DireccionEmpresa> obtenerListaComboDirecciones(int idEmpresa)
/*  394:     */   {
/*  395: 481 */     return this.direccionEmpresaDao.obtenerListaComboDirecciones(idEmpresa);
/*  396:     */   }
/*  397:     */   
/*  398:     */   public List<Contacto> obtenerListaComboContactos(Empresa empresa)
/*  399:     */   {
/*  400: 485 */     HashMap<String, String> filtros = new HashMap();
/*  401: 486 */     filtros.put("empresa.idEmpresa", String.valueOf(empresa.getIdEmpresa()));
/*  402: 487 */     return this.contactoDao.obtenerListaCombo("nombre", false, filtros);
/*  403:     */   }
/*  404:     */   
/*  405:     */   public Long verificaClienteListaPrecios(int idEmpresa)
/*  406:     */   {
/*  407: 497 */     return this.clienteDao.verificaClienteListaPrecios(idEmpresa);
/*  408:     */   }
/*  409:     */   
/*  410:     */   public boolean verificaExcentaImpuestos(int idEmpresa)
/*  411:     */   {
/*  412: 507 */     return this.clienteDao.verificaExcentaImpuestos(idEmpresa);
/*  413:     */   }
/*  414:     */   
/*  415:     */   public List<Empresa> autocompletarClientes(String consulta)
/*  416:     */   {
/*  417: 512 */     return autocompletarClientes(consulta, false, null, false);
/*  418:     */   }
/*  419:     */   
/*  420:     */   public List<Empresa> autocompletarClientes(String consulta, boolean filtrarActivos)
/*  421:     */   {
/*  422: 517 */     return autocompletarClientes(consulta, filtrarActivos, null, false);
/*  423:     */   }
/*  424:     */   
/*  425:     */   public List<Empresa> autocompletarClientes(String consulta, boolean filtrarActivos, CategoriaEmpresa categoriaEmpresa)
/*  426:     */   {
/*  427: 522 */     return autocompletarClientes(consulta, filtrarActivos, categoriaEmpresa, false);
/*  428:     */   }
/*  429:     */   
/*  430:     */   public List<Empresa> autocompletarClientes(String consulta, boolean filtrarActivos, CategoriaEmpresa categoriaEmpresa, boolean verificarBloqueoCliente)
/*  431:     */   {
/*  432: 532 */     List<Empresa> lista = new ArrayList();
/*  433:     */     
/*  434: 534 */     String sortField = "codigo";
/*  435: 535 */     System.out.println(verificarBloqueoCliente);
/*  436: 536 */     HashMap<String, String> filters = new HashMap();
/*  437: 537 */     filters.put("nombreFiscal", consulta.trim());
/*  438: 538 */     filters.put("nombreComercial", consulta.trim());
/*  439: 539 */     filters.put("identificacion", consulta.trim());
/*  440: 541 */     if (verificarBloqueoCliente) {
/*  441: 542 */       filters.put("cliente.indicadorClienteBloqueado", "false");
/*  442:     */     }
/*  443: 544 */     filters.put("cliente.tipoCliente", "!=" + TipoEmpresaEnum.SUBCLIENTE.toString());
/*  444: 545 */     if (filtrarActivos) {
/*  445: 546 */       filters.put("activo", "true");
/*  446:     */     }
/*  447: 548 */     if (categoriaEmpresa != null) {
/*  448: 549 */       filters.put("categoriaEmpresa.idCategoriaEmpresa", "" + categoriaEmpresa.getId());
/*  449:     */     }
/*  450: 552 */     lista = obtenerClientes(sortField, true, filters);
/*  451:     */     
/*  452: 554 */     return lista;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public List<Empresa> autocompletarClientes(String consulta, Map<String, String> filters)
/*  456:     */   {
/*  457: 559 */     List<Empresa> lista = new ArrayList();
/*  458:     */     
/*  459: 561 */     String sortField = "codigo";
/*  460: 562 */     filters.put("nombreFiscal", consulta.trim());
/*  461: 563 */     filters.put("nombreComercial", consulta.trim());
/*  462: 564 */     filters.put("identificacion", consulta.trim());
/*  463:     */     
/*  464: 566 */     lista = obtenerClientes(sortField, true, filters);
/*  465:     */     
/*  466: 568 */     return lista;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public List<Empresa> autocompletarClientesTransportista(String consulta, HashMap<String, String> filters)
/*  470:     */   {
/*  471: 572 */     List<Empresa> lista = new ArrayList();
/*  472:     */     
/*  473: 574 */     String sortField = "codigo";
/*  474: 575 */     filters.put("nombreFiscal", consulta.trim());
/*  475: 576 */     filters.put("nombreComercial", consulta.trim());
/*  476: 577 */     filters.put("identificacion", consulta.trim());
/*  477:     */     
/*  478: 579 */     lista = obtenerClientes(sortField, true, filters);
/*  479:     */     
/*  480: 581 */     return lista;
/*  481:     */   }
/*  482:     */   
/*  483:     */   public List<Empresa> autocompletarProveedores(String consulta)
/*  484:     */   {
/*  485: 586 */     return autocompletarProveedores(consulta, false);
/*  486:     */   }
/*  487:     */   
/*  488:     */   public List<Empresa> autocompletarProveedores(String consulta, boolean indicadorActivos)
/*  489:     */   {
/*  490: 591 */     return autocompletarProveedores(consulta, indicadorActivos, null);
/*  491:     */   }
/*  492:     */   
/*  493:     */   public List<Empresa> autocompletarProveedores(String consulta, boolean indicadorActivos, CategoriaEmpresa categoriaEmpresa)
/*  494:     */   {
/*  495: 602 */     List<Empresa> lista = new ArrayList();
/*  496:     */     
/*  497: 604 */     String sortField = "codigo";
/*  498: 605 */     HashMap<String, String> filters = new HashMap();
/*  499: 606 */     filters.put("nombreFiscal", consulta.trim());
/*  500: 607 */     filters.put("nombreComercial", consulta.trim());
/*  501: 608 */     filters.put("identificacion", consulta.trim());
/*  502: 609 */     if (indicadorActivos) {
/*  503: 610 */       filters.put("activo", "true");
/*  504:     */     }
/*  505: 613 */     if (categoriaEmpresa != null) {
/*  506: 614 */       filters.put("categoriaEmpresa.idCategoriaEmpresa", "" + categoriaEmpresa.getId());
/*  507:     */     }
/*  508: 617 */     lista = obtenerProveedores(sortField, true, filters);
/*  509:     */     
/*  510: 619 */     return lista;
/*  511:     */   }
/*  512:     */   
/*  513:     */   public List<Empresa> obtenerEmpleados(String sortField, boolean sortOrder, Map<String, String> filters)
/*  514:     */   {
/*  515: 629 */     if (filters == null) {
/*  516: 630 */       filters = new HashMap();
/*  517:     */     }
/*  518: 632 */     if (!filters.containsKey("indicadorEmpleado")) {
/*  519: 633 */       filters.put("indicadorEmpleado", "true");
/*  520:     */     }
/*  521: 636 */     return this.empresaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  522:     */   }
/*  523:     */   
/*  524:     */   public List<Empresa> obtenerEmpleados()
/*  525:     */   {
/*  526: 646 */     return obtenerEmpleados(null, false, null);
/*  527:     */   }
/*  528:     */   
/*  529:     */   public List<Empresa> autocompletarEmpleados(String consulta)
/*  530:     */   {
/*  531: 656 */     List<Empresa> lista = new ArrayList();
/*  532:     */     
/*  533: 658 */     String sortField = "codigo";
/*  534: 659 */     HashMap<String, String> filters = new HashMap();
/*  535: 660 */     filters.put("nombreFiscal", consulta.trim());
/*  536: 661 */     filters.put("nombreComercial", consulta.trim());
/*  537: 662 */     filters.put("identificacion", consulta.trim());
/*  538: 663 */     filters.put("codigo", consulta.trim());
/*  539: 664 */     lista = obtenerEmpleados(sortField, true, filters);
/*  540:     */     
/*  541: 666 */     return lista;
/*  542:     */   }
/*  543:     */   
/*  544:     */   public List<Empresa> autocompletarAgenteComercial(String consulta)
/*  545:     */   {
/*  546: 676 */     List<Empresa> lista = new ArrayList();
/*  547:     */     
/*  548: 678 */     String sortField = "codigo";
/*  549: 679 */     HashMap<String, String> filters = new HashMap();
/*  550: 680 */     filters.put("nombreFiscal", consulta.trim());
/*  551: 681 */     filters.put("nombreComercial", consulta.trim());
/*  552: 682 */     filters.put("identificacion", consulta.trim());
/*  553: 683 */     filters.put("indicadorAgenteComercial", consulta.trim());
/*  554: 684 */     lista = obtenerEmpleados(sortField, true, filters);
/*  555:     */     
/*  556: 686 */     return lista;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public List<Empresa> obtenerListaComboAgentesComerciales()
/*  560:     */   {
/*  561: 696 */     return obtenerEmpleados(null, true, null);
/*  562:     */   }
/*  563:     */   
/*  564:     */   public Empleado buscarEmpleadoPorId(Integer id)
/*  565:     */   {
/*  566: 706 */     return (Empleado)this.empleadoDao.buscarPorId(id);
/*  567:     */   }
/*  568:     */   
/*  569:     */   public List<Empleado> listaAgenteComercialCombo()
/*  570:     */   {
/*  571: 711 */     return this.empleadoDao.listaAgenteComercialCombo();
/*  572:     */   }
/*  573:     */   
/*  574:     */   public DireccionEmpresa buscarDireccionEmpresaPorId(int idDireccionEmpresa)
/*  575:     */   {
/*  576: 721 */     DireccionEmpresa direccionEmpresa = (DireccionEmpresa)this.direccionEmpresaDao.buscarPorId(Integer.valueOf(idDireccionEmpresa));
/*  577: 722 */     direccionEmpresa.getId();
/*  578: 723 */     if (direccionEmpresa.getEmpresa() != null) {
/*  579: 724 */       direccionEmpresa.getEmpresa().getIdEmpresa();
/*  580:     */     }
/*  581: 726 */     direccionEmpresa.getUbicacion().getId();
/*  582: 727 */     direccionEmpresa.getCiudad().getId();
/*  583: 728 */     direccionEmpresa.getCiudad().getProvincia().getId();
/*  584: 729 */     direccionEmpresa.getCiudad().getProvincia().getId();
/*  585: 730 */     return direccionEmpresa;
/*  586:     */   }
/*  587:     */   
/*  588:     */   public List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRI(int idEmpresa, Date fechaFacturaProveedor)
/*  589:     */   {
/*  590: 740 */     return this.empresaDao.listaAutorizacionProveedorSRI(idEmpresa, fechaFacturaProveedor);
/*  591:     */   }
/*  592:     */   
/*  593:     */   public AutorizacionProveedorSRI buscarAutorizacionProveedorSRIPorId(int idAutorizacionProveedorSRI)
/*  594:     */   {
/*  595: 750 */     return (AutorizacionProveedorSRI)this.autorizacionProveedorSRIDao.buscarPorId(Integer.valueOf(idAutorizacionProveedorSRI));
/*  596:     */   }
/*  597:     */   
/*  598:     */   public int contarPorCriterio(Map<String, String> filters)
/*  599:     */   {
/*  600: 760 */     return this.empresaDao.contarPorCriterio(filters);
/*  601:     */   }
/*  602:     */   
/*  603:     */   public List<AutorizacionProveedorSRI> obtenerListaComboAutorizacionSRI(int idEmpresa, String consulta)
/*  604:     */   {
/*  605: 770 */     HashMap<String, String> filters = new HashMap();
/*  606:     */     
/*  607: 772 */     consulta = consulta.replace("-", "");
/*  608: 773 */     if (consulta.length() > 3)
/*  609:     */     {
/*  610: 774 */       filters.put("establecimiento", consulta.substring(0, 3));
/*  611: 775 */       filters.put("puntoEmision", consulta.substring(3));
/*  612:     */     }
/*  613:     */     else
/*  614:     */     {
/*  615: 777 */       filters.put("establecimiento", consulta);
/*  616:     */     }
/*  617: 780 */     filters.put("empresa.idEmpresa", String.valueOf(idEmpresa));
/*  618:     */     
/*  619: 782 */     filters.put("activo", "true");
/*  620:     */     
/*  621: 784 */     return this.autorizacionProveedorSRIDao.obtenerListaCombo("autorizacion", true, filters);
/*  622:     */   }
/*  623:     */   
/*  624:     */   public List<AutorizacionProveedorSRI> obtenerListaComboAutorizacionSRI(int idEmpresa, String consulta, Date fecha)
/*  625:     */   {
/*  626: 789 */     HashMap<String, String> filters = new HashMap();
/*  627:     */     
/*  628: 791 */     consulta = consulta.replace("-", "");
/*  629: 792 */     if (consulta.length() > 3)
/*  630:     */     {
/*  631: 793 */       filters.put("establecimiento", consulta.substring(0, 3));
/*  632: 794 */       filters.put("puntoEmision", consulta.substring(3));
/*  633:     */     }
/*  634:     */     else
/*  635:     */     {
/*  636: 796 */       filters.put("establecimiento", consulta);
/*  637:     */     }
/*  638: 799 */     filters.put("empresa.idEmpresa", String.valueOf(idEmpresa));
/*  639:     */     
/*  640: 801 */     filters.put("activo", "true");
/*  641:     */     
/*  642: 803 */     SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
/*  643: 804 */     filters.put("fechaDesde", "<=" + dateFormat.format(fecha));
/*  644: 805 */     filters.put("fechaHasta", ">=" + dateFormat.format(fecha));
/*  645:     */     
/*  646: 807 */     return this.autorizacionProveedorSRIDao.obtenerListaCombo("autorizacion", true, filters);
/*  647:     */   }
/*  648:     */   
/*  649:     */   @Deprecated
/*  650:     */   public Empresa buscarEmpresaPorIdentificacion(String identificacion)
/*  651:     */     throws ExcepcionAS2
/*  652:     */   {
/*  653: 818 */     return this.empresaDao.buscarEmpresaPorIdentificacion(identificacion);
/*  654:     */   }
/*  655:     */   
/*  656:     */   public Empresa buscarEmpresaPorIdentificacion(String identificacion, int idOrganizacion)
/*  657:     */     throws ExcepcionAS2
/*  658:     */   {
/*  659: 824 */     Map<String, String> filtros = new HashMap();
/*  660: 825 */     filtros.put("idOrganizacion", Integer.toString(idOrganizacion));
/*  661: 826 */     filtros.put("identificacion", identificacion);
/*  662: 827 */     return this.empresaDao.bucarEmpresaPorIdentificacion(filtros);
/*  663:     */   }
/*  664:     */   
/*  665:     */   public Empresa obtenerDatosProveedor(int idEmpresa)
/*  666:     */   {
/*  667: 837 */     return this.empresaDao.obtenerDatosProveedor(idEmpresa);
/*  668:     */   }
/*  669:     */   
/*  670:     */   @Deprecated
/*  671:     */   public Empresa bucarEmpresaPorIdentificacion(Map<String, String> filters)
/*  672:     */     throws ExcepcionAS2
/*  673:     */   {
/*  674: 848 */     if (!filters.containsKey("idOrganizacion")) {
/*  675: 849 */       filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getId()));
/*  676:     */     }
/*  677: 851 */     return this.empresaDao.bucarEmpresaPorIdentificacion(filters);
/*  678:     */   }
/*  679:     */   
/*  680:     */   public DireccionEmpresa obtieneDireccionPrincipal(int idEmpresa)
/*  681:     */     throws ExcepcionAS2
/*  682:     */   {
/*  683: 861 */     return this.clienteDao.obtieneDireccionPrincipal(idEmpresa);
/*  684:     */   }
/*  685:     */   
/*  686:     */   public List<Empresa> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  687:     */   {
/*  688: 871 */     return this.empresaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  689:     */   }
/*  690:     */   
/*  691:     */   public Empresa crearClienteConDatosBasicos(String identificacion, TipoIdentificacion tipoIdentificacion, String nombre, String direccion, String telefono, int idOrganizacion, int idSucursal, Ciudad ciudad, EntidadUsuario agenteComercial, String email)
/*  692:     */     throws ExcepcionAS2, ExcepcionAS2Identification, Exception
/*  693:     */   {
/*  694: 883 */     return crearClienteProveedorConDatosBasicos(identificacion, tipoIdentificacion, nombre, direccion, telefono, idOrganizacion, idSucursal, ciudad, agenteComercial, email, true);
/*  695:     */   }
/*  696:     */   
/*  697:     */   public Empresa crearClienteProveedorConDatosBasicos(String identificacion, TipoIdentificacion tipoIdentificacion, String nombre, String direccion, String telefono, int idOrganizacion, int idSucursal, Ciudad ciudad, EntidadUsuario agenteComercial, String email, boolean cliente)
/*  698:     */     throws ExcepcionAS2, ExcepcionAS2Identification, Exception
/*  699:     */   {
/*  700: 892 */     Empresa empresa = null;
/*  701:     */     try
/*  702:     */     {
/*  703: 894 */       empresa = buscarEmpresaPorIdentificacion(idOrganizacion, identificacion);
/*  704: 895 */       empresa = cargarDetalle(empresa);
/*  705: 896 */       empresa = cargarDetalleAtributos(empresa);
/*  706: 897 */       empresa.setIndicadorClienteProveedor(true);
/*  707: 898 */       if ((empresa.getCliente() == null) && (cliente))
/*  708:     */       {
/*  709: 899 */         crearClienteNuevo(empresa, agenteComercial);
/*  710: 900 */         empresa.setIndicadorCliente(true);
/*  711:     */       }
/*  712: 901 */       else if ((empresa.getProveedor() == null) && (!cliente))
/*  713:     */       {
/*  714: 902 */         crearProveedorNuevo(empresa);
/*  715: 903 */         empresa.setIndicadorProveedor(true);
/*  716:     */       }
/*  717:     */     }
/*  718:     */     catch (ExcepcionAS2 e)
/*  719:     */     {
/*  720: 908 */       empresa = new Empresa();
/*  721: 909 */       empresa.setCodigo(identificacion);
/*  722: 910 */       empresa.setIdentificacion(identificacion);
/*  723: 911 */       empresa.setTipoIdentificacion(tipoIdentificacion);
/*  724:     */       
/*  725: 913 */       empresa.setNombreFiscal(nombre);
/*  726: 914 */       empresa.setEmail1(email);
/*  727: 915 */       empresa.setDescripcion("CLIENTE CREADO EN PUNTO DE VENTA");
/*  728: 916 */       empresa.setActivo(true);
/*  729: 917 */       empresa.setTipoEmpresa(TipoEmpresa.NATURAL);
/*  730: 918 */       empresa.setNombreComercial(empresa.getNombreFiscal());
/*  731: 919 */       empresa.setIndicadorClienteProveedor(true);
/*  732: 920 */       empresa.setIdOrganizacion(idOrganizacion);
/*  733: 921 */       empresa.setIdSucursal(idSucursal);
/*  734:     */       
/*  735: 923 */       Map<String, String> filters = new HashMap();
/*  736: 924 */       filters.put("predeterminado", "true");
/*  737: 926 */       if (empresa.getCategoriaEmpresa() == null)
/*  738:     */       {
/*  739: 928 */         List<CategoriaEmpresa> listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", false, filters);
/*  740: 929 */         if (listaCategoriaEmpresa.isEmpty()) {
/*  741: 930 */           throw new ExcepcionAS2("msg_info_creacion_cliente_agil_categoria_empresa");
/*  742:     */         }
/*  743: 932 */         empresa.setCategoriaEmpresa((CategoriaEmpresa)listaCategoriaEmpresa.get(0));
/*  744:     */       }
/*  745: 937 */       if (cliente)
/*  746:     */       {
/*  747: 938 */         empresa.setIndicadorCliente(true);
/*  748: 939 */         crearClienteNuevo(empresa, agenteComercial);
/*  749:     */       }
/*  750:     */       else
/*  751:     */       {
/*  752: 941 */         empresa.setIndicadorProveedor(true);
/*  753: 942 */         crearProveedorNuevo(empresa);
/*  754:     */       }
/*  755: 945 */       Ubicacion ubicacion = new Ubicacion();
/*  756: 946 */       ubicacion.setIdOrganizacion(empresa.getIdOrganizacion());
/*  757: 947 */       ubicacion.setIdSucursal(empresa.getIdSucursal());
/*  758: 948 */       ubicacion.setDireccion1(direccion.isEmpty() ? "S/N" : direccion);
/*  759: 949 */       ubicacion.setDireccion2("");
/*  760: 950 */       ubicacion.setDireccion3("");
/*  761: 951 */       ubicacion.setDireccion3("");
/*  762: 952 */       ubicacion.setDireccion5("");
/*  763:     */       
/*  764:     */ 
/*  765:     */ 
/*  766: 956 */       DireccionEmpresa direccionEmpresa = new DireccionEmpresa();
/*  767: 957 */       direccionEmpresa.setActivo(true);
/*  768:     */       
/*  769: 959 */       direccionEmpresa.setIdOrganizacion(empresa.getIdOrganizacion());
/*  770: 960 */       direccionEmpresa.setIdSucursal(empresa.getIdSucursal());
/*  771: 961 */       direccionEmpresa.setEmpresa(empresa);
/*  772: 962 */       direccionEmpresa.setIndicadorDireccionPrincipal(true);
/*  773: 963 */       direccionEmpresa.setIndicadorDireccionEnvio(false);
/*  774: 964 */       direccionEmpresa.setIndicadorDireccionFactura(false);
/*  775: 965 */       direccionEmpresa.setUbicacion(ubicacion);
/*  776: 966 */       direccionEmpresa.setTelefono1(telefono);
/*  777: 967 */       direccionEmpresa.setTelefono2("");
/*  778: 969 */       if (ciudad == null)
/*  779:     */       {
/*  780: 970 */         if (direccionEmpresa.getCiudad() == null)
/*  781:     */         {
/*  782: 971 */           List<Ciudad> listaCiudad = this.servicioCiudad.obtenerListaCombo("nombre", false, filters);
/*  783: 972 */           if (listaCiudad.isEmpty()) {
/*  784: 973 */             throw new ExcepcionAS2("msg_info_creacion_cliente_agil_ciudad");
/*  785:     */           }
/*  786: 975 */           direccionEmpresa.setCiudad((Ciudad)listaCiudad.get(0));
/*  787:     */         }
/*  788:     */       }
/*  789:     */       else {
/*  790: 979 */         direccionEmpresa.setCiudad(ciudad);
/*  791:     */       }
/*  792: 982 */       empresa.getDirecciones().add(direccionEmpresa);
/*  793:     */     }
/*  794: 985 */     guardar(empresa);
/*  795: 986 */     return empresa;
/*  796:     */   }
/*  797:     */   
/*  798:     */   public Empresa crearEmpresaConDatosBasicos(int idEmpresa, String codigo, String identificacion, TipoIdentificacion tipoIdentificacion, String nombre, String direccion, String telefono, int idOrganizacion, int idSucursal, Ciudad ciudad, EntidadUsuario agenteComercial, String email, boolean indicadorCliente, boolean indicadorProveedor, TipoEmpresa tipoEmpresa, List<EmpresaAtributo> empresaAtributo)
/*  799:     */     throws ExcepcionAS2, ExcepcionAS2Identification, Exception
/*  800:     */   {
/*  801: 995 */     Empresa empresa = null;
/*  802: 996 */     if (idEmpresa != 0)
/*  803:     */     {
/*  804: 997 */       empresa = buscarPorId(Integer.valueOf(idEmpresa));
/*  805: 998 */       empresa = cargarDetalle(empresa);
/*  806:     */     }
/*  807:     */     else
/*  808:     */     {
/*  809:1006 */       empresa = new Empresa();
/*  810:     */     }
/*  811:1008 */     empresa.setCodigo(codigo);
/*  812:1009 */     empresa.setIdentificacion(identificacion);
/*  813:1010 */     empresa.setTipoIdentificacion(tipoIdentificacion);
/*  814:     */     
/*  815:1012 */     empresa.setNombreFiscal(nombre);
/*  816:1013 */     empresa.setEmail1(email);
/*  817:1014 */     empresa.setActivo(true);
/*  818:1015 */     empresa.setTipoEmpresa(tipoEmpresa);
/*  819:1016 */     empresa.setNombreComercial(empresa.getNombreFiscal());
/*  820:1017 */     empresa.setIdOrganizacion(idOrganizacion);
/*  821:1018 */     empresa.setIdSucursal(idSucursal);
/*  822:1019 */     empresa.setListaEmpresaAtributo(empresaAtributo);
/*  823:     */     
/*  824:1021 */     Map<String, String> filters = new HashMap();
/*  825:1022 */     filters.put("predeterminado", "true");
/*  826:1023 */     filters.put("idOrganizacion", "" + empresa.getIdOrganizacion());
/*  827:1025 */     if (empresa.getCategoriaEmpresa() == null)
/*  828:     */     {
/*  829:1027 */       List<CategoriaEmpresa> listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", false, filters);
/*  830:1028 */       if (listaCategoriaEmpresa.isEmpty()) {
/*  831:1029 */         throw new ExcepcionAS2("msg_info_creacion_cliente_agil_categoria_empresa");
/*  832:     */       }
/*  833:1031 */       empresa.setCategoriaEmpresa((CategoriaEmpresa)listaCategoriaEmpresa.get(0));
/*  834:     */     }
/*  835:1036 */     if (indicadorCliente)
/*  836:     */     {
/*  837:1037 */       if (empresa.getCliente() == null)
/*  838:     */       {
/*  839:1038 */         empresa.setCliente(new Cliente(empresa));
/*  840:1039 */         empresa.getCliente().setNumeroCuotas(1);
/*  841:1040 */         empresa.getCliente().setMetodoFacturacion(MetodoFacturacionEnum.PEDIDO_FACTURA_DESPACHO);
/*  842:1041 */         empresa.getCliente().setTipoCliente(TipoEmpresaEnum.AMBOS);
/*  843:1042 */         empresa.getCliente().setIdOrganizacion(empresa.getIdOrganizacion());
/*  844:1043 */         empresa.getCliente().setIdSucursal(empresa.getIdSucursal());
/*  845:1044 */         empresa.setCliente(empresa.getCliente());
/*  846:     */       }
/*  847:1046 */       empresa.setIndicadorCliente(true);
/*  848:1047 */       empresa.getCliente().setAgenteComercial(agenteComercial);
/*  849:1049 */       if (empresa.getCliente().getFormaPago() == null)
/*  850:     */       {
/*  851:1050 */         List<FormaPago> listaFormaPago = this.servicioFormaPago.obtenerListaCombo("nombre", false, filters);
/*  852:1051 */         if (listaFormaPago.isEmpty()) {
/*  853:1052 */           throw new ExcepcionAS2("msg_info_creacion_cliente_agil_forma_pago");
/*  854:     */         }
/*  855:1054 */         empresa.getCliente().setFormaPago((FormaPago)listaFormaPago.get(0));
/*  856:     */       }
/*  857:1058 */       if (empresa.getCliente().getCondicionPago() == null)
/*  858:     */       {
/*  859:1059 */         List<CondicionPago> listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("nombre", false, filters);
/*  860:1060 */         if (listaCondicionPago.isEmpty()) {
/*  861:1061 */           throw new ExcepcionAS2("msg_info_creacion_cliente_agil_condicion_pago");
/*  862:     */         }
/*  863:1063 */         empresa.getCliente().setCondicionPago((CondicionPago)listaCondicionPago.get(0));
/*  864:     */       }
/*  865:1067 */       if (empresa.getCliente().getListaDescuentos() == null)
/*  866:     */       {
/*  867:1068 */         List<ListaDescuentos> listaDescuentos = this.servicioListaDescuentos.obtenerListaCombo("nombre", false, filters);
/*  868:1069 */         if (listaDescuentos.isEmpty()) {
/*  869:1070 */           throw new ExcepcionAS2("msg_info_creacion_cliente_agil_lista_descuentos");
/*  870:     */         }
/*  871:1072 */         empresa.getCliente().setListaDescuentos((ListaDescuentos)listaDescuentos.get(0));
/*  872:     */       }
/*  873:1076 */       filters.put("indicadorVenta", "true");
/*  874:1077 */       if (empresa.getCliente().getListaPrecios() == null)
/*  875:     */       {
/*  876:1078 */         List<ListaPrecios> listaListaPrecios = this.servicioListaPrecios.obtenerListaCombo("nombre", false, filters);
/*  877:1079 */         if (listaListaPrecios.isEmpty()) {
/*  878:1080 */           throw new ExcepcionAS2("msg_info_creacion_cliente_agil_lista_precios");
/*  879:     */         }
/*  880:1082 */         empresa.getCliente().setListaPrecios((ListaPrecios)listaListaPrecios.get(0));
/*  881:     */       }
/*  882:1085 */       filters.remove("indicadorVenta");
/*  883:     */     }
/*  884:1089 */     if (indicadorProveedor)
/*  885:     */     {
/*  886:1090 */       if (empresa.getProveedor() == null)
/*  887:     */       {
/*  888:1091 */         empresa.setProveedor(new Proveedor(empresa));
/*  889:1092 */         empresa.getProveedor().setIdOrganizacion(empresa.getIdOrganizacion());
/*  890:1093 */         empresa.getProveedor().setIdSucursal(empresa.getIdSucursal());
/*  891:1094 */         empresa.getProveedor().setNumeroCuotas(1);
/*  892:1095 */         empresa.setProveedor(empresa.getProveedor());
/*  893:     */       }
/*  894:1098 */       empresa.setIndicadorProveedor(true);
/*  895:1100 */       if (empresa.getProveedor().getFormaPago() == null)
/*  896:     */       {
/*  897:1101 */         List<FormaPago> listaFormaPago = this.servicioFormaPago.obtenerListaCombo("nombre", false, filters);
/*  898:1102 */         if (listaFormaPago.isEmpty()) {
/*  899:1103 */           throw new ExcepcionAS2("msg_info_creacion_cliente_agil_forma_pago");
/*  900:     */         }
/*  901:1105 */         empresa.getProveedor().setFormaPago((FormaPago)listaFormaPago.get(0));
/*  902:     */       }
/*  903:1109 */       if ((empresa.getListaFormaPagoSRI() == null) || (empresa.getListaFormaPagoSRI().size() == 0))
/*  904:     */       {
/*  905:1110 */         Map<String, String> filters2 = new HashMap();
/*  906:1111 */         filters2.put("idOrganizacion", "" + empresa.getIdOrganizacion());
/*  907:1112 */         List<FormaPagoSRI> listaFormaPagoSRI = this.formaPagoSRIDao.buscarFormaPagoSRI(filters2);
/*  908:1113 */         if (listaFormaPagoSRI.isEmpty()) {
/*  909:1114 */           throw new ExcepcionAS2("msg_info_creacion_cliente_agil_forma_pago");
/*  910:     */         }
/*  911:1116 */         empresa.setListaFormaPagoSRI(listaFormaPagoSRI);
/*  912:     */       }
/*  913:1120 */       if (empresa.getProveedor().getCondicionPago() == null)
/*  914:     */       {
/*  915:1121 */         List<CondicionPago> listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("nombre", false, filters);
/*  916:1122 */         if (listaCondicionPago.isEmpty()) {
/*  917:1123 */           throw new ExcepcionAS2("msg_info_creacion_cliente_agil_condicion_pago");
/*  918:     */         }
/*  919:1125 */         empresa.getProveedor().setCondicionPago((CondicionPago)listaCondicionPago.get(0));
/*  920:     */       }
/*  921:     */     }
/*  922:1130 */     DireccionEmpresa direccionEmpresa = null;
/*  923:     */     Ubicacion ubicacion;
/*  924:1132 */     if ((empresa.getDirecciones() == null) || (empresa.getDirecciones().size() == 0))
/*  925:     */     {
/*  926:1133 */       ubicacion = new Ubicacion();
/*  927:1134 */       ubicacion.setIdOrganizacion(empresa.getIdOrganizacion());
/*  928:1135 */       ubicacion.setIdSucursal(empresa.getIdSucursal());
/*  929:     */       
/*  930:1137 */       direccionEmpresa = new DireccionEmpresa();
/*  931:1138 */       direccionEmpresa.setActivo(true);
/*  932:     */       
/*  933:1140 */       direccionEmpresa.setIdOrganizacion(empresa.getIdOrganizacion());
/*  934:1141 */       direccionEmpresa.setIdSucursal(empresa.getIdSucursal());
/*  935:1142 */       direccionEmpresa.setEmpresa(empresa);
/*  936:1143 */       direccionEmpresa.setIndicadorDireccionPrincipal(true);
/*  937:1144 */       direccionEmpresa.setIndicadorDireccionEnvio(false);
/*  938:1145 */       direccionEmpresa.setIndicadorDireccionFactura(false);
/*  939:1146 */       direccionEmpresa.setUbicacion(ubicacion);
/*  940:     */       
/*  941:1148 */       empresa.setDirecciones(new ArrayList());
/*  942:1149 */       empresa.getDirecciones().add(direccionEmpresa);
/*  943:     */     }
/*  944:1152 */     direccionEmpresa = (DireccionEmpresa)empresa.getDirecciones().get(0);
/*  945:1153 */     for (DireccionEmpresa direccionE : empresa.getDirecciones()) {
/*  946:1154 */       if (direccionE.isIndicadorDireccionPrincipal()) {
/*  947:1155 */         direccionEmpresa = direccionE;
/*  948:     */       }
/*  949:     */     }
/*  950:1159 */     direccionEmpresa.getUbicacion().setDireccion1(direccion.isEmpty() ? "S/N" : direccion);
/*  951:1160 */     direccionEmpresa.getUbicacion().setDireccion2("");
/*  952:1161 */     direccionEmpresa.getUbicacion().setDireccion3("");
/*  953:1162 */     direccionEmpresa.getUbicacion().setDireccion3("");
/*  954:1163 */     direccionEmpresa.getUbicacion().setDireccion5("");
/*  955:1164 */     direccionEmpresa.setCiudad(ciudad);
/*  956:     */     
/*  957:     */ 
/*  958:1167 */     direccionEmpresa.setTelefono1(telefono);
/*  959:1168 */     direccionEmpresa.setTelefono2(direccionEmpresa.getTelefono2() == null ? "" : direccionEmpresa.getTelefono2());
/*  960:1169 */     empresa.setListaEmpresaAtributo(new ArrayList());
/*  961:     */     
/*  962:1171 */     guardar(empresa);
/*  963:     */     
/*  964:1173 */     return empresa;
/*  965:     */   }
/*  966:     */   
/*  967:     */   public List<Empresa> obtenerEmpresas(int idOrganizacion)
/*  968:     */   {
/*  969:1177 */     return this.empresaDao.obtenerEmpresas(idOrganizacion);
/*  970:     */   }
/*  971:     */   
/*  972:     */   public Empresa obtenerDatosCliente(int idEmpresa)
/*  973:     */   {
/*  974:1187 */     return this.empresaDao.obtenerDatosCliente(idEmpresa);
/*  975:     */   }
/*  976:     */   
/*  977:     */   public List<RecargoListaPreciosCliente> obtenerRecargos(Empresa empresa)
/*  978:     */   {
/*  979:1192 */     return this.empresaDao.obtenerRecargos(empresa);
/*  980:     */   }
/*  981:     */   
/*  982:     */   public Empresa crearClienteConDatosBasicos(String identificacion, String codigoTipoIdentificacion, String nombre, String direccion, String telefono, int idOrganizacion, int idSucursal, String email)
/*  983:     */     throws ExcepcionAS2, ExcepcionAS2Identification, Exception
/*  984:     */   {
/*  985:1204 */     Map<String, String> filters = new HashMap();
/*  986:1205 */     filters.put("codigo", codigoTipoIdentificacion);
/*  987:1206 */     filters.put("idOrganizacion", String.valueOf(idOrganizacion));
/*  988:     */     
/*  989:1208 */     TipoIdentificacion tipoIdentificacion = (TipoIdentificacion)this.servicioTipoIdentificacion.obtenerListaCombo(null, false, filters).get(0);
/*  990:     */     
/*  991:     */ 
/*  992:     */ 
/*  993:1212 */     return crearClienteConDatosBasicos(identificacion, tipoIdentificacion, nombre, direccion, telefono, idOrganizacion, idSucursal, null, null, email);
/*  994:     */   }
/*  995:     */   
/*  996:     */   public AutorizacionProveedorSRI buscarAutorizacion(int idEmpresa, String factura, Date fecha, int idOrganizacion)
/*  997:     */     throws ExcepcionAS2Compras
/*  998:     */   {
/*  999:1223 */     return this.autorizacionProveedorSRIDao.buscarAutorizacion(idEmpresa, factura, fecha, idOrganizacion);
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   public List<Subempresa> obtenerListaComboSubEmpresa(int idEmpresa, boolean activo)
/* 1003:     */   {
/* 1004:1228 */     return this.subempresaDao.obtenerListaComboSubEmpresa(idEmpresa, activo);
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public List<Subempresa> obtenerListaComboSubEmpresa(int idEmpresa)
/* 1008:     */   {
/* 1009:1233 */     return obtenerListaComboSubEmpresa(idEmpresa, true);
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   public List<FormaPagoSRI> getListaFormaPagoSRIEmpresa(String identificacion, int idOrganizacion)
/* 1013:     */   {
/* 1014:1238 */     return this.empresaDao.getListaFormaPagoSRIEmpresa(identificacion, idOrganizacion);
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   private void validar(Empresa empresa)
/* 1018:     */     throws ExcepcionAS2Identification, ExcepcionAS2, AS2Exception
/* 1019:     */   {
/* 1020:1243 */     if (verificarCodigoEmpresa(empresa)) {
/* 1021:1244 */       throw new ExcepcionAS2("msg_info_existe_codigo_empresa", ": " + empresa.getCodigo());
/* 1022:     */     }
/* 1023:1247 */     if ((empresa.isIndicadorEmpleado()) && (empresa.getEmpleado() != null) && (empresa.getEmpleado().getResidenciaTrabajador() == null)) {
/* 1024:1249 */       throw new AS2Exception("com.asinfo.as2.datosbase.servicio.impl.ServicioEmpresaImpl.ERROR_INGRESAR_DATOS_SRI", new String[] {empresa.getEmpleado().getApellidos() + " " + empresa.getEmpleado().getNombres() });
/* 1025:     */     }
/* 1026:1253 */     TipoIdentificacion tipoIdentificacion = this.servicioTipoIdentificacion.buscarPorId(Integer.valueOf(empresa.getTipoIdentificacion().getId()));
/* 1027:1254 */     ValidarIdentificacion.validarIdentificacion(tipoIdentificacion.isIndicadorValidarIdentificacion(), empresa.getIdentificacion());
/* 1028:1257 */     if (empresa.isIndicadorProveedor())
/* 1029:     */     {
/* 1030:1258 */       boolean error = true;
/* 1031:1259 */       for (Iterator localIterator = empresa.getListaFormaPagoSRI().iterator(); localIterator.hasNext();)
/* 1032:     */       {
/* 1033:1259 */         formaPagoSRI = (FormaPagoSRI)localIterator.next();
/* 1034:1260 */         if (!formaPagoSRI.isEliminado()) {
/* 1035:1261 */           error = false;
/* 1036:     */         }
/* 1037:     */       }
/* 1038:1264 */       if (error) {
/* 1039:1265 */         throw new ExcepcionAS2("msg_info_creacion_cliente_agil_forma_pago");
/* 1040:     */       }
/* 1041:     */     }
/* 1042:1271 */     int tamanioLista = empresa.getDirecciones().size();
/* 1043:1272 */     int tamanioEliminados = 0;
/* 1044:1273 */     for (DireccionEmpresa direccionEmpresa : empresa.getDirecciones()) {
/* 1045:1274 */       if (direccionEmpresa.isEliminado()) {
/* 1046:1275 */         tamanioEliminados++;
/* 1047:     */       }
/* 1048:     */     }
/* 1049:1279 */     if ((empresa.getDirecciones().isEmpty()) || (empresa.getDirecciones().size() == 0) || (tamanioLista - tamanioEliminados == 0)) {
/* 1050:1280 */       throw new AS2Exception("com.asinfo.as2.datosbase.servicio.impl.ServicioEmpresaImpl", new String[] { empresa.getNombreFiscal() });
/* 1051:     */     }
/* 1052:1283 */     for (FormaPagoSRI formaPagoSRI = empresa.getDirecciones().iterator(); formaPagoSRI.hasNext();)
/* 1053:     */     {
/* 1054:1283 */       direccionEmpresa = (DireccionEmpresa)formaPagoSRI.next();
/* 1055:1284 */       if ((!direccionEmpresa.isEliminado()) && (direccionEmpresa.getCiudad() == null)) {
/* 1056:1285 */         throw new ExcepcionAS2("msg_error_ciudad_direccion");
/* 1057:     */       }
/* 1058:     */     }
/* 1059:     */     DireccionEmpresa direccionEmpresa;
/* 1060:1290 */     if ((empresa.getProveedor() != null) && (empresa.getProveedor().isIndicadorPagoCash()))
/* 1061:     */     {
/* 1062:1291 */       if (empresa.getListaCuentaBancariaEmpresa().isEmpty()) {
/* 1063:1292 */         throw new ExcepcionAS2("msg_error_cuenta_bancaria_direccion_pago_cash");
/* 1064:     */       }
/* 1065:1294 */       boolean error = true;
/* 1066:1295 */       for (CuentaBancariaEmpresa cbe : empresa.getListaCuentaBancariaEmpresa()) {
/* 1067:1296 */         if (!cbe.isEliminado())
/* 1068:     */         {
/* 1069:1297 */           error = false;
/* 1070:1298 */           break;
/* 1071:     */         }
/* 1072:     */       }
/* 1073:1301 */       if (error) {
/* 1074:1302 */         throw new ExcepcionAS2("msg_error_cuenta_bancaria_direccion_pago_cash");
/* 1075:     */       }
/* 1076:     */     }
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public Empresa buscarEmpresaPorIdentificacion(int IdOrganizacion, String identificacion)
/* 1080:     */     throws ExcepcionAS2
/* 1081:     */   {
/* 1082:1311 */     return this.empresaDao.buscarEmpresaPorIdentificacion(IdOrganizacion, identificacion);
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public Boolean empresaExiste(int idOrganizacion, String codigo)
/* 1086:     */     throws ExcepcionAS2
/* 1087:     */   {
/* 1088:1319 */     return Boolean.valueOf(this.empresaDao.empresaExiste(idOrganizacion, codigo) != null);
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   private void setearYValidarValoresSRI(Empleado empleado)
/* 1092:     */     throws ExcepcionAS2Identification, ExcepcionAS2
/* 1093:     */   {
/* 1094:1332 */     if (empleado.getResidenciaTrabajador().equals("01"))
/* 1095:     */     {
/* 1096:1333 */       empleado.setPaisResidenciaTrabajador("593");
/* 1097:1334 */       empleado.setConvenioDobleImposicion("NA");
/* 1098:     */     }
/* 1099:1337 */     if (empleado.getCondicionRespectoDiscapacidad().equals("01"))
/* 1100:     */     {
/* 1101:1338 */       empleado.setTipoDiscapacidad(null);
/* 1102:1339 */       empleado.setPorcentajeDiscapacidad(BigDecimal.ZERO);
/* 1103:1340 */       empleado.setTipoIdentificacionSustitutoPariente("N");
/* 1104:1341 */       empleado.setIdentificacionSustitutoPariente("999");
/* 1105:     */     }
/* 1106:1344 */     if (((empleado.getCondicionRespectoDiscapacidad().equals("02")) || 
/* 1107:1345 */       (empleado.getCondicionRespectoDiscapacidad().equals("03")) || (empleado.getCondicionRespectoDiscapacidad().equals("04"))) && 
/* 1108:1346 */       (empleado.getPorcentajeDiscapacidad().compareTo(BigDecimal.ZERO) == 0)) {
/* 1109:1347 */       throw new ExcepcionAS2("msg_error_porcentaje_discapacidad");
/* 1110:     */     }
/* 1111:1351 */     if (((empleado.getCondicionRespectoDiscapacidad().equals("03")) || (empleado.getCondicionRespectoDiscapacidad().equals("04"))) && 
/* 1112:1352 */       (empleado.getTipoIdentificacionSustitutoPariente().equals("C"))) {
/* 1113:1353 */       ValidarIdentificacion.validarIdentificacion(true, empleado.getIdentificacionSustitutoPariente());
/* 1114:     */     }
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   public List getFichaEmpleado(int idEmpleado)
/* 1118:     */   {
/* 1119:1362 */     return this.empresaDao.getFichaEmpleado(idEmpleado);
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public List<FormacionAcademica> getFichaEmpleadoFormacionAcademica(int idEmpleado)
/* 1123:     */   {
/* 1124:1367 */     return this.empresaDao.getFichaEmpleadoFormacionAcademica(idEmpleado);
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   public void actualizarActivoSalidaEmpleado(Empresa empresa, int idOrganizacion, boolean estado)
/* 1128:     */   {
/* 1129:1372 */     this.empresaDao.actualizarActivoSalidaEmpleado(empresa, idOrganizacion, estado);
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   public void actualizarDatos(Empresa empresa)
/* 1133:     */   {
/* 1134:1377 */     this.empresaDao.guardar(empresa);
/* 1135:     */   }
/* 1136:     */   
/* 1137:     */   public List<Object[]> listaEmpledos(Date fechaDesde, Date fechaHasta, Departamento departamento, Sucursal sucursal, Genero genero, int idOrganizacion, String estado, String tipoReporte, int mes)
/* 1138:     */   {
/* 1139:1383 */     List<Object[]> lresult = this.empresaDao.listaEmpledos(fechaDesde, fechaHasta, departamento, sucursal, genero, idOrganizacion, estado, tipoReporte, mes);
/* 1140:1385 */     for (Object[] line : lresult)
/* 1141:     */     {
/* 1142:1387 */       Date fechaIngreso = (Date)line[5];
/* 1143:1388 */       Date fechaSalida = (Date)line[6];
/* 1144:1389 */       if (null == fechaSalida) {
/* 1145:1390 */         fechaSalida = new Date();
/* 1146:     */       }
/* 1147:1392 */       line[34] = getTiempoTrabajado(fechaIngreso, fechaSalida);
/* 1148:     */     }
/* 1149:1395 */     return lresult;
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   private String getTiempoTrabajado(Date fechaIngreso, Date fechaSalida)
/* 1153:     */   {
/* 1154:1399 */     int totaldias = FuncionesUtiles.diferenciasDeFechas(fechaIngreso, fechaSalida);
/* 1155:1400 */     int anios = totaldias / 365;
/* 1156:1401 */     int meses = totaldias % 365 / 30;
/* 1157:1402 */     int dias = totaldias % 365 % 30;
/* 1158:1403 */     StringBuilder sb = new StringBuilder();
/* 1159:1404 */     sb.append(anios).append("a");
/* 1160:1405 */     sb.append(meses).append("m");
/* 1161:1406 */     sb.append(dias).append("d");
/* 1162:     */     
/* 1163:1408 */     return sb.toString();
/* 1164:     */   }
/* 1165:     */   
/* 1166:     */   public List<Object[]> reporteEmpresas(int idOrganizacion, boolean indicadorCliente, boolean indicadorProveedor, int idcategoria, TipoEmpresa tipoEmpresa, Empresa empresa)
/* 1167:     */   {
/* 1168:1414 */     return this.empresaDao.reporteEmpresas(idOrganizacion, indicadorCliente, indicadorProveedor, idcategoria, tipoEmpresa, empresa);
/* 1169:     */   }
/* 1170:     */   
/* 1171:     */   public String cargarMails(Empresa empresa, DocumentoBase documentoBase)
/* 1172:     */   {
/* 1173:1419 */     return cargarMails(empresa, documentoBase, null);
/* 1174:     */   }
/* 1175:     */   
/* 1176:     */   public String cargarMails(Empresa empresa, DocumentoBase documentoBase, ReporteEnvioMailsEnum indicadorEnvioMailsEnum)
/* 1177:     */   {
/* 1178:1424 */     String email = "";
/* 1179:1425 */     boolean admiteEmailPrincipal = true;
/* 1180:1426 */     List<Contacto> contactos = this.contactoDao.leerPorEmpresa(empresa, documentoBase, indicadorEnvioMailsEnum);
/* 1181:1427 */     for (Contacto contacto : contactos)
/* 1182:     */     {
/* 1183:1428 */       if ((contacto.getEmail1() != null) && (!contacto.getEmail1().trim().equals("")) && (!contacto.getEmail1().trim().equals("N/A")))
/* 1184:     */       {
/* 1185:1429 */         if (!email.equals("")) {
/* 1186:1430 */           email = email + ";";
/* 1187:     */         }
/* 1188:1432 */         email = email + contacto.getEmail1().trim();
/* 1189:     */       }
/* 1190:1434 */       if ((contacto.getEmail2() != null) && (!contacto.getEmail2().trim().equals("")) && (!contacto.getEmail2().trim().equals("N/A")))
/* 1191:     */       {
/* 1192:1435 */         if (!email.equals("")) {
/* 1193:1436 */           email = email + ";";
/* 1194:     */         }
/* 1195:1438 */         email = email + contacto.getEmail2().trim();
/* 1196:     */       }
/* 1197:     */     }
/* 1198:1441 */     if ((documentoBase != null) && ((documentoBase.equals(DocumentoBase.PEDIDO_PROVEEDOR)) || (documentoBase.equals(DocumentoBase.PEDIDO_CLIENTE)) || 
/* 1199:1442 */       (documentoBase.equals(DocumentoBase.PAGO_PROVEEDOR))))
/* 1200:     */     {
/* 1201:1443 */       admiteEmailPrincipal = false;
/* 1202:1444 */       if (DocumentoBase.PEDIDO_PROVEEDOR.equals(documentoBase))
/* 1203:     */       {
/* 1204:1445 */         String emailRespuesta = "^";
/* 1205:1446 */         String telefonoRespuesta = "^";
/* 1206:1447 */         for (Contacto contacto : contactos)
/* 1207:     */         {
/* 1208:1448 */           if ((contacto.getEmailRespuesta() != null) && (!contacto.getEmailRespuesta().trim().equals("")) && 
/* 1209:1449 */             (!contacto.getEmailRespuesta().trim().equals("N/A")))
/* 1210:     */           {
/* 1211:1450 */             if (!emailRespuesta.replace("^", "").equals("")) {
/* 1212:1451 */               emailRespuesta = emailRespuesta + ";";
/* 1213:     */             }
/* 1214:1453 */             emailRespuesta = emailRespuesta + contacto.getEmailRespuesta().trim();
/* 1215:     */           }
/* 1216:1455 */           if ((contacto.getTelefonoRespuesta() != null) && (!contacto.getTelefonoRespuesta().trim().equals("")) && 
/* 1217:1456 */             (!contacto.getTelefonoRespuesta().trim().equals("N/A")))
/* 1218:     */           {
/* 1219:1457 */             if (!telefonoRespuesta.replace("^", "").equals("")) {
/* 1220:1458 */               telefonoRespuesta = telefonoRespuesta + "/";
/* 1221:     */             }
/* 1222:1460 */             telefonoRespuesta = telefonoRespuesta + contacto.getTelefonoRespuesta().trim();
/* 1223:     */           }
/* 1224:     */         }
/* 1225:1463 */         email = email + "~" + emailRespuesta + "~" + telefonoRespuesta;
/* 1226:     */       }
/* 1227:     */     }
/* 1228:1466 */     if ((email.trim().equals("")) && (admiteEmailPrincipal))
/* 1229:     */     {
/* 1230:1467 */       if ((empresa.getEmail1() != null) && (!empresa.getEmail1().trim().trim().equals("")) && (!empresa.getEmail1().trim().trim().equals("N/A"))) {
/* 1231:1468 */         email = email + empresa.getEmail1().trim();
/* 1232:     */       }
/* 1233:1470 */       if ((empresa.getEmail2() != null) && (!empresa.getEmail2().trim().trim().equals("")) && (!empresa.getEmail2().trim().trim().equals("N/A")))
/* 1234:     */       {
/* 1235:1471 */         if (!email.equals("")) {
/* 1236:1472 */           email = email + ";";
/* 1237:     */         }
/* 1238:1474 */         email = email + empresa.getEmail2().trim();
/* 1239:     */       }
/* 1240:     */     }
/* 1241:1477 */     return email.trim();
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public void actualizarMails(Empresa empresa, String correos, DocumentoBase tipoDocumento)
/* 1245:     */   {
/* 1246:1482 */     empresa = cargarDetalle(empresa, false);
/* 1247:1483 */     List<Contacto> contactos = this.contactoDao.leerPorEmpresa(empresa, tipoDocumento);
/* 1248:     */     
/* 1249:1485 */     boolean manejaContactos = false;
/* 1250:1486 */     boolean guardarMails = false;
/* 1251:1488 */     if (contactos.size() > 0) {
/* 1252:1489 */       manejaContactos = true;
/* 1253:     */     }
/* 1254:1492 */     if ((!manejaContactos) && 
/* 1255:1493 */       ((empresa.getEmail1() == null) || (empresa.getEmail1().isEmpty())) && ((empresa.getEmail2() == null) || (empresa.getEmail2().isEmpty()))) {
/* 1256:1494 */       guardarMails = true;
/* 1257:     */     }
/* 1258:1498 */     if (guardarMails)
/* 1259:     */     {
/* 1260:1499 */       empresa.setEmail2(correos);
/* 1261:1500 */       actualizarDatos(empresa);
/* 1262:     */     }
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   public void configurarDireccionComoPrincipal(Empresa empresa)
/* 1266:     */   {
/* 1267:1507 */     int i = 0;
/* 1268:1508 */     if (empresa.getDirecciones().size() != 0)
/* 1269:     */     {
/* 1270:1509 */       for (DireccionEmpresa direccionEmpresa : empresa.getDirecciones()) {
/* 1271:1510 */         if ((direccionEmpresa.isIndicadorDireccionPrincipal()) && (!direccionEmpresa.isEliminado()))
/* 1272:     */         {
/* 1273:1511 */           i++;
/* 1274:1512 */           break;
/* 1275:     */         }
/* 1276:     */       }
/* 1277:1515 */       if (i == 0) {
/* 1278:1517 */         for (DireccionEmpresa direccionEmpresa : empresa.getDirecciones()) {
/* 1279:1518 */           if (!direccionEmpresa.isEliminado())
/* 1280:     */           {
/* 1281:1519 */             direccionEmpresa.setIndicadorDireccionPrincipal(true);
/* 1282:1520 */             break;
/* 1283:     */           }
/* 1284:     */         }
/* 1285:     */       }
/* 1286:     */     }
/* 1287:     */   }
/* 1288:     */   
/* 1289:     */   public List<Subempresa> autocompletarSubEmpresa(String consulta, Empresa empresa)
/* 1290:     */   {
/* 1291:1531 */     return this.subempresaDao.autocompletarSubEmpresa(consulta, empresa);
/* 1292:     */   }
/* 1293:     */   
/* 1294:     */   public Subempresa buscarPorIdSubempresa(Integer idSubempresa)
/* 1295:     */   {
/* 1296:1536 */     return (Subempresa)this.subempresaDao.buscarPorId(idSubempresa);
/* 1297:     */   }
/* 1298:     */   
/* 1299:     */   public void guardarActualizacionCliente(Empresa empresa)
/* 1300:     */     throws ExcepcionAS2, ExcepcionAS2Identification, Exception
/* 1301:     */   {
/* 1302:1540 */     this.clienteDao.guardar(empresa.getCliente());
/* 1303:     */   }
/* 1304:     */   
/* 1305:     */   public void flush()
/* 1306:     */   {
/* 1307:1545 */     this.empresaDao.flush();
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   public List<CuentaBancariaEmpresa> obtenerListaCuentaBancariaEmpresa(int idOrganizacion, int idEmpresa)
/* 1311:     */   {
/* 1312:1551 */     return this.empresaDao.obtenerListaCuentaBancariaEmpresa(idOrganizacion, idEmpresa);
/* 1313:     */   }
/* 1314:     */   
/* 1315:     */   public boolean verificarCodigoEmpresa(Empresa empresa)
/* 1316:     */   {
/* 1317:1556 */     return this.empresaDao.verificarCodigoEmpresa(empresa);
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   public void crearClienteNuevo(Empresa empresa, EntidadUsuario agenteComercial)
/* 1321:     */     throws ExcepcionAS2, ExcepcionAS2Identification, Exception
/* 1322:     */   {
/* 1323:1561 */     empresa.setCliente(new Cliente(empresa));
/* 1324:     */     
/* 1325:1563 */     empresa.getCliente().setIdOrganizacion(empresa.getIdOrganizacion());
/* 1326:1564 */     empresa.getCliente().setIdSucursal(empresa.getIdSucursal());
/* 1327:1565 */     empresa.getCliente().setNumeroCuotas(1);
/* 1328:1566 */     empresa.getCliente().setMetodoFacturacion(MetodoFacturacionEnum.PEDIDO_FACTURA_DESPACHO);
/* 1329:1567 */     empresa.getCliente().setTipoCliente(TipoEmpresaEnum.AMBOS);
/* 1330:1568 */     empresa.getCliente().setAgenteComercial(agenteComercial);
/* 1331:1569 */     empresa.getCliente().setIndicadorExterior(Boolean.valueOf(false));
/* 1332:1570 */     empresa.setCliente(empresa.getCliente());
/* 1333:     */     
/* 1334:1572 */     Map<String, String> filters = new HashMap();
/* 1335:1573 */     filters.put("predeterminado", "true");
/* 1336:1575 */     if (empresa.getCliente().getFormaPago() == null)
/* 1337:     */     {
/* 1338:1576 */       List<FormaPago> listaFormaPago = this.servicioFormaPago.obtenerListaCombo("nombre", false, filters);
/* 1339:1577 */       if (listaFormaPago.isEmpty()) {
/* 1340:1578 */         throw new ExcepcionAS2("msg_info_creacion_cliente_agil_forma_pago");
/* 1341:     */       }
/* 1342:1580 */       empresa.getCliente().setFormaPago((FormaPago)listaFormaPago.get(0));
/* 1343:     */     }
/* 1344:1584 */     if (empresa.getCliente().getCondicionPago() == null)
/* 1345:     */     {
/* 1346:1585 */       List<CondicionPago> listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("nombre", false, filters);
/* 1347:1586 */       if (listaCondicionPago.isEmpty()) {
/* 1348:1587 */         throw new ExcepcionAS2("msg_info_creacion_cliente_agil_condicion_pago");
/* 1349:     */       }
/* 1350:1589 */       empresa.getCliente().setCondicionPago((CondicionPago)listaCondicionPago.get(0));
/* 1351:     */     }
/* 1352:1593 */     if (empresa.getCliente().getListaPrecios() == null)
/* 1353:     */     {
/* 1354:1594 */       filters.put("indicadorVenta", "true");
/* 1355:1595 */       List<ListaPrecios> listaListaPrecios = this.servicioListaPrecios.obtenerListaCombo("nombre", false, filters);
/* 1356:1596 */       if (listaListaPrecios.isEmpty()) {
/* 1357:1597 */         throw new ExcepcionAS2("msg_info_creacion_cliente_agil_lista_precios");
/* 1358:     */       }
/* 1359:1599 */       empresa.getCliente().setListaPrecios((ListaPrecios)listaListaPrecios.get(0));
/* 1360:     */       
/* 1361:1601 */       filters.remove("indicadorVenta");
/* 1362:     */     }
/* 1363:1606 */     if (empresa.getCliente().getListaDescuentos() == null)
/* 1364:     */     {
/* 1365:1607 */       List<ListaDescuentos> listaListaDescuentos = this.servicioListaDescuentos.obtenerListaCombo("nombre", false, filters);
/* 1366:1608 */       if (!listaListaDescuentos.isEmpty()) {
/* 1367:1609 */         empresa.getCliente().setListaDescuentos((ListaDescuentos)listaListaDescuentos.get(0));
/* 1368:     */       }
/* 1369:     */     }
/* 1370:1613 */     if (empresa.getCliente().getZona() == null)
/* 1371:     */     {
/* 1372:1614 */       List<Zona> listaZona = this.servicioZona.obtenerListaCombo("nombre", false, filters);
/* 1373:1615 */       if (!listaZona.isEmpty()) {
/* 1374:1616 */         empresa.getCliente().setZona((Zona)listaZona.get(0));
/* 1375:     */       }
/* 1376:     */     }
/* 1377:1620 */     empresa.getCliente().setIndicadorEditarPrecio(Boolean.valueOf(true));
/* 1378:     */   }
/* 1379:     */   
/* 1380:     */   private void crearProveedorNuevo(Empresa empresa)
/* 1381:     */     throws ExcepcionAS2, ExcepcionAS2Identification, Exception
/* 1382:     */   {
/* 1383:1625 */     empresa.setProveedor(new Proveedor(empresa));
/* 1384:     */     
/* 1385:1627 */     empresa.getProveedor().setIdOrganizacion(empresa.getIdOrganizacion());
/* 1386:1628 */     empresa.getProveedor().setIdSucursal(empresa.getIdSucursal());
/* 1387:1629 */     empresa.getProveedor().setNumeroCuotas(1);
/* 1388:     */     
/* 1389:1631 */     Map<String, String> filters = new HashMap();
/* 1390:1632 */     filters.put("predeterminado", "true");
/* 1391:1634 */     if (empresa.getProveedor().getFormaPago() == null)
/* 1392:     */     {
/* 1393:1635 */       List<FormaPago> listaFormaPago = this.servicioFormaPago.obtenerListaCombo("nombre", false, filters);
/* 1394:1636 */       if (listaFormaPago.isEmpty()) {
/* 1395:1637 */         throw new ExcepcionAS2("msg_info_creacion_cliente_agil_forma_pago");
/* 1396:     */       }
/* 1397:1639 */       empresa.getProveedor().setFormaPago((FormaPago)listaFormaPago.get(0));
/* 1398:     */     }
/* 1399:1643 */     if (!DatosSRI.getListaFormaPago(AppUtil.getOrganizacion().getId()).isEmpty())
/* 1400:     */     {
/* 1401:1644 */       FormaPagoSRI fpSRI = new FormaPagoSRI();
/* 1402:1645 */       fpSRI.setCodigo(((SelectItem)DatosSRI.getListaFormaPago(AppUtil.getOrganizacion().getId()).get(0)).getValue().toString());
/* 1403:1646 */       fpSRI.setEmpresa(empresa);
/* 1404:1647 */       empresa.getListaFormaPagoSRI().add(fpSRI);
/* 1405:     */     }
/* 1406:1650 */     if (empresa.getProveedor().getCondicionPago() == null)
/* 1407:     */     {
/* 1408:1651 */       List<CondicionPago> listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("nombre", false, filters);
/* 1409:1652 */       if (listaCondicionPago.isEmpty()) {
/* 1410:1653 */         throw new ExcepcionAS2("msg_info_creacion_cliente_agil_condicion_pago");
/* 1411:     */       }
/* 1412:1655 */       empresa.getProveedor().setCondicionPago((CondicionPago)listaCondicionPago.get(0));
/* 1413:     */     }
/* 1414:1659 */     if (empresa.getProveedor().getListaPrecios() == null)
/* 1415:     */     {
/* 1416:1660 */       filters.put("indicadorCompra", "true");
/* 1417:1661 */       List<ListaPrecios> listaListaPrecios = this.servicioListaPrecios.obtenerListaCombo("nombre", false, filters);
/* 1418:1662 */       if (listaListaPrecios.isEmpty()) {
/* 1419:1663 */         throw new ExcepcionAS2("msg_info_creacion_cliente_agil_lista_precios");
/* 1420:     */       }
/* 1421:1665 */       empresa.getProveedor().setListaPrecios((ListaPrecios)listaListaPrecios.get(0));
/* 1422:     */     }
/* 1423:     */   }
/* 1424:     */   
/* 1425:     */   public void validarIdentificacion(String identificacion)
/* 1426:     */     throws ExcepcionAS2Identification
/* 1427:     */   {
/* 1428:1674 */     ValidarIdentificacion.validarIdentificacion(true, identificacion);
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   public Empresa cargarDetalleProducto(Empresa proveedor)
/* 1432:     */   {
/* 1433:1679 */     proveedor = buscarPorId(Integer.valueOf(proveedor.getId()));
/* 1434:1680 */     List<ProductoUltimaCompra> list = this.productoUltimaCompraDao.obtenerPrecioUltimaCompraPorProveedor(proveedor.getId());
/* 1435:1681 */     proveedor.setListaProductoUltimaCompra(list);
/* 1436:1682 */     return proveedor;
/* 1437:     */   }
/* 1438:     */   
/* 1439:     */   public void guardarProductoEmpresa(Empresa proveedor)
/* 1440:     */   {
/* 1441:1687 */     for (ProductoUltimaCompra puc : proveedor.getListaProductoUltimaCompra()) {
/* 1442:1688 */       this.productoUltimaCompraDao.guardar(puc);
/* 1443:     */     }
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   public List<ProductoUltimaCompra> obtenerProveedoresProductoEspecifico(int idProducto)
/* 1447:     */   {
/* 1448:1694 */     return this.productoUltimaCompraDao.obtenerProveedoresProductoEspecifico(idProducto);
/* 1449:     */   }
/* 1450:     */   
/* 1451:     */   public List<Object[]> listaCuentaBancariaEmpresa(int idOrganizacion)
/* 1452:     */   {
/* 1453:1699 */     return this.empresaDao.listaCuentaBancariaEmpresa(idOrganizacion);
/* 1454:     */   }
/* 1455:     */   
/* 1456:     */   public List<LlamadoAtencion> cargarListaLlamadosAtencion(int idEmpleado)
/* 1457:     */   {
/* 1458:1704 */     return this.empresaDao.cargarLlamadosDeAtencion(idEmpleado);
/* 1459:     */   }
/* 1460:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioEmpresaImpl
 * JD-Core Version:    0.7.0.1
 */