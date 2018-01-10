/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*   9:    */ import com.asinfo.as2.entities.DetallePagoCash;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.FormaPago;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.PagoCash;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  19:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPagoCash;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  22:    */ import java.math.BigDecimal;
/*  23:    */ import java.util.ArrayList;
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
/*  37:    */ public class AprobarPagoCashBean
/*  38:    */   extends PageControllerAS2
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 4047190694821792927L;
/*  41:    */   @EJB
/*  42:    */   private transient ServicioPagoCash servicioPagoCash;
/*  43:    */   @EJB
/*  44:    */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  45:    */   @EJB
/*  46:    */   private transient ServicioDocumento servicioDocumento;
/*  47:    */   @EJB
/*  48:    */   private transient ServicioFormaPago servicioFormaPago;
/*  49:    */   private PagoCash pagoCash;
/*  50:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  51:    */   private List<Documento> listaDocumento;
/*  52:    */   private BigDecimal totalLiquidado;
/*  53:    */   private int idCuentaBancariaOrganizacion;
/*  54:    */   private List<FormaPago> listaFormaPago;
/*  55:    */   private int idFormaPago;
/*  56:    */   private Empresa empresa;
/*  57:    */   private BigDecimal valorAnticipo;
/*  58:    */   private boolean indicadorAprobar;
/*  59:    */   private boolean deshabilitarAprobar;
/*  60:    */   private boolean aprobarTodos;
/*  61:    */   private BigDecimal totalValorALiquidar;
/*  62:    */   private BigDecimal totalValorPendiente;
/*  63:    */   private BigDecimal diferencia;
/*  64:    */   private LazyDataModel<PagoCash> listaPagoCash;
/*  65:    */   private DataTable dtPagoCash;
/*  66:    */   private DataTable dtDetallePagoCash;
/*  67:    */   
/*  68:    */   @PostConstruct
/*  69:    */   public void init()
/*  70:    */   {
/*  71:105 */     this.listaPagoCash = new LazyDataModel()
/*  72:    */     {
/*  73:    */       private static final long serialVersionUID = -6092104942165704404L;
/*  74:    */       
/*  75:    */       public List<PagoCash> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  76:    */       {
/*  77:117 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  78:    */         
/*  79:119 */         filters.put("estado", Estado.ELABORADO.toString());
/*  80:120 */         filters.put("documento.documentoBase", DocumentoBase.PAGO_CASH_PROVEEDOR.toString());
/*  81:    */         
/*  82:122 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  83:123 */         List<PagoCash> lista = AprobarPagoCashBean.this.servicioPagoCash.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  84:    */         
/*  85:125 */         AprobarPagoCashBean.this.listaPagoCash.setRowCount(AprobarPagoCashBean.this.servicioPagoCash.contarPorCriterio(filters));
/*  86:126 */         return lista;
/*  87:    */       }
/*  88:    */     };
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String crear()
/*  92:    */   {
/*  93:143 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  94:144 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String editar()
/*  98:    */   {
/*  99:153 */     if (getPagoCash().getIdPagoCash() != 0)
/* 100:    */     {
/* 101:154 */       setPagoCash(this.servicioPagoCash.cargarDetalle(getPagoCash().getIdPagoCash()));
/* 102:155 */       for (DetallePagoCash detallePagoCash : this.pagoCash.getListaDetallePagoCash()) {
/* 103:156 */         if (detallePagoCash.getCuentaPorPagar() != null) {
/* 104:157 */           detallePagoCash.setDiasVencidos((int)FuncionesUtiles.DiasEntreFechas(detallePagoCash.getCuentaPorPagar().getFechaVencimiento(), detallePagoCash
/* 105:158 */             .getPagoCash().getFechaPago()));
/* 106:    */         }
/* 107:    */       }
/* 108:163 */       calcularValorPagoCash();
/* 109:164 */       setEditado(true);
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:168 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 114:    */     }
/* 115:170 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String guardar()
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:180 */       if (isIndicadorAprobar()) {
/* 123:181 */         this.pagoCash.setEstado(Estado.APROBADO);
/* 124:    */       }
/* 125:184 */       this.servicioPagoCash.guardar(this.pagoCash);
/* 126:185 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 127:186 */       setEditado(false);
/* 128:187 */       limpiar();
/* 129:    */     }
/* 130:    */     catch (Exception e)
/* 131:    */     {
/* 132:189 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 133:190 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 134:    */     }
/* 135:192 */     return "";
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String eliminar()
/* 139:    */   {
/* 140:    */     try
/* 141:    */     {
/* 142:202 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 143:    */     }
/* 144:    */     catch (Exception e)
/* 145:    */     {
/* 146:206 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 147:207 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 148:    */     }
/* 149:209 */     return "";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String cargarDatos()
/* 153:    */   {
/* 154:218 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String limpiar()
/* 158:    */   {
/* 159:227 */     setEditado(false);
/* 160:228 */     this.pagoCash = new PagoCash();
/* 161:229 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String calcularValorPagoCash()
/* 165:    */   {
/* 166:239 */     this.totalValorALiquidar = BigDecimal.ZERO;
/* 167:240 */     this.totalValorPendiente = BigDecimal.ZERO;
/* 168:241 */     this.diferencia = BigDecimal.ZERO;
/* 169:242 */     for (DetallePagoCash detallePagoCash : this.pagoCash.getListaDetallePagoCash())
/* 170:    */     {
/* 171:243 */       if (detallePagoCash.isIndicadorAprobado()) {
/* 172:244 */         this.totalValorALiquidar = this.totalValorALiquidar.add(detallePagoCash.getValor());
/* 173:    */       }
/* 174:246 */       this.totalValorPendiente = this.totalValorPendiente.add(detallePagoCash.getCuentaPorPagar() != null ? detallePagoCash.getCuentaPorPagar().getSaldo() : detallePagoCash.getValor());
/* 175:    */     }
/* 176:248 */     this.deshabilitarAprobar = (this.totalValorALiquidar.compareTo(BigDecimal.ZERO) == 0);
/* 177:249 */     this.diferencia = this.totalValorPendiente.subtract(this.totalValorALiquidar);
/* 178:250 */     this.pagoCash.setValorPago(this.totalValorALiquidar);
/* 179:251 */     return "";
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String seleccionarTodos()
/* 183:    */   {
/* 184:260 */     for (DetallePagoCash detalle : this.pagoCash.getListaDetallePagoCash()) {
/* 185:261 */       detalle.setIndicadorAprobado(this.aprobarTodos);
/* 186:    */     }
/* 187:263 */     calcularValorPagoCash();
/* 188:264 */     return "";
/* 189:    */   }
/* 190:    */   
/* 191:    */   public PagoCash getPagoCash()
/* 192:    */   {
/* 193:276 */     if (this.pagoCash == null) {
/* 194:277 */       this.pagoCash = new PagoCash();
/* 195:    */     }
/* 196:279 */     return this.pagoCash;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setPagoCash(PagoCash pagoCash)
/* 200:    */   {
/* 201:289 */     this.pagoCash = pagoCash;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public LazyDataModel<PagoCash> getListaPagoCash()
/* 205:    */   {
/* 206:298 */     return this.listaPagoCash;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setListaPagoCash(LazyDataModel<PagoCash> listaPagoCash)
/* 210:    */   {
/* 211:308 */     this.listaPagoCash = listaPagoCash;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public DataTable getDtPagoCash()
/* 215:    */   {
/* 216:317 */     return this.dtPagoCash;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setDtPagoCash(DataTable dtPagoCash)
/* 220:    */   {
/* 221:327 */     this.dtPagoCash = dtPagoCash;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 225:    */   {
/* 226:336 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 227:337 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/* 228:    */     }
/* 229:339 */     return this.listaCuentaBancariaOrganizacion;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 233:    */   {
/* 234:349 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public DataTable getDtDetallePagoCash()
/* 238:    */   {
/* 239:358 */     return this.dtDetallePagoCash;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setDtDetallePagoCash(DataTable dtDetallePagoCash)
/* 243:    */   {
/* 244:368 */     this.dtDetallePagoCash = dtDetallePagoCash;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<Documento> getListaDocumento()
/* 248:    */   {
/* 249:377 */     if (this.listaDocumento == null) {
/* 250:    */       try
/* 251:    */       {
/* 252:379 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PAGO_CASH_PROVEEDOR);
/* 253:    */       }
/* 254:    */       catch (ExcepcionAS2 e)
/* 255:    */       {
/* 256:381 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 257:    */       }
/* 258:    */     }
/* 259:384 */     return this.listaDocumento;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 263:    */   {
/* 264:394 */     this.listaDocumento = listaDocumento;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public List<DetallePagoCash> getListaDetallePagoCash()
/* 268:    */   {
/* 269:403 */     List<DetallePagoCash> lista = new ArrayList();
/* 270:404 */     for (DetallePagoCash detallePagoCash : getPagoCash().getListaDetallePagoCash()) {
/* 271:405 */       if (!detallePagoCash.isEliminado()) {
/* 272:406 */         lista.add(detallePagoCash);
/* 273:    */       }
/* 274:    */     }
/* 275:409 */     return lista;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public BigDecimal getTotalLiquidado()
/* 279:    */   {
/* 280:418 */     this.totalLiquidado = BigDecimal.ZERO;
/* 281:420 */     for (DetallePagoCash detallePagoCash : getPagoCash().getListaDetallePagoCash()) {
/* 282:421 */       if (!detallePagoCash.isEliminado()) {
/* 283:422 */         this.totalLiquidado = this.totalLiquidado.add(detallePagoCash.getValor());
/* 284:    */       }
/* 285:    */     }
/* 286:426 */     return this.totalLiquidado;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setTotalLiquidado(BigDecimal totalLiquidado)
/* 290:    */   {
/* 291:436 */     this.totalLiquidado = totalLiquidado;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public int getIdCuentaBancariaOrganizacion()
/* 295:    */   {
/* 296:445 */     return this.idCuentaBancariaOrganizacion;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setIdCuentaBancariaOrganizacion(int idCuentaBancariaOrganizacion)
/* 300:    */   {
/* 301:455 */     this.idCuentaBancariaOrganizacion = idCuentaBancariaOrganizacion;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public List<FormaPago> getListaFormaPago()
/* 305:    */   {
/* 306:464 */     if (this.listaFormaPago == null) {
/* 307:465 */       this.listaFormaPago = this.servicioFormaPago.obtenerListaCombo(null, false, null);
/* 308:    */     }
/* 309:467 */     return this.listaFormaPago;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setListaFormaPago(List<FormaPago> listaFormaPago)
/* 313:    */   {
/* 314:477 */     this.listaFormaPago = listaFormaPago;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public int getIdFormaPago()
/* 318:    */   {
/* 319:486 */     return this.idFormaPago;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setIdFormaPago(int idFormaPago)
/* 323:    */   {
/* 324:496 */     this.idFormaPago = idFormaPago;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public Empresa getEmpresa()
/* 328:    */   {
/* 329:505 */     return this.empresa;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setEmpresa(Empresa empresa)
/* 333:    */   {
/* 334:515 */     this.empresa = empresa;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public BigDecimal getValorAnticipo()
/* 338:    */   {
/* 339:524 */     return this.valorAnticipo;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setValorAnticipo(BigDecimal valorAnticipo)
/* 343:    */   {
/* 344:534 */     this.valorAnticipo = valorAnticipo;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public boolean isIndicadorAprobar()
/* 348:    */   {
/* 349:543 */     return this.indicadorAprobar;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setIndicadorAprobar(boolean indicadorAprobar)
/* 353:    */   {
/* 354:553 */     this.indicadorAprobar = indicadorAprobar;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public boolean isDeshabilitarAprobar()
/* 358:    */   {
/* 359:562 */     return this.deshabilitarAprobar;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setDeshabilitarAprobar(boolean deshabilitarAprobar)
/* 363:    */   {
/* 364:572 */     this.deshabilitarAprobar = deshabilitarAprobar;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public boolean isAprobarTodos()
/* 368:    */   {
/* 369:581 */     return this.aprobarTodos;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setAprobarTodos(boolean aprobarTodos)
/* 373:    */   {
/* 374:591 */     this.aprobarTodos = aprobarTodos;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public BigDecimal getTotalValorALiquidar()
/* 378:    */   {
/* 379:595 */     return this.totalValorALiquidar;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setTotalValorALiquidar(BigDecimal totalValorALiquidar)
/* 383:    */   {
/* 384:599 */     this.totalValorALiquidar = totalValorALiquidar;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public BigDecimal getTotalValorPendiente()
/* 388:    */   {
/* 389:603 */     return this.totalValorPendiente;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setTotalValorPendiente(BigDecimal totalValorPendiente)
/* 393:    */   {
/* 394:607 */     this.totalValorPendiente = totalValorPendiente;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public BigDecimal getDiferencia()
/* 398:    */   {
/* 399:611 */     return this.diferencia;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setDiferencia(BigDecimal diferencia)
/* 403:    */   {
/* 404:615 */     this.diferencia = diferencia;
/* 405:    */   }
/* 406:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.AprobarPagoCashBean
 * JD-Core Version:    0.7.0.1
 */