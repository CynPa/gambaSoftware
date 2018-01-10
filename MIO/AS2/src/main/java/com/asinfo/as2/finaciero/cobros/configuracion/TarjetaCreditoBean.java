/*   1:    */ package com.asinfo.as2.finaciero.cobros.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Banco;
/*   6:    */ import com.asinfo.as2.entities.Comision;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.CuentaContable;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.TarjetaCredito;
/*  13:    */ import com.asinfo.as2.entities.TipoTarjetaCredito;
/*  14:    */ import com.asinfo.as2.entities.VersionComision;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*  16:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  17:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioTarjetaCredito;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  19:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.Iterator;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import java.util.Map.Entry;
/*  29:    */ import javax.annotation.PostConstruct;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ManagedProperty;
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
/*  43:    */ public class TarjetaCreditoBean
/*  44:    */   extends PageControllerAS2
/*  45:    */ {
/*  46:    */   private static final long serialVersionUID = -9061218854698018535L;
/*  47:    */   @EJB
/*  48:    */   private ServicioTarjetaCredito servicioTarjetaCredito;
/*  49:    */   @EJB
/*  50:    */   private ServicioGenerico<PlanTarjetaCredito> servicioPlanTarjetaCredito;
/*  51:    */   @EJB
/*  52:    */   private ServicioGenerico<TipoTarjetaCredito> servicioTipoTarjetaCredito;
/*  53:    */   @EJB
/*  54:    */   private ServicioGenerico<Banco> servicioBanco;
/*  55:    */   @EJB
/*  56:    */   private ServicioGenerico<VersionComision> servicioVersionComision;
/*  57:    */   @EJB
/*  58:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  59:    */   private List<Comision> listaComisionTarjeta;
/*  60:    */   private List<PlanTarjetaCredito> listaPlanTarjetaCredito;
/*  61:    */   private List<TipoTarjetaCredito> listaTipoTarjetaCredito;
/*  62:    */   private List<Banco> listaBanco;
/*  63:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  64:    */   private List<VersionComision> listaVersionComision;
/*  65:    */   private VersionComision versionComision;
/*  66:    */   private VersionComision versionComisionSeleccionada;
/*  67:    */   private int codigo;
/*  68:    */   private TarjetaCredito tarjetaCredito;
/*  69:    */   private LazyDataModel<TarjetaCredito> listaTarjetaCredito;
/*  70:    */   private DataTable dataTableTarjetaCredito;
/*  71:    */   private DataTable dtComisionTarjeta;
/*  72:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  73:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  74:    */   private enumCuentaContableEditada cuentaContableEditada;
/*  75:    */   private CuentaContable cuentaContable;
/*  76:    */   private DataTable dtCuentaContable;
/*  77:    */   
/*  78:    */   private static enum enumCuentaContableEditada
/*  79:    */   {
/*  80: 97 */     CUENTA_CONTABLE_RETENCION_FUENTE,  CUENTA_CONTABLE_RETENCION_IVA,  CUENTA_CONTABLE_COMISION;
/*  81:    */     
/*  82:    */     private enumCuentaContableEditada() {}
/*  83:    */   }
/*  84:    */   
/*  85:    */   @PostConstruct
/*  86:    */   public void init()
/*  87:    */   {
/*  88:107 */     this.listaTarjetaCredito = new LazyDataModel()
/*  89:    */     {
/*  90:    */       private static final long serialVersionUID = 1L;
/*  91:    */       
/*  92:    */       public List<TarjetaCredito> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  93:    */       {
/*  94:113 */         List<TarjetaCredito> lista = new ArrayList();
/*  95:114 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  96:115 */         lista = TarjetaCreditoBean.this.servicioTarjetaCredito.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  97:116 */         TarjetaCreditoBean.this.listaTarjetaCredito.setRowCount(TarjetaCreditoBean.this.servicioTarjetaCredito.contarPorCriterio(filters));
/*  98:117 */         return lista;
/*  99:    */       }
/* 100:    */     };
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String editar()
/* 104:    */   {
/* 105:132 */     if ((this.tarjetaCredito != null) && (this.tarjetaCredito.getIdTarjetaCredito() != 0))
/* 106:    */     {
/* 107:133 */       this.tarjetaCredito = this.servicioTarjetaCredito.cargarDetalle(this.tarjetaCredito.getIdTarjetaCredito());
/* 108:134 */       setEditado(true);
/* 109:135 */       this.tarjetaCredito.setTipoTarjetaCredito(this.tarjetaCredito.getTipoTarjetaCredito());
/* 110:136 */       this.tarjetaCredito.setCuentaBancariaOrganizacion(this.tarjetaCredito.getCuentaBancariaOrganizacion());
/* 111:137 */       this.tarjetaCredito.setBanco(this.tarjetaCredito.getBanco());
/* 112:138 */       inicializaDatos();
/* 113:139 */       this.versionComisionSeleccionada = ((VersionComision)this.tarjetaCredito.getListaVersionComision().get(this.tarjetaCredito.getListaVersionComision().size() - 1));
/* 114:140 */       this.listaComisionTarjeta = this.versionComisionSeleccionada.getListaComision();
/* 115:141 */       this.codigo = this.versionComisionSeleccionada.getRowKey();
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:144 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 120:    */     }
/* 121:146 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String guardar()
/* 125:    */   {
/* 126:    */     try
/* 127:    */     {
/* 128:155 */       if (validarPlanTarjetaCredito())
/* 129:    */       {
/* 130:156 */         this.servicioTarjetaCredito.guardar(this.tarjetaCredito);
/* 131:157 */         limpiar();
/* 132:158 */         setEditado(false);
/* 133:159 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 134:    */       }
/* 135:    */     }
/* 136:    */     catch (Exception e)
/* 137:    */     {
/* 138:162 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 139:163 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 140:    */     }
/* 141:165 */     return "";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String eliminar()
/* 145:    */   {
/* 146:173 */     return "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String cargarDatos()
/* 150:    */   {
/* 151:181 */     return "";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String limpiar()
/* 155:    */   {
/* 156:186 */     crearTarjetaCredito();
/* 157:187 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void crearTarjetaCredito()
/* 161:    */   {
/* 162:194 */     this.tarjetaCredito = new TarjetaCredito();
/* 163:195 */     this.tarjetaCredito.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 164:196 */     this.tarjetaCredito.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 165:197 */     this.tarjetaCredito.setFechaDesde(new Date());
/* 166:198 */     this.tarjetaCredito.setFechaHasta(new Date());
/* 167:199 */     this.tarjetaCredito.setActivo(true);
/* 168:    */     
/* 169:201 */     inicializaDatos();
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void inicializaDatos()
/* 173:    */   {
/* 174:207 */     this.listaPlanTarjetaCredito = new ArrayList();
/* 175:208 */     this.listaTipoTarjetaCredito = new ArrayList();
/* 176:209 */     this.listaBanco = new ArrayList();
/* 177:210 */     this.listaVersionComision = new ArrayList();
/* 178:211 */     this.versionComisionSeleccionada = new VersionComision();
/* 179:212 */     this.versionComision = new VersionComision();
/* 180:213 */     HashMap<String, String> filtros = new HashMap();
/* 181:214 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 182:215 */     this.listaPlanTarjetaCredito = this.servicioPlanTarjetaCredito.obtenerListaCombo(PlanTarjetaCredito.class, "nombre", true, filtros);
/* 183:216 */     this.listaTipoTarjetaCredito = this.servicioTipoTarjetaCredito.obtenerListaCombo(TipoTarjetaCredito.class, "nombre", true, filtros);
/* 184:217 */     this.listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, filtros);
/* 185:    */     
/* 186:219 */     this.listaVersionComision = this.tarjetaCredito.getListaVersionComision();
/* 187:220 */     this.listaComisionTarjeta = this.versionComisionSeleccionada.getListaComision();
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String eliminarDetalle()
/* 191:    */   {
/* 192:225 */     Comision comision = (Comision)this.dtComisionTarjeta.getRowData();
/* 193:226 */     comision.setEliminado(true);
/* 194:227 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void onRowSelect(SelectEvent event)
/* 198:    */   {
/* 199:235 */     TarjetaCredito tipoTarjetaCredito = (TarjetaCredito)event.getObject();
/* 200:236 */     setTarjetaCredito(tipoTarjetaCredito);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void agregarDetalle()
/* 204:    */   {
/* 205:240 */     for (VersionComision vc : this.tarjetaCredito.getListaVersionComision()) {
/* 206:241 */       if (vc.getRowKey() == this.codigo)
/* 207:    */       {
/* 208:242 */         this.versionComisionSeleccionada = vc;
/* 209:243 */         Comision comision = new Comision();
/* 210:244 */         comision.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 211:245 */         comision.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 212:246 */         comision.setVersionComision(this.versionComisionSeleccionada);
/* 213:247 */         this.versionComisionSeleccionada.getListaComision().add(comision);
/* 214:248 */         this.listaComisionTarjeta = this.versionComisionSeleccionada.getListaComision();
/* 215:    */       }
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   public boolean validarPlanTarjetaCredito()
/* 220:    */   {
/* 221:255 */     buscarVersionComision();
/* 222:256 */     HashMap<String, String> planTarjetaCredito = new HashMap();
/* 223:257 */     for (Comision co : this.versionComisionSeleccionada.getListaComision())
/* 224:    */     {
/* 225:258 */       if (planTarjetaCredito.get(co.getPlanTarjetaCredito().getCodigo()) != null)
/* 226:    */       {
/* 227:259 */         addErrorMessage(getLanguageController().getMensaje("msg_error_plan_tarjeta_credito_repetido"));
/* 228:260 */         return false;
/* 229:    */       }
/* 230:262 */       planTarjetaCredito.put(co.getPlanTarjetaCredito().getCodigo(), co.getPlanTarjetaCredito().getCodigo());
/* 231:    */     }
/* 232:264 */     return true;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void cargarPlanes()
/* 236:    */   {
/* 237:269 */     HashMap<String, String> filtros = new HashMap();
/* 238:270 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 239:271 */     this.listaPlanTarjetaCredito = this.servicioPlanTarjetaCredito.obtenerListaCombo(PlanTarjetaCredito.class, "nombre", true, filtros);
/* 240:    */     
/* 241:273 */     HashMap<Integer, PlanTarjetaCredito> planes = new HashMap();
/* 242:274 */     for (Iterator localIterator = this.listaPlanTarjetaCredito.iterator(); localIterator.hasNext();)
/* 243:    */     {
/* 244:274 */       pt = (PlanTarjetaCredito)localIterator.next();
/* 245:275 */       planes.put(Integer.valueOf(pt.getId()), pt);
/* 246:    */     }
/* 247:    */     PlanTarjetaCredito pt;
/* 248:279 */     buscarVersionComision();
/* 249:280 */     if (this.versionComisionSeleccionada.getListaComision().size() == 0)
/* 250:    */     {
/* 251:281 */       for (PlanTarjetaCredito ptc : this.listaPlanTarjetaCredito)
/* 252:    */       {
/* 253:282 */         Comision comision = new Comision();
/* 254:283 */         comision.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 255:284 */         comision.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 256:285 */         comision.setVersionComision(this.versionComisionSeleccionada);
/* 257:286 */         comision.setPlanTarjetaCredito(ptc);
/* 258:287 */         this.versionComisionSeleccionada.getListaComision().add(comision);
/* 259:    */       }
/* 260:    */     }
/* 261:    */     else
/* 262:    */     {
/* 263:290 */       for (Comision comi : this.versionComisionSeleccionada.getListaComision()) {
/* 264:291 */         if (planes.get(Integer.valueOf(comi.getPlanTarjetaCredito().getId())) != null) {
/* 265:292 */           planes.remove(Integer.valueOf(comi.getPlanTarjetaCredito().getId()));
/* 266:    */         }
/* 267:    */       }
/* 268:296 */       for (Map.Entry<Integer, PlanTarjetaCredito> e : planes.entrySet())
/* 269:    */       {
/* 270:297 */         Comision comision = new Comision();
/* 271:298 */         comision.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 272:299 */         comision.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 273:300 */         comision.setVersionComision(this.versionComisionSeleccionada);
/* 274:301 */         comision.setPlanTarjetaCredito((PlanTarjetaCredito)e.getValue());
/* 275:302 */         this.versionComisionSeleccionada.getListaComision().add(comision);
/* 276:    */       }
/* 277:    */     }
/* 278:    */   }
/* 279:    */   
/* 280:    */   public List<Comision> getListaComisionTarjeta()
/* 281:    */   {
/* 282:308 */     List<Comision> lista = new ArrayList();
/* 283:309 */     for (VersionComision vc : this.tarjetaCredito.getListaVersionComision()) {
/* 284:310 */       if (vc.getRowKey() == this.codigo)
/* 285:    */       {
/* 286:311 */         this.versionComisionSeleccionada = vc;
/* 287:312 */         for (Comision com : this.versionComisionSeleccionada.getListaComision()) {
/* 288:313 */           if (!com.isEliminado()) {
/* 289:314 */             lista.add(com);
/* 290:    */           }
/* 291:    */         }
/* 292:    */       }
/* 293:    */     }
/* 294:319 */     return lista;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<SelectItem> getListaVersionesComision()
/* 298:    */   {
/* 299:323 */     List<SelectItem> lista = new ArrayList();
/* 300:324 */     for (VersionComision vc : getTarjetaCredito().getListaVersionComision()) {
/* 301:325 */       lista.add(new SelectItem(Integer.valueOf(vc.getRowKey()), vc.toString()));
/* 302:    */     }
/* 303:328 */     return lista;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public List<SelectItem> getListaVersion()
/* 307:    */   {
/* 308:333 */     List<SelectItem> listaTipoListaPrecios = new ArrayList();
/* 309:335 */     for (VersionComision vc : getTarjetaCredito().getListaVersionComision())
/* 310:    */     {
/* 311:336 */       SelectItem item = new SelectItem(vc.getCodigo() + "" + vc.getNombre(), vc.getCodigo() + " " + vc.getNombre());
/* 312:337 */       listaTipoListaPrecios.add(item);
/* 313:    */     }
/* 314:340 */     return listaTipoListaPrecios;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void nombreTarjetaCredito()
/* 318:    */   {
/* 319:344 */     if ((this.tarjetaCredito.getTipoTarjetaCredito() != null) && (this.tarjetaCredito.getBanco() != null)) {
/* 320:345 */       this.tarjetaCredito.setNombre(this.tarjetaCredito.getTipoTarjetaCredito().getNombre() + " " + this.tarjetaCredito.getBanco().getNombre());
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   private boolean validar()
/* 325:    */   {
/* 326:349 */     if (this.versionComision.getFechaDesde() == null) {
/* 327:350 */       return false;
/* 328:    */     }
/* 329:352 */     if ((this.versionComision.getFechaHasta() != null) && 
/* 330:353 */       (this.versionComision.getFechaDesde().after(this.versionComision.getFechaHasta()))) {
/* 331:354 */       return false;
/* 332:    */     }
/* 333:357 */     for (VersionComision ver : this.tarjetaCredito.getListaVersionComision())
/* 334:    */     {
/* 335:358 */       if ((ver.getRowKey() != this.versionComision.getRowKey()) && 
/* 336:359 */         (ver.getCodigo().equals(this.versionComision.getCodigo())))
/* 337:    */       {
/* 338:360 */         addErrorMessage(getLanguageController().getMensaje("msg_error_codigo_repetido"));
/* 339:361 */         return false;
/* 340:    */       }
/* 341:364 */       if (ver.getFechaHasta() != null)
/* 342:    */       {
/* 343:365 */         if (ver.getFechaDesde() != null)
/* 344:    */         {
/* 345:366 */           if (this.versionComision.getFechaHasta() != null)
/* 346:    */           {
/* 347:367 */             if ((this.versionComision.getFechaHasta().after(ver.getFechaDesde())) && (this.versionComision.getFechaHasta().before(ver.getFechaHasta()))) {
/* 348:368 */               return false;
/* 349:    */             }
/* 350:370 */             if ((this.versionComision.getFechaDesde().after(ver.getFechaDesde())) && 
/* 351:371 */               (this.versionComision.getFechaDesde().before(ver.getFechaHasta()))) {
/* 352:372 */               return false;
/* 353:    */             }
/* 354:    */           }
/* 355:    */         }
/* 356:    */         else {
/* 357:376 */           return false;
/* 358:    */         }
/* 359:    */       }
/* 360:378 */       else if ((this.versionComision.getFechaHasta() != null) && (
/* 361:379 */         (this.versionComision.getFechaDesde().after(ver.getFechaDesde())) || (this.versionComision.getFechaHasta().after(ver.getFechaDesde())))) {
/* 362:380 */         return false;
/* 363:    */       }
/* 364:    */     }
/* 365:384 */     return true;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void agregarVersionComision()
/* 369:    */   {
/* 370:388 */     buscarVersionComision();
/* 371:389 */     boolean ve = true;
/* 372:390 */     if (validar())
/* 373:    */     {
/* 374:392 */       for (VersionComision vercom : this.tarjetaCredito.getListaVersionComision()) {
/* 375:393 */         if (this.versionComision.getRowKey() == vercom.getRowKey()) {
/* 376:394 */           ve = false;
/* 377:    */         }
/* 378:    */       }
/* 379:397 */       if (ve)
/* 380:    */       {
/* 381:398 */         this.versionComisionSeleccionada.setFechaHasta(FuncionesUtiles.sumarFechaDiasMeses(this.versionComision.getFechaDesde(), -1));
/* 382:399 */         this.tarjetaCredito.getListaVersionComision().add(this.versionComision);
/* 383:400 */         this.codigo = this.versionComision.getRowKey();
/* 384:401 */         limpiarDialogo();
/* 385:    */       }
/* 386:    */     }
/* 387:    */     else
/* 388:    */     {
/* 389:404 */       limpiarDialogo();
/* 390:405 */       addErrorMessage(getLanguageController().getMensaje("msg_error_rango_de_fechas"));
/* 391:    */     }
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void buscarVersionComision()
/* 395:    */   {
/* 396:410 */     for (VersionComision sd : this.tarjetaCredito.getListaVersionComision()) {
/* 397:411 */       if (sd.getRowKey() == this.codigo) {
/* 398:412 */         this.versionComisionSeleccionada = sd;
/* 399:    */       }
/* 400:    */     }
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void copiarVersion()
/* 404:    */   {
/* 405:417 */     buscarVersionComision();
/* 406:418 */     this.versionComision.setCodigo(null);
/* 407:419 */     this.versionComision.setNombre(this.versionComisionSeleccionada.getNombre() + "_new");
/* 408:420 */     this.versionComision.setFechaDesde(this.versionComisionSeleccionada.getFechaDesde());
/* 409:421 */     this.versionComision.setFechaHasta(this.versionComisionSeleccionada.getFechaHasta());
/* 410:422 */     for (Comision c : this.versionComisionSeleccionada.getListaComision())
/* 411:    */     {
/* 412:423 */       Comision comision = new Comision();
/* 413:424 */       comision.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 414:425 */       comision.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 415:426 */       comision.setVersionComision(this.versionComision);
/* 416:427 */       comision.setPlanTarjetaCredito(c.getPlanTarjetaCredito());
/* 417:428 */       this.versionComision.getListaComision().add(comision);
/* 418:    */     }
/* 419:430 */     this.codigo = -1;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void editarVersion()
/* 423:    */   {
/* 424:435 */     buscarVersionComision();
/* 425:436 */     this.versionComision = this.versionComisionSeleccionada;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void nuevaVersion()
/* 429:    */   {
/* 430:440 */     this.codigo = -2;
/* 431:441 */     this.versionComision = new VersionComision();
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void eliminarVersion()
/* 435:    */   {
/* 436:445 */     buscarVersionComision();
/* 437:446 */     this.versionComisionSeleccionada.setEliminado(true);
/* 438:447 */     Iterator<VersionComision> i = this.tarjetaCredito.getListaVersionComision().iterator();
/* 439:448 */     while (i.hasNext())
/* 440:    */     {
/* 441:449 */       VersionComision s = (VersionComision)i.next();
/* 442:450 */       if (s.getRowKey() == this.codigo) {
/* 443:451 */         i.remove();
/* 444:    */       }
/* 445:    */     }
/* 446:454 */     this.listaVersionComision = this.tarjetaCredito.getListaVersionComision();
/* 447:455 */     this.listaComisionTarjeta = new ArrayList();
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void limpiarDialogo()
/* 451:    */   {
/* 452:459 */     this.versionComision = new VersionComision();
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void cargarCuentaContable(SelectEvent event)
/* 456:    */   {
/* 457:463 */     this.cuentaContable = ((CuentaContable)event.getObject());
/* 458:464 */     switch (2.$SwitchMap$com$asinfo$as2$finaciero$cobros$configuracion$TarjetaCreditoBean$enumCuentaContableEditada[this.cuentaContableEditada.ordinal()])
/* 459:    */     {
/* 460:    */     case 1: 
/* 461:467 */       this.tarjetaCredito.setCuentaContableRetencionFuente(this.cuentaContable);
/* 462:468 */       break;
/* 463:    */     case 2: 
/* 464:470 */       this.tarjetaCredito.setCuentaContableRetencionIva(this.cuentaContable);
/* 465:471 */       break;
/* 466:    */     case 3: 
/* 467:474 */       this.tarjetaCredito.setCuentaContableIvaComision(this.cuentaContable);
/* 468:475 */       break;
/* 469:    */     }
/* 470:    */   }
/* 471:    */   
/* 472:    */   public TarjetaCredito getTarjetaCredito()
/* 473:    */   {
/* 474:484 */     return this.tarjetaCredito;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public void setTarjetaCredito(TarjetaCredito tarjetaCredito)
/* 478:    */   {
/* 479:488 */     this.tarjetaCredito = tarjetaCredito;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public LazyDataModel<TarjetaCredito> getListaTarjetaCredito()
/* 483:    */   {
/* 484:492 */     return this.listaTarjetaCredito;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public void setListaTarjetaCredito(LazyDataModel<TarjetaCredito> listaTarjetaCredito)
/* 488:    */   {
/* 489:496 */     this.listaTarjetaCredito = listaTarjetaCredito;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public DataTable getDataTableTarjetaCredito()
/* 493:    */   {
/* 494:500 */     return this.dataTableTarjetaCredito;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public void setDataTableTarjetaCredito(DataTable dataTableTarjetaCredito)
/* 498:    */   {
/* 499:504 */     this.dataTableTarjetaCredito = dataTableTarjetaCredito;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public DataTable getDtComisionTarjeta()
/* 503:    */   {
/* 504:508 */     return this.dtComisionTarjeta;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public void setDtComisionTarjeta(DataTable dtComisionTarjeta)
/* 508:    */   {
/* 509:512 */     this.dtComisionTarjeta = dtComisionTarjeta;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public List<PlanTarjetaCredito> getListaPlanTarjetaCredito()
/* 513:    */   {
/* 514:516 */     return this.listaPlanTarjetaCredito;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public void setListaPlanTarjetaCredito(List<PlanTarjetaCredito> listaPlanTarjetaCredito)
/* 518:    */   {
/* 519:520 */     this.listaPlanTarjetaCredito = listaPlanTarjetaCredito;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public List<TipoTarjetaCredito> getListaTipoTarjetaCredito()
/* 523:    */   {
/* 524:524 */     return this.listaTipoTarjetaCredito;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public void setListaTipoTarjetaCredito(List<TipoTarjetaCredito> listaTipoTarjetaCredito)
/* 528:    */   {
/* 529:528 */     this.listaTipoTarjetaCredito = listaTipoTarjetaCredito;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public List<Banco> getListaBanco()
/* 533:    */   {
/* 534:532 */     return this.listaBanco;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public void setListaBanco(List<Banco> listaBanco)
/* 538:    */   {
/* 539:536 */     this.listaBanco = listaBanco;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 543:    */   {
/* 544:541 */     if (this.listaCuentaBancariaOrganizacion == null)
/* 545:    */     {
/* 546:542 */       this.listaCuentaBancariaOrganizacion = new ArrayList();
/* 547:543 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 548:544 */       filters.put("tipoCuentaBancariaOrganizacion", TipoCuentaBancariaOrganizacion.TARJETA.toString());
/* 549:545 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, filters);
/* 550:    */     }
/* 551:548 */     return this.listaCuentaBancariaOrganizacion;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 555:    */   {
/* 556:552 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public VersionComision getVersionComision()
/* 560:    */   {
/* 561:556 */     return this.versionComision;
/* 562:    */   }
/* 563:    */   
/* 564:    */   public void setVersionComision(VersionComision versionComision)
/* 565:    */   {
/* 566:560 */     this.versionComision = versionComision;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public VersionComision getVersionComisionSeleccionada()
/* 570:    */   {
/* 571:564 */     return this.versionComisionSeleccionada;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public void setVersionComisionSeleccionada(VersionComision versionComisionSeleccionada)
/* 575:    */   {
/* 576:568 */     this.versionComisionSeleccionada = versionComisionSeleccionada;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public List<VersionComision> getListaVersionComision()
/* 580:    */   {
/* 581:572 */     return this.listaVersionComision;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public void setListaVersionComision(List<VersionComision> listaVersionComision)
/* 585:    */   {
/* 586:576 */     this.listaVersionComision = listaVersionComision;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public void setListaComisionTarjeta(List<Comision> listaComisionTarjeta)
/* 590:    */   {
/* 591:580 */     this.listaComisionTarjeta = listaComisionTarjeta;
/* 592:    */   }
/* 593:    */   
/* 594:    */   public int getCodigo()
/* 595:    */   {
/* 596:584 */     buscarVersionComision();
/* 597:585 */     return this.codigo;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public void setCodigo(int codigo)
/* 601:    */   {
/* 602:589 */     buscarVersionComision();
/* 603:590 */     this.codigo = codigo;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 607:    */   {
/* 608:594 */     return this.listaCuentaContableBean;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 612:    */   {
/* 613:598 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 614:    */   }
/* 615:    */   
/* 616:    */   public enumCuentaContableEditada getCuentaContableEditada()
/* 617:    */   {
/* 618:602 */     return this.cuentaContableEditada;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public void setCuentaContableEditada(enumCuentaContableEditada cuentaContableEditada)
/* 622:    */   {
/* 623:606 */     this.cuentaContableEditada = cuentaContableEditada;
/* 624:    */   }
/* 625:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.configuracion.TarjetaCreditoBean
 * JD-Core Version:    0.7.0.1
 */