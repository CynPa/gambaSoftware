/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.entities.DetalleMovimientoUnidadManejo;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.MotivoAjusteUnidadManejo;
/*  13:    */ import com.asinfo.as2.entities.MovimientoUnidadManejo;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.entities.Subempresa;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.entities.Transportista;
/*  18:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  19:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  20:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  21:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  22:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  23:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  24:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  26:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  27:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoUnidadManejo;
/*  28:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  29:    */ import com.asinfo.as2.util.AppUtil;
/*  30:    */ import com.asinfo.as2.ventas.configuracion.UnidadManejoBean;
/*  31:    */ import java.util.ArrayList;
/*  32:    */ import java.util.Collection;
/*  33:    */ import java.util.Date;
/*  34:    */ import java.util.HashMap;
/*  35:    */ import java.util.List;
/*  36:    */ import java.util.Map;
/*  37:    */ import javax.annotation.PostConstruct;
/*  38:    */ import javax.ejb.EJB;
/*  39:    */ import javax.faces.bean.ManagedBean;
/*  40:    */ import javax.faces.bean.ManagedProperty;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import javax.faces.context.FacesContext;
/*  43:    */ import javax.faces.context.PartialViewContext;
/*  44:    */ import javax.faces.model.SelectItem;
/*  45:    */ import org.apache.log4j.Logger;
/*  46:    */ import org.primefaces.component.datatable.DataTable;
/*  47:    */ import org.primefaces.context.RequestContext;
/*  48:    */ import org.primefaces.event.SelectEvent;
/*  49:    */ import org.primefaces.model.LazyDataModel;
/*  50:    */ import org.primefaces.model.SortOrder;
/*  51:    */ 
/*  52:    */ @ManagedBean
/*  53:    */ @ViewScoped
/*  54:    */ public class AjusteUnidadManejoBean
/*  55:    */   extends PageControllerAS2
/*  56:    */ {
/*  57:    */   private static final long serialVersionUID = -5463907651730263568L;
/*  58:    */   @EJB
/*  59:    */   protected transient ServicioMovimientoUnidadManejo servicioMovimientoUnidadManejo;
/*  60:    */   @EJB
/*  61:    */   protected ServicioGenerico<MotivoAjusteUnidadManejo> servicioMotivoAjusteUnidadManejo;
/*  62:    */   @EJB
/*  63:    */   protected ServicioSucursal servicioSucursal;
/*  64:    */   @EJB
/*  65:    */   protected ServicioDocumento servicioDocumento;
/*  66:    */   @EJB
/*  67:    */   protected ServicioOrganizacion servicioOrganizacion;
/*  68:    */   @EJB
/*  69:    */   protected ServicioEmpresa servicioEmpresa;
/*  70:    */   @EJB
/*  71:    */   protected ServicioTransportista servicioTransportista;
/*  72:    */   @EJB
/*  73:    */   protected ServicioMovimientoUnidadManejo serviciosMovimientoUnidadManejo;
/*  74:    */   @ManagedProperty("#{unidadManejoBean}")
/*  75:    */   private UnidadManejoBean unidadManejoBean;
/*  76:    */   protected LazyDataModel<MovimientoUnidadManejo> listaMovimientoUnidadManejo;
/*  77:    */   private List<Documento> listaDocumento;
/*  78:    */   private List<Transportista> listaTransportista;
/*  79:    */   private List<Sucursal> listaSucursal;
/*  80:    */   private List<Subempresa> listaSubempresa;
/*  81:    */   private List<SelectItem> listaTipoAjuste;
/*  82:100 */   private boolean indicadorSucursal = true;
/*  83:    */   private boolean indicadorCliente;
/*  84:    */   private boolean indicadorTransportista;
/*  85:    */   private DataTable dtListaAjuste;
/*  86:    */   private DataTable dtDetalleAjuste;
/*  87:    */   protected MovimientoUnidadManejo movimientoUnidadManejo;
/*  88:    */   
/*  89:    */   protected static enum enumClasificacion
/*  90:    */   {
/*  91:111 */     Sucursal,  Cliente,  Transportista;
/*  92:    */     
/*  93:    */     private enumClasificacion() {}
/*  94:    */   }
/*  95:    */   
/*  96:114 */   private enumClasificacion clasificacion = enumClasificacion.Sucursal;
/*  97:    */   
/*  98:    */   @PostConstruct
/*  99:    */   public void init()
/* 100:    */   {
/* 101:119 */     verificarTipoMovimiento();
/* 102:    */     
/* 103:121 */     getUnidadManejoBean().setOrder("nombre");
/* 104:    */     
/* 105:    */ 
/* 106:124 */     this.listaMovimientoUnidadManejo = new LazyDataModel()
/* 107:    */     {
/* 108:    */       private static final long serialVersionUID = 1L;
/* 109:    */       
/* 110:    */       public List<MovimientoUnidadManejo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 111:    */       {
/* 112:131 */         List<MovimientoUnidadManejo> lista = new ArrayList();
/* 113:    */         
/* 114:133 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 115:135 */         if (filters == null) {
/* 116:136 */           filters = new HashMap();
/* 117:    */         }
/* 118:138 */         filters.put("documento.documentoBase", AjusteUnidadManejoBean.this.getDocumentoBase().toString());
/* 119:139 */         filters = AjusteUnidadManejoBean.this.getFiltros(filters);
/* 120:    */         
/* 121:141 */         lista.addAll(AjusteUnidadManejoBean.this.servicioMovimientoUnidadManejo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters));
/* 122:    */         
/* 123:143 */         AjusteUnidadManejoBean.this.listaMovimientoUnidadManejo.setRowCount(AjusteUnidadManejoBean.this.servicioMovimientoUnidadManejo.contarPorCirterio(filters));
/* 124:    */         
/* 125:145 */         return lista;
/* 126:    */       }
/* 127:    */     };
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected void verificarTipoMovimiento() {}
/* 131:    */   
/* 132:    */   public Map<String, String> getFiltros(Map<String, String> filters)
/* 133:    */   {
/* 134:156 */     filters.put("indicadorAjusteUnidadManejo", "true");
/* 135:157 */     return filters;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String anular()
/* 139:    */   {
/* 140:    */     try
/* 141:    */     {
/* 142:163 */       this.servicioMovimientoUnidadManejo.anular(getMovimientoUnidadManejo());
/* 143:164 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 144:165 */       setEditado(false);
/* 145:    */     }
/* 146:    */     catch (ExcepcionAS2Inventario e)
/* 147:    */     {
/* 148:168 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 149:169 */       LOG.error("ERROR AL ANULAR UN MOVIMIENTO", e);
/* 150:    */     }
/* 151:    */     catch (ExcepcionAS2Financiero e)
/* 152:    */     {
/* 153:171 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 154:172 */       LOG.error("ERROR AL ANULAR UN MOVIMIENTO", e);
/* 155:    */     }
/* 156:    */     catch (ExcepcionAS2 e)
/* 157:    */     {
/* 158:174 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 159:175 */       LOG.error("ERROR AL ANULAR UN MOVIMIENTO", e);
/* 160:    */     }
/* 161:    */     catch (AS2Exception e)
/* 162:    */     {
/* 163:177 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 164:178 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 165:179 */       LOG.error("ERROR AL ANULAR UN MOVIMIENTO", e);
/* 166:    */     }
/* 167:    */     catch (Exception e)
/* 168:    */     {
/* 169:181 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 170:182 */       LOG.error("ERROR AL ANULAR DATOS", e);
/* 171:    */     }
/* 172:184 */     return "";
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String editar()
/* 176:    */   {
/* 177:189 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 178:190 */     return null;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String guardar()
/* 182:    */   {
/* 183:    */     try
/* 184:    */     {
/* 185:196 */       if (getDetalleMovimientoUnidadManejo().size() > 0)
/* 186:    */       {
/* 187:198 */         this.servicioMovimientoUnidadManejo.guardar(getMovimientoUnidadManejo());
/* 188:199 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 189:200 */         setEditado(false);
/* 190:    */       }
/* 191:    */       else
/* 192:    */       {
/* 193:203 */         addErrorMessage(getLanguageController().getMensaje("msg_error_detalles_vacios"));
/* 194:    */       }
/* 195:    */     }
/* 196:    */     catch (ExcepcionAS2Inventario e)
/* 197:    */     {
/* 198:206 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 199:207 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/* 200:    */     }
/* 201:    */     catch (ExcepcionAS2Financiero e)
/* 202:    */     {
/* 203:209 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 204:210 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/* 205:    */     }
/* 206:    */     catch (ExcepcionAS2 e)
/* 207:    */     {
/* 208:212 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 209:213 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/* 210:    */     }
/* 211:    */     catch (AS2Exception e)
/* 212:    */     {
/* 213:215 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 214:216 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 215:217 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/* 216:    */     }
/* 217:    */     catch (Exception e)
/* 218:    */     {
/* 219:219 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 220:220 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 221:    */     }
/* 222:222 */     return "";
/* 223:    */   }
/* 224:    */   
/* 225:    */   public String eliminar()
/* 226:    */   {
/* 227:227 */     anular();
/* 228:228 */     return "";
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String limpiar()
/* 232:    */   {
/* 233:233 */     crearMovimientoUnidadManejo();
/* 234:234 */     return "";
/* 235:    */   }
/* 236:    */   
/* 237:    */   protected void crearMovimientoUnidadManejo()
/* 238:    */   {
/* 239:238 */     this.movimientoUnidadManejo = new MovimientoUnidadManejo();
/* 240:239 */     this.movimientoUnidadManejo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 241:240 */     this.movimientoUnidadManejo.setNumero("");
/* 242:241 */     Documento documento = null;
/* 243:242 */     if ((getListaDocumentosAjuste() != null) && (!getListaDocumentosAjuste().isEmpty()))
/* 244:    */     {
/* 245:243 */       documento = (Documento)getListaDocumentosAjuste().get(0);
/* 246:244 */       this.movimientoUnidadManejo.setDocumento(documento);
/* 247:245 */       actualizarDocumento();
/* 248:    */     }
/* 249:    */     else
/* 250:    */     {
/* 251:247 */       documento = new Documento();
/* 252:248 */       this.movimientoUnidadManejo.setDocumento(documento);
/* 253:    */     }
/* 254:251 */     this.movimientoUnidadManejo.setEstado(Estado.PROCESADO);
/* 255:252 */     this.movimientoUnidadManejo.setFecha(new Date());
/* 256:253 */     this.movimientoUnidadManejo.setHora(new Date());
/* 257:254 */     this.movimientoUnidadManejo.setIndicadorAjusteUnidadManejo(true);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public DetalleMovimientoUnidadManejo agregarDetalle()
/* 261:    */   {
/* 262:258 */     DetalleMovimientoUnidadManejo dmumt = new DetalleMovimientoUnidadManejo();
/* 263:259 */     dmumt.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 264:260 */     dmumt.setMovimientoUnidadManejo(getMovimientoUnidadManejo());
/* 265:261 */     getMovimientoUnidadManejo().getDetalleMovimientoUnidadManejo().add(dmumt);
/* 266:262 */     return dmumt;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String cargarDatos()
/* 270:    */   {
/* 271:267 */     return "";
/* 272:    */   }
/* 273:    */   
/* 274:    */   public DocumentoBase getDocumentoBase()
/* 275:    */   {
/* 276:271 */     return DocumentoBase.MOVIMIENTO_UNIDAD_MANEJO;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public LazyDataModel<MovimientoUnidadManejo> getListaMovimientoUnidadManejo()
/* 280:    */   {
/* 281:278 */     return this.listaMovimientoUnidadManejo;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setListaMovimientoUnidadManejo(LazyDataModel<MovimientoUnidadManejo> listaMovimientoUnidadManejo)
/* 285:    */   {
/* 286:287 */     this.listaMovimientoUnidadManejo = listaMovimientoUnidadManejo;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public List<Documento> getListaDocumentosAjuste()
/* 290:    */   {
/* 291:295 */     if (this.listaDocumento == null) {
/* 292:    */       try
/* 293:    */       {
/* 294:297 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(getDocumentoBase(), AppUtil.getOrganizacion()
/* 295:298 */           .getIdOrganizacion());
/* 296:    */       }
/* 297:    */       catch (ExcepcionAS2 e)
/* 298:    */       {
/* 299:300 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 300:    */       }
/* 301:    */     }
/* 302:304 */     return this.listaDocumento;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public String actualizarDocumento()
/* 306:    */   {
/* 307:308 */     setSecuenciaEditable(!this.movimientoUnidadManejo.getDocumento().isIndicadorBloqueoSecuencia());
/* 308:309 */     for (DetalleMovimientoUnidadManejo detalleMovimientoUnidadManejo : getDetalleMovimientoUnidadManejo()) {
/* 309:310 */       actualizarOperacion(detalleMovimientoUnidadManejo);
/* 310:    */     }
/* 311:312 */     return "";
/* 312:    */   }
/* 313:    */   
/* 314:    */   private void actualizarOperacion(DetalleMovimientoUnidadManejo dm)
/* 315:    */   {
/* 316:316 */     dm.setOperacion(getMovimientoUnidadManejo().getDocumento().getOperacion());
/* 317:    */   }
/* 318:    */   
/* 319:    */   public List<MotivoAjusteUnidadManejo> autocompletarMotivoAjusteUnidadManejo(String consulta)
/* 320:    */   {
/* 321:320 */     List<MotivoAjusteUnidadManejo> lista = new ArrayList();
/* 322:321 */     HashMap<String, String> filters = new HashMap();
/* 323:322 */     filters.put("nombre", "%" + consulta + "%");
/* 324:323 */     lista = this.servicioMotivoAjusteUnidadManejo.obtenerListaCombo(MotivoAjusteUnidadManejo.class, "nombre", true, filters);
/* 325:324 */     return lista;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public List<SelectItem> getListaTipoAjuste()
/* 329:    */   {
/* 330:331 */     if (this.listaTipoAjuste == null)
/* 331:    */     {
/* 332:332 */       this.listaTipoAjuste = new ArrayList();
/* 333:333 */       for (enumClasificacion tr : enumClasificacion.values()) {
/* 334:334 */         this.listaTipoAjuste.add(new SelectItem(tr, tr.name()));
/* 335:    */       }
/* 336:    */     }
/* 337:337 */     return this.listaTipoAjuste;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public List<Sucursal> getListaSucursal()
/* 341:    */   {
/* 342:344 */     if (this.listaSucursal == null)
/* 343:    */     {
/* 344:346 */       Map<String, String> filtros = new HashMap();
/* 345:347 */       filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 346:348 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filtros);
/* 347:    */     }
/* 348:351 */     return this.listaSucursal;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 352:    */   {
/* 353:355 */     return this.servicioEmpresa.autocompletarClientes(consulta, true);
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void actualizarCliente(SelectEvent event)
/* 357:    */   {
/* 358:359 */     getMovimientoUnidadManejo().setEmpresa((Empresa)event.getObject());
/* 359:360 */     cargarSubempresas();
/* 360:361 */     actualizarEmpresa();
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void cargarSubempresas()
/* 364:    */   {
/* 365:365 */     if (this.movimientoUnidadManejo.getEmpresa() != null) {
/* 366:366 */       this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(this.movimientoUnidadManejo.getEmpresa().getId());
/* 367:    */     }
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void cargarUnidadManejo(UnidadManejo unidadManejo)
/* 371:    */   {
/* 372:371 */     getUnidadManejoBean().setUnidadManejo(unidadManejo);
/* 373:372 */     cargarUnidadManejo();
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void actualizarSucursal()
/* 377:    */   {
/* 378:376 */     for (DetalleMovimientoUnidadManejo detalleMovimientoUnidadManejo : getDetalleMovimientoUnidadManejo())
/* 379:    */     {
/* 380:377 */       detalleMovimientoUnidadManejo.setSucursal(getMovimientoUnidadManejo().getSucursal());
/* 381:378 */       detalleMovimientoUnidadManejo.setEmpresa(null);
/* 382:379 */       detalleMovimientoUnidadManejo.setSubempresa(null);
/* 383:380 */       detalleMovimientoUnidadManejo.setTransportista(null);
/* 384:    */     }
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void actualizarEmpresa()
/* 388:    */   {
/* 389:385 */     for (DetalleMovimientoUnidadManejo detalleMovimientoUnidadManejo : getDetalleMovimientoUnidadManejo())
/* 390:    */     {
/* 391:386 */       detalleMovimientoUnidadManejo.setEmpresa(getMovimientoUnidadManejo().getEmpresa());
/* 392:387 */       detalleMovimientoUnidadManejo.setSubempresa(getMovimientoUnidadManejo().getSubempresa());
/* 393:388 */       detalleMovimientoUnidadManejo.setTransportista(null);
/* 394:389 */       detalleMovimientoUnidadManejo.setSucursal(null);
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void actualizarTransportista()
/* 399:    */   {
/* 400:394 */     for (DetalleMovimientoUnidadManejo detalleMovimientoUnidadManejo : getDetalleMovimientoUnidadManejo())
/* 401:    */     {
/* 402:395 */       detalleMovimientoUnidadManejo.setTransportista(getMovimientoUnidadManejo().getTransportista());
/* 403:396 */       detalleMovimientoUnidadManejo.setEmpresa(null);
/* 404:397 */       detalleMovimientoUnidadManejo.setSubempresa(null);
/* 405:398 */       detalleMovimientoUnidadManejo.setSucursal(null);
/* 406:    */     }
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void cargarUnidadManejo()
/* 410:    */   {
/* 411:    */     try
/* 412:    */     {
/* 413:405 */       UnidadManejo unidadManejo = getUnidadManejoBean().getUnidadManejo();
/* 414:406 */       if (unidadManejo != null)
/* 415:    */       {
/* 416:407 */         DetalleMovimientoUnidadManejo detalleMovimientoUnidadManejo = new DetalleMovimientoUnidadManejo();
/* 417:408 */         detalleMovimientoUnidadManejo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 418:409 */         detalleMovimientoUnidadManejo.setUnidadManejo(unidadManejo);
/* 419:410 */         actualizarOperacion(detalleMovimientoUnidadManejo);
/* 420:411 */         detalleMovimientoUnidadManejo.setCantidad(unidadManejo.getTraCantidad());
/* 421:412 */         detalleMovimientoUnidadManejo.setMovimientoUnidadManejo(this.movimientoUnidadManejo);
/* 422:413 */         detalleMovimientoUnidadManejo.setSucursal(this.movimientoUnidadManejo.getSucursal());
/* 423:414 */         detalleMovimientoUnidadManejo.setEmpresa(this.movimientoUnidadManejo.getEmpresa());
/* 424:415 */         detalleMovimientoUnidadManejo.setSubempresa(this.movimientoUnidadManejo.getSubempresa());
/* 425:416 */         detalleMovimientoUnidadManejo.setTransportista(this.movimientoUnidadManejo.getTransportista());
/* 426:    */         
/* 427:418 */         this.movimientoUnidadManejo.getDetalleMovimientoUnidadManejo().add(detalleMovimientoUnidadManejo);
/* 428:    */       }
/* 429:    */     }
/* 430:    */     catch (Exception e)
/* 431:    */     {
/* 432:421 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 433:422 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 434:    */     }
/* 435:    */     finally
/* 436:    */     {
/* 437:424 */       getUnidadManejoBean().setUnidadManejo(null);
/* 438:    */     }
/* 439:    */   }
/* 440:    */   
/* 441:    */   public List<Transportista> getListaTransportista()
/* 442:    */   {
/* 443:434 */     if (this.listaTransportista == null)
/* 444:    */     {
/* 445:435 */       HashMap<String, String> filters = new HashMap();
/* 446:436 */       filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 447:437 */       filters.put("activo", "true");
/* 448:438 */       filters.put("usuario", OperacionEnum.IS_NOT_NULL.toString());
/* 449:439 */       this.listaTransportista = this.servicioTransportista.obtenerListaCombo("nombre", true, filters);
/* 450:    */     }
/* 451:442 */     return this.listaTransportista;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public MovimientoUnidadManejo getMovimientoUnidadManejo()
/* 455:    */   {
/* 456:449 */     return this.movimientoUnidadManejo;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public void setMovimientoUnidadManejo(MovimientoUnidadManejo ajusteUnidadManejo)
/* 460:    */   {
/* 461:457 */     this.movimientoUnidadManejo = ajusteUnidadManejo;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public DataTable getDtListaAjuste()
/* 465:    */   {
/* 466:464 */     return this.dtListaAjuste;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void setDtListaAjuste(DataTable dtListaAjuste)
/* 470:    */   {
/* 471:472 */     this.dtListaAjuste = dtListaAjuste;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public DataTable getDtDetalleAjuste()
/* 475:    */   {
/* 476:479 */     return this.dtDetalleAjuste;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void setDtDetalleAjuste(DataTable dtDetalleAjuste)
/* 480:    */   {
/* 481:487 */     this.dtDetalleAjuste = dtDetalleAjuste;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public List<Subempresa> getListaSubempresa()
/* 485:    */   {
/* 486:494 */     if (this.listaSubempresa == null) {
/* 487:495 */       this.listaSubempresa = new ArrayList();
/* 488:    */     }
/* 489:497 */     return this.listaSubempresa;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 493:    */   {
/* 494:505 */     this.listaSubempresa = listaSubempresa;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public List<DetalleMovimientoUnidadManejo> getDetalleMovimientoUnidadManejo()
/* 498:    */   {
/* 499:515 */     List<DetalleMovimientoUnidadManejo> detalle = new ArrayList();
/* 500:516 */     for (DetalleMovimientoUnidadManejo dm : getMovimientoUnidadManejo().getDetalleMovimientoUnidadManejo()) {
/* 501:517 */       if (!dm.isEliminado()) {
/* 502:518 */         detalle.add(dm);
/* 503:    */       }
/* 504:    */     }
/* 505:522 */     return detalle;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public String eliminarDetalle()
/* 509:    */   {
/* 510:526 */     DetalleMovimientoUnidadManejo d = (DetalleMovimientoUnidadManejo)this.dtDetalleAjuste.getRowData();
/* 511:527 */     d.setEliminado(true);
/* 512:528 */     return "";
/* 513:    */   }
/* 514:    */   
/* 515:    */   public enumClasificacion getClasificacion()
/* 516:    */   {
/* 517:535 */     return this.clasificacion;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void setClasificacion(enumClasificacion clasificacion)
/* 521:    */   {
/* 522:539 */     this.clasificacion = clasificacion;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public boolean isIndicadorSucursal()
/* 526:    */   {
/* 527:546 */     return this.indicadorSucursal;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void setIndicadorSucursal(boolean indicadorSucursal)
/* 531:    */   {
/* 532:554 */     this.indicadorSucursal = indicadorSucursal;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public boolean isIndicadorCliente()
/* 536:    */   {
/* 537:561 */     return this.indicadorCliente;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setIndicadorCliente(boolean indicadorCliente)
/* 541:    */   {
/* 542:569 */     this.indicadorCliente = indicadorCliente;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public boolean isIndicadorTransportista()
/* 546:    */   {
/* 547:576 */     return this.indicadorTransportista;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void setIndicadorTransportista(boolean indicadorTransportista)
/* 551:    */   {
/* 552:584 */     this.indicadorTransportista = indicadorTransportista;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public UnidadManejoBean getUnidadManejoBean()
/* 556:    */   {
/* 557:589 */     return this.unidadManejoBean;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public void setUnidadManejoBean(UnidadManejoBean unidadManejoBean)
/* 561:    */   {
/* 562:593 */     this.unidadManejoBean = unidadManejoBean;
/* 563:    */   }
/* 564:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.AjusteUnidadManejoBean
 * JD-Core Version:    0.7.0.1
 */