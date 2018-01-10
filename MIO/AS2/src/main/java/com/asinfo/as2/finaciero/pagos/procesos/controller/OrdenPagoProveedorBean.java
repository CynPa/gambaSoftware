/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   9:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  10:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  11:    */ import com.asinfo.as2.entities.DetalleOrdenPagoProveedor;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.Empleado;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  16:    */ import com.asinfo.as2.entities.OrdenPagoProveedor;
/*  17:    */ import com.asinfo.as2.entities.Organizacion;
/*  18:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  19:    */ import com.asinfo.as2.entities.Proveedor;
/*  20:    */ import com.asinfo.as2.entities.Sucursal;
/*  21:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  25:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  26:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioOrdenPagoProveedor;
/*  27:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPagoCash;
/*  28:    */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReportePagoProveedor;
/*  29:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  30:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  31:    */ import com.asinfo.as2.util.AppUtil;
/*  32:    */ import com.asinfo.as2.util.RutaArchivo;
/*  33:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  34:    */ import com.asinfo.as2.utils.JsfUtil;
/*  35:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  36:    */ import java.math.BigDecimal;
/*  37:    */ import java.util.ArrayList;
/*  38:    */ import java.util.Date;
/*  39:    */ import java.util.HashMap;
/*  40:    */ import java.util.List;
/*  41:    */ import java.util.Map;
/*  42:    */ import javax.annotation.PostConstruct;
/*  43:    */ import javax.ejb.EJB;
/*  44:    */ import javax.faces.bean.ManagedBean;
/*  45:    */ import javax.faces.bean.ViewScoped;
/*  46:    */ import javax.faces.event.ActionEvent;
/*  47:    */ import org.apache.log4j.Logger;
/*  48:    */ import org.primefaces.component.datatable.DataTable;
/*  49:    */ import org.primefaces.event.FileUploadEvent;
/*  50:    */ import org.primefaces.event.ToggleEvent;
/*  51:    */ import org.primefaces.model.LazyDataModel;
/*  52:    */ import org.primefaces.model.SortOrder;
/*  53:    */ 
/*  54:    */ @ManagedBean
/*  55:    */ @ViewScoped
/*  56:    */ public class OrdenPagoProveedorBean
/*  57:    */   extends PageControllerAS2
/*  58:    */ {
/*  59:    */   private static final long serialVersionUID = 7085091448710210515L;
/*  60:    */   @EJB
/*  61:    */   protected transient ServicioPagoCash servicioPagoCash;
/*  62:    */   @EJB
/*  63:    */   protected transient ServicioOrdenPagoProveedor servicioOrdenPagoProveedor;
/*  64:    */   @EJB
/*  65:    */   protected transient ServicioDocumento servicioDocumento;
/*  66:    */   @EJB
/*  67:    */   protected transient ServicioReportePagoProveedor servicioReportePagoProveedor;
/*  68:    */   @EJB
/*  69:    */   protected transient ServicioEmpresa servicioEmpresa;
/*  70:    */   @EJB
/*  71:    */   protected transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  72:    */   @EJB
/*  73:    */   private ServicioUsuario servicioUsuario;
/*  74:    */   @EJB
/*  75:    */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*  76:    */   protected OrdenPagoProveedor ordenPagoProveedor;
/*  77:    */   protected List<Documento> listaDocumento;
/*  78:    */   protected BigDecimal valorAnticipo;
/*  79:    */   protected String descripcionAnticipo;
/*  80:    */   protected String numero;
/*  81:    */   protected List<Documento> listaDocumentoAnticipo;
/*  82:    */   protected Empresa empresaAnticipo;
/*  83:    */   private PersonaResponsable personaResponsable;
/*  84:    */   private boolean indicadorRenderedAnticipo;
/*  85:    */   protected LazyDataModel<OrdenPagoProveedor> listaOrdenPagoProveedor;
/*  86:    */   protected DataTable dtOrdenPagoProveedor;
/*  87:    */   protected DataTable dtDetalleOrdenPagoProveedor;
/*  88:    */   protected DataTable dtDetalleFacturasPendientesNoVencidas;
/*  89:    */   protected DataTable dtDetalleCategoriaProveedor;
/*  90:    */   protected DataTable dtDetalleProveedor;
/*  91:    */   protected List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedor;
/*  92:    */   protected List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedorFilters;
/*  93:    */   protected List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedorAprobadas;
/*  94:    */   protected List<CuentaPorPagar> listaFacturasPendientesNoVencidas;
/*  95:    */   protected List<CuentaPorPagar> listaFacturasPendientesNoVencidasFilters;
/*  96:    */   protected List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoSeleccionado;
/*  97:    */   private List<PersonaResponsable> listaPersonaResponsable;
/*  98:    */   protected Map<Integer, Proveedor> mapaProveedor;
/*  99:    */   protected Map<Integer, CategoriaEmpresa> mapaCategoriaProveedor;
/* 100:    */   private String numeroFiltro;
/* 101:134 */   protected boolean indicadorAprobar = false;
/* 102:    */   
/* 103:    */   @PostConstruct
/* 104:    */   public void init()
/* 105:    */   {
/* 106:141 */     setDocumentoBase(DocumentoBase.ORDEN_PAGO_PROVEEDOR);
/* 107:142 */     this.listaOrdenPagoProveedor = new LazyDataModel()
/* 108:    */     {
/* 109:    */       private static final long serialVersionUID = -6092104942165704404L;
/* 110:    */       
/* 111:    */       public List<OrdenPagoProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 112:    */       {
/* 113:154 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 114:155 */         if (OrdenPagoProveedorBean.this.numero != null) {
/* 115:156 */           filters.put("numero", OrdenPagoProveedorBean.this.numero);
/* 116:    */         }
/* 117:    */         int num;
/* 118:158 */         if ((OrdenPagoProveedorBean.this.numeroFiltro != null) && (!OrdenPagoProveedorBean.this.numeroFiltro.isEmpty()))
/* 119:    */         {
/* 120:159 */           List<Integer> listIds = OrdenPagoProveedorBean.this.servicioOrdenPagoProveedor.buscarOrdenPagoProveedorPorNumeroDeFactura(AppUtil.getOrganizacion().getId(), 
/* 121:160 */             OrdenPagoProveedorBean.this.numeroFiltro);
/* 122:161 */           if (listIds.isEmpty()) {
/* 123:162 */             return null;
/* 124:    */           }
/* 125:164 */           num = 1;
/* 126:165 */           for (Integer integer : listIds)
/* 127:    */           {
/* 128:166 */             filters.put("OR~id~0" + num + "~idOrdenPagoProveedor", String.valueOf(integer));
/* 129:167 */             num++;
/* 130:    */           }
/* 131:    */         }
/* 132:171 */         if (filters.size() == 0) {
/* 133:172 */           if (OrdenPagoProveedorBean.this.indicadorAprobar)
/* 134:    */           {
/* 135:173 */             filters.put("AND~est~01~estado", "!=" + Estado.APROBADO.toString());
/* 136:174 */             filters.put("AND~est~02~estado", "!=" + Estado.CERRADO.toString());
/* 137:175 */             filters.put("AND~est~03~estado", "!=" + Estado.ANULADO.toString());
/* 138:176 */             filters.put("AND~est~04~estado", "!=" + Estado.ELABORADO.toString());
/* 139:    */           }
/* 140:    */           else
/* 141:    */           {
/* 142:178 */             filters.put("AND~est~01~estado", "!=" + Estado.ANULADO.name());
/* 143:179 */             filters.put("AND~est~02~estado", "!=" + Estado.CERRADO.name());
/* 144:    */           }
/* 145:    */         }
/* 146:182 */         OrdenPagoProveedorBean.this.agregarFiltroOrganizacion(filters);
/* 147:183 */         List<OrdenPagoProveedor> lista = OrdenPagoProveedorBean.this.servicioOrdenPagoProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 148:    */         
/* 149:    */ 
/* 150:186 */         OrdenPagoProveedorBean.this.listaOrdenPagoProveedor.setRowCount(OrdenPagoProveedorBean.this.servicioOrdenPagoProveedor.contarPorCriterio(filters));
/* 151:187 */         return lista;
/* 152:    */       }
/* 153:    */     };
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void crearOrdenPagoProveedor()
/* 157:    */   {
/* 158:197 */     this.ordenPagoProveedor = new OrdenPagoProveedor();
/* 159:198 */     this.ordenPagoProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 160:199 */     this.ordenPagoProveedor.setIdSucursal(AppUtil.getSucursal().getId());
/* 161:200 */     this.ordenPagoProveedor.setEstado(Estado.ELABORADO);
/* 162:201 */     this.ordenPagoProveedor.setFechaCorte(new Date());
/* 163:    */     
/* 164:203 */     Documento documento = null;
/* 165:204 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 166:    */     {
/* 167:205 */       documento = (Documento)getListaDocumento().get(0);
/* 168:206 */       this.ordenPagoProveedor.setDocumento(documento);
/* 169:    */     }
/* 170:209 */     if ((getListaDocumentoAnticipo() != null) && (!getListaDocumentoAnticipo().isEmpty())) {
/* 171:210 */       this.ordenPagoProveedor.setDocumentoAnticipo((Documento)getListaDocumentoAnticipo().get(0));
/* 172:    */     }
/* 173:213 */     this.ordenPagoProveedor.setNumero("");
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String editar()
/* 177:    */   {
/* 178:223 */     if ((this.ordenPagoProveedor != null) && (this.ordenPagoProveedor.getId() != 0))
/* 179:    */     {
/* 180:224 */       this.ordenPagoProveedor = this.servicioOrdenPagoProveedor.buscarPorId(Integer.valueOf(this.ordenPagoProveedor.getId()));
/* 181:225 */       if (this.ordenPagoProveedor.getEstado().equals(Estado.ELABORADO))
/* 182:    */       {
/* 183:226 */         this.ordenPagoProveedor = this.servicioOrdenPagoProveedor.cargarDetalle(this.ordenPagoProveedor.getId());
/* 184:227 */         setEditado(true);
/* 185:228 */         agruparMapas();
/* 186:229 */         calculoTotales();
/* 187:230 */         this.listaDetalleOrdenPagoProveedor = null;
/* 188:231 */         this.listaDetalleOrdenPagoProveedorFilters = getListaDetalleOrdenPagoProveedor();
/* 189:    */       }
/* 190:    */       else
/* 191:    */       {
/* 192:233 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " - " + this.ordenPagoProveedor.getEstado());
/* 193:    */       }
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:236 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 198:    */     }
/* 199:238 */     return "";
/* 200:    */   }
/* 201:    */   
/* 202:    */   public String guardar()
/* 203:    */   {
/* 204:    */     try
/* 205:    */     {
/* 206:248 */       this.servicioOrdenPagoProveedor.guardar(this.ordenPagoProveedor);
/* 207:    */       
/* 208:250 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 209:251 */       setEditado(false);
/* 210:252 */       limpiar();
/* 211:    */     }
/* 212:    */     catch (AS2Exception e)
/* 213:    */     {
/* 214:254 */       JsfUtil.addErrorMessage(e, "");
/* 215:255 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 216:    */     }
/* 217:    */     catch (Exception e)
/* 218:    */     {
/* 219:257 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 220:258 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 221:259 */       e.printStackTrace();
/* 222:    */     }
/* 223:261 */     return "";
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String eliminar()
/* 227:    */   {
/* 228:270 */     if ((this.ordenPagoProveedor != null) && (this.ordenPagoProveedor.getId() != 0)) {
/* 229:    */       try
/* 230:    */       {
/* 231:272 */         this.servicioOrdenPagoProveedor.anular(this.ordenPagoProveedor);
/* 232:273 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 233:    */       }
/* 234:    */       catch (AS2Exception e)
/* 235:    */       {
/* 236:275 */         JsfUtil.addErrorMessage(e, "");
/* 237:    */       }
/* 238:    */       catch (Exception e)
/* 239:    */       {
/* 240:277 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 241:278 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 242:    */       }
/* 243:    */     } else {
/* 244:281 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 245:    */     }
/* 246:283 */     return "";
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String cargarDatos()
/* 250:    */   {
/* 251:292 */     return "";
/* 252:    */   }
/* 253:    */   
/* 254:    */   public String limpiar()
/* 255:    */   {
/* 256:301 */     crearOrdenPagoProveedor();
/* 257:302 */     this.listaDetalleOrdenPagoProveedor = null;
/* 258:303 */     this.listaDetalleOrdenPagoProveedorFilters = getListaDetalleOrdenPagoProveedor();
/* 259:304 */     this.mapaCategoriaProveedor = new HashMap();
/* 260:305 */     this.mapaProveedor = new HashMap();
/* 261:306 */     this.dtDetalleOrdenPagoProveedor.reset();
/* 262:307 */     this.dtDetalleCategoriaProveedor.reset();
/* 263:308 */     this.dtDetalleProveedor.reset();
/* 264:309 */     this.dtDetalleFacturasPendientesNoVencidas.reset();
/* 265:310 */     return "";
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void cargarFacturasPendientes(ActionEvent event)
/* 269:    */   {
/* 270:319 */     if (this.ordenPagoProveedor.getFechaDesdeFiltro() == null) {
/* 271:320 */       this.ordenPagoProveedor.setFechaDesdeFiltro(FuncionesUtiles.getFecha(1, 1, 1900));
/* 272:    */     }
/* 273:322 */     this.servicioOrdenPagoProveedor.cargarFacturasPendientes(this.ordenPagoProveedor);
/* 274:    */     
/* 275:324 */     this.listaDetalleOrdenPagoProveedor = null;
/* 276:325 */     this.listaDetalleOrdenPagoProveedorFilters = getListaDetalleOrdenPagoProveedor();
/* 277:326 */     this.dtDetalleOrdenPagoProveedor.reset();
/* 278:327 */     agruparMapas();
/* 279:328 */     calculoTotales();
/* 280:329 */     this.ordenPagoProveedor.setFechaDesdeFiltro(null);
/* 281:    */   }
/* 282:    */   
/* 283:    */   protected void agruparMapas()
/* 284:    */   {
/* 285:334 */     this.mapaCategoriaProveedor = new HashMap();
/* 286:335 */     this.mapaProveedor = new HashMap();
/* 287:336 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 288:337 */       if (!detalleOrdenPago.isEliminado()) {
/* 289:339 */         if ((!this.indicadorAprobar) || (detalleOrdenPago.getValor().compareTo(BigDecimal.ZERO) > 0) || (detalleOrdenPago.isIndicadorAprobacionManual()))
/* 290:    */         {
/* 291:340 */           Proveedor proveedor = detalleOrdenPago.getProveedor();
/* 292:    */           
/* 293:342 */           CategoriaEmpresa categoriaProveedor = proveedor.getEmpresa().getCategoriaEmpresa();
/* 294:343 */           if (!this.mapaCategoriaProveedor.containsKey(Integer.valueOf(categoriaProveedor.getId()))) {
/* 295:344 */             categoriaProveedor.setMapaProveedor(new HashMap());
/* 296:    */           } else {
/* 297:346 */             categoriaProveedor = (CategoriaEmpresa)this.mapaCategoriaProveedor.get(Integer.valueOf(categoriaProveedor.getId()));
/* 298:    */           }
/* 299:348 */           if (!this.mapaProveedor.containsKey(Integer.valueOf(proveedor.getId())))
/* 300:    */           {
/* 301:350 */             this.mapaCategoriaProveedor.put(Integer.valueOf(categoriaProveedor.getId()), categoriaProveedor);
/* 302:351 */             proveedor.setListaDetalleOrdenPagoProveedor(new ArrayList());
/* 303:    */           }
/* 304:353 */           proveedor.getListaDetalleOrdenPagoProveedor().add(detalleOrdenPago);
/* 305:354 */           categoriaProveedor.getMapaProveedor().put(Integer.valueOf(proveedor.getId()), proveedor);
/* 306:355 */           this.mapaProveedor.put(Integer.valueOf(proveedor.getId()), proveedor);
/* 307:    */         }
/* 308:    */       }
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void cargarFacturasPendientesNoVencidas()
/* 313:    */   {
/* 314:362 */     List<CuentaPorPagar> lista = this.servicioFacturaProveedor.obtenerFacturasPendientes(0, 0, null, null, null, 0, null, null, 
/* 315:363 */       Integer.valueOf(this.ordenPagoProveedor.getIdOrganizacion()), this.ordenPagoProveedor.getFechaCorte(), Boolean.valueOf(false));
/* 316:364 */     this.listaFacturasPendientesNoVencidas = new ArrayList();
/* 317:366 */     for (CuentaPorPagar cuentaPorPagar : lista)
/* 318:    */     {
/* 319:367 */       boolean encontre = false;
/* 320:368 */       for (DetalleOrdenPagoProveedor detalle : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 321:369 */         if ((!detalle.isEliminado()) && (detalle.getCuentaPorPagar() != null) && (detalle.getCuentaPorPagar().getId() == cuentaPorPagar.getId()))
/* 322:    */         {
/* 323:370 */           encontre = true;
/* 324:371 */           break;
/* 325:    */         }
/* 326:    */       }
/* 327:374 */       if (!encontre) {
/* 328:375 */         this.listaFacturasPendientesNoVencidas.add(cuentaPorPagar);
/* 329:    */       }
/* 330:    */     }
/* 331:    */   }
/* 332:    */   
/* 333:    */   public DetalleOrdenPagoProveedor agregarAnticipo()
/* 334:    */   {
/* 335:386 */     DetalleOrdenPagoProveedor detalleOrdenPago = null;
/* 336:387 */     if (this.servicioEmpresa.obtenerListaCuentaBancariaEmpresa(AppUtil.getOrganizacion().getId(), this.empresaAnticipo.getId()).size() > 0)
/* 337:    */     {
/* 338:388 */       detalleOrdenPago = new DetalleOrdenPagoProveedor();
/* 339:389 */       this.empresaAnticipo = this.servicioEmpresa.cargarDetalle(this.empresaAnticipo);
/* 340:390 */       detalleOrdenPago.setProveedor(this.empresaAnticipo.getProveedor());
/* 341:391 */       detalleOrdenPago.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 342:392 */       detalleOrdenPago.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 343:393 */       detalleOrdenPago.setCuentaPorPagar(null);
/* 344:394 */       detalleOrdenPago.setValor(this.valorAnticipo);
/* 345:395 */       detalleOrdenPago.setOrdenPagoProveedor(this.ordenPagoProveedor);
/* 346:396 */       detalleOrdenPago.setDescripcion(this.descripcionAnticipo);
/* 347:397 */       detalleOrdenPago.setIndicadorAnticipo(true);
/* 348:398 */       detalleOrdenPago.setPersonaResponsable(getPersonaResponsable());
/* 349:    */       
/* 350:400 */       this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor().add(detalleOrdenPago);
/* 351:401 */       this.empresaAnticipo = null;
/* 352:402 */       this.valorAnticipo = null;
/* 353:403 */       this.descripcionAnticipo = "";
/* 354:404 */       agruparMapas();
/* 355:405 */       calculoTotales();
/* 356:    */     }
/* 357:    */     else
/* 358:    */     {
/* 359:407 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cuenta_bancaria_proveedor"));
/* 360:408 */       this.empresaAnticipo = null;
/* 361:409 */       this.valorAnticipo = null;
/* 362:410 */       this.descripcionAnticipo = "";
/* 363:    */     }
/* 364:412 */     this.listaDetalleOrdenPagoProveedor = null;
/* 365:413 */     this.listaDetalleOrdenPagoProveedorFilters = getListaDetalleOrdenPagoProveedor();
/* 366:414 */     this.dtDetalleOrdenPagoProveedor.reset();
/* 367:415 */     setIndicadorRenderedAnticipo(false);
/* 368:416 */     return detalleOrdenPago;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public DetalleOrdenPagoProveedor agregarFactura(CuentaPorPagar cxp)
/* 372:    */   {
/* 373:420 */     DetalleOrdenPagoProveedor detalleOrdenPago = new DetalleOrdenPagoProveedor();
/* 374:421 */     detalleOrdenPago.setProveedor(cxp.getFacturaProveedor().getEmpresa().getProveedor());
/* 375:422 */     detalleOrdenPago.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 376:423 */     detalleOrdenPago.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 377:424 */     detalleOrdenPago.setCuentaPorPagar(cxp);
/* 378:425 */     detalleOrdenPago.setDiasVencidos((int)FuncionesUtiles.DiasEntreFechas(cxp.getFechaVencimiento(), this.ordenPagoProveedor.getFechaCorte()) - 1);
/* 379:426 */     detalleOrdenPago.setValor(BigDecimal.ZERO);
/* 380:427 */     detalleOrdenPago.setOrdenPagoProveedor(this.ordenPagoProveedor);
/* 381:428 */     detalleOrdenPago.setIndicadorAnticipo(false);
/* 382:429 */     if (this.indicadorAprobar)
/* 383:    */     {
/* 384:430 */       detalleOrdenPago.setIndicadorAprobacionManual(true);
/* 385:431 */       detalleOrdenPago.setValorAprobado(cxp.getSaldo());
/* 386:    */     }
/* 387:434 */     this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor().add(detalleOrdenPago);
/* 388:435 */     agruparMapas();
/* 389:436 */     calculoTotales();
/* 390:437 */     cargarFacturasPendientesNoVencidas();
/* 391:438 */     this.dtDetalleFacturasPendientesNoVencidas.reset();
/* 392:    */     
/* 393:440 */     this.listaDetalleOrdenPagoProveedor = null;
/* 394:441 */     this.listaDetalleOrdenPagoProveedorFilters = getListaDetalleOrdenPagoProveedor();
/* 395:442 */     this.dtDetalleOrdenPagoProveedor.reset();
/* 396:443 */     return detalleOrdenPago;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void calculoTotales()
/* 400:    */   {
/* 401:447 */     reiniciarTotalesMapas();
/* 402:448 */     BigDecimal totalLiquidar = BigDecimal.ZERO;
/* 403:449 */     BigDecimal totalPendiente = BigDecimal.ZERO;
/* 404:450 */     BigDecimal totalAprobado = BigDecimal.ZERO;
/* 405:451 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : getListaDetalleOrdenPagoProveedor()) {
/* 406:452 */       if (!detalleOrdenPago.isEliminado())
/* 407:    */       {
/* 408:453 */         totalLiquidar = totalLiquidar.add(detalleOrdenPago.getValor());
/* 409:454 */         totalAprobado = totalAprobado.add(detalleOrdenPago.getValorAprobado());
/* 410:    */         
/* 411:456 */         Proveedor proveedor = (Proveedor)this.mapaProveedor.get(Integer.valueOf(detalleOrdenPago.getProveedor().getId()));
/* 412:457 */         proveedor.setValorSolicitadoPago(proveedor.getValorSolicitadoPago().add(detalleOrdenPago.getValor()));
/* 413:458 */         proveedor.setValorAprobadoPago(proveedor.getValorAprobadoPago().add(detalleOrdenPago.getValorAprobado()));
/* 414:    */         
/* 415:460 */         CategoriaEmpresa categoria = (CategoriaEmpresa)this.mapaCategoriaProveedor.get(Integer.valueOf(detalleOrdenPago.getProveedor().getEmpresa().getCategoriaEmpresa().getId()));
/* 416:461 */         categoria.setValorSolicitadoPago(categoria.getValorSolicitadoPago().add(detalleOrdenPago.getValor()));
/* 417:462 */         categoria.setValorAprobadoPago(categoria.getValorAprobadoPago().add(detalleOrdenPago.getValorAprobado()));
/* 418:464 */         if (!detalleOrdenPago.isIndicadorAnticipo())
/* 419:    */         {
/* 420:465 */           totalPendiente = totalPendiente.add(detalleOrdenPago.getCuentaPorPagar().getSaldo());
/* 421:466 */           proveedor.setValorPendientePago(proveedor.getValorPendientePago().add(detalleOrdenPago.getCuentaPorPagar().getSaldo()));
/* 422:467 */           categoria.setValorPendientePago(categoria.getValorPendientePago().add(detalleOrdenPago.getCuentaPorPagar().getSaldo()));
/* 423:    */         }
/* 424:    */       }
/* 425:    */     }
/* 426:471 */     this.ordenPagoProveedor.setValorPago(totalLiquidar);
/* 427:472 */     this.ordenPagoProveedor.setValorPendiente(totalPendiente);
/* 428:473 */     this.ordenPagoProveedor.setValorAprobado(totalAprobado);
/* 429:    */   }
/* 430:    */   
/* 431:    */   protected void reiniciarTotalesMapas()
/* 432:    */   {
/* 433:477 */     for (Proveedor proveedor : this.mapaProveedor.values())
/* 434:    */     {
/* 435:478 */       proveedor.setValorAprobadoPago(BigDecimal.ZERO);
/* 436:479 */       proveedor.setValorPendientePago(BigDecimal.ZERO);
/* 437:480 */       proveedor.setValorSolicitadoPago(BigDecimal.ZERO);
/* 438:481 */       proveedor.setValorPagado(BigDecimal.ZERO);
/* 439:    */     }
/* 440:483 */     for (CategoriaEmpresa ce : this.mapaCategoriaProveedor.values())
/* 441:    */     {
/* 442:484 */       ce.setValorAprobadoPago(BigDecimal.ZERO);
/* 443:485 */       ce.setValorPendientePago(BigDecimal.ZERO);
/* 444:486 */       ce.setValorSolicitadoPago(BigDecimal.ZERO);
/* 445:    */     }
/* 446:    */   }
/* 447:    */   
/* 448:    */   public String eliminarDetalle()
/* 449:    */   {
/* 450:496 */     DetalleOrdenPagoProveedor detalleOrdenPago = (DetalleOrdenPagoProveedor)this.dtDetalleOrdenPagoProveedor.getRowData();
/* 451:497 */     detalleOrdenPago.setEliminado(true);
/* 452:498 */     return "";
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void cerrarOrdenPagoProveedor()
/* 456:    */   {
/* 457:502 */     this.servicioOrdenPagoProveedor.cerrar(this.ordenPagoProveedor, AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 458:503 */     addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 459:    */   }
/* 460:    */   
/* 461:    */   public void revisarOrdenPagoProveedor()
/* 462:    */   {
/* 463:507 */     this.servicioOrdenPagoProveedor.revisarOrdenPagoProveedor(this.ordenPagoProveedor, AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void cargarDetalleOrdenPago(ToggleEvent event)
/* 467:    */   {
/* 468:511 */     this.ordenPagoProveedor = ((OrdenPagoProveedor)event.getData());
/* 469:512 */     this.ordenPagoProveedor = this.servicioOrdenPagoProveedor.cargarDetalle(this.ordenPagoProveedor.getId());
/* 470:513 */     setListaDetalleOrdenPagoSeleccionado(this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor());
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void cargarDetallesAprobados()
/* 474:    */   {
/* 475:517 */     this.listaDetalleOrdenPagoProveedorAprobadas = this.servicioOrdenPagoProveedor.buscarDetallesPendientesPago(AppUtil.getOrganizacion().getId(), null, null);
/* 476:    */   }
/* 477:    */   
/* 478:    */   public void processDownload()
/* 479:    */   {
/* 480:    */     try
/* 481:    */     {
/* 482:527 */       OrdenPagoProveedor fp = (OrdenPagoProveedor)this.dtOrdenPagoProveedor.getRowData();
/* 483:528 */       String fileName = fp.getPdf();
/* 484:529 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "orden_pago_proveedor");
/* 485:531 */       if (fileName == null) {
/* 486:532 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 487:    */       } else {
/* 488:534 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 489:    */       }
/* 490:    */     }
/* 491:    */     catch (Exception e)
/* 492:    */     {
/* 493:538 */       e.printStackTrace();
/* 494:539 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 495:    */     }
/* 496:    */   }
/* 497:    */   
/* 498:    */   public String eliminarArchivo()
/* 499:    */   {
/* 500:544 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getOrdenPagoProveedor().getPdf());
/* 501:545 */     getOrdenPagoProveedor().setPdf(null);
/* 502:546 */     HashMap<String, Object> campos = new HashMap();
/* 503:547 */     campos.put("pdf", null);
/* 504:548 */     this.servicioOrdenPagoProveedor.actualizarAtributoEntidad(getOrdenPagoProveedor(), campos);
/* 505:549 */     return null;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public void processUpload(FileUploadEvent event)
/* 509:    */   {
/* 510:    */     try
/* 511:    */     {
/* 512:562 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "orden_pago_proveedor");
/* 513:    */       
/* 514:564 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getOrdenPagoProveedor().getNumero(), uploadDir);
/* 515:    */       
/* 516:    */ 
/* 517:567 */       HashMap<String, Object> campos = new HashMap();
/* 518:568 */       campos.put("pdf", fileName);
/* 519:569 */       this.servicioOrdenPagoProveedor.actualizarAtributoEntidad(getOrdenPagoProveedor(), campos);
/* 520:    */       
/* 521:571 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 522:    */     }
/* 523:    */     catch (Exception e)
/* 524:    */     {
/* 525:574 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 526:575 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 527:    */     }
/* 528:    */   }
/* 529:    */   
/* 530:    */   public String getDirectorioDescarga()
/* 531:    */   {
/* 532:582 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "orden_pago_proveedor");
/* 533:    */   }
/* 534:    */   
/* 535:    */   public List<Documento> getListaDocumento()
/* 536:    */   {
/* 537:591 */     if (this.listaDocumento == null) {
/* 538:    */       try
/* 539:    */       {
/* 540:593 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.ORDEN_PAGO_PROVEEDOR, AppUtil.getOrganizacion()
/* 541:594 */           .getId());
/* 542:    */       }
/* 543:    */       catch (ExcepcionAS2 e)
/* 544:    */       {
/* 545:596 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 546:    */       }
/* 547:    */     }
/* 548:599 */     return this.listaDocumento;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 552:    */   {
/* 553:609 */     this.listaDocumento = listaDocumento;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public BigDecimal getValorAnticipo()
/* 557:    */   {
/* 558:618 */     return this.valorAnticipo;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public void setValorAnticipo(BigDecimal valorAnticipo)
/* 562:    */   {
/* 563:628 */     this.valorAnticipo = valorAnticipo;
/* 564:    */   }
/* 565:    */   
/* 566:    */   public String getDescripcionAnticipo()
/* 567:    */   {
/* 568:637 */     return this.descripcionAnticipo;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public void setDescripcionAnticipo(String descripcionAnticipo)
/* 572:    */   {
/* 573:647 */     this.descripcionAnticipo = descripcionAnticipo;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public List<Documento> getListaDocumentoAnticipo()
/* 577:    */   {
/* 578:654 */     if (this.listaDocumentoAnticipo == null) {
/* 579:    */       try
/* 580:    */       {
/* 581:656 */         this.listaDocumentoAnticipo = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.ANTICIPO_PROVEEDOR, 
/* 582:657 */           AppUtil.getOrganizacion().getId());
/* 583:    */       }
/* 584:    */       catch (ExcepcionAS2 e)
/* 585:    */       {
/* 586:659 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 587:    */       }
/* 588:    */     }
/* 589:663 */     return this.listaDocumentoAnticipo;
/* 590:    */   }
/* 591:    */   
/* 592:    */   public void setListaDocumentoAnticipo(List<Documento> listaDocumentoAnticipo)
/* 593:    */   {
/* 594:671 */     this.listaDocumentoAnticipo = listaDocumentoAnticipo;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public OrdenPagoProveedor getOrdenPagoProveedor()
/* 598:    */   {
/* 599:675 */     return this.ordenPagoProveedor;
/* 600:    */   }
/* 601:    */   
/* 602:    */   public void setOrdenPagoProveedor(OrdenPagoProveedor ordenPagoProveedor)
/* 603:    */   {
/* 604:679 */     this.ordenPagoProveedor = ordenPagoProveedor;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public Empresa getEmpresaAnticipo()
/* 608:    */   {
/* 609:683 */     return this.empresaAnticipo;
/* 610:    */   }
/* 611:    */   
/* 612:    */   public void setEmpresaAnticipo(Empresa empresaAnticipo)
/* 613:    */   {
/* 614:687 */     this.empresaAnticipo = empresaAnticipo;
/* 615:    */   }
/* 616:    */   
/* 617:    */   public LazyDataModel<OrdenPagoProveedor> getListaOrdenPagoProveedor()
/* 618:    */   {
/* 619:691 */     return this.listaOrdenPagoProveedor;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public void setListaOrdenPagoProveedor(LazyDataModel<OrdenPagoProveedor> listaOrdenPagoProveedor)
/* 623:    */   {
/* 624:695 */     this.listaOrdenPagoProveedor = listaOrdenPagoProveedor;
/* 625:    */   }
/* 626:    */   
/* 627:    */   public DataTable getDtOrdenPagoProveedor()
/* 628:    */   {
/* 629:699 */     return this.dtOrdenPagoProveedor;
/* 630:    */   }
/* 631:    */   
/* 632:    */   public void setDtOrdenPagoProveedor(DataTable dtOrdenPagoProveedor)
/* 633:    */   {
/* 634:703 */     this.dtOrdenPagoProveedor = dtOrdenPagoProveedor;
/* 635:    */   }
/* 636:    */   
/* 637:    */   public DataTable getDtDetalleOrdenPagoProveedor()
/* 638:    */   {
/* 639:707 */     return this.dtDetalleOrdenPagoProveedor;
/* 640:    */   }
/* 641:    */   
/* 642:    */   public void setDtDetalleOrdenPagoProveedor(DataTable dtDetalleOrdenPagoProveedor)
/* 643:    */   {
/* 644:711 */     this.dtDetalleOrdenPagoProveedor = dtDetalleOrdenPagoProveedor;
/* 645:    */   }
/* 646:    */   
/* 647:    */   public List<DetalleOrdenPagoProveedor> getListaDetalleOrdenPagoProveedor()
/* 648:    */   {
/* 649:715 */     this.listaDetalleOrdenPagoProveedor = new ArrayList();
/* 650:716 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 651:717 */       if (!detalleOrdenPago.isEliminado()) {
/* 652:718 */         this.listaDetalleOrdenPagoProveedor.add(detalleOrdenPago);
/* 653:    */       }
/* 654:    */     }
/* 655:721 */     return this.listaDetalleOrdenPagoProveedor;
/* 656:    */   }
/* 657:    */   
/* 658:    */   public void setListaDetalleOrdenPagoProveedor(List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedor)
/* 659:    */   {
/* 660:725 */     this.listaDetalleOrdenPagoProveedor = listaDetalleOrdenPagoProveedor;
/* 661:    */   }
/* 662:    */   
/* 663:    */   public List<DetalleOrdenPagoProveedor> getListaDetalleOrdenPagoProveedorFilters()
/* 664:    */   {
/* 665:729 */     return this.listaDetalleOrdenPagoProveedorFilters;
/* 666:    */   }
/* 667:    */   
/* 668:    */   public void setListaDetalleOrdenPagoProveedorFilters(List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedorFilters)
/* 669:    */   {
/* 670:733 */     this.listaDetalleOrdenPagoProveedorFilters = listaDetalleOrdenPagoProveedorFilters;
/* 671:    */   }
/* 672:    */   
/* 673:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 674:    */   {
/* 675:737 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 676:    */   }
/* 677:    */   
/* 678:    */   public void cargarValorCuota()
/* 679:    */   {
/* 680:741 */     DetalleOrdenPagoProveedor detalleOrdenPago = (DetalleOrdenPagoProveedor)this.dtDetalleOrdenPagoProveedor.getRowData();
/* 681:742 */     if (!detalleOrdenPago.isIndicadorAnticipo())
/* 682:    */     {
/* 683:743 */       detalleOrdenPago.setValor(detalleOrdenPago.getCuentaPorPagar().getSaldo());
/* 684:744 */       calculoTotales();
/* 685:    */     }
/* 686:    */   }
/* 687:    */   
/* 688:    */   public void cargarValorCuotaGlobal()
/* 689:    */   {
/* 690:749 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 691:750 */       if (!detalleOrdenPago.isIndicadorAnticipo()) {
/* 692:751 */         detalleOrdenPago.setValor(detalleOrdenPago.getCuentaPorPagar().getSaldo());
/* 693:    */       }
/* 694:    */     }
/* 695:754 */     calculoTotales();
/* 696:    */   }
/* 697:    */   
/* 698:    */   public void limpiarValorCuota()
/* 699:    */   {
/* 700:758 */     DetalleOrdenPagoProveedor detalleOrdenPago = (DetalleOrdenPagoProveedor)this.dtDetalleOrdenPagoProveedor.getRowData();
/* 701:759 */     detalleOrdenPago.setValor(BigDecimal.ZERO);
/* 702:760 */     calculoTotales();
/* 703:    */   }
/* 704:    */   
/* 705:    */   public void limpiarValorCuotaGlobal()
/* 706:    */   {
/* 707:764 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 708:765 */       detalleOrdenPago.setValor(BigDecimal.ZERO);
/* 709:    */     }
/* 710:767 */     calculoTotales();
/* 711:    */   }
/* 712:    */   
/* 713:    */   public void cargarValorCuotaGlobalProveedor(CategoriaEmpresa categoriaEmpresa)
/* 714:    */   {
/* 715:771 */     for (Proveedor proveedor : categoriaEmpresa.getListaProveedor()) {
/* 716:772 */       for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 717:773 */         if (!detalleOrdenPago.isIndicadorAnticipo()) {
/* 718:774 */           detalleOrdenPago.setValor(detalleOrdenPago.getCuentaPorPagar().getSaldo());
/* 719:    */         }
/* 720:    */       }
/* 721:    */     }
/* 722:778 */     calculoTotales();
/* 723:    */   }
/* 724:    */   
/* 725:    */   public void cargarValorCuotaProveedor()
/* 726:    */   {
/* 727:782 */     Proveedor proveedor = (Proveedor)this.dtDetalleProveedor.getRowData();
/* 728:783 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 729:784 */       if (!detalleOrdenPago.isIndicadorAnticipo()) {
/* 730:785 */         detalleOrdenPago.setValor(detalleOrdenPago.getCuentaPorPagar().getSaldo());
/* 731:    */       }
/* 732:    */     }
/* 733:788 */     calculoTotales();
/* 734:    */   }
/* 735:    */   
/* 736:    */   public void limpiarValorCuotaGlobalProveedor(CategoriaEmpresa categoriaEmpresa)
/* 737:    */   {
/* 738:792 */     for (Proveedor proveedor : categoriaEmpresa.getListaProveedor()) {
/* 739:793 */       for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 740:794 */         detalleOrdenPago.setValor(BigDecimal.ZERO);
/* 741:    */       }
/* 742:    */     }
/* 743:797 */     calculoTotales();
/* 744:    */   }
/* 745:    */   
/* 746:    */   public void limpiarValorCuotaProveedor()
/* 747:    */   {
/* 748:801 */     Proveedor proveedor = (Proveedor)this.dtDetalleProveedor.getRowData();
/* 749:802 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 750:803 */       detalleOrdenPago.setValor(BigDecimal.ZERO);
/* 751:    */     }
/* 752:805 */     calculoTotales();
/* 753:    */   }
/* 754:    */   
/* 755:    */   public void cargarTabla()
/* 756:    */   {
/* 757:809 */     this.listaDetalleOrdenPagoProveedorFilters = getListaDetalleOrdenPagoProveedor();
/* 758:    */   }
/* 759:    */   
/* 760:    */   public List<CuentaPorPagar> getListaFacturasPendientesNoVencidas()
/* 761:    */   {
/* 762:813 */     return this.listaFacturasPendientesNoVencidas;
/* 763:    */   }
/* 764:    */   
/* 765:    */   public void setListaFacturasPendientesNoVencidas(List<CuentaPorPagar> listaFacturasPendientesNoVencidas)
/* 766:    */   {
/* 767:817 */     this.listaFacturasPendientesNoVencidas = listaFacturasPendientesNoVencidas;
/* 768:    */   }
/* 769:    */   
/* 770:    */   public List<CuentaPorPagar> getListaFacturasPendientesNoVencidasFilters()
/* 771:    */   {
/* 772:821 */     return this.listaFacturasPendientesNoVencidasFilters;
/* 773:    */   }
/* 774:    */   
/* 775:    */   public void setListaFacturasPendientesNoVencidasFilters(List<CuentaPorPagar> listaFacturasPendientesNoVencidasFilters)
/* 776:    */   {
/* 777:825 */     this.listaFacturasPendientesNoVencidasFilters = listaFacturasPendientesNoVencidasFilters;
/* 778:    */   }
/* 779:    */   
/* 780:    */   public DataTable getDtDetalleFacturasPendientesNoVencidas()
/* 781:    */   {
/* 782:829 */     return this.dtDetalleFacturasPendientesNoVencidas;
/* 783:    */   }
/* 784:    */   
/* 785:    */   public void setDtDetalleFacturasPendientesNoVencidas(DataTable dtDetalleFacturasPendientesNoVencidas)
/* 786:    */   {
/* 787:833 */     this.dtDetalleFacturasPendientesNoVencidas = dtDetalleFacturasPendientesNoVencidas;
/* 788:    */   }
/* 789:    */   
/* 790:    */   public List<CategoriaEmpresa> getListaCategoriaProveedor()
/* 791:    */   {
/* 792:837 */     List<CategoriaEmpresa> listaCategoriaEmpresa = new ArrayList();
/* 793:838 */     if (this.mapaCategoriaProveedor != null) {
/* 794:839 */       listaCategoriaEmpresa.addAll(this.mapaCategoriaProveedor.values());
/* 795:    */     }
/* 796:841 */     return listaCategoriaEmpresa;
/* 797:    */   }
/* 798:    */   
/* 799:    */   public Map<Integer, Proveedor> getMapaProveedor()
/* 800:    */   {
/* 801:845 */     return this.mapaProveedor;
/* 802:    */   }
/* 803:    */   
/* 804:    */   public void setMapaProveedor(Map<Integer, Proveedor> mapaProveedor)
/* 805:    */   {
/* 806:849 */     this.mapaProveedor = mapaProveedor;
/* 807:    */   }
/* 808:    */   
/* 809:    */   public Map<Integer, CategoriaEmpresa> getMapaCategoriaProveedor()
/* 810:    */   {
/* 811:853 */     return this.mapaCategoriaProveedor;
/* 812:    */   }
/* 813:    */   
/* 814:    */   public void setMapaCategoriaProveedor(Map<Integer, CategoriaEmpresa> mapaCategoriaProveedor)
/* 815:    */   {
/* 816:857 */     this.mapaCategoriaProveedor = mapaCategoriaProveedor;
/* 817:    */   }
/* 818:    */   
/* 819:    */   public DataTable getDtDetalleCategoriaProveedor()
/* 820:    */   {
/* 821:861 */     return this.dtDetalleCategoriaProveedor;
/* 822:    */   }
/* 823:    */   
/* 824:    */   public void setDtDetalleCategoriaProveedor(DataTable dtDetalleCategoriaProveedor)
/* 825:    */   {
/* 826:865 */     this.dtDetalleCategoriaProveedor = dtDetalleCategoriaProveedor;
/* 827:    */   }
/* 828:    */   
/* 829:    */   public DataTable getDtDetalleProveedor()
/* 830:    */   {
/* 831:869 */     return this.dtDetalleProveedor;
/* 832:    */   }
/* 833:    */   
/* 834:    */   public void setDtDetalleProveedor(DataTable dtDetalleProveedor)
/* 835:    */   {
/* 836:873 */     this.dtDetalleProveedor = dtDetalleProveedor;
/* 837:    */   }
/* 838:    */   
/* 839:    */   public List<DetalleOrdenPagoProveedor> getListaDetalleOrdenPagoSeleccionado()
/* 840:    */   {
/* 841:877 */     return this.listaDetalleOrdenPagoSeleccionado;
/* 842:    */   }
/* 843:    */   
/* 844:    */   public void setListaDetalleOrdenPagoSeleccionado(List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoSeleccionado)
/* 845:    */   {
/* 846:881 */     this.listaDetalleOrdenPagoSeleccionado = listaDetalleOrdenPagoSeleccionado;
/* 847:    */   }
/* 848:    */   
/* 849:    */   public String getNumeroFiltro()
/* 850:    */   {
/* 851:885 */     return this.numeroFiltro;
/* 852:    */   }
/* 853:    */   
/* 854:    */   public void setNumeroFiltro(String numeroFiltro)
/* 855:    */   {
/* 856:889 */     this.numeroFiltro = numeroFiltro;
/* 857:    */   }
/* 858:    */   
/* 859:    */   public List<DetalleOrdenPagoProveedor> getListaDetalleOrdenPagoProveedorAprobadas()
/* 860:    */   {
/* 861:893 */     return this.listaDetalleOrdenPagoProveedorAprobadas;
/* 862:    */   }
/* 863:    */   
/* 864:    */   public void setListaDetalleOrdenPagoProveedorAprobadas(List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedorAprobadas)
/* 865:    */   {
/* 866:897 */     this.listaDetalleOrdenPagoProveedorAprobadas = listaDetalleOrdenPagoProveedorAprobadas;
/* 867:    */   }
/* 868:    */   
/* 869:    */   public String getNumero()
/* 870:    */   {
/* 871:901 */     return this.numero;
/* 872:    */   }
/* 873:    */   
/* 874:    */   public void setNumero(String numero)
/* 875:    */   {
/* 876:905 */     this.numero = numero;
/* 877:    */   }
/* 878:    */   
/* 879:    */   public List<PersonaResponsable> getListaPersonaResponsable()
/* 880:    */   {
/* 881:909 */     if (this.listaPersonaResponsable == null)
/* 882:    */     {
/* 883:910 */       HashMap<String, String> filters = new HashMap();
/* 884:911 */       filters.put("indicadorCompra", "true");
/* 885:912 */       this.listaPersonaResponsable = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filters);
/* 886:    */     }
/* 887:914 */     return this.listaPersonaResponsable;
/* 888:    */   }
/* 889:    */   
/* 890:    */   public void setListaPersonaResponsable(List<PersonaResponsable> listaPersonaResponsable)
/* 891:    */   {
/* 892:918 */     this.listaPersonaResponsable = listaPersonaResponsable;
/* 893:    */   }
/* 894:    */   
/* 895:    */   public PersonaResponsable getPersonaResponsable()
/* 896:    */   {
/* 897:    */     List<EntidadUsuario> listaUsuario;
/* 898:922 */     if ((isIndicadorOrdenCompraConPersonaResponsable()) && (this.personaResponsable == null))
/* 899:    */     {
/* 900:925 */       HashMap<String, String> filters = new HashMap();
/* 901:926 */       filters.put("idUsuario", "" + AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 902:927 */       listaUsuario = this.servicioUsuario.obtenerListaCombo("", true, filters);
/* 903:930 */       if ((listaUsuario != null) && (!listaUsuario.isEmpty()) && (((EntidadUsuario)listaUsuario.get(0)).getEmpleado() != null)) {
/* 904:931 */         for (PersonaResponsable personaResponsable : getListaPersonaResponsable()) {
/* 905:932 */           if ((personaResponsable.getEmpleado() != null) && 
/* 906:933 */             (personaResponsable.getEmpleado().getIdEmpleado() == ((EntidadUsuario)listaUsuario.get(0)).getEmpleado().getIdEmpleado()))
/* 907:    */           {
/* 908:934 */             setPersonaResponsable(personaResponsable);
/* 909:935 */             break;
/* 910:    */           }
/* 911:    */         }
/* 912:    */       }
/* 913:    */     }
/* 914:940 */     return this.personaResponsable;
/* 915:    */   }
/* 916:    */   
/* 917:    */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/* 918:    */   {
/* 919:944 */     this.personaResponsable = personaResponsable;
/* 920:    */   }
/* 921:    */   
/* 922:    */   public boolean isIndicadorOrdenCompraConPersonaResponsable()
/* 923:    */   {
/* 924:948 */     return ParametrosSistema.isOrdenCompraConPersonaResponsable(AppUtil.getOrganizacion().getId()).booleanValue();
/* 925:    */   }
/* 926:    */   
/* 927:    */   public boolean isIndicadorRenderedAnticipo()
/* 928:    */   {
/* 929:952 */     return this.indicadorRenderedAnticipo;
/* 930:    */   }
/* 931:    */   
/* 932:    */   public void setIndicadorRenderedAnticipo(boolean indicadorRenderedAnticipo)
/* 933:    */   {
/* 934:956 */     this.indicadorRenderedAnticipo = indicadorRenderedAnticipo;
/* 935:    */   }
/* 936:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.OrdenPagoProveedorBean
 * JD-Core Version:    0.7.0.1
 */