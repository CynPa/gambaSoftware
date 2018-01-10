/*   1:    */ package com.asinfo.as2.compras.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioSolicitudCompra;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.DetalleSolicitudCompra;
/*   7:    */ import com.asinfo.as2.entities.SolicitudCompra;
/*   8:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   9:    */ import com.asinfo.as2.enumeraciones.EstadoSolicitudCompraEnum;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Arrays;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.faces.model.SelectItem;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ import org.primefaces.event.ToggleEvent;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class AprobacionSolicitudCompraBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 725178972436303995L;
/*  35:    */   @EJB
/*  36:    */   private ServicioSolicitudCompra servicioSolicitudCompra;
/*  37:    */   private SolicitudCompra solicitudCompra;
/*  38:    */   private String mails;
/*  39:    */   protected LazyDataModel<SolicitudCompra> listaSolicitudCompra;
/*  40:    */   private List<DetalleSolicitudCompra> listaDetalleSolicitudSeleccionado;
/*  41:    */   private DataTable dtSolicitudcompra;
/*  42:    */   private SelectItem[] listaEstadoItem;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 64 */     setDocumentoBase(DocumentoBase.SOLICITUD_COMPRA);
/*  48:    */     
/*  49: 66 */     this.listaSolicitudCompra = new LazyDataModel()
/*  50:    */     {
/*  51:    */       private static final long serialVersionUID = 1L;
/*  52:    */       
/*  53:    */       public List<SolicitudCompra> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  54:    */       {
/*  55: 73 */         List<SolicitudCompra> lista = new ArrayList();
/*  56: 74 */         if (filters.size() == 0) {
/*  57: 76 */           filters.put("estado", "=" + EstadoSolicitudCompraEnum.ELABORADO.name());
/*  58:    */         }
/*  59: 78 */         filters = AprobacionSolicitudCompraBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/*  60: 79 */         for (String filterValue : AprobacionSolicitudCompraBean.this.getFiltrosListado(filters).keySet()) {
/*  61: 80 */           if (!filters.containsKey(filterValue)) {
/*  62: 81 */             filters.put(filterValue, AprobacionSolicitudCompraBean.this.getFiltrosListado(filters).get(filterValue));
/*  63:    */           }
/*  64:    */         }
/*  65: 84 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  66:    */         try
/*  67:    */         {
/*  68: 87 */           lista = AprobacionSolicitudCompraBean.this.servicioSolicitudCompra.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  69: 88 */           AprobacionSolicitudCompraBean.this.listaSolicitudCompra.setRowCount(AprobacionSolicitudCompraBean.this.servicioSolicitudCompra.contarPorCriterio(filters));
/*  70:    */         }
/*  71:    */         catch (ExcepcionAS2 e)
/*  72:    */         {
/*  73: 91 */           AprobacionSolicitudCompraBean.this.addInfoMessage(AprobacionSolicitudCompraBean.this.getLanguageController().getMensaje("msg_info_carga_datos"));
/*  74: 92 */           AprobacionSolicitudCompraBean.LOG.info("ERROR AL CARGAR LISTA SOLICITUD COMPRA", e);
/*  75:    */         }
/*  76: 95 */         return lista;
/*  77:    */       }
/*  78:    */     };
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Map<String, String> getFiltrosListado(Map<String, String> filters)
/*  82:    */   {
/*  83:102 */     if (filters == null) {
/*  84:103 */       filters = new HashMap();
/*  85:    */     }
/*  86:104 */     filters.put("indicadorConsolidado", "false");
/*  87:105 */     return filters;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void enviarMail()
/*  91:    */   {
/*  92:109 */     if ((getSolicitudCompra() != null) && (getSolicitudCompra().getId() != 0))
/*  93:    */     {
/*  94:110 */       this.servicioSolicitudCompra.enviarEmail(this.servicioSolicitudCompra.cargarDetalle(getSolicitudCompra().getId()), this.mails);
/*  95:111 */       this.mails = null;
/*  96:112 */       addInfoMessage(getLanguageController().getMensaje("msg_info_emails_enviados"));
/*  97:    */     }
/*  98:    */     else
/*  99:    */     {
/* 100:114 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String editar()
/* 105:    */   {
/* 106:120 */     if ((getSolicitudCompra() != null) && (getSolicitudCompra().getId() != 0))
/* 107:    */     {
/* 108:121 */       this.solicitudCompra = this.servicioSolicitudCompra.cargarDetalle(this.solicitudCompra.getId());
/* 109:122 */       if (this.solicitudCompra.getEstado().equals(EstadoSolicitudCompraEnum.ELABORADO)) {
/* 110:123 */         setEditado(true);
/* 111:    */       } else {
/* 112:126 */         addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 113:    */       }
/* 114:    */     }
/* 115:    */     else
/* 116:    */     {
/* 117:129 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 118:    */     }
/* 119:132 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String eliminar()
/* 123:    */   {
/* 124:136 */     addInfoMessage("msg_accion_no_permitida");
/* 125:137 */     return null;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String guardar()
/* 129:    */   {
/* 130:    */     try
/* 131:    */     {
/* 132:144 */       this.servicioSolicitudCompra.aprobar(this.solicitudCompra);
/* 133:145 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 134:146 */       cargarDatos();
/* 135:    */     }
/* 136:    */     catch (Exception e)
/* 137:    */     {
/* 138:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 139:149 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 140:    */     }
/* 141:151 */     return "";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String limpiar()
/* 145:    */   {
/* 146:156 */     this.solicitudCompra = null;
/* 147:157 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void cargarDetallesListado(ToggleEvent event)
/* 151:    */   {
/* 152:161 */     this.solicitudCompra = ((SolicitudCompra)event.getData());
/* 153:162 */     this.solicitudCompra = this.servicioSolicitudCompra.cargarDetalle(this.solicitudCompra.getId());
/* 154:163 */     setListaDetalleSolicitudSeleccionado(this.solicitudCompra.getListaDetalleSolicitudCompra());
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String cargarDatos()
/* 158:    */   {
/* 159:    */     try
/* 160:    */     {
/* 161:169 */       setEditado(false);
/* 162:170 */       limpiar();
/* 163:    */     }
/* 164:    */     catch (Exception e)
/* 165:    */     {
/* 166:172 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 167:173 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 168:    */     }
/* 169:175 */     return "";
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List<DetalleSolicitudCompra> getListaDetalleSolicitudCompra()
/* 173:    */   {
/* 174:179 */     List<DetalleSolicitudCompra> detalle = new ArrayList();
/* 175:180 */     for (DetalleSolicitudCompra dsc : getSolicitudCompra().getListaDetalleSolicitudCompra()) {
/* 176:181 */       if (!dsc.isEliminado()) {
/* 177:182 */         detalle.add(dsc);
/* 178:    */       }
/* 179:    */     }
/* 180:185 */     return detalle;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void reversarAprobacionSolicitudCompra()
/* 184:    */   {
/* 185:    */     try
/* 186:    */     {
/* 187:190 */       this.solicitudCompra = this.servicioSolicitudCompra.cargarDetalle(this.solicitudCompra.getId());
/* 188:191 */       this.servicioSolicitudCompra.aprobar(this.solicitudCompra, true);
/* 189:    */     }
/* 190:    */     catch (Exception e)
/* 191:    */     {
/* 192:193 */       addErrorMessage(getLanguageController().getMensaje(" " + e.getMessage()));
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public SolicitudCompra getSolicitudCompra()
/* 197:    */   {
/* 198:199 */     return this.solicitudCompra;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setSolicitudCompra(SolicitudCompra solicitudCompra)
/* 202:    */   {
/* 203:203 */     this.solicitudCompra = solicitudCompra;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public LazyDataModel<SolicitudCompra> getListaSolicitudCompra()
/* 207:    */   {
/* 208:207 */     if (this.listaSolicitudCompra == null) {
/* 209:208 */       cargarDatos();
/* 210:    */     }
/* 211:210 */     return this.listaSolicitudCompra;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setListaSolicitudCompra(LazyDataModel<SolicitudCompra> listaSolicitudCompra)
/* 215:    */   {
/* 216:214 */     this.listaSolicitudCompra = listaSolicitudCompra;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public DataTable getDtSolicitudCompra()
/* 220:    */   {
/* 221:218 */     return this.dtSolicitudcompra;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setDtSolicitudCompra(DataTable dtSolicitudCompra)
/* 225:    */   {
/* 226:222 */     this.dtSolicitudcompra = dtSolicitudCompra;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public SelectItem[] getListaEstadoItem()
/* 230:    */   {
/* 231:231 */     if (this.listaEstadoItem == null)
/* 232:    */     {
/* 233:233 */       List<SelectItem> lista = new ArrayList();
/* 234:234 */       lista.add(new SelectItem("", ""));
/* 235:    */       
/* 236:236 */       SelectItem item = new SelectItem(EstadoSolicitudCompraEnum.ELABORADO, EstadoSolicitudCompraEnum.ELABORADO.getNombre());
/* 237:237 */       lista.add(item);
/* 238:    */       
/* 239:239 */       item = new SelectItem(EstadoSolicitudCompraEnum.APROBADO, EstadoSolicitudCompraEnum.APROBADO.getNombre());
/* 240:240 */       lista.add(item);
/* 241:    */       
/* 242:242 */       this.listaEstadoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 243:    */       
/* 244:244 */       Arrays.sort(this.listaEstadoItem, new SelectItemComparator());
/* 245:    */     }
/* 246:247 */     return this.listaEstadoItem;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String getMails()
/* 250:    */   {
/* 251:251 */     if ((this.mails == null) && (getSolicitudCompra() != null) && (getSolicitudCompra().getId() != 0)) {
/* 252:252 */       this.mails = this.servicioSolicitudCompra.getEmails(this.solicitudCompra);
/* 253:    */     }
/* 254:254 */     return this.mails;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setMails(String mails)
/* 258:    */   {
/* 259:258 */     this.mails = mails;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public List<DetalleSolicitudCompra> getListaDetalleSolicitudSeleccionado()
/* 263:    */   {
/* 264:262 */     return this.listaDetalleSolicitudSeleccionado;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setListaDetalleSolicitudSeleccionado(List<DetalleSolicitudCompra> listaDetalleSolicitudSeleccionado)
/* 268:    */   {
/* 269:266 */     this.listaDetalleSolicitudSeleccionado = listaDetalleSolicitudSeleccionado;
/* 270:    */   }
/* 271:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.AprobacionSolicitudCompraBean
 * JD-Core Version:    0.7.0.1
 */