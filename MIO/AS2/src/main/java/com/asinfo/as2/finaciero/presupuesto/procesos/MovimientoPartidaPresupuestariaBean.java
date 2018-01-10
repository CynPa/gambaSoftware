/*   1:    */ package com.asinfo.as2.finaciero.presupuesto.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.CuentaContable;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Ejercicio;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.presupuesto.DetalleMovimientoPartidaPresupuestaria;
/*  12:    */ import com.asinfo.as2.entities.presupuesto.DetallePresupuesto;
/*  13:    */ import com.asinfo.as2.entities.presupuesto.MovimientoPartidaPresupuestaria;
/*  14:    */ import com.asinfo.as2.entities.presupuesto.Presupuesto;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  18:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  19:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  20:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  21:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioEjercicio;
/*  23:    */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioMovimientoPartidaPresupuestaria;
/*  24:    */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioPresupuesto;
/*  25:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  26:    */ import com.asinfo.as2.util.AppUtil;
/*  27:    */ import com.asinfo.as2.utils.JsfUtil;
/*  28:    */ import java.math.BigDecimal;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.HashMap;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import javax.annotation.PostConstruct;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.faces.bean.ManagedBean;
/*  36:    */ import javax.faces.bean.ManagedProperty;
/*  37:    */ import javax.faces.bean.ViewScoped;
/*  38:    */ import javax.faces.model.SelectItem;
/*  39:    */ import org.apache.log4j.Logger;
/*  40:    */ import org.primefaces.component.datatable.DataTable;
/*  41:    */ import org.primefaces.event.SelectEvent;
/*  42:    */ import org.primefaces.model.LazyDataModel;
/*  43:    */ import org.primefaces.model.SortOrder;
/*  44:    */ 
/*  45:    */ @ManagedBean
/*  46:    */ @ViewScoped
/*  47:    */ public class MovimientoPartidaPresupuestariaBean
/*  48:    */   extends PageControllerAS2
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = -4939703199824253624L;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioMovimientoPartidaPresupuestaria servicioMovimientoPartidaPresupuestaria;
/*  53:    */   @EJB
/*  54:    */   private transient ServicioDocumento servicioDocumento;
/*  55:    */   @EJB
/*  56:    */   private transient ServicioPresupuesto servicioPresupuesto;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioEjercicio servicioEjercicio;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioDimensionContable servicioDimensionContable;
/*  61:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  62:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  63:    */   private MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria;
/*  64:    */   private DetalleMovimientoPartidaPresupuestaria detalleMovimientoPartidaPresupuestaria;
/*  65:    */   private CuentaContable cuentaContable;
/*  66:    */   private Presupuesto presupuesto;
/*  67: 70 */   Map<String, DetallePresupuesto> hashDetallesPresupuesto = new HashMap();
/*  68: 71 */   private boolean presupuestoCargado = false;
/*  69: 72 */   private boolean renderColumns = true;
/*  70:    */   private List<Documento> listaDocumento;
/*  71:    */   private List<Ejercicio> listaEjercicio;
/*  72:    */   private List<DimensionContable> listaDimensionContable;
/*  73:    */   private DataTable dtDetalleMovimiento;
/*  74:    */   private LazyDataModel<MovimientoPartidaPresupuestaria> listaMovimientoPartidaPresupuestaria;
/*  75:    */   
/*  76:    */   @PostConstruct
/*  77:    */   public void init()
/*  78:    */   {
/*  79: 81 */     this.listaMovimientoPartidaPresupuestaria = new LazyDataModel()
/*  80:    */     {
/*  81:    */       private static final long serialVersionUID = 1L;
/*  82:    */       
/*  83:    */       public List<MovimientoPartidaPresupuestaria> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  84:    */       {
/*  85: 86 */         List<MovimientoPartidaPresupuestaria> lista = new ArrayList();
/*  86: 87 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  87: 88 */         MovimientoPartidaPresupuestariaBean.this.agregarFiltroOrganizacion(filters);
/*  88: 89 */         lista = MovimientoPartidaPresupuestariaBean.this.servicioMovimientoPartidaPresupuestaria.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  89: 90 */         MovimientoPartidaPresupuestariaBean.this.listaMovimientoPartidaPresupuestaria.setRowCount(MovimientoPartidaPresupuestariaBean.this.servicioMovimientoPartidaPresupuestaria.contarPorCriterio(filters));
/*  90: 91 */         return lista;
/*  91:    */       }
/*  92:    */     };
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String editar()
/*  96:    */   {
/*  97: 98 */     if ((getMovimientoPartidaPresupuestaria() != null) && (getMovimientoPartidaPresupuestaria().getId() > 0))
/*  98:    */     {
/*  99: 99 */       if ((Estado.ANULADO.equals(this.movimientoPartidaPresupuestaria.getEstado())) || 
/* 100:100 */         (Estado.APROBADO.equals(this.movimientoPartidaPresupuestaria.getEstado()))) {
/* 101:101 */         addInfoMessage(getLanguageController().getMensaje("msg_error_editar_movimiento_aprobado_anulado"));
/* 102:    */       } else {
/* 103:    */         try
/* 104:    */         {
/* 105:104 */           setMovimientoPartidaPresupuestaria(this.servicioMovimientoPartidaPresupuestaria.cargarDetalle(getMovimientoPartidaPresupuestaria()
/* 106:105 */             .getId()));
/* 107:106 */           cargarPresupuesto();
/* 108:107 */           this.servicioMovimientoPartidaPresupuestaria.actualizarSaldos(this.movimientoPartidaPresupuestaria, this.presupuesto);
/* 109:108 */           actualizarPanelDetalle();
/* 110:109 */           setEditado(true);
/* 111:    */         }
/* 112:    */         catch (Exception e)
/* 113:    */         {
/* 114:111 */           addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 115:112 */           LOG.error("ERROR AL EDITAR MOVIMIENTO PARTIDA PRESUPUESTARIA:", e);
/* 116:    */         }
/* 117:    */       }
/* 118:    */     }
/* 119:    */     else {
/* 120:116 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 121:    */     }
/* 122:119 */     return null;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String guardar()
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:125 */       if (getListaDetalleMovimientoPartidaPresupuestaria().size() > 0)
/* 130:    */       {
/* 131:126 */         this.servicioMovimientoPartidaPresupuestaria.guardar(getMovimientoPartidaPresupuestaria(), this.presupuesto);
/* 132:127 */         limpiar();
/* 133:128 */         setEditado(false);
/* 134:    */       }
/* 135:    */       else
/* 136:    */       {
/* 137:130 */         addErrorMessage(getLanguageController().getMensaje("msg_error_detalles_vacios"));
/* 138:    */       }
/* 139:    */     }
/* 140:    */     catch (AS2Exception e)
/* 141:    */     {
/* 142:133 */       JsfUtil.addErrorMessage(e, "");
/* 143:134 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/* 144:135 */       e.printStackTrace();
/* 145:    */     }
/* 146:    */     catch (ExcepcionAS2 e)
/* 147:    */     {
/* 148:137 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 149:138 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/* 150:139 */       e.printStackTrace();
/* 151:    */     }
/* 152:141 */     return null;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String eliminar()
/* 156:    */   {
/* 157:147 */     return null;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String limpiar()
/* 161:    */   {
/* 162:152 */     setMovimientoPartidaPresupuestaria(new MovimientoPartidaPresupuestaria());
/* 163:153 */     getMovimientoPartidaPresupuestaria().setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 164:154 */     getMovimientoPartidaPresupuestaria().setEstado(Estado.ELABORADO);
/* 165:155 */     this.presupuestoCargado = false;
/* 166:156 */     return null;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String cargarDatos()
/* 170:    */   {
/* 171:162 */     return null;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String actualizarPanelDetalle()
/* 175:    */   {
/* 176:166 */     if ((getMovimientoPartidaPresupuestaria().getDocumento() != null) && (getMovimientoPartidaPresupuestaria().getDocumento().getOperacion() == 0)) {
/* 177:167 */       this.renderColumns = true;
/* 178:    */     } else {
/* 179:169 */       this.renderColumns = false;
/* 180:    */     }
/* 181:171 */     return null;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void agregarDetalle()
/* 185:    */   {
/* 186:175 */     DetalleMovimientoPartidaPresupuestaria dmumt = new DetalleMovimientoPartidaPresupuestaria();
/* 187:    */     
/* 188:177 */     dmumt.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 189:178 */     dmumt.setMovimientoPartidaPresupuestaria(this.movimientoPartidaPresupuestaria);
/* 190:179 */     getMovimientoPartidaPresupuestaria().getListaDetalleMovimientoPartidaPresupuestaria().add(dmumt);
/* 191:    */   }
/* 192:    */   
/* 193:    */   private void cargarPresupuesto()
/* 194:    */   {
/* 195:183 */     if (!this.presupuestoCargado)
/* 196:    */     {
/* 197:184 */       this.presupuestoCargado = true;
/* 198:185 */       this.presupuesto = this.servicioPresupuesto.buscarPresupuestoPorEjercicio(getMovimientoPartidaPresupuestaria().getEjercicio().getId(), 
/* 199:186 */         AppUtil.getOrganizacion().getId());
/* 200:187 */       this.presupuesto = this.servicioPresupuesto.cargarDetalle(this.presupuesto.getId());
/* 201:189 */       for (DetallePresupuesto dpr : this.presupuesto.getListaDetallePresupuesto()) {
/* 202:190 */         this.hashDetallesPresupuesto.put(dpr.getCuentaContable().getCodigo() + "~" + dpr.getDimensionContable().getId(), dpr);
/* 203:    */       }
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String seleccionarCuentaContable(SelectEvent event)
/* 208:    */   {
/* 209:196 */     this.cuentaContable = ((CuentaContable)event.getObject());
/* 210:198 */     if (getMovimientoPartidaPresupuestaria().getEjercicio() == null)
/* 211:    */     {
/* 212:199 */       addErrorMessage(getLanguageController().getMensaje("msg_error_seleccione_ejercicio"));
/* 213:    */     }
/* 214:    */     else
/* 215:    */     {
/* 216:201 */       cargarPresupuesto();
/* 217:202 */       boolean existeCuenta = false;
/* 218:203 */       for (String codigo : this.hashDetallesPresupuesto.keySet()) {
/* 219:204 */         if (codigo.contains(this.cuentaContable.getCodigo())) {
/* 220:205 */           existeCuenta = true;
/* 221:    */         }
/* 222:    */       }
/* 223:208 */       if (existeCuenta)
/* 224:    */       {
/* 225:209 */         if ((this.detalleMovimientoPartidaPresupuestaria == null) || (this.detalleMovimientoPartidaPresupuestaria.getCuentaContableOrigen() == null))
/* 226:    */         {
/* 227:210 */           DetalleMovimientoPartidaPresupuestaria dmumt = new DetalleMovimientoPartidaPresupuestaria();
/* 228:211 */           dmumt.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 229:212 */           dmumt.setMovimientoPartidaPresupuestaria(this.movimientoPartidaPresupuestaria);
/* 230:213 */           dmumt.setCuentaContableOrigen(this.cuentaContable);
/* 231:214 */           getMovimientoPartidaPresupuestaria().getListaDetalleMovimientoPartidaPresupuestaria().add(dmumt);
/* 232:    */         }
/* 233:    */         else
/* 234:    */         {
/* 235:216 */           this.detalleMovimientoPartidaPresupuestaria.setCuentaContableDestino(this.cuentaContable);
/* 236:217 */           this.detalleMovimientoPartidaPresupuestaria = null;
/* 237:    */         }
/* 238:    */       }
/* 239:    */       else {
/* 240:220 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cuenta_contable_presupuesto"));
/* 241:    */       }
/* 242:    */     }
/* 243:223 */     return null;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public String actualizarSaldoOrigen(DetalleMovimientoPartidaPresupuestaria dmpp)
/* 247:    */   {
/* 248:227 */     if ((dmpp.getCuentaContableOrigen() != null) && (dmpp.getDimensionContableOrigen() != null))
/* 249:    */     {
/* 250:228 */       if (this.hashDetallesPresupuesto.get(dmpp.getCuentaContableOrigen().getCodigo() + "~" + dmpp.getDimensionContableOrigen().getId()) != null) {
/* 251:230 */         if (((DetallePresupuesto)this.hashDetallesPresupuesto.get(dmpp.getCuentaContableOrigen().getCodigo() + "~" + dmpp.getDimensionContableOrigen().getId())).getDimensionContable().equals(dmpp.getDimensionContableOrigen()))
/* 252:    */         {
/* 253:233 */           DetallePresupuesto d = (DetallePresupuesto)this.hashDetallesPresupuesto.get(dmpp.getCuentaContableOrigen().getCodigo() + "~" + dmpp.getDimensionContableOrigen().getId());
/* 254:234 */           Mes mes = getMovimientoPartidaPresupuestaria().getMesOrigen();
/* 255:    */           
/* 256:    */ 
/* 257:237 */           dmpp.setSaldoOrigen(obtenerSaldo(d, mes));
/* 258:    */           break label229;
/* 259:    */         }
/* 260:    */       }
/* 261:239 */       dmpp.setSaldoOrigen(null);
/* 262:240 */       addErrorMessage(
/* 263:241 */         getLanguageController().getMensaje("msg_error_dimension_contable_seleccionada_no_coincide_cuenta_contable"));
/* 264:    */     }
/* 265:    */     else
/* 266:    */     {
/* 267:244 */       dmpp.setSaldoOrigen(null);
/* 268:245 */       addErrorMessage(getLanguageController().getMensaje("msg_error_dimension_contable_seleccionada_no_coincide_cuenta_contable"));
/* 269:    */     }
/* 270:    */     label229:
/* 271:247 */     return null;
/* 272:    */   }
/* 273:    */   
/* 274:    */   private BigDecimal obtenerSaldo(DetallePresupuesto d, Mes mes)
/* 275:    */   {
/* 276:251 */     BigDecimal saldo = BigDecimal.ZERO;
/* 277:252 */     switch (2.$SwitchMap$com$asinfo$as2$enumeraciones$Mes[mes.ordinal()])
/* 278:    */     {
/* 279:    */     case 1: 
/* 280:254 */       saldo = d.getValorCalculadoEnero();
/* 281:255 */       break;
/* 282:    */     case 2: 
/* 283:257 */       saldo = d.getValorCalculadoFebrero();
/* 284:258 */       break;
/* 285:    */     case 3: 
/* 286:260 */       saldo = d.getValorCalculadoMarzo();
/* 287:261 */       break;
/* 288:    */     case 4: 
/* 289:263 */       saldo = d.getValorCalculadoAbril();
/* 290:264 */       break;
/* 291:    */     case 5: 
/* 292:266 */       saldo = d.getValorCalculadoMayo();
/* 293:267 */       break;
/* 294:    */     case 6: 
/* 295:269 */       saldo = d.getValorCalculadoJunio();
/* 296:270 */       break;
/* 297:    */     case 7: 
/* 298:272 */       saldo = d.getValorCalculadoJulio();
/* 299:273 */       break;
/* 300:    */     case 8: 
/* 301:275 */       saldo = d.getValorCalculadoAgosto();
/* 302:276 */       break;
/* 303:    */     case 9: 
/* 304:278 */       saldo = d.getValorCalculadoSeptiembre();
/* 305:279 */       break;
/* 306:    */     case 10: 
/* 307:281 */       saldo = d.getValorCalculadoOctubre();
/* 308:282 */       break;
/* 309:    */     case 11: 
/* 310:284 */       saldo = d.getValorCalculadoNoviembre();
/* 311:285 */       break;
/* 312:    */     case 12: 
/* 313:287 */       saldo = d.getValorCalculadoDiciembre();
/* 314:288 */       break;
/* 315:    */     }
/* 316:294 */     return saldo;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public String actualizarSaldoDestino(DetalleMovimientoPartidaPresupuestaria dmpp)
/* 320:    */   {
/* 321:298 */     if ((dmpp.getCuentaContableDestino() != null) && (dmpp.getDimensionContableDestino() != null))
/* 322:    */     {
/* 323:299 */       if (this.hashDetallesPresupuesto.get(dmpp.getCuentaContableDestino().getCodigo() + "~" + dmpp.getDimensionContableDestino().getId()) != null) {
/* 324:301 */         if (((DetallePresupuesto)this.hashDetallesPresupuesto.get(dmpp.getCuentaContableDestino().getCodigo() + "~" + dmpp.getDimensionContableDestino().getId())).getDimensionContable().equals(dmpp.getDimensionContableDestino()))
/* 325:    */         {
/* 326:304 */           DetallePresupuesto d = (DetallePresupuesto)this.hashDetallesPresupuesto.get(dmpp.getCuentaContableDestino().getCodigo() + "~" + dmpp.getDimensionContableOrigen().getId());
/* 327:305 */           Mes mes = getMovimientoPartidaPresupuestaria().getMesDestino();
/* 328:    */           
/* 329:307 */           dmpp.setSaldoDestino(obtenerSaldo(d, mes));
/* 330:    */           break label229;
/* 331:    */         }
/* 332:    */       }
/* 333:309 */       dmpp.setSaldoDestino(null);
/* 334:310 */       addErrorMessage(getLanguageController()
/* 335:311 */         .getMensaje("msg_error_dimension_contable_seleccionada_no_coincide_cuenta_contable"));
/* 336:    */     }
/* 337:    */     else
/* 338:    */     {
/* 339:314 */       dmpp.setSaldoDestino(null);
/* 340:315 */       addErrorMessage(getLanguageController().getMensaje("msg_error_dimension_contable_seleccionada_no_coincide_cuenta_contable"));
/* 341:    */     }
/* 342:    */     label229:
/* 343:317 */     return null;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public String eliminarDetalle()
/* 347:    */   {
/* 348:321 */     DetalleMovimientoPartidaPresupuestaria d = (DetalleMovimientoPartidaPresupuestaria)this.dtDetalleMovimiento.getRowData();
/* 349:322 */     d.setEliminado(true);
/* 350:323 */     return "";
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void totalizar()
/* 354:    */   {
/* 355:332 */     BigDecimal total = BigDecimal.ZERO;
/* 356:333 */     for (DetalleMovimientoPartidaPresupuestaria dmpp : getListaDetalleMovimientoPartidaPresupuestaria()) {
/* 357:334 */       if (dmpp.getValor() != null) {
/* 358:335 */         total = total.add(dmpp.getValor());
/* 359:    */       }
/* 360:    */     }
/* 361:338 */     getMovimientoPartidaPresupuestaria().setValorTotal(total);
/* 362:    */   }
/* 363:    */   
/* 364:    */   public MovimientoPartidaPresupuestaria getMovimientoPartidaPresupuestaria()
/* 365:    */   {
/* 366:343 */     return this.movimientoPartidaPresupuestaria;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setMovimientoPartidaPresupuestaria(MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria)
/* 370:    */   {
/* 371:347 */     this.movimientoPartidaPresupuestaria = movimientoPartidaPresupuestaria;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public LazyDataModel<MovimientoPartidaPresupuestaria> getListaMovimientoPartidaPresupuestaria()
/* 375:    */   {
/* 376:351 */     return this.listaMovimientoPartidaPresupuestaria;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setListaMovimientoPartidaPresupuestaria(LazyDataModel<MovimientoPartidaPresupuestaria> listaMovimientoPartidaPresupuestaria)
/* 380:    */   {
/* 381:355 */     this.listaMovimientoPartidaPresupuestaria = listaMovimientoPartidaPresupuestaria;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public List<Documento> getListaDocumento()
/* 385:    */   {
/* 386:    */     try
/* 387:    */     {
/* 388:360 */       if (this.listaDocumento == null)
/* 389:    */       {
/* 390:361 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.TRANSFERENCIA_PARTIDA_PRESUPUESTARIA, 
/* 391:362 */           AppUtil.getOrganizacion().getId());
/* 392:363 */         this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.MOVIMIENTO_PARTIDA_PRESUPUESTARIA, 
/* 393:364 */           AppUtil.getOrganizacion().getId()));
/* 394:    */       }
/* 395:    */     }
/* 396:    */     catch (ExcepcionAS2 e)
/* 397:    */     {
/* 398:367 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 399:    */     }
/* 400:370 */     return this.listaDocumento;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public List<SelectItem> getListaMes()
/* 404:    */   {
/* 405:374 */     ArrayList<SelectItem> lista = new ArrayList();
/* 406:375 */     for (Mes mes : Mes.values()) {
/* 407:376 */       lista.add(new SelectItem(mes, mes.getNombre()));
/* 408:    */     }
/* 409:378 */     return lista;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public List<Ejercicio> getListaEjercicio()
/* 413:    */   {
/* 414:382 */     if (this.listaEjercicio == null)
/* 415:    */     {
/* 416:383 */       Map<String, String> filters = new HashMap();
/* 417:384 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 418:385 */       this.listaEjercicio = this.servicioEjercicio.obtenerListaCombo("nombre", false, filters);
/* 419:    */     }
/* 420:387 */     return this.listaEjercicio;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public List<DetalleMovimientoPartidaPresupuestaria> getListaDetalleMovimientoPartidaPresupuestaria()
/* 424:    */   {
/* 425:397 */     List<DetalleMovimientoPartidaPresupuestaria> detalle = new ArrayList();
/* 426:398 */     for (DetalleMovimientoPartidaPresupuestaria dm : getMovimientoPartidaPresupuestaria().getListaDetalleMovimientoPartidaPresupuestaria()) {
/* 427:399 */       if (!dm.isEliminado()) {
/* 428:400 */         detalle.add(dm);
/* 429:    */       }
/* 430:    */     }
/* 431:404 */     return detalle;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public DataTable getDtDetalleMovimiento()
/* 435:    */   {
/* 436:408 */     return this.dtDetalleMovimiento;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void setDtDetalleMovimiento(DataTable dtDetalleMovimiento)
/* 440:    */   {
/* 441:412 */     this.dtDetalleMovimiento = dtDetalleMovimiento;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public List<DimensionContable> getListaDimensionContable()
/* 445:    */   {
/* 446:416 */     if (this.listaDimensionContable == null)
/* 447:    */     {
/* 448:417 */       this.listaDimensionContable = new ArrayList();
/* 449:418 */       for (DimensionContable dc : this.servicioDimensionContable.obtenerDimensionContablePorUsuario(AppUtil.getOrganizacion().getId(), 
/* 450:419 */         AppUtil.getUsuarioEnSesion().getIdUsuario(), 0, true)) {
/* 451:420 */         if (dc.isIndicadorMovimiento()) {
/* 452:421 */           this.listaDimensionContable.add(dc);
/* 453:    */         }
/* 454:    */       }
/* 455:    */     }
/* 456:425 */     return this.listaDimensionContable;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 460:    */   {
/* 461:429 */     return this.listaCuentaContableBean;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 465:    */   {
/* 466:433 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public DetalleMovimientoPartidaPresupuestaria getDetalleMovimientoPartidaPresupuestaria()
/* 470:    */   {
/* 471:437 */     return this.detalleMovimientoPartidaPresupuestaria;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setDetalleMovimientoPartidaPresupuestaria(DetalleMovimientoPartidaPresupuestaria detalleMovimientoPartidaPresupuestaria)
/* 475:    */   {
/* 476:441 */     this.detalleMovimientoPartidaPresupuestaria = detalleMovimientoPartidaPresupuestaria;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public boolean isRenderColumns()
/* 480:    */   {
/* 481:445 */     return this.renderColumns;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setRenderColumns(boolean renderColumns)
/* 485:    */   {
/* 486:449 */     this.renderColumns = renderColumns;
/* 487:    */   }
/* 488:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.presupuesto.procesos.MovimientoPartidaPresupuestariaBean
 * JD-Core Version:    0.7.0.1
 */