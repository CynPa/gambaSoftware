/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioEspecialidad;
/*   4:    */ import com.asinfo.as2.dao.EspecialidadDao;
/*   5:    */ import com.asinfo.as2.entities.Especialidad;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioEspecialidadImpl
/*  15:    */   implements ServicioEspecialidad
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private EspecialidadDao especialidadDao;
/*  19:    */   
/*  20:    */   public void guardar(Especialidad especialidad)
/*  21:    */   {
/*  22: 48 */     this.especialidadDao.guardar(especialidad);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(Especialidad especialidad)
/*  26:    */   {
/*  27: 61 */     this.especialidadDao.guardar(especialidad);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Especialidad buscarPorId(int idEspecialidad)
/*  31:    */   {
/*  32: 71 */     return (Especialidad)this.especialidadDao.buscarPorId(Integer.valueOf(idEspecialidad));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<Especialidad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 82 */     return this.especialidadDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<Especialidad> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 92 */     return this.especialidadDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int contarPorCriterio(Map<String, String> filters)
/*  46:    */   {
/*  47:102 */     return this.especialidadDao.contarPorCriterio(filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Especialidad cargarDetalle(int idEspecialidad)
/*  51:    */   {
/*  52:112 */     return this.especialidadDao.cargarDetalle(idEspecialidad);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<Especialidad> autocompletarEspecialidad(String consulta, int idOrganizacion)
/*  56:    */   {
/*  57:117 */     List<Especialidad> lista = new ArrayList();
/*  58:    */     
/*  59:119 */     String sortField = "codigo";
/*  60:120 */     HashMap<String, String> filters = new HashMap();
/*  61:121 */     filters.put("nombre", consulta.trim());
/*  62:122 */     filters.put("activo", "true");
/*  63:123 */     filters.put("idOrganizacion", Integer.toString(idOrganizacion));
/*  64:    */     
/*  65:125 */     lista = this.especialidadDao.obtenerListaCombo(sortField, true, filters);
/*  66:    */     
/*  67:127 */     return lista;
/*  68:    */   }
/*  69:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioEspecialidadImpl
 * JD-Core Version:    0.7.0.1
 */