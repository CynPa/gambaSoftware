/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.CentroCosto;
/*   7:    */ import com.asinfo.as2.entities.DetalleEstadoFinanciero;
/*   8:    */ import com.asinfo.as2.entities.DimensionContable;
/*   9:    */ import com.asinfo.as2.entities.EstadoFinanciero;
/*  10:    */ import com.asinfo.as2.entities.NivelCuenta;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  14:    */ import com.asinfo.as2.enumeraciones.TipoEstadoFinanciero;
/*  15:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*  17:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCuenta;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioEstadoFinanciero;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.util.AppUtil;
/*  22:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.annotation.PostConstruct;
/*  29:    */ import javax.ejb.EJB;
/*  30:    */ import javax.faces.bean.ManagedBean;
/*  31:    */ import javax.faces.bean.ManagedProperty;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import javax.faces.model.SelectItem;
/*  34:    */ import org.apache.log4j.Logger;
/*  35:    */ import org.primefaces.component.datatable.DataTable;
/*  36:    */ import org.primefaces.event.SelectEvent;
/*  37:    */ 
/*  38:    */ @ManagedBean
/*  39:    */ @ViewScoped
/*  40:    */ public class BalanceComprobacionBean
/*  41:    */   extends PageController
/*  42:    */ {
/*  43:    */   private static final long serialVersionUID = -1147884310176254261L;
/*  44:    */   @EJB
/*  45:    */   private ServicioEstadoFinanciero servicioEstadoFinanciero;
/*  46:    */   @EJB
/*  47:    */   private ServicioCuentaContable servicioCuentaContable;
/*  48:    */   @EJB
/*  49:    */   private ServicioCentroCosto servicioCentroCosto;
/*  50:    */   @EJB
/*  51:    */   private ServicioSucursal servicioSucursal;
/*  52:    */   @EJB
/*  53:    */   private ServicioNivelCuenta servicioNivelCuenta;
/*  54: 76 */   private int mesDesde = 1;
/*  55: 77 */   private int anioDesde = FuncionesUtiles.obtenerAnioActual();
/*  56: 78 */   private int mesHasta = FuncionesUtiles.obtenerMesActual();
/*  57: 79 */   private int anioHasta = FuncionesUtiles.obtenerAnioActual();
/*  58: 80 */   private int idSucursal = -1;
/*  59:    */   private int nivelCuentaMaximo;
/*  60:    */   private List<SelectItem> listaSucursal;
/*  61:    */   private List<NivelCuenta> listaNivelCuenta;
/*  62:    */   private EstadoFinanciero estadoFinanciero;
/*  63:    */   private List<SelectItem> listaMes;
/*  64:    */   private DataTable dtDetalleEstadoFinanciero;
/*  65: 91 */   private String valorDimension = "";
/*  66:    */   @ManagedProperty("#{listaDimensionContableBean}")
/*  67:    */   private ListaDimensionContableBean listaDimensionContableBean;
/*  68: 96 */   private BigDecimal totalDebe = BigDecimal.ZERO;
/*  69: 97 */   private BigDecimal totalHaber = BigDecimal.ZERO;
/*  70: 98 */   private BigDecimal totalAcreedor = BigDecimal.ZERO;
/*  71: 99 */   private BigDecimal totalDeudor = BigDecimal.ZERO;
/*  72:100 */   private BigDecimal saldoInicial = BigDecimal.ZERO;
/*  73:102 */   private String sucursal = "";
/*  74:103 */   private String centroCosto = "";
/*  75:    */   
/*  76:    */   @PostConstruct
/*  77:    */   public void init()
/*  78:    */   {
/*  79:108 */     this.mesDesde = FuncionesUtiles.obtenerMesActual();
/*  80:109 */     this.anioDesde = FuncionesUtiles.obtenerAnioActual();
/*  81:110 */     this.mesHasta = FuncionesUtiles.obtenerMesActual();
/*  82:111 */     this.anioHasta = FuncionesUtiles.obtenerAnioActual();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void procesar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:117 */       this.estadoFinanciero.setFechaDesde(FuncionesUtiles.getFecha(1, this.mesDesde, this.anioDesde));
/*  90:118 */       this.estadoFinanciero.setFechaHasta(FuncionesUtiles.getFechaFinMes(this.anioHasta, this.mesHasta));
/*  91:119 */       this.estadoFinanciero.setTipoEstadoFinanciero(TipoEstadoFinanciero.BALANCE_COMPROBACION);
/*  92:120 */       this.estadoFinanciero = this.servicioEstadoFinanciero.cargarDatos(this.estadoFinanciero, this.listaDimensionContableBean.getNumeroDimension(), 
/*  93:121 */         getValorDimension(), getIdSucursal(), getNivelCuentaMaximo(), false);
/*  94:122 */       for (int i = 0; i < getListaSucursal().size(); i++) {
/*  95:123 */         if (((SelectItem)getListaSucursal().get(i)).getValue().equals(Integer.valueOf(getIdSucursal()))) {
/*  96:124 */           this.sucursal = ((SelectItem)getListaSucursal().get(i)).getLabel();
/*  97:    */         }
/*  98:    */       }
/*  99:128 */       this.totalDebe = BigDecimal.ZERO;
/* 100:129 */       this.totalHaber = BigDecimal.ZERO;
/* 101:130 */       this.totalAcreedor = BigDecimal.ZERO;
/* 102:131 */       this.totalDeudor = BigDecimal.ZERO;
/* 103:132 */       this.saldoInicial = BigDecimal.ZERO;
/* 104:    */       
/* 105:134 */       ArrayList<DetalleEstadoFinanciero> listaDetalleEstadoFinanciero = (ArrayList)this.estadoFinanciero.getListaDetalleEstadoFinanciero();
/* 106:135 */       for (DetalleEstadoFinanciero def : listaDetalleEstadoFinanciero)
/* 107:    */       {
/* 108:136 */         this.totalDebe = this.totalDebe.add(def.getDebe());
/* 109:137 */         this.totalHaber = this.totalHaber.add(def.getHaber());
/* 110:138 */         this.totalAcreedor = this.totalAcreedor.add(def.getSaldoAcreedor());
/* 111:139 */         this.totalDeudor = this.totalDeudor.add(def.getSaldoDeudor());
/* 112:140 */         this.saldoInicial = this.saldoInicial.add(def.getSaldoInicial());
/* 113:    */       }
/* 114:    */     }
/* 115:    */     catch (ExcepcionAS2Financiero e)
/* 116:    */     {
/* 117:144 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 118:145 */       e.printStackTrace();
/* 119:146 */       LOG.error("ERROR AL PROCESAR", e);
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public List<CentroCosto> autocompletarCentroCosto(String consulta)
/* 124:    */   {
/* 125:158 */     consulta = consulta.toUpperCase();
/* 126:    */     
/* 127:160 */     String sortField = "codigo";
/* 128:161 */     HashMap<String, String> filters = new HashMap();
/* 129:162 */     filters.put("activo", "true");
/* 130:    */     
/* 131:164 */     List<CentroCosto> lista = new ArrayList();
/* 132:166 */     for (CentroCosto centroCosto : this.servicioCentroCosto.obtenerListaCombo(sortField, true, filters)) {
/* 133:168 */       if ((centroCosto.getCodigo().toUpperCase().contains(consulta)) || (centroCosto.getNombre().toUpperCase().contains(consulta))) {
/* 134:169 */         lista.add(centroCosto);
/* 135:    */       }
/* 136:    */     }
/* 137:173 */     return lista;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void cargarDimensionContableListener(SelectEvent event)
/* 141:    */   {
/* 142:176 */     DimensionContable dimension = (DimensionContable)event.getObject();
/* 143:    */     try
/* 144:    */     {
/* 145:178 */       this.valorDimension = dimension.getCodigo();
/* 146:179 */       this.centroCosto = dimension.getNombre();
/* 147:    */     }
/* 148:    */     catch (Exception e)
/* 149:    */     {
/* 150:181 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 151:182 */       LOG.error("ERROR AL CARGAR DIMENSION CONTABLE", e);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public EstadoFinanciero getEstadoFinanciero()
/* 156:    */   {
/* 157:186 */     if (this.estadoFinanciero == null) {
/* 158:187 */       this.estadoFinanciero = new EstadoFinanciero();
/* 159:    */     }
/* 160:189 */     return this.estadoFinanciero;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setEstadoFinanciero(EstadoFinanciero estadoFinanciero)
/* 164:    */   {
/* 165:193 */     this.estadoFinanciero = estadoFinanciero;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public ServicioEstadoFinanciero getServicioEstadoFinanciero()
/* 169:    */   {
/* 170:197 */     return this.servicioEstadoFinanciero;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public ServicioCuentaContable getServicioCuentaContable()
/* 174:    */   {
/* 175:201 */     return this.servicioCuentaContable;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int getMesDesde()
/* 179:    */   {
/* 180:205 */     return this.mesDesde;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setMesDesde(int mesDesde)
/* 184:    */   {
/* 185:209 */     this.mesDesde = mesDesde;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int getAnioDesde()
/* 189:    */   {
/* 190:213 */     return this.anioDesde;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setAnioDesde(int anioDesde)
/* 194:    */   {
/* 195:217 */     this.anioDesde = anioDesde;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public int getMesHasta()
/* 199:    */   {
/* 200:221 */     return this.mesHasta;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setMesHasta(int mesHasta)
/* 204:    */   {
/* 205:225 */     this.mesHasta = mesHasta;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public int getAnioHasta()
/* 209:    */   {
/* 210:229 */     return this.anioHasta;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setAnioHasta(int anioHasta)
/* 214:    */   {
/* 215:233 */     this.anioHasta = anioHasta;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List<SelectItem> getListaMes()
/* 219:    */   {
/* 220:237 */     if (this.listaMes == null)
/* 221:    */     {
/* 222:238 */       this.listaMes = new ArrayList();
/* 223:239 */       for (Mes t : Mes.values())
/* 224:    */       {
/* 225:240 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 226:241 */         this.listaMes.add(item);
/* 227:    */       }
/* 228:    */     }
/* 229:244 */     return this.listaMes;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setListaMes(List<SelectItem> listaMes)
/* 233:    */   {
/* 234:248 */     this.listaMes = listaMes;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public DataTable getDtDetalleEstadoFinanciero()
/* 238:    */   {
/* 239:257 */     return this.dtDetalleEstadoFinanciero;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setDtDetalleEstadoFinanciero(DataTable dtDetalleEstadoFinanciero)
/* 243:    */   {
/* 244:267 */     this.dtDetalleEstadoFinanciero = dtDetalleEstadoFinanciero;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<SelectItem> getListaSucursal()
/* 248:    */   {
/* 249:276 */     if (this.listaSucursal == null)
/* 250:    */     {
/* 251:277 */       this.listaSucursal = new ArrayList();
/* 252:278 */       String sortField = "codigo";
/* 253:279 */       Map<String, String> filters = new HashMap();
/* 254:280 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 255:282 */       for (Sucursal sucursal : this.servicioSucursal.obtenerListaCombo(sortField, true, filters))
/* 256:    */       {
/* 257:283 */         SelectItem se = new SelectItem();
/* 258:284 */         se.setValue(Integer.valueOf(sucursal.getIdSucursal()));
/* 259:285 */         se.setLabel(sucursal.getNombre());
/* 260:286 */         this.listaSucursal.add(se);
/* 261:    */       }
/* 262:    */     }
/* 263:289 */     return this.listaSucursal;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setListaSucursal(List<SelectItem> listaSucursal)
/* 267:    */   {
/* 268:299 */     this.listaSucursal = listaSucursal;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public int getIdSucursal()
/* 272:    */   {
/* 273:308 */     return this.idSucursal;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setIdSucursal(int idSucursal)
/* 277:    */   {
/* 278:318 */     this.idSucursal = idSucursal;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public List<NivelCuenta> getListaNivelCuenta()
/* 282:    */   {
/* 283:327 */     if (this.listaNivelCuenta == null) {
/* 284:328 */       this.listaNivelCuenta = this.servicioNivelCuenta.obtenerTodosOrdenadoDescendente();
/* 285:    */     }
/* 286:330 */     return this.listaNivelCuenta;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setListaNivelCuenta(List<NivelCuenta> listaNivelCuenta)
/* 290:    */   {
/* 291:340 */     this.listaNivelCuenta = listaNivelCuenta;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public int getNivelCuentaMaximo()
/* 295:    */   {
/* 296:350 */     this.nivelCuentaMaximo = 0;
/* 297:351 */     if (getListaNivelCuenta().isEmpty()) {
/* 298:352 */       addErrorMessage(getLanguageController().getMensaje("msg_error_nivel_cuenta_contable_no_existe"));
/* 299:    */     } else {
/* 300:354 */       for (NivelCuenta nivelCuenta : getListaNivelCuenta()) {
/* 301:355 */         if (nivelCuenta.getCodigo() > this.nivelCuentaMaximo) {
/* 302:356 */           this.nivelCuentaMaximo = nivelCuenta.getCodigo();
/* 303:    */         }
/* 304:    */       }
/* 305:    */     }
/* 306:360 */     return this.nivelCuentaMaximo;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setNivelCuentaMaximo(int nivelCuentaMaximo)
/* 310:    */   {
/* 311:371 */     this.nivelCuentaMaximo = nivelCuentaMaximo;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public String getValorDimension()
/* 315:    */   {
/* 316:375 */     return this.valorDimension;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setValorDimension(String valorDimension)
/* 320:    */   {
/* 321:379 */     this.valorDimension = valorDimension;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 325:    */   {
/* 326:383 */     return this.listaDimensionContableBean;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 330:    */   {
/* 331:387 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public BigDecimal getTotalDebe()
/* 335:    */   {
/* 336:391 */     return this.totalDebe;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setTotalDebe(BigDecimal totalDebe)
/* 340:    */   {
/* 341:395 */     this.totalDebe = totalDebe;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public BigDecimal getTotalHaber()
/* 345:    */   {
/* 346:399 */     return this.totalHaber;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void setTotalHaber(BigDecimal totalHaber)
/* 350:    */   {
/* 351:403 */     this.totalHaber = totalHaber;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public BigDecimal getTotalAcreedor()
/* 355:    */   {
/* 356:407 */     return this.totalAcreedor;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void setTotalAcreedor(BigDecimal totalAcreedor)
/* 360:    */   {
/* 361:411 */     this.totalAcreedor = totalAcreedor;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public BigDecimal getTotalDeudor()
/* 365:    */   {
/* 366:415 */     return this.totalDeudor;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setTotalDeudor(BigDecimal totalDeudor)
/* 370:    */   {
/* 371:419 */     this.totalDeudor = totalDeudor;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public BigDecimal getSaldoInicial()
/* 375:    */   {
/* 376:423 */     return this.saldoInicial;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setSaldoInicial(BigDecimal saldoInicial)
/* 380:    */   {
/* 381:427 */     this.saldoInicial = saldoInicial;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public String getSucursal()
/* 385:    */   {
/* 386:431 */     return this.sucursal;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setSucursal(String sucursal)
/* 390:    */   {
/* 391:435 */     this.sucursal = sucursal;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public String getCentroCosto()
/* 395:    */   {
/* 396:439 */     return this.centroCosto;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void setCentroCosto(String centroCosto)
/* 400:    */   {
/* 401:443 */     this.centroCosto = centroCosto;
/* 402:    */   }
/* 403:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.BalanceComprobacionBean
 * JD-Core Version:    0.7.0.1
 */