/*   1:    */ package com.asinfo.as2.seguridad.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ProcesoOrganizacionDao;
/*   4:    */ import com.asinfo.as2.dao.seguridad.ProcesoDao;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*   7:    */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*   8:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*   9:    */ import com.asinfo.as2.seguridad.ServicioProceso;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ServicioProcesoImpl
/*  17:    */   implements ServicioProceso
/*  18:    */ {
/*  19:    */   @EJB
/*  20:    */   private ProcesoDao procesoDao;
/*  21:    */   @EJB
/*  22:    */   private ProcesoOrganizacionDao procesoOrganizacionDao;
/*  23:    */   
/*  24:    */   public void guardar(EntidadProceso entidad)
/*  25:    */   {
/*  26: 52 */     this.procesoDao.guardar(entidad);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void eliminar(EntidadProceso entidad)
/*  30:    */   {
/*  31: 62 */     this.procesoDao.eliminar(entidad);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public EntidadProceso buscarPorId(Integer id)
/*  35:    */   {
/*  36: 72 */     return (EntidadProceso)this.procesoDao.buscarPorId(id);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<EntidadProceso> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  40:    */   {
/*  41: 82 */     return this.procesoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<EntidadProceso> obtenerLista(List<Integer> lista, EntidadSistema sistema)
/*  45:    */   {
/*  46: 92 */     return this.procesoDao.obtenerLista(lista, sistema);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int contarPorCriterio(Map<String, String> filters)
/*  50:    */   {
/*  51: 97 */     return this.procesoDao.contarPorCriterio(filters);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<EntidadProceso> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56:102 */     return this.procesoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List<ProcesoOrganizacion> obtenerListaProcesoOrganizacion(EntidadSistema sistema, Organizacion organizacion)
/*  60:    */   {
/*  61:108 */     return this.procesoOrganizacionDao.buscarPorSistemaOrganizacion(sistema, organizacion);
/*  62:    */   }
/*  63:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.impl.ServicioProcesoImpl
 * JD-Core Version:    0.7.0.1
 */