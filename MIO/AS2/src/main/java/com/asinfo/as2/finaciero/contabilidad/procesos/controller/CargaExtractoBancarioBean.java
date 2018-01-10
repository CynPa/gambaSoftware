/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Asiento;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.CuentaContable;
/*   9:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  10:    */ import com.asinfo.as2.entities.DetalleExtractoBancario;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.ExtractoBancario;
/*  13:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*  18:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  19:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  20:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  21:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  23:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioExtractoBancario;
/*  25:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  26:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  27:    */ import com.asinfo.as2.util.AppUtil;
/*  28:    */ import com.asinfo.as2.utils.JsfUtil;
/*  29:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  30:    */ import java.io.BufferedInputStream;
/*  31:    */ import java.io.InputStream;
/*  32:    */ import java.util.ArrayList;
/*  33:    */ import java.util.Calendar;
/*  34:    */ import java.util.Collection;
/*  35:    */ import java.util.List;
/*  36:    */ import java.util.Map;
/*  37:    */ import javax.annotation.PostConstruct;
/*  38:    */ import javax.ejb.EJB;
/*  39:    */ import javax.faces.bean.ManagedBean;
/*  40:    */ import javax.faces.bean.ManagedProperty;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import javax.faces.context.FacesContext;
/*  43:    */ import javax.faces.context.PartialViewContext;
/*  44:    */ import org.apache.log4j.Logger;
/*  45:    */ import org.primefaces.component.datatable.DataTable;
/*  46:    */ import org.primefaces.context.RequestContext;
/*  47:    */ import org.primefaces.event.FileUploadEvent;
/*  48:    */ import org.primefaces.event.SelectEvent;
/*  49:    */ import org.primefaces.model.LazyDataModel;
/*  50:    */ import org.primefaces.model.SortOrder;
/*  51:    */ import org.primefaces.model.UploadedFile;
/*  52:    */ 
/*  53:    */ @ManagedBean
/*  54:    */ @ViewScoped
/*  55:    */ public class CargaExtractoBancarioBean
/*  56:    */   extends PageControllerAS2
/*  57:    */ {
/*  58:    */   private static final long serialVersionUID = 1L;
/*  59:    */   @EJB
/*  60:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  61:    */   @EJB
/*  62:    */   private ServicioDocumento servicioDocumento;
/*  63:    */   @EJB
/*  64:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  65:    */   @EJB
/*  66:    */   private ServicioCuentaContable servicioCuentaContable;
/*  67:    */   @EJB
/*  68:    */   private ServicioAsiento servicioAsiento;
/*  69:    */   @EJB
/*  70:    */   private ServicioExtractoBancario servicioExtractoBancario;
/*  71:    */   private InterfazContableProceso interfazContableProceso;
/*  72:    */   private List<DetalleExtractoBancario> listaDetalleExtracto;
/*  73:    */   private DetalleExtractoBancario lineaDetalleExtractoSelected;
/*  74:    */   private LazyDataModel<InterfazContableProceso> listaInterfazContableProceso;
/*  75:    */   private List<Documento> listaDocumento;
/*  76:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  77:    */   private DataTable dtDetalleAsiento;
/*  78:    */   private DataTable dtInterfazContableProceso;
/*  79:107 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  80:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  81:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  82:    */   
/*  83:    */   @PostConstruct
/*  84:    */   public void init()
/*  85:    */   {
/*  86:116 */     this.listaInterfazContableProceso = new LazyDataModel()
/*  87:    */     {
/*  88:    */       private static final long serialVersionUID = 1L;
/*  89:    */       
/*  90:    */       public List<InterfazContableProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  91:    */       {
/*  92:123 */         List<InterfazContableProceso> lista = new ArrayList();
/*  93:    */         
/*  94:125 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  95:126 */         filters.put("documentoBase", String.valueOf(DocumentoBase.EXTRACTO_BANCARIO));
/*  96:    */         
/*  97:128 */         lista = CargaExtractoBancarioBean.this.servicioInterfazContableProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  98:129 */         CargaExtractoBancarioBean.this.listaInterfazContableProceso.setRowCount(CargaExtractoBancarioBean.this.servicioInterfazContableProceso.contarPorCriterio(filters));
/*  99:130 */         return lista;
/* 100:    */       }
/* 101:    */     };
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String editar()
/* 105:    */   {
/* 106:143 */     if ((getInterfazContableProceso() != null) && (getInterfazContableProceso().getId() != 0))
/* 107:    */     {
/* 108:145 */       if (getInterfazContableProceso().getEstado() == Estado.ELABORADO)
/* 109:    */       {
/* 110:146 */         setInterfazContableProceso(this.servicioExtractoBancario.cargarDetalle(getInterfazContableProceso()));
/* 111:147 */         setEditado(true);
/* 112:    */       }
/* 113:    */       else
/* 114:    */       {
/* 115:149 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 116:    */       }
/* 117:    */     }
/* 118:    */     else {
/* 119:152 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 120:    */     }
/* 121:154 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String guardar()
/* 125:    */   {
/* 126:    */     try
/* 127:    */     {
/* 128:166 */       if (this.interfazContableProceso.getListaExtractoBancario().isEmpty())
/* 129:    */       {
/* 130:167 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/* 131:    */       }
/* 132:    */       else
/* 133:    */       {
/* 134:171 */         this.servicioExtractoBancario.guardar(this.interfazContableProceso);
/* 135:    */         
/* 136:173 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 137:    */         
/* 138:175 */         limpiar();
/* 139:    */         
/* 140:177 */         setEditado(false);
/* 141:    */       }
/* 142:    */     }
/* 143:    */     catch (AS2Exception ex)
/* 144:    */     {
/* 145:181 */       JsfUtil.addErrorMessage(ex, "");
/* 146:    */     }
/* 147:    */     catch (Exception ex)
/* 148:    */     {
/* 149:183 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 150:184 */       LOG.error("ERROR AL GUARDAR DATOS", ex);
/* 151:    */     }
/* 152:187 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String eliminar()
/* 156:    */   {
/* 157:    */     try
/* 158:    */     {
/* 159:200 */       this.servicioInterfazContableProceso.anular(this.interfazContableProceso);
/* 160:201 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 161:202 */       cargarDatos();
/* 162:    */     }
/* 163:    */     catch (ExcepcionAS2Ventas e)
/* 164:    */     {
/* 165:205 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()), e.getMessage());
/* 166:    */     }
/* 167:    */     catch (ExcepcionAS2Financiero e)
/* 168:    */     {
/* 169:208 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()), e.getMessage());
/* 170:    */     }
/* 171:    */     catch (ExcepcionAS2 e)
/* 172:    */     {
/* 173:211 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()), e.getMessage());
/* 174:212 */       LOG.info(e);
/* 175:    */     }
/* 176:    */     catch (Exception e)
/* 177:    */     {
/* 178:214 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 179:215 */       LOG.error(e);
/* 180:    */     }
/* 181:218 */     return "";
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void contabilizarListener(InterfazContableProceso interfaz)
/* 185:    */   {
/* 186:    */     try
/* 187:    */     {
/* 188:223 */       interfaz = this.servicioExtractoBancario.cargarDetalle(interfaz);
/* 189:225 */       if (interfaz.getEstado() != Estado.ELABORADO)
/* 190:    */       {
/* 191:226 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 192:    */       }
/* 193:    */       else
/* 194:    */       {
/* 195:228 */         this.servicioExtractoBancario.contabilizar(interfaz);
/* 196:229 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 197:    */       }
/* 198:    */     }
/* 199:    */     catch (ExcepcionAS2Financiero e)
/* 200:    */     {
/* 201:232 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 202:233 */       LOG.error("ERROR AL CONTABILIZAR EXTRACTO", e);
/* 203:    */     }
/* 204:    */     catch (AS2Exception e)
/* 205:    */     {
/* 206:235 */       this.exContabilizacion = e;
/* 207:236 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 208:237 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 209:238 */       LOG.error("ERROR AL CONTABILIZAR EXTRACTO BANCARIO", e);
/* 210:    */     }
/* 211:    */     catch (ExcepcionAS2 e)
/* 212:    */     {
/* 213:240 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 214:241 */       LOG.error("ERROR AL CONTABILIZAR EXTRACTO", e);
/* 215:    */     }
/* 216:    */     catch (Exception e)
/* 217:    */     {
/* 218:243 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 219:244 */       LOG.error("ERROR AL CONTABILIZAR EXTRACTO", e);
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String limpiar()
/* 224:    */   {
/* 225:256 */     this.listaDetalleExtracto = null;
/* 226:257 */     this.lineaDetalleExtractoSelected = null;
/* 227:    */     
/* 228:259 */     this.interfazContableProceso = new InterfazContableProceso();
/* 229:260 */     this.interfazContableProceso.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 230:261 */     this.interfazContableProceso.setSucursal(AppUtil.getSucursal());
/* 231:262 */     this.interfazContableProceso.setFechaDesde(Calendar.getInstance().getTime());
/* 232:263 */     this.interfazContableProceso.setFechaHasta(Calendar.getInstance().getTime());
/* 233:264 */     this.interfazContableProceso.setEstado(Estado.ELABORADO);
/* 234:265 */     this.interfazContableProceso.setDocumentoBase(DocumentoBase.EXTRACTO_BANCARIO);
/* 235:267 */     if (!getListaDocumento().isEmpty()) {
/* 236:268 */       this.interfazContableProceso.setDocumento((Documento)getListaDocumento().get(0));
/* 237:    */     }
/* 238:271 */     if (!getListaCuentaBancariaOrganizacion().isEmpty()) {
/* 239:272 */       this.interfazContableProceso.setCuentaBancariaOrganizacion((CuentaBancariaOrganizacion)getListaCuentaBancariaOrganizacion().get(0));
/* 240:    */     }
/* 241:275 */     return "";
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void cargarCuentaContable(SelectEvent event)
/* 245:    */   {
/* 246:279 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/* 247:280 */     this.lineaDetalleExtractoSelected.setCuentaContable(cuentaContable);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void buscarCuentaContable(DetalleAsiento detalleAsiento)
/* 251:    */   {
/* 252:    */     try
/* 253:    */     {
/* 254:288 */       String codigoCuentaContable = detalleAsiento.getCuentaContable().getCodigo();
/* 255:289 */       if (codigoCuentaContable != "")
/* 256:    */       {
/* 257:290 */         CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion()
/* 258:291 */           .getIdOrganizacion());
/* 259:292 */         detalleAsiento.setCuentaContable(cuentaContable);
/* 260:    */       }
/* 261:    */     }
/* 262:    */     catch (ExcepcionAS2Financiero e)
/* 263:    */     {
/* 264:296 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion()) + this.lineaDetalleExtractoSelected.getCuentaContable().getCodigo();
/* 265:297 */       addInfoMessage(strMensaje);
/* 266:    */     }
/* 267:    */     catch (Exception e)
/* 268:    */     {
/* 269:300 */       LOG.error("ERROR AL BUSCAR CUENTA CONTABLE");
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   public String cargarExtractoBancarioListener(FileUploadEvent event)
/* 274:    */   {
/* 275:    */     try
/* 276:    */     {
/* 277:307 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 278:308 */       this.servicioExtractoBancario.leerExtractoBancario(this.interfazContableProceso, input, 1, this.interfazContableProceso.getCuentaBancariaOrganizacion());
/* 279:    */       
/* 280:310 */       this.listaDetalleExtracto = null;
/* 281:311 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 282:    */     }
/* 283:    */     catch (ExcepcionAS2 e)
/* 284:    */     {
/* 285:314 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 286:    */     }
/* 287:    */     catch (Exception e)
/* 288:    */     {
/* 289:316 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 290:317 */       e.printStackTrace();
/* 291:    */     }
/* 292:319 */     return null;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public String cargarDatos()
/* 296:    */   {
/* 297:329 */     return "";
/* 298:    */   }
/* 299:    */   
/* 300:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 301:    */   {
/* 302:338 */     List<DetalleAsiento> lista = new ArrayList();
/* 303:339 */     if (this.interfazContableProceso.getAsiento() != null) {
/* 304:340 */       for (DetalleAsiento da : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 305:342 */         if (!da.isEliminado()) {
/* 306:343 */           lista.add(da);
/* 307:    */         }
/* 308:    */       }
/* 309:    */     }
/* 310:347 */     return lista;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public List<Documento> getListaDocumento()
/* 314:    */   {
/* 315:356 */     if (this.listaDocumento == null) {
/* 316:    */       try
/* 317:    */       {
/* 318:358 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.EXTRACTO_BANCARIO, AppUtil.getOrganizacion()
/* 319:359 */           .getId());
/* 320:    */       }
/* 321:    */       catch (ExcepcionAS2 e)
/* 322:    */       {
/* 323:361 */         this.listaDocumento = new ArrayList();
/* 324:362 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 325:    */       }
/* 326:    */     }
/* 327:365 */     return this.listaDocumento;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 331:    */   {
/* 332:375 */     this.listaDocumento = listaDocumento;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public InterfazContableProceso getInterfazContableProceso()
/* 336:    */   {
/* 337:379 */     return this.interfazContableProceso;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 341:    */   {
/* 342:383 */     this.interfazContableProceso = interfazContableProceso;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public LazyDataModel<InterfazContableProceso> getListaInterfazContableProceso()
/* 346:    */   {
/* 347:387 */     return this.listaInterfazContableProceso;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setListaInterfazContableProceso(LazyDataModel<InterfazContableProceso> listaInterfazContableProceso)
/* 351:    */   {
/* 352:391 */     this.listaInterfazContableProceso = listaInterfazContableProceso;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public DataTable getDtDetalleAsiento()
/* 356:    */   {
/* 357:395 */     return this.dtDetalleAsiento;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setDtDetalleAsiento(DataTable dtDetalleAsiento)
/* 361:    */   {
/* 362:399 */     this.dtDetalleAsiento = dtDetalleAsiento;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public DataTable getDtInterfazContableProceso()
/* 366:    */   {
/* 367:403 */     return this.dtInterfazContableProceso;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setDtInterfazContableProceso(DataTable dtInterfazContableProceso)
/* 371:    */   {
/* 372:407 */     this.dtInterfazContableProceso = dtInterfazContableProceso;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public AS2Exception getExContabilizacion()
/* 376:    */   {
/* 377:416 */     return this.exContabilizacion;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 381:    */   {
/* 382:426 */     this.exContabilizacion = exContabilizacion;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 386:    */   {
/* 387:431 */     if (this.listaCuentaBancariaOrganizacion == null)
/* 388:    */     {
/* 389:432 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 390:433 */       filters.put("tipoCuentaBancariaOrganizacion", TipoCuentaBancariaOrganizacion.BANCO.toString());
/* 391:434 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("", false, filters);
/* 392:    */     }
/* 393:437 */     return this.listaCuentaBancariaOrganizacion;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 397:    */   {
/* 398:441 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 402:    */   {
/* 403:445 */     return this.listaCuentaContableBean;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 407:    */   {
/* 408:449 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public List<DetalleExtractoBancario> getListaDetalleExtracto()
/* 412:    */   {
/* 413:453 */     if (this.listaDetalleExtracto == null)
/* 414:    */     {
/* 415:454 */       this.listaDetalleExtracto = new ArrayList();
/* 416:455 */       for (ExtractoBancario extracto : getInterfazContableProceso().getListaExtractoBancario()) {
/* 417:456 */         if (!extracto.isEliminado()) {
/* 418:457 */           for (DetalleExtractoBancario detalleExtracto : extracto.getListaDetalleExtractoBancario()) {
/* 419:458 */             if (!detalleExtracto.isEliminado()) {
/* 420:459 */               this.listaDetalleExtracto.add(detalleExtracto);
/* 421:    */             }
/* 422:    */           }
/* 423:    */         }
/* 424:    */       }
/* 425:    */     }
/* 426:465 */     return this.listaDetalleExtracto;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public void setListaDetalleExtracto(List<DetalleExtractoBancario> listaDetalleExtracto)
/* 430:    */   {
/* 431:469 */     this.listaDetalleExtracto = listaDetalleExtracto;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public DetalleExtractoBancario getLineaDetalleExtractoSelected()
/* 435:    */   {
/* 436:473 */     return this.lineaDetalleExtractoSelected;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void setLineaDetalleExtractoSelected(DetalleExtractoBancario lineaDetalleExtractoSelected)
/* 440:    */   {
/* 441:477 */     this.lineaDetalleExtractoSelected = lineaDetalleExtractoSelected;
/* 442:    */   }
/* 443:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.CargaExtractoBancarioBean
 * JD-Core Version:    0.7.0.1
 */