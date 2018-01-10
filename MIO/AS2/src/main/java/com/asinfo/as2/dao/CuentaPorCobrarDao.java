/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Cliente;
/*   4:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   7:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class CuentaPorCobrarDao
/*  17:    */   extends AbstractDaoAS2<CuentaPorCobrar>
/*  18:    */ {
/*  19:    */   public CuentaPorCobrarDao()
/*  20:    */   {
/*  21: 38 */     super(CuentaPorCobrar.class);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public BigDecimal getSaldo(CuentaPorCobrar cuentaPorCobrar)
/*  25:    */   {
/*  26: 48 */     Query query = this.em.createQuery("SELECT c.saldo FROM CuentaPorCobrar c WHERE c.idCuentaPorCobrar=:idCuentaPorCobrar");
/*  27: 49 */     query.setParameter("idCuentaPorCobrar", Integer.valueOf(cuentaPorCobrar.getIdCuentaPorCobrar()));
/*  28: 50 */     return (BigDecimal)query.getSingleResult();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void actualizarCuentaPorCobrar(CuentaPorCobrar cuentaPorCobrar, BigDecimal valor)
/*  32:    */   {
/*  33: 61 */     valor = FuncionesUtiles.redondearBigDecimal(valor, 2);
/*  34:    */     
/*  35: 63 */     StringBuilder sql = new StringBuilder();
/*  36: 64 */     sql.append(" UPDATE CuentaPorCobrar c SET saldo=saldo-:valor ");
/*  37: 68 */     if (cuentaPorCobrar.getSaldo().subtract(valor).compareTo(BigDecimal.ZERO) == 0)
/*  38:    */     {
/*  39: 69 */       sql.append(", fechaCancelacion= :fechaCancelacion ");
/*  40: 70 */       sql.append(", diasRotacion= :diasRotacion ");
/*  41:    */     }
/*  42:    */     else
/*  43:    */     {
/*  44: 72 */       sql.append(", fechaCancelacion=null ");
/*  45:    */     }
/*  46: 74 */     sql.append(" WHERE c.idCuentaPorCobrar=:idCuentaPorCobrar ");
/*  47:    */     
/*  48: 76 */     Query query = this.em.createQuery(sql.toString());
/*  49: 77 */     query.setParameter("idCuentaPorCobrar", Integer.valueOf(cuentaPorCobrar.getIdCuentaPorCobrar()));
/*  50: 78 */     query.setParameter("valor", valor);
/*  51: 81 */     if (cuentaPorCobrar.getSaldo().subtract(valor).compareTo(BigDecimal.ZERO) == 0)
/*  52:    */     {
/*  53: 82 */       Date fechaFactura = cuentaPorCobrar.getFacturaCliente().getFecha();
/*  54: 83 */       Date fechaCancelacion = cuentaPorCobrar.getTraFechaPago();
/*  55: 84 */       int diasRotacion = FuncionesUtiles.diferenciasDeFechas(fechaFactura, fechaCancelacion) - 1;
/*  56: 85 */       query.setParameter("fechaCancelacion", fechaCancelacion);
/*  57: 86 */       query.setParameter("diasRotacion", Integer.valueOf(diasRotacion));
/*  58:    */     }
/*  59: 88 */     query.executeUpdate();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void actualizarCuentasPorCobrar(Empresa empresa)
/*  63:    */   {
/*  64: 94 */     StringBuilder sql = new StringBuilder();
/*  65: 95 */     sql.append("UPDATE CuentaPorCobrar cxc");
/*  66: 96 */     sql.append(" SET saldo=");
/*  67: 97 */     sql.append(" (SELECT COALESCE(SUM(COALESCE(v.debito,0)-COALESCE(v.credito,0)),0)");
/*  68: 98 */     sql.append(" FROM VEstadoCuenta v");
/*  69: 99 */     sql.append(" WHERE v.idCuentaPorCobrar=cxc.idCuentaPorCobrar)");
/*  70:100 */     if (empresa != null) {
/*  71:101 */       sql.append(" AND v.idEmpresa=:idEmpresa)");
/*  72:    */     }
/*  73:103 */     Query query = this.em.createQuery(sql.toString());
/*  74:104 */     if (empresa != null) {
/*  75:105 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/*  76:    */     }
/*  77:107 */     query.executeUpdate();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Cliente obtenerCliente(CuentaPorCobrar cuentaPorCobrar)
/*  81:    */   {
/*  82:117 */     Query query = this.em.createQuery("SELECT c.facturaCliente.empresa.cliente FROM CuentaPorCobrar c WHERE c.idCuentaPorCobrar=:idCuentaPorCobrar");
/*  83:118 */     query.setParameter("idCuentaPorCobrar", Integer.valueOf(cuentaPorCobrar.getIdCuentaPorCobrar()));
/*  84:119 */     return (Cliente)query.getSingleResult();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public BigDecimal valorBloqueado(int idFacturaCliente)
/*  88:    */   {
/*  89:124 */     StringBuilder sql = new StringBuilder();
/*  90:125 */     sql.append(" SELECT SUM(cpc.valorBloqueado) ");
/*  91:126 */     sql.append(" FROM CuentaPorCobrar cpc ");
/*  92:127 */     sql.append(" INNER JOIN cpc.facturaCliente fc ");
/*  93:128 */     sql.append(" WHERE fc.idFacturaCliente =:idFacturaCliente ");
/*  94:    */     
/*  95:130 */     Query query = this.em.createQuery(sql.toString());
/*  96:131 */     query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
/*  97:132 */     return (BigDecimal)query.getSingleResult();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public CuentaPorCobrar cargarDetalle(int idCuentaPorCobrar)
/* 101:    */   {
/* 102:138 */     CuentaPorCobrar cuentaPorCobrar = buscarPorId(Integer.valueOf(idCuentaPorCobrar));
/* 103:139 */     return cuentaPorCobrar;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<CuentaPorCobrar> buscarCuentaPorCobrarPorNumeroFacturaCliente(int idOrganizacion, int idEmpresa, String numeroFacturaCliente)
/* 107:    */   {
/* 108:144 */     StringBuilder sql = new StringBuilder();
/* 109:145 */     sql.append(" SELECT cpc ");
/* 110:146 */     sql.append(" FROM CuentaPorCobrar cpc ");
/* 111:147 */     sql.append(" JOIN FETCH cpc.facturaCliente fc ");
/* 112:148 */     sql.append(" INNER JOIN fc.empresa em ");
/* 113:149 */     sql.append(" WHERE fc.numero =:numeroFacturaCliente ");
/* 114:150 */     sql.append(" AND fc.idOrganizacion =:idOrganizacion ");
/* 115:151 */     sql.append(" AND em.idEmpresa =:idEmpresa ");
/* 116:152 */     sql.append(" AND cpc.saldo > 0 ");
/* 117:153 */     sql.append(" ORDER BY cpc.numeroCuota ASC ");
/* 118:    */     
/* 119:155 */     Query query = this.em.createQuery(sql.toString());
/* 120:156 */     query.setParameter("numeroFacturaCliente", numeroFacturaCliente);
/* 121:157 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 122:158 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 123:    */     
/* 124:160 */     return query.getResultList();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public CuentaPorCobrar buscarPorId(Object idCuentaPorCobrar)
/* 128:    */   {
/* 129:167 */     StringBuilder sql = new StringBuilder();
/* 130:168 */     sql.append(" SELECT cxc FROM CuentaPorCobrar cxc ");
/* 131:169 */     sql.append(" JOIN FETCH cxc.facturaCliente f ");
/* 132:170 */     sql.append(" JOIN FETCH f.sucursal s ");
/* 133:171 */     sql.append(" WHERE cxc.idCuentaPorCobrar = :idCuentaPorCobrar ");
/* 134:    */     
/* 135:173 */     Query query = this.em.createQuery(sql.toString());
/* 136:174 */     query.setParameter("idCuentaPorCobrar", idCuentaPorCobrar);
/* 137:    */     
/* 138:176 */     return (CuentaPorCobrar)query.getSingleResult();
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CuentaPorCobrarDao
 * JD-Core Version:    0.7.0.1
 */