/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   5:    */ import com.asinfo.as2.entities.DetalleCierreCaja;
/*   6:    */ import com.asinfo.as2.entities.Documento;
/*   7:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   8:    */ import com.asinfo.as2.entities.FormaPago;
/*   9:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  10:    */ import com.asinfo.as2.entities.Secuencia;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import java.io.PrintStream;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ import javax.persistence.EntityManager;
/*  22:    */ import javax.persistence.Query;
/*  23:    */ import javax.persistence.TypedQuery;
/*  24:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  25:    */ import javax.persistence.criteria.CriteriaQuery;
/*  26:    */ import javax.persistence.criteria.Expression;
/*  27:    */ import javax.persistence.criteria.Fetch;
/*  28:    */ import javax.persistence.criteria.JoinType;
/*  29:    */ import javax.persistence.criteria.Predicate;
/*  30:    */ import javax.persistence.criteria.Root;
/*  31:    */ 
/*  32:    */ @Stateless
/*  33:    */ public class InterfazContableProcesoDao
/*  34:    */   extends AbstractDaoAS2<InterfazContableProceso>
/*  35:    */ {
/*  36:    */   public InterfazContableProcesoDao()
/*  37:    */   {
/*  38: 29 */     super(InterfazContableProceso.class);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List<InterfazContableProceso> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  42:    */   {
/*  43: 35 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  44: 36 */     CriteriaQuery<InterfazContableProceso> criteriaQuery = criteriaBuilder.createQuery(InterfazContableProceso.class);
/*  45: 37 */     Root<InterfazContableProceso> from = criteriaQuery.from(InterfazContableProceso.class);
/*  46: 38 */     from.fetch("documento", JoinType.LEFT);
/*  47: 39 */     from.fetch("sucursal", JoinType.LEFT);
/*  48: 40 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/*  49: 41 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/*  50:    */     
/*  51: 43 */     Fetch<Object, Object> cuentaBancaria = from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  52: 44 */     cuentaBancaria.fetch("cuentaBancaria", JoinType.LEFT);
/*  53:    */     
/*  54: 46 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  55:    */     
/*  56: 48 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  57: 49 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  58:    */     
/*  59: 51 */     CriteriaQuery<InterfazContableProceso> select = criteriaQuery.select(from);
/*  60: 52 */     TypedQuery<InterfazContableProceso> typedQuery = this.em.createQuery(select);
/*  61: 53 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  62: 54 */     return typedQuery.getResultList();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public InterfazContableProceso cargarDetalle(int idInterfazContableProceso)
/*  66:    */   {
/*  67: 59 */     return cargarDetalle(idInterfazContableProceso, false);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public InterfazContableProceso cargarDetalle(int idInterfazContableProceso, boolean indicadorAnulacion)
/*  71:    */   {
/*  72: 64 */     InterfazContableProceso interfazContableProceso = (InterfazContableProceso)buscarPorId(Integer.valueOf(idInterfazContableProceso));
/*  73: 65 */     if (interfazContableProceso.getDocumento() != null)
/*  74:    */     {
/*  75: 66 */       interfazContableProceso.getDocumento().getId();
/*  76: 67 */       if (interfazContableProceso.getDocumento().getSecuencia() != null) {
/*  77: 68 */         interfazContableProceso.getDocumento().getSecuencia().getId();
/*  78:    */       }
/*  79: 70 */       interfazContableProceso.getDocumento().getTipoAsiento().getId();
/*  80:    */     }
/*  81: 73 */     if (interfazContableProceso.getCuentaBancariaOrganizacion() != null) {
/*  82: 74 */       interfazContableProceso.getCuentaBancariaOrganizacion().getId();
/*  83:    */     }
/*  84: 77 */     if (interfazContableProceso.getFormaPago() != null) {
/*  85: 78 */       interfazContableProceso.getFormaPago().getId();
/*  86:    */     }
/*  87: 81 */     if (interfazContableProceso.getAsiento() != null) {
/*  88: 82 */       interfazContableProceso.getAsiento().getId();
/*  89:    */     }
/*  90: 85 */     interfazContableProceso.getListaDespachoCliente().size();
/*  91: 86 */     interfazContableProceso.getListaFacturaCliente().size();
/*  92: 87 */     if (interfazContableProceso.getListaFacturaCliente().size() > 0) {
/*  93: 88 */       for (FacturaCliente facturaCliente : interfazContableProceso.getListaFacturaCliente()) {
/*  94: 89 */         facturaCliente.getIdFacturaCliente();
/*  95:    */       }
/*  96:    */     }
/*  97: 93 */     interfazContableProceso.getListaMovimientoInventario().size();
/*  98: 95 */     if (interfazContableProceso.getAsiento() != null)
/*  99:    */     {
/* 100: 96 */       interfazContableProceso.getAsiento().getId();
/* 101: 97 */       interfazContableProceso.getAsiento().getTipoAsiento().getId();
/* 102:    */     }
/* 103:100 */     if (indicadorAnulacion) {
/* 104:101 */       interfazContableProceso.getListaDetalleCierreCaja().size();
/* 105:    */     } else {
/* 106:103 */       interfazContableProceso.setListaDetalleCierreCaja(obtenerListaDetalleCierreCaja(interfazContableProceso, AppUtil.getSucursal()
/* 107:104 */         .getIdSucursal()));
/* 108:    */     }
/* 109:107 */     return interfazContableProceso;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<DetalleCierreCaja> obtenerListaDetalleCierreCaja(InterfazContableProceso interfazContableProceso, int idSucursal)
/* 113:    */   {
/* 114:113 */     List<DetalleCierreCaja> listaCierreCaja = new ArrayList();
/* 115:114 */     int idInterfazContableProceso = interfazContableProceso.getIdInterfazContableProceso();
/* 116:    */     
/* 117:116 */     StringBuilder sql1 = new StringBuilder();
/* 118:117 */     sql1.append(" SELECT New DetalleCierreCaja (d.idDetalleCierreCaja,d.idSucursal,d.idOrganizacion,d.valor,i.idInterfazContableProceso,");
/* 119:118 */     sql1.append(" i.numero,i.documentoBase,cc.idCierreCaja,cc.valor,cc.fechaHasta,cc.numero,");
/* 120:119 */     sql1.append(" dfc.idDetalleFormaCobro,dfc.documentoReferencia,co.idCobro,co.fecha,co.numero,");
/* 121:120 */     sql1.append(" e.idEmpresa,e.nombreFiscal,");
/* 122:121 */     sql1.append(" fp.idFormaPago,fp.nombre,u.idUsuario,u.nombreUsuario) ");
/* 123:122 */     sql1.append(" from DetalleCierreCaja d ");
/* 124:    */     
/* 125:    */ 
/* 126:125 */     sql1.append(" LEFT JOIN d.interfazContableProceso i ");
/* 127:126 */     sql1.append(" JOIN d.cierreCaja cc ");
/* 128:127 */     sql1.append(" LEFT JOIN cc.caja c ");
/* 129:128 */     sql1.append(" JOIN cc.usuario u ");
/* 130:129 */     sql1.append(" JOIN d.detalleFormaCobro dfc ");
/* 131:130 */     sql1.append(" JOIN dfc.formaPago fp ");
/* 132:131 */     sql1.append(" JOIN dfc.cobro co ");
/* 133:132 */     sql1.append(" JOIN co.empresa e ");
/* 134:133 */     sql1.append(" WHERE ((i IS NULL AND co.fecha <= :fechaDeposito) OR i.idInterfazContableProceso = :idInterfazContableProceso) ");
/* 135:134 */     sql1.append(" AND d.idSucursal = :idSucursal ");
/* 136:135 */     sql1.append(" AND fp.indicadorDepositoAutomatico = false ");
/* 137:136 */     sql1.append(" AND fp.indicadorRetencionFuente = false ");
/* 138:137 */     sql1.append(" AND fp.indicadorRetencionIva = false ");
/* 139:138 */     sql1.append(" AND co.estado = :estado ");
/* 140:    */     
/* 141:140 */     Query query = this.em.createQuery(sql1.toString());
/* 142:141 */     query.setParameter("idInterfazContableProceso", Integer.valueOf(idInterfazContableProceso));
/* 143:142 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 144:143 */     query.setParameter("fechaDeposito", interfazContableProceso.getFechaHasta());
/* 145:144 */     query.setParameter("estado", Estado.CONTABILIZADO);
/* 146:145 */     listaCierreCaja.addAll(query.getResultList());
/* 147:    */     
/* 148:    */ 
/* 149:148 */     StringBuilder sql2 = new StringBuilder();
/* 150:149 */     sql2.append("SELECT New DetalleCierreCaja (d.idDetalleCierreCaja,d.idSucursal,d.idOrganizacion,d.valor,i.idInterfazContableProceso,i.numero,i.documentoBase,cc.idCierreCaja,cc.valor,cc.fechaHasta,cc.numero,");
/* 151:150 */     sql2.append("ac.idAnticipoCliente,ac.documentoReferencia,ac.fecha,ac.numero,e.idEmpresa,e.nombreFiscal, ");
/* 152:151 */     sql2.append("fp.idFormaPago,fp.nombre,u.idUsuario,u.nombreUsuario) ");
/* 153:152 */     sql2.append("FROM DetalleCierreCaja d ");
/* 154:153 */     sql2.append("LEFT JOIN d.interfazContableProceso i ");
/* 155:    */     
/* 156:    */ 
/* 157:156 */     sql2.append("JOIN d.cierreCaja cc ");
/* 158:157 */     sql2.append("LEFT JOIN cc.caja c ");
/* 159:158 */     sql2.append("JOIN cc.usuario u ");
/* 160:159 */     sql2.append("JOIN d.anticipoCliente ac ");
/* 161:160 */     sql2.append("JOIN ac.empresa e ");
/* 162:161 */     sql2.append("JOIN ac.formaPago fp ");
/* 163:    */     
/* 164:163 */     sql2.append("WHERE ((i IS NULL AND ac.fecha <= :fechaDeposito) OR i.idInterfazContableProceso = :idInterfazContableProceso) ");
/* 165:164 */     sql2.append("AND d.idSucursal = :idSucursal ");
/* 166:165 */     sql2.append("AND fp.indicadorDepositoAutomatico = false ");
/* 167:166 */     sql2.append("AND ac.estado <> :estado ");
/* 168:    */     
/* 169:168 */     query = this.em.createQuery(sql2.toString());
/* 170:169 */     query.setParameter("idInterfazContableProceso", Integer.valueOf(idInterfazContableProceso));
/* 171:170 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 172:171 */     query.setParameter("fechaDeposito", interfazContableProceso.getFechaHasta());
/* 173:172 */     query.setParameter("estado", Estado.ANULADO);
/* 174:173 */     listaCierreCaja.addAll(query.getResultList());
/* 175:175 */     for (DetalleCierreCaja detalleCierreCaja : listaCierreCaja) {
/* 176:176 */       if (detalleCierreCaja.getInterfazContableProceso() != null) {
/* 177:177 */         detalleCierreCaja.setSeleccionado(true);
/* 178:    */       }
/* 179:    */     }
/* 180:181 */     return listaCierreCaja;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List getReporteDepositoCierreCajaBean(InterfazContableProceso interfazContableProceso)
/* 184:    */   {
/* 185:187 */     List<Object[]> listaGeneral = new ArrayList();
/* 186:    */     
/* 187:189 */     StringBuilder sql = new StringBuilder();
/* 188:190 */     sql.append(" SELECT icp.numero,dcc.valor,fp.nombre,c.nombre,u.nombreUsuario, dfc.documentoReferencia, ");
/* 189:191 */     sql.append("  b.nombre, em.nombreFiscal, icp.documentoReferencia, asi.numero ");
/* 190:192 */     sql.append(" FROM DetalleCierreCaja dcc");
/* 191:193 */     sql.append(" JOIN dcc.cierreCaja cc\t");
/* 192:194 */     sql.append(" LEFT JOIN cc.caja c");
/* 193:195 */     sql.append(" JOIN cc.usuario u");
/* 194:196 */     sql.append(" JOIN dcc.interfazContableProceso icp");
/* 195:197 */     sql.append(" JOIN dcc.detalleFormaCobro dfc");
/* 196:198 */     sql.append(" JOIN dfc.formaPago fp");
/* 197:199 */     sql.append(" JOIN dfc.cobro cob\t");
/* 198:200 */     sql.append(" JOIN cob.empresa em ");
/* 199:201 */     sql.append(" LEFT JOIN icp.asiento asi ");
/* 200:202 */     sql.append(" LEFT JOIN dfc.banco b ");
/* 201:203 */     sql.append(" WHERE icp=:interfazContableProceso");
/* 202:204 */     Query query = this.em.createQuery(sql.toString());
/* 203:205 */     query.setParameter("interfazContableProceso", interfazContableProceso);
/* 204:    */     
/* 205:207 */     listaGeneral.addAll(query.getResultList());
/* 206:    */     
/* 207:    */ 
/* 208:210 */     sql = new StringBuilder();
/* 209:211 */     sql.append(" SELECT icp.numero,dcc.valor,fp.nombre,c.nombre,u.nombreUsuario, '', ");
/* 210:212 */     sql.append("  ' ' , em.nombreFiscal, icp.documentoReferencia, asi.numero ");
/* 211:213 */     sql.append(" FROM DetalleCierreCaja dcc");
/* 212:214 */     sql.append(" JOIN dcc.cierreCaja cc\t");
/* 213:215 */     sql.append(" LEFT JOIN cc.caja c");
/* 214:216 */     sql.append(" JOIN cc.usuario u");
/* 215:217 */     sql.append(" JOIN dcc.interfazContableProceso icp");
/* 216:218 */     sql.append(" JOIN dcc.anticipoCliente ac");
/* 217:219 */     sql.append(" JOIN ac.formaPago fp");
/* 218:220 */     sql.append(" JOIN ac.empresa em ");
/* 219:221 */     sql.append(" LEFT JOIN icp.asiento asi ");
/* 220:222 */     sql.append(" WHERE icp=:interfazContableProceso");
/* 221:223 */     query = this.em.createQuery(sql.toString());
/* 222:224 */     query.setParameter("interfazContableProceso", interfazContableProceso);
/* 223:    */     
/* 224:    */ 
/* 225:227 */     listaGeneral.addAll(query.getResultList());
/* 226:    */     
/* 227:    */ 
/* 228:230 */     return listaGeneral;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public List<Object[]> getListaReporteDepositosCierreCaja(Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 232:    */   {
/* 233:236 */     StringBuilder sql = new StringBuilder();
/* 234:237 */     List<Object[]> datos = new ArrayList();
/* 235:    */     
/* 236:239 */     sql.append(" SELECT icp.fechaContabilizacion, icp.fechaDesde, icp.fechaHasta,icp.numero,icp.documentoReferencia, ");
/* 237:240 */     sql.append(" b.nombre, fp.nombre, c.fecha, e.nombreFiscal, dfc.documentoReferencia, fc.numero, dc.valor  ");
/* 238:241 */     sql.append(" FROM DetalleCierreCaja dcc ");
/* 239:242 */     sql.append(" INNER JOIN dcc.interfazContableProceso icp ");
/* 240:243 */     sql.append(" INNER JOIN dcc.cierreCaja cc ");
/* 241:244 */     sql.append(" INNER JOIN dcc.detalleFormaCobro dfc ");
/* 242:    */     
/* 243:    */ 
/* 244:    */ 
/* 245:248 */     sql.append(" LEFT JOIN dfc.banco b ");
/* 246:249 */     sql.append(" INNER JOIN dfc.formaPago fp ");
/* 247:250 */     sql.append(" INNER JOIN dfc.cobro c, DetalleCobro dc ");
/* 248:251 */     sql.append(" INNER JOIN dc.cuentaPorCobrar cpc ");
/* 249:252 */     sql.append(" INNER JOIN cpc.facturaCliente fc ");
/* 250:253 */     sql.append(" INNER JOIN fc.empresa e ");
/* 251:254 */     if (fechaDesde == null) {
/* 252:255 */       sql.append(" WHERE icp.fechaHasta = :fechaHasta");
/* 253:    */     } else {
/* 254:257 */       sql.append(" WHERE icp.fechaDesde BETWEEN :fechaDesde AND :fechaHasta");
/* 255:    */     }
/* 256:259 */     sql.append(" AND dc.cobro = c ");
/* 257:260 */     sql.append(" AND icp.idOrganizacion = :idOrganizacion ");
/* 258:261 */     sql.append(" ORDER BY icp.numero ");
/* 259:    */     
/* 260:263 */     Query query = this.em.createQuery(sql.toString());
/* 261:264 */     if (fechaDesde != null) {
/* 262:265 */       query.setParameter("fechaDesde", fechaDesde);
/* 263:    */     }
/* 264:267 */     query.setParameter("fechaHasta", fechaHasta);
/* 265:268 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 266:    */     
/* 267:270 */     datos = query.getResultList();
/* 268:271 */     System.out.println("datos 11111 :::::   " + datos.size());
/* 269:    */     
/* 270:273 */     StringBuilder sql2 = new StringBuilder();
/* 271:274 */     sql2.append("SELECT i.fechaContabilizacion, i.fechaDesde, i.fechaHasta,i.numero,i.documentoReferencia, ");
/* 272:275 */     sql2.append(" b.nombre, fp.nombre, ac.fecha, e.nombreFiscal, ac.documentoReferencia, fc.numero, ac.valor  ");
/* 273:276 */     sql2.append(" FROM DetalleCierreCaja d ");
/* 274:277 */     sql2.append(" INNER JOIN d.cierreCaja cc ");
/* 275:278 */     sql2.append(" LEFT JOIN cc.caja c ");
/* 276:279 */     sql2.append(" INNER JOIN cc.usuario u ");
/* 277:280 */     sql2.append(" INNER JOIN d.anticipoCliente ac ");
/* 278:281 */     sql2.append(" INNER JOIN ac.empresa e ");
/* 279:282 */     sql2.append(" LEFT  JOIN ac.formaPago fp ");
/* 280:283 */     sql2.append(" LEFT  JOIN d.interfazContableProceso i ");
/* 281:284 */     sql2.append(" LEFT  JOIN ac.cuentaBancariaOrganizacion cbo ");
/* 282:285 */     sql2.append(" LEFT  JOIN cbo.cuentaBancaria cb ");
/* 283:286 */     sql2.append(" LEFT  JOIN cb.banco b ");
/* 284:287 */     sql2.append(" LEFT  JOIN ac.notaCreditoCliente ncc ");
/* 285:288 */     sql2.append(" LEFT  JOIN ncc.facturaClientePadre fc ");
/* 286:289 */     if (fechaDesde == null) {
/* 287:290 */       sql2.append(" WHERE i.fechaHasta = :fechaHasta");
/* 288:    */     } else {
/* 289:292 */       sql2.append(" WHERE i.fechaDesde BETWEEN :fechaDesde AND :fechaHasta");
/* 290:    */     }
/* 291:295 */     sql2.append(" AND i.idOrganizacion = :idOrganizacion ");
/* 292:296 */     sql2.append(" ORDER BY i.numero ");
/* 293:    */     
/* 294:298 */     query = this.em.createQuery(sql2.toString());
/* 295:299 */     if (fechaDesde != null) {
/* 296:300 */       query.setParameter("fechaDesde", fechaDesde);
/* 297:    */     }
/* 298:302 */     query.setParameter("fechaHasta", fechaHasta);
/* 299:303 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 300:304 */     datos.addAll(query.getResultList());
/* 301:    */     
/* 302:306 */     System.out.println("datos 2222 :::::   " + query.getResultList().size());
/* 303:    */     
/* 304:308 */     return datos;
/* 305:    */   }
/* 306:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.InterfazContableProcesoDao
 * JD-Core Version:    0.7.0.1
 */