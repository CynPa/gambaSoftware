/*    1:     */ package com.asinfo.as2.produccion.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.dao.GenericoDao;
/*    4:     */ import com.asinfo.as2.dao.InventarioProductoDao;
/*    5:     */ import com.asinfo.as2.dao.MovimientoInventarioDao;
/*    6:     */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*    7:     */ import com.asinfo.as2.dao.produccion.OrdenSalidaMaterialDao;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   10:     */ import com.asinfo.as2.entities.Bodega;
/*   11:     */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   12:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*   13:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterialOrdenFabricacion;
/*   14:     */ import com.asinfo.as2.entities.Documento;
/*   15:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   16:     */ import com.asinfo.as2.entities.LecturaBalanza;
/*   17:     */ import com.asinfo.as2.entities.Lote;
/*   18:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   19:     */ import com.asinfo.as2.entities.OrdenFabricacionOrdenSalidaMaterial;
/*   20:     */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   21:     */ import com.asinfo.as2.entities.Organizacion;
/*   22:     */ import com.asinfo.as2.entities.Producto;
/*   23:     */ import com.asinfo.as2.entities.ProductoMaterial;
/*   24:     */ import com.asinfo.as2.entities.Sucursal;
/*   25:     */ import com.asinfo.as2.entities.Unidad;
/*   26:     */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   27:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   28:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   29:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   30:     */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*   31:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   32:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   33:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   34:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   35:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   36:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*   37:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   38:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*   39:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*   40:     */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*   41:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioDesechoMaterial;
/*   42:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*   43:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*   44:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   45:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   46:     */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*   47:     */ import com.asinfo.as2.util.AppUtil;
/*   48:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   49:     */ import java.math.BigDecimal;
/*   50:     */ import java.math.RoundingMode;
/*   51:     */ import java.util.ArrayList;
/*   52:     */ import java.util.Date;
/*   53:     */ import java.util.HashMap;
/*   54:     */ import java.util.HashSet;
/*   55:     */ import java.util.Iterator;
/*   56:     */ import java.util.List;
/*   57:     */ import java.util.Map;
/*   58:     */ import java.util.Set;
/*   59:     */ import javax.ejb.EJB;
/*   60:     */ import javax.ejb.Lock;
/*   61:     */ import javax.ejb.LockType;
/*   62:     */ import javax.ejb.SessionContext;
/*   63:     */ import javax.ejb.Stateless;
/*   64:     */ import javax.ejb.TransactionAttribute;
/*   65:     */ import javax.ejb.TransactionAttributeType;
/*   66:     */ import javax.ejb.TransactionManagement;
/*   67:     */ import javax.ejb.TransactionManagementType;
/*   68:     */ 
/*   69:     */ @Stateless
/*   70:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*   71:     */ public class ServicioOrdenSalidaMaterialImpl
/*   72:     */   extends AbstractServicioAS2
/*   73:     */   implements ServicioOrdenSalidaMaterial
/*   74:     */ {
/*   75:     */   private static final long serialVersionUID = 1L;
/*   76:     */   @EJB
/*   77:     */   private OrdenSalidaMaterialDao ordenSalidaMaterialDao;
/*   78:     */   @EJB
/*   79:     */   private GenericoDao<DetalleOrdenSalidaMaterial> detalleOrdenSalidaMaterialDao;
/*   80:     */   @EJB
/*   81:     */   private GenericoDao<LecturaBalanza> lecturaBalanzaDao;
/*   82:     */   @EJB
/*   83:     */   private ServicioPeriodo servicioPeriodo;
/*   84:     */   @EJB
/*   85:     */   private ServicioSecuencia servicioSecuencia;
/*   86:     */   @EJB
/*   87:     */   private ServicioUnidadConversion servicioUnidadConversion;
/*   88:     */   @EJB
/*   89:     */   private ServicioDocumento servicioDocumento;
/*   90:     */   @EJB
/*   91:     */   private ServicioMovimientoInventario servicioMovimientoInventario;
/*   92:     */   @EJB
/*   93:     */   private GenericoDao<DetalleOrdenSalidaMaterialOrdenFabricacion> detalleOrdenSalidaMaterialOrdenFabricacionDao;
/*   94:     */   @EJB
/*   95:     */   private ServicioProducto servicioProducto;
/*   96:     */   @EJB
/*   97:     */   private MovimientoInventarioDao movimientoInventarioDao;
/*   98:     */   @EJB
/*   99:     */   private GenericoDao<OrdenFabricacionOrdenSalidaMaterial> ordenFabricacionOrdenSalidaMaterialDao;
/*  100:     */   @EJB
/*  101:     */   private OrdenFabricacionDao ordenFabricacionDao;
/*  102:     */   @EJB
/*  103:     */   private ServicioDesechoMaterial servicioDesechoMaterial;
/*  104:     */   @EJB
/*  105:     */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*  106:     */   @EJB
/*  107:     */   private transient ServicioUsuario servicioUsuario;
/*  108:     */   @EJB
/*  109:     */   private transient ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  110:     */   @EJB
/*  111:     */   private InventarioProductoDao inventarioProductoDao;
/*  112:     */   @EJB
/*  113:     */   private ServicioVerificadorInventario servicioVerificadorInventario;
/*  114:     */   @EJB
/*  115:     */   private GenericoDao<DetalleMovimientoInventario> detalleMovimientoInventarioDao;
/*  116:     */   
/*  117:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  118:     */   public void guardar(OrdenSalidaMaterial ordenSalidaMaterial)
/*  119:     */     throws ExcepcionAS2, AS2Exception
/*  120:     */   {
/*  121:     */     try
/*  122:     */     {
/*  123: 146 */       List<DetalleOrdenSalidaMaterial> listaDetalleOrdenSalidaMaterialEliminados = new ArrayList();
/*  124: 147 */       List<DetalleOrdenSalidaMaterialOrdenFabricacion> listaDetalleOrdenSalidaMaterialOrdenFabriicacionEliminados = new ArrayList();
/*  125: 148 */       boolean nuevo = ordenSalidaMaterial.getId() == 0;
/*  126: 149 */       if ((nuevo) && (ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial() != null) && 
/*  127: 150 */         (ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().size() == 0)) {
/*  128: 151 */         throw new ExcepcionAS2("msg_error_detalles_vacios");
/*  129:     */       }
/*  130: 154 */       validar(ordenSalidaMaterial);
/*  131: 155 */       cargarSecuencia(ordenSalidaMaterial);
/*  132: 156 */       setearDescripcion(ordenSalidaMaterial);
/*  133: 157 */       repartirOrdenesFabricacionPorDetalleCicloLargo(ordenSalidaMaterial);
/*  134: 158 */       if (nuevo) {
/*  135: 159 */         this.ordenSalidaMaterialDao.guardar(ordenSalidaMaterial);
/*  136:     */       }
/*  137: 162 */       for (DetalleOrdenSalidaMaterial dosm : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial())
/*  138:     */       {
/*  139: 163 */         boolean nuevoDetalle = dosm.getId() == 0;
/*  140: 164 */         if (nuevoDetalle) {
/*  141: 165 */           this.detalleOrdenSalidaMaterialDao.guardar(dosm);
/*  142:     */         }
/*  143: 167 */         for (LecturaBalanza lb : dosm.getListaLecturaBalanza()) {
/*  144: 168 */           this.lecturaBalanzaDao.guardar(lb);
/*  145:     */         }
/*  146: 171 */         for (DetalleOrdenSalidaMaterialOrdenFabricacion dosmof : dosm.getListaDetalleOrdenSalidaMaterialOrdenFabricacion())
/*  147:     */         {
/*  148: 173 */           if (dosmof.isEliminado())
/*  149:     */           {
/*  150: 175 */             List<LecturaBalanza> listaLecturaBalanza = this.ordenFabricacionDao.buscarListaLecturaBalanzaDetalleOrdenSalidaMaterialOrdenFabricacion(dosmof);
/*  151: 176 */             for (LecturaBalanza lecturaBalanza : listaLecturaBalanza) {
/*  152: 177 */               this.servicioDesechoMaterial.eliminarPesoDesecho(lecturaBalanza);
/*  153:     */             }
/*  154: 179 */             if ((dosm.getCantidadDesecho() != null) && (dosmof.getCantidadDesecho() != null)) {
/*  155: 180 */               dosm.setCantidadDesecho(dosm.getCantidadDesecho().subtract(dosmof.getCantidadDesecho()));
/*  156:     */             }
/*  157: 182 */             listaDetalleOrdenSalidaMaterialOrdenFabriicacionEliminados.add(dosmof);
/*  158:     */           }
/*  159: 184 */           this.detalleOrdenSalidaMaterialOrdenFabricacionDao.guardar(dosmof);
/*  160:     */         }
/*  161: 187 */         if (!nuevoDetalle)
/*  162:     */         {
/*  163: 188 */           if (dosm.isEliminado())
/*  164:     */           {
/*  165: 189 */             dosm.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().removeAll(listaDetalleOrdenSalidaMaterialOrdenFabriicacionEliminados);
/*  166: 190 */             listaDetalleOrdenSalidaMaterialEliminados.add(dosm);
/*  167:     */           }
/*  168: 192 */           this.detalleOrdenSalidaMaterialDao.guardar(dosm);
/*  169:     */         }
/*  170:     */       }
/*  171: 196 */       if (TipoCicloProduccionEnum.CICLO_LARGO.equals(ordenSalidaMaterial.getTipoCicloProduccionEnum())) {
/*  172: 197 */         for (OrdenFabricacionOrdenSalidaMaterial detalle : ordenSalidaMaterial.getListaOrdenFabricacionOrdenSalidaMaterial()) {
/*  173: 198 */           this.ordenFabricacionOrdenSalidaMaterialDao.guardar(detalle);
/*  174:     */         }
/*  175:     */       }
/*  176: 201 */       if (!nuevo)
/*  177:     */       {
/*  178: 202 */         if (listaDetalleOrdenSalidaMaterialEliminados.size() > 0) {
/*  179: 203 */           ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().removeAll(listaDetalleOrdenSalidaMaterialEliminados);
/*  180:     */         }
/*  181: 205 */         this.ordenSalidaMaterialDao.guardar(ordenSalidaMaterial);
/*  182:     */       }
/*  183:     */     }
/*  184:     */     catch (AS2Exception e)
/*  185:     */     {
/*  186: 208 */       this.context.setRollbackOnly();
/*  187: 209 */       throw e;
/*  188:     */     }
/*  189:     */     catch (ExcepcionAS2 e)
/*  190:     */     {
/*  191: 211 */       this.context.setRollbackOnly();
/*  192: 212 */       throw e;
/*  193:     */     }
/*  194:     */     catch (Exception e)
/*  195:     */     {
/*  196: 214 */       this.context.setRollbackOnly();
/*  197: 215 */       e.printStackTrace();
/*  198: 216 */       throw new ExcepcionAS2(e);
/*  199:     */     }
/*  200:     */   }
/*  201:     */   
/*  202:     */   private void repartirOrdenesFabricacionPorDetalleCicloLargo(OrdenSalidaMaterial ordenSalidaMaterial)
/*  203:     */   {
/*  204:     */     Map<String, DetalleOrdenSalidaMaterialOrdenFabricacion> mapaDetalleOrdenSalidaMaterialOrdenFabricacion;
/*  205: 221 */     if (ordenSalidaMaterial.getTipoCicloProduccionEnum().equals(TipoCicloProduccionEnum.CICLO_LARGO))
/*  206:     */     {
/*  207: 223 */       mapaDetalleOrdenSalidaMaterialOrdenFabricacion = new HashMap();
/*  208: 224 */       for (DetalleOrdenSalidaMaterial detalleOSM : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/*  209: 225 */         if (!detalleOSM.isEliminado()) {
/*  210: 226 */           for (DetalleOrdenSalidaMaterialOrdenFabricacion detalleOSMOF : detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion())
/*  211:     */           {
/*  212: 227 */             detalleOSMOF.setEliminado(true);
/*  213: 228 */             String key = detalleOSMOF.getOrdenFabricacion().getId() + "~" + detalleOSMOF.getDetalleOrdenSalidaMaterial().getId();
/*  214: 229 */             mapaDetalleOrdenSalidaMaterialOrdenFabricacion.put(key, detalleOSMOF);
/*  215:     */           }
/*  216:     */         }
/*  217:     */       }
/*  218: 235 */       for (??? = ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().iterator(); ???.hasNext();)
/*  219:     */       {
/*  220: 235 */         detalleOSM = (DetalleOrdenSalidaMaterial)???.next();
/*  221: 236 */         if (!detalleOSM.isEliminado()) {
/*  222: 237 */           for (OrdenFabricacionOrdenSalidaMaterial ofosm : ordenSalidaMaterial.getListaOrdenFabricacionOrdenSalidaMaterial())
/*  223:     */           {
/*  224: 238 */             String key = ofosm.getOrdenFabricacion().getId() + "~" + detalleOSM.getId();
/*  225: 239 */             DetalleOrdenSalidaMaterialOrdenFabricacion detalleOSMOF = (DetalleOrdenSalidaMaterialOrdenFabricacion)mapaDetalleOrdenSalidaMaterialOrdenFabricacion.get(key);
/*  226: 240 */             if (detalleOSMOF == null)
/*  227:     */             {
/*  228: 241 */               detalleOSMOF = new DetalleOrdenSalidaMaterialOrdenFabricacion();
/*  229: 242 */               detalleOSMOF.setIdOrganizacion(ordenSalidaMaterial.getIdOrganizacion());
/*  230: 243 */               detalleOSMOF.setIdSucursal(detalleOSM.getIdSucursal());
/*  231: 244 */               detalleOSMOF.setDetalleOrdenSalidaMaterial(detalleOSM);
/*  232: 245 */               detalleOSMOF.setOrdenFabricacion(ofosm.getOrdenFabricacion());
/*  233: 246 */               detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().add(detalleOSMOF);
/*  234:     */             }
/*  235: 248 */             detalleOSMOF.setCantidad(ofosm.getOrdenFabricacion().getCantidad());
/*  236: 249 */             detalleOSMOF.setEliminado(false);
/*  237: 250 */             mapaDetalleOrdenSalidaMaterialOrdenFabricacion.put(key, detalleOSMOF);
/*  238:     */           }
/*  239:     */         }
/*  240:     */       }
/*  241:     */     }
/*  242:     */     DetalleOrdenSalidaMaterial detalleOSM;
/*  243:     */   }
/*  244:     */   
/*  245:     */   public void eliminar(OrdenSalidaMaterial ordenSalidaMaterial)
/*  246:     */   {
/*  247: 264 */     this.ordenSalidaMaterialDao.eliminar(ordenSalidaMaterial);
/*  248:     */   }
/*  249:     */   
/*  250:     */   public OrdenSalidaMaterial buscarPorId(int idOrdenSalidaMaterial)
/*  251:     */   {
/*  252: 275 */     return (OrdenSalidaMaterial)this.ordenSalidaMaterialDao.buscarPorId(Integer.valueOf(idOrdenSalidaMaterial));
/*  253:     */   }
/*  254:     */   
/*  255:     */   public List<OrdenSalidaMaterial> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  256:     */   {
/*  257: 286 */     return this.ordenSalidaMaterialDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  258:     */   }
/*  259:     */   
/*  260:     */   public List<OrdenSalidaMaterial> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  261:     */   {
/*  262: 296 */     return this.ordenSalidaMaterialDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  263:     */   }
/*  264:     */   
/*  265:     */   public int contarPorCriterio(Map<String, String> filters)
/*  266:     */   {
/*  267: 306 */     return this.ordenSalidaMaterialDao.contarPorCriterio(filters);
/*  268:     */   }
/*  269:     */   
/*  270:     */   public OrdenSalidaMaterial cargarDetalle(int idOrdenSalidaMaterial)
/*  271:     */   {
/*  272: 316 */     return cargarDetalle(idOrdenSalidaMaterial, null);
/*  273:     */   }
/*  274:     */   
/*  275:     */   public OrdenSalidaMaterial cargarDetalle(int idOrdenSalidaMaterial, Producto producto)
/*  276:     */   {
/*  277: 320 */     return this.ordenSalidaMaterialDao.cargarDetalle(idOrdenSalidaMaterial, producto);
/*  278:     */   }
/*  279:     */   
/*  280:     */   private void validar(OrdenSalidaMaterial ordenSalidaMaterial)
/*  281:     */     throws ExcepcionAS2Financiero, AS2Exception
/*  282:     */   {
/*  283: 332 */     this.servicioPeriodo.buscarPorFecha(ordenSalidaMaterial.getFecha(), ordenSalidaMaterial.getIdOrganizacion(), ordenSalidaMaterial
/*  284: 333 */       .getDocumento().getDocumentoBase());
/*  285: 334 */     if ((ordenSalidaMaterial.getFechaSalidaMaterial() != null) && 
/*  286: 335 */       (!FuncionesUtiles.compararFechaAnteriorOIgual(FuncionesUtiles.setAtributoFecha(ordenSalidaMaterial.getFecha()), 
/*  287: 336 */       FuncionesUtiles.setAtributoFecha(ordenSalidaMaterial.getFechaSalidaMaterial())))) {
/*  288: 337 */       throw new ExcepcionAS2Financiero("msg_error_fecha_salida_material");
/*  289:     */     }
/*  290:     */   }
/*  291:     */   
/*  292:     */   private void cargarSecuencia(OrdenSalidaMaterial ordenSalidaMaterial)
/*  293:     */     throws ExcepcionAS2
/*  294:     */   {
/*  295: 345 */     if ((ordenSalidaMaterial.getNumero() == null) || (ordenSalidaMaterial.getNumero().isEmpty()))
/*  296:     */     {
/*  297: 346 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(ordenSalidaMaterial.getDocumento().getId(), ordenSalidaMaterial.getFecha());
/*  298: 347 */       ordenSalidaMaterial.setNumero(numero);
/*  299:     */     }
/*  300:     */   }
/*  301:     */   
/*  302:     */   public List<ProductoMaterial> getMateriales(Producto producto, Date fecha)
/*  303:     */   {
/*  304: 359 */     return this.ordenSalidaMaterialDao.getMateriales(producto, fecha);
/*  305:     */   }
/*  306:     */   
/*  307:     */   public List<OrdenSalidaMaterial> autocompletarOrdenSalidaMaterial(int idOrganizacion, String numero, Boolean indicadorTransferencia)
/*  308:     */   {
/*  309: 369 */     return autocompletarOrdenSalidaMaterial(idOrganizacion, numero, indicadorTransferencia, null);
/*  310:     */   }
/*  311:     */   
/*  312:     */   public List<OrdenSalidaMaterial> autocompletarOrdenSalidaMaterial(int idOrganizacion, String numero, Boolean indicadorTransferencia, Boolean indicadorAprobado)
/*  313:     */   {
/*  314: 375 */     return this.ordenSalidaMaterialDao.autocompletarOrdenSalidaMaterial(idOrganizacion, numero, indicadorTransferencia, indicadorAprobado);
/*  315:     */   }
/*  316:     */   
/*  317:     */   public void setearDescripcion(OrdenSalidaMaterial osm)
/*  318:     */   {
/*  319: 385 */     Map<Integer, String> hmOrdenFabricacion = new HashMap();
/*  320: 387 */     for (DetalleOrdenSalidaMaterial dmi : osm.getListaDetalleOrdenSalidaMaterial()) {
/*  321: 388 */       if (!dmi.isEliminado())
/*  322:     */       {
/*  323: 389 */         OrdenFabricacion of = dmi.getOrdenFabricacion();
/*  324: 390 */         if ((of != null) && (!of.isEliminado())) {
/*  325: 391 */           hmOrdenFabricacion.put(Integer.valueOf(of.getId()), of.getNumero());
/*  326:     */         }
/*  327:     */       }
/*  328:     */     }
/*  329: 395 */     osm.setDescripcion(FuncionesUtiles.formarDescripcion(hmOrdenFabricacion.values(), osm.getDescripcion(), 200));
/*  330:     */   }
/*  331:     */   
/*  332:     */   public MovimientoInventario generarConsumoBodega(OrdenSalidaMaterial ordenSalidaMaterial, MovimientoInventario movimientoInventario, boolean liquidarOrdenSalida, DocumentoBase documentoBase)
/*  333:     */     throws ExcepcionAS2
/*  334:     */   {
/*  335: 401 */     return generarConsumoBodega(ordenSalidaMaterial, movimientoInventario, liquidarOrdenSalida, documentoBase, false);
/*  336:     */   }
/*  337:     */   
/*  338:     */   private MovimientoInventario generarConsumoBodega(OrdenSalidaMaterial ordenSalidaMaterial, MovimientoInventario movimientoInventario, boolean liquidarOrdenSalida, DocumentoBase documentoBase, boolean cantidadDespachada)
/*  339:     */     throws ExcepcionAS2
/*  340:     */   {
/*  341: 406 */     return generarConsumoBodega(ordenSalidaMaterial, movimientoInventario, liquidarOrdenSalida, documentoBase, cantidadDespachada, null);
/*  342:     */   }
/*  343:     */   
/*  344:     */   private MovimientoInventario generarConsumoBodega(OrdenSalidaMaterial ordenSalidaMaterial, MovimientoInventario movimientoInventario, boolean liquidarOrdenSalida, DocumentoBase documentoBase, boolean cantidadDespachada, Producto producto)
/*  345:     */     throws ExcepcionAS2
/*  346:     */   {
/*  347: 411 */     ordenSalidaMaterial = cargarDetalle(ordenSalidaMaterial.getId(), producto);
/*  348: 412 */     if ((!liquidarOrdenSalida) && (ordenSalidaMaterial.getId() != 0)) {
/*  349: 413 */       ordenSalidaMaterial = cargarDetalle(ordenSalidaMaterial.getId());
/*  350:     */     }
/*  351: 416 */     if (movimientoInventario == null)
/*  352:     */     {
/*  353: 417 */       movimientoInventario = new MovimientoInventario();
/*  354: 418 */       List<Documento> listaDocumentoCombo = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(documentoBase, ordenSalidaMaterial
/*  355: 419 */         .getIdOrganizacion());
/*  356: 421 */       if (!listaDocumentoCombo.isEmpty()) {
/*  357: 422 */         movimientoInventario.setDocumento((Documento)listaDocumentoCombo.get(0));
/*  358:     */       } else {
/*  359: 424 */         throw new ExcepcionAS2("msg_configuracion_documento", " " + documentoBase.toString());
/*  360:     */       }
/*  361: 427 */       movimientoInventario.setIdOrganizacion(ordenSalidaMaterial.getIdOrganizacion());
/*  362: 428 */       movimientoInventario.setSucursal(ordenSalidaMaterial.getSucursal());
/*  363: 429 */       movimientoInventario.setFecha(new Date());
/*  364: 430 */       movimientoInventario.setNumero("");
/*  365: 431 */       movimientoInventario.setEstado(Estado.ELABORADO);
/*  366:     */     }
/*  367: 434 */     Map<Integer, DetalleMovimientoInventario> hmDetalleOrde = new HashMap();
/*  368: 435 */     for (DetalleMovimientoInventario dm : movimientoInventario.getDetalleMovimientosInventario()) {
/*  369: 436 */       if (dm.getDetalleOrdenSalidaMaterial() != null)
/*  370:     */       {
/*  371: 437 */         if (liquidarOrdenSalida) {
/*  372: 438 */           dm.setEliminado(true);
/*  373:     */         }
/*  374: 440 */         hmDetalleOrde.put(Integer.valueOf(dm.getDetalleOrdenSalidaMaterial().getId()), dm);
/*  375:     */       }
/*  376:     */     }
/*  377: 444 */     for (DetalleOrdenSalidaMaterial detalle : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial())
/*  378:     */     {
/*  379: 446 */       BigDecimal cantidad = BigDecimal.ZERO;
/*  380: 447 */       if (cantidadDespachada) {
/*  381: 448 */         cantidad = detalle.getCantidadDespachada().subtract(detalle.getCantidadDevuelta()).add(detalle.getCantidadAdicional());
/*  382:     */       } else {
/*  383: 450 */         cantidad = liquidarOrdenSalida ? detalle.getCantidadUtilizada() : detalle.getCantidadPorUtilizar();
/*  384:     */       }
/*  385: 452 */       if (cantidad.compareTo(BigDecimal.ZERO) > 0)
/*  386:     */       {
/*  387: 454 */         DetalleMovimientoInventario detMov = (DetalleMovimientoInventario)hmDetalleOrde.get(Integer.valueOf(detalle.getId()));
/*  388: 456 */         if ((detMov == null) || (liquidarOrdenSalida))
/*  389:     */         {
/*  390: 457 */           if (detMov == null)
/*  391:     */           {
/*  392: 458 */             detMov = new DetalleMovimientoInventario();
/*  393: 459 */             detMov.setIdOrganizacion(movimientoInventario.getIdOrganizacion());
/*  394: 460 */             detMov.setIdSucursal(movimientoInventario.getSucursal().getId());
/*  395: 461 */             detMov.setMovimientoInventario(movimientoInventario);
/*  396:     */             
/*  397: 463 */             InventarioProducto inventarioProducto = new InventarioProducto();
/*  398: 464 */             inventarioProducto.setOperacion(movimientoInventario.getDocumento().getOperacion());
/*  399: 465 */             inventarioProducto.setProducto(detMov.getProducto());
/*  400: 466 */             inventarioProducto.setDetalleMovimientoInventario(detMov);
/*  401: 467 */             inventarioProducto.setLote(detalle.getLote());
/*  402: 468 */             detMov.setInventarioProducto(inventarioProducto);
/*  403: 469 */             movimientoInventario.getDetalleMovimientosInventario().add(detMov);
/*  404:     */           }
/*  405: 472 */           if (!movimientoInventario.getDetalleMovimientosInventario().isEmpty()) {
/*  406: 473 */             detMov.setDestinoCosto(((DetalleMovimientoInventario)movimientoInventario.getDetalleMovimientosInventario().get(0)).getDestinoCosto());
/*  407:     */           }
/*  408: 476 */           detMov.setProducto(detalle.getProducto());
/*  409: 477 */           detMov.setCantidad(cantidad);
/*  410: 478 */           detMov.setCantidadOrigen(cantidad);
/*  411: 479 */           detMov.setTraCantidadCoversion(cantidad);
/*  412: 480 */           detMov.setDetalleOrdenSalidaMaterial(detalle);
/*  413: 481 */           detMov.setDescripcion(detalle.getDescripcion());
/*  414: 482 */           detMov.setBodegaOrigen(detalle.getBodega());
/*  415: 483 */           detMov.setUnidadConversion(detMov.getProducto().getUnidad());
/*  416: 484 */           detMov.setDestinoCosto(detalle.getDestinoCosto());
/*  417: 485 */           detMov.setLote(detalle.getLote());
/*  418: 486 */           detMov.setSaldo(this.servicioProducto.getSaldo(detalle.getProducto().getId(), detalle.getBodega().getId(), movimientoInventario
/*  419: 487 */             .getFecha()));
/*  420: 488 */           this.servicioUnidadConversion.cargarListaUnidadConversion(detMov.getProducto());
/*  421:     */         }
/*  422: 490 */         detMov.setEliminado(false);
/*  423:     */       }
/*  424:     */     }
/*  425: 495 */     return movimientoInventario;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public MovimientoInventario generarTransferenciaBodega(OrdenSalidaMaterial ordenSalidaMaterial, MovimientoInventario transferenciaBodega)
/*  429:     */     throws ExcepcionAS2
/*  430:     */   {
/*  431: 502 */     ordenSalidaMaterial = cargarDetalle(ordenSalidaMaterial.getId());
/*  432: 504 */     if (transferenciaBodega == null)
/*  433:     */     {
/*  434: 505 */       transferenciaBodega = new MovimientoInventario();
/*  435: 506 */       List<Documento> listaDocumentoCombo = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.TRANSFERENCIA_BODEGA, ordenSalidaMaterial
/*  436: 507 */         .getIdOrganizacion());
/*  437: 509 */       if (!listaDocumentoCombo.isEmpty()) {
/*  438: 510 */         transferenciaBodega.setDocumento((Documento)listaDocumentoCombo.get(0));
/*  439:     */       }
/*  440: 515 */       transferenciaBodega.setIdOrganizacion(ordenSalidaMaterial.getIdOrganizacion());
/*  441: 516 */       transferenciaBodega.setFecha(new Date());
/*  442: 517 */       transferenciaBodega.setNumero("");
/*  443: 518 */       transferenciaBodega.setEstado(Estado.ELABORADO);
/*  444:     */     }
/*  445: 520 */     if (ordenSalidaMaterial.getBodegaOrigen() != null) {
/*  446: 521 */       transferenciaBodega.setBodegaOrigen(ordenSalidaMaterial.getBodegaOrigen());
/*  447:     */     }
/*  448: 523 */     Map<Integer, DetalleMovimientoInventario> hmDetalleOrde = new HashMap();
/*  449: 524 */     for (DetalleMovimientoInventario dm : transferenciaBodega.getDetalleMovimientosInventario()) {
/*  450: 525 */       if (dm.getDetalleOrdenSalidaMaterial() != null) {
/*  451: 526 */         hmDetalleOrde.put(Integer.valueOf(dm.getDetalleOrdenSalidaMaterial().getId()), dm);
/*  452:     */       }
/*  453:     */     }
/*  454: 530 */     for (DetalleOrdenSalidaMaterial detalle : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial())
/*  455:     */     {
/*  456: 532 */       BigDecimal cantidad = detalle.getCantidadPorUtilizar();
/*  457: 534 */       if (cantidad.compareTo(BigDecimal.ZERO) > 0)
/*  458:     */       {
/*  459: 536 */         DetalleMovimientoInventario detMov = (DetalleMovimientoInventario)hmDetalleOrde.get(Integer.valueOf(detalle.getId()));
/*  460: 538 */         if (detMov == null)
/*  461:     */         {
/*  462: 540 */           detMov = new DetalleMovimientoInventario();
/*  463: 541 */           detMov.setIdOrganizacion(transferenciaBodega.getIdOrganizacion());
/*  464: 542 */           detMov.setIdSucursal(transferenciaBodega.getSucursal().getId());
/*  465: 543 */           detMov.setMovimientoInventario(transferenciaBodega);
/*  466:     */           
/*  467: 545 */           InventarioProducto inventarioProducto = new InventarioProducto();
/*  468: 546 */           inventarioProducto.setOperacion(transferenciaBodega.getDocumento().getOperacion());
/*  469: 547 */           inventarioProducto.setProducto(detMov.getProducto());
/*  470: 548 */           inventarioProducto.setDetalleMovimientoInventario(detMov);
/*  471: 549 */           detMov.setInventarioProducto(inventarioProducto);
/*  472:     */           
/*  473: 551 */           detMov.setProducto(detalle.getProducto());
/*  474: 552 */           detMov.setCantidad(cantidad);
/*  475: 553 */           detMov.setCantidadOrigen(cantidad);
/*  476: 554 */           detMov.setTraCantidadCoversion(cantidad);
/*  477: 555 */           detMov.setDetalleOrdenSalidaMaterial(detalle);
/*  478: 556 */           detMov.setDescripcion(detalle.getDescripcion());
/*  479: 557 */           detMov.setBodegaOrigen(transferenciaBodega.getBodegaOrigen());
/*  480: 558 */           detMov.setBodegaDestino(detalle.getBodega());
/*  481: 559 */           detMov.setUnidadConversion(detMov.getProducto().getUnidad());
/*  482: 560 */           detMov.setSaldo(this.servicioProducto.getSaldo(detalle.getProducto().getId(), detMov.getBodegaOrigen().getId(), transferenciaBodega
/*  483: 561 */             .getFecha()));
/*  484: 562 */           this.servicioUnidadConversion.cargarListaUnidadConversion(detMov.getProducto());
/*  485: 563 */           transferenciaBodega.getDetalleMovimientosInventario().add(detMov);
/*  486:     */         }
/*  487:     */         else
/*  488:     */         {
/*  489: 565 */           detMov.setEliminado(false);
/*  490:     */         }
/*  491:     */       }
/*  492:     */     }
/*  493: 569 */     asignarBodegaDestinoTransferenciaDeBodega(transferenciaBodega);
/*  494: 570 */     return transferenciaBodega;
/*  495:     */   }
/*  496:     */   
/*  497:     */   public void asignarBodegaDestinoTransferenciaDeBodega(MovimientoInventario transferenciaBodega)
/*  498:     */   {
/*  499: 581 */     Map<Bodega, Integer> hashBodegas = new HashMap();
/*  500:     */     
/*  501: 583 */     Bodega bodega = null;
/*  502: 584 */     for (DetalleMovimientoInventario dmi : transferenciaBodega.getDetalleMovimientosInventario())
/*  503:     */     {
/*  504: 585 */       Integer count = (Integer)hashBodegas.get(dmi.getBodegaDestino());
/*  505: 586 */       if (count != null)
/*  506:     */       {
/*  507: 587 */         count = Integer.valueOf(count.intValue() + 1);
/*  508: 588 */         hashBodegas.put(dmi.getBodegaDestino(), count);
/*  509:     */       }
/*  510:     */       else
/*  511:     */       {
/*  512: 590 */         count = Integer.valueOf(1);
/*  513: 591 */         bodega = dmi.getBodegaDestino();
/*  514: 592 */         hashBodegas.put(dmi.getBodegaDestino(), count);
/*  515:     */       }
/*  516:     */     }
/*  517: 596 */     for (Bodega b : hashBodegas.keySet()) {
/*  518: 597 */       if (((Integer)hashBodegas.get(b)).intValue() > ((Integer)hashBodegas.get(bodega)).intValue()) {
/*  519: 598 */         bodega = b;
/*  520:     */       }
/*  521:     */     }
/*  522: 601 */     transferenciaBodega.setBodegaDestino(bodega);
/*  523:     */   }
/*  524:     */   
/*  525:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  526:     */   public MovimientoInventario cerrarOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/*  527:     */     throws ExcepcionAS2, AS2Exception
/*  528:     */   {
/*  529:     */     try
/*  530:     */     {
/*  531: 614 */       if (ordenSalidaMaterial.getTipoCicloProduccionEnum().equals(TipoCicloProduccionEnum.CICLO_CORTO))
/*  532:     */       {
/*  533: 616 */         List<OrdenFabricacion> listaOrdenes = getOrdenesNoFinalizadas(ordenSalidaMaterial);
/*  534: 617 */         if (!listaOrdenes.isEmpty())
/*  535:     */         {
/*  536: 618 */           String mensaje = "";
/*  537: 619 */           for (OrdenFabricacion orden : listaOrdenes) {
/*  538: 620 */             mensaje = mensaje + "," + orden.getNumero();
/*  539:     */           }
/*  540: 622 */           mensaje = mensaje.substring(1);
/*  541: 623 */           throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterialImpl.ERROR_ORDENES_PENDIENTES_FINALIZAR", new String[] { ordenSalidaMaterial.getNumero(), mensaje });
/*  542:     */         }
/*  543:     */       }
/*  544: 631 */       ordenSalidaMaterial = buscarPorId(ordenSalidaMaterial.getId());
/*  545: 633 */       if (!ordenSalidaMaterial.isIndicadorTransferencia()) {
/*  546: 634 */         detalleSinDespachoConsumosDirectos(ordenSalidaMaterial);
/*  547:     */       }
/*  548: 636 */       ordenSalidaMaterial.setEstado(Estado.CERRADO);
/*  549: 637 */       List<OrdenFabricacion> listaOrdenFabricacion = new ArrayList();
/*  550: 638 */       if (!ordenSalidaMaterial.isIndicadorTransferencia())
/*  551:     */       {
/*  552: 639 */         prorratearLotes(ordenSalidaMaterial);
/*  553: 640 */         listaOrdenFabricacion = prorratearCantidadUtilizada(ordenSalidaMaterial);
/*  554:     */       }
/*  555: 643 */       guardar(ordenSalidaMaterial);
/*  556:     */       
/*  557: 645 */       MovimientoInventario movimientoInventario = null;
/*  558: 646 */       if (!ordenSalidaMaterial.isIndicadorTransferencia())
/*  559:     */       {
/*  560: 647 */         movimientoInventario = this.ordenSalidaMaterialDao.obtenerSalidaMaterialPorOrdenSalida(ordenSalidaMaterial);
/*  561: 648 */         if (movimientoInventario != null) {
/*  562: 649 */           movimientoInventario = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(movimientoInventario.getId()));
/*  563:     */         }
/*  564: 651 */         movimientoInventario = generarConsumoBodega(ordenSalidaMaterial, movimientoInventario, true, DocumentoBase.SALIDA_MATERIAL);
/*  565: 652 */         movimientoInventario.setIndicadorSaldoInicial(false);
/*  566:     */         
/*  567: 654 */         movimientoInventario.setFecha(ordenSalidaMaterial.getFechaSalidaMaterial() != null ? ordenSalidaMaterial.getFechaSalidaMaterial() : ordenSalidaMaterial
/*  568: 655 */           .getFecha());
/*  569:     */         
/*  570: 657 */         movimientoInventario.setIndicadorAutomatico(true);
/*  571:     */         
/*  572: 659 */         this.servicioMovimientoInventario.guardar(movimientoInventario);
/*  573: 660 */         for (OrdenFabricacion ordenFabricacion : listaOrdenFabricacion)
/*  574:     */         {
/*  575: 662 */           List<MovimientoInventario> listaIngresoFabricacion = this.movimientoInventarioDao.buscarIngresoFabricacionPorOrdenFabricacion(ordenFabricacion);
/*  576: 664 */           for (MovimientoInventario ingresoFabricacion : listaIngresoFabricacion) {
/*  577: 665 */             this.servicioMovimientoInventario.contabilizarIngresoFabricacion(ingresoFabricacion);
/*  578:     */           }
/*  579:     */         }
/*  580:     */       }
/*  581: 670 */       return movimientoInventario;
/*  582:     */     }
/*  583:     */     catch (ExcepcionAS2Financiero e)
/*  584:     */     {
/*  585: 673 */       this.context.setRollbackOnly();
/*  586: 674 */       throw e;
/*  587:     */     }
/*  588:     */     catch (ExcepcionAS2 e)
/*  589:     */     {
/*  590: 676 */       this.context.setRollbackOnly();
/*  591: 677 */       throw e;
/*  592:     */     }
/*  593:     */     catch (AS2Exception e)
/*  594:     */     {
/*  595: 679 */       this.context.setRollbackOnly();
/*  596: 680 */       throw e;
/*  597:     */     }
/*  598:     */     catch (Exception e)
/*  599:     */     {
/*  600: 682 */       e.printStackTrace();
/*  601: 683 */       this.context.setRollbackOnly();
/*  602: 684 */       throw new ExcepcionAS2(e);
/*  603:     */     }
/*  604:     */   }
/*  605:     */   
/*  606:     */   private BigDecimal totalizarCantidadDOSMOF(OrdenSalidaMaterial ordenSalidaMaterial, Producto producto, OrdenFabricacion ordenFabricacion)
/*  607:     */   {
/*  608: 689 */     BigDecimal total = BigDecimal.ZERO;
/*  609: 690 */     boolean indicadorProrratear = false;
/*  610: 691 */     for (DetalleOrdenSalidaMaterial dosm : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial())
/*  611:     */     {
/*  612: 692 */       DetalleOrdenSalidaMaterialOrdenFabricacion dosmof = getDOSMOF(dosm, producto, ordenFabricacion);
/*  613: 693 */       if (dosmof != null) {
/*  614: 694 */         total = total.add(dosmof.getCantidad());
/*  615:     */       } else {
/*  616: 696 */         indicadorProrratear = true;
/*  617:     */       }
/*  618:     */     }
/*  619: 699 */     if (indicadorProrratear) {
/*  620: 700 */       return total;
/*  621:     */     }
/*  622: 702 */     return null;
/*  623:     */   }
/*  624:     */   
/*  625:     */   private DetalleOrdenSalidaMaterialOrdenFabricacion getDOSMOF(DetalleOrdenSalidaMaterial dosm, Producto producto, OrdenFabricacion ordenFabricacion)
/*  626:     */   {
/*  627: 708 */     for (DetalleOrdenSalidaMaterialOrdenFabricacion dosmof : dosm.getListaDetalleOrdenSalidaMaterialOrdenFabricacion()) {
/*  628: 709 */       if ((dosmof.getDetalleOrdenSalidaMaterial().getProducto().getId() == producto.getId()) && 
/*  629: 710 */         (dosmof.getOrdenFabricacion().getId() == ordenFabricacion.getId())) {
/*  630: 711 */         return dosmof;
/*  631:     */       }
/*  632:     */     }
/*  633: 714 */     return null;
/*  634:     */   }
/*  635:     */   
/*  636:     */   private void prorratearLotes(OrdenSalidaMaterial ordenSalidaMaterial)
/*  637:     */   {
/*  638: 718 */     Map<Integer, List<DetalleOrdenSalidaMaterial>> mapaLotesDetalles = new HashMap();
/*  639: 719 */     Map<Integer, BigDecimal> mapaLotesTotalDespachado = new HashMap();
/*  640: 720 */     Map<Integer, List<DetalleOrdenSalidaMaterialOrdenFabricacion>> mapaLotesDOSMOF = new HashMap();
/*  641: 721 */     Set<String> detallesAgregados = new HashSet();
/*  642: 722 */     for (DetalleOrdenSalidaMaterial detalle : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/*  643: 723 */       if ((!detalle.isIndicadorConsumoDirecto()) && (detalle.getProducto().isIndicadorLote()))
/*  644:     */       {
/*  645: 724 */         int idProducto = detalle.getProducto().getId();
/*  646: 725 */         List<DetalleOrdenSalidaMaterial> listaDOSM = (List)mapaLotesDetalles.get(Integer.valueOf(idProducto));
/*  647: 726 */         BigDecimal valorAcumulado = (BigDecimal)mapaLotesTotalDespachado.get(Integer.valueOf(idProducto));
/*  648: 727 */         listaDOSMOF = (List)mapaLotesDOSMOF.get(Integer.valueOf(idProducto));
/*  649: 729 */         if (listaDOSM == null) {
/*  650: 730 */           listaDOSM = new ArrayList();
/*  651:     */         }
/*  652: 732 */         if (valorAcumulado == null) {
/*  653: 733 */           valorAcumulado = BigDecimal.ZERO;
/*  654:     */         }
/*  655: 735 */         if (listaDOSMOF == null) {
/*  656: 736 */           listaDOSMOF = new ArrayList();
/*  657:     */         }
/*  658: 738 */         listaDOSM.add(detalle);
/*  659: 739 */         BigDecimal cantidadUtilizada = detalle.getCantidadDespachada().subtract(detalle.getCantidadDevuelta());
/*  660: 740 */         valorAcumulado = valorAcumulado.add(cantidadUtilizada);
/*  661: 741 */         for (DetalleOrdenSalidaMaterialOrdenFabricacion dosmof : detalle.getListaDetalleOrdenSalidaMaterialOrdenFabricacion())
/*  662:     */         {
/*  663: 742 */           key = idProducto + "~" + dosmof.getOrdenFabricacion().getId();
/*  664: 743 */           if (!detallesAgregados.contains(key))
/*  665:     */           {
/*  666: 744 */             BigDecimal totalProrratear = totalizarCantidadDOSMOF(ordenSalidaMaterial, detalle.getProducto(), dosmof
/*  667: 745 */               .getOrdenFabricacion());
/*  668: 746 */             if ((totalProrratear != null) && (totalProrratear.compareTo(BigDecimal.ZERO) != 0))
/*  669:     */             {
/*  670: 747 */               dosmof.setCantidadOriginalRepartir(totalProrratear);
/*  671: 748 */               detallesAgregados.add(key);
/*  672: 749 */               listaDOSMOF.add(dosmof);
/*  673:     */             }
/*  674:     */           }
/*  675:     */         }
/*  676: 754 */         mapaLotesDetalles.put(Integer.valueOf(idProducto), listaDOSM);
/*  677: 755 */         mapaLotesTotalDespachado.put(Integer.valueOf(idProducto), valorAcumulado);
/*  678: 756 */         mapaLotesDOSMOF.put(Integer.valueOf(idProducto), listaDOSMOF);
/*  679:     */       }
/*  680:     */     }
/*  681:     */     List<DetalleOrdenSalidaMaterialOrdenFabricacion> listaDOSMOF;
/*  682:     */     String key;
/*  683: 759 */     Object it = mapaLotesDetalles.keySet().iterator();
/*  684:     */     Integer idProducto;
/*  685:     */     BigDecimal totalDespacho;
/*  686:     */     List<DetalleOrdenSalidaMaterialOrdenFabricacion> listaDOSMOF;
/*  687:     */     List<DetalleOrdenSalidaMaterial> listaDOSM;
/*  688:     */     DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial;
/*  689:     */     BigDecimal proporcion;
/*  690: 760 */     while (((Iterator)it).hasNext())
/*  691:     */     {
/*  692: 761 */       idProducto = (Integer)((Iterator)it).next();
/*  693: 762 */       totalDespacho = (BigDecimal)mapaLotesTotalDespachado.get(idProducto);
/*  694: 763 */       listaDOSMOF = (List)mapaLotesDOSMOF.get(idProducto);
/*  695: 764 */       listaDOSM = (List)mapaLotesDetalles.get(idProducto);
/*  696: 766 */       for (listaDOSMOF = listaDOSM.iterator(); listaDOSMOF.hasNext();)
/*  697:     */       {
/*  698: 766 */         detalleOrdenSalidaMaterial = (DetalleOrdenSalidaMaterial)listaDOSMOF.next();
/*  699:     */         
/*  700:     */ 
/*  701: 769 */         BigDecimal cantidadUtilizada = detalleOrdenSalidaMaterial.getCantidadDespachada().subtract(detalleOrdenSalidaMaterial.getCantidadDevuelta());
/*  702:     */         BigDecimal proporcion;
/*  703: 771 */         if (totalDespacho.compareTo(BigDecimal.ZERO) == 0) {
/*  704: 772 */           proporcion = BigDecimal.ZERO;
/*  705:     */         } else {
/*  706: 774 */           proporcion = cantidadUtilizada.divide(totalDespacho, 10, RoundingMode.HALF_UP);
/*  707:     */         }
/*  708: 778 */         for (DetalleOrdenSalidaMaterialOrdenFabricacion dosmof : listaDOSMOF)
/*  709:     */         {
/*  710: 779 */           DetalleOrdenSalidaMaterialOrdenFabricacion detalle = getDOSMOF(detalleOrdenSalidaMaterial, detalleOrdenSalidaMaterial
/*  711: 780 */             .getProducto(), dosmof.getOrdenFabricacion());
/*  712: 783 */           if (detalle == null)
/*  713:     */           {
/*  714: 784 */             DetalleOrdenSalidaMaterialOrdenFabricacion nueva = new DetalleOrdenSalidaMaterialOrdenFabricacion();
/*  715: 785 */             nueva.setIdOrganizacion(detalleOrdenSalidaMaterial.getIdOrganizacion());
/*  716: 786 */             nueva.setIdSucursal(detalleOrdenSalidaMaterial.getIdSucursal());
/*  717: 787 */             nueva.setDetalleOrdenSalidaMaterial(detalleOrdenSalidaMaterial);
/*  718: 788 */             nueva.setOrdenFabricacion(dosmof.getOrdenFabricacion());
/*  719: 789 */             nueva.setCantidad(dosmof.getCantidadOriginalRepartir().multiply(proporcion).setScale(2, RoundingMode.HALF_UP));
/*  720: 790 */             detalleOrdenSalidaMaterial.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().add(nueva);
/*  721:     */           }
/*  722:     */           else
/*  723:     */           {
/*  724: 794 */             detalle.setCantidad(dosmof.getCantidadOriginalRepartir().multiply(proporcion).setScale(2, RoundingMode.HALF_UP));
/*  725:     */           }
/*  726:     */         }
/*  727:     */       }
/*  728:     */     }
/*  729: 801 */     for (DetalleOrdenSalidaMaterial detalle : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/*  730: 802 */       if ((!detalle.isIndicadorConsumoDirecto()) && (detalle.getProducto().isIndicadorLote()))
/*  731:     */       {
/*  732: 803 */         BigDecimal totalCantidad = BigDecimal.ZERO;
/*  733: 804 */         for (DetalleOrdenSalidaMaterialOrdenFabricacion dosmof : detalle.getListaDetalleOrdenSalidaMaterialOrdenFabricacion()) {
/*  734: 805 */           totalCantidad = totalCantidad.add(dosmof.getCantidad());
/*  735:     */         }
/*  736: 807 */         detalle.setCantidad(totalCantidad);
/*  737:     */       }
/*  738:     */     }
/*  739:     */   }
/*  740:     */   
/*  741:     */   private List<OrdenFabricacion> prorratearCantidadUtilizada(OrdenSalidaMaterial ordenSalidaMaterial)
/*  742:     */     throws AS2Exception
/*  743:     */   {
/*  744: 813 */     repartirOrdenesFabricacionPorDetalleCicloLargo(ordenSalidaMaterial);
/*  745: 814 */     List<OrdenFabricacion> listaOrdenFabricacion = new ArrayList();
/*  746: 815 */     Set<Integer> setOrdenFabricacion = new HashSet();
/*  747: 817 */     for (DetalleOrdenSalidaMaterial detalleOS : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial())
/*  748:     */     {
/*  749: 819 */       int numeroDecimales = detalleOS.getUnidad().getNumeroDecimales().intValue();
/*  750: 820 */       numeroDecimales = numeroDecimales > 2 ? 2 : numeroDecimales;
/*  751: 821 */       detalleOS.setCantidadSegunFabricacion(BigDecimal.ZERO);
/*  752: 822 */       detalleOS.setCantidadUtilizada(BigDecimal.ZERO);
/*  753:     */       
/*  754: 824 */       BigDecimal totalCantidadAdicionalRepartida = BigDecimal.ZERO;
/*  755: 826 */       for (DetalleOrdenSalidaMaterialOrdenFabricacion detalleOSMOF : detalleOS.getListaDetalleOrdenSalidaMaterialOrdenFabricacion())
/*  756:     */       {
/*  757: 827 */         if (!setOrdenFabricacion.contains(Integer.valueOf(detalleOSMOF.getOrdenFabricacion().getId())))
/*  758:     */         {
/*  759: 828 */           listaOrdenFabricacion.add(detalleOSMOF.getOrdenFabricacion());
/*  760: 829 */           setOrdenFabricacion.add(Integer.valueOf(detalleOSMOF.getOrdenFabricacion().getId()));
/*  761:     */         }
/*  762: 832 */         porcentajeProducido = BigDecimal.ZERO;
/*  763: 833 */         OrdenFabricacion ordenF = detalleOSMOF.getOrdenFabricacion();
/*  764: 834 */         if (TipoCicloProduccionEnum.CICLO_CORTO.equals(ordenSalidaMaterial.getTipoCicloProduccionEnum()))
/*  765:     */         {
/*  766: 835 */           if (ordenF.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
/*  767: 836 */             porcentajeProducido = ordenF.getCantidadFabricada().divide(ordenF.getCantidad(), 10, RoundingMode.HALF_UP);
/*  768:     */           }
/*  769:     */         }
/*  770:     */         else {
/*  771: 839 */           porcentajeProducido = BigDecimal.ONE;
/*  772:     */         }
/*  773: 842 */         BigDecimal cantidadSegunFabricacion = detalleOSMOF.getCantidad().multiply(porcentajeProducido);
/*  774: 843 */         detalleOSMOF.setCantidadSegunFabricacion(cantidadSegunFabricacion);
/*  775: 844 */         detalleOS.setCantidadSegunFabricacion(detalleOS.getCantidadSegunFabricacion().add(detalleOSMOF.getCantidadSegunFabricacion()));
/*  776: 846 */         if (detalleOS.isIndicadorConsumoDirecto())
/*  777:     */         {
/*  778: 849 */           detalleOSMOF.setCantidadUtilizada(cantidadSegunFabricacion.setScale(numeroDecimales, RoundingMode.HALF_UP));
/*  779: 851 */           if (detalleOS.getCantidadAdicional().compareTo(BigDecimal.ZERO) > 0)
/*  780:     */           {
/*  781: 853 */             BigDecimal cantidadRepartida = BigDecimal.ZERO;
/*  782: 854 */             if (detalleOS.getCantidadSegunFabricacion().compareTo(BigDecimal.ZERO) != 0) {
/*  783: 857 */               cantidadRepartida = detalleOSMOF.getCantidadSegunFabricacion().divide(detalleOS.getCantidadSegunFabricacion(), 20, RoundingMode.HALF_UP).multiply(detalleOS.getCantidadAdicional());
/*  784:     */             }
/*  785: 860 */             cantidadRepartida = cantidadRepartida.setScale(numeroDecimales, RoundingMode.HALF_UP);
/*  786: 861 */             totalCantidadAdicionalRepartida = totalCantidadAdicionalRepartida.add(cantidadRepartida);
/*  787:     */             
/*  788: 863 */             detalleOSMOF.setCantidadUtilizada(detalleOSMOF.getCantidadUtilizada().add(cantidadRepartida));
/*  789:     */           }
/*  790: 871 */           detalleOS.setCantidadUtilizada(detalleOS.getCantidadUtilizada().add(detalleOSMOF.getCantidadUtilizada()));
/*  791:     */         }
/*  792:     */       }
/*  793:     */       BigDecimal porcentajeProducido;
/*  794: 874 */       if (detalleOS.isIndicadorConsumoDirecto())
/*  795:     */       {
/*  796: 875 */         if (detalleOS.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().isEmpty()) {
/*  797: 876 */           throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterial.ERROR_DETALLE_ORDEN_SALIDA_MATERIAL_SIN_DOSMOF", new String[] { detalleOS.getProducto().getNombre(), "" });
/*  798:     */         }
/*  799: 879 */         BigDecimal diferencia = detalleOS.getCantidadAdicional().subtract(totalCantidadAdicionalRepartida);
/*  800: 880 */         if ((diferencia.compareTo(BigDecimal.ZERO) > 0) && (detalleOS.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().size() > 0))
/*  801:     */         {
/*  802: 881 */           BigDecimal cantidad = ((DetalleOrdenSalidaMaterialOrdenFabricacion)detalleOS.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().get(0)).getCantidadUtilizada();
/*  803: 882 */           ((DetalleOrdenSalidaMaterialOrdenFabricacion)detalleOS.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().get(0)).setCantidadUtilizada(cantidad.add(diferencia));
/*  804: 883 */           detalleOS.setCantidadUtilizada(detalleOS.getCantidadUtilizada().add(diferencia));
/*  805:     */         }
/*  806:     */       }
/*  807: 887 */       if (!detalleOS.isIndicadorConsumoDirecto())
/*  808:     */       {
/*  809: 889 */         BigDecimal cantidadUtilizada = detalleOS.getCantidadDespachada().subtract(detalleOS.getCantidadDevuelta());
/*  810: 891 */         if ((cantidadUtilizada.compareTo(BigDecimal.ZERO) != 0) && (detalleOS.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().isEmpty())) {
/*  811: 893 */           throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterial.ERROR_DETALLE_ORDEN_SALIDA_MATERIAL_SIN_DOSMOF", new String[] { detalleOS.getProducto().getNombre(), cantidadUtilizada.toString() });
/*  812:     */         }
/*  813: 896 */         detalleOS.setCantidadUtilizada(cantidadUtilizada);
/*  814:     */         
/*  815: 898 */         BigDecimal totalCantidadRepartida = BigDecimal.ZERO;
/*  816: 900 */         for (DetalleOrdenSalidaMaterialOrdenFabricacion detalleOSMOF : detalleOS.getListaDetalleOrdenSalidaMaterialOrdenFabricacion())
/*  817:     */         {
/*  818: 901 */           BigDecimal cantidadRepartida = BigDecimal.ZERO;
/*  819: 902 */           if (detalleOS.getCantidadSegunFabricacion().compareTo(BigDecimal.ZERO) != 0) {
/*  820: 904 */             cantidadRepartida = detalleOSMOF.getCantidadSegunFabricacion().divide(detalleOS.getCantidadSegunFabricacion(), 20, RoundingMode.HALF_UP).multiply(cantidadUtilizada);
/*  821:     */           }
/*  822: 907 */           cantidadRepartida = cantidadRepartida.setScale(numeroDecimales, RoundingMode.HALF_UP);
/*  823: 908 */           totalCantidadRepartida = totalCantidadRepartida.add(cantidadRepartida);
/*  824:     */           
/*  825: 910 */           detalleOSMOF.setCantidadUtilizada(cantidadRepartida);
/*  826:     */         }
/*  827: 913 */         BigDecimal diferencia = cantidadUtilizada.subtract(totalCantidadRepartida);
/*  828: 914 */         if ((diferencia.compareTo(BigDecimal.ZERO) > 0) && (detalleOS.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().size() > 0))
/*  829:     */         {
/*  830: 915 */           BigDecimal cantidad = ((DetalleOrdenSalidaMaterialOrdenFabricacion)detalleOS.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().get(0)).getCantidadUtilizada();
/*  831: 916 */           ((DetalleOrdenSalidaMaterialOrdenFabricacion)detalleOS.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().get(0)).setCantidadUtilizada(cantidad.add(diferencia));
/*  832:     */         }
/*  833:     */       }
/*  834:     */     }
/*  835: 922 */     prorratearDesecho(ordenSalidaMaterial);
/*  836:     */     
/*  837: 924 */     return listaOrdenFabricacion;
/*  838:     */   }
/*  839:     */   
/*  840:     */   private void prorratearDesecho(OrdenSalidaMaterial ordenSalidaMaterial)
/*  841:     */   {
/*  842: 929 */     for (DetalleOrdenSalidaMaterial detalleOSM : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial())
/*  843:     */     {
/*  844: 931 */       BigDecimal totalCantidadRepartida = BigDecimal.ZERO;
/*  845:     */       
/*  846:     */ 
/*  847: 934 */       int numeroDecimales = detalleOSM.getUnidad().getNumeroDecimales().intValue();
/*  848: 935 */       numeroDecimales = numeroDecimales > 2 ? 2 : numeroDecimales;
/*  849:     */       
/*  850:     */ 
/*  851: 938 */       BigDecimal cantidadDesecho = detalleOSM.getCantidadDesecho() == null ? BigDecimal.ZERO : detalleOSM.getCantidadDesecho();
/*  852: 940 */       if (cantidadDesecho.compareTo(BigDecimal.ZERO) > 0)
/*  853:     */       {
/*  854: 942 */         for (DetalleOrdenSalidaMaterialOrdenFabricacion detalleOSMOF : detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion())
/*  855:     */         {
/*  856: 946 */           BigDecimal cantidadDesechoOSMOF = cantidadDesecho.multiply(detalleOSMOF.getCantidadUtilizada()).divide(detalleOSM.getCantidadUtilizada(), 20, RoundingMode.HALF_UP);
/*  857: 947 */           detalleOSMOF.setCantidadDesecho(cantidadDesechoOSMOF.setScale(numeroDecimales, RoundingMode.HALF_UP));
/*  858: 950 */           if (detalleOSM.isIndicadorConsumoDirecto())
/*  859:     */           {
/*  860: 951 */             detalleOSMOF.setCantidadUtilizada(detalleOSMOF.getCantidadUtilizada().add(detalleOSMOF.getCantidadDesecho()));
/*  861: 952 */             totalCantidadRepartida = totalCantidadRepartida.add(detalleOSMOF.getCantidadUtilizada());
/*  862:     */           }
/*  863:     */         }
/*  864: 957 */         if (detalleOSM.isIndicadorConsumoDirecto())
/*  865:     */         {
/*  866: 958 */           detalleOSM.setCantidadUtilizada(detalleOSM.getCantidadUtilizada().add(cantidadDesecho));
/*  867: 959 */           BigDecimal diferencia = detalleOSM.getCantidadUtilizada().subtract(totalCantidadRepartida);
/*  868: 960 */           if ((diferencia.compareTo(BigDecimal.ZERO) > 0) && (detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().size() > 0))
/*  869:     */           {
/*  870: 961 */             BigDecimal cantidad = ((DetalleOrdenSalidaMaterialOrdenFabricacion)detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().get(0)).getCantidadUtilizada();
/*  871: 962 */             ((DetalleOrdenSalidaMaterialOrdenFabricacion)detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().get(0)).setCantidadUtilizada(cantidad.add(diferencia));
/*  872:     */           }
/*  873: 963 */           else if ((diferencia.compareTo(BigDecimal.ZERO) < 0) && 
/*  874: 964 */             (detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().size() > 0))
/*  875:     */           {
/*  876: 965 */             BigDecimal cantidad = ((DetalleOrdenSalidaMaterialOrdenFabricacion)detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().get(0)).getCantidadUtilizada();
/*  877: 966 */             ((DetalleOrdenSalidaMaterialOrdenFabricacion)detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().get(0)).setCantidadUtilizada(cantidad.subtract(diferencia));
/*  878:     */           }
/*  879:     */         }
/*  880:     */       }
/*  881:     */     }
/*  882:     */   }
/*  883:     */   
/*  884:     */   public void confirmarDevolucion(OrdenSalidaMaterial ordenSalidaMaterial)
/*  885:     */     throws AS2Exception
/*  886:     */   {
/*  887: 976 */     if (this.ordenSalidaMaterialDao.existeLecturaBalanzaSinRecibir(ordenSalidaMaterial)) {
/*  888: 977 */       throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterialImpl.ERROR_ORDENES_PENDIENTES_RECIBIR", new String[] { "" });
/*  889:     */     }
/*  890: 979 */     ordenSalidaMaterial = cargarDetalle(ordenSalidaMaterial.getId());
/*  891: 980 */     ordenSalidaMaterial.setEstado(Estado.PROCESADO);
/*  892: 981 */     this.ordenSalidaMaterialDao.guardar(ordenSalidaMaterial);
/*  893:     */   }
/*  894:     */   
/*  895:     */   public void recibirLecturaBalanza(LecturaBalanza lecturaBalanza)
/*  896:     */     throws AS2Exception
/*  897:     */   {
/*  898: 986 */     this.ordenSalidaMaterialDao.actualizarARecibidaLecturaBalanza(lecturaBalanza);
/*  899:     */   }
/*  900:     */   
/*  901:     */   public List<LecturaBalanza> obtenerPesadasNoRecibidas(int idOrganizacion, String usuarioCreacion, List<Integer> idSucursales)
/*  902:     */   {
/*  903: 991 */     return this.ordenSalidaMaterialDao.getLecturaBalanzaPorRecibir(idOrganizacion, usuarioCreacion, idSucursales);
/*  904:     */   }
/*  905:     */   
/*  906:     */   public List<OrdenFabricacion> getOrdenesNoFinalizadas(OrdenSalidaMaterial ordenSalida)
/*  907:     */   {
/*  908: 996 */     return this.ordenSalidaMaterialDao.getOrdenesNoFinalizadas(ordenSalida);
/*  909:     */   }
/*  910:     */   
/*  911:     */   public void reabrirOrden(OrdenSalidaMaterial ordenSalidaMaterial)
/*  912:     */     throws ExcepcionAS2Financiero, AS2Exception
/*  913:     */   {
/*  914:1002 */     if ((Estado.CERRADO.equals(ordenSalidaMaterial.getEstado())) || (Estado.PROCESADO.equals(ordenSalidaMaterial.getEstado())))
/*  915:     */     {
/*  916:1004 */       this.servicioPeriodo.buscarPorFecha(ordenSalidaMaterial.getFecha(), ordenSalidaMaterial.getIdOrganizacion(), ordenSalidaMaterial
/*  917:1005 */         .getDocumento().getDocumentoBase());
/*  918:1006 */       this.ordenSalidaMaterialDao.reabrirOrden(ordenSalidaMaterial);
/*  919:     */     }
/*  920:     */     else
/*  921:     */     {
/*  922:1008 */       throw new AS2Exception("msg_error_accion_no_permitida", new String[] { ordenSalidaMaterial.getEstado().toString() });
/*  923:     */     }
/*  924:     */   }
/*  925:     */   
/*  926:     */   public void crearMovimientoInventario(OrdenSalidaMaterial ordenSalidaMaterial, Producto producto)
/*  927:     */     throws AS2Exception, ExcepcionAS2
/*  928:     */   {
/*  929:     */     try
/*  930:     */     {
/*  931:1016 */       MovimientoInventario movimientoInventario = null;
/*  932:1017 */       if (!ordenSalidaMaterial.isIndicadorTransferencia())
/*  933:     */       {
/*  934:1019 */         if (ordenSalidaMaterial.getId() != 0)
/*  935:     */         {
/*  936:1020 */           movimientoInventario = this.ordenSalidaMaterialDao.obtenerSalidaMaterialPorOrdenSalida(ordenSalidaMaterial);
/*  937:1021 */           if (movimientoInventario != null) {
/*  938:1022 */             movimientoInventario = this.servicioMovimientoInventario.cargarDetalle(movimientoInventario.getId(), producto);
/*  939:     */           }
/*  940:     */         }
/*  941:1025 */         movimientoInventario = generarConsumoBodega(ordenSalidaMaterial, movimientoInventario, true, DocumentoBase.SALIDA_MATERIAL, true, producto);
/*  942:     */         
/*  943:1027 */         movimientoInventario.setIndicadorSaldoInicial(true);
/*  944:     */         
/*  945:1029 */         movimientoInventario.setFecha(ordenSalidaMaterial.getFechaSalidaMaterial() != null ? ordenSalidaMaterial.getFechaSalidaMaterial() : ordenSalidaMaterial
/*  946:1030 */           .getFecha());
/*  947:     */         
/*  948:1032 */         movimientoInventario.setIndicadorAutomatico(true);
/*  949:     */         
/*  950:1034 */         this.servicioMovimientoInventario.guardar(movimientoInventario, producto);
/*  951:     */       }
/*  952:     */     }
/*  953:     */     catch (AS2Exception e)
/*  954:     */     {
/*  955:1038 */       this.context.setRollbackOnly();
/*  956:1039 */       throw e;
/*  957:     */     }
/*  958:     */     catch (ExcepcionAS2 e)
/*  959:     */     {
/*  960:1041 */       this.context.setRollbackOnly();
/*  961:1042 */       throw e;
/*  962:     */     }
/*  963:     */   }
/*  964:     */   
/*  965:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  966:     */   @Lock(LockType.WRITE)
/*  967:     */   public void anularOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/*  968:     */     throws AS2Exception
/*  969:     */   {
/*  970:     */     try
/*  971:     */     {
/*  972:1051 */       Map<Integer, DetalleOrdenSalidaMaterial> hmGuardarDetalleOrdenSalidaMaterial = new HashMap();
/*  973:     */       
/*  974:1053 */       List<DetalleOrdenSalidaMaterialOrdenFabricacion> lista = this.ordenFabricacionDao.obtenerDetalleOrdenSalidaMaterialOrdenFabricacion(ordenFabricacion, false);
/*  975:1055 */       for (DetalleOrdenSalidaMaterialOrdenFabricacion dosmof : lista)
/*  976:     */       {
/*  977:1056 */         for (LecturaBalanza lecturaBalanza : dosmof.getListaLecturaBalanza()) {
/*  978:1057 */           this.servicioDesechoMaterial.eliminarPesoDesecho(lecturaBalanza);
/*  979:     */         }
/*  980:1059 */         DetalleOrdenSalidaMaterial dosm = dosmof.getDetalleOrdenSalidaMaterial();
/*  981:1060 */         BigDecimal cantidadDOSMOF = dosmof.getCantidad();
/*  982:1061 */         dosmof.setEliminado(true);
/*  983:1062 */         this.detalleOrdenSalidaMaterialOrdenFabricacionDao.guardar(dosmof);
/*  984:1064 */         if (TipoCicloProduccionEnum.CICLO_CORTO.equals(ordenFabricacion.getTipoCicloProduccionEnum()))
/*  985:     */         {
/*  986:1065 */           BigDecimal cantidadRequerida = dosm.getCantidad();
/*  987:1066 */           cantidadRequerida = cantidadRequerida.subtract(cantidadDOSMOF);
/*  988:1067 */           if (cantidadRequerida.compareTo(BigDecimal.ZERO) < 0) {
/*  989:1068 */             cantidadRequerida = BigDecimal.ZERO;
/*  990:     */           }
/*  991:1070 */           dosm.setCantidad(cantidadRequerida);
/*  992:     */           
/*  993:1072 */           List<Integer> listaVerificacionOrdenFabricacion = this.ordenFabricacionDao.getVerificaOrdenFabricacionUnico(dosm);
/*  994:1074 */           if ((cantidadRequerida.compareTo(BigDecimal.ZERO) == 0) && (dosm.getCantidadDespachada().compareTo(BigDecimal.ZERO) == 0) && (
/*  995:1075 */             (listaVerificacionOrdenFabricacion.isEmpty()) || ((listaVerificacionOrdenFabricacion.size() == 1) && 
/*  996:1076 */             (((Integer)listaVerificacionOrdenFabricacion.get(0)).intValue() == dosmof.getOrdenFabricacion().getId())))) {
/*  997:1077 */             dosm.setEliminado(true);
/*  998:     */           }
/*  999:     */         }
/* 1000:1082 */         if ((dosm.getCantidadDesecho() != null) && (dosmof.getCantidadDesecho() != null)) {
/* 1001:1083 */           dosm.setCantidadDesecho(dosm.getCantidadDesecho().subtract(dosmof.getCantidadDesecho()));
/* 1002:     */         }
/* 1003:1085 */         hmGuardarDetalleOrdenSalidaMaterial.put(Integer.valueOf(dosm.getIdDetalleOrdenSalidaMaterial()), dosm);
/* 1004:     */       }
/* 1005:1089 */       if (TipoCicloProduccionEnum.CICLO_LARGO.equals(ordenFabricacion.getTipoCicloProduccionEnum())) {
/* 1006:1090 */         this.ordenSalidaMaterialDao.eliminarAsignacionOrdenFabricacion(ordenFabricacion, null);
/* 1007:     */       }
/* 1008:1092 */       for (DetalleOrdenSalidaMaterial dosm : hmGuardarDetalleOrdenSalidaMaterial.values()) {
/* 1009:1093 */         this.detalleOrdenSalidaMaterialDao.guardar(dosm);
/* 1010:     */       }
/* 1011:     */     }
/* 1012:     */     catch (AS2Exception e)
/* 1013:     */     {
/* 1014:1097 */       this.context.setRollbackOnly();
/* 1015:1098 */       e.printStackTrace();
/* 1016:1099 */       throw e;
/* 1017:     */     }
/* 1018:     */     catch (Exception e)
/* 1019:     */     {
/* 1020:1101 */       this.context.setRollbackOnly();
/* 1021:1102 */       e.printStackTrace();
/* 1022:1103 */       throw new AS2Exception(e.getMessage());
/* 1023:     */     }
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   public void agregarPeso(OrdenSalidaMaterial ordenSalidaMaterial, LecturaBalanza lecturaBalanza, DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterialSeleccionado, boolean mostrarBalanza, List<DetalleOrdenSalidaMaterial> listDetalleOrdenSalidaMaterial, String movimiento, List<LecturaBalanza> listLecturaBalanza, Bodega bodegaSesion)
/* 1027:     */     throws ExcepcionAS2, AS2Exception
/* 1028:     */   {
/* 1029:1112 */     DetalleOrdenSalidaMaterial dosm = null;
/* 1030:     */     try
/* 1031:     */     {
/* 1032:1114 */       if (detalleOrdenSalidaMaterialSeleccionado.getBodega() == null) {
/* 1033:1115 */         throw new ExcepcionAS2("msg_info_seleccionar_bodega", "");
/* 1034:     */       }
/* 1035:1117 */       if ((detalleOrdenSalidaMaterialSeleccionado.getProducto().isIndicadorLote()) && (detalleOrdenSalidaMaterialSeleccionado.getLote() == null)) {
/* 1036:1120 */         throw new ExcepcionAS2("msg_producto_indicador_lote_sin_lote", ": " + detalleOrdenSalidaMaterialSeleccionado.getProducto().getCodigo() + " " + detalleOrdenSalidaMaterialSeleccionado.getProducto().getNombre());
/* 1037:     */       }
/* 1038:     */       String mensaje;
/* 1039:1123 */       if (movimiento.equals("DESPACHO")) {
/* 1040:1125 */         if (detalleOrdenSalidaMaterialSeleccionado.getProducto().isIndicadorLote())
/* 1041:     */         {
/* 1042:1127 */           BigDecimal saldoLote = this.servicioProducto.getSaldoLote(detalleOrdenSalidaMaterialSeleccionado.getProducto().getId(), detalleOrdenSalidaMaterialSeleccionado
/* 1043:1128 */             .getBodega().getId(), detalleOrdenSalidaMaterialSeleccionado.getLote().getId(), ordenSalidaMaterial
/* 1044:1129 */             .getFecha());
/* 1045:1131 */           if (saldoLote.compareTo(lecturaBalanza.getPesoNeto()) < 0)
/* 1046:     */           {
/* 1047:1135 */             String mensaje = " :" + detalleOrdenSalidaMaterialSeleccionado.getBodega().getNombre() + " Producto: " + detalleOrdenSalidaMaterialSeleccionado.getProducto().getNombre() + " Lote:" + detalleOrdenSalidaMaterialSeleccionado.getLote().getCodigo() + " (" + lecturaBalanza.getPesoNeto().toString() + ">" + saldoLote.toString() + ")";
/* 1048:1136 */             throw new ExcepcionAS2Inventario("msg_info_inventario_0001", mensaje);
/* 1049:     */           }
/* 1050:     */         }
/* 1051:     */         else
/* 1052:     */         {
/* 1053:1141 */           BigDecimal saldo = this.servicioProducto.getSaldo(detalleOrdenSalidaMaterialSeleccionado.getProducto().getId(), detalleOrdenSalidaMaterialSeleccionado
/* 1054:1142 */             .getBodega().getId(), ordenSalidaMaterial.getFecha());
/* 1055:1144 */           if (saldo.compareTo(lecturaBalanza.getPesoNeto()) < 0)
/* 1056:     */           {
/* 1057:1147 */             mensaje = " :" + detalleOrdenSalidaMaterialSeleccionado.getBodega().getNombre() + " Producto: " + detalleOrdenSalidaMaterialSeleccionado.getProducto().getNombre() + " (" + lecturaBalanza.getPesoNeto().toString() + ">" + saldo.toString() + ")";
/* 1058:1148 */             throw new ExcepcionAS2Inventario("msg_info_inventario_0001", mensaje);
/* 1059:     */           }
/* 1060:     */         }
/* 1061:     */       }
/* 1062:1153 */       this.servicioMarcacionDispositivo.validarCantidad(lecturaBalanza);
/* 1063:     */       
/* 1064:     */ 
/* 1065:1156 */       this.servicioMarcacionDispositivo.getCantidades(lecturaBalanza);
/* 1066:1157 */       if ((detalleOrdenSalidaMaterialSeleccionado != null) && 
/* 1067:1158 */         (detalleOrdenSalidaMaterialSeleccionado.getProducto().getId() == lecturaBalanza.getProducto().getId()))
/* 1068:     */       {
/* 1069:1159 */         dosm = detalleOrdenSalidaMaterialSeleccionado;
/* 1070:     */       }
/* 1071:     */       else
/* 1072:     */       {
/* 1073:1162 */         Map<Integer, DetalleOrdenSalidaMaterial> hmDetalle = new HashMap();
/* 1074:1163 */         for (DetalleOrdenSalidaMaterial detalle : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/* 1075:1164 */           hmDetalle.put(Integer.valueOf(detalle.getProducto().getId()), detalle);
/* 1076:     */         }
/* 1077:1167 */         dosm = (DetalleOrdenSalidaMaterial)hmDetalle.get(Integer.valueOf(lecturaBalanza.getProducto().getId()));
/* 1078:     */       }
/* 1079:1169 */       if (dosm == null)
/* 1080:     */       {
/* 1081:1170 */         dosm = crearDetalleOrdenSalida(ordenSalidaMaterial, false, listDetalleOrdenSalidaMaterial, null);
/* 1082:1171 */         dosm.setProducto(lecturaBalanza.getProducto());
/* 1083:1172 */         Bodega bodegaTrabajo = this.servicioProducto.obtenerBodegaTrabajoProducto(lecturaBalanza.getProducto(), 
/* 1084:1173 */           Integer.valueOf(ordenSalidaMaterial.getSucursal().getId()));
/* 1085:1174 */         dosm.setBodega(bodegaTrabajo);
/* 1086:1175 */         dosm.setUnidad(lecturaBalanza.getProducto().getUnidad());
/* 1087:1176 */         dosm.setCantidad(BigDecimal.ONE);
/* 1088:1178 */         if (movimiento.equals("DESPACHO"))
/* 1089:     */         {
/* 1090:1179 */           ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().add(dosm);
/* 1091:1180 */           listDetalleOrdenSalidaMaterial.add(dosm);
/* 1092:1181 */           actulizarBodega(dosm, bodegaSesion, ordenSalidaMaterial.getSucursal().getId());
/* 1093:     */         }
/* 1094:     */       }
/* 1095:1185 */       lecturaBalanza.setDetalleOrdenSalidaMaterial(dosm);
/* 1096:1186 */       int operacion = 1;
/* 1097:1187 */       operacion = movimiento.equals("DEVOLVER") ? -1 : movimiento.equals("DESPACHO") ? 1 : 0;
/* 1098:1188 */       lecturaBalanza.setOperacion(operacion);
/* 1099:1189 */       dosm.setEliminado(false);
/* 1100:1190 */       dosm.getListaLecturaBalanza().add(lecturaBalanza);
/* 1101:     */       
/* 1102:1192 */       BigDecimal cantidadTotalDevuelta = dosm.getCantidadDevuelta().add(lecturaBalanza.getPesoNeto());
/* 1103:1193 */       if ((movimiento.equals("DEVOLVER")) && (cantidadTotalDevuelta.compareTo(dosm.getCantidadDespachada()) > 0)) {
/* 1104:1195 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterialImpl.ERROR_ORDEN_SALIDA_MATERIAL_CANTIDAD_DEVUELTA", new String[] { dosm.getProducto().getCodigo(), dosm.getProducto().getNombre(), cantidadTotalDevuelta.toString(), dosm.getCantidadDespachada().toString() });
/* 1105:     */       }
/* 1106:1198 */       listLecturaBalanza.add(lecturaBalanza);
/* 1107:1199 */       dosm.setIndicadorAutomatico(true);
/* 1108:1200 */       totalizarCantidadPesada(dosm, movimiento);
/* 1109:1201 */       guardar(ordenSalidaMaterial);
/* 1110:1202 */       if ((mostrarBalanza) && (!movimiento.equals("DESECHO"))) {
/* 1111:1203 */         crearMovimientoInventario(ordenSalidaMaterial, dosm.getProducto());
/* 1112:     */       }
/* 1113:     */     }
/* 1114:     */     catch (ExcepcionAS2 e)
/* 1115:     */     {
/* 1116:1206 */       e.printStackTrace();
/* 1117:1207 */       if (dosm != null)
/* 1118:     */       {
/* 1119:1208 */         dosm.getListaLecturaBalanza().remove(lecturaBalanza);
/* 1120:1209 */         listLecturaBalanza.remove(lecturaBalanza);
/* 1121:1210 */         totalizarCantidadPesada(dosm, movimiento);
/* 1122:     */       }
/* 1123:1212 */       this.context.setRollbackOnly();
/* 1124:1213 */       throw e;
/* 1125:     */     }
/* 1126:     */     catch (AS2Exception e)
/* 1127:     */     {
/* 1128:1215 */       e.printStackTrace();
/* 1129:1216 */       if (dosm != null)
/* 1130:     */       {
/* 1131:1217 */         dosm.getListaLecturaBalanza().remove(lecturaBalanza);
/* 1132:1218 */         listLecturaBalanza.remove(lecturaBalanza);
/* 1133:1219 */         totalizarCantidadPesada(dosm, movimiento);
/* 1134:     */       }
/* 1135:1221 */       this.context.setRollbackOnly();
/* 1136:1222 */       throw e;
/* 1137:     */     }
/* 1138:     */     catch (Exception e)
/* 1139:     */     {
/* 1140:1224 */       e.printStackTrace();
/* 1141:1225 */       if (dosm != null)
/* 1142:     */       {
/* 1143:1226 */         dosm.getListaLecturaBalanza().remove(lecturaBalanza);
/* 1144:1227 */         listLecturaBalanza.remove(lecturaBalanza);
/* 1145:1228 */         totalizarCantidadPesada(dosm, movimiento);
/* 1146:     */       }
/* 1147:1230 */       this.context.setRollbackOnly();
/* 1148:1231 */       throw new ExcepcionAS2(e);
/* 1149:     */     }
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   public void editarDetalle(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterialSeleccionado, String movimiento)
/* 1153:     */     throws ExcepcionAS2
/* 1154:     */   {
/* 1155:1237 */     BigDecimal cantidadBackUp = detalleOrdenSalidaMaterialSeleccionado.getCantidadDespachada();
/* 1156:     */     try
/* 1157:     */     {
/* 1158:1239 */       if (detalleOrdenSalidaMaterialSeleccionado.getBodega() == null) {
/* 1159:1240 */         throw new ExcepcionAS2("msg_info_seleccionar_bodega", "");
/* 1160:     */       }
/* 1161:1242 */       if ((detalleOrdenSalidaMaterialSeleccionado.getProducto().isIndicadorLote()) && (detalleOrdenSalidaMaterialSeleccionado.getLote() == null)) {
/* 1162:1245 */         throw new ExcepcionAS2("msg_producto_indicador_lote_sin_lote", ": " + detalleOrdenSalidaMaterialSeleccionado.getProducto().getCodigo() + " " + detalleOrdenSalidaMaterialSeleccionado.getProducto().getNombre());
/* 1163:     */       }
/* 1164:1247 */       if (movimiento.equals("DESPACHO")) {
/* 1165:1248 */         detalleOrdenSalidaMaterialSeleccionado.setCantidadDespachada(detalleOrdenSalidaMaterialSeleccionado.getTraCantidadDespachada());
/* 1166:     */       }
/* 1167:1251 */       guardar(detalleOrdenSalidaMaterialSeleccionado.getOrdenSalidaMaterial());
/* 1168:1253 */       if (!movimiento.equals("DESECHO")) {
/* 1169:1254 */         crearMovimientoInventario(detalleOrdenSalidaMaterialSeleccionado.getOrdenSalidaMaterial(), detalleOrdenSalidaMaterialSeleccionado
/* 1170:1255 */           .getProducto());
/* 1171:     */       }
/* 1172:     */     }
/* 1173:     */     catch (ExcepcionAS2 e)
/* 1174:     */     {
/* 1175:1258 */       detalleOrdenSalidaMaterialSeleccionado.setCantidadDespachada(cantidadBackUp);
/* 1176:1259 */       detalleOrdenSalidaMaterialSeleccionado.setTraCantidadDespachada(cantidadBackUp);
/* 1177:1260 */       e.printStackTrace();
/* 1178:1261 */       this.context.setRollbackOnly();
/* 1179:1262 */       throw e;
/* 1180:     */     }
/* 1181:     */     catch (Exception e)
/* 1182:     */     {
/* 1183:1264 */       detalleOrdenSalidaMaterialSeleccionado.setCantidadDespachada(cantidadBackUp);
/* 1184:1265 */       detalleOrdenSalidaMaterialSeleccionado.setTraCantidadDespachada(cantidadBackUp);
/* 1185:1266 */       e.printStackTrace();
/* 1186:1267 */       this.context.setRollbackOnly();
/* 1187:1268 */       throw new ExcepcionAS2(e);
/* 1188:     */     }
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   public DetalleOrdenSalidaMaterial crearDetalleOrdenSalida(OrdenSalidaMaterial ordenSalidaMaterial, boolean agregarDetalle, List<DetalleOrdenSalidaMaterial> listDetalleOrdenSalidaMaterial, DetalleOrdenSalidaMaterial detalle)
/* 1192:     */   {
/* 1193:1276 */     DetalleOrdenSalidaMaterial detalleOrden = new DetalleOrdenSalidaMaterial();
/* 1194:1277 */     detalleOrden.setIdOrganizacion(ordenSalidaMaterial.getIdOrganizacion());
/* 1195:1278 */     detalleOrden.setIdSucursal(ordenSalidaMaterial.getSucursal().getIdSucursal());
/* 1196:1279 */     detalleOrden.setProducto(new Producto());
/* 1197:1280 */     detalleOrden.setOrdenSalidaMaterial(ordenSalidaMaterial);
/* 1198:1281 */     detalleOrden.setTraEditarCopia(true);
/* 1199:1283 */     if (agregarDetalle) {
/* 1200:1284 */       if (detalle != null)
/* 1201:     */       {
/* 1202:1285 */         int i = listDetalleOrdenSalidaMaterial.indexOf(detalle);
/* 1203:1286 */         enlazarOrdenesDeFabricacion(detalleOrden, null);
/* 1204:1287 */         ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().add(i + 1, detalleOrden);
/* 1205:1288 */         listDetalleOrdenSalidaMaterial.add(i + 1, detalleOrden);
/* 1206:     */       }
/* 1207:     */       else
/* 1208:     */       {
/* 1209:1292 */         ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().add(detalleOrden);
/* 1210:1293 */         listDetalleOrdenSalidaMaterial.add(detalleOrden);
/* 1211:     */       }
/* 1212:     */     }
/* 1213:1296 */     return detalleOrden;
/* 1214:     */   }
/* 1215:     */   
/* 1216:     */   public void totalizarCantidadPesada(DetalleOrdenSalidaMaterial detalle, String movimiento)
/* 1217:     */     throws AS2Exception
/* 1218:     */   {
/* 1219:1302 */     if (movimiento.equals("DEVOLVER"))
/* 1220:     */     {
/* 1221:1303 */       detalle.setCantidadDevuelta(BigDecimal.ZERO);
/* 1222:1304 */       detalle.setCantidadUnidadInformativaDevolucion(BigDecimal.ZERO);
/* 1223:     */     }
/* 1224:1305 */     else if (movimiento.equals("DESECHO"))
/* 1225:     */     {
/* 1226:1306 */       detalle.setCantidadDesecho(BigDecimal.ZERO);
/* 1227:1307 */       detalle.setCantidadUnidadInformativaDesecho(BigDecimal.ZERO);
/* 1228:     */     }
/* 1229:     */     else
/* 1230:     */     {
/* 1231:1309 */       detalle.setCantidadDespachada(BigDecimal.ZERO);
/* 1232:1310 */       detalle.setCantidadUnidadInformativaDespacho(BigDecimal.ZERO);
/* 1233:     */     }
/* 1234:1313 */     for (LecturaBalanza lb : detalle.getListaLecturaBalanza()) {
/* 1235:1314 */       if (!lb.isEliminado()) {
/* 1236:1314 */         if (lb.getOperacion() == (movimiento.equals("DEVOLVER") ? -1 : movimiento.equals("DESPACHO") ? 1 : 0))
/* 1237:     */         {
/* 1238:1315 */           BigDecimal[] cantidades = this.servicioMarcacionDispositivo.getCantidades(lb);
/* 1239:1316 */           BigDecimal cantidad = cantidades[0];
/* 1240:1317 */           BigDecimal cantidadInformativa = cantidades[1];
/* 1241:1319 */           if (movimiento.equals("DEVOLVER"))
/* 1242:     */           {
/* 1243:1321 */             detalle.setCantidadDevuelta(detalle.getCantidadDevuelta().add(cantidad));
/* 1244:1322 */             if (cantidadInformativa != null) {
/* 1245:1323 */               detalle.setCantidadUnidadInformativaDevolucion(detalle.getCantidadUnidadInformativaDevolucion().add(cantidadInformativa));
/* 1246:     */             }
/* 1247:     */           }
/* 1248:1325 */           else if (movimiento.equals("DESECHO"))
/* 1249:     */           {
/* 1250:1327 */             detalle.setCantidadDesecho(detalle.getCantidadDesecho().add(cantidad));
/* 1251:1328 */             if (cantidadInformativa != null) {
/* 1252:1329 */               detalle.setCantidadUnidadInformativaDesecho(detalle.getCantidadUnidadInformativaDesecho().add(cantidadInformativa));
/* 1253:     */             }
/* 1254:     */           }
/* 1255:     */           else
/* 1256:     */           {
/* 1257:1333 */             detalle.setCantidadDespachada(detalle.getCantidadDespachada().add(cantidad));
/* 1258:1334 */             if (cantidadInformativa != null) {
/* 1259:1335 */               detalle.setCantidadUnidadInformativaDespacho(detalle.getCantidadUnidadInformativaDespacho().add(cantidadInformativa));
/* 1260:     */             }
/* 1261:     */           }
/* 1262:     */         }
/* 1263:     */       }
/* 1264:     */     }
/* 1265:     */   }
/* 1266:     */   
/* 1267:     */   public void actulizarBodega(DetalleOrdenSalidaMaterial detalleOrden, Bodega bodega, int idSucursal)
/* 1268:     */   {
/* 1269:1344 */     if (detalleOrden.getBodega() == null)
/* 1270:     */     {
/* 1271:1345 */       Bodega bodegaTrabajo = this.servicioProducto.obtenerBodegaTrabajoProducto(detalleOrden.getProducto(), Integer.valueOf(idSucursal));
/* 1272:1346 */       if ((detalleOrden.getProducto() != null) && (bodegaTrabajo != null)) {
/* 1273:1347 */         detalleOrden.setBodega(bodegaTrabajo);
/* 1274:     */       } else {
/* 1275:1349 */         detalleOrden.setBodega(bodega);
/* 1276:     */       }
/* 1277:     */     }
/* 1278:     */   }
/* 1279:     */   
/* 1280:     */   public void guardarLogistica(OrdenSalidaMaterial ordenSalidaMaterial)
/* 1281:     */   {
/* 1282:1356 */     ordenSalidaMaterial.setEstado(Estado.REALIZADO_LOGISTICA);
/* 1283:1357 */     this.ordenSalidaMaterialDao.guardar(ordenSalidaMaterial);
/* 1284:     */   }
/* 1285:     */   
/* 1286:     */   public void aprobarDesaprobarOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial, boolean aprobar)
/* 1287:     */   {
/* 1288:1362 */     ordenSalidaMaterial.setAprobado(aprobar);
/* 1289:1363 */     this.ordenSalidaMaterialDao.guardar(ordenSalidaMaterial);
/* 1290:     */   }
/* 1291:     */   
/* 1292:     */   public OrdenSalidaMaterial copiarOrdenSalidaMaterial(int idOrdenSalidaMaterial, Sucursal sucursal)
/* 1293:     */   {
/* 1294:1368 */     OrdenSalidaMaterial ordenSalidaMaterialACopiar = cargarDetalle(idOrdenSalidaMaterial);
/* 1295:1369 */     OrdenSalidaMaterial ordenSalidaMaterial = new OrdenSalidaMaterial();
/* 1296:1370 */     ordenSalidaMaterial.setIdOrganizacion(ordenSalidaMaterialACopiar.getIdOrganizacion());
/* 1297:1371 */     ordenSalidaMaterial.setSucursal(sucursal);
/* 1298:1372 */     ordenSalidaMaterial.setEstado(Estado.ELABORADO);
/* 1299:1373 */     ordenSalidaMaterial.setFecha(new Date());
/* 1300:1374 */     ordenSalidaMaterial.setDocumento(ordenSalidaMaterialACopiar.getDocumento());
/* 1301:1375 */     ordenSalidaMaterial.setDescripcion(ordenSalidaMaterialACopiar.getDescripcion());
/* 1302:1376 */     ordenSalidaMaterial.setIndicadorTransferencia(ordenSalidaMaterialACopiar.isIndicadorTransferencia());
/* 1303:1377 */     ordenSalidaMaterial.setTipoCicloProduccionEnum(ordenSalidaMaterialACopiar.getTipoCicloProduccionEnum());
/* 1304:1378 */     for (DetalleOrdenSalidaMaterial dosm : ordenSalidaMaterialACopiar.getListaDetalleOrdenSalidaMaterial())
/* 1305:     */     {
/* 1306:1379 */       DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial = new DetalleOrdenSalidaMaterial();
/* 1307:1380 */       detalleOrdenSalidaMaterial.setCantidad(dosm.getCantidad());
/* 1308:1381 */       detalleOrdenSalidaMaterial.setDescripcion(dosm.getDescripcion());
/* 1309:1382 */       detalleOrdenSalidaMaterial.setDestinoCosto(dosm.getDestinoCosto());
/* 1310:1383 */       detalleOrdenSalidaMaterial.setIdSucursal(sucursal.getId());
/* 1311:1384 */       detalleOrdenSalidaMaterial.setLote(dosm.getLote());
/* 1312:1385 */       detalleOrdenSalidaMaterial.setBodega(dosm.getBodega());
/* 1313:1386 */       detalleOrdenSalidaMaterial.setOrdenSalidaMaterial(ordenSalidaMaterial);
/* 1314:1387 */       detalleOrdenSalidaMaterial.setProducto(dosm.getProducto());
/* 1315:1388 */       detalleOrdenSalidaMaterial.setIndicadorConsumoDirecto(dosm.getProducto().isIndicadorConsumoDirecto());
/* 1316:1389 */       detalleOrdenSalidaMaterial.setUnidad(dosm.getUnidad());
/* 1317:1390 */       ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().add(detalleOrdenSalidaMaterial);
/* 1318:     */     }
/* 1319:1392 */     return ordenSalidaMaterial;
/* 1320:     */   }
/* 1321:     */   
/* 1322:     */   public List<OrdenSalidaMaterial> getConsultaOrdenSalidaMaterial(Date fechaHasta, TipoCicloProduccionEnum tipoCiclo, Estado estado, boolean transferencia)
/* 1323:     */   {
/* 1324:1398 */     return this.ordenSalidaMaterialDao.getConsultaOrdenSalidaMaterial(fechaHasta, tipoCiclo, estado, transferencia);
/* 1325:     */   }
/* 1326:     */   
/* 1327:     */   public List<OrdenSalidaMaterial> getOrdenSalidaMaterialPorOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 1328:     */   {
/* 1329:1403 */     return this.ordenSalidaMaterialDao.getOrdenSalidaMaterialPorOrdenFabricacion(ordenFabricacion);
/* 1330:     */   }
/* 1331:     */   
/* 1332:     */   public List<OrdenFabricacionOrdenSalidaMaterial> getOrdenFabricacionOrdenSalidaMaterialPorOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 1333:     */   {
/* 1334:1409 */     return this.ordenSalidaMaterialDao.getOrdenFabricacionOrdenSalidaMaterialPorOrdenSalidaMaterial(ordenSalidaMaterial);
/* 1335:     */   }
/* 1336:     */   
/* 1337:     */   public List<OrdenSalidaMaterial> getOrdenSalidaMaterialPorAprobar(int idOrganizacion, Usuario usuarioEnSesion, Integer idOrdenSalidaMaterial, String numero, TipoCicloProduccionEnum tipoCicloProduccionEnum, String sucursal, Estado estado, String descripcion)
/* 1338:     */     throws AS2Exception
/* 1339:     */   {
/* 1340:1416 */     List<EntidadUsuario> listaSuperiores = this.servicioUsuario.buscarJerarquiaInmediata(usuarioEnSesion.getIdUsuario(), true, DocumentoBase.ORDEN_SALIDA_MATERIAL);
/* 1341:     */     
/* 1342:1418 */     List<EntidadUsuario> listaSubordinados = this.servicioUsuario.buscarJerarquiaInmediata(usuarioEnSesion.getIdUsuario(), false, DocumentoBase.ORDEN_SALIDA_MATERIAL);
/* 1343:     */     
/* 1344:1420 */     return this.ordenSalidaMaterialDao.getOrdenSalidaMaterialPorAprobar(idOrganizacion, usuarioEnSesion, idOrdenSalidaMaterial, listaSuperiores, listaSubordinados, numero, tipoCicloProduccionEnum, sucursal, estado, descripcion);
/* 1345:     */   }
/* 1346:     */   
/* 1347:     */   public void enlazarOrdenesDeFabricacion(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial, List<OrdenFabricacion> listaOrdenFabricacion)
/* 1348:     */   {
/* 1349:     */     List<OrdenFabricacion> listaOrdenFabricacionPorOSM;
/* 1350:1427 */     if ((detalleOrdenSalidaMaterial != null) && (listaOrdenFabricacion == null))
/* 1351:     */     {
/* 1352:1429 */       listaOrdenFabricacionPorOSM = this.servicioOrdenFabricacion.getOrdenFabricacionPorOrdenSalidaMaterial(detalleOrdenSalidaMaterial.getOrdenSalidaMaterial());
/* 1353:1430 */       for (OrdenFabricacion ordenFabricacion : listaOrdenFabricacionPorOSM)
/* 1354:     */       {
/* 1355:1431 */         DetalleOrdenSalidaMaterialOrdenFabricacion dosmof = new DetalleOrdenSalidaMaterialOrdenFabricacion();
/* 1356:1432 */         dosmof.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1357:1433 */         dosmof.setIdSucursal(AppUtil.getSucursal().getId());
/* 1358:1434 */         dosmof.setDetalleOrdenSalidaMaterial(detalleOrdenSalidaMaterial);
/* 1359:1435 */         dosmof.setOrdenFabricacion(ordenFabricacion);
/* 1360:1436 */         dosmof.setCantidad(detalleOrdenSalidaMaterial.getCantidadDespachada());
/* 1361:1437 */         dosmof.setCantidadDesecho(detalleOrdenSalidaMaterial.getCantidadDesecho());
/* 1362:1438 */         dosmof.setCantidadUtilizada(detalleOrdenSalidaMaterial.getCantidadUtilizada());
/* 1363:1439 */         dosmof.setCantidadSegunFabricacion(detalleOrdenSalidaMaterial.getCantidadSegunFabricacion());
/* 1364:1440 */         detalleOrdenSalidaMaterial.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().add(dosmof);
/* 1365:     */       }
/* 1366:     */     }
/* 1367:1443 */     if ((detalleOrdenSalidaMaterial != null) && (listaOrdenFabricacion != null)) {
/* 1368:1444 */       for (OrdenFabricacion ordenFabricacion : listaOrdenFabricacion)
/* 1369:     */       {
/* 1370:1445 */         DetalleOrdenSalidaMaterialOrdenFabricacion dosmof = new DetalleOrdenSalidaMaterialOrdenFabricacion();
/* 1371:1446 */         dosmof.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1372:1447 */         dosmof.setIdSucursal(AppUtil.getSucursal().getId());
/* 1373:1448 */         dosmof.setDetalleOrdenSalidaMaterial(detalleOrdenSalidaMaterial);
/* 1374:1449 */         dosmof.setOrdenFabricacion(ordenFabricacion);
/* 1375:1450 */         dosmof.setCantidad(detalleOrdenSalidaMaterial.getCantidadDespachada());
/* 1376:1451 */         dosmof.setCantidadDesecho(detalleOrdenSalidaMaterial.getCantidadDesecho());
/* 1377:1452 */         dosmof.setCantidadUtilizada(detalleOrdenSalidaMaterial.getCantidadUtilizada());
/* 1378:1453 */         dosmof.setCantidadSegunFabricacion(detalleOrdenSalidaMaterial.getCantidadSegunFabricacion());
/* 1379:1454 */         detalleOrdenSalidaMaterial.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().add(dosmof);
/* 1380:     */       }
/* 1381:     */     }
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   public void agregarDetalleDesdeCodigoCabecera(OrdenSalidaMaterial ordenSalidaMaterial, DetalleOrdenSalidaMaterial detalleOrden)
/* 1385:     */     throws ExcepcionAS2, AS2Exception
/* 1386:     */   {
/* 1387:1465 */     if (detalleOrden.getBodega() == null)
/* 1388:     */     {
/* 1389:1466 */       detalleOrden.setEliminado(true);
/* 1390:1467 */       throw new ExcepcionAS2("msg_info_seleccionar_bodega", "");
/* 1391:     */     }
/* 1392:1469 */     if ((detalleOrden.getProducto().isIndicadorLote()) && (detalleOrden.getLote() == null))
/* 1393:     */     {
/* 1394:1470 */       detalleOrden.setEliminado(true);
/* 1395:     */       
/* 1396:1472 */       throw new ExcepcionAS2("msg_producto_indicador_lote_sin_lote", ": " + detalleOrden.getProducto().getCodigo() + " " + detalleOrden.getProducto().getNombre());
/* 1397:     */     }
/* 1398:1475 */     BigDecimal saldo = BigDecimal.ZERO;
/* 1399:1476 */     if (detalleOrden.getProducto().isIndicadorLote())
/* 1400:     */     {
/* 1401:1477 */       saldo = this.servicioProducto.getSaldoLote(detalleOrden.getProducto().getId(), detalleOrden.getBodega().getId(), detalleOrden
/* 1402:1478 */         .getLote().getId(), ordenSalidaMaterial.getFecha());
/* 1403:1479 */       if (saldo.compareTo(BigDecimal.ZERO) <= 0)
/* 1404:     */       {
/* 1405:1480 */         detalleOrden.setEliminado(true);
/* 1406:     */         
/* 1407:1482 */         String mensaje = " :" + detalleOrden.getBodega().getNombre() + " Producto: " + detalleOrden.getProducto().getNombre() + " Lote:" + detalleOrden.getLote().getCodigo() + " (" + BigDecimal.ZERO + ">" + saldo.toString() + ")";
/* 1408:1483 */         throw new ExcepcionAS2Inventario("msg_info_inventario_0001", mensaje);
/* 1409:     */       }
/* 1410:     */     }
/* 1411:     */     else
/* 1412:     */     {
/* 1413:1486 */       saldo = this.servicioProducto.getSaldo(detalleOrden.getProducto().getId(), detalleOrden.getBodega().getId(), ordenSalidaMaterial.getFecha());
/* 1414:1487 */       if (saldo.compareTo(BigDecimal.ZERO) < 0)
/* 1415:     */       {
/* 1416:1488 */         detalleOrden.setEliminado(true);
/* 1417:     */         
/* 1418:1490 */         String mensaje = " :" + detalleOrden.getBodega().getNombre() + " Producto: " + detalleOrden.getProducto().getNombre() + " (" + BigDecimal.ZERO + ">" + saldo.toString() + ")";
/* 1419:1491 */         throw new ExcepcionAS2Inventario("msg_info_inventario_0001", mensaje);
/* 1420:     */       }
/* 1421:     */     }
/* 1422:1494 */     detalleOrden.setCantidadDespachada(FuncionesUtiles.redondearBigDecimal(saldo, 2));
/* 1423:1495 */     detalleOrden.setCantidadUnidadInformativaDespacho(FuncionesUtiles.redondearBigDecimal(saldo, 2));
/* 1424:1496 */     detalleOrden.setIndicadorAutomatico(true);
/* 1425:1497 */     detalleOrden.setOrdenSalidaMaterial(ordenSalidaMaterial);
/* 1426:     */     
/* 1427:     */ 
/* 1428:     */ 
/* 1429:1501 */     this.detalleOrdenSalidaMaterialDao.guardar(detalleOrden);
/* 1430:     */     
/* 1431:1503 */     crearMovimientoInventario(ordenSalidaMaterial, detalleOrden.getProducto());
/* 1432:     */   }
/* 1433:     */   
/* 1434:     */   private void repartirOrdenesFabricacionNoDirecto(OrdenSalidaMaterial ordenSalidaMaterial)
/* 1435:     */   {
/* 1436:     */     Map<String, DetalleOrdenSalidaMaterialOrdenFabricacion> mapaDetalleOrdenSalidaMaterialOrdenFabricacion;
/* 1437:1509 */     if (ordenSalidaMaterial.getTipoCicloProduccionEnum().equals(TipoCicloProduccionEnum.CICLO_CORTO))
/* 1438:     */     {
/* 1439:1512 */       mapaDetalleOrdenSalidaMaterialOrdenFabricacion = new HashMap();
/* 1440:1513 */       for (DetalleOrdenSalidaMaterial detalleOSM : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/* 1441:1514 */         if (!detalleOSM.isEliminado()) {
/* 1442:1515 */           for (DetalleOrdenSalidaMaterialOrdenFabricacion detalleOSMOF : detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion()) {
/* 1443:1516 */             if ((detalleOSMOF.getOrdenFabricacion().getOrdenFabricacionPadre() != null) && 
/* 1444:1517 */               (!detalleOSMOF.getOrdenFabricacion().getOrdenFabricacionPadre().isIndicadorDirecto()))
/* 1445:     */             {
/* 1446:1518 */               detalleOSMOF.setEliminado(true);
/* 1447:1519 */               String key = detalleOSMOF.getOrdenFabricacion().getId() + "~" + detalleOSMOF.getDetalleOrdenSalidaMaterial().getId();
/* 1448:1520 */               mapaDetalleOrdenSalidaMaterialOrdenFabricacion.put(key, detalleOSMOF);
/* 1449:     */             }
/* 1450:     */           }
/* 1451:     */         }
/* 1452:     */       }
/* 1453:1527 */       for (??? = ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().iterator(); ???.hasNext();)
/* 1454:     */       {
/* 1455:1527 */         detalleOSM = (DetalleOrdenSalidaMaterial)???.next();
/* 1456:1528 */         if (!detalleOSM.isEliminado()) {
/* 1457:1529 */           for (OrdenFabricacionOrdenSalidaMaterial ofosm : ordenSalidaMaterial.getListaOrdenFabricacionOrdenSalidaMaterial()) {
/* 1458:1530 */             if ((ofosm.getOrdenFabricacion().getOrdenFabricacionPadre() != null) && 
/* 1459:1531 */               (!ofosm.getOrdenFabricacion().getOrdenFabricacionPadre().isIndicadorDirecto()))
/* 1460:     */             {
/* 1461:1532 */               String key = ofosm.getOrdenFabricacion().getId() + "~" + detalleOSM.getId();
/* 1462:1533 */               DetalleOrdenSalidaMaterialOrdenFabricacion detalleOSMOF = (DetalleOrdenSalidaMaterialOrdenFabricacion)mapaDetalleOrdenSalidaMaterialOrdenFabricacion.get(key);
/* 1463:1534 */               if (detalleOSMOF == null)
/* 1464:     */               {
/* 1465:1535 */                 detalleOSMOF = new DetalleOrdenSalidaMaterialOrdenFabricacion();
/* 1466:1536 */                 detalleOSMOF.setIdOrganizacion(ordenSalidaMaterial.getIdOrganizacion());
/* 1467:1537 */                 detalleOSMOF.setIdSucursal(detalleOSM.getIdSucursal());
/* 1468:1538 */                 detalleOSMOF.setDetalleOrdenSalidaMaterial(detalleOSM);
/* 1469:1539 */                 detalleOSMOF.setOrdenFabricacion(ofosm.getOrdenFabricacion());
/* 1470:1540 */                 detalleOSM.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().add(detalleOSMOF);
/* 1471:     */               }
/* 1472:1542 */               detalleOSMOF.setCantidad(ofosm.getOrdenFabricacion().getCantidad());
/* 1473:1543 */               detalleOSMOF.setEliminado(false);
/* 1474:1544 */               mapaDetalleOrdenSalidaMaterialOrdenFabricacion.put(key, detalleOSMOF);
/* 1475:     */             }
/* 1476:     */           }
/* 1477:     */         }
/* 1478:     */       }
/* 1479:     */     }
/* 1480:     */     DetalleOrdenSalidaMaterial detalleOSM;
/* 1481:     */   }
/* 1482:     */   
/* 1483:     */   public void eliminarDetalleOrdenSalidaMaterial(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial)
/* 1484:     */   {
/* 1485:     */     try
/* 1486:     */     {
/* 1487:1562 */       MovimientoInventario movimientoInventario = null;
/* 1488:1564 */       if (detalleOrdenSalidaMaterial.getOrdenSalidaMaterial().getId() != 0)
/* 1489:     */       {
/* 1490:1566 */         movimientoInventario = this.ordenSalidaMaterialDao.obtenerSalidaMaterialPorOrdenSalida(detalleOrdenSalidaMaterial.getOrdenSalidaMaterial());
/* 1491:1567 */         if (movimientoInventario != null) {
/* 1492:1568 */           movimientoInventario = this.servicioMovimientoInventario.cargarDetalle(movimientoInventario.getId(), detalleOrdenSalidaMaterial
/* 1493:1569 */             .getProducto());
/* 1494:     */         }
/* 1495:     */       }
/* 1496:1574 */       HashMap<String, String> filters = new HashMap();
/* 1497:1575 */       filters.put("detalleMovimientoInventario.detalleOrdenSalidaMaterial.idDetalleOrdenSalidaMaterial", "=" + detalleOrdenSalidaMaterial
/* 1498:1576 */         .getId());
/* 1499:1577 */       List<InventarioProducto> lista = this.inventarioProductoDao.obtenerListaCombo(null, false, filters);
/* 1500:     */       
/* 1501:1579 */       InventarioProducto inventarioProducto = null;
/* 1502:1580 */       if (!lista.isEmpty()) {
/* 1503:1581 */         inventarioProducto = this.inventarioProductoDao.cargarDetalle(((InventarioProducto)lista.get(0)).getId());
/* 1504:     */       }
/* 1505:1585 */       if (inventarioProducto != null)
/* 1506:     */       {
/* 1507:1587 */         this.servicioVerificadorInventario.actualizarSaldoDetalle(inventarioProducto.getDetalleMovimientoInventario(), true, movimientoInventario);
/* 1508:     */         
/* 1509:     */ 
/* 1510:1590 */         this.inventarioProductoDao.eliminarInventarioProductoDetalleMovimientoInventario(inventarioProducto.getDetalleMovimientoInventario());
/* 1511:     */         
/* 1512:     */ 
/* 1513:1593 */         inventarioProducto.getDetalleMovimientoInventario().setEliminado(true);
/* 1514:1594 */         this.detalleMovimientoInventarioDao.guardar(inventarioProducto.getDetalleMovimientoInventario());
/* 1515:1597 */         for (DetalleOrdenSalidaMaterialOrdenFabricacion detalleOrdenSalidaMaterialOrdenFabricacion : detalleOrdenSalidaMaterial.getListaDetalleOrdenSalidaMaterialOrdenFabricacion())
/* 1516:     */         {
/* 1517:1598 */           detalleOrdenSalidaMaterialOrdenFabricacion.setEliminado(true);
/* 1518:1599 */           this.detalleOrdenSalidaMaterialOrdenFabricacionDao.guardar(detalleOrdenSalidaMaterialOrdenFabricacion);
/* 1519:     */         }
/* 1520:1603 */         detalleOrdenSalidaMaterial.setEliminado(true);
/* 1521:1604 */         this.detalleOrdenSalidaMaterialDao.guardar(detalleOrdenSalidaMaterial);
/* 1522:     */       }
/* 1523:     */     }
/* 1524:     */     catch (Exception e)
/* 1525:     */     {
/* 1526:1608 */       this.context.setRollbackOnly();
/* 1527:1609 */       e.printStackTrace();
/* 1528:     */     }
/* 1529:     */   }
/* 1530:     */   
/* 1531:     */   public List<String> ordenSalidaMaterialEnConsumoBodega(OrdenSalidaMaterial ordenSalidaMaterial)
/* 1532:     */   {
/* 1533:1615 */     return this.ordenSalidaMaterialDao.ordenSalidaMaterialEnConsumoBodega(ordenSalidaMaterial);
/* 1534:     */   }
/* 1535:     */   
/* 1536:     */   public void detalleSinDespachoConsumosDirectos(OrdenSalidaMaterial ordenSalidaMaterial)
/* 1537:     */     throws AS2Exception
/* 1538:     */   {
/* 1539:1619 */     List<DetalleOrdenSalidaMaterial> listaDetalleComsumo = new ArrayList();
/* 1540:1620 */     BigDecimal totalCantidadDespachada = BigDecimal.ZERO;
/* 1541:1621 */     for (DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/* 1542:1622 */       if ((!detalleOrdenSalidaMaterial.isEliminado()) && (!detalleOrdenSalidaMaterial.isIndicadorConsumoDirecto()))
/* 1543:     */       {
/* 1544:1623 */         listaDetalleComsumo.add(detalleOrdenSalidaMaterial);
/* 1545:1624 */         totalCantidadDespachada = totalCantidadDespachada.add(detalleOrdenSalidaMaterial.getCantidadDespachada());
/* 1546:     */       }
/* 1547:     */     }
/* 1548:1627 */     if ((listaDetalleComsumo.size() > 0) && (totalCantidadDespachada.compareTo(BigDecimal.ZERO) == 0)) {
/* 1549:1628 */       throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterialImpl.MENSAJE_ERROR_CANTIDAD_DESPACHADA_IGUAL_CERO", new String[] { "" });
/* 1550:     */     }
/* 1551:     */   }
/* 1552:     */   
/* 1553:     */   public void validarCantidadDesecho(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial, BigDecimal pesoNetoDesechoDevolucion, String movimiento, TipoCicloProduccionEnum cicloProduccion)
/* 1554:     */     throws AS2Exception
/* 1555:     */   {
/* 1556:1634 */     if (cicloProduccion != null)
/* 1557:     */     {
/* 1558:1636 */       if (cicloProduccion.equals(TipoCicloProduccionEnum.CICLO_CORTO))
/* 1559:     */       {
/* 1560:1637 */         if ((movimiento.equals("DESECHO")) && (detalleOrdenSalidaMaterial.getProducto().isIndicadorConsumoDirecto()) && 
/* 1561:1638 */           (detalleOrdenSalidaMaterial.getCantidad().compareTo(pesoNetoDesechoDevolucion) < 0)) {
/* 1562:1641 */           throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterialImpl.MENSAJE_ERROR_CANTIDAD_DESECHO_SUPERA", new String[] { movimiento, "" + detalleOrdenSalidaMaterial.getCantidadDesecho(), "" + detalleOrdenSalidaMaterial.getCantidad(), "" + detalleOrdenSalidaMaterial.getProducto().getCodigo() });
/* 1563:     */         }
/* 1564:     */       }
/* 1565:1644 */       else if ((movimiento.equals("DESECHO")) && (detalleOrdenSalidaMaterial.getCantidadDespachada().compareTo(pesoNetoDesechoDevolucion) < 0)) {
/* 1566:1647 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterialImpl.MENSAJE_ERROR_CANTIDAD_DESECHO_SUPERA", new String[] { movimiento, "" + detalleOrdenSalidaMaterial.getCantidadDesecho(), "" + detalleOrdenSalidaMaterial.getCantidadDespachada(), "" + detalleOrdenSalidaMaterial.getProducto().getCodigo() });
/* 1567:     */       }
/* 1568:     */     }
/* 1569:     */     else
/* 1570:     */     {
/* 1571:1652 */       if ((movimiento.equals("DESECHO")) && 
/* 1572:1653 */         (detalleOrdenSalidaMaterial.getCantidadDespachada().compareTo(pesoNetoDesechoDevolucion.add(detalleOrdenSalidaMaterial.getCantidadDesecho())) < 0)) {
/* 1573:1656 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterialImpl.MENSAJE_ERROR_CANTIDAD_DESECHO_SUPERA", new String[] { movimiento, "" + pesoNetoDesechoDevolucion.add(detalleOrdenSalidaMaterial.getCantidadDesecho()), "" + detalleOrdenSalidaMaterial.getCantidadDespachada(), "" + detalleOrdenSalidaMaterial.getProducto().getCodigo() });
/* 1574:     */       }
/* 1575:1659 */       if ((movimiento.equals("DEVOLVER")) && 
/* 1576:1660 */         (detalleOrdenSalidaMaterial.getCantidadDespachada().compareTo(pesoNetoDesechoDevolucion.add(detalleOrdenSalidaMaterial.getCantidadDevuelta())) < 0)) {
/* 1577:1663 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterialImpl.MENSAJE_ERROR_CANTIDAD_DESECHO_SUPERA", new String[] { movimiento, "" + pesoNetoDesechoDevolucion.add(detalleOrdenSalidaMaterial.getCantidadDevuelta()), "" + detalleOrdenSalidaMaterial.getCantidadDespachada(), "" + detalleOrdenSalidaMaterial.getProducto().getCodigo() });
/* 1578:     */       }
/* 1579:     */     }
/* 1580:     */   }
/* 1581:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterialImpl
 * JD-Core Version:    0.7.0.1
 */