/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Transportista;
/*   4:    */ import com.asinfo.as2.entities.Zona;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.ejb.Stateless;
/*   8:    */ import javax.persistence.EntityManager;
/*   9:    */ import javax.persistence.NoResultException;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ import javax.persistence.TemporalType;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.JoinType;
/*  16:    */ import javax.persistence.criteria.Path;
/*  17:    */ import javax.persistence.criteria.Root;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class TransportistaDao
/*  21:    */   extends AbstractDaoAS2<Transportista>
/*  22:    */ {
/*  23:    */   public TransportistaDao()
/*  24:    */   {
/*  25: 32 */     super(Transportista.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Transportista verificarTransportista(int idOrganizacion, String nombreUsuario)
/*  29:    */   {
/*  30: 37 */     Transportista transportista = null;
/*  31:    */     
/*  32: 39 */     Query query = this.em.createQuery("SELECT t FROM Transportista t LEFT JOIN t.usuario u WHERE t.idOrganizacion =:idOrganizacion AND u.nombreUsuario = :nombreUsuario");
/*  33: 40 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  34: 41 */     query.setParameter("nombreUsuario", nombreUsuario);
/*  35:    */     try
/*  36:    */     {
/*  37: 44 */       transportista = (Transportista)query.getSingleResult();
/*  38:    */     }
/*  39:    */     catch (NoResultException localNoResultException) {}
/*  40: 49 */     return transportista;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void actuzalizaTransportista(Date fecha, List<Zona> listaZona, Transportista transportista, boolean actualizarTransportista)
/*  44:    */   {
/*  45: 54 */     if (actualizarTransportista)
/*  46:    */     {
/*  47: 56 */       StringBuilder sql2 = new StringBuilder();
/*  48: 57 */       sql2.append("UPDATE Cliente c SET c.transportista = :transportista ");
/*  49: 58 */       sql2.append("WHERE  c.zona in (:listaZona) ");
/*  50: 59 */       Query query = this.em.createQuery(sql2.toString());
/*  51: 60 */       query = this.em.createQuery(sql2.toString());
/*  52: 61 */       query.setParameter("transportista", transportista);
/*  53: 62 */       query.setParameter("listaZona", listaZona);
/*  54: 63 */       query.executeUpdate();
/*  55:    */     }
/*  56:    */     else
/*  57:    */     {
/*  58: 66 */       StringBuilder sql = new StringBuilder();
/*  59: 67 */       sql.append("UPDATE PedidoCliente pc SET pc.transportista = :transportista ");
/*  60: 68 */       sql.append("WHERE  pc.empresa in (SELECT c.empresa FROM Cliente c WHERE c.zona in (:listaZona)) ");
/*  61: 69 */       sql.append("AND CASE WHEN pc.fechaDespacho IS NULL THEN pc.fecha ELSE pc.fechaDespacho END = :fecha ");
/*  62: 70 */       Query query = this.em.createQuery(sql.toString());
/*  63: 71 */       query.setParameter("transportista", transportista);
/*  64: 72 */       query.setParameter("listaZona", listaZona);
/*  65: 73 */       query.setParameter("fecha", fecha, TemporalType.DATE);
/*  66: 74 */       query.executeUpdate();
/*  67:    */       
/*  68: 76 */       StringBuilder sql1 = new StringBuilder();
/*  69: 77 */       sql1.append("UPDATE DespachoCliente dc SET dc.transportista = :transportista ");
/*  70: 78 */       sql1.append("WHERE  dc.empresa in (SELECT c.empresa FROM Cliente c WHERE c.zona in (:listaZona)) ");
/*  71: 79 */       sql1.append("AND dc.fecha = :fecha ");
/*  72: 80 */       query = this.em.createQuery(sql1.toString());
/*  73: 81 */       query.setParameter("transportista", transportista);
/*  74: 82 */       query.setParameter("listaZona", listaZona);
/*  75: 83 */       query.setParameter("fecha", fecha, TemporalType.DATE);
/*  76: 84 */       query.executeUpdate();
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<Zona> obtenerZonaAsignada(int idTransportista)
/*  81:    */   {
/*  82: 90 */     StringBuilder sql = new StringBuilder();
/*  83: 91 */     sql.append("SELECT DISTINCT c.zona FROM Cliente c ");
/*  84: 92 */     sql.append("JOIN c.transportista t ");
/*  85: 93 */     sql.append("WHERE  t.idTransportista = :idTransportista ");
/*  86: 94 */     Query query = this.em.createQuery(sql.toString());
/*  87: 95 */     query.setParameter("idTransportista", Integer.valueOf(idTransportista));
/*  88: 96 */     return query.getResultList();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Transportista cargarDetalle(int idTransportista)
/*  92:    */   {
/*  93:102 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  94:    */     
/*  95:    */ 
/*  96:105 */     CriteriaQuery<Transportista> cqCabecera = criteriaBuilder.createQuery(Transportista.class);
/*  97:106 */     Root<Transportista> fromCabecera = cqCabecera.from(Transportista.class);
/*  98:107 */     fromCabecera.fetch("cliente", JoinType.LEFT);
/*  99:108 */     fromCabecera.fetch("empresa", JoinType.LEFT);
/* 100:    */     
/* 101:110 */     Path<Integer> pathId = fromCabecera.get("idTransportista");
/* 102:111 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idTransportista)));
/* 103:112 */     CriteriaQuery<Transportista> selectTransportista = cqCabecera.select(fromCabecera);
/* 104:    */     
/* 105:114 */     return (Transportista)this.em.createQuery(selectTransportista).getSingleResult();
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TransportistaDao
 * JD-Core Version:    0.7.0.1
 */