/*   1:    */ package com.asinfo.as2.dao.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*   4:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   5:    */ import com.asinfo.as2.entities.CajaChica;
/*   6:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  10:    */ import com.asinfo.as2.entities.FormaPago;
/*  11:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  12:    */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*  13:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  14:    */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*  15:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  16:    */ import com.asinfo.as2.entities.sri.ReembolsoGastos;
/*  17:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*  21:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  22:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  23:    */ import java.io.PrintStream;
/*  24:    */ import java.math.BigDecimal;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Collection;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.Iterator;
/*  29:    */ import java.util.List;
/*  30:    */ import javax.ejb.Stateless;
/*  31:    */ import javax.persistence.EntityManager;
/*  32:    */ import javax.persistence.NoResultException;
/*  33:    */ import javax.persistence.Query;
/*  34:    */ import javax.persistence.TemporalType;
/*  35:    */ import javax.persistence.TypedQuery;
/*  36:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  37:    */ import javax.persistence.criteria.CriteriaQuery;
/*  38:    */ import javax.persistence.criteria.Fetch;
/*  39:    */ import javax.persistence.criteria.Join;
/*  40:    */ import javax.persistence.criteria.JoinType;
/*  41:    */ import javax.persistence.criteria.Path;
/*  42:    */ import javax.persistence.criteria.Root;
/*  43:    */ 
/*  44:    */ @Stateless
/*  45:    */ public class FacturaProveedorSRIDao
/*  46:    */   extends AbstractDaoAS2<FacturaProveedorSRI>
/*  47:    */ {
/*  48:    */   public FacturaProveedorSRIDao()
/*  49:    */   {
/*  50: 53 */     super(FacturaProveedorSRI.class);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<FacturaProveedorSRI> obtenerFacturasMes(int anio, int mes, int idOrganizacion)
/*  54:    */   {
/*  55: 67 */     Query query = this.em.createQuery("SELECT f FROM FacturaProveedorSRI f  LEFT JOIN FETCH f.creditoTributarioSRI c  LEFT JOIN FETCH f.tipoIdentificacion t  LEFT JOIN FETCH f.documento do  LEFT JOIN FETCH f.tipoComprobanteSRI tc  LEFT JOIN FETCH f.documentoModificado tcm  WHERE MONTH(f.fechaRegistro)=:mes  AND YEAR(f.fechaRegistro)=:anio AND f.idOrganizacion = :idOrganizacion AND f.estado!=:estadoAnulado");
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59: 71 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  60: 72 */     query.setParameter("mes", Integer.valueOf(mes));
/*  61: 73 */     query.setParameter("anio", Integer.valueOf(anio));
/*  62: 74 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  63:    */     
/*  64: 76 */     List<FacturaProveedorSRI> lista = query.getResultList();
/*  65: 78 */     for (FacturaProveedorSRI facturaProveedorSRI : lista) {
/*  66: 79 */       if (facturaProveedorSRI.getDocumentoModificado() != null) {
/*  67: 80 */         facturaProveedorSRI.getDocumentoModificado().getId();
/*  68:    */       }
/*  69:    */     }
/*  70: 85 */     return query.getResultList();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public FacturaProveedorSRI cargarDetalle(int idFacturaProveedorSRI)
/*  74:    */   {
/*  75: 96 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  76:    */     
/*  77:    */ 
/*  78: 99 */     CriteriaQuery<FacturaProveedorSRI> cqCabecera = criteriaBuilder.createQuery(FacturaProveedorSRI.class);
/*  79:100 */     Root<FacturaProveedorSRI> fromCabecera = cqCabecera.from(FacturaProveedorSRI.class);
/*  80:    */     
/*  81:102 */     Fetch<Object, Object> facturaProveedor = fromCabecera.fetch("facturaProveedor", JoinType.LEFT);
/*  82:103 */     Fetch<Object, Object> empresa = facturaProveedor.fetch("empresa", JoinType.LEFT);
/*  83:104 */     facturaProveedor.fetch("documento", JoinType.LEFT);
/*  84:105 */     Fetch<Object, Object> proveedor = empresa.fetch("proveedor", JoinType.LEFT);
/*  85:106 */     proveedor.fetch("categoriaRetencion", JoinType.LEFT);
/*  86:107 */     fromCabecera.fetch("creditoTributarioSRI", JoinType.LEFT);
/*  87:108 */     fromCabecera.fetch("documento", JoinType.LEFT);
/*  88:109 */     fromCabecera.fetch("tipoIdentificacion", JoinType.LEFT);
/*  89:110 */     fromCabecera.fetch("tipoComprobanteSRI", JoinType.LEFT);
/*  90:111 */     fromCabecera.fetch("pago", JoinType.LEFT);
/*  91:112 */     fromCabecera.fetch("compraCajaChica", JoinType.LEFT);
/*  92:    */     
/*  93:114 */     Path<Integer> pathId = fromCabecera.get("idFacturaProveedorSRI");
/*  94:115 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idFacturaProveedorSRI)));
/*  95:116 */     CriteriaQuery<FacturaProveedorSRI> selectCabecera = cqCabecera.select(fromCabecera);
/*  96:    */     
/*  97:118 */     FacturaProveedorSRI facturaProveedorSRI = (FacturaProveedorSRI)this.em.createQuery(selectCabecera).getSingleResult();
/*  98:    */     
/*  99:    */ 
/* 100:121 */     CriteriaQuery<DetalleFacturaProveedorSRI> cqDetalle = criteriaBuilder.createQuery(DetalleFacturaProveedorSRI.class);
/* 101:122 */     Root<DetalleFacturaProveedorSRI> fromDetalle = cqDetalle.from(DetalleFacturaProveedorSRI.class);
/* 102:    */     
/* 103:124 */     Fetch<Object, Object> conceptoRetencionSRI = fromDetalle.fetch("conceptoRetencionSRI", JoinType.LEFT);
/* 104:125 */     conceptoRetencionSRI.fetch("cuentaContable", JoinType.LEFT);
/* 105:    */     
/* 106:127 */     Path<Integer> pathIdDetalle = fromDetalle.join("facturaProveedorSRI").get("idFacturaProveedorSRI");
/* 107:128 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idFacturaProveedorSRI)));
/* 108:129 */     CriteriaQuery<DetalleFacturaProveedorSRI> selectDetalle = cqDetalle.select(fromDetalle);
/* 109:    */     
/* 110:131 */     List<DetalleFacturaProveedorSRI> listaDetalleFacturaProveedorSRI = this.em.createQuery(selectDetalle).getResultList();
/* 111:132 */     facturaProveedorSRI.setListaDetalleFacturaProveedorSRI(listaDetalleFacturaProveedorSRI);
/* 112:    */     
/* 113:134 */     return facturaProveedorSRI;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public FacturaProveedorSRI obtenerFacturaProveedorSRI(int idFacturaProveedor)
/* 117:    */   {
/* 118:146 */     Query query = this.em.createQuery("SELECT f FROM FacturaProveedorSRI f left join f.documento do where f.facturaProveedor.idFacturaProveedor=:idFacturaProveedor");
/* 119:147 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 120:148 */     FacturaProveedorSRI facturaProveedorSRI = (FacturaProveedorSRI)query.getSingleResult();
/* 121:149 */     facturaProveedorSRI.getListaDetalleFacturaProveedorSRI().size();
/* 122:    */     
/* 123:151 */     return facturaProveedorSRI;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List getReporteFacturaProveedorSRI(int idFacturaProveedorSRI)
/* 127:    */     throws ExcepcionAS2
/* 128:    */   {
/* 129:166 */     int cantidadRetencionesIVA = 0;
/* 130:    */     
/* 131:    */ 
/* 132:169 */     StringBuilder sql = new StringBuilder();
/* 133:170 */     sql.append(" SELECT f.nombreProveedor, f.direccionProveedor, f.identificacionProveedor, f.fechaEmisionRetencion, ");
/* 134:171 */     sql.append(" CONCAT(f.establecimiento, '-' , f.puntoEmision , '-' , f.numero), ");
/* 135:172 */     sql.append(" COALESCE(d.baseImponibleRetencion,0) + ");
/* 136:173 */     sql.append(" COALESCE(d.baseImponibleTarifaCero,0) + ");
/* 137:174 */     sql.append(" COALESCE(d.baseImponibleDiferenteCero,0) + ");
/* 138:175 */     sql.append(" COALESCE(d.baseImponibleNoObjetoIva,0), ");
/* 139:176 */     sql.append(" c.nombre, c.codigo, CASE WHEN c.tipoConceptoRetencion = :tipoConceptoIVA then 'IVA' WHEN c.tipoConceptoRetencion = :tipoConceptoFUENTE then 'FUENTE' WHEN c.tipoConceptoRetencion = :tipoConceptoISD then 'ISD' else '' end, ");
/* 140:177 */     sql.append(" d.porcentajeRetencion, d.valorRetencion, t.nombre, YEAR(f.fechaEmisionRetencion), f.telefonoProveedor, ");
/* 141:178 */     sql.append(" MONTH(f.fechaEmisionRetencion), DAY(f.fechaEmisionRetencion),f.ciudad, f.provincia,f.autorizacionRetencion ");
/* 142:    */     
/* 143:180 */     sql.append(" ,o.razonSocial, o.identificacion, f.fechaAutorizacion, f.claveAcceso, oc.numeroResolucionContribuyente,");
/* 144:181 */     sql.append(" oc.indicadorObligadoContabilidad, f.direccionMatriz, f.direccionSucursal, f.ambiente, f.tipoEmision,CONCAT(f.establecimientoRetencion, '-' , f.puntoEmisionRetencion , '-' , f.numeroRetencion),CASE WHEN fcp != null then (fcp.fecha) else (cch.fecha) end,f.email, ");
/* 145:182 */     sql.append(" f.fechaEmision, f.autorizacion ");
/* 146:    */     
/* 147:184 */     sql.append(" FROM DetalleFacturaProveedorSRI d ");
/* 148:185 */     sql.append(" INNER JOIN d.facturaProveedorSRI f, Organizacion o ");
/* 149:186 */     sql.append(" LEFT JOIN f.facturaProveedor fcp");
/* 150:187 */     sql.append(" LEFT JOIN f.compraCajaChica cch");
/* 151:188 */     sql.append(" JOIN o.organizacionConfiguracion oc");
/* 152:189 */     sql.append(" INNER JOIN d.conceptoRetencionSRI c ");
/* 153:190 */     sql.append(" INNER JOIN f.tipoComprobanteSRI t ");
/* 154:191 */     sql.append(" WHERE f.idFacturaProveedorSRI = :idFacturaProveedorSRI ");
/* 155:192 */     sql.append(" AND o.idOrganizacion = f.idOrganizacion");
/* 156:    */     
/* 157:    */ 
/* 158:195 */     Query query = this.em.createQuery(sql.toString());
/* 159:196 */     query.setParameter("idFacturaProveedorSRI", Integer.valueOf(idFacturaProveedorSRI));
/* 160:197 */     query.setParameter("tipoConceptoIVA", TipoConceptoRetencion.IVA);
/* 161:198 */     query.setParameter("tipoConceptoFUENTE", TipoConceptoRetencion.FUENTE);
/* 162:199 */     query.setParameter("tipoConceptoISD", TipoConceptoRetencion.ISD);
/* 163:200 */     List lista = query.getResultList();
/* 164:    */     
/* 165:202 */     return lista;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public BigDecimal getValorRetenidoPorCodigo(FacturaProveedorSRI facturaProveedorSRI, String codigo)
/* 169:    */   {
/* 170:273 */     StringBuilder sql = new StringBuilder();
/* 171:274 */     sql.append(" SELECT d.valorRetencion ");
/* 172:    */     
/* 173:276 */     sql.append(" FROM DetalleFacturaProveedorSRI d ");
/* 174:277 */     sql.append(" INNER JOIN d.facturaProveedorSRI f ");
/* 175:278 */     sql.append(" INNER JOIN d.conceptoRetencionSRI c ");
/* 176:279 */     sql.append(" WHERE f.idFacturaProveedorSRI = :idFacturaProveedorSRI ");
/* 177:280 */     sql.append(" AND c.codigo = :codigo ");
/* 178:    */     
/* 179:282 */     Query query = this.em.createQuery(sql.toString());
/* 180:283 */     query.setParameter("codigo", codigo);
/* 181:284 */     query.setParameter("idFacturaProveedorSRI", Integer.valueOf(facturaProveedorSRI.getIdFacturaProveedorSRI()));
/* 182:    */     try
/* 183:    */     {
/* 184:286 */       return (BigDecimal)query.getSingleResult();
/* 185:    */     }
/* 186:    */     catch (NoResultException e) {}
/* 187:288 */     return BigDecimal.ZERO;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public BigDecimal getValorRetenidoPorCodigoEnMemoria(FacturaProveedorSRI facturaProveedorSRI, String codigo)
/* 191:    */   {
/* 192:293 */     BigDecimal valorRetenido = BigDecimal.ZERO;
/* 193:295 */     for (DetalleFacturaProveedorSRI detalle : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI()) {
/* 194:296 */       if ((!detalle.isEliminado()) && (detalle.getConceptoRetencionSRI().getCodigo().equals(codigo))) {
/* 195:297 */         valorRetenido = valorRetenido.add(detalle.getValorRetencion());
/* 196:    */       }
/* 197:    */     }
/* 198:301 */     return valorRetenido;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void actualizarEstado(Integer idFacturaProveedorSRI, Estado estado)
/* 202:    */   {
/* 203:311 */     Query query = this.em.createQuery("UPDATE FacturaProveedorSRI f SET f.estado=:estado WHERE f.idFacturaProveedorSRI=:idFacturaProveedorSRI");
/* 204:312 */     query.setParameter("idFacturaProveedorSRI", idFacturaProveedorSRI);
/* 205:313 */     query.setParameter("estado", estado);
/* 206:314 */     query.executeUpdate();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void eliminarFacturaProveedorSRI(Integer idFacturaProveedorSRI)
/* 210:    */   {
/* 211:324 */     String sql3 = " DELETE FROM FacturaClienteSRI f WHERE f.facturaProveedorSRI.idFacturaProveedorSRI=:idFacturaProveedorSRI";
/* 212:325 */     Query query3 = this.em.createQuery(sql3);
/* 213:326 */     query3.setParameter("idFacturaProveedorSRI", idFacturaProveedorSRI);
/* 214:327 */     query3.executeUpdate();
/* 215:    */     
/* 216:329 */     String sql = "DELETE FROM DetalleFacturaProveedorSRI f WHERE f.facturaProveedorSRI.idFacturaProveedorSRI = :idFacturaProveedorSRI ";
/* 217:330 */     Query query = this.em.createQuery(sql);
/* 218:331 */     query.setParameter("idFacturaProveedorSRI", idFacturaProveedorSRI);
/* 219:332 */     query.executeUpdate();
/* 220:    */     
/* 221:334 */     sql = "DELETE FROM ReembolsoGastos f WHERE f.facturaProveedorSRI.idFacturaProveedorSRI = :idFacturaProveedorSRI ";
/* 222:335 */     query = this.em.createQuery(sql);
/* 223:336 */     query.setParameter("idFacturaProveedorSRI", idFacturaProveedorSRI);
/* 224:337 */     query.executeUpdate();
/* 225:    */     
/* 226:339 */     String sql2 = "DELETE FROM FacturaProveedorSRI f WHERE f.idFacturaProveedorSRI = :idFacturaProveedorSRI ";
/* 227:340 */     Query query2 = this.em.createQuery(sql2);
/* 228:341 */     query2.setParameter("idFacturaProveedorSRI", idFacturaProveedorSRI);
/* 229:342 */     query2.executeUpdate();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean existeFactura(FacturaProveedorSRI facturaProveedorSRI)
/* 233:    */   {
/* 234:    */     try
/* 235:    */     {
/* 236:356 */       Query query = this.em.createQuery("Select f from FacturaProveedorSRI f  WHERE f.establecimiento=:establecimiento AND f.puntoEmision=:puntoEmision AND f.numero=:numero AND f.autorizacion=:autorizacion AND f.identificacionProveedor=:identificacionProveedor AND f.idFacturaProveedorSRI!=:idFacturaProveedorSRI AND f.facturaProveedor.estado!=:estadoAnulado AND f.tipoComprobanteSRI.idTipoComprobanteSRI=:idTipoComprobanteSRI");
/* 237:    */       
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:361 */       query.setParameter("establecimiento", facturaProveedorSRI.getEstablecimiento());
/* 242:362 */       query.setParameter("puntoEmision", facturaProveedorSRI.getPuntoEmision());
/* 243:363 */       query.setParameter("numero", facturaProveedorSRI.getNumero());
/* 244:364 */       query.setParameter("autorizacion", facturaProveedorSRI.getAutorizacion());
/* 245:365 */       query.setParameter("identificacionProveedor", facturaProveedorSRI.getIdentificacionProveedor());
/* 246:366 */       query.setParameter("idFacturaProveedorSRI", Integer.valueOf(facturaProveedorSRI.getId()));
/* 247:367 */       query.setParameter("idTipoComprobanteSRI", Integer.valueOf(facturaProveedorSRI.getTipoComprobanteSRI().getId()));
/* 248:368 */       query.setParameter("estadoAnulado", Estado.ANULADO);
/* 249:    */       
/* 250:370 */       query.getSingleResult();
/* 251:    */       
/* 252:372 */       return true;
/* 253:    */     }
/* 254:    */     catch (NoResultException e) {}
/* 255:374 */     return false;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void actualizarFechaDeRegistroPorCajaChica(CajaChica cajaChica)
/* 259:    */   {
/* 260:384 */     Query query = this.em.createQuery("UPDATE FacturaProveedorSRI fp1 SET fp1.fechaRegistro=:fechaRegistro  WHERE fp1.idFacturaProveedorSRI IN ( \tSELECT fp2.idFacturaProveedorSRI FROM FacturaProveedorSRI fp2  \tINNER JOIN fp2.compraCajaChica co WHERE co.cajaChica.idCajaChica=:idCajaChica) ");
/* 261:    */     
/* 262:    */ 
/* 263:387 */     query.setParameter("idCajaChica", Integer.valueOf(cajaChica.getId()));
/* 264:388 */     query.setParameter("fechaRegistro", cajaChica.getFechaContabilizacion());
/* 265:    */     
/* 266:390 */     query.executeUpdate();
/* 267:    */   }
/* 268:    */   
/* 269:    */   public List<FacturaProveedorSRI> obtenerFacturasPorEgresoPago(int idAsiento)
/* 270:    */   {
/* 271:395 */     Query query = this.em.createQuery("SELECT fps FROM DetallePago dp  JOIN dp.pago p JOIN dp.cuentaPorPagar cp JOIN cp.facturaProveedor fp JOIN fp.facturaProveedorSRI fps WHERE p.asiento.idAsiento =:idAsiento ");
/* 272:    */     
/* 273:397 */     query.setParameter("idAsiento", Integer.valueOf(idAsiento));
/* 274:398 */     List<FacturaProveedorSRI> lista = query.getResultList();
/* 275:399 */     if (lista.size() == 0) {
/* 276:400 */       return new ArrayList();
/* 277:    */     }
/* 278:402 */     return lista;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public FacturaProveedorSRI buscarFacturaProveedorSRI(int establecimiento, int punto, int numeroFactura, int idOrganizacion)
/* 282:    */     throws ExcepcionAS2
/* 283:    */   {
/* 284:    */     try
/* 285:    */     {
/* 286:410 */       StringBuilder sql = new StringBuilder();
/* 287:411 */       sql.append(" SELECT fps  FROM FacturaProveedorSRI fps ");
/* 288:412 */       sql.append(" JOIN FETCH fps.facturaProveedor fp ");
/* 289:413 */       sql.append(" JOIN FETCH fps.documento do ");
/* 290:414 */       sql.append(" WHERE (CAST fps.establecimiento AS integer)= :establecimiento ");
/* 291:415 */       sql.append(" AND (CAST fps.puntoEmision AS integer) = :punto ");
/* 292:416 */       sql.append(" AND (CAST fps.numero AS integer) = :numeroFactura ");
/* 293:417 */       sql.append(" AND fps.idOrganizacion = :idOrganizacion ");
/* 294:    */       
/* 295:419 */       Query query = this.em.createQuery(sql.toString());
/* 296:420 */       query.setParameter("establecimiento", Integer.valueOf(establecimiento));
/* 297:421 */       query.setParameter("punto", Integer.valueOf(punto));
/* 298:422 */       query.setParameter("numeroFactura", Integer.valueOf(numeroFactura));
/* 299:423 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 300:    */       
/* 301:425 */       return (FacturaProveedorSRI)query.getSingleResult();
/* 302:    */     }
/* 303:    */     catch (NoResultException e)
/* 304:    */     {
/* 305:428 */       throw new ExcepcionAS2("msg_error_numero_factura", " " + establecimiento + "-" + "-" + punto + "-" + numeroFactura);
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void verificarExitenciaRetencion(MovimientoBancario movimientoBancario)
/* 310:    */     throws ExcepcionAS2
/* 311:    */   {
/* 312:436 */     StringBuilder sql = new StringBuilder();
/* 313:437 */     sql.append(" SELECT mb FROM MovimientoBancario mb ");
/* 314:438 */     sql.append(" JOIN FETCH mb.cuentaBancariaOrganizacion cbo ");
/* 315:439 */     sql.append(" JOIN FETCH cbo.cuentaBancaria cb ");
/* 316:440 */     sql.append(" JOIN FETCH mb.formaPago fp ");
/* 317:441 */     sql.append(" WHERE mb.documentoReferencia =:documentoReferencia ");
/* 318:442 */     sql.append(" AND cbo.idCuentaBancariaOrganizacion=:idCuentaBancariaOrganizacion ");
/* 319:443 */     sql.append(" AND fp.idFormaPago=:idFormaPago ");
/* 320:444 */     sql.append(" AND mb.estado!=:estadoAnulado");
/* 321:445 */     sql.append(" AND mb.idOrganizacion=:idOrganizacion ");
/* 322:446 */     sql.append(" AND mb.idMovimientoBancario!=:idMovimientoBancario ");
/* 323:    */     
/* 324:448 */     Query query = this.em.createQuery(sql.toString());
/* 325:449 */     query.setParameter("documentoReferencia", movimientoBancario.getDocumentoReferencia());
/* 326:450 */     query.setParameter("idCuentaBancariaOrganizacion", Integer.valueOf(movimientoBancario.getCuentaBancariaOrganizacion().getId()));
/* 327:451 */     query.setParameter("idFormaPago", Integer.valueOf(movimientoBancario.getFormaPago().getId()));
/* 328:452 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 329:453 */     query.setParameter("idOrganizacion", Integer.valueOf(movimientoBancario.getIdOrganizacion()));
/* 330:454 */     query.setParameter("idMovimientoBancario", Integer.valueOf(movimientoBancario.getIdMovimientoBancario()));
/* 331:    */     
/* 332:456 */     List<MovimientoBancario> lista = query.getResultList();
/* 333:458 */     if (!lista.isEmpty())
/* 334:    */     {
/* 335:459 */       MovimientoBancario mb = (MovimientoBancario)lista.get(0);
/* 336:    */       
/* 337:461 */       throw new ExcepcionAS2("msg_error_existe_documento_referencia", " Doc. " + mb.getDocumentoReferencia() + " #CB. " + mb.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero() + " Fp. " + mb.getFormaPago().getNombre());
/* 338:    */     }
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void verificarExitenciaRetencion(FacturaProveedorSRI facturaProveedorSRI)
/* 342:    */     throws ExcepcionAS2Financiero
/* 343:    */   {
/* 344:468 */     int numeroRetencion = Integer.parseInt(facturaProveedorSRI.getNumeroRetencion());
/* 345:469 */     StringBuilder sql = new StringBuilder();
/* 346:470 */     sql.append(" SELECT fs FROM FacturaProveedorSRI fs ");
/* 347:    */     
/* 348:472 */     sql.append(" WHERE fs.idOrganizacion = :idOrganizacion ");
/* 349:473 */     sql.append(" AND fs.establecimientoRetencion     = :establecimientoRetencion ");
/* 350:474 */     sql.append(" AND fs.puntoEmisionRetencion        = :puntoEmisionRetencion ");
/* 351:475 */     sql.append(" AND cast(fs.numeroRetencion as int) = :numeroRetencion ");
/* 352:    */     
/* 353:    */ 
/* 354:478 */     sql.append(" AND fs.idFacturaProveedorSRI != :idFacturaProveedorSRI ");
/* 355:    */     
/* 356:480 */     Query query = this.em.createQuery(sql.toString());
/* 357:481 */     query.setParameter("establecimientoRetencion", facturaProveedorSRI.getEstablecimientoRetencion());
/* 358:482 */     query.setParameter("puntoEmisionRetencion", facturaProveedorSRI.getPuntoEmisionRetencion());
/* 359:483 */     query.setParameter("numeroRetencion", Integer.valueOf(numeroRetencion));
/* 360:    */     
/* 361:    */ 
/* 362:486 */     query.setParameter("idOrganizacion", Integer.valueOf(facturaProveedorSRI.getIdOrganizacion()));
/* 363:487 */     query.setParameter("idFacturaProveedorSRI", Integer.valueOf(facturaProveedorSRI.getIdFacturaProveedorSRI()));
/* 364:    */     
/* 365:489 */     List<FacturaProveedorSRI> lista = query.getResultList();
/* 366:492 */     if (lista.size() > 0)
/* 367:    */     {
/* 368:493 */       FacturaProveedorSRI factura = (FacturaProveedorSRI)lista.get(0);
/* 369:    */       
/* 370:495 */       throw new ExcepcionAS2Financiero("msgs_error_existe_retencion", " Fac. " + factura.getEstablecimiento() + " - " + factura.getPuntoEmision() + " - " + factura.getNumero());
/* 371:    */     }
/* 372:    */   }
/* 373:    */   
/* 374:    */   public List<ReembolsoGastos> listaReembolsoGastos(FacturaProveedorSRI facturaProveedorSRI)
/* 375:    */   {
/* 376:502 */     StringBuilder sql = new StringBuilder();
/* 377:503 */     sql.append(" SELECT rg FROM ReembolsoGastos     rg ");
/* 378:504 */     sql.append(" JOIN FETCH rg.tipoIdentificacion   ti ");
/* 379:505 */     sql.append(" JOIN FETCH rg.tipoComprobanteSRI   tcsri ");
/* 380:506 */     sql.append(" INNER JOIN rg.facturaProveedorSRI  fpSRI ");
/* 381:507 */     sql.append(" WHERE fpSRI = :facturaProveedorSRI ");
/* 382:    */     
/* 383:509 */     Query query = this.em.createQuery(sql.toString());
/* 384:510 */     query.setParameter("facturaProveedorSRI", facturaProveedorSRI);
/* 385:    */     
/* 386:512 */     return query.getResultList();
/* 387:    */   }
/* 388:    */   
/* 389:    */   public boolean existeRetencion(FacturaProveedorSRI facturaProveedorSRI)
/* 390:    */   {
/* 391:    */     try
/* 392:    */     {
/* 393:519 */       StringBuilder sql = new StringBuilder();
/* 394:520 */       sql.append(" Select f from FacturaProveedorSRI f ");
/* 395:521 */       sql.append(" INNER JOIN f.documento doc ");
/* 396:522 */       sql.append(" WHERE f.establecimientoRetencion = :establecimiento ");
/* 397:523 */       sql.append(" AND f.puntoEmisionRetencion = :puntoEmision ");
/* 398:524 */       sql.append(" AND f.numeroRetencion = :numero ");
/* 399:525 */       sql.append(" AND f.idOrganizacion = :idOrganizacion ");
/* 400:526 */       if ((facturaProveedorSRI.getDocumento() != null) && (!facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico())) {
/* 401:527 */         sql.append(" AND f.autorizacionRetencion=:autorizacion ");
/* 402:    */       } else {
/* 403:529 */         sql.append(" AND doc.indicadorDocumentoElectronico = true ");
/* 404:    */       }
/* 405:531 */       sql.append(" AND f.idFacturaProveedorSRI != :idFacturaProveedorSRI ");
/* 406:532 */       if (facturaProveedorSRI.getFacturaProveedor() != null) {
/* 407:533 */         sql.append(" AND f.facturaProveedor.estado != :estadoAnulado ");
/* 408:535 */       } else if (facturaProveedorSRI.getCompraCajaChica() != null) {
/* 409:536 */         sql.append(" AND f.compraCajaChica.estado != :estadoAnulado ");
/* 410:    */       }
/* 411:539 */       Query query = this.em.createQuery(sql.toString());
/* 412:540 */       query.setParameter("establecimiento", facturaProveedorSRI.getEstablecimientoRetencion());
/* 413:541 */       query.setParameter("puntoEmision", facturaProveedorSRI.getPuntoEmisionRetencion());
/* 414:542 */       query.setParameter("numero", facturaProveedorSRI.getNumeroRetencion());
/* 415:543 */       if ((facturaProveedorSRI.getDocumento() != null) && (!facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico())) {
/* 416:544 */         query.setParameter("autorizacion", facturaProveedorSRI.getAutorizacionRetencion());
/* 417:    */       }
/* 418:546 */       query.setParameter("idFacturaProveedorSRI", Integer.valueOf(facturaProveedorSRI.getId()));
/* 419:547 */       if (facturaProveedorSRI.getFacturaProveedor() != null) {
/* 420:548 */         query.setParameter("estadoAnulado", Estado.ANULADO);
/* 421:550 */       } else if (facturaProveedorSRI.getCompraCajaChica() != null) {
/* 422:551 */         query.setParameter("estadoAnulado", Estado.ANULADO);
/* 423:    */       }
/* 424:553 */       query.setParameter("idOrganizacion", Integer.valueOf(facturaProveedorSRI.getId()));
/* 425:    */       
/* 426:555 */       query.getSingleResult();
/* 427:    */       
/* 428:557 */       return true;
/* 429:    */     }
/* 430:    */     catch (NoResultException e) {}
/* 431:559 */     return false;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public FacturaProveedorSRI buscarPorNumero(String numero)
/* 435:    */   {
/* 436:    */     try
/* 437:    */     {
/* 438:566 */       StringBuilder sql = new StringBuilder();
/* 439:567 */       sql.append(" SELECT fp FROM FacturaProveedorSRI fp ");
/* 440:568 */       sql.append(" WHERE fp.numero = :numero ");
/* 441:    */       
/* 442:570 */       Query query = this.em.createQuery(sql.toString());
/* 443:571 */       query.setParameter("numero", numero.trim());
/* 444:572 */       return (FacturaProveedorSRI)query.getSingleResult();
/* 445:    */     }
/* 446:    */     catch (NoResultException e)
/* 447:    */     {
/* 448:575 */       System.out.println(e);
/* 449:    */     }
/* 450:576 */     return null;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public CreditoTributarioSRI getCreditoTributarioSRI(FacturaProveedor fp)
/* 454:    */   {
/* 455:583 */     StringBuilder sql = new StringBuilder();
/* 456:584 */     sql.append(" SELECT p.idProducto FROM DetalleFacturaProveedor dfp");
/* 457:585 */     sql.append(" JOIN dfp.producto p");
/* 458:586 */     sql.append(" WHERE dfp.facturaProveedor = :facturaProveedor");
/* 459:587 */     sql.append(" AND p.creditoTributarioSRI IS NOT NULL");
/* 460:588 */     sql.append(" GROUP BY p.idProducto");
/* 461:589 */     sql.append(" ORDER BY sum(dfp.cantidad*(dfp.precio-dfp.descuento)) DESC");
/* 462:590 */     Query q1 = this.em.createQuery(sql.toString());
/* 463:591 */     q1.setParameter("facturaProveedor", fp);
/* 464:592 */     q1.setMaxResults(1);
/* 465:593 */     List<Integer> lproducto = q1.getResultList();
/* 466:595 */     if (!lproducto.isEmpty())
/* 467:    */     {
/* 468:596 */       Integer idProducto = (Integer)lproducto.get(0);
/* 469:597 */       sql = new StringBuilder();
/* 470:598 */       sql.append(" SELECT ct FROM Producto p");
/* 471:599 */       sql.append(" JOIN p.creditoTributarioSRI ct");
/* 472:600 */       sql.append(" WHERE p.idProducto = :idProducto");
/* 473:    */       
/* 474:602 */       Query q2 = this.em.createQuery(sql.toString());
/* 475:603 */       q2.setParameter("idProducto", idProducto);
/* 476:    */       
/* 477:605 */       return (CreditoTributarioSRI)q2.getSingleResult();
/* 478:    */     }
/* 479:607 */     return null;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public List<Object[]> getMontoBasePorTipoProducto(FacturaProveedor fp)
/* 483:    */   {
/* 484:614 */     StringBuilder sql = new StringBuilder();
/* 485:615 */     sql.append(" SELECT p.tipoProducto, sum(dfp.cantidad*(dfp.precio-dfp.descuento)), 1, '' ");
/* 486:616 */     sql.append(" FROM DetalleFacturaProveedor dfp");
/* 487:617 */     sql.append(" JOIN dfp.producto p");
/* 488:618 */     sql.append(" WHERE dfp.facturaProveedor = :facturaProveedor");
/* 489:619 */     sql.append(" GROUP BY p.tipoProducto");
/* 490:620 */     sql.append(" HAVING sum(dfp.cantidad*(dfp.precio-dfp.descuento)) > 0");
/* 491:621 */     sql.append(" ORDER BY p.tipoProducto");
/* 492:622 */     Query query = this.em.createQuery(sql.toString());
/* 493:623 */     query.setParameter("facturaProveedor", fp);
/* 494:    */     
/* 495:625 */     List<Object[]> lista1 = query.getResultList();
/* 496:626 */     for (Iterator localIterator = lista1.iterator(); localIterator.hasNext();)
/* 497:    */     {
/* 498:626 */       dato = (Object[])localIterator.next();
/* 499:627 */       dato[2] = TipoConceptoRetencion.FUENTE;
/* 500:    */     }
/* 501:    */     Object[] dato;
/* 502:631 */     sql = new StringBuilder();
/* 503:632 */     sql.append(" SELECT p.tipoProducto, sum(dfp.cantidad*((dfp.precio-dfp.descuento)*(coalesce(ipfp.porcentajeImpuesto,0)/100))), 0, '' ");
/* 504:633 */     sql.append(" FROM ImpuestoProductoFacturaProveedor ipfp");
/* 505:634 */     sql.append(" JOIN ipfp.detalleFacturaProveedor dfp");
/* 506:635 */     sql.append(" JOIN dfp.producto p");
/* 507:636 */     sql.append(" WHERE dfp.facturaProveedor = :facturaProveedor");
/* 508:637 */     sql.append(" GROUP BY p.tipoProducto");
/* 509:638 */     sql.append(" HAVING sum(dfp.cantidad*((dfp.precio-dfp.descuento)*(coalesce(ipfp.porcentajeImpuesto,0)/100))) > 0");
/* 510:639 */     sql.append(" ORDER BY p.tipoProducto");
/* 511:640 */     query = this.em.createQuery(sql.toString());
/* 512:641 */     query.setParameter("facturaProveedor", fp);
/* 513:    */     
/* 514:643 */     Object lista2 = query.getResultList();
/* 515:644 */     for (Object[] dato : (List)lista2) {
/* 516:645 */       dato[2] = TipoConceptoRetencion.IVA;
/* 517:    */     }
/* 518:648 */     lista1.addAll((Collection)lista2);
/* 519:    */     
/* 520:650 */     return lista1;
/* 521:    */   }
/* 522:    */   
/* 523:    */   public void actualizaFacturaProveedorSRI(int idFacturaProveedorSRI, Estado estadoFactura, EstadoDocumentoElectronico estadoSRI, Date fechaAutorizacion, String numeroAutorizacion, String mensajeSRI)
/* 524:    */   {
/* 525:656 */     String set = "SET ";
/* 526:657 */     StringBuilder sql1 = new StringBuilder();
/* 527:658 */     sql1.append("UPDATE FacturaProveedorSRI fc ");
/* 528:659 */     if (estadoFactura != null)
/* 529:    */     {
/* 530:660 */       sql1.append(set + " fc.estado = :estado ");
/* 531:661 */       set = ", ";
/* 532:662 */       if ((estadoFactura.equals(Estado.PROCESADO)) || (estadoFactura.equals(Estado.APROBADO)) || (estadoFactura.equals(Estado.CONTABILIZADO))) {
/* 533:663 */         sql1.append(set + " fc.indicadorRetencionEmitida = TRUE ");
/* 534:    */       }
/* 535:    */     }
/* 536:670 */     if (fechaAutorizacion != null)
/* 537:    */     {
/* 538:671 */       sql1.append(set + " fc.fechaAutorizacion = :fechaAutorizacion ");
/* 539:672 */       set = ", ";
/* 540:    */     }
/* 541:674 */     if (numeroAutorizacion != null)
/* 542:    */     {
/* 543:675 */       sql1.append(set + " fc.autorizacionRetencion = :numeroAutorizacion ");
/* 544:676 */       set = ", ";
/* 545:    */     }
/* 546:678 */     if (estadoSRI != null)
/* 547:    */     {
/* 548:679 */       sql1.append(set + " fc.estadoDocumentoElectronico = :estadoSRI ");
/* 549:680 */       set = ", ";
/* 550:    */     }
/* 551:682 */     if (mensajeSRI != null) {
/* 552:683 */       sql1.append(set + " fc.mensajeSRI = :mensajeSRI ");
/* 553:    */     }
/* 554:685 */     sql1.append(" WHERE fc.idFacturaProveedorSRI = :idFacturaProveedorSRI ");
/* 555:    */     
/* 556:687 */     Query query1 = this.em.createQuery(sql1.toString());
/* 557:688 */     query1.setParameter("idFacturaProveedorSRI", Integer.valueOf(idFacturaProveedorSRI));
/* 558:689 */     if (estadoFactura != null) {
/* 559:690 */       query1.setParameter("estado", estadoFactura);
/* 560:    */     }
/* 561:692 */     if (fechaAutorizacion != null) {
/* 562:693 */       query1.setParameter("fechaAutorizacion", fechaAutorizacion);
/* 563:    */     }
/* 564:695 */     if (numeroAutorizacion != null) {
/* 565:696 */       query1.setParameter("numeroAutorizacion", numeroAutorizacion);
/* 566:    */     }
/* 567:698 */     if (estadoSRI != null) {
/* 568:699 */       query1.setParameter("estadoSRI", estadoSRI);
/* 569:    */     }
/* 570:701 */     if (mensajeSRI != null) {
/* 571:702 */       query1.setParameter("mensajeSRI", mensajeSRI);
/* 572:    */     }
/* 573:704 */     query1.executeUpdate();
/* 574:    */   }
/* 575:    */   
/* 576:    */   public List<FacturaProveedorSRI> obtenerRetencionesProveedor(int idOrganizacion, Date fechaDesde, Date fechaHasta, DocumentoBase documentoBase, int idEmpresa)
/* 577:    */   {
/* 578:711 */     StringBuilder sql = new StringBuilder();
/* 579:712 */     sql.append(" SELECT fcs from FacturaProveedorSRI fcs");
/* 580:713 */     sql.append(" JOIN FETCH fcs.documento d ");
/* 581:714 */     sql.append(" JOIN FETCH fcs.facturaProveedor fp ");
/* 582:715 */     sql.append(" JOIN FETCH fp.empresa e");
/* 583:716 */     sql.append(" WHERE fp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 584:717 */     sql.append(" AND d.documentoBase = :documentoBase");
/* 585:718 */     sql.append(" AND fcs.estado <> :estadoAnulado AND fcs.estado <> :estadoEspera AND fcs.estado <> :estadoContingencia");
/* 586:719 */     sql.append(" AND fp.estado <> :estadoAnulado");
/* 587:720 */     sql.append(" AND d.indicadorDocumentoElectronico = TRUE");
/* 588:721 */     sql.append(" AND (e.idEmpresa = :idEmpresa OR :idEmpresa=0) ");
/* 589:    */     
/* 590:723 */     Query query = this.em.createQuery(sql.toString());
/* 591:    */     
/* 592:725 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 593:726 */     query.setParameter("estadoEspera", Estado.EN_ESPERA);
/* 594:727 */     query.setParameter("estadoContingencia", Estado.EN_ESPERA_CONTINGENCIA);
/* 595:    */     
/* 596:729 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 597:730 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 598:731 */     query.setParameter("documentoBase", documentoBase);
/* 599:    */     
/* 600:733 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 601:734 */     return query.getResultList();
/* 602:    */   }
/* 603:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.FacturaProveedorSRIDao
 * JD-Core Version:    0.7.0.1
 */