/*   1:    */ package com.asinfo.as2.dao.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   7:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import java.math.BigDecimal;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.Stateless;
/*  17:    */ import javax.persistence.EntityManager;
/*  18:    */ import javax.persistence.Query;
/*  19:    */ import javax.persistence.TypedQuery;
/*  20:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  21:    */ import javax.persistence.criteria.CriteriaQuery;
/*  22:    */ import javax.persistence.criteria.Join;
/*  23:    */ import javax.persistence.criteria.JoinType;
/*  24:    */ import javax.persistence.criteria.Path;
/*  25:    */ import javax.persistence.criteria.Root;
/*  26:    */ 
/*  27:    */ @Stateless
/*  28:    */ public class FacturaClienteSRIDao
/*  29:    */   extends AbstractDaoAS2<FacturaClienteSRI>
/*  30:    */ {
/*  31:    */   public FacturaClienteSRIDao()
/*  32:    */   {
/*  33: 44 */     super(FacturaClienteSRI.class);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List<FacturaClienteSRI> obtenerFacturasMes(int anio, int mes, int idOrganizacion)
/*  37:    */   {
/*  38: 61 */     List<FacturaClienteSRI> lista = new ArrayList();
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46: 69 */     StringBuilder sql = new StringBuilder();
/*  47: 70 */     sql.append(" SELECT new FacturaClienteSRI(f.establecimiento, t.codigo,tc.codigo,f.identificacionCliente,");
/*  48: 71 */     sql.append(" SUM(f.montoIva),SUM(f.baseImponibleTarifaCero),SUM(f.baseImponibleDiferenteCero),SUM(f.baseImponibleNoObjetoIva),COUNT(*), ");
/*  49: 72 */     sql.append(" SUM(f.baseImponibleTarifaCero * 0), SUM(f.baseImponibleTarifaCero * 0), SUM(f.montoIce), ");
/*  50: 73 */     sql.append(" f.indicadorDocumentoElectronico, f.codigoFormaPagoSRI, SUM(fc.descuentoImpuesto))");
/*  51: 74 */     sql.append(" FROM FacturaClienteSRI f ");
/*  52: 75 */     sql.append(" LEFT JOIN f.tipoIdentificacion t ");
/*  53: 76 */     sql.append(" LEFT JOIN f.tipoComprobanteSRI tc ");
/*  54: 77 */     sql.append(" INNER JOIN f.facturaCliente fc ");
/*  55: 78 */     sql.append(" INNER JOIN fc.documento do ");
/*  56: 79 */     sql.append(" WHERE MONTH(f.fechaEmision)=:mes AND YEAR(f.fechaEmision)=:anio AND fc.estado!=:estadoAnulado ");
/*  57: 80 */     sql.append(" AND (do.documentoBase=:factura OR do.documentoBase=:notaCredito OR do.documentoBase=:notaDebito OR do.documentoBase = :devolucionCLiente) ");
/*  58: 81 */     sql.append(" AND do.indicadorDocumentoExterior = false ");
/*  59: 82 */     sql.append(" AND (do.indicadorDocumentoElectronico = false OR f.autorizacion != '0000000000')");
/*  60: 83 */     sql.append(" AND fc.idOrganizacion = :idOrganizacion ");
/*  61: 84 */     sql.append(" AND f.indicadorSaldoInicial = false  ");
/*  62: 85 */     sql.append(" AND NOT EXISTS (");
/*  63: 86 */     sql.append("                 SELECT  1 FROM  FacturaCliente fc1 ");
/*  64: 87 */     sql.append("                 JOIN   fc1.facturaClientePadre fcp ");
/*  65: 88 */     sql.append("                 JOIN   fcp.documento do1 ");
/*  66: 89 */     sql.append("                 JOIN   fc1.documento do2 ");
/*  67: 90 */     sql.append("                 WHERE  do1.indicadorDocumentoExterior = true ");
/*  68: 91 */     sql.append("                 AND    do2.documentoBase IN (:notaCredito , :devolucionCLiente, :notaDebito )");
/*  69: 92 */     sql.append("                 AND    fc=fc1 ");
/*  70: 93 */     sql.append("                ) ");
/*  71: 94 */     sql.append(" GROUP BY f.establecimiento, t.codigo,tc.codigo,f.identificacionCliente,f.indicadorDocumentoElectronico,f.codigoFormaPagoSRI ");
/*  72:    */     
/*  73: 96 */     Query query = this.em.createQuery(sql.toString());
/*  74: 97 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  75: 98 */     query.setParameter("mes", Integer.valueOf(mes));
/*  76: 99 */     query.setParameter("anio", Integer.valueOf(anio));
/*  77:100 */     query.setParameter("factura", DocumentoBase.FACTURA_CLIENTE);
/*  78:101 */     query.setParameter("notaDebito", DocumentoBase.NOTA_DEBITO_CLIENTE);
/*  79:102 */     query.setParameter("notaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/*  80:103 */     query.setParameter("devolucionCLiente", DocumentoBase.DEVOLUCION_CLIENTE);
/*  81:104 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  82:    */     
/*  83:106 */     lista.addAll(query.getResultList());
/*  84:    */     
/*  85:    */ 
/*  86:109 */     sql = new StringBuilder();
/*  87:110 */     sql.append(" SELECT new FacturaClienteSRI(f.establecimiento, t.codigo,tc.codigo,f.identificacionCliente, ");
/*  88:111 */     sql.append(" SUM(f.montoIva),SUM(f.baseImponibleTarifaCero),SUM(f.baseImponibleDiferenteCero),SUM(f.baseImponibleNoObjetoIva),SUM(0), ");
/*  89:112 */     sql.append(" SUM(f.baseImponibleTarifaCero * 0), SUM(f.valorRetenidoFuente), SUM(f.montoIce), ");
/*  90:113 */     sql.append(" f.indicadorDocumentoElectronico, f.codigoFormaPagoSRI)");
/*  91:114 */     sql.append(" FROM FacturaClienteSRI f ");
/*  92:115 */     sql.append(" LEFT JOIN f.tipoIdentificacion t ");
/*  93:116 */     sql.append(" LEFT JOIN f.tipoComprobanteSRI tc ");
/*  94:117 */     sql.append(" WHERE MONTH(f.fechaEmision)=:mes AND YEAR(f.fechaEmision)=:anio  ");
/*  95:118 */     sql.append(" AND f.facturaCliente IS NULL ");
/*  96:119 */     sql.append(" AND f.idOrganizacion = :idOrganizacion ");
/*  97:120 */     sql.append(" AND f.valorRetenidoFuente > 0 ");
/*  98:121 */     sql.append(" AND f.indicadorSaldoInicial = false  ");
/*  99:122 */     sql.append(" AND f.estado != :estadoAnulado  ");
/* 100:123 */     sql.append(" GROUP BY f.establecimiento, t.codigo,tc.codigo,f.identificacionCliente,f.indicadorDocumentoElectronico, f.codigoFormaPagoSRI ");
/* 101:    */     
/* 102:125 */     query = this.em.createQuery(sql.toString());
/* 103:126 */     query.setParameter("mes", Integer.valueOf(mes));
/* 104:127 */     query.setParameter("anio", Integer.valueOf(anio));
/* 105:128 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 106:129 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 107:    */     
/* 108:131 */     lista.addAll(query.getResultList());
/* 109:    */     
/* 110:133 */     return lista;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<FacturaClienteSRI> obtenerFacturasExportacionMes(int anio, int mes, int idOrganizacion)
/* 114:    */   {
/* 115:146 */     StringBuilder sql = new StringBuilder();
/* 116:147 */     sql.append("SELECT new FacturaClienteSRI(f.fechaEmision, f.establecimiento, f.puntoEmision, f.numero, ");
/* 117:148 */     sql.append(" f.autorizacion, f.distritoRefrendo, f.anioRefrendo, f.regimenRefrendo, f.correlativoRefrendo, f.documentoTransporteRefrendo, ");
/* 118:149 */     sql.append(" f.fechaTransaccion, f.valorFobRefrendo, f.valorFobComprobanteRefrendo, ");
/* 119:150 */     sql.append(" f.refrendo, tc.codigo, CASE WHEN de.indicadorParaisoFiscal = false THEN 'NO' ELSE 'SI' END, emp.identificacion, ti.codigo, pai.codigo, ");
/* 120:151 */     sql.append(" CASE WHEN length(f.tipoIngresoExterior) > 0 THEN f.tipoIngresoExterior WHEN length(fs2.tipoIngresoExterior) > 0 THEN fs2.tipoIngresoExterior ELSE '' end,");
/* 121:152 */     sql.append(" f.ingresoExteriorGraboImpuestos, f.valorImpuestoExportacion) ");
/* 122:153 */     sql.append(" FROM FacturaClienteSRI f LEFT JOIN f.tipoComprobanteSRI tc ");
/* 123:154 */     sql.append(" INNER JOIN f.facturaCliente fc ");
/* 124:155 */     sql.append(" LEFT JOIN fc.documento do ");
/* 125:156 */     sql.append(" LEFT JOIN fc.facturaClientePadre fcp ");
/* 126:157 */     sql.append(" LEFT JOIN fcp.facturaClienteSRI fs2");
/* 127:158 */     sql.append(" LEFT JOIN fcp.documento doo ");
/* 128:159 */     sql.append(" LEFT JOIN fc.direccionEmpresa de ");
/* 129:160 */     sql.append(" LEFT JOIN fc.empresa emp ");
/* 130:161 */     sql.append(" LEFT JOIN emp.tipoIdentificacion ti ");
/* 131:162 */     sql.append(" LEFT JOIN de.ciudad ciu ");
/* 132:163 */     sql.append(" LEFT JOIN ciu.provincia pro ");
/* 133:164 */     sql.append(" LEFT JOIN pro.pais pai ");
/* 134:165 */     sql.append(" WHERE MONTH(f.fechaEmision)=:mes AND YEAR(f.fechaEmision)=:anio AND fc.estado!=:estadoAnulado ");
/* 135:166 */     sql.append(" AND (fc.documento.documentoBase=:factura OR fc.documento.documentoBase=:notaCredito OR fc.documento.documentoBase=:notaDebito) ");
/* 136:167 */     sql.append(" AND ( fc.documento.indicadorDocumentoExterior = true  OR doo.indicadorDocumentoExterior = true  ) ");
/* 137:168 */     sql.append(" AND (do.indicadorDocumentoElectronico = false OR f.autorizacion != '0000000000')");
/* 138:169 */     sql.append(" AND f.indicadorSaldoInicial!=true ");
/* 139:170 */     sql.append(" AND f.facturaCliente.idOrganizacion = :idOrganizacion ");
/* 140:    */     
/* 141:172 */     Query query = this.em.createQuery(sql.toString());
/* 142:173 */     query.setParameter("mes", Integer.valueOf(mes));
/* 143:174 */     query.setParameter("anio", Integer.valueOf(anio));
/* 144:175 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 145:176 */     query.setParameter("factura", DocumentoBase.FACTURA_CLIENTE);
/* 146:177 */     query.setParameter("notaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 147:178 */     query.setParameter("notaDebito", DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 148:179 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 149:180 */     return query.getResultList();
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<FacturaClienteSRI> obtenerValoresRetenidosMes(int anio, int mes, int idOrganizacion)
/* 153:    */   {
/* 154:198 */     StringBuilder sql = new StringBuilder();
/* 155:199 */     sql.append(" SELECT new FacturaClienteSRI(t.codigo, tc.codigo, fcSRI.identificacionCliente, SUM(fcSRI.baseImponibleTarifaCero * 0),");
/* 156:200 */     sql.append(" SUM(fcSRI.baseImponibleTarifaCero * 0), SUM(fcSRI.baseImponibleTarifaCero * 0), SUM(fcSRI.baseImponibleTarifaCero * 0), COUNT(*), ");
/* 157:201 */     sql.append(" SUM(CASE WHEN f.indicadorRetencionIva=true THEN dcfc.valor ELSE 0 END),");
/* 158:202 */     sql.append(" SUM(CASE WHEN f.indicadorRetencionFuente=true THEN dcfc.valor ELSE 0 END),");
/* 159:203 */     sql.append(" fcSRI.indicadorDocumentoElectronico, fcSRI.codigoFormaPagoSRI)");
/* 160:204 */     sql.append(" from DetalleCobroFormaCobro dcfc ");
/* 161:205 */     sql.append(" inner join dcfc.detalleCobro dc ");
/* 162:206 */     sql.append(" inner join dcfc.detalleFormaCobro dfc ");
/* 163:207 */     sql.append(" inner join dfc.formaPago f ");
/* 164:208 */     sql.append(" inner join dc.cuentaPorCobrar cxc ");
/* 165:209 */     sql.append(" inner join cxc.facturaCliente fc ");
/* 166:210 */     sql.append(" inner join fc.documento do ");
/* 167:211 */     sql.append(" inner join fc.empresa em ");
/* 168:212 */     sql.append(" inner join fc.facturaClienteSRI fcSRI  ");
/* 169:213 */     sql.append(" inner join em.cliente cl ");
/* 170:214 */     sql.append(" inner join dc.cobro co ");
/* 171:215 */     sql.append(" LEFT JOIN fcSRI.tipoIdentificacion t ");
/* 172:216 */     sql.append(" LEFT JOIN fcSRI.tipoComprobanteSRI tc ");
/* 173:217 */     sql.append(" WHERE MONTH(co.fecha)=:mes AND YEAR(co.fecha)=:anio");
/* 174:218 */     sql.append(" AND fc.estado != :estadoAnulado AND co.estado != :estadoAnulado ");
/* 175:219 */     sql.append(" AND do.indicadorDocumentoExterior = false");
/* 176:220 */     sql.append(" AND fc.idOrganizacion = :idOrganizacion");
/* 177:221 */     sql.append(" AND (f.indicadorRetencionFuente=true OR f.indicadorRetencionIva=true)");
/* 178:222 */     sql.append(" GROUP BY t.codigo,tc.codigo,fcSRI.identificacionCliente,fcSRI.indicadorDocumentoElectronico,fcSRI.codigoFormaPagoSRI");
/* 179:    */     
/* 180:    */ 
/* 181:225 */     Query query = this.em.createQuery(sql.toString());
/* 182:226 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 183:227 */     query.setParameter("mes", Integer.valueOf(mes));
/* 184:228 */     query.setParameter("anio", Integer.valueOf(anio));
/* 185:229 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 186:230 */     return query.getResultList();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void eliminarFacturaClienteSRI(Integer idFacturaClienteSRI)
/* 190:    */   {
/* 191:241 */     Integer idFacturaProveedorSRI = Integer.valueOf(0);
/* 192:    */     
/* 193:243 */     Query query1 = this.em.createQuery(" SELECT fp.idFacturaProveedorSRI FROM FacturaProveedorSRI fp JOIN fp.facturaClienteSRI fc WHERE fc.idFacturaclienteSRI =:idFacturaClienteSRI ");
/* 194:244 */     query1.setParameter("idFacturaClienteSRI", idFacturaClienteSRI);
/* 195:245 */     if (!query1.getResultList().isEmpty()) {
/* 196:246 */       idFacturaProveedorSRI = (Integer)query1.getResultList().get(0);
/* 197:    */     }
/* 198:249 */     Query query = this.em.createQuery("UPDATE FacturaClienteSRI f SET f.estado = :estadoAnulado WHERE f.idFacturaclienteSRI=:idFacturaClienteSRI");
/* 199:250 */     query.setParameter("idFacturaClienteSRI", idFacturaClienteSRI);
/* 200:251 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 201:252 */     query.executeUpdate();
/* 202:    */     
/* 203:254 */     Query query2 = this.em.createQuery("UPDATE FacturaProveedorSRI f  SET f.estado = :estadoAnulado WHERE f.idFacturaProveedorSRI=:idFacturaProveedorSRI");
/* 204:255 */     query2.setParameter("idFacturaProveedorSRI", idFacturaProveedorSRI);
/* 205:256 */     query2.setParameter("estadoAnulado", Estado.ANULADO);
/* 206:257 */     query2.executeUpdate();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public FacturaClienteSRI buscarFacturaClienteSRIPorFacturaCliente(FacturaCliente facturaCliente)
/* 210:    */     throws ExcepcionAS2
/* 211:    */   {
/* 212:263 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 213:264 */     CriteriaQuery<FacturaClienteSRI> cqDetalle = criteriaBuilder.createQuery(FacturaClienteSRI.class);
/* 214:265 */     Root<FacturaClienteSRI> from = cqDetalle.from(FacturaClienteSRI.class);
/* 215:266 */     from.fetch("tipoComprobanteSRI", JoinType.LEFT);
/* 216:267 */     from.fetch("facturaCliente", JoinType.LEFT);
/* 217:    */     
/* 218:269 */     Path<Integer> pathIdFacturaCliente = from.join("facturaCliente").get("idFacturaCliente");
/* 219:270 */     cqDetalle.where(criteriaBuilder.equal(pathIdFacturaCliente, Integer.valueOf(facturaCliente.getIdFacturaCliente())));
/* 220:271 */     CriteriaQuery<FacturaClienteSRI> select = cqDetalle.select(from);
/* 221:272 */     return (FacturaClienteSRI)this.em.createQuery(select).getSingleResult();
/* 222:    */   }
/* 223:    */   
/* 224:    */   public List<FacturaClienteSRI> obtenerFacturasPorSerieEntreNumero(String establecimiento, String puntoVenta, Date fechaDesde, Date fechaHasta, Organizacion organizacion)
/* 225:    */   {
/* 226:277 */     Query query = this.em.createQuery("SELECT f  FROM FacturaClienteSRI f LEFT JOIN FETCH f.facturaCliente fc  WHERE f.establecimiento =:establecimiento AND f.puntoEmision =:puntoVenta  AND f.fechaEmision between :fechaDesde AND :fechaHasta AND f.idOrganizacion = :idOrganizacion ");
/* 227:    */     
/* 228:    */ 
/* 229:280 */     query.setParameter("establecimiento", establecimiento);
/* 230:281 */     query.setParameter("puntoVenta", puntoVenta);
/* 231:282 */     query.setParameter("fechaDesde", fechaDesde);
/* 232:283 */     query.setParameter("fechaHasta", fechaHasta);
/* 233:284 */     query.setParameter("idOrganizacion", Integer.valueOf(organizacion.getId()));
/* 234:    */     
/* 235:286 */     return query.getResultList();
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void actualizarDatosExportacion(FacturaCliente facturaCliente)
/* 239:    */   {
/* 240:295 */     StringBuffer sql = new StringBuffer();
/* 241:296 */     sql.append("UPDATE FacturaClienteSRI f SET f.refrendo = :refrendo, f.distritoRefrendo = :distrito, ");
/* 242:297 */     sql.append(" f.anioRefrendo = :anio, f.regimenRefrendo = :regimen, f.correlativoRefrendo = :correlativo, ");
/* 243:298 */     sql.append(" f.fechaTransaccion = :fecha, f.documentoTransporteRefrendo = :documento");
/* 244:299 */     if ((facturaCliente.getFacturaClienteSRI().getTipoIngresoExterior() != null) && 
/* 245:300 */       (!facturaCliente.getFacturaClienteSRI().getTipoIngresoExterior().isEmpty()))
/* 246:    */     {
/* 247:301 */       sql.append(" , f.tipoIngresoExterior = :tipoIngresoExterior, f.ingresoExteriorGraboImpuestos = :ingresoExteriorGraboImpuestos,");
/* 248:302 */       sql.append(" f.valorImpuestoExportacion = :valorImpuestoExportacion");
/* 249:    */     }
/* 250:304 */     sql.append(" WHERE f.facturaCliente=:facturaCliente");
/* 251:    */     
/* 252:306 */     Query query = this.em.createQuery(sql.toString());
/* 253:307 */     query.setParameter("refrendo", facturaCliente.getFacturaClienteSRI().getRefrendo());
/* 254:308 */     query.setParameter("distrito", facturaCliente.getFacturaClienteSRI().getDistritoRefrendo());
/* 255:309 */     query.setParameter("anio", facturaCliente.getFacturaClienteSRI().getAnioRefrendo());
/* 256:310 */     query.setParameter("regimen", facturaCliente.getFacturaClienteSRI().getRegimenRefrendo());
/* 257:311 */     query.setParameter("correlativo", facturaCliente.getFacturaClienteSRI().getCorrelativoRefrendo());
/* 258:312 */     query.setParameter("fecha", facturaCliente.getFacturaClienteSRI().getFechaTransaccion());
/* 259:313 */     query.setParameter("documento", facturaCliente.getFacturaClienteSRI().getDocumentoTransporteRefrendo());
/* 260:314 */     query.setParameter("facturaCliente", facturaCliente);
/* 261:315 */     if ((facturaCliente.getFacturaClienteSRI().getTipoIngresoExterior() != null) && 
/* 262:316 */       (!facturaCliente.getFacturaClienteSRI().getTipoIngresoExterior().isEmpty()))
/* 263:    */     {
/* 264:317 */       query.setParameter("tipoIngresoExterior", facturaCliente.getFacturaClienteSRI().getTipoIngresoExterior());
/* 265:318 */       query.setParameter("ingresoExteriorGraboImpuestos", Boolean.valueOf(facturaCliente.getFacturaClienteSRI().isIngresoExteriorGraboImpuestos()));
/* 266:319 */       query.setParameter("valorImpuestoExportacion", facturaCliente.getFacturaClienteSRI().getValorImpuestoExportacion());
/* 267:    */     }
/* 268:322 */     query.executeUpdate();
/* 269:    */   }
/* 270:    */   
/* 271:    */   public List<Object[]> obtenerFacturasMesResumen(int anio, int mes, int idOrganizacion)
/* 272:    */   {
/* 273:330 */     List<Object[]> lista = new ArrayList();
/* 274:    */     
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:337 */     StringBuilder sql = new StringBuilder();
/* 281:338 */     sql.append(" SELECT f.establecimiento, ");
/* 282:339 */     sql.append(" SUM(f.montoIva),SUM(f.baseImponibleTarifaCero),SUM(f.baseImponibleDiferenteCero),SUM(f.baseImponibleNoObjetoIva), ");
/* 283:340 */     sql.append(" SUM(f.baseImponibleTarifaCero * 0), SUM(f.baseImponibleTarifaCero * 0) ");
/* 284:341 */     sql.append(" FROM FacturaClienteSRI f ");
/* 285:342 */     sql.append(" LEFT JOIN f.tipoIdentificacion t ");
/* 286:343 */     sql.append(" LEFT JOIN f.tipoComprobanteSRI tc ");
/* 287:344 */     sql.append(" INNER JOIN f.facturaCliente fc ");
/* 288:345 */     sql.append(" INNER JOIN fc.documento do ");
/* 289:346 */     sql.append(" WHERE MONTH(f.fechaEmision)=:mes AND YEAR(f.fechaEmision)=:anio AND fc.estado!=:estadoAnulado ");
/* 290:347 */     sql.append(" AND (do.documentoBase=:factura OR do.documentoBase=:notaCredito OR do.documentoBase=:notaDebito) ");
/* 291:348 */     sql.append(" AND do.indicadorDocumentoExterior = false ");
/* 292:349 */     sql.append(" AND fc.idOrganizacion = :idOrganizacion ");
/* 293:350 */     sql.append(" AND f.indicadorSaldoInicial = false  ");
/* 294:351 */     sql.append(" AND fc IS NOT NULL");
/* 295:352 */     sql.append(" GROUP BY f.establecimiento ");
/* 296:    */     
/* 297:354 */     Query query = this.em.createQuery(sql.toString());
/* 298:355 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 299:356 */     query.setParameter("mes", Integer.valueOf(mes));
/* 300:357 */     query.setParameter("anio", Integer.valueOf(anio));
/* 301:358 */     query.setParameter("factura", DocumentoBase.FACTURA_CLIENTE);
/* 302:359 */     query.setParameter("notaDebito", DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 303:360 */     query.setParameter("notaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 304:361 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 305:    */     
/* 306:363 */     lista.addAll(query.getResultList());
/* 307:    */     
/* 308:    */ 
/* 309:366 */     sql = new StringBuilder();
/* 310:367 */     sql.append(" SELECT f.establecimiento, ");
/* 311:368 */     sql.append(" SUM(f.montoIva),SUM(f.baseImponibleTarifaCero),SUM(f.baseImponibleDiferenteCero),SUM(f.baseImponibleNoObjetoIva), ");
/* 312:369 */     sql.append(" SUM(f.baseImponibleTarifaCero * 0), SUM(f.valorRetenidoFuente)  ");
/* 313:370 */     sql.append(" FROM FacturaClienteSRI f ");
/* 314:371 */     sql.append(" LEFT JOIN f.facturaCliente fc ");
/* 315:372 */     sql.append(" LEFT JOIN f.tipoIdentificacion t ");
/* 316:373 */     sql.append(" LEFT JOIN f.tipoComprobanteSRI tc ");
/* 317:374 */     sql.append(" WHERE MONTH(f.fechaEmision)=:mes AND YEAR(f.fechaEmision)=:anio  ");
/* 318:375 */     sql.append(" AND fc = NULL ");
/* 319:376 */     sql.append(" AND f.idOrganizacion = :idOrganizacion ");
/* 320:377 */     sql.append(" AND f.valorRetenidoFuente > 0 ");
/* 321:378 */     sql.append(" AND f.indicadorSaldoInicial = false  ");
/* 322:379 */     sql.append(" AND f.estado != :estadoAnulado  ");
/* 323:380 */     sql.append(" AND fc IS NOT NULL");
/* 324:381 */     sql.append(" GROUP BY f.establecimiento ");
/* 325:    */     
/* 326:383 */     query = this.em.createQuery(sql.toString());
/* 327:384 */     query.setParameter("mes", Integer.valueOf(mes));
/* 328:385 */     query.setParameter("anio", Integer.valueOf(anio));
/* 329:386 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 330:387 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 331:    */     
/* 332:389 */     lista.addAll(query.getResultList());
/* 333:    */     
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:395 */     sql = new StringBuilder();
/* 339:396 */     sql.append(" SELECT f.establecimiento, ");
/* 340:397 */     sql.append(" SUM(f.montoIva),SUM(f.baseImponibleTarifaCero),SUM(f.baseImponibleDiferenteCero),SUM(f.baseImponibleNoObjetoIva), ");
/* 341:398 */     sql.append(" SUM(f.baseImponibleTarifaCero * 0), SUM(f.baseImponibleTarifaCero * 0) ");
/* 342:399 */     sql.append(" FROM FacturaClienteSRI f ");
/* 343:400 */     sql.append(" LEFT JOIN f.tipoIdentificacion t ");
/* 344:401 */     sql.append(" LEFT JOIN f.tipoComprobanteSRI tc ");
/* 345:402 */     sql.append(" WHERE MONTH(f.fechaEmision)=:mes AND YEAR(f.fechaEmision)=:anio AND f.facturaCliente.estado!=:estadoAnulado ");
/* 346:403 */     sql.append(" AND (f.facturaCliente.documento.documentoBase=:factura OR f.facturaCliente.documento.documentoBase=:notaCredito) ");
/* 347:404 */     sql.append(" AND f.facturaCliente.documento.indicadorDocumentoExterior = false ");
/* 348:405 */     sql.append(" AND f.facturaCliente.idOrganizacion = :idOrganizacion ");
/* 349:406 */     sql.append(" AND f.indicadorSaldoInicial = false  ");
/* 350:407 */     sql.append(" AND f.facturaCliente IS NOT NULL");
/* 351:408 */     sql.append(" GROUP BY f.establecimiento ");
/* 352:    */     
/* 353:410 */     query = this.em.createQuery(sql.toString());
/* 354:411 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 355:412 */     query.setParameter("mes", Integer.valueOf(mes));
/* 356:413 */     query.setParameter("anio", Integer.valueOf(anio));
/* 357:414 */     query.setParameter("factura", DocumentoBase.FACTURA_CLIENTE);
/* 358:415 */     query.setParameter("notaCredito", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 359:416 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 360:    */     
/* 361:418 */     lista.addAll(query.getResultList());
/* 362:    */     
/* 363:    */ 
/* 364:421 */     sql = new StringBuilder();
/* 365:422 */     sql.append(" SELECT f.establecimiento, ");
/* 366:423 */     sql.append(" SUM(f.montoIva),SUM(f.baseImponibleTarifaCero),SUM(f.baseImponibleDiferenteCero),SUM(f.baseImponibleNoObjetoIva), ");
/* 367:424 */     sql.append(" SUM(f.baseImponibleTarifaCero * 0), SUM(f.valorRetenidoFuente)  ");
/* 368:425 */     sql.append(" FROM FacturaClienteSRI f ");
/* 369:426 */     sql.append(" LEFT JOIN f.facturaCliente fc ");
/* 370:427 */     sql.append(" LEFT JOIN f.tipoIdentificacion t ");
/* 371:428 */     sql.append(" LEFT JOIN f.tipoComprobanteSRI tc ");
/* 372:429 */     sql.append(" WHERE MONTH(f.fechaEmision)=:mes AND YEAR(f.fechaEmision)=:anio  ");
/* 373:430 */     sql.append(" AND fc = NULL ");
/* 374:431 */     sql.append(" AND f.idOrganizacion = :idOrganizacion ");
/* 375:432 */     sql.append(" AND f.valorRetenidoFuente > 0 ");
/* 376:433 */     sql.append(" AND f.indicadorSaldoInicial = false  ");
/* 377:434 */     sql.append(" AND fc IS NOT NULL");
/* 378:435 */     sql.append(" GROUP BY f.establecimiento ");
/* 379:    */     
/* 380:437 */     query = this.em.createQuery(sql.toString());
/* 381:438 */     query.setParameter("mes", Integer.valueOf(mes));
/* 382:439 */     query.setParameter("anio", Integer.valueOf(anio));
/* 383:440 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 384:    */     
/* 385:442 */     lista.addAll(query.getResultList());
/* 386:    */     
/* 387:444 */     return lista;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public List<Object[]> getReporteICE(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 391:    */   {
/* 392:452 */     Map<String, Object[]> mapa = new HashMap();
/* 393:    */     
/* 394:454 */     StringBuilder jpql = new StringBuilder();
/* 395:455 */     jpql = new StringBuilder();
/* 396:456 */     jpql.append("SELECT ti.codigo, em.identificacion, pro.nombre, pro.codigo, pro.codigoIce, clas.codigo, mar.codigoIce, pro.icePresentacion,");
/* 397:457 */     jpql.append(" cap.codigo, unid.codigo, pro.ibpGradoAlcohol,  SUM(dfc.cantidad * (coalesce(pp.cantidadUnidades,1))), pro.iceGramosAzucar, do.indicadorDocumentoExterior, 0, em.nombreFiscal ");
/* 398:458 */     jpql.append(" FROM  DetalleFacturaCliente dfc ");
/* 399:459 */     jpql.append(" INNER JOIN dfc.facturaCliente fc ");
/* 400:460 */     jpql.append(" INNER JOIN fc.empresa em ");
/* 401:461 */     jpql.append(" INNER JOIN em.tipoIdentificacion ti ");
/* 402:462 */     jpql.append(" INNER JOIN fc.documento do ");
/* 403:463 */     jpql.append(" INNER JOIN dfc.producto pro ");
/* 404:464 */     jpql.append(" LEFT JOIN pro.presentacionProducto pp ");
/* 405:465 */     jpql.append(" LEFT JOIN pro.ibpMarca mar ");
/* 406:466 */     jpql.append(" LEFT JOIN pro.ibpCapacidad cap ");
/* 407:467 */     jpql.append(" LEFT JOIN pro.ibpUnidad unid ");
/* 408:468 */     jpql.append(" LEFT JOIN mar.ibpClasificacion clas ");
/* 409:469 */     jpql.append(" WHERE (fc.fecha >= :fechaDesde AND fc.fecha <= :fechaHasta) ");
/* 410:470 */     jpql.append(" AND pro.ice > '0' ");
/* 411:471 */     jpql.append(" AND do.documentoBase = :documentoBaseFactura ");
/* 412:472 */     jpql.append(" AND (fc.estado = :estadoProcesado OR fc.estado = :estadoContabilizado)");
/* 413:473 */     jpql.append(" AND fc.idOrganizacion = :idOrganizacion ");
/* 414:474 */     jpql.append(" GROUP BY em.identificacion, pro.nombre, pro.codigo, pro.codigoIce, clas.codigo, mar.codigoIce, pro.icePresentacion,");
/* 415:475 */     jpql.append(" cap.codigo, unid.codigo, pro.ibpGradoAlcohol, ti.codigo, pro.iceGramosAzucar, do.indicadorDocumentoExterior, em.nombreFiscal ");
/* 416:    */     
/* 417:477 */     Query query = this.em.createQuery(jpql.toString());
/* 418:478 */     query.setParameter("fechaDesde", fechaDesde);
/* 419:479 */     query.setParameter("fechaHasta", fechaHasta);
/* 420:480 */     query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 421:481 */     query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/* 422:482 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 423:483 */     query.setParameter("documentoBaseFactura", DocumentoBase.FACTURA_CLIENTE);
/* 424:    */     
/* 425:485 */     List<Object[]> result = query.getResultList();
/* 426:486 */     for (Object[] objects : result)
/* 427:    */     {
/* 428:487 */       objects[11] = Integer.valueOf(((BigDecimal)objects[11]).intValue());
/* 429:488 */       mapa.put((String)objects[1] + "-" + (String)objects[3], objects);
/* 430:    */     }
/* 431:491 */     StringBuilder jpql2 = new StringBuilder();
/* 432:492 */     jpql2 = new StringBuilder();
/* 433:493 */     jpql2.append("SELECT ti.codigo, em.identificacion, pro.nombre, pro.codigo, pro.codigoIce, clas.codigo, mar.codigoIce, pro.icePresentacion,");
/* 434:494 */     jpql2.append(" cap.codigo, unid.codigo, pro.ibpGradoAlcohol,  SUM(dfc.cantidad * (coalesce(pp.cantidadUnidades,1))), pro.iceGramosAzucar, do.indicadorDocumentoExterior, 0 , em.nombreFiscal");
/* 435:495 */     jpql2.append(" FROM  DetalleFacturaCliente dfc ");
/* 436:496 */     jpql2.append(" INNER JOIN dfc.facturaCliente fc ");
/* 437:497 */     jpql2.append(" INNER JOIN fc.empresa em ");
/* 438:498 */     jpql2.append(" INNER JOIN em.tipoIdentificacion ti ");
/* 439:499 */     jpql2.append(" INNER JOIN fc.documento do ");
/* 440:500 */     jpql2.append(" INNER JOIN dfc.producto pro ");
/* 441:501 */     jpql2.append(" LEFT JOIN pro.presentacionProducto pp ");
/* 442:502 */     jpql2.append(" LEFT JOIN pro.ibpMarca mar ");
/* 443:503 */     jpql2.append(" LEFT JOIN pro.ibpCapacidad cap ");
/* 444:504 */     jpql2.append(" LEFT JOIN pro.ibpUnidad unid ");
/* 445:505 */     jpql2.append(" LEFT JOIN mar.ibpClasificacion clas ");
/* 446:506 */     jpql2.append(" WHERE (fc.fecha >= :fechaDesde AND fc.fecha <= :fechaHasta) ");
/* 447:507 */     jpql2.append(" AND pro.ice > '0' ");
/* 448:508 */     jpql2.append(" AND do.documentoBase = :documentoBaseFactura ");
/* 449:509 */     jpql2.append(" AND (fc.estado = :estadoProcesado OR fc.estado = :estadoContabilizado)");
/* 450:510 */     jpql2.append(" AND fc.idOrganizacion = :idOrganizacion ");
/* 451:511 */     jpql2.append(" GROUP BY em.identificacion, pro.nombre, pro.codigo, pro.codigoIce, clas.codigo, mar.codigoIce, pro.icePresentacion,");
/* 452:512 */     jpql2.append(" cap.codigo, unid.codigo, pro.ibpGradoAlcohol, ti.codigo, pro.iceGramosAzucar, do.indicadorDocumentoExterior, em.nombreFiscal");
/* 453:    */     
/* 454:514 */     Query query2 = this.em.createQuery(jpql.toString());
/* 455:515 */     query2.setParameter("fechaDesde", fechaDesde);
/* 456:516 */     query2.setParameter("fechaHasta", fechaHasta);
/* 457:517 */     query2.setParameter("estadoProcesado", Estado.PROCESADO);
/* 458:518 */     query2.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/* 459:519 */     query2.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 460:520 */     query2.setParameter("documentoBaseFactura", DocumentoBase.DEVOLUCION_CLIENTE);
/* 461:    */     
/* 462:522 */     List<Object[]> result2 = query2.getResultList();
/* 463:523 */     for (Object[] objects : result2) {
/* 464:524 */       if (mapa.containsKey((String)objects[1] + "-" + (String)objects[3]))
/* 465:    */       {
/* 466:525 */         Object[] factura = (Object[])mapa.get((String)objects[1] + "-" + (String)objects[3]);
/* 467:526 */         factura[14] = Integer.valueOf(((BigDecimal)objects[11]).intValue());
/* 468:527 */         mapa.put((String)objects[1] + "-" + (String)objects[3], factura);
/* 469:    */       }
/* 470:    */       else
/* 471:    */       {
/* 472:529 */         objects[14] = Integer.valueOf(((BigDecimal)objects[11]).intValue());
/* 473:530 */         objects[11] = Integer.valueOf(0);
/* 474:531 */         mapa.put((String)objects[1] + "-" + (String)objects[3], objects);
/* 475:    */       }
/* 476:    */     }
/* 477:535 */     Object lista = new ArrayList();
/* 478:536 */     ((List)lista).addAll(mapa.values());
/* 479:537 */     return lista;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public Long getImportaciones(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 483:    */   {
/* 484:541 */     StringBuilder jpql = new StringBuilder();
/* 485:542 */     jpql = new StringBuilder();
/* 486:    */     
/* 487:544 */     jpql.append(" SELECT COUNT(fp.idFacturaProveedor)");
/* 488:545 */     jpql.append(" FROM DetalleFacturaProveedor dfp ");
/* 489:546 */     jpql.append(" INNER JOIN dfp.producto pr ");
/* 490:547 */     jpql.append(" INNER JOIN dfp.facturaProveedor fp ");
/* 491:548 */     jpql.append(" INNER JOIN fp.documento do ");
/* 492:549 */     jpql.append(" WHERE (fp.fecha >= :fechaDesde AND fp.fecha <= :fechaHasta) AND fp.estado != :estadoAnulado ");
/* 493:550 */     jpql.append(" AND pr.ice>0 AND do.documentoBase = :documentoFactura AND do.indicadorDocumentoExterior = true");
/* 494:551 */     jpql.append(" AND fp.idOrganizacion = :idOrganizacion ");
/* 495:    */     
/* 496:553 */     Query query = this.em.createQuery(jpql.toString());
/* 497:554 */     query.setParameter("fechaDesde", fechaDesde);
/* 498:555 */     query.setParameter("fechaHasta", fechaHasta);
/* 499:556 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 500:557 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 501:558 */     query.setParameter("documentoFactura", DocumentoBase.FACTURA_PROVEEDOR);
/* 502:    */     
/* 503:560 */     return (Long)query.getSingleResult();
/* 504:    */   }
/* 505:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.FacturaClienteSRIDao
 * JD-Core Version:    0.7.0.1
 */