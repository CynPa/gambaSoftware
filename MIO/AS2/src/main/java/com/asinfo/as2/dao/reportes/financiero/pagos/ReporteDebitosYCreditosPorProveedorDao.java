/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.pagos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empresa;
/*   4:    */ import com.asinfo.as2.entities.Sucursal;
/*   5:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.PersistenceContext;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TemporalType;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ReporteDebitosYCreditosPorProveedorDao
/*  18:    */ {
/*  19:    */   @PersistenceContext(name="AS2PU")
/*  20:    */   protected EntityManager em;
/*  21:    */   
/*  22:    */   public List<Object[]> getSaldos(int idOrganizacion, Empresa empresa, Date fechaDesde, int tipoReporte)
/*  23:    */   {
/*  24: 37 */     String numeroFactura = tipoReporte == 1 ? "" : ", v.numeroFactura, v.fechaFactura";
/*  25: 38 */     StringBuilder sql = new StringBuilder();
/*  26: 39 */     sql.append(" SELECT v.idEmpresa, v.nombreFiscal, SUM(v.debito - v.credito)");
/*  27: 40 */     sql.append(numeroFactura);
/*  28: 41 */     sql.append(" FROM VEstadoCuentaProveedor v ");
/*  29: 42 */     sql.append(" WHERE v.fechaDocumento < :fechaDesde ");
/*  30: 43 */     if (empresa != null) {
/*  31: 44 */       sql.append(" AND (v.idEmpresa = :idProveedor) ");
/*  32:    */     }
/*  33: 46 */     sql.append(" AND v.idOrganizacion = :idOrganizacion ");
/*  34: 47 */     sql.append(" GROUP BY v.idEmpresa, v.nombreFiscal" + numeroFactura);
/*  35: 48 */     sql.append(" HAVING SUM(v.debito - v.credito) != 0");
/*  36: 49 */     sql.append(" ORDER BY v.nombreFiscal");
/*  37:    */     
/*  38: 51 */     Query query = this.em.createQuery(sql.toString());
/*  39: 52 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  40: 53 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  41: 54 */     if (empresa != null) {
/*  42: 55 */       query.setParameter("idProveedor", Integer.valueOf(empresa.getId()));
/*  43:    */     }
/*  44: 58 */     return query.getResultList();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<Object[]> getPagos(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, Estado estado, int tipoReporte)
/*  48:    */   {
/*  49: 64 */     String numeroFactura = tipoReporte == 1 ? "" : ", fp.idFacturaProveedor, coalesce(concat(fps.establecimiento,'-',fps.puntoEmision,'-',fps.numero),fp.referencia3), fp.fecha";
/*  50: 65 */     StringBuilder sql = new StringBuilder();
/*  51: 66 */     sql.append(" SELECT em.idEmpresa, em.nombreFiscal, SUM(dp.valor)");
/*  52: 67 */     sql.append(numeroFactura);
/*  53: 68 */     sql.append(" FROM DetallePago dp ");
/*  54: 69 */     sql.append(" INNER JOIN dp.pago pa ");
/*  55: 70 */     sql.append(" INNER JOIN dp.cuentaPorPagar cxp ");
/*  56: 71 */     sql.append(" INNER JOIN cxp.facturaProveedor fp ");
/*  57: 72 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fps ");
/*  58: 73 */     sql.append(" INNER JOIN pa.empresa em ");
/*  59: 74 */     sql.append(" WHERE pa.idOrganizacion = :idOrganizacion ");
/*  60: 75 */     if (empresa != null) {
/*  61: 76 */       sql.append(" AND em = :empresa ");
/*  62:    */     }
/*  63: 78 */     if (sucursal != null) {
/*  64: 79 */       sql.append(" AND fp.sucursal = :sucursal ");
/*  65:    */     }
/*  66: 81 */     if (estado == Estado.CONTABILIZADO) {
/*  67: 82 */       sql.append(" AND pa.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  68:    */     } else {
/*  69: 84 */       sql.append(" AND pa.fecha <= :fechaHasta ");
/*  70:    */     }
/*  71: 86 */     sql.append(" AND pa.estado=:estado");
/*  72: 87 */     sql.append(" GROUP BY em.idEmpresa, em.nombreFiscal" + numeroFactura);
/*  73: 88 */     sql.append(" ORDER BY em.nombreFiscal");
/*  74:    */     
/*  75: 90 */     Query query = this.em.createQuery(sql.toString());
/*  76: 91 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  77: 92 */     if (estado == Estado.CONTABILIZADO) {
/*  78: 93 */       query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  79:    */     }
/*  80: 95 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  81: 96 */     query.setParameter("estado", estado);
/*  82: 97 */     if (empresa != null) {
/*  83: 98 */       query.setParameter("empresa", empresa);
/*  84:    */     }
/*  85:100 */     if (sucursal != null) {
/*  86:101 */       query.setParameter("sucursal", sucursal);
/*  87:    */     }
/*  88:104 */     return query.getResultList();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List<Object[]> getLiquidaciones(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, int tipoReporte)
/*  92:    */   {
/*  93:109 */     String numeroFactura = tipoReporte == 1 ? "" : ", fp.idFacturaProveedor, coalesce(concat(fps.establecimiento,'-',fps.puntoEmision,'-',fps.numero),fp.referencia3), fp.fecha";
/*  94:110 */     StringBuilder sql = new StringBuilder();
/*  95:111 */     sql.append(" SELECT em.idEmpresa, em.nombreFiscal, SUM(dlap.valor)");
/*  96:112 */     sql.append(numeroFactura);
/*  97:113 */     sql.append(" FROM DetalleLiquidacionAnticipoProveedor dlap ");
/*  98:114 */     sql.append(" JOIN dlap.cuentaPorPagar cxp ");
/*  99:115 */     sql.append(" JOIN cxp.facturaProveedor fp ");
/* 100:116 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fps ");
/* 101:117 */     sql.append(" JOIN dlap.liquidacionAnticipoProveedor lap ");
/* 102:118 */     sql.append(" JOIN lap.anticipoProveedor ap ");
/* 103:119 */     sql.append(" JOIN ap.empresa em ");
/* 104:120 */     sql.append(" WHERE lap.idOrganizacion = :idOrganizacion ");
/* 105:121 */     if (empresa != null) {
/* 106:122 */       sql.append(" AND em = :empresa ");
/* 107:    */     }
/* 108:124 */     if (sucursal != null) {
/* 109:125 */       sql.append(" AND fp.sucursal = :sucursal ");
/* 110:    */     }
/* 111:127 */     sql.append(" AND lap.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 112:128 */     sql.append(" AND lap.estado >= :estado");
/* 113:129 */     sql.append(" GROUP BY em.idEmpresa, em.nombreFiscal" + numeroFactura + " ");
/* 114:130 */     sql.append(" ORDER BY em.nombreFiscal");
/* 115:    */     
/* 116:132 */     Query query = this.em.createQuery(sql.toString());
/* 117:133 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 118:134 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 119:135 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 120:136 */     query.setParameter("estado", Estado.CONTABILIZADO);
/* 121:137 */     if (empresa != null) {
/* 122:138 */       query.setParameter("empresa", empresa);
/* 123:    */     }
/* 124:140 */     if (sucursal != null) {
/* 125:141 */       query.setParameter("sucursal", sucursal);
/* 126:    */     }
/* 127:144 */     return query.getResultList();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<Object[]> getCompras(int idOrganizacion, Sucursal sucursal, Empresa empresa, DocumentoBase documentoBase, Date fechaDesde, Date fechaHasta, int tipoReporte)
/* 131:    */   {
/* 132:162 */     String numeroFactura = tipoReporte == 1 ? "" : ", fp.idFacturaProveedor, coalesce(concat(fps.establecimiento,'-',fps.puntoEmision,'-',fps.numero),fp.referencia3), fp.fecha";
/* 133:163 */     List<DocumentoBase> listaDocumentoBase = new ArrayList();
/* 134:164 */     listaDocumentoBase.add(documentoBase);
/* 135:165 */     if (documentoBase == DocumentoBase.NOTA_CREDITO_PROVEEDOR)
/* 136:    */     {
/* 137:166 */       listaDocumentoBase.add(DocumentoBase.DEVOLUCION_PROVEEDOR);
/* 138:167 */       numeroFactura = tipoReporte == 1 ? "" : ", fp.facturaProveedorPadre.idFacturaProveedor, fp.facturaProveedorPadre.numero, fp.facturaProveedorPadre.fecha";
/* 139:    */     }
/* 140:169 */     if (documentoBase == DocumentoBase.NOTA_DEBITO_PROVEEDOR) {
/* 141:170 */       numeroFactura = tipoReporte == 1 ? "" : ", fp.facturaProveedorPadre.idFacturaProveedor, fp.facturaProveedorPadre.numero, fp.facturaProveedorPadre.fecha";
/* 142:    */     }
/* 143:172 */     StringBuilder sql = new StringBuilder();
/* 144:173 */     sql.append(" SELECT em.idEmpresa, em.nombreFiscal, SUM(fp.total-fp.descuento), SUM(fp.impuesto) ");
/* 145:174 */     sql.append(numeroFactura);
/* 146:175 */     sql.append(" FROM FacturaProveedor fp ");
/* 147:176 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fps ");
/* 148:177 */     sql.append(" INNER JOIN fp.empresa em ");
/* 149:178 */     sql.append(" INNER JOIN fp.documento d ");
/* 150:179 */     sql.append(" WHERE fp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 151:180 */     sql.append(" AND fp.estado != :estadoAnulado ");
/* 152:181 */     sql.append(" AND d.documentoBase in (:listaDocumentoBase) ");
/* 153:182 */     if (empresa != null) {
/* 154:183 */       sql.append(" AND em = :empresa ");
/* 155:    */     }
/* 156:185 */     if (sucursal != null) {
/* 157:186 */       sql.append(" AND fp.sucursal = :sucursal ");
/* 158:    */     }
/* 159:188 */     sql.append(" AND fp.idOrganizacion = :idOrganizacion ");
/* 160:189 */     sql.append(" GROUP BY em.idEmpresa, em.nombreFiscal" + numeroFactura);
/* 161:190 */     sql.append(" ORDER BY em.nombreFiscal");
/* 162:    */     
/* 163:192 */     Query query = this.em.createQuery(sql.toString());
/* 164:193 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 165:194 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 166:195 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 167:196 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 168:197 */     query.setParameter("listaDocumentoBase", listaDocumentoBase);
/* 169:198 */     if (empresa != null) {
/* 170:199 */       query.setParameter("empresa", empresa);
/* 171:    */     }
/* 172:201 */     if (sucursal != null) {
/* 173:202 */       query.setParameter("sucursal", sucursal);
/* 174:    */     }
/* 175:205 */     return query.getResultList();
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<Object[]> getSaldoAnticipoProveedor(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, int tipoReporte)
/* 179:    */   {
/* 180:222 */     StringBuilder sql = new StringBuilder();
/* 181:223 */     sql.append(" SELECT e.idEmpresa, e.nombreFiscal, ");
/* 182:224 */     sql.append(" SUM ( ap.valor) , ");
/* 183:225 */     sql.append(" SUM ( CASE WHEN ap.fechaDevolucion IS NULL OR ap.fechaDevolucion > :fechaHasta THEN (ap.valor * 0) ELSE ap.valorDevolucion END ) ");
/* 184:226 */     sql.append(" FROM AnticipoProveedor ap ");
/* 185:227 */     sql.append(" INNER JOIN ap.empresa e ");
/* 186:228 */     sql.append(" WHERE ap.idOrganizacion = :idOrganizacion ");
/* 187:229 */     sql.append(" AND ap.fecha <= :fechaHasta ");
/* 188:230 */     sql.append(" AND ap.estado != :estadoAnulado ");
/* 189:231 */     if (empresa != null) {
/* 190:232 */       sql.append(" AND e = :empresa ");
/* 191:    */     }
/* 192:234 */     if (sucursal != null) {
/* 193:235 */       sql.append(" AND ap.sucursal.idSucursal = :idSucursal ");
/* 194:    */     }
/* 195:237 */     sql.append(" GROUP BY e.idEmpresa, e.nombreFiscal ");
/* 196:238 */     sql.append(" ORDER BY e.nombreFiscal");
/* 197:    */     
/* 198:240 */     Query query = this.em.createQuery(sql.toString());
/* 199:241 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 200:242 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 201:243 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 202:244 */     if (empresa != null) {
/* 203:245 */       query.setParameter("empresa", empresa);
/* 204:    */     }
/* 205:247 */     if (sucursal != null) {
/* 206:248 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 207:    */     }
/* 208:251 */     return query.getResultList();
/* 209:    */   }
/* 210:    */   
/* 211:    */   public List<Object[]> getSaldoLiquidaciones(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, int tipoReporte)
/* 212:    */   {
/* 213:268 */     StringBuilder sql = new StringBuilder();
/* 214:269 */     sql.append(" SELECT e.idEmpresa, e.nombreFiscal, ");
/* 215:270 */     sql.append(" SUM ( lap.valor) ");
/* 216:271 */     sql.append(" FROM LiquidacionAnticipoProveedor lap ");
/* 217:272 */     sql.append(" INNER JOIN lap.anticipoProveedor ap ");
/* 218:273 */     sql.append(" INNER JOIN ap.empresa e ");
/* 219:274 */     sql.append(" WHERE ap.idOrganizacion = :idOrganizacion ");
/* 220:275 */     sql.append(" AND lap.fecha <= :fechaHasta ");
/* 221:276 */     sql.append(" AND lap.estado != :estadoAnulado ");
/* 222:277 */     if (empresa != null) {
/* 223:278 */       sql.append(" AND (e = :empresa) ");
/* 224:    */     }
/* 225:280 */     if (sucursal != null) {
/* 226:281 */       sql.append(" AND (ap.sucursal.idSucursal = :idSucursal) ");
/* 227:    */     }
/* 228:283 */     sql.append(" GROUP BY e.idEmpresa, e.nombreFiscal ");
/* 229:284 */     sql.append(" ORDER BY e.nombreFiscal");
/* 230:    */     
/* 231:286 */     Query query = this.em.createQuery(sql.toString());
/* 232:287 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 233:288 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 234:289 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 235:290 */     if (empresa != null) {
/* 236:291 */       query.setParameter("empresa", empresa);
/* 237:    */     }
/* 238:293 */     if (sucursal != null) {
/* 239:294 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 240:    */     }
/* 241:297 */     return query.getResultList();
/* 242:    */   }
/* 243:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.pagos.ReporteDebitosYCreditosPorProveedorDao
 * JD-Core Version:    0.7.0.1
 */