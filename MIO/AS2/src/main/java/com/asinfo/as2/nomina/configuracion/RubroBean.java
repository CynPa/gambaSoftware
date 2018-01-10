/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Quincena;
/*   8:    */ import com.asinfo.as2.entities.Rubro;
/*   9:    */ import com.asinfo.as2.entities.RubroEmpleado;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoRubro;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*  14:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  15:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena;
/*  16:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  17:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  18:    */ import com.asinfo.as2.nomina.procesos.compilador.Evaluador;
/*  19:    */ import com.asinfo.as2.nomina.procesos.servicio.FuncionRol;
/*  20:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  21:    */ import com.asinfo.as2.util.AppUtil;
/*  22:    */ import com.asinfo.as2.utils.formula.Identificador;
/*  23:    */ import com.asinfo.as2.utils.formula.IdentificadorClase;
/*  24:    */ import java.lang.annotation.Annotation;
/*  25:    */ import java.lang.reflect.Method;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.Iterator;
/*  29:    */ import java.util.List;
/*  30:    */ import java.util.Map;
/*  31:    */ import javax.annotation.PostConstruct;
/*  32:    */ import javax.ejb.EJB;
/*  33:    */ import javax.faces.bean.ManagedBean;
/*  34:    */ import javax.faces.bean.ViewScoped;
/*  35:    */ import javax.faces.model.SelectItem;
/*  36:    */ import org.apache.log4j.Logger;
/*  37:    */ import org.primefaces.component.datatable.DataTable;
/*  38:    */ import org.primefaces.model.LazyDataModel;
/*  39:    */ import org.primefaces.model.SortOrder;
/*  40:    */ 
/*  41:    */ @ManagedBean
/*  42:    */ @ViewScoped
/*  43:    */ public class RubroBean
/*  44:    */   extends PageControllerAS2
/*  45:    */ {
/*  46:    */   private static final long serialVersionUID = -4999516232327633622L;
/*  47:    */   @EJB
/*  48:    */   private ServicioRubro servicioRubro;
/*  49:    */   @EJB
/*  50:    */   private ServicioQuincena servicioQuincena;
/*  51:    */   @EJB
/*  52:    */   private ServicioRubroEmpleado servicioRubroEmpleado;
/*  53:    */   @EJB
/*  54:    */   private ServicioEmpleado servicioEmpleado;
/*  55:    */   private String quincenaSelecionada;
/*  56:    */   private Rubro rubro;
/*  57:    */   private List<Quincena> listaQuincena;
/*  58:    */   private List<SelectItem> listaTipoRubro;
/*  59:    */   private List<SelectItem> listaTipo;
/*  60: 86 */   private Evaluador evaluador = new Evaluador();
/*  61:    */   private List<Identificador> listaMetodosFormulaRol;
/*  62:    */   private String formulaSelecionada;
/*  63:    */   private List<Rubro> listaRubroCombo;
/*  64:    */   private List<RubroEmpleado> listaRubroEmpleado;
/*  65:    */   private List<SelectItem> listaMes;
/*  66:    */   private List<SelectItem> listaMesPago;
/*  67:    */   private boolean aplicarRubroAEmpleados;
/*  68:    */   private boolean seleccionarTodos;
/*  69:    */   private LazyDataModel<Rubro> listaRubro;
/*  70:    */   private DataTable dtRubro;
/*  71:    */   private DataTable dtEmpleado;
/*  72:    */   
/*  73:    */   @PostConstruct
/*  74:    */   public void init()
/*  75:    */   {
/*  76:115 */     this.listaRubro = new LazyDataModel()
/*  77:    */     {
/*  78:    */       private static final long serialVersionUID = -4320486915028176364L;
/*  79:    */       
/*  80:    */       public List<Rubro> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  81:    */       {
/*  82:124 */         List<Rubro> lista = new ArrayList();
/*  83:125 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  84:    */         
/*  85:127 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  86:128 */         lista = RubroBean.this.servicioRubro.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  87:    */         
/*  88:130 */         RubroBean.this.listaRubro.setRowCount(RubroBean.this.servicioRubro.contarPorCriterio(filters));
/*  89:    */         
/*  90:132 */         return lista;
/*  91:    */       }
/*  92:    */     };
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void crearRubro()
/*  96:    */   {
/*  97:147 */     this.rubro = new Rubro();
/*  98:148 */     this.rubro.setFormula(new String());
/*  99:149 */     this.rubro.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 100:150 */     this.rubro.setIdSucursal(AppUtil.getSucursal().getId());
/* 101:151 */     this.rubro.setQuincena(new Quincena());
/* 102:152 */     setAplicarRubroAEmpleados(false);
/* 103:153 */     this.listaRubroEmpleado = null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String editar()
/* 107:    */   {
/* 108:163 */     if ((getRubro() != null) && (getRubro().getIdRubro() != 0))
/* 109:    */     {
/* 110:164 */       setRubro(this.servicioRubro.cargarDetalle(getRubro().getId()));
/* 111:165 */       getRubro().setFormula(decodificarFormula());
/* 112:166 */       setEditado(true);
/* 113:    */     }
/* 114:    */     else
/* 115:    */     {
/* 116:168 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 117:    */     }
/* 118:170 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String guardar()
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:180 */       if ((this.evaluador.iAnalizaSintaxis(codificarFormula()) == 0) || (this.evaluador.iAnalizaSintaxis(codificarFormula()) == 14))
/* 126:    */       {
/* 127:181 */         if ((this.rubro.getTipo().equals(TipoRubroEnum.DECIMO_CUARTO)) && (this.rubro.getMesCalculoDesde() == 8) && (this.rubro.getMesCalculoHasta() == 7)) {
/* 128:182 */           this.rubro.setIndicadorDecimoCuartoAcumulado(true);
/* 129:    */         }
/* 130:184 */         this.rubro.setFormula(codificarFormula());
/* 131:185 */         this.servicioRubro.guardar(this.rubro);
/* 132:186 */         agregarRubroTodosEmpleados();
/* 133:187 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 134:188 */         setEditado(false);
/* 135:189 */         limpiar();
/* 136:    */       }
/* 137:    */       else
/* 138:    */       {
/* 139:191 */         addErrorMessage(getLanguageController().getMensaje("msg_error_formula"));
/* 140:    */       }
/* 141:    */     }
/* 142:    */     catch (ExcepcionAS2Nomina e)
/* 143:    */     {
/* 144:195 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 145:196 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 146:    */     }
/* 147:    */     catch (Exception e)
/* 148:    */     {
/* 149:198 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 150:199 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 151:    */     }
/* 152:201 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String eliminar()
/* 156:    */   {
/* 157:    */     try
/* 158:    */     {
/* 159:211 */       this.servicioRubro.eliminar(this.rubro);
/* 160:212 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 161:    */     }
/* 162:    */     catch (Exception e)
/* 163:    */     {
/* 164:214 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 165:215 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 166:    */     }
/* 167:217 */     return "";
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String cargarDatos()
/* 171:    */   {
/* 172:226 */     return "";
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String limpiar()
/* 176:    */   {
/* 177:235 */     crearRubro();
/* 178:236 */     return "";
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<Quincena> getListaQuincena()
/* 182:    */   {
/* 183:241 */     if (this.listaQuincena == null) {
/* 184:242 */       this.listaQuincena = this.servicioQuincena.obtenerListaCombo("nombre", true, null);
/* 185:    */     }
/* 186:245 */     return this.listaQuincena;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<SelectItem> getListaTipoRubro()
/* 190:    */   {
/* 191:254 */     if (this.listaTipoRubro == null)
/* 192:    */     {
/* 193:255 */       this.listaTipoRubro = new ArrayList();
/* 194:256 */       for (TipoRubro tipoRubro : TipoRubro.values())
/* 195:    */       {
/* 196:257 */         SelectItem item = new SelectItem(tipoRubro, tipoRubro.getNombre());
/* 197:258 */         this.listaTipoRubro.add(item);
/* 198:    */       }
/* 199:    */     }
/* 200:261 */     return this.listaTipoRubro;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void validaFormula()
/* 204:    */   {
/* 205:273 */     if ((this.evaluador.iAnalizaSintaxis(codificarFormula()) == 0) || (this.evaluador.iAnalizaSintaxis(codificarFormula()) == 14)) {
/* 206:274 */       addInfoMessage(getLanguageController().getMensaje("msg_info_formula"));
/* 207:    */     } else {
/* 208:277 */       addErrorMessage(getLanguageController().getMensaje("msg_error_formula"));
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void cargarFormula()
/* 213:    */   {
/* 214:285 */     getRubro().setFormula(getRubro().getFormula().concat(getFormulaSelecionada()));
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String codificarFormula()
/* 218:    */   {
/* 219:295 */     String formulaCodificada = null;
/* 220:296 */     String formulaOriginal = getRubro().getFormula();
/* 221:297 */     for (Identificador identificador : getListaMetodosFormulaRol())
/* 222:    */     {
/* 223:298 */       formulaCodificada = formulaOriginal.replace(identificador.getLabel().toString(), identificador.getValue().toString());
/* 224:299 */       formulaOriginal = formulaCodificada;
/* 225:    */     }
/* 226:302 */     return formulaCodificada;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public String decodificarFormula()
/* 230:    */   {
/* 231:311 */     String formulaDecodificada = null;
/* 232:312 */     String formulaOriginal = getRubro().getFormula();
/* 233:313 */     for (Identificador identificador : getListaMetodosFormulaRol())
/* 234:    */     {
/* 235:314 */       formulaDecodificada = formulaOriginal.replace(identificador.getValue().toString(), identificador.getLabel().toString());
/* 236:315 */       formulaOriginal = formulaDecodificada;
/* 237:    */     }
/* 238:318 */     return formulaDecodificada;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void cargarRubroEmpleado()
/* 242:    */   {
/* 243:    */     HashMap<Integer, RubroEmpleado> hmRubroEmpleados;
/* 244:    */     RubroEmpleado re;
/* 245:325 */     if (this.aplicarRubroAEmpleados)
/* 246:    */     {
/* 247:327 */       hmRubroEmpleados = new HashMap();
/* 248:328 */       this.listaRubroEmpleado = new ArrayList();
/* 249:330 */       for (Iterator localIterator = this.servicioRubroEmpleado.getListaRubroEmpleado(getRubro().getId(), AppUtil.getOrganizacion().getIdOrganizacion()).iterator(); localIterator.hasNext();)
/* 250:    */       {
/* 251:330 */         re = (RubroEmpleado)localIterator.next();
/* 252:331 */         hmRubroEmpleados.put(Integer.valueOf(re.getEmpleado().getId()), re);
/* 253:    */       }
/* 254:334 */       Object filters = new HashMap();
/* 255:335 */       ((HashMap)filters).put("empresa.indicadorEmpleado", "true");
/* 256:337 */       for (Empleado empleado : this.servicioEmpleado.obtenerListaCombo("", false, (Map)filters))
/* 257:    */       {
/* 258:338 */         RubroEmpleado rubroEmpleado = (RubroEmpleado)hmRubroEmpleados.get(Integer.valueOf(empleado.getId()));
/* 259:339 */         if (rubroEmpleado == null)
/* 260:    */         {
/* 261:340 */           rubroEmpleado = new RubroEmpleado();
/* 262:341 */           rubroEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 263:342 */           rubroEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/* 264:343 */           rubroEmpleado.setEmpleado(empleado);
/* 265:344 */           rubroEmpleado.setRubro(this.rubro);
/* 266:345 */           rubroEmpleado.setValor(this.rubro.getValor());
/* 267:    */         }
/* 268:    */         else
/* 269:    */         {
/* 270:347 */           rubroEmpleado.setAplicarRubroEmpleado(true);
/* 271:    */         }
/* 272:349 */         this.listaRubroEmpleado.add(rubroEmpleado);
/* 273:    */       }
/* 274:    */     }
/* 275:    */     else
/* 276:    */     {
/* 277:352 */       this.listaRubroEmpleado = null;
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void seleccionar()
/* 282:    */   {
/* 283:360 */     for (RubroEmpleado re : this.listaRubroEmpleado) {
/* 284:361 */       if (this.seleccionarTodos)
/* 285:    */       {
/* 286:362 */         if (re.getEmpleado().isActivo()) {
/* 287:363 */           re.setAplicarRubroEmpleado(true);
/* 288:    */         }
/* 289:    */       }
/* 290:    */       else {
/* 291:366 */         re.setAplicarRubroEmpleado(false);
/* 292:    */       }
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void agregarRubroTodosEmpleados()
/* 297:    */   {
/* 298:376 */     for (RubroEmpleado rubroEmpleado : getListaRubroEmpleado())
/* 299:    */     {
/* 300:377 */       if (!rubroEmpleado.isAplicarRubroEmpleado()) {
/* 301:378 */         rubroEmpleado.setEliminado(true);
/* 302:    */       }
/* 303:380 */       this.servicioRubroEmpleado.guardar(rubroEmpleado);
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   public String validarProvision()
/* 308:    */   {
/* 309:385 */     this.rubro.setIndicadorCalculoIESS(false);
/* 310:386 */     this.rubro.setIndicadorCalculoImpuestoRenta(false);
/* 311:387 */     this.rubro.setOperacion(1);
/* 312:388 */     return "";
/* 313:    */   }
/* 314:    */   
/* 315:    */   public String validarEgreso()
/* 316:    */   {
/* 317:393 */     if (this.rubro.getOperacion() == -1)
/* 318:    */     {
/* 319:394 */       this.rubro.setIndicadorCalculoIESS(false);
/* 320:395 */       this.rubro.setIndicadorCalculoImpuestoRenta(false);
/* 321:396 */       this.rubro.setOperacion(-1);
/* 322:    */     }
/* 323:399 */     return "";
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void actualizarIndicadorProyeccion()
/* 327:    */   {
/* 328:403 */     this.rubro.setIndicadorProyectar(this.rubro.isIndicadorCalculoImpuestoRenta());
/* 329:    */   }
/* 330:    */   
/* 331:    */   public Rubro getRubro()
/* 332:    */   {
/* 333:416 */     if (this.rubro == null) {
/* 334:417 */       this.rubro = new Rubro();
/* 335:    */     }
/* 336:419 */     return this.rubro;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setRubro(Rubro rubro)
/* 340:    */   {
/* 341:429 */     this.rubro = rubro;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public LazyDataModel<Rubro> getListaRubro()
/* 345:    */   {
/* 346:438 */     return this.listaRubro;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void setListaRubro(LazyDataModel<Rubro> listaRubro)
/* 350:    */   {
/* 351:448 */     this.listaRubro = listaRubro;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public String getQuincenaSelecionada()
/* 355:    */   {
/* 356:457 */     this.quincenaSelecionada = null;
/* 357:458 */     if (getRubro().getQuincena() != null) {
/* 358:459 */       this.quincenaSelecionada = ("" + getRubro().getQuincena().getId());
/* 359:    */     }
/* 360:461 */     return this.quincenaSelecionada;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void setQuincenaSelecionada(String quincenaSelecionada)
/* 364:    */   {
/* 365:472 */     if (this.quincenaSelecionada != quincenaSelecionada)
/* 366:    */     {
/* 367:473 */       this.quincenaSelecionada = quincenaSelecionada;
/* 368:474 */       Quincena auxQuincena = null;
/* 369:475 */       if (this.quincenaSelecionada != null)
/* 370:    */       {
/* 371:476 */         int idQuincenaSelecionada = Integer.parseInt(this.quincenaSelecionada);
/* 372:477 */         auxQuincena = this.servicioQuincena.buscarPorId(idQuincenaSelecionada);
/* 373:    */       }
/* 374:479 */       getRubro().setQuincena(auxQuincena);
/* 375:    */     }
/* 376:    */   }
/* 377:    */   
/* 378:    */   public DataTable getDtRubro()
/* 379:    */   {
/* 380:489 */     return this.dtRubro;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setDtRubro(DataTable dtRubro)
/* 384:    */   {
/* 385:499 */     this.dtRubro = dtRubro;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public String getFormulaSelecionada()
/* 389:    */   {
/* 390:508 */     return this.formulaSelecionada;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setFormulaSelecionada(String formulaSelecionada)
/* 394:    */   {
/* 395:519 */     this.formulaSelecionada = formulaSelecionada;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public List<Identificador> getListaMetodosFormulaRol()
/* 399:    */   {
/* 400:530 */     if (this.listaMetodosFormulaRol == null)
/* 401:    */     {
/* 402:531 */       this.listaMetodosFormulaRol = new ArrayList();
/* 403:532 */       Class clase = FuncionRol.class;
/* 404:533 */       for (Method method : clase.getDeclaredMethods()) {
/* 405:535 */         for (Annotation annotation : method.getAnnotations()) {
/* 406:536 */           if ((annotation instanceof IdentificadorClase))
/* 407:    */           {
/* 408:537 */             IdentificadorClase identificadorClase = (IdentificadorClase)annotation;
/* 409:538 */             Identificador identificador = new Identificador();
/* 410:539 */             identificador.setLabel(identificadorClase.label());
/* 411:540 */             identificador.setValue(identificadorClase.value());
/* 412:541 */             this.listaMetodosFormulaRol.add(identificador);
/* 413:    */           }
/* 414:    */         }
/* 415:    */       }
/* 416:    */     }
/* 417:546 */     return this.listaMetodosFormulaRol;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setListaMetodosFormulaRol(List<Identificador> listaMetodosFormulaRol)
/* 421:    */   {
/* 422:556 */     this.listaMetodosFormulaRol = listaMetodosFormulaRol;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public List<Rubro> getListaRubroCombo()
/* 426:    */   {
/* 427:565 */     if (this.listaRubroCombo == null) {
/* 428:566 */       this.listaRubroCombo = this.servicioRubro.obtenerListaCombo("nombre", true, null);
/* 429:    */     }
/* 430:568 */     return this.listaRubroCombo;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public DataTable getDtEmpleado()
/* 434:    */   {
/* 435:577 */     return this.dtEmpleado;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void setDtEmpleado(DataTable dtEmpleado)
/* 439:    */   {
/* 440:587 */     this.dtEmpleado = dtEmpleado;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public boolean isAplicarRubroAEmpleados()
/* 444:    */   {
/* 445:596 */     return this.aplicarRubroAEmpleados;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void setAplicarRubroAEmpleados(boolean aplicarRubroAEmpleados)
/* 449:    */   {
/* 450:606 */     this.aplicarRubroAEmpleados = aplicarRubroAEmpleados;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public boolean isSeleccionarTodos()
/* 454:    */   {
/* 455:615 */     return this.seleccionarTodos;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void setSeleccionarTodos(boolean seleccionarTodos)
/* 459:    */   {
/* 460:625 */     this.seleccionarTodos = seleccionarTodos;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public List<RubroEmpleado> getListaRubroEmpleado()
/* 464:    */   {
/* 465:634 */     if (this.listaRubroEmpleado == null) {
/* 466:635 */       this.listaRubroEmpleado = new ArrayList();
/* 467:    */     }
/* 468:637 */     return this.listaRubroEmpleado;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void setListaRubroEmpleado(List<RubroEmpleado> listaRubroEmpleado)
/* 472:    */   {
/* 473:647 */     this.listaRubroEmpleado = listaRubroEmpleado;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public List<SelectItem> getListaTipo()
/* 477:    */   {
/* 478:654 */     if (this.listaTipo == null)
/* 479:    */     {
/* 480:655 */       this.listaTipo = new ArrayList();
/* 481:656 */       for (TipoRubroEnum tipo : TipoRubroEnum.values())
/* 482:    */       {
/* 483:657 */         SelectItem item = new SelectItem(tipo, tipo.name());
/* 484:658 */         this.listaTipo.add(item);
/* 485:    */       }
/* 486:    */     }
/* 487:661 */     return this.listaTipo;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void setListaTipo(List<SelectItem> listaTipo)
/* 491:    */   {
/* 492:669 */     this.listaTipo = listaTipo;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public List<SelectItem> getListaMes()
/* 496:    */   {
/* 497:676 */     if (this.listaMes == null) {
/* 498:677 */       this.listaMes = generaMeses("TODOS");
/* 499:    */     }
/* 500:679 */     return this.listaMes;
/* 501:    */   }
/* 502:    */   
/* 503:    */   private List<SelectItem> generaMeses(String seleccione)
/* 504:    */   {
/* 505:684 */     List<SelectItem> lista = new ArrayList();
/* 506:685 */     SelectItem item = new SelectItem(Integer.valueOf(0), seleccione);
/* 507:686 */     lista.add(item);
/* 508:687 */     for (Mes t : Mes.values())
/* 509:    */     {
/* 510:688 */       item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 511:689 */       lista.add(item);
/* 512:    */     }
/* 513:691 */     return lista;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public List<SelectItem> getListaMesPago()
/* 517:    */   {
/* 518:695 */     if (this.listaMesPago == null) {
/* 519:696 */       this.listaMesPago = generaMeses("QUINCENA");
/* 520:    */     }
/* 521:698 */     return this.listaMesPago;
/* 522:    */   }
/* 523:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.RubroBean
 * JD-Core Version:    0.7.0.1
 */