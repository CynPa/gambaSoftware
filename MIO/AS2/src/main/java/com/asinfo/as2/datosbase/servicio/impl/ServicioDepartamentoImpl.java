/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CentroTrabajoDao;
/*   4:    */ import com.asinfo.as2.dao.DepartamentoDao;
/*   5:    */ import com.asinfo.as2.dao.DocumentoDigitalizadoDepartamentoDao;
/*   6:    */ import com.asinfo.as2.dao.GenericoDao;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   8:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*   9:    */ import com.asinfo.as2.entities.Departamento;
/*  10:    */ import com.asinfo.as2.entities.DepartamentoRubro;
/*  11:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoDepartamento;
/*  12:    */ import com.asinfo.as2.entities.Rubro;
/*  13:    */ import com.asinfo.as2.entities.nomina.asistencia.TurnoDepartamento;
/*  14:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  15:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  16:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class ServicioDepartamentoImpl
/*  24:    */   implements ServicioDepartamento
/*  25:    */ {
/*  26:    */   @EJB
/*  27:    */   private DepartamentoDao departamentoDao;
/*  28:    */   @EJB
/*  29:    */   private CentroTrabajoDao centroTrabajoDao;
/*  30:    */   @EJB
/*  31:    */   private DocumentoDigitalizadoDepartamentoDao documentoDigitalizadoDepartamentoDao;
/*  32:    */   @EJB
/*  33:    */   private ServicioRubro servicioRubro;
/*  34:    */   @EJB
/*  35:    */   private ServicioGenerico<TurnoDepartamento> servicioTurno;
/*  36:    */   @EJB
/*  37:    */   private GenericoDao<DepartamentoRubro> departamentoRubroDao;
/*  38:    */   
/*  39:    */   public void guardar(Departamento departamento)
/*  40:    */   {
/*  41: 66 */     this.departamentoDao.guardar(departamento);
/*  42: 67 */     for (CentroTrabajo centroTrabajo : departamento.getListaCentroTrabajo()) {
/*  43: 68 */       this.centroTrabajoDao.guardar(centroTrabajo);
/*  44:    */     }
/*  45: 70 */     for (DocumentoDigitalizadoDepartamento documentoDigitalizadoDepartamento : departamento.getListaDocumentoDigitalizadoDepartamento()) {
/*  46: 71 */       this.documentoDigitalizadoDepartamentoDao.guardar(documentoDigitalizadoDepartamento);
/*  47:    */     }
/*  48: 73 */     for (TurnoDepartamento turno : departamento.getListaTurnoDepartamento()) {
/*  49:    */       try
/*  50:    */       {
/*  51: 75 */         this.servicioTurno.guardar(turno);
/*  52:    */       }
/*  53:    */       catch (AS2Exception localAS2Exception) {}
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void eliminar(Departamento departamento)
/*  58:    */   {
/*  59: 90 */     departamento = cargarDetalle(departamento.getIdDepartamento());
/*  60: 91 */     for (DocumentoDigitalizadoDepartamento ddd : departamento.getListaDocumentoDigitalizadoDepartamento())
/*  61:    */     {
/*  62: 92 */       ddd.setDocumentoDigitalizado(null);
/*  63: 93 */       ddd.setDepartamento(null);
/*  64: 94 */       this.documentoDigitalizadoDepartamentoDao.eliminar(ddd);
/*  65:    */     }
/*  66: 97 */     for (TurnoDepartamento turno : departamento.getListaTurnoDepartamento())
/*  67:    */     {
/*  68: 98 */       turno.setTurno(null);
/*  69: 99 */       turno.setDepartamento(null);
/*  70:100 */       this.servicioTurno.eliminar(turno);
/*  71:    */     }
/*  72:103 */     for (CentroTrabajo centro : departamento.getListaCentroTrabajo())
/*  73:    */     {
/*  74:104 */       centro.setDepartamento(null);
/*  75:105 */       this.centroTrabajoDao.eliminar(centro);
/*  76:    */     }
/*  77:108 */     this.departamentoDao.eliminar(departamento);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Departamento buscarPorId(int idDepartamento)
/*  81:    */   {
/*  82:118 */     return (Departamento)this.departamentoDao.buscarPorId(Integer.valueOf(idDepartamento));
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<Departamento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  86:    */   {
/*  87:129 */     return this.departamentoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List<Departamento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  91:    */   {
/*  92:139 */     return this.departamentoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int contarPorCriterio(Map<String, String> filters)
/*  96:    */   {
/*  97:149 */     return this.departamentoDao.contarPorCriterio(filters);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Departamento cargarDetalle(int idDepartamento)
/* 101:    */   {
/* 102:159 */     return this.departamentoDao.cargarDetalle(idDepartamento);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Departamento buscarPorNombre(String nombre)
/* 106:    */   {
/* 107:170 */     return this.departamentoDao.buscarPorNombre(nombre);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<Rubro> getListaRubros(int idCRubro)
/* 111:    */   {
/* 112:175 */     return this.departamentoDao.getListaRubros(idCRubro);
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioDepartamentoImpl
 * JD-Core Version:    0.7.0.1
 */