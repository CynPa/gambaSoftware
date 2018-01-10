/*   1:    */ package com.asinfo.as2.seguridad.configuracion.cotroller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.UsuarioOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*  10:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  11:    */ import com.asinfo.as2.seguridad.ServicioProceso;
/*  12:    */ import com.asinfo.as2.seguridad.ServicioSistema;
/*  13:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ import org.primefaces.component.datatable.DataTable;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ProcesoOrganizacionBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private ServicioProceso servicioProceso;
/*  33:    */   @EJB
/*  34:    */   private ServicioSistema servicioSistema;
/*  35:    */   @EJB
/*  36:    */   private ServicioOrganizacion servicioOrganizacion;
/*  37:    */   private DataTable dtEntidadRol;
/*  38:    */   private DataTable dtProceso;
/*  39:    */   private DataTable dtOrganizacion;
/*  40: 62 */   private List<EntidadProceso> listaProcesosNoAsignados = new ArrayList();
/*  41:    */   private EntidadProceso[] listaProcesoOrganizacionsSeleccionados;
/*  42:    */   private List<EntidadSistema> listaSistema;
/*  43:    */   private List<ProcesoOrganizacion> listaProcesoOrganizacion;
/*  44:    */   private Organizacion organizacion;
/*  45:    */   
/*  46:    */   public String editar()
/*  47:    */   {
/*  48: 78 */     if (this.organizacion == null)
/*  49:    */     {
/*  50: 79 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  51: 80 */       return "";
/*  52:    */     }
/*  53: 82 */     this.organizacion = this.servicioOrganizacion.cargarDetalle(this.organizacion.getId());
/*  54: 83 */     setEditado(true);
/*  55: 84 */     getListaProcesoOrganizacion();
/*  56: 85 */     return "";
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String guardar()
/*  60:    */   {
/*  61:    */     try
/*  62:    */     {
/*  63: 96 */       this.servicioOrganizacion.guardarListaProcesoOrganizacion(this.listaProcesoOrganizacion);
/*  64: 97 */       cargarDatos();
/*  65: 98 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  66:    */     }
/*  67:    */     catch (Exception e)
/*  68:    */     {
/*  69:100 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  70:101 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  71:    */     }
/*  72:103 */     return "";
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String eliminar()
/*  76:    */   {
/*  77:113 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  78:114 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String limpiar()
/*  82:    */   {
/*  83:124 */     this.listaProcesoOrganizacion = new ArrayList();
/*  84:125 */     this.organizacion = null;
/*  85:126 */     this.listaProcesoOrganizacionsSeleccionados = null;
/*  86:127 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String cargarDatos()
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:138 */       setEditado(false);
/*  94:139 */       limpiar();
/*  95:    */     }
/*  96:    */     catch (Exception e)
/*  97:    */     {
/*  98:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  99:142 */       LOG.error("ERROR AL CARGAR LOS DATOS", e);
/* 100:    */     }
/* 101:144 */     return null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String crear()
/* 105:    */   {
/* 106:149 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 107:150 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String cargarProcesosNoAsignados()
/* 111:    */   {
/* 112:159 */     this.listaProcesoOrganizacionsSeleccionados = null;
/* 113:160 */     HashMap<Integer, EntidadProceso> procesos = new HashMap();
/* 114:161 */     Map<String, String> filtros = new HashMap();
/* 115:162 */     filtros.put("idOrganizacion", "!=-1");
/* 116:163 */     filtros.put("activo", "true");
/* 117:164 */     for (EntidadProceso entidadProceso : this.servicioProceso.obtenerListaCombo("viewName", false, filtros)) {
/* 118:165 */       procesos.put(Integer.valueOf(entidadProceso.getId()), entidadProceso);
/* 119:    */     }
/* 120:167 */     for (ProcesoOrganizacion procesoOrganizacion : this.listaProcesoOrganizacion) {
/* 121:168 */       if (!procesoOrganizacion.isEliminado()) {
/* 122:169 */         procesos.remove(Integer.valueOf(procesoOrganizacion.getEntidadProceso().getId()));
/* 123:    */       }
/* 124:    */     }
/* 125:173 */     this.listaProcesosNoAsignados = new ArrayList(procesos.values());
/* 126:174 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String eliminarProceso(ProcesoOrganizacion procesoOrganizacion)
/* 130:    */   {
/* 131:183 */     procesoOrganizacion.setEliminado(true);
/* 132:184 */     this.dtProceso.reset();
/* 133:185 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String agregarProceso()
/* 137:    */   {
/* 138:194 */     if (this.listaProcesoOrganizacionsSeleccionados != null)
/* 139:    */     {
/* 140:195 */       ProcesoOrganizacion procesoOrganizacion = null;
/* 141:196 */       for (EntidadProceso entidadProceso : this.listaProcesoOrganizacionsSeleccionados)
/* 142:    */       {
/* 143:197 */         procesoOrganizacion = new ProcesoOrganizacion();
/* 144:198 */         procesoOrganizacion.setEntidadProceso(entidadProceso);
/* 145:199 */         procesoOrganizacion.setOrganizacion(this.organizacion);
/* 146:    */         
/* 147:201 */         this.organizacion.getListaProcesoOrganizacion().add(procesoOrganizacion);
/* 148:202 */         this.listaProcesoOrganizacion.add(procesoOrganizacion);
/* 149:    */       }
/* 150:    */     }
/* 151:205 */     cargarProcesosNoAsignados();
/* 152:206 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public DataTable getDtEntidadRol()
/* 156:    */   {
/* 157:211 */     return this.dtEntidadRol;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setDtEntidadRol(DataTable dtEntidadRol)
/* 161:    */   {
/* 162:215 */     this.dtEntidadRol = dtEntidadRol;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<EntidadProceso> getListaProcesosNoAsignados()
/* 166:    */   {
/* 167:224 */     return this.listaProcesosNoAsignados;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaProcesosNoAsignados(List<EntidadProceso> listaProcesosNoAsignados)
/* 171:    */   {
/* 172:234 */     this.listaProcesosNoAsignados = listaProcesosNoAsignados;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public EntidadProceso[] getListaProcesosSeleccionados()
/* 176:    */   {
/* 177:243 */     return this.listaProcesoOrganizacionsSeleccionados;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setListaProcesosSeleccionados(EntidadProceso[] listaProcesoOrganizacionsSeleccionados)
/* 181:    */   {
/* 182:253 */     this.listaProcesoOrganizacionsSeleccionados = listaProcesoOrganizacionsSeleccionados;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public List<ProcesoOrganizacion> getListaProcesoOrganizacion()
/* 186:    */   {
/* 187:262 */     List<ProcesoOrganizacion> listaProv = new ArrayList();
/* 188:263 */     if ((this.listaProcesoOrganizacion == null) || (this.listaProcesoOrganizacion.size() == 0))
/* 189:    */     {
/* 190:264 */       this.listaProcesoOrganizacion = new ArrayList();
/* 191:265 */       this.organizacion = this.servicioOrganizacion.cargarDetalle(this.organizacion.getId());
/* 192:266 */       for (ProcesoOrganizacion procesoOrganizacion : this.organizacion.getListaProcesoOrganizacion()) {
/* 193:267 */         if (!procesoOrganizacion.isEliminado()) {
/* 194:268 */           this.listaProcesoOrganizacion.add(procesoOrganizacion);
/* 195:    */         }
/* 196:    */       }
/* 197:271 */       listaProv = this.listaProcesoOrganizacion;
/* 198:    */     }
/* 199:    */     else
/* 200:    */     {
/* 201:273 */       for (ProcesoOrganizacion elemento : this.listaProcesoOrganizacion) {
/* 202:274 */         if (!elemento.isEliminado()) {
/* 203:275 */           listaProv.add(elemento);
/* 204:    */         }
/* 205:    */       }
/* 206:    */     }
/* 207:279 */     return listaProv;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List<EntidadSistema> getListaSistema()
/* 211:    */   {
/* 212:288 */     if (this.listaSistema == null) {
/* 213:289 */       this.listaSistema = this.servicioSistema.obtenerListaCombo("nombre", true, null);
/* 214:    */     }
/* 215:291 */     return this.listaSistema;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setListaSistema(List<EntidadSistema> listaSistema)
/* 219:    */   {
/* 220:301 */     this.listaSistema = listaSistema;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<Organizacion> getListaOrganizacion()
/* 224:    */   {
/* 225:306 */     List<Organizacion> listaOrganizacion = new ArrayList();
/* 226:308 */     if (AppUtil.getUsuarioEnSesion().isIndicadorSuperAdministrador())
/* 227:    */     {
/* 228:309 */       listaOrganizacion = this.servicioOrganizacion.obtenerListaCombo("razonSocial", true, null);
/* 229:310 */       return listaOrganizacion;
/* 230:    */     }
/* 231:313 */     List<UsuarioOrganizacion> lista = AppUtil.getUsuarioEnSesion().getListaUsuarioOrganizacion();
/* 232:315 */     for (UsuarioOrganizacion usuarioOrganizacion : lista) {
/* 233:316 */       listaOrganizacion.add(usuarioOrganizacion.getOrganizacion());
/* 234:    */     }
/* 235:319 */     return listaOrganizacion;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Organizacion getOrganizacion()
/* 239:    */   {
/* 240:323 */     return this.organizacion;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setOrganizacion(Organizacion organizacion)
/* 244:    */   {
/* 245:327 */     this.organizacion = organizacion;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public DataTable getDtOrganizacion()
/* 249:    */   {
/* 250:331 */     return this.dtOrganizacion;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setDtOrganizacion(DataTable dtOrganizacion)
/* 254:    */   {
/* 255:335 */     this.dtOrganizacion = dtOrganizacion;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public DataTable getDtProceso()
/* 259:    */   {
/* 260:339 */     return this.dtProceso;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setDtProceso(DataTable dtProceso)
/* 264:    */   {
/* 265:343 */     this.dtProceso = dtProceso;
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.configuracion.cotroller.ProcesoOrganizacionBean
 * JD-Core Version:    0.7.0.1
 */