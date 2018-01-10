/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteSaldoProducto;
/*   4:    */ import com.asinfo.as2.clases.ReporteStockValoradoResumido;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioVersionListaPrecios;
/*   9:    */ import com.asinfo.as2.entities.Atributo;
/*  10:    */ import com.asinfo.as2.entities.Bodega;
/*  11:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  12:    */ import com.asinfo.as2.entities.ListaPrecios;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  15:    */ import com.asinfo.as2.entities.Sucursal;
/*  16:    */ import com.asinfo.as2.entities.Unidad;
/*  17:    */ import com.asinfo.as2.entities.VersionListaPrecios;
/*  18:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  19:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  20:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  23:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*  26:    */ import com.asinfo.as2.inventario.reportes.controller.dataSource.ReporteStockProductoDataSourcePersonalizado;
/*  27:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteStockValoradoPersonalizado;
/*  28:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  29:    */ import com.asinfo.as2.util.AppUtil;
/*  30:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  31:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  32:    */ import com.asinfo.as2.ventas.reportes.AbstractInventarioReportBean;
/*  33:    */ import java.io.IOException;
/*  34:    */ import java.math.BigDecimal;
/*  35:    */ import java.math.RoundingMode;
/*  36:    */ import java.util.ArrayList;
/*  37:    */ import java.util.Calendar;
/*  38:    */ import java.util.Date;
/*  39:    */ import java.util.HashMap;
/*  40:    */ import java.util.Iterator;
/*  41:    */ import java.util.List;
/*  42:    */ import java.util.Map;
/*  43:    */ import java.util.Map.Entry;
/*  44:    */ import java.util.Set;
/*  45:    */ import java.util.TreeMap;
/*  46:    */ import javax.annotation.PostConstruct;
/*  47:    */ import javax.ejb.EJB;
/*  48:    */ import javax.faces.bean.ManagedBean;
/*  49:    */ import javax.faces.bean.ViewScoped;
/*  50:    */ import javax.faces.model.SelectItem;
/*  51:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  52:    */ import net.sf.jasperreports.engine.JRException;
/*  53:    */ import org.apache.log4j.Logger;
/*  54:    */ 
/*  55:    */ @ManagedBean
/*  56:    */ @ViewScoped
/*  57:    */ public class ReporteStockValoradoResumidoPersonalizadoBean
/*  58:    */   extends AbstractInventarioReportBean
/*  59:    */ {
/*  60:    */   private static final long serialVersionUID = -595660703984412823L;
/*  61:    */   @EJB
/*  62:    */   private ServicioReporteStockValoradoPersonalizado servicioReporteStockValoradoPersonalizado;
/*  63:    */   @EJB
/*  64:    */   private ServicioSucursal servicioSucursal;
/*  65:    */   @EJB
/*  66:    */   private ServicioBodega servicioBodega;
/*  67:    */   @EJB
/*  68:    */   private ServicioProducto servicioProducto;
/*  69:    */   @EJB
/*  70:    */   private ServicioAtributo servicioAtributo;
/*  71:    */   @EJB
/*  72:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  73:    */   @EJB
/*  74:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  75:    */   @EJB
/*  76:    */   private ServicioUsuario servicioUsuario;
/*  77:    */   @EJB
/*  78:    */   private ServicioListaPrecios servicioListaPrecios;
/*  79:    */   @EJB
/*  80:    */   private ServicioVersionListaPrecios servicioVersionListaPrecios;
/*  81:    */   @EJB
/*  82:    */   private ServicioValorAtributo servicioValorAtributo;
/*  83:103 */   private ReporteStockProductoDataSourcePersonalizado reporteStockProductoDataSourcePersonalizado = new ReporteStockProductoDataSourcePersonalizado();
/*  84:    */   private Date fechaDesde;
/*  85:    */   private Date fechaHasta;
/*  86:    */   private Bodega bodega;
/*  87:    */   private Sucursal sucursal;
/*  88:    */   private EntidadUsuario usuario;
/*  89:    */   private List<Bodega> listaBodega;
/*  90:    */   private List<Sucursal> listaSucursal;
/*  91:116 */   private int reporteSeleccionado = 0;
/*  92:    */   private List<SelectItem> listaNombreReporte;
/*  93:120 */   private String reporteResumenMovimientoInventarios = "reporteResumenMovimientoInventarios";
/*  94:121 */   private String reporteResultadoNetoProductosVendidos = "reporteResultadoNetoProductosVendidos";
/*  95:122 */   private String reportePrecioVentaVSCostoVentaResultadoNeto = "reportePrecioVentaVSCostoVentaResultadoNeto";
/*  96:    */   private ListaPrecios listaPrecios;
/*  97:    */   private VersionListaPrecios versionListaPrecios;
/*  98:    */   boolean agrupadoPorProducto;
/*  99:    */   
/* 100:    */   private static enum enumUnidad
/* 101:    */   {
/* 102:131 */     STOCK,  VENTA,  ALMACENAMIENTO;
/* 103:    */     
/* 104:    */     private enumUnidad() {}
/* 105:    */   }
/* 106:    */   
/* 107:134 */   private enumUnidad unidad = enumUnidad.STOCK;
/* 108:    */   private List<SelectItem> listaUnidad;
/* 109:    */   private CategoriaProducto categoriaProductoSeleccionado;
/* 110:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/* 111:    */   private List<CategoriaProducto> listaCategoriaProductos;
/* 112:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/* 113:    */   
/* 114:    */   protected JRDataSource getJRDataSource()
/* 115:    */   {
/* 116:154 */     return this.reporteStockProductoDataSourcePersonalizado;
/* 117:    */   }
/* 118:    */   
/* 119:    */   @PostConstruct
/* 120:    */   public void init()
/* 121:    */   {
/* 122:160 */     Calendar calfechaDesde = Calendar.getInstance();
/* 123:161 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 124:162 */     this.fechaDesde = calfechaDesde.getTime();
/* 125:163 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected Map<String, Object> getReportParameters()
/* 129:    */   {
/* 130:174 */     asignarValorAtributo(getValorAtributoSeleccionado());
/* 131:    */     
/* 132:176 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 133:177 */     reportParameters.put("ReportTitle", "Reporte Stock Valorado Resumido");
/* 134:    */     
/* 135:179 */     reportParameters.put("p_sucursal", this.sucursal == null ? "" : this.sucursal.getNombre());
/* 136:180 */     reportParameters.put("p_bodega", this.bodega == null ? "" : this.bodega.getNombre());
/* 137:181 */     reportParameters.put("p_fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 138:182 */     reportParameters.put("p_fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 139:183 */     reportParameters.put("p_categoriaProducto", this.categoriaProductoSeleccionado != null ? this.categoriaProductoSeleccionado.getNombre() : "Todos");
/* 140:184 */     reportParameters.put("p_subCategoriaProducto", this.subcategoriaProductoSeleccionado != null ? this.subcategoriaProductoSeleccionado.getNombre() : "Todos");
/* 141:    */     
/* 142:186 */     reportParameters.put("p_atributo", getAtributo() != null ? getAtributo().getNombre() : "Todos");
/* 143:187 */     reportParameters.put("p_valorAtributo", (getValorAtributo() != null) && (!getValorAtributo().isEmpty()) ? getValorAtributo() : "Todos");
/* 144:188 */     if (this.reporteSeleccionado == 4) {
/* 145:189 */       reportParameters.put("listaPrecioVersion", getListaPrecios().getNombre() + " : " + getVersionListaPrecios().getNombre());
/* 146:    */     }
/* 147:192 */     return reportParameters;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String execute()
/* 151:    */   {
/* 152:    */     try
/* 153:    */     {
/* 154:204 */       asignarValorAtributo(getValorAtributoSeleccionado());
/* 155:205 */       Map<String, ReporteStockValoradoResumido> hmReporte = new TreeMap();
/* 156:206 */       Map<String, ReporteStockValoradoResumido> hmReporteTMP = new TreeMap();
/* 157:208 */       if (this.reporteSeleccionado == 4) {
/* 158:209 */         this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 159:    */       }
/* 160:213 */       List<ReporteStockValoradoResumido> listaReporteStockValoradoResumido = this.servicioReporteStockValoradoPersonalizado.getReporteStockValoradoResumido(null, this.bodega, this.fechaDesde, this.fechaHasta, getAtributo(), getValorAtributo(), 
/* 161:214 */         AppUtil.getOrganizacion().getId(), this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, this.agrupadoPorProducto);
/* 162:216 */       for (ReporteStockValoradoResumido reporteStock : listaReporteStockValoradoResumido)
/* 163:    */       {
/* 164:218 */         clave = "";
/* 165:219 */         if (this.agrupadoPorProducto) {
/* 166:220 */           clave = reporteStock.getNombreProducto() + ":" + reporteStock.getIdProducto();
/* 167:    */         } else {
/* 168:222 */           clave = reporteStock.getIdBodega() + ":" + reporteStock.getNombreProducto() + ":" + reporteStock.getIdProducto();
/* 169:    */         }
/* 170:224 */         ReporteStockValoradoResumido reporteStockTmp = (ReporteStockValoradoResumido)hmReporteTMP.get(clave);
/* 171:225 */         if (reporteStockTmp != null)
/* 172:    */         {
/* 173:226 */           reporteStockTmp.setAjusteEgreso(reporteStockTmp.getAjusteEgreso().add(reporteStock.getAjusteEgreso()));
/* 174:227 */           reporteStockTmp.setAjusteIngreso(reporteStockTmp.getAjusteIngreso().add(reporteStock.getAjusteIngreso()));
/* 175:228 */           reporteStockTmp.setConsumo(reporteStockTmp.getConsumo().add(reporteStock.getConsumo()));
/* 176:229 */           reporteStockTmp.setCostoPromedioAjusteEgreso(reporteStockTmp.getCostoPromedioAjusteEgreso().add(reporteStock
/* 177:230 */             .getCostoPromedioAjusteEgreso()));
/* 178:231 */           reporteStockTmp.setCostoPromedioAjusteInventario(reporteStockTmp.getCostoPromedioAjusteInventario().add(reporteStock
/* 179:232 */             .getCostoPromedioAjusteInventario()));
/* 180:233 */           reporteStockTmp.setCostoPromedioConsumo(reporteStockTmp.getCostoPromedioConsumo().add(reporteStock.getCostoPromedioConsumo()));
/* 181:234 */           reporteStockTmp.setCostoPromedioDespachos(reporteStockTmp.getCostoPromedioDespachos().add(reporteStock
/* 182:235 */             .getCostoPromedioDespachos()));
/* 183:236 */           reporteStockTmp.setCostoPromedioDevolucion(reporteStockTmp.getCostoPromedioDevolucion().add(reporteStock
/* 184:237 */             .getCostoPromedioDevolucion()));
/* 185:238 */           reporteStockTmp.setCostoPromedioDevolucionProveedor(reporteStockTmp.getCostoPromedioDevolucionProveedor().add(reporteStock
/* 186:239 */             .getCostoPromedioDevolucionProveedor()));
/* 187:240 */           reporteStockTmp.setCostoPromedioRecepcion(reporteStockTmp.getCostoPromedioRecepcion().add(reporteStock
/* 188:241 */             .getCostoPromedioRecepcion()));
/* 189:242 */           reporteStockTmp.setCostoPromedioTransferenciaEgreso(reporteStockTmp.getCostoPromedioTransferenciaEgreso().add(reporteStock
/* 190:243 */             .getCostoPromedioTransferenciaEgreso()));
/* 191:244 */           reporteStockTmp.setCostoPromedioTransferenciaIngreso(reporteStockTmp.getCostoPromedioTransferenciaIngreso().add(reporteStock
/* 192:245 */             .getCostoPromedioTransferenciaIngreso()));
/* 193:246 */           reporteStockTmp.setDespacho(reporteStockTmp.getDespacho().add(reporteStock.getDespacho()));
/* 194:247 */           reporteStockTmp.setDevolucionCliente(reporteStockTmp.getDevolucionCliente().add(reporteStock.getDevolucionCliente()));
/* 195:248 */           reporteStockTmp.setDevolucionClienteXAnulacion(reporteStockTmp.getDevolucionClienteXAnulacion().add(reporteStock
/* 196:249 */             .getDevolucionClienteXAnulacion()));
/* 197:250 */           reporteStockTmp.setDevolucionProvedor(reporteStockTmp.getDevolucionProvedor().add(reporteStock.getDevolucionProvedor()));
/* 198:251 */           reporteStockTmp.setDevolucionProveedorXAnulacion(reporteStockTmp.getDevolucionProveedorXAnulacion().add(reporteStock
/* 199:252 */             .getDevolucionProveedorXAnulacion()));
/* 200:253 */           reporteStockTmp.setNombreBodega("Consolidado");
/* 201:254 */           reporteStockTmp.setPrecioDespacho(reporteStockTmp.getPrecioDespacho().add(reporteStock.getPrecioDespacho()));
/* 202:255 */           reporteStockTmp.setRecepcion(reporteStockTmp.getRecepcion().add(reporteStock.getRecepcion()));
/* 203:256 */           reporteStockTmp.setSaldoDevolucionCliente(reporteStockTmp.getSaldoDevolucionCliente().add(reporteStock
/* 204:257 */             .getSaldoDevolucionCliente()));
/* 205:258 */           reporteStockTmp.setSaldoFinal(reporteStockTmp.getSaldoFinal().add(reporteStock.getSaldoFinal()));
/* 206:259 */           reporteStockTmp.setSaldoInicial(reporteStockTmp.getSaldoInicial().add(reporteStock.getSaldoInicial()));
/* 207:260 */           reporteStockTmp.setSaldoInicialEnPlata(reporteStockTmp.getSaldoInicialEnPlata().add(reporteStock.getSaldoInicialEnPlata()));
/* 208:261 */           reporteStockTmp.setTransferenciaEgreso(reporteStockTmp.getTransferenciaEgreso().add(reporteStock.getTransferenciaEgreso()));
/* 209:262 */           reporteStockTmp.setTransferenciaIngreso(reporteStockTmp.getTransferenciaIngreso().add(reporteStock.getTransferenciaIngreso()));
/* 210:    */         }
/* 211:    */         else
/* 212:    */         {
/* 213:265 */           hmReporteTMP.put(clave, reporteStock);
/* 214:    */         }
/* 215:    */       }
/* 216:    */       String clave;
/* 217:    */       Object fechaSaldoInicial;
/* 218:270 */       if ((this.reporteSeleccionado != 1) && (this.reporteSeleccionado != 2))
/* 219:    */       {
/* 220:273 */         fechaSaldoInicial = FuncionesUtiles.sumarFechaDiasMeses(this.fechaDesde, -1);
/* 221:274 */         List<ReporteSaldoProducto> lista = this.servicioReporteStockValoradoPersonalizado.getReporteSaldoProducto(null, this.bodega, (Date)fechaSaldoInicial, 
/* 222:275 */           getAtributo(), getValorAtributo(), AppUtil.getOrganizacion().getId(), this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado);
/* 223:278 */         for (ReporteSaldoProducto rSaldoProducto : lista)
/* 224:    */         {
/* 225:280 */           String clave = "";
/* 226:282 */           if (this.agrupadoPorProducto) {
/* 227:283 */             clave = rSaldoProducto.getNombreProducto() + ":" + rSaldoProducto.getIdProducto();
/* 228:    */           } else {
/* 229:285 */             clave = rSaldoProducto.getIdBodega() + ":" + rSaldoProducto.getNombreProducto() + ":" + rSaldoProducto.getIdProducto();
/* 230:    */           }
/* 231:290 */           BigDecimal saldoInicial = this.servicioProducto.getSaldoInicial(rSaldoProducto.getIdProducto().intValue(), new Bodega(rSaldoProducto
/* 232:291 */             .getIdBodega().intValue()), this.fechaDesde);
/* 233:292 */           if (saldoInicial.compareTo(BigDecimal.ZERO) != 0)
/* 234:    */           {
/* 235:293 */             ReporteStockValoradoResumido dr = (ReporteStockValoradoResumido)hmReporteTMP.get(clave);
/* 236:294 */             if (dr == null) {
/* 237:295 */               dr = new ReporteStockValoradoResumido(rSaldoProducto);
/* 238:    */             }
/* 239:297 */             dr.setSaldoInicial(dr.getSaldoInicial().add(saldoInicial));
/* 240:298 */             dr.setSaldoFinal(dr.getSaldoFinal().add(saldoInicial));
/* 241:299 */             dr.setCodigoUnidad(rSaldoProducto.getCodigoUnidad());
/* 242:300 */             dr.setNombreBodega(this.agrupadoPorProducto ? "Consolidado" : dr.getNombreBodega());
/* 243:    */             
/* 244:302 */             BigDecimal costo = this.servicioProducto.getCostoInicial(rSaldoProducto.getIdProducto().intValue(), this.fechaDesde, new Bodega(rSaldoProducto
/* 245:303 */               .getIdBodega().intValue()));
/* 246:    */             
/* 247:305 */             dr.setSaldoInicialEnPlata(dr.getSaldoInicialEnPlata().add(costo));
/* 248:306 */             hmReporte.put(clave, dr);
/* 249:    */           }
/* 250:    */         }
/* 251:    */       }
/* 252:    */       else
/* 253:    */       {
/* 254:312 */         for (fechaSaldoInicial = listaReporteStockValoradoResumido.iterator(); ((Iterator)fechaSaldoInicial).hasNext();)
/* 255:    */         {
/* 256:312 */           ReporteStockValoradoResumido reporteStock = (ReporteStockValoradoResumido)((Iterator)fechaSaldoInicial).next();
/* 257:    */           
/* 258:314 */           String clave = "";
/* 259:316 */           if (this.agrupadoPorProducto) {
/* 260:317 */             clave = reporteStock.getNombreProducto() + ":" + reporteStock.getIdProducto();
/* 261:    */           } else {
/* 262:319 */             clave = reporteStock.getIdBodega() + ":" + reporteStock.getNombreProducto() + ":" + reporteStock.getIdProducto();
/* 263:    */           }
/* 264:324 */           BigDecimal saldoInicial = this.servicioProducto.getSaldoInicial(reporteStock.getIdProducto(), new Bodega(reporteStock.getIdBodega()), this.fechaDesde);
/* 265:326 */           if (saldoInicial.compareTo(BigDecimal.ZERO) != 0)
/* 266:    */           {
/* 267:327 */             ReporteStockValoradoResumido dr = (ReporteStockValoradoResumido)hmReporteTMP.get(clave);
/* 268:328 */             dr.setSaldoInicial(dr.getSaldoInicial().add(saldoInicial));
/* 269:329 */             dr.setSaldoFinal(dr.getSaldoFinal().add(saldoInicial));
/* 270:330 */             dr.setCodigoUnidad(reporteStock.getCodigoUnidad());
/* 271:331 */             dr.setNombreBodega(this.agrupadoPorProducto ? "Consolidado" : dr.getNombreBodega());
/* 272:332 */             BigDecimal costo = this.servicioProducto.getCostoInicial(reporteStock.getIdProducto(), this.fechaDesde, new Bodega(reporteStock
/* 273:333 */               .getIdBodega()));
/* 274:    */             
/* 275:335 */             dr.setSaldoInicialEnPlata(dr.getSaldoInicialEnPlata().add(costo));
/* 276:336 */             hmReporte.put(clave, dr);
/* 277:    */           }
/* 278:    */         }
/* 279:339 */         if (this.reporteSeleccionado == 1)
/* 280:    */         {
/* 281:340 */           Iterator it = hmReporte.entrySet().iterator();
/* 282:341 */           while (it.hasNext())
/* 283:    */           {
/* 284:342 */             Map.Entry e = (Map.Entry)it.next();
/* 285:343 */             ReporteStockValoradoResumido rsvr = (ReporteStockValoradoResumido)e.getValue();
/* 286:344 */             if (rsvr.getDespacho().subtract(rsvr.getDevolucionCliente()).compareTo(BigDecimal.ZERO) == 0) {
/* 287:345 */               it.remove();
/* 288:    */             }
/* 289:    */           }
/* 290:    */         }
/* 291:350 */         if (this.reporteSeleccionado == 4)
/* 292:    */         {
/* 293:351 */           Iterator it = hmReporte.entrySet().iterator();
/* 294:352 */           while (it.hasNext())
/* 295:    */           {
/* 296:353 */             Map.Entry e = (Map.Entry)it.next();
/* 297:354 */             ReporteStockValoradoResumido rsvr = (ReporteStockValoradoResumido)e.getValue();
/* 298:    */             
/* 299:356 */             BigDecimal saldo = this.servicioProducto.getSaldo(rsvr.getIdProducto(), rsvr.getIdBodega(), this.fechaHasta);
/* 300:357 */             if (saldo.compareTo(BigDecimal.ZERO) != 0)
/* 301:    */             {
/* 302:360 */               BigDecimal precio = this.servicioListaPrecios.precioProducto(this.versionListaPrecios, rsvr.getNombreProducto());
/* 303:361 */               if (precio.compareTo(BigDecimal.ZERO) != 0) {
/* 304:362 */                 rsvr.setPrecioDespacho(precio);
/* 305:    */               } else {
/* 306:364 */                 rsvr.setPrecioDespacho(BigDecimal.ZERO);
/* 307:    */               }
/* 308:368 */               BigDecimal saldoTotal = this.servicioProducto.getSaldo(rsvr.getIdProducto(), this.bodega == null ? 0 : this.bodega.getIdBodega(), this.fechaHasta);
/* 309:    */               
/* 310:370 */               BigDecimal costoTotal = BigDecimal.ZERO;
/* 311:372 */               if (saldoTotal.compareTo(BigDecimal.ZERO) > 0)
/* 312:    */               {
/* 313:374 */                 if (ParametrosSistema.isCosteoPorBodega(AppUtil.getOrganizacion().getId()).booleanValue()) {
/* 314:375 */                   costoTotal = this.servicioProducto.getCosto(rsvr.getIdProducto(), this.fechaHasta, this.bodega);
/* 315:    */                 } else {
/* 316:377 */                   costoTotal = this.servicioProducto.getCosto(rsvr.getIdProducto(), this.fechaHasta, null);
/* 317:    */                 }
/* 318:379 */                 costoTotal = costoTotal == null ? BigDecimal.ZERO : costoTotal;
/* 319:380 */                 rsvr.setSaldoFinal(saldoTotal);
/* 320:381 */                 rsvr.setCostoPromedioDespachos(costoTotal.divide(saldoTotal, 4, RoundingMode.HALF_UP));
/* 321:    */               }
/* 322:    */             }
/* 323:    */             else
/* 324:    */             {
/* 325:384 */               it.remove();
/* 326:    */             }
/* 327:    */           }
/* 328:    */         }
/* 329:    */       }
/* 330:391 */       if (this.unidad != enumUnidad.STOCK) {
/* 331:392 */         convertirSaldos(hmReporte);
/* 332:    */       }
/* 333:395 */       this.reporteStockProductoDataSourcePersonalizado.setListaReporteStockValoradoResumido(new ArrayList(hmReporte
/* 334:396 */         .values()));
/* 335:397 */       super.prepareReport();
/* 336:    */     }
/* 337:    */     catch (JRException e)
/* 338:    */     {
/* 339:400 */       LOG.info("Error JRException");
/* 340:401 */       e.printStackTrace();
/* 341:402 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 342:    */     }
/* 343:    */     catch (IOException e)
/* 344:    */     {
/* 345:404 */       LOG.info("Error IOException");
/* 346:405 */       e.printStackTrace();
/* 347:406 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 348:    */     }
/* 349:409 */     return "";
/* 350:    */   }
/* 351:    */   
/* 352:    */   private void convertirSaldos(Map<String, ReporteStockValoradoResumido> hmReporte)
/* 353:    */   {
/* 354:415 */     for (ReporteStockValoradoResumido r : hmReporte.values()) {
/* 355:    */       try
/* 356:    */       {
/* 357:419 */         Unidad unidadStock = new Unidad(r.getIdUnidad(), r.getCodigoUnidad(), r.getNombreUnidad());
/* 358:420 */         Unidad unidadConversion = null;
/* 359:422 */         if (this.unidad == enumUnidad.VENTA) {
/* 360:424 */           unidadConversion = r.getIdUnidadVenta() == null ? unidadStock : new Unidad(r.getIdUnidadVenta().intValue(), r.getCodigoUnidadVenta(), r.getNombreUnidadVenta());
/* 361:426 */         } else if (this.unidad == enumUnidad.ALMACENAMIENTO) {
/* 362:428 */           unidadConversion = r.getIdUnidadAlmacenamiento() == null ? unidadStock : new Unidad(r.getIdUnidadAlmacenamiento().intValue(), r.getCodigoUnidadAlmacenamiento(), r.getNombreUnidadAlmacenamiento());
/* 363:    */         }
/* 364:431 */         BigDecimal saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getSaldoInicial());
/* 365:432 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 366:433 */         r.setSaldoInicial(saldoUnidad);
/* 367:    */         
/* 368:435 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getRecepcion());
/* 369:436 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 370:437 */         r.setRecepcion(saldoUnidad);
/* 371:    */         
/* 372:439 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getAjusteIngreso());
/* 373:440 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 374:441 */         r.setAjusteIngreso(saldoUnidad);
/* 375:    */         
/* 376:443 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getTransferenciaIngreso());
/* 377:444 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 378:445 */         r.setTransferenciaIngreso(saldoUnidad);
/* 379:    */         
/* 380:447 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getDevolucionCliente());
/* 381:448 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 382:449 */         r.setDevolucionCliente(saldoUnidad);
/* 383:    */         
/* 384:451 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getAjusteEgreso());
/* 385:452 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 386:453 */         r.setAjusteEgreso(saldoUnidad);
/* 387:    */         
/* 388:455 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getTransferenciaEgreso());
/* 389:456 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 390:457 */         r.setTransferenciaEgreso(saldoUnidad);
/* 391:    */         
/* 392:459 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getConsumo());
/* 393:460 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 394:461 */         r.setConsumo(saldoUnidad);
/* 395:    */         
/* 396:463 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getDespacho());
/* 397:464 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 398:465 */         r.setDespacho(saldoUnidad);
/* 399:    */         
/* 400:467 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getDevolucionProvedor());
/* 401:468 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 402:469 */         r.setDevolucionProvedor(saldoUnidad);
/* 403:    */         
/* 404:471 */         r.setCodigoUnidad(unidadConversion.getCodigo());
/* 405:    */       }
/* 406:    */       catch (ExcepcionAS2 e)
/* 407:    */       {
/* 408:474 */         e.printStackTrace();
/* 409:    */       }
/* 410:    */     }
/* 411:    */   }
/* 412:    */   
/* 413:    */   protected String getCompileFileName()
/* 414:    */   {
/* 415:486 */     if (this.reporteSeleccionado == 4) {
/* 416:487 */       return this.reportePrecioVentaVSCostoVentaResultadoNeto;
/* 417:    */     }
/* 418:488 */     if (this.reporteSeleccionado == 3) {
/* 419:489 */       return "reporteStockValoradoResumidoPersonalizado";
/* 420:    */     }
/* 421:490 */     if (this.reporteSeleccionado == 2) {
/* 422:491 */       return "reporteResumenMovimientoInventarios";
/* 423:    */     }
/* 424:493 */     return "reporteResultadoNetoProductosVendidos";
/* 425:    */   }
/* 426:    */   
/* 427:    */   public void cargarListaSubcategoriaProducto()
/* 428:    */   {
/* 429:498 */     HashMap<String, String> filters = new HashMap();
/* 430:499 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 431:500 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 432:    */   }
/* 433:    */   
/* 434:    */   public List<Sucursal> getListaSucursal()
/* 435:    */   {
/* 436:509 */     if (this.listaSucursal == null) {
/* 437:510 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 438:    */     }
/* 439:512 */     return this.listaSucursal;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public Sucursal getSucursal()
/* 443:    */   {
/* 444:521 */     if (this.sucursal == null) {
/* 445:522 */       this.sucursal = new Sucursal();
/* 446:    */     }
/* 447:524 */     return this.sucursal;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setSucursal(Sucursal sucursal)
/* 451:    */   {
/* 452:534 */     this.sucursal = sucursal;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public Date getFechaDesde()
/* 456:    */   {
/* 457:543 */     return this.fechaDesde;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public void setFechaDesde(Date fechaDesde)
/* 461:    */   {
/* 462:553 */     this.fechaDesde = fechaDesde;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public Date getFechaHasta()
/* 466:    */   {
/* 467:562 */     return this.fechaHasta;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void setFechaHasta(Date fechaHasta)
/* 471:    */   {
/* 472:572 */     this.fechaHasta = fechaHasta;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public Bodega getBodega()
/* 476:    */   {
/* 477:581 */     return this.bodega;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setBodega(Bodega bodega)
/* 481:    */   {
/* 482:591 */     this.bodega = bodega;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public List<Bodega> getListaBodega()
/* 486:    */   {
/* 487:600 */     if (this.listaBodega == null) {
/* 488:601 */       this.listaBodega = this.servicioBodega.obtenerBodegaCombo("nombre", true, null);
/* 489:    */     }
/* 490:603 */     return this.listaBodega;
/* 491:    */   }
/* 492:    */   
/* 493:    */   public ReporteStockProductoDataSourcePersonalizado getReporteStockProductoDataSource()
/* 494:    */   {
/* 495:612 */     return this.reporteStockProductoDataSourcePersonalizado;
/* 496:    */   }
/* 497:    */   
/* 498:    */   public enumUnidad getUnidad()
/* 499:    */   {
/* 500:621 */     return this.unidad;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public void setUnidad(enumUnidad unidad)
/* 504:    */   {
/* 505:631 */     this.unidad = unidad;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public List<SelectItem> getListaUnidad()
/* 509:    */   {
/* 510:640 */     if (this.listaUnidad == null)
/* 511:    */     {
/* 512:641 */       this.listaUnidad = new ArrayList();
/* 513:642 */       for (enumUnidad unidad : enumUnidad.values())
/* 514:    */       {
/* 515:643 */         SelectItem item = new SelectItem(unidad, unidad.name());
/* 516:644 */         this.listaUnidad.add(item);
/* 517:    */       }
/* 518:    */     }
/* 519:647 */     return this.listaUnidad;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public void setListaUnidad(List<SelectItem> listaUnidad)
/* 523:    */   {
/* 524:657 */     this.listaUnidad = listaUnidad;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 528:    */   {
/* 529:664 */     return this.categoriaProductoSeleccionado;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 533:    */   {
/* 534:672 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 538:    */   {
/* 539:680 */     HashMap<String, String> filters = new HashMap();
/* 540:681 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 541:682 */     if (this.listaCategoriaProductos == null) {
/* 542:683 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 543:    */     }
/* 544:685 */     return this.listaCategoriaProductos;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 548:    */   {
/* 549:693 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 553:    */   {
/* 554:700 */     return this.subcategoriaProductoSeleccionado;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 558:    */   {
/* 559:708 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 563:    */   {
/* 564:715 */     return this.listaSubcategoriaProductos;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 568:    */   {
/* 569:723 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public int getReporteSeleccionado()
/* 573:    */   {
/* 574:727 */     return this.reporteSeleccionado;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void setReporteSeleccionado(int reporteSeleccionado)
/* 578:    */   {
/* 579:731 */     this.reporteSeleccionado = reporteSeleccionado;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public List<SelectItem> getListaNombreReporte()
/* 583:    */   {
/* 584:735 */     if (this.listaNombreReporte == null)
/* 585:    */     {
/* 586:736 */       this.listaNombreReporte = new ArrayList();
/* 587:737 */       this.listaNombreReporte.add(new SelectItem(Integer.valueOf(1), "Resultado Neto Productos Vendidos"));
/* 588:738 */       this.listaNombreReporte.add(new SelectItem(Integer.valueOf(2), "Resumen Movimiento Inventarios"));
/* 589:739 */       this.listaNombreReporte.add(new SelectItem(Integer.valueOf(3), "Stock Valorado Resumido"));
/* 590:740 */       this.listaNombreReporte.add(new SelectItem(Integer.valueOf(4), "Precio de Venta VS Costo de Venta - Resultado Neto"));
/* 591:    */     }
/* 592:742 */     return this.listaNombreReporte;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public String getReporteResumenMovimientoInventarios()
/* 596:    */   {
/* 597:746 */     return this.reporteResumenMovimientoInventarios;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public void setReporteResumenMovimientoInventarios(String reporteResumenMovimientoInventarios)
/* 601:    */   {
/* 602:750 */     this.reporteResumenMovimientoInventarios = reporteResumenMovimientoInventarios;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public String getReporteResultadoNetoProductosVendidos()
/* 606:    */   {
/* 607:754 */     return this.reporteResultadoNetoProductosVendidos;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public void setReporteResultadoNetoProductosVendidos(String reporteResultadoNetoProductosVendidos)
/* 611:    */   {
/* 612:758 */     this.reporteResultadoNetoProductosVendidos = reporteResultadoNetoProductosVendidos;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public EntidadUsuario getUsuario()
/* 616:    */   {
/* 617:762 */     return this.usuario;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public void setUsuario(EntidadUsuario usuario)
/* 621:    */   {
/* 622:766 */     this.usuario = usuario;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public String getReportePrecioVentaVSCostoVentaResultadoNeto()
/* 626:    */   {
/* 627:770 */     return this.reportePrecioVentaVSCostoVentaResultadoNeto;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public void setReportePrecioVentaVSCostoVentaResultadoNeto(String reportePrecioVentaVSCostoVentaResultadoNeto)
/* 631:    */   {
/* 632:774 */     this.reportePrecioVentaVSCostoVentaResultadoNeto = reportePrecioVentaVSCostoVentaResultadoNeto;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public List<ListaPrecios> autocompletarListaPrecios(String consulta)
/* 636:    */   {
/* 637:783 */     return this.servicioListaPrecios.autocompletarListaPrecios(consulta);
/* 638:    */   }
/* 639:    */   
/* 640:    */   public ListaPrecios getListaPrecios()
/* 641:    */   {
/* 642:787 */     return this.listaPrecios;
/* 643:    */   }
/* 644:    */   
/* 645:    */   public void setListaPrecios(ListaPrecios listaPrecios)
/* 646:    */   {
/* 647:791 */     this.listaPrecios = listaPrecios;
/* 648:    */   }
/* 649:    */   
/* 650:    */   public VersionListaPrecios getVersionListaPrecios()
/* 651:    */   {
/* 652:795 */     return this.versionListaPrecios;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public void setVersionListaPrecios(VersionListaPrecios versionListaPrecios)
/* 656:    */   {
/* 657:799 */     this.versionListaPrecios = versionListaPrecios;
/* 658:    */   }
/* 659:    */   
/* 660:    */   public List<VersionListaPrecios> autocompletarListaVersionListaPrecios(String consulta)
/* 661:    */   {
/* 662:808 */     return this.servicioVersionListaPrecios.autocompletarListaVersionListaPrecios(consulta, getListaPrecios());
/* 663:    */   }
/* 664:    */   
/* 665:    */   public boolean isAgrupadoPorProducto()
/* 666:    */   {
/* 667:812 */     return this.agrupadoPorProducto;
/* 668:    */   }
/* 669:    */   
/* 670:    */   public void setAgrupadoPorProducto(boolean agrupadoPorProducto)
/* 671:    */   {
/* 672:816 */     this.agrupadoPorProducto = agrupadoPorProducto;
/* 673:    */   }
/* 674:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteStockValoradoResumidoPersonalizadoBean
 * JD-Core Version:    0.7.0.1
 */