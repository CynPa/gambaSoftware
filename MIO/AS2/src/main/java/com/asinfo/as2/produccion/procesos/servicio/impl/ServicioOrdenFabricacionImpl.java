/*    1:     */ package com.asinfo.as2.produccion.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.dao.GenericoDao;
/*    4:     */ import com.asinfo.as2.dao.MovimientoInventarioDao;
/*    5:     */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*    6:     */ import com.asinfo.as2.dao.produccion.RutaFabricacionDao;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*    9:     */ import com.asinfo.as2.entities.Bodega;
/*   10:     */ import com.asinfo.as2.entities.BodegaTrabajoProductoSucursal;
/*   11:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   12:     */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   13:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*   14:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterialOrdenFabricacion;
/*   15:     */ import com.asinfo.as2.entities.Documento;
/*   16:     */ import com.asinfo.as2.entities.Lote;
/*   17:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   18:     */ import com.asinfo.as2.entities.OrdenFabricacionMaquina;
/*   19:     */ import com.asinfo.as2.entities.OrdenFabricacionOrdenSalidaMaterial;
/*   20:     */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   21:     */ import com.asinfo.as2.entities.Organizacion;
/*   22:     */ import com.asinfo.as2.entities.Producto;
/*   23:     */ import com.asinfo.as2.entities.ProductoMaterial;
/*   24:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   25:     */ import com.asinfo.as2.entities.Sucursal;
/*   26:     */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacion;
/*   27:     */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
/*   28:     */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterialMezcla;
/*   29:     */ import com.asinfo.as2.entities.produccion.MezclaProducto;
/*   30:     */ import com.asinfo.as2.entities.produccion.OperacionOrdenFabricacion;
/*   31:     */ import com.asinfo.as2.entities.produccion.OperacionProduccion;
/*   32:     */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   33:     */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacionSucursal;
/*   34:     */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*   35:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   36:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   37:     */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*   38:     */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*   39:     */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*   40:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   41:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   42:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   43:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   44:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   45:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*   46:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*   47:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*   48:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*   49:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*   50:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*   51:     */ import com.asinfo.as2.util.AppUtil;
/*   52:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   53:     */ import com.asinfo.as2.utils.NodoArbol;
/*   54:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   55:     */ import java.io.PrintStream;
/*   56:     */ import java.math.BigDecimal;
/*   57:     */ import java.math.RoundingMode;
/*   58:     */ import java.util.ArrayList;
/*   59:     */ import java.util.Date;
/*   60:     */ import java.util.HashMap;
/*   61:     */ import java.util.Iterator;
/*   62:     */ import java.util.List;
/*   63:     */ import java.util.Map;
/*   64:     */ import java.util.Set;
/*   65:     */ import javax.ejb.EJB;
/*   66:     */ import javax.ejb.SessionContext;
/*   67:     */ import javax.ejb.Stateless;
/*   68:     */ import javax.ejb.TransactionAttribute;
/*   69:     */ import javax.ejb.TransactionAttributeType;
/*   70:     */ import org.primefaces.model.DefaultTreeNode;
/*   71:     */ import org.primefaces.model.TreeNode;
/*   72:     */ 
/*   73:     */ @Stateless
/*   74:     */ public class ServicioOrdenFabricacionImpl
/*   75:     */   extends AbstractServicioAS2Financiero
/*   76:     */   implements ServicioOrdenFabricacion
/*   77:     */ {
/*   78:     */   private static final long serialVersionUID = 1L;
/*   79:     */   @EJB
/*   80:     */   private OrdenFabricacionDao ordenFabricacionDao;
/*   81:     */   @EJB
/*   82:     */   private ServicioPeriodo servicioPeriodo;
/*   83:     */   @EJB
/*   84:     */   private ServicioProducto servicioProducto;
/*   85:     */   @EJB
/*   86:     */   private ServicioSecuencia servicioSecuencia;
/*   87:     */   @EJB
/*   88:     */   private ServicioDocumento servicioDocumento;
/*   89:     */   @EJB
/*   90:     */   private ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*   91:     */   @EJB
/*   92:     */   private GenericoDao<DetalleOrdenFabricacion> detalleOrdenFabricacionDao;
/*   93:     */   @EJB
/*   94:     */   private RutaFabricacionDao rutaFabricacionDao;
/*   95:     */   @EJB
/*   96:     */   private GenericoDao<OperacionOrdenFabricacion> operacionOrdenFabricacionDao;
/*   97:     */   @EJB
/*   98:     */   private MovimientoInventarioDao movimientoInventarioDao;
/*   99:     */   @EJB
/*  100:     */   private ServicioMovimientoInventario servicioMovimientoInventario;
/*  101:     */   @EJB
/*  102:     */   private ServicioCosteo servicioCosteo;
/*  103:     */   @EJB
/*  104:     */   private GenericoDao<DetalleOrdenFabricacionMaterial> detalleMaterialOrdenFabricacionDao;
/*  105:     */   @EJB
/*  106:     */   private GenericoDao<DetalleOrdenSalidaMaterialOrdenFabricacion> detalleOrdenSalidaMaterialOrdenFabricacionDao;
/*  107:     */   @EJB
/*  108:     */   private GenericoDao<DetalleOrdenSalidaMaterial> detalleOrdenSalidaMaterialDao;
/*  109:     */   @EJB
/*  110:     */   private ServicioVerificadorInventario servicioVerificadorInventario;
/*  111:     */   @EJB
/*  112:     */   private GenericoDao<OrdenFabricacionOrdenSalidaMaterial> ordenFabricacionOrdenSalidaMaterialDao;
/*  113:     */   @EJB
/*  114:     */   private GenericoDao<OrdenFabricacionMaquina> ordenFabricacionMaquinaDao;
/*  115:     */   @EJB
/*  116:     */   private GenericoDao<DetalleOrdenFabricacionMaterialMezcla> detalleOrdenFabricacionMaterialMezclaDao;
/*  117:     */   @EJB
/*  118:     */   private GenericoDao<ProductoMaterial> productoMaterialDao;
/*  119:     */   private TreeNode root;
/*  120:     */   
/*  121:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  122:     */   public void guardar(OrdenFabricacion ordenFabricacion)
/*  123:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  124:     */   {
/*  125: 147 */     validar(ordenFabricacion);
/*  126:     */     try
/*  127:     */     {
/*  128: 150 */       cargarSecuencia(ordenFabricacion);
/*  129: 152 */       for (DetalleOrdenFabricacion dofd : ordenFabricacion.getDetalleOrdenFabricacion())
/*  130:     */       {
/*  131: 153 */         if (dofd.getCantidad().compareTo(BigDecimal.ZERO) == 0) {
/*  132: 154 */           dofd.setEliminado(true);
/*  133:     */         }
/*  134: 156 */         this.detalleOrdenFabricacionDao.guardar(dofd);
/*  135:     */       }
/*  136: 158 */       for (OperacionOrdenFabricacion operacion : ordenFabricacion.getListaOperacionProduccion())
/*  137:     */       {
/*  138: 159 */         System.out.println("ENTROOO OPERACION" + operacion.getHorasHombre() + " " + operacion.getHorasMaquina());
/*  139: 160 */         this.operacionOrdenFabricacionDao.guardar(operacion);
/*  140:     */       }
/*  141: 162 */       if (ordenFabricacion.getListaSubordenes().isEmpty()) {
/*  142: 163 */         ordenFabricacion.setIndicadorHoja(true);
/*  143:     */       } else {
/*  144: 165 */         ordenFabricacion.setIndicadorHoja(false);
/*  145:     */       }
/*  146: 167 */       for (OrdenFabricacionMaquina ordenFabricacionMaquina : ordenFabricacion.getListaOrdenFabricacionMaquina()) {
/*  147: 168 */         this.ordenFabricacionMaquinaDao.guardar(ordenFabricacionMaquina);
/*  148:     */       }
/*  149: 172 */       int contador = getCountSubordenesFabricacion(ordenFabricacion, 0);
/*  150:     */       
/*  151:     */ 
/*  152: 175 */       guardarSubordenesRecursivo(ordenFabricacion, null, contador);
/*  153:     */       
/*  154:     */ 
/*  155: 178 */       this.ordenFabricacionDao.guardar(ordenFabricacion);
/*  156:     */     }
/*  157:     */     catch (ExcepcionAS2Financiero e)
/*  158:     */     {
/*  159: 181 */       this.context.setRollbackOnly();
/*  160: 182 */       throw e;
/*  161:     */     }
/*  162:     */     catch (ExcepcionAS2 e)
/*  163:     */     {
/*  164: 184 */       this.context.setRollbackOnly();
/*  165: 185 */       throw e;
/*  166:     */     }
/*  167:     */     catch (Exception e)
/*  168:     */     {
/*  169: 187 */       e.printStackTrace();
/*  170: 188 */       this.context.setRollbackOnly();
/*  171: 189 */       throw new ExcepcionAS2(e);
/*  172:     */     }
/*  173:     */   }
/*  174:     */   
/*  175:     */   private void guardarSubordenesRecursivo(OrdenFabricacion ordenFabricacion)
/*  176:     */     throws AS2Exception
/*  177:     */   {
/*  178: 194 */     int consecutivo = 1;
/*  179: 195 */     if (ordenFabricacion.isIndicadorDirecto())
/*  180:     */     {
/*  181: 196 */       actualizarDatosSubordenes(ordenFabricacion);
/*  182: 197 */       for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenes())
/*  183:     */       {
/*  184: 198 */         suborden.setIndicadorSuborden(true);
/*  185: 199 */         if (suborden.getListaSubordenes().isEmpty()) {
/*  186: 200 */           suborden.setIndicadorHoja(true);
/*  187:     */         } else {
/*  188: 202 */           suborden.setIndicadorHoja(false);
/*  189:     */         }
/*  190: 204 */         if (!suborden.isEliminado())
/*  191:     */         {
/*  192: 205 */           String numero = ordenFabricacion.getNumero() + "." + consecutivo;
/*  193: 206 */           suborden.setNumero(numero);
/*  194: 207 */           consecutivo++;
/*  195:     */         }
/*  196: 209 */         this.ordenFabricacionDao.guardar(suborden);
/*  197: 210 */         if (!suborden.isEliminado()) {
/*  198: 211 */           guardarSubordenesRecursivo(suborden);
/*  199:     */         }
/*  200:     */       }
/*  201:     */     }
/*  202:     */     else
/*  203:     */     {
/*  204: 215 */       for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenes())
/*  205:     */       {
/*  206: 216 */         if (!suborden.isEliminado())
/*  207:     */         {
/*  208: 217 */           String numero = ordenFabricacion.getNumero() + "." + consecutivo;
/*  209: 218 */           suborden.setNumero(numero);
/*  210: 219 */           consecutivo++;
/*  211:     */         }
/*  212: 221 */         this.ordenFabricacionDao.guardar(suborden);
/*  213:     */       }
/*  214:     */     }
/*  215:     */   }
/*  216:     */   
/*  217:     */   private void guardarSubordenesRecursivo(OrdenFabricacion ordenFabricacion, String numeroOrdenFabricacionPrincipal, int consecutivo)
/*  218:     */     throws AS2Exception
/*  219:     */   {
/*  220: 229 */     if (ordenFabricacion.isIndicadorDirecto())
/*  221:     */     {
/*  222: 232 */       numeroOrdenFabricacionPrincipal = numeroOrdenFabricacionPrincipal == null ? ordenFabricacion.getNumero() : numeroOrdenFabricacionPrincipal;
/*  223: 239 */       for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenes()) {
/*  224: 241 */         if (!suborden.isEliminado())
/*  225:     */         {
/*  226: 244 */           List<OrdenFabricacion> listaSuborden = suborden.getListaSubordenes();
/*  227: 245 */           suborden.setIndicadorSuborden(true);
/*  228: 246 */           suborden.setIndicadorHoja((listaSuborden != null) && (!listaSuborden.isEmpty()));
/*  229:     */           
/*  230: 248 */           String numero = numeroOrdenFabricacionPrincipal + "." + consecutivo;
/*  231: 249 */           suborden.setNumero(numero);
/*  232: 250 */           consecutivo--;
/*  233:     */           
/*  234: 252 */           this.ordenFabricacionDao.guardar(suborden);
/*  235: 253 */           suborden.setListaSubordenes(listaSuborden);
/*  236: 254 */           for (OrdenFabricacion so : suborden.getListaSubordenes()) {
/*  237: 255 */             so.setOrdenFabricacionPadre(suborden);
/*  238:     */           }
/*  239: 257 */           guardarSubordenesRecursivo(suborden, numeroOrdenFabricacionPrincipal, consecutivo);
/*  240:     */         }
/*  241:     */       }
/*  242:     */     }
/*  243:     */     else
/*  244:     */     {
/*  245: 263 */       for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenes())
/*  246:     */       {
/*  247: 264 */         if (!suborden.isEliminado())
/*  248:     */         {
/*  249: 265 */           String numero = ordenFabricacion.getNumero() + "." + consecutivo;
/*  250: 266 */           suborden.setNumero(numero);
/*  251: 267 */           consecutivo++;
/*  252:     */         }
/*  253: 269 */         this.ordenFabricacionDao.guardar(suborden);
/*  254:     */       }
/*  255:     */     }
/*  256:     */   }
/*  257:     */   
/*  258:     */   private int getCountSubordenesFabricacion(OrdenFabricacion ordenFabricacion, int contador)
/*  259:     */   {
/*  260: 275 */     if (!ordenFabricacion.getListaSubordenesView().isEmpty())
/*  261:     */     {
/*  262: 276 */       contador++;
/*  263: 277 */       for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenesView()) {
/*  264: 278 */         contador = getCountSubordenesFabricacion(suborden, contador);
/*  265:     */       }
/*  266:     */     }
/*  267: 281 */     return contador;
/*  268:     */   }
/*  269:     */   
/*  270:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  271:     */   public void solicitarMaterial(List<OrdenFabricacion> listaOrdenFabricacion, String descripcion, OrdenSalidaMaterial ordenSalidaMaterial, Date fechaSalidaMaterial, boolean costeoSubOrdenes)
/*  272:     */     throws AS2Exception, ExcepcionAS2Financiero, ExcepcionAS2
/*  273:     */   {
/*  274: 289 */     if (listaOrdenFabricacion.size() == 0) {
/*  275: 290 */       throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_NO_SELECCIONO_ORDEN_FABRICACION_SOLICITAR_MATERIAL", new String[] { "" });
/*  276:     */     }
/*  277:     */     try
/*  278:     */     {
/*  279: 295 */       for (Iterator localIterator1 = listaOrdenFabricacion.iterator(); localIterator1.hasNext();)
/*  280:     */       {
/*  281: 295 */         ordenFabricacion = (OrdenFabricacion)localIterator1.next();
/*  282: 296 */         List<OrdenFabricacion> subordenes = new ArrayList();
/*  283: 297 */         for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenesFabricacion()) {
/*  284: 298 */           if ((!ordenFabricacion.isIndicadorRequiereFormulacion()) || (suborden.isIndicadorHoja()) || (!suborden.isIndicadorFormulada())) {
/*  285: 299 */             subordenes.add(suborden);
/*  286:     */           }
/*  287:     */         }
/*  288: 302 */         ordenFabricacion.setListaSubordenesFabricacion(subordenes);
/*  289: 303 */         if (!ordenFabricacion.isIndicadorDirecto()) {
/*  290: 304 */           solicitarMaterialOrdenFabricacion(subordenes, descripcion, ordenSalidaMaterial, fechaSalidaMaterial, costeoSubOrdenes);
/*  291:     */         }
/*  292:     */       }
/*  293:     */       OrdenFabricacion ordenFabricacion;
/*  294: 309 */       Object lista = new ArrayList();
/*  295: 310 */       for (OrdenFabricacion ordenFabricacion : listaOrdenFabricacion) {
/*  296: 311 */         if (!ordenFabricacion.isIndicadorDirecto())
/*  297:     */         {
/*  298: 312 */           ordenFabricacion.setMaterialesSolicitados(true);
/*  299: 313 */           this.ordenFabricacionDao.guardar(ordenFabricacion);
/*  300:     */         }
/*  301:     */         else
/*  302:     */         {
/*  303: 315 */           ((List)lista).addAll(ordenFabricacion.getListaSubordenesFabricacion());
/*  304: 316 */           ((List)lista).add(ordenFabricacion);
/*  305:     */         }
/*  306:     */       }
/*  307: 321 */       solicitarMaterialOrdenFabricacion((List)lista, descripcion, ordenSalidaMaterial, fechaSalidaMaterial, costeoSubOrdenes);
/*  308:     */     }
/*  309:     */     catch (ExcepcionAS2Financiero e)
/*  310:     */     {
/*  311: 324 */       this.context.setRollbackOnly();
/*  312: 325 */       throw e;
/*  313:     */     }
/*  314:     */     catch (ExcepcionAS2 e)
/*  315:     */     {
/*  316: 327 */       this.context.setRollbackOnly();
/*  317: 328 */       throw e;
/*  318:     */     }
/*  319:     */     catch (AS2Exception e)
/*  320:     */     {
/*  321: 330 */       this.context.setRollbackOnly();
/*  322: 331 */       throw e;
/*  323:     */     }
/*  324:     */     catch (Exception e)
/*  325:     */     {
/*  326: 333 */       e.printStackTrace();
/*  327: 334 */       this.context.setRollbackOnly();
/*  328: 335 */       throw new ExcepcionAS2(e);
/*  329:     */     }
/*  330:     */   }
/*  331:     */   
/*  332:     */   private void solicitarMaterialOrdenFabricacion(List<OrdenFabricacion> listaOrdenFabricacion, String descripcion, OrdenSalidaMaterial ordenSalidaMaterial, Date fechaSalidaMaterial, boolean costeoSubOrdenes)
/*  333:     */     throws AS2Exception, ExcepcionAS2Financiero, ExcepcionAS2
/*  334:     */   {
/*  335:     */     try
/*  336:     */     {
/*  337: 344 */       OrdenFabricacion ordenFabricacionMuestra = null;
/*  338:     */       
/*  339:     */ 
/*  340: 347 */       Map<Integer, Object[]> mapaMateriales = new HashMap();
/*  341: 348 */       List<OrdenFabricacion> listaNuevaOrdenFabricacion = new ArrayList();
/*  342: 349 */       for (Iterator localIterator1 = listaOrdenFabricacion.iterator(); localIterator1.hasNext();)
/*  343:     */       {
/*  344: 349 */         ordenFabricacion = (OrdenFabricacion)localIterator1.next();
/*  345: 350 */         ordenFabricacion = cargarDetalle(ordenFabricacion.getId());
/*  346: 351 */         if (!ordenFabricacion.isMaterialesSolicitados())
/*  347:     */         {
/*  348: 352 */           listaNuevaOrdenFabricacion.add(ordenFabricacion);
/*  349: 353 */           ordenFabricacion.setMaterialesSolicitados(true);
/*  350: 354 */           this.ordenFabricacionDao.guardar(ordenFabricacion);
/*  351: 355 */           ordenFabricacionMuestra = ordenFabricacion;
/*  352:     */           
/*  353: 357 */           NodoArbol<Producto> componentesMateriales = this.servicioProducto.obtenerArbolComponentes(ordenFabricacion.getProducto());
/*  354: 358 */           ((Producto)componentesMateriales.getValor()).setCantidadProducir(ordenFabricacion.getCantidad());
/*  355: 359 */           this.servicioProducto.actualizarCantidadesProducir(componentesMateriales, null);
/*  356: 362 */           for (NodoArbol<Producto> nodoMaterial : componentesMateriales.getHojas())
/*  357:     */           {
/*  358: 363 */             Map<String, Object[]> listaOrdenes = new HashMap();
/*  359: 364 */             Producto material = (Producto)nodoMaterial.getValor();
/*  360: 365 */             BigDecimal cantidad = material.getCantidadProducir().setScale(2, RoundingMode.HALF_UP);
/*  361: 366 */             itemOrden = new Object[2];
/*  362: 367 */             String key = material.getId() + "~" + ordenFabricacion.getId();
/*  363: 368 */             BigDecimal cantidadAnteriorMat = BigDecimal.ZERO;
/*  364: 369 */             if (listaOrdenes.containsKey(key))
/*  365:     */             {
/*  366: 370 */               itemOrden = (Object[])listaOrdenes.get(key);
/*  367: 371 */               cantidadAnteriorMat = (BigDecimal)itemOrden[0];
/*  368:     */             }
/*  369: 373 */             itemOrden[0] = cantidad.add(cantidadAnteriorMat);
/*  370: 374 */             itemOrden[1] = ordenFabricacion;
/*  371: 375 */             Object[] valor = new Object[2];
/*  372: 377 */             if (mapaMateriales.containsKey(Integer.valueOf(material.getId())))
/*  373:     */             {
/*  374: 378 */               valor = (Object[])mapaMateriales.get(Integer.valueOf(material.getId()));
/*  375: 379 */               BigDecimal cantidadAnterior = (BigDecimal)valor[0];
/*  376: 380 */               cantidad = cantidad.add(cantidadAnterior);
/*  377: 381 */               listaOrdenes = (Map)valor[1];
/*  378:     */             }
/*  379: 383 */             listaOrdenes.put(key, itemOrden);
/*  380: 384 */             valor[0] = cantidad;
/*  381: 385 */             valor[1] = listaOrdenes;
/*  382: 386 */             mapaMateriales.put(Integer.valueOf(material.getId()), valor);
/*  383:     */           }
/*  384:     */         }
/*  385:     */       }
/*  386:     */       OrdenFabricacion ordenFabricacion;
/*  387:     */       Object[] itemOrden;
/*  388: 390 */       if (listaNuevaOrdenFabricacion.size() > 0)
/*  389:     */       {
/*  390: 391 */         Object mapaDetalleOrdenSalidaMaterial = new HashMap();
/*  391:     */         Map<String, String> filtros;
/*  392: 392 */         if (ordenSalidaMaterial == null)
/*  393:     */         {
/*  394: 393 */           ordenSalidaMaterial = new OrdenSalidaMaterial();
/*  395: 394 */           ordenSalidaMaterial.setIdOrganizacion(ordenFabricacionMuestra.getIdOrganizacion());
/*  396: 395 */           ordenSalidaMaterial.setSucursal(ordenFabricacionMuestra.getSucursal());
/*  397: 396 */           ordenSalidaMaterial.setFecha(new Date());
/*  398: 397 */           ordenSalidaMaterial.setEstado(Estado.ELABORADO);
/*  399:     */           
/*  400: 399 */           filtros = new HashMap();
/*  401: 400 */           filtros.put("idOrganizacion", String.valueOf(ordenFabricacionMuestra.getIdOrganizacion()));
/*  402: 401 */           filtros.put("documentoBase", DocumentoBase.ORDEN_SALIDA_MATERIAL.toString());
/*  403: 402 */           List<Documento> listaDocumento = this.servicioDocumento.obtenerListaCombo("predeterminado", false, filtros);
/*  404: 403 */           if (listaDocumento.size() > 0) {
/*  405: 404 */             ordenSalidaMaterial.setDocumento((Documento)listaDocumento.get(0));
/*  406:     */           } else {
/*  407: 406 */             throw new AS2Exception("msg_configuracion_documento", new String[] { DocumentoBase.ORDEN_SALIDA_MATERIAL.getNombre() });
/*  408:     */           }
/*  409:     */         }
/*  410:     */         else
/*  411:     */         {
/*  412: 409 */           ordenSalidaMaterial = this.servicioOrdenSalidaMaterial.cargarDetalle(ordenSalidaMaterial.getId());
/*  413: 411 */           for (DetalleOrdenSalidaMaterial detalleOrdenSalida : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/*  414: 412 */             ((Map)mapaDetalleOrdenSalidaMaterial).put(Integer.valueOf(detalleOrdenSalida.getProducto().getId()), detalleOrdenSalida);
/*  415:     */           }
/*  416:     */         }
/*  417: 415 */         ordenSalidaMaterial.setDescripcion(descripcion);
/*  418: 416 */         ordenSalidaMaterial.setFechaSalidaMaterial(fechaSalidaMaterial);
/*  419:     */         
/*  420: 418 */         Iterator<Integer> it = mapaMateriales.keySet().iterator();
/*  421:     */         Object[] itemOrden;
/*  422:     */         DetalleOrdenSalidaMaterial dosm;
/*  423: 419 */         while (it.hasNext())
/*  424:     */         {
/*  425: 420 */           idMaterial = (Integer)it.next();
/*  426: 421 */           Producto material = this.servicioProducto.cargaDetalle(idMaterial.intValue());
/*  427: 422 */           Bodega bodegaTrabajoMaterial = this.servicioProducto.obtenerBodegaTrabajoProducto(material, Integer.valueOf(ordenSalidaMaterial.getSucursal().getId()));
/*  428: 423 */           if (bodegaTrabajoMaterial == null) {
/*  429: 425 */             throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_NO_EXISTE_BODEGA_TRABAJO_SUCURSAL", new String[] {material.getCodigo() + "-" + material.getNombre() });
/*  430:     */           }
/*  431: 427 */           Object[] valorMapa = (Object[])mapaMateriales.get(Integer.valueOf(material.getId()));
/*  432: 428 */           BigDecimal cantidadMaterial = (BigDecimal)valorMapa[0];
/*  433: 429 */           List<Object[]> listaOrdenes = new ArrayList();
/*  434: 430 */           listaOrdenes.addAll(((Map)valorMapa[1]).values());
/*  435: 432 */           for (itemOrden = listaOrdenes.iterator(); itemOrden.hasNext();)
/*  436:     */           {
/*  437: 432 */             itemOrden = (Object[])itemOrden.next();
/*  438: 433 */             OrdenFabricacion ordenFabricacion = (OrdenFabricacion)itemOrden[1];
/*  439: 434 */             HashMap<String, String> filtros = new HashMap();
/*  440: 435 */             filtros.put("material.idProducto", "" + material.getId());
/*  441: 436 */             filtros.put("producto.idProducto", "" + ordenFabricacion.getProducto().getId());
/*  442: 437 */             List<ProductoMaterial> lista = this.productoMaterialDao.obtenerListaCombo(ProductoMaterial.class, "idProductoMaterial", true, filtros);
/*  443: 439 */             if ((!lista.isEmpty()) && (((ProductoMaterial)lista.get(0)).isIndicadorGeneraSuborden()))
/*  444:     */             {
/*  445: 440 */               material.setIndicadorGeneraSuborden(true);
/*  446: 441 */               break;
/*  447:     */             }
/*  448:     */           }
/*  449: 445 */           dosm = (DetalleOrdenSalidaMaterial)((Map)mapaDetalleOrdenSalidaMaterial).get(Integer.valueOf(material.getId()));
/*  450: 446 */           if ((dosm == null) && (
/*  451: 447 */             (costeoSubOrdenes) || (!material.isIndicadorGeneraSuborden())))
/*  452:     */           {
/*  453: 448 */             dosm = new DetalleOrdenSalidaMaterial();
/*  454: 449 */             dosm.setIndicadorAutomatico(true);
/*  455: 450 */             dosm.setOrdenSalidaMaterial(ordenSalidaMaterial);
/*  456: 451 */             dosm.setIdOrganizacion(ordenSalidaMaterial.getIdOrganizacion());
/*  457: 452 */             dosm.setIdSucursal(ordenSalidaMaterial.getSucursal().getId());
/*  458: 453 */             dosm.setProducto(material);
/*  459: 454 */             dosm.setIndicadorConsumoDirecto(material.isIndicadorConsumoDirecto());
/*  460: 455 */             dosm.setUnidad(material.getUnidad());
/*  461: 456 */             dosm.setBodega(bodegaTrabajoMaterial);
/*  462: 457 */             ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().add(dosm);
/*  463:     */           }
/*  464: 461 */           if (dosm != null) {
/*  465: 462 */             dosm.setCantidad(dosm.getCantidad().add(cantidadMaterial));
/*  466:     */           }
/*  467: 465 */           for (Object[] itemOrden : listaOrdenes)
/*  468:     */           {
/*  469: 466 */             BigDecimal cantidadOrden = (BigDecimal)itemOrden[0];
/*  470: 467 */             OrdenFabricacion ordenFabricacion = (OrdenFabricacion)itemOrden[1];
/*  471: 468 */             if ((dosm != null) && ((costeoSubOrdenes) || (!dosm.getProducto().isIndicadorGeneraSuborden())))
/*  472:     */             {
/*  473: 469 */               DetalleOrdenSalidaMaterialOrdenFabricacion dosmof = new DetalleOrdenSalidaMaterialOrdenFabricacion();
/*  474: 470 */               dosmof.setIdOrganizacion(ordenFabricacion.getIdOrganizacion());
/*  475: 471 */               dosmof.setIdSucursal(dosm.getIdSucursal());
/*  476: 472 */               dosmof.setCantidad(cantidadOrden);
/*  477: 473 */               dosmof.setDetalleOrdenSalidaMaterial(dosm);
/*  478: 474 */               dosmof.setOrdenFabricacion(costeoSubOrdenes ? ordenFabricacion : ordenFabricacion.getOrdenFabricacionPrincipal());
/*  479: 475 */               dosm.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().add(dosmof);
/*  480:     */             }
/*  481:     */           }
/*  482:     */         }
/*  483: 479 */         this.servicioOrdenSalidaMaterial.guardar(ordenSalidaMaterial);
/*  484: 480 */         for (OrdenFabricacion ordenFabricacion : listaNuevaOrdenFabricacion) {
/*  485: 482 */           if ((costeoSubOrdenes) || (!ordenFabricacion.isIndicadorSuborden()))
/*  486:     */           {
/*  487: 483 */             OrdenFabricacionOrdenSalidaMaterial ofosm = new OrdenFabricacionOrdenSalidaMaterial();
/*  488: 484 */             ofosm.setIdOrganizacion(ordenFabricacion.getIdOrganizacion());
/*  489: 485 */             ofosm.setIdSucursal(ordenFabricacion.getSucursal().getId());
/*  490: 486 */             ofosm.setOrdenFabricacion(ordenFabricacion);
/*  491: 487 */             ofosm.setOrdenSalidaMaterial(ordenSalidaMaterial);
/*  492: 488 */             ordenFabricacion.getListaOrdenFabricacionOrdenSalidaMaterial().add(ofosm);
/*  493: 489 */             this.ordenFabricacionOrdenSalidaMaterialDao.guardar(ofosm);
/*  494:     */             
/*  495: 491 */             ordenFabricacion.setOrdenSalidaMaterialPrincipal(ordenSalidaMaterial);
/*  496: 492 */             this.ordenFabricacionDao.guardar(ordenFabricacion);
/*  497:     */           }
/*  498:     */         }
/*  499:     */       }
/*  500:     */     }
/*  501:     */     catch (ExcepcionAS2Financiero e)
/*  502:     */     {
/*  503:     */       Integer idMaterial;
/*  504: 498 */       this.context.setRollbackOnly();
/*  505: 499 */       throw e;
/*  506:     */     }
/*  507:     */     catch (ExcepcionAS2 e)
/*  508:     */     {
/*  509: 501 */       this.context.setRollbackOnly();
/*  510: 502 */       throw e;
/*  511:     */     }
/*  512:     */     catch (AS2Exception e)
/*  513:     */     {
/*  514: 504 */       this.context.setRollbackOnly();
/*  515: 505 */       throw e;
/*  516:     */     }
/*  517:     */     catch (Exception e)
/*  518:     */     {
/*  519: 507 */       e.printStackTrace();
/*  520: 508 */       this.context.setRollbackOnly();
/*  521: 509 */       throw new ExcepcionAS2(e);
/*  522:     */     }
/*  523:     */   }
/*  524:     */   
/*  525:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  526:     */   public void lanzarOrden(OrdenFabricacion ordenFabricacion)
/*  527:     */     throws AS2Exception
/*  528:     */   {
/*  529:     */     try
/*  530:     */     {
/*  531: 524 */       for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenesFabricacion())
/*  532:     */       {
/*  533: 525 */         suborden.setIndicadorRequiereFormulacion(ordenFabricacion.isIndicadorRequiereFormulacion());
/*  534: 526 */         suborden.setFechaLanzamiento(ordenFabricacion.getFechaLanzamiento());
/*  535: 527 */         lanzarOrdenFabricacion(suborden);
/*  536:     */       }
/*  537: 531 */       if (!ordenFabricacion.getListaSubordenesFabricacion().isEmpty())
/*  538:     */       {
/*  539: 532 */         ordenFabricacion.setEstado(EstadoProduccionEnum.LANZADA);
/*  540: 533 */         this.ordenFabricacionDao.guardar(ordenFabricacion);
/*  541:     */       }
/*  542:     */       else
/*  543:     */       {
/*  544: 535 */         lanzarOrdenFabricacion(ordenFabricacion);
/*  545:     */       }
/*  546:     */     }
/*  547:     */     catch (AS2Exception e)
/*  548:     */     {
/*  549: 539 */       this.context.setRollbackOnly();
/*  550: 540 */       throw e;
/*  551:     */     }
/*  552:     */     catch (Exception e)
/*  553:     */     {
/*  554: 542 */       e.printStackTrace();
/*  555: 543 */       this.context.setRollbackOnly();
/*  556: 544 */       throw new AS2Exception(e.getMessage());
/*  557:     */     }
/*  558:     */   }
/*  559:     */   
/*  560:     */   public void lanzarOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/*  561:     */     throws AS2Exception
/*  562:     */   {
/*  563: 549 */     OrdenFabricacion orden = buscarPorId(ordenFabricacion.getId());
/*  564: 551 */     if (orden.getEstado() == EstadoProduccionEnum.PLANEADA)
/*  565:     */     {
/*  566: 552 */       ordenFabricacion.setEstado(EstadoProduccionEnum.LANZADA);
/*  567: 553 */       this.ordenFabricacionDao.guardar(ordenFabricacion);
/*  568: 554 */       ordenFabricacionMateriales(ordenFabricacion);
/*  569: 555 */       if (!ordenFabricacion.isIndicadorRequiereFormulacion())
/*  570:     */       {
/*  571: 556 */         ordenFabricacion.setIndicadorFormulada(true);
/*  572: 557 */         this.ordenFabricacionDao.guardar(ordenFabricacion);
/*  573:     */       }
/*  574:     */     }
/*  575:     */     else
/*  576:     */     {
/*  577: 560 */       throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_LANZAR_ORDEN_FABRICACION", new String[] { ordenFabricacion.getNumero(), ordenFabricacion.getEstado().toString() });
/*  578:     */     }
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void ordenFabricacionMateriales(OrdenFabricacion ordenFabricacion)
/*  582:     */     throws AS2Exception
/*  583:     */   {
/*  584:     */     DetalleOrdenFabricacionMaterial detalleOrdenFabricacionProductoFinal;
/*  585: 565 */     if (ordenFabricacion.isIndicadorDirecto())
/*  586:     */     {
/*  587: 566 */       ordenFabricacion.getProducto().setCantidadProducir(ordenFabricacion.getCantidad());
/*  588: 567 */       NodoArbol<Producto> arbol = this.servicioProducto.obtenerArbolComponentes(ordenFabricacion.getProducto());
/*  589: 568 */       this.servicioProducto.actualizarCantidadesProducir(arbol, null);
/*  590: 569 */       detalleOrdenFabricacionProductoFinal = crearDetalleMaterialOrdenFabricacion(ordenFabricacion, null, arbol);
/*  591:     */       
/*  592: 571 */       this.detalleMaterialOrdenFabricacionDao.guardar(detalleOrdenFabricacionProductoFinal);
/*  593: 572 */       this.root = new DefaultTreeNode(arbol.getValor(), null);
/*  594: 573 */       for (NodoArbol<Producto> nodo : arbol.getHijos())
/*  595:     */       {
/*  596: 574 */         DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterialPadre = crearDetalleMaterialOrdenFabricacion(ordenFabricacion, detalleOrdenFabricacionProductoFinal, nodo);
/*  597:     */         
/*  598: 576 */         this.detalleMaterialOrdenFabricacionDao.guardar(detalleOrdenFabricacionMaterialPadre);
/*  599: 577 */         arbolOrdenFabricacionMateriales(nodo, this.root, ordenFabricacion, detalleOrdenFabricacionMaterialPadre);
/*  600: 578 */         for (DetalleOrdenFabricacionMaterialMezcla detalleOrdenFabricacionMaterialMezcla : detalleOrdenFabricacionMaterialPadre
/*  601: 579 */           .getListaDetalleOrdenFabricacionMaterialMezcla()) {
/*  602: 580 */           this.detalleOrdenFabricacionMaterialMezclaDao.guardar(detalleOrdenFabricacionMaterialMezcla);
/*  603:     */         }
/*  604:     */       }
/*  605:     */     }
/*  606:     */   }
/*  607:     */   
/*  608:     */   public void arbolOrdenFabricacionMateriales(NodoArbol<Producto> nodo, TreeNode padre, OrdenFabricacion ordenFabricacion, DetalleOrdenFabricacionMaterial detalleMaterialOrdenFabricacionPadre)
/*  609:     */     throws AS2Exception
/*  610:     */   {
/*  611: 588 */     TreeNode root1 = new DefaultTreeNode(nodo.getValor(), this.root);
/*  612: 589 */     padre.getChildren().add(root1);
/*  613: 590 */     for (NodoArbol<Producto> nodo1 : nodo.getHijos())
/*  614:     */     {
/*  615: 591 */       DetalleOrdenFabricacionMaterial dmofp = crearDetalleMaterialOrdenFabricacion(ordenFabricacion, detalleMaterialOrdenFabricacionPadre, nodo1);
/*  616: 593 */       for (DetalleOrdenFabricacionMaterialMezcla detalleOrdenFabricacionMaterialMezcla : dmofp
/*  617: 594 */         .getListaDetalleOrdenFabricacionMaterialMezcla()) {
/*  618: 595 */         this.detalleOrdenFabricacionMaterialMezclaDao.guardar(detalleOrdenFabricacionMaterialMezcla);
/*  619:     */       }
/*  620: 597 */       this.detalleMaterialOrdenFabricacionDao.guardar(dmofp);
/*  621: 598 */       arbolOrdenFabricacionMateriales(nodo1, root1, ordenFabricacion, dmofp);
/*  622:     */     }
/*  623:     */   }
/*  624:     */   
/*  625:     */   public DetalleOrdenFabricacionMaterial crearDetalleMaterialOrdenFabricacion(OrdenFabricacion ordenFabricacion, DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterialPadre, NodoArbol<Producto> nodo)
/*  626:     */     throws AS2Exception
/*  627:     */   {
/*  628: 604 */     DetalleOrdenFabricacionMaterial dmof = new DetalleOrdenFabricacionMaterial();
/*  629: 605 */     dmof.setOrdenFabricacion(ordenFabricacion);
/*  630: 606 */     dmof.setIdOrganizacion(ordenFabricacion.getIdOrganizacion());
/*  631: 607 */     dmof.setSucursal(ordenFabricacion.getSucursal());
/*  632: 608 */     dmof.setDetalleOrdenFabricacionMaterialPadre(detalleOrdenFabricacionMaterialPadre);
/*  633: 609 */     dmof.setMaterial((Producto)nodo.getValor());
/*  634: 610 */     dmof.setIndicadorConsumoDirecto(((Producto)nodo.getValor()).isIndicadorConsumoDirecto());
/*  635: 611 */     dmof.setCantidad(((Producto)nodo.getValor()).getCantidadProducir());
/*  636: 612 */     dmof.setUnidad(((Producto)nodo.getValor()).getUnidad());
/*  637: 613 */     dmof.setIndicadorHoja(nodo.isHoja());
/*  638: 616 */     if (dmof.isIndicadorHoja())
/*  639:     */     {
/*  640: 618 */       dmof.setBodegaTrabajoMaterial(this.servicioProducto.obtenerBodegaTrabajoProducto(dmof.getMaterial(), Integer.valueOf(ordenFabricacion.getSucursal().getId())));
/*  641: 619 */       if (dmof.getBodegaTrabajoMaterial() == null) {
/*  642: 620 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_PARAMETRIZAR_BODEGA_TRABAJO", new String[] { dmof.getMaterial().getNombre() });
/*  643:     */       }
/*  644: 624 */       dmof.setCantidadDisponible(this.servicioProducto.getSaldo(dmof.getMaterial().getId(), dmof.getBodegaTrabajoMaterial().getId(), new Date()));
/*  645: 627 */       if (dmof.getMaterial().isIndicadorMezcla())
/*  646:     */       {
/*  647: 628 */         Producto p = this.servicioProducto.cargarDetalleListaMezclaProducto(dmof.getMaterial());
/*  648: 629 */         for (MezclaProducto mezclaProducto : p.getListaMezclaProducto())
/*  649:     */         {
/*  650: 630 */           DetalleOrdenFabricacionMaterialMezcla detalleMezcla = new DetalleOrdenFabricacionMaterialMezcla();
/*  651: 631 */           detalleMezcla.setDetalleOrdenFabricacionMaterial(dmof);
/*  652: 632 */           detalleMezcla.setProducto(mezclaProducto.getProductoMezcla());
/*  653: 633 */           detalleMezcla.setPorcentaje(mezclaProducto.getPorcentaje());
/*  654: 634 */           detalleMezcla.setCantidad(dmof
/*  655: 635 */             .getCantidad().multiply(mezclaProducto.getPorcentaje()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/*  656: 636 */           BigDecimal cantidadPorCadaBatch = dmof.getCantidadPorCadaBatch();
/*  657: 637 */           detalleMezcla.setCantidadPorCadaBatch(cantidadPorCadaBatch
/*  658: 638 */             .multiply(mezclaProducto.getPorcentaje()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/*  659: 639 */           dmof.getListaDetalleOrdenFabricacionMaterialMezcla().add(detalleMezcla);
/*  660:     */         }
/*  661:     */       }
/*  662:     */     }
/*  663: 644 */     return dmof;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void eliminar(OrdenFabricacion ordenFabricacion)
/*  667:     */   {
/*  668: 654 */     this.ordenFabricacionDao.eliminar(ordenFabricacion);
/*  669:     */   }
/*  670:     */   
/*  671:     */   public OrdenFabricacion buscarPorId(int idOrdenFabricacion)
/*  672:     */   {
/*  673: 665 */     return (OrdenFabricacion)this.ordenFabricacionDao.buscarPorId(Integer.valueOf(idOrdenFabricacion));
/*  674:     */   }
/*  675:     */   
/*  676:     */   public List<OrdenFabricacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters, boolean cargarSalidaMaterial)
/*  677:     */   {
/*  678: 676 */     return this.ordenFabricacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters, cargarSalidaMaterial);
/*  679:     */   }
/*  680:     */   
/*  681:     */   public List<OrdenFabricacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  682:     */   {
/*  683: 686 */     return this.ordenFabricacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  684:     */   }
/*  685:     */   
/*  686:     */   public int contarPorCriterio(Map<String, String> filters)
/*  687:     */   {
/*  688: 696 */     return this.ordenFabricacionDao.contarPorCriterio(filters);
/*  689:     */   }
/*  690:     */   
/*  691:     */   public OrdenFabricacion cargarDetalle(int idOrdenFabricacion)
/*  692:     */   {
/*  693: 706 */     return this.ordenFabricacionDao.cargarDetalle(idOrdenFabricacion);
/*  694:     */   }
/*  695:     */   
/*  696:     */   private void validar(OrdenFabricacion ordenFabricacion)
/*  697:     */     throws ExcepcionAS2Financiero, AS2Exception
/*  698:     */   {
/*  699: 717 */     if (ordenFabricacion.isIndicadorDirecto())
/*  700:     */     {
/*  701: 718 */       if (ordenFabricacion.getProducto() == null) {
/*  702: 719 */         throw new AS2Exception("msg_error_campo_obligatorio", new String[] { "Producto" });
/*  703:     */       }
/*  704: 721 */       if (ordenFabricacion.getRutaFabricacion() == null) {
/*  705: 722 */         throw new AS2Exception("msg_error_campo_obligatorio", new String[] { "Ruta Fabricacion" });
/*  706:     */       }
/*  707:     */     }
/*  708:     */     else
/*  709:     */     {
/*  710: 726 */       BigDecimal cantidadOrden = ordenFabricacion.getCantidad();
/*  711: 727 */       BigDecimal cantidadSuborden = BigDecimal.ZERO;
/*  712: 728 */       for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenes()) {
/*  713: 729 */         if (!suborden.isEliminado()) {
/*  714: 730 */           cantidadSuborden = cantidadSuborden.add(suborden.getCantidad());
/*  715:     */         }
/*  716:     */       }
/*  717: 734 */       if (cantidadOrden.compareTo(cantidadSuborden) != 0) {
/*  718: 736 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CANTIDAD_ORDEN_FABRICACION_NO_IGUAL_SUBORDEN", new String[] { cantidadOrden.toString(), cantidadSuborden.toString() });
/*  719:     */       }
/*  720:     */     }
/*  721: 741 */     if (ordenFabricacion.getEstado() == EstadoProduccionEnum.PLANEADA) {
/*  722: 742 */       this.servicioPeriodo.buscarPorFecha(ordenFabricacion.getFecha(), ordenFabricacion.getIdOrganizacion(), ordenFabricacion
/*  723: 743 */         .getDocumento().getDocumentoBase());
/*  724:     */     }
/*  725: 746 */     if (ordenFabricacion.getFechaLanzamiento() != null) {
/*  726: 747 */       this.servicioPeriodo.buscarPorFecha(ordenFabricacion.getFechaLanzamiento(), ordenFabricacion.getIdOrganizacion(), ordenFabricacion
/*  727: 748 */         .getDocumento().getDocumentoBase());
/*  728:     */     }
/*  729: 751 */     if (ordenFabricacion.getFechaInicio() != null) {
/*  730: 752 */       this.servicioPeriodo.buscarPorFecha(ordenFabricacion.getFechaInicio(), ordenFabricacion.getIdOrganizacion(), ordenFabricacion
/*  731: 753 */         .getDocumento().getDocumentoBase());
/*  732:     */     }
/*  733: 756 */     if (ordenFabricacion.getFechaCierre() != null) {
/*  734: 757 */       this.servicioPeriodo.buscarPorFecha(ordenFabricacion.getFechaCierre(), ordenFabricacion.getIdOrganizacion(), ordenFabricacion
/*  735: 758 */         .getDocumento().getDocumentoBase());
/*  736:     */     }
/*  737:     */   }
/*  738:     */   
/*  739:     */   private void cargarSecuencia(OrdenFabricacion ordenFabricacion)
/*  740:     */     throws ExcepcionAS2
/*  741:     */   {
/*  742: 764 */     if ((ordenFabricacion.getNumero() == null) || (ordenFabricacion.getNumero().isEmpty()))
/*  743:     */     {
/*  744: 765 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(ordenFabricacion.getDocumento().getId(), ordenFabricacion.getFecha());
/*  745: 766 */       ordenFabricacion.setNumero(numero);
/*  746:     */     }
/*  747:     */   }
/*  748:     */   
/*  749:     */   public List<OrdenFabricacion> autocompletarOrdenesAbiertas(int idOrganizacion, String cadena, CategoriaProducto categoriaProducto)
/*  750:     */   {
/*  751: 778 */     return this.ordenFabricacionDao.autocompletarOrdenesAbiertas(idOrganizacion, cadena, categoriaProducto);
/*  752:     */   }
/*  753:     */   
/*  754:     */   public List<OrdenFabricacion> autocompletarOrdenesAbiertas(int idOrganizacion, String cadena, CategoriaProducto categoriaProducto, List<Integer> listIdsSucursalesAsignadasUsuarioEnSesion, Boolean indicadorBusquedaPorOrdenFabricacion)
/*  755:     */   {
/*  756: 784 */     return this.ordenFabricacionDao.autocompletarOrdenesAbiertas(idOrganizacion, cadena, categoriaProducto, listIdsSucursalesAsignadasUsuarioEnSesion, indicadorBusquedaPorOrdenFabricacion);
/*  757:     */   }
/*  758:     */   
/*  759:     */   public List<OrdenFabricacion> getListaOrdenesLanzadas(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/*  760:     */   {
/*  761: 790 */     return this.ordenFabricacionDao.getListaOrdenesLanzadas(idOrganizacion, fechaDesde, fechaHasta);
/*  762:     */   }
/*  763:     */   
/*  764:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  765:     */   public void finalizarOrden(OrdenFabricacion ordenFabricacion, Date fechaCierre, boolean costeoSubOrdenes)
/*  766:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  767:     */   {
/*  768:     */     try
/*  769:     */     {
/*  770: 799 */       List<OperacionOrdenFabricacion> lista = ordenFabricacion.getListaOperacionOrdenFabricacion();
/*  771:     */       
/*  772: 801 */       ordenFabricacion = cargarDetalle(ordenFabricacion.getId());
/*  773: 802 */       ordenFabricacion.setListaOperacionProduccion(lista);
/*  774: 803 */       ordenFabricacion.setListaSubordenes(obtenerListaSubordenFabricacion(ordenFabricacion.getId(), true));
/*  775: 805 */       if ((!EstadoProduccionEnum.LANZADA.equals(ordenFabricacion.getEstado())) && 
/*  776: 806 */         (!EstadoProduccionEnum.SUSPENDIDA.equals(ordenFabricacion.getEstado())) && 
/*  777: 807 */         (!EstadoProduccionEnum.ENVIADA.equals(ordenFabricacion.getEstado()))) {
/*  778: 808 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_DEBE_LANZAR_ORDEN_FABRICACION", new String[] { "" });
/*  779:     */       }
/*  780: 810 */       if (ordenFabricacion.getCantidadFabricada().compareTo(BigDecimal.ZERO) <= 0) {
/*  781: 811 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_CANTIDAD_FABRICACION_ZERO_FINALIZAR_OF", new String[] { ordenFabricacion.getNumero() });
/*  782:     */       }
/*  783: 813 */       if (((!TipoCicloProduccionEnum.CICLO_LARGO.equals(ordenFabricacion.getTipoCicloProduccionEnum())) && 
/*  784: 814 */         (!ordenFabricacion.isMaterialesSolicitados())) || (ordenFabricacion.getListaOrdenFabricacionOrdenSalidaMaterial().isEmpty())) {
/*  785: 815 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_NO_SOLICITADOS_MATERIALES", new String[] { ordenFabricacion.getNumero() });
/*  786:     */       }
/*  787: 817 */       ordenFabricacion.setEstado(EstadoProduccionEnum.FINALIZADA);
/*  788:     */       
/*  789:     */ 
/*  790: 820 */       ordenFabricacion.setFechaCierre(fechaCierre);
/*  791:     */       
/*  792:     */ 
/*  793:     */ 
/*  794: 824 */       List<MovimientoInventario> listaIngresoFabricacionMes = this.servicioMovimientoInventario.obtenerIngresoFabricacionDelMes(ordenFabricacion.getFechaCierre(), ordenFabricacion);
/*  795: 825 */       if ((listaIngresoFabricacionMes.isEmpty()) && (ordenFabricacion.getTipoCicloProduccionEnum().equals(TipoCicloProduccionEnum.CICLO_LARGO))) {
/*  796: 826 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_DEBE_EXISTIR_INGRESO_FABRICACION_MES", new String[] { "" });
/*  797:     */       }
/*  798: 831 */       List<MovimientoInventario> listaIngresoFabricacion = this.movimientoInventarioDao.buscarIngresoFabricacionPorOrdenFabricacion(ordenFabricacion);
/*  799: 833 */       for (Iterator localIterator1 = listaIngresoFabricacion.iterator(); localIterator1.hasNext();)
/*  800:     */       {
/*  801: 833 */         movimientoInventario = (MovimientoInventario)localIterator1.next();
/*  802: 834 */         Integer versionCosteo = Integer.valueOf(0);
/*  803: 835 */         i = 0;
/*  804:     */         
/*  805:     */ 
/*  806: 838 */         movimientoInventario = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(movimientoInventario.getId()));
/*  807: 839 */         for (DetalleMovimientoInventario detalleMovimientoInventario : movimientoInventario.getDetalleMovimientosInventario())
/*  808:     */         {
/*  809: 840 */           if (i == 0) {
/*  810: 841 */             versionCosteo = Integer.valueOf(detalleMovimientoInventario.getProducto().getVersionCosteo() + 1);
/*  811:     */           }
/*  812: 843 */           i++;
/*  813: 844 */           if (!detalleMovimientoInventario.isIndicadorRecibido()) {
/*  814: 845 */             throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_FALTAN_PRODUCTOS_POR_RECIBIR", new String[] { "" });
/*  815:     */           }
/*  816:     */         }
/*  817: 849 */         this.servicioCosteo.generarCostos(movimientoInventario, ParametrosSistema.isCosteoPorBodega(movimientoInventario.getIdOrganizacion()).booleanValue(), versionCosteo);
/*  818:     */         
/*  819:     */ 
/*  820:     */ 
/*  821: 853 */         this.servicioMovimientoInventario.contabilizarIngresoFabricacion(movimientoInventario);
/*  822:     */       }
/*  823:     */       MovimientoInventario movimientoInventario;
/*  824: 855 */       guardar(ordenFabricacion);
/*  825:     */       
/*  826:     */ 
/*  827: 858 */       Object listaMateriales = this.servicioProducto.obtenerMaterialesDetalleOrdenFabricacionMaterial(ordenFabricacion, Boolean.valueOf(true));
/*  828: 859 */       for (DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterial : (List)listaMateriales) {
/*  829: 860 */         this.servicioVerificadorInventario.actualizarInventarioComprometidoProduccion(detalleOrdenFabricacionMaterial, new Date(), true);
/*  830:     */       }
/*  831: 864 */       if (!costeoSubOrdenes)
/*  832:     */       {
/*  833: 866 */         HashMap<String, String> filters = new HashMap();
/*  834: 867 */         filters.put("ordenFabricacionPrincipal.idOrdenFabricacion", "" + ordenFabricacion.getIdOrdenFabricacion());
/*  835: 868 */         List<OrdenFabricacion> subordenes = obtenerListaCombo("numero", true, filters);
/*  836: 870 */         for (OrdenFabricacion suborden : subordenes)
/*  837:     */         {
/*  838: 871 */           HashMap<String, Object> campos = new HashMap();
/*  839: 872 */           campos.put("estado", EstadoProduccionEnum.FINALIZADA);
/*  840: 873 */           this.ordenFabricacionDao.actualizarAtributoEntidad(suborden, campos);
/*  841:     */         }
/*  842:     */       }
/*  843:     */     }
/*  844:     */     catch (ExcepcionAS2Financiero e)
/*  845:     */     {
/*  846:     */       int i;
/*  847: 878 */       this.context.setRollbackOnly();
/*  848: 879 */       throw e;
/*  849:     */     }
/*  850:     */     catch (ExcepcionAS2 e)
/*  851:     */     {
/*  852: 881 */       this.context.setRollbackOnly();
/*  853: 882 */       throw e;
/*  854:     */     }
/*  855:     */     catch (AS2Exception e)
/*  856:     */     {
/*  857: 884 */       this.context.setRollbackOnly();
/*  858: 885 */       throw e;
/*  859:     */     }
/*  860:     */     catch (Exception e)
/*  861:     */     {
/*  862: 887 */       e.printStackTrace();
/*  863: 888 */       this.context.setRollbackOnly();
/*  864: 889 */       throw new ExcepcionAS2(e);
/*  865:     */     }
/*  866:     */   }
/*  867:     */   
/*  868:     */   public void cargarOperaciones(OrdenFabricacion ordenFabricacion, boolean costeoSubOrdenes)
/*  869:     */   {
/*  870: 896 */     Date fecha = ordenFabricacion.getTipoCicloProduccionEnum().equals(TipoCicloProduccionEnum.CICLO_CORTO) ? ordenFabricacion.getFechaLanzamiento() : new Date();
/*  871: 897 */     int anio = FuncionesUtiles.getAnio(fecha);
/*  872: 898 */     int mes = FuncionesUtiles.getMes(fecha);
/*  873: 899 */     List<OperacionOrdenFabricacion> lista = getOperacionOrdenFabricacionActualizado(ordenFabricacion.getIdOrganizacion(), ordenFabricacion, anio, mes, costeoSubOrdenes);
/*  874:     */     
/*  875: 901 */     ordenFabricacion.setListaOperacionOrdenFabricacion(lista);
/*  876:     */   }
/*  877:     */   
/*  878:     */   public List<OrdenFabricacion> getListaOrdenFabricacionFinalizar(List<OrdenFabricacion> listaOrdenFabricacion, Boolean costeoSubOrdenes, Boolean indicadorRequiereFormulacion)
/*  879:     */   {
/*  880: 908 */     List<OrdenFabricacion> lista = new ArrayList();
/*  881:     */     OrdenFabricacion suborden;
/*  882: 909 */     if (costeoSubOrdenes.booleanValue())
/*  883:     */     {
/*  884: 911 */       if (listaOrdenFabricacion != null) {
/*  885: 914 */         for (OrdenFabricacion orden : listaOrdenFabricacion) {
/*  886: 915 */           if (((EstadoProduccionEnum.LANZADA.equals(orden.getEstado())) || (EstadoProduccionEnum.SUSPENDIDA.equals(orden.getEstado())) || 
/*  887: 916 */             (EstadoProduccionEnum.ENVIADA.equals(orden.getEstado()))) && 
/*  888: 917 */             ((orden.isMaterialesSolicitados()) || ((TipoCicloProduccionEnum.CICLO_LARGO.equals(orden.getTipoCicloProduccionEnum())) && 
/*  889: 918 */             (orden.getListaOrdenFabricacionOrdenSalidaMaterial().size() > 0))) && 
/*  890: 919 */             (orden.getCantidadFabricada().compareTo(BigDecimal.ZERO) > 0) && 
/*  891: 920 */             (!this.movimientoInventarioDao.existeDetalleIngresoFabricacionSinRecibir(orden)))
/*  892:     */           {
/*  893: 922 */             orden.setListaSubordenesFabricacion(new ArrayList());
/*  894:     */             
/*  895:     */ 
/*  896: 925 */             HashMap<String, String> filters = new HashMap();
/*  897: 926 */             filters.put("ordenFabricacionPrincipal.idOrdenFabricacion", "" + orden.getIdOrdenFabricacion());
/*  898: 927 */             List<OrdenFabricacion> subordenes = obtenerListaPorPagina(0, 1000, "numero", true, filters, false);
/*  899: 929 */             for (OrdenFabricacion suborden : subordenes) {
/*  900: 930 */               if (indicadorRequiereFormulacion.booleanValue())
/*  901:     */               {
/*  902: 931 */                 if (EstadoProduccionEnum.ENVIADA.equals(suborden.getEstado())) {
/*  903: 932 */                   orden.getListaSubordenesFabricacion().add(suborden);
/*  904:     */                 }
/*  905:     */               }
/*  906: 935 */               else if (((EstadoProduccionEnum.LANZADA.equals(suborden.getEstado())) || 
/*  907: 936 */                 (EstadoProduccionEnum.SUSPENDIDA.equals(suborden.getEstado())) || 
/*  908: 937 */                 (EstadoProduccionEnum.ENVIADA.equals(suborden.getEstado()))) && 
/*  909: 938 */                 ((suborden.isMaterialesSolicitados()) || (
/*  910: 939 */                 (TipoCicloProduccionEnum.CICLO_LARGO.equals(suborden.getTipoCicloProduccionEnum())) && 
/*  911: 940 */                 (suborden.getListaOrdenFabricacionOrdenSalidaMaterial().size() > 0))) && 
/*  912: 941 */                 (suborden.getCantidadFabricada().compareTo(BigDecimal.ZERO) > 0) && 
/*  913: 942 */                 (!this.movimientoInventarioDao.existeDetalleIngresoFabricacionSinRecibir(suborden))) {
/*  914: 943 */                 orden.getListaSubordenesFabricacion().add(suborden);
/*  915:     */               }
/*  916:     */             }
/*  917: 949 */             if (subordenes.size() == orden.getListaSubordenesFabricacion().size())
/*  918:     */             {
/*  919: 950 */               lista.add(orden);
/*  920: 951 */               for (??? = orden.getListaSubordenesFabricacion().iterator(); ???.hasNext();)
/*  921:     */               {
/*  922: 951 */                 suborden = (OrdenFabricacion)???.next();
/*  923: 952 */                 lista.add(suborden);
/*  924:     */               }
/*  925:     */             }
/*  926:     */           }
/*  927:     */         }
/*  928:     */       }
/*  929:     */     }
/*  930: 962 */     else if (listaOrdenFabricacion != null) {
/*  931: 965 */       for (OrdenFabricacion orden : listaOrdenFabricacion) {
/*  932: 967 */         if (((EstadoProduccionEnum.LANZADA.equals(orden.getEstado())) || (EstadoProduccionEnum.SUSPENDIDA.equals(orden.getEstado())) || 
/*  933: 968 */           (EstadoProduccionEnum.ENVIADA.equals(orden.getEstado()))) && 
/*  934: 969 */           ((orden.isMaterialesSolicitados()) || ((TipoCicloProduccionEnum.CICLO_LARGO.equals(orden.getTipoCicloProduccionEnum())) && 
/*  935: 970 */           (orden.getListaOrdenFabricacionOrdenSalidaMaterial().size() > 0))) && 
/*  936: 971 */           (orden.getCantidadFabricada().compareTo(BigDecimal.ZERO) > 0)) {
/*  937: 973 */           if (!this.movimientoInventarioDao.existeDetalleIngresoFabricacionSinRecibir(orden))
/*  938:     */           {
/*  939: 975 */             boolean indicadorSubordenesEnviadas = true;
/*  940: 976 */             if (indicadorRequiereFormulacion.booleanValue())
/*  941:     */             {
/*  942: 978 */               HashMap<String, String> filters = new HashMap();
/*  943: 979 */               filters.put("ordenFabricacionPrincipal.idOrdenFabricacion", "" + orden.getIdOrdenFabricacion());
/*  944: 980 */               Object subordenes = obtenerListaPorPagina(0, 1000, "numero", true, filters, false);
/*  945: 982 */               for (OrdenFabricacion suborden : (List)subordenes) {
/*  946: 983 */                 if (!EstadoProduccionEnum.ENVIADA.equals(suborden.getEstado()))
/*  947:     */                 {
/*  948: 984 */                   indicadorSubordenesEnviadas = false;
/*  949: 985 */                   break;
/*  950:     */                 }
/*  951:     */               }
/*  952:     */             }
/*  953: 990 */             if (indicadorSubordenesEnviadas) {
/*  954: 991 */               lista.add(orden);
/*  955:     */             }
/*  956:     */           }
/*  957:     */         }
/*  958:     */       }
/*  959:     */     }
/*  960: 999 */     return lista;
/*  961:     */   }
/*  962:     */   
/*  963:     */   public void anular(OrdenFabricacion ordenFabricacion)
/*  964:     */     throws AS2Exception
/*  965:     */   {
/*  966:1003 */     anular(ordenFabricacion, false);
/*  967:     */   }
/*  968:     */   
/*  969:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  970:     */   public void anular(OrdenFabricacion ordenFabricacion, boolean indicadorSuborden)
/*  971:     */     throws AS2Exception
/*  972:     */   {
/*  973:     */     try
/*  974:     */     {
/*  975:1009 */       esAnulable(ordenFabricacion, indicadorSuborden);
/*  976:1010 */       HashMap<String, Object> campos = new HashMap();
/*  977:1011 */       campos.put("estado", EstadoProduccionEnum.ANULADO);
/*  978:1012 */       this.ordenFabricacionDao.actualizarAtributoEntidad(ordenFabricacion, campos);
/*  979:     */       
/*  980:     */ 
/*  981:1015 */       this.servicioOrdenSalidaMaterial.anularOrdenFabricacion(ordenFabricacion);
/*  982:     */       
/*  983:     */ 
/*  984:     */ 
/*  985:1019 */       List<DetalleOrdenFabricacionMaterial> listaMateriales = this.servicioProducto.obtenerMaterialesDetalleOrdenFabricacionMaterial(ordenFabricacion, Boolean.valueOf(true));
/*  986:1020 */       Map<Integer, OrdenSalidaMaterial> mapaOrdenSalidaMaterial = new HashMap();
/*  987:1021 */       for (DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterial : listaMateriales) {
/*  988:1022 */         this.servicioVerificadorInventario.actualizarInventarioComprometidoProduccion(detalleOrdenFabricacionMaterial, new Date(), true);
/*  989:     */       }
/*  990:1025 */       ordenFabricacion.setListaSubordenes(obtenerListaSubordenFabricacion(ordenFabricacion.getId(), true));
/*  991:1026 */       for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenes()) {
/*  992:1027 */         anular(suborden, true);
/*  993:     */       }
/*  994:     */     }
/*  995:     */     catch (AS2Exception e)
/*  996:     */     {
/*  997:1030 */       this.context.setRollbackOnly();
/*  998:1031 */       throw e;
/*  999:     */     }
/* 1000:     */     catch (Exception e)
/* 1001:     */     {
/* 1002:1033 */       e.printStackTrace();
/* 1003:1034 */       this.context.setRollbackOnly();
/* 1004:1035 */       throw new AS2Exception(e.getMessage());
/* 1005:     */     }
/* 1006:     */   }
/* 1007:     */   
/* 1008:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1009:     */   public void anularPorFormulacion(OrdenFabricacion ordenFabricacion)
/* 1010:     */     throws AS2Exception
/* 1011:     */   {
/* 1012:     */     try
/* 1013:     */     {
/* 1014:1043 */       HashMap<String, Object> campos = new HashMap();
/* 1015:1044 */       campos.put("estado", EstadoProduccionEnum.ANULADO);
/* 1016:1045 */       this.ordenFabricacionDao.actualizarAtributoEntidad(ordenFabricacion, campos);
/* 1017:     */       
/* 1018:     */ 
/* 1019:1048 */       this.servicioOrdenSalidaMaterial.anularOrdenFabricacion(ordenFabricacion);
/* 1020:     */       
/* 1021:     */ 
/* 1022:     */ 
/* 1023:1052 */       List<DetalleOrdenFabricacionMaterial> listaMateriales = this.servicioProducto.obtenerMaterialesDetalleOrdenFabricacionMaterial(ordenFabricacion, Boolean.valueOf(true));
/* 1024:1053 */       Map<Integer, OrdenSalidaMaterial> mapaOrdenSalidaMaterial = new HashMap();
/* 1025:1054 */       for (DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterial : listaMateriales) {
/* 1026:1055 */         this.servicioVerificadorInventario.actualizarInventarioComprometidoProduccion(detalleOrdenFabricacionMaterial, new Date(), true);
/* 1027:     */       }
/* 1028:1058 */       ordenFabricacion.setListaSubordenes(obtenerListaSubordenFabricacion(ordenFabricacion.getId(), true));
/* 1029:1059 */       for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenes()) {
/* 1030:1060 */         anular(suborden, true);
/* 1031:     */       }
/* 1032:     */     }
/* 1033:     */     catch (AS2Exception e)
/* 1034:     */     {
/* 1035:1063 */       this.context.setRollbackOnly();
/* 1036:1064 */       throw e;
/* 1037:     */     }
/* 1038:     */     catch (Exception e)
/* 1039:     */     {
/* 1040:1066 */       e.printStackTrace();
/* 1041:1067 */       this.context.setRollbackOnly();
/* 1042:1068 */       throw new AS2Exception(e.getMessage());
/* 1043:     */     }
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   private Map<Estado, OrdenSalidaMaterial> getEstadosOrdenSalidaMaterial(OrdenFabricacion orden)
/* 1047:     */   {
/* 1048:1073 */     Map<Estado, OrdenSalidaMaterial> estados = new HashMap();
/* 1049:1074 */     for (OrdenFabricacionOrdenSalidaMaterial ofosm : orden.getListaOrdenFabricacionOrdenSalidaMaterial()) {
/* 1050:1075 */       estados.put(ofosm.getOrdenSalidaMaterial().getEstado(), ofosm.getOrdenSalidaMaterial());
/* 1051:     */     }
/* 1052:1077 */     return estados;
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   private void esAnulable(OrdenFabricacion ordenFabricacion, boolean indicadorSuborden)
/* 1056:     */     throws AS2Exception
/* 1057:     */   {
/* 1058:1081 */     ordenFabricacion = this.ordenFabricacionDao.cargarDetalle(ordenFabricacion.getIdOrdenFabricacion());
/* 1059:1082 */     if (((!ordenFabricacion.getListaOrdenFabricacionOrdenSalidaMaterial().isEmpty()) && 
/* 1060:1083 */       (getEstadosOrdenSalidaMaterial(ordenFabricacion).containsKey(Estado.CERRADO))) || 
/* 1061:1084 */       (ordenFabricacion.getEstado().equals(EstadoProduccionEnum.ANULADO)) || 
/* 1062:1085 */       (ordenFabricacion.getCantidadFabricada().compareTo(BigDecimal.ZERO) > 0) || ((!indicadorSuborden) && 
/* 1063:1086 */       (ordenFabricacion.isIndicadorSuborden()))) {
/* 1064:1087 */       throw new AS2Exception("msg_error_anular", new String[] { "" });
/* 1065:     */     }
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   private void esEditable(OrdenFabricacion ordenFabricacion)
/* 1069:     */     throws AS2Exception
/* 1070:     */   {
/* 1071:1092 */     ordenFabricacion = this.ordenFabricacionDao.cargarDetalle(ordenFabricacion.getIdOrdenFabricacion());
/* 1072:1093 */     if (!ordenFabricacion.getEstado().equals(EstadoProduccionEnum.PLANEADA)) {
/* 1073:1095 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_ORDEN_FABRICACION_NO_EDITABLE", new String[] { ordenFabricacion.getNumero(), ordenFabricacion.getEstado().getNombre() });
/* 1074:     */     }
/* 1075:1097 */     if (ordenFabricacion.isMaterialesSolicitados()) {
/* 1076:1098 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_ORDEN_FABRICACION_NO_EDITABLE", new String[] { ordenFabricacion.getNumero(), "MATERIALES SOLICITADOS" });
/* 1077:     */     }
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public List<OrdenFabricacion> buscarOrdenesPorRangoFechaCosto(int idOrganizacion, Date fechaDesde, Date fechaHasta, boolean costeoSubOrdenes)
/* 1081:     */   {
/* 1082:1104 */     return this.ordenFabricacionDao.buscarOrdenesPorRangoFechaCosto(idOrganizacion, fechaDesde, fechaHasta, costeoSubOrdenes);
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public BigDecimal getCostosMateriales(OrdenFabricacion ordenFabricacion)
/* 1086:     */   {
/* 1087:1109 */     return this.ordenFabricacionDao.getCostosMateriales(ordenFabricacion);
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public TreeNode getRoot()
/* 1091:     */   {
/* 1092:1113 */     return this.root;
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   public void setRoot(TreeNode root)
/* 1096:     */   {
/* 1097:1117 */     this.root = root;
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public void reabrirOrden(OrdenFabricacion ordenFabricacion)
/* 1101:     */     throws ExcepcionAS2Financiero, AS2Exception
/* 1102:     */   {
/* 1103:1122 */     OrdenFabricacion ordenBase = cargarDetalle(ordenFabricacion.getId());
/* 1104:1123 */     if (!EstadoProduccionEnum.FINALIZADA.equals(ordenBase.getEstado())) {
/* 1105:1124 */       throw new AS2Exception("msg_error_accion_no_permitida", new String[] { ordenBase.getEstado().toString() });
/* 1106:     */     }
/* 1107:1127 */     Map<Estado, OrdenSalidaMaterial> mapa = getEstadosOrdenSalidaMaterial(ordenBase);
/* 1108:1128 */     if ((!ordenBase.getListaOrdenFabricacionOrdenSalidaMaterial().isEmpty()) && (mapa.containsKey(Estado.CERRADO)))
/* 1109:     */     {
/* 1110:1129 */       OrdenSalidaMaterial ordenSalidaCerrada = (OrdenSalidaMaterial)mapa.get(Estado.CERRADO);
/* 1111:     */       
/* 1112:     */ 
/* 1113:1132 */       throw new AS2Exception("msg_error_accion_no_permitida", new String[] {"(" + ordenSalidaCerrada.getNumero() + ")" + ordenSalidaCerrada.getEstado().toString() });
/* 1114:     */     }
/* 1115:1135 */     this.servicioPeriodo.buscarPorFecha(ordenBase.getFecha(), ordenBase.getIdOrganizacion(), ordenBase.getDocumento().getDocumentoBase());
/* 1116:1136 */     this.ordenFabricacionDao.reabrirOrden(ordenBase);
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1120:     */   public void actualizarFormula(List<DetalleOrdenFabricacionMaterial> listaDetalle, OrdenFabricacion ordenFabricacion, BigDecimal cantidadFormulacion)
/* 1121:     */     throws AS2Exception
/* 1122:     */   {
/* 1123:     */     try
/* 1124:     */     {
/* 1125:1144 */       for (DetalleOrdenFabricacionMaterial detalle : listaDetalle)
/* 1126:     */       {
/* 1127:1157 */         for (DetalleOrdenFabricacionMaterialMezcla detalleOrdenFabricacionMaterialMezcla : detalle
/* 1128:1158 */           .getListaDetalleOrdenFabricacionMaterialMezcla()) {
/* 1129:1159 */           this.detalleOrdenFabricacionMaterialMezclaDao.guardar(detalleOrdenFabricacionMaterialMezcla);
/* 1130:     */         }
/* 1131:1161 */         detalle.setOrdenFabricacion(ordenFabricacion);
/* 1132:1162 */         this.detalleMaterialOrdenFabricacionDao.guardar(detalle);
/* 1133:     */       }
/* 1134:1165 */       this.ordenFabricacionDao.actualizarCabeceraFormulacionOrdenFabricacion(ordenFabricacion.getId(), ordenFabricacion.getDescripcionFormula(), ordenFabricacion
/* 1135:1166 */         .getOrdenFabricacionFormulacion(), true, cantidadFormulacion, ordenFabricacion.getFechaFormulacion());
/* 1136:     */     }
/* 1137:     */     catch (Exception e)
/* 1138:     */     {
/* 1139:1169 */       this.context.setRollbackOnly();
/* 1140:1170 */       throw new AS2Exception(e.getMessage());
/* 1141:     */     }
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public List<OrdenFabricacion> obtenerListaSubordenFabricacion(int idOrdenFabricacion, boolean indicadorSoloInmediatos)
/* 1145:     */   {
/* 1146:1176 */     return this.ordenFabricacionDao.obtenerListaSubordenFabricacion(idOrdenFabricacion, indicadorSoloInmediatos);
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   public void cargarSubordenes(OrdenFabricacion ordenFabricacion)
/* 1150:     */     throws AS2Exception
/* 1151:     */   {
/* 1152:1181 */     if (ordenFabricacion.isIndicadorDirecto())
/* 1153:     */     {
/* 1154:1182 */       cargaRecursivaSubordenesBase(ordenFabricacion, true);
/* 1155:1183 */       ordenFabricacion.getProducto().setCantidadProducir(ordenFabricacion.getCantidad());
/* 1156:1184 */       NodoArbol<Producto> nodo = this.servicioProducto.obtenerArbolComponentes(ordenFabricacion.getProducto(), true);
/* 1157:1185 */       agregarNodoRecursivo(nodo, ordenFabricacion, ordenFabricacion, ordenFabricacion);
/* 1158:     */     }
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   public void actualizarCantidadSubordenes(OrdenFabricacion ordenFabricacion)
/* 1162:     */   {
/* 1163:1191 */     BigDecimal cantidadPadre = ordenFabricacion.getCantidad();
/* 1164:1192 */     for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenes())
/* 1165:     */     {
/* 1166:1193 */       BigDecimal proporcion = suborden.getProporcionOrdenPadre();
/* 1167:1194 */       suborden.setCantidad(cantidadPadre.multiply(proporcion).setScale(2, RoundingMode.HALF_UP));
/* 1168:1195 */       BigDecimal cantidadBatch = suborden.getCantidad().divide(suborden.getProducto().getCantidadProduccion(), 2, RoundingMode.HALF_UP);
/* 1169:1196 */       suborden.setCantidadBatch(cantidadBatch);
/* 1170:1197 */       actualizarCantidadSubordenes(suborden);
/* 1171:     */     }
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   private void agregarNodoRecursivo(NodoArbol<Producto> nodo, OrdenFabricacion ordenFabricacion, OrdenFabricacion ordenFabricacionPadre, OrdenFabricacion ordenFabricacionPrincipal)
/* 1175:     */     throws AS2Exception
/* 1176:     */   {
/* 1177:1203 */     if ((ordenFabricacion != null) && (ordenFabricacion.getId() != 0)) {
/* 1178:1204 */       esEditable(ordenFabricacion);
/* 1179:     */     }
/* 1180:1206 */     ProductoMaterial productoMaterial = (ProductoMaterial)nodo.getPropiedades().get("ProductoMaterial");
/* 1181:     */     BigDecimal cantidadBatch;
/* 1182:1207 */     if ((!nodo.isHoja()) && ((productoMaterial == null) || (productoMaterial.isIndicadorExplota()) || (productoMaterial.isIndicadorGeneraSuborden())))
/* 1183:     */     {
/* 1184:1208 */       if ((((Producto)nodo.getValor()).getCantidadProduccion() == null) || (((Producto)nodo.getValor()).getCantidadProduccion().equals(BigDecimal.ZERO))) {
/* 1185:1209 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_CANTIDAD_BOM", new String[] { ((Producto)nodo.getValor()).getNombre() });
/* 1186:     */       }
/* 1187:1211 */       if ((productoMaterial != null) && (productoMaterial.isIndicadorGeneraSuborden()) && (ordenFabricacion != null) && 
/* 1188:1212 */         (((Producto)nodo.getValor()).getId() == ordenFabricacion.getProducto().getId()))
/* 1189:     */       {
/* 1190:1213 */         ordenFabricacion.setEliminado(false);
/* 1191:1214 */         ordenFabricacion.setCantidad(((Producto)nodo.getValor()).getCantidadProducir());
/* 1192:1215 */         cantidadBatch = ordenFabricacion.getCantidad().divide(((Producto)nodo.getValor()).getCantidadProduccion(), 2, RoundingMode.HALF_UP);
/* 1193:1216 */         ordenFabricacion.setCantidadBatch(cantidadBatch);
/* 1194:1217 */         BigDecimal proporcional = BigDecimal.ZERO;
/* 1195:1218 */         if ((ordenFabricacionPadre != null) && (ordenFabricacionPadre.getCantidad().compareTo(BigDecimal.ZERO) != 0)) {
/* 1196:1219 */           proporcional = ordenFabricacion.getCantidad().divide(ordenFabricacionPadre.getCantidad(), 10, RoundingMode.HALF_UP);
/* 1197:     */         }
/* 1198:1221 */         ordenFabricacion.setProporcionOrdenPadre(proporcional);
/* 1199:     */       }
/* 1200:1224 */       for (NodoArbol<Producto> hijo : nodo.getHijos())
/* 1201:     */       {
/* 1202:1225 */         ProductoMaterial productoMaterialHijo = (ProductoMaterial)hijo.getPropiedades().get("ProductoMaterial");
/* 1203:1226 */         if (((productoMaterialHijo.isIndicadorExplota()) || (productoMaterialHijo.isIndicadorGeneraSuborden())) && (!hijo.isHoja()))
/* 1204:     */         {
/* 1205:1227 */           OrdenFabricacion suborden = null;
/* 1206:1228 */           if (productoMaterialHijo.isIndicadorGeneraSuborden())
/* 1207:     */           {
/* 1208:1229 */             suborden = buscarSubordenIgual(ordenFabricacion, hijo);
/* 1209:1230 */             if (suborden == null)
/* 1210:     */             {
/* 1211:1231 */               suborden = new OrdenFabricacion();
/* 1212:1232 */               suborden.setIdOrganizacion(ordenFabricacionPrincipal.getIdOrganizacion());
/* 1213:1233 */               suborden.setSucursal(ordenFabricacionPrincipal.getSucursal());
/* 1214:1234 */               suborden.setFecha(ordenFabricacion.getFecha());
/* 1215:1235 */               suborden.setDocumento(ordenFabricacion.getDocumento());
/* 1216:     */               
/* 1217:     */ 
/* 1218:1238 */               Producto producto = this.servicioProducto.cargarDetalleOrdenFabricacion(((Producto)hijo.getValor()).getId());
/* 1219:1239 */               suborden.setProducto(producto);
/* 1220:1240 */               suborden.setRutaFabricacion(producto.getRutaFabricacion());
/* 1221:1243 */               for (BodegaTrabajoProductoSucursal bodega : producto.getListaBodegaTrabajoSucursal()) {
/* 1222:1244 */                 if (bodega.getSucursal().getId() == ordenFabricacion.getSucursal().getId())
/* 1223:     */                 {
/* 1224:1245 */                   suborden.setBodega(bodega.getBodegaTrabajo());
/* 1225:1246 */                   break;
/* 1226:     */                 }
/* 1227:     */               }
/* 1228:1251 */               for (ProductoRutaFabricacionSucursal productoRutaFabricacionSucursal : producto
/* 1229:1252 */                 .getListaProductoRutaFabricacionSucursal()) {
/* 1230:1253 */                 if (productoRutaFabricacionSucursal.getSucursal().getId() == ordenFabricacion.getSucursal().getId())
/* 1231:     */                 {
/* 1232:1254 */                   suborden.setRutaFabricacion(productoRutaFabricacionSucursal.getRutaFabricacion());
/* 1233:1255 */                   break;
/* 1234:     */                 }
/* 1235:     */               }
/* 1236:1260 */               if (suborden.getRutaFabricacion() == null) {
/* 1237:1262 */                 throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_RUTA_FABRICACION_NULL_PRODUCTO", new String[] {suborden.getProducto().getCodigo() + "-" + suborden.getProducto().getNombre() });
/* 1238:     */               }
/* 1239:1266 */               if (suborden.getBodega() == null) {
/* 1240:1268 */                 throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_NO_EXISTE_BODEGA_TRABAJO_SUCURSAL", new String[] {suborden.getProducto().getCodigo() + "-" + suborden.getProducto().getNombre() });
/* 1241:     */               }
/* 1242:1271 */               suborden.setIndicadorSuborden(productoMaterialHijo.isIndicadorGeneraSuborden());
/* 1243:1272 */               suborden.setIndicadorValidaStockSuborden(productoMaterialHijo.isIndicadorValidaStockSuborden());
/* 1244:1273 */               suborden.setDocumento(ordenFabricacionPrincipal.getDocumento());
/* 1245:1274 */               suborden.setEstado(EstadoProduccionEnum.PLANEADA);
/* 1246:1275 */               suborden.setFecha(ordenFabricacionPrincipal.getFecha());
/* 1247:1276 */               suborden.setOrdenFabricacionPadre(ordenFabricacion != null ? ordenFabricacion : ordenFabricacionPadre);
/* 1248:1277 */               suborden.setOrdenFabricacionPrincipal(ordenFabricacionPrincipal);
/* 1249:1278 */               suborden.setPlanProduccion(ordenFabricacionPrincipal.getPlanProduccion());
/* 1250:1279 */               suborden.setProducto((Producto)hijo.getValor());
/* 1251:1280 */               suborden.setRutaFabricacion(((Producto)hijo.getValor()).getRutaFabricacion());
/* 1252:1281 */               suborden.getOrdenFabricacionPadre().getListaSubordenes().add(suborden);
/* 1253:     */             }
/* 1254:     */           }
/* 1255:1284 */           agregarNodoRecursivo(hijo, suborden, ordenFabricacion != null ? ordenFabricacion : ordenFabricacionPadre, ordenFabricacionPrincipal);
/* 1256:     */         }
/* 1257:     */       }
/* 1258:     */     }
/* 1259:     */   }
/* 1260:     */   
/* 1261:     */   private OrdenFabricacion buscarSubordenIgual(OrdenFabricacion ordenFabricacionPadre, NodoArbol<Producto> nodoHijo)
/* 1262:     */   {
/* 1263:1293 */     if (ordenFabricacionPadre == null) {
/* 1264:1294 */       return null;
/* 1265:     */     }
/* 1266:1296 */     for (OrdenFabricacion suborden : ordenFabricacionPadre.getListaSubordenes()) {
/* 1267:1297 */       if (((Producto)nodoHijo.getValor()).getId() == suborden.getProducto().getId()) {
/* 1268:1298 */         return suborden;
/* 1269:     */       }
/* 1270:     */     }
/* 1271:1301 */     return null;
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public void actualizarDatosSubordenes(OrdenFabricacion ordenFabricacion)
/* 1275:     */     throws AS2Exception
/* 1276:     */   {
/* 1277:1306 */     for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenes())
/* 1278:     */     {
/* 1279:1308 */       Producto producto = this.servicioProducto.cargarDetalleOrdenFabricacion(suborden.getProducto().getId());
/* 1280:1309 */       suborden.setRutaFabricacion(producto.getRutaFabricacion());
/* 1281:1310 */       suborden.setDocumento(ordenFabricacion.getDocumento());
/* 1282:1311 */       suborden.setFecha(ordenFabricacion.getFecha());
/* 1283:1314 */       for (BodegaTrabajoProductoSucursal bodega : producto.getListaBodegaTrabajoSucursal()) {
/* 1284:1315 */         if (bodega.getSucursal().getId() == ordenFabricacion.getSucursal().getId())
/* 1285:     */         {
/* 1286:1316 */           suborden.setBodega(bodega.getBodegaTrabajo());
/* 1287:1317 */           break;
/* 1288:     */         }
/* 1289:     */       }
/* 1290:1322 */       for (ProductoRutaFabricacionSucursal productoRutaFabricacionSucursal : producto.getListaProductoRutaFabricacionSucursal()) {
/* 1291:1323 */         if (productoRutaFabricacionSucursal.getSucursal().getId() == ordenFabricacion.getSucursal().getId())
/* 1292:     */         {
/* 1293:1324 */           suborden.setRutaFabricacion(productoRutaFabricacionSucursal.getRutaFabricacion());
/* 1294:1325 */           break;
/* 1295:     */         }
/* 1296:     */       }
/* 1297:1330 */       if (suborden.getRutaFabricacion() == null) {
/* 1298:1332 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_RUTA_FABRICACION_NULL_PRODUCTO", new String[] {suborden.getProducto().getCodigo() + "-" + suborden.getProducto().getNombre() });
/* 1299:     */       }
/* 1300:1336 */       if (suborden.getBodega() == null) {
/* 1301:1338 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_NO_EXISTE_BODEGA_TRABAJO_SUCURSAL", new String[] {suborden.getProducto().getCodigo() + "-" + suborden.getProducto().getNombre() });
/* 1302:     */       }
/* 1303:1341 */       suborden.setPersonaResponsable(ordenFabricacion.getPersonaResponsable());
/* 1304:1342 */       suborden.setAtributoOrdenFabricacion(ordenFabricacion.getAtributoOrdenFabricacion());
/* 1305:1343 */       suborden.setValorAtributoOrdenFabricacion(ordenFabricacion.getValorAtributoOrdenFabricacion());
/* 1306:1344 */       suborden.setListaSubordenes((suborden.getListaSubordenes() == null) || (suborden.getListaSubordenes().isEmpty()) ? new ArrayList() : suborden
/* 1307:1345 */         .getListaSubordenes());
/* 1308:1346 */       actualizarDatosSubordenes(suborden);
/* 1309:     */     }
/* 1310:     */   }
/* 1311:     */   
/* 1312:     */   private void cargaRecursivaSubordenesBase(OrdenFabricacion ordenFabricacion, boolean indicadroSetearEliminado)
/* 1313:     */   {
/* 1314:1351 */     if (ordenFabricacion.getOrdenFabricacionPadre() != null) {
/* 1315:1352 */       ordenFabricacion.setEliminado(indicadroSetearEliminado);
/* 1316:     */     }
/* 1317:1354 */     ordenFabricacion.setListaSubordenes(obtenerListaSubordenFabricacion(ordenFabricacion.getId(), true));
/* 1318:1355 */     for (OrdenFabricacion suborden : ordenFabricacion.getListaSubordenes()) {
/* 1319:1356 */       cargaRecursivaSubordenesBase(suborden, true);
/* 1320:     */     }
/* 1321:     */   }
/* 1322:     */   
/* 1323:     */   public void cargarSaldoBodegaTrabajo(OrdenFabricacion ordenFabricacion, Lote lote, Date fecha)
/* 1324:     */   {
/* 1325:1362 */     Bodega bodegaTrabajo = ordenFabricacion.getBodega();
/* 1326:1363 */     if (bodegaTrabajo == null) {
/* 1327:1364 */       this.servicioProducto.obtenerBodegaTrabajoProducto(ordenFabricacion.getProducto(), Integer.valueOf(ordenFabricacion.getSucursal().getId()));
/* 1328:     */     }
/* 1329:1366 */     ordenFabricacion.setBodegaTrabajo(bodegaTrabajo);
/* 1330:     */     BigDecimal saldoBodega;
/* 1331:     */     BigDecimal saldoBodega;
/* 1332:1368 */     if (ordenFabricacion.getProducto().isIndicadorLote()) {
/* 1333:1369 */       saldoBodega = this.servicioProducto.getSaldoLote(ordenFabricacion.getProducto().getId(), bodegaTrabajo.getId(), lote.getId(), fecha);
/* 1334:     */     } else {
/* 1335:1371 */       saldoBodega = this.servicioProducto.getSaldo(ordenFabricacion.getProducto().getId(), bodegaTrabajo.getId(), fecha);
/* 1336:     */     }
/* 1337:1373 */     ordenFabricacion.setSaldoBodegaTrabajo(saldoBodega);
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   public List<Object[]> getReporteInspeccionCalidadPT(Date fechaDesde, Date fechaHasta, EstadoControlCalidad estado, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, OrdenFabricacion ordenFabricacion)
/* 1341:     */   {
/* 1342:1379 */     return this.ordenFabricacionDao.getReporteInspeccionCalidadPT(fechaDesde, fechaHasta, estado, categoriaProducto, subcategoriaProducto, producto, ordenFabricacion);
/* 1343:     */   }
/* 1344:     */   
/* 1345:     */   public List<Object[]> getReporteFormulacion(OrdenFabricacion ordenFabricacion)
/* 1346:     */   {
/* 1347:1385 */     return this.ordenFabricacionDao.getReporteFormulacion(ordenFabricacion);
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1351:     */   public void suspenderOrdenFabricacion(OrdenFabricacion ordenFabricacion, boolean indicadorCrearNuevaOrdenFabricacion)
/* 1352:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 1353:     */   {
/* 1354:     */     try
/* 1355:     */     {
/* 1356:1395 */       ordenFabricacion = cargarDetalle(ordenFabricacion.getId());
/* 1357:1397 */       if (!ordenFabricacion.getEstado().equals(EstadoProduccionEnum.ENVIADA)) {
/* 1358:1398 */         throw new AS2Exception("msg_error_accion_no_permitida", new String[] { ordenFabricacion.getEstado() + "" });
/* 1359:     */       }
/* 1360:1401 */       if ((ordenFabricacion.getCantidadBatchFabricados().compareTo(BigDecimal.ZERO) == 0) || (ordenFabricacion.getCantidadFabricada().compareTo(BigDecimal.ZERO) == 0))
/* 1361:     */       {
/* 1362:1403 */         ordenFabricacion.setEstado(EstadoProduccionEnum.SUSPENDIDA);
/* 1363:1404 */         ordenFabricacion.setFechaLanzamiento(null);
/* 1364:1405 */         this.ordenFabricacionDao.guardar(ordenFabricacion);
/* 1365:     */         
/* 1366:1407 */         ordenFabricacion.getOrdenFabricacionPrincipal().setEstado(EstadoProduccionEnum.SUSPENDIDA);
/* 1367:1408 */         this.ordenFabricacionDao.guardar(ordenFabricacion.getOrdenFabricacionPrincipal());
/* 1368:     */       }
/* 1369:1412 */       else if (indicadorCrearNuevaOrdenFabricacion)
/* 1370:     */       {
/* 1371:1415 */         BigDecimal diferencia = ordenFabricacion.getCantidadBatch().subtract(ordenFabricacion.getCantidadBatchFabricados());
/* 1372:1416 */         if (diferencia.compareTo(BigDecimal.ZERO) > 0)
/* 1373:     */         {
/* 1374:1418 */           OrdenFabricacion ordenNueva = new OrdenFabricacion();
/* 1375:1419 */           ordenNueva.setIdOrganizacion(ordenFabricacion.getIdOrganizacion());
/* 1376:1420 */           ordenNueva.setSucursal(ordenFabricacion.getSucursal());
/* 1377:1421 */           ordenNueva.setBodega(ordenFabricacion.getBodega());
/* 1378:1422 */           ordenNueva.setCantidadBatch(diferencia);
/* 1379:1423 */           ordenNueva.setCantidad(diferencia
/* 1380:1424 */             .multiply(ordenFabricacion.getProducto().getCantidadProduccion()).setScale(2, RoundingMode.HALF_UP));
/* 1381:1425 */           ordenNueva.setDescripcion("Re-Formulacion: " + ordenFabricacion.getNumero() + " - " + (ordenFabricacion
/* 1382:1426 */             .getDescripcion() == null ? "" : ordenFabricacion.getDescripcion()));
/* 1383:1427 */           ordenNueva.setDescripcionFormula(ordenFabricacion.getDescripcionFormula());
/* 1384:1428 */           ordenNueva.setDocumento(ordenFabricacion.getDocumento());
/* 1385:1429 */           ordenNueva.setEstado(EstadoProduccionEnum.SUSPENDIDA);
/* 1386:1430 */           ordenNueva.setFecha(ordenFabricacion.getFecha());
/* 1387:1431 */           ordenNueva.setPlanProduccion(ordenFabricacion.getPlanProduccion());
/* 1388:1432 */           ordenNueva.setProducto(ordenFabricacion.getProducto());
/* 1389:1433 */           ordenNueva.setRutaFabricacion(ordenFabricacion.getRutaFabricacion());
/* 1390:1434 */           ordenNueva.setOrdenFabricacionFormulacion(ordenFabricacion);
/* 1391:1435 */           ordenNueva.setOrdenFabricacionPadre(ordenFabricacion.getOrdenFabricacionPadre());
/* 1392:1436 */           ordenNueva.setOrdenFabricacionPrincipal(ordenFabricacion.getOrdenFabricacionPrincipal());
/* 1393:1437 */           ordenNueva.setIndicadorHoja(ordenFabricacion.isIndicadorHoja());
/* 1394:1438 */           ordenNueva.setIndicadorSuborden(ordenFabricacion.isIndicadorSuborden());
/* 1395:1439 */           ordenNueva.setIndicadorValidaStockSuborden(ordenFabricacion.isIndicadorValidaStockSuborden());
/* 1396:     */           
/* 1397:     */ 
/* 1398:1442 */           String numero = ordenFabricacion.getOrdenFabricacionPadre().getNumero();
/* 1399:1443 */           String numeroSuborden = "";
/* 1400:1444 */           int consecutivo = 1;
/* 1401:1445 */           while (numeroSuborden.isEmpty())
/* 1402:     */           {
/* 1403:1446 */             HashMap<String, String> filters = new HashMap();
/* 1404:1447 */             filters.put("numero", "" + numero + "." + consecutivo);
/* 1405:1448 */             List<OrdenFabricacion> lista = obtenerListaCombo("numero", true, filters);
/* 1406:1449 */             if (lista.isEmpty()) {
/* 1407:1450 */               numeroSuborden = numero + "." + consecutivo;
/* 1408:     */             }
/* 1409:1452 */             consecutivo++;
/* 1410:     */           }
/* 1411:1454 */           ordenNueva.setNumero(numeroSuborden);
/* 1412:1455 */           guardar(ordenNueva);
/* 1413:     */           
/* 1414:1457 */           List<DetalleOrdenFabricacionMaterial> listaDetalleOrdenFabricacionMaterial = this.servicioProducto.copiarFormula(ordenFabricacion, ordenNueva, ordenNueva
/* 1415:1458 */             .getCantidad());
/* 1416:1459 */           actualizarFormula(listaDetalleOrdenFabricacionMaterial, ordenNueva, ordenNueva.getCantidad());
/* 1417:     */         }
/* 1418:1463 */         finalizarOrdenPorFormulacion(ordenFabricacion, new Date());
/* 1419:     */       }
/* 1420:     */       else
/* 1421:     */       {
/* 1422:1467 */         finalizarOrdenPorFormulacion(ordenFabricacion, new Date());
/* 1423:1470 */         if (ordenFabricacion.getOrdenFabricacionPadre() != null)
/* 1424:     */         {
/* 1425:1471 */           ordenFabricacion.getOrdenFabricacionPadre().setEstado(EstadoProduccionEnum.FINALIZADA);
/* 1426:1472 */           this.ordenFabricacionDao.guardar(ordenFabricacion);
/* 1427:     */         }
/* 1428:     */       }
/* 1429:1478 */       this.ordenFabricacionDao.suspenderOrdenFabricacionWinCC(ordenFabricacion);
/* 1430:     */     }
/* 1431:     */     catch (ExcepcionAS2Financiero e)
/* 1432:     */     {
/* 1433:1481 */       this.context.setRollbackOnly();
/* 1434:1482 */       throw e;
/* 1435:     */     }
/* 1436:     */     catch (ExcepcionAS2 e)
/* 1437:     */     {
/* 1438:1484 */       this.context.setRollbackOnly();
/* 1439:1485 */       throw e;
/* 1440:     */     }
/* 1441:     */     catch (AS2Exception e)
/* 1442:     */     {
/* 1443:1487 */       this.context.setRollbackOnly();
/* 1444:1488 */       throw e;
/* 1445:     */     }
/* 1446:     */     catch (Exception e)
/* 1447:     */     {
/* 1448:1490 */       e.printStackTrace();
/* 1449:1491 */       this.context.setRollbackOnly();
/* 1450:1492 */       throw new ExcepcionAS2(e);
/* 1451:     */     }
/* 1452:     */   }
/* 1453:     */   
/* 1454:     */   public List<DetalleOrdenSalidaMaterialOrdenFabricacion> obtenerDetalleOrdenSalidaMaterialOrdenFabricacion(OrdenFabricacion ordenFabricacion, boolean indicadorNoCerradas)
/* 1455:     */   {
/* 1456:1499 */     return this.ordenFabricacionDao.obtenerDetalleOrdenSalidaMaterialOrdenFabricacion(ordenFabricacion, indicadorNoCerradas);
/* 1457:     */   }
/* 1458:     */   
/* 1459:     */   public List<OperacionOrdenFabricacion> getOperacionOrdenFabricacionPorAnioMes(int idOrganizacion, OrdenFabricacion ordenFabricacion, int anio, int mes, boolean costeoSubOrdenes)
/* 1460:     */   {
/* 1461:1505 */     return this.ordenFabricacionDao.getOperacionOrdenFabricacionPorAnioMes(idOrganizacion, ordenFabricacion, anio, mes, costeoSubOrdenes);
/* 1462:     */   }
/* 1463:     */   
/* 1464:     */   public List<OperacionOrdenFabricacion> getOperacionOrdenFabricacionActualizado(int idOrganizacion, OrdenFabricacion ordenFabricacion, int anio, int mes, boolean costeoSubOrdenes)
/* 1465:     */   {
/* 1466:1514 */     Date fecha = FuncionesUtiles.setAtributoFecha(anio, mes - 1, 1);
/* 1467:1515 */     Date fechaDesde = FuncionesUtiles.getFechaInicioMes(fecha);
/* 1468:1516 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(fecha);
/* 1469:     */     
/* 1470:1518 */     List<OperacionOrdenFabricacion> lista = new ArrayList();
/* 1471:1519 */     RutaFabricacion rutaFabricacion = ordenFabricacion == null ? null : ordenFabricacion.getRutaFabricacion();
/* 1472:     */     
/* 1473:1521 */     List<OperacionProduccion> listaOperacionProduccion = this.rutaFabricacionDao.getListaOperacionProduccionActiva(idOrganizacion, rutaFabricacion, fechaHasta);
/* 1474:     */     
/* 1475:     */ 
/* 1476:1524 */     Map<Integer, Map<Integer, OperacionProduccion>> mapaOperacionProduccionPorRuta = new HashMap();
/* 1477:1525 */     for (OperacionProduccion operacionProduccion : listaOperacionProduccion)
/* 1478:     */     {
/* 1479:1527 */       mapaOperacionProduccion = (Map)mapaOperacionProduccionPorRuta.get(Integer.valueOf(operacionProduccion.getRutaFabricacion().getId()));
/* 1480:1528 */       if (mapaOperacionProduccion == null) {
/* 1481:1529 */         mapaOperacionProduccion = new HashMap();
/* 1482:     */       }
/* 1483:1531 */       mapaOperacionProduccion.put(Integer.valueOf(operacionProduccion.getId()), operacionProduccion);
/* 1484:1532 */       mapaOperacionProduccionPorRuta.put(Integer.valueOf(operacionProduccion.getRutaFabricacion().getId()), mapaOperacionProduccion);
/* 1485:     */     }
/* 1486:     */     Map<Integer, OperacionProduccion> mapaOperacionProduccion;
/* 1487:1537 */     Object listaOperacionOrdenFabricacionMensual = this.ordenFabricacionDao.getOperacionOrdenFabricacionPorAnioMes(idOrganizacion, ordenFabricacion, anio, mes, costeoSubOrdenes);
/* 1488:     */     
/* 1489:1539 */     Map<Integer, Map<Integer, OperacionOrdenFabricacion>> mapaOperacionOrdenFabricacionMensual = new HashMap();
/* 1490:1540 */     for (OperacionOrdenFabricacion operacionOrdenFabricacion : (List)listaOperacionOrdenFabricacionMensual)
/* 1491:     */     {
/* 1492:1542 */       Map<Integer, OperacionOrdenFabricacion> mapaOperacionOrdenFabricacion = (Map)mapaOperacionOrdenFabricacionMensual.get(Integer.valueOf(operacionOrdenFabricacion.getOrdenFabricacion().getId()));
/* 1493:1543 */       if (mapaOperacionOrdenFabricacion == null) {
/* 1494:1544 */         mapaOperacionOrdenFabricacion = new HashMap();
/* 1495:     */       }
/* 1496:1546 */       mapaOperacionOrdenFabricacion.put(Integer.valueOf(operacionOrdenFabricacion.getOperacionProduccion().getId()), operacionOrdenFabricacion);
/* 1497:1547 */       mapaOperacionOrdenFabricacionMensual.put(Integer.valueOf(operacionOrdenFabricacion.getOrdenFabricacion().getId()), mapaOperacionOrdenFabricacion);
/* 1498:     */     }
/* 1499:1550 */     List<OrdenFabricacion> listaOrdenFabricacion = new ArrayList();
/* 1500:1551 */     if (ordenFabricacion != null) {
/* 1501:1553 */       listaOrdenFabricacion.add(ordenFabricacion);
/* 1502:     */     } else {
/* 1503:1556 */       listaOrdenFabricacion = this.ordenFabricacionDao.obtenerOrdenFabricacionParaIngresoHorasMensual(AppUtil.getOrganizacion().getId(), fechaDesde, fechaHasta, costeoSubOrdenes);
/* 1504:     */     }
/* 1505:1559 */     return mapearOperaciones(anio, mes, listaOrdenFabricacion, mapaOperacionProduccionPorRuta, mapaOperacionOrdenFabricacionMensual);
/* 1506:     */   }
/* 1507:     */   
/* 1508:     */   private List<OperacionOrdenFabricacion> mapearOperaciones(int anio, int mes, List<OrdenFabricacion> listaOrdenFabricacion, Map<Integer, Map<Integer, OperacionProduccion>> mapaOperacionProduccionPorRuta, Map<Integer, Map<Integer, OperacionOrdenFabricacion>> mapaOperacionOrdenFabricacionMensual)
/* 1509:     */   {
/* 1510:1576 */     List<OperacionOrdenFabricacion> lista = new ArrayList();
/* 1511:1579 */     for (OrdenFabricacion ordenFabricacion : listaOrdenFabricacion)
/* 1512:     */     {
/* 1513:1581 */       Map<Integer, OperacionProduccion> mapaOperacionProduccion = (Map)mapaOperacionProduccionPorRuta.get(Integer.valueOf(ordenFabricacion.getRutaFabricacion().getId()));
/* 1514:     */       
/* 1515:1583 */       Map<Integer, OperacionOrdenFabricacion> mapaOperacionOrdenFabricacion = (Map)mapaOperacionOrdenFabricacionMensual.get(Integer.valueOf(ordenFabricacion.getId()));
/* 1516:     */       
/* 1517:     */ 
/* 1518:1586 */       boolean creoTodaOperacionOrdenFabricacion = false;
/* 1519:1587 */       if (mapaOperacionOrdenFabricacion == null)
/* 1520:     */       {
/* 1521:1589 */         creoTodaOperacionOrdenFabricacion = true;
/* 1522:1590 */         mapaOperacionOrdenFabricacion = new HashMap();
/* 1523:1593 */         if (mapaOperacionProduccion != null)
/* 1524:     */         {
/* 1525:1594 */           Iterator<Integer> it = mapaOperacionProduccion.keySet().iterator();
/* 1526:1595 */           while (it.hasNext())
/* 1527:     */           {
/* 1528:1597 */             Integer idOperacionProduccion = (Integer)it.next();
/* 1529:1598 */             OperacionProduccion operacionProduccion = (OperacionProduccion)mapaOperacionProduccion.get(idOperacionProduccion);
/* 1530:1599 */             OperacionOrdenFabricacion operacionOrdenFabricacion = new OperacionOrdenFabricacion();
/* 1531:1600 */             operacionOrdenFabricacion.setIdOrganizacion(operacionProduccion.getRutaFabricacion().getIdOrganizacion());
/* 1532:1601 */             operacionOrdenFabricacion.setIdSucursal(operacionProduccion.getRutaFabricacion().getIdSucursal());
/* 1533:1602 */             operacionOrdenFabricacion.setOrdenFabricacion(ordenFabricacion);
/* 1534:1603 */             operacionOrdenFabricacion.setAnio(anio);
/* 1535:1604 */             operacionOrdenFabricacion.setMes(mes);
/* 1536:1605 */             operacionOrdenFabricacion.setOperacionProduccion(operacionProduccion);
/* 1537:1606 */             operacionOrdenFabricacion.setHorasHombre(new BigDecimal(operacionProduccion.getNumeroPersonas()));
/* 1538:1607 */             operacionOrdenFabricacion.setHorasMaquina(new BigDecimal(operacionProduccion.getNumeroMaquinas()));
/* 1539:1608 */             mapaOperacionOrdenFabricacion.put(idOperacionProduccion, operacionOrdenFabricacion);
/* 1540:1609 */             lista.add(operacionOrdenFabricacion);
/* 1541:     */           }
/* 1542:1611 */           mapaOperacionOrdenFabricacionMensual.put(Integer.valueOf(ordenFabricacion.getId()), mapaOperacionOrdenFabricacion);
/* 1543:     */         }
/* 1544:     */       }
/* 1545:1615 */       if (!creoTodaOperacionOrdenFabricacion)
/* 1546:     */       {
/* 1547:1618 */         Iterator<Integer> it = mapaOperacionProduccion.keySet().iterator();
/* 1548:1619 */         while (it.hasNext())
/* 1549:     */         {
/* 1550:1620 */           Integer idOperacionProduccion = (Integer)it.next();
/* 1551:     */           
/* 1552:     */ 
/* 1553:1623 */           OperacionOrdenFabricacion operacionOrdenFabricacion = (OperacionOrdenFabricacion)mapaOperacionOrdenFabricacion.get(idOperacionProduccion);
/* 1554:1624 */           if (operacionOrdenFabricacion != null)
/* 1555:     */           {
/* 1556:1625 */             lista.add(operacionOrdenFabricacion);
/* 1557:     */           }
/* 1558:     */           else
/* 1559:     */           {
/* 1560:1629 */             OperacionProduccion operacionProduccion = (OperacionProduccion)mapaOperacionProduccion.get(idOperacionProduccion);
/* 1561:1630 */             operacionOrdenFabricacion = new OperacionOrdenFabricacion();
/* 1562:1631 */             operacionOrdenFabricacion.setIdOrganizacion(operacionProduccion.getRutaFabricacion().getIdOrganizacion());
/* 1563:1632 */             operacionOrdenFabricacion.setIdSucursal(operacionProduccion.getRutaFabricacion().getIdSucursal());
/* 1564:1633 */             operacionOrdenFabricacion.setOrdenFabricacion(ordenFabricacion);
/* 1565:1634 */             operacionOrdenFabricacion.setAnio(anio);
/* 1566:1635 */             operacionOrdenFabricacion.setMes(mes);
/* 1567:1636 */             operacionOrdenFabricacion.setOperacionProduccion(operacionProduccion);
/* 1568:1637 */             operacionOrdenFabricacion.setHorasHombre(new BigDecimal(operacionProduccion.getNumeroPersonas()));
/* 1569:1638 */             operacionOrdenFabricacion.setHorasMaquina(new BigDecimal(operacionProduccion.getNumeroMaquinas()));
/* 1570:1639 */             lista.add(operacionOrdenFabricacion);
/* 1571:1640 */             mapaOperacionOrdenFabricacion.put(idOperacionProduccion, operacionOrdenFabricacion);
/* 1572:     */           }
/* 1573:     */         }
/* 1574:1645 */         Iterator<Integer> it1 = mapaOperacionOrdenFabricacion.keySet().iterator();
/* 1575:1646 */         while (it1.hasNext())
/* 1576:     */         {
/* 1577:1647 */           Integer idOperacionOrdenFabricacion = (Integer)it1.next();
/* 1578:1649 */           if (!mapaOperacionProduccion.containsKey(idOperacionOrdenFabricacion))
/* 1579:     */           {
/* 1580:1650 */             OperacionOrdenFabricacion operacionOrdenFabricacion = (OperacionOrdenFabricacion)mapaOperacionOrdenFabricacion.get(idOperacionOrdenFabricacion);
/* 1581:1651 */             operacionOrdenFabricacion.setEliminado(true);
/* 1582:1652 */             lista.add(operacionOrdenFabricacion);
/* 1583:     */           }
/* 1584:     */         }
/* 1585:     */       }
/* 1586:     */     }
/* 1587:1659 */     return lista;
/* 1588:     */   }
/* 1589:     */   
/* 1590:     */   public void guardarOperacionOrdenFrabricacion(OperacionOrdenFabricacion operacionOrdenFabricacion)
/* 1591:     */   {
/* 1592:1664 */     this.operacionOrdenFabricacionDao.guardar(operacionOrdenFabricacion);
/* 1593:     */   }
/* 1594:     */   
/* 1595:     */   public List<OrdenFabricacion> getConsultaOrdenFabricacion(Date fechaHasta, TipoCicloProduccionEnum tipoCiclo, EstadoProduccionEnum estado, RutaFabricacion rutaFabricacion)
/* 1596:     */   {
/* 1597:1670 */     return this.ordenFabricacionDao.getConsultaOrdenFabricacion(fechaHasta, tipoCiclo, estado, rutaFabricacion);
/* 1598:     */   }
/* 1599:     */   
/* 1600:     */   public List<OrdenFabricacion> getOrdenFabricacionPorOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 1601:     */   {
/* 1602:1676 */     List<OrdenFabricacionOrdenSalidaMaterial> listaDetalle = this.servicioOrdenSalidaMaterial.getOrdenFabricacionOrdenSalidaMaterialPorOrdenSalidaMaterial(ordenSalidaMaterial);
/* 1603:1677 */     List<OrdenFabricacion> lista = new ArrayList();
/* 1604:1678 */     for (OrdenFabricacionOrdenSalidaMaterial ordenFabricacionOrdenSalidaMaterial : listaDetalle) {
/* 1605:1679 */       lista.add(ordenFabricacionOrdenSalidaMaterial.getOrdenFabricacion());
/* 1606:     */     }
/* 1607:1681 */     return lista;
/* 1608:     */   }
/* 1609:     */   
/* 1610:     */   public void finalizarOrdenPorFormulacion(OrdenFabricacion ordenFabricacion, Date fechaCierre)
/* 1611:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 1612:     */   {
/* 1613:     */     try
/* 1614:     */     {
/* 1615:1695 */       if (ordenFabricacion.getCantidadFabricada().compareTo(BigDecimal.ZERO) <= 0) {
/* 1616:1696 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_CANTIDAD_FABRICACION_ZERO_FINALIZAR_OF", new String[] { ordenFabricacion.getNumero() });
/* 1617:     */       }
/* 1618:1699 */       ordenFabricacion.setListaSubordenes(obtenerListaSubordenFabricacion(ordenFabricacion.getId(), true));
/* 1619:     */       
/* 1620:1701 */       ordenFabricacion.setEstado(EstadoProduccionEnum.FINALIZADA);
/* 1621:     */       
/* 1622:     */ 
/* 1623:1704 */       ordenFabricacion.setFechaCierre(fechaCierre);
/* 1624:     */       
/* 1625:     */ 
/* 1626:     */ 
/* 1627:1708 */       List<MovimientoInventario> listaIngresoFabricacion = this.movimientoInventarioDao.buscarIngresoFabricacionPorOrdenFabricacion(ordenFabricacion);
/* 1628:1710 */       for (Iterator localIterator1 = listaIngresoFabricacion.iterator(); localIterator1.hasNext();)
/* 1629:     */       {
/* 1630:1710 */         movimientoInventario = (MovimientoInventario)localIterator1.next();
/* 1631:1711 */         Integer versionCosteo = Integer.valueOf(0);
/* 1632:1712 */         int i = 0;
/* 1633:     */         
/* 1634:     */ 
/* 1635:1715 */         movimientoInventario = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(movimientoInventario.getId()));
/* 1636:1716 */         for (DetalleMovimientoInventario detalleMovimientoInventario : movimientoInventario.getDetalleMovimientosInventario())
/* 1637:     */         {
/* 1638:1717 */           if (i == 0) {
/* 1639:1718 */             versionCosteo = Integer.valueOf(detalleMovimientoInventario.getProducto().getVersionCosteo() + 1);
/* 1640:     */           }
/* 1641:1720 */           i++;
/* 1642:1721 */           if (!detalleMovimientoInventario.isIndicadorRecibido()) {
/* 1643:1722 */             throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_FALTAN_PRODUCTOS_POR_RECIBIR", new String[] { "" });
/* 1644:     */           }
/* 1645:     */         }
/* 1646:1726 */         this.servicioCosteo.generarCostos(movimientoInventario, ParametrosSistema.isCosteoPorBodega(movimientoInventario.getIdOrganizacion()).booleanValue(), versionCosteo);
/* 1647:     */         
/* 1648:     */ 
/* 1649:     */ 
/* 1650:1730 */         this.servicioMovimientoInventario.contabilizarIngresoFabricacion(movimientoInventario);
/* 1651:     */       }
/* 1652:1732 */       guardar(ordenFabricacion);
/* 1653:     */       
/* 1654:     */ 
/* 1655:     */ 
/* 1656:1736 */       Object listaMateriales = this.servicioProducto.obtenerMaterialesDetalleOrdenFabricacionMaterial(ordenFabricacion, Boolean.valueOf(true));
/* 1657:1737 */       for (DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterial : (List)listaMateriales) {
/* 1658:1738 */         this.servicioVerificadorInventario.actualizarInventarioComprometidoProduccion(detalleOrdenFabricacionMaterial, new Date(), true);
/* 1659:     */       }
/* 1660:     */     }
/* 1661:     */     catch (ExcepcionAS2Financiero e)
/* 1662:     */     {
/* 1663:     */       MovimientoInventario movimientoInventario;
/* 1664:1742 */       this.context.setRollbackOnly();
/* 1665:1743 */       throw e;
/* 1666:     */     }
/* 1667:     */     catch (ExcepcionAS2 e)
/* 1668:     */     {
/* 1669:1745 */       this.context.setRollbackOnly();
/* 1670:1746 */       throw e;
/* 1671:     */     }
/* 1672:     */     catch (AS2Exception e)
/* 1673:     */     {
/* 1674:1748 */       this.context.setRollbackOnly();
/* 1675:1749 */       throw e;
/* 1676:     */     }
/* 1677:     */     catch (Exception e)
/* 1678:     */     {
/* 1679:1751 */       e.printStackTrace();
/* 1680:1752 */       this.context.setRollbackOnly();
/* 1681:1753 */       throw new ExcepcionAS2(e);
/* 1682:     */     }
/* 1683:     */   }
/* 1684:     */   
/* 1685:     */   public void enviarWinCC(OrdenFabricacion ordenFabricacion)
/* 1686:     */     throws AS2Exception
/* 1687:     */   {
/* 1688:1760 */     ordenFabricacion = cargarDetalle(ordenFabricacion.getId());
/* 1689:1763 */     if ((ordenFabricacion.getOrdenFabricacionPadre() != null) && 
/* 1690:1764 */       (!ordenFabricacion.getOrdenFabricacionPadre().getEstado().equals(EstadoProduccionEnum.LANZADA)) && 
/* 1691:1765 */       (!ordenFabricacion.getOrdenFabricacionPadre().getEstado().equals(EstadoProduccionEnum.SUSPENDIDA))) {
/* 1692:1767 */       throw new AS2Exception("msg_error_accion_no_permitida", new String[] {" " + ordenFabricacion.getOrdenFabricacionPadre().getNumero() + " " + ordenFabricacion.getOrdenFabricacionPadre().getEstado() + "" });
/* 1693:     */     }
/* 1694:1771 */     if ((!ordenFabricacion.getEstado().equals(EstadoProduccionEnum.LANZADA)) && 
/* 1695:1772 */       (!ordenFabricacion.getEstado().equals(EstadoProduccionEnum.SUSPENDIDA))) {
/* 1696:1774 */       throw new AS2Exception("msg_error_accion_no_permitida", new String[] {" " + ordenFabricacion.getNumero() + " " + ordenFabricacion.getEstado() + "" });
/* 1697:     */     }
/* 1698:1778 */     if (!ordenFabricacion.isIndicadorFormulada()) {
/* 1699:1779 */       throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.MENSAJE_ERROR_ORDEN_FABRICACION_NO_FORMULADA", new String[] { ordenFabricacion.getNumero() });
/* 1700:     */     }
/* 1701:1783 */     this.ordenFabricacionDao.generarOrdenFabricacionWinCC(ordenFabricacion);
/* 1702:1784 */     ordenFabricacion.setEstado(EstadoProduccionEnum.ENVIADA);
/* 1703:1785 */     ordenFabricacion.setFechaLanzamiento(new Date());
/* 1704:1786 */     this.ordenFabricacionDao.guardar(ordenFabricacion);
/* 1705:     */     
/* 1706:1788 */     ordenFabricacion.getOrdenFabricacionPadre().setEstado(EstadoProduccionEnum.ENVIADA);
/* 1707:1789 */     ordenFabricacion.getOrdenFabricacionPadre().setFechaLanzamiento(new Date());
/* 1708:1790 */     this.ordenFabricacionDao.guardar(ordenFabricacion.getOrdenFabricacionPadre());
/* 1709:     */   }
/* 1710:     */   
/* 1711:     */   public List<Object[]> getReporteOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 1712:     */   {
/* 1713:1795 */     return this.ordenFabricacionDao.getReporteOrdenFabricacion(ordenFabricacion);
/* 1714:     */   }
/* 1715:     */   
/* 1716:     */   public void sincronizarMovimientosProduccion()
/* 1717:     */   {
/* 1718:1800 */     this.ordenFabricacionDao.sincronizarMovimientosProduccion();
/* 1719:     */   }
/* 1720:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl
 * JD-Core Version:    0.7.0.1
 */