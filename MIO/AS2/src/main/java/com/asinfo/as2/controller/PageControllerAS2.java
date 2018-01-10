/*   1:    */ package com.asinfo.as2.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.datosbase.servicio.ServicioEstadoProceso;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.UsuarioSucursal;
/*   8:    */ import com.asinfo.as2.enumeraciones.Accion;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  10:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import org.primefaces.context.RequestContext;
/*  17:    */ 
/*  18:    */ public abstract class PageControllerAS2
/*  19:    */   extends PageController
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = -1780829944972089955L;
/*  22:    */   @EJB
/*  23:    */   protected ServicioEstadoProceso servicioEstadoProceso;
/*  24:    */   private boolean editado;
/*  25:    */   private boolean nuevo;
/*  26:    */   private String READ;
/*  27:    */   private String CREATE;
/*  28:    */   private String UPDATE;
/*  29:    */   private String DELETE;
/*  30:    */   private String ALL;
/*  31:    */   private String NONE;
/*  32:    */   private String IMPRIMIR;
/*  33:    */   private Boolean indicadorManejaCiclosLargos;
/*  34:    */   
/*  35:    */   public String crear()
/*  36:    */   {
/*  37: 60 */     limpiar();
/*  38: 61 */     setEditado(true);
/*  39:    */     
/*  40: 63 */     return "";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public abstract String editar();
/*  44:    */   
/*  45:    */   public abstract String guardar();
/*  46:    */   
/*  47:    */   public abstract String eliminar();
/*  48:    */   
/*  49:    */   public String verificarEliminacion()
/*  50:    */   {
/*  51: 97 */     RequestContext.getCurrentInstance().execute("confirmacionEliminar.show()");
/*  52: 98 */     return "";
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String cancelar()
/*  56:    */   {
/*  57:108 */     setEditado(false);
/*  58:109 */     limpiar();
/*  59:110 */     return "";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public abstract String limpiar();
/*  63:    */   
/*  64:    */   public abstract String cargarDatos();
/*  65:    */   
/*  66:    */   public boolean isEditado()
/*  67:    */   {
/*  68:135 */     return this.editado;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setEditado(boolean editado)
/*  72:    */   {
/*  73:145 */     this.editado = editado;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean isNuevo()
/*  77:    */   {
/*  78:154 */     return this.nuevo;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setNuevo(boolean nuevo)
/*  82:    */   {
/*  83:164 */     this.nuevo = nuevo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getREAD()
/*  87:    */   {
/*  88:168 */     return Accion.READ.toString();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getCREATE()
/*  92:    */   {
/*  93:172 */     return Accion.CREATE.toString();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getUPDATE()
/*  97:    */   {
/*  98:176 */     return Accion.UPDATE.toString();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getDELETE()
/* 102:    */   {
/* 103:180 */     return Accion.DELETE.toString();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getALL()
/* 107:    */   {
/* 108:184 */     return Accion.ALL.toString();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getNONE()
/* 112:    */   {
/* 113:188 */     return Accion.NONE.toString();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getIMPRIMIR()
/* 117:    */   {
/* 118:192 */     return "IMPRIMIR";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isRecargosEnFactura()
/* 122:    */   {
/* 123:199 */     return ParametrosSistema.getRecargosEnFactura(AppUtil.getOrganizacion().getId()).booleanValue();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isOrganizacionTextilPadilla()
/* 127:    */   {
/* 128:204 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isGuardarAjax()
/* 132:    */   {
/* 133:208 */     return true;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isIndicadorManejoPeso()
/* 137:    */   {
/* 138:212 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().isIndicadorManejoPeso();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean isPermitirFechaInventario()
/* 142:    */   {
/* 143:216 */     return ParametrosSistema.isPermitirFechaInventario(AppUtil.getOrganizacion().getId()).booleanValue();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isIndicadorUsuarioPOS()
/* 147:    */   {
/* 148:220 */     return AppUtil.getUsuarioEnSesion().isIndicadorUsuarioPos();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isIndicadorListaPrecioPorZona()
/* 152:    */   {
/* 153:224 */     return ParametrosSistema.isIndicadorListaPrecioPorZona(AppUtil.getOrganizacion().getId()).booleanValue();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isOrganizacionGeneral()
/* 157:    */   {
/* 158:228 */     return TipoOrganizacion.TIPO_ORGANIZACION_GENERAL.equals(AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion());
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Boolean getIndicadorManejaCiclosLargos()
/* 162:    */   {
/* 163:232 */     if (this.indicadorManejaCiclosLargos == null) {
/* 164:233 */       this.indicadorManejaCiclosLargos = ParametrosSistema.getManejaCiclosLargosProduccion(AppUtil.getOrganizacion().getId());
/* 165:    */     }
/* 166:235 */     return this.indicadorManejaCiclosLargos;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public List<Integer> getListaIdsSucursalesAsignadasUsuarioEnSesion(Usuario usuarioSesion)
/* 170:    */   {
/* 171:240 */     List<Integer> idsSucursalesAsignadasUsuarioEnSesion = new ArrayList();
/* 172:242 */     for (UsuarioSucursal usuarioSucursal : usuarioSesion.getListaUsuarioSucursal()) {
/* 173:243 */       idsSucursalesAsignadasUsuarioEnSesion.add(Integer.valueOf(usuarioSucursal.getSucursal().getId()));
/* 174:    */     }
/* 175:245 */     return idsSucursalesAsignadasUsuarioEnSesion;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isIndicadorTotalizar()
/* 179:    */   {
/* 180:249 */     return ParametrosSistema.getTotalizarPrecioYCantidad(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public boolean isIndicadorOrdenCompraConPersonaResponsable()
/* 184:    */   {
/* 185:253 */     return ParametrosSistema.isOrdenCompraConPersonaResponsable(AppUtil.getOrganizacion().getId()).booleanValue();
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.controller.PageControllerAS2
 * JD-Core Version:    0.7.0.1
 */