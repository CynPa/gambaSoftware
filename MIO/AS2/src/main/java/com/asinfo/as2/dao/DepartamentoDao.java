/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import com.asinfo.as2.entities.Departamento;
/*   6:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoDepartamento;
/*   7:    */ import com.asinfo.as2.entities.Rubro;
/*   8:    */ import com.asinfo.as2.entities.RubroDepartamentoCuentaContable;
/*   9:    */ import com.asinfo.as2.entities.nomina.asistencia.TurnoDepartamento;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ import javax.persistence.TypedQuery;
/*  17:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  18:    */ import javax.persistence.criteria.CriteriaQuery;
/*  19:    */ import javax.persistence.criteria.Fetch;
/*  20:    */ import javax.persistence.criteria.JoinType;
/*  21:    */ import javax.persistence.criteria.Order;
/*  22:    */ import javax.persistence.criteria.Root;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class DepartamentoDao
/*  26:    */   extends AbstractDaoAS2<Departamento>
/*  27:    */ {
/*  28:    */   public DepartamentoDao()
/*  29:    */   {
/*  30: 46 */     super(Departamento.class);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Departamento cargarDetalle(int idDepartamento)
/*  34:    */   {
/*  35: 57 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  36:    */     
/*  37:    */ 
/*  38: 60 */     CriteriaQuery<Departamento> cqCabecera = criteriaBuilder.createQuery(Departamento.class);
/*  39: 61 */     Root<Departamento> fromCabecera = cqCabecera.from(Departamento.class);
/*  40: 62 */     fromCabecera.fetch("supervisor", JoinType.LEFT);
/*  41: 63 */     fromCabecera.fetch("supervisor2", JoinType.LEFT);
/*  42: 64 */     cqCabecera.where(criteriaBuilder.equal(fromCabecera.get("idDepartamento"), Integer.valueOf(idDepartamento)));
/*  43: 65 */     CriteriaQuery<Departamento> select = cqCabecera.select(fromCabecera);
/*  44:    */     
/*  45: 67 */     Departamento departamento = (Departamento)this.em.createQuery(select).getSingleResult();
/*  46: 68 */     this.em.detach(departamento);
/*  47:    */     
/*  48:    */ 
/*  49: 71 */     CriteriaQuery<CentroTrabajo> cqCentro = criteriaBuilder.createQuery(CentroTrabajo.class);
/*  50: 72 */     Root<CentroTrabajo> fromCentro = cqCentro.from(CentroTrabajo.class);
/*  51: 73 */     fromCentro.fetch("bodegaTrabajo", JoinType.LEFT);
/*  52: 74 */     fromCentro.fetch("empleadoGenerico", JoinType.LEFT);
/*  53: 75 */     cqCentro.where(criteriaBuilder.equal(fromCentro.get("departamento"), departamento));
/*  54: 76 */     CriteriaQuery<CentroTrabajo> selectCentro = cqCentro.select(fromCentro);
/*  55: 77 */     cqCentro.orderBy(new Order[] { criteriaBuilder.asc(fromCentro.get("idCentroTrabajo")) });
/*  56:    */     
/*  57: 79 */     List<CentroTrabajo> listaCentroTrabajo = this.em.createQuery(selectCentro).getResultList();
/*  58: 80 */     departamento.setListaCentroTrabajo(listaCentroTrabajo);
/*  59: 82 */     for (CentroTrabajo centro : listaCentroTrabajo)
/*  60:    */     {
/*  61: 83 */       this.em.detach(centro);
/*  62: 84 */       centro.setDepartamento(departamento);
/*  63:    */     }
/*  64: 88 */     Object cqDocumento = criteriaBuilder.createQuery(DocumentoDigitalizadoDepartamento.class);
/*  65: 89 */     Root<DocumentoDigitalizadoDepartamento> fromDocumento = ((CriteriaQuery)cqDocumento).from(DocumentoDigitalizadoDepartamento.class);
/*  66: 90 */     fromDocumento.fetch("documentoDigitalizado", JoinType.LEFT).fetch("categoriaDocumentoDigitalizado", JoinType.LEFT);
/*  67: 91 */     ((CriteriaQuery)cqDocumento).where(criteriaBuilder.equal(fromDocumento.get("departamento"), departamento));
/*  68: 92 */     CriteriaQuery<DocumentoDigitalizadoDepartamento> selectDocumento = ((CriteriaQuery)cqDocumento).select(fromDocumento);
/*  69: 93 */     ((CriteriaQuery)cqDocumento).orderBy(new Order[] { criteriaBuilder.asc(fromDocumento.get("idDocumentoDigitalizadoDepartamento")) });
/*  70:    */     
/*  71: 95 */     List<DocumentoDigitalizadoDepartamento> listaDocumentoDigitalizadoDepartamento = this.em.createQuery(selectDocumento).getResultList();
/*  72: 96 */     departamento.setListaDocumentoDigitalizadoDepartamento(listaDocumentoDigitalizadoDepartamento);
/*  73: 98 */     for (DocumentoDigitalizadoDepartamento documento : listaDocumentoDigitalizadoDepartamento)
/*  74:    */     {
/*  75: 99 */       this.em.detach(documento);
/*  76:100 */       documento.setDepartamento(departamento);
/*  77:    */     }
/*  78:104 */     Object cqTurno = criteriaBuilder.createQuery(TurnoDepartamento.class);
/*  79:105 */     Root<TurnoDepartamento> fromTurno = ((CriteriaQuery)cqTurno).from(TurnoDepartamento.class);
/*  80:106 */     fromTurno.fetch("turno");
/*  81:107 */     ((CriteriaQuery)cqTurno).where(criteriaBuilder.equal(fromTurno.get("departamento"), departamento));
/*  82:108 */     CriteriaQuery<TurnoDepartamento> selectTurno = ((CriteriaQuery)cqTurno).select(fromTurno);
/*  83:109 */     ((CriteriaQuery)cqTurno).orderBy(new Order[] { criteriaBuilder.asc(fromTurno.get("idTurnoDepartamento")) });
/*  84:    */     
/*  85:111 */     List<TurnoDepartamento> listaTurnoDepartamento = this.em.createQuery(selectTurno).getResultList();
/*  86:112 */     departamento.setListaTurnoDepartamento(listaTurnoDepartamento);
/*  87:114 */     for (TurnoDepartamento turno : listaTurnoDepartamento)
/*  88:    */     {
/*  89:115 */       this.em.detach(turno);
/*  90:116 */       turno.setDepartamento(departamento);
/*  91:    */     }
/*  92:133 */     return departamento;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Departamento cargarListaRubroVariableDepartamento(int idDepartamento)
/*  96:    */   {
/*  97:143 */     Departamento departamento = (Departamento)buscarPorId(Integer.valueOf(idDepartamento));
/*  98:144 */     if (departamento.getListaRubroDepartamentoCuentaContable().size() > 0) {
/*  99:145 */       for (RubroDepartamentoCuentaContable rubroDepartamentoCuentaContable : departamento.getListaRubroDepartamentoCuentaContable())
/* 100:    */       {
/* 101:146 */         rubroDepartamentoCuentaContable.getId();
/* 102:147 */         rubroDepartamentoCuentaContable.getRubro().getId();
/* 103:148 */         if (rubroDepartamentoCuentaContable.getCuentaContableRubro() != null) {
/* 104:149 */           rubroDepartamentoCuentaContable.getCuentaContableRubro().getId();
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:153 */     return departamento;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Departamento buscarPorNombre(String nombre)
/* 112:    */   {
/* 113:158 */     Map<String, String> filters = new HashMap();
/* 114:159 */     filters.put("nombre", nombre);
/* 115:160 */     List<Departamento> listaDepartamento = obtenerListaCombo("nombre", true, filters);
/* 116:162 */     if (listaDepartamento.size() > 0) {
/* 117:163 */       return (Departamento)listaDepartamento.get(0);
/* 118:    */     }
/* 119:165 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Departamento buscarPorEmpleado(int idEmpleado)
/* 123:    */   {
/* 124:170 */     StringBuilder sql = new StringBuilder();
/* 125:171 */     sql.append(" SELECT d ");
/* 126:172 */     sql.append(" FROM Empleado e ");
/* 127:173 */     sql.append(" INNER JOIN e.departamento d ");
/* 128:174 */     sql.append(" WHERE e.idEmpleado =:idEmpleado ");
/* 129:    */     
/* 130:176 */     Query query = this.em.createQuery(sql.toString());
/* 131:177 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 132:    */     
/* 133:179 */     return (Departamento)query.getSingleResult();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public List<Rubro> getListaRubros(int idDpto)
/* 137:    */   {
/* 138:185 */     StringBuilder SQL_QUERY = new StringBuilder();
/* 139:186 */     SQL_QUERY.append(" select r from DepartamentoRubro dr inner join dr.rubro r ");
/* 140:187 */     SQL_QUERY.append(" where dr.departamento.idDepartamento = :idDepartamento ");
/* 141:188 */     Query query = this.em.createQuery(SQL_QUERY.toString());
/* 142:189 */     query.setParameter("idDepartamento", Integer.valueOf(idDpto));
/* 143:    */     
/* 144:191 */     return query.getResultList();
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DepartamentoDao
 * JD-Core Version:    0.7.0.1
 */