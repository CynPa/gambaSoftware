/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.entities.Especialidad;
/*   6:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*   8:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  11:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  12:    */ import java.math.BigDecimal;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.Stateless;
/*  17:    */ import javax.persistence.EntityManager;
/*  18:    */ import javax.persistence.Query;
/*  19:    */ import javax.persistence.TypedQuery;
/*  20:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  21:    */ import javax.persistence.criteria.CriteriaQuery;
/*  22:    */ import javax.persistence.criteria.Expression;
/*  23:    */ import javax.persistence.criteria.Join;
/*  24:    */ import javax.persistence.criteria.JoinType;
/*  25:    */ import javax.persistence.criteria.Path;
/*  26:    */ import javax.persistence.criteria.Predicate;
/*  27:    */ import javax.persistence.criteria.Root;
/*  28:    */ import javax.persistence.criteria.Selection;
/*  29:    */ 
/*  30:    */ @Stateless
/*  31:    */ public class EmisionBonoDao
/*  32:    */   extends AbstractDaoAS2<DetalleFacturaCliente>
/*  33:    */ {
/*  34:    */   public EmisionBonoDao()
/*  35:    */   {
/*  36: 51 */     super(DetalleFacturaCliente.class);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<DetalleFacturaCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  40:    */   {
/*  41: 61 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  42: 62 */     CriteriaQuery<DetalleFacturaCliente> criteriaQuery = criteriaBuilder.createQuery(DetalleFacturaCliente.class);
/*  43: 63 */     Root<DetalleFacturaCliente> from = criteriaQuery.from(DetalleFacturaCliente.class);
/*  44:    */     
/*  45: 65 */     Join<Object, Object> empresaBono = from.join("empresaBono", JoinType.LEFT);
/*  46: 66 */     Join<Object, Object> facturaCliente = from.join("facturaCliente", JoinType.LEFT);
/*  47:    */     
/*  48:    */ 
/*  49: 69 */     Path<Integer> pathidOrganizacionDetalleFacturaCliente = from.get("idOrganizacion");
/*  50: 70 */     Path<String> pathUsuarioCreacionDetalleFacturaCliente = from.get("usuarioCreacion");
/*  51: 71 */     Path<String> pathUsuarioModificacionDetalleFacturaCliente = from.get("usuarioModificacion");
/*  52: 72 */     Path<Date> pathFechaCreacionDetalleFacturaCliente = from.get("fechaCreacion");
/*  53: 73 */     Path<Date> pathFechaModificacionDetalleFacturaCliente = from.get("fechaModificacion");
/*  54: 74 */     Path<Integer> pathIdDetalleFacturaCliente = from.get("idDetalleFacturaCliente");
/*  55: 75 */     Path<String> pathNumeroBono = from.get("referencia");
/*  56: 76 */     Path<Estado> pathEstadoDetalleFacturaCliente = from.get("estado");
/*  57: 77 */     Path<BigDecimal> pathPrecioDetalleFacturaCliente = from.get("precio");
/*  58: 78 */     Path<BigDecimal> pathDescuentoDetalleFacturaCliente = from.get("descuento");
/*  59: 79 */     Path<BigDecimal> pathPrecioLineaDetalleFacturaCliente = from.get("precioLinea");
/*  60:    */     
/*  61:    */ 
/*  62: 82 */     Path<Integer> pathIdFacturaCliente = facturaCliente.get("idFacturaCliente");
/*  63: 83 */     Path<String> pathNumeroFacturaCliente = facturaCliente.get("numero");
/*  64: 84 */     Path<Estado> pathEstadoFacturaCliente = facturaCliente.get("estado");
/*  65: 85 */     Path<Date> pathFechaFacturaCliente = facturaCliente.get("fecha");
/*  66:    */     
/*  67:    */ 
/*  68:    */ 
/*  69: 89 */     Path<String> pathEmpresaNombreFiscalBono = empresaBono.get("nombreFiscal");
/*  70: 90 */     Path<String> pathEmpresaNombreComercialBono = empresaBono.get("nombreComercial");
/*  71: 91 */     Path<Integer> pathIdEmpresaBono = empresaBono.get("idEmpresa");
/*  72:    */     
/*  73: 93 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  74: 94 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  75:    */     
/*  76: 96 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  77:    */     
/*  78: 98 */     CriteriaQuery<DetalleFacturaCliente> select = criteriaQuery.multiselect(new Selection[] { pathidOrganizacionDetalleFacturaCliente, pathUsuarioCreacionDetalleFacturaCliente, pathUsuarioModificacionDetalleFacturaCliente, pathFechaCreacionDetalleFacturaCliente, pathFechaModificacionDetalleFacturaCliente, pathIdDetalleFacturaCliente, pathNumeroBono, pathEstadoDetalleFacturaCliente, pathIdFacturaCliente, pathNumeroFacturaCliente, pathEstadoFacturaCliente, pathEmpresaNombreFiscalBono, pathEmpresaNombreComercialBono, pathIdEmpresaBono, pathFechaFacturaCliente, pathPrecioDetalleFacturaCliente, pathDescuentoDetalleFacturaCliente, pathPrecioLineaDetalleFacturaCliente });
/*  79:    */     
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:107 */     TypedQuery<DetalleFacturaCliente> typedQuery = this.em.createQuery(select);
/*  88:108 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  89:109 */     return typedQuery.getResultList();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List<DetalleFacturaCliente> listaBonos(Date fechaDesde, Date fechaHasta, Empresa empresa, Empresa empresaBono, Sucursal sucursal, PuntoDeVenta puntoVenta, Especialidad tipoBono, PersonaResponsable responsableSalidaMercaderia, int idOrganizacion, FacturaCliente facturaCliente, EntidadUsuario usuario, int valorReporte, boolean anulado)
/*  93:    */   {
/*  94:119 */     StringBuilder sql = new StringBuilder();
/*  95:120 */     sql.append(" SELECT fc.fecha, e.nombreFiscal, eb.nombreFiscal, tb.nombre, s.nombre, dfc.precio, dfc.precioLinea, dfc.referencia,");
/*  96:121 */     sql.append(" fc.numero, CONCAT(m.nombres,' ',m.apellidos), 1, dfc.estado ");
/*  97:122 */     sql.append(" FROM DetalleFacturaCliente dfc ");
/*  98:123 */     sql.append(" LEFT JOIN dfc.facturaCliente fc ");
/*  99:124 */     sql.append(" LEFT JOIN fc.empresa e ");
/* 100:125 */     sql.append(" LEFT JOIN fc.sucursal s ");
/* 101:126 */     sql.append(" LEFT JOIN dfc.empresaBono eb ");
/* 102:127 */     sql.append(" LEFT JOIN dfc.tipoBono tb ");
/* 103:128 */     sql.append(" LEFT JOIN dfc.medico m ");
/* 104:129 */     sql.append(" WHERE fc.idOrganizacion = :idOrganizacion ");
/* 105:130 */     sql.append(" AND fc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 106:131 */     sql.append(" AND fc.estado != :estado ");
/* 107:132 */     sql.append(" AND dfc.indicadorBono = true ");
/* 108:133 */     if (facturaCliente != null) {
/* 109:134 */       sql.append(" AND fc = :facturaCliente  ");
/* 110:    */     }
/* 111:137 */     if (empresa != null) {
/* 112:138 */       sql.append(" AND e = :empresa  ");
/* 113:    */     }
/* 114:140 */     if (empresaBono != null) {
/* 115:141 */       sql.append(" AND eb = :empresaBono  ");
/* 116:    */     }
/* 117:143 */     if (sucursal != null) {
/* 118:144 */       sql.append(" AND s = :sucursal  ");
/* 119:    */     }
/* 120:146 */     if (puntoVenta != null) {
/* 121:147 */       sql.append(" AND fc.numero LIKE CONCAT('%-',:puntoVenta,'-%') ");
/* 122:    */     }
/* 123:149 */     if (tipoBono != null) {
/* 124:150 */       sql.append(" AND tb = :tipoBono  ");
/* 125:    */     }
/* 126:152 */     if (responsableSalidaMercaderia != null) {
/* 127:153 */       sql.append(" AND m = :responsableSalidaMercaderia  ");
/* 128:    */     }
/* 129:155 */     if (usuario != null) {
/* 130:156 */       sql.append(" AND fc.usuarioCreacion = :usuario  ");
/* 131:    */     }
/* 132:158 */     if ((valorReporte == 1) || (valorReporte == 2)) {
/* 133:159 */       sql.append(" AND dfc.estado = :estadoBono  ");
/* 134:    */     }
/* 135:163 */     sql.append(" ORDER BY fc.numero, dfc.referencia ");
/* 136:    */     
/* 137:165 */     Query query = this.em.createQuery(sql.toString());
/* 138:166 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 139:167 */     query.setParameter("estado", Estado.ANULADO);
/* 140:168 */     query.setParameter("fechaDesde", fechaDesde);
/* 141:169 */     query.setParameter("fechaHasta", fechaHasta);
/* 142:170 */     if (facturaCliente != null) {
/* 143:171 */       query.setParameter("facturaCliente", facturaCliente);
/* 144:    */     }
/* 145:173 */     if (empresa != null) {
/* 146:174 */       query.setParameter("empresa", empresa);
/* 147:    */     }
/* 148:176 */     if (empresaBono != null) {
/* 149:177 */       query.setParameter("empresaBono", empresaBono);
/* 150:    */     }
/* 151:179 */     if (sucursal != null) {
/* 152:180 */       query.setParameter("sucursal", sucursal);
/* 153:    */     }
/* 154:182 */     if (puntoVenta != null) {
/* 155:183 */       query.setParameter("puntoVenta", puntoVenta.getCodigo());
/* 156:    */     }
/* 157:185 */     if (tipoBono != null) {
/* 158:186 */       query.setParameter("tipoBono", tipoBono);
/* 159:    */     }
/* 160:188 */     if (responsableSalidaMercaderia != null) {
/* 161:189 */       query.setParameter("responsableSalidaMercaderia", responsableSalidaMercaderia);
/* 162:    */     }
/* 163:191 */     if (usuario != null) {
/* 164:192 */       query.setParameter("usuario", usuario.getNombreUsuario());
/* 165:    */     }
/* 166:194 */     if ((valorReporte == 1) || (valorReporte == 2))
/* 167:    */     {
/* 168:195 */       Estado estadoBono = Estado.ELABORADO;
/* 169:196 */       if (anulado) {
/* 170:197 */         estadoBono = Estado.ANULADO;
/* 171:    */       }
/* 172:199 */       query.setParameter("estadoBono", estadoBono);
/* 173:    */     }
/* 174:202 */     return query.getResultList();
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.EmisionBonoDao
 * JD-Core Version:    0.7.0.1
 */