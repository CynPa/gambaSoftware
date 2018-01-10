/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.SaldoNegativoProducto;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  12:    */ import com.asinfo.as2.entities.Periodo;
/*  13:    */ import com.asinfo.as2.entities.Producto;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  19:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  20:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  23:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*  24:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  25:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  26:    */ import com.asinfo.as2.util.AppUtil;
/*  27:    */ import com.asinfo.as2.ventas.procesos.ErrorCarga;
/*  28:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.Date;
/*  31:    */ import java.util.Iterator;
/*  32:    */ import java.util.List;
/*  33:    */ import javax.ejb.EJB;
/*  34:    */ import javax.faces.bean.ManagedBean;
/*  35:    */ import javax.faces.bean.ManagedProperty;
/*  36:    */ import javax.faces.bean.ViewScoped;
/*  37:    */ import javax.persistence.Temporal;
/*  38:    */ import javax.persistence.TemporalType;
/*  39:    */ import javax.validation.constraints.NotNull;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ 
/*  42:    */ @ManagedBean
/*  43:    */ @ViewScoped
/*  44:    */ public class CosteoBean
/*  45:    */   extends PageController
/*  46:    */ {
/*  47:    */   private static final long serialVersionUID = -2705895877359168158L;
/*  48:    */   @EJB
/*  49:    */   private ServicioCosteo servicioCosteo;
/*  50:    */   @EJB
/*  51:    */   private ServicioBodega servicioBodega;
/*  52:    */   @EJB
/*  53:    */   private ServicioInventarioProducto servicioInventarioProducto;
/*  54:    */   @EJB
/*  55:    */   private ServicioMovimientoInventario servicioMovimientoInventario;
/*  56:    */   @EJB
/*  57:    */   private ServicioPeriodo servicioPeriodo;
/*  58:    */   @EJB
/*  59:    */   private ServicioProducto servicioProducto;
/*  60:    */   @EJB
/*  61:    */   private ServicioDespachoCliente servicioDespachoCliente;
/*  62:    */   @Temporal(TemporalType.DATE)
/*  63:    */   @NotNull
/*  64:    */   private Date fechaDesde;
/*  65:    */   @Temporal(TemporalType.DATE)
/*  66:    */   @NotNull
/*  67:    */   private Date fechaHasta;
/*  68:    */   private Bodega bodega;
/*  69:    */   private List<Bodega> listaBodegas;
/*  70: 94 */   private List<SaldoNegativoProducto> listaNegativoProducto = new ArrayList();
/*  71:    */   @ManagedProperty("#{listaProductoBean}")
/*  72:    */   private ListaProductoBean listaProductoBean;
/*  73:    */   private boolean indicadorGenerarAsiento;
/*  74:    */   private Producto productoSeleccionado;
/*  75:101 */   private List<ErrorCarga> errores = new ArrayList();
/*  76:    */   
/*  77:    */   public void revisarNegativos()
/*  78:    */   {
/*  79:104 */     Producto producto = getProductoSeleccionado();
/*  80:105 */     this.listaNegativoProducto = new ArrayList();
/*  81:107 */     for (Iterator localIterator1 = obtenerBodegas().iterator(); localIterator1.hasNext();)
/*  82:    */     {
/*  83:107 */       bodegaTmp = (Bodega)localIterator1.next();
/*  84:109 */       for (Producto productoTmp : obtenerProductos(bodegaTmp, producto)) {
/*  85:110 */         this.listaNegativoProducto.addAll(this.servicioCosteo.revisionSaldosNegativos(AppUtil.getOrganizacion().getIdOrganizacion(), productoTmp, this.fechaDesde, this.fechaHasta, bodegaTmp));
/*  86:    */       }
/*  87:    */     }
/*  88:    */     Bodega bodegaTmp;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void generarCostos()
/*  92:    */   {
/*  93:119 */     Periodo periodoValidatorInventario = null;
/*  94:120 */     Periodo periodoValidatorContabilidad = null;
/*  95:    */     try
/*  96:    */     {
/*  97:122 */       periodoValidatorContabilidad = this.servicioPeriodo.buscarPorFecha(this.fechaDesde, AppUtil.getOrganizacion().getId(), DocumentoBase.CONCEPTOS_CONTABLES);
/*  98:    */       
/*  99:124 */       periodoValidatorInventario = this.servicioPeriodo.buscarPorFecha(this.fechaDesde, AppUtil.getOrganizacion().getId(), DocumentoBase.AJUSTE_INVENTARIO);
/* 100:    */     }
/* 101:    */     catch (ExcepcionAS2Financiero e1)
/* 102:    */     {
/* 103:127 */       addErrorMessage(getLanguageController().getMensaje(e1.getCodigoExcepcion()) + " " + e1.getMessage());
/* 104:    */     }
/* 105:129 */     if ((periodoValidatorInventario != null) && (periodoValidatorContabilidad != null)) {
/* 106:    */       try
/* 107:    */       {
/* 108:133 */         if (!isCosteoPorBodega()) {
/* 109:134 */           this.servicioCosteo.actualizaIndicadorGeneraCostoTransferenciaIngreso(AppUtil.getOrganizacion().getIdOrganizacion(), this.fechaDesde, this.fechaHasta, this.productoSeleccionado);
/* 110:    */         }
/* 111:138 */         Producto producto = getProductoSeleccionado();
/* 112:139 */         for (Iterator localIterator1 = obtenerBodegas().iterator(); localIterator1.hasNext();)
/* 113:    */         {
/* 114:139 */           bodegaTmp = (Bodega)localIterator1.next();
/* 115:    */           
/* 116:141 */           versionCosteo = 0;
/* 117:142 */           for (Producto productoTmp : obtenerProductos(bodegaTmp, producto))
/* 118:    */           {
/* 119:143 */             versionCosteo = productoTmp.getVersionCosteo() + 1;
/* 120:144 */             this.servicioCosteo.generarCostosEstandar(AppUtil.getOrganizacion().getIdOrganizacion(), productoTmp, this.fechaDesde, this.fechaHasta);
/* 121:145 */             this.servicioCosteo.generarCostos(AppUtil.getOrganizacion().getIdOrganizacion(), productoTmp, this.fechaDesde, this.fechaHasta, bodegaTmp, 
/* 122:146 */               Integer.valueOf(versionCosteo), null);
/* 123:    */           }
/* 124:    */         }
/* 125:    */         Bodega bodegaTmp;
/* 126:    */         int versionCosteo;
/* 127:151 */         this.servicioCosteo.eliminarCostoProductoNoInventarioProducto(AppUtil.getOrganizacion().getIdOrganizacion(), this.fechaDesde, this.fechaHasta, 
/* 128:152 */           isCosteoPorBodega());
/* 129:    */         
/* 130:154 */         addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/* 131:    */       }
/* 132:    */       catch (Exception e)
/* 133:    */       {
/* 134:156 */         e.printStackTrace();
/* 135:157 */         addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 136:158 */         LOG.error("ERROR AL COSTEAR " + this.fechaDesde + "-" + this.fechaHasta, e);
/* 137:    */       }
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private List<Producto> obtenerProductos(Bodega bodegaTmp, Producto producto)
/* 142:    */   {
/* 143:165 */     List<Producto> productosRecosteo = new ArrayList();
/* 144:167 */     if (producto == null)
/* 145:    */     {
/* 146:168 */       productosRecosteo = this.servicioInventarioProducto.obtenerProductosConMovimiento(AppUtil.getOrganizacion().getIdOrganizacion(), this.fechaDesde, this.fechaHasta, bodegaTmp);
/* 147:    */     }
/* 148:    */     else
/* 149:    */     {
/* 150:171 */       producto = this.servicioProducto.buscarPorId(producto.getId());
/* 151:172 */       productosRecosteo.add(producto);
/* 152:    */     }
/* 153:174 */     return productosRecosteo;
/* 154:    */   }
/* 155:    */   
/* 156:    */   private List<Bodega> obtenerBodegas()
/* 157:    */   {
/* 158:178 */     List<Bodega> bodegasRecosteo = new ArrayList();
/* 159:179 */     if (isCosteoPorBodega())
/* 160:    */     {
/* 161:180 */       bodegasRecosteo = this.listaBodegas;
/* 162:181 */       if (this.bodega != null)
/* 163:    */       {
/* 164:182 */         bodegasRecosteo = new ArrayList();
/* 165:183 */         bodegasRecosteo.add(this.bodega);
/* 166:    */       }
/* 167:    */     }
/* 168:    */     else
/* 169:    */     {
/* 170:186 */       bodegasRecosteo = new ArrayList();
/* 171:187 */       bodegasRecosteo.add(null);
/* 172:    */     }
/* 173:189 */     return bodegasRecosteo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void contabilizarAjustes()
/* 177:    */   {
/* 178:193 */     Periodo periodoValidator = null;
/* 179:    */     try
/* 180:    */     {
/* 181:196 */       periodoValidator = this.servicioPeriodo.buscarPorFecha(this.fechaDesde, AppUtil.getOrganizacion().getId(), DocumentoBase.CONCEPTOS_CONTABLES);
/* 182:    */     }
/* 183:    */     catch (ExcepcionAS2Financiero e1)
/* 184:    */     {
/* 185:198 */       addErrorMessage(getLanguageController().getMensaje(e1.getCodigoExcepcion()) + " " + e1.getMessage());
/* 186:    */     }
/* 187:200 */     if (periodoValidator != null)
/* 188:    */     {
/* 189:201 */       this.errores = new ArrayList();
/* 190:    */       
/* 191:203 */       TipoOrganizacion tipoOrganizacion = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion();
/* 192:204 */       List<DocumentoBase> listaDocumento = new ArrayList();
/* 193:205 */       listaDocumento.add(DocumentoBase.AJUSTE_INVENTARIO);
/* 194:206 */       listaDocumento.add(DocumentoBase.TRANSFORMACION_PRODUCTO);
/* 195:207 */       listaDocumento.add(DocumentoBase.TRANSFERENCIA_BODEGA);
/* 196:208 */       listaDocumento.add(DocumentoBase.INGRESO_PRODUCCION);
/* 197:209 */       listaDocumento.add(DocumentoBase.SALIDA_MATERIAL);
/* 198:    */       
/* 199:    */ 
/* 200:212 */       listaDocumento.add(DocumentoBase.CONSUMO_BODEGA);
/* 201:    */       
/* 202:214 */       List<MovimientoInventario> lista = this.servicioMovimientoInventario.getListaAjusteInvetario(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, tipoOrganizacion, listaDocumento);
/* 203:    */       
/* 204:    */ 
/* 205:    */ 
/* 206:218 */       List<DespachoCliente> listaDespachos = this.servicioDespachoCliente.obtenerDespachos(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta);
/* 207:    */       try
/* 208:    */       {
/* 209:221 */         for (MovimientoInventario movimientoInventario : lista) {
/* 210:222 */           if (!movimientoInventario.getDocumento().getDocumentoBase().equals(DocumentoBase.CONSUMO_BODEGA))
/* 211:    */           {
/* 212:223 */             this.servicioMovimientoInventario.contabilizar(movimientoInventario);
/* 213:    */           }
/* 214:225 */           else if (movimientoInventario.getAsiento() != null)
/* 215:    */           {
/* 216:226 */             List<MovimientoInventario> listaMovimientoInventario = new ArrayList();
/* 217:227 */             listaMovimientoInventario.add(movimientoInventario);
/* 218:228 */             this.servicioMovimientoInventario.contabilizarConsumoBodega(listaMovimientoInventario);
/* 219:    */           }
/* 220:    */         }
/* 221:234 */         for (DespachoCliente despachoCliente : listaDespachos)
/* 222:    */         {
/* 223:235 */           List<DespachoCliente> despachos = new ArrayList();
/* 224:236 */           despachos.add(despachoCliente);
/* 225:237 */           this.servicioDespachoCliente.contabilizar(despachos);
/* 226:    */         }
/* 227:    */       }
/* 228:    */       catch (AS2Exception e)
/* 229:    */       {
/* 230:240 */         e.printStackTrace();
/* 231:241 */         List<String> listaMensajes = e.getCodigoMensajes();
/* 232:242 */         int i = 0;
/* 233:243 */         for (String a : listaMensajes)
/* 234:    */         {
/* 235:244 */           i = a.indexOf("*");
/* 236:245 */           a.substring(0, i + 1);
/* 237:246 */           ErrorCarga ec = new ErrorCarga();
/* 238:247 */           ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/* 239:248 */           this.errores.add(ec);
/* 240:    */         }
/* 241:250 */         for (String a : e.getMensajes())
/* 242:    */         {
/* 243:251 */           ErrorCarga ec = new ErrorCarga();
/* 244:252 */           ec.setError(a);
/* 245:253 */           this.errores.add(ec);
/* 246:    */         }
/* 247:    */       }
/* 248:    */       catch (ExcepcionAS2 e)
/* 249:    */       {
/* 250:257 */         e.printStackTrace();
/* 251:258 */         ErrorCarga ec = new ErrorCarga();
/* 252:259 */         ec.setError(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 253:260 */         this.errores.add(ec);
/* 254:    */       }
/* 255:    */       catch (Exception e)
/* 256:    */       {
/* 257:262 */         e.printStackTrace();
/* 258:263 */         ErrorCarga ec = new ErrorCarga();
/* 259:264 */         ec.setError(e.getMessage());
/* 260:265 */         this.errores.add(ec);
/* 261:    */       }
/* 262:270 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void cargarProducto(Producto producto)
/* 267:    */   {
/* 268:275 */     this.productoSeleccionado = producto;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public Date getFechaDesde()
/* 272:    */   {
/* 273:286 */     if (this.fechaDesde == null) {
/* 274:    */       try
/* 275:    */       {
/* 276:289 */         Periodo periodo = this.servicioPeriodo.obtenerPeriodoActual(AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.AJUSTE_INVENTARIO);
/* 277:290 */         this.fechaDesde = periodo.getFechaDesde();
/* 278:291 */         this.fechaHasta = periodo.getFechaHasta();
/* 279:    */       }
/* 280:    */       catch (ExcepcionAS2Financiero e)
/* 281:    */       {
/* 282:293 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 283:    */       }
/* 284:    */     }
/* 285:297 */     return this.fechaDesde;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setFechaDesde(Date fechaDesde)
/* 289:    */   {
/* 290:307 */     this.fechaDesde = fechaDesde;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public Date getFechaHasta()
/* 294:    */   {
/* 295:316 */     return this.fechaHasta;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setFechaHasta(Date fechaHasta)
/* 299:    */   {
/* 300:326 */     this.fechaHasta = fechaHasta;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean isIndicadorGenerarAsiento()
/* 304:    */   {
/* 305:335 */     return this.indicadorGenerarAsiento;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setIndicadorGenerarAsiento(boolean indicadorGenerarAsiento)
/* 309:    */   {
/* 310:345 */     this.indicadorGenerarAsiento = indicadorGenerarAsiento;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public ListaProductoBean getListaProductoBean()
/* 314:    */   {
/* 315:349 */     return this.listaProductoBean;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 319:    */   {
/* 320:353 */     this.listaProductoBean = listaProductoBean;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public Bodega getBodega()
/* 324:    */   {
/* 325:357 */     return this.bodega;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setBodega(Bodega bodega)
/* 329:    */   {
/* 330:361 */     this.bodega = bodega;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public List<Bodega> getListaBodegas()
/* 334:    */   {
/* 335:365 */     if (this.listaBodegas == null) {
/* 336:366 */       this.listaBodegas = this.servicioBodega.obtenerListaCombo("nombre", true, null);
/* 337:    */     }
/* 338:368 */     return this.listaBodegas;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setListaBodegas(List<Bodega> listaBodegas)
/* 342:    */   {
/* 343:372 */     this.listaBodegas = listaBodegas;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public Producto getProductoSeleccionado()
/* 347:    */   {
/* 348:376 */     return this.productoSeleccionado;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setProductoSeleccionado(Producto productoSeleccionado)
/* 352:    */   {
/* 353:380 */     this.productoSeleccionado = productoSeleccionado;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<ErrorCarga> getErrores()
/* 357:    */   {
/* 358:384 */     return this.errores;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setErrores(List<ErrorCarga> errores)
/* 362:    */   {
/* 363:388 */     this.errores = errores;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public List<SaldoNegativoProducto> getListaNegativoProducto()
/* 367:    */   {
/* 368:392 */     return this.listaNegativoProducto;
/* 369:    */   }
/* 370:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.CosteoBean
 * JD-Core Version:    0.7.0.1
 */