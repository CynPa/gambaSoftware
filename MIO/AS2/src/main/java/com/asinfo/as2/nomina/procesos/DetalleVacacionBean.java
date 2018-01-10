/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.DetalleVacacion;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.Vacacion;
/*  13:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  19:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioDetalleVacacion;
/*  20:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioVacacion;
/*  21:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  22:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  25:    */ import com.asinfo.as2.utils.JsfUtil;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.faces.bean.ManagedBean;
/*  33:    */ import javax.faces.bean.ViewScoped;
/*  34:    */ import javax.faces.model.SelectItem;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ import org.primefaces.component.datatable.DataTable;
/*  37:    */ import org.primefaces.event.SelectEvent;
/*  38:    */ import org.primefaces.model.LazyDataModel;
/*  39:    */ import org.primefaces.model.SortOrder;
/*  40:    */ 
/*  41:    */ @ManagedBean
/*  42:    */ @ViewScoped
/*  43:    */ public class DetalleVacacionBean
/*  44:    */   extends PageControllerAS2
/*  45:    */ {
/*  46:    */   private static final long serialVersionUID = 1L;
/*  47:    */   @EJB
/*  48:    */   private ServicioDetalleVacacion servicioDetalleVacacion;
/*  49:    */   @EJB
/*  50:    */   private ServicioVacacion servicioVacacion;
/*  51:    */   @EJB
/*  52:    */   private ServicioUsuario servicioUsuario;
/*  53:    */   @EJB
/*  54:    */   private transient ServicioDocumento servicioDocumento;
/*  55:    */   private boolean empleadoSimple;
/*  56:    */   private DetalleVacacion detalleVacacion;
/*  57:    */   private Vacacion vacacion;
/*  58:    */   private Empleado empleado;
/*  59:    */   private Empleado empleadoSession;
/*  60:    */   private List<Vacacion> listaVacacion;
/*  61:    */   private List<Documento> listaDocumentos;
/*  62:    */   private SelectItem[] listaEstadoItem;
/*  63:    */   private int totalDiasSolicitadosPorPeriodo;
/*  64:    */   LazyDataModel<DetalleVacacion> listaDetalleVacacion;
/*  65:    */   private DataTable dtDetalleVacacion;
/*  66:    */   private DataTable dtVacacion;
/*  67:    */   
/*  68:    */   @PostConstruct
/*  69:    */   public void init()
/*  70:    */   {
/*  71:107 */     int idUsuario = AppUtil.getUsuarioEnSesion().getIdUsuario();
/*  72:    */     
/*  73:109 */     EntidadUsuario entidadUsuario = this.servicioUsuario.buscarPorId(Integer.valueOf(idUsuario));
/*  74:110 */     if ((entidadUsuario.getEmpleado() == null) || (entidadUsuario.isIndicadorAdministrador())) {
/*  75:111 */       setEmpleadoSimple(false);
/*  76:    */     } else {
/*  77:113 */       setEmpleadoSimple(true);
/*  78:    */     }
/*  79:115 */     this.listaDetalleVacacion = new LazyDataModel()
/*  80:    */     {
/*  81:    */       private static final long serialVersionUID = 1L;
/*  82:    */       
/*  83:    */       public List<DetalleVacacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  84:    */       {
/*  85:122 */         if (DetalleVacacionBean.this.empleadoSimple) {
/*  86:123 */           filters.put("vacacion.historicoEmpleado.empleado.idEmpleado", String.valueOf(DetalleVacacionBean.this.getEmpleadoSession().getIdEmpleado()));
/*  87:    */         }
/*  88:126 */         List<DetalleVacacion> lista = new ArrayList();
/*  89:127 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  90:128 */         lista = DetalleVacacionBean.this.servicioDetalleVacacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  91:129 */         DetalleVacacionBean.this.listaDetalleVacacion.setRowCount(DetalleVacacionBean.this.servicioDetalleVacacion.contarPorCriterio(filters));
/*  92:    */         
/*  93:131 */         return lista;
/*  94:    */       }
/*  95:    */     };
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void crearDetalleVacacion()
/*  99:    */   {
/* 100:145 */     this.detalleVacacion = new DetalleVacacion();
/* 101:146 */     this.empleado = new Empleado();
/* 102:147 */     this.detalleVacacion.setEstado(Estado.ELABORADO);
/* 103:148 */     this.detalleVacacion.setFechaInicio(new Date());
/* 104:149 */     this.detalleVacacion.setDiasTomados(0);
/* 105:150 */     this.detalleVacacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 106:151 */     this.detalleVacacion.setIdSucursal(AppUtil.getSucursal().getId());
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String editar()
/* 110:    */   {
/* 111:160 */     if (getDetalleVacacion().getIdDetalleVacacion() > 0)
/* 112:    */     {
/* 113:161 */       if (getDetalleVacacion().getEstado() == Estado.ELABORADO)
/* 114:    */       {
/* 115:162 */         this.detalleVacacion = this.servicioDetalleVacacion.cargarDetalle(getDetalleVacacion().getIdDetalleVacacion());
/* 116:163 */         this.empleado = this.detalleVacacion.getVacacion().getHistoricoEmpleado().getEmpleado();
/* 117:164 */         this.dtVacacion.setSelection(this.detalleVacacion.getVacacion());
/* 118:165 */         setEditado(true);
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:167 */         addInfoMessage(getLanguageController().getMensaje("msg_info_info_editar_vacacion"));
/* 123:    */       }
/* 124:    */     }
/* 125:    */     else {
/* 126:170 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 127:    */     }
/* 128:172 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String guardar()
/* 132:    */   {
/* 133:    */     try
/* 134:    */     {
/* 135:182 */       if (getVacacion().getId() != 0)
/* 136:    */       {
/* 137:185 */         if (this.servicioVacacion.getTotalDiasPendientesXCerrar(this.detalleVacacion, getEmpleado()) != new Long(0L).longValue())
/* 138:    */         {
/* 139:186 */           addErrorMessage(getLanguageController().getMensaje("msg_error_existe_vacaciones_sin_cerrar"));
/* 140:    */         }
/* 141:    */         else
/* 142:    */         {
/* 143:189 */           this.detalleVacacion.setVacacion(this.vacacion);
/* 144:190 */           this.servicioDetalleVacacion.guardar(this.detalleVacacion);
/* 145:191 */           addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 146:192 */           setEditado(false);
/* 147:193 */           limpiar();
/* 148:    */         }
/* 149:    */       }
/* 150:    */       else {
/* 151:197 */         addInfoMessage(getLanguageController().getMensaje("msg_info_vacacion"));
/* 152:    */       }
/* 153:    */     }
/* 154:    */     catch (ExcepcionAS2Inventario e)
/* 155:    */     {
/* 156:201 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 157:202 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 158:    */     }
/* 159:    */     catch (ExcepcionAS2 e)
/* 160:    */     {
/* 161:204 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 162:205 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 163:    */     }
/* 164:    */     catch (AS2Exception e)
/* 165:    */     {
/* 166:207 */       JsfUtil.addErrorMessage(e, "");
/* 167:    */     }
/* 168:    */     catch (Exception e)
/* 169:    */     {
/* 170:209 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 171:210 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 172:    */     }
/* 173:212 */     return "";
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String eliminar()
/* 177:    */   {
/* 178:    */     try
/* 179:    */     {
/* 180:222 */       if ((getDetalleVacacion().getEstado() == Estado.ELABORADO) || (getDetalleVacacion().getEstado() == Estado.ANULADO) || 
/* 181:223 */         (getDetalleVacacion().getUsuarioCreacion() == AppUtil.getUsuarioEnSesion().getNombreUsuario()))
/* 182:    */       {
/* 183:224 */         this.servicioDetalleVacacion.eliminar(this.detalleVacacion);
/* 184:225 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 185:    */       }
/* 186:    */       else
/* 187:    */       {
/* 188:227 */         addInfoMessage(getLanguageController().getMensaje("msg_info_info_editar_vacacion"));
/* 189:    */       }
/* 190:    */     }
/* 191:    */     catch (Exception e)
/* 192:    */     {
/* 193:231 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 194:232 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 195:    */     }
/* 196:234 */     return "";
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String cargarDatos()
/* 200:    */   {
/* 201:243 */     return "";
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String limpiar()
/* 205:    */   {
/* 206:252 */     crearDetalleVacacion();
/* 207:253 */     return "";
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void onRowSelect(SelectEvent event)
/* 211:    */   {
/* 212:257 */     Vacacion vacacion = (Vacacion)event.getObject();
/* 213:258 */     this.detalleVacacion.setVacacion(vacacion);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public SelectItem[] getListaEstadoItem()
/* 217:    */   {
/* 218:267 */     if (this.listaEstadoItem == null)
/* 219:    */     {
/* 220:269 */       List<SelectItem> lista = new ArrayList();
/* 221:270 */       lista.add(new SelectItem("", ""));
/* 222:272 */       for (Estado t : Estado.values())
/* 223:    */       {
/* 224:273 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 225:274 */         lista.add(item);
/* 226:    */       }
/* 227:276 */       this.listaEstadoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 228:    */     }
/* 229:279 */     return this.listaEstadoItem;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String cargarEmpleado()
/* 233:    */   {
/* 234:289 */     return "";
/* 235:    */   }
/* 236:    */   
/* 237:    */   public List<Vacacion> getListaVacacion()
/* 238:    */   {
/* 239:298 */     this.listaVacacion = new ArrayList();
/* 240:299 */     this.listaVacacion = this.servicioDetalleVacacion.getListaVacacion(getEmpleado().getId(), AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 241:300 */     return this.listaVacacion;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void actualizaFecha()
/* 245:    */   {
/* 246:308 */     actualizarFecha(getDetalleVacacion().getFechaInicio());
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void dateSelect(SelectEvent event)
/* 250:    */   {
/* 251:312 */     actualizarFecha((Date)event.getObject());
/* 252:    */   }
/* 253:    */   
/* 254:    */   private void actualizarFecha(Date fecha)
/* 255:    */   {
/* 256:316 */     getDetalleVacacion().setFechaFin(FuncionesUtiles.sumarFechaDiasMeses(fecha, getDetalleVacacion().getDiasTomados() - 1));
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void vacacionSeleccionado()
/* 260:    */   {
/* 261:320 */     this.detalleVacacion = ((DetalleVacacion)this.dtDetalleVacacion.getRowData());
/* 262:    */   }
/* 263:    */   
/* 264:    */   public DetalleVacacion getDetalleVacacion()
/* 265:    */   {
/* 266:332 */     if (this.detalleVacacion == null) {
/* 267:333 */       this.detalleVacacion = new DetalleVacacion();
/* 268:    */     }
/* 269:335 */     return this.detalleVacacion;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setDetalleVacacion(DetalleVacacion detalleVacacion)
/* 273:    */   {
/* 274:345 */     this.detalleVacacion = detalleVacacion;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public LazyDataModel<DetalleVacacion> getListaDetalleVacacion()
/* 278:    */   {
/* 279:354 */     return this.listaDetalleVacacion;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setListaDetalleVacacion(LazyDataModel<DetalleVacacion> listaDetalleVacacion)
/* 283:    */   {
/* 284:364 */     this.listaDetalleVacacion = listaDetalleVacacion;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public DataTable getDtDetalleVacacion()
/* 288:    */   {
/* 289:373 */     return this.dtDetalleVacacion;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setDtDetalleVacacion(DataTable dtDetalleVacacion)
/* 293:    */   {
/* 294:383 */     this.dtDetalleVacacion = dtDetalleVacacion;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public Empleado getEmpleado()
/* 298:    */   {
/* 299:392 */     if (this.empleado == null) {
/* 300:393 */       this.empleado = new Empleado();
/* 301:    */     }
/* 302:395 */     return this.empleado;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setEmpleado(Empleado empleado)
/* 306:    */   {
/* 307:405 */     this.empleado = empleado;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public Vacacion getVacacion()
/* 311:    */   {
/* 312:414 */     if (this.vacacion == null) {
/* 313:415 */       this.vacacion = new Vacacion();
/* 314:    */     }
/* 315:417 */     return this.vacacion;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setVacacion(Vacacion vacacion)
/* 319:    */   {
/* 320:427 */     this.vacacion = vacacion;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public DataTable getDtVacacion()
/* 324:    */   {
/* 325:436 */     return this.dtVacacion;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setDtVacacion(DataTable dtVacacion)
/* 329:    */   {
/* 330:446 */     this.dtVacacion = dtVacacion;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public Empleado getEmpleadoSession()
/* 334:    */   {
/* 335:450 */     if ((this.empleado != null) && (this.empleado.getNombres() != null)) {
/* 336:451 */       return this.empleado;
/* 337:    */     }
/* 338:453 */     int idUsuario = AppUtil.getUsuarioEnSesion().getIdUsuario();
/* 339:    */     
/* 340:455 */     EntidadUsuario entidadUsuario = this.servicioUsuario.buscarPorId(Integer.valueOf(idUsuario));
/* 341:456 */     this.empleadoSession = entidadUsuario.getEmpleado();
/* 342:457 */     this.empleado = this.empleadoSession;
/* 343:458 */     return this.empleadoSession;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setEmpleadoSession(Empleado empleadoSession)
/* 347:    */   {
/* 348:462 */     this.empleadoSession = empleadoSession;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public boolean isEmpleadoSimple()
/* 352:    */   {
/* 353:466 */     return this.empleadoSimple;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setEmpleadoSimple(boolean empleadoSimple)
/* 357:    */   {
/* 358:470 */     this.empleadoSimple = empleadoSimple;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public int getTotalDiasSolicitadosPorPeriodo()
/* 362:    */   {
/* 363:474 */     int idDetalleVacacion = getDetalleVacacion().getIdDetalleVacacion();
/* 364:    */     
/* 365:476 */     int idVacacion = this.vacacion.getIdVacacion();
/* 366:477 */     this.totalDiasSolicitadosPorPeriodo = this.servicioDetalleVacacion.totalDiasSolicitadosPorEmpleadoPorVacacion(idDetalleVacacion, idVacacion);
/* 367:478 */     return this.totalDiasSolicitadosPorPeriodo;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setTotalDiasSolicitadosPorPeriodo(int totalDiasSolicitadosPorPeriodo)
/* 371:    */   {
/* 372:482 */     this.totalDiasSolicitadosPorPeriodo = totalDiasSolicitadosPorPeriodo;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public List<Documento> getListaDocumentos()
/* 376:    */   {
/* 377:487 */     if (this.listaDocumentos == null) {
/* 378:    */       try
/* 379:    */       {
/* 380:489 */         this.listaDocumentos = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.SOLICITUD_VACACION, 
/* 381:490 */           AppUtil.getOrganizacion().getIdOrganizacion());
/* 382:    */       }
/* 383:    */       catch (ExcepcionAS2 e)
/* 384:    */       {
/* 385:492 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 386:    */       }
/* 387:    */     }
/* 388:496 */     return this.listaDocumentos;
/* 389:    */   }
/* 390:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.DetalleVacacionBean
 * JD-Core Version:    0.7.0.1
 */