/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ComponenteCostoDao;
/*   4:    */ import com.asinfo.as2.dao.GenericoDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioComponenteCosto;
/*   6:    */ import com.asinfo.as2.entities.ComponenteCosto;
/*   7:    */ import com.asinfo.as2.entities.CuentaContable;
/*   8:    */ import com.asinfo.as2.entities.DetalleComponenteCosto;
/*   9:    */ import com.asinfo.as2.entities.DetalleComponenteCostoDistribucion;
/*  10:    */ import com.asinfo.as2.entities.DimensionContable;
/*  11:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoComponenteCostoEnum;
/*  14:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  15:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  17:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioCostosDeFabricacion;
/*  18:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  19:    */ import java.math.BigDecimal;
/*  20:    */ import java.math.RoundingMode;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.HashSet;
/*  25:    */ import java.util.Iterator;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import java.util.Set;
/*  29:    */ import javax.annotation.Resource;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.ejb.SessionContext;
/*  32:    */ import javax.ejb.Stateless;
/*  33:    */ import javax.ejb.TransactionAttribute;
/*  34:    */ import javax.ejb.TransactionAttributeType;
/*  35:    */ import javax.ejb.TransactionManagement;
/*  36:    */ import javax.ejb.TransactionManagementType;
/*  37:    */ 
/*  38:    */ @Stateless
/*  39:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  40:    */ public class ServicioComponenteCostoImpl
/*  41:    */   implements ServicioComponenteCosto
/*  42:    */ {
/*  43:    */   @Resource
/*  44:    */   protected SessionContext context;
/*  45:    */   @EJB
/*  46:    */   private ComponenteCostoDao componenteCostoDao;
/*  47:    */   @EJB
/*  48:    */   private GenericoDao<DetalleComponenteCosto> detalleComponenteCostoDao;
/*  49:    */   @EJB
/*  50:    */   private GenericoDao<DetalleComponenteCostoDistribucion> detalleComponenteCostoDistribucionDao;
/*  51:    */   @EJB
/*  52:    */   private ServicioCuentaContable servicioCuentaContable;
/*  53:    */   @EJB
/*  54:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  55:    */   @EJB
/*  56:    */   private ServicioCostosDeFabricacion servicioCostosDeFabricacion;
/*  57:    */   
/*  58:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  59:    */   public void guardar(ComponenteCosto componenteCosto)
/*  60:    */     throws AS2Exception
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64: 84 */       validar(componenteCosto);
/*  65: 85 */       this.componenteCostoDao.guardar(componenteCosto);
/*  66: 86 */       for (DetalleComponenteCosto detalle : componenteCosto.getListaDetalleComponenteCosto())
/*  67:    */       {
/*  68: 87 */         if ((detalle.getCuentaContableCierre() != null) && (detalle.getCuentaContableCierre().getId() == 0)) {
/*  69: 88 */           detalle.setCuentaContableCierre(null);
/*  70:    */         }
/*  71: 90 */         detalle.setComponenteCosto(componenteCosto);
/*  72: 91 */         this.detalleComponenteCostoDao.guardar(detalle);
/*  73:    */       }
/*  74: 93 */       for (DetalleComponenteCostoDistribucion detalle : componenteCosto.getListaDetalleComponenteCostoDistribucion()) {
/*  75: 94 */         this.detalleComponenteCostoDistribucionDao.guardar(detalle);
/*  76:    */       }
/*  77:    */     }
/*  78:    */     catch (AS2Exception e)
/*  79:    */     {
/*  80: 97 */       this.context.setRollbackOnly();
/*  81: 98 */       throw e;
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85:100 */       this.context.setRollbackOnly();
/*  86:101 */       throw new AS2Exception(e.getMessage());
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   private void validar(ComponenteCosto componenteCosto)
/*  91:    */     throws AS2Exception
/*  92:    */   {
/*  93:107 */     Map<String, String> filtro = new HashMap();
/*  94:108 */     filtro.put("idOrganizacion", componenteCosto.getIdOrganizacion() + "");
/*  95:109 */     filtro.put("codigo", "=" + componenteCosto.getCodigo());
/*  96:110 */     List<ComponenteCosto> lista = obtenerListaCombo("codigo", true, filtro);
/*  97:111 */     for (ComponenteCosto base : lista) {
/*  98:112 */       if (base.getId() != componenteCosto.getId()) {
/*  99:113 */         throw new AS2Exception("msg_error_codigo_repetido", new String[] { componenteCosto.getCodigo() });
/* 100:    */       }
/* 101:    */     }
/* 102:118 */     Object listaCuentaContableUtilizadas = this.componenteCostoDao.obtenerCuentasContableUtilizadas(componenteCosto.getIdOrganizacion(), componenteCosto, true);
/* 103:    */     
/* 104:120 */     Set<Integer> setIdCuentaContable = new HashSet();
/* 105:121 */     for (CuentaContable cuenta : (List)listaCuentaContableUtilizadas) {
/* 106:122 */       setIdCuentaContable.add(Integer.valueOf(cuenta.getIdCuentaContable()));
/* 107:    */     }
/* 108:124 */     for (??? = componenteCosto.getListaDetalleComponenteCosto().iterator(); ???.hasNext();)
/* 109:    */     {
/* 110:124 */       detalle = (DetalleComponenteCosto)???.next();
/* 111:125 */       if (!detalle.isEliminado())
/* 112:    */       {
/* 113:126 */         if ((detalle.getCuentaContable() == null) || (detalle.getCuentaContable().getId() == 0)) {
/* 114:127 */           throw new AS2Exception("msg_error_campo_obligatorio", new String[] { "Cuenta Contable" });
/* 115:    */         }
/* 116:129 */         if (setIdCuentaContable.contains(Integer.valueOf(detalle.getCuentaContable().getId()))) {
/* 117:130 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_ERROR_CUENTA_CONTABLE_REPETIDA", new String[] { detalle.getCuentaContable().getCodigo() });
/* 118:    */         }
/* 119:132 */         setIdCuentaContable.add(Integer.valueOf(detalle.getCuentaContable().getId()));
/* 120:    */       }
/* 121:    */     }
/* 122:    */     DetalleComponenteCosto detalle;
/* 123:137 */     Object dimensiones = new HashSet();
/* 124:138 */     for (DetalleComponenteCosto detalle : componenteCosto.getListaDetalleComponenteCosto()) {
/* 125:139 */       if (!detalle.isEliminado())
/* 126:    */       {
/* 127:144 */         key = detalle.getCuentaContable().isIndicadorValidarDimension1() + "~" + detalle.getCuentaContable().isIndicadorValidarDimension2() + "~" + detalle.getCuentaContable().isIndicadorValidarDimension3() + "~" + detalle.getCuentaContable().isIndicadorValidarDimension4() + "~" + detalle.getCuentaContable().isIndicadorValidarDimension5();
/* 128:145 */         ((Set)dimensiones).add(key);
/* 129:146 */         if (((Set)dimensiones).size() > 1) {
/* 130:148 */           throw new AS2Exception("msg_error_componente_costo_cuentas_con_dimensiones_diferentes", new String[] {detalle.getCuentaContable().getCodigo() });
/* 131:    */         }
/* 132:    */       }
/* 133:    */     }
/* 134:    */     String key;
/* 135:154 */     Set<String> distribucion = new HashSet();
/* 136:155 */     Set<String> keysDimension = new HashSet();
/* 137:156 */     for (DetalleComponenteCostoDistribucion detalle : componenteCosto.getListaDetalleComponenteCostoDistribucion()) {
/* 138:157 */       if (!detalle.isEliminado())
/* 139:    */       {
/* 140:161 */         String key = (detalle.getDimensionContable1() != null) + "~" + (detalle.getDimensionContable2() != null) + "~" + (detalle.getDimensionContable3() != null) + "~" + (detalle.getDimensionContable4() != null) + "~" + (detalle.getDimensionContable5() != null);
/* 141:    */         
/* 142:163 */         distribucion.add(key);
/* 143:164 */         if (distribucion.size() > 1) {
/* 144:165 */           throw new AS2Exception("msg_error_componente_costo_dimensiones_diferente_distribucion", new String[] { "" });
/* 145:    */         }
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void eliminar(ComponenteCosto componenteCosto)
/* 151:    */   {
/* 152:190 */     componenteCosto = cargarDetalle(componenteCosto.getId());
/* 153:191 */     for (DetalleComponenteCosto detalle : componenteCosto.getListaDetalleComponenteCosto()) {
/* 154:192 */       this.detalleComponenteCostoDao.eliminar(detalle);
/* 155:    */     }
/* 156:194 */     for (DetalleComponenteCostoDistribucion detalle : componenteCosto.getListaDetalleComponenteCostoDistribucion()) {
/* 157:195 */       this.detalleComponenteCostoDistribucionDao.eliminar(detalle);
/* 158:    */     }
/* 159:197 */     this.componenteCostoDao.eliminar(componenteCosto);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public ComponenteCosto buscarPorId(int idComponenteCosto)
/* 163:    */   {
/* 164:207 */     return (ComponenteCosto)this.componenteCostoDao.buscarPorId(Integer.valueOf(idComponenteCosto));
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<ComponenteCosto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 168:    */   {
/* 169:218 */     return this.componenteCostoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List<ComponenteCosto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 173:    */   {
/* 174:228 */     return this.componenteCostoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int contarPorCriterio(Map<String, String> filters)
/* 178:    */   {
/* 179:238 */     return this.componenteCostoDao.contarPorCriterio(filters);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public ComponenteCosto cargarDetalle(int idComponenteCosto)
/* 183:    */   {
/* 184:243 */     return this.componenteCostoDao.cargarDetalle(idComponenteCosto);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<ComponenteCosto> buscarComponentePorTipo(int idOrganizacion, TipoComponenteCostoEnum tipoComponente)
/* 188:    */   {
/* 189:248 */     List<ComponenteCosto> listaComponenteCosto = new ArrayList();
/* 190:249 */     Map<String, String> filtros = new HashMap();
/* 191:250 */     filtros.put("idOrganizacion", idOrganizacion + "");
/* 192:251 */     filtros.put("activo", "true");
/* 193:252 */     filtros.put("tipoComponenteCostoEnum", tipoComponente.toString());
/* 194:253 */     List<ComponenteCosto> listaComponenetes = obtenerListaCombo("predeterminado", false, filtros);
/* 195:254 */     for (ComponenteCosto componente : listaComponenetes)
/* 196:    */     {
/* 197:255 */       ComponenteCosto componenteCosto = cargarDetalle(componente.getId());
/* 198:256 */       listaComponenteCosto.add(componenteCosto);
/* 199:    */     }
/* 200:259 */     return listaComponenteCosto;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Map<Integer, BigDecimal> obtenerSaldoCuentaDistribuidoPorRuta(ComponenteCosto componenteCosto, Date fechaDesde, Date fechaHasta)
/* 204:    */     throws AS2Exception
/* 205:    */   {
/* 206:267 */     Map<Integer, BigDecimal> mapaValorPorRutaFabricacion = new HashMap();
/* 207:    */     
/* 208:    */ 
/* 209:270 */     HashMap<CuentaSaldo, List<RutaFabricacion>> hmValorRutasFabricacion = new HashMap();
/* 210:272 */     for (DetalleComponenteCosto detalle : componenteCosto.getListaDetalleComponenteCosto())
/* 211:    */     {
/* 212:274 */       cuenta = detalle.getCuentaContable();
/* 213:275 */       valorCalculo = detalle.getValorCalculo();
/* 214:278 */       for (DetalleComponenteCostoDistribucion detalleDistribucion : componenteCosto.getListaDetalleComponenteCostoDistribucion())
/* 215:    */       {
/* 216:286 */         key = cuenta.getId() + "~" + (detalleDistribucion.getDimensionContable1() != null ? Integer.valueOf(detalleDistribucion.getDimensionContable1().getId()) : "na") + "~" + (detalleDistribucion.getDimensionContable2() != null ? Integer.valueOf(detalleDistribucion.getDimensionContable2().getId()) : "na") + "~" + (detalleDistribucion.getDimensionContable3() != null ? Integer.valueOf(detalleDistribucion.getDimensionContable3().getId()) : "na") + "~" + (detalleDistribucion.getDimensionContable4() != null ? Integer.valueOf(detalleDistribucion.getDimensionContable4().getId()) : "na") + "~" + (detalleDistribucion.getDimensionContable5() != null ? Integer.valueOf(detalleDistribucion.getDimensionContable5().getId()) : "na");
/* 217:    */         
/* 218:288 */         CuentaSaldo cuentaSaldo = new CuentaSaldo(null);
/* 219:289 */         cuentaSaldo.setCodigo(key);
/* 220:    */         
/* 221:    */ 
/* 222:292 */         RutaFabricacion rutaFabricacion = detalleDistribucion.getRutaFabricacion();
/* 223:    */         
/* 224:    */ 
/* 225:295 */         List<RutaFabricacion> lista = (List)hmValorRutasFabricacion.get(cuentaSaldo);
/* 226:296 */         if (lista == null)
/* 227:    */         {
/* 228:297 */           BigDecimal saldoRuta = this.servicioCuentaContable.obtenerSaldoPorConbinacionDimensiones(fechaDesde, fechaHasta, cuenta, valorCalculo, TipoCalculo.MOVIMIENTOS_MES, false, 0, detalleDistribucion
/* 229:298 */             .getDimensionContable1(), detalleDistribucion
/* 230:299 */             .getDimensionContable2(), detalleDistribucion.getDimensionContable3(), detalleDistribucion
/* 231:300 */             .getDimensionContable4(), detalleDistribucion.getDimensionContable5());
/* 232:302 */           if (saldoRuta.compareTo(BigDecimal.ZERO) < 0) {
/* 233:303 */             throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioAsientoImpl.CUENTA_CONTABLE_VALOR_NEGATIVO", new String[] { cuenta.getCodigo() });
/* 234:    */           }
/* 235:305 */           cuentaSaldo.setSaldo(saldoRuta);
/* 236:306 */           lista = new ArrayList();
/* 237:    */         }
/* 238:308 */         lista.add(rutaFabricacion);
/* 239:309 */         hmValorRutasFabricacion.put(cuentaSaldo, lista);
/* 240:    */       }
/* 241:    */     }
/* 242:    */     CuentaContable cuenta;
/* 243:    */     ValoresCalculo valorCalculo;
/* 244:    */     String key;
/* 245:315 */     for (CuentaSaldo cuentaSaldo : hmValorRutasFabricacion.keySet())
/* 246:    */     {
/* 247:317 */       lista = (List)hmValorRutasFabricacion.get(cuentaSaldo);
/* 248:318 */       if (!lista.isEmpty())
/* 249:    */       {
/* 250:321 */         saldoRuta = cuentaSaldo.getSaldo();
/* 251:322 */         saldoProporcional = saldoRuta.divide(new BigDecimal(lista.size()), 2, RoundingMode.HALF_UP);
/* 252:323 */         contador = 1;
/* 253:325 */         for (RutaFabricacion ruta : lista)
/* 254:    */         {
/* 255:328 */           saldoProporcional = contador == lista.size() ? saldoRuta : saldoProporcional;
/* 256:    */           
/* 257:    */ 
/* 258:331 */           saldoRuta = saldoRuta.subtract(saldoProporcional);
/* 259:    */           
/* 260:    */ 
/* 261:334 */           BigDecimal saldoAcumulado = (BigDecimal)mapaValorPorRutaFabricacion.get(Integer.valueOf(ruta.getId()));
/* 262:335 */           saldoAcumulado = saldoAcumulado == null ? BigDecimal.ZERO : saldoAcumulado;
/* 263:    */           
/* 264:    */ 
/* 265:338 */           saldoAcumulado = saldoAcumulado.add(saldoProporcional);
/* 266:339 */           mapaValorPorRutaFabricacion.put(Integer.valueOf(ruta.getId()), saldoAcumulado);
/* 267:    */           
/* 268:341 */           contador++;
/* 269:    */         }
/* 270:    */       }
/* 271:    */     }
/* 272:    */     List<RutaFabricacion> lista;
/* 273:    */     BigDecimal saldoRuta;
/* 274:    */     BigDecimal saldoProporcional;
/* 275:    */     int contador;
/* 276:347 */     return mapaValorPorRutaFabricacion;
/* 277:    */   }
/* 278:    */   
/* 279:    */   private class CuentaSaldo
/* 280:    */   {
/* 281:    */     private String codigo;
/* 282:    */     private BigDecimal saldo;
/* 283:    */     
/* 284:    */     private CuentaSaldo() {}
/* 285:    */     
/* 286:    */     public String getCodigo()
/* 287:    */     {
/* 288:356 */       return this.codigo;
/* 289:    */     }
/* 290:    */     
/* 291:    */     public void setCodigo(String codigo)
/* 292:    */     {
/* 293:360 */       this.codigo = codigo;
/* 294:    */     }
/* 295:    */     
/* 296:    */     public BigDecimal getSaldo()
/* 297:    */     {
/* 298:364 */       return this.saldo;
/* 299:    */     }
/* 300:    */     
/* 301:    */     public void setSaldo(BigDecimal saldo)
/* 302:    */     {
/* 303:368 */       this.saldo = saldo;
/* 304:    */     }
/* 305:    */     
/* 306:    */     public int hashCode()
/* 307:    */     {
/* 308:373 */       int prime = 31;
/* 309:374 */       int result = 1;
/* 310:375 */       result = 31 * result + getOuterType().hashCode();
/* 311:376 */       result = 31 * result + (this.codigo == null ? 0 : this.codigo.hashCode());
/* 312:377 */       return result;
/* 313:    */     }
/* 314:    */     
/* 315:    */     public boolean equals(Object obj)
/* 316:    */     {
/* 317:382 */       if (this == obj) {
/* 318:383 */         return true;
/* 319:    */       }
/* 320:384 */       if (obj == null) {
/* 321:385 */         return false;
/* 322:    */       }
/* 323:386 */       if (getClass() != obj.getClass()) {
/* 324:387 */         return false;
/* 325:    */       }
/* 326:388 */       CuentaSaldo other = (CuentaSaldo)obj;
/* 327:389 */       if (!getOuterType().equals(other.getOuterType())) {
/* 328:390 */         return false;
/* 329:    */       }
/* 330:391 */       if (this.codigo == null)
/* 331:    */       {
/* 332:392 */         if (other.codigo != null) {
/* 333:393 */           return false;
/* 334:    */         }
/* 335:    */       }
/* 336:394 */       else if (!this.codigo.equals(other.codigo)) {
/* 337:395 */         return false;
/* 338:    */       }
/* 339:396 */       return true;
/* 340:    */     }
/* 341:    */     
/* 342:    */     private ServicioComponenteCostoImpl getOuterType()
/* 343:    */     {
/* 344:400 */       return ServicioComponenteCostoImpl.this;
/* 345:    */     }
/* 346:    */   }
/* 347:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioComponenteCostoImpl
 * JD-Core Version:    0.7.0.1
 */