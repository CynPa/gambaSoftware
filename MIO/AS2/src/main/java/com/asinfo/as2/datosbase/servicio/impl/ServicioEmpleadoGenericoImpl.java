/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.EmpleadoGenericoDao;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpleadoGenerico;
/*   5:    */ import com.asinfo.as2.entities.EmpleadoGenerico;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioEmpleadoGenericoImpl
/*  15:    */   implements ServicioEmpleadoGenerico
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private EmpleadoGenericoDao empleadoGenericoDao;
/*  19:    */   
/*  20:    */   public void guardar(EmpleadoGenerico empleadoGenerico)
/*  21:    */   {
/*  22: 44 */     this.empleadoGenericoDao.guardar(empleadoGenerico);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(EmpleadoGenerico empleadoGenerico)
/*  26:    */   {
/*  27: 54 */     this.empleadoGenericoDao.eliminar(empleadoGenerico);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public EmpleadoGenerico buscarPorId(int idEmpleadoGenerico)
/*  31:    */   {
/*  32: 65 */     return (EmpleadoGenerico)this.empleadoGenericoDao.buscarPorId(Integer.valueOf(idEmpleadoGenerico));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<EmpleadoGenerico> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 75 */     return this.empleadoGenericoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<EmpleadoGenerico> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 85 */     return this.empleadoGenericoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int contarPorCriterio(Map<String, String> filters)
/*  46:    */   {
/*  47: 95 */     return this.empleadoGenericoDao.contarPorCriterio(filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public EmpleadoGenerico cargarDetalle(int idEmpleadoGenerico)
/*  51:    */   {
/*  52:105 */     return this.empleadoGenericoDao.cargarDetalle(idEmpleadoGenerico);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<EmpleadoGenerico> autocompletarEmpeladoGenerico(String consulta)
/*  56:    */   {
/*  57:116 */     List<EmpleadoGenerico> lista = new ArrayList();
/*  58:    */     
/*  59:118 */     String sortField = "codigo";
/*  60:119 */     HashMap<String, String> filters = new HashMap();
/*  61:120 */     filters.put("codigo", consulta.trim());
/*  62:121 */     filters.put("nombre", consulta.trim());
/*  63:122 */     lista = this.empleadoGenericoDao.autocompletarEmpleadoGenerico(sortField, true, filters);
/*  64:    */     
/*  65:124 */     return lista;
/*  66:    */   }
/*  67:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioEmpleadoGenericoImpl
 * JD-Core Version:    0.7.0.1
 */