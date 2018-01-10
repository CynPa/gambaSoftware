/*   1:    */ package com.asinfo.as2.compras.importaciones.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioProcesoImportacion;
/*   4:    */ import com.asinfo.as2.dao.ProcesoImportacionDao;
/*   5:    */ import com.asinfo.as2.entities.ProcesoImportacion;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class ServicioProcesoImportacionImpl
/*  14:    */   implements ServicioProcesoImportacion
/*  15:    */ {
/*  16:    */   @EJB
/*  17:    */   private transient ProcesoImportacionDao procesoImportacionDao;
/*  18:    */   
/*  19:    */   public void guardar(ProcesoImportacion procesoImportacion)
/*  20:    */   {
/*  21: 42 */     this.procesoImportacionDao.guardar(procesoImportacion);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void eliminar(ProcesoImportacion procesoImportacion)
/*  25:    */   {
/*  26: 53 */     this.procesoImportacionDao.eliminar(procesoImportacion);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ProcesoImportacion buscarPorId(Integer id)
/*  30:    */   {
/*  31: 63 */     return (ProcesoImportacion)this.procesoImportacionDao.buscarPorId(id);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<ProcesoImportacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  35:    */   {
/*  36: 74 */     return this.procesoImportacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<ProcesoImportacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  40:    */   {
/*  41: 86 */     return this.procesoImportacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int contarPorCriterio(Map<String, String> filters)
/*  45:    */   {
/*  46: 96 */     return this.procesoImportacionDao.contarPorCriterio(filters);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public ProcesoImportacion buscarPorCodigo(String codigo)
/*  50:    */     throws ExcepcionAS2
/*  51:    */   {
/*  52:106 */     return this.procesoImportacionDao.buscarPorCodigo(codigo);
/*  53:    */   }
/*  54:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.impl.ServicioProcesoImportacionImpl
 * JD-Core Version:    0.7.0.1
 */