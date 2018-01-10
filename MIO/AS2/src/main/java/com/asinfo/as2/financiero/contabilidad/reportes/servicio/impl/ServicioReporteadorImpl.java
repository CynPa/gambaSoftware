/*   1:    */ package com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.ReporteadorDao;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleReporteador;
/*   7:    */ import com.asinfo.as2.entities.DetalleReporteadorVariable;
/*   8:    */ import com.asinfo.as2.entities.Reporteador;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteador;
/*  13:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.math.RoundingMode;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.HashSet;
/*  20:    */ import java.util.Iterator;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import java.util.Set;
/*  24:    */ import javax.annotation.Resource;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.ejb.SessionContext;
/*  27:    */ import javax.ejb.Stateless;
/*  28:    */ import javax.ejb.TransactionAttribute;
/*  29:    */ import javax.ejb.TransactionAttributeType;
/*  30:    */ import javax.ejb.TransactionManagement;
/*  31:    */ import javax.ejb.TransactionManagementType;
/*  32:    */ import net.objecthunter.exp4j.Expression;
/*  33:    */ import net.objecthunter.exp4j.ExpressionBuilder;
/*  34:    */ 
/*  35:    */ @Stateless
/*  36:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  37:    */ public class ServicioReporteadorImpl
/*  38:    */   implements ServicioReporteador
/*  39:    */ {
/*  40:    */   @EJB
/*  41:    */   private ReporteadorDao reporteadorDao;
/*  42:    */   @EJB
/*  43:    */   private GenericoDao<DetalleReporteador> detalleReporteadorDao;
/*  44:    */   @EJB
/*  45:    */   private GenericoDao<DetalleReporteadorVariable> detalleReporteadorVariableDao;
/*  46:    */   @EJB
/*  47:    */   private ServicioCuentaContable servicioCuentaContable;
/*  48:    */   @Resource
/*  49:    */   protected SessionContext context;
/*  50:    */   
/*  51:    */   public List<Reporteador> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  52:    */   {
/*  53: 71 */     return this.reporteadorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List<Reporteador> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/*  57:    */   {
/*  58: 76 */     return this.reporteadorDao.obtenerListaCombo(sortField, sortOrder, filtros);
/*  59:    */   }
/*  60:    */   
/*  61:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  62:    */   public void guardar(Reporteador reporteador)
/*  63:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  64:    */   {
/*  65:    */     try
/*  66:    */     {
/*  67: 91 */       boolean indicadorNuevo = reporteador.getId() == 0;
/*  68: 93 */       if (indicadorNuevo) {
/*  69: 94 */         this.reporteadorDao.guardar(reporteador);
/*  70:    */       }
/*  71: 96 */       for (DetalleReporteadorVariable detalle : reporteador.getListaDetalleReporteadorVariable()) {
/*  72: 97 */         this.detalleReporteadorVariableDao.guardar(detalle);
/*  73:    */       }
/*  74: 99 */       guardarDetallesReporteador(reporteador, null);
/*  75:100 */       if (!indicadorNuevo) {
/*  76:101 */         this.reporteadorDao.guardar(reporteador);
/*  77:    */       }
/*  78:    */     }
/*  79:    */     catch (AS2Exception e)
/*  80:    */     {
/*  81:104 */       this.context.setRollbackOnly();
/*  82:105 */       throw e;
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:107 */       e.printStackTrace();
/*  87:108 */       this.context.setRollbackOnly();
/*  88:109 */       throw new AS2Exception(e.getMessage());
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  93:    */   private void guardarDetallesReporteador(Reporteador reporteador, DetalleReporteador detalleReporteadorPadre)
/*  94:    */     throws AS2Exception
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:116 */       boolean indicadorEliminar = (detalleReporteadorPadre != null) && (detalleReporteadorPadre.isEliminado());
/*  99:118 */       if (indicadorEliminar)
/* 100:    */       {
/* 101:119 */         eliminarDetallesReporteador(reporteador, detalleReporteadorPadre);
/* 102:    */       }
/* 103:    */       else
/* 104:    */       {
/* 105:122 */         List<DetalleReporteador> listaDetalleReporteador = null;
/* 106:123 */         if (detalleReporteadorPadre == null) {
/* 107:124 */           listaDetalleReporteador = reporteador.getListaDetalleReporteador();
/* 108:    */         } else {
/* 109:126 */           listaDetalleReporteador = detalleReporteadorPadre.getListaDetalleReporteadorHijo();
/* 110:    */         }
/* 111:130 */         if (detalleReporteadorPadre != null) {
/* 112:131 */           this.detalleReporteadorDao.guardar(detalleReporteadorPadre);
/* 113:    */         }
/* 114:135 */         for (DetalleReporteador detalleReporteador : listaDetalleReporteador) {
/* 115:136 */           guardarDetallesReporteador(reporteador, detalleReporteador);
/* 116:    */         }
/* 117:    */       }
/* 118:    */     }
/* 119:    */     catch (AS2Exception e)
/* 120:    */     {
/* 121:140 */       this.context.setRollbackOnly();
/* 122:141 */       throw e;
/* 123:    */     }
/* 124:    */     catch (Exception e)
/* 125:    */     {
/* 126:143 */       e.printStackTrace();
/* 127:144 */       this.context.setRollbackOnly();
/* 128:145 */       throw new AS2Exception(e.getMessage());
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 133:    */   public void eliminar(Reporteador reporteador)
/* 134:    */     throws AS2Exception
/* 135:    */   {
/* 136:    */     try
/* 137:    */     {
/* 138:158 */       reporteador = this.reporteadorDao.cargarDetalle(reporteador.getIdReporteador());
/* 139:    */       
/* 140:160 */       eliminarDetallesReporteador(reporteador, null);
/* 141:162 */       for (DetalleReporteadorVariable detalle : reporteador.getListaDetalleReporteadorVariable()) {
/* 142:163 */         this.detalleReporteadorVariableDao.eliminar(detalle);
/* 143:    */       }
/* 144:166 */       this.reporteadorDao.eliminar(reporteador);
/* 145:    */     }
/* 146:    */     catch (AS2Exception e)
/* 147:    */     {
/* 148:168 */       this.context.setRollbackOnly();
/* 149:169 */       throw e;
/* 150:    */     }
/* 151:    */     catch (Exception e)
/* 152:    */     {
/* 153:171 */       e.printStackTrace();
/* 154:172 */       this.context.setRollbackOnly();
/* 155:173 */       throw new AS2Exception(e.getMessage());
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 160:    */   private void eliminarDetallesReporteador(Reporteador reporteador, DetalleReporteador detalleReporteadorPadre)
/* 161:    */     throws AS2Exception
/* 162:    */   {
/* 163:    */     try
/* 164:    */     {
/* 165:181 */       List<DetalleReporteador> listaDetalleReporteador = null;
/* 166:182 */       if (detalleReporteadorPadre == null) {
/* 167:183 */         listaDetalleReporteador = reporteador.getListaDetalleReporteador();
/* 168:    */       } else {
/* 169:185 */         listaDetalleReporteador = detalleReporteadorPadre.getListaDetalleReporteadorHijo();
/* 170:    */       }
/* 171:189 */       for (DetalleReporteador detalleReporteador : listaDetalleReporteador) {
/* 172:190 */         eliminarDetallesReporteador(reporteador, detalleReporteador);
/* 173:    */       }
/* 174:194 */       if ((detalleReporteadorPadre != null) && (detalleReporteadorPadre.getId() != 0)) {
/* 175:195 */         this.detalleReporteadorDao.eliminar(detalleReporteadorPadre);
/* 176:    */       }
/* 177:    */     }
/* 178:    */     catch (AS2Exception e)
/* 179:    */     {
/* 180:198 */       this.context.setRollbackOnly();
/* 181:199 */       throw e;
/* 182:    */     }
/* 183:    */     catch (Exception e)
/* 184:    */     {
/* 185:201 */       e.printStackTrace();
/* 186:202 */       this.context.setRollbackOnly();
/* 187:203 */       throw new AS2Exception(e.getMessage());
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Reporteador buscarPorId(Integer id)
/* 192:    */   {
/* 193:209 */     return (Reporteador)this.reporteadorDao.buscarPorId(id);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Reporteador cargarDetalle(int idReporte)
/* 197:    */   {
/* 198:214 */     return this.reporteadorDao.cargarDetalle(idReporte);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public BigDecimal obtenerValorExpresion(DetalleReporteadorVariable detalleVariable, Date fechaDesde, Date fechaHasta, int idSucursal, Map<Integer, BigDecimal> mapaValorVariables)
/* 202:    */     throws AS2Exception, IllegalArgumentException, ArithmeticException
/* 203:    */   {
/* 204:219 */     Integer key = Integer.valueOf(detalleVariable.getId());
/* 205:220 */     String expresion = detalleVariable.getExpresion();
/* 206:    */     
/* 207:222 */     BigDecimal valorSaldoCuenta = BigDecimal.ZERO;
/* 208:224 */     if (mapaValorVariables == null) {
/* 209:225 */       mapaValorVariables = new HashMap();
/* 210:    */     }
/* 211:229 */     if (mapaValorVariables.containsKey(key)) {
/* 212:230 */       return (BigDecimal)mapaValorVariables.get(key);
/* 213:    */     }
/* 214:232 */     if ((detalleVariable.isIndicadorFormula()) && ((expresion == null) || (expresion.trim().isEmpty()))) {
/* 215:233 */       return BigDecimal.ZERO;
/* 216:    */     }
/* 217:237 */     if (!detalleVariable.isIndicadorFormula())
/* 218:    */     {
/* 219:239 */       List<CuentaContable> listaCuentas = new ArrayList();
/* 220:240 */       if (detalleVariable.getCuentaContable() != null)
/* 221:    */       {
/* 222:241 */         if (detalleVariable.getCuentaContable().isIndicadorMovimiento())
/* 223:    */         {
/* 224:242 */           listaCuentas.add(detalleVariable.getCuentaContable());
/* 225:    */         }
/* 226:    */         else
/* 227:    */         {
/* 228:245 */           Map<String, String> filters = new HashMap();
/* 229:246 */           filters.put("codigo", detalleVariable.getCuentaContable().getCodigo() + "%");
/* 230:247 */           filters.put("indicadorMovimiento", "true");
/* 231:248 */           filters.put("idOrganizacion", "" + detalleVariable.getIdOrganizacion());
/* 232:249 */           listaCuentas = this.servicioCuentaContable.obtenerListaCombo("codigo", true, filters);
/* 233:    */         }
/* 234:252 */         List<Object[]> saldos = this.servicioCuentaContable.obtenerValores(fechaDesde, fechaHasta, detalleVariable.getIdOrganizacion(), listaCuentas, detalleVariable
/* 235:253 */           .getValoresCalculo(), detalleVariable.getTipoCalculo(), false, idSucursal, null, null);
/* 236:255 */         for (Object[] objects : saldos) {
/* 237:256 */           if (objects[1] != null) {
/* 238:257 */             valorSaldoCuenta = valorSaldoCuenta.add((BigDecimal)objects[1]);
/* 239:    */           }
/* 240:    */         }
/* 241:261 */         valorSaldoCuenta = valorSaldoCuenta.setScale(2, RoundingMode.HALF_UP);
/* 242:    */         
/* 243:263 */         mapaValorVariables.put(key, valorSaldoCuenta);
/* 244:    */       }
/* 245:265 */       return valorSaldoCuenta;
/* 246:    */     }
/* 247:269 */     Map<String, Double> mapaVariables = new HashMap();
/* 248:270 */     Set<String> setVariables = new HashSet();
/* 249:271 */     for (DetalleReporteadorVariable detalleRelacionado : detalleVariable.getListaDetalleVariablesExpresion())
/* 250:    */     {
/* 251:272 */       BigDecimal valorVariableRelacionada = (BigDecimal)mapaValorVariables.get(Integer.valueOf(detalleRelacionado.getId()));
/* 252:273 */       if (valorVariableRelacionada == null) {
/* 253:274 */         valorVariableRelacionada = obtenerValorExpresion(detalleRelacionado, fechaDesde, fechaHasta, idSucursal, mapaValorVariables);
/* 254:    */       }
/* 255:276 */       mapaVariables.put(detalleRelacionado.getCodigo(), Double.valueOf(valorVariableRelacionada.doubleValue()));
/* 256:277 */       setVariables.add(detalleRelacionado.getCodigo());
/* 257:    */     }
/* 258:280 */     Expression expresionBuild = new ExpressionBuilder(expresion).variables(setVariables).build();
/* 259:281 */     expresionBuild.setVariables(mapaVariables);
/* 260:282 */     valorSaldoCuenta = new BigDecimal(expresionBuild.evaluate()).setScale(2, RoundingMode.HALF_UP);
/* 261:283 */     mapaValorVariables.put(key, valorSaldoCuenta);
/* 262:284 */     return valorSaldoCuenta;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public List<Object[]> getReporte(Reporteador reporteadorSeleccionado, Date fechaDesde, Date fechaHasta, int idSucursal)
/* 266:    */     throws AS2Exception, IllegalArgumentException, ArithmeticException
/* 267:    */   {
/* 268:291 */     reporteadorSeleccionado = cargarDetalle(reporteadorSeleccionado.getId());
/* 269:292 */     Map<Integer, BigDecimal> mapaValorVariables = new HashMap();
/* 270:    */     
/* 271:294 */     List<Object[]> resultado = new ArrayList();
/* 272:297 */     for (Iterator localIterator1 = reporteadorSeleccionado.getListaDetalleReporteadorVariable().iterator(); localIterator1.hasNext();)
/* 273:    */     {
/* 274:297 */       detalle1 = (DetalleReporteadorVariable)localIterator1.next();
/* 275:298 */       for (DetalleReporteadorVariable detalle2 : reporteadorSeleccionado.getListaDetalleReporteadorVariable()) {
/* 276:299 */         if ((detalle2.isIndicadorFormula()) && 
/* 277:300 */           (detalle2.getExpresion().contains(detalle1.getCodigo()))) {
/* 278:301 */           detalle2.getListaDetalleVariablesExpresion().add(detalle1);
/* 279:    */         }
/* 280:    */       }
/* 281:    */     }
/* 282:    */     DetalleReporteadorVariable detalle1;
/* 283:307 */     resultado.addAll(getReporteRecursivo(reporteadorSeleccionado, null, fechaDesde, fechaHasta, idSucursal, 0, mapaValorVariables));
/* 284:    */     
/* 285:309 */     return resultado;
/* 286:    */   }
/* 287:    */   
/* 288:    */   private List<Object[]> getReporteRecursivo(Reporteador reporteadorSeleccionado, DetalleReporteador detallePadre, Date fechaDesde, Date fechaHasta, int idSucursal, int nivel, Map<Integer, BigDecimal> mapaValorVariables)
/* 289:    */     throws AS2Exception, IllegalArgumentException, ArithmeticException
/* 290:    */   {
/* 291:315 */     List<DetalleReporteador> listaDetalleReporteador = null;
/* 292:316 */     if (detallePadre == null) {
/* 293:317 */       listaDetalleReporteador = reporteadorSeleccionado.getListaDetalleReporteador();
/* 294:    */     } else {
/* 295:319 */       listaDetalleReporteador = detallePadre.getListaDetalleReporteadorHijo();
/* 296:    */     }
/* 297:321 */     nivel++;
/* 298:    */     
/* 299:323 */     List<Object[]> resultado = new ArrayList();
/* 300:326 */     for (DetalleReporteador detalle : listaDetalleReporteador) {
/* 301:327 */       if (detalle.isActivo())
/* 302:    */       {
/* 303:328 */         DetalleReporteadorVariable detalleVariable = detalle.getDetalleReporteadorVariable();
/* 304:329 */         Object[] fila = new Object[4];
/* 305:330 */         fila[0] = Integer.valueOf(nivel);
/* 306:331 */         fila[1] = detalle.getNombre();
/* 307:332 */         fila[2] = detalle.getDescripcion();
/* 308:334 */         if (detalle.getDetalleReporteadorVariable() != null)
/* 309:    */         {
/* 310:335 */           BigDecimal valor = obtenerValorExpresion(detalleVariable, fechaDesde, fechaHasta, idSucursal, mapaValorVariables);
/* 311:336 */           fila[3] = valor;
/* 312:    */         }
/* 313:339 */         resultado.add(fila);
/* 314:    */         
/* 315:    */ 
/* 316:342 */         resultado
/* 317:343 */           .addAll(getReporteRecursivo(reporteadorSeleccionado, detalle, fechaDesde, fechaHasta, idSucursal, nivel, mapaValorVariables));
/* 318:    */       }
/* 319:    */     }
/* 320:347 */     return resultado;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List<DetalleReporteadorVariable> listaVariablesSinCuentaContable(Reporteador reporteador)
/* 324:    */     throws AS2Exception
/* 325:    */   {
/* 326:369 */     return this.reporteadorDao.getVariablesSinCuentaContable(reporteador);
/* 327:    */   }
/* 328:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioReporteadorImpl
 * JD-Core Version:    0.7.0.1
 */