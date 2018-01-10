/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Banco;
/*   4:    */ import com.asinfo.as2.entities.Cobro;
/*   5:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   6:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   9:    */ import com.asinfo.as2.entities.GarantiaCliente;
/*  10:    */ import com.asinfo.as2.enumeraciones.EstadoGarantiaCliente;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoGarantiaCliente;
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
/*  23:    */ import javax.persistence.criteria.Fetch;
/*  24:    */ import javax.persistence.criteria.JoinType;
/*  25:    */ import javax.persistence.criteria.Predicate;
/*  26:    */ import javax.persistence.criteria.Root;
/*  27:    */ 
/*  28:    */ @Stateless
/*  29:    */ public class GarantiaClienteDao
/*  30:    */   extends AbstractDaoAS2<GarantiaCliente>
/*  31:    */ {
/*  32:    */   public GarantiaClienteDao()
/*  33:    */   {
/*  34: 48 */     super(GarantiaCliente.class);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public GarantiaCliente cargarDetalle(int idGarantiaCliente)
/*  38:    */   {
/*  39: 54 */     GarantiaCliente garantiaCliente = (GarantiaCliente)buscarPorId(Integer.valueOf(idGarantiaCliente));
/*  40:    */     
/*  41: 56 */     garantiaCliente.getEmpresa().getId();
/*  42: 57 */     if (garantiaCliente.getBanco() != null) {
/*  43: 58 */       garantiaCliente.getBanco().getId();
/*  44:    */     }
/*  45: 60 */     if (garantiaCliente.getCuentaBancariaOrganizacion() == null) {
/*  46: 61 */       garantiaCliente.getCuentaBancariaOrganizacion().getId();
/*  47:    */     }
/*  48: 63 */     if (garantiaCliente.getFacturaCliente() != null) {
/*  49: 64 */       garantiaCliente.getFacturaCliente().getId();
/*  50:    */     }
/*  51: 66 */     if (garantiaCliente.getDetalleFormaCobro() != null)
/*  52:    */     {
/*  53: 67 */       garantiaCliente.getDetalleFormaCobro().getId();
/*  54: 68 */       if (garantiaCliente.getDetalleFormaCobro().getCaja() != null) {
/*  55: 69 */         garantiaCliente.getDetalleFormaCobro().getCobro().getId();
/*  56:    */       }
/*  57: 71 */       if (garantiaCliente.getDetalleFormaCobro().getCuentaBancariaOrganizacion() != null) {
/*  58: 72 */         garantiaCliente.getDetalleFormaCobro().getCuentaBancariaOrganizacion().getId();
/*  59:    */       }
/*  60:    */     }
/*  61: 76 */     return garantiaCliente;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List<GarantiaCliente> buscaListaChequesPosfechados(int idEmpresa)
/*  65:    */   {
/*  66: 89 */     Query query = this.em.createQuery("SELECT ch FROM GarantiaCliente ch  INNER JOIN FETCH ch.empresa e  INNER JOIN FETCH ch.banco b   WHERE e.idEmpresa = :idEmpresa AND ch.estadoGarantiaCliente = :estado AND ch.tipoGarantiaCliente =:tipoGarantiaCliente");
/*  67:    */     
/*  68:    */ 
/*  69: 92 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  70: 93 */     query.setParameter("estado", EstadoGarantiaCliente.REGISTRADO);
/*  71: 94 */     query.setParameter("tipoGarantiaCliente", TipoGarantiaCliente.CHEQUE_POSFECHADO);
/*  72:    */     
/*  73: 96 */     return query.getResultList();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public List<GarantiaCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  77:    */   {
/*  78:102 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  79:103 */     CriteriaQuery<GarantiaCliente> criteriaQuery = criteriaBuilder.createQuery(GarantiaCliente.class);
/*  80:104 */     Root<GarantiaCliente> from = criteriaQuery.from(GarantiaCliente.class);
/*  81:    */     
/*  82:106 */     from.fetch("empresa", JoinType.LEFT);
/*  83:107 */     from.fetch("banco", JoinType.LEFT);
/*  84:108 */     from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  85:109 */     from.fetch("facturaCliente", JoinType.LEFT);
/*  86:110 */     Fetch<Object, Object> detalleFormaCobro = from.fetch("detalleFormaCobro", JoinType.LEFT);
/*  87:111 */     detalleFormaCobro.fetch("cobro", JoinType.LEFT);
/*  88:    */     
/*  89:113 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  90:    */     
/*  91:115 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  92:116 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  93:    */     
/*  94:118 */     CriteriaQuery<GarantiaCliente> select = criteriaQuery.select(from);
/*  95:    */     
/*  96:120 */     TypedQuery<GarantiaCliente> typedQuery = this.em.createQuery(select);
/*  97:121 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  98:    */     
/*  99:123 */     return typedQuery.getResultList();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public BigDecimal obtenerSaldoChequePosfechado(int idCliente, Date fechaHasta)
/* 103:    */   {
/* 104:127 */     String sql = " SELECT COALESCE(SUM(valor),0) FROM GarantiaCliente c  WHERE c.fechaCobro <= :fechaHasta AND c.estadoGarantiaCliente = :estadoRegistrado AND c.empresa.idEmpresa= :idCliente AND c.tipoGarantiaCliente = :tipoGarantiaCliente";
/* 105:    */     
/* 106:    */ 
/* 107:130 */     Query query = this.em.createQuery(sql);
/* 108:131 */     query.setParameter("fechaHasta", fechaHasta).setParameter("idCliente", Integer.valueOf(idCliente))
/* 109:132 */       .setParameter("estadoRegistrado", EstadoGarantiaCliente.REGISTRADO)
/* 110:133 */       .setParameter("tipoGarantiaCliente", TipoGarantiaCliente.CHEQUE_POSFECHADO);
/* 111:134 */     BigDecimal resultado = (BigDecimal)query.getSingleResult();
/* 112:135 */     return resultado;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public List<GarantiaCliente> getListaGarantiaClienteCobro(int idCobro)
/* 116:    */   {
/* 117:146 */     StringBuffer sql = new StringBuffer();
/* 118:147 */     sql.append(" SELECT ga FROM GarantiaCliente ga ");
/* 119:148 */     sql.append(" LEFT OUTER JOIN ga.detalleFormaCobro dfc ");
/* 120:149 */     sql.append(" LEFT OUTER JOIN dfc.cobro c ");
/* 121:150 */     sql.append(" LEFT JOIN FETCH ga.banco ba ");
/* 122:151 */     sql.append(" WHERE c.idCobro = :idCobro ");
/* 123:152 */     Query query = this.em.createQuery(sql.toString());
/* 124:153 */     query.setParameter("idCobro", Integer.valueOf(idCobro));
/* 125:    */     
/* 126:155 */     return query.getResultList();
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.GarantiaClienteDao
 * JD-Core Version:    0.7.0.1
 */