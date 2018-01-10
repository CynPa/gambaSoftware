/*   1:    */ package com.asinfo.as2.calidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.calidad.procesos.servicio.ServicioControlCalidad;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.RegistroPeso;
/*  12:    */ import com.asinfo.as2.entities.calidad.CategoriaVariableCalidad;
/*  13:    */ import com.asinfo.as2.entities.calidad.MotivoCastigoCalidad;
/*  14:    */ import com.asinfo.as2.entities.calidad.VariableCalidad;
/*  15:    */ import com.asinfo.as2.entities.calidad.VariableCalidadProducto;
/*  16:    */ import com.asinfo.as2.entities.calidad.VariableCalidadRegistroPeso;
/*  17:    */ import com.asinfo.as2.enumeraciones.ClaseBodegaEnum;
/*  18:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*  19:    */ import com.asinfo.as2.enumeraciones.EstadoRegistroPeso;
/*  20:    */ import com.asinfo.as2.enumeraciones.TipoRegistroPeso;
/*  21:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  22:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  23:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  26:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*  27:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  28:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  29:    */ import com.asinfo.as2.util.AppUtil;
/*  30:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  31:    */ import com.asinfo.as2.utils.JsfUtil;
/*  32:    */ import java.io.BufferedInputStream;
/*  33:    */ import java.io.IOException;
/*  34:    */ import java.io.InputStream;
/*  35:    */ import java.math.BigDecimal;
/*  36:    */ import java.util.ArrayList;
/*  37:    */ import java.util.Date;
/*  38:    */ import java.util.HashMap;
/*  39:    */ import java.util.List;
/*  40:    */ import java.util.Map;
/*  41:    */ import java.util.Map.Entry;
/*  42:    */ import javax.annotation.PostConstruct;
/*  43:    */ import javax.ejb.EJB;
/*  44:    */ import javax.faces.bean.ManagedBean;
/*  45:    */ import javax.faces.bean.ViewScoped;
/*  46:    */ import javax.faces.model.SelectItem;
/*  47:    */ import org.apache.log4j.Logger;
/*  48:    */ import org.primefaces.component.datatable.DataTable;
/*  49:    */ import org.primefaces.event.FileUploadEvent;
/*  50:    */ import org.primefaces.model.LazyDataModel;
/*  51:    */ import org.primefaces.model.SortOrder;
/*  52:    */ import org.primefaces.model.UploadedFile;
/*  53:    */ 
/*  54:    */ @ManagedBean
/*  55:    */ @ViewScoped
/*  56:    */ public class ControlCalidadMPBean
/*  57:    */   extends PageControllerAS2
/*  58:    */ {
/*  59:    */   private static final long serialVersionUID = 1L;
/*  60:    */   @EJB
/*  61:    */   protected transient ServicioRegistroPeso servicioRegistroPeso;
/*  62:    */   @EJB
/*  63:    */   protected transient ServicioControlCalidad servicioControlCalidad;
/*  64:    */   @EJB
/*  65:    */   protected transient ServicioBodega servicioBodega;
/*  66:    */   @EJB
/*  67:    */   protected transient ServicioPedidoProveedor servicioPedidoProveedor;
/*  68:    */   @EJB
/*  69:    */   protected ServicioProducto servicioProducto;
/*  70:    */   @EJB
/*  71:    */   protected ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  72:    */   @EJB
/*  73:    */   protected transient ServicioListaPrecios servicioListaPrecios;
/*  74:    */   @EJB
/*  75:    */   protected transient ServicioGenerico<MotivoCastigoCalidad> servicioMotivoCastigoCalidad;
/*  76:    */   protected RegistroPeso registroPeso;
/*  77:    */   protected LazyDataModel<RegistroPeso> listaRegistroPeso;
/*  78:    */   private DataTable dtRegistroPeso;
/*  79:    */   private SelectItem[] listaEstadoControlCalidadItem;
/*  80:    */   private SelectItem[] listaTipoRegistroPesoItem;
/*  81: 90 */   private Map<CategoriaVariableCalidad, List<VariableCalidadRegistroPeso>> mapaCategoriaVariableCalidad = new HashMap();
/*  82:    */   protected Bodega bodegaCalidad;
/*  83:    */   private List<Bodega> listaBodegaCalidad;
/*  84:    */   protected PedidoProveedor pedidoProveedor;
/*  85:    */   private List<MotivoCastigoCalidad> listaMotivoCastigoCalidad;
/*  86:    */   private MotivoCastigoCalidad motivoCastigo;
/*  87:    */   private MotivoCastigoCalidad motivoRechazo;
/*  88:    */   EstadoControlCalidad estadoControlCalidad;
/*  89:    */   
/*  90:    */   @PostConstruct
/*  91:    */   public void init()
/*  92:    */   {
/*  93:101 */     this.listaRegistroPeso = new LazyDataModel()
/*  94:    */     {
/*  95:    */       private static final long serialVersionUID = 1L;
/*  96:    */       
/*  97:    */       public List<RegistroPeso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  98:    */       {
/*  99:107 */         List<RegistroPeso> lista = new ArrayList();
/* 100:108 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 101:110 */         if (filters.size() == 0) {
/* 102:111 */           filters.put("controlCalidadFinalizado", "false");
/* 103:    */         }
/* 104:114 */         filters.put("OR~TR~1~tipoRegistroPeso", "" + TipoRegistroPeso.RECEPCION_TRANSFERENCIA);
/* 105:115 */         filters.put("OR~TR~2~tipoRegistroPeso", "" + TipoRegistroPeso.INGRESO_MATERIA_PRIMA);
/* 106:    */         
/* 107:117 */         filters.put("estado", EstadoRegistroPeso.SEGUNDO_PESO.name());
/* 108:    */         
/* 109:119 */         ControlCalidadMPBean.this.agregarFiltroOrganizacion(filters);
/* 110:    */         
/* 111:121 */         lista = ControlCalidadMPBean.this.servicioRegistroPeso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 112:122 */         ControlCalidadMPBean.this.listaRegistroPeso.setRowCount(ControlCalidadMPBean.this.servicioRegistroPeso.contarPorCriterio(filters));
/* 113:    */         
/* 114:124 */         return lista;
/* 115:    */       }
/* 116:    */     };
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String editar()
/* 120:    */   {
/* 121:131 */     if ((this.registroPeso != null) && (this.registroPeso.getId() != 0))
/* 122:    */     {
/* 123:132 */       this.registroPeso = this.servicioRegistroPeso.cargarDetalle(this.registroPeso.getId());
/* 124:    */       
/* 125:134 */       this.estadoControlCalidad = this.registroPeso.getEstadoControlCalidad();
/* 126:136 */       if ((this.estadoControlCalidad.equals(EstadoControlCalidad.PENDIENTE)) || (this.estadoControlCalidad.equals(EstadoControlCalidad.EN_ESPERA))) {
/* 127:137 */         this.estadoControlCalidad = EstadoControlCalidad.EN_ESPERA;
/* 128:    */       }
/* 129:141 */       cargaListaVariableCalidadRegistroPeso();
/* 130:    */       
/* 131:143 */       actualizaValorNIRAceptable();
/* 132:    */       
/* 133:145 */       clasificaVariables();
/* 134:    */       
/* 135:147 */       cargarFechaCaducidad();
/* 136:149 */       if (!this.registroPeso.getProducto().getIndicadorControlCalidad().booleanValue())
/* 137:    */       {
/* 138:150 */         this.bodegaCalidad = this.registroPeso.getBodega();
/* 139:151 */         this.registroPeso.setBodegaLiberar(this.registroPeso.getBodega());
/* 140:    */       }
/* 141:154 */       setEditado(true);
/* 142:    */     }
/* 143:    */     else
/* 144:    */     {
/* 145:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 146:    */     }
/* 147:158 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected void cargarFechaCaducidad()
/* 151:    */   {
/* 152:162 */     if ((this.registroPeso != null) && (this.registroPeso.getFechaCaducidad() == null) && (this.registroPeso.getProducto() != null))
/* 153:    */     {
/* 154:163 */       Date fechaCaducidad = FuncionesUtiles.sumarFechaDiasMeses(this.registroPeso.getFecha(), this.registroPeso.getProducto().getDiasCaducidad().intValue());
/* 155:164 */       this.registroPeso.setFechaCaducidad(fechaCaducidad);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected void cargaListaVariableCalidadRegistroPeso()
/* 160:    */   {
/* 161:169 */     List<VariableCalidadRegistroPeso> lvcrp = this.servicioControlCalidad.getListaVariableCalidadRegistroPeso(this.registroPeso);
/* 162:170 */     if ((lvcrp.isEmpty()) || (lvcrp.size() == 0)) {
/* 163:171 */       for (VariableCalidadProducto vcp : this.servicioControlCalidad.getListaVariableCalidadProducto(this.registroPeso.getProducto())) {
/* 164:172 */         if (vcp.getVariableCalidad().isIndicadorMateriaPrima())
/* 165:    */         {
/* 166:173 */           VariableCalidadRegistroPeso vcrp = new VariableCalidadRegistroPeso();
/* 167:174 */           vcrp.setIdOrganizacion(this.registroPeso.getIdOrganizacion());
/* 168:175 */           vcrp.setIdSucursal(this.registroPeso.getIdSucursal());
/* 169:176 */           vcrp.setVariableCalidadProducto(vcp);
/* 170:177 */           vcrp.setRegistroPeso(this.registroPeso);
/* 171:178 */           lvcrp.add(vcrp);
/* 172:    */         }
/* 173:    */       }
/* 174:    */     }
/* 175:182 */     this.registroPeso.setListaVariableCalidadRegistroPeso(lvcrp);
/* 176:    */   }
/* 177:    */   
/* 178:    */   protected void actualizaValorNIRAceptable()
/* 179:    */   {
/* 180:186 */     for (VariableCalidadRegistroPeso vcrp : this.registroPeso.getListaVariableCalidadRegistroPeso()) {
/* 181:187 */       actualizaValorNIRAceptable(vcrp);
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void actualizaValorNIRAceptable(VariableCalidadRegistroPeso vcrp)
/* 186:    */   {
/* 187:192 */     if ((vcrp.getValorNir().compareTo(BigDecimal.ZERO) == 0) || (
/* 188:193 */       (vcrp.getValorNir().compareTo(vcrp.getVariableCalidadProducto().getValorMinimo()) >= 0) && 
/* 189:194 */       (vcrp.getValorNir().compareTo(vcrp.getVariableCalidadProducto().getValorMaximo()) <= 0))) {
/* 190:195 */       vcrp.setAceptable(true);
/* 191:    */     } else {
/* 192:197 */       vcrp.setAceptable(false);
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   protected void clasificaVariables()
/* 197:    */   {
/* 198:202 */     for (VariableCalidadRegistroPeso vcrp : this.registroPeso.getListaVariableCalidadRegistroPeso())
/* 199:    */     {
/* 200:203 */       CategoriaVariableCalidad cvc = vcrp.getVariableCalidadProducto().getVariableCalidad().getCategoriaVariableCalidad();
/* 201:204 */       if (!this.mapaCategoriaVariableCalidad.containsKey(cvc)) {
/* 202:205 */         this.mapaCategoriaVariableCalidad.put(cvc, new ArrayList());
/* 203:    */       }
/* 204:207 */       ((List)this.mapaCategoriaVariableCalidad.get(cvc)).add(vcrp);
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String guardar()
/* 209:    */   {
/* 210:    */     try
/* 211:    */     {
/* 212:214 */       this.servicioControlCalidad.guardarRegistroPeso(this.registroPeso, this.estadoControlCalidad);
/* 213:215 */       limpiar();
/* 214:216 */       setEditado(false);
/* 215:217 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 216:    */     }
/* 217:    */     catch (AS2Exception e)
/* 218:    */     {
/* 219:219 */       JsfUtil.addErrorMessage(e, "");
/* 220:    */     }
/* 221:    */     catch (ExcepcionAS2 e)
/* 222:    */     {
/* 223:221 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/* 224:    */     }
/* 225:    */     catch (Exception e)
/* 226:    */     {
/* 227:223 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 228:224 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 229:    */     }
/* 230:227 */     return "";
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String eliminar()
/* 234:    */   {
/* 235:232 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 236:233 */     return "";
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String limpiar()
/* 240:    */   {
/* 241:238 */     this.mapaCategoriaVariableCalidad = new HashMap();
/* 242:239 */     this.bodegaCalidad = null;
/* 243:240 */     this.motivoCastigo = null;
/* 244:241 */     this.motivoRechazo = null;
/* 245:    */     
/* 246:243 */     return "";
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String crear()
/* 250:    */   {
/* 251:248 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 252:249 */     return "";
/* 253:    */   }
/* 254:    */   
/* 255:    */   public String cargarDatos()
/* 256:    */   {
/* 257:254 */     return "";
/* 258:    */   }
/* 259:    */   
/* 260:    */   public SelectItem[] getListaEstadoControlCalidadItem()
/* 261:    */   {
/* 262:258 */     if (this.listaEstadoControlCalidadItem == null)
/* 263:    */     {
/* 264:259 */       List<SelectItem> lista = new ArrayList();
/* 265:260 */       lista.add(new SelectItem("", ""));
/* 266:261 */       for (EstadoControlCalidad estadoCC : EstadoControlCalidad.values()) {
/* 267:262 */         lista.add(new SelectItem(estadoCC, estadoCC.getNombre()));
/* 268:    */       }
/* 269:264 */       this.listaEstadoControlCalidadItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 270:    */     }
/* 271:267 */     return this.listaEstadoControlCalidadItem;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public RegistroPeso getRegistroPeso()
/* 275:    */   {
/* 276:271 */     return this.registroPeso;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setRegistroPeso(RegistroPeso registroPeso)
/* 280:    */   {
/* 281:275 */     this.registroPeso = registroPeso;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public LazyDataModel<RegistroPeso> getListaRegistroPeso()
/* 285:    */   {
/* 286:279 */     return this.listaRegistroPeso;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setListaRegistroPeso(LazyDataModel<RegistroPeso> listaRegistroPeso)
/* 290:    */   {
/* 291:283 */     this.listaRegistroPeso = listaRegistroPeso;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public DataTable getDtRegistroPeso()
/* 295:    */   {
/* 296:287 */     return this.dtRegistroPeso;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setDtRegistroPeso(DataTable dtRegistroPeso)
/* 300:    */   {
/* 301:291 */     this.dtRegistroPeso = dtRegistroPeso;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public String cargarExcel(FileUploadEvent event)
/* 305:    */   {
/* 306:    */     try
/* 307:    */     {
/* 308:296 */       InputStream istream = new BufferedInputStream(event.getFile().getInputstream());
/* 309:    */       
/* 310:298 */       this.registroPeso = this.servicioControlCalidad.cargarExcel(istream, this.registroPeso);
/* 311:    */       
/* 312:300 */       actualizaValorNIRAceptable();
/* 313:    */       
/* 314:302 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 315:    */     }
/* 316:    */     catch (ExcepcionAS2 e)
/* 317:    */     {
/* 318:304 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 319:305 */       LOG.error("ERROR AL CARGAR EXCEL", e);
/* 320:    */     }
/* 321:    */     catch (IOException e)
/* 322:    */     {
/* 323:307 */       JsfUtil.addErrorMessage(e.getMessage());
/* 324:308 */       e.printStackTrace();
/* 325:    */     }
/* 326:311 */     return null;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public Map<CategoriaVariableCalidad, List<VariableCalidadRegistroPeso>> getMapaCategoriaVariableCalidad()
/* 330:    */   {
/* 331:318 */     return this.mapaCategoriaVariableCalidad;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setMapaCategoriaVariableCalidad(Map<CategoriaVariableCalidad, List<VariableCalidadRegistroPeso>> mapaCategoriaVariableCalidad)
/* 335:    */   {
/* 336:326 */     this.mapaCategoriaVariableCalidad = mapaCategoriaVariableCalidad;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public List<Map.Entry<CategoriaVariableCalidad, List<VariableCalidadRegistroPeso>>> getMapaCVCEntryList()
/* 340:    */   {
/* 341:331 */     return new ArrayList(this.mapaCategoriaVariableCalidad.entrySet());
/* 342:    */   }
/* 343:    */   
/* 344:    */   public Bodega getBodegaCalidad()
/* 345:    */   {
/* 346:338 */     return this.bodegaCalidad;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void setBodegaCalidad(Bodega bodegaCalidad)
/* 350:    */   {
/* 351:346 */     this.bodegaCalidad = bodegaCalidad;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public List<Bodega> getListaBodegaCalidad()
/* 355:    */   {
/* 356:353 */     if (this.listaBodegaCalidad == null)
/* 357:    */     {
/* 358:354 */       this.listaBodegaCalidad = new ArrayList();
/* 359:355 */       List<Bodega> lBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 360:356 */       for (Bodega bodega : lBodega) {
/* 361:357 */         if (!bodega.getClaseBodega().equals(ClaseBodegaEnum.CUARENTENA)) {
/* 362:358 */           this.listaBodegaCalidad.add(bodega);
/* 363:    */         }
/* 364:    */       }
/* 365:    */     }
/* 366:363 */     return this.listaBodegaCalidad;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setListaBodegaCalidad(List<Bodega> listaBodegaCalidad)
/* 370:    */   {
/* 371:371 */     this.listaBodegaCalidad = listaBodegaCalidad;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public PedidoProveedor getPedidoProveedor()
/* 375:    */   {
/* 376:378 */     return this.pedidoProveedor;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 380:    */   {
/* 381:386 */     this.pedidoProveedor = pedidoProveedor;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void liberar()
/* 385:    */   {
/* 386:    */     try
/* 387:    */     {
/* 388:391 */       if (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA))
/* 389:    */       {
/* 390:392 */         validarBodegaLiberacion();
/* 391:393 */         this.servicioControlCalidad.liberar(this.registroPeso, this.bodegaCalidad);
/* 392:    */       }
/* 393:    */       else
/* 394:    */       {
/* 395:395 */         this.servicioControlCalidad.guardarRegistroPeso(this.registroPeso, EstadoControlCalidad.LIBERADO);
/* 396:    */       }
/* 397:397 */       limpiar();
/* 398:398 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 399:399 */       setEditado(false);
/* 400:    */     }
/* 401:    */     catch (ExcepcionAS2 e)
/* 402:    */     {
/* 403:401 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 404:    */     }
/* 405:    */     catch (AS2Exception e)
/* 406:    */     {
/* 407:403 */       JsfUtil.addErrorMessage(e, "");
/* 408:    */     }
/* 409:    */   }
/* 410:    */   
/* 411:    */   public void rechazar()
/* 412:    */   {
/* 413:408 */     if (this.motivoRechazo == null)
/* 414:    */     {
/* 415:409 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_motivo_castigo"));
/* 416:410 */       return;
/* 417:    */     }
/* 418:412 */     this.registroPeso.setMotivoCastigoCalidad(this.motivoRechazo);
/* 419:    */     try
/* 420:    */     {
/* 421:414 */       this.servicioControlCalidad.rechazar(this.registroPeso);
/* 422:415 */       setEditado(false);
/* 423:    */     }
/* 424:    */     catch (ExcepcionAS2 e)
/* 425:    */     {
/* 426:417 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 427:    */     }
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void castigar()
/* 431:    */   {
/* 432:    */     try
/* 433:    */     {
/* 434:423 */       if (this.motivoCastigo == null)
/* 435:    */       {
/* 436:424 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_motivo_castigo"));
/* 437:425 */         return;
/* 438:    */       }
/* 439:427 */       this.registroPeso.setMotivoCastigoCalidad(this.motivoCastigo);
/* 440:428 */       this.servicioControlCalidad.guardarRegistroPeso(this.registroPeso, EstadoControlCalidad.ESPERA_CASTIGO);
/* 441:429 */       limpiar();
/* 442:430 */       setEditado(false);
/* 443:    */     }
/* 444:    */     catch (ExcepcionAS2 e)
/* 445:    */     {
/* 446:432 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 447:    */     }
/* 448:    */     catch (AS2Exception e)
/* 449:    */     {
/* 450:434 */       JsfUtil.addErrorMessage(e, "");
/* 451:    */     }
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void validarBodegaLiberacion()
/* 455:    */   {
/* 456:439 */     if (this.bodegaCalidad == null)
/* 457:    */     {
/* 458:440 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_bodega"));
/* 459:441 */       return;
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   public List<MotivoCastigoCalidad> getListaMotivoCastigoCalidad()
/* 464:    */   {
/* 465:449 */     if (this.listaMotivoCastigoCalidad == null) {
/* 466:450 */       this.listaMotivoCastigoCalidad = this.servicioMotivoCastigoCalidad.obtenerListaCombo(MotivoCastigoCalidad.class, "nombre", true, 
/* 467:451 */         agregarFiltroOrganizacion(null));
/* 468:    */     }
/* 469:454 */     return this.listaMotivoCastigoCalidad;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public SelectItem[] getListaTipoRegistroPesoItem()
/* 473:    */   {
/* 474:458 */     if (this.listaTipoRegistroPesoItem == null)
/* 475:    */     {
/* 476:459 */       List<SelectItem> lista = new ArrayList();
/* 477:460 */       lista.add(new SelectItem("", ""));
/* 478:461 */       lista.add(new SelectItem(TipoRegistroPeso.INGRESO_MATERIA_PRIMA, TipoRegistroPeso.INGRESO_MATERIA_PRIMA.getNombre()));
/* 479:462 */       lista.add(new SelectItem(TipoRegistroPeso.RECEPCION_TRANSFERENCIA, TipoRegistroPeso.RECEPCION_TRANSFERENCIA.getNombre()));
/* 480:463 */       this.listaTipoRegistroPesoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 481:    */     }
/* 482:465 */     return this.listaTipoRegistroPesoItem;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public void setListaMotivoCastigoCalidad(List<MotivoCastigoCalidad> listaMotivoCastigoCalidad)
/* 486:    */   {
/* 487:473 */     this.listaMotivoCastigoCalidad = listaMotivoCastigoCalidad;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public MotivoCastigoCalidad getMotivoCastigo()
/* 491:    */   {
/* 492:480 */     return this.motivoCastigo;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public void setMotivoCastigo(MotivoCastigoCalidad motivoCastigo)
/* 496:    */   {
/* 497:488 */     this.motivoCastigo = motivoCastigo;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public MotivoCastigoCalidad getMotivoRechazo()
/* 501:    */   {
/* 502:495 */     return this.motivoRechazo;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void setMotivoRechazo(MotivoCastigoCalidad motivoRechazo)
/* 506:    */   {
/* 507:503 */     this.motivoRechazo = motivoRechazo;
/* 508:    */   }
/* 509:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.procesos.controller.ControlCalidadMPBean
 * JD-Core Version:    0.7.0.1
 */