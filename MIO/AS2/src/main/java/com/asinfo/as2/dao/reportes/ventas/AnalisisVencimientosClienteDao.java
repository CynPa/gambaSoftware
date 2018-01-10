/*   1:    */ package com.asinfo.as2.dao.reportes.ventas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   8:    */ import com.asinfo.as2.entities.Recaudador;
/*   9:    */ import com.asinfo.as2.entities.Subempresa;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.Zona;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import com.asinfo.as2.enumeraciones.EstadoGarantiaCliente;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import java.math.BigDecimal;
/*  18:    */ import java.math.RoundingMode;
/*  19:    */ import java.text.SimpleDateFormat;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Date;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.Iterator;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.Stateless;
/*  27:    */ import javax.persistence.EntityManager;
/*  28:    */ import javax.persistence.Query;
/*  29:    */ import javax.persistence.TemporalType;
/*  30:    */ 
/*  31:    */ @Stateless
/*  32:    */ public class AnalisisVencimientosClienteDao
/*  33:    */   extends AbstractDaoAS2<FacturaCliente>
/*  34:    */ {
/*  35:    */   public AnalisisVencimientosClienteDao()
/*  36:    */   {
/*  37: 54 */     super(FacturaCliente.class);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List getAnalisisVencimientoCliente(Date fechaHasta, Empresa empresa, Recaudador recaudador, int idOrganizacion, Subempresa subempresa, EntidadUsuario agenteComercial, Sucursal sucursal, PuntoDeVenta puntoDeVenta, Zona zona, int numeroDia)
/*  41:    */   {
/*  42: 69 */     Date fechaMenos120 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -120);
/*  43: 70 */     Date fechaMenos90 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -90);
/*  44: 71 */     Date fechaMenos60 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -60);
/*  45: 72 */     Date fechaMenos30 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -30);
/*  46: 73 */     Date fechaMenosNumeroDia = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -numeroDia);
/*  47: 74 */     Date fechaMas120 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 120);
/*  48: 75 */     Date fechaMas90 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 90);
/*  49: 76 */     Date fechaMas60 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 60);
/*  50: 77 */     Date fechaMas30 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 30);
/*  51: 78 */     Date fechaMasNumeroDia = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, numeroDia);
/*  52:    */     
/*  53: 80 */     StringBuilder sql = new StringBuilder();
/*  54: 81 */     sql.append(" SELECT vec.codigo,vec.nombreFiscal,vec.codigoTipoIdentificacion,vec.identificacion,vec.numeroFactura,vec.fechaFactura,vec.fechaVencimiento,vec.diasPlazoFactura,vec.nombreAgenteComercial, ");
/*  55:    */     
/*  56: 83 */     sql.append(" vec.empresaFinal, ");
/*  57: 84 */     sql.append(" SUM(vec.debito), ");
/*  58: 85 */     sql.append(" SUM(vec.debito-vec.credito), ");
/*  59: 86 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento <  :fechaMenos120 THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  60: 87 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos120 AND vec.fechaVencimiento < :fechaMenos90  THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  61:    */     
/*  62: 89 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos90  AND vec.fechaVencimiento < :fechaMenos60  THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  63:    */     
/*  64: 91 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos60  AND vec.fechaVencimiento < :fechaMenos30  THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  65:    */     
/*  66: 93 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos30  AND vec.fechaVencimiento < :fechaMenosNumeroDia  THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  67:    */     
/*  68: 95 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenosNumeroDia  AND vec.fechaVencimiento < :fechaHasta    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  69:    */     
/*  70: 97 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaHasta    AND vec.fechaVencimiento < :fechaMasNumeroDia    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  71:    */     
/*  72: 99 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMasNumeroDia    AND vec.fechaVencimiento < :fechaMas30    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  73:    */     
/*  74:101 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas30    AND vec.fechaVencimiento < :fechaMas60    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  75:    */     
/*  76:103 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas60    AND vec.fechaVencimiento < :fechaMas90    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  77:    */     
/*  78:105 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas90    AND vec.fechaVencimiento < :fechaMas120   THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  79:    */     
/*  80:107 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas120   THEN (vec.debito-vec.credito) ELSE 0 END), ");
/*  81:108 */     sql.append(" SUM(vec.debito-vec.credito), vec.codigoDocumento, SUM(vec.valorBloqueado), SUM(vec.debito), fc.referencia2, see.nombreFiscal ");
/*  82:109 */     sql.append(" FROM FacturaCliente fc, VEstadoCuenta vec ");
/*  83:110 */     sql.append(" LEFT JOIN fc.subempresa se ");
/*  84:111 */     sql.append(" LEFT JOIN se.empresa see ");
/*  85:112 */     sql.append(" WHERE vec.idFacturaCliente = fc.idFacturaCliente AND EXISTS (SELECT ec2.idFacturaCliente FROM VEstadoCuenta ec2 WHERE ec2.idFacturaCliente=vec.idFacturaCliente AND ec2.fechaDocumento<=:fechaHasta GROUP BY ec2.idFacturaCliente HAVING SUM(ec2.debito-ec2.credito) <> 0 ) ");
/*  86:    */     
/*  87:114 */     sql.append(" AND vec.idOrganizacion = :idOrganizacion ");
/*  88:115 */     if ((null != empresa) && (empresa.getId() != 0)) {
/*  89:116 */       sql.append(" AND vec.idEmpresa = :idEmpresa ");
/*  90:    */     }
/*  91:118 */     if ((null != recaudador) && (recaudador.getId() != 0)) {
/*  92:119 */       sql.append(" AND vec.idRecaudador = :idRecaudador ");
/*  93:    */     }
/*  94:121 */     if ((null != subempresa) && (subempresa.getId() != 0)) {
/*  95:122 */       sql.append(" AND vec.idSubempresa = :idSubempresa ");
/*  96:    */     }
/*  97:124 */     if ((null != agenteComercial) && (agenteComercial.getId() != 0)) {
/*  98:125 */       sql.append(" AND vec.idAgenteComercial = :idAgenteComercial ");
/*  99:    */     }
/* 100:127 */     if ((null != sucursal) && (sucursal.getId() != 0)) {
/* 101:128 */       sql.append(" AND vec.idSucursal = :idSucursal ");
/* 102:    */     }
/* 103:130 */     if (null != zona) {
/* 104:131 */       sql.append(" AND vec.idZona = :idZona ");
/* 105:    */     }
/* 106:133 */     if (puntoDeVenta != null) {
/* 107:134 */       sql.append(" AND vec.numeroFactura LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/* 108:    */     }
/* 109:136 */     sql.append(" AND vec.fechaDocumento <= :fechaHasta ");
/* 110:137 */     sql.append(" GROUP BY vec.codigo,vec.nombreFiscal,vec.codigoTipoIdentificacion,vec.identificacion,vec.numeroFactura,vec.fechaFactura,vec.fechaVencimiento,vec.diasPlazoFactura,vec.nombreAgenteComercial, ");
/* 111:    */     
/* 112:139 */     sql.append(" vec.empresaFinal, vec.codigoDocumento, fc.referencia2, see.nombreFiscal ");
/* 113:140 */     sql.append(" HAVING SUM(vec.debito-vec.credito) <> 0 ");
/* 114:141 */     sql.append(" ORDER BY vec.nombreFiscal ASC, vec.fechaVencimiento ASC, vec.numeroFactura ASC ");
/* 115:    */     
/* 116:143 */     Query query = this.em.createQuery(sql.toString());
/* 117:144 */     query.setParameter("fechaMenos120", fechaMenos120, TemporalType.DATE);
/* 118:145 */     query.setParameter("fechaMenos90", fechaMenos90, TemporalType.DATE);
/* 119:146 */     query.setParameter("fechaMenos60", fechaMenos60, TemporalType.DATE);
/* 120:147 */     query.setParameter("fechaMenos30", fechaMenos30, TemporalType.DATE);
/* 121:148 */     query.setParameter("fechaMenosNumeroDia", fechaMenosNumeroDia, TemporalType.DATE);
/* 122:149 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 123:150 */     query.setParameter("fechaMasNumeroDia", fechaMasNumeroDia, TemporalType.DATE);
/* 124:151 */     query.setParameter("fechaMas30", fechaMas30, TemporalType.DATE);
/* 125:152 */     query.setParameter("fechaMas60", fechaMas60, TemporalType.DATE);
/* 126:153 */     query.setParameter("fechaMas90", fechaMas90, TemporalType.DATE);
/* 127:154 */     query.setParameter("fechaMas120", fechaMas120, TemporalType.DATE);
/* 128:155 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 129:156 */     if ((null != empresa) && (empresa.getId() != 0)) {
/* 130:157 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/* 131:    */     }
/* 132:159 */     if ((null != recaudador) && (recaudador.getId() != 0)) {
/* 133:160 */       query.setParameter("idRecaudador", Integer.valueOf(recaudador.getId()));
/* 134:    */     }
/* 135:162 */     if ((null != subempresa) && (subempresa.getId() != 0)) {
/* 136:163 */       query.setParameter("idSubempresa", Integer.valueOf(subempresa.getId()));
/* 137:    */     }
/* 138:165 */     if ((null != agenteComercial) && (agenteComercial.getId() != 0)) {
/* 139:166 */       query.setParameter("idAgenteComercial", Integer.valueOf(agenteComercial.getId()));
/* 140:    */     }
/* 141:168 */     if ((null != sucursal) && (sucursal.getId() != 0)) {
/* 142:169 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/* 143:    */     }
/* 144:171 */     if (null != zona) {
/* 145:172 */       query.setParameter("idZona", Integer.valueOf(zona.getId()));
/* 146:    */     }
/* 147:174 */     if ((puntoDeVenta != null) && (puntoDeVenta.getId() != 0)) {
/* 148:175 */       query.setParameter("codigoPuntoVenta", puntoDeVenta.getCodigo());
/* 149:    */     }
/* 150:178 */     return query.getResultList();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List getAnalisisDinarpad(Date fechaHasta, Date fechaDesde, int idEmpresa, int idRecaudador, int idOrganizacion, int idSubempresa, int idAgenteComercial, Sucursal sucursal, int diasMinimo, BigDecimal montoMinimo)
/* 154:    */   {
/* 155:195 */     Date fechaMenos360 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -360);
/* 156:196 */     Date fechaMenos90 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -90);
/* 157:197 */     Date fechaMenos180 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -180);
/* 158:198 */     Date fechaMenos30 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, -30);
/* 159:199 */     Date fechaMas360 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 360);
/* 160:200 */     Date fechaMas90 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 90);
/* 161:201 */     Date fechaMas180 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 180);
/* 162:202 */     Date fechaMas30 = FuncionesUtiles.sumarFechaDiasMeses(fechaHasta, 30);
/* 163:    */     
/* 164:204 */     StringBuilder sql = new StringBuilder();
/* 165:205 */     sql.append(" SELECT vec.codigo,vec.nombreFiscal,vec.codigoTipoIdentificacion,vec.identificacion,vec.numeroFactura,vec.fechaFactura, ");
/* 166:206 */     sql.append(" vec.fechaVencimientoFactura,vec.diasPlazoFactura,vec.nombreAgenteComercial, vec.empresaFinal, fc.total-fc.descuento+fc.impuesto, SUM(vec.debito-vec.credito), ");
/* 167:    */     
/* 168:208 */     sql.append(" MIN(CASE WHEN vec.fechaVencimiento > :fechaHasta THEN vec.fechaVencimiento else null end), ");
/* 169:209 */     sql.append(" MIN(CASE WHEN vec.fechaVencimiento < :fechaHasta THEN vec.fechaVencimiento else null end), ");
/* 170:210 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento <  :fechaMenos360 THEN (vec.debito-vec.credito) ELSE 0 END), ");
/* 171:211 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos360 AND vec.fechaVencimiento < :fechaMenos180  THEN (vec.debito-vec.credito) ELSE 0 END), ");
/* 172:    */     
/* 173:213 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos180  AND vec.fechaVencimiento < :fechaMenos90  THEN (vec.debito-vec.credito) ELSE 0 END), ");
/* 174:    */     
/* 175:215 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMenos90  AND vec.fechaVencimiento <= :fechaMenos30  THEN (vec.debito-vec.credito) ELSE 0 END), ");
/* 176:    */     
/* 177:217 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento > :fechaMenos30  AND vec.fechaVencimiento < :fechaHasta    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/* 178:    */     
/* 179:219 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaHasta    AND vec.fechaVencimiento < :fechaMas30    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/* 180:    */     
/* 181:    */ 
/* 182:222 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas30    AND vec.fechaVencimiento < :fechaMas90    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/* 183:    */     
/* 184:    */ 
/* 185:225 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas90    AND vec.fechaVencimiento < :fechaMas180    THEN (vec.debito-vec.credito) ELSE 0 END), ");
/* 186:    */     
/* 187:    */ 
/* 188:228 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas180    AND vec.fechaVencimiento < :fechaMas360   THEN (vec.debito-vec.credito) ELSE 0 END), ");
/* 189:    */     
/* 190:    */ 
/* 191:231 */     sql.append(" SUM(CASE WHEN vec.fechaVencimiento >= :fechaMas360   THEN (vec.debito-vec.credito) ELSE 0 END), ");
/* 192:232 */     sql.append(" SUM(vec.debito-vec.credito), vec.codigoDocumento, SUM(vec.valorBloqueado), SUM(vec.debito), ");
/* 193:    */     
/* 194:    */ 
/* 195:235 */     sql.append(" vec.tipoEmpresa, ");
/* 196:236 */     sql.append(" ' ', ");
/* 197:237 */     sql.append(" ' ', ");
/* 198:238 */     sql.append(" CASE WHEN fc.numeroCuotas > 1 THEN MAX(vec.debito) ELSE 0 END , vec.origenIngresosCodigo, e.genero, ec.nombre, ");
/* 199:239 */     sql.append(" pro.codigo, pa.codigo, c.codigo, fc.idFacturaCliente ");
/* 200:    */     
/* 201:241 */     sql.append(" FROM FacturaCliente fc, VEstadoCuenta vec, CuentaPorCobrar cxc ");
/* 202:242 */     sql.append(" LEFT JOIN fc.empresa e ");
/* 203:243 */     sql.append(" LEFT JOIN e.estadoCivil ec ");
/* 204:244 */     sql.append(" LEFT JOIN e.tipoIdentificacion ti ");
/* 205:245 */     sql.append(" LEFT JOIN fc.direccionEmpresa de ");
/* 206:246 */     sql.append(" LEFT JOIN de.ciudad c ");
/* 207:247 */     sql.append(" LEFT JOIN c.provincia pro ");
/* 208:248 */     sql.append(" LEFT JOIN de.parroquia pa ");
/* 209:    */     
/* 210:250 */     sql.append(" WHERE vec.idFacturaCliente = fc.idFacturaCliente and vec.idCuentaPorCobrar = cxc.idCuentaPorCobrar ");
/* 211:251 */     sql.append(" AND e.indicadorEntidadPublica = false ");
/* 212:252 */     sql.append(" AND vec.idOrganizacion = :idOrganizacion ");
/* 213:253 */     sql.append(" AND vec.fechaDocumento <= :fechaHasta ");
/* 214:254 */     sql.append(" AND vec.diasPlazoFactura > 0");
/* 215:255 */     sql.append(" AND e.indicadorEntidadPublica != true ");
/* 216:256 */     sql.append(" AND ti.indicadorValidarIdentificacion != false ");
/* 217:257 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 218:258 */       sql.append(" AND vec.idSucursal = :idSucursal ");
/* 219:    */     }
/* 220:261 */     sql.append(" GROUP BY vec.codigo,vec.nombreFiscal,vec.codigoTipoIdentificacion,vec.identificacion,vec.numeroFactura,vec.fechaFactura,vec.fechaVencimientoFactura,vec.diasPlazoFactura,vec.nombreAgenteComercial, ");
/* 221:    */     
/* 222:263 */     sql.append(" vec.empresaFinal, vec.codigoDocumento, vec.tipoEmpresa, vec.origenIngresosCodigo, e.genero, ec.nombre, pro.codigo, pa.codigo, c.codigo, fc.idFacturaCliente, fc.numeroCuotas, fc.total,fc.descuento,fc.impuesto ");
/* 223:    */     
/* 224:265 */     sql.append(" HAVING (SUM(vec.debito-vec.credito) <> 0 OR MAX(vec.fechaDocumento) >= :fechaDesde) AND SUM(vec.debito) >= :montoMinimo ");
/* 225:266 */     sql.append(" ORDER BY vec.nombreFiscal ");
/* 226:    */     
/* 227:268 */     Query query = this.em.createQuery(sql.toString());
/* 228:269 */     query.setParameter("fechaMenos360", fechaMenos360, TemporalType.DATE);
/* 229:270 */     query.setParameter("fechaMenos90", fechaMenos90, TemporalType.DATE);
/* 230:271 */     query.setParameter("fechaMenos180", fechaMenos180, TemporalType.DATE);
/* 231:272 */     query.setParameter("fechaMenos30", fechaMenos30, TemporalType.DATE);
/* 232:273 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 233:274 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 234:275 */     query.setParameter("fechaMas30", fechaMas30, TemporalType.DATE);
/* 235:276 */     query.setParameter("fechaMas180", fechaMas180, TemporalType.DATE);
/* 236:277 */     query.setParameter("fechaMas90", fechaMas90, TemporalType.DATE);
/* 237:278 */     query.setParameter("fechaMas360", fechaMas360, TemporalType.DATE);
/* 238:279 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 239:280 */     query.setParameter("montoMinimo", montoMinimo);
/* 240:281 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 241:282 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 242:    */     }
/* 243:285 */     List<Object[]> lista = query.getResultList();
/* 244:286 */     List<Object[]> result = new ArrayList();
/* 245:    */     
/* 246:288 */     List<List<Integer>> listaTotal = new ArrayList();
/* 247:    */     
/* 248:290 */     listaTotal.add(new ArrayList());
/* 249:291 */     int contador = 0;
/* 250:292 */     int numeroListas = 0;
/* 251:293 */     for (Iterator localIterator1 = lista.iterator(); localIterator1.hasNext();)
/* 252:    */     {
/* 253:293 */       objects = (Object[])localIterator1.next();
/* 254:294 */       if (contador == 2000)
/* 255:    */       {
/* 256:295 */         listaTotal.add(new ArrayList());
/* 257:296 */         numeroListas++;
/* 258:297 */         contador = 0;
/* 259:    */       }
/* 260:299 */       ((List)listaTotal.get(numeroListas)).add((Integer)objects[38]);
/* 261:300 */       contador++;
/* 262:    */     }
/* 263:    */     Object[] objects;
/* 264:303 */     Object mapaInfo = new HashMap();
/* 265:305 */     for (List<Integer> listaFC : listaTotal)
/* 266:    */     {
/* 267:306 */       StringBuilder sql1 = new StringBuilder();
/* 268:307 */       sql1.append(" SELECT COALESCE(fp.codigoAlterno, fp.codigo), c.fecha, fp.indicadorTarjetaCredito, fc.idFacturaCliente, dfc.valor ");
/* 269:    */       
/* 270:309 */       sql1.append(" FROM DetalleFormaCobro dfc ");
/* 271:310 */       sql1.append(" INNER JOIN dfc.cobro c ");
/* 272:311 */       sql1.append(" INNER JOIN dfc.formaPago fp ");
/* 273:312 */       sql1.append(" LEFT JOIN c.listaDetalleCobro dc ");
/* 274:313 */       sql1.append(" INNER JOIN dc.cuentaPorCobrar cxc ");
/* 275:314 */       sql1.append(" INNER JOIN cxc.facturaCliente fc ");
/* 276:315 */       sql1.append(" WHERE fc.idFacturaCliente in :listaFC ");
/* 277:316 */       sql1.append(" AND fp.indicadorRetencionFuente = false");
/* 278:317 */       sql1.append(" AND fp.indicadorRetencionIva = false ");
/* 279:318 */       sql1.append(" AND c.estado <> :anulado ");
/* 280:319 */       sql1.append(" AND CASE WHEN dfc.fechaProtesto < :fechaHasta THEN dfc.indicadorChequeProtestado ELSE false END = false ");
/* 281:320 */       sql1.append(" AND exists (SELECT v.idCuentaPorCobrar FROM VEstadoCuenta v where v.idCuentaPorCobrar = cxc.idCuentaPorCobrar AND v.fechaDocumento <= :fechaHasta GROUP BY v.idCuentaPorCobrar HAVING (SUM(v.debito-v.credito) <= 0)) ");
/* 282:    */       
/* 283:    */ 
/* 284:323 */       sql1.append(" ORDER BY c.fecha ");
/* 285:    */       
/* 286:325 */       Query query1 = this.em.createQuery(sql1.toString());
/* 287:326 */       query1.setParameter("listaFC", listaFC);
/* 288:327 */       query1.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 289:328 */       query1.setParameter("anulado", Estado.ANULADO);
/* 290:    */       
/* 291:330 */       List<Object[]> listaTmp = query1.getResultList();
/* 292:332 */       for (Object[] objects : listaTmp)
/* 293:    */       {
/* 294:334 */         List<Object[]> lista11 = (List)((Map)mapaInfo).get((Integer)objects[3]);
/* 295:335 */         if (lista11 == null)
/* 296:    */         {
/* 297:336 */           lista11 = new ArrayList();
/* 298:337 */           ((Map)mapaInfo).put((Integer)objects[3], lista11);
/* 299:    */         }
/* 300:339 */         lista11.add(objects);
/* 301:    */       }
/* 302:    */     }
/* 303:344 */     for (Object[] objects : lista)
/* 304:    */     {
/* 305:345 */       Object[] newObjects = new Object[38];
/* 306:    */       
/* 307:347 */       Date fechaFactura = (Date)objects[5];
/* 308:348 */       Date fechaVencimiento = (Date)objects[6];
/* 309:349 */       int idFacturaCliente = ((Integer)objects[38]).intValue();
/* 310:352 */       if (((BigDecimal)objects[11]).compareTo(BigDecimal.ZERO) <= 0)
/* 311:    */       {
/* 312:353 */         objects[11] = BigDecimal.ZERO;
/* 313:354 */         objects[13] = null;
/* 314:    */       }
/* 315:356 */       if (((BigDecimal)objects[14]).compareTo(BigDecimal.ZERO) < 0) {
/* 316:357 */         objects[14] = BigDecimal.ZERO;
/* 317:    */       }
/* 318:359 */       if (((BigDecimal)objects[15]).compareTo(BigDecimal.ZERO) < 0) {
/* 319:360 */         objects[15] = BigDecimal.ZERO;
/* 320:    */       }
/* 321:362 */       if (((BigDecimal)objects[16]).compareTo(BigDecimal.ZERO) < 0) {
/* 322:363 */         objects[16] = BigDecimal.ZERO;
/* 323:    */       }
/* 324:365 */       if (((BigDecimal)objects[17]).compareTo(BigDecimal.ZERO) < 0) {
/* 325:366 */         objects[17] = BigDecimal.ZERO;
/* 326:    */       }
/* 327:368 */       if (((BigDecimal)objects[18]).compareTo(BigDecimal.ZERO) < 0) {
/* 328:369 */         objects[18] = BigDecimal.ZERO;
/* 329:    */       }
/* 330:371 */       if (((BigDecimal)objects[19]).compareTo(BigDecimal.ZERO) < 0) {
/* 331:372 */         objects[19] = BigDecimal.ZERO;
/* 332:    */       }
/* 333:374 */       if (((BigDecimal)objects[20]).compareTo(BigDecimal.ZERO) < 0) {
/* 334:375 */         objects[20] = BigDecimal.ZERO;
/* 335:    */       }
/* 336:377 */       if (((BigDecimal)objects[21]).compareTo(BigDecimal.ZERO) < 0) {
/* 337:378 */         objects[21] = BigDecimal.ZERO;
/* 338:    */       }
/* 339:380 */       if (((BigDecimal)objects[22]).compareTo(BigDecimal.ZERO) < 0) {
/* 340:381 */         objects[22] = BigDecimal.ZERO;
/* 341:    */       }
/* 342:383 */       if (((BigDecimal)objects[23]).compareTo(BigDecimal.ZERO) < 0) {
/* 343:384 */         objects[23] = BigDecimal.ZERO;
/* 344:    */       }
/* 345:386 */       if (((BigDecimal)objects[24]).compareTo(BigDecimal.ZERO) < 0) {
/* 346:387 */         objects[24] = BigDecimal.ZERO;
/* 347:    */       }
/* 348:392 */       if (FuncionesUtiles.diferenciasDeFechas(fechaFactura, fechaVencimiento) - 1 >= diasMinimo)
/* 349:    */       {
/* 350:393 */         for (int i = 0; i < 38; i++) {
/* 351:394 */           if ((i != 29) && (i != 30))
/* 352:    */           {
/* 353:395 */             newObjects[i] = objects[i];
/* 354:    */           }
/* 355:397 */           else if (i == 29)
/* 356:    */           {
/* 357:399 */             newObjects[i] = null;
/* 358:400 */             newObjects[(i + 1)] = null;
/* 359:    */             
/* 360:402 */             List<Object[]> lista1 = (List)((Map)mapaInfo).get(Integer.valueOf(idFacturaCliente));
/* 361:403 */             BigDecimal aux = new BigDecimal(0);
/* 362:405 */             if ((lista1 != null) && (lista1.size() > 0))
/* 363:    */             {
/* 364:406 */               for (Object[] o : lista1)
/* 365:    */               {
/* 366:407 */                 if (((BigDecimal)o[4]).compareTo(aux) == 1)
/* 367:    */                 {
/* 368:408 */                   if (((Boolean)o[2]).booleanValue() == true)
/* 369:    */                   {
/* 370:409 */                     newObjects[i] = ((String)o[0]).substring(0, 1);
/* 371:410 */                     newObjects[(i + 1)] = o[1];
/* 372:411 */                     break;
/* 373:    */                   }
/* 374:413 */                   newObjects[i] = ((String)o[0]).substring(0, 1);
/* 375:414 */                   newObjects[(i + 1)] = o[1];
/* 376:    */                 }
/* 377:416 */                 aux = (BigDecimal)o[4];
/* 378:    */               }
/* 379:    */             }
/* 380:    */             else
/* 381:    */             {
/* 382:423 */               StringBuilder sql2 = new StringBuilder();
/* 383:    */               
/* 384:425 */               sql2.append(" SELECT fc.fecha ");
/* 385:426 */               sql2.append(" FROM FacturaCliente fc ");
/* 386:427 */               sql2.append(" INNER JOIN fc.facturaClientePadre fcp ");
/* 387:428 */               sql2.append(" WHERE fcp.idFacturaCliente = :idFacturaCliente ");
/* 388:429 */               sql2.append(" AND fc.fecha <= :fechaHasta ");
/* 389:    */               
/* 390:431 */               Query query2 = this.em.createQuery(sql2.toString());
/* 391:432 */               query2.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
/* 392:433 */               query2.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 393:    */               
/* 394:435 */               List lista2 = query2.getResultList();
/* 395:437 */               if ((lista2 != null) && (lista2.size() > 0))
/* 396:    */               {
/* 397:438 */                 newObjects[i] = "E";
/* 398:439 */                 newObjects[(i + 1)] = lista2.get(0);
/* 399:    */               }
/* 400:    */             }
/* 401:    */           }
/* 402:    */         }
/* 403:445 */         result.add(newObjects);
/* 404:    */       }
/* 405:    */     }
/* 406:449 */     return result;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public List getVencimientos(int idEmpresa, Date fecha, int idRecaudador, int idOrganizacion, int idSubempresa, int idAgenteComercial, Sucursal sucursal, PuntoDeVenta puntoDeVenta, Zona zona, CategoriaEmpresa categoriaEmpresa, boolean indicadorMostrarPosfechados)
/* 410:    */     throws ExcepcionAS2
/* 411:    */   {
/* 412:458 */     StringBuilder sql = new StringBuilder();
/* 413:459 */     sql.append(" SELECT ec.codigo,ec.nombreFiscal,ec.identificacion,ec.numeroFactura,ec.fechaFactura,ec.fechaVencimiento,  ");
/* 414:460 */     sql.append(" SUM(CASE WHEN ec.fechaVencimiento <= :fecha THEN (ec.debito-ec.credito) ELSE 0 END), ");
/* 415:461 */     sql.append(" SUM(CASE WHEN ec.fechaVencimiento > :fecha THEN (ec.debito-ec.credito) ELSE 0 END), ");
/* 416:462 */     sql.append(" SUM(ec.debito-ec.credito), ec.codigoDocumento, ");
/* 417:463 */     sql.append(" de.telefono1, CONCAT(ub.direccion1, ' ', ub.direccion2, ' ', ub.direccion3, ' ', ub.direccion4), ");
/* 418:464 */     sql.append(" cont.nombre, cp.nombre, fpag.nombre, cl.creditoMaximo, cl.creditoUtilizado, emp.descripcion, ");
/* 419:465 */     sql.append(" fc.referencia2, CONCAT(ac.nombre2 ,' ', ac.nombre1), see.nombreFiscal, pro.codigo, pro.nombre,'','','','','' ");
/* 420:466 */     if (indicadorMostrarPosfechados) {
/* 421:467 */       sql.append(" , ec.idCuentaPorCobrar ");
/* 422:    */     }
/* 423:469 */     sql.append(" FROM FacturaCliente fc ");
/* 424:470 */     sql.append(" INNER JOIN fc.direccionEmpresa de ");
/* 425:471 */     sql.append(" INNER JOIN fc.empresa emp ");
/* 426:472 */     sql.append(" INNER JOIN emp.categoriaEmpresa cemp ");
/* 427:473 */     sql.append(" INNER JOIN emp.cliente cl ");
/* 428:474 */     sql.append(" INNER JOIN cl.formaPago fpag ");
/* 429:475 */     sql.append(" LEFT JOIN fc.condicionPago cp ");
/* 430:476 */     sql.append(" LEFT JOIN fc.proyecto pro ");
/* 431:477 */     sql.append(" LEFT OUTER JOIN fc.agenteComercial ac ");
/* 432:478 */     sql.append(" LEFT JOIN de.ubicacion ub ");
/* 433:479 */     sql.append(" LEFT JOIN fc.pedidoCliente pc ");
/* 434:480 */     sql.append(" LEFT JOIN pc.contacto cont ");
/* 435:481 */     sql.append(" LEFT JOIN fc.subempresa se ");
/* 436:482 */     sql.append(" LEFT JOIN se.empresa see, VEstadoCuenta ec ");
/* 437:483 */     sql.append(" WHERE ec.idFacturaCliente = fc.idFacturaCliente ");
/* 438:484 */     sql.append(" AND EXISTS (SELECT ec2.idFacturaCliente FROM VEstadoCuenta ec2 WHERE ec2.idFacturaCliente=ec.idFacturaCliente AND ec2.fechaDocumento<=:fecha GROUP BY ec2.idFacturaCliente HAVING SUM(ec2.debito-ec2.credito) <> 0 ) ");
/* 439:    */     
/* 440:486 */     sql.append(" AND ec.fechaDocumento<=:fecha ");
/* 441:487 */     sql.append(" AND ec.idOrganizacion = :idOrganizacion ");
/* 442:488 */     sql.append(" AND (ec.idEmpresa = :idEmpresa OR :idEmpresa=0) ");
/* 443:489 */     sql.append(" AND (ec.idRecaudador = :idRecaudador OR :idRecaudador = 0) ");
/* 444:490 */     sql.append(" AND (ec.idAgenteComercial = :idAgenteComercial OR :idAgenteComercial = 0) ");
/* 445:491 */     sql.append(" AND (ec.idSubempresa = :idSubempresa OR :idSubempresa = 0) ");
/* 446:492 */     if (categoriaEmpresa != null) {
/* 447:493 */       sql.append(" AND cemp.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 448:    */     }
/* 449:495 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 450:496 */       sql.append(" AND ec.idSucursal = :idSucursal ");
/* 451:    */     }
/* 452:498 */     if (null != zona) {
/* 453:499 */       sql.append(" AND ec.idZona = :idZona ");
/* 454:    */     }
/* 455:501 */     if (puntoDeVenta != null) {
/* 456:502 */       sql.append(" AND ec.numeroFactura LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/* 457:    */     }
/* 458:504 */     sql.append(" GROUP BY ec.codigo,ec.nombreFiscal,ec.identificacion,ec.numeroFactura,ec.fechaFactura,ec.fechaVencimiento, ec.codigoDocumento ");
/* 459:505 */     sql.append(" , de.telefono1, CONCAT(ub.direccion1, ' ', ub.direccion2, ' ', ub.direccion3, ' ', ub.direccion4) ");
/* 460:506 */     sql.append(" , cont.nombre, cp.nombre, fpag.nombre, cl.creditoMaximo, cl.creditoUtilizado, emp.descripcion, fc.referencia2, ac.nombre2 , ac.nombre1, see.nombreFiscal, pro.codigo, pro.nombre ");
/* 461:508 */     if (indicadorMostrarPosfechados) {
/* 462:509 */       sql.append(" , ec.idCuentaPorCobrar ");
/* 463:    */     }
/* 464:511 */     sql.append(" HAVING SUM(ec.debito-ec.credito) <> 0 ");
/* 465:512 */     sql.append(" ORDER BY ec.nombreFiscal,ec.numeroFactura ");
/* 466:    */     
/* 467:514 */     Query query = this.em.createQuery(sql.toString());
/* 468:515 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 469:516 */     query.setParameter("fecha", fecha);
/* 470:517 */     query.setParameter("idRecaudador", Integer.valueOf(idRecaudador));
/* 471:518 */     query.setParameter("idSubempresa", Integer.valueOf(idSubempresa));
/* 472:519 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 473:520 */     query.setParameter("idAgenteComercial", Integer.valueOf(idAgenteComercial));
/* 474:521 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 475:522 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 476:    */     }
/* 477:524 */     if (null != zona) {
/* 478:525 */       query.setParameter("idZona", Integer.valueOf(zona.getId()));
/* 479:    */     }
/* 480:527 */     if (puntoDeVenta != null) {
/* 481:528 */       query.setParameter("codigoPuntoVenta", puntoDeVenta.getCodigo());
/* 482:    */     }
/* 483:530 */     if (categoriaEmpresa != null) {
/* 484:531 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getId()));
/* 485:    */     }
/* 486:534 */     List<Object[]> lista = query.getResultList();
/* 487:535 */     List<Object[]> resultado = lista;
/* 488:    */     SimpleDateFormat sdf;
/* 489:536 */     if (indicadorMostrarPosfechados)
/* 490:    */     {
/* 491:538 */       sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 492:539 */       resultado = new ArrayList();
/* 493:540 */       for (Object[] objects : lista)
/* 494:    */       {
/* 495:541 */         Object[] filaNew = new Object[28];
/* 496:543 */         for (int i = 0; i < 28; i++) {
/* 497:544 */           filaNew[i] = objects[i];
/* 498:    */         }
/* 499:547 */         int idCuentaPorCobrar = ((Integer)objects[28]).intValue();
/* 500:    */         
/* 501:549 */         StringBuilder sql2 = new StringBuilder();
/* 502:550 */         sql2.append(" SELECT b.nombre, dfc.documentoReferencia, c.fecha, gc.fechaCobro, dfc.valor ");
/* 503:551 */         sql2.append(" FROM DetalleCobroFormaCobro dcfc ");
/* 504:552 */         sql2.append(" INNER JOIN dcfc.detalleCobro dc ");
/* 505:553 */         sql2.append(" INNER JOIN dcfc.detalleFormaCobro dfc ");
/* 506:554 */         sql2.append(" INNER JOIN dc.cobro c ");
/* 507:555 */         sql2.append(" INNER JOIN dc.cuentaPorCobrar cxc ");
/* 508:556 */         sql2.append(" INNER JOIN dfc.banco b ");
/* 509:557 */         sql2.append(" INNER JOIN dfc.garantiaCliente gc ");
/* 510:558 */         sql2.append(" WHERE cxc.idCuentaPorCobrar = :idCuentaPorCobrar ");
/* 511:559 */         sql2.append(" AND dfc.indicadorChequePosfechado IS TRUE ");
/* 512:560 */         sql2.append(" AND gc.estadoGarantiaCliente = :estadoGarantiaRegistrado ");
/* 513:    */         
/* 514:562 */         Query query2 = this.em.createQuery(sql2.toString());
/* 515:563 */         query2.setParameter("idCuentaPorCobrar", Integer.valueOf(idCuentaPorCobrar));
/* 516:564 */         query2.setParameter("estadoGarantiaRegistrado", EstadoGarantiaCliente.REGISTRADO);
/* 517:    */         
/* 518:566 */         List<Object[]> listaPosfechados = query2.getResultList();
/* 519:567 */         String banco = null;
/* 520:568 */         String numeroCheque = null;
/* 521:569 */         String fechaCobro = null;
/* 522:570 */         String fechaACobrar = null;
/* 523:571 */         String valor = null;
/* 524:573 */         for (Object[] objects2 : listaPosfechados)
/* 525:    */         {
/* 526:575 */           if (banco == null)
/* 527:    */           {
/* 528:576 */             banco = "";
/* 529:577 */             numeroCheque = "";
/* 530:578 */             fechaCobro = "";
/* 531:579 */             fechaACobrar = "";
/* 532:580 */             valor = "";
/* 533:    */           }
/* 534:    */           else
/* 535:    */           {
/* 536:582 */             banco = banco + "\n";
/* 537:583 */             numeroCheque = numeroCheque + "\n";
/* 538:584 */             fechaCobro = fechaCobro + "\n";
/* 539:585 */             fechaACobrar = fechaACobrar + "\n";
/* 540:586 */             valor = valor + "\n";
/* 541:    */           }
/* 542:588 */           banco = banco + (String)objects2[0];
/* 543:589 */           numeroCheque = numeroCheque + (String)objects2[1];
/* 544:590 */           Date fechaCobro1 = (Date)objects2[2];
/* 545:591 */           fechaCobro = fechaCobro + sdf.format(fechaCobro1);
/* 546:592 */           Date fechaACobrar1 = (Date)objects2[3];
/* 547:593 */           fechaACobrar = fechaACobrar + sdf.format(fechaACobrar1);
/* 548:594 */           valor = valor + ((BigDecimal)objects2[4]).toString();
/* 549:    */         }
/* 550:598 */         filaNew[23] = banco;
/* 551:599 */         filaNew[24] = numeroCheque;
/* 552:600 */         filaNew[25] = fechaCobro;
/* 553:601 */         filaNew[26] = fechaACobrar;
/* 554:602 */         filaNew[27] = valor;
/* 555:603 */         resultado.add(filaNew);
/* 556:    */       }
/* 557:    */     }
/* 558:607 */     return resultado;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public BigDecimal getPorcentajeMorosidad(int idEmpresa, Date fecha)
/* 562:    */   {
/* 563:611 */     StringBuilder sql = new StringBuilder();
/* 564:612 */     sql.append(" SELECT SUM(CASE WHEN ec.fechaVencimiento <= :fecha THEN (ec.debito-ec.credito) ELSE 0 END), ");
/* 565:613 */     sql.append(" SUM(CASE WHEN ec.fechaVencimiento > :fecha THEN (ec.debito-ec.credito) ELSE 0 END) ");
/* 566:614 */     sql.append(" FROM VEstadoCuenta ec ");
/* 567:615 */     sql.append(" WHERE ec.fechaDocumento<=:fecha ");
/* 568:616 */     sql.append(" AND ec.idEmpresa = :idEmpresa ");
/* 569:    */     
/* 570:618 */     Query query = this.em.createQuery(sql.toString());
/* 571:619 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 572:620 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 573:    */     
/* 574:622 */     Object[] resultado = (Object[])query.getSingleResult();
/* 575:623 */     BigDecimal valorVencido = (BigDecimal)resultado[0];
/* 576:624 */     if (valorVencido == null) {
/* 577:625 */       valorVencido = BigDecimal.ZERO;
/* 578:    */     }
/* 579:627 */     BigDecimal valorPorVencer = (BigDecimal)resultado[1];
/* 580:628 */     if (valorPorVencer == null) {
/* 581:629 */       valorPorVencer = BigDecimal.ZERO;
/* 582:    */     }
/* 583:631 */     BigDecimal total = valorVencido.add(valorPorVencer);
/* 584:632 */     BigDecimal porcientoVencimiento = BigDecimal.ZERO;
/* 585:633 */     if (total.compareTo(BigDecimal.ZERO) != 0) {
/* 586:634 */       porcientoVencimiento = valorVencido.multiply(new BigDecimal(100)).divide(total, 2, RoundingMode.HALF_UP);
/* 587:    */     }
/* 588:636 */     return porcientoVencimiento;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public List<Empresa> getEmpresasEnvioEmailVencimiento(int idEmpresa, Date fecha, int idRecaudador, int idOrganizacion, int idSubempresa, int idAgenteComercial, Sucursal sucursal, PuntoDeVenta puntoDeVenta, Zona zona, CategoriaEmpresa categoriaEmpresa)
/* 592:    */   {
/* 593:643 */     StringBuilder sql = new StringBuilder();
/* 594:644 */     sql.append(" SELECT new Empresa(emp.idEmpresa, emp.codigo, emp.identificacion, emp.nombreFiscal, emp.nombreComercial) ");
/* 595:645 */     sql.append(" FROM VEstadoCuenta ec, Empresa emp ");
/* 596:646 */     sql.append(" INNER JOIN emp.categoriaEmpresa cemp ");
/* 597:647 */     sql.append(" WHERE ec.idEmpresa = emp.idEmpresa ");
/* 598:648 */     sql.append(" AND EXISTS (SELECT ec2.idFacturaCliente FROM VEstadoCuenta ec2 WHERE ec2.idFacturaCliente=ec.idFacturaCliente AND ec2.fechaDocumento<=:fecha GROUP BY ec2.idFacturaCliente HAVING SUM(ec2.debito-ec2.credito) <> 0 ) ");
/* 599:    */     
/* 600:650 */     sql.append(" AND ec.fechaDocumento<=:fecha ");
/* 601:651 */     sql.append(" AND ec.idOrganizacion = :idOrganizacion ");
/* 602:652 */     sql.append(" AND (ec.idEmpresa = :idEmpresa OR :idEmpresa=0) ");
/* 603:653 */     sql.append(" AND (ec.idRecaudador = :idRecaudador OR :idRecaudador = 0) ");
/* 604:654 */     sql.append(" AND (ec.idAgenteComercial = :idAgenteComercial OR :idAgenteComercial = 0) ");
/* 605:655 */     sql.append(" AND (ec.idSubempresa = :idSubempresa OR :idSubempresa = 0) ");
/* 606:656 */     if (categoriaEmpresa != null) {
/* 607:657 */       sql.append(" AND cemp.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 608:    */     }
/* 609:659 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 610:660 */       sql.append(" AND ec.idSucursal = :idSucursal ");
/* 611:    */     }
/* 612:662 */     if (null != zona) {
/* 613:663 */       sql.append(" AND ec.idZona = :idZona ");
/* 614:    */     }
/* 615:665 */     if (puntoDeVenta != null) {
/* 616:666 */       sql.append(" AND ec.numeroFactura LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/* 617:    */     }
/* 618:668 */     sql.append(" GROUP BY emp.idEmpresa, emp.codigo, emp.identificacion, emp.nombreFiscal, emp.nombreComercial ");
/* 619:669 */     sql.append(" HAVING SUM(ec.debito-ec.credito) <> 0 ");
/* 620:    */     
/* 621:671 */     Query query = this.em.createQuery(sql.toString());
/* 622:672 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 623:673 */     query.setParameter("fecha", fecha);
/* 624:674 */     query.setParameter("idRecaudador", Integer.valueOf(idRecaudador));
/* 625:675 */     query.setParameter("idSubempresa", Integer.valueOf(idSubempresa));
/* 626:676 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 627:677 */     query.setParameter("idAgenteComercial", Integer.valueOf(idAgenteComercial));
/* 628:678 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 629:679 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 630:    */     }
/* 631:681 */     if (null != zona) {
/* 632:682 */       query.setParameter("idZona", Integer.valueOf(zona.getId()));
/* 633:    */     }
/* 634:684 */     if (puntoDeVenta != null) {
/* 635:685 */       query.setParameter("codigoPuntoVenta", puntoDeVenta.getCodigo());
/* 636:    */     }
/* 637:687 */     if (categoriaEmpresa != null) {
/* 638:688 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getId()));
/* 639:    */     }
/* 640:691 */     return query.getResultList();
/* 641:    */   }
/* 642:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.ventas.AnalisisVencimientosClienteDao
 * JD-Core Version:    0.7.0.1
 */