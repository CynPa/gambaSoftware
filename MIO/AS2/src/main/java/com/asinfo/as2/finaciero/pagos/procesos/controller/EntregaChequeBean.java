/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.dao.MovimientoBancarioDao;
/*   6:    */ import com.asinfo.as2.entities.EstadoCheque;
/*   7:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*   8:    */ import com.asinfo.as2.entities.MovimientoBancarioEstadoCheque;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioMovimientoBancario;
/*  12:    */ import com.asinfo.as2.financiero.pagos.configuracion.servicio.ServicioEstadoCheque;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.event.SelectEvent;
/*  23:    */ import org.primefaces.event.ToggleEvent;
/*  24:    */ import org.primefaces.model.LazyDataModel;
/*  25:    */ import org.primefaces.model.SortOrder;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class EntregaChequeBean
/*  30:    */   extends PageControllerAS2
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioMovimientoBancario servicioMovimientoBancario;
/*  35:    */   @EJB
/*  36:    */   private transient ServicioEstadoCheque servicioEstadoCheque;
/*  37:    */   @EJB
/*  38:    */   private transient MovimientoBancarioDao movimientoBancarioDao;
/*  39:    */   private DataTable dtMovimientoBancario;
/*  40:    */   private MovimientoBancario movimientoBancario;
/*  41:    */   private LazyDataModel<MovimientoBancario> listaCheque;
/*  42:    */   private EstadoCheque estadoCheque;
/*  43:    */   private String observacionCambioEstado;
/*  44:    */   private DataTable dtListaReporte;
/*  45: 61 */   private boolean validarEstadoCheque = false;
/*  46:    */   private EstadoCheque estadoChequeFinal;
/*  47:    */   List<EstadoCheque> listaEstadoCheque;
/*  48:    */   List<MovimientoBancarioEstadoCheque> listaEstadosCheque;
/*  49:    */   
/*  50:    */   @PostConstruct
/*  51:    */   public void init()
/*  52:    */   {
/*  53: 70 */     this.listaCheque = new LazyDataModel()
/*  54:    */     {
/*  55:    */       private static final long serialVersionUID = 1L;
/*  56:    */       
/*  57:    */       public List<MovimientoBancario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  58:    */       {
/*  59: 77 */         List<MovimientoBancario> lista = new ArrayList();
/*  60: 78 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  61: 80 */         if (filters.size() == 0) {
/*  62: 81 */           filters.put("estadoCheque.idEstadoCheque", String.valueOf(EntregaChequeBean.this.getEstadoChequeFinal().getId()));
/*  63:    */         }
/*  64: 83 */         EntregaChequeBean.this.agregarFiltroOrganizacion(filters);
/*  65: 84 */         filters.put("formaPago.indicadorCheque", "true");
/*  66: 85 */         filters.put("valor", "<0");
/*  67: 86 */         filters.put("estado", "!=ANULADO");
/*  68: 87 */         lista = EntregaChequeBean.this.servicioMovimientoBancario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  69: 88 */         EntregaChequeBean.this.listaCheque.setRowCount(EntregaChequeBean.this.servicioMovimientoBancario.contarPorCriterio(filters));
/*  70:    */         
/*  71: 90 */         return lista;
/*  72:    */       }
/*  73:    */     };
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String editar()
/*  77:    */   {
/*  78:102 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  79:103 */     return "";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String crear()
/*  83:    */   {
/*  84:108 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  85:109 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String guardar()
/*  89:    */   {
/*  90:119 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String limpiar()
/*  94:    */   {
/*  95:129 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String eliminar()
/*  99:    */   {
/* 100:139 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 101:140 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String cargarDatos()
/* 105:    */   {
/* 106:150 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void onRowSelect(SelectEvent event)
/* 110:    */   {
/* 111:157 */     MovimientoBancario MovimientoBancario1 = (MovimientoBancario)event.getObject();
/* 112:158 */     setMovimientoBancario(MovimientoBancario1);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public MovimientoBancario getMovimientoBancario()
/* 116:    */   {
/* 117:167 */     return this.movimientoBancario;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setMovimientoBancario(MovimientoBancario MovimientoBancario)
/* 121:    */   {
/* 122:177 */     this.movimientoBancario = MovimientoBancario;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public DataTable getDtMovimientoBancario()
/* 126:    */   {
/* 127:186 */     return this.dtMovimientoBancario;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setDtMovimientoBancario(DataTable dtMovimientoBancario)
/* 131:    */   {
/* 132:196 */     this.dtMovimientoBancario = dtMovimientoBancario;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public LazyDataModel<MovimientoBancario> getListaCheque()
/* 136:    */   {
/* 137:203 */     return this.listaCheque;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setListaCheque(LazyDataModel<MovimientoBancario> listaCheque)
/* 141:    */   {
/* 142:211 */     this.listaCheque = listaCheque;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List<EstadoCheque> getListaEstadoCheque()
/* 146:    */   {
/* 147:218 */     if (this.listaEstadoCheque == null) {
/* 148:219 */       this.listaEstadoCheque = this.servicioEstadoCheque.obtenerListaCombo("nombre", true, null);
/* 149:    */     }
/* 150:222 */     return this.listaEstadoCheque;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setListaEstadoCheque(List<EstadoCheque> listaEstadoCheque)
/* 154:    */   {
/* 155:230 */     this.listaEstadoCheque = listaEstadoCheque;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public EstadoCheque getEstadoCheque()
/* 159:    */   {
/* 160:237 */     return this.estadoCheque;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setEstadoCheque(EstadoCheque estadoCheque)
/* 164:    */   {
/* 165:245 */     this.estadoCheque = estadoCheque;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getObservacionCambioEstado()
/* 169:    */   {
/* 170:252 */     return this.observacionCambioEstado;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setObservacionCambioEstado(String observacionCambioEstado)
/* 174:    */   {
/* 175:260 */     this.observacionCambioEstado = observacionCambioEstado;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void seleccionarMovimientoBancario()
/* 179:    */   {
/* 180:264 */     this.estadoCheque = null;
/* 181:265 */     this.observacionCambioEstado = "";
/* 182:266 */     this.movimientoBancario = ((MovimientoBancario)this.dtMovimientoBancario.getRowData());
/* 183:267 */     this.validarEstadoCheque = true;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void cambiarEstado()
/* 187:    */   {
/* 188:    */     try
/* 189:    */     {
/* 190:272 */       this.servicioMovimientoBancario.cambiarEstado(this.movimientoBancario, this.estadoCheque, this.observacionCambioEstado);
/* 191:    */     }
/* 192:    */     catch (AS2Exception e)
/* 193:    */     {
/* 194:274 */       addErrorMessage(e.getMensaje());
/* 195:275 */       e.printStackTrace();
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public DataTable getDtListaReporte()
/* 200:    */   {
/* 201:283 */     return this.dtListaReporte;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setDtListaReporte(DataTable dtListaReporte)
/* 205:    */   {
/* 206:291 */     this.dtListaReporte = dtListaReporte;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public List<MovimientoBancarioEstadoCheque> getListaEstadosCheque()
/* 210:    */   {
/* 211:298 */     if (this.listaEstadosCheque == null) {
/* 212:299 */       this.listaEstadosCheque = new ArrayList();
/* 213:    */     }
/* 214:301 */     return this.listaEstadosCheque;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setListaEstadosCheque(List<MovimientoBancarioEstadoCheque> listaEstadosCheque)
/* 218:    */   {
/* 219:309 */     this.listaEstadosCheque = listaEstadosCheque;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void cargarEstadosCheque(ToggleEvent event)
/* 223:    */   {
/* 224:313 */     getListaEstadosCheque().clear();
/* 225:314 */     MovimientoBancario mb = (MovimientoBancario)event.getData();
/* 226:    */     try
/* 227:    */     {
/* 228:316 */       getListaEstadosCheque().addAll(this.movimientoBancarioDao.getListaEstadosCheque(mb));
/* 229:    */     }
/* 230:    */     catch (Exception e)
/* 231:    */     {
/* 232:318 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean isValidarEstadoCheque()
/* 237:    */   {
/* 238:326 */     return this.validarEstadoCheque;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setValidarEstadoCheque(boolean validarEstadoCheque)
/* 242:    */   {
/* 243:333 */     this.validarEstadoCheque = validarEstadoCheque;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public EstadoCheque getEstadoChequeFinal()
/* 247:    */   {
/* 248:340 */     if (this.estadoChequeFinal == null) {
/* 249:341 */       this.estadoChequeFinal = this.servicioEstadoCheque.getEstadoFinal(AppUtil.getOrganizacion().getId());
/* 250:    */     }
/* 251:344 */     return this.estadoChequeFinal;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setEstadoChequeFinal(EstadoCheque estadoChequeFinal)
/* 255:    */   {
/* 256:351 */     this.estadoChequeFinal = estadoChequeFinal;
/* 257:    */   }
/* 258:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.EntregaChequeBean
 * JD-Core Version:    0.7.0.1
 */