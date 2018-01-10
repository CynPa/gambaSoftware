/*    1:     */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.CatalogosACopiar;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*    5:     */ import com.asinfo.as2.dao.BodegaDao;
/*    6:     */ import com.asinfo.as2.dao.CajaDao;
/*    7:     */ import com.asinfo.as2.dao.CategoriaEmpresaDao;
/*    8:     */ import com.asinfo.as2.dao.CategoriaImpuestoDao;
/*    9:     */ import com.asinfo.as2.dao.CategoriaProductoDao;
/*   10:     */ import com.asinfo.as2.dao.CiudadDao;
/*   11:     */ import com.asinfo.as2.dao.CondicionPagoDao;
/*   12:     */ import com.asinfo.as2.dao.ConfiguracionDao;
/*   13:     */ import com.asinfo.as2.dao.DocumentoContabilizacionDao;
/*   14:     */ import com.asinfo.as2.dao.DocumentoDao;
/*   15:     */ import com.asinfo.as2.dao.DocumentoVariableProcesoDao;
/*   16:     */ import com.asinfo.as2.dao.EstadoCivilDao;
/*   17:     */ import com.asinfo.as2.dao.FiltroProductoDao;
/*   18:     */ import com.asinfo.as2.dao.FormaPagoDao;
/*   19:     */ import com.asinfo.as2.dao.GenericoDao;
/*   20:     */ import com.asinfo.as2.dao.ImpuestoDao;
/*   21:     */ import com.asinfo.as2.dao.ListaPreciosDao;
/*   22:     */ import com.asinfo.as2.dao.ModuloDao;
/*   23:     */ import com.asinfo.as2.dao.MonedaDao;
/*   24:     */ import com.asinfo.as2.dao.OrganizacionConfiguracionDao;
/*   25:     */ import com.asinfo.as2.dao.OrganizacionDao;
/*   26:     */ import com.asinfo.as2.dao.PaisDao;
/*   27:     */ import com.asinfo.as2.dao.ProcesoOrganizacionDao;
/*   28:     */ import com.asinfo.as2.dao.ProvinciaDao;
/*   29:     */ import com.asinfo.as2.dao.PuntoDeVentaDao;
/*   30:     */ import com.asinfo.as2.dao.RangoImpuestoDao;
/*   31:     */ import com.asinfo.as2.dao.ReporteadorDao;
/*   32:     */ import com.asinfo.as2.dao.SecuenciaDao;
/*   33:     */ import com.asinfo.as2.dao.SubcategoriaProductoDao;
/*   34:     */ import com.asinfo.as2.dao.SucursalDao;
/*   35:     */ import com.asinfo.as2.dao.TemaDao;
/*   36:     */ import com.asinfo.as2.dao.TipoAsientoDao;
/*   37:     */ import com.asinfo.as2.dao.TipoBodegaDao;
/*   38:     */ import com.asinfo.as2.dao.TipoCuentaBancariaDao;
/*   39:     */ import com.asinfo.as2.dao.TipoIdentificacionDao;
/*   40:     */ import com.asinfo.as2.dao.UbicacionDao;
/*   41:     */ import com.asinfo.as2.dao.UnidadDao;
/*   42:     */ import com.asinfo.as2.dao.UsuarioBodegaDao;
/*   43:     */ import com.asinfo.as2.dao.UsuarioOrganizacionDao;
/*   44:     */ import com.asinfo.as2.dao.UsuarioSucursalDao;
/*   45:     */ import com.asinfo.as2.dao.ZonaDao;
/*   46:     */ import com.asinfo.as2.dao.seguridad.AccionDao;
/*   47:     */ import com.asinfo.as2.dao.seguridad.PermisoDao;
/*   48:     */ import com.asinfo.as2.dao.seguridad.ProcesoDao;
/*   49:     */ import com.asinfo.as2.dao.seguridad.RolDao;
/*   50:     */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*   51:     */ import com.asinfo.as2.dao.sri.ConceptoRetencionSRIDao;
/*   52:     */ import com.asinfo.as2.dao.sri.CreditoTributarioSRIDao;
/*   53:     */ import com.asinfo.as2.dao.sri.TipoComprobanteSRIDao;
/*   54:     */ import com.asinfo.as2.entities.Banco;
/*   55:     */ import com.asinfo.as2.entities.Bodega;
/*   56:     */ import com.asinfo.as2.entities.Caja;
/*   57:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   58:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   59:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   60:     */ import com.asinfo.as2.entities.Ciudad;
/*   61:     */ import com.asinfo.as2.entities.ComprobanteSRICreditoTributarioSRI;
/*   62:     */ import com.asinfo.as2.entities.CondicionPago;
/*   63:     */ import com.asinfo.as2.entities.Configuracion;
/*   64:     */ import com.asinfo.as2.entities.DetalleReporteador;
/*   65:     */ import com.asinfo.as2.entities.DetalleReporteadorVariable;
/*   66:     */ import com.asinfo.as2.entities.Documento;
/*   67:     */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*   68:     */ import com.asinfo.as2.entities.DocumentoVariableProceso;
/*   69:     */ import com.asinfo.as2.entities.EstadoCivil;
/*   70:     */ import com.asinfo.as2.entities.FiltroProducto;
/*   71:     */ import com.asinfo.as2.entities.FormaPago;
/*   72:     */ import com.asinfo.as2.entities.Impuesto;
/*   73:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   74:     */ import com.asinfo.as2.entities.Modulo;
/*   75:     */ import com.asinfo.as2.entities.Moneda;
/*   76:     */ import com.asinfo.as2.entities.Organizacion;
/*   77:     */ import com.asinfo.as2.entities.Pais;
/*   78:     */ import com.asinfo.as2.entities.Parroquia;
/*   79:     */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*   80:     */ import com.asinfo.as2.entities.Provincia;
/*   81:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   82:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   83:     */ import com.asinfo.as2.entities.Reporteador;
/*   84:     */ import com.asinfo.as2.entities.Secuencia;
/*   85:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   86:     */ import com.asinfo.as2.entities.Sucursal;
/*   87:     */ import com.asinfo.as2.entities.TipoAsiento;
/*   88:     */ import com.asinfo.as2.entities.TipoBodega;
/*   89:     */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*   90:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   91:     */ import com.asinfo.as2.entities.TipoIdentificacionComprobanteSRI;
/*   92:     */ import com.asinfo.as2.entities.Ubicacion;
/*   93:     */ import com.asinfo.as2.entities.Unidad;
/*   94:     */ import com.asinfo.as2.entities.UsuarioOrganizacion;
/*   95:     */ import com.asinfo.as2.entities.UsuarioSucursal;
/*   96:     */ import com.asinfo.as2.entities.Zona;
/*   97:     */ import com.asinfo.as2.entities.nomina.asistencia.HoraExtra;
/*   98:     */ import com.asinfo.as2.entities.seguridad.EntidadAccion;
/*   99:     */ import com.asinfo.as2.entities.seguridad.EntidadPermiso;
/*  100:     */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*  101:     */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*  102:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  103:     */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*  104:     */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  105:     */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  106:     */ import com.asinfo.as2.enumeraciones.CatalogosACopiarEnum;
/*  107:     */ import com.asinfo.as2.enumeraciones.Parametro;
/*  108:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*  109:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  110:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  111:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  112:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  113:     */ import com.asinfo.as2.util.AppUtil;
/*  114:     */ import com.asinfo.as2.utils.JsfUtil;
/*  115:     */ import java.lang.reflect.Field;
/*  116:     */ import java.util.ArrayList;
/*  117:     */ import java.util.HashMap;
/*  118:     */ import java.util.Iterator;
/*  119:     */ import java.util.List;
/*  120:     */ import java.util.Map;
/*  121:     */ import java.util.Set;
/*  122:     */ import javax.ejb.EJB;
/*  123:     */ import javax.persistence.Id;
/*  124:     */ 
/*  125:     */ public class ServicioOrganizacionBase
/*  126:     */ {
/*  127:     */   @EJB
/*  128:     */   protected transient OrganizacionDao organizacionDao;
/*  129:     */   @EJB
/*  130:     */   private transient TipoIdentificacionDao tipoIdentificacionDao;
/*  131:     */   @EJB
/*  132:     */   private transient RolDao rolDao;
/*  133:     */   @EJB
/*  134:     */   private transient TemaDao temaDao;
/*  135:     */   @EJB
/*  136:     */   private transient UsuarioDao usuarioDao;
/*  137:     */   @EJB
/*  138:     */   private transient UsuarioSucursalDao usuarioSucursalDao;
/*  139:     */   @EJB
/*  140:     */   private transient ConfiguracionDao configuracionDao;
/*  141:     */   @EJB
/*  142:     */   private transient FormaPagoDao formaPagoDao;
/*  143:     */   @EJB
/*  144:     */   private transient SecuenciaDao secuenciaDao;
/*  145:     */   @EJB
/*  146:     */   private transient TipoAsientoDao tipoAsientoDao;
/*  147:     */   @EJB
/*  148:     */   private transient TipoComprobanteSRIDao tipoComprobanteSRIDao;
/*  149:     */   @EJB
/*  150:     */   private transient CreditoTributarioSRIDao creditoTributarioSRIDao;
/*  151:     */   @EJB
/*  152:     */   private transient ConceptoRetencionSRIDao conceptoRetencionSRIDao;
/*  153:     */   @EJB
/*  154:     */   private transient CondicionPagoDao condicionPagoDao;
/*  155:     */   @EJB
/*  156:     */   private transient UnidadDao unidadDao;
/*  157:     */   @EJB
/*  158:     */   private transient MonedaDao monedaDao;
/*  159:     */   @EJB
/*  160:     */   private transient TipoCuentaBancariaDao tipoCuentaBancariaDao;
/*  161:     */   @EJB
/*  162:     */   private transient TipoBodegaDao tipoBodegaDao;
/*  163:     */   @EJB
/*  164:     */   private transient FiltroProductoDao filtroProductoDao;
/*  165:     */   @EJB
/*  166:     */   private transient ZonaDao zonaDao;
/*  167:     */   @EJB
/*  168:     */   private transient PaisDao paisDao;
/*  169:     */   @EJB
/*  170:     */   private transient EstadoCivilDao estadoCivilDao;
/*  171:     */   @EJB
/*  172:     */   private transient ProvinciaDao provinciaDao;
/*  173:     */   @EJB
/*  174:     */   private transient CiudadDao ciudadDao;
/*  175:     */   @EJB
/*  176:     */   private transient BodegaDao bodegaDao;
/*  177:     */   @EJB
/*  178:     */   private transient UsuarioOrganizacionDao usuarioOrganizacionDao;
/*  179:     */   @EJB
/*  180:     */   private transient SucursalDao sucursalDao;
/*  181:     */   @EJB
/*  182:     */   private transient PuntoDeVentaDao puntoDeVentaDao;
/*  183:     */   @EJB
/*  184:     */   private transient CajaDao cajaDao;
/*  185:     */   @EJB
/*  186:     */   private transient UbicacionDao ubicacionDao;
/*  187:     */   @EJB
/*  188:     */   private transient ListaPreciosDao listaPreciosDao;
/*  189:     */   @EJB
/*  190:     */   private transient DocumentoDao documentoDao;
/*  191:     */   @EJB
/*  192:     */   private transient DocumentoContabilizacionDao documentoContabilizacionDao;
/*  193:     */   @EJB
/*  194:     */   private transient DocumentoVariableProcesoDao documentoVariableProcesoDao;
/*  195:     */   @EJB
/*  196:     */   private transient CategoriaImpuestoDao categoriaImpuestoDao;
/*  197:     */   @EJB
/*  198:     */   private transient ImpuestoDao impuestoDao;
/*  199:     */   @EJB
/*  200:     */   private transient RangoImpuestoDao rangoImpuestoDao;
/*  201:     */   @EJB
/*  202:     */   private transient ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  203:     */   @EJB
/*  204:     */   private transient ModuloDao moduloDao;
/*  205:     */   @EJB
/*  206:     */   private transient ProcesoDao procesoDao;
/*  207:     */   @EJB
/*  208:     */   private transient AccionDao accionDao;
/*  209:     */   @EJB
/*  210:     */   private transient PermisoDao permisoDao;
/*  211:     */   @EJB
/*  212:     */   private transient UsuarioBodegaDao usuarioBodegaDao;
/*  213:     */   @EJB
/*  214:     */   private transient ServicioConfiguracion servicioConfiguracion;
/*  215:     */   @EJB
/*  216:     */   private transient CategoriaEmpresaDao categoriaEmpresaDao;
/*  217:     */   @EJB
/*  218:     */   protected transient OrganizacionConfiguracionDao organizacionConfiguracionDao;
/*  219:     */   @EJB
/*  220:     */   protected transient SubcategoriaProductoDao subcategoriaProductoDao;
/*  221:     */   @EJB
/*  222:     */   protected transient CategoriaProductoDao categoriaProductoDao;
/*  223:     */   @EJB
/*  224:     */   protected transient ProcesoOrganizacionDao procesoOrganizacionDao;
/*  225:     */   @EJB
/*  226:     */   protected transient ServicioGenerico<Banco> servicioBanco;
/*  227:     */   @EJB
/*  228:     */   protected transient GenericoDao<Banco> bancoDao;
/*  229:     */   @EJB
/*  230:     */   protected transient ServicioGenerico<Parroquia> servicioParroquia;
/*  231:     */   @EJB
/*  232:     */   protected transient GenericoDao<Parroquia> parroquiaDao;
/*  233:     */   @EJB
/*  234:     */   protected transient ServicioGenerico<HoraExtra> servicioHoraExtra;
/*  235:     */   @EJB
/*  236:     */   protected transient GenericoDao<HoraExtra> horaExtraDao;
/*  237:     */   @EJB
/*  238:     */   protected transient ServicioGenerico<UsuarioSucursal> servicioUsuarioSucursal;
/*  239:     */   @EJB
/*  240:     */   protected transient GenericoDao<UsuarioSucursal> usuarioSucursalGDao;
/*  241:     */   @EJB
/*  242:     */   protected transient GenericoDao<TipoIdentificacionComprobanteSRI> tipoIdentificacionComprobanteSRIDao;
/*  243:     */   @EJB
/*  244:     */   protected transient GenericoDao<ComprobanteSRICreditoTributarioSRI> comprobanteSRICreditoTributarioSRIDao;
/*  245:     */   @EJB
/*  246:     */   protected transient ReporteadorDao reporteadorDao;
/*  247:     */   @EJB
/*  248:     */   protected transient GenericoDao<DetalleReporteador> detalleReporteadorDao;
/*  249:     */   @EJB
/*  250:     */   protected transient GenericoDao<DetalleReporteadorVariable> detalleReporteadorVariableDao;
/*  251: 271 */   private Map<Integer, Secuencia> hmSecuencia = new HashMap();
/*  252: 272 */   private Map<Integer, Moneda> hmMoneda = new HashMap();
/*  253: 273 */   private Map<Integer, TipoComprobanteSRI> hmTipoComprobanteSRI = new HashMap();
/*  254: 274 */   private Map<Integer, TipoAsiento> hmTipoAsiento = new HashMap();
/*  255: 275 */   private Map<Integer, Modulo> hmModulo = new HashMap();
/*  256: 276 */   private Map<Integer, CategoriaImpuesto> hmCategoriaImpuesto = new HashMap();
/*  257: 277 */   private Map<Integer, Impuesto> hmImpuesto = new HashMap();
/*  258: 278 */   private Map<Integer, EntidadRol> hmRol = new HashMap();
/*  259: 279 */   private Map<Integer, Sucursal> hmSucursal = new HashMap();
/*  260: 280 */   private Map<Integer, UsuarioSucursal> hmUsuarioSucursal = new HashMap();
/*  261: 281 */   private Map<Integer, Bodega> hmBodega = new HashMap();
/*  262: 282 */   private Map<Integer, EntidadProceso> hmProceso = new HashMap();
/*  263: 283 */   private Map<Integer, TipoBodega> hmTipoBodega = new HashMap();
/*  264: 284 */   private Map<Integer, EntidadPermiso> hmPermiso = new HashMap();
/*  265: 285 */   private Map<Integer, Banco> hmBanco = new HashMap();
/*  266: 286 */   private Map<Integer, Parroquia> hmParroquia = new HashMap();
/*  267: 287 */   private Map<Integer, HoraExtra> hmHoraExtra = new HashMap();
/*  268: 288 */   private Map<Integer, Reporteador> hmReportes = new HashMap();
/*  269:     */   
/*  270:     */   public void copiarTipoIdentificacion(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  271:     */   {
/*  272: 298 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  273: 299 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.TIPO_IDENTIFICACION)) && (catalogos.isIndicadorCopiar()))
/*  274:     */       {
/*  275: 300 */         Map<String, String> filters = new HashMap();
/*  276: 301 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  277: 302 */         List<TipoIdentificacion> listaTipoIdentificacion = this.tipoIdentificacionDao.obtenerListaCombo(null, false, filters);
/*  278: 303 */         for (TipoIdentificacion tipoIdentificacion : listaTipoIdentificacion)
/*  279:     */         {
/*  280: 304 */           tipoIdentificacionPersist = (TipoIdentificacion)copiarObjeto(this.tipoIdentificacionDao
/*  281: 305 */             .cargarDetalle(tipoIdentificacion.getId()), true);
/*  282: 306 */           this.tipoIdentificacionDao.detach(tipoIdentificacion);
/*  283: 307 */           tipoIdentificacionPersist.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  284: 308 */           this.tipoIdentificacionDao.guardar(tipoIdentificacionPersist);
/*  285: 309 */           for (TipoIdentificacionComprobanteSRI tipoIdentificacionComprobanteSRI : tipoIdentificacionPersist
/*  286: 310 */             .getListaTipoIdentificacionComprobanteSRI())
/*  287:     */           {
/*  288: 311 */             this.tipoIdentificacionComprobanteSRIDao.detach(tipoIdentificacionComprobanteSRI);
/*  289: 312 */             tipoIdentificacionComprobanteSRIPersist = (TipoIdentificacionComprobanteSRI)copiarObjeto(tipoIdentificacionComprobanteSRI, true);
/*  290:     */             
/*  291: 314 */             tipoIdentificacionComprobanteSRIPersist.setIdOrganizacion(organizacion.getId());
/*  292: 315 */             tipoIdentificacionComprobanteSRIPersist.setTipoIdentificacion(tipoIdentificacionPersist);
/*  293: 316 */             this.tipoIdentificacionComprobanteSRIDao.guardar(tipoIdentificacionComprobanteSRIPersist);
/*  294: 317 */             for (CreditoTributarioSRI cr : tipoIdentificacionComprobanteSRIPersist.getTipoComprobanteSRI().getListaCreditoTributarioSRI())
/*  295:     */             {
/*  296: 318 */               ComprobanteSRICreditoTributarioSRI newObj = new ComprobanteSRICreditoTributarioSRI();
/*  297: 319 */               newObj.setCreditoTributarioSRI(cr);
/*  298: 320 */               newObj.setTipoIdentificacion(tipoIdentificacionPersist);
/*  299: 321 */               newObj.setIdOrganizacion(organizacion.getId());
/*  300: 322 */               newObj.setTipoComprobanteSRI(tipoIdentificacionComprobanteSRIPersist.getTipoComprobanteSRI());
/*  301: 323 */               this.comprobanteSRICreditoTributarioSRIDao.guardar(newObj);
/*  302:     */             }
/*  303:     */           }
/*  304:     */         }
/*  305:     */       }
/*  306:     */     }
/*  307:     */     TipoIdentificacion tipoIdentificacionPersist;
/*  308:     */     TipoIdentificacionComprobanteSRI tipoIdentificacionComprobanteSRIPersist;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public void copiarSucursal(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  312:     */   {
/*  313: 340 */     this.hmSucursal.clear();
/*  314: 341 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  315: 342 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.SUCURSAL)) && (catalogos.isIndicadorCopiar()))
/*  316:     */       {
/*  317: 343 */         Map<String, String> filters = new HashMap();
/*  318: 344 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  319: 345 */         List<Sucursal> listaSucursal = this.sucursalDao.obtenerListaCombo(null, false, filters);
/*  320: 346 */         for (localIterator2 = listaSucursal.iterator(); localIterator2.hasNext();)
/*  321:     */         {
/*  322: 346 */           sucursal = (Sucursal)localIterator2.next();
/*  323: 347 */           this.hmSucursal.put(Integer.valueOf(sucursal.getIdSucursal()), sucursal);
/*  324: 348 */           Map<String, String> filtersPuntoVenta = new HashMap();
/*  325: 349 */           filtersPuntoVenta.put("sucursal.idSucursal", String.valueOf(sucursal.getIdSucursal()));
/*  326: 350 */           filtersPuntoVenta.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  327: 351 */           List<PuntoDeVenta> listaPuntoDeVenta = this.puntoDeVentaDao.obtenerListaCombo(null, false, filtersPuntoVenta);
/*  328: 352 */           Map<String, String> filtersUsuarioSucursal = new HashMap();
/*  329: 353 */           filtersUsuarioSucursal.put("sucursal.idSucursal", String.valueOf(sucursal.getIdSucursal()));
/*  330: 354 */           filtersUsuarioSucursal.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  331: 355 */           List<UsuarioSucursal> listaUsuarioSucursal = this.usuarioSucursalDao.obtenerListaCombo(null, false, filtersPuntoVenta);
/*  332: 356 */           this.ubicacionDao.detach(sucursal.getUbicacion());
/*  333: 357 */           Ubicacion ubicacionPersist = (Ubicacion)copiarObjeto(this.ubicacionDao.buscarPorId(Integer.valueOf(sucursal.getUbicacion().getId())), false);
/*  334: 358 */           ubicacionPersist.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  335: 359 */           this.ubicacionDao.guardar(ubicacionPersist);
/*  336: 360 */           this.sucursalDao.detach(sucursal);
/*  337: 361 */           sucursal = (Sucursal)copiarObjeto(this.sucursalDao.buscarPorId(Integer.valueOf(sucursal.getId())), false);
/*  338: 362 */           sucursal.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  339: 363 */           sucursal.setUbicacion(ubicacionPersist);
/*  340: 364 */           this.sucursalDao.guardar(sucursal);
/*  341: 365 */           ubicacionPersist.setIdSucursal(sucursal.getId());
/*  342: 366 */           this.ubicacionDao.guardar(ubicacionPersist);
/*  343: 367 */           for (UsuarioSucursal usuarioSucursal : listaUsuarioSucursal)
/*  344:     */           {
/*  345: 368 */             this.hmUsuarioSucursal.put(Integer.valueOf(usuarioSucursal.getIdUsuarioSucursal()), usuarioSucursal);
/*  346: 369 */             this.usuarioSucursalDao.detach(usuarioSucursal);
/*  347: 370 */             usuarioSucursal.setIdUsuarioSucursal(0);
/*  348: 371 */             usuarioSucursal.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  349: 372 */             usuarioSucursal.setEntidadUsuario(usuarioSucursal.getEntidadUsuario());
/*  350: 373 */             usuarioSucursal.setSucursal(sucursal);
/*  351: 374 */             this.usuarioSucursalGDao.guardar(usuarioSucursal);
/*  352:     */           }
/*  353: 376 */           for (??? = listaPuntoDeVenta.iterator(); ???.hasNext();)
/*  354:     */           {
/*  355: 376 */             puntoDeVenta = (PuntoDeVenta)???.next();
/*  356: 377 */             Map<String, String> filtersCaja = new HashMap();
/*  357: 378 */             filtersCaja.put("puntoDeVenta.idPuntoDeVenta", String.valueOf(puntoDeVenta.getIdPuntoDeVenta()));
/*  358: 379 */             filtersCaja.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  359: 380 */             this.puntoDeVentaDao.detach(puntoDeVenta);
/*  360: 381 */             puntoDeVenta = (PuntoDeVenta)copiarObjeto(this.puntoDeVentaDao.buscarPorId(Integer.valueOf(puntoDeVenta.getId())), false);
/*  361: 382 */             puntoDeVenta.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  362: 383 */             puntoDeVenta.setSucursal(sucursal);
/*  363: 384 */             this.puntoDeVentaDao.guardar(puntoDeVenta);
/*  364:     */             
/*  365: 386 */             List<Caja> listaCaja = this.cajaDao.obtenerListaCombo(null, false, filtersCaja);
/*  366: 387 */             for (Caja caja : listaCaja)
/*  367:     */             {
/*  368: 388 */               this.cajaDao.detach(caja);
/*  369: 389 */               caja = (Caja)copiarObjeto(this.cajaDao.buscarPorId(Integer.valueOf(caja.getId())), false);
/*  370: 390 */               caja.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  371: 391 */               caja.setIdSucursal(puntoDeVenta.getSucursal().getIdSucursal());
/*  372: 392 */               caja.setPuntoDeVenta(puntoDeVenta);
/*  373: 393 */               this.cajaDao.guardar(caja);
/*  374:     */             }
/*  375:     */           }
/*  376:     */         }
/*  377:     */       }
/*  378:     */     }
/*  379:     */     Iterator localIterator2;
/*  380:     */     Sucursal sucursal;
/*  381:     */     PuntoDeVenta puntoDeVenta;
/*  382:     */   }
/*  383:     */   
/*  384:     */   public void copiarModulo(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  385:     */   {
/*  386: 409 */     this.hmModulo.clear();
/*  387: 410 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  388: 411 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.MODULO)) && (catalogos.isIndicadorCopiar()))
/*  389:     */       {
/*  390: 412 */         Map<String, String> filters = new HashMap();
/*  391: 413 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  392: 414 */         List<Modulo> listaModulo = this.moduloDao.obtenerListaCombo(null, false, filters);
/*  393: 415 */         for (Modulo modulo : listaModulo)
/*  394:     */         {
/*  395: 416 */           this.hmModulo.put(Integer.valueOf(modulo.getIdModuloMenu()), modulo);
/*  396: 417 */           this.moduloDao.detach(modulo);
/*  397: 418 */           modulo.setIdModuloMenu(0);
/*  398: 419 */           modulo.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  399: 420 */           this.moduloDao.guardar(modulo);
/*  400:     */         }
/*  401:     */       }
/*  402:     */     }
/*  403:     */   }
/*  404:     */   
/*  405:     */   public void copiarProcesos(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  406:     */   {
/*  407: 434 */     this.hmProceso.clear();
/*  408: 435 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  409: 436 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.MODULO)) && (catalogos.isIndicadorCopiar()))
/*  410:     */       {
/*  411: 437 */         Map<String, String> filters = new HashMap();
/*  412: 438 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  413: 439 */         List<EntidadProceso> listaProceso = this.procesoDao.obtenerListaCombo(null, false, filters);
/*  414: 440 */         for (EntidadProceso proceso : listaProceso)
/*  415:     */         {
/*  416: 441 */           this.hmProceso.put(Integer.valueOf(proceso.getIdProceso()), proceso);
/*  417: 442 */           if (proceso.getModulo() != null) {
/*  418: 443 */             proceso.setModulo((Modulo)this.hmModulo.get(Integer.valueOf(proceso.getModulo().getIdModuloMenu())));
/*  419:     */           }
/*  420: 445 */           this.procesoDao.detach(proceso);
/*  421: 446 */           proceso.setIdProceso(0);
/*  422: 447 */           proceso.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  423: 448 */           this.procesoDao.guardar(proceso);
/*  424:     */         }
/*  425:     */       }
/*  426:     */     }
/*  427:     */   }
/*  428:     */   
/*  429:     */   @Deprecated
/*  430:     */   public void copiarRol(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  431:     */   {
/*  432: 463 */     this.hmRol.clear();
/*  433: 464 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  434: 465 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.ROL)) && (catalogos.isIndicadorCopiar()))
/*  435:     */       {
/*  436: 466 */         Map<String, String> filters = new HashMap();
/*  437: 467 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  438: 468 */         List<EntidadRol> listaRol = this.rolDao.obtenerListaCombo(null, false, filters);
/*  439: 469 */         for (EntidadRol rol : listaRol)
/*  440:     */         {
/*  441: 470 */           this.hmRol.put(Integer.valueOf(rol.getIdRol()), rol);
/*  442: 471 */           this.rolDao.detach(rol);
/*  443: 472 */           rol.setIdRol(0);
/*  444: 473 */           rol.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  445: 474 */           this.rolDao.guardar(rol);
/*  446:     */         }
/*  447:     */       }
/*  448:     */     }
/*  449:     */   }
/*  450:     */   
/*  451:     */   public void copiarPermiso(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  452:     */   {
/*  453: 493 */     this.hmPermiso.clear();
/*  454: 494 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  455: 495 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.PERMISO)) && (catalogos.isIndicadorCopiar()))
/*  456:     */       {
/*  457: 496 */         Map<String, String> filters = new HashMap();
/*  458: 497 */         filters.put("procesoOrganizacion.organizacion.idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  459: 498 */         filters.put("procesoOrganizacion.entidadProceso.activo", "=true");
/*  460: 499 */         List<EntidadPermiso> listaPermiso = this.permisoDao.obtenerListaCombo(null, false, filters);
/*  461: 500 */         mapaProcesos = new HashMap();
/*  462:     */         
/*  463: 502 */         List<ProcesoOrganizacion> listaProcesoOrganizacion = this.procesoOrganizacionDao.buscarPorSistemaOrganizacion(null, organizacionACopiar);
/*  464: 503 */         for (ProcesoOrganizacion procesoOrganizacion : listaProcesoOrganizacion)
/*  465:     */         {
/*  466: 504 */           this.procesoOrganizacionDao.detach(procesoOrganizacion);
/*  467: 505 */           procesoOrganizacion.setIdProcesoOrganizacion(0);
/*  468: 506 */           procesoOrganizacion.setOrganizacion(organizacion);
/*  469: 507 */           this.procesoOrganizacionDao.guardar(procesoOrganizacion);
/*  470: 508 */           mapaProcesos.put(Integer.valueOf(procesoOrganizacion.getEntidadProceso().getId()), procesoOrganizacion);
/*  471:     */         }
/*  472: 511 */         for (EntidadPermiso entidadPermiso : listaPermiso)
/*  473:     */         {
/*  474: 512 */           this.hmPermiso.put(Integer.valueOf(entidadPermiso.getId()), entidadPermiso);
/*  475: 513 */           ProcesoOrganizacion procesoOrganizacion = (ProcesoOrganizacion)mapaProcesos.get(Integer.valueOf(entidadPermiso.getProcesoOrganizacion().getEntidadProceso().getId()));
/*  476:     */           
/*  477: 515 */           EntidadPermiso permisoNew = new EntidadPermiso();
/*  478: 516 */           permisoNew.setEntidadRol(entidadPermiso.getEntidadRol());
/*  479: 517 */           permisoNew.setProcesoOrganizacion(procesoOrganizacion);
/*  480: 518 */           permisoNew.setListaAccion(new ArrayList());
/*  481: 520 */           for (EntidadAccion accion : entidadPermiso.getListaAccion()) {
/*  482: 521 */             permisoNew.getListaAccion().add(accion);
/*  483:     */           }
/*  484: 524 */           this.permisoDao.guardar(permisoNew);
/*  485:     */         }
/*  486:     */       }
/*  487:     */     }
/*  488:     */     Map<Integer, ProcesoOrganizacion> mapaProcesos;
/*  489:     */   }
/*  490:     */   
/*  491:     */   public void copiarUsuario(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  492:     */   {
/*  493: 538 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  494: 539 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.USUARIO)) && (catalogos.isIndicadorCopiar()))
/*  495:     */       {
/*  496: 540 */         Usuario usuario = AppUtil.getUsuarioEnSesion();
/*  497: 541 */         UsuarioOrganizacion usuarioOrganizacion = new UsuarioOrganizacion();
/*  498: 542 */         EntidadUsuario entidadUsuario = new EntidadUsuario();
/*  499: 543 */         entidadUsuario.setIdUsuario(usuario.getIdUsuario());
/*  500: 544 */         usuarioOrganizacion.setOrganizacion(organizacion);
/*  501: 545 */         usuarioOrganizacion.setEntidadUsuario(entidadUsuario);
/*  502: 546 */         this.usuarioOrganizacionDao.guardar(usuarioOrganizacion);
/*  503:     */       }
/*  504:     */     }
/*  505:     */   }
/*  506:     */   
/*  507:     */   public void copiarDocumentoContabilizacion(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  508:     */   {
/*  509: 561 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  510: 562 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.DOCUMENTO_CONTABILIZACION)) && (catalogos.isIndicadorCopiar()))
/*  511:     */       {
/*  512: 563 */         Map<String, String> filters = new HashMap();
/*  513: 564 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  514:     */         
/*  515: 566 */         List<DocumentoContabilizacion> listaDocumento = this.documentoContabilizacionDao.obtenerListaCombo(null, false, filters);
/*  516: 568 */         for (DocumentoContabilizacion docContabilizacion : listaDocumento)
/*  517:     */         {
/*  518: 569 */           DocumentoContabilizacion docCon = (DocumentoContabilizacion)copiarObjeto(this.documentoContabilizacionDao
/*  519: 570 */             .buscarPorId(Integer.valueOf(docContabilizacion.getIdDocumentoContabilizacion())), false);
/*  520: 571 */           this.documentoContabilizacionDao.detach(docContabilizacion);
/*  521: 572 */           docCon.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  522: 573 */           this.documentoContabilizacionDao.guardar(docCon);
/*  523:     */         }
/*  524:     */       }
/*  525:     */     }
/*  526:     */   }
/*  527:     */   
/*  528:     */   public void copiarDocumentoVariableProceso(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  529:     */   {
/*  530: 590 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  531: 591 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.DOCUMENTO_VARIABLE_PROCESO)) && (catalogos.isIndicadorCopiar()))
/*  532:     */       {
/*  533: 592 */         Map<String, String> filters = new HashMap();
/*  534: 593 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  535:     */         
/*  536: 595 */         List<DocumentoVariableProceso> listaDocumento = this.documentoVariableProcesoDao.obtenerListaCombo(null, false, filters);
/*  537: 597 */         for (DocumentoVariableProceso docVariableProceso : listaDocumento)
/*  538:     */         {
/*  539: 598 */           DocumentoVariableProceso docVariableProcesoPersist = (DocumentoVariableProceso)this.documentoVariableProcesoDao.buscarPorId(Integer.valueOf(docVariableProceso.getId()));
/*  540: 599 */           this.documentoVariableProcesoDao.detach(docVariableProceso);
/*  541: 600 */           docVariableProcesoPersist.setIdDocumentoVariableProceso(0);
/*  542: 601 */           docVariableProcesoPersist.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  543: 602 */           this.documentoVariableProcesoDao.guardar(docVariableProcesoPersist);
/*  544:     */         }
/*  545:     */       }
/*  546:     */     }
/*  547:     */   }
/*  548:     */   
/*  549:     */   public void copiarFiltroProducto(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  550:     */   {
/*  551: 617 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  552: 618 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.FILTRO_PRODUCTO)) && (catalogos.isIndicadorCopiar()))
/*  553:     */       {
/*  554: 619 */         FiltroProducto filtroProducto = new FiltroProducto();
/*  555: 620 */         filtroProducto.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  556: 621 */         filtroProducto.setIdSucursal(1);
/*  557: 622 */         filtroProducto.setIndicadorAtributo1(false);
/*  558: 623 */         filtroProducto.setIndicadorAtributo2(false);
/*  559: 624 */         filtroProducto.setIndicadorAtributo3(false);
/*  560: 625 */         filtroProducto.setIndicadorAtributo4(false);
/*  561: 626 */         filtroProducto.setIndicadorAtributo5(false);
/*  562: 627 */         filtroProducto.setIndicadorAtributo6(false);
/*  563: 628 */         filtroProducto.setIndicadorAtributo7(false);
/*  564: 629 */         filtroProducto.setIndicadorAtributo8(false);
/*  565: 630 */         filtroProducto.setIndicadorAtributo9(false);
/*  566: 631 */         filtroProducto.setIndicadorAtributo10(false);
/*  567: 632 */         filtroProducto.setIndicadorCategoriaProducto(true);
/*  568: 633 */         filtroProducto.setIndicadorCodigo(true);
/*  569: 634 */         filtroProducto.setIndicadorCodigoAlterno(true);
/*  570: 635 */         filtroProducto.setIndicadorDescripcion(true);
/*  571: 636 */         filtroProducto.setIndicadorNombre(true);
/*  572: 637 */         filtroProducto.setIndicadorNombreComercial(true);
/*  573: 638 */         filtroProducto.setIndicadorSubcategoriaProducto(true);
/*  574: 639 */         filtroProducto.setIndicadorUnidad(true);
/*  575: 640 */         filtroProducto.setIndicadorUnidadAlmacenamiento(false);
/*  576: 641 */         filtroProducto.setIndicadorUnidadCompra(false);
/*  577: 642 */         filtroProducto.setIndicadorUnidadVenta(false);
/*  578:     */         
/*  579: 644 */         this.filtroProductoDao.guardar(filtroProducto);
/*  580:     */       }
/*  581:     */     }
/*  582:     */   }
/*  583:     */   
/*  584:     */   public void copiarConfiguracion(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  585:     */     throws ExcepcionAS2
/*  586:     */   {
/*  587: 659 */     List<Parametro> listaParametrosCuentaContables = new ArrayList();
/*  588: 660 */     listaParametrosCuentaContables.add(Parametro.CUENTA_UTILIDAD_PERIODO_ACTUAL);
/*  589: 661 */     listaParametrosCuentaContables.add(Parametro.CUENTA_UTILIDAD_PERIODO_ANTERIOR);
/*  590: 662 */     listaParametrosCuentaContables.add(Parametro.CUENTA_PERDIDA_PERIODO_ACTUAL);
/*  591: 663 */     listaParametrosCuentaContables.add(Parametro.CUENTA_PERDIDA_PERIODO_ANTERIOR);
/*  592: 664 */     listaParametrosCuentaContables.add(Parametro.CUENTA_ORDEN_IMPUESTO_RENTA);
/*  593: 665 */     listaParametrosCuentaContables.add(Parametro.CUENTA_BONO_CXP);
/*  594: 666 */     listaParametrosCuentaContables.add(Parametro.CUENTA_IMPUESTO_DIFERIDO);
/*  595: 667 */     listaParametrosCuentaContables.add(Parametro.CUENTA_RETENCION_ASUMIDA);
/*  596:     */     
/*  597:     */ 
/*  598: 670 */     List<Parametro> listaParametrosRubros = new ArrayList();
/*  599: 671 */     listaParametrosCuentaContables.add(Parametro.RUBRO_SALARIO_UNIFICADO);
/*  600: 672 */     listaParametrosCuentaContables.add(Parametro.RUBRO_APORTE_PATRONAL_IESS);
/*  601: 673 */     listaParametrosCuentaContables.add(Parametro.RUBRO_APORTE_PERSONAL_IESS);
/*  602: 674 */     listaParametrosCuentaContables.add(Parametro.RUBRO_UTILIDAD);
/*  603: 675 */     listaParametrosCuentaContables.add(Parametro.RUBRO_IMPUESTO_A_LA_RENTA);
/*  604:     */     
/*  605:     */ 
/*  606: 678 */     List<Parametro> listaParametroTipoAsientos = new ArrayList();
/*  607: 679 */     listaParametroTipoAsientos.add(Parametro.TIPO_ASIENTO_INTERFAZ_VENTAS);
/*  608: 680 */     listaParametroTipoAsientos.add(Parametro.TIPO_ASIENTO_INTERFAZ_DEPRECIACION);
/*  609: 681 */     listaParametroTipoAsientos.add(Parametro.TIPO_ASIENTO_INTERFAZ_AJUSTE_DEPRECIACION);
/*  610: 682 */     listaParametroTipoAsientos.add(Parametro.TIPO_ASIENTO_CONCILIACION_BANCARIA_AUTOMATICA);
/*  611: 683 */     listaParametroTipoAsientos.add(Parametro.TIPO_ASIENTO_CIERRE_CONTABLE_CUENTA);
/*  612: 684 */     listaParametroTipoAsientos.add(Parametro.TIPO_ASIENTO_CONSUMO_BODEGA);
/*  613: 685 */     listaParametroTipoAsientos.add(Parametro.TIPO_ASIENTO_DESPACHO);
/*  614: 686 */     listaParametroTipoAsientos.add(Parametro.TIPO_ASIENTO_INTERFAZ_DEVOLUCION_COSTO_VENTAS);
/*  615: 687 */     listaParametroTipoAsientos.add(Parametro.TIPO_ASIENTO_PROTESTO);
/*  616: 689 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  617: 690 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.CONFIGURACION)) && (catalogos.isIndicadorCopiar()))
/*  618:     */       {
/*  619: 691 */         Map<String, String> filters = new HashMap();
/*  620: 692 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  621: 693 */         List<Configuracion> listaConfiguracion = this.configuracionDao.obtenerListaCombo(null, false, filters);
/*  622: 694 */         for (Configuracion configuracion : listaConfiguracion)
/*  623:     */         {
/*  624: 695 */           this.configuracionDao.detach(configuracion);
/*  625: 696 */           for (Parametro parametro : listaParametrosCuentaContables) {
/*  626: 697 */             if (configuracion.getParametro().equals(parametro))
/*  627:     */             {
/*  628: 698 */               configuracion.setValor("");
/*  629: 699 */               configuracion.setValorMostrar("");
/*  630:     */             }
/*  631:     */           }
/*  632: 702 */           for (??? = listaParametrosRubros.iterator(); ???.hasNext();)
/*  633:     */           {
/*  634: 702 */             parametro = (Parametro)???.next();
/*  635: 703 */             if (configuracion.getParametro().equals(parametro))
/*  636:     */             {
/*  637: 704 */               configuracion.setValor("");
/*  638: 705 */               configuracion.setValorMostrar("");
/*  639:     */             }
/*  640:     */           }
/*  641:     */           Parametro parametro;
/*  642: 708 */           TipoAsiento tipoAsiento = null;
/*  643: 709 */           for (Parametro parametro : listaParametroTipoAsientos) {
/*  644: 710 */             if (configuracion.getParametro().equals(parametro)) {
/*  645:     */               try
/*  646:     */               {
/*  647: 712 */                 tipoAsiento = (TipoAsiento)this.hmTipoAsiento.get(Integer.valueOf(Integer.parseInt(configuracion.getValor())));
/*  648: 713 */                 if (tipoAsiento != null)
/*  649:     */                 {
/*  650: 714 */                   configuracion.setValor(String.valueOf(tipoAsiento.getIdTipoAsiento()));
/*  651: 715 */                   configuracion.setValorMostrar(tipoAsiento.getNombre());
/*  652:     */                 }
/*  653:     */                 else
/*  654:     */                 {
/*  655: 717 */                   configuracion.setValor("");
/*  656: 718 */                   configuracion.setValorMostrar("");
/*  657:     */                 }
/*  658:     */               }
/*  659:     */               catch (NumberFormatException e)
/*  660:     */               {
/*  661: 721 */                 configuracion.setValor("");
/*  662: 722 */                 configuracion.setValorMostrar("");
/*  663:     */               }
/*  664:     */             }
/*  665:     */           }
/*  666: 726 */           configuracion.setIdConfiguracion(0);
/*  667: 727 */           configuracion.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  668: 728 */           this.configuracionDao.guardar(configuracion);
/*  669:     */         }
/*  670:     */       }
/*  671:     */     }
/*  672: 732 */     this.servicioConfiguracion.cargarConfiguracion(Integer.valueOf(organizacion.getIdOrganizacion()));
/*  673:     */   }
/*  674:     */   
/*  675:     */   public void copiarFormaPago(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  676:     */     throws AS2Exception
/*  677:     */   {
/*  678: 745 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  679: 746 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.FORMA_PAGO)) && (catalogos.isIndicadorCopiar()))
/*  680:     */       {
/*  681: 747 */         Map<String, String> filters = new HashMap();
/*  682: 748 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  683: 749 */         List<FormaPago> listaFormaPago = this.formaPagoDao.obtenerListaCombo(null, false, filters);
/*  684: 750 */         for (FormaPago formaPago : listaFormaPago)
/*  685:     */         {
/*  686: 751 */           this.formaPagoDao.detach(formaPago);
/*  687: 752 */           formaPago.setIdFormaPago(0);
/*  688: 753 */           if ((formaPago.getCodigoAlterno() != null) && (formaPago.getCodigoAlterno().length() < 2)) {
/*  689: 754 */             throw new AS2Exception("com.asinfo.as2.configuracionbase.servicio.impl.ServicioOrganizacionBase.ERROR_LONGITUD_CODIGO", new String[] { "la Forma de pago", "codigo alterno" });
/*  690:     */           }
/*  691: 755 */           formaPago.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  692: 756 */           this.formaPagoDao.guardar(formaPago);
/*  693:     */         }
/*  694:     */       }
/*  695:     */     }
/*  696:     */   }
/*  697:     */   
/*  698:     */   public void copiarSecuencia(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  699:     */   {
/*  700: 770 */     this.hmSecuencia.clear();
/*  701: 771 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  702: 772 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.SECUENCIA)) && (catalogos.isIndicadorCopiar()))
/*  703:     */       {
/*  704: 773 */         Map<String, String> filters = new HashMap();
/*  705: 774 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  706: 775 */         List<Secuencia> listaSecuencia = this.secuenciaDao.obtenerListaCombo(null, false, filters);
/*  707: 776 */         for (Secuencia secuencia : listaSecuencia)
/*  708:     */         {
/*  709: 777 */           this.hmSecuencia.put(Integer.valueOf(secuencia.getIdSecuencia()), secuencia);
/*  710: 778 */           this.secuenciaDao.detach(secuencia);
/*  711: 779 */           secuencia.setNumero(0);
/*  712: 780 */           secuencia.setDesde(1);
/*  713: 781 */           secuencia.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  714: 782 */           secuencia.setIdSecuencia(0);
/*  715: 783 */           secuencia.setNumero(0);
/*  716: 784 */           secuencia.setDesde(1);
/*  717: 785 */           this.secuenciaDao.guardar(secuencia);
/*  718:     */         }
/*  719:     */       }
/*  720:     */     }
/*  721:     */   }
/*  722:     */   
/*  723:     */   public void copiarDocumento(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  724:     */   {
/*  725: 800 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  726: 801 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.DOCUMENTO)) && (catalogos.isIndicadorCopiar()))
/*  727:     */       {
/*  728: 802 */         Map<String, String> filters = new HashMap();
/*  729: 803 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  730: 804 */         List<Documento> listaDocumento = this.documentoDao.obtenerListaCombo(null, false, filters);
/*  731: 805 */         i = 0;
/*  732: 806 */         for (Documento documento : listaDocumento)
/*  733:     */         {
/*  734: 807 */           i++;
/*  735: 808 */           documento = this.documentoDao.cargarDetalle(documento.getIdDocumento());
/*  736: 809 */           this.documentoDao.detach(documento);
/*  737: 810 */           if (documento.getTipoComprobanteSRI() != null) {
/*  738: 811 */             documento.setTipoComprobanteSRI((TipoComprobanteSRI)this.hmTipoComprobanteSRI.get(Integer.valueOf(documento.getTipoComprobanteSRI().getIdTipoComprobanteSRI())));
/*  739:     */           }
/*  740: 814 */           if (documento.getTipoAsiento() != null) {
/*  741: 815 */             documento.setTipoAsiento((TipoAsiento)this.hmTipoAsiento.get(Integer.valueOf(documento.getTipoAsiento().getIdTipoAsiento())));
/*  742:     */           }
/*  743: 817 */           if (documento.getReporte().isEmpty()) {
/*  744: 818 */             documento.setReporte("NA");
/*  745:     */           }
/*  746: 820 */           documento.setIdDocumento(0);
/*  747: 821 */           documento.getListaAutorizacionDocumentoSRI().clear();
/*  748: 822 */           documento.getListaDocumentoGastoImportacion().clear();
/*  749: 823 */           documento.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  750: 824 */           if ((documento.getCodigo() == null) || (documento.getCodigo().isEmpty())) {
/*  751: 825 */             if (documento.getNombre().length() > 6) {
/*  752: 826 */               documento.setCodigo(i + "_" + documento.getNombre().substring(0, 6));
/*  753:     */             } else {
/*  754: 828 */               documento.setCodigo(i + "_" + documento.getNombre().substring(0, documento.getNombre().length()));
/*  755:     */             }
/*  756:     */           }
/*  757: 831 */           if (documento.getSecuencia() != null) {
/*  758: 832 */             documento.setSecuencia((Secuencia)this.hmSecuencia.get(Integer.valueOf(documento.getSecuencia().getIdSecuencia())));
/*  759:     */           }
/*  760: 834 */           this.documentoDao.guardar(documento);
/*  761:     */         }
/*  762:     */       }
/*  763:     */     }
/*  764:     */     int i;
/*  765:     */   }
/*  766:     */   
/*  767:     */   public void copiarTipoComprobanteSRI(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  768:     */   {
/*  769: 856 */     this.hmTipoComprobanteSRI.clear();
/*  770: 857 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  771: 858 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.TIPO_COMPROBANTE_SRI)) && (catalogos.isIndicadorCopiar()))
/*  772:     */       {
/*  773: 859 */         Map<String, String> filters = new HashMap();
/*  774: 860 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  775: 861 */         List<TipoComprobanteSRI> listaTipoComprobanteSRI = this.tipoComprobanteSRIDao.obtenerListaCombo(null, false, filters);
/*  776: 862 */         for (TipoComprobanteSRI tipoComprobanteSRI : listaTipoComprobanteSRI)
/*  777:     */         {
/*  778: 863 */           this.hmTipoComprobanteSRI.put(Integer.valueOf(tipoComprobanteSRI.getIdTipoComprobanteSRI()), tipoComprobanteSRI);
/*  779: 864 */           this.tipoComprobanteSRIDao.detach(tipoComprobanteSRI);
/*  780: 865 */           tipoComprobanteSRI.setIdTipoComprobanteSRI(0);
/*  781: 866 */           tipoComprobanteSRI.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  782: 867 */           this.tipoComprobanteSRIDao.guardar(tipoComprobanteSRI);
/*  783:     */         }
/*  784:     */       }
/*  785:     */     }
/*  786:     */   }
/*  787:     */   
/*  788:     */   public void copiarTipoAsiento(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  789:     */   {
/*  790: 881 */     this.hmTipoAsiento.clear();
/*  791:     */     
/*  792: 883 */     HashMap<String, Secuencia> hmSecuencia = new HashMap();
/*  793: 884 */     HashMap<String, String> filtros = new HashMap();
/*  794: 885 */     filtros.put("idOrganizacion", "" + organizacion.getIdOrganizacion());
/*  795: 886 */     for (Secuencia secuencia : this.secuenciaDao.obtenerListaCombo("nombre", true, filtros)) {
/*  796: 887 */       hmSecuencia.put(secuencia.getNombre(), secuencia);
/*  797:     */     }
/*  798: 890 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  799: 891 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.TIPO_ASIENTO)) && (catalogos.isIndicadorCopiar()))
/*  800:     */       {
/*  801: 892 */         Map<String, String> filters = new HashMap();
/*  802: 893 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  803: 894 */         List<TipoAsiento> listaTipoAsiento = this.tipoAsientoDao.obtenerListaCombo(null, false, filters);
/*  804: 895 */         for (TipoAsiento tipoAsiento : listaTipoAsiento)
/*  805:     */         {
/*  806: 896 */           this.hmTipoAsiento.put(Integer.valueOf(tipoAsiento.getIdTipoAsiento()), tipoAsiento);
/*  807: 897 */           this.tipoAsientoDao.detach(tipoAsiento);
/*  808: 898 */           tipoAsiento.setIdTipoAsiento(0);
/*  809: 899 */           tipoAsiento.setSecuencia((Secuencia)hmSecuencia.get(tipoAsiento.getSecuencia().getNombre()));
/*  810: 900 */           tipoAsiento.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  811: 901 */           this.tipoAsientoDao.guardar(tipoAsiento);
/*  812:     */         }
/*  813:     */       }
/*  814:     */     }
/*  815:     */   }
/*  816:     */   
/*  817:     */   public void copiarCreditoTributarioSRI(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  818:     */   {
/*  819: 915 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  820: 916 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.CREDITO_TRIBUTARIO)) && (catalogos.isIndicadorCopiar()))
/*  821:     */       {
/*  822: 917 */         Map<String, String> filters = new HashMap();
/*  823: 918 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  824: 919 */         List<CreditoTributarioSRI> listaCreditoTributarioSRI = this.creditoTributarioSRIDao.obtenerListaCombo(null, false, filters);
/*  825: 920 */         for (CreditoTributarioSRI creditoTributarioSRI : listaCreditoTributarioSRI)
/*  826:     */         {
/*  827: 921 */           this.creditoTributarioSRIDao.detach(creditoTributarioSRI);
/*  828: 922 */           creditoTributarioSRI.setIdCreditoTributarioSRI(0);
/*  829: 923 */           this.creditoTributarioSRIDao.guardar(creditoTributarioSRI);
/*  830:     */         }
/*  831:     */       }
/*  832:     */     }
/*  833:     */   }
/*  834:     */   
/*  835:     */   public void copiarConceptoRetencionSRI(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  836:     */   {
/*  837: 937 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  838: 938 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.CONCEPTO_RETENCION)) && (catalogos.isIndicadorCopiar()))
/*  839:     */       {
/*  840: 939 */         Map<String, String> filters = new HashMap();
/*  841: 940 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  842: 941 */         List<ConceptoRetencionSRI> listaConceptoRetencionSRI = this.conceptoRetencionSRIDao.obtenerListaCombo(null, false, filters);
/*  843: 942 */         for (ConceptoRetencionSRI conceptoRetencionSRI : listaConceptoRetencionSRI)
/*  844:     */         {
/*  845: 943 */           this.conceptoRetencionSRIDao.detach(conceptoRetencionSRI);
/*  846: 944 */           conceptoRetencionSRI.setIdConceptoRetencionSRI(0);
/*  847: 945 */           conceptoRetencionSRI.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  848: 946 */           conceptoRetencionSRI.setCuentaContable(null);
/*  849: 947 */           this.conceptoRetencionSRIDao.guardar(conceptoRetencionSRI);
/*  850:     */         }
/*  851:     */       }
/*  852:     */     }
/*  853:     */   }
/*  854:     */   
/*  855:     */   public void copiarCondicionPago(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  856:     */   {
/*  857: 961 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  858: 962 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.CONDICION_PAGO)) && (catalogos.isIndicadorCopiar()))
/*  859:     */       {
/*  860: 963 */         Map<String, String> filters = new HashMap();
/*  861: 964 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  862: 965 */         List<CondicionPago> listaCondicionPago = this.condicionPagoDao.obtenerListaCombo(null, false, filters);
/*  863: 966 */         for (CondicionPago condicionPago : listaCondicionPago)
/*  864:     */         {
/*  865: 967 */           this.condicionPagoDao.detach(condicionPago);
/*  866: 968 */           condicionPago.setIdCondicionPago(0);
/*  867: 969 */           condicionPago.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  868: 970 */           this.condicionPagoDao.guardar(condicionPago);
/*  869:     */         }
/*  870:     */       }
/*  871:     */     }
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void copiarUnidad(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  875:     */   {
/*  876: 984 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  877: 985 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.UNIDAD)) && (catalogos.isIndicadorCopiar()))
/*  878:     */       {
/*  879: 986 */         Map<String, String> filters = new HashMap();
/*  880: 987 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  881: 988 */         List<Unidad> listaUnidad = this.unidadDao.obtenerListaCombo(null, false, filters);
/*  882: 989 */         for (Unidad unidad : listaUnidad)
/*  883:     */         {
/*  884: 990 */           unidad = this.unidadDao.cargarDetalle(unidad.getIdUnidad());
/*  885: 991 */           this.unidadDao.detach(unidad);
/*  886: 992 */           unidad.setIdUnidad(0);
/*  887: 993 */           unidad.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  888: 994 */           unidad.getListaUnidadConversion().clear();
/*  889: 995 */           this.unidadDao.guardar(unidad);
/*  890:     */         }
/*  891:     */       }
/*  892:     */     }
/*  893:     */   }
/*  894:     */   
/*  895:     */   public void copiarTipoCuentaBancaria(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  896:     */   {
/*  897:1009 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  898:1010 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.TIPO_CUENTA_BANCARIA)) && (catalogos.isIndicadorCopiar()))
/*  899:     */       {
/*  900:1011 */         Map<String, String> filters = new HashMap();
/*  901:1012 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  902:1013 */         List<TipoCuentaBancaria> listaCuentaBancaria = this.tipoCuentaBancariaDao.obtenerListaCombo(null, false, filters);
/*  903:1014 */         for (TipoCuentaBancaria tipoCuentaBancaria : listaCuentaBancaria)
/*  904:     */         {
/*  905:1015 */           this.tipoCuentaBancariaDao.detach(tipoCuentaBancaria);
/*  906:1016 */           tipoCuentaBancaria.setIdTipoCuentaBancaria(0);
/*  907:1017 */           tipoCuentaBancaria.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  908:1018 */           this.tipoCuentaBancariaDao.guardar(tipoCuentaBancaria);
/*  909:     */         }
/*  910:     */       }
/*  911:     */     }
/*  912:     */   }
/*  913:     */   
/*  914:     */   public void copiarBodega(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  915:     */   {
/*  916:1032 */     this.hmBodega.clear();
/*  917:1033 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  918:1034 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.BODEGA)) && (catalogos.isIndicadorCopiar()))
/*  919:     */       {
/*  920:1035 */         Map<String, String> filters = new HashMap();
/*  921:1036 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  922:1037 */         List<Bodega> listaBodega = this.bodegaDao.obtenerListaCombo(null, false, filters);
/*  923:1038 */         List<TipoBodega> listaTipoBodega = this.tipoBodegaDao.obtenerListaCombo(null, false, filters);
/*  924:1039 */         for (TipoBodega tipoBodega : listaTipoBodega)
/*  925:     */         {
/*  926:1040 */           Integer idTipBodega = Integer.valueOf(tipoBodega.getIdTipoBodega());
/*  927:1041 */           this.tipoBodegaDao.detach(tipoBodega);
/*  928:1042 */           tipoBodega = (TipoBodega)copiarObjeto(this.tipoBodegaDao.buscarPorId(Integer.valueOf(tipoBodega.getId())), false);
/*  929:1043 */           tipoBodega.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  930:1044 */           this.tipoBodegaDao.guardar(tipoBodega);
/*  931:1045 */           this.hmTipoBodega.put(idTipBodega, tipoBodega);
/*  932:     */         }
/*  933:1048 */         for (Bodega bodega : listaBodega)
/*  934:     */         {
/*  935:1049 */           this.hmBodega.put(Integer.valueOf(bodega.getIdBodega()), bodega);
/*  936:1050 */           TipoBodega tipoBodega = (TipoBodega)this.hmTipoBodega.get(Integer.valueOf(bodega.getTipoBodega().getIdTipoBodega()));
/*  937:1051 */           this.bodegaDao.detach(bodega);
/*  938:1052 */           bodega = (Bodega)copiarObjeto(this.bodegaDao.buscarPorId(Integer.valueOf(bodega.getId())), false);
/*  939:1053 */           bodega.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  940:1054 */           bodega.setTipoBodega(tipoBodega);
/*  941:1055 */           this.bodegaDao.guardar(bodega);
/*  942:     */         }
/*  943:     */       }
/*  944:     */     }
/*  945:     */   }
/*  946:     */   
/*  947:     */   public void copiarZona(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  948:     */   {
/*  949:1069 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  950:1070 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.ZONA)) && (catalogos.isIndicadorCopiar()))
/*  951:     */       {
/*  952:1071 */         Map<String, String> filters = new HashMap();
/*  953:1072 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  954:1073 */         List<Zona> listaZona = this.zonaDao.obtenerListaCombo("nombre", true, filters);
/*  955:1074 */         for (Zona zona : listaZona)
/*  956:     */         {
/*  957:1075 */           this.zonaDao.detach(zona);
/*  958:1076 */           zona.setIdZona(0);
/*  959:1077 */           zona.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  960:1078 */           this.zonaDao.guardar(zona);
/*  961:     */         }
/*  962:     */       }
/*  963:     */     }
/*  964:     */   }
/*  965:     */   
/*  966:     */   public void copiarPais(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/*  967:     */   {
/*  968:1092 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/*  969:1093 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.PAIS)) && (catalogos.isIndicadorCopiar()))
/*  970:     */       {
/*  971:1094 */         Map<String, String> filters = new HashMap();
/*  972:1095 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/*  973:1096 */         List<Pais> listaPais = this.paisDao.obtenerListaCombo(null, true, filters);
/*  974:1097 */         for (localIterator2 = listaPais.iterator(); localIterator2.hasNext();)
/*  975:     */         {
/*  976:1097 */           pais = (Pais)localIterator2.next();
/*  977:1098 */           pais = this.paisDao.cargarDetalle(pais.getId());
/*  978:1099 */           List<Provincia> lisProvincias = pais.getListaProvincia();
/*  979:1100 */           this.paisDao.detach(pais);
/*  980:1101 */           pais = (Pais)copiarObjeto(this.paisDao.buscarPorId(Integer.valueOf(pais.getId())), false);
/*  981:1102 */           pais.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  982:1103 */           this.paisDao.guardar(pais);
/*  983:1104 */           for (localIterator3 = lisProvincias.iterator(); localIterator3.hasNext();)
/*  984:     */           {
/*  985:1104 */             provincia = (Provincia)localIterator3.next();
/*  986:1105 */             provincia = this.provinciaDao.cargarDetalle(provincia.getId());
/*  987:1106 */             List<Ciudad> listCiudades = provincia.getListaCiudad();
/*  988:1107 */             this.provinciaDao.detach(provincia);
/*  989:1108 */             provincia = (Provincia)copiarObjeto(this.provinciaDao.buscarPorId(Integer.valueOf(provincia.getId())), false);
/*  990:1109 */             provincia.setPais(pais);
/*  991:1110 */             provincia.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  992:1111 */             this.provinciaDao.guardar(provincia);
/*  993:1112 */             for (Ciudad ciudad : listCiudades)
/*  994:     */             {
/*  995:1113 */               this.ciudadDao.detach(ciudad);
/*  996:1114 */               ciudad = (Ciudad)copiarObjeto(this.ciudadDao.buscarPorId(Integer.valueOf(ciudad.getId())), false);
/*  997:1115 */               ciudad.setProvincia(provincia);
/*  998:1116 */               ciudad.setIdOrganizacion(organizacion.getIdOrganizacion());
/*  999:1117 */               this.ciudadDao.guardar(ciudad);
/* 1000:     */             }
/* 1001:     */           }
/* 1002:     */         }
/* 1003:     */       }
/* 1004:     */     }
/* 1005:     */     Iterator localIterator2;
/* 1006:     */     Pais pais;
/* 1007:     */     Iterator localIterator3;
/* 1008:     */     Provincia provincia;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public void copiarEstadoCivil(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1012:     */   {
/* 1013:1133 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1014:1134 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.ESTADO_CIVIL)) && (catalogos.isIndicadorCopiar()))
/* 1015:     */       {
/* 1016:1135 */         Map<String, String> filters = new HashMap();
/* 1017:1136 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1018:1137 */         List<EstadoCivil> listEstadoCivil = this.estadoCivilDao.obtenerListaCombo(null, true, filters);
/* 1019:1138 */         for (EstadoCivil estadoCivilCy : listEstadoCivil)
/* 1020:     */         {
/* 1021:1139 */           this.estadoCivilDao.detach(estadoCivilCy);
/* 1022:1140 */           estadoCivilCy = (EstadoCivil)copiarObjeto(this.estadoCivilDao.buscarPorId(Integer.valueOf(estadoCivilCy.getId())), false);
/* 1023:1141 */           estadoCivilCy.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1024:1142 */           this.estadoCivilDao.guardar(estadoCivilCy);
/* 1025:     */         }
/* 1026:     */       }
/* 1027:     */     }
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   private static Object copiarObjeto(Object objetoACopiar, boolean copiarListas)
/* 1031:     */   {
/* 1032:1157 */     Object clone = null;
/* 1033:     */     try
/* 1034:     */     {
/* 1035:1160 */       clone = objetoACopiar.getClass().newInstance();
/* 1036:     */     }
/* 1037:     */     catch (InstantiationException e)
/* 1038:     */     {
/* 1039:1162 */       e.printStackTrace();
/* 1040:     */     }
/* 1041:     */     catch (IllegalAccessException e)
/* 1042:     */     {
/* 1043:1164 */       e.printStackTrace();
/* 1044:     */     }
/* 1045:1166 */     if (clone != null) {
/* 1046:1167 */       for (Class obj = objetoACopiar.getClass(); !obj.equals(Object.class); obj = obj.getSuperclass())
/* 1047:     */       {
/* 1048:1168 */         Field[] fields = obj.getDeclaredFields();
/* 1049:1169 */         for (int i = 0; i < fields.length; i++)
/* 1050:     */         {
/* 1051:1170 */           fields[i].setAccessible(true);
/* 1052:     */           try
/* 1053:     */           {
/* 1054:1174 */             if ((!fields[i].isAnnotationPresent(Id.class)) && (!fields[i].getName().contains("serialVersionUID"))) {
/* 1055:1175 */               if (copiarListas) {
/* 1056:1176 */                 fields[i].set(clone, fields[i].get(objetoACopiar));
/* 1057:1178 */               } else if ((!fields[i].getType().getName().contains("List")) && (!fields[i].getName().contains("list"))) {
/* 1058:1179 */                 fields[i].set(clone, fields[i].get(objetoACopiar));
/* 1059:     */               }
/* 1060:     */             }
/* 1061:     */           }
/* 1062:     */           catch (IllegalArgumentException e)
/* 1063:     */           {
/* 1064:1183 */             e.printStackTrace();
/* 1065:     */           }
/* 1066:     */           catch (IllegalAccessException e)
/* 1067:     */           {
/* 1068:1185 */             e.printStackTrace();
/* 1069:     */           }
/* 1070:     */         }
/* 1071:     */       }
/* 1072:     */     }
/* 1073:1190 */     return clone;
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public void copiarListaPrecios(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1077:     */   {
/* 1078:1201 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1079:1202 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.LISTA_PRECIOS)) && (catalogos.isIndicadorCopiar()))
/* 1080:     */       {
/* 1081:1203 */         Map<String, String> filters = new HashMap();
/* 1082:1204 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1083:1205 */         List<ListaPrecios> listaPreciosLista = this.listaPreciosDao.obtenerListaCombo(null, false, filters);
/* 1084:1206 */         for (ListaPrecios listaPrecios : listaPreciosLista)
/* 1085:     */         {
/* 1086:1207 */           if (listaPrecios.getMoneda() != null) {
/* 1087:1208 */             listaPrecios.setMoneda((Moneda)this.hmMoneda.get(Integer.valueOf(listaPrecios.getMoneda().getIdMoneda())));
/* 1088:     */           }
/* 1089:1210 */           listaPrecios = this.listaPreciosDao.cargarDetalle(listaPrecios.getIdListaPrecios());
/* 1090:1211 */           this.listaPreciosDao.detach(listaPrecios);
/* 1091:1212 */           listaPrecios.setIdListaPrecios(0);
/* 1092:1213 */           listaPrecios.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1093:1214 */           listaPrecios.getVersionesListaPrecios().clear();
/* 1094:1215 */           this.listaPreciosDao.guardar(listaPrecios);
/* 1095:     */         }
/* 1096:     */       }
/* 1097:     */     }
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public void copiarMoneda(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1101:     */   {
/* 1102:1229 */     this.hmMoneda.clear();
/* 1103:1230 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1104:1231 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.MONEDA)) && (catalogos.isIndicadorCopiar()))
/* 1105:     */       {
/* 1106:1232 */         Map<String, String> filters = new HashMap();
/* 1107:1233 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1108:1234 */         List<Moneda> listaMoneda = this.monedaDao.obtenerListaCombo(null, false, filters);
/* 1109:1235 */         for (Moneda moneda : listaMoneda)
/* 1110:     */         {
/* 1111:1236 */           this.hmMoneda.put(Integer.valueOf(moneda.getIdMoneda()), moneda);
/* 1112:1237 */           this.monedaDao.detach(moneda);
/* 1113:1238 */           moneda.setIdMoneda(0);
/* 1114:1239 */           moneda.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1115:1240 */           this.monedaDao.guardar(moneda);
/* 1116:     */         }
/* 1117:     */       }
/* 1118:     */     }
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public void copiarImpuestoRangoImpuesto(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1122:     */   {
/* 1123:1254 */     this.hmImpuesto.clear();
/* 1124:1255 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1125:1256 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.IMPUESTO)) && (catalogos.isIndicadorCopiar()))
/* 1126:     */       {
/* 1127:1257 */         Map<String, String> filters = new HashMap();
/* 1128:1258 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1129:1259 */         List<Impuesto> listaImpuesto = this.impuestoDao.obtenerListaCombo(null, false, filters);
/* 1130:1261 */         for (Impuesto impuesto : listaImpuesto)
/* 1131:     */         {
/* 1132:1262 */           this.hmImpuesto.put(Integer.valueOf(impuesto.getIdImpuesto()), impuesto);
/* 1133:1263 */           Impuesto impuestoAux = this.impuestoDao.cargarDetalle(impuesto.getIdImpuesto());
/* 1134:1264 */           this.impuestoDao.detach(impuestoAux);
/* 1135:1265 */           impuestoAux.setIdImpuesto(0);
/* 1136:1266 */           impuestoAux.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1137:1267 */           impuestoAux.setCuentaContableCompra(null);
/* 1138:1268 */           impuestoAux.setCuentaContableRedondeo(null);
/* 1139:1269 */           impuestoAux.setCuentaContableVenta(null);
/* 1140:1270 */           impuestoAux.getListaCategoriaImpuesto().clear();
/* 1141:1271 */           this.impuestoDao.guardar(impuestoAux);
/* 1142:1273 */           for (RangoImpuesto rangoImpuesto : impuestoAux.getListaRangoImpuesto())
/* 1143:     */           {
/* 1144:1274 */             this.rangoImpuestoDao.detach(rangoImpuesto);
/* 1145:1275 */             rangoImpuesto.setIdRangoImpuesto(0);
/* 1146:1276 */             rangoImpuesto.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1147:1277 */             this.rangoImpuestoDao.guardar(rangoImpuesto);
/* 1148:     */           }
/* 1149:     */         }
/* 1150:     */       }
/* 1151:     */     }
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public void copiarCategoriaImpuesto(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1155:     */   {
/* 1156:1293 */     this.hmCategoriaImpuesto.clear();
/* 1157:1294 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1158:1295 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.IMPUESTO)) && (catalogos.isIndicadorCopiar()))
/* 1159:     */       {
/* 1160:1296 */         Map<String, String> filters = new HashMap();
/* 1161:1297 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1162:1298 */         List<CategoriaImpuesto> listaCategoriaImpuesto = this.categoriaImpuestoDao.obtenerListaCombo(null, false, filters);
/* 1163:1300 */         for (CategoriaImpuesto categoriaImpuesto : listaCategoriaImpuesto)
/* 1164:     */         {
/* 1165:1301 */           this.hmCategoriaImpuesto.put(Integer.valueOf(categoriaImpuesto.getIdCategoriaImpuesto()), categoriaImpuesto);
/* 1166:1302 */           CategoriaImpuesto categoriaImpuestoAux = this.categoriaImpuestoDao.cargarDetalle(categoriaImpuesto.getIdCategoriaImpuesto());
/* 1167:1303 */           this.categoriaImpuestoDao.detach(categoriaImpuestoAux);
/* 1168:1304 */           categoriaImpuestoAux.setIdCategoriaImpuesto(0);
/* 1169:1305 */           categoriaImpuestoAux.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1170:1306 */           categoriaImpuestoAux.getListaImpuesto().clear();
/* 1171:1307 */           this.categoriaImpuestoDao.guardar(categoriaImpuestoAux);
/* 1172:     */         }
/* 1173:1310 */         for (it = this.hmImpuesto.keySet().iterator(); ((Iterator)it).hasNext();)
/* 1174:     */         {
/* 1175:1311 */           Integer idImpuestoOld = (Integer)((Iterator)it).next();
/* 1176:     */           
/* 1177:1313 */           Impuesto impuesto = (Impuesto)this.hmImpuesto.get(idImpuestoOld);
/* 1178:     */           
/* 1179:1315 */           Impuesto impuestoOld = this.impuestoDao.cargarDetalle(idImpuestoOld.intValue());
/* 1180:     */           
/* 1181:1317 */           List<CategoriaImpuesto> listaCategoriaImpuestos = new ArrayList();
/* 1182:1318 */           for (CategoriaImpuesto categoriaImpuestoOld : impuestoOld.getListaCategoriaImpuesto())
/* 1183:     */           {
/* 1184:1319 */             CategoriaImpuesto categoriaImpuesto = (CategoriaImpuesto)this.hmCategoriaImpuesto.get(Integer.valueOf(categoriaImpuestoOld.getIdCategoriaImpuesto()));
/* 1185:1320 */             listaCategoriaImpuestos.add(categoriaImpuesto);
/* 1186:     */           }
/* 1187:1322 */           impuesto.setListaCategoriaImpuesto(null);
/* 1188:1323 */           this.impuestoDao.guardar(impuesto);
/* 1189:1324 */           impuesto.setListaCategoriaImpuesto(listaCategoriaImpuestos);
/* 1190:1325 */           this.impuestoDao.guardar(impuesto);
/* 1191:     */         }
/* 1192:     */       }
/* 1193:     */     }
/* 1194:     */     Object it;
/* 1195:     */   }
/* 1196:     */   
/* 1197:     */   @Deprecated
/* 1198:     */   public void copiarAccion(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1199:     */   {
/* 1200:1342 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1201:1343 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.ACCION)) && (catalogos.isIndicadorCopiar()))
/* 1202:     */       {
/* 1203:1344 */         Map<String, String> filters = new HashMap();
/* 1204:1345 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1205:1346 */         List<EntidadAccion> listaAccion = this.accionDao.obtenerListaCombo(null, false, filters);
/* 1206:1347 */         for (EntidadAccion accion : listaAccion)
/* 1207:     */         {
/* 1208:1348 */           this.accionDao.detach(accion);
/* 1209:1349 */           accion.setIdAccion(0);
/* 1210:1350 */           accion.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1211:1351 */           this.accionDao.guardar(accion);
/* 1212:     */         }
/* 1213:     */       }
/* 1214:     */     }
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public void copiarCategoriaEmpresa(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1218:     */   {
/* 1219:1365 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1220:1366 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.CATEGORIA_EMPRESA)) && (catalogos.isIndicadorCopiar()))
/* 1221:     */       {
/* 1222:1367 */         Map<String, String> filters = new HashMap();
/* 1223:1368 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1224:1369 */         List<CategoriaEmpresa> listaCategoriaEmpresa = this.categoriaEmpresaDao.obtenerListaCombo(null, false, filters);
/* 1225:1370 */         for (CategoriaEmpresa categoriaEmpresa : listaCategoriaEmpresa)
/* 1226:     */         {
/* 1227:1371 */           this.categoriaEmpresaDao.detach(categoriaEmpresa);
/* 1228:1372 */           categoriaEmpresa.setIdCategoriaEmpresa(0);
/* 1229:1373 */           categoriaEmpresa.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1230:1374 */           categoriaEmpresa.setCuentaContableCliente(null);
/* 1231:1375 */           categoriaEmpresa.setCuentaContableProveedor(null);
/* 1232:1376 */           categoriaEmpresa.setCuentaContableAnticipoCliente(null);
/* 1233:1377 */           categoriaEmpresa.setCuentaContableAnticipoClienteNotaCredito(null);
/* 1234:1378 */           categoriaEmpresa.setCuentaContableAnticipoProveedor(null);
/* 1235:1379 */           categoriaEmpresa.setCuentaContableAnticipoProveedorNotaCredito(null);
/* 1236:1380 */           categoriaEmpresa.setCuentaContableCliente(null);
/* 1237:1381 */           categoriaEmpresa.setCuentaContableProveedor(null);
/* 1238:1382 */           categoriaEmpresa.setCuentaContableSueldoPorPagar(null);
/* 1239:1383 */           categoriaEmpresa.setCuentaContableIvaPresuntivo(null);
/* 1240:1384 */           categoriaEmpresa.setCuentaContable2X1000(null);
/* 1241:1385 */           categoriaEmpresa.setCuentaContable3X1000(null);
/* 1242:1386 */           this.categoriaEmpresaDao.guardar(categoriaEmpresa);
/* 1243:     */         }
/* 1244:     */       }
/* 1245:     */     }
/* 1246:     */   }
/* 1247:     */   
/* 1248:     */   public void copiarSubcategoriaProducto(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1249:     */   {
/* 1250:1400 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1251:1401 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.SUB_CATEGORIA_PRODUCTO)) && (catalogos.isIndicadorCopiar()))
/* 1252:     */       {
/* 1253:1402 */         Map<String, String> filters = new HashMap();
/* 1254:1403 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1255:1404 */         List<SubcategoriaProducto> listaSubcategoriaProducto = this.subcategoriaProductoDao.obtenerListaCombo(null, false, filters);
/* 1256:1405 */         mapaCategoriaProducto = new HashMap();
/* 1257:1406 */         for (SubcategoriaProducto subcategoriaProducto : listaSubcategoriaProducto)
/* 1258:     */         {
/* 1259:1407 */           subcategoriaProducto = this.subcategoriaProductoDao.cargarDetalle(subcategoriaProducto.getId());
/* 1260:1408 */           CategoriaProducto categoriaProducto = null;
/* 1261:1409 */           int idCategoria = subcategoriaProducto.getCategoriaProducto().getId();
/* 1262:1411 */           if (mapaCategoriaProducto.containsKey(Integer.valueOf(idCategoria)))
/* 1263:     */           {
/* 1264:1412 */             categoriaProducto = (CategoriaProducto)mapaCategoriaProducto.get(Integer.valueOf(idCategoria));
/* 1265:     */           }
/* 1266:     */           else
/* 1267:     */           {
/* 1268:1414 */             categoriaProducto = new CategoriaProducto();
/* 1269:1415 */             categoriaProducto.setActivo(subcategoriaProducto.getCategoriaProducto().getActivo());
/* 1270:1416 */             categoriaProducto.setCodigo(subcategoriaProducto.getCategoriaProducto().getCodigo());
/* 1271:1417 */             categoriaProducto.setDescripcion(subcategoriaProducto.getCategoriaProducto().getDescripcion());
/* 1272:1418 */             categoriaProducto.setNombre(subcategoriaProducto.getCategoriaProducto().getNombre());
/* 1273:1419 */             categoriaProducto.setPredeterminado(subcategoriaProducto.getCategoriaProducto().getPredeterminado());
/* 1274:1420 */             categoriaProducto.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1275:1421 */             this.categoriaProductoDao.guardar(categoriaProducto);
/* 1276:     */             
/* 1277:1423 */             mapaCategoriaProducto.put(Integer.valueOf(idCategoria), categoriaProducto);
/* 1278:     */           }
/* 1279:1425 */           this.subcategoriaProductoDao.detach(subcategoriaProducto);
/* 1280:1426 */           subcategoriaProducto.setCategoriaProducto(categoriaProducto);
/* 1281:1427 */           subcategoriaProducto.setIdSubcategoriaProducto(0);
/* 1282:1428 */           subcategoriaProducto.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1283:1429 */           this.subcategoriaProductoDao.guardar(subcategoriaProducto);
/* 1284:     */         }
/* 1285:     */       }
/* 1286:     */     }
/* 1287:     */     Map<Integer, CategoriaProducto> mapaCategoriaProducto;
/* 1288:     */   }
/* 1289:     */   
/* 1290:     */   public void copiarBancos(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1291:     */   {
/* 1292:1443 */     this.hmBanco.clear();
/* 1293:1444 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1294:1445 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.BANCOS)) && (catalogos.isIndicadorCopiar()))
/* 1295:     */       {
/* 1296:1446 */         Map<String, String> filters = new HashMap();
/* 1297:1447 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1298:1448 */         List<Banco> listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, null, false, filters);
/* 1299:1449 */         for (Banco banco : listaBanco)
/* 1300:     */         {
/* 1301:1450 */           this.hmBanco.put(Integer.valueOf(banco.getIdBanco()), banco);
/* 1302:1451 */           this.bancoDao.detach(banco);
/* 1303:1452 */           banco.setIdBanco(0);
/* 1304:1453 */           banco.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1305:     */           try
/* 1306:     */           {
/* 1307:1455 */             this.servicioBanco.guardar(banco);
/* 1308:     */           }
/* 1309:     */           catch (AS2Exception e)
/* 1310:     */           {
/* 1311:1457 */             JsfUtil.addErrorMessage(e, "");
/* 1312:     */           }
/* 1313:     */         }
/* 1314:     */       }
/* 1315:     */     }
/* 1316:     */   }
/* 1317:     */   
/* 1318:     */   public void copiarParroquias(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1319:     */     throws AS2Exception
/* 1320:     */   {
/* 1321:1474 */     this.hmParroquia.clear();
/* 1322:1475 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1323:1476 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.BANCOS)) && (catalogos.isIndicadorCopiar()))
/* 1324:     */       {
/* 1325:1477 */         Map<String, String> filters = new HashMap();
/* 1326:1478 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1327:1479 */         List<Parroquia> listaParroquia = this.servicioParroquia.obtenerListaCombo(Parroquia.class, null, false, filters);
/* 1328:1481 */         for (Parroquia parroquia : listaParroquia)
/* 1329:     */         {
/* 1330:1482 */           this.hmParroquia.put(Integer.valueOf(parroquia.getIdParroquia()), parroquia);
/* 1331:1483 */           Parroquia parroquiaNueva = (Parroquia)copiarObjeto(parroquia, false);
/* 1332:1484 */           this.parroquiaDao.detach(parroquia);
/* 1333:1485 */           if ((parroquiaNueva.getCodigo() != null) && (parroquiaNueva.getCodigo().length() < 2)) {
/* 1334:1486 */             throw new AS2Exception("com.asinfo.as2.configuracionbase.servicio.impl.ServicioOrganizacionBase.ERROR_LONGITUD_CODIGO", new String[] { "la Parroquia", "codigo" });
/* 1335:     */           }
/* 1336:1487 */           parroquiaNueva.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1337:     */           try
/* 1338:     */           {
/* 1339:1489 */             this.servicioParroquia.guardar(parroquiaNueva);
/* 1340:     */           }
/* 1341:     */           catch (AS2Exception e)
/* 1342:     */           {
/* 1343:1491 */             JsfUtil.addErrorMessage(e, "");
/* 1344:     */           }
/* 1345:     */         }
/* 1346:     */       }
/* 1347:     */     }
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   public void copiarHorarioHorasExtras(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1351:     */   {
/* 1352:1506 */     this.hmHoraExtra.clear();
/* 1353:1507 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1354:1508 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.HORAS_EXTRAS)) && (catalogos.isIndicadorCopiar()))
/* 1355:     */       {
/* 1356:1509 */         Map<String, String> filters = new HashMap();
/* 1357:1510 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1358:1511 */         List<HoraExtra> listaHoraExtra = this.servicioHoraExtra.obtenerListaCombo(HoraExtra.class, null, false, filters);
/* 1359:1512 */         for (HoraExtra horaExtra : listaHoraExtra)
/* 1360:     */         {
/* 1361:1513 */           this.hmHoraExtra.put(Integer.valueOf(horaExtra.getIdHoraExtra()), horaExtra);
/* 1362:1514 */           this.horaExtraDao.detach(horaExtra);
/* 1363:1515 */           horaExtra.setIdHoraExtra(0);
/* 1364:1516 */           horaExtra.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1365:     */           try
/* 1366:     */           {
/* 1367:1518 */             this.servicioHoraExtra.guardar(horaExtra);
/* 1368:     */           }
/* 1369:     */           catch (AS2Exception e)
/* 1370:     */           {
/* 1371:1520 */             JsfUtil.addErrorMessage(e, "");
/* 1372:     */           }
/* 1373:     */         }
/* 1374:     */       }
/* 1375:     */     }
/* 1376:     */   }
/* 1377:     */   
/* 1378:     */   public void copiarReportes(Organizacion organizacion, Organizacion organizacionACopiar, List<CatalogosACopiar> listaCatalogosACopiar)
/* 1379:     */   {
/* 1380:1528 */     this.hmReportes.clear();
/* 1381:1529 */     for (CatalogosACopiar catalogos : listaCatalogosACopiar) {
/* 1382:1530 */       if ((catalogos.getCatalogosACopiar().equals(CatalogosACopiarEnum.REPORTEADOR)) && (catalogos.isIndicadorCopiar()))
/* 1383:     */       {
/* 1384:1532 */         Map<String, String> filters = new HashMap();
/* 1385:1533 */         filters.put("idOrganizacion", String.valueOf(organizacionACopiar.getIdOrganizacion()));
/* 1386:1534 */         List<Reporteador> listaReportes = this.reporteadorDao.obtenerListaCombo(null, true, filters);
/* 1387:1536 */         for (Reporteador reporteador : listaReportes)
/* 1388:     */         {
/* 1389:1537 */           this.hmReportes.put(Integer.valueOf(reporteador.getIdReporteador()), reporteador);
/* 1390:1538 */           Reporteador reporteAux = this.reporteadorDao.cargarDetalle(reporteador.getIdReporteador());
/* 1391:1539 */           this.reporteadorDao.detach(reporteAux);
/* 1392:1540 */           reporteAux.setIdReporteador(0);
/* 1393:1541 */           reporteAux.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1394:     */           
/* 1395:1543 */           this.reporteadorDao.guardar(reporteAux);
/* 1396:1545 */           for (DetalleReporteadorVariable drv : reporteAux.getListaDetalleReporteadorVariable())
/* 1397:     */           {
/* 1398:1546 */             this.detalleReporteadorVariableDao.detach(drv);
/* 1399:1547 */             drv.setIdDetalleReporteadorVariable(0);
/* 1400:1548 */             drv.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1401:1549 */             drv.setReporteador(reporteAux);
/* 1402:1550 */             this.detalleReporteadorVariableDao.guardar(drv);
/* 1403:     */           }
/* 1404:1555 */           guardarDetallesReporteador(reporteador, null, organizacion);
/* 1405:     */         }
/* 1406:     */       }
/* 1407:     */     }
/* 1408:     */   }
/* 1409:     */   
/* 1410:     */   private void guardarDetallesReporteador(Reporteador reporteador, DetalleReporteador detalleReporteadorPadre, Organizacion organizacion)
/* 1411:     */   {
/* 1412:1565 */     List<DetalleReporteador> listaDetalleReporteador = null;
/* 1413:1566 */     if (detalleReporteadorPadre == null) {
/* 1414:1567 */       listaDetalleReporteador = reporteador.getListaDetalleReporteador();
/* 1415:     */     } else {
/* 1416:1569 */       listaDetalleReporteador = detalleReporteadorPadre.getListaDetalleReporteadorHijo();
/* 1417:     */     }
/* 1418:1572 */     if (detalleReporteadorPadre != null)
/* 1419:     */     {
/* 1420:1573 */       this.detalleReporteadorDao.detach(detalleReporteadorPadre);
/* 1421:1574 */       detalleReporteadorPadre.setIdDetalleReporteador(0);
/* 1422:1575 */       detalleReporteadorPadre.setIdOrganizacion(organizacion.getIdOrganizacion());
/* 1423:     */       
/* 1424:1577 */       this.detalleReporteadorDao.guardar(detalleReporteadorPadre);
/* 1425:     */     }
/* 1426:1580 */     for (DetalleReporteador detalleReporteador : listaDetalleReporteador) {
/* 1427:1581 */       guardarDetallesReporteador(reporteador, detalleReporteador, organizacion);
/* 1428:     */     }
/* 1429:     */   }
/* 1430:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioOrganizacionBase
 * JD-Core Version:    0.7.0.1
 */