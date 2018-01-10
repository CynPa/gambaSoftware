/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DespachoClienteDao;
/*   4:    */ import com.asinfo.as2.dao.DetalleDespachoClienteDao;
/*   5:    */ import com.asinfo.as2.dao.DetalleOrdenDespachoClienteDao;
/*   6:    */ import com.asinfo.as2.dao.DetallePedidoClienteDao;
/*   7:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   8:    */ import com.asinfo.as2.dao.GenericoDao;
/*   9:    */ import com.asinfo.as2.dao.InventarioProductoDao;
/*  10:    */ import com.asinfo.as2.dao.MovimientoInventarioDao;
/*  11:    */ import com.asinfo.as2.dao.OrdenDespachoClienteDao;
/*  12:    */ import com.asinfo.as2.dao.PedidoClienteDao;
/*  13:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  14:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  15:    */ import com.asinfo.as2.entities.Bodega;
/*  16:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  17:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  18:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  19:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  20:    */ import com.asinfo.as2.entities.DetalleOrdenDespachoCliente;
/*  21:    */ import com.asinfo.as2.entities.DetalleOrdenDespachoClientePedidoCliente;
/*  22:    */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*  23:    */ import com.asinfo.as2.entities.Documento;
/*  24:    */ import com.asinfo.as2.entities.Empresa;
/*  25:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  26:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  27:    */ import com.asinfo.as2.entities.LecturaBalanza;
/*  28:    */ import com.asinfo.as2.entities.Lote;
/*  29:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  30:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  31:    */ import com.asinfo.as2.entities.OrdenDespachoCliente;
/*  32:    */ import com.asinfo.as2.entities.PedidoCliente;
/*  33:    */ import com.asinfo.as2.entities.PresentacionProducto;
/*  34:    */ import com.asinfo.as2.entities.Producto;
/*  35:    */ import com.asinfo.as2.entities.Sucursal;
/*  36:    */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*  37:    */ import com.asinfo.as2.entities.TipoPresentacionProducto;
/*  38:    */ import com.asinfo.as2.entities.Unidad;
/*  39:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  40:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  41:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  42:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  43:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  44:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  45:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*  46:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  47:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  48:    */ import com.asinfo.as2.utils.NodoArbol;
/*  49:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*  50:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioOrdenDespachoCliente;
/*  51:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  52:    */ import java.math.BigDecimal;
/*  53:    */ import java.math.RoundingMode;
/*  54:    */ import java.util.ArrayList;
/*  55:    */ import java.util.Date;
/*  56:    */ import java.util.HashMap;
/*  57:    */ import java.util.Iterator;
/*  58:    */ import java.util.List;
/*  59:    */ import java.util.Map;
/*  60:    */ import java.util.Set;
/*  61:    */ import javax.annotation.Resource;
/*  62:    */ import javax.ejb.EJB;
/*  63:    */ import javax.ejb.SessionContext;
/*  64:    */ import javax.ejb.Stateless;
/*  65:    */ import javax.ejb.TransactionAttribute;
/*  66:    */ import javax.ejb.TransactionAttributeType;
/*  67:    */ import javax.ejb.TransactionManagement;
/*  68:    */ import javax.ejb.TransactionManagementType;
/*  69:    */ 
/*  70:    */ @Stateless
/*  71:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  72:    */ public class ServicioOrdenDespachoClienteImpl
/*  73:    */   implements ServicioOrdenDespachoCliente
/*  74:    */ {
/*  75:    */   @EJB
/*  76:    */   private DespachoClienteDao despachoClienteDao;
/*  77:    */   @EJB
/*  78:    */   private DetalleDespachoClienteDao detalleDespachoClienteDao;
/*  79:    */   @EJB
/*  80:    */   private ServicioSecuencia servicioSecuencia;
/*  81:    */   @EJB
/*  82:    */   private ServicioProducto servicioProducto;
/*  83:    */   @EJB
/*  84:    */   private ServicioPedidoCliente servicioPedidoCliente;
/*  85:    */   @EJB
/*  86:    */   private OrdenDespachoClienteDao ordenDespachoClienteDao;
/*  87:    */   @EJB
/*  88:    */   private DetalleOrdenDespachoClienteDao detalleOrdenDespachoClienteDao;
/*  89:    */   @EJB
/*  90:    */   private PedidoClienteDao pedidoClienteDao;
/*  91:    */   @EJB
/*  92:    */   private DetallePedidoClienteDao detallePedidoClienteDao;
/*  93:    */   @EJB
/*  94:    */   private GenericoDao<LecturaBalanza> lecturaBalanzaDao;
/*  95:    */   @EJB
/*  96:    */   private ServicioDocumento servicioDocumento;
/*  97:    */   @EJB
/*  98:    */   private ServicioDespachoCliente servicioDespachoCliente;
/*  99:    */   @EJB
/* 100:    */   private ServicioMovimientoInventario servicioMovimientoInventario;
/* 101:    */   @EJB
/* 102:    */   private GenericoDao<DetalleOrdenDespachoClientePedidoCliente> detalleOrdenDespachoClientePedidoClienteDao;
/* 103:    */   @EJB
/* 104:    */   private MovimientoInventarioDao movimientoInventarioDao;
/* 105:    */   @EJB
/* 106:    */   private GenericoDao<DetalleMovimientoInventario> detalleMovimientoInventarioDao;
/* 107:    */   @EJB
/* 108:    */   private InventarioProductoDao inventarioProductoDao;
/* 109:    */   @EJB
/* 110:    */   private ServicioCosteo servicioCosteo;
/* 111:    */   @EJB
/* 112:    */   private FacturaClienteDao facturaClienteDao;
/* 113:    */   @Resource
/* 114:    */   protected SessionContext context;
/* 115:    */   
/* 116:    */   public void guardar(OrdenDespachoCliente ordenDespachoCliente)
/* 117:    */     throws AS2Exception
/* 118:    */   {
/* 119:129 */     cargarSecuencia(ordenDespachoCliente);
/* 120:    */     
/* 121:131 */     this.ordenDespachoClienteDao.guardar(ordenDespachoCliente);
/* 122:132 */     List<Integer> listaDetalleEliminado = new ArrayList();
/* 123:133 */     for (int i = 0; i < ordenDespachoCliente.getListaDetalleOrdenDespachoCliente().size(); i++)
/* 124:    */     {
/* 125:134 */       DetalleOrdenDespachoCliente detalle = (DetalleOrdenDespachoCliente)ordenDespachoCliente.getListaDetalleOrdenDespachoCliente().get(i);
/* 126:136 */       if (detalle.isEditado())
/* 127:    */       {
/* 128:138 */         boolean nuevo = detalle.getId() == 0;
/* 129:139 */         boolean eliminado = detalle.isEliminado();
/* 130:140 */         if (!nuevo)
/* 131:    */         {
/* 132:141 */           for (DetalleOrdenDespachoClientePedidoCliente dpc : detalle.getListaDetallePedidoCliente()) {
/* 133:142 */             if (eliminado) {
/* 134:143 */               this.detalleOrdenDespachoClientePedidoClienteDao.eliminar(dpc);
/* 135:    */             } else {
/* 136:145 */               this.detalleOrdenDespachoClientePedidoClienteDao.guardar(dpc);
/* 137:    */             }
/* 138:    */           }
/* 139:148 */           for (LecturaBalanza lecturaBalanza : detalle.getListaLecturaBalanza()) {
/* 140:149 */             this.lecturaBalanzaDao.guardar(lecturaBalanza);
/* 141:    */           }
/* 142:    */         }
/* 143:152 */         this.detalleOrdenDespachoClienteDao.guardar(detalle);
/* 144:153 */         listaDetalleEliminado.add(Integer.valueOf(i));
/* 145:155 */         if (nuevo)
/* 146:    */         {
/* 147:156 */           for (LecturaBalanza lecturaBalanza : detalle.getListaLecturaBalanza()) {
/* 148:157 */             this.lecturaBalanzaDao.guardar(lecturaBalanza);
/* 149:    */           }
/* 150:159 */           if (!eliminado) {
/* 151:160 */             for (DetalleOrdenDespachoClientePedidoCliente dpc : detalle.getListaDetallePedidoCliente()) {
/* 152:161 */               this.detalleOrdenDespachoClientePedidoClienteDao.guardar(dpc);
/* 153:    */             }
/* 154:    */           }
/* 155:    */         }
/* 156:    */       }
/* 157:    */     }
/* 158:167 */     for (Integer i : listaDetalleEliminado) {
/* 159:168 */       ordenDespachoCliente.getListaDetalleOrdenDespachoCliente().remove(i);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   private void cargarSecuencia(OrdenDespachoCliente ordenDespachoCliente)
/* 164:    */     throws AS2Exception
/* 165:    */   {
/* 166:175 */     if ((ordenDespachoCliente.getNumero() == null) || (ordenDespachoCliente.getNumero().equals("")))
/* 167:    */     {
/* 168:176 */       String numero = "";
/* 169:    */       try
/* 170:    */       {
/* 171:178 */         numero = this.servicioSecuencia.obtenerSecuenciaDocumento(ordenDespachoCliente.getDocumento().getId(), ordenDespachoCliente.getFecha());
/* 172:    */       }
/* 173:    */       catch (ExcepcionAS2 e)
/* 174:    */       {
/* 175:180 */         throw new AS2Exception(e.getCodigoExcepcion() + e.getMessage());
/* 176:    */       }
/* 177:182 */       ordenDespachoCliente.setNumero(numero);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void eliminar(OrdenDespachoCliente ordenDespachoCliente)
/* 182:    */     throws AS2Exception
/* 183:    */   {
/* 184:188 */     this.ordenDespachoClienteDao.eliminar(ordenDespachoCliente);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public OrdenDespachoCliente buscarPorId(Integer idOrdenDespachoCliente)
/* 188:    */   {
/* 189:194 */     return (OrdenDespachoCliente)this.ordenDespachoClienteDao.buscarPorId(idOrdenDespachoCliente);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public DetalleOrdenDespachoCliente buscarPorId(DetalleOrdenDespachoCliente detalleOrden)
/* 193:    */   {
/* 194:199 */     return (DetalleOrdenDespachoCliente)this.detalleOrdenDespachoClienteDao.buscarPorId(Integer.valueOf(detalleOrden.getId()));
/* 195:    */   }
/* 196:    */   
/* 197:    */   public OrdenDespachoCliente cargarDetalle(Integer idOrdenDespachoCliente)
/* 198:    */   {
/* 199:204 */     return this.ordenDespachoClienteDao.cargarDetalle(idOrdenDespachoCliente.intValue());
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<OrdenDespachoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 203:    */   {
/* 204:210 */     return this.ordenDespachoClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int contarPorCriterio(Map<String, String> filters)
/* 208:    */   {
/* 209:215 */     return this.ordenDespachoClienteDao.contarPorCriterio(filters);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<OrdenDespachoCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 213:    */   {
/* 214:220 */     return this.ordenDespachoClienteDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public List<DetallePedidoCliente> actualizarDetallesPedidoCliente(OrdenDespachoCliente ordenDespachoCliente)
/* 218:    */   {
/* 219:225 */     return this.pedidoClienteDao.buscarDetallePedidoClientePorFechaDespacho(ordenDespachoCliente.getIdOrganizacion(), ordenDespachoCliente
/* 220:226 */       .getTipoOrdenDespacho(), ordenDespachoCliente.getFecha(), ordenDespachoCliente.getTipoPresentacionProducto());
/* 221:    */   }
/* 222:    */   
/* 223:    */   private Map<String, List<DetallePedidoCliente>> crearMapaListaDetallePedidoCliente(List<DetallePedidoCliente> listaDetallePedidoCliente)
/* 224:    */   {
/* 225:230 */     Map<String, List<DetallePedidoCliente>> mapaListaDetallePedidoCliente = new HashMap();
/* 226:231 */     for (DetallePedidoCliente detallePedidoCliente : listaDetallePedidoCliente)
/* 227:    */     {
/* 228:232 */       String key = detallePedidoCliente.getProducto().getId() + "-" + detallePedidoCliente.getCantidadEmbalajeDespacho();
/* 229:233 */       if (mapaListaDetallePedidoCliente.containsKey(key))
/* 230:    */       {
/* 231:234 */         ((List)mapaListaDetallePedidoCliente.get(key)).add(detallePedidoCliente);
/* 232:    */       }
/* 233:    */       else
/* 234:    */       {
/* 235:236 */         List<DetallePedidoCliente> lista = new ArrayList();
/* 236:237 */         lista.add(detallePedidoCliente);
/* 237:238 */         mapaListaDetallePedidoCliente.put(key, lista);
/* 238:    */       }
/* 239:    */     }
/* 240:241 */     return mapaListaDetallePedidoCliente;
/* 241:    */   }
/* 242:    */   
/* 243:    */   private Map<String, DetalleOrdenDespachoCliente> crearMapaDetalleOrdenDespachoCliente(List<DetalleOrdenDespachoCliente> listaDetalleOrdenDespachoCliente)
/* 244:    */   {
/* 245:246 */     Map<String, DetalleOrdenDespachoCliente> mapaDetalleOrdenDespachoCliente = new HashMap();
/* 246:247 */     for (DetalleOrdenDespachoCliente detalleOrdenDespachoCliente : listaDetalleOrdenDespachoCliente)
/* 247:    */     {
/* 248:248 */       String key = detalleOrdenDespachoCliente.getProducto().getId() + "-" + detalleOrdenDespachoCliente.getCantidadEmbalajeDespacho();
/* 249:249 */       if (mapaDetalleOrdenDespachoCliente.containsKey(key))
/* 250:    */       {
/* 251:250 */         DetalleOrdenDespachoCliente detalle = (DetalleOrdenDespachoCliente)mapaDetalleOrdenDespachoCliente.get(key);
/* 252:251 */         detalle.getListaDetallePedidoCliente().addAll(detalleOrdenDespachoCliente.getListaDetallePedidoCliente());
/* 253:252 */         detalle.getListaLecturaBalanza().addAll(detalleOrdenDespachoCliente.getListaLecturaBalanza());
/* 254:253 */         detalle.setCantidad(detalle.getCantidad().add(detalleOrdenDespachoCliente.getCantidad()));
/* 255:254 */         detalle.setCantidadUnidadDespacho(detalle.getCantidadUnidadDespacho().add(detalleOrdenDespachoCliente.getCantidadUnidadDespacho()));
/* 256:    */       }
/* 257:    */       else
/* 258:    */       {
/* 259:256 */         mapaDetalleOrdenDespachoCliente.put(key, detalleOrdenDespachoCliente);
/* 260:    */       }
/* 261:    */     }
/* 262:259 */     return mapaDetalleOrdenDespachoCliente;
/* 263:    */   }
/* 264:    */   
/* 265:    */   private Map<Integer, DetallePedidoCliente> crearMapaDetallePedidoCliente(Map<String, List<DetallePedidoCliente>> mapaListaDetallePedidoCliente)
/* 266:    */   {
/* 267:263 */     Map<Integer, DetallePedidoCliente> mapaDetallePedidoCliente = new HashMap();
/* 268:264 */     for (List<DetallePedidoCliente> listaDetallePedidoCliente : mapaListaDetallePedidoCliente.values()) {
/* 269:265 */       for (DetallePedidoCliente detallePedidoCliente : listaDetallePedidoCliente) {
/* 270:266 */         mapaDetallePedidoCliente.put(Integer.valueOf(detallePedidoCliente.getId()), detallePedidoCliente);
/* 271:    */       }
/* 272:    */     }
/* 273:269 */     return mapaDetallePedidoCliente;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void actualizarPedidosClientePorDetalle(OrdenDespachoCliente ordenDespachoCliente)
/* 277:    */     throws AS2Exception
/* 278:    */   {
/* 279:275 */     List<DetallePedidoCliente> listaDetallePedidoCliente = actualizarDetallesPedidoCliente(ordenDespachoCliente);
/* 280:    */     
/* 281:277 */     Map<String, List<DetallePedidoCliente>> mapaListaDetallePedidoCliente = crearMapaListaDetallePedidoCliente(listaDetallePedidoCliente);
/* 282:    */     
/* 283:279 */     Map<Integer, DetallePedidoCliente> mapaDetallePedidoCliente = crearMapaDetallePedidoCliente(mapaListaDetallePedidoCliente);
/* 284:    */     
/* 285:281 */     Map<String, DetalleOrdenDespachoCliente> mapaDetalleOrdenDespachoCliente = crearMapaDetalleOrdenDespachoCliente(ordenDespachoCliente
/* 286:282 */       .getListaDetalleOrdenDespachoCliente());
/* 287:    */     
/* 288:    */ 
/* 289:285 */     Iterator<String> it = mapaListaDetallePedidoCliente.keySet().iterator();
/* 290:    */     boolean existe;
/* 291:    */     DetalleOrdenDespachoCliente detalleOrden;
/* 292:286 */     while (it.hasNext())
/* 293:    */     {
/* 294:287 */       String key = (String)it.next();
/* 295:    */       DetalleOrdenDespachoCliente detalleOrden;
/* 296:289 */       if (mapaDetalleOrdenDespachoCliente.containsKey(key))
/* 297:    */       {
/* 298:290 */         detalleOrden = (DetalleOrdenDespachoCliente)mapaDetalleOrdenDespachoCliente.get(key);
/* 299:291 */         for (DetallePedidoCliente detallePedidoCliente : (List)mapaListaDetallePedidoCliente.get(key))
/* 300:    */         {
/* 301:292 */           existe = false;
/* 302:293 */           List<DetalleOrdenDespachoClientePedidoCliente> listaDetallePedidoClienteEliminar = new ArrayList();
/* 303:294 */           for (DetalleOrdenDespachoClientePedidoCliente detalle : detalleOrden.getListaDetallePedidoCliente())
/* 304:    */           {
/* 305:296 */             if (detalle.getDetallePedidoCliente().getId() == detallePedidoCliente.getId())
/* 306:    */             {
/* 307:297 */               detalle.setDetallePedidoCliente(detallePedidoCliente);
/* 308:298 */               existe = true;
/* 309:299 */               break;
/* 310:    */             }
/* 311:302 */             if (!mapaDetallePedidoCliente.containsKey(Integer.valueOf(detalle.getDetallePedidoCliente().getId()))) {
/* 312:303 */               listaDetallePedidoClienteEliminar.add(detalle);
/* 313:    */             }
/* 314:    */           }
/* 315:308 */           for (DetalleOrdenDespachoClientePedidoCliente detallePedidoClienteEliminar : listaDetallePedidoClienteEliminar)
/* 316:    */           {
/* 317:309 */             this.detalleOrdenDespachoClientePedidoClienteDao.eliminar(detallePedidoClienteEliminar);
/* 318:310 */             detalleOrden.getListaDetallePedidoCliente().remove(detallePedidoClienteEliminar);
/* 319:    */           }
/* 320:313 */           if (!existe)
/* 321:    */           {
/* 322:314 */             DetalleOrdenDespachoClientePedidoCliente detalleOrdenDespachoClientePedidoCliente = new DetalleOrdenDespachoClientePedidoCliente();
/* 323:315 */             detalleOrdenDespachoClientePedidoCliente.setIdOrganizacion(detalleOrden.getIdOrganizacion());
/* 324:316 */             detalleOrdenDespachoClientePedidoCliente.setIdSucursal(detalleOrden.getIdSucursal());
/* 325:317 */             detalleOrdenDespachoClientePedidoCliente.setDetalleOrdenDespachoCliente(detalleOrden);
/* 326:318 */             detalleOrdenDespachoClientePedidoCliente.setDetallePedidoCliente(detallePedidoCliente);
/* 327:319 */             detalleOrden.getListaDetallePedidoCliente().add(detalleOrdenDespachoClientePedidoCliente);
/* 328:    */           }
/* 329:    */         }
/* 330:    */       }
/* 331:    */       else
/* 332:    */       {
/* 333:326 */         DetallePedidoCliente detallePedidoCliente = (DetallePedidoCliente)((List)mapaListaDetallePedidoCliente.get(key)).get(0);
/* 334:327 */         detalleOrden = new DetalleOrdenDespachoCliente();
/* 335:328 */         detalleOrden.setProducto(detallePedidoCliente.getProducto());
/* 336:329 */         detalleOrden.setIndicadorManejoPeso(detallePedidoCliente.getProducto().isIndicadorManejoPeso());
/* 337:330 */         detalleOrden.setIdOrganizacion(ordenDespachoCliente.getIdOrganizacion());
/* 338:331 */         detalleOrden.setIdSucursal(ordenDespachoCliente.getSucursal().getId());
/* 339:332 */         detalleOrden.setCantidadEmbalajeDespacho(detallePedidoCliente.getCantidadEmbalajeDespacho());
/* 340:333 */         detalleOrden.setOrdenDespachoCliente(ordenDespachoCliente);
/* 341:334 */         Bodega bodegaTrabajo = this.servicioProducto.obtenerBodegaTrabajoProducto(detalleOrden.getProducto(), 
/* 342:335 */           Integer.valueOf(ordenDespachoCliente.getSucursal().getId()));
/* 343:336 */         detalleOrden.setBodega(bodegaTrabajo);
/* 344:337 */         if (detalleOrden.getBodega() == null) {
/* 345:340 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_PARAMETRIZAR_BODEGA_TRABAJO", new String[] {"( " + (detalleOrden.getProducto() != null ? detalleOrden.getProducto().getNombre() : "") + " - " + ordenDespachoCliente.getSucursal().getNombre() + " )." });
/* 346:    */         }
/* 347:342 */         ordenDespachoCliente.getListaDetalleOrdenDespachoCliente().add(detalleOrden);
/* 348:344 */         for (DetallePedidoCliente detallePedido : (List)mapaListaDetallePedidoCliente.get(key))
/* 349:    */         {
/* 350:345 */           DetalleOrdenDespachoClientePedidoCliente detalleOrdenDespachoClientePedidoCliente = new DetalleOrdenDespachoClientePedidoCliente();
/* 351:346 */           detalleOrdenDespachoClientePedidoCliente.setIdOrganizacion(detalleOrden.getIdOrganizacion());
/* 352:347 */           detalleOrdenDespachoClientePedidoCliente.setIdSucursal(detalleOrden.getIdSucursal());
/* 353:348 */           detalleOrdenDespachoClientePedidoCliente.setDetalleOrdenDespachoCliente(detalleOrden);
/* 354:349 */           detalleOrdenDespachoClientePedidoCliente.setDetallePedidoCliente(detallePedido);
/* 355:350 */           detalleOrden.getListaDetallePedidoCliente().add(detalleOrdenDespachoClientePedidoCliente);
/* 356:    */         }
/* 357:    */       }
/* 358:    */     }
/* 359:355 */     actualizarReparticionPedidos(ordenDespachoCliente);
/* 360:    */     
/* 361:357 */     guardar(ordenDespachoCliente);
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void actualizarReparticionPedidos(OrdenDespachoCliente ordenDespachoCliente)
/* 365:    */     throws AS2Exception
/* 366:    */   {
/* 367:362 */     for (DetalleOrdenDespachoCliente detalleOrden : ordenDespachoCliente.getListaDetalleOrdenDespachoCliente()) {
/* 368:363 */       actualizarReparticionPedidos(detalleOrden);
/* 369:    */     }
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void actualizarReparticionPedidos(DetalleOrdenDespachoCliente detalleOrden)
/* 373:    */     throws AS2Exception
/* 374:    */   {
/* 375:    */     BigDecimal cantidadARepartir;
/* 376:    */     BigDecimal cantidadRepartir;
/* 377:370 */     if ((!detalleOrden.isEliminado()) && (!detalleOrden.getListaDetallePedidoCliente().isEmpty()))
/* 378:    */     {
/* 379:371 */       cantidadARepartir = detalleOrden.getCantidad();
/* 380:372 */       BigDecimal cantidadPedido = BigDecimal.ZERO;
/* 381:373 */       for (DetalleOrdenDespachoClientePedidoCliente detallePedido : detalleOrden.getListaDetallePedidoCliente()) {
/* 382:375 */         if (!detallePedido.getDetallePedidoCliente().getPedidoCliente().getEstado().equals(Estado.ANULADO)) {
/* 383:376 */           cantidadPedido = cantidadPedido.add(detallePedido.getDetallePedidoCliente().getCantidadPorDespachar());
/* 384:    */         }
/* 385:    */       }
/* 386:381 */       BigDecimal porcientoPermitido = new BigDecimal(detalleOrden.getOrdenDespachoCliente().getTipoOrdenDespacho().getIndicadorAplicaPorcientoAdicionalPedidos().booleanValue() ? detalleOrden.getProducto().getPorCientoDespachoSuperaPedido() : 0);
/* 387:382 */       BigDecimal cantidadAdicional = porcientoPermitido.multiply(cantidadPedido).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
/* 388:383 */       detalleOrden.setCantidadPedido(cantidadPedido.add(cantidadAdicional)
/* 389:384 */         .setScale(detalleOrden.getProducto().getUnidadVenta().getNumeroDecimales().intValue(), RoundingMode.HALF_UP));
/* 390:    */       
/* 391:386 */       BigDecimal cantidadRepartida = BigDecimal.ZERO;
/* 392:387 */       BigDecimal mayorProporcion = BigDecimal.ZERO;
/* 393:    */       
/* 394:389 */       DetalleOrdenDespachoClientePedidoCliente detalleMayorProporcion = null;
/* 395:    */       
/* 396:391 */       List<DetalleOrdenDespachoClientePedidoCliente> listaOrdenada = new ArrayList();
/* 397:393 */       for (DetalleOrdenDespachoClientePedidoCliente detallePedido : detalleOrden.getListaDetallePedidoCliente()) {
/* 398:394 */         if (!detallePedido.getDetallePedidoCliente().getPedidoCliente().getEstado().equals(Estado.ANULADO))
/* 399:    */         {
/* 400:395 */           BigDecimal proporcion = detallePedido.getDetallePedidoCliente().getCantidadPorDespachar().divide(cantidadPedido, 20, RoundingMode.HALF_UP);
/* 401:    */           
/* 402:397 */           detallePedido.setProporcion(proporcion);
/* 403:399 */           if (proporcion.compareTo(mayorProporcion) > 0)
/* 404:    */           {
/* 405:400 */             detalleMayorProporcion = detallePedido;
/* 406:401 */             mayorProporcion = proporcion;
/* 407:    */           }
/* 408:405 */           cantidadDetalle = proporcion.multiply(cantidadARepartir).setScale(detalleOrden.getProducto().getUnidadVenta().getNumeroDecimales().intValue(), RoundingMode.HALF_UP);
/* 409:406 */           if (detalleOrden.getProducto().getMultiploPedido().intValue() > 0)
/* 410:    */           {
/* 411:407 */             BigDecimal multiplo = new BigDecimal(detalleOrden.getProducto().getMultiploPedido().intValue());
/* 412:408 */             BigDecimal cantidadARepartirMultiplo = cantidadARepartir.divide(multiplo, 0, RoundingMode.DOWN);
/* 413:409 */             BigDecimal proporcionMultiplo = proporcion.multiply(cantidadARepartirMultiplo);
/* 414:410 */             if (proporcionMultiplo.setScale(0, RoundingMode.HALF_UP).subtract(proporcionMultiplo).compareTo(new BigDecimal(0.05D)) < 0) {
/* 415:411 */               proporcionMultiplo = proporcionMultiplo.setScale(0, RoundingMode.HALF_UP);
/* 416:    */             } else {
/* 417:413 */               proporcionMultiplo = proporcionMultiplo.setScale(0, RoundingMode.DOWN);
/* 418:    */             }
/* 419:415 */             cantidadDetalle = proporcionMultiplo.multiply(multiplo);
/* 420:    */           }
/* 421:422 */           cantidadRepartida = cantidadRepartida.add(cantidadDetalle);
/* 422:423 */           detallePedido.getDetallePedidoCliente().setCantidadADespachar(cantidadDetalle);
/* 423:424 */           detallePedido.setCantidad(cantidadDetalle);
/* 424:425 */           listaOrdenada.add(detallePedido);
/* 425:    */         }
/* 426:    */       }
/* 427:    */       BigDecimal cantidadDetalle;
/* 428:429 */       FuncionesUtiles.ordenaLista(listaOrdenada, "proporcion", false);
/* 429:    */       
/* 430:    */ 
/* 431:    */ 
/* 432:    */ 
/* 433:434 */       BigDecimal diferencia = cantidadARepartir.subtract(cantidadRepartida).setScale(detalleOrden.getProducto().getUnidadVenta().getNumeroDecimales().intValue(), RoundingMode.HALF_UP);
/* 434:    */       
/* 435:436 */       BigDecimal minima = BigDecimal.ZERO;
/* 436:437 */       if (detalleOrden.getProducto().getMultiploPedido().intValue() > 0) {
/* 437:438 */         minima = new BigDecimal(detalleOrden.getProducto().getMultiploPedido().intValue());
/* 438:    */       }
/* 439:441 */       while ((diferencia.compareTo(minima) >= 0) && (diferencia.compareTo(BigDecimal.ZERO) > 0))
/* 440:    */       {
/* 441:442 */         cantidadRepartir = diferencia;
/* 442:443 */         if (detalleOrden.getProducto().getMultiploPedido().intValue() > 0) {
/* 443:444 */           cantidadRepartir = minima;
/* 444:    */         }
/* 445:446 */         for (DetalleOrdenDespachoClientePedidoCliente detalle : listaOrdenada)
/* 446:    */         {
/* 447:447 */           if (diferencia.compareTo(minima) < 0) {
/* 448:    */             break;
/* 449:    */           }
/* 450:449 */           detalle.getDetallePedidoCliente().setCantidadADespachar(detalle.getDetallePedidoCliente().getCantidadADespachar().add(cantidadRepartir));
/* 451:450 */           detalle.setCantidad(detalle.getCantidad().add(cantidadRepartir));
/* 452:    */           
/* 453:452 */           cantidadRepartida = cantidadRepartida.add(cantidadRepartir);
/* 454:453 */           diferencia = diferencia.subtract(cantidadRepartir);
/* 455:    */         }
/* 456:    */       }
/* 457:459 */       if (diferencia.compareTo(BigDecimal.ZERO) < 0)
/* 458:    */       {
/* 459:461 */         detalleMayorProporcion.getDetallePedidoCliente().setCantidadADespachar(detalleMayorProporcion.getDetallePedidoCliente().getCantidadADespachar().add(diferencia));
/* 460:462 */         detalleMayorProporcion.setCantidad(detalleMayorProporcion.getCantidad().add(diferencia));
/* 461:    */         
/* 462:464 */         cantidadRepartida = cantidadRepartida.add(diferencia);
/* 463:465 */         diferencia = diferencia.subtract(diferencia);
/* 464:    */       }
/* 465:469 */       for (DetalleOrdenDespachoClientePedidoCliente detallePedido : detalleOrden.getListaDetallePedidoCliente()) {
/* 466:470 */         if ((!detallePedido.getDetallePedidoCliente().getPedidoCliente().getEstado().equals(Estado.ANULADO)) && 
/* 467:471 */           (cantidadARepartir != null) && (cantidadARepartir.compareTo(BigDecimal.ZERO) != 0))
/* 468:    */         {
/* 469:472 */           BigDecimal proporcionRepartidaDetalle = detallePedido.getCantidad().divide(cantidadARepartir, 20, RoundingMode.HALF_UP);
/* 470:473 */           BigDecimal pesoRepartirDetalle = proporcionRepartidaDetalle.multiply(detalleOrden.getPesoMateriaPrima()).setScale(20, RoundingMode.HALF_UP);
/* 471:    */           
/* 472:475 */           detallePedido.setPesoMateriaPrima(pesoRepartirDetalle);
/* 473:    */         }
/* 474:    */       }
/* 475:    */     }
/* 476:    */   }
/* 477:    */   
/* 478:    */   public List<DespachoCliente> generarDespachos(OrdenDespachoCliente ordenDespachoCliente)
/* 479:    */     throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2
/* 480:    */   {
/* 481:498 */     validarOrdenDespachoAntesgenerarDespacho(ordenDespachoCliente);
/* 482:499 */     List<DespachoCliente> listaDespachoCliente = new ArrayList();
/* 483:    */     
/* 484:501 */     Map<String, DespachoCliente> mapaDespachos = new HashMap();
/* 485:503 */     for (DetalleOrdenDespachoCliente detalleOrden : ordenDespachoCliente.getListaDetalleOrdenDespachoCliente()) {
/* 486:504 */       if (detalleOrden.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
/* 487:505 */         for (DetalleOrdenDespachoClientePedidoCliente detallePedido : detalleOrden.getListaDetallePedidoCliente())
/* 488:    */         {
/* 489:507 */           String key = detallePedido.getDetallePedidoCliente().getPedidoCliente().getEmpresa().getId() + " - " + detallePedido.getDetallePedidoCliente().getPedidoCliente().getNumero();
/* 490:508 */           if ((detallePedido.getDetallePedidoCliente().getCantidadADespachar() != null) && 
/* 491:509 */             (detallePedido.getDetallePedidoCliente().getCantidadADespachar().compareTo(BigDecimal.ZERO) != 0))
/* 492:    */           {
/* 493:510 */             DespachoCliente despachoCliente = crearDespachoCliente((DespachoCliente)mapaDespachos.get(key), detallePedido);
/* 494:511 */             mapaDespachos.put(key, despachoCliente);
/* 495:    */           }
/* 496:    */         }
/* 497:    */       }
/* 498:    */     }
/* 499:516 */     listaDespachoCliente.addAll(mapaDespachos.values());
/* 500:517 */     return listaDespachoCliente;
/* 501:    */   }
/* 502:    */   
/* 503:    */   private DespachoCliente crearDespachoCliente(DespachoCliente despachoCliente, DetalleOrdenDespachoClientePedidoCliente detalleOrdenDespachoPedidoCliente)
/* 504:    */   {
/* 505:523 */     if (despachoCliente == null)
/* 506:    */     {
/* 507:524 */       despachoCliente = new DespachoCliente();
/* 508:525 */       despachoCliente.setIdOrganizacion(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getPedidoCliente().getIdOrganizacion());
/* 509:526 */       despachoCliente.setSucursal(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getPedidoCliente().getSucursal());
/* 510:527 */       despachoCliente.setOrdenDespachoCliente(detalleOrdenDespachoPedidoCliente.getDetalleOrdenDespachoCliente().getOrdenDespachoCliente());
/* 511:528 */       despachoCliente.setEmpresa(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getPedidoCliente().getEmpresa());
/* 512:529 */       despachoCliente.setDireccionEmpresa(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getPedidoCliente().getDireccionEmpresa());
/* 513:530 */       despachoCliente.setFecha(detalleOrdenDespachoPedidoCliente.getDetalleOrdenDespachoCliente().getOrdenDespachoCliente().getFecha());
/* 514:531 */       despachoCliente.setPedidoCliente(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getPedidoCliente());
/* 515:532 */       despachoCliente.setTransportista(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getPedidoCliente().getTransportista());
/* 516:533 */       despachoCliente.setSubempresa(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getPedidoCliente().getSubempresa());
/* 517:534 */       despachoCliente.setEstado(Estado.PROCESADO);
/* 518:535 */       despachoCliente.setProyecto(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getPedidoCliente().getProyecto());
/* 519:536 */       despachoCliente.setDescripcion(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getPedidoCliente().getDescripcion());
/* 520:537 */       despachoCliente.setNumero("");
/* 521:538 */       List<Documento> listaDocumentoDespacho = new ArrayList();
/* 522:    */       try
/* 523:    */       {
/* 524:540 */         listaDocumentoDespacho = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.DESPACHO_BODEGA, detalleOrdenDespachoPedidoCliente
/* 525:541 */           .getDetallePedidoCliente().getPedidoCliente().getIdOrganizacion());
/* 526:    */       }
/* 527:    */       catch (ExcepcionAS2 localExcepcionAS2) {}
/* 528:544 */       if (!listaDocumentoDespacho.isEmpty())
/* 529:    */       {
/* 530:545 */         Documento documento = (Documento)listaDocumentoDespacho.get(0);
/* 531:546 */         despachoCliente.setDocumento(documento);
/* 532:    */       }
/* 533:    */     }
/* 534:550 */     Producto producto = detalleOrdenDespachoPedidoCliente.getDetalleOrdenDespachoCliente().getProducto();
/* 535:551 */     String descripcionDetalle = "";
/* 536:552 */     if (detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getDescripcion() != null) {
/* 537:553 */       descripcionDetalle = descripcionDetalle + "Pedido( " + detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getDescripcion() + " ). ";
/* 538:    */     }
/* 539:555 */     if ((producto.getPresentacionProducto() != null) && (producto.getPresentacionProducto().getTipoPresentacionProducto() != null)) {
/* 540:556 */       descripcionDetalle = descripcionDetalle + "Tipo Presentacion ( " + producto.getPresentacionProducto().getTipoPresentacionProducto().getNombre() + " ).";
/* 541:    */     }
/* 542:558 */     DetalleDespachoCliente detalleDespacho = new DetalleDespachoCliente();
/* 543:559 */     despachoCliente.getListaDetalleDespachoCliente().add(detalleDespacho);
/* 544:    */     
/* 545:561 */     detalleDespacho.setDespachoCliente(despachoCliente);
/* 546:562 */     detalleDespacho.setDetallePedidoCliente(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente());
/* 547:563 */     detalleDespacho.setProducto(producto);
/* 548:564 */     detalleDespacho.setUnidadVenta(producto.getUnidadVenta());
/* 549:565 */     detalleDespacho
/* 550:566 */       .setCantidad(detalleOrdenDespachoPedidoCliente.getDetallePedidoCliente().getCantidadADespachar().setScale(4, RoundingMode.HALF_UP));
/* 551:567 */     detalleDespacho.setCantidadEmbalaje(detalleOrdenDespachoPedidoCliente
/* 552:568 */       .getDetallePedidoCliente().getCantidadEmbalajeDespacho().setScale(2, RoundingMode.HALF_UP));
/* 553:569 */     detalleDespacho.setDescripcion(descripcionDetalle);
/* 554:570 */     InventarioProducto inventarioProducto = new InventarioProducto();
/* 555:571 */     inventarioProducto.setOperacion(despachoCliente.getDocumento().getOperacion());
/* 556:572 */     inventarioProducto.setProducto(detalleDespacho.getProducto());
/* 557:573 */     inventarioProducto.setLote(detalleOrdenDespachoPedidoCliente.getDetalleOrdenDespachoCliente().getLote());
/* 558:574 */     detalleDespacho.setInventarioProducto(inventarioProducto);
/* 559:575 */     detalleDespacho.setBodega(detalleOrdenDespachoPedidoCliente.getDetalleOrdenDespachoCliente().getBodega());
/* 560:576 */     detalleDespacho.setPesoMateriaPrima(detalleOrdenDespachoPedidoCliente.getPesoMateriaPrima());
/* 561:    */     
/* 562:    */ 
/* 563:579 */     BigDecimal saldo = this.servicioProducto.getSaldo(detalleDespacho.getProducto().getIdProducto(), detalleDespacho.getBodega().getIdBodega(), despachoCliente
/* 564:580 */       .getFecha());
/* 565:581 */     detalleDespacho.setSaldo(saldo);
/* 566:    */     
/* 567:583 */     return despachoCliente;
/* 568:    */   }
/* 569:    */   
/* 570:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 571:    */   public MovimientoInventario guardarDespachosCliente(List<DespachoCliente> listaDespachoCliente, OrdenDespachoCliente ordenDespachoCliente)
/* 572:    */     throws ExcepcionAS2Inventario, AS2Exception, ExcepcionAS2
/* 573:    */   {
/* 574:    */     try
/* 575:    */     {
/* 576:591 */       MovimientoInventario egresoTransformacion = corteProduccionRapida(ordenDespachoCliente);
/* 577:593 */       for (DespachoCliente despachoCliente : listaDespachoCliente) {
/* 578:594 */         this.servicioDespachoCliente.guardar(despachoCliente);
/* 579:    */       }
/* 580:597 */       OrdenDespachoCliente ordenDespachoClienteAux = (OrdenDespachoCliente)this.ordenDespachoClienteDao.buscarPorId(Integer.valueOf(ordenDespachoCliente.getId()));
/* 581:598 */       if (ordenDespachoClienteAux.getEstado().equals(Estado.PROCESADO)) {
/* 582:599 */         throw new ExcepcionAS2Inventario("msg_error_orden_despacho_cliente");
/* 583:    */       }
/* 584:602 */       ordenDespachoCliente.setEstado(Estado.PROCESADO);
/* 585:603 */       this.ordenDespachoClienteDao.guardar(ordenDespachoCliente);
/* 586:    */       
/* 587:605 */       return egresoTransformacion;
/* 588:    */     }
/* 589:    */     catch (AS2Exception e)
/* 590:    */     {
/* 591:608 */       this.context.setRollbackOnly();
/* 592:609 */       throw e;
/* 593:    */     }
/* 594:    */     catch (ExcepcionAS2Inventario e)
/* 595:    */     {
/* 596:611 */       this.context.setRollbackOnly();
/* 597:612 */       throw e;
/* 598:    */     }
/* 599:    */     catch (ExcepcionAS2 e)
/* 600:    */     {
/* 601:614 */       this.context.setRollbackOnly();
/* 602:615 */       throw e;
/* 603:    */     }
/* 604:    */     catch (Exception e)
/* 605:    */     {
/* 606:617 */       this.context.setRollbackOnly();
/* 607:618 */       throw new ExcepcionAS2(e);
/* 608:    */     }
/* 609:    */   }
/* 610:    */   
/* 611:    */   private void validarSaldo(Producto prodcuto, Bodega bodega, Date fecha, BigDecimal cantidad)
/* 612:    */     throws AS2Exception
/* 613:    */   {
/* 614:623 */     BigDecimal saldo = this.servicioProducto.getSaldo(prodcuto.getIdProducto(), bodega.getIdBodega(), fecha);
/* 615:626 */     if (saldo.compareTo(cantidad) < 0) {
/* 616:627 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_SALDO_PRODUCTO_BODEGA", new String[] { prodcuto.getNombre(), bodega.getNombre(), cantidad + " > " + saldo });
/* 617:    */     }
/* 618:    */   }
/* 619:    */   
/* 620:    */   public void validarOrdenDespachoAntesgenerarDespacho(OrdenDespachoCliente ordenDespachoCliente)
/* 621:    */     throws AS2Exception
/* 622:    */   {
/* 623:633 */     for (DetalleOrdenDespachoCliente detalleOrden : ordenDespachoCliente.getListaDetalleOrdenDespachoCliente()) {
/* 624:634 */       if (detalleOrden.getCantidad().compareTo(BigDecimal.ZERO) > 0)
/* 625:    */       {
/* 626:635 */         if (detalleOrden.getCantidadPedidos() == 0) {
/* 627:636 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_DETALLE_SIN_PEDIDOS", new String[] { detalleOrden.getProducto().getNombre() });
/* 628:    */         }
/* 629:638 */         if (!detalleOrden.getProducto().isIndicadorProduccionRapida())
/* 630:    */         {
/* 631:639 */           validarSaldo(detalleOrden.getProducto(), detalleOrden.getBodega(), ordenDespachoCliente.getFecha(), detalleOrden.getCantidad());
/* 632:    */         }
/* 633:    */         else
/* 634:    */         {
/* 635:641 */           detalleOrden.getProducto().setCantidadProducir(detalleOrden.getCantidad());
/* 636:642 */           NodoArbol<Producto> arbol = this.servicioProducto.obtenerArbolComponentes(detalleOrden.getProducto());
/* 637:643 */           for (NodoArbol<Producto> nodo : arbol.getHijos())
/* 638:    */           {
/* 639:644 */             BigDecimal cantidad = ((Producto)nodo.getValor()).getCantidadProducir();
/* 640:645 */             Producto material = (Producto)nodo.getValor();
/* 641:646 */             Bodega bodegaTrabajoMaterial = this.servicioProducto.obtenerBodegaTrabajoProducto(material, 
/* 642:647 */               Integer.valueOf(ordenDespachoCliente.getSucursal().getId()));
/* 643:648 */             if (bodegaTrabajoMaterial != null) {
/* 644:649 */               validarSaldo(material, bodegaTrabajoMaterial, ordenDespachoCliente.getFecha(), cantidad);
/* 645:    */             } else {
/* 646:651 */               throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_PARAMETRIZAR_BODEGA_TRABAJO", new String[] { material.getNombre() });
/* 647:    */             }
/* 648:    */           }
/* 649:    */         }
/* 650:656 */         if (detalleOrden.getCantidad().compareTo(detalleOrden.getCantidadPedido()) > 0)
/* 651:    */         {
/* 652:659 */           BigDecimal porcientoPermitido = new BigDecimal(ordenDespachoCliente.getTipoOrdenDespacho().getIndicadorAplicaPorcientoAdicionalPedidos().booleanValue() ? detalleOrden.getProducto().getPorCientoDespachoSuperaPedido() : 0);
/* 653:    */           
/* 654:661 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_DESPACHO_SUPERA_PEDIDO_PORCIENTO_PERMITIDO", new String[] { detalleOrden.getProducto().getNombre(), porcientoPermitido.toString() });
/* 655:    */         }
/* 656:    */       }
/* 657:    */     }
/* 658:    */   }
/* 659:    */   
/* 660:    */   public void validarAgregarPeso(DetalleOrdenDespachoCliente detalleOrden)
/* 661:    */     throws AS2Exception
/* 662:    */   {
/* 663:669 */     actualizarReparticionPedidos(detalleOrden);
/* 664:672 */     if (detalleOrden.getCantidad().compareTo(detalleOrden.getCantidadPedido()) > 0)
/* 665:    */     {
/* 666:675 */       BigDecimal porcientoPermitido = new BigDecimal(detalleOrden.getOrdenDespachoCliente().getTipoOrdenDespacho().getIndicadorAplicaPorcientoAdicionalPedidos().booleanValue() ? detalleOrden.getProducto().getPorCientoDespachoSuperaPedido() : 0);
/* 667:    */       
/* 668:677 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_DESPACHO_SUPERA_PEDIDO_PORCIENTO_PERMITIDO", new String[] { detalleOrden.getProducto().getNombre(), porcientoPermitido.toString(), detalleOrden.getCantidadPedido().toString() });
/* 669:    */     }
/* 670:    */   }
/* 671:    */   
/* 672:    */   public MovimientoInventario corteProduccionRapida(OrdenDespachoCliente ordenDespachoCliente)
/* 673:    */     throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2
/* 674:    */   {
/* 675:685 */     Map<String, Producto> mapaProductos = new HashMap();
/* 676:686 */     for (DetalleOrdenDespachoCliente detalle : ordenDespachoCliente.getListaDetalleOrdenDespachoCliente()) {
/* 677:687 */       if ((!detalle.isEliminado()) && (detalle.getProducto().isIndicadorProduccionRapida()))
/* 678:    */       {
/* 679:688 */         Producto producto = detalle.getProducto();
/* 680:689 */         String key = producto.getId() + "~";
/* 681:690 */         if (detalle.getLote() != null) {
/* 682:691 */           key = key + detalle.getLote().getCodigo();
/* 683:    */         }
/* 684:693 */         if (mapaProductos.containsKey(key))
/* 685:    */         {
/* 686:694 */           producto = (Producto)mapaProductos.get(key);
/* 687:    */         }
/* 688:    */         else
/* 689:    */         {
/* 690:696 */           producto.setCantidadProducir(BigDecimal.ZERO);
/* 691:697 */           producto.setPesoMaterialPrincipal(null);
/* 692:    */         }
/* 693:699 */         producto.setCantidadProducir(producto.getCantidadProducir().add(detalle.getCantidad()));
/* 694:700 */         if ((detalle.getPesoMateriaPrima() != null) && (detalle.getPesoMateriaPrima().compareTo(BigDecimal.ZERO) > 0))
/* 695:    */         {
/* 696:701 */           if (producto.getPesoMaterialPrincipal() == null) {
/* 697:702 */             producto.setPesoMaterialPrincipal(BigDecimal.ZERO);
/* 698:    */           }
/* 699:704 */           producto.setPesoMaterialPrincipal(producto.getPesoMaterialPrincipal().add(detalle.getPesoMateriaPrima()));
/* 700:    */         }
/* 701:706 */         mapaProductos.put(key, producto);
/* 702:    */       }
/* 703:    */     }
/* 704:709 */     MovimientoInventario transformacionMateriales = null;
/* 705:710 */     if ((!mapaProductos.isEmpty()) && (ordenDespachoCliente != null)) {
/* 706:711 */       transformacionMateriales = this.servicioMovimientoInventario.guardaTransformacionRapidaProducto(mapaProductos, ordenDespachoCliente.getFecha(), ordenDespachoCliente, false, ordenDespachoCliente
/* 707:712 */         .getSucursal().getId(), null);
/* 708:    */     }
/* 709:714 */     if ((transformacionMateriales != null) && (transformacionMateriales.getDetalleMovimientosInventario().size() > 0)) {
/* 710:715 */       return transformacionMateriales;
/* 711:    */     }
/* 712:717 */     return null;
/* 713:    */   }
/* 714:    */   
/* 715:    */   public MovimientoInventario generarExplosionRapida(FacturaCliente notaCreditoCliente)
/* 716:    */     throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2
/* 717:    */   {
/* 718:723 */     Map<String, Producto> mapaProductos = new HashMap();
/* 719:724 */     OrdenDespachoCliente ordenDespachoCliente = null;
/* 720:725 */     for (DetalleFacturaCliente detalle : notaCreditoCliente.getListaDetalleFacturaCliente())
/* 721:    */     {
/* 722:    */       BigDecimal proporcionDevuelta;
/* 723:726 */       if ((notaCreditoCliente.getMotivoNotaCreditoCliente().isIndicadorReversaTransformacion()) && 
/* 724:727 */         (detalle.getProducto().isIndicadorProduccionRapida()))
/* 725:    */       {
/* 726:728 */         if ((detalle.getDetalleFacturaClientePadre() != null) && (detalle.getDetalleFacturaClientePadre().getDetalleDespachoCliente() != null)) {
/* 727:730 */           if (detalle.getDetalleFacturaClientePadre().getDetalleDespachoCliente().getDespachoCliente().getOrdenDespachoCliente() != null)
/* 728:    */           {
/* 729:731 */             HashMap<String, Object> campos = new HashMap();
/* 730:732 */             campos.put("indicadorGeneroTransformacion", Boolean.valueOf(true));
/* 731:733 */             this.facturaClienteDao.actualizarAtributoEntidad(notaCreditoCliente, campos);
/* 732:734 */             BigDecimal cantidadDevuelta = detalle.getCantidad();
/* 733:735 */             BigDecimal cantidadDespachada = detalle.getDetalleFacturaClientePadre().getDetalleDespachoCliente().getCantidad();
/* 734:736 */             BigDecimal proporcionDevuelta = cantidadDevuelta.divide(cantidadDespachada, 20, RoundingMode.HALF_UP);
/* 735:    */             
/* 736:738 */             BigDecimal pesoProporcionalMateriales = detalle.getDetalleFacturaClientePadre().getDetalleDespachoCliente().getPesoMateriaPrima().multiply(proporcionDevuelta).setScale(20, RoundingMode.HALF_UP);
/* 737:    */             
/* 738:740 */             Bodega bodegaDevolucion = detalle.getBodega();
/* 739:    */             
/* 740:742 */             Producto producto = detalle.getProducto();
/* 741:743 */             String key = producto.getId() + "~";
/* 742:744 */             if ((detalle.getInventarioProducto() != null) && (detalle.getInventarioProducto().getLote() != null)) {
/* 743:745 */               key = key + detalle.getInventarioProducto().getLote().getCodigo();
/* 744:    */             }
/* 745:747 */             if (mapaProductos.containsKey(key)) {
/* 746:748 */               producto = (Producto)mapaProductos.get(key);
/* 747:    */             }
/* 748:750 */             producto.setCantidadProducir(producto.getCantidadProducir().add(cantidadDevuelta));
/* 749:751 */             if (producto.getPesoMaterialPrincipal() == null) {
/* 750:752 */               producto.setPesoMaterialPrincipal(BigDecimal.ZERO);
/* 751:    */             }
/* 752:754 */             producto.setPesoMaterialPrincipal(producto.getPesoMaterialPrincipal().add(pesoProporcionalMateriales));
/* 753:755 */             producto.setBodegaDevolucion(bodegaDevolucion);
/* 754:756 */             mapaProductos.put(key, producto);
/* 755:    */             
/* 756:758 */             ordenDespachoCliente = detalle.getDetalleFacturaClientePadre().getDetalleDespachoCliente().getDespachoCliente().getOrdenDespachoCliente();
/* 757:    */             break label887;
/* 758:    */           }
/* 759:    */         }
/* 760:759 */         if ((detalle.getDetalleDespachoClienteNoFacturado() != null) && 
/* 761:760 */           (detalle.getDetalleDespachoClienteNoFacturado().getDespachoCliente().getOrdenDespachoCliente() != null))
/* 762:    */         {
/* 763:761 */           HashMap<String, Object> campos = new HashMap();
/* 764:762 */           campos.put("indicadorGeneroTransformacion", Boolean.valueOf(true));
/* 765:763 */           this.facturaClienteDao.actualizarAtributoEntidad(notaCreditoCliente, campos);
/* 766:764 */           BigDecimal cantidadDevuelta = detalle.getCantidad();
/* 767:765 */           BigDecimal cantidadDespachada = detalle.getDetalleDespachoClienteNoFacturado().getCantidad();
/* 768:766 */           proporcionDevuelta = cantidadDevuelta.divide(cantidadDespachada, 20, RoundingMode.HALF_UP);
/* 769:    */           
/* 770:768 */           BigDecimal pesoProporcionalMateriales = detalle.getDetalleDespachoClienteNoFacturado().getPesoMateriaPrima().multiply(proporcionDevuelta).setScale(20, RoundingMode.HALF_UP);
/* 771:    */           
/* 772:770 */           Bodega bodegaDevolucion = detalle.getBodega();
/* 773:    */           
/* 774:772 */           Producto producto = detalle.getProducto();
/* 775:773 */           String key = producto.getId() + "~";
/* 776:774 */           if ((detalle.getInventarioProducto() != null) && (detalle.getInventarioProducto().getLote() != null)) {
/* 777:775 */             key = key + detalle.getInventarioProducto().getLote().getCodigo();
/* 778:    */           }
/* 779:777 */           if (mapaProductos.containsKey(key)) {
/* 780:778 */             producto = (Producto)mapaProductos.get(key);
/* 781:    */           }
/* 782:780 */           producto.setCantidadProducir(producto.getCantidadProducir().add(cantidadDevuelta));
/* 783:781 */           if (producto.getPesoMaterialPrincipal() == null) {
/* 784:782 */             producto.setPesoMaterialPrincipal(BigDecimal.ZERO);
/* 785:    */           }
/* 786:784 */           producto.setPesoMaterialPrincipal(producto.getPesoMaterialPrincipal().add(pesoProporcionalMateriales));
/* 787:785 */           producto.setBodegaDevolucion(bodegaDevolucion);
/* 788:786 */           mapaProductos.put(key, producto);
/* 789:787 */           ordenDespachoCliente = detalle.getDetalleDespachoClienteNoFacturado().getDespachoCliente().getOrdenDespachoCliente();
/* 790:    */         }
/* 791:    */       }
/* 792:790 */       else if (detalle.getProducto().isIndicadorExplotaDevolucion())
/* 793:    */       {
/* 794:791 */         BigDecimal cantidadDevuelta = detalle.getCantidad();
/* 795:792 */         Bodega bodegaDevolucion = detalle.getBodega();
/* 796:    */         
/* 797:794 */         MovimientoInventario transformacion = this.servicioMovimientoInventario.obtenerUltimaTransformacion(detalle.getProducto(), detalle
/* 798:795 */           .getInventarioProducto().getLote());
/* 799:796 */         if (transformacion != null)
/* 800:    */         {
/* 801:797 */           transformacion = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(transformacion.getId()));
/* 802:798 */           this.movimientoInventarioDao.detach(transformacion);
/* 803:799 */           transformacion.setIdMovimientoInventario(0);
/* 804:800 */           transformacion.setFecha(notaCreditoCliente.getFecha());
/* 805:801 */           for (DetalleMovimientoInventario detalleTransformacion : transformacion.getDetalleMovimientosInventario())
/* 806:    */           {
/* 807:802 */             this.detalleMovimientoInventarioDao.detach(detalleTransformacion);
/* 808:803 */             detalleTransformacion.setIdDetalleMovimientoInventario(0);
/* 809:804 */             this.inventarioProductoDao.detach(detalleTransformacion.getInventarioProducto());
/* 810:805 */             detalleTransformacion.getInventarioProducto().setIdInventarioProducto(0);
/* 811:    */           }
/* 812:807 */           transformacion.setProductoTerminadoTransformacion(detalle.getProducto());
/* 813:808 */           this.servicioMovimientoInventario.guardaTransformacionProducto(transformacion, true, cantidadDevuelta, bodegaDevolucion, notaCreditoCliente);
/* 814:    */         }
/* 815:    */       }
/* 816:    */     }
/* 817:    */     label887:
/* 818:813 */     MovimientoInventario movimientoMateriales = null;
/* 819:814 */     if ((!mapaProductos.isEmpty()) && (ordenDespachoCliente != null)) {
/* 820:815 */       movimientoMateriales = this.servicioMovimientoInventario.guardaTransformacionRapidaProducto(mapaProductos, notaCreditoCliente.getFecha(), ordenDespachoCliente, true, notaCreditoCliente
/* 821:816 */         .getSucursal().getId(), notaCreditoCliente);
/* 822:    */     }
/* 823:819 */     return movimientoMateriales;
/* 824:    */   }
/* 825:    */   
/* 826:    */   public void cerrar(OrdenDespachoCliente ordenDespachoCliente)
/* 827:    */     throws ExcepcionAS2Inventario
/* 828:    */   {
/* 829:    */     try
/* 830:    */     {
/* 831:825 */       Map<Integer, PedidoCliente> mapaPedidoCliente = new HashMap();
/* 832:826 */       for (DetalleOrdenDespachoCliente detalleOrden : ordenDespachoCliente.getListaDetalleOrdenDespachoCliente()) {
/* 833:827 */         for (DetalleOrdenDespachoClientePedidoCliente detalleOrdenPedido : detalleOrden.getListaDetallePedidoCliente()) {
/* 834:828 */           mapaPedidoCliente.put(Integer.valueOf(detalleOrdenPedido.getDetallePedidoCliente().getPedidoCliente().getId()), detalleOrdenPedido
/* 835:829 */             .getDetallePedidoCliente().getPedidoCliente());
/* 836:    */         }
/* 837:    */       }
/* 838:832 */       for (PedidoCliente pedidoCliente : mapaPedidoCliente.values()) {
/* 839:833 */         this.servicioPedidoCliente.cerrarPedidoCliente(pedidoCliente);
/* 840:    */       }
/* 841:835 */       ordenDespachoCliente.setEstado(Estado.CERRADO);
/* 842:836 */       this.ordenDespachoClienteDao.guardar(ordenDespachoCliente);
/* 843:    */     }
/* 844:    */     catch (ExcepcionAS2Inventario e)
/* 845:    */     {
/* 846:838 */       this.context.setRollbackOnly();
/* 847:839 */       e.printStackTrace();
/* 848:840 */       throw e;
/* 849:    */     }
/* 850:    */   }
/* 851:    */   
/* 852:    */   public List<Object[]> getReporteOrdenDespachoGavetas(OrdenDespachoCliente ordenDespachoCliente, boolean indicadorAcumulado)
/* 853:    */   {
/* 854:846 */     return this.ordenDespachoClienteDao.getReporteOrdenDespachoGavetas(ordenDespachoCliente, indicadorAcumulado);
/* 855:    */   }
/* 856:    */   
/* 857:    */   public void validarProducotListaMaterial(OrdenDespachoCliente ordenDespachoCliente)
/* 858:    */     throws AS2Exception
/* 859:    */   {
/* 860:851 */     String productosSinIndicadorListaMaterial = "";
/* 861:852 */     boolean indicadorSinListaMaterial = false;
/* 862:853 */     for (DetalleOrdenDespachoCliente detalle : ordenDespachoCliente.getListaDetalleOrdenDespachoCliente())
/* 863:    */     {
/* 864:854 */       Producto producto = detalle.getProducto();
/* 865:855 */       if ((producto.isIndicadorProduccionRapida()) && (!producto.isIndicadorListaMaterial()))
/* 866:    */       {
/* 867:856 */         productosSinIndicadorListaMaterial = productosSinIndicadorListaMaterial + producto.getCodigo();
/* 868:857 */         indicadorSinListaMaterial = true;
/* 869:    */       }
/* 870:    */     }
/* 871:860 */     if (indicadorSinListaMaterial) {
/* 872:861 */       throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioOrdenDespachoClienteImpl.MENSAJE_ERROR_PRODUCTO_SIN_MATERIAL", new String[] { productosSinIndicadorListaMaterial });
/* 873:    */     }
/* 874:    */   }
/* 875:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioOrdenDespachoClienteImpl
 * JD-Core Version:    0.7.0.1
 */