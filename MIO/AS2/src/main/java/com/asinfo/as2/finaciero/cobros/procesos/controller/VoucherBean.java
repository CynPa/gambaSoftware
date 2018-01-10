/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.dao.DetalleFormaCobroDao;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*  10:    */ import com.asinfo.as2.entities.Cobro;
/*  11:    */ import com.asinfo.as2.entities.Comision;
/*  12:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  13:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  14:    */ import com.asinfo.as2.entities.Documento;
/*  15:    */ import com.asinfo.as2.entities.Empresa;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*  18:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  19:    */ import com.asinfo.as2.entities.Sucursal;
/*  20:    */ import com.asinfo.as2.entities.TarjetaCredito;
/*  21:    */ import com.asinfo.as2.entities.VersionComision;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*  25:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  26:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioRegistroVoucher;
/*  27:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioTarjetaCredito;
/*  28:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  29:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  30:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  31:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  32:    */ import com.asinfo.as2.util.AppUtil;
/*  33:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  34:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  35:    */ import java.math.BigDecimal;
/*  36:    */ import java.util.ArrayList;
/*  37:    */ import java.util.Date;
/*  38:    */ import java.util.HashMap;
/*  39:    */ import java.util.List;
/*  40:    */ import java.util.Map;
/*  41:    */ import javax.annotation.PostConstruct;
/*  42:    */ import javax.ejb.EJB;
/*  43:    */ import org.apache.log4j.Logger;
/*  44:    */ import org.primefaces.component.datatable.DataTable;
/*  45:    */ import org.primefaces.model.LazyDataModel;
/*  46:    */ import org.primefaces.model.SortOrder;
/*  47:    */ 
/*  48:    */ public abstract class VoucherBean
/*  49:    */   extends PageControllerAS2
/*  50:    */ {
/*  51:    */   private static final long serialVersionUID = 1L;
/*  52:    */   @EJB
/*  53:    */   protected ServicioRegistroVoucher servicioRegistroVoucher;
/*  54:    */   @EJB
/*  55:    */   protected ServicioTarjetaCredito servicioTarjetaCredito;
/*  56:    */   @EJB
/*  57:    */   protected ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  58:    */   @EJB
/*  59:    */   protected DetalleFormaCobroDao detalleFormaCobroDao;
/*  60:    */   @EJB
/*  61:    */   protected ServicioFormaPago servicioFormaPago;
/*  62:    */   @EJB
/*  63:    */   protected ServicioGenerico<PlanTarjetaCredito> servicioPlanTarjetaCredito;
/*  64:    */   @EJB
/*  65:    */   protected ServicioEmpresa servicioEmpresa;
/*  66:    */   @EJB
/*  67:    */   protected ServicioDocumento servicioDocumento;
/*  68:    */   @EJB
/*  69:    */   protected ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  70:    */   @EJB
/*  71:    */   protected ServicioImpuesto servicioImpuesto;
/*  72:    */   protected Cobro cobro;
/*  73:    */   protected LazyDataModel<Cobro> listaCobro;
/*  74:    */   protected DataTable dtVoucher;
/*  75: 92 */   protected Map<Integer, TarjetaCredito> hmTarjeta = new HashMap();
/*  76:    */   protected List<DetalleFormaCobro> listaDetalleVoucher;
/*  77:    */   protected List<DetalleFormaCobro> listaDetalleVoucherFiler;
/*  78:    */   protected List<TarjetaCredito> listaTarjetaCredito;
/*  79:    */   protected List<PlanTarjetaCredito> listaPlanTarjetaCredito;
/*  80:    */   protected List<PuntoDeVenta> listaPuntoVenta;
/*  81:    */   protected List<Documento> listaDocumento;
/*  82:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  83:    */   
/*  84:    */   public abstract DocumentoBase getDocumento();
/*  85:    */   
/*  86:    */   public abstract TipoCuentaBancariaOrganizacion getTipoCuentaBancariaOrganizacion();
/*  87:    */   
/*  88:    */   @PostConstruct
/*  89:    */   public void init()
/*  90:    */   {
/*  91:109 */     this.listaCobro = new LazyDataModel()
/*  92:    */     {
/*  93:    */       private static final long serialVersionUID = 1L;
/*  94:    */       
/*  95:    */       public List<Cobro> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  96:    */       {
/*  97:116 */         List<Cobro> lista = new ArrayList();
/*  98:117 */         filters = VoucherBean.this.agregarFiltroOrganizacion(filters);
/*  99:118 */         filters.put("documento.documentoBase", "=" + VoucherBean.this.getDocumento().toString());
/* 100:    */         
/* 101:120 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 102:121 */         lista = VoucherBean.this.servicioRegistroVoucher.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 103:122 */         VoucherBean.this.listaCobro.setRowCount(VoucherBean.this.servicioRegistroVoucher.contarPorCriterio(filters));
/* 104:    */         
/* 105:124 */         return lista;
/* 106:    */       }
/* 107:    */     };
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:133 */     crearCobro();
/* 113:134 */     setearTablas();
/* 114:    */     
/* 115:136 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected void setearTablas()
/* 119:    */   {
/* 120:140 */     this.listaDetalleVoucher = null;
/* 121:141 */     this.listaDetalleVoucherFiler = null;
/* 122:142 */     if (this.dtVoucher != null) {
/* 123:143 */       this.dtVoucher.reset();
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void crearCobro()
/* 128:    */   {
/* 129:149 */     this.hmTarjeta = new HashMap();
/* 130:150 */     this.cobro = new Cobro();
/* 131:151 */     this.cobro.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 132:152 */     this.cobro.setSucursal(new Sucursal(AppUtil.getSucursal().getIdSucursal(), AppUtil.getSucursal().getCodigo(), AppUtil.getSucursal().getNombre()));
/* 133:153 */     this.cobro.setFecha(new Date());
/* 134:154 */     this.cobro.setValor(BigDecimal.ZERO);
/* 135:155 */     if (!getListaDocumento().isEmpty()) {
/* 136:156 */       this.cobro.setDocumento((Documento)getListaDocumento().get(0));
/* 137:    */     }
/* 138:159 */     Integer idEmpresa = ParametrosSistema.getClienteGenerico(AppUtil.getOrganizacion().getIdOrganizacion());
/* 139:160 */     if (idEmpresa == null)
/* 140:    */     {
/* 141:161 */       addInfoMessage(getLanguageController().getMensaje("msg_info_cliente_generico"));
/* 142:    */     }
/* 143:    */     else
/* 144:    */     {
/* 145:163 */       Empresa empresa = this.servicioEmpresa.buscarPorId(idEmpresa);
/* 146:164 */       if (empresa != null)
/* 147:    */       {
/* 148:165 */         this.cobro.setEmpresa(empresa);
/* 149:166 */         this.cobro.setEstado(Estado.ELABORADO);
/* 150:167 */         this.cobro.setNumero("");
/* 151:    */       }
/* 152:    */       else
/* 153:    */       {
/* 154:169 */         addInfoMessage(getLanguageController().getMensaje("msg_info_cliente_generico"));
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void totalizar(DetalleFormaCobro dfc)
/* 160:    */   {
/* 161:175 */     dfc.setValor(BigDecimal.ZERO);
/* 162:    */     
/* 163:177 */     BigDecimal montoIva = FuncionesUtiles.porcentaje(dfc.getBaseImponibleDiferenteCero(), 
/* 164:178 */       ParametrosSistema.getPorcentajeIva(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 165:179 */     dfc.setMontoIva(montoIva);
/* 166:180 */     dfc.setValor(dfc.getBaseImponibleDiferenteCero().add(dfc.getBaseImponibleTarifaCero()).add(montoIva));
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String editar()
/* 170:    */   {
/* 171:185 */     if (this.cobro.getIdCobro() != 0) {
/* 172:    */       try
/* 173:    */       {
/* 174:188 */         this.cobro = this.servicioRegistroVoucher.cargarDetalle(this.cobro, getDocumento() == DocumentoBase.COBRO_VOUCHER);
/* 175:    */         
/* 176:190 */         this.servicioRegistroVoucher.esEditable(this.cobro);
/* 177:192 */         if (getCobro().getDocumento().getDocumentoBase() == DocumentoBase.REGISTRO_VOUCHER)
/* 178:    */         {
/* 179:194 */           Map<Integer, TarjetaCredito> hmTar = new HashMap();
/* 180:196 */           for (DetalleFormaCobro dfc : getCobro().getListaDetalleFormaCobro()) {
/* 181:197 */             hmTar.put(Integer.valueOf(dfc.getTarjetaCredito().getId()), dfc.getTarjetaCredito());
/* 182:    */           }
/* 183:200 */           for (TarjetaCredito tarjeta : hmTar.values()) {
/* 184:201 */             cargarPlanTarjeta(tarjeta);
/* 185:    */           }
/* 186:    */         }
/* 187:205 */         setEditado(true);
/* 188:    */       }
/* 189:    */       catch (ExcepcionAS2Financiero e)
/* 190:    */       {
/* 191:207 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 192:208 */         LOG.info("ERROR AL EDITAR REGISTO-COBRO VOUCHER");
/* 193:209 */         return "-1";
/* 194:    */       }
/* 195:    */     } else {
/* 196:213 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 197:    */     }
/* 198:215 */     return "";
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void cargarPlanTarjeta(TarjetaCredito tc)
/* 202:    */   {
/* 203:220 */     if (tc != null)
/* 204:    */     {
/* 205:222 */       TarjetaCredito tcMapa = (TarjetaCredito)this.hmTarjeta.get(Integer.valueOf(tc.getId()));
/* 206:224 */       if (tcMapa == null)
/* 207:    */       {
/* 208:226 */         tcMapa = this.servicioTarjetaCredito.cargarDetalle(tc.getId());
/* 209:    */         
/* 210:228 */         Map<Integer, PlanTarjetaCredito> hmPlan = new HashMap();
/* 211:230 */         for (VersionComision version : tcMapa.getListaVersionComision()) {
/* 212:231 */           for (Comision com : version.getListaComision()) {
/* 213:232 */             hmPlan.put(Integer.valueOf(com.getPlanTarjetaCredito().getId()), com.getPlanTarjetaCredito());
/* 214:    */           }
/* 215:    */         }
/* 216:236 */         tcMapa.getListaPlanTarjetaCredito().addAll(hmPlan.values());
/* 217:    */         
/* 218:238 */         this.hmTarjeta.put(Integer.valueOf(tcMapa.getId()), tcMapa);
/* 219:    */       }
/* 220:241 */       if (tc.getListaPlanTarjetaCredito().isEmpty()) {
/* 221:242 */         tc.getListaPlanTarjetaCredito().addAll(tcMapa.getListaPlanTarjetaCredito());
/* 222:    */       }
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String eliminar()
/* 227:    */   {
/* 228:250 */     return null;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String cargarDatos()
/* 232:    */   {
/* 233:256 */     return null;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public LazyDataModel<Cobro> getListaCobro()
/* 237:    */   {
/* 238:262 */     return this.listaCobro;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setListaCobro(LazyDataModel<Cobro> listaVoucher)
/* 242:    */   {
/* 243:266 */     this.listaCobro = listaVoucher;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Cobro getCobro()
/* 247:    */   {
/* 248:270 */     return this.cobro;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setCobro(Cobro cobro)
/* 252:    */   {
/* 253:274 */     this.cobro = cobro;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public DataTable getDtVoucher()
/* 257:    */   {
/* 258:278 */     return this.dtVoucher;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setDtVoucher(DataTable dtVoucher)
/* 262:    */   {
/* 263:282 */     this.dtVoucher = dtVoucher;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public List<TarjetaCredito> getListaTarjetaCredito()
/* 267:    */   {
/* 268:286 */     if (this.listaTarjetaCredito == null)
/* 269:    */     {
/* 270:287 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 271:288 */       this.listaTarjetaCredito = this.servicioTarjetaCredito.obtenerListaCombo("nombre", false, filtros);
/* 272:290 */       for (TarjetaCredito tc : this.listaTarjetaCredito) {
/* 273:291 */         cargarPlanTarjeta(tc);
/* 274:    */       }
/* 275:    */     }
/* 276:295 */     return this.listaTarjetaCredito;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setListaTarjetaCredito(List<TarjetaCredito> listaTarjetaCredito)
/* 280:    */   {
/* 281:299 */     this.listaTarjetaCredito = listaTarjetaCredito;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public List<PuntoDeVenta> getListaPuntoVenta()
/* 285:    */   {
/* 286:303 */     if (this.listaPuntoVenta == null)
/* 287:    */     {
/* 288:304 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 289:305 */       this.listaPuntoVenta = this.servicioPuntoDeVenta.obtenerListaCombo("codigoAlterno", true, filtros);
/* 290:    */     }
/* 291:308 */     return this.listaPuntoVenta;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setListaPuntoVenta(List<PuntoDeVenta> listaPuntoVenta)
/* 295:    */   {
/* 296:312 */     this.listaPuntoVenta = listaPuntoVenta;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<DetalleFormaCobro> getListaDetalleVoucherFiler()
/* 300:    */   {
/* 301:316 */     return this.listaDetalleVoucherFiler;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setListaDetalleVoucherFiler(List<DetalleFormaCobro> listaDetalleVoucherFiler)
/* 305:    */   {
/* 306:320 */     this.listaDetalleVoucherFiler = listaDetalleVoucherFiler;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public List<DetalleFormaCobro> getListaDetalleVoucher()
/* 310:    */   {
/* 311:324 */     if (this.listaDetalleVoucher == null)
/* 312:    */     {
/* 313:325 */       this.listaDetalleVoucher = new ArrayList();
/* 314:326 */       for (DetalleFormaCobro dc : this.cobro.getListaDetalleFormaCobro()) {
/* 315:327 */         if (!dc.isEliminado()) {
/* 316:328 */           this.listaDetalleVoucher.add(dc);
/* 317:    */         }
/* 318:    */       }
/* 319:    */     }
/* 320:333 */     return this.listaDetalleVoucher;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setListaDetalleVoucher(List<DetalleFormaCobro> listaDetalleVoucher)
/* 324:    */   {
/* 325:337 */     this.listaDetalleVoucher = listaDetalleVoucher;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public List<Documento> getListaDocumento()
/* 329:    */   {
/* 330:341 */     if (this.listaDocumento == null) {
/* 331:    */       try
/* 332:    */       {
/* 333:343 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(getDocumento(), AppUtil.getOrganizacion().getId());
/* 334:    */       }
/* 335:    */       catch (ExcepcionAS2 e)
/* 336:    */       {
/* 337:345 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 338:    */       }
/* 339:    */     }
/* 340:348 */     return this.listaDocumento;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 344:    */   {
/* 345:352 */     if (this.listaCuentaBancariaOrganizacion == null)
/* 346:    */     {
/* 347:353 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 348:354 */       filters.put("tipoCuentaBancariaOrganizacion", getTipoCuentaBancariaOrganizacion().toString());
/* 349:355 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("", true, filters);
/* 350:    */     }
/* 351:358 */     return this.listaCuentaBancariaOrganizacion;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 355:    */   {
/* 356:362 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public List<PlanTarjetaCredito> getListaPlanTarjetaCredito()
/* 360:    */   {
/* 361:366 */     if (this.listaPlanTarjetaCredito == null)
/* 362:    */     {
/* 363:367 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 364:368 */       this.listaPlanTarjetaCredito = this.servicioPlanTarjetaCredito.obtenerListaCombo(PlanTarjetaCredito.class, "codigo", true, filters);
/* 365:    */     }
/* 366:370 */     return this.listaPlanTarjetaCredito;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setListaPlanTarjetaCredito(List<PlanTarjetaCredito> listaPlanTarjetaCredito)
/* 370:    */   {
/* 371:374 */     this.listaPlanTarjetaCredito = listaPlanTarjetaCredito;
/* 372:    */   }
/* 373:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.VoucherBean
 * JD-Core Version:    0.7.0.1
 */