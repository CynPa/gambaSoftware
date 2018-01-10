/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Empresa;
/*  4:   */ import com.asinfo.as2.entities.TipoVehiculo;
/*  5:   */ import com.asinfo.as2.entities.Transportista;
/*  6:   */ import com.asinfo.as2.entities.Vehiculo;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ import javax.persistence.EntityManager;
/* 11:   */ import javax.persistence.Query;
/* 12:   */ import javax.persistence.TypedQuery;
/* 13:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 14:   */ import javax.persistence.criteria.CriteriaQuery;
/* 15:   */ import javax.persistence.criteria.Expression;
/* 16:   */ import javax.persistence.criteria.Fetch;
/* 17:   */ import javax.persistence.criteria.JoinType;
/* 18:   */ import javax.persistence.criteria.Predicate;
/* 19:   */ import javax.persistence.criteria.Root;
/* 20:   */ 
/* 21:   */ @Stateless
/* 22:   */ public class VehiculoDao
/* 23:   */   extends AbstractDaoAS2<Vehiculo>
/* 24:   */ {
/* 25:   */   public VehiculoDao()
/* 26:   */   {
/* 27:21 */     super(Vehiculo.class);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public List<Vehiculo> obtenerListaVehiculoPorTransportista(int idTransportista)
/* 31:   */   {
/* 32:27 */     Query query = this.em.createQuery("SELECT new Vehiculo(v.idVehiculo,v.codigo,v.marca,v.placa,v.anio)  FROM Vehiculo v  INNER JOIN v.transportista t  WHERE t.idTransportista = :idTransportista ");
/* 33:   */     
/* 34:   */ 
/* 35:30 */     query.setParameter("idTransportista", Integer.valueOf(idTransportista));
/* 36:   */     
/* 37:32 */     return query.getResultList();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public List<Vehiculo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 41:   */   {
/* 42:38 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 43:39 */     CriteriaQuery<Vehiculo> criteriaQuery = criteriaBuilder.createQuery(Vehiculo.class);
/* 44:   */     
/* 45:41 */     Root<Vehiculo> from = criteriaQuery.from(Vehiculo.class);
/* 46:42 */     from.fetch("tipoVehiculo", JoinType.LEFT);
/* 47:43 */     from.fetch("transportista", JoinType.LEFT);
/* 48:   */     
/* 49:45 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 50:46 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 51:47 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 52:   */     
/* 53:49 */     CriteriaQuery<Vehiculo> select = criteriaQuery.select(from);
/* 54:50 */     TypedQuery<Vehiculo> typedQuery = this.em.createQuery(select);
/* 55:   */     
/* 56:52 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 57:   */     
/* 58:54 */     return typedQuery.getResultList();
/* 59:   */   }
/* 60:   */   
/* 61:   */   public List<Vehiculo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 62:   */   {
/* 63:64 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 64:65 */     CriteriaQuery<Vehiculo> criteriaQuery = criteriaBuilder.createQuery(Vehiculo.class);
/* 65:66 */     Root<Vehiculo> from = criteriaQuery.from(Vehiculo.class);
/* 66:67 */     from.fetch("tipoVehiculo", JoinType.LEFT);
/* 67:68 */     from.fetch("transportista", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/* 68:   */     
/* 69:70 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 70:71 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 71:   */     
/* 72:73 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 73:   */     
/* 74:75 */     CriteriaQuery<Vehiculo> select = criteriaQuery.select(from);
/* 75:76 */     TypedQuery<Vehiculo> typedQuery = this.em.createQuery(select);
/* 76:   */     
/* 77:78 */     return typedQuery.getResultList();
/* 78:   */   }
/* 79:   */   
/* 80:   */   public Vehiculo cargarDetalle(int idVehiculo)
/* 81:   */   {
/* 82:83 */     Vehiculo vehiculo = (Vehiculo)buscarPorId(Integer.valueOf(idVehiculo));
/* 83:84 */     vehiculo.getTipoVehiculo().getId();
/* 84:85 */     vehiculo.getTransportista().getId();
/* 85:86 */     if (vehiculo.getTransportista().getEmpresa() != null) {
/* 86:87 */       vehiculo.getTransportista().getEmpresa().getId();
/* 87:   */     }
/* 88:89 */     return vehiculo;
/* 89:   */   }
/* 90:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.VehiculoDao
 * JD-Core Version:    0.7.0.1
 */