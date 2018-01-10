/*   1:    */ package com.asinfo.as2.produccion.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.dao.DespachoClienteDao;
/*   5:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   6:    */ import com.asinfo.as2.dao.MovimientoInventarioDao;
/*   7:    */ import com.asinfo.as2.dao.PedidoClienteDao;
/*   8:    */ import com.asinfo.as2.dao.produccion.DetallePlanProduccionDao;
/*   9:    */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*  10:    */ import com.asinfo.as2.dao.produccion.PlanProduccionDao;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  12:    */ import com.asinfo.as2.entities.Bodega;
/*  13:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*  14:    */ import com.asinfo.as2.entities.Documento;
/*  15:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*  16:    */ import com.asinfo.as2.entities.Producto;
/*  17:    */ import com.asinfo.as2.entities.produccion.DetallePlanMaestroProduccion;
/*  18:    */ import com.asinfo.as2.entities.produccion.DetallePlanProduccion;
/*  19:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  20:    */ import com.asinfo.as2.entities.produccion.PlanProduccion;
/*  21:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  22:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  23:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  24:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  25:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  28:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  29:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  30:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*  31:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioPlanMaestroProduccion;
/*  32:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioPlanProduccion;
/*  33:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  34:    */ import com.asinfo.as2.utils.NodoArbol;
/*  35:    */ import java.lang.reflect.Method;
/*  36:    */ import java.math.BigDecimal;
/*  37:    */ import java.math.RoundingMode;
/*  38:    */ import java.text.SimpleDateFormat;
/*  39:    */ import java.util.ArrayList;
/*  40:    */ import java.util.Calendar;
/*  41:    */ import java.util.Date;
/*  42:    */ import java.util.HashMap;
/*  43:    */ import java.util.Iterator;
/*  44:    */ import java.util.List;
/*  45:    */ import java.util.Map;
/*  46:    */ import java.util.Set;
/*  47:    */ import javax.ejb.EJB;
/*  48:    */ import javax.ejb.Stateless;
/*  49:    */ 
/*  50:    */ @Stateless
/*  51:    */ public class ServicioPlanProduccionImpl
/*  52:    */   implements ServicioPlanProduccion
/*  53:    */ {
/*  54:    */   @EJB
/*  55:    */   private PlanProduccionDao planProduccionDao;
/*  56:    */   @EJB
/*  57:    */   private DetallePlanProduccionDao detallePlanProduccionDao;
/*  58:    */   @EJB
/*  59:    */   private ServicioPlanMaestroProduccion servicioPlanMaestroProduccion;
/*  60:    */   @EJB
/*  61:    */   private ServicioProducto servicioProducto;
/*  62:    */   @EJB
/*  63:    */   private DespachoClienteDao despachoClienteDao;
/*  64:    */   @EJB
/*  65:    */   private FacturaClienteDao facturaClienteDao;
/*  66:    */   @EJB
/*  67:    */   private PedidoClienteDao pedidoClienteDao;
/*  68:    */   @EJB
/*  69:    */   private OrdenFabricacionDao ordenfabricacionDao;
/*  70:    */   @EJB
/*  71:    */   private ServicioSucursal servicioSucursal;
/*  72:    */   @EJB
/*  73:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  74:    */   @EJB
/*  75:    */   private ServicioDocumento servicioDocumento;
/*  76:    */   @EJB
/*  77:    */   private ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  78:    */   @EJB
/*  79:    */   private MovimientoInventarioDao movimientoInventarioDao;
/*  80:100 */   HashMap<Integer, BigDecimal> despachado = new HashMap();
/*  81:101 */   HashMap<Integer, BigDecimal> devoluciones = new HashMap();
/*  82:    */   
/*  83:    */   public void guardar(PlanProduccion planProduccion)
/*  84:    */     throws AS2Exception
/*  85:    */   {
/*  86:105 */     validar(planProduccion);
/*  87:106 */     this.planProduccionDao.guardar(planProduccion);
/*  88:107 */     for (DetallePlanProduccion detalle : planProduccion.getListaDetallePlanProduccion())
/*  89:    */     {
/*  90:109 */       detalle.setTotalProduccionSemana(detalle.getTotalProduccionSemana().setScale(0, RoundingMode.HALF_UP));
/*  91:110 */       this.detallePlanProduccionDao.guardar(detalle);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void validar(PlanProduccion planProduccion)
/*  96:    */     throws AS2Exception
/*  97:    */   {
/*  98:    */     SimpleDateFormat sdf;
/*  99:115 */     if (planProduccion.getFechaInicio().after(planProduccion.getFechaFin()))
/* 100:    */     {
/* 101:116 */       sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 102:    */       
/* 103:118 */       throw new AS2Exception("msg_error_intervalo_fechas", new String[] { sdf.format(planProduccion.getFechaInicio()), sdf.format(planProduccion.getFechaFin()) });
/* 104:    */     }
/* 105:120 */     for (DetallePlanProduccion detalle : planProduccion.getListaDetallePlanProduccion()) {
/* 106:121 */       if (!detalle.isEliminado())
/* 107:    */       {
/* 108:122 */         if (detalle.getUnidadStock() == null) {
/* 109:123 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_UNIDAD_STOCK_NULL_PRODUCTO", new String[] { detalle.getProducto().getNombre() });
/* 110:    */         }
/* 111:125 */         if (detalle.getRutaFabricacion() == null) {
/* 112:126 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_RUTA_FABRICACION_NULL_PRODUCTO", new String[] { detalle.getProducto().getNombre() });
/* 113:    */         }
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void eliminar(PlanProduccion planProduccion)
/* 119:    */   {
/* 120:134 */     planProduccion = cargarDetalle(planProduccion.getId());
/* 121:135 */     for (DetallePlanProduccion detalle : planProduccion.getListaDetallePlanProduccion()) {
/* 122:136 */       this.detallePlanProduccionDao.eliminar(detalle);
/* 123:    */     }
/* 124:138 */     this.planProduccionDao.eliminar(planProduccion);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public PlanProduccion buscarPorId(int idPlanProduccion)
/* 128:    */   {
/* 129:144 */     return (PlanProduccion)this.planProduccionDao.buscarPorId(Integer.valueOf(idPlanProduccion));
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<PlanProduccion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 133:    */   {
/* 134:150 */     return this.planProduccionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<PlanProduccion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 138:    */   {
/* 139:155 */     return this.planProduccionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int contarPorCriterio(Map<String, String> filters)
/* 143:    */   {
/* 144:160 */     return this.planProduccionDao.contarPorCriterio(filters);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public PlanProduccion cargarDetalle(int idPlanProduccion)
/* 148:    */   {
/* 149:165 */     return this.planProduccionDao.cargarDetalle(idPlanProduccion);
/* 150:    */   }
/* 151:    */   
/* 152:    */   private void calcularCantidadRequeridaProduccionSemana(DetallePlanProduccion detallePlanProduccion, DetallePlanMaestroProduccion detallePMP)
/* 153:    */   {
/* 154:171 */     int diasSemana = 7;
/* 155:172 */     if (detallePlanProduccion.getVentaDomingo().compareTo(BigDecimal.ZERO) == 0) {
/* 156:173 */       diasSemana = 6;
/* 157:    */     }
/* 158:176 */     BigDecimal stockMinimo = detallePlanProduccion.getTotalVentasSemana().divide(new BigDecimal(diasSemana), 6, RoundingMode.HALF_UP).multiply(detallePMP.getCantidadDiasStock()).setScale(2, RoundingMode.HALF_UP);
/* 159:177 */     detallePlanProduccion.setStockMinimo(stockMinimo);
/* 160:    */     
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:182 */     BigDecimal cantidadRequeridaProduccionSemana = detallePlanProduccion.getTotalVentasSemana().add(stockMinimo).subtract(detallePlanProduccion.getSaldoInicial());
/* 165:183 */     if (cantidadRequeridaProduccionSemana.compareTo(BigDecimal.ZERO) < 0) {
/* 166:184 */       cantidadRequeridaProduccionSemana = BigDecimal.ZERO;
/* 167:    */     }
/* 168:186 */     detallePlanProduccion.setCantidadRequeridaProduccionSemana(cantidadRequeridaProduccionSemana);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void actualizarPlanMaestroProduccion(PlanProduccion planProduccion)
/* 172:    */     throws AS2Exception
/* 173:    */   {
/* 174:    */     Date lunes;
/* 175:    */     Date martes;
/* 176:    */     Date miercoles;
/* 177:    */     Date jueves;
/* 178:    */     Date viernes;
/* 179:    */     Date sabado;
/* 180:    */     Date domingo;
/* 181:    */     Date hoy;
/* 182:    */     Map<Integer, DetallePlanMaestroProduccion> mapa;
/* 183:191 */     if (planProduccion.getPlanMaestroProduccion() != null)
/* 184:    */     {
/* 185:192 */       lunes = planProduccion.getFechaInicio();
/* 186:193 */       martes = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), 1);
/* 187:194 */       miercoles = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), 2);
/* 188:195 */       jueves = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), 3);
/* 189:196 */       viernes = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), 4);
/* 190:197 */       sabado = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), 5);
/* 191:198 */       domingo = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), 6);
/* 192:199 */       hoy = new Date();
/* 193:200 */       hoy = FuncionesUtiles.getHoraCero(hoy);
/* 194:    */       
/* 195:202 */       Calendar fechaCalendar = Calendar.getInstance();
/* 196:203 */       fechaCalendar.setTime(planProduccion.getFechaInicio());
/* 197:204 */       int numeroMes = fechaCalendar.get(2);
/* 198:205 */       Mes mes = Mes.values()[numeroMes];
/* 199:    */       
/* 200:207 */       mapa = this.servicioPlanMaestroProduccion.obtenerDetallesPlanMaestroPorFecha(planProduccion.getPlanMaestroProduccion(), mes);
/* 201:209 */       for (DetallePlanProduccion detallePlanProduccion : planProduccion.getListaDetallePlanProduccion()) {
/* 202:210 */         if (!detallePlanProduccion.isEliminado())
/* 203:    */         {
/* 204:211 */           DetallePlanMaestroProduccion detallePMP = (DetallePlanMaestroProduccion)mapa.get(Integer.valueOf(detallePlanProduccion.getProducto().getId()));
/* 205:212 */           if (detallePMP != null)
/* 206:    */           {
/* 207:213 */             if (detallePlanProduccion.getRutaFabricacion() == null) {
/* 208:214 */               detallePlanProduccion.setRutaFabricacion(detallePMP.getRutaFabricacion());
/* 209:    */             }
/* 210:217 */             calcularCantidadRequeridaProduccionSemana(detallePlanProduccion, detallePMP);
/* 211:219 */             if (lunes.after(hoy))
/* 212:    */             {
/* 213:220 */               detallePlanProduccion.setBatchLunes(detallePlanProduccion.getCantidadRequeridaProduccionSemana()
/* 214:221 */                 .multiply(detallePMP.getProporcionLunes()).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP)
/* 215:222 */                 .divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.HALF_UP).intValue());
/* 216:223 */               detallePlanProduccion.setIndicadorPlanMaestroLunes(detallePMP.getProporcionLunes().compareTo(BigDecimal.ZERO) > 0);
/* 217:    */             }
/* 218:225 */             if (martes.after(hoy))
/* 219:    */             {
/* 220:226 */               detallePlanProduccion.setBatchMartes(detallePlanProduccion.getCantidadRequeridaProduccionSemana()
/* 221:227 */                 .multiply(detallePMP.getProporcionMartes()).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP)
/* 222:228 */                 .divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.HALF_UP).intValue());
/* 223:229 */               detallePlanProduccion.setIndicadorPlanMaestroMartes(detallePMP.getProporcionMartes().compareTo(BigDecimal.ZERO) > 0);
/* 224:    */             }
/* 225:231 */             if (miercoles.after(hoy))
/* 226:    */             {
/* 227:232 */               detallePlanProduccion.setBatchMiercoles(detallePlanProduccion.getCantidadRequeridaProduccionSemana()
/* 228:233 */                 .multiply(detallePMP.getProporcionMiercoles()).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP)
/* 229:234 */                 .divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.HALF_UP).intValue());
/* 230:235 */               detallePlanProduccion
/* 231:236 */                 .setIndicadorPlanMaestroMiercoles(detallePMP.getProporcionMiercoles().compareTo(BigDecimal.ZERO) > 0);
/* 232:    */             }
/* 233:238 */             if (jueves.after(hoy))
/* 234:    */             {
/* 235:239 */               detallePlanProduccion.setBatchJueves(detallePlanProduccion.getCantidadRequeridaProduccionSemana()
/* 236:240 */                 .multiply(detallePMP.getProporcionJueves()).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP)
/* 237:241 */                 .divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.HALF_UP).intValue());
/* 238:242 */               detallePlanProduccion.setIndicadorPlanMaestroJueves(detallePMP.getProporcionJueves().compareTo(BigDecimal.ZERO) > 0);
/* 239:    */             }
/* 240:244 */             if (viernes.after(hoy))
/* 241:    */             {
/* 242:245 */               detallePlanProduccion.setBatchViernes(detallePlanProduccion.getCantidadRequeridaProduccionSemana()
/* 243:246 */                 .multiply(detallePMP.getProporcionViernes()).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP)
/* 244:247 */                 .divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.HALF_UP).intValue());
/* 245:248 */               detallePlanProduccion.setIndicadorPlanMaestroViernes(detallePMP.getProporcionViernes().compareTo(BigDecimal.ZERO) > 0);
/* 246:    */             }
/* 247:250 */             if (sabado.after(hoy))
/* 248:    */             {
/* 249:251 */               detallePlanProduccion.setBatchSabado(detallePlanProduccion.getCantidadRequeridaProduccionSemana()
/* 250:252 */                 .multiply(detallePMP.getProporcionSabado()).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP)
/* 251:253 */                 .divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.HALF_UP).intValue());
/* 252:254 */               detallePlanProduccion.setIndicadorPlanMaestroSabado(detallePMP.getProporcionSabado().compareTo(BigDecimal.ZERO) > 0);
/* 253:    */             }
/* 254:256 */             if (domingo.after(hoy))
/* 255:    */             {
/* 256:257 */               detallePlanProduccion.setBatchDomingo(detallePlanProduccion.getCantidadRequeridaProduccionSemana()
/* 257:258 */                 .multiply(detallePMP.getProporcionDomingo()).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP)
/* 258:259 */                 .divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.HALF_UP).intValue());
/* 259:260 */               detallePlanProduccion.setIndicadorPlanMaestroDomingo(detallePMP.getProporcionDomingo().compareTo(BigDecimal.ZERO) > 0);
/* 260:    */             }
/* 261:    */           }
/* 262:264 */           redistribuirBatchStockMinimo(detallePlanProduccion);
/* 263:    */         }
/* 264:    */       }
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void redistribuirBatchStockMinimo(DetallePlanProduccion detallePlanProduccion)
/* 269:    */     throws AS2Exception
/* 270:    */   {
/* 271:272 */     calcularSaldosSemana(detallePlanProduccion);
/* 272:273 */     Date lunes = detallePlanProduccion.getPlanProduccion().getFechaInicio();
/* 273:274 */     Date martes = FuncionesUtiles.sumarFechaDiasMeses(detallePlanProduccion.getPlanProduccion().getFechaInicio(), 1);
/* 274:275 */     Date miercoles = FuncionesUtiles.sumarFechaDiasMeses(detallePlanProduccion.getPlanProduccion().getFechaInicio(), 2);
/* 275:276 */     Date jueves = FuncionesUtiles.sumarFechaDiasMeses(detallePlanProduccion.getPlanProduccion().getFechaInicio(), 3);
/* 276:277 */     Date viernes = FuncionesUtiles.sumarFechaDiasMeses(detallePlanProduccion.getPlanProduccion().getFechaInicio(), 4);
/* 277:278 */     Date sabado = FuncionesUtiles.sumarFechaDiasMeses(detallePlanProduccion.getPlanProduccion().getFechaInicio(), 5);
/* 278:279 */     Date domingo = FuncionesUtiles.sumarFechaDiasMeses(detallePlanProduccion.getPlanProduccion().getFechaInicio(), 6);
/* 279:    */     
/* 280:    */ 
/* 281:282 */     BigDecimal cantidadBatchPorRestar = BigDecimal.ZERO;
/* 282:283 */     Date hoy = new Date();
/* 283:284 */     hoy = FuncionesUtiles.getHoraCero(hoy);
/* 284:285 */     if ((lunes.after(hoy)) && 
/* 285:286 */       (detallePlanProduccion.getSaldoMartes().compareTo(detallePlanProduccion.getStockMinimo()) < 0))
/* 286:    */     {
/* 287:287 */       BigDecimal diferencia = detallePlanProduccion.getStockMinimo().subtract(detallePlanProduccion.getSaldoMartes());
/* 288:288 */       BigDecimal batchRequeridos = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.UP);
/* 289:289 */       detallePlanProduccion.setBatchLunes(detallePlanProduccion.getBatchLunes() + batchRequeridos.intValue());
/* 290:290 */       cantidadBatchPorRestar = batchRequeridos;
/* 291:291 */       calcularSaldosSemana(detallePlanProduccion);
/* 292:    */     }
/* 293:294 */     if (martes.after(hoy))
/* 294:    */     {
/* 295:295 */       if (detallePlanProduccion.getSaldoMiercoles().compareTo(detallePlanProduccion.getStockMinimo()) < 0)
/* 296:    */       {
/* 297:296 */         BigDecimal diferencia = detallePlanProduccion.getStockMinimo().subtract(detallePlanProduccion.getSaldoMiercoles());
/* 298:297 */         BigDecimal batchRequeridos = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.UP);
/* 299:298 */         detallePlanProduccion.setBatchMartes(detallePlanProduccion.getBatchMartes() + batchRequeridos.intValue());
/* 300:299 */         cantidadBatchPorRestar = cantidadBatchPorRestar.add(batchRequeridos);
/* 301:    */       }
/* 302:300 */       else if ((cantidadBatchPorRestar.compareTo(BigDecimal.ZERO) > 0) && (detallePlanProduccion.getBatchMartes() > 0))
/* 303:    */       {
/* 304:301 */         BigDecimal diferencia = detallePlanProduccion.getSaldoMiercoles().subtract(detallePlanProduccion.getStockMinimo());
/* 305:302 */         BigDecimal batchSobrantes = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.DOWN);
/* 306:303 */         if (batchSobrantes.compareTo(BigDecimal.ZERO) > 0) {
/* 307:304 */           if (batchSobrantes.compareTo(cantidadBatchPorRestar) >= 0)
/* 308:    */           {
/* 309:305 */             detallePlanProduccion.setBatchMartes(detallePlanProduccion.getBatchMartes() - cantidadBatchPorRestar.intValue());
/* 310:306 */             cantidadBatchPorRestar = BigDecimal.ZERO;
/* 311:    */           }
/* 312:    */           else
/* 313:    */           {
/* 314:308 */             detallePlanProduccion.setBatchMartes(detallePlanProduccion.getBatchMartes() - batchSobrantes.intValue());
/* 315:309 */             cantidadBatchPorRestar = cantidadBatchPorRestar.subtract(batchSobrantes);
/* 316:    */           }
/* 317:    */         }
/* 318:    */       }
/* 319:313 */       calcularSaldosSemana(detallePlanProduccion);
/* 320:    */     }
/* 321:315 */     if (miercoles.after(hoy))
/* 322:    */     {
/* 323:316 */       if (detallePlanProduccion.getSaldoJueves().compareTo(detallePlanProduccion.getStockMinimo()) < 0)
/* 324:    */       {
/* 325:317 */         BigDecimal diferencia = detallePlanProduccion.getStockMinimo().subtract(detallePlanProduccion.getSaldoJueves());
/* 326:318 */         BigDecimal batchRequeridos = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.UP);
/* 327:319 */         detallePlanProduccion.setBatchMiercoles(detallePlanProduccion.getBatchMiercoles() + batchRequeridos.intValue());
/* 328:320 */         cantidadBatchPorRestar = cantidadBatchPorRestar.add(batchRequeridos);
/* 329:    */       }
/* 330:321 */       else if ((cantidadBatchPorRestar.compareTo(BigDecimal.ZERO) > 0) && (detallePlanProduccion.getBatchMiercoles() > 0))
/* 331:    */       {
/* 332:322 */         BigDecimal diferencia = detallePlanProduccion.getSaldoJueves().subtract(detallePlanProduccion.getStockMinimo());
/* 333:323 */         BigDecimal batchSobrantes = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.DOWN);
/* 334:324 */         if (batchSobrantes.compareTo(BigDecimal.ZERO) > 0) {
/* 335:325 */           if (batchSobrantes.compareTo(cantidadBatchPorRestar) >= 0)
/* 336:    */           {
/* 337:326 */             detallePlanProduccion.setBatchMiercoles(detallePlanProduccion.getBatchMiercoles() - cantidadBatchPorRestar.intValue());
/* 338:327 */             cantidadBatchPorRestar = BigDecimal.ZERO;
/* 339:    */           }
/* 340:    */           else
/* 341:    */           {
/* 342:329 */             detallePlanProduccion.setBatchMiercoles(detallePlanProduccion.getBatchMiercoles() - batchSobrantes.intValue());
/* 343:330 */             cantidadBatchPorRestar = cantidadBatchPorRestar.subtract(batchSobrantes);
/* 344:    */           }
/* 345:    */         }
/* 346:    */       }
/* 347:334 */       calcularSaldosSemana(detallePlanProduccion);
/* 348:    */     }
/* 349:336 */     if (jueves.after(hoy))
/* 350:    */     {
/* 351:337 */       if (detallePlanProduccion.getSaldoViernes().compareTo(detallePlanProduccion.getStockMinimo()) < 0)
/* 352:    */       {
/* 353:338 */         BigDecimal diferencia = detallePlanProduccion.getStockMinimo().subtract(detallePlanProduccion.getSaldoViernes());
/* 354:339 */         BigDecimal batchRequeridos = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.UP);
/* 355:340 */         detallePlanProduccion.setBatchJueves(detallePlanProduccion.getBatchJueves() + batchRequeridos.intValue());
/* 356:341 */         cantidadBatchPorRestar = cantidadBatchPorRestar.add(batchRequeridos);
/* 357:    */       }
/* 358:342 */       else if ((cantidadBatchPorRestar.compareTo(BigDecimal.ZERO) > 0) && (detallePlanProduccion.getBatchJueves() > 0))
/* 359:    */       {
/* 360:343 */         BigDecimal diferencia = detallePlanProduccion.getSaldoViernes().subtract(detallePlanProduccion.getStockMinimo());
/* 361:344 */         BigDecimal batchSobrantes = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.DOWN);
/* 362:345 */         if (batchSobrantes.compareTo(BigDecimal.ZERO) > 0) {
/* 363:346 */           if (batchSobrantes.compareTo(cantidadBatchPorRestar) >= 0)
/* 364:    */           {
/* 365:347 */             detallePlanProduccion.setBatchJueves(detallePlanProduccion.getBatchJueves() - cantidadBatchPorRestar.intValue());
/* 366:348 */             cantidadBatchPorRestar = BigDecimal.ZERO;
/* 367:    */           }
/* 368:    */           else
/* 369:    */           {
/* 370:350 */             detallePlanProduccion.setBatchJueves(detallePlanProduccion.getBatchJueves() - batchSobrantes.intValue());
/* 371:351 */             cantidadBatchPorRestar = cantidadBatchPorRestar.subtract(batchSobrantes);
/* 372:    */           }
/* 373:    */         }
/* 374:    */       }
/* 375:355 */       calcularSaldosSemana(detallePlanProduccion);
/* 376:    */     }
/* 377:357 */     if (viernes.after(hoy))
/* 378:    */     {
/* 379:358 */       if (detallePlanProduccion.getSaldoSabado().compareTo(detallePlanProduccion.getStockMinimo()) < 0)
/* 380:    */       {
/* 381:359 */         BigDecimal diferencia = detallePlanProduccion.getStockMinimo().subtract(detallePlanProduccion.getSaldoSabado());
/* 382:360 */         BigDecimal batchRequeridos = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.UP);
/* 383:361 */         detallePlanProduccion.setBatchViernes(detallePlanProduccion.getBatchViernes() + batchRequeridos.intValue());
/* 384:362 */         cantidadBatchPorRestar = cantidadBatchPorRestar.add(batchRequeridos);
/* 385:    */       }
/* 386:363 */       else if ((cantidadBatchPorRestar.compareTo(BigDecimal.ZERO) > 0) && (detallePlanProduccion.getBatchViernes() > 0))
/* 387:    */       {
/* 388:364 */         BigDecimal diferencia = detallePlanProduccion.getSaldoSabado().subtract(detallePlanProduccion.getStockMinimo());
/* 389:365 */         BigDecimal batchSobrantes = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.DOWN);
/* 390:366 */         if (batchSobrantes.compareTo(BigDecimal.ZERO) > 0) {
/* 391:367 */           if (batchSobrantes.compareTo(cantidadBatchPorRestar) >= 0)
/* 392:    */           {
/* 393:368 */             detallePlanProduccion.setBatchViernes(detallePlanProduccion.getBatchViernes() - cantidadBatchPorRestar.intValue());
/* 394:369 */             cantidadBatchPorRestar = BigDecimal.ZERO;
/* 395:    */           }
/* 396:    */           else
/* 397:    */           {
/* 398:371 */             detallePlanProduccion.setBatchViernes(detallePlanProduccion.getBatchViernes() - batchSobrantes.intValue());
/* 399:372 */             cantidadBatchPorRestar = cantidadBatchPorRestar.subtract(batchSobrantes);
/* 400:    */           }
/* 401:    */         }
/* 402:    */       }
/* 403:376 */       calcularSaldosSemana(detallePlanProduccion);
/* 404:    */     }
/* 405:378 */     if (sabado.after(hoy))
/* 406:    */     {
/* 407:379 */       if (detallePlanProduccion.getSaldoDomingo().compareTo(detallePlanProduccion.getStockMinimo()) < 0)
/* 408:    */       {
/* 409:380 */         BigDecimal diferencia = detallePlanProduccion.getStockMinimo().subtract(detallePlanProduccion.getSaldoDomingo());
/* 410:381 */         BigDecimal batchRequeridos = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.UP);
/* 411:382 */         detallePlanProduccion.setBatchSabado(detallePlanProduccion.getBatchSabado() + batchRequeridos.intValue());
/* 412:383 */         cantidadBatchPorRestar = cantidadBatchPorRestar.add(batchRequeridos);
/* 413:    */       }
/* 414:384 */       else if ((cantidadBatchPorRestar.compareTo(BigDecimal.ZERO) > 0) && (detallePlanProduccion.getBatchSabado() > 0))
/* 415:    */       {
/* 416:385 */         BigDecimal diferencia = detallePlanProduccion.getSaldoDomingo().subtract(detallePlanProduccion.getStockMinimo());
/* 417:386 */         BigDecimal batchSobrantes = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.DOWN);
/* 418:387 */         if (batchSobrantes.compareTo(BigDecimal.ZERO) > 0) {
/* 419:388 */           if (batchSobrantes.compareTo(cantidadBatchPorRestar) >= 0)
/* 420:    */           {
/* 421:389 */             detallePlanProduccion.setBatchSabado(detallePlanProduccion.getBatchSabado() - cantidadBatchPorRestar.intValue());
/* 422:390 */             cantidadBatchPorRestar = BigDecimal.ZERO;
/* 423:    */           }
/* 424:    */           else
/* 425:    */           {
/* 426:392 */             detallePlanProduccion.setBatchSabado(detallePlanProduccion.getBatchSabado() - batchSobrantes.intValue());
/* 427:393 */             cantidadBatchPorRestar = cantidadBatchPorRestar.subtract(batchSobrantes);
/* 428:    */           }
/* 429:    */         }
/* 430:    */       }
/* 431:397 */       calcularSaldosSemana(detallePlanProduccion);
/* 432:    */     }
/* 433:399 */     if (domingo.after(hoy))
/* 434:    */     {
/* 435:400 */       if (detallePlanProduccion.getSaldoDomingo().compareTo(detallePlanProduccion.getStockMinimo()) < 0)
/* 436:    */       {
/* 437:401 */         BigDecimal diferencia = detallePlanProduccion.getStockMinimo().subtract(detallePlanProduccion.getSaldoDomingo());
/* 438:402 */         BigDecimal batchRequeridos = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.UP);
/* 439:403 */         detallePlanProduccion.setBatchDomingo(detallePlanProduccion.getBatchDomingo() + batchRequeridos.intValue());
/* 440:404 */         cantidadBatchPorRestar = cantidadBatchPorRestar.add(batchRequeridos);
/* 441:    */       }
/* 442:405 */       else if ((cantidadBatchPorRestar.compareTo(BigDecimal.ZERO) > 0) && (detallePlanProduccion.getBatchDomingo() > 0))
/* 443:    */       {
/* 444:406 */         BigDecimal diferencia = detallePlanProduccion.getSaldoDomingo().subtract(detallePlanProduccion.getStockMinimo());
/* 445:407 */         BigDecimal batchSobrantes = diferencia.divide(detallePlanProduccion.getProducto().getCantidadProduccion(), 0, RoundingMode.DOWN);
/* 446:408 */         if (batchSobrantes.compareTo(BigDecimal.ZERO) > 0) {
/* 447:409 */           if (batchSobrantes.compareTo(cantidadBatchPorRestar) >= 0)
/* 448:    */           {
/* 449:410 */             detallePlanProduccion.setBatchDomingo(detallePlanProduccion.getBatchDomingo() - cantidadBatchPorRestar.intValue());
/* 450:411 */             cantidadBatchPorRestar = BigDecimal.ZERO;
/* 451:    */           }
/* 452:    */           else
/* 453:    */           {
/* 454:413 */             detallePlanProduccion.setBatchDomingo(detallePlanProduccion.getBatchDomingo() - batchSobrantes.intValue());
/* 455:414 */             cantidadBatchPorRestar = cantidadBatchPorRestar.subtract(batchSobrantes);
/* 456:    */           }
/* 457:    */         }
/* 458:    */       }
/* 459:418 */       calcularSaldosSemana(detallePlanProduccion);
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   public void cargarSugerenciasVentas(PlanProduccion planProduccion, int idSucursal, List<Bodega> listaBodegaTrabajo)
/* 464:    */     throws AS2Exception
/* 465:    */   {
/* 466:430 */     List<Producto> listaProducto = this.planProduccionDao.obtenerProductosPlanificacion(planProduccion.getIdOrganizacion(), listaBodegaTrabajo, idSucursal);
/* 467:431 */     for (Producto producto : listaProducto) {
/* 468:432 */       crearDetallePlanProduccion(planProduccion, producto);
/* 469:    */     }
/* 470:435 */     Date lunesAnterior = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), -7);
/* 471:436 */     Date martesAnterior = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), -6);
/* 472:437 */     Date miercolesAnterior = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), -5);
/* 473:438 */     Date juevesAnterior = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), -4);
/* 474:439 */     Date viernesAnterior = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), -3);
/* 475:440 */     Date sabadoAnterior = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), -2);
/* 476:441 */     Date domingoAnterior = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getFechaInicio(), -1);
/* 477:    */     
/* 478:443 */     HashMap<Integer, BigDecimal> ventasLunes = obtenerVentasPorfecha(lunesAnterior, listaProducto, "Lunes", listaBodegaTrabajo);
/* 479:444 */     HashMap<Integer, BigDecimal> ventasMartes = obtenerVentasPorfecha(martesAnterior, listaProducto, "Martes", listaBodegaTrabajo);
/* 480:445 */     HashMap<Integer, BigDecimal> ventasMiercoles = obtenerVentasPorfecha(miercolesAnterior, listaProducto, "Miercoles", listaBodegaTrabajo);
/* 481:446 */     HashMap<Integer, BigDecimal> ventasJueves = obtenerVentasPorfecha(juevesAnterior, listaProducto, "Jueves", listaBodegaTrabajo);
/* 482:447 */     HashMap<Integer, BigDecimal> ventasViernes = obtenerVentasPorfecha(viernesAnterior, listaProducto, "Viernes", listaBodegaTrabajo);
/* 483:448 */     HashMap<Integer, BigDecimal> ventasSabado = obtenerVentasPorfecha(sabadoAnterior, listaProducto, "Sabado", listaBodegaTrabajo);
/* 484:449 */     HashMap<Integer, BigDecimal> ventasDomingo = obtenerVentasPorfecha(domingoAnterior, listaProducto, "Domingo", listaBodegaTrabajo);
/* 485:451 */     for (DetallePlanProduccion detalle : planProduccion.getListaDetallePlanProduccion())
/* 486:    */     {
/* 487:452 */       detalle.setVentaLunesAnterior((BigDecimal)ventasLunes.get(Integer.valueOf(detalle.getProducto().getIdProducto())));
/* 488:453 */       detalle.setVentaMartesAnterior((BigDecimal)ventasMartes.get(Integer.valueOf(detalle.getProducto().getIdProducto())));
/* 489:454 */       detalle.setVentaMiercolesAnterior((BigDecimal)ventasMiercoles.get(Integer.valueOf(detalle.getProducto().getIdProducto())));
/* 490:455 */       detalle.setVentaJuevesAnterior((BigDecimal)ventasJueves.get(Integer.valueOf(detalle.getProducto().getIdProducto())));
/* 491:456 */       detalle.setVentaViernesAnterior((BigDecimal)ventasViernes.get(Integer.valueOf(detalle.getProducto().getIdProducto())));
/* 492:457 */       detalle.setVentaSabadoAnterior((BigDecimal)ventasSabado.get(Integer.valueOf(detalle.getProducto().getIdProducto())));
/* 493:458 */       detalle.setVentaDomingoAnterior((BigDecimal)ventasDomingo.get(Integer.valueOf(detalle.getProducto().getIdProducto())));
/* 494:    */     }
/* 495:462 */     Object hmDespahos = new HashMap();
/* 496:463 */     HashMap<Integer, BigDecimal> hmDevoluciones = new HashMap();
/* 497:464 */     HashMap<Integer, BigDecimal> hmTransferencias = new HashMap();
/* 498:466 */     for (DetallePlanProduccion detalle : planProduccion.getListaDetallePlanProduccion())
/* 499:    */     {
/* 500:467 */       Date fechaLunes = lunesAnterior;
/* 501:468 */       Date fechaDomingo = domingoAnterior;
/* 502:    */       
/* 503:470 */       BigDecimal valorSemana = detalle.getTotalVentasSemanaPrevia();
/* 504:471 */       BigDecimal sumaFactorCrecimiento = BigDecimal.ZERO;
/* 505:473 */       for (int i = 0; i < 4; i++)
/* 506:    */       {
/* 507:474 */         fechaLunes = FuncionesUtiles.sumarFechaDiasMeses(fechaLunes, -7);
/* 508:475 */         fechaDomingo = FuncionesUtiles.sumarFechaDiasMeses(fechaDomingo, -7);
/* 509:    */         
/* 510:477 */         hmDespahos = this.despachoClienteDao.obtenerTotalDespachadoPorProductoPorFecha(detalle.getProducto(), fechaLunes, fechaDomingo, listaBodegaTrabajo);
/* 511:478 */         hmDevoluciones = this.facturaClienteDao.obtenerTotalDevolucionesPorProductoPorFecha(detalle.getProducto(), fechaLunes, fechaDomingo, listaBodegaTrabajo);
/* 512:479 */         hmTransferencias = this.movimientoInventarioDao.obtenerTotalTransferenciasPorProductoPorFecha(detalle.getProducto(), fechaLunes, fechaDomingo, listaBodegaTrabajo);
/* 513:    */         
/* 514:481 */         BigDecimal valorDespachado = (BigDecimal)((HashMap)hmDespahos).get(Integer.valueOf(detalle.getProducto().getIdProducto()));
/* 515:482 */         BigDecimal valorDevoluciones = (BigDecimal)hmDevoluciones.get(Integer.valueOf(detalle.getProducto().getIdProducto()));
/* 516:483 */         BigDecimal valorTransferencias = (BigDecimal)hmTransferencias.get(Integer.valueOf(detalle.getProducto().getIdProducto()));
/* 517:485 */         if (valorDespachado == null) {
/* 518:486 */           valorDespachado = BigDecimal.ZERO;
/* 519:    */         }
/* 520:488 */         if (valorDevoluciones == null) {
/* 521:489 */           valorDevoluciones = BigDecimal.ZERO;
/* 522:    */         }
/* 523:492 */         if (valorTransferencias == null) {
/* 524:493 */           valorTransferencias = BigDecimal.ZERO;
/* 525:    */         }
/* 526:496 */         BigDecimal valorSemanaPasada = valorDespachado.add(valorTransferencias).subtract(valorDevoluciones).setScale(2, RoundingMode.HALF_UP);
/* 527:498 */         if (valorSemana != null)
/* 528:    */         {
/* 529:    */           BigDecimal formula;
/* 530:    */           BigDecimal formula;
/* 531:500 */           if ((valorSemanaPasada.compareTo(BigDecimal.ZERO) == 0) || (valorSemana.compareTo(BigDecimal.ZERO) == 0)) {
/* 532:501 */             formula = BigDecimal.ONE;
/* 533:    */           } else {
/* 534:503 */             formula = valorSemana.divide(valorSemanaPasada.abs(), 4, RoundingMode.HALF_UP);
/* 535:    */           }
/* 536:505 */           sumaFactorCrecimiento = sumaFactorCrecimiento.add(formula);
/* 537:    */         }
/* 538:507 */         valorSemana = valorSemanaPasada;
/* 539:    */       }
/* 540:509 */       BigDecimal factorCrecimiento = sumaFactorCrecimiento.divide(new BigDecimal(4), 4, RoundingMode.HALF_UP);
/* 541:510 */       detalle.setFactorCrecimiento(factorCrecimiento);
/* 542:    */     }
/* 543:514 */     for (??? = planProduccion.getListaDetallePlanProduccion().iterator(); ???.hasNext();)
/* 544:    */     {
/* 545:514 */       detallePlanProduccion = (DetallePlanProduccion)???.next();
/* 546:515 */       proyectarVentas(detallePlanProduccion);
/* 547:    */     }
/* 548:    */     DetallePlanProduccion detallePlanProduccion;
/* 549:518 */     Object saldosIniciales = calcularSaldoInicial(listaProducto, planProduccion.getFechaInicio(), listaBodegaTrabajo);
/* 550:520 */     for (DetallePlanProduccion detallePlanProduccion : planProduccion.getListaDetallePlanProduccion())
/* 551:    */     {
/* 552:521 */       BigDecimal saldoInicial = (BigDecimal)((HashMap)saldosIniciales).get(Integer.valueOf(detallePlanProduccion.getProducto().getIdProducto()));
/* 553:522 */       detallePlanProduccion.setSaldoInicial(saldoInicial.setScale(2, RoundingMode.HALF_UP));
/* 554:    */     }
/* 555:526 */     for (DetallePlanProduccion detallePlanProduccion : planProduccion.getListaDetallePlanProduccion()) {
/* 556:527 */       calcularSaldosSemana(detallePlanProduccion);
/* 557:    */     }
/* 558:531 */     actualizarPlanMaestroProduccion(planProduccion);
/* 559:    */   }
/* 560:    */   
/* 561:    */   public void calcularSaldosSemana(DetallePlanProduccion detallePlanProduccion)
/* 562:    */     throws AS2Exception
/* 563:    */   {
/* 564:536 */     if (detallePlanProduccion.getProducto().getCantidadProduccion().compareTo(BigDecimal.ZERO) <= 0) {
/* 565:538 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_CANTIDAD_BOM", new String[] { detallePlanProduccion.getProducto().getCodigo(), detallePlanProduccion.getProducto().getNombre() });
/* 566:    */     }
/* 567:540 */     BigDecimal bom = detallePlanProduccion.getProducto().getCantidadProduccion();
/* 568:541 */     detallePlanProduccion.setSaldoLunes(detallePlanProduccion
/* 569:542 */       .getSaldoInicial().subtract(detallePlanProduccion.getVentaLunes()).setScale(2, RoundingMode.HALF_UP));
/* 570:    */     
/* 571:544 */     detallePlanProduccion.setSaldoMartes(detallePlanProduccion.getSaldoLunes().subtract(detallePlanProduccion.getVentaMartes())
/* 572:545 */       .add(new BigDecimal(detallePlanProduccion.getBatchLunes()).multiply(bom)).setScale(2, RoundingMode.HALF_UP));
/* 573:    */     
/* 574:547 */     detallePlanProduccion.setSaldoMiercoles(detallePlanProduccion.getSaldoMartes().subtract(detallePlanProduccion.getVentaMiercoles())
/* 575:548 */       .add(new BigDecimal(detallePlanProduccion.getBatchMartes()).multiply(bom)).setScale(2, RoundingMode.HALF_UP));
/* 576:    */     
/* 577:550 */     detallePlanProduccion.setSaldoJueves(detallePlanProduccion.getSaldoMiercoles().subtract(detallePlanProduccion.getVentaJueves())
/* 578:551 */       .add(new BigDecimal(detallePlanProduccion.getBatchMiercoles()).multiply(bom)).setScale(2, RoundingMode.HALF_UP));
/* 579:    */     
/* 580:553 */     detallePlanProduccion.setSaldoViernes(detallePlanProduccion.getSaldoJueves().subtract(detallePlanProduccion.getVentaViernes())
/* 581:554 */       .add(new BigDecimal(detallePlanProduccion.getBatchJueves()).multiply(bom)).setScale(2, RoundingMode.HALF_UP));
/* 582:    */     
/* 583:556 */     detallePlanProduccion.setSaldoSabado(detallePlanProduccion.getSaldoViernes().subtract(detallePlanProduccion.getVentaSabado())
/* 584:557 */       .add(new BigDecimal(detallePlanProduccion.getBatchViernes()).multiply(bom)).setScale(2, RoundingMode.HALF_UP));
/* 585:    */     
/* 586:559 */     detallePlanProduccion.setSaldoDomingo(detallePlanProduccion.getSaldoSabado().subtract(detallePlanProduccion.getVentaDomingo())
/* 587:560 */       .add(new BigDecimal(detallePlanProduccion.getBatchSabado()).multiply(bom))
/* 588:561 */       .add(new BigDecimal(detallePlanProduccion.getBatchDomingo()).multiply(bom)).setScale(2, RoundingMode.HALF_UP));
/* 589:    */   }
/* 590:    */   
/* 591:    */   private HashMap<Integer, BigDecimal> calcularSaldoInicial(List<Producto> listaProductos, Date fechaInicio, List<Bodega> listaBodegaTrabajo)
/* 592:    */   {
/* 593:567 */     HashMap<Integer, BigDecimal> mpPlanificadoBash = new HashMap();
/* 594:568 */     HashMap<Integer, BigDecimal> hmapPlanificadoVentas = new HashMap();
/* 595:569 */     HashMap<Integer, BigDecimal> hmapSaldoIniciales = new HashMap();
/* 596:570 */     HashMap<String, String> hmapDiaSemana = new HashMap();
/* 597:571 */     HashMap<Integer, BigDecimal> hmapsaldosInicial = new HashMap();
/* 598:    */     
/* 599:573 */     Date hoy = new Date();
/* 600:574 */     hoy = FuncionesUtiles.getHoraCero(hoy);
/* 601:    */     Date fechaInicial;
/* 602:    */     Date fechaInicial;
/* 603:576 */     if (hoy.before(fechaInicio)) {
/* 604:577 */       fechaInicial = hoy;
/* 605:    */     } else {
/* 606:579 */       fechaInicial = fechaInicio;
/* 607:    */     }
/* 608:582 */     Date fechaDiaAntes = FuncionesUtiles.sumarFechaDiasMeses(fechaInicial, -1);
/* 609:    */     
/* 610:584 */     BigDecimal saldo = BigDecimal.ZERO;
/* 611:585 */     BigDecimal saldoBase = BigDecimal.ZERO;
/* 612:586 */     for (Iterator localIterator1 = listaProductos.iterator(); localIterator1.hasNext();)
/* 613:    */     {
/* 614:586 */       producto = (Producto)localIterator1.next();
/* 615:587 */       if (listaBodegaTrabajo.size() > 0)
/* 616:    */       {
/* 617:588 */         for (Bodega bodega : listaBodegaTrabajo)
/* 618:    */         {
/* 619:589 */           saldoBase = this.servicioProducto.getSaldo(producto.getId(), bodega.getIdBodega(), fechaDiaAntes);
/* 620:590 */           saldo = saldo.add(saldoBase);
/* 621:    */         }
/* 622:    */       }
/* 623:    */       else
/* 624:    */       {
/* 625:593 */         saldoBase = this.servicioProducto.getSaldo(producto.getId(), 0, fechaDiaAntes);
/* 626:594 */         saldo = saldoBase;
/* 627:    */       }
/* 628:596 */       hmapSaldoIniciales.put(Integer.valueOf(producto.getIdProducto()), saldo);
/* 629:    */     }
/* 630:    */     Producto producto;
/* 631:600 */     BigDecimal valorSumarSaldo = BigDecimal.ZERO;
/* 632:601 */     for (Producto producto : listaProductos)
/* 633:    */     {
/* 634:602 */       while (fechaInicial.before(fechaInicio))
/* 635:    */       {
/* 636:603 */         String diaSemana = obtenerDiaSemana(fechaInicial);
/* 637:605 */         if (!hmapDiaSemana.containsKey(diaSemana))
/* 638:    */         {
/* 639:606 */           mpPlanificadoBash = this.planProduccionDao.obtenerTotalPlanificadoPorProductoPorFecha(null, fechaInicial, diaSemana, false);
/* 640:607 */           hmapPlanificadoVentas = this.planProduccionDao.obtenerTotalPlanificadoPorProductoPorFecha(null, fechaInicial, diaSemana, true);
/* 641:608 */           hmapDiaSemana.put(diaSemana, diaSemana);
/* 642:    */         }
/* 643:611 */         BigDecimal valorPlanificadoProduccion = (BigDecimal)mpPlanificadoBash.get(Integer.valueOf(producto.getIdProducto()));
/* 644:612 */         BigDecimal valorProyeccionVentas = (BigDecimal)hmapPlanificadoVentas.get(Integer.valueOf(producto.getIdProducto()));
/* 645:614 */         if (valorPlanificadoProduccion == null) {
/* 646:615 */           valorPlanificadoProduccion = BigDecimal.ZERO;
/* 647:    */         }
/* 648:617 */         if (valorProyeccionVentas == null) {
/* 649:618 */           valorProyeccionVentas = BigDecimal.ZERO;
/* 650:    */         }
/* 651:620 */         valorSumarSaldo = valorSumarSaldo.add(valorPlanificadoProduccion).subtract(valorProyeccionVentas).setScale(2, RoundingMode.HALF_UP);
/* 652:    */         
/* 653:    */ 
/* 654:623 */         fechaInicial = FuncionesUtiles.sumarFechaDiasMeses(fechaInicial, 1);
/* 655:    */       }
/* 656:625 */       BigDecimal saldoInicial = (BigDecimal)hmapSaldoIniciales.get(Integer.valueOf(producto.getIdProducto()));
/* 657:626 */       saldoInicial = saldoInicial.add(valorSumarSaldo);
/* 658:627 */       hmapsaldosInicial.put(Integer.valueOf(producto.getIdProducto()), saldoInicial);
/* 659:    */     }
/* 660:630 */     return hmapsaldosInicial;
/* 661:    */   }
/* 662:    */   
/* 663:    */   private String obtenerDiaSemana(Date fecha)
/* 664:    */   {
/* 665:634 */     Calendar fechaC = Calendar.getInstance();
/* 666:635 */     fechaC.setTime(fecha);
/* 667:636 */     String diaSemana = "";
/* 668:637 */     switch (fechaC.get(7))
/* 669:    */     {
/* 670:    */     case 2: 
/* 671:639 */       diaSemana = "Lunes";
/* 672:640 */       break;
/* 673:    */     case 3: 
/* 674:642 */       diaSemana = "Martes";
/* 675:643 */       break;
/* 676:    */     case 4: 
/* 677:645 */       diaSemana = "Miercoles";
/* 678:646 */       break;
/* 679:    */     case 5: 
/* 680:648 */       diaSemana = "Jueves";
/* 681:649 */       break;
/* 682:    */     case 6: 
/* 683:651 */       diaSemana = "Viernes";
/* 684:652 */       break;
/* 685:    */     case 7: 
/* 686:654 */       diaSemana = "Sabado";
/* 687:655 */       break;
/* 688:    */     case 1: 
/* 689:657 */       diaSemana = "Domingo";
/* 690:658 */       break;
/* 691:    */     }
/* 692:662 */     return diaSemana;
/* 693:    */   }
/* 694:    */   
/* 695:    */   public void proyectarVentas(DetallePlanProduccion detallePlanProduccion)
/* 696:    */     throws AS2Exception
/* 697:    */   {
/* 698:667 */     if (detallePlanProduccion.getProducto().getCantidadProduccion().compareTo(BigDecimal.ZERO) <= 0) {
/* 699:669 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_CANTIDAD_BOM", new String[] { detallePlanProduccion.getProducto().getCodigo(), detallePlanProduccion.getProducto().getNombre() });
/* 700:    */     }
/* 701:671 */     detallePlanProduccion.setVentaLunes(detallePlanProduccion.getVentaLunesAnterior().multiply(detallePlanProduccion.getFactorCrecimiento())
/* 702:672 */       .multiply(detallePlanProduccion.getFactorCambio()).setScale(2, RoundingMode.HALF_UP));
/* 703:673 */     detallePlanProduccion.setVentaMartes(detallePlanProduccion.getVentaMartesAnterior().multiply(detallePlanProduccion.getFactorCrecimiento())
/* 704:674 */       .multiply(detallePlanProduccion.getFactorCambio()).setScale(2, RoundingMode.HALF_UP));
/* 705:675 */     detallePlanProduccion
/* 706:676 */       .setVentaMiercoles(detallePlanProduccion.getVentaMiercolesAnterior().multiply(detallePlanProduccion.getFactorCrecimiento())
/* 707:677 */       .multiply(detallePlanProduccion.getFactorCambio()).setScale(2, RoundingMode.HALF_UP));
/* 708:678 */     detallePlanProduccion.setVentaJueves(detallePlanProduccion.getVentaJuevesAnterior().multiply(detallePlanProduccion.getFactorCrecimiento())
/* 709:679 */       .multiply(detallePlanProduccion.getFactorCambio()).setScale(2, RoundingMode.HALF_UP));
/* 710:680 */     detallePlanProduccion.setVentaViernes(detallePlanProduccion.getVentaViernesAnterior().multiply(detallePlanProduccion.getFactorCrecimiento())
/* 711:681 */       .multiply(detallePlanProduccion.getFactorCambio()).setScale(2, RoundingMode.HALF_UP));
/* 712:682 */     detallePlanProduccion.setVentaSabado(detallePlanProduccion.getVentaSabadoAnterior().multiply(detallePlanProduccion.getFactorCrecimiento())
/* 713:683 */       .multiply(detallePlanProduccion.getFactorCambio()).setScale(2, RoundingMode.HALF_UP));
/* 714:684 */     detallePlanProduccion.setVentaDomingo(detallePlanProduccion.getVentaDomingoAnterior().multiply(detallePlanProduccion.getFactorCrecimiento())
/* 715:685 */       .multiply(detallePlanProduccion.getFactorCambio()).setScale(2, RoundingMode.HALF_UP));
/* 716:    */   }
/* 717:    */   
/* 718:    */   private void crearDetallePlanProduccion(PlanProduccion planProduccion, Producto producto)
/* 719:    */   {
/* 720:690 */     boolean encontre = false;
/* 721:691 */     for (DetallePlanProduccion detalle : planProduccion.getListaDetallePlanProduccion()) {
/* 722:692 */       if (detalle.getProducto().getId() == producto.getId())
/* 723:    */       {
/* 724:693 */         encontre = true;
/* 725:694 */         if (!detalle.isEliminado()) {
/* 726:    */           break;
/* 727:    */         }
/* 728:695 */         detalle.setEliminado(false); break;
/* 729:    */       }
/* 730:    */     }
/* 731:701 */     if (!encontre)
/* 732:    */     {
/* 733:702 */       DetallePlanProduccion detalle = new DetallePlanProduccion();
/* 734:703 */       detalle.setIdOrganizacion(planProduccion.getIdOrganizacion());
/* 735:704 */       detalle.setIdSucursal(planProduccion.getIdSucursal());
/* 736:705 */       detalle.setPlanProduccion(planProduccion);
/* 737:706 */       detalle.setProducto(producto);
/* 738:707 */       detalle.setUnidadStock(producto.getUnidadAlmacenamiento());
/* 739:708 */       detalle.getProducto().setArbolComponentes(this.servicioProducto.obtenerArbolComponentes(detalle.getProducto()));
/* 740:709 */       planProduccion.getListaDetallePlanProduccion().add(detalle);
/* 741:    */     }
/* 742:    */   }
/* 743:    */   
/* 744:    */   private HashMap<Integer, BigDecimal> obtenerVentasPorfecha(Date fecha, List<Producto> listaProducto, String diaSemana, List<Bodega> listaBodegaTrabajo)
/* 745:    */   {
/* 746:717 */     HashMap<Integer, BigDecimal> ventasPorfecha = new HashMap();
/* 747:718 */     HashMap<Integer, BigDecimal> despachado = this.despachoClienteDao.obtenerTotalDespachadoPorProductoPorFecha(null, fecha, fecha, listaBodegaTrabajo);
/* 748:719 */     HashMap<Integer, BigDecimal> devolucion = this.facturaClienteDao.obtenerTotalDevolucionesPorProductoPorFecha(null, fecha, fecha, listaBodegaTrabajo);
/* 749:720 */     HashMap<Integer, BigDecimal> transferencias = this.movimientoInventarioDao.obtenerTotalTransferenciasPorProductoPorFecha(null, fecha, fecha, listaBodegaTrabajo);
/* 750:721 */     HashMap<Integer, BigDecimal> proyeccionVentas = this.planProduccionDao.obtenerTotalPlanificadoPorProductoPorFecha(null, fecha, diaSemana, true);
/* 751:    */     
/* 752:723 */     BigDecimal valor = BigDecimal.ZERO;
/* 753:724 */     Date hoy = new Date();
/* 754:725 */     hoy = FuncionesUtiles.getHoraCero(hoy);
/* 755:727 */     for (Producto producto : listaProducto)
/* 756:    */     {
/* 757:728 */       if (fecha.before(hoy))
/* 758:    */       {
/* 759:729 */         BigDecimal valorDespachado = (BigDecimal)despachado.get(Integer.valueOf(producto.getIdProducto()));
/* 760:730 */         BigDecimal valorDevoluciones = (BigDecimal)devolucion.get(Integer.valueOf(producto.getIdProducto()));
/* 761:731 */         BigDecimal valorTransferencias = (BigDecimal)transferencias.get(Integer.valueOf(producto.getIdProducto()));
/* 762:733 */         if (valorDespachado == null) {
/* 763:734 */           valorDespachado = BigDecimal.ZERO;
/* 764:    */         }
/* 765:736 */         if (valorDevoluciones == null) {
/* 766:737 */           valorDevoluciones = BigDecimal.ZERO;
/* 767:    */         }
/* 768:739 */         if (valorTransferencias == null) {
/* 769:740 */           valorTransferencias = BigDecimal.ZERO;
/* 770:    */         }
/* 771:743 */         valor = valorDespachado.add(valorTransferencias).subtract(valorDevoluciones).setScale(2, RoundingMode.HALF_UP);
/* 772:    */       }
/* 773:    */       else
/* 774:    */       {
/* 775:747 */         BigDecimal valorProyeccionVentas = (BigDecimal)proyeccionVentas.get(Integer.valueOf(producto.getIdProducto()));
/* 776:748 */         if (valorProyeccionVentas == null) {
/* 777:749 */           valorProyeccionVentas = BigDecimal.ZERO;
/* 778:    */         }
/* 779:751 */         valor = valorProyeccionVentas;
/* 780:    */       }
/* 781:753 */       if (valor.compareTo(BigDecimal.ZERO) < 0) {
/* 782:754 */         valor = BigDecimal.ZERO;
/* 783:    */       }
/* 784:756 */       ventasPorfecha.put(Integer.valueOf(producto.getIdProducto()), valor);
/* 785:    */     }
/* 786:758 */     return ventasPorfecha;
/* 787:    */   }
/* 788:    */   
/* 789:    */   public void generarOrdenProduccion(PlanProduccion planProduccion, Date fecha)
/* 790:    */     throws AS2Exception
/* 791:    */   {
/* 792:763 */     planProduccion = cargarDetalle(planProduccion.getId());
/* 793:764 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 794:765 */     if ((fecha.before(planProduccion.getFechaInicio())) || (fecha.after(planProduccion.getFechaFin()))) {
/* 795:767 */       throw new AS2Exception("msg_error_fecha_fuera_rango", new String[] { sdf.format(planProduccion.getFechaInicio()), sdf.format(planProduccion.getFechaFin()) });
/* 796:    */     }
/* 797:769 */     boolean creadaOrden = false;
/* 798:770 */     for (DetallePlanProduccion detalle : planProduccion.getListaDetallePlanProduccion())
/* 799:    */     {
/* 800:771 */       if (this.ordenfabricacionDao.existeOrdenPorPlanYFecha(planProduccion, fecha, detalle.getProducto())) {
/* 801:772 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_EXISTE_ORDEN_PRODUCTO_FECHA", new String[] { detalle.getProducto().getNombre(), sdf.format(fecha) });
/* 802:    */       }
/* 803:774 */       if (crearOrdenfabricacion(detalle, fecha)) {
/* 804:775 */         creadaOrden = true;
/* 805:    */       }
/* 806:    */     }
/* 807:779 */     if (creadaOrden)
/* 808:    */     {
/* 809:780 */       planProduccion.setUltimaFechaGeneradaOrden(fecha);
/* 810:781 */       guardar(planProduccion);
/* 811:    */     }
/* 812:    */     else
/* 813:    */     {
/* 814:783 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_NO_HAY_VALORES_PARA_CREAR", new String[] { "" });
/* 815:    */     }
/* 816:    */   }
/* 817:    */   
/* 818:    */   private boolean crearOrdenfabricacion(DetallePlanProduccion detallePlanProduccion, Date fecha)
/* 819:    */     throws AS2Exception
/* 820:    */   {
/* 821:789 */     BigDecimal cantidadBatch = BigDecimal.ZERO;
/* 822:790 */     String diaSemana = obtenerDiaSemana(fecha);
/* 823:791 */     Object[] parametros = null;
/* 824:    */     try
/* 825:    */     {
/* 826:794 */       Method metodo = detallePlanProduccion.getClass().getMethod("getBatch" + diaSemana, new Class[0]);
/* 827:795 */       cantidadBatch = new BigDecimal(((Integer)metodo.invoke(detallePlanProduccion, parametros)).intValue());
/* 828:    */     }
/* 829:    */     catch (Exception e)
/* 830:    */     {
/* 831:797 */       e.printStackTrace();
/* 832:798 */       throw new AS2Exception(e.getMessage());
/* 833:    */     }
/* 834:    */     Method metodo;
/* 835:800 */     if (cantidadBatch.compareTo(BigDecimal.ZERO) > 0)
/* 836:    */     {
/* 837:801 */       OrdenFabricacion ordenFabricacion = new OrdenFabricacion();
/* 838:802 */       ordenFabricacion.setIdOrganizacion(detallePlanProduccion.getPlanProduccion().getIdOrganizacion());
/* 839:803 */       ordenFabricacion.setSucursal(this.servicioSucursal.buscarPorId(Integer.valueOf(detallePlanProduccion.getPlanProduccion().getIdSucursal())));
/* 840:804 */       ordenFabricacion.setNumero("");
/* 841:805 */       ordenFabricacion.setProducto(detallePlanProduccion.getProducto());
/* 842:806 */       ordenFabricacion.setRutaFabricacion(detallePlanProduccion.getRutaFabricacion());
/* 843:807 */       ordenFabricacion.setBodega(detallePlanProduccion.getPlanProduccion().getBodegaTrabajo());
/* 844:808 */       ordenFabricacion.setPlanProduccion(detallePlanProduccion.getPlanProduccion());
/* 845:809 */       ordenFabricacion.setEstado(EstadoProduccionEnum.PLANEADA);
/* 846:810 */       ordenFabricacion.setFecha(fecha);
/* 847:    */       
/* 848:812 */       List<Documento> listaDocumento = null;
/* 849:    */       try
/* 850:    */       {
/* 851:814 */         listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.ORDEN_FABRICACION, detallePlanProduccion
/* 852:815 */           .getPlanProduccion().getIdOrganizacion());
/* 853:    */       }
/* 854:    */       catch (ExcepcionAS2 e)
/* 855:    */       {
/* 856:817 */         e.printStackTrace();
/* 857:818 */         throw new AS2Exception(e.getCodigoExcepcion());
/* 858:    */       }
/* 859:820 */       if (listaDocumento.size() > 0) {
/* 860:821 */         ordenFabricacion.setDocumento((Documento)listaDocumento.get(0));
/* 861:    */       } else {
/* 862:823 */         throw new AS2Exception("msg_configuracion_documento", new String[] { "ORDEN_FABRICACION" });
/* 863:    */       }
/* 864:826 */       ordenFabricacion.setCantidadBatch(cantidadBatch);
/* 865:    */       try
/* 866:    */       {
/* 867:829 */         this.servicioOrdenFabricacion.guardar(ordenFabricacion);
/* 868:    */       }
/* 869:    */       catch (ExcepcionAS2Financiero e)
/* 870:    */       {
/* 871:831 */         e.printStackTrace();
/* 872:832 */         throw new AS2Exception(e.getCodigoExcepcion());
/* 873:    */       }
/* 874:    */       catch (ExcepcionAS2 e)
/* 875:    */       {
/* 876:834 */         e.printStackTrace();
/* 877:835 */         throw new AS2Exception(e.getCodigoExcepcion());
/* 878:    */       }
/* 879:837 */       return true;
/* 880:    */     }
/* 881:839 */     return false;
/* 882:    */   }
/* 883:    */   
/* 884:    */   public List<DetalleOrdenSalidaMaterial> generarDetallesOrdenSalidaMaterial(PlanProduccion planProduccion)
/* 885:    */     throws AS2Exception
/* 886:    */   {
/* 887:844 */     List<DetalleOrdenSalidaMaterial> lista = new ArrayList();
/* 888:845 */     List<Object[]> listaProducto = this.planProduccionDao.obtenerProductosPlanificadosPorFecha(planProduccion);
/* 889:846 */     Map<Producto, BigDecimal> mapaMateriales = new HashMap();
/* 890:847 */     for (Object[] objects : listaProducto)
/* 891:    */     {
/* 892:848 */       Integer idProducto = (Integer)objects[0];
/* 893:849 */       Producto producto = this.servicioProducto.buscarPorId(idProducto.intValue());
/* 894:850 */       BigDecimal cantidad = (BigDecimal)objects[1];
/* 895:    */       
/* 896:852 */       NodoArbol<Producto> componentesMateriales = this.servicioProducto.obtenerArbolComponentes(producto);
/* 897:853 */       ((Producto)componentesMateriales.getValor()).setCantidadProducir(cantidad);
/* 898:854 */       this.servicioProducto.actualizarCantidadesProducir(componentesMateriales, null);
/* 899:856 */       for (NodoArbol<Producto> nodoMaterial : componentesMateriales.getHojas())
/* 900:    */       {
/* 901:857 */         Producto material = (Producto)nodoMaterial.getValor();
/* 902:858 */         if (material.isIndicadorConsumoDirecto())
/* 903:    */         {
/* 904:859 */           BigDecimal cantidadMaterial = material.getCantidadProducir().setScale(2, RoundingMode.HALF_UP);
/* 905:860 */           if (mapaMateriales.containsKey(material)) {
/* 906:861 */             cantidadMaterial = cantidadMaterial.add((BigDecimal)mapaMateriales.get(material));
/* 907:    */           }
/* 908:863 */           mapaMateriales.put(material, cantidadMaterial);
/* 909:    */         }
/* 910:    */       }
/* 911:    */     }
/* 912:867 */     Object it = mapaMateriales.keySet().iterator();
/* 913:868 */     while (((Iterator)it).hasNext())
/* 914:    */     {
/* 915:869 */       Producto material = (Producto)((Iterator)it).next();
/* 916:870 */       material = this.servicioProducto.cargaDetalle(material.getId());
/* 917:871 */       BigDecimal cantidadMaterial = (BigDecimal)mapaMateriales.get(material);
/* 918:872 */       DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial = new DetalleOrdenSalidaMaterial();
/* 919:873 */       detalleOrdenSalidaMaterial.setIdOrganizacion(planProduccion.getIdOrganizacion());
/* 920:874 */       detalleOrdenSalidaMaterial.setIdSucursal(planProduccion.getIdSucursal());
/* 921:875 */       detalleOrdenSalidaMaterial.setProducto(material);
/* 922:876 */       detalleOrdenSalidaMaterial.setCantidad(cantidadMaterial);
/* 923:877 */       detalleOrdenSalidaMaterial.setIndicadorConsumoDirecto(material.isIndicadorConsumoDirecto());
/* 924:878 */       Bodega bodegaTrabajoMaterial = this.servicioProducto.obtenerBodegaTrabajoProducto(material, Integer.valueOf(planProduccion.getIdSucursal()));
/* 925:879 */       detalleOrdenSalidaMaterial.setBodega(bodegaTrabajoMaterial);
/* 926:880 */       detalleOrdenSalidaMaterial.setUnidad(material.getUnidad());
/* 927:881 */       if (bodegaTrabajoMaterial != null) {
/* 928:883 */         detalleOrdenSalidaMaterial.setSaldoBodegaTrabajo(this.servicioProducto.getSaldo(material.getId(), bodegaTrabajoMaterial.getId(), new Date()));
/* 929:    */       }
/* 930:886 */       lista.add(detalleOrdenSalidaMaterial);
/* 931:    */     }
/* 932:889 */     return lista;
/* 933:    */   }
/* 934:    */   
/* 935:    */   public boolean generarOrdenSolicitudMaterialConsumibles(List<DetalleOrdenSalidaMaterial> listaDetalleOrdenSalidaMaterial, String descripcion)
/* 936:    */     throws AS2Exception, ExcepcionAS2Financiero, ExcepcionAS2
/* 937:    */   {
/* 938:895 */     OrdenSalidaMaterial ordenSalidaMaterial = new OrdenSalidaMaterial();
/* 939:896 */     if (listaDetalleOrdenSalidaMaterial.size() > 0)
/* 940:    */     {
/* 941:897 */       ordenSalidaMaterial.setIdOrganizacion(((DetalleOrdenSalidaMaterial)listaDetalleOrdenSalidaMaterial.get(0)).getIdOrganizacion());
/* 942:898 */       ordenSalidaMaterial.setSucursal(this.servicioSucursal.buscarPorId(Integer.valueOf(((DetalleOrdenSalidaMaterial)listaDetalleOrdenSalidaMaterial.get(0)).getIdSucursal())));
/* 943:899 */       ordenSalidaMaterial.setIndicadorTransferencia(true);
/* 944:900 */       ordenSalidaMaterial.setNumero("");
/* 945:901 */       ordenSalidaMaterial.setFecha(new Date());
/* 946:902 */       ordenSalidaMaterial.setEstado(Estado.ELABORADO);
/* 947:903 */       ordenSalidaMaterial.setDescripcion(descripcion);
/* 948:    */       
/* 949:905 */       Map<String, String> filtros = new HashMap();
/* 950:906 */       filtros.put("idOrganizacion", String.valueOf(((DetalleOrdenSalidaMaterial)listaDetalleOrdenSalidaMaterial.get(0)).getIdOrganizacion()));
/* 951:907 */       filtros.put("documentoBase", DocumentoBase.ORDEN_SALIDA_MATERIAL.toString());
/* 952:908 */       List<Documento> listaDocumento = this.servicioDocumento.obtenerListaCombo("predeterminado", false, filtros);
/* 953:909 */       if (listaDocumento.size() > 0) {
/* 954:910 */         ordenSalidaMaterial.setDocumento((Documento)listaDocumento.get(0));
/* 955:    */       } else {
/* 956:912 */         throw new AS2Exception("msg_configuracion_documento", new String[] { DocumentoBase.ORDEN_SALIDA_MATERIAL.getNombre() });
/* 957:    */       }
/* 958:915 */       for (DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial : listaDetalleOrdenSalidaMaterial) {
/* 959:916 */         if (detalleOrdenSalidaMaterial.getCantidad().compareTo(BigDecimal.ZERO) > 0)
/* 960:    */         {
/* 961:917 */           detalleOrdenSalidaMaterial.setOrdenSalidaMaterial(ordenSalidaMaterial);
/* 962:918 */           ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().add(detalleOrdenSalidaMaterial);
/* 963:    */         }
/* 964:    */       }
/* 965:    */     }
/* 966:922 */     if (ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().size() > 0)
/* 967:    */     {
/* 968:923 */       this.servicioOrdenSalidaMaterial.guardar(ordenSalidaMaterial);
/* 969:924 */       return true;
/* 970:    */     }
/* 971:926 */     return false;
/* 972:    */   }
/* 973:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.impl.ServicioPlanProduccionImpl
 * JD-Core Version:    0.7.0.1
 */