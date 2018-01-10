/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.ConfiguracionExtractoBancario;
/*   6:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   7:    */ import com.asinfo.as2.entities.CuentaContable;
/*   8:    */ import com.asinfo.as2.entities.DetalleConfiguracionExtractoBancario;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioExtractoBancario;
/*  16:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.JsfUtil;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ManagedProperty;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import javax.faces.model.SelectItem;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.component.datatable.DataTable;
/*  30:    */ import org.primefaces.event.SelectEvent;
/*  31:    */ import org.primefaces.model.LazyDataModel;
/*  32:    */ import org.primefaces.model.SortOrder;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class ConfiguracionExtractoBancarioBean
/*  37:    */   extends PageControllerAS2
/*  38:    */ {
/*  39:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  40:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  41:    */   private DataTable dtConfiguracionExtracto;
/*  42: 74 */   private int opcionCuenta = 0;
/*  43:    */   private List<SelectItem> listaOpercionEnum;
/*  44:    */   private DetalleConfiguracionExtractoBancario detalleExtractoBancarioSeleccionado;
/*  45:    */   private List<DetalleConfiguracionExtractoBancario> listaDetalleConfiguracionExtractoBancario;
/*  46:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  47:    */   private LazyDataModel<ConfiguracionExtractoBancario> listaConfiguracionExtracto;
/*  48:    */   private ConfiguracionExtractoBancario configuracionExtracto;
/*  49:    */   @EJB
/*  50:    */   private ServicioExtractoBancario servicioExtractoBancario;
/*  51:    */   @EJB
/*  52:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  53:    */   @EJB
/*  54:    */   private ServicioGenerico<ConfiguracionExtractoBancario> servicioConfiguracionExtractoBancario;
/*  55: 83 */   public static List<String> listaCampos = new ArrayList();
/*  56:    */   private static final long serialVersionUID = 1L;
/*  57:    */   
/*  58:    */   static
/*  59:    */   {
/*  60: 84 */     listaCampos.add("cuentaBancariaOrganizacion.cuentaBancaria");
/*  61:    */   }
/*  62:    */   
/*  63:    */   @PostConstruct
/*  64:    */   public void init()
/*  65:    */   {
/*  66: 89 */     this.listaConfiguracionExtracto = new LazyDataModel()
/*  67:    */     {
/*  68:    */       private static final long serialVersionUID = 1L;
/*  69:    */       
/*  70:    */       public List<ConfiguracionExtractoBancario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  71:    */       {
/*  72: 96 */         List<ConfiguracionExtractoBancario> lista = new ArrayList();
/*  73:    */         
/*  74: 98 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  75:    */         try
/*  76:    */         {
/*  77:101 */           lista = ConfiguracionExtractoBancarioBean.this.servicioConfiguracionExtractoBancario.obtenerListaPorPagina(ConfiguracionExtractoBancario.class, startIndex, pageSize, sortField, ordenar, filters, ConfiguracionExtractoBancarioBean.listaCampos);
/*  78:    */         }
/*  79:    */         catch (Exception e)
/*  80:    */         {
/*  81:105 */           e.printStackTrace();
/*  82:    */         }
/*  83:107 */         ConfiguracionExtractoBancarioBean.this.listaConfiguracionExtracto.setRowCount(ConfiguracionExtractoBancarioBean.this.servicioConfiguracionExtractoBancario.contarPorCriterio(ConfiguracionExtractoBancario.class, filters));
/*  84:    */         
/*  85:109 */         return lista;
/*  86:    */       }
/*  87:    */     };
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String editar()
/*  91:    */   {
/*  92:117 */     if (getConfiguracionExtracto().getId() != 0)
/*  93:    */     {
/*  94:119 */       setearTablas();
/*  95:    */       
/*  96:121 */       List<String> listaCampos = new ArrayList();
/*  97:122 */       listaCampos.add("cuentaBancariaOrganizacion");
/*  98:123 */       listaCampos.add("listaDetalleConfiguracionExtractoBancario.cuentaContable");
/*  99:    */       
/* 100:125 */       this.configuracionExtracto = this.servicioExtractoBancario.cargarDetalle(getConfiguracionExtracto());
/* 101:    */       
/* 102:127 */       setEditado(true);
/* 103:    */     }
/* 104:    */     else
/* 105:    */     {
/* 106:130 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 107:    */     }
/* 108:133 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String guardar()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:140 */       this.servicioConfiguracionExtractoBancario.guardarValidar(getConfiguracionExtracto(), getConfiguracionExtracto()
/* 116:141 */         .getListaDetalleConfiguracionExtractoBancario());
/* 117:    */       
/* 118:143 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 119:    */       
/* 120:145 */       limpiar();
/* 121:    */       
/* 122:147 */       setEditado(false);
/* 123:    */     }
/* 124:    */     catch (AS2Exception ex)
/* 125:    */     {
/* 126:150 */       JsfUtil.addErrorMessage(ex, "");
/* 127:    */     }
/* 128:    */     catch (Exception ex)
/* 129:    */     {
/* 130:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 131:153 */       LOG.error("ERROR AL GUARDAR DATOS CONFIGURACION EXTRACTO BANCARIO", ex);
/* 132:    */     }
/* 133:156 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String eliminar()
/* 137:    */   {
/* 138:162 */     return null;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String limpiar()
/* 142:    */   {
/* 143:168 */     this.configuracionExtracto = new ConfiguracionExtractoBancario();
/* 144:169 */     this.configuracionExtracto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 145:170 */     this.configuracionExtracto.setIdSucursal(AppUtil.getSucursal().getId());
/* 146:171 */     this.configuracionExtracto.setActivo(true);
/* 147:    */     
/* 148:173 */     setearTablas();
/* 149:    */     
/* 150:175 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   private void setearTablas()
/* 154:    */   {
/* 155:179 */     if (this.dtConfiguracionExtracto != null) {
/* 156:180 */       this.dtConfiguracionExtracto.reset();
/* 157:    */     }
/* 158:182 */     this.listaDetalleConfiguracionExtractoBancario = null;
/* 159:183 */     this.detalleExtractoBancarioSeleccionado = null;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String cargarDatos()
/* 163:    */   {
/* 164:188 */     return "";
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void cargarCuentaContable(SelectEvent event)
/* 168:    */   {
/* 169:192 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/* 170:193 */     if (getOpcionCuenta() == 1) {
/* 171:194 */       this.detalleExtractoBancarioSeleccionado.setCuentaContable(cuentaContable);
/* 172:    */     } else {
/* 173:196 */       this.detalleExtractoBancarioSeleccionado.setCuentaContable2(cuentaContable);
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void agregarDetalleListener()
/* 178:    */   {
/* 179:201 */     DetalleConfiguracionExtractoBancario dceb = new DetalleConfiguracionExtractoBancario();
/* 180:202 */     dceb.setIdOrganizacion(getConfiguracionExtracto().getIdOrganizacion());
/* 181:203 */     dceb.setConfiguracionExtractoBancario(getConfiguracionExtracto());
/* 182:204 */     getConfiguracionExtracto().getListaDetalleConfiguracionExtractoBancario().add(dceb);
/* 183:205 */     getListaDetalleConfiguracionExtractoBancario().add(dceb);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void eliminarDetalle(DetalleConfiguracionExtractoBancario dceb)
/* 187:    */   {
/* 188:210 */     dceb.setEliminado(true);
/* 189:211 */     this.listaDetalleConfiguracionExtractoBancario = null;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void eliminarCuenta(DetalleConfiguracionExtractoBancario dceb, int i)
/* 193:    */   {
/* 194:215 */     if (i == 1) {
/* 195:216 */       dceb.setCuentaContable(null);
/* 196:    */     } else {
/* 197:218 */       dceb.setCuentaContable2(null);
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public static List<String> getListaCampos()
/* 202:    */   {
/* 203:222 */     return listaCampos;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static void setListaCampos(List<String> listaCampos)
/* 207:    */   {
/* 208:226 */     listaCampos = listaCampos;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public ConfiguracionExtractoBancario getConfiguracionExtracto()
/* 212:    */   {
/* 213:230 */     return this.configuracionExtracto;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setConfiguracionExtracto(ConfiguracionExtractoBancario configuracionExtracto)
/* 217:    */   {
/* 218:234 */     this.configuracionExtracto = configuracionExtracto;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public LazyDataModel<ConfiguracionExtractoBancario> getListaConfiguracionExtracto()
/* 222:    */   {
/* 223:238 */     return this.listaConfiguracionExtracto;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setListaConfiguracionExtracto(LazyDataModel<ConfiguracionExtractoBancario> listaConfiguracionExtracto)
/* 227:    */   {
/* 228:242 */     this.listaConfiguracionExtracto = listaConfiguracionExtracto;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 232:    */   {
/* 233:246 */     if (this.listaCuentaBancariaOrganizacion == null)
/* 234:    */     {
/* 235:247 */       this.listaCuentaBancariaOrganizacion = new ArrayList();
/* 236:248 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 237:249 */       filters.put("tipoCuentaBancariaOrganizacion", TipoCuentaBancariaOrganizacion.BANCO.toString());
/* 238:250 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, filters);
/* 239:    */     }
/* 240:252 */     return this.listaCuentaBancariaOrganizacion;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 244:    */   {
/* 245:256 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public List<DetalleConfiguracionExtractoBancario> getListaDetalleConfiguracionExtractoBancario()
/* 249:    */   {
/* 250:260 */     if (this.listaDetalleConfiguracionExtractoBancario == null)
/* 251:    */     {
/* 252:261 */       this.listaDetalleConfiguracionExtractoBancario = new ArrayList();
/* 253:262 */       for (DetalleConfiguracionExtractoBancario dceb : getConfiguracionExtracto().getListaDetalleConfiguracionExtractoBancario()) {
/* 254:263 */         if (!dceb.isEliminado()) {
/* 255:264 */           this.listaDetalleConfiguracionExtractoBancario.add(dceb);
/* 256:    */         }
/* 257:    */       }
/* 258:    */     }
/* 259:268 */     return this.listaDetalleConfiguracionExtractoBancario;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setListaDetalleConfiguracionExtractoBancario(List<DetalleConfiguracionExtractoBancario> listaDetalleConfiguracionExtractoBancario)
/* 263:    */   {
/* 264:272 */     this.listaDetalleConfiguracionExtractoBancario = listaDetalleConfiguracionExtractoBancario;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public DataTable getDtConfiguracionExtracto()
/* 268:    */   {
/* 269:276 */     return this.dtConfiguracionExtracto;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setDtConfiguracionExtracto(DataTable dtConfiguracionExtracto)
/* 273:    */   {
/* 274:280 */     this.dtConfiguracionExtracto = dtConfiguracionExtracto;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 278:    */   {
/* 279:284 */     return this.listaCuentaContableBean;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 283:    */   {
/* 284:288 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public DetalleConfiguracionExtractoBancario getDetalleExtractoBancarioSeleccionado()
/* 288:    */   {
/* 289:292 */     return this.detalleExtractoBancarioSeleccionado;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setDetalleExtractoBancarioSeleccionado(DetalleConfiguracionExtractoBancario detalleExtractoBancarioSeleccionado)
/* 293:    */   {
/* 294:296 */     this.detalleExtractoBancarioSeleccionado = detalleExtractoBancarioSeleccionado;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<SelectItem> getListaOpercionEnum()
/* 298:    */   {
/* 299:300 */     if (this.listaOpercionEnum == null)
/* 300:    */     {
/* 301:301 */       this.listaOpercionEnum = new ArrayList();
/* 302:302 */       this.listaOpercionEnum.add(new SelectItem(OperacionEnum.MENOR, OperacionEnum.MENOR.toString()));
/* 303:303 */       this.listaOpercionEnum.add(new SelectItem(OperacionEnum.MAYOR_IGUAL, OperacionEnum.MAYOR_IGUAL.toString()));
/* 304:    */     }
/* 305:306 */     return this.listaOpercionEnum;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setListaOpercionEnum(List<SelectItem> listaOpercionEnum)
/* 309:    */   {
/* 310:310 */     this.listaOpercionEnum = listaOpercionEnum;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public int getOpcionCuenta()
/* 314:    */   {
/* 315:314 */     return this.opcionCuenta;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setOpcionCuenta(int opcionCuenta)
/* 319:    */   {
/* 320:318 */     this.opcionCuenta = opcionCuenta;
/* 321:    */   }
/* 322:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ConfiguracionExtractoBancarioBean
 * JD-Core Version:    0.7.0.1
 */