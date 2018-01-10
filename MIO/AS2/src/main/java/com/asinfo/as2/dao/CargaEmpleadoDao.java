/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CargaEmpleado;
/*   4:    */ import com.asinfo.as2.enumeraciones.Parentezco;
/*   5:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.Query;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Expression;
/*  16:    */ import javax.persistence.criteria.Fetch;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Predicate;
/*  19:    */ import javax.persistence.criteria.Root;
/*  20:    */ import org.omg.CORBA.Object;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class CargaEmpleadoDao
/*  24:    */   extends AbstractDaoAS2<CargaEmpleado>
/*  25:    */ {
/*  26:    */   public CargaEmpleadoDao()
/*  27:    */   {
/*  28: 47 */     super(CargaEmpleado.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<CargaEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  32:    */   {
/*  33: 57 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  34: 58 */     CriteriaQuery<CargaEmpleado> criteriaQuery = criteriaBuilder.createQuery(CargaEmpleado.class);
/*  35: 59 */     Root<CargaEmpleado> from = criteriaQuery.from(CargaEmpleado.class);
/*  36: 60 */     from.fetch("tipoDiscapacidad", JoinType.LEFT);
/*  37: 61 */     Fetch<Object, Object> empleado = from.fetch("empleado", JoinType.LEFT);
/*  38: 62 */     empleado.fetch("empresa", JoinType.LEFT);
/*  39:    */     
/*  40: 64 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  41: 65 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  42: 66 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  43:    */     
/*  44: 68 */     CriteriaQuery<CargaEmpleado> select = criteriaQuery.select(from);
/*  45: 69 */     TypedQuery<CargaEmpleado> typedQuery = this.em.createQuery(select);
/*  46: 70 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  47:    */     
/*  48: 72 */     return typedQuery.getResultList();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public CargaEmpleado cargarDetalle(int idCargaEmpleado)
/*  52:    */   {
/*  53: 83 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void actualizaIndicadorActivo(int idCargaEmpleado, boolean indicador)
/*  57:    */   {
/*  58: 88 */     StringBuilder sql = new StringBuilder();
/*  59: 89 */     sql.append(" UPDATE CargaEmpleado ce SET ce.activo =:indicador  ");
/*  60: 90 */     sql.append(" WHERE ce.idCargaEmpleado =:idCargaEmpleado ");
/*  61: 91 */     Query query = this.em.createQuery(sql.toString());
/*  62: 92 */     query.setParameter("indicador", Boolean.valueOf(indicador));
/*  63: 93 */     query.setParameter("idCargaEmpleado", Integer.valueOf(idCargaEmpleado));
/*  64: 94 */     query.executeUpdate();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void actualizaCargasActivas(int idOrganizacion, Date fecha)
/*  68:    */   {
/*  69: 99 */     StringBuilder sql = new StringBuilder();
/*  70:100 */     sql.append(" UPDATE CargaEmpleado ce SET ce.activo = true ");
/*  71:101 */     sql.append(" WHERE (  ");
/*  72:102 */     sql.append("\t\t\t(  ");
/*  73:103 */     sql.append("\t\t\t\tce.parentezco = :parentezcoHijo AND :anio - year(fechaNacimiento) <= 17 OR (:anio - year(fechaNacimiento) = 18 AND :mes < month(fechaNacimiento)) ");
/*  74:104 */     sql.append("\t\t\t)\tOR tipoDiscapacidad IS NOT NULL ");
/*  75:105 */     sql.append("\t\t)");
/*  76:106 */     sql.append(" AND ce.idOrganizacion = :idOrganizacion ");
/*  77:107 */     Query query = this.em.createQuery(sql.toString());
/*  78:108 */     query.setParameter("parentezcoHijo", Parentezco.HIJO);
/*  79:109 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  80:110 */     query.setParameter("anio", Integer.valueOf(FuncionesUtiles.getAnio(fecha)));
/*  81:111 */     query.setParameter("mes", Integer.valueOf(FuncionesUtiles.getMes(fecha)));
/*  82:112 */     query.executeUpdate();
/*  83:    */     
/*  84:114 */     sql = new StringBuilder();
/*  85:115 */     sql.append(" UPDATE CargaEmpleado ce SET ce.activo = false ");
/*  86:116 */     sql.append(" WHERE ce.parentezco = :parentezcoHijo ");
/*  87:117 */     sql.append(" AND (:anio - year(fechaNacimiento) = 18 AND :mes >= month(fechaNacimiento) ");
/*  88:118 */     sql.append(" OR :anio - year(fechaNacimiento) > 18 AND tipoDiscapacidad IS NULL)");
/*  89:119 */     sql.append(" AND ce.idOrganizacion = :idOrganizacion ");
/*  90:120 */     query = this.em.createQuery(sql.toString());
/*  91:121 */     query.setParameter("parentezcoHijo", Parentezco.HIJO);
/*  92:122 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  93:123 */     query.setParameter("anio", Integer.valueOf(FuncionesUtiles.getAnio(fecha)));
/*  94:124 */     query.setParameter("mes", Integer.valueOf(FuncionesUtiles.getMes(fecha)));
/*  95:125 */     query.executeUpdate();
/*  96:    */     
/*  97:127 */     sql = new StringBuilder();
/*  98:128 */     sql.append(" UPDATE Empleado e SET e.numeroCargasActivas = ");
/*  99:129 */     sql.append(" ( ");
/* 100:130 */     sql.append(" \tSELECT COUNT(*) ");
/* 101:131 */     sql.append(" \tFROM CargaEmpleado ce ");
/* 102:132 */     sql.append(" \tWHERE ce.empleado.idEmpleado = e.idEmpleado ");
/* 103:133 */     sql.append(" \tAND ce.activo = true ");
/* 104:134 */     sql.append(" ) ");
/* 105:135 */     sql.append(" WHERE e.idOrganizacion = :idOrganizacion ");
/* 106:136 */     query = this.em.createQuery(sql.toString());
/* 107:137 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 108:138 */     query.executeUpdate();
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CargaEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */