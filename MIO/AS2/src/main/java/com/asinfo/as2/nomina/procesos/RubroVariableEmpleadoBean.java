/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empleado;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.PagoRol;
/*  11:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  12:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  13:    */ import com.asinfo.as2.entities.Rubro;
/*  14:    */ import com.asinfo.as2.enumeraciones.TipoRubro;
/*  15:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  16:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  17:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleadoRubro;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.util.RutaArchivo;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import java.io.BufferedInputStream;
/*  22:    */ import java.io.InputStream;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import java.util.Vector;
/*  29:    */ import javax.annotation.PostConstruct;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ import org.primefaces.component.datatable.DataTable;
/*  35:    */ import org.primefaces.event.FileUploadEvent;
/*  36:    */ import org.primefaces.event.SelectEvent;
/*  37:    */ import org.primefaces.model.StreamedContent;
/*  38:    */ import org.primefaces.model.UploadedFile;
/*  39:    */ 
/*  40:    */ @ManagedBean
/*  41:    */ @ViewScoped
/*  42:    */ public class RubroVariableEmpleadoBean
/*  43:    */   extends PageControllerAS2
/*  44:    */ {
/*  45:    */   private static final long serialVersionUID = 5005578893311181983L;
/*  46:    */   @EJB
/*  47:    */   ServicioRubro servicioRubro;
/*  48:    */   @EJB
/*  49:    */   ServicioEmpresa servicioEmpresa;
/*  50:    */   @EJB
/*  51:    */   ServicioPagoRol servicioPagoRol;
/*  52:    */   @EJB
/*  53:    */   ServicioMigracion servicioMigracion;
/*  54:    */   @EJB
/*  55:    */   ServicioPagoRolEmpleadoRubro servicioPagoRolEmpleadoRubro;
/*  56:    */   private PagoRol pagoRol;
/*  57: 81 */   private PagoRolEmpleado pagoRolEmpleado = new PagoRolEmpleado();
/*  58: 82 */   private PagoRolEmpleadoRubro pagoRolEmpleadoRubro = new PagoRolEmpleadoRubro();
/*  59: 83 */   private Rubro rubro = null;
/*  60: 84 */   private Empresa empresa = null;
/*  61:    */   private StreamedContent archivo;
/*  62:    */   private static final String TIPO_CONTENIDO = "application/xls";
/*  63:    */   private List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubro;
/*  64:    */   private List<Rubro> listaRubro;
/*  65:    */   private DataTable dtPagoRolEmpleadoRubro;
/*  66:    */   private BigDecimal totalHoras;
/*  67:    */   private BigDecimal totalValor;
/*  68:    */   
/*  69:    */   @PostConstruct
/*  70:    */   public void init()
/*  71:    */   {
/*  72:108 */     this.pagoRol = ((PagoRol)AppUtil.getAtributo("pago_rol"));
/*  73:109 */     cargarDatos();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void generarPlantilla(boolean valor)
/*  77:    */   {
/*  78:    */     try
/*  79:    */     {
/*  80:122 */       v = new Vector();
/*  81:    */       
/*  82:    */ 
/*  83:125 */       v.addElement("-,-,-,-,-,");
/*  84:126 */       v.addElement("-,-,-,-,-,");
/*  85:127 */       v.addElement("Id, Cedula,Nombres,Apellidos,Valor");
/*  86:128 */       linea = "";
/*  87:130 */       for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : this.listaPagoRolEmpleadoRubro)
/*  88:    */       {
/*  89:131 */         if (!pagoRolEmpleadoRubro.isIndicadorAutomatico())
/*  90:    */         {
/*  91:135 */           linea = pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getIdEmpleado() + "," + pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getEmpresa().getIdentificacion().trim() + "," + pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getNombres() + "," + pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getApellidos();
/*  92:136 */           if (valor) {
/*  93:139 */             linea = linea + ",n::" + pagoRolEmpleadoRubro.getValor();
/*  94:    */           } else {
/*  95:141 */             linea = linea + ",n::0";
/*  96:    */           }
/*  97:143 */           v.addElement(linea);
/*  98:    */         }
/*  99:146 */         String rutaArchivo = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "plantillas", "listaEmpleados");
/* 100:147 */         String nombreArchivo = "listaEmpleados.xls";
/* 101:    */         
/* 102:149 */         FuncionesUtiles.crearExcel(v, "listaEmpleados", rutaArchivo, nombreArchivo);
/* 103:150 */         this.archivo = FuncionesUtiles.descargarArchivo(rutaArchivo + nombreArchivo, "application/xls", nombreArchivo);
/* 104:    */       }
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:    */       Vector v;
/* 109:    */       String linea;
/* 110:153 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 111:154 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isEditado()
/* 116:    */   {
/* 117:165 */     return true;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String editar()
/* 121:    */   {
/* 122:177 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String guardar()
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:187 */       List<PagoRolEmpleadoRubro> lista = new ArrayList();
/* 130:188 */       for (PagoRolEmpleadoRubro prer : this.listaPagoRolEmpleadoRubro) {
/* 131:189 */         lista.add(prer);
/* 132:    */       }
/* 133:191 */       this.servicioPagoRolEmpleadoRubro.guardar(lista, this.pagoRol);
/* 134:192 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 135:193 */       return "";
/* 136:    */     }
/* 137:    */     catch (Exception e)
/* 138:    */     {
/* 139:195 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 140:196 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 141:    */     }
/* 142:197 */     return "";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String cancelar()
/* 146:    */   {
/* 147:209 */     super.cancelar();
/* 148:210 */     return "pagoRol?faces-redirect=true";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String eliminar()
/* 152:    */   {
/* 153:219 */     return "";
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String cargarDatos()
/* 157:    */   {
/* 158:229 */     Map<String, String> filters = new HashMap();
/* 159:230 */     cargarFiltros(filters);
/* 160:    */     
/* 161:232 */     String orden = this.empresa != null ? "rubro.nombre" : "pagoRolEmpleado.empleado.apellidos";
/* 162:233 */     this.listaPagoRolEmpleadoRubro = this.servicioPagoRolEmpleadoRubro.obtenerListaCombo(orden, true, filters);
/* 163:234 */     totalizarTiempoValor();
/* 164:235 */     return "";
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String limpiar()
/* 168:    */   {
/* 169:244 */     return "";
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void cargarFiltros(Map<String, String> filters)
/* 173:    */   {
/* 174:258 */     filters.put("rubro.tipoRubro", String.valueOf(TipoRubro.VARIABLE));
/* 175:259 */     if (this.pagoRol != null) {
/* 176:260 */       filters.put("pagoRolEmpleado.pagoRol.idPagoRol", String.valueOf(this.pagoRol.getIdPagoRol()));
/* 177:    */     }
/* 178:262 */     if (this.empresa != null) {
/* 179:263 */       filters.put("pagoRolEmpleado.empleado.empresa.idEmpresa", String.valueOf(this.empresa.getIdEmpresa()));
/* 180:    */     }
/* 181:265 */     if (this.rubro != null) {
/* 182:266 */       filters.put("rubro.idRubro", String.valueOf(this.rubro.getIdRubro()));
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String limpiarFiltroEmpleado()
/* 187:    */   {
/* 188:271 */     this.empresa = null;
/* 189:    */     
/* 190:273 */     cargarDatos();
/* 191:274 */     return "";
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String limpiarFiltroRubro()
/* 195:    */   {
/* 196:278 */     this.rubro = null;
/* 197:279 */     cargarDatos();
/* 198:280 */     return "";
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<Empresa> autocompletarEmpleados(String consulta)
/* 202:    */   {
/* 203:284 */     return this.servicioEmpresa.autocompletarEmpleados(consulta);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<Rubro> autocompletarRubros(String consulta)
/* 207:    */   {
/* 208:295 */     List<Rubro> lista = new ArrayList();
/* 209:296 */     String consulta2 = consulta.toUpperCase();
/* 210:297 */     for (Rubro rubro : getListaRubro()) {
/* 211:298 */       if ((rubro.getCodigo().toUpperCase().contains(consulta2)) || (rubro.getNombre().contains(consulta2))) {
/* 212:299 */         lista.add(rubro);
/* 213:    */       }
/* 214:    */     }
/* 215:302 */     return lista;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void actualizarEmpleado(SelectEvent event)
/* 219:    */   {
/* 220:312 */     Empresa empresa = (Empresa)event.getObject();
/* 221:313 */     setEmpresa(empresa);
/* 222:    */     
/* 223:315 */     cargarDatos();
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void actualizarRubro(SelectEvent event)
/* 227:    */   {
/* 228:326 */     Rubro rubro = (Rubro)event.getObject();
/* 229:327 */     setRubro(rubro);
/* 230:    */     
/* 231:329 */     cargarDatos();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public String cargarRubrosVariables(FileUploadEvent event)
/* 235:    */   {
/* 236:    */     try
/* 237:    */     {
/* 238:336 */       String fileName = "subir_rubro_empleado" + event.getFile().getFileName();
/* 239:337 */       InputStream imInputStream = new BufferedInputStream(event.getFile().getInputstream());
/* 240:338 */       this.servicioMigracion.subidaRubrosVariablesEmpleado(this.pagoRol, this.rubro.getIdRubro(), fileName, imInputStream, 2);
/* 241:339 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 242:340 */       cargarDatos();
/* 243:341 */       totalizarTiempoValor();
/* 244:    */     }
/* 245:    */     catch (Exception e)
/* 246:    */     {
/* 247:343 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 248:344 */       e.printStackTrace();
/* 249:    */     }
/* 250:346 */     return null;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void totalizarTiempoValor()
/* 254:    */   {
/* 255:352 */     this.totalHoras = BigDecimal.ZERO;
/* 256:353 */     this.totalValor = BigDecimal.ZERO;
/* 257:354 */     List<PagoRolEmpleadoRubro> lista = getListaPagoRolEmpleadoRubro();
/* 258:355 */     for (PagoRolEmpleadoRubro prer : lista)
/* 259:    */     {
/* 260:356 */       if (prer.getTiempo() != null) {
/* 261:357 */         this.totalHoras = this.totalHoras.add(prer.getTiempo());
/* 262:    */       }
/* 263:359 */       if (prer.getValor() != null) {
/* 264:360 */         this.totalValor = this.totalValor.add(prer.getValor());
/* 265:    */       }
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void noProcesarRubroListener(PagoRolEmpleadoRubro prer)
/* 270:    */   {
/* 271:366 */     List<PagoRolEmpleadoRubro> lista = new ArrayList();
/* 272:367 */     lista.add(prer);
/* 273:368 */     this.servicioPagoRolEmpleadoRubro.guardar(lista, this.pagoRol);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public PagoRol getPagoRol()
/* 277:    */   {
/* 278:382 */     return this.pagoRol;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setPagoRol(PagoRol pagoRol)
/* 282:    */   {
/* 283:392 */     this.pagoRol = pagoRol;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public PagoRolEmpleado getPagoRolEmpleado()
/* 287:    */   {
/* 288:401 */     return this.pagoRolEmpleado;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setPagoRolEmpleado(PagoRolEmpleado pagoRolEmpleado)
/* 292:    */   {
/* 293:411 */     this.pagoRolEmpleado = pagoRolEmpleado;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public Rubro getRubro()
/* 297:    */   {
/* 298:420 */     return this.rubro;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setRubro(Rubro rubro)
/* 302:    */   {
/* 303:430 */     this.rubro = rubro;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Empresa getEmpresa()
/* 307:    */   {
/* 308:439 */     return this.empresa;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setEmpresa(Empresa empresa)
/* 312:    */   {
/* 313:449 */     this.empresa = empresa;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public List<Rubro> getListaRubro()
/* 317:    */   {
/* 318:458 */     if (this.listaRubro == null)
/* 319:    */     {
/* 320:459 */       Map<String, String> filters = new HashMap();
/* 321:460 */       filters.put("tipoRubro", String.valueOf(TipoRubro.VARIABLE));
/* 322:461 */       this.listaRubro = this.servicioRubro.obtenerListaCombo("nombre", true, filters);
/* 323:    */     }
/* 324:463 */     return this.listaRubro;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setListaRubro(List<Rubro> listaRubro)
/* 328:    */   {
/* 329:473 */     this.listaRubro = listaRubro;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public DataTable getDtPagoRolEmpleadoRubro()
/* 333:    */   {
/* 334:482 */     return this.dtPagoRolEmpleadoRubro;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setDtPagoRolEmpleadoRubro(DataTable dtPagoRolEmpleadoRubro)
/* 338:    */   {
/* 339:492 */     this.dtPagoRolEmpleadoRubro = dtPagoRolEmpleadoRubro;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public List<PagoRolEmpleadoRubro> getListaPagoRolEmpleadoRubro()
/* 343:    */   {
/* 344:501 */     return this.listaPagoRolEmpleadoRubro;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setListaPagoRolEmpleadoRubro(List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubro)
/* 348:    */   {
/* 349:511 */     this.listaPagoRolEmpleadoRubro = listaPagoRolEmpleadoRubro;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public PagoRolEmpleadoRubro getPagoRolEmpleadoRubro()
/* 353:    */   {
/* 354:520 */     return this.pagoRolEmpleadoRubro;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setPagoRolEmpleadoRubro(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 358:    */   {
/* 359:530 */     this.pagoRolEmpleadoRubro = pagoRolEmpleadoRubro;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public StreamedContent getArchivo()
/* 363:    */   {
/* 364:539 */     generarPlantilla(false);
/* 365:540 */     return this.archivo;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public StreamedContent getValores()
/* 369:    */   {
/* 370:549 */     generarPlantilla(true);
/* 371:550 */     return this.archivo;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setArchivo(StreamedContent archivo)
/* 375:    */   {
/* 376:560 */     this.archivo = archivo;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public BigDecimal getTotalHoras()
/* 380:    */   {
/* 381:564 */     return this.totalHoras;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setTotalHoras(BigDecimal totalHoras)
/* 385:    */   {
/* 386:568 */     this.totalHoras = totalHoras;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public BigDecimal getTotalValor()
/* 390:    */   {
/* 391:572 */     return this.totalValor;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setTotalValor(BigDecimal totalValor)
/* 395:    */   {
/* 396:576 */     this.totalValor = totalValor;
/* 397:    */   }
/* 398:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.RubroVariableEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */