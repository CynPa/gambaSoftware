/*   1:    */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ExtractoBancarioDao;
/*   4:    */ import com.asinfo.as2.dao.GenericoDao;
/*   5:    */ import com.asinfo.as2.dao.InterfazContableProcesoDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   8:    */ import com.asinfo.as2.entities.Asiento;
/*   9:    */ import com.asinfo.as2.entities.ConfiguracionExtractoBancario;
/*  10:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  11:    */ import com.asinfo.as2.entities.CuentaContable;
/*  12:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  13:    */ import com.asinfo.as2.entities.DetalleConfiguracionExtractoBancario;
/*  14:    */ import com.asinfo.as2.entities.DetalleExtractoBancario;
/*  15:    */ import com.asinfo.as2.entities.Documento;
/*  16:    */ import com.asinfo.as2.entities.ExtractoBancario;
/*  17:    */ import com.asinfo.as2.entities.FormaPago;
/*  18:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  19:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  20:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  21:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  22:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  23:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  24:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  25:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  26:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  27:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  28:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioExtractoBancario;
/*  29:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  30:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  31:    */ import com.asinfo.as2.servicio.tema.impl.ServicioGenericoImpl;
/*  32:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  33:    */ import java.io.InputStream;
/*  34:    */ import java.math.BigDecimal;
/*  35:    */ import java.math.RoundingMode;
/*  36:    */ import java.text.SimpleDateFormat;
/*  37:    */ import java.util.ArrayList;
/*  38:    */ import java.util.Collection;
/*  39:    */ import java.util.Date;
/*  40:    */ import java.util.HashMap;
/*  41:    */ import java.util.Iterator;
/*  42:    */ import java.util.List;
/*  43:    */ import java.util.Map;
/*  44:    */ import java.util.Set;
/*  45:    */ import java.util.TreeMap;
/*  46:    */ import javax.annotation.Resource;
/*  47:    */ import javax.ejb.EJB;
/*  48:    */ import javax.ejb.SessionContext;
/*  49:    */ import javax.ejb.Stateless;
/*  50:    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  51:    */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*  52:    */ 
/*  53:    */ @Stateless
/*  54:    */ public class ServicioExtractoBancarioImpl
/*  55:    */   extends ServicioGenericoImpl<InterfazContableProceso>
/*  56:    */   implements ServicioExtractoBancario
/*  57:    */ {
/*  58:    */   @EJB
/*  59:    */   private ServicioGenerico<ConfiguracionExtractoBancario> servicioConfiguracionExtractoBancario;
/*  60:    */   @EJB
/*  61:    */   private ExtractoBancarioDao extractoBancarioDao;
/*  62:    */   @EJB
/*  63:    */   private GenericoDao<DetalleExtractoBancario> detalleExtractoBancarioDao;
/*  64:    */   @EJB
/*  65:    */   private InterfazContableProcesoDao interfazContableProcesoDao;
/*  66:    */   @EJB
/*  67:    */   private ServicioAsiento servicioAsiento;
/*  68:    */   @EJB
/*  69:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  70:    */   @EJB
/*  71:    */   private ServicioSecuencia servicioSecuencia;
/*  72:    */   @EJB
/*  73:    */   private ServicioFormaPago servicioFormaPago;
/*  74:    */   @Resource
/*  75:    */   protected SessionContext context;
/*  76:    */   
/*  77:    */   public void guardar(InterfazContableProceso cargaExtracto)
/*  78:    */     throws AS2Exception
/*  79:    */   {
/*  80: 95 */     cargarSecuencia(cargaExtracto);
/*  81: 97 */     for (ExtractoBancario extracto : cargaExtracto.getListaExtractoBancario())
/*  82:    */     {
/*  83: 99 */       for (DetalleExtractoBancario detalleExtracto : extracto.getListaDetalleExtractoBancario()) {
/*  84:100 */         this.detalleExtractoBancarioDao.guardar(detalleExtracto);
/*  85:    */       }
/*  86:103 */       this.extractoBancarioDao.guardar(extracto);
/*  87:    */     }
/*  88:106 */     this.interfazContableProcesoDao.guardar(cargaExtracto);
/*  89:    */   }
/*  90:    */   
/*  91:    */   private void cargarSecuencia(InterfazContableProceso cargaExtracto)
/*  92:    */     throws AS2Exception
/*  93:    */   {
/*  94:111 */     if ((cargaExtracto.getNumero() == null) || (cargaExtracto.getNumero().equals(""))) {
/*  95:    */       try
/*  96:    */       {
/*  97:113 */         cargaExtracto.setNumero(this.servicioSecuencia.obtenerSecuenciaDocumento(cargaExtracto.getDocumento().getIdDocumento(), cargaExtracto
/*  98:114 */           .getFechaDesde()));
/*  99:    */       }
/* 100:    */       catch (ExcepcionAS2 e)
/* 101:    */       {
/* 102:116 */         throw new AS2Exception(e.getMessage());
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public InterfazContableProceso cargarDetalle(InterfazContableProceso interfazContableProceso)
/* 108:    */   {
/* 109:123 */     return this.extractoBancarioDao.cargarDetalle(interfazContableProceso);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public ConfiguracionExtractoBancario cargarDetalle(ConfiguracionExtractoBancario configuracionExtracto)
/* 113:    */   {
/* 114:128 */     return this.extractoBancarioDao.cargarDetalle(configuracionExtracto);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public InterfazContableProceso leerExtractoBancario(InterfazContableProceso interfaz, InputStream imInputStream, int filaInicial, CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 118:    */     throws ExcepcionAS2
/* 119:    */   {
/* 120:135 */     for (ExtractoBancario ex : interfaz.getListaExtractoBancario())
/* 121:    */     {
/* 122:136 */       ex.setEliminado(true);
/* 123:137 */       for (DetalleExtractoBancario deb : ex.getListaDetalleExtractoBancario()) {
/* 124:138 */         deb.setEliminado(true);
/* 125:    */       }
/* 126:    */     }
/* 127:142 */     Object listaFormatoFecha = new ArrayList();
/* 128:143 */     ((List)listaFormatoFecha).add("?MM/dd/yyyy");
/* 129:144 */     ((List)listaFormatoFecha).add("?dd/MM/yyyy");
/* 130:145 */     ((List)listaFormatoFecha).add("?yyyy/MM/dd");
/* 131:    */     
/* 132:147 */     Map<Date, List<DetalleExtractoBancario>> hmDetalleExtracto = new TreeMap();
/* 133:    */     
/* 134:149 */     ConfiguracionExtractoBancario configuracionExtractoBancario = this.servicioCuentaBancariaOrganizacion.getConfiguracionExtractoBancario(interfaz
/* 135:150 */       .getCuentaBancariaOrganizacion());
/* 136:    */     
/* 137:    */ 
/* 138:153 */     configuracionExtractoBancario = cargarDetalle(configuracionExtractoBancario);
/* 139:    */     
/* 140:155 */     List<DetalleConfiguracionExtractoBancario> criterios = configuracionExtractoBancario.getListaDetalleConfiguracionExtractoBancario();
/* 141:    */     
/* 142:157 */     int numeroCeldasVacias = 0;
/* 143:158 */     int filaActual = filaInicial;
/* 144:159 */     int columnaActual = 0;
/* 145:160 */     HSSFCell[] cabecera = new HSSFCell[0];
/* 146:161 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 147:    */     try
/* 148:    */     {
/* 149:164 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 150:165 */       leerCriterios(configuracionExtractoBancario, datos);
/* 151:    */       int colFecha;
/* 152:166 */       for (HSSFCell[] fila : datos)
/* 153:    */       {
/* 154:168 */         filaActual++;
/* 155:169 */         filaErronea = fila;
/* 156:171 */         if ((fila.length <= 2) || ((fila[0] == null) && (fila[1] == null) && (fila[2] == null)))
/* 157:    */         {
/* 158:172 */           numeroCeldasVacias++;
/* 159:    */         }
/* 160:    */         else
/* 161:    */         {
/* 162:176 */           if (numeroCeldasVacias >= 10) {
/* 163:    */             break;
/* 164:    */           }
/* 165:181 */           colFecha = configuracionExtractoBancario.getColumnaFecha().intValue();
/* 166:182 */           int colOperacion = configuracionExtractoBancario.getColumnaOperacion().intValue();
/* 167:183 */           int colMonto = configuracionExtractoBancario.getColumnaMonto().intValue();
/* 168:    */           
/* 169:    */ 
/* 170:186 */           Date fecha = null;
/* 171:187 */           columnaActual = colFecha - 1;
/* 172:    */           try
/* 173:    */           {
/* 174:190 */             fecha = fila[columnaActual].getDateCellValue();
/* 175:    */           }
/* 176:    */           catch (Exception ex)
/* 177:    */           {
/* 178:192 */             String formatoFecha = configuracionExtractoBancario.getFormatoFecha();
/* 179:193 */             SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
/* 180:194 */             fecha = sdf.parse(fila[columnaActual].getStringCellValue());
/* 181:    */           }
/* 182:196 */           fecha = FuncionesUtiles.setAtributoFecha(fecha);
/* 183:    */           
/* 184:    */ 
/* 185:199 */           int dia = FuncionesUtiles.obtenerDiaFecha(fecha);
/* 186:200 */           String criterioBusqueda = fila[(fila.length - 2)].getStringCellValue().trim();
/* 187:201 */           String criterioBusqueda2 = fila[(fila.length - 1)].getStringCellValue().trim();
/* 188:202 */           int operacion = 1;
/* 189:203 */           String strOperacion = fila[(columnaActual = colOperacion - 1)].getStringCellValue();
/* 190:204 */           if ((strOperacion.equals("-")) || (strOperacion.equals("N/D"))) {
/* 191:205 */             operacion = -1;
/* 192:    */           }
/* 193:209 */           double montoD = 0.0D;
/* 194:210 */           columnaActual = colMonto - 1;
/* 195:211 */           if (fila[columnaActual].getCellType() == 1)
/* 196:    */           {
/* 197:212 */             String strMonto = fila[columnaActual].getStringCellValue();
/* 198:213 */             strMonto = strMonto.replace(".", "").replace(",", "");
/* 199:214 */             montoD = Integer.parseInt(strMonto) / 100.0D;
/* 200:    */           }
/* 201:    */           else
/* 202:    */           {
/* 203:216 */             montoD = fila[columnaActual].getNumericCellValue();
/* 204:    */           }
/* 205:219 */           BigDecimal monto = new BigDecimal(montoD * operacion).setScale(2, RoundingMode.HALF_UP);
/* 206:    */           
/* 207:221 */           String descripcion = "";
/* 208:222 */           String descripcion2Banco = "";
/* 209:223 */           String descripcion2Contrapartida = "";
/* 210:224 */           boolean modificaCuentaContable = false;
/* 211:    */           
/* 212:226 */           CuentaContable cuentaContable1 = null;
/* 213:227 */           CuentaContable cuentaContable2 = null;
/* 214:    */           
/* 215:    */ 
/* 216:230 */           DetalleConfiguracionExtractoBancario criterio = getCriterio(criterios, dia, criterioBusqueda, criterioBusqueda2, monto);
/* 217:232 */           if (criterio != null)
/* 218:    */           {
/* 219:233 */             cuentaContable1 = criterio.getCuentaContable();
/* 220:234 */             cuentaContable2 = criterio.getCuentaContable2();
/* 221:235 */             modificaCuentaContable = criterio.isIndicadorModificaCuentaContable();
/* 222:    */             
/* 223:237 */             descripcion = setearFecha((List)listaFormatoFecha, fecha, criterio.getDescripcion());
/* 224:238 */             descripcion2Banco = setearFecha((List)listaFormatoFecha, fecha, criterio.getDescripcion2CuentaBanco());
/* 225:239 */             descripcion2Contrapartida = setearFecha((List)listaFormatoFecha, fecha, criterio.getDescripcion2CuentaContrapartida());
/* 226:242 */             if ((criterio.getCriterioDeBusqueda() != null) && (!criterio.getCriterioDeBusqueda().isEmpty()))
/* 227:    */             {
/* 228:243 */               descripcion = descripcion.replace("?c1", criterio.getCriterioDeBusqueda());
/* 229:244 */               descripcion2Banco = descripcion2Banco.replace("?c1", criterio.getCriterioDeBusqueda());
/* 230:245 */               descripcion2Contrapartida = descripcion2Contrapartida.replace("?c1", criterio.getCriterioDeBusqueda());
/* 231:    */             }
/* 232:249 */             if ((criterio.getCriterioDeBusqueda2() != null) && (!criterio.getCriterioDeBusqueda2().isEmpty()))
/* 233:    */             {
/* 234:250 */               descripcion = descripcion.replace("?c2", criterio.getCriterioDeBusqueda2());
/* 235:251 */               descripcion2Banco = descripcion2Banco.replace("?c2", criterio.getCriterioDeBusqueda2());
/* 236:252 */               descripcion2Contrapartida = descripcion2Contrapartida.replace("?c2", criterio.getCriterioDeBusqueda2());
/* 237:    */             }
/* 238:253 */             else if ((criterioBusqueda2 != null) && (!criterioBusqueda2.isEmpty()))
/* 239:    */             {
/* 240:255 */               descripcion = descripcion.replace("?c2", criterioBusqueda2);
/* 241:256 */               descripcion2Banco = descripcion2Banco.replace("?c2", criterioBusqueda2);
/* 242:257 */               descripcion2Contrapartida = descripcion2Contrapartida.replace("?c2", criterioBusqueda2);
/* 243:    */             }
/* 244:    */           }
/* 245:262 */           if ((criterio == null) || (criterio.isIndicadorContabilizar()))
/* 246:    */           {
/* 247:264 */             String[] ArrayDescripcion = descripcion.split("::");
/* 248:265 */             String[] ArrayDescripcion2Banco = descripcion2Banco.split("::");
/* 249:266 */             String[] ArrayDescripcion2Contrapartida = descripcion2Contrapartida.split("::");
/* 250:    */             
/* 251:268 */             BigDecimal monto1 = monto;
/* 252:269 */             BigDecimal monto2 = BigDecimal.ZERO;
/* 253:270 */             String criterioBusquedaP1 = criterioBusqueda;
/* 254:271 */             String criterioBusquedaP2 = criterioBusqueda;
/* 255:273 */             if ((criterio != null) && (criterio.getPorcentaje().intValue() < 100))
/* 256:    */             {
/* 257:274 */               monto1 = FuncionesUtiles.porcentaje(monto, criterio.getPorcentaje().intValue());
/* 258:275 */               monto2 = monto.subtract(monto1);
/* 259:    */               
/* 260:277 */               criterioBusquedaP1 = criterioBusquedaP1 + " " + criterio.getPorcentaje() + "%";
/* 261:278 */               criterioBusquedaP2 = criterioBusquedaP2 + " " + (100 - criterio.getPorcentaje().intValue()) + "%";
/* 262:    */             }
/* 263:281 */             List<DetalleExtractoBancario> listaDetalle = (List)hmDetalleExtracto.get(fecha);
/* 264:282 */             if (listaDetalle == null)
/* 265:    */             {
/* 266:283 */               listaDetalle = new ArrayList();
/* 267:284 */               hmDetalleExtracto.put(fecha, listaDetalle);
/* 268:    */             }
/* 269:287 */             DetalleExtractoBancario deb = new DetalleExtractoBancario(cuentaContable1, criterioBusquedaP1, criterioBusqueda2, monto1, ArrayDescripcion[0], ArrayDescripcion2Banco[0], ArrayDescripcion2Contrapartida[0], modificaCuentaContable);
/* 270:    */             
/* 271:289 */             listaDetalle.add(deb);
/* 272:291 */             if (monto2.compareTo(BigDecimal.ZERO) != 0)
/* 273:    */             {
/* 274:293 */               if (ArrayDescripcion.length > 1) {
/* 275:294 */                 descripcion = ArrayDescripcion[1];
/* 276:    */               }
/* 277:297 */               if (ArrayDescripcion2Banco.length > 1) {
/* 278:298 */                 descripcion2Banco = ArrayDescripcion2Banco[1];
/* 279:    */               }
/* 280:301 */               if (ArrayDescripcion2Contrapartida.length > 1) {
/* 281:302 */                 descripcion2Contrapartida = ArrayDescripcion2Contrapartida[1];
/* 282:    */               }
/* 283:305 */               DetalleExtractoBancario deb2 = new DetalleExtractoBancario(cuentaContable2, criterioBusquedaP2, criterioBusqueda2, monto2, descripcion, descripcion2Banco, descripcion2Contrapartida, modificaCuentaContable);
/* 284:    */               
/* 285:    */ 
/* 286:308 */               listaDetalle.add(deb2);
/* 287:    */             }
/* 288:    */           }
/* 289:    */         }
/* 290:    */       }
/* 291:313 */       Object listaExtractoBancario = new ArrayList();
/* 292:314 */       for (Date fecha : hmDetalleExtracto.keySet())
/* 293:    */       {
/* 294:315 */         ExtractoBancario extracto = new ExtractoBancario();
/* 295:316 */         extracto.setInterfazContableProceso(interfaz);
/* 296:317 */         extracto.setFecha(fecha);
/* 297:318 */         extracto.setDescripcion("Extracto bancario...");
/* 298:319 */         extracto.setListaDetalleExtractoBancario((List)hmDetalleExtracto.get(fecha));
/* 299:321 */         for (DetalleExtractoBancario da : extracto.getListaDetalleExtractoBancario()) {
/* 300:322 */           da.setExtractoBancario(extracto);
/* 301:    */         }
/* 302:325 */         ((List)listaExtractoBancario).add(extracto);
/* 303:    */       }
/* 304:328 */       interfaz.getListaExtractoBancario().addAll((Collection)listaExtractoBancario);
/* 305:    */       
/* 306:330 */       return interfaz;
/* 307:    */     }
/* 308:    */     catch (IllegalStateException e)
/* 309:    */     {
/* 310:333 */       e.printStackTrace();
/* 311:334 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", e.getMessage() + " " + (e.getCause() == null ? "" : e.getCause()));
/* 312:    */     }
/* 313:    */     catch (IllegalArgumentException e)
/* 314:    */     {
/* 315:336 */       e.printStackTrace();
/* 316:337 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", e.getMessage() + " " + (e.getCause() == null ? "" : e.getCause()));
/* 317:    */     }
/* 318:    */     catch (Exception e)
/* 319:    */     {
/* 320:339 */       e.printStackTrace();
/* 321:340 */       throw new ExcepcionAS2(e);
/* 322:    */     }
/* 323:    */   }
/* 324:    */   
/* 325:    */   private String setearFecha(List<String> listaFormatoFecha, Date fecha, String descripcion)
/* 326:    */   {
/* 327:346 */     for (String formato : listaFormatoFecha) {
/* 328:347 */       if (descripcion.contains(formato))
/* 329:    */       {
/* 330:348 */         SimpleDateFormat df = new SimpleDateFormat(formato.replace("?", ""));
/* 331:349 */         return descripcion.replace(formato, df.format(fecha));
/* 332:    */       }
/* 333:    */     }
/* 334:352 */     return descripcion;
/* 335:    */   }
/* 336:    */   
/* 337:    */   private DetalleConfiguracionExtractoBancario getCriterio(List<DetalleConfiguracionExtractoBancario> criterios, int dia, String criterioBusqueda, String criterioBusqueda2, BigDecimal valor)
/* 338:    */   {
/* 339:357 */     criterioBusqueda2 = criterioBusqueda2 == null ? "" : criterioBusqueda2;
/* 340:358 */     DetalleConfiguracionExtractoBancario criterio = null;
/* 341:359 */     int operacion = valor.compareTo(BigDecimal.ZERO) > 0 ? 1 : -1;
/* 342:360 */     valor = valor.abs();
/* 343:361 */     for (DetalleConfiguracionExtractoBancario dceb : criterios)
/* 344:    */     {
/* 345:363 */       boolean validaOperacion = (!dceb.isIndicadorValidarOperacion()) || (operacion == dceb.getOperacion());
/* 346:364 */       boolean criterio1 = criterioBusqueda.equals(dceb.getCriterioDeBusqueda());
/* 347:365 */       boolean criterio2 = dceb.getCriterioDeBusqueda2().isEmpty();
/* 348:369 */       if ((validaOperacion) && (criterio1) && (!criterio2) && (criterioBusqueda2.contains(dceb.getCriterioDeBusqueda2())) && 
/* 349:370 */         (buscarCriterio(dceb.getDia(), dceb.getMonto(), dceb.getOperacionMonto(), dceb.getOperacionDia(), valor, dia).booleanValue()))
/* 350:    */       {
/* 351:371 */         criterio = dceb;
/* 352:372 */         break;
/* 353:    */       }
/* 354:385 */       if ((validaOperacion) && (criterio1) && (criterio2) && 
/* 355:386 */         (buscarCriterio(dceb.getDia(), dceb.getMonto(), dceb.getOperacionMonto(), dceb.getOperacionDia(), valor, dia).booleanValue()))
/* 356:    */       {
/* 357:387 */         criterio = dceb;
/* 358:388 */         break;
/* 359:    */       }
/* 360:    */     }
/* 361:392 */     return criterio;
/* 362:    */   }
/* 363:    */   
/* 364:    */   private Boolean buscarCriterio(int diaDB, BigDecimal monto, OperacionEnum operacionMonto, OperacionEnum operacionDia, BigDecimal valor, int dia)
/* 365:    */   {
/* 366:396 */     if ((monto.compareTo(BigDecimal.ZERO) == 0) && (diaDB == 0)) {
/* 367:397 */       return Boolean.valueOf(true);
/* 368:    */     }
/* 369:400 */     if ((monto.compareTo(BigDecimal.ZERO) > 0) && (diaDB == 0))
/* 370:    */     {
/* 371:401 */       if ((valor.compareTo(monto) >= 0) && (operacionMonto == OperacionEnum.MAYOR_IGUAL)) {
/* 372:402 */         return Boolean.valueOf(true);
/* 373:    */       }
/* 374:403 */       if ((valor.compareTo(monto) < 0) && (operacionMonto == OperacionEnum.MENOR)) {
/* 375:404 */         return Boolean.valueOf(true);
/* 376:    */       }
/* 377:    */     }
/* 378:407 */     else if ((monto.compareTo(BigDecimal.ZERO) > 0) && (diaDB > 0))
/* 379:    */     {
/* 380:409 */       if ((valor.compareTo(monto) >= 0) && (operacionMonto == OperacionEnum.MAYOR_IGUAL) && (dia >= diaDB) && (operacionDia == OperacionEnum.MAYOR_IGUAL)) {
/* 381:411 */         return Boolean.valueOf(true);
/* 382:    */       }
/* 383:412 */       if ((valor.compareTo(monto) >= 0) && (operacionMonto == OperacionEnum.MAYOR_IGUAL) && (dia < diaDB) && (operacionDia == OperacionEnum.MENOR)) {
/* 384:414 */         return Boolean.valueOf(true);
/* 385:    */       }
/* 386:415 */       if ((valor.compareTo(monto) < 0) && (operacionMonto == OperacionEnum.MENOR) && (dia >= diaDB) && (operacionDia == OperacionEnum.MAYOR_IGUAL)) {
/* 387:417 */         return Boolean.valueOf(true);
/* 388:    */       }
/* 389:418 */       if ((valor.compareTo(monto) < 0) && (operacionMonto == OperacionEnum.MENOR) && (dia < diaDB) && (operacionDia == OperacionEnum.MENOR)) {
/* 390:419 */         return Boolean.valueOf(true);
/* 391:    */       }
/* 392:    */     }
/* 393:422 */     else if ((monto.compareTo(BigDecimal.ZERO) == 0) && (diaDB > 0))
/* 394:    */     {
/* 395:424 */       if ((dia >= diaDB) && (operacionDia == OperacionEnum.MAYOR_IGUAL)) {
/* 396:425 */         return Boolean.valueOf(true);
/* 397:    */       }
/* 398:426 */       if ((dia < diaDB) && (operacionDia == OperacionEnum.MENOR)) {
/* 399:427 */         return Boolean.valueOf(true);
/* 400:    */       }
/* 401:    */     }
/* 402:432 */     return Boolean.valueOf(false);
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void contabilizar(InterfazContableProceso interfaz)
/* 406:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 407:    */   {
/* 408:438 */     AS2Exception e = null;
/* 409:441 */     for (Iterator localIterator1 = interfaz.getListaExtractoBancario().iterator(); localIterator1.hasNext();)
/* 410:    */     {
/* 411:441 */       extracto = (ExtractoBancario)localIterator1.next();
/* 412:442 */       for (DetalleExtractoBancario detalleExtracto : extracto.getListaDetalleExtractoBancario())
/* 413:    */       {
/* 414:444 */         if (detalleExtracto.getCuentaContable() == null) {
/* 415:445 */           e = AS2Exception.agregarMensaje(e, "com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioExtractoBancarioImpl.SIN_CUENTA_EXTRACTO_BANCARIO", new String[] { detalleExtracto.getCriterioDeBusqueda(), detalleExtracto
/* 416:446 */             .getMonto().toString() });
/* 417:    */         }
/* 418:449 */         if (detalleExtracto.getDescripcion().isEmpty()) {
/* 419:450 */           e = AS2Exception.agregarMensaje(e, "com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioExtractoBancarioImpl.SIN_DESRIPCION_EXTRACTO_BANCARIO", new String[] { detalleExtracto.getCriterioDeBusqueda(), detalleExtracto
/* 420:451 */             .getMonto().toString() });
/* 421:    */         }
/* 422:454 */         if (detalleExtracto.getDescripcion2CuentaBanco().isEmpty()) {
/* 423:455 */           e = AS2Exception.agregarMensaje(e, "com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioExtractoBancarioImpl.SIN_DESRIPCION_EXTRACTO_BANCARIO", new String[] { detalleExtracto.getCriterioDeBusqueda(), detalleExtracto
/* 424:456 */             .getMonto().toString() });
/* 425:    */         }
/* 426:459 */         if (detalleExtracto.getDescripcion2CuentaContrapartida().isEmpty()) {
/* 427:460 */           e = AS2Exception.agregarMensaje(e, "com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioExtractoBancarioImpl.SIN_DESRIPCION_EXTRACTO_BANCARIO", new String[] { detalleExtracto.getCriterioDeBusqueda(), detalleExtracto
/* 428:461 */             .getMonto().toString() });
/* 429:    */         }
/* 430:    */       }
/* 431:    */     }
/* 432:466 */     if (e != null) {
/* 433:467 */       throw e;
/* 434:    */     }
/* 435:469 */     int count = 0;
/* 436:471 */     for (ExtractoBancario extracto = interfaz.getListaExtractoBancario().iterator(); extracto.hasNext();)
/* 437:    */     {
/* 438:471 */       extracto = (ExtractoBancario)extracto.next();
/* 439:472 */       asiento = new Asiento();
/* 440:473 */       asiento.setFecha(extracto.getFecha());
/* 441:474 */       asiento.setIdOrganizacion(interfaz.getIdOrganizacion());
/* 442:475 */       asiento.setSucursal(interfaz.getSucursal());
/* 443:476 */       TipoAsiento tipoAsiento = interfaz.getDocumento().getTipoAsiento();
/* 444:477 */       asiento.setTipoAsiento(tipoAsiento);
/* 445:478 */       asiento.setEstado(Estado.ELABORADO);
/* 446:479 */       asiento.setDocumentoOrigen(interfaz.getDocumento());
/* 447:480 */       asiento.setConcepto(interfaz.getDocumento().getNombre() + (interfaz.getNumero() == null ? "" : new StringBuilder().append(" ").append(interfaz.getNumero()).toString()) + interfaz
/* 448:481 */         .getCuentaBancariaOrganizacion().getNombreCompleto());
/* 449:    */       
/* 450:483 */       extracto.setAsiento(asiento);
/* 451:484 */       for (DetalleExtractoBancario detalleExtracto : extracto.getListaDetalleExtractoBancario())
/* 452:    */       {
/* 453:488 */         DetalleAsiento daBanco = new DetalleAsiento(asiento, interfaz.getCuentaBancariaOrganizacion().getCuentaContableBanco(), detalleExtracto.getMonto(), detalleExtracto.getDescripcion(), detalleExtracto.getDescripcion2CuentaBanco());
/* 454:489 */         if (daBanco.getCuentaContable().getTipoCuentaContable() == TipoCuentaContable.BANCO)
/* 455:    */         {
/* 456:490 */           MovimientoBancario movBancario = new MovimientoBancario();
/* 457:491 */           movBancario.setCuentaBancariaOrganizacion(interfaz.getCuentaBancariaOrganizacion());
/* 458:492 */           movBancario.setValor(detalleExtracto.getMonto());
/* 459:493 */           movBancario.setDocumentoReferencia(interfaz.getNumero() + "-" + count);
/* 460:494 */           movBancario.setDocumento(interfaz.getDocumento());
/* 461:495 */           movBancario.setDetalleAsiento(daBanco);
/* 462:496 */           movBancario.setFecha(extracto.getFecha());
/* 463:497 */           movBancario.setEstado(Estado.ELABORADO);
/* 464:498 */           movBancario.setFormaPago((FormaPago)this.servicioFormaPago.obtenerListaCombo("predeterminado", false, null).get(0));
/* 465:499 */           daBanco.setMovimientoBancario(movBancario);
/* 466:500 */           count++;
/* 467:    */         }
/* 468:505 */         DetalleAsiento da = new DetalleAsiento(asiento, detalleExtracto.getCuentaContable(), detalleExtracto.getMonto().negate(), detalleExtracto.getDescripcion(), detalleExtracto.getDescripcion2CuentaContrapartida());
/* 469:    */         
/* 470:507 */         asiento.getListaDetalleAsiento().add(da);
/* 471:508 */         if (da.getCuentaContable().getTipoCuentaContable() == TipoCuentaContable.BANCO)
/* 472:    */         {
/* 473:509 */           MovimientoBancario movBancario = new MovimientoBancario();
/* 474:    */           
/* 475:    */ 
/* 476:    */ 
/* 477:513 */           CuentaBancariaOrganizacion cuentaBancariaContrapartida = this.servicioCuentaBancariaOrganizacion.buscarPorCuentaContable(da.getCuentaContable().getIdCuentaContable());
/* 478:514 */           if (cuentaBancariaContrapartida == null) {
/* 479:515 */             throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioExtractoBancarioImpl.CONFIGURAR_CUENTA_BANCARIA_CONTRAPARTIDA", new String[] { "" });
/* 480:    */           }
/* 481:517 */           movBancario.setCuentaBancariaOrganizacion(cuentaBancariaContrapartida);
/* 482:518 */           movBancario.setValor(detalleExtracto.getMonto().negate());
/* 483:519 */           movBancario.setDocumentoReferencia(interfaz.getNumero() + "-" + count);
/* 484:520 */           movBancario.setDocumento(interfaz.getDocumento());
/* 485:521 */           movBancario.setDetalleAsiento(da);
/* 486:522 */           movBancario.setFecha(extracto.getFecha());
/* 487:523 */           movBancario.setEstado(Estado.ELABORADO);
/* 488:524 */           movBancario.setFormaPago((FormaPago)this.servicioFormaPago.obtenerListaCombo("predeterminado", false, null).get(0));
/* 489:525 */           da.setMovimientoBancario(movBancario);
/* 490:526 */           count++;
/* 491:    */         }
/* 492:528 */         asiento.getListaDetalleAsiento().add(daBanco);
/* 493:    */       }
/* 494:    */     }
/* 495:    */     try
/* 496:    */     {
/* 497:    */       ExtractoBancario extracto;
/* 498:    */       Asiento asiento;
/* 499:533 */       for (ExtractoBancario extracto : interfaz.getListaExtractoBancario())
/* 500:    */       {
/* 501:534 */         this.servicioAsiento.guardar(extracto.getAsiento());
/* 502:535 */         this.extractoBancarioDao.guardar(extracto);
/* 503:    */       }
/* 504:538 */       interfaz.setEstado(Estado.CONTABILIZADO);
/* 505:539 */       interfaz.setFechaContabilizacion(new Date());
/* 506:540 */       this.interfazContableProcesoDao.guardar(interfaz);
/* 507:    */     }
/* 508:    */     catch (ExcepcionAS2Financiero ex)
/* 509:    */     {
/* 510:543 */       this.context.setRollbackOnly();
/* 511:544 */       throw ex;
/* 512:    */     }
/* 513:    */     catch (ExcepcionAS2 ex)
/* 514:    */     {
/* 515:546 */       this.context.setRollbackOnly();
/* 516:547 */       throw ex;
/* 517:    */     }
/* 518:    */     catch (Exception ex)
/* 519:    */     {
/* 520:549 */       this.context.setRollbackOnly();
/* 521:550 */       throw new AS2Exception(ex.getMessage());
/* 522:    */     }
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void leerCriterios(ConfiguracionExtractoBancario configuracionExtractoBancario, HSSFCell[][] datos)
/* 526:    */     throws ExcepcionAS2
/* 527:    */   {
/* 528:556 */     List<DetalleConfiguracionExtractoBancario> criterios = configuracionExtractoBancario.getListaDetalleConfiguracionExtractoBancario();
/* 529:    */     
/* 530:558 */     int i = 0;
/* 531:559 */     Map<Integer, HSSFCell[]> hmDatos = new HashMap();
/* 532:    */     int j;
/* 533:560 */     for (HSSFCell[] fila : datos)
/* 534:    */     {
/* 535:562 */       HSSFCell[] filaNew = new HSSFCell[fila.length + 2];
/* 536:563 */       for (j = 0; j < fila.length; j++) {
/* 537:564 */         filaNew[j] = fila[j];
/* 538:    */       }
/* 539:567 */       filaNew[fila.length] = filaNew[0].getRow().createCell(fila.length);
/* 540:568 */       filaNew[(fila.length + 1)] = filaNew[0].getRow().createCell(fila.length + 1);
/* 541:    */       
/* 542:570 */       filaNew[fila.length].setCellValue("NO_DEFINIDO");
/* 543:571 */       filaNew[(fila.length + 1)].setCellValue("");
/* 544:    */       
/* 545:573 */       datos[i] = filaNew;
/* 546:574 */       hmDatos.put(Integer.valueOf(i), filaNew);
/* 547:    */       
/* 548:576 */       i++;
/* 549:    */     }
/* 550:579 */     for (??? = criterios.iterator(); ((Iterator)???).hasNext();)
/* 551:    */     {
/* 552:579 */       DetalleConfiguracionExtractoBancario criterio = (DetalleConfiguracionExtractoBancario)((Iterator)???).next();
/* 553:580 */       int colCriterio1 = criterio.getColumnaCriterioDeBusqueda();
/* 554:581 */       Integer colCriterio2 = criterio.getColumnaCriterioDeBusqueda2();
/* 555:    */       
/* 556:583 */       List<Integer> listaRemover = new ArrayList();
/* 557:584 */       for (j = hmDatos.keySet().iterator(); j.hasNext();)
/* 558:    */       {
/* 559:584 */         int k = ((Integer)j.next()).intValue();
/* 560:    */         
/* 561:586 */         HSSFCell[] fila = (HSSFCell[])hmDatos.get(Integer.valueOf(k));
/* 562:    */         
/* 563:588 */         String criterioBusqueda1 = "";
/* 564:589 */         String criterioBusqueda2 = "";
/* 565:591 */         if (fila[(colCriterio1 - 1)] != null)
/* 566:    */         {
/* 567:592 */           fila[(colCriterio1 - 1)].setCellType(1);
/* 568:593 */           criterioBusqueda1 = fila[(colCriterio1 - 1)].getStringCellValue();
/* 569:    */         }
/* 570:596 */         if ((colCriterio2 != null) && (colCriterio2.intValue() > 0) && (fila[(colCriterio2.intValue() - 1)] != null) && 
/* 571:597 */           (criterio.getCriterioDeBusqueda2().contains(criterioBusqueda2)))
/* 572:    */         {
/* 573:598 */           fila[(colCriterio2.intValue() - 1)].setCellType(1);
/* 574:599 */           criterioBusqueda2 = fila[(colCriterio2.intValue() - 1)].getStringCellValue();
/* 575:    */         }
/* 576:603 */         if (criterioBusqueda1.equals(criterio.getCriterioDeBusqueda()))
/* 577:    */         {
/* 578:605 */           fila[(fila.length - 2)].setCellValue(criterioBusqueda1);
/* 579:606 */           fila[(fila.length - 1)].setCellValue(criterioBusqueda2 == null ? "" : criterioBusqueda2);
/* 580:    */           
/* 581:608 */           listaRemover.add(Integer.valueOf(k));
/* 582:    */         }
/* 583:    */       }
/* 584:612 */       for (j = listaRemover.iterator(); j.hasNext();)
/* 585:    */       {
/* 586:612 */         int k = ((Integer)j.next()).intValue();
/* 587:613 */         hmDatos.remove(Integer.valueOf(k));
/* 588:    */       }
/* 589:    */     }
/* 590:    */   }
/* 591:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioExtractoBancarioImpl
 * JD-Core Version:    0.7.0.1
 */