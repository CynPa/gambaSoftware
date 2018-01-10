/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.DetalleUtilidad;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.Utilidad;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  14:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioUtilidad;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import com.asinfo.as2.utils.JsfUtil;
/*  18:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  19:    */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*  20:    */ import java.math.BigDecimal;
/*  21:    */ import java.math.RoundingMode;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Calendar;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ import org.primefaces.component.datatable.DataTable;
/*  32:    */ import org.primefaces.model.LazyDataModel;
/*  33:    */ import org.primefaces.model.SortOrder;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class UtilidadBean
/*  38:    */   extends PageControllerAS2
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = -1525424477339140626L;
/*  41:    */   @EJB
/*  42:    */   private ServicioUtilidad servicioUtilidad;
/*  43:    */   @EJB
/*  44:    */   private ServicioEmpleado servicioEmpleado;
/*  45:    */   private Utilidad utilidad;
/*  46:    */   private List<DetalleUtilidad> listaDetalleUtilidad;
/*  47:    */   private List<DetalleUtilidad> listaDetalleUtilidadEmpleadosConRDEP;
/*  48:    */   private List<DetalleUtilidad> listaDetalleUtilidadEmpleadosConRDEPFilter;
/*  49:    */   private BigDecimal valor10PEmpleados;
/*  50:    */   private BigDecimal valor5PCargas;
/*  51:    */   private LazyDataModel<Utilidad> listaUtilidad;
/*  52:    */   private DataTable dtUtilidad;
/*  53:    */   private DataTable dtDetalleUtilidades;
/*  54:    */   private DataTable dtRelacionDependencia;
/*  55:    */   
/*  56:    */   @PostConstruct
/*  57:    */   public void init()
/*  58:    */   {
/*  59:100 */     this.listaUtilidad = new LazyDataModel()
/*  60:    */     {
/*  61:    */       private static final long serialVersionUID = 1L;
/*  62:    */       
/*  63:    */       public List<Utilidad> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  64:    */       {
/*  65:107 */         List<Utilidad> lista = new ArrayList();
/*  66:108 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  67:    */         
/*  68:110 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  69:111 */         lista = UtilidadBean.this.servicioUtilidad.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  70:    */         
/*  71:113 */         UtilidadBean.this.listaUtilidad.setRowCount(UtilidadBean.this.servicioUtilidad.contarPorCriterio(filters));
/*  72:    */         
/*  73:115 */         return lista;
/*  74:    */       }
/*  75:    */     };
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void crearUtilidad()
/*  79:    */   {
/*  80:128 */     this.utilidad = new Utilidad();
/*  81:129 */     this.utilidad.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  82:130 */     this.utilidad.setIdSucursal(AppUtil.getSucursal().getId());
/*  83:131 */     this.utilidad.setAnio(Calendar.getInstance().get(1) - 1);
/*  84:132 */     this.utilidad.setFechaPagoUtilidad(FuncionesUtiles.getFecha(14, 4, this.utilidad.getAnio() + 1));
/*  85:    */     
/*  86:134 */     this.listaDetalleUtilidad = null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String eliminarDetalle()
/*  90:    */   {
/*  91:139 */     DetalleUtilidad s = (DetalleUtilidad)this.dtRelacionDependencia.getRowData();
/*  92:140 */     s.setEliminado(true);
/*  93:141 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String editar()
/*  97:    */   {
/*  98:151 */     if (getUtilidad().getEstado().equals(Estado.CERRADO))
/*  99:    */     {
/* 100:152 */       this.utilidad = this.servicioUtilidad.cargarDetalle(this.utilidad.getIdUtilidad());
/* 101:153 */       addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 102:    */     }
/* 103:155 */     else if (getUtilidad().getIdUtilidad() > 0)
/* 104:    */     {
/* 105:156 */       this.utilidad = this.servicioUtilidad.cargarDetalle(this.utilidad.getIdUtilidad());
/* 106:157 */       setEditado(true);
/* 107:    */     }
/* 108:    */     else
/* 109:    */     {
/* 110:159 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 111:    */     }
/* 112:164 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String guardar()
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:174 */       this.servicioUtilidad.guardar(this.utilidad);
/* 120:    */       
/* 121:176 */       limpiar();
/* 122:177 */       setEditado(false);
/* 123:178 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 124:    */     }
/* 125:    */     catch (AS2Exception e)
/* 126:    */     {
/* 127:181 */       JsfUtil.addErrorMessage(e, "");
/* 128:182 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 129:183 */       e.printStackTrace();
/* 130:    */     }
/* 131:    */     catch (Exception e)
/* 132:    */     {
/* 133:185 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 134:186 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 135:    */     }
/* 136:188 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String eliminar()
/* 140:    */   {
/* 141:    */     try
/* 142:    */     {
/* 143:198 */       this.servicioUtilidad.eliminar(this.utilidad);
/* 144:199 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 145:    */     }
/* 146:    */     catch (Exception e)
/* 147:    */     {
/* 148:201 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 149:202 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 150:    */     }
/* 151:204 */     return "";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String cargarDatos()
/* 155:    */   {
/* 156:213 */     return "";
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String limpiar()
/* 160:    */   {
/* 161:222 */     crearUtilidad();
/* 162:223 */     this.listaDetalleUtilidadEmpleadosConRDEPFilter = null;
/* 163:224 */     this.listaDetalleUtilidadEmpleadosConRDEP = null;
/* 164:226 */     if (this.dtDetalleUtilidades != null) {
/* 165:227 */       this.dtDetalleUtilidades.reset();
/* 166:    */     }
/* 167:230 */     return "";
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String procesarUtilidad()
/* 171:    */     throws ExcepcionAS2
/* 172:    */   {
/* 173:240 */     this.utilidad = this.servicioUtilidad.procesarUtilidad(this.utilidad);
/* 174:241 */     this.listaDetalleUtilidadEmpleadosConRDEP = new ArrayList();
/* 175:242 */     this.listaDetalleUtilidad = new ArrayList();
/* 176:244 */     for (DetalleUtilidad du : this.utilidad.getListaDetalleUtilidad()) {
/* 177:246 */       if (!du.isEliminado()) {
/* 178:248 */         if (du.getEmpleado() != null)
/* 179:    */         {
/* 180:249 */           Empleado e = this.servicioEmpleado.cargarDetalle(du.getEmpleado().getIdEmpleado());
/* 181:250 */           du.setEmpleado(e);
/* 182:251 */           this.listaDetalleUtilidadEmpleadosConRDEP.add(du);
/* 183:    */         }
/* 184:    */         else
/* 185:    */         {
/* 186:253 */           this.listaDetalleUtilidad.add(du);
/* 187:    */         }
/* 188:    */       }
/* 189:    */     }
/* 190:258 */     if (this.dtDetalleUtilidades != null) {
/* 191:259 */       this.dtDetalleUtilidades.reset();
/* 192:    */     }
/* 193:262 */     return "";
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void agregarEmpleadoListener()
/* 197:    */   {
/* 198:266 */     DetalleUtilidad du = new DetalleUtilidad();
/* 199:267 */     du.setUtilidad(this.utilidad);
/* 200:268 */     du.setIdOrganizacion(this.utilidad.getIdOrganizacion());
/* 201:269 */     this.utilidad.getListaDetalleUtilidad().add(du);
/* 202:270 */     getListaDetalleUtilidad().add(du);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void cargarPorcentajesUtilidades()
/* 206:    */   {
/* 207:275 */     this.valor10PEmpleados = this.utilidad.getValor().multiply(new BigDecimal(10)).divide(new BigDecimal(100), 5, RoundingMode.HALF_UP);
/* 208:276 */     this.valor5PCargas = this.utilidad.getValor().multiply(new BigDecimal(5)).divide(new BigDecimal(100), 5, RoundingMode.HALF_UP);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void cerrar()
/* 212:    */   {
/* 213:281 */     Utilidad u = (Utilidad)this.dtUtilidad.getRowData();
/* 214:    */     try
/* 215:    */     {
/* 216:283 */       this.servicioUtilidad.cerrar(u);
/* 217:284 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 218:    */     }
/* 219:    */     catch (AS2Exception e)
/* 220:    */     {
/* 221:286 */       JsfUtil.addErrorMessage(e, "");
/* 222:287 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 223:288 */       e.printStackTrace();
/* 224:    */     }
/* 225:    */     catch (ExcepcionAS2 e)
/* 226:    */     {
/* 227:290 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 228:291 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 229:292 */       e.printStackTrace();
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void buscarEmpresaEvent()
/* 234:    */   {
/* 235:    */     try
/* 236:    */     {
/* 237:298 */       DetalleUtilidad rg = (DetalleUtilidad)this.dtRelacionDependencia.getRowData();
/* 238:299 */       ValidarIdentificacion.validarIdentificacion(true, rg.getRucEmpresaComplementaria());
/* 239:    */     }
/* 240:    */     catch (ExcepcionAS2Identification e)
/* 241:    */     {
/* 242:301 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 243:302 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 244:303 */       e.printStackTrace();
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void verificarDiasTrabajados()
/* 249:    */   {
/* 250:308 */     DetalleUtilidad du = (DetalleUtilidad)this.dtRelacionDependencia.getRowData();
/* 251:309 */     if ((du.getDiasRealesTrabajados() != null) && ((du.getDiasRealesTrabajados().compareTo(new BigDecimal(360)) > 0) || (du.getDiasRealesTrabajados().compareTo(BigDecimal.ZERO) < 0))) {
/* 252:310 */       du.setDiasRealesTrabajados(new BigDecimal(360));
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void verificarAnioFechaUtilidad()
/* 257:    */   {
/* 258:315 */     int anioUtilidad = getUtilidad().getAnio();
/* 259:316 */     int anioActual = Calendar.getInstance().get(1);
/* 260:317 */     if (anioUtilidad >= anioActual) {
/* 261:318 */       getUtilidad().setAnio(anioActual - 1);
/* 262:    */     }
/* 263:320 */     getUtilidad().setFechaPagoUtilidad(FuncionesUtiles.getFecha(14, 4, getUtilidad().getAnio() + 1));
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Utilidad getUtilidad()
/* 267:    */   {
/* 268:332 */     return this.utilidad;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setUtilidad(Utilidad utilidad)
/* 272:    */   {
/* 273:342 */     this.utilidad = utilidad;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public LazyDataModel<Utilidad> getListaUtilidad()
/* 277:    */   {
/* 278:351 */     return this.listaUtilidad;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setListaUtilidad(LazyDataModel<Utilidad> listaUtilidad)
/* 282:    */   {
/* 283:361 */     this.listaUtilidad = listaUtilidad;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public DataTable getDtUtilidad()
/* 287:    */   {
/* 288:370 */     return this.dtUtilidad;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setDtUtilidad(DataTable dtUtilidad)
/* 292:    */   {
/* 293:380 */     this.dtUtilidad = dtUtilidad;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public List<DetalleUtilidad> getListaDetalleUtilidad()
/* 297:    */   {
/* 298:388 */     this.listaDetalleUtilidad = new ArrayList();
/* 299:390 */     for (DetalleUtilidad du : this.utilidad.getListaDetalleUtilidad()) {
/* 300:391 */       if ((du.getEmpleado() == null) && (!du.isEliminado())) {
/* 301:392 */         this.listaDetalleUtilidad.add(du);
/* 302:    */       }
/* 303:    */     }
/* 304:395 */     return this.listaDetalleUtilidad;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setListaDetalleUtilidad(List<DetalleUtilidad> listaDetalleUtilidad)
/* 308:    */   {
/* 309:403 */     this.listaDetalleUtilidad = listaDetalleUtilidad;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public BigDecimal getValor10PEmpleados()
/* 313:    */   {
/* 314:410 */     return this.valor10PEmpleados;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setValor10PEmpleados(BigDecimal valor10pEmpleados)
/* 318:    */   {
/* 319:418 */     this.valor10PEmpleados = valor10pEmpleados;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public BigDecimal getValor5PCargas()
/* 323:    */   {
/* 324:425 */     return this.valor5PCargas;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setValor5PCargas(BigDecimal valor5pCargas)
/* 328:    */   {
/* 329:433 */     this.valor5PCargas = valor5pCargas;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public List<DetalleUtilidad> getListaDetalleUtilidadEmpleadosConRDEP()
/* 333:    */   {
/* 334:440 */     if (this.listaDetalleUtilidadEmpleadosConRDEP == null)
/* 335:    */     {
/* 336:441 */       this.listaDetalleUtilidadEmpleadosConRDEP = new ArrayList();
/* 337:443 */       for (DetalleUtilidad du : this.utilidad.getListaDetalleUtilidad()) {
/* 338:444 */         if (du.getEmpleado() != null) {
/* 339:445 */           this.listaDetalleUtilidadEmpleadosConRDEP.add(du);
/* 340:    */         }
/* 341:    */       }
/* 342:    */     }
/* 343:449 */     return this.listaDetalleUtilidadEmpleadosConRDEP;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setListaDetalleUtilidadEmpleadosConRDEP(List<DetalleUtilidad> listaDetalleUtilidadEmpleadosConRDEP)
/* 347:    */   {
/* 348:457 */     this.listaDetalleUtilidadEmpleadosConRDEP = listaDetalleUtilidadEmpleadosConRDEP;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public DataTable getDtDetalleUtilidades()
/* 352:    */   {
/* 353:464 */     return this.dtDetalleUtilidades;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setDtDetalleUtilidades(DataTable dtDetalleUtilidades)
/* 357:    */   {
/* 358:472 */     this.dtDetalleUtilidades = dtDetalleUtilidades;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public List<DetalleUtilidad> getListaDetalleUtilidadEmpleadosConRDEPFilter()
/* 362:    */   {
/* 363:479 */     return this.listaDetalleUtilidadEmpleadosConRDEPFilter;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setListaDetalleUtilidadEmpleadosConRDEPFilter(List<DetalleUtilidad> listaDetalleUtilidadEmpleadosConRDEPFilter)
/* 367:    */   {
/* 368:487 */     this.listaDetalleUtilidadEmpleadosConRDEPFilter = listaDetalleUtilidadEmpleadosConRDEPFilter;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public DataTable getDtRelacionDependencia()
/* 372:    */   {
/* 373:491 */     return this.dtRelacionDependencia;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setDtRelacionDependencia(DataTable dtRelacionDependencia)
/* 377:    */   {
/* 378:495 */     this.dtRelacionDependencia = dtRelacionDependencia;
/* 379:    */   }
/* 380:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.UtilidadBean
 * JD-Core Version:    0.7.0.1
 */