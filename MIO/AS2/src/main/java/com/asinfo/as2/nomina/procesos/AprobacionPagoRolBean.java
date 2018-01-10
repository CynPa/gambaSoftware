/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.AprobacionPagoRol;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.dao.DepartamentoDao;
/*   7:    */ import com.asinfo.as2.dao.EmpleadoDao;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  10:    */ import com.asinfo.as2.entities.Departamento;
/*  11:    */ import com.asinfo.as2.entities.Empleado;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.PagoRol;
/*  14:    */ import com.asinfo.as2.entities.Quincena;
/*  15:    */ import com.asinfo.as2.entities.Sucursal;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import java.math.BigDecimal;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.Iterator;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import javax.faces.model.SelectItem;
/*  30:    */ import javax.validation.constraints.NotNull;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ import org.primefaces.component.datatable.DataTable;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class AprobacionPagoRolBean
/*  37:    */   extends PageControllerAS2
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 1L;
/*  40:    */   @EJB
/*  41:    */   private ServicioPagoRol servicioPagoRol;
/*  42:    */   @EJB
/*  43:    */   private ServicioDepartamento servicioDepartamento;
/*  44: 64 */   private List<String> cabecerasIngresos = new ArrayList();
/*  45: 65 */   private List<String> cabecerasEgresos = new ArrayList();
/*  46: 66 */   private List<AprobacionPagoRol> datosAprobacionPagoRol = new ArrayList();
/*  47:    */   @NotNull
/*  48:    */   private PagoRol pagoRol;
/*  49:    */   private List<SelectItem> listaPagoRol;
/*  50:    */   private boolean aprobarTodos;
/*  51:    */   private Sucursal sucursal;
/*  52:    */   private Departamento departamento;
/*  53:    */   private List<Departamento> listaDepartamento;
/*  54:    */   private List<Departamento> listaDepartamentoAsiciados;
/*  55:    */   private List<Departamento> listaDepartamentoAsociadosFilter;
/*  56:    */   @EJB
/*  57:    */   private EmpleadoDao empleadaDao;
/*  58:    */   @EJB
/*  59:    */   private ServicioEmpresa servicioEmpresa;
/*  60:    */   @EJB
/*  61:    */   private DepartamentoDao departamentoDao;
/*  62:    */   private DataTable dtDepartamentoGrupo;
/*  63:    */   private BigDecimal totalIngresos;
/*  64:    */   private BigDecimal totalEgresos;
/*  65:    */   
/*  66:    */   public String editar()
/*  67:    */   {
/*  68:102 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  69:103 */     return "";
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String guardar()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:114 */       this.servicioPagoRol.guardar(this.pagoRol, this.datosAprobacionPagoRol);
/*  77:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  78:116 */       limpiar();
/*  79:    */     }
/*  80:    */     catch (ExcepcionAS2 e)
/*  81:    */     {
/*  82:118 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  83:119 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/*  84:120 */       e.printStackTrace();
/*  85:    */     }
/*  86:123 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String eliminar()
/*  90:    */   {
/*  91:133 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  92:134 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void resetFiltros() {}
/*  96:    */   
/*  97:    */   public String limpiar()
/*  98:    */   {
/*  99:148 */     this.datosAprobacionPagoRol = new ArrayList();
/* 100:149 */     this.pagoRol = new PagoRol();
/* 101:150 */     this.departamento = new Departamento();
/* 102:151 */     this.listaDepartamentoAsiciados = new ArrayList();
/* 103:152 */     this.totalEgresos = BigDecimal.ZERO;
/* 104:153 */     this.totalIngresos = BigDecimal.ZERO;
/* 105:    */     
/* 106:155 */     return null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String cargarDatos()
/* 110:    */   {
/* 111:165 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 112:    */     
/* 113:167 */     this.datosAprobacionPagoRol = this.servicioPagoRol.getDatosAprobacionPagoRol(idOrganizacion, this.sucursal, this.pagoRol, this.departamento);
/* 114:169 */     if (!this.datosAprobacionPagoRol.isEmpty())
/* 115:    */     {
/* 116:170 */       this.cabecerasIngresos = ((AprobacionPagoRol)this.datosAprobacionPagoRol.get(0)).getCabecerasIngresos();
/* 117:171 */       this.cabecerasEgresos = ((AprobacionPagoRol)this.datosAprobacionPagoRol.get(0)).getCabecerasEgresos();
/* 118:    */     }
/* 119:    */     else
/* 120:    */     {
/* 121:173 */       this.cabecerasIngresos = new ArrayList();
/* 122:174 */       this.cabecerasEgresos = new ArrayList();
/* 123:    */     }
/* 124:177 */     calcularTotales();
/* 125:    */     
/* 126:179 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String cargarDatosDepartamento()
/* 130:    */   {
/* 131:184 */     this.listaDepartamentoAsiciados.clear();
/* 132:    */     
/* 133:186 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 134:    */     
/* 135:188 */     this.datosAprobacionPagoRol = this.servicioPagoRol.getDatosAprobacionPagoRol(idOrganizacion, this.sucursal, this.pagoRol, this.departamento);
/* 136:190 */     if (!this.datosAprobacionPagoRol.isEmpty())
/* 137:    */     {
/* 138:191 */       this.cabecerasIngresos = ((AprobacionPagoRol)this.datosAprobacionPagoRol.get(0)).getCabecerasIngresos();
/* 139:192 */       this.cabecerasEgresos = ((AprobacionPagoRol)this.datosAprobacionPagoRol.get(0)).getCabecerasEgresos();
/* 140:    */     }
/* 141:    */     else
/* 142:    */     {
/* 143:194 */       this.cabecerasIngresos = new ArrayList();
/* 144:195 */       this.cabecerasEgresos = new ArrayList();
/* 145:    */     }
/* 146:198 */     HashMap<Departamento, AprobacionPagoRol> hmAprobacion = new HashMap();
/* 147:199 */     for (Iterator localIterator1 = this.datosAprobacionPagoRol.iterator(); localIterator1.hasNext();)
/* 148:    */     {
/* 149:199 */       apr = (AprobacionPagoRol)localIterator1.next();
/* 150:200 */       Empleado e = this.empleadaDao.bucarEmpleadoPorIdentificacion(apr.getIdentificacion(), idOrganizacion);
/* 151:201 */       d = this.departamentoDao.buscarPorEmpleado(e.getIdEmpleado());
/* 152:202 */       if (!hmAprobacion.containsKey(d))
/* 153:    */       {
/* 154:203 */         this.listaDepartamentoAsiciados.add(d);
/* 155:204 */         hmAprobacion.put(d, apr);
/* 156:    */       }
/* 157:207 */       for (Departamento depar : this.listaDepartamentoAsiciados) {
/* 158:208 */         if (depar.equals(d)) {
/* 159:209 */           depar.getListaAprobacionPagoRol().add(apr);
/* 160:    */         }
/* 161:    */       }
/* 162:    */     }
/* 163:    */     AprobacionPagoRol apr;
/* 164:    */     Departamento d;
/* 165:215 */     calcularTotales();
/* 166:216 */     return "";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void aprobarTodosListener()
/* 170:    */   {
/* 171:220 */     for (AprobacionPagoRol apRol : this.datosAprobacionPagoRol) {
/* 172:221 */       if (!apRol.isIndicadorCobrado()) {
/* 173:222 */         apRol.setIndicadorAprobado(true);
/* 174:    */       }
/* 175:    */     }
/* 176:226 */     for (Departamento dep : getListaDepartamentoAsiciados()) {
/* 177:227 */       dep.setNumeroEmpleadosPorAprobar(0);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void aprobarGrupoListener()
/* 182:    */   {
/* 183:233 */     Departamento depar = (Departamento)this.dtDepartamentoGrupo.getRowData();
/* 184:234 */     for (AprobacionPagoRol apr : depar.getListaAprobacionPagoRol())
/* 185:    */     {
/* 186:235 */       depar.setNumeroEmpleadosPorAprobar(0);
/* 187:236 */       if (!apr.isIndicadorCobrado()) {
/* 188:237 */         apr.setIndicadorAprobado(true);
/* 189:    */       }
/* 190:239 */       if (!apr.isIndicadorAprobado()) {
/* 191:240 */         depar.setNumeroEmpleadosPorAprobar(depar.getNumeroEmpleadosPorAprobar() + 1);
/* 192:    */       }
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void calcularTotales()
/* 197:    */   {
/* 198:247 */     this.totalEgresos = BigDecimal.ZERO;
/* 199:248 */     this.totalIngresos = BigDecimal.ZERO;
/* 200:249 */     for (AprobacionPagoRol apRol : this.datosAprobacionPagoRol)
/* 201:    */     {
/* 202:250 */       this.totalIngresos = this.totalIngresos.add(apRol.getTotalIngresos());
/* 203:251 */       this.totalEgresos = this.totalEgresos.add(apRol.getTotalEgresos());
/* 204:    */     }
/* 205:254 */     for (??? = this.listaDepartamentoAsiciados.iterator(); ???.hasNext();)
/* 206:    */     {
/* 207:254 */       d = (Departamento)???.next();
/* 208:255 */       d.setTotalEgresos(BigDecimal.ZERO);
/* 209:256 */       d.setTotalIngresos(BigDecimal.ZERO);
/* 210:257 */       d.setTotalIngresosT1(BigDecimal.ZERO);
/* 211:258 */       d.setTotalIngresosT2(BigDecimal.ZERO);
/* 212:259 */       d.setNumeroEmpleadosPorAprobar(0);
/* 213:260 */       for (AprobacionPagoRol apr : d.getListaAprobacionPagoRol())
/* 214:    */       {
/* 215:261 */         d.setTotalEgresos(d.getTotalEgresos().add(apr.getTotalEgresos()));
/* 216:262 */         d.setTotalIngresos(d.getTotalIngresos().add(apr.getTotalIngresos()));
/* 217:264 */         if (!apr.isIndicadorAprobado()) {
/* 218:265 */           d.setNumeroEmpleadosPorAprobar(d.getNumeroEmpleadosPorAprobar() + 1);
/* 219:    */         }
/* 220:268 */         if ((apr.getTotalIngresost_1() != null) && (apr.getTotalIngresost_2() != null))
/* 221:    */         {
/* 222:269 */           d.setTotalIngresosT1(d.getTotalIngresosT1().add(apr.getTotalIngresost_1()));
/* 223:270 */           d.setTotalIngresosT2(d.getTotalIngresosT2().add(apr.getTotalIngresost_2()));
/* 224:    */         }
/* 225:    */       }
/* 226:    */     }
/* 227:    */     Departamento d;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<AprobacionPagoRol> getDatosAprobacionPagoRol()
/* 231:    */   {
/* 232:283 */     return this.datosAprobacionPagoRol;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setDatosAprobacionPagoRol(List<AprobacionPagoRol> datosAprobacionPagoRol)
/* 236:    */   {
/* 237:293 */     this.datosAprobacionPagoRol = datosAprobacionPagoRol;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Sucursal getSucursal()
/* 241:    */   {
/* 242:302 */     return this.sucursal;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setSucursal(Sucursal sucursal)
/* 246:    */   {
/* 247:312 */     this.sucursal = sucursal;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public PagoRol getPagoRol()
/* 251:    */   {
/* 252:321 */     return this.pagoRol;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setPagoRol(PagoRol pagoRol)
/* 256:    */   {
/* 257:331 */     this.pagoRol = pagoRol;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public Departamento getDepartamento()
/* 261:    */   {
/* 262:340 */     return this.departamento;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setDepartamento(Departamento departamento)
/* 266:    */   {
/* 267:350 */     this.departamento = departamento;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public List<SelectItem> getListaPagoRol()
/* 271:    */   {
/* 272:360 */     if (this.listaPagoRol == null)
/* 273:    */     {
/* 274:361 */       List<PagoRol> lista = new ArrayList();
/* 275:362 */       Map<String, String> filters = new HashMap();
/* 276:363 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 277:364 */       filters.put("indicadorFiniquito", "false");
/* 278:    */       
/* 279:366 */       lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 280:367 */       if (this.listaPagoRol == null)
/* 281:    */       {
/* 282:368 */         this.listaPagoRol = new ArrayList();
/* 283:369 */         for (PagoRol pagoRol : lista)
/* 284:    */         {
/* 285:375 */           String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 286:376 */           SelectItem item = new SelectItem(pagoRol, label);
/* 287:377 */           this.listaPagoRol.add(item);
/* 288:    */         }
/* 289:    */       }
/* 290:    */     }
/* 291:382 */     return this.listaPagoRol;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setListaPagoRol(List<SelectItem> listaPagoRol)
/* 295:    */   {
/* 296:392 */     this.listaPagoRol = listaPagoRol;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<Departamento> getListaDepartamento()
/* 300:    */   {
/* 301:401 */     if (this.listaDepartamento == null) {
/* 302:402 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 303:    */     }
/* 304:404 */     return this.listaDepartamento;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setListaDepartamento(List<Departamento> listaDepartamento)
/* 308:    */   {
/* 309:414 */     this.listaDepartamento = listaDepartamento;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public List<String> getCabecerasIngresos()
/* 313:    */   {
/* 314:423 */     return this.cabecerasIngresos;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setCabecerasIngresos(List<String> cabecerasIngresos)
/* 318:    */   {
/* 319:433 */     this.cabecerasIngresos = cabecerasIngresos;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public List<String> getCabecerasEgresos()
/* 323:    */   {
/* 324:442 */     return this.cabecerasEgresos;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setCabecerasEgresos(List<String> cabecerasEgresos)
/* 328:    */   {
/* 329:452 */     this.cabecerasEgresos = cabecerasEgresos;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public boolean isAprobarTodos()
/* 333:    */   {
/* 334:461 */     return this.aprobarTodos;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setAprobarTodos(boolean aprobarTodos)
/* 338:    */   {
/* 339:471 */     this.aprobarTodos = aprobarTodos;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public BigDecimal getTotalIngresos()
/* 343:    */   {
/* 344:480 */     return this.totalIngresos;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setTotalIngresos(BigDecimal totalIngresos)
/* 348:    */   {
/* 349:490 */     this.totalIngresos = totalIngresos;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public BigDecimal getTotalEgresos()
/* 353:    */   {
/* 354:499 */     return this.totalEgresos;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setTotalEgresos(BigDecimal totalEgresos)
/* 358:    */   {
/* 359:509 */     this.totalEgresos = totalEgresos;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public List<Departamento> getListaDepartamentoAsiciados()
/* 363:    */   {
/* 364:513 */     if (this.listaDepartamentoAsiciados == null) {
/* 365:514 */       this.listaDepartamentoAsiciados = new ArrayList();
/* 366:    */     }
/* 367:516 */     return this.listaDepartamentoAsiciados;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setListaDepartamentoAsiciados(List<Departamento> listaDepartamentoAsiciados)
/* 371:    */   {
/* 372:520 */     this.listaDepartamentoAsiciados = listaDepartamentoAsiciados;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public DataTable getDtDepartamentoGrupo()
/* 376:    */   {
/* 377:524 */     return this.dtDepartamentoGrupo;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setDtDepartamentoGrupo(DataTable dtDepartamentoGrupo)
/* 381:    */   {
/* 382:528 */     this.dtDepartamentoGrupo = dtDepartamentoGrupo;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public List<Departamento> getListaDepartamentoAsociadosFilter()
/* 386:    */   {
/* 387:532 */     return this.listaDepartamentoAsociadosFilter;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setListaDepartamentoAsociadosFilter(List<Departamento> listaDepartamentoAsociadosFilter)
/* 391:    */   {
/* 392:536 */     this.listaDepartamentoAsociadosFilter = listaDepartamentoAsociadosFilter;
/* 393:    */   }
/* 394:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.AprobacionPagoRolBean
 * JD-Core Version:    0.7.0.1
 */