/*   1:    */ package com.asinfo.as2.seguridad.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.seguridad.PermisoDao;
/*   4:    */ import com.asinfo.as2.dao.seguridad.RolDao;
/*   5:    */ import com.asinfo.as2.entities.seguridad.EntidadAccion;
/*   6:    */ import com.asinfo.as2.entities.seguridad.EntidadPermiso;
/*   7:    */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*   8:    */ import com.asinfo.as2.seguridad.ServicioRol;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioRolImpl
/*  16:    */   implements ServicioRol
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private RolDao rolDao;
/*  20:    */   @EJB
/*  21:    */   private PermisoDao permisoDao;
/*  22:    */   
/*  23:    */   public void guardar(EntidadRol entidad)
/*  24:    */   {
/*  25: 53 */     for (EntidadPermiso entidadPermiso : entidad.getListaPermiso())
/*  26:    */     {
/*  27: 56 */       entidadPermiso.setAcciones(null);
/*  28: 57 */       entidadPermiso.getAcciones();
/*  29: 59 */       if (entidadPermiso.getId() == 0)
/*  30:    */       {
/*  31: 60 */         List<EntidadAccion> lista = entidadPermiso.getListaAccion();
/*  32: 61 */         entidadPermiso.setListaAccion(null);
/*  33:    */         
/*  34: 63 */         this.permisoDao.guardar(entidadPermiso);
/*  35: 64 */         entidadPermiso.setListaAccion(lista);
/*  36: 65 */         this.permisoDao.guardar(entidadPermiso);
/*  37:    */       }
/*  38:    */       else
/*  39:    */       {
/*  40: 68 */         this.permisoDao.guardar(entidadPermiso);
/*  41:    */       }
/*  42:    */     }
/*  43: 72 */     this.rolDao.guardar(entidad);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void eliminar(EntidadRol entidad)
/*  47:    */   {
/*  48: 84 */     this.rolDao.eliminar(entidad);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public EntidadRol buscarPorId(Integer id)
/*  52:    */   {
/*  53: 94 */     return (EntidadRol)this.rolDao.buscarPorId(id);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public EntidadRol cargarDetalle(int id)
/*  57:    */   {
/*  58:104 */     return this.rolDao.cargarDetalle(id);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<EntidadRol> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  62:    */   {
/*  63:109 */     return this.rolDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public EntidadRol copiarRol(EntidadRol entidad)
/*  67:    */   {
/*  68:114 */     entidad.setIdRol(0);
/*  69:115 */     entidad.setActivo(false);
/*  70:116 */     entidad.setNombre("");
/*  71:117 */     entidad.setDescripcion("");
/*  72:118 */     for (EntidadPermiso entidadPermiso : entidad.getListaPermiso()) {
/*  73:119 */       entidadPermiso.setIdPermiso(0);
/*  74:    */     }
/*  75:121 */     return entidad;
/*  76:    */   }
/*  77:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.impl.ServicioRolImpl
 * JD-Core Version:    0.7.0.1
 */