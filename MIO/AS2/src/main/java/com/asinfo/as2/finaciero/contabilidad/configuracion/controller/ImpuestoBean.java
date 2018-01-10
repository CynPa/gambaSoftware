/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.Impuesto;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.RangoImpuesto;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.enumeraciones.TipoImpuestoEnum;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  14:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import com.asinfo.as2.utils.JsfUtil;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ManagedProperty;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import javax.faces.model.SelectItem;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.component.datatable.DataTable;
/*  30:    */ import org.primefaces.event.SelectEvent;
/*  31:    */ import org.primefaces.model.LazyDataModel;
/*  32:    */ import org.primefaces.model.SortOrder;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class ImpuestoBean
/*  37:    */   extends PageControllerAS2
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 7416530175194416099L;
/*  40:    */   @EJB
/*  41:    */   private ServicioImpuesto servicioImpuesto;
/*  42:    */   private RangoImpuesto rangoImpuesto;
/*  43:    */   private Impuesto impuesto;
/*  44:    */   private boolean indicadorImpuestoTributario;
/*  45:    */   private DataTable dtImpuesto;
/*  46:    */   private DataTable dtRangoImpuesto;
/*  47:    */   private DataTable dtCuentaContable;
/*  48:    */   private LazyDataModel<Impuesto> listaImpuesto;
/*  49:    */   private CuentaContable cuentaContable;
/*  50:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  51:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  52:    */   private enumCuentaContableEditada cuentaContableEditada;
/*  53:    */   
/*  54:    */   private static enum enumCuentaContableEditada
/*  55:    */   {
/*  56: 79 */     CUENTA_CONTABLE_VENTA,  CUENTA_CONTABLE_COMPRA,  CUENTA_CONTABLE_REDONDEO;
/*  57:    */     
/*  58:    */     private enumCuentaContableEditada() {}
/*  59:    */   }
/*  60:    */   
/*  61:    */   @PostConstruct
/*  62:    */   public void init()
/*  63:    */   {
/*  64: 86 */     this.listaImpuesto = new LazyDataModel()
/*  65:    */     {
/*  66:    */       private static final long serialVersionUID = 1L;
/*  67:    */       
/*  68:    */       public List<Impuesto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  69:    */       {
/*  70: 92 */         List<Impuesto> lista = new ArrayList();
/*  71:    */         
/*  72: 94 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  73: 96 */         if (sortField == null)
/*  74:    */         {
/*  75: 97 */           sortField = "codigo";
/*  76: 98 */           ordenar = true;
/*  77:    */         }
/*  78:101 */         lista = ImpuestoBean.this.servicioImpuesto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  79:    */         
/*  80:103 */         ImpuestoBean.this.listaImpuesto.setRowCount(ImpuestoBean.this.servicioImpuesto.contarPorCriterio(filters));
/*  81:    */         
/*  82:105 */         return lista;
/*  83:    */       }
/*  84:108 */     };
/*  85:109 */     setIndicadorImpuestoTributario(this.servicioImpuesto.getIndicadorTributario());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String editar()
/*  89:    */   {
/*  90:120 */     if (getImpuesto().getId() > 0)
/*  91:    */     {
/*  92:121 */       setEditado(true);
/*  93:    */       try
/*  94:    */       {
/*  95:123 */         this.impuesto = this.servicioImpuesto.cargarDetalle(getImpuesto().getIdImpuesto());
/*  96:124 */         setEditado(true);
/*  97:    */       }
/*  98:    */       catch (ExcepcionAS2Financiero e)
/*  99:    */       {
/* 100:126 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 101:127 */         LOG.error("Error al editar pedido de cliente: " + e.getErrorMessage(e));
/* 102:    */       }
/* 103:    */       catch (Exception e)
/* 104:    */       {
/* 105:129 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 106:130 */         LOG.error("Error al editar pedido de cliente", e);
/* 107:    */       }
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:133 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 112:    */     }
/* 113:135 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String guardar()
/* 117:    */   {
/* 118:    */     try
/* 119:    */     {
/* 120:146 */       this.servicioImpuesto.guardar(this.impuesto);
/* 121:147 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 122:148 */       limpiar();
/* 123:149 */       setEditado(false);
/* 124:    */     }
/* 125:    */     catch (AS2Exception e)
/* 126:    */     {
/* 127:151 */       JsfUtil.addErrorMessage(e, "");
/* 128:152 */       e.printStackTrace();
/* 129:    */     }
/* 130:    */     catch (ExcepcionAS2 e)
/* 131:    */     {
/* 132:154 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 133:155 */       LOG.error("ERROR AL GUARDAR DATOS: " + e.getErrorMessage(e));
/* 134:    */     }
/* 135:    */     catch (Exception e)
/* 136:    */     {
/* 137:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 138:158 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 139:    */     }
/* 140:160 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String eliminar()
/* 144:    */   {
/* 145:    */     try
/* 146:    */     {
/* 147:172 */       this.servicioImpuesto.eliminar(this.impuesto);
/* 148:173 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 149:    */     }
/* 150:    */     catch (AS2Exception e)
/* 151:    */     {
/* 152:175 */       JsfUtil.addErrorMessage(e, "");
/* 153:176 */       e.printStackTrace();
/* 154:    */     }
/* 155:    */     catch (ExcepcionAS2 e)
/* 156:    */     {
/* 157:178 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 158:179 */       LOG.error("ERROR AL ELIMINAR DATOS" + e.getErrorMessage(e));
/* 159:    */     }
/* 160:    */     catch (Exception e)
/* 161:    */     {
/* 162:181 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 163:182 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 164:    */     }
/* 165:185 */     return "";
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String limpiar()
/* 169:    */   {
/* 170:195 */     crearImpuesto();
/* 171:196 */     return "";
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void crearImpuesto()
/* 175:    */   {
/* 176:200 */     this.impuesto = new Impuesto();
/* 177:201 */     this.impuesto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 178:202 */     this.impuesto.setIdSucursal(AppUtil.getSucursal().getId());
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String cargarDatos()
/* 182:    */   {
/* 183:212 */     return "";
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String agregarDetalle()
/* 187:    */   {
/* 188:221 */     this.rangoImpuesto = new RangoImpuesto();
/* 189:222 */     this.rangoImpuesto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 190:223 */     this.rangoImpuesto.setIdSucursal(AppUtil.getSucursal().getId());
/* 191:224 */     this.rangoImpuesto.setImpuesto(getImpuesto());
/* 192:225 */     getImpuesto().getListaRangoImpuesto().add(this.rangoImpuesto);
/* 193:226 */     return "";
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setFechaHasta()
/* 197:    */   {
/* 198:233 */     RangoImpuesto rangoImpuesto = (RangoImpuesto)this.dtRangoImpuesto.getRowData();
/* 199:234 */     if ((getListaRangoImpuesto() != null) && (getListaRangoImpuesto().size() > 0)) {
/* 200:235 */       for (RangoImpuesto ri : getListaRangoImpuesto())
/* 201:    */       {
/* 202:236 */         if ((ri.getFechaHasta() == null) && (ri.getFechaDesde() != null) && (rangoImpuesto.getFechaDesde() != null))
/* 203:    */         {
/* 204:237 */           ri.setFechaHasta(FuncionesUtiles.sumarFechaDiasMeses(rangoImpuesto.getFechaDesde(), -1));
/* 205:238 */           rangoImpuesto.setFechaHasta(null);
/* 206:    */         }
/* 207:240 */         if ((ri.getFechaDesde() != null) && (ri.getFechaHasta() != null) && (rangoImpuesto.getFechaDesde() != null) && 
/* 208:241 */           (rangoImpuesto.getFechaDesde().after(ri.getFechaDesde())) && (rangoImpuesto.getFechaDesde().before(ri.getFechaHasta())))
/* 209:    */         {
/* 210:242 */           addErrorMessage(getLanguageController().getMensaje("msg_error_fecha_intervalo_no_permitido"));
/* 211:243 */           rangoImpuesto.setFechaDesde(null);
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void validacionFechasDesde()
/* 218:    */   {
/* 219:250 */     RangoImpuesto fechaHasta = null;
/* 220:251 */     for (RangoImpuesto rangoImpuesto : getListaRangoImpuesto())
/* 221:    */     {
/* 222:252 */       if ((fechaHasta != null) && (fechaHasta.getFechaHasta().after(rangoImpuesto.getFechaDesde())))
/* 223:    */       {
/* 224:253 */         fechaHasta.setFechaHasta(FuncionesUtiles.sumarFechaDiasMeses(rangoImpuesto.getFechaDesde(), -1));
/* 225:254 */         addErrorMessage(getLanguageController().getMensaje("msg_error_fecha_intervalo_no_permitido"));
/* 226:    */       }
/* 227:256 */       fechaHasta = rangoImpuesto;
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String eliminarDetalle()
/* 232:    */   {
/* 233:266 */     RangoImpuesto rangoImpuesto = (RangoImpuesto)this.dtRangoImpuesto.getRowData();
/* 234:267 */     rangoImpuesto.setEliminado(true);
/* 235:268 */     return "";
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void seleccionarCuentaContable(SelectEvent event)
/* 239:    */   {
/* 240:273 */     this.cuentaContable = ((CuentaContable)event.getObject());
/* 241:275 */     switch (2.$SwitchMap$com$asinfo$as2$finaciero$contabilidad$configuracion$controller$ImpuestoBean$enumCuentaContableEditada[this.cuentaContableEditada.ordinal()])
/* 242:    */     {
/* 243:    */     case 1: 
/* 244:277 */       getImpuesto().setCuentaContableVenta(this.cuentaContable);
/* 245:278 */       break;
/* 246:    */     case 2: 
/* 247:281 */       getImpuesto().setCuentaContableCompra(this.cuentaContable);
/* 248:282 */       break;
/* 249:    */     case 3: 
/* 250:284 */       getImpuesto().setCuentaContableRedondeo(this.cuentaContable);
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void actualizarCuentaContableVenta()
/* 255:    */   {
/* 256:290 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_VENTA;
/* 257:291 */     this.cuentaContable = this.impuesto.getCuentaContableVenta();
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void actualizarCuentaContableCompra()
/* 261:    */   {
/* 262:295 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_COMPRA;
/* 263:296 */     this.cuentaContable = this.impuesto.getCuentaContableCompra();
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void actualizarCuentaContableRedondeo()
/* 267:    */   {
/* 268:300 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_REDONDEO;
/* 269:301 */     this.cuentaContable = this.impuesto.getCuentaContableRedondeo();
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void indicadorImpuestoIVA()
/* 273:    */   {
/* 274:305 */     this.impuesto.setIndicadorNoObjetoIVA(false);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void indicadorNoObjetoIVA()
/* 278:    */   {
/* 279:309 */     this.impuesto.setIndicadorImpuestoTributario(false);
/* 280:    */   }
/* 281:    */   
/* 282:    */   public Impuesto getImpuesto()
/* 283:    */   {
/* 284:320 */     return this.impuesto;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setImpuesto(Impuesto impuesto)
/* 288:    */   {
/* 289:330 */     this.impuesto = impuesto;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public RangoImpuesto getRangoImpuesto()
/* 293:    */   {
/* 294:334 */     return this.rangoImpuesto;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setRangoImpuesto(RangoImpuesto rangoImpuesto)
/* 298:    */   {
/* 299:338 */     this.rangoImpuesto = rangoImpuesto;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public CuentaContable getCuentaContable()
/* 303:    */   {
/* 304:342 */     return this.cuentaContable;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 308:    */   {
/* 309:346 */     this.cuentaContable = cuentaContable;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public boolean isIndicadorImpuestoTributario()
/* 313:    */   {
/* 314:350 */     return this.indicadorImpuestoTributario;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setIndicadorImpuestoTributario(boolean indicadorImpuestoTributario)
/* 318:    */   {
/* 319:354 */     this.indicadorImpuestoTributario = indicadorImpuestoTributario;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public List<RangoImpuesto> getListaRangoImpuesto()
/* 323:    */   {
/* 324:363 */     List<RangoImpuesto> rangoImpuestos = new ArrayList();
/* 325:364 */     for (RangoImpuesto ri : getImpuesto().getListaRangoImpuesto()) {
/* 326:365 */       if (!ri.isEliminado()) {
/* 327:366 */         rangoImpuestos.add(ri);
/* 328:    */       }
/* 329:    */     }
/* 330:369 */     return rangoImpuestos;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setRangoImpuesto(List<RangoImpuesto> rangoImpuestos)
/* 334:    */   {
/* 335:376 */     getImpuesto().setListaRangoImpuesto(rangoImpuestos);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public LazyDataModel<Impuesto> getListaImpuesto()
/* 339:    */   {
/* 340:380 */     return this.listaImpuesto;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void setListaImpuesto(LazyDataModel<Impuesto> listaImpuesto)
/* 344:    */   {
/* 345:384 */     this.listaImpuesto = listaImpuesto;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public DataTable getDtImpuesto()
/* 349:    */   {
/* 350:393 */     return this.dtImpuesto;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void setDtImpuesto(DataTable dtImpuesto)
/* 354:    */   {
/* 355:403 */     this.dtImpuesto = dtImpuesto;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public DataTable getDtRangoImpuesto()
/* 359:    */   {
/* 360:412 */     return this.dtRangoImpuesto;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void setDtRangoImpuesto(DataTable dtRangoImpuesto)
/* 364:    */   {
/* 365:422 */     this.dtRangoImpuesto = dtRangoImpuesto;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public enumCuentaContableEditada getCuentaContableEditada()
/* 369:    */   {
/* 370:431 */     return this.cuentaContableEditada;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void setCuentaContableEditada(enumCuentaContableEditada cuentaContableEditada)
/* 374:    */   {
/* 375:441 */     this.cuentaContableEditada = cuentaContableEditada;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public DataTable getDtCuentaContable()
/* 379:    */   {
/* 380:445 */     return this.dtCuentaContable;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 384:    */   {
/* 385:449 */     this.dtCuentaContable = dtCuentaContable;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 389:    */   {
/* 390:453 */     return this.listaCuentaContableBean;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 394:    */   {
/* 395:457 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public List<SelectItem> getListaTipoImpuesto()
/* 399:    */   {
/* 400:461 */     List<SelectItem> lista = new ArrayList();
/* 401:462 */     for (TipoImpuestoEnum impuesto : TipoImpuestoEnum.values()) {
/* 402:463 */       lista.add(new SelectItem(impuesto, impuesto.getNombre()));
/* 403:    */     }
/* 404:465 */     return lista;
/* 405:    */   }
/* 406:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ImpuestoBean
 * JD-Core Version:    0.7.0.1
 */