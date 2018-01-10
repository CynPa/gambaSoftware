/*   1:    */ package com.asinfo.as2.inventario.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.MovimientoUnidadManejoDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   7:    */ import com.asinfo.as2.entities.DetalleMovimientoUnidadManejo;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.MovimientoUnidadManejo;
/*  11:    */ import com.asinfo.as2.entities.SaldoUnidadManejo;
/*  12:    */ import com.asinfo.as2.entities.Subempresa;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.Transportista;
/*  15:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  20:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  21:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoUnidadManejo;
/*  22:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.ejb.Lock;
/*  28:    */ import javax.ejb.LockType;
/*  29:    */ import javax.ejb.SessionContext;
/*  30:    */ import javax.ejb.Stateless;
/*  31:    */ import javax.ejb.TransactionAttribute;
/*  32:    */ import javax.ejb.TransactionAttributeType;
/*  33:    */ import javax.ejb.TransactionManagement;
/*  34:    */ import javax.ejb.TransactionManagementType;
/*  35:    */ 
/*  36:    */ @Stateless
/*  37:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  38:    */ public class ServicioMovimientoUnidadManejoImpl
/*  39:    */   extends AbstractServicioAS2Financiero
/*  40:    */   implements ServicioMovimientoUnidadManejo
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 1L;
/*  43:    */   @EJB
/*  44:    */   private MovimientoUnidadManejoDao movimientoUnidadManejoDao;
/*  45:    */   @EJB
/*  46:    */   private GenericoDao<DetalleMovimientoUnidadManejo> detalleMovimientoUnidadManejoDao;
/*  47:    */   @EJB
/*  48:    */   private ServicioSecuencia servicioSecuencia;
/*  49:    */   @EJB
/*  50:    */   private ServicioDocumento servicioDocumento;
/*  51: 71 */   List<DetalleMovimientoUnidadManejo> listaDetalleMovimientoUnidadManejo = new ArrayList();
/*  52:    */   
/*  53:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  54:    */   public void guardar(MovimientoUnidadManejo movimientoUnidadManejo)
/*  55:    */     throws ExcepcionAS2, AS2Exception
/*  56:    */   {
/*  57:    */     try
/*  58:    */     {
/*  59: 83 */       validaciones(movimientoUnidadManejo, false);
/*  60: 84 */       if ((movimientoUnidadManejo.getEmpresa() != null) && (movimientoUnidadManejo.getTransportista() != null)) {
/*  61: 85 */         crearIngresoEgreso(movimientoUnidadManejo, false);
/*  62:    */       }
/*  63: 88 */       actualizarSaldoUnidadManejo(movimientoUnidadManejo, false);
/*  64: 89 */       cargarSecuencia(movimientoUnidadManejo);
/*  65: 91 */       for (DetalleMovimientoUnidadManejo detalleMovimientoUnidadManejo : movimientoUnidadManejo.getDetalleMovimientoUnidadManejo()) {
/*  66: 92 */         if ((!detalleMovimientoUnidadManejo.isEliminado()) && (detalleMovimientoUnidadManejo.getCantidad() > 0)) {
/*  67: 93 */           this.detalleMovimientoUnidadManejoDao.guardar(detalleMovimientoUnidadManejo);
/*  68:    */         }
/*  69:    */       }
/*  70: 96 */       this.movimientoUnidadManejoDao.guardar(movimientoUnidadManejo);
/*  71:    */     }
/*  72:    */     catch (ExcepcionAS2Financiero e)
/*  73:    */     {
/*  74: 98 */       reversarDetallesCreados(movimientoUnidadManejo);
/*  75: 99 */       this.context.setRollbackOnly();
/*  76:100 */       throw e;
/*  77:    */     }
/*  78:    */     catch (ExcepcionAS2 e)
/*  79:    */     {
/*  80:102 */       reversarDetallesCreados(movimientoUnidadManejo);
/*  81:103 */       this.context.setRollbackOnly();
/*  82:104 */       throw e;
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:106 */       reversarDetallesCreados(movimientoUnidadManejo);
/*  87:107 */       this.context.setRollbackOnly();
/*  88:108 */       throw new ExcepcionAS2(e);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   private void reversarDetallesCreados(MovimientoUnidadManejo movimientoUnidadManejo)
/*  93:    */   {
/*  94:113 */     for (DetalleMovimientoUnidadManejo detalleMovimientoUnidadManejo : this.listaDetalleMovimientoUnidadManejo) {
/*  95:114 */       if (detalleMovimientoUnidadManejo.isIndicadorCreado()) {
/*  96:115 */         detalleMovimientoUnidadManejo.setEliminado(true);
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 102:    */   public void guardarRecepcion(MovimientoUnidadManejo movimientoUnidadManejo)
/* 103:    */     throws ExcepcionAS2, AS2Exception
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:125 */       movimientoUnidadManejo = (MovimientoUnidadManejo)this.movimientoUnidadManejoDao.buscarPorId(Integer.valueOf(movimientoUnidadManejo.getId()));
/* 108:126 */       if (movimientoUnidadManejo.getEstado().equals(Estado.PROCESADO)) {
/* 109:127 */         throw new ExcepcionAS2Inventario("msg_error_movimiento_procesado");
/* 110:    */       }
/* 111:128 */       if (movimientoUnidadManejo.getEstado().equals(Estado.ANULADO)) {
/* 112:129 */         throw new ExcepcionAS2Inventario("msg_error_movimiento_anulado");
/* 113:    */       }
/* 114:131 */       movimientoUnidadManejo.setEstado(Estado.PROCESADO);
/* 115:132 */       crearIngresoEgreso(movimientoUnidadManejo, true);
/* 116:133 */       this.movimientoUnidadManejoDao.guardar(movimientoUnidadManejo);
/* 117:    */     }
/* 118:    */     catch (ExcepcionAS2Financiero e)
/* 119:    */     {
/* 120:136 */       this.context.setRollbackOnly();
/* 121:137 */       throw e;
/* 122:    */     }
/* 123:    */     catch (ExcepcionAS2 e)
/* 124:    */     {
/* 125:139 */       this.context.setRollbackOnly();
/* 126:140 */       throw e;
/* 127:    */     }
/* 128:    */     catch (Exception e)
/* 129:    */     {
/* 130:142 */       this.context.setRollbackOnly();
/* 131:143 */       throw new ExcepcionAS2(e);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void crearIngresoEgreso(MovimientoUnidadManejo movimientoUnidadManejo, boolean guardar)
/* 136:    */     throws ExcepcionAS2, AS2Exception
/* 137:    */   {
/* 138:149 */     List<DetalleMovimientoUnidadManejo> lista = new ArrayList();
/* 139:151 */     for (DetalleMovimientoUnidadManejo dmum : movimientoUnidadManejo.getDetalleMovimientoUnidadManejo()) {
/* 140:152 */       if (!dmum.isEliminado())
/* 141:    */       {
/* 142:154 */         if (dmum.getCantidad() > 0)
/* 143:    */         {
/* 144:155 */           DetalleMovimientoUnidadManejo dmumt = new DetalleMovimientoUnidadManejo();
/* 145:156 */           dmumt.setIdOrganizacion(dmum.getIdOrganizacion());
/* 146:157 */           dmumt.setUnidadManejo(dmum.getUnidadManejo());
/* 147:    */           
/* 148:159 */           dmumt.setCantidad(dmum.getCantidad());
/* 149:160 */           dmumt.setOperacion(dmum.getOperacion() * -1);
/* 150:    */           
/* 151:162 */           dmumt.setMovimientoUnidadManejo(movimientoUnidadManejo);
/* 152:163 */           if (dmum.getTransportista() != null)
/* 153:    */           {
/* 154:164 */             dmumt.setSucursal(movimientoUnidadManejo.getSucursal());
/* 155:165 */             dmumt.setEmpresa(movimientoUnidadManejo.getEmpresa());
/* 156:166 */             dmumt.setSubempresa(movimientoUnidadManejo.getSubempresa());
/* 157:    */           }
/* 158:168 */           if (dmum.getSucursal() != null) {
/* 159:169 */             dmumt.setTransportista(movimientoUnidadManejo.getTransportista());
/* 160:    */           }
/* 161:171 */           dmumt.setIndicadorCreado(true);
/* 162:172 */           validarSaldoUnidadManejo(dmumt, false);
/* 163:173 */           if (guardar)
/* 164:    */           {
/* 165:174 */             actualizarSaldoUnidadManejoDetalle(dmumt, false);
/* 166:175 */             this.detalleMovimientoUnidadManejoDao.guardar(dmumt);
/* 167:    */           }
/* 168:177 */           lista.add(dmumt);
/* 169:    */         }
/* 170:179 */         if (dmum.getCantidadCliente() > 0)
/* 171:    */         {
/* 172:180 */           DetalleMovimientoUnidadManejo dmumt = new DetalleMovimientoUnidadManejo();
/* 173:181 */           dmumt.setIdOrganizacion(dmum.getIdOrganizacion());
/* 174:182 */           dmumt.setUnidadManejo(dmum.getUnidadManejo());
/* 175:    */           
/* 176:184 */           dmumt.setCantidad(dmum.getCantidadCliente());
/* 177:185 */           if (dmum.getTransportista() != null) {
/* 178:186 */             dmumt.setOperacion(-1);
/* 179:    */           } else {
/* 180:188 */             dmumt.setOperacion(dmum.getOperacion());
/* 181:    */           }
/* 182:190 */           dmumt.setMovimientoUnidadManejo(movimientoUnidadManejo);
/* 183:191 */           dmumt.setEmpresa(movimientoUnidadManejo.getEmpresa());
/* 184:192 */           dmumt.setSubempresa(movimientoUnidadManejo.getSubempresa());
/* 185:193 */           dmumt.setIndicadorCreado(true);
/* 186:194 */           validarSaldoUnidadManejo(dmumt, false);
/* 187:195 */           if (guardar)
/* 188:    */           {
/* 189:196 */             actualizarSaldoUnidadManejoDetalle(dmumt, false);
/* 190:197 */             this.detalleMovimientoUnidadManejoDao.guardar(dmumt);
/* 191:    */           }
/* 192:199 */           lista.add(dmumt);
/* 193:    */           
/* 194:201 */           dmumt = new DetalleMovimientoUnidadManejo();
/* 195:202 */           dmumt.setIdOrganizacion(dmum.getIdOrganizacion());
/* 196:203 */           dmumt.setUnidadManejo(dmum.getUnidadManejo());
/* 197:    */           
/* 198:205 */           dmumt.setCantidad(dmum.getCantidadCliente());
/* 199:206 */           dmumt.setOperacion(1);
/* 200:207 */           dmumt.setMovimientoUnidadManejo(movimientoUnidadManejo);
/* 201:208 */           dmumt.setTransportista(movimientoUnidadManejo.getTransportista());
/* 202:209 */           dmumt.setIndicadorCreado(true);
/* 203:211 */           if (guardar)
/* 204:    */           {
/* 205:212 */             actualizarSaldoUnidadManejoDetalle(dmumt, false);
/* 206:213 */             this.detalleMovimientoUnidadManejoDao.guardar(dmumt);
/* 207:    */           }
/* 208:216 */           lista.add(dmumt);
/* 209:    */         }
/* 210:    */       }
/* 211:    */     }
/* 212:221 */     movimientoUnidadManejo.getDetalleMovimientoUnidadManejo().addAll(lista);
/* 213:    */   }
/* 214:    */   
/* 215:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 216:    */   public void anular(MovimientoUnidadManejo movimientoUnidadManejo)
/* 217:    */     throws ExcepcionAS2, AS2Exception
/* 218:    */   {
/* 219:    */     try
/* 220:    */     {
/* 221:228 */       if (((MovimientoUnidadManejo)this.movimientoUnidadManejoDao.buscarPorId(Integer.valueOf(movimientoUnidadManejo.getId()))).getEstado().equals(Estado.ANULADO)) {
/* 222:229 */         throw new ExcepcionAS2Inventario("msg_error_movimiento_anulado");
/* 223:    */       }
/* 224:230 */       if (((MovimientoUnidadManejo)this.movimientoUnidadManejoDao.buscarPorId(Integer.valueOf(movimientoUnidadManejo.getId()))).getEstado().equals(Estado.PROCESADO)) {
/* 225:231 */         throw new ExcepcionAS2Inventario("msg_error_movimiento_procesado");
/* 226:    */       }
/* 227:233 */       movimientoUnidadManejo = cargarDetalle(movimientoUnidadManejo.getIdMovimientoUnidadManejo());
/* 228:234 */       movimientoUnidadManejo.setEstado(Estado.ANULADO);
/* 229:235 */       validaciones(movimientoUnidadManejo, true);
/* 230:236 */       actualizarSaldoUnidadManejo(movimientoUnidadManejo, true);
/* 231:237 */       this.movimientoUnidadManejoDao.guardar(movimientoUnidadManejo);
/* 232:    */     }
/* 233:    */     catch (ExcepcionAS2Financiero e)
/* 234:    */     {
/* 235:240 */       this.context.setRollbackOnly();
/* 236:241 */       throw e;
/* 237:    */     }
/* 238:    */     catch (ExcepcionAS2 e)
/* 239:    */     {
/* 240:243 */       this.context.setRollbackOnly();
/* 241:244 */       throw e;
/* 242:    */     }
/* 243:    */     catch (Exception e)
/* 244:    */     {
/* 245:246 */       this.context.setRollbackOnly();
/* 246:247 */       throw new ExcepcionAS2(e);
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   private void actualizarSaldoUnidadManejo(MovimientoUnidadManejo movimientoUnidadManejo, boolean reverso)
/* 251:    */     throws ExcepcionAS2
/* 252:    */   {
/* 253:253 */     for (DetalleMovimientoUnidadManejo dmum : movimientoUnidadManejo.getDetalleMovimientoUnidadManejo()) {
/* 254:254 */       if (!dmum.isEliminado()) {
/* 255:255 */         actualizarSaldoUnidadManejoDetalle(dmum, reverso);
/* 256:    */       }
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   @Lock(LockType.WRITE)
/* 261:    */   private void actualizarSaldoUnidadManejoDetalle(DetalleMovimientoUnidadManejo dmum, boolean reverso)
/* 262:    */     throws ExcepcionAS2
/* 263:    */   {
/* 264:263 */     this.movimientoUnidadManejoDao.actualizarSaldo(dmum.getMovimientoUnidadManejo().getIdOrganizacion(), dmum.getSucursal(), dmum.getEmpresa(), dmum
/* 265:264 */       .getSubempresa(), dmum.getTransportista(), dmum.getUnidadManejo(), dmum.getCantidad() * dmum.getOperacion() * (reverso ? -1 : 1));
/* 266:    */   }
/* 267:    */   
/* 268:    */   private void validaciones(MovimientoUnidadManejo movimientoUnidadManejo, boolean reverso)
/* 269:    */     throws ExcepcionAS2
/* 270:    */   {
/* 271:269 */     for (DetalleMovimientoUnidadManejo dmum : movimientoUnidadManejo.getDetalleMovimientoUnidadManejo()) {
/* 272:270 */       if (!dmum.isEliminado()) {
/* 273:271 */         validarSaldoUnidadManejo(dmum, reverso);
/* 274:    */       }
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void validarSaldoUnidadManejo(DetalleMovimientoUnidadManejo dmum, boolean reverso)
/* 279:    */     throws ExcepcionAS2Inventario
/* 280:    */   {
/* 281:278 */     MovimientoUnidadManejo movimientoUnidadManejo = dmum.getMovimientoUnidadManejo();
/* 282:279 */     int saldo = 0;
/* 283:280 */     if ((dmum.getOperacion() < 0) || ((reverso) && (dmum.getOperacion() > 0)))
/* 284:    */     {
/* 285:281 */       saldo = this.movimientoUnidadManejoDao.getSaldo(movimientoUnidadManejo.getIdOrganizacion(), dmum.getSucursal(), dmum.getEmpresa(), dmum
/* 286:282 */         .getSubempresa(), dmum.getTransportista(), dmum.getUnidadManejo());
/* 287:284 */       if (saldo < dmum.getCantidad() * (reverso ? -1 : 1))
/* 288:    */       {
/* 289:285 */         String mensaje = " :" + dmum.getUnidadManejo().getNombre() + " (" + dmum.getCantidad() + ">" + saldo + ")";
/* 290:286 */         throw new ExcepcionAS2Inventario("msg_info_inventario_0001", mensaje);
/* 291:    */       }
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   public List<SaldoUnidadManejo> obtenerSaldoUnidadManejo(int idOrganizacion, Sucursal sucursal, UnidadManejo unidadManejo)
/* 296:    */   {
/* 297:293 */     return obtenerSaldoUnidadManejo(idOrganizacion, sucursal, null, null, null, unidadManejo);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public List<SaldoUnidadManejo> obtenerSaldoUnidadManejo(int idOrganizacion, Empresa empresa, Subempresa subempresa, UnidadManejo unidadManejo)
/* 301:    */   {
/* 302:298 */     return obtenerSaldoUnidadManejo(idOrganizacion, null, empresa, subempresa, null, unidadManejo);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public List<SaldoUnidadManejo> obtenerSaldoUnidadManejo(int idOrganizacion, Transportista transportista, UnidadManejo unidadManejo)
/* 306:    */   {
/* 307:303 */     return obtenerSaldoUnidadManejo(idOrganizacion, null, null, null, transportista, unidadManejo);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public List<SaldoUnidadManejo> obtenerSaldoUnidadManejo(int idOrganicacion, Sucursal sucursal, Empresa empresa, Subempresa subempresa, Transportista transportista, UnidadManejo unidadManejo)
/* 311:    */   {
/* 312:309 */     return this.movimientoUnidadManejoDao.obtenerSaldoUnidadManejo(idOrganicacion, sucursal, empresa, subempresa, transportista, unidadManejo);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public List<MovimientoUnidadManejo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 316:    */   {
/* 317:315 */     return this.movimientoUnidadManejoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public int contarPorCirterio(Map<String, String> filters)
/* 321:    */   {
/* 322:320 */     return this.movimientoUnidadManejoDao.contarPorCriterio(filters);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void cargarSecuencia(MovimientoUnidadManejo movimientoUnidadManejo)
/* 326:    */     throws ExcepcionAS2, ExcepcionAS2Inventario
/* 327:    */   {
/* 328:325 */     if (movimientoUnidadManejo.getNumero().equals(""))
/* 329:    */     {
/* 330:326 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(movimientoUnidadManejo.getDocumento().getIdDocumento(), movimientoUnidadManejo
/* 331:327 */         .getFecha());
/* 332:328 */       movimientoUnidadManejo.setNumero(numero);
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   public List<SaldoUnidadManejo> reporteSaldoUnidadManejo(int idOrganizacion, Sucursal sucursal, Empresa empresa, Transportista transportista)
/* 337:    */   {
/* 338:334 */     return this.movimientoUnidadManejoDao.getReporteSaldoUnidadManejo(idOrganizacion, sucursal, empresa, transportista);
/* 339:    */   }
/* 340:    */   
/* 341:    */   public MovimientoUnidadManejo cargarDetalle(int idMovimientoUnidadManejo)
/* 342:    */   {
/* 343:339 */     return this.movimientoUnidadManejoDao.cargarDetalle(idMovimientoUnidadManejo);
/* 344:    */   }
/* 345:    */   
/* 346:    */   public List<UnidadManejo> obtenerUnidadManejoPorUsuario(int idOrganizacion, String consulta, Sucursal sucursal)
/* 347:    */   {
/* 348:345 */     return obtenerUnidadManejoPorUsuario(idOrganizacion, consulta, null, sucursal, null, null);
/* 349:    */   }
/* 350:    */   
/* 351:    */   public List<UnidadManejo> obtenerUnidadManejoPorUsuario(int idOrganizacion, String consulta, Empresa empresa, Subempresa subempresa)
/* 352:    */   {
/* 353:350 */     return obtenerUnidadManejoPorUsuario(idOrganizacion, consulta, null, null, empresa, subempresa);
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<UnidadManejo> obtenerUnidadManejoPorUsuario(int idOrganizacion, String consulta, Transportista transportista)
/* 357:    */   {
/* 358:355 */     return obtenerUnidadManejoPorUsuario(idOrganizacion, consulta, transportista, null, null, null);
/* 359:    */   }
/* 360:    */   
/* 361:    */   public List<UnidadManejo> obtenerUnidadManejoPorUsuario(int idOrganizacion, String consulta, Transportista transportista, Sucursal sucursal, Empresa empresa, Subempresa subempresa)
/* 362:    */   {
/* 363:362 */     return this.movimientoUnidadManejoDao.obtenerUnidadManejoPorUsuario(idOrganizacion, consulta, transportista, sucursal, empresa, subempresa);
/* 364:    */   }
/* 365:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.impl.ServicioMovimientoUnidadManejoImpl
 * JD-Core Version:    0.7.0.1
 */