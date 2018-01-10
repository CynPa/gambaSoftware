/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Ciudad;
/*   8:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   9:    */ import com.asinfo.as2.entities.DetalleGuiaRemision;
/*  10:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.Empresa;
/*  13:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  14:    */ import com.asinfo.as2.entities.GuiaRemision;
/*  15:    */ import com.asinfo.as2.entities.Organizacion;
/*  16:    */ import com.asinfo.as2.entities.Producto;
/*  17:    */ import com.asinfo.as2.entities.Secuencia;
/*  18:    */ import com.asinfo.as2.entities.Sucursal;
/*  19:    */ import com.asinfo.as2.entities.Ubicacion;
/*  20:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  21:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  22:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  23:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  24:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  26:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  27:    */ import com.asinfo.as2.util.AppUtil;
/*  28:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  29:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  30:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*  31:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  32:    */ import java.io.BufferedInputStream;
/*  33:    */ import java.io.InputStream;
/*  34:    */ import java.math.BigDecimal;
/*  35:    */ import java.math.RoundingMode;
/*  36:    */ import java.util.ArrayList;
/*  37:    */ import java.util.HashMap;
/*  38:    */ import java.util.Iterator;
/*  39:    */ import java.util.List;
/*  40:    */ import java.util.Map;
/*  41:    */ import javax.annotation.PostConstruct;
/*  42:    */ import javax.ejb.EJB;
/*  43:    */ import javax.faces.bean.ManagedBean;
/*  44:    */ import javax.faces.bean.ManagedProperty;
/*  45:    */ import javax.faces.bean.ViewScoped;
/*  46:    */ import javax.faces.component.html.HtmlInputText;
/*  47:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  48:    */ import org.apache.log4j.Logger;
/*  49:    */ import org.primefaces.component.datatable.DataTable;
/*  50:    */ import org.primefaces.event.FileUploadEvent;
/*  51:    */ import org.primefaces.event.SelectEvent;
/*  52:    */ import org.primefaces.model.LazyDataModel;
/*  53:    */ import org.primefaces.model.SortOrder;
/*  54:    */ import org.primefaces.model.UploadedFile;
/*  55:    */ 
/*  56:    */ @ManagedBean
/*  57:    */ @ViewScoped
/*  58:    */ public class GuiaRemisionElectronicaBean
/*  59:    */   extends GuiaRemisionBean
/*  60:    */ {
/*  61:    */   @EJB
/*  62:    */   private ServicioProducto servicioProducto;
/*  63:    */   @EJB
/*  64:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  65:    */   private static final long serialVersionUID = 5743838206711541349L;
/*  66:    */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  67:    */   private DataTable dtDetalleGuiaRemision;
/*  68:    */   private String codigoCabecera;
/*  69:    */   private LazyDataModel<GuiaRemision> listaGuiaRemision;
/*  70: 64 */   private List<ErrorCarga> errores = new ArrayList();
/*  71:    */   private DataTable dtGuiaRemision;
/*  72:    */   private String mails;
/*  73:    */   @ManagedProperty("#{listaProductoBean}")
/*  74:    */   private ListaProductoBean listaProductoBean;
/*  75:    */   
/*  76:    */   public ListaProductoBean getListaProductoBean()
/*  77:    */   {
/*  78: 74 */     return this.listaProductoBean;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/*  82:    */   {
/*  83: 78 */     this.listaProductoBean = listaProductoBean;
/*  84:    */   }
/*  85:    */   
/*  86:    */   @PostConstruct
/*  87:    */   public void init()
/*  88:    */   {
/*  89: 83 */     setEditado(false);
/*  90: 84 */     getListaProductoBean().setActivo(true);
/*  91:    */     
/*  92: 86 */     this.listaGuiaRemision = new LazyDataModel()
/*  93:    */     {
/*  94:    */       private static final long serialVersionUID = 1L;
/*  95:    */       
/*  96:    */       public List<GuiaRemision> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  97:    */       {
/*  98: 93 */         List<GuiaRemision> lista = new ArrayList();
/*  99:    */         
/* 100: 95 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 101:    */         
/* 102: 97 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 103:    */         
/* 104: 99 */         lista = GuiaRemisionElectronicaBean.this.servicioGuiaRemision.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 105:100 */         GuiaRemisionElectronicaBean.this.listaGuiaRemision.setRowCount(GuiaRemisionElectronicaBean.this.servicioGuiaRemision.contarPorCriterio(filters));
/* 106:    */         
/* 107:102 */         return lista;
/* 108:    */       }
/* 109:    */     };
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String eliminar()
/* 113:    */   {
/* 114:110 */     GuiaRemision grAux = this.servicioGuiaRemision.buscarPorId(Integer.valueOf(this.guiaRemision.getId()));
/* 115:111 */     this.servicioGuiaRemision.anular(grAux);
/* 116:112 */     addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 117:113 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void actualizarCliente(SelectEvent event)
/* 121:    */   {
/* 122:117 */     this.guiaRemision.setEmpresa((Empresa)event.getObject());
/* 123:118 */     cargarDirecciones();
/* 124:    */     
/* 125:120 */     this.guiaRemision.setEmailCliente(this.servicioEmpresa.cargarMails(this.guiaRemision.getEmpresa(), DocumentoBase.GUIA_REMISION));
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String cancelar()
/* 129:    */   {
/* 130:125 */     setEditado(false);
/* 131:126 */     limpiar();
/* 132:127 */     return "";
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String editar()
/* 136:    */   {
/* 137:132 */     if ((this.guiaRemision != null) && (this.guiaRemision.getId() > 0)) {
/* 138:    */       try
/* 139:    */       {
/* 140:135 */         this.guiaRemision = this.servicioGuiaRemision.cargarDetalle(this.guiaRemision.getId());
/* 141:136 */         if (this.guiaRemision.getEmpresa() == null) {
/* 142:137 */           this.guiaRemision.setEmpresa(new Empresa());
/* 143:    */         }
/* 144:139 */         if (this.guiaRemision.getDireccionEmpresa() == null) {
/* 145:140 */           this.guiaRemision.setDireccionEmpresa(new DireccionEmpresa());
/* 146:    */         }
/* 147:143 */         cargarDirecciones();
/* 148:144 */         actualizarDocumento();
/* 149:    */         
/* 150:146 */         setEditado(true);
/* 151:    */       }
/* 152:    */       catch (Exception e)
/* 153:    */       {
/* 154:149 */         addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 155:150 */         LOG.error("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 156:    */       }
/* 157:    */     } else {
/* 158:153 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 159:    */     }
/* 160:156 */     return "";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void cargarDirecciones()
/* 164:    */   {
/* 165:165 */     this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(this.guiaRemision.getEmpresa().getId());
/* 166:166 */     this.guiaRemision.setDireccionEmpresa(null);
/* 167:167 */     if (this.guiaRemision.getDireccionEmpresa() == null) {
/* 168:168 */       for (DireccionEmpresa de : this.listaDireccionEmpresa) {
/* 169:169 */         if (de.isIndicadorDireccionEnvio()) {
/* 170:170 */           this.guiaRemision.setDireccionEmpresa(de);
/* 171:    */         }
/* 172:    */       }
/* 173:    */     }
/* 174:174 */     if (this.guiaRemision.getDireccionEmpresa() == null) {
/* 175:175 */       for (DireccionEmpresa de : this.listaDireccionEmpresa) {
/* 176:176 */         if (de.isIndicadorDireccionPrincipal()) {
/* 177:177 */           this.guiaRemision.setDireccionEmpresa(de);
/* 178:    */         }
/* 179:    */       }
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 184:    */   {
/* 185:184 */     return this.listaDireccionEmpresa;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 189:    */   {
/* 190:188 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 194:    */   {
/* 195:192 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String agregarDetalle()
/* 199:    */   {
/* 200:202 */     DetalleGuiaRemision detalleGuiaRemision = new DetalleGuiaRemision();
/* 201:203 */     detalleGuiaRemision.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 202:204 */     detalleGuiaRemision.setIdSucursal(AppUtil.getSucursal().getId());
/* 203:205 */     detalleGuiaRemision.setProducto(new Producto());
/* 204:206 */     detalleGuiaRemision.setGuiaRemision(this.guiaRemision);
/* 205:    */     
/* 206:208 */     this.guiaRemision.getListaDetalleGuiaRemision().add(detalleGuiaRemision);
/* 207:    */     
/* 208:210 */     return "";
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void agregarDetalleDesdeCodigoCabecera()
/* 212:    */   {
/* 213:214 */     DetalleGuiaRemision detalleGuiaRemision = null;
/* 214:    */     try
/* 215:    */     {
/* 216:216 */       detalleGuiaRemision = new DetalleGuiaRemision();
/* 217:217 */       detalleGuiaRemision.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 218:218 */       detalleGuiaRemision.setIdSucursal(AppUtil.getSucursal().getId());
/* 219:219 */       detalleGuiaRemision.setProducto(new Producto());
/* 220:220 */       detalleGuiaRemision.setGuiaRemision(this.guiaRemision);
/* 221:    */       
/* 222:222 */       this.guiaRemision.getListaDetalleGuiaRemision().add(detalleGuiaRemision);
/* 223:223 */       cargarProductoDesdeCodigoEnDetalle(this.codigoCabecera, detalleGuiaRemision);
/* 224:224 */       this.codigoCabecera = "";
/* 225:    */     }
/* 226:    */     catch (ExcepcionAS2 e)
/* 227:    */     {
/* 228:226 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 229:227 */       detalleGuiaRemision.getProducto().setCodigo("");
/* 230:228 */       detalleGuiaRemision.getProducto().setNombre("");
/* 231:229 */       detalleGuiaRemision.setEliminado(true);
/* 232:230 */       this.codigoCabecera = "";
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public String eliminarDetalle()
/* 237:    */   {
/* 238:235 */     DetalleGuiaRemision detalleGuiaRemision = (DetalleGuiaRemision)this.dtDetalleGuiaRemision.getRowData();
/* 239:236 */     detalleGuiaRemision.setEliminado(true);
/* 240:237 */     return "";
/* 241:    */   }
/* 242:    */   
/* 243:    */   public DataTable getDtDetalleGuiaRemision()
/* 244:    */   {
/* 245:241 */     return this.dtDetalleGuiaRemision;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setDtDetalleGuiaRemision(DataTable dtDetalleGuiaRemision)
/* 249:    */   {
/* 250:245 */     this.dtDetalleGuiaRemision = dtDetalleGuiaRemision;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<DetalleGuiaRemision> getListaDetalleGuiaRemision()
/* 254:    */   {
/* 255:254 */     List<DetalleGuiaRemision> detalle = new ArrayList();
/* 256:255 */     for (DetalleGuiaRemision detalleGuiaRemision : this.guiaRemision.getListaDetalleGuiaRemision()) {
/* 257:257 */       if (!detalleGuiaRemision.isEliminado()) {
/* 258:258 */         detalle.add(detalleGuiaRemision);
/* 259:    */       }
/* 260:    */     }
/* 261:262 */     return detalle;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 265:    */   {
/* 266:271 */     DetalleGuiaRemision detalleGuiaRemision = null;
/* 267:    */     try
/* 268:    */     {
/* 269:273 */       String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 270:274 */       detalleGuiaRemision = (DetalleGuiaRemision)this.dtDetalleGuiaRemision.getRowData();
/* 271:275 */       cargarProductoDesdeCodigoEnDetalle(codigo, detalleGuiaRemision);
/* 272:    */     }
/* 273:    */     catch (ExcepcionAS2 e)
/* 274:    */     {
/* 275:277 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 276:278 */       detalleGuiaRemision.getProducto().setCodigo("");
/* 277:279 */       detalleGuiaRemision.getProducto().setNombre("");
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   private void cargarProductoDesdeCodigoEnDetalle(String codigo, DetalleGuiaRemision detalleGuiaRemision)
/* 282:    */     throws ExcepcionAS2
/* 283:    */   {
/* 284:284 */     Producto producto = null;
/* 285:    */     
/* 286:286 */     producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 287:287 */     detalleGuiaRemision.setProducto(producto);
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void cargarProducto(Producto producto)
/* 291:    */   {
/* 292:    */     try
/* 293:    */     {
/* 294:294 */       if (producto != null)
/* 295:    */       {
/* 296:295 */         DetalleGuiaRemision detalleGuiaRemision = new DetalleGuiaRemision();
/* 297:296 */         detalleGuiaRemision.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 298:297 */         detalleGuiaRemision.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 299:298 */         detalleGuiaRemision.setProducto(producto);
/* 300:    */         
/* 301:300 */         detalleGuiaRemision.setCantidad(producto.getTraCantidad().setScale(2, RoundingMode.HALF_UP));
/* 302:    */         
/* 303:302 */         this.guiaRemision.getListaDetalleGuiaRemision().add(detalleGuiaRemision);
/* 304:303 */         getListaProductoBean().setProducto(null);
/* 305:304 */         getListaProductoBean().setSaldoProductoLote(null);
/* 306:    */       }
/* 307:    */     }
/* 308:    */     catch (Exception e)
/* 309:    */     {
/* 310:308 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 311:309 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 312:    */     }
/* 313:    */     finally
/* 314:    */     {
/* 315:311 */       getListaProductoBean().setProducto(null);
/* 316:312 */       getListaProductoBean().setSaldoProductoLote(null);
/* 317:    */     }
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String limpiar()
/* 321:    */   {
/* 322:323 */     crearGuiaRemision();
/* 323:324 */     return "";
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void crearGuiaRemision()
/* 327:    */   {
/* 328:332 */     super.crearGuiaRemision();
/* 329:    */     
/* 330:334 */     Documento documento = null;
/* 331:335 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 332:    */     {
/* 333:336 */       documento = (Documento)getListaDocumento().get(0);
/* 334:337 */       this.guiaRemision.setDocumento(documento);
/* 335:338 */       actualizarDocumento();
/* 336:    */     }
/* 337:    */     else
/* 338:    */     {
/* 339:340 */       documento = new Documento();
/* 340:341 */       documento.setSecuencia(new Secuencia());
/* 341:342 */       this.guiaRemision.setDocumento(documento);
/* 342:    */     }
/* 343:346 */     this.guiaRemision.setDespachoCliente(this.despachoCliente);
/* 344:347 */     this.guiaRemision.setTransferenciaBodega(this.transferenciaBodega);
/* 345:348 */     if (this.despachoCliente != null)
/* 346:    */     {
/* 347:349 */       this.guiaRemision.setCiudadDestino(this.despachoCliente.getDireccionEmpresa().getCiudad());
/* 348:350 */       this.guiaRemision.setEmailCliente(this.servicioEmpresa.cargarMails(this.despachoCliente.getEmpresa(), DocumentoBase.GUIA_REMISION));
/* 349:    */     }
/* 350:352 */     if (this.transferenciaBodega != null) {}
/* 351:357 */     Map<String, String> filters = new HashMap();
/* 352:358 */     filters.put("predeterminado", "true");
/* 353:359 */     List<Ciudad> listaCiudad = this.servicioCiudad.obtenerListaCombo("nombre", true, filters);
/* 354:360 */     if (listaCiudad.size() > 0) {
/* 355:361 */       this.guiaRemision.setCiudadOrigen((Ciudad)listaCiudad.get(0));
/* 356:    */     }
/* 357:365 */     int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(AppUtil.getOrganizacion().getId()).booleanValue() ? 2 : 1;
/* 358:366 */     this.guiaRemision.setAmbiente(ambiente);
/* 359:    */     
/* 360:368 */     this.guiaRemision.setDireccionMatriz(AppUtil.getDireccionMatriz());
/* 361:369 */     this.guiaRemision.setDireccionSucursal(AppUtil.getSucursal().getUbicacion().getDireccionCompleta());
/* 362:    */   }
/* 363:    */   
/* 364:    */   public String guardar()
/* 365:    */   {
/* 366:    */     try
/* 367:    */     {
/* 368:376 */       if (this.guiaRemision.getFacturaCliente() != null) {
/* 369:377 */         this.guiaRemision.setFacturaCliente(this.servicioFacturaCliente.cargarDetalle(this.guiaRemision.getFacturaCliente().getId()));
/* 370:    */       }
/* 371:379 */       this.servicioGuiaRemision.guardar(this.guiaRemision);
/* 372:380 */       limpiar();
/* 373:381 */       setEditado(false);
/* 374:    */     }
/* 375:    */     catch (ExcepcionAS2Identification e)
/* 376:    */     {
/* 377:384 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 378:385 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 379:    */     }
/* 380:    */     catch (Exception e)
/* 381:    */     {
/* 382:387 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 383:388 */       LOG.error("ERROR AL GUARDAR DATOS GUIA REMISION", e);
/* 384:    */     }
/* 385:390 */     return "";
/* 386:    */   }
/* 387:    */   
/* 388:    */   public ServicioProducto getServicioProducto()
/* 389:    */   {
/* 390:394 */     return this.servicioProducto;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setServicioProducto(ServicioProducto servicioProducto)
/* 394:    */   {
/* 395:398 */     this.servicioProducto = servicioProducto;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public String getCodigoCabecera()
/* 399:    */   {
/* 400:402 */     return this.codigoCabecera;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void setCodigoCabecera(String codigoCabecera)
/* 404:    */   {
/* 405:406 */     this.codigoCabecera = codigoCabecera;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public LazyDataModel<GuiaRemision> getListaGuiaRemision()
/* 409:    */   {
/* 410:410 */     return this.listaGuiaRemision;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setListaGuiaRemision(LazyDataModel<GuiaRemision> listaGuiaRemision)
/* 414:    */   {
/* 415:414 */     this.listaGuiaRemision = listaGuiaRemision;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public DataTable getDtGuiaRemision()
/* 419:    */   {
/* 420:418 */     return this.dtGuiaRemision;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setDtGuiaRemision(DataTable dtGuiaRemision)
/* 424:    */   {
/* 425:422 */     this.dtGuiaRemision = dtGuiaRemision;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public List<FacturaCliente> autocompletarFacturas(String consulta)
/* 429:    */   {
/* 430:432 */     Map<String, String> filters = new HashMap();
/* 431:433 */     List<FacturaCliente> lista = new ArrayList();
/* 432:435 */     if (this.guiaRemision.getEmpresa() != null) {
/* 433:436 */       filters.put("empresa.idEmpresa", "" + this.guiaRemision.getEmpresa().getId());
/* 434:    */     }
/* 435:439 */     if ((consulta != null) && (!consulta.isEmpty())) {
/* 436:440 */       filters.put("numero", "%" + consulta);
/* 437:    */     }
/* 438:443 */     filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/* 439:444 */     filters.put("estado", "!=" + Estado.ANULADO.toString());
/* 440:445 */     lista = this.servicioFacturaCliente.obtenerListaCombo("fecha", true, filters);
/* 441:    */     
/* 442:447 */     return lista;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public String cargarGuiaRemision(FileUploadEvent event)
/* 446:    */   {
/* 447:456 */     List<GuiaRemision> listaGuiaRemision = new ArrayList();
/* 448:    */     try
/* 449:    */     {
/* 450:459 */       String fileName = "migracion_guia_remision" + event.getFile().getFileName();
/* 451:460 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 452:461 */       listaGuiaRemision = this.servicioGuiaRemision.migracionGuiaRemision(AppUtil.getOrganizacion().getId(), fileName, input, 4);
/* 453:462 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 454:464 */       for (localIterator = listaGuiaRemision.iterator(); localIterator.hasNext();)
/* 455:    */       {
/* 456:464 */         gr = (GuiaRemision)localIterator.next();
/* 457:465 */         this.servicioGuiaRemision.guardar(gr);
/* 458:    */       }
/* 459:    */     }
/* 460:    */     catch (AS2Exception e)
/* 461:    */     {
/* 462:    */       Iterator localIterator;
/* 463:469 */       e.printStackTrace();
/* 464:470 */       List<String> listaMensajes = e.getCodigoMensajes();
/* 465:471 */       int i = 0;
/* 466:472 */       for (String a : listaMensajes)
/* 467:    */       {
/* 468:473 */         i = a.indexOf("*");
/* 469:474 */         a.substring(0, i + 1);
/* 470:475 */         ErrorCarga ec = new ErrorCarga();
/* 471:476 */         ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/* 472:477 */         this.errores.add(ec);
/* 473:    */       }
/* 474:479 */       for (String a : e.getMensajes())
/* 475:    */       {
/* 476:480 */         ErrorCarga ec = new ErrorCarga();
/* 477:481 */         ec.setError(a);
/* 478:482 */         this.errores.add(ec);
/* 479:    */       }
/* 480:    */     }
/* 481:    */     catch (ExcepcionAS2Financiero e)
/* 482:    */     {
/* 483:    */       GuiaRemision gr;
/* 484:487 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 485:488 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 486:    */     }
/* 487:    */     catch (ExcepcionAS2Compras e)
/* 488:    */     {
/* 489:491 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 490:492 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 491:    */     }
/* 492:    */     catch (ExcepcionAS2 e)
/* 493:    */     {
/* 494:495 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 495:496 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 496:    */     }
/* 497:    */     catch (Exception e)
/* 498:    */     {
/* 499:499 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 500:500 */       e.printStackTrace();
/* 501:    */     }
/* 502:502 */     return null;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public List<ErrorCarga> getErrores()
/* 506:    */   {
/* 507:506 */     return this.errores;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public void setErrores(List<ErrorCarga> errores)
/* 511:    */   {
/* 512:510 */     this.errores = errores;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public String getMails()
/* 516:    */   {
/* 517:514 */     return this.mails;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void setMails(String mails)
/* 521:    */   {
/* 522:518 */     this.mails = mails;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void enviarMail()
/* 526:    */   {
/* 527:    */     try
/* 528:    */     {
/* 529:523 */       if (getGuiaRemision() != null)
/* 530:    */       {
/* 531:524 */         GuiaRemision guiaRem = this.servicioGuiaRemision.cargarDetalle(getGuiaRemision().getId());
/* 532:525 */         if (((guiaRem.getDocumento() != null) && (!guiaRem.getDocumento().isIndicadorDocumentoElectronico())) || 
/* 533:526 */           (guiaRem.getEstado().equals(Estado.ANULADO)) || (guiaRem.getEstado().equals(Estado.EN_ESPERA)) || 
/* 534:527 */           (guiaRem.getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA))) {
/* 535:528 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 536:    */         } else {
/* 537:530 */           this.servicioGuiaRemision.enviarMail(guiaRem, this.mails);
/* 538:    */         }
/* 539:    */       }
/* 540:    */       else
/* 541:    */       {
/* 542:533 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 543:    */       }
/* 544:    */     }
/* 545:    */     catch (ExcepcionAS2 e)
/* 546:    */     {
/* 547:536 */       addErrorMessage(this.despachoCliente.getNumero() + " -> " + this.mails + e.getMessage() + e.getCodigoExcepcion());
/* 548:    */     }
/* 549:    */     catch (Exception e)
/* 550:    */     {
/* 551:538 */       addErrorMessage(this.despachoCliente.getNumero() + " -> " + this.mails + e.getMessage());
/* 552:    */     }
/* 553:    */   }
/* 554:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.GuiaRemisionElectronicaBean
 * JD-Core Version:    0.7.0.1
 */