/*   1:    */ package com.asinfo.as2.polizas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   6:    */ import com.asinfo.as2.entities.Departamento;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.polizas.Afianzado;
/*   9:    */ import com.asinfo.as2.entities.polizas.Asegurado;
/*  10:    */ import com.asinfo.as2.entities.polizas.Aseguradora;
/*  11:    */ import com.asinfo.as2.entities.polizas.Broker;
/*  12:    */ import com.asinfo.as2.entities.polizas.ContratoPoliza;
/*  13:    */ import com.asinfo.as2.entities.polizas.Poliza;
/*  14:    */ import com.asinfo.as2.entities.polizas.TipoPoliza;
/*  15:    */ import com.asinfo.as2.enumeraciones.polizas.EstadoPoliza;
/*  16:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioAfianzado;
/*  17:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioAsegurado;
/*  18:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioAseguradora;
/*  19:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioBroker;
/*  20:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioContratoPoliza;
/*  21:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioTipoPoliza;
/*  22:    */ import com.asinfo.as2.polizas.procesos.servicio.ServicioPoliza;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.util.RutaArchivo;
/*  25:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.HashMap;
/*  29:    */ import java.util.List;
/*  30:    */ import java.util.Map;
/*  31:    */ import javax.annotation.PostConstruct;
/*  32:    */ import javax.ejb.EJB;
/*  33:    */ import javax.faces.bean.ManagedBean;
/*  34:    */ import javax.faces.bean.ViewScoped;
/*  35:    */ import javax.faces.model.SelectItem;
/*  36:    */ import org.apache.log4j.Logger;
/*  37:    */ import org.primefaces.component.datatable.DataTable;
/*  38:    */ import org.primefaces.event.FileUploadEvent;
/*  39:    */ import org.primefaces.model.LazyDataModel;
/*  40:    */ import org.primefaces.model.SortOrder;
/*  41:    */ 
/*  42:    */ @ManagedBean
/*  43:    */ @ViewScoped
/*  44:    */ public class PolizaBean
/*  45:    */   extends PageControllerAS2
/*  46:    */ {
/*  47:    */   private static final long serialVersionUID = 1L;
/*  48:    */   @EJB
/*  49:    */   private ServicioPoliza servicioPoliza;
/*  50:    */   @EJB
/*  51:    */   private ServicioAsegurado servicioAsegurado;
/*  52:    */   @EJB
/*  53:    */   private ServicioAfianzado servicioAfianzado;
/*  54:    */   @EJB
/*  55:    */   private ServicioAseguradora servicioAseguradora;
/*  56:    */   @EJB
/*  57:    */   private ServicioBroker servicioBroker;
/*  58:    */   @EJB
/*  59:    */   private ServicioTipoPoliza servicioTipoPoliza;
/*  60:    */   @EJB
/*  61:    */   private ServicioContratoPoliza servicioContratoPoliza;
/*  62:    */   @EJB
/*  63:    */   private ServicioDepartamento servicioDepartamento;
/*  64:    */   private Poliza poliza;
/*  65:    */   private Poliza polizaPadre;
/*  66:    */   private String contratoPolizaSeleccionado;
/*  67:    */   private LazyDataModel<Poliza> listaPoliza;
/*  68:    */   private List<Asegurado> listaAsegurado;
/*  69:    */   private List<Afianzado> listaAfianzado;
/*  70:    */   private List<Aseguradora> listaAseguradora;
/*  71:    */   private List<Broker> listaBroker;
/*  72:    */   private List<TipoPoliza> listaTipoPoliza;
/*  73:    */   private List<SelectItem> listaEstado;
/*  74:    */   private List<ContratoPoliza> listaContratoPoliza;
/*  75:    */   private List<Departamento> listaDepartamento;
/*  76:    */   private DataTable dtPoliza;
/*  77:    */   
/*  78:    */   @PostConstruct
/*  79:    */   public void init()
/*  80:    */   {
/*  81:117 */     this.listaPoliza = new LazyDataModel()
/*  82:    */     {
/*  83:    */       private static final long serialVersionUID = 1L;
/*  84:    */       
/*  85:    */       public List<Poliza> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  86:    */       {
/*  87:124 */         List<Poliza> lista = new ArrayList();
/*  88:125 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  89:    */         
/*  90:127 */         lista = PolizaBean.this.servicioPoliza.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  91:    */         
/*  92:129 */         PolizaBean.this.listaPoliza.setRowCount(PolizaBean.this.servicioPoliza.contarPorCriterio(filters));
/*  93:130 */         return lista;
/*  94:    */       }
/*  95:    */     };
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void crearPoliza()
/*  99:    */   {
/* 100:144 */     this.poliza = new Poliza();
/* 101:145 */     this.poliza.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 102:146 */     this.poliza.setSucursal(AppUtil.getSucursal());
/* 103:147 */     this.poliza.setAfianzado(new Afianzado());
/* 104:148 */     this.poliza.setAsegurado(new Asegurado());
/* 105:149 */     this.poliza.setDepartamento(new Departamento());
/* 106:150 */     this.poliza.setAseguradora(new Aseguradora());
/* 107:151 */     this.poliza.setBroker(new Broker());
/* 108:152 */     this.poliza.setTipoPoliza(new TipoPoliza());
/* 109:153 */     this.poliza.setPlazo(0);
/* 110:154 */     this.poliza.setFechaSolicitud(new Date());
/* 111:155 */     this.poliza.setFecha(new Date());
/* 112:156 */     this.poliza.setEstado(EstadoPoliza.ELABORADO);
/* 113:157 */     this.poliza.setIndicadorVigente(true);
/* 114:158 */     actualizarFechaVencimiento();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String editar()
/* 118:    */   {
/* 119:167 */     if (getPoliza().getIdPoliza() > 0)
/* 120:    */     {
/* 121:168 */       if (getPoliza().isIndicadorVigente())
/* 122:    */       {
/* 123:169 */         this.poliza = this.servicioPoliza.cargarDetalle(getPoliza().getId());
/* 124:170 */         setEditado(true);
/* 125:    */       }
/* 126:    */       else
/* 127:    */       {
/* 128:172 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 129:    */       }
/* 130:    */     }
/* 131:    */     else {
/* 132:176 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 133:    */     }
/* 134:178 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String guardar()
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:188 */       if (getPolizaPadre().getId() > 0) {
/* 142:189 */         this.servicioPoliza.guardar(getPolizaPadre());
/* 143:    */       }
/* 144:191 */       this.servicioPoliza.guardar(getPoliza());
/* 145:192 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 146:193 */       setEditado(false);
/* 147:194 */       limpiar();
/* 148:    */     }
/* 149:    */     catch (Exception e)
/* 150:    */     {
/* 151:196 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 152:197 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 153:    */     }
/* 154:199 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String eliminar()
/* 158:    */   {
/* 159:    */     try
/* 160:    */     {
/* 161:209 */       this.servicioPoliza.eliminar(getPoliza());
/* 162:210 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 163:    */     }
/* 164:    */     catch (Exception e)
/* 165:    */     {
/* 166:212 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 167:213 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 168:    */     }
/* 169:215 */     return "";
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String cargarDatos()
/* 173:    */   {
/* 174:224 */     return "";
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String limpiar()
/* 178:    */   {
/* 179:233 */     crearPoliza();
/* 180:234 */     return "";
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String renovar()
/* 184:    */   {
/* 185:239 */     this.polizaPadre = ((Poliza)this.dtPoliza.getRowData());
/* 186:241 */     if (this.polizaPadre.getEstado() == EstadoPoliza.EMITIDA)
/* 187:    */     {
/* 188:243 */       this.polizaPadre.setEstado(EstadoPoliza.VENCIDA);
/* 189:244 */       this.polizaPadre.setIndicadorVigente(false);
/* 190:    */       
/* 191:246 */       this.poliza = this.servicioPoliza.cargarDetalle(this.polizaPadre.getId());
/* 192:247 */       this.poliza.setIdPoliza(0);
/* 193:248 */       this.poliza.setNumero(this.polizaPadre.getNumero() + " " + this.polizaPadre.getNumeroRenegociacion() + 1);
/* 194:249 */       this.poliza.setNumeroRenegociacion(this.polizaPadre.getNumeroRenegociacion() + 1);
/* 195:250 */       this.poliza.setIndicadorVigente(true);
/* 196:251 */       this.poliza.setEstado(EstadoPoliza.ELABORADO);
/* 197:252 */       this.poliza.setFecha(this.polizaPadre.getFechaVencimiento());
/* 198:253 */       this.poliza.setPolizaPadre(this.polizaPadre);
/* 199:    */       
/* 200:255 */       setEditado(true);
/* 201:    */     }
/* 202:258 */     return "";
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void processUpload(FileUploadEvent event)
/* 206:    */   {
/* 207:    */     try
/* 208:    */     {
/* 209:271 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "polizas");
/* 210:272 */       String fileName = FuncionesUtiles.uploadArchivo(event, AppUtil.getOrganizacion().getId() + "", getPoliza().getNumero(), uploadDir);
/* 211:    */       
/* 212:274 */       HashMap<String, Object> campos = new HashMap();
/* 213:275 */       campos.put("pdf", fileName);
/* 214:276 */       this.servicioPoliza.actualizarAtributoEntidad(this.poliza, campos);
/* 215:    */       
/* 216:278 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 217:    */     }
/* 218:    */     catch (Exception e)
/* 219:    */     {
/* 220:281 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 221:282 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void processDownload()
/* 226:    */   {
/* 227:    */     try
/* 228:    */     {
/* 229:292 */       Poliza poliza = (Poliza)this.dtPoliza.getRowData();
/* 230:293 */       String fileName = poliza.getPdf();
/* 231:294 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "polizas", "documentos");
/* 232:295 */       if (fileName == null) {
/* 233:296 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 234:    */       } else {
/* 235:298 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 236:    */       }
/* 237:    */     }
/* 238:    */     catch (Exception e)
/* 239:    */     {
/* 240:301 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 241:302 */       e.printStackTrace();
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public String seleccionar()
/* 246:    */   {
/* 247:308 */     this.poliza = ((Poliza)this.dtPoliza.getRowData());
/* 248:309 */     return "";
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void dateSelect() {}
/* 252:    */   
/* 253:    */   public void actualizarFechaVencimiento()
/* 254:    */   {
/* 255:332 */     Date fechaVencimiento = FuncionesUtiles.sumarFechaDiasMeses(getPoliza().getFecha(), getPoliza().getPlazo());
/* 256:333 */     getPoliza().setFechaVencimiento(fechaVencimiento);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public Poliza getPoliza()
/* 260:    */   {
/* 261:347 */     if (this.poliza == null) {
/* 262:348 */       crearPoliza();
/* 263:    */     }
/* 264:350 */     return this.poliza;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setPoliza(Poliza poliza)
/* 268:    */   {
/* 269:360 */     this.poliza = poliza;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public Poliza getPolizaPadre()
/* 273:    */   {
/* 274:369 */     if (this.polizaPadre == null) {
/* 275:370 */       this.polizaPadre = new Poliza();
/* 276:    */     }
/* 277:372 */     return this.polizaPadre;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setPolizaPadre(Poliza polizaPadre)
/* 281:    */   {
/* 282:382 */     this.polizaPadre = polizaPadre;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getContratoPolizaSeleccionado()
/* 286:    */   {
/* 287:392 */     this.contratoPolizaSeleccionado = "";
/* 288:394 */     if (getPoliza().getContratoPoliza() != null) {
/* 289:395 */       this.contratoPolizaSeleccionado = ("" + getPoliza().getContratoPoliza().getId());
/* 290:    */     }
/* 291:398 */     return this.contratoPolizaSeleccionado;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setContratoPolizaSeleccionado(String contratoPolizaSeleccionado)
/* 295:    */   {
/* 296:409 */     if (this.contratoPolizaSeleccionado != contratoPolizaSeleccionado)
/* 297:    */     {
/* 298:410 */       this.contratoPolizaSeleccionado = contratoPolizaSeleccionado;
/* 299:    */       
/* 300:412 */       ContratoPoliza contratoPoliza = null;
/* 301:414 */       if (this.contratoPolizaSeleccionado != "")
/* 302:    */       {
/* 303:415 */         int idContratoPoliza = Integer.parseInt(this.contratoPolizaSeleccionado);
/* 304:416 */         contratoPoliza = this.servicioContratoPoliza.buscarPorId(idContratoPoliza);
/* 305:    */       }
/* 306:419 */       getPoliza().setContratoPoliza(contratoPoliza);
/* 307:    */     }
/* 308:    */   }
/* 309:    */   
/* 310:    */   public LazyDataModel<Poliza> getListaPoliza()
/* 311:    */   {
/* 312:429 */     return this.listaPoliza;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setListaPoliza(LazyDataModel<Poliza> listaPoliza)
/* 316:    */   {
/* 317:439 */     this.listaPoliza = listaPoliza;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public List<Asegurado> getListaAsegurado()
/* 321:    */   {
/* 322:448 */     if (this.listaAsegurado == null) {
/* 323:449 */       this.listaAsegurado = this.servicioAsegurado.obtenerListaCombo("nombre", true, null);
/* 324:    */     }
/* 325:451 */     return this.listaAsegurado;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setListaAsegurado(List<Asegurado> listaAsegurado)
/* 329:    */   {
/* 330:461 */     this.listaAsegurado = listaAsegurado;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public List<Afianzado> getListaAfianzado()
/* 334:    */   {
/* 335:470 */     if (this.listaAfianzado == null) {
/* 336:471 */       this.listaAfianzado = this.servicioAfianzado.obtenerListaCombo("nombres", true, null);
/* 337:    */     }
/* 338:474 */     return this.listaAfianzado;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setListaAfianzado(List<Afianzado> listaAfianzado)
/* 342:    */   {
/* 343:484 */     this.listaAfianzado = listaAfianzado;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public List<Aseguradora> getListaAseguradora()
/* 347:    */   {
/* 348:493 */     if (this.listaAseguradora == null) {
/* 349:494 */       this.listaAseguradora = this.servicioAseguradora.obtenerListaCombo("nombre", true, null);
/* 350:    */     }
/* 351:496 */     return this.listaAseguradora;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setListaAseguradora(List<Aseguradora> listaAseguradora)
/* 355:    */   {
/* 356:506 */     this.listaAseguradora = listaAseguradora;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public List<Broker> getListaBroker()
/* 360:    */   {
/* 361:515 */     if (this.listaBroker == null) {
/* 362:516 */       this.listaBroker = this.servicioBroker.obtenerListaCombo("nombres", true, null);
/* 363:    */     }
/* 364:518 */     return this.listaBroker;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void setListaBroker(List<Broker> listaBroker)
/* 368:    */   {
/* 369:528 */     this.listaBroker = listaBroker;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public List<TipoPoliza> getListaTipoPoliza()
/* 373:    */   {
/* 374:537 */     if (this.listaTipoPoliza == null) {
/* 375:538 */       this.listaTipoPoliza = this.servicioTipoPoliza.obtenerListaCombo("nombre", true, null);
/* 376:    */     }
/* 377:540 */     return this.listaTipoPoliza;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setListaTipoPoliza(List<TipoPoliza> listaTipoPoliza)
/* 381:    */   {
/* 382:550 */     this.listaTipoPoliza = listaTipoPoliza;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public List<ContratoPoliza> getListaContratoPoliza()
/* 386:    */   {
/* 387:559 */     if (this.listaContratoPoliza == null) {
/* 388:560 */       this.listaContratoPoliza = this.servicioContratoPoliza.obtenerListaCombo("nombre", true, null);
/* 389:    */     }
/* 390:562 */     return this.listaContratoPoliza;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setListaContratoPoliza(List<ContratoPoliza> listaContratoPoliza)
/* 394:    */   {
/* 395:572 */     this.listaContratoPoliza = listaContratoPoliza;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public List<Departamento> getListaDepartamento()
/* 399:    */   {
/* 400:581 */     if (this.listaDepartamento == null) {
/* 401:582 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 402:    */     }
/* 403:584 */     return this.listaDepartamento;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setListaDepartamento(List<Departamento> listaDepartamento)
/* 407:    */   {
/* 408:594 */     this.listaDepartamento = listaDepartamento;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public List<SelectItem> getListaEstado()
/* 412:    */   {
/* 413:603 */     if (this.listaEstado == null)
/* 414:    */     {
/* 415:604 */       this.listaEstado = new ArrayList();
/* 416:605 */       for (EstadoPoliza estadoPoliza : EstadoPoliza.values()) {
/* 417:606 */         this.listaEstado.add(new SelectItem(estadoPoliza, estadoPoliza.getNombre()));
/* 418:    */       }
/* 419:    */     }
/* 420:609 */     return this.listaEstado;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setListaEstado(List<SelectItem> listaEstado)
/* 424:    */   {
/* 425:619 */     this.listaEstado = listaEstado;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public DataTable getDtPoliza()
/* 429:    */   {
/* 430:628 */     return this.dtPoliza;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setDtPoliza(DataTable dtPoliza)
/* 434:    */   {
/* 435:638 */     this.dtPoliza = dtPoliza;
/* 436:    */   }
/* 437:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.polizas.procesos.PolizaBean
 * JD-Core Version:    0.7.0.1
 */