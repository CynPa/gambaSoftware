/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.contabilidad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   5:    */ import com.asinfo.as2.entities.GarantiaCliente;
/*   6:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ReporteMovimientoBancarioDao
/*  19:    */   extends AbstractDaoAS2<GarantiaCliente>
/*  20:    */ {
/*  21:    */   public ReporteMovimientoBancarioDao()
/*  22:    */   {
/*  23: 22 */     super(GarantiaCliente.class);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<MovimientoBancario> getReporteMovimientoBancario(Date fechaDesde, Date fechaHasta, int idCuentaBancariaOrganizacion, int idFormaPago, int idConceptoContable, boolean indicadorEstadoAnulado, int idOrganizacion, boolean indicadorGiradosNoCobrados, Map<String, String> filter, String tipoReporte)
/*  27:    */   {
/*  28: 30 */     Map<String, String> hmParametros = new HashMap();
/*  29:    */     
/*  30: 32 */     String filterValue = null;
/*  31: 33 */     StringBuilder sql = new StringBuilder();
/*  32: 34 */     sql.append(" SELECT mb ");
/*  33: 35 */     sql.append(" FROM MovimientoBancario mb ");
/*  34: 36 */     sql.append(" INNER JOIN FETCH mb.cuentaBancariaOrganizacion cbo ");
/*  35: 37 */     sql.append(" INNER JOIN FETCH cbo.cuentaBancaria cb ");
/*  36: 38 */     sql.append(" INNER JOIN FETCH cb.banco b ");
/*  37: 39 */     sql.append(" LEFT OUTER JOIN FETCH mb.conceptoContable cc ");
/*  38: 40 */     sql.append(" INNER JOIN FETCH mb.formaPago fp ");
/*  39: 41 */     sql.append(" INNER JOIN FETCH mb.documento do ");
/*  40: 42 */     sql.append(" INNER JOIN FETCH mb.detalleAsiento da ");
/*  41: 43 */     sql.append(" INNER JOIN FETCH da.asiento a ");
/*  42: 44 */     sql.append(" INNER JOIN FETCH a.tipoAsiento ta ");
/*  43: 45 */     sql.append(" LEFT JOIN FETCH mb.conciliacionBancaria cban ");
/*  44: 46 */     sql.append(" WHERE mb.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  45: 47 */     sql.append(" AND (cbo.idCuentaBancariaOrganizacion=:idCuentaBancariaOrganizacion OR :idCuentaBancariaOrganizacion=0) ");
/*  46: 48 */     sql.append(" AND (fp.idFormaPago = :idFormaPago OR :idFormaPago=0) ");
/*  47: 49 */     sql.append(" AND (cc.idConceptoContable = :idConceptoContable OR :idConceptoContable=0) ");
/*  48: 50 */     sql.append(" AND a.idOrganizacion = :idOrganizacion ");
/*  49: 51 */     if (indicadorEstadoAnulado) {
/*  50: 52 */       sql.append("AND mb.estado = :estado ");
/*  51:    */     } else {
/*  52: 54 */       sql.append("AND mb.estado != :estado ");
/*  53:    */     }
/*  54: 56 */     if (indicadorGiradosNoCobrados) {
/*  55: 57 */       sql.append("AND  (cban.fecha > :fechaHasta  OR  cban IS NULL  ) ");
/*  56:    */     }
/*  57: 59 */     if ((tipoReporte != null) && (tipoReporte.equals("VALORES_COBRADOS"))) {
/*  58: 60 */       sql.append(" AND mb.valor >= 0");
/*  59:    */     }
/*  60: 62 */     if ((tipoReporte != null) && (tipoReporte.equals("VALORES_PAGADOS"))) {
/*  61: 63 */       sql.append(" AND mb.valor < 0");
/*  62:    */     }
/*  63:    */     int i;
/*  64:    */     String parametro;
/*  65: 65 */     if (filter != null)
/*  66:    */     {
/*  67: 67 */       i = 1;
/*  68: 68 */       parametro = "parametro";
/*  69: 69 */       for (String filterProperty : filter.keySet())
/*  70:    */       {
/*  71: 70 */         if (filterProperty.equals("cuentaBancariaOrganizacion.cuentaBancaria.numero"))
/*  72:    */         {
/*  73: 71 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/*  74: 72 */           sql.append(" AND cb.numero LIKE :" + parametro + i);
/*  75: 73 */           i++;
/*  76:    */         }
/*  77: 75 */         if (filterProperty.equals("cuentaBancariaOrganizacion.cuentaBancaria.banco.nombre"))
/*  78:    */         {
/*  79: 76 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/*  80: 77 */           sql.append(" AND b.nombre LIKE :" + parametro + i);
/*  81: 78 */           i++;
/*  82:    */         }
/*  83: 80 */         if (filterProperty.equals("detalleAsiento.descripcion"))
/*  84:    */         {
/*  85: 81 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/*  86: 82 */           sql.append(" AND da.descripcion LIKE :" + parametro + i);
/*  87: 83 */           i++;
/*  88:    */         }
/*  89: 85 */         if (filterProperty.equals("formaPago.nombre"))
/*  90:    */         {
/*  91: 86 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/*  92: 87 */           sql.append(" AND fp.nombre LIKE :" + parametro + i);
/*  93: 88 */           i++;
/*  94:    */         }
/*  95: 90 */         if (filterProperty.equals("documentoReferencia"))
/*  96:    */         {
/*  97: 91 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/*  98: 92 */           sql.append(" AND mb.documentoReferencia LIKE :" + parametro + i);
/*  99: 93 */           i++;
/* 100:    */         }
/* 101: 95 */         if (filterProperty.equals("beneficiario"))
/* 102:    */         {
/* 103: 96 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 104: 97 */           sql.append(" AND mb.beneficiario LIKE :" + parametro + i);
/* 105: 98 */           i++;
/* 106:    */         }
/* 107:100 */         if (filterProperty.equals("detalleAsiento.asiento.numero"))
/* 108:    */         {
/* 109:101 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 110:102 */           sql.append(" AND a.numero LIKE :" + parametro + i);
/* 111:103 */           i++;
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:108 */     sql.append(" ORDER BY b.nombre, mb.fecha ");
/* 116:109 */     Query query = this.em.createQuery(sql.toString());
/* 117:110 */     query.setParameter("fechaDesde", fechaDesde);
/* 118:111 */     query.setParameter("fechaHasta", fechaHasta);
/* 119:112 */     query.setParameter("idCuentaBancariaOrganizacion", Integer.valueOf(idCuentaBancariaOrganizacion));
/* 120:113 */     query.setParameter("idFormaPago", Integer.valueOf(idFormaPago));
/* 121:114 */     query.setParameter("idConceptoContable", Integer.valueOf(idConceptoContable));
/* 122:115 */     query.setParameter("estado", Estado.ANULADO);
/* 123:116 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 124:118 */     for (String parametro : hmParametros.keySet())
/* 125:    */     {
/* 126:119 */       filterValue = (String)hmParametros.get(parametro);
/* 127:120 */       query.setParameter(parametro, "%" + filterValue + "%");
/* 128:    */     }
/* 129:123 */     return query.getResultList();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List getReporteChequesGiradosNoCobrados(Date fechaDesde, Date fechaHasta, CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 133:    */   {
/* 134:134 */     StringBuilder sql = new StringBuilder();
/* 135:135 */     sql.append(" SELECT mb.idMovimientoBancario, b.nombre, cb.numero, e.nombreFiscal, mb.documentoReferencia, mb.fecha, ");
/* 136:136 */     sql.append(" fps.establecimiento+'-'+fps.puntoEmision+'-'+fps.numero,mb.valor, dp.valor ");
/* 137:137 */     sql.append(" FROM MovimientoBancario mb ");
/* 138:138 */     sql.append(" JOIN mb.formaPago fpg ");
/* 139:139 */     sql.append(" JOIN mb.cuentaBancariaOrganizacion cbo ");
/* 140:140 */     sql.append(" JOIN cbo.cuentaBancaria cb ");
/* 141:141 */     sql.append(" JOIN cb.banco b ");
/* 142:142 */     sql.append(" JOIN mb.detalleAsiento da ");
/* 143:143 */     sql.append(" JOIN da.asiento a, ");
/* 144:144 */     sql.append(" DetallePago dp ");
/* 145:145 */     sql.append(" JOIN dp.pago p ");
/* 146:146 */     sql.append(" JOIN dp.cuentaPorPagar cxp ");
/* 147:147 */     sql.append(" JOIN p.empresa e ");
/* 148:148 */     sql.append(" JOIN p.asiento aa ");
/* 149:149 */     sql.append(" JOIN cxp.facturaProveedor fp ");
/* 150:150 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fps ");
/* 151:151 */     sql.append(" WHERE a = aa AND fpg.indicadorCheque = 1 and mb.valor < 0 AND a.estado <> 0 ");
/* 152:152 */     sql.append(" AND mb.fecha BETWEEN :fechaDesde AND :fechaHasta and mb.conciliacionBancaria IS NULL");
/* 153:153 */     sql.append(" AND (cbo.idCuentaBancariaOrganizacion = :idCuentaBancariaOrganizacion OR :idCuentaBancariaOrganizacion = 0) ");
/* 154:    */     
/* 155:155 */     Query query = this.em.createQuery(sql.toString());
/* 156:156 */     query.setParameter("fechaDesde", fechaDesde);
/* 157:157 */     query.setParameter("fechaHasta", fechaHasta);
/* 158:158 */     query.setParameter("idCuentaBancariaOrganizacion", 
/* 159:159 */       Integer.valueOf(cuentaBancariaOrganizacion == null ? 0 : cuentaBancariaOrganizacion.getIdCuentaBancariaOrganizacion()));
/* 160:    */     
/* 161:161 */     List lista = query.getResultList();
/* 162:    */     
/* 163:163 */     sql = new StringBuilder();
/* 164:164 */     sql.append(" SELECT mb.idMovimientoBancario,b.nombre, cb.numero, e.nombreFiscal, mb.documentoReferencia, mb.fecha, ");
/* 165:165 */     sql.append(" fps.establecimiento+'-'+fps.puntoEmision+'-'+fps.numero,mb.valor, dp.valor");
/* 166:166 */     sql.append(" FROM MovimientoBancario mb ");
/* 167:167 */     sql.append(" JOIN mb.formaPago fpg ");
/* 168:168 */     sql.append(" JOIN mb.cuentaBancariaOrganizacion cbo ");
/* 169:169 */     sql.append(" JOIN cbo.cuentaBancaria cb ");
/* 170:170 */     sql.append(" JOIN cb.banco b ");
/* 171:171 */     sql.append(" JOIN mb.detalleAsiento da ");
/* 172:172 */     sql.append(" JOIN da.asiento a, ");
/* 173:173 */     sql.append(" DetalleLiquidacionAnticipoProveedor dp ");
/* 174:174 */     sql.append(" JOIN dp.liquidacionAnticipoProveedor p ");
/* 175:175 */     sql.append(" JOIN p.anticipoProveedor ap ");
/* 176:176 */     sql.append(" JOIN dp.cuentaPorPagar cxp ");
/* 177:177 */     sql.append(" JOIN ap.empresa e ");
/* 178:178 */     sql.append(" JOIN p.asiento aa ");
/* 179:179 */     sql.append(" JOIN cxp.facturaProveedor fp ");
/* 180:180 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fps ");
/* 181:181 */     sql.append(" WHERE a = aa AND fpg.indicadorCheque = 1 and mb.valor < 0 AND a.estado <> 0");
/* 182:182 */     sql.append(" AND mb.fecha BETWEEN :fechaDesde AND :fechaHasta and mb.conciliacionBancaria IS NULL");
/* 183:183 */     sql.append(" AND (cbo.idCuentaBancariaOrganizacion = :idCuentaBancariaOrganizacion OR :idCuentaBancariaOrganizacion = 0) ");
/* 184:    */     
/* 185:185 */     query = this.em.createQuery(sql.toString());
/* 186:186 */     query.setParameter("fechaDesde", fechaDesde);
/* 187:187 */     query.setParameter("fechaHasta", fechaHasta);
/* 188:188 */     query.setParameter("idCuentaBancariaOrganizacion", 
/* 189:189 */       Integer.valueOf(cuentaBancariaOrganizacion == null ? 0 : cuentaBancariaOrganizacion.getIdCuentaBancariaOrganizacion()));
/* 190:    */     
/* 191:191 */     lista.addAll(query.getResultList());
/* 192:    */     
/* 193:193 */     sql = new StringBuilder();
/* 194:194 */     sql.append(" SELECT mb.idMovimientoBancario,b.nombre, cb.numero, mb.beneficiario, mb.documentoReferencia, mb.fecha, ");
/* 195:195 */     sql.append(" '',mb.valor, 0 ");
/* 196:196 */     sql.append(" FROM MovimientoBancario mb ");
/* 197:197 */     sql.append(" JOIN mb.formaPago fpg ");
/* 198:198 */     sql.append(" JOIN mb.cuentaBancariaOrganizacion cbo ");
/* 199:199 */     sql.append(" JOIN cbo.cuentaBancaria cb ");
/* 200:200 */     sql.append(" JOIN cb.banco b ");
/* 201:201 */     sql.append(" JOIN mb.detalleAsiento da ");
/* 202:202 */     sql.append(" JOIN da.asiento a ");
/* 203:203 */     sql.append(" WHERE NOT EXISTS (SELECT p FROM Pago p WHERE p.asiento = a) ");
/* 204:204 */     sql.append(" AND NOT EXISTS (SELECT l FROM LiquidacionAnticipoProveedor l WHERE l.asiento = a) ");
/* 205:205 */     sql.append(" AND fpg.indicadorCheque = 1 and mb.valor < 0 AND a.estado <> 0");
/* 206:206 */     sql.append(" AND mb.fecha BETWEEN :fechaDesde AND :fechaHasta and mb.conciliacionBancaria IS NULL");
/* 207:207 */     sql.append(" AND NOT EXISTS (SELECT c FROM Cobro c WHERE c.asientoProtesto = a) ");
/* 208:208 */     sql.append(" AND (cbo.idCuentaBancariaOrganizacion = :idCuentaBancariaOrganizacion OR :idCuentaBancariaOrganizacion = 0) ");
/* 209:    */     
/* 210:210 */     query = this.em.createQuery(sql.toString());
/* 211:211 */     query.setParameter("fechaDesde", fechaDesde);
/* 212:212 */     query.setParameter("fechaHasta", fechaHasta);
/* 213:213 */     query.setParameter("idCuentaBancariaOrganizacion", 
/* 214:214 */       Integer.valueOf(cuentaBancariaOrganizacion == null ? 0 : cuentaBancariaOrganizacion.getIdCuentaBancariaOrganizacion()));
/* 215:215 */     lista.addAll(query.getResultList());
/* 216:    */     
/* 217:217 */     return lista;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public BigDecimal getSaldoFecha(CuentaBancariaOrganizacion cbo, Date fechaDesde)
/* 221:    */   {
/* 222:222 */     StringBuilder sql = new StringBuilder();
/* 223:223 */     sql.append(" SELECT COALESCE(SUM(da.debe) - SUM(da.haber), 0.00) ");
/* 224:224 */     sql.append(" FROM MovimientoBancario mb ");
/* 225:225 */     sql.append(" INNER JOIN mb.cuentaBancariaOrganizacion cbo ");
/* 226:226 */     sql.append(" INNER JOIN mb.detalleAsiento da ");
/* 227:227 */     sql.append(" WHERE mb.fecha < :fechaDesde ");
/* 228:228 */     sql.append(" AND mb.estado != :estadoAnulado ");
/* 229:229 */     sql.append(" AND cbo = :cbo) ");
/* 230:230 */     Query query = this.em.createQuery(sql.toString());
/* 231:231 */     query.setParameter("fechaDesde", fechaDesde);
/* 232:232 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 233:233 */     query.setParameter("cbo", cbo);
/* 234:    */     
/* 235:235 */     return (BigDecimal)query.getSingleResult();
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.contabilidad.ReporteMovimientoBancarioDao
 * JD-Core Version:    0.7.0.1
 */