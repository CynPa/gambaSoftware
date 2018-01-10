/*   1:    */ package com.asinfo.as2.financiero.presupuesto.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.presupuesto.DetalleMovimientoPartidaPresupuestariaDao;
/*   4:    */ import com.asinfo.as2.dao.presupuesto.MovimientoPartidaPresupuestariaDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   6:    */ import com.asinfo.as2.entities.CuentaContable;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Ejercicio;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.presupuesto.DetalleMovimientoPartidaPresupuestaria;
/*  12:    */ import com.asinfo.as2.entities.presupuesto.DetallePresupuesto;
/*  13:    */ import com.asinfo.as2.entities.presupuesto.MovimientoPartidaPresupuestaria;
/*  14:    */ import com.asinfo.as2.entities.presupuesto.Presupuesto;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  17:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioMovimientoPartidaPresupuestaria;
/*  20:    */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioPresupuesto;
/*  21:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.Iterator;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import javax.ejb.EJB;
/*  30:    */ import javax.ejb.SessionContext;
/*  31:    */ import javax.ejb.Stateless;
/*  32:    */ import javax.ejb.TransactionManagement;
/*  33:    */ import javax.ejb.TransactionManagementType;
/*  34:    */ 
/*  35:    */ @Stateless
/*  36:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  37:    */ public class ServicioMovimientoPartidaPresupuestariaImpl
/*  38:    */   extends AbstractServicioAS2
/*  39:    */   implements ServicioMovimientoPartidaPresupuestaria
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 4406759585649363071L;
/*  42:    */   @EJB
/*  43:    */   private transient MovimientoPartidaPresupuestariaDao movimientoPartidaPresupuestariaDao;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioSecuencia servicioSecuencia;
/*  46:    */   @EJB
/*  47:    */   private transient DetalleMovimientoPartidaPresupuestariaDao detalleMovimientoPartidaPresupuestariaDao;
/*  48:    */   @EJB
/*  49:    */   private transient ServicioPresupuesto servicioPresupuesto;
/*  50:    */   
/*  51:    */   public void guardar(MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria, Presupuesto presupuesto)
/*  52:    */     throws AS2Exception, ExcepcionAS2
/*  53:    */   {
/*  54:    */     try
/*  55:    */     {
/*  56: 61 */       validar(movimientoPartidaPresupuestaria, presupuesto);
/*  57: 62 */       cargarSecuencia(movimientoPartidaPresupuestaria);
/*  58: 63 */       this.movimientoPartidaPresupuestariaDao.guardar(movimientoPartidaPresupuestaria);
/*  59: 64 */       for (DetalleMovimientoPartidaPresupuestaria dmpp : movimientoPartidaPresupuestaria.getListaDetalleMovimientoPartidaPresupuestaria()) {
/*  60: 65 */         this.detalleMovimientoPartidaPresupuestariaDao.guardar(dmpp);
/*  61:    */       }
/*  62:    */     }
/*  63:    */     catch (ExcepcionAS2 e)
/*  64:    */     {
/*  65: 68 */       this.context.setRollbackOnly();
/*  66: 69 */       throw e;
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void validar(MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria, Presupuesto presupuesto)
/*  71:    */     throws AS2Exception
/*  72:    */   {
/*  73:    */     Map<String, DetallePresupuesto> hashDetallesPresupuesto;
/*  74:    */     DetallePresupuesto dpr;
/*  75: 75 */     if ((!Estado.ANULADO.equals(movimientoPartidaPresupuestaria.getEstado())) && 
/*  76: 76 */       (!Estado.APROBADO.equals(movimientoPartidaPresupuestaria.getEstado())))
/*  77:    */     {
/*  78: 77 */       hashDetallesPresupuesto = new HashMap();
/*  79: 78 */       for (Iterator localIterator = presupuesto.getListaDetallePresupuesto().iterator(); localIterator.hasNext();)
/*  80:    */       {
/*  81: 78 */         dpr = (DetallePresupuesto)localIterator.next();
/*  82: 79 */         hashDetallesPresupuesto.put(dpr.getCuentaContable().getId() + "~" + dpr.getDimensionContable().getId(), dpr);
/*  83:    */       }
/*  84: 82 */       for (DetalleMovimientoPartidaPresupuestaria dmpp : movimientoPartidaPresupuestaria.getListaDetalleMovimientoPartidaPresupuestaria())
/*  85:    */       {
/*  86: 83 */         DetallePresupuesto dpOrigen = (DetallePresupuesto)hashDetallesPresupuesto.get(dmpp.getCuentaContableOrigen().getId() + "~" + dmpp.getDimensionContableOrigen().getId());
/*  87: 84 */         if (movimientoPartidaPresupuestaria.getDocumento().getOperacion() == 0) {
/*  88: 85 */           validarValor(dpOrigen, dmpp.getValor(), movimientoPartidaPresupuestaria.getMesOrigen());
/*  89: 87 */         } else if (movimientoPartidaPresupuestaria.getDocumento().getOperacion() == -1) {
/*  90: 88 */           validarValor(dpOrigen, dmpp.getValor(), movimientoPartidaPresupuestaria.getMesOrigen());
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void eliminar(MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria)
/*  97:    */     throws AS2Exception
/*  98:    */   {
/*  99: 97 */     this.movimientoPartidaPresupuestariaDao.eliminar(movimientoPartidaPresupuestaria);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public MovimientoPartidaPresupuestaria cargarDetalle(int idMovimientoPartidaPresupuestaria)
/* 103:    */   {
/* 104:102 */     return this.movimientoPartidaPresupuestariaDao.cargarDetalle(idMovimientoPartidaPresupuestaria);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public MovimientoPartidaPresupuestaria buscarPorId(Integer idMovimientoPartidaPresupuestaria)
/* 108:    */   {
/* 109:107 */     return (MovimientoPartidaPresupuestaria)this.movimientoPartidaPresupuestariaDao.buscarPorId(idMovimientoPartidaPresupuestaria);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<MovimientoPartidaPresupuestaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 113:    */   {
/* 114:113 */     return this.movimientoPartidaPresupuestariaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int contarPorCriterio(Map<String, String> filters)
/* 118:    */   {
/* 119:118 */     return this.movimientoPartidaPresupuestariaDao.contarPorCriterio(filters);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void flush()
/* 123:    */   {
/* 124:123 */     this.movimientoPartidaPresupuestariaDao.flush();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void cargarSecuencia(MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria)
/* 128:    */     throws ExcepcionAS2
/* 129:    */   {
/* 130:127 */     if ((movimientoPartidaPresupuestaria.getNumero() == null) || (movimientoPartidaPresupuestaria.getNumero().equals("")))
/* 131:    */     {
/* 132:128 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(movimientoPartidaPresupuestaria.getDocumento().getIdDocumento(), movimientoPartidaPresupuestaria
/* 133:129 */         .getFecha());
/* 134:130 */       movimientoPartidaPresupuestaria.setNumero(numero);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public List<MovimientoPartidaPresupuestaria> cargarMovimientosPorAprobar(int idOrganizacion, Documento documento, Date fechaDesde, Date fechaHasta)
/* 139:    */   {
/* 140:136 */     return this.movimientoPartidaPresupuestariaDao.cargarMovimientosPorAprobar(idOrganizacion, documento, fechaDesde, fechaHasta);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void procesarMovimientos(List<MovimientoPartidaPresupuestaria> listaMovimientoPartidaPresupuestaria, Estado estado)
/* 144:    */     throws AS2Exception, ExcepcionAS2
/* 145:    */   {
/* 146:144 */     Presupuesto presupuesto = null;
/* 147:145 */     Map<String, DetallePresupuesto> hashDetallesPresupuesto = new HashMap();
/* 148:    */     try
/* 149:    */     {
/* 150:147 */       for (MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria : listaMovimientoPartidaPresupuestaria) {
/* 151:148 */         if (movimientoPartidaPresupuestaria.isTraSeleccionado()) {
/* 152:149 */           if (estado.equals(Estado.ANULADO))
/* 153:    */           {
/* 154:150 */             actualizarEstado(movimientoPartidaPresupuestaria.getId(), Estado.ANULADO);
/* 155:    */           }
/* 156:    */           else
/* 157:    */           {
/* 158:152 */             movimientoPartidaPresupuestaria = cargarDetalle(movimientoPartidaPresupuestaria.getId());
/* 159:153 */             if (presupuesto == null)
/* 160:    */             {
/* 161:154 */               presupuesto = this.servicioPresupuesto.buscarPresupuestoPorEjercicio(movimientoPartidaPresupuestaria.getEjercicio().getId(), 
/* 162:155 */                 AppUtil.getOrganizacion().getId());
/* 163:156 */               presupuesto = this.servicioPresupuesto.cargarDetalle(presupuesto.getId());
/* 164:157 */               for (DetallePresupuesto dpr : presupuesto.getListaDetallePresupuesto()) {
/* 165:158 */                 hashDetallesPresupuesto.put(dpr.getCuentaContable().getId() + "~" + dpr.getDimensionContable().getId(), dpr);
/* 166:    */               }
/* 167:    */             }
/* 168:162 */             else if (presupuesto.getId() != this.servicioPresupuesto.buscarPresupuestoPorEjercicio(movimientoPartidaPresupuestaria.getEjercicio().getId(), AppUtil.getOrganizacion().getId()).getId())
/* 169:    */             {
/* 170:163 */               presupuesto = this.servicioPresupuesto.buscarPresupuestoPorEjercicio(movimientoPartidaPresupuestaria.getEjercicio()
/* 171:164 */                 .getId(), AppUtil.getOrganizacion().getId());
/* 172:165 */               presupuesto = this.servicioPresupuesto.cargarDetalle(presupuesto.getId());
/* 173:166 */               for (DetallePresupuesto dpr : presupuesto.getListaDetallePresupuesto()) {
/* 174:167 */                 hashDetallesPresupuesto.put(dpr.getCuentaContable().getId() + "~" + dpr.getDimensionContable().getId(), dpr);
/* 175:    */               }
/* 176:    */             }
/* 177:172 */             for (DetalleMovimientoPartidaPresupuestaria dmpp : movimientoPartidaPresupuestaria
/* 178:173 */               .getListaDetalleMovimientoPartidaPresupuestaria()) {
/* 179:174 */               if (movimientoPartidaPresupuestaria.getDocumento().getOperacion() == 0)
/* 180:    */               {
/* 181:176 */                 DetallePresupuesto dpDestino = (DetallePresupuesto)hashDetallesPresupuesto.get(dmpp.getCuentaContableDestino().getId() + "~" + dmpp
/* 182:177 */                   .getDimensionContableDestino().getId());
/* 183:178 */                 DetallePresupuesto dpOrigen = (DetallePresupuesto)hashDetallesPresupuesto.get(dmpp.getCuentaContableOrigen().getId() + "~" + dmpp
/* 184:179 */                   .getDimensionContableOrigen().getId());
/* 185:180 */                 if ((dpDestino != null) && (dpOrigen != null))
/* 186:    */                 {
/* 187:182 */                   validarValor(dpOrigen, dmpp.getValor(), movimientoPartidaPresupuestaria.getMesOrigen());
/* 188:183 */                   actualizarValores(dpOrigen, dmpp.getValor(), movimientoPartidaPresupuestaria.getMesOrigen(), -1, true);
/* 189:    */                   
/* 190:185 */                   actualizarValores(dpDestino, dmpp.getValor(), movimientoPartidaPresupuestaria.getMesDestino(), 1, true);
/* 191:    */                 }
/* 192:    */               }
/* 193:    */               else
/* 194:    */               {
/* 195:188 */                 DetallePresupuesto dpOrigen = (DetallePresupuesto)hashDetallesPresupuesto.get(dmpp.getCuentaContableOrigen().getId() + "~" + dmpp
/* 196:189 */                   .getDimensionContableOrigen().getId());
/* 197:190 */                 if (dpOrigen != null) {
/* 198:191 */                   if (movimientoPartidaPresupuestaria.getDocumento().getOperacion() == 1)
/* 199:    */                   {
/* 200:193 */                     actualizarValores(dpOrigen, dmpp.getValor(), movimientoPartidaPresupuestaria.getMesOrigen(), 1, false);
/* 201:    */                   }
/* 202:    */                   else
/* 203:    */                   {
/* 204:196 */                     validarValor(dpOrigen, dmpp.getValor(), movimientoPartidaPresupuestaria.getMesOrigen());
/* 205:197 */                     actualizarValores(dpOrigen, dmpp.getValor(), movimientoPartidaPresupuestaria.getMesOrigen(), -1, false);
/* 206:    */                   }
/* 207:    */                 }
/* 208:    */               }
/* 209:    */             }
/* 210:202 */             this.servicioPresupuesto.guardar(presupuesto);
/* 211:203 */             movimientoPartidaPresupuestaria.setEstado(estado);
/* 212:204 */             guardar(movimientoPartidaPresupuestaria, presupuesto);
/* 213:    */           }
/* 214:    */         }
/* 215:    */       }
/* 216:    */     }
/* 217:    */     catch (AS2Exception e)
/* 218:    */     {
/* 219:210 */       this.context.setRollbackOnly();
/* 220:211 */       throw e;
/* 221:    */     }
/* 222:    */     catch (ExcepcionAS2 e)
/* 223:    */     {
/* 224:213 */       this.context.setRollbackOnly();
/* 225:214 */       throw e;
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   private void actualizarValores(DetallePresupuesto detallePresupuesto, BigDecimal valor, Mes mes, int operacion, boolean transferencia)
/* 230:    */   {
/* 231:220 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$Mes[mes.ordinal()])
/* 232:    */     {
/* 233:    */     case 1: 
/* 234:222 */       if (transferencia)
/* 235:    */       {
/* 236:223 */         if (operacion == 1) {
/* 237:224 */           detallePresupuesto.setTransferenciasIngresoEnero(detallePresupuesto.getTransferenciasIngresoEnero().add(valor));
/* 238:    */         } else {
/* 239:226 */           detallePresupuesto.setTransferenciasEgresoEnero(detallePresupuesto.getTransferenciasEgresoEnero().add(valor));
/* 240:    */         }
/* 241:    */       }
/* 242:229 */       else if (operacion == 1) {
/* 243:230 */         detallePresupuesto.setIncrementosEnero(detallePresupuesto.getIncrementosEnero().add(valor));
/* 244:    */       } else {
/* 245:232 */         detallePresupuesto.setDecrementosEnero(detallePresupuesto.getDecrementosEnero().add(valor));
/* 246:    */       }
/* 247:234 */       break;
/* 248:    */     case 2: 
/* 249:236 */       if (transferencia)
/* 250:    */       {
/* 251:237 */         if (operacion == 1) {
/* 252:238 */           detallePresupuesto.setTransferenciasIngresoFebrero(detallePresupuesto.getTransferenciasIngresoFebrero().add(valor));
/* 253:    */         } else {
/* 254:240 */           detallePresupuesto.setTransferenciasEgresoFebrero(detallePresupuesto.getTransferenciasEgresoFebrero().add(valor));
/* 255:    */         }
/* 256:    */       }
/* 257:242 */       else if (operacion == 1) {
/* 258:243 */         detallePresupuesto.setIncrementosFebrero(detallePresupuesto.getIncrementosFebrero().add(valor));
/* 259:    */       } else {
/* 260:245 */         detallePresupuesto.setDecrementosFebrero(detallePresupuesto.getDecrementosFebrero().add(valor));
/* 261:    */       }
/* 262:247 */       break;
/* 263:    */     case 3: 
/* 264:249 */       if (transferencia)
/* 265:    */       {
/* 266:250 */         if (operacion == 1) {
/* 267:251 */           detallePresupuesto.setTransferenciasIngresoMarzo(detallePresupuesto.getTransferenciasIngresoMarzo().add(valor));
/* 268:    */         } else {
/* 269:253 */           detallePresupuesto.setTransferenciasEgresoMarzo(detallePresupuesto.getTransferenciasEgresoMarzo().add(valor));
/* 270:    */         }
/* 271:    */       }
/* 272:255 */       else if (operacion == 1) {
/* 273:256 */         detallePresupuesto.setIncrementosMarzo(detallePresupuesto.getIncrementosMarzo().add(valor));
/* 274:    */       } else {
/* 275:258 */         detallePresupuesto.setDecrementosMarzo(detallePresupuesto.getDecrementosMarzo().add(valor));
/* 276:    */       }
/* 277:260 */       break;
/* 278:    */     case 4: 
/* 279:262 */       if (transferencia)
/* 280:    */       {
/* 281:263 */         if (operacion == 1) {
/* 282:264 */           detallePresupuesto.setTransferenciasIngresoAbril(detallePresupuesto.getTransferenciasIngresoAbril().add(valor));
/* 283:    */         } else {
/* 284:266 */           detallePresupuesto.setTransferenciasEgresoAbril(detallePresupuesto.getTransferenciasEgresoAbril().add(valor));
/* 285:    */         }
/* 286:    */       }
/* 287:268 */       else if (operacion == 1) {
/* 288:269 */         detallePresupuesto.setIncrementosAbril(detallePresupuesto.getIncrementosAbril().add(valor));
/* 289:    */       } else {
/* 290:271 */         detallePresupuesto.setDecrementosAbril(detallePresupuesto.getDecrementosAbril().add(valor));
/* 291:    */       }
/* 292:273 */       break;
/* 293:    */     case 5: 
/* 294:275 */       if (transferencia)
/* 295:    */       {
/* 296:276 */         if (operacion == 1) {
/* 297:277 */           detallePresupuesto.setTransferenciasIngresoMayo(detallePresupuesto.getTransferenciasIngresoMayo().add(valor));
/* 298:    */         } else {
/* 299:279 */           detallePresupuesto.setTransferenciasEgresoMayo(detallePresupuesto.getTransferenciasEgresoMayo().add(valor));
/* 300:    */         }
/* 301:    */       }
/* 302:281 */       else if (operacion == 1) {
/* 303:282 */         detallePresupuesto.setIncrementosMayo(detallePresupuesto.getIncrementosMayo().add(valor));
/* 304:    */       } else {
/* 305:284 */         detallePresupuesto.setDecrementosMayo(detallePresupuesto.getDecrementosMayo().add(valor));
/* 306:    */       }
/* 307:286 */       break;
/* 308:    */     case 6: 
/* 309:288 */       if (transferencia)
/* 310:    */       {
/* 311:289 */         if (operacion == 1) {
/* 312:290 */           detallePresupuesto.setTransferenciasIngresoJunio(detallePresupuesto.getTransferenciasIngresoJunio().add(valor));
/* 313:    */         } else {
/* 314:292 */           detallePresupuesto.setTransferenciasEgresoJunio(detallePresupuesto.getTransferenciasEgresoJunio().add(valor));
/* 315:    */         }
/* 316:    */       }
/* 317:295 */       else if (operacion == 1) {
/* 318:296 */         detallePresupuesto.setIncrementosJunio(detallePresupuesto.getIncrementosJunio().add(valor));
/* 319:    */       } else {
/* 320:298 */         detallePresupuesto.setDecrementosJunio(detallePresupuesto.getDecrementosJunio().add(valor));
/* 321:    */       }
/* 322:300 */       break;
/* 323:    */     case 7: 
/* 324:302 */       if (transferencia)
/* 325:    */       {
/* 326:303 */         if (operacion == 1) {
/* 327:304 */           detallePresupuesto.setTransferenciasIngresoJulio(detallePresupuesto.getTransferenciasIngresoJulio().add(valor));
/* 328:    */         } else {
/* 329:306 */           detallePresupuesto.setTransferenciasEgresoJulio(detallePresupuesto.getTransferenciasEgresoJulio().add(valor));
/* 330:    */         }
/* 331:    */       }
/* 332:309 */       else if (operacion == 1) {
/* 333:310 */         detallePresupuesto.setIncrementosJulio(detallePresupuesto.getIncrementosJulio().add(valor));
/* 334:    */       } else {
/* 335:312 */         detallePresupuesto.setDecrementosJulio(detallePresupuesto.getDecrementosJulio().add(valor));
/* 336:    */       }
/* 337:314 */       break;
/* 338:    */     case 8: 
/* 339:316 */       if (transferencia)
/* 340:    */       {
/* 341:317 */         if (operacion == 1) {
/* 342:318 */           detallePresupuesto.setTransferenciasIngresoAgosto(detallePresupuesto.getTransferenciasIngresoAgosto().add(valor));
/* 343:    */         } else {
/* 344:320 */           detallePresupuesto.setTransferenciasEgresoAgosto(detallePresupuesto.getTransferenciasEgresoAgosto().add(valor));
/* 345:    */         }
/* 346:    */       }
/* 347:323 */       else if (operacion == 1) {
/* 348:324 */         detallePresupuesto.setIncrementosAgosto(detallePresupuesto.getIncrementosAgosto().add(valor));
/* 349:    */       } else {
/* 350:326 */         detallePresupuesto.setDecrementosAgosto(detallePresupuesto.getDecrementosAgosto().add(valor));
/* 351:    */       }
/* 352:328 */       break;
/* 353:    */     case 9: 
/* 354:330 */       if (transferencia)
/* 355:    */       {
/* 356:331 */         if (operacion == 1) {
/* 357:332 */           detallePresupuesto.setTransferenciasIngresoSeptiembre(detallePresupuesto.getTransferenciasIngresoSeptiembre().add(valor));
/* 358:    */         } else {
/* 359:334 */           detallePresupuesto.setTransferenciasEgresoSeptiembre(detallePresupuesto.getTransferenciasEgresoSeptiembre().add(valor));
/* 360:    */         }
/* 361:    */       }
/* 362:337 */       else if (operacion == 1) {
/* 363:338 */         detallePresupuesto.setIncrementosSeptiembre(detallePresupuesto.getIncrementosSeptiembre().add(valor));
/* 364:    */       } else {
/* 365:340 */         detallePresupuesto.setDecrementosSeptiembre(detallePresupuesto.getDecrementosSeptiembre().add(valor));
/* 366:    */       }
/* 367:342 */       break;
/* 368:    */     case 10: 
/* 369:344 */       if (transferencia)
/* 370:    */       {
/* 371:345 */         if (operacion == 1) {
/* 372:346 */           detallePresupuesto.setTransferenciasIngresoOctubre(detallePresupuesto.getTransferenciasIngresoOctubre().add(valor));
/* 373:    */         } else {
/* 374:348 */           detallePresupuesto.setTransferenciasEgresoOctubre(detallePresupuesto.getTransferenciasEgresoOctubre().add(valor));
/* 375:    */         }
/* 376:    */       }
/* 377:351 */       else if (operacion == 1) {
/* 378:352 */         detallePresupuesto.setIncrementosOctubre(detallePresupuesto.getIncrementosOctubre().add(valor));
/* 379:    */       } else {
/* 380:354 */         detallePresupuesto.setDecrementosOctubre(detallePresupuesto.getDecrementosOctubre().add(valor));
/* 381:    */       }
/* 382:356 */       break;
/* 383:    */     case 11: 
/* 384:358 */       if (transferencia)
/* 385:    */       {
/* 386:359 */         if (operacion == 1) {
/* 387:360 */           detallePresupuesto.setTransferenciasIngresoNoviembre(detallePresupuesto.getTransferenciasIngresoNoviembre().add(valor));
/* 388:    */         } else {
/* 389:362 */           detallePresupuesto.setTransferenciasEgresoNoviembre(detallePresupuesto.getTransferenciasEgresoNoviembre().add(valor));
/* 390:    */         }
/* 391:    */       }
/* 392:365 */       else if (operacion == 1) {
/* 393:366 */         detallePresupuesto.setIncrementosNoviembre(detallePresupuesto.getIncrementosNoviembre().add(valor));
/* 394:    */       } else {
/* 395:368 */         detallePresupuesto.setDecrementosNoviembre(detallePresupuesto.getDecrementosNoviembre().add(valor));
/* 396:    */       }
/* 397:370 */       break;
/* 398:    */     case 12: 
/* 399:372 */       if (transferencia)
/* 400:    */       {
/* 401:373 */         if (operacion == 1) {
/* 402:374 */           detallePresupuesto.setTransferenciasIngresoDiciembre(detallePresupuesto.getTransferenciasIngresoDiciembre().add(valor));
/* 403:    */         } else {
/* 404:376 */           detallePresupuesto.setTransferenciasEgresoDiciembre(detallePresupuesto.getTransferenciasEgresoDiciembre().add(valor));
/* 405:    */         }
/* 406:    */       }
/* 407:378 */       else if (operacion == 1) {
/* 408:379 */         detallePresupuesto.setIncrementosDiciembre(detallePresupuesto.getIncrementosDiciembre().add(valor));
/* 409:    */       } else {
/* 410:381 */         detallePresupuesto.setDecrementosDiciembre(detallePresupuesto.getDecrementosDiciembre().add(valor));
/* 411:    */       }
/* 412:383 */       break;
/* 413:    */     }
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void actualizarSaldos(MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria, Presupuesto presupuesto)
/* 417:    */   {
/* 418:393 */     Map<String, DetallePresupuesto> hashDetallesPresupuesto = new HashMap();
/* 419:394 */     for (DetallePresupuesto dpr : presupuesto.getListaDetallePresupuesto()) {
/* 420:395 */       hashDetallesPresupuesto.put(dpr.getCuentaContable().getId() + "~" + dpr.getDimensionContable().getId(), dpr);
/* 421:    */     }
/* 422:397 */     for (DetalleMovimientoPartidaPresupuestaria dmpp : movimientoPartidaPresupuestaria.getListaDetalleMovimientoPartidaPresupuestaria())
/* 423:    */     {
/* 424:398 */       DetallePresupuesto aux = (DetallePresupuesto)hashDetallesPresupuesto.get(dmpp.getCuentaContableOrigen().getId() + "~" + dmpp
/* 425:399 */         .getDimensionContableOrigen().getId());
/* 426:400 */       actualizarValorDetalle(dmpp, aux, movimientoPartidaPresupuestaria.getMesOrigen(), true);
/* 427:401 */       if (dmpp.getCuentaContableDestino() != null) {
/* 428:402 */         actualizarValorDetalle(dmpp, aux, movimientoPartidaPresupuestaria.getMesDestino(), false);
/* 429:    */       }
/* 430:    */     }
/* 431:    */   }
/* 432:    */   
/* 433:    */   private void actualizarValorDetalle(DetalleMovimientoPartidaPresupuestaria detalleMovimientoPartidaPresupuestaria, DetallePresupuesto detallePresupuesto, Mes mes, boolean origen)
/* 434:    */   {
/* 435:408 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$Mes[mes.ordinal()])
/* 436:    */     {
/* 437:    */     case 1: 
/* 438:410 */       if (origen) {
/* 439:411 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorEnero());
/* 440:    */       } else {
/* 441:413 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorEnero());
/* 442:    */       }
/* 443:414 */       break;
/* 444:    */     case 2: 
/* 445:416 */       if (origen) {
/* 446:417 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorFebrero());
/* 447:    */       } else {
/* 448:419 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorFebrero());
/* 449:    */       }
/* 450:420 */       break;
/* 451:    */     case 3: 
/* 452:422 */       if (origen) {
/* 453:423 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorMarzo());
/* 454:    */       } else {
/* 455:425 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorMarzo());
/* 456:    */       }
/* 457:426 */       break;
/* 458:    */     case 4: 
/* 459:428 */       if (origen) {
/* 460:429 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorAbril());
/* 461:    */       } else {
/* 462:431 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorAbril());
/* 463:    */       }
/* 464:432 */       break;
/* 465:    */     case 5: 
/* 466:434 */       if (origen) {
/* 467:435 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorMayo());
/* 468:    */       } else {
/* 469:437 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorMayo());
/* 470:    */       }
/* 471:438 */       break;
/* 472:    */     case 6: 
/* 473:440 */       if (origen) {
/* 474:441 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorJunio());
/* 475:    */       } else {
/* 476:443 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorJunio());
/* 477:    */       }
/* 478:444 */       break;
/* 479:    */     case 7: 
/* 480:446 */       if (origen) {
/* 481:447 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorJulio());
/* 482:    */       } else {
/* 483:449 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorJulio());
/* 484:    */       }
/* 485:450 */       break;
/* 486:    */     case 8: 
/* 487:452 */       if (origen) {
/* 488:453 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorAgosto());
/* 489:    */       } else {
/* 490:455 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorAgosto());
/* 491:    */       }
/* 492:456 */       break;
/* 493:    */     case 9: 
/* 494:458 */       if (origen) {
/* 495:459 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorSeptiembre());
/* 496:    */       } else {
/* 497:461 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorSeptiembre());
/* 498:    */       }
/* 499:462 */       break;
/* 500:    */     case 10: 
/* 501:464 */       if (origen) {
/* 502:465 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorOctubre());
/* 503:    */       } else {
/* 504:467 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorOctubre());
/* 505:    */       }
/* 506:468 */       break;
/* 507:    */     case 11: 
/* 508:470 */       if (origen) {
/* 509:471 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorNoviembre());
/* 510:    */       } else {
/* 511:473 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorNoviembre());
/* 512:    */       }
/* 513:474 */       break;
/* 514:    */     case 12: 
/* 515:476 */       if (origen) {
/* 516:477 */         detalleMovimientoPartidaPresupuestaria.setSaldoOrigen(detallePresupuesto.getValorDiciembre());
/* 517:    */       } else {
/* 518:479 */         detalleMovimientoPartidaPresupuestaria.setSaldoDestino(detallePresupuesto.getValorDiciembre());
/* 519:    */       }
/* 520:480 */       break;
/* 521:    */     }
/* 522:    */   }
/* 523:    */   
/* 524:    */   private void validarValor(DetallePresupuesto detallePresupuesto, BigDecimal valor, Mes mes)
/* 525:    */     throws AS2Exception
/* 526:    */   {
/* 527:489 */     BigDecimal valorAux = BigDecimal.ZERO;
/* 528:490 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$Mes[mes.ordinal()])
/* 529:    */     {
/* 530:    */     case 1: 
/* 531:493 */       valorAux = detallePresupuesto.getValorCalculadoEnero();
/* 532:494 */       break;
/* 533:    */     case 2: 
/* 534:496 */       valorAux = detallePresupuesto.getValorCalculadoFebrero();
/* 535:497 */       break;
/* 536:    */     case 3: 
/* 537:499 */       valorAux = detallePresupuesto.getValorCalculadoMarzo();
/* 538:500 */       break;
/* 539:    */     case 4: 
/* 540:502 */       valorAux = detallePresupuesto.getValorCalculadoAbril();
/* 541:503 */       break;
/* 542:    */     case 5: 
/* 543:505 */       valorAux = detallePresupuesto.getValorCalculadoMayo();
/* 544:506 */       break;
/* 545:    */     case 6: 
/* 546:508 */       valorAux = detallePresupuesto.getValorCalculadoJunio();
/* 547:509 */       break;
/* 548:    */     case 7: 
/* 549:511 */       valorAux = detallePresupuesto.getValorCalculadoJulio();
/* 550:512 */       break;
/* 551:    */     case 8: 
/* 552:514 */       valorAux = detallePresupuesto.getValorCalculadoAgosto();
/* 553:515 */       break;
/* 554:    */     case 9: 
/* 555:517 */       valorAux = detallePresupuesto.getValorCalculadoSeptiembre();
/* 556:518 */       break;
/* 557:    */     case 10: 
/* 558:520 */       valorAux = detallePresupuesto.getValorCalculadoOctubre();
/* 559:521 */       break;
/* 560:    */     case 11: 
/* 561:523 */       valorAux = detallePresupuesto.getValorCalculadoNoviembre();
/* 562:524 */       break;
/* 563:    */     case 12: 
/* 564:526 */       valorAux = detallePresupuesto.getValorCalculadoDiciembre();
/* 565:527 */       break;
/* 566:    */     }
/* 567:532 */     if (valorAux.compareTo(valor) < 0) {
/* 568:533 */       throw new AS2Exception("com.asinfo.as2.financiero.presupuesto.procesos.servicio.impl.ServicioMovimientoPartidaPresupuestariaImpl.ERROR_VALOR_MAYOR_AL_ACTUAL", new String[] { "" + valor, "" + valorAux });
/* 569:    */     }
/* 570:    */   }
/* 571:    */   
/* 572:    */   private void actualizarEstado(int idMovimientoPartidaPresupuestaria, Estado anulado)
/* 573:    */   {
/* 574:538 */     this.movimientoPartidaPresupuestariaDao.actualizarEstado(idMovimientoPartidaPresupuestaria, anulado);
/* 575:    */   }
/* 576:    */   
/* 577:    */   public List<Object[]> getReporteMovimientoPartidaPresupuestaria(int idMovimientoPartidaPresupuestaria, int idOrganizacion)
/* 578:    */   {
/* 579:543 */     return this.movimientoPartidaPresupuestariaDao.getReporteMovimientoPartidaPresupuestaria(idMovimientoPartidaPresupuestaria, idOrganizacion);
/* 580:    */   }
/* 581:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.presupuesto.procesos.servicio.impl.ServicioMovimientoPartidaPresupuestariaImpl
 * JD-Core Version:    0.7.0.1
 */