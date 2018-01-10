/*  1:   */ package com.asinfo.as2.financiero.activos.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.activos.ReporteActivoFijoDao;
/*  4:   */ import com.asinfo.as2.entities.ActivoFijo;
/*  5:   */ import com.asinfo.as2.entities.CategoriaActivo;
/*  6:   */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*  7:   */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteActivoFijo;
/*  8:   */ import java.util.Date;
/*  9:   */ import java.util.List;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.ejb.Stateless;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class ServicioReporteActivoFijoIml
/* 15:   */   implements ServicioReporteActivoFijo
/* 16:   */ {
/* 17:   */   @EJB
/* 18:   */   private ReporteActivoFijoDao reporteActivoFijoDao;
/* 19:   */   
/* 20:   */   public List getReporteActivoFijo(int idActivoFijo)
/* 21:   */   {
/* 22:45 */     return this.reporteActivoFijoDao.getReporteActivoFijo(idActivoFijo);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<Object[]> getReporteActivoFijoNECVSNIIF(int anio, int mes, CategoriaActivo categoriaActivo, ActivoFijo activoFijo, int idOrganizacion, boolean indicadorFechaCompra)
/* 26:   */   {
/* 27:57 */     return this.reporteActivoFijoDao.getReporteActivoFijoNECVSNIIF(anio, mes, categoriaActivo, activoFijo, idOrganizacion, indicadorFechaCompra);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public List<Object[]> listaActivoFijoFechas(Date fechaDesde, Date fechaHasta, CategoriaActivo categoriaActivo, SubcategoriaActivo subcategoriaActivo, int idOrganizacion, boolean activo)
/* 31:   */   {
/* 32:65 */     List<Object[]> lista = this.reporteActivoFijoDao.listaActivoFijoFechas(fechaDesde, fechaHasta, categoriaActivo, subcategoriaActivo, idOrganizacion, activo);
/* 33:66 */     return lista;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public List<SubcategoriaActivo> listaSubcategoriaActivo(int idCategoriaActivo)
/* 37:   */   {
/* 38:71 */     return this.reporteActivoFijoDao.listaSubcategoriaActivo(idCategoriaActivo);
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.reportes.servicio.impl.ServicioReporteActivoFijoIml
 * JD-Core Version:    0.7.0.1
 */