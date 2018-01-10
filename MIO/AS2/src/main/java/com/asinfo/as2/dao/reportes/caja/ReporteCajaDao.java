/*   1:    */ package com.asinfo.as2.dao.reportes.caja;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Caja;
/*   5:    */ import com.asinfo.as2.entities.CierreCaja;
/*   6:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ReporteCajaDao
/*  17:    */   extends AbstractDaoAS2<Caja>
/*  18:    */ {
/*  19:    */   public ReporteCajaDao()
/*  20:    */   {
/*  21: 32 */     super(Caja.class);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public List<Object[]> getReporteCierreCajaPorDenominacionFormaCobro(int idCierreCaja)
/*  25:    */   {
/*  26: 37 */     StringBuilder sql1 = new StringBuilder();
/*  27: 38 */     sql1.append(" SELECT c.nombre, u.nombreUsuario ,cc.fechaHasta, cc.estado, ");
/*  28: 39 */     sql1.append(" cc.numero, dfc.nombre, ddfc.cantidad, ddfc.total");
/*  29: 40 */     sql1.append(" FROM DetalleDenominacionFormaCobro ddfc");
/*  30: 41 */     sql1.append(" INNER JOIN ddfc.cierreCaja cc");
/*  31: 42 */     sql1.append(" INNER JOIN ddfc.denominacionFormaCobro dfc");
/*  32: 43 */     sql1.append(" INNER JOIN cc.usuario u");
/*  33: 44 */     sql1.append(" LEFT JOIN cc.caja c");
/*  34: 45 */     sql1.append(" WHERE cc.idCierreCaja = :idCierreCaja");
/*  35: 46 */     sql1.append(" ORDER BY dfc.nombre");
/*  36:    */     
/*  37: 48 */     Query query = this.em.createQuery(sql1.toString());
/*  38: 49 */     query.setParameter("idCierreCaja", Integer.valueOf(idCierreCaja));
/*  39: 50 */     return query.getResultList();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List getReporteCierreCaja(int idCierreCaja)
/*  43:    */   {
/*  44: 55 */     List list = new ArrayList();
/*  45:    */     
/*  46: 57 */     StringBuilder sql1 = new StringBuilder();
/*  47: 58 */     sql1.append(" SELECT c.nombre, u.nombreUsuario ,cc.fechaHasta, cc.estado, co.numero, co.fecha, fp.nombre, dfc.descripcion,");
/*  48: 59 */     sql1.append(" dfc.valor, dfc.documentoReferencia,cc.numero, e.nombreComercial, e.nombreFiscal, b.nombre, cb.numero, dcc.valor");
/*  49: 60 */     sql1.append(" FROM DetalleCierreCaja dcc ");
/*  50: 61 */     sql1.append(" INNER JOIN dcc.detalleFormaCobro dfc ");
/*  51: 62 */     sql1.append(" INNER JOIN dfc.cuentaBancariaOrganizacion cbo ");
/*  52: 63 */     sql1.append(" INNER JOIN cbo.cuentaBancaria cb ");
/*  53: 64 */     sql1.append(" INNER JOIN cb.banco b ");
/*  54: 65 */     sql1.append(" INNER JOIN dfc.cobro co ");
/*  55: 66 */     sql1.append(" INNER JOIN co.empresa e ");
/*  56: 67 */     sql1.append(" INNER JOIN dfc.formaPago fp ");
/*  57: 68 */     sql1.append(" INNER JOIN dcc.cierreCaja cc ");
/*  58: 69 */     sql1.append(" LEFT JOIN cc.caja c ");
/*  59: 70 */     sql1.append(" INNER JOIN cc.usuario u ");
/*  60: 71 */     sql1.append(" WHERE cc.idCierreCaja = :idCierreCaja ");
/*  61: 72 */     sql1.append(" ORDER BY fp.nombre, co.numero, dfc.descripcion ");
/*  62:    */     
/*  63: 74 */     Query query = this.em.createQuery(sql1.toString());
/*  64: 75 */     query.setParameter("idCierreCaja", Integer.valueOf(idCierreCaja));
/*  65: 76 */     list.addAll(query.getResultList());
/*  66:    */     
/*  67: 78 */     StringBuilder sql2 = new StringBuilder();
/*  68: 79 */     sql2.append(" SELECT c.nombre, u.nombreUsuario ,cc.fechaHasta, cc.estado, ac.numero, ac.fecha, fp.nombre, ac.descripcion,");
/*  69: 80 */     sql2.append(" ac.valor, ac.documentoReferencia,cc.numero, e.nombreComercial, e.nombreFiscal, b.nombre, cb.numero, dcc.valor");
/*  70: 81 */     sql2.append(" FROM DetalleCierreCaja dcc ");
/*  71: 82 */     sql2.append(" INNER JOIN dcc.anticipoCliente ac ");
/*  72: 83 */     sql2.append(" INNER JOIN ac.cuentaBancariaOrganizacion cbo ");
/*  73: 84 */     sql2.append(" INNER JOIN cbo.cuentaBancaria cb ");
/*  74: 85 */     sql2.append(" INNER JOIN cb.banco b ");
/*  75: 86 */     sql2.append(" INNER JOIN ac.empresa e ");
/*  76: 87 */     sql2.append(" INNER JOIN ac.formaPago fp ");
/*  77: 88 */     sql2.append(" INNER JOIN dcc.cierreCaja cc ");
/*  78: 89 */     sql2.append(" LEFT JOIN cc.caja c ");
/*  79: 90 */     sql2.append(" INNER JOIN cc.usuario u ");
/*  80: 91 */     sql2.append(" WHERE cc.idCierreCaja = :idCierreCaja ");
/*  81: 92 */     sql2.append(" AND  ac.notaCreditoCliente IS NULL ");
/*  82: 93 */     sql2.append(" ORDER BY fp.nombre, ac.descripcion ");
/*  83:    */     
/*  84: 95 */     query = this.em.createQuery(sql2.toString());
/*  85: 96 */     query.setParameter("idCierreCaja", Integer.valueOf(idCierreCaja));
/*  86: 97 */     list.addAll(query.getResultList());
/*  87:    */     
/*  88: 99 */     return list;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List<Object[]> getReporteCierreCajaPorFactura(CierreCaja cierreCaja)
/*  92:    */   {
/*  93:106 */     List<Object[]> lista = new ArrayList();
/*  94:107 */     StringBuilder sql1 = new StringBuilder();
/*  95:    */     
/*  96:109 */     sql1.append(" SELECT c.nombre, u.nombreUsuario ,cc.fechaHasta, cc.estado, ");
/*  97:110 */     sql1.append(" co.numero, co.fecha,CONCAT( '2',CASE WHEN co.indicadorTienePosfechados=true THEN 'POSFECHADO' ELSE fp.nombre END), ");
/*  98:111 */     sql1.append(" dfc.descripcion, -dc.valor, dfc.documentoReferencia, cc.numero, e.nombreComercial, e.nombreFiscal, b.nombre, ");
/*  99:112 */     sql1.append(" fc.numero, CASE WHEN fc.fecha = cc.fechaHasta THEN 'DIA ACTUAL' ELSE 'DIAS ANTERIORES' END, ");
/* 100:113 */     sql1.append(" fc.idFacturaCliente, '1'");
/* 101:114 */     sql1.append(" FROM DetalleCierreCaja dcc ");
/* 102:115 */     sql1.append(" INNER JOIN dcc.detalleFormaCobro dfc ");
/* 103:116 */     sql1.append(" LEFT JOIN dfc.banco b ");
/* 104:117 */     sql1.append(" INNER JOIN dfc.cobro co");
/* 105:118 */     sql1.append(" INNER JOIN dfc.listaDetalleCobroFormaCobro dcfc ");
/* 106:119 */     sql1.append(" INNER JOIN dcfc.detalleCobro dc ");
/* 107:120 */     sql1.append(" INNER JOIN dc.cuentaPorCobrar cpc ");
/* 108:121 */     sql1.append(" INNER JOIN cpc.facturaCliente fc ");
/* 109:122 */     sql1.append(" INNER JOIN co.empresa e ");
/* 110:123 */     sql1.append(" INNER JOIN dfc.formaPago fp ");
/* 111:124 */     sql1.append(" INNER JOIN dcc.cierreCaja cc ");
/* 112:125 */     sql1.append(" LEFT JOIN cc.caja c ");
/* 113:126 */     sql1.append(" INNER JOIN cc.usuario u ");
/* 114:127 */     sql1.append(" WHERE cc.idCierreCaja = :idCierreCaja ");
/* 115:128 */     sql1.append(" AND co.estado != :estado  ");
/* 116:    */     
/* 117:    */ 
/* 118:131 */     Query query = this.em.createQuery(sql1.toString());
/* 119:132 */     query.setParameter("idCierreCaja", Integer.valueOf(cierreCaja.getIdCierreCaja()));
/* 120:133 */     query.setParameter("estado", Estado.ANULADO);
/* 121:134 */     lista = query.getResultList();
/* 122:    */     
/* 123:136 */     return lista;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<Object[]> getListaFactura(CierreCaja cierreCaja)
/* 127:    */   {
/* 128:143 */     List<DocumentoBase> listaDocumentoBase = new ArrayList();
/* 129:144 */     listaDocumentoBase.add(DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 130:145 */     listaDocumentoBase.add(DocumentoBase.FACTURA_CLIENTE);
/* 131:    */     
/* 132:147 */     List<Object[]> lista = new ArrayList();
/* 133:    */     
/* 134:149 */     StringBuilder sql1 = new StringBuilder();
/* 135:150 */     sql1.append(" SELECT '', fc.usuarioCreacion, fc.fecha, fc.estado, ");
/* 136:151 */     sql1.append(" '', fc.fecha, '1VENTAS', '',CASE WHEN fc.estado=:estadoAnulado THEN (fc.total * 0) ELSE (fc.total-fc.descuento+fc.impuesto) END, '', '', e.nombreComercial, e.nombreFiscal, '', ");
/* 137:152 */     sql1.append(" fc.numero,'DIA ACTUAL',fc.idFacturaCliente, CASE WHEN fc.estado = :estadoAnulado THEN '0' ELSE  '1' END ");
/* 138:153 */     sql1.append(" FROM FacturaCliente fc ");
/* 139:154 */     sql1.append(" INNER JOIN fc.documento d ");
/* 140:155 */     sql1.append(" INNER JOIN fc.empresa e ");
/* 141:156 */     sql1.append(" WHERE fc.fecha = :fecha ");
/* 142:157 */     sql1.append(" AND d.documentoBase in (:listaDocumentoBase)");
/* 143:158 */     sql1.append(" AND fc.usuarioCreacion = :usuario ");
/* 144:    */     
/* 145:160 */     Query query = this.em.createQuery(sql1.toString());
/* 146:161 */     query.setParameter("fecha", cierreCaja.getFechaHasta());
/* 147:162 */     query.setParameter("listaDocumentoBase", listaDocumentoBase);
/* 148:163 */     query.setParameter("usuario", cierreCaja.getUsuarioCreacion());
/* 149:164 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 150:    */     
/* 151:166 */     lista.addAll(query.getResultList());
/* 152:    */     
/* 153:168 */     return lista;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public List<Object[]> getListaAnticipoCliente(CierreCaja cierreCaja)
/* 157:    */   {
/* 158:174 */     StringBuilder sql2 = new StringBuilder();
/* 159:175 */     sql2.append(" SELECT c.nombre, u.nombreUsuario, cc.fechaHasta, cc.estado,  ");
/* 160:176 */     sql2.append(" ac.numero, ac.fecha,CONCAT('2',fp.nombre),  ");
/* 161:177 */     sql2.append(" ac.descripcion, ac.valor, ac.documentoReferencia, cc.numero, e.nombreComercial, e.nombreFiscal, b.nombre,  ");
/* 162:178 */     sql2.append(" ac.numero, CASE WHEN ac.fecha = cc.fechaHasta THEN 'DIA ACTUAL' ELSE 'DIAS ANTERIORES' END , 0, '1'   ");
/* 163:179 */     sql2.append(" FROM DetalleCierreCaja dcc ");
/* 164:180 */     sql2.append(" INNER JOIN dcc.cierreCaja cc ");
/* 165:181 */     sql2.append(" LEFT JOIN cc.caja c ");
/* 166:182 */     sql2.append(" INNER JOIN cc.usuario u ");
/* 167:183 */     sql2.append(" INNER JOIN dcc.anticipoCliente ac ");
/* 168:184 */     sql2.append(" INNER JOIN ac.empresa e ");
/* 169:185 */     sql2.append(" LEFT  JOIN ac.formaPago fp ");
/* 170:186 */     sql2.append(" LEFT  JOIN ac.cuentaBancariaOrganizacion cbo ");
/* 171:187 */     sql2.append(" LEFT  JOIN cbo.cuentaBancaria cb ");
/* 172:188 */     sql2.append(" LEFT  JOIN cb.banco b ");
/* 173:189 */     sql2.append(" WHERE cc = :cierreCaja ");
/* 174:    */     
/* 175:191 */     Query query = this.em.createQuery(sql2.toString());
/* 176:192 */     query.setParameter("cierreCaja", cierreCaja);
/* 177:193 */     return query.getResultList();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public BigDecimal totalChquesPosfechados(CierreCaja cierreCaja)
/* 181:    */   {
/* 182:198 */     StringBuilder sql1 = new StringBuilder();
/* 183:    */     
/* 184:200 */     sql1.append(" SELECT COALESCE(SUM(dfc.valor),0) ");
/* 185:201 */     sql1.append(" FROM  DetalleCierreCaja dcc ");
/* 186:202 */     sql1.append(" INNER JOIN dcc.cierreCaja cc ");
/* 187:203 */     sql1.append(" INNER JOIN dcc.detalleFormaCobro dfc ");
/* 188:204 */     sql1.append(" INNER JOIN dfc.cobro co ");
/* 189:205 */     sql1.append(" WHERE cc= :cierreCaja ");
/* 190:206 */     sql1.append(" AND co.estado != :estado ");
/* 191:207 */     sql1.append(" AND dfc.indicadorChequePosfechado = true ");
/* 192:    */     
/* 193:209 */     Query query = this.em.createQuery(sql1.toString());
/* 194:210 */     query.setParameter("cierreCaja", cierreCaja);
/* 195:211 */     query.setParameter("estado", Estado.ANULADO);
/* 196:    */     
/* 197:213 */     return (BigDecimal)query.getSingleResult();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Integer numeroChequesPosfechados(CierreCaja cierreCaja)
/* 201:    */   {
/* 202:219 */     StringBuilder sql1 = new StringBuilder();
/* 203:    */     
/* 204:221 */     sql1.append(" SELECT COUNT(dfc.indicadorChequePosfechado) ");
/* 205:222 */     sql1.append(" FROM  DetalleCierreCaja dcc ");
/* 206:223 */     sql1.append(" INNER JOIN dcc.cierreCaja cc ");
/* 207:224 */     sql1.append(" INNER JOIN dcc.detalleFormaCobro dfc ");
/* 208:225 */     sql1.append(" INNER JOIN dfc.cobro co ");
/* 209:226 */     sql1.append(" WHERE cc= :cierreCaja ");
/* 210:227 */     sql1.append(" AND co.estado != :estado ");
/* 211:228 */     sql1.append(" AND dfc.indicadorChequePosfechado = true ");
/* 212:    */     
/* 213:230 */     Query query = this.em.createQuery(sql1.toString());
/* 214:231 */     query.setParameter("cierreCaja", cierreCaja);
/* 215:232 */     query.setParameter("estado", Estado.ANULADO);
/* 216:    */     
/* 217:234 */     return Integer.valueOf(((Long)query.getSingleResult()).intValue());
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List<Object[]> getReporteCierreCajaComparativo(int idCierreCaja)
/* 221:    */   {
/* 222:241 */     StringBuilder sql = new StringBuilder();
/* 223:242 */     sql.append("SELECT c.nombre, u.nombreUsuario, cc.fechaHasta, cc.estado, cc.numero, fp.nombre, SUM(ddfc.total), u.nombreUsuario, SUM(ddfc.total*0)");
/* 224:243 */     sql.append(" FROM DetalleDenominacionFormaCobro ddfc");
/* 225:244 */     sql.append(" INNER JOIN ddfc.cierreCaja cc");
/* 226:245 */     sql.append(" LEFT JOIN cc.caja c");
/* 227:246 */     sql.append(" INNER JOIN cc.usuario u");
/* 228:247 */     sql.append(" INNER JOIN ddfc.denominacionFormaCobro dfc");
/* 229:248 */     sql.append(" INNER JOIN dfc.formaPago fp");
/* 230:249 */     sql.append(" WHERE cc.idCierreCaja = :idCierreCaja");
/* 231:250 */     sql.append(" GROUP BY c.nombre, u.nombreUsuario, cc.fechaHasta, cc.estado, cc.numero, fp.nombre, u.nombreUsuario");
/* 232:    */     
/* 233:252 */     Query query = this.em.createQuery(sql.toString());
/* 234:253 */     query.setParameter("idCierreCaja", Integer.valueOf(idCierreCaja));
/* 235:254 */     return query.getResultList();
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.caja.ReporteCajaDao
 * JD-Core Version:    0.7.0.1
 */