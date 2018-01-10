/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.DetallePermisoEmpleado;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PermisoEmpleado;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.TipoPermisoEmpleado;
/*  14:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  19:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  20:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPermisoEmpleado;
/*  21:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioTipoPermisoEmpleado;
/*  22:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  23:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  24:    */ import com.asinfo.as2.util.AppUtil;
/*  25:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  26:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  27:    */ import java.math.BigDecimal;
/*  28:    */ import java.math.RoundingMode;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.Date;
/*  31:    */ import java.util.HashMap;
/*  32:    */ import java.util.List;
/*  33:    */ import java.util.Map;
/*  34:    */ import javax.annotation.PostConstruct;
/*  35:    */ import javax.ejb.EJB;
/*  36:    */ import javax.faces.bean.ManagedBean;
/*  37:    */ import javax.faces.bean.ViewScoped;
/*  38:    */ import javax.faces.model.SelectItem;
/*  39:    */ import org.apache.log4j.Logger;
/*  40:    */ import org.primefaces.component.datatable.DataTable;
/*  41:    */ import org.primefaces.model.LazyDataModel;
/*  42:    */ import org.primefaces.model.SortOrder;
/*  43:    */ 
/*  44:    */ @ManagedBean
/*  45:    */ @ViewScoped
/*  46:    */ public class PermisoEmpleadoBean
/*  47:    */   extends PageControllerAS2
/*  48:    */ {
/*  49:    */   private static final long serialVersionUID = -5141839345436106206L;
/*  50:    */   @EJB
/*  51:    */   protected transient ServicioPermisoEmpleado servicioPermisoEmpleado;
/*  52:    */   @EJB
/*  53:    */   private transient ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioTipoPermisoEmpleado servicioTipoPermisoEmpleado;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioDocumento servicioDocumento;
/*  58:    */   @EJB
/*  59:    */   private ServicioUsuario servicioUsuario;
/*  60:    */   protected boolean empleadoSimple;
/*  61:    */   protected PermisoEmpleado permisoEmpleado;
/*  62:    */   protected Empleado empleado;
/*  63:    */   protected Empleado empleadoSession;
/*  64:    */   private LazyDataModel<PermisoEmpleado> listaPermisoEmpleado;
/*  65:    */   private List<TipoPermisoEmpleado> listaTipoPermisoEmpleado;
/*  66:    */   private SelectItem[] listaEstadoItem;
/*  67:    */   private List<Documento> listaDocumento;
/*  68:    */   private List<DetallePermisoEmpleado> listaDetallePermiso;
/*  69:    */   private DataTable dtPermisoEmpleado;
/*  70:    */   private DataTable dtDetallePermiso;
/*  71:    */   
/*  72:    */   @PostConstruct
/*  73:    */   public void init()
/*  74:    */   {
/*  75:109 */     int idUsuario = AppUtil.getUsuarioEnSesion().getIdUsuario();
/*  76:    */     
/*  77:111 */     EntidadUsuario entidadUsuario = this.servicioUsuario.buscarPorId(Integer.valueOf(idUsuario));
/*  78:112 */     if ((entidadUsuario.getEmpleado() == null) || (entidadUsuario.isIndicadorAdministrador())) {
/*  79:113 */       setEmpleadoSimple(false);
/*  80:    */     } else {
/*  81:115 */       setEmpleadoSimple(true);
/*  82:    */     }
/*  83:117 */     this.listaPermisoEmpleado = new LazyDataModel()
/*  84:    */     {
/*  85:    */       private static final long serialVersionUID = 1L;
/*  86:    */       
/*  87:    */       public List<PermisoEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  88:    */       {
/*  89:124 */         List<PermisoEmpleado> lista = new ArrayList();
/*  90:125 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  91:126 */         if (PermisoEmpleadoBean.this.empleadoSimple) {
/*  92:127 */           filters.put("historicoEmpleado.empleado.idEmpleado", String.valueOf(PermisoEmpleadoBean.this.getEmpleadoSession().getIdEmpleado()));
/*  93:    */         }
/*  94:129 */         lista = PermisoEmpleadoBean.this.servicioPermisoEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  95:    */         
/*  96:131 */         PermisoEmpleadoBean.this.listaPermisoEmpleado.setRowCount(PermisoEmpleadoBean.this.servicioPermisoEmpleado.contarPorCriterio(filters));
/*  97:    */         
/*  98:133 */         return lista;
/*  99:    */       }
/* 100:    */     };
/* 101:    */   }
/* 102:    */   
/* 103:    */   private void crearPermisoEmpleado()
/* 104:    */   {
/* 105:147 */     this.permisoEmpleado = new PermisoEmpleado();
/* 106:148 */     this.permisoEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 107:149 */     this.permisoEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/* 108:150 */     this.permisoEmpleado.setEstado(Estado.ELABORADO);
/* 109:151 */     this.permisoEmpleado.setFechaPermiso(new Date());
/* 110:152 */     Documento documento = null;
/* 111:153 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 112:    */     {
/* 113:154 */       documento = (Documento)getListaDocumento().get(0);
/* 114:155 */       this.permisoEmpleado.setDocumento(documento);
/* 115:156 */       actualizarDocumento();
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:158 */       documento = new Documento();
/* 120:159 */       this.permisoEmpleado.setDocumento(documento);
/* 121:    */     }
/* 122:162 */     this.empleado = new Empleado();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String editar()
/* 126:    */   {
/* 127:172 */     if (getPermisoEmpleado().getIdPermisoEmpleado() > 0)
/* 128:    */     {
/* 129:173 */       if (getPermisoEmpleado().getEstado() == Estado.ELABORADO)
/* 130:    */       {
/* 131:174 */         this.permisoEmpleado = this.servicioPermisoEmpleado.cargarDetalle(this.permisoEmpleado.getId());
/* 132:175 */         setEmpleado(this.permisoEmpleado.getHistoricoEmpleado().getEmpleado());
/* 133:176 */         setEditado(true);
/* 134:    */       }
/* 135:    */       else
/* 136:    */       {
/* 137:178 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 138:    */       }
/* 139:    */     }
/* 140:    */     else {
/* 141:181 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 142:    */     }
/* 143:183 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String guardar()
/* 147:    */   {
/* 148:    */     try
/* 149:    */     {
/* 150:193 */       HistoricoEmpleado historicoEmpleado = this.servicioHistoricoEmpleado.obtenerPeriodoActivo(this.empleado.getId(), this.permisoEmpleado.getFechaPermiso());
/* 151:194 */       this.permisoEmpleado.setHistoricoEmpleado(historicoEmpleado);
/* 152:195 */       this.servicioPermisoEmpleado.guardar(this.permisoEmpleado);
/* 153:196 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 154:197 */       setEditado(false);
/* 155:198 */       limpiar();
/* 156:    */     }
/* 157:    */     catch (ExcepcionAS2Nomina e)
/* 158:    */     {
/* 159:201 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 160:    */     }
/* 161:    */     catch (ExcepcionAS2 e)
/* 162:    */     {
/* 163:203 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 164:    */     }
/* 165:    */     catch (Exception e)
/* 166:    */     {
/* 167:205 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 168:206 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 169:    */     }
/* 170:208 */     return "";
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String eliminar()
/* 174:    */   {
/* 175:    */     try
/* 176:    */     {
/* 177:218 */       if (getPermisoEmpleado().getEstado() == Estado.ELABORADO)
/* 178:    */       {
/* 179:219 */         this.servicioPermisoEmpleado.eliminar(this.permisoEmpleado);
/* 180:220 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 181:    */       }
/* 182:    */       else
/* 183:    */       {
/* 184:222 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 185:223 */         return "";
/* 186:    */       }
/* 187:    */     }
/* 188:    */     catch (Exception e)
/* 189:    */     {
/* 190:226 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 191:227 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 192:    */     }
/* 193:229 */     return "";
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String cargarDatos()
/* 197:    */   {
/* 198:238 */     return "";
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String limpiar()
/* 202:    */   {
/* 203:247 */     crearPermisoEmpleado();
/* 204:248 */     return "";
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String actualizarDocumento()
/* 208:    */   {
/* 209:257 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getPermisoEmpleado().getDocumento().getIdDocumento()));
/* 210:258 */     getPermisoEmpleado().setDocumento(documento);
/* 211:259 */     return "";
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String cargarEmpleado()
/* 215:    */   {
/* 216:266 */     return "";
/* 217:    */   }
/* 218:    */   
/* 219:    */   public SelectItem[] getListaEstadoItem()
/* 220:    */   {
/* 221:270 */     if (this.listaEstadoItem == null)
/* 222:    */     {
/* 223:272 */       List<SelectItem> lista = new ArrayList();
/* 224:273 */       lista.add(new SelectItem("", ""));
/* 225:275 */       for (Estado t : Estado.values())
/* 226:    */       {
/* 227:276 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 228:277 */         lista.add(item);
/* 229:    */       }
/* 230:279 */       this.listaEstadoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 231:    */     }
/* 232:282 */     return this.listaEstadoItem;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public String imprimirPermiso()
/* 236:    */   {
/* 237:287 */     return "";
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void actualizarHoraHasta() {}
/* 241:    */   
/* 242:    */   public void actualizarTotalHoras()
/* 243:    */   {
/* 244:295 */     BigDecimal totalHoras = BigDecimal.ZERO;
/* 245:296 */     for (DetallePermisoEmpleado det : this.permisoEmpleado.getListaDetallePermisoEmpleado()) {
/* 246:297 */       if (!det.isEliminado()) {
/* 247:298 */         totalHoras = totalHoras.add(det.getHoras());
/* 248:    */       }
/* 249:    */     }
/* 250:301 */     this.permisoEmpleado.setHoras(totalHoras);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void actualizarDetalle(DetallePermisoEmpleado detallePermisoEmpleado)
/* 254:    */   {
/* 255:305 */     if ((detallePermisoEmpleado.getHoras() != null) && (detallePermisoEmpleado.getHoraDesde() != null))
/* 256:    */     {
/* 257:306 */       Integer horasSemanal = ParametrosSistema.getHorasSemanaLaboral(AppUtil.getOrganizacion().getId());
/* 258:307 */       BigDecimal horasXDia = BigDecimal.valueOf(horasSemanal.intValue()).divide(new BigDecimal(5), RoundingMode.HALF_UP);
/* 259:308 */       if (detallePermisoEmpleado.getHoras().compareTo(horasXDia) >= 0) {
/* 260:309 */         detallePermisoEmpleado.setIndicadorDiaCompleto(true);
/* 261:    */       }
/* 262:312 */       detallePermisoEmpleado.setHoraHasta(FuncionesUtiles.SumarHoras(detallePermisoEmpleado.getHoraDesde(), detallePermisoEmpleado.getHoras().doubleValue()));
/* 263:    */     }
/* 264:314 */     actualizarTotalHoras();
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void crearDetalles()
/* 268:    */   {
/* 269:318 */     if ((this.permisoEmpleado.getDiaDesde() != null) && (this.permisoEmpleado.getDiaHasta() != null))
/* 270:    */     {
/* 271:319 */       Map<Date, DetallePermisoEmpleado> hashDetalles = new HashMap();
/* 272:320 */       for (DetallePermisoEmpleado det : this.permisoEmpleado.getListaDetallePermisoEmpleado())
/* 273:    */       {
/* 274:321 */         det.setEliminado(true);
/* 275:322 */         hashDetalles.put(det.getFechaPermiso(), det);
/* 276:    */       }
/* 277:325 */       Date fechaHasta = FuncionesUtiles.sumarFechaDiasMeses(this.permisoEmpleado.getDiaHasta(), 1);
/* 278:326 */       for (Date date = this.permisoEmpleado.getDiaDesde(); date.before(fechaHasta); date = FuncionesUtiles.sumarFechaDiasMeses(date, 1))
/* 279:    */       {
/* 280:327 */         DetallePermisoEmpleado det = (DetallePermisoEmpleado)hashDetalles.get(date);
/* 281:328 */         if (det == null)
/* 282:    */         {
/* 283:329 */           det = new DetallePermisoEmpleado();
/* 284:330 */           det.setIdOrganizacion(this.permisoEmpleado.getIdOrganizacion());
/* 285:331 */           det.setIdSucursal(this.permisoEmpleado.getIdSucursal());
/* 286:332 */           det.setPermisoEmpleado(this.permisoEmpleado);
/* 287:333 */           det.setFechaPermiso(date);
/* 288:334 */           this.permisoEmpleado.getListaDetallePermisoEmpleado().add(det);
/* 289:    */         }
/* 290:336 */         det.setEliminado(false);
/* 291:    */       }
/* 292:338 */       actualizarTotalHoras();
/* 293:    */     }
/* 294:339 */     else if (this.permisoEmpleado.getDiaDesde() != null)
/* 295:    */     {
/* 296:340 */       this.permisoEmpleado.setDiaHasta(this.permisoEmpleado.getDiaDesde());
/* 297:341 */       crearDetalles();
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   public PermisoEmpleado getPermisoEmpleado()
/* 302:    */   {
/* 303:355 */     if (this.permisoEmpleado == null) {
/* 304:356 */       this.permisoEmpleado = new PermisoEmpleado();
/* 305:    */     }
/* 306:358 */     return this.permisoEmpleado;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setPermisoEmpleado(PermisoEmpleado permisoEmpleado)
/* 310:    */   {
/* 311:368 */     this.permisoEmpleado = permisoEmpleado;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public LazyDataModel<PermisoEmpleado> getListaPermisoEmpleado()
/* 315:    */   {
/* 316:377 */     return this.listaPermisoEmpleado;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setListaPermisoEmpleado(LazyDataModel<PermisoEmpleado> listaPermisoEmpleado)
/* 320:    */   {
/* 321:387 */     this.listaPermisoEmpleado = listaPermisoEmpleado;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public DataTable getDtPermisoEmpleado()
/* 325:    */   {
/* 326:396 */     return this.dtPermisoEmpleado;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setDtPermisoEmpleado(DataTable dtPermisoEmpleado)
/* 330:    */   {
/* 331:406 */     this.dtPermisoEmpleado = dtPermisoEmpleado;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public Empleado getEmpleado()
/* 335:    */   {
/* 336:415 */     if (this.empleado == null) {
/* 337:416 */       this.empleado = new Empleado();
/* 338:    */     }
/* 339:418 */     return this.empleado;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setEmpleado(Empleado empleado)
/* 343:    */   {
/* 344:428 */     this.empleado = empleado;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public List<TipoPermisoEmpleado> getListaTipoPermisoEmpleado()
/* 348:    */   {
/* 349:437 */     if (this.listaTipoPermisoEmpleado == null) {
/* 350:438 */       this.listaTipoPermisoEmpleado = this.servicioTipoPermisoEmpleado.obtenerListaCombo("nombre", true, null);
/* 351:    */     }
/* 352:441 */     return this.listaTipoPermisoEmpleado;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public List<Documento> getListaDocumento()
/* 356:    */   {
/* 357:450 */     if (this.listaDocumento == null) {
/* 358:    */       try
/* 359:    */       {
/* 360:452 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PERMISO_NOMINA);
/* 361:    */       }
/* 362:    */       catch (ExcepcionAS2 e)
/* 363:    */       {
/* 364:454 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 365:    */       }
/* 366:    */     }
/* 367:457 */     return this.listaDocumento;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 371:    */   {
/* 372:467 */     this.listaDocumento = listaDocumento;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public boolean isEmpleadoSimple()
/* 376:    */   {
/* 377:471 */     return this.empleadoSimple;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setEmpleadoSimple(boolean empleadoSimple)
/* 381:    */   {
/* 382:475 */     this.empleadoSimple = empleadoSimple;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public Empleado getEmpleadoSession()
/* 386:    */   {
/* 387:479 */     if ((this.empleado != null) && (this.empleado.getNombres() != null)) {
/* 388:480 */       return this.empleado;
/* 389:    */     }
/* 390:482 */     int idUsuario = AppUtil.getUsuarioEnSesion().getIdUsuario();
/* 391:    */     
/* 392:484 */     EntidadUsuario entidadUsuario = this.servicioUsuario.buscarPorId(Integer.valueOf(idUsuario));
/* 393:485 */     this.empleadoSession = entidadUsuario.getEmpleado();
/* 394:486 */     this.empleado = this.empleadoSession;
/* 395:487 */     return this.empleadoSession;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void setEmpleadoSession(Empleado empleadoSession)
/* 399:    */   {
/* 400:491 */     this.empleadoSession = empleadoSession;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public List<DetallePermisoEmpleado> getListaDetallePermiso()
/* 404:    */   {
/* 405:495 */     this.listaDetallePermiso = new ArrayList();
/* 406:496 */     for (DetallePermisoEmpleado det : getPermisoEmpleado().getListaDetallePermisoEmpleado()) {
/* 407:497 */       if (!det.isEliminado()) {
/* 408:498 */         this.listaDetallePermiso.add(det);
/* 409:    */       }
/* 410:    */     }
/* 411:501 */     return this.listaDetallePermiso;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setListaDetallePermiso(List<DetallePermisoEmpleado> listaDetallePermiso)
/* 415:    */   {
/* 416:505 */     this.listaDetallePermiso = listaDetallePermiso;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public DataTable getDtDetallePermiso()
/* 420:    */   {
/* 421:509 */     return this.dtDetallePermiso;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setDtDetallePermiso(DataTable dtDetallePermiso)
/* 425:    */   {
/* 426:513 */     this.dtDetallePermiso = dtDetallePermiso;
/* 427:    */   }
/* 428:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.PermisoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */