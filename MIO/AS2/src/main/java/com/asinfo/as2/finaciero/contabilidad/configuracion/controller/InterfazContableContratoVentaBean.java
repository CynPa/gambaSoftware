/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.dao.DetalleValoresContratoVentaDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   9:    */ import com.asinfo.as2.entities.DetalleValoresContratoVenta;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  19:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  22:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  23:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDevengar;
/*  24:    */ import java.math.BigDecimal;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Collection;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.faces.bean.ManagedBean;
/*  33:    */ import javax.faces.bean.ViewScoped;
/*  34:    */ import javax.faces.context.FacesContext;
/*  35:    */ import javax.faces.context.PartialViewContext;
/*  36:    */ import javax.faces.model.SelectItem;
/*  37:    */ import org.apache.log4j.Logger;
/*  38:    */ import org.primefaces.component.datatable.DataTable;
/*  39:    */ import org.primefaces.context.RequestContext;
/*  40:    */ import org.primefaces.model.LazyDataModel;
/*  41:    */ import org.primefaces.model.SortOrder;
/*  42:    */ 
/*  43:    */ @ManagedBean
/*  44:    */ @ViewScoped
/*  45:    */ public class InterfazContableContratoVentaBean
/*  46:    */   extends PageControllerAS2
/*  47:    */ {
/*  48:    */   private static final long serialVersionUID = -8914881290932226380L;
/*  49:    */   @EJB
/*  50:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  51:    */   @EJB
/*  52:    */   private ServicioDocumento servicioDocumento;
/*  53:    */   @EJB
/*  54:    */   private ServicioDevengar servicioDevengar;
/*  55:    */   @EJB
/*  56:    */   private DetalleValoresContratoVentaDao detalleValoresContratoVentaDao;
/*  57:    */   private BigDecimal debe;
/*  58:    */   private BigDecimal haber;
/*  59:    */   private InterfazContableProceso interfazContableProceso;
/*  60:    */   private int anioHasta;
/*  61:    */   private int mesHasta;
/*  62:    */   private List<SelectItem> listaMes;
/*  63:    */   private LazyDataModel<InterfazContableProceso> listaInterfazContableProceso;
/*  64:    */   private List<Documento> listaDocumento;
/*  65:    */   private List<SelectItem> listaDocumentoBase;
/*  66:    */   private DataTable dtDetalleAsiento;
/*  67:    */   private DataTable dtDetalleValoresContratoVenta;
/*  68:    */   private DataTable dtInterfazContableProceso;
/*  69: 99 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  70:    */   
/*  71:    */   @PostConstruct
/*  72:    */   public void init()
/*  73:    */   {
/*  74:103 */     this.mesHasta = FuncionesUtiles.getMes(new Date());
/*  75:104 */     this.anioHasta = FuncionesUtiles.obtenerAnioActual();
/*  76:    */     
/*  77:    */ 
/*  78:107 */     this.listaInterfazContableProceso = new LazyDataModel()
/*  79:    */     {
/*  80:    */       private static final long serialVersionUID = 763093382591716471L;
/*  81:    */       
/*  82:    */       public List<InterfazContableProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  83:    */       {
/*  84:119 */         List<InterfazContableProceso> lista = new ArrayList();
/*  85:    */         
/*  86:121 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  87:122 */         filters.put("documentoBase", String.valueOf(DocumentoBase.CONTRATO_VENTA));
/*  88:    */         
/*  89:124 */         lista = InterfazContableContratoVentaBean.this.servicioInterfazContableProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  90:125 */         InterfazContableContratoVentaBean.this.listaInterfazContableProceso.setRowCount(InterfazContableContratoVentaBean.this.servicioInterfazContableProceso.contarPorCriterio(filters));
/*  91:126 */         return lista;
/*  92:    */       }
/*  93:    */     };
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String crear()
/*  97:    */   {
/*  98:138 */     limpiar();
/*  99:139 */     setEditado(true);
/* 100:    */     
/* 101:141 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String editar()
/* 105:    */   {
/* 106:151 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 107:152 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String guardar()
/* 111:    */   {
/* 112:    */     try
/* 113:    */     {
/* 114:163 */       this.interfazContableProceso.setFechaHasta(FuncionesUtiles.getFechaFinMes(getAnioHasta(), getMesHasta()));
/* 115:164 */       this.interfazContableProceso.setEstado(Estado.ELABORADO);
/* 116:165 */       this.servicioInterfazContableProceso.guardar(this.interfazContableProceso);
/* 117:    */       
/* 118:167 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 119:    */       
/* 120:169 */       cargarDatos();
/* 121:    */     }
/* 122:    */     catch (ExcepcionAS2Financiero e)
/* 123:    */     {
/* 124:172 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 125:    */     }
/* 126:    */     catch (ExcepcionAS2 e)
/* 127:    */     {
/* 128:175 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 129:    */     }
/* 130:178 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String eliminar()
/* 134:    */   {
/* 135:    */     try
/* 136:    */     {
/* 137:190 */       this.servicioInterfazContableProceso.anular(this.interfazContableProceso);
/* 138:191 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 139:192 */       cargarDatos();
/* 140:    */     }
/* 141:    */     catch (ExcepcionAS2Ventas e)
/* 142:    */     {
/* 143:195 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 144:    */     }
/* 145:    */     catch (ExcepcionAS2Financiero e)
/* 146:    */     {
/* 147:198 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 148:    */     }
/* 149:    */     catch (ExcepcionAS2 e)
/* 150:    */     {
/* 151:201 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 152:202 */       LOG.info(e);
/* 153:    */     }
/* 154:    */     catch (Exception e)
/* 155:    */     {
/* 156:204 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 157:205 */       LOG.error(e);
/* 158:    */     }
/* 159:208 */     return "";
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String limpiar()
/* 163:    */   {
/* 164:218 */     setEditado(false);
/* 165:219 */     this.interfazContableProceso = new InterfazContableProceso();
/* 166:220 */     this.interfazContableProceso.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 167:221 */     this.interfazContableProceso.setSucursal(AppUtil.getSucursal());
/* 168:222 */     this.interfazContableProceso.setFechaDesde(FuncionesUtiles.getFechaFinMes(getAnioHasta(), getMesHasta()));
/* 169:223 */     this.interfazContableProceso.setFechaHasta(FuncionesUtiles.getFechaFinMes(getAnioHasta(), getMesHasta()));
/* 170:224 */     this.interfazContableProceso.setEstado(Estado.ELABORADO);
/* 171:225 */     this.interfazContableProceso.setDocumentoBase(DocumentoBase.CONTRATO_VENTA);
/* 172:226 */     this.interfazContableProceso.setAsiento(new Asiento());
/* 173:227 */     this.interfazContableProceso.setFiltroDocumentoBase(DocumentoBase.CONTRATO_VENTA);
/* 174:228 */     cargarListaDocumento();
/* 175:229 */     return "";
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String preliminarInterfazProceso()
/* 179:    */   {
/* 180:    */     try
/* 181:    */     {
/* 182:239 */       if (isIndicadorAutoimpresor()) {
/* 183:240 */         this.interfazContableProceso.setFechaHasta(this.interfazContableProceso.getFechaDesde());
/* 184:    */       }
/* 185:242 */       if (getInterfazContableProceso().getFiltroDocumento() == null) {
/* 186:243 */         this.interfazContableProceso.setFiltroDocumento(new Documento());
/* 187:    */       }
/* 188:246 */       this.interfazContableProceso.setAsiento(this.servicioInterfazContableProceso.generarAsiento(this.interfazContableProceso));
/* 189:247 */       calcular();
/* 190:    */     }
/* 191:    */     catch (ExcepcionAS2Financiero e)
/* 192:    */     {
/* 193:250 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 194:251 */       LOG.info("ERROR AL GENERAR-INTERFAZ VENTAS:", e);
/* 195:252 */       e.printStackTrace();
/* 196:    */     }
/* 197:    */     catch (ExcepcionAS2 e)
/* 198:    */     {
/* 199:254 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 200:255 */       LOG.info("ERROR AL GENERAR-INTERFAZ VENTAS:", e);
/* 201:256 */       e.printStackTrace();
/* 202:    */     }
/* 203:    */     catch (AS2Exception e)
/* 204:    */     {
/* 205:258 */       e.printStackTrace();
/* 206:259 */       this.exContabilizacion = e;
/* 207:260 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 208:261 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 209:    */     }
/* 210:    */     catch (Exception e)
/* 211:    */     {
/* 212:263 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 213:264 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 214:    */     }
/* 215:266 */     return "";
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String cargarDatos()
/* 219:    */   {
/* 220:276 */     setEditado(false);
/* 221:277 */     limpiar();
/* 222:278 */     return null;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 226:    */   {
/* 227:287 */     List<DetalleAsiento> lista = new ArrayList();
/* 228:288 */     if (this.interfazContableProceso.getAsiento() != null) {
/* 229:289 */       for (DetalleAsiento da : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 230:291 */         if (!da.isEliminado()) {
/* 231:292 */           lista.add(da);
/* 232:    */         }
/* 233:    */       }
/* 234:    */     }
/* 235:296 */     return lista;
/* 236:    */   }
/* 237:    */   
/* 238:    */   private void calcular()
/* 239:    */   {
/* 240:303 */     this.debe = BigDecimal.ZERO;
/* 241:304 */     this.haber = BigDecimal.ZERO;
/* 242:306 */     for (DetalleAsiento d : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 243:307 */       if (!d.isEliminado())
/* 244:    */       {
/* 245:308 */         this.haber = this.haber.add(d.getHaber());
/* 246:309 */         this.debe = this.debe.add(d.getDebe());
/* 247:    */       }
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void cargarListaDocumento()
/* 252:    */   {
/* 253:    */     try
/* 254:    */     {
/* 255:316 */       this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(getInterfazContableProceso().getFiltroDocumentoBase());
/* 256:    */     }
/* 257:    */     catch (ExcepcionAS2 e)
/* 258:    */     {
/* 259:318 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   public BigDecimal getDebe()
/* 264:    */   {
/* 265:333 */     if (this.debe == null) {
/* 266:334 */       calcular();
/* 267:    */     }
/* 268:336 */     return this.debe;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setDebe(BigDecimal debe)
/* 272:    */   {
/* 273:340 */     this.debe = debe;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public BigDecimal getHaber()
/* 277:    */   {
/* 278:344 */     if (this.haber == null) {
/* 279:345 */       calcular();
/* 280:    */     }
/* 281:347 */     return this.haber;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setHaber(BigDecimal haber)
/* 285:    */   {
/* 286:351 */     this.haber = haber;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public List<Documento> getListaDocumento()
/* 290:    */   {
/* 291:360 */     if (this.listaDocumento == null) {
/* 292:361 */       this.listaDocumento = new ArrayList();
/* 293:    */     }
/* 294:363 */     return this.listaDocumento;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 298:    */   {
/* 299:373 */     this.listaDocumento = listaDocumento;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public List<SelectItem> getListaDocumentoBase()
/* 303:    */   {
/* 304:382 */     if (this.listaDocumentoBase == null)
/* 305:    */     {
/* 306:383 */       this.listaDocumentoBase = new ArrayList();
/* 307:384 */       SelectItem item1 = new SelectItem(DocumentoBase.CONTRATO_VENTA, DocumentoBase.CONTRATO_VENTA.getNombre());
/* 308:385 */       this.listaDocumentoBase.add(item1);
/* 309:    */     }
/* 310:387 */     return this.listaDocumentoBase;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setListaDocumentoBase(List<SelectItem> listaDocumentoBase)
/* 314:    */   {
/* 315:397 */     this.listaDocumentoBase = listaDocumentoBase;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public InterfazContableProceso getInterfazContableProceso()
/* 319:    */   {
/* 320:401 */     return this.interfazContableProceso;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 324:    */   {
/* 325:405 */     this.interfazContableProceso = interfazContableProceso;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public LazyDataModel<InterfazContableProceso> getListaInterfazContableProceso()
/* 329:    */   {
/* 330:409 */     return this.listaInterfazContableProceso;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setListaInterfazContableProceso(LazyDataModel<InterfazContableProceso> listaInterfazContableProceso)
/* 334:    */   {
/* 335:413 */     this.listaInterfazContableProceso = listaInterfazContableProceso;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public DataTable getDtDetalleAsiento()
/* 339:    */   {
/* 340:417 */     return this.dtDetalleAsiento;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void setDtDetalleAsiento(DataTable dtDetalleAsiento)
/* 344:    */   {
/* 345:421 */     this.dtDetalleAsiento = dtDetalleAsiento;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public DataTable getDtInterfazContableProceso()
/* 349:    */   {
/* 350:425 */     return this.dtInterfazContableProceso;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void setDtInterfazContableProceso(DataTable dtInterfazContableProceso)
/* 354:    */   {
/* 355:429 */     this.dtInterfazContableProceso = dtInterfazContableProceso;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public AS2Exception getExContabilizacion()
/* 359:    */   {
/* 360:438 */     return this.exContabilizacion;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 364:    */   {
/* 365:448 */     this.exContabilizacion = exContabilizacion;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public int getAnioHasta()
/* 369:    */   {
/* 370:452 */     return this.anioHasta;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void setAnioHasta(int anioHasta)
/* 374:    */   {
/* 375:456 */     this.anioHasta = anioHasta;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public int getMesHasta()
/* 379:    */   {
/* 380:460 */     return this.mesHasta;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setMesHasta(int mesHasta)
/* 384:    */   {
/* 385:464 */     this.mesHasta = mesHasta;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public List<SelectItem> getListaMes()
/* 389:    */   {
/* 390:468 */     if (this.listaMes == null)
/* 391:    */     {
/* 392:469 */       this.listaMes = new ArrayList();
/* 393:470 */       for (Mes t : Mes.values())
/* 394:    */       {
/* 395:471 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 396:472 */         this.listaMes.add(item);
/* 397:    */       }
/* 398:    */     }
/* 399:475 */     return this.listaMes;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void devengar()
/* 403:    */   {
/* 404:482 */     InterfazContableProceso interfazContableProceso = (InterfazContableProceso)this.dtInterfazContableProceso.getRowData();
/* 405:    */     try
/* 406:    */     {
/* 407:484 */       this.servicioDevengar.devengar(interfazContableProceso.getFechaHasta(), interfazContableProceso, AppUtil.getOrganizacion());
/* 408:    */     }
/* 409:    */     catch (ExcepcionAS2Financiero e)
/* 410:    */     {
/* 411:486 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 412:    */     }
/* 413:    */     catch (ExcepcionAS2 e)
/* 414:    */     {
/* 415:488 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 416:    */     }
/* 417:    */     catch (AS2Exception e)
/* 418:    */     {
/* 419:490 */       e.printStackTrace();
/* 420:491 */       this.exContabilizacion = e;
/* 421:492 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 422:493 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 423:    */     }
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void mostrarDevengado() {}
/* 427:    */   
/* 428:    */   public List<DetalleValoresContratoVenta> getListaDetalleValoresContratoVentaADevengar()
/* 429:    */   {
/* 430:509 */     List<DetalleValoresContratoVenta> lista = this.detalleValoresContratoVentaDao.listaDetalleValoresContratoVenta(FuncionesUtiles.getFechaFinMes(getAnioHasta(), getMesHasta()), AppUtil.getOrganizacion());
/* 431:510 */     return lista;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public DataTable getDtDetalleValoresContratoVenta()
/* 435:    */   {
/* 436:514 */     return this.dtDetalleValoresContratoVenta;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void setDtDetalleValoresContratoVenta(DataTable dtDetalleValoresContratoVenta)
/* 440:    */   {
/* 441:519 */     this.dtDetalleValoresContratoVenta = dtDetalleValoresContratoVenta;
/* 442:    */   }
/* 443:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.InterfazContableContratoVentaBean
 * JD-Core Version:    0.7.0.1
 */