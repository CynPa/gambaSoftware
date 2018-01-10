/*   1:    */ package com.asinfo.as2.amortizacion.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.amortizacion.procesos.servicio.ServicioAmortizacion;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   6:    */ import com.asinfo.as2.dao.GenericoDao;
/*   7:    */ import com.asinfo.as2.dao.InterfazContableProcesoDao;
/*   8:    */ import com.asinfo.as2.dao.amortizacion.AmortizacionDao;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  10:    */ import com.asinfo.as2.entities.Asiento;
/*  11:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  14:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  15:    */ import com.asinfo.as2.entities.Sucursal;
/*  16:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  17:    */ import com.asinfo.as2.entities.amortizacion.Amortizacion;
/*  18:    */ import com.asinfo.as2.entities.amortizacion.DetalleAmortizacion;
/*  19:    */ import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
/*  20:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  21:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  22:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  23:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  25:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  26:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  27:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  28:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  29:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  30:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  31:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  32:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  33:    */ import java.math.BigDecimal;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.Iterator;
/*  36:    */ import java.util.List;
/*  37:    */ import java.util.Map;
/*  38:    */ import javax.ejb.EJB;
/*  39:    */ import javax.ejb.SessionContext;
/*  40:    */ import javax.ejb.Stateless;
/*  41:    */ 
/*  42:    */ @Stateless
/*  43:    */ public class ServicioAmortizacionImpl
/*  44:    */   extends AbstractServicioAS2
/*  45:    */   implements ServicioAmortizacion
/*  46:    */ {
/*  47:    */   private static final long serialVersionUID = 1L;
/*  48:    */   @EJB
/*  49:    */   private transient GenericoDao<DetalleAmortizacion> detalleAmortizacionDao;
/*  50:    */   @EJB
/*  51:    */   private transient AmortizacionDao amortizacionDao;
/*  52:    */   @EJB
/*  53:    */   private transient ServicioSecuencia servicioSecuencia;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioSucursal servicioSucursal;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioPeriodo servicioPeriodo;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioAsiento servicioAsiento;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioTipoAsiento servicioTipoAsiento;
/*  68:    */   @EJB
/*  69:    */   private transient InterfazContableProcesoDao interfazContableProcesoDao;
/*  70:    */   
/*  71:    */   public List<Amortizacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  72:    */   {
/*  73: 81 */     return this.amortizacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void guardar(Amortizacion amortizacion)
/*  77:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  78:    */   {
/*  79: 86 */     validar(amortizacion);
/*  80: 87 */     if ((amortizacion.getNumero() == null) || (amortizacion.getNumero().isEmpty())) {
/*  81: 88 */       cargarSecuencia(amortizacion);
/*  82:    */     }
/*  83: 90 */     this.amortizacionDao.guardar(amortizacion);
/*  84: 91 */     int mesesPorAmortizar = 0;
/*  85: 92 */     for (DetalleAmortizacion detalle : amortizacion.getListaDetalleAmortizacion())
/*  86:    */     {
/*  87: 93 */       this.detalleAmortizacionDao.guardar(detalle);
/*  88: 94 */       if (!detalle.isEliminado()) {
/*  89: 95 */         mesesPorAmortizar++;
/*  90:    */       }
/*  91:    */     }
/*  92: 98 */     amortizacion.setMesesPorAmortizarReal(mesesPorAmortizar);
/*  93: 99 */     this.amortizacionDao.guardar(amortizacion);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void validar(Amortizacion amortizacion)
/*  97:    */     throws ExcepcionAS2Financiero
/*  98:    */   {
/*  99:104 */     this.servicioPeriodo.buscarPorFecha(amortizacion.getFechaInicioAmortizacion(), amortizacion.getIdOrganizacion(), amortizacion.getDocumento().getDocumentoBase());
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Amortizacion cargarSecuencia(Amortizacion amortizacion)
/* 103:    */     throws ExcepcionAS2
/* 104:    */   {
/* 105:109 */     String numero = this.servicioSecuencia.obtenerSecuencia(amortizacion.getDocumento().getSecuencia(), amortizacion.getFechaRegistro());
/* 106:110 */     this.servicioSecuencia.actualizarSecuencia(amortizacion.getDocumento().getSecuencia(), numero);
/* 107:111 */     amortizacion.setNumero(numero);
/* 108:    */     
/* 109:113 */     return amortizacion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void calcularDetalleAmortizacion(Amortizacion amortizacion)
/* 113:    */   {
/* 114:118 */     if ((amortizacion.getValor() != null) && (amortizacion.getValorAmortizado() != null) && (amortizacion.getMesesPorAmortizar() > 0))
/* 115:    */     {
/* 116:120 */       for (DetalleAmortizacion detalle : amortizacion.getListaDetalleAmortizacion()) {
/* 117:121 */         detalle.setEliminado(true);
/* 118:    */       }
/* 119:124 */       BigDecimal valorAmortizar = amortizacion.getValor().subtract(amortizacion.getValorAmortizado());
/* 120:125 */       BigDecimal cuota = valorAmortizar.divide(new BigDecimal(amortizacion.getMesesPorAmortizar()), 2, 4);
/* 121:126 */       Date fechaInicioMes = FuncionesUtiles.getFechaInicioMes(amortizacion.getFechaInicioAmortizacion());
/* 122:127 */       Date fechaFinMes = FuncionesUtiles.getFechaFinMes(amortizacion.getFechaInicioAmortizacion());
/* 123:128 */       BigDecimal totalCuotas = BigDecimal.ZERO;
/* 124:129 */       for (int i = 0; i < amortizacion.getMesesPorAmortizar(); i++)
/* 125:    */       {
/* 126:130 */         BigDecimal tmpCuota = cuota;
/* 127:131 */         if ((i == 0) && (amortizacion.getFechaInicioAmortizacion().after(fechaInicioMes)))
/* 128:    */         {
/* 129:133 */           int diasCuota = FuncionesUtiles.diferenciasDeFechas(amortizacion.getFechaInicioAmortizacion(), fechaFinMes);
/* 130:134 */           int diasMes = FuncionesUtiles.diferenciasDeFechas(fechaInicioMes, fechaFinMes);
/* 131:135 */           tmpCuota = cuota.divide(new BigDecimal(diasMes), 2, 4).multiply(new BigDecimal(diasCuota));
/* 132:    */         }
/* 133:137 */         DetalleAmortizacion detalle = new DetalleAmortizacion();
/* 134:138 */         detalle.setAmortizacion(amortizacion);
/* 135:139 */         detalle.setEstado(Estado.ELABORADO);
/* 136:140 */         detalle.setIdOrganizacion(amortizacion.getIdOrganizacion());
/* 137:141 */         detalle.setIdSucursal(amortizacion.getSucursal().getIdSucursal());
/* 138:142 */         detalle.setFechaVencimiento(fechaFinMes);
/* 139:143 */         detalle.setValor(tmpCuota);
/* 140:144 */         amortizacion.getListaDetalleAmortizacion().add(detalle);
/* 141:145 */         fechaFinMes = FuncionesUtiles.getFechaFinMes(FuncionesUtiles.sumarFechaMeses(fechaFinMes, 1));
/* 142:146 */         totalCuotas = totalCuotas.add(tmpCuota);
/* 143:    */       }
/* 144:148 */       if (valorAmortizar.compareTo(totalCuotas) > 0)
/* 145:    */       {
/* 146:149 */         DetalleAmortizacion detalle = new DetalleAmortizacion();
/* 147:150 */         detalle.setAmortizacion(amortizacion);
/* 148:151 */         detalle.setEstado(Estado.ELABORADO);
/* 149:152 */         detalle.setIdOrganizacion(amortizacion.getIdOrganizacion());
/* 150:153 */         detalle.setIdSucursal(amortizacion.getSucursal().getIdSucursal());
/* 151:154 */         detalle.setFechaVencimiento(fechaFinMes);
/* 152:155 */         detalle.setValor(valorAmortizar.subtract(totalCuotas));
/* 153:156 */         amortizacion.getListaDetalleAmortizacion().add(detalle);
/* 154:    */       }
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void eliminar(Amortizacion amortizacion)
/* 159:    */     throws ExcepcionAS2
/* 160:    */   {
/* 161:163 */     for (DetalleAmortizacion detalle : amortizacion.getListaDetalleAmortizacion()) {
/* 162:164 */       this.detalleAmortizacionDao.eliminar(detalle);
/* 163:    */     }
/* 164:166 */     this.amortizacionDao.eliminar(amortizacion);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Amortizacion buscarPorId(Integer id)
/* 168:    */     throws ExcepcionAS2
/* 169:    */   {
/* 170:171 */     return null;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Amortizacion cargarDetalle(Amortizacion amortizacion)
/* 174:    */   {
/* 175:176 */     return this.amortizacionDao.cargarDetalle(amortizacion);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int contarPorCriterio(Map<String, String> filters)
/* 179:    */   {
/* 180:181 */     return this.amortizacionDao.contarPorCriterio(filters);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void esEditable(Amortizacion amortizacion)
/* 184:    */     throws ExcepcionAS2
/* 185:    */   {
/* 186:186 */     if (this.amortizacionDao.cuotasContabilizadas(amortizacion)) {
/* 187:187 */       throw new ExcepcionAS2("msg_accion_no_permitida");
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void esAnulable(Amortizacion amortizacion)
/* 192:    */     throws ExcepcionAS2
/* 193:    */   {}
/* 194:    */   
/* 195:    */   public void contabilizar(int idOrganizacion, int idSucursal, Date fecha)
/* 196:    */     throws AS2Exception, ExcepcionAS2
/* 197:    */   {}
/* 198:    */   
/* 199:    */   public void contabilizarInterfazContable(int anio, int mes, InterfazContableProceso interfazContableProceso)
/* 200:    */     throws AS2Exception, ExcepcionAS2
/* 201:    */   {
/* 202:210 */     this.servicioPeriodo.buscarPorFecha(interfazContableProceso.getFechaHasta(), interfazContableProceso.getIdOrganizacion(), interfazContableProceso
/* 203:211 */       .getDocumento().getDocumentoBase());
/* 204:    */     try
/* 205:    */     {
/* 206:215 */       this.interfazContableProcesoDao.guardar(interfazContableProceso);
/* 207:216 */       this.interfazContableProcesoDao.flush();
/* 208:    */       
/* 209:218 */       TipoAsiento tipoAsiento = this.servicioTipoAsiento.buscarPorId(Integer.valueOf(interfazContableProceso.getDocumento().getTipoAsiento().getIdTipoAsiento()));
/* 210:219 */       Asiento asiento = contabilizarMes(anio, mes, interfazContableProceso, tipoAsiento);
/* 211:    */       
/* 212:221 */       interfazContableProceso.setAsiento(asiento);
/* 213:222 */       interfazContableProceso.setEstado(Estado.CONTABILIZADO);
/* 214:223 */       Date fechaDesde = FuncionesUtiles.getFecha(1, mes, anio);
/* 215:224 */       Date fechaHasta = FuncionesUtiles.getFechaFinMes(fechaDesde);
/* 216:225 */       interfazContableProceso.setFechaContabilizacion(fechaHasta);
/* 217:226 */       this.interfazContableProcesoDao.guardar(interfazContableProceso);
/* 218:    */     }
/* 219:    */     catch (ExcepcionAS2 e)
/* 220:    */     {
/* 221:228 */       this.context.setRollbackOnly();
/* 222:229 */       throw e;
/* 223:    */     }
/* 224:    */     catch (AS2Exception e)
/* 225:    */     {
/* 226:231 */       this.context.setRollbackOnly();
/* 227:232 */       throw e;
/* 228:    */     }
/* 229:    */     catch (Exception e)
/* 230:    */     {
/* 231:234 */       this.context.setRollbackOnly();
/* 232:235 */       throw new ExcepcionAS2(e.getMessage());
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Asiento contabilizarMes(int anio, int mes, InterfazContableProceso interfazContableProceso, TipoAsiento tipoAsiento)
/* 237:    */     throws ExcepcionAS2, AS2Exception
/* 238:    */   {
/* 239:242 */     Asiento asiento = new Asiento();
/* 240:    */     try
/* 241:    */     {
/* 242:244 */       Date fechaDesde = FuncionesUtiles.getFecha(1, mes, anio);
/* 243:245 */       Date fechaHasta = FuncionesUtiles.getFechaFinMes(fechaDesde);
/* 244:246 */       asiento.setEstado(Estado.ELABORADO);
/* 245:247 */       asiento.setIdOrganizacion(interfazContableProceso.getIdOrganizacion());
/* 246:248 */       asiento.setSucursal(interfazContableProceso.getSucursal());
/* 247:249 */       asiento.setIndicadorAutomatico(true);
/* 248:250 */       asiento.setTipoAsiento(tipoAsiento);
/* 249:251 */       asiento.setConcepto("Amortizacion mes: " + FuncionesUtiles.nombreMes(mes - 1));
/* 250:252 */       asiento.setFecha(fechaHasta);
/* 251:253 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(interfazContableProceso
/* 252:254 */         .getIdOrganizacion(), DocumentoBase.AMORTIZACION);
/* 253:255 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(interfazContableProceso
/* 254:256 */         .getIdOrganizacion(), DocumentoBase.AMORTIZACION);
/* 255:257 */       for (Iterator localIterator = listaDocumentoContabilizacion.iterator(); localIterator.hasNext();)
/* 256:    */       {
/* 257:257 */         documentoContabilizacion = (DocumentoContabilizacion)localIterator.next();
/* 258:258 */         List<DetalleInterfazContableProceso> lista = this.amortizacionDao.getInterfazContableAmortizacion(fechaDesde, fechaHasta, interfazContableProceso
/* 259:259 */           .getIdOrganizacion(), documentoContabilizacion.getProcesoContabilizacion());
/* 260:260 */         asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 261:261 */           .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 262:    */       }
/* 263:    */       DocumentoContabilizacion documentoContabilizacion;
/* 264:264 */       this.servicioAsiento.guardar(asiento);
/* 265:265 */       this.amortizacionDao.flush();
/* 266:    */       
/* 267:267 */       Object lamortizaciones = this.amortizacionDao.obtenerAmortizacionesContabilizadas(fechaDesde, fechaHasta, interfazContableProceso
/* 268:268 */         .getIdOrganizacion());
/* 269:269 */       for (Integer amortizacion : (List)lamortizaciones) {
/* 270:270 */         this.amortizacionDao.actualizarDetalleAmortizacion(amortizacion.intValue(), asiento, fechaDesde, fechaHasta, interfazContableProceso, Estado.CONTABILIZADO);
/* 271:    */       }
/* 272:273 */       this.amortizacionDao.flush();
/* 273:275 */       for (Integer idAmortizacion : (List)lamortizaciones)
/* 274:    */       {
/* 275:276 */         Object[] totales = this.amortizacionDao.obtenerTotalesContabilizados(idAmortizacion.intValue());
/* 276:277 */         BigDecimal valorAmortizadoTotal = (BigDecimal)totales[0];
/* 277:278 */         int mesesAmortizados = ((Long)totales[1]).intValue();
/* 278:279 */         this.amortizacionDao.actualizarCabeceraAmortizacion(idAmortizacion.intValue(), valorAmortizadoTotal, mesesAmortizados);
/* 279:    */       }
/* 280:281 */       this.amortizacionDao.flush();
/* 281:    */     }
/* 282:    */     catch (ExcepcionAS2 e)
/* 283:    */     {
/* 284:283 */       this.context.setRollbackOnly();
/* 285:284 */       throw e;
/* 286:    */     }
/* 287:    */     catch (AS2Exception e)
/* 288:    */     {
/* 289:286 */       this.context.setRollbackOnly();
/* 290:287 */       throw e;
/* 291:    */     }
/* 292:    */     catch (Exception e)
/* 293:    */     {
/* 294:289 */       this.context.setRollbackOnly();
/* 295:290 */       throw new ExcepcionAS2(e);
/* 296:    */     }
/* 297:293 */     return asiento;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public List getReporteAmortizacion(Amortizacion amortizacion)
/* 301:    */     throws ExcepcionAS2
/* 302:    */   {
/* 303:299 */     return this.amortizacionDao.getReporteAmortizacion(amortizacion);
/* 304:    */   }
/* 305:    */   
/* 306:    */   public List getReporteAmortizacionResumido(int anio, int mes, TipoAmortizacion tipoAmortizacion, int idOrganizacion)
/* 307:    */   {
/* 308:305 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(FuncionesUtiles.getFecha(1, mes, anio));
/* 309:306 */     List<Object[]> lamortizaciones = this.amortizacionDao.getAmortizaciones(fechaHasta, tipoAmortizacion, idOrganizacion);
/* 310:307 */     for (Object[] amortizacion : lamortizaciones)
/* 311:    */     {
/* 312:308 */       int idAmortizacion = ((Integer)amortizacion[0]).intValue();
/* 313:309 */       Object[] detalle = this.amortizacionDao.getDetalleAmortizadoFecha(idAmortizacion, fechaHasta, Estado.CONTABILIZADO);
/* 314:310 */       amortizacion[4] = detalle[0];
/* 315:311 */       amortizacion[8] = detalle[1];
/* 316:312 */       amortizacion[9] = detalle[2];
/* 317:    */     }
/* 318:315 */     return lamortizaciones;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public List<DetalleAmortizacion> getDetalleAmortizacion(int idAmortizacion, Date fechaDesde, Date fechaHasta, Estado estado, int idOrganizacion)
/* 322:    */   {
/* 323:320 */     return this.amortizacionDao.getDetalleAmortizacion(idAmortizacion, fechaDesde, fechaHasta, estado, idOrganizacion);
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void anularInterfazContable(InterfazContableProceso interfazContableProceso)
/* 327:    */   {
/* 328:325 */     List<Integer> lamortizaciones = this.amortizacionDao.obtenerAmortizacionesInterfazContable(interfazContableProceso);
/* 329:326 */     this.amortizacionDao.anularAmortizacionesInterfazContable(interfazContableProceso);
/* 330:327 */     this.amortizacionDao.flush();
/* 331:328 */     for (Integer idAmortizacion : lamortizaciones)
/* 332:    */     {
/* 333:329 */       Object[] totales = this.amortizacionDao.obtenerTotalesContabilizados(idAmortizacion.intValue());
/* 334:330 */       BigDecimal valorAmortizadoTotal = (BigDecimal)totales[0];
/* 335:331 */       int mesesAmortizados = ((Long)totales[1]).intValue();
/* 336:332 */       this.amortizacionDao.actualizarCabeceraAmortizacion(idAmortizacion.intValue(), valorAmortizadoTotal, mesesAmortizados);
/* 337:    */     }
/* 338:    */   }
/* 339:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.amortizacion.procesos.servicio.impl.ServicioAmortizacionImpl
 * JD-Core Version:    0.7.0.1
 */