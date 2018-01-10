/*   1:    */ package com.asinfo.as2.financiero.activos.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   4:    */ import com.asinfo.as2.dao.DetalleDepreciacionDao;
/*   5:    */ import com.asinfo.as2.dao.HistoricoDepreciacionDao;
/*   6:    */ import com.asinfo.as2.entities.Asiento;
/*   7:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   8:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   9:    */ import com.asinfo.as2.entities.DetalleDepreciacion;
/*  10:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  11:    */ import com.asinfo.as2.entities.HistoricoDepreciacion;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  17:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioPorcentajeImpuestoRentaAnual;
/*  20:    */ import com.asinfo.as2.financiero.activos.procesos.servicio.ServicioDepreciacionMensual;
/*  21:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  23:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  25:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  26:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  27:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  28:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  29:    */ import com.asinfo.as2.util.AppUtil;
/*  30:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  31:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.math.RoundingMode;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.List;
/*  36:    */ import java.util.Map;
/*  37:    */ import javax.ejb.EJB;
/*  38:    */ import javax.ejb.SessionContext;
/*  39:    */ import javax.ejb.Stateless;
/*  40:    */ import javax.ejb.TransactionAttribute;
/*  41:    */ import javax.ejb.TransactionAttributeType;
/*  42:    */ import javax.ejb.TransactionManagement;
/*  43:    */ import javax.ejb.TransactionManagementType;
/*  44:    */ 
/*  45:    */ @Stateless
/*  46:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  47:    */ public class ServicioDepreciacionMensualImpl
/*  48:    */   extends AbstractServicioAS2Financiero
/*  49:    */   implements ServicioDepreciacionMensual
/*  50:    */ {
/*  51:    */   private static final long serialVersionUID = 1L;
/*  52:    */   @EJB
/*  53:    */   private HistoricoDepreciacionDao historicoDepreciacionDao;
/*  54:    */   @EJB
/*  55:    */   private DetalleDepreciacionDao detalleDepreciacionDao;
/*  56:    */   @EJB
/*  57:    */   private ServicioPorcentajeImpuestoRentaAnual servicioPorcentajeImpuestoRentaAnual;
/*  58:    */   @EJB
/*  59:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  60:    */   @EJB
/*  61:    */   private ServicioPeriodo servicioPeriodo;
/*  62:    */   @EJB
/*  63:    */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  64:    */   @EJB
/*  65:    */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  66:    */   @EJB
/*  67:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  68:    */   
/*  69:    */   public void guardar(HistoricoDepreciacion historicoDepreciacion)
/*  70:    */     throws ExcepcionAS2Financiero
/*  71:    */   {
/*  72: 96 */     for (DetalleDepreciacion detalleDepreciacion : historicoDepreciacion.getListaDetalleDepreciacion()) {
/*  73: 97 */       this.detalleDepreciacionDao.guardar(detalleDepreciacion);
/*  74:    */     }
/*  75: 99 */     this.historicoDepreciacionDao.guardar(historicoDepreciacion);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void anular(HistoricoDepreciacion historicoDepreciacion)
/*  79:    */     throws ExcepcionAS2Financiero
/*  80:    */   {
/*  81:112 */     esEditable(historicoDepreciacion);
/*  82:113 */     this.servicioAsiento.anular(historicoDepreciacion.getAsientoFiscal());
/*  83:114 */     this.servicioAsiento.anular(historicoDepreciacion.getAsientoNIIF());
/*  84:116 */     for (DetalleDepreciacion detalleDepreciacion : historicoDepreciacion.getListaDetalleDepreciacion())
/*  85:    */     {
/*  86:117 */       detalleDepreciacion.setHistoricoDepreciacion(null);
/*  87:118 */       this.detalleDepreciacionDao.guardar(detalleDepreciacion);
/*  88:    */     }
/*  89:120 */     this.historicoDepreciacionDao.guardar(historicoDepreciacion);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public HistoricoDepreciacion buscarPorId(int idHistoricoDepreciacion)
/*  93:    */   {
/*  94:131 */     return (HistoricoDepreciacion)this.historicoDepreciacionDao.buscarPorId(Integer.valueOf(idHistoricoDepreciacion));
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List<HistoricoDepreciacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  98:    */   {
/*  99:144 */     return this.historicoDepreciacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int contarPorCriterio(Map<String, String> filters)
/* 103:    */   {
/* 104:155 */     return this.historicoDepreciacionDao.contarPorCriterio(filters);
/* 105:    */   }
/* 106:    */   
/* 107:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 108:    */   public void depreciar(HistoricoDepreciacion historicoDepreciacion)
/* 109:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 110:    */   {
/* 111:    */     try
/* 112:    */     {
/* 113:170 */       validar(historicoDepreciacion);
/* 114:171 */       List<DetalleDepreciacion> listaDetalleDepreciacionFiscal = this.historicoDepreciacionDao.obtieneDetalleDepreciacionAdepreciarFiscal(historicoDepreciacion
/* 115:172 */         .getAnio(), historicoDepreciacion.getMes(), historicoDepreciacion.getIdOrganizacion());
/* 116:    */       
/* 117:174 */       List<DetalleDepreciacion> listaDetalleDepreciacionNIFF = this.historicoDepreciacionDao.obtieneDetalleDepreciacionAdepreciarNIIF(historicoDepreciacion
/* 118:175 */         .getAnio(), historicoDepreciacion.getMes(), historicoDepreciacion.getIdOrganizacion());
/* 119:176 */       if (listaDetalleDepreciacionNIFF.size() + listaDetalleDepreciacionFiscal.size() == 0)
/* 120:    */       {
/* 121:177 */         this.context.setRollbackOnly();
/* 122:178 */         throw new ExcepcionAS2Financiero("msg_no_hay_datos");
/* 123:    */       }
/* 124:180 */       crearHistoricoDepreciacion(historicoDepreciacion, listaDetalleDepreciacionFiscal, listaDetalleDepreciacionNIFF);
/* 125:    */     }
/* 126:    */     catch (ExcepcionAS2Financiero e)
/* 127:    */     {
/* 128:183 */       this.context.setRollbackOnly();
/* 129:184 */       throw e;
/* 130:    */     }
/* 131:    */     catch (ExcepcionAS2 e)
/* 132:    */     {
/* 133:186 */       this.context.setRollbackOnly();
/* 134:187 */       throw e;
/* 135:    */     }
/* 136:    */     catch (Exception e)
/* 137:    */     {
/* 138:189 */       this.context.setRollbackOnly();
/* 139:190 */       throw new ExcepcionAS2(e);
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void contabilizarDepreciacionNIFF(HistoricoDepreciacion historicoDepreciacion)
/* 144:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 145:    */   {
/* 146:196 */     Date fechaContabilizacion = FuncionesUtiles.getFechaFinMes(historicoDepreciacion.getAnio(), historicoDepreciacion.getMes());
/* 147:    */     Asiento asiento;
/* 148:198 */     if (historicoDepreciacion.getAsientoNIIF() != null)
/* 149:    */     {
/* 150:199 */       Asiento asiento = historicoDepreciacion.getAsientoNIIF();
/* 151:200 */       asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/* 152:201 */       for (DetalleAsiento da : asiento.getListaDetalleAsiento()) {
/* 153:202 */         da.setEliminado(true);
/* 154:    */       }
/* 155:    */     }
/* 156:    */     else
/* 157:    */     {
/* 158:205 */       asiento = new Asiento();
/* 159:206 */       asiento.setEstado(Estado.ELABORADO);
/* 160:207 */       asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 161:208 */       asiento.setSucursal(AppUtil.getSucursal());
/* 162:209 */       asiento.setIndicadorAutomatico(true);
/* 163:210 */       int idTipoAsientoAjusteDepreciacion = 0;
/* 164:    */       try
/* 165:    */       {
/* 166:213 */         idTipoAsientoAjusteDepreciacion = ParametrosSistema.getTipoAsientoInterfazAjusteDepreciacion(historicoDepreciacion
/* 167:214 */           .getIdOrganizacion()).intValue();
/* 168:    */       }
/* 169:    */       catch (Exception e)
/* 170:    */       {
/* 171:216 */         throw new ExcepcionAS2("msg_info_configuracion", "TipoAsientoAjusteDepreciacion");
/* 172:    */       }
/* 173:219 */       TipoAsiento tipoAsiento = this.servicioTipoAsiento.buscarPorId(Integer.valueOf(idTipoAsientoAjusteDepreciacion));
/* 174:220 */       if (tipoAsiento == null) {
/* 175:221 */         throw new ExcepcionAS2("msg_info_configuracion", "TipoAsientoAjusteDepreciacion");
/* 176:    */       }
/* 177:223 */       asiento.setTipoAsiento(tipoAsiento);
/* 178:    */     }
/* 179:227 */     String concepto = "";
/* 180:228 */     concepto = "Ajuste Depreciación # " + FuncionesUtiles.nombreMes(historicoDepreciacion.getMes() - 1);
/* 181:229 */     asiento.setConcepto(concepto);
/* 182:230 */     asiento.setFecha(fechaContabilizacion);
/* 183:231 */     List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(historicoDepreciacion
/* 184:232 */       .getIdOrganizacion(), DocumentoBase.DEPRECIACION_NIIF);
/* 185:233 */     List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(historicoDepreciacion
/* 186:234 */       .getIdOrganizacion(), DocumentoBase.DEPRECIACION_NIIF);
/* 187:235 */     BigDecimal porcentajeImpuestoRentaAnual = this.servicioPorcentajeImpuestoRentaAnual.obtenerPorcentajePorAnio(historicoDepreciacion.getAnio());
/* 188:237 */     for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 189:    */     {
/* 190:239 */       List<DetalleInterfazContableProceso> lista = this.historicoDepreciacionDao.getInterfazContableDepreciacionNIIF(historicoDepreciacion);
/* 191:241 */       if ((documentoContabilizacion.getProcesoContabilizacion() == ProcesoContabilizacionEnum.CUENTA_ORDEN_IMPUESTO_RENNTA) || 
/* 192:242 */         (documentoContabilizacion.getProcesoContabilizacion() == ProcesoContabilizacionEnum.IMPUESTO_DIFERIDO)) {
/* 193:243 */         for (DetalleInterfazContableProceso detalleInterfazContableProceso : lista) {
/* 194:244 */           detalleInterfazContableProceso.setValor(detalleInterfazContableProceso.getValor().multiply(porcentajeImpuestoRentaAnual)
/* 195:245 */             .divide(BigDecimal.valueOf(100L), 18, RoundingMode.HALF_UP));
/* 196:    */         }
/* 197:    */       }
/* 198:249 */       asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 199:250 */         .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 200:    */     }
/* 201:254 */     this.servicioAsiento.guardar(asiento);
/* 202:    */     
/* 203:256 */     historicoDepreciacion.setEstado(Estado.CONTABILIZADO);
/* 204:257 */     historicoDepreciacion.setAsientoNIIF(asiento);
/* 205:    */   }
/* 206:    */   
/* 207:    */   private void contabilizarDepreciacionFiscal(HistoricoDepreciacion historicoDepreciacion)
/* 208:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 209:    */   {
/* 210:263 */     Date fechaContabilizacion = FuncionesUtiles.getFechaFinMes(historicoDepreciacion.getAnio(), historicoDepreciacion.getMes());
/* 211:    */     Asiento asiento;
/* 212:266 */     if (historicoDepreciacion.getAsientoFiscal() != null)
/* 213:    */     {
/* 214:267 */       Asiento asiento = historicoDepreciacion.getAsientoFiscal();
/* 215:268 */       asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/* 216:269 */       for (DetalleAsiento da : asiento.getListaDetalleAsiento()) {
/* 217:270 */         da.setEliminado(true);
/* 218:    */       }
/* 219:    */     }
/* 220:    */     else
/* 221:    */     {
/* 222:273 */       asiento = new Asiento();
/* 223:274 */       asiento.setEstado(Estado.ELABORADO);
/* 224:275 */       asiento.setIdOrganizacion(historicoDepreciacion.getIdOrganizacion());
/* 225:276 */       asiento.setSucursal(AppUtil.getSucursal());
/* 226:277 */       asiento.setIndicadorAutomatico(true);
/* 227:278 */       int idTipoAsientoDepreciacion = 0;
/* 228:    */       try
/* 229:    */       {
/* 230:281 */         idTipoAsientoDepreciacion = ParametrosSistema.getTipoAsientoInterfazDepreciacion(historicoDepreciacion.getIdOrganizacion()).intValue();
/* 231:282 */         TipoAsiento tipoAsiento = this.servicioTipoAsiento.buscarPorId(Integer.valueOf(idTipoAsientoDepreciacion));
/* 232:283 */         if (tipoAsiento == null) {
/* 233:284 */           throw new Exception();
/* 234:    */         }
/* 235:    */       }
/* 236:    */       catch (Exception e)
/* 237:    */       {
/* 238:288 */         throw new ExcepcionAS2("msg_info_configuracion", "TipoAsientoDepreciacion");
/* 239:    */       }
/* 240:    */       TipoAsiento tipoAsiento;
/* 241:291 */       asiento.setTipoAsiento(tipoAsiento);
/* 242:    */     }
/* 243:295 */     String concepto = "";
/* 244:296 */     concepto = "Depreciación # " + FuncionesUtiles.nombreMes(historicoDepreciacion.getMes() - 1);
/* 245:297 */     asiento.setConcepto(concepto);
/* 246:298 */     asiento.setFecha(fechaContabilizacion);
/* 247:299 */     List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(historicoDepreciacion
/* 248:300 */       .getIdOrganizacion(), DocumentoBase.DEPRECIACION);
/* 249:301 */     List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(historicoDepreciacion
/* 250:302 */       .getIdOrganizacion(), DocumentoBase.DEPRECIACION);
/* 251:304 */     for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 252:    */     {
/* 253:306 */       List<DetalleInterfazContableProceso> lista = this.historicoDepreciacionDao.getInterfazContableDepreciacion(historicoDepreciacion);
/* 254:307 */       asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 255:308 */         .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 256:    */     }
/* 257:311 */     this.servicioAsiento.guardar(asiento);
/* 258:    */     
/* 259:    */ 
/* 260:314 */     historicoDepreciacion.setEstado(Estado.CONTABILIZADO);
/* 261:    */     
/* 262:316 */     historicoDepreciacion.setAsientoFiscal(asiento);
/* 263:    */   }
/* 264:    */   
/* 265:    */   private void crearHistoricoDepreciacion(HistoricoDepreciacion historicoDepreciacion, List<DetalleDepreciacion> listaDetalleDepreciacionFiscal, List<DetalleDepreciacion> listaDetalleDepreciacionNIIF)
/* 266:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 267:    */   {
/* 268:    */     HistoricoDepreciacion historicoDepreciacionTMP;
/* 269:    */     HistoricoDepreciacion historicoDepreciacionTMP;
/* 270:325 */     if (historicoDepreciacion.getId() > 0)
/* 271:    */     {
/* 272:327 */       historicoDepreciacionTMP = buscarPorId(historicoDepreciacion.getId());
/* 273:    */     }
/* 274:    */     else
/* 275:    */     {
/* 276:330 */       historicoDepreciacionTMP = new HistoricoDepreciacion();
/* 277:331 */       historicoDepreciacionTMP.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 278:332 */       historicoDepreciacionTMP.setSucursal(AppUtil.getSucursal());
/* 279:333 */       historicoDepreciacionTMP.setEstado(Estado.ELABORADO);
/* 280:334 */       historicoDepreciacionTMP.setAnio(historicoDepreciacion.getAnio());
/* 281:335 */       historicoDepreciacionTMP.setMes(historicoDepreciacion.getMes());
/* 282:336 */       this.historicoDepreciacionDao.guardar(historicoDepreciacionTMP);
/* 283:    */     }
/* 284:338 */     for (DetalleDepreciacion detalleDepreciacion : listaDetalleDepreciacionNIIF)
/* 285:    */     {
/* 286:339 */       detalleDepreciacion.setHistoricoDepreciacion(historicoDepreciacionTMP);
/* 287:340 */       this.detalleDepreciacionDao.guardar(detalleDepreciacion);
/* 288:    */     }
/* 289:343 */     for (DetalleDepreciacion detalleDepreciacion : listaDetalleDepreciacionFiscal)
/* 290:    */     {
/* 291:344 */       detalleDepreciacion.setHistoricoDepreciacion(historicoDepreciacionTMP);
/* 292:345 */       this.detalleDepreciacionDao.guardar(detalleDepreciacion);
/* 293:    */     }
/* 294:347 */     if (listaDetalleDepreciacionFiscal.size() > 0) {
/* 295:348 */       contabilizarDepreciacionFiscal(historicoDepreciacionTMP);
/* 296:    */     }
/* 297:350 */     if (listaDetalleDepreciacionNIIF.size() > 0) {
/* 298:351 */       contabilizarDepreciacionNIFF(historicoDepreciacionTMP);
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void esEditable(HistoricoDepreciacion historicoDepreciacion)
/* 303:    */     throws ExcepcionAS2Financiero
/* 304:    */   {
/* 305:364 */     if (((historicoDepreciacion.getAsientoFiscal() != null) && (historicoDepreciacion.getAsientoFiscal().getEstado() != Estado.ELABORADO)) || (
/* 306:365 */       (historicoDepreciacion.getAsientoNIIF() != null) && (historicoDepreciacion.getAsientoNIIF().getEstado() != Estado.ELABORADO))) {
/* 307:366 */       throw new ExcepcionAS2Financiero("msg_info_anulacion_proceso_estado_asiento");
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   private void validar(HistoricoDepreciacion historicoDepreciacion)
/* 312:    */     throws ExcepcionAS2
/* 313:    */   {
/* 314:378 */     Date fechaHistoricoDepreciacion = FuncionesUtiles.setAtributoFecha(historicoDepreciacion.getAnio(), historicoDepreciacion.getMes() - 1, 1);
/* 315:379 */     this.servicioPeriodo.buscarPorFecha(fechaHistoricoDepreciacion, historicoDepreciacion.getIdOrganizacion(), DocumentoBase.DEPRECIACION);
/* 316:380 */     if (historicoDepreciacion.getId() == 0) {
/* 317:381 */       this.historicoDepreciacionDao.verificaRegistrosDepreciados(historicoDepreciacion.getMes(), historicoDepreciacion.getAnio(), historicoDepreciacion
/* 318:382 */         .getIdOrganizacion());
/* 319:    */     }
/* 320:    */   }
/* 321:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.procesos.servicio.impl.ServicioDepreciacionMensualImpl
 * JD-Core Version:    0.7.0.1
 */