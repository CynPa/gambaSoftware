/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   5:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   6:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*   7:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*   8:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*   9:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  10:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*  11:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ManagedProperty;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.event.ToggleEvent;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ReporteOrdenFabricacionBean
/*  27:    */   extends AbstractBaseReportBean
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  30:    */   @EJB
/*  31:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  32:    */   @EJB
/*  33:    */   private ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  34:    */   @EJB
/*  35:    */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*  36:    */   @ManagedProperty("#{languageController}")
/*  37:    */   private LanguageController languageController;
/*  38: 71 */   private Date fechaHasta = new Date();
/*  39:    */   private TipoCicloProduccionEnum tipoCiclo;
/*  40:    */   private RutaFabricacion rutaFabricacion;
/*  41:    */   private EstadoProduccionEnum estado;
/*  42:    */   private List<OrdenFabricacion> listaOrdenFabricacion;
/*  43:    */   private List<OrdenFabricacion> listaOrdenFabricacionFiltrado;
/*  44:    */   private List<RutaFabricacion> listaRutaFabricacion;
/*  45:    */   private List<TipoCicloProduccionEnum> listaTipoCicloEnum;
/*  46:    */   private List<EstadoProduccionEnum> listaEstadoProduccionEnum;
/*  47:    */   private DataTable dtOrdenFabricacion;
/*  48:    */   private DataTable dtOperacionOrdenFabricacion;
/*  49:    */   private DataTable dtOrdenSalidaMaterial;
/*  50:    */   
/*  51:    */   public String limpiar()
/*  52:    */   {
/*  53: 95 */     this.listaOrdenFabricacion = null;
/*  54: 96 */     this.listaOrdenFabricacionFiltrado = null;
/*  55: 97 */     return "";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void procesar()
/*  59:    */   {
/*  60:    */     try
/*  61:    */     {
/*  62:103 */       this.listaOrdenFabricacion = this.servicioOrdenFabricacion.getConsultaOrdenFabricacion(this.fechaHasta, this.tipoCiclo, this.estado, this.rutaFabricacion);
/*  63:104 */       if (this.listaOrdenFabricacion.size() == 0)
/*  64:    */       {
/*  65:105 */         limpiar();
/*  66:106 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/*  67:107 */         return;
/*  68:    */       }
/*  69:109 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/*  70:    */     }
/*  71:    */     catch (Exception e)
/*  72:    */     {
/*  73:112 */       e.printStackTrace();
/*  74:113 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void cargarOrdenSalidaMaterial(ToggleEvent event)
/*  79:    */   {
/*  80:118 */     OrdenFabricacion ordenFabricacion = (OrdenFabricacion)event.getData();
/*  81:119 */     if (ordenFabricacion.getListaOrdenSalidaMaterial().isEmpty())
/*  82:    */     {
/*  83:120 */       List<OrdenSalidaMaterial> listaDetalle = this.servicioOrdenSalidaMaterial.getOrdenSalidaMaterialPorOrdenFabricacion(ordenFabricacion);
/*  84:121 */       ordenFabricacion.setListaOrdenSalidaMaterial(listaDetalle);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected JRDataSource getJRDataSource()
/*  89:    */   {
/*  90:128 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected String getCompileFileName()
/*  94:    */   {
/*  95:134 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String execute()
/*  99:    */   {
/* 100:140 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Date getFechaHasta()
/* 104:    */   {
/* 105:144 */     return this.fechaHasta;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setFechaHasta(Date fechaHasta)
/* 109:    */   {
/* 110:148 */     this.fechaHasta = fechaHasta;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public TipoCicloProduccionEnum getTipoCiclo()
/* 114:    */   {
/* 115:152 */     return this.tipoCiclo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setTipoCiclo(TipoCicloProduccionEnum tipoCiclo)
/* 119:    */   {
/* 120:156 */     this.tipoCiclo = tipoCiclo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public RutaFabricacion getRutaFabricacion()
/* 124:    */   {
/* 125:160 */     return this.rutaFabricacion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setRutaFabricacion(RutaFabricacion rutaFabricacion)
/* 129:    */   {
/* 130:164 */     this.rutaFabricacion = rutaFabricacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public EstadoProduccionEnum getEstado()
/* 134:    */   {
/* 135:168 */     return this.estado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setEstado(EstadoProduccionEnum estado)
/* 139:    */   {
/* 140:172 */     this.estado = estado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<OrdenFabricacion> getListaOrdenFabricacion()
/* 144:    */   {
/* 145:176 */     return this.listaOrdenFabricacion;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setListaOrdenFabricacion(List<OrdenFabricacion> listaOrdenFabricacion)
/* 149:    */   {
/* 150:180 */     this.listaOrdenFabricacion = listaOrdenFabricacion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<OrdenFabricacion> getListaOrdenFabricacionFiltrado()
/* 154:    */   {
/* 155:184 */     return this.listaOrdenFabricacionFiltrado;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setListaOrdenFabricacionFiltrado(List<OrdenFabricacion> listaOrdenFabricacionFiltrado)
/* 159:    */   {
/* 160:188 */     this.listaOrdenFabricacionFiltrado = listaOrdenFabricacionFiltrado;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public List<TipoCicloProduccionEnum> getListaTipoCicloEnum()
/* 164:    */   {
/* 165:192 */     if (this.listaTipoCicloEnum == null)
/* 166:    */     {
/* 167:193 */       this.listaTipoCicloEnum = new ArrayList();
/* 168:194 */       for (TipoCicloProduccionEnum tipoCiclo : TipoCicloProduccionEnum.values()) {
/* 169:195 */         this.listaTipoCicloEnum.add(tipoCiclo);
/* 170:    */       }
/* 171:    */     }
/* 172:198 */     return this.listaTipoCicloEnum;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<RutaFabricacion> getListaRutaFabricacion()
/* 176:    */   {
/* 177:202 */     if (this.listaRutaFabricacion == null) {
/* 178:203 */       this.listaRutaFabricacion = this.servicioRutaFabricacion.obtenerListaCombo("nombre", true, null);
/* 179:    */     }
/* 180:205 */     return this.listaRutaFabricacion;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List<EstadoProduccionEnum> getListaEstadoProduccionEnum()
/* 184:    */   {
/* 185:209 */     if (this.listaEstadoProduccionEnum == null)
/* 186:    */     {
/* 187:210 */       this.listaEstadoProduccionEnum = new ArrayList();
/* 188:211 */       for (EstadoProduccionEnum estadoProduccionEnum : EstadoProduccionEnum.values()) {
/* 189:212 */         this.listaEstadoProduccionEnum.add(estadoProduccionEnum);
/* 190:    */       }
/* 191:    */     }
/* 192:215 */     return this.listaEstadoProduccionEnum;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public DataTable getDtOrdenFabricacion()
/* 196:    */   {
/* 197:219 */     return this.dtOrdenFabricacion;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setDtOrdenFabricacion(DataTable dtOrdenFabricacion)
/* 201:    */   {
/* 202:223 */     this.dtOrdenFabricacion = dtOrdenFabricacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public DataTable getDtOperacionOrdenFabricacion()
/* 206:    */   {
/* 207:227 */     return this.dtOperacionOrdenFabricacion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setDtOperacionOrdenFabricacion(DataTable dtOperacionOrdenFabricacion)
/* 211:    */   {
/* 212:231 */     this.dtOperacionOrdenFabricacion = dtOperacionOrdenFabricacion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public DataTable getDtOrdenSalidaMaterial()
/* 216:    */   {
/* 217:235 */     return this.dtOrdenSalidaMaterial;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setDtOrdenSalidaMaterial(DataTable dtOrdenSalidaMaterial)
/* 221:    */   {
/* 222:239 */     this.dtOrdenSalidaMaterial = dtOrdenSalidaMaterial;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public LanguageController getLanguageController()
/* 226:    */   {
/* 227:243 */     return this.languageController;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setLanguageController(LanguageController languageController)
/* 231:    */   {
/* 232:247 */     this.languageController = languageController;
/* 233:    */   }
/* 234:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteOrdenFabricacionBean
 * JD-Core Version:    0.7.0.1
 */