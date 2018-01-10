/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.dao.SectorDao;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.RutaVendedor;
/*   9:    */ import com.asinfo.as2.entities.Sector;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.UsuarioOrganizacion;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  13:    */ import com.asinfo.as2.enumeraciones.DiaSemanaEnum;
/*  14:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  17:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.JsfUtil;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.component.datatable.DataTable;
/*  30:    */ import org.primefaces.model.LazyDataModel;
/*  31:    */ import org.primefaces.model.SortOrder;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @ViewScoped
/*  35:    */ public class RutaVendedorBean
/*  36:    */   extends PageControllerAS2
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   private DataTable dtRutaVendedor;
/*  40:    */   private List<Sector> listaSector;
/*  41:    */   private List<DiaSemanaEnum> listaDiaSemana;
/*  42:    */   private EntidadUsuario entidadUsuario;
/*  43:    */   private LazyDataModel<EntidadUsuario> listaEntidadUsuarios;
/*  44:    */   private DataTable dtEntidadUsuario;
/*  45:    */   @EJB
/*  46:    */   private SectorDao SectorDao;
/*  47:    */   @EJB
/*  48:    */   private ServicioGenerico<Sector> servicioSector;
/*  49:    */   @EJB
/*  50:    */   private ServicioUsuario servicioUsuario;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioOrganizacion servicioOrganizacion;
/*  53:    */   
/*  54:    */   public void obtenerFiltros(Map<String, String> filters)
/*  55:    */   {
/*  56: 71 */     filters.put("indicadorAgenteComercial", "true");
/*  57:    */   }
/*  58:    */   
/*  59:    */   @PostConstruct
/*  60:    */   public void init()
/*  61:    */   {
/*  62: 77 */     this.listaEntidadUsuarios = new LazyDataModel()
/*  63:    */     {
/*  64:    */       private static final long serialVersionUID = 1L;
/*  65:    */       
/*  66:    */       public List<EntidadUsuario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  67:    */       {
/*  68: 84 */         List<EntidadUsuario> lista = new ArrayList();
/*  69: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  70:    */         
/*  71: 87 */         List<Integer> listaIdOrganizacion = new ArrayList();
/*  72: 88 */         listaIdOrganizacion.add(Integer.valueOf(AppUtil.getOrganizacion().getId()));
/*  73: 89 */         filters.put("indicadorAgenteComercial", "true");
/*  74:    */         
/*  75: 91 */         lista = RutaVendedorBean.this.servicioUsuario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters, listaIdOrganizacion);
/*  76: 92 */         RutaVendedorBean.this.listaEntidadUsuarios.setRowCount(RutaVendedorBean.this.servicioUsuario.contarPorCriterio(filters, listaIdOrganizacion));
/*  77:    */         
/*  78: 94 */         return lista;
/*  79:    */       }
/*  80:    */     };
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String editar()
/*  84:    */   {
/*  85:103 */     if (getEntidadUsuario().getId() > 0)
/*  86:    */     {
/*  87:104 */       setEditado(true);
/*  88:105 */       setEntidadUsuario(this.servicioUsuario.cargarDetalleRutaVendedor(getEntidadUsuario().getId()));
/*  89:    */     }
/*  90:    */     else
/*  91:    */     {
/*  92:108 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  93:    */     }
/*  94:110 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void agregarDetalle()
/*  98:    */   {
/*  99:114 */     RutaVendedor vs = new RutaVendedor();
/* 100:115 */     vs.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 101:116 */     vs.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 102:117 */     vs.setUsuario(getEntidadUsuario());
/* 103:118 */     getEntidadUsuario().getListaRutaVendedor().add(vs);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String cancelar()
/* 107:    */   {
/* 108:123 */     setEditado(false);
/* 109:124 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String crear()
/* 113:    */   {
/* 114:128 */     setEditado(false);
/* 115:129 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 116:130 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String guardar()
/* 120:    */   {
/* 121:    */     try
/* 122:    */     {
/* 123:139 */       getEntidadUsuario().setListaUsuarioBodega(new ArrayList());
/* 124:140 */       getEntidadUsuario().setListaUsuarioOrganizacion(new ArrayList());
/* 125:141 */       getEntidadUsuario().setListaUsuarioSucursal(new ArrayList());
/* 126:    */       
/* 127:143 */       this.servicioUsuario.guardar(getEntidadUsuario(), false);
/* 128:    */       
/* 129:145 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 130:    */       
/* 131:    */ 
/* 132:148 */       setEditado(false);
/* 133:    */     }
/* 134:    */     catch (AS2Exception e)
/* 135:    */     {
/* 136:151 */       JsfUtil.addErrorMessage(e, "");
/* 137:    */     }
/* 138:    */     catch (ExcepcionAS2 e)
/* 139:    */     {
/* 140:153 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 141:154 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 142:    */     }
/* 143:    */     catch (Exception e)
/* 144:    */     {
/* 145:156 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"), e.getMessage());
/* 146:157 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 147:    */     }
/* 148:159 */     return "";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public List<RutaVendedor> getListaRutaVendedorFiltrado()
/* 152:    */   {
/* 153:164 */     List<RutaVendedor> listaRutaVendedorF = new ArrayList();
/* 154:166 */     for (RutaVendedor vs : getEntidadUsuario().getListaRutaVendedor()) {
/* 155:167 */       if (!vs.isEliminado()) {
/* 156:168 */         listaRutaVendedorF.add(vs);
/* 157:    */       }
/* 158:    */     }
/* 159:172 */     return listaRutaVendedorF;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String limpiar()
/* 163:    */   {
/* 164:177 */     crearUsuario();
/* 165:178 */     return "";
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void crearUsuario()
/* 169:    */   {
/* 170:182 */     this.entidadUsuario = new EntidadUsuario();
/* 171:183 */     this.entidadUsuario.setActivo(true);
/* 172:184 */     UsuarioOrganizacion usuarioOrganizacion = new UsuarioOrganizacion();
/* 173:185 */     usuarioOrganizacion.setEntidadUsuario(this.entidadUsuario);
/* 174:186 */     usuarioOrganizacion.setOrganizacion(AppUtil.getOrganizacion());
/* 175:187 */     usuarioOrganizacion.setPredeterminado(true);
/* 176:188 */     this.entidadUsuario.getListaUsuarioOrganizacion().add(usuarioOrganizacion);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String eliminarDetalleRutaVendedor()
/* 180:    */   {
/* 181:193 */     RutaVendedor vs = (RutaVendedor)this.dtRutaVendedor.getRowData();
/* 182:194 */     vs.setEliminado(true);
/* 183:195 */     return "";
/* 184:    */   }
/* 185:    */   
/* 186:    */   public DataTable getDtRutaVendedor()
/* 187:    */   {
/* 188:199 */     return this.dtRutaVendedor;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setDtRutaVendedor(DataTable dtRutaVendedor)
/* 192:    */   {
/* 193:203 */     this.dtRutaVendedor = dtRutaVendedor;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public List<Sector> getListaSector()
/* 197:    */   {
/* 198:207 */     HashMap<String, String> filters = new HashMap();
/* 199:208 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 200:210 */     if (this.listaSector == null) {
/* 201:211 */       this.listaSector = this.servicioSector.obtenerListaCombo(Sector.class, "nombre", true, filters);
/* 202:    */     }
/* 203:214 */     return this.listaSector;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setListaSector(List<Sector> listaSector)
/* 207:    */   {
/* 208:218 */     this.listaSector = listaSector;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public List<DiaSemanaEnum> getListaDiaSemana()
/* 212:    */   {
/* 213:222 */     if (this.listaDiaSemana == null)
/* 214:    */     {
/* 215:223 */       this.listaDiaSemana = new ArrayList();
/* 216:224 */       for (DiaSemanaEnum dse : DiaSemanaEnum.values()) {
/* 217:225 */         this.listaDiaSemana.add(dse);
/* 218:    */       }
/* 219:    */     }
/* 220:229 */     return this.listaDiaSemana;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setListaDiaSemana(List<DiaSemanaEnum> listaDiaSemana)
/* 224:    */   {
/* 225:233 */     this.listaDiaSemana = listaDiaSemana;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public EntidadUsuario getEntidadUsuario()
/* 229:    */   {
/* 230:237 */     return this.entidadUsuario;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setEntidadUsuario(EntidadUsuario entidadUsuario)
/* 234:    */   {
/* 235:241 */     this.entidadUsuario = entidadUsuario;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public LazyDataModel<EntidadUsuario> getListaEntidadUsuarios()
/* 239:    */   {
/* 240:245 */     return this.listaEntidadUsuarios;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setListaEntidadUsuarios(LazyDataModel<EntidadUsuario> listaEntidadUsuarios)
/* 244:    */   {
/* 245:249 */     this.listaEntidadUsuarios = listaEntidadUsuarios;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String eliminar()
/* 249:    */   {
/* 250:255 */     return null;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public String cargarDatos()
/* 254:    */   {
/* 255:261 */     return null;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public DataTable getDtEntidadUsuario()
/* 259:    */   {
/* 260:265 */     return this.dtEntidadUsuario;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setDtEntidadUsuario(DataTable dtEntidadUsuario)
/* 264:    */   {
/* 265:269 */     this.dtEntidadUsuario = dtEntidadUsuario;
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.RutaVendedorBean
 * JD-Core Version:    0.7.0.1
 */