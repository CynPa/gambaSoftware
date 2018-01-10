/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Provincia;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import javax.ejb.Stateless;
/*   8:    */ import javax.persistence.EntityManager;
/*   9:    */ import javax.persistence.TypedQuery;
/*  10:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  11:    */ import javax.persistence.criteria.CriteriaQuery;
/*  12:    */ import javax.persistence.criteria.Join;
/*  13:    */ import javax.persistence.criteria.Path;
/*  14:    */ import javax.persistence.criteria.Root;
/*  15:    */ import javax.persistence.criteria.Selection;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ProvinciaDao
/*  19:    */   extends AbstractDaoAS2<Provincia>
/*  20:    */ {
/*  21:    */   public ProvinciaDao()
/*  22:    */   {
/*  23: 40 */     super(Provincia.class);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Provincia cargarDetalle(int id)
/*  27:    */   {
/*  28: 50 */     Provincia provincia = (Provincia)buscarPorId(Integer.valueOf(id));
/*  29: 51 */     provincia.getListaCiudad().size();
/*  30: 52 */     return provincia;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<Provincia> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 63 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  36: 64 */     CriteriaQuery<Provincia> criteriaQuery = criteriaBuilder.createQuery(Provincia.class);
/*  37: 65 */     Root<Provincia> from = criteriaQuery.from(Provincia.class);
/*  38:    */     
/*  39: 67 */     Path<Integer> pathIdProvincia = from.get("idProvincia");
/*  40: 68 */     Path<String> pathCodigo = from.get("codigo");
/*  41: 69 */     Path<String> pathNombre = from.get("nombre");
/*  42:    */     
/*  43: 71 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  44:    */     
/*  45: 73 */     CriteriaQuery<Provincia> select = criteriaQuery.multiselect(new Selection[] { pathIdProvincia, pathCodigo, pathNombre });
/*  46: 74 */     TypedQuery<Provincia> typedQuery = this.em.createQuery(select);
/*  47:    */     
/*  48: 76 */     return typedQuery.getResultList();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<Provincia> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters, int idPais)
/*  52:    */   {
/*  53: 81 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  54: 82 */     CriteriaQuery<Provincia> criteriaQuery = criteriaBuilder.createQuery(Provincia.class);
/*  55: 83 */     Root<Provincia> from = criteriaQuery.from(Provincia.class);
/*  56:    */     
/*  57: 85 */     Path<Integer> pathIdProvincia = from.get("idProvincia");
/*  58: 86 */     Path<String> pathCodigo = from.get("codigo");
/*  59: 87 */     Path<String> pathNombre = from.get("nombre");
/*  60:    */     
/*  61: 89 */     Path<Integer> pathIdPais = from.join("pais").get("idPais");
/*  62: 90 */     criteriaQuery.where(criteriaBuilder.equal(pathIdPais, Integer.valueOf(idPais)));
/*  63: 91 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  64:    */     
/*  65: 93 */     CriteriaQuery<Provincia> select = criteriaQuery.multiselect(new Selection[] { pathIdProvincia, pathCodigo, pathNombre });
/*  66:    */     
/*  67: 95 */     TypedQuery<Provincia> typedQuery = this.em.createQuery(select);
/*  68:    */     
/*  69: 97 */     return typedQuery.getResultList();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Provincia buscarPorNombre(String nombre)
/*  73:    */   {
/*  74:103 */     Map<String, String> filters = new HashMap();
/*  75:104 */     filters.put("nombre", nombre);
/*  76:    */     
/*  77:106 */     List<Provincia> listaProvincias = obtenerListaCombo("nombre", false, filters);
/*  78:108 */     if (listaProvincias.size() > 0) {
/*  79:109 */       return (Provincia)listaProvincias.get(0);
/*  80:    */     }
/*  81:111 */     return null;
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ProvinciaDao
 * JD-Core Version:    0.7.0.1
 */