/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CuentaContable;
/*   4:    */ import com.asinfo.as2.entities.CuentaContableDimensionContable;
/*   5:    */ import com.asinfo.as2.entities.DimensionContable;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.NoResultException;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TypedQuery;
/*  15:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  16:    */ import javax.persistence.criteria.CriteriaQuery;
/*  17:    */ import javax.persistence.criteria.Expression;
/*  18:    */ import javax.persistence.criteria.Predicate;
/*  19:    */ import javax.persistence.criteria.Root;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class DimensionContableDao
/*  23:    */   extends AbstractDaoAS2<DimensionContable>
/*  24:    */ {
/*  25:    */   public DimensionContableDao()
/*  26:    */   {
/*  27: 46 */     super(DimensionContable.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void eliminar(DimensionContable entidad)
/*  31:    */   {
/*  32: 57 */     this.em.merge(entidad);
/*  33: 58 */     if (entidad.getDimensionPadre() != null) {
/*  34: 59 */       entidad.setDimensionPadre(null);
/*  35:    */     }
/*  36: 61 */     super.eliminar(entidad);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public DimensionContable cargarDetalle(int idDimensionContable)
/*  40:    */   {
/*  41: 73 */     DimensionContable dimensionContable = (DimensionContable)buscarPorId(Integer.valueOf(idDimensionContable));
/*  42: 74 */     if (dimensionContable.getDimensionPadre() != null) {
/*  43: 75 */       dimensionContable.getDimensionPadre().getId();
/*  44:    */     }
/*  45: 77 */     for (CuentaContable cuentaContable : dimensionContable.getListaCuentaContable()) {
/*  46: 78 */       cuentaContable.getId();
/*  47:    */     }
/*  48: 80 */     for (CuentaContableDimensionContable cuentaContableDimensionContable : dimensionContable.getListaCuentaContableDimensionContable())
/*  49:    */     {
/*  50: 81 */       cuentaContableDimensionContable.getId();
/*  51: 82 */       cuentaContableDimensionContable.getCuentaContable().getId();
/*  52: 83 */       cuentaContableDimensionContable.getDimensionContable().getId();
/*  53:    */     }
/*  54: 85 */     for (CuentaContableDimensionContable cuentaContableDimensionContable : dimensionContable.getListaCuentaContableDimensionContable())
/*  55:    */     {
/*  56: 86 */       cuentaContableDimensionContable.getId();
/*  57: 87 */       cuentaContableDimensionContable.getCuentaContable().getId();
/*  58: 88 */       cuentaContableDimensionContable.getDimensionContable().getId();
/*  59:    */     }
/*  60: 90 */     return dimensionContable;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public DimensionContable buscarPorCodigo(String numeroDimension, String codigo)
/*  64:    */     throws ExcepcionAS2
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:102 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  69:103 */       CriteriaQuery<DimensionContable> criteriaQuery = criteriaBuilder.createQuery(DimensionContable.class);
/*  70:104 */       Root<DimensionContable> from = criteriaQuery.from(DimensionContable.class);
/*  71:    */       
/*  72:106 */       Map<String, String> filters = new HashMap();
/*  73:107 */       filters.put("numero", "=" + numeroDimension);
/*  74:108 */       filters.put("codigo", "=" + codigo);
/*  75:109 */       List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  76:110 */       criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  77:    */       
/*  78:112 */       CriteriaQuery<DimensionContable> select = criteriaQuery.select(from);
/*  79:113 */       TypedQuery<DimensionContable> typedQuery = this.em.createQuery(select);
/*  80:    */       
/*  81:115 */       return (DimensionContable)typedQuery.getSingleResult();
/*  82:    */     }
/*  83:    */     catch (NoResultException e)
/*  84:    */     {
/*  85:118 */       throw new ExcepcionAS2("msg_info_dimension_contable_no_encontrada", " " + codigo);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public List<Object[]> getReporteDimensionContable(int idDimensionContable, int idOrganizacion)
/*  90:    */   {
/*  91:124 */     StringBuilder sql = new StringBuilder();
/*  92:125 */     sql.append(" SELECT dc.codigo, dc.nombre, dc.fechaDesde, dc.fechaHasta, dc.tipoAccesoContable, dc.descripcion, dp.nombre, cc.codigo, cc.nombre");
/*  93:126 */     sql.append(" FROM DimensionContable dc");
/*  94:127 */     sql.append(" LEFT JOIN dc.listaCuentaContableDimensionContable lccdc");
/*  95:128 */     sql.append(" LEFT JOIN lccdc.cuentaContable cc");
/*  96:129 */     sql.append(" LEFT JOIN dc.dimensionPadre dp");
/*  97:130 */     sql.append(" WHERE dc.idOrganizacion=:idOrganizacion");
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101:134 */     sql.append(" ORDER BY dc.codigo, cc.codigo");
/* 102:135 */     Query query = this.em.createQuery(sql.toString());
/* 103:136 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 104:    */     
/* 105:    */ 
/* 106:    */ 
/* 107:140 */     return query.getResultList();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<DimensionContable> buscarDimensionesHijas(int idDimensionContablePadre, int idOrganizacion)
/* 111:    */   {
/* 112:145 */     StringBuilder sql = new StringBuilder();
/* 113:146 */     sql.append(" SELECT dc");
/* 114:147 */     sql.append(" FROM DimensionContable dc");
/* 115:148 */     sql.append(" LEFT JOIN dc.listaCuentaContable lcc");
/* 116:149 */     sql.append(" LEFT JOIN dc.dimensionPadre dp");
/* 117:150 */     sql.append(" WHERE dc.idOrganizacion=:idOrganizacion");
/* 118:151 */     sql.append(" AND dp.idDimensionContable=:idDimensionContablePadre");
/* 119:152 */     Query query = this.em.createQuery(sql.toString());
/* 120:153 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 121:154 */     query.setParameter("idDimensionContablePadre", Integer.valueOf(idDimensionContablePadre));
/* 122:155 */     return query.getResultList();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public List<DimensionContable> obtenerDimensionContablePorUsuario(int idOrganizacion, int idUsuario, int longitudCodigo, boolean indicadorPresupuesto)
/* 126:    */   {
/* 127:162 */     StringBuilder sql = new StringBuilder();
/* 128:163 */     sql.append("SELECT dc");
/* 129:164 */     sql.append(" FROM UsuarioDimensionContable udc");
/* 130:165 */     sql.append(" INNER JOIN udc.entidadUsuario u");
/* 131:166 */     sql.append(" INNER JOIN udc.dimensionContable dc");
/* 132:167 */     sql.append(" WHERE u.idUsuario = :idUsuario");
/* 133:168 */     sql.append(" AND dc.activo = true");
/* 134:169 */     sql.append(" AND udc.idOrganizacion =:idOrganizacion");
/* 135:170 */     sql.append(" AND udc.indicadorPresupuesto = :indicadorPresupuesto");
/* 136:171 */     if (longitudCodigo > 0) {
/* 137:172 */       sql.append(" AND LENGTH(dc.codigo) =:longitudCodigo");
/* 138:    */     }
/* 139:174 */     sql.append(" ORDER BY dc.codigo");
/* 140:    */     
/* 141:176 */     Query queryOrganizacion = this.em.createQuery(sql.toString());
/* 142:177 */     queryOrganizacion.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 143:178 */     queryOrganizacion.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 144:179 */     queryOrganizacion.setParameter("indicadorPresupuesto", Boolean.valueOf(indicadorPresupuesto));
/* 145:180 */     if (longitudCodigo > 0) {
/* 146:181 */       queryOrganizacion.setParameter("longitudCodigo", Integer.valueOf(longitudCodigo));
/* 147:    */     }
/* 148:183 */     return queryOrganizacion.getResultList();
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DimensionContableDao
 * JD-Core Version:    0.7.0.1
 */