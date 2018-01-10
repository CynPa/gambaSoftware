/*   1:    */ package com.asinfo.as2.util.seguridad;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class AuthorizationPermission
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = 1L;
/*   9:    */   private int idProceso;
/*  10:    */   private String viewId;
/*  11:    */   private String viewName;
/*  12:    */   private String acciones;
/*  13:    */   private int idOrganizacion;
/*  14:    */   
/*  15:    */   public AuthorizationPermission() {}
/*  16:    */   
/*  17:    */   public AuthorizationPermission(int idProceso, String viewId, String viewName, String acciones, int idOrganizacion)
/*  18:    */   {
/*  19: 22 */     this.viewId = viewId;
/*  20: 23 */     this.viewName = viewName;
/*  21: 24 */     this.acciones = (acciones == null ? "" : acciones);
/*  22: 25 */     this.idProceso = idProceso;
/*  23: 26 */     this.idOrganizacion = idOrganizacion;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getIdProceso()
/*  27:    */   {
/*  28: 36 */     return this.idProceso;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setIdProceso(int idProceso)
/*  32:    */   {
/*  33: 46 */     this.idProceso = idProceso;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getViewId()
/*  37:    */   {
/*  38: 55 */     return this.viewId;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setViewId(String viewId)
/*  42:    */   {
/*  43: 65 */     this.viewId = viewId;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getViewName()
/*  47:    */   {
/*  48: 74 */     return this.viewName;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setViewName(String viewName)
/*  52:    */   {
/*  53: 84 */     this.viewName = viewName;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getAcciones()
/*  57:    */   {
/*  58: 93 */     return this.acciones;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setAcciones(String acciones)
/*  62:    */   {
/*  63:103 */     this.acciones = acciones;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68:107 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdOrganizacion(int idOrganizacion)
/*  72:    */   {
/*  73:111 */     this.idOrganizacion = idOrganizacion;
/*  74:    */   }
/*  75:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.seguridad.AuthorizationPermission
 * JD-Core Version:    0.7.0.1
 */