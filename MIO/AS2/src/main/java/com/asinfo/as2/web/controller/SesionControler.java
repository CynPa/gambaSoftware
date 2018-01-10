/*   1:    */ package com.asinfo.as2.web.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import com.asinfo.as2.entities.DimensionContable;
/*   6:    */ import com.asinfo.as2.entities.FiltroProducto;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   9:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*  10:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  12:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioFiltroProducto;
/*  17:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  18:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*  19:    */ import com.asinfo.as2.seguridad.ServicioSistema;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  22:    */ import java.io.Serializable;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.SessionScoped;
/*  30:    */ import javax.faces.model.SelectItem;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @SessionScoped
/*  34:    */ public class SesionControler
/*  35:    */   implements Serializable
/*  36:    */ {
/*  37:    */   @EJB
/*  38:    */   private transient ServicioDimensionContable servicioProyecto;
/*  39:    */   @EJB
/*  40:    */   private transient ServicioBodega servicioBodega;
/*  41:    */   @EJB
/*  42:    */   private transient ServicioSistema servicioSistema;
/*  43:    */   @EJB
/*  44:    */   private transient ServicioFiltroProducto servicioFiltroProducto;
/*  45:    */   @EJB
/*  46:    */   private transient ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  47:    */   @EJB
/*  48:    */   private transient ServicioRutaFabricacion servicioRutaFabricacion;
/*  49:    */   @EJB
/*  50:    */   private transient ServicioCuentaContable servicioCuentaContable;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioCreditoTributario servicioCreditoTributario;
/*  53:    */   private Boolean floricola;
/*  54:    */   private FiltroProducto filtroProducto;
/*  55:    */   private List<SelectItem> listaTipoProducto;
/*  56:    */   protected List<CreditoTributarioSRI> listaCreditoTributarioSRI;
/*  57:    */   public static final String VERSION = "2.2.11.012";
/*  58:    */   private static final long serialVersionUID = -4602888180403644640L;
/*  59:    */   
/*  60:    */   public int getRetrasoFiltro()
/*  61:    */   {
/*  62: 82 */     return ParametrosSistema.getRetrasoFiltro(AppUtil.getOrganizacion().getId()).intValue();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Boolean getManejaProyectos()
/*  66:    */   {
/*  67: 86 */     return ParametrosSistema.getManejaProyectos(AppUtil.getOrganizacion().getId());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getVersion()
/*  71:    */   {
/*  72: 90 */     return "2.2.11.012";
/*  73:    */   }
/*  74:    */   
/*  75:    */   public List<DimensionContable> autocompletarProyectosActivos(String consulta)
/*  76:    */   {
/*  77:100 */     return autocompletarProyectos(Boolean.valueOf(true), consulta);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<DimensionContable> autocompletarProyectos(Boolean activo, String consulta)
/*  81:    */   {
/*  82:111 */     Map<String, String> filters = new HashMap();
/*  83:112 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/*  84:113 */     filters.put("numero", "5");
/*  85:114 */     filters.put("nombre", consulta);
/*  86:115 */     if (activo != null) {
/*  87:116 */       filters.put("activo", String.valueOf(activo));
/*  88:    */     }
/*  89:118 */     return this.servicioProyecto.obtenerListaCombo("nombre", true, filters);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List<DimensionContable> autocompletarCentroCostos(String consulta)
/*  93:    */   {
/*  94:122 */     Map<String, String> filters = new HashMap();
/*  95:123 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/*  96:124 */     filters.put("numero", "1");
/*  97:125 */     filters.put("nombre", consulta);
/*  98:126 */     return this.servicioProyecto.obtenerListaCombo("codigo", true, filters);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public List<Bodega> autocompletarTodasLasBodegas(String consulta)
/* 102:    */   {
/* 103:131 */     return autocompletarBodega(null, consulta);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<Bodega> autocompletarBodegasActivas(String consulta)
/* 107:    */   {
/* 108:135 */     return autocompletarBodega(Boolean.valueOf(true), consulta);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<Bodega> autocompletarBodega(Boolean activo, String consulta)
/* 112:    */   {
/* 113:139 */     Map<String, String> filters = new HashMap();
/* 114:140 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 115:141 */     if (activo != null) {
/* 116:142 */       filters.put("activo", String.valueOf(activo));
/* 117:    */     }
/* 118:144 */     return this.servicioBodega.obtenerListaCombo("nombre", true, filters);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<OrdenFabricacion> autocompletarOrdenFabricacion(String cadena)
/* 122:    */   {
/* 123:158 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 124:159 */     return this.servicioOrdenFabricacion.autocompletarOrdenesAbiertas(idOrganizacion, cadena, null);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<RutaFabricacion> autocompletarRutaFabricacion(String consulta)
/* 128:    */   {
/* 129:168 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 130:169 */     List<RutaFabricacion> lista = new ArrayList();
/* 131:170 */     HashMap<String, String> filters = new HashMap();
/* 132:171 */     filters.put("idOrganizacion", String.valueOf(idOrganizacion));
/* 133:172 */     filters.put("nombre", consulta.trim());
/* 134:173 */     lista = this.servicioRutaFabricacion.obtenerListaCombo("nombre", true, filters);
/* 135:    */     
/* 136:175 */     return lista;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public List<CuentaContable> autocompletarCuentaContable(String consulta)
/* 140:    */   {
/* 141:179 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 142:180 */     HashMap<String, String> filters = new HashMap();
/* 143:181 */     filters.put("idOrganizacion", String.valueOf(idOrganizacion));
/* 144:182 */     filters.put("codigo", consulta.trim() + "%");
/* 145:183 */     return this.servicioCuentaContable.obtenerListaCombo("codigo", true, filters);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Boolean getFloricola()
/* 149:    */   {
/* 150:188 */     if (this.floricola == null) {
/* 151:189 */       if (this.servicioSistema.buscarPorNombre("AS2-FLORICOLA") != null) {
/* 152:190 */         this.floricola = Boolean.valueOf(true);
/* 153:    */       } else {
/* 154:192 */         this.floricola = Boolean.valueOf(false);
/* 155:    */       }
/* 156:    */     }
/* 157:196 */     return this.floricola;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setFloricola(Boolean floricola)
/* 161:    */   {
/* 162:200 */     this.floricola = floricola;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public FiltroProducto getFiltroProducto()
/* 166:    */   {
/* 167:204 */     if (this.filtroProducto == null) {
/* 168:205 */       this.filtroProducto = ((FiltroProducto)this.servicioFiltroProducto.obtenerListaCombo("", false, null).get(0));
/* 169:    */     }
/* 170:207 */     return this.filtroProducto;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setFiltroProducto(FiltroProducto filtroProducto)
/* 174:    */   {
/* 175:211 */     this.filtroProducto = filtroProducto;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<SelectItem> getListaTipoProducto()
/* 179:    */   {
/* 180:216 */     if (this.listaTipoProducto == null)
/* 181:    */     {
/* 182:217 */       this.listaTipoProducto = new ArrayList();
/* 183:219 */       for (TipoProducto t : TipoProducto.values())
/* 184:    */       {
/* 185:220 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 186:221 */         this.listaTipoProducto.add(item);
/* 187:    */       }
/* 188:    */     }
/* 189:224 */     return this.listaTipoProducto;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<CreditoTributarioSRI> getListaCreditoTributarioSRI()
/* 193:    */   {
/* 194:228 */     if (this.listaCreditoTributarioSRI == null) {
/* 195:229 */       this.listaCreditoTributarioSRI = this.servicioCreditoTributario.obtenerListaCombo("codigo", true, null);
/* 196:    */     }
/* 197:231 */     return this.listaCreditoTributarioSRI;
/* 198:    */   }
/* 199:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.web.controller.SesionControler
 * JD-Core Version:    0.7.0.1
 */