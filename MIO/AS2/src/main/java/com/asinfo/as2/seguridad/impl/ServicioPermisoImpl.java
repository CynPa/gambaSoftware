/*   1:    */ package com.asinfo.as2.seguridad.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ProcesoOrganizacionDao;
/*   4:    */ import com.asinfo.as2.dao.seguridad.PermisoDao;
/*   5:    */ import com.asinfo.as2.dao.seguridad.RolDao;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.seguridad.EntidadAccion;
/*   9:    */ import com.asinfo.as2.entities.seguridad.EntidadPermiso;
/*  10:    */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*  11:    */ import com.asinfo.as2.seguridad.ServicioPermiso;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.ejb.Stateless;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class ServicioPermisoImpl
/*  21:    */   implements ServicioPermiso
/*  22:    */ {
/*  23:    */   @EJB
/*  24:    */   private PermisoDao permisoDao;
/*  25:    */   @EJB
/*  26:    */   private ProcesoOrganizacionDao procesoOrganizacionDao;
/*  27:    */   @EJB
/*  28:    */   private RolDao rolDao;
/*  29:    */   
/*  30:    */   public void guardar(EntidadPermiso entidad)
/*  31:    */   {
/*  32: 67 */     this.permisoDao.guardar(entidad);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void eliminar(EntidadPermiso entidad)
/*  36:    */   {
/*  37: 79 */     this.permisoDao.eliminar(entidad);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public EntidadPermiso buscarPorId(Integer id)
/*  41:    */   {
/*  42: 90 */     return (EntidadPermiso)this.permisoDao.buscarPorId(id);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<EntidadPermiso> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 95 */     return this.permisoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void copiarPermisos(Organizacion organizacionACopiar, Organizacion organizacionSeleccionada, boolean agregar)
/*  51:    */   {
/*  52:101 */     Map<Integer, ProcesoOrganizacion> mapaProcesos = new HashMap();
/*  53:102 */     Map<String, String> filters = new HashMap();
/*  54:103 */     filters.put("procesoOrganizacion.organizacion.idOrganizacion", String.valueOf(organizacionSeleccionada.getIdOrganizacion()));
/*  55:104 */     filters.put("procesoOrganizacion.entidadProceso.activo", "=true");
/*  56:    */     
/*  57:106 */     List<EntidadPermiso> listaPermiso = this.permisoDao.obtenerListaCombo(null, false, filters);
/*  58:107 */     List<ProcesoOrganizacion> listaProcesoOrganizacion = this.procesoOrganizacionDao.buscarPorSistemaOrganizacion(null, organizacionSeleccionada);
/*  59:109 */     for (ProcesoOrganizacion procesoOrganizacion : listaProcesoOrganizacion)
/*  60:    */     {
/*  61:110 */       ProcesoOrganizacion po = this.procesoOrganizacionDao.buscarProcesoOrganizacion(organizacionACopiar, procesoOrganizacion.getEntidadProceso());
/*  62:111 */       if (po == null)
/*  63:    */       {
/*  64:112 */         this.procesoOrganizacionDao.detach(procesoOrganizacion);
/*  65:113 */         procesoOrganizacion.setIdProcesoOrganizacion(0);
/*  66:114 */         procesoOrganizacion.setOrganizacion(organizacionACopiar);
/*  67:115 */         this.procesoOrganizacionDao.guardar(procesoOrganizacion);
/*  68:    */       }
/*  69:117 */       mapaProcesos.put(Integer.valueOf(procesoOrganizacion.getEntidadProceso().getId()), po == null ? procesoOrganizacion : po);
/*  70:    */     }
/*  71:120 */     for (EntidadPermiso entidadPermiso : listaPermiso)
/*  72:    */     {
/*  73:121 */       ProcesoOrganizacion procesoOrganizacion = (ProcesoOrganizacion)mapaProcesos.get(Integer.valueOf(entidadPermiso.getProcesoOrganizacion().getEntidadProceso().getId()));
/*  74:122 */       if (!this.permisoDao.existePermiso(procesoOrganizacion, entidadPermiso.getEntidadRol()))
/*  75:    */       {
/*  76:123 */         EntidadPermiso permisoNew = new EntidadPermiso();
/*  77:124 */         permisoNew.setEntidadRol(entidadPermiso.getEntidadRol());
/*  78:125 */         permisoNew.setProcesoOrganizacion(procesoOrganizacion);
/*  79:126 */         permisoNew.setListaAccion(new ArrayList());
/*  80:128 */         for (EntidadAccion accion : entidadPermiso.getListaAccion()) {
/*  81:129 */           permisoNew.getListaAccion().add(accion);
/*  82:    */         }
/*  83:133 */         this.permisoDao.guardar(permisoNew);
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.impl.ServicioPermisoImpl
 * JD-Core Version:    0.7.0.1
 */