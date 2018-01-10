/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*   9:    */ import com.asinfo.as2.entities.produccion.OperacionOrdenFabricacion;
/*  10:    */ import com.asinfo.as2.entities.produccion.OperacionProduccion;
/*  11:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  12:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*  13:    */ import com.asinfo.as2.entities.produccion.TareaProduccion;
/*  14:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  15:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  16:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.util.RutaArchivo;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  21:    */ import java.io.BufferedInputStream;
/*  22:    */ import java.io.InputStream;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Collection;
/*  26:    */ import java.util.Date;
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
/*  37:    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  38:    */ import org.primefaces.component.datatable.DataTable;
/*  39:    */ import org.primefaces.event.FileUploadEvent;
/*  40:    */ import org.primefaces.model.StreamedContent;
/*  41:    */ import org.primefaces.model.UploadedFile;
/*  42:    */ 
/*  43:    */ @ManagedBean
/*  44:    */ @ViewScoped
/*  45:    */ public class OperacionOrdenFabricacionBean
/*  46:    */   extends PageControllerAS2
/*  47:    */ {
/*  48:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  49:    */   @EJB
/*  50:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  51:    */   private int anio;
/*  52:    */   private int mes;
/*  53:    */   private StreamedContent file;
/*  54:    */   private List<OrdenFabricacion> listaOrdenFabricacion;
/*  55:    */   private List<OrdenFabricacion> listaOrdenFabricacionFiltrado;
/*  56:    */   private Map<Integer, List<OperacionOrdenFabricacion>> mapaOperacionOrdenFabricacionEliminados;
/*  57:    */   private DataTable dtOrdenFabricacion;
/*  58:    */   private DataTable dtOperacionOrdenFabricacion;
/*  59:    */   private List<SelectItem> listaMesCombo;
/*  60:    */   
/*  61:    */   @PostConstruct
/*  62:    */   public void init()
/*  63:    */   {
/*  64: 85 */     setAnio(FuncionesUtiles.getAnio(new Date()));
/*  65: 86 */     setMes(FuncionesUtiles.getMes(new Date()));
/*  66: 87 */     setEditado(true);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String editar()
/*  70:    */   {
/*  71:100 */     return "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String guardar()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:111 */       agregarOperacionOrdenFabricacion();
/*  79:112 */       for (OrdenFabricacion ordenFabricacion : getListaOrdenFabricacion()) {
/*  80:113 */         for (OperacionOrdenFabricacion operacionOrdenFabricacion : ordenFabricacion.getListaOperacionOrdenFabricacion()) {
/*  81:114 */           this.servicioOrdenFabricacion.guardarOperacionOrdenFrabricacion(operacionOrdenFabricacion);
/*  82:    */         }
/*  83:    */       }
/*  84:117 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  85:118 */       limpiar();
/*  86:119 */       setEditado(true);
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  91:123 */       LOG.error("ERROR AL GUARDAR DATOS COSTOS DE FABRICACION", e);
/*  92:    */     }
/*  93:125 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String cancelar()
/*  97:    */   {
/*  98:134 */     setEditado(true);
/*  99:135 */     limpiar();
/* 100:136 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String eliminar()
/* 104:    */   {
/* 105:145 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String cargarDatos()
/* 109:    */   {
/* 110:154 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String limpiar()
/* 114:    */   {
/* 115:163 */     this.listaOrdenFabricacion = null;
/* 116:164 */     this.listaOrdenFabricacionFiltrado = null;
/* 117:165 */     this.mapaOperacionOrdenFabricacionEliminados = null;
/* 118:166 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void procesar()
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:173 */       List<OperacionOrdenFabricacion> listaOperacionOrdenFabricacion = this.servicioOrdenFabricacion.getOperacionOrdenFabricacionActualizado(AppUtil.getOrganizacion().getId(), null, this.anio, this.mes, isCosteoSubOrdenes());
/* 126:174 */       if (listaOperacionOrdenFabricacion.size() == 0)
/* 127:    */       {
/* 128:175 */         limpiar();
/* 129:176 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/* 130:177 */         return;
/* 131:    */       }
/* 132:181 */       this.listaOrdenFabricacion = new ArrayList();
/* 133:182 */       this.mapaOperacionOrdenFabricacionEliminados = new HashMap();
/* 134:    */       
/* 135:184 */       Map<Integer, OrdenFabricacion> mapaOrdenFabricacion = new HashMap();
/* 136:186 */       for (OperacionOrdenFabricacion operacionOrdenFabricacion : listaOperacionOrdenFabricacion) {
/* 137:188 */         if (!operacionOrdenFabricacion.isEliminado())
/* 138:    */         {
/* 139:191 */           if (!mapaOrdenFabricacion.containsKey(Integer.valueOf(operacionOrdenFabricacion.getOrdenFabricacion().getId()))) {
/* 140:192 */             mapaOrdenFabricacion.put(Integer.valueOf(operacionOrdenFabricacion.getOrdenFabricacion().getId()), operacionOrdenFabricacion
/* 141:193 */               .getOrdenFabricacion());
/* 142:    */           }
/* 143:195 */           OrdenFabricacion ordenFabricacion = (OrdenFabricacion)mapaOrdenFabricacion.get(Integer.valueOf(operacionOrdenFabricacion.getOrdenFabricacion().getId()));
/* 144:196 */           ordenFabricacion.getListaOperacionOrdenFabricacion().add(operacionOrdenFabricacion);
/* 145:    */         }
/* 146:    */         else
/* 147:    */         {
/* 148:202 */           List<OperacionOrdenFabricacion> listaEliminados = (List)this.mapaOperacionOrdenFabricacionEliminados.get(Integer.valueOf(operacionOrdenFabricacion.getOrdenFabricacion().getId()));
/* 149:203 */           if (listaEliminados == null) {
/* 150:204 */             listaEliminados = new ArrayList();
/* 151:    */           }
/* 152:206 */           listaEliminados.add(operacionOrdenFabricacion);
/* 153:207 */           this.mapaOperacionOrdenFabricacionEliminados.put(Integer.valueOf(operacionOrdenFabricacion.getOrdenFabricacion().getId()), listaEliminados);
/* 154:    */         }
/* 155:    */       }
/* 156:212 */       this.listaOrdenFabricacion.addAll(mapaOrdenFabricacion.values());
/* 157:    */       
/* 158:214 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/* 159:    */     }
/* 160:    */     catch (Exception e)
/* 161:    */     {
/* 162:217 */       e.printStackTrace();
/* 163:218 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   private void agregarOperacionOrdenFabricacion()
/* 168:    */   {
/* 169:226 */     for (OrdenFabricacion ordenFabricacion : this.listaOrdenFabricacion) {
/* 170:227 */       if (this.mapaOperacionOrdenFabricacionEliminados.containsKey(Integer.valueOf(ordenFabricacion.getId()))) {
/* 171:228 */         ordenFabricacion.getListaOperacionProduccion().addAll((Collection)this.mapaOperacionOrdenFabricacionEliminados.get(Integer.valueOf(ordenFabricacion.getId())));
/* 172:    */       }
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public StreamedContent getFile()
/* 177:    */   {
/* 178:234 */     this.file = generarPlantilla();
/* 179:235 */     return this.file;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public StreamedContent generarPlantilla()
/* 183:    */   {
/* 184:    */     try
/* 185:    */     {
/* 186:243 */       int contador = 1;
/* 187:    */       
/* 188:    */ 
/* 189:246 */       String rutaArchivo = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "plantillas", "registroHorasProduccion");
/* 190:247 */       String nombreArchivo = "registroHorasProduccion.xls";
/* 191:    */       
/* 192:    */ 
/* 193:250 */       List<Object[]> listaDatos = new ArrayList();
/* 194:251 */       Object[] datos = new Object[16];
/* 195:252 */       datos[0] = "No.";
/* 196:253 */       datos[1] = "Codigo Orden";
/* 197:254 */       datos[2] = "Numero";
/* 198:255 */       datos[3] = "Fecha Lanzamiento";
/* 199:256 */       datos[4] = "Codigo Producto";
/* 200:257 */       datos[5] = "Producto";
/* 201:258 */       datos[6] = "Ruta Fabricacion";
/* 202:259 */       datos[7] = "Estado";
/* 203:260 */       datos[8] = "Codigo Operacion";
/* 204:261 */       datos[9] = "Codigo Tarea";
/* 205:262 */       datos[10] = "Tarea";
/* 206:263 */       datos[11] = "Maquina";
/* 207:264 */       datos[12] = "Centro Trabajo";
/* 208:265 */       datos[13] = "Automatico";
/* 209:266 */       datos[14] = "Horas Hombre";
/* 210:267 */       datos[15] = "Horas Maquina";
/* 211:268 */       listaDatos.add(datos);
/* 212:    */       
/* 213:    */ 
/* 214:271 */       FuncionesUtiles.ordenaLista(this.listaOrdenFabricacion, "numero");
/* 215:272 */       contador = 1;
/* 216:273 */       for (Iterator localIterator1 = this.listaOrdenFabricacion.iterator(); localIterator1.hasNext();)
/* 217:    */       {
/* 218:273 */         ordenFabricacion = (OrdenFabricacion)localIterator1.next();
/* 219:275 */         for (OperacionOrdenFabricacion operacionOrdenFabricacion : ordenFabricacion.getListaOperacionOrdenFabricacion()) {
/* 220:276 */           if (!operacionOrdenFabricacion.getOperacionProduccion().isIndicadorFijo())
/* 221:    */           {
/* 222:277 */             datos = new Object[16];
/* 223:278 */             datos[0] = Integer.valueOf(contador);
/* 224:279 */             datos[1] = Integer.valueOf(ordenFabricacion.getId());
/* 225:280 */             datos[2] = ordenFabricacion.getNumero();
/* 226:281 */             datos[3] = ordenFabricacion.getFechaLanzamiento();
/* 227:282 */             datos[4] = ordenFabricacion.getProducto().getCodigo();
/* 228:283 */             datos[5] = ordenFabricacion.getProducto().getNombre();
/* 229:284 */             datos[6] = ordenFabricacion.getRutaFabricacion().getNombre();
/* 230:285 */             datos[7] = ordenFabricacion.getEstado().toString();
/* 231:286 */             datos[8] = Integer.valueOf(operacionOrdenFabricacion.getOperacionProduccion().getId());
/* 232:287 */             datos[9] = operacionOrdenFabricacion.getOperacionProduccion().getTareaProduccion().getCodigo();
/* 233:288 */             datos[10] = operacionOrdenFabricacion.getOperacionProduccion().getTareaProduccion().getNombre();
/* 234:289 */             datos[11] = operacionOrdenFabricacion.getOperacionProduccion().getMaquina().getNombre();
/* 235:290 */             datos[12] = operacionOrdenFabricacion.getOperacionProduccion().getCentroTrabajo().getNombre();
/* 236:291 */             datos[13] = Boolean.valueOf(operacionOrdenFabricacion.getOperacionProduccion().isIndicadorAutomatico());
/* 237:292 */             datos[14] = Integer.valueOf(operacionOrdenFabricacion.getOperacionProduccion().getNumeroPersonas());
/* 238:293 */             datos[15] = Integer.valueOf(operacionOrdenFabricacion.getOperacionProduccion().getNumeroMaquinas());
/* 239:294 */             listaDatos.add(datos);
/* 240:295 */             contador++;
/* 241:    */           }
/* 242:    */         }
/* 243:    */       }
/* 244:    */       OrdenFabricacion ordenFabricacion;
/* 245:301 */       FuncionesUtiles.crearExcel(rutaArchivo + nombreArchivo, listaDatos, 16, true);
/* 246:    */       
/* 247:    */ 
/* 248:304 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivo + nombreArchivo, "application/xls", nombreArchivo);
/* 249:    */       
/* 250:306 */       addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_generado"));
/* 251:    */     }
/* 252:    */     catch (Exception e)
/* 253:    */     {
/* 254:309 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 255:310 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 256:    */     }
/* 257:313 */     return this.file;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String cargarRegistroHorasDistribucion(FileUploadEvent event)
/* 261:    */   {
/* 262:    */     try
/* 263:    */     {
/* 264:321 */       String fileName = "registro_horas_produccion" + event.getFile().getFileName();
/* 265:322 */       InputStream inputStream = new BufferedInputStream(event.getFile().getInputstream());
/* 266:    */       
/* 267:    */ 
/* 268:325 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(AppUtil.getOrganizacion().getId(), fileName, inputStream, 2, 0);
/* 269:    */       
/* 270:    */ 
/* 271:328 */       HashMap<String, BigDecimal[]> horasProduccion = new HashMap();
/* 272:329 */       for (HSSFCell[] fila : datos)
/* 273:    */       {
/* 274:331 */         Integer idOrdenFabricacion = Integer.valueOf(Double.valueOf(fila[1].getNumericCellValue()).intValue());
/* 275:332 */         Integer idOperacionOrdenProduccion = Integer.valueOf(Double.valueOf(fila[8].getNumericCellValue()).intValue());
/* 276:333 */         Double horasHombre = Double.valueOf(fila[14].getNumericCellValue());
/* 277:334 */         Double horasMaquina = Double.valueOf(fila[15].getNumericCellValue());
/* 278:    */         
/* 279:336 */         String clave = idOrdenFabricacion + "~" + idOperacionOrdenProduccion;
/* 280:    */         
/* 281:338 */         BigDecimal[] horas = (BigDecimal[])horasProduccion.get(clave);
/* 282:339 */         if (horas == null) {
/* 283:340 */           horas = new BigDecimal[2];
/* 284:    */         }
/* 285:342 */         horas[0] = new BigDecimal(horasHombre.doubleValue());
/* 286:343 */         horas[1] = new BigDecimal(horasMaquina.doubleValue());
/* 287:    */         
/* 288:345 */         horasProduccion.put(clave, horas);
/* 289:    */       }
/* 290:349 */       for (??? = this.listaOrdenFabricacion.iterator(); ((Iterator)???).hasNext();)
/* 291:    */       {
/* 292:349 */         ordenFabricacion = (OrdenFabricacion)((Iterator)???).next();
/* 293:350 */         for (OperacionOrdenFabricacion operacionOrdenFabricacion : ordenFabricacion.getListaOperacionOrdenFabricacion())
/* 294:    */         {
/* 295:351 */           String clave = ordenFabricacion.getId() + "~" + operacionOrdenFabricacion.getOperacionProduccion().getId();
/* 296:352 */           if (horasProduccion.containsKey(clave))
/* 297:    */           {
/* 298:353 */             BigDecimal[] horas = (BigDecimal[])horasProduccion.get(clave);
/* 299:354 */             operacionOrdenFabricacion.setHorasHombre(horas[0]);
/* 300:355 */             operacionOrdenFabricacion.setHorasMaquina(horas[1]);
/* 301:    */           }
/* 302:    */         }
/* 303:    */       }
/* 304:    */       OrdenFabricacion ordenFabricacion;
/* 305:360 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 306:    */     }
/* 307:    */     catch (Exception e)
/* 308:    */     {
/* 309:363 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 310:364 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 311:    */     }
/* 312:366 */     return null;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public List<SelectItem> getListaMesCombo()
/* 316:    */   {
/* 317:370 */     if (this.listaMesCombo == null)
/* 318:    */     {
/* 319:371 */       this.listaMesCombo = new ArrayList();
/* 320:372 */       for (Mes mes : Mes.values())
/* 321:    */       {
/* 322:373 */         SelectItem itemMes = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 323:374 */         this.listaMesCombo.add(itemMes);
/* 324:    */       }
/* 325:    */     }
/* 326:377 */     return this.listaMesCombo;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setListaMesCombo(List<SelectItem> listaMesCombo)
/* 330:    */   {
/* 331:381 */     this.listaMesCombo = listaMesCombo;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public int getAnio()
/* 335:    */   {
/* 336:385 */     return this.anio;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setAnio(int anio)
/* 340:    */   {
/* 341:389 */     this.anio = anio;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public int getMes()
/* 345:    */   {
/* 346:393 */     return this.mes;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void setMes(int mes)
/* 350:    */   {
/* 351:397 */     this.mes = mes;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public List<OrdenFabricacion> getListaOrdenFabricacion()
/* 355:    */   {
/* 356:401 */     return this.listaOrdenFabricacion;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void setListaOrdenFabricacion(List<OrdenFabricacion> listaOrdenFabricacion)
/* 360:    */   {
/* 361:405 */     this.listaOrdenFabricacion = listaOrdenFabricacion;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public List<OrdenFabricacion> getListaOrdenFabricacionFiltrado()
/* 365:    */   {
/* 366:409 */     return this.listaOrdenFabricacionFiltrado;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setListaOrdenFabricacionFiltrado(List<OrdenFabricacion> listaOrdenFabricacionFiltrado)
/* 370:    */   {
/* 371:413 */     this.listaOrdenFabricacionFiltrado = listaOrdenFabricacionFiltrado;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public DataTable getDtOrdenFabricacion()
/* 375:    */   {
/* 376:417 */     return this.dtOrdenFabricacion;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setDtOrdenFabricacion(DataTable dtOrdenFabricacion)
/* 380:    */   {
/* 381:421 */     this.dtOrdenFabricacion = dtOrdenFabricacion;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public DataTable getDtOperacionOrdenFabricacion()
/* 385:    */   {
/* 386:425 */     return this.dtOperacionOrdenFabricacion;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setDtOperacionOrdenFabricacion(DataTable dtOperacionOrdenFabricacion)
/* 390:    */   {
/* 391:429 */     this.dtOperacionOrdenFabricacion = dtOperacionOrdenFabricacion;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public boolean isCosteoSubOrdenes()
/* 395:    */   {
/* 396:433 */     return ParametrosSistema.isCosteoSubOrdenes(AppUtil.getOrganizacion().getId()).booleanValue();
/* 397:    */   }
/* 398:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.OperacionOrdenFabricacionBean
 * JD-Core Version:    0.7.0.1
 */