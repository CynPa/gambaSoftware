/*   1:    */ package com.asinfo.as2.finaciero.activos.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   7:    */ import com.asinfo.as2.entities.CustodioActivoFijo;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*  13:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo;
/*  14:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCustodioActivoFijo;
/*  15:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioUbicacionActivo;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ import org.primefaces.component.datatable.DataTable;
/*  28:    */ import org.primefaces.model.LazyDataModel;
/*  29:    */ import org.primefaces.model.SortOrder;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class CustodioActivoFijoBean
/*  34:    */   extends PageControllerAS2
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private ServicioCustodioActivoFijo servicioCustodioActivoFijo;
/*  39:    */   @EJB
/*  40:    */   private ServicioUbicacionActivo servicioUbicacionActivo;
/*  41:    */   @EJB
/*  42:    */   private ServicioActivoFijo servicioActivoFijo;
/*  43:    */   @EJB
/*  44:    */   private ServicioEmpresa servicioEmpresa;
/*  45:    */   private CustodioActivoFijo custodioActivoFijo;
/*  46:    */   private ActivoFijo activoFijo;
/*  47:    */   private Empleado empleado;
/*  48:    */   private LazyDataModel<CustodioActivoFijo> listaCustodioActivoFijo;
/*  49:    */   private List<UbicacionActivo> listaUbicacionActivoCombo;
/*  50:    */   private DataTable dtCustodioActivoFijo;
/*  51:    */   private DataTable dtDetalleCustodioActivoFijo;
/*  52:    */   
/*  53:    */   @PostConstruct
/*  54:    */   public void init()
/*  55:    */   {
/*  56: 92 */     this.listaCustodioActivoFijo = new LazyDataModel()
/*  57:    */     {
/*  58:    */       private static final long serialVersionUID = 1L;
/*  59:    */       
/*  60:    */       public List<CustodioActivoFijo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  61:    */       {
/*  62: 99 */         List<CustodioActivoFijo> lista = new ArrayList();
/*  63:100 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  64:    */         
/*  65:102 */         lista = CustodioActivoFijoBean.this.servicioCustodioActivoFijo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  66:    */         
/*  67:104 */         CustodioActivoFijoBean.this.listaCustodioActivoFijo.setRowCount(CustodioActivoFijoBean.this.servicioCustodioActivoFijo.contarPorCriterio(filters));
/*  68:    */         
/*  69:106 */         return lista;
/*  70:    */       }
/*  71:    */     };
/*  72:    */   }
/*  73:    */   
/*  74:    */   private void crearCustodioActivoFijo()
/*  75:    */   {
/*  76:120 */     this.custodioActivoFijo = new CustodioActivoFijo();
/*  77:121 */     this.custodioActivoFijo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  78:122 */     this.custodioActivoFijo.setIdSucursal(AppUtil.getSucursal().getId());
/*  79:123 */     this.custodioActivoFijo.setActivoFijo(new ActivoFijo());
/*  80:124 */     this.custodioActivoFijo.setUbicacionActivo(new UbicacionActivo());
/*  81:125 */     this.custodioActivoFijo.setActivo(true);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String editar()
/*  85:    */   {
/*  86:134 */     if (getCustodioActivoFijo().getIdCustodioActivoFijo() > 0) {
/*  87:135 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  88:    */     } else {
/*  89:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  90:    */     }
/*  91:139 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String guardar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:149 */       if (((this.custodioActivoFijo.getEmpleado() == null) || (this.custodioActivoFijo.getEmpleado().getId() == 0)) && (this.custodioActivoFijo.getEmpresa() == null))
/*  99:    */       {
/* 100:150 */         addInfoMessage(getLanguageController().getMensaje("msg_mensaje_info_seleccionar_un_responsable"));
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104:152 */         this.servicioCustodioActivoFijo.guardar(this.custodioActivoFijo);
/* 105:153 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 106:154 */         setEditado(false);
/* 107:155 */         limpiar();
/* 108:    */       }
/* 109:    */     }
/* 110:    */     catch (ExcepcionAS2Financiero e)
/* 111:    */     {
/* 112:158 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 113:159 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:162 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 118:163 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 119:    */     }
/* 120:165 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String eliminar()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:175 */       this.servicioCustodioActivoFijo.eliminar(this.custodioActivoFijo);
/* 128:176 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 129:    */     }
/* 130:    */     catch (Exception e)
/* 131:    */     {
/* 132:178 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 133:179 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 134:    */     }
/* 135:181 */     return "";
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String cargarDatos()
/* 139:    */   {
/* 140:190 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String limpiar()
/* 144:    */   {
/* 145:199 */     crearCustodioActivoFijo();
/* 146:200 */     this.activoFijo = new ActivoFijo();
/* 147:201 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<CustodioActivoFijo> getListaDetalleListaCustodioActivoFijo()
/* 151:    */   {
/* 152:210 */     List<CustodioActivoFijo> lista = new ArrayList();
/* 153:211 */     for (CustodioActivoFijo custodioActivoFijo : this.activoFijo.getListaCustodioActivoFijo()) {
/* 154:212 */       if (!custodioActivoFijo.isEliminado()) {
/* 155:213 */         lista.add(custodioActivoFijo);
/* 156:    */       }
/* 157:    */     }
/* 158:216 */     return lista;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String agregarDetalle()
/* 162:    */   {
/* 163:225 */     int idCustodio = 0;
/* 164:226 */     if (this.activoFijo.getListaCustodioActivoFijo().size() > 0)
/* 165:    */     {
/* 166:227 */       idCustodio = ((CustodioActivoFijo)this.activoFijo.getListaCustodioActivoFijo().get(this.activoFijo.getListaCustodioActivoFijo().size() - 1)).getId();
/* 167:228 */       if (idCustodio > 0)
/* 168:    */       {
/* 169:229 */         this.activoFijo.getListaCustodioActivoFijo().add(this.custodioActivoFijo);
/* 170:230 */         this.custodioActivoFijo.setActivo(true);
/* 171:    */       }
/* 172:    */     }
/* 173:    */     else
/* 174:    */     {
/* 175:233 */       this.activoFijo.getListaCustodioActivoFijo().add(this.custodioActivoFijo);
/* 176:234 */       this.custodioActivoFijo.setActivo(true);
/* 177:    */     }
/* 178:237 */     return "";
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String cargarActivoFijo()
/* 182:    */   {
/* 183:246 */     this.activoFijo = this.servicioActivoFijo.cargarDetalle(this.activoFijo.getId());
/* 184:247 */     for (CustodioActivoFijo custodio : this.activoFijo.getListaCustodioActivoFijo()) {
/* 185:248 */       custodio.setActivo(false);
/* 186:    */     }
/* 187:250 */     this.custodioActivoFijo.setActivoFijo(this.activoFijo);
/* 188:251 */     return "";
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String cargarEmpleado()
/* 192:    */   {
/* 193:260 */     this.custodioActivoFijo.setEmpleado(this.empleado);
/* 194:261 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public CustodioActivoFijo getCustodioActivoFijo()
/* 198:    */   {
/* 199:278 */     return this.custodioActivoFijo;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setCustodioActivoFijo(CustodioActivoFijo custodioActivoFijo)
/* 203:    */   {
/* 204:288 */     this.custodioActivoFijo = custodioActivoFijo;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public LazyDataModel<CustodioActivoFijo> getListaCustodioActivoFijo()
/* 208:    */   {
/* 209:297 */     return this.listaCustodioActivoFijo;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setListaCustodioActivoFijo(LazyDataModel<CustodioActivoFijo> listaCustodioActivoFijo)
/* 213:    */   {
/* 214:307 */     this.listaCustodioActivoFijo = listaCustodioActivoFijo;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public List<UbicacionActivo> getListaUbicacionActivoCombo()
/* 218:    */   {
/* 219:316 */     if (this.listaUbicacionActivoCombo == null) {
/* 220:317 */       this.listaUbicacionActivoCombo = this.servicioUbicacionActivo.obtenerListaCombo("nombre", true, null);
/* 221:    */     }
/* 222:319 */     return this.listaUbicacionActivoCombo;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setListaUbicacionActivoCombo(List<UbicacionActivo> listaUbicacionActivoCombo)
/* 226:    */   {
/* 227:329 */     this.listaUbicacionActivoCombo = listaUbicacionActivoCombo;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public ActivoFijo getActivoFijo()
/* 231:    */   {
/* 232:338 */     if (this.activoFijo == null) {
/* 233:339 */       this.activoFijo = new ActivoFijo();
/* 234:    */     }
/* 235:341 */     return this.activoFijo;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 239:    */   {
/* 240:351 */     this.activoFijo = activoFijo;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public Empleado getEmpleado()
/* 244:    */   {
/* 245:360 */     if (this.empleado == null) {
/* 246:361 */       this.empleado = new Empleado();
/* 247:    */     }
/* 248:363 */     return this.empleado;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setEmpleado(Empleado empleado)
/* 252:    */   {
/* 253:373 */     this.empleado = empleado;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public DataTable getDtCustodioActivoFijo()
/* 257:    */   {
/* 258:381 */     return this.dtCustodioActivoFijo;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setDtCustodioActivoFijo(DataTable dtCustodioActivoFijo)
/* 262:    */   {
/* 263:391 */     this.dtCustodioActivoFijo = dtCustodioActivoFijo;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public DataTable getDtDetalleCustodioActivoFijo()
/* 267:    */   {
/* 268:400 */     return this.dtDetalleCustodioActivoFijo;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setDtDetalleCustodioActivoFijo(DataTable dtDetalleCustodioActivoFijo)
/* 272:    */   {
/* 273:410 */     this.dtDetalleCustodioActivoFijo = dtDetalleCustodioActivoFijo;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 277:    */   {
/* 278:414 */     List<Empresa> lista = new ArrayList();
/* 279:    */     
/* 280:416 */     HashMap<String, String> filters = new HashMap();
/* 281:417 */     filters.put("nombreFiscal", "%" + consulta.trim());
/* 282:418 */     lista = this.servicioEmpresa.obtenerClientes("nombreFiscal", true, filters);
/* 283:    */     
/* 284:420 */     return lista;
/* 285:    */   }
/* 286:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.configuracion.CustodioActivoFijoBean
 * JD-Core Version:    0.7.0.1
 */