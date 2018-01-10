/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioTipoContacto;
/*   9:    */ import com.asinfo.as2.entities.Ciudad;
/*  10:    */ import com.asinfo.as2.entities.Contacto;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.TipoContacto;
/*  14:    */ import com.asinfo.as2.entities.Ubicacion;
/*  15:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.primefaces.component.datatable.DataTable;
/*  27:    */ import org.primefaces.event.SelectEvent;
/*  28:    */ import org.primefaces.model.LazyDataModel;
/*  29:    */ import org.primefaces.model.SortOrder;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class SucursalBean
/*  34:    */   extends PageControllerAS2
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = -2679652345182976793L;
/*  37:    */   @EJB
/*  38:    */   private transient ServicioSucursal servicioSucursal;
/*  39:    */   @EJB
/*  40:    */   private transient ServicioOrganizacion servicioOrganizacion;
/*  41:    */   @EJB
/*  42:    */   private transient ServicioCiudad servicioCiudad;
/*  43:    */   @EJB
/*  44:    */   private transient ServicioTipoContacto servicioTipoContacto;
/*  45:    */   private Sucursal sucursal;
/*  46:    */   private LazyDataModel<Sucursal> listaSucursal;
/*  47:    */   private List<Organizacion> listaOrganizacion;
/*  48:    */   private List<Ciudad> listaCiudad;
/*  49:    */   private List<TipoContacto> listaTipoContacto;
/*  50:    */   private DataTable dtSucursal;
/*  51:    */   
/*  52:    */   @PostConstruct
/*  53:    */   public void init()
/*  54:    */   {
/*  55: 77 */     this.listaSucursal = new LazyDataModel()
/*  56:    */     {
/*  57:    */       private static final long serialVersionUID = 1L;
/*  58:    */       
/*  59:    */       public List<Sucursal> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  60:    */       {
/*  61: 84 */         List<Sucursal> lista = new ArrayList();
/*  62: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  63:    */         
/*  64: 87 */         lista = SucursalBean.this.servicioSucursal.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  65: 88 */         SucursalBean.this.listaSucursal.setRowCount(SucursalBean.this.servicioSucursal.contarPorCriterio(filters));
/*  66:    */         
/*  67: 90 */         return lista;
/*  68:    */       }
/*  69:    */     };
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String editar()
/*  73:    */   {
/*  74:103 */     if (getSucursal().getId() > 0)
/*  75:    */     {
/*  76:104 */       setSucursal(this.servicioSucursal.cargarDetalle(getSucursal().getIdSucursal()));
/*  77:105 */       setEditado(true);
/*  78:    */     }
/*  79:    */     else
/*  80:    */     {
/*  81:107 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  82:    */     }
/*  83:110 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String guardar()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:121 */       this.servicioSucursal.guardar(getSucursal());
/*  91:122 */       limpiar();
/*  92:123 */       setEditado(false);
/*  93:124 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  94:    */     }
/*  95:    */     catch (ExcepcionAS2Financiero e)
/*  96:    */     {
/*  97:126 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  98:127 */       e.printStackTrace();
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 103:130 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 104:    */     }
/* 105:133 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String eliminar()
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:144 */       this.servicioSucursal.eliminar(this.sucursal);
/* 113:145 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:147 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 118:148 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 119:    */     }
/* 120:150 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void crearSucursal()
/* 124:    */   {
/* 125:157 */     this.sucursal = new Sucursal();
/* 126:158 */     this.sucursal.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 127:159 */     this.sucursal.setUbicacion(new Ubicacion());
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String limpiar()
/* 131:    */   {
/* 132:169 */     crearSucursal();
/* 133:170 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String cargarDatos()
/* 137:    */   {
/* 138:180 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<Sucursal> obtenerListaCombo()
/* 142:    */   {
/* 143:190 */     String sortField = "codigo";
/* 144:    */     
/* 145:192 */     Map<String, String> filters = new HashMap();
/* 146:193 */     filters.put("" + AppUtil.getOrganizacion().getId(), "organizacion.idOrganizacion");
/* 147:    */     
/* 148:195 */     return this.servicioSucursal.obtenerListaCombo(sortField, true, filters);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void agregarContactoListener()
/* 152:    */   {
/* 153:199 */     Contacto contacto = new Contacto();
/* 154:200 */     contacto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 155:201 */     contacto.setSucursal(getSucursal());
/* 156:202 */     getSucursal().getListaContacto().add(contacto);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void eliminarContacto(Contacto contacto)
/* 160:    */   {
/* 161:206 */     contacto.setEliminado(true);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void onRowSelect(SelectEvent event)
/* 165:    */   {
/* 166:215 */     this.sucursal = ((Sucursal)event.getObject());
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Sucursal getSucursal()
/* 170:    */   {
/* 171:224 */     if (this.sucursal == null) {
/* 172:225 */       crearSucursal();
/* 173:    */     }
/* 174:227 */     return this.sucursal;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setSucursal(Sucursal sucursal)
/* 178:    */   {
/* 179:237 */     this.sucursal = sucursal;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public LazyDataModel<Sucursal> getListaSucursal()
/* 183:    */   {
/* 184:246 */     return this.listaSucursal;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setListaSucursal(LazyDataModel<Sucursal> listaSucursal)
/* 188:    */   {
/* 189:256 */     this.listaSucursal = listaSucursal;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public DataTable getDtSucursal()
/* 193:    */   {
/* 194:265 */     return this.dtSucursal;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setDtSucursal(DataTable dtSucursal)
/* 198:    */   {
/* 199:275 */     this.dtSucursal = dtSucursal;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<Organizacion> getListaOrganizacion()
/* 203:    */   {
/* 204:284 */     if (this.listaOrganizacion == null) {
/* 205:285 */       this.listaOrganizacion = this.servicioOrganizacion.obtenerListaCombo("razonSocial", true, null);
/* 206:    */     }
/* 207:287 */     return this.listaOrganizacion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setListaOrganizacion(List<Organizacion> listaOrganizacion)
/* 211:    */   {
/* 212:297 */     this.listaOrganizacion = listaOrganizacion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List<Ciudad> getListaCiudad()
/* 216:    */   {
/* 217:306 */     if (this.listaCiudad == null) {
/* 218:307 */       this.listaCiudad = this.servicioCiudad.obtenerListaCombo(null, false, null);
/* 219:    */     }
/* 220:309 */     return this.listaCiudad;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setListaCiudad(List<Ciudad> listaCiudad)
/* 224:    */   {
/* 225:319 */     this.listaCiudad = listaCiudad;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<Contacto> getListaContacto()
/* 229:    */   {
/* 230:323 */     List<Contacto> lista = new ArrayList();
/* 231:324 */     for (Contacto contacto : getSucursal().getListaContacto()) {
/* 232:325 */       if (!contacto.isEliminado()) {
/* 233:326 */         lista.add(contacto);
/* 234:    */       }
/* 235:    */     }
/* 236:330 */     return lista;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<TipoContacto> getListaTipoContacto()
/* 240:    */   {
/* 241:333 */     if (this.listaTipoContacto == null) {
/* 242:334 */       this.listaTipoContacto = this.servicioTipoContacto.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 243:    */     }
/* 244:336 */     return this.listaTipoContacto;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setListaTipoContacto(List<TipoContacto> listaTipoContacto)
/* 248:    */   {
/* 249:340 */     this.listaTipoContacto = listaTipoContacto;
/* 250:    */   }
/* 251:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.SucursalBean
 * JD-Core Version:    0.7.0.1
 */