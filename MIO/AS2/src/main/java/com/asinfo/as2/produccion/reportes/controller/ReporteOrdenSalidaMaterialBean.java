/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   5:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*   8:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*   9:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*  10:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.List;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ManagedProperty;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.event.ToggleEvent;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class ReporteOrdenSalidaMaterialBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  29:    */   @EJB
/*  30:    */   private ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  31:    */   @EJB
/*  32:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  33:    */   @EJB
/*  34:    */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*  35:    */   @ManagedProperty("#{languageController}")
/*  36:    */   private LanguageController languageController;
/*  37: 70 */   private Date fechaHasta = new Date();
/*  38:    */   private TipoCicloProduccionEnum tipoCiclo;
/*  39:    */   private Estado estado;
/*  40:    */   private boolean transferencia;
/*  41:    */   private List<OrdenSalidaMaterial> listaOrdenSalidaMaterial;
/*  42:    */   private List<OrdenSalidaMaterial> listaOrdenSalidaMaterialFiltrado;
/*  43:    */   private List<TipoCicloProduccionEnum> listaTipoCicloEnum;
/*  44:    */   private List<Estado> listaEstadoEnum;
/*  45:    */   private DataTable dtOrdenSalidaMaterial;
/*  46:    */   private DataTable dtOrdenFabricacion;
/*  47:    */   
/*  48:    */   public String limpiar()
/*  49:    */   {
/*  50: 92 */     this.listaOrdenSalidaMaterial = null;
/*  51: 93 */     this.listaOrdenSalidaMaterialFiltrado = null;
/*  52: 94 */     return "";
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void procesar()
/*  56:    */   {
/*  57:    */     try
/*  58:    */     {
/*  59:100 */       this.listaOrdenSalidaMaterial = this.servicioOrdenSalidaMaterial.getConsultaOrdenSalidaMaterial(this.fechaHasta, this.tipoCiclo, this.estado, this.transferencia);
/*  60:101 */       if (this.listaOrdenSalidaMaterial.size() == 0)
/*  61:    */       {
/*  62:102 */         limpiar();
/*  63:103 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/*  64:104 */         return;
/*  65:    */       }
/*  66:106 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/*  67:    */     }
/*  68:    */     catch (Exception e)
/*  69:    */     {
/*  70:109 */       e.printStackTrace();
/*  71:110 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void cargarOrdenFabricacion(ToggleEvent event)
/*  76:    */   {
/*  77:115 */     OrdenSalidaMaterial ordenSalidaMaterial = (OrdenSalidaMaterial)event.getData();
/*  78:116 */     if (ordenSalidaMaterial.getListaOrdenFabricacion().isEmpty())
/*  79:    */     {
/*  80:117 */       List<OrdenFabricacion> listaDetalle = this.servicioOrdenFabricacion.getOrdenFabricacionPorOrdenSalidaMaterial(ordenSalidaMaterial);
/*  81:118 */       ordenSalidaMaterial.setListaOrdenFabricacion(listaDetalle);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected JRDataSource getJRDataSource()
/*  86:    */   {
/*  87:125 */     return null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected String getCompileFileName()
/*  91:    */   {
/*  92:131 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String execute()
/*  96:    */   {
/*  97:137 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Date getFechaHasta()
/* 101:    */   {
/* 102:141 */     return this.fechaHasta;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setFechaHasta(Date fechaHasta)
/* 106:    */   {
/* 107:145 */     this.fechaHasta = fechaHasta;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public TipoCicloProduccionEnum getTipoCiclo()
/* 111:    */   {
/* 112:149 */     return this.tipoCiclo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setTipoCiclo(TipoCicloProduccionEnum tipoCiclo)
/* 116:    */   {
/* 117:153 */     this.tipoCiclo = tipoCiclo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Estado getEstado()
/* 121:    */   {
/* 122:157 */     return this.estado;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setEstado(Estado estado)
/* 126:    */   {
/* 127:161 */     this.estado = estado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public ServicioOrdenSalidaMaterial getServicioOrdenSalidaMaterial()
/* 131:    */   {
/* 132:165 */     return this.servicioOrdenSalidaMaterial;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setServicioOrdenSalidaMaterial(ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial)
/* 136:    */   {
/* 137:169 */     this.servicioOrdenSalidaMaterial = servicioOrdenSalidaMaterial;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public ServicioRutaFabricacion getServicioRutaFabricacion()
/* 141:    */   {
/* 142:173 */     return this.servicioRutaFabricacion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setServicioRutaFabricacion(ServicioRutaFabricacion servicioRutaFabricacion)
/* 146:    */   {
/* 147:177 */     this.servicioRutaFabricacion = servicioRutaFabricacion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isTransferencia()
/* 151:    */   {
/* 152:181 */     return this.transferencia;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setTransferencia(boolean transferencia)
/* 156:    */   {
/* 157:185 */     this.transferencia = transferencia;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List<OrdenSalidaMaterial> getListaOrdenSalidaMaterial()
/* 161:    */   {
/* 162:189 */     return this.listaOrdenSalidaMaterial;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setListaOrdenSalidaMaterial(List<OrdenSalidaMaterial> listaOrdenSalidaMaterial)
/* 166:    */   {
/* 167:193 */     this.listaOrdenSalidaMaterial = listaOrdenSalidaMaterial;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<OrdenSalidaMaterial> getListaOrdenSalidaMaterialFiltrado()
/* 171:    */   {
/* 172:197 */     return this.listaOrdenSalidaMaterialFiltrado;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setListaOrdenSalidaMaterialFiltrado(List<OrdenSalidaMaterial> listaOrdenSalidaMaterialFiltrado)
/* 176:    */   {
/* 177:201 */     this.listaOrdenSalidaMaterialFiltrado = listaOrdenSalidaMaterialFiltrado;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public List<TipoCicloProduccionEnum> getListaTipoCicloEnum()
/* 181:    */   {
/* 182:205 */     if (this.listaTipoCicloEnum == null)
/* 183:    */     {
/* 184:206 */       this.listaTipoCicloEnum = new ArrayList();
/* 185:207 */       for (TipoCicloProduccionEnum tipoCiclo : TipoCicloProduccionEnum.values()) {
/* 186:208 */         this.listaTipoCicloEnum.add(tipoCiclo);
/* 187:    */       }
/* 188:    */     }
/* 189:211 */     return this.listaTipoCicloEnum;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<Estado> getListaEstadoEnum()
/* 193:    */   {
/* 194:215 */     if (this.listaEstadoEnum == null)
/* 195:    */     {
/* 196:216 */       this.listaEstadoEnum = new ArrayList();
/* 197:217 */       this.listaEstadoEnum.add(Estado.ANULADO);
/* 198:218 */       this.listaEstadoEnum.add(Estado.ELABORADO);
/* 199:219 */       this.listaEstadoEnum.add(Estado.PROCESADO);
/* 200:220 */       this.listaEstadoEnum.add(Estado.CERRADO);
/* 201:    */     }
/* 202:222 */     return this.listaEstadoEnum;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public LanguageController getLanguageController()
/* 206:    */   {
/* 207:226 */     return this.languageController;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setLanguageController(LanguageController languageController)
/* 211:    */   {
/* 212:230 */     this.languageController = languageController;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public DataTable getDtOrdenSalidaMaterial()
/* 216:    */   {
/* 217:234 */     return this.dtOrdenSalidaMaterial;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setDtOrdenSalidaMaterial(DataTable dtOrdenSalidaMaterial)
/* 221:    */   {
/* 222:238 */     this.dtOrdenSalidaMaterial = dtOrdenSalidaMaterial;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public DataTable getDtOrdenFabricacion()
/* 226:    */   {
/* 227:242 */     return this.dtOrdenFabricacion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setDtOrdenFabricacion(DataTable dtOrdenFabricacion)
/* 231:    */   {
/* 232:246 */     this.dtOrdenFabricacion = dtOrdenFabricacion;
/* 233:    */   }
/* 234:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteOrdenSalidaMaterialBean
 * JD-Core Version:    0.7.0.1
 */