/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.DetallePreDevolucionCliente;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PreDevolucionCliente;
/*  12:    */ import com.asinfo.as2.entities.Producto;
/*  13:    */ import com.asinfo.as2.entities.RecepcionDevolucionTransportista;
/*  14:    */ import com.asinfo.as2.entities.Subempresa;
/*  15:    */ import com.asinfo.as2.entities.Transportista;
/*  16:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  17:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  18:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  19:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  20:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  21:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  23:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioPreDevolucionCliente;
/*  24:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRecepcionDevolucionTransportista;
/*  25:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  26:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  27:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  28:    */ import com.asinfo.as2.util.AppUtil;
/*  29:    */ import com.asinfo.as2.utils.JsfUtil;
/*  30:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  31:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.HashMap;
/*  36:    */ import java.util.Iterator;
/*  37:    */ import java.util.List;
/*  38:    */ import java.util.Map;
/*  39:    */ import javax.annotation.PostConstruct;
/*  40:    */ import javax.ejb.EJB;
/*  41:    */ import javax.faces.bean.ManagedBean;
/*  42:    */ import javax.faces.bean.ViewScoped;
/*  43:    */ import javax.faces.model.SelectItem;
/*  44:    */ import org.apache.log4j.Logger;
/*  45:    */ import org.primefaces.component.datatable.DataTable;
/*  46:    */ import org.primefaces.context.RequestContext;
/*  47:    */ import org.primefaces.model.LazyDataModel;
/*  48:    */ import org.primefaces.model.SortOrder;
/*  49:    */ 
/*  50:    */ @ManagedBean
/*  51:    */ @ViewScoped
/*  52:    */ public class ProcesarDevolucionClienteBean
/*  53:    */   extends PageControllerAS2
/*  54:    */ {
/*  55:    */   private static final long serialVersionUID = 1L;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioPreDevolucionCliente servicioPreDevolucionCliente;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioRecepcionDevolucionTransportista servicioRecepcionDevolucionTransportista;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioGenerico<DetallePreDevolucionCliente> servicioDetallePreDevolucionCliente;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioUsuario servicioUsuario;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioTransportista servicioTransportista;
/*  68:    */   @EJB
/*  69:    */   protected transient ServicioEmpresa servicioEmpresa;
/*  70:    */   @EJB
/*  71:    */   protected transient ServicioGenerico<RecepcionDevolucionTransportista> servicioDetallePreDevolucionCliente2;
/*  72:    */   private RecepcionDevolucionTransportista recepcionDevolucionTransportista;
/*  73:    */   private LazyDataModel<RecepcionDevolucionTransportista> listaRecepcionDevolucionTransportista;
/*  74:    */   
/*  75:    */   static enum TIPO_PRODUCTO
/*  76:    */   {
/*  77: 72 */     MALO("false"),  BUENO("true");
/*  78:    */     
/*  79:    */     private String nombre;
/*  80:    */     
/*  81:    */     private TIPO_PRODUCTO(String nombre)
/*  82:    */     {
/*  83: 82 */       this.nombre = nombre;
/*  84:    */     }
/*  85:    */     
/*  86:    */     public String getNombre()
/*  87:    */     {
/*  88: 91 */       return this.nombre;
/*  89:    */     }
/*  90:    */     
/*  91:    */     public void setNombre(String nombre)
/*  92:    */     {
/*  93:100 */       this.nombre = nombre;
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:127 */   private List<DetallePreDevolucionCliente> listaDetallePreDevolucionCliente = new ArrayList();
/*  98:    */   private DataTable dtPreDevolucionCliente;
/*  99:    */   private DataTable dtRecepcionDevolucionTransportista;
/* 100:    */   private DataTable dtDetallePreDevolucionCliente;
/* 101:    */   private List<Bodega> listaBodega;
/* 102:    */   private Subempresa subEmpresa;
/* 103:    */   private PreDevolucionCliente preDevolucionCliente;
/* 104:    */   private DetallePreDevolucionCliente detallePreDevolucionCliente;
/* 105:    */   private List<Subempresa> listaSubempresa;
/* 106:    */   
/* 107:    */   @PostConstruct
/* 108:    */   public void init()
/* 109:    */   {
/* 110:142 */     this.listaRecepcionDevolucionTransportista = new LazyDataModel()
/* 111:    */     {
/* 112:    */       private static final long serialVersionUID = 1L;
/* 113:    */       
/* 114:    */       public List<RecepcionDevolucionTransportista> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 115:    */       {
/* 116:149 */         List<RecepcionDevolucionTransportista> lista = new ArrayList();
/* 117:150 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 118:    */         try
/* 119:    */         {
/* 120:152 */           List<String> lcampos = new ArrayList();
/* 121:153 */           lcampos.add("transportista");
/* 122:154 */           lista = ProcesarDevolucionClienteBean.this.servicioRecepcionDevolucionTransportista.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 123:    */         }
/* 124:    */         catch (Exception e)
/* 125:    */         {
/* 126:157 */           e.printStackTrace();
/* 127:    */         }
/* 128:159 */         ProcesarDevolucionClienteBean.this.listaRecepcionDevolucionTransportista.setRowCount(ProcesarDevolucionClienteBean.this.servicioRecepcionDevolucionTransportista.contarPorCriterio(filters));
/* 129:160 */         return lista;
/* 130:    */       }
/* 131:    */     };
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String editar()
/* 135:    */   {
/* 136:172 */     if ((getRecepcionDevolucionTransportista() != null) && (getRecepcionDevolucionTransportista().getIdRecepcionDevolucionTransportista() != 0))
/* 137:    */     {
/* 138:173 */       if (getRecepcionDevolucionTransportista().getEstado() == Estado.ELABORADO)
/* 139:    */       {
/* 140:174 */         this.recepcionDevolucionTransportista = this.servicioRecepcionDevolucionTransportista.cargarDetalle(this.recepcionDevolucionTransportista.getId());
/* 141:175 */         this.listaDetallePreDevolucionCliente = new ArrayList();
/* 142:176 */         for (PreDevolucionCliente pdc : this.recepcionDevolucionTransportista.getListaPreDevolucionCliente()) {
/* 143:177 */           for (DetallePreDevolucionCliente dpdc : pdc.getListaDetallePreDevolucionCliente()) {
/* 144:178 */             if (dpdc.isIndicadorProcesar()) {
/* 145:179 */               this.listaDetallePreDevolucionCliente.add(dpdc);
/* 146:    */             }
/* 147:    */           }
/* 148:    */         }
/* 149:183 */         setEditado(true);
/* 150:    */       }
/* 151:    */       else
/* 152:    */       {
/* 153:185 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 154:    */       }
/* 155:    */     }
/* 156:    */     else {
/* 157:188 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 158:    */     }
/* 159:190 */     return null;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String guardar()
/* 163:    */   {
/* 164:    */     try
/* 165:    */     {
/* 166:201 */       this.servicioRecepcionDevolucionTransportista.guardar(this.recepcionDevolucionTransportista);
/* 167:    */       
/* 168:203 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 169:204 */       limpiar();
/* 170:205 */       setEditado(false);
/* 171:    */     }
/* 172:    */     catch (AS2Exception e)
/* 173:    */     {
/* 174:207 */       JsfUtil.addErrorMessage(e, "");
/* 175:208 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 176:209 */       e.printStackTrace();
/* 177:    */     }
/* 178:    */     catch (Exception e)
/* 179:    */     {
/* 180:211 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 181:212 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 182:    */     }
/* 183:215 */     return null;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String eliminar()
/* 187:    */   {
/* 188:220 */     return null;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String limpiar()
/* 192:    */   {
/* 193:225 */     this.recepcionDevolucionTransportista = new RecepcionDevolucionTransportista();
/* 194:226 */     this.recepcionDevolucionTransportista.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 195:227 */     this.recepcionDevolucionTransportista.setSucursal(AppUtil.getSucursal());
/* 196:228 */     this.recepcionDevolucionTransportista.setFecha(new Date());
/* 197:229 */     this.recepcionDevolucionTransportista.setEstado(Estado.ELABORADO);
/* 198:230 */     this.listaDetallePreDevolucionCliente = new ArrayList();
/* 199:231 */     String identificacion = AppUtil.getOrganizacion().getIdentificacion();
/* 200:    */     try
/* 201:    */     {
/* 202:234 */       Empresa empresa = this.servicioEmpresa.buscarEmpresaPorIdentificacion(AppUtil.getOrganizacion().getId(), identificacion);
/* 203:235 */       this.recepcionDevolucionTransportista.setEmpresa(empresa);
/* 204:    */     }
/* 205:    */     catch (ExcepcionAS2 e)
/* 206:    */     {
/* 207:237 */       addErrorMessage(getLanguageController().getMensaje("msg_info_cliente_organizacion"));
/* 208:238 */       e.printStackTrace();
/* 209:    */     }
/* 210:241 */     return null;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public String cargarDatos()
/* 214:    */   {
/* 215:246 */     return null;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public LazyDataModel<RecepcionDevolucionTransportista> getListaRecepcionDevolucionTransportista()
/* 219:    */   {
/* 220:250 */     return this.listaRecepcionDevolucionTransportista;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setListaRecepcionDevolucionTransportista(LazyDataModel<RecepcionDevolucionTransportista> listaRecepcionDevolucionTransportista)
/* 224:    */   {
/* 225:254 */     this.listaRecepcionDevolucionTransportista = listaRecepcionDevolucionTransportista;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public DataTable getDtPreDevolucionCliente()
/* 229:    */   {
/* 230:261 */     return this.dtPreDevolucionCliente;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setDtPreDevolucionCliente(DataTable dtPreDevolucionCliente)
/* 234:    */   {
/* 235:269 */     this.dtPreDevolucionCliente = dtPreDevolucionCliente;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public DataTable getDtDetallePreDevolucionCliente()
/* 239:    */   {
/* 240:276 */     return this.dtDetallePreDevolucionCliente;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setDtDetallePreDevolucionCliente(DataTable dtDetallePreDevolucionCliente)
/* 244:    */   {
/* 245:284 */     this.dtDetallePreDevolucionCliente = dtDetallePreDevolucionCliente;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void procesarPreDevoluciones()
/* 249:    */   {
/* 250:    */     try
/* 251:    */     {
/* 252:289 */       this.recepcionDevolucionTransportista = ((RecepcionDevolucionTransportista)this.dtRecepcionDevolucionTransportista.getRowData());
/* 253:290 */       this.recepcionDevolucionTransportista = this.servicioRecepcionDevolucionTransportista.cargarDetalle(this.recepcionDevolucionTransportista.getId());
/* 254:291 */       this.servicioNotaCreditoCliente
/* 255:292 */         .procesarPreDevoluciones(this.recepcionDevolucionTransportista, isIndicadorAutoimpresor(), AppUtil.getPuntoDeVenta());
/* 256:293 */       this.listaDetallePreDevolucionCliente = new ArrayList();
/* 257:294 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 258:    */     }
/* 259:    */     catch (ExcepcionAS2Financiero e)
/* 260:    */     {
/* 261:296 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 262:297 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 263:298 */       e.printStackTrace();
/* 264:    */     }
/* 265:    */     catch (ExcepcionAS2Ventas e)
/* 266:    */     {
/* 267:300 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 268:301 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 269:302 */       e.printStackTrace();
/* 270:    */     }
/* 271:    */     catch (AS2Exception e)
/* 272:    */     {
/* 273:304 */       JsfUtil.addErrorMessage(e, "");
/* 274:305 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 275:306 */       e.printStackTrace();
/* 276:    */     }
/* 277:    */     catch (ExcepcionAS2 e)
/* 278:    */     {
/* 279:308 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 280:309 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 281:310 */       e.printStackTrace();
/* 282:    */     }
/* 283:    */     catch (Exception e)
/* 284:    */     {
/* 285:312 */       addErrorMessage(e.getMessage());
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public List<Bodega> getListaBodega()
/* 290:    */   {
/* 291:320 */     if (null == this.listaBodega) {
/* 292:321 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 293:    */     }
/* 294:324 */     return this.listaBodega;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 298:    */   {
/* 299:332 */     this.listaBodega = listaBodega;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public List<Transportista> autocompletarTransportista(String consulta)
/* 303:    */   {
/* 304:336 */     Map<String, String> filters = new HashMap();
/* 305:337 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 306:338 */     filters.put("nombre", consulta);
/* 307:339 */     filters.put("usuario", OperacionEnum.IS_NOT_NULL.name());
/* 308:340 */     return this.servicioTransportista.obtenerListaCombo("nombre", true, filters);
/* 309:    */   }
/* 310:    */   
/* 311:    */   public List<SelectItem> getListaTipoProducto()
/* 312:    */   {
/* 313:344 */     List<SelectItem> lista = new ArrayList();
/* 314:345 */     for (TIPO_PRODUCTO tp : TIPO_PRODUCTO.values()) {
/* 315:346 */       lista.add(new SelectItem(tp.getNombre(), tp.name()));
/* 316:    */     }
/* 317:348 */     return lista;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String buscarDetalles()
/* 321:    */   {
/* 322:352 */     this.listaDetallePreDevolucionCliente = new ArrayList();
/* 323:353 */     Map<String, String> filtros = new HashMap();
/* 324:354 */     filtros.put("preDevolucionCliente.estado", Estado.ELABORADO.name());
/* 325:355 */     List<String> lcampos = new ArrayList();
/* 326:356 */     lcampos.add("producto.categoriaImpuesto");
/* 327:357 */     lcampos.add("unidad");
/* 328:358 */     lcampos.add("bodega");
/* 329:359 */     lcampos.add("lote");
/* 330:360 */     for (PreDevolucionCliente pdc : this.recepcionDevolucionTransportista.getListaPreDevolucionCliente()) {
/* 331:361 */       if ((pdc.isIndicadorProcesar()) && (pdc.getEstado().ordinal() < 5))
/* 332:    */       {
/* 333:362 */         filtros.put("preDevolucionCliente.idPreDevolucionCliente", "" + pdc.getId());
/* 334:363 */         List<DetallePreDevolucionCliente> list = this.servicioDetallePreDevolucionCliente.obtenerListaPorPagina(DetallePreDevolucionCliente.class, 0, 1000, "idDetallePreDevolucionCliente", true, filtros, lcampos);
/* 335:365 */         for (DetallePreDevolucionCliente detallePreDevolucionCliente : list)
/* 336:    */         {
/* 337:366 */           if ((!this.recepcionDevolucionTransportista.isProductoBueno()) && (this.recepcionDevolucionTransportista.getId() != 0)) {
/* 338:367 */             detallePreDevolucionCliente.setCantidadRecibida(BigDecimal.ZERO);
/* 339:    */           }
/* 340:368 */           this.listaDetallePreDevolucionCliente.add(detallePreDevolucionCliente);
/* 341:    */         }
/* 342:371 */         pdc.setListaDetallePreDevolucionCliente(list);
/* 343:    */       }
/* 344:    */     }
/* 345:375 */     return null;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public String buscar()
/* 349:    */   {
/* 350:379 */     if (this.recepcionDevolucionTransportista.isProductoBueno())
/* 351:    */     {
/* 352:380 */       buscarPreDevoluciones();
/* 353:    */     }
/* 354:382 */     else if (tieneCantidadesDigitadas())
/* 355:    */     {
/* 356:383 */       RequestContext context = RequestContext.getCurrentInstance();
/* 357:384 */       context.execute("dialogConfirmar.show();");
/* 358:    */     }
/* 359:    */     else
/* 360:    */     {
/* 361:386 */       buscarPreDevoluciones();
/* 362:    */     }
/* 363:389 */     return null;
/* 364:    */   }
/* 365:    */   
/* 366:    */   private boolean tieneCantidadesDigitadas()
/* 367:    */   {
/* 368:393 */     for (DetallePreDevolucionCliente d : getListaDetallePreDevolucionCliente()) {
/* 369:394 */       if (d.getCantidadRecibida().compareTo(BigDecimal.ZERO) != 0) {
/* 370:395 */         return true;
/* 371:    */       }
/* 372:    */     }
/* 373:398 */     return false;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public String buscarPreDevoluciones()
/* 377:    */   {
/* 378:402 */     boolean reemplazaLista = false;
/* 379:403 */     Map<Integer, PreDevolucionCliente> hashPreDevoluciones = new HashMap();
/* 380:404 */     for (Iterator localIterator = this.recepcionDevolucionTransportista.getListaPreDevolucionCliente().iterator(); localIterator.hasNext();)
/* 381:    */     {
/* 382:404 */       pdc = (PreDevolucionCliente)localIterator.next();
/* 383:    */       
/* 384:406 */       reemplazaLista = pdc.getMotivoNotaCreditoCliente().isIndicadorReversaTransformacion() != this.recepcionDevolucionTransportista.isProductoBueno();
/* 385:407 */       hashPreDevoluciones.put(Integer.valueOf(pdc.getId()), pdc);
/* 386:    */     }
/* 387:    */     PreDevolucionCliente pdc;
/* 388:410 */     Object listPreDevolucionCliente = this.servicioPreDevolucionCliente.buscarPreDevolucionesPorTransportista(this.recepcionDevolucionTransportista
/* 389:411 */       .getIdOrganizacion(), this.recepcionDevolucionTransportista.isProductoBueno(), this.recepcionDevolucionTransportista
/* 390:412 */       .getTransportista().getUsuario().getId(), this.recepcionDevolucionTransportista.getFecha());
/* 391:413 */     this.recepcionDevolucionTransportista.setListaPreDevolucionCliente(new ArrayList());
/* 392:414 */     if (!reemplazaLista) {
/* 393:415 */       this.recepcionDevolucionTransportista.getListaPreDevolucionCliente().addAll(hashPreDevoluciones.values());
/* 394:    */     }
/* 395:417 */     if (reemplazaLista) {
/* 396:418 */       this.recepcionDevolucionTransportista.setListaPreDevolucionCliente((List)listPreDevolucionCliente);
/* 397:    */     } else {
/* 398:420 */       for (PreDevolucionCliente pdc : (List)listPreDevolucionCliente) {
/* 399:421 */         if ((pdc.getRecepcionDevolucionTransportista() == null) && 
/* 400:422 */           (!hashPreDevoluciones.containsKey(Integer.valueOf(pdc.getId()))))
/* 401:    */         {
/* 402:423 */           pdc = this.servicioPreDevolucionCliente.cargarDetalle(pdc.getId());
/* 403:424 */           if (!this.recepcionDevolucionTransportista.isProductoBueno()) {
/* 404:425 */             actualizarDetalle(pdc.getListaDetallePreDevolucionCliente());
/* 405:    */           }
/* 406:426 */           pdc.setIndicadorProcesar(true);
/* 407:427 */           pdc.setRecepcionDevolucionTransportista(this.recepcionDevolucionTransportista);
/* 408:428 */           this.recepcionDevolucionTransportista.getListaPreDevolucionCliente().add(pdc);
/* 409:    */         }
/* 410:    */       }
/* 411:    */     }
/* 412:433 */     agregarDetalles();
/* 413:434 */     return null;
/* 414:    */   }
/* 415:    */   
/* 416:    */   private void agregarDetalles()
/* 417:    */   {
/* 418:438 */     this.listaDetallePreDevolucionCliente = new ArrayList();
/* 419:439 */     Map<String, DetallePreDevolucionCliente> mapDetalles = new HashMap();
/* 420:440 */     for (PreDevolucionCliente pdc : this.recepcionDevolucionTransportista.getListaPreDevolucionCliente()) {
/* 421:441 */       for (DetallePreDevolucionCliente dpdc : pdc.getListaDetallePreDevolucionCliente())
/* 422:    */       {
/* 423:442 */         dpdc.setIndicadorProcesar(false);
/* 424:443 */         DetallePreDevolucionCliente aux = (DetallePreDevolucionCliente)mapDetalles.get(dpdc.getProducto().getId() + "~" + dpdc.getBodega().getId());
/* 425:444 */         if (aux != null)
/* 426:    */         {
/* 427:445 */           aux.setCantidadProcesar(aux.getCantidadProcesar().add(dpdc.getCantidad()));
/* 428:446 */           if (this.recepcionDevolucionTransportista.isProductoBueno()) {
/* 429:447 */             aux.setCantidadRecibida(aux.getCantidadProcesar());
/* 430:    */           }
/* 431:    */         }
/* 432:    */         else
/* 433:    */         {
/* 434:449 */           dpdc.setIndicadorProcesar(true);
/* 435:450 */           dpdc.setCantidadProcesar(dpdc.getCantidad());
/* 436:451 */           if (this.recepcionDevolucionTransportista.isProductoBueno()) {
/* 437:452 */             dpdc.setCantidadRecibida(dpdc.getCantidad());
/* 438:    */           } else {
/* 439:454 */             dpdc.setCantidadRecibida(BigDecimal.ZERO);
/* 440:    */           }
/* 441:455 */           mapDetalles.put(dpdc.getProducto().getId() + "~" + dpdc.getBodega().getId(), dpdc);
/* 442:    */         }
/* 443:    */       }
/* 444:    */     }
/* 445:459 */     this.listaDetallePreDevolucionCliente.addAll(mapDetalles.values());
/* 446:    */   }
/* 447:    */   
/* 448:    */   public List<DetallePreDevolucionCliente> getListaDetallePreDevolucionCliente()
/* 449:    */   {
/* 450:463 */     return this.listaDetallePreDevolucionCliente;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void setListaDetallePreDevolucionCliente(List<DetallePreDevolucionCliente> listaDetallePreDevolucionCliente)
/* 454:    */   {
/* 455:467 */     this.listaDetallePreDevolucionCliente = listaDetallePreDevolucionCliente;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public Subempresa getSubEmpresa()
/* 459:    */   {
/* 460:471 */     return this.subEmpresa;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public void setSubEmpresa(Subempresa subEmpresa)
/* 464:    */   {
/* 465:475 */     this.subEmpresa = subEmpresa;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public RecepcionDevolucionTransportista getRecepcionDevolucionTransportista()
/* 469:    */   {
/* 470:479 */     return this.recepcionDevolucionTransportista;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void setRecepcionDevolucionTransportista(RecepcionDevolucionTransportista recepcionDevolucionTransportista)
/* 474:    */   {
/* 475:483 */     this.recepcionDevolucionTransportista = recepcionDevolucionTransportista;
/* 476:    */   }
/* 477:    */   
/* 478:    */   public DataTable getDtRecepcionDevolucionTransportista()
/* 479:    */   {
/* 480:487 */     return this.dtRecepcionDevolucionTransportista;
/* 481:    */   }
/* 482:    */   
/* 483:    */   public void setDtRecepcionDevolucionTransportista(DataTable dtRecepcionDevolucionTransportista)
/* 484:    */   {
/* 485:491 */     this.dtRecepcionDevolucionTransportista = dtRecepcionDevolucionTransportista;
/* 486:    */   }
/* 487:    */   
/* 488:    */   public PreDevolucionCliente getPreDevolucionCliente()
/* 489:    */   {
/* 490:495 */     return this.preDevolucionCliente;
/* 491:    */   }
/* 492:    */   
/* 493:    */   public void setPreDevolucionCliente(PreDevolucionCliente preDevolucionCliente)
/* 494:    */   {
/* 495:499 */     this.preDevolucionCliente = preDevolucionCliente;
/* 496:    */   }
/* 497:    */   
/* 498:    */   public DetallePreDevolucionCliente getDetallePreDevolucionCliente()
/* 499:    */   {
/* 500:503 */     return this.detallePreDevolucionCliente;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public void setDetallePreDevolucionCliente(DetallePreDevolucionCliente detallePreDevolucionCliente)
/* 504:    */   {
/* 505:507 */     this.detallePreDevolucionCliente = detallePreDevolucionCliente;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public void cargarSubempresas()
/* 509:    */   {
/* 510:511 */     this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(this.recepcionDevolucionTransportista.getEmpresa().getId());
/* 511:    */   }
/* 512:    */   
/* 513:    */   public List<Subempresa> getListaSubempresa()
/* 514:    */   {
/* 515:515 */     if (this.listaSubempresa == null) {
/* 516:516 */       cargarSubempresas();
/* 517:    */     }
/* 518:517 */     return this.listaSubempresa;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 522:    */   {
/* 523:521 */     this.listaSubempresa = listaSubempresa;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public String actualizarTransportista()
/* 527:    */   {
/* 528:525 */     if (this.recepcionDevolucionTransportista.getTransportista() != null) {
/* 529:526 */       this.recepcionDevolucionTransportista.setTransportista(this.servicioTransportista.cargarDetalle(this.recepcionDevolucionTransportista.getTransportista()
/* 530:527 */         .getId()));
/* 531:    */     }
/* 532:529 */     limpiarListaPreDevoluciones();
/* 533:530 */     return null;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public String copiarValores()
/* 537:    */   {
/* 538:534 */     for (DetallePreDevolucionCliente dpdc : getListaDetallePreDevolucionCliente()) {
/* 539:535 */       dpdc.setCantidadRecibida(dpdc.getCantidadProcesar());
/* 540:    */     }
/* 541:537 */     return null;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public String actualizarDetalle(List<DetallePreDevolucionCliente> list)
/* 545:    */   {
/* 546:541 */     for (DetallePreDevolucionCliente dp : list) {
/* 547:542 */       dp.setCantidadRecibida(BigDecimal.ZERO);
/* 548:    */     }
/* 549:544 */     return null;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public String limpiarListaPreDevoluciones()
/* 553:    */   {
/* 554:548 */     this.recepcionDevolucionTransportista.setListaPreDevolucionCliente(new ArrayList());
/* 555:549 */     this.listaDetallePreDevolucionCliente = new ArrayList();
/* 556:550 */     return null;
/* 557:    */   }
/* 558:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.ProcesarDevolucionClienteBean
 * JD-Core Version:    0.7.0.1
 */