/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.Banco;
/*   5:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.CuentaContable;
/*   9:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  10:    */ import com.asinfo.as2.entities.DetallePagoCash;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.Empleado;
/*  13:    */ import com.asinfo.as2.entities.Empresa;
/*  14:    */ import com.asinfo.as2.entities.FormaPago;
/*  15:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  16:    */ import com.asinfo.as2.entities.PagoCash;
/*  17:    */ import com.asinfo.as2.entities.PagoRol;
/*  18:    */ import com.asinfo.as2.entities.Secuencia;
/*  19:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  20:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  21:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  22:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  23:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.Stateless;
/*  27:    */ import javax.persistence.EntityManager;
/*  28:    */ import javax.persistence.Query;
/*  29:    */ import javax.persistence.TypedQuery;
/*  30:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  31:    */ import javax.persistence.criteria.CriteriaQuery;
/*  32:    */ import javax.persistence.criteria.Expression;
/*  33:    */ import javax.persistence.criteria.Fetch;
/*  34:    */ import javax.persistence.criteria.JoinType;
/*  35:    */ import javax.persistence.criteria.Predicate;
/*  36:    */ import javax.persistence.criteria.Root;
/*  37:    */ 
/*  38:    */ @Stateless
/*  39:    */ public class PagoCashDao
/*  40:    */   extends AbstractDaoAS2<PagoCash>
/*  41:    */ {
/*  42:    */   public PagoCashDao()
/*  43:    */   {
/*  44: 47 */     super(PagoCash.class);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public PagoCash cargarDetalle(int idPagoCash)
/*  48:    */   {
/*  49: 57 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  50: 58 */     CriteriaQuery<PagoCash> cq = cb.createQuery(PagoCash.class);
/*  51: 59 */     Root<PagoCash> from = cq.from(PagoCash.class);
/*  52: 60 */     Fetch<Object, Object> documento = from.fetch("documento", JoinType.LEFT);
/*  53: 61 */     documento.fetch("secuencia", JoinType.LEFT);
/*  54: 62 */     documento.fetch("tipoAsiento", JoinType.LEFT);
/*  55: 63 */     from.fetch("ordenPagoProveedor", JoinType.LEFT);
/*  56: 64 */     Fetch<Object, Object> documentoPago = from.fetch("documentoPago", JoinType.LEFT);
/*  57: 65 */     documentoPago.fetch("secuencia", JoinType.LEFT);
/*  58: 66 */     documentoPago.fetch("tipoAsiento", JoinType.LEFT);
/*  59: 67 */     Fetch<Object, Object> documentoAnticipo = from.fetch("documentoAnticipo", JoinType.LEFT);
/*  60: 68 */     documentoAnticipo.fetch("secuencia", JoinType.LEFT);
/*  61: 69 */     documentoAnticipo.fetch("tipoAsiento", JoinType.LEFT);
/*  62: 70 */     from.fetch("formaPago", JoinType.LEFT);
/*  63: 71 */     Fetch<Object, Object> cuentaBancariaOrganizacion = from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  64: 72 */     Fetch<Object, Object> cuentaBancaria = cuentaBancariaOrganizacion.fetch("cuentaBancaria", JoinType.LEFT);
/*  65: 73 */     cuentaBancaria.fetch("banco", JoinType.LEFT);
/*  66: 74 */     cq.where(cb.equal(from.get("idPagoCash"), Integer.valueOf(idPagoCash)));
/*  67: 75 */     PagoCash pc = (PagoCash)this.em.createQuery(cq.select(from)).getSingleResult();
/*  68:    */     
/*  69: 77 */     CriteriaQuery<FormaPagoCuentaBancariaOrganizacion> cqFP = cb.createQuery(FormaPagoCuentaBancariaOrganizacion.class);
/*  70: 78 */     Root<FormaPagoCuentaBancariaOrganizacion> fromFP = cqFP.from(FormaPagoCuentaBancariaOrganizacion.class);
/*  71: 79 */     fromFP.fetch("formaPago", JoinType.LEFT);
/*  72: 80 */     fromFP.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  73: 81 */     cqFP.where(cb.equal(fromFP.get("cuentaBancariaOrganizacion"), pc.getCuentaBancariaOrganizacion()));
/*  74: 82 */     List<FormaPagoCuentaBancariaOrganizacion> listaFormaPago = this.em.createQuery(cqFP.select(fromFP)).getResultList();
/*  75: 83 */     pc.getCuentaBancariaOrganizacion().setListaFormaPago(listaFormaPago);
/*  76:    */     
/*  77: 85 */     CriteriaQuery<DetallePagoCash> cqDetalle = cb.createQuery(DetallePagoCash.class);
/*  78: 86 */     Root<DetallePagoCash> fromDetalle = cqDetalle.from(DetallePagoCash.class);
/*  79: 87 */     fromDetalle.fetch("personaResponsable", JoinType.LEFT);
/*  80: 88 */     Fetch<Object, Object> proveedor = fromDetalle.fetch("proveedor", JoinType.LEFT);
/*  81: 89 */     Fetch<Object, Object> detalleOrdenPagoProveedor = fromDetalle.fetch("detalleOrdenPagoProveedor", JoinType.LEFT);
/*  82: 90 */     detalleOrdenPagoProveedor.fetch("ordenPagoProveedor", JoinType.LEFT);
/*  83: 91 */     Fetch<Object, Object> empresa = proveedor.fetch("empresa", JoinType.LEFT);
/*  84: 92 */     empresa.fetch("tipoIdentificacion", JoinType.LEFT);
/*  85: 93 */     Fetch<Object, Object> cxp = fromDetalle.fetch("cuentaPorPagar", JoinType.LEFT);
/*  86: 94 */     cxp.fetch("facturaProveedor", JoinType.LEFT);
/*  87: 95 */     cqDetalle.where(cb.equal(fromDetalle.get("pagoCash"), pc));
/*  88: 96 */     List<DetallePagoCash> listaDetallePagoCash = this.em.createQuery(cqDetalle.select(fromDetalle)).getResultList();
/*  89: 97 */     for (DetallePagoCash detallePagoCash : listaDetallePagoCash) {
/*  90: 98 */       if (detallePagoCash.getCuentaPorPagar() == null) {
/*  91: 99 */         detallePagoCash.setIndicadorDeshabilitarIngreso(true);
/*  92:    */       } else {
/*  93:101 */         detallePagoCash.setDiasVencidos((int)FuncionesUtiles.DiasEntreFechas(detallePagoCash.getCuentaPorPagar().getFechaVencimiento(), pc
/*  94:102 */           .getFechaPago()));
/*  95:    */       }
/*  96:    */     }
/*  97:105 */     pc.setListaDetallePagoCash(listaDetallePagoCash);
/*  98:    */     
/*  99:107 */     return pc;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public PagoCash cargarDetalleAnulacion(int idPagoCash)
/* 103:    */   {
/* 104:111 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 105:112 */     CriteriaQuery<PagoCash> cq = cb.createQuery(PagoCash.class);
/* 106:113 */     Root<PagoCash> from = cq.from(PagoCash.class);
/* 107:114 */     cq.where(cb.equal(from.get("idPagoCash"), Integer.valueOf(idPagoCash)));
/* 108:115 */     PagoCash pc = (PagoCash)this.em.createQuery(cq.select(from)).getSingleResult();
/* 109:    */     
/* 110:117 */     CriteriaQuery<DetallePagoCash> cqDetalle = cb.createQuery(DetallePagoCash.class);
/* 111:118 */     Root<DetallePagoCash> fromDetalle = cqDetalle.from(DetallePagoCash.class);
/* 112:119 */     Fetch<Object, Object> proveedor = fromDetalle.fetch("proveedor", JoinType.LEFT);
/* 113:120 */     Fetch<Object, Object> empresa = proveedor.fetch("empresa", JoinType.LEFT);
/* 114:121 */     empresa.fetch("tipoIdentificacion", JoinType.LEFT);
/* 115:122 */     Fetch<Object, Object> cxp = fromDetalle.fetch("cuentaPorPagar", JoinType.LEFT);
/* 116:123 */     cxp.fetch("facturaProveedor", JoinType.LEFT);
/* 117:124 */     cqDetalle.where(cb.equal(fromDetalle.get("pagoCash"), pc));
/* 118:125 */     List<DetallePagoCash> listaDetallePagoCash = this.em.createQuery(cqDetalle.select(fromDetalle)).getResultList();
/* 119:126 */     for (DetallePagoCash detallePagoCash : listaDetallePagoCash) {
/* 120:127 */       if (detallePagoCash.getCuentaPorPagar() == null) {
/* 121:128 */         detallePagoCash.setIndicadorDeshabilitarIngreso(true);
/* 122:    */       } else {
/* 123:130 */         detallePagoCash.setDiasVencidos((int)FuncionesUtiles.DiasEntreFechas(detallePagoCash.getCuentaPorPagar().getFechaVencimiento(), pc
/* 124:131 */           .getFechaPago()));
/* 125:    */       }
/* 126:    */     }
/* 127:134 */     pc.setListaDetallePagoCash(listaDetallePagoCash);
/* 128:    */     
/* 129:136 */     return pc;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<PagoCash> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 133:    */   {
/* 134:146 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 135:147 */     CriteriaQuery<PagoCash> criteriaQuery = criteriaBuilder.createQuery(PagoCash.class);
/* 136:148 */     Root<PagoCash> from = criteriaQuery.from(PagoCash.class);
/* 137:149 */     from.fetch("formaPago", JoinType.LEFT);
/* 138:150 */     from.fetch("documento", JoinType.LEFT);
/* 139:151 */     from.fetch("documentoPago", JoinType.LEFT);
/* 140:152 */     from.fetch("documentoAnticipo", JoinType.LEFT);
/* 141:153 */     from.fetch("ordenPagoProveedor", JoinType.LEFT);
/* 142:154 */     Fetch<Object, Object> pagoRol = from.fetch("pagoRol", JoinType.LEFT);
/* 143:155 */     pagoRol.fetch("quincena", JoinType.LEFT);
/* 144:    */     
/* 145:157 */     from.fetch("ordenPagoProveedor", JoinType.LEFT);
/* 146:158 */     Fetch<Object, Object> cuentaBancariaOrganizacion = from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/* 147:159 */     cuentaBancariaOrganizacion.fetch("cuentaBancaria", JoinType.LEFT);
/* 148:    */     
/* 149:    */ 
/* 150:162 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/* 151:163 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/* 152:    */     
/* 153:165 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 154:166 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 155:    */     
/* 156:168 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 157:    */     
/* 158:170 */     CriteriaQuery<PagoCash> select = criteriaQuery.select(from);
/* 159:171 */     TypedQuery<PagoCash> typedQuery = this.em.createQuery(select);
/* 160:172 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 161:    */     
/* 162:174 */     return typedQuery.getResultList();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void actualizarEstadoPagoCash(int idPagoCash, Estado estado)
/* 166:    */   {
/* 167:184 */     String sql = "UPDATE PagoCash  pc SET pc.estado = :estado WHERE pc.idPagoCash = :idPagoCash";
/* 168:185 */     Query query = this.em.createQuery(sql);
/* 169:186 */     query.setParameter("estado", estado).setParameter("idPagoCash", Integer.valueOf(idPagoCash));
/* 170:187 */     query.executeUpdate();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void actualizaIndicadorBloqueado(int idPagoCash, boolean bloqueo)
/* 174:    */     throws ExcepcionAS2Financiero
/* 175:    */   {
/* 176:197 */     StringBuilder sql = new StringBuilder();
/* 177:198 */     sql.append("UPDATE CuentaPorPagar cxp SET cxp.indicadorBloqueada = :bloqueo ");
/* 178:199 */     sql.append(" WHERE EXISTS (SELECT 1 FROM DetallePagoCash dpc WHERE dpc.cuentaPorPagar.idCuentaPorPagar = cxp.idCuentaPorPagar ");
/* 179:200 */     sql.append(" AND dpc.pagoCash.idPagoCash = :idPagoCash)");
/* 180:201 */     Query query = this.em.createQuery(sql.toString()).setParameter("bloqueo", Boolean.valueOf(bloqueo)).setParameter("idPagoCash", Integer.valueOf(idPagoCash));
/* 181:    */     
/* 182:203 */     query.executeUpdate();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public PagoCash cargarDetalleEmpleado(int idPagoCash)
/* 186:    */     throws ExcepcionAS2Financiero
/* 187:    */   {
/* 188:217 */     PagoCash pagoCash = (PagoCash)buscarPorId(Integer.valueOf(idPagoCash));
/* 189:218 */     pagoCash.getListaDetallePagoCash().size();
/* 190:219 */     pagoCash.getDocumento().getIdDocumento();
/* 191:220 */     pagoCash.getDocumento().getTipoAsiento().getIdTipoAsiento();
/* 192:221 */     pagoCash.getDocumento().getSecuencia().getPatron();
/* 193:222 */     pagoCash.getCuentaBancariaOrganizacion().getIdCuentaBancariaOrganizacion();
/* 194:223 */     pagoCash.getCuentaBancariaOrganizacion().getListaFormaPago().size();
/* 195:224 */     for (FormaPagoCuentaBancariaOrganizacion fpcbo : pagoCash.getCuentaBancariaOrganizacion().getListaFormaPago()) {
/* 196:225 */       fpcbo.getFormaPago().getIdFormaPago();
/* 197:    */     }
/* 198:227 */     pagoCash.getListaDetallePagoCash().size();
/* 199:228 */     pagoCash.getCuentaBancariaOrganizacion().getIdCuentaBancariaOrganizacion();
/* 200:229 */     pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getIdCuentaBancaria();
/* 201:230 */     pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getId();
/* 202:231 */     pagoCash.getFormaPago().getIdFormaPago();
/* 203:232 */     pagoCash.getPagoRol().getIdPagoRol();
/* 204:233 */     pagoCash.getPagoRol().getDocumento().getIdDocumento();
/* 205:234 */     pagoCash.getCuentaBancariaOrganizacion().getCuentaContableBanco().getIdCuentaContable();
/* 206:235 */     if (pagoCash.getAsiento() != null)
/* 207:    */     {
/* 208:236 */       pagoCash.getAsiento().getIdAsiento();
/* 209:237 */       pagoCash.getAsiento().getTipoAsiento().getIdTipoAsiento();
/* 210:    */     }
/* 211:240 */     for (DetallePagoCash detallePagoCash : pagoCash.getListaDetallePagoCash()) {
/* 212:241 */       if (detallePagoCash.getEmpleado() != null)
/* 213:    */       {
/* 214:242 */         detallePagoCash.getEmpleado().getEmpresa().getIdEmpresa();
/* 215:243 */         detallePagoCash.getEmpleado().getEmpresa().getTipoIdentificacion().getIdTipoIdentificacion();
/* 216:244 */         detallePagoCash.getEmpleado().getEmpresa().getCategoriaEmpresa().getIdCategoriaEmpresa();
/* 217:    */       }
/* 218:    */     }
/* 219:256 */     return pagoCash;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public List<Object[]> datosPagoCash(int idOrganizacion, PagoCash pagoCash)
/* 223:    */   {
/* 224:261 */     StringBuilder sql = new StringBuilder();
/* 225:262 */     sql.append(" select e.nombreFiscal, pc.numero, pc.fechaPago, dpc.valor, fp.numero, fpsri.numero, fp.fecha, cpp.fechaVencimiento, dpc.indicadorAprobado, dpc.indicadorProcesado,  ");
/* 226:263 */     sql.append(" fpsri.establecimiento,fpsri.puntoEmision ");
/* 227:264 */     sql.append(" ,ce.nombre, banc.nombre, cb.numero, pc.documentoReferencia, dpc.cuentaBancariaEmpresa, dpc.tipoCuentaBancaria, dpc.nombreBanco, dpc.codigoBanco, fp.descripcion, CONCAT(pr.apellidos,' ',pr.nombres), CONCAT(prf.apellidos,' ',prf.nombres) ");
/* 228:265 */     sql.append(" from DetallePagoCash dpc ");
/* 229:266 */     sql.append(" inner join dpc.pagoCash pc ");
/* 230:267 */     sql.append(" left  join dpc.personaResponsable pr ");
/* 231:268 */     sql.append(" inner join pc.cuentaBancariaOrganizacion cbo ");
/* 232:269 */     sql.append(" left join cbo.cuentaBancaria cb ");
/* 233:270 */     sql.append(" left join cb.banco banc ");
/* 234:271 */     sql.append(" inner join pc.documento d ");
/* 235:272 */     sql.append(" left join dpc.proveedor p ");
/* 236:273 */     sql.append(" inner join p.empresa e ");
/* 237:274 */     sql.append(" inner join e.categoriaEmpresa ce ");
/* 238:275 */     sql.append(" left join dpc.cuentaPorPagar cpp ");
/* 239:276 */     sql.append(" left join cpp.facturaProveedor fp ");
/* 240:277 */     sql.append(" left join fp.personaResponsable prf ");
/* 241:278 */     sql.append(" left join fp.facturaProveedorSRI fpsri ");
/* 242:279 */     sql.append(" where pc.idOrganizacion = :idOrganizacion ");
/* 243:280 */     sql.append(" and pc = :pagoCash ");
/* 244:    */     
/* 245:282 */     Query query = this.em.createQuery(sql.toString());
/* 246:283 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 247:284 */     query.setParameter("pagoCash", pagoCash);
/* 248:    */     
/* 249:286 */     return query.getResultList();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public List<Object[]> datosPagoCashEmpleado(PagoCash pagoCash)
/* 253:    */   {
/* 254:292 */     StringBuilder sql = new StringBuilder();
/* 255:293 */     sql.append(" select pr.fecha, pc.numero, banc.nombre,  dpc.cuentaBancariaEmpresa, e.nombreFiscal, (dpc.valor/100), dpc.tipoCuentaBancaria, dpc.cuentaBancariaEmpleado ,dpc.nombreBanco ");
/* 256:294 */     sql.append(" , ep.nombres, ep.apellidos, qu.nombre, e.identificacion, e.codigo ");
/* 257:295 */     sql.append(" from DetallePagoCash dpc ");
/* 258:296 */     sql.append(" inner join dpc.pagoCash pc ");
/* 259:297 */     sql.append(" inner join pc.cuentaBancariaOrganizacion cbo ");
/* 260:298 */     sql.append(" inner join pc.pagoRol pr ");
/* 261:299 */     sql.append(" inner join pr.quincena qu ");
/* 262:300 */     sql.append(" left join cbo.cuentaBancaria cb ");
/* 263:301 */     sql.append(" left join cb.banco banc ");
/* 264:302 */     sql.append(" left join pc.documento d ");
/* 265:303 */     sql.append(" left join dpc.empleado ep ");
/* 266:304 */     sql.append(" left join ep.empresa e ");
/* 267:305 */     sql.append(" where pc = :pagoCash ");
/* 268:306 */     sql.append(" order by ep.apellidos ");
/* 269:    */     
/* 270:308 */     Query query = this.em.createQuery(sql.toString());
/* 271:309 */     query.setParameter("pagoCash", pagoCash);
/* 272:    */     
/* 273:311 */     return query.getResultList();
/* 274:    */   }
/* 275:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PagoCashDao
 * JD-Core Version:    0.7.0.1
 */