/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.presupuesto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CentroCosto;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Ejercicio;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*  12:    */ import com.asinfo.as2.entities.presupuesto.Presupuesto;
/*  13:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  16:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  17:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.ejb.Stateless;
/*  29:    */ import javax.persistence.EntityManager;
/*  30:    */ import javax.persistence.NoResultException;
/*  31:    */ import javax.persistence.Query;
/*  32:    */ 
/*  33:    */ @Stateless
/*  34:    */ public class ReportePresupuestoDao
/*  35:    */   extends AbstractDaoAS2<Presupuesto>
/*  36:    */ {
/*  37:    */   @EJB
/*  38:    */   private ServicioDimensionContable servicioDimensionContable;
/*  39:    */   
/*  40:    */   public ReportePresupuestoDao()
/*  41:    */   {
/*  42: 43 */     super(Presupuesto.class);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<Object[]> getReportePresupuesto(Ejercicio ejercicio, Mes mes, Sucursal sucursal, boolean indicadorAcumulado, List<CuentaContable> listaCuentaContable, int idOrganizacion, DimensionContable dimensionContable, String dimensionContablePresupuesto)
/*  46:    */   {
/*  47: 50 */     String constanteMes = "";
/*  48: 51 */     String constanteAnio = "e.nombre ";
/*  49: 52 */     StringBuilder sql = new StringBuilder();
/*  50: 53 */     List<Object[]> lista = new ArrayList();
/*  51: 54 */     if (!indicadorAcumulado) {
/*  52: 55 */       constanteMes = ",p.nombre";
/*  53:    */     }
/*  54: 57 */     sql = new StringBuilder();
/*  55: 58 */     sql.append("SELECT cc.codigo, cc.nombre," + constanteAnio + constanteMes + ", ppp.codigo, ppp.nombre,");
/*  56: 59 */     if (mes == null)
/*  57:    */     {
/*  58: 60 */       if (indicadorAcumulado)
/*  59:    */       {
/*  60: 61 */         sql.append(" SUM(dpr.valorEnero+dpr.valorFebrero+dpr.valorMarzo+dpr.valorAbril+dpr.valorMayo+dpr.valorJunio");
/*  61: 62 */         sql.append("+dpr.valorJulio+dpr.valorAgosto+dpr.valorSeptiembre+dpr.valorOctubre+dpr.valorNoviembre+dpr.valorDiciembre),");
/*  62:    */       }
/*  63:    */       else
/*  64:    */       {
/*  65: 64 */         sql.append(" (CASE UPPER(p.nombre)");
/*  66: 65 */         sql.append(" WHEN '" + Mes.ENERO.getNombre().toUpperCase() + "' THEN SUM(dpr.valorEnero)");
/*  67: 66 */         sql.append(" WHEN '" + Mes.FEBRERO.getNombre().toUpperCase() + "' THEN SUM(dpr.valorFebrero)");
/*  68: 67 */         sql.append(" WHEN '" + Mes.MARZO.getNombre().toUpperCase() + "' THEN SUM(dpr.valorMarzo)");
/*  69: 68 */         sql.append(" WHEN '" + Mes.ABRIL.getNombre().toUpperCase() + "' THEN SUM(dpr.valorAbril)");
/*  70: 69 */         sql.append(" WHEN '" + Mes.MAYO.getNombre().toUpperCase() + "' THEN SUM(dpr.valorMayo)");
/*  71: 70 */         sql.append(" WHEN '" + Mes.JUNIO.getNombre().toUpperCase() + "' THEN SUM(dpr.valorJunio)");
/*  72: 71 */         sql.append(" WHEN '" + Mes.JULIO.getNombre().toUpperCase() + "' THEN SUM(dpr.valorJulio)");
/*  73: 72 */         sql.append(" WHEN '" + Mes.AGOSTO.getNombre().toUpperCase() + "' THEN SUM(dpr.valorAgosto)");
/*  74: 73 */         sql.append(" WHEN '" + Mes.SEPTIEMBRE.getNombre().toUpperCase() + "' THEN SUM(dpr.valorSeptiembre)");
/*  75: 74 */         sql.append(" WHEN '" + Mes.OCTUBRE.getNombre().toUpperCase() + "' THEN SUM(dpr.valorOctubre)");
/*  76: 75 */         sql.append(" WHEN '" + Mes.NOVIEMBRE.getNombre().toUpperCase() + "' THEN SUM(dpr.valorNoviembre)");
/*  77: 76 */         sql.append(" WHEN '" + Mes.DICIEMBRE.getNombre().toUpperCase() + "' THEN SUM(dpr.valorDiciembre)");
/*  78: 77 */         sql.append(" ELSE  SUM(dpr.valorEnero*0) END),");
/*  79:    */       }
/*  80:    */     }
/*  81: 79 */     else if (mes.getNumero() == 1) {
/*  82: 80 */       sql.append(" SUM(dpr.valorEnero),");
/*  83: 81 */     } else if (mes.getNumero() == 2) {
/*  84: 82 */       sql.append(" SUM(dpr.valorFebrero),");
/*  85: 83 */     } else if (mes.getNumero() == 3) {
/*  86: 84 */       sql.append(" SUM(dpr.valorMarzo),");
/*  87: 85 */     } else if (mes.getNumero() == 4) {
/*  88: 86 */       sql.append(" SUM(dpr.valorAbril),");
/*  89: 87 */     } else if (mes.getNumero() == 5) {
/*  90: 88 */       sql.append(" SUM(dpr.valorMayo),");
/*  91: 89 */     } else if (mes.getNumero() == 6) {
/*  92: 90 */       sql.append(" SUM(dpr.valorJunio),");
/*  93: 91 */     } else if (mes.getNumero() == 7) {
/*  94: 92 */       sql.append(" SUM(dpr.valorJulio),");
/*  95: 93 */     } else if (mes.getNumero() == 8) {
/*  96: 94 */       sql.append(" SUM(dpr.valorAgosto),");
/*  97: 95 */     } else if (mes.getNumero() == 9) {
/*  98: 96 */       sql.append(" SUM(dpr.valorSeptiembre),");
/*  99: 97 */     } else if (mes.getNumero() == 10) {
/* 100: 98 */       sql.append(" SUM(dpr.valorOctubre),");
/* 101: 99 */     } else if (mes.getNumero() == 11) {
/* 102:100 */       sql.append(" SUM(dpr.valorNoviembre),");
/* 103:101 */     } else if (mes.getNumero() == 12) {
/* 104:102 */       sql.append(" SUM(dpr.valorDiciembre),");
/* 105:    */     }
/* 106:104 */     sql.append(" SUM(dpr.valorEnero*0), p.fechaDesde,  ppp.idDimensionContable, -1, 1");
/* 107:105 */     sql.append(" FROM DetallePresupuesto dpr");
/* 108:106 */     sql.append(" INNER JOIN dpr.cuentaContable cc");
/* 109:107 */     sql.append(" INNER JOIN dpr.presupuesto pr");
/* 110:    */     
/* 111:109 */     sql.append(" INNER JOIN dpr.dimensionContable ppp");
/* 112:110 */     sql.append(" INNER JOIN pr.ejercicio e");
/* 113:111 */     sql.append(" INNER JOIN e.periodos p");
/* 114:112 */     if (sucursal != null) {
/* 115:113 */       sql.append(" INNER JOIN pr.sucursal s");
/* 116:    */     }
/* 117:114 */     sql.append(" WHERE e.idEjercicio = :idEjercicio");
/* 118:115 */     sql.append(" AND ppp = :codigoDimensionContable");
/* 119:116 */     if (mes != null) {
/* 120:117 */       sql.append(" AND p.nombre = :mes");
/* 121:    */     }
/* 122:119 */     if (sucursal != null) {
/* 123:120 */       sql.append(" AND s.idSucursal =:idSucursal");
/* 124:    */     }
/* 125:122 */     sql.append(" AND pr.idOrganizacion=:idOrganizacion");
/* 126:123 */     if (listaCuentaContable.size() != 0) {}
/* 127:126 */     sql.append(" GROUP BY cc.codigo, cc.nombre, " + constanteAnio + constanteMes + ", ppp.codigo, ppp.nombre, ppp.idDimensionContable,p.fechaDesde");
/* 128:    */     
/* 129:128 */     Query query = this.em.createQuery(sql.toString());
/* 130:129 */     query.setParameter("idEjercicio", Integer.valueOf(ejercicio.getIdEjercicio()));
/* 131:130 */     if (sucursal != null) {
/* 132:131 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 133:    */     }
/* 134:132 */     if (mes != null) {
/* 135:133 */       query.setParameter("mes", mes.getNombre().toUpperCase());
/* 136:    */     }
/* 137:135 */     query.setParameter("codigoDimensionContable", dimensionContable);
/* 138:136 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 139:137 */     if (listaCuentaContable.size() != 0) {}
/* 140:140 */     lista.addAll(query.getResultList());
/* 141:    */     
/* 142:142 */     StringBuilder sql2 = new StringBuilder();
/* 143:143 */     sql2.append(" SELECT  cc.codigo, cc.nombre," + constanteAnio + constanteMes + ", dcda.codigo, dcda.nombre, ");
/* 144:144 */     sql2.append(" SUM(da.debe*0), SUM(da.debe-da.haber),p.fechaDesde");
/* 145:145 */     sql2.append(" FROM DetalleAsiento da ");
/* 146:146 */     sql2.append(" LEFT JOIN da.dimensionContable" + dimensionContablePresupuesto + " dcda ");
/* 147:147 */     sql2.append(" JOIN da.asiento a ");
/* 148:148 */     sql2.append(" LEFT JOIN a.documentoOrigen do");
/* 149:149 */     sql2.append(" JOIN da.cuentaContable cc ");
/* 150:    */     
/* 151:    */ 
/* 152:    */ 
/* 153:153 */     sql2.append(" INNER JOIN a.periodo p ");
/* 154:154 */     sql2.append(" INNER JOIN p.ejercicio e ");
/* 155:155 */     if (sucursal != null) {
/* 156:156 */       sql2.append(" INNER JOIN a.sucursal s ");
/* 157:    */     }
/* 158:157 */     sql2.append(" WHERE e.idEjercicio = :idEjercicio");
/* 159:158 */     sql2.append(" AND do.documentoBase = :documento");
/* 160:159 */     sql2.append(" AND dcda = :codigoDimensionContable ");
/* 161:160 */     if (mes != null) {
/* 162:161 */       sql2.append(" AND p.nombre = :mes");
/* 163:    */     }
/* 164:163 */     if (sucursal != null) {
/* 165:164 */       sql2.append(" AND s.idSucursal =:idSucursal");
/* 166:    */     }
/* 167:165 */     sql2.append(" AND a.idOrganizacion=:idOrganizacion");
/* 168:166 */     if (listaCuentaContable.size() != 0) {}
/* 169:169 */     sql2.append(" GROUP BY cc.codigo, cc.nombre, " + constanteAnio + constanteMes + ", dcda.codigo, dcda.nombre, dcda.idDimensionContable, p.fechaDesde");
/* 170:    */     
/* 171:171 */     Query query2 = this.em.createQuery(sql2.toString());
/* 172:172 */     query2.setParameter("idEjercicio", Integer.valueOf(ejercicio.getIdEjercicio()));
/* 173:173 */     if (sucursal != null) {
/* 174:174 */       query2.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 175:    */     }
/* 176:175 */     if (mes != null) {
/* 177:176 */       query2.setParameter("mes", mes.getNombre().toUpperCase());
/* 178:    */     }
/* 179:178 */     query2.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 180:179 */     query2.setParameter("codigoDimensionContable", dimensionContable);
/* 181:180 */     query2.setParameter("documento", DocumentoBase.FACTURA_PROVEEDOR);
/* 182:181 */     if (listaCuentaContable.size() != 0) {}
/* 183:184 */     lista.addAll(query2.getResultList());
/* 184:185 */     return lista;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<DetalleAsiento> getMayorPartidaPresupuestaria(PartidaPresupuestaria partidaPresupuestaria, Date fechaDesde, Date fechaHasta, int idSucursal, boolean indicadorNIIF, CentroCosto centroCosto)
/* 188:    */   {
/* 189:192 */     StringBuilder sql = new StringBuilder();
/* 190:194 */     if (centroCosto != null)
/* 191:    */     {
/* 192:195 */       sql.append("SELECT new DetalleAsiento(CASE WHEN da.debe>0 THEN dacc.valor ELSE 0 END , CASE WHEN da.haber > 0 THEN dacc.valor ELSE 0 END,");
/* 193:196 */       sql.append(" da.descripcion, at.concepto, at.numero, at.fecha, ta.nombre,cc.codigo,cc.nombre,ppc.codigo,ppc.nombre) ");
/* 194:197 */       sql.append(" FROM DetalleAsientoCentroCosto dacc INNER JOIN dacc.centroCosto ccc INNER JOIN dacc.detalleAsiento da");
/* 195:    */     }
/* 196:    */     else
/* 197:    */     {
/* 198:199 */       sql.append("SELECT new DetalleAsiento(da.debe,da.haber,da.descripcion, at.concepto, at.numero, at.fecha, ta.nombre,cc.codigo,cc.nombre,");
/* 199:200 */       sql.append("ppc.codigo,ppc.nombre) ");
/* 200:201 */       sql.append(" FROM DetalleAsiento da");
/* 201:    */     }
/* 202:204 */     sql.append(" INNER JOIN da.asiento at");
/* 203:205 */     sql.append(" INNER JOIN at.tipoAsiento ta");
/* 204:206 */     sql.append(" INNER JOIN at.sucursal sc");
/* 205:207 */     sql.append(" INNER JOIN da.cuentaContable cc");
/* 206:208 */     sql.append(" INNER JOIN cc.partidaPresupuestaria ppc");
/* 207:209 */     sql.append(" WHERE ppc = :partidaPresupuestaria");
/* 208:210 */     sql.append(" AND ta.indicadorNIIF =:indicadorNIIF");
/* 209:211 */     sql.append(" AND at.estado !=:estadoAnulado");
/* 210:212 */     sql.append(" AND (sc.idSucursal =:idSucursal OR :idSucursal=0)");
/* 211:213 */     sql.append(" AND at.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 212:214 */     sql.append(" AND at.idOrganizacion=:idOrganizacion ");
/* 213:216 */     if (centroCosto != null) {
/* 214:217 */       sql.append(" AND ccc = :centroCosto");
/* 215:    */     }
/* 216:220 */     Query query = this.em.createQuery(sql.toString());
/* 217:221 */     query.setParameter("partidaPresupuestaria", partidaPresupuestaria);
/* 218:222 */     query.setParameter("fechaDesde", fechaDesde);
/* 219:223 */     query.setParameter("fechaHasta", fechaHasta);
/* 220:224 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 221:225 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 222:226 */     query.setParameter("indicadorNIIF", Boolean.valueOf(indicadorNIIF));
/* 223:227 */     query.setParameter("idOrganizacion", Integer.valueOf(AppUtil.getOrganizacion().getId()));
/* 224:228 */     if (centroCosto != null) {
/* 225:229 */       query.setParameter("centroCosto", centroCosto);
/* 226:    */     }
/* 227:231 */     return query.getResultList();
/* 228:    */   }
/* 229:    */   
/* 230:    */   public BigDecimal obtenerSaldo(Date fechaDesde, Date fechaHasta, ValoresCalculo valoresCalculo, TipoCalculo tipoCalculo, boolean indicadorNIIF, int idSucursal, PartidaPresupuestaria partidaPresupuestaria, CentroCosto centroCosto)
/* 231:    */   {
/* 232:238 */     BigDecimal saldo = BigDecimal.ZERO;
/* 233:    */     
/* 234:    */ 
/* 235:241 */     String sumatoria = "";
/* 236:242 */     String condicionFecha = "";
/* 237:243 */     String debe = "da.debe";
/* 238:244 */     String haber = "da.haber";
/* 239:246 */     if (centroCosto != null)
/* 240:    */     {
/* 241:247 */       debe = " CASE WHEN da.debe>0 THEN dacc.valor ELSE 0 END ";
/* 242:248 */       haber = " CASE WHEN da.haber > 0 THEN dacc.valor ELSE 0 END ";
/* 243:    */     }
/* 244:251 */     if (valoresCalculo == ValoresCalculo.DEBE_HABER) {
/* 245:252 */       sumatoria = debe + "-" + haber;
/* 246:253 */     } else if (valoresCalculo == ValoresCalculo.DEBE) {
/* 247:254 */       sumatoria = debe;
/* 248:255 */     } else if (valoresCalculo == ValoresCalculo.HABER) {
/* 249:256 */       sumatoria = haber;
/* 250:    */     }
/* 251:259 */     if (tipoCalculo == TipoCalculo.MOVIMIENTOS_MES) {
/* 252:260 */       condicionFecha = " AND a.fecha BETWEEN :fechaDesde AND :fechaHasta";
/* 253:261 */     } else if (tipoCalculo == TipoCalculo.SALDO_INICIAL) {
/* 254:262 */       condicionFecha = " AND a.fecha < :fechaDesde AND :fechaHasta = :fechaHasta";
/* 255:263 */     } else if (tipoCalculo == TipoCalculo.SALDO_FINAL) {
/* 256:264 */       condicionFecha = " AND :fechaDesde = :fechaDesde AND a.fecha <= :fechaHasta";
/* 257:    */     }
/* 258:267 */     StringBuilder sqlMovimientos = new StringBuilder();
/* 259:269 */     if (centroCosto != null)
/* 260:    */     {
/* 261:270 */       sqlMovimientos.append("SELECT SUM(" + sumatoria + ")");
/* 262:271 */       sqlMovimientos.append(" FROM DetalleAsientoCentroCosto dacc");
/* 263:272 */       sqlMovimientos.append(" INNER JOIN dacc.detalleAsiento da");
/* 264:273 */       sqlMovimientos.append(" INNER JOIN dacc.centroCosto ccc ");
/* 265:    */     }
/* 266:    */     else
/* 267:    */     {
/* 268:275 */       sqlMovimientos.append("SELECT SUM(" + sumatoria + ")");
/* 269:276 */       sqlMovimientos.append(" FROM DetalleAsiento da ");
/* 270:    */     }
/* 271:279 */     sqlMovimientos.append(" INNER JOIN da.asiento a");
/* 272:280 */     sqlMovimientos.append(" INNER JOIN a.tipoAsiento ta");
/* 273:281 */     sqlMovimientos.append(" INNER JOIN a.sucursal s");
/* 274:282 */     sqlMovimientos.append(" INNER JOIN da.cuentaContable cc");
/* 275:283 */     sqlMovimientos.append(" INNER JOIN cc.partidaPresupuestaria pp");
/* 276:    */     
/* 277:285 */     sqlMovimientos.append(" WHERE ta.indicadorNIIF = :indicadorNIIF");
/* 278:286 */     sqlMovimientos.append(condicionFecha);
/* 279:287 */     sqlMovimientos.append(" AND pp =:partidaPresupuestaria");
/* 280:288 */     sqlMovimientos.append(" AND a.idOrganizacion = :idOrganizacion");
/* 281:289 */     sqlMovimientos.append(" AND (s.idSucursal = :idSucursal OR :idSucursal = 0)");
/* 282:290 */     sqlMovimientos.append(" AND a.estado!=:estadoAnulado");
/* 283:292 */     if (centroCosto != null) {
/* 284:293 */       sqlMovimientos.append(" AND ccc = :centroCosto ");
/* 285:    */     }
/* 286:296 */     Query query = this.em.createQuery(sqlMovimientos.toString());
/* 287:297 */     query.setParameter("partidaPresupuestaria", partidaPresupuestaria);
/* 288:298 */     query.setParameter("fechaDesde", fechaDesde);
/* 289:299 */     query.setParameter("fechaHasta", fechaHasta);
/* 290:    */     
/* 291:301 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 292:302 */     query.setParameter("indicadorNIIF", Boolean.valueOf(indicadorNIIF));
/* 293:303 */     query.setParameter("idOrganizacion", Integer.valueOf(AppUtil.getSucursal().getIdOrganizacion()));
/* 294:304 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 295:305 */     if (centroCosto != null) {
/* 296:306 */       query.setParameter("centroCosto", centroCosto);
/* 297:    */     }
/* 298:309 */     BigDecimal valorMovimientos = (BigDecimal)query.getSingleResult();
/* 299:310 */     if (valorMovimientos == null) {
/* 300:311 */       valorMovimientos = BigDecimal.ZERO;
/* 301:    */     }
/* 302:314 */     String valorEnero = "dpr.valorEnero+dpr.incrementosEnero+dpr.transferenciasIngresoEnero-dpr.decrementosEnero-dpr.transferenciasEgresoEnero";
/* 303:315 */     String valorFebrero = "dpr.valorFebrero+dpr.incrementosFebrero+dpr.transferenciasIngresoFebrero-dpr.decrementosFebrero-dpr.transferenciasEgresoFebrero";
/* 304:316 */     String valorMarzo = "dpr.valorMarzo+dpr.incrementosMarzo+dpr.transferenciasIngresoMarzo-dpr.decrementosMarzo-dpr.transferenciasEgresoMarzo";
/* 305:317 */     String valorAbril = "dpr.valorAbril+dpr.incrementosAbril+dpr.transferenciasIngresoAbril-dpr.decrementosAbril-dpr.transferenciasEgresoAbril";
/* 306:318 */     String valorMayo = "dpr.valorMayo+dpr.incrementosMayo+dpr.transferenciasIngresoMayo-dpr.decrementosMayo-dpr.transferenciasEgresoMayo";
/* 307:319 */     String valorJunio = "dpr.valorJunio+dpr.incrementosJunio+dpr.transferenciasIngresoJunio-dpr.decrementosJunio-dpr.transferenciasEgresoJunio";
/* 308:320 */     String valorJulio = "dpr.valorJulio+dpr.incrementosJulio+dpr.transferenciasIngresoJulio-dpr.decrementosJulio-dpr.transferenciasEgresoJulio";
/* 309:321 */     String valorAgosto = "dpr.valorAgosto+dpr.incrementosAgosto+dpr.transferenciasIngresoAgosto-dpr.decrementosAgosto-dpr.transferenciasEgresoAgosto";
/* 310:322 */     String valorSeptiembre = "dpr.valorSeptiembre+dpr.incrementosSeptiembre+dpr.transferenciasIngresoSeptiembre-dpr.decrementosSeptiembre-dpr.transferenciasEgresoSeptiembre";
/* 311:323 */     String valorOctubre = "dpr.valorOctubre+dpr.incrementosOctubre+dpr.transferenciasIngresoOctubre-dpr.decrementosOctubre-dpr.transferenciasEgresoOctubre";
/* 312:324 */     String valorNoviembre = "dpr.valorNoviembre+dpr.incrementosNoviembre+dpr.transferenciasIngresoNoviembre-dpr.decrementosNoviembre-dpr.transferenciasEgresoNoviembre";
/* 313:325 */     String valorDiciembre = "dpr.valorDiciembre+dpr.incrementosDiciembre+dpr.transferenciasIngresoDiciembre-dpr.decrementosDiciembre-dpr.transferenciasEgresoDiciembre";
/* 314:    */     
/* 315:327 */     String acumuladoFebrero = valorEnero + "+" + valorFebrero;
/* 316:328 */     String acumuladoMarzo = acumuladoFebrero + "+" + valorMarzo;
/* 317:329 */     String acumuladoAbril = acumuladoMarzo + "+" + valorAbril;
/* 318:330 */     String acumuladoMayo = acumuladoAbril + "+" + valorMayo;
/* 319:331 */     String acumuladoJunio = acumuladoMayo + "+" + valorJunio;
/* 320:332 */     String acumuladoJulio = acumuladoJunio + "+" + valorJulio;
/* 321:333 */     String acumuladoAgosto = acumuladoJulio + "+" + valorAgosto;
/* 322:334 */     String acumuladoSeptiembre = acumuladoAgosto + "+" + valorSeptiembre;
/* 323:335 */     String acumuladoOctubre = acumuladoSeptiembre + "+" + valorOctubre;
/* 324:336 */     String acumuladoNoviembre = acumuladoOctubre + "+" + valorNoviembre;
/* 325:337 */     String acumuladoDiciembre = acumuladoNoviembre + "+" + valorDiciembre;
/* 326:    */     
/* 327:    */ 
/* 328:340 */     String codicionPresupuesto = null;
/* 329:341 */     if (FuncionesUtiles.getMes(fechaDesde) == 1) {
/* 330:342 */       codicionPresupuesto = " SELECT SUM(" + valorEnero + ") FROM DetallePresupuesto dpr ";
/* 331:343 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 2) {
/* 332:344 */       codicionPresupuesto = " SELECT SUM(" + acumuladoFebrero + ") FROM DetallePresupuesto dpr ";
/* 333:345 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 3) {
/* 334:346 */       codicionPresupuesto = " SELECT SUM(" + acumuladoMarzo + ") FROM DetallePresupuesto dpr ";
/* 335:347 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 4) {
/* 336:348 */       codicionPresupuesto = " SELECT SUM(" + acumuladoAbril + ") FROM DetallePresupuesto dpr ";
/* 337:349 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 5) {
/* 338:350 */       codicionPresupuesto = " SELECT SUM(" + acumuladoMayo + ") FROM DetallePresupuesto dpr ";
/* 339:351 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 6) {
/* 340:352 */       codicionPresupuesto = " SELECT SUM(" + acumuladoJunio + ") FROM DetallePresupuesto dpr ";
/* 341:353 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 7) {
/* 342:354 */       codicionPresupuesto = " SELECT SUM(" + acumuladoJulio + ") FROM DetallePresupuesto dpr ";
/* 343:355 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 8) {
/* 344:356 */       codicionPresupuesto = " SELECT SUM(" + acumuladoAgosto + ") FROM DetallePresupuesto dpr ";
/* 345:357 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 9) {
/* 346:358 */       codicionPresupuesto = " SELECT SUM(" + acumuladoSeptiembre + ") FROM DetallePresupuesto dpr ";
/* 347:359 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 10) {
/* 348:360 */       codicionPresupuesto = " SELECT SUM(" + acumuladoOctubre + ") FROM DetallePresupuesto dpr ";
/* 349:361 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 11) {
/* 350:362 */       codicionPresupuesto = " SELECT SUM(" + acumuladoNoviembre + ") FROM DetallePresupuesto dpr ";
/* 351:363 */     } else if (FuncionesUtiles.getMes(fechaDesde) == 12) {
/* 352:364 */       codicionPresupuesto = " SELECT SUM(" + acumuladoDiciembre + ") FROM DetallePresupuesto dpr ";
/* 353:    */     }
/* 354:366 */     if (centroCosto != null) {
/* 355:367 */       codicionPresupuesto = " SELECT SUM(dprcc.valor) FROM DetallePresupuestoCentroCosto dprcc INNER JOIN dprcc.detallePresupuesto dpr INNER JOIN dprcc.centroCosto ccp ";
/* 356:    */     }
/* 357:370 */     if (tipoCalculo == TipoCalculo.MOVIMIENTOS_MES)
/* 358:    */     {
/* 359:371 */       condicionFecha = " AND p.idPeriodo IN (SELECT pe.idPeriodo FROM Periodo pe WHERE pe.fechaHasta BETWEEN :fechaDesde AND :fechaHasta)";
/* 360:    */     }
/* 361:372 */     else if (tipoCalculo == TipoCalculo.SALDO_INICIAL)
/* 362:    */     {
/* 363:373 */       condicionFecha = " AND p.idPeriodo NOT IN (SELECT pe.idPeriodo FROM Periodo pe WHERE :fechaDesde BETWEEN pe.fechaDesde AND pe.fechaHasta)";
/* 364:374 */       condicionFecha = condicionFecha + " AND p.fechaHasta < :fechaDesde AND :fechaHasta = :fechaHasta AND p.fechaDesde> :fechaIncial";
/* 365:    */     }
/* 366:375 */     else if (tipoCalculo == TipoCalculo.SALDO_FINAL)
/* 367:    */     {
/* 368:376 */       condicionFecha = " AND p.idPeriodo IN (SELECT pe.idPeriodo FROM Periodo pe WHERE pe.fechaHasta <= :fechaHasta AND :fechaDesde=:fechaDesde)";
/* 369:    */     }
/* 370:379 */     String sqlPresupuesto = codicionPresupuesto + " INNER JOIN dpr.presupuesto pr " + " INNER JOIN dpr.partidaPresupuestaria ppp " + " INNER JOIN pr.periodo p " + " INNER JOIN pr.sucursal sp " + " WHERE ppp = :partidaPresupuestaria " + condicionFecha + " AND (sp.idSucursal =:idSucursal OR :idSucursal=0) " + " AND pr.idOrganizacion = :idOrganizacion ";
/* 371:383 */     if (centroCosto != null) {
/* 372:384 */       sqlPresupuesto = sqlPresupuesto + " AND ccp = :centroCosto";
/* 373:    */     }
/* 374:386 */     Date fechaIncial = FuncionesUtiles.getFecha(1, 1, FuncionesUtiles.getAnio(fechaDesde));
/* 375:387 */     query = this.em.createQuery(sqlPresupuesto);
/* 376:388 */     query.setParameter("partidaPresupuestaria", partidaPresupuestaria);
/* 377:389 */     query.setParameter("fechaDesde", fechaDesde);
/* 378:390 */     query.setParameter("fechaHasta", fechaHasta);
/* 379:391 */     query.setParameter("fechaIncial", fechaIncial);
/* 380:392 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 381:393 */     query.setParameter("idOrganizacion", Integer.valueOf(AppUtil.getSucursal().getIdOrganizacion()));
/* 382:394 */     if (centroCosto != null) {
/* 383:395 */       query.setParameter("centroCosto", centroCosto);
/* 384:    */     }
/* 385:398 */     BigDecimal valorPresupuestoActual = (BigDecimal)query.getSingleResult();
/* 386:399 */     if (valorPresupuestoActual == null) {
/* 387:400 */       valorPresupuestoActual = BigDecimal.ZERO;
/* 388:    */     }
/* 389:403 */     fechaDesde = FuncionesUtiles.getFecha(31, 12, FuncionesUtiles.getAnio(fechaDesde) - 1);
/* 390:404 */     codicionPresupuesto = null;
/* 391:405 */     codicionPresupuesto = " SELECT SUM(" + acumuladoDiciembre + ") FROM DetallePresupuesto dpr ";
/* 392:406 */     if (centroCosto != null) {
/* 393:407 */       codicionPresupuesto = " SELECT SUM(dprcc.valor) FROM DetallePresupuestoCentroCosto dprcc INNER JOIN dprcc.detallePresupuesto dpr INNER JOIN dprcc.centroCosto ccp ";
/* 394:    */     }
/* 395:409 */     if (tipoCalculo == TipoCalculo.MOVIMIENTOS_MES) {
/* 396:410 */       condicionFecha = " AND p.idPeriodo IN (SELECT pe.idPeriodo FROM Periodo pe WHERE pe.fechaHasta BETWEEN :fechaDesde AND :fechaHasta)";
/* 397:411 */     } else if (tipoCalculo == TipoCalculo.SALDO_INICIAL) {
/* 398:412 */       condicionFecha = " AND p.idPeriodo NOT IN (SELECT pe.idPeriodo FROM Periodo pe WHERE :fechaDesde BETWEEN pe.fechaDesde AND pe.fechaHasta) AND p.fechaHasta < :fechaDesde AND :fechaHasta = :fechaHasta ";
/* 399:413 */     } else if (tipoCalculo == TipoCalculo.SALDO_FINAL) {
/* 400:414 */       condicionFecha = " AND p.idPeriodo IN (SELECT pe.idPeriodo FROM Periodo pe WHERE pe.fechaHasta <= :fechaHasta AND :fechaDesde=:fechaDesde)";
/* 401:    */     }
/* 402:416 */     sqlPresupuesto = codicionPresupuesto + " INNER JOIN dpr.presupuesto pr " + " INNER JOIN dpr.partidaPresupuestaria ppp " + " INNER JOIN pr.periodo p " + " INNER JOIN pr.sucursal sp " + " WHERE ppp = :partidaPresupuestaria " + condicionFecha + " AND (sp.idSucursal =:idSucursal OR :idSucursal=0) " + " AND pr.idOrganizacion = :idOrganizacion ";
/* 403:419 */     if (centroCosto != null) {
/* 404:420 */       sqlPresupuesto = sqlPresupuesto + " AND ccp = :centroCosto";
/* 405:    */     }
/* 406:422 */     query = this.em.createQuery(sqlPresupuesto);
/* 407:423 */     query.setParameter("partidaPresupuestaria", partidaPresupuestaria);
/* 408:424 */     query.setParameter("fechaDesde", fechaDesde);
/* 409:425 */     query.setParameter("fechaHasta", fechaHasta);
/* 410:426 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 411:427 */     query.setParameter("idOrganizacion", Integer.valueOf(partidaPresupuestaria.getIdOrganizacion()));
/* 412:428 */     if (centroCosto != null) {
/* 413:429 */       query.setParameter("centroCosto", centroCosto);
/* 414:    */     }
/* 415:431 */     BigDecimal valorPresupuestoAnterior = (BigDecimal)query.getSingleResult();
/* 416:432 */     if (valorPresupuestoAnterior == null) {
/* 417:433 */       valorPresupuestoAnterior = BigDecimal.ZERO;
/* 418:    */     }
/* 419:435 */     valorPresupuestoAnterior = valorPresupuestoAnterior.add(valorPresupuestoActual);
/* 420:436 */     saldo = valorMovimientos.subtract(valorPresupuestoAnterior);
/* 421:    */     
/* 422:438 */     return saldo;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public List getReporteMayorPartidaPresupuestaria(PartidaPresupuestaria partidaPresupuestaria, Date fechaDesde, Date fechaHasta, int idSucursal, boolean indicadorNIIF, CentroCosto centroCosto)
/* 426:    */   {
/* 427:445 */     StringBuilder sql = new StringBuilder();
/* 428:446 */     sql.append("SELECT a.fecha,ppc.codigo, ppc.nombre, a.numero,ta.nombre,ccc.nombre,ccc.codigo,");
/* 429:447 */     sql.append(" (CASE WHEN da.debe > 0 THEN dacc.valor ELSE 0 END),(CASE WHEN da.haber > 0 THEN dacc.valor ELSE 0 END)");
/* 430:448 */     sql.append(" FROM DetalleAsientoCentroCosto dacc");
/* 431:449 */     sql.append(" INNER JOIN dacc.centroCosto ccc");
/* 432:450 */     sql.append(" INNER JOIN dacc.detalleAsiento da");
/* 433:451 */     sql.append(" INNER JOIN da.asiento a");
/* 434:452 */     sql.append(" INNER JOIN a.tipoAsiento ta");
/* 435:453 */     sql.append(" INNER JOIN a.sucursal sc");
/* 436:454 */     sql.append(" INNER JOIN da.cuentaContable cc");
/* 437:455 */     sql.append(" INNER JOIN cc.partidaPresupuestaria ppc");
/* 438:456 */     sql.append(" WHERE ppc = :partidaPresupuestaria");
/* 439:457 */     sql.append(" AND ta.indicadorNIIF =:indicadorNIIF");
/* 440:458 */     sql.append(" AND (sc.idSucursal =:idSucursal OR :idSucursal=0)");
/* 441:459 */     sql.append(" AND a.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 442:460 */     if (centroCosto != null) {
/* 443:461 */       sql.append(" AND ccc = :centroCosto");
/* 444:    */     }
/* 445:463 */     Query query = this.em.createQuery(sql.toString());
/* 446:464 */     query.setParameter("partidaPresupuestaria", partidaPresupuestaria);
/* 447:465 */     query.setParameter("fechaDesde", fechaDesde);
/* 448:466 */     query.setParameter("fechaHasta", fechaHasta);
/* 449:467 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 450:468 */     query.setParameter("indicadorNIIF", Boolean.valueOf(indicadorNIIF));
/* 451:469 */     if (centroCosto != null) {
/* 452:470 */       query.setParameter("centroCosto", centroCosto);
/* 453:    */     }
/* 454:472 */     return query.getResultList();
/* 455:    */   }
/* 456:    */   
/* 457:    */   public List<Object[]> getReportePresupuesto(Ejercicio ejercicio, Mes mes, int longitudCodigo, DimensionContable dimensionContable, List<DimensionContable> listDimensionContable, int tipoReporte, int idUsuario, int idOrganizacion)
/* 458:    */   {
/* 459:478 */     List<DimensionContable> listDimensionesHijas = null;
/* 460:479 */     if (dimensionContable != null)
/* 461:    */     {
/* 462:480 */       Map<String, String> filters = new HashMap();
/* 463:481 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 464:482 */       filters.put("codigo", dimensionContable.getCodigo() + "%");
/* 465:483 */       filters.put("numero", dimensionContable.getNumero());
/* 466:484 */       filters.put("activo", "true");
/* 467:485 */       listDimensionesHijas = this.servicioDimensionContable.obtenerListaCombo("codigo", true, filters);
/* 468:    */     }
/* 469:487 */     else if (longitudCodigo == 0) {}
/* 470:492 */     StringBuilder sql = new StringBuilder();
/* 471:494 */     if (tipoReporte == 1)
/* 472:    */     {
/* 473:495 */       sql.append("SELECT e.nombre, dc.codigo, dc.nombre, cc.codigo, cc.nombre,");
/* 474:496 */       if (mes == null)
/* 475:    */       {
/* 476:497 */         sql.append(" dpr.valorEnero, dpr.valorFebrero, dpr.valorMarzo, dpr.valorAbril, dpr.valorMayo, dpr.valorJunio,");
/* 477:498 */         sql.append(" dpr.valorJulio, dpr.valorAgosto, dpr.valorSeptiembre, dpr.valorOctubre, dpr.valorNoviembre, dpr.valorDiciembre,");
/* 478:499 */         sql.append(" dpr.transferenciasIngresoEnero, dpr.transferenciasEgresoEnero, dpr.incrementosEnero, dpr.decrementosEnero,");
/* 479:500 */         sql.append(" dpr.transferenciasIngresoFebrero, dpr.transferenciasEgresoFebrero, dpr.incrementosFebrero, dpr.decrementosFebrero,");
/* 480:501 */         sql.append(" dpr.transferenciasIngresoMarzo, dpr.transferenciasEgresoMarzo, dpr.incrementosMarzo, dpr.decrementosMarzo,");
/* 481:502 */         sql.append(" dpr.transferenciasIngresoAbril, dpr.transferenciasEgresoAbril, dpr.incrementosAbril, dpr.decrementosAbril,");
/* 482:503 */         sql.append(" dpr.transferenciasIngresoMayo, dpr.transferenciasEgresoMayo, dpr.incrementosMayo, dpr.decrementosMayo,");
/* 483:504 */         sql.append(" dpr.transferenciasIngresoJunio, dpr.transferenciasEgresoJunio, dpr.incrementosJunio, dpr.decrementosJunio,");
/* 484:505 */         sql.append(" dpr.transferenciasIngresoJulio, dpr.transferenciasEgresoJulio, dpr.incrementosJulio, dpr.decrementosJulio,");
/* 485:506 */         sql.append(" dpr.transferenciasIngresoAgosto, dpr.transferenciasEgresoAgosto, dpr.incrementosAgosto, dpr.decrementosAgosto,");
/* 486:507 */         sql.append(" dpr.transferenciasIngresoSeptiembre, dpr.transferenciasEgresoSeptiembre, dpr.incrementosSeptiembre, dpr.decrementosSeptiembre,");
/* 487:508 */         sql.append(" dpr.transferenciasIngresoOctubre, dpr.transferenciasEgresoOctubre, dpr.incrementosOctubre, dpr.decrementosOctubre,");
/* 488:509 */         sql.append(" dpr.transferenciasIngresoNoviembre, dpr.transferenciasEgresoNoviembre, dpr.incrementosNoviembre, dpr.decrementosNoviembre,");
/* 489:510 */         sql.append(" dpr.transferenciasIngresoDiciembre, dpr.transferenciasEgresoDiciembre, dpr.incrementosDiciembre, dpr.decrementosDiciembre");
/* 490:    */       }
/* 491:    */       else
/* 492:    */       {
/* 493:512 */         switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$Mes[mes.ordinal()])
/* 494:    */         {
/* 495:    */         case 1: 
/* 496:514 */           sql.append(" dpr.valorEnero, dpr.transferenciasIngresoEnero, dpr.transferenciasEgresoEnero, dpr.incrementosEnero, dpr.decrementosEnero");
/* 497:515 */           break;
/* 498:    */         case 2: 
/* 499:517 */           sql.append(" dpr.valorFebrero, dpr.transferenciasIngresoFebrero, dpr.transferenciasEgresoFebrero, dpr.incrementosFebrero, dpr.decrementosFebrero");
/* 500:518 */           break;
/* 501:    */         case 3: 
/* 502:520 */           sql.append(" dpr.valorMarzo, dpr.transferenciasIngresoMarzo, dpr.transferenciasEgresoMarzo, dpr.incrementosMarzo, dpr.decrementosMarzo");
/* 503:521 */           break;
/* 504:    */         case 4: 
/* 505:523 */           sql.append(" dpr.valorAbril, dpr.transferenciasIngresoAbril, dpr.transferenciasEgresoAbril, dpr.incrementosAbril, dpr.decrementosAbril");
/* 506:524 */           break;
/* 507:    */         case 5: 
/* 508:526 */           sql.append(" dpr.valorMayo, dpr.transferenciasIngresoMayo, dpr.transferenciasEgresoMayo, dpr.incrementosMayo, dpr.decrementosMayo");
/* 509:527 */           break;
/* 510:    */         case 6: 
/* 511:529 */           sql.append(" dpr.valorJunio, dpr.transferenciasIngresoJunio, dpr.transferenciasEgresoJunio, dpr.incrementosJunio, dpr.decrementosJunio");
/* 512:530 */           break;
/* 513:    */         case 7: 
/* 514:532 */           sql.append(" dpr.valorJulio, dpr.transferenciasIngresoJulio, dpr.transferenciasEgresoJulio, dpr.incrementosJulio, dpr.decrementosJulio");
/* 515:533 */           break;
/* 516:    */         case 8: 
/* 517:535 */           sql.append(" dpr.valorAgosto, dpr.transferenciasIngresoAgosto, dpr.transferenciasEgresoAgosto, dpr.incrementosAgosto, dpr.decrementosAgosto");
/* 518:536 */           break;
/* 519:    */         case 9: 
/* 520:538 */           sql.append(" dpr.valorSeptiembre, dpr.transferenciasIngresoSeptiembre, dpr.transferenciasEgresoSeptiembre, dpr.incrementosSeptiembre, dpr.decrementosSeptiembre");
/* 521:539 */           break;
/* 522:    */         case 10: 
/* 523:541 */           sql.append(" dpr.valorOctubre, dpr.transferenciasIngresoOctubre, dpr.transferenciasEgresoOctubre, dpr.incrementosOctubre, dpr.decrementosOctubre");
/* 524:542 */           break;
/* 525:    */         case 11: 
/* 526:544 */           sql.append(" dpr.valorNoviembre, dpr.transferenciasIngresoNoviembre, dpr.transferenciasEgresoNoviembre, dpr.incrementosNoviembre, dpr.decrementosNoviembre");
/* 527:545 */           break;
/* 528:    */         case 12: 
/* 529:547 */           sql.append(" dpr.valorDiciembre, dpr.transferenciasIngresoDiciembre, dpr.transferenciasEgresoDiciembre, dpr.incrementosDiciembre, dpr.decrementosDiciembre");
/* 530:548 */           break;
/* 531:    */         }
/* 532:    */       }
/* 533:    */     }
/* 534:    */     else
/* 535:    */     {
/* 536:554 */       sql.append("SELECT e.nombre, dc.codigo, dc.nombre, '', '',");
/* 537:555 */       sql.append(" SUM(dpr.valorEnero), SUM(dpr.valorFebrero), SUM(dpr.valorMarzo), SUM(dpr.valorAbril), SUM(dpr.valorMayo), SUM(dpr.valorJunio),");
/* 538:556 */       sql.append(" SUM(dpr.valorJulio), SUM(dpr.valorAgosto), SUM(dpr.valorSeptiembre), SUM(dpr.valorOctubre), SUM(dpr.valorNoviembre), SUM(dpr.valorDiciembre)");
/* 539:    */     }
/* 540:558 */     sql.append(" FROM DetallePresupuesto dpr");
/* 541:559 */     sql.append(" INNER JOIN dpr.presupuesto pr");
/* 542:560 */     sql.append(" INNER JOIN pr.ejercicio e");
/* 543:561 */     sql.append(" INNER JOIN dpr.dimensionContable dc");
/* 544:562 */     sql.append(" INNER JOIN dpr.cuentaContable cc");
/* 545:563 */     sql.append(" WHERE pr.idOrganizacion=:idOrganizacion");
/* 546:564 */     sql.append(" AND e=:ejercicio");
/* 547:565 */     if ((dimensionContable == null) && (longitudCodigo == 0))
/* 548:    */     {
/* 549:566 */       sql.append(" AND dc.idDimensionContable in");
/* 550:567 */       sql.append(" (SELECT dcu.idDimensionContable FROM UsuarioDimensionContable udc");
/* 551:568 */       sql.append(" INNER JOIN udc.dimensionContable dcu");
/* 552:569 */       sql.append(" INNER JOIN udc.entidadUsuario eu");
/* 553:570 */       sql.append(" WHERE eu.idUsuario=:idUsuario)");
/* 554:571 */       sql.append(" AND udc.indicadorPresupuesto = true");
/* 555:    */     }
/* 556:572 */     else if ((dimensionContable == null) && (longitudCodigo != 0))
/* 557:    */     {
/* 558:574 */       sql.append(" AND dc.idDimensionContable in");
/* 559:575 */       sql.append(" (SELECT dcu.idDimensionContable FROM UsuarioDimensionContable udc");
/* 560:576 */       sql.append(" INNER JOIN udc.dimensionContable dcu");
/* 561:577 */       sql.append(" INNER JOIN udc.entidadUsuario eu");
/* 562:578 */       sql.append(" WHERE eu.idUsuario=:idUsuario)");
/* 563:579 */       sql.append(" AND udc.indicadorPresupuesto = true");
/* 564:    */     }
/* 565:    */     else
/* 566:    */     {
/* 567:581 */       sql.append(" AND dc IN :listDimensionesHijas");
/* 568:    */     }
/* 569:584 */     if (tipoReporte == 1)
/* 570:    */     {
/* 571:585 */       sql.append(" ORDER BY dc.codigo, cc.codigo");
/* 572:    */     }
/* 573:    */     else
/* 574:    */     {
/* 575:587 */       sql.append(" GROUP BY dc.codigo, dc.nombre, e.nombre");
/* 576:588 */       sql.append(" ORDER BY dc.codigo");
/* 577:    */     }
/* 578:591 */     Query query = this.em.createQuery(sql.toString());
/* 579:592 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 580:    */     
/* 581:594 */     query.setParameter("ejercicio", ejercicio);
/* 582:596 */     if ((dimensionContable == null) && (longitudCodigo == 0)) {
/* 583:597 */       query.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 584:598 */     } else if ((dimensionContable == null) && (longitudCodigo != 0)) {
/* 585:599 */       query.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 586:    */     } else {
/* 587:602 */       query.setParameter("listDimensionesHijas", listDimensionesHijas);
/* 588:    */     }
/* 589:605 */     return query.getResultList();
/* 590:    */   }
/* 591:    */   
/* 592:    */   public BigDecimal obtenerMovimiemtos(Ejercicio ejercicio, Mes mes, short operacion, String codigoCuentaContable, String codigoDimensionContable, int idOrganizacion)
/* 593:    */   {
/* 594:610 */     StringBuilder sql = new StringBuilder();
/* 595:    */     
/* 596:612 */     sql.append("SELECT SUM(dmpp.valor)");
/* 597:613 */     sql.append(" FROM DetalleMovimientoPartidaPresupuestaria dmpp");
/* 598:614 */     sql.append(" INNER JOIN dmpp.dimensionContableOrigen dco");
/* 599:615 */     sql.append(" INNER JOIN dmpp.cuentaContableOrigen cco");
/* 600:616 */     if (operacion == 0)
/* 601:    */     {
/* 602:617 */       sql.append(" INNER JOIN dmpp.dimensionContableDestino dcd");
/* 603:618 */       sql.append(" INNER JOIN dmpp.cuentaContableDestino ccd");
/* 604:    */     }
/* 605:620 */     sql.append(" INNER JOIN dmpp.movimientoPartidaPresupuestaria mpp");
/* 606:621 */     sql.append(" INNER JOIN mpp.ejercicio e");
/* 607:622 */     sql.append(" INNER JOIN mpp.documento d");
/* 608:623 */     sql.append(" WHERE mpp.idOrganizacion=:idOrganizacion");
/* 609:    */     
/* 610:625 */     sql.append(" AND e=:ejercicio");
/* 611:626 */     if (operacion == 0)
/* 612:    */     {
/* 613:628 */       sql.append(" AND dcd.codigo=:codigoDimensionContable");
/* 614:629 */       sql.append(" AND ccd.codigo=:codigoCuentaContable");
/* 615:    */     }
/* 616:    */     else
/* 617:    */     {
/* 618:632 */       sql.append(" AND dco.codigo=:codigoDimensionContable");
/* 619:633 */       sql.append(" AND cco.codigo=:codigoCuentaContable");
/* 620:    */     }
/* 621:637 */     Query query = this.em.createQuery(sql.toString());
/* 622:638 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 623:639 */     query.setParameter("ejercicio", ejercicio);
/* 624:    */     
/* 625:    */ 
/* 626:642 */     query.setParameter("codigoCuentaContable", codigoCuentaContable);
/* 627:643 */     query.setParameter("codigoDimensionContable", codigoDimensionContable);
/* 628:644 */     BigDecimal result = BigDecimal.ZERO;
/* 629:    */     try
/* 630:    */     {
/* 631:646 */       result = (BigDecimal)query.getSingleResult();
/* 632:    */     }
/* 633:    */     catch (NoResultException localNoResultException) {}
/* 634:650 */     return result;
/* 635:    */   }
/* 636:    */   
/* 637:    */   public List<Object[]> getReportePresupuestoComparativo(Ejercicio ejercicio, Mes mesSeleccionado, Sucursal sucursal, int idOrganizacion, String dimensionPresupuesto, DimensionContable dimensionContable, List<DimensionContable> listaDimensionContable, int tipoReporte, int idUsuario)
/* 638:    */   {
/* 639:657 */     if (dimensionContable != null)
/* 640:    */     {
/* 641:658 */       Map<String, String> filters = new HashMap();
/* 642:659 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 643:660 */       filters.put("codigo", dimensionContable.getCodigo() + "%");
/* 644:661 */       filters.put("numero", dimensionContable.getNumero());
/* 645:662 */       filters.put("activo", "true");
/* 646:663 */       listaDimensionContable = this.servicioDimensionContable.obtenerListaCombo("codigo", true, filters);
/* 647:    */     }
/* 648:665 */     StringBuilder sql = new StringBuilder();
/* 649:    */     
/* 650:667 */     sql.append("SELECT e.nombre, dc.codigo, dc.nombre, cc.codigo, cc.nombre,");
/* 651:668 */     if (mesSeleccionado == null)
/* 652:    */     {
/* 653:669 */       if (tipoReporte == 0)
/* 654:    */       {
/* 655:670 */         sql.append(" dpr.valorEnero, dpr.saldoRealEnero, ");
/* 656:671 */         sql.append(" dpr.valorFebrero, dpr.saldoRealFebrero, ");
/* 657:672 */         sql.append(" dpr.valorMarzo, dpr.saldoRealMarzo, ");
/* 658:673 */         sql.append(" dpr.valorAbril, dpr.saldoRealAbril, ");
/* 659:674 */         sql.append(" dpr.valorMayo, dpr.saldoRealMayo, ");
/* 660:675 */         sql.append(" dpr.valorJunio, dpr.saldoRealJunio, ");
/* 661:676 */         sql.append(" dpr.valorJulio, dpr.saldoRealJulio, ");
/* 662:677 */         sql.append(" dpr.valorAgosto, dpr.saldoRealAgosto, ");
/* 663:678 */         sql.append(" dpr.valorSeptiembre, dpr.saldoRealSeptiembre, ");
/* 664:679 */         sql.append(" dpr.valorOctubre, dpr.saldoRealOctubre, ");
/* 665:680 */         sql.append(" dpr.valorNoviembre, dpr.saldoRealNoviembre, ");
/* 666:681 */         sql.append(" dpr.valorDiciembre, dpr.saldoRealDiciembre ");
/* 667:    */       }
/* 668:682 */       else if (tipoReporte == 1)
/* 669:    */       {
/* 670:683 */         sql.append(" dpr.valorEnero, dpr.saldoComprometidoEnero, ");
/* 671:684 */         sql.append(" dpr.valorFebrero, dpr.saldoComprometidoFebrero, ");
/* 672:685 */         sql.append(" dpr.valorMarzo, dpr.saldoComprometidoMarzo, ");
/* 673:686 */         sql.append(" dpr.valorAbril, dpr.saldoComprometidoAbril, ");
/* 674:687 */         sql.append(" dpr.valorMayo, dpr.saldoComprometidoMayo, ");
/* 675:688 */         sql.append(" dpr.valorJunio, dpr.saldoComprometidoJunio, ");
/* 676:689 */         sql.append(" dpr.valorJulio, dpr.saldoComprometidoJulio, ");
/* 677:690 */         sql.append(" dpr.valorAgosto, dpr.saldoComprometidoAgosto, ");
/* 678:691 */         sql.append(" dpr.valorSeptiembre, dpr.saldoComprometidoSeptiembre, ");
/* 679:692 */         sql.append(" dpr.valorOctubre, dpr.saldoComprometidoOctubre, ");
/* 680:693 */         sql.append(" dpr.valorNoviembre, dpr.saldoComprometidoNoviembre, ");
/* 681:694 */         sql.append(" dpr.valorDiciembre, dpr.saldoComprometidoDiciembre ");
/* 682:    */       }
/* 683:    */       else
/* 684:    */       {
/* 685:696 */         sql.append(" dpr.valorEnero, dpr.saldoComprometidoEnero, dpr.saldoRealEnero, ");
/* 686:697 */         sql.append(" dpr.valorFebrero, dpr.saldoComprometidoFebrero, dpr.saldoRealFebrero, ");
/* 687:698 */         sql.append(" dpr.valorMarzo, dpr.saldoComprometidoMarzo, dpr.saldoRealMarzo, ");
/* 688:699 */         sql.append(" dpr.valorAbril, dpr.saldoComprometidoAbril, dpr.saldoRealAbril, ");
/* 689:700 */         sql.append(" dpr.valorMayo, dpr.saldoComprometidoMayo, dpr.saldoRealMayo, ");
/* 690:701 */         sql.append(" dpr.valorJunio, dpr.saldoComprometidoJunio, dpr.saldoRealJunio, ");
/* 691:702 */         sql.append(" dpr.valorJulio, dpr.saldoComprometidoJulio, dpr.saldoRealJulio, ");
/* 692:703 */         sql.append(" dpr.valorAgosto, dpr.saldoComprometidoAgosto, dpr.saldoRealAgosto, ");
/* 693:704 */         sql.append(" dpr.valorSeptiembre, dpr.saldoComprometidoSeptiembre, dpr.saldoRealSeptiembre, ");
/* 694:705 */         sql.append(" dpr.valorOctubre, dpr.saldoComprometidoOctubre, dpr.saldoRealOctubre, ");
/* 695:706 */         sql.append(" dpr.valorNoviembre, dpr.saldoComprometidoNoviembre, dpr.saldoRealNoviembre, ");
/* 696:707 */         sql.append(" dpr.valorDiciembre, dpr.saldoComprometidoDiciembre, dpr.saldoRealDiciembre ");
/* 697:    */       }
/* 698:    */     }
/* 699:    */     else {
/* 700:711 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$Mes[mesSeleccionado.ordinal()])
/* 701:    */       {
/* 702:    */       case 1: 
/* 703:713 */         if (tipoReporte == 0) {
/* 704:714 */           sql.append(" dpr.valorEnero, dpr.saldoRealEnero ");
/* 705:715 */         } else if (tipoReporte == 1) {
/* 706:716 */           sql.append(" dpr.valorEnero, dpr.saldoComprometidoEnero ");
/* 707:    */         } else {
/* 708:718 */           sql.append(" dpr.valorEnero, dpr.saldoComprometidoEnero, dpr.saldoRealEnero ");
/* 709:    */         }
/* 710:720 */         break;
/* 711:    */       case 2: 
/* 712:722 */         if (tipoReporte == 0) {
/* 713:723 */           sql.append(" dpr.valorFebrero, dpr.saldoRealFebrero ");
/* 714:724 */         } else if (tipoReporte == 1) {
/* 715:725 */           sql.append(" dpr.valorFebrero, dpr.saldoComprometidoFebrero ");
/* 716:    */         } else {
/* 717:727 */           sql.append(" dpr.valorFebrero, dpr.saldoComprometidoFebrero, dpr.saldoRealFebrero ");
/* 718:    */         }
/* 719:729 */         break;
/* 720:    */       case 3: 
/* 721:731 */         if (tipoReporte == 0) {
/* 722:732 */           sql.append(" dpr.valorMarzo, dpr.saldoRealMarzo ");
/* 723:733 */         } else if (tipoReporte == 1) {
/* 724:734 */           sql.append(" dpr.valorMarzo, dpr.saldoComprometidoMarzo ");
/* 725:    */         } else {
/* 726:736 */           sql.append(" dpr.valorMarzo, dpr.saldoComprometidoMarzo, dpr.saldoRealMarzo ");
/* 727:    */         }
/* 728:738 */         break;
/* 729:    */       case 4: 
/* 730:740 */         if (tipoReporte == 0) {
/* 731:741 */           sql.append(" dpr.valorAbril, dpr.saldoRealAbril ");
/* 732:742 */         } else if (tipoReporte == 1) {
/* 733:743 */           sql.append(" dpr.valorAbril, dpr.saldoComprometidoAbril ");
/* 734:    */         } else {
/* 735:745 */           sql.append(" dpr.valorAbril, dpr.saldoComprometidoAbril, dpr.saldoRealAbril ");
/* 736:    */         }
/* 737:747 */         break;
/* 738:    */       case 5: 
/* 739:749 */         if (tipoReporte == 0) {
/* 740:750 */           sql.append(" dpr.valorMayo, dpr.saldoRealMayo ");
/* 741:751 */         } else if (tipoReporte == 1) {
/* 742:752 */           sql.append(" dpr.valorMayo, dpr.saldoComprometidoMayo ");
/* 743:    */         } else {
/* 744:754 */           sql.append(" dpr.valorMayo, dpr.saldoComprometidoMayo, dpr.saldoRealMayo ");
/* 745:    */         }
/* 746:756 */         break;
/* 747:    */       case 6: 
/* 748:758 */         if (tipoReporte == 0) {
/* 749:759 */           sql.append(" dpr.valorJunio, dpr.saldoRealJunio ");
/* 750:760 */         } else if (tipoReporte == 1) {
/* 751:761 */           sql.append(" dpr.valorJunio, dpr.saldoComprometidoJunio ");
/* 752:    */         } else {
/* 753:763 */           sql.append(" dpr.valorJunio, dpr.saldoComprometidoJunio, dpr.saldoRealJunio ");
/* 754:    */         }
/* 755:765 */         break;
/* 756:    */       case 7: 
/* 757:767 */         if (tipoReporte == 0) {
/* 758:768 */           sql.append(" dpr.valorJulio, dpr.saldoRealJulio ");
/* 759:769 */         } else if (tipoReporte == 1) {
/* 760:770 */           sql.append(" dpr.valorJulio, dpr.saldoComprometidoJulio ");
/* 761:    */         } else {
/* 762:772 */           sql.append(" dpr.valorJulio, dpr.saldoComprometidoJulio, dpr.saldoRealJulio ");
/* 763:    */         }
/* 764:774 */         break;
/* 765:    */       case 8: 
/* 766:776 */         if (tipoReporte == 0) {
/* 767:777 */           sql.append(" dpr.valorAgosto, dpr.saldoRealAgosto ");
/* 768:778 */         } else if (tipoReporte == 1) {
/* 769:779 */           sql.append(" dpr.valorAgosto, dpr.saldoComprometidoAgosto ");
/* 770:    */         } else {
/* 771:781 */           sql.append(" dpr.valorAgosto, dpr.saldoComprometidoAgosto, dpr.saldoRealAgosto ");
/* 772:    */         }
/* 773:783 */         break;
/* 774:    */       case 9: 
/* 775:785 */         if (tipoReporte == 0) {
/* 776:786 */           sql.append(" dpr.valorSeptiembre, dpr.saldoRealSeptiembre ");
/* 777:787 */         } else if (tipoReporte == 1) {
/* 778:788 */           sql.append(" dpr.valorSeptiembre, dpr.saldoComprometidoSeptiembre ");
/* 779:    */         } else {
/* 780:790 */           sql.append(" dpr.valorSeptiembre, dpr.saldoComprometidoSeptiembre, dpr.saldoRealSeptiembre ");
/* 781:    */         }
/* 782:792 */         break;
/* 783:    */       case 10: 
/* 784:794 */         if (tipoReporte == 0) {
/* 785:795 */           sql.append(" dpr.valorOctubre, dpr.saldoRealOctubre ");
/* 786:796 */         } else if (tipoReporte == 1) {
/* 787:797 */           sql.append(" dpr.valorOctubre, dpr.saldoComprometidoOctubre ");
/* 788:    */         } else {
/* 789:799 */           sql.append(" dpr.valorOctubre, dpr.saldoComprometidoOctubre, dpr.saldoRealOctubre ");
/* 790:    */         }
/* 791:801 */         break;
/* 792:    */       case 11: 
/* 793:803 */         if (tipoReporte == 0) {
/* 794:804 */           sql.append(" dpr.valorNoviembre, dpr.saldoRealNoviembre ");
/* 795:805 */         } else if (tipoReporte == 1) {
/* 796:806 */           sql.append(" dpr.valorNoviembre, dpr.saldoComprometidoNoviembre ");
/* 797:    */         } else {
/* 798:808 */           sql.append(" dpr.valorNoviembre, dpr.saldoComprometidoNoviembre, dpr.saldoRealNoviembre ");
/* 799:    */         }
/* 800:810 */         break;
/* 801:    */       case 12: 
/* 802:812 */         if (tipoReporte == 0) {
/* 803:813 */           sql.append(" dpr.valorDiciembre, dpr.saldoRealDiciembre ");
/* 804:814 */         } else if (tipoReporte == 1) {
/* 805:815 */           sql.append(" dpr.valorDiciembre, dpr.saldoComprometidoDiciembre ");
/* 806:    */         } else {
/* 807:817 */           sql.append(" dpr.valorDiciembre, dpr.saldoComprometidoDiciembre, dpr.saldoRealDiciembre ");
/* 808:    */         }
/* 809:819 */         break;
/* 810:    */       }
/* 811:    */     }
/* 812:824 */     sql.append(" FROM DetallePresupuesto dpr");
/* 813:825 */     sql.append(" INNER JOIN dpr.presupuesto pr");
/* 814:826 */     sql.append(" INNER JOIN pr.ejercicio e");
/* 815:827 */     sql.append(" INNER JOIN dpr.dimensionContable dc");
/* 816:828 */     sql.append(" INNER JOIN dpr.cuentaContable cc");
/* 817:829 */     sql.append(" WHERE pr.idOrganizacion=:idOrganizacion");
/* 818:830 */     sql.append(" AND e=:ejercicio");
/* 819:831 */     if (dimensionContable == null)
/* 820:    */     {
/* 821:832 */       sql.append(" AND dc.idDimensionContable in");
/* 822:833 */       sql.append(" (SELECT dcu.idDimensionContable FROM UsuarioDimensionContable udc");
/* 823:834 */       sql.append(" INNER JOIN udc.dimensionContable dcu");
/* 824:835 */       sql.append(" INNER JOIN udc.entidadUsuario eu");
/* 825:836 */       sql.append(" WHERE eu.idUsuario=:idUsuario)");
/* 826:837 */       sql.append(" AND udc.indicadorPresupuesto = true");
/* 827:    */     }
/* 828:    */     else
/* 829:    */     {
/* 830:839 */       sql.append(" AND dc IN :listDimensionesHijas");
/* 831:    */     }
/* 832:842 */     if (tipoReporte == 1) {
/* 833:843 */       sql.append(" ORDER BY dc.codigo, cc.codigo");
/* 834:    */     }
/* 835:845 */     Query query = this.em.createQuery(sql.toString());
/* 836:846 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 837:    */     
/* 838:848 */     query.setParameter("ejercicio", ejercicio);
/* 839:850 */     if (dimensionContable == null) {
/* 840:851 */       query.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 841:    */     } else {
/* 842:853 */       query.setParameter("listDimensionesHijas", listaDimensionContable);
/* 843:    */     }
/* 844:856 */     return query.getResultList();
/* 845:    */   }
/* 846:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.presupuesto.ReportePresupuestoDao
 * JD-Core Version:    0.7.0.1
 */