/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Cliente;
/*   4:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   5:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Subempresa;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import java.math.BigDecimal;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.NoResultException;
/*  16:    */ import javax.persistence.Query;
/*  17:    */ import javax.persistence.TypedQuery;
/*  18:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  19:    */ import javax.persistence.criteria.CriteriaQuery;
/*  20:    */ import javax.persistence.criteria.Expression;
/*  21:    */ import javax.persistence.criteria.Fetch;
/*  22:    */ import javax.persistence.criteria.JoinType;
/*  23:    */ import javax.persistence.criteria.Path;
/*  24:    */ import javax.persistence.criteria.Predicate;
/*  25:    */ import javax.persistence.criteria.Root;
/*  26:    */ 
/*  27:    */ @Stateless
/*  28:    */ public class ClienteDao
/*  29:    */   extends AbstractDaoAS2<Cliente>
/*  30:    */ {
/*  31:    */   public ClienteDao()
/*  32:    */   {
/*  33: 45 */     super(Cliente.class);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Long verificaClienteListaPrecios(int idEmpresa)
/*  37:    */   {
/*  38: 55 */     String sql = "SELECT COUNT(c) FROM Cliente c  INNER JOIN c.empresa e INNER JOIN c.listaPrecios lp  WHERE e.idEmpresa = :idEmpresa";
/*  39:    */     
/*  40: 57 */     Query query = this.em.createQuery(sql).setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  41:    */     
/*  42: 59 */     return (Long)query.getSingleResult();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean verificaExcentaImpuestos(int idEmpresa)
/*  46:    */   {
/*  47: 69 */     String sql = "SELECT c.excentoImpuestos FROM Cliente c  INNER JOIN c.empresa e  WHERE e.idEmpresa = :idEmpresa";
/*  48:    */     
/*  49: 71 */     Query query = this.em.createQuery(sql).setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  50: 72 */     boolean dato = ((Boolean)query.getSingleResult()).booleanValue();
/*  51:    */     
/*  52: 74 */     return dato;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public DireccionEmpresa obtieneDireccionPrincipal(int idEmpresa)
/*  56:    */     throws ExcepcionAS2
/*  57:    */   {
/*  58: 86 */     String sql = "SELECT d FROM DireccionEmpresa d WHERE d.empresa.idEmpresa = :idEmpresa AND d.indicadorDireccionPrincipal = true";
/*  59:    */     
/*  60: 88 */     Query query = this.em.createQuery(sql).setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  61:    */     
/*  62: 90 */     List<DireccionEmpresa> lista = query.getResultList();
/*  63: 92 */     if (lista.isEmpty()) {
/*  64: 93 */       throw new ExcepcionAS2("msg_error_direccion_principal_no_lista_direcciones");
/*  65:    */     }
/*  66: 96 */     return (DireccionEmpresa)lista.get(0);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void actualizarCreditoUtilizado(Cliente cliente, BigDecimal valor)
/*  70:    */   {
/*  71:106 */     Query query = this.em.createQuery("UPDATE Cliente c SET creditoUtilizado=creditoUtilizado+:valor WHERE c.idCliente=:idCliente");
/*  72:107 */     query.setParameter("idCliente", Integer.valueOf(cliente.getIdCliente()));
/*  73:108 */     query.setParameter("valor", valor);
/*  74:109 */     query.executeUpdate();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public BigDecimal getCreditoDisponible(Cliente cliente)
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:120 */       Query query = this.em.createQuery("SELECT c.creditoMaximo-c.creditoUtilizado FROM Cliente c WHERE c.idCliente=:idCliente");
/*  82:121 */       query.setParameter("idCliente", Integer.valueOf(cliente.getIdCliente()));
/*  83:    */       
/*  84:123 */       return (BigDecimal)query.getSingleResult();
/*  85:    */     }
/*  86:    */     catch (NoResultException e) {}
/*  87:126 */     return BigDecimal.ZERO;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List<Cliente> buscarClientesPorIdUsuarioTransportistaOAgenteComercial(int idUsuario)
/*  91:    */   {
/*  92:134 */     StringBuilder sql = new StringBuilder();
/*  93:135 */     sql.append(" SELECT c ");
/*  94:136 */     sql.append(" FROM Cliente c ");
/*  95:137 */     sql.append(" INNER JOIN FETCH c.empresa e ");
/*  96:138 */     sql.append(" LEFT JOIN FETCH c.listaPrecios lp ");
/*  97:139 */     sql.append(" INNER JOIN FETCH e.tipoIdentificacion ti ");
/*  98:140 */     sql.append(" LEFT JOIN c.transportista tra ");
/*  99:141 */     sql.append(" LEFT JOIN tra.usuario ut ");
/* 100:142 */     sql.append(" LEFT JOIN c.agenteComercial ac ");
/* 101:143 */     sql.append(" WHERE (ut.idUsuario = :idUsuario");
/* 102:144 */     sql.append(" OR ac.idUsuario = :idUsuario) ");
/* 103:145 */     sql.append(" AND e.activo IS TRUE ");
/* 104:    */     
/* 105:147 */     Query query = this.em.createQuery(sql.toString());
/* 106:148 */     query.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 107:149 */     return query.getResultList();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<Cliente> buscarClientesActivos(Integer idOrganizacion)
/* 111:    */   {
/* 112:156 */     StringBuilder sql = new StringBuilder();
/* 113:157 */     sql.append(" SELECT c ");
/* 114:158 */     sql.append(" FROM Cliente c ");
/* 115:159 */     sql.append(" INNER JOIN FETCH c.empresa e ");
/* 116:160 */     sql.append(" LEFT JOIN FETCH c.listaPrecios lp ");
/* 117:161 */     sql.append(" LEFT JOIN FETCH c.listaDescuentos ");
/* 118:162 */     sql.append(" INNER JOIN FETCH e.tipoIdentificacion ti ");
/* 119:163 */     sql.append(" LEFT JOIN c.transportista tra ");
/* 120:164 */     sql.append(" LEFT JOIN tra.usuario ut ");
/* 121:165 */     sql.append(" LEFT JOIN c.agenteComercial ac ");
/* 122:166 */     sql.append(" WHERE e.idOrganizacion =:idOrganizacion ");
/* 123:167 */     sql.append(" AND e.activo IS TRUE ");
/* 124:    */     
/* 125:169 */     Query query = this.em.createQuery(sql.toString());
/* 126:170 */     query.setParameter("idOrganizacion", idOrganizacion);
/* 127:171 */     return query.getResultList();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<Subempresa> obtenerListaSubempresaBySubcliente(int idEmpresaSubcliente)
/* 131:    */   {
/* 132:178 */     StringBuilder sql = new StringBuilder();
/* 133:179 */     sql.append(" SELECT se FROM Subempresa se ");
/* 134:180 */     sql.append(" INNER JOIN FETCH se.empresa e ");
/* 135:181 */     sql.append(" INNER JOIN FETCH se.empresaPadre ep ");
/* 136:182 */     sql.append(" INNER JOIN FETCH ep.cliente cl ");
/* 137:183 */     sql.append(" WHERE e.idEmpresa=:idEmpresa ");
/* 138:    */     
/* 139:185 */     Query query = this.em.createQuery(sql.toString());
/* 140:186 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresaSubcliente));
/* 141:    */     
/* 142:188 */     return query.getResultList();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Cliente cargarDetalle(int idCliente)
/* 146:    */   {
/* 147:193 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 148:    */     
/* 149:    */ 
/* 150:196 */     CriteriaQuery<Cliente> cqCabecera = criteriaBuilder.createQuery(Cliente.class);
/* 151:197 */     Root<Cliente> fromCabecera = cqCabecera.from(Cliente.class);
/* 152:    */     
/* 153:199 */     Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.INNER);
/* 154:200 */     empresa.fetch("tipoIdentificacion", JoinType.INNER);
/* 155:201 */     fromCabecera.fetch("listaPrecios", JoinType.LEFT);
/* 156:    */     
/* 157:203 */     Path<Integer> pathId = fromCabecera.get("idCliente");
/* 158:204 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idCliente)));
/* 159:205 */     CriteriaQuery<Cliente> selectDespacho = cqCabecera.select(fromCabecera);
/* 160:    */     
/* 161:207 */     Cliente cliente = (Cliente)this.em.createQuery(selectDespacho).getSingleResult();
/* 162:    */     
/* 163:209 */     return cliente;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Integer actualizarNumeroFacturasPendientesSinGarantia(Cliente cliente)
/* 167:    */   {
/* 168:214 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 169:    */     
/* 170:    */ 
/* 171:217 */     CriteriaQuery<Long> cqCabecera = criteriaBuilder.createQuery(Long.class);
/* 172:218 */     Root<CuentaPorCobrar> fromCabecera = cqCabecera.from(CuentaPorCobrar.class);
/* 173:    */     
/* 174:220 */     List<Expression<?>> listaWhere = new ArrayList();
/* 175:221 */     Path<Integer> pathIdEmpresa = fromCabecera.get("facturaCliente").get("empresa").get("idEmpresa");
/* 176:222 */     listaWhere.add(criteriaBuilder.equal(pathIdEmpresa, Integer.valueOf(cliente.getEmpresa().getId())));
/* 177:223 */     Path<Integer> pathCantidadGarantias = fromCabecera.get("facturaCliente").get("cantidadGarantias");
/* 178:224 */     listaWhere.add(criteriaBuilder.equal(pathCantidadGarantias, Integer.valueOf(0)));
/* 179:225 */     Path<BigDecimal> pathSaldo = fromCabecera.get("saldo");
/* 180:226 */     Path<BigDecimal> pathValorBloqueado = fromCabecera.get("valorBloqueado");
/* 181:227 */     listaWhere.add(criteriaBuilder.greaterThan(criteriaBuilder.diff(pathSaldo, pathValorBloqueado), BigDecimal.ZERO));
/* 182:228 */     Path<Estado> pathEstado = fromCabecera.get("facturaCliente").get("estado");
/* 183:229 */     listaWhere.add(criteriaBuilder.notEqual(pathEstado, Estado.ANULADO));
/* 184:230 */     Path<Boolean> pathIndicadorAnulada = fromCabecera.get("indicadorAnulada");
/* 185:231 */     listaWhere.add(criteriaBuilder.equal(pathIndicadorAnulada, Boolean.valueOf(false)));
/* 186:    */     
/* 187:233 */     cqCabecera.where((Predicate[])listaWhere.toArray(new Predicate[listaWhere.size()]));
/* 188:    */     
/* 189:235 */     CriteriaQuery<Long> select = cqCabecera.select(criteriaBuilder.countDistinct(fromCabecera.get("facturaCliente").get("idFacturaCliente")));
/* 190:    */     
/* 191:237 */     Long count = null;
/* 192:    */     try
/* 193:    */     {
/* 194:239 */       count = (Long)this.em.createQuery(select).getSingleResult();
/* 195:    */     }
/* 196:    */     catch (NoResultException e)
/* 197:    */     {
/* 198:241 */       count = Long.valueOf(0L);
/* 199:    */     }
/* 200:244 */     int numeroFacturasSinGarantia = 0;
/* 201:245 */     if (count != null) {
/* 202:246 */       numeroFacturasSinGarantia = count.intValue();
/* 203:    */     }
/* 204:249 */     Query queryUpdate = this.em.createQuery("UPDATE Cliente c SET cantidadFacturasPendientesSinGarantia=:valor WHERE c.idCliente=:idCliente");
/* 205:250 */     queryUpdate.setParameter("idCliente", Integer.valueOf(cliente.getIdCliente()));
/* 206:251 */     queryUpdate.setParameter("valor", Integer.valueOf(numeroFacturasSinGarantia));
/* 207:252 */     queryUpdate.executeUpdate();
/* 208:253 */     return Integer.valueOf(numeroFacturasSinGarantia);
/* 209:    */   }
/* 210:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ClienteDao
 * JD-Core Version:    0.7.0.1
 */