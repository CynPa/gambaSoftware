/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleVacacion;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   8:    */ import com.asinfo.as2.entities.Secuencia;
/*   9:    */ import com.asinfo.as2.entities.Vacacion;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.persistence.TypedQuery;
/*  18:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  19:    */ import javax.persistence.criteria.CriteriaQuery;
/*  20:    */ import javax.persistence.criteria.Expression;
/*  21:    */ import javax.persistence.criteria.Fetch;
/*  22:    */ import javax.persistence.criteria.JoinType;
/*  23:    */ import javax.persistence.criteria.Predicate;
/*  24:    */ import javax.persistence.criteria.Root;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ public class DetalleVacacionDao
/*  28:    */   extends AbstractDaoAS2<DetalleVacacion>
/*  29:    */ {
/*  30:    */   public DetalleVacacionDao()
/*  31:    */   {
/*  32: 50 */     super(DetalleVacacion.class);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<DetalleVacacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 59 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  38: 60 */     CriteriaQuery<DetalleVacacion> criteriaQuery = criteriaBuilder.createQuery(DetalleVacacion.class);
/*  39: 61 */     Root<DetalleVacacion> from = criteriaQuery.from(DetalleVacacion.class);
/*  40: 62 */     Fetch<Object, Object> documento = from.fetch("documento", JoinType.LEFT);
/*  41: 63 */     Fetch<Object, Object> vacacion = from.fetch("vacacion", JoinType.LEFT);
/*  42: 64 */     Fetch<Object, Object> historicoEmpleado = vacacion.fetch("historicoEmpleado", JoinType.LEFT);
/*  43: 65 */     Fetch<Object, Object> empleado = historicoEmpleado.fetch("empleado", JoinType.LEFT);
/*  44: 66 */     empleado.fetch("empresa", JoinType.LEFT);
/*  45:    */     
/*  46: 68 */     String estado2 = null;
/*  47: 69 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  48: 70 */     if (filters.containsKey("estado2"))
/*  49:    */     {
/*  50: 71 */       estado2 = (String)filters.get("estado2");
/*  51:    */       
/*  52: 73 */       filters.remove("estado2");
/*  53:    */     }
/*  54: 76 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  55: 78 */     if (estado2 != null) {
/*  56: 79 */       expresiones.add(criteriaBuilder.notEqual(from.get("estado"), Estado.valueOf(estado2)));
/*  57:    */     }
/*  58: 82 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  59:    */     
/*  60: 84 */     CriteriaQuery<DetalleVacacion> select = criteriaQuery.select(from);
/*  61: 85 */     TypedQuery<DetalleVacacion> typedQuery = this.em.createQuery(select);
/*  62: 86 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  63:    */     
/*  64: 88 */     return typedQuery.getResultList();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public DetalleVacacion cargarDetalle(int idDetalleVacacion)
/*  68:    */   {
/*  69: 98 */     DetalleVacacion detalleVacacion = new DetalleVacacion();
/*  70: 99 */     detalleVacacion = (DetalleVacacion)buscarPorId(Integer.valueOf(idDetalleVacacion));
/*  71:100 */     detalleVacacion.getId();
/*  72:101 */     detalleVacacion.getVacacion().getId();
/*  73:102 */     detalleVacacion.getVacacion().getHistoricoEmpleado().getId();
/*  74:103 */     detalleVacacion.getVacacion().getHistoricoEmpleado().getEmpleado().getId();
/*  75:104 */     detalleVacacion.getVacacion().getHistoricoEmpleado().getEmpleado().getEmpresa().getId();
/*  76:105 */     if (detalleVacacion.getDocumento() != null)
/*  77:    */     {
/*  78:106 */       detalleVacacion.getDocumento().getId();
/*  79:107 */       if (detalleVacacion.getDocumento().getSecuencia() != null) {
/*  80:108 */         detalleVacacion.getDocumento().getSecuencia().getId();
/*  81:    */       }
/*  82:    */     }
/*  83:110 */     return detalleVacacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int totalDiasSolicitadosPorEmpleadoPorVacacion(int idDetalleVacacion, int idVacacion)
/*  87:    */   {
/*  88:122 */     StringBuilder sql = new StringBuilder();
/*  89:123 */     sql.append(" SELECT sum(dv.diasTomados) FROM  DetalleVacacion dv ");
/*  90:124 */     sql.append(" INNER JOIN dv.vacacion v ");
/*  91:125 */     sql.append(" WHERE v.idVacacion=:idVacacion ");
/*  92:126 */     sql.append(" AND dv.idDetalleVacacion!=:idDetalleVacacion ");
/*  93:127 */     sql.append(" AND (dv.estado=:estadoElaborado OR dv.estado=:estadoAprobado) ");
/*  94:    */     
/*  95:129 */     Query query = this.em.createQuery(sql.toString());
/*  96:130 */     query.setParameter("estadoElaborado", Estado.ELABORADO);
/*  97:131 */     query.setParameter("estadoAprobado", Estado.APROBADO);
/*  98:    */     
/*  99:133 */     query.setParameter("idVacacion", Integer.valueOf(idVacacion));
/* 100:134 */     query.setParameter("idDetalleVacacion", Integer.valueOf(idDetalleVacacion));
/* 101:    */     String result;
/* 102:    */     String result;
/* 103:137 */     if (query.getSingleResult() == null) {
/* 104:138 */       result = "0";
/* 105:    */     } else {
/* 106:140 */       result = query.getSingleResult().toString();
/* 107:    */     }
/* 108:142 */     return Integer.parseInt(result);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<Vacacion> obtenerListaVacacionesPorDetalle(int idDetalleVacacion)
/* 112:    */   {
/* 113:147 */     DetalleVacacion detalleVacacion = new DetalleVacacion();
/* 114:148 */     detalleVacacion = (DetalleVacacion)buscarPorId(Integer.valueOf(idDetalleVacacion));
/* 115:149 */     int idEmpleado = detalleVacacion.getVacacion().getHistoricoEmpleado().getEmpleado().getId();
/* 116:150 */     String sql = "SELECT v\tFROM  Vacacion v LEFT JOIN v.historicoEmpleado he LEFT JOIN he.empleado e  WHERE e.idEmpleado=:idEmpleado)";
/* 117:    */     
/* 118:152 */     Query query = this.em.createQuery(sql);
/* 119:153 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 120:154 */     return query.getResultList();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public List getReporteSolicitudVacacion(int idDetalleVacacion, int idOrganizacion)
/* 124:    */   {
/* 125:159 */     StringBuilder sql = new StringBuilder();
/* 126:160 */     sql.append("SELECT he.empleado.empresa.codigo, em.identificacion, em.nombreFiscal, ec.nombre, d.nombre, ce.nombre, t.nombre, e.imagen,");
/* 127:161 */     sql.append(" he.fechaIngreso, dv.diasTomados, dv.fechaInicio, dv.fechaFin, v.fechaInicioPeriodo, v.fechaFinPeriodo,");
/* 128:162 */     sql.append(" v.dias, v.diasAdicionales, v.diasTomados, dv.descripcion, dv.numero, dv.usuarioCreacion, dv.usuarioModificacion, e.apellidos, e.nombres,  dv.fechaCreacion, v.indicadorAnticipoVacacion,dv.aprobadoPor,dir.telefono1 ");
/* 129:    */     
/* 130:164 */     sql.append(" FROM  DetalleVacacion dv");
/* 131:165 */     sql.append(" LEFT JOIN dv.vacacion v");
/* 132:166 */     sql.append(" LEFT JOIN v.historicoEmpleado he");
/* 133:167 */     sql.append(" LEFT JOIN he.empleado e");
/* 134:168 */     sql.append(" LEFT JOIN e.empresa em");
/* 135:169 */     sql.append(" LEFT JOIN em.direcciones dir ");
/* 136:170 */     sql.append(" LEFT JOIN e.cargoEmpleado ce");
/* 137:171 */     sql.append(" LEFT JOIN e.titulo t");
/* 138:172 */     sql.append(" LEFT JOIN e.departamento d");
/* 139:173 */     sql.append(" LEFT JOIN e.estadoCivil ec");
/* 140:174 */     sql.append(" WHERE (dv.idDetalleVacacion=:idDetalleVacacion)");
/* 141:175 */     sql.append(" AND he.idOrganizacion = :idOrganizacion");
/* 142:176 */     sql.append(" AND dir.indicadorDireccionPrincipal = TRUE");
/* 143:    */     
/* 144:178 */     Query query = this.em.createQuery(sql.toString());
/* 145:179 */     query.setParameter("idDetalleVacacion", Integer.valueOf(idDetalleVacacion));
/* 146:180 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 147:181 */     return query.getResultList();
/* 148:    */   }
/* 149:    */   
/* 150:    */   public long verificarDetalleVacacionDuplicado(DetalleVacacion detalleVacacion)
/* 151:    */   {
/* 152:186 */     StringBuilder sql = new StringBuilder();
/* 153:187 */     sql.append(" SELECT COUNT(*) FROM DetalleVacacion dv ");
/* 154:188 */     sql.append(" INNER JOIN dv.vacacion v ");
/* 155:189 */     sql.append(" WHERE v.idVacacion = :idVacacion ");
/* 156:190 */     sql.append(" AND dv.fechaInicio = :fechaInicio and dv.fechaFin = :fechaFin");
/* 157:191 */     sql.append(" AND dv.idOrganizacion = :idOrganizacion ");
/* 158:192 */     sql.append(" AND dv.estado <> :estado ");
/* 159:193 */     sql.append(" AND dv.idDetalleVacacion <> :idDetalleVacacion ");
/* 160:    */     
/* 161:195 */     Query query = this.em.createQuery(sql.toString());
/* 162:196 */     query.setParameter("idVacacion", Integer.valueOf(detalleVacacion.getVacacion().getId()));
/* 163:197 */     query.setParameter("fechaInicio", detalleVacacion.getFechaInicio(), TemporalType.DATE);
/* 164:198 */     query.setParameter("fechaFin", detalleVacacion.getFechaFin(), TemporalType.DATE);
/* 165:199 */     query.setParameter("idOrganizacion", Integer.valueOf(detalleVacacion.getIdOrganizacion()));
/* 166:200 */     query.setParameter("estado", Estado.ANULADO);
/* 167:201 */     query.setParameter("idDetalleVacacion", Integer.valueOf(detalleVacacion.getId()));
/* 168:    */     
/* 169:203 */     return ((Long)query.getSingleResult()).longValue();
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleVacacionDao
 * JD-Core Version:    0.7.0.1
 */