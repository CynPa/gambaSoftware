/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.FormaPago;
/*  11:    */ import com.asinfo.as2.entities.PagoEmpleado;
/*  12:    */ import com.asinfo.as2.entities.PagoRol;
/*  13:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  14:    */ import com.asinfo.as2.entities.Secuencia;
/*  15:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
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
/*  33:    */ public class PagoEmpleadoDao
/*  34:    */   extends AbstractDaoAS2<PagoEmpleado>
/*  35:    */ {
/*  36:    */   public PagoEmpleadoDao()
/*  37:    */   {
/*  38: 49 */     super(PagoEmpleado.class);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public PagoEmpleado cargarDetalle(int idPagoEmpleado)
/*  42:    */   {
/*  43: 59 */     PagoEmpleado pagoEmpleado = (PagoEmpleado)buscarPorId(Integer.valueOf(idPagoEmpleado));
/*  44: 60 */     pagoEmpleado.getDocumento().getId();
/*  45:    */     
/*  46: 62 */     pagoEmpleado.getCuentaBancariaOrganizacion().getId();
/*  47: 63 */     pagoEmpleado.getFormaPago().getId();
/*  48: 65 */     if (pagoEmpleado.getDocumento().getSecuencia() != null) {
/*  49: 66 */       pagoEmpleado.getDocumento().getSecuencia().getId();
/*  50:    */     }
/*  51: 69 */     pagoEmpleado.getDocumento().getTipoAsiento().getId();
/*  52: 70 */     pagoEmpleado.getPagoRol().getId();
/*  53:    */     
/*  54: 72 */     pagoEmpleado.getListaPagoRolEmpleado().size();
/*  55: 73 */     for (PagoRolEmpleado pagoRolEmpleado : pagoEmpleado.getListaPagoRolEmpleado())
/*  56:    */     {
/*  57: 74 */       pagoRolEmpleado.getEmpleado().getId();
/*  58: 75 */       pagoRolEmpleado.getEmpleado().getEmpresa().getId();
/*  59:    */     }
/*  60: 78 */     if (pagoEmpleado.getAsiento() != null) {
/*  61: 79 */       pagoEmpleado.getAsiento().getId();
/*  62:    */     }
/*  63: 82 */     return pagoEmpleado;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public List<PagoEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  67:    */   {
/*  68: 93 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  69: 94 */     CriteriaQuery<PagoEmpleado> criteriaQuery = criteriaBuilder.createQuery(PagoEmpleado.class);
/*  70: 95 */     Root<PagoEmpleado> from = criteriaQuery.from(PagoEmpleado.class);
/*  71:    */     
/*  72: 97 */     from.fetch("pagoRol", JoinType.LEFT);
/*  73: 98 */     from.fetch("asiento", JoinType.LEFT);
/*  74: 99 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/*  75:100 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/*  76:101 */     from.fetch("documento", JoinType.LEFT);
/*  77:    */     
/*  78:103 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  79:104 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  80:    */     
/*  81:106 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  82:    */     
/*  83:108 */     CriteriaQuery<PagoEmpleado> select = criteriaQuery.select(from);
/*  84:    */     
/*  85:110 */     TypedQuery<PagoEmpleado> typedQuery = this.em.createQuery(select);
/*  86:111 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  87:    */     
/*  88:113 */     return typedQuery.getResultList();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List<PagoRolEmpleado> cargarEmpleadosPendientes(int idPagoRol, boolean requiereAprobacion)
/*  92:    */   {
/*  93:118 */     StringBuilder sbSQL = new StringBuilder();
/*  94:119 */     sbSQL.append(" SELECT pre FROM PagoRolEmpleado pre");
/*  95:120 */     sbSQL.append(" JOIN pre.pagoRol pr JOIN FETCH pre.empleado e JOIN FETCH e.empresa");
/*  96:121 */     sbSQL.append(" WHERE pr.idPagoRol = :idPagoRol");
/*  97:122 */     sbSQL.append(" AND pre.pagoEmpleado IS NULL");
/*  98:123 */     sbSQL.append(" AND e.indicadorPagoCash=false");
/*  99:124 */     sbSQL.append(" AND pre.indicadorCobrado = false");
/* 100:125 */     sbSQL.append(" AND pre.valorAPagar > 0");
/* 101:127 */     if (requiereAprobacion) {
/* 102:128 */       sbSQL.append(" AND pre.indicadorAprobado=true");
/* 103:    */     }
/* 104:131 */     Query query = this.em.createQuery(sbSQL.toString());
/* 105:132 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/* 106:133 */     return query.getResultList();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<DetalleInterfazContable> getListaDIC(PagoEmpleado pagoEmpleado)
/* 110:    */     throws ExcepcionAS2Financiero
/* 111:    */   {
/* 112:138 */     String sql = "";
/* 113:    */     
/* 114:140 */     sql = "\tSELECT new DetalleInterfazContable(cbo.cuentaContableBanco.idCuentaContable,'',concat(em.nombres,' ',em.apellidos),pre.documentoReferencia,concat(em.nombres,' ',em.apellidos), pe.formaPago.idFormaPago,-pre.valorAPagar)\tFROM PagoRolEmpleado pre\t\tINNER JOIN pre.empleado em\tINNER JOIN em.empresa e\tINNER JOIN e.categoriaEmpresa ce\tINNER JOIN pre.pagoEmpleado pe\tINNER JOIN pe.cuentaBancariaOrganizacion cbo\tWHERE pe=:pagoEmpleado ";
/* 115:    */     
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:149 */     Query query = this.em.createQuery(sql);
/* 124:150 */     query.setParameter("pagoEmpleado", pagoEmpleado);
/* 125:151 */     return query.getResultList();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void actualizarEstado(int idPagoEmpleado, Estado estado)
/* 129:    */   {
/* 130:155 */     String sql = "UPDATE PagoEmpleado p SET p.estado = :estado WHERE p.idPagoEmpleado = :idPagoEmpleado";
/* 131:156 */     Query query = this.em.createQuery(sql);
/* 132:    */     
/* 133:158 */     query.setParameter("estado", estado).setParameter("idPagoEmpleado", Integer.valueOf(idPagoEmpleado)).executeUpdate();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public List<DetalleInterfazContableProceso> getListaPagoRolEmpleado(PagoEmpleado pagoEmpleado)
/* 137:    */     throws ExcepcionAS2Financiero
/* 138:    */   {
/* 139:163 */     String sql = "";
/* 140:    */     
/* 141:165 */     sql = "\tSELECT new DetallePagoEmpleado(ce.idCategoriaEmpresa, ce.nombre, ROUND(SUM(pre.valorAPagar),2))\tFROM PagoRolEmpleado pre \tINNER JOIN pre.empleado em\tINNER JOIN em.empresa e\tINNER JOIN e.categoriaEmpresa ce\tINNER JOIN pre.pagoEmpleado pe\tWHERE pe=:pagoEmpleado\tGROUP BY ce.idCategoriaEmpresa,ce.nombre HAVING SUM(pre.valorAPagar) > 0 ";
/* 142:    */     
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:174 */     Query query = this.em.createQuery(sql);
/* 151:175 */     query.setParameter("pagoEmpleado", pagoEmpleado);
/* 152:    */     
/* 153:177 */     return query.getResultList();
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PagoEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */