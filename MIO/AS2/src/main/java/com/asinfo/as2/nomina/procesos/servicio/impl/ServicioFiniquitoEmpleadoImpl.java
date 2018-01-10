/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.HistoricoEmpleadoDao;
/*   5:    */ import com.asinfo.as2.dao.PagoRolDao;
/*   6:    */ import com.asinfo.as2.dao.PagoRolEmpleadoDao;
/*   7:    */ import com.asinfo.as2.dao.PagoRolEmpleadoRubroDao;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   9:    */ import com.asinfo.as2.entities.DetalleFiniquitoEmpleado;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.PagoRol;
/*  14:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  15:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  16:    */ import com.asinfo.as2.entities.Quincena;
/*  17:    */ import com.asinfo.as2.entities.Rubro;
/*  18:    */ import com.asinfo.as2.entities.RubroEmpleado;
/*  19:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  20:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  21:    */ import com.asinfo.as2.enumeraciones.TipoRubro;
/*  22:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  23:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  24:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena;
/*  25:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  26:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioFiniquitoEmpleado;
/*  27:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  28:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  29:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*  30:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleadoRubro;
/*  31:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  32:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioVacacion;
/*  33:    */ import com.asinfo.as2.util.AppUtil;
/*  34:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  35:    */ import java.math.BigDecimal;
/*  36:    */ import java.math.RoundingMode;
/*  37:    */ import java.util.ArrayList;
/*  38:    */ import java.util.Date;
/*  39:    */ import java.util.HashMap;
/*  40:    */ import java.util.Iterator;
/*  41:    */ import java.util.List;
/*  42:    */ import java.util.Map;
/*  43:    */ import javax.ejb.EJB;
/*  44:    */ import javax.ejb.Stateless;
/*  45:    */ import javax.ejb.TransactionAttribute;
/*  46:    */ import javax.ejb.TransactionAttributeType;
/*  47:    */ 
/*  48:    */ @Stateless
/*  49:    */ public class ServicioFiniquitoEmpleadoImpl
/*  50:    */   implements ServicioFiniquitoEmpleado
/*  51:    */ {
/*  52:    */   @EJB
/*  53:    */   private transient ServicioQuincena servicioQuincena;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioDocumento servicioDocumento;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioPagoRol servicioPagoRol;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioPagoRolEmpleadoRubro servicioPagoRolEmpleadoRubro;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  64:    */   @EJB
/*  65:    */   private transient PagoRolDao pagoRolDao;
/*  66:    */   @EJB
/*  67:    */   private transient PagoRolEmpleadoDao pagoRolEmpleadoDao;
/*  68:    */   @EJB
/*  69:    */   private transient PagoRolEmpleadoRubroDao pagoRolEmpleadoRubroDao;
/*  70:    */   @EJB
/*  71:    */   private transient HistoricoEmpleadoDao historicoEmpleadoDao;
/*  72:    */   @EJB
/*  73:    */   private transient GenericoDao<DetalleFiniquitoEmpleado> detalleFiniquitoEmpleadoDao;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioRubroEmpleado servicioRubroEmpleado;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioVacacion servicioVacacion;
/*  78:    */   
/*  79:    */   public HistoricoEmpleado generarFiniquito(HistoricoEmpleado historicoEmpleado)
/*  80:    */     throws ExcepcionAS2
/*  81:    */   {
/*  82: 93 */     this.historicoEmpleadoDao.detach(historicoEmpleado);
/*  83:    */     PagoRolEmpleado pagoRolEmpleado;
/*  84:    */     PagoRol pagoRol;
/*  85: 94 */     if (historicoEmpleado.getPagoRolEmpleadoFiniquito() == null)
/*  86:    */     {
/*  87: 95 */       Map<String, String> filtersQuincena = new HashMap();
/*  88: 96 */       filtersQuincena.put("indicadorFiniquito", "true");
/*  89: 97 */       filtersQuincena.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  90:    */       
/*  91: 99 */       List<Quincena> quincenas = this.servicioQuincena.obtenerListaCombo("", true, filtersQuincena);
/*  92:100 */       if (quincenas.size() == 0) {
/*  93:101 */         throw new ExcepcionAS2Nomina("msg_error_quincena_finiquito");
/*  94:    */       }
/*  95:103 */       Quincena quincena = (Quincena)quincenas.get(0);
/*  96:104 */       int anio = FuncionesUtiles.getAnio(historicoEmpleado.getFechaSalida());
/*  97:105 */       int mes = FuncionesUtiles.getMes(historicoEmpleado.getFechaSalida());
/*  98:    */       
/*  99:107 */       PagoRol pagoRol = this.servicioPagoRol.obtenerPagoRolPorQuincenaMesAnio(historicoEmpleado.getIdOrganizacion(), quincena, mes, anio);
/* 100:109 */       if (pagoRol == null)
/* 101:    */       {
/* 102:110 */         pagoRol = new PagoRol();
/* 103:111 */         pagoRol.setIdOrganizacion(historicoEmpleado.getIdOrganizacion());
/* 104:112 */         pagoRol.setAnio(anio);
/* 105:113 */         pagoRol.setMes(mes);
/* 106:114 */         pagoRol.setQuincena(quincena);
/* 107:115 */         pagoRol.setEstado(Estado.ELABORADO);
/* 108:    */         
/* 109:117 */         Map<String, String> filtroDoc = new HashMap();
/* 110:118 */         filtroDoc.put("documentoBase", "" + DocumentoBase.FINIQUITO);
/* 111:119 */         List<Documento> listaDocumento = this.servicioDocumento.obtenerListaCombo("", false, filtroDoc);
/* 112:120 */         pagoRol.setFecha(FuncionesUtiles.getFecha(1, mes, anio));
/* 113:121 */         if (listaDocumento.isEmpty()) {
/* 114:122 */           throw new ExcepcionAS2("msg_configuracion_documento", " FINIQUITO ");
/* 115:    */         }
/* 116:124 */         pagoRol.setDocumento((Documento)listaDocumento.get(0));
/* 117:    */       }
/* 118:128 */       PagoRolEmpleado pagoRolEmpleado = new PagoRolEmpleado();
/* 119:129 */       pagoRolEmpleado.setEmpleado(historicoEmpleado.getEmpleado());
/* 120:130 */       pagoRolEmpleado.setIdOrganizacion(pagoRol.getIdOrganizacion());
/* 121:131 */       pagoRolEmpleado.setIdSucursal(pagoRol.getIdSucursal());
/* 122:132 */       pagoRolEmpleado.setHistoricoEmpleadoFiniquito(historicoEmpleado);
/* 123:133 */       pagoRolEmpleado.setPagoRol(pagoRol);
/* 124:134 */       pagoRolEmpleado.setDiasFalta(0);
/* 125:    */       
/* 126:136 */       historicoEmpleado.setPagoRolEmpleadoFiniquito(pagoRolEmpleado);
/* 127:138 */       if (pagoRolEmpleado.getIdPagoRolEmpleado() != 0)
/* 128:    */       {
/* 129:139 */         List<PagoRolEmpleadoRubro> lista = this.servicioPagoRolEmpleadoRubro.getListaPagoRolEmpleadoRubroFiniquito(historicoEmpleado);
/* 130:140 */         pagoRolEmpleado.setListaPagoRolEmpleadoRubro(lista == null ? new ArrayList() : lista);
/* 131:    */       }
/* 132:    */     }
/* 133:    */     else
/* 134:    */     {
/* 135:144 */       pagoRolEmpleado = historicoEmpleado.getPagoRolEmpleadoFiniquito();
/* 136:145 */       pagoRol = pagoRolEmpleado.getPagoRol();
/* 137:    */     }
/* 138:148 */     historicoEmpleado.setEstadoFiniquito(Estado.ELABORADO);
/* 139:149 */     pagoRol.setListaPagoRolEmpleado(new ArrayList());
/* 140:150 */     pagoRol.getListaPagoRolEmpleado().add(pagoRolEmpleado);
/* 141:    */     
/* 142:152 */     return historicoEmpleado;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public PagoRolEmpleado procesarFiniquito(PagoRolEmpleado pagoRolEmpleado, boolean simular)
/* 146:    */     throws ExcepcionAS2Nomina, ExcepcionAS2Financiero, ExcepcionAS2
/* 147:    */   {
/* 148:163 */     int anio = pagoRolEmpleado.getPagoRol().getAnio();
/* 149:164 */     int mes = pagoRolEmpleado.getPagoRol().getMes();
/* 150:    */     
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:170 */     Map<Integer, RubroEmpleado> mapaRubrosEmpleado = new HashMap();
/* 156:171 */     Map<Integer, PagoRolEmpleadoRubro> mapaPagoRolEmpleadoRubro = new HashMap();
/* 157:172 */     if (pagoRolEmpleado.getListaPagoRolEmpleadoRubro() == null) {
/* 158:173 */       pagoRolEmpleado.setListaPagoRolEmpleadoRubro(new ArrayList());
/* 159:    */     }
/* 160:175 */     List<RubroEmpleado> listaRubroEmpleado = new ArrayList();
/* 161:178 */     if (pagoRolEmpleado.getHistoricoEmpleadoFiniquito().isIndicadorFiniquito()) {
/* 162:179 */       listaRubroEmpleado = this.servicioRubroEmpleado.getListaRubroEmpleadoFiniquito(pagoRolEmpleado, anio, mes);
/* 163:    */     }
/* 164:183 */     for (Iterator localIterator = listaRubroEmpleado.iterator(); localIterator.hasNext();)
/* 165:    */     {
/* 166:183 */       rubroEmpleado = (RubroEmpleado)localIterator.next();
/* 167:184 */       mapaRubrosEmpleado.put(Integer.valueOf(rubroEmpleado.getRubro().getId()), rubroEmpleado);
/* 168:    */     }
/* 169:188 */     Object listaEliminados = new ArrayList();
/* 170:189 */     for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : pagoRolEmpleado.getListaPagoRolEmpleadoRubro())
/* 171:    */     {
/* 172:190 */       if ((!mapaRubrosEmpleado.containsKey(Integer.valueOf(pagoRolEmpleadoRubro.getRubro().getId()))) && (!pagoRolEmpleadoRubro.isIndicadorManual()))
/* 173:    */       {
/* 174:191 */         pagoRolEmpleadoRubro.setEliminado(true);
/* 175:192 */         ((List)listaEliminados).add(pagoRolEmpleadoRubro);
/* 176:    */       }
/* 177:195 */       mapaPagoRolEmpleadoRubro.put(Integer.valueOf(pagoRolEmpleadoRubro.getRubro().getId()), pagoRolEmpleadoRubro);
/* 178:    */     }
/* 179:198 */     for (RubroEmpleado rubroEmpleado = ((List)listaEliminados).iterator(); rubroEmpleado.hasNext();)
/* 180:    */     {
/* 181:198 */       pagoRolEmpleadoRubro = (PagoRolEmpleadoRubro)rubroEmpleado.next();
/* 182:199 */       pagoRolEmpleado.getListaPagoRolEmpleadoRubro().remove(pagoRolEmpleadoRubro);
/* 183:    */     }
/* 184:    */     PagoRolEmpleadoRubro pagoRolEmpleadoRubro;
/* 185:201 */     Rubro rubro = null;
/* 186:204 */     for (RubroEmpleado rubroEmpleado : listaRubroEmpleado)
/* 187:    */     {
/* 188:205 */       PagoRolEmpleadoRubro prer = null;
/* 189:206 */       rubro = rubroEmpleado.getRubro();
/* 190:207 */       if (!mapaPagoRolEmpleadoRubro.containsKey(Integer.valueOf(rubroEmpleado.getRubro().getId())))
/* 191:    */       {
/* 192:209 */         prer = new PagoRolEmpleadoRubro();
/* 193:210 */         prer.setTiempo(BigDecimal.ZERO);
/* 194:211 */         prer.setValor(BigDecimal.ZERO);
/* 195:212 */         prer.setPagoRolEmpleado(pagoRolEmpleado);
/* 196:213 */         prer.setIdOrganizacion(pagoRolEmpleado.getIdOrganizacion());
/* 197:214 */         prer.setIdSucursal(pagoRolEmpleado.getIdSucursal());
/* 198:    */         
/* 199:216 */         prer.setIndicadorCalculoIESS(rubro.isIndicadorCalculoIESS());
/* 200:217 */         prer.setIndicadorCalculoImpuestoRenta(rubro.isIndicadorCalculoImpuestoRenta());
/* 201:218 */         prer.setIndicadorTiempo(rubro.isIndicadorTiempo());
/* 202:219 */         prer.setIndicadorProvision(rubro.isIndicadorProvision());
/* 203:220 */         prer.setIndicadorImpresionSobre(rubro.isIndicadorImpresionSobre());
/* 204:221 */         prer.setRubro(rubro);
/* 205:222 */         pagoRolEmpleado.getListaPagoRolEmpleadoRubro().add(prer);
/* 206:    */       }
/* 207:    */       else
/* 208:    */       {
/* 209:224 */         prer = (PagoRolEmpleadoRubro)mapaPagoRolEmpleadoRubro.get(Integer.valueOf(rubroEmpleado.getRubro().getId()));
/* 210:    */       }
/* 211:226 */       prer.setIndicadorCalculoIESS(rubro.isIndicadorCalculoIESS());
/* 212:227 */       if (rubro.getTipoRubro().equals(TipoRubro.FIJO)) {
/* 213:228 */         prer.setValor(rubroEmpleado.getValor());
/* 214:    */       }
/* 215:    */     }
/* 216:231 */     pagoRolEmpleado.getPagoRol().setListaPagoRolEmpleado(new ArrayList());
/* 217:232 */     pagoRolEmpleado.getPagoRol().getListaPagoRolEmpleado().add(pagoRolEmpleado);
/* 218:234 */     if (!simular) {
/* 219:235 */       guardarFiniquito(pagoRolEmpleado);
/* 220:    */     }
/* 221:241 */     return pagoRolEmpleado;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void refrescarDatosFiniquito(PagoRolEmpleado pagoRolEmpleado)
/* 225:    */   {
/* 226:252 */     int anio = pagoRolEmpleado.getPagoRol().getAnio();
/* 227:253 */     int mes = pagoRolEmpleado.getPagoRol().getMes();
/* 228:    */     
/* 229:    */ 
/* 230:256 */     Map<Date, DetalleFiniquitoEmpleado> mapaDetalleFiniquitoEmpleado = new HashMap();
/* 231:    */     
/* 232:258 */     List<RubroEmpleado> listaRubros = this.servicioRubroEmpleado.obtenerRubroPorFormula(pagoRolEmpleado.getIdOrganizacion(), pagoRolEmpleado
/* 233:259 */       .getEmpleado(), "g");
/* 234:261 */     if (listaRubros.size() == 1) {
/* 235:262 */       pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setRubroDecimoTercero(((RubroEmpleado)listaRubros.get(0)).getRubro());
/* 236:    */     } else {
/* 237:264 */       pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setRubroDecimoTercero(null);
/* 238:    */     }
/* 239:267 */     if (listaRubros.size() > 0)
/* 240:    */     {
/* 241:268 */       List<Rubro> listaRubro = new ArrayList();
/* 242:269 */       for (RubroEmpleado rubroEmpleado : listaRubros) {
/* 243:270 */         listaRubro.add(rubroEmpleado.getRubro());
/* 244:    */       }
/* 245:273 */       Date fechaDesde = FuncionesUtiles.getFecha(1, 12, mes == 12 ? anio : anio - 1);
/* 246:274 */       Date fechaHasta = FuncionesUtiles.getFecha(30, 11, mes == 12 ? anio + 1 : anio);
/* 247:276 */       for (DetalleFiniquitoEmpleado detalleFiniquitoEmpleado : pagoRolEmpleado.getHistoricoEmpleadoFiniquito()
/* 248:277 */         .getListaDetalleFiniquitoEmpleado())
/* 249:    */       {
/* 250:278 */         detalleFiniquitoEmpleado.setEliminado(true);
/* 251:279 */         mapaDetalleFiniquitoEmpleado.put(detalleFiniquitoEmpleado.getFecha(), detalleFiniquitoEmpleado);
/* 252:    */       }
/* 253:282 */       for (DetalleFiniquitoEmpleado detalleFiniquitoEmpleado : this.servicioPagoRol.obtenerValoresPagadosPorRubrosYFechas(pagoRolEmpleado
/* 254:283 */         .getIdOrganizacion(), pagoRolEmpleado.getEmpleado(), listaRubro, fechaDesde, fechaHasta))
/* 255:    */       {
/* 256:285 */         DetalleFiniquitoEmpleado dfe = (DetalleFiniquitoEmpleado)mapaDetalleFiniquitoEmpleado.get(detalleFiniquitoEmpleado.getFecha());
/* 257:287 */         if (dfe != null)
/* 258:    */         {
/* 259:288 */           dfe.setEliminado(false);
/* 260:289 */           dfe.setValor(detalleFiniquitoEmpleado.getValor());
/* 261:290 */           dfe.setIndicadorImpresionSobre(detalleFiniquitoEmpleado.isIndicadorImpresionSobre());
/* 262:    */         }
/* 263:    */         else
/* 264:    */         {
/* 265:292 */           detalleFiniquitoEmpleado.setHistoricoEmpleado(pagoRolEmpleado.getHistoricoEmpleadoFiniquito());
/* 266:293 */           pagoRolEmpleado.getHistoricoEmpleadoFiniquito().getListaDetalleFiniquitoEmpleado().add(detalleFiniquitoEmpleado);
/* 267:    */         }
/* 268:    */       }
/* 269:    */     }
/* 270:299 */     listaRubros = this.servicioRubroEmpleado.obtenerRubroPorFormula(pagoRolEmpleado.getIdOrganizacion(), pagoRolEmpleado.getEmpleado(), "f");
/* 271:301 */     if (listaRubros.size() == 1) {
/* 272:302 */       pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setRubroDecimoCuarto(((RubroEmpleado)listaRubros.get(0)).getRubro());
/* 273:    */     } else {
/* 274:304 */       pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setRubroDecimoCuarto(null);
/* 275:    */     }
/* 276:308 */     listaRubros = this.servicioRubroEmpleado.obtenerRubroPorFormula(pagoRolEmpleado.getIdOrganizacion(), pagoRolEmpleado.getEmpleado(), "j");
/* 277:310 */     if (listaRubros.size() == 1) {
/* 278:311 */       pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setRubroFondoReserva(((RubroEmpleado)listaRubros.get(0)).getRubro());
/* 279:    */     } else {
/* 280:313 */       pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setRubroFondoReserva(null);
/* 281:    */     }
/* 282:318 */     this.servicioVacacion.generarVacaciones(pagoRolEmpleado.getIdOrganizacion(), pagoRolEmpleado.getEmpleado(), false);
/* 283:    */     
/* 284:320 */     pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setDiasVacacionesFiniquito(this.servicioVacacion
/* 285:321 */       .getDiasPendientesFiniquito(pagoRolEmpleado.getHistoricoEmpleadoFiniquito()));
/* 286:    */     
/* 287:    */ 
/* 288:324 */     Date fechaSalida = pagoRolEmpleado.getHistoricoEmpleadoFiniquito().getFechaSalida();
/* 289:325 */     Date fechaFinMesSalida = FuncionesUtiles.getFechaFinMes(fechaSalida);
/* 290:326 */     int mesesRestar = 1;
/* 291:327 */     if (fechaSalida.equals(fechaFinMesSalida)) {
/* 292:328 */       mesesRestar = 0;
/* 293:    */     }
/* 294:330 */     Date fechaDesdeMesRolAnterior = FuncionesUtiles.getFechaInicioMes(FuncionesUtiles.sumarFechaMeses(pagoRolEmpleado.getPagoRol().getFecha(), -mesesRestar));
/* 295:    */     
/* 296:332 */     Date fechaHastaMesRolAnterior = FuncionesUtiles.getFechaFinMes(fechaDesdeMesRolAnterior);
/* 297:333 */     pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setValorUltimaRemuneracion(this.servicioPagoRolEmpleado
/* 298:334 */       .obtenerIngresosAportables(fechaDesdeMesRolAnterior, fechaHastaMesRolAnterior, pagoRolEmpleado
/* 299:335 */       .getHistoricoEmpleadoFiniquito().getId()));
/* 300:    */     
/* 301:337 */     int mesSalida = FuncionesUtiles.getMes(fechaSalida);
/* 302:338 */     int anioSalida = FuncionesUtiles.getAnio(fechaSalida);
/* 303:    */     
/* 304:340 */     int mesIngreso = FuncionesUtiles.getMes(pagoRolEmpleado.getHistoricoEmpleadoFiniquito().getFechaIngreso());
/* 305:    */     
/* 306:342 */     Date fechaVacacionAnterior = FuncionesUtiles.getFecha(1, mesIngreso, anioSalida);
/* 307:343 */     if (mesIngreso > mesSalida) {
/* 308:344 */       fechaVacacionAnterior = FuncionesUtiles.getFecha(1, mesIngreso, anioSalida - 1);
/* 309:    */     }
/* 310:347 */     pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setValorRemuneracionVacacion(this.servicioPagoRolEmpleado
/* 311:348 */       .obtenerIngresosAportables(fechaVacacionAnterior, fechaFinMesSalida, pagoRolEmpleado
/* 312:349 */       .getHistoricoEmpleadoFiniquito().getId()));
/* 313:    */     
/* 314:    */ 
/* 315:352 */     Date fechaDecimoAnterior = FuncionesUtiles.getFecha(1, 12, anioSalida - 1);
/* 316:353 */     if (mesSalida == 12) {
/* 317:354 */       fechaDecimoAnterior = FuncionesUtiles.getFecha(1, 12, anioSalida);
/* 318:    */     }
/* 319:357 */     pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setValorRemuneracionDecimoTercero(this.servicioPagoRolEmpleado
/* 320:358 */       .obtenerIngresosAportables(fechaDecimoAnterior, fechaFinMesSalida, pagoRolEmpleado
/* 321:359 */       .getHistoricoEmpleadoFiniquito().getId()));
/* 322:    */     
/* 323:    */ 
/* 324:362 */     pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setSaldoPrestamos(this.servicioPagoRolEmpleado
/* 325:363 */       .getSaldoTotalPrestamosEmpleado(pagoRolEmpleado.getIdOrganizacion(), pagoRolEmpleado
/* 326:364 */       .getHistoricoEmpleadoFiniquito().getEmpleado()));
/* 327:    */     
/* 328:    */ 
/* 329:367 */     Date fechaInicio = pagoRolEmpleado.getHistoricoEmpleadoFiniquito().getFechaIngreso();
/* 330:368 */     int dias = FuncionesUtiles.diferenciasDeFechas30Dias(fechaInicio, fechaSalida);
/* 331:    */     
/* 332:370 */     BigDecimal desahucio = BigDecimal.ZERO;
/* 333:    */     
/* 334:    */ 
/* 335:373 */     int diaSalida = FuncionesUtiles.getDiaFecha(fechaSalida);
/* 336:    */     
/* 337:375 */     Date fechaSalidaDesahucio = FuncionesUtiles.getFecha(diaSalida, mesSalida, anioSalida - 1);
/* 338:377 */     if ((fechaSalidaDesahucio.compareTo(fechaInicio) == 0) || (fechaSalidaDesahucio.compareTo(fechaInicio) > 0))
/* 339:    */     {
/* 340:378 */       desahucio = FuncionesUtiles.redondearBigDecimal(new BigDecimal(dias).divide(new BigDecimal(360), 2, RoundingMode.HALF_UP)
/* 341:379 */         .multiply(pagoRolEmpleado.getHistoricoEmpleadoFiniquito().getValorUltimaRemuneracion().multiply(new BigDecimal(0.25D))), 2);
/* 342:380 */       pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setValorDesahucio(desahucio);
/* 343:    */     }
/* 344:    */     else
/* 345:    */     {
/* 346:382 */       pagoRolEmpleado.getHistoricoEmpleadoFiniquito().setValorDesahucio(desahucio);
/* 347:    */     }
/* 348:    */   }
/* 349:    */   
/* 350:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 351:    */   public void guardarFiniquito(PagoRolEmpleado pagoRolEmpleado)
/* 352:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 353:    */   {
/* 354:393 */     for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : pagoRolEmpleado.getListaPagoRolEmpleadoRubro())
/* 355:    */     {
/* 356:394 */       pagoRolEmpleadoRubro.setPagoRolEmpleado(pagoRolEmpleado);
/* 357:395 */       this.pagoRolEmpleadoRubroDao.guardar(pagoRolEmpleadoRubro);
/* 358:    */     }
/* 359:398 */     for (DetalleFiniquitoEmpleado detalleFiniquitoEmpleado : pagoRolEmpleado.getHistoricoEmpleadoFiniquito().getListaDetalleFiniquitoEmpleado()) {
/* 360:399 */       this.detalleFiniquitoEmpleadoDao.guardar(detalleFiniquitoEmpleado);
/* 361:    */     }
/* 362:402 */     HistoricoEmpleado historicoEmpleado = pagoRolEmpleado.getHistoricoEmpleadoFiniquito();
/* 363:403 */     int diasTrabajados = FuncionesUtiles.getDiaFecha(historicoEmpleado.getFechaSalida());
/* 364:405 */     if ((FuncionesUtiles.getMes(historicoEmpleado.getFechaSalida()) == FuncionesUtiles.getMes(historicoEmpleado.getFechaIngreso())) && 
/* 365:406 */       (FuncionesUtiles.getAnio(historicoEmpleado.getFechaSalida()) == FuncionesUtiles.getAnio(historicoEmpleado.getFechaIngreso()))) {
/* 366:407 */       diasTrabajados = diasTrabajados - FuncionesUtiles.getDiaFecha(historicoEmpleado.getFechaIngreso()) + 1;
/* 367:    */     }
/* 368:411 */     pagoRolEmpleado.setDiasTrabajados(diasTrabajados);
/* 369:412 */     this.pagoRolEmpleadoDao.guardar(pagoRolEmpleado);
/* 370:    */     
/* 371:414 */     this.historicoEmpleadoDao.guardar(pagoRolEmpleado.getHistoricoEmpleadoFiniquito());
/* 372:415 */     this.pagoRolDao.guardar(pagoRolEmpleado.getPagoRol());
/* 373:    */   }
/* 374:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioFiniquitoEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */