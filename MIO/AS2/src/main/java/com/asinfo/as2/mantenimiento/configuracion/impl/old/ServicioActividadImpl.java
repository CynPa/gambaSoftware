/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.old.ActividadDao;
/*   4:    */ import com.asinfo.as2.dao.mantenimiento.old.TareaDao;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.old.Actividad;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.old.Tarea;
/*   7:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioActividad;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ServicioActividadImpl
/*  17:    */   implements ServicioActividad
/*  18:    */ {
/*  19:    */   @EJB
/*  20:    */   private ActividadDao actividadDao;
/*  21:    */   @EJB
/*  22:    */   private TareaDao tareaDao;
/*  23:    */   
/*  24:    */   public void guardar(Actividad actividad)
/*  25:    */   {
/*  26: 49 */     for (Tarea tarea : actividad.getListaTarea()) {
/*  27: 50 */       this.tareaDao.guardar(tarea);
/*  28:    */     }
/*  29: 52 */     this.actividadDao.guardar(actividad);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void eliminar(Actividad actividad)
/*  33:    */   {
/*  34: 62 */     this.actividadDao.eliminar(actividad);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Actividad buscarPorId(int idActividad)
/*  38:    */   {
/*  39: 73 */     return (Actividad)this.actividadDao.buscarPorId(Integer.valueOf(idActividad));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List<Actividad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  43:    */   {
/*  44: 83 */     return this.actividadDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<Actividad> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  48:    */   {
/*  49: 93 */     return this.actividadDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int contarPorCriterio(Map<String, String> filters)
/*  53:    */   {
/*  54:103 */     return this.actividadDao.contarPorCriterio(filters);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Actividad cargarDetalle(int idActividad)
/*  58:    */   {
/*  59:113 */     return this.actividadDao.cargarDetalle(idActividad);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<Actividad> autocompletarActividad(String consulta)
/*  63:    */   {
/*  64:123 */     List<Actividad> lista = new ArrayList();
/*  65:    */     
/*  66:125 */     String sortField = "nombre";
/*  67:126 */     HashMap<String, String> filters = new HashMap();
/*  68:127 */     filters.put("nombre", consulta.trim());
/*  69:128 */     filters.put("codigo", consulta.trim());
/*  70:129 */     lista = obtenerListaCombo(sortField, true, filters);
/*  71:    */     
/*  72:131 */     return lista;
/*  73:    */   }
/*  74:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioActividadImpl
 * JD-Core Version:    0.7.0.1
 */