/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Rubro;
/*  10:    */ import com.asinfo.as2.entities.RubroEmpleado;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  13:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  14:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.util.RutaArchivo;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import java.io.BufferedInputStream;
/*  19:    */ import java.io.InputStream;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.Iterator;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import java.util.TreeMap;
/*  26:    */ import java.util.Vector;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ import org.primefaces.component.datatable.DataTable;
/*  34:    */ import org.primefaces.event.FileUploadEvent;
/*  35:    */ import org.primefaces.model.LazyDataModel;
/*  36:    */ import org.primefaces.model.SortOrder;
/*  37:    */ import org.primefaces.model.StreamedContent;
/*  38:    */ import org.primefaces.model.UploadedFile;
/*  39:    */ 
/*  40:    */ @ManagedBean
/*  41:    */ @ViewScoped
/*  42:    */ public class RubroEmpleadoBean
/*  43:    */   extends PageControllerAS2
/*  44:    */ {
/*  45:    */   private static final long serialVersionUID = 1L;
/*  46:    */   @EJB
/*  47:    */   ServicioEmpleado servicioEmpleado;
/*  48:    */   @EJB
/*  49:    */   ServicioRubro servicioRubro;
/*  50:    */   @EJB
/*  51:    */   ServicioRubroEmpleado servicioRubroEmpleado;
/*  52:    */   @EJB
/*  53:    */   private ServicioMigracion servicioMigracion;
/*  54:    */   private RubroEmpleado rubroEmpleado;
/*  55: 75 */   private Empleado empleadoSeleccionado = new Empleado();
/*  56: 76 */   private Empleado empleado = new Empleado();
/*  57:    */   private Rubro rubro;
/*  58:    */   private List<Rubro> listaRubro;
/*  59:    */   private List<Rubro> listaRubroSeleccionado;
/*  60:    */   private LazyDataModel<Empleado> listaEmpleado;
/*  61: 85 */   private HashMap<Integer, RubroEmpleado> mapaRubroEmpleado = new HashMap();
/*  62:    */   private DataTable dtRubroEmpleado;
/*  63:    */   private DataTable dtRubro;
/*  64:    */   private DataTable dtEmpleado;
/*  65:    */   private SelectItem[] listaActivoItem;
/*  66:    */   private StreamedContent file;
/*  67:    */   private static final String TIPO_CONTENIDO = "application/xls";
/*  68:    */   
/*  69:    */   @PostConstruct
/*  70:    */   public void init()
/*  71:    */   {
/*  72:103 */     this.listaEmpleado = new LazyDataModel()
/*  73:    */     {
/*  74:    */       private static final long serialVersionUID = 1L;
/*  75:    */       
/*  76:    */       public List<Empleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  77:    */       {
/*  78:110 */         List<Empleado> lista = new ArrayList();
/*  79:111 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  80:112 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  81:    */         
/*  82:114 */         lista = RubroEmpleadoBean.this.servicioEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  83:115 */         RubroEmpleadoBean.this.listaEmpleado.setRowCount(RubroEmpleadoBean.this.servicioEmpleado.contarPorCriterio(filters));
/*  84:    */         
/*  85:117 */         return lista;
/*  86:    */       }
/*  87:    */     };
/*  88:    */   }
/*  89:    */   
/*  90:    */   private void crearRubroEmpleado() {}
/*  91:    */   
/*  92:    */   public String crear()
/*  93:    */   {
/*  94:140 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  95:141 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String editar()
/*  99:    */   {
/* 100:152 */     setEditado(true);
/* 101:153 */     return actualizaEmpleado();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String guardar()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:163 */       for (RubroEmpleado rubroEmpleadoTmp : this.empleado.getListaRubroEmpleado()) {
/* 109:164 */         this.servicioRubroEmpleado.guardar(rubroEmpleadoTmp);
/* 110:    */       }
/* 111:166 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 112:167 */       limpiar();
/* 113:    */     }
/* 114:    */     catch (Exception e)
/* 115:    */     {
/* 116:169 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 117:170 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 118:    */     }
/* 119:172 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String eliminar()
/* 123:    */   {
/* 124:181 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String cargarDatos()
/* 128:    */   {
/* 129:190 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String limpiar()
/* 133:    */   {
/* 134:199 */     setEditado(false);
/* 135:200 */     crearRubroEmpleado();
/* 136:201 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String asignaRubroEmpleado()
/* 140:    */   {
/* 141:205 */     RubroEmpleado rubroEmpleadoTmp = new RubroEmpleado();
/* 142:206 */     rubroEmpleadoTmp.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 143:207 */     rubroEmpleadoTmp.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 144:208 */     this.rubro = ((Rubro)this.dtRubro.getRowData());
/* 145:209 */     rubroEmpleadoTmp.setRubro(this.rubro);
/* 146:210 */     rubroEmpleadoTmp.setValor(this.rubro.getValor());
/* 147:211 */     rubroEmpleadoTmp.setEmpleado(this.empleadoSeleccionado);
/* 148:212 */     this.mapaRubroEmpleado.put(Integer.valueOf(this.rubro.getIdRubro()), rubroEmpleadoTmp);
/* 149:213 */     this.empleado.getListaRubroEmpleado().add(rubroEmpleadoTmp);
/* 150:214 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String actualizaEmpleado()
/* 154:    */   {
/* 155:219 */     this.empleado = this.servicioEmpleado.cargarRubros(this.empleadoSeleccionado.getIdEmpleado());
/* 156:220 */     this.mapaRubroEmpleado = new HashMap();
/* 157:221 */     for (RubroEmpleado rubroEmpleadoTmp : this.empleado.getListaRubroEmpleado()) {
/* 158:222 */       this.mapaRubroEmpleado.put(Integer.valueOf(rubroEmpleadoTmp.getRubro().getIdRubro()), rubroEmpleadoTmp);
/* 159:    */     }
/* 160:224 */     return "";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String eliminarRubroEmpleado()
/* 164:    */   {
/* 165:233 */     RubroEmpleado r = (RubroEmpleado)this.dtRubroEmpleado.getRowData();
/* 166:234 */     r.setEliminado(true);
/* 167:235 */     this.mapaRubroEmpleado.remove(Integer.valueOf(r.getRubro().getIdRubro()));
/* 168:236 */     return "";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List<RubroEmpleado> getListaRubroEmpleado()
/* 172:    */   {
/* 173:240 */     List<RubroEmpleado> lista = new ArrayList();
/* 174:241 */     for (RubroEmpleado rubroEmpleado : this.empleado.getListaRubroEmpleado()) {
/* 175:242 */       if (!rubroEmpleado.isEliminado()) {
/* 176:243 */         lista.add(rubroEmpleado);
/* 177:    */       }
/* 178:    */     }
/* 179:246 */     FuncionesUtiles.ordenaLista(lista, "nombreRubro");
/* 180:247 */     return lista;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public RubroEmpleado getRubroEmpleado()
/* 184:    */   {
/* 185:260 */     return this.rubroEmpleado;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setRubroEmpleado(RubroEmpleado rubroEmpleado)
/* 189:    */   {
/* 190:270 */     this.rubroEmpleado = rubroEmpleado;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public LazyDataModel<Empleado> getListaEmpleado()
/* 194:    */   {
/* 195:279 */     return this.listaEmpleado;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setListaEmpleado(LazyDataModel<Empleado> listaEmpleado)
/* 199:    */   {
/* 200:289 */     this.listaEmpleado = listaEmpleado;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public List<Rubro> getListaRubro()
/* 204:    */   {
/* 205:299 */     if (this.listaRubro == null) {
/* 206:300 */       this.listaRubro = this.servicioRubro.obtenerListaCombo(null, true, null);
/* 207:    */     }
/* 208:302 */     List<Rubro> lista = new ArrayList();
/* 209:303 */     for (Rubro rubro : this.listaRubro) {
/* 210:304 */       if (!this.mapaRubroEmpleado.containsKey(Integer.valueOf(rubro.getIdRubro()))) {
/* 211:305 */         lista.add(rubro);
/* 212:    */       }
/* 213:    */     }
/* 214:308 */     FuncionesUtiles.ordenaLista(lista, "nombre");
/* 215:    */     
/* 216:310 */     return lista;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public DataTable getDtRubroEmpleado()
/* 220:    */   {
/* 221:320 */     return this.dtRubroEmpleado;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setDtRubroEmpleado(DataTable dtRubroEmpleado)
/* 225:    */   {
/* 226:330 */     this.dtRubroEmpleado = dtRubroEmpleado;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public DataTable getDtRubro()
/* 230:    */   {
/* 231:339 */     return this.dtRubro;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setDtRubro(DataTable dtRubro)
/* 235:    */   {
/* 236:349 */     this.dtRubro = dtRubro;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public DataTable getDtEmpleado()
/* 240:    */   {
/* 241:358 */     return this.dtEmpleado;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setDtEmpleado(DataTable dtEmpleado)
/* 245:    */   {
/* 246:368 */     this.dtEmpleado = dtEmpleado;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Empleado getEmpleadoSeleccionado()
/* 250:    */   {
/* 251:377 */     return this.empleadoSeleccionado;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setEmpleadoSeleccionado(Empleado empleado)
/* 255:    */   {
/* 256:387 */     this.empleadoSeleccionado = empleado;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public Rubro getRubro()
/* 260:    */   {
/* 261:396 */     if (this.rubro == null) {
/* 262:397 */       this.rubro = new Rubro();
/* 263:    */     }
/* 264:399 */     return this.rubro;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setRubro(Rubro rubro)
/* 268:    */   {
/* 269:409 */     this.rubro = rubro;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setListaRubro(List<Rubro> listaRubro)
/* 273:    */   {
/* 274:419 */     this.listaRubro = listaRubro;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public SelectItem[] getListaActivoItem()
/* 278:    */   {
/* 279:429 */     if (this.listaActivoItem == null)
/* 280:    */     {
/* 281:431 */       List<SelectItem> lista = new ArrayList();
/* 282:    */       
/* 283:433 */       lista.add(new SelectItem("", ""));
/* 284:434 */       lista.add(new SelectItem("true", "true"));
/* 285:435 */       lista.add(new SelectItem("false", "false"));
/* 286:    */       
/* 287:437 */       this.listaActivoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 288:    */     }
/* 289:440 */     return this.listaActivoItem;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setListaActivoItem(SelectItem[] listaActivoItem)
/* 293:    */   {
/* 294:450 */     this.listaActivoItem = listaActivoItem;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public StreamedContent getFile()
/* 298:    */   {
/* 299:454 */     this.file = generarPlantilla();
/* 300:455 */     return this.file;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public StreamedContent generarPlantilla()
/* 304:    */   {
/* 305:    */     try
/* 306:    */     {
/* 307:464 */       Vector v = new Vector();
/* 308:    */       
/* 309:466 */       String cadenaCabecera = "";
/* 310:467 */       String cadenaCodigoRubro = "";
/* 311:468 */       String cadenaNombreRubro = "";
/* 312:469 */       HashMap<String, String> filtersRubro = new HashMap();
/* 313:470 */       List<Rubro> listaRubros = new ArrayList();
/* 314:471 */       if (this.listaRubroSeleccionado.size() > 0)
/* 315:    */       {
/* 316:472 */         for (Rubro r : this.listaRubroSeleccionado) {
/* 317:473 */           listaRubros.add(r);
/* 318:    */         }
/* 319:476 */         for (Rubro rubro : listaRubros)
/* 320:    */         {
/* 321:477 */           cadenaCabecera = cadenaCabecera + "-,";
/* 322:478 */           cadenaCodigoRubro = cadenaCodigoRubro + rubro.getId() + ",";
/* 323:479 */           cadenaNombreRubro = cadenaNombreRubro + rubro.getNombre() + ",";
/* 324:    */         }
/* 325:482 */         if (listaRubros.size() > 0)
/* 326:    */         {
/* 327:483 */           cadenaCabecera = cadenaCabecera.substring(0, cadenaCabecera.length());
/* 328:484 */           cadenaCodigoRubro = cadenaCodigoRubro.substring(0, cadenaCodigoRubro.length());
/* 329:485 */           cadenaNombreRubro = cadenaNombreRubro.substring(0, cadenaNombreRubro.length());
/* 330:    */         }
/* 331:488 */         v.addElement("-,-,-,-," + cadenaCabecera);
/* 332:489 */         v.addElement("-,-,-,-," + cadenaCodigoRubro);
/* 333:490 */         String rubrosActuales = "";
/* 334:491 */         v.addElement("Codigo,Identificacion,Apellido,Nombre," + cadenaNombreRubro);
/* 335:492 */         int numeroColumnasFijas = 4;
/* 336:493 */         int numeroColumnas = numeroColumnasFijas + listaRubros.size();
/* 337:    */         
/* 338:495 */         List<RubroEmpleado> listaRubroEmpleado = this.servicioRubroEmpleado.getGenerarRubroEmpleado(listaRubros, AppUtil.getOrganizacion().getIdOrganizacion());
/* 339:    */         
/* 340:497 */         List<Empleado> listaEmpleadoSinRubro = this.servicioRubroEmpleado.getEmpleadoSinRubro(listaRubros, AppUtil.getOrganizacion().getIdOrganizacion());
/* 341:499 */         for (Iterator localIterator2 = listaEmpleadoSinRubro.iterator(); localIterator2.hasNext();)
/* 342:    */         {
/* 343:499 */           empleado = (Empleado)localIterator2.next();
/* 344:500 */           listaRubroEmpleado.add(new RubroEmpleado(empleado));
/* 345:    */         }
/* 346:    */         Empleado empleado;
/* 347:503 */         Object hmEmpleadoRubro = new TreeMap();
/* 348:505 */         for (RubroEmpleado re : listaRubroEmpleado)
/* 349:    */         {
/* 350:506 */           Object[] datos = (Object[])((Map)hmEmpleadoRubro).get(Integer.valueOf(re.getEmpleado().getId()));
/* 351:507 */           if (datos == null)
/* 352:    */           {
/* 353:508 */             datos = new Object[numeroColumnas];
/* 354:509 */             datos[0] = re.getEmpleado().getEmpresa().getCodigo();
/* 355:510 */             datos[1] = re.getEmpleado().getEmpresa().getIdentificacion();
/* 356:511 */             datos[2] = re.getEmpleado().getApellidos();
/* 357:512 */             datos[3] = re.getEmpleado().getNombres();
/* 358:514 */             for (int i = numeroColumnasFijas; i < datos.length; i++) {
/* 359:515 */               datos[i] = "NO";
/* 360:    */             }
/* 361:518 */             ((Map)hmEmpleadoRubro).put(Integer.valueOf(re.getEmpleado().getId()), datos);
/* 362:    */           }
/* 363:520 */           if (re.getRubro() != null)
/* 364:    */           {
/* 365:521 */             int x = listaRubros.indexOf(re.getRubro());
/* 366:522 */             if (x >= 0) {
/* 367:523 */               datos[(numeroColumnasFijas + x)] = "SI";
/* 368:    */             }
/* 369:    */           }
/* 370:    */         }
/* 371:528 */         for (Object[] datos : ((Map)hmEmpleadoRubro).values())
/* 372:    */         {
/* 373:529 */           StringBuilder fila = new StringBuilder();
/* 374:530 */           for (int i = 0; i < datos.length; i++)
/* 375:    */           {
/* 376:531 */             fila.append(datos[i] == null ? "-" : datos[i].toString());
/* 377:532 */             if (i <= datos.length) {
/* 378:533 */               fila.append(",");
/* 379:    */             }
/* 380:    */           }
/* 381:536 */           v.addElement(fila.toString());
/* 382:    */         }
/* 383:539 */         String rutaArchivo = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "plantillas", "listaRubros");
/* 384:540 */         String nombreArchivo = "ListaDeRubros.xls";
/* 385:    */         
/* 386:542 */         FuncionesUtiles.crearExcel(v, "ListaRubros", rutaArchivo, nombreArchivo);
/* 387:543 */         this.file = FuncionesUtiles.descargarArchivo(rutaArchivo + nombreArchivo, "application/xls", nombreArchivo);
/* 388:    */       }
/* 389:    */       else
/* 390:    */       {
/* 391:545 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 392:    */       }
/* 393:    */     }
/* 394:    */     catch (Exception e)
/* 395:    */     {
/* 396:548 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 397:549 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 398:    */     }
/* 399:552 */     return this.file;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public String cargarAsignarRubros(FileUploadEvent event)
/* 403:    */   {
/* 404:    */     try
/* 405:    */     {
/* 406:559 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 407:560 */       this.servicioMigracion.cargarAsignarRubros(AppUtil.getOrganizacion().getId(), input, 1);
/* 408:561 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 409:    */     }
/* 410:    */     catch (Exception e)
/* 411:    */     {
/* 412:563 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 413:564 */       e.printStackTrace();
/* 414:    */     }
/* 415:566 */     return null;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public List<Rubro> getListaRubroSeleccionado()
/* 419:    */   {
/* 420:570 */     return this.listaRubroSeleccionado;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setListaRubroSeleccionado(List<Rubro> listaRubroSeleccionado)
/* 424:    */   {
/* 425:574 */     this.listaRubroSeleccionado = listaRubroSeleccionado;
/* 426:    */   }
/* 427:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.RubroEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */