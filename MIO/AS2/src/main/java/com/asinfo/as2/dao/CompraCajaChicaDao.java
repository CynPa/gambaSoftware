/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CajaChica;
/*   4:    */ import com.asinfo.as2.entities.CompraCajaChica;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleCompraCajaChica;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  12:    */ import com.asinfo.as2.entities.Ubicacion;
/*  13:    */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*  14:    */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*  15:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  16:    */ import com.asinfo.as2.entities.sri.ReembolsoGastos;
/*  17:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  18:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  19:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ import javax.persistence.EntityManager;
/*  25:    */ import javax.persistence.Query;
/*  26:    */ import javax.persistence.TypedQuery;
/*  27:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  28:    */ import javax.persistence.criteria.CriteriaQuery;
/*  29:    */ import javax.persistence.criteria.Expression;
/*  30:    */ import javax.persistence.criteria.Fetch;
/*  31:    */ import javax.persistence.criteria.JoinType;
/*  32:    */ import javax.persistence.criteria.Predicate;
/*  33:    */ import javax.persistence.criteria.Root;
/*  34:    */ 
/*  35:    */ @Stateless
/*  36:    */ public class CompraCajaChicaDao
/*  37:    */   extends AbstractDaoAS2<CompraCajaChica>
/*  38:    */ {
/*  39:    */   public CompraCajaChicaDao()
/*  40:    */   {
/*  41: 47 */     super(CompraCajaChica.class);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public CompraCajaChica cargarDetalle(int idCompraCajaChica)
/*  45:    */   {
/*  46: 52 */     CompraCajaChica compraCajaChica = (CompraCajaChica)buscarPorId(Integer.valueOf(idCompraCajaChica));
/*  47:    */     
/*  48: 54 */     compraCajaChica.getListaDetalleCompraCajaChica().size();
/*  49: 55 */     for (DetalleCompraCajaChica detalleCompraCajaChica : compraCajaChica.getListaDetalleCompraCajaChica())
/*  50:    */     {
/*  51: 56 */       detalleCompraCajaChica.getListaDetalleCompraCajaChicaCentroCosto().size();
/*  52: 57 */       detalleCompraCajaChica.getCuentaContable().getId();
/*  53: 58 */       detalleCompraCajaChica.getCuentaContable().setCodigoCuentaTransient(detalleCompraCajaChica.getCuentaContable().getCodigo());
/*  54: 59 */       if (detalleCompraCajaChica.getDimensionContable1() != null) {
/*  55: 60 */         detalleCompraCajaChica.getDimensionContable1().getIdDimensionContable();
/*  56:    */       }
/*  57: 62 */       if (detalleCompraCajaChica.getDimensionContable2() != null) {
/*  58: 63 */         detalleCompraCajaChica.getDimensionContable2().getIdDimensionContable();
/*  59:    */       }
/*  60: 65 */       if (detalleCompraCajaChica.getDimensionContable3() != null) {
/*  61: 66 */         detalleCompraCajaChica.getDimensionContable3().getIdDimensionContable();
/*  62:    */       }
/*  63: 68 */       if (detalleCompraCajaChica.getDimensionContable4() != null) {
/*  64: 69 */         detalleCompraCajaChica.getDimensionContable4().getIdDimensionContable();
/*  65:    */       }
/*  66: 71 */       if (detalleCompraCajaChica.getDimensionContable5() != null) {
/*  67: 72 */         detalleCompraCajaChica.getDimensionContable5().getIdDimensionContable();
/*  68:    */       }
/*  69:    */     }
/*  70: 76 */     if (compraCajaChica.getFacturaProveedorSRI() != null)
/*  71:    */     {
/*  72: 77 */       compraCajaChica.getCajaChica().getId();
/*  73: 78 */       compraCajaChica.getEmpresa().getId();
/*  74: 79 */       compraCajaChica.getDireccionEmpresa().getId();
/*  75: 80 */       compraCajaChica.getDireccionEmpresa().getUbicacion().getId();
/*  76: 81 */       compraCajaChica.getFacturaProveedorSRI().getId();
/*  77: 82 */       compraCajaChica.getFacturaProveedorSRI().getTipoComprobanteSRI().getId();
/*  78: 83 */       compraCajaChica.getFacturaProveedorSRI().getListaDetalleFacturaProveedorSRI().size();
/*  79: 84 */       if (compraCajaChica.getFacturaProveedorSRI().getDocumento() != null) {
/*  80: 85 */         compraCajaChica.getFacturaProveedorSRI().getDocumento().getId();
/*  81:    */       }
/*  82: 88 */       for (DetalleFacturaProveedorSRI dfpSRI : compraCajaChica.getFacturaProveedorSRI().getListaDetalleFacturaProveedorSRINoEliminados())
/*  83:    */       {
/*  84: 89 */         dfpSRI.getId();
/*  85: 90 */         dfpSRI.getConceptoRetencionSRI().getId();
/*  86:    */       }
/*  87: 93 */       if ((compraCajaChica.getFacturaProveedorSRI().getListaReembolsoGastos() != null) && 
/*  88: 94 */         (compraCajaChica.getFacturaProveedorSRI().getListaReembolsoGastos().size() > 0)) {
/*  89: 95 */         for (ReembolsoGastos rg : compraCajaChica.getFacturaProveedorSRI().getListaReembolsoGastos()) {
/*  90: 96 */           if (!rg.isEliminado())
/*  91:    */           {
/*  92: 97 */             rg.getIdReembolsoGastos();
/*  93: 98 */             if (rg.getTipoIdentificacion() != null) {
/*  94: 99 */               rg.getTipoIdentificacion().getIdTipoIdentificacion();
/*  95:    */             }
/*  96:101 */             if (rg.getTipoComprobanteSRI() != null) {
/*  97:102 */               rg.getTipoComprobanteSRI().getIdTipoComprobanteSRI();
/*  98:    */             }
/*  99:104 */             if (rg.getTipoComprobanteSRI() != null) {
/* 100:105 */               rg.getTipoComprobanteSRI().getIdTipoComprobanteSRI();
/* 101:    */             }
/* 102:    */           }
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:112 */     return compraCajaChica;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<CompraCajaChica> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 110:    */   {
/* 111:123 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 112:124 */     CriteriaQuery<CompraCajaChica> criteriaQuery = criteriaBuilder.createQuery(CompraCajaChica.class);
/* 113:125 */     Root<CompraCajaChica> from = criteriaQuery.from(CompraCajaChica.class);
/* 114:    */     
/* 115:127 */     from.fetch("facturaProveedorSRI", JoinType.LEFT).fetch("documento", JoinType.LEFT);
/* 116:128 */     from.fetch("cajaChica", JoinType.LEFT);
/* 117:129 */     from.fetch("empresa", JoinType.LEFT);
/* 118:    */     
/* 119:131 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 120:132 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 121:    */     
/* 122:134 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 123:    */     
/* 124:136 */     CriteriaQuery<CompraCajaChica> select = criteriaQuery.select(from);
/* 125:    */     
/* 126:138 */     TypedQuery<CompraCajaChica> typedQuery = this.em.createQuery(select);
/* 127:139 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 128:    */     
/* 129:141 */     return typedQuery.getResultList();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public CompraCajaChica cargarInformacionSRI(int idCompraCajaChica)
/* 133:    */   {
/* 134:151 */     CompraCajaChica compraCajaChica = (CompraCajaChica)buscarPorId(Integer.valueOf(idCompraCajaChica));
/* 135:152 */     compraCajaChica.getFacturaProveedorSRI().getId();
/* 136:153 */     compraCajaChica.getFacturaProveedorSRI().getTipoComprobanteSRI().getId();
/* 137:154 */     compraCajaChica.getFacturaProveedorSRI().getListaDetalleFacturaProveedorSRI().size();
/* 138:155 */     return compraCajaChica;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List getListaReporteCompraCajaChica(Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, int idCajaChica, int tipoCompra, int idOrganizacion, boolean fechaContabilizacion)
/* 142:    */     throws ExcepcionAS2Ventas
/* 143:    */   {
/* 144:172 */     StringBuilder sql = new StringBuilder();
/* 145:    */     
/* 146:174 */     sql.append(" SELECT cc.codigo, cc.nombre, f.documentoReferencia, f.fecha, f.indicadorFactura, e.nombreFiscal, ");
/* 147:175 */     sql.append(" CASE WHEN p.montoIva IS NULL THEN f.valor ELSE  (f.valor-p.montoIva) END , ");
/* 148:176 */     sql.append(" f.valor*0, ");
/* 149:177 */     sql.append(" CASE WHEN p.montoIva IS NULL THEN (f.valor*0) ELSE p.montoIva END , ");
/* 150:178 */     sql.append(" f.descripcion, e.codigo, ce.nombre, e.identificacion, ");
/* 151:179 */     sql.append("  '', p.fechaEmision, p.autorizacion , p.autorizacionRetencion, p.baseImponibleTarifaCero,");
/* 152:180 */     sql.append("  p.baseImponibleDiferenteCero, p.claveAcceso, f.descuentoImpuesto  ");
/* 153:181 */     sql.append(" FROM CompraCajaChica f ");
/* 154:182 */     sql.append(" LEFT JOIN  f.empresa e ");
/* 155:183 */     sql.append(" LEFT JOIN  e.categoriaEmpresa ce ");
/* 156:184 */     sql.append(" LEFT JOIN f.facturaProveedorSRI p ");
/* 157:185 */     sql.append(" LEFT JOIN f.cajaChica cc ");
/* 158:    */     
/* 159:187 */     sql.append(" WHERE f.estado != :estadoAnulado ");
/* 160:188 */     sql.append(" AND f.idOrganizacion =:idOrganizacion ");
/* 161:189 */     if ((idCajaChica == 0) && (fechaContabilizacion)) {
/* 162:190 */       sql.append(" AND cc.fechaContabilizacion BETWEEN :fechaDesde AND :fechaHasta ");
/* 163:    */     }
/* 164:192 */     if ((idCajaChica == 0) && (!fechaContabilizacion)) {
/* 165:193 */       sql.append(" AND f.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 166:    */     }
/* 167:195 */     sql.append(" AND (cc.idCajaChica = :idCajaChica OR :idCajaChica=0) ");
/* 168:197 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0)) {
/* 169:198 */       sql.append(" AND f.documentoReferencia >= :numeroDesde AND f.documentoReferencia <= :numeroHasta ");
/* 170:    */     }
/* 171:200 */     if (tipoCompra == 1) {
/* 172:201 */       sql.append(" AND f.indicadorFactura = true ");
/* 173:202 */     } else if (tipoCompra == 0) {
/* 174:203 */       sql.append(" AND f.indicadorFactura = false ");
/* 175:    */     }
/* 176:206 */     sql.append(" ORDER BY cc.nombre,f.fecha ");
/* 177:    */     
/* 178:208 */     Query query = this.em.createQuery(sql.toString());
/* 179:209 */     if (idCajaChica == 0)
/* 180:    */     {
/* 181:210 */       query.setParameter("fechaDesde", fechaDesde);
/* 182:211 */       query.setParameter("fechaHasta", fechaHasta);
/* 183:    */     }
/* 184:213 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 185:214 */     query.setParameter("idCajaChica", Integer.valueOf(idCajaChica));
/* 186:215 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 187:217 */     if ((numeroDesde.trim().length() != 0) && (numeroHasta.trim().length() != 0))
/* 188:    */     {
/* 189:218 */       query.setParameter("numeroDesde", numeroDesde);
/* 190:219 */       query.setParameter("numeroHasta", numeroHasta);
/* 191:    */     }
/* 192:222 */     return query.getResultList();
/* 193:    */   }
/* 194:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CompraCajaChicaDao
 * JD-Core Version:    0.7.0.1
 */