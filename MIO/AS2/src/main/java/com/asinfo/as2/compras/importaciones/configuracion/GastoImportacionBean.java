/*   1:    */ package com.asinfo.as2.compras.importaciones.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioGastoImportacion;
/*   4:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioGrupoGastoImportacion;
/*   5:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioTipoTramiteImportacion;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   8:    */ import com.asinfo.as2.entities.GastoImportacion;
/*   9:    */ import com.asinfo.as2.entities.GrupoGastoImportacion;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.TipoTramiteImportacion;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoProrrateoEnum;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import java.math.BigDecimal;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import javax.faces.model.SelectItem;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class GastoImportacionBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 4483308434004404328L;
/*  35:    */   @EJB
/*  36:    */   private transient ServicioGastoImportacion servicioGastoImportacion;
/*  37:    */   @EJB
/*  38:    */   private transient ServicioGrupoGastoImportacion servicioGrupoGastoImportacion;
/*  39:    */   @EJB
/*  40:    */   private transient ServicioTipoTramiteImportacion servicioTipoTramiteImportacion;
/*  41:    */   private GastoImportacion gastoImportacion;
/*  42:    */   private GrupoGastoImportacion grupoGastoImportacion;
/*  43:    */   private TipoTramiteImportacion tipoTramiteImportacion;
/*  44:    */   private LazyDataModel<GastoImportacion> listaGastoImportacion;
/*  45:    */   private List<GrupoGastoImportacion> listaGrupoGastoImportacion;
/*  46:    */   private List<TipoTramiteImportacion> listaTipoTramiteImportacion;
/*  47:    */   private List<SelectItem> listaTipoProrrateo;
/*  48:    */   private DataTable dtGastoImportacion;
/*  49:    */   
/*  50:    */   @PostConstruct
/*  51:    */   public void init()
/*  52:    */   {
/*  53: 87 */     this.listaGastoImportacion = new LazyDataModel()
/*  54:    */     {
/*  55:    */       private static final long serialVersionUID = 6332796485708443987L;
/*  56:    */       
/*  57:    */       public List<GastoImportacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  58:    */       {
/*  59: 94 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  60:    */         
/*  61: 96 */         List<GastoImportacion> lista = GastoImportacionBean.this.servicioGastoImportacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  62:    */         
/*  63: 98 */         GastoImportacionBean.this.listaGastoImportacion.setRowCount(GastoImportacionBean.this.servicioGastoImportacion.contarPorCriterio(filters));
/*  64: 99 */         return lista;
/*  65:    */       }
/*  66:    */     };
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void actualizaPorcentajeValorManual()
/*  70:    */   {
/*  71:110 */     if (this.gastoImportacion.isIndicadorCalculoAutomatico()) {
/*  72:111 */       this.gastoImportacion.setValorManual(BigDecimal.ZERO);
/*  73:    */     } else {
/*  74:113 */       this.gastoImportacion.setPorcentaje(BigDecimal.ZERO);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void actualizaPorcentaje()
/*  79:    */   {
/*  80:118 */     if (!this.gastoImportacion.isIndicadorCalculoAutomatico())
/*  81:    */     {
/*  82:119 */       this.gastoImportacion.setValorManual(BigDecimal.ZERO);
/*  83:120 */       this.gastoImportacion.setPorcentaje(BigDecimal.ZERO);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void procesarPorcentaje()
/*  88:    */   {
/*  89:125 */     if ((this.gastoImportacion.getPorcentaje() != null) && (this.gastoImportacion.getPorcentaje().compareTo(BigDecimal.ZERO) != 0)) {
/*  90:126 */       this.gastoImportacion.setValorManual(BigDecimal.ZERO);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void procesarValorManual()
/*  95:    */   {
/*  96:131 */     if ((this.gastoImportacion.getValorManual() != null) && (this.gastoImportacion.getValorManual().compareTo(BigDecimal.ZERO) != 0)) {
/*  97:132 */       this.gastoImportacion.setPorcentaje(BigDecimal.ZERO);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void actualizaValorManual()
/* 102:    */   {
/* 103:137 */     this.gastoImportacion.setValorManual(BigDecimal.ZERO);
/* 104:    */   }
/* 105:    */   
/* 106:    */   private void crearEntidad()
/* 107:    */   {
/* 108:144 */     this.gastoImportacion = new GastoImportacion();
/* 109:145 */     this.gastoImportacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 110:146 */     this.gastoImportacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String editar()
/* 114:    */   {
/* 115:156 */     if (getGastoImportacion().getIdGastoImportacion() > 0) {
/* 116:157 */       setEditado(true);
/* 117:    */     } else {
/* 118:159 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 119:    */     }
/* 120:161 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String guardar()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:171 */       this.servicioGastoImportacion.guardar(this.gastoImportacion);
/* 128:172 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 129:173 */       setEditado(false);
/* 130:174 */       limpiar();
/* 131:    */     }
/* 132:    */     catch (Exception e)
/* 133:    */     {
/* 134:176 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 135:177 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 136:    */     }
/* 137:179 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String eliminar()
/* 141:    */   {
/* 142:    */     try
/* 143:    */     {
/* 144:189 */       this.servicioGastoImportacion.eliminar(this.gastoImportacion);
/* 145:190 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 146:    */     }
/* 147:    */     catch (Exception e)
/* 148:    */     {
/* 149:192 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 150:193 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 151:    */     }
/* 152:195 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String cargarDatos()
/* 156:    */   {
/* 157:204 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String limpiar()
/* 161:    */   {
/* 162:213 */     crearEntidad();
/* 163:214 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public GrupoGastoImportacion getGrupoGastoImportacion()
/* 167:    */   {
/* 168:231 */     return this.grupoGastoImportacion;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public GastoImportacion getGastoImportacion()
/* 172:    */   {
/* 173:240 */     return this.gastoImportacion;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setGastoImportacion(GastoImportacion gastoImportacion)
/* 177:    */   {
/* 178:250 */     this.gastoImportacion = gastoImportacion;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setGrupoGastoImportacion(GrupoGastoImportacion grupoGastoImportacion)
/* 182:    */   {
/* 183:260 */     this.grupoGastoImportacion = grupoGastoImportacion;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public TipoTramiteImportacion getTipoTramiteImportacion()
/* 187:    */   {
/* 188:269 */     return this.tipoTramiteImportacion;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setTipoTramiteImportacion(TipoTramiteImportacion tipoTramiteImportacion)
/* 192:    */   {
/* 193:279 */     this.tipoTramiteImportacion = tipoTramiteImportacion;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public LazyDataModel<GastoImportacion> getListaGastoImportacion()
/* 197:    */   {
/* 198:288 */     return this.listaGastoImportacion;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setListaGastoImportacion(LazyDataModel<GastoImportacion> listaGastoImportacion)
/* 202:    */   {
/* 203:298 */     this.listaGastoImportacion = listaGastoImportacion;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public DataTable getDtGastoImportacion()
/* 207:    */   {
/* 208:307 */     return this.dtGastoImportacion;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setDtGastoImportacion(DataTable dtGastoImportacion)
/* 212:    */   {
/* 213:317 */     this.dtGastoImportacion = dtGastoImportacion;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public List<GrupoGastoImportacion> getListaGrupoGastoImportacion()
/* 217:    */   {
/* 218:326 */     if (this.listaGrupoGastoImportacion == null) {
/* 219:327 */       this.listaGrupoGastoImportacion = this.servicioGrupoGastoImportacion.obtenerListaCombo(null, false, null);
/* 220:    */     }
/* 221:329 */     return this.listaGrupoGastoImportacion;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public List<TipoTramiteImportacion> getListaTipoTramiteImportacion()
/* 225:    */   {
/* 226:338 */     if (this.listaTipoTramiteImportacion == null) {
/* 227:339 */       this.listaTipoTramiteImportacion = this.servicioTipoTramiteImportacion.obtenerListaCombo(null, false, null);
/* 228:    */     }
/* 229:341 */     return this.listaTipoTramiteImportacion;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public List<SelectItem> getListaTipoProrrateo()
/* 233:    */   {
/* 234:345 */     if (this.listaTipoProrrateo == null)
/* 235:    */     {
/* 236:346 */       this.listaTipoProrrateo = new ArrayList();
/* 237:347 */       for (TipoProrrateoEnum tipoProrrateo : TipoProrrateoEnum.values())
/* 238:    */       {
/* 239:348 */         SelectItem item = new SelectItem(tipoProrrateo, tipoProrrateo.getNombre());
/* 240:349 */         this.listaTipoProrrateo.add(item);
/* 241:    */       }
/* 242:    */     }
/* 243:352 */     return this.listaTipoProrrateo;
/* 244:    */   }
/* 245:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.GastoImportacionBean
 * JD-Core Version:    0.7.0.1
 */