/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleReporteador;
/*   7:    */ import com.asinfo.as2.entities.DetalleReporteadorVariable;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Reporteador;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.Periodicidad;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  13:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  14:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteador;
/*  17:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.JsfUtil;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.HashSet;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import java.util.Set;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ManagedProperty;
/*  31:    */ import javax.faces.bean.ViewScoped;
/*  32:    */ import javax.faces.model.SelectItem;
/*  33:    */ import net.objecthunter.exp4j.Expression;
/*  34:    */ import net.objecthunter.exp4j.ExpressionBuilder;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ import org.primefaces.component.datatable.DataTable;
/*  37:    */ import org.primefaces.component.treetable.TreeTable;
/*  38:    */ import org.primefaces.event.SelectEvent;
/*  39:    */ import org.primefaces.model.DefaultTreeNode;
/*  40:    */ import org.primefaces.model.LazyDataModel;
/*  41:    */ import org.primefaces.model.SortOrder;
/*  42:    */ import org.primefaces.model.TreeNode;
/*  43:    */ 
/*  44:    */ @ManagedBean
/*  45:    */ @ViewScoped
/*  46:    */ public class ReporteadorBean
/*  47:    */   extends PageControllerAS2
/*  48:    */ {
/*  49:    */   private static final long serialVersionUID = 1L;
/*  50:    */   @EJB
/*  51:    */   private ServicioReporteador servicioReporteador;
/*  52:    */   @EJB
/*  53:    */   private ServicioCuentaContable servicioCuentaContable;
/*  54:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  55:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  56:    */   private Reporteador reporteador;
/*  57:    */   private DetalleReporteador detalleReporteadorSeleccionado;
/*  58:    */   private LazyDataModel<Reporteador> listaReporteador;
/*  59:    */   private DetalleReporteadorVariable detalleVariableSeleccionada;
/*  60:    */   private List<TipoCalculo> listaTipoCalculo;
/*  61:    */   private List<ValoresCalculo> listaValoresCalculo;
/*  62:    */   private DataTable dtReporte;
/*  63:    */   private DataTable dtVariable;
/*  64:    */   private DataTable dtFormula;
/*  65:    */   private TreeTable dtTreeTable;
/*  66:    */   private TreeNode root;
/*  67:    */   private int nivelMaximo;
/*  68:    */   
/*  69:    */   @PostConstruct
/*  70:    */   public void init()
/*  71:    */   {
/*  72: 99 */     getListaCuentaContableBean().setIndicadorSeleccionarTodo(true);
/*  73:100 */     this.listaReporteador = new LazyDataModel()
/*  74:    */     {
/*  75:    */       private static final long serialVersionUID = 1L;
/*  76:    */       
/*  77:    */       public List<Reporteador> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  78:    */       {
/*  79:107 */         List<Reporteador> lista = new ArrayList();
/*  80:108 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  81:    */         
/*  82:110 */         lista = ReporteadorBean.this.servicioReporteador.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  83:    */         
/*  84:112 */         ReporteadorBean.this.listaReporteador.setRowCount(lista.size());
/*  85:    */         
/*  86:114 */         return lista;
/*  87:    */       }
/*  88:    */     };
/*  89:    */   }
/*  90:    */   
/*  91:    */   private void crearReporte()
/*  92:    */   {
/*  93:123 */     this.reporteador = new Reporteador();
/*  94:124 */     this.reporteador.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  95:125 */     this.reporteador.setIdSucursal(AppUtil.getSucursal().getId());
/*  96:126 */     this.reporteador.setFicheroReporte("reporteador");
/*  97:    */     
/*  98:128 */     generarArbol();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String editar()
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:134 */       if ((this.reporteador != null) && (this.reporteador.getId() != 0))
/* 106:    */       {
/* 107:135 */         this.reporteador = this.servicioReporteador.cargarDetalle(this.reporteador.getId());
/* 108:136 */         generarArbol();
/* 109:137 */         setEditado(true);
/* 110:    */       }
/* 111:    */       else
/* 112:    */       {
/* 113:139 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 114:    */       }
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:142 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 119:143 */       LOG.error("ERROR AL CARGAR LOS DETALLES DATOS");
/* 120:    */     }
/* 121:145 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String guardar()
/* 125:    */   {
/* 126:    */     try
/* 127:    */     {
/* 128:156 */       for (DetalleReporteadorVariable detalle : this.reporteador.getListaDetalleReporteadorVariable()) {
/* 129:157 */         if ((!detalle.isEliminado()) && (detalle.isIndicadorFormula())) {
/* 130:158 */           validaFormula(detalle);
/* 131:    */         }
/* 132:    */       }
/* 133:161 */       this.servicioReporteador.guardar(this.reporteador);
/* 134:162 */       setEditado(false);
/* 135:163 */       limpiar();
/* 136:164 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 137:    */     }
/* 138:    */     catch (IllegalArgumentException e)
/* 139:    */     {
/* 140:166 */       addErrorMessage(getLanguageController().getMensaje("msg_error_formula") + " : " + e.getLocalizedMessage());
/* 141:    */     }
/* 142:    */     catch (ArithmeticException e)
/* 143:    */     {
/* 144:168 */       addErrorMessage(getLanguageController().getMensaje("msg_error_formula") + " : " + e.getLocalizedMessage());
/* 145:    */     }
/* 146:    */     catch (ExcepcionAS2Financiero ex)
/* 147:    */     {
/* 148:170 */       addErrorMessage(getLanguageController().getMensaje(ex.getMessage()));
/* 149:    */     }
/* 150:    */     catch (AS2Exception e)
/* 151:    */     {
/* 152:172 */       JsfUtil.addErrorMessage(e, "");
/* 153:    */     }
/* 154:    */     catch (Exception e)
/* 155:    */     {
/* 156:174 */       e.printStackTrace();
/* 157:175 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 158:    */     }
/* 159:177 */     return "";
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String eliminar()
/* 163:    */   {
/* 164:    */     try
/* 165:    */     {
/* 166:183 */       if ((this.reporteador != null) && (this.reporteador.getId() != 0))
/* 167:    */       {
/* 168:184 */         this.servicioReporteador.eliminar(this.reporteador);
/* 169:185 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 170:    */       }
/* 171:    */       else
/* 172:    */       {
/* 173:187 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 174:    */       }
/* 175:    */     }
/* 176:    */     catch (Exception e)
/* 177:    */     {
/* 178:190 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 179:191 */       e.printStackTrace();
/* 180:    */     }
/* 181:193 */     return "";
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String limpiar()
/* 185:    */   {
/* 186:198 */     crearReporte();
/* 187:199 */     return "";
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String cargarDatos()
/* 191:    */   {
/* 192:204 */     return "";
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String agregarDetalleReporte(DetalleReporteador detalleReporteadorPadre)
/* 196:    */   {
/* 197:209 */     DetalleReporteador detalleReporteador = new DetalleReporteador();
/* 198:210 */     detalleReporteador.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 199:211 */     detalleReporteador.setIdSucursal(AppUtil.getSucursal().getId());
/* 200:212 */     detalleReporteador.setActivo(true);
/* 201:213 */     detalleReporteador.setReporteador(this.reporteador);
/* 202:214 */     detalleReporteador.setDetalleReporteadorPadre(detalleReporteadorPadre);
/* 203:216 */     if (detalleReporteadorPadre != null) {
/* 204:217 */       detalleReporteadorPadre.getListaDetalleReporteadorHijo().add(detalleReporteador);
/* 205:    */     } else {
/* 206:219 */       this.reporteador.getListaDetalleReporteador().add(detalleReporteador);
/* 207:    */     }
/* 208:222 */     generarArbol();
/* 209:223 */     return "";
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void agregarNodo()
/* 213:    */   {
/* 214:227 */     TreeNode nodo = this.dtTreeTable.getRowNode();
/* 215:228 */     DetalleReporteador detalleReporteador = (DetalleReporteador)nodo.getData();
/* 216:229 */     agregarDetalleReporte(detalleReporteador);
/* 217:    */     
/* 218:231 */     generarArbol();
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String eliminarDetalleReporte()
/* 222:    */   {
/* 223:235 */     TreeNode nodo = this.dtTreeTable.getRowNode();
/* 224:236 */     DetalleReporteador detalleReporteador = (DetalleReporteador)nodo.getData();
/* 225:237 */     detalleReporteador.setEliminado(true);
/* 226:    */     
/* 227:239 */     generarArbol();
/* 228:240 */     return "";
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void generarArbol()
/* 232:    */   {
/* 233:244 */     this.nivelMaximo = 0;
/* 234:245 */     generarNodosRecursivo(null);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void generarNodosRecursivo(TreeNode nodoPadre)
/* 238:    */   {
/* 239:249 */     int nivel = 0;
/* 240:250 */     List<DetalleReporteador> listaDetalleReporteador = null;
/* 241:251 */     if (nodoPadre == null)
/* 242:    */     {
/* 243:252 */       this.root = new DefaultTreeNode(null, null);
/* 244:253 */       nodoPadre = this.root;
/* 245:254 */       listaDetalleReporteador = this.reporteador.getListaDetalleReporteador();
/* 246:    */     }
/* 247:    */     else
/* 248:    */     {
/* 249:256 */       listaDetalleReporteador = ((DetalleReporteador)nodoPadre.getData()).getListaDetalleReporteadorHijo();
/* 250:257 */       nivel = ((DetalleReporteador)nodoPadre.getData()).getNivel();
/* 251:    */     }
/* 252:260 */     nivel++;
/* 253:261 */     nodoPadre.setExpanded(true);
/* 254:    */     
/* 255:263 */     boolean tieneHijos = false;
/* 256:264 */     for (DetalleReporteador detalleReporteador : listaDetalleReporteador) {
/* 257:265 */       if (!detalleReporteador.isEliminado())
/* 258:    */       {
/* 259:266 */         detalleReporteador.setNivel(nivel);
/* 260:267 */         if (nivel > this.nivelMaximo) {
/* 261:268 */           this.nivelMaximo = nivel;
/* 262:    */         }
/* 263:270 */         TreeNode nodo = new DefaultTreeNode(detalleReporteador, nodoPadre);
/* 264:271 */         generarNodosRecursivo(nodo);
/* 265:272 */         tieneHijos = true;
/* 266:    */       }
/* 267:    */     }
/* 268:275 */     if (nodoPadre.getData() != null) {
/* 269:276 */       ((DetalleReporteador)nodoPadre.getData()).setIndicadorHoja(!tieneHijos);
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<SelectItem> getListaPeriodicidad()
/* 274:    */   {
/* 275:284 */     List<SelectItem> periodicidad = new ArrayList();
/* 276:285 */     for (Periodicidad p : Periodicidad.values()) {
/* 277:286 */       periodicidad.add(new SelectItem(p, p.getNombre()));
/* 278:    */     }
/* 279:288 */     return periodicidad;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public List<TipoCalculo> getListaTipoCalculo()
/* 283:    */   {
/* 284:292 */     if (this.listaTipoCalculo == null)
/* 285:    */     {
/* 286:293 */       this.listaTipoCalculo = new ArrayList();
/* 287:294 */       for (TipoCalculo tipoCalculo : TipoCalculo.values()) {
/* 288:295 */         this.listaTipoCalculo.add(tipoCalculo);
/* 289:    */       }
/* 290:    */     }
/* 291:298 */     return this.listaTipoCalculo;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public List<ValoresCalculo> getListaValoresCalculo()
/* 295:    */   {
/* 296:302 */     if (this.listaValoresCalculo == null)
/* 297:    */     {
/* 298:303 */       this.listaValoresCalculo = new ArrayList();
/* 299:304 */       for (ValoresCalculo valor : ValoresCalculo.values()) {
/* 300:305 */         this.listaValoresCalculo.add(valor);
/* 301:    */       }
/* 302:    */     }
/* 303:308 */     return this.listaValoresCalculo;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public TreeNode getRoot()
/* 307:    */   {
/* 308:312 */     return this.root;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setRoot(TreeNode root)
/* 312:    */   {
/* 313:316 */     this.root = root;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public TreeTable getDtTreeTable()
/* 317:    */   {
/* 318:320 */     return this.dtTreeTable;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setDtTreeTable(TreeTable dtTreeTable)
/* 322:    */   {
/* 323:324 */     this.dtTreeTable = dtTreeTable;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public Reporteador getReporteador()
/* 327:    */   {
/* 328:328 */     return this.reporteador;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setReporteador(Reporteador reporteador)
/* 332:    */   {
/* 333:332 */     this.reporteador = reporteador;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public DetalleReporteador getDetalleReporteadorSeleccionado()
/* 337:    */   {
/* 338:336 */     return this.detalleReporteadorSeleccionado;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setDetalleReporteadorSeleccionado(DetalleReporteador detalleReporteadorSeleccionado)
/* 342:    */   {
/* 343:340 */     this.detalleReporteadorSeleccionado = detalleReporteadorSeleccionado;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public LazyDataModel<Reporteador> getListaReporteador()
/* 347:    */   {
/* 348:344 */     return this.listaReporteador;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setListaReporteador(LazyDataModel<Reporteador> listaReporteador)
/* 352:    */   {
/* 353:348 */     this.listaReporteador = listaReporteador;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public DataTable getDtReporte()
/* 357:    */   {
/* 358:352 */     return this.dtReporte;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setDtReporte(DataTable dtReporte)
/* 362:    */   {
/* 363:356 */     this.dtReporte = dtReporte;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public DataTable getDtVariable()
/* 367:    */   {
/* 368:360 */     return this.dtVariable;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void setDtVariable(DataTable dtVariable)
/* 372:    */   {
/* 373:364 */     this.dtVariable = dtVariable;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public DataTable getDtFormula()
/* 377:    */   {
/* 378:368 */     return this.dtFormula;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void setDtFormula(DataTable dtFormula)
/* 382:    */   {
/* 383:372 */     this.dtFormula = dtFormula;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public int getNivelMaximo()
/* 387:    */   {
/* 388:376 */     return this.nivelMaximo;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void setNivelMaximo(int nivelMaximo)
/* 392:    */   {
/* 393:380 */     this.nivelMaximo = nivelMaximo;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void cargarDetalleReporteador()
/* 397:    */   {
/* 398:384 */     TreeNode nodo = this.dtTreeTable.getRowNode();
/* 399:385 */     this.detalleReporteadorSeleccionado = ((DetalleReporteador)nodo.getData());
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void agregarVariable(boolean indicadorFormula)
/* 403:    */   {
/* 404:389 */     DetalleReporteadorVariable detalle = new DetalleReporteadorVariable();
/* 405:390 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 406:391 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 407:392 */     detalle.setReporteador(this.reporteador);
/* 408:393 */     detalle.setIndicadorFormula(indicadorFormula);
/* 409:394 */     if (!indicadorFormula)
/* 410:    */     {
/* 411:395 */       detalle.setTipoCalculo(TipoCalculo.MOVIMIENTOS_MES);
/* 412:396 */       detalle.setCuentaContable(new CuentaContable());
/* 413:    */     }
/* 414:398 */     this.reporteador.getListaDetalleReporteadorVariable().add(detalle);
/* 415:    */   }
/* 416:    */   
/* 417:    */   public void eliminarVariable(boolean indicadorFormula)
/* 418:    */   {
/* 419:402 */     DetalleReporteadorVariable detalle = null;
/* 420:403 */     if (indicadorFormula)
/* 421:    */     {
/* 422:404 */       detalle = (DetalleReporteadorVariable)this.dtFormula.getRowData();
/* 423:    */       
/* 424:406 */       limpiarDetalle(null, detalle);
/* 425:    */     }
/* 426:    */     else
/* 427:    */     {
/* 428:408 */       detalle = (DetalleReporteadorVariable)this.dtVariable.getRowData();
/* 429:    */     }
/* 430:410 */     detalle.setEliminado(true);
/* 431:    */   }
/* 432:    */   
/* 433:    */   private void limpiarDetalle(DetalleReporteador detalleReporteadorPadre, DetalleReporteadorVariable detalleReporteadorVariable)
/* 434:    */   {
/* 435:414 */     List<DetalleReporteador> listaDetalleReporteador = null;
/* 436:415 */     if (detalleReporteadorPadre == null) {
/* 437:416 */       listaDetalleReporteador = this.reporteador.getListaDetalleReporteador();
/* 438:    */     } else {
/* 439:418 */       listaDetalleReporteador = detalleReporteadorPadre.getListaDetalleReporteadorHijo();
/* 440:    */     }
/* 441:421 */     for (DetalleReporteador detalleReporteador : listaDetalleReporteador)
/* 442:    */     {
/* 443:422 */       if ((detalleReporteador.getDetalleReporteadorVariable() != null) && 
/* 444:423 */         (detalleReporteador.getDetalleReporteadorVariable().getRowKey() == detalleReporteadorVariable.getRowKey())) {
/* 445:424 */         detalleReporteador.setDetalleReporteadorVariable(null);
/* 446:    */       }
/* 447:426 */       limpiarDetalle(detalleReporteador, detalleReporteadorVariable);
/* 448:    */     }
/* 449:    */   }
/* 450:    */   
/* 451:    */   public List<DetalleReporteadorVariable> getListaVariables()
/* 452:    */   {
/* 453:431 */     List<DetalleReporteadorVariable> lista = new ArrayList();
/* 454:432 */     if (this.reporteador != null) {
/* 455:433 */       for (DetalleReporteadorVariable detalleReporteadorVariable : this.reporteador.getListaDetalleReporteadorVariable()) {
/* 456:434 */         if ((!detalleReporteadorVariable.isEliminado()) && (!detalleReporteadorVariable.isIndicadorFormula())) {
/* 457:435 */           lista.add(detalleReporteadorVariable);
/* 458:    */         }
/* 459:    */       }
/* 460:    */     }
/* 461:439 */     return lista;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public List<DetalleReporteadorVariable> getListaFormulas()
/* 465:    */   {
/* 466:443 */     List<DetalleReporteadorVariable> lista = new ArrayList();
/* 467:444 */     if (this.reporteador != null) {
/* 468:445 */       for (DetalleReporteadorVariable detalleReporteadorVariable : this.reporteador.getListaDetalleReporteadorVariable()) {
/* 469:446 */         if ((!detalleReporteadorVariable.isEliminado()) && (detalleReporteadorVariable.isIndicadorFormula())) {
/* 470:447 */           lista.add(detalleReporteadorVariable);
/* 471:    */         }
/* 472:    */       }
/* 473:    */     }
/* 474:451 */     return lista;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public List<DetalleReporteadorVariable> autocompletarFormula(String consulta)
/* 478:    */   {
/* 479:455 */     consulta = consulta.trim().toLowerCase();
/* 480:456 */     List<DetalleReporteadorVariable> lista = new ArrayList();
/* 481:457 */     for (DetalleReporteadorVariable detalleReporteadorVariable : this.reporteador.getListaDetalleReporteadorVariable()) {
/* 482:458 */       if ((!detalleReporteadorVariable.isEliminado()) && (
/* 483:459 */         (detalleReporteadorVariable.getCodigo().toLowerCase().contains(consulta)) || (consulta.isEmpty()))) {
/* 484:460 */         lista.add(detalleReporteadorVariable);
/* 485:    */       }
/* 486:    */     }
/* 487:464 */     return lista;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void buscarCuentaContable()
/* 491:    */   {
/* 492:    */     try
/* 493:    */     {
/* 494:469 */       this.detalleVariableSeleccionada = ((DetalleReporteadorVariable)this.dtVariable.getRowData());
/* 495:470 */       String codigoCuentaContable = this.detalleVariableSeleccionada.getCuentaContable().getCodigo();
/* 496:471 */       if (codigoCuentaContable != "")
/* 497:    */       {
/* 498:472 */         CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion()
/* 499:473 */           .getIdOrganizacion());
/* 500:474 */         this.detalleVariableSeleccionada.setCuentaContable(cuentaContable);
/* 501:    */       }
/* 502:    */     }
/* 503:    */     catch (ExcepcionAS2Financiero e)
/* 504:    */     {
/* 505:478 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion()) + this.detalleVariableSeleccionada.getCuentaContable().getCodigo();
/* 506:479 */       addInfoMessage(strMensaje);
/* 507:    */     }
/* 508:    */     catch (Exception e)
/* 509:    */     {
/* 510:482 */       LOG.error("ERROR AL BUSCAR CUENTA CONTABLE");
/* 511:    */     }
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void seleccionarCuentaContable(SelectEvent event)
/* 515:    */   {
/* 516:487 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/* 517:    */     
/* 518:489 */     this.detalleVariableSeleccionada.setCuentaContable(cuentaContable);
/* 519:    */   }
/* 520:    */   
/* 521:    */   public void validaFormula()
/* 522:    */   {
/* 523:493 */     DetalleReporteadorVariable detalle = (DetalleReporteadorVariable)this.dtFormula.getRowData();
/* 524:    */     try
/* 525:    */     {
/* 526:495 */       validaFormula(detalle);
/* 527:    */     }
/* 528:    */     catch (IllegalArgumentException e)
/* 529:    */     {
/* 530:497 */       addErrorMessage(getLanguageController().getMensaje("msg_error_formula") + " : " + e.getLocalizedMessage());
/* 531:    */     }
/* 532:    */     catch (ArithmeticException e)
/* 533:    */     {
/* 534:499 */       addErrorMessage(getLanguageController().getMensaje("msg_error_formula") + " : " + e.getLocalizedMessage());
/* 535:    */     }
/* 536:    */     catch (Exception e)
/* 537:    */     {
/* 538:501 */       e.printStackTrace();
/* 539:502 */       addErrorMessage(e.getMessage());
/* 540:    */     }
/* 541:    */   }
/* 542:    */   
/* 543:    */   public void validaFormula(DetalleReporteadorVariable detalle)
/* 544:    */     throws AS2Exception, IllegalArgumentException, ArithmeticException
/* 545:    */   {
/* 546:508 */     Set<String> setVariables = new HashSet();
/* 547:509 */     Map<String, Double> mapaVariables = new HashMap();
/* 548:510 */     for (DetalleReporteadorVariable detalleReporteadorVariable : this.reporteador.getListaDetalleReporteadorVariable()) {
/* 549:511 */       if ((!detalleReporteadorVariable.isEliminado()) && (detalleReporteadorVariable.getRowKey() != detalle.getRowKey()))
/* 550:    */       {
/* 551:512 */         setVariables.add(detalleReporteadorVariable.getCodigo());
/* 552:513 */         mapaVariables.put(detalleReporteadorVariable.getCodigo(), Double.valueOf(1.0D));
/* 553:    */       }
/* 554:    */     }
/* 555:    */     try
/* 556:    */     {
/* 557:518 */       Expression expresion = new ExpressionBuilder(detalle.getExpresion()).variables(setVariables).build();
/* 558:519 */       expresion.setVariables(mapaVariables);
/* 559:520 */       expresion.evaluate();
/* 560:    */     }
/* 561:    */     catch (IllegalArgumentException e)
/* 562:    */     {
/* 563:522 */       throw e;
/* 564:    */     }
/* 565:    */     catch (ArithmeticException e)
/* 566:    */     {
/* 567:524 */       throw e;
/* 568:    */     }
/* 569:    */     catch (Exception e)
/* 570:    */     {
/* 571:526 */       throw new AS2Exception(e.getMessage());
/* 572:    */     }
/* 573:    */   }
/* 574:    */   
/* 575:    */   public DetalleReporteadorVariable getDetalleVariableSeleccionada()
/* 576:    */   {
/* 577:531 */     return this.detalleVariableSeleccionada;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public void setDetalleVariableSeleccionada(DetalleReporteadorVariable detalleVariableSeleccionada)
/* 581:    */   {
/* 582:535 */     this.detalleVariableSeleccionada = detalleVariableSeleccionada;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 586:    */   {
/* 587:539 */     return this.listaCuentaContableBean;
/* 588:    */   }
/* 589:    */   
/* 590:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 591:    */   {
/* 592:543 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public void ordenar(DetalleReporteador detalleReporteadorPadre)
/* 596:    */   {
/* 597:547 */     List<DetalleReporteador> listaDetalle = null;
/* 598:548 */     if (detalleReporteadorPadre != null) {
/* 599:549 */       listaDetalle = detalleReporteadorPadre.getListaDetalleReporteadorHijo();
/* 600:    */     } else {
/* 601:551 */       listaDetalle = this.reporteador.getListaDetalleReporteador();
/* 602:    */     }
/* 603:554 */     FuncionesUtiles.ordenaLista(listaDetalle, "orden");
/* 604:555 */     for (DetalleReporteador detalleReporteador : listaDetalle) {
/* 605:556 */       ordenar(detalleReporteador);
/* 606:    */     }
/* 607:559 */     generarArbol();
/* 608:    */   }
/* 609:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ReporteadorBean
 * JD-Core Version:    0.7.0.1
 */