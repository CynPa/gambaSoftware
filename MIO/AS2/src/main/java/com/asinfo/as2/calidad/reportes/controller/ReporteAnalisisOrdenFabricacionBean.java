/*   1:    */ package com.asinfo.as2.calidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.calidad.VariableCalidad;
/*   6:    */ import com.asinfo.as2.entities.calidad.VariableCalidadOrdenFabricacion;
/*   7:    */ import com.asinfo.as2.entities.calidad.VariableCalidadProducto;
/*   8:    */ import com.asinfo.as2.entities.produccion.HistoricoCalidadOrdenFabricacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  10:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import java.text.SimpleDateFormat;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ReporteAnalisisOrdenFabricacionBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioGenerico<HistoricoCalidadOrdenFabricacion> servicioHistoricoCalidadOrdenFabricacion;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioGenerico<VariableCalidad> servicioVariableCalidad;
/*  35:    */   private LazyDataModel<HistoricoCalidadOrdenFabricacion> listaHistoricoCalidadMezcla;
/*  36:    */   private LazyDataModel<HistoricoCalidadOrdenFabricacion> listaHistoricoCalidadPT;
/*  37:    */   private DataTable dtHistoricoCalidadMezcla;
/*  38:    */   private DataTable dtHistoricoCalidadPT;
/*  39:    */   private List<VariableCalidad> listaVariableCalidad;
/*  40:    */   private Date fechaDesde;
/*  41:    */   private Date fechaHasta;
/*  42:    */   private Date ahora;
/*  43: 61 */   private int intervalo = 10;
/*  44:    */   
/*  45:    */   public void refrescar()
/*  46:    */   {
/*  47: 64 */     if (this.dtHistoricoCalidadMezcla != null) {
/*  48: 65 */       this.dtHistoricoCalidadMezcla.reset();
/*  49:    */     }
/*  50: 67 */     if (this.dtHistoricoCalidadPT != null) {
/*  51: 68 */       this.dtHistoricoCalidadPT.reset();
/*  52:    */     }
/*  53: 70 */     this.ahora = new Date();
/*  54:    */   }
/*  55:    */   
/*  56:    */   @PostConstruct
/*  57:    */   public void init()
/*  58:    */   {
/*  59: 75 */     this.fechaHasta = FuncionesUtiles.setAtributoFecha(new Date());
/*  60: 76 */     this.fechaDesde = FuncionesUtiles.setAtributoFecha(new Date());
/*  61: 77 */     this.fechaHasta = FuncionesUtiles.sumarFechaDiasMeses(this.fechaHasta, 1);
/*  62:    */     
/*  63: 79 */     this.listaHistoricoCalidadMezcla = new LazyDataModel()
/*  64:    */     {
/*  65:    */       private static final long serialVersionUID = 1L;
/*  66:    */       
/*  67:    */       public List<HistoricoCalidadOrdenFabricacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  68:    */       {
/*  69: 86 */         if (sortField == null)
/*  70:    */         {
/*  71: 87 */           sortField = "fechaControlCalidad";
/*  72: 88 */           sortOrder = SortOrder.DESCENDING;
/*  73:    */         }
/*  74: 90 */         List<HistoricoCalidadOrdenFabricacion> lista = new ArrayList();
/*  75: 91 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  76: 92 */         ReporteAnalisisOrdenFabricacionBean.this.agregarFiltroOrganizacion(filters);
/*  77: 93 */         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/*  78: 94 */         filters.put("fechaControlCalidad", OperacionEnum.BETWEEN.name() + sdf.format(ReporteAnalisisOrdenFabricacionBean.this.fechaDesde) + "~" + sdf.format(ReporteAnalisisOrdenFabricacionBean.this.fechaHasta));
/*  79: 95 */         filters.put("ordenFabricacion.indicadorSuborden", "true");
/*  80:    */         
/*  81: 97 */         List<String> listaCampos = new ArrayList();
/*  82: 98 */         listaCampos.add("ordenFabricacion.producto");
/*  83: 99 */         listaCampos.add("ordenFabricacion.bodega");
/*  84:100 */         listaCampos.add("lote");
/*  85:101 */         listaCampos.add("listaVariableCalidadOrdenFabricacion.variableCalidadProducto.variableCalidad");
/*  86:    */         
/*  87:103 */         lista = ReporteAnalisisOrdenFabricacionBean.this.servicioHistoricoCalidadOrdenFabricacion.obtenerListaPorPagina(HistoricoCalidadOrdenFabricacion.class, startIndex, pageSize, sortField, ordenar, filters, listaCampos);
/*  88:    */         
/*  89:105 */         ReporteAnalisisOrdenFabricacionBean.this.listaHistoricoCalidadMezcla.setRowCount(ReporteAnalisisOrdenFabricacionBean.this.servicioHistoricoCalidadOrdenFabricacion.contarPorCriterio(HistoricoCalidadOrdenFabricacion.class, filters));
/*  90:    */         
/*  91:    */ 
/*  92:108 */         return lista;
/*  93:    */       }
/*  94:111 */     };
/*  95:112 */     this.listaHistoricoCalidadPT = new LazyDataModel()
/*  96:    */     {
/*  97:    */       private static final long serialVersionUID = 1L;
/*  98:    */       
/*  99:    */       public List<HistoricoCalidadOrdenFabricacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 100:    */       {
/* 101:118 */         if (sortField == null)
/* 102:    */         {
/* 103:119 */           sortField = "fechaControlCalidad";
/* 104:120 */           sortOrder = SortOrder.DESCENDING;
/* 105:    */         }
/* 106:122 */         List<HistoricoCalidadOrdenFabricacion> lista = new ArrayList();
/* 107:123 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 108:124 */         ReporteAnalisisOrdenFabricacionBean.this.agregarFiltroOrganizacion(filters);
/* 109:125 */         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 110:126 */         filters.put("fechaControlCalidad", OperacionEnum.BETWEEN.name() + sdf.format(ReporteAnalisisOrdenFabricacionBean.this.fechaDesde) + "~" + sdf.format(ReporteAnalisisOrdenFabricacionBean.this.fechaHasta));
/* 111:127 */         filters.put("ordenFabricacion.indicadorSuborden", "false");
/* 112:    */         
/* 113:129 */         List<String> listaCampos = new ArrayList();
/* 114:130 */         listaCampos.add("ordenFabricacion.producto");
/* 115:131 */         listaCampos.add("ordenFabricacion.bodega");
/* 116:132 */         listaCampos.add("lote");
/* 117:133 */         listaCampos.add("listaVariableCalidadOrdenFabricacion.variableCalidadProducto.variableCalidad");
/* 118:    */         
/* 119:135 */         lista = ReporteAnalisisOrdenFabricacionBean.this.servicioHistoricoCalidadOrdenFabricacion.obtenerListaPorPagina(HistoricoCalidadOrdenFabricacion.class, startIndex, pageSize, sortField, ordenar, filters, listaCampos);
/* 120:    */         
/* 121:137 */         ReporteAnalisisOrdenFabricacionBean.this.listaHistoricoCalidadPT.setRowCount(ReporteAnalisisOrdenFabricacionBean.this.servicioHistoricoCalidadOrdenFabricacion.contarPorCriterio(HistoricoCalidadOrdenFabricacion.class, filters));
/* 122:    */         
/* 123:139 */         return lista;
/* 124:    */       }
/* 125:142 */     };
/* 126:143 */     refrescar();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String editar()
/* 130:    */   {
/* 131:148 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 132:149 */     return "";
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String guardar()
/* 136:    */   {
/* 137:154 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 138:    */     
/* 139:156 */     return "";
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String eliminar()
/* 143:    */   {
/* 144:161 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 145:162 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String limpiar()
/* 149:    */   {
/* 150:167 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String cargarDatos()
/* 154:    */   {
/* 155:172 */     return "";
/* 156:    */   }
/* 157:    */   
/* 158:    */   public LazyDataModel<HistoricoCalidadOrdenFabricacion> getListaHistoricoCalidadMezcla()
/* 159:    */   {
/* 160:176 */     return this.listaHistoricoCalidadMezcla;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setListaHistoricoCalidadMezcla(LazyDataModel<HistoricoCalidadOrdenFabricacion> listaHistoricoCalidadMezcla)
/* 164:    */   {
/* 165:180 */     this.listaHistoricoCalidadMezcla = listaHistoricoCalidadMezcla;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public LazyDataModel<HistoricoCalidadOrdenFabricacion> getListaHistoricoCalidadPT()
/* 169:    */   {
/* 170:184 */     return this.listaHistoricoCalidadPT;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setListaHistoricoCalidadPT(LazyDataModel<HistoricoCalidadOrdenFabricacion> listaHistoricoCalidadPT)
/* 174:    */   {
/* 175:188 */     this.listaHistoricoCalidadPT = listaHistoricoCalidadPT;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public DataTable getDtHistoricoCalidadMezcla()
/* 179:    */   {
/* 180:192 */     return this.dtHistoricoCalidadMezcla;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setDtHistoricoCalidadMezcla(DataTable dtHistoricoCalidadMezcla)
/* 184:    */   {
/* 185:196 */     this.dtHistoricoCalidadMezcla = dtHistoricoCalidadMezcla;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public DataTable getDtHistoricoCalidadPT()
/* 189:    */   {
/* 190:200 */     return this.dtHistoricoCalidadPT;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setDtHistoricoCalidadPT(DataTable dtHistoricoCalidadPT)
/* 194:    */   {
/* 195:204 */     this.dtHistoricoCalidadPT = dtHistoricoCalidadPT;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Date getFechaDesde()
/* 199:    */   {
/* 200:208 */     return this.fechaDesde;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setFechaDesde(Date fechaDesde)
/* 204:    */   {
/* 205:212 */     this.fechaDesde = fechaDesde;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Date getFechaHasta()
/* 209:    */   {
/* 210:216 */     return this.fechaHasta;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setFechaHasta(Date fechaHasta)
/* 214:    */   {
/* 215:220 */     this.fechaHasta = fechaHasta;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Date getAhora()
/* 219:    */   {
/* 220:224 */     return this.ahora;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setAhora(Date ahora)
/* 224:    */   {
/* 225:228 */     this.ahora = ahora;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<VariableCalidad> getListaVariableCalidad()
/* 229:    */   {
/* 230:232 */     if (this.listaVariableCalidad == null)
/* 231:    */     {
/* 232:233 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 233:234 */       filtros.put("activo", "true");
/* 234:235 */       filtros.put("indicadorProductoTerminado", "true");
/* 235:236 */       filtros.put("indicadorVisualizar", "true");
/* 236:237 */       this.listaVariableCalidad = this.servicioVariableCalidad.obtenerListaCombo(VariableCalidad.class, "nombre", true, filtros);
/* 237:    */     }
/* 238:239 */     return this.listaVariableCalidad;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setListaVariableCalidad(List<VariableCalidad> listaVariableCalidad)
/* 242:    */   {
/* 243:243 */     this.listaVariableCalidad = listaVariableCalidad;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public VariableCalidadOrdenFabricacion obtenerValorVariable(VariableCalidad variable, HistoricoCalidadOrdenFabricacion historico)
/* 247:    */   {
/* 248:247 */     for (VariableCalidadOrdenFabricacion detalle : historico.getListaVariableCalidadOrdenFabricacion()) {
/* 249:248 */       if (detalle.getVariableCalidadProducto().getVariableCalidad().getId() == variable.getId()) {
/* 250:249 */         return detalle;
/* 251:    */       }
/* 252:    */     }
/* 253:252 */     return null;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public int getIntervalo()
/* 257:    */   {
/* 258:256 */     return this.intervalo;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setIntervalo(int intervalo)
/* 262:    */   {
/* 263:260 */     this.intervalo = intervalo;
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.reportes.controller.ReporteAnalisisOrdenFabricacionBean
 * JD-Core Version:    0.7.0.1
 */