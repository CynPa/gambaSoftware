/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empresa;
/*   4:    */ import com.asinfo.as2.entities.Subempresa;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.ejb.Stateless;
/*   7:    */ import javax.persistence.EntityManager;
/*   8:    */ import javax.persistence.NoResultException;
/*   9:    */ import javax.persistence.Query;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class SubempresaDao
/*  13:    */   extends AbstractDaoAS2<Subempresa>
/*  14:    */ {
/*  15:    */   public SubempresaDao()
/*  16:    */   {
/*  17: 35 */     super(Subempresa.class);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public List<Subempresa> obtenerListaComboSubEmpresa(int idEmpresa, boolean activo)
/*  21:    */   {
/*  22: 41 */     StringBuilder sql = new StringBuilder();
/*  23: 42 */     sql.append(" SELECT se FROM Subempresa se ");
/*  24: 43 */     sql.append(" JOIN FETCH se.empresa e ");
/*  25: 44 */     sql.append(" JOIN FETCH e.cliente c ");
/*  26: 45 */     sql.append(" LEFT JOIN FETCH c.tipoOrdenDespacho tod ");
/*  27: 46 */     sql.append(" LEFT JOIN FETCH c.zona e ");
/*  28: 47 */     sql.append(" LEFT JOIN FETCH c.condicionPago cp ");
/*  29: 48 */     sql.append(" LEFT JOIN FETCH c.transportista tra ");
/*  30: 49 */     sql.append(" JOIN se.empresaPadre ep ");
/*  31: 50 */     sql.append(" WHERE ep.idEmpresa=:idEmpresa ");
/*  32: 51 */     if (activo) {
/*  33: 52 */       sql.append(" AND se.activo = true ");
/*  34:    */     }
/*  35: 54 */     sql.append(" ORDER BY se.empresaFinal ");
/*  36:    */     
/*  37: 56 */     Query query = this.em.createQuery(sql.toString());
/*  38: 57 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  39:    */     
/*  40: 59 */     return query.getResultList();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<Subempresa> autocompletarSubEmpresa(String consulta, Empresa empresa)
/*  44:    */   {
/*  45: 70 */     StringBuilder sql = new StringBuilder();
/*  46: 71 */     sql.append(" SELECT se FROM Subempresa se ");
/*  47: 72 */     sql.append(" JOIN FETCH se.empresa e ");
/*  48: 74 */     if (empresa != null)
/*  49:    */     {
/*  50: 75 */       sql.append(" WHERE e.idEmpresa=:idEmpresa ");
/*  51: 76 */       sql.append(" AND ( UPPER(se.codigo) like :consulta OR UPPER(se.empresaFinal) like :consulta )");
/*  52: 77 */       sql.append(" ORDER BY se.empresaFinal ");
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 79 */       sql.append(" WHERE (UPPER(se.codigo) like :consulta OR UPPER(se.empresaFinal) like :consulta )");
/*  57: 80 */       sql.append(" ORDER BY se.empresaFinal ");
/*  58:    */     }
/*  59: 83 */     Query query = this.em.createQuery(sql.toString());
/*  60: 84 */     if (empresa != null) {
/*  61: 85 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/*  62:    */     }
/*  63: 87 */     query.setParameter("consulta", "%" + consulta.toUpperCase() + "%");
/*  64:    */     
/*  65: 89 */     return query.getResultList();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Subempresa buscarSubempresaPorCodigo(int idOrganizacion, String codeAgent)
/*  69:    */   {
/*  70: 94 */     StringBuilder sql = new StringBuilder();
/*  71: 95 */     sql.append(" SELECT se FROM Subempresa se ");
/*  72: 96 */     sql.append(" WHERE se.idOrganizacion = :idOrganizacion ");
/*  73: 97 */     sql.append(" AND se.codigo = :codeAgent ");
/*  74:    */     
/*  75: 99 */     Query query = this.em.createQuery(sql.toString());
/*  76:100 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  77:101 */     query.setParameter("codeAgent", codeAgent);
/*  78:    */     try
/*  79:    */     {
/*  80:104 */       return (Subempresa)query.getSingleResult();
/*  81:    */     }
/*  82:    */     catch (NoResultException e) {}
/*  83:106 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Subempresa buscarSubEmpresaPorIdEmpresaAndIdSubCliente(int idEmpresaPadre, int idEmpresaSubcliente)
/*  87:    */   {
/*  88:115 */     StringBuilder sql = new StringBuilder();
/*  89:116 */     sql.append(" SELECT se FROM Subempresa se ");
/*  90:117 */     sql.append(" JOIN FETCH se.empresa e ");
/*  91:118 */     sql.append(" JOIN FETCH e.cliente c ");
/*  92:119 */     sql.append(" LEFT JOIN FETCH c.tipoOrdenDespacho tod ");
/*  93:120 */     sql.append(" LEFT JOIN FETCH c.zona zon ");
/*  94:121 */     sql.append(" LEFT JOIN FETCH c.condicionPago cp ");
/*  95:122 */     sql.append(" LEFT JOIN FETCH c.transportista tra ");
/*  96:123 */     sql.append(" JOIN se.empresaPadre ep ");
/*  97:124 */     sql.append(" WHERE ep.idEmpresa=:idEmpresaPadre ");
/*  98:125 */     sql.append(" AND e.idEmpresa=:idEmpresaSubcliente ");
/*  99:    */     
/* 100:127 */     Query query = this.em.createQuery(sql.toString());
/* 101:128 */     query.setParameter("idEmpresaPadre", Integer.valueOf(idEmpresaPadre));
/* 102:129 */     query.setParameter("idEmpresaSubcliente", Integer.valueOf(idEmpresaSubcliente));
/* 103:    */     
/* 104:131 */     List<Subempresa> lista = query.getResultList();
/* 105:132 */     if (lista.size() > 0) {
/* 106:133 */       return (Subempresa)lista.get(0);
/* 107:    */     }
/* 108:136 */     return null;
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.SubempresaDao
 * JD-Core Version:    0.7.0.1
 */