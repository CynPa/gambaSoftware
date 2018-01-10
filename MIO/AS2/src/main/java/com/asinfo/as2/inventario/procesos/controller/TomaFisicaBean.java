/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.DetalleTomaFisica;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Lote;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Producto;
/*  13:    */ import com.asinfo.as2.entities.SaldoProducto;
/*  14:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*  15:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.entities.TomaFisica;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  21:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  22:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  23:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  26:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  27:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  28:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  29:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioTomaFisica;
/*  30:    */ import com.asinfo.as2.util.AppUtil;
/*  31:    */ import com.asinfo.as2.util.RutaArchivo;
/*  32:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  33:    */ import java.io.BufferedInputStream;
/*  34:    */ import java.io.InputStream;
/*  35:    */ import java.math.BigDecimal;
/*  36:    */ import java.util.ArrayList;
/*  37:    */ import java.util.Collection;
/*  38:    */ import java.util.Date;
/*  39:    */ import java.util.HashMap;
/*  40:    */ import java.util.Iterator;
/*  41:    */ import java.util.List;
/*  42:    */ import java.util.Map;
/*  43:    */ import java.util.Vector;
/*  44:    */ import javax.annotation.PostConstruct;
/*  45:    */ import javax.ejb.EJB;
/*  46:    */ import javax.faces.bean.ManagedBean;
/*  47:    */ import javax.faces.bean.ViewScoped;
/*  48:    */ import javax.faces.component.html.HtmlInputText;
/*  49:    */ import javax.faces.context.FacesContext;
/*  50:    */ import javax.faces.context.PartialViewContext;
/*  51:    */ import javax.faces.event.ActionEvent;
/*  52:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  53:    */ import org.apache.log4j.Logger;
/*  54:    */ import org.primefaces.component.datatable.DataTable;
/*  55:    */ import org.primefaces.context.RequestContext;
/*  56:    */ import org.primefaces.event.FileUploadEvent;
/*  57:    */ import org.primefaces.model.LazyDataModel;
/*  58:    */ import org.primefaces.model.SortOrder;
/*  59:    */ import org.primefaces.model.StreamedContent;
/*  60:    */ import org.primefaces.model.UploadedFile;
/*  61:    */ 
/*  62:    */ @ManagedBean
/*  63:    */ @ViewScoped
/*  64:    */ public class TomaFisicaBean
/*  65:    */   extends MovimientoInventarioBaseBean
/*  66:    */ {
/*  67:    */   private static final long serialVersionUID = 7378585540146745650L;
/*  68:    */   @EJB
/*  69:    */   private ServicioTomaFisica servicioTomaFisica;
/*  70:    */   @EJB
/*  71:    */   private ServicioDocumento servicioDocumento;
/*  72:    */   @EJB
/*  73:    */   private ServicioProducto servicioProducto;
/*  74:    */   @EJB
/*  75:    */   private ServicioBodega servicioBodega;
/*  76:    */   @EJB
/*  77:    */   private ServicioLote servicioLote;
/*  78:    */   private TomaFisica tomaFisica;
/*  79:    */   private LazyDataModel<TomaFisica> listaTomaFisica;
/*  80:    */   private List<Documento> listaDocumento;
/*  81:    */   private List<Documento> listaDocumentoAjuste;
/*  82:    */   private List<Documento> listaDocumentoAjusteIngreso;
/*  83:    */   private List<Documento> listaDocumentoAjusteEgreso;
/*  84:    */   private String codigoCabecera;
/*  85:    */   private DataTable dtTomaFisica;
/*  86:    */   private DataTable dtDetalleTomaFisica;
/*  87:    */   private BigDecimal totalMovimientoIngreso;
/*  88:    */   private BigDecimal totalMovimientoEgreso;
/*  89:    */   private Producto[] productosSeleccionados;
/*  90:107 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  91:109 */   private HashMap<String, DetalleTomaFisica> hmproductos = new HashMap();
/*  92:    */   private StreamedContent file;
/*  93:    */   private static final String TIPO_CONTENIDO = "application/xls";
/*  94:    */   @EJB
/*  95:    */   private ServicioMigracion servicioMigracion;
/*  96:    */   private List<String> listaCodigosProducto;
/*  97:119 */   private boolean valoresTomaDiferenteCero = false;
/*  98:    */   
/*  99:    */   public StreamedContent getFile()
/* 100:    */   {
/* 101:129 */     this.file = generarPlantilla();
/* 102:130 */     return this.file;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public StreamedContent generarPlantilla()
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109:137 */       Vector v = new Vector();
/* 110:138 */       HashMap<String, String> filtersRubro = new HashMap();
/* 111:139 */       List<DetalleTomaFisica> listaDetalleTomaFisica = new ArrayList();
/* 112:140 */       if (getDetalleTomaFisica().size() > 0)
/* 113:    */       {
/* 114:141 */         String rubrosActuales = "";
/* 115:142 */         v.addElement("Codigo,Codigo Barras,Producto,Categoria Producto,Unidad,Lote,Numero Lote,Cantidad Sistema, Cantidad Toma Fisica");
/* 116:143 */         List<Object[]> listaTomaFisica = new ArrayList();
/* 117:144 */         for (DetalleTomaFisica re : getDetalleTomaFisica())
/* 118:    */         {
/* 119:145 */           Object[] datos = new Object[9];
/* 120:146 */           datos[0] = re.getProducto().getCodigo();
/* 121:147 */           datos[1] = ((re.getProducto().getCodigoBarras() != null) && (!re.getProducto().getCodigoBarras().isEmpty()) ? re
/* 122:148 */             .getProducto().getCodigoBarras() : "n/a");
/* 123:149 */           datos[2] = re.getProducto().getNombre().replace(",", "");
/* 124:150 */           datos[3] = re.getProducto().getSubcategoriaProducto().getCategoriaProducto().getNombre().replace(",", "");
/* 125:151 */           datos[4] = re.getProducto().getUnidad();
/* 126:152 */           datos[5] = Boolean.valueOf(re.getProducto().isIndicadorLote());
/* 127:153 */           datos[6] = (re.getLote() != null ? re.getLote().getCodigo() : "n/a");
/* 128:154 */           datos[7] = re.getCantidadSistema();
/* 129:155 */           datos[8] = re.getCantidadTomaFisica();
/* 130:156 */           listaTomaFisica.add(datos);
/* 131:    */         }
/* 132:159 */         for (Object[] datos : listaTomaFisica)
/* 133:    */         {
/* 134:160 */           StringBuilder fila = new StringBuilder();
/* 135:161 */           for (int i = 0; i < datos.length; i++)
/* 136:    */           {
/* 137:162 */             fila.append(datos[i] == null ? "-" : datos[i].toString());
/* 138:163 */             if (i <= datos.length) {
/* 139:164 */               fila.append(",");
/* 140:    */             }
/* 141:    */           }
/* 142:167 */           v.addElement(fila.toString());
/* 143:    */         }
/* 144:169 */         String rutaArchivo = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "plantillas", "listaTomasFisicas");
/* 145:170 */         String nombreArchivo = "TomaFisica.xls";
/* 146:    */         
/* 147:172 */         FuncionesUtiles.crearExcel(v, "ListaTomaFisica", rutaArchivo, nombreArchivo);
/* 148:173 */         this.file = FuncionesUtiles.descargarArchivo(rutaArchivo + nombreArchivo, "application/xls", nombreArchivo);
/* 149:    */       }
/* 150:    */       else
/* 151:    */       {
/* 152:175 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 153:    */       }
/* 154:    */     }
/* 155:    */     catch (Exception e)
/* 156:    */     {
/* 157:178 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 158:179 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 159:    */     }
/* 160:182 */     return this.file;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String cargarTomaFisica(FileUploadEvent event)
/* 164:    */   {
/* 165:    */     try
/* 166:    */     {
/* 167:189 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 168:190 */       this.servicioMigracion.cargarTomaFisica(AppUtil.getOrganizacion().getId(), input, 1, getDetalleTomaFisica(), getListaCodigosProducto());
/* 169:191 */       String codigosProducto = "";
/* 170:193 */       for (DetalleTomaFisica dtf : getDetalleTomaFisica()) {
/* 171:194 */         actualizarCantidadAjustes(dtf);
/* 172:    */       }
/* 173:196 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 174:198 */       if ((getListaCodigosProducto() != null) && (getListaCodigosProducto().size() > 0))
/* 175:    */       {
/* 176:199 */         for (String cp : getListaCodigosProducto()) {
/* 177:200 */           codigosProducto = codigosProducto + " - " + cp;
/* 178:    */         }
/* 179:202 */         addInfoMessage("Codigos que no existen en el sistema o no estan en la lista de la toma fisica : " + codigosProducto);
/* 180:    */       }
/* 181:    */     }
/* 182:    */     catch (ExcepcionAS2 e)
/* 183:    */     {
/* 184:206 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 185:207 */       LOG.error("ERROR AL MIGRAR ASIENTO", e);
/* 186:208 */       e.printStackTrace();
/* 187:    */     }
/* 188:    */     catch (Exception e)
/* 189:    */     {
/* 190:210 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 191:211 */       e.printStackTrace();
/* 192:    */     }
/* 193:213 */     return null;
/* 194:    */   }
/* 195:    */   
/* 196:    */   @PostConstruct
/* 197:    */   public void init()
/* 198:    */   {
/* 199:221 */     getListaProductoBean().setActivo(true);
/* 200:222 */     getListaProductoBean().setTipoProducto(TipoProducto.ARTICULO);
/* 201:223 */     this.listaTomaFisica = new LazyDataModel()
/* 202:    */     {
/* 203:    */       private static final long serialVersionUID = 1790401081946531968L;
/* 204:    */       
/* 205:    */       public List<TomaFisica> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 206:    */       {
/* 207:229 */         List<TomaFisica> lista = new ArrayList();
/* 208:230 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 209:231 */         lista = TomaFisicaBean.this.servicioTomaFisica.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 210:    */         
/* 211:233 */         TomaFisicaBean.this.listaTomaFisica.setRowCount(TomaFisicaBean.this.servicioTomaFisica.contarPorCriterio(filters));
/* 212:    */         
/* 213:235 */         return lista;
/* 214:    */       }
/* 215:    */     };
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String editar()
/* 219:    */   {
/* 220:247 */     if (getTomaFisica().getId() > 0)
/* 221:    */     {
/* 222:248 */       if (getTomaFisica().getEstado() != Estado.APROBADO)
/* 223:    */       {
/* 224:    */         try
/* 225:    */         {
/* 226:250 */           this.tomaFisica = this.servicioTomaFisica.cargarDetalle(this.tomaFisica.getIdTomaFisica());
/* 227:251 */           this.hmproductos = new HashMap();
/* 228:252 */           for (DetalleTomaFisica dtf : this.tomaFisica.getListaDetalleTomaFisica()) {
/* 229:253 */             if (dtf.getProducto().isIndicadorLote()) {
/* 230:254 */               this.hmproductos.put(dtf.getProducto().getCodigo() + "^" + dtf.getLote().getId(), dtf);
/* 231:    */             } else {
/* 232:256 */               this.hmproductos.put(dtf.getProducto().getCodigo(), dtf);
/* 233:    */             }
/* 234:    */           }
/* 235:    */         }
/* 236:    */         catch (Exception e)
/* 237:    */         {
/* 238:260 */           addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 239:261 */           LOG.error("ERROR AL CARGAR DATOS " + e);
/* 240:262 */           e.printStackTrace();
/* 241:    */         }
/* 242:264 */         setEditado(true);
/* 243:265 */         asignarValorMovimiento();
/* 244:    */       }
/* 245:    */       else
/* 246:    */       {
/* 247:267 */         addInfoMessage(getLanguageController().getMensaje("msg_error_editar_toma_fisica_finalizada"));
/* 248:    */       }
/* 249:    */     }
/* 250:    */     else {
/* 251:270 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 252:    */     }
/* 253:272 */     return "";
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String guardar()
/* 257:    */   {
/* 258:    */     try
/* 259:    */     {
/* 260:283 */       this.tomaFisica.setEstado(Estado.ELABORADO);
/* 261:284 */       this.servicioTomaFisica.guardar(this.tomaFisica);
/* 262:285 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 263:286 */       cargarDatos();
/* 264:    */     }
/* 265:    */     catch (ExcepcionAS2 e)
/* 266:    */     {
/* 267:288 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 268:    */     }
/* 269:    */     catch (Exception e)
/* 270:    */     {
/* 271:290 */       e.printStackTrace();
/* 272:291 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 273:292 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 274:    */     }
/* 275:294 */     return "";
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String eliminar()
/* 279:    */   {
/* 280:    */     try
/* 281:    */     {
/* 282:305 */       this.servicioTomaFisica.eliminar(this.tomaFisica);
/* 283:306 */       cargarDatos();
/* 284:307 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 285:    */     }
/* 286:    */     catch (Exception e)
/* 287:    */     {
/* 288:309 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 289:310 */       LOG.error("ERROR AL ELMINAR DATOS", e);
/* 290:    */     }
/* 291:312 */     return "";
/* 292:    */   }
/* 293:    */   
/* 294:    */   public String limpiar()
/* 295:    */   {
/* 296:322 */     crearTomaFisica();
/* 297:323 */     return "";
/* 298:    */   }
/* 299:    */   
/* 300:    */   private void crearTomaFisica()
/* 301:    */   {
/* 302:330 */     this.tomaFisica = new TomaFisica();
/* 303:331 */     this.tomaFisica.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 304:332 */     this.tomaFisica.setIdSucursal(AppUtil.getSucursal().getId());
/* 305:333 */     this.tomaFisica.setFecha(new Date());
/* 306:334 */     if (!getListaDocumento().isEmpty())
/* 307:    */     {
/* 308:335 */       Documento documento = (Documento)getListaDocumento().get(0);
/* 309:336 */       this.tomaFisica.setDocumento(documento);
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   public String cargarDatos()
/* 314:    */   {
/* 315:347 */     setEditado(false);
/* 316:348 */     limpiar();
/* 317:349 */     return "";
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String agregarDetalle()
/* 321:    */   {
/* 322:358 */     DetalleTomaFisica d = new DetalleTomaFisica();
/* 323:359 */     d.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 324:360 */     d.setIdSucursal(AppUtil.getSucursal().getId());
/* 325:361 */     d.setProducto(new Producto());
/* 326:362 */     d.setTomaFisica(this.tomaFisica);
/* 327:363 */     this.tomaFisica.getListaDetalleTomaFisica().add(d);
/* 328:    */     
/* 329:365 */     return "";
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 333:    */   {
/* 334:374 */     DetalleTomaFisica dtf = (DetalleTomaFisica)this.dtDetalleTomaFisica.getRowData();
/* 335:375 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 336:377 */     if (this.hmproductos.containsKey(codigo)) {
/* 337:378 */       return;
/* 338:    */     }
/* 339:    */     try
/* 340:    */     {
/* 341:381 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 342:382 */       dtf.setProducto(producto);
/* 343:383 */       if (!producto.isIndicadorLote())
/* 344:    */       {
/* 345:384 */         dtf.setCantidadSistema(this.servicioProducto.getSaldo(producto.getId(), this.tomaFisica.getBodega().getId(), this.tomaFisica.getFecha()));
/* 346:385 */         dtf.setCantidadTomaFisica(dtf.getCantidadSistema());
/* 347:    */       }
/* 348:387 */       this.hmproductos.put(codigo, dtf);
/* 349:    */     }
/* 350:    */     catch (Exception e)
/* 351:    */     {
/* 352:389 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/* 353:    */     }
/* 354:    */   }
/* 355:    */   
/* 356:    */   public String eliminarDetalle()
/* 357:    */   {
/* 358:399 */     DetalleTomaFisica dtf = (DetalleTomaFisica)this.dtDetalleTomaFisica.getRowData();
/* 359:400 */     dtf.setEliminado(true);
/* 360:402 */     if (dtf.getProducto().isIndicadorLote()) {
/* 361:403 */       this.hmproductos.remove(dtf.getProducto().getCodigo() + "^" + dtf.getLote().getId());
/* 362:    */     } else {
/* 363:405 */       this.hmproductos.remove(dtf.getProducto().getCodigo());
/* 364:    */     }
/* 365:408 */     return "";
/* 366:    */   }
/* 367:    */   
/* 368:    */   public String asignarValorMovimiento()
/* 369:    */   {
/* 370:418 */     DetalleTomaFisica dtf = (DetalleTomaFisica)this.dtDetalleTomaFisica.getRowData();
/* 371:419 */     actualizarCantidadAjustes(dtf);
/* 372:    */     
/* 373:421 */     return "";
/* 374:    */   }
/* 375:    */   
/* 376:    */   private void actualizarCantidadAjustes(DetalleTomaFisica dtf)
/* 377:    */   {
/* 378:428 */     BigDecimal diferencia = dtf.getCantidadTomaFisica().subtract(dtf.getCantidadSistema());
/* 379:429 */     dtf.setTraMovimientoIngreso(BigDecimal.ZERO);
/* 380:430 */     dtf.setTraMovimientoEgreso(BigDecimal.ZERO);
/* 381:431 */     if (diferencia.compareTo(BigDecimal.ZERO) > 0) {
/* 382:432 */       dtf.setTraMovimientoIngreso(diferencia);
/* 383:433 */     } else if (diferencia.compareTo(BigDecimal.ZERO) < 0) {
/* 384:434 */       dtf.setTraMovimientoEgreso(diferencia.negate());
/* 385:    */     }
/* 386:    */   }
/* 387:    */   
/* 388:    */   public String finalizarTomaFisica()
/* 389:    */   {
/* 390:    */     try
/* 391:    */     {
/* 392:446 */       if (this.tomaFisica.getEstado() != Estado.APROBADO) {
/* 393:447 */         this.servicioTomaFisica.finalizar(this.tomaFisica);
/* 394:    */       } else {
/* 395:449 */         addErrorMessage(getLanguageController().getMensaje("msg_error_toma_fisica_aprobada"));
/* 396:    */       }
/* 397:    */     }
/* 398:    */     catch (ExcepcionAS2Inventario e)
/* 399:    */     {
/* 400:453 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 401:454 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 402:    */     }
/* 403:    */     catch (ExcepcionAS2Financiero e)
/* 404:    */     {
/* 405:456 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 406:457 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 407:    */     }
/* 408:    */     catch (ExcepcionAS2 e)
/* 409:    */     {
/* 410:459 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 411:460 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 412:    */     }
/* 413:    */     catch (AS2Exception e)
/* 414:    */     {
/* 415:462 */       this.exContabilizacion = e;
/* 416:463 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 417:464 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 418:465 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 419:    */     }
/* 420:468 */     return "";
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void cargarTodo(ActionEvent event)
/* 424:    */   {
/* 425:472 */     Map<String, DetalleTomaFisica> hashDetalles = new HashMap();
/* 426:473 */     for (DetalleTomaFisica dtf : getTomaFisica().getListaDetalleTomaFisica())
/* 427:    */     {
/* 428:474 */       dtf.setEliminado(true);
/* 429:475 */       if (dtf.getProducto().isIndicadorLote()) {
/* 430:477 */         hashDetalles.put(dtf.getProducto().getCodigo() + "^" + dtf.getLote().getId(), dtf);
/* 431:    */       } else {
/* 432:479 */         hashDetalles.put(dtf.getProducto().getCodigo(), dtf);
/* 433:    */       }
/* 434:    */     }
/* 435:482 */     for (SaldoProducto sp : this.servicioProducto.obtenerProductosConSaldoBodega(getTomaFisica().getBodega(), getTomaFisica().getFecha(), true))
/* 436:    */     {
/* 437:484 */       DetalleTomaFisica dtf = (DetalleTomaFisica)hashDetalles.get(sp.getProducto().getCodigo());
/* 438:485 */       if (dtf == null)
/* 439:    */       {
/* 440:486 */         dtf = new DetalleTomaFisica();
/* 441:487 */         dtf.setIdOrganizacion(this.tomaFisica.getIdOrganizacion());
/* 442:488 */         dtf.setIdSucursal(this.tomaFisica.getIdSucursal());
/* 443:489 */         dtf.setProducto(sp.getProducto());
/* 444:490 */         dtf.setTomaFisica(this.tomaFisica);
/* 445:491 */         getTomaFisica().getListaDetalleTomaFisica().add(dtf);
/* 446:    */       }
/* 447:493 */       dtf.setCantidadSistema(sp.getSaldo());
/* 448:494 */       dtf.setCantidadTomaFisica(sp.getSaldo());
/* 449:495 */       dtf.setEliminado(false);
/* 450:496 */       this.hmproductos.put(sp.getProducto().getCodigo(), dtf);
/* 451:    */     }
/* 452:498 */     for (SaldoProductoLote spl : this.servicioProducto.obtenerProductosConSaldoBodegaLote(getTomaFisica().getBodega(), getTomaFisica().getFecha(), null, true))
/* 453:    */     {
/* 454:501 */       DetalleTomaFisica dtf = (DetalleTomaFisica)hashDetalles.get(spl.getProducto().getCodigo() + "^" + spl.getLote().getId());
/* 455:503 */       if (dtf == null)
/* 456:    */       {
/* 457:504 */         dtf = new DetalleTomaFisica();
/* 458:505 */         dtf.setProducto(spl.getProducto());
/* 459:506 */         dtf.setLote(spl.getLote());
/* 460:507 */         dtf.setTomaFisica(this.tomaFisica);
/* 461:508 */         getTomaFisica().getListaDetalleTomaFisica().add(dtf);
/* 462:    */       }
/* 463:510 */       dtf.setCantidadSistema(spl.getSaldo());
/* 464:511 */       dtf.setCantidadTomaFisica(spl.getSaldo());
/* 465:512 */       dtf.setEliminado(false);
/* 466:513 */       this.hmproductos.put(spl.getProducto().getCodigo() + "^" + spl.getLote().getId(), dtf);
/* 467:    */     }
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void cargarProducto(Producto producto)
/* 471:    */   {
/* 472:521 */     if (this.hmproductos.containsKey(producto.getCodigo())) {
/* 473:522 */       return;
/* 474:    */     }
/* 475:525 */     DetalleTomaFisica dtf = new DetalleTomaFisica();
/* 476:526 */     dtf.setIdOrganizacion(this.tomaFisica.getIdOrganizacion());
/* 477:527 */     dtf.setIdSucursal(this.tomaFisica.getIdSucursal());
/* 478:528 */     dtf.setProducto(producto);
/* 479:529 */     dtf.setTomaFisica(this.tomaFisica);
/* 480:530 */     if (!producto.isIndicadorLote())
/* 481:    */     {
/* 482:531 */       dtf.setCantidadSistema(this.servicioProducto.getSaldo(producto.getId(), this.tomaFisica.getBodega().getId(), this.tomaFisica.getFecha()));
/* 483:532 */       dtf.setCantidadTomaFisica(dtf.getCantidadSistema());
/* 484:    */     }
/* 485:534 */     getTomaFisica().getListaDetalleTomaFisica().add(dtf);
/* 486:535 */     this.hmproductos.put(producto.getCodigo(), dtf);
/* 487:    */   }
/* 488:    */   
/* 489:    */   public void eventBodegaOrigen()
/* 490:    */   {
/* 491:539 */     for (DetalleTomaFisica dtm : getTomaFisica().getListaDetalleTomaFisica()) {
/* 492:540 */       dtm.setEliminado(true);
/* 493:    */     }
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void cargarProductoLote(SaldoProductoLote saldoProductoLote)
/* 497:    */   {
/* 498:547 */     if (this.hmproductos.containsKey(saldoProductoLote.getProducto().getCodigo() + "^" + saldoProductoLote.getLote().getId())) {
/* 499:548 */       return;
/* 500:    */     }
/* 501:551 */     DetalleTomaFisica dtf = new DetalleTomaFisica();
/* 502:552 */     dtf.setIdOrganizacion(this.tomaFisica.getIdOrganizacion());
/* 503:553 */     dtf.setIdSucursal(this.tomaFisica.getIdSucursal());
/* 504:554 */     dtf.setProducto(saldoProductoLote.getProducto());
/* 505:555 */     dtf.setLote(saldoProductoLote.getLote());
/* 506:556 */     dtf.setTomaFisica(this.tomaFisica);
/* 507:557 */     actualizarCantidadesLote(dtf);
/* 508:558 */     getTomaFisica().getListaDetalleTomaFisica().add(dtf);
/* 509:559 */     this.hmproductos.put(saldoProductoLote.getProducto().getCodigo() + "^" + saldoProductoLote.getLote().getId(), dtf);
/* 510:    */   }
/* 511:    */   
/* 512:    */   public List<Lote> autocompletarLotes(String consulta)
/* 513:    */   {
/* 514:563 */     DetalleTomaFisica detalleTomaFisica = (DetalleTomaFisica)this.dtDetalleTomaFisica.getRowData();
/* 515:564 */     return this.servicioLote.autocompletarLote(detalleTomaFisica.getProducto(), consulta);
/* 516:    */   }
/* 517:    */   
/* 518:    */   public List<Bodega> autocompletarBodega(String consulta)
/* 519:    */   {
/* 520:574 */     List<Bodega> lista = new ArrayList();
/* 521:575 */     String sortField = "nombre";
/* 522:576 */     HashMap<String, String> filters = new HashMap();
/* 523:577 */     filters.put("nombreOCodigo", consulta.trim() + "%");
/* 524:578 */     lista = this.servicioBodega.obtenerListaCombo(sortField, true, filters);
/* 525:    */     
/* 526:580 */     return lista;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void generaMovimientoInventario() {}
/* 530:    */   
/* 531:    */   public void asignarValorCeroODiferenteCeroMovimiento()
/* 532:    */   {
/* 533:    */     Iterator localIterator;
/* 534:    */     DetalleTomaFisica dtf;
/* 535:    */     Object hashDetalles;
/* 536:592 */     if (isValoresTomaDiferenteCero())
/* 537:    */     {
/* 538:594 */       this.valoresTomaDiferenteCero = false;
/* 539:595 */       for (localIterator = getTomaFisica().getListaDetalleTomaFisica().iterator(); localIterator.hasNext();)
/* 540:    */       {
/* 541:595 */         dtf = (DetalleTomaFisica)localIterator.next();
/* 542:596 */         dtf.setCantidadTomaFisica(BigDecimal.ZERO);
/* 543:597 */         dtf.setTraMovimientoEgreso(BigDecimal.ZERO);
/* 544:598 */         dtf.setTraMovimientoIngreso(BigDecimal.ZERO);
/* 545:599 */         actualizarCantidadAjustes(dtf);
/* 546:    */       }
/* 547:    */     }
/* 548:    */     else
/* 549:    */     {
/* 550:603 */       this.valoresTomaDiferenteCero = true;
/* 551:    */       
/* 552:    */ 
/* 553:606 */       hashDetalles = new HashMap();
/* 554:607 */       for (DetalleTomaFisica dtf : getTomaFisica().getListaDetalleTomaFisica()) {
/* 555:608 */         if (!dtf.isEliminado())
/* 556:    */         {
/* 557:609 */           dtf.setCantidadSistema(BigDecimal.ZERO);
/* 558:610 */           if (dtf.getProducto().isIndicadorLote()) {
/* 559:611 */             ((Map)hashDetalles).put(dtf.getProducto().getCodigo() + "^" + dtf.getLote().getId(), dtf);
/* 560:    */           } else {
/* 561:613 */             ((Map)hashDetalles).put(dtf.getProducto().getCodigo(), dtf);
/* 562:    */           }
/* 563:    */         }
/* 564:    */       }
/* 565:619 */       for (SaldoProducto sp : this.servicioProducto.obtenerProductosConSaldoBodega(getTomaFisica().getBodega(), getTomaFisica().getFecha(), false))
/* 566:    */       {
/* 567:620 */         DetalleTomaFisica dtf = (DetalleTomaFisica)((Map)hashDetalles).get(sp.getProducto().getCodigo());
/* 568:621 */         if (dtf != null)
/* 569:    */         {
/* 570:622 */           dtf.setCantidadSistema(sp.getSaldo());
/* 571:623 */           dtf.setCantidadTomaFisica(sp.getSaldo());
/* 572:624 */           actualizarCantidadAjustes(dtf);
/* 573:    */         }
/* 574:    */       }
/* 575:629 */       for (SaldoProductoLote spl : this.servicioProducto.obtenerProductosConSaldoBodegaLote(getTomaFisica().getBodega(), getTomaFisica().getFecha(), null, false))
/* 576:    */       {
/* 577:631 */         DetalleTomaFisica dtf = (DetalleTomaFisica)((Map)hashDetalles).get(spl.getProducto().getCodigo() + "^" + spl.getLote().getId());
/* 578:632 */         if (dtf != null)
/* 579:    */         {
/* 580:633 */           dtf.setCantidadSistema(spl.getSaldo());
/* 581:634 */           dtf.setCantidadTomaFisica(spl.getSaldo());
/* 582:635 */           actualizarCantidadAjustes(dtf);
/* 583:    */         }
/* 584:    */       }
/* 585:    */     }
/* 586:    */   }
/* 587:    */   
/* 588:    */   public TomaFisica getTomaFisica()
/* 589:    */   {
/* 590:649 */     return this.tomaFisica;
/* 591:    */   }
/* 592:    */   
/* 593:    */   public void setTomaFisica(TomaFisica tomaFisica)
/* 594:    */   {
/* 595:659 */     this.tomaFisica = tomaFisica;
/* 596:    */   }
/* 597:    */   
/* 598:    */   public LazyDataModel<TomaFisica> getListaTomaFisica()
/* 599:    */   {
/* 600:668 */     return this.listaTomaFisica;
/* 601:    */   }
/* 602:    */   
/* 603:    */   public void setListaTomaFisica(LazyDataModel<TomaFisica> listaTomaFisica)
/* 604:    */   {
/* 605:678 */     this.listaTomaFisica = listaTomaFisica;
/* 606:    */   }
/* 607:    */   
/* 608:    */   public DataTable getDtTomaFisica()
/* 609:    */   {
/* 610:687 */     return this.dtTomaFisica;
/* 611:    */   }
/* 612:    */   
/* 613:    */   public void setDtTomaFisica(DataTable dtTomaFisica)
/* 614:    */   {
/* 615:697 */     this.dtTomaFisica = dtTomaFisica;
/* 616:    */   }
/* 617:    */   
/* 618:    */   public List<Documento> getListaDocumento()
/* 619:    */   {
/* 620:    */     try
/* 621:    */     {
/* 622:708 */       if (this.listaDocumento == null) {
/* 623:709 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.TOMA_FISICA);
/* 624:    */       }
/* 625:    */     }
/* 626:    */     catch (ExcepcionAS2 e)
/* 627:    */     {
/* 628:712 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 629:    */     }
/* 630:715 */     return this.listaDocumento;
/* 631:    */   }
/* 632:    */   
/* 633:    */   public List<Documento> getListaDocumentoAjuste()
/* 634:    */   {
/* 635:723 */     if (this.listaDocumentoAjuste == null)
/* 636:    */     {
/* 637:724 */       Map<String, String> filters = new HashMap();
/* 638:725 */       filters.put("documentoBase", "" + DocumentoBase.AJUSTE_INVENTARIO);
/* 639:726 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 640:727 */       filters.put("activo", "true");
/* 641:728 */       filters.put("indicadorGeneraCosto", "false");
/* 642:729 */       this.listaDocumentoAjuste = this.servicioDocumento.obtenerListaCombo("predeterminado", false, filters);
/* 643:    */     }
/* 644:732 */     return this.listaDocumentoAjuste;
/* 645:    */   }
/* 646:    */   
/* 647:    */   public List<Documento> getListaDocumentoAjusteIngreso()
/* 648:    */   {
/* 649:736 */     if (this.listaDocumentoAjusteIngreso == null)
/* 650:    */     {
/* 651:737 */       this.listaDocumentoAjusteIngreso = new ArrayList();
/* 652:738 */       for (Documento documento : getListaDocumentoAjuste()) {
/* 653:739 */         if (documento.getOperacion() == 1) {
/* 654:740 */           this.listaDocumentoAjusteIngreso.add(documento);
/* 655:    */         }
/* 656:    */       }
/* 657:    */     }
/* 658:744 */     return this.listaDocumentoAjusteIngreso;
/* 659:    */   }
/* 660:    */   
/* 661:    */   public List<Documento> getListaDocumentoAjusteEgreso()
/* 662:    */   {
/* 663:748 */     if (this.listaDocumentoAjusteEgreso == null)
/* 664:    */     {
/* 665:749 */       this.listaDocumentoAjusteEgreso = new ArrayList();
/* 666:750 */       for (Documento documento : getListaDocumentoAjuste()) {
/* 667:751 */         if (documento.getOperacion() == -1) {
/* 668:752 */           this.listaDocumentoAjusteEgreso.add(documento);
/* 669:    */         }
/* 670:    */       }
/* 671:    */     }
/* 672:756 */     return this.listaDocumentoAjusteEgreso;
/* 673:    */   }
/* 674:    */   
/* 675:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 676:    */   {
/* 677:766 */     this.listaDocumento = listaDocumento;
/* 678:    */   }
/* 679:    */   
/* 680:    */   public List<DetalleTomaFisica> getDetalleTomaFisica()
/* 681:    */   {
/* 682:775 */     List<DetalleTomaFisica> detalle = new ArrayList();
/* 683:776 */     for (DetalleTomaFisica dtm : getTomaFisica().getListaDetalleTomaFisica()) {
/* 684:777 */       if (!dtm.isEliminado()) {
/* 685:778 */         detalle.add(dtm);
/* 686:    */       }
/* 687:    */     }
/* 688:781 */     return detalle;
/* 689:    */   }
/* 690:    */   
/* 691:    */   public DataTable getDtDetalleTomaFisica()
/* 692:    */   {
/* 693:790 */     return this.dtDetalleTomaFisica;
/* 694:    */   }
/* 695:    */   
/* 696:    */   public void setDtDetalleTomaFisica(DataTable dtDetalleTomaFisica)
/* 697:    */   {
/* 698:800 */     this.dtDetalleTomaFisica = dtDetalleTomaFisica;
/* 699:    */   }
/* 700:    */   
/* 701:    */   public BigDecimal getTotalMovimientoIngreso()
/* 702:    */   {
/* 703:809 */     this.totalMovimientoIngreso = BigDecimal.ZERO;
/* 704:811 */     for (DetalleTomaFisica detalle : getTomaFisica().getListaDetalleTomaFisica()) {
/* 705:812 */       if (!detalle.isEliminado()) {
/* 706:813 */         this.totalMovimientoIngreso = this.totalMovimientoIngreso.add(detalle.getTraMovimientoIngreso());
/* 707:    */       }
/* 708:    */     }
/* 709:817 */     return this.totalMovimientoIngreso;
/* 710:    */   }
/* 711:    */   
/* 712:    */   public void setTotalMovimientoIngreso(BigDecimal totalMovimientoIngreso)
/* 713:    */   {
/* 714:827 */     this.totalMovimientoIngreso = totalMovimientoIngreso;
/* 715:    */   }
/* 716:    */   
/* 717:    */   public BigDecimal getTotalMovimientoEgreso()
/* 718:    */   {
/* 719:836 */     this.totalMovimientoEgreso = BigDecimal.ZERO;
/* 720:838 */     for (DetalleTomaFisica detalle : getTomaFisica().getListaDetalleTomaFisica()) {
/* 721:839 */       if (!detalle.isEliminado()) {
/* 722:840 */         this.totalMovimientoEgreso = this.totalMovimientoEgreso.add(detalle.getTraMovimientoEgreso());
/* 723:    */       }
/* 724:    */     }
/* 725:843 */     return this.totalMovimientoEgreso;
/* 726:    */   }
/* 727:    */   
/* 728:    */   public void setTotalMovimientoEgreso(BigDecimal totalMovimientoEgreso)
/* 729:    */   {
/* 730:853 */     this.totalMovimientoEgreso = totalMovimientoEgreso;
/* 731:    */   }
/* 732:    */   
/* 733:    */   public Producto[] getProductosSeleccionados()
/* 734:    */   {
/* 735:862 */     return this.productosSeleccionados;
/* 736:    */   }
/* 737:    */   
/* 738:    */   public void setProductosSeleccionados(Producto[] productosSeleccionados)
/* 739:    */   {
/* 740:872 */     this.productosSeleccionados = productosSeleccionados;
/* 741:    */   }
/* 742:    */   
/* 743:    */   public AS2Exception getExContabilizacion()
/* 744:    */   {
/* 745:876 */     return this.exContabilizacion;
/* 746:    */   }
/* 747:    */   
/* 748:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 749:    */   {
/* 750:880 */     this.exContabilizacion = exContabilizacion;
/* 751:    */   }
/* 752:    */   
/* 753:    */   public void cargarProducto() {}
/* 754:    */   
/* 755:    */   public void actualizarCantidadesLote(DetalleTomaFisica dtf)
/* 756:    */   {
/* 757:893 */     dtf.setCantidadSistema(this.servicioProducto.getSaldoLote(dtf.getProducto().getId(), this.tomaFisica.getBodega().getId(), dtf.getLote().getId(), this.tomaFisica
/* 758:894 */       .getFecha()));
/* 759:895 */     dtf.setCantidadTomaFisica(dtf.getCantidadSistema());
/* 760:896 */     actualizarCantidadAjustes(dtf);
/* 761:    */   }
/* 762:    */   
/* 763:    */   public String getCodigoCabecera()
/* 764:    */   {
/* 765:903 */     return this.codigoCabecera;
/* 766:    */   }
/* 767:    */   
/* 768:    */   public void setCodigoCabecera(String codigoCabecera)
/* 769:    */   {
/* 770:911 */     this.codigoCabecera = codigoCabecera;
/* 771:    */   }
/* 772:    */   
/* 773:    */   public void agregarDetalleDesdeCodigoCabecera()
/* 774:    */   {
/* 775:915 */     if ((null == this.codigoCabecera) || (this.codigoCabecera.isEmpty())) {
/* 776:916 */       return;
/* 777:    */     }
/* 778:    */     try
/* 779:    */     {
/* 780:921 */       Producto producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(this.codigoCabecera, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 781:    */       
/* 782:    */ 
/* 783:924 */       String key = producto.getCodigo();
/* 784:925 */       if (null != producto.getLote()) {
/* 785:926 */         key = producto.getCodigo() + "^" + producto.getLote().getId();
/* 786:    */       }
/* 787:930 */       DetalleTomaFisica dtf = (DetalleTomaFisica)this.hmproductos.get(key);
/* 788:931 */       if (dtf == null)
/* 789:    */       {
/* 790:932 */         dtf = new DetalleTomaFisica();
/* 791:933 */         dtf.setIdOrganizacion(this.tomaFisica.getIdOrganizacion());
/* 792:934 */         dtf.setIdSucursal(this.tomaFisica.getIdSucursal());
/* 793:935 */         dtf.setProducto(producto);
/* 794:936 */         dtf.setLote(producto.getLote());
/* 795:937 */         dtf.setTomaFisica(this.tomaFisica);
/* 796:938 */         getTomaFisica().getListaDetalleTomaFisica().add(dtf);
/* 797:939 */         if (null != producto.getLote()) {
/* 798:940 */           this.hmproductos.put(producto.getCodigo() + "^" + producto.getLote().getId(), dtf);
/* 799:    */         } else {
/* 800:942 */           this.hmproductos.put(producto.getCodigo(), dtf);
/* 801:    */         }
/* 802:    */       }
/* 803:945 */       if (null != producto.getLote())
/* 804:    */       {
/* 805:946 */         actualizarCantidadesLote(dtf);
/* 806:    */       }
/* 807:947 */       else if (!producto.isIndicadorLote())
/* 808:    */       {
/* 809:948 */         dtf.setCantidadSistema(this.servicioProducto.getSaldo(producto.getId(), this.tomaFisica.getBodega().getId(), this.tomaFisica.getFecha()));
/* 810:949 */         dtf.setCantidadTomaFisica(dtf.getCantidadSistema());
/* 811:950 */         actualizarCantidadAjustes(dtf);
/* 812:    */       }
/* 813:952 */       this.codigoCabecera = "";
/* 814:    */     }
/* 815:    */     catch (ExcepcionAS2 e)
/* 816:    */     {
/* 817:954 */       this.codigoCabecera = "";
/* 818:955 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 819:    */     }
/* 820:    */   }
/* 821:    */   
/* 822:    */   public void setFile(StreamedContent file)
/* 823:    */   {
/* 824:960 */     this.file = file;
/* 825:    */   }
/* 826:    */   
/* 827:    */   public List<String> getListaCodigosProducto()
/* 828:    */   {
/* 829:964 */     return this.listaCodigosProducto == null ? (this.listaCodigosProducto = new ArrayList()) : this.listaCodigosProducto;
/* 830:    */   }
/* 831:    */   
/* 832:    */   public void setListaCodigosProducto(List<String> listaCodigosProducto)
/* 833:    */   {
/* 834:968 */     this.listaCodigosProducto = listaCodigosProducto;
/* 835:    */   }
/* 836:    */   
/* 837:    */   public boolean isValoresTomaDiferenteCero()
/* 838:    */   {
/* 839:972 */     return this.valoresTomaDiferenteCero;
/* 840:    */   }
/* 841:    */   
/* 842:    */   public void setValoresTomaDiferenteCero(boolean valoresTomaDiferenteCero)
/* 843:    */   {
/* 844:976 */     this.valoresTomaDiferenteCero = valoresTomaDiferenteCero;
/* 845:    */   }
/* 846:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.TomaFisicaBean
 * JD-Core Version:    0.7.0.1
 */