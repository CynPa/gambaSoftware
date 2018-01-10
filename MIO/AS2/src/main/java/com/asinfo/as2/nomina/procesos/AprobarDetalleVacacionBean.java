/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.DetalleVacacion;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.Vacacion;
/*  11:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioDetalleVacacion;
/*  15:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioVacacion;
/*  16:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  17:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.JsfUtil;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.annotation.PostConstruct;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import javax.faces.model.SelectItem;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ import org.primefaces.component.datatable.DataTable;
/*  32:    */ import org.primefaces.event.SelectEvent;
/*  33:    */ import org.primefaces.model.LazyDataModel;
/*  34:    */ import org.primefaces.model.SortOrder;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class AprobarDetalleVacacionBean
/*  39:    */   extends PageControllerAS2
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 1L;
/*  42:    */   @EJB
/*  43:    */   private ServicioDetalleVacacion servicioDetalleVacacion;
/*  44:    */   @EJB
/*  45:    */   private ServicioVacacion servicioVacacion;
/*  46:    */   @EJB
/*  47:    */   private ServicioUsuario servicioUsuario;
/*  48:    */   private DetalleVacacion detalleVacacion;
/*  49:    */   private Vacacion vacacion;
/*  50:    */   private Empleado empleado;
/*  51:    */   private Empleado empleadoSession;
/*  52:    */   private List<Vacacion> listaVacacion;
/*  53:    */   private SelectItem[] listaEstadoItem;
/*  54:    */   private int totalDiasSolicitadosPorPeriodo;
/*  55:    */   LazyDataModel<DetalleVacacion> listaDetalleVacacionAprobar;
/*  56:    */   private DataTable dtDetalleVacacion;
/*  57:    */   private DataTable dtVacacion;
/*  58:    */   
/*  59:    */   public LazyDataModel<DetalleVacacion> getListaDetalleVacacionAprobar()
/*  60:    */   {
/*  61: 87 */     return this.listaDetalleVacacionAprobar;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setListaDetalleVacacionAprobar(LazyDataModel<DetalleVacacion> listaDetalleVacacionAprobar)
/*  65:    */   {
/*  66: 91 */     this.listaDetalleVacacionAprobar = listaDetalleVacacionAprobar;
/*  67:    */   }
/*  68:    */   
/*  69:    */   @PostConstruct
/*  70:    */   public void init()
/*  71:    */   {
/*  72:105 */     this.listaDetalleVacacionAprobar = new LazyDataModel()
/*  73:    */     {
/*  74:    */       private static final long serialVersionUID = 1L;
/*  75:    */       
/*  76:    */       public List<DetalleVacacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  77:    */       {
/*  78:110 */         filters.put("estado", "!=" + String.valueOf(Estado.CERRADO));
/*  79:111 */         filters.put("estado2", String.valueOf(Estado.ANULADO));
/*  80:112 */         List<DetalleVacacion> lista = new ArrayList();
/*  81:113 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  82:114 */         lista = AprobarDetalleVacacionBean.this.servicioDetalleVacacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  83:115 */         AprobarDetalleVacacionBean.this.listaDetalleVacacionAprobar.setRowCount(AprobarDetalleVacacionBean.this.servicioDetalleVacacion.contarPorCriterio(filters));
/*  84:    */         
/*  85:117 */         return lista;
/*  86:    */       }
/*  87:    */     };
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String crear()
/*  91:    */   {
/*  92:128 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  93:129 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   private void crearDetalleVacacion()
/*  97:    */   {
/*  98:137 */     this.detalleVacacion = new DetalleVacacion();
/*  99:138 */     this.empleado = new Empleado();
/* 100:139 */     this.detalleVacacion.setEstado(Estado.ELABORADO);
/* 101:140 */     this.detalleVacacion.setFechaInicio(new Date());
/* 102:141 */     this.detalleVacacion.setDiasTomados(0);
/* 103:142 */     this.detalleVacacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 104:143 */     this.detalleVacacion.setIdSucursal(AppUtil.getSucursal().getId());
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String editar()
/* 108:    */   {
/* 109:152 */     if (getDetalleVacacion().getIdDetalleVacacion() > 0)
/* 110:    */     {
/* 111:153 */       this.detalleVacacion = this.servicioDetalleVacacion.cargarDetalle(getDetalleVacacion().getIdDetalleVacacion());
/* 112:154 */       this.empleado = this.detalleVacacion.getVacacion().getHistoricoEmpleado().getEmpleado();
/* 113:155 */       this.dtVacacion.setSelection(this.detalleVacacion.getVacacion());
/* 114:156 */       setEditado(true);
/* 115:    */     }
/* 116:    */     else
/* 117:    */     {
/* 118:158 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 119:    */     }
/* 120:160 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String guardar()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:170 */       if (getVacacion().getId() > 0)
/* 128:    */       {
/* 129:171 */         this.detalleVacacion.setVacacion(this.vacacion);
/* 130:172 */         this.servicioDetalleVacacion.guardar(this.detalleVacacion);
/* 131:173 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 132:174 */         setEditado(false);
/* 133:175 */         limpiar();
/* 134:    */       }
/* 135:    */       else
/* 136:    */       {
/* 137:177 */         addInfoMessage(getLanguageController().getMensaje("msg_info_vacacion"));
/* 138:    */       }
/* 139:    */     }
/* 140:    */     catch (AS2Exception e)
/* 141:    */     {
/* 142:180 */       JsfUtil.addErrorMessage(e, "");
/* 143:    */     }
/* 144:    */     catch (Exception e)
/* 145:    */     {
/* 146:182 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 147:183 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 148:    */     }
/* 149:185 */     return "";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String eliminar()
/* 153:    */   {
/* 154:    */     try
/* 155:    */     {
/* 156:195 */       addInfoMessage(getLanguageController().getMensaje("msg_info_info_editar_vacacion"));
/* 157:    */     }
/* 158:    */     catch (Exception e)
/* 159:    */     {
/* 160:197 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 161:198 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 162:    */     }
/* 163:200 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String cargarDatos()
/* 167:    */   {
/* 168:209 */     return "";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String limpiar()
/* 172:    */   {
/* 173:218 */     crearDetalleVacacion();
/* 174:219 */     return "";
/* 175:    */   }
/* 176:    */   
/* 177:    */   public SelectItem[] getListaEstadoItem()
/* 178:    */   {
/* 179:228 */     if (this.listaEstadoItem == null)
/* 180:    */     {
/* 181:230 */       List<SelectItem> lista = new ArrayList();
/* 182:231 */       lista.add(new SelectItem("", ""));
/* 183:233 */       for (Estado t : Estado.values())
/* 184:    */       {
/* 185:234 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 186:235 */         lista.add(item);
/* 187:    */       }
/* 188:237 */       this.listaEstadoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 189:    */     }
/* 190:240 */     return this.listaEstadoItem;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String cargarEmpleado()
/* 194:    */   {
/* 195:250 */     return "";
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<Vacacion> getListaVacacion()
/* 199:    */   {
/* 200:259 */     this.listaVacacion = new ArrayList();
/* 201:260 */     this.listaVacacion = this.servicioDetalleVacacion.getListaVacacion(this.empleado.getId(), AppUtil.getOrganizacion().getIdOrganizacion(), getDetalleVacacion());
/* 202:    */     
/* 203:262 */     return this.listaVacacion;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void actualizaFecha()
/* 207:    */   {
/* 208:271 */     actualizarFecha(getDetalleVacacion().getFechaInicio());
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void dateSelect() {}
/* 212:    */   
/* 213:    */   private void actualizarFecha(Date fecha)
/* 214:    */   {
/* 215:280 */     getDetalleVacacion().setFechaFin(FuncionesUtiles.sumarFechaDiasMeses(fecha, getDetalleVacacion().getDiasTomados() - 1));
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void aprobarVacacion()
/* 219:    */   {
/* 220:    */     try
/* 221:    */     {
/* 222:288 */       if (this.detalleVacacion.getEstado().equals(Estado.ELABORADO)) {
/* 223:289 */         this.detalleVacacion.setAprobadoPor(AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 224:    */       }
/* 225:290 */       this.servicioDetalleVacacion.aprobarVacacion(this.detalleVacacion);
/* 226:291 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 227:    */     }
/* 228:    */     catch (AS2Exception e)
/* 229:    */     {
/* 230:293 */       JsfUtil.addErrorMessage(e, "");
/* 231:    */     }
/* 232:    */     catch (Exception e)
/* 233:    */     {
/* 234:295 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 235:296 */       e.printStackTrace();
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void negarVacacion()
/* 240:    */   {
/* 241:    */     try
/* 242:    */     {
/* 243:305 */       this.detalleVacacion.setEstado(Estado.ANULADO);
/* 244:306 */       this.servicioDetalleVacacion.guardar(this.detalleVacacion);
/* 245:307 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 246:    */     }
/* 247:    */     catch (Exception e)
/* 248:    */     {
/* 249:310 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 250:311 */       e.printStackTrace();
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void cerrarVacacion()
/* 255:    */   {
/* 256:    */     try
/* 257:    */     {
/* 258:321 */       this.servicioDetalleVacacion.cerrarVacacion(this.detalleVacacion);
/* 259:322 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 260:    */     }
/* 261:    */     catch (AS2Exception e)
/* 262:    */     {
/* 263:324 */       JsfUtil.addErrorMessage(e, "");
/* 264:    */     }
/* 265:    */     catch (Exception e)
/* 266:    */     {
/* 267:326 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 268:327 */       e.printStackTrace();
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void vacacionSeleccionado()
/* 273:    */   {
/* 274:332 */     this.detalleVacacion = ((DetalleVacacion)this.dtDetalleVacacion.getRowData());
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void onRowSelect(SelectEvent event)
/* 278:    */   {
/* 279:336 */     Vacacion vacacion = (Vacacion)event.getObject();
/* 280:337 */     this.detalleVacacion.setVacacion(vacacion);
/* 281:    */   }
/* 282:    */   
/* 283:    */   public DetalleVacacion getDetalleVacacion()
/* 284:    */   {
/* 285:349 */     if (this.detalleVacacion == null) {
/* 286:350 */       this.detalleVacacion = new DetalleVacacion();
/* 287:    */     }
/* 288:352 */     return this.detalleVacacion;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setDetalleVacacion(DetalleVacacion detalleVacacion)
/* 292:    */   {
/* 293:362 */     this.detalleVacacion = detalleVacacion;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public DataTable getDtDetalleVacacion()
/* 297:    */   {
/* 298:371 */     return this.dtDetalleVacacion;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setDtDetalleVacacion(DataTable dtDetalleVacacion)
/* 302:    */   {
/* 303:381 */     this.dtDetalleVacacion = dtDetalleVacacion;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Empleado getEmpleado()
/* 307:    */   {
/* 308:390 */     if (this.empleado == null) {
/* 309:391 */       this.empleado = new Empleado();
/* 310:    */     }
/* 311:393 */     return this.empleado;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setEmpleado(Empleado empleado)
/* 315:    */   {
/* 316:403 */     this.empleado = empleado;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public Vacacion getVacacion()
/* 320:    */   {
/* 321:412 */     if (this.vacacion == null) {
/* 322:413 */       this.vacacion = new Vacacion();
/* 323:    */     }
/* 324:415 */     return this.vacacion;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setVacacion(Vacacion vacacion)
/* 328:    */   {
/* 329:425 */     this.vacacion = vacacion;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public DataTable getDtVacacion()
/* 333:    */   {
/* 334:434 */     return this.dtVacacion;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setDtVacacion(DataTable dtVacacion)
/* 338:    */   {
/* 339:444 */     this.dtVacacion = dtVacacion;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public Empleado getEmpleadoSession()
/* 343:    */   {
/* 344:448 */     if ((this.empleado != null) && (this.empleado.getNombres() != null)) {
/* 345:449 */       return this.empleado;
/* 346:    */     }
/* 347:451 */     int idUsuario = AppUtil.getUsuarioEnSesion().getIdUsuario();
/* 348:    */     
/* 349:453 */     EntidadUsuario entidadUsuario = this.servicioUsuario.buscarPorId(Integer.valueOf(idUsuario));
/* 350:454 */     this.empleadoSession = entidadUsuario.getEmpleado();
/* 351:455 */     this.empleado = this.empleadoSession;
/* 352:456 */     return this.empleadoSession;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setEmpleadoSession(Empleado empleadoSession)
/* 356:    */   {
/* 357:460 */     this.empleadoSession = empleadoSession;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public int getTotalDiasSolicitadosPorPeriodo()
/* 361:    */   {
/* 362:464 */     int idDetalleVacacion = getDetalleVacacion().getIdDetalleVacacion();
/* 363:    */     
/* 364:466 */     int idVacacion = getDetalleVacacion().getVacacion().getIdVacacion();
/* 365:467 */     this.totalDiasSolicitadosPorPeriodo = this.servicioDetalleVacacion.totalDiasSolicitadosPorEmpleadoPorVacacion(idDetalleVacacion, idVacacion);
/* 366:468 */     return this.totalDiasSolicitadosPorPeriodo;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setTotalDiasSolicitadosPorPeriodo(int totalDiasSolicitadosPorPeriodo)
/* 370:    */   {
/* 371:472 */     this.totalDiasSolicitadosPorPeriodo = totalDiasSolicitadosPorPeriodo;
/* 372:    */   }
/* 373:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.AprobarDetalleVacacionBean
 * JD-Core Version:    0.7.0.1
 */