/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.PreDevolucionCliente;
/*   4:    */ import com.asinfo.as2.entities.RecepcionDevolucionTransportista;
/*   5:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TemporalType;
/*  15:    */ import javax.persistence.TypedQuery;
/*  16:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  17:    */ import javax.persistence.criteria.CriteriaQuery;
/*  18:    */ import javax.persistence.criteria.Fetch;
/*  19:    */ import javax.persistence.criteria.JoinType;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class RecepcionDevolucionTransportistaDao
/*  24:    */   extends AbstractDaoAS2<RecepcionDevolucionTransportista>
/*  25:    */ {
/*  26:    */   @EJB
/*  27:    */   private PreDevolucionClienteDao devolucionClienteDao;
/*  28:    */   
/*  29:    */   public RecepcionDevolucionTransportistaDao()
/*  30:    */   {
/*  31: 46 */     super(RecepcionDevolucionTransportista.class);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<RecepcionDevolucionTransportista> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  35:    */   {
/*  36: 53 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  37: 54 */     CriteriaQuery<RecepcionDevolucionTransportista> criteriaQuery = criteriaBuilder.createQuery(RecepcionDevolucionTransportista.class);
/*  38: 55 */     Root<RecepcionDevolucionTransportista> from = criteriaQuery.from(RecepcionDevolucionTransportista.class);
/*  39: 56 */     from.fetch("transportista", JoinType.LEFT);
/*  40:    */     
/*  41: 58 */     agregarFiltros(filters, criteriaBuilder, criteriaQuery, from);
/*  42: 59 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  43:    */     
/*  44: 61 */     CriteriaQuery<RecepcionDevolucionTransportista> select = criteriaQuery.select(from);
/*  45: 62 */     TypedQuery<RecepcionDevolucionTransportista> typedQuery = this.em.createQuery(select);
/*  46: 63 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  47:    */     
/*  48: 65 */     return typedQuery.getResultList();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public RecepcionDevolucionTransportista cargarDetalle(int idRecepcionDevolucionTransportista)
/*  52:    */   {
/*  53: 70 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  54: 71 */     CriteriaQuery<RecepcionDevolucionTransportista> cq = cb.createQuery(RecepcionDevolucionTransportista.class);
/*  55:    */     
/*  56: 73 */     Root<RecepcionDevolucionTransportista> from = cq.from(RecepcionDevolucionTransportista.class);
/*  57: 74 */     from.fetch("empresa", JoinType.LEFT);
/*  58: 75 */     from.fetch("subempresa", JoinType.LEFT);
/*  59: 76 */     from.fetch("subempresa", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  60: 77 */     from.fetch("transportista", JoinType.LEFT);
/*  61: 78 */     from.fetch("transportista", JoinType.LEFT).fetch("cliente", JoinType.LEFT);
/*  62: 79 */     from.fetch("sucursal", JoinType.LEFT);
/*  63:    */     
/*  64: 81 */     cq.where(cb.equal(from.get("idRecepcionDevolucionTransportista"), Integer.valueOf(idRecepcionDevolucionTransportista)));
/*  65: 82 */     RecepcionDevolucionTransportista recepcionDevolucionTransportista = (RecepcionDevolucionTransportista)this.em.createQuery(cq.select(from)).getSingleResult();
/*  66:    */     
/*  67: 84 */     this.em.detach(recepcionDevolucionTransportista);
/*  68:    */     
/*  69: 86 */     CriteriaQuery<PreDevolucionCliente> cqPreDevolucionCliente = cb.createQuery(PreDevolucionCliente.class);
/*  70: 87 */     Root<PreDevolucionCliente> detalle = cqPreDevolucionCliente.from(PreDevolucionCliente.class);
/*  71: 88 */     cqPreDevolucionCliente.where(cb.equal(detalle.get("recepcionDevolucionTransportista"), recepcionDevolucionTransportista));
/*  72: 89 */     List<PreDevolucionCliente> listaPreDevolucionCliente = new ArrayList();
/*  73: 92 */     for (PreDevolucionCliente preDevolucionCliente : this.em.createQuery(cqPreDevolucionCliente).getResultList())
/*  74:    */     {
/*  75: 93 */       this.em.detach(preDevolucionCliente);
/*  76: 94 */       preDevolucionCliente = this.devolucionClienteDao.cargarDetalle(preDevolucionCliente.getId());
/*  77: 95 */       preDevolucionCliente.setIndicadorProcesar(true);
/*  78: 96 */       preDevolucionCliente.setRecepcionDevolucionTransportista(recepcionDevolucionTransportista);
/*  79: 97 */       listaPreDevolucionCliente.add(preDevolucionCliente);
/*  80:    */     }
/*  81: 99 */     recepcionDevolucionTransportista.setListaPreDevolucionCliente(listaPreDevolucionCliente);
/*  82:100 */     return recepcionDevolucionTransportista;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<Object[]> getReporteRecepcionDevolucionTransportista(Date fecha, int idTransportista, boolean soloDiferencias)
/*  86:    */   {
/*  87:106 */     String fechaDB = "";
/*  88:107 */     if (soloDiferencias) {
/*  89:108 */       fechaDB = "rdt.fechaProcesamiento";
/*  90:    */     } else {
/*  91:110 */       fechaDB = "rdt.fecha";
/*  92:    */     }
/*  93:111 */     StringBuilder sql = new StringBuilder();
/*  94:112 */     sql.append("SELECT " + fechaDB + ", t.nombre, p.codigo, p.nombre,");
/*  95:113 */     if (soloDiferencias) {
/*  96:114 */       sql.append(" SUM((dpdc.cantidadProcesar-dpdc.cantidadRecibida)), SUM(dpdc.cantidadRecibida*0), c.nombreFiscal,");
/*  97:    */     } else {
/*  98:116 */       sql.append(" SUM(dpdc.cantidadProcesar), SUM(dpdc.cantidadRecibida), '',");
/*  99:    */     }
/* 100:118 */     sql.append(" CASE WHEN rdt.productoBueno = true THEN 'Bueno' ELSE 'Malo' END");
/* 101:119 */     sql.append(" FROM DetallePreDevolucionCliente dpdc");
/* 102:120 */     sql.append(" INNER JOIN dpdc.producto p");
/* 103:121 */     sql.append(" INNER JOIN dpdc.preDevolucionCliente pdc ");
/* 104:122 */     sql.append(" INNER JOIN pdc.recepcionDevolucionTransportista rdt");
/* 105:123 */     sql.append(" INNER JOIN rdt.transportista t");
/* 106:124 */     sql.append(" LEFT JOIN t.cliente c");
/* 107:125 */     sql.append(" WHERE rdt.fecha = :fecha");
/* 108:126 */     sql.append(" AND t.idTransportista=:idTransportista");
/* 109:127 */     sql.append(" AND dpdc.indicadorProcesar = true");
/* 110:128 */     if (soloDiferencias)
/* 111:    */     {
/* 112:129 */       sql.append(" AND (dpdc.cantidadProcesar-dpdc.cantidadRecibida) <> 0");
/* 113:130 */       sql.append(" AND rdt.estado=:estadoProcesado");
/* 114:    */     }
/* 115:132 */     sql.append(" AND pdc.estado!=:estadoAnulado");
/* 116:133 */     sql.append(" GROUP BY " + fechaDB + ", t.nombre, p.codigo, p.nombre, rdt.productoBueno");
/* 117:134 */     if (soloDiferencias) {
/* 118:135 */       sql.append(", c.nombreFiscal");
/* 119:    */     }
/* 120:138 */     sql.append(" ORDER BY rdt.productoBueno, p.nombre");
/* 121:139 */     Query query = this.em.createQuery(sql.toString());
/* 122:140 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 123:141 */     query.setParameter("idTransportista", Integer.valueOf(idTransportista));
/* 124:142 */     if (soloDiferencias) {
/* 125:143 */       query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 126:    */     }
/* 127:145 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 128:    */     
/* 129:147 */     return query.getResultList();
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RecepcionDevolucionTransportistaDao
 * JD-Core Version:    0.7.0.1
 */