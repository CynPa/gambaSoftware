/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Unidad;
/*   4:    */ import com.asinfo.as2.entities.UnidadConversion;
/*   5:    */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import java.util.ArrayList;
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
/*  22:    */ public class UnidadDao
/*  23:    */   extends AbstractDaoAS2<Unidad>
/*  24:    */ {
/*  25:    */   public UnidadDao()
/*  26:    */   {
/*  27: 41 */     super(Unidad.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public List<Unidad> listaUnidadCombo(String consulta, TipoUnidadMedida tipoUnidadMedida, int idUnidad)
/*  31:    */   {
/*  32: 54 */     String sql = "SELECT new Unidad(u.idUnidad, u.codigo, u.nombre, u.activo) FROM Unidad u  WHERE u.activo = true AND u.nombre LIKE :consulta AND u.tipoUnidadMedida = :tipoUnidadMedida";
/*  33: 56 */     if (idUnidad != 0) {
/*  34: 57 */       sql = sql + " AND u.idUnidad <> :idUnidad";
/*  35:    */     }
/*  36: 59 */     Query query = this.em.createQuery(sql).setParameter("consulta", consulta + "%").setParameter("tipoUnidadMedida", tipoUnidadMedida);
/*  37: 60 */     if (idUnidad != 0) {
/*  38: 61 */       query = query.setParameter("idUnidad", Integer.valueOf(idUnidad));
/*  39:    */     }
/*  40: 63 */     return query.getResultList();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Unidad cargarDetalle(int idUnidad)
/*  44:    */   {
/*  45: 74 */     Unidad unidad = (Unidad)buscarPorId(Integer.valueOf(idUnidad));
/*  46: 75 */     Query query = this.em.createQuery(" SELECT uc  FROM UnidadConversion uc  INNER JOIN uc.unidadOrigen u  WHERE u.idUnidad = :idUnidad  AND uc.subcategoriaProducto IS NULL  AND uc.producto IS NULL");
/*  47:    */     
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52: 81 */     query.setParameter("idUnidad", Integer.valueOf(idUnidad));
/*  53: 82 */     List<UnidadConversion> listaUnidadConversion = query.getResultList();
/*  54: 83 */     if (!listaUnidadConversion.isEmpty()) {
/*  55: 84 */       unidad.setListaUnidadConversion(listaUnidadConversion);
/*  56:    */     } else {
/*  57: 87 */       unidad.setListaUnidadConversion(new ArrayList());
/*  58:    */     }
/*  59: 89 */     return unidad;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<Unidad> obtenerListaUnidadPorUnidadOrigen(int idUnidadOrigen)
/*  63:    */   {
/*  64: 94 */     Query query = this.em.createQuery(" SELECT uc.unidadDestino  FROM UnidadConversion uc  INNER JOIN uc.unidadOrigen u  WHERE u.idUnidad = :idUnidadOrigen ");
/*  65:    */     
/*  66:    */ 
/*  67:    */ 
/*  68: 98 */     query.setParameter("idUnidadOrigen", Integer.valueOf(idUnidadOrigen));
/*  69: 99 */     return query.getResultList();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Unidad buscarUnidad(Map<String, String> filters)
/*  73:    */     throws ExcepcionAS2
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:113 */       CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  78:114 */       CriteriaQuery<Unidad> cq = cb.createQuery(Unidad.class);
/*  79:115 */       Root<Unidad> from = cq.from(Unidad.class);
/*  80:    */       
/*  81:117 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, cb, from);
/*  82:118 */       cq.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  83:    */       
/*  84:120 */       CriteriaQuery<Unidad> select = cq.select(from);
/*  85:121 */       return cargarDetalle(((Unidad)this.em.createQuery(select).getSingleResult()).getId());
/*  86:    */     }
/*  87:    */     catch (NoResultException e)
/*  88:    */     {
/*  89:124 */       throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + (String)filters.get("nombre"));
/*  90:    */     }
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.UnidadDao
 * JD-Core Version:    0.7.0.1
 */