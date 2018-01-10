/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.controller.TipoIdentificacionBean;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  13:    */ import com.asinfo.as2.entities.Transportista;
/*  14:    */ import com.asinfo.as2.entities.Zona;
/*  15:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  17:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  20:    */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ManagedProperty;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ import org.primefaces.component.datatable.DataTable;
/*  31:    */ import org.primefaces.context.RequestContext;
/*  32:    */ import org.primefaces.model.LazyDataModel;
/*  33:    */ import org.primefaces.model.SortOrder;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class TransportistaBean
/*  38:    */   extends PageControllerAS2
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = -5870993678417105671L;
/*  41:    */   @EJB
/*  42:    */   private transient ServicioTransportista servicioTransportista;
/*  43:    */   @EJB
/*  44:    */   private transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  45:    */   @EJB
/*  46:    */   private transient ServicioUsuario servicioUsuario;
/*  47:    */   @EJB
/*  48:    */   private transient ServicioEmpresa servicioEmpresa;
/*  49:    */   private Transportista transportista;
/*  50:    */   private LazyDataModel<Transportista> listaTransportista;
/*  51:    */   private List<TipoIdentificacion> listaTipoIdentificacionCombo;
/*  52:    */   private List<Transportista> listaTransportistaCombo;
/*  53:    */   private List<Zona> zonaAsignada;
/*  54:    */   private DataTable dtTransportista;
/*  55:    */   private DataTable dtZonaAsignada;
/*  56:    */   private DataTable dtZona;
/*  57:    */   @ManagedProperty("#{tipoIdentificacionBean}")
/*  58:    */   private TipoIdentificacionBean tipoIdentificacionBean;
/*  59:    */   
/*  60:    */   @PostConstruct
/*  61:    */   public void init()
/*  62:    */   {
/*  63: 67 */     this.listaTransportista = new LazyDataModel()
/*  64:    */     {
/*  65:    */       private static final long serialVersionUID = 1L;
/*  66:    */       
/*  67:    */       public List<Transportista> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  68:    */       {
/*  69: 78 */         List<Transportista> lista = new ArrayList();
/*  70:    */         
/*  71: 80 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  72: 81 */         lista = TransportistaBean.this.servicioTransportista.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  73:    */         
/*  74: 83 */         TransportistaBean.this.listaTransportista.setRowCount(TransportistaBean.this.servicioTransportista.contarPorCriterio(filters));
/*  75: 84 */         return lista;
/*  76:    */       }
/*  77:    */     };
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String editar()
/*  81:    */   {
/*  82: 91 */     if ((this.transportista != null) && (this.transportista.getId() != 0))
/*  83:    */     {
/*  84: 92 */       this.transportista = this.servicioTransportista.cargarDetalle(this.transportista.getId());
/*  85: 93 */       setEditado(true);
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89: 95 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  90:    */     }
/*  91: 97 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<EntidadUsuario> autocompletarUsuarios(String consulta)
/*  95:    */   {
/*  96:101 */     return this.servicioUsuario.autocompletarUsuarios(consulta);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String guardar()
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:107 */       TipoIdentificacion tipoIdentificacion = this.servicioTipoIdentificacion.buscarPorId(Integer.valueOf(getTransportista().getTipoIdentificacion().getId()));
/* 104:    */       
/* 105:    */ 
/* 106:110 */       ValidarIdentificacion.validarIdentificacion(tipoIdentificacion.isIndicadorValidarIdentificacion(), getTransportista().getIdentificacion());
/* 107:    */       
/* 108:112 */       this.servicioTransportista.guardar(getTransportista());
/* 109:113 */       limpiar();
/* 110:114 */       setEditado(false);
/* 111:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 112:    */     }
/* 113:    */     catch (ExcepcionAS2Identification e)
/* 114:    */     {
/* 115:117 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 116:118 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 117:    */     }
/* 118:    */     catch (Exception e)
/* 119:    */     {
/* 120:120 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 121:121 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 122:    */     }
/* 123:123 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String eliminar()
/* 127:    */   {
/* 128:    */     try
/* 129:    */     {
/* 130:129 */       this.servicioTransportista.eliminar(getTransportista());
/* 131:130 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 132:131 */       limpiar();
/* 133:    */     }
/* 134:    */     catch (Exception e)
/* 135:    */     {
/* 136:133 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 137:134 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 138:    */     }
/* 139:136 */     return "";
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String limpiar()
/* 143:    */   {
/* 144:141 */     crearTransportista();
/* 145:142 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String cargarDatos()
/* 149:    */   {
/* 150:147 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void crearTransportista()
/* 154:    */   {
/* 155:151 */     this.transportista = new Transportista();
/* 156:152 */     this.transportista.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 157:153 */     this.transportista.setIdSucursal(AppUtil.getSucursal().getId());
/* 158:154 */     this.transportista.setTipoIdentificacion(new TipoIdentificacion());
/* 159:155 */     this.transportista.setActivo(true);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 163:    */   {
/* 164:159 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 168:    */   {
/* 169:163 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void actualizarProveedorListener()
/* 173:    */   {
/* 174:167 */     if (this.transportista.getEmpresa() != null)
/* 175:    */     {
/* 176:168 */       Empresa empresa = this.servicioEmpresa.cargarDetalle(this.transportista.getEmpresa());
/* 177:    */       
/* 178:    */ 
/* 179:171 */       this.transportista.setTipoIdentificacion(this.transportista.getEmpresa().getTipoIdentificacion());
/* 180:172 */       this.transportista.setIdentificacion(this.transportista.getEmpresa().getIdentificacion());
/* 181:173 */       this.transportista.setNombre(this.transportista.getEmpresa().getNombreFiscal());
/* 182:174 */       this.transportista.setEmail(this.transportista.getEmpresa().getEmail1());
/* 183:177 */       for (DireccionEmpresa direccionEmpresa : empresa.getDirecciones())
/* 184:    */       {
/* 185:178 */         this.transportista.setTelefono(direccionEmpresa.getTelefono1());
/* 186:179 */         this.transportista.setDireccion(direccionEmpresa.getDireccionCompleta());
/* 187:180 */         if (direccionEmpresa.isIndicadorDireccionPrincipal()) {
/* 188:    */           break;
/* 189:    */         }
/* 190:    */       }
/* 191:    */     }
/* 192:    */     else
/* 193:    */     {
/* 194:185 */       this.transportista.setIndicadorPagaFlete(false);
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Transportista getTransportista()
/* 199:    */   {
/* 200:190 */     if (this.transportista == null) {
/* 201:191 */       crearTransportista();
/* 202:    */     }
/* 203:193 */     return this.transportista;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setTransportista(Transportista transportista)
/* 207:    */   {
/* 208:197 */     this.transportista = transportista;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public DataTable getDtTransportista()
/* 212:    */   {
/* 213:201 */     return this.dtTransportista;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public List<TipoIdentificacion> getListaTipoIdentificacionCombo()
/* 217:    */   {
/* 218:205 */     if (this.listaTipoIdentificacionCombo == null) {
/* 219:206 */       this.listaTipoIdentificacionCombo = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/* 220:    */     }
/* 221:208 */     return this.listaTipoIdentificacionCombo;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setListaTipoIdentificacionCombo(List<TipoIdentificacion> listaTipoIdentificacionCombo)
/* 225:    */   {
/* 226:212 */     this.listaTipoIdentificacionCombo = listaTipoIdentificacionCombo;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setDtTransportista(DataTable dtTransportista)
/* 230:    */   {
/* 231:216 */     this.dtTransportista = dtTransportista;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public LazyDataModel<Transportista> getListaTransportista()
/* 235:    */   {
/* 236:220 */     return this.listaTransportista;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setListaTransportista(LazyDataModel<Transportista> listaTransportista)
/* 240:    */   {
/* 241:224 */     this.listaTransportista = listaTransportista;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public List<Transportista> getListaTransportistaCombo()
/* 245:    */   {
/* 246:228 */     String sortField = "codigo";
/* 247:229 */     this.listaTransportistaCombo = this.servicioTransportista.obtenerListaCombo(sortField, true, null);
/* 248:230 */     return this.listaTransportistaCombo;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setListaTransportistaCombo(List<Transportista> listaTransportistaCombo)
/* 252:    */   {
/* 253:234 */     this.listaTransportistaCombo = listaTransportistaCombo;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public List<Zona> getZonaAsignada()
/* 257:    */   {
/* 258:238 */     if (this.transportista != null) {
/* 259:239 */       this.zonaAsignada = this.servicioTransportista.obtenerZonaAsignada(this.transportista.getId());
/* 260:    */     }
/* 261:241 */     return this.zonaAsignada;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setZonaAsignada(List<Zona> zonaAsignada)
/* 265:    */   {
/* 266:245 */     this.zonaAsignada = zonaAsignada;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public DataTable getDtZonaAsignada()
/* 270:    */   {
/* 271:249 */     return this.dtZonaAsignada;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setDtZonaAsignada(DataTable dtZona)
/* 275:    */   {
/* 276:253 */     this.dtZonaAsignada = dtZona;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public DataTable getDtZona()
/* 280:    */   {
/* 281:257 */     return this.dtZona;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setDtZona(DataTable dtZona)
/* 285:    */   {
/* 286:261 */     this.dtZona = dtZona;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void cargarZona()
/* 290:    */   {
/* 291:265 */     this.transportista = ((Transportista)this.dtTransportista.getRowData());
/* 292:266 */     getZonaAsignada();
/* 293:267 */     RequestContext context = RequestContext.getCurrentInstance();
/* 294:268 */     context.execute("PF('cerrarDialog').show();");
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void reset()
/* 298:    */   {
/* 299:272 */     this.dtZonaAsignada.reset();
/* 300:    */   }
/* 301:    */   
/* 302:    */   public TipoIdentificacionBean getTipoIdentificacionBean()
/* 303:    */   {
/* 304:276 */     return this.tipoIdentificacionBean;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setTipoIdentificacionBean(TipoIdentificacionBean tipoIdentificacionBean)
/* 308:    */   {
/* 309:280 */     this.tipoIdentificacionBean = tipoIdentificacionBean;
/* 310:    */   }
/* 311:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.TransportistaBean
 * JD-Core Version:    0.7.0.1
 */