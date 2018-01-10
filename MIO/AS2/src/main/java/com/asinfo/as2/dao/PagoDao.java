/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.PagoAnticipoCheque;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.Contacto;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.CuentaContable;
/*  10:    */ import com.asinfo.as2.entities.CuentaContableCruceCuentaBancariaOrganizacion;
/*  11:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  12:    */ import com.asinfo.as2.entities.DetalleFormaPago;
/*  13:    */ import com.asinfo.as2.entities.DetallePago;
/*  14:    */ import com.asinfo.as2.entities.Documento;
/*  15:    */ import com.asinfo.as2.entities.Empresa;
/*  16:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  17:    */ import com.asinfo.as2.entities.FormaPago;
/*  18:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  19:    */ import com.asinfo.as2.entities.OrdenPagoProveedor;
/*  20:    */ import com.asinfo.as2.entities.Pago;
/*  21:    */ import com.asinfo.as2.entities.Secuencia;
/*  22:    */ import com.asinfo.as2.entities.Sucursal;
/*  23:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  24:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  25:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.ejb.Stateless;
/*  31:    */ import javax.persistence.EntityManager;
/*  32:    */ import javax.persistence.Query;
/*  33:    */ import javax.persistence.TypedQuery;
/*  34:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  35:    */ import javax.persistence.criteria.CriteriaQuery;
/*  36:    */ import javax.persistence.criteria.Expression;
/*  37:    */ import javax.persistence.criteria.Fetch;
/*  38:    */ import javax.persistence.criteria.JoinType;
/*  39:    */ import javax.persistence.criteria.Predicate;
/*  40:    */ import javax.persistence.criteria.Root;
/*  41:    */ 
/*  42:    */ @Stateless
/*  43:    */ public class PagoDao
/*  44:    */   extends AbstractDaoAS2<Pago>
/*  45:    */ {
/*  46:    */   public PagoDao()
/*  47:    */   {
/*  48: 57 */     super(Pago.class);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Pago cargarDetalle(int idPago)
/*  52:    */   {
/*  53: 68 */     Pago pago = (Pago)buscarPorId(Integer.valueOf(idPago));
/*  54: 69 */     pago.getDocumento().getId();
/*  55: 71 */     if (pago.getDocumento().getSecuencia() != null) {
/*  56: 72 */       pago.getDocumento().getSecuencia().getId();
/*  57:    */     }
/*  58: 75 */     pago.getDocumento().getTipoAsiento();
/*  59: 76 */     pago.getEmpresa().getId();
/*  60: 77 */     if (pago.getOrdenPagoProveedor() != null) {
/*  61: 78 */       pago.getOrdenPagoProveedor().getId();
/*  62:    */     }
/*  63: 81 */     pago.getListaDetallePago().size();
/*  64: 82 */     for (DetallePago detallePago : pago.getListaDetallePago())
/*  65:    */     {
/*  66: 83 */       detallePago.getCuentaPorPagar();
/*  67: 84 */       detallePago.getCuentaPorPagar().getId();
/*  68: 85 */       detallePago.getCuentaPorPagar().getFacturaProveedor().getId();
/*  69:    */     }
/*  70: 88 */     pago.getListaDetalleFormaPago().size();
/*  71: 89 */     for (DetalleFormaPago detalleFormaPago : pago.getListaDetalleFormaPago())
/*  72:    */     {
/*  73: 90 */       detalleFormaPago.getFormaPago().getId();
/*  74: 92 */       if (detalleFormaPago.getCuentaBancariaOrganizacion() != null)
/*  75:    */       {
/*  76: 93 */         detalleFormaPago.getCuentaBancariaOrganizacion().getId();
/*  77: 94 */         detalleFormaPago.getCuentaBancariaOrganizacion().getCuentaBancaria().getId();
/*  78: 95 */         detalleFormaPago.getCuentaContableFormaPago().getId();
/*  79: 96 */         detalleFormaPago.getCuentaBancariaOrganizacion().getListaFormaPago().size();
/*  80: 97 */         detalleFormaPago.getCuentaBancariaOrganizacion().getListaCuentaContableCruceCuentaBancariaOrganizacion().size();
/*  81: 99 */         for (CuentaContableCruceCuentaBancariaOrganizacion ccccbo : detalleFormaPago.getCuentaBancariaOrganizacion()
/*  82:100 */           .getListaCuentaContableCruceCuentaBancariaOrganizacion())
/*  83:    */         {
/*  84:101 */           ccccbo.getCuentaBancariaOrganizacion().getIdCuentaBancariaOrganizacion();
/*  85:102 */           ccccbo.getCuentaContable().getIdCuentaContable();
/*  86:    */         }
/*  87:105 */         for (FormaPagoCuentaBancariaOrganizacion formaPagoCuenta : detalleFormaPago.getCuentaBancariaOrganizacion().getListaFormaPago()) {
/*  88:106 */           formaPagoCuenta.getFormaPago().getId();
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:111 */     if (pago.getAsiento() != null) {
/*  93:112 */       pago.getAsiento().getId();
/*  94:    */     }
/*  95:115 */     if (pago.getSucursal() != null) {
/*  96:116 */       pago.getSucursal().getId();
/*  97:    */     }
/*  98:119 */     return pago;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public List<Pago> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 102:    */   {
/* 103:130 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 104:131 */     CriteriaQuery<Pago> criteriaQuery = criteriaBuilder.createQuery(Pago.class);
/* 105:132 */     Root<Pago> from = criteriaQuery.from(Pago.class);
/* 106:    */     
/* 107:134 */     Fetch<Object, Object> empresa = from.fetch("empresa", JoinType.LEFT);
/* 108:135 */     empresa.fetch("cliente", JoinType.LEFT);
/* 109:136 */     empresa.fetch("proveedor", JoinType.LEFT);
/* 110:137 */     empresa.fetch("empleado", JoinType.LEFT);
/* 111:    */     
/* 112:139 */     from.fetch("pagoCash", JoinType.LEFT);
/* 113:140 */     from.fetch("ordenPagoProveedor", JoinType.LEFT);
/* 114:141 */     from.fetch("asiento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/* 115:142 */     from.fetch("documento", JoinType.LEFT);
/* 116:143 */     from.fetch("sucursal", JoinType.LEFT).fetch("ubicacion", JoinType.LEFT);
/* 117:    */     
/* 118:145 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 119:146 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 120:    */     
/* 121:148 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 122:    */     
/* 123:150 */     CriteriaQuery<Pago> select = criteriaQuery.select(from);
/* 124:    */     
/* 125:152 */     TypedQuery<Pago> typedQuery = this.em.createQuery(select);
/* 126:153 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 127:    */     
/* 128:155 */     return typedQuery.getResultList();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<DetalleInterfazContable> getDetallePagoIC(int idPago)
/* 132:    */     throws ExcepcionAS2Financiero
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:171 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal, CONCAT(do.nombre,' #',fc.numero,CASE WHEN fp IS NOT NULL THEN CONCAT(' F:/',fp.establecimiento,'-',fp.puntoEmision,'-',fp.numero) ELSE '' END), '', dc.valor)  FROM DetallePago dc  INNER JOIN dc.cuentaPorPagar cx  INNER JOIN cx.facturaProveedor fc  LEFT JOIN fc.facturaProveedorSRI fp  INNER JOIN fc.documento do  INNER JOIN dc.pago co  INNER JOIN co.empresa em  INNER JOIN em.categoriaEmpresa ce  INNER JOIN ce.cuentaContableProveedor cc  WHERE co.idPago=:idPago");
/* 137:    */       
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:178 */       query.setParameter("idPago", Integer.valueOf(idPago));
/* 144:    */       
/* 145:180 */       return query.getResultList();
/* 146:    */     }
/* 147:    */     catch (IllegalArgumentException e)
/* 148:    */     {
/* 149:183 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableProveedor");
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<DetalleInterfazContable> getFormaPagoBancosIC(int idPago)
/* 154:    */     throws ExcepcionAS2Financiero
/* 155:    */   {
/* 156:    */     try
/* 157:    */     {
/* 158:200 */       StringBuilder sql = new StringBuilder();
/* 159:201 */       sql.append("SELECT new DetalleInterfazContable(df.cuentaContableFormaPago.idCuentaContable,em.nombreFiscal, ");
/* 160:202 */       sql.append(" CONCAT(do.nombre, ' #', co.numero,' ',co.descripcion), df.documentoReferencia, co.beneficiario,df.formaPago.idFormaPago, -df.valor) ");
/* 161:    */       
/* 162:204 */       sql.append(" FROM DetalleFormaPago df ");
/* 163:205 */       sql.append(" INNER JOIN df.pago co ");
/* 164:206 */       sql.append(" INNER JOIN co.documento do ");
/* 165:207 */       sql.append(" INNER JOIN co.empresa em ");
/* 166:208 */       sql.append(" WHERE co.idPago=:idPago");
/* 167:209 */       Query query = this.em.createQuery(sql.toString());
/* 168:    */       
/* 169:211 */       query.setParameter("idPago", Integer.valueOf(idPago));
/* 170:    */       
/* 171:213 */       return query.getResultList();
/* 172:    */     }
/* 173:    */     catch (IllegalArgumentException e)
/* 174:    */     {
/* 175:216 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuenta contable Pago Cash en cuenta pagos");
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void actualizaEstado(int idPago, Estado estado)
/* 180:    */   {
/* 181:251 */     Query query = this.em.createQuery("UPDATE Pago p SET p.estado = :estado WHERE p.idPago = :idPago");
/* 182:    */     
/* 183:253 */     query.setParameter("estado", estado).setParameter("idPago", Integer.valueOf(idPago)).executeUpdate();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void actualizarEstado(Integer idPago, Estado estado)
/* 187:    */   {
/* 188:264 */     Query query = this.em.createQuery("UPDATE Pago a SET a.estado=:estado WHERE a.idPago=:idPago");
/* 189:265 */     query.setParameter("idPago", idPago);
/* 190:266 */     query.setParameter("estado", estado);
/* 191:267 */     query.executeUpdate();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void actualizaIndicadorBloqueadoPago(int idPago, boolean bloqueo, boolean edicion)
/* 195:    */     throws ExcepcionAS2Financiero
/* 196:    */   {
/* 197:278 */     StringBuilder sql = new StringBuilder();
/* 198:279 */     StringBuilder sql1 = new StringBuilder();
/* 199:    */     
/* 200:281 */     HashMap<String, Object[]> hashMapCuentasxPagar = new HashMap();
/* 201:282 */     String facturas = "";
/* 202:    */     
/* 203:284 */     sql.append("select fp.numero, cxp.indicadorBloqueada FROM CuentaPorPagar cxp ");
/* 204:285 */     sql.append("INNER JOIN cxp.facturaProveedor fp ");
/* 205:286 */     sql.append(" WHERE EXISTS (SELECT 1 FROM DetallePago dp WHERE dp.cuentaPorPagar.idCuentaPorPagar = cxp.idCuentaPorPagar ");
/* 206:287 */     sql.append(" AND cxp.indicadorBloqueada = TRUE");
/* 207:288 */     sql.append(" AND dp.pago.idPago = :idPago)");
/* 208:289 */     Query query = this.em.createQuery(sql.toString()).setParameter("idPago", Integer.valueOf(idPago));
/* 209:    */     
/* 210:291 */     List<Object[]> lista = query.getResultList();
/* 211:293 */     for (Object[] objects : lista) {
/* 212:294 */       facturas = facturas + objects[0].toString() + " - ";
/* 213:    */     }
/* 214:296 */     if ((bloqueo) && (!lista.isEmpty()) && (!edicion)) {
/* 215:297 */       throw new ExcepcionAS2Financiero("msg_error_pago_bloqueado", facturas);
/* 216:    */     }
/* 217:299 */     sql1.append("UPDATE CuentaPorPagar cxp SET cxp.indicadorBloqueada = :bloqueo ");
/* 218:300 */     sql1.append(" WHERE EXISTS (SELECT 1 FROM DetallePago dp WHERE dp.cuentaPorPagar.idCuentaPorPagar = cxp.idCuentaPorPagar ");
/* 219:301 */     sql1.append(" AND dp.pago.idPago = :idPago)");
/* 220:302 */     Query query1 = this.em.createQuery(sql1.toString()).setParameter("bloqueo", Boolean.valueOf(bloqueo)).setParameter("idPago", Integer.valueOf(idPago));
/* 221:    */     
/* 222:304 */     query1.executeUpdate();
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void actualizarIndicadorBloqueadoPagoPorFactura(int idCuentaPorPagar, boolean bloqueo)
/* 226:    */   {
/* 227:315 */     StringBuilder sql = new StringBuilder();
/* 228:316 */     sql.append("UPDATE CuentaPorPagar cxp SET cxp.indicadorBloqueada = :bloqueo ");
/* 229:317 */     sql.append(" WHERE cxp.idCuentaPorPagar = :idCuentaPorPagar ");
/* 230:    */     
/* 231:319 */     Query query = this.em.createQuery(sql.toString()).setParameter("bloqueo", Boolean.valueOf(bloqueo)).setParameter("idCuentaPorPagar", Integer.valueOf(idCuentaPorPagar));
/* 232:    */     
/* 233:321 */     query.executeUpdate();
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void actualizarChequeEntregadoPago(int idPago, boolean indicadorEntregaCheque, String usuarioEntregaCheque, Date fechaEntregaCheque)
/* 237:    */   {
/* 238:331 */     Query query = this.em.createQuery("UPDATE Pago p SET p.indicadorChequeEntregado = :indicadorEntregaCheque,  p.fechaEntregaCheque = :fechaEntregaCheque, p.usuarioEntregaCheque = :usuarioEntregaCheque  WHERE p.idPago = :idPago");
/* 239:    */     
/* 240:333 */     query.setParameter("indicadorEntregaCheque", Boolean.valueOf(indicadorEntregaCheque)).setParameter("idPago", Integer.valueOf(idPago))
/* 241:334 */       .setParameter("fechaEntregaCheque", fechaEntregaCheque).setParameter("usuarioEntregaCheque", usuarioEntregaCheque).executeUpdate();
/* 242:    */   }
/* 243:    */   
/* 244:    */   public List<PagoAnticipoCheque> getPagoConCheques()
/* 245:    */   {
/* 246:344 */     StringBuilder sql = new StringBuilder();
/* 247:345 */     sql.append("SELECT new PagoAnticipoCheque(p.idPago, d.documentoBase, d.nombre, p.numero, p.fecha, e.nombreFiscal, p.valor) ");
/* 248:346 */     sql.append(" FROM Pago p INNER JOIN p.documento d INNER JOIN p.empresa e ");
/* 249:347 */     sql.append(" WHERE p.indicadorTieneCheques = true AND p.indicadorChequeEntregado = false AND (p.estado <> :anulado ");
/* 250:348 */     sql.append(" OR p.estado = :contabilizado) AND d.documentoBase = :documentoPago");
/* 251:    */     
/* 252:    */ 
/* 253:351 */     Query query = this.em.createQuery(sql.toString()).setParameter("anulado", Estado.ANULADO).setParameter("contabilizado", Estado.CONTABILIZADO).setParameter("documentoPago", DocumentoBase.PAGO_PROVEEDOR);
/* 254:    */     
/* 255:353 */     return query.getResultList();
/* 256:    */   }
/* 257:    */   
/* 258:    */   public List<Contacto> getContactosPagoProveedor(Pago pago)
/* 259:    */   {
/* 260:358 */     StringBuilder sql = new StringBuilder();
/* 261:359 */     sql.append("SELECT con");
/* 262:360 */     sql.append(" FROM Pago p ");
/* 263:361 */     sql.append(" INNER JOIN p.empresa e ");
/* 264:362 */     sql.append(" INNER JOIN e.listaContacto con ");
/* 265:363 */     sql.append(" INNER JOIN FETCH con.tipoContacto tc ");
/* 266:364 */     sql.append(" WHERE p.idPago = :idPago");
/* 267:365 */     sql.append(" AND tc.indicadorNotificarPagoProveedor = true");
/* 268:    */     
/* 269:367 */     Query query = this.em.createQuery(sql.toString()).setParameter("idPago", Integer.valueOf(pago.getId()));
/* 270:368 */     return query.getResultList();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<Pago> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* 274:    */   {
/* 275:373 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 276:374 */     CriteriaQuery<Pago> cq = cb.createQuery(this.claseEntidad);
/* 277:375 */     Root<Pago> from = cq.from(this.claseEntidad);
/* 278:    */     
/* 279:    */ 
/* 280:378 */     agregarFiltros(filtros, cb, cq, from);
/* 281:    */     
/* 282:    */ 
/* 283:381 */     agregarOrdenamiento(sortField, sortOrder, cb, cq, from);
/* 284:    */     
/* 285:383 */     return this.em.createQuery(cq).getResultList();
/* 286:    */   }
/* 287:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PagoDao
 * JD-Core Version:    0.7.0.1
 */