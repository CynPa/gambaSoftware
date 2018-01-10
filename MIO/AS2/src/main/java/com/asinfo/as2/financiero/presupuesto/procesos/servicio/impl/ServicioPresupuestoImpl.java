/*   1:    */ package com.asinfo.as2.financiero.presupuesto.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.presupuesto.DetallePresupuestoCentroCostoDao;
/*   4:    */ import com.asinfo.as2.dao.presupuesto.DetallePresupuestoDao;
/*   5:    */ import com.asinfo.as2.dao.presupuesto.PresupuestoDao;
/*   6:    */ import com.asinfo.as2.entities.Periodo;
/*   7:    */ import com.asinfo.as2.entities.presupuesto.DetallePresupuesto;
/*   8:    */ import com.asinfo.as2.entities.presupuesto.DetallePresupuestoCentroCosto;
/*   9:    */ import com.asinfo.as2.entities.presupuesto.Presupuesto;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  11:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  12:    */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioPresupuesto;
/*  13:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.ejb.Stateless;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class ServicioPresupuestoImpl
/*  22:    */   implements ServicioPresupuesto
/*  23:    */ {
/*  24:    */   @EJB
/*  25:    */   private PresupuestoDao presupuestoDao;
/*  26:    */   @EJB
/*  27:    */   private DetallePresupuestoDao detallePresupuestoDao;
/*  28:    */   @EJB
/*  29:    */   private DetallePresupuestoCentroCostoDao detallePresupuestoCentroCostoDao;
/*  30:    */   @EJB
/*  31:    */   private transient ServicioPeriodo servicioPeriodo;
/*  32:    */   
/*  33:    */   public void guardar(Presupuesto presupuesto)
/*  34:    */     throws ExcepcionAS2Inventario, ExcepcionAS2Financiero
/*  35:    */   {
/*  36: 59 */     validaPresupuesto(presupuesto);
/*  37: 61 */     for (DetallePresupuesto detallePresupuesto : presupuesto.getListaDetallePresupuesto())
/*  38:    */     {
/*  39: 62 */       this.detallePresupuestoDao.guardar(detallePresupuesto);
/*  40: 63 */       for (DetallePresupuestoCentroCosto detallePresupuestoCentroCosto : detallePresupuesto.getListaDetallePresupuestoCentroCosto()) {
/*  41: 64 */         this.detallePresupuestoCentroCostoDao.guardar(detallePresupuestoCentroCosto);
/*  42:    */       }
/*  43:    */     }
/*  44: 67 */     this.presupuestoDao.guardar(presupuesto);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void eliminar(Presupuesto presupuesto)
/*  48:    */   {
/*  49: 77 */     Presupuesto p = this.presupuestoDao.cargarDetalle(presupuesto.getIdPresupuesto());
/*  50: 78 */     for (DetallePresupuesto dp : p.getListaDetallePresupuesto()) {
/*  51: 79 */       this.detallePresupuestoDao.eliminar(dp);
/*  52:    */     }
/*  53: 81 */     this.presupuestoDao.eliminar(presupuesto);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Presupuesto buscarPorId(int idPresupuesto)
/*  57:    */   {
/*  58: 92 */     return (Presupuesto)this.presupuestoDao.buscarPorId(Integer.valueOf(idPresupuesto));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<Presupuesto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  62:    */   {
/*  63:103 */     return this.presupuestoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public List<Presupuesto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  67:    */   {
/*  68:113 */     return this.presupuestoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int contarPorCriterio(Map<String, String> filters)
/*  72:    */   {
/*  73:123 */     return this.presupuestoDao.contarPorCriterio(filters);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Presupuesto cargarDetalle(int idPresupuesto)
/*  77:    */   {
/*  78:133 */     return this.presupuestoDao.cargarDetalle(idPresupuesto);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void validaPresupuesto(Presupuesto presupuesto)
/*  82:    */     throws ExcepcionAS2Inventario, ExcepcionAS2Financiero
/*  83:    */   {}
/*  84:    */   
/*  85:    */   public Presupuesto copiarPresupuesto(Presupuesto presupuesto)
/*  86:    */   {
/*  87:154 */     presupuesto.setIdPresupuesto(0);
/*  88:155 */     presupuesto.setPeriodo(new Periodo());
/*  89:156 */     for (DetallePresupuesto dp : presupuesto.getListaDetallePresupuesto()) {
/*  90:157 */       dp.setIdDetallePresupuesto(0);
/*  91:    */     }
/*  92:159 */     return presupuesto;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public List<Object[]> getReportePresupuesto(int idPresupuesto, int idOrganizacion, int idUsuario)
/*  96:    */   {
/*  97:165 */     return this.presupuestoDao.getReportePresupuesto(idPresupuesto, idOrganizacion, idUsuario);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Presupuesto buscarPresupuestoPorEjercicio(int idEjercicio, int idOrganizacion)
/* 101:    */   {
/* 102:170 */     return this.presupuestoDao.buscarPresupuestoPorEjercicio(idEjercicio, idOrganizacion);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Presupuesto buscarPorFecha(Date fecha, int idOrganizacion)
/* 106:    */     throws ExcepcionAS2Financiero
/* 107:    */   {
/* 108:175 */     return this.presupuestoDao.buscarPorFecha(fecha, idOrganizacion);
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.presupuesto.procesos.servicio.impl.ServicioPresupuestoImpl
 * JD-Core Version:    0.7.0.1
 */