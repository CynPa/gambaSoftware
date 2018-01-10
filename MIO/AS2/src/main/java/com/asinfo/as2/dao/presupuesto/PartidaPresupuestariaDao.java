/*   1:    */ package com.asinfo.as2.dao.presupuesto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import com.asinfo.as2.entities.NivelCuenta;
/*   6:    */ import com.asinfo.as2.entities.presupuesto.NivelPartidaPresupuestaria;
/*   7:    */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*   8:    */ import com.asinfo.as2.enumeraciones.GrupoCuenta;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TypedQuery;
/*  15:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  16:    */ import javax.persistence.criteria.CriteriaQuery;
/*  17:    */ import javax.persistence.criteria.Expression;
/*  18:    */ import javax.persistence.criteria.JoinType;
/*  19:    */ import javax.persistence.criteria.Predicate;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class PartidaPresupuestariaDao
/*  24:    */   extends AbstractDaoAS2<PartidaPresupuestaria>
/*  25:    */ {
/*  26:    */   public PartidaPresupuestariaDao()
/*  27:    */   {
/*  28: 45 */     super(PartidaPresupuestaria.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<PartidaPresupuestaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  32:    */   {
/*  33: 56 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  34: 57 */     CriteriaQuery<PartidaPresupuestaria> criteriaQuery = criteriaBuilder.createQuery(PartidaPresupuestaria.class);
/*  35:    */     
/*  36: 59 */     Root<PartidaPresupuestaria> from = criteriaQuery.from(PartidaPresupuestaria.class);
/*  37: 60 */     from.fetch("nivelPartidaPresupuestaria", JoinType.LEFT);
/*  38:    */     
/*  39: 62 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  40: 63 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  41:    */     
/*  42: 65 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  43:    */     
/*  44: 67 */     CriteriaQuery<PartidaPresupuestaria> select = criteriaQuery.select(from);
/*  45: 68 */     TypedQuery<PartidaPresupuestaria> typedQuery = this.em.createQuery(select);
/*  46: 69 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  47:    */     
/*  48: 71 */     return typedQuery.getResultList();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<PartidaPresupuestaria> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  52:    */   {
/*  53: 80 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  54: 81 */     CriteriaQuery<PartidaPresupuestaria> criteriaQuery = criteriaBuilder.createQuery(PartidaPresupuestaria.class);
/*  55: 82 */     Root<PartidaPresupuestaria> from = criteriaQuery.from(PartidaPresupuestaria.class);
/*  56:    */     
/*  57: 84 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  58: 85 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  59:    */     
/*  60: 87 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  61:    */     
/*  62: 89 */     CriteriaQuery<PartidaPresupuestaria> select = criteriaQuery.select(from);
/*  63: 90 */     TypedQuery<PartidaPresupuestaria> typedQuery = this.em.createQuery(select);
/*  64:    */     
/*  65: 92 */     return typedQuery.getResultList();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public PartidaPresupuestaria cargarDetalle(int idPartidaPresupuestaria)
/*  69:    */   {
/*  70:102 */     PartidaPresupuestaria partidaPresupuestaria = (PartidaPresupuestaria)buscarPorId(Integer.valueOf(idPartidaPresupuestaria));
/*  71:103 */     partidaPresupuestaria.getListaCuentaContable().size();
/*  72:105 */     for (CuentaContable cuentaContable : partidaPresupuestaria.getListaCuentaContable())
/*  73:    */     {
/*  74:106 */       cuentaContable.getId();
/*  75:107 */       cuentaContable.getPartidaPresupuestaria().getId();
/*  76:108 */       cuentaContable.getNivelCuenta().getId();
/*  77:109 */       cuentaContable.getCuentaPadre().getId();
/*  78:    */     }
/*  79:113 */     if (partidaPresupuestaria.getNivelPartidaPresupuestaria() != null) {
/*  80:114 */       partidaPresupuestaria.getNivelPartidaPresupuestaria().getId();
/*  81:    */     }
/*  82:116 */     if (partidaPresupuestaria.getPartidaPresupuestariaPadre() != null) {
/*  83:117 */       partidaPresupuestaria.getPartidaPresupuestariaPadre().getId();
/*  84:    */     }
/*  85:119 */     return partidaPresupuestaria;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public List<PartidaPresupuestaria> buscarPorGrupoNivelPartidaPresupuestaria(GrupoCuenta grupoPartidaPresupuestaria, int codigo, int idOrganizacion)
/*  89:    */   {
/*  90:125 */     StringBuilder sql = new StringBuilder();
/*  91:126 */     sql.append(" SELECT p FROM PartidaPresupuestaria p ");
/*  92:127 */     sql.append(" WHERE p.nivelPartidaPresupuestaria.codigo =:codigo ");
/*  93:128 */     sql.append(" and p.grupoPartidaPresupuestaria =:grupoPartidaPresupuestaria ");
/*  94:129 */     sql.append(" and p.idOrganizacion = :idOrganizacion ");
/*  95:    */     
/*  96:131 */     Query query = this.em.createQuery(sql.toString());
/*  97:132 */     query.setParameter("codigo", Integer.valueOf(codigo));
/*  98:133 */     query.setParameter("grupoPartidaPresupuestaria", grupoPartidaPresupuestaria);
/*  99:134 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 100:135 */     return query.getResultList();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List<Object[]> getReportePartidaPresupuestaria(int idPartidaPresupuestaria, int idOrganizacion)
/* 104:    */   {
/* 105:140 */     StringBuilder sql = new StringBuilder();
/* 106:141 */     sql.append(" SELECT npp.nombre, 'gpp.nombre', ppp.nombre, p.codigo, p.nombre, p.indicadorMovimiento, p.descripcion, lcc.codigo, lcc.nombre");
/* 107:142 */     sql.append(" FROM PartidaPresupuestaria p");
/* 108:143 */     sql.append(" INNER JOIN p.nivelPartidaPresupuestaria npp");
/* 109:144 */     sql.append(" LEFT JOIN p.listaCuentaContable lcc");
/* 110:145 */     sql.append(" LEFT JOIN p.partidaPresupuestariaPadre ppp");
/* 111:146 */     sql.append(" WHERE p.idOrganizacion=:idOrganizacion");
/* 112:    */     
/* 113:    */ 
/* 114:    */ 
/* 115:150 */     sql.append(" ORDER BY p.codigo, lcc.codigo");
/* 116:151 */     Query query = this.em.createQuery(sql.toString());
/* 117:152 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 118:    */     
/* 119:    */ 
/* 120:    */ 
/* 121:156 */     return query.getResultList();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public List<PartidaPresupuestaria> getPartidaPresupuestariaPorUsuario(int idOrganizacion, int idUsuario)
/* 125:    */   {
/* 126:162 */     StringBuilder sql = new StringBuilder();
/* 127:163 */     sql.append("SELECT pp");
/* 128:164 */     sql.append(" FROM UsuarioDimensionContable udc");
/* 129:165 */     sql.append(" INNER JOIN udc.entidadUsuario u");
/* 130:166 */     sql.append(" INNER JOIN udc.dimensionContable dc");
/* 131:167 */     sql.append(" INNER JOIN dc.listaCuentaContableDimensionContable lccdc");
/* 132:168 */     sql.append(" INNER JOIN lccdc.cuentaContable cc");
/* 133:169 */     sql.append(" INNER JOIN cc.partidaPresupuestaria pp");
/* 134:170 */     sql.append(" WHERE u.idUsuario = :idUsuario");
/* 135:171 */     sql.append(" AND dc.activo = true");
/* 136:172 */     sql.append(" AND pp.activo = true");
/* 137:173 */     sql.append(" AND udc.indicadorPresupuesto = true");
/* 138:174 */     sql.append(" AND udc.idOrganizacion =:idOrganizacion");
/* 139:175 */     sql.append(" ORDER BY pp.codigo");
/* 140:    */     
/* 141:177 */     Query queryOrganizacion = this.em.createQuery(sql.toString());
/* 142:178 */     queryOrganizacion.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 143:179 */     queryOrganizacion.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 144:    */     
/* 145:181 */     return queryOrganizacion.getResultList();
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.presupuesto.PartidaPresupuestariaDao
 * JD-Core Version:    0.7.0.1
 */